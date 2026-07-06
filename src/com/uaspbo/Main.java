package com.uaspbo;

import com.uaspbo.view.AboutPanel;
import com.uaspbo.view.TiketPanel;
import com.uaspbo.view.TransaksiPanel;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        setTitle("Sistem Informasi Penjualan Tiket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // TEMPORARY: TiketPanel dimatikan dulu, ada bug SQLException belum
        // ditangani di TiketPanel.muatDataTiket() (punya Gusandra).
        // Aktifkan lagi baris di bawah ini kalau sudah diperbaiki:
        // mainPanel.add(new TiketPanel(), "tiket");

        mainPanel.add(new TransaksiPanel(this), "transaksi");
        mainPanel.add(new AboutPanel(), "about"); // Fitur 4 - About Us

        add(mainPanel);
        setupMenuBar();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem tiketItem = new JMenuItem("Master Tiket");
        JMenuItem transaksiItem = new JMenuItem("Transaksi");
        JMenuItem aboutItem = new JMenuItem("About Us");
        JMenuItem logoutItem = new JMenuItem("Logout");

        // TEMPORARY: nonaktifkan menu Master Tiket selama TiketPanel dimatikan
        tiketItem.setEnabled(false);

        tiketItem.addActionListener(e -> cardLayout.show(mainPanel, "tiket"));
        transaksiItem.addActionListener(e -> cardLayout.show(mainPanel, "transaksi"));
        aboutItem.addActionListener(e -> cardLayout.show(mainPanel, "about"));
        logoutItem.addActionListener(e -> logout());

        menu.add(tiketItem);
        menu.add(transaksiItem);
        menu.add(aboutItem);
        menu.addSeparator();
        menu.add(logoutItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    private void logout() {
        // CATATAN: LoginPanel dari fitur/login-auth (Komang) belum ter-merge
        // ke branch ini, jadi logout untuk sementara hanya menutup aplikasi
        // dengan konfirmasi. Setelah LoginPanel & SessionManager sudah ada,
        // ganti dispose() dengan: SessionManager.logout(); cardLayout.show(mainPanel, "login");
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin logout dan keluar aplikasi?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (pilihan == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setVisible(true);
    }
}