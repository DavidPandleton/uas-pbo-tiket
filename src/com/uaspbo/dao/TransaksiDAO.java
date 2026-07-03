package com.uaspbo.dao;

import com.uaspbo.model.Transaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public int insert(Transaksi transaksi) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            String cekStok = "SELECT stok_tiket FROM tiket WHERE id = ? FOR UPDATE";
            PreparedStatement psCek = conn.prepareStatement(cekStok);
            psCek.setInt(1, transaksi.getTiketId());
            ResultSet rs = psCek.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Tiket tidak ditemukan");
            }

            int stok = rs.getInt("stok_tiket");
            if (transaksi.getJumlahBeli() > stok) {
                throw new SQLException("Stok tidak mencukupi. Sisa stok: " + stok);
            }

            rs.close();
            psCek.close();

            String sql = "INSERT INTO transaksi (tiket_id, jumlah_beli, total_harga) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, transaksi.getTiketId());
            ps.setInt(2, transaksi.getJumlahBeli());
            ps.setDouble(3, transaksi.getTotalHarga());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int transaksiId = 0;
            if (generatedKeys.next()) {
                transaksiId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
            ps.close();

            String updateStok = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id = ?";
            PreparedStatement psUpdate = conn.prepareStatement(updateStok);
            psUpdate.setInt(1, transaksi.getJumlahBeli());
            psUpdate.setInt(2, transaksi.getTiketId());
            psUpdate.executeUpdate();
            psUpdate.close();

            conn.commit();
            return transaksiId;

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public List<Transaksi> getAll() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.id, t.tiket_id, tk.nama_tiket, t.jumlah_beli, t.total_harga, t.tanggal "
                + "FROM transaksi t JOIN tiket tk ON t.tiket_id = tk.id ORDER BY t.tanggal DESC";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Transaksi trx = new Transaksi();
            trx.setId(rs.getInt("id"));
            trx.setTiketId(rs.getInt("tiket_id"));
            trx.setNamaTiket(rs.getString("nama_tiket"));
            trx.setJumlahBeli(rs.getInt("jumlah_beli"));
            trx.setTotalHarga(rs.getDouble("total_harga"));
            trx.setTanggal(rs.getTimestamp("tanggal"));
            list.add(trx);
        }
        rs.close();
        ps.close();
        return list;
    }
}
