package com.ounis.fo2tweaksrditor;

import com.ounis.utils.FramesUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author AndroidDev
 */
public class MainFrame extends javax.swing.JFrame {

    private DefaultListModel<String> lmSections;
    private String fileName;

    private boolean FileLoadErr = false;
    public boolean isFileLoaded() {
        return !this.FileLoadErr;
    }
    

     
//     aktualizacja tytułu panelu
     private void updateJpSectDetails(String aSect, String aKey) {
         final TitledBorder tb = (TitledBorder)jpSectDetails.getBorder();
         final JLabel label = new JLabel("%s :: %s");
         label.setBorder(tb);
         this.add(label);
         tb.setTitle(String.format("%s :: %s",aSect,aKey));
         label.repaint();
//         ((TitledBorder)jpSectDetails.getBorder()).setTitle(String.format("%s :: %s",aSect,aKey));
     }
//    aktulizacja panelu edycji wartości po wybraniu jej z listy
     private void updateSectionValue(String aLine) {
        String[] chunks = aLine.split(CONST.DEF_SPEC_LINE_VAL_SEP);
        String lineNum = "";
        String section = "";
        String keyvalue = "";
        String key = "";
        String value = "";
        if (chunks.length > 0) {
            lineNum = chunks[0].substring(1);
            section = chunks[1];
            keyvalue = chunks[2];
            chunks = keyvalue.split(CONST.KEY_VAL_SEP);
            if (chunks.length > 0) {
                key = chunks[0];
                value = chunks[1];
            }
        }
        if (!(lineNum + section + keyvalue + key + value).isEmpty()) {
            edLineNum.setText(lineNum);
            edSectName.setText(section);
            edKey.setText(key);
            edValueOfKey.setText(value);
            updateJpSectDetails(section, key);
        }
    }
     @Deprecated
     private void refreshControlsAfterLoad() {
//                     ((DefaultListModel) lstSections.getModel()).clear();
    
     }
     
     private String getListSectionItemLineFromControls() {
         return String.format(CONST.DEF_SPEC_LINE, 
                 Integer.valueOf(edLineNum.getText()),
                 edSectName.getText(),
                 edKey.getText()+CONST.KEY_VAL_SEP+edValueOfKey.getText());
     }
     
     
     private ArrayList<String> loadLines(String aFileName) {
        ArrayList<String> res = new ArrayList<String>();
        try {
            BufferedReader textFile = new BufferedReader(
                          new InputStreamReader(new FileInputStream(aFileName)));
            String line;
            String section = "";
            int linenum = 0;
            while((line = textFile.readLine()) != null) {
                linenum++;
                if (line.startsWith("[")) {
                    section = line;
                }
                else {
                    System.out.printf("%d - %d\n", linenum, line.length());
                    if (line.length() == 0) {
                        res.add(String.format(" ",linenum, line));
                        continue;
                    }
                    if (line.startsWith(CONST.REM_CHAR)) {
                        res.add(line);
                        continue;
                    }
                    String keyval = line;
                    String specLine = String.format(CONST.DEF_SPEC_LINE, linenum,section, keyval);
                    res.add(specLine);
                }
            }
        }
        catch (Exception  e) {
            res.clear();
            this.FileLoadErr = true;
        }
         
         return res;
     }
     
//   obsługa kliknięcia myszy na liście - nieobsługiwane
     class lstSectionsMouseHandler extends MouseAdapter {
         
     }
     
