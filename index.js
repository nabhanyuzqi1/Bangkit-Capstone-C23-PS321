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
    };

    await usersCollection.doc(userId).set(user);

    res.json({ message: "User registered successfully" });
  } catch (error) {
    console.error("Error registering user:", error);
    res.status(500).json({ error: "Failed to register user" });
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

      res.json({ message: "Technician registered successfully" });
    } catch (error) {
      console.error("Error registering technician:", error);
      res.status(500).json({ error: "Failed to register technician" });
    }
  }
);

/// ---------------Endpoint untuk mengubah data teknisi (technicians)-------------------
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

///----- (kode 000)Endpoint untuk mengambil data teknisi berdasarkan jenis keahlian (technicians) ----------
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

///------------- 000 Endpoint untuk mengambil data user berdasarkan jenis email (users) ------------
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

///---- Endpoint untuk mengunggah service-request atau permintaan layanan dari user (users) ----
app.post(
  "/api/service-requests/:userId",
  upload.single("fotoBarang"),
  async (req, res) => {
    try {
      const { userId } = req.params; // Mendapatkan userID dari parameter rute
      const { alamat, detailBarang } = req.body;
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

      const serviceRequestData = {
        alamat,
        fotoBarang: fotoBarangUrl,
        detailBarang,
        timestamp: new Date(),
      };

      const serviceRequestRef = await db
        .collection("serviceRequests")
        .add(serviceRequestData);

      const userRef = db.collection("users").doc(userId);

      // Simpan referensi service request di dalam dokumen pengguna
      await userRef
        .collection("serviceRequests")
        .doc(serviceRequestRef.id)
        .set({
          idPesanan: serviceRequestRef.id,
          alamat,
          fotoBarang: fotoBarangUrl,
          detailBarang,
          timestamp: new Date(),
        });

      res.status(200).json({
        status: "success",
        orderNumber: serviceRequestRef.id,
      });
    } catch (error) {
      res.status(500).json({
        status: "error",
        message: "Failed to create service request.",
      });
    }
  }
);

///---------------------- Mengambil histori pesanan-------------------------------
app.get("/api/service-requests/:userId", async (req, res) => {
  try {
    const { userId } = req.params;

    const userRef = db.collection("users").doc(userId);
    const serviceRequestsRef = userRef.collection("serviceRequests");

    const serviceRequestsSnapshot = await serviceRequestsRef.get();
    const serviceRequests = [];

    serviceRequestsSnapshot.forEach((doc) => {
      const serviceRequestData = doc.data();
      serviceRequests.push({
        idPesanan: doc.id,
        alamat: serviceRequestData.alamat,
        fotoBarang: serviceRequestData.fotoBarang,
        detailBarang: serviceRequestData.detailBarang,
        timestamp: serviceRequestData.timestamp,
        status: serviceRequestData.status,
      });
    });

    res.status(200).json({
      status: "success",
      serviceRequests: serviceRequests,
    });
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Failed to retrieve service requests.",
    });
  }
});

///-------- Endpoint untuk memperbarui status penerimaan pekerjaan teknisi (technicians) ----------
app.put("/api/service-requests/:userId/:serviceRequestId", async (req, res) => {
  try {
    const { userId, serviceRequestId } = req.params;
    const { status } = req.body;

    const userRef = db.collection("users").doc(userId);
    const serviceRequestRef = userRef
      .collection("serviceRequests")
      .doc(serviceRequestId);

    // Periksa apakah service request ada dan milik pengguna
    const serviceRequestSnapshot = await serviceRequestRef.get();
    if (!serviceRequestSnapshot.exists) {
      return res.status(404).json({
        status: "error",
        message: "Service request not found.",
      });
    }

    // Perbarui status penerimaan pekerjaan
    await serviceRequestRef.update({ status });

    res.status(200).json({
      status: "success",
      message: "Status service request berhasil diperbarui.",
    });
  } catch (error) {
    res.status(500).json({
      status: "error",
      message: "Gagal memperbarui status service request.",
    });
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

app.listen(5000, () => {
  console.log("Obeng REST API listening on port 5000");
});

// newest : CRU data user and technicians(new 10/06/23), Mengupload NIK, foto ktp dan foto profil pada register (new), 
// newest : Memperbarui service request , endpoint mengambil histori pesanan (update), endpoint mengupdate status pesanan (update)
// kode 000 > endpoint bisa dihapus tpi perlu diskusi dulu