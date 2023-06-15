const express = require("express");
const fetch = require("node-fetch");
const paymentRouter = express.Router();

paymentRouter.post("/payment", (req, res) => {
  const { order_id, amount } = req.body;

  processPayment(order_id, amount)
    .then((data) => {
      // Tanggapan dari Midtrans
      res.json(data);
    })
    .catch((error) => {
      res.status(500).json({ error: error.message });
    });
});

function processPayment(orderId, amount) {
  const midtransUrl = "https://app.sandbox.midtrans.com/snap/v1/transactions";
  const midtransOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization:
        "Basic U0ItTWlkLXNlcnZlci1INEdiZ051amZZcDEyeW1TdXYtcVdmeUk6",
    },
    body: JSON.stringify({
      transaction_details: { order_id: orderId, gross_amount: amount },
      credit_card: { secure: true },
    }),
  };

  return fetch(midtransUrl, midtransOptions)
    .then((response) => response.json())
    .catch((error) => {
      console.error("Error:", error);
      throw new Error("An error occurred while processing payment.");
    });
}

module.exports = paymentRouter;

