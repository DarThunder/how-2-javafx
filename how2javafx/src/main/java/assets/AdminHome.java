/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assets;

/**
 *
 * @author dard
 */
import javax.swing.*;
import java.awt.*;

public class AdminHome extends JFrame {
    public AdminHome() {
        setTitle("Home - Administrador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230, 230, 230));

        JLabel lblTitle = new JLabel("Bienvenido, Administrador", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton btnManageUsers = new JButton("Gestionar Usuarios");
        JButton btnViewReports = new JButton("Ver Reportes");
        JButton btnLogout = new JButton("Cerrar Sesión");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnManageUsers);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnViewReports);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnLogout);

        add(panel);
    }
}

