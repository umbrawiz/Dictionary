/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


public class NewJFrame extends javax.swing.JFrame {
    
    /**
     * Creates new form NewJFrame
     */
    
    private DictionaryManagement management = new DictionaryManagement();
    private DictionaryCommandline cmd = new DictionaryCommandline();
    public Dictionary restore = management.insertFromFile("BASIX.txt");
    public Dictionary dictionary = management.insertFromFile("E_V.txt");
    public Dictionary added = management.insertFromFile("addedHistory.txt");
    public Dictionary removed = management.insertFromFile("removedHistory.txt");
    public Dictionary history = management.insertFromFile("History.txt");
    public DefaultListModel model = new DefaultListModel();
    public DefaultListModel modelD = new DefaultListModel();
    public DefaultListModel modelA = new DefaultListModel();
    public DefaultListModel modelR = new DefaultListModel();
    public DefaultListModel modelH = new DefaultListModel();
    public TextToSpeech TTS = new TextToSpeech();
    public Translate trans = new Translate();
    public int topList=0;
    public int s =0;
    public int e =dictionary.size();
    
    public void setApplicationIcon(){
        ImageIcon img = new ImageIcon("icon.jpg");
        this.setIconImage(img.getImage());
    }
    public NewJFrame() {
        setApplicationIcon();
        initComponents();
    }
    
    public void listsetMH() {
         for (int i = 0; i < history.wordList.size() ; i++) {
             modelH.addElement(history.getWordTarget(i));
        }
    }
    
    public void listsetMR() {
         for (int i = 0; i < removed.wordList.size() ; i++) {
             modelR.addElement(removed.getWordTarget(i));
        }
    }
    
    public void listsetMA() {

         for (int i = 0; i < added.wordList.size() ; i++) {
             modelA.addElement(added.getWordTarget(i));
        }
    }
    
    public void listsetMD() {
        modelD.clear();
         for (int i = 0; i < dictionary.wordList.size() ; i++) {
             modelD.addElement(dictionary.getWordTarget(i));
        }
    }


    public void searchSE(String x, Dictionary dict,DefaultListModel modelo) {
        model.clear();
        if (x.length()==0) {
            jList1.setModel(modelo);
        }
        for (int i=0; i<dict.size(); i++) {
            if (dict.getWordTarget(i).indexOf(x) == 0) {
                model.addElement(dict.getWordTarget(i));
            }
        }
        jList1.setModel(model);
    }

    public void printSE(Dictionary dict) {
        String str = jList1.getSelectedValue();
        int a = management.lookUpSE(str, dict);
        if (a>=0) {
            jTextPane1.setText(dict.getWordExplain(a));
        }
    }

    public void speakerSE(Dictionary dict) {
        String str = jList1.getSelectedValue();
        int a = management.lookUpSE(str, dict);
        if (a >= 0) {
            TTS.setText(dict.getWordTarget(a));
            TTS.speak();
        }
    }

    public void listInit() {
        model.clear();
        if (s==0 && e == dictionary.size()) {
            jList1.setModel(modelD);
            return;
        } else {
            for (int i = s; i < e ; i++) {
                model.addElement(dictionary.getWordTarget(i));
            }
            jList1.setModel(model);
        }
    }


    public void search(String x) {
        s=0;
        e=dictionary.size();
        if (x.length() == 0) {
            listInit();
            topList = 0;
            return;
        }
        while (s < dictionary.size()-1) {
            if (dictionary.getWordTarget(s).indexOf(x) != 0) {
                s++;
            } else {
                break;
            }
            
        }
        e=s;
        topList = s;
        while (e < dictionary.size()) {
            if (dictionary.getWordTarget(e).indexOf(x) == 0) {
                e++;
            } else {
                break;
            }
        }
        listInit();
    }
    
    public void print() {
        String t = jTextField1.getText();
        int selectedIndex = jList1.getSelectedIndex() ;
        if (selectedIndex != -1 && topList < dictionary.size()) {
            if (t.equals("")) {
                jTextPane1.setText(dictionary.getWordExplain(selectedIndex));
                history.pre(dictionary.getWord(selectedIndex));
            } else {
                jTextPane1.setText(dictionary.getWordExplain(topList + selectedIndex));
                history.pre(dictionary.getWord(selectedIndex + topList));
            }
            management.removeDuplicates(history);
            management.fileRewriter(history, "history.txt");

        } else {
            jTextPane1.setText("<wbr>");
        }
    }
    
