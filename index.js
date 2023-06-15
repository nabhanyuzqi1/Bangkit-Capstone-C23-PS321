const express = require("express");
const bodyParser = require("body-parser");
const admin = require("firebase-admin");
const serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const app = express();
app.use(bodyParser.json());

// Endpoint untuk mengakses APIs
const usersRouter = require("./src/users");
const techniciansRouter = require("./src/technicians");
const serviceReqRouter = require("./src/service-req");
const dompetRouter = require("./src/dompet");
const paymentRouter = require("./src/payment");

// Endpoint untuk memproses APIs
app.use("/api/users", usersRouter);
app.use("/api/technicians", techniciansRouter);
app.use("/api/service-requests", serviceReqRouter);
app.use("/api/dompet", dompetRouter);
app.post("/payment", paymentRouter);

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
  console.log("Obeng REST API listening on port", PORT);
});


