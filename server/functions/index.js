const functions = require('firebase-functions');
const express = require('express');
const cors = require('cors');
const { paymentSnap } = require('./utils/payment');
const { modelRequest } = require('./utils/recommendation');
const bodyParser = require('body-parser');

// Endpoint for processing payment with Midtrans
const app = express();
app.use(cors());
app.use(express.json());
app.post('/process-payment', paymentSnap);
exports.api = functions.https.onRequest(app);



// Create and export the Firebase Cloud Function
const modelapp = express();
modelapp.use(bodyParser.json());
modelapp.post('/recommendation', modelRequest);
exports.model = functions.https.onRequest(modelapp);
