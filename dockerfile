# Menggunakan image Python sebagai base
FROM python:3.9-slim

# Mengatur direktori kerja
WORKDIR /app

# Menyalin file dependencies ke direktori kerja
COPY requirements.txt .

# Menginstall dependensi yang dibutuhkan
RUN pip install --no-cache-dir -r requirements.txt

# Menyalin file kode sumber ke direktori kerja
COPY . .

# Menjalankan server dengan menggunakan uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8080"]
