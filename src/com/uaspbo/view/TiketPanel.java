package com.uaspbo.view;

import com.uaspbo.dao.TiketDAO;
import com.uaspbo.model.Tiket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class TiketPanel extends JPanel {

    private final TiketDAO tiketDAO = new TiketDAO();
    private final DecimalFormat formatRupiah = new DecimalFormat("Rp#,###");

    private List<Tiket> daftarTiketSaatIni;

    private JTable tabelTiket;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtStok;

    private JButton btnTambah;
    private JButton btnUpdate;
    private JButton btnHapus;
    private JButton btnBersihkan;

    public TiketPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buatJudul(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        add(buatPanelForm(), BorderLayout.SOUTH);

        muatDataTiket();
    }

    private JLabel buatJudul() {
        JLabel judul = new JLabel("Master Data Tiket", SwingConstants.LEFT);
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));
        return judul;
    }

    private JScrollPane buatPanelTabel() {
        String[] kolom = {"ID", "Nama Tiket", "Harga", "Stok"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel hanya untuk tampilan, edit lewat form
            }
        };
        tabelTiket = new JTable(tableModel);
        tabelTiket.setRowHeight(25);
        tabelTiket.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });

        return new JScrollPane(tabelTiket);
    }

    private JPanel buatPanelForm() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Form Tiket"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtId.setEditable(false); 
        txtNama = new JTextField();
        txtHarga = new JTextField();
        txtStok = new JTextField();

        tambahBaris(panelForm, gbc, 0, "ID Tiket", txtId);
        tambahBaris(panelForm, gbc, 1, "Nama Tiket", txtNama);
        tambahBaris(panelForm, gbc, 2, "Harga (angka saja, tanpa Rp/titik)", txtHarga);
        tambahBaris(panelForm, gbc, 3, "Stok Tiket", txtStok);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan Form");

        btnTambah.addActionListener(e -> tambahTiket());
        btnUpdate.addActionListener(e -> updateTiket());
        btnHapus.addActionListener(e -> hapusTiket());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        panelTombol.add(btnTambah);
        panelTombol.add(btnUpdate);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersihkan);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelForm.add(panelTombol, gbc);

        return panelForm;
    }

    private void tambahBaris(JPanel panel, GridBagConstraints gbc, int baris, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = baris;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void muatDataTiket() {
        try {
            tableModel.setRowCount(0);
            daftarTiketSaatIni = tiketDAO.getAllTiket();
            for (Tiket t : daftarTiketSaatIni) {
                tableModel.addRow(new Object[]{
                        t.getId(),
                        t.getNamaTiket(),
                        formatRupiah.format(t.getHarga()),
                        t.getStokTiket()
                });
            }
        } catch (Exception ex) {
            daftarTiketSaatIni = new java.util.ArrayList<>();
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memuat data tiket dari database.\n"
                            + "Pastikan MySQL sudah aktif dan database sudah benar.\n\n"
                            + "Detail: " + ex.getMessage(),
                    "Kesalahan Koneksi Database",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void isiFormDariTabel() {
        int baris = tabelTiket.getSelectedRow();
        if (baris >= 0 && daftarTiketSaatIni != null && baris < daftarTiketSaatIni.size()) {
            Tiket t = daftarTiketSaatIni.get(baris);
            txtId.setText(String.valueOf(t.getId()));
            txtNama.setText(t.getNamaTiket());
            txtHarga.setText(String.valueOf((long) t.getHarga()));
            txtStok.setText(String.valueOf(t.getStokTiket()));
        }
    }

    private void tambahTiket() {
        if (!validasiForm()) return;

        try {
            Tiket tiket = new Tiket();
            tiket.setNamaTiket(txtNama.getText().trim());
            tiket.setHarga(Double.parseDouble(txtHarga.getText().trim()));
            tiket.setStokTiket(Integer.parseInt(txtStok.getText().trim()));

            if (tiketDAO.tambahTiket(tiket)) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil ditambahkan.");
                muatDataTiket();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan tiket. Cek koneksi database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka (contoh: 250000, tanpa Rp atau titik).", "Input Salah", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan tak terduga: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTiket() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data tiket dari tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiForm()) return;

        try {
            Tiket tiket = new Tiket();
            tiket.setId(Integer.parseInt(txtId.getText().trim()));
            tiket.setNamaTiket(txtNama.getText().trim());
            tiket.setHarga(Double.parseDouble(txtHarga.getText().trim()));
            tiket.setStokTiket(Integer.parseInt(txtStok.getText().trim()));

            if (tiketDAO.updateTiket(tiket)) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil diupdate.");
                muatDataTiket();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate tiket. Cek koneksi database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka (contoh: 250000, tanpa Rp atau titik).", "Input Salah", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan tak terduga: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusTiket() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data tiket dari tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus tiket \"" + txtNama.getText() + "\"?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                if (tiketDAO.hapusTiket(id)) {
                    JOptionPane.showMessageDialog(this, "Tiket berhasil dihapus.");
                    muatDataTiket();
                    bersihkanForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus tiket. Cek koneksi database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan tak terduga: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validasiForm() {
        if (txtNama.getText().trim().isEmpty()
                || txtHarga.getText().trim().isEmpty() || txtStok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.", "Input Belum Lengkap", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        tabelTiket.clearSelection();
    }
}