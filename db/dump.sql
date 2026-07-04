CREATE DATABASE IF NOT EXISTS db_uas_pbo;
USE db_uas_pbo;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tiket (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama_tiket VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL,
    stok_tiket INT NOT NULL
);

CREATE TABLE transaksi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tiket_id INT NOT NULL,
    jumlah_beli INT NOT NULL,
    total_harga DOUBLE NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tiket_id) REFERENCES tiket(id)
);

INSERT INTO user (username, password) VALUES ('admin', 'admin123');

INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES
('Konser Java', 150000, 20),
('Tiket Bioskop', 50000, 50),
('Seminar PBO', 75000, 10);
