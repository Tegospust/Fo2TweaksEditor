/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.ounis.fo2tweaksrditor;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author AndroidDev
 */
public class Fo2TweaksEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) {
        // TODO code application logic here
//        JOptionPane.showMessageDialog(null, "Fo2TweaksEditor");
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                String par = args.length > 0 ? args[0] : "";
                if (args.length > 0) {
                    File f = new File(args[0]);
                    if (!(new File(args[0])).exists())
                        JOptionPane.showMessageDialog(null, 
                                String.format("Plik %s nie istnieje!!!", args[0]),
                                "B????d", JOptionPane.ERROR_MESSAGE
                        );
                    else
                        new MainFrame(args[0]).setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(null, 
                            "Brak parametru nazwy pliku!!!\nFo2WeaksEditor.jar <plik.ini>",
                            "B????d", JOptionPane.CANCEL_OPTION);
            }
        });
    }
    
}
