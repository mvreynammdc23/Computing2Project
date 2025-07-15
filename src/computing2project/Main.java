/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package computing2project;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Mikymus Victorius
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Start the application
            SwingUtilities.invokeLater(() -> {
                new LogInSelectPage().setVisible(true);
            });
        } 
        catch (Exception e) {
            showError("Failed to initialize application: " + e.getMessage());
        }
    }
    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
}
