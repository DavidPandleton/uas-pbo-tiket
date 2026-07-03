package com.uaspbo;

import com.uaspbo.view.TransaksiPanel;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

        mainPanel.add(new TransaksiPanel(this), "transaksi");

        add(mainPanel);
        setupMenuBar();
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        JMenuItem transaksiItem = new JMenuItem("Transaksi");
        JMenuItem logoutItem = new JMenuItem("Logout");

        transaksiItem.addActionListener(e -> cardLayout.show(mainPanel, "transaksi"));
        logoutItem.addActionListener(e -> logout());

        menu.add(transaksiItem);
        menu.addSeparator();
        menu.add(logoutItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    private void logout() {
        dispose();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setVisible(true);
    }
}
