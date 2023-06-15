# Pilih base image yang akan digunakan (Node.js versi 16)
FROM node:16

# Set working directory di dalam kontainer
WORKDIR /app

# Salin package.json dan package-lock.json ke direktori kerja
COPY package*.json ./

# Install dependensi
RUN npm install 

# Salin serviceAccountKey.json ke dalam direktori kerja
COPY serviceAccountKey.json .

# Salin seluruh kode aplikasi ke direktori kerja
COPY . .

# Expose port yang akan digunakan oleh aplikasi Node.js
EXPOSE 5000

# Tentukan perintah yang akan dijalankan saat kontainer berjalan
CMD ["node", "index.js"]
