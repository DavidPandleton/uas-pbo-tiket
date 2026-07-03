package com.uaspbo.view;

import com.uaspbo.Main;
import com.uaspbo.dao.TiketDAO;
import com.uaspbo.dao.TransaksiDAO;
import com.uaspbo.model.Tiket;
import com.uaspbo.model.Transaksi;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TransaksiPanel extends JPanel {

    private Main mainFrame;
    private TiketDAO tiketDAO;
    private TransaksiDAO transaksiDAO;
    private JComboBox<Tiket> comboBox;
    private JLabel labelStok;
    private JTextField fieldJumlah;
    private JButton btnBeli;
    private JTable tableRiwayat;
    private DefaultTableModel tableModel;

    public TransaksiPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        tiketDAO = new TiketDAO();
        transaksiDAO = new TransaksiDAO();

        setLayout(new GridLayout(3, 1));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        comboBox = new JComboBox<>();
        labelStok = new JLabel("Stok: -");
        fieldJumlah = new JTextField();
        btnBeli = new JButton("Beli");

        formPanel.add(new JLabel("Pilih Tiket:"));
        formPanel.add(comboBox);
        formPanel.add(new JLabel("Stok Tersedia:"));
        formPanel.add(labelStok);
        formPanel.add(new JLabel("Jumlah Beli:"));
        formPanel.add(fieldJumlah);
        formPanel.add(new JLabel(""));
        formPanel.add(btnBeli);

        comboBox.addActionListener(e -> updateStokLabel());

        btnBeli.addActionListener(e -> prosesBeli());

        String[] kolom = {"ID", "Tiket", "Jumlah", "Total Harga", "Tanggal"};
        tableModel = new DefaultTableModel(kolom, 0);
        tableRiwayat = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableRiwayat);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());
        btnPanel.add(btnRefresh);

        add(formPanel);
        add(scrollPane);
        add(btnPanel);

        loadData();
    }

    private void updateStokLabel() {
        Tiket selected = (Tiket) comboBox.getSelectedItem();
        if (selected != null) {
            labelStok.setText("Stok: " + selected.getStokTiket());
        } else {
            labelStok.setText("Stok: -");
        }
    }

    private void prosesBeli() {
        Tiket selected = (Tiket) comboBox.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Pilih tiket terlebih dahulu");
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(fieldJumlah.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid");
            return;
        }

        if (jumlah <= 0) {
            JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0");
            return;
        }

        if (jumlah > selected.getStokTiket()) {
            JOptionPane.showMessageDialog(this,
                    "Stok tidak mencukupi!\nSisa stok: " + selected.getStokTiket()
                    + "\nJumlah dibeli: " + jumlah,
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalHarga = selected.getHarga() * jumlah;
        Transaksi transaksi = new Transaksi(selected.getId(), jumlah, totalHarga);

        try {
            transaksiDAO.insert(transaksi);
            JOptionPane.showMessageDialog(this, "Transaksi berhasil!");
            fieldJumlah.setText("");
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try {
            List<Tiket> listTiket = tiketDAO.getAll();
            comboBox.setModel(new DefaultComboBoxModel<>(listTiket.toArray(new Tiket[0])));
            updateStokLabel();

            tableModel.setRowCount(0);
            List<Transaksi> listTrx = transaksiDAO.getAll();
            for (Transaksi trx : listTrx) {
                tableModel.addRow(new Object[]{
                    trx.getId(),
                    trx.getNamaTiket(),
                    trx.getJumlahBeli(),
                    "Rp" + (int) trx.getTotalHarga(),
                    trx.getTanggal()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
