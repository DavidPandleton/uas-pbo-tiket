package com.uaspbo.view;

import com.uaspbo.Main;
import com.uaspbo.dao.UserDAO;
import com.uaspbo.model.User;
import com.uaspbo.util.SessionManager;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {

    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JButton btnLogin;
    private UserDAO userDAO;

    public LoginPanel() {
        userDAO = new UserDAO();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel judul = new JLabel("Login Sistem Informasi Tiket", SwingConstants.CENTER);
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(judul, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);

        fieldUsername = new JTextField(15);
        gbc.gridx = 1;
        add(fieldUsername, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);

        fieldPassword = new JPasswordField(15);
        gbc.gridx = 1;
        add(fieldPassword, gbc);

        btnLogin = new JButton("Login");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = fieldUsername.getText().trim();
        String password = new String(fieldPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password harus diisi",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = userDAO.login(username, password);

            if (user != null) {
                SessionManager.setUser(user);
                JOptionPane.showMessageDialog(this,
                        "Selamat datang, " + user.getUsername() + "!");
                Main mainFrame = new Main();
                mainFrame.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username atau password salah",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Koneksi database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
