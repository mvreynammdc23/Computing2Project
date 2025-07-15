/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package computing2project;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Mikymus Victorius
 */
public class ComputeSalaryPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ComputeSalaryPage.class.getName());

    /**
     * Creates new form ComputeSalaryPage
     */
    private String employeeId;
    private double totalHoursWorked;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double hourlyRate;
    
    public ComputeSalaryPage(String employeeId, double totalHoursWorked) {
        initComponents();
        this.employeeId = employeeId;
        this.totalHoursWorked = totalHoursWorked;
        loadEmployeeInfo(employeeId);
        
        
        double grossMonthlySalary = calculateMonthlyPay(employeeId, totalHoursWorked);
        double withholdingTax = calculateWithholdingTax(grossMonthlySalary);
        double sssContribution = calculateSSSContribution(grossMonthlySalary);
        double philhealthContribution = calculatePhilHealthContribution(grossMonthlySalary);
        double pagibigContribution = calculatePagIbigContribution(grossMonthlySalary);
        double netPay = calculateNetPay(
                grossMonthlySalary,
                withholdingTax,
                sssContribution,
                philhealthContribution,
                pagibigContribution,
                riceSubsidy,
                phoneAllowance,
                clothingAllowance
                );
        
        
        witholdingTaxField.setText(String.format("%,.2f", withholdingTax));
        sssField.setText(String.format("%,.2f", sssContribution));
        philhealthField.setText(String.format("%,.2f", philhealthContribution));
        pagibigField.setText(String.format("%,.2f", pagibigContribution));
        netPayField.setText("₱" + String.format("%,.2f", netPay));
        totalHoursWorkedField.setText(String.format("%.2f", totalHoursWorked));
        
    }
    
    private void loadEmployeeInfo(String employeeId) {
        String filePath = "src/DataFiles/employees.csv";

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length >= 4 && row[0].equals(employeeId)) {
                    employeeIDLabe.setText(row[0]);
                    lastNameLabel.setText(row[2]);
                    firstNameLabel.setText(row[3]);                    
                    basicSalaryField.setText(row[14]);
                    riceSubsidyField.setText(row[15]);
                    phoneAllowanceField.setText(row[16]);
                    clothingAllowanceField.setText(row[17]);
                    grossSemiMonthlyField.setText(row[18]);
                    hourlyRateField.setText(row[19]);
                    
                    // Extract allowances from CSV columns
                    riceSubsidy = Double.parseDouble(row[15].replace(",", ""));
                    phoneAllowance = Double.parseDouble(row[16].replace(",", ""));
                    clothingAllowance = Double.parseDouble(row[17].replace(",", ""));
                    hourlyRate = Double.parseDouble(row[19].replace(",", ""));                    
                    break;
                }
            }
        } catch (IOException | CsvValidationException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee info:\n" + e.getMessage(),
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //MonthlySalary based on Hours worked
    public double calculateMonthlyPay(String employeeId, double totalHoursWorked) {

        return hourlyRate * totalHoursWorked;
    }
    
    //Witholding Tax
    public static double calculateWithholdingTax(double monthlyRate) {
        if (monthlyRate <= 20832) {
            return 0.0;
        } else if (monthlyRate <= 33333) {
            return (monthlyRate - 20833) * 0.20;
        } else if (monthlyRate <= 66667) {
            return 2500 + (monthlyRate - 33333) * 0.25;
        } else if (monthlyRate <= 166667) {
            return 10833 + (monthlyRate - 66667) * 0.30;
        } else if (monthlyRate <= 666667) {
            return 40833.33 + (monthlyRate - 166667) * 0.32;
        } else {
            return 200833.33 + (monthlyRate - 666667) * 0.35;
        }
    }
    
    //SSS
    public static double calculateSSSContribution(double monthlySalary) {
        double[][] sssBrackets = {
            {0, 3249.99, 135.00},
            {3250, 3749.99, 157.50},
            {3750, 4249.99, 180.00},
            {4250, 4749.99, 202.50},
            {4750, 5249.99, 225.00},
            {5250, 5749.99, 247.50},
            {5750, 6249.99, 270.00},
            {6250, 6749.99, 292.50},
            {6750, 7249.99, 315.00},
            {7250, 7749.99, 337.50},
            {7750, 8249.99, 360.00},
            {8250, 8749.99, 382.50},
            {8750, 9249.99, 405.00},
            {9250, 9749.99, 427.50},
            {9750, 10249.99, 450.00},
            {10250, 10749.99, 472.50},
            {10750, 11249.99, 495.00},
            {11250, 11749.99, 517.50},
            {11750, 12249.99, 540.00},
            {12250, 12749.99, 562.50},
            {12750, 13249.99, 585.00},
            {13250, 13749.99, 607.50},
            {13750, 14249.99, 630.00},
            {14250, 14749.99, 652.50},
            {14750, 15249.99, 675.00},
            {15250, 15749.99, 697.50},
            {15750, 16249.99, 720.00},
            {16250, 16749.99, 742.50},
            {16750, 17249.99, 765.00},
            {17250, 17749.99, 787.50},
            {17750, 18249.99, 810.00},
            {18250, 18749.99, 832.50},
            {18750, 19249.99, 855.00},
            {19250, 19749.99, 877.50},
            {19750, 20249.99, 900.00},
            {20250, 20749.99, 922.50},
            {20750, 21249.99, 945.00},
            {21250, 21749.99, 967.50},
            {21750, 22249.99, 990.00},
            {22250, 22749.99, 1012.50},
            {22750, 23249.99, 1035.00},
            {23250, 23749.99, 1057.50},
            {23750, 24249.99, 1080.00},
            {24250, 24749.99, 1102.50},
            {24750, Double.MAX_VALUE, 1125.00} // Over 24,750
        };

        for (double[] bracket : sssBrackets) {
            if (monthlySalary >= bracket[0] && monthlySalary <= bracket[1]) {
                return bracket[2];
            }
        }

        return 0.0; // fallback, shouldn't happen
    }
    
    //philhealth
    public static double calculatePhilHealthContribution(double monthlySalary) {
        double rate = 0.03; // 3%
        return monthlySalary * rate;
    }
    
    //Pagibig
    public static double calculatePagIbigContribution(double monthlySalary) {
        if (monthlySalary >= 1000 && monthlySalary <= 1500) {
            return monthlySalary * 0.01;  // 1%
        } 
        else if (monthlySalary > 1500) {
            return monthlySalary * 0.02;  // 2%
        } 
        else {
            return 0.0; // Below 1,000: no contribution
        }
    }
    
    public double calculateNetPay(double grossMonthlyPay, 
                              double withholdingTax, 
                              double sssContribution,
                              double philHealthContribution,
                              double pagibigContribution,
                              double riceSubsidy,
                              double phoneAllowance,
                              double clothingAllowance) {
    
        double deductions = withholdingTax + sssContribution + philHealthContribution + pagibigContribution;
        double allowances = riceSubsidy + phoneAllowance + clothingAllowance;
        return (grossMonthlyPay - deductions) + allowances;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        employeeIDLabe = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        hourlyRateLabel2 = new javax.swing.JLabel();
        riceSubsidyLabel = new javax.swing.JLabel();
        riceSubsidyField = new javax.swing.JTextField();
        phoneAllowanceLabel = new javax.swing.JLabel();
        phoneAllowanceField = new javax.swing.JTextField();
        clothingAllowanceLabel = new javax.swing.JLabel();
        clothingAllowanceField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        basicSalaryLabel = new javax.swing.JLabel();
        basicSalaryField = new javax.swing.JTextField();
        grossSemiMonthlyLabel = new javax.swing.JLabel();
        grossSemiMonthlyField = new javax.swing.JTextField();
        hourlyRateLabel = new javax.swing.JLabel();
        hourlyRateField = new javax.swing.JTextField();
        totalHoursWorkedLabel = new javax.swing.JLabel();
        totalHoursWorkedField = new javax.swing.JTextField();
        sssLabel = new javax.swing.JPanel();
        hourlyRateLabel1 = new javax.swing.JLabel();
        witholdingTaxLabel = new javax.swing.JLabel();
        witholdingTaxField = new javax.swing.JTextField();
        basicSalaryLabel1 = new javax.swing.JLabel();
        sssField = new javax.swing.JTextField();
        philhealthLabel = new javax.swing.JLabel();
        philhealthField = new javax.swing.JTextField();
        pagibigLabel = new javax.swing.JLabel();
        pagibigField = new javax.swing.JTextField();
        netPayLabel = new javax.swing.JLabel();
        netPayField = new javax.swing.JTextField();
        doneButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Payslip");

        employeeIDLabe.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        employeeIDLabe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        employeeIDLabe.setText("Employee ID");

        lastNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lastNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lastNameLabel.setText("Last Name");

        firstNameLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        firstNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstNameLabel.setText("First Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(employeeIDLabe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeIDLabe)
                    .addComponent(lastNameLabel)
                    .addComponent(firstNameLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));

        hourlyRateLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hourlyRateLabel2.setForeground(new java.awt.Color(204, 0, 0));
        hourlyRateLabel2.setText("Allowances");

        riceSubsidyLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        riceSubsidyLabel.setForeground(new java.awt.Color(204, 0, 0));
        riceSubsidyLabel.setText("Rice Subsidy");

        riceSubsidyField.setEditable(false);
        riceSubsidyField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        riceSubsidyField.setText("jTextField1");
        riceSubsidyField.setAlignmentX(1.0F);
        riceSubsidyField.setAlignmentY(1.0F);
        riceSubsidyField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        riceSubsidyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riceSubsidyFieldActionPerformed(evt);
            }
        });

        phoneAllowanceLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        phoneAllowanceLabel.setForeground(new java.awt.Color(204, 0, 0));
        phoneAllowanceLabel.setText("Phone Allowance");

        phoneAllowanceField.setEditable(false);
        phoneAllowanceField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        phoneAllowanceField.setText("jTextField1");
        phoneAllowanceField.setAlignmentX(1.0F);
        phoneAllowanceField.setAlignmentY(1.0F);
        phoneAllowanceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        phoneAllowanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneAllowanceFieldActionPerformed(evt);
            }
        });

        clothingAllowanceLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clothingAllowanceLabel.setForeground(new java.awt.Color(204, 0, 0));
        clothingAllowanceLabel.setText("Clothing Allowance");

        clothingAllowanceField.setEditable(false);
        clothingAllowanceField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clothingAllowanceField.setText("jTextField1");
        clothingAllowanceField.setAlignmentX(1.0F);
        clothingAllowanceField.setAlignmentY(1.0F);
        clothingAllowanceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        clothingAllowanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clothingAllowanceFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(riceSubsidyLabel))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(hourlyRateLabel2)))
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(riceSubsidyField)
                            .addComponent(phoneAllowanceField)
                            .addComponent(clothingAllowanceField)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phoneAllowanceLabel)
                                    .addComponent(clothingAllowanceLabel))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hourlyRateLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(riceSubsidyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(riceSubsidyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneAllowanceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneAllowanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clothingAllowanceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clothingAllowanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));

        basicSalaryLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        basicSalaryLabel.setForeground(new java.awt.Color(204, 0, 0));
        basicSalaryLabel.setText("Basic Salary");

        basicSalaryField.setEditable(false);
        basicSalaryField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        basicSalaryField.setText("jTextField1");
        basicSalaryField.setAlignmentX(1.0F);
        basicSalaryField.setAlignmentY(1.0F);
        basicSalaryField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        basicSalaryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                basicSalaryFieldActionPerformed(evt);
            }
        });

        grossSemiMonthlyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        grossSemiMonthlyLabel.setForeground(new java.awt.Color(204, 0, 0));
        grossSemiMonthlyLabel.setText("Gross Semi Monthly");

        grossSemiMonthlyField.setEditable(false);
        grossSemiMonthlyField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        grossSemiMonthlyField.setText("jTextField1");
        grossSemiMonthlyField.setAlignmentX(1.0F);
        grossSemiMonthlyField.setAlignmentY(1.0F);
        grossSemiMonthlyField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        grossSemiMonthlyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grossSemiMonthlyFieldActionPerformed(evt);
            }
        });

        hourlyRateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hourlyRateLabel.setForeground(new java.awt.Color(204, 0, 0));
        hourlyRateLabel.setText("Hourly Rate");

        hourlyRateField.setEditable(false);
        hourlyRateField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hourlyRateField.setText("jTextField1");
        hourlyRateField.setAlignmentX(1.0F);
        hourlyRateField.setAlignmentY(1.0F);
        hourlyRateField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        hourlyRateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourlyRateFieldActionPerformed(evt);
            }
        });

        totalHoursWorkedLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totalHoursWorkedLabel.setForeground(new java.awt.Color(204, 0, 0));
        totalHoursWorkedLabel.setText("Total Hours Worked");

        totalHoursWorkedField.setEditable(false);
        totalHoursWorkedField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totalHoursWorkedField.setText("jTextField1");
        totalHoursWorkedField.setAlignmentX(1.0F);
        totalHoursWorkedField.setAlignmentY(1.0F);
        totalHoursWorkedField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        totalHoursWorkedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalHoursWorkedFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicSalaryField)
                    .addComponent(grossSemiMonthlyField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hourlyRateField)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(basicSalaryLabel)
                            .addComponent(grossSemiMonthlyLabel)
                            .addComponent(hourlyRateLabel)
                            .addComponent(totalHoursWorkedLabel))
                        .addGap(0, 66, Short.MAX_VALUE))
                    .addComponent(totalHoursWorkedField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(basicSalaryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(basicSalaryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grossSemiMonthlyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grossSemiMonthlyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hourlyRateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hourlyRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalHoursWorkedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalHoursWorkedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        sssLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));

        hourlyRateLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hourlyRateLabel1.setForeground(new java.awt.Color(204, 0, 0));
        hourlyRateLabel1.setText("Government Deductions");

        witholdingTaxLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        witholdingTaxLabel.setForeground(new java.awt.Color(204, 0, 0));
        witholdingTaxLabel.setText("Witholding Tax");

        witholdingTaxField.setEditable(false);
        witholdingTaxField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        witholdingTaxField.setText("jTextField1");
        witholdingTaxField.setAlignmentX(1.0F);
        witholdingTaxField.setAlignmentY(1.0F);
        witholdingTaxField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        witholdingTaxField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                witholdingTaxFieldActionPerformed(evt);
            }
        });

        basicSalaryLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        basicSalaryLabel1.setForeground(new java.awt.Color(204, 0, 0));
        basicSalaryLabel1.setText("SSS");

        sssField.setEditable(false);
        sssField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sssField.setText("jTextField1");
        sssField.setAlignmentX(1.0F);
        sssField.setAlignmentY(1.0F);
        sssField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        sssField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sssFieldActionPerformed(evt);
            }
        });

        philhealthLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        philhealthLabel.setForeground(new java.awt.Color(204, 0, 0));
        philhealthLabel.setText("Philhealth");

        philhealthField.setEditable(false);
        philhealthField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        philhealthField.setText("jTextField1");
        philhealthField.setAlignmentX(1.0F);
        philhealthField.setAlignmentY(1.0F);
        philhealthField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        philhealthField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                philhealthFieldActionPerformed(evt);
            }
        });

        pagibigLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pagibigLabel.setForeground(new java.awt.Color(204, 0, 0));
        pagibigLabel.setText("Pagibig");

        pagibigField.setEditable(false);
        pagibigField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pagibigField.setText("jTextField1");
        pagibigField.setAlignmentX(1.0F);
        pagibigField.setAlignmentY(1.0F);
        pagibigField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        pagibigField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagibigFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sssLabelLayout = new javax.swing.GroupLayout(sssLabel);
        sssLabel.setLayout(sssLabelLayout);
        sssLabelLayout.setHorizontalGroup(
            sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sssLabelLayout.createSequentialGroup()
                .addGroup(sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sssLabelLayout.createSequentialGroup()
                        .addGroup(sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sssLabelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(hourlyRateLabel1))
                            .addGroup(sssLabelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(witholdingTaxLabel)))
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(sssLabelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(witholdingTaxField)
                            .addComponent(sssField)
                            .addComponent(philhealthField)
                            .addComponent(pagibigField)
                            .addGroup(sssLabelLayout.createSequentialGroup()
                                .addGroup(sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(basicSalaryLabel1)
                                    .addComponent(philhealthLabel)
                                    .addComponent(pagibigLabel))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        sssLabelLayout.setVerticalGroup(
            sssLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sssLabelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hourlyRateLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(witholdingTaxLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(witholdingTaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(basicSalaryLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sssField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(philhealthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(philhealthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagibigLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagibigField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        netPayLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        netPayLabel.setForeground(new java.awt.Color(204, 0, 0));
        netPayLabel.setText("Net Pay:");

        netPayField.setEditable(false);
        netPayField.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        netPayField.setText("jTextField1");
        netPayField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));

        doneButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        doneButton.setForeground(new java.awt.Color(204, 0, 0));
        doneButton.setText("Done");
        doneButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sssLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(netPayLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(netPayField, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sssLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(netPayLabel)
                    .addComponent(netPayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void basicSalaryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_basicSalaryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_basicSalaryFieldActionPerformed

    private void grossSemiMonthlyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grossSemiMonthlyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grossSemiMonthlyFieldActionPerformed

    private void hourlyRateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourlyRateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourlyRateFieldActionPerformed

    private void witholdingTaxFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_witholdingTaxFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_witholdingTaxFieldActionPerformed

    private void sssFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sssFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sssFieldActionPerformed

    private void philhealthFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_philhealthFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_philhealthFieldActionPerformed

    private void pagibigFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagibigFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagibigFieldActionPerformed

    private void riceSubsidyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riceSubsidyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_riceSubsidyFieldActionPerformed

    private void phoneAllowanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneAllowanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneAllowanceFieldActionPerformed

    private void clothingAllowanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clothingAllowanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clothingAllowanceFieldActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

    private void totalHoursWorkedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalHoursWorkedFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalHoursWorkedFieldActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new ComputeSalaryPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField basicSalaryField;
    private javax.swing.JLabel basicSalaryLabel;
    private javax.swing.JLabel basicSalaryLabel1;
    private javax.swing.JTextField clothingAllowanceField;
    private javax.swing.JLabel clothingAllowanceLabel;
    private javax.swing.JButton doneButton;
    private javax.swing.JLabel employeeIDLabe;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField grossSemiMonthlyField;
    private javax.swing.JLabel grossSemiMonthlyLabel;
    private javax.swing.JTextField hourlyRateField;
    private javax.swing.JLabel hourlyRateLabel;
    private javax.swing.JLabel hourlyRateLabel1;
    private javax.swing.JLabel hourlyRateLabel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField netPayField;
    private javax.swing.JLabel netPayLabel;
    private javax.swing.JTextField pagibigField;
    private javax.swing.JLabel pagibigLabel;
    private javax.swing.JTextField philhealthField;
    private javax.swing.JLabel philhealthLabel;
    private javax.swing.JTextField phoneAllowanceField;
    private javax.swing.JLabel phoneAllowanceLabel;
    private javax.swing.JTextField riceSubsidyField;
    private javax.swing.JLabel riceSubsidyLabel;
    private javax.swing.JTextField sssField;
    private javax.swing.JPanel sssLabel;
    private javax.swing.JTextField totalHoursWorkedField;
    private javax.swing.JLabel totalHoursWorkedLabel;
    private javax.swing.JTextField witholdingTaxField;
    private javax.swing.JLabel witholdingTaxLabel;
    // End of variables declaration//GEN-END:variables
}
