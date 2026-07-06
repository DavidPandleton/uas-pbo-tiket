package com.uaspbo.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * AboutPanel - Halaman "About Us".
 * Menampilkan foto, NIM, dan Nama tiap anggota kelompok.
 *
 * Fitur 4 - About Us + Navigasi + Laporan
 */
public class AboutPanel extends JPanel {

    // Data anggota kelompok: {NIM, Nama, nama file foto di folder resources/}
    private static final String[][] ANGGOTA = {
            {"250030085", "I Komang Gede Okantara", "komang.jpg"},
            {"250030077", "Ida Bagus Mastyendra Suja", "mastyendra.jpg"},
            {"250030485", "I Dewa Agung Ayu Lidya Aristawati", "lidya.jpg"},
            {"250030487", "I Gusti Nyoman David Ray Tarigan", "david.jpg"}
    };

    private static final String RESOURCE_FOLDER = "resources/";
    private static final int FOTO_SIZE = 130;

    public AboutPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildMemberGrid(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel title = new JLabel("Sistem Informasi Penjualan Tiket");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Tugas Akhir Pemrograman Berorientasi Objek");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createRigidArea(new Dimension(0, 4)));
        header.add(subtitle);
        return header;
    }

    private JComponent buildMemberGrid() {
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBackground(Color.WHITE);

        for (String[] anggota : ANGGOTA) {
            grid.add(buildMemberCard(anggota[0], anggota[1], anggota[2]));
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(grid);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JComponent buildMemberCard(String nim, String nama, String fileFoto) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.setPreferredSize(new Dimension(320, 200));

        JLabel foto = new JLabel(loadFoto(fileFoto));
        foto.setAlignmentX(Component.CENTER_ALIGNMENT);
        foto.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel namaLabel = new JLabel(nama);
        namaLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        namaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nimLabel = new JLabel("NIM: " + nim);
        nimLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nimLabel.setForeground(Color.DARK_GRAY);
        nimLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(foto);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(namaLabel);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(nimLabel);

        return card;
    }

    /**
     * Load foto anggota dari folder resources/.
     * Kalau file tidak ditemukan, tampilkan placeholder ikon
     * supaya UI tidak error/kosong.
     */
    private ImageIcon loadFoto(String fileName) {
        File file = new File(RESOURCE_FOLDER + fileName);

        if (file.exists()) {
            ImageIcon original = new ImageIcon(file.getPath());
            Image scaled = original.getImage()
                    .getScaledInstance(FOTO_SIZE, FOTO_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }

        return buildPlaceholderIcon();
    }

    /**
     * Placeholder lingkaran abu-abu dengan ikon orang,
     * dipakai kalau foto anggota belum tersedia di folder resources/.
     */
    private ImageIcon buildPlaceholderIcon() {
        BufferedImageWrapper wrapper = new BufferedImageWrapper(FOTO_SIZE, FOTO_SIZE);
        Graphics2D g2 = wrapper.getGraphics2D();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(0, 0, FOTO_SIZE, FOTO_SIZE);

        g2.setColor(Color.WHITE);
        int headSize = FOTO_SIZE / 3;
        g2.fillOval((FOTO_SIZE - headSize) / 2, FOTO_SIZE / 5, headSize, headSize);
        g2.fillOval(FOTO_SIZE / 5, (int) (FOTO_SIZE * 0.55), (int) (FOTO_SIZE * 0.6), (int) (FOTO_SIZE * 0.5));

        g2.dispose();
        return new ImageIcon(wrapper.getImage());
    }

    private JComponent buildFooter() {
        JLabel footer = new JLabel("© 2026 Kelompok UAS PBO - Sistem Informasi Penjualan Tiket", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footer.setForeground(Color.GRAY);
        footer.setBorder(new EmptyBorder(15, 0, 0, 0));
        return footer;
    }

    /**
     * Helper kecil untuk menggambar placeholder icon tanpa perlu
     * import java.awt.image.BufferedImage secara langsung di atas.
     */
    private static class BufferedImageWrapper {
        private final java.awt.image.BufferedImage image;

        BufferedImageWrapper(int width, int height) {
            image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D getGraphics2D() {
            return image.createGraphics();
        }

        java.awt.image.BufferedImage getImage() {
            return image;
        }
    }
}