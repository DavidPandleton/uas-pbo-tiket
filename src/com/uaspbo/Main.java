package com.uaspbo;

import com.uaspbo.view.AboutPanel;
import com.uaspbo.view.LoginPanel;
import com.uaspbo.view.TiketPanel;
import com.uaspbo.view.TransaksiPanel;
import com.uaspbo.util.SessionManager;
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
    private JMenuBar menuBar;

    public Main() {
        setTitle("Sistem Informasi Penjualan Tiket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel login ditampilkan pertama kali. Setelah login berhasil,
        // callback ini otomatis pindah ke menu utama (Master Tiket).
        mainPanel.add(new LoginPanel(this::onLoginBerhasil), "login");

        mainPanel.add(new TiketPanel(), "tiket");
        mainPanel.add(new TransaksiPanel(this), "transaksi");
        mainPanel.add(new AboutPanel(), "about"); // Fitur 4 - About Us

        add(mainPanel);
        setupMenuBar();

        // Sembunyikan menu bar dulu sampai user berhasil login
        menuBar.setVisible(false);
        cardLayout.show(mainPanel, "login");
    }

    private void onLoginBerhasil() {
        menuBar.setVisible(true);
        cardLayout.show(mainPanel, "tiket");
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem tiketItem = new JMenuItem("Master Tiket");
        JMenuItem transaksiItem = new JMenuItem("Transaksi");
        JMenuItem aboutItem = new JMenuItem("About Us");
        JMenuItem logoutItem = new JMenuItem("Logout");

        tiketItem.setEnabled(true);
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
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            SessionManager.logout();
            menuBar.setVisible(false);
            cardLayout.show(mainPanel, "login");
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setVisible(true);
    }
}