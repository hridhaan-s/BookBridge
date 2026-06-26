package bookbridge;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookManagementFrame extends JFrame {
    private JTextField txtTitle, txtAuthor, txtSubject, txtDonor;
    private JComboBox<String> cbClass, cbCondition;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BookManagementFrame() {
        setTitle("BookBridge - Book Management");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        // ------------------ LEFT PANEL: FORM ------------------
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 500));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(30, 144, 255)), 
                "Donate a Book", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(30, 144, 255)));

        // Fields Setup
        String[] labels = {"Book Title:", "Author:", "Subject:", "Class:", "Condition:", "Donor Name:"};
        int yOffset = 35;

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lbl.setBounds(15, yOffset + (i * 50), 90, 25);
            formPanel.add(lbl);
        }

        txtTitle = new JTextField(); txtTitle.setBounds(110, 35, 170, 25); formPanel.add(txtTitle);
        txtAuthor = new JTextField(); txtAuthor.setBounds(110, 85, 170, 25); formPanel.add(txtAuthor);
        txtSubject = new JTextField(); txtSubject.setBounds(110, 135, 170, 25); formPanel.add(txtSubject);

        cbClass = new JComboBox<>(new String[]{"Class 9", "Class 10", "Class 11", "Class 12"});
        cbClass.setBounds(110, 185, 170, 25); cbClass.setBackground(Color.WHITE); formPanel.add(cbClass);

        cbCondition = new JComboBox<>(new String[]{"New", "Like New", "Good", "Fair"});
        cbCondition.setBounds(110, 235, 170, 25); cbCondition.setBackground(Color.WHITE); formPanel.add(cbCondition);

        txtDonor = new JTextField(); txtDonor.setBounds(110, 285, 170, 25); formPanel.add(txtDonor);

        JButton btnDonate = new JButton("Donate");
        styleButton(btnDonate);
        btnDonate.setBounds(15, 345, 265, 35);
        formPanel.add(btnDonate);

        JButton btnBack = new JButton("Back to Dashboard");
        styleButton(btnBack);
        btnBack.setBackground(Color.GRAY);
        btnBack.setBounds(15, 395, 265, 35);
        formPanel.add(btnBack);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // ------------------ RIGHT PANEL: TABLE & CONTROLS ------------------
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Title", "Author", "Subject", "Class", "Condition", "Donor", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Action Toolbar
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtonPanel.setBackground(Color.WHITE);

        JButton btnRefresh = new JButton("Refresh Table");
        styleButton(btnRefresh);
        
        JButton btnRequest = new JButton("Request Selected Book");
        styleButton(btnRequest);

        actionButtonPanel.add(btnRefresh);
        actionButtonPanel.add(btnRequest);
        rightPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // ------------------ LISTENERS ------------------
        btnDonate.addActionListener(e -> insertBook());
        btnRefresh.addActionListener(e -> loadBookData());
        btnRequest.addActionListener(e -> requestBook());
        btnBack.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            this.dispose();
        });

        loadBookData(); // Pre-load records
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    private void loadBookData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("subject"),
                        rs.getString("class_name"),
                        rs.getString("condition_type"),
                        rs.getString("donor"),
                        rs.getString("status")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load records: " + ex.getMessage());
        }
    }

    private void insertBook() {
        if (txtTitle.getText().isBlank() || txtAuthor.getText().isBlank() || txtDonor.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Please completely fill Title, Author, and Donor Name fields.");
            return;
        }

        String sql = "INSERT INTO books (title, author, subject, class_name, condition_type, donor) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, txtTitle.getText().trim());
            pstmt.setString(2, txtAuthor.getText().trim());
            pstmt.setString(3, txtSubject.getText().trim());
            pstmt.setString(4, cbClass.getSelectedItem().toString());
            pstmt.setString(5, cbCondition.getSelectedItem().toString());
            pstmt.setString(6, txtDonor.getText().trim());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thank you for donating!");
            
            // Clear entry inputs
            txtTitle.setText(""); txtAuthor.setText(""); txtSubject.setText(""); txtDonor.setText("");
            loadBookData();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Insert Failure: " + ex.getMessage());
        }
    }

    private void requestBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book from the table list first.");
            return;
        }

        int bookId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 8);

        if ("Reserved".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This book is already reserved by another student.");
            return;
        }

        String sql = "UPDATE books SET status = 'Reserved' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book successfully reserved!");
            loadBookData();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Status update failed: " + ex.getMessage());
        }
    }
}