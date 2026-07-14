/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;
import rekammedis.DlgMasterRencanaKeperawatan;



/**
 *
 * @author perpustakaan
 */
public final class RMTTVBalanceCairan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    public DlgMasterRencanaKeperawatan masterr=new DlgMasterRencanaKeperawatan(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    private SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTTVBalanceCairan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jam();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Input/24 Jam","Urine","Muntah",
            "NGT","IWL","Drain","Jumlah Keluar","Output/24 Jam","Balance Cairan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(90);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(90);
            }else if(i==21){
                column.setPreferredWidth(90);
            }else if(i==22){
                column.setPreferredWidth(90);
            
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","KODE","MASALAH KEPERAWATAN"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };


        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Masuk1.setDocument(new batasInput((byte)5).getKata(Masuk1));
        Masuk2.setDocument(new batasInput((byte)5).getKata(Masuk2));
        Masuk3.setDocument(new batasInput((byte)5).getKata(Masuk3));
        Masuk4.setDocument(new batasInput((byte)5).getKata(Masuk4));
        Masuk5.setDocument(new batasInput((byte)5).getKata(Masuk5));
        Masuk6.setDocument(new batasInput((byte)5).getKata(Masuk6));
        Keluar1.setDocument(new batasInput((byte)5).getKata(Keluar1));
        Keluar2.setDocument(new batasInput((byte)5).getKata(Keluar2));
        Keluar3.setDocument(new batasInput((byte)5).getKata(Keluar3));
        Keluar4.setDocument(new batasInput((byte)5).getKata(Keluar4));
        Keluar5.setDocument(new batasInput((byte)5).getKata(Keluar5));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
                }              
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
                }
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("RMTTVBalanceCairan")){
                    if(pegawai.getTable().getSelectedRow()!= -1){   
  
                        KdPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                        KdPetugas.requestFocus();
                    }        
                }
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
        

        
        masterr.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("RMPenilaianAwalKeperawatanRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        masterr.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        
        
//        ChkAccor.setSelected(false);
//        isMenu();
        FormInput.setFocusTraversalPolicy(new java.awt.FocusTraversalPolicy() {
            java.util.List<java.awt.Component> order = java.util.Arrays.asList(
                TNoRw, Tanggal, CmbJam, CmbMnt, CmbDtk, chkJam, KdPetugas, BtnDokter,
                TNadi, TRespirasi, TSuhu, TTensi, TBB, TTB, TDiet, TKodeInfus, TInterval,
                Masuk1, Masuk2, Masuk3, Masuk4, Masuk5, Masuk6,
                Keluar1, Keluar2, Keluar3, Keluar4, Keluar5,
                BtnSimpan, BtnBatal, BtnEdit, BtnHapus, BtnPrint, BtnKeluar
            );
            public java.awt.Component getComponentAfter(java.awt.Container focusCycleRoot, java.awt.Component aComponent) {
                int idx = (order.indexOf(aComponent) + 1) % order.size();
                return order.get(idx);
            }
            public java.awt.Component getComponentBefore(java.awt.Container focusCycleRoot, java.awt.Component aComponent) {
                int idx = order.indexOf(aComponent) - 1;
                if (idx < 0) idx = order.size() - 1;
                return order.get(idx);
            }
            public java.awt.Component getDefaultComponent(java.awt.Container focusCycleRoot) {
                return order.get(0);
            }
            public java.awt.Component getLastComponent(java.awt.Container focusCycleRoot) {
                return order.get(order.size()-1);
            }
            public java.awt.Component getFirstComponent(java.awt.Container focusCycleRoot) {
                return order.get(0);
            }
        });
        FormInput.setFocusCycleRoot(true);
        
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        
        LNadi = new widget.Label();
        TNadi = new widget.TextBox();
        LRespirasi = new widget.Label();
        TRespirasi = new widget.TextBox();
        LSuhu = new widget.Label();
        TSuhu = new widget.TextBox();
        LTensi = new widget.Label();
        TTensi = new widget.TextBox();
        LBB = new widget.Label();
        TBB = new widget.TextBox();
        LTB = new widget.Label();
        TTB = new widget.TextBox();
        LDiet = new widget.Label();
        TDiet = new widget.TextBox();
        
        
        LInput24 = new widget.Label();
        TInput24 = new widget.TextBox();
        LOutput24 = new widget.Label();
        TOutput24 = new widget.TextBox();
        LBalance24 = new widget.Label();
        TBalance24 = new widget.TextBox();

        LInterval = new widget.Label();
        TInterval = new widget.TextBox();

        LKodeInfus = new widget.Label();
        TKodeInfus = new widget.ComboBox();

        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        // TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        
        
        LTanggal = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMnt = new widget.ComboBox();
        CmbDtk = new widget.ComboBox();
        chkJam = new widget.CekBox();

        LDiagnosa = new widget.Label();
        TDiagnosa = new widget.TextBox();
        LKamar = new widget.Label();
        TKamar = new widget.TextBox();

        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel251 = new widget.Label();
        Masuk1 = new widget.TextBox();
        Masuk2 = new widget.TextBox();
        jLabel253 = new widget.Label();
        JumlahMasuk = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel254 = new widget.Label();
        Keluar1 = new widget.TextBox();
        jLabel255 = new widget.Label();
        Keluar2 = new widget.TextBox();
        jLabel256 = new widget.Label();
        Keluar3 = new widget.TextBox();
        jLabel257 = new widget.Label();
        Keluar4 = new widget.TextBox();
        jLabel258 = new widget.Label();
        Keluar5 = new widget.TextBox();
        jLabel259 = new widget.Label();
        JumlahKeluar = new widget.TextBox();
        jLabel260 = new widget.Label();
        BC = new widget.TextBox();
        BtnEWS2 = new widget.Button();
        Masuk3 = new widget.TextBox();
        Masuk4 = new widget.TextBox();
        Masuk5 = new widget.TextBox();
        jLabel261 = new widget.Label();
        jLabel263 = new widget.Label();
        jLabel262 = new widget.Label();
        jLabel264 = new widget.Label();
        Masuk6 = new widget.TextBox();
        jLabel265 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Balance Cairan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        // TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        // TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        // TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        // TabRawat.setName("TabRawat"); // NOI18N
        // TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
        //     public void mouseClicked(java.awt.event.MouseEvent evt) {
        //         TabRawatMouseClicked(evt);
        //     }
        // });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 470));
        FormInput.setLayout(null);

        LNadi.setText("Nadi :");
        LNadi.setBounds(580, 140, 90, 23);
        FormInput.add(LNadi);
        TNadi.setBounds(680, 140, 100, 23);
        FormInput.add(TNadi);
        
        LRespirasi.setText("Respirasi :");
        LRespirasi.setBounds(580, 170, 90, 23);
        FormInput.add(LRespirasi);
        TRespirasi.setBounds(680, 170, 100, 23);
        FormInput.add(TRespirasi);
        
        LSuhu.setText("Suhu :");
        LSuhu.setBounds(580, 200, 90, 23);
        FormInput.add(LSuhu);
        TSuhu.setBounds(680, 200, 100, 23);
        FormInput.add(TSuhu);
        
        LTensi.setText("Tensi :");
        LTensi.setBounds(580, 230, 90, 23);
        FormInput.add(LTensi);
        TTensi.setBounds(680, 230, 100, 23);
        FormInput.add(TTensi);
        
        LBB.setText("BB :");
        LBB.setBounds(580, 260, 90, 23);
        FormInput.add(LBB);
        TBB.setBounds(680, 260, 100, 23);
        FormInput.add(TBB);
        
        LTB.setText("TB :");
        LTB.setBounds(580, 290, 90, 23);
        FormInput.add(LTB);
        TTB.setBounds(680, 290, 100, 23);
        FormInput.add(TTB);
        
        LDiet.setText("Diet :");
        LDiet.setBounds(580, 320, 90, 23);
        FormInput.add(LDiet);
        TDiet.setBounds(680, 320, 180, 23);
        FormInput.add(TDiet);
        
        LKodeInfus.setText("Kode Infus :");
        LKodeInfus.setBounds(580, 350, 90, 23);
        
        LInterval.setText("Interval/6 Jam :");
        LInterval.setName("LInterval");
        
        LInput24.setText("Jml/24 Jam :");
        LInput24.setName("LInput24");
        FormInput.add(LInput24);
        LInput24.setBounds(50, 350, 100, 23);
        
        TInput24.setName("TInput24");
        TInput24.setHighlighter(null);
        FormInput.add(TInput24);
        TInput24.setBounds(170, 350, 70, 23);
        
        LOutput24.setText("Jml/24 Jam :");
        LOutput24.setName("LOutput24");
        FormInput.add(LOutput24);
        LOutput24.setBounds(350, 320, 100, 23);
        
        TOutput24.setName("TOutput24");
        TOutput24.setHighlighter(null);
        FormInput.add(TOutput24);
        TOutput24.setBounds(450, 320, 70, 23);
        
        LBalance24.setText("Balance/24 Jam :");
        LBalance24.setName("LBalance24");
        FormInput.add(LBalance24);
        LBalance24.setBounds(350, 350, 100, 23);
        
        TBalance24.setName("TBalance24");
        TBalance24.setHighlighter(null);
        FormInput.add(TBalance24);
        TBalance24.setBounds(450, 350, 70, 23);

        FormInput.add(LInterval);
        LInterval.setBounds(580, 380, 90, 23);
        
        TInterval.setName("TInterval");
        TInterval.setHighlighter(null);
        FormInput.add(TInterval);
        TInterval.setBounds(680, 380, 180, 23);

        FormInput.add(LKodeInfus);
        TKodeInfus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TKodeInfus.setName("TKodeInfus"); // NOI18N
        TKodeInfus.setBounds(680, 350, 180, 23);
        FormInput.add(TKodeInfus);


        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(440, 70, 60, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(504, 70, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(608, 70, 210, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(820, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        
        LDiagnosa.setText("Diagnosa :");
        LDiagnosa.setName("LDiagnosa");
        
        LTanggal.setText("Tanggal :");
        LTanggal.setName("LTanggal");
        FormInput.add(LTanggal);
        LTanggal.setBounds(0, 70, 70, 23);
        
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal");
        Tanggal.setOpaque(false);
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 70, 90, 23);
        
        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam");
        FormInput.add(CmbJam);
        CmbJam.setBounds(170, 70, 62, 23);
        
        CmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMnt.setName("CmbMnt");
        FormInput.add(CmbMnt);
        CmbMnt.setBounds(236, 70, 62, 23);
        
        CmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDtk.setName("CmbDtk");
        FormInput.add(CmbDtk);
        CmbDtk.setBounds(302, 70, 62, 23);
        
        chkJam.setText("Jam");
        chkJam.setName("chkJam");
        chkJam.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chkJam.setBorderPainted(true);
        chkJam.setBorderPaintedFlat(true);
        chkJam.setSelected(true);
        FormInput.add(chkJam);
        chkJam.setBounds(370, 70, 60, 23);

        FormInput.add(LDiagnosa);
        LDiagnosa.setBounds(0, 40, 70, 23);
        
        TDiagnosa.setEditable(false);
        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa");
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(74, 40, 330, 23);
        
        LKamar.setText("Kamar :");
        LKamar.setName("LKamar");
        FormInput.add(LKamar);
        LKamar.setBounds(410, 40, 60, 23);
        
        TKamar.setEditable(false);
        TKamar.setHighlighter(null);
        TKamar.setName("TKamar");
        FormInput.add(TKamar);
        TKamar.setBounds(474, 40, 380, 23);

        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("BALANCE CAIRAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(20, 90, 380, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("MASUK");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(40, 120, 50, 23);

        jLabel251.setText("Makan : ");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(70, 140, 100, 23);

        Masuk1.setFocusTraversalPolicyProvider(true);
        Masuk1.setName("Masuk1"); // NOI18N
        Masuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk1KeyReleased(evt);
            }
        });
        FormInput.add(Masuk1);
        Masuk1.setBounds(170, 140, 70, 23);

        Masuk2.setFocusTraversalPolicyProvider(true);
        Masuk2.setName("Masuk2"); // NOI18N
        Masuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk2KeyReleased(evt);
            }
        });
        FormInput.add(Masuk2);
        Masuk2.setBounds(170, 170, 70, 23);

        jLabel253.setText("Jumlah : ");
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(70, 320, 100, 23);

        JumlahMasuk.setFocusTraversalPolicyProvider(true);
        JumlahMasuk.setName("JumlahMasuk"); // NOI18N
        JumlahMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahMasukKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JumlahMasukKeyReleased(evt);
            }
        });
        FormInput.add(JumlahMasuk);
        JumlahMasuk.setBounds(170, 320, 70, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("KELUAR");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(320, 110, 60, 23);

        jLabel254.setText("Urine : ");
        jLabel254.setName("jLabel254"); // NOI18N
        FormInput.add(jLabel254);
        jLabel254.setBounds(350, 140, 100, 23);

        Keluar1.setFocusTraversalPolicyProvider(true);
        Keluar1.setName("Keluar1"); // NOI18N
        Keluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keluar1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Keluar1KeyReleased(evt);
            }
        });
        FormInput.add(Keluar1);
        Keluar1.setBounds(450, 140, 70, 23);

        jLabel255.setText("Muntah : ");
        jLabel255.setName("jLabel255"); // NOI18N
        FormInput.add(jLabel255);
        jLabel255.setBounds(350, 170, 100, 23);

        Keluar2.setFocusTraversalPolicyProvider(true);
        Keluar2.setName("Keluar2"); // NOI18N
        Keluar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keluar2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Keluar2KeyReleased(evt);
            }
        });
        FormInput.add(Keluar2);
        Keluar2.setBounds(450, 170, 70, 23);

        jLabel256.setText("NGT : ");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(350, 200, 100, 23);

        Keluar3.setFocusTraversalPolicyProvider(true);
        Keluar3.setName("Keluar3"); // NOI18N
        Keluar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keluar3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Keluar3KeyReleased(evt);
            }
        });
        FormInput.add(Keluar3);
        Keluar3.setBounds(450, 200, 70, 23);

        jLabel257.setText("IWL : ");
        jLabel257.setName("jLabel257"); // NOI18N
        FormInput.add(jLabel257);
        jLabel257.setBounds(350, 230, 100, 23);

        Keluar4.setFocusTraversalPolicyProvider(true);
        Keluar4.setName("Keluar4"); // NOI18N
        Keluar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keluar4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Keluar4KeyReleased(evt);
            }
        });
        FormInput.add(Keluar4);
        Keluar4.setBounds(450, 230, 70, 23);

        jLabel258.setText("Drain : ");
        jLabel258.setName("jLabel258"); // NOI18N
        FormInput.add(jLabel258);
        jLabel258.setBounds(350, 260, 100, 23);

        Keluar5.setFocusTraversalPolicyProvider(true);
        Keluar5.setName("Keluar5"); // NOI18N
        Keluar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keluar5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Keluar5KeyReleased(evt);
            }
        });
        FormInput.add(Keluar5);
        Keluar5.setBounds(450, 260, 70, 23);

        jLabel259.setText("Jumlah : ");
        jLabel259.setName("jLabel259"); // NOI18N
        FormInput.add(jLabel259);
        jLabel259.setBounds(350, 290, 100, 23);

        JumlahKeluar.setFocusTraversalPolicyProvider(true);
        JumlahKeluar.setName("JumlahKeluar"); // NOI18N
        JumlahKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKeluarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JumlahKeluarKeyReleased(evt);
            }
        });
        FormInput.add(JumlahKeluar);
        JumlahKeluar.setBounds(450, 290, 70, 23);

        jLabel260.setText("Balance Cairan : ");
        jLabel260.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel260.setName("jLabel260"); // NOI18N
        // FormInput.add(jLabel260);
        jLabel260.setBounds(50, 550, 120, 23);

        BC.setEditable(false);
        BC.setFocusTraversalPolicyProvider(true);
        BC.setName("BC"); // NOI18N
        BC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BCKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BCKeyReleased(evt);
            }
        });
        // FormInput.add(BC);
        BC.setBounds(170, 550, 70, 23);

        BtnEWS2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/curve.png"))); // NOI18N
        BtnEWS2.setMnemonic('K');
        BtnEWS2.setText("Grafik Balance Cairan");
        BtnEWS2.setToolTipText("");
        BtnEWS2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEWS2.setName("BtnEWS2"); // NOI18N
        BtnEWS2.setPreferredSize(new java.awt.Dimension(160, 30));
        BtnEWS2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEWS2ActionPerformed(evt);
            }
        });
        BtnEWS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEWS2KeyPressed(evt);
            }
        });
        FormInput.add(BtnEWS2);
        BtnEWS2.setBounds(580, 420, 190, 30);

        Masuk3.setFocusTraversalPolicyProvider(true);
        Masuk3.setName("Masuk3"); // NOI18N
        Masuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk3KeyReleased(evt);
            }
        });
        FormInput.add(Masuk3);
        Masuk3.setBounds(170, 200, 70, 23);

        Masuk4.setFocusTraversalPolicyProvider(true);
        Masuk4.setName("Masuk4"); // NOI18N
        Masuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk4KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk4KeyReleased(evt);
            }
        });
        FormInput.add(Masuk4);
        Masuk4.setBounds(170, 230, 70, 23);

        Masuk5.setFocusTraversalPolicyProvider(true);
        Masuk5.setName("Masuk5"); // NOI18N
        Masuk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk5KeyReleased(evt);
            }
        });
        FormInput.add(Masuk5);
        Masuk5.setBounds(170, 260, 70, 23);

        jLabel261.setText("NGT : ");
        jLabel261.setName("jLabel261"); // NOI18N
        FormInput.add(jLabel261);
        jLabel261.setBounds(70, 200, 100, 23);

        jLabel263.setText("Sisa Infus : ");
        jLabel263.setName("jLabel263"); // NOI18N
        FormInput.add(jLabel263);
        jLabel263.setBounds(70, 290, 100, 23);

        jLabel262.setText("Minum : ");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(70, 170, 100, 23);

        jLabel264.setText("Transfusi : ");
        jLabel264.setName("jLabel264"); // NOI18N
        FormInput.add(jLabel264);
        jLabel264.setBounds(70, 230, 100, 23);

        Masuk6.setFocusTraversalPolicyProvider(true);
        Masuk6.setName("Masuk6"); // NOI18N
        Masuk6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masuk6KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Masuk6KeyReleased(evt);
            }
        });
        FormInput.add(Masuk6);
        Masuk6.setBounds(170, 290, 70, 23);

        jLabel265.setText("Infus : ");
        jLabel265.setName("jLabel265"); // NOI18N
        FormInput.add(jLabel265);
        jLabel265.setBounds(70, 260, 100, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame2.setPreferredSize(new java.awt.Dimension(1024, 490));
        internalFrame1.add(internalFrame2, java.awt.BorderLayout.PAGE_START);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-07-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-07-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(internalFrame3, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"NIP Petugas");
        
        
        }else{
            if(Sequel.menyimpantf("rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",29,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMnt.getSelectedItem()+":"+CmbDtk.getSelectedItem(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getSelectedItem().toString(),TInterval.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),TInput24.getText(),Keluar1.getText(),Keluar2.getText(),
                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),TOutput24.getText(),TBalance24.getText(),KdPetugas.getText()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Masuk1,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.queryu2tf("delete from rm_ttv_balance_cairan where tanggal=? and jam=? and no_rawat=?",3,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"NIP Petugas");
        
        
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,jumlah_input_24=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,jumlah_output_24=?,balance_24=?,nik=?",32,new String[]{
                    TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMnt.getSelectedItem()+":"+CmbDtk.getSelectedItem(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getSelectedItem().toString(),TInterval.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),TInput24.getText(),Keluar1.getText(),Keluar2.getText(),
                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),TOutput24.getText(),TBalance24.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                       tampil();
                       emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                java.util.Map<String, Object> param = new java.util.HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                param.put("diagnosa", TDiagnosa.getText());
                param.put("ruang", TKamar.getText());
                param.put("norm", TNoRM.getText());
                param.put("nama", TPasien.getText());
                param.put("tgl_lahir", TglLahir.getText());
                param.put("jk", Jk.getText());

                // Fetch all data
                java.util.List<String[]> dataList = new java.util.ArrayList<>();
                ps = koneksi.prepareStatement(
                        "select tanggal, jam, tensi, bb, tb, diet, kode_infus, " +
                        "nadi, respirasi, suhu, " +
                        "intake_makan, intake_minum, intake_ngt, intake_transfusi, intake_infus, intake_sisa_infus, " +
                        "jumlah_input, jumlah_input_24, output_urine, output_muntah, output_ngt, output_iwl, output_drain, " +
                        "jumlah_output, jumlah_output_24, balance_24 " +
                        "from rm_ttv_balance_cairan where no_rawat=? order by tanggal, jam");
                try {
                    ps.setString(1, TNoRw.getText());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        String[] arr = new String[26];
                        arr[0] = rs.getString("tanggal");
                        arr[1] = rs.getString("jam");
                        arr[2] = rs.getString("tensi");
                        String bb = rs.getString("bb"); String tb = rs.getString("tb");
                        arr[3] = (bb != null ? bb : "") + " / " + (tb != null ? tb : "");
                        arr[4] = rs.getString("diet");
                        arr[5] = rs.getString("kode_infus");
                        arr[6] = rs.getString("nadi");
                        arr[7] = rs.getString("respirasi");
                        arr[8] = rs.getString("suhu");
                        arr[9] = rs.getString("intake_makan");
                        arr[10] = rs.getString("intake_minum");
                        arr[11] = rs.getString("intake_ngt");
                        arr[12] = rs.getString("intake_transfusi");
                        arr[13] = rs.getString("intake_infus");
                        arr[14] = rs.getString("intake_sisa_infus");
                        arr[15] = rs.getString("jumlah_input");
                        arr[16] = rs.getString("jumlah_input_24");
                        arr[17] = rs.getString("output_urine");
                        arr[18] = rs.getString("output_muntah");
                        arr[19] = rs.getString("output_ngt");
                        arr[20] = rs.getString("output_iwl");
                        arr[21] = rs.getString("output_drain");
                        arr[22] = rs.getString("jumlah_output");
                        arr[23] = rs.getString("jumlah_output_24");
                        arr[24] = rs.getString("balance_24");
                        dataList.add(arr);
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }

                javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
                model.addColumn("page_number");
                model.addColumn("kelompok");
                model.addColumn("row_label");
                for (int i=1; i<=12; i++) model.addColumn("col"+i);
                model.addColumn("chart_image"); // Pass the chart image

                int recordsPerPage = 12;
                int totalPages = (int) Math.ceil((double)dataList.size() / recordsPerPage);
                if (totalPages == 0) totalPages = 1;

                for (int p=0; p<totalPages; p++) {
                    int pageNum = p + 1;
                    int startIdx = p * recordsPerPage;

                    // --- GENERATE CHART FOR THIS PAGE ---
                    org.jfree.data.category.DefaultCategoryDataset nadiDataset = new org.jfree.data.category.DefaultCategoryDataset();
                    org.jfree.data.category.DefaultCategoryDataset respDataset = new org.jfree.data.category.DefaultCategoryDataset();
                    org.jfree.data.category.DefaultCategoryDataset suhuDataset = new org.jfree.data.category.DefaultCategoryDataset();

                    double[] nadiArr = new double[12];
                    double[] respArr = new double[12];
                    double[] suhuArr = new double[12];
                    final boolean[] nadiValid = new boolean[12];
                    final boolean[] respValid = new boolean[12];
                    final boolean[] suhuValid = new boolean[12];

                    for (int c=0; c<12; c++) {
                        int di = startIdx + c;
                        nadiArr[c] = -1; respArr[c] = -1; suhuArr[c] = -1;
                        if (di < dataList.size()) {
                            String[] d = dataList.get(di);
                            try { nadiArr[c] = Double.parseDouble(d[6]); nadiValid[c] = true; } catch(Exception e) {}
                            try { respArr[c] = Double.parseDouble(d[7]); respValid[c] = true; } catch(Exception e) {}
                            try { suhuArr[c] = Double.parseDouble(d[8]); suhuValid[c] = true; } catch(Exception e) {}
                        }
                    }

                    class Interpolator {
                        void process(double[] arr, boolean[] valid) {
                            int lastValid = -1;
                            for (int c=0; c<12; c++) {
                                if (valid[c]) {
                                    if (lastValid != -1 && lastValid < c - 1) {
                                        double y1 = arr[lastValid];
                                        double y2 = arr[c];
                                        for (int i = lastValid + 1; i < c; i++) {
                                            arr[i] = y1 + (y2 - y1) * (i - lastValid) / (c - lastValid);
                                        }
                                    }
                                    lastValid = c;
                                }
                            }
                        }
                    }
                    Interpolator interp = new Interpolator();
                    interp.process(nadiArr, nadiValid);
                    interp.process(respArr, respValid);
                    interp.process(suhuArr, suhuValid);

                    for (int c=0; c<12; c++) {
                        String cat = "C" + c;
                        if (nadiArr[c] != -1) nadiDataset.addValue(nadiArr[c], "Nadi", cat); else nadiDataset.addValue(null, "Nadi", cat);
                        if (respArr[c] != -1) respDataset.addValue(respArr[c], "Respirasi", cat); else respDataset.addValue(null, "Respirasi", cat);
                        if (suhuArr[c] != -1) suhuDataset.addValue(suhuArr[c], "Suhu", cat); else suhuDataset.addValue(null, "Suhu", cat);
                    }

                    // Create chart (transparent)
                    org.jfree.chart.plot.CategoryPlot plot = new org.jfree.chart.plot.CategoryPlot();
                    
                    // Renderer for Nadi (Red Square)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendNadi = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return nadiValid[item];
                        }
                    };
                    rendNadi.setSeriesPaint(0, java.awt.Color.RED);
                    rendNadi.setSeriesShape(0, new java.awt.geom.Rectangle2D.Double(-3, -3, 6, 6));
                    plot.setDataset(0, nadiDataset);
                    plot.setRenderer(0, rendNadi);

                    // Renderer for Respirasi (Black Circle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendResp = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return respValid[item];
                        }
                    };
                    rendResp.setSeriesPaint(0, java.awt.Color.BLACK);
                    rendResp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));
                    plot.setDataset(1, respDataset);
                    plot.setRenderer(1, rendResp);

                    // Renderer for Suhu (Blue Triangle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendSuhu = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return suhuValid[item];
                        }
                    };
                    rendSuhu.setSeriesPaint(0, java.awt.Color.BLUE);
                    java.awt.geom.GeneralPath triangle = new java.awt.geom.GeneralPath();
                    triangle.moveTo(0, -4); triangle.lineTo(4, 4); triangle.lineTo(-4, 4); triangle.closePath();
                    rendSuhu.setSeriesShape(0, triangle);
                    plot.setDataset(2, suhuDataset);
                    plot.setRenderer(2, rendSuhu);

                    // Axes (Hidden text, but explicit ranges for perfectly matched gridlines)
                    org.jfree.chart.axis.CategoryAxis domainAxis = new org.jfree.chart.axis.CategoryAxis();
                    domainAxis.setTickLabelsVisible(false);
                    domainAxis.setTickMarksVisible(false);
                    domainAxis.setAxisLineVisible(false);
                    // VERY IMPORTANT: Margin 0 so the first/last category fills exactly its 1/12th width box!
                    domainAxis.setLowerMargin(0.0);
                    domainAxis.setUpperMargin(0.0);
                    plot.setDomainAxis(domainAxis);

                    org.jfree.chart.axis.NumberAxis axisNadi = new org.jfree.chart.axis.NumberAxis();
                    axisNadi.setRange(40.0, 160.0);
                    axisNadi.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(20.0));
                    axisNadi.setTickLabelsVisible(false);
                    axisNadi.setTickMarksVisible(false);
                    axisNadi.setAxisLineVisible(false);
                    plot.setRangeAxis(0, axisNadi);
                    plot.mapDatasetToRangeAxis(0, 0);

                    org.jfree.chart.axis.NumberAxis axisResp = new org.jfree.chart.axis.NumberAxis();
                    axisResp.setRange(0.0, 60.0);
                    axisResp.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(10.0));
                    axisResp.setTickLabelsVisible(false);
                    axisResp.setTickMarksVisible(false);
                    axisResp.setAxisLineVisible(false);
                    plot.setRangeAxis(1, axisResp);
                    plot.mapDatasetToRangeAxis(1, 1);

                    org.jfree.chart.axis.NumberAxis axisSuhu = new org.jfree.chart.axis.NumberAxis();
                    axisSuhu.setRange(35.0, 41.0);
                    axisSuhu.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1.0));
                    axisSuhu.setTickLabelsVisible(false);
                    axisSuhu.setTickMarksVisible(false);
                    axisSuhu.setAxisLineVisible(false);
                    plot.setRangeAxis(2, axisSuhu);
                    plot.mapDatasetToRangeAxis(2, 2);

                    // Transparent background!
                    plot.setBackgroundPaint(new java.awt.Color(0,0,0,0));
                    plot.setOutlineVisible(false);
                    
                    // Gridlines
                    plot.setRangeGridlinesVisible(true);
                    plot.setRangeGridlinePaint(new java.awt.Color(200,200,200));
                    plot.setDomainGridlinesVisible(false);

                    org.jfree.chart.JFreeChart chart = new org.jfree.chart.JFreeChart(plot);
                    chart.setBackgroundPaint(new java.awt.Color(0,0,0,0));
                    chart.removeLegend();

                    // Generate image
                    java.awt.image.BufferedImage chartImg = chart.createBufferedImage(672, 120, java.awt.image.BufferedImage.TYPE_INT_ARGB, null);

                    // --- POPULATE TABLE MODEL ---
                    String[] headerLabels = {"Tanggal", "Jam", "Kode Infus/transf."};
                    int[] headerIdx = {0, 1, 5};
                    for (int r=0; r<headerLabels.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "HEAD";
                        rowData[2] = headerLabels[r];
                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            rowData[3+c] = (di < dataList.size()) ? dataList.get(di)[headerIdx[r]] : "";
                        }
                        rowData[15] = chartImg;
                        model.addRow(rowData);
                    }

                    // ADD THE CHART PLACEHOLDER ROW
                    Object[] chartRow = new Object[16];
                    chartRow[0] = pageNum;
                    chartRow[1] = "CHART";
                    chartRow[2] = "CHART";
                    chartRow[15] = chartImg;
                    model.addRow(chartRow);

                    // --- DATA ROWS (TD, BB/TB, Diet, Intake, Output) ---
                    String[] dataLabels = {
                        "TD", "BB / TB", "Diet", "Interval/6 jam :",
                        "    Makan", "    Minum", "    NGT", "    Transfusi", "    Infus", "    Sisa Infus",
                        "Jumlah Input", "Jumlah input/24 jam",
                        "OUTPUT",
                        "    Urine", "    Muntah", "    NGT ", "    IWL", "    Drain",
                        "Jumlah Output", "Jumlah Output/24 Jam", "Jumlah Total/24 Jam"
                    };
                    int[] dataIdx = {
                        2, 3, 4, -1,
                        9, 10, 11, 12, 13, 14,
                        15, 16,
                        -1,
                        17, 18, 19, 20, 21,
                        22, 23, 24
                    };

                    for (int r=0; r<dataLabels.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "DATA";
                        rowData[2] = dataLabels[r];
                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            if (di < dataList.size()) {
                                if (dataIdx[r] == -1) {
                                    rowData[3+c] = "";
                                } else {
                                    String val = dataList.get(di)[dataIdx[r]];
                                    rowData[3+c] = (val != null ? val : "-");
                                }
                            } else {
                                rowData[3+c] = "";
                            }
                        }
                        rowData[15] = chartImg;
                        model.addRow(rowData);
                    }
                }

                Valid.MyReport("./report/rptObservasiTTVBalance.jasper", param, new net.sf.jasperreports.engine.data.JRTableModelDataSource(model));
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
//                ChkAccor.setSelected(true);
//                isMenu();
//                getMasalah();
                getData();
