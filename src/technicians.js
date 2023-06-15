const express = require("express");
const bodyParser = require("body-parser");
const admin = require("firebase-admin");
const { Storage } = require("@google-cloud/storage");
const UUID = require("uuid-v4");
const multer = require("multer");
const upload = multer({ dest: "Obeng_images/" });

const app = express();
app.use(bodyParser.json());

const techniciansCollection = admin.firestore().collection("technicians");
const storage = new Storage({
  keyFilename: "serviceAccountKey.json",
});

const techniciansRouter = express.Router();

// Fungsi untuk menghasilkan uniqueNumericId dengan minimal 8 digit
function generateUniqueNumericId() {
  const minDigits = 8;
  let uniqueId = "";
  while (uniqueId.length < minDigits) {
    const randomDigit = Math.floor(Math.random() * 10);
    uniqueId += randomDigit;
  }
  return Number(uniqueId); // Mengubah nilai menjadi angka (number)
}

// Endpoint untuk mengunggah data teknisi (technicians)
techniciansRouter.post(
  "/data",
  upload.fields([
    { name: "fotoKTP", maxCount: 1 },
    { name: "fotoProfil", maxCount: 1 },
  ]),
  async (req, res) => {
    try {
      const {
        nama,
        email,
        password,
        noHandphone,
        jenisKeahlian,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKendaraan,
      } = req.body;
      const { fotoKTP, fotoProfil } = req.files;

      // Check if username, email, noHandphone, or NIK already exists
      const existingTechnicianEmail = await techniciansCollection
        .where("email", "==", email)
        .get();

      const existingTechnicianNoHandphone = await techniciansCollection
        .where("noHandphone", "==", noHandphone)
        .get();

      const existingTechnicianNIK = await techniciansCollection
        .where("NIK", "==", NIK)
        .get();

      if (
        !existingTechnicianEmail.empty ||
        !existingTechnicianNoHandphone.empty ||
        !existingTechnicianNIK.empty
      ) {
        return res.status(400).json({
          status: "error",
          error: "Username, email, noHandphone, or NIK already exists",
        });
      }

      const userRecord = await admin.auth().createUser({
        email,
        password,
      });

      const technicianId = userRecord.uid;
      const technicianNumericId = generateUniqueNumericId();
      const jenisKeahlianArray = Array.isArray(jenisKeahlian)
      ? jenisKeahlian
      : jenisKeahlian.split(",").map((item) => item.trim());

      const technician = {
        technicianId,
        technicianNumericId,
        nama,
        email,
        noHandphone,
        jenisKeahlian: jenisKeahlianArray,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKendaraan,
        role: "technician",
        timestamp: new Date(),
      };

      const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

      // URL foto KTP dan foto profil yang diunggah
      let fotoKTPUrl = "";
      let fotoProfilUrl = "";

      if (fotoKTP) {
        let uuid = UUID();
        const downloadPath =
          "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

        const fotoKTPResponse = await bucket.upload(fotoKTP[0].path, {
          destination: `techniciansKTP/${fotoKTP[0].originalname}`,
          resumable: true,
          metadata: {
            metadata: {
              firebaseStorageDownloadTokens: uuid,
            },
          },
        });

        // URL foto KTP
        fotoKTPUrl =
          downloadPath +
          encodeURIComponent(fotoKTPResponse[0].name) +
          "?alt=media&token=" +
          uuid;
      }

      if (fotoProfil) {
        let uuid = UUID();
        const downloadPath =
          "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

        const fotoProfilResponse = await bucket.upload(fotoProfil[0].path, {
          destination: `techniciansProfil/${fotoProfil[0].originalname}`,
          resumable: true,
          metadata: {
            metadata: {
              firebaseStorageDownloadTokens: uuid,
            },
          },
        });

        // URL foto profil
        fotoProfilUrl =
          downloadPath +
          encodeURIComponent(fotoProfilResponse[0].name) +
          "?alt=media&token=" +
          uuid;
      }

      technician.fotoKTP = fotoKTPUrl;
      technician.fotoProfil = fotoProfilUrl;

      await techniciansCollection.doc(userRecord.uid).set(technician);

      res.status(200).json({
        status: "success",
        technicianId: technicianId,
        technicianNumericId: technicianNumericId,
        message: "Register Teknisi berhasil!",
      });
    } catch (error) {
      res.status(500).json({
        status: "error",
        message: "Register Teknisi gagal.",
      });
    }
  }
);

// Endpoint untuk mengambil semua data teknisi (technicians)
techniciansRouter.get("/data", (req, res) => {
  techniciansCollection
    .get()
    .then((snapshot) => {
      const technicians = [];
      snapshot.forEach((doc) => {
        technicians.push(doc.data());
      });
      res.json(technicians);
    })
    .catch((error) => {
      console.error("Error getting technicians:", error);
      res.status(500).json({ error: "Failed to get technicians" });
    });
});

