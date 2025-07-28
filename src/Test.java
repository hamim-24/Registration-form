import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Test extends JFrame {

    JTextField nameField, emailField, phoneField, addressField, experienceField;
    JRadioButton maleRadio, femaleRadio;
    ButtonGroup genderGroup;
    JComboBox<String> dayCombo, monthCombo, yearCombo, poistionCombo, degreeCombo;
    JButton saveButton, resetButton, chooseButton;
    JLabel titleLabel, personalLabel, professionalLabel, filePathLabel, experienceLabel, nameLabel, emailLabel, phoneLabel, addressLabel, dobLabel, genderLabel, positionLabel, degreeLabel, photoLabel;


    String selectedFilePath = "";

    private static final String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    private static final String[] degrees = {
            "Select Degree", "Bachelor of Software Engineering", "Master of Software Engineering",
            "Bachelor of Computer Science", "Master of Computer Science", "PhD in Computer Science"
    };

    private static final String[] positions = {
            "Select Position", "Goalkeeper", "Centre-back", "Full-back", "Wing-back",
            "Defensive Midfielder", "Central Midfielder", "Attacking Midfielder",
            "Winger", "Striker", "Centre Forward"
    };

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+._-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
//            "^" +                             // Start of the string
//                    "[A-Za-z0-9+_.-]+" +              // Username: letters, digits, plus, underscore, dot, dash (at least one)
//                    "@" +                             // At symbol separating username and domain
//                    "(" +                             // Start of domain group
//                    "[A-Za-z0-9.-]+" +            // Domain: letters, digits, dot or dash (like 'gmail' or 'sub.domain')
//                    "\\." +                       // Literal dot before top-level domain
//                    "[A-Za-z]{2,}" +              // Top-level domain: at least two letters (e.g., com, org, net)
//                    ")" +
//                    "$"                               // End of the string

    public Test() {
        initializeFrame();
        createComponemts();
        layoutComponents();
        addEvenListener();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLocationRelativeTo(null);
    }

    private void createComponemts() {
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        experienceField = new JTextField();

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        dayCombo = new JComboBox<>();
        for (int i = 0; i < 31; i++) {
            dayCombo.addItem(String.format("%02d", i + 1));
        }
        monthCombo = new JComboBox<>(months);
        yearCombo = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i > currentYear - 50; i--) {
            yearCombo.addItem(String.valueOf(i));
        }

        degreeCombo = new JComboBox<>(degrees);
        poistionCombo = new JComboBox<>(positions);

        titleLabel = new JLabel("Player Registration Form");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        personalLabel = new JLabel("Personal Information");
        personalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        personalLabel.setForeground(new Color(70, 130, 180));

        professionalLabel = new JLabel("Professional Information");
        professionalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        professionalLabel.setForeground(new Color(70, 130, 180));

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(150, 35));
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150, 35));
        chooseButton = new JButton("Choose");

        filePathLabel = new JLabel("No file selected");
        filePathLabel.setForeground(Color.GRAY);

        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        addressLabel = new JLabel("Address");
        genderLabel = new JLabel("Gender:");
        dobLabel = new JLabel("Date of Birth:");
        degreeLabel = new JLabel("Degree:");
        positionLabel = new JLabel("Position:");
        experienceLabel = new JLabel("Experience:");
        photoLabel = new JLabel("Photo:");
    }

    private void layoutComponents() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20,  20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;


        addSectionHeader(mainPanel, personalLabel, gbc, 1);

        addFormField(mainPanel, nameLabel, nameField, gbc, 2);
        addFormField(mainPanel, phoneLabel, phoneField, gbc, 3);
        addFormField(mainPanel, emailLabel, emailField, gbc, 4);
        addFormField(mainPanel, addressLabel, addressField, gbc, 5);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(genderPanel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(dobLabel, gbc);

        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dobPanel.add(dayCombo);
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(monthCombo);
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(yearCombo);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(dobPanel, gbc);
        gbc.gridwidth = 1;

        addSectionHeader(mainPanel, professionalLabel, gbc, 8);
        addFormField(mainPanel, degreeLabel, degreeCombo, gbc, 9);
        addFormField(mainPanel, positionLabel, poistionCombo, gbc, 10);
        addFormField(mainPanel, experienceLabel, experienceField, gbc, 11);

        gbc.gridx = 0;
        gbc.gridy = 12;
        mainPanel.add(photoLabel, gbc);
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        phonePanel.add(chooseButton);
        phonePanel.add(Box.createHorizontalStrut(5));
        phonePanel.add(filePathLabel);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(phonePanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        chooseButton.setActionCommand("Choose");
        resetButton.setActionCommand("Reset");
        saveButton.setActionCommand("Save");

        chooseButton.addActionListener(new FormActionListener());
        resetButton.addActionListener(new FormActionListener());
        saveButton.addActionListener(new FormActionListener());

        pack();
    }

    private void addSectionHeader(JPanel mainPanel, JLabel label, GridBagConstraints gbc, int row) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 5, 5, 5);
        mainPanel.add(label, gbc);

        //gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
    }

    private void addFormField(JPanel mainPanel, JLabel label, JComponent component, GridBagConstraints gbc, int row) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(component, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
    }

    private void addEvenListener() {
        // Add input validation listeners
        nameField.addFocusListener(new ValidationFocusListener());
        emailField.addFocusListener(new ValidationFocusListener());
        phoneField.addFocusListener(new ValidationFocusListener());
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

    private class  FormActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Choose")) {
                chooseFile();
            } else if (command.equals("Reset")) {
                resetForm();
            } else if (command.equals("Save")) {
                saveRegistration();
            }
        }
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp"
        ));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();
            filePathLabel.setText(selectedFilePath);
            filePathLabel.setForeground(Color.black);
        }
    }

    private void saveRegistration() {

        if (!validateForm()) {
            return;
        }

        try {
            String data = buildRegistrationData();

            FileWriter writer = new FileWriter("registrations.txt", true);
            writer.write(data);
            writer.write("=".repeat(50) + "\n\n");
            writer.close();

            showMessage();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        genderGroup.clearSelection();
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        degreeCombo.setSelectedIndex(0);
        experienceField.setText("");
        poistionCombo.setSelectedIndex(0);
        selectedFilePath = "";
        filePathLabel.setText("No File Selected");
        filePathLabel.setForeground(Color.GRAY);

        nameLabel.requestFocus();
    }

    private void showMessage() {
        String message = String.format(
                "Registration saved successfully!\n\n" +
                        "Name: %s\n" +
                        "Phone: %s\n" +
                        "Email: %s\n" +
                        "Position: %s\n",
                nameField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                poistionCombo.getSelectedItem()
        );

        JOptionPane.showMessageDialog(this, message, "Registration Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private String buildRegistrationData() {

        StringBuilder data = new StringBuilder();

        data.append("Player Registration");
        data.append("Date: ").append(LocalDate.now()).append("\n\n");

        data.append("Personal Information:\n");
        data.append("Name: ").append(nameField.getText().trim()).append("\n");
        data.append("Email: ").append(emailField.getText().trim()).append("\n");
        data.append("Phone: ").append(phoneField.getText().trim()).append("\n");
        data.append("Address: ").append(addressField.getText().trim()).append("\n");
        data.append("Gender: ").append(maleRadio.isSelected() ? "Male" : "Female").append("\n");
        data.append("Date of Birth: ").append(dayCombo.getSelectedItem())
                .append("-").append(monthCombo.getSelectedItem())
                .append("-").append(yearCombo.getSelectedItem()).append("\n");

        data.append("Professional Information:\n");
        data.append("Degree: ").append(degreeCombo.getSelectedItem()).append("\n");
        data.append("Position: ").append(poistionCombo.getSelectedItem()).append("\n");
        data.append("Experience: ").append(experienceField.getText().trim()).append("\n");
        data.append("Photo: ").append(selectedFilePath.isEmpty() ? "Not provided" : selectedFilePath);

        return data.toString();
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().trim().isEmpty()) {
            errors.append("- Name is required\n");
        }

        if (emailField.getText().trim().isEmpty()) {
            errors.append("- Email is required\n");
        } else if (!EMAIL_PATTERN.matcher(emailField.getText().trim()).matches()) {
            errors.append("- Email is not valid\n");
        }

        if (phoneField.getText().trim().isEmpty()) {
            errors.append("- Phone number is required\n");
        } else if (!phoneField.getText().trim().matches("\\d{10,15}")) {
            errors.append("- Phone number should contain 10-15 digits\n");
        }

        if (addressField.getText().trim().isEmpty()) {
            errors.append("- Address is required\n");
        }
        if (experienceField.getText().trim().isEmpty()) {
            errors.append("- Experience is required\n");
        } else {
            try {
                int exp = Integer.parseInt(experienceField.getText().trim());
                if (exp < 0 || exp > 50) {
                    errors.append("- Experience should be between 0 and 50\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Experience should be a valid number\n");
            }
        }

        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            errors.append("- Gender is required\n");
        }

        if (degreeCombo.getSelectedIndex() == 0) {
            errors.append("- Degree is required\n");
        }

        if (poistionCombo.getSelectedIndex() == 0) {
            errors.append("- Poistion is required\n");
        }

        if (errors.length() != 0) {
            JOptionPane.showMessageDialog(this,"Please correct the following errors: \n\n" + errors.toString(), "Registration Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
