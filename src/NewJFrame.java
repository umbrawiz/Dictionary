/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
    public int s ;
    public int e ;
    
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
         for (int i = 0; i < dictionary.wordList.size() ; i++) {
             modelD.addElement(dictionary.getWordTarget(i));
        }
    }
    
    public void setES(Dictionary dict) {
        s=0;
        e=dict.size();
        topList = 0;
    }
    
    public void listInit(Dictionary dict, DefaultListModel modelo) {
        model.clear();
        if (s==0 && e == dict.size()) {
            jList1.setModel(modelo);
            return;
        } else {
            for (int i = s; i < e ; i++) {
                model.addElement(dict.getWordTarget(i));
            }
            jList1.setModel(model);
        }
        
    }


    public void search(String x, Dictionary dict, DefaultListModel modelo) {
        s=0;
        e=dict.size();
        if (x.length() == 0) {
            listInit(dict, modelo);
            return;
        }
        while (s < dict.size()-1) {
            if (dict.getWordTarget(s).indexOf(x) != 0) {
                s++;
            } else {
                break;
            }
            
        }
        e=s;
        topList = s;
        while (e < dict.size()) {
            if (dict.getWordTarget(e).indexOf(x) == 0) {
                e++;
            } else {
                break;
            }
        }
        listInit(dict, modelo);
    }
    
    public void print(Dictionary dict) {
        String t = jTextField1.getText();
        int selectedIndex = jList1.getSelectedIndex() ;
        if (selectedIndex != -1 && topList < dict.size()) {
            if (t.equals("")) {
                jTextPane1.setText(dict.getWordExplain(selectedIndex));
            } else {
                jTextPane1.setText(dict.getWordExplain(topList + selectedIndex));
            }
            management.searchedWord(selectedIndex, dictionary);
        } else {
            jTextPane1.setText("<wbr>");
        }
    }
    
    public void speaker(Dictionary dict) {
        int selectedIndex = jList1.getSelectedIndex() + topList;
        if(selectedIndex < 0) {
            return;
        }
        TTS.setText(dict.wordList.get(selectedIndex).getWord_target());
        TTS.speak();
    }
    
    public void delete() {
        int selectedIndex = jList1.getSelectedIndex() + topList;
        if(selectedIndex < 0) {
            return;
        }
        String currentWord = dictionary.wordList.get(selectedIndex).getWord_target();
        int choice = JOptionPane.showOptionDialog(null, 
        "Are you sure you want to delete '" + currentWord + "' ?", 
        "Delete?", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        if (choice == JOptionPane.YES_OPTION){
            management.removeWord(selectedIndex,dictionary);
            jTextPane1.setText("<wbr>");
            model.removeElement(currentWord);
            modelD.removeElement(currentWord);
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
            management.addWord(word_target.getText(), word_ex.getText(), dictionary);
            dictionary.Soort();
            listsetMD();
            search(jTextField1.getText(), dictionary, modelD);
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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

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

        jTextPane1.setEditable(false);
        jTextPane1.setContentType("text/html"); // NOI18N
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextPane1.setDoubleBuffered(true);
        jScrollPane3.setViewportView(jTextPane1);
        DefaultCaret caret = (DefaultCaret) jTextPane1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel1);
        ImageIcon icon = new ImageIcon("add.jpg");
        jLabel1.setIcon(icon);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel3);
        ImageIcon Ricon = new ImageIcon("remove.jpg");
        jLabel3.setIcon(Ricon);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        add(jLabel4);
        ImageIcon Vicon = new ImageIcon("Speak.jpg");
        jLabel4.setIcon(Vicon);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 204));
        jLabel5.setText("BASIX DICTIONARY");
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

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(307, 40));

        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
                .addGap(23, 23, 23)
                .addComponent(jTextField1)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Dictionary");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Added");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Removed");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("History");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Clear History");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        if(jRadioButton1.isSelected()) {
            jButton1.setVisible(false);
        }

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tools");

        jMenuItem2.setText("Add");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Remove");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Restore");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("About");
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
            return;
        }
        
        if (jRadioButton1.isSelected()) {
            print(dictionary);
        } else {
            if (jRadioButton2.isSelected()) {
                print(added);
            } else {
                if (jRadioButton3.isSelected()) {
                    print(removed);
                } else {
                    print(history);
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
            search(t, dictionary, modelD);
        } else {
            if (jRadioButton2.isSelected()) {
                search(t, added, modelA);
            } else {
                if (jRadioButton3.isSelected()) {
                    search(t, removed, modelR);
                } else {
                    search(t, history, modelH);
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
            speaker(dictionary);
        } else {
            if (jRadioButton2.isSelected()) {
                speaker(added);
            } else {
                if (jRadioButton3.isSelected()) {
                    speaker(removed);
                } else {
                    speaker(history);
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

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed
//Delete tu
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_jMenuItem3ActionPerformed
//Thêm từ but trên menu
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        add();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        setES(dictionary);
        listInit(dictionary, modelD);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        setES(added);
        listInit(added, modelA);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        setES(removed);
        listInit(removed, modelR);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        jButton1.setVisible(true);
        setES(history);
        listInit(history, modelH);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        searchAction();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(jRadioButton2.isSelected()){
            jButton1.setVisible(true);
            try {
                cmd.clearTheFile("addedHistory.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            added = management.insertFromFile("addedHistory.txt");
            modelA.clear();
            
        }else if(jRadioButton3.isSelected()){
            jButton1.setVisible(true);
            try {
                cmd.clearTheFile("removedHistory.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelR.clear();
        }else if(jRadioButton4.isSelected()){
            jButton1.setVisible(true);
            try {
                cmd.clearTheFile("History.txt");
            } catch (IOException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelH.clear();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void searchAction(){
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
                management.addWord(word_target.getText(), word_ex.getText(), dictionary);
                dictionary.Soort();
                listsetMD();
                search(jTextField1.getText(), dictionary, modelD);
            }
        }
        else{
            if(jList1.isSelectionEmpty()){
                jList1.setSelectedIndex(0);
            }
        }
    }
 
    public void reloadFile(){
    Dictionary added = management.insertFromFile("addedHistory.txt");
    Dictionary removed = management.insertFromFile("removedHistory.txt");
    Dictionary history = management.insertFromFile("History.txt");
    }
    
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
        frame.setES(frame.dictionary);
        frame.listInit(frame.dictionary, frame.modelD);

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
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
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