    public void speaker() {
        int selectedIndex = jList1.getSelectedIndex() + topList;
        if(selectedIndex < 0) {
            return;
        }
        TTS.setText(dictionary.getWordTarget(selectedIndex));
        TTS.speak();
    }
    
    public void delete() {
        int selectedIndex = jList1.getSelectedIndex() + topList;
        if(selectedIndex < 0) {
            return;
        }
        String currentWord = dictionary.getWordTarget(selectedIndex);
        int choice = JOptionPane.showOptionDialog(null, 
        "Are you sure you want to delete '" + currentWord + "' ?", 
        "Delete?", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        if (choice == JOptionPane.YES_OPTION){
            removed.pre(dictionary.getWord(selectedIndex));
            management.writeToFile(dictionary.getWord(selectedIndex), "removedHistory.txt");
            dictionary.wordList.remove(selectedIndex);
            management.fileRewriter(dictionary, "E_V.txt");
            jTextPane1.setText("<wbr>");
            model.removeElement(currentWord);
            modelD.removeElement(currentWord);
            if (jRadioButton1.isSelected()) {
                search(jTextField1.getText());
            }
            if (jRadioButton3.isSelected()) {
                searchSE(jTextField1.getText(), removed, modelR);
            }
        }
    }
    
    public void add() {
        JTextField word_target = new JTextField();
        JTextArea word_ex = new JTextArea();
        JScrollPane word_explain = new JScrollPane(word_ex);
        word_explain.setPreferredSize(new Dimension(350, 100));    
        Object[] Word = {
            "Enter Word:",word_target,
            "Enter Word Explain",word_explain,
        };
        int option;
        option = JOptionPane.showConfirmDialog(null,Word, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION){
            Word newWord = management.wordBuilder(word_target.getText(), word_ex.getText());
            int i=management.lookUp(word_target.getText(), dictionary);
            if (i >= 0) {
                JOptionPane.showMessageDialog(null,"WE ALREADY HAVE THIS WORD");
                return;
            }
            management.writeToFile(newWord, "E_V.txt");
            management.writeToFile(newWord, "addedHistory.txt");
            dictionary.pre(newWord);
            added.pre(newWord);
            dictionary.Soort();
            JOptionPane.showMessageDialog(null,"Added");
            listsetMD();
            listsetMA();
            if (jRadioButton1.isSelected()) {
                search(jTextField1.getText());
            }
            if (jRadioButton2.isSelected()) {
                searchSE(jTextField1.getText(), added, modelA);
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BASIX DICTIONARY");
        setBackground(new java.awt.Color(255, 255, 255));

        jList1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);
        jList1.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXX");

        jTextPane1.setEditable(false);
        jTextPane1.setContentType("text/html"); // NOI18N
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextPane1.setDoubleBuffered(true);
        jScrollPane3.setViewportView(jTextPane1);
        DefaultCaret caret = (DefaultCaret) jTextPane1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setToolTipText("");
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setToolTipText("Add");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel1);
        ImageIcon icon = new ImageIcon("add.jpg");
        jLabel1.setIcon(icon);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setToolTipText("Remove");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel3);
        ImageIcon Ricon = new ImageIcon("remove.jpg");
        jLabel3.setIcon(Ricon);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setToolTipText("Speaker");
        add(jLabel4);
        ImageIcon Vicon = new ImageIcon("Speak.jpg");
        jLabel4.setIcon(Vicon);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel5.setText("BASIX DICTIONARY");
        jLabel5.setToolTipText("Sooo BASIX");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(307, 40));

        jTextField1.setToolTipText("Enter your word");
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        add(jLabel2);
        ImageIcon Sicon = new ImageIcon("C:\\Users\\John\\search.jpg");
        jLabel2.setIcon(Sicon);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Dictionary");
        jRadioButton1.setToolTipText("Main Dictionary");
        jRadioButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Added");
        jRadioButton2.setToolTipText("Every added words");
        jRadioButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Removed");
        jRadioButton3.setToolTipText("Every deleted words");
        jRadioButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("History");
        jRadioButton4.setToolTipText("Your look up history");
        jRadioButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Clear History");
        jButton1.setToolTipText("Clear chosen history");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        if(jRadioButton1.isSelected()){
            jButton1.setVisible(false);
        }

        jMenuBar1.setBackground(new java.awt.Color(204, 255, 255));

        jMenu1.setText("File");

        jMenuItem2.setText("Restore");
        jMenuItem2.setToolTipText("Restore the main dictionary");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("About");

