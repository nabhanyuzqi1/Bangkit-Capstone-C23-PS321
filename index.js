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

const serviceRequestsCollection = admin.firestore().collection('service-requests');
const usersCollection = admin.firestore().collection('users'); 
const techniciansCollection = admin.firestore().collection('technicians'); 
const feedbacksCollection = admin.firestore().collection('feedbacks'); 
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

// Upload gambar dan feedback
app.post('/api/users/uploads', upload.single('image'), async (req, res) => {
  try {
    let uuid = UUID();
    const downLoadPath = "https://firebasestorage.googleapis.com/v0/b/loginsignup-auth-dc6a9.appspot.com/o/";

    const image = req.file;

    // URL gambar yang diunggah
    let imageUrl;

    const bucket = storage.bucket("gs://loginsignup-auth-dc6a9.appspot.com");

    if (!image) {
      // Tidak ada gambar yang diunggah, lanjutkan tanpa mengunggah
    } else {
      const imageResponse = await bucket.upload(image.path, {
        destination: `image/${image.originalname}`,
        resumable: true,
        metadata: {
          metadata: {
            firebaseStorageDownloadTokens: uuid,
          },
        },
      });

      // URL gambar
      imageUrl =
        downLoadPath +
        encodeURIComponent(imageResponse[0].name) +
        "?alt=media&token=" +
        uuid;
    }

    // Menyimpan feedback ke Firestore
    const feedback = {
      image: image ? imageUrl : "",
      message: req.body.message,
    };

    await feedbacksCollection.add(feedback);

    res.status(200).json({
      message: 'Gambar dan feedback berhasil diunggah',
      data: feedback,
    });
  } catch (error) {
    res.status(500).json({
      message: 'Terjadi kesalahan',
      error: error.message,
    });
  }
});

// Mendapatkan semua feedback
app.get('/api/users/feedbacks', async (req, res) => {
  try {
    const snapshot = await feedbacksCollection.get();

    const feedbacks = [];
    snapshot.forEach((doc) => {
      feedbacks.push({
        id: doc.id,
        ...doc.data(),
      });
    });

    res.status(200).json(feedbacks);
  } catch (error) {
    res.status(500).json({
      message: 'Gagal mendapatkan feedback',
      error: error.message,
    });
  }
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
app.post('/api/service-requests', async (req, res) => {
  try {
    const serviceRequestData = req.body;

    // Membuat dokumen baru di koleksi "service-requests"
    const serviceRequestRef = await serviceRequestsCollection().add(serviceRequestData);


    // Mengembalikan response dengan status keberhasilan dan ID permintaan layanan baru
    res.status(200).json({
      success: true,
      requestId: serviceRequestRef.id
    });
  } catch (error) {
    // Mengembalikan response dengan status kegagalan dan pesan error
    res.status(500).json({
      success: false,
      message: 'Failed to create service request',
      error: error.message
    });
  }
});

// Mengambil data permintaan layanan berdasarkan ID
app.get('/api/service-requests/:requestId', async (req, res) => {
  try {
    const requestId = req.params.requestId;

    // Mengambil dokumen permintaan layanan dari Firestore berdasarkan ID
    const serviceRequestDoc = await serviceRequestsCollection.doc(requestId).get();

    // Memeriksa apakah dokumen ditemukan
    if (serviceRequestDoc.exists) {
      // Mendapatkan data permintaan layanan dari dokumen
      const serviceRequestData = serviceRequestDoc.data();

      // Mengembalikan response dengan data permintaan layanan yang diambil
      res.status(200).json(serviceRequestData);
    } else {
      // Mengembalikan response dengan status 404 jika permintaan layanan tidak ditemukan
      res.status(404).json({
        success: false,
        message: 'Service request not found'
      });
    }
  } catch (error) {
    // Mengembalikan response dengan status kegagalan dan pesan error
    res.status(500).json({
      success: false,
      message: 'Failed to retrieve service request',
      error: error.message
    });
  }
});

app.listen(5000, () => {
  console.log('Server berjalan pada port 5000');
});