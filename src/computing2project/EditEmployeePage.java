/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package computing2project;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 *
 * @author Mikymus Victorius
 */
public class EditEmployeePage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditEmployeePage.class.getName());

    /**
     * Creates new form AddEmployeePage
     */
    
    private String employeeID;
    private AdminDetailPage adminDetailPage;
    
    
    
    public EditEmployeePage(String employeeID, AdminDetailPage adminDetailPage) throws CsvValidationException {
        initComponents();
        this.employeeID = employeeID;
        this.adminDetailPage = adminDetailPage;
        employeeIDField.setEditable(false);
        loadEmployeeDataFromCSV();
        
        // Auto-format patterns
        addAutoDashFormatter(sssNumberField, "##-#######-#");
        addAutoDashFormatter(philhealthNumberField, "####-####-####");
        addAutoDashFormatter(tinField, "###-###-###-###");
        addAutoDashFormatter(pagibigNumberField, "####-####-####");
        addAutoDashFormatter(phoneNumberField, "####-###-####");
    }
    
    public void addAutoDashFormatter(javax.swing.JTextField field, String pattern) {
        field.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    String text = field.getText().replaceAll("[^\\d]", ""); // Remove non-digits
                    StringBuilder formatted = new StringBuilder();
                    int index = 0;

                    for (char ch : pattern.toCharArray()) {
                        if (ch == '#') {
                            if (index < text.length()) {
                                formatted.append(text.charAt(index++));
                            }
                        } else {
                            if (index < text.length()) {
                                formatted.append(ch);
                            }
                        }
                    }

                    field.setText(formatted.toString());
                }
            });
    }
    
    public boolean isBirthdayValid(String birthday) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM dd yyyy");
            sdf.setLenient(false); // Makes sure invalid dates like 02-30-2023 are caught
            sdf.parse(birthday);   // Try parsing
            return true;
        } 
        catch (java.text.ParseException e) {
            return false;
        }
    }
    
    public boolean isPhoneValid(String phone) {
        return phone.matches("^09\\d{2}-\\d{3}-\\d{4}$");
    }
    
    public boolean isSSSNumberValid(String sssNumber) {
        return sssNumber.matches("^\\d{2}-\\d{7}-\\d{1}$");
    }
    
    // PhilHealth: 1234-5678-9012
    public boolean isPhilHealthValid(String philHealth) {
        return philHealth.matches("^\\d{4}-\\d{4}-\\d{4}$");
    }

    // TIN: 123-456-789-000 (common format)
    public boolean isTINValid(String tin) {
        return tin.matches("^\\d{3}-\\d{3}-\\d{3}-\\d{3}$");
    }

    // Pag-IBIG: 1234-5678-9123
    public boolean isPagibigValid(String pagibig) {
        return pagibig.matches("^\\d{4}-\\d{4}-\\d{4}$");
    }
    
    public boolean areFieldsValid() {
        String[] fields = {
            employeeIDField.getText().trim(),
            portalPasswordField.getText().trim(),
            lastNameField.getText().trim(),
            firstNameField.getText().trim(),
            birthdayField.getText().trim(),
            addressTextArea.getText().trim(),
            phoneNumberField.getText().trim(),
            sssNumberField.getText().trim(),
            philhealthNumberField.getText().trim(),
            tinField.getText().trim(),
            pagibigNumberField.getText().trim(),
            statusField.getText().trim(),
            positionField.getText().trim(),
            immediateSupervisorField.getText().trim(),
            basicSalaryField.getText().trim(),
            riceSubsidyField.getText().trim(),
            phoneAllowanceField.getText().trim(),
            clothingAllowanceField.getText().trim(),
            grossSemiMonthlyRateField.getText().trim(),
            hourlyRateField.getText().trim()
        };

        for (String field : fields) {
            if (field.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all fields before saving.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        // Simple format check
        String birthday = birthdayField.getText().trim();
        if (!isBirthdayValid(birthday)) {
            JOptionPane.showMessageDialog(this,
                "Birthday must follow MM-DD-YYYY and be a valid date.",
                "Invalid Birthday Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
    
        // Phone number format validation
        String phoneNumber = phoneNumberField.getText().trim();
        if (!isPhoneValid(phoneNumber)) {
            JOptionPane.showMessageDialog(this,
                "Phone number must follow the format 09XX-XXX-XXXX.",
                "Invalid Phone Number Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // SSS number format validation
        String sssNumberNumber = sssNumberField.getText().trim();
        if (!isSSSNumberValid(sssNumberNumber)) {
            JOptionPane.showMessageDialog(this,
                "SSS number must follow the format ##-#######-# (e.g., 34-1234567-8).",
                "Invalid SSS Number Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // PhilHealth number format validation
        String philhealthNumber = philhealthNumberField.getText().trim();
        if (!isPhilHealthValid(philhealthNumber)) {
            JOptionPane.showMessageDialog(this,
                "PhilHealth number must follow the format ####-####-####.",
                "Invalid PhilHealth Number Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // TIN number format validation
        String tin = tinField.getText().trim();
        if (!isTINValid(tin)) {
            JOptionPane.showMessageDialog(this,
                "TIN number must follow the format ###-###-###-###.",
                "Invalid TIN Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Pag-IBIG number format validation
        String pagibigNumber = pagibigNumberField.getText().trim();
        if (!isPagibigValid(pagibigNumber)) {
            JOptionPane.showMessageDialog(this,
                "Pag-IBIG number must follow the format ####-####-####.",
                "Invalid Pag-IBIG Format",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        

    return true;
    }
    
    private void loadEmployeeDataFromCSV() throws CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader("src/DataFiles/employees.csv"))) {
        String[] rowData;
            while ((rowData = reader.readNext()) != null) {
                if (rowData.length > 0 && rowData[0].equals(employeeID)) {
                    populateFields(rowData);
                    break;
                }
            }
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void populateFields(String[] employeeDetails) {
        employeeIDField.setText(employeeDetails[0]);
        portalPasswordField.setText(employeeDetails[1]);
        lastNameField.setText(employeeDetails[2]);
        firstNameField.setText(employeeDetails[3]);
        birthdayField.setText(employeeDetails[4]);
        addressTextArea.setText(employeeDetails[5]);
        phoneNumberField.setText(employeeDetails[6]);
        sssNumberField.setText(employeeDetails[7]);
        philhealthNumberField.setText(employeeDetails[8]);
        tinField.setText(employeeDetails[9]);
        pagibigNumberField.setText(employeeDetails[10]);
        statusField.setText(employeeDetails[11]);
        positionField.setText(employeeDetails[12]);
        immediateSupervisorField.setText(employeeDetails[13]);
        basicSalaryField.setText(employeeDetails[14]);
        riceSubsidyField.setText(employeeDetails[15]);
        phoneAllowanceField.setText(employeeDetails[16]);
        clothingAllowanceField.setText(employeeDetails[17]);
        grossSemiMonthlyRateField.setText(employeeDetails[18]);
        hourlyRateField.setText(employeeDetails[19]);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EditEmployeeHeader = new javax.swing.JLabel();
        EmployeeIDLabel = new javax.swing.JLabel();
        PortalPasswordLabel = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JLabel();
        FirstNameLabel = new javax.swing.JLabel();
        BirthdayLabel = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JLabel();
        SSSNumberLabel = new javax.swing.JLabel();
        PhilhealthNumberLabel = new javax.swing.JLabel();
        TINLabel = new javax.swing.JLabel();
        PagibigNumberLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        PositionLabel = new javax.swing.JLabel();
        ImmediateSupervisorLabel = new javax.swing.JLabel();
        BasicSalaryLabel = new javax.swing.JLabel();
        RiceSubsidyLabel = new javax.swing.JLabel();
        PhoneAllowanceLabel = new javax.swing.JLabel();
        ClothingAllowanceLabel = new javax.swing.JLabel();
        GrossSemiMonthlyRateLabel = new javax.swing.JLabel();
        HourlyRateLabel = new javax.swing.JLabel();
        employeeIDField = new javax.swing.JTextField();
        portalPasswordField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        firstNameField = new javax.swing.JTextField();
        birthdayField = new javax.swing.JTextField();
        sssNumberField = new javax.swing.JTextField();
        philhealthNumberField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTextArea = new javax.swing.JTextArea();
        tinField = new javax.swing.JTextField();
        pagibigNumberField = new javax.swing.JTextField();
        statusField = new javax.swing.JTextField();
        positionField = new javax.swing.JTextField();
        immediateSupervisorField = new javax.swing.JTextField();
        basicSalaryField = new javax.swing.JTextField();
        riceSubsidyField = new javax.swing.JTextField();
        phoneAllowanceField = new javax.swing.JTextField();
        clothingAllowanceField = new javax.swing.JTextField();
        grossSemiMonthlyRateField = new javax.swing.JTextField();
        hourlyRateField = new javax.swing.JTextField();
        SaveButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        phoneNumberField = new javax.swing.JTextField();
        PhoneNumberLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EditEmployeeHeader.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        EditEmployeeHeader.setForeground(new java.awt.Color(204, 0, 0));
        EditEmployeeHeader.setText("Edit New Employee");

        EmployeeIDLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        EmployeeIDLabel.setForeground(new java.awt.Color(204, 0, 0));
        EmployeeIDLabel.setText("Employee ID:");

        PortalPasswordLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PortalPasswordLabel.setForeground(new java.awt.Color(204, 0, 0));
        PortalPasswordLabel.setText("Portal Password:");

        LastNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        LastNameLabel.setForeground(new java.awt.Color(204, 0, 0));
        LastNameLabel.setText("Last Name:");

        FirstNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        FirstNameLabel.setForeground(new java.awt.Color(204, 0, 0));
        FirstNameLabel.setText("First Name:");

        BirthdayLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        BirthdayLabel.setForeground(new java.awt.Color(204, 0, 0));
        BirthdayLabel.setText("Birthday:");

        AddressLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        AddressLabel.setForeground(new java.awt.Color(204, 0, 0));
        AddressLabel.setText("Address:");

        SSSNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        SSSNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        SSSNumberLabel.setText("SSS Number:");

        PhilhealthNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PhilhealthNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        PhilhealthNumberLabel.setText("Philhealth Number:");

        TINLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        TINLabel.setForeground(new java.awt.Color(204, 0, 0));
        TINLabel.setText("TIN:");

        PagibigNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PagibigNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        PagibigNumberLabel.setText("Pagibig Number:");

        StatusLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        StatusLabel.setForeground(new java.awt.Color(204, 0, 0));
        StatusLabel.setText("Status:");

        PositionLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PositionLabel.setForeground(new java.awt.Color(204, 0, 0));
        PositionLabel.setText("Position:");

        ImmediateSupervisorLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        ImmediateSupervisorLabel.setForeground(new java.awt.Color(204, 0, 0));
        ImmediateSupervisorLabel.setText("Immediate Supervisor:");

        BasicSalaryLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        BasicSalaryLabel.setForeground(new java.awt.Color(204, 0, 0));
        BasicSalaryLabel.setText("Basic Salary:");

        RiceSubsidyLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        RiceSubsidyLabel.setForeground(new java.awt.Color(204, 0, 0));
        RiceSubsidyLabel.setText("Rice Subsidy:");

        PhoneAllowanceLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PhoneAllowanceLabel.setForeground(new java.awt.Color(204, 0, 0));
        PhoneAllowanceLabel.setText("Phone Allowance:");

        ClothingAllowanceLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        ClothingAllowanceLabel.setForeground(new java.awt.Color(204, 0, 0));
        ClothingAllowanceLabel.setText("Clothing Allowance:");

        GrossSemiMonthlyRateLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        GrossSemiMonthlyRateLabel.setForeground(new java.awt.Color(204, 0, 0));
        GrossSemiMonthlyRateLabel.setText("Gross Semi-monthly Rate:");

        HourlyRateLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        HourlyRateLabel.setForeground(new java.awt.Color(204, 0, 0));
        HourlyRateLabel.setText("Hourly Rate:");

        employeeIDField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        employeeIDField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        employeeIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeIDFieldActionPerformed(evt);
            }
        });

        portalPasswordField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        portalPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        portalPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portalPasswordFieldActionPerformed(evt);
            }
        });

        lastNameField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        lastNameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        lastNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameFieldActionPerformed(evt);
            }
        });

        firstNameField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        firstNameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        firstNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameFieldActionPerformed(evt);
            }
        });

        birthdayField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        birthdayField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        birthdayField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthdayFieldActionPerformed(evt);
            }
        });

        sssNumberField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        sssNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        sssNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sssNumberFieldActionPerformed(evt);
            }
        });

        philhealthNumberField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        philhealthNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        philhealthNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                philhealthNumberFieldActionPerformed(evt);
            }
        });

        addressTextArea.setColumns(20);
        addressTextArea.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        addressTextArea.setRows(5);
        addressTextArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        jScrollPane1.setViewportView(addressTextArea);

        tinField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        tinField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        tinField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tinFieldActionPerformed(evt);
            }
        });

        pagibigNumberField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        pagibigNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        pagibigNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagibigNumberFieldActionPerformed(evt);
            }
        });

        statusField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        statusField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        statusField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusFieldActionPerformed(evt);
            }
        });

        positionField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        positionField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        positionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionFieldActionPerformed(evt);
            }
        });

        immediateSupervisorField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        immediateSupervisorField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        immediateSupervisorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                immediateSupervisorFieldActionPerformed(evt);
            }
        });

        basicSalaryField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        basicSalaryField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        basicSalaryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                basicSalaryFieldActionPerformed(evt);
            }
        });

        riceSubsidyField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        riceSubsidyField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        riceSubsidyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riceSubsidyFieldActionPerformed(evt);
            }
        });

        phoneAllowanceField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        phoneAllowanceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        phoneAllowanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneAllowanceFieldActionPerformed(evt);
            }
        });

        clothingAllowanceField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        clothingAllowanceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        clothingAllowanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clothingAllowanceFieldActionPerformed(evt);
            }
        });

        grossSemiMonthlyRateField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        grossSemiMonthlyRateField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        grossSemiMonthlyRateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grossSemiMonthlyRateFieldActionPerformed(evt);
            }
        });

        hourlyRateField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        hourlyRateField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        hourlyRateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourlyRateFieldActionPerformed(evt);
            }
        });

        SaveButton.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 16)); // NOI18N
        SaveButton.setForeground(new java.awt.Color(204, 0, 0));
        SaveButton.setText("Save");
        SaveButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        CancelButton.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 16)); // NOI18N
        CancelButton.setForeground(new java.awt.Color(204, 0, 0));
        CancelButton.setText("Cancel");
        CancelButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        phoneNumberField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        phoneNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        phoneNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberFieldActionPerformed(evt);
            }
        });

        PhoneNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        PhoneNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        PhoneNumberLabel.setText("Phone Number:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(EditEmployeeHeader))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SSSNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PhilhealthNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(TINLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EmployeeIDLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PortalPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LastNameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(FirstNameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BirthdayLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(AddressLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(GrossSemiMonthlyRateLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(HourlyRateLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PhoneNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ClothingAllowanceLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PhoneAllowanceLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(RiceSubsidyLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BasicSalaryLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ImmediateSupervisorLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PositionLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(StatusLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PagibigNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(birthdayField)
                            .addComponent(firstNameField)
                            .addComponent(lastNameField)
                            .addComponent(portalPasswordField)
                            .addComponent(employeeIDField)
                            .addComponent(philhealthNumberField)
                            .addComponent(sssNumberField)
                            .addComponent(jScrollPane1)
                            .addComponent(tinField)
                            .addComponent(pagibigNumberField)
                            .addComponent(statusField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(positionField)
                            .addComponent(immediateSupervisorField)
                            .addComponent(basicSalaryField)
                            .addComponent(riceSubsidyField)
                            .addComponent(phoneAllowanceField)
                            .addComponent(clothingAllowanceField)
                            .addComponent(grossSemiMonthlyRateField)
                            .addComponent(hourlyRateField)
                            .addComponent(phoneNumberField))))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(EditEmployeeHeader)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeIDLabel)
                    .addComponent(employeeIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PortalPasswordLabel)
                    .addComponent(portalPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LastNameLabel)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FirstNameLabel)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BirthdayLabel)
                    .addComponent(birthdayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddressLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneNumberLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SSSNumberLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sssNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(philhealthNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PhilhealthNumberLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TINLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pagibigNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PagibigNumberLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StatusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(positionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PositionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(immediateSupervisorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ImmediateSupervisorLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(basicSalaryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BasicSalaryLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(riceSubsidyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RiceSubsidyLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneAllowanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PhoneAllowanceLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clothingAllowanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClothingAllowanceLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(grossSemiMonthlyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GrossSemiMonthlyRateLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hourlyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HourlyRateLabel))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void employeeIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeIDFieldActionPerformed

    private void portalPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portalPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portalPasswordFieldActionPerformed

    private void lastNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameFieldActionPerformed

    private void firstNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameFieldActionPerformed

    private void birthdayFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthdayFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthdayFieldActionPerformed

    private void sssNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sssNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sssNumberFieldActionPerformed

    private void philhealthNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_philhealthNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_philhealthNumberFieldActionPerformed

    private void tinFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tinFieldActionPerformed

    private void pagibigNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagibigNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagibigNumberFieldActionPerformed

    private void statusFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusFieldActionPerformed

    private void positionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_positionFieldActionPerformed

    private void immediateSupervisorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_immediateSupervisorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_immediateSupervisorFieldActionPerformed

    private void basicSalaryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_basicSalaryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_basicSalaryFieldActionPerformed

    private void riceSubsidyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riceSubsidyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_riceSubsidyFieldActionPerformed

    private void phoneAllowanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneAllowanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneAllowanceFieldActionPerformed

    private void clothingAllowanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clothingAllowanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clothingAllowanceFieldActionPerformed

    private void grossSemiMonthlyRateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grossSemiMonthlyRateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grossSemiMonthlyRateFieldActionPerformed

    private void hourlyRateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourlyRateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourlyRateFieldActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void phoneNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberFieldActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        // TODO add your handling code here:
        if (!areFieldsValid()) return;

        String originalEmployeeId = this.employeeID; // From constructor or field

        String[] updatedEmployeeDetails = {
            originalEmployeeId,
            portalPasswordField.getText().trim(),
            lastNameField.getText().trim(),
            firstNameField.getText().trim(),
            birthdayField.getText().trim(),
            addressTextArea.getText().trim(),
            phoneNumberField.getText().trim(),
            sssNumberField.getText().trim(),
            philhealthNumberField.getText().trim(),
            tinField.getText().trim(),
            pagibigNumberField.getText().trim(),
            statusField.getText().trim(),
            positionField.getText().trim(),
            immediateSupervisorField.getText().trim(),
            basicSalaryField.getText().trim(),
            riceSubsidyField.getText().trim(),
            phoneAllowanceField.getText().trim(),
            clothingAllowanceField.getText().trim(),
            grossSemiMonthlyRateField.getText().trim(),
            hourlyRateField.getText().trim()
            };

            File inputFile = new File("src/DataFiles/employees.csv");
            File tempFile = new File("src/DataFiles/employees_temp.csv");
            boolean updated = false;

            try (
                CSVReader reader = new CSVReader(new FileReader(inputFile));
                CSVWriter writer = new CSVWriter(new FileWriter(tempFile))
            ) {
                String[] line;

                while ((line = reader.readNext()) != null) {
                    if (line.length > 0 && line[0].equals(originalEmployeeId)) {
                        writer.writeNext(updatedEmployeeDetails);  // write updated row
                        updated = true;
                    } else {
                        writer.writeNext(line); // write original row
                    }
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (CsvValidationException ex) {
            System.getLogger(EditEmployeePage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

            // Replace original file with temp
            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    JOptionPane.showMessageDialog(this, "Failed to rename updated file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Employee updated successfully!");

            if (adminDetailPage != null) {
                adminDetailPage.loadEmployeeDataFromCSV("src/DataFiles/employees.csv");
            }

            this.dispose();
    }//GEN-LAST:event_SaveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new EditEmployeePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JLabel BasicSalaryLabel;
    private javax.swing.JLabel BirthdayLabel;
    private javax.swing.JButton CancelButton;
    private javax.swing.JLabel ClothingAllowanceLabel;
    private javax.swing.JLabel EditEmployeeHeader;
    private javax.swing.JLabel EmployeeIDLabel;
    private javax.swing.JLabel FirstNameLabel;
    private javax.swing.JLabel GrossSemiMonthlyRateLabel;
    private javax.swing.JLabel HourlyRateLabel;
    private javax.swing.JLabel ImmediateSupervisorLabel;
    private javax.swing.JLabel LastNameLabel;
    private javax.swing.JLabel PagibigNumberLabel;
    private javax.swing.JLabel PhilhealthNumberLabel;
    private javax.swing.JLabel PhoneAllowanceLabel;
    private javax.swing.JLabel PhoneNumberLabel;
    private javax.swing.JLabel PortalPasswordLabel;
    private javax.swing.JLabel PositionLabel;
    private javax.swing.JLabel RiceSubsidyLabel;
    private javax.swing.JLabel SSSNumberLabel;
    private javax.swing.JButton SaveButton;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel TINLabel;
    private javax.swing.JTextArea addressTextArea;
    private javax.swing.JTextField basicSalaryField;
    private javax.swing.JTextField birthdayField;
    private javax.swing.JTextField clothingAllowanceField;
    private javax.swing.JTextField employeeIDField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JTextField grossSemiMonthlyRateField;
    private javax.swing.JTextField hourlyRateField;
    private javax.swing.JTextField immediateSupervisorField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JTextField pagibigNumberField;
    private javax.swing.JTextField philhealthNumberField;
    private javax.swing.JTextField phoneAllowanceField;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JTextField portalPasswordField;
    private javax.swing.JTextField positionField;
    private javax.swing.JTextField riceSubsidyField;
    private javax.swing.JTextField sssNumberField;
    private javax.swing.JTextField statusField;
    private javax.swing.JTextField tinField;
    // End of variables declaration//GEN-END:variables
}
