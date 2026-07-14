/*
 * Kontribusi dari Haris Rochmatullah, RS Bhayangkara Nganjuk
 */


package rekammedis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMSkriningMPPFormA extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0, pilihan=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahidentifikasi="",finger=""; 
    private StringBuilder htmlContent;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    
    /** Creates new form 
     * @param parent
     * @param modal */
    public RMSkriningMPPFormA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Alamat","Tgl.Evaluasi","Ruang","Tgl.Masuk",
            "Kode DPJP","DPJP","Kode Konsulan","Dokter Konsulan","Diagnosis","Kelompok","Assesmen","Identifikasi",
            "Rencana","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(160);
            }else if(i==14){
                column.setPreferredWidth(160);
            }else if(i==15){
                column.setPreferredWidth(160);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(160);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","Kode","Identifikasi Masalah"
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
        tbIdentifikasiMPP.setModel(tabModeMasalah);

        tbIdentifikasiMPP.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIdentifikasiMPP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbIdentifikasiMPP.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(360);
            }
        }
        tbIdentifikasiMPP.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Identifikasi Masalah"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);

        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TDiagnosis.setDocument(new batasInput((int)150).getKata(TDiagnosis));
        TKelompok.setDocument(new batasInput((int)150).getKata(TKelompok));
        Assemen.setDocument(new batasInput((int)250).getKata(Assemen));
        Identifikasi.setDocument(new batasInput((int)250).getKata(Identifikasi));
        Perencanaan.setDocument(new batasInput((int)2000).getKata(Perencanaan));
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
            
            TCariMasalah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariMasalah.getText().length()>2){
                        tampilMasalah2();
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
                    if(pilihan==1){
                        KdDok1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDokter1.requestFocus();
                    }else if(pilihan==2){
                        KdDok2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDokter2.requestFocus();
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
        
        
        ChkAccor.setSelected(false);
        isMenu();
        initBaru();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Perencanaan = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Assemen = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Identifikasi = new widget.TextArea();
        TglEvaluasi = new widget.Tanggal();
        jLabel94 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        Scroll6 = new widget.ScrollPane();
        tbIdentifikasiMPP = new widget.Table();
        label12 = new widget.Label();
        TCariMasalah = new widget.TextBox();
        BtnCariMasalah = new widget.Button();
        BtnTambahMasalah = new widget.Button();
        Alamat = new widget.TextBox();
        jLabel5 = new widget.Label();
        Kamar = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel16 = new widget.Label();
        TDokter1 = new widget.TextBox();
        KdDok1 = new widget.TextBox();
        jLabel18 = new widget.Label();
        btnDokter1 = new widget.Button();
        jLabel20 = new widget.Label();
        TDokter2 = new widget.TextBox();
        KdDok2 = new widget.TextBox();
        btnDokter2 = new widget.Button();
        jLabel40 = new widget.Label();
        jLabel22 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        TDiagnosis = new widget.TextArea();
        scrollPane7 = new widget.ScrollPane();
        TKelompok = new widget.TextArea();
        BtnAllMasalah = new widget.Button();
        TglMasuk = new widget.TextBox();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrintLap = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetailMasalah = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Form A – Evaluasi Awal Manajer Pelayanan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
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

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 573));
        FormInput.setLayout(null);

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
        label14.setBounds(424, 70, 80, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(508, 70, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(610, 70, 213, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(826, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Identifikasi Masalah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(444, 330, 120, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tgl.Evaluasi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(200, 70, 80, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Perencanaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Perencanaan.setColumns(20);
        Perencanaan.setRows(10);
        Perencanaan.setTabSize(14);
        Perencanaan.setName("Perencanaan"); // NOI18N
        Perencanaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerencanaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Perencanaan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(444, 470, 410, 93);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Perencanaan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(444, 450, 120, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Assemen.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Assemen.setColumns(20);
        Assemen.setRows(10);
        Assemen.setTabSize(14);
        Assemen.setName("Assemen"); // NOI18N
        Assemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AssemenKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Assemen);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(444, 230, 410, 93);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Assesmen :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(444, 210, 120, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Identifikasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Identifikasi.setColumns(20);
        Identifikasi.setRows(10);
        Identifikasi.setTabSize(14);
        Identifikasi.setName("Identifikasi"); // NOI18N
        Identifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdentifikasiKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Identifikasi);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(444, 350, 410, 93);

        TglEvaluasi.setForeground(new java.awt.Color(50, 70, 50));
        TglEvaluasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-03-2023 07:08:09" }));
        TglEvaluasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglEvaluasi.setName("TglEvaluasi"); // NOI18N
        TglEvaluasi.setOpaque(false);
        TglEvaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglEvaluasiKeyPressed(evt);
            }
        });
        FormInput.add(TglEvaluasi);
        TglEvaluasi.setBounds(284, 70, 130, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("Catatan :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 210, 180, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 210, 880, 1);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbIdentifikasiMPP.setName("tbIdentifikasiMPP"); // NOI18N
        Scroll6.setViewportView(tbIdentifikasiMPP);

        FormInput.add(Scroll6);
        Scroll6.setBounds(10, 230, 410, 303);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(0, 540, 70, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(74, 540, 241, 23);

        BtnCariMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMasalah.setMnemonic('1');
        BtnCariMasalah.setToolTipText("Alt+1");
        BtnCariMasalah.setName("BtnCariMasalah"); // NOI18N
        BtnCariMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMasalahActionPerformed(evt);
            }
        });
        BtnCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariMasalah);
        BtnCariMasalah.setBounds(319, 540, 28, 23);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(383, 540, 28, 23);

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(74, 40, 495, 23);

        jLabel5.setText("Alamat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 70, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KamarKeyPressed(evt);
            }
        });
        FormInput.add(Kamar);
        Kamar.setBounds(644, 40, 210, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(580, 40, 60, 23);

        jLabel16.setText("Tgl.Masuk :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 70, 70, 23);

        TDokter1.setEditable(false);
        TDokter1.setName("TDokter1"); // NOI18N
        FormInput.add(TDokter1);
        TDokter1.setBounds(176, 100, 190, 23);

        KdDok1.setEditable(false);
        KdDok1.setHighlighter(null);
        KdDok1.setName("KdDok1"); // NOI18N
        KdDok1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok1KeyPressed(evt);
            }
        });
        FormInput.add(KdDok1);
        KdDok1.setBounds(74, 100, 100, 23);

        jLabel18.setText("Dokter P.J. :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 100, 70, 23);

        btnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter1.setMnemonic('2');
        btnDokter1.setToolTipText("ALt+2");
        btnDokter1.setName("btnDokter1"); // NOI18N
        btnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter1ActionPerformed(evt);
            }
        });
        btnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter1KeyPressed(evt);
            }
        });
        FormInput.add(btnDokter1);
        btnDokter1.setBounds(369, 100, 28, 23);

        jLabel20.setText("Dokter Konsulan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(401, 100, 103, 23);

        TDokter2.setEditable(false);
        TDokter2.setName("TDokter2"); // NOI18N
        FormInput.add(TDokter2);
        TDokter2.setBounds(610, 100, 213, 23);

        KdDok2.setEditable(false);
        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); // NOI18N
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        FormInput.add(KdDok2);
        KdDok2.setBounds(508, 100, 100, 23);

        btnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter2.setMnemonic('2');
        btnDokter2.setToolTipText("ALt+2");
        btnDokter2.setName("btnDokter2"); // NOI18N
        btnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter2ActionPerformed(evt);
            }
        });
        btnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(btnDokter2);
        btnDokter2.setBounds(826, 100, 28, 23);

        jLabel40.setText("Diagnosis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 130, 70, 23);

        jLabel22.setText("Kelompok Resiko :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(401, 130, 103, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TDiagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TDiagnosis.setColumns(20);
        TDiagnosis.setRows(5);
        TDiagnosis.setName("TDiagnosis"); // NOI18N
        TDiagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDiagnosisKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TDiagnosis);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(74, 130, 323, 73);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        TKelompok.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKelompok.setColumns(20);
        TKelompok.setRows(5);
        TKelompok.setName("TKelompok"); // NOI18N
        TKelompok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKelompokKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(TKelompok);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(508, 130, 346, 73);

        BtnAllMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllMasalah.setMnemonic('2');
        BtnAllMasalah.setToolTipText("2Alt+2");
        BtnAllMasalah.setName("BtnAllMasalah"); // NOI18N
        BtnAllMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllMasalahActionPerformed(evt);
            }
        });
        BtnAllMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllMasalahKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllMasalah);
        BtnAllMasalah.setBounds(351, 540, 28, 23);

        TglMasuk.setEditable(false);
        TglMasuk.setHighlighter(null);
        TglMasuk.setName("TglMasuk"); // NOI18N
        FormInput.add(TglMasuk);
        TglMasuk.setBounds(74, 70, 130, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Evaluasi", internalFrame2);

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

        jLabel19.setText("Tgl. Evaluasi : ");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-03-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-03-2023" }));
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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255,250,250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrintLap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrintLap.setMnemonic('T');
        BtnPrintLap.setToolTipText("Alt+T");
        BtnPrintLap.setName("BtnPrintLap"); // NOI18N
        BtnPrintLap.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrintLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintLapActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrintLap);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(2, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetailMasalah.setName("tbMasalahDetailMasalah"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetailMasalah);

        FormMasalahRencana.add(Scroll7);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Perencanaan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        DetailRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        DetailRencana.setColumns(20);
        DetailRencana.setRows(5);
        DetailRencana.setName("DetailRencana"); // NOI18N
        DetailRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailRencanaKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(DetailRencana);

        FormMasalahRencana.add(scrollPane6);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Evaluasi", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else if(TDokter1.getText().trim().equals("")){
            Valid.textKosong(TDokter1,"Dokter DPJP");
        }else if(TDokter2.getText().trim().equals("")){
            Valid.textKosong(TDokter2,"Dokter Konsulan");
        }else{
            if(Sequel.menyimpantf("mpp_evaluasi_form_a","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",101,new String[]{
    TNoRw.getText(),Valid.SetTgl(TglEvaluasi.getSelectedItem()+"")+" "+TglEvaluasi.getSelectedItem().toString().substring(11,19),KdDok1.getText(),KdDok2.getText(),
    getGroupValue(ewsGroup),
    ewsAnalisis.getText(),
    getGroupValue(adlGroup),
    adlSebutkan.getText(),
    getGroupValue(riwayatGroup),
    riwayatPenyakitKronisSebutkan.getText(),
    kebiasaanMerokok.isSelected()?"Ya":"Tidak",
    kebiasaanAlkohol.isSelected()?"Ya":"Tidak",
    kebiasaanLain.isSelected()?"Ya":"Tidak",
    kebiasaanLainSebutkan.getText(),
    riwayatLainSebutkan.getText(),
    getGroupValue(perilakuGroup),
    perilakuAgamaSebutkan.getText(),
    perilakuSosialSebutkan.getText(),
    perilakuBudayaSebutkan.getText(),
    lingkunganTidakAda.isSelected()?"Ya":"Tidak",
    lingkunganTidakMenerima.isSelected()?"Ya":"Tidak",
    lingkunganSendirian.isSelected()?"Ya":"Tidak",
    lingkunganJauhFaskes.isSelected()?"Ya":"Tidak",
    lingkunganPanti.isSelected()?"Ya":"Tidak",
    lingkunganDekatFaskes.isSelected()?"Ya":"Tidak",
    lingkunganLain.isSelected()?"Ya":"Tidak",
    lingkunganLainSebutkan.getText(),
    keluargaTidakAda.isSelected()?"Ya":"Tidak",
    keluargaTidakMenerima.isSelected()?"Ya":"Tidak",
    keluargaTidakMenunggui.isSelected()?"Ya":"Tidak",
    keluargaTidakMampu.isSelected()?"Ya":"Tidak",
    keluargaTidakBisaDihubungi.isSelected()?"Ya":"Tidak",
    keluargaTidakTahu.isSelected()?"Ya":"Tidak",
    keluargaLain.isSelected()?"Ya":"Tidak",
    keluargaLainSebutkan.getText(),
    finansialTidakAda.isSelected()?"Ya":"Tidak",
    finansialAsuransi.isSelected()?"Ya":"Tidak",
    finansialTidakMampu.isSelected()?"Ya":"Tidak",
    finansialTidakAdaPj.isSelected()?"Ya":"Tidak",
    finansialBelumAdaAsuransi.isSelected()?"Ya":"Tidak",
    finansialMelebihiKlaim.isSelected()?"Ya":"Tidak",
    finansialLain.isSelected()?"Ya":"Tidak",
    finansialLainSebutkan.getText(),
    getGroupValue(mentalGroup),
    mentalTidakBaikSebutkan.getText(),
    getGroupValue(alternatifGroup),
    alternatifAdaSebutkan.getText(),
    getGroupValue(pemahamanGroup),
    pemahamanKurangSebutkan.getText(),
    harapanMembaik.isSelected()?"Ya":"Tidak",
    harapanTindakan.isSelected()?"Ya":"Tidak",
    harapanPasrah.isSelected()?"Ya":"Tidak",
    harapanLain.isSelected()?"Ya":"Tidak",
    harapanLainSebutkan.getText(),
    getGroupValue(asuransiGroup),
    getGroupValue(traumaGroup),
    traumaAdaSebutkan.getText(),
    getGroupValue(legalGroup),
    legalDibutuhkanSebutkan.getText(),
    masalahKepatuhan.isSelected()?"Ya":"Tidak",
    masalahTingkatAsuhan.isSelected()?"Ya":"Tidak",
    masalahKompleks.isSelected()?"Ya":"Tidak",
    masalahPerburukan.isSelected()?"Ya":"Tidak",
    masalahFinansial.isSelected()?"Ya":"Tidak",
    masalahSistemPembayaran.isSelected()?"Ya":"Tidak",
    masalahDukunganKeluarga.isSelected()?"Ya":"Tidak",
    masalahPengetahuan.isSelected()?"Ya":"Tidak",
    masalahKomplain.isSelected()?"Ya":"Tidak",
    masalahReadmission.isSelected()?"Ya":"Tidak",
    masalahPemulangan.isSelected()?"Ya":"Tidak",
    masalahUtilisasi.isSelected()?"Ya":"Tidak",
    masalahRujukan.isSelected()?"Ya":"Tidak",
    rencanaUtilisasi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiInformasi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiDiskusi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiDukungan.isSelected()?"Ya":"Tidak",
    rencanaEdukasi.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiMasalah.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiIntensif.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiSaran.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiTerminasi.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiDPJP.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiDietisien.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiKeuangan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiSpesialis.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiSpesialisSebutkan.getText(),
    rencanaKolaborasiFisioterapis.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPimpinan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPerawat.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiApoteker.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPsikolog.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiRohaniawan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiLainnyaSebutkan.getText(),
    rencanaEvaluasiFollowUp.isSelected()?"Ya":"Tidak",
    rencanaEvaluasiFollowUpSebutkan.getText(),
    rencanaEvaluasiEfektifitas.isSelected()?"Ya":"Tidak",
    rencanaEvaluasiEfektifitasSebutkan.getText(),
    KdPetugas.getText(),
    getAssesmenText(),
    getIdentifikasiText(),
    getRencanaText()
})==true){
                    for (i = 0; i < tbIdentifikasiMPP.getRowCount(); i++) {
                        if(tbIdentifikasiMPP.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("mpp_evaluasi_form_a_masalah","?,?,?",3,new String[]{TNoRw.getText(),Valid.SetTgl(TglEvaluasi.getSelectedItem()+"")+" "+TglEvaluasi.getSelectedItem().toString().substring(11,19),tbIdentifikasiMPP.getValueAt(i,1).toString()});
                        }
                    }
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Perencanaan,BtnBatal);
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
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
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
            Valid.textKosong(BtnPetugas,"Petugas");
        }else if(TDokter1.getText().trim().equals("")){
            Valid.textKosong(TDokter1,"Dokter DPJP");
        }else if(TDokter2.getText().trim().equals("")){
            Valid.textKosong(TDokter2,"Dokter Konsulan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir, " +
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,mpp_evaluasi_form_a.tanggal, " +
                        "ifnull(bangsal.nm_bangsal,'Ranap Gabung') as ruang,ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"+
                                                "mpp_evaluasi_form_a.kd_dokter,dokterpj.nm_dokter as dpjp,mpp_evaluasi_form_a.kd_konsulan,dokterkonsulen.nm_dokter as konsulan, " +
                        "'' as diagnosis,'' as kelompok,mpp_evaluasi_form_a.assesmen_teks as assesmen, mpp_evaluasi_form_a.identifikasi_teks as identifikasi, mpp_evaluasi_form_a.rencana_teks as rencana,mpp_evaluasi_form_a.nip,petugas.nama "+
"from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_evaluasi_form_a on mpp_evaluasi_form_a.no_rawat=reg_periksa.no_rawat " +
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join dokter as dokterpj on mpp_evaluasi_form_a.kd_dokter=dokterpj.kd_dokter " +
                        "inner join dokter as dokterkonsulen on mpp_evaluasi_form_a.kd_konsulan=dokterkonsulen.kd_dokter " +
                        "inner join petugas on mpp_evaluasi_form_a.nip=petugas.nip " +
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_evaluasi_form_a.tanggal between ? and ? group by reg_periksa.no_rawat order by mpp_evaluasi_form_a.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir, " +
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,mpp_evaluasi_form_a.tanggal, " +
                        "ifnull(bangsal.nm_bangsal,'Ranap Gabung') as ruang,ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"+
                                                "mpp_evaluasi_form_a.kd_dokter,dokterpj.nm_dokter as dpjp,mpp_evaluasi_form_a.kd_konsulan,dokterkonsulen.nm_dokter as konsulan, " +
                        "'' as diagnosis,'' as kelompok,mpp_evaluasi_form_a.assesmen_teks as assesmen, mpp_evaluasi_form_a.identifikasi_teks as identifikasi, mpp_evaluasi_form_a.rencana_teks as rencana,mpp_evaluasi_form_a.nip,petugas.nama "+
"from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_evaluasi_form_a on mpp_evaluasi_form_a.no_rawat=reg_periksa.no_rawat " +
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join dokter as dokterpj on mpp_evaluasi_form_a.kd_dokter=dokterpj.kd_dokter " +
                        "inner join dokter as dokterkonsulen on mpp_evaluasi_form_a.kd_konsulan=dokterkonsulen.kd_dokter " +
                        "inner join petugas on mpp_evaluasi_form_a.nip=petugas.nip " +
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_evaluasi_form_a.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                        "pasien.nm_pasien like ? or mpp_evaluasi_form_a.nip like ? or petugas.nama like ?) "+
                        "group by reg_periksa.no_rawat order by mpp_evaluasi_form_a.tanggal");
                }

                try {
                    if(TCari.getText().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                    }
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30%'><b>IDENTIFIKASI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'><b>EVALUASI</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        masalahidentifikasi="";
                        ps2=koneksi.prepareStatement(
                            "select master_masalah_mpp.kode_masalah,master_masalah_mpp.nama_masalah from master_masalah_mpp "+
                            "inner join mpp_evaluasi_form_a_masalah on mpp_evaluasi_form_a_masalah.kode_masalah=master_masalah_mpp.kode_masalah "+
                            "where mpp_evaluasi_form_a_masalah.no_rawat=? and mpp_evaluasi_form_a_masalah.tanggal=? order by kode_masalah");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,rs.getString("tanggal"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                masalahidentifikasi=rs2.getString("nama_masalah")+", "+masalahidentifikasi;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>J.K.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("jk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Lahir</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Alamat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("alamat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Kamar</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("kamar")+" "+rs.getString("ruang")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Masuk</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tgl_masuk")+" "+rs.getString("jam_masuk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Dokter DPJP</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("dpjp")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Dokter Konsulan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("konsulan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tanggal Evaluasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tanggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Diagnosis</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("diagnosis")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Kelompok</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("kelompok")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Assesmen</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("assesmen")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "Masalah MPP : "+masalahidentifikasi+"<br><br>"+
                                    "Rencana MPP : "+rs.getString("rencana")+
                                "</td>"+
                            "</tr>"
                        );
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );

                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
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
                    bg.close();

                    File f = new File("DataEvaluasiMPP.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA EVALUASI MANAJER PELAYANAN PASIEN<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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
        }
        this.setCursor(Cursor.getDefaultCursor());
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
                ChkAccor.setSelected(true);
                isMenu();
                getMasalah();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    getMasalah();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,TglEvaluasi,btnDokter1);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void PerencanaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerencanaanKeyPressed
        Valid.pindah2(evt,Identifikasi,BtnSimpan);
    }//GEN-LAST:event_PerencanaanKeyPressed

    private void AssemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AssemenKeyPressed
        Valid.pindah2(evt,TCariMasalah,Identifikasi);
    }//GEN-LAST:event_AssemenKeyPressed

    private void IdentifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdentifikasiKeyPressed
        Valid.pindah2(evt,Assemen,Perencanaan);
    }//GEN-LAST:event_IdentifikasiKeyPressed

    private void TglEvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglEvaluasiKeyPressed
        Valid.pindah2(evt,TNoRw,BtnPetugas);
    }//GEN-LAST:event_TglEvaluasiKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Assemen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TKelompok.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BtnCariMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariMasalahActionPerformed
        tampilMasalah2();
    }//GEN-LAST:event_BtnCariMasalahActionPerformed

    private void BtnCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Assemen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCariMasalah.requestFocus();
        }
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahMPP form=new MasterMasalahMPP(null,false);
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/masalahmpp.iyem")<30){
                tampilMasalah2();
            }else{
                tampilMasalah();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrintLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintLapActionPerformed
       if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select logo from setting"));  
            finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),18).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));  
           try {
                masalahidentifikasi="";
                ps2=koneksi.prepareStatement(
                    "select master_masalah_mpp.kode_masalah,master_masalah_mpp.nama_masalah from master_masalah_mpp "+
                    "inner join mpp_evaluasi_form_a_masalah on mpp_evaluasi_form_a_masalah.kode_masalah=master_masalah_mpp.kode_masalah "+
                    "where mpp_evaluasi_form_a_masalah.no_rawat=? and mpp_evaluasi_form_a_masalah.tanggal=? order by kode_masalah");
                try {
                    ps2.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps2.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        masalahidentifikasi=rs2.getString("nama_masalah")+", "+masalahidentifikasi;
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            param.put("masalah",masalahidentifikasi);  
            param.put("assesmen", getAssesmenText());
            param.put("identifikasi", getIdentifikasiText());
            param.put("rencana", getRencanaText());
            Valid.MyReportqry("rptCetakEvaluasiAwalMPP.jasper","report","::[ Laporan Evaluasi Awal Manajer Pelayanan Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir, " +                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,mpp_evaluasi_form_a.tanggal, " +
                        "ifnull(bangsal.nm_bangsal,'Ranap Gabung') as ruang,ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"+
                                                "mpp_evaluasi_form_a.kd_dokter,dokterpj.nm_dokter as dpjp,mpp_evaluasi_form_a.kd_konsulan,dokterkonsulen.nm_dokter as konsulan, " +
                        "'' as diagnosis,'' as kelompok,mpp_evaluasi_form_a.assesmen_teks as assesmen, mpp_evaluasi_form_a.identifikasi_teks as identifikasi, mpp_evaluasi_form_a.rencana_teks as rencana,mpp_evaluasi_form_a.nip,petugas.nama "+
"from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_evaluasi_form_a on mpp_evaluasi_form_a.no_rawat=reg_periksa.no_rawat " +
                        "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join dokter as dokterpj on mpp_evaluasi_form_a.kd_dokter=dokterpj.kd_dokter " +
                        "inner join dokter as dokterkonsulen on mpp_evaluasi_form_a.kd_konsulan=dokterkonsulen.kd_dokter " +
                        "inner join petugas on mpp_evaluasi_form_a.nip=petugas.nip " +
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_evaluasi_form_a.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and "+
                        "mpp_evaluasi_form_a.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrintLapActionPerformed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void KamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KamarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KamarKeyPressed

    private void KdDok1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter1.setText(dokter.tampil3(KdDok1.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokter1ActionPerformed(null);
        }
    }//GEN-LAST:event_KdDok1KeyPressed

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokter1ActionPerformed

    private void btnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter1KeyPressed
        Valid.pindah(evt,BtnPetugas,btnDokter2);
    }//GEN-LAST:event_btnDokter1KeyPressed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter2.setText(dokter.tampil3(KdDok2.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokter2ActionPerformed(null);
        }
    }//GEN-LAST:event_KdDok2KeyPressed

    private void btnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter2ActionPerformed
        pilihan=2;
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokter2ActionPerformed

    private void btnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter2KeyPressed
        Valid.pindah(evt,btnDokter1,TDiagnosis);
    }//GEN-LAST:event_btnDokter2KeyPressed

    private void TDiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDiagnosisKeyPressed
        Valid.pindah2(evt,btnDokter1,TKelompok);
    }//GEN-LAST:event_TDiagnosisKeyPressed

    private void TKelompokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKelompokKeyPressed
        Valid.pindah2(evt,TDiagnosis,TCariMasalah);
    }//GEN-LAST:event_TKelompokKeyPressed

    private void BtnAllMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllMasalahActionPerformed
        TCari.setText("");
        tampilMasalah();
    }//GEN-LAST:event_BtnAllMasalahActionPerformed

    private void BtnAllMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllMasalahActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariMasalah, TCariMasalah);
        }
    }//GEN-LAST:event_BtnAllMasalahKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningMPPFormA dialog = new RMSkriningMPPFormA(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.TextArea Assemen;
    private widget.Button BtnAll;
    private widget.Button BtnAllMasalah;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariMasalah;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnPrintLap;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextArea Identifikasi;
    private widget.TextBox Jk;
    private widget.TextBox Kamar;
    private widget.TextBox KdDok1;
    private widget.TextBox KdDok2;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmPetugas;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea Perencanaan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextArea TDiagnosis;
    private widget.TextBox TDokter1;
    private widget.TextBox TDokter2;
    private widget.TextArea TKelompok;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglEvaluasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TglMasuk;
    private widget.Button btnDokter1;
    private widget.Button btnDokter2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel34;
    private widget.Label jLabel40;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbIdentifikasiMPP;
    private widget.Table tbMasalahDetailMasalah;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    // Custom components for new form
    private javax.swing.JRadioButton ews01, ews23, ews46, ews7plus;
    private javax.swing.ButtonGroup ewsGroup;
    private javax.swing.JTextField ewsAnalisis;
    
    private javax.swing.JRadioButton adlTidakAda, adlAda;
    private javax.swing.ButtonGroup adlGroup;
    private javax.swing.JTextField adlSebutkan;
    
    private javax.swing.JRadioButton riwayatTidakAda, riwayatPenyakitKronis, riwayatLain;
    private javax.swing.ButtonGroup riwayatGroup;
    private javax.swing.JTextField riwayatPenyakitKronisSebutkan, riwayatLainSebutkan;
    
    private javax.swing.JRadioButton kebiasaanMerokok, kebiasaanAlkohol, kebiasaanLain;
    private javax.swing.ButtonGroup kebiasaanGroup;
    private javax.swing.JTextField kebiasaanLainSebutkan;
    
    private javax.swing.JRadioButton perilakuTidakAda, perilakuAgama, perilakuSosial, perilakuBudaya;
    private javax.swing.ButtonGroup perilakuGroup;
    private javax.swing.JTextField perilakuAgamaSebutkan, perilakuSosialSebutkan, perilakuBudayaSebutkan;
    
    private javax.swing.JRadioButton lingkunganTidakAda, lingkunganTidakMenerima, lingkunganSendirian, lingkunganJauhFaskes, lingkunganPanti, lingkunganDekatFaskes, lingkunganLain;
    private javax.swing.ButtonGroup lingkunganGroup;
    private javax.swing.JTextField lingkunganLainSebutkan;
    
    private javax.swing.JRadioButton keluargaTidakAda, keluargaTidakMenerima, keluargaTidakMenunggui, keluargaTidakMampu, keluargaTidakBisaDihubungi, keluargaTidakTahu, keluargaLain;
    private javax.swing.ButtonGroup keluargaGroup;
    private javax.swing.JTextField keluargaLainSebutkan;
    
    private javax.swing.JRadioButton finansialTidakAda, finansialAsuransi, finansialTidakMampu, finansialTidakAdaPj, finansialBelumAdaAsuransi, finansialMelebihiKlaim, finansialLain;
    private javax.swing.ButtonGroup finansialGroup;
    private javax.swing.JTextField finansialLainSebutkan;
    
    private javax.swing.JRadioButton mentalBaik, mentalTidakBaik;
    private javax.swing.ButtonGroup mentalGroup;
    private javax.swing.JTextField mentalTidakBaikSebutkan;
    
    private javax.swing.JRadioButton alternatifTidakAda, alternatifAda;
    private javax.swing.ButtonGroup alternatifGroup;
    private javax.swing.JTextField alternatifAdaSebutkan;
    
    private javax.swing.JRadioButton pemahamanBaik, pemahamanKurang;
    private javax.swing.ButtonGroup pemahamanGroup;
    private javax.swing.JTextField pemahamanKurangSebutkan;
    
    private javax.swing.JRadioButton harapanMembaik, harapanTindakan, harapanPasrah, harapanLain;
    private javax.swing.ButtonGroup harapanGroup;
    private javax.swing.JTextField harapanLainSebutkan;
    
    private javax.swing.JRadioButton asuransiAktif, asuransiTidakAktif, asuransiUmum;
    private javax.swing.ButtonGroup asuransiGroup;
    
    private javax.swing.JRadioButton traumaAda, traumaTidakAda;
    private javax.swing.ButtonGroup traumaGroup;
    private javax.swing.JTextField traumaAdaSebutkan;
    
    private javax.swing.JRadioButton legalTidakDibutuhkan, legalDibutuhkan;
    private javax.swing.ButtonGroup legalGroup;
    private javax.swing.JTextField legalDibutuhkanSebutkan;
    
    private javax.swing.ButtonGroup identifikasiGroup;
    private javax.swing.ButtonGroup rencanaGroup;
    // Identifikasi Masalah
    private javax.swing.JRadioButton masalahKepatuhan, masalahTingkatAsuhan, masalahKompleks, masalahPerburukan;
    private javax.swing.JRadioButton masalahFinansial, masalahSistemPembayaran, masalahDukunganKeluarga;
    private javax.swing.JRadioButton masalahPengetahuan, masalahKomplain, masalahReadmission;
    private javax.swing.JRadioButton masalahPemulangan, masalahUtilisasi, masalahRujukan;
    
    // Perencanaan MPP
    private javax.swing.JRadioButton rencanaUtilisasi;
    private javax.swing.JRadioButton rencanaAdvokasiInformasi, rencanaAdvokasiDiskusi, rencanaAdvokasiDukungan;
    private javax.swing.JRadioButton rencanaEdukasi;
    private javax.swing.JRadioButton rencanaKoordinasiMasalah, rencanaKoordinasiIntensif, rencanaKoordinasiSaran, rencanaKoordinasiTerminasi;
    private javax.swing.JRadioButton rencanaKolaborasiDPJP, rencanaKolaborasiDietisien, rencanaKolaborasiKeuangan, rencanaKolaborasiSpesialis, rencanaKolaborasiFisioterapis, rencanaKolaborasiPimpinan, rencanaKolaborasiPerawat, rencanaKolaborasiApoteker, rencanaKolaborasiPsikolog, rencanaKolaborasiRohaniawan;
    private javax.swing.JTextField rencanaKolaborasiSpesialisSebutkan, rencanaKolaborasiLainnyaSebutkan;
    private javax.swing.JRadioButton rencanaEvaluasiFollowUp, rencanaEvaluasiEfektifitas;
    private javax.swing.JTextField rencanaEvaluasiFollowUpSebutkan, rencanaEvaluasiEfektifitasSebutkan;

    private javax.swing.JLabel createLabel(String text, int x, int y, int w, int h) {
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setFont(new java.awt.Font("Tahoma", 0, 11));
        label.setForeground(new java.awt.Color(50, 50, 50));
        label.setBounds(x, y, w, h);
        FormInput.add(label);
        return label;
    }
    
    private javax.swing.JLabel createTitleLabel(String text, int x, int y, int w, int h) {
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
        label.setForeground(new java.awt.Color(50, 50, 50));
        label.setBounds(x, y, w, h);
        FormInput.add(label);
        return label;
    }

    private javax.swing.JRadioButton createRadioButton(String text, int x, int y, int w, int h) {
        javax.swing.JRadioButton rb = new javax.swing.JRadioButton(text);
        rb.setFont(new java.awt.Font("Tahoma", 0, 11));
        rb.setBackground(new java.awt.Color(255, 255, 255));
        rb.setForeground(new java.awt.Color(50, 50, 50));
        rb.setBounds(x, y, w, h);
        FormInput.add(rb);
        return rb;
    }

    private javax.swing.JRadioButton createRadioButton(String text, int x, int y, int w, int h, javax.swing.ButtonGroup group) {
        javax.swing.JRadioButton rb = new javax.swing.JRadioButton(text);
        rb.setFont(new java.awt.Font("Tahoma", 0, 11));
        rb.setBackground(new java.awt.Color(255, 255, 255));
        rb.setForeground(new java.awt.Color(50, 50, 50));
        rb.setBounds(x, y, w, h);
        group.add(rb);
        FormInput.add(rb);
        return rb;
    }

    private javax.swing.JTextField createTextField(int x, int y, int w, int h) {
        javax.swing.JTextField tf = new javax.swing.JTextField();
        tf.setFont(new java.awt.Font("Tahoma", 0, 11));
        tf.setBounds(x, y, w, h);
        FormInput.add(tf);
        return tf;
    }

    private String extractBetween(String source, String start, String end) {
        if (source == null || !source.contains(start)) return "";
        int idxStart = source.indexOf(start) + start.length();
        if (end == null || end.isEmpty()) {
            return source.substring(idxStart).trim();
        }
        int idxEnd = source.indexOf(end, idxStart);
        if (idxEnd > idxStart) {
            return source.substring(idxStart, idxEnd).trim();
        }
        return source.substring(idxStart).trim();
    }

    private void adjustDatabaseSchema() {
        try {
            Connection c = koneksi;
            
            // Check if table needs to be dropped (legacy schema)
            boolean dropNeeded = false;
            try {
                java.sql.ResultSet rsCheck = c.createStatement().executeQuery("SELECT * FROM mpp_evaluasi_form_a LIMIT 1");
                if (rsCheck.getMetaData().getColumnCount() < 101) {
                    dropNeeded = true;
                }
                rsCheck.close();
            } catch (Exception e) {
                // Table probably doesn't exist, ignore
            }
            
            if (dropNeeded) {
                c.createStatement().executeUpdate("DROP TABLE IF EXISTS mpp_evaluasi_form_a_masalah");
                c.createStatement().executeUpdate("DROP TABLE IF EXISTS mpp_evaluasi_form_a");
                System.out.println("Legacy tables dropped!");
            }

            PreparedStatement ps1 = c.prepareStatement(
"CREATE TABLE IF NOT EXISTS mpp_evaluasi_form_a (" +
                "  no_rawat varchar(17) NOT NULL," +
                "  tanggal datetime NOT NULL," +
                "  kd_dokter varchar(20) DEFAULT NULL," +
                "  kd_konsulan varchar(20) DEFAULT NULL," +
                "  skor_ews varchar(100) DEFAULT NULL," +
                "  analisis_ews varchar(200) DEFAULT NULL," +
                "  penurunan_adl varchar(100) DEFAULT NULL," +
                "  adl_sebutkan varchar(200) DEFAULT NULL," +
                "  riwayat_kesehatan varchar(100) DEFAULT NULL," +
                "  riwayat_penyakit_kronis varchar(200) DEFAULT NULL," +
                "  kebiasaan_merokok varchar(5) DEFAULT NULL," +
                "  kebiasaan_alkohol varchar(5) DEFAULT NULL," +
                "  kebiasaan_lain varchar(5) DEFAULT NULL," +
                "  kebiasaan_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  riwayat_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  perilaku varchar(100) DEFAULT NULL," +
                "  perilaku_agama varchar(200) DEFAULT NULL," +
                "  perilaku_sosial varchar(200) DEFAULT NULL," +
                "  perilaku_budaya varchar(200) DEFAULT NULL," +
                "  lingkungan_tidak_ada varchar(5) DEFAULT NULL," +
                "  lingkungan_tidak_menerima varchar(5) DEFAULT NULL," +
                "  lingkungan_sendirian varchar(5) DEFAULT NULL," +
                "  lingkungan_jauh_faskes varchar(5) DEFAULT NULL," +
                "  lingkungan_panti varchar(5) DEFAULT NULL," +
                "  lingkungan_dekat_faskes varchar(5) DEFAULT NULL," +
                "  lingkungan_lain varchar(5) DEFAULT NULL," +
                "  lingkungan_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  keluarga_tidak_ada varchar(5) DEFAULT NULL," +
                "  keluarga_tidak_menerima varchar(5) DEFAULT NULL," +
                "  keluarga_tidak_menunggui varchar(5) DEFAULT NULL," +
                "  keluarga_tidak_mampu varchar(5) DEFAULT NULL," +
                "  keluarga_tidak_bisa_dihubungi varchar(5) DEFAULT NULL," +
                "  keluarga_tidak_tahu varchar(5) DEFAULT NULL," +
                "  keluarga_lain varchar(5) DEFAULT NULL," +
                "  keluarga_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  finansial_tidak_ada varchar(5) DEFAULT NULL," +
                "  finansial_asuransi varchar(5) DEFAULT NULL," +
                "  finansial_tidak_mampu varchar(5) DEFAULT NULL," +
                "  finansial_tidak_ada_pj varchar(5) DEFAULT NULL," +
                "  finansial_belum_ada_asuransi varchar(5) DEFAULT NULL," +
                "  finansial_melebihi_klaim varchar(5) DEFAULT NULL," +
                "  finansial_lain varchar(5) DEFAULT NULL," +
                "  finansial_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  kesehatan_mental varchar(100) DEFAULT NULL," +
                "  mental_tidak_baik varchar(200) DEFAULT NULL," +
                "  pengobatan_alternatif varchar(100) DEFAULT NULL," +
                "  alternatif_ada varchar(200) DEFAULT NULL," +
                "  pemahaman_kesehatan varchar(100) DEFAULT NULL," +
                "  pemahaman_kurang varchar(200) DEFAULT NULL," +
                "  harapan_membaik varchar(5) DEFAULT NULL," +
                "  harapan_tindakan varchar(5) DEFAULT NULL," +
                "  harapan_pasrah varchar(5) DEFAULT NULL," +
                "  harapan_lain varchar(5) DEFAULT NULL," +
                "  harapan_lain_sebutkan varchar(200) DEFAULT NULL," +
                "  status_asuransi varchar(100) DEFAULT NULL," +
                "  riwayat_trauma varchar(100) DEFAULT NULL," +
                "  trauma_ada varchar(200) DEFAULT NULL," +
                "  aspek_legal varchar(100) DEFAULT NULL," +
                "  legal_dibutuhkan varchar(200) DEFAULT NULL," +
                "  masalah_kepatuhan varchar(5) DEFAULT NULL," +
                "  masalah_tingkat_asuhan varchar(5) DEFAULT NULL," +
                "  masalah_kompleks varchar(5) DEFAULT NULL," +
                "  masalah_perburukan varchar(5) DEFAULT NULL," +
                "  masalah_finansial varchar(5) DEFAULT NULL," +
                "  masalah_sistem_pembayaran varchar(5) DEFAULT NULL," +
                "  masalah_dukungan_keluarga varchar(5) DEFAULT NULL," +
                "  masalah_pengetahuan varchar(5) DEFAULT NULL," +
                "  masalah_komplain varchar(5) DEFAULT NULL," +
                "  masalah_readmission varchar(5) DEFAULT NULL," +
                "  masalah_pemulangan varchar(5) DEFAULT NULL," +
                "  masalah_utilisasi varchar(5) DEFAULT NULL," +
                "  masalah_rujukan varchar(5) DEFAULT NULL," +
                "  rencana_utilisasi varchar(5) DEFAULT NULL," +
                "  rencana_advokasi_informasi varchar(5) DEFAULT NULL," +
                "  rencana_advokasi_diskusi varchar(5) DEFAULT NULL," +
                "  rencana_advokasi_dukungan varchar(5) DEFAULT NULL," +
                "  rencana_edukasi varchar(5) DEFAULT NULL," +
                "  rencana_koordinasi_masalah varchar(5) DEFAULT NULL," +
                "  rencana_koordinasi_intensif varchar(5) DEFAULT NULL," +
                "  rencana_koordinasi_saran varchar(5) DEFAULT NULL," +
                "  rencana_koordinasi_terminasi varchar(5) DEFAULT NULL," +
                "  kolaborasi_dpjp varchar(5) DEFAULT NULL," +
                "  kolaborasi_dietisien varchar(5) DEFAULT NULL," +
                "  kolaborasi_keuangan varchar(5) DEFAULT NULL," +
                "  kolaborasi_spesialis varchar(5) DEFAULT NULL," +
                "  kolaborasi_spesialis_sebutkan varchar(200) DEFAULT NULL," +
                "  kolaborasi_fisioterapis varchar(5) DEFAULT NULL," +
                "  kolaborasi_pimpinan varchar(5) DEFAULT NULL," +
                "  kolaborasi_perawat varchar(5) DEFAULT NULL," +
                "  kolaborasi_apoteker varchar(5) DEFAULT NULL," +
                "  kolaborasi_psikolog varchar(5) DEFAULT NULL," +
                "  kolaborasi_rohaniawan varchar(5) DEFAULT NULL," +
                "  kolaborasi_lainnya_sebutkan varchar(200) DEFAULT NULL," +
                "  evaluasi_follow_up varchar(5) DEFAULT NULL," +
                "  evaluasi_follow_up_sebutkan varchar(200) DEFAULT NULL," +
                "  evaluasi_efektifitas varchar(5) DEFAULT NULL," +
                "  evaluasi_efektifitas_sebutkan varchar(200) DEFAULT NULL," +
                "  nip varchar(20) DEFAULT NULL," +
                "  assesmen_teks text DEFAULT NULL," +
                "  identifikasi_teks text DEFAULT NULL," +
                "  rencana_teks text DEFAULT NULL," +
                "  PRIMARY KEY (no_rawat, tanggal)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1"
            );
            ps1.executeUpdate();
            
            PreparedStatement ps2 = c.prepareStatement(
                "CREATE TABLE IF NOT EXISTS mpp_evaluasi_form_a_masalah (" +
                "  no_rawat varchar(17) NOT NULL," +
                "  tanggal datetime NOT NULL," +
                "  kode_masalah varchar(5) NOT NULL," +
                "  PRIMARY KEY (no_rawat,tanggal,kode_masalah)," +
                "  KEY kode_masalah (kode_masalah)," +
                "  CONSTRAINT mpp_evaluasi_form_a_masalah_ibfk_1 FOREIGN KEY (no_rawat, tanggal) REFERENCES mpp_evaluasi_form_a (no_rawat, tanggal) ON DELETE CASCADE ON UPDATE CASCADE," +
                "  CONSTRAINT mpp_evaluasi_form_a_masalah_ibfk_2 FOREIGN KEY (kode_masalah) REFERENCES master_masalah_mpp (kode_masalah)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1"
            );
            ps2.executeUpdate();
        } catch (Exception e) {
            System.out.println("Notif Adjust DB: " + e);
        }
    }

    private String getAssesmenText() {
        StringBuilder sb = new StringBuilder();
        sb.append("ASESMEN MANAJEMEN PELAYANAN PASIEN\n");
        sb.append("- Skor EWS: ");
        if (ews01.isSelected()) sb.append("Skor 0 – 1 (warna hijau : normal)");
        else if (ews23.isSelected()) sb.append("Skor 2 -3 (warna kuning : rendah)");
        else if (ews46.isSelected()) sb.append("Skor 4 – 6 (warna orange : sedang)");
        else if (ews7plus.isSelected()) sb.append("Skor ≥ 7 (warna merah : tinggi)");
        sb.append(", Analisis: ").append(ewsAnalisis.getText()).append("\n");

        sb.append("- ADL: ");
        if (adlTidakAda.isSelected()) sb.append("Tidak Ada Hambatan");
        else if (adlAda.isSelected()) sb.append("Ada, sebutkan : ").append(adlSebutkan.getText());
        sb.append("\n");

        sb.append("- Riwayat kesehatan: ");
        if (riwayatTidakAda.isSelected()) sb.append("Tidak Ada");
        else if (riwayatPenyakitKronis.isSelected()) sb.append("Penyakit Kronis, sebutkan: ").append(riwayatPenyakitKronisSebutkan.getText());
        else if (kebiasaanMerokok.isSelected() || kebiasaanAlkohol.isSelected() || kebiasaanLain.isSelected()) {
            sb.append("Pola Kebiasaan: ");
            if (kebiasaanMerokok.isSelected()) sb.append("Merokok; ");
            if (kebiasaanAlkohol.isSelected()) sb.append("Konsumsi Alkohol; ");
            if (kebiasaanLain.isSelected()) sb.append("Lain-lain: ").append(kebiasaanLainSebutkan.getText()).append("; ");
        }
        else if (riwayatLain.isSelected()) sb.append("Lain-lain, sebutkan: ").append(riwayatLainSebutkan.getText());
        sb.append("\n");

        sb.append("- Perilaku spiritual: ");
        if (perilakuTidakAda.isSelected()) sb.append("Tidak Ada Hambatan");
        else if (perilakuAgama.isSelected()) sb.append("Nilai keyakinan agama tertentu: ").append(perilakuAgamaSebutkan.getText());
        else if (perilakuSosial.isSelected()) sb.append("Nilai keyakinan sosial tertentu: ").append(perilakuSosialSebutkan.getText());
        else if (perilakuBudaya.isSelected()) sb.append("Nilai budaya tertentu: ").append(perilakuBudayaSebutkan.getText());
        sb.append("\n");

        sb.append("- Lingkungan: ");
        if (lingkunganTidakAda.isSelected()) sb.append("Tidak Ada Hambatan; ");
        if (lingkunganTidakMenerima.isSelected()) sb.append("Masyarakat tidak menerima pasien; ");
        if (lingkunganSendirian.isSelected()) sb.append("Pasien tinggal sendirian; ");
        if (lingkunganJauhFaskes.isSelected()) sb.append("Rumah tinggal jauh dari faskes; ");
        if (lingkunganPanti.isSelected()) sb.append("Pasien tinggal di panti; ");
        if (lingkunganDekatFaskes.isSelected()) sb.append("Rumah tinggal dekat dengan faskes; ");
        if (lingkunganLain.isSelected()) sb.append("Lain-lain: ").append(lingkunganLainSebutkan.getText()).append("; ");
        sb.append("\n");

        sb.append("- Dukungan keluarga: ");
        if (keluargaTidakAda.isSelected()) sb.append("Tidak Ada Hambatan; ");
        if (keluargaTidakMenerima.isSelected()) sb.append("Keluarga tidak mau menerima kondisi pasien; ");
        if (keluargaTidakMenunggui.isSelected()) sb.append("Keluarga tidak pernah menunggui; ");
        if (keluargaTidakMampu.isSelected()) sb.append("Keluarga tidak mampu merawat; ");
        if (keluargaTidakBisaDihubungi.isSelected()) sb.append("Keluarga tidak bisa dihubungi; ");
        if (keluargaTidakTahu.isSelected()) sb.append("Keluarga tidak tahu perkembangan kondisi pasien; ");
        if (keluargaLain.isSelected()) sb.append("Lain-lain: ").append(keluargaLainSebutkan.getText()).append("; ");
        sb.append("\n");

        sb.append("- Finansial: ");
        if (finansialTidakAda.isSelected()) sb.append("Tidak Ada Hambatan; ");
        if (finansialAsuransi.isSelected()) sb.append("Ada masalah Asuransi; ");
        if (finansialTidakMampu.isSelected()) sb.append("Pasien tidak mampu dan/atau miskin; ");
        if (finansialTidakAdaPj.isSelected()) sb.append("Tidak ada penanggung jawab pembiayaan; ");
        if (finansialBelumAdaAsuransi.isSelected()) sb.append("Pasien belum memiliki Asuransi; ");
        if (finansialMelebihiKlaim.isSelected()) sb.append("Total biaya melebihi klaim asuransi; ");
        if (finansialLain.isSelected()) sb.append("Lain-lain: ").append(finansialLainSebutkan.getText()).append("; ");
        sb.append("\n");

        sb.append("- Mental/Kognitif: ");
        if (mentalBaik.isSelected()) sb.append("Baik");
        else if (mentalTidakBaik.isSelected()) sb.append("Tidak baik, jelaskan: ").append(mentalTidakBaikSebutkan.getText());
        sb.append("\n");

        sb.append("- Riwayat pengobatan: ");
        if (alternatifTidakAda.isSelected()) sb.append("Tidak Ada");
        else if (alternatifAda.isSelected()) sb.append("Ada, sebutkan: ").append(alternatifAdaSebutkan.getText());
        sb.append("\n");

        sb.append("- Pemahaman: ");
        if (pemahamanBaik.isSelected()) sb.append("Baik");
        else if (pemahamanKurang.isSelected()) sb.append("Kurang, keterangan: ").append(pemahamanKurangSebutkan.getText());
        sb.append("\n");

        sb.append("- Harapan: ");
        if (harapanMembaik.isSelected()) sb.append("Kondisi pasien membaik dan/atau sembuh; ");
        if (harapanTindakan.isSelected()) sb.append("Segera dilakukan tindakan; ");
        if (harapanPasrah.isSelected()) sb.append("Keluarga pasrah terhadap kondisi pasien; ");
        if (harapanLain.isSelected()) sb.append("Lain-lain: ").append(harapanLainSebutkan.getText()).append("; ");
        sb.append("\n");

        sb.append("- Status Asuransi: ");
        if (asuransiAktif.isSelected()) sb.append("Aktif");
        else if (asuransiTidakAktif.isSelected()) sb.append("Tidak Aktif / tidak memiliki");
        else if (asuransiUmum.isSelected()) sb.append("Dengan Layanan Umum / bayar mandiri");
        sb.append("\n");

        sb.append("- Trauma: ");
        if (traumaAda.isSelected()) sb.append("Ada, sebutkan: ").append(traumaAdaSebutkan.getText());
        else if (traumaTidakAda.isSelected()) sb.append("Tidak ada");
        sb.append("\n");

        sb.append("- Legal/Advokasi: ");
        if (legalTidakDibutuhkan.isSelected()) sb.append("Tidak Dibutuhkan");
        else if (legalDibutuhkan.isSelected()) sb.append("Dibutuhkan, keterangan: ").append(legalDibutuhkanSebutkan.getText());
        
        return sb.toString();
    }

    private String getIdentifikasiText() {
        StringBuilder sb = new StringBuilder();
        sb.append("IDENTIFIKASI MASALAH\n");
        sb.append("- Masalah Kesehatan: ");
        if (masalahKepatuhan.isSelected()) sb.append("[Ketidakpatuhan pasien dalam proses asuhan] ");
        if (masalahTingkatAsuhan.isSelected()) sb.append("[Tingkat Asuhan yang tidak sesuai dengan regulasi dan/atau norma] ");
        if (masalahKompleks.isSelected()) sb.append("[Pasien dengan perawatan kompleks] ");
        if (masalahPerburukan.isSelected()) sb.append("[Pasien mengalami penurunan / perburukan kondisi / peningkatan komplikasi] ");
        sb.append("\n");

        sb.append("- Masalah Spiritual/Psiko/Sosial/Ekonomi/Budaya: ");
        if (masalahFinansial.isSelected()) sb.append("[Kendala finansial akibat adanya perburukan kondisi, peningkatan komplikasi] ");
        if (masalahSistemPembayaran.isSelected()) sb.append("[Kendala system pembayaran] ");
        if (masalahDukunganKeluarga.isSelected()) sb.append("[Kurangnya dukungan keluarga berdampak pada perawatan] ");
        sb.append("\n");

        sb.append("- Pemahaman yang kurang memadai: ");
        if (masalahPengetahuan.isSelected()) sb.append("[Pengetahuan pasien / keluarga tentang Kesehatan yang rendah] ");
        if (masalahKomplain.isSelected()) sb.append("[Resiko tinggi komplain] ");
        if (masalahReadmission.isSelected()) sb.append("[Pasien readmission] ");
        sb.append("\n");

        sb.append("- Kontinuitas pelayanan: ");
        if (masalahPemulangan.isSelected()) sb.append("[Rencana pemulangan yang belum memenuhi kriteria dan/atau penundaan pemulangan] ");
        if (masalahUtilisasi.isSelected()) sb.append("[Over dan/atau under utilisasi layanan atas dasar regulasi yang berlaku] ");
        if (masalahRujukan.isSelected()) sb.append("[Rujukan ke fasilitas Kesehatan lain] ");
        
        return sb.toString();
    }

    private String getRencanaText() {
        StringBuilder sb = new StringBuilder();
        sb.append("PERENCANAAN MPP\n");
        sb.append("- Utilisasi/Mutu Asuhan: ");
        if (rencanaUtilisasi.isSelected()) sb.append("[Kaji efektifitas pelayanan di ruangan oleh PPA (terapi DPJP, tindakan keperawatan, Pemeriksaan penunjang, Tindakan PPA lainnya)] ");
        sb.append("\n");

        sb.append("- Advokasi: ");
        if (rencanaAdvokasiInformasi.isSelected()) sb.append("[Pastikan pasien / keluarga mendapatkan informasi kondisi kesehatannya dari DPJP / PPA] ");
        if (rencanaAdvokasiDiskusi.isSelected()) sb.append("[Beri kesempatan pasien / keluarga untuk berdiskusi tentang hal yang belum dipahami] ");
        if (rencanaAdvokasiDukungan.isSelected()) sb.append("[Beri dukungan moral / spiritual pada pasien / keluarga terhadap perburukan kondisi kesehatan] ");
        sb.append("\n");

        sb.append("- Edukasi: ");
        if (rencanaEdukasi.isSelected()) sb.append("[Beri informasi / edukasi tambahan jika diperlukan] ");
        sb.append("\n");

        sb.append("- Koordinasi: ");
        if (rencanaKoordinasiMasalah.isSelected()) sb.append("[Diskusikan dengan DPJP / PPA / Pimpinan RS jika ditemukan masalah perawatan, pengobatan, Tindakan, pemeriksaan dan lainnya] ");
        if (rencanaKoordinasiIntensif.isSelected()) sb.append("[Diskusikan dengan DPJP / PPA jika pasien diindikasi perawatan ruang intensif / paliatif / stabil] ");
        if (rencanaKoordinasiSaran.isSelected()) sb.append("[Beri saran pada DPJP / PPA untuk melakukan pembahasan kasus kompleks pada pasien] ");
        if (rencanaKoordinasiTerminasi.isSelected()) sb.append("[Diskusikan dengan DPJP / PPA untuk melakukan terminasi layanan MPP] ");
        sb.append("\n");

        sb.append("- Kolaborasi: ");
        if (rencanaKolaborasiDPJP.isSelected()) sb.append("[DPJP] ");
        if (rencanaKolaborasiDietisien.isSelected()) sb.append("[Dietisien] ");
        if (rencanaKolaborasiKeuangan.isSelected()) sb.append("[Bagian keuangan] ");
        if (rencanaKolaborasiSpesialis.isSelected()) sb.append("[Dokter Spesialis lainnya: ").append(rencanaKolaborasiSpesialisSebutkan.getText()).append("] ");
        if (rencanaKolaborasiFisioterapis.isSelected()) sb.append("[Fisioterapis] ");
        if (rencanaKolaborasiPimpinan.isSelected()) sb.append("[Pimpinan RS] ");
        if (rencanaKolaborasiPerawat.isSelected()) sb.append("[Perawat] ");
        if (rencanaKolaborasiApoteker.isSelected()) sb.append("[Apoteker] ");
        if (rencanaKolaborasiPsikolog.isSelected()) sb.append("[Psikolog] ");
        if (rencanaKolaborasiRohaniawan.isSelected()) sb.append("[Petugas Rohaniawan] ");
        if (!rencanaKolaborasiLainnyaSebutkan.getText().trim().isEmpty()) sb.append("[Lainnya: ").append(rencanaKolaborasiLainnyaSebutkan.getText()).append("] ");
        sb.append("\n");

        sb.append("- Evaluasi: ");
        if (rencanaEvaluasiFollowUp.isSelected()) sb.append("[Follow up hasil pembahasan masalah kompleks: ").append(rencanaEvaluasiFollowUpSebutkan.getText()).append("] ");
        if (rencanaEvaluasiEfektifitas.isSelected()) sb.append("[Evaluasi efektifitas pelayanan (terapi DPJP, tindakan keperawatan, Pemeriksaan penunjang dan Tindakan PPA lainnya): ").append(rencanaEvaluasiEfektifitasSebutkan.getText()).append("] ");
        
        return sb.toString();
    }

    private void parseAssesmen(String val) {
        clearCustomFields();
        if (val == null || val.isEmpty()) return;
        String[] lines = val.split("\n");
        for (String line : lines) {
            if (line.startsWith("- Skor EWS:")) {
                ews01.setSelected(line.contains("Skor 0 – 1 (warna hijau : normal)"));
                ews23.setSelected(line.contains("Skor 2 -3 (warna kuning : rendah)"));
                ews46.setSelected(line.contains("Skor 4 – 6 (warna orange : sedang)"));
                ews7plus.setSelected(line.contains("Skor ≥ 7 (warna merah : tinggi)"));
                ewsAnalisis.setText(extractBetween(line, ", Analisis: ", ""));
            } else if (line.startsWith("- ADL:")) {
                adlTidakAda.setSelected(line.contains("Tidak Ada Hambatan"));
                adlAda.setSelected(line.contains("Ada, sebutkan :"));
                adlSebutkan.setText(extractBetween(line, "Ada, sebutkan : ", ""));
            } else if (line.startsWith("- Riwayat kesehatan:")) {
                riwayatTidakAda.setSelected(line.contains("Tidak Ada"));
                riwayatPenyakitKronis.setSelected(line.contains("Penyakit Kronis, sebutkan:"));
                riwayatPenyakitKronisSebutkan.setText(extractBetween(line, "Penyakit Kronis, sebutkan: ", ""));
                kebiasaanMerokok.setSelected(line.contains("Merokok;"));
                kebiasaanAlkohol.setSelected(line.contains("Konsumsi Alkohol;"));
                kebiasaanLain.setSelected(line.contains("Lain-lain:"));
                kebiasaanLainSebutkan.setText(extractBetween(line, "Lain-lain: ", ";"));
                riwayatLain.setSelected(line.contains("Lain-lain, sebutkan:"));
                riwayatLainSebutkan.setText(extractBetween(line, "Lain-lain, sebutkan: ", ""));
            } else if (line.startsWith("- Perilaku spiritual:")) {
                perilakuTidakAda.setSelected(line.contains("Tidak Ada Hambatan"));
                perilakuAgama.setSelected(line.contains("Nilai keyakinan agama tertentu:"));
                perilakuAgamaSebutkan.setText(extractBetween(line, "Nilai keyakinan agama tertentu: ", ""));
                perilakuSosial.setSelected(line.contains("Nilai keyakinan sosial tertentu:"));
                perilakuSosialSebutkan.setText(extractBetween(line, "Nilai keyakinan sosial tertentu: ", ""));
                perilakuBudaya.setSelected(line.contains("Nilai budaya tertentu:"));
                perilakuBudayaSebutkan.setText(extractBetween(line, "Nilai budaya tertentu: ", ""));
            } else if (line.startsWith("- Lingkungan:")) {
                lingkunganTidakAda.setSelected(line.contains("Tidak Ada Hambatan;"));
                lingkunganTidakMenerima.setSelected(line.contains("Masyarakat tidak menerima pasien;"));
                lingkunganSendirian.setSelected(line.contains("Pasien tinggal sendirian;"));
                lingkunganJauhFaskes.setSelected(line.contains("Rumah tinggal jauh dari faskes;"));
                lingkunganPanti.setSelected(line.contains("Pasien tinggal di panti;"));
                lingkunganDekatFaskes.setSelected(line.contains("Rumah tinggal dekat dengan faskes;"));
                lingkunganLain.setSelected(line.contains("Lain-lain:"));
                lingkunganLainSebutkan.setText(extractBetween(line, "Lain-lain: ", ";"));
            } else if (line.startsWith("- Dukungan keluarga:")) {
                keluargaTidakAda.setSelected(line.contains("Tidak Ada Hambatan;"));
                keluargaTidakMenerima.setSelected(line.contains("Keluarga tidak mau menerima kondisi pasien;"));
                keluargaTidakMenunggui.setSelected(line.contains("Keluarga tidak pernah menunggui;"));
                keluargaTidakMampu.setSelected(line.contains("Keluarga tidak mampu merawat;"));
                keluargaTidakBisaDihubungi.setSelected(line.contains("Keluarga tidak bisa dihubungi;"));
                keluargaTidakTahu.setSelected(line.contains("Keluarga tidak tahu perkembangan kondisi pasien;"));
                keluargaLain.setSelected(line.contains("Lain-lain:"));
                keluargaLainSebutkan.setText(extractBetween(line, "Lain-lain: ", ";"));
            } else if (line.startsWith("- Finansial:")) {
                finansialTidakAda.setSelected(line.contains("Tidak Ada Hambatan;"));
                finansialAsuransi.setSelected(line.contains("Ada masalah Asuransi;"));
                finansialTidakMampu.setSelected(line.contains("Pasien tidak mampu dan/atau miskin;"));
                finansialTidakAdaPj.setSelected(line.contains("Tidak ada penanggung jawab pembiayaan;"));
                finansialBelumAdaAsuransi.setSelected(line.contains("Pasien belum memiliki Asuransi;"));
                finansialMelebihiKlaim.setSelected(line.contains("Total biaya melebihi klaim asuransi;"));
                finansialLain.setSelected(line.contains("Lain-lain:"));
                finansialLainSebutkan.setText(extractBetween(line, "Lain-lain: ", ";"));
            } else if (line.startsWith("- Mental/Kognitif:")) {
                mentalBaik.setSelected(line.contains("Baik"));
                mentalTidakBaik.setSelected(line.contains("Tidak baik, jelaskan:"));
                mentalTidakBaikSebutkan.setText(extractBetween(line, "Tidak baik, jelaskan: ", ""));
            } else if (line.startsWith("- Riwayat pengobatan:")) {
                alternatifTidakAda.setSelected(line.contains("Tidak Ada"));
                alternatifAda.setSelected(line.contains("Ada, sebutkan:"));
                alternatifAdaSebutkan.setText(extractBetween(line, "Ada, sebutkan: ", ""));
            } else if (line.startsWith("- Pemahaman:")) {
                pemahamanBaik.setSelected(line.contains("Baik"));
                pemahamanKurang.setSelected(line.contains("Kurang, keterangan:"));
                pemahamanKurangSebutkan.setText(extractBetween(line, "Kurang, keterangan: ", ""));
            } else if (line.startsWith("- Harapan:")) {
                harapanMembaik.setSelected(line.contains("Kondisi pasien membaik dan/atau sembuh;"));
                harapanTindakan.setSelected(line.contains("Segera dilakukan tindakan;"));
                harapanPasrah.setSelected(line.contains("Keluarga pasrah terhadap kondisi pasien;"));
                harapanLain.setSelected(line.contains("Lain-lain:"));
                harapanLainSebutkan.setText(extractBetween(line, "Lain-lain: ", ";"));
            } else if (line.startsWith("- Status Asuransi:")) {
                asuransiAktif.setSelected(line.contains("Aktif"));
                asuransiTidakAktif.setSelected(line.contains("Tidak Aktif / tidak memiliki"));
                asuransiUmum.setSelected(line.contains("Dengan Layanan Umum / bayar mandiri"));
            } else if (line.startsWith("- Trauma:")) {
                traumaAda.setSelected(line.contains("Ada, sebutkan:"));
                traumaAdaSebutkan.setText(extractBetween(line, "Ada, sebutkan: ", ""));
                traumaTidakAda.setSelected(line.contains("Tidak ada"));
            } else if (line.startsWith("- Legal/Advokasi:")) {
                legalTidakDibutuhkan.setSelected(line.contains("Tidak Dibutuhkan"));
                legalDibutuhkan.setSelected(line.contains("Dibutuhkan, keterangan:"));
                legalDibutuhkanSebutkan.setText(extractBetween(line, "Dibutuhkan, keterangan: ", ""));
            }
        }
    }

    private void parseIdentifikasi(String val) {
        if (val == null || val.isEmpty()) return;
        String[] lines = val.split("\n");
        for (String line : lines) {
            if (line.startsWith("- Masalah Kesehatan:")) {
                masalahKepatuhan.setSelected(line.contains("[Ketidakpatuhan pasien dalam proses asuhan]"));
                masalahTingkatAsuhan.setSelected(line.contains("[Tingkat Asuhan yang tidak sesuai dengan regulasi dan/atau norma]"));
                masalahKompleks.setSelected(line.contains("[Pasien dengan perawatan kompleks]"));
                masalahPerburukan.setSelected(line.contains("[Pasien mengalami penurunan / perburukan kondisi / peningkatan komplikasi]"));
            } else if (line.startsWith("- Masalah Spiritual/Psiko/Sosial/Ekonomi/Budaya:")) {
                masalahFinansial.setSelected(line.contains("[Kendala finansial akibat adanya perburukan kondisi, peningkatan komplikasi]"));
                masalahSistemPembayaran.setSelected(line.contains("[Kendala system pembayaran]"));
                masalahDukunganKeluarga.setSelected(line.contains("[Kurangnya dukungan keluarga berdampak pada perawatan]"));
            } else if (line.startsWith("- Pemahaman yang kurang memadai:")) {
                masalahPengetahuan.setSelected(line.contains("[Pengetahuan pasien / keluarga tentang Kesehatan yang rendah]"));
                masalahKomplain.setSelected(line.contains("[Resiko tinggi komplain]"));
                masalahReadmission.setSelected(line.contains("[Pasien readmission]"));
            } else if (line.startsWith("- Kontinuitas pelayanan:")) {
                masalahPemulangan.setSelected(line.contains("[Rencana pemulangan yang belum memenuhi kriteria dan/atau penundaan pemulangan]"));
                masalahUtilisasi.setSelected(line.contains("[Over dan/atau under utilisasi layanan atas dasar regulasi yang berlaku]"));
                masalahRujukan.setSelected(line.contains("[Rujukan ke fasilitas Kesehatan lain]"));
            }
        }
    }

    private void parseRencana(String val) {
        if (val == null || val.isEmpty()) return;
        String[] lines = val.split("\n");
        for (String line : lines) {
            if (line.startsWith("- Utilisasi/Mutu Asuhan:")) {
                rencanaUtilisasi.setSelected(line.contains("[Kaji efektifitas pelayanan di ruangan oleh PPA"));
            } else if (line.startsWith("- Advokasi:")) {
                rencanaAdvokasiInformasi.setSelected(line.contains("[Pastikan pasien / keluarga mendapatkan informasi kondisi kesehatannya dari DPJP / PPA]"));
                rencanaAdvokasiDiskusi.setSelected(line.contains("[Beri kesempatan pasien / keluarga untuk berdiskusi tentang hal yang belum dipahami]"));
                rencanaAdvokasiDukungan.setSelected(line.contains("[Beri dukungan moral / spiritual pada pasien / keluarga terhadap perburukan kondisi kesehatan]"));
            } else if (line.startsWith("- Edukasi:")) {
                rencanaEdukasi.setSelected(line.contains("[Beri informasi / edukasi tambahan jika diperlukan]"));
            } else if (line.startsWith("- Koordinasi:")) {
                rencanaKoordinasiMasalah.setSelected(line.contains("[Diskusikan dengan DPJP / PPA / Pimpinan RS jika ditemukan masalah"));
                rencanaKoordinasiIntensif.setSelected(line.contains("[Diskusikan dengan DPJP / PPA jika pasien diindikasi perawatan ruang intensif"));
                rencanaKoordinasiSaran.setSelected(line.contains("[Beri saran pada DPJP / PPA untuk melakukan pembahasan kasus kompleks"));
                rencanaKoordinasiTerminasi.setSelected(line.contains("[Diskusikan dengan DPJP / PPA untuk melakukan terminasi layanan MPP]"));
            } else if (line.startsWith("- Kolaborasi:")) {
                rencanaKolaborasiDPJP.setSelected(line.contains("[DPJP]"));
                rencanaKolaborasiDietisien.setSelected(line.contains("[Dietisien]"));
                rencanaKolaborasiKeuangan.setSelected(line.contains("[Bagian keuangan]"));
                rencanaKolaborasiSpesialis.setSelected(line.contains("[Dokter Spesialis lainnya:"));
                rencanaKolaborasiSpesialisSebutkan.setText(extractBetween(line, "[Dokter Spesialis lainnya: ", "]"));
                rencanaKolaborasiFisioterapis.setSelected(line.contains("[Fisioterapis]"));
                rencanaKolaborasiPimpinan.setSelected(line.contains("[Pimpinan RS]"));
                rencanaKolaborasiPerawat.setSelected(line.contains("[Perawat]"));
                rencanaKolaborasiApoteker.setSelected(line.contains("[Apoteker]"));
                rencanaKolaborasiPsikolog.setSelected(line.contains("[Psikolog]"));
                rencanaKolaborasiRohaniawan.setSelected(line.contains("[Petugas Rohaniawan]"));
                rencanaKolaborasiLainnyaSebutkan.setText(extractBetween(line, "[Lainnya: ", "]"));
            } else if (line.startsWith("- Evaluasi:")) {
                rencanaEvaluasiFollowUp.setSelected(line.contains("[Follow up hasil pembahasan masalah kompleks:"));
                rencanaEvaluasiFollowUpSebutkan.setText(extractBetween(line, "[Follow up hasil pembahasan masalah kompleks: ", "]"));
                rencanaEvaluasiEfektifitas.setSelected(line.contains("[Evaluasi efektifitas pelayanan (terapi DPJP, tindakan keperawatan, Pemeriksaan penunjang dan Tindakan PPA lainnya):"));
                rencanaEvaluasiEfektifitasSebutkan.setText(extractBetween(line, "[Evaluasi efektifitas pelayanan (terapi DPJP, tindakan keperawatan, Pemeriksaan penunjang dan Tindakan PPA lainnya): ", "]"));
            }
        }
    }

    private void clearCustomFields() {
        ewsGroup.clearSelection();
        ewsAnalisis.setText("");
        adlGroup.clearSelection();
        adlSebutkan.setText("");
        riwayatGroup.clearSelection();
        riwayatPenyakitKronisSebutkan.setText("");
        riwayatLainSebutkan.setText("");
        kebiasaanMerokok.setSelected(false);
        kebiasaanAlkohol.setSelected(false);
        kebiasaanLain.setSelected(false);
        kebiasaanLainSebutkan.setText("");
        perilakuGroup.clearSelection();
        perilakuAgamaSebutkan.setText("");
        perilakuSosialSebutkan.setText("");
        perilakuBudayaSebutkan.setText("");
        
        lingkunganTidakAda.setSelected(false);
        lingkunganTidakMenerima.setSelected(false);
        lingkunganSendirian.setSelected(false);
        lingkunganJauhFaskes.setSelected(false);
        lingkunganPanti.setSelected(false);
        lingkunganDekatFaskes.setSelected(false);
        lingkunganLain.setSelected(false);
        lingkunganLainSebutkan.setText("");
        
        keluargaTidakAda.setSelected(false);
        keluargaTidakMenerima.setSelected(false);
        keluargaTidakMenunggui.setSelected(false);
        keluargaTidakMampu.setSelected(false);
        keluargaTidakBisaDihubungi.setSelected(false);
        keluargaTidakTahu.setSelected(false);
        keluargaLain.setSelected(false);
        keluargaLainSebutkan.setText("");
        
        finansialTidakAda.setSelected(false);
        finansialAsuransi.setSelected(false);
        finansialTidakMampu.setSelected(false);
        finansialTidakAdaPj.setSelected(false);
        finansialBelumAdaAsuransi.setSelected(false);
        finansialMelebihiKlaim.setSelected(false);
        finansialLain.setSelected(false);
        finansialLainSebutkan.setText("");
        
        mentalGroup.clearSelection();
        mentalTidakBaikSebutkan.setText("");
        alternatifGroup.clearSelection();
        alternatifAdaSebutkan.setText("");
        pemahamanGroup.clearSelection();
        pemahamanKurangSebutkan.setText("");
        
        harapanMembaik.setSelected(false);
        harapanTindakan.setSelected(false);
        harapanPasrah.setSelected(false);
        harapanLain.setSelected(false);
        harapanLainSebutkan.setText("");
        
        asuransiGroup.clearSelection();
        traumaGroup.clearSelection();
        traumaAdaSebutkan.setText("");
        legalGroup.clearSelection();
        legalDibutuhkanSebutkan.setText("");
        
        masalahKepatuhan.setSelected(false);
        masalahTingkatAsuhan.setSelected(false);
        masalahKompleks.setSelected(false);
        masalahPerburukan.setSelected(false);
        masalahFinansial.setSelected(false);
        masalahSistemPembayaran.setSelected(false);
        masalahDukunganKeluarga.setSelected(false);
        masalahPengetahuan.setSelected(false);
        masalahKomplain.setSelected(false);
        masalahReadmission.setSelected(false);
        masalahPemulangan.setSelected(false);
        masalahUtilisasi.setSelected(false);
        masalahRujukan.setSelected(false);
        
        rencanaUtilisasi.setSelected(false);
        rencanaAdvokasiInformasi.setSelected(false);
        rencanaAdvokasiDiskusi.setSelected(false);
        rencanaAdvokasiDukungan.setSelected(false);
        rencanaEdukasi.setSelected(false);
        rencanaKoordinasiMasalah.setSelected(false);
        rencanaKoordinasiIntensif.setSelected(false);
        rencanaKoordinasiSaran.setSelected(false);
        rencanaKoordinasiTerminasi.setSelected(false);
        
        rencanaKolaborasiDPJP.setSelected(false);
        rencanaKolaborasiDietisien.setSelected(false);
        rencanaKolaborasiKeuangan.setSelected(false);
        rencanaKolaborasiSpesialis.setSelected(false);
        rencanaKolaborasiSpesialisSebutkan.setText("");
        rencanaKolaborasiFisioterapis.setSelected(false);
        rencanaKolaborasiPimpinan.setSelected(false);
        rencanaKolaborasiPerawat.setSelected(false);
        rencanaKolaborasiApoteker.setSelected(false);
        rencanaKolaborasiPsikolog.setSelected(false);
        rencanaKolaborasiRohaniawan.setSelected(false);
        rencanaKolaborasiLainnyaSebutkan.setText("");
        
        rencanaEvaluasiFollowUp.setSelected(false);
        rencanaEvaluasiFollowUpSebutkan.setText("");
        rencanaEvaluasiEfektifitas.setSelected(false);
        rencanaEvaluasiEfektifitasSebutkan.setText("");
    }

    private void initBaru() {
        // 1. Adjust database schema
        adjustDatabaseSchema();

        // 2. Hide old components
        scrollPane5.setVisible(false);
        scrollPane7.setVisible(false);
        Scroll6.setVisible(false);
        scrollPane3.setVisible(false);
        scrollPane4.setVisible(false);
        scrollPane2.setVisible(false);
        jSeparator3.setVisible(false);
        jLabel9.setVisible(false);
        jLabel31.setVisible(false);
        jLabel32.setVisible(false);
        jLabel40.setVisible(false);
        jLabel22.setVisible(false);
        jLabel94.setVisible(false);
        TCariMasalah.setVisible(false);
        BtnCariMasalah.setVisible(false);
        BtnTambahMasalah.setVisible(false);
        BtnAllMasalah.setVisible(false);
        label12.setVisible(false);

        // 3. Initialize groups
        ewsGroup = new javax.swing.ButtonGroup();
        adlGroup = new javax.swing.ButtonGroup();
        riwayatGroup = new javax.swing.ButtonGroup();
        perilakuGroup = new javax.swing.ButtonGroup();
        mentalGroup = new javax.swing.ButtonGroup();
        alternatifGroup = new javax.swing.ButtonGroup();
        pemahamanGroup = new javax.swing.ButtonGroup();
        asuransiGroup = new javax.swing.ButtonGroup();
        traumaGroup = new javax.swing.ButtonGroup();
        legalGroup = new javax.swing.ButtonGroup();
        kebiasaanGroup = new javax.swing.ButtonGroup();
        lingkunganGroup = new javax.swing.ButtonGroup();
        keluargaGroup = new javax.swing.ButtonGroup();
        finansialGroup = new javax.swing.ButtonGroup();
        harapanGroup = new javax.swing.ButtonGroup();
        identifikasiGroup = new javax.swing.ButtonGroup();
        rencanaGroup = new javax.swing.ButtonGroup();

        // 4. Create new components
        // Section 1: ASESMEN MANAJEMEN PELAYANAN PASIEN
        createTitleLabel("ASESMEN MANAJEMEN PELAYANAN PASIEN", 10, 135, 400, 23);
        
        createLabel("Skor EWS :", 10, 165, 150, 23);
        ews01 = createRadioButton("Skor 0 – 1 (warna hijau : normal)", 160, 165, 220, 23, ewsGroup);
        ews23 = createRadioButton("Skor 2 -3 (warna kuning : rendah)", 390, 165, 220, 23, ewsGroup);
        ews46 = createRadioButton("Skor 4 – 6 (warna orange : sedang)", 160, 190, 220, 23, ewsGroup);
        ews7plus = createRadioButton("Skor ≥ 7 (warna merah : tinggi)", 390, 190, 220, 23, ewsGroup);
        createLabel("Analisis :", 10, 215, 150, 23);
        ewsAnalisis = createTextField(160, 215, 670, 23);

        createLabel("Penurunan Kemampuan ADL's :", 10, 245, 250, 23);
        adlTidakAda = createRadioButton("Tidak Ada Hambatan", 270, 245, 150, 23, adlGroup);
        adlAda = createRadioButton("Ada, sebutkan :", 430, 245, 110, 23, adlGroup);
        adlSebutkan = createTextField(540, 245, 290, 23);

        createLabel("Riwayat Kesehatan/Kebiasaan :", 10, 275, 250, 23);
        riwayatTidakAda = createRadioButton("Tidak Ada", 270, 275, 150, 23, riwayatGroup);
        riwayatPenyakitKronis = createRadioButton("Penyakit Kronis, sebutkan :", 270, 300, 200, 23, riwayatGroup);
        riwayatPenyakitKronisSebutkan = createTextField(470, 300, 360, 23);
        
        createLabel("Pola Kebiasaan :", 270, 325, 150, 23);
        kebiasaanMerokok = createRadioButton("Merokok", 400, 325, 90, 23, kebiasaanGroup);
        kebiasaanAlkohol = createRadioButton("Konsumsi Alkohol", 500, 325, 130, 23, kebiasaanGroup);
        kebiasaanLain = createRadioButton("Lain-lain :", 640, 325, 90, 23, kebiasaanGroup);
        kebiasaanLainSebutkan = createTextField(730, 325, 100, 23);
        
        riwayatLain = createRadioButton("Lain-lain, sebutkan :", 270, 350, 150, 23, riwayatGroup);
        riwayatLainSebutkan = createTextField(420, 350, 410, 23);

        createLabel("Perilaku Spiritual/Sosial/Kultural :", 10, 380, 250, 23);
        perilakuTidakAda = createRadioButton("Tidak Ada Hambatan", 270, 380, 150, 23, perilakuGroup);
        perilakuAgama = createRadioButton("Nilai keyakinan agama tertentu :", 270, 405, 210, 23, perilakuGroup);
        perilakuAgamaSebutkan = createTextField(490, 405, 340, 23);
        perilakuSosial = createRadioButton("Nilai keyakinan sosial tertentu :", 270, 430, 210, 23, perilakuGroup);
        perilakuSosialSebutkan = createTextField(490, 430, 340, 23);
        perilakuBudaya = createRadioButton("Nilai budaya tertentu :", 270, 455, 210, 23, perilakuGroup);
        perilakuBudayaSebutkan = createTextField(490, 455, 340, 23);
        createLabel("Kendala Lingkungan Tempat Tinggal :", 10, 485, 250, 23);
        lingkunganTidakAda = createRadioButton("Tidak Ada Hambatan", 270, 485, 250, 23, lingkunganGroup);
        lingkunganTidakMenerima = createRadioButton("Masyarakat tidak menerima pasien", 530, 485, 290, 23, lingkunganGroup);
        lingkunganSendirian = createRadioButton("Pasien tinggal sendirian", 270, 510, 250, 23, lingkunganGroup);
        lingkunganJauhFaskes = createRadioButton("Rumah tinggal jauh dari faskes", 530, 510, 290, 23, lingkunganGroup);
        lingkunganPanti = createRadioButton("Pasien tinggal di panti", 270, 535, 250, 23, lingkunganGroup);
        lingkunganDekatFaskes = createRadioButton("Rumah tinggal dekat dengan faskes", 530, 535, 290, 23, lingkunganGroup);
        lingkunganLain = createRadioButton("Lain-lain, sebutkan :", 270, 560, 150, 23, lingkunganGroup);
        lingkunganLainSebutkan = createTextField(420, 560, 410, 23);

        createLabel("Dukungan Keluarga & Kemampuan :", 10, 590, 250, 23);
        keluargaTidakAda = createRadioButton("Tidak Ada Hambatan", 270, 590, 250, 23, keluargaGroup);
        keluargaTidakMenerima = createRadioButton("Keluarga tidak mau menerima kondisi pasien", 530, 590, 290, 23, keluargaGroup);
        keluargaTidakMenunggui = createRadioButton("Keluarga tidak pernah menunggui", 270, 615, 250, 23, keluargaGroup);
        keluargaTidakMampu = createRadioButton("Keluarga tidak mampu merawat", 530, 615, 290, 23, keluargaGroup);
        keluargaTidakBisaDihubungi = createRadioButton("Keluarga tidak bisa dihubungi", 270, 640, 250, 23, keluargaGroup);
        keluargaTidakTahu = createRadioButton("Keluarga tidak tahu perkembangan kondisi", 530, 640, 290, 23, keluargaGroup);
        keluargaLain = createRadioButton("Lain-lain, sebutkan :", 270, 665, 150, 23, keluargaGroup);
        keluargaLainSebutkan = createTextField(420, 665, 410, 23);

        createLabel("Kemampuan Finansial :", 10, 695, 250, 23);
        finansialTidakAda = createRadioButton("Tidak Ada Hambatan", 270, 695, 250, 23, finansialGroup);
        finansialAsuransi = createRadioButton("Ada masalah Asuransi", 530, 695, 290, 23, finansialGroup);
        finansialTidakMampu = createRadioButton("Pasien tidak mampu dan/atau miskin", 270, 720, 250, 23, finansialGroup);
        finansialTidakAdaPj = createRadioButton("Tidak ada penanggung jawab pembiayaan", 530, 720, 290, 23, finansialGroup);
        finansialBelumAdaAsuransi = createRadioButton("Pasien belum memiliki Asuransi", 270, 745, 250, 23, finansialGroup);
        finansialMelebihiKlaim = createRadioButton("Total biaya melebihi klaim asuransi", 530, 745, 290, 23, finansialGroup);
        finansialLain = createRadioButton("Lain-lain, sebutkan :", 270, 770, 150, 23, finansialGroup);
        finansialLainSebutkan = createTextField(420, 770, 410, 23);

        createLabel("Kesehatan Mental dan Kognitif :", 10, 800, 250, 23);
        mentalBaik = createRadioButton("Baik", 270, 800, 100, 23, mentalGroup);
        mentalTidakBaik = createRadioButton("Tidak baik, jelaskan :", 370, 800, 150, 23, mentalGroup);
        mentalTidakBaikSebutkan = createTextField(520, 800, 310, 23);

        createLabel("Riwayat Pengobatan Alternatif :", 10, 830, 250, 23);
        alternatifTidakAda = createRadioButton("Tidak Ada", 270, 830, 100, 23, alternatifGroup);
        alternatifAda = createRadioButton("Ada, sebutkan :", 370, 830, 150, 23, alternatifGroup);
        alternatifAdaSebutkan = createTextField(520, 830, 310, 23);

        createLabel("Pemahaman Terhadap Kesehatan :", 10, 860, 250, 23);
        pemahamanBaik = createRadioButton("Baik", 270, 860, 100, 23, pemahamanGroup);
        pemahamanKurang = createRadioButton("Kurang, keterangan :", 370, 860, 150, 23, pemahamanGroup);
        pemahamanKurangSebutkan = createTextField(520, 860, 310, 23);

        createLabel("Harapan Terhadap Asuhan :", 10, 890, 250, 23);
        harapanMembaik = createRadioButton("Kondisi pasien membaik dan/atau sembuh", 270, 890, 300, 23, harapanGroup);
        harapanTindakan = createRadioButton("Segera dilakukan tindakan", 270, 915, 300, 23, harapanGroup);
        harapanPasrah = createRadioButton("Keluarga pasrah terhadap kondisi pasien", 270, 940, 300, 23, harapanGroup);
        harapanLain = createRadioButton("Lain-lain, sebutkan :", 270, 965, 150, 23, harapanGroup);
        harapanLainSebutkan = createTextField(420, 965, 410, 23);

        createLabel("Status Asuransi :", 10, 995, 250, 23);
        asuransiAktif = createRadioButton("Aktif", 270, 995, 100, 23, asuransiGroup);
        asuransiTidakAktif = createRadioButton("Tidak Aktif / tidak memiliki", 370, 995, 180, 23, asuransiGroup);
        asuransiUmum = createRadioButton("Dengan Layanan Umum / bayar mandiri", 550, 995, 280, 23, asuransiGroup);

        createLabel("Riwayat Trauma / Kekerasan :", 10, 1025, 250, 23);
        traumaAda = createRadioButton("Ada, sebutkan :", 270, 1025, 120, 23, traumaGroup);
        traumaAdaSebutkan = createTextField(390, 1025, 440, 23);
        traumaTidakAda = createRadioButton("Tidak ada", 270, 1050, 150, 23, traumaGroup);

        createLabel("Aspek Legal / Advokasi :", 10, 1080, 250, 23);
        legalTidakDibutuhkan = createRadioButton("Tidak Dibutuhkan", 270, 1080, 150, 23, legalGroup);
        legalDibutuhkan = createRadioButton("Dibutuhkan, keterangan :", 270, 1105, 150, 23, legalGroup);
        legalDibutuhkanSebutkan = createTextField(420, 1105, 410, 23);

        // Separator 1
        javax.swing.JSeparator sep1 = new javax.swing.JSeparator();
        sep1.setBounds(0, 1145, 870, 2);
        FormInput.add(sep1);

        // Section 2: IDENTIFIKASI MASALAH
        createTitleLabel("IDENTIFIKASI MASALAH", 10, 1165, 400, 23);
        
        createLabel("Masalah Kesehatan :", 10, 1195, 250, 23);
        masalahKepatuhan = createRadioButton("Ketidakpatuhan pasien dalam proses asuhan", 270, 1195, 550, 23, identifikasiGroup);
        masalahTingkatAsuhan = createRadioButton("Tingkat Asuhan yang tidak sesuai dengan regulasi dan/atau norma", 270, 1220, 550, 23, identifikasiGroup);
        masalahKompleks = createRadioButton("Pasien dengan perawatan kompleks", 270, 1245, 550, 23, identifikasiGroup);
        masalahPerburukan = createRadioButton("Pasien mengalami penurunan / perburukan kondisi / peningkatan komplikasi", 270, 1270, 550, 23, identifikasiGroup);

        createLabel("Masalah Spiritual, Psiko, Sosial, Ekonomi, Budaya :", 10, 1300, 250, 23);
        masalahFinansial = createRadioButton("Kendala finansial akibat adanya perburukan kondisi, peningkatan komplikasi", 270, 1300, 550, 23, identifikasiGroup);
        masalahSistemPembayaran = createRadioButton("Kendala system pembayaran", 270, 1325, 550, 23, identifikasiGroup);
        masalahDukunganKeluarga = createRadioButton("Kurangnya dukungan keluarga berdampak pada perawatan", 270, 1350, 550, 23, identifikasiGroup);

        createLabel("Pemahaman Yang Kurang Memadai :", 10, 1380, 250, 23);
        masalahPengetahuan = createRadioButton("Pengetahuan pasien / keluarga tentang Kesehatan yang rendah", 270, 1380, 550, 23, identifikasiGroup);
        masalahKomplain = createRadioButton("Resiko tinggi komplain", 270, 1405, 550, 23, identifikasiGroup);
        masalahReadmission = createRadioButton("Pasien readmission", 270, 1430, 550, 23, identifikasiGroup);

        createLabel("Kontinuitas Pelayanan :", 10, 1460, 250, 23);
        masalahPemulangan = createRadioButton("Rencana pemulangan yang belum memenuhi kriteria dan/atau penundaan", 270, 1460, 550, 23, identifikasiGroup);
        masalahUtilisasi = createRadioButton("Over dan/atau under utilisasi layanan atas dasar regulasi yang berlaku", 270, 1485, 550, 23, identifikasiGroup);
        masalahRujukan = createRadioButton("Rujukan ke fasilitas Kesehatan lain", 270, 1510, 550, 23, identifikasiGroup);

        // Separator 2
        javax.swing.JSeparator sep2 = new javax.swing.JSeparator();
        sep2.setBounds(0, 1550, 870, 2);
        FormInput.add(sep2);

        // Section 3: PERENCANAAN MPP
        createTitleLabel("PERENCANAAN MPP", 10, 1570, 400, 23);
        
        createLabel("Utilisasi / Mutu Asuhan :", 10, 1600, 250, 23);
        rencanaUtilisasi = createRadioButton("Kaji efektifitas pelayanan di ruangan oleh PPA (terapi DPJP, dll)", 270, 1600, 550, 23, rencanaGroup);

        createLabel("Advokasi :", 10, 1630, 250, 23);
        rencanaAdvokasiInformasi = createRadioButton("Pastikan pasien / keluarga mendapatkan informasi kondisi kesehatannya", 270, 1630, 550, 23, rencanaGroup);
        rencanaAdvokasiDiskusi = createRadioButton("Beri kesempatan pasien / keluarga untuk berdiskusi tentang hal", 270, 1655, 550, 23, rencanaGroup);
        rencanaAdvokasiDukungan = createRadioButton("Beri dukungan moral / spiritual pada pasien / keluarga", 270, 1680, 550, 23, rencanaGroup);

        createLabel("Edukasi :", 10, 1710, 250, 23);
        rencanaEdukasi = createRadioButton("Beri informasi / edukasi tambahan jika diperlukan", 270, 1710, 550, 23, rencanaGroup);

        createLabel("Koordinasi :", 10, 1740, 250, 23);
        rencanaKoordinasiMasalah = createRadioButton("Diskusikan dengan DPJP / PPA / Pimpinan RS jika ditemukan masalah", 270, 1740, 550, 23, rencanaGroup);
        rencanaKoordinasiIntensif = createRadioButton("Diskusikan dengan DPJP / PPA jika pasien diindikasi perawatan intensif", 270, 1765, 550, 23, rencanaGroup);
        rencanaKoordinasiSaran = createRadioButton("Beri saran pada DPJP / PPA untuk pembahasan kasus kompleks", 270, 1790, 550, 23, rencanaGroup);
        rencanaKoordinasiTerminasi = createRadioButton("Diskusikan dengan DPJP / PPA untuk melakukan terminasi layanan MPP", 270, 1815, 550, 23, rencanaGroup);

        createLabel("Kolaborasi :", 10, 1845, 250, 23);
        rencanaKolaborasiDPJP = createRadioButton("DPJP", 270, 1845, 150, 23, rencanaGroup);
        rencanaKolaborasiDietisien = createRadioButton("Dietisien", 430, 1845, 150, 23, rencanaGroup);
        rencanaKolaborasiKeuangan = createRadioButton("Bagian keuangan", 590, 1845, 180, 23, rencanaGroup);
        
        rencanaKolaborasiSpesialis = createRadioButton("Dokter Spesialis lainnya (sebutkan) :", 270, 1870, 210, 23, rencanaGroup);
        rencanaKolaborasiSpesialisSebutkan = createTextField(480, 1870, 350, 23);
        
        rencanaKolaborasiFisioterapis = createRadioButton("Fisioterapis", 270, 1895, 150, 23, rencanaGroup);
        rencanaKolaborasiPimpinan = createRadioButton("Pimpinan RS", 430, 1895, 150, 23, rencanaGroup);
        
        rencanaKolaborasiPerawat = createRadioButton("Perawat", 270, 1920, 150, 23, rencanaGroup);
        rencanaKolaborasiApoteker = createRadioButton("Apoteker", 430, 1920, 150, 23, rencanaGroup);
        
        rencanaKolaborasiPsikolog = createRadioButton("Psikolog", 270, 1945, 150, 23, rencanaGroup);
        rencanaKolaborasiRohaniawan = createRadioButton("Petugas Rohaniawan", 430, 1945, 150, 23, rencanaGroup);
        
        createLabel("Lainnya :", 270, 1970, 80, 23);
        rencanaKolaborasiLainnyaSebutkan = createTextField(350, 1970, 480, 23);

        createLabel("Evaluasi :", 10, 2000, 250, 23);
        rencanaEvaluasiFollowUp = createRadioButton("Follow up hasil pembahasan masalah kompleks (jelaskan) :", 270, 2000, 320, 23, rencanaGroup);
        rencanaEvaluasiFollowUpSebutkan = createTextField(590, 2000, 240, 23);
        rencanaEvaluasiEfektifitas = createRadioButton("Evaluasi efektifitas pelayanan (terapi DPJP, dll) (jelaskan) :", 270, 2025, 560, 23, rencanaGroup);
        rencanaEvaluasiEfektifitasSebutkan = createTextField(270, 2050, 560, 23);

        // Update layout preference
        FormInput.setPreferredSize(new Dimension(870, 2100));
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir, " +
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,mpp_evaluasi_form_a.tanggal, " +
                    "ifnull(bangsal.nm_bangsal,'Ranap Gabung') as ruang,ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"+
                                            "mpp_evaluasi_form_a.kd_dokter,dokterpj.nm_dokter as dpjp,mpp_evaluasi_form_a.kd_konsulan,dokterkonsulen.nm_dokter as konsulan, " +
                        "'' as diagnosis,'' as kelompok,mpp_evaluasi_form_a.assesmen_teks as assesmen, mpp_evaluasi_form_a.identifikasi_teks as identifikasi, mpp_evaluasi_form_a.rencana_teks as rencana,mpp_evaluasi_form_a.nip,petugas.nama "+
"from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join mpp_evaluasi_form_a on mpp_evaluasi_form_a.no_rawat=reg_periksa.no_rawat " +
                    "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                    "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join dokter as dokterpj on mpp_evaluasi_form_a.kd_dokter=dokterpj.kd_dokter " +
                    "inner join dokter as dokterkonsulen on mpp_evaluasi_form_a.kd_konsulan=dokterkonsulen.kd_dokter " +
                    "inner join petugas on mpp_evaluasi_form_a.nip=petugas.nip " +
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                    "mpp_evaluasi_form_a.tanggal between ? and ? group by mpp_evaluasi_form_a.no_rawat,mpp_evaluasi_form_a.tanggal order by mpp_evaluasi_form_a.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir, " +
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,mpp_evaluasi_form_a.tanggal, " +
                    "ifnull(bangsal.nm_bangsal,'Ranap Gabung') as ruang,ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"+
                                            "mpp_evaluasi_form_a.kd_dokter,dokterpj.nm_dokter as dpjp,mpp_evaluasi_form_a.kd_konsulan,dokterkonsulen.nm_dokter as konsulan, " +
                        "'' as diagnosis,'' as kelompok,mpp_evaluasi_form_a.assesmen_teks as assesmen, mpp_evaluasi_form_a.identifikasi_teks as identifikasi, mpp_evaluasi_form_a.rencana_teks as rencana,mpp_evaluasi_form_a.nip,petugas.nama "+
"from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join mpp_evaluasi_form_a on mpp_evaluasi_form_a.no_rawat=reg_periksa.no_rawat " +
                    "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                    "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join dokter as dokterpj on mpp_evaluasi_form_a.kd_dokter=dokterpj.kd_dokter " +
                    "inner join dokter as dokterkonsulen on mpp_evaluasi_form_a.kd_konsulan=dokterkonsulen.kd_dokter " +
                    "inner join petugas on mpp_evaluasi_form_a.nip=petugas.nip " +
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                    "mpp_evaluasi_form_a.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or mpp_evaluasi_form_a.nip like ? or petugas.nama like ?) "+
                    "group by mpp_evaluasi_form_a.no_rawat,mpp_evaluasi_form_a.tanggal order by mpp_evaluasi_form_a.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("alamat"),rs.getString("tanggal"),
                        rs.getString("kamar")+" "+rs.getString("ruang"),rs.getString("tgl_masuk")+" "+rs.getString("jam_masuk"),rs.getString("kd_dokter"),rs.getString("dpjp"),rs.getString("kd_konsulan"),
                        rs.getString("konsulan"),rs.getString("diagnosis"),rs.getString("kelompok"),rs.getString("assesmen"),rs.getString("identifikasi"),rs.getString("rencana"),rs.getString("nip"),
                        rs.getString("nama")
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

    public void emptTeks() {
        TglEvaluasi.setDate(new Date());
        KdDok1.setText("");
        KdDok2.setText("");
        TDiagnosis.setText("");
        TKelompok.setText("");
        Assemen.setText("");
        Identifikasi.setText("");
        Perencanaan.setText("");
        clearCustomFields();
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        TabRawat.setSelectedIndex(0);
        TDokter1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            Kamar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            TglMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            KdDok1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KdDok2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TDokter2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            TDiagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TKelompok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Assemen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Identifikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Perencanaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());

            try {
                java.sql.PreparedStatement psd = koneksi.prepareStatement("SELECT * FROM mpp_evaluasi_form_a WHERE no_rawat=? AND tanggal=?");
                try {
                    psd.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    psd.setString(2, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
                    java.sql.ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
setGroupValue(ewsGroup, rs.getString("skor_ews"));
                        ewsAnalisis.setText(rs.getString("analisis_ews")!=null?rs.getString("analisis_ews"):"");
                        setGroupValue(adlGroup, rs.getString("penurunan_adl"));
                        adlSebutkan.setText(rs.getString("adl_sebutkan")!=null?rs.getString("adl_sebutkan"):"");
                        setGroupValue(riwayatGroup, rs.getString("riwayat_kesehatan"));
                        riwayatPenyakitKronisSebutkan.setText(rs.getString("riwayat_penyakit_kronis")!=null?rs.getString("riwayat_penyakit_kronis"):"");
                        kebiasaanMerokok.setSelected("Ya".equals(rs.getString("kebiasaan_merokok")));
                        kebiasaanAlkohol.setSelected("Ya".equals(rs.getString("kebiasaan_alkohol")));
                        kebiasaanLain.setSelected("Ya".equals(rs.getString("kebiasaan_lain")));
                        kebiasaanLainSebutkan.setText(rs.getString("kebiasaan_lain_sebutkan")!=null?rs.getString("kebiasaan_lain_sebutkan"):"");
                        riwayatLainSebutkan.setText(rs.getString("riwayat_lain_sebutkan")!=null?rs.getString("riwayat_lain_sebutkan"):"");
                        setGroupValue(perilakuGroup, rs.getString("perilaku"));
                        perilakuAgamaSebutkan.setText(rs.getString("perilaku_agama")!=null?rs.getString("perilaku_agama"):"");
                        perilakuSosialSebutkan.setText(rs.getString("perilaku_sosial")!=null?rs.getString("perilaku_sosial"):"");
                        perilakuBudayaSebutkan.setText(rs.getString("perilaku_budaya")!=null?rs.getString("perilaku_budaya"):"");
                        lingkunganTidakAda.setSelected("Ya".equals(rs.getString("lingkungan_tidak_ada")));
                        lingkunganTidakMenerima.setSelected("Ya".equals(rs.getString("lingkungan_tidak_menerima")));
                        lingkunganSendirian.setSelected("Ya".equals(rs.getString("lingkungan_sendirian")));
                        lingkunganJauhFaskes.setSelected("Ya".equals(rs.getString("lingkungan_jauh_faskes")));
                        lingkunganPanti.setSelected("Ya".equals(rs.getString("lingkungan_panti")));
                        lingkunganDekatFaskes.setSelected("Ya".equals(rs.getString("lingkungan_dekat_faskes")));
                        lingkunganLain.setSelected("Ya".equals(rs.getString("lingkungan_lain")));
                        lingkunganLainSebutkan.setText(rs.getString("lingkungan_lain_sebutkan")!=null?rs.getString("lingkungan_lain_sebutkan"):"");
                        keluargaTidakAda.setSelected("Ya".equals(rs.getString("keluarga_tidak_ada")));
                        keluargaTidakMenerima.setSelected("Ya".equals(rs.getString("keluarga_tidak_menerima")));
                        keluargaTidakMenunggui.setSelected("Ya".equals(rs.getString("keluarga_tidak_menunggui")));
                        keluargaTidakMampu.setSelected("Ya".equals(rs.getString("keluarga_tidak_mampu")));
                        keluargaTidakBisaDihubungi.setSelected("Ya".equals(rs.getString("keluarga_tidak_bisa_dihubungi")));
                        keluargaTidakTahu.setSelected("Ya".equals(rs.getString("keluarga_tidak_tahu")));
                        keluargaLain.setSelected("Ya".equals(rs.getString("keluarga_lain")));
                        keluargaLainSebutkan.setText(rs.getString("keluarga_lain_sebutkan")!=null?rs.getString("keluarga_lain_sebutkan"):"");
                        finansialTidakAda.setSelected("Ya".equals(rs.getString("finansial_tidak_ada")));
                        finansialAsuransi.setSelected("Ya".equals(rs.getString("finansial_asuransi")));
                        finansialTidakMampu.setSelected("Ya".equals(rs.getString("finansial_tidak_mampu")));
                        finansialTidakAdaPj.setSelected("Ya".equals(rs.getString("finansial_tidak_ada_pj")));
                        finansialBelumAdaAsuransi.setSelected("Ya".equals(rs.getString("finansial_belum_ada_asuransi")));
                        finansialMelebihiKlaim.setSelected("Ya".equals(rs.getString("finansial_melebihi_klaim")));
                        finansialLain.setSelected("Ya".equals(rs.getString("finansial_lain")));
                        finansialLainSebutkan.setText(rs.getString("finansial_lain_sebutkan")!=null?rs.getString("finansial_lain_sebutkan"):"");
                        setGroupValue(mentalGroup, rs.getString("kesehatan_mental"));
                        mentalTidakBaikSebutkan.setText(rs.getString("mental_tidak_baik")!=null?rs.getString("mental_tidak_baik"):"");
                        setGroupValue(alternatifGroup, rs.getString("pengobatan_alternatif"));
                        alternatifAdaSebutkan.setText(rs.getString("alternatif_ada")!=null?rs.getString("alternatif_ada"):"");
                        setGroupValue(pemahamanGroup, rs.getString("pemahaman_kesehatan"));
                        pemahamanKurangSebutkan.setText(rs.getString("pemahaman_kurang")!=null?rs.getString("pemahaman_kurang"):"");
                        harapanMembaik.setSelected("Ya".equals(rs.getString("harapan_membaik")));
                        harapanTindakan.setSelected("Ya".equals(rs.getString("harapan_tindakan")));
                        harapanPasrah.setSelected("Ya".equals(rs.getString("harapan_pasrah")));
                        harapanLain.setSelected("Ya".equals(rs.getString("harapan_lain")));
                        harapanLainSebutkan.setText(rs.getString("harapan_lain_sebutkan")!=null?rs.getString("harapan_lain_sebutkan"):"");
                        setGroupValue(asuransiGroup, rs.getString("status_asuransi"));
                        setGroupValue(traumaGroup, rs.getString("riwayat_trauma"));
                        traumaAdaSebutkan.setText(rs.getString("trauma_ada")!=null?rs.getString("trauma_ada"):"");
                        setGroupValue(legalGroup, rs.getString("aspek_legal"));
                        legalDibutuhkanSebutkan.setText(rs.getString("legal_dibutuhkan")!=null?rs.getString("legal_dibutuhkan"):"");
                        masalahKepatuhan.setSelected("Ya".equals(rs.getString("masalah_kepatuhan")));
                        masalahTingkatAsuhan.setSelected("Ya".equals(rs.getString("masalah_tingkat_asuhan")));
                        masalahKompleks.setSelected("Ya".equals(rs.getString("masalah_kompleks")));
                        masalahPerburukan.setSelected("Ya".equals(rs.getString("masalah_perburukan")));
                        masalahFinansial.setSelected("Ya".equals(rs.getString("masalah_finansial")));
                        masalahSistemPembayaran.setSelected("Ya".equals(rs.getString("masalah_sistem_pembayaran")));
                        masalahDukunganKeluarga.setSelected("Ya".equals(rs.getString("masalah_dukungan_keluarga")));
                        masalahPengetahuan.setSelected("Ya".equals(rs.getString("masalah_pengetahuan")));
                        masalahKomplain.setSelected("Ya".equals(rs.getString("masalah_komplain")));
                        masalahReadmission.setSelected("Ya".equals(rs.getString("masalah_readmission")));
                        masalahPemulangan.setSelected("Ya".equals(rs.getString("masalah_pemulangan")));
                        masalahUtilisasi.setSelected("Ya".equals(rs.getString("masalah_utilisasi")));
                        masalahRujukan.setSelected("Ya".equals(rs.getString("masalah_rujukan")));
                        rencanaUtilisasi.setSelected("Ya".equals(rs.getString("rencana_utilisasi")));
                        rencanaAdvokasiInformasi.setSelected("Ya".equals(rs.getString("rencana_advokasi_informasi")));
                        rencanaAdvokasiDiskusi.setSelected("Ya".equals(rs.getString("rencana_advokasi_diskusi")));
                        rencanaAdvokasiDukungan.setSelected("Ya".equals(rs.getString("rencana_advokasi_dukungan")));
                        rencanaEdukasi.setSelected("Ya".equals(rs.getString("rencana_edukasi")));
                        rencanaKoordinasiMasalah.setSelected("Ya".equals(rs.getString("rencana_koordinasi_masalah")));
                        rencanaKoordinasiIntensif.setSelected("Ya".equals(rs.getString("rencana_koordinasi_intensif")));
                        rencanaKoordinasiSaran.setSelected("Ya".equals(rs.getString("rencana_koordinasi_saran")));
                        rencanaKoordinasiTerminasi.setSelected("Ya".equals(rs.getString("rencana_koordinasi_terminasi")));
                        rencanaKolaborasiDPJP.setSelected("Ya".equals(rs.getString("kolaborasi_dpjp")));
                        rencanaKolaborasiDietisien.setSelected("Ya".equals(rs.getString("kolaborasi_dietisien")));
                        rencanaKolaborasiKeuangan.setSelected("Ya".equals(rs.getString("kolaborasi_keuangan")));
                        rencanaKolaborasiSpesialis.setSelected("Ya".equals(rs.getString("kolaborasi_spesialis")));
                        rencanaKolaborasiSpesialisSebutkan.setText(rs.getString("kolaborasi_spesialis_sebutkan")!=null?rs.getString("kolaborasi_spesialis_sebutkan"):"");
                        rencanaKolaborasiFisioterapis.setSelected("Ya".equals(rs.getString("kolaborasi_fisioterapis")));
                        rencanaKolaborasiPimpinan.setSelected("Ya".equals(rs.getString("kolaborasi_pimpinan")));
                        rencanaKolaborasiPerawat.setSelected("Ya".equals(rs.getString("kolaborasi_perawat")));
                        rencanaKolaborasiApoteker.setSelected("Ya".equals(rs.getString("kolaborasi_apoteker")));
                        rencanaKolaborasiPsikolog.setSelected("Ya".equals(rs.getString("kolaborasi_psikolog")));
                        rencanaKolaborasiRohaniawan.setSelected("Ya".equals(rs.getString("kolaborasi_rohaniawan")));
                        rencanaKolaborasiLainnyaSebutkan.setText(rs.getString("kolaborasi_lainnya_sebutkan")!=null?rs.getString("kolaborasi_lainnya_sebutkan"):"");
                        rencanaEvaluasiFollowUp.setSelected("Ya".equals(rs.getString("evaluasi_follow_up")));
                        rencanaEvaluasiFollowUpSebutkan.setText(rs.getString("evaluasi_follow_up_sebutkan")!=null?rs.getString("evaluasi_follow_up_sebutkan"):"");
                        rencanaEvaluasiEfektifitas.setSelected("Ya".equals(rs.getString("evaluasi_efektifitas")));
                        rencanaEvaluasiEfektifitasSebutkan.setText(rs.getString("evaluasi_efektifitas_sebutkan")!=null?rs.getString("evaluasi_efektifitas_sebutkan"):"");

                    }
                } finally {
                    if (psd != null) psd.close();
                }
            } catch (Exception e) {
                System.out.println("Notif: " + e);
            }

            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Valid.SetTgl2(TglEvaluasi,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_mpp.kode_masalah,master_masalah_mpp.nama_masalah from master_masalah_mpp "+
                        "inner join mpp_evaluasi_masalah on mpp_evaluasi_masalah.kode_masalah=master_masalah_mpp.kode_masalah "+
                        "where mpp_evaluasi_masalah.no_rawat=? and mpp_evaluasi_masalah.tanggal=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop)as alamat,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal, "+
                    "ifnull(kamar_inap.kd_kamar,'RG') as kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                    "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                    "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where reg_periksa.no_rawat=? group by reg_periksa.no_rawat");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Alamat.setText(rs.getString("alamat"));
                    TglMasuk.setText(rs.getString("tgl_masuk")+" "+rs.getString("jam_masuk"));
                    Kamar.setText(rs.getString("kamar")+" "+rs.getString("nm_bangsal"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getmpp_skrining());
        BtnHapus.setEnabled(akses.getmpp_skrining());
        BtnEdit.setEnabled(akses.getmpp_skrining());
        BtnPrint.setEnabled(akses.getmpp_skrining());   
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void tampilMasalah() {
        try{
            Valid.tabelKosong(tabModeMasalah);
            file=new File("./cache/masalahmpp.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            ps=koneksi.prepareStatement("select * from master_masalah_mpp order by master_masalah_mpp.kode_masalah");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyem=iyem+"{\"KodeMasalah\":\""+rs.getString(1)+"\",\"NamaMasalah\":\""+rs.getString(2)+"\"},";
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
            fileWriter.write("{\"masalahmpp\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilMasalah2() {
        try{
            jml=0;
            for(i=0;i<tbIdentifikasiMPP.getRowCount();i++){
                if(tbIdentifikasiMPP.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbIdentifikasiMPP.getRowCount();i++){
                if(tbIdentifikasiMPP.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbIdentifikasiMPP.getValueAt(i,1).toString();
                    masalah[index]=tbIdentifikasiMPP.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            
            myObj = new FileReader("./cache/masalahmpp.iyem");
            root = mapper.readTree(myObj);
            response = root.path("masalahmpp");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("KodeMasalah").asText().toLowerCase().contains(TCariMasalah.getText().toLowerCase())||list.path("NamaMasalah").asText().toLowerCase().contains(TCariMasalah.getText().toLowerCase())){
                        tabModeMasalah.addRow(new Object[]{
                            false,list.path("KodeMasalah").asText(),list.path("NamaMasalah").asText()
                        });                    
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            FormMasalahRencana.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){   
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            FormMasalahRencana.setVisible(false);   
            ChkAccor.setVisible(true);
        }
    }

    private void getMasalah() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_mpp.kode_masalah,master_masalah_mpp.nama_masalah from master_masalah_mpp "+
                        "inner join mpp_evaluasi_masalah on mpp_evaluasi_masalah.kode_masalah=master_masalah_mpp.kode_masalah "+
                        "where mpp_evaluasi_masalah.no_rawat=? and mpp_evaluasi_masalah.tanggal=? order by master_masalah_mpp.kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from mpp_evaluasi_form_a where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
        })==true){
            TNoRM1.setText("");
            TPasien1.setText("");
            Sequel.meghapus("mpp_evaluasi_form_a_masalah","no_rawat","tanggal",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Valid.tabelKosong(tabModeDetailMasalah);
            ChkAccor.setSelected(false);
            isMenu();
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.mengedittf("mpp_evaluasi_form_a","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kd_dokter=?,kd_konsulan=?,skor_ews=?,analisis_ews=?,penurunan_adl=?,adl_sebutkan=?,riwayat_kesehatan=?,riwayat_penyakit_kronis=?,kebiasaan_merokok=?,kebiasaan_alkohol=?,kebiasaan_lain=?,kebiasaan_lain_sebutkan=?,riwayat_lain_sebutkan=?,perilaku=?,perilaku_agama=?,perilaku_sosial=?,perilaku_budaya=?,lingkungan_tidak_ada=?,lingkungan_tidak_menerima=?,lingkungan_sendirian=?,lingkungan_jauh_faskes=?,lingkungan_panti=?,lingkungan_dekat_faskes=?,lingkungan_lain=?,lingkungan_lain_sebutkan=?,keluarga_tidak_ada=?,keluarga_tidak_menerima=?,keluarga_tidak_menunggui=?,keluarga_tidak_mampu=?,keluarga_tidak_bisa_dihubungi=?,keluarga_tidak_tahu=?,keluarga_lain=?,keluarga_lain_sebutkan=?,finansial_tidak_ada=?,finansial_asuransi=?,finansial_tidak_mampu=?,finansial_tidak_ada_pj=?,finansial_belum_ada_asuransi=?,finansial_melebihi_klaim=?,finansial_lain=?,finansial_lain_sebutkan=?,kesehatan_mental=?,mental_tidak_baik=?,pengobatan_alternatif=?,alternatif_ada=?,pemahaman_kesehatan=?,pemahaman_kurang=?,harapan_membaik=?,harapan_tindakan=?,harapan_pasrah=?,harapan_lain=?,harapan_lain_sebutkan=?,status_asuransi=?,riwayat_trauma=?,trauma_ada=?,aspek_legal=?,legal_dibutuhkan=?,masalah_kepatuhan=?,masalah_tingkat_asuhan=?,masalah_kompleks=?,masalah_perburukan=?,masalah_finansial=?,masalah_sistem_pembayaran=?,masalah_dukungan_keluarga=?,masalah_pengetahuan=?,masalah_komplain=?,masalah_readmission=?,masalah_pemulangan=?,masalah_utilisasi=?,masalah_rujukan=?,rencana_utilisasi=?,rencana_advokasi_informasi=?,rencana_advokasi_diskusi=?,rencana_advokasi_dukungan=?,rencana_edukasi=?,rencana_koordinasi_masalah=?,rencana_koordinasi_intensif=?,rencana_koordinasi_saran=?,rencana_koordinasi_terminasi=?,kolaborasi_dpjp=?,kolaborasi_dietisien=?,kolaborasi_keuangan=?,kolaborasi_spesialis=?,kolaborasi_spesialis_sebutkan=?,kolaborasi_fisioterapis=?,kolaborasi_pimpinan=?,kolaborasi_perawat=?,kolaborasi_apoteker=?,kolaborasi_psikolog=?,kolaborasi_rohaniawan=?,kolaborasi_lainnya_sebutkan=?,evaluasi_follow_up=?,evaluasi_follow_up_sebutkan=?,evaluasi_efektifitas=?,evaluasi_efektifitas_sebutkan=?,nip=?,assesmen_teks=?,identifikasi_teks=?,rencana_teks=?",103,new String[]{
    TNoRw.getText(),Valid.SetTgl(TglEvaluasi.getSelectedItem()+"")+" "+TglEvaluasi.getSelectedItem().toString().substring(11,19),KdDok1.getText(),KdDok2.getText(),
    getGroupValue(ewsGroup),
    ewsAnalisis.getText(),
    getGroupValue(adlGroup),
    adlSebutkan.getText(),
    getGroupValue(riwayatGroup),
    riwayatPenyakitKronisSebutkan.getText(),
    kebiasaanMerokok.isSelected()?"Ya":"Tidak",
    kebiasaanAlkohol.isSelected()?"Ya":"Tidak",
    kebiasaanLain.isSelected()?"Ya":"Tidak",
    kebiasaanLainSebutkan.getText(),
    riwayatLainSebutkan.getText(),
    getGroupValue(perilakuGroup),
    perilakuAgamaSebutkan.getText(),
    perilakuSosialSebutkan.getText(),
    perilakuBudayaSebutkan.getText(),
    lingkunganTidakAda.isSelected()?"Ya":"Tidak",
    lingkunganTidakMenerima.isSelected()?"Ya":"Tidak",
    lingkunganSendirian.isSelected()?"Ya":"Tidak",
    lingkunganJauhFaskes.isSelected()?"Ya":"Tidak",
    lingkunganPanti.isSelected()?"Ya":"Tidak",
    lingkunganDekatFaskes.isSelected()?"Ya":"Tidak",
    lingkunganLain.isSelected()?"Ya":"Tidak",
    lingkunganLainSebutkan.getText(),
    keluargaTidakAda.isSelected()?"Ya":"Tidak",
    keluargaTidakMenerima.isSelected()?"Ya":"Tidak",
    keluargaTidakMenunggui.isSelected()?"Ya":"Tidak",
    keluargaTidakMampu.isSelected()?"Ya":"Tidak",
    keluargaTidakBisaDihubungi.isSelected()?"Ya":"Tidak",
    keluargaTidakTahu.isSelected()?"Ya":"Tidak",
    keluargaLain.isSelected()?"Ya":"Tidak",
    keluargaLainSebutkan.getText(),
    finansialTidakAda.isSelected()?"Ya":"Tidak",
    finansialAsuransi.isSelected()?"Ya":"Tidak",
    finansialTidakMampu.isSelected()?"Ya":"Tidak",
    finansialTidakAdaPj.isSelected()?"Ya":"Tidak",
    finansialBelumAdaAsuransi.isSelected()?"Ya":"Tidak",
    finansialMelebihiKlaim.isSelected()?"Ya":"Tidak",
    finansialLain.isSelected()?"Ya":"Tidak",
    finansialLainSebutkan.getText(),
    getGroupValue(mentalGroup),
    mentalTidakBaikSebutkan.getText(),
    getGroupValue(alternatifGroup),
    alternatifAdaSebutkan.getText(),
    getGroupValue(pemahamanGroup),
    pemahamanKurangSebutkan.getText(),
    harapanMembaik.isSelected()?"Ya":"Tidak",
    harapanTindakan.isSelected()?"Ya":"Tidak",
    harapanPasrah.isSelected()?"Ya":"Tidak",
    harapanLain.isSelected()?"Ya":"Tidak",
    harapanLainSebutkan.getText(),
    getGroupValue(asuransiGroup),
    getGroupValue(traumaGroup),
    traumaAdaSebutkan.getText(),
    getGroupValue(legalGroup),
    legalDibutuhkanSebutkan.getText(),
    masalahKepatuhan.isSelected()?"Ya":"Tidak",
    masalahTingkatAsuhan.isSelected()?"Ya":"Tidak",
    masalahKompleks.isSelected()?"Ya":"Tidak",
    masalahPerburukan.isSelected()?"Ya":"Tidak",
    masalahFinansial.isSelected()?"Ya":"Tidak",
    masalahSistemPembayaran.isSelected()?"Ya":"Tidak",
    masalahDukunganKeluarga.isSelected()?"Ya":"Tidak",
    masalahPengetahuan.isSelected()?"Ya":"Tidak",
    masalahKomplain.isSelected()?"Ya":"Tidak",
    masalahReadmission.isSelected()?"Ya":"Tidak",
    masalahPemulangan.isSelected()?"Ya":"Tidak",
    masalahUtilisasi.isSelected()?"Ya":"Tidak",
    masalahRujukan.isSelected()?"Ya":"Tidak",
    rencanaUtilisasi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiInformasi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiDiskusi.isSelected()?"Ya":"Tidak",
    rencanaAdvokasiDukungan.isSelected()?"Ya":"Tidak",
    rencanaEdukasi.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiMasalah.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiIntensif.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiSaran.isSelected()?"Ya":"Tidak",
    rencanaKoordinasiTerminasi.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiDPJP.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiDietisien.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiKeuangan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiSpesialis.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiSpesialisSebutkan.getText(),
    rencanaKolaborasiFisioterapis.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPimpinan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPerawat.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiApoteker.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiPsikolog.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiRohaniawan.isSelected()?"Ya":"Tidak",
    rencanaKolaborasiLainnyaSebutkan.getText(),
    rencanaEvaluasiFollowUp.isSelected()?"Ya":"Tidak",
    rencanaEvaluasiFollowUpSebutkan.getText(),
    rencanaEvaluasiEfektifitas.isSelected()?"Ya":"Tidak",
    rencanaEvaluasiEfektifitasSebutkan.getText(),
    KdPetugas.getText(),
    getAssesmenText(),
    getIdentifikasiText(),
    getRencanaText(),
    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
    tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
})==true){
                    Sequel.meghapus("mpp_evaluasi_form_a_masalah","no_rawat","tanggal",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                    Valid.tabelKosong(tabModeDetailMasalah);
                    tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                    tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                    tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                    tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                    tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                    tbObat.setValueAt(Alamat.getText(),tbObat.getSelectedRow(),5);
                    tbObat.setValueAt(Valid.SetTgl(TglEvaluasi.getSelectedItem()+"")+" "+TglEvaluasi.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                    tbObat.setValueAt(Kamar.getText(),tbObat.getSelectedRow(),7);
                    tbObat.setValueAt(TglMasuk.getText(),tbObat.getSelectedRow(),8);
                    tbObat.setValueAt(KdDok1.getText(),tbObat.getSelectedRow(),9);
                    tbObat.setValueAt(TDokter1.getText(),tbObat.getSelectedRow(),10);
                    tbObat.setValueAt(KdDok2.getText(),tbObat.getSelectedRow(),11);
                    tbObat.setValueAt(TDokter2.getText(),tbObat.getSelectedRow(),12);
                    tbObat.setValueAt("",tbObat.getSelectedRow(),13);
                    tbObat.setValueAt("",tbObat.getSelectedRow(),14);
                    tbObat.setValueAt(getAssesmenText(),tbObat.getSelectedRow(),15);
                    tbObat.setValueAt(getIdentifikasiText(),tbObat.getSelectedRow(),16);
                    tbObat.setValueAt(getRencanaText(),tbObat.getSelectedRow(),17);
                    tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),18);
                    tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),19);
                    for (i = 0; i < tbIdentifikasiMPP.getRowCount(); i++) {
                        if(tbIdentifikasiMPP.getValueAt(i,0).toString().equals("true")){
                            if(Sequel.menyimpantf2("mpp_evaluasi_masalah","?,?,?",3,new String[]{TNoRw.getText(),Valid.SetTgl(TglEvaluasi.getSelectedItem()+"")+" "+TglEvaluasi.getSelectedItem().toString().substring(11,19),tbIdentifikasiMPP.getValueAt(i,1).toString()})==true){
                                tabModeDetailMasalah.addRow(new Object[]{tbIdentifikasiMPP.getValueAt(i,1).toString(),tbIdentifikasiMPP.getValueAt(i,2).toString()});
                            }
                        }
                    }
                    TabRawat.setSelectedIndex(1);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        } 
    }

    private String getGroupValue(javax.swing.ButtonGroup group) {
        for (java.util.Enumeration<javax.swing.AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            javax.swing.AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }

    private void setGroupValue(javax.swing.ButtonGroup group, String text) {
        if(text==null) return;
        for (java.util.Enumeration<javax.swing.AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            javax.swing.AbstractButton button = buttons.nextElement();
            if (button.getText().equals(text)) {
                button.setSelected(true);
                return;
            }
        }
    }
}
