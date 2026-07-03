# UAS PBO — Sistem Informasi Penjualan Tiket

Tugas akhir mata kuliah Pemrograman Berorientasi Objek.

## Kelompok

| NIM | Nama |
|---|---|
| 250030085 | I Komang Gede Okantara |
| 250030077 | Ida Bagus Mastyendra Suja |
| 250030485 | I Dewa Agung Ayu Lidya Aristawati |
| 250030487 | I Gusti Nyoman David Ray Tarigan |

## Tech Stack

| Teknologi | Kegunaan |
|---|---|
| Java (JDK 8+) | Bahasa pemrograman |
| Swing / AWT | Antarmuka grafis (GUI) |
| MySQL | Database |
| JDBC | Koneksi Java ke MySQL |
| CardLayout | Navigasi antar panel |
| JMenuBar | Menu navigasi utama |

## Struktur Project

```
├── src/
│   ├── main/          Entry point aplikasi
│   ├── model/         POJO class (User, Tiket, Transaksi)
│   ├── dao/           Data Access Object (DBConnection, UserDAO, TiketDAO, TransaksiDAO)
│   ├── view/          Panel GUI (Login, Tiket, Transaksi, About)
│   └── util/          Utility (SessionManager)
├── db/                Database dump (.sql)
├── lib/               JDBC driver (.jar)
├── resources/         Asset (foto, icon)
└── laporan/           Laporan PDF
```

## Cara Menjalankan

1. Buat database `db_uas_pbo` di MySQL
2. Import `db/dump.sql`
3. Buka project di NetBeans / IDE Java
4. Add `lib/mysql-connector-j.jar` ke classpath
5. Run `Main.java`

## Fitur

- **Login** — Validasi username & password dari database
- **Master Tiket** — CRUD data tiket (Create, Read, Update, Delete) dengan field stok_tiket
- **Transaksi** — Pembelian tiket dengan stok otomatis berkurang, notifikasi JOptionPane jika stok tidak mencukupi
- **About Us** — Profil anggota kelompok (NIM, Nama, Foto)
- **Logout** — Kembali ke halaman Login

## Branching

| Branch | Isi |
|---|---|
| `main` | Branch utama, hasil merge semua fitur |
| `setup/scaffolding` | Setup awal project |
| `fitur/login-auth` | Login & autentikasi |
| `fitur/master-tiket` | CRUD tiket |
| `fitur/transaksi` | Transaksi pembelian |
| `fitur/about-us` | Profil kelompok + laporan |

## Laporan

Laporan PDF tersedia di folder `laporan/`.
