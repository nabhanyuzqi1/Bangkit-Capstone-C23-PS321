const express = require("express");
const admin = require("firebase-admin");

const db = admin.firestore();
const dompetCollection = db.collection("dompet");

const dompetRouter = express.Router();

// Endpoint untuk mengunggah data Dompet user dan teknisi
dompetRouter.post("/", async (req, res) => {
  try {
    const { userId, technicianId, totalDana, mutasi, waktu } = req.body;

    // Memastikan totalDana memiliki nilai yang valid
    if (typeof totalDana !== "number" || isNaN(totalDana)) {
      return res.status(400).json({ error: "Nilai totalDana tidak valid" });
    }

    // Membuat objek data dompet
    const dompetData = {
      userId,
      technicianId,
      totalDana,
      mutasi,
      waktu,
      timestamp: new Date(),
    };

    // Menyimpan data dompet ke Firebase Firestore
    const docRef = await dompetCollection.add(dompetData);
    const dompetId = docRef.id;

    // Memasukkan dompetId ke dalam dokumen Firestore
    await docRef.update({ dompetId });

    res.status(200).json({
      status: "success",
      dompetId: dompetId,
      message: "Berhasil mengakses dompet!",
    });
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Gagal mengakses dompet.",
    });
  }
});

// Endpoint untuk mengambil data Dompet user maupun teknisi
dompetRouter.get("/:dompetId", async (req, res) => {
  try {
    const { dompetId } = req.params;

    // Mengambil data dompet dari Firebase Firestore berdasarkan dompetId
    const docRef = await dompetCollection.doc(dompetId).get();

    if (!docRef.exists) {
      return res.status(404).json({ error: "Dompet tidak ditemukan" });
    }

    const dompetData = docRef.data();

    res.json({ dompetId: docRef.id, ...dompetData });
  } catch (error) {
    console.error("Error mengakses dompet:", error);
    res.status(500).json({ error: "Gagal mengakses dompet" });
  }
});

module.exports = dompetRouter;
