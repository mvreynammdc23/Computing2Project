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
public class AdminLoginPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminLoginPage.class.getName());

    /**
     * Creates new form AdminLoginPage
     */
    public AdminLoginPage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AdminLoginImage = new javax.swing.JLabel();
        AdminIDLabel = new javax.swing.JLabel();
        AdminIDField = new javax.swing.JTextField();
        AdminPasswordLabel = new javax.swing.JLabel();
        showPasswordCheckbox = new javax.swing.JCheckBox();
        LogInButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AdminPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AdminLoginImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageFiles/sharpen image.png"))); // NOI18N

        AdminIDLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        AdminIDLabel.setForeground(new java.awt.Color(204, 0, 0));
        AdminIDLabel.setText("Admin ID:");

        AdminIDField.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        AdminIDField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
        AdminIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminIDFieldActionPerformed(evt);
            }
        });

        AdminPasswordLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        AdminPasswordLabel.setForeground(new java.awt.Color(204, 0, 0));
        AdminPasswordLabel.setText("Password:");

        showPasswordCheckbox.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        showPasswordCheckbox.setForeground(new java.awt.Color(204, 0, 0));
        showPasswordCheckbox.setText("Show Password");
        showPasswordCheckbox.setBorder(null);
        showPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckboxActionPerformed(evt);
            }
        });

        LogInButton.setBackground(new java.awt.Color(204, 0, 0));
        LogInButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        LogInButton.setForeground(new java.awt.Color(255, 255, 255));
        LogInButton.setText("Log In");
        LogInButton.setBorderPainted(false);
        LogInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Demi Cond", 0, 70)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Motor PH");

        AdminPassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        AdminPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 1, true));
        AdminPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminLoginImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AdminPasswordLabel)
                    .addComponent(AdminIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showPasswordCheckbox)
                    .addComponent(AdminIDField)
                    .addComponent(AdminPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(LogInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(210, 210, 210))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(AdminLoginImage, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminIDLabel)
                    .addComponent(AdminIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdminPasswordLabel)
                    .addComponent(AdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(showPasswordCheckbox)
                .addGap(18, 18, 18)
                .addComponent(LogInButton)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AdminIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminIDFieldActionPerformed

    private void LogInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInButtonActionPerformed
        // TODO add your handling code here:
        String adminId = AdminIDField.getText();
        String portalpassword = new String(AdminPassword.getPassword());

        if (adminId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your employee number");
            return;
        } 
        if (portalpassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your password");
            return;
        }

        try {
            if (validateCredentials(adminId, portalpassword)) {
                JOptionPane.showMessageDialog(this, "Log in successful");
                
                try {
                    // Pass employeeNumber to the dashboard if needed
                    AdminDetailPage adminDetail = new AdminDetailPage();
                    adminDetail.setVisible(true);
                    
                    // Close the current login window
                    this.dispose();
                }
                catch (CsvValidationException e) {
                    JOptionPane.showMessageDialog(this, "Failed to start application: " + e.getMessage(), "Startup Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                
            }
            else {
                JOptionPane.showMessageDialog(this, "Incorrect employee number or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (CsvValidationException ex) {
            System.getLogger(AdminLoginPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_LogInButtonActionPerformed

    private void AdminPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminPasswordActionPerformed
        // TODO add your handling code here:
        AdminPassword.setEchoChar('•');
       
    }//GEN-LAST:event_AdminPasswordActionPerformed

    private void showPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckboxActionPerformed
        // TODO add your handling code here:
         if (showPasswordCheckbox.isSelected()) {
        AdminPassword.setEchoChar((char) 0); // Show password
        } 
        else {
        AdminPassword.setEchoChar('•'); // Hide password
        }
    }//GEN-LAST:event_showPasswordCheckboxActionPerformed

    private boolean validateCredentials(String adminId, String portalPassword) throws CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader("src/DataFiles/admins.csv"))) {
            String[] row;

            while ((row = reader.readNext()) != null) {
                if (row.length >= 2) {
                    String id = row[0].trim();
                    String password = row[1].trim();

                    if (id.equals(adminId.trim()) && password.equals(portalPassword.trim())) {
                        return true; // ✅ Credentials match
                    }
                }
            }
        } catch (IOException | CsvValidationException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while logging in. Please try again later.");
            ex.printStackTrace();
        }

        return false; // ❌ No match found
    }
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
        java.awt.EventQueue.invokeLater(() -> new AdminLoginPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AdminIDField;
    private javax.swing.JLabel AdminIDLabel;
    private javax.swing.JLabel AdminLoginImage;
    private javax.swing.JPasswordField AdminPassword;
    private javax.swing.JLabel AdminPasswordLabel;
    private javax.swing.JButton LogInButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox showPasswordCheckbox;
    // End of variables declaration//GEN-END:variables
}
