from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np

# Initialize Flask
app = Flask(__name__)

# Define the predict endpoint
@app.route('/predict', methods=['POST'])
def predict():
    # TFLite model
    model_file = 'gs://model_obeng/model.tflite' 
    interpreter = tf.lite.Interpreter(model_path=model_file)
    interpreter.allocate_tensors()

    # Preprocess input data from the request
    data = request.get_json()
    user_id = data['user_id']
    needed_mesin = data['needed_mesin']
    needed_ban = data['needed_ban']
    needed_bodi = data['needed_bodi']
    needed_interior = data['needed_interior']
    needed_oli = data['needed_oli']
    input_data = np.array([[user_id, needed_mesin, needed_ban, needed_bodi, needed_interior, needed_oli]], dtype=np.float32)

    # Set the input tensor
    input_details = interpreter.get_input_details()
    interpreter.set_tensor(input_details[0]['index'], input_data)

    # Run the inference
    interpreter.invoke()

    # Get the output tensors
    output_details = interpreter.get_output_details()
    technician_id = int(np.argmax(interpreter.get_tensor(output_details[0]['index'])))
    covered = int(np.argmax(interpreter.get_tensor(output_details[1]['index'])))
    rating = int(np.argmax(interpreter.get_tensor(output_details[2]['index'])))

    # Create the response
    response = {
        'technician_id': technician_id,
        'covered': covered,
        'rating': rating
    }

    return jsonify(response)

# Run the Flask application
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=8080)
