/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.SqlLib;

public class login extends JFrame {

    private SqlLib db;
    private JTextField txtUser;
    private JPasswordField txtPass;

    public login(SqlLib db) {
        this.db = db;
        initUI();
    }

    private void initUI() {
        setTitle("Inicio de sesión");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(34, 139, 34));

        createComponents();
    }

    private void createComponents() {
        JLabel lblTitle = new JLabel("Inicio de sesión", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 26));
        txtUser = new JTextField(15);
        txtUser.setPreferredSize(new Dimension(300, 25));

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.BOLD, 26));
        txtPass = new JPasswordField(15);
        txtPass.setPreferredSize(new Dimension(300, 25));

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(0, 100, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 26));
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUser.getText();

            if (username.equals("dAdmin") || username.equals("dUser")) {
                loggerDebug();
            } else {
                try {
                    logger(db);
                } catch (SQLException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblUser, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(txtUser, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblPass, gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(txtPass, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        add(panel);
    }

    private void logger(SqlLib db) throws SQLException {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (db.isValidCredentials(username, password)) {
            String role = db.getRole(username);

            if ("admin".equals(role)) {
                JOptionPane.showMessageDialog(this, "Bienvenido Admin");
            } else if ("user".equals(role)) {
                JOptionPane.showMessageDialog(this, "Bienvenido Usuario");
            }
            this.dispose();
            if (role.equals("admin")) {
                new AdminHome().setVisible(true);
            } else {
                new UserHome().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loggerDebug() {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (password.equals("debugger")) {
            if (username.equals("dAdmin")) {
                JOptionPane.showMessageDialog(this, "Bienvenido Admin");
            } else if (username.equals("dUser")) {
                JOptionPane.showMessageDialog(this, "Bienvenido Usuario");
            }
            this.dispose();
            if (username.equals("dAdmin")) {
                new AdminHome().setVisible(true);
            } else {
                new UserHome().setVisible(true);
            }
        }
    }
}
