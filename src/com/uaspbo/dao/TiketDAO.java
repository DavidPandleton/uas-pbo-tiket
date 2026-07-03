package com.uaspbo.dao;

import com.uaspbo.model.Tiket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    public boolean tambahTiket(Tiket tiket) {
        String sql = "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tiket.getNamaTiket());
            ps.setDouble(2, tiket.getHarga());
            ps.setInt(3, tiket.getStokTiket());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menambah tiket: " + e.getMessage());
            return false;
        }
    }

    public List<Tiket> getAllTiket() {
        List<Tiket> daftarTiket = new ArrayList<>();
        String sql = "SELECT * FROM tiket ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tiket tiket = new Tiket(
                        rs.getInt("id"),
                        rs.getString("nama_tiket"),
                        rs.getDouble("harga"),
                        rs.getInt("stok_tiket")
                );
                daftarTiket.add(tiket);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data tiket: " + e.getMessage());
        }
        return daftarTiket;
    }

    public Tiket getTiketById(int id) {
        String sql = "SELECT * FROM tiket WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Tiket(
                            rs.getInt("id"),
                            rs.getString("nama_tiket"),
                            rs.getDouble("harga"),
                            rs.getInt("stok_tiket")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil tiket: " + e.getMessage());
        }
        return null;
    }

    public boolean updateTiket(Tiket tiket) {
        String sql = "UPDATE tiket SET nama_tiket=?, harga=?, stok_tiket=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tiket.getNamaTiket());
            ps.setDouble(2, tiket.getHarga());
            ps.setInt(3, tiket.getStokTiket());
            ps.setInt(4, tiket.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal mengupdate tiket: " + e.getMessage());
            return false;
        }
    }

    public boolean kurangiStok(int id, int jumlah) {
        String sql = "UPDATE tiket SET stok_tiket = stok_tiket - ? WHERE id = ? AND stok_tiket >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jumlah);
            ps.setInt(2, id);
            ps.setInt(3, jumlah);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal mengurangi stok: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusTiket(int id) {
        String sql = "DELETE FROM tiket WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus tiket: " + e.getMessage());
            return false;
        }
    }
}