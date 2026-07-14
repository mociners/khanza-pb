import os

java_code = """package rekammedis;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
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

public class RMPenilaianAwalMedisRanapAnak1 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
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

    // Fields
    private TextBox TNoRw, TPasien, TNoRM, KdDokter, NmDokter, TglLahir, Jk;
    private Button BtnDokter;
    private Tanggal TglAsuhan;
    private ComboBox JamAsuhan;

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
            "No.Rawat", "No RM", "Nama Pasien", "Tanggal", "Dokter", "Keluhan Utama", "Keadaan Umum"
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosing(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }

    private void initComponents() {
        internalFrame1 = new InternalFrame();
        internalFrame1.setBorder(BorderFactory.createTitledBorder("::[ Assesmen Medis Awal Rawat Inap Anak ]::"));
        internalFrame1.setLayout(new BorderLayout(1, 1));
        setContentPane(internalFrame1);

        panelGlass8 = new panelisi();
        panelGlass8.setPreferredSize(new Dimension(44, 54));
        panelGlass8.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));
        
        BtnSimpan = new Button(); BtnSimpan.setText("Simpan");
        BtnBatal = new Button(); BtnBatal.setText("Baru");
        BtnHapus = new Button(); BtnHapus.setText("Hapus");
        BtnEdit = new Button(); BtnEdit.setText("Ganti");
        BtnPrint = new Button(); BtnPrint.setText("Cetak");
        BtnAll = new Button(); BtnAll.setText("Semua");
        BtnKeluar = new Button(); BtnKeluar.setText("Keluar");
        
        panelGlass8.add(BtnSimpan); panelGlass8.add(BtnBatal); panelGlass8.add(BtnHapus);
        panelGlass8.add(BtnEdit); panelGlass8.add(BtnPrint); panelGlass8.add(BtnAll); panelGlass8.add(BtnKeluar);
        internalFrame1.add(panelGlass8, BorderLayout.PAGE_END);

        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new InternalFrame();
        internalFrame2.setLayout(new BorderLayout(1, 1));
        
        scrollInput = new ScrollPane();
        FormInput = new PanelBiasa();
        FormInput.setLayout(null);
        FormInput.setPreferredSize(new Dimension(870, 1100));
        scrollInput.setViewportView(FormInput);
        internalFrame2.add(scrollInput, BorderLayout.CENTER);
        TabRawat.addTab("Input Penilaian", internalFrame2);
        internalFrame1.add(TabRawat, BorderLayout.CENTER);

        int y = 10;
        addLabel("No.Rawat :", 10, y, 70, 23);
        TNoRw = new TextBox(); TNoRw.setBounds(85, y, 130, 23); FormInput.add(TNoRw);
        TNoRM = new TextBox(); TNoRM.setBounds(220, y, 100, 23); FormInput.add(TNoRM);
        TPasien = new TextBox(); TPasien.setBounds(325, y, 260, 23); FormInput.add(TPasien);
        
        addLabel("Tgl.Lahir :", 595, y, 60, 23);
        TglLahir = new TextBox(); TglLahir.setBounds(660, y, 90, 23); FormInput.add(TglLahir);
        addLabel("J.K. :", 760, y, 30, 23);
        Jk = new TextBox(); Jk.setBounds(795, y, 60, 23); FormInput.add(Jk);
        
        y += 30;
        addLabel("Tanggal :", 10, y, 70, 23);
        TglAsuhan = new Tanggal(); TglAsuhan.setBounds(85, y, 90, 23); FormInput.add(TglAsuhan);
        
        addLabel("DPJP :", 185, y, 50, 23);
        KdDokter = new TextBox(); KdDokter.setBounds(240, y, 90, 23); FormInput.add(KdDokter);
        NmDokter = new TextBox(); NmDokter.setBounds(335, y, 180, 23); FormInput.add(NmDokter);
        BtnDokter = new Button(); BtnDokter.setText("..."); BtnDokter.setBounds(520, y, 28, 23); FormInput.add(BtnDokter);
        
        y += 40;
        addSeparator("A. ANAMNESA", y);
        y += 20;
        addLabel("Keluhan Utama :", 20, y, 100, 23);
        KeluhanUtama = addTextArea(125, y, 730, 50);
        
        y += 60;
        addSeparator("B. PEMERIKSAAN FISIK", y);
        y += 20;
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
        y += 20;
        Laboratorium = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("D. DIAGNOSA BANDING", y);
        y += 20;
        DiagnosaBanding = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("E. DIAGNOSA KERJA", y);
        y += 20;
        DiagnosaKerja = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("F. PENATALAKSANAAN", y);
        y += 20;
        Penatalaksanaan = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("G. USUL PEMERIKSAAN", y);
        y += 20;
        UsulPemeriksaan = addTextArea(20, y, 835, 50);

        y += 60;
        addSeparator("H. PROGNOSA", y);
        y += 20;
        Prognosa = addTextArea(20, y, 835, 50);

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
        
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Fungsi simpan belum diimplementasikan ke database baru.");
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
        FormInput.add(lbl);
        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setBounds(10, y+20, 845, 5);
        FormInput.add(sep);
    }
    
    private TextArea addTextArea(int x, int y, int width, int height) {
        TextArea ta = new TextArea();
        ScrollPane sp = new ScrollPane();
        sp.setViewportView(ta);
        sp.setBounds(x, y, width, height);
        FormInput.add(sp);
        return ta;
    }
    
    public void setNoRm(String norwt, Date tgl) {
        TNoRw.setText(norwt);
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
    
    public void isCek() {
        BtnSimpan.setEnabled(akses.getpenilaian_medis_ranap_anak());
        BtnHapus.setEnabled(akses.getpenilaian_medis_ranap_anak());
        BtnEdit.setEnabled(akses.getpenilaian_medis_ranap_anak());
        BtnPrint.setEnabled(akses.getpenilaian_medis_ranap_anak());
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
    }
}
"""

with open('/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalMedisRanapAnak1.java', 'w') as f:
    f.write(java_code)