//                // TabRawat.setSelectedIndex(0);
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
//                    ChkAccor.setSelected(true);
//                    isMenu();
//                    getMasalah();
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    // TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
//        pegawai.isCek();
        akses.setform("RMTTVBalanceCairan");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    private void Masuk1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk1KeyReleased
        isMasuk();
    }//GEN-LAST:event_Masuk1KeyReleased

    private void Masuk2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk2KeyReleased
        isMasuk();
    }//GEN-LAST:event_Masuk2KeyReleased

    private void JumlahMasukKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahMasukKeyReleased
        isBc();
    }//GEN-LAST:event_JumlahMasukKeyReleased

    private void Keluar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar1KeyReleased
        isKeluar();
    }//GEN-LAST:event_Keluar1KeyReleased

    private void Keluar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar2KeyReleased
        isKeluar();
    }//GEN-LAST:event_Keluar2KeyReleased

    private void Keluar3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar3KeyReleased
        isKeluar();
    }//GEN-LAST:event_Keluar3KeyReleased

    private void Keluar4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar4KeyReleased
        isKeluar();
    }//GEN-LAST:event_Keluar4KeyReleased

    private void Keluar5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar5KeyReleased
        
    }//GEN-LAST:event_Keluar5KeyReleased

    private void JumlahKeluarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeluarKeyReleased
        isBc();
    }//GEN-LAST:event_JumlahKeluarKeyReleased

    private void BCKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BCKeyReleased
       
    }//GEN-LAST:event_BCKeyReleased

    private void Masuk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk1KeyPressed
        
    }//GEN-LAST:event_Masuk1KeyPressed

    private void Masuk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk2KeyPressed
        
    }//GEN-LAST:event_Masuk2KeyPressed

    private void JumlahMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahMasukKeyPressed
      
    }//GEN-LAST:event_JumlahMasukKeyPressed

    private void Keluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar1KeyPressed
        
    }//GEN-LAST:event_Keluar1KeyPressed

    private void Keluar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar2KeyPressed
        
    }//GEN-LAST:event_Keluar2KeyPressed

    private void Keluar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar3KeyPressed
        
    }//GEN-LAST:event_Keluar3KeyPressed

    private void Keluar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar4KeyPressed
        
    }//GEN-LAST:event_Keluar4KeyPressed

    private void Keluar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keluar5KeyPressed
        
    }//GEN-LAST:event_Keluar5KeyPressed

    private void JumlahKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeluarKeyPressed
        
    }//GEN-LAST:event_JumlahKeluarKeyPressed

    private void BCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BCKeyPressed
        isBc();
    }//GEN-LAST:event_BCKeyPressed

    private void BtnEWS2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEWS2ActionPerformed
        if(TNoRw.getText().trim().equals("") ){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            GrafikBalanceCairanRanap cairan=new GrafikBalanceCairanRanap(null,false);
            cairan.setNoRawat(TNoRw.getText(),TNoRw.getText());
            cairan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            cairan.setLocationRelativeTo(internalFrame1);
            cairan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }           // TODO add your handling code here:
    }//GEN-LAST:event_BtnEWS2ActionPerformed

    private void BtnEWS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEWS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEWS2KeyPressed

    private void Masuk3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk3KeyPressed
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk3KeyPressed

    private void Masuk3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk3KeyReleased
        isMasuk();        // TODO add your handling code here:
    }//GEN-LAST:event_Masuk3KeyReleased

    private void Masuk4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk4KeyPressed
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk4KeyPressed

    private void Masuk4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk4KeyReleased
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk4KeyReleased

    private void Masuk5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk5KeyPressed
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk5KeyPressed

    private void Masuk5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk5KeyReleased
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk5KeyReleased

    private void Masuk6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk6KeyPressed
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk6KeyPressed

    private void Masuk6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masuk6KeyReleased
        isMasuk();// TODO add your handling code here:
    }//GEN-LAST:event_Masuk6KeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTTVBalanceCairan dialog = new RMTTVBalanceCairan(new javax.swing.JFrame(), true);
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
    private widget.TextBox BC;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEWS2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox JumlahKeluar;
    private widget.TextBox JumlahMasuk;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keluar1;
    private widget.TextBox Keluar2;
    private widget.TextBox Keluar3;
    private widget.TextBox Keluar4;
    private widget.TextBox Keluar5;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox Masuk1;
    private widget.TextBox Masuk2;
    private widget.TextBox Masuk3;
    private widget.TextBox Masuk4;
    private widget.TextBox Masuk5;
    private widget.TextBox Masuk6;
    private widget.TextBox NmPetugas;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    
    
    private widget.Label LTanggal;
    private widget.Tanggal Tanggal;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMnt;
    private widget.ComboBox CmbDtk;
    private widget.CekBox chkJam;
