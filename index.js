const express = require("express");
const bodyParser = require("body-parser");
const admin = require("firebase-admin");
const { Storage } = require("@google-cloud/storage");
const UUID = require("uuid-v4");
const multer = require("multer");
const upload = multer({ dest: "Obeng_images/" });
const serviceAccount = require("./serviceAccountKey.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const app = express();
app.use(bodyParser.json());

const db = admin.firestore();
const serviceRequestsCollection = admin
  .firestore()
  .collection("service-requests");
const usersCollection = admin.firestore().collection("users");
const techniciansCollection = admin.firestore().collection("technicians");
const feedbacksCollection = admin.firestore().collection("feedback");
const dompetCollection = db.collection("dompet");
const storage = new Storage({
  keyFilename: "serviceAccountKey.json",
});

/// ----------------------Endpoint untuk mengunggah data profil user (users)---------------------
app.post("/api/users/data", upload.fields([
  { name: "fotoKTP", maxCount: 1 },
  { name: "fotoProfil", maxCount: 1 }
]), async (req, res) => {
  try {
    const { nama, email, password, alamat, NIK } = req.body;
    const { fotoKTP, fotoProfil } = req.files;

    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    // URL foto KTP dan foto profil yang diunggah
    let fotoKTPUrl = "";
    let fotoProfilUrl = "";

    if (fotoKTP) {
      let uuid = UUID();
      const downloadPath =
        "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

      const fotoKTPResponse = await bucket.upload(fotoKTP[0].path, {
        destination: `usersKTP/${fotoKTP[0].originalname}`,
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
        destination: `usersProfil/${fotoProfil[0].originalname}`,
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

    const userRecord = await admin.auth().createUser({
      email,
      password,
    });

    const userId = userRecord.uid;
    const user = {
      id: userId,
      email,
      nama,
      alamat,
      NIK,
      fotoKTP: fotoKTPUrl,
      fotoProfil: fotoProfilUrl,
      role: "user",
      timestamp: new Date(),
    };

    await usersCollection.doc(userId).set(user);

    res.status(200).json({
      status: "success",
      userId: userId,
      message: "Register User berhasil!" 
    });
  } catch (error) {
    res.status(500).json({
       status: "error" ,
       message: "Register User gagal."
      });
  }
});

/// ----------------------Endpoint untuk mengubah data profil user (users)---------------------
app.put("/api/users/data/:userId", upload.fields([
  { name: "fotoKTP", maxCount: 1 },
  { name: "fotoProfil", maxCount: 1 }
]), async (req, res) => {
  try {
    const userId = req.params.userId;
    const { nama, email, password, alamat, NIK } = req.body;
    const { fotoKTP, fotoProfil } = req.files;

    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    const user = {
      nama,
      email,
      password,
      alamat,
      NIK,
    };

    // URL foto KTP dan foto profil yang diunggah
    let fotoKTPUrl = "";
    let fotoProfilUrl = "";

    if (fotoKTP) {
      let uuid = UUID();
      const downloadPath =
        "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

      const fotoKTPResponse = await bucket.upload(fotoKTP[0].path, {
        destination: `usersKTP/${fotoKTP[0].originalname}`,
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
        destination: `usersProfil/${fotoProfil[0].originalname}`,
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

    if (fotoKTPUrl !== "") {
      user.fotoKTP = fotoKTPUrl;
    }

    if (fotoProfilUrl !== "") {
      user.fotoProfil = fotoProfilUrl;
    }

    await usersCollection.doc(userId).update(user);

    res.json({ message: "User data updated successfully" });
  } catch (error) {
    console.error("Error updating user data:", error);
    res.status(500).json({ error: "Failed to update user data" });
  }
});

/// --------------------Endpoint untuk mengunggah data teknisi (technicians)---------------------
app.post(
  "/api/technicians/data",
  upload.fields([
    { name: "fotoKTP", maxCount: 1 },
    { name: "fotoProfil", maxCount: 1 }
  ]),
  async (req, res) => {
    try {
      const {
        nama,
        email,
        password,
        noHandphone,
        keahlian,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKeahlian,
      } = req.body;
      const { fotoKTP, fotoProfil } = req.files;

      const userRecord = await admin.auth().createUser({
        email,
        password,
      });

      const technicianId = userRecord.uid;
      const technician = {
        technicianId,
        nama,
        email,
        noHandphone,
        keahlian,
        NIK,
        linkSertifikasi,
        linkPortofolio,
        jenisKeahlian,
        role: "technician",
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
        message: "Register Teknisi berhasil!" 
      });
    } catch (error) {
      res.status(500).json({ 
        status: "error",
        message: "Register Teknisi gagal." 
      });
    }
  }
);

/// -------------------- Endpoint untuk mengubah data teknisi (technicians) ---------------------
app.put("/api/technicians/data/:technicianId", upload.fields([
  { name: "fotoKTP", maxCount: 1 },
  { name: "fotoProfil", maxCount: 1 }
]), async (req, res) => {
  try {
    const technicianId = req.params.technicianId;
    const {
      nama,
      email,
      password,
      noHandphone,
      keahlian,
      NIK,
      linkSertifikasi,
      linkPortofolio,
      jenisKeahlian,
    } = req.body;
    const { fotoKTP, fotoProfil } = req.files;

    const technician = {
      nama,
      email,
      password,
      noHandphone,
      keahlian,
      NIK,
      linkSertifikasi,
      linkPortofolio,
      jenisKeahlian,
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

    if (fotoKTPUrl !== "") {
      technician.fotoKTP = fotoKTPUrl;
    }

    if (fotoProfilUrl !== "") {
      technician.fotoProfil = fotoProfilUrl;
    }

    await techniciansCollection.doc(technicianId).update(technician);

    res.json({ message: "Technician data updated successfully" });
  } catch (error) {
    console.error("Error updating technician data:", error);
    res.status(500).json({ error: "Failed to update technician data" });
  }
});

///---------------- Endpoint untuk mengambil semua data pengguna (users)----------------------
app.get("/api/users", (req, res) => {
  usersCollection
    .get()
    .then((snapshot) => {
      const users = [];
      snapshot.forEach((doc) => {
        users.push(doc.data());
      });
      res.json(users);
    })
    .catch((error) => {
      console.error("Error getting users:", error);
      res.status(500).json({ error: "Failed to get users" });
    });
});

///------------------ Endpoint untuk mengambil semua data teknisi (technicians)------------------
app.get("/api/technicians", (req, res) => {
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

///-- (kode 000)Endpoint untuk mengambil data teknisi berdasarkan jenis keahlian (technicians) --
app.get("/api/technicians/by-jenis-keahlian", (req, res) => {
  const { jenisKeahlian } = req.query;

  techniciansCollection
    .where("jenisKeahlian", "==", jenisKeahlian)
    .get()
    .then((snapshot) => {
      const technicians = [];
      snapshot.forEach((doc) => {
        const technician = {
          nama: doc.data().nama,
          jenisKeahlian: doc.data().jenisKeahlian,
        };
        technicians.push(technician);
      });
      res.json(technicians);
    })
    .catch((error) => {
      console.error("Error getting technicians:", error);
      res.status(500).json({ error: "Failed to get technicians" });
    });
});

///----------- 000 Endpoint untuk mengambil data user berdasarkan jenis email (users) ----------
app.get("/api/users/by-email/:email", (req, res) => {
  const email = req.params.email;

  usersCollection
    .where("email", "==", email)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.status(404).json({ error: "User not found" });
      } else {
        const users = [];
        snapshot.forEach((doc) => {
          const user = doc.data();
          users.push(user);
        });
        res.json(users);
      }
    })
    .catch((error) => {
      console.error("Error getting users:", error);
      res.status(500).json({ error: "Failed to get users" });
    });
});

///-------------- Endpoint untuk mengirim service-request atau permintaan layanan ---------------
app.post(
  "/api/service-requests",
  upload.single("fotoBarang"),
  async (req, res) => {
    try {
      const { alamatUser, detailBarang, jenisKendaraan, rincianPembayaran, statusPesanan, waktuPesanan, tanggalPesanan, lokasi } = req.body;
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
        message: "Service request berhasil dibuat!"
      });
    } catch (error) {
      res.status(500).json({
        status: "error",
        message: "Service request gagal dibuat.",
      });
    }
  }
);

///----------------- Endpoint untuk mengambil semua data permintaan layanan ---------------------
app.get("/api/service-requests", async (req, res) => {
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
app.get("/api/service-requests/:idPesanan", async (req, res) => {
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

///------------------- Endpoint untuk mengubah penerimaan service-request -----------------------
app.patch("/api/service-requests/:requestId/penerimaan", (req, res) => {
  const { requestId } = req.params;
  const { acceptanceStatus } = req.body;

  const serviceRequestsRef = db.collection("serviceRequests").doc(requestId);

  if (acceptanceStatus !== undefined) {
    serviceRequestsRef
      .update({ acceptanceStatus })
      .then(() => {
        res.json({
          status: "success",
          message: "Penerimaan pekerjaan berhasil diperbarui",
        });
      })
      .catch((error) => {
        console.error("Error memperbarui penerimaan pekerjaan:", error);
        res
          .status(500)
          .json({ error: "Gagal memperbarui penerimaan pekerjaan" });
      });
  } else {
    console.log("acceptanceStatus is undefined. Skipping update.");
    res.status(400).json({ error: "acceptanceStatus is undefined" });
  }
});

///------ Endpoint untuk memperbarui data feedback dan rating untuk teknisi(technicians) --------
app.patch("/api/technicians/:technicianId/feedback", async (req, res) => {
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
    await technicianRef.update({
      feedbackId: feedbackRef.id,
    });

    res.json({
      status: "sukses",
      pesan: "Feedback dan rating berhasil diperbarui",
    });
  } catch (error) {
    console.error(
      "Terjadi kesalahan saat memperbarui feedback dan rating:",
      error
    );
    res.status(500).json({ error: "Gagal memperbarui feedback dan rating" });
  }
});

///---- Endpoint untuk mengambil feedback data teknisi berdasarkan id teknisi (technicians) -----
app.get("/api/technicians/:technicianId/feedbacks", async (req, res) => {
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

///------------------ Endpoint untuk mengunggah data Dompet user dan teknisi --------------------
app.post("/api/dompet", async (req, res) => {
  try {
    const { totalDana, mutasi, waktu } = req.body;

    // Memastikan totalDana memiliki nilai yang valid
    if (typeof totalDana !== "number" || isNaN(totalDana)) {
      return res.status(400).json({ error: "Invalid totalDana value" });
    }

    // Membuat objek data dompet
    const dompetData = {
      totalDana,
      mutasi,
      waktu,
    };

    // Menyimpan data dompet ke Firebase Firestore
    const docRef = await dompetCollection.add(dompetData);

    res.status(200).json({ 
      status: "success",
      dompetId: dompetData,
      message: "Berhasil mengakses dompet!"
    });
  } catch (error) {
    res.status(500).json({ 
      status: "error",
      message: "Gagal mengakses dompet." 
    });
  }
});

///------------------ Endpoint untuk mengambil data Dompet user maupun teknisi ------------------
app.get("/api/dompet/:dompetId", async (req, res) => {
  try {
    const { dompetId } = req.params;

    // Mengambil data dompet dari Firebase Firestore berdasarkan dompetId
    const docRef = await dompetCollection.doc(dompetId).get();

    if (!docRef.exists) {
      return res.status(404).json({ error: "Dompet not found" });
    }

    const dompetData = docRef.data();

    res.json({ dompetId: docRef.id, ...dompetData });
  } catch (error) {
    console.error("Error retrieving dompet:", error);
    res.status(500).json({ error: "Failed to retrieve dompet" });
  }
});

app.listen(5000, () => {
  console.log("Obeng REST API listening on port 5000");
});

// newest : CRU data user and technicians(new 10/06/23), Mengupload NIK, foto ktp dan foto profil pada register (new), menambah item pada serv-req (new), POST and GET API Dompet.
// newest : Memperbarui service request , endpoint mengambil histori pesanan (update), endpoint mengupdate status pesanan (update)
// kode 000 > endpoint bisa dihapus tpi perlu diskusi dulu