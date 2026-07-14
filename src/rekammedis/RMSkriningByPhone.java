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

public final class RMSkriningByPhone extends javax.swing.JDialog {
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
    
    private widget.Label jLabelPenelepon;
    private widget.Label jLabelNamaPen;
    private widget.TextBox NamaPenelepon;
    private widget.Label jLabelAsal;
    private widget.TextBox AsalPenelepon;
    
    private widget.Label jLabelPasien;
    private widget.Label jLabelNamaPas;
    private widget.TextBox NamaPasienPhone;
    private widget.Label jLabelUsia;
    private widget.TextBox UsiaPasienPhone;
    private widget.Label jLabelDiag;
    private widget.TextBox DiagnosaPhone;
    private widget.Label jLabelGCS;
    private widget.TextBox GCSPhone;
    private widget.Label jLabelKeluhan;
    private widget.TextBox KeluhanPhone;
    private widget.Label jLabelTTV;
    private widget.TextBox TTVPhone;
    
    private widget.Label jLabelSDM;
    private widget.ComboBox SaranaSDM;
    private widget.Label jLabelSaran;
    private widget.TextBox Saran;

    public RMSkriningByPhone(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(850, 700);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Kode Petugas", "Nama Petugas", "Tanggal",
            "Nama Penelepon", "Asal Penelepon", "Nama Pasien (Ph)", "Usia (Ph)", "Diagnosa", "Kes/GCS", "Keluhan Umum", "TTV", "Sarana/SDM", "Saran"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(800, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(105);
            else if (i == 1) column.setPreferredWidth(70);
            else if (i == 2) column.setPreferredWidth(150);
            else if (i == 3) column.setPreferredWidth(65);
            else if (i == 4) column.setPreferredWidth(35);
            else if (i == 5) column.setPreferredWidth(80);
            else if (i == 6) column.setPreferredWidth(150);
            else if (i == 7) column.setPreferredWidth(115);
            else if (i == 8) column.setPreferredWidth(150);
            else if (i == 9) column.setPreferredWidth(150);
            else if (i == 10) column.setPreferredWidth(150);
            else if (i == 11) column.setPreferredWidth(80);
            else if (i == 12) column.setPreferredWidth(200);
            else if (i == 13) column.setPreferredWidth(100);
            else if (i == 14) column.setPreferredWidth(200);
            else if (i == 15) column.setPreferredWidth(200);
            else if (i == 16) column.setPreferredWidth(100);
            else if (i == 17) column.setPreferredWidth(250);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningByPhone = new javax.swing.JMenuItem();
        MnSkriningByPhone.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningByPhone.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningByPhone.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningByPhone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MnSkriningByPhone.setText("Cetak Laporan Skrining By Phone");
        MnSkriningByPhone.setName("MnSkriningByPhone");
        MnSkriningByPhone.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningByPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningByPhoneActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningByPhone);
        tbObat.setComponentPopupMenu(jPopupMenu1);

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte) 20).getKata(KdPetugas));
        NamaPenelepon.setDocument(new batasInput((int) 100).getKata(NamaPenelepon));
        AsalPenelepon.setDocument(new batasInput((int) 100).getKata(AsalPenelepon));
        NamaPasienPhone.setDocument(new batasInput((int) 100).getKata(NamaPasienPhone));
        UsiaPasienPhone.setDocument(new batasInput((int) 50).getKata(UsiaPasienPhone));
        DiagnosaPhone.setDocument(new batasInput((int) 200).getKata(DiagnosaPhone));
        GCSPhone.setDocument(new batasInput((int) 100).getKata(GCSPhone));
        KeluhanPhone.setDocument(new batasInput((int) 200).getKata(KeluhanPhone));
        TTVPhone.setDocument(new batasInput((int) 200).getKata(TTVPhone));
        Saran.setDocument(new batasInput((int) 300).getKata(Saran));
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
        
        jLabelPenelepon = new widget.Label();
        jLabelNamaPen = new widget.Label();
        NamaPenelepon = new widget.TextBox();
        jLabelAsal = new widget.Label();
        AsalPenelepon = new widget.TextBox();
        
        jLabelPasien = new widget.Label();
        jLabelNamaPas = new widget.Label();
        NamaPasienPhone = new widget.TextBox();
        jLabelUsia = new widget.Label();
        UsiaPasienPhone = new widget.TextBox();
        jLabelDiag = new widget.Label();
        DiagnosaPhone = new widget.TextBox();
        jLabelGCS = new widget.Label();
        GCSPhone = new widget.TextBox();
        jLabelKeluhan = new widget.Label();
        KeluhanPhone = new widget.TextBox();
        jLabelTTV = new widget.Label();
        TTVPhone = new widget.TextBox();
        
        jLabelSDM = new widget.Label();
        SaranaSDM = new widget.ComboBox();
        jLabelSaran = new widget.Label();
        Saran = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Pasien By Phone IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 400));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setPreferredSize(new java.awt.Dimension(100, 400));
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

        jLabel16.setText("Tanggal :");
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setOpaque(false);
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

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
        btnPetugas.setBounds(761, 40, 28, 23);

        // Identitas Penelepon
        jLabelPenelepon.setText("IDENTITAS PENELEPON :");
        jLabelPenelepon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabelPenelepon);
        jLabelPenelepon.setBounds(10, 70, 200, 23);

        jLabelNamaPen.setText("Nama Petugas :");
        FormInput.add(jLabelNamaPen);
        jLabelNamaPen.setBounds(0, 100, 100, 23);
        FormInput.add(NamaPenelepon);
        NamaPenelepon.setBounds(104, 100, 250, 23);

        jLabelAsal.setText("Asal RS/Klinik/PKM :");
        FormInput.add(jLabelAsal);
        jLabelAsal.setBounds(360, 100, 120, 23);
        FormInput.add(AsalPenelepon);
        AsalPenelepon.setBounds(484, 100, 250, 23);

        // Identitas Pasien
        jLabelPasien.setText("IDENTITAS PASIEN :");
        jLabelPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabelPasien);
        jLabelPasien.setBounds(10, 130, 200, 23);

        jLabelNamaPas.setText("Nama :");
        FormInput.add(jLabelNamaPas);
        jLabelNamaPas.setBounds(0, 160, 100, 23);
        FormInput.add(NamaPasienPhone);
        NamaPasienPhone.setBounds(104, 160, 250, 23);

        jLabelUsia.setText("Usia Pasien :");
        FormInput.add(jLabelUsia);
        jLabelUsia.setBounds(360, 160, 120, 23);
        FormInput.add(UsiaPasienPhone);
        UsiaPasienPhone.setBounds(484, 160, 250, 23);

        jLabelDiag.setText("Diagnosa :");
        FormInput.add(jLabelDiag);
        jLabelDiag.setBounds(0, 190, 100, 23);
        FormInput.add(DiagnosaPhone);
        DiagnosaPhone.setBounds(104, 190, 630, 23);

        jLabelGCS.setText("Kes/GCS :");
        FormInput.add(jLabelGCS);
        jLabelGCS.setBounds(0, 220, 100, 23);
        FormInput.add(GCSPhone);
        GCSPhone.setBounds(104, 220, 630, 23);

        jLabelKeluhan.setText("Keluhan Umum :");
        FormInput.add(jLabelKeluhan);
        jLabelKeluhan.setBounds(0, 250, 100, 23);
        FormInput.add(KeluhanPhone);
        KeluhanPhone.setBounds(104, 250, 630, 23);

        jLabelTTV.setText("TTV :");
        FormInput.add(jLabelTTV);
        jLabelTTV.setBounds(0, 280, 100, 23);
        FormInput.add(TTVPhone);
        TTVPhone.setBounds(104, 280, 630, 23);

        jLabelSDM.setText("SARANA/SDM TERSEDIA :");
        FormInput.add(jLabelSDM);
        jLabelSDM.setBounds(0, 310, 150, 23);
        SaranaSDM.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tersedia", "Tidak Tersedia"}));
        FormInput.add(SaranaSDM);
        SaranaSDM.setBounds(154, 310, 150, 23);

        jLabelSaran.setText("Saran :");
        FormInput.add(jLabelSaran);
        jLabelSaran.setBounds(0, 340, 100, 23);
        FormInput.add(Saran);
        Saran.setBounds(104, 340, 630, 23);
        
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
                "p.nama_penelepon, p.asal_penelepon, p.nama_pasien, p.usia_pasien, p.diagnosa, p.kes_gcs, p.keluhan_umum, p.ttv, p.sarana_sdm, p.saran " +
                "from skrining_pasien_by_phone_igd p inner join reg_periksa r on p.no_rawat=r.no_rawat " +
                "inner join pasien ps on r.no_rkm_medis=ps.no_rkm_medis inner join petugas pt on p.nip=pt.nip " +
                "where p.tanggal between ? and ? and (p.no_rawat like ? or r.no_rkm_medis like ? or ps.nm_pasien like ? or p.nama_penelepon like ? or pt.nama like ?) order by p.tanggal desc");
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
                        rs.getString("tanggal"), rs.getString("nama_penelepon"), rs.getString("asal_penelepon"), 
                        rs.getString("nama_pasien"), rs.getString("usia_pasien"), rs.getString("diagnosa"), 
                        rs.getString("kes_gcs"), rs.getString("keluhan_umum"), rs.getString("ttv"), 
                        rs.getString("sarana_sdm"), rs.getString("saran")
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
        NamaPenelepon.setText("");
        AsalPenelepon.setText("");
        NamaPasienPhone.setText("");
        UsiaPasienPhone.setText("");
        DiagnosaPhone.setText("");
        GCSPhone.setText("");
        KeluhanPhone.setText("");
        TTVPhone.setText("");
        SaranaSDM.setSelectedIndex(0);
        Saran.setText("");
        Tanggal.setDate(new Date());
        NamaPenelepon.requestFocus();
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
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(11, 13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(14, 16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString().substring(17, 19));
            NamaPenelepon.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            AsalPenelepon.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            NamaPasienPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            UsiaPasienPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            DiagnosaPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            GCSPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            KeluhanPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            TTVPhone.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            SaranaSDM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Saran.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
        }
    }

    private void simpan() {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Petugas");
        } else {
            if (Sequel.menyimpantf("skrining_pasien_by_phone_igd", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 13, new String[]{
                TNoRw.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                KdPetugas.getText(),
                NamaPenelepon.getText(),
                AsalPenelepon.getText(),
                NamaPasienPhone.getText(),
                UsiaPasienPhone.getText(),
                DiagnosaPhone.getText(),
                GCSPhone.getText(),
                KeluhanPhone.getText(),
                TTVPhone.getText(),
                SaranaSDM.getSelectedItem().toString(),
                Saran.getText()
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
            if (Sequel.mengedittf("skrining_pasien_by_phone_igd", "no_rawat=?", 
                "tanggal=?, nip=?, nama_penelepon=?, asal_penelepon=?, nama_pasien=?, usia_pasien=?, diagnosa=?, kes_gcs=?, keluhan_umum=?, ttv=?, sarana_sdm=?, saran=?", 13, new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                KdPetugas.getText(),
                NamaPenelepon.getText(),
                AsalPenelepon.getText(),
                NamaPasienPhone.getText(),
                UsiaPasienPhone.getText(),
                DiagnosaPhone.getText(),
                GCSPhone.getText(),
                KeluhanPhone.getText(),
                TTVPhone.getText(),
                SaranaSDM.getSelectedItem().toString(),
                Saran.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
            }) == true) {
                tampil();
                emptTeks();
            }
        }
    }

    private void hapus() {
        if (tbObat.getSelectedRow() != -1) {
            if (Sequel.queryu2tf("delete from skrining_pasien_by_phone_igd where no_rawat=?", 1, new String[]{
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
            PanelInput.setPreferredSize(new Dimension(WIDTH, 400));
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
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    private void MnSkriningByPhoneActionPerformed(java.awt.event.ActionEvent evt) {
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
            Valid.MyReportqry("rptSkriningByPhone.jasper","report","::[ Formulir Skrining Pasien By Phone ]::",
                    "select p.no_rawat, r.no_rkm_medis, ps.nm_pasien, ps.tgl_lahir, ps.jk, p.nip, pt.nama, p.tanggal, " +
                    "p.nama_penelepon, p.asal_penelepon, p.nama_pasien as nama_pasien_phone, p.usia_pasien, p.diagnosa, p.kes_gcs, p.keluhan_umum, p.ttv, p.sarana_sdm, p.saran " +
                    "from skrining_pasien_by_phone_igd p inner join reg_periksa r on p.no_rawat=r.no_rawat " +
                    "inner join pasien ps on r.no_rkm_medis=ps.no_rkm_medis inner join petugas pt on p.nip=pt.nip " +
                    "where p.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }

    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnSkriningByPhone;
}