private widget.Label LDiagnosa;
    private widget.TextBox TDiagnosa;
    private widget.Label LKamar;
    private widget.TextBox TKamar;
private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel251;
    private widget.Label jLabel253;
    private widget.Label jLabel254;
    private widget.Label jLabel255;
    private widget.Label jLabel256;
    private widget.Label jLabel257;
    private widget.Label jLabel258;
    private widget.Label jLabel259;
    private widget.Label jLabel260;
    private widget.Label jLabel261;
    private widget.Label jLabel262;
    private widget.Label jLabel263;
    private widget.Label jLabel264;
    private widget.Label jLabel265;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi,rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb,rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.interval_waktu,rm_ttv_balance_cairan.nik,pegawai.nama,"+
                        
                        "rm_ttv_balance_cairan.intake_makan,rm_ttv_balance_cairan.intake_minum,rm_ttv_balance_cairan.intake_ngt,rm_ttv_balance_cairan.intake_transfusi,rm_ttv_balance_cairan.intake_infus,rm_ttv_balance_cairan.intake_sisa_infus,rm_ttv_balance_cairan.jumlah_input,rm_ttv_balance_cairan.jumlah_input_24,"+
                        "rm_ttv_balance_cairan.output_urine,rm_ttv_balance_cairan.output_muntah,rm_ttv_balance_cairan.output_ngt,rm_ttv_balance_cairan.output_iwl,rm_ttv_balance_cairan.output_drain,rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance_24 "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join rm_ttv_balance_cairan on reg_periksa.no_rawat=rm_ttv_balance_cairan.no_rawat "+
                        "inner join pegawai on rm_ttv_balance_cairan.nik=pegawai.nik "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? order by rm_ttv_balance_cairan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi,rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb,rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.interval_waktu,rm_ttv_balance_cairan.nik,pegawai.nama,"+
                        
                        "rm_ttv_balance_cairan.intake_makan,rm_ttv_balance_cairan.intake_minum,rm_ttv_balance_cairan.intake_ngt,rm_ttv_balance_cairan.intake_transfusi,rm_ttv_balance_cairan.intake_infus,rm_ttv_balance_cairan.intake_sisa_infus,rm_ttv_balance_cairan.jumlah_input,rm_ttv_balance_cairan.jumlah_input_24,"+
                        "rm_ttv_balance_cairan.output_urine,rm_ttv_balance_cairan.output_muntah,rm_ttv_balance_cairan.output_ngt,rm_ttv_balance_cairan.output_iwl,rm_ttv_balance_cairan.output_drain,rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance_24 "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join rm_ttv_balance_cairan on reg_periksa.no_rawat=rm_ttv_balance_cairan.no_rawat "+
                        "inner join pegawai on rm_ttv_balance_cairan.nik=pegawai.nik "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? and rm_ttv_balance_cairan.nik like ? or "+
                        "rm_ttv_balance_cairan.tanggal between ? and ? and pegawai.nama like ? order by rm_ttv_balance_cairan.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nik"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("jam"),rs.getString("nadi"),rs.getString("respirasi"),rs.getString("suhu"),rs.getString("tensi"),rs.getString("bb"),rs.getString("tb"),rs.getString("diet"),rs.getString("kode_infus"),rs.getString("interval_waktu"),
                        rs.getString("intake_makan"),rs.getString("intake_minum"),rs.getString("intake_ngt"),rs.getString("intake_transfusi"),rs.getString("intake_infus"),rs.getString("intake_sisa_infus"),rs.getString("jumlah_input"),rs.getString("jumlah_input_24"),
                        rs.getString("output_urine"),rs.getString("output_muntah"),rs.getString("output_ngt"),rs.getString("output_iwl"),rs.getString("output_drain"),rs.getString("jumlah_output"),rs.getString("jumlah_output_24"),rs.getString("balance_24")
                     });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    

    
    private void pernafasan(){

        isTotalKlasifikasi();
        isTotalRespon();
        isTotalTindakan();
        isTotalFrekuensi();
    }
    
    private void saturasi(){

        isTotalKlasifikasi();
        isTotalRespon();
        isTotalTindakan();
        isTotalFrekuensi();
    }
    
    private void suhu(){

        isTotalKlasifikasi();
        isTotalRespon();
        isTotalTindakan();
        isTotalFrekuensi();
    }
    
    private void denyut(){

        isTotalKlasifikasi();
        isTotalRespon();
        isTotalTindakan();
        isTotalFrekuensi();
    }
    
    private void tekanan(){

        isTotalKlasifikasi();
        isTotalRespon();
        isTotalTindakan();
        isTotalFrekuensi();
    }
    
    private void isTotalKlasifikasi(){}

    
    private void isTotalRespon(){}

    
    private void isTotalTindakan(){}

    
    private void hitung24Jam() {
        // Kalkulasi otomatis dinonaktifkan karena permintaan input manual
    }

    private void isTotalFrekuensi(){}

    
    private void isMasuk(){
        isBc();
    }
    
    private void isKeluar(){
        isBc();
    }
    
    private void isBc(){
        hitung24Jam();
    }
    


    public void emptTeks() {
        Tanggal.setDate(new java.util.Date());
        chkJam.setSelected(true);
        TNadi.setText("");
        TRespirasi.setText("");
        TSuhu.setText("");
        TTensi.setText("");
        TBB.setText("");
        TTB.setText("");
        TDiet.setText("");
        TKodeInfus.setSelectedIndex(0);
        TInterval.setText("");
        Masuk1.setText("");
        Masuk2.setText("");
        Masuk3.setText("");
        Masuk4.setText("");
        Masuk5.setText("");
        Masuk6.setText("");
        JumlahMasuk.setText("0");
        TInput24.setText("0");
        Keluar1.setText("");
        Keluar2.setText("");
        Keluar3.setText("");
        Keluar4.setText("");
        Keluar5.setText("");
        JumlahKeluar.setText("0");
        TOutput24.setText("0");
        TBalance24.setText("0");
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            
                        Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            CmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(0,2));
            CmbMnt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(3,5));
            CmbDtk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(6,8));

            TNadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TRespirasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            TSuhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TTensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            TBB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TTB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            TDiet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
                        TKodeInfus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TInterval.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            
            Masuk1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Masuk2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Masuk3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Masuk4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Masuk5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Masuk6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
                        JumlahMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            TInput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Keluar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Keluar2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Keluar3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Keluar4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Keluar5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
                        JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            TOutput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            TBalance24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());

        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(chkJam.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(chkJam.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMnt.getSelectedIndex();
                    nilai_detik =CmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                CmbJam.setSelectedItem(jam);
                CmbMnt.setSelectedItem(menit);
                CmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        
        TDiagnosa.setText("");
        TKamar.setText("");
        try {
            ps=koneksi.prepareStatement(
                    "select kamar_inap.diagnosa_awal, bangsal.nm_bangsal from kamar_inap "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TDiagnosa.setText(rs.getString("diagnosa_awal"));
                    TKamar.setText(rs.getString("nm_bangsal"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        isPsien();
        tampil();
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NmPetugas,KdPetugas.getText());

        }            
    }

    public void setTampil(){
       // TabRawat.setSelectedIndex(1);
       tampil();
    }
    


    private widget.Label LNadi;
    private widget.TextBox TNadi;
    private widget.Label LRespirasi;
    private widget.TextBox TRespirasi;
    private widget.Label LSuhu;
    private widget.TextBox TSuhu;
    private widget.Label LTensi;
    private widget.TextBox TTensi;
    private widget.Label LBB;
    private widget.TextBox TBB;
    private widget.Label LTB;
    private widget.TextBox TTB;
    private widget.Label LDiet;
    private widget.TextBox TDiet;
    
    
    private widget.Label LInput24;
    private widget.TextBox TInput24;
    private widget.Label LOutput24;
    private widget.TextBox TOutput24;
    private widget.Label LBalance24;
    private widget.TextBox TBalance24;

private widget.Label LInterval;
    private widget.TextBox TInterval;

private widget.Label LKodeInfus;
    private widget.ComboBox TKodeInfus;

    


    public void setTTVFromPonek() {
        if (Sequel.cariInteger("select count(no_rawat) from rm_ttv_balance_cairan where no_rawat=?", TNoRw.getText()) > 0) {
            return;
        }
        java.sql.PreparedStatement psTTV = null;
        java.sql.ResultSet rsTTV = null;
        try {
            psTTV = koneksi.prepareStatement(
                "select obj_td_sistol, obj_td_diastol, obj_hr, obj_rr, obj_suhu, tb_kbd, bb_sekarang " +
                "from penilaian_awal_keperawatan_ponek where no_rawat=?");
            try {
                psTTV.setString(1, TNoRw.getText());
                rsTTV = psTTV.executeQuery();
                if (rsTTV.next()) {
                    String sistol = rsTTV.getString("obj_td_sistol");
                    String diastol = rsTTV.getString("obj_td_diastol");
                    String td = "";
                    if (sistol != null && !sistol.isEmpty() && !sistol.equals("-")) td = sistol;
                    if (diastol != null && !diastol.isEmpty() && !diastol.equals("-")) td += "/" + diastol;
                    if (!td.isEmpty()) TTensi.setText(td);

                    String nadi = rsTTV.getString("obj_hr");
                    if (nadi != null && !nadi.isEmpty() && !nadi.equals("-")) TNadi.setText(nadi);

                    String rr = rsTTV.getString("obj_rr");
                    if (rr != null && !rr.isEmpty() && !rr.equals("-")) TRespirasi.setText(rr);

                    String suhu = rsTTV.getString("obj_suhu");
                    if (suhu != null && !suhu.isEmpty() && !suhu.equals("-")) TSuhu.setText(suhu);
                    
                    String tb = rsTTV.getString("tb_kbd");
                    if (tb != null && !tb.isEmpty() && !tb.equals("-")) TTB.setText(tb);
                    
                    String bb = rsTTV.getString("bb_sekarang");
                    if (bb != null && !bb.isEmpty() && !bb.equals("-")) TBB.setText(bb);
                }
            } finally {
                if (rsTTV != null) rsTTV.close();
                if (psTTV != null) psTTV.close();
            }
        } catch (Exception e) {
            System.out.println("Notif TTV Ponek: " + e);
        }
    }

}
