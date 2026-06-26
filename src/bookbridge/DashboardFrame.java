package bookbridge;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DashboardFrame extends JFrame {
    private JLabel lblTotalBooks;

    public DashboardFrame() {
        setTitle("BookBridge - Dashboard");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        add(mainPanel);

        // Welcome Message
        JLabel lblWelcome = new JLabel("Welcome back, Admin!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(new Color(30, 144, 255));
        lblWelcome.setBounds(20, 20, 410, 30);
        mainPanel.add(lblWelcome);

        // Book Counter Panel Metric
        lblTotalBooks = new JLabel("Total Books Available: Loading...");
        lblTotalBooks.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTotalBooks.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalBooks.setBounds(20, 70, 410, 30);
        mainPanel.add(lblTotalBooks);

        // Action Buttons Setup
        JButton btnDonate = createBlueButton("Donate Book", 120);
        JButton btnBrowse = createBlueButton("Browse Books", 170);
        JButton btnExit = createBlueButton("Exit Application", 220);

        mainPanel.add(btnDonate);
        mainPanel.add(btnBrowse);
        mainPanel.add(btnExit);

        // Actions
        btnDonate.addActionListener(e -> openBookManagement());
        btnBrowse.addActionListener(e -> openBookManagement());
        btnExit.addActionListener(e -> System.exit(0));

        updateBookCount();
    }

    private JButton createBlueButton(String text, int yPos) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBounds(100, yPos, 250, 35);
        return btn;
    }

    private void openBookManagement() {
        new BookManagementFrame().setVisible(true);
        this.dispose();
    }

    private void updateBookCount() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM books")) {
            if (rs.next()) {
                lblTotalBooks.setText("Total Books Registered: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            lblTotalBooks.setText("Total Books Registered: Error loading");
        }
    }
}