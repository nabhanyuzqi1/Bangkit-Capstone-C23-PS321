const express = require('express');
const bodyParser = require('body-parser');
const admin = require('firebase-admin');
const { Storage } = require("@google-cloud/storage");
const formidable = require("formidable");
const UUID = require("uuid-v4");
const multer = require('multer');
const upload = multer({ dest: 'Obeng_images/' });
const axios = require('axios');

const serviceAccount = require('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const app = express();
app.use(bodyParser.json());

const db = admin.firestore();
const serviceRequestsCollection = admin.firestore().collection('service-requests');
const usersCollection = admin.firestore().collection('users'); 
const techniciansCollection = admin.firestore().collection('technicians'); 
const feedbacksCollection = admin.firestore().collection('feedback'); 
const storage = new Storage({
  keyFilename: "serviceAccountKey.json",
});

app.post('/api/users/register', (req, res) => {
  const { nama, email, password, alamat } = req.body;

  admin
    .auth()
    .createUser({
      email,
      password
    })
    .then((userRecord) => {
      const user = {
        id: userRecord.uid,
        email,
        nama,
        alamat,
        role: 'user' 
      };

        // Memanggil endpoint upload-photo menggunakan axios
  axios.post('http://localhost:5000/api/users/upload-photo', user)
  .then((response) => {
    console.log(response.data);
  })
  .catch((error) => {
    console.error('Error uploading profile photo:', error);
  });

      return usersCollection.doc(userRecord.uid).set(user);
    })
    .then(() => {
      res.json({ message: 'User registered successfully' });
    })
    .catch((error) => {
      console.error('Error registering user:', error);
      res.status(500).json({ error: 'Failed to register user' });
    });
});

app.post("/api/technicians/register", (req, res) => {
  const {
    nama,
    email,
    password,
    noHandphone,
    keahlian,
    linkSertifikasi,
    linkPortofolio,
    jenisKeahlian,
  } = req.body;

  admin
    .auth()
    .createUser({
      email,
      password,
    })
    .then((userRecord) => {
      const technician = {
        technicianId: userRecord.uid,
        nama,
        email,
        noHandphone,
        keahlian,
        linkSertifikasi,
        linkPortofolio,
        jenisKeahlian,
        role: "technician",
      };

      return techniciansCollection.doc(userRecord.uid).set(technician);
    })
    .then(() => {
      res.json({ message: "Technician registered successfully" });
    })
    .catch((error) => {
      console.error("Error registering technician:", error);
      res.status(500).json({ error: "Failed to register technician" });
    });
});

// Endpoint untuk mengambil semua data pengguna (users)
app.get('/api/users', (req, res) => {
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
      console.error('Error getting users:', error);
      res.status(500).json({ error: 'Failed to get users' });
    });
});

// Endpoint untuk mengambil semua data teknisi (technicians)
app.get('/api/technicians', (req, res) => {
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
      console.error('Error getting technicians:', error);
      res.status(500).json({ error: 'Failed to get technicians' });
    });
});

//get data teknisi berdasarkan jenis keahlian
app.get('/api/technicians/by-jenis-keahlian', (req, res) => {
  const { jenisKeahlian } = req.query;

  techniciansCollection
    .where('jenisKeahlian', '==', jenisKeahlian)
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
      console.error('Error getting technicians:', error);
      res.status(500).json({ error: 'Failed to get technicians' });
    });
});

//get data pengguna berdasarkan email
app.get('/api/users/by-email/:email', (req, res) => {
  const email = req.params.email;

  usersCollection
    .where('email', '==', email)
    .get()
    .then((snapshot) => {
      if (snapshot.empty) {
        res.status(404).json({ error: 'User not found' });
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
      console.error('Error getting users:', error);
      res.status(500).json({ error: 'Failed to get users' });
    });
});

// Mengirim service-request atau permintaan layanan
app.post('/api/service-requests', upload.single('fotoBarang'), async (req, res) => {
  try {
    const { alamat, detailBarang } = req.body;
    const fotoBarang = req.file;

    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    // URL gambar barang yang diunggah
    let fotoBarangUrl = "";

    if (fotoBarang) {
      let uuid = UUID();
      const downLoadPath = "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

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

    const serviceRequestRef = db.collection('serviceRequests').doc();
    const IdPesanan = serviceRequestRef.id; // Menggunakan ID dokumen sebagai ID pesanan

    const serviceRequestData = {
      alamat,
      fotoBarang: fotoBarangUrl,
      detailBarang,
      IdPesanan, // Menyimpan ID pesanan di data permintaan layanan
      timestamp: new Date() // Menambahkan properti timestamp dengan waktu saat ini
    };

    await serviceRequestRef.set(serviceRequestData); // Menyimpan data permintaan layanan ke Firestore

    res.status(200).json({
      status: 'success',
      orderNumber: IdPesanan // Mengembalikan ID pesanan sebagai nomor pesanan
    });
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to create service request.'
    });
  }
});