        jMenuItem3.setText("About us");
        jMenuItem3.setToolTipText("Don't");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(20, 20, 20))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jButton1))
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //In ra từ chọn
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if(evt.getValueIsAdjusting()){
            if (jRadioButton1.isSelected()) {
                print();
            } else {
                if (jRadioButton2.isSelected()) {
                    printSE(added);
                } else {
                    if (jRadioButton3.isSelected()) {
                        printSE(removed);
                    } else {
                        printSE(history);
                    }
                }
            }
        }
    }//GEN-LAST:event_jList1ValueChanged
    
    // List reloader
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            searchAction();
            return;
        }
        topList = 0;
        String t = jTextField1.getText();
        if (jRadioButton1.isSelected()) {
            search(t);
        } else {
            if (jRadioButton2.isSelected()) {
                searchSE(t, added, modelA);
            } else {
                if (jRadioButton3.isSelected()) {
                    searchSE(t, removed, modelR);
                } else {
                    searchSE(t, history, modelH);
                }
            }
        }
        
        
    }//GEN-LAST:event_jTextField1KeyReleased

    //Thêm từ
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            add();
        } 
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    //TTS SPEAKER
    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            speaker();
        } else {
            if (jRadioButton2.isSelected()) {
                speakerSE(added);
            } else {
                if (jRadioButton3.isSelected()) {
                    speakerSE(removed);
                } else {
                    speakerSE(history);
                }
            }
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            delete();
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

//Thêm từ but trên menu
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(false);
        jTextField1.setText("");
        jTextPane1.setText("<wbr>");
        search("");

    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        jTextField1.setText("");
        jTextPane1.setText("<wbr>");
        searchSE("",added, modelA);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        jTextField1.setText("");
        jTextPane1.setText("<wbr>");
        searchSE("",removed, modelR);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        jTextField1.setText("");
        jTextPane1.setText("<wbr>");
        searchSE("",history, modelH);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        searchAction();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(jRadioButton2.isSelected()){
            try {
                cmd.clearTheFile("addedHistory.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            added.clear();
            modelA.clear();
            searchSE("",added, modelA);
            
        }else if(jRadioButton3.isSelected()){
            try {
                cmd.clearTheFile("removedHistory.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            removed.clear();
            modelR.clear();
            searchSE("",removed, modelR);
        }else if(jRadioButton4.isSelected()){
            try {
                cmd.clearTheFile("History.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            history.clear();
            modelH.clear();
            searchSE("", history, modelH);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        management.restore();
        dictionary = restore;
        listsetMD();
        jTextField1.setText("");
        search("");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        JLabel hyperlink = new JLabel("About us");
        hyperlink.setForeground(Color.BLUE.darker());
        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            // the user clicks on the label
                try {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                hyperlink.setText("<html><a href=''>About us</a></html>");
            }
            public void mouseExited(MouseEvent e) {
                hyperlink.setText("About us");
            }
    });
        JOptionPane.showMessageDialog(null,hyperlink);
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    private void searchAction(){
        if(!jRadioButton1.isSelected()){
            return;
        }
        if(jList1.getModel().getSize() == 0) {
            JTextField word_target = new JTextField();
            JTextArea word_ex = new JTextArea();
            word_target.setText(jTextField1.getText());
            try {
                word_ex.setText(trans.translate("en","vi",jTextField1.getText()));
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            JScrollPane word_explain = new JScrollPane(word_ex);
            word_explain.setPreferredSize(new Dimension(400, 100));    
            Object[] Word = {
            "Word:",word_target,
            "Word Explain",word_explain,
            };
            int option;
            option = JOptionPane.showConfirmDialog(null,Word, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                Word newWord = management.wordBuilder(word_target.getText(), word_ex.getText());
                management.writeToFile(newWord, "E_V.txt");
                management.writeToFile(newWord, "addedHistory.txt");
                dictionary.pre(newWord);
                added.pre(newWord);
                dictionary.Soort();
                listsetMD();
                listsetMA();
                JOptionPane.showMessageDialog(null,"Added");
                search(jTextField1.getText());
            }
        }
        else{
            jList1.clearSelection();
            jList1.setSelectedIndex(0);
            print();
        }
    }
 
//    public void reloadFile(){
//    Dictionary added = management.insertFromFile("addedHistory.txt");
//    Dictionary removed = management.insertFromFile("removedHistory.txt");
//    Dictionary history = management.insertFromFile("History.txt");
//    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Texture look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
          try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        NewJFrame frame = new NewJFrame();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        
        frame.listsetMD();
        frame.listsetMA();
        frame.listsetMH();
        frame.listsetMR();
        frame.listInit();

        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
