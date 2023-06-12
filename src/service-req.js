const express = require("express");
const bodyParser = require("body-parser");
const admin = require("firebase-admin");
const { Storage } = require("@google-cloud/storage");
const UUID = require("uuid-v4");
const multer = require("multer");
const upload = multer({ dest: "Obeng_images/" });

const app = express();
app.use(bodyParser.json());

const db = admin.firestore();
const storage = new Storage({
  keyFilename: "serviceAccountKey.json",
});

const serviceReqRouter = express.Router();

////-------------- Endpoint untuk mengirim service-request atau permintaan layanan --------------
serviceReqRouter.post("/", upload.single("fotoBarang"), async (req, res) => {
  try {
    const {
      alamatUser,
      detailBarang,
      jenisKendaraan,
      rincianPembayaran,
      statusPesanan,
      waktuPesanan,
      tanggalPesanan,
      lokasi,
    } = req.body;
    const fotoBarang = req.file;

    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    // URL gambar barang yang diunggah
    let fotoBarangUrl = "";

    if (fotoBarang) {
      let uuid = UUID();
      const downLoadPath =
        "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

      const fotoBarangResponse = await bucket.upload(fotoBarang.path, {
        destination: `fotoBarang/${fotoBarang.originalname}`,
        resumable: true,
        metadata: {
          metadata: {
            firebaseStorageDownloadTokens: uuid,
          },
        },
      });

      // URL gambar barang
      fotoBarangUrl =
        downLoadPath +
        encodeURIComponent(fotoBarangResponse[0].name) +
        "?alt=media&token=" +
        uuid;
    }

    const serviceRequestRef = db.collection("serviceRequests").doc();
    const IdPesanan = serviceRequestRef.id; // Menggunakan ID dokumen sebagai ID pesanan
    const serviceRequestData = {
      IdPesanan, // Menyimpan ID pesanan di data permintaan layanan
      lokasi,
      alamatUser,
      jenisKendaraan,
      fotoBarang: fotoBarangUrl,
      detailBarang,
      rincianPembayaran,
      statusPesanan,
      waktuPesanan,
      tanggalPesanan,
      timestamp: new Date(), // Menambahkan properti timestamp dengan waktu saat ini
    };

    await serviceRequestRef.set(serviceRequestData); // Menyimpan data permintaan layanan ke Firestore

    res.status(200).json({
      status: "success",
      IdPesanan: IdPesanan, // Mengembalikan ID pesanan sebagai nomor pesanan
      message: "Service request berhasil dibuat!",
    });
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Service request gagal dibuat.",
    });
  }
});

///----------------- Endpoint untuk mengambil semua data permintaan layanan ---------------------
serviceReqRouter.get("/", async (req, res) => {
  try {
    const serviceRequestsRef = db.collection("serviceRequests");
    const serviceRequestsSnapshot = await serviceRequestsRef.get();

    if (serviceRequestsSnapshot.empty) {
      res.status(404).json({
        status: "error",
        message: "No service requests found.",
      });
    } else {
      const serviceRequestsData = [];

      serviceRequestsSnapshot.forEach((doc) => {
        const serviceRequestData = doc.data();
        serviceRequestsData.push(serviceRequestData);
      });

      res.status(200).json({
        status: "success",
        serviceRequestsData,
      });
    }
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Failed to retrieve service requests.",
    });
  }
});

/// ----------- Endpoint untuk mengambil data permintaan layanan berdasarkan ID -----------------
serviceReqRouter.get("/:idPesanan", async (req, res) => {
  try {
    const { idPesanan } = req.params;

    const serviceRequestRef = db.collection("serviceRequests").doc(idPesanan);
    const serviceRequestDoc = await serviceRequestRef.get();

    if (!serviceRequestDoc.exists) {
      res.status(404).json({
        status: "error",
        message: "Service request not found.",
      });
    } else {
      const serviceRequestData = serviceRequestDoc.data();

      res.status(200).json({
        status: "success",
        serviceRequestData,
      });
    }
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Failed to retrieve service request.",
    });
  }
});

module.exports = serviceReqRouter;
