package com.ounis.fo2tweaksrditor;

import com.ounis.utils.FramesUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


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
    

    private boolean fileLoadErr = false;
    public boolean isFileLoaded() {
        return !this.fileLoadErr;
    }
    
    private boolean fileSaveErr = false;
    public boolean isFIleSaved() {
        return !this.fileSaveErr;
    }
    

     
//     aktualizacja tytułu panelu
     private void updateJpSectDetails(String aSect, String aKey) {
//         final TitledBorder tb = (TitledBorder)jpSectDetails.getBorder();
//         final JLabel label = new JLabel("%s :: %s");
//         label.setBorder(tb);
//         this.add(label);
//         tb.setTitle(String.format("%s :: %s",aSect,aKey));
//         label.repaint();
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
     
     
    private String makeBackupFileName(String aFileName) {
//        String fn = aFileName.split("\\.")[0];
//        String fe = aFileName.split("\\.")[1];
//        return fn.concat(CONST.BACKUP_FILE_SUFF).concat(".").concat(fe);
        return aFileName.split("\\.")[0].
                concat(CONST.BACKUP_FILE_SUFF).
                concat(".").
                concat(aFileName.split("\\.")[1]);
    }
     
    private void saveLines(String aFileName, DefaultListModel alistModel) throws IOException, ArrayIndexOutOfBoundsException {
        
        ArrayList<String> tempList = null;
        

//         for(;elements.hasMoreElements();)  
//               tempList.add(elements.nextElement());
//      konwersja z DefaultListModel<String> na ArrayList<String>
//https://stackoverflow.com/questions/7160568/iterating-through-enumeration-of-hastable-keys-throws-nosuchelementexception-err
//
        tempList = Collections.list(lmSections.elements());
        
//https://stackoverflow.com/questions/7935858/the-split-method-in-java-does-not-work-on-a-dot
//        String fname = aFileName.split("\\.")[0];
//        String fext = ".".concat(aFileName.split("\\.")[1]);
//        String backupF = fname.concat(CONST.BACKUP_FILE_SUFF).concat(fext);
        
        FileWriter file = 
                new FileWriter(aFileName, false);
        String lnsep = System.getProperty("line.separator");
        // tutaj zapis linia po linii
        String line2save = "";
        for (String line : tempList) {
            if (line.startsWith(CONST.STR_NUMBER_PREFIX)) {
//                 String keyvalue = 
                line2save = line.split(CONST.DEF_SPEC_LINE_VAL_SEP)[2];
            } else {
                line2save = line;
            }
            file.write(((String) line2save).concat(lnsep));
        }
        file.flush();
        file.close();
        
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
//                pusta linia
                if (line.strip().isEmpty()) {
                    res.add(line);
                    continue;
                }
//                sekcja
                if (line.startsWith("[")) {
//                    res.add(String.format("%s%d %s",
//                            CONST.STR_NUMBER_PREFIX, 
//                            linenum, 
//                            line));
                    res.add(line);
                    section = line;
                }
                else {
//                    System.out.printf("%d - %d\n", linenum, line.length());
                    if (line.length() == 0) {
                        res.add(String.format(" ",linenum, line));
                        continue;
                    }
//                    komentarz
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
            this.fileLoadErr = true;
        }
         
         return res;
     }
     //TODO wyciagnie numeru linii z wyswietlanej linii w liście
     @Deprecated
    private int getLineNumFromLine(String aLine) {
        if (aLine.length() > 0) {
            String temp = aLine.split(CONST.DEF_SPEC_LINE_VAL_SEP)[0];
            return Integer.valueOf(temp.substring(1));
        }
        else
            return -1;
    }
    
    @Deprecated
    private String getSectionFromLine(String aLine) {
        if (aLine.length() > 0)
            return aLine.split(CONST.DEF_SPEC_LINE_VAL_SEP)[1];
        else
            return null;
    }
    @Deprecated
    private String getKeyValFromLine(String aLine) {
        if (aLine.length() > 0)
            return aLine.split(CONST.DEF_SPEC_LINE_VAL_SEP)[2];
        else
            return null;
    }     
     private void changeValueAtList() {
         if (JOptionPane.showConfirmDialog(null, "Kontynuować?",
                 "Potwiedzenie", JOptionPane.YES_NO_OPTION,
                 JOptionPane.QUESTION_MESSAGE) == ConfirmationCallback.YES)
            if (lstSections.getModel().getSize() > 0) {
                    String temp = lstSections.getModel().getElementAt(lstSections.getSelectedIndex());
                    String editedKV = String.format(CONST.DEF_SPEC_LINE,
                            Integer.valueOf(edLineNum.getText()),
                            edSectName.getText(),
                            edKey.getText());
                    if (editedKV.equals(temp.split("=")[0])) {
                        ((DefaultListModel) lstSections.getModel()).set(lstSections.getSelectedIndex(),
                                editedKV.concat("=".concat(edValueOfKey.getText())));
                    }

            }
    }
     
//   obsługa kliknięcia myszy na liście - nieobsługiwane
     @Deprecated
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
//                    if ((itemStr.startsWith(CONST.REM_CHAR) || itemStr.strip().isEmpty())) ;
                    if (!itemStr.startsWith(CONST.STR_NUMBER_PREFIX));
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
     
     
     class btnChangeKeyAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                changeValueAtList();
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
         
     }
     
    class btnChangeClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changeValueAtList();

          }
    }

    class btnSaveClick implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean saveErr = false;
            if (JOptionPane.showConfirmDialog(null, 
                    "Zapisać do pliku: ".concat(edBackupFileName.getText()), 
                    "Potwierdzenie", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE) == CONST.CONFIRM_YES )
                    try {
                        saveLines(edBackupFileName.getText(), lmSections);
                    }
                    catch (IOException ioe) {
                        saveErr = true;
                    }
                    catch (ArrayIndexOutOfBoundsException aiobe) {
                        saveErr = true;
                    }
            if (!saveErr)
                JOptionPane.showMessageDialog(null, 
          "Plik zapisany.", 
           "Informacja",JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, 
            String.format("Błąd podczas zapisu!!!\nPlik: %s",
                 fileName));


          }
    }

    
    private void test() {
        Fo2TweaksChange fo2tc;
    }
        
    /**
     * Creates new form MainFrame
     */
    public MainFrame(String aFileName) {
        initComponents();

//        obsługa prametró
        if (!(aFileName == null))
            this.fileName = aFileName;
        else
            this.fileName = CONST.FILE_FO2TWEAKS;
        
        
        
        edBackupFileName.setText(makeBackupFileName(aFileName));
        
        this.setResizable(false);
        FramesUtils.centerWindow(this);
        this.setTitle("Fo2Tweaks");
//        JOptionPane.showMessageDialog(null, this.fileName);
        
        ArrayList<String> specLines = loadLines(this.fileName);
        if (specLines.size() == 0) {
            JOptionPane.showMessageDialog(null, "Coś poszło nie tak z wczytywaniem pliku ".concat(this.fileName));
        }
        else
            this.setTitle(this.getTitle()+ " "+this.fileName);
        
//        lstSections SETUP
        lmSections = new DefaultListModel();
        lmSections.addAll(specLines);
        lstSections.setModel(lmSections);
        lstSections.addKeyListener(new lstSectionsKeyListener());
        lstSections.addMouseListener( null// shit happends!
//                new MouseAdapter() {
//            public void mouseClicked(MouseEvent mouseevent) {
//                
//            }
//        }
        );
        

        
        
//      jpSectDetails SETUP
        updateJpSectDetails("Szczegóły","");
        
//      edLineNum SETUP
//        edLineNum.setEditable(false);
//        edValueOfKey SETUP
        edValueOfKey.addKeyListener(new edValueOfKeyKeyListener());
        
        //        btnDelSect.addActionListener(new BtnDelSectClick());
//      btnChange SETUP
        btnChange.addActionListener(new btnChangeClick());
        btnChange.addKeyListener(new btnChangeKeyAdapter());
        
//        btnSave SETUP
        btnSave.addActionListener(new btnSaveClick());
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
        btnSave = new javax.swing.JButton();
        edBackupFileName = new javax.swing.JTextField();
        lblSections1 = new javax.swing.JLabel();
        lblSections2 = new javax.swing.JLabel();
        edSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstSections.setBackground(new java.awt.Color(255, 255, 204));
        lstSections.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(lstSections);

        jpSectDetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

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

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setText("Zapisz");

        javax.swing.GroupLayout jpSectDetailsLayout = new javax.swing.GroupLayout(jpSectDetails);
        jpSectDetails.setLayout(jpSectDetailsLayout);
        jpSectDetailsLayout.setHorizontalGroup(
            jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSectDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpSectDetailsLayout.createSequentialGroup()
                            .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLineNum)
                                .addComponent(lblSectName)
                                .addComponent(lblKey))
                            .addGap(65, 65, 65))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSectDetailsLayout.createSequentialGroup()
                            .addComponent(lblKeyValue)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(jpSectDetailsLayout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addGap(68, 68, 68)))
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edLineNum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edValueOfKey)
                    .addComponent(edSectName, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addComponent(edKey)
                    .addComponent(edBackupFileName))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edValueOfKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChange)
                    .addComponent(lblKeyValue))
                .addGap(18, 18, 18)
                .addGroup(jpSectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(edBackupFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        lblSections1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSections1.setText("Szukaj:");

        lblSections2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSections2.setText("Sekcje");

        edSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpSectDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblSections2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSections1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edSearch)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSections1)
                    .addComponent(lblSections2)
                    .addComponent(edSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField edBackupFileName;
    private javax.swing.JTextField edKey;
    private javax.swing.JTextField edLineNum;
    private javax.swing.JTextField edSearch;
    private javax.swing.JTextField edSectName;
    private javax.swing.JTextField edValueOfKey;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpSectDetails;
    private javax.swing.JLabel lblKey;
    private javax.swing.JLabel lblKeyValue;
    private javax.swing.JLabel lblLineNum;
    private javax.swing.JLabel lblSectName;
    private javax.swing.JLabel lblSections1;
    private javax.swing.JLabel lblSections2;
    private javax.swing.JList<String> lstSections;
    // End of variables declaration//GEN-END:variables
}
