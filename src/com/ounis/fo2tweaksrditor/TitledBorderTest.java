package com.ounis.fo2tweaksrditor;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author AndroidDev
 */
public class TitledBorderTest {

     public static void main(String[] args) {
          EventQueue.invokeLater(new Runnable() {
              public void run() {
                  new TitledBorderTest().create();
              }
          });
     }

         
     private void create() {

         String s = "This is a TitledBorder update test.";
         final JLabel label = new JLabel(s);
         final TitledBorder tb =
             BorderFactory.createTitledBorder(new Date().toString());
         label.setBorder(tb);
         
         JFrame f = new JFrame("Titled Border Test");
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.add(label);
         f.add(new JButton(new AbstractAction("Update") {
         Object[] selValues = new Object[] {"Romani", "Ite", "Domum"};
            public void actionPerformed(ActionEvent e) {
//                http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanewithapredefinedselections.htm
                UIManager.put("OptionPane.noButtonText", "Nie");
                UIManager.put("OptionPane.yesButtonText", "Tak");
                UIManager.put("OptionPane.okButtonText", "Dobra");
                UIManager.put("OptionPane.cancelButtonText", "Anuluj");
                System.out.println(JOptionPane.showInputDialog(null, 
                        "Info", 
                        "Potwierdź", 
                        JOptionPane.QUESTION_MESSAGE, null
                        , new Object[] {"Romani", "Ite", "Domum"}, 
                        "jeden"));
//                System.out.println(JOptionPane.showInputDialog(null, "JEST","propozycja"));
//                JOptionPane.showInputDialog(null, "Nazwa pliku:", null);
                tb.setTitle(new Date().toString());
                label.repaint();
            }
        }), BorderLayout.SOUTH);
         f.pack();
         f.setLocationRelativeTo(null);
         f.setVisible(true);
         System.out.println(tb.getMinimumSize(f));
     }
}
