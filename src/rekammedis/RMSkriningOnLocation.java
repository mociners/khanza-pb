package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

public final class RMSkriningOnLocation extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    
    // Components
    private widget.InternalFrame internalFrame1;
    private widget.ScrollPane Scroll;
    private widget.Table tbObat;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.Button BtnSimpan;
    private widget.Button BtnBatal;
    private widget.Button BtnHapus;
    private widget.Button BtnEdit;
    private widget.Button BtnPrint;
    private widget.Label jLabel7;
    private widget.Label LCount;
    private widget.Button BtnKeluar;
    private widget.panelisi panelGlass9;
    private widget.Label jLabel19;
    private widget.Tanggal DTPCari1;
    private widget.Label jLabel21;
    private widget.Tanggal DTPCari2;
    private widget.Label jLabel6;
    private widget.TextBox TCari;
    private widget.Button BtnCari;
    private widget.Button BtnAll;
    private javax.swing.JPanel PanelInput;
    private widget.PanelBiasa FormInput;
    private widget.Label jLabel4;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TNoRM;
    private widget.Label jLabel16;
    private widget.ComboBox Jam;
    private widget.ComboBox Menit;
    private widget.ComboBox Detik;
    private widget.CekBox ChkKejadian;
    private widget.Label jLabel18;
    private widget.TextBox KdPetugas;
    private widget.TextBox NmPetugas;
    private widget.Button btnPetugas;
    private widget.Label jLabel8;
    private widget.TextBox TglLahir;
    private widget.Label jLabelJk;
    private widget.TextBox Jk;
    private widget.CekBox ChkInput;
    
    private widget.Label jLabelHarap;
    
    private widget.Label jLabelTglRujuk;
    private widget.Tanggal TanggalRujuk;
    private widget.ComboBox JamRujuk;
    private widget.ComboBox MenitRujuk;
    private widget.ComboBox DetikRujuk;
    
    private widget.Label jLabelIdPerujuk;
    private widget.TextBox IdPerujuk;
    private widget.Label jLabelInstalasi;
    private widget.TextBox InstalasiPerujuk;
    
    private widget.Label jLabelPasien;
    private widget.Label jLabelNamaPas;
    private widget.TextBox NamaPasien;
    private widget.Label jLabelUmur;
    private widget.TextBox UmurPasien;
    
    private widget.Label jLabelAlamat;
    private widget.TextBox Alamat;
    private widget.Label jLabelDiag;
    private widget.TextBox Diagnosa;
    private widget.Label jLabelKeluhan;
    private widget.TextBox KeluhanUtama;
    private widget.Label jLabelGCS;
    private widget.TextBox GCS;
    
    private widget.Label jLabelHemodinamik;
    private widget.Label jLabelT;
    private widget.TextBox T_Hemo;
    private widget.Label jLabelN;
    private widget.TextBox N_Hemo;
    private widget.Label jLabelR;
    private widget.TextBox R_Hemo;
    private widget.Label jLabelS;
    private widget.TextBox S_Hemo;
    
    private widget.Label jLabelAlasan;
    private widget.TextBox AlasanDirujuk;
    private widget.Label jLabelTherapi;
    private widget.TextBox Therapi;

    public RMSkriningOnLocation(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(880, 700);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Kode Petugas", "Nama Petugas", "Tgl.Pengkajian",
            "Tgl.Rujuk", "Identitas Perujuk", "Instalasi Perujuk", "Nama Pasien (Rjk)", "Umur/Tgl Lahir (Rjk)", 
            "Alamat", "Diagnosa", "Keluhan Utama", "Kesadaran GCS", "T", "N", "R", "S", "Alasan Dirujuk", "Therapi"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(800, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(105);
            else if (i == 1) column.setPreferredWidth(70);
            else if (i == 2) column.setPreferredWidth(150);
            else if (i == 3) column.setPreferredWidth(65);
            else if (i == 4) column.setPreferredWidth(35);
            else if (i == 5) column.setPreferredWidth(80);
            else if (i == 6) column.setPreferredWidth(150);
            else if (i == 7) column.setPreferredWidth(115);
            else if (i == 8) column.setPreferredWidth(115);
            else if (i == 9) column.setPreferredWidth(150);
            else if (i == 10) column.setPreferredWidth(150);
            else if (i == 11) column.setPreferredWidth(150);
            else if (i == 12) column.setPreferredWidth(120);
            else if (i == 13) column.setPreferredWidth(200);
            else if (i == 14) column.setPreferredWidth(200);
            else if (i == 15) column.setPreferredWidth(200);
            else if (i == 16) column.setPreferredWidth(100);
            else if (i >= 17 && i <= 20) column.setPreferredWidth(50);
            else if (i == 21) column.setPreferredWidth(200);
            else if (i == 22) column.setPreferredWidth(250);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningOnLocation = new javax.swing.JMenuItem();
        MnSkriningOnLocation.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningOnLocation.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningOnLocation.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningOnLocation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MnSkriningOnLocation.setText("Cetak Laporan Skrining On Location");
        MnSkriningOnLocation.setName("MnSkriningOnLocation");
        MnSkriningOnLocation.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningOnLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningOnLocationActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningOnLocation);
        tbObat.setComponentPopupMenu(jPopupMenu1);

        TNoRw.setDocument(new batasInput((int) 17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((int) 20).getKata(KdPetugas));
        IdPerujuk.setDocument(new batasInput((int) 100).getKata(IdPerujuk));
        InstalasiPerujuk.setDocument(new batasInput((int) 100).getKata(InstalasiPerujuk));
        NamaPasien.setDocument(new batasInput((int) 100).getKata(NamaPasien));
        UmurPasien.setDocument(new batasInput((int) 100).getKata(UmurPasien));
        Alamat.setDocument(new batasInput((int) 200).getKata(Alamat));
        Diagnosa.setDocument(new batasInput((int) 200).getKata(Diagnosa));
        KeluhanUtama.setDocument(new batasInput((int) 200).getKata(KeluhanUtama));
        GCS.setDocument(new batasInput((int) 100).getKata(GCS));
        T_Hemo.setDocument(new batasInput((int) 50).getKata(T_Hemo));
        N_Hemo.setDocument(new batasInput((int) 50).getKata(N_Hemo));
        R_Hemo.setDocument(new batasInput((int) 50).getKata(R_Hemo));
        S_Hemo.setDocument(new batasInput((int) 50).getKata(S_Hemo));
        AlasanDirujuk.setDocument(new batasInput((int) 200).getKata(AlasanDirujuk));
        Therapi.setDocument(new batasInput((int) 300).getKata(Therapi));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void removeUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
                @Override public void changedUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampil(); }
            });
        }

        petugas.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                KdPetugas.requestFocus();
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        ChkInput.setSelected(false);
        isForm();
        jam();
        tampil();
    }

    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabelJk = new widget.Label();
        Jk = new widget.TextBox();
        ChkInput = new widget.CekBox();
        
        jLabelHarap = new widget.Label();
        jLabelTglRujuk = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        JamRujuk = new widget.ComboBox();
        MenitRujuk = new widget.ComboBox();
        DetikRujuk = new widget.ComboBox();
        
        jLabelIdPerujuk = new widget.Label();
        IdPerujuk = new widget.TextBox();
        jLabelInstalasi = new widget.Label();
        InstalasiPerujuk = new widget.TextBox();
        
        jLabelPasien = new widget.Label();
        jLabelNamaPas = new widget.Label();
        NamaPasien = new widget.TextBox();
        jLabelUmur = new widget.Label();
        UmurPasien = new widget.TextBox();
        
        jLabelAlamat = new widget.Label();
        Alamat = new widget.TextBox();
        jLabelDiag = new widget.Label();
        Diagnosa = new widget.TextBox();
        jLabelKeluhan = new widget.Label();
        KeluhanUtama = new widget.TextBox();
        jLabelGCS = new widget.Label();
        GCS = new widget.TextBox();
        
        jLabelHemodinamik = new widget.Label();
        jLabelT = new widget.Label();
        T_Hemo = new widget.TextBox();
        jLabelN = new widget.Label();
        N_Hemo = new widget.TextBox();
        jLabelR = new widget.Label();
        R_Hemo = new widget.TextBox();
        jLabelS = new widget.Label();
        S_Hemo = new widget.TextBox();
        
        jLabelAlasan = new widget.Label();
        AlasanDirujuk = new widget.TextBox();
        jLabelTherapi = new widget.Label();
        Therapi = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Pasien On Location ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tbObat.getSelectedRow() != -1) getData();
            }
        });
        Scroll.setViewportView(tbObat);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setText("Simpan");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan();
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBatal.setText("Baru");
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emptTeks();
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setText("Hapus");
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus();
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setText("Ganti");
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ganti();
            }
        });
        panelGlass8.add(BtnEdit);

        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setText("Keluar");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampil();
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCari.setText("");
                tampil();
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);
        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 470));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setPreferredSize(new java.awt.Dimension(100, 470));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TNoRM.setEditable(false);
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        TPasien.setEditable(false);
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        jLabel8.setText("Tgl.Lahir :");
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setEditable(false);
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 80, 23);
        
        jLabelJk.setText("J.K :");
        FormInput.add(jLabelJk);
        jLabelJk.setBounds(775, 10, 30, 23);
        
        Jk.setEditable(false);
        FormInput.add(Jk);
        Jk.setBounds(810, 10, 30, 23);

        jLabel16.setText("Tanggal Pengkajian :");
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 120, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setOpaque(false);
        FormInput.add(Tanggal);
        Tanggal.setBounds(124, 40, 90, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        FormInput.add(Jam);
        Jam.setBounds(218, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(Menit);
        Menit.setBounds(283, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(Detik);
        Detik.setBounds(348, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(413, 40, 23, 23);

        jLabel18.setText("Petugas IGD :");
        FormInput.add(jLabel18);
        jLabel18.setBounds(440, 40, 70, 23);

        KdPetugas.setEditable(false);
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(514, 40, 94, 23);

        NmPetugas.setEditable(false);
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(610, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                petugas.emptTeks();
                petugas.isCek();
                petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                petugas.setLocationRelativeTo(internalFrame1);
                petugas.setVisible(true);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(801, 40, 28, 23);

        // Harap di isi oleh Perujuk
        jLabelHarap.setText("Harap di isi oleh Perujuk:");
        jLabelHarap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabelHarap);
        jLabelHarap.setBounds(10, 70, 200, 23);

        jLabelTglRujuk.setText("Tanggal / Jam :");
        FormInput.add(jLabelTglRujuk);
        jLabelTglRujuk.setBounds(0, 100, 120, 23);
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk.setOpaque(false);
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(124, 100, 90, 23);
        
        JamRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        FormInput.add(JamRujuk);
        JamRujuk.setBounds(218, 100, 62, 23);

        MenitRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(MenitRujuk);
        MenitRujuk.setBounds(283, 100, 62, 23);

        DetikRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(DetikRujuk);
        DetikRujuk.setBounds(348, 100, 62, 23);

        jLabelIdPerujuk.setText("Identitas Perujuk :");
        FormInput.add(jLabelIdPerujuk);
        jLabelIdPerujuk.setBounds(0, 130, 120, 23);
        FormInput.add(IdPerujuk);
        IdPerujuk.setBounds(124, 130, 300, 23);

        jLabelInstalasi.setText("Instalasi Perujuk :");
        FormInput.add(jLabelInstalasi);
        jLabelInstalasi.setBounds(434, 130, 100, 23);
        FormInput.add(InstalasiPerujuk);
        InstalasiPerujuk.setBounds(538, 130, 290, 23);

        jLabelPasien.setText("Identitas Pasien :");
        jLabelPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabelPasien);
        jLabelPasien.setBounds(10, 160, 200, 23);

        jLabelNamaPas.setText("Nama :");
        FormInput.add(jLabelNamaPas);
        jLabelNamaPas.setBounds(0, 190, 120, 23);
        FormInput.add(NamaPasien);
        NamaPasien.setBounds(124, 190, 250, 23);

        jLabelUmur.setText("Umur/Tgl Lahir :");
        FormInput.add(jLabelUmur);
        jLabelUmur.setBounds(384, 190, 100, 23);
        FormInput.add(UmurPasien);
        UmurPasien.setBounds(488, 190, 340, 23);

        jLabelAlamat.setText("Alamat :");
        FormInput.add(jLabelAlamat);
        jLabelAlamat.setBounds(0, 220, 120, 23);
        FormInput.add(Alamat);
        Alamat.setBounds(124, 220, 704, 23);

        jLabelDiag.setText("Diagnosa :");
        FormInput.add(jLabelDiag);
        jLabelDiag.setBounds(0, 250, 120, 23);
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(124, 250, 704, 23);

        jLabelKeluhan.setText("Keluhan Utama :");
        FormInput.add(jLabelKeluhan);
        jLabelKeluhan.setBounds(0, 280, 120, 23);
        FormInput.add(KeluhanUtama);
        KeluhanUtama.setBounds(124, 280, 704, 23);
        
        jLabelGCS.setText("Kesadaran dlm GCS :");
        FormInput.add(jLabelGCS);
        jLabelGCS.setBounds(0, 310, 120, 23);
        FormInput.add(GCS);
        GCS.setBounds(124, 310, 704, 23);

        jLabelHemodinamik.setText("Haemodinamik :");
        FormInput.add(jLabelHemodinamik);
        jLabelHemodinamik.setBounds(0, 340, 120, 23);
        
        jLabelT.setText("T:");
        FormInput.add(jLabelT);
        jLabelT.setBounds(124, 340, 20, 23);
        FormInput.add(T_Hemo);
        T_Hemo.setBounds(148, 340, 100, 23);

        jLabelN.setText("N:");
        FormInput.add(jLabelN);
        jLabelN.setBounds(268, 340, 20, 23);
        FormInput.add(N_Hemo);
        N_Hemo.setBounds(292, 340, 100, 23);

        jLabelR.setText("R:");
        FormInput.add(jLabelR);
        jLabelR.setBounds(412, 340, 20, 23);
        FormInput.add(R_Hemo);
        R_Hemo.setBounds(436, 340, 100, 23);

        jLabelS.setText("S:");
        FormInput.add(jLabelS);
        jLabelS.setBounds(556, 340, 20, 23);
        FormInput.add(S_Hemo);
        S_Hemo.setBounds(580, 340, 100, 23);

        jLabelAlasan.setText("Alasan dirujuk :");
        FormInput.add(jLabelAlasan);
        jLabelAlasan.setBounds(0, 370, 120, 23);
        FormInput.add(AlasanDirujuk);
        AlasanDirujuk.setBounds(124, 370, 704, 23);

        jLabelTherapi.setText("Therapi yg diberikan :");
        FormInput.add(jLabelTherapi);
        jLabelTherapi.setBounds(0, 400, 120, 23);
        FormInput.add(Therapi);
        Therapi.setBounds(124, 400, 704, 23);
        
        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); 
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isForm();
            }
        });
        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        pack();
    }

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                "select p.no_rawat, r.no_rkm_medis, ps.nm_pasien, ps.tgl_lahir, ps.jk, p.nip, pt.nama, p.tanggal, " +
                "p.tanggal_rujuk, p.identitas_perujuk, p.instalasi_perujuk, p.nama_pasien, p.umur_tgl_lahir, p.alamat, " +
                "p.diagnosa, p.keluhan_utama, p.kesadaran_gcs, p.t_hemodinamik, p.n_hemodinamik, p.r_hemodinamik, p.s_hemodinamik, p.alasan_dirujuk, p.therapi " +
                "from skrining_pasien_on_location p inner join reg_periksa r on p.no_rawat=r.no_rawat " +
                "inner join pasien ps on r.no_rkm_medis=ps.no_rkm_medis inner join petugas pt on p.nip=pt.nip " +
                "where p.tanggal between ? and ? and (p.no_rawat like ? or r.no_rkm_medis like ? or ps.nm_pasien like ? or p.identitas_perujuk like ? or pt.nama like ?) order by p.tanggal desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                ps.setString(3, "%" + TCari.getText() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
                ps.setString(5, "%" + TCari.getText() + "%");
                ps.setString(6, "%" + TCari.getText() + "%");
                ps.setString(7, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), 
                        rs.getString("tgl_lahir"), rs.getString("jk"), rs.getString("nip"), rs.getString("nama"), 
                        rs.getString("tanggal"), rs.getString("tanggal_rujuk"), rs.getString("identitas_perujuk"), 
                        rs.getString("instalasi_perujuk"), rs.getString("nama_pasien"), rs.getString("umur_tgl_lahir"), 
                        rs.getString("alamat"), rs.getString("diagnosa"), rs.getString("keluhan_utama"), 
                        rs.getString("kesadaran_gcs"), rs.getString("t_hemodinamik"), rs.getString("n_hemodinamik"), 
                        rs.getString("r_hemodinamik"), rs.getString("s_hemodinamik"), rs.getString("alasan_dirujuk"), 
                        rs.getString("therapi")
                    });
                }
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        IdPerujuk.setText("");
        InstalasiPerujuk.setText("");
        NamaPasien.setText("");
        UmurPasien.setText("");
        Alamat.setText("");
        Diagnosa.setText("");
        KeluhanUtama.setText("");
        GCS.setText("");
        T_Hemo.setText("");
        N_Hemo.setText("");
        R_Hemo.setText("");
        S_Hemo.setText("");
        AlasanDirujuk.setText("");
        Therapi.setText("");
        Tanggal.setDate(new Date());
        TanggalRujuk.setDate(new Date());
        IdPerujuk.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            
            String tgl = tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString();
            Jam.setSelectedItem(tgl.substring(11, 13));
            Menit.setSelectedItem(tgl.substring(14, 16));
            Detik.setSelectedItem(tgl.substring(17, 19));
            Valid.SetTgl(Tanggal, tgl);
            
            String tglRjk = tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString();
            JamRujuk.setSelectedItem(tglRjk.substring(11, 13));
            MenitRujuk.setSelectedItem(tglRjk.substring(14, 16));
            DetikRujuk.setSelectedItem(tglRjk.substring(17, 19));
            Valid.SetTgl(TanggalRujuk, tglRjk);
            
            IdPerujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            InstalasiPerujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            NamaPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            UmurPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            T_Hemo.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            N_Hemo.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            R_Hemo.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            S_Hemo.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            AlasanDirujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            Therapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
        }
    }

    private void simpan() {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Petugas");
        } else {
            if (Sequel.menyimpantf("skrining_pasien_on_location", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 18, new String[]{
                TNoRw.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                KdPetugas.getText(),
                Valid.SetTgl(TanggalRujuk.getSelectedItem() + "") + " " + JamRujuk.getSelectedItem() + ":" + MenitRujuk.getSelectedItem() + ":" + DetikRujuk.getSelectedItem(),
                IdPerujuk.getText(),
                InstalasiPerujuk.getText(),
                NamaPasien.getText(),
                UmurPasien.getText(),
                Alamat.getText(),
                Diagnosa.getText(),
                KeluhanUtama.getText(),
                GCS.getText(),
                T_Hemo.getText(),
                N_Hemo.getText(),
                R_Hemo.getText(),
                S_Hemo.getText(),
                AlasanDirujuk.getText(),
                Therapi.getText()
            }) == true) {
                tampil();
                emptTeks();
            }
        }
    }

    private void ganti() {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Petugas");
        } else {
            if (Sequel.mengedittf("skrining_pasien_on_location", "no_rawat=?", 
                "tanggal=?, nip=?, tanggal_rujuk=?, identitas_perujuk=?, instalasi_perujuk=?, nama_pasien=?, umur_tgl_lahir=?, alamat=?, diagnosa=?, keluhan_utama=?, kesadaran_gcs=?, t_hemodinamik=?, n_hemodinamik=?, r_hemodinamik=?, s_hemodinamik=?, alasan_dirujuk=?, therapi=?", 18, new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                KdPetugas.getText(),
                Valid.SetTgl(TanggalRujuk.getSelectedItem() + "") + " " + JamRujuk.getSelectedItem() + ":" + MenitRujuk.getSelectedItem() + ":" + DetikRujuk.getSelectedItem(),
                IdPerujuk.getText(),
                InstalasiPerujuk.getText(),
                NamaPasien.getText(),
                UmurPasien.getText(),
                Alamat.getText(),
                Diagnosa.getText(),
                KeluhanUtama.getText(),
                GCS.getText(),
                T_Hemo.getText(),
                N_Hemo.getText(),
                R_Hemo.getText(),
                S_Hemo.getText(),
                AlasanDirujuk.getText(),
                Therapi.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
            }) == true) {
                tampil();
                emptTeks();
            }
        }
    }

    private void hapus() {
        if (tbObat.getSelectedRow() != -1) {
            if (Sequel.queryu2tf("delete from skrining_pasien_on_location where no_rawat=?", 1, new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
            }) == true) {
                tampil();
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang mau dihapus..!!");
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='" + norwt + "'", TNoRM);
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + TNoRM.getText() + "'", TPasien);
        Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis='" + TNoRM.getText() + "'", TglLahir);
        Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + TNoRM.getText() + "'", Jk);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 470));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnHapus.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnEdit.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnPrint.setEnabled(akses.getskrining_nutrisi_dewasa());
        if (akses.getjml2() >= 1) {
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if (NmPetugas.getText().equals("")) {
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }
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
                
                Date now = Calendar.getInstance().getTime();

                if (ChkKejadian.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else {
                    nilai_jam = Jam.getSelectedIndex();
                    nilai_menit = Menit.getSelectedIndex();
                    nilai_detik = Detik.getSelectedIndex();
                }

                if (nilai_jam <= 9) nol_jam = "0";
                if (nilai_menit <= 9) nol_menit = "0";
                if (nilai_detik <= 9) nol_detik = "0";
                
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
                
                if (ChkKejadian.isSelected() == true) {
                    JamRujuk.setSelectedItem(jam);
                    MenitRujuk.setSelectedItem(menit);
                    DetikRujuk.setSelectedItem(detik);
                }
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    private void MnSkriningOnLocationActionPerformed(java.awt.event.ActionEvent evt) {
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Valid.MyReportqry("rptSkriningOnLocation.jasper","report","::[ Formulir Skrining Pasien On Location ]::",
                    "select p.no_rawat, r.no_rkm_medis, ps.nm_pasien, ps.tgl_lahir, ps.jk, p.nip, pt.nama, p.tanggal, " +
                    "p.tanggal_rujuk, p.identitas_perujuk, p.instalasi_perujuk, p.nama_pasien as nama_pasien_rujuk, p.umur_tgl_lahir, p.alamat, " +
                    "p.diagnosa, p.keluhan_utama, p.kesadaran_gcs, p.t_hemodinamik, p.n_hemodinamik, p.r_hemodinamik, p.s_hemodinamik, p.alasan_dirujuk, p.therapi " +
                    "from skrining_pasien_on_location p inner join reg_periksa r on p.no_rawat=r.no_rawat " +
                    "inner join pasien ps on r.no_rkm_medis=ps.no_rkm_medis inner join petugas pt on p.nip=pt.nip " +
                    "where p.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }

    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnSkriningOnLocation;
}
