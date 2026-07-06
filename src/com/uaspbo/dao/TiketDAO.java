package com.uaspbo.dao;

import com.uaspbo.model.Tiket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    public List<Tiket> getAll() throws SQLException {
        List<Tiket> list = new ArrayList<>();
        String sql = "SELECT * FROM tiket ORDER BY id";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Tiket t = new Tiket();
            t.setId(rs.getInt("id"));
            t.setNamaTiket(rs.getString("nama_tiket"));
            t.setHarga(rs.getDouble("harga"));
            t.setStokTiket(rs.getInt("stok_tiket"));
            list.add(t);
        }
        rs.close();
        ps.close();
        return list;
    }

    // Alias sementara supaya kompatibel dengan TiketPanel.java
    // (hapus method ini kalau Gusandra sudah menyamakan nama methodnya)
    public List<Tiket> getAllTiket() throws SQLException {
        return getAll();
    }

    public Tiket getById(int id) throws SQLException {
        String sql = "SELECT * FROM tiket WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Tiket t = null;
        if (rs.next()) {
            t = new Tiket();
            t.setId(rs.getInt("id"));
            t.setNamaTiket(rs.getString("nama_tiket"));
            t.setHarga(rs.getDouble("harga"));
            t.setStokTiket(rs.getInt("stok_tiket"));
        }
        rs.close();
        ps.close();
        return t;
    }

    public void insert(Tiket tiket) throws SQLException {
        String sql = "INSERT INTO tiket (nama_tiket, harga, stok_tiket) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tiket.getNamaTiket());
        ps.setDouble(2, tiket.getHarga());
        ps.setInt(3, tiket.getStokTiket());
        ps.executeUpdate();
        ps.close();
    }

    public void update(Tiket tiket) throws SQLException {
        String sql = "UPDATE tiket SET nama_tiket = ?, harga = ?, stok_tiket = ? WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tiket.getNamaTiket());
        ps.setDouble(2, tiket.getHarga());
        ps.setInt(3, tiket.getStokTiket());
        ps.setInt(4, tiket.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tiket WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}