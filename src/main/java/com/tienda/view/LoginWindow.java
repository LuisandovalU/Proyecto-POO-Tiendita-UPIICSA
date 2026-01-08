package com.tienda.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.tienda.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Ventana de inicio de sesión del sistema
 */
public class LoginWindow extends JFrame {
    private UsuarioController usuarioController;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private MainWindow mainWindow;

    public LoginWindow() {
        try {
            // Configurar FlatLaf Dark Mode
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        this.usuarioController = new UsuarioController();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        // Panel principal centrado
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(45, 45, 45));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Título
        JLabel titulo = new JLabel("Sistema de Punto de Venta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Más grande!
        titulo.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(titulo, gbc);

        // Subtítulo
        JLabel subtitulo = new JLabel("Iniciar Sesión");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 24)); // Legibilidad
        subtitulo.setForeground(new Color(200, 200, 200));
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 30, 10);
        panelPrincipal.add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 20)); // ¡Que se vea!
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblUsuario, gbc);

        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtUsuario.setPreferredSize(new Dimension(300, 45)); // Más gordito
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsuario, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblContrasena.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtContrasena.setPreferredSize(new Dimension(300, 45));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtContrasena, gbc);

        // Botón Login
        btnLogin = new JButton("ENTRAR");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Letrotas
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setPreferredSize(new Dimension(250, 60)); // Botón gigante
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        panelPrincipal.add(btnLogin, gbc);

        // Información de usuarios de ejemplo
        JLabel info = new JLabel("<html><center>Para usuarios de 60+:<br>" +
                "Fuentes grandes y botones legibles.</center></html>");
        info.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        info.setForeground(new Color(150, 150, 150));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelPrincipal.add(info, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        // Eventos
        btnLogin.addActionListener(e -> realizarLogin());

        // Enter en campos de texto
        ActionListener loginAction = e -> realizarLogin();
        txtUsuario.addActionListener(loginAction);
        txtContrasena.addActionListener(loginAction);

        // Enter en password field
        txtContrasena.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void configurarVentana() {
        setTitle("Login - Sistema de Punto de Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550); // Un poco más grande para las fuentes
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void realizarLogin() {
        try {
            String usuario = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese usuario y contraseña",
                        "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (usuarioController.autenticar(usuario, contrasena)) {
                // Ocultar ventana de login
                this.setVisible(false);

                // Crear y mostrar ventana principal
                mainWindow = new MainWindow(usuarioController);
                mainWindow.setVisible(true);

                // Cerrar ventana de login cuando se cierre la principal
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos",
                        "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                txtContrasena.setText("");
                txtUsuario.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al iniciar sesión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }
}
