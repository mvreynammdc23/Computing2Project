/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package computing2project;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mikymus Victorius
 */
public class EmployeeDetailPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmployeeDetailPage.class.getName());

    /**
     * Creates new form EmployeeDetailPage
     */
    private String employeeNumber;
    private String lastName;
    private String firstName;
    
    public EmployeeDetailPage(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        initComponents();
        loadEmployeeDetails();
        populateMonthComboBox();
        loadAttendanceToTable(employeeNumber); // loads attendance
        
        monthComboBox.addActionListener(e -> {
            if (this.employeeNumber != null && !this.employeeNumber.isEmpty()) {
                loadAttendanceToTable(this.employeeNumber);
            }
        });
        
    }
    
    private void loadEmployeeDetails() {
        String csvPath = "src/DataFiles/employees.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] row;

            while ((row = reader.readNext()) != null) {
                if (row.length >= 19 && row[0].equals(employeeNumber)) {
                    
                    lastName = row[2];
                    firstName = row[3];
                    
                    LastNameValue.setText(lastName);
                    FirstNameValue.setText(firstName);
                    BirthdayValue.setText(row[4]);
                    //AddressValue
                    PhoneValue.setText(row[6]);
                    sssNumberValue.setText(row[7]);
                    philHealthNumberValue.setText(row[8]);
                    tinNumberValue.setText(row[9]);
                    pagibigNumberValue.setText(row[10]);
                    StatusValue.setText(row[11]);
                    PositionValue.setText(row[12]);
                    immediateSupervisorValue.setText(row[13]);
                                        
                    FirstNameHeaderLabel.setText(row[2]); // Header label
                    break;
                }
            }
        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee details.");
            e.printStackTrace();
        }
    }
    
    private void populateMonthComboBox() {
        Set<String> uniqueMonths = new LinkedHashSet<>(); // Keeps order of first appearance
        DateTimeFormatter csvDateFormat = DateTimeFormatter.ofPattern("MM dd yyyy");
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMMM yyyy"); // e.g., January 2024

        try (CSVReader reader = new CSVReader(new FileReader("src/DataFiles/attendance.csv"))) {
            String[] row;
            reader.readNext(); // Skip header if present

            while ((row = reader.readNext()) != null) {
                if (row.length >= 4) {
                    String dateStr = row[3].trim(); // Date is at column index 3

                    try {
                        LocalDate date = LocalDate.parse(dateStr, csvDateFormat);
                        String formattedMonthYear = date.format(displayFormat);
                        uniqueMonths.add(formattedMonthYear);
                    } catch (DateTimeParseException e) {
                        System.err.println("Skipping invalid date: " + dateStr);
                    }
                }
            }

            // Populate combo box
            monthComboBox.removeAllItems(); // Clear previous items
            for (String monthYear : uniqueMonths) {
                monthComboBox.addItem(monthYear);
            }

        } catch (IOException | CsvValidationException ex) {
            JOptionPane.showMessageDialog(this, "Error loading months from attendance file.");
            ex.printStackTrace();
        }
    }
    
    private void loadAttendanceToTable(String employeeNumber) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Employee Number", "Date", "Time In", "Time Out"}, 0);
        String selectedMonthYear = (String) monthComboBox.getSelectedItem();

        if (selectedMonthYear == null || selectedMonthYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a month.");
            return;
        }

        // Parse "January 2024" into YearMonth
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("MMMM yyyy");
        DateTimeFormatter csvDateFormat = DateTimeFormatter.ofPattern("MM dd yyyy");

        try {
            YearMonth selectedYearMonth = YearMonth.parse(selectedMonthYear, displayFormat);

            try (CSVReader reader = new CSVReader(new FileReader("src/DataFiles/attendance.csv"))) {
                String[] row;
                reader.readNext(); // Skip header row

                while ((row = reader.readNext()) != null) {
                    if (row.length < 6) continue; // Skip malformed rows

                    String id = row[0].trim();
                    String dateStr = row[3].trim();
                    String timeIn = row[4].trim();
                    String timeOut = row[5].trim();

                    if (!id.equals(employeeNumber)) continue;

                    try {
                        LocalDate logDate = LocalDate.parse(dateStr, csvDateFormat);
                        YearMonth logMonth = YearMonth.from(logDate);

                        if (logMonth.equals(selectedYearMonth)) {
                            model.addRow(new Object[]{id, dateStr, timeIn, timeOut});
                        }

                    } catch (DateTimeParseException dtpe) {
                        System.err.println("Invalid date format in row: " + Arrays.toString(row));
                        continue; // Skip invalid date
                    }
                }

                attendanceTable.setModel(model);

            } catch (IOException | CsvValidationException ex) {
                JOptionPane.showMessageDialog(this, "Error reading attendance CSV:\n" + ex.getMessage());
                ex.printStackTrace();
            }

        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Invalid month selected format. Expected format: 'MMMM yyyy'.");
            dtpe.printStackTrace();
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderPanel = new javax.swing.JPanel();
        HelloWelcomeLabel = new javax.swing.JLabel();
        FirstNameHeaderLabel = new javax.swing.JLabel();
        HeaderMessageLabel = new javax.swing.JLabel();
        PersonalInfoPanel = new javax.swing.JPanel();
        PersonalInfoLabel = new javax.swing.JLabel();
        FirstNameLabel = new javax.swing.JLabel();
        FirstNameValue = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JLabel();
        LastNameValue = new javax.swing.JLabel();
        BirthdayLabel = new javax.swing.JLabel();
        BirthdayValue = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        PhoneValue = new javax.swing.JLabel();
        EmployementInfoPanel = new javax.swing.JPanel();
        EmploymentInfoLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        StatusValue = new javax.swing.JLabel();
        PositionLabel = new javax.swing.JLabel();
        PositionValue = new javax.swing.JLabel();
        immediateSupervisorLabel = new javax.swing.JLabel();
        immediateSupervisorValue = new javax.swing.JLabel();
        GovernmentInfoPanel = new javax.swing.JPanel();
        GovernmentInfoLabel = new javax.swing.JLabel();
        sssNumberLabel = new javax.swing.JLabel();
        sssNumberValue = new javax.swing.JLabel();
        pagibigNumberLabel = new javax.swing.JLabel();
        pagibigNumberValue = new javax.swing.JLabel();
        philHealthNumberLabel = new javax.swing.JLabel();
        philHealthNumberValue = new javax.swing.JLabel();
        tinNumberLabel = new javax.swing.JLabel();
        tinNumberValue = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        timeOutButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        AttendanceHeader = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox<>();
        timeInButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HeaderPanel.setBackground(new java.awt.Color(204, 0, 0));

        HelloWelcomeLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 30)); // NOI18N
        HelloWelcomeLabel.setForeground(new java.awt.Color(255, 255, 255));
        HelloWelcomeLabel.setText("Hello and Welcome!");

        FirstNameHeaderLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 30)); // NOI18N
        FirstNameHeaderLabel.setForeground(new java.awt.Color(255, 255, 255));
        FirstNameHeaderLabel.setText("First Name");

        HeaderMessageLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        HeaderMessageLabel.setForeground(new java.awt.Color(255, 255, 255));
        HeaderMessageLabel.setText("We appreciate you!");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeaderMessageLabel)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addComponent(HelloWelcomeLabel)
                        .addGap(18, 18, 18)
                        .addComponent(FirstNameHeaderLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FirstNameHeaderLabel)
                    .addComponent(HelloWelcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HeaderMessageLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        PersonalInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));

        PersonalInfoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        PersonalInfoLabel.setForeground(new java.awt.Color(204, 0, 0));
        PersonalInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PersonalInfoLabel.setText("Personal");

        FirstNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        FirstNameLabel.setForeground(new java.awt.Color(204, 0, 0));
        FirstNameLabel.setText("First Name:");

        FirstNameValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        FirstNameValue.setText("First Name Value");

        LastNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        LastNameLabel.setForeground(new java.awt.Color(204, 0, 0));
        LastNameLabel.setText("Last Name:");

        LastNameValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        LastNameValue.setText("Last Name Value");

        BirthdayLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        BirthdayLabel.setForeground(new java.awt.Color(204, 0, 0));
        BirthdayLabel.setText("Birthday:");

        BirthdayValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        BirthdayValue.setText("Birthday Value");

        PhoneLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        PhoneLabel.setForeground(new java.awt.Color(204, 0, 0));
        PhoneLabel.setText("Phone:");

        PhoneValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        PhoneValue.setText("Phone Value");

        javax.swing.GroupLayout PersonalInfoPanelLayout = new javax.swing.GroupLayout(PersonalInfoPanel);
        PersonalInfoPanel.setLayout(PersonalInfoPanelLayout);
        PersonalInfoPanelLayout.setHorizontalGroup(
            PersonalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalInfoPanelLayout.createSequentialGroup()
                .addGroup(PersonalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInfoPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(PersonalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BirthdayValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LastNameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FirstNameValue, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(PhoneValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PersonalInfoPanelLayout.createSequentialGroup()
                                .addGroup(PersonalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BirthdayLabel)
                                    .addComponent(LastNameLabel)
                                    .addComponent(FirstNameLabel)
                                    .addComponent(PhoneLabel))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(PersonalInfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PersonalInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PersonalInfoPanelLayout.setVerticalGroup(
            PersonalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalInfoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(PersonalInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FirstNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstNameValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastNameValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BirthdayLabel)
                .addGap(3, 3, 3)
                .addComponent(BirthdayValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PhoneLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PhoneValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EmployementInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));

        EmploymentInfoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        EmploymentInfoLabel.setForeground(new java.awt.Color(204, 0, 0));
        EmploymentInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EmploymentInfoLabel.setText("Employment");

        StatusLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        StatusLabel.setForeground(new java.awt.Color(204, 0, 0));
        StatusLabel.setText("Status:");

        StatusValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        StatusValue.setText("Status Value");

        PositionLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        PositionLabel.setForeground(new java.awt.Color(204, 0, 0));
        PositionLabel.setText("Position:");

        PositionValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        PositionValue.setText("Position Value");

        immediateSupervisorLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        immediateSupervisorLabel.setForeground(new java.awt.Color(204, 0, 0));
        immediateSupervisorLabel.setText("Immediate Supervisor");

        immediateSupervisorValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        immediateSupervisorValue.setText("Immediate Supervisor Value");

        javax.swing.GroupLayout EmployementInfoPanelLayout = new javax.swing.GroupLayout(EmployementInfoPanel);
        EmployementInfoPanel.setLayout(EmployementInfoPanelLayout);
        EmployementInfoPanelLayout.setHorizontalGroup(
            EmployementInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployementInfoPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(EmployementInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StatusValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PositionValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(immediateSupervisorValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(EmployementInfoPanelLayout.createSequentialGroup()
                        .addGroup(EmployementInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(immediateSupervisorLabel)
                            .addComponent(PositionLabel)
                            .addComponent(StatusLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(EmployementInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EmploymentInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EmployementInfoPanelLayout.setVerticalGroup(
            EmployementInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployementInfoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(EmploymentInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(StatusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StatusValue)
                .addGap(18, 18, 18)
                .addComponent(PositionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PositionValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(immediateSupervisorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(immediateSupervisorValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GovernmentInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));

        GovernmentInfoLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        GovernmentInfoLabel.setForeground(new java.awt.Color(204, 0, 0));
        GovernmentInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GovernmentInfoLabel.setText("Government");

        sssNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        sssNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        sssNumberLabel.setText("SSS Number");

        sssNumberValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        sssNumberValue.setText("SSS Number Value");

        pagibigNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        pagibigNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        pagibigNumberLabel.setText("Pagibig Number:");

        pagibigNumberValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        pagibigNumberValue.setText("Pagibig Number Value");

        philHealthNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        philHealthNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        philHealthNumberLabel.setText("PhilHealth Number:");

        philHealthNumberValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        philHealthNumberValue.setText("PhilHealth Number Value");

        tinNumberLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tinNumberLabel.setForeground(new java.awt.Color(204, 0, 0));
        tinNumberLabel.setText("TIN Number:");

        tinNumberValue.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tinNumberValue.setText("TIN Number Value");

        javax.swing.GroupLayout GovernmentInfoPanelLayout = new javax.swing.GroupLayout(GovernmentInfoPanel);
        GovernmentInfoPanel.setLayout(GovernmentInfoPanelLayout);
        GovernmentInfoPanelLayout.setHorizontalGroup(
            GovernmentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GovernmentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GovernmentInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GovernmentInfoPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(GovernmentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tinNumberValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pagibigNumberValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(philHealthNumberValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sssNumberValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GovernmentInfoPanelLayout.createSequentialGroup()
                        .addGroup(GovernmentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tinNumberLabel)
                            .addComponent(pagibigNumberLabel)
                            .addComponent(philHealthNumberLabel)
                            .addComponent(sssNumberLabel))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        GovernmentInfoPanelLayout.setVerticalGroup(
            GovernmentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GovernmentInfoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(GovernmentInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sssNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sssNumberValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(philHealthNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(philHealthNumberValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pagibigNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagibigNumberValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tinNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tinNumberValue)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        timeOutButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        timeOutButton.setForeground(new java.awt.Color(204, 0, 0));
        timeOutButton.setText("Time Out");
        timeOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeOutButtonActionPerformed(evt);
            }
        });

        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Employee Number", "Date", "Time In", "Time Out"
            }
        ));
        jScrollPane1.setViewportView(attendanceTable);

        AttendanceHeader.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        AttendanceHeader.setForeground(new java.awt.Color(204, 0, 0));
        AttendanceHeader.setText("Attendance Details");

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBoxActionPerformed(evt);
            }
        });

        timeInButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        timeInButton.setForeground(new java.awt.Color(204, 0, 0));
        timeInButton.setText("Time In");
        timeInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeInButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(AttendanceHeader)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timeInButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeOutButton)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(AttendanceHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(timeOutButton)
                        .addComponent(timeInButton))
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PersonalInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmployementInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GovernmentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PersonalInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EmployementInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GovernmentInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timeOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeOutButtonActionPerformed
        // TODO add your handling code here:
        String filePath = "src/DataFiles/attendance.csv";
        String employeeId = this.employeeNumber; // Use your actual variable for the logged-in employee ID
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String logOutTime = LocalTime.now().format(timeFormatter);

        List<String[]> allRows = new ArrayList<>();
        boolean updated = false;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();

            // Reverse iterate to find latest matching Time In without Log Out
            for (int i = rows.size() - 1; i >= 0; i--) {
                String[] row = rows.get(i);
                if (row.length >= 6 && row[0].equals(employeeId) && row[5].isEmpty()) {
                    row[5] = logOutTime;
                    updated = true;
                    break; // Update only the most recent one
                }
            }

            allRows = rows;

        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(this, "Error reading attendance file:\n" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (CsvException ex) {
            System.getLogger(EmployeeDetailPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        if (!updated) {
            JOptionPane.showMessageDialog(this, "No incomplete Time In record found for this employee.", "No Open Log", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Rewrite updated CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(allRows);
            JOptionPane.showMessageDialog(this, "Time Out recorded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to attendance file:\n" + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_timeOutButtonActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthComboBoxActionPerformed

    private void timeInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeInButtonActionPerformed
        // TODO add your handling code here:
        String filePath = "src/DataFiles/attendance.csv";
        String employeeId = this.employeeNumber; // Replace with how your app stores the logged-in employee ID
        String lastNameAttendance = this.lastName;         // Replace with how you store last name
        String firstNameAttendance = this.firstName;       // Replace with how you store first name

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String[] record = {
            employeeId,
            lastNameAttendance,
            firstNameAttendance,
            currentDate.format(dateFormatter),
            currentTime.format(timeFormatter),
            "" // Empty Log Out field for now
        };

        // Write the record
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(filePath, true))) { // `true` to append
            writer.writeNext(record);
            JOptionPane.showMessageDialog(this, "Time In recorded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to record Time In.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_timeInButtonActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new EmployeeDetailPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AttendanceHeader;
    private javax.swing.JLabel BirthdayLabel;
    private javax.swing.JLabel BirthdayValue;
    private javax.swing.JPanel EmployementInfoPanel;
    private javax.swing.JLabel EmploymentInfoLabel;
    private javax.swing.JLabel FirstNameHeaderLabel;
    private javax.swing.JLabel FirstNameLabel;
    private javax.swing.JLabel FirstNameValue;
    private javax.swing.JLabel GovernmentInfoLabel;
    private javax.swing.JPanel GovernmentInfoPanel;
    private javax.swing.JLabel HeaderMessageLabel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HelloWelcomeLabel;
    private javax.swing.JLabel LastNameLabel;
    private javax.swing.JLabel LastNameValue;
    private javax.swing.JLabel PersonalInfoLabel;
    private javax.swing.JPanel PersonalInfoPanel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JLabel PhoneValue;
    private javax.swing.JLabel PositionLabel;
    private javax.swing.JLabel PositionValue;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel StatusValue;
    private javax.swing.JTable attendanceTable;
    private javax.swing.JLabel immediateSupervisorLabel;
    private javax.swing.JLabel immediateSupervisorValue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> monthComboBox;
    private javax.swing.JLabel pagibigNumberLabel;
    private javax.swing.JLabel pagibigNumberValue;
    private javax.swing.JLabel philHealthNumberLabel;
    private javax.swing.JLabel philHealthNumberValue;
    private javax.swing.JLabel sssNumberLabel;
    private javax.swing.JLabel sssNumberValue;
    private javax.swing.JButton timeInButton;
    private javax.swing.JButton timeOutButton;
    private javax.swing.JLabel tinNumberLabel;
    private javax.swing.JLabel tinNumberValue;
    // End of variables declaration//GEN-END:variables
}
