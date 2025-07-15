/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package computing2project;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mikymus Victorius
 */
public class AdminDetailPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDetailPage.class.getName());
    private final Map<String, String> monthDisplayToValueMap = new LinkedHashMap<>();

    /**
     * Creates new form AdminDetailPage
     */
    public AdminDetailPage() throws CsvValidationException {
        initComponents();
        loadEmployeeDataFromCSV("src/DataFiles/employees.csv"); // Adjust path as needed
        loadPayrollEmployeeListCSVReader();
        populateMonthComboBoxFromAttendance("src/DataFiles/attendance.csv");
    }
    
    public void loadEmployeeDataFromCSV(String filePath) {
        
        String[] columnNames = {
        "Employee ID", "Last Name", "First Name", "Birthday", "Address", "Phone Number",
        "SSS Number", "Philhealth Number", "TIN", "Pagibig Number", "Status",
        "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy",
        "Phone Allowance", "Clothing Allowance", "Gross Semi-monthly Rate", "Hourly Rate"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();

            for (int i = 1; i < rows.size(); i++) { // skip header
                String[] rowData = rows.get(i);

                // Filter out portal password (index 1)
                String[] filteredRow = new String[] {
                    rowData[0],  // Employee ID
                    rowData[2],  // Last Name
                    rowData[3],  // First Name
                    rowData[4],  // Birthday
                    rowData[5],  // Address (with commas preserved!)
                    rowData[6],  // Phone Number
                    rowData[7],  // SSS Number
                    rowData[8],  // Philhealth Number
                    rowData[9],  // TIN
                    rowData[10], // Pagibig Number
                    rowData[11], // Status
                    rowData[12], // Position
                    rowData[13], // Supervisor
                    rowData[14], // Basic Salary
                    rowData[15], // Rice Subsidy
                    rowData[16], // Phone Allowance
                    rowData[17], // Clothing Allowance
                    rowData[18], // Gross
                    rowData[19]  // Hourly
                };

                tableModel.addRow(filteredRow);
            }

            employeesTable.setModel(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadPayrollEmployeeListCSVReader() {
    String filePath = "src/DataFiles/employees.csv";
    String[] columnNames = { "Employee ID", "Last Name", "First Name" };
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();

            // Skip header
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);

                if (row.length >= 4) {
                    String[] filteredRow = new String[] {
                        row[0], // Employee ID
                        row[2], // Last Name
                        row[3]  // First Name
                    };
                    tableModel.addRow(filteredRow);
                }
            }

            payrollEmployeeListTable.setModel(tableModel);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee list: " + e.getMessage(),
                "File Error", JOptionPane.ERROR_MESSAGE);
        } catch (CsvException ex) {
            System.getLogger(AdminDetailPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        payrollEmployeeListTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int selectedRow = payrollEmployeeListTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedEmployeeId = payrollEmployeeListTable.getValueAt(selectedRow, 0).toString();
                String selectedMonth = getSelectedMonthValue(); // MM format
                try {
                    loadAttendanceForEmployee(selectedEmployeeId, selectedMonth);
                } catch (CsvException ex) {
                    ex.printStackTrace();
                }
            }
        }
        });
    }
    
    private void populateMonthComboBoxFromAttendance(String csvPath) throws CsvValidationException {
        monthDisplayToValueMap.clear(); // Reset if already populated

        Set<String> seenValues = new HashSet<>(); // To avoid duplicates
        DateTimeFormatter csvDateFormat = DateTimeFormatter.ofPattern("MM dd yyyy");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMMM yyyy"); // e.g., March 2024
        DateTimeFormatter valueFormatter = DateTimeFormatter.ofPattern("MM yyyy");     // e.g., 03 2024

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] row;
            reader.readNext(); // Skip header

            while ((row = reader.readNext()) != null) {
                if (row.length >= 4) {
                    String rawDate = row[3].trim();

                    try {
                        LocalDate date = LocalDate.parse(rawDate, csvDateFormat);
                        String display = date.format(displayFormatter); // e.g., March 2024
                        String value = date.format(valueFormatter);     // e.g., 03 2024

                        if (!seenValues.contains(value)) {
                            monthComboBox.addItem(display);
                            monthDisplayToValueMap.put(display, value);
                            seenValues.add(value);
                        }

                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date: " + rawDate);
                    }
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading attendance CSV: " + e.getMessage());
        }
    }

    /*private String capitalizeFirstLetter(String input) {
        if (input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }*/
    
    private String getSelectedMonthValue() {
        String selectedDisplay = (String) monthComboBox.getSelectedItem(); // e.g., "March 2024"
        if (selectedDisplay == null || selectedDisplay.isEmpty()) return "";
        return monthDisplayToValueMap.getOrDefault(selectedDisplay, ""); // e.g., "03 2024"
    }
    
    private void loadAttendanceForEmployee(String employeeId, String selectedMonthYear) throws CsvException {
        String filePath = "src/DataFiles/attendance.csv";
        String[] columnNames = { "Date", "Log In", "Log Out" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        DateTimeFormatter csvDateFormat = DateTimeFormatter.ofPattern("MM dd yyyy");
        DateTimeFormatter monthYearFormat = DateTimeFormatter.ofPattern("MM yyyy"); // match comboBox

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();

            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);

                if (row.length >= 6 && row[0].equals(employeeId)) {
                    String dateStr = row[3].trim(); // e.g., "06 03 2024"

                    try {
                        LocalDate date = LocalDate.parse(dateStr, csvDateFormat);
                        String recordMonthYear = date.format(monthYearFormat); // e.g., "06 2024"

                        if (selectedMonthYear.isEmpty() || selectedMonthYear.equals(recordMonthYear)) {
                            tableModel.addRow(new String[]{ dateStr, row[4], row[5] });
                        }

                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date: " + dateStr);
                    }
                }
            }

            payrollAttendanceListTable.setModel(tableModel);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading attendance file:\n" + e.getMessage(),
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshEmployeeTable() {
        loadEmployeeDataFromCSV("src/DataFiles/employees.csv");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeesTable = new javax.swing.JTable();
        AddButton = new javax.swing.JButton();
        EditButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        ManageEmployeeHeader = new javax.swing.JLabel();
        payrollTab = new javax.swing.JPanel();
        payrollAttendanceListPanel = new javax.swing.JScrollPane();
        payrollAttendanceListTable = new javax.swing.JTable();
        computeSalaryButton = new javax.swing.JButton();
        payrollEmployeeListPanel = new javax.swing.JScrollPane();
        payrollEmployeeListTable = new javax.swing.JTable();
        monthLabel = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox<>();
        GreetingHeader = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setForeground(new java.awt.Color(204, 0, 0));

        employeesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(employeesTable);

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        EditButton.setText("Edit");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        ManageEmployeeHeader.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 16)); // NOI18N
        ManageEmployeeHeader.setForeground(new java.awt.Color(204, 0, 0));
        ManageEmployeeHeader.setText("Manage Employee Details");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EditButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(DeleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ManageEmployeeHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(ManageEmployeeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AddButton)
                        .addGap(24, 24, 24)
                        .addComponent(EditButton)
                        .addGap(26, 26, 26)
                        .addComponent(DeleteButton)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Employees", jPanel1);

        payrollAttendanceListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Time In", "Time Out"
            }
        ));
        payrollAttendanceListPanel.setViewportView(payrollAttendanceListTable);

        computeSalaryButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        computeSalaryButton.setForeground(new java.awt.Color(204, 0, 0));
        computeSalaryButton.setText("Compute Salary");
        computeSalaryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeSalaryButtonActionPerformed(evt);
            }
        });

        payrollEmployeeListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        payrollEmployeeListPanel.setViewportView(payrollEmployeeListTable);

        monthLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        monthLabel.setForeground(new java.awt.Color(204, 0, 0));
        monthLabel.setText("Month:");

        monthComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        monthComboBox.setForeground(new java.awt.Color(204, 0, 0));
        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout payrollTabLayout = new javax.swing.GroupLayout(payrollTab);
        payrollTab.setLayout(payrollTabLayout);
        payrollTabLayout.setHorizontalGroup(
            payrollTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, payrollTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(payrollEmployeeListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(payrollTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(payrollTabLayout.createSequentialGroup()
                        .addComponent(payrollAttendanceListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(payrollTabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(computeSalaryButton)))
                .addContainerGap())
        );
        payrollTabLayout.setVerticalGroup(
            payrollTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollTabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(payrollTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(payrollEmployeeListPanel)
                    .addComponent(payrollAttendanceListPanel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(payrollTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(computeSalaryButton)
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthLabel)))
        );

        jTabbedPane1.addTab("Payroll", payrollTab);

        GreetingHeader.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        GreetingHeader.setForeground(new java.awt.Color(204, 0, 0));
        GreetingHeader.setText("Hello Admin/HR User, Have a great day!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(GreetingHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GreetingHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        // TODO add your handling code here:
        AddEmployeePage addEmployeePage = new AddEmployeePage(this); // pass current AdminDetailPage
        addEmployeePage.setVisible(true);
    }//GEN-LAST:event_AddButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = employeesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            return;
        }

        String selectedEmployeeId = (String) employeesTable.getValueAt(selectedRow, 0); // Column 0 = Employee ID
        try {
            new EditEmployeePage(selectedEmployeeId, this).setVisible(true);
        } 
        
        catch (CsvValidationException ex) {
            System.getLogger(AdminDetailPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_EditButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = employeesTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeIdToDelete = (String) employeesTable.getValueAt(selectedRow, 0); // Assuming column 0 is Employee ID
        String employeeName = (String) employeesTable.getValueAt(selectedRow, 2) + " " + (String) employeesTable.getValueAt(selectedRow, 1); // FirstName + LastName

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete employee: " + employeeName + " (ID: " + employeeIdToDelete + ")?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return; // Cancel deletion
        }

        File inputFile = new File("src/DataFiles/employees.csv");
        File tempFile = new File("src/DataFiles/employees_temp.csv");

        boolean deleted = false;

        try (
            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(tempFile))
        ) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length > 0 && nextLine[0].equals(employeeIdToDelete)) {
                    deleted = true; // Skip this line (deleting it)
                    continue;
                }
                writer.writeNext(nextLine);
            }

        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (CsvValidationException ex) {
            System.getLogger(AdminDetailPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        // Replace original file with updated one
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Failed to rename updated file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (deleted) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            loadEmployeeDataFromCSV("src/DataFiles/employees.csv"); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Employee ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void computeSalaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeSalaryButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = payrollEmployeeListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedEmployeeId = payrollEmployeeListTable.getValueAt(selectedRow, 0).toString();
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        if (selectedMonth == null || selectedMonth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a month.", "No Month Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Extract month and year (e.g., "July 2024")
        String[] parts = selectedMonth.split(" ");
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(this, "Invalid month format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String monthName = parts[0];
        String year = parts[1];

        Map<String, String> monthMap = Map.ofEntries(
            Map.entry("January", "01"), Map.entry("February", "02"), Map.entry("March", "03"),
            Map.entry("April", "04"), Map.entry("May", "05"), Map.entry("June", "06"),
            Map.entry("July", "07"), Map.entry("August", "08"), Map.entry("September", "09"),
            Map.entry("October", "10"), Map.entry("November", "11"), Map.entry("December", "12")
        );

        String monthNumber = monthMap.getOrDefault(monthName, "");
        if (monthNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid month selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String attendanceFile = "src/DataFiles/attendance.csv";
        double totalHoursWorked = 0.0;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("[H:mm][HH:mm]"); // Accept 8:59 or 08:59

        try (CSVReader reader = new CSVReader(new FileReader(attendanceFile))) {
            List<String[]> rows = reader.readAll();

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length >= 6 && row[0].equals(selectedEmployeeId)) {
                    String dateStr = row[3].trim(); // MM dd yyyy
                    String logIn = row[4].trim();
                    String logOut = row[5].trim();

                    if (logIn.isEmpty() || logOut.isEmpty()) continue;

                    try {
                        LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                        String recordMonth = String.format("%02d", date.getMonthValue());
                        String recordYear = String.valueOf(date.getYear());

                        if (recordMonth.equals(monthNumber) && recordYear.equals(year)) {
                            LocalTime in = LocalTime.parse(logIn, timeFormatter);
                            LocalTime out = LocalTime.parse(logOut, timeFormatter);

                            long minutesWorked = Duration.between(in, out).toMinutes();
                            totalHoursWorked += minutesWorked / 60.0; // Don't round yet
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid date or time in row: " + Arrays.toString(row));
                    }
                }
            }

            totalHoursWorked = Math.round(totalHoursWorked * 100.0) / 100.0; // Final rounding

            System.out.println("Total Hours Worked: " + totalHoursWorked);

            // Pass to salary page
            ComputeSalaryPage salaryPage = new ComputeSalaryPage(selectedEmployeeId, totalHoursWorked);
            salaryPage.setVisible(true);

        } catch (IOException | CsvException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load attendance: " + ex.getMessage(),
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_computeSalaryButtonActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        // TODO add your handling code here:
        monthComboBox.addActionListener(e -> {
            int selectedRow = payrollEmployeeListTable.getSelectedRow();
            if (selectedRow != -1) {
                String employeeId = payrollEmployeeListTable.getValueAt(selectedRow, 0).toString();
                try {
                    String selectedMonth = getSelectedMonthValue(); // now returns "MM-yyyy"
                    loadAttendanceForEmployee(employeeId, selectedMonth);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }//GEN-LAST:event_monthComboBoxActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new AdminDetailPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JLabel GreetingHeader;
    private javax.swing.JLabel ManageEmployeeHeader;
    private javax.swing.JButton computeSalaryButton;
    private javax.swing.JTable employeesTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> monthComboBox;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JScrollPane payrollAttendanceListPanel;
    private javax.swing.JTable payrollAttendanceListTable;
    private javax.swing.JScrollPane payrollEmployeeListPanel;
    private javax.swing.JTable payrollEmployeeListTable;
    private javax.swing.JPanel payrollTab;
    // End of variables declaration//GEN-END:variables
}