// Endpoint untuk mengubah data teknisi (technicians)
techniciansRouter.put(
  "/data/:technicianId",
  upload.fields([
    { name: "fotoKTP", maxCount: 1 },
    { name: "fotoProfil", maxCount: 1 },
  ]),
  async (req, res) => {
    try {
      const technicianId = req.params.technicianId;
      const {
        nama,
        email,
        password,
        noHandphone,
        jenisKeahlian,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKendaraan,
      } = req.body;
      const { fotoKTP, fotoProfil } = req.files;

      // Check if username, email, noHandphone, or NIK already exists
      const existingTechnicianEmail = await techniciansCollection
        .where("email", "==", email)
        .get();

      const existingTechnicianNoHandphone = await techniciansCollection
        .where("noHandphone", "==", noHandphone)
        .get();

      const existingTechnicianNIK = await techniciansCollection
        .where("NIK", "==", NIK)
        .get();

      if (
        !existingTechnicianEmail.empty ||
        !existingTechnicianNoHandphone.empty ||
        !existingTechnicianNIK.empty
      ) {
        return res.status(400).json({
          status: "error",
          error: "Username, email, noHandphone, or NIK already exists",
        });
      }

      const jenisKeahlianArray = Array.isArray(jenisKeahlian)
      ? jenisKeahlian
      : jenisKeahlian.split(",").map((item) => item.trim());
      const technician = {
        nama,
        email,
        password,
        noHandphone,
        jenisKeahlian: jenisKeahlianArray,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKendaraan,
      };

      const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

      // URL foto KTP dan foto profil yang diunggah
      let fotoKTPUrl = "";
      let fotoProfilUrl = "";

      if (fotoKTP && fotoKTP.length > 0) {
        const fotoKTPResponse = await bucket.upload(fotoKTP[0].path, {
          destination: `techniciansKTP/${fotoKTP[0].originalname}`,
          resumable: true,
          metadata: {
            metadata: {
              firebaseStorageDownloadTokens: UUID(),
            },
          },
        });

        // URL foto KTP
        fotoKTPUrl = fotoKTPResponse[0].publicUrl();
      }

      if (fotoProfil && fotoProfil.length > 0) {
        const fotoProfilResponse = await bucket.upload(fotoProfil[0].path, {
          destination: `techniciansProfil/${fotoProfil[0].originalname}`,
          resumable: true,
          metadata: {
            metadata: {
              firebaseStorageDownloadTokens: UUID(),
            },
          },
        });

        // URL foto profil
        fotoProfilUrl = fotoProfilResponse[0].publicUrl();
      }

      if (fotoKTPUrl !== "") {
        technician.fotoKTP = fotoKTPUrl;
      }
      if (fotoProfilUrl !== "") {
        technician.fotoProfil = fotoProfilUrl;
      }

      await techniciansCollection.doc(technicianId).update(technician);

      res.status(200).json({
        status: "success",
        technicianId: technicianId,
        message: "Data teknisi berhasil diubah!",
      });
    } catch (error) {
      console.error("Error updating technician:", error);
      res.status(500).json({
        status: "error",
        error: "Gagal mengubah data teknisi.",
      });
    }
  }
);

// Endpoint untuk mengunggah data feedback dan rating untuk teknisi (technicians)
techniciansRouter.post("/:technicianId/feedback", async (req, res) => {
  try {
    const { technicianId } = req.params;
    const { pesan, rating } = req.body;

    // Validasi rating agar berada dalam rentang 1 hingga 5
    if (rating < 1 || rating > 5) {
      return res
        .status(400)
        .json({ error: "Rating harus berada dalam rentang 1 hingga 5" });
    }

    // Dapatkan referensi dokumen teknisi
    const technicianRef = techniciansCollection.doc(technicianId);

    // Periksa apakah teknisi tersebut ada
    const technicianDoc = await technicianRef.get();
    if (!technicianDoc.exists) {
      return res.status(404).json({ error: "Teknisi tidak ditemukan" });
    }

    // Buat objek feedback baru dengan ID yang dihasilkan otomatis oleh Firestore
    const feedback = {
      pesan,
      rating,
      timestamp: admin.firestore.FieldValue.serverTimestamp(),
    };

    // Tambahkan feedback ke sub-koleksi "feedbacks" pada dokumen teknisi
    const feedbackRef = await technicianRef
      .collection("feedbacks")
      .add(feedback);

    // Perbarui feedbackId pada dokumen teknisi
    await technicianRef.update({ feedbackId: feedbackRef.id });

    res.status(200).json({
      status: "success",
      feedbackId: feedbackRef.id,
      message: "Feedback dan rating berhasil diperbarui",
    });
  } catch (error) {
    console.error(
      "Terjadi kesalahan saat memperbarui feedback dan rating:",
      error
    );
    res.status(500).json({
      status: "error",
      message: "Gagal memperbarui feedback dan rating",
    });
  }
});

// Endpoint untuk mengambil feedback data teknisi berdasarkan id teknisi (technicians)
techniciansRouter.get("/:technicianId/feedbacks", async (req, res) => {
  try {
    const { technicianId } = req.params;

    // Dapatkan referensi koleksi feedbacks dari teknisi yang sesuai
    const feedbacksSnapshot = await techniciansCollection
      .doc(technicianId)
      .collection("feedbacks")
      .get();

    // Buat array untuk menyimpan feedback
    const feedbacks = [];

    // Loop melalui setiap dokumen feedback dan tambahkan ke array feedbacks
    feedbacksSnapshot.forEach((doc) => {
      const feedback = doc.data();
      feedbacks.push(feedback);
    });

    res.json({
      status: "sukses",
      feedbacks,
    });
  } catch (error) {
    console.error("Terjadi kesalahan saat mendapatkan feedback:", error);
    res.status(500).json({ error: "Gagal mendapatkan feedback" });
  }
});

module.exports = techniciansRouter;
