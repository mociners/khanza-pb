package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class RMDischargePlanning extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;

    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelGlass8;
    private widget.Button BtnSimpan, BtnBatal, BtnHapus, BtnEdit, BtnPrint, BtnAll, BtnKeluar;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.ScrollPane scrollInput;
    private widget.PanelBiasa FormInput;
    
    private widget.ScrollPane Scroll;
    private widget.Table tbObat;
    private widget.panelisi panelGlass9;
    private widget.Label jLabel19;
    private widget.Tanggal DTPCari1;
    private widget.Label jLabel21;
    private widget.Tanggal DTPCari2;
    private widget.Label jLabel6;
    private widget.TextBox TCari;
    private widget.Button BtnCari;
    private widget.Label jLabel7;
    private widget.Label LCount;
    
    private widget.TextBox TNoRw, TNoRM, TPasien, TglLahir, Jk;
    private widget.TextBox KdPetugas, NmPetugas;
    
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnCetak;

    private widget.Label jLabelKdDokter;
    private widget.TextBox KdDokter;
    private widget.TextBox NmDokter;
    private widget.Button BtnDokter;
    private widget.Label jLabelTglMasuk;
    private widget.Tanggal TglMasuk;
    private widget.Label jLabelTglPengkajian;
    private widget.Tanggal TglPengkajian;
    private widget.Label jLabelTglKeluar;
    private widget.Tanggal TglKeluar;
    private widget.Label jLabelDiagnosaMasuk;
    private widget.TextBox DiagnosaMasuk;
    private widget.Label jLabelDiagnosaKeluar;
    private widget.TextBox DiagnosaKeluar;
    private widget.Label jLabelIndikasiPulang;
    private widget.TextBox IndikasiPulang;
    private widget.Label jLabelDietKhusus;
    private widget.TextBox DietKhusus;
    private widget.Label jLabelKanulIV;
    private widget.ComboBox KanulIV;
    private widget.Label jLabelTglKanulIV;
    private widget.Tanggal TglKanulIV;
    private widget.Label jLabelAlasanKanulIV;
    private widget.TextBox AlasanKanulIV;
    private widget.Label jLabelNgt;
    private widget.ComboBox Ngt;
    private widget.Label jLabelTglNgt;
    private widget.Tanggal TglNgt;
    private widget.Label jLabelAlasanNgt;
    private widget.TextBox AlasanNgt;
    private widget.Label jLabelBalutan;
    private widget.ComboBox Balutan;
    private widget.Label jLabelTglBalutan;
    private widget.Tanggal TglBalutan;
    private widget.Label jLabelAlasanBalutan;
    private widget.TextBox AlasanBalutan;
    private widget.Label jLabelDrain;
    private widget.ComboBox Drain;
    private widget.Label jLabelTglDrain;
    private widget.Tanggal TglDrain;
    private widget.Label jLabelAlasanDrain;
    private widget.TextBox AlasanDrain;
    private widget.Label jLabelKateterUrine;
    private widget.ComboBox KateterUrine;
    private widget.Label jLabelTglKateterUrine;
    private widget.Tanggal TglKateterUrine;
    private widget.Label jLabelAlasanKateterUrine;
    private widget.TextBox AlasanKateterUrine;
    private widget.Label jLabelGelangIdentitas;
    private widget.ComboBox GelangIdentitas;
    private widget.Label jLabelKetGelangIdentitas;
    private widget.TextBox KetGelangIdentitas;
    private widget.Label jLabelLainDilepas;
    private widget.TextBox LainDilepas;
    private widget.Label jLabelTransportasiPulang;
    private widget.ComboBox TransportasiPulang;
    private widget.Label jLabelObatSisa;
    private widget.ComboBox ObatSisa;
    private widget.Label jLabelResepTambahan;
    private widget.ComboBox ResepTambahan;
    private widget.Label jLabelSuratKontrol;
    private widget.ComboBox SuratKontrol;
    private widget.Label jLabelSuratSakit;
    private widget.ComboBox SuratSakit;
    private widget.Label jLabelResumeMedis;
    private widget.ComboBox ResumeMedis;
    private widget.Label jLabelHasilLab;
    private widget.ComboBox HasilLab;
    private widget.Label jLabelTglHasilLab;
    private widget.Tanggal TglHasilLab;
    private widget.Label jLabelHasilRad;
    private widget.ComboBox HasilRad;
    private widget.Label jLabelTglHasilRad;
    private widget.Tanggal TglHasilRad;
    private widget.Label jLabelEkgEegUsg;
    private widget.ComboBox EkgEegUsg;
    private widget.Label jLabelTglEkg;
    private widget.Tanggal TglEkg;
    private widget.Label jLabelHasilPribadi;
    private widget.ComboBox HasilPribadi;
    private widget.Label jLabelTglHasilPribadi;
    private widget.Tanggal TglHasilPribadi;
    private widget.Label jLabelLainHasil;
    private widget.ComboBox LainHasil;
    private widget.Label jLabelTglLainHasil;
    private widget.Tanggal TglLainHasil;
    private widget.Label jLabelOksigenPortable;
    private widget.ComboBox OksigenPortable;
    private widget.Label jLabelTracheostomi;
    private widget.ComboBox Tracheostomi;
    private widget.Label jLabelDowerKateter;
    private widget.ComboBox DowerKateter;
    private widget.Label jLabelTongkat;
    private widget.ComboBox Tongkat;
    private widget.Label jLabelKursiRoda;
    private widget.ComboBox KursiRoda;
    private widget.Label jLabelLainAlatMedis;
    private widget.TextBox LainAlatMedis;
    private widget.Label jLabelPerawatanLuka;
    private widget.ComboBox PerawatanLuka;
    private widget.Label jLabelPerawatanNgtDc;
    private widget.ComboBox PerawatanNgtDc;
    private widget.Label jLabelPerawatanIbu;
    private widget.ComboBox PerawatanIbu;
    private widget.Label jLabelPerawatanBayi;
    private widget.ComboBox PerawatanBayi;
    private widget.Label jLabelFisioterapi;
    private widget.ComboBox Fisioterapi;
    private widget.Label jLabelLainPerawatan;
    private widget.TextBox LainPerawatan;
    private widget.Label jLabelEduBalutan;
    private widget.ComboBox EduBalutan;
    private widget.Label jLabelEduKendaraan;
    private widget.ComboBox EduKendaraan;
    private widget.Label jLabelEduBeban;
    private widget.ComboBox EduBeban;
    private widget.Label jLabelEduLab;
    private widget.ComboBox EduLab;
    private widget.Label jLabelEduTangga;
    private widget.ComboBox EduTangga;
    private widget.Label jLabelEduPekerjaan;
    private widget.ComboBox EduPekerjaan;
    private widget.Label jLabelEduAktifitas;
    private widget.ComboBox EduAktifitas;
    private widget.Label jLabelEduNyeri;
    private widget.ComboBox EduNyeri;
    private widget.Label jLabelEduLainLain;
    private widget.TextBox EduLainLain;
    private widget.Label jLabelDokterKontrol;
    private widget.TextBox DokterKontrol;
    private widget.Label jLabelJadwalKontrol;
    private widget.TextBox JadwalKontrol;
    private widget.Label jLabelFaskes1;
    private widget.TextBox Faskes1;
    private widget.Label jLabelFaskes2;
    private widget.TextBox Faskes2;
    private widget.Label jLabelFaskes3;
    private widget.TextBox Faskes3;


    public RMDischargePlanning(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("RMDischargePlanning")) {
                    if (dokter.getTable().getSelectedRow() != -1) {
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        KdDokter.requestFocus();
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
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat",
            "No.RM",
            "Nama Pasien",
            "Umur",
            "J.K.",
            "Tgl.Lahir",
            "DPJP",
            "Tanggal Masuk RS",
            "Tanggal Pengkajian",
            "Tanggal Keluar RS",
            "Diagnosa Masuk RS",
            "Diagnosa Keluar RS",
            "Indikasi Pulang",
            "Diet Khusus Pasien",
            "Kanul IV",
            "Tgl",
            "Alasan",
            "NGT",
            "Tgl",
            "Alasan",
            "Balutan",
            "Tgl",
            "Alasan",
            "Drain",
            "Tgl",
            "Alasan",
            "Kateter Urine",
            "Tgl",
            "Alasan",
            "Gelang Identitas",
            "Ket",
            "Lain-lain Dilepas",
            "Transportasi saat pulang",
            "Obat obat sisa rawat",
            "Resep obat tambahan",
            "Surat kontrol",
            "Surat sakit/ket dirawat",
            "Resume medis/Rujukan",
            "Hasil Laboratorium",
            "Tgl Hasil Lab",
            "Hasil Radiologi",
            "Tgl Hasil Rad",
            "EKG / EEG / USG",
            "Tgl EKG",
            "Pemeriksaan pribadi",
            "Tgl Hasil Pribadi",
            "Lain-lain Hasil",
            "Tgl Lain Hasil",
            "Oksigen Portable",
            "Tracheostomi",
            "Dower kateter",
            "Tongkat",
            "Kursi Roda",
            "Lain-lain Alat Medis",
            "Perawatan Luka",
            "Perawatan NGT/DC",
            "Perawatan ibu sehat",
            "Perawatan bayi sehat",
            "Fisioterapi",
            "Lain-lain Perawatan",
            "Balutan jgn basah",
            "Jgn mengendarai motor",
            "Hindari beban berat",
            "Cek lab sblm kontrol",
            "Jgn naik tangga >2x/hr",
            "Batasi pekerjaan",
            "Aktifitas bertahap",
            "Jika keluhan nyeri",
            "Edukasi Lain-lain",
            "Nama Dokter Kontrol",
            "Jadwal Kontrol",
            "Faskes terdekat 1",
            "Faskes terdekat 2",
            "Faskes terdekat 3",
            "NIP",
            "Nama Petugas"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbObat.setComponentPopupMenu(jPopupMenu1);

        for (i = 0; i < 76; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tampil();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                tampil();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                tampil();
            }
        });
    }

    private void initComponents() {
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetak = new javax.swing.JMenuItem();

        MnCetak.setBackground(new java.awt.Color(255, 255, 255));
        MnCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetak.setForeground(new java.awt.Color(50, 50, 50));
        MnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        MnCetak.setText("Cetak Form Discharge Planning");
        MnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetak);
        
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
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
        
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Discharge Planning ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));
        
        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(TabRawat.getSelectedIndex()==1){
                    tampil();
                }
            }
        });
        
        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1460));
        FormInput.setLayout(null);

        widget.Label jLabelNoRw = new widget.Label();
        jLabelNoRw.setText("No.Rawat :");
        jLabelNoRw.setBounds(10, 10, 70, 23);
        FormInput.add(jLabelNoRw);

        TNoRw.setEditable(false);
        TNoRw.setBounds(84, 10, 131, 23);
        FormInput.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setBounds(218, 10, 80, 23);
        FormInput.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setBounds(300, 10, 285, 23);
        FormInput.add(TPasien);

        Jk.setEditable(false);
        Jk.setBounds(588, 10, 30, 23);
        FormInput.add(Jk);

        TglLahir.setEditable(false);
        TglLahir.setBounds(620, 10, 100, 23);
        FormInput.add(TglLahir);


        widget.Label header0 = new widget.Label();
        header0.setText("A. INFORMASI UMUM");
        header0.setFont(new java.awt.Font("Tahoma", 1, 11));
        header0.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header0.setBounds(10, 50, 800, 23);
        FormInput.add(header0);

        javax.swing.JSeparator sep0 = new javax.swing.JSeparator();
        sep0.setBounds(10, 75, 830, 10);
        FormInput.add(sep0);

        jLabelKdDokter = new widget.Label();
        jLabelKdDokter.setText("DPJP :");
        jLabelKdDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKdDokter.setBounds(20, 90, 145, 23);
        FormInput.add(jLabelKdDokter);

        KdDokter = new widget.TextBox();
        KdDokter.setBounds(170, 90, 70, 23);
        FormInput.add(KdDokter);
        
        NmDokter = new widget.TextBox();
        NmDokter.setEditable(false);
        NmDokter.setBounds(242, 90, 130, 23);
        FormInput.add(NmDokter);
        
        BtnDokter = new widget.Button();
        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnDokter.setBounds(374, 90, 28, 23);
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                akses.setform("RMDischargePlanning");
                kepegawaian.DlgCariDokter dokter = new kepegawaian.DlgCariDokter(null, false);
                dokter.isCek();
                dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setVisible(true);
            }
        });
        FormInput.add(BtnDokter);

        jLabelTglMasuk = new widget.Label();
        jLabelTglMasuk.setText("Tanggal Masuk RS :");
        jLabelTglMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglMasuk.setBounds(430, 90, 145, 23);
        FormInput.add(jLabelTglMasuk);

        TglMasuk = new widget.Tanggal();
        TglMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglMasuk.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglMasuk.setOpaque(false);
        TglMasuk.setBounds(580, 90, 240, 23);
        FormInput.add(TglMasuk);

        jLabelTglPengkajian = new widget.Label();
        jLabelTglPengkajian.setText("Tanggal Pengkajian :");
        jLabelTglPengkajian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglPengkajian.setBounds(20, 120, 145, 23);
        FormInput.add(jLabelTglPengkajian);

        TglPengkajian = new widget.Tanggal();
        TglPengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglPengkajian.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglPengkajian.setOpaque(false);
        TglPengkajian.setBounds(170, 120, 240, 23);
        FormInput.add(TglPengkajian);

        jLabelTglKeluar = new widget.Label();
        jLabelTglKeluar.setText("Tanggal Keluar RS :");
        jLabelTglKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglKeluar.setBounds(430, 120, 145, 23);
        FormInput.add(jLabelTglKeluar);

        TglKeluar = new widget.Tanggal();
        TglKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglKeluar.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglKeluar.setOpaque(false);
        TglKeluar.setBounds(580, 120, 240, 23);
        FormInput.add(TglKeluar);

        jLabelDiagnosaMasuk = new widget.Label();
        jLabelDiagnosaMasuk.setText("Diagnosa Masuk RS :");
        jLabelDiagnosaMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDiagnosaMasuk.setBounds(20, 150, 145, 23);
        FormInput.add(jLabelDiagnosaMasuk);

        DiagnosaMasuk = new widget.TextBox();
        DiagnosaMasuk.setBounds(170, 150, 240, 23);
        FormInput.add(DiagnosaMasuk);

        jLabelDiagnosaKeluar = new widget.Label();
        jLabelDiagnosaKeluar.setText("Diagnosa Keluar RS :");
        jLabelDiagnosaKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDiagnosaKeluar.setBounds(430, 150, 145, 23);
        FormInput.add(jLabelDiagnosaKeluar);

        DiagnosaKeluar = new widget.TextBox();
        DiagnosaKeluar.setBounds(580, 150, 240, 23);
        FormInput.add(DiagnosaKeluar);

        jLabelIndikasiPulang = new widget.Label();
        jLabelIndikasiPulang.setText("Indikasi Pulang :");
        jLabelIndikasiPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelIndikasiPulang.setBounds(20, 180, 145, 23);
        FormInput.add(jLabelIndikasiPulang);

        IndikasiPulang = new widget.TextBox();
        IndikasiPulang.setBounds(170, 180, 240, 23);
        FormInput.add(IndikasiPulang);

        jLabelDietKhusus = new widget.Label();
        jLabelDietKhusus.setText("Diet Khusus Pasien :");
        jLabelDietKhusus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDietKhusus.setBounds(430, 180, 145, 23);
        FormInput.add(jLabelDietKhusus);

        DietKhusus = new widget.TextBox();
        DietKhusus.setBounds(580, 180, 240, 23);
        FormInput.add(DietKhusus);

        widget.Label header1 = new widget.Label();
        header1.setText("B. ITEM YANG AKAN DILEPASKAN SAAT AKAN PULANG");
        header1.setFont(new java.awt.Font("Tahoma", 1, 11));
        header1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header1.setBounds(10, 220, 800, 23);
        FormInput.add(header1);

        javax.swing.JSeparator sep1 = new javax.swing.JSeparator();
        sep1.setBounds(10, 245, 830, 10);
        FormInput.add(sep1);

        jLabelKanulIV = new widget.Label();
        jLabelKanulIV.setText("Kanul IV :");
        jLabelKanulIV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKanulIV.setBounds(20, 260, 100, 23);
        FormInput.add(jLabelKanulIV);

        KanulIV = new widget.ComboBox();
        KanulIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "Tidak dilepas", "Tidak ada"}));
        KanulIV.setBounds(120, 260, 110, 23);
        FormInput.add(KanulIV);

        jLabelTglKanulIV = new widget.Label();
        jLabelTglKanulIV.setText("Tgl :");
        jLabelTglKanulIV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglKanulIV.setBounds(250, 260, 30, 23);
        FormInput.add(jLabelTglKanulIV);

        TglKanulIV = new widget.Tanggal();
        TglKanulIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglKanulIV.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglKanulIV.setOpaque(false);
        TglKanulIV.setBounds(280, 260, 140, 23);
        FormInput.add(TglKanulIV);

        jLabelAlasanKanulIV = new widget.Label();
        jLabelAlasanKanulIV.setText("Alasan :");
        jLabelAlasanKanulIV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAlasanKanulIV.setBounds(430, 260, 50, 23);
        FormInput.add(jLabelAlasanKanulIV);

        AlasanKanulIV = new widget.TextBox();
        AlasanKanulIV.setBounds(480, 260, 340, 23);
        FormInput.add(AlasanKanulIV);

        jLabelNgt = new widget.Label();
        jLabelNgt.setText("NGT :");
        jLabelNgt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNgt.setBounds(20, 290, 100, 23);
        FormInput.add(jLabelNgt);

        Ngt = new widget.ComboBox();
        Ngt.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "dipasang", "Tidak dilepas", "Tidak ada"}));
        Ngt.setBounds(120, 290, 110, 23);
        FormInput.add(Ngt);

        jLabelTglNgt = new widget.Label();
        jLabelTglNgt.setText("Tgl :");
        jLabelTglNgt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglNgt.setBounds(250, 290, 30, 23);
        FormInput.add(jLabelTglNgt);

        TglNgt = new widget.Tanggal();
        TglNgt.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglNgt.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglNgt.setOpaque(false);
        TglNgt.setBounds(280, 290, 140, 23);
        FormInput.add(TglNgt);

        jLabelAlasanNgt = new widget.Label();
        jLabelAlasanNgt.setText("Alasan :");
        jLabelAlasanNgt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAlasanNgt.setBounds(430, 290, 50, 23);
        FormInput.add(jLabelAlasanNgt);

        AlasanNgt = new widget.TextBox();
        AlasanNgt.setBounds(480, 290, 340, 23);
        FormInput.add(AlasanNgt);

        jLabelBalutan = new widget.Label();
        jLabelBalutan.setText("Balutan :");
        jLabelBalutan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelBalutan.setBounds(20, 320, 100, 23);
        FormInput.add(jLabelBalutan);

        Balutan = new widget.ComboBox();
        Balutan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "Tidak dilepas", "Tidak ada"}));
        Balutan.setBounds(120, 320, 110, 23);
        FormInput.add(Balutan);

        jLabelTglBalutan = new widget.Label();
        jLabelTglBalutan.setText("Tgl :");
        jLabelTglBalutan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglBalutan.setBounds(250, 320, 30, 23);
        FormInput.add(jLabelTglBalutan);

        TglBalutan = new widget.Tanggal();
        TglBalutan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglBalutan.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglBalutan.setOpaque(false);
        TglBalutan.setBounds(280, 320, 140, 23);
        FormInput.add(TglBalutan);

        jLabelAlasanBalutan = new widget.Label();
        jLabelAlasanBalutan.setText("Alasan :");
        jLabelAlasanBalutan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAlasanBalutan.setBounds(430, 320, 50, 23);
        FormInput.add(jLabelAlasanBalutan);

        AlasanBalutan = new widget.TextBox();
        AlasanBalutan.setBounds(480, 320, 340, 23);
        FormInput.add(AlasanBalutan);

        jLabelDrain = new widget.Label();
        jLabelDrain.setText("Drain :");
        jLabelDrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDrain.setBounds(20, 350, 100, 23);
        FormInput.add(jLabelDrain);

        Drain = new widget.ComboBox();
        Drain.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "Tidak dilepas", "Tidak ada"}));
        Drain.setBounds(120, 350, 110, 23);
        FormInput.add(Drain);

        jLabelTglDrain = new widget.Label();
        jLabelTglDrain.setText("Tgl :");
        jLabelTglDrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglDrain.setBounds(250, 350, 30, 23);
        FormInput.add(jLabelTglDrain);

        TglDrain = new widget.Tanggal();
        TglDrain.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglDrain.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglDrain.setOpaque(false);
        TglDrain.setBounds(280, 350, 140, 23);
        FormInput.add(TglDrain);

        jLabelAlasanDrain = new widget.Label();
        jLabelAlasanDrain.setText("Alasan :");
        jLabelAlasanDrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAlasanDrain.setBounds(430, 350, 50, 23);
        FormInput.add(jLabelAlasanDrain);

        AlasanDrain = new widget.TextBox();
        AlasanDrain.setBounds(480, 350, 340, 23);
        FormInput.add(AlasanDrain);

        jLabelKateterUrine = new widget.Label();
        jLabelKateterUrine.setText("Kateter Urine :");
        jLabelKateterUrine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKateterUrine.setBounds(20, 380, 100, 23);
        FormInput.add(jLabelKateterUrine);

        KateterUrine = new widget.ComboBox();
        KateterUrine.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "Tidak dilepas", "Tidak ada"}));
        KateterUrine.setBounds(120, 380, 110, 23);
        FormInput.add(KateterUrine);

        jLabelTglKateterUrine = new widget.Label();
        jLabelTglKateterUrine.setText("Tgl :");
        jLabelTglKateterUrine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglKateterUrine.setBounds(250, 380, 30, 23);
        FormInput.add(jLabelTglKateterUrine);

        TglKateterUrine = new widget.Tanggal();
        TglKateterUrine.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglKateterUrine.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglKateterUrine.setOpaque(false);
        TglKateterUrine.setBounds(280, 380, 140, 23);
        FormInput.add(TglKateterUrine);

        jLabelAlasanKateterUrine = new widget.Label();
        jLabelAlasanKateterUrine.setText("Alasan :");
        jLabelAlasanKateterUrine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelAlasanKateterUrine.setBounds(430, 380, 50, 23);
        FormInput.add(jLabelAlasanKateterUrine);

        AlasanKateterUrine = new widget.TextBox();
        AlasanKateterUrine.setBounds(480, 380, 340, 23);
        FormInput.add(AlasanKateterUrine);

        jLabelGelangIdentitas = new widget.Label();
        jLabelGelangIdentitas.setText("Gelang Identitas :");
        jLabelGelangIdentitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelGelangIdentitas.setBounds(20, 410, 100, 23);
        FormInput.add(jLabelGelangIdentitas);

        GelangIdentitas = new widget.ComboBox();
        GelangIdentitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Di lepas", "Tidak ada"}));
        GelangIdentitas.setBounds(120, 410, 110, 23);
        FormInput.add(GelangIdentitas);

        jLabelKetGelangIdentitas = new widget.Label();
        jLabelKetGelangIdentitas.setText("Ket :");
        jLabelKetGelangIdentitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKetGelangIdentitas.setBounds(250, 410, 30, 23);
        FormInput.add(jLabelKetGelangIdentitas);

        KetGelangIdentitas = new widget.TextBox();
        KetGelangIdentitas.setBounds(280, 410, 540, 23);
        FormInput.add(KetGelangIdentitas);

        jLabelLainDilepas = new widget.Label();
        jLabelLainDilepas.setText("Lain-lain Dilepas :");
        jLabelLainDilepas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLainDilepas.setBounds(20, 440, 100, 23);
        FormInput.add(jLabelLainDilepas);

        LainDilepas = new widget.TextBox();
        LainDilepas.setBounds(120, 440, 110, 23);
        FormInput.add(LainDilepas);

        widget.Label header2 = new widget.Label();
        header2.setText("C. CEKLIS PULANG");
        header2.setFont(new java.awt.Font("Tahoma", 1, 11));
        header2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header2.setBounds(10, 480, 800, 23);
        FormInput.add(header2);

        javax.swing.JSeparator sep2 = new javax.swing.JSeparator();
        sep2.setBounds(10, 505, 830, 10);
        FormInput.add(sep2);

        jLabelTransportasiPulang = new widget.Label();
        jLabelTransportasiPulang.setText("Transportasi saat pulang :");
        jLabelTransportasiPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTransportasiPulang.setBounds(20, 520, 145, 23);
        FormInput.add(jLabelTransportasiPulang);

        TransportasiPulang = new widget.ComboBox();
        TransportasiPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        TransportasiPulang.setBounds(170, 520, 240, 23);
        FormInput.add(TransportasiPulang);

        jLabelObatSisa = new widget.Label();
        jLabelObatSisa.setText("Obat obat sisa rawat :");
        jLabelObatSisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelObatSisa.setBounds(430, 520, 145, 23);
        FormInput.add(jLabelObatSisa);

        ObatSisa = new widget.ComboBox();
        ObatSisa.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        ObatSisa.setBounds(580, 520, 240, 23);
        FormInput.add(ObatSisa);

        jLabelResepTambahan = new widget.Label();
        jLabelResepTambahan.setText("Resep obat tambahan :");
        jLabelResepTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelResepTambahan.setBounds(20, 550, 145, 23);
        FormInput.add(jLabelResepTambahan);

        ResepTambahan = new widget.ComboBox();
        ResepTambahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        ResepTambahan.setBounds(170, 550, 240, 23);
        FormInput.add(ResepTambahan);

        jLabelSuratKontrol = new widget.Label();
        jLabelSuratKontrol.setText("Surat kontrol :");
        jLabelSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSuratKontrol.setBounds(430, 550, 145, 23);
        FormInput.add(jLabelSuratKontrol);

        SuratKontrol = new widget.ComboBox();
        SuratKontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        SuratKontrol.setBounds(580, 550, 240, 23);
        FormInput.add(SuratKontrol);

        jLabelSuratSakit = new widget.Label();
        jLabelSuratSakit.setText("Surat sakit/ket dirawat :");
        jLabelSuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSuratSakit.setBounds(20, 580, 145, 23);
        FormInput.add(jLabelSuratSakit);

        SuratSakit = new widget.ComboBox();
        SuratSakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        SuratSakit.setBounds(170, 580, 240, 23);
        FormInput.add(SuratSakit);

        jLabelResumeMedis = new widget.Label();
        jLabelResumeMedis.setText("Resume medis/Rujukan :");
        jLabelResumeMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelResumeMedis.setBounds(430, 580, 145, 23);
        FormInput.add(jLabelResumeMedis);

        ResumeMedis = new widget.ComboBox();
        ResumeMedis.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        ResumeMedis.setBounds(580, 580, 240, 23);
        FormInput.add(ResumeMedis);

        jLabelHasilLab = new widget.Label();
        jLabelHasilLab.setText("Hasil Laboratorium :");
        jLabelHasilLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelHasilLab.setBounds(20, 610, 145, 23);
        FormInput.add(jLabelHasilLab);

        HasilLab = new widget.ComboBox();
        HasilLab.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        HasilLab.setBounds(170, 610, 240, 23);
        FormInput.add(HasilLab);

        jLabelTglHasilLab = new widget.Label();
        jLabelTglHasilLab.setText("Tgl Hasil Lab :");
        jLabelTglHasilLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglHasilLab.setBounds(430, 610, 145, 23);
        FormInput.add(jLabelTglHasilLab);

        TglHasilLab = new widget.Tanggal();
        TglHasilLab.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglHasilLab.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglHasilLab.setOpaque(false);
        TglHasilLab.setBounds(580, 610, 240, 23);
        FormInput.add(TglHasilLab);

        jLabelHasilRad = new widget.Label();
        jLabelHasilRad.setText("Hasil Radiologi :");
        jLabelHasilRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelHasilRad.setBounds(20, 640, 145, 23);
        FormInput.add(jLabelHasilRad);

        HasilRad = new widget.ComboBox();
        HasilRad.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        HasilRad.setBounds(170, 640, 240, 23);
        FormInput.add(HasilRad);

        jLabelTglHasilRad = new widget.Label();
        jLabelTglHasilRad.setText("Tgl Hasil Rad :");
        jLabelTglHasilRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglHasilRad.setBounds(430, 640, 145, 23);
        FormInput.add(jLabelTglHasilRad);

        TglHasilRad = new widget.Tanggal();
        TglHasilRad.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglHasilRad.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglHasilRad.setOpaque(false);
        TglHasilRad.setBounds(580, 640, 240, 23);
        FormInput.add(TglHasilRad);

        jLabelEkgEegUsg = new widget.Label();
        jLabelEkgEegUsg.setText("EKG / EEG / USG :");
        jLabelEkgEegUsg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEkgEegUsg.setBounds(20, 670, 145, 23);
        FormInput.add(jLabelEkgEegUsg);

        EkgEegUsg = new widget.ComboBox();
        EkgEegUsg.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EkgEegUsg.setBounds(170, 670, 240, 23);
        FormInput.add(EkgEegUsg);

        jLabelTglEkg = new widget.Label();
        jLabelTglEkg.setText("Tgl EKG :");
        jLabelTglEkg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglEkg.setBounds(430, 670, 145, 23);
        FormInput.add(jLabelTglEkg);

        TglEkg = new widget.Tanggal();
        TglEkg.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglEkg.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglEkg.setOpaque(false);
        TglEkg.setBounds(580, 670, 240, 23);
        FormInput.add(TglEkg);

        jLabelHasilPribadi = new widget.Label();
        jLabelHasilPribadi.setText("Pemeriksaan pribadi :");
        jLabelHasilPribadi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelHasilPribadi.setBounds(20, 700, 145, 23);
        FormInput.add(jLabelHasilPribadi);

        HasilPribadi = new widget.ComboBox();
        HasilPribadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        HasilPribadi.setBounds(170, 700, 240, 23);
        FormInput.add(HasilPribadi);

        jLabelTglHasilPribadi = new widget.Label();
        jLabelTglHasilPribadi.setText("Tgl Hasil Pribadi :");
        jLabelTglHasilPribadi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglHasilPribadi.setBounds(430, 700, 145, 23);
        FormInput.add(jLabelTglHasilPribadi);

        TglHasilPribadi = new widget.Tanggal();
        TglHasilPribadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglHasilPribadi.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglHasilPribadi.setOpaque(false);
        TglHasilPribadi.setBounds(580, 700, 240, 23);
        FormInput.add(TglHasilPribadi);

        jLabelLainHasil = new widget.Label();
        jLabelLainHasil.setText("Lain-lain Hasil :");
        jLabelLainHasil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLainHasil.setBounds(20, 730, 145, 23);
        FormInput.add(jLabelLainHasil);

        LainHasil = new widget.ComboBox();
        LainHasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        LainHasil.setBounds(170, 730, 240, 23);
        FormInput.add(LainHasil);

        jLabelTglLainHasil = new widget.Label();
        jLabelTglLainHasil.setText("Tgl Lain Hasil :");
        jLabelTglLainHasil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTglLainHasil.setBounds(430, 730, 145, 23);
        FormInput.add(jLabelTglLainHasil);

        TglLainHasil = new widget.Tanggal();
        TglLainHasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        TglLainHasil.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglLainHasil.setOpaque(false);
        TglLainHasil.setBounds(580, 730, 240, 23);
        FormInput.add(TglLainHasil);

        widget.Label header3 = new widget.Label();
        header3.setText("D. PERAWATAN / PERALATAN MEDIS YANG DILANJUTKAN DIRUMAH");
        header3.setFont(new java.awt.Font("Tahoma", 1, 11));
        header3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header3.setBounds(10, 770, 800, 23);
        FormInput.add(header3);

        javax.swing.JSeparator sep3 = new javax.swing.JSeparator();
        sep3.setBounds(10, 795, 830, 10);
        FormInput.add(sep3);

        jLabelOksigenPortable = new widget.Label();
        jLabelOksigenPortable.setText("Oksigen Portable :");
        jLabelOksigenPortable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelOksigenPortable.setBounds(20, 810, 145, 23);
        FormInput.add(jLabelOksigenPortable);

        OksigenPortable = new widget.ComboBox();
        OksigenPortable.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        OksigenPortable.setBounds(170, 810, 240, 23);
        FormInput.add(OksigenPortable);

        jLabelTracheostomi = new widget.Label();
        jLabelTracheostomi.setText("Tracheostomi :");
        jLabelTracheostomi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTracheostomi.setBounds(430, 810, 145, 23);
        FormInput.add(jLabelTracheostomi);

        Tracheostomi = new widget.ComboBox();
        Tracheostomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        Tracheostomi.setBounds(580, 810, 240, 23);
        FormInput.add(Tracheostomi);

        jLabelDowerKateter = new widget.Label();
        jLabelDowerKateter.setText("Dower kateter :");
        jLabelDowerKateter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDowerKateter.setBounds(20, 840, 145, 23);
        FormInput.add(jLabelDowerKateter);

        DowerKateter = new widget.ComboBox();
        DowerKateter.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        DowerKateter.setBounds(170, 840, 240, 23);
        FormInput.add(DowerKateter);

        jLabelTongkat = new widget.Label();
        jLabelTongkat.setText("Tongkat :");
        jLabelTongkat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelTongkat.setBounds(430, 840, 145, 23);
        FormInput.add(jLabelTongkat);

        Tongkat = new widget.ComboBox();
        Tongkat.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        Tongkat.setBounds(580, 840, 240, 23);
        FormInput.add(Tongkat);

        jLabelKursiRoda = new widget.Label();
        jLabelKursiRoda.setText("Kursi Roda :");
        jLabelKursiRoda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelKursiRoda.setBounds(20, 870, 145, 23);
        FormInput.add(jLabelKursiRoda);

        KursiRoda = new widget.ComboBox();
        KursiRoda.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        KursiRoda.setBounds(170, 870, 240, 23);
        FormInput.add(KursiRoda);

        jLabelLainAlatMedis = new widget.Label();
        jLabelLainAlatMedis.setText("Lain-lain Alat Medis :");
        jLabelLainAlatMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLainAlatMedis.setBounds(430, 870, 145, 23);
        FormInput.add(jLabelLainAlatMedis);

        LainAlatMedis = new widget.TextBox();
        LainAlatMedis.setBounds(580, 870, 240, 23);
        FormInput.add(LainAlatMedis);

        jLabelPerawatanLuka = new widget.Label();
        jLabelPerawatanLuka.setText("Perawatan Luka :");
        jLabelPerawatanLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPerawatanLuka.setBounds(20, 900, 145, 23);
        FormInput.add(jLabelPerawatanLuka);

        PerawatanLuka = new widget.ComboBox();
        PerawatanLuka.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        PerawatanLuka.setBounds(170, 900, 240, 23);
        FormInput.add(PerawatanLuka);

        jLabelPerawatanNgtDc = new widget.Label();
        jLabelPerawatanNgtDc.setText("Perawatan NGT/DC :");
        jLabelPerawatanNgtDc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPerawatanNgtDc.setBounds(430, 900, 145, 23);
        FormInput.add(jLabelPerawatanNgtDc);

        PerawatanNgtDc = new widget.ComboBox();
        PerawatanNgtDc.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        PerawatanNgtDc.setBounds(580, 900, 240, 23);
        FormInput.add(PerawatanNgtDc);

        jLabelPerawatanIbu = new widget.Label();
        jLabelPerawatanIbu.setText("Perawatan ibu sehat :");
        jLabelPerawatanIbu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPerawatanIbu.setBounds(20, 930, 145, 23);
        FormInput.add(jLabelPerawatanIbu);

        PerawatanIbu = new widget.ComboBox();
        PerawatanIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        PerawatanIbu.setBounds(170, 930, 240, 23);
        FormInput.add(PerawatanIbu);

        jLabelPerawatanBayi = new widget.Label();
        jLabelPerawatanBayi.setText("Perawatan bayi sehat :");
        jLabelPerawatanBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPerawatanBayi.setBounds(430, 930, 145, 23);
        FormInput.add(jLabelPerawatanBayi);

        PerawatanBayi = new widget.ComboBox();
        PerawatanBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        PerawatanBayi.setBounds(580, 930, 240, 23);
        FormInput.add(PerawatanBayi);

        jLabelFisioterapi = new widget.Label();
        jLabelFisioterapi.setText("Fisioterapi :");
        jLabelFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFisioterapi.setBounds(20, 960, 145, 23);
        FormInput.add(jLabelFisioterapi);

        Fisioterapi = new widget.ComboBox();
        Fisioterapi.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        Fisioterapi.setBounds(170, 960, 240, 23);
        FormInput.add(Fisioterapi);

        jLabelLainPerawatan = new widget.Label();
        jLabelLainPerawatan.setText("Lain-lain Perawatan :");
        jLabelLainPerawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLainPerawatan.setBounds(430, 960, 145, 23);
        FormInput.add(jLabelLainPerawatan);

        LainPerawatan = new widget.TextBox();
        LainPerawatan.setBounds(580, 960, 240, 23);
        FormInput.add(LainPerawatan);

        widget.Label header4 = new widget.Label();
        header4.setText("E. PENDIDIKAN KESEHATAN UNTUK DI RUMAH");
        header4.setFont(new java.awt.Font("Tahoma", 1, 11));
        header4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header4.setBounds(10, 1000, 800, 23);
        FormInput.add(header4);

        javax.swing.JSeparator sep4 = new javax.swing.JSeparator();
        sep4.setBounds(10, 1025, 830, 10);
        FormInput.add(sep4);

        jLabelEduBalutan = new widget.Label();
        jLabelEduBalutan.setText("Balutan jgn basah :");
        jLabelEduBalutan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduBalutan.setBounds(20, 1040, 145, 23);
        FormInput.add(jLabelEduBalutan);

        EduBalutan = new widget.ComboBox();
        EduBalutan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduBalutan.setBounds(170, 1040, 240, 23);
        FormInput.add(EduBalutan);

        jLabelEduKendaraan = new widget.Label();
        jLabelEduKendaraan.setText("Jgn mengendarai motor :");
        jLabelEduKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduKendaraan.setBounds(430, 1040, 145, 23);
        FormInput.add(jLabelEduKendaraan);

        EduKendaraan = new widget.ComboBox();
        EduKendaraan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduKendaraan.setBounds(580, 1040, 240, 23);
        FormInput.add(EduKendaraan);

        jLabelEduBeban = new widget.Label();
        jLabelEduBeban.setText("Hindari beban berat :");
        jLabelEduBeban.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduBeban.setBounds(20, 1070, 145, 23);
        FormInput.add(jLabelEduBeban);

        EduBeban = new widget.ComboBox();
        EduBeban.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduBeban.setBounds(170, 1070, 240, 23);
        FormInput.add(EduBeban);

        jLabelEduLab = new widget.Label();
        jLabelEduLab.setText("Cek lab sblm kontrol :");
        jLabelEduLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduLab.setBounds(430, 1070, 145, 23);
        FormInput.add(jLabelEduLab);

        EduLab = new widget.ComboBox();
        EduLab.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduLab.setBounds(580, 1070, 240, 23);
        FormInput.add(EduLab);

        jLabelEduTangga = new widget.Label();
        jLabelEduTangga.setText("Jgn naik tangga >2x/hr :");
        jLabelEduTangga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduTangga.setBounds(20, 1100, 145, 23);
        FormInput.add(jLabelEduTangga);

        EduTangga = new widget.ComboBox();
        EduTangga.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduTangga.setBounds(170, 1100, 240, 23);
        FormInput.add(EduTangga);

        jLabelEduPekerjaan = new widget.Label();
        jLabelEduPekerjaan.setText("Batasi pekerjaan :");
        jLabelEduPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduPekerjaan.setBounds(430, 1100, 145, 23);
        FormInput.add(jLabelEduPekerjaan);

        EduPekerjaan = new widget.ComboBox();
        EduPekerjaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduPekerjaan.setBounds(580, 1100, 240, 23);
        FormInput.add(EduPekerjaan);

        jLabelEduAktifitas = new widget.Label();
        jLabelEduAktifitas.setText("Aktifitas bertahap :");
        jLabelEduAktifitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduAktifitas.setBounds(20, 1130, 145, 23);
        FormInput.add(jLabelEduAktifitas);

        EduAktifitas = new widget.ComboBox();
        EduAktifitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduAktifitas.setBounds(170, 1130, 240, 23);
        FormInput.add(EduAktifitas);

        jLabelEduNyeri = new widget.Label();
        jLabelEduNyeri.setText("Jika keluhan nyeri :");
        jLabelEduNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduNyeri.setBounds(430, 1130, 145, 23);
        FormInput.add(jLabelEduNyeri);

        EduNyeri = new widget.ComboBox();
        EduNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Ya", "Tidak"}));
        EduNyeri.setBounds(580, 1130, 240, 23);
        FormInput.add(EduNyeri);

        jLabelEduLainLain = new widget.Label();
        jLabelEduLainLain.setText("Edukasi Lain-lain :");
        jLabelEduLainLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEduLainLain.setBounds(20, 1160, 145, 23);
        FormInput.add(jLabelEduLainLain);

        EduLainLain = new widget.TextBox();
        EduLainLain.setBounds(170, 1160, 240, 23);
        FormInput.add(EduLainLain);

        widget.Label header5 = new widget.Label();
        header5.setText("F. JADWAL KONTROL BERIKUTNYA");
        header5.setFont(new java.awt.Font("Tahoma", 1, 11));
        header5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header5.setBounds(10, 1200, 800, 23);
        FormInput.add(header5);

        javax.swing.JSeparator sep5 = new javax.swing.JSeparator();
        sep5.setBounds(10, 1225, 830, 10);
        FormInput.add(sep5);

        jLabelDokterKontrol = new widget.Label();
        jLabelDokterKontrol.setText("Nama Dokter Kontrol :");
        jLabelDokterKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDokterKontrol.setBounds(20, 1240, 145, 23);
        FormInput.add(jLabelDokterKontrol);

        DokterKontrol = new widget.TextBox();
        DokterKontrol.setBounds(170, 1240, 240, 23);
        FormInput.add(DokterKontrol);

        jLabelJadwalKontrol = new widget.Label();
        jLabelJadwalKontrol.setText("Jadwal Kontrol :");
        jLabelJadwalKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelJadwalKontrol.setBounds(430, 1240, 145, 23);
        FormInput.add(jLabelJadwalKontrol);

        JadwalKontrol = new widget.TextBox();
        JadwalKontrol.setBounds(580, 1240, 240, 23);
        FormInput.add(JadwalKontrol);

        jLabelFaskes1 = new widget.Label();
        jLabelFaskes1.setText("Faskes terdekat 1 :");
        jLabelFaskes1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFaskes1.setBounds(20, 1270, 145, 23);
        FormInput.add(jLabelFaskes1);

        Faskes1 = new widget.TextBox();
        Faskes1.setBounds(170, 1270, 240, 23);
        FormInput.add(Faskes1);

        jLabelFaskes2 = new widget.Label();
        jLabelFaskes2.setText("Faskes terdekat 2 :");
        jLabelFaskes2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFaskes2.setBounds(430, 1270, 145, 23);
        FormInput.add(jLabelFaskes2);

        Faskes2 = new widget.TextBox();
        Faskes2.setBounds(580, 1270, 240, 23);
        FormInput.add(Faskes2);

        jLabelFaskes3 = new widget.Label();
        jLabelFaskes3.setText("Faskes terdekat 3 :");
        jLabelFaskes3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelFaskes3.setBounds(20, 1300, 145, 23);
        FormInput.add(jLabelFaskes3);

        Faskes3 = new widget.TextBox();
        Faskes3.setBounds(170, 1300, 240, 23);
        FormInput.add(Faskes3);


        widget.Label jLabelPetugas = new widget.Label();
        jLabelPetugas.setText("Petugas :");
        jLabelPetugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelPetugas.setBounds(10, 1340, 145, 23);
        FormInput.add(jLabelPetugas);

        KdPetugas.setBounds(80, 1340, 100, 23);
        FormInput.add(KdPetugas);
        
        NmPetugas.setEditable(false);
        NmPetugas.setBounds(185, 1340, 300, 23);
        FormInput.add(NmPetugas);

        scrollInput.setViewportView(FormInput);
        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);
        TabRawat.addTab("Input Data", internalFrame2);
        
        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(tabMode.getRowCount()!=0){
                    try {
                        getData();
                    } catch (java.lang.NullPointerException e) {
                    }
                }
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(tabMode.getRowCount()!=0){
                    if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                        try {
                            getData();
                        } catch (java.lang.NullPointerException e) {
                        }
                    }
                }
            }
        });
        Scroll.setViewportView(tbObat);
        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl :");
        jLabel19.setPreferredSize(new java.awt.Dimension(41, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-06-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-06-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setText("Tampilkan Data");
        BtnCari.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampil();
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Discharge Planning", internalFrame3);
        
        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setText("Simpan");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setText("Ganti");
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setText("Hapus");
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnHapus);
        
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setText("Semua");
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCari.setText("");
                tampil();
            }
        });
        panelGlass8.add(BtnAll);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setText("Batal");
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emptTeks();
            }
        });
        panelGlass8.add(BtnBatal);
        
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setText("Keluar");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        pack();
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, norwt);
        Sequel.cariIsi("select nm_pasien from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=?", TPasien, norwt);
        Sequel.cariIsi("select jk from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=?", Jk, norwt);
        Sequel.cariIsi("select tgl_lahir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=?", TglLahir, norwt);
        
        String tglReg = Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?", norwt);
        if (!tglReg.equals("")) {
            Valid.SetTgl(DTPCari1, tglReg);
        }
        
        emptTeks();
        tampil();
    }

    public void emptTeks() {
        KdDokter.setText("");
        NmDokter.setText("");
        TglMasuk.setDate(new java.util.Date());
        TglPengkajian.setDate(new java.util.Date());
        TglKeluar.setDate(new java.util.Date());
        DiagnosaMasuk.setText("");
        DiagnosaKeluar.setText("");
        IndikasiPulang.setText("");
        DietKhusus.setText("");
        KanulIV.setSelectedIndex(0);
        TglKanulIV.setDate(new java.util.Date());
        AlasanKanulIV.setText("");
        Ngt.setSelectedIndex(0);
        TglNgt.setDate(new java.util.Date());
        AlasanNgt.setText("");
        Balutan.setSelectedIndex(0);
        TglBalutan.setDate(new java.util.Date());
        AlasanBalutan.setText("");
        Drain.setSelectedIndex(0);
        TglDrain.setDate(new java.util.Date());
        AlasanDrain.setText("");
        KateterUrine.setSelectedIndex(0);
        TglKateterUrine.setDate(new java.util.Date());
        AlasanKateterUrine.setText("");
        GelangIdentitas.setSelectedIndex(0);
        KetGelangIdentitas.setText("");
        LainDilepas.setText("");
        TransportasiPulang.setSelectedIndex(0);
        ObatSisa.setSelectedIndex(0);
        ResepTambahan.setSelectedIndex(0);
        SuratKontrol.setSelectedIndex(0);
        SuratSakit.setSelectedIndex(0);
        ResumeMedis.setSelectedIndex(0);
        HasilLab.setSelectedIndex(0);
        TglHasilLab.setDate(new java.util.Date());
        HasilRad.setSelectedIndex(0);
        TglHasilRad.setDate(new java.util.Date());
        EkgEegUsg.setSelectedIndex(0);
        TglEkg.setDate(new java.util.Date());
        HasilPribadi.setSelectedIndex(0);
        TglHasilPribadi.setDate(new java.util.Date());
        LainHasil.setSelectedIndex(0);
        TglLainHasil.setDate(new java.util.Date());
        OksigenPortable.setSelectedIndex(0);
        Tracheostomi.setSelectedIndex(0);
        DowerKateter.setSelectedIndex(0);
        Tongkat.setSelectedIndex(0);
        KursiRoda.setSelectedIndex(0);
        LainAlatMedis.setText("");
        PerawatanLuka.setSelectedIndex(0);
        PerawatanNgtDc.setSelectedIndex(0);
        PerawatanIbu.setSelectedIndex(0);
        PerawatanBayi.setSelectedIndex(0);
        Fisioterapi.setSelectedIndex(0);
        LainPerawatan.setText("");
        EduBalutan.setSelectedIndex(0);
        EduKendaraan.setSelectedIndex(0);
        EduBeban.setSelectedIndex(0);
        EduLab.setSelectedIndex(0);
        EduTangga.setSelectedIndex(0);
        EduPekerjaan.setSelectedIndex(0);
        EduAktifitas.setSelectedIndex(0);
        EduNyeri.setSelectedIndex(0);
        EduLainLain.setText("");
        DokterKontrol.setText("");
        JadwalKontrol.setText("");
        Faskes1.setText("");
        Faskes2.setText("");
        Faskes3.setText("");

        KdPetugas.setText(akses.getkode());
        Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas, akses.getkode());
        
        // Auto-populate from kamar_inap again if TNoRw is filled
        if (!TNoRw.getText().trim().equals("")) {
            String tglMasukRanap = Sequel.cariIsi("select concat(tgl_masuk, ' ', jam_masuk) from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1", TNoRw.getText());
            if (!tglMasukRanap.equals("")) {
                Valid.SetTgl2(TglMasuk, tglMasukRanap);
            }
            Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1", DiagnosaMasuk, TNoRw.getText());
            Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", KdDokter, TNoRw.getText());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        }
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getperencanaan_pemulangan());
        BtnHapus.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas, KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select rm_discharge_planning.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, concat(reg_periksa.umurdaftar, ' ', reg_periksa.sttsumur), pasien.jk, pasien.tgl_lahir, rm_discharge_planning.kd_dokter, rm_discharge_planning.tanggal_masuk, rm_discharge_planning.tanggal_pengkajian, rm_discharge_planning.tanggal_keluar, rm_discharge_planning.diagnosa_masuk, rm_discharge_planning.diagnosa_keluar, rm_discharge_planning.indikasi_pulang, rm_discharge_planning.diet_khusus, rm_discharge_planning.kanul_iv, rm_discharge_planning.tgl_kanul_iv, rm_discharge_planning.alasan_kanul_iv, rm_discharge_planning.ngt, rm_discharge_planning.tgl_ngt, rm_discharge_planning.alasan_ngt, rm_discharge_planning.balutan, rm_discharge_planning.tgl_balutan, rm_discharge_planning.alasan_balutan, rm_discharge_planning.drain, rm_discharge_planning.tgl_drain, rm_discharge_planning.alasan_drain, rm_discharge_planning.kateter_urine, rm_discharge_planning.tgl_kateter_urine, rm_discharge_planning.alasan_kateter_urine, rm_discharge_planning.gelang_identitas, rm_discharge_planning.ket_gelang_identitas, rm_discharge_planning.lain_lain_dilepas, rm_discharge_planning.transportasi_pulang, rm_discharge_planning.obat_sisa_rawat, rm_discharge_planning.resep_tambahan, rm_discharge_planning.surat_kontrol, rm_discharge_planning.surat_sakit, rm_discharge_planning.resume_medis, rm_discharge_planning.hasil_lab, rm_discharge_planning.tgl_hasil_lab, rm_discharge_planning.hasil_rad, rm_discharge_planning.tgl_hasil_rad, rm_discharge_planning.ekg_eeg_usg, rm_discharge_planning.tgl_ekg, rm_discharge_planning.hasil_pemeriksaan_pribadi, rm_discharge_planning.tgl_pemeriksaan_pribadi, rm_discharge_planning.lain_lain_hasil, rm_discharge_planning.tgl_lain_hasil, rm_discharge_planning.oksigen_portable, rm_discharge_planning.tracheostomi, rm_discharge_planning.dower_kateter, rm_discharge_planning.tongkat, rm_discharge_planning.kursi_roda, rm_discharge_planning.lain_lain_alat_medis, rm_discharge_planning.perawatan_luka, rm_discharge_planning.perawatan_ngt, rm_discharge_planning.perawatan_ibu, rm_discharge_planning.perawatan_bayi, rm_discharge_planning.fisioterapi, rm_discharge_planning.lain_lain_perawatan, rm_discharge_planning.edu_balutan, rm_discharge_planning.edu_kendaraan, rm_discharge_planning.edu_beban, rm_discharge_planning.edu_lab, rm_discharge_planning.edu_tangga, rm_discharge_planning.edu_pekerjaan, rm_discharge_planning.edu_aktifitas, rm_discharge_planning.edu_nyeri, rm_discharge_planning.edu_lain_lain, rm_discharge_planning.nama_dokter_kontrol, rm_discharge_planning.tgl_jam_tempat_kontrol, rm_discharge_planning.faskes_terdekat_1, rm_discharge_planning.faskes_terdekat_2, rm_discharge_planning.faskes_terdekat_3, rm_discharge_planning.nip, petugas.nama "+
                "from rm_discharge_planning inner join reg_periksa on rm_discharge_planning.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join petugas on rm_discharge_planning.nip=petugas.nip "+
                "where rm_discharge_planning.tanggal_masuk between ? and ? "+
                (TCari.getText().trim().equals("")?"":" and (rm_discharge_planning.no_rawat like ? or pasien.nm_pasien like ? or rm_discharge_planning.diagnosa_masuk like ?) ")+
                "order by rm_discharge_planning.tanggal_masuk desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    String[] data = new String[76];
                    for(int i=0; i<76; i++){
                        data[i] = rs.getString(i+1);
                    }
                    tabMode.addRow(data);
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            int row = tbObat.getSelectedRow();
            TNoRw.setText(tbObat.getValueAt(row, 0).toString());
            TNoRM.setText(tbObat.getValueAt(row, 1).toString());
            TPasien.setText(tbObat.getValueAt(row, 2).toString());
            TglLahir.setText(tbObat.getValueAt(row, 5).toString());
            Jk.setText(tbObat.getValueAt(row, 4).toString());
            
        KdDokter.setText(tbObat.getValueAt(row, 6).toString());
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        Valid.SetTgl2(TglMasuk, tbObat.getValueAt(row, 7).toString());
        Valid.SetTgl2(TglPengkajian, tbObat.getValueAt(row, 8).toString());
        Valid.SetTgl2(TglKeluar, tbObat.getValueAt(row, 9).toString());
        DiagnosaMasuk.setText(tbObat.getValueAt(row, 10).toString());
        DiagnosaKeluar.setText(tbObat.getValueAt(row, 11).toString());
        IndikasiPulang.setText(tbObat.getValueAt(row, 12).toString());
        DietKhusus.setText(tbObat.getValueAt(row, 13).toString());
        KanulIV.setSelectedItem(tbObat.getValueAt(row, 14).toString());
        Valid.SetTgl2(TglKanulIV, tbObat.getValueAt(row, 15).toString());
        AlasanKanulIV.setText(tbObat.getValueAt(row, 16).toString());
        Ngt.setSelectedItem(tbObat.getValueAt(row, 17).toString());
        Valid.SetTgl2(TglNgt, tbObat.getValueAt(row, 18).toString());
        AlasanNgt.setText(tbObat.getValueAt(row, 19).toString());
        Balutan.setSelectedItem(tbObat.getValueAt(row, 20).toString());
        Valid.SetTgl2(TglBalutan, tbObat.getValueAt(row, 21).toString());
        AlasanBalutan.setText(tbObat.getValueAt(row, 22).toString());
        Drain.setSelectedItem(tbObat.getValueAt(row, 23).toString());
        Valid.SetTgl2(TglDrain, tbObat.getValueAt(row, 24).toString());
        AlasanDrain.setText(tbObat.getValueAt(row, 25).toString());
        KateterUrine.setSelectedItem(tbObat.getValueAt(row, 26).toString());
        Valid.SetTgl2(TglKateterUrine, tbObat.getValueAt(row, 27).toString());
        AlasanKateterUrine.setText(tbObat.getValueAt(row, 28).toString());
        GelangIdentitas.setSelectedItem(tbObat.getValueAt(row, 29).toString());
        KetGelangIdentitas.setText(tbObat.getValueAt(row, 30).toString());
        LainDilepas.setText(tbObat.getValueAt(row, 31).toString());
        TransportasiPulang.setSelectedItem(tbObat.getValueAt(row, 32).toString());
        ObatSisa.setSelectedItem(tbObat.getValueAt(row, 33).toString());
        ResepTambahan.setSelectedItem(tbObat.getValueAt(row, 34).toString());
        SuratKontrol.setSelectedItem(tbObat.getValueAt(row, 35).toString());
        SuratSakit.setSelectedItem(tbObat.getValueAt(row, 36).toString());
        ResumeMedis.setSelectedItem(tbObat.getValueAt(row, 37).toString());
        HasilLab.setSelectedItem(tbObat.getValueAt(row, 38).toString());
        Valid.SetTgl2(TglHasilLab, tbObat.getValueAt(row, 39).toString());
        HasilRad.setSelectedItem(tbObat.getValueAt(row, 40).toString());
        Valid.SetTgl2(TglHasilRad, tbObat.getValueAt(row, 41).toString());
        EkgEegUsg.setSelectedItem(tbObat.getValueAt(row, 42).toString());
        Valid.SetTgl2(TglEkg, tbObat.getValueAt(row, 43).toString());
        HasilPribadi.setSelectedItem(tbObat.getValueAt(row, 44).toString());
        Valid.SetTgl2(TglHasilPribadi, tbObat.getValueAt(row, 45).toString());
        LainHasil.setSelectedItem(tbObat.getValueAt(row, 46).toString());
        Valid.SetTgl2(TglLainHasil, tbObat.getValueAt(row, 47).toString());
        OksigenPortable.setSelectedItem(tbObat.getValueAt(row, 48).toString());
        Tracheostomi.setSelectedItem(tbObat.getValueAt(row, 49).toString());
        DowerKateter.setSelectedItem(tbObat.getValueAt(row, 50).toString());
        Tongkat.setSelectedItem(tbObat.getValueAt(row, 51).toString());
        KursiRoda.setSelectedItem(tbObat.getValueAt(row, 52).toString());
        LainAlatMedis.setText(tbObat.getValueAt(row, 53).toString());
        PerawatanLuka.setSelectedItem(tbObat.getValueAt(row, 54).toString());
        PerawatanNgtDc.setSelectedItem(tbObat.getValueAt(row, 55).toString());
        PerawatanIbu.setSelectedItem(tbObat.getValueAt(row, 56).toString());
        PerawatanBayi.setSelectedItem(tbObat.getValueAt(row, 57).toString());
        Fisioterapi.setSelectedItem(tbObat.getValueAt(row, 58).toString());
        LainPerawatan.setText(tbObat.getValueAt(row, 59).toString());
        EduBalutan.setSelectedItem(tbObat.getValueAt(row, 60).toString());
        EduKendaraan.setSelectedItem(tbObat.getValueAt(row, 61).toString());
        EduBeban.setSelectedItem(tbObat.getValueAt(row, 62).toString());
        EduLab.setSelectedItem(tbObat.getValueAt(row, 63).toString());
        EduTangga.setSelectedItem(tbObat.getValueAt(row, 64).toString());
        EduPekerjaan.setSelectedItem(tbObat.getValueAt(row, 65).toString());
        EduAktifitas.setSelectedItem(tbObat.getValueAt(row, 66).toString());
        EduNyeri.setSelectedItem(tbObat.getValueAt(row, 67).toString());
        EduLainLain.setText(tbObat.getValueAt(row, 68).toString());
        DokterKontrol.setText(tbObat.getValueAt(row, 69).toString());
        JadwalKontrol.setText(tbObat.getValueAt(row, 70).toString());
        Faskes1.setText(tbObat.getValueAt(row, 71).toString());
        Faskes2.setText(tbObat.getValueAt(row, 72).toString());
        Faskes3.setText(tbObat.getValueAt(row, 73).toString());

            KdPetugas.setText(tbObat.getValueAt(row, 74).toString());
            NmPetugas.setText(tbObat.getValueAt(row, 75).toString());
        }
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (KdDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "DPJP");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from rm_discharge_planning where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Data sudah ada, silahkan gunakan tombol Edit!");
            } else {
                try {
                    ps = koneksi.prepareStatement("insert into rm_discharge_planning(no_rawat,`kd_dokter`,`tanggal_masuk`,`tanggal_pengkajian`,`tanggal_keluar`,`diagnosa_masuk`,`diagnosa_keluar`,`indikasi_pulang`,`diet_khusus`,`kanul_iv`,`tgl_kanul_iv`,`alasan_kanul_iv`,`ngt`,`tgl_ngt`,`alasan_ngt`,`balutan`,`tgl_balutan`,`alasan_balutan`,`drain`,`tgl_drain`,`alasan_drain`,`kateter_urine`,`tgl_kateter_urine`,`alasan_kateter_urine`,`gelang_identitas`,`ket_gelang_identitas`,`lain_lain_dilepas`,`transportasi_pulang`,`obat_sisa_rawat`,`resep_tambahan`,`surat_kontrol`,`surat_sakit`,`resume_medis`,`hasil_lab`,`tgl_hasil_lab`,`hasil_rad`,`tgl_hasil_rad`,`ekg_eeg_usg`,`tgl_ekg`,`hasil_pemeriksaan_pribadi`,`tgl_pemeriksaan_pribadi`,`lain_lain_hasil`,`tgl_lain_hasil`,`oksigen_portable`,`tracheostomi`,`dower_kateter`,`tongkat`,`kursi_roda`,`lain_lain_alat_medis`,`perawatan_luka`,`perawatan_ngt`,`perawatan_ibu`,`perawatan_bayi`,`fisioterapi`,`lain_lain_perawatan`,`edu_balutan`,`edu_kendaraan`,`edu_beban`,`edu_lab`,`edu_tangga`,`edu_pekerjaan`,`edu_aktifitas`,`edu_nyeri`,`edu_lain_lain`,`nama_dokter_kontrol`,`tgl_jam_tempat_kontrol`,`faskes_terdekat_1`,`faskes_terdekat_2`,`faskes_terdekat_3`,`nip`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    try {
                        ps.setString(1, TNoRw.getText());
                ps.setString(2, KdDokter.getText());
                ps.setString(3, TglMasuk.getSelectedItem().toString());
                ps.setString(4, TglPengkajian.getSelectedItem().toString());
                ps.setString(5, TglKeluar.getSelectedItem().toString());
                ps.setString(6, DiagnosaMasuk.getText());
                ps.setString(7, DiagnosaKeluar.getText());
                ps.setString(8, IndikasiPulang.getText());
                ps.setString(9, DietKhusus.getText());
                ps.setString(10, KanulIV.getSelectedItem().toString());
                ps.setString(11, TglKanulIV.getSelectedItem().toString());
                ps.setString(12, AlasanKanulIV.getText());
                ps.setString(13, Ngt.getSelectedItem().toString());
                ps.setString(14, TglNgt.getSelectedItem().toString());
                ps.setString(15, AlasanNgt.getText());
                ps.setString(16, Balutan.getSelectedItem().toString());
                ps.setString(17, TglBalutan.getSelectedItem().toString());
                ps.setString(18, AlasanBalutan.getText());
                ps.setString(19, Drain.getSelectedItem().toString());
                ps.setString(20, TglDrain.getSelectedItem().toString());
                ps.setString(21, AlasanDrain.getText());
                ps.setString(22, KateterUrine.getSelectedItem().toString());
                ps.setString(23, TglKateterUrine.getSelectedItem().toString());
                ps.setString(24, AlasanKateterUrine.getText());
                ps.setString(25, GelangIdentitas.getSelectedItem().toString());
                ps.setString(26, KetGelangIdentitas.getText());
                ps.setString(27, LainDilepas.getText());
                ps.setString(28, TransportasiPulang.getSelectedItem().toString());
                ps.setString(29, ObatSisa.getSelectedItem().toString());
                ps.setString(30, ResepTambahan.getSelectedItem().toString());
                ps.setString(31, SuratKontrol.getSelectedItem().toString());
                ps.setString(32, SuratSakit.getSelectedItem().toString());
                ps.setString(33, ResumeMedis.getSelectedItem().toString());
                ps.setString(34, HasilLab.getSelectedItem().toString());
                ps.setString(35, TglHasilLab.getSelectedItem().toString());
                ps.setString(36, HasilRad.getSelectedItem().toString());
                ps.setString(37, TglHasilRad.getSelectedItem().toString());
                ps.setString(38, EkgEegUsg.getSelectedItem().toString());
                ps.setString(39, TglEkg.getSelectedItem().toString());
                ps.setString(40, HasilPribadi.getSelectedItem().toString());
                ps.setString(41, TglHasilPribadi.getSelectedItem().toString());
                ps.setString(42, LainHasil.getSelectedItem().toString());
                ps.setString(43, TglLainHasil.getSelectedItem().toString());
                ps.setString(44, OksigenPortable.getSelectedItem().toString());
                ps.setString(45, Tracheostomi.getSelectedItem().toString());
                ps.setString(46, DowerKateter.getSelectedItem().toString());
                ps.setString(47, Tongkat.getSelectedItem().toString());
                ps.setString(48, KursiRoda.getSelectedItem().toString());
                ps.setString(49, LainAlatMedis.getText());
                ps.setString(50, PerawatanLuka.getSelectedItem().toString());
                ps.setString(51, PerawatanNgtDc.getSelectedItem().toString());
                ps.setString(52, PerawatanIbu.getSelectedItem().toString());
                ps.setString(53, PerawatanBayi.getSelectedItem().toString());
                ps.setString(54, Fisioterapi.getSelectedItem().toString());
                ps.setString(55, LainPerawatan.getText());
                ps.setString(56, EduBalutan.getSelectedItem().toString());
                ps.setString(57, EduKendaraan.getSelectedItem().toString());
                ps.setString(58, EduBeban.getSelectedItem().toString());
                ps.setString(59, EduLab.getSelectedItem().toString());
                ps.setString(60, EduTangga.getSelectedItem().toString());
                ps.setString(61, EduPekerjaan.getSelectedItem().toString());
                ps.setString(62, EduAktifitas.getSelectedItem().toString());
                ps.setString(63, EduNyeri.getSelectedItem().toString());
                ps.setString(64, EduLainLain.getText());
                ps.setString(65, DokterKontrol.getText());
                ps.setString(66, JadwalKontrol.getText());
                ps.setString(67, Faskes1.getText());
                ps.setString(68, Faskes2.getText());
                ps.setString(69, Faskes3.getText());
                ps.setString(70, KdPetugas.getText());

                        ps.executeUpdate();
                        tampil();
                        emptTeks();
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (ps != null) ps.close();
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
            }
        }
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (KdDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "DPJP");
        } else {
            try {
                ps = koneksi.prepareStatement("update rm_discharge_planning set `kd_dokter`=?,`tanggal_masuk`=?,`tanggal_pengkajian`=?,`tanggal_keluar`=?,`diagnosa_masuk`=?,`diagnosa_keluar`=?,`indikasi_pulang`=?,`diet_khusus`=?,`kanul_iv`=?,`tgl_kanul_iv`=?,`alasan_kanul_iv`=?,`ngt`=?,`tgl_ngt`=?,`alasan_ngt`=?,`balutan`=?,`tgl_balutan`=?,`alasan_balutan`=?,`drain`=?,`tgl_drain`=?,`alasan_drain`=?,`kateter_urine`=?,`tgl_kateter_urine`=?,`alasan_kateter_urine`=?,`gelang_identitas`=?,`ket_gelang_identitas`=?,`lain_lain_dilepas`=?,`transportasi_pulang`=?,`obat_sisa_rawat`=?,`resep_tambahan`=?,`surat_kontrol`=?,`surat_sakit`=?,`resume_medis`=?,`hasil_lab`=?,`tgl_hasil_lab`=?,`hasil_rad`=?,`tgl_hasil_rad`=?,`ekg_eeg_usg`=?,`tgl_ekg`=?,`hasil_pemeriksaan_pribadi`=?,`tgl_pemeriksaan_pribadi`=?,`lain_lain_hasil`=?,`tgl_lain_hasil`=?,`oksigen_portable`=?,`tracheostomi`=?,`dower_kateter`=?,`tongkat`=?,`kursi_roda`=?,`lain_lain_alat_medis`=?,`perawatan_luka`=?,`perawatan_ngt`=?,`perawatan_ibu`=?,`perawatan_bayi`=?,`fisioterapi`=?,`lain_lain_perawatan`=?,`edu_balutan`=?,`edu_kendaraan`=?,`edu_beban`=?,`edu_lab`=?,`edu_tangga`=?,`edu_pekerjaan`=?,`edu_aktifitas`=?,`edu_nyeri`=?,`edu_lain_lain`=?,`nama_dokter_kontrol`=?,`tgl_jam_tempat_kontrol`=?,`faskes_terdekat_1`=?,`faskes_terdekat_2`=?,`faskes_terdekat_3`=?,`nip`=? where no_rawat=?");
                try {
                ps.setString(1, KdDokter.getText());
                ps.setString(2, TglMasuk.getSelectedItem().toString());
                ps.setString(3, TglPengkajian.getSelectedItem().toString());
                ps.setString(4, TglKeluar.getSelectedItem().toString());
                ps.setString(5, DiagnosaMasuk.getText());
                ps.setString(6, DiagnosaKeluar.getText());
                ps.setString(7, IndikasiPulang.getText());
                ps.setString(8, DietKhusus.getText());
                ps.setString(9, KanulIV.getSelectedItem().toString());
                ps.setString(10, TglKanulIV.getSelectedItem().toString());
                ps.setString(11, AlasanKanulIV.getText());
                ps.setString(12, Ngt.getSelectedItem().toString());
                ps.setString(13, TglNgt.getSelectedItem().toString());
                ps.setString(14, AlasanNgt.getText());
                ps.setString(15, Balutan.getSelectedItem().toString());
                ps.setString(16, TglBalutan.getSelectedItem().toString());
                ps.setString(17, AlasanBalutan.getText());
                ps.setString(18, Drain.getSelectedItem().toString());
                ps.setString(19, TglDrain.getSelectedItem().toString());
                ps.setString(20, AlasanDrain.getText());
                ps.setString(21, KateterUrine.getSelectedItem().toString());
                ps.setString(22, TglKateterUrine.getSelectedItem().toString());
                ps.setString(23, AlasanKateterUrine.getText());
                ps.setString(24, GelangIdentitas.getSelectedItem().toString());
                ps.setString(25, KetGelangIdentitas.getText());
                ps.setString(26, LainDilepas.getText());
                ps.setString(27, TransportasiPulang.getSelectedItem().toString());
                ps.setString(28, ObatSisa.getSelectedItem().toString());
                ps.setString(29, ResepTambahan.getSelectedItem().toString());
                ps.setString(30, SuratKontrol.getSelectedItem().toString());
                ps.setString(31, SuratSakit.getSelectedItem().toString());
                ps.setString(32, ResumeMedis.getSelectedItem().toString());
                ps.setString(33, HasilLab.getSelectedItem().toString());
                ps.setString(34, TglHasilLab.getSelectedItem().toString());
                ps.setString(35, HasilRad.getSelectedItem().toString());
                ps.setString(36, TglHasilRad.getSelectedItem().toString());
                ps.setString(37, EkgEegUsg.getSelectedItem().toString());
                ps.setString(38, TglEkg.getSelectedItem().toString());
                ps.setString(39, HasilPribadi.getSelectedItem().toString());
                ps.setString(40, TglHasilPribadi.getSelectedItem().toString());
                ps.setString(41, LainHasil.getSelectedItem().toString());
                ps.setString(42, TglLainHasil.getSelectedItem().toString());
                ps.setString(43, OksigenPortable.getSelectedItem().toString());
                ps.setString(44, Tracheostomi.getSelectedItem().toString());
                ps.setString(45, DowerKateter.getSelectedItem().toString());
                ps.setString(46, Tongkat.getSelectedItem().toString());
                ps.setString(47, KursiRoda.getSelectedItem().toString());
                ps.setString(48, LainAlatMedis.getText());
                ps.setString(49, PerawatanLuka.getSelectedItem().toString());
                ps.setString(50, PerawatanNgtDc.getSelectedItem().toString());
                ps.setString(51, PerawatanIbu.getSelectedItem().toString());
                ps.setString(52, PerawatanBayi.getSelectedItem().toString());
                ps.setString(53, Fisioterapi.getSelectedItem().toString());
                ps.setString(54, LainPerawatan.getText());
                ps.setString(55, EduBalutan.getSelectedItem().toString());
                ps.setString(56, EduKendaraan.getSelectedItem().toString());
                ps.setString(57, EduBeban.getSelectedItem().toString());
                ps.setString(58, EduLab.getSelectedItem().toString());
                ps.setString(59, EduTangga.getSelectedItem().toString());
                ps.setString(60, EduPekerjaan.getSelectedItem().toString());
                ps.setString(61, EduAktifitas.getSelectedItem().toString());
                ps.setString(62, EduNyeri.getSelectedItem().toString());
                ps.setString(63, EduLainLain.getText());
                ps.setString(64, DokterKontrol.getText());
                ps.setString(65, JadwalKontrol.getText());
                ps.setString(66, Faskes1.getText());
                ps.setString(67, Faskes2.getText());
                ps.setString(68, Faskes3.getText());
                ps.setString(69, KdPetugas.getText());

                    ps.setString(70, TNoRw.getText());
                    ps.executeUpdate();
                    tampil();
                    emptTeks();
                    JOptionPane.showMessageDialog(null, "Data berhasil diubah");
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (ps != null) ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }
    private void MnCetakActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReport("rptRMDischargePlanning.jasper", "report", "::[ Form Perencanaan Pemulangan ]::",
                    "select rm_discharge_planning.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, " +
                    "concat(reg_periksa.umurdaftar, ' ', reg_periksa.sttsumur) as umur, pasien.jk, pasien.tgl_lahir, " +
                    "rm_discharge_planning.kd_dokter, dokter.nm_dokter, rm_discharge_planning.tanggal_masuk, " +
                    "rm_discharge_planning.tanggal_pengkajian, rm_discharge_planning.tanggal_keluar, rm_discharge_planning.diagnosa_masuk, " +
                    "rm_discharge_planning.diagnosa_keluar, rm_discharge_planning.indikasi_pulang, rm_discharge_planning.diet_khusus, " +
                    "rm_discharge_planning.kanul_iv, rm_discharge_planning.tgl_kanul_iv, rm_discharge_planning.alasan_kanul_iv, " +
                    "rm_discharge_planning.ngt, rm_discharge_planning.tgl_ngt, rm_discharge_planning.alasan_ngt, " +
                    "rm_discharge_planning.balutan, rm_discharge_planning.tgl_balutan, rm_discharge_planning.alasan_balutan, " +
                    "rm_discharge_planning.drain, rm_discharge_planning.tgl_drain, rm_discharge_planning.alasan_drain, " +
                    "rm_discharge_planning.kateter_urine, rm_discharge_planning.tgl_kateter_urine, rm_discharge_planning.alasan_kateter_urine, " +
                    "rm_discharge_planning.gelang_identitas, rm_discharge_planning.ket_gelang_identitas, rm_discharge_planning.lain_lain_dilepas, " +
                    "rm_discharge_planning.transportasi_pulang, rm_discharge_planning.obat_sisa_rawat, rm_discharge_planning.resep_tambahan, " +
                    "rm_discharge_planning.surat_kontrol, rm_discharge_planning.surat_sakit, rm_discharge_planning.resume_medis, " +
                    "rm_discharge_planning.hasil_lab, rm_discharge_planning.tgl_hasil_lab, rm_discharge_planning.hasil_rad, " +
                    "rm_discharge_planning.tgl_hasil_rad, rm_discharge_planning.ekg_eeg_usg, rm_discharge_planning.tgl_ekg, " +
                    "rm_discharge_planning.hasil_pemeriksaan_pribadi, rm_discharge_planning.tgl_pemeriksaan_pribadi, " +
                    "rm_discharge_planning.lain_lain_hasil, rm_discharge_planning.tgl_lain_hasil, rm_discharge_planning.oksigen_portable, " +
                    "rm_discharge_planning.tracheostomi, rm_discharge_planning.dower_kateter, rm_discharge_planning.tongkat, " +
                    "rm_discharge_planning.kursi_roda, rm_discharge_planning.lain_lain_alat_medis, rm_discharge_planning.perawatan_luka, " +
                    "rm_discharge_planning.perawatan_ngt, rm_discharge_planning.perawatan_ibu, rm_discharge_planning.perawatan_bayi, " +
                    "rm_discharge_planning.fisioterapi, rm_discharge_planning.lain_lain_perawatan, rm_discharge_planning.edu_balutan, " +
                    "rm_discharge_planning.edu_kendaraan, rm_discharge_planning.edu_beban, rm_discharge_planning.edu_lab, " +
                    "rm_discharge_planning.edu_tangga, rm_discharge_planning.edu_pekerjaan, rm_discharge_planning.edu_aktifitas, " +
                    "rm_discharge_planning.edu_nyeri, rm_discharge_planning.edu_lain_lain, rm_discharge_planning.nama_dokter_kontrol, " +
                    "rm_discharge_planning.tgl_jam_tempat_kontrol, rm_discharge_planning.faskes_terdekat_1, rm_discharge_planning.faskes_terdekat_2, " +
                    "rm_discharge_planning.faskes_terdekat_3, rm_discharge_planning.nip, petugas.nama " +
                    "from rm_discharge_planning inner join reg_periksa on rm_discharge_planning.no_rawat=reg_periksa.no_rawat " +
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "inner join petugas on rm_discharge_planning.nip=petugas.nip " +
                    "left join dokter on rm_discharge_planning.kd_dokter=dokter.kd_dokter " +
                    "where rm_discharge_planning.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data no rawat yang mau dicetak...!!!!");
            }
        }
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            Sequel.meghapus("rm_discharge_planning", "no_rawat", TNoRw.getText());
            tampil();
            emptTeks();
        }
    }

    


    public void setTTVFromPonek() {
        // No fields in this class for TTV
    }

}