     class edValueOfKeyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                lmSections.
                lstSections.requestFocus();
            }
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
         
     }
     
     class lstSectionsKeyListener implements KeyListener {

        
         
        @Override
        public void keyTyped(KeyEvent e) {
            
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (lstSections.getModel().getSize() > 0)
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int sectIDX = lstSections.getSelectedIndex();
                    String itemStr = lmSections.getElementAt(sectIDX);
                    if ((itemStr.startsWith(CONST.REM_CHAR) || itemStr.strip().isEmpty())) ;
                    else {
                        updateSectionValue(itemStr);

                        edValueOfKey.requestFocus();
                    }

    //                JOptionPane.showMessageDialog(null, itemStr);
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
         
     }
        class BtnChangeClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (lstSections.getModel().getSize() >0) {
                int selidx = lstSections.getSelectedIndex();
                lmSections.set(selidx, getListSectionItemLineFromControls());
                
            }
            else
                JOptionPane.showMessageDialog(null,"Lista sekcji jest pusta!!!");
        }
         
     }
     
    /**
     * Creates new form MainFrame
     */
    public MainFrame(String aFileName) {
        boolean b = true;
        if (!(aFileName == null))
            this.fileName = aFileName;
        else
            this.fileName = CONST.FILE_FO2TWEAKS;
        
        initComponents();
        FramesUtils.centerWindow(this);
        this.setTitle("Fo2Tweaks");
//        JOptionPane.showMessageDialog(null, this.fileName);
        
        ArrayList<String> specLines = loadLines(this.fileName);
        this.setTitle(this.getTitle()+ " "+this.fileName);
//        lstSections SETUP
        lmSections = new DefaultListModel();
        lmSections.addAll(specLines);
        
        lstSections.setModel(lmSections);
        lstSections.addKeyListener(new lstSectionsKeyListener());
        

        
        
//      jpSectDetails SETUP
        updateJpSectDetails("Szczegóły","");
        
//      edLineNum SETUP
//        edLineNum.setEditable(false);
//        edValueOfKey SETUP
        edValueOfKey.addKeyListener(new edValueOfKeyKeyListener());
        
        //        btnDelSect.addActionListener(new BtnDelSectClick());
//      btnChange SETUP
        btnChange.addActionListener(new BtnChangeClick());
    }
        
        
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstSections = new javax.swing.JList<>();
        jpSectDetails = new javax.swing.JPanel();
        lblKeyValue = new javax.swing.JLabel();
        edValueOfKey = new javax.swing.JTextField();
        lblLineNum = new javax.swing.JLabel();
        edLineNum = new javax.swing.JTextField();
        lblSectName = new javax.swing.JLabel();
        edSectName = new javax.swing.JTextField();
        lblKey = new javax.swing.JLabel();
        edKey = new javax.swing.JTextField();
        btnChange = new javax.swing.JButton();
        lblSections1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstSections.setBackground(new java.awt.Color(255, 255, 204));
        lstSections.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(lstSections);

        jpSectDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "<nie wybrano sekcji>", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        lblKeyValue.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblKeyValue.setText("Wartość parametru:");

        edValueOfKey.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblLineNum.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLineNum.setText("Numer liniil");

        edLineNum.setEditable(false);
        edLineNum.setBackground(new java.awt.Color(255, 255, 204));
        edLineNum.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edLineNum.setText("0");
        edLineNum.setEnabled(false);

        lblSectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSectName.setText("Sekcja:");

        edSectName.setEditable(false);
        edSectName.setBackground(new java.awt.Color(255, 255, 204));
        edSectName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edSectName.setEnabled(false);

        lblKey.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblKey.setText("Parametr:");

        edKey.setEditable(false);
        edKey.setBackground(new java.awt.Color(255, 255, 204));
        edKey.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edKey.setEnabled(false);

        btnChange.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChange.setText("Zmień");

        javax.swing.GroupLayout jpSectDetailsLayout = new javax.swing.GroupLayout(jpSectDetails);
        jpSectDetails.setLayout(jpSectDetailsLayout);
        jpSectDetailsLayout.setHorizontalGroup(
            jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSectDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKeyValue)
                    .addComponent(lblLineNum)
                    .addComponent(lblSectName)
                    .addComponent(lblKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edLineNum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edValueOfKey)
                    .addComponent(edSectName, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addComponent(edKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChange)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpSectDetailsLayout.setVerticalGroup(
            jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSectDetailsLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLineNum)
                    .addComponent(edLineNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSectName)
                    .addComponent(edSectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKey)
                    .addComponent(edKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKeyValue)
                    .addComponent(edValueOfKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChange))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        lblSections1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSections1.setText("Sekcje");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpSectDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSections1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblSections1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpSectDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChange;
    private javax.swing.JTextField edKey;
    private javax.swing.JTextField edLineNum;
    private javax.swing.JTextField edSectName;
    private javax.swing.JTextField edValueOfKey;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpSectDetails;
    private javax.swing.JLabel lblKey;
    private javax.swing.JLabel lblKeyValue;
    private javax.swing.JLabel lblLineNum;
    private javax.swing.JLabel lblSectName;
    private javax.swing.JLabel lblSections1;
    private javax.swing.JList<String> lstSections;
    // End of variables declaration//GEN-END:variables
}