// Mengambil semua data permintaan layanan
app.get('/api/service-requests', async (req, res) => {
  try {
    const serviceRequestsRef = db.collection('serviceRequests');
    const serviceRequestsSnapshot = await serviceRequestsRef.get();

    if (serviceRequestsSnapshot.empty) {
      res.status(404).json({
        status: 'error',
        message: 'No service requests found.'
      });
    } else {
      const serviceRequestsData = [];

      serviceRequestsSnapshot.forEach((doc) => {
        const serviceRequestData = doc.data();
        serviceRequestsData.push(serviceRequestData);
      });

      res.status(200).json({
        status: 'success',
        serviceRequestsData
      });
    }
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to retrieve service requests.'
    });
  }
});


// Mengambil data permintaan layanan berdasarkan ID
app.get('/api/service-requests/:idPesanan', async (req, res) => {
  try {
    const { idPesanan } = req.params;

    const serviceRequestRef = db.collection('serviceRequests').doc(idPesanan);
    const serviceRequestDoc = await serviceRequestRef.get();

    if (!serviceRequestDoc.exists) {
      res.status(404).json({
        status: 'error',
        message: 'Service request not found.'
      });
    } else {
      const serviceRequestData = serviceRequestDoc.data();

      res.status(200).json({
        status: 'success',
        serviceRequestData
      });
    }
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to retrieve service request.'
    });
  }
});

// API Penerimaan Pekerjaan
app.patch("/api/service-requests/:requestId/penerimaan", (req, res) => {
  const { requestId } = req.params;
  const { acceptanceStatus } = req.body;

  const serviceRequestRef = db.collection("serviceRequests").doc(requestId);

  serviceRequestRef
    .update({ acceptanceStatus })
    .then(() => {
      res.json({
        status: "success",
        message: "Penerimaan pekerjaan berhasil diperbarui",
      });
    })
    .catch((error) => {
      console.error("Error memperbarui penerimaan pekerjaan:", error);
      res.status(500).json({ error: "Gagal memperbarui penerimaan pekerjaan" });
    });
});

// Update feedback dan rating untuk seorang teknisi
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

// Mengambil feedback data teknisi berdasarkan id teknisi
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


// Mengunggah foto profil
app.post('/api/users/upload-photo', upload.single('fotoProfil'), async (req, res) => {
  try {
    const fotoProfil = req.file;
    const user = req.body;
    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    // URL foto profil yang diunggah
    let fotoProfilUrl = "";

    if (fotoProfil) {
      let uuid = UUID();
      const downLoadPath = "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

      const fotoProfilResponse = await bucket.upload(fotoProfil.path, {
        destination: `fotoProfil/${fotoProfil.originalname}`,
        resumable: true,
        metadata: {
          metadata: {
            firebaseStorageDownloadTokens: uuid,
          },
        },
      });

      // URL foto profil
      fotoProfilUrl =
        downLoadPath +
        encodeURIComponent(fotoProfilResponse[0].name) +
        "?alt=media&token=" +
        uuid;
    }

    // Simpan URL foto profil ke dalam dokumen pengguna
    const userId = req.body.userId;
    const userRef = usersCollection.doc(userId);
    await userRef.update({
      fotoProfil: fotoProfilUrl
    });
        
      res.status(200).json({
        status: 'success',
        message: 'Profile photo uploaded successfully',
        fotoProfilUrl: fotoProfilUrl
      });

    }catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to upload profile photo.',
    });
  }
});

// Endpoint untuk mendapatkan data profil pengguna
app.get('/api/users/profile/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;

    const userDoc = await usersCollection.doc(userId).get();
    const userData = userDoc.data();

    res.status(200).json({
      status: 'success',
      data: userData
    });
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to retrieve user profile.',
    });
  }
});

// Endpoint untuk memperbarui data profil pengguna
app.put('/api/users/profile/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    const updatedData = req.body;

    const userRef = usersCollection.doc(userId);
    await userRef.update(updatedData);

    res.status(200).json({
      status: 'success',
      message: 'User profile updated successfully',
    });
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to update user profile.',
    });
  }
});


// Mengambil foto profil
app.get('/api/users/:userId/foto-profil', async (req, res) => {
  try {
    const { userId } = req.params;

    const userRef = db.collection('users').doc(userId);
    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      res.status(404).json({
        status: 'error',
        message: 'User not found.'
      });
    } else {
      const userData = userDoc.data();
      const fotoProfilUrl = userData.fotoProfil;

      if (fotoProfilUrl) {
        res.status(200).json({
          status: 'success',
          fotoProfilUrl
        });
      } else {
        res.status(404).json({
          status: 'error',
          message: 'Photo profile not found.'
        });
      }
    }
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to retrieve photo profile.'
    });
  }
});

app.listen(5000, () => {
  console.log('Obeng REST API listening on port 5000');
});

// newest : CRU data user (new), Mengambil foto profil (error)
