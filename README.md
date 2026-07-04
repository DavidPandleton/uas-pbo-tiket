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
| Apache NetBeans | IDE |

## Struktur Project

```
uas-pbo-tiket/
├── src/
│   └── com/uaspbo/
│       ├── Main.java                 Entry point + JMenuBar + CardLayout
│       ├── model/
│       │   ├── User.java             Model user (login)
│       │   ├── Tiket.java            Model tiket (CRUD)
│       │   └── Transaksi.java        Model transaksi (pembelian)
│       ├── dao/
│       │   ├── DBConnection.java     Koneksi MySQL (singleton)
│       │   ├── UserDAO.java          Query login (login-auth)
│       │   ├── TiketDAO.java         CRUD tiket (master-tiket)
│       │   └── TransaksiDAO.java     Insert + update stok (transaksi)
│       ├── view/
│       │   ├── LoginPanel.java       Halaman login (login-auth)
│       │   ├── TiketPanel.java       CRUD tiket GUI (master-tiket)
│       │   ├── TransaksiPanel.java   Form pembelian tiket (transaksi)
│       │   └── AboutPanel.java       Profil kelompok (about-us)
│       └── util/
│           └── SessionManager.java   Session user login (login-auth)
├── db/
│   └── dump.sql                      Database dump
├── lib/
│   └── mysql-connector-j.jar         JDBC driver MySQL
├── resources/
│   └── (foto anggota untuk About Us)
├── laporan/
│   └── (laporan PDF)
├── nbproject/
│   └── project.properties, project.xml, build-impl.xml
├── build.xml
└── README.md
```

## Cara Menjalankan

1. **Clone repositori**

```bash
git clone https://github.com/DavidPandleton/uas-pbo-tiket.git
```

2. **Import database**
   - Buka Laragon / XAMPP, start MySQL
   - Jalankan `db/dump.sql` ke MySQL
   - Database `db_uas_pbo` akan terbentuk dengan 3 tabel + data dummy

3. **Buka di NetBeans**
   - File > Open Project > pilih folder `uas-pbo-tiket`
   - NetBeans akan otomatis mendeteksi project

4. **Tambah JDBC driver**
   - Right-click project > Properties > Libraries
   - Klik Add JAR/Folder > pilih `lib/mysql-connector-j.jar`

5. **Run**
   - Klik Run > Run Project (F6)
   - Atau jalankan manual:
     ```bash
     java -cp "build/classes;lib/mysql-connector-j.jar" com.uaspbo.Main
     ```

## Fitur & Pembagian Tugas

| Fitur | Branch | Pengerja | File |
|---|---|---|---|
| Shared (DB, Model, Navigasi) | `main` / `fitur/transaksi` | David | `DBConnection.java`, `User.java`, `Tiket.java`, `Main.java`, `dump.sql` |
| Login & Auth | `fitur/login-auth` | Komang | `UserDAO.java`, `LoginPanel.java`, `SessionManager.java` |
| Master Tiket CRUD | `fitur/master-tiket` | Gusandra | `TiketDAO.java`, `TiketPanel.java` |
| Transaksi | `fitur/transaksi` | David | `TransaksiDAO.java`, `TransaksiPanel.java` |
| About Us + Laporan | `fitur/about-us` | Lidya | `AboutPanel.java`, laporan PDF |

## Branching

| Branch | Isi |
|---|---|
| `main` | Branch utama, hasil merge semua fitur |
| `setup/scaffolding` | Setup awal project |
| `fitur/login-auth` | Login & autentikasi |
| `fitur/master-tiket` | CRUD tiket |
| `fitur/transaksi` | Transaksi pembelian |
| `fitur/about-us` | Profil kelompok + laporan |

## Git Workflow

```bash
# Clone repositori
git clone https://github.com/DavidPandleton/uas-pbo-tiket.git

# Masuk ke branch fitur masing-masing
git checkout fitur/nama-fitur

# Update branch dengan main terbaru
git pull origin main --rebase

# Atau kalo branch udah lama ga diupdate
git checkout fitur/nama-fitur
git rebase main

# Commit perubahan
git add -A
git commit -m "pesan commit"
git push origin fitur/nama-fitur

# Merge ke main (setelah semua fitur selesai)
git checkout main
git pull origin main
git merge fitur/nama-fitur
git push origin main
```

## Catatan

- Gunakan `PreparedStatement` untuk semua query database (cegah SQL Injection).
- Setiap panel menggunakan layout manager (GridLayout, BorderLayout, dll).
- Navigasi antar panel menggunakan CardLayout di Main.java.
- Saat transaksi, stok tiket otomatis berkurang. Jika stok kurang, akan muncul JOptionPane warning.
