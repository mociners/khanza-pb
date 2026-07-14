package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import widget.Button;
import widget.ComboBox;
import widget.InternalFrame;
import widget.Label;
import widget.PanelBiasa;
import widget.ScrollPane;
import widget.Table;
import widget.TextArea;
import widget.TextBox;
import widget.Tanggal;
import widget.panelisi;
import widget.CekBox;
import javax.swing.Timer;
import java.awt.Cursor;

public class RMPenilaianAwalMedisRanapAnak1 extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);

    private InternalFrame internalFrame1;
    private panelisi panelGlass8;
    private Button BtnSimpan, BtnBatal, BtnHapus, BtnEdit, BtnPrint, BtnAll, BtnKeluar;
    private javax.swing.JTabbedPane TabRawat;
    private InternalFrame internalFrame2;
    private ScrollPane scrollInput;
    private PanelBiasa FormInput;
    
    // Tab Data
    private InternalFrame internalFrame3;
    private ScrollPane Scroll;
    private Table tbObat;
    private panelisi panelGlass9;
    private Label jLabel19, jLabel21, jLabel6, jLabel7, LCount;
    private Tanggal DTPCari1, DTPCari2;
    private TextBox TCari;
    private Button BtnCari;

    // Fields
    private TextBox TNoRw, TPasien, TNoRM, KdDokter, NmDokter, TglLahir, Jk;
    private Button BtnDokter;
    private Tanggal TglAsuhan;
    private ComboBox cmbJam;
    private ComboBox cmbMnt;
    private ComboBox cmbDtk;
    private CekBox ChkKejadian;

    // A. ANAMNESA
    private TextArea KeluhanUtama;

    // B. PEMERIKSAAN FISIK
    private ComboBox KeadaanUmum;
    private TextBox GCSe, GCSv, GCSm;
    private ComboBox Kesadaran;
    private TextBox Tensi, Suhu, Nadi, RR;
    private TextBox BB, TB, LK;
    private TextBox Kepala, Leher, Jantung, Paru, Abdomen, Ekstremitas, Genitilia, StatusNeurologis;

    // C-H
    private TextArea Laboratorium, DiagnosaBanding, DiagnosaKerja, Penatalaksanaan, UsulPemeriksaan, Prognosa;

    public RMPenilaianAwalMedisRanapAnak1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Tanggal", "Kode Dokter", "Nama Dokter", "Keluhan Utama", "Keadaan Umum",
            "GCS E", "GCS V", "GCS M", "Kesadaran", "Tensi", "Suhu", "Nadi", "RR", "BB", "TB", "LK", "Kepala", "Leher", "Jantung", "Paru", "Abdomen", "Ekstremitas", "Genitalia", "Status Neurologis",
            "Laboratorium", "Diagnosa Banding", "Diagnosa Kerja", "Penatalaksanaan", "Usul Pemeriksaan", "Prognosa"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 35; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
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
        
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                tampil();
            }
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N
        
        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);
        tbObat.setComponentPopupMenu(jPopupMenu1);
        
        jam();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        internalFrame1 = new InternalFrame();
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Inap Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new BorderLayout(1, 1));
        setContentPane(internalFrame1);

        panelGlass8 = new panelisi();
        panelGlass8.setPreferredSize(new Dimension(44, 54));
        panelGlass8.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));
        
        BtnSimpan = new Button(); BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); BtnSimpan.setText("Simpan"); BtnSimpan.setPreferredSize(new Dimension(100, 30));
        BtnBatal = new Button(); BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); BtnBatal.setText("Baru"); BtnBatal.setPreferredSize(new Dimension(100, 30));
        BtnHapus = new Button(); BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); BtnHapus.setText("Hapus"); BtnHapus.setPreferredSize(new Dimension(100, 30));
        BtnEdit = new Button(); BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); BtnEdit.setText("Ganti"); BtnEdit.setPreferredSize(new Dimension(100, 30));
        BtnPrint = new Button(); BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); BtnPrint.setText("Cetak"); BtnPrint.setPreferredSize(new Dimension(100, 30));
        BtnAll = new Button(); BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); BtnAll.setText("Semua"); BtnAll.setPreferredSize(new Dimension(100, 30));
        BtnKeluar = new Button(); BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); BtnKeluar.setText("Keluar"); BtnKeluar.setPreferredSize(new Dimension(100, 30));
        
        panelGlass8.add(BtnSimpan); panelGlass8.add(BtnBatal); panelGlass8.add(BtnHapus);
        panelGlass8.add(BtnEdit); panelGlass8.add(BtnPrint); panelGlass8.add(BtnAll); panelGlass8.add(BtnKeluar);
        internalFrame1.add(panelGlass8, BorderLayout.PAGE_END);

        TabRawat = new javax.swing.JTabbedPane();
        TabRawat.setBackground(new Color(254, 255, 254));
        TabRawat.setForeground(new Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        // TAB 1: FORM INPUT
        internalFrame2 = new InternalFrame();
        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new BorderLayout(1, 1));
        
        scrollInput = new ScrollPane();
        FormInput = new PanelBiasa();
        FormInput.setLayout(null);
        FormInput.setPreferredSize(new Dimension(870, 1150));
        scrollInput.setViewportView(FormInput);
        internalFrame2.add(scrollInput, BorderLayout.CENTER);
        TabRawat.addTab("Input Penilaian", internalFrame2);
        
        // TAB 2: DATA PENILAIAN
        internalFrame3 = new InternalFrame();
        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new BorderLayout(1, 1));
        
        Scroll = new ScrollPane();
        tbObat = new Table();
        Scroll.setViewportView(tbObat);
        internalFrame3.add(Scroll, BorderLayout.CENTER);
        
        panelGlass9 = new panelisi();
        panelGlass9.setPreferredSize(new Dimension(44, 44));
        panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));
        
        jLabel19 = new Label(); jLabel19.setText("Tgl.Asuhan :"); jLabel19.setPreferredSize(new Dimension(70, 23));
        DTPCari1 = new Tanggal(); DTPCari1.setDisplayFormat("dd-MM-yyyy"); DTPCari1.setForeground(new Color(50, 50, 50)); DTPCari1.setPreferredSize(new Dimension(90, 23));
        jLabel21 = new Label(); jLabel21.setText("s.d."); jLabel21.setPreferredSize(new Dimension(23, 23));
        DTPCari2 = new Tanggal(); DTPCari2.setDisplayFormat("dd-MM-yyyy"); DTPCari2.setForeground(new Color(50, 50, 50)); DTPCari2.setPreferredSize(new Dimension(90, 23));
        jLabel6 = new Label(); jLabel6.setText("Key Word :"); jLabel6.setPreferredSize(new Dimension(70, 23));
        TCari = new TextBox(); TCari.setPreferredSize(new Dimension(210, 23));
        BtnCari = new Button(); BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); BtnCari.setText("Tampilkan"); BtnCari.setPreferredSize(new Dimension(130, 23));
        jLabel7 = new Label(); jLabel7.setText("Record :"); jLabel7.setPreferredSize(new Dimension(65, 23));
        LCount = new Label(); LCount.setText("0"); LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); LCount.setPreferredSize(new Dimension(50, 23));
        
        panelGlass9.add(jLabel19); panelGlass9.add(DTPCari1); panelGlass9.add(jLabel21); panelGlass9.add(DTPCari2);
        panelGlass9.add(jLabel6); panelGlass9.add(TCari); panelGlass9.add(BtnCari); panelGlass9.add(jLabel7); panelGlass9.add(LCount);
        internalFrame3.add(panelGlass9, BorderLayout.PAGE_END);
        
        TabRawat.addTab("Data Penilaian", internalFrame3);
        internalFrame1.add(TabRawat, BorderLayout.CENTER);

        // FORM FIELDS SETUP
        int y = 10;
        addLabel("No.Rawat :", 10, y, 70, 23);
        TNoRw = new TextBox(); TNoRw.setBounds(85, y, 130, 23); FormInput.add(TNoRw);
        TNoRM = new TextBox(); TNoRM.setEditable(false); TNoRM.setBounds(220, y, 100, 23); FormInput.add(TNoRM);
        TPasien = new TextBox(); TPasien.setEditable(false); TPasien.setBounds(325, y, 260, 23); FormInput.add(TPasien);
        
        addLabel("Tgl.Lahir :", 595, y, 60, 23);
        TglLahir = new TextBox(); TglLahir.setEditable(false); TglLahir.setBounds(660, y, 90, 23); FormInput.add(TglLahir);
        addLabel("J.K. :", 760, y, 30, 23);
        Jk = new TextBox(); Jk.setEditable(false); Jk.setBounds(795, y, 60, 23); FormInput.add(Jk);
        
        y += 30;
        addLabel("Tanggal :", 10, y, 70, 23);
        TglAsuhan = new Tanggal(); TglAsuhan.setDisplayFormat("dd-MM-yyyy"); TglAsuhan.setBounds(85, y, 90, 23); FormInput.add(TglAsuhan);
        
        cmbJam = new ComboBox(); cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setBounds(180, y, 50, 23); FormInput.add(cmbJam);
        
        cmbMnt = new ComboBox(); cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setBounds(235, y, 50, 23); FormInput.add(cmbMnt);
        
        cmbDtk = new ComboBox(); cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setBounds(290, y, 50, 23); FormInput.add(cmbDtk);
        
        ChkKejadian = new CekBox(); ChkKejadian.setBorder(null); ChkKejadian.setSelected(true); ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setBounds(345, y, 23, 23); FormInput.add(ChkKejadian);
        
        addLabel("DPJP :", 380, y, 50, 23);
        KdDokter = new TextBox(); KdDokter.setEditable(false); KdDokter.setBounds(435, y, 90, 23); FormInput.add(KdDokter);
        NmDokter = new TextBox(); NmDokter.setEditable(false); NmDokter.setBounds(530, y, 180, 23); FormInput.add(NmDokter);
        BtnDokter = new Button(); BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); BtnDokter.setBounds(715, y, 28, 23); FormInput.add(BtnDokter);
        
        y += 40;
        addSeparator("A. ANAMNESA", y);
        y += 35;
        addLabel("Keluhan Utama :", 20, y, 100, 23);
        KeluhanUtama = addTextArea(125, y, 730, 50);
        
        y += 60;
        addSeparator("B. PEMERIKSAAN FISIK", y);
        y += 35;
        addLabel("Keadaan Umum :", 20, y, 100, 23);
        KeadaanUmum = new ComboBox(); KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        KeadaanUmum.setBounds(125, y, 150, 23); FormInput.add(KeadaanUmum);
        
        y += 30;
        addLabel("Kesadaran :", 20, y, 100, 23);
        addLabel("GCS: E", 125, y, 45, 23); GCSe = new TextBox(); GCSe.setBounds(175, y, 40, 23); FormInput.add(GCSe);
        addLabel("V", 225, y, 20, 23); GCSv = new TextBox(); GCSv.setBounds(250, y, 40, 23); FormInput.add(GCSv);
        addLabel("M", 300, y, 20, 23); GCSm = new TextBox(); GCSm.setBounds(325, y, 40, 23); FormInput.add(GCSm);
        Kesadaran = new ComboBox(); Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setBounds(380, y, 150, 23); FormInput.add(Kesadaran);
        
        y += 30;
        addLabel("Tanda Vital :", 20, y, 100, 23);
        addLabel("T", 125, y, 20, 23); Tensi = new TextBox(); Tensi.setBounds(145, y, 60, 23); FormInput.add(Tensi); addLabel("mm/Hg", 210, y, 40, 23);
        addLabel("S", 260, y, 20, 23); Suhu = new TextBox(); Suhu.setBounds(280, y, 40, 23); FormInput.add(Suhu); addLabel("C", 325, y, 20, 23);
        addLabel("N", 355, y, 20, 23); Nadi = new TextBox(); Nadi.setBounds(375, y, 40, 23); FormInput.add(Nadi); addLabel("x/m", 420, y, 30, 23);
        addLabel("R", 460, y, 20, 23); RR = new TextBox(); RR.setBounds(480, y, 40, 23); FormInput.add(RR); addLabel("xm", 525, y, 30, 23);

        y += 30;
        addLabel("Antropometri :", 20, y, 100, 23);
        addLabel("BB", 125, y, 25, 23); BB = new TextBox(); BB.setBounds(155, y, 50, 23); FormInput.add(BB);
        addLabel("TB", 220, y, 25, 23); TB = new TextBox(); TB.setBounds(250, y, 50, 23); FormInput.add(TB);
        addLabel("LK", 315, y, 25, 23); LK = new TextBox(); LK.setBounds(345, y, 50, 23); FormInput.add(LK);

        y += 30;
        addLabel("Kepala :", 20, y, 100, 23); Kepala = new TextBox(); Kepala.setBounds(125, y, 730, 23); FormInput.add(Kepala);
        y += 30;
        addLabel("Leher :", 20, y, 100, 23); Leher = new TextBox(); Leher.setBounds(125, y, 730, 23); FormInput.add(Leher);
        
        y += 30;
        addLabel("Toraks :", 20, y, 100, 23);
        addLabel("Jantung", 125, y, 60, 23); Jantung = new TextBox(); Jantung.setBounds(190, y, 665, 23); FormInput.add(Jantung);
        y += 30;
        addLabel("Paru-paru", 125, y, 60, 23); Paru = new TextBox(); Paru.setBounds(190, y, 665, 23); FormInput.add(Paru);
        
        y += 30;
        addLabel("Abdomen :", 20, y, 100, 23); Abdomen = new TextBox(); Abdomen.setBounds(125, y, 730, 23); FormInput.add(Abdomen);
        y += 30;
        addLabel("Ekstremitas :", 20, y, 100, 23); Ekstremitas = new TextBox(); Ekstremitas.setBounds(125, y, 730, 23); FormInput.add(Ekstremitas);
        y += 30;
        addLabel("Genitilia :", 20, y, 100, 23); Genitilia = new TextBox(); Genitilia.setBounds(125, y, 730, 23); FormInput.add(Genitilia);
        y += 30;
        addLabel("Status Neorologis :", 20, y, 110, 23); StatusNeurologis = new TextBox(); StatusNeurologis.setBounds(135, y, 720, 23); FormInput.add(StatusNeurologis);

        y += 40;
        addSeparator("C. LABORATORIUM", y);
        y += 35;
        Laboratorium = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("D. DIAGNOSA BANDING", y);
        y += 35;
        DiagnosaBanding = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("E. DIAGNOSA KERJA", y);
        y += 35;
        DiagnosaKerja = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("F. PENATALAKSANAAN", y);
        y += 35;
        Penatalaksanaan = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("G. USUL PEMERIKSAAN", y);
        y += 35;
        UsulPemeriksaan = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("H. PROGNOSA", y);
        y += 35;
        Prognosa = addTextArea(20, y, 835, 50);

        // ACTION LISTENERS
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setVisible(true);
            }
        });
        
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emptTeks();
            }
        });
        
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (TNoRw.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "No.Rawat");
                } else if (KdDokter.getText().trim().equals("")) {
                    Valid.textKosong(KdDokter, "Dokter");
                } else if (KeluhanUtama.getText().trim().equals("")) {
                    Valid.textKosong(KeluhanUtama, "Keluhan Utama");
                } else {
                    if (Sequel.menyimpantf("penilaian_awal_medis_ranap_anak", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 30, new String[]{
                        TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        KdDokter.getText(), KeluhanUtama.getText(), KeadaanUmum.getSelectedItem().toString(),
                        GCSe.getText(), GCSv.getText(), GCSm.getText(), Kesadaran.getSelectedItem().toString(), Tensi.getText(), Suhu.getText(),
                        Nadi.getText(), RR.getText(), BB.getText(), TB.getText(), LK.getText(), Kepala.getText(), Leher.getText(), Jantung.getText(),
                        Paru.getText(), Abdomen.getText(), Ekstremitas.getText(), Genitilia.getText(), StatusNeurologis.getText(), Laboratorium.getText(),
                        DiagnosaBanding.getText(), DiagnosaKerja.getText(), Penatalaksanaan.getText(), UsulPemeriksaan.getText(), Prognosa.getText()
                    }) == true) {
                        tampil();
                        emptTeks();
                    }
                }
            }
        });
        
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (TNoRw.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "No.Rawat");
                } else if (KdDokter.getText().trim().equals("")) {
                    Valid.textKosong(KdDokter, "Dokter");
                } else if (KeluhanUtama.getText().trim().equals("")) {
                    Valid.textKosong(KeluhanUtama, "Keluhan Utama");
                } else {
                    if (Sequel.mengedittf("penilaian_awal_medis_ranap_anak", "no_rawat=?", "tanggal=?,kd_dokter=?,keluhan_utama=?,keadaan_umum=?,gcs_e=?,gcs_v=?,gcs_m=?,kesadaran=?,tensi=?,suhu=?,nadi=?,rr=?,bb=?,tb=?,lk=?,kepala=?,leher=?,jantung=?,paru=?,abdomen=?,ekstremitas=?,genitalia=?,status_neurologis=?,laboratorium=?,diagnosa_banding=?,diagnosa_kerja=?,penatalaksanaan=?,usul_pemeriksaan=?,prognosa=?", 30, new String[]{
                        Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        KdDokter.getText(), KeluhanUtama.getText(), KeadaanUmum.getSelectedItem().toString(),
                        GCSe.getText(), GCSv.getText(), GCSm.getText(), Kesadaran.getSelectedItem().toString(), Tensi.getText(), Suhu.getText(),
                        Nadi.getText(), RR.getText(), BB.getText(), TB.getText(), LK.getText(), Kepala.getText(), Leher.getText(), Jantung.getText(),
                        Paru.getText(), Abdomen.getText(), Ekstremitas.getText(), Genitilia.getText(), StatusNeurologis.getText(), Laboratorium.getText(),
                        DiagnosaBanding.getText(), DiagnosaKerja.getText(), Penatalaksanaan.getText(), UsulPemeriksaan.getText(), Prognosa.getText(),
                        TNoRw.getText()
                    }) == true) {
                        tampil();
                        emptTeks();
                    }
                }
            }
        });
        
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else if (TNoRw.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
                } else {
                    if (Sequel.queryutf("delete from penilaian_awal_medis_ranap_anak where no_rawat='" + TNoRw.getText() + "'") == true) {
                        tampil();
                        emptTeks();
                    }
                }
            }
        });
        
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampil();
            }
        });
        
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCari.setText("");
                tampil();
            }
        });
        
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tabMode.getRowCount() != 0) {
                    try {
                        getData();
                    } catch (java.lang.NullPointerException e) {
                    }
                }
            }
        });
        
        TabRawat.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if (TabRawat.getSelectedIndex() == 1) {
                    tampil();
                }
            }
        });
        
        setSize(890, 700);
        setLocationRelativeTo(null);
    }
    
    private void addLabel(String text, int x, int y, int width, int height) {
        Label lbl = new Label();
        lbl.setText(text);
        lbl.setBounds(x, y, width, height);
        FormInput.add(lbl);
    }
    
    private void addSeparator(String text, int y) {
        Label lbl = new Label();
        lbl.setText(text);
        lbl.setBounds(10, y, 800, 23);
        lbl.setFont(new java.awt.Font("Tahoma", 1, 11));
        lbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FormInput.add(lbl);
        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setBounds(10, y+20, 845, 5);
        FormInput.add(sep);
    }
    
    private TextArea addTextArea(int x, int y, int width, int height) {
        TextArea ta = new TextArea();
        ScrollPane sp = new ScrollPane();
        sp.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        sp.setViewportView(ta);
        sp.setBounds(x, y, width, height);
        FormInput.add(sp);
        return ta;
    }
    
    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = new Date();
                
                if(ChkKejadian.isSelected() == true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected() == false){
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }
                
                if (nilai_jam <= 9) nol_jam = "0";
                if (nilai_menit <= 9) nol_menit = "0";
                if (nilai_detik <= 9) nol_detik = "0";
                
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        new Timer(1000, taskPerformer).start();
    }
    
    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            try {
                param.put("lokalis",getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            } 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),6).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanapAnak.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Inap Anak ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_awal_medis_ranap_anak.tanggal," +
                "penilaian_awal_medis_ranap_anak.kd_dokter,dokter.nm_dokter," +
                "kamar_inap.kd_kamar, bangsal.nm_bangsal," +
                "penilaian_awal_medis_ranap_anak.keluhan_utama," +
                "penilaian_awal_medis_ranap_anak.keadaan_umum,penilaian_awal_medis_ranap_anak.gcs_e,penilaian_awal_medis_ranap_anak.gcs_v,penilaian_awal_medis_ranap_anak.gcs_m,penilaian_awal_medis_ranap_anak.kesadaran," +
                "penilaian_awal_medis_ranap_anak.tensi,penilaian_awal_medis_ranap_anak.suhu,penilaian_awal_medis_ranap_anak.nadi,penilaian_awal_medis_ranap_anak.rr,penilaian_awal_medis_ranap_anak.bb,penilaian_awal_medis_ranap_anak.tb,penilaian_awal_medis_ranap_anak.lk," +
                "penilaian_awal_medis_ranap_anak.kepala,penilaian_awal_medis_ranap_anak.leher,penilaian_awal_medis_ranap_anak.jantung,penilaian_awal_medis_ranap_anak.paru,penilaian_awal_medis_ranap_anak.abdomen,penilaian_awal_medis_ranap_anak.ekstremitas," +
                "penilaian_awal_medis_ranap_anak.genitalia,penilaian_awal_medis_ranap_anak.status_neurologis,penilaian_awal_medis_ranap_anak.laboratorium," +
                "penilaian_awal_medis_ranap_anak.diagnosa_banding,penilaian_awal_medis_ranap_anak.diagnosa_kerja,penilaian_awal_medis_ranap_anak.penatalaksanaan," +
                "penilaian_awal_medis_ranap_anak.usul_pemeriksaan,penilaian_awal_medis_ranap_anak.prognosa " +
                "from reg_periksa " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join penilaian_awal_medis_ranap_anak on reg_periksa.no_rawat=penilaian_awal_medis_ranap_anak.no_rawat " +
                "inner join dokter on penilaian_awal_medis_ranap_anak.kd_dokter=dokter.kd_dokter " +
                "inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.stts_pulang='-' " +
                "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar " +
                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal " +
                "where penilaian_awal_medis_ranap_anak.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }
    
    public void setNoRm(String norwt, Date tgl) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        TglAsuhan.setDate(tgl);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, norwt);
                rs = ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Jk.setText(rs.getString("jk"));
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
    
    public void setDPJP(String kddokter, String nmdokter) {
        KdDokter.setText(kddokter);
        NmDokter.setText(nmdokter);
    }
    
    public void isCek() {
        BtnSimpan.setEnabled(true);
        BtnHapus.setEnabled(true);
        BtnEdit.setEnabled(true);
        BtnPrint.setEnabled(true);
        if (akses.getjml2() >= 1) {
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if (NmDokter.getText().equals("")) {
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan Dokter...!!");
            }
        }
    }
    
    public void emptTeks() {
        KeluhanUtama.setText("");
        GCSe.setText("");
        GCSv.setText("");
        GCSm.setText("");
        Tensi.setText("");
        Suhu.setText("");
        Nadi.setText("");
        RR.setText("");
        BB.setText("");
        TB.setText("");
        LK.setText("");
        Kepala.setText("");
        Leher.setText("");
        Jantung.setText("");
        Paru.setText("");
        Abdomen.setText("");
        Ekstremitas.setText("");
        Genitilia.setText("");
        StatusNeurologis.setText("");
        Laboratorium.setText("");
        DiagnosaBanding.setText("");
        DiagnosaKerja.setText("");
        Penatalaksanaan.setText("");
        UsulPemeriksaan.setText("");
        Prognosa.setText("");
        TabRawat.setSelectedIndex(0);
        ChkKejadian.setSelected(true);
    }
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_awal_medis_ranap_anak.tanggal, " +
                    "penilaian_awal_medis_ranap_anak.kd_dokter,dokter.nm_dokter,penilaian_awal_medis_ranap_anak.keluhan_utama, " +
                    "penilaian_awal_medis_ranap_anak.keadaan_umum,penilaian_awal_medis_ranap_anak.gcs_e,penilaian_awal_medis_ranap_anak.gcs_v, " +
                    "penilaian_awal_medis_ranap_anak.gcs_m,penilaian_awal_medis_ranap_anak.kesadaran,penilaian_awal_medis_ranap_anak.tensi, " +
                    "penilaian_awal_medis_ranap_anak.suhu,penilaian_awal_medis_ranap_anak.nadi,penilaian_awal_medis_ranap_anak.rr, " +
                    "penilaian_awal_medis_ranap_anak.bb,penilaian_awal_medis_ranap_anak.tb,penilaian_awal_medis_ranap_anak.lk, " +
                    "penilaian_awal_medis_ranap_anak.kepala,penilaian_awal_medis_ranap_anak.leher,penilaian_awal_medis_ranap_anak.jantung, " +
                    "penilaian_awal_medis_ranap_anak.paru,penilaian_awal_medis_ranap_anak.abdomen,penilaian_awal_medis_ranap_anak.ekstremitas, " +
                    "penilaian_awal_medis_ranap_anak.genitalia,penilaian_awal_medis_ranap_anak.status_neurologis,penilaian_awal_medis_ranap_anak.laboratorium, " +
                    "penilaian_awal_medis_ranap_anak.diagnosa_banding,penilaian_awal_medis_ranap_anak.diagnosa_kerja,penilaian_awal_medis_ranap_anak.penatalaksanaan, " +
                    "penilaian_awal_medis_ranap_anak.usul_pemeriksaan,penilaian_awal_medis_ranap_anak.prognosa " +
                    "from penilaian_awal_medis_ranap_anak inner join reg_periksa on penilaian_awal_medis_ranap_anak.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join dokter on penilaian_awal_medis_ranap_anak.kd_dokter=dokter.kd_dokter where " +
                    "penilaian_awal_medis_ranap_anak.tanggal between ? and ? "+
                    (TCari.getText().equals("") ? "" : "and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ")+
                    "order by penilaian_awal_medis_ranap_anak.tanggal");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_lahir"), rs.getString("jk"),
                        rs.getString("tanggal"), rs.getString("kd_dokter"), rs.getString("nm_dokter"), rs.getString("keluhan_utama"), rs.getString("keadaan_umum"),
                        rs.getString("gcs_e"), rs.getString("gcs_v"), rs.getString("gcs_m"), rs.getString("kesadaran"), rs.getString("tensi"), rs.getString("suhu"),
                        rs.getString("nadi"), rs.getString("rr"), rs.getString("bb"), rs.getString("tb"), rs.getString("lk"), rs.getString("kepala"), rs.getString("leher"),
                        rs.getString("jantung"), rs.getString("paru"), rs.getString("abdomen"), rs.getString("ekstremitas"), rs.getString("genitalia"), rs.getString("status_neurologis"),
                        rs.getString("laboratorium"), rs.getString("diagnosa_banding"), rs.getString("diagnosa_kerja"), rs.getString("penatalaksanaan"), rs.getString("usul_pemeriksaan"), rs.getString("prognosa")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl2(TglAsuhan, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            cmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString().substring(11, 13));
            cmbMnt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString().substring(14, 16));
            cmbDtk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString().substring(17, 19));
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            GCSe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            GCSv.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            GCSm.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Tensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            LK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Leher.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Jantung.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Paru.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Abdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Ekstremitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Genitilia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            StatusNeurologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Laboratorium.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            DiagnosaBanding.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            DiagnosaKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Penatalaksanaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            UsulPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Prognosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            ChkKejadian.setSelected(false);
        }
    }
}
