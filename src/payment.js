const axios = require('axios');

async function paymentSnap(req, res) {
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
          order_id: 'order-id3',
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
}

module.exports = {
  paymentSnap
};