import traceback
import tensorflow as tf
import numpy as np
import pandas as pd

from firebase_admin import initialize_app
from firebase_functions import  https_fn
import flask

# Initialize Firebase
initialize_app()

# Initialize Flask app
app = flask.Flask(__name__)

tech_data = pd.read_csv('technicians_data.csv')
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

class PredictionRequest:
    def __init__(self, user_id, needed_ban, needed_mesin, needed_bodi, needed_interior, needed_oli):
        self.user_id = user_id
        self.needed_ban = needed_ban
        self.needed_mesin = needed_mesin
        self.needed_bodi = needed_bodi
        self.needed_interior = needed_interior
        self.needed_oli = needed_oli


@https_fn.on_request()
def predict_technician(req: https_fn.Request) -> https_fn.Response:
    try:
        if req.content_type != "application/json":
            return https_fn.Response("Unsupported Media Type. Expected 'application/json'.", status=415)
        
        data = req.get_json()
        req = PredictionRequest(data['user_id'], data['needed_ban'], data['needed_mesin'], data['needed_bodi'],
                                data['needed_interior'], data['needed_oli'])

        print("PredictionRequest", req)
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
        predicted_technicians_id = int(np.round(np.clip(predictions[0][0], 1, 20)))
        predicted_rating = int(np.round(np.clip(predictions[0][1], 1, 5)))

        # Get the covered columns
        covered_columns = ['covered_mesin', 'covered_ban', 'covered_bodi', 'covered_interior', 'covered_oli']

        # Convert the covered columns to integers

        # Find the recommended technicians based on user's needs
        recommended_technicians = []
        for index, row in tech_data.iterrows():
            if (row[covered_columns[0]] == 1 and int(needed_mesin) == 1) or \
                    (row[covered_columns[1]] == 1 and int(needed_ban) == 1) or \
                    (row[covered_columns[2]] == 1 and int(needed_bodi) == 1) or \
                    (row[covered_columns[3]] == 1 and int(needed_interior) == 1) or \
                    (row[covered_columns[4]] == 1 and int(needed_oli) == 1):
                recommended_technicians.append(row['technicians_id'])

        # Prepare the response payload
        response_payload = {
            "Predicted Technicians ID": int(predicted_technicians_id),
            "Predicted Rating": int(predicted_rating),
            "Recommended Technicians ID": [int(tid) for tid in recommended_technicians]
        }


        print("user_id", user_id)
        print("needed_ban", needed_ban)
        print("needed_mesin", needed_mesin)
        print("needed_bodi", needed_bodi)
        print("needed_interior", needed_interior)
        print("needed_oli", needed_oli)
        print(response_payload)
        return response_payload
    except Exception as e:
        traceback.print_exc()
        return https_fn.Response("Internal Server Error", status=500)