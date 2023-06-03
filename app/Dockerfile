# Use the base TensorFlow image
FROM tensorflow/tensorflow:2.6.0

# Install TensorFlow Lite library
RUN pip install tensorflow==2.6.0

# Copy the TFLite model into the container
COPY model.tflite /app/model.tflite

# Copy the main.py file into the container
COPY main.py /app/main.py

# Install dependencies
RUN pip install flask

# Expose 8080 port
EXPOSE 8080

# Run the application
CMD ["python", "/app/main.py"]
