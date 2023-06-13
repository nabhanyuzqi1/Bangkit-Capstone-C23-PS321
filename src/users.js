const express = require("express");
const bodyParser = require("body-parser");
const admin = require("firebase-admin");
const { Storage } = require("@google-cloud/storage");
const UUID = require("uuid-v4");
const multer = require("multer");
const upload = multer({ dest: "Obeng_images/" });

const app = express();
app.use(bodyParser.json());
const usersCollection = admin.firestore().collection("users");
const storage = new Storage({
  keyFilename: "serviceAccountKey.json",
});

const usersRouter = express.Router();

//// ----------------------Endpoint untuk mengunggah data profil user (users)--------------------
usersRouter.post(
  "/data",
  upload.fields([
    { name: "fotoKTP", maxCount: 1 },
    { name: "fotoProfil", maxCount: 1 },
  ]),
  async (req, res) => {
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

      // Cek apakah email atau NIK sudah ada dalam database
      const existingUserEmailSnapshot = await usersCollection
        .where("email", "==", email)
        .get();

      const existingUserNIKSnapshot = await usersCollection
        .where("NIK", "==", NIK)
        .get();

      if (!existingUserEmailSnapshot.empty || !existingUserNIKSnapshot.empty) {
        return res.status(400).json({
          status: "error",
          message: "Username, email, or NIK already exists",
        });
      }

      // Simpan data ke dalam database
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
        message: "Register User berhasil!",
      });
    } catch (error) {
      res.status(500).json({
        status: "error",
        message: error.message,
      });
    }
  }
);

///---------------- Endpoint untuk mengambil semua data pengguna (users)----------------------
usersRouter.get("/data", (req, res) => {
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

/// ----------------------Endpoint untuk mengubah data profil user (users)---------------------
usersRouter.put(
  "/data/:userId",
  upload.fields([
    { name: "fotoKTP", maxCount: 1 },
    { name: "fotoProfil", maxCount: 1 },
  ]),
  async (req, res) => {
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
      // Cek apakah email atau NIK sudah ada dalam database
      const existingUserEmailSnapshot = await usersCollection
        .where("email", "==", email)
        .get();

      const existingUserNIKSnapshot = await usersCollection
        .where("NIK", "==", NIK)
        .get();

      if (!existingUserEmailSnapshot.empty || !existingUserNIKSnapshot.empty) {
        return res.status(400).json({
          status: "error",
          message: "Username, email, or NIK already exists",
        });
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

      // Perbarui data pengguna di dalam database
      await usersCollection.doc(userId).update(user);

      res.status(200).json({
        status: "success",
        userId: userId,
        message: "Data user berhasil diubah!",
      });
    } catch (error) {
      res.status(500).json({
        status: "error",
        message: "Gagal mengubah data user.",
      });
    }
  }
);

module.exports = usersRouter;
