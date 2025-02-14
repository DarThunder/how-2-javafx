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

public class UserHome extends JFrame {
    public UserHome() {
        setTitle("Home - Usuario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel("Bienvenido, Usuario", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton btnViewProfile = new JButton("Ver Perfil");
        JButton btnLogout = new JButton("Cerrar Sesión");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnViewProfile);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnLogout);

        add(panel);
    }
}
