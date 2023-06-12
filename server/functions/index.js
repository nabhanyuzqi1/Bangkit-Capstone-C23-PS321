const axios = require('axios');
const functions = require('firebase-functions');
const admin = require('firebase-admin');
const express = require('express');
const cors = require('cors');
const serviceAccount = require('./serviceAccountKey.json'); // Path to your Firebase service account key JSON file

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  storageBucket: 'loginsignup-auth-dc6a9.appspot.com' // Replace with your Firebase Storage bucket name
});

// Initialize Express
const app = express();
app.use(cors());
app.use(express.json());

// Endpoint for processing payment with Midtrans
app.post('/process-payment', async (req, res) => {
  try {
    const options = {
      method: 'POST',
      url: 'https://app.sandbox.midtrans.com/snap/v1/transactions',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Basic U0ItTWlkLXNlcnZlci1INEdiZ051amZZcDEyeW1TdXYtcVdmeUk6'
      },
      data: {
        transaction_details: {
          order_id: 'order-id',
          gross_amount: 10000
        },
        credit_card: {
          secure: true
        }
      }
    };

    const response = await axios.request(options);
    console.log(response.data);

    res.status(200).json({ response: response.data });
  } catch (error) {
    console.error('Error generating Snap token:', error);
    res.status(500).json({ message: 'Error generating Snap token', error });
  }
});



// Expose the Express app as a Firebase Cloud Function
exports.api = functions.https.onRequest(app);
