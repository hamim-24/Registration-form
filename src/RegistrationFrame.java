import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegistrationFrame extends JFrame {

    private JTextField nameField, emailField, phoneField, addressField, experienceField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JComboBox<String> dayCombo, monthCombo, yearCombo, degreeCombo, positionCombo;
    private JLabel filePathLabel;
    private String selectedFilePath = "";

    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    private static final String[] DEGREES = {
            "Select Degree", "Bachelor of Software Engineering", "Master of Software Engineering",
            "Bachelor of Computer Science", "Master of Computer Science", "PhD in Computer Science"
    };

    private static final String[] POSITIONS = {
            "Select Position", "Goalkeeper", "Centre-back", "Full-back", "Wing-back",
            "Defensive Midfielder", "Central Midfielder", "Attacking Midfielder",
            "Winger", "Striker", "Centre Forward"
    };

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );

    public RegistrationFrame() {
        initializeFrame();
        createComponents();
        layoutComponents();
        addEventListeners();


        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Player Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Center the frame on screen
        setLocationRelativeTo(null);

    }

    private void createComponents() {
        // Text fields
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        experienceField = new JTextField();

        // Gender radio buttons
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        // Date combo boxes
        dayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayCombo.addItem(String.format("%02d", i));
        }

        monthCombo = new JComboBox<>(MONTHS);

        yearCombo = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i >= currentYear - 50; i--) {
            yearCombo.addItem(String.valueOf(i));
        }

        // Other combo boxes
        degreeCombo = new JComboBox<>(DEGREES);
        positionCombo = new JComboBox<>(POSITIONS);

        // File path label
        filePathLabel = new JLabel("No file selected");
        filePathLabel.setForeground(Color.GRAY);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleLabel = new JLabel("Player Registration Form");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);

        // Reset grid width for form fields
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        // Personal Information Section
        addSectionHeader(mainPanel, "Personal Information", gbc, 1);

        addFormField(mainPanel, "Name:", nameField, gbc, 2);
        addFormField(mainPanel, "Email:", emailField, gbc, 3);
        addFormField(mainPanel, "Phone:", phoneField, gbc, 4);
        addFormField(mainPanel, "Address:", addressField, gbc, 5);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Gender:"), gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(maleRadio);
        genderPanel.add(Box.createHorizontalStrut(10));
        genderPanel.add(femaleRadio);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(genderPanel, gbc);
        gbc.gridwidth = 1;

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Date of Birth:"), gbc);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.add(dayCombo);
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(monthCombo);
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(yearCombo);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(datePanel, gbc);
        gbc.gridwidth = 1;

        // Professional Information Section
        addSectionHeader(mainPanel, "Professional Information", gbc, 8);

        addFormField(mainPanel, "Degree:", degreeCombo, gbc, 9);
        addFormField(mainPanel, "Position:", positionCombo, gbc, 10);
        addFormField(mainPanel, "Experience (years):", experienceField, gbc, 11);

        // Photo upload
        gbc.gridx = 0; gbc.gridy = 12;
        mainPanel.add(new JLabel("Photo:"), gbc);

        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton chooseFileButton = new JButton("Choose File");
        photoPanel.add(chooseFileButton);
        photoPanel.add(Box.createHorizontalStrut(10));
        photoPanel.add(filePathLabel);
        gbc.gridx = 1; gbc.gridwidth = 2;
        mainPanel.add(photoPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save Registration");
        JButton resetButton = new JButton("Reset Form");

        saveButton.setPreferredSize(new Dimension(150, 35));
        resetButton.setPreferredSize(new Dimension(150, 35));

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(resetButton);

        gbc.gridx = 0; gbc.gridy = 13; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Store button references for event listeners
        chooseFileButton.setActionCommand("CHOOSE_FILE");
        saveButton.setActionCommand("SAVE");
        resetButton.setActionCommand("RESET");

        chooseFileButton.addActionListener(new FormActionListener());
        saveButton.addActionListener(new FormActionListener());
        resetButton.addActionListener(new FormActionListener());

        pack();
    }

    private void addSectionHeader(JPanel panel, String title, GridBagConstraints gbc, int row) {
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        sectionLabel.setForeground(new Color(70, 130, 180));

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        gbc.insets = new Insets(15, 5, 10, 5);
        panel.add(sectionLabel, gbc);

        // Reset insets for regular fields
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
    }

    private void addFormField(JPanel panel, String labelText, JComponent component,
                              GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }

    private void addEventListeners() {
        // Add input validation listeners
        nameField.addFocusListener(new ValidationFocusListener());
        emailField.addFocusListener(new ValidationFocusListener());
        phoneField.addFocusListener(new ValidationFocusListener());
    }

    private class FormActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "CHOOSE_FILE":
                    chooseFile();
                    break;
                case "SAVE":
                    saveRegistration();
                    break;
                case "RESET":
                    resetForm();
                    break;
            }
        }
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath = selectedFile.getAbsolutePath();
            filePathLabel.setText(selectedFile.getName());
            filePathLabel.setForeground(Color.BLACK);
        }
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        // Validate required fields
        if (nameField.getText().trim().isEmpty()) {
            errors.append("• Name is required\n");
        }

        if (emailField.getText().trim().isEmpty()) {
            errors.append("• Email is required\n");
        } else if (!EMAIL_PATTERN.matcher(emailField.getText().trim()).matches()) {
            errors.append("• Please enter a valid email address\n");
        }

        if (phoneField.getText().trim().isEmpty()) {
            errors.append("• Phone number is required\n");
        } else if (!phoneField.getText().trim().matches("\\d{10,15}")) {
            errors.append("• Phone number should contain 10-15 digits\n");
        }

        if (addressField.getText().trim().isEmpty()) {
            errors.append("• Address is required\n");
        }

        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            errors.append("• Please select gender\n");
        }

        if (degreeCombo.getSelectedIndex() == 0) {
            errors.append("• Please select a degree\n");
        }

        if (positionCombo.getSelectedIndex() == 0) {
            errors.append("• Please select a position\n");
        }

        if (experienceField.getText().trim().isEmpty()) {
            errors.append("• Experience is required\n");
        } else {
            try {
                int exp = Integer.parseInt(experienceField.getText().trim());
                if (exp < 0 || exp > 50) {
                    errors.append("• Experience should be between 0 and 50 years\n");
                }
            } catch (NumberFormatException e) {
                errors.append("• Experience should be a valid number\n");
            }
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Please correct the following errors:\n\n" + errors.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void saveRegistration() {
        if (!validateForm()) {
            return;
        }

        try {
            String data = buildRegistrationData();

            FileWriter writer = new FileWriter("registrations.txt", true);
            writer.write(data);
            writer.write("\n" + "=".repeat(50) + "\n\n");
            writer.close();

            showSuccessMessage();
            resetForm();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving registration: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String buildRegistrationData() {
        StringBuilder data = new StringBuilder();
        data.append("PLAYER REGISTRATION\n");
        data.append("Registration Date: ").append(LocalDate.now()).append("\n\n");

        data.append("PERSONAL INFORMATION:\n");
        data.append("Name: ").append(nameField.getText().trim()).append("\n");
        data.append("Email: ").append(emailField.getText().trim()).append("\n");
        data.append("Phone: ").append(phoneField.getText().trim()).append("\n");
        data.append("Address: ").append(addressField.getText().trim()).append("\n");
        data.append("Gender: ").append(maleRadio.isSelected() ? "Male" : "Female").append("\n");
        data.append("Date of Birth: ").append(dayCombo.getSelectedItem())
                .append(" ").append(monthCombo.getSelectedItem())
                .append(" ").append(yearCombo.getSelectedItem()).append("\n\n");

        data.append("PROFESSIONAL INFORMATION:\n");
        data.append("Degree: ").append(degreeCombo.getSelectedItem()).append("\n");
        data.append("Position: ").append(positionCombo.getSelectedItem()).append("\n");
        data.append("Experience: ").append(experienceField.getText().trim()).append(" years\n");
        data.append("Photo: ").append(selectedFilePath.isEmpty() ? "Not provided" : selectedFilePath).append("\n");

        return data.toString();
    }

    private void showSuccessMessage() {
        String message = String.format(
                "Registration saved successfully!\n\n" +
                        "Player: %s\n" +
                        "Email: %s\n" +
                        "Position: %s\n" +
                        "Experience: %s years",
                nameField.getText().trim(),
                emailField.getText().trim(),
                positionCombo.getSelectedItem(),
                experienceField.getText().trim()
        );

        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        // Clear text fields
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        experienceField.setText("");

        // Reset radio buttons
        genderGroup.clearSelection();

        // Reset combo boxes
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        degreeCombo.setSelectedIndex(0);
        positionCombo.setSelectedIndex(0);

        // Reset file selection
        selectedFilePath = "";
        filePathLabel.setText("No file selected");
        filePathLabel.setForeground(Color.GRAY);

        // Focus on first field
        nameField.requestFocus();
    }

    private class ValidationFocusListener extends java.awt.event.FocusAdapter {
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            JTextField field = (JTextField) e.getSource();

            if (field == emailField && !field.getText().trim().isEmpty()) {
                if (!EMAIL_PATTERN.matcher(field.getText().trim()).matches()) {
                    field.setBackground(new Color(255, 230, 230));
                } else {
                    field.setBackground(Color.WHITE);
                }
            }
        }
    }
        public static void main(String[] args) {

            new RegistrationFrame();
        }

}