const express = require('express');
const bodyParser = require('body-parser');
const admin = require('firebase-admin');
const { Storage } = require("@google-cloud/storage");
const formidable = require("formidable");
const UUID = require("uuid-v4");
const multer = require('multer');
const upload = multer({ dest: 'Obeng_images/' });

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

app.post('/api/technicians/register', (req, res) => {
  const { nama, email, password, noHandphone, keahlian, linkSertifikasi, linkPortofolio, jenisKeahlian } = req.body;

  admin
    .auth()
    .createUser({
      email,
      password
    })
    .then((userRecord) => {
      const technician = {
        id: userRecord.uid,
        nama, 
        email,
        noHandphone,
        keahlian,
        linkSertifikasi,
        linkPortofolio,
        jenisKeahlian,
        role: 'technician' 
      };

      return techniciansCollection.doc(userRecord.uid).set(technician);
    })
    .then(() => {
      res.json({ message: 'Technician registered successfully' });
    })
    .catch((error) => {
      console.error('Error registering technician:', error);
      res.status(500).json({ error: 'Failed to register technician' });
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

// Mengirim permintaan layanan
app.post('/api/service-requests', upload.single('fotoBarang'), async (req, res) => {
  try {
    const { alamat, jenisKendaraan, detailBarang } = req.body;
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
      jenisKendaraan,
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


// Upload feedback
app.post('/api/feedback', async (req, res) => {
  try {
    const FeedbackRef = db.collection('serviceRequests').doc();
    const IdFeedback = FeedbackRef.id; // Menggunakan ID dokumen sebagai ID feedback
    const { pesan, rating } = req.body; // Menambahkan rating dari body permintaan

    const feedback = {
      IdFeedback,
      pesan,
      rating,
      timestamp: admin.firestore.FieldValue.serverTimestamp()
    };

    const docRef = await feedbacksCollection.add(feedback);

    res.status(200).json({
      status: 'success',
      feedbackId: docRef.id
    });
  } catch (error) {
    res.status(500).json({
      status: 'error',
      message: 'Failed to upload feedback.'
    });
  }
});


// Mendapatkan semua feedback
app.get('/api/feedback', (req, res) => {
  feedbacksCollection
    .orderBy('timestamp', 'desc')
    .get()
    .then((snapshot) => {
      const feedbacks = [];
      snapshot.forEach((doc) => {
        const feedback = doc.data();
        feedback.idFeedback = doc.id; // Menambahkan ID feedback ke dalam objek feedback
        feedbacks.push(feedback);
      });
      res.json(feedbacks);
    })
    .catch((error) => {
      console.error('Error getting feedbacks:', error);
      res.status(500).json({ error: 'Failed to get feedbacks' });
    });
});


app.listen(5000, () => {
  console.log('Server berjalan pada port 5000');
});
