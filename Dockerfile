FROM tensorflow/tensorflow:2.6.0-lite

# Copy the TFLite model into the container
COPY model.tflite /app/model.tflite

# Copy the main.py file into the container
COPY main.py /app/main.py

# Install dependencies
RUN pip install flask

# Run the application
CMD ["python", "/app/main.py"]
