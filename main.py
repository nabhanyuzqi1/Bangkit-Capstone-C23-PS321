import os
import uvicorn
import traceback
import tensorflow as tf
import numpy as np
import pandas as pd

from pydantic import BaseModel
from urllib.request import Request
from fastapi import FastAPI, Response, UploadFile

# Define the custom metric function
def mean_average_precision(y_true, y_pred):
    # Extract the relevant information from y_true and y_pred
    y_true = test_features
    y_pred = test_target
    user_id = y_true[:, 0]
    needed = y_true[:, 1:6]
    technicians_id = y_pred[:, 0]
    rating = y_pred[:, 1]
    covered = y_pred[:, 2:7]

    # Compute the Average Precision (AP) for each user
    ap_values = []
    for i in range(len(user_id)):
        # Get the relevant items for the user from y_true
        relevant_items = np.where(needed[i] == 1)[0]

        # Get the predicted items for the user from y_pred
        predicted_items = np.where(covered[i] == 1)[0]

        # Compute the precision at each position
        precision = []
        for j in range(len(predicted_items)):
            if predicted_items[j] in relevant_items:
                precision.append(np.sum(predicted_items[:j+1] == predicted_items[j]) / (j+1))

        # Compute the Average Precision (AP) for the user
        ap = np.mean(precision) if precision else 0.0
        ap_values.append(ap)

    # Compute the Mean Average Precision (MAP) across all users
    map_value = np.mean(ap_values)
    
    return map_value

# Register the custom metric function
tf.keras.utils.get_custom_objects()['mean_average_precision'] = mean_average_precision

# Load the model
model = tf.keras.models.load_model('model.h5')

app = FastAPI()

class PredictionRequest(BaseModel):
    user_id: int
    needed_ban: int
    needed_mesin: int
    needed_bodi: int
    needed_interior: int
    needed_oli: int

@app.post("/predict")
def predict_technician(req: PredictionRequest):
    try:
        user_id = req.user_id
        needed_ban = req.needed_ban
        needed_mesin = req.needed_mesin
        needed_bodi = req.needed_bodi
        needed_interior = req.needed_interior
        needed_oli = req.needed_oli

        # Encode the user's input
        user_data = pd.DataFrame([[user_id, needed_ban, needed_mesin, needed_bodi, needed_interior, needed_oli]],
                                 columns=['user_id', 'needed_ban', 'needed_mesin', 'needed_bodi', 'needed_interior', 'needed_oli'])

        # Convert the user's encoded features to float32
        user_features = np.array(user_data).astype(np.float32)

        # Make predictions
        predictions = model.predict(user_features)

        # Retrieve the predicted technicians and ratings
        predicted_technicians_id = predictions[0][0]
        predicted_rating = predictions[0][1]
        predicted_covered = predictions[0][2:]
        predicted_technicians_id = int(np.round(np.clip(predicted_technicians_id, 1, 20)))
        predicted_rating = int(np.round(np.clip(predicted_rating, 1, 5)))

        # Convert the predicted_covered values to exact 1s and 0s
        predicted_covered_exact = np.argmax(predicted_covered)

        # Decode the one-hot encoded covered columns
        covered_columns = ['covered_ban', 'covered_mesin', 'covered_bodi', 'covered_interior', 'covered_oli']
        predicted_covered_dict = {}
        for i in range(len(covered_columns)):
            predicted_covered_dict[covered_columns[i]] = int(predicted_covered_exact == i)

        # Prepare the response payload
        response_payload = {
            "Predicted Technicians ID": predicted_technicians_id,
            "Predicted Rating": predicted_rating,
            "Predicted Covered": predicted_covered_dict
        }

        return response_payload
    except Exception as e:
        traceback.print_exc()
        response.status_code = 500
        return "Internal Server Error"


# Starting the server
port = os.environ.get("PORT", 8080)
print(f"Listening to http://0.0.0.0:{port}")
uvicorn.run(app, host='0.0.0.0', port=port)
