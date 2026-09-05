    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgSpesialis.java
 *
 * Created on May 23, 2010, 1:25:13 AM
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariTemplateLab;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class MasterCriticalValue extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariTemplateLab masalah=new DlgCariTemplateLab(null,false);

    /** Creates new form DlgSpesialis
     * @param parent
     * @param modal */
    public MasterCriticalValue(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();


        Object[] row={"ID Template","Pemeriksaan","Satuan","Kd Jenis Perawatan","Nama Jenis Perawatan","Nilai Kritis Min","Nilai Kritis Max","Keterangan"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbSpesialis.setModel(tabMode);
        //tampil();
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbSpesialis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSpesialis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbSpesialis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(270);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(150);
            }
        }

        tbSpesialis.setDefaultRenderer(Object.class, new WarnaTable());

       // nmjns.setDocument(new batasInput((byte)3).getKata(nmjns));
        //min.setDocument(new batasInput((int)1000).getKata(min));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        masalah.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(masalah.getTable().getSelectedRow()!= -1){
                    kdmasalah.setText(masalah.getTable().getValueAt(masalah.getTable().getSelectedRow(),0).toString());
                    nmmasalah.setText(masalah.getTable().getValueAt(masalah.getTable().getSelectedRow(),1).toString());
                    sat.setText(masalah.getTable().getValueAt(masalah.getTable().getSelectedRow(),2).toString());
                    kdjns.setText(masalah.getTable().getValueAt(masalah.getTable().getSelectedRow(),3).toString());
                    isCek();
                }  
                btnPemeriksaan.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        masalah.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    masalah.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbSpesialis = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass7 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        nmjns = new widget.TextBox();
        min = new widget.TextBox();
        jLabel5 = new widget.Label();
        kdmasalah = new widget.TextBox();
        nmmasalah = new widget.TextBox();
        btnPemeriksaan = new widget.Button();
        jLabel8 = new widget.Label();
        max = new widget.TextBox();
        jLabel9 = new widget.Label();
        ket = new widget.TextBox();
        sat = new widget.TextBox();
        jLabel10 = new widget.Label();
        kdjns = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Master Critical Value ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbSpesialis.setAutoCreateRowSorter(true);
        tbSpesialis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSpesialis.setName("tbSpesialis"); // NOI18N
        tbSpesialis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSpesialisMouseClicked(evt);
            }
        });
        tbSpesialis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbSpesialisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSpesialisKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbSpesialis);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BtnCariKeyReleased(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 183));
        panelGlass7.setLayout(null);

        jLabel3.setText("Jenis Perawatan  :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass7.add(jLabel3);
        jLabel3.setBounds(0, 70, 100, 23);

        jLabel4.setText("Nilai Kritis MIN :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(20, 100, 80, 23);

        nmjns.setHighlighter(null);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjnsKeyPressed(evt);
            }
        });
        panelGlass7.add(nmjns);
        nmjns.setBounds(200, 70, 260, 23);

        min.setName("min"); // NOI18N
        min.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                minKeyPressed(evt);
            }
        });
        panelGlass7.add(min);
        min.setBounds(110, 100, 220, 23);

        jLabel5.setText("Pemeriksaan :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(10, 10, 90, 23);

        kdmasalah.setEditable(false);
        kdmasalah.setHighlighter(null);
        kdmasalah.setName("kdmasalah"); // NOI18N
        kdmasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmasalahKeyPressed(evt);
            }
        });
        panelGlass7.add(kdmasalah);
        kdmasalah.setBounds(110, 10, 70, 23);

        nmmasalah.setEditable(false);
        nmmasalah.setName("nmmasalah"); // NOI18N
        nmmasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmmasalahKeyPressed(evt);
            }
        });
        panelGlass7.add(nmmasalah);
        nmmasalah.setBounds(180, 10, 345, 23);

        btnPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPemeriksaan.setMnemonic('1');
        btnPemeriksaan.setToolTipText("Alt+1");
        btnPemeriksaan.setName("btnPemeriksaan"); // NOI18N
        btnPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPemeriksaanActionPerformed(evt);
            }
        });
        btnPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPemeriksaanKeyPressed(evt);
            }
        });
        panelGlass7.add(btnPemeriksaan);
        btnPemeriksaan.setBounds(530, 10, 28, 23);

        jLabel8.setText("Nilai Kritis MAX :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass7.add(jLabel8);
        jLabel8.setBounds(350, 100, 80, 23);

        max.setName("max"); // NOI18N
        max.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                maxKeyPressed(evt);
            }
        });
        panelGlass7.add(max);
        max.setBounds(440, 100, 80, 23);

        jLabel9.setText("Keterangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass7.add(jLabel9);
        jLabel9.setBounds(20, 130, 80, 23);

        ket.setName("ket"); // NOI18N
        ket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ketKeyPressed(evt);
            }
        });
        panelGlass7.add(ket);
        ket.setBounds(110, 130, 410, 23);

        sat.setHighlighter(null);
        sat.setName("sat"); // NOI18N
        sat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                satKeyPressed(evt);
            }
        });
        panelGlass7.add(sat);
        sat.setBounds(110, 40, 110, 23);

        jLabel10.setText("Satuan  :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass7.add(jLabel10);
        jLabel10.setBounds(40, 40, 62, 23);

        kdjns.setHighlighter(null);
        kdjns.setName("kdjns"); // NOI18N
        kdjns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjnsKeyPressed(evt);
            }
        });
        panelGlass7.add(kdjns);
        kdjns.setBounds(110, 70, 90, 23);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nmjnsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjnsKeyPressed
        Valid.pindah(evt,TCari,min);
}//GEN-LAST:event_nmjnsKeyPressed

    private void minKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_minKeyPressed
        Valid.pindah(evt,nmjns,BtnSimpan);
}//GEN-LAST:event_minKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(kdmasalah.getText().trim().equals("")){
    Valid.textKosong(kdmasalah,"ID Template");
}else if(nmmasalah.getText().trim().equals("")){
    Valid.textKosong(nmmasalah,"Pemeriksaan");
}else if(min.getText().trim().equals("")){
    Valid.textKosong(min,"Nilai Kritis Min");
}else{
    Sequel.menyimpan(
        "master_critical_value",
        "'" + kdmasalah.getText() + "'," +        
        "'" + nmmasalah.getText() + "'," +
        "'" + kdjns.getText() + "'," +
        "'" + sat.getText() + "'," +
        "'" + min.getText() + "'," +
        "'" + max.getText() + "'," +
        "'" + ket.getText() + "'"
    );

    tampil();
    emptTeks();
}
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,min,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed

       if(Sequel.queryu2tf("delete from master_critical_value where id_template=?",1,new String[]{
            tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbSpesialis.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
     if(kdmasalah.getText().trim().equals("")){
    Valid.textKosong(kdmasalah,"ID Template");
}else if(nmmasalah.getText().trim().equals("")){
    Valid.textKosong(nmmasalah,"Pemeriksaan");
}else if(kdjns.getText().trim().equals("")){
    Valid.textKosong(kdjns,"Kode Jenis Perawatan");
}else if(min.getText().trim().equals("")){
    Valid.textKosong(min,"Nilai Kritis Min");
}else{
    Sequel.mengedittf(
        "master_critical_value",
        "id_template=?",
        "kd_jenis_prw=?,nama_pemeriksaan=?,satuan=?,nilai_min=?,nilai_max=?,keterangan=?",
        7,
        new String[]{
            kdjns.getText(),
            nmmasalah.getText(),
            sat.getText(),
            min.getText(),
            max.getText(),
            ket.getText(),
            kdmasalah.getText()
        }
    );

    tampil();
    emptTeks();
}
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbSpesialis.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyReleased
        // TODO add your handling code here:
}//GEN-LAST:event_BtnCariKeyReleased

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
       emptTeks();
       tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, nmjns);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbSpesialisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpesialisMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbSpesialisMouseClicked

    private void tbSpesialisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSpesialisKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }           
        }
}//GEN-LAST:event_tbSpesialisKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TCari.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void tbSpesialisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSpesialisKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }          
        }
    }//GEN-LAST:event_tbSpesialisKeyReleased

    private void kdmasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmasalahKeyPressed
        Valid.pindah(evt, TCari,nmjns);
    }//GEN-LAST:event_kdmasalahKeyPressed

    private void nmmasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmmasalahKeyPressed
        Valid.pindah(evt,nmjns,BtnSimpan);
    }//GEN-LAST:event_nmmasalahKeyPressed

    private void btnPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPemeriksaanActionPerformed
        masalah.isCek();
        masalah.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        masalah.setLocationRelativeTo(internalFrame1);
        masalah.setVisible(true);
    }//GEN-LAST:event_btnPemeriksaanActionPerformed

    private void btnPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPemeriksaanKeyPressed
        Valid.pindah(evt,kdmasalah,BtnSimpan);
    }//GEN-LAST:event_btnPemeriksaanKeyPressed

    private void maxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxKeyPressed

    private void ketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ketKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ketKeyPressed

    private void satKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_satKeyPressed

    private void kdjnsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjnsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdjnsKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterCriticalValue dialog = new MasterCriticalValue(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Button btnPemeriksaan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdjns;
    private widget.TextBox kdmasalah;
    private widget.TextBox ket;
    private widget.TextBox max;
    private widget.TextBox min;
    private widget.TextBox nmjns;
    private widget.TextBox nmmasalah;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox sat;
    private widget.Table tbSpesialis;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);

    try {
        ps = koneksi.prepareStatement(
            "SELECT master_critical_value.id_template,master_critical_value.nama_pemeriksaan, master_critical_value.kd_jenis_prw, jns_perawatan_lab.nm_perawatan,  " +
            "master_critical_value.satuan, master_critical_value.nilai_min, master_critical_value.nilai_max, master_critical_value.keterangan " +
            "FROM master_critical_value " +
            "INNER JOIN jns_perawatan_lab on master_critical_value.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw  " +
            "WHERE master_critical_value.kd_jenis_prw LIKE ? " +
            "OR master_critical_value.nama_pemeriksaan LIKE ? " +
            "ORDER BY jns_perawatan_lab.nm_perawatan,master_critical_value.nama_pemeriksaan"
        );

         String cari = "%" + TCari.getText() + "%";

        ps.setString(1, cari);
        ps.setString(2, cari);
        

        rs = ps.executeQuery();

        while (rs.next()) {
            tabMode.addRow(new Object[]{
                
                rs.getString("id_template"),
                rs.getString("nama_pemeriksaan"),
                 rs.getString("satuan"),
                rs.getString("kd_jenis_prw"),
                rs.getString("nm_perawatan"),
               
                rs.getString("nilai_min"),
                rs.getString("nilai_max"),
                rs.getString("keterangan")
            });
        }

    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
    }
        LCount.setText(""+tabMode.getRowCount());
    }

    private void emptTeks() {
        kdmasalah.setText("");
        nmmasalah.setText("");
        sat.setText("");
        kdjns.setText("");
        nmjns.setText("");
        min.setText("");
        max.setText("");
        ket.setText("");
        TCari.setText("");
        
        btnPemeriksaan.requestFocus();
       // Valid.autoNomer(tabMode,"",3,kdrencana);
       
        
    }
    
    

    private void getData() {
        if(tbSpesialis.getSelectedRow()!= -1){
            kdmasalah.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 0).toString());
        nmmasalah.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 1).toString());
        sat.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 2).toString());
        kdjns.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 3).toString());
        nmjns.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 4).toString());
        min.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 5).toString());
        max.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 6).toString());
        ket.setText(tbSpesialis.getValueAt(tbSpesialis.getSelectedRow(), 7).toString());
        }
    }
    
    public JTable getTable(){
        return tbSpesialis;
    }
    
    public void isCek(){
    //   BtnSimpan.setEnabled(akses.getmaster_rencana_keperawatan());
    //   BtnHapus.setEnabled(akses.getmaster_rencana_keperawatan());
    //   BtnEdit.setEnabled(akses.getmaster_rencana_keperawatan());
     try {
        if (!kdjns.getText().trim().equals("")) {

            ps = koneksi.prepareStatement(
                "SELECT nm_perawatan " +
                "FROM jns_perawatan_lab " +
                "WHERE kd_jenis_prw=?"
            );

            ps.setString(1, kdjns.getText().trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                nmjns.setText(rs.getString("nm_perawatan"));
            } else {
                nmjns.setText("");
            }

        } else {
            nmjns.setText("");
        }

    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
    }
    }
}
