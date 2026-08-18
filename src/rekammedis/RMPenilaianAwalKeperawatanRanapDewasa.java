package rekammedis;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import widget.Button;
import widget.CekBox;
import widget.ComboBox;
import widget.InternalFrame;
import widget.Label;
import widget.PanelBiasa;
import widget.RadioButton;
import widget.ScrollPane;
import widget.Tanggal;
import widget.TextArea;
import widget.TextBox;
import widget.panelisi;


import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

import widget.CekBox;
import widget.RadioButton;
import widget.TextArea;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import widget.Button;
import widget.ComboBox;
import widget.Label;
import widget.ScrollPane;
import widget.TextBox;
import widget.Tanggal;
import widget.panelisi;
import widget.InternalFrame;

public class RMPenilaianAwalKeperawatanRanapDewasa extends JDialog {
    private DefaultTableModel tabMode;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnCetak;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    
    private InternalFrame internalFrame1;
    private panelisi panelGlass8;
    private JTabbedPane TabRawat;
    private ScrollPane scrollInput;
    private ScrollPane scrollData;
    private JPanel panelInput;
    private JTable tbData;
    private widget.InternalFrame internalFrame3;
    private widget.panelisi panelGlass9;
    private Tanggal DTPCari1, DTPCari2;
    private TextBox TCari;
    private Button BtnCari, BtnAll;
    private Label LCount, jLabel19, jLabel21, jLabel6, jLabel7;

    
    // Top Section
    private TextBox TNoRw, TNoRM, TNmPasien, TglLahir, JK;
    private TextBox NIP, NmPetugas;
    private Button btnPetugas;
    private Tanggal TglTiba, TglAsesmen;
    private ComboBox JamTiba, MenitTiba, DetikTiba;
    private ComboBox JamAsesmen, MenitAsesmen, DetikAsesmen;
    private widget.CekBox ChkAsesmen, ChkTiba;
    private ComboBox InfoDari;
    private TextBox KdDokter, NmDokter;
    private Button btnDokter;
    
    
    // Pemeriksaan Fisik
    private TextBox TDiagnosaMasuk, TTD, TSuhu, TNadi, TRR;
    private TextArea TRiwayatKeluarga, TRiwayatPasien, TDeskripsiPenyakit, TRiwayatSekarang;
    private CekBox ChkIGD, ChkPersetujuan, ChkPerintah;
    private ComboBox CmbAlergi;
    private TextBox TJenisAlergi;

    // Psikologis
    private TextBox TStatusNikah, TPendidikan, TAgama, TKetPsiko, TKetTinggal, TKetTempatTinggal;
    private ComboBox CmbWargaNegara, CmbPekerjaan, CmbAktivitas, CmbTinggalBersama, CmbTempatTinggal, CmbCuriga, CmbBudaya;
    private ComboBox CmbAnak;
    private TextBox TJumlahAnak;
    private CekBox ChkCurigaYa, ChkCurigaTidak;
    private CekBox ChkBudayaYa, ChkBudayaTidak;
    private ComboBox CmbBimbinganIbadah;
    private CekBox ChkMasalahPsiko1, ChkMasalahPsiko2, ChkMasalahPsiko3, ChkMasalahPsiko4;
    private ComboBox CmbPsiko;

    // Neurosensoris
    private RadioButton RdoStatusCM, RdoStatusBingung, RdoStatusMengantuk, RdoStatusApatis, RdoStatusTidakRespon;
    private RadioButton RdoOriOrang, RdoOriTempat, RdoOriWaktu, RdoOriSituasi;
    private TextBox TOriOrang, TOriTempat, TOriWaktu, TOriSituasi;
    private ComboBox CmbMemori;
    private TextBox TPupilKananUk, TPupilKananReflex, TPupilKiriUk, TPupilKiriReflex;
    private TextBox TGCSEye, TGCSMotorik, TGCSVerbal, TGCSJumlah;
    private RadioButton RdoKakuKuduk, RdoBrudzinski, RdoKernig, RdoNeuroLain;
    private CekBox ChkMasalahNeuro1, ChkMasalahNeuro2;

    // Pernafasan
    private RadioButton RdoAirway1, RdoAirway2, RdoAirway3, RdoAirway4;
    private TextBox TBendaAsing, TAirwayUkuran;
    private RadioButton RdoBreath1, RdoBreath2, RdoBreath3, RdoBreath4, RdoBreath5;
    private RadioButton RdoBunyi1, RdoBunyi2, RdoBunyi3, RdoBunyi4, RdoBunyi5;
    private RadioButton RdoSulitNafas1, RdoSulitNafas2, RdoSulitNafas3;
    private RadioButton RdoAlatNafas1, RdoAlatNafas2;
    private RadioButton RdoOksigen1, RdoOksigen2;
    private TextBox TOksigenLtr;
    private ComboBox CmbOksigenJenis;
    private TextBox TFrekuensiNafas;
    private RadioButton RdoBatuk1, RdoBatuk2, RdoBatuk3, RdoBatuk4;
    private TextBox TSpO2;
    private CekBox ChkMasalahNafas1, ChkMasalahNafas2, ChkMasalahNafas3, ChkMasalahNafas4;

    // Sirkulasi
    private TextBox TTekananDarah;
    private RadioButton RdoSirkulasi1, RdoSirkulasi2, RdoSirkulasi3, RdoSirkulasi4, RdoSirkulasi5;
    private RadioButton RdoCRT1, RdoCRT2;
    private TextBox TDenyutNadi;
    private RadioButton RdoNadi1, RdoNadi2, RdoNadi3;
    private TextBox TNadiJelas;
    private RadioButton RdoIrama1, RdoIrama2;
    private RadioButton RdoPacemaker1, RdoPacemaker2;
    private TextBox TPacemakerJelas;
    private RadioButton RdoAkral1, RdoAkral2;
    private CekBox ChkMasalahSirk1, ChkMasalahSirk2, ChkMasalahSirk3;
    private TextBox TMasalahSirkJelas1, TMasalahSirkJelas2, TMasalahSirkJelas3;

    // Perkemihan
    private RadioButton RdoBAK1, RdoBAK2, RdoBAK3, RdoBAK4, RdoBAK5, RdoBAK6, RdoBAK7;
    private TextBox TBAKLainnya;
    private RadioButton RdoKateter1, RdoKateter2;
    private TextBox TKateterJelas;
    private TextBox TUrinJumlah;
    private RadioButton RdoUrin1, RdoUrin2, RdoUrin3;
    private RadioButton RdoProstat1, RdoProstat2;
    private RadioButton RdoNyeriPinggang1, RdoNyeriPinggang2;
    private RadioButton RdoKelainan1, RdoKelainan2;
    private TextBox TKelainanSebut;
    private CekBox ChkMasalahKemih1;
    private TextBox TMasalahKemihJelas1;

    // Seksual/Reproduksi
    // Seksual/Reproduksi
    private TextBox TStatusObG, TStatusObP, TStatusObA;
    private RadioButton RdoMens1, RdoMens2, RdoMens3, RdoMens4;
    private RadioButton RdoPregnan1, RdoPregnan2;
    private TextBox TPregnanHPHT, TPregnanHPL;
    private TextBox TPostPartum;
    private TextBox TLochea, TLocheaJumlah, TPayudara, TPengeluaranASI, TKontraksi;
    private RadioButton RdoPapsmear1, RdoPapsmear2;
    private TextBox TPapsmearTgl;
    private RadioButton RdoMammo1, RdoMammo2;
    private TextBox TMammoTgl;
    private RadioButton RdoSadari1, RdoSadari2;
    private RadioButton RdoSkrining1, RdoSkrining2;
    private TextBox TSkriningTgl;
    private CekBox ChkMasalahRep1, ChkMasalahRep2, ChkMasalahRep3, ChkMasalahRep4;
    private TextBox TMasalahRepLain;

    // Integumen
    private RadioButton RdoInteg1, RdoInteg2, RdoInteg3, RdoInteg4, RdoInteg5;
    private RadioButton RdoKekerasan1, RdoKekerasan2, RdoKekerasan3;
    private RadioButton RdoTurgor1, RdoTurgor2, RdoTurgor3;
    private RadioButton RdoRambut1, RdoRambut2;
    private RadioButton RdoKuku1, RdoKuku2;
    private RadioButton RdoLuka1, RdoLuka2;
    private TextBox TLukaDalam;
    private RadioButton RdoPerdarahan1, RdoPerdarahan2;
    private RadioButton RdoFraktur1, RdoFraktur2, RdoFraktur3, RdoFraktur4;
    private RadioButton RdoLokasi1, RdoLokasi2, RdoLokasi3, RdoLokasi4;
    private TextBox TLokasiLain;
    private CekBox ChkMasalahInteg1, ChkMasalahInteg2, ChkMasalahInteg3, ChkMasalahInteg4;
    private TextBox TMasalahIntegJelas1, TMasalahIntegJelas2, TMasalahIntegJelas3, TMasalahIntegJelas4;

    // THT Mata
    private RadioButton RdoTelinga1, RdoTelinga2, RdoTelinga3;
    private TextBox TTelingaLainnya;
    private RadioButton RdoHidung1, RdoHidung2, RdoHidung3, RdoHidung4, RdoHidung5;
    private RadioButton RdoTenggorokan1, RdoTenggorokan2, RdoTenggorokan3;
    private RadioButton RdoGigi1, RdoGigi2, RdoGigi3, RdoGigi4, RdoGigi5, RdoGigi6;
    private RadioButton RdoSakitGigi1, RdoSakitGigi2;
    private RadioButton RdoGigiPalsu1, RdoGigiPalsu2;
    private RadioButton RdoMata1, RdoMata2, RdoMata3, RdoMata4, RdoMata5;
    private RadioButton RdoMata6, RdoMata7, RdoMata8, RdoMata9, RdoMata10;
    private TextBox TMataLainnya;
    private CekBox ChkMasalahTHTMata1;

    // Pencernaan
    private RadioButton RdoWasir1, RdoWasir2;
    private RadioButton RdoPerdarahanRectal1, RdoPerdarahanRectal2;
    private TextBox TJenisDiit;
    private TextBox TFeedingTube;
    private RadioButton RdoPembatasanCairan1, RdoPembatasanCairan2;
    private RadioButton RdoAbdomen1, RdoAbdomen2, RdoAbdomen3;
    private RadioButton RdoBunyiUsus1, RdoBunyiUsus2, RdoBunyiUsus3;
    private TextBox TBunyiUsusFreq;
    private RadioButton RdoBAB1, RdoBAB2, RdoBAB3;
    private TextBox TBABDiareSejak, TBABFreq;
    private RadioButton RdoKonsistensi1, RdoKonsistensi2, RdoKonsistensi3, RdoKonsistensi4;
    private TextBox TWarnaCerna;
    private RadioButton RdoPencahar1, RdoPencahar2;
    private CekBox ChkMasalahPencernaan1;
    // Nyeri
    private RadioButton RdoNyeriTidakAda, RdoNyeriAda, RdoNyeriAkut, RdoNyeriKronis, RdoNyeriViseral, RdoNyeriSomatis;
    private RadioButton RdoSkor0, RdoSkor1, RdoSkor2, RdoSkor3, RdoSkor4, RdoSkor5, RdoSkor6, RdoSkor7, RdoSkor8, RdoSkor9, RdoSkor10;
    private RadioButton RdoWb0, RdoWb2, RdoWb4, RdoWb6, RdoWb8, RdoWb10;
    private RadioButton RdoProvokesDiam, RdoProvokesMobilisasi, RdoProvokesDitekan, RdoProvokesTiduran, RdoProvokesBerdiri, RdoProvokesBerjalan;
    private TextBox TProvokesLainnya;
    private RadioButton RdoQualityTajam, RdoQualityTumpul, RdoQualityDitusuk, RdoQualityDitarik, RdoQualityDipukul, RdoQualityBerdenyut, RdoQualityDibakar, RdoQualityDitikam, RdoQualityDisayat;
    private TextBox TQualityLainnya;
    private ComboBox CmbRadiation;
    private TextBox TRadiationLokasi;
    private ComboBox CmbSeverityMetode;
    private TextBox TSeveritySkor, TSeverityNyeri;
    private ComboBox CmbTimeSetiap, CmbTimeSelama;
    private TextBox TTimeSejak;
    private ComboBox CmbCpotEkspresi, CmbCpotGerakan, CmbCpotKetegangan, CmbCpotVentilator, CmbCpotVokalisasi;
    private TextBox TCpotTotal, TCpotKategori;



    // Bottom Buttons
    private Button BtnSimpan, BtnBatal, BtnHapus, BtnEdit, BtnPrint, BtnKeluar;

    public RMPenilaianAwalKeperawatanRanapDewasa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        jam();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                ChkIGD = new widget.CekBox();
        ChkPersetujuan = new widget.CekBox();
        ChkPerintah = new widget.CekBox();
        ChkCurigaYa = new widget.CekBox();
        ChkCurigaTidak = new widget.CekBox();
        ChkBudayaYa = new widget.CekBox();
        ChkBudayaTidak = new widget.CekBox();
        ChkMasalahPsiko1 = new widget.CekBox();
        ChkMasalahPsiko2 = new widget.CekBox();
        ChkMasalahPsiko3 = new widget.CekBox();
        ChkMasalahPsiko4 = new widget.CekBox();
        ChkMasalahNeuro1 = new widget.CekBox();
        ChkMasalahNeuro2 = new widget.CekBox();
        ChkMasalahNafas1 = new widget.CekBox();
        ChkMasalahNafas2 = new widget.CekBox();
        ChkMasalahNafas3 = new widget.CekBox();
        ChkMasalahNafas4 = new widget.CekBox();
        ChkMasalahSirk1 = new widget.CekBox();
        ChkMasalahSirk2 = new widget.CekBox();
        ChkMasalahSirk3 = new widget.CekBox();
        ChkMasalahKemih1 = new widget.CekBox();
        ChkMasalahRep1 = new widget.CekBox();
        ChkMasalahRep2 = new widget.CekBox();
        ChkMasalahRep3 = new widget.CekBox();
        ChkMasalahRep4 = new widget.CekBox();
        ChkMasalahInteg1 = new widget.CekBox();
        ChkMasalahInteg2 = new widget.CekBox();
        ChkMasalahInteg3 = new widget.CekBox();
        ChkMasalahInteg4 = new widget.CekBox();
        ChkMasalahTHTMata1 = new widget.CekBox();
        ChkMasalahPencernaan1 = new widget.CekBox();
        ChkIGD = new widget.CekBox();
        ChkPersetujuan = new widget.CekBox();
        ChkPerintah = new widget.CekBox();
        ChkCurigaYa = new widget.CekBox();
        ChkCurigaTidak = new widget.CekBox();
        ChkBudayaYa = new widget.CekBox();
        ChkBudayaTidak = new widget.CekBox();
        ChkMasalahPsiko1 = new widget.CekBox();
        ChkMasalahPsiko2 = new widget.CekBox();
        ChkMasalahPsiko3 = new widget.CekBox();
        ChkMasalahPsiko4 = new widget.CekBox();
        ChkMasalahNeuro1 = new widget.CekBox();
        ChkMasalahNeuro2 = new widget.CekBox();
        ChkMasalahNafas1 = new widget.CekBox();
        ChkMasalahNafas2 = new widget.CekBox();
        ChkMasalahNafas3 = new widget.CekBox();
        ChkMasalahNafas4 = new widget.CekBox();
        ChkMasalahSirk1 = new widget.CekBox();
        ChkMasalahSirk2 = new widget.CekBox();
        ChkMasalahSirk3 = new widget.CekBox();
        ChkMasalahKemih1 = new widget.CekBox();
        ChkMasalahRep1 = new widget.CekBox();
        ChkMasalahRep2 = new widget.CekBox();
        ChkMasalahRep3 = new widget.CekBox();
        ChkMasalahRep4 = new widget.CekBox();
        ChkMasalahInteg1 = new widget.CekBox();
        ChkMasalahInteg2 = new widget.CekBox();
        ChkMasalahInteg3 = new widget.CekBox();
        ChkMasalahInteg4 = new widget.CekBox();
        ChkMasalahTHTMata1 = new widget.CekBox();
        ChkMasalahPencernaan1 = new widget.CekBox();
        setSize(800, 600);
    }
    private void initComponents() {
        String[] tempatTinggalItems;
        String[] tinggalBersamaItems;
        String[] aktivitasItems;
        String[] pekerjaanItems;
        String[] psikoItems;
        int i;
        int i2;
        this.internalFrame1 = new InternalFrame();
        this.internalFrame1.setLayout(new BorderLayout());
        this.internalFrame1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Inap Dewasa ]::", 0, 0, new Font("Tahoma", 0, 11), new Color(50, 50, 50)));
        this.TabRawat = new JTabbedPane();
        this.TabRawat.setForeground(new Color(50, 50, 50));
        this.TabRawat.setFont(new Font("Tahoma", 0, 11));
        this.panelInput = new JPanel();
        this.panelInput.setLayout(new BorderLayout());
        this.panelInput.setBackground(new Color(255, 255, 255));
        ScrollPane scrollInput = new ScrollPane();
        this.panelInput.add((Component)scrollInput, "Center");
        PanelBiasa FormInput = new PanelBiasa();
        FormInput.setLayout(new BoxLayout(FormInput, 1));
        FormInput.setBackground(new Color(255, 255, 255));
        scrollInput.setViewportView(FormInput);
        panelisi panelTop = new panelisi();
        panelTop.setLayout(new BoxLayout(panelTop, 1));
        JPanel pRow1 = new JPanel(new FlowLayout(0, 2, 0));
        pRow1.setOpaque(false);
        this.TNoRw = new TextBox();
        this.TNoRw.setPreferredSize(new Dimension(130, 23));
        this.TNoRw.setEditable(false);
        this.TNoRM = new TextBox();
        this.TNoRM.setPreferredSize(new Dimension(80, 23));
        this.TNoRM.setEditable(false);
        this.TNmPasien = new TextBox();
        this.TNmPasien.setEditable(false);
        this.TNmPasien.setPreferredSize(new Dimension(280, 23));
        this.TglLahir = new TextBox();
        this.TglLahir.setEditable(false);
        this.TglLahir.setPreferredSize(new Dimension(100, 23));
        this.JK = new TextBox();
        this.JK.setEditable(false);
        this.JK.setPreferredSize(new Dimension(80, 23));
        pRow1.add((Component)((Object)this.TNoRw));
        pRow1.add((Component)((Object)this.TNoRM));
        pRow1.add((Component)((Object)this.TNmPasien));
        pRow1.add((Component)((Object)this.createLabel(" Tgl.Lahir : ")));
        pRow1.add((Component)((Object)this.TglLahir));
        pRow1.add((Component)((Object)this.createLabel(" J.K. : ")));
        pRow1.add((Component)((Object)this.JK));
        panelTop.add(this.wrapRow("No.Rawat :", pRow1));
        JPanel pRow2 = new JPanel(new FlowLayout(0, 2, 0));
        pRow2.setOpaque(false);
        this.NIP = new TextBox();
        this.NIP.setEditable(false);
        this.NIP.setPreferredSize(new Dimension(100, 23));
        this.NmPetugas = new TextBox();
        this.NmPetugas.setEditable(false);
        this.NmPetugas.setPreferredSize(new Dimension(180, 23));
        this.btnPetugas = new Button();
        this.btnPetugas.setIcon(new ImageIcon(this.getClass().getResource("/picture/190.png")));
        this.btnPetugas.setPreferredSize(new Dimension(28, 23));
        this.TglAsesmen = new Tanggal();
        this.TglAsesmen.setPreferredSize(new Dimension(90, 23));
        this.TglAsesmen.setDisplayFormat("dd-MM-yyyy");
        this.JamAsesmen = new ComboBox();
        this.MenitAsesmen = new ComboBox();
        this.DetikAsesmen = new ComboBox();
        this.ChkAsesmen = new CekBox();
        this.ChkAsesmen.setBorder(null);
        this.ChkAsesmen.setSelected(true);
        this.ChkAsesmen.setPreferredSize(new Dimension(23, 23));
        for (i2 = 0; i2 < 24; ++i2) {
            this.JamAsesmen.addItem(String.format("%02d", i2));
        }
        for (i2 = 0; i2 < 60; ++i2) {
            this.MenitAsesmen.addItem(String.format("%02d", i2));
            this.DetikAsesmen.addItem(String.format("%02d", i2));
        }
        this.JamAsesmen.setPreferredSize(new Dimension(45, 23));
        this.MenitAsesmen.setPreferredSize(new Dimension(45, 23));
        this.DetikAsesmen.setPreferredSize(new Dimension(45, 23));
        this.InfoDari = new ComboBox();
        this.InfoDari.addItem("Autoanamnesis");
        this.InfoDari.addItem("Alloanamnesis");
        this.InfoDari.setPreferredSize(new Dimension(120, 23));
        pRow2.add((Component)((Object)this.NIP));
        pRow2.add((Component)((Object)this.NmPetugas));
        pRow2.add((Component)((Object)this.btnPetugas));
        pRow2.add((Component)((Object)this.createLabel(" Tgl.Asesmen : ")));
        pRow2.add((Component)((Object)this.TglAsesmen));
        pRow2.add((Component)((Object)this.createLabel(" Jam : ")));
        pRow2.add(this.JamAsesmen);
        pRow2.add(this.MenitAsesmen);
        pRow2.add(this.DetikAsesmen);
        pRow2.add(this.ChkAsesmen);
        pRow2.add((Component)((Object)this.createLabel(" Info Dari : ")));
        pRow2.add(this.InfoDari);
        panelTop.add(this.wrapRow("Petugas :", pRow2));
        JPanel pRow3 = new JPanel(new FlowLayout(0, 2, 0));
        pRow3.setOpaque(false);
        this.KdDokter = new TextBox();
        this.KdDokter.setPreferredSize(new Dimension(100, 23));
        this.NmDokter = new TextBox();
        this.NmDokter.setPreferredSize(new Dimension(180, 23));
        this.btnDokter = new Button();
        this.btnDokter.setIcon(new ImageIcon(this.getClass().getResource("/picture/190.png")));
        this.btnDokter.setPreferredSize(new Dimension(28, 23));
        this.TglTiba = new Tanggal();
        this.TglTiba.setPreferredSize(new Dimension(90, 23));
        this.TglTiba.setDisplayFormat("dd-MM-yyyy");
        this.JamTiba = new ComboBox();
        this.MenitTiba = new ComboBox();
        this.DetikTiba = new ComboBox();
        this.ChkTiba = new CekBox();
        this.ChkTiba.setBorder(null);
        this.ChkTiba.setSelected(true);
        this.ChkTiba.setPreferredSize(new Dimension(23, 23));
        for (i = 0; i < 24; ++i) {
            this.JamTiba.addItem(String.format("%02d", i));
        }
        for (i = 0; i < 60; ++i) {
            this.MenitTiba.addItem(String.format("%02d", i));
            this.DetikTiba.addItem(String.format("%02d", i));
        }
        this.JamTiba.setPreferredSize(new Dimension(45, 23));
        this.MenitTiba.setPreferredSize(new Dimension(45, 23));
        this.DetikTiba.setPreferredSize(new Dimension(45, 23));
        pRow3.add((Component)((Object)this.KdDokter));
        pRow3.add((Component)((Object)this.NmDokter));
        pRow3.add((Component)((Object)this.btnDokter));
        pRow3.add((Component)((Object)this.createLabel(" Tgl.Tiba Ruangan : ")));
        pRow3.add((Component)((Object)this.TglTiba));
        pRow3.add((Component)((Object)this.createLabel(" Jam : ")));
        pRow3.add(this.JamTiba);
        pRow3.add(this.MenitTiba);
        pRow3.add(this.DetikTiba);
        pRow3.add(this.ChkTiba);
        panelTop.add(this.wrapRow("DPJP :", pRow3));
        FormInput.add(panelTop);
        panelisi pFisik = new panelisi();
        pFisik.setLayout(new BoxLayout(pFisik, 1));
        pFisik.setBorder(new TitledBorder("PEMERIKSAAN FISIK"));
        this.TDiagnosaMasuk = new TextBox();
        this.TDiagnosaMasuk.setPreferredSize(new Dimension(500, 23));
        pFisik.add(this.wrapRow("Diagnosa Masuk :", (Component)((Object)this.TDiagnosaMasuk)));
        JPanel pVital = new JPanel(new FlowLayout(0, 2, 0));
        pVital.setOpaque(false);
        this.TTD = new TextBox();
        this.TTD.setPreferredSize(new Dimension(50, 23));
        this.TSuhu = new TextBox();
        this.TSuhu.setPreferredSize(new Dimension(50, 23));
        this.TNadi = new TextBox();
        this.TNadi.setPreferredSize(new Dimension(50, 23));
        this.TRR = new TextBox();
        this.TRR.setPreferredSize(new Dimension(50, 23));
        pVital.add((Component)((Object)this.createLabel("TD:")));
        pVital.add((Component)((Object)this.TTD));
        pVital.add((Component)((Object)this.createLabel("mmHg")));
        pVital.add((Component)((Object)this.createLabel(" S:")));
        pVital.add((Component)((Object)this.TSuhu));
        pVital.add((Component)((Object)this.createLabel("C")));
        pVital.add((Component)((Object)this.createLabel(" N:")));
        pVital.add((Component)((Object)this.TNadi));
        pVital.add((Component)((Object)this.createLabel("x/mnt")));
        pVital.add((Component)((Object)this.createLabel(" RR:")));
        pVital.add((Component)((Object)this.TRR));
        pVital.add((Component)((Object)this.createLabel("x/mnt")));
        pFisik.add(this.wrapRow("Vital Sign :", pVital));
        this.TRiwayatKeluarga = new TextArea();
        pFisik.add(this.wrapRow("Riwayat Peny. Keluarga :", this.createScrollPane(this.TRiwayatKeluarga)));
        this.TRiwayatPasien = new TextArea();
        pFisik.add(this.wrapRow("Riwayat Pasien :", this.createScrollPane(this.TRiwayatPasien)));
        this.TDeskripsiPenyakit = new TextArea();
        pFisik.add(this.wrapRow("Deksripsi Penyakit & Operasi :", this.createScrollPane(this.TDeskripsiPenyakit)));
        this.TRiwayatSekarang = new TextArea();
        pFisik.add(this.wrapRow("Riwayat Peny. Sekarang :", this.createScrollPane(this.TRiwayatSekarang)));
        JPanel pAlergi = new JPanel(new FlowLayout(0, 2, 0));
        pAlergi.setOpaque(false);
        this.CmbAlergi = new ComboBox();
        this.CmbAlergi.addItem("Tidak ada");
        this.CmbAlergi.addItem("Ada");
        this.CmbAlergi.setPreferredSize(new Dimension(100, 23));
        this.TJenisAlergi = new TextBox();
        this.TJenisAlergi.setPreferredSize(new Dimension(150, 23));
        pAlergi.add(this.CmbAlergi);
        pAlergi.add((Component)((Object)this.createLabel(" Jenis: ")));
        pAlergi.add((Component)((Object)this.TJenisAlergi));
        pFisik.add(this.wrapRow("Alergi :", pAlergi));
        JPanel pSyarat = new JPanel(new FlowLayout(0, 2, 0));
        pSyarat.setOpaque(false);
        this.ChkIGD = this.createCekBox("Asesmen awal IGD");
        this.ChkPersetujuan = this.createCekBox("Srt persetujuan ranap");
        this.ChkPerintah = this.createCekBox("Srt perintah ranap");
        pSyarat.add(this.ChkIGD);
        pSyarat.add(this.ChkPersetujuan);
        pSyarat.add(this.ChkPerintah);
        pFisik.add(this.wrapRow("Persyaratan Ranap :", pSyarat));
        FormInput.add(pFisik);
        panelisi pPsiko = new panelisi();
        pPsiko.setLayout(new BoxLayout(pPsiko, 1));
        pPsiko.setBorder(new TitledBorder("DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL, KULTURAL"));
        JPanel pPsikoItem = new JPanel(new FlowLayout(0, 2, 0));
        pPsikoItem.setOpaque(false);
        this.CmbPsiko = new ComboBox();
        for (String item : psikoItems = new String[]{"Sedih", "Tenang", "Cemas", "Marah/Tegang", "Depresi", "Ketakutan", "Agresif", "Lainnya"}) {
            this.CmbPsiko.addItem(item);
        }
        this.CmbPsiko.setPreferredSize(new Dimension(150, 23));
        this.TKetPsiko = new TextBox();
        this.TKetPsiko.setPreferredSize(new Dimension(150, 23));
        pPsikoItem.add(this.CmbPsiko);
        pPsikoItem.add((Component)((Object)this.createLabel(" Ket: ")));
        pPsikoItem.add((Component)((Object)this.TKetPsiko));
        pPsiko.add(this.wrapRow("Psikologis :", pPsikoItem));
        this.TStatusNikah = new TextBox();
        this.TStatusNikah.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Status Pernikahan :", (Component)((Object)this.TStatusNikah)));
        JPanel pAnak = new JPanel(new FlowLayout(0, 2, 0));
        pAnak.setOpaque(false);
        this.CmbAnak = new ComboBox();
        this.CmbAnak.addItem("Tidak Ada");
        this.CmbAnak.addItem("Ada");
        this.CmbAnak.setPreferredSize(new Dimension(100, 23));
        this.TJumlahAnak = new TextBox();
        this.TJumlahAnak.setPreferredSize(new Dimension(50, 23));
        pAnak.add(this.CmbAnak);
        pAnak.add((Component)((Object)this.createLabel(" Jmlh:")));
        pAnak.add((Component)((Object)this.TJumlahAnak));
        pPsiko.add(this.wrapRow("Anak :", pAnak));
        this.TPendidikan = new TextBox();
        this.TPendidikan.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Pendidikan Terakhir :", (Component)((Object)this.TPendidikan)));
        this.CmbWargaNegara = new ComboBox();
        this.CmbWargaNegara.addItem("WNI");
        this.CmbWargaNegara.addItem("WNA");
        this.CmbWargaNegara.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Warga Negara :", this.CmbWargaNegara));
        this.CmbPekerjaan = new ComboBox();
        for (String item : pekerjaanItems = new String[]{"PNS", "Swasta", "TNI/Polri", "Wiraswasta", "Tidak Bekerja"}) {
            this.CmbPekerjaan.addItem(item);
        }
        this.CmbPekerjaan.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Pekerjaan :", this.CmbPekerjaan));
        this.CmbAktivitas = new ComboBox();
        for (String item : aktivitasItems = new String[]{"Mandiri", "Kursi Roda", "Tirah Baring"}) {
            this.CmbAktivitas.addItem(item);
        }
        this.CmbAktivitas.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Aktivitas :", this.CmbAktivitas));
        this.CmbCuriga = new ComboBox();
        this.CmbCuriga.addItem("Tidak");
        this.CmbCuriga.addItem("Ya");
        this.CmbCuriga.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Curiga Penganiayaan/Penelantaran :", this.CmbCuriga));
        JPanel pTinggalBersama = new JPanel(new FlowLayout(0, 2, 0));
        pTinggalBersama.setOpaque(false);
        this.CmbTinggalBersama = new ComboBox();
        for (String item : tinggalBersamaItems = new String[]{"Suami/Istri", "Anak", "Orang Tua", "Sendiri", "Lainnya"}) {
            this.CmbTinggalBersama.addItem(item);
        }
        this.CmbTinggalBersama.setPreferredSize(new Dimension(150, 23));
        this.TKetTinggal = new TextBox();
        this.TKetTinggal.setPreferredSize(new Dimension(150, 23));
        pTinggalBersama.add(this.CmbTinggalBersama);
        pTinggalBersama.add((Component)((Object)this.createLabel(" Ket: ")));
        pTinggalBersama.add((Component)((Object)this.TKetTinggal));
        pPsiko.add(this.wrapRow("Tinggal Bersama :", pTinggalBersama));
        JPanel pTempatTinggal = new JPanel(new FlowLayout(0, 2, 0));
        pTempatTinggal.setOpaque(false);
        this.CmbTempatTinggal = new ComboBox();
        for (String item : tempatTinggalItems = new String[]{"Rumah", "Panti Asuhan", "Lainnya"}) {
            this.CmbTempatTinggal.addItem(item);
        }
        this.CmbTempatTinggal.setPreferredSize(new Dimension(150, 23));
        this.TKetTempatTinggal = new TextBox();
        this.TKetTempatTinggal.setPreferredSize(new Dimension(150, 23));
        pTempatTinggal.add(this.CmbTempatTinggal);
        pTempatTinggal.add((Component)((Object)this.createLabel(" Ket: ")));
        pTempatTinggal.add((Component)((Object)this.TKetTempatTinggal));
        pPsiko.add(this.wrapRow("Tempat Tinggal :", pTempatTinggal));
        this.CmbBudaya = new ComboBox();
        this.CmbBudaya.addItem("Tidak");
        this.CmbBudaya.addItem("Ya");
        this.CmbBudaya.setPreferredSize(new Dimension(150, 23));
        pPsiko.add(this.wrapRow("Keyakinan Budaya Terkait Pelayanan :", this.CmbBudaya));
        JPanel pAgama = new JPanel(new FlowLayout(0, 2, 0));
        pAgama.setOpaque(false);
        this.TAgama = new TextBox();
        this.TAgama.setPreferredSize(new Dimension(150, 23));
        this.CmbBimbinganIbadah = new ComboBox();
        this.CmbBimbinganIbadah.addItem("Ya");
        this.CmbBimbinganIbadah.addItem("Tidak");
        this.CmbBimbinganIbadah.setPreferredSize(new Dimension(80, 23));
        pAgama.add((Component)((Object)this.TAgama));
        pAgama.add((Component)((Object)this.createLabel(" Memerlukan Bimbingan Ibadah?")));
        pAgama.add(this.CmbBimbinganIbadah);
        pPsiko.add(this.wrapRow("Agama :", pAgama));
        JPanel pMasalahKep = new JPanel(new FlowLayout(0, 2, 0));
        pMasalahKep.setOpaque(false);
        this.ChkMasalahPsiko1 = this.createCekBox("Kurang Pengetahuan");
        this.ChkMasalahPsiko2 = this.createCekBox("Cemas");
        this.ChkMasalahPsiko3 = this.createCekBox("Hambatan praktek ibadah");
        this.ChkMasalahPsiko4 = this.createCekBox("Risiko melukai diri dan orang lain");
        pMasalahKep.add(this.ChkMasalahPsiko1);
        pMasalahKep.add(this.ChkMasalahPsiko2);
        pMasalahKep.add(this.ChkMasalahPsiko3);
        pMasalahKep.add(this.ChkMasalahPsiko4);
        pPsiko.add(this.wrapRow("Masalah Kep :", pMasalahKep));
        FormInput.add(pPsiko);
        panelisi pNeuro = new panelisi();
        pNeuro.setLayout(new BoxLayout(pNeuro, 1));
        pNeuro.setBorder(new TitledBorder("PENGKAJIAN NEUROSENSORIS"));
        JPanel pMental = new JPanel(new FlowLayout(0, 2, 0));
        pMental.setOpaque(false);
        this.RdoStatusCM = this.createRadioButton("CM");
        this.RdoStatusBingung = this.createRadioButton("Bingung");
        this.RdoStatusMengantuk = this.createRadioButton("Mengantuk");
        this.RdoStatusApatis = this.createRadioButton("Apatis");
        this.RdoStatusTidakRespon = this.createRadioButton("Tidak Ada Respon");
        ButtonGroup grpMental = new ButtonGroup();
        grpMental.add(this.RdoStatusCM);
        grpMental.add(this.RdoStatusBingung);
        grpMental.add(this.RdoStatusMengantuk);
        grpMental.add(this.RdoStatusApatis);
        grpMental.add(this.RdoStatusTidakRespon);
        pMental.add(this.RdoStatusCM);
        pMental.add(this.RdoStatusBingung);
        pMental.add(this.RdoStatusMengantuk);
        pMental.add(this.RdoStatusApatis);
        pMental.add(this.RdoStatusTidakRespon);
        pNeuro.add(this.wrapRow("Status Mental :", pMental));
        JPanel pOrientasi1 = new JPanel(new FlowLayout(0, 2, 0));
        pOrientasi1.setOpaque(false);
        this.RdoOriOrang = this.createRadioButton("Orang");
        this.TOriOrang = new TextBox();
        this.TOriOrang.setEditable(false);
        this.TOriOrang.setPreferredSize(new Dimension(100, 23));
        this.RdoOriTempat = this.createRadioButton("Tempat");
        this.TOriTempat = new TextBox();
        this.TOriTempat.setEditable(false);
        this.TOriTempat.setPreferredSize(new Dimension(100, 23));
        pOrientasi1.add(this.RdoOriOrang);
        pOrientasi1.add((Component)((Object)this.TOriOrang));
        pOrientasi1.add((Component)((Object)this.createLabel("    ")));
        pOrientasi1.add(this.RdoOriTempat);
        pOrientasi1.add((Component)((Object)this.TOriTempat));
        JPanel pOrientasi2 = new JPanel(new FlowLayout(0, 2, 0));
        pOrientasi2.setOpaque(false);
        this.RdoOriWaktu = this.createRadioButton("Waktu");
        this.TOriWaktu = new TextBox();
        this.TOriWaktu.setEditable(false);
        this.TOriWaktu.setPreferredSize(new Dimension(100, 23));
        this.RdoOriSituasi = this.createRadioButton("Situasi");
        this.TOriSituasi = new TextBox();
        this.TOriSituasi.setEditable(false);
        this.TOriSituasi.setPreferredSize(new Dimension(100, 23));
        pOrientasi2.add(this.RdoOriWaktu);
        pOrientasi2.add((Component)((Object)this.TOriWaktu));
        pOrientasi2.add((Component)((Object)this.createLabel("    ")));
        pOrientasi2.add(this.RdoOriSituasi);
        pOrientasi2.add((Component)((Object)this.TOriSituasi));
        ButtonGroup grpOrientasi = new ButtonGroup();
        grpOrientasi.add(this.RdoOriOrang);
        grpOrientasi.add(this.RdoOriTempat);
        grpOrientasi.add(this.RdoOriWaktu);
        grpOrientasi.add(this.RdoOriSituasi);
        ItemListener ilOrang = e -> this.TOriOrang.setEditable(this.RdoOriOrang.isSelected());
        ItemListener ilTempat = e -> this.TOriTempat.setEditable(this.RdoOriTempat.isSelected());
        ItemListener ilWaktu = e -> this.TOriWaktu.setEditable(this.RdoOriWaktu.isSelected());
        ItemListener ilSituasi = e -> this.TOriSituasi.setEditable(this.RdoOriSituasi.isSelected());
        this.RdoOriOrang.addItemListener(ilOrang);
        this.RdoOriTempat.addItemListener(ilTempat);
        this.RdoOriWaktu.addItemListener(ilWaktu);
        this.RdoOriSituasi.addItemListener(ilSituasi);
        JPanel pO = new JPanel();
        pO.setLayout(new BoxLayout(pO, 1));
        pO.setOpaque(false);
        pO.add(pOrientasi1);
        pO.add(pOrientasi2);
        pNeuro.add(this.wrapRow("Orientasi :", pO));
        this.CmbMemori = new ComboBox();
        this.CmbMemori.addItem("Baik");
        this.CmbMemori.addItem("Sedang");
        this.CmbMemori.addItem("Buruk");
        this.CmbMemori.setPreferredSize(new Dimension(150, 23));
        pNeuro.add(this.wrapRow("Memori :", this.CmbMemori));
        JPanel pPupilKanan = new JPanel(new FlowLayout(0, 2, 0));
        pPupilKanan.setOpaque(false);
        this.TPupilKananUk = new TextBox();
        this.TPupilKananUk.setPreferredSize(new Dimension(50, 23));
        this.TPupilKananReflex = new TextBox();
        this.TPupilKananReflex.setPreferredSize(new Dimension(80, 23));
        pPupilKanan.add((Component)((Object)this.createLabel("Ukuran:")));
        pPupilKanan.add((Component)((Object)this.TPupilKananUk));
        pPupilKanan.add((Component)((Object)this.createLabel("   Reflex Cahaya:")));
        pPupilKanan.add((Component)((Object)this.TPupilKananReflex));
        pNeuro.add(this.wrapRow("Pupil Kanan :", pPupilKanan));
        JPanel pPupilKiri = new JPanel(new FlowLayout(0, 2, 0));
        pPupilKiri.setOpaque(false);
        this.TPupilKiriUk = new TextBox();
        this.TPupilKiriUk.setPreferredSize(new Dimension(50, 23));
        this.TPupilKiriReflex = new TextBox();
        this.TPupilKiriReflex.setPreferredSize(new Dimension(80, 23));
        pPupilKiri.add((Component)((Object)this.createLabel("Ukuran:")));
        pPupilKiri.add((Component)((Object)this.TPupilKiriUk));
        pPupilKiri.add((Component)((Object)this.createLabel("   Reflex Cahaya:")));
        pPupilKiri.add((Component)((Object)this.TPupilKiriReflex));
        pNeuro.add(this.wrapRow("Pupil Kiri :", pPupilKiri));
        JPanel pGCS = new JPanel(new FlowLayout(0, 2, 0));
        pGCS.setOpaque(false);
        this.TGCSEye = new TextBox();
        this.TGCSEye.setPreferredSize(new Dimension(30, 23));
        this.TGCSMotorik = new TextBox();
        this.TGCSMotorik.setPreferredSize(new Dimension(30, 23));
        this.TGCSVerbal = new TextBox();
        this.TGCSVerbal.setPreferredSize(new Dimension(30, 23));
        this.TGCSJumlah = new TextBox();
        this.TGCSJumlah.setPreferredSize(new Dimension(30, 23));
        pGCS.add((Component)((Object)this.createLabel("E:")));
        pGCS.add((Component)((Object)this.TGCSEye));
        pGCS.add((Component)((Object)this.createLabel(" M:")));
        pGCS.add((Component)((Object)this.TGCSMotorik));
        pGCS.add((Component)((Object)this.createLabel(" V:")));
        pGCS.add((Component)((Object)this.TGCSVerbal));
        pGCS.add((Component)((Object)this.createLabel(" Jml:")));
        pGCS.add((Component)((Object)this.TGCSJumlah));
        pNeuro.add(this.wrapRow("GCS :", pGCS));
        JPanel pTanda = new JPanel(new FlowLayout(0, 2, 0));
        pTanda.setOpaque(false);
        this.RdoKakuKuduk = this.createRadioButton("Kaku Kuduk");
        this.RdoBrudzinski = this.createRadioButton("Reflek Brudzinski");
        this.RdoKernig = this.createRadioButton("Kernig Sign");
        this.RdoNeuroLain = this.createRadioButton("Lain-lain");
        ButtonGroup grpTanda = new ButtonGroup();
        grpTanda.add(this.RdoKakuKuduk);
        grpTanda.add(this.RdoBrudzinski);
        grpTanda.add(this.RdoKernig);
        grpTanda.add(this.RdoNeuroLain);
        pTanda.add(this.RdoKakuKuduk);
        pTanda.add(this.RdoBrudzinski);
        pTanda.add(this.RdoKernig);
        pTanda.add(this.RdoNeuroLain);
        pNeuro.add(this.wrapRow("Tanda Perangsang Selaput Otak :", pTanda));
        JPanel pMasNeuro = new JPanel(new FlowLayout(0, 2, 0));
        pMasNeuro.setOpaque(false);
        this.ChkMasalahNeuro1 = this.createCekBox("Risiko injury");
        this.ChkMasalahNeuro2 = this.createCekBox("Gangguan pemenuhan aktivitas b/d penurunan fungsi ekstrimitas");
        pMasNeuro.add(this.ChkMasalahNeuro1);
        pMasNeuro.add(this.ChkMasalahNeuro2);
        pNeuro.add(this.wrapRow("Masalah Kep :", pMasNeuro));
        FormInput.add(pNeuro);
        panelisi pNafas = new panelisi();
        pNafas.setLayout(new BoxLayout(pNafas, 1));
        pNafas.setBorder(new TitledBorder("PERNAFASAN"));
        JPanel pAirway = new JPanel(new FlowLayout(0, 2, 0));
        pAirway.setOpaque(false);
        this.RdoAirway1 = this.createRadioButton("Normal/Bersih");
        this.RdoAirway2 = this.createRadioButton("Opa/NPA");
        this.RdoAirway3 = this.createRadioButton("Benda asing");
        this.TBendaAsing = new TextBox();
        this.TBendaAsing.setPreferredSize(new Dimension(100, 23));
        this.TBendaAsing.setEditable(false);
        this.RdoAirway4 = this.createRadioButton("ETT: Ukuran");
        this.TAirwayUkuran = new TextBox();
        this.TAirwayUkuran.setPreferredSize(new Dimension(50, 23));
        this.TAirwayUkuran.setEditable(false);
        ButtonGroup grpAirway = new ButtonGroup();
        grpAirway.add(this.RdoAirway1);
        grpAirway.add(this.RdoAirway2);
        grpAirway.add(this.RdoAirway3);
        grpAirway.add(this.RdoAirway4);
        pAirway.add(this.RdoAirway1);
        pAirway.add(this.RdoAirway2);
        pAirway.add(this.RdoAirway3);
        pAirway.add((Component)((Object)this.TBendaAsing));
        pAirway.add(this.RdoAirway4);
        pAirway.add((Component)((Object)this.TAirwayUkuran));
        pNafas.add(this.wrapRow("Airway :", pAirway));
        ItemListener ilA3 = e -> this.TBendaAsing.setEditable(this.RdoAirway3.isSelected());
        ItemListener ilA4 = e -> this.TAirwayUkuran.setEditable(this.RdoAirway4.isSelected());
        this.RdoAirway3.addItemListener(ilA3);
        this.RdoAirway4.addItemListener(ilA4);
        JPanel pBreathing = new JPanel(new FlowLayout(0, 2, 0));
        pBreathing.setOpaque(false);
        this.RdoBreath1 = this.createRadioButton("Normal");
        this.RdoBreath2 = this.createRadioButton("Dyspnea");
        this.RdoBreath3 = this.createRadioButton("Tachypnea");
        this.RdoBreath4 = this.createRadioButton("Sleep apnea");
        this.RdoBreath5 = this.createRadioButton("Stridor");
        ButtonGroup grpBreath = new ButtonGroup();
        grpBreath.add(this.RdoBreath1);
        grpBreath.add(this.RdoBreath2);
        grpBreath.add(this.RdoBreath3);
        grpBreath.add(this.RdoBreath4);
        grpBreath.add(this.RdoBreath5);
        pBreathing.add(this.RdoBreath1);
        pBreathing.add(this.RdoBreath2);
        pBreathing.add(this.RdoBreath3);
        pBreathing.add(this.RdoBreath4);
        pBreathing.add(this.RdoBreath5);
        pNafas.add(this.wrapRow("Breathing :", pBreathing));
        JPanel pBunyi1 = new JPanel(new FlowLayout(0, 2, 0));
        pBunyi1.setOpaque(false);
        this.RdoBunyi1 = this.createRadioButton("Normal");
        this.RdoBunyi1.setPreferredSize(new Dimension(80, 23));
        this.RdoBunyi2 = this.createRadioButton("Ronkhi, paru kanan/kiri");
        this.RdoBunyi2.setPreferredSize(new Dimension(170, 23));
        this.RdoBunyi3 = this.createRadioButton("Melemah, paru kanan/kiri");
        this.RdoBunyi3.setPreferredSize(new Dimension(180, 23));
        pBunyi1.add(this.RdoBunyi1);
        pBunyi1.add(this.RdoBunyi2);
        pBunyi1.add(this.RdoBunyi3);
        JPanel pBunyi2 = new JPanel(new FlowLayout(0, 2, 0));
        pBunyi2.setOpaque(false);
        this.RdoBunyi4 = this.createRadioButton("Whezing, paru kanan/kiri");
        this.RdoBunyi4.setPreferredSize(new Dimension(170, 23));
        this.RdoBunyi5 = this.createRadioButton("Menghilang, paru kanan/kiri");
        this.RdoBunyi5.setPreferredSize(new Dimension(180, 23));
        JLabel lblEmpty = new JLabel("");
        lblEmpty.setPreferredSize(new Dimension(80, 23));
        pBunyi2.add(lblEmpty);
        pBunyi2.add(this.RdoBunyi4);
        pBunyi2.add(this.RdoBunyi5);
        ButtonGroup grpBunyi = new ButtonGroup();
        grpBunyi.add(this.RdoBunyi1);
        grpBunyi.add(this.RdoBunyi2);
        grpBunyi.add(this.RdoBunyi3);
        grpBunyi.add(this.RdoBunyi4);
        grpBunyi.add(this.RdoBunyi5);
        JPanel pBunyiAll = new JPanel();
        pBunyiAll.setLayout(new BoxLayout(pBunyiAll, 1));
        pBunyiAll.setOpaque(false);
        pBunyiAll.add(pBunyi1);
        pBunyiAll.add(pBunyi2);
        pNafas.add(this.wrapRow("Bunyi Paru :", pBunyiAll));
        JPanel pSulitNafas = new JPanel(new FlowLayout(0, 2, 0));
        pSulitNafas.setOpaque(false);
        this.RdoSulitNafas1 = this.createRadioButton("Tidak");
        this.RdoSulitNafas2 = this.createRadioButton("Ya, penggunaan alat bantu nafas");
        this.RdoSulitNafas3 = this.createRadioButton("Saat aktifitas");
        ButtonGroup grpSulitNafas = new ButtonGroup();
        grpSulitNafas.add(this.RdoSulitNafas1);
        grpSulitNafas.add(this.RdoSulitNafas2);
        grpSulitNafas.add(this.RdoSulitNafas3);
        this.RdoAlatNafas1 = this.createRadioButton("Ya");
        this.RdoAlatNafas2 = this.createRadioButton("Tidak");
        ButtonGroup grpAlatNafas = new ButtonGroup();
        grpAlatNafas.add(this.RdoAlatNafas1);
        grpAlatNafas.add(this.RdoAlatNafas2);
        pSulitNafas.add(this.RdoSulitNafas1);
        pSulitNafas.add(this.RdoSulitNafas2);
        pSulitNafas.add(this.RdoAlatNafas1);
        pSulitNafas.add(this.RdoAlatNafas2);
        pSulitNafas.add(this.RdoSulitNafas3);
        pNafas.add(this.wrapRow("Kesulitan bernafas :", pSulitNafas));
        JPanel pOksigen = new JPanel(new FlowLayout(0, 2, 0));
        pOksigen.setOpaque(false);
        this.RdoOksigen1 = this.createRadioButton("Tidak");
        this.RdoOksigen2 = this.createRadioButton("Ya,");
        ButtonGroup grpOksigen = new ButtonGroup();
        grpOksigen.add(this.RdoOksigen1);
        grpOksigen.add(this.RdoOksigen2);
        this.TOksigenLtr = new TextBox();
        this.TOksigenLtr.setPreferredSize(new Dimension(50, 23));
        this.CmbOksigenJenis = new ComboBox();
        this.CmbOksigenJenis.addItem("-");
        this.CmbOksigenJenis.addItem("Nasal");
        this.CmbOksigenJenis.addItem("Sungkup");
        this.CmbOksigenJenis.addItem("Rebreathing mask");
        this.CmbOksigenJenis.setPreferredSize(new Dimension(150, 23));
        pOksigen.add(this.RdoOksigen1);
        pOksigen.add(this.RdoOksigen2);
        pOksigen.add((Component)((Object)this.TOksigenLtr));
        pOksigen.add((Component)((Object)this.createLabel(" L/m: ")));
        pOksigen.add(this.CmbOksigenJenis);
        pNafas.add(this.wrapRow("Menggunakan Oksigen :", pOksigen));
        JPanel pFreq = new JPanel(new FlowLayout(0, 2, 0));
        pFreq.setOpaque(false);
        this.TFrekuensiNafas = new TextBox();
        this.TFrekuensiNafas.setPreferredSize(new Dimension(50, 23));
        pFreq.add((Component)((Object)this.TFrekuensiNafas));
        pFreq.add((Component)((Object)this.createLabel(" x/m")));
        pNafas.add(this.wrapRow("Frekuensi Nafas :", pFreq));
        JPanel pBatuk = new JPanel(new FlowLayout(0, 2, 0));
        pBatuk.setOpaque(false);
        this.RdoBatuk1 = this.createRadioButton("Tidak");
        this.RdoBatuk2 = this.createRadioButton("Ya");
        this.RdoBatuk3 = this.createRadioButton("Produktif");
        this.RdoBatuk4 = this.createRadioButton("Tidak produktif");
        ButtonGroup grpBatuk = new ButtonGroup();
        grpBatuk.add(this.RdoBatuk1);
        grpBatuk.add(this.RdoBatuk2);
        grpBatuk.add(this.RdoBatuk3);
        grpBatuk.add(this.RdoBatuk4);
        pBatuk.add(this.RdoBatuk1);
        pBatuk.add(this.RdoBatuk2);
        pBatuk.add(this.RdoBatuk3);
        pBatuk.add(this.RdoBatuk4);
        pNafas.add(this.wrapRow("Batuk :", pBatuk));
        JPanel pSpO2Panel = new JPanel(new FlowLayout(0, 2, 0));
        pSpO2Panel.setOpaque(false);
        this.TSpO2 = new TextBox();
        this.TSpO2.setPreferredSize(new Dimension(50, 23));
        pSpO2Panel.add((Component)((Object)this.TSpO2));
        pSpO2Panel.add((Component)((Object)this.createLabel(" %")));
        pNafas.add(this.wrapRow("SpO2 :", pSpO2Panel));
        JPanel pMasNafas1 = new JPanel(new FlowLayout(0, 2, 0));
        pMasNafas1.setOpaque(false);
        this.ChkMasalahNafas1 = this.createCekBox("Bersihkan jalan nafas tidak efektif");
        this.ChkMasalahNafas1.setPreferredSize(new Dimension(250, 23));
        this.ChkMasalahNafas2 = this.createCekBox("Risiko aspirasi/asfiksia");
        pMasNafas1.add(this.ChkMasalahNafas1);
        pMasNafas1.add(this.ChkMasalahNafas2);
        JPanel pMasNafas2 = new JPanel(new FlowLayout(0, 2, 0));
        pMasNafas2.setOpaque(false);
        this.ChkMasalahNafas3 = this.createCekBox("Pola nafas tidak efektif");
        this.ChkMasalahNafas3.setPreferredSize(new Dimension(250, 23));
        pMasNafas2.add(this.ChkMasalahNafas3);
        JPanel pMasNafas3 = new JPanel(new FlowLayout(0, 2, 0));
        pMasNafas3.setOpaque(false);
        this.ChkMasalahNafas4 = this.createCekBox("Gangguan pertukaran gas");
        this.ChkMasalahNafas4.setPreferredSize(new Dimension(250, 23));
        pMasNafas3.add(this.ChkMasalahNafas4);
        JPanel pMasNafasAll = new JPanel();
        pMasNafasAll.setLayout(new BoxLayout(pMasNafasAll, 1));
        pMasNafasAll.setOpaque(false);
        pMasNafasAll.add(pMasNafas1);
        pMasNafasAll.add(pMasNafas2);
        pMasNafasAll.add(pMasNafas3);
        pNafas.add(this.wrapRow("Masalah Keperawatan :", pMasNafasAll));
        FormInput.add(pNafas);
        panelisi pSirkulasi = new panelisi();
        pSirkulasi.setLayout(new BoxLayout(pSirkulasi, 1));
        pSirkulasi.setBorder(new TitledBorder("SIRKULASI"));
        JPanel pTD = new JPanel(new FlowLayout(0, 2, 0));
        pTD.setOpaque(false);
        this.TTekananDarah = new TextBox();
        this.TTekananDarah.setPreferredSize(new Dimension(80, 23));
        pTD.add((Component)((Object)this.TTekananDarah));
        pTD.add((Component)((Object)this.createLabel(" mmHg")));
        pSirkulasi.add(this.wrapRow("Tekanan darah :", pTD));
        JPanel pSirk = new JPanel(new FlowLayout(0, 2, 0));
        pSirk.setOpaque(false);
        this.RdoSirkulasi1 = this.createRadioButton("Tidak masalah");
        this.RdoSirkulasi2 = this.createRadioButton("Edema");
        this.RdoSirkulasi3 = this.createRadioButton("Sianosis");
        this.RdoSirkulasi4 = this.createRadioButton("Berkeringat");
        this.RdoSirkulasi5 = this.createRadioButton("Lainnya");
        ButtonGroup grpSirkulasi = new ButtonGroup();
        grpSirkulasi.add(this.RdoSirkulasi1);
        grpSirkulasi.add(this.RdoSirkulasi2);
        grpSirkulasi.add(this.RdoSirkulasi3);
        grpSirkulasi.add(this.RdoSirkulasi4);
        grpSirkulasi.add(this.RdoSirkulasi5);
        pSirk.add(this.RdoSirkulasi1);
        pSirk.add(this.RdoSirkulasi2);
        pSirk.add(this.RdoSirkulasi3);
        pSirk.add(this.RdoSirkulasi4);
        pSirk.add(this.RdoSirkulasi5);
        pSirkulasi.add(this.wrapRow("Gangguan sirkulasi :", pSirk));
        JPanel pCRT = new JPanel(new FlowLayout(0, 2, 0));
        pCRT.setOpaque(false);
        this.RdoCRT1 = this.createRadioButton("< 2 detik");
        this.RdoCRT2 = this.createRadioButton("> 2 detik");
        ButtonGroup grpCRT = new ButtonGroup();
        grpCRT.add(this.RdoCRT1);
        grpCRT.add(this.RdoCRT2);
        pCRT.add(this.RdoCRT1);
        pCRT.add(this.RdoCRT2);
        pSirkulasi.add(this.wrapRow("CRT :", pCRT));
        JPanel pNadi = new JPanel(new FlowLayout(0, 2, 0));
        pNadi.setOpaque(false);
        this.TDenyutNadi = new TextBox();
        this.TDenyutNadi.setPreferredSize(new Dimension(50, 23));
        this.RdoNadi1 = this.createRadioButton("Kuat");
        this.RdoNadi2 = this.createRadioButton("Lemah");
        this.RdoNadi3 = this.createRadioButton("Tidak teraba, jelaskan");
        ButtonGroup grpNadi = new ButtonGroup();
        grpNadi.add(this.RdoNadi1);
        grpNadi.add(this.RdoNadi2);
        grpNadi.add(this.RdoNadi3);
        this.TNadiJelas = new TextBox();
        this.TNadiJelas.setPreferredSize(new Dimension(150, 23));
        pNadi.add((Component)((Object)this.createLabel("Jumlah ")));
        pNadi.add((Component)((Object)this.TDenyutNadi));
        pNadi.add((Component)((Object)this.createLabel(" x/mnt ")));
        pNadi.add(this.RdoNadi1);
        pNadi.add(this.RdoNadi2);
        pNadi.add(this.RdoNadi3);
        pNadi.add((Component)((Object)this.TNadiJelas));
        pSirkulasi.add(this.wrapRow("Denyut nadi :", pNadi));
        JPanel pIrama = new JPanel(new FlowLayout(0, 2, 0));
        pIrama.setOpaque(false);
        this.RdoIrama1 = this.createRadioButton("Teratur");
        this.RdoIrama2 = this.createRadioButton("Tidak teratur");
        ButtonGroup grpIrama = new ButtonGroup();
        grpIrama.add(this.RdoIrama1);
        grpIrama.add(this.RdoIrama2);
        pIrama.add(this.RdoIrama1);
        pIrama.add(this.RdoIrama2);
        pSirkulasi.add(this.wrapRow("Irama jantung :", pIrama));
        JPanel pPacemaker = new JPanel(new FlowLayout(0, 2, 0));
        pPacemaker.setOpaque(false);
        this.RdoPacemaker1 = this.createRadioButton("Tidak");
        this.RdoPacemaker2 = this.createRadioButton("Ya, Jelaskan");
        ButtonGroup grpPacemaker = new ButtonGroup();
        grpPacemaker.add(this.RdoPacemaker1);
        grpPacemaker.add(this.RdoPacemaker2);
        this.TPacemakerJelas = new TextBox();
        this.TPacemakerJelas.setPreferredSize(new Dimension(150, 23));
        pPacemaker.add(this.RdoPacemaker1);
        pPacemaker.add(this.RdoPacemaker2);
        pPacemaker.add((Component)((Object)this.TPacemakerJelas));
        pSirkulasi.add(this.wrapRow("Pacemaker :", pPacemaker));
        JPanel pAkral = new JPanel(new FlowLayout(0, 2, 0));
        pAkral.setOpaque(false);
        this.RdoAkral1 = this.createRadioButton("Dingin");
        this.RdoAkral2 = this.createRadioButton("Hangat");
        ButtonGroup grpAkral = new ButtonGroup();
        grpAkral.add(this.RdoAkral1);
        grpAkral.add(this.RdoAkral2);
        pAkral.add(this.RdoAkral1);
        pAkral.add(this.RdoAkral2);
        pSirkulasi.add(this.wrapRow("Akral :", pAkral));
        JPanel pMasSirk1 = new JPanel(new FlowLayout(0, 2, 0));
        pMasSirk1.setOpaque(false);
        this.ChkMasalahSirk1 = this.createCekBox("Gangguan Perfusi jaringan :");
        this.ChkMasalahSirk1.setPreferredSize(new Dimension(280, 23));
        this.TMasalahSirkJelas1 = new TextBox();
        this.TMasalahSirkJelas1.setPreferredSize(new Dimension(250, 23));
        pMasSirk1.add(this.ChkMasalahSirk1);
        pMasSirk1.add((Component)((Object)this.TMasalahSirkJelas1));
        JPanel pMasSirk2 = new JPanel(new FlowLayout(0, 2, 0));
        pMasSirk2.setOpaque(false);
        this.ChkMasalahSirk2 = this.createCekBox("Risiko Syok :");
        this.ChkMasalahSirk2.setPreferredSize(new Dimension(280, 23));
        this.TMasalahSirkJelas2 = new TextBox();
        this.TMasalahSirkJelas2.setPreferredSize(new Dimension(250, 23));
        pMasSirk2.add(this.ChkMasalahSirk2);
        pMasSirk2.add((Component)((Object)this.TMasalahSirkJelas2));
        JPanel pMasSirk3 = new JPanel(new FlowLayout(0, 2, 0));
        pMasSirk3.setOpaque(false);
        this.ChkMasalahSirk3 = this.createCekBox("Gangguan/risiko penurunan curah jantung :");
        this.ChkMasalahSirk3.setPreferredSize(new Dimension(280, 23));
        this.TMasalahSirkJelas3 = new TextBox();
        this.TMasalahSirkJelas3.setPreferredSize(new Dimension(250, 23));
        pMasSirk3.add(this.ChkMasalahSirk3);
        pMasSirk3.add((Component)((Object)this.TMasalahSirkJelas3));
        JPanel pMasSirkAll = new JPanel();
        pMasSirkAll.setLayout(new BoxLayout(pMasSirkAll, 1));
        pMasSirkAll.setOpaque(false);
        pMasSirkAll.add(pMasSirk1);
        pMasSirkAll.add(pMasSirk2);
        pMasSirkAll.add(pMasSirk3);
        pSirkulasi.add(this.wrapRow("Masalah Keperawatan :", pMasSirkAll));
        FormInput.add(pSirkulasi);
        panelisi pKemih = new panelisi();
        pKemih.setLayout(new BoxLayout(pKemih, 1));
        pKemih.setBorder(new TitledBorder("PERKEMIHAN"));
        JPanel pBAK1 = new JPanel(new FlowLayout(0, 2, 0));
        pBAK1.setOpaque(false);
        this.RdoBAK1 = this.createRadioButton("Normal");
        this.RdoBAK1.setPreferredSize(new Dimension(80, 23));
        this.RdoBAK2 = this.createRadioButton("Inkontinensia");
        this.RdoBAK2.setPreferredSize(new Dimension(100, 23));
        this.RdoBAK3 = this.createRadioButton("Anuria");
        this.RdoBAK3.setPreferredSize(new Dimension(80, 23));
        this.RdoBAK4 = this.createRadioButton("Polyuria");
        this.RdoBAK4.setPreferredSize(new Dimension(80, 23));
        this.RdoBAK5 = this.createRadioButton("Hematuria");
        pBAK1.add(this.RdoBAK1);
        pBAK1.add(this.RdoBAK2);
        pBAK1.add(this.RdoBAK3);
        pBAK1.add(this.RdoBAK4);
        pBAK1.add(this.RdoBAK5);
        JPanel pBAK2 = new JPanel(new FlowLayout(0, 2, 0));
        pBAK2.setOpaque(false);
        this.RdoBAK6 = this.createRadioButton("Dysuria");
        this.RdoBAK6.setPreferredSize(new Dimension(80, 23));
        this.RdoBAK7 = this.createRadioButton("Lainnya");
        this.TBAKLainnya = new TextBox();
        this.TBAKLainnya.setPreferredSize(new Dimension(200, 23));
        pBAK2.add(this.RdoBAK6);
        pBAK2.add(this.RdoBAK7);
        pBAK2.add((Component)((Object)this.TBAKLainnya));
        ButtonGroup grpBAK = new ButtonGroup();
        grpBAK.add(this.RdoBAK1);
        grpBAK.add(this.RdoBAK2);
        grpBAK.add(this.RdoBAK3);
        grpBAK.add(this.RdoBAK4);
        grpBAK.add(this.RdoBAK5);
        grpBAK.add(this.RdoBAK6);
        grpBAK.add(this.RdoBAK7);
        JPanel pBAKAll = new JPanel();
        pBAKAll.setLayout(new BoxLayout(pBAKAll, 1));
        pBAKAll.setOpaque(false);
        pBAKAll.add(pBAK1);
        pBAKAll.add(pBAK2);
        pKemih.add(this.wrapRow("BAK :", pBAKAll));
        JPanel pKateter = new JPanel(new FlowLayout(0, 2, 0));
        pKateter.setOpaque(false);
        this.RdoKateter1 = this.createRadioButton("Tidak");
        this.RdoKateter2 = this.createRadioButton("Ya, jelaskan");
        ButtonGroup grpKateter = new ButtonGroup();
        grpKateter.add(this.RdoKateter1);
        grpKateter.add(this.RdoKateter2);
        this.TKateterJelas = new TextBox();
        this.TKateterJelas.setPreferredSize(new Dimension(200, 23));
        pKateter.add(this.RdoKateter1);
        pKateter.add(this.RdoKateter2);
        pKateter.add((Component)((Object)this.TKateterJelas));
        pKemih.add(this.wrapRow("Menggunakan kateter urin :", pKateter));
        JPanel pUrin = new JPanel(new FlowLayout(0, 2, 0));
        pUrin.setOpaque(false);
        this.TUrinJumlah = new TextBox();
        this.TUrinJumlah.setPreferredSize(new Dimension(80, 23));
        this.RdoUrin1 = this.createRadioButton("Jernih");
        this.RdoUrin2 = this.createRadioButton("Keruh");
        this.RdoUrin3 = this.createRadioButton("Darah");
        ButtonGroup grpUrin = new ButtonGroup();
        grpUrin.add(this.RdoUrin1);
        grpUrin.add(this.RdoUrin2);
        grpUrin.add(this.RdoUrin3);
        pUrin.add((Component)((Object)this.createLabel("Jumlah ")));
        pUrin.add((Component)((Object)this.TUrinJumlah));
        pUrin.add((Component)((Object)this.createLabel(" Warna ")));
        pUrin.add(this.RdoUrin1);
        pUrin.add(this.RdoUrin2);
        pUrin.add(this.RdoUrin3);
        pKemih.add(this.wrapRow("Urin :", pUrin));
        JPanel pProstat = new JPanel(new FlowLayout(0, 2, 0));
        pProstat.setOpaque(false);
        this.RdoProstat1 = this.createRadioButton("Tidak");
        this.RdoProstat2 = this.createRadioButton("Ya");
        ButtonGroup grpProstat = new ButtonGroup();
        grpProstat.add(this.RdoProstat1);
        grpProstat.add(this.RdoProstat2);
        pProstat.add(this.RdoProstat1);
        pProstat.add(this.RdoProstat2);
        pKemih.add(this.wrapRow("Masalah Prostat :", pProstat));
        JPanel pNyeri = new JPanel(new FlowLayout(0, 2, 0));
        pNyeri.setOpaque(false);
        this.RdoNyeriPinggang1 = this.createRadioButton("Tidak");
        this.RdoNyeriPinggang2 = this.createRadioButton("Ya");
        ButtonGroup grpNyeri = new ButtonGroup();
        grpNyeri.add(this.RdoNyeriPinggang1);
        grpNyeri.add(this.RdoNyeriPinggang2);
        pNyeri.add(this.RdoNyeriPinggang1);
        pNyeri.add(this.RdoNyeriPinggang2);
        pKemih.add(this.wrapRow("Keluhan nyeri pinggang :", pNyeri));
        JPanel pKelainan = new JPanel(new FlowLayout(0, 2, 0));
        pKelainan.setOpaque(false);
        this.RdoKelainan1 = this.createRadioButton("Tidak");
        this.RdoKelainan2 = this.createRadioButton("Ya, sebutkan");
        ButtonGroup grpKelainan = new ButtonGroup();
        grpKelainan.add(this.RdoKelainan1);
        grpKelainan.add(this.RdoKelainan2);
        this.TKelainanSebut = new TextBox();
        this.TKelainanSebut.setPreferredSize(new Dimension(200, 23));
        pKelainan.add(this.RdoKelainan1);
        pKelainan.add(this.RdoKelainan2);
        pKelainan.add((Component)((Object)this.TKelainanSebut));
        pKemih.add(this.wrapRow("Kelainan :", pKelainan));
        JPanel pMasKepKemih = new JPanel(new FlowLayout(0, 2, 0));
        pMasKepKemih.setOpaque(false);
        this.ChkMasalahKemih1 = this.createCekBox("Gangguan pola eliminasi");
        this.TMasalahKemihJelas1 = new TextBox();
        this.TMasalahKemihJelas1.setPreferredSize(new Dimension(250, 23));
        pMasKepKemih.add(this.ChkMasalahKemih1);
        pMasKepKemih.add((Component)((Object)this.TMasalahKemihJelas1));
        pKemih.add(this.wrapRow("Masalah Keperawatan :", pMasKepKemih));
        FormInput.add(pKemih);
        panelisi pRepro = new panelisi();
        pRepro.setLayout(new BoxLayout(pRepro, 1));
        pRepro.setBorder(new TitledBorder("SEKSUAL/REPRODUKSI"));
        pRepro.add(this.wrapRow("Wanita", new JLabel("")));
        JPanel pStatusOb = new JPanel(new FlowLayout(0, 2, 0));
        pStatusOb.setOpaque(false);
        this.TStatusObG = new TextBox();
        this.TStatusObG.setPreferredSize(new Dimension(50, 23));
        this.TStatusObP = new TextBox();
        this.TStatusObP.setPreferredSize(new Dimension(50, 23));
        this.TStatusObA = new TextBox();
        this.TStatusObA.setPreferredSize(new Dimension(50, 23));
        pStatusOb.add((Component)((Object)this.createLabel("G ")));
        pStatusOb.add((Component)((Object)this.TStatusObG));
        pStatusOb.add((Component)((Object)this.createLabel(" P ")));
        pStatusOb.add((Component)((Object)this.TStatusObP));
        pStatusOb.add((Component)((Object)this.createLabel(" A ")));
        pStatusOb.add((Component)((Object)this.TStatusObA));
        pRepro.add(this.wrapRow("Status obstetric :", pStatusOb));
        JPanel pMens = new JPanel(new FlowLayout(0, 2, 0));
        pMens.setOpaque(false);
        this.RdoMens1 = this.createRadioButton("Teratur");
        this.RdoMens2 = this.createRadioButton("Tidak teratur");
        this.RdoMens3 = this.createRadioButton("Menopause");
        this.RdoMens4 = this.createRadioButton("Dismenorhea");
        ButtonGroup grpMens = new ButtonGroup();
        grpMens.add(this.RdoMens1);
        grpMens.add(this.RdoMens2);
        grpMens.add(this.RdoMens3);
        grpMens.add(this.RdoMens4);
        pMens.add(this.RdoMens1);
        pMens.add(this.RdoMens2);
        pMens.add(this.RdoMens3);
        pMens.add(this.RdoMens4);
        pRepro.add(this.wrapRow("Menstruasi :", pMens));
        JPanel pPregnan = new JPanel(new FlowLayout(0, 2, 0));
        pPregnan.setOpaque(false);
        this.RdoPregnan1 = this.createRadioButton("Tidak");
        this.RdoPregnan2 = this.createRadioButton("Ya, HPHT");
        ButtonGroup grpPregnan = new ButtonGroup();
        grpPregnan.add(this.RdoPregnan1);
        grpPregnan.add(this.RdoPregnan2);
        this.TPregnanHPHT = new TextBox();
        this.TPregnanHPHT.setPreferredSize(new Dimension(80, 23));
        this.TPregnanHPL = new TextBox();
        this.TPregnanHPL.setPreferredSize(new Dimension(80, 23));
        pPregnan.add(this.RdoPregnan1);
        pPregnan.add(this.RdoPregnan2);
        pPregnan.add((Component)((Object)this.TPregnanHPHT));
        pPregnan.add((Component)((Object)this.createLabel(" HPL ")));
        pPregnan.add((Component)((Object)this.TPregnanHPL));
        pRepro.add(this.wrapRow("Pregnan :", pPregnan));
        JPanel pPostPartum = new JPanel(new FlowLayout(0, 2, 0));
        pPostPartum.setOpaque(false);
        this.TPostPartum = new TextBox();
        this.TPostPartum.setPreferredSize(new Dimension(250, 23));
        pPostPartum.add((Component)((Object)this.TPostPartum));
        pRepro.add(this.wrapRow("Post partum ke :", pPostPartum));
        JPanel pLochea = new JPanel(new FlowLayout(0, 2, 0));
        pLochea.setOpaque(false);
        this.TLochea = new TextBox();
        this.TLochea.setPreferredSize(new Dimension(150, 23));
        this.TLocheaJumlah = new TextBox();
        this.TLocheaJumlah.setPreferredSize(new Dimension(100, 23));
        pLochea.add((Component)((Object)this.createLabel("      Lochea : ")));
        pLochea.add((Component)((Object)this.TLochea));
        pLochea.add((Component)((Object)this.createLabel(" Jumlah ")));
        pLochea.add((Component)((Object)this.TLocheaJumlah));
        pRepro.add(this.wrapRow("", pLochea));
        JPanel pPayudara = new JPanel(new FlowLayout(0, 2, 0));
        pPayudara.setOpaque(false);
        this.TPayudara = new TextBox();
        this.TPayudara.setPreferredSize(new Dimension(150, 23));
        pPayudara.add((Component)((Object)this.createLabel("      Payudara : ")));
        pPayudara.add((Component)((Object)this.TPayudara));
        pRepro.add(this.wrapRow("", pPayudara));
        JPanel pPengeluaranASI = new JPanel(new FlowLayout(0, 2, 0));
        pPengeluaranASI.setOpaque(false);
        this.TPengeluaranASI = new TextBox();
        this.TPengeluaranASI.setPreferredSize(new Dimension(150, 23));
        pPengeluaranASI.add((Component)((Object)this.createLabel("      Pengeluaran ASI : ")));
        pPengeluaranASI.add((Component)((Object)this.TPengeluaranASI));
        pRepro.add(this.wrapRow("", pPengeluaranASI));
        JPanel pKontraksi = new JPanel(new FlowLayout(0, 2, 0));
        pKontraksi.setOpaque(false);
        this.TKontraksi = new TextBox();
        this.TKontraksi.setPreferredSize(new Dimension(150, 23));
        pKontraksi.add((Component)((Object)this.createLabel("      Kontraksi : ")));
        pKontraksi.add((Component)((Object)this.TKontraksi));
        pRepro.add(this.wrapRow("", pKontraksi));
        JPanel pPapsmear = new JPanel(new FlowLayout(0, 2, 0));
        pPapsmear.setOpaque(false);
        this.RdoPapsmear1 = this.createRadioButton("Tidak pernah");
        this.RdoPapsmear2 = this.createRadioButton("Pernah, terakhir tanggal");
        ButtonGroup grpPapsmear = new ButtonGroup();
        grpPapsmear.add(this.RdoPapsmear1);
        grpPapsmear.add(this.RdoPapsmear2);
        this.TPapsmearTgl = new TextBox();
        this.TPapsmearTgl.setPreferredSize(new Dimension(120, 23));
        pPapsmear.add(this.RdoPapsmear1);
        pPapsmear.add(this.RdoPapsmear2);
        pPapsmear.add((Component)((Object)this.TPapsmearTgl));
        pRepro.add(this.wrapRow("Papsmear :", pPapsmear));
        JPanel pMammo = new JPanel(new FlowLayout(0, 2, 0));
        pMammo.setOpaque(false);
        this.RdoMammo1 = this.createRadioButton("Tidak pernah");
        this.RdoMammo2 = this.createRadioButton("Pernah, terakhir tanggal");
        ButtonGroup grpMammo = new ButtonGroup();
        grpMammo.add(this.RdoMammo1);
        grpMammo.add(this.RdoMammo2);
        this.TMammoTgl = new TextBox();
        this.TMammoTgl.setPreferredSize(new Dimension(120, 23));
        pMammo.add(this.RdoMammo1);
        pMammo.add(this.RdoMammo2);
        pMammo.add((Component)((Object)this.TMammoTgl));
        pRepro.add(this.wrapRow("mammografi :", pMammo));
        JPanel pSadari = new JPanel(new FlowLayout(0, 2, 0));
        pSadari.setOpaque(false);
        this.RdoSadari1 = this.createRadioButton("Tidak");
        this.RdoSadari2 = this.createRadioButton("Ya");
        ButtonGroup grpSadari = new ButtonGroup();
        grpSadari.add(this.RdoSadari1);
        grpSadari.add(this.RdoSadari2);
        pSadari.add(this.RdoSadari1);
        pSadari.add(this.RdoSadari2);
        pRepro.add(this.wrapRow("SADARI :", pSadari));
        pRepro.add(this.wrapRow("Pria", new JLabel("")));
        JPanel pSkrining = new JPanel(new FlowLayout(0, 2, 0));
        pSkrining.setOpaque(false);
        this.RdoSkrining1 = this.createRadioButton("Tidak pernah");
        this.RdoSkrining2 = this.createRadioButton("Pernah, terakhir tanggal");
        ButtonGroup grpSkrining = new ButtonGroup();
        grpSkrining.add(this.RdoSkrining1);
        grpSkrining.add(this.RdoSkrining2);
        this.TSkriningTgl = new TextBox();
        this.TSkriningTgl.setPreferredSize(new Dimension(120, 23));
        pSkrining.add(this.RdoSkrining1);
        pSkrining.add(this.RdoSkrining2);
        pSkrining.add((Component)((Object)this.TSkriningTgl));
        pRepro.add(this.wrapRow("Skrining prostat :", pSkrining));
        JPanel pMasKepRep1 = new JPanel(new FlowLayout(0, 2, 0));
        pMasKepRep1.setOpaque(false);
        this.ChkMasalahRep1 = this.createCekBox("ASI belum keluar");
        this.ChkMasalahRep2 = this.createCekBox("Resiko perdarahan");
        this.ChkMasalahRep3 = this.createCekBox("Gangguan konsep diri");
        pMasKepRep1.add(this.ChkMasalahRep1);
        pMasKepRep1.add(this.ChkMasalahRep2);
        pMasKepRep1.add(this.ChkMasalahRep3);
        JPanel pMasKepRep2 = new JPanel(new FlowLayout(0, 2, 0));
        pMasKepRep2.setOpaque(false);
        this.ChkMasalahRep4 = this.createCekBox("Lain-lain");
        this.TMasalahRepLain = new TextBox();
        this.TMasalahRepLain.setPreferredSize(new Dimension(200, 23));
        pMasKepRep2.add(this.ChkMasalahRep4);
        pMasKepRep2.add((Component)((Object)this.TMasalahRepLain));
        JPanel pMasKepRepAll = new JPanel();
        pMasKepRepAll.setLayout(new BoxLayout(pMasKepRepAll, 1));
        pMasKepRepAll.setOpaque(false);
        pMasKepRepAll.add(pMasKepRep1);
        pMasKepRepAll.add(pMasKepRep2);
        pRepro.add(this.wrapRow("Masalah keperawatan :", pMasKepRepAll));
        FormInput.add(pRepro);
        panelisi pInteg = new panelisi();
        pInteg.setLayout(new BoxLayout(pInteg, 1));
        pInteg.setBorder(new TitledBorder("INTEGUMEN & MUSKULOSKELETAL"));
        JPanel pIntegRow1 = new JPanel(new FlowLayout(0, 2, 0));
        pIntegRow1.setOpaque(false);
        this.RdoInteg1 = this.createRadioButton("Tidak masalah");
        this.RdoInteg1.setPreferredSize(new Dimension(160, 23));
        this.RdoInteg2 = this.createRadioButton("Rash");
        this.RdoInteg2.setPreferredSize(new Dimension(80, 23));
        this.RdoInteg3 = this.createRadioButton("Lesi");
        this.RdoInteg3.setPreferredSize(new Dimension(80, 23));
        this.RdoInteg4 = this.createRadioButton("Memar");
        this.RdoInteg4.setPreferredSize(new Dimension(80, 23));
        this.RdoInteg5 = this.createRadioButton("Banyak keringat");
        ButtonGroup grpInteg = new ButtonGroup();
        grpInteg.add(this.RdoInteg1);
        grpInteg.add(this.RdoInteg2);
        grpInteg.add(this.RdoInteg3);
        grpInteg.add(this.RdoInteg4);
        grpInteg.add(this.RdoInteg5);
        pIntegRow1.add(this.RdoInteg1);
        pIntegRow1.add(this.RdoInteg2);
        pIntegRow1.add(this.RdoInteg3);
        pIntegRow1.add(this.RdoInteg4);
        pIntegRow1.add(this.RdoInteg5);
        pInteg.add(this.wrapRow("", pIntegRow1));
        JPanel pIntegRow2 = new JPanel(new FlowLayout(0, 2, 0));
        pIntegRow2.setOpaque(false);
        this.RdoKekerasan1 = this.createRadioButton("Indikasi kekerasan fisik");
        this.RdoKekerasan1.setPreferredSize(new Dimension(160, 23));
        this.RdoKekerasan2 = this.createRadioButton("Pucat");
        this.RdoKekerasan2.setPreferredSize(new Dimension(80, 23));
        this.RdoKekerasan3 = this.createRadioButton("Sianosis");
        ButtonGroup grpKekerasan = new ButtonGroup();
        grpKekerasan.add(this.RdoKekerasan1);
        grpKekerasan.add(this.RdoKekerasan2);
        grpKekerasan.add(this.RdoKekerasan3);
        pIntegRow2.add(this.RdoKekerasan1);
        pIntegRow2.add(this.RdoKekerasan2);
        pIntegRow2.add(this.RdoKekerasan3);
        pInteg.add(this.wrapRow("", pIntegRow2));
        JPanel pSplit = new JPanel(new GridLayout(1, 2));
        pSplit.setOpaque(false);
        JPanel pLeft = new JPanel();
        pLeft.setLayout(new BoxLayout(pLeft, 1));
        pLeft.setOpaque(false);
        JPanel pTurgor = new JPanel(new FlowLayout(0, 2, 0));
        pTurgor.setOpaque(false);
        this.RdoTurgor1 = this.createRadioButton("Baik");
        this.RdoTurgor2 = this.createRadioButton("Sedang");
        this.RdoTurgor3 = this.createRadioButton("Buruk");
        ButtonGroup grpTurgor = new ButtonGroup();
        grpTurgor.add(this.RdoTurgor1);
        grpTurgor.add(this.RdoTurgor2);
        grpTurgor.add(this.RdoTurgor3);
        pTurgor.add(this.RdoTurgor1);
        pTurgor.add(this.RdoTurgor2);
        pTurgor.add(this.RdoTurgor3);
        pLeft.add(this.wrapRow("Turgor :", 110, pTurgor));
        JPanel pRambut = new JPanel(new FlowLayout(0, 2, 0));
        pRambut.setOpaque(false);
        this.RdoRambut1 = this.createRadioButton("Bersih");
        this.RdoRambut2 = this.createRadioButton("Kotor");
        ButtonGroup grpRambut = new ButtonGroup();
        grpRambut.add(this.RdoRambut1);
        grpRambut.add(this.RdoRambut2);
        pRambut.add(this.RdoRambut1);
        pRambut.add(this.RdoRambut2);
        pLeft.add(this.wrapRow("Rambut :", 110, pRambut));
        JPanel pKuku = new JPanel(new FlowLayout(0, 2, 0));
        pKuku.setOpaque(false);
        this.RdoKuku1 = this.createRadioButton("Bersih");
        this.RdoKuku2 = this.createRadioButton("Kotor");
        ButtonGroup grpKuku = new ButtonGroup();
        grpKuku.add(this.RdoKuku1);
        grpKuku.add(this.RdoKuku2);
        pKuku.add(this.RdoKuku1);
        pKuku.add(this.RdoKuku2);
        pLeft.add(this.wrapRow("Kuku :", 110, pKuku));
        JPanel pLuka = new JPanel(new FlowLayout(0, 2, 0));
        pLuka.setOpaque(false);
        this.RdoLuka1 = this.createRadioButton("Ya");
        this.RdoLuka2 = this.createRadioButton("Tidak");
        ButtonGroup grpLuka = new ButtonGroup();
        grpLuka.add(this.RdoLuka1);
        grpLuka.add(this.RdoLuka2);
        pLuka.add(this.RdoLuka1);
        pLuka.add(this.RdoLuka2);
        pLeft.add(this.wrapRow("Luka :", 110, pLuka));
        JPanel pKedalaman = new JPanel(new FlowLayout(0, 2, 0));
        pKedalaman.setOpaque(false);
        this.TLukaDalam = new TextBox();
        this.TLukaDalam.setPreferredSize(new Dimension(150, 23));
        pKedalaman.add((Component)((Object)this.TLukaDalam));
        pLeft.add(this.wrapRow("Kedalaman :", 110, pKedalaman));
        JPanel pRight = new JPanel();
        pRight.setLayout(new BoxLayout(pRight, 1));
        pRight.setOpaque(false);
        JPanel pPerdarahan = new JPanel(new FlowLayout(0, 2, 0));
        pPerdarahan.setOpaque(false);
        this.RdoPerdarahan1 = this.createRadioButton("Tidak");
        this.RdoPerdarahan2 = this.createRadioButton("Ya");
        ButtonGroup grpPerdarahan = new ButtonGroup();
        grpPerdarahan.add(this.RdoPerdarahan1);
        grpPerdarahan.add(this.RdoPerdarahan2);
        pPerdarahan.add(this.RdoPerdarahan1);
        pPerdarahan.add(this.RdoPerdarahan2);
        pRight.add(this.wrapRow("Perdarahan :", 110, pPerdarahan));
        JPanel pFraktur = new JPanel(new FlowLayout(0, 2, 0));
        pFraktur.setOpaque(false);
        this.RdoFraktur1 = this.createRadioButton("Tidak");
        this.RdoFraktur2 = this.createRadioButton("Ya");
        this.RdoFraktur3 = this.createRadioButton("Tertutup");
        this.RdoFraktur4 = this.createRadioButton("Terbuka");
        ButtonGroup grpFraktur = new ButtonGroup();
        grpFraktur.add(this.RdoFraktur1);
        grpFraktur.add(this.RdoFraktur2);
        grpFraktur.add(this.RdoFraktur3);
        grpFraktur.add(this.RdoFraktur4);
        pFraktur.add(this.RdoFraktur1);
        pFraktur.add(this.RdoFraktur2);
        pFraktur.add(this.RdoFraktur3);
        pFraktur.add(this.RdoFraktur4);
        pRight.add(this.wrapRow("Fraktur/dislokasi :", 110, pFraktur));
        JPanel pLokasiWrap = new JPanel(new FlowLayout(0, 2, 0));
        pLokasiWrap.setOpaque(false);
        JPanel pLokasiCol = new JPanel();
        pLokasiCol.setLayout(new BoxLayout(pLokasiCol, 1));
        pLokasiCol.setOpaque(false);
        this.RdoLokasi1 = this.createRadioButton("Extremitas atas");
        this.RdoLokasi2 = this.createRadioButton("Extremitas bawah");
        this.RdoLokasi3 = this.createRadioButton("Batang Tubuh");
        JPanel pLok1 = new JPanel(new FlowLayout(0, 0, 0));
        pLok1.setOpaque(false);
        pLok1.add(this.RdoLokasi1);
        JPanel pLok2 = new JPanel(new FlowLayout(0, 0, 0));
        pLok2.setOpaque(false);
        pLok2.add(this.RdoLokasi2);
        JPanel pLok3 = new JPanel(new FlowLayout(0, 0, 0));
        pLok3.setOpaque(false);
        pLok3.add(this.RdoLokasi3);
        JPanel pLokasiLain = new JPanel(new FlowLayout(0, 0, 0));
        pLokasiLain.setOpaque(false);
        this.RdoLokasi4 = this.createRadioButton("Lain-lain");
        this.TLokasiLain = new TextBox();
        this.TLokasiLain.setPreferredSize(new Dimension(150, 23));
        ButtonGroup grpLokasi = new ButtonGroup();
        grpLokasi.add(this.RdoLokasi1);
        grpLokasi.add(this.RdoLokasi2);
        grpLokasi.add(this.RdoLokasi3);
        grpLokasi.add(this.RdoLokasi4);
        pLokasiLain.add(this.RdoLokasi4);
        pLokasiLain.add((Component)((Object)this.TLokasiLain));
        pLokasiCol.add(pLok1);
        pLokasiCol.add(pLok2);
        pLokasiCol.add(pLok3);
        pLokasiCol.add(pLokasiLain);
        pLokasiWrap.add(pLokasiCol);
        pRight.add(this.wrapRow("Lokasi :", 110, pLokasiWrap));
        pLeft.add(Box.createVerticalGlue());
        pRight.add(Box.createVerticalGlue());
        pSplit.add(pLeft);
        pSplit.add(pRight);
        pInteg.add(pSplit);
        JPanel pMKI1 = new JPanel(new FlowLayout(0, 2, 0));
        pMKI1.setOpaque(false);
        this.ChkMasalahInteg1 = this.createCekBox("Gangguan integritas kulit");
        this.ChkMasalahInteg1.setPreferredSize(new Dimension(180, 23));
        this.TMasalahIntegJelas1 = new TextBox();
        this.TMasalahIntegJelas1.setPreferredSize(new Dimension(250, 23));
        pMKI1.add(this.ChkMasalahInteg1);
        pMKI1.add((Component)((Object)this.TMasalahIntegJelas1));
        pInteg.add(this.wrapRow("Masalah keperawatan :", pMKI1));
        JPanel pMKI2 = new JPanel(new FlowLayout(0, 2, 0));
        pMKI2.setOpaque(false);
        this.ChkMasalahInteg2 = this.createCekBox("Risiko infeksi");
        this.ChkMasalahInteg2.setPreferredSize(new Dimension(180, 23));
        this.TMasalahIntegJelas2 = new TextBox();
        this.TMasalahIntegJelas2.setPreferredSize(new Dimension(250, 23));
        pMKI2.add(this.ChkMasalahInteg2);
        pMKI2.add((Component)((Object)this.TMasalahIntegJelas2));
        pInteg.add(this.wrapRow(":", pMKI2));
        JPanel pMKI3 = new JPanel(new FlowLayout(0, 2, 0));
        pMKI3.setOpaque(false);
        this.ChkMasalahInteg3 = this.createCekBox("Gangguan pemenuhan ADL");
        this.ChkMasalahInteg3.setPreferredSize(new Dimension(180, 23));
        this.TMasalahIntegJelas3 = new TextBox();
        this.TMasalahIntegJelas3.setPreferredSize(new Dimension(250, 23));
        pMKI3.add(this.ChkMasalahInteg3);
        pMKI3.add((Component)((Object)this.TMasalahIntegJelas3));
        pInteg.add(this.wrapRow(":", pMKI3));
        JPanel pMKI4 = new JPanel(new FlowLayout(0, 2, 0));
        pMKI4.setOpaque(false);
        this.ChkMasalahInteg4 = this.createCekBox("Gangguan mobilisasi");
        this.ChkMasalahInteg4.setPreferredSize(new Dimension(180, 23));
        this.TMasalahIntegJelas4 = new TextBox();
        this.TMasalahIntegJelas4.setPreferredSize(new Dimension(250, 23));
        pMKI4.add(this.ChkMasalahInteg4);
        pMKI4.add((Component)((Object)this.TMasalahIntegJelas4));
        pInteg.add(this.wrapRow(":", pMKI4));
        FormInput.add(pInteg);
        panelisi pTHT = new panelisi();
        pTHT.setLayout(new BoxLayout(pTHT, 1));
        pTHT.setBorder(new TitledBorder("THT & MATA"));
        JPanel pTelinga = new JPanel(new FlowLayout(0, 2, 0));
        pTelinga.setOpaque(false);
        this.RdoTelinga1 = this.createRadioButton("Normal");
        this.RdoTelinga2 = this.createRadioButton("Alat bantu dengar");
        this.RdoTelinga3 = this.createRadioButton("Lainnya");
        this.TTelingaLainnya = new TextBox();
        this.TTelingaLainnya.setPreferredSize(new Dimension(150, 23));
        ButtonGroup grpTelinga = new ButtonGroup();
        grpTelinga.add(this.RdoTelinga1);
        grpTelinga.add(this.RdoTelinga2);
        grpTelinga.add(this.RdoTelinga3);
        pTelinga.add(this.RdoTelinga1);
        pTelinga.add(this.RdoTelinga2);
        pTelinga.add(this.RdoTelinga3);
        pTelinga.add((Component)((Object)this.TTelingaLainnya));
        pTHT.add(this.wrapRow("Telinga :", pTelinga));
        JPanel pHidung = new JPanel(new FlowLayout(0, 2, 0));
        pHidung.setOpaque(false);
        this.RdoHidung1 = this.createRadioButton("Normal");
        this.RdoHidung2 = this.createRadioButton("Sinusitis");
        this.RdoHidung3 = this.createRadioButton("Polip");
        this.RdoHidung4 = this.createRadioButton("Epistaksis");
        this.RdoHidung5 = this.createRadioButton("Lainnya");
        ButtonGroup grpHidung = new ButtonGroup();
        grpHidung.add(this.RdoHidung1);
        grpHidung.add(this.RdoHidung2);
        grpHidung.add(this.RdoHidung3);
        grpHidung.add(this.RdoHidung4);
        grpHidung.add(this.RdoHidung5);
        pHidung.add(this.RdoHidung1);
        pHidung.add(this.RdoHidung2);
        pHidung.add(this.RdoHidung3);
        pHidung.add(this.RdoHidung4);
        pHidung.add(this.RdoHidung5);
        pTHT.add(this.wrapRow("Hidung :", pHidung));
        JPanel pTenggorokan = new JPanel(new FlowLayout(0, 2, 0));
        pTenggorokan.setOpaque(false);
        this.RdoTenggorokan1 = this.createRadioButton("Normal");
        this.RdoTenggorokan2 = this.createRadioButton("Nyeri telan");
        this.RdoTenggorokan3 = this.createRadioButton("Tonsilitis");
        ButtonGroup grpTenggorokan = new ButtonGroup();
        grpTenggorokan.add(this.RdoTenggorokan1);
        grpTenggorokan.add(this.RdoTenggorokan2);
        grpTenggorokan.add(this.RdoTenggorokan3);
        pTenggorokan.add(this.RdoTenggorokan1);
        pTenggorokan.add(this.RdoTenggorokan2);
        pTenggorokan.add(this.RdoTenggorokan3);
        pTHT.add(this.wrapRow("Tenggorokan :", pTenggorokan));
        JPanel pGigi = new JPanel(new FlowLayout(0, 2, 0));
        pGigi.setOpaque(false);
        Label lblGigi = this.createLabel("Gigi :");
        lblGigi.setPreferredSize(new Dimension(50, 23));
        pGigi.add((Component)((Object)lblGigi));
        this.RdoGigi1 = this.createRadioButton("Bersih");
        this.RdoGigi2 = this.createRadioButton("Karies");
        this.RdoGigi3 = this.createRadioButton("Karang gigi");
        this.RdoGigi4 = this.createRadioButton("Kotor");
        this.RdoGigi5 = this.createRadioButton("Ompong");
        this.RdoGigi6 = this.createRadioButton("Lengkap");
        ButtonGroup grpGigi = new ButtonGroup();
        grpGigi.add(this.RdoGigi1);
        grpGigi.add(this.RdoGigi2);
        grpGigi.add(this.RdoGigi3);
        grpGigi.add(this.RdoGigi4);
        grpGigi.add(this.RdoGigi5);
        grpGigi.add(this.RdoGigi6);
        pGigi.add(this.RdoGigi1);
        pGigi.add(this.RdoGigi2);
        pGigi.add(this.RdoGigi3);
        pGigi.add(this.RdoGigi4);
        pGigi.add(this.RdoGigi5);
        pGigi.add(this.RdoGigi6);
        pTHT.add(this.wrapRow("Mulut :", pGigi));
        JPanel pSakitGigi = new JPanel(new FlowLayout(0, 2, 0));
        pSakitGigi.setOpaque(false);
        Label lblSakit = this.createLabel("Sakit gigi :");
        lblSakit.setPreferredSize(new Dimension(80, 23));
        pSakitGigi.add((Component)((Object)lblSakit));
        this.RdoSakitGigi1 = this.createRadioButton("Ya");
        this.RdoSakitGigi2 = this.createRadioButton("Tidak");
        ButtonGroup grpSakitGigi = new ButtonGroup();
        grpSakitGigi.add(this.RdoSakitGigi1);
        grpSakitGigi.add(this.RdoSakitGigi2);
        pSakitGigi.add(this.RdoSakitGigi1);
        pSakitGigi.add(this.RdoSakitGigi2);
        Label lblGigiPalsu = this.createLabel("Gigi palsu :");
        lblGigiPalsu.setPreferredSize(new Dimension(80, 23));
        pSakitGigi.add((Component)((Object)lblGigiPalsu));
        this.RdoGigiPalsu1 = this.createRadioButton("Ya");
        this.RdoGigiPalsu2 = this.createRadioButton("Tidak");
        ButtonGroup grpGigiPalsu = new ButtonGroup();
        grpGigiPalsu.add(this.RdoGigiPalsu1);
        grpGigiPalsu.add(this.RdoGigiPalsu2);
        pSakitGigi.add(this.RdoGigiPalsu1);
        pSakitGigi.add(this.RdoGigiPalsu2);
        pTHT.add(this.wrapRow("", pSakitGigi));
        JPanel pMataRow1 = new JPanel(new FlowLayout(0, 2, 0));
        pMataRow1.setOpaque(false);
        this.RdoMata1 = this.createRadioButton("Normal");
        this.RdoMata2 = this.createRadioButton("Kering");
        this.RdoMata3 = this.createRadioButton("Buta");
        this.RdoMata4 = this.createRadioButton("Katarak");
        this.RdoMata5 = this.createRadioButton("Glaukoma");
        pMataRow1.add(this.RdoMata1);
        pMataRow1.add(this.RdoMata2);
        pMataRow1.add(this.RdoMata3);
        pMataRow1.add(this.RdoMata4);
        pMataRow1.add(this.RdoMata5);
        pTHT.add(this.wrapRow("Mata :", pMataRow1));
        JPanel pMataRow2 = new JPanel(new FlowLayout(0, 2, 0));
        pMataRow2.setOpaque(false);
        this.RdoMata6 = this.createRadioButton("Rabun Jauh");
        this.RdoMata7 = this.createRadioButton("Rabun dekat");
        this.RdoMata8 = this.createRadioButton("Konjungtivitis");
        this.RdoMata9 = this.createRadioButton("Kaca mata");
        this.RdoMata10 = this.createRadioButton("Lainnya");
        this.TMataLainnya = new TextBox();
        this.TMataLainnya.setPreferredSize(new Dimension(150, 23));
        ButtonGroup grpMata = new ButtonGroup();
        grpMata.add(this.RdoMata1);
        grpMata.add(this.RdoMata2);
        grpMata.add(this.RdoMata3);
        grpMata.add(this.RdoMata4);
        grpMata.add(this.RdoMata5);
        grpMata.add(this.RdoMata6);
        grpMata.add(this.RdoMata7);
        grpMata.add(this.RdoMata8);
        grpMata.add(this.RdoMata9);
        grpMata.add(this.RdoMata10);
        pMataRow2.add(this.RdoMata6);
        pMataRow2.add(this.RdoMata7);
        pMataRow2.add(this.RdoMata8);
        pMataRow2.add(this.RdoMata9);
        pMataRow2.add(this.RdoMata10);
        pMataRow2.add((Component)((Object)this.TMataLainnya));
        pTHT.add(this.wrapRow("", pMataRow2));
        this.ChkMasalahTHTMata1 = this.createCekBox("Gangguan persepsi sensori");
        pTHT.add(this.wrapRow("Masalah keperawatan :", this.ChkMasalahTHTMata1));
        FormInput.add(pTHT);
        panelisi pCerna = new panelisi();
        pCerna.setLayout(new BoxLayout(pCerna, 1));
        pCerna.setBorder(new TitledBorder("PENCERNAAN"));
        JPanel pWasirRow = new JPanel(new FlowLayout(0, 2, 0));
        pWasirRow.setOpaque(false);
        this.RdoWasir1 = this.createRadioButton("Ya");
        this.RdoWasir2 = this.createRadioButton("Tidak");
        ButtonGroup grpWasir = new ButtonGroup();
        grpWasir.add(this.RdoWasir1);
        grpWasir.add(this.RdoWasir2);
        pWasirRow.add(this.RdoWasir1);
        pWasirRow.add(this.RdoWasir2);
        Label lblPerdarahan = this.createLabel("Perdarahan rectal :");
        lblPerdarahan.setPreferredSize(new Dimension(130, 23));
        pWasirRow.add((Component)((Object)lblPerdarahan));
        this.RdoPerdarahanRectal1 = this.createRadioButton("Ya");
        this.RdoPerdarahanRectal2 = this.createRadioButton("Tidak");
        ButtonGroup grpPerdarahanRectal = new ButtonGroup();
        grpPerdarahanRectal.add(this.RdoPerdarahanRectal1);
        grpPerdarahanRectal.add(this.RdoPerdarahanRectal2);
        pWasirRow.add(this.RdoPerdarahanRectal1);
        pWasirRow.add(this.RdoPerdarahanRectal2);
        pCerna.add(this.wrapRow("Wasir :", pWasirRow));
        JPanel pDiitRow = new JPanel(new FlowLayout(0, 2, 0));
        pDiitRow.setOpaque(false);
        this.TJenisDiit = new TextBox();
        this.TJenisDiit.setPreferredSize(new Dimension(150, 23));
        pDiitRow.add((Component)((Object)this.TJenisDiit));
        Label lblFeeding = this.createLabel("Feeding Tube :");
        lblFeeding.setPreferredSize(new Dimension(100, 23));
        pDiitRow.add((Component)((Object)lblFeeding));
        this.TFeedingTube = new TextBox();
        this.TFeedingTube.setPreferredSize(new Dimension(150, 23));
        pDiitRow.add((Component)((Object)this.TFeedingTube));
        pCerna.add(this.wrapRow("Jenis diit :", pDiitRow));
        JPanel pPembatasan = new JPanel(new FlowLayout(0, 2, 0));
        pPembatasan.setOpaque(false);
        this.RdoPembatasanCairan1 = this.createRadioButton("Ya");
        this.RdoPembatasanCairan2 = this.createRadioButton("Tidak");
        ButtonGroup grpPembatasan = new ButtonGroup();
        grpPembatasan.add(this.RdoPembatasanCairan1);
        grpPembatasan.add(this.RdoPembatasanCairan2);
        pPembatasan.add(this.RdoPembatasanCairan1);
        pPembatasan.add(this.RdoPembatasanCairan2);
        pCerna.add(this.wrapRow("Pembatasan cairan :", pPembatasan));
        JPanel pAbdomen = new JPanel(new FlowLayout(0, 2, 0));
        pAbdomen.setOpaque(false);
        this.RdoAbdomen1 = this.createRadioButton("Supel");
        this.RdoAbdomen2 = this.createRadioButton("Distensi");
        this.RdoAbdomen3 = this.createRadioButton("Kembung");
        ButtonGroup grpAbdomen = new ButtonGroup();
        grpAbdomen.add(this.RdoAbdomen1);
        grpAbdomen.add(this.RdoAbdomen2);
        grpAbdomen.add(this.RdoAbdomen3);
        pAbdomen.add(this.RdoAbdomen1);
        pAbdomen.add(this.RdoAbdomen2);
        pAbdomen.add(this.RdoAbdomen3);
        pCerna.add(this.wrapRow("Abdomen :", pAbdomen));
        JPanel pBunyiUsus = new JPanel(new FlowLayout(0, 2, 0));
        pBunyiUsus.setOpaque(false);
        this.RdoBunyiUsus1 = this.createRadioButton("Normal");
        this.RdoBunyiUsus2 = this.createRadioButton("Tidak ada");
        this.RdoBunyiUsus3 = this.createRadioButton("Frekuensi");
        this.TBunyiUsusFreq = new TextBox();
        this.TBunyiUsusFreq.setPreferredSize(new Dimension(80, 23));
        Label lblXMenit = this.createLabel("x/menit");
        lblXMenit.setPreferredSize(new Dimension(50, 23));
        ButtonGroup grpBunyiUsus = new ButtonGroup();
        grpBunyiUsus.add(this.RdoBunyiUsus1);
        grpBunyiUsus.add(this.RdoBunyiUsus2);
        grpBunyiUsus.add(this.RdoBunyiUsus3);
        pBunyiUsus.add(this.RdoBunyiUsus1);
        pBunyiUsus.add(this.RdoBunyiUsus2);
        pBunyiUsus.add(this.RdoBunyiUsus3);
        pBunyiUsus.add((Component)((Object)this.TBunyiUsusFreq));
        pBunyiUsus.add((Component)((Object)lblXMenit));
        pCerna.add(this.wrapRow("Bunyi usus :", pBunyiUsus));
        JPanel pBAB = new JPanel(new FlowLayout(0, 2, 0));
        pBAB.setOpaque(false);
        this.RdoBAB1 = this.createRadioButton("Normal");
        this.RdoBAB2 = this.createRadioButton("Diare, sejak :");
        this.RdoBAB3 = this.createRadioButton("Frekwensi");
        this.TBABDiareSejak = new TextBox();
        this.TBABDiareSejak.setPreferredSize(new Dimension(80, 23));
        this.TBABFreq = new TextBox();
        this.TBABFreq.setPreferredSize(new Dimension(80, 23));
        Label lblXHari = this.createLabel("x/hari");
        lblXHari.setPreferredSize(new Dimension(50, 23));
        ButtonGroup grpBAB = new ButtonGroup();
        grpBAB.add(this.RdoBAB1);
        grpBAB.add(this.RdoBAB2);
        grpBAB.add(this.RdoBAB3);
        pBAB.add(this.RdoBAB1);
        pBAB.add(this.RdoBAB2);
        pBAB.add((Component)((Object)this.TBABDiareSejak));
        pBAB.add(this.RdoBAB3);
        pBAB.add((Component)((Object)this.TBABFreq));
        pBAB.add((Component)((Object)lblXHari));
        pCerna.add(this.wrapRow("BAB :", pBAB));
        JPanel pKonsistensi = new JPanel(new FlowLayout(0, 2, 0));
        pKonsistensi.setOpaque(false);
        this.RdoKonsistensi1 = this.createRadioButton("Padat");
        this.RdoKonsistensi2 = this.createRadioButton("Cair");
        this.RdoKonsistensi3 = this.createRadioButton("Lembek");
        this.RdoKonsistensi4 = this.createRadioButton("Berlendir");
        ButtonGroup grpKonsistensi = new ButtonGroup();
        grpKonsistensi.add(this.RdoKonsistensi1);
        grpKonsistensi.add(this.RdoKonsistensi2);
        grpKonsistensi.add(this.RdoKonsistensi3);
        grpKonsistensi.add(this.RdoKonsistensi4);
        pKonsistensi.add(this.RdoKonsistensi1);
        pKonsistensi.add(this.RdoKonsistensi2);
        pKonsistensi.add(this.RdoKonsistensi3);
        pKonsistensi.add(this.RdoKonsistensi4);
        pCerna.add(this.wrapRow("Konsistensi :", pKonsistensi));
        JPanel pWarnaCerna = new JPanel(new FlowLayout(0, 2, 0));
        pWarnaCerna.setOpaque(false);
        this.TWarnaCerna = new TextBox();
        this.TWarnaCerna.setPreferredSize(new Dimension(200, 23));
        pWarnaCerna.add((Component)((Object)this.TWarnaCerna));
        pCerna.add(this.wrapRow("Warna :", pWarnaCerna));
        JPanel pPencahar = new JPanel(new FlowLayout(0, 2, 0));
        pPencahar.setOpaque(false);
        this.RdoPencahar1 = this.createRadioButton("Ya");
        this.RdoPencahar2 = this.createRadioButton("Tidak");
        ButtonGroup grpPencahar = new ButtonGroup();
        grpPencahar.add(this.RdoPencahar1);
        grpPencahar.add(this.RdoPencahar2);
        pPencahar.add(this.RdoPencahar1);
        pPencahar.add(this.RdoPencahar2);
        pCerna.add(this.wrapRow("Penggunaan pencahar :", pPencahar));
        this.ChkMasalahPencernaan1 = this.createCekBox("Risiko/Gangguan pola eliminasi");
        pCerna.add(this.wrapRow("Masalah keperawatan :", this.ChkMasalahPencernaan1));
        FormInput.add(pCerna);
        panelisi pAssesmenNyeri = new panelisi();
        pAssesmenNyeri.setLayout(new BoxLayout(pAssesmenNyeri, 1));
        pAssesmenNyeri.setBorder(new TitledBorder("ASSESMEN NYERI DAN ASSESMEN ULANG NYERI"));
        this.RdoNyeriTidakAda = this.createRadioButton("Tidak ada nyeri");
        this.RdoNyeriAda = this.createRadioButton("Nyeri");
        this.RdoNyeriAkut = this.createRadioButton("Akut");
        this.RdoNyeriKronis = this.createRadioButton("Kronis");
        this.RdoNyeriViseral = this.createRadioButton("Viseral");
        this.RdoNyeriSomatis = this.createRadioButton("Somatis");
        ButtonGroup grpStatusNyeri = new ButtonGroup();
        grpStatusNyeri.add(this.RdoNyeriTidakAda);
        grpStatusNyeri.add(this.RdoNyeriAda);
        grpStatusNyeri.add(this.RdoNyeriAkut);
        grpStatusNyeri.add(this.RdoNyeriKronis);
        grpStatusNyeri.add(this.RdoNyeriViseral);
        grpStatusNyeri.add(this.RdoNyeriSomatis);
        JPanel pStatusNyeri = new JPanel(new FlowLayout(0, 2, 0));
        pStatusNyeri.setOpaque(false);
        pStatusNyeri.add(this.RdoNyeriTidakAda);
        pStatusNyeri.add(this.RdoNyeriAda);
        pStatusNyeri.add(this.RdoNyeriAkut);
        pStatusNyeri.add(this.RdoNyeriKronis);
        pStatusNyeri.add(this.RdoNyeriViseral);
        pStatusNyeri.add(this.RdoNyeriSomatis);
        pAssesmenNyeri.add(this.wrapRow("Status Nyeri :", pStatusNyeri));
        this.RdoProvokesDiam = this.createRadioButton("Diam");
        this.RdoProvokesMobilisasi = this.createRadioButton("Mobilisasi");
        this.RdoProvokesDitekan = this.createRadioButton("Ditekan");
        this.RdoProvokesTiduran = this.createRadioButton("Tiduran");
        this.RdoProvokesBerdiri = this.createRadioButton("Berdiri");
        this.RdoProvokesBerjalan = this.createRadioButton("Berjalan");
        ButtonGroup grpProvokes = new ButtonGroup();
        grpProvokes.add(this.RdoProvokesDiam);
        grpProvokes.add(this.RdoProvokesMobilisasi);
        grpProvokes.add(this.RdoProvokesDitekan);
        grpProvokes.add(this.RdoProvokesTiduran);
        grpProvokes.add(this.RdoProvokesBerdiri);
        grpProvokes.add(this.RdoProvokesBerjalan);
        this.TProvokesLainnya = new TextBox();
        this.TProvokesLainnya.setPreferredSize(new Dimension(150, 23));
        JPanel pProvokes = new JPanel(new FlowLayout(0, 2, 0));
        pProvokes.setOpaque(false);
        pProvokes.add(this.RdoProvokesDiam);
        pProvokes.add(this.RdoProvokesMobilisasi);
        pProvokes.add(this.RdoProvokesDitekan);
        pProvokes.add(this.RdoProvokesTiduran);
        pProvokes.add(this.RdoProvokesBerdiri);
        pProvokes.add(this.RdoProvokesBerjalan);
        pProvokes.add((Component)((Object)this.createLabel(" Lainnya: ")));
        pProvokes.add((Component)((Object)this.TProvokesLainnya));
        pAssesmenNyeri.add(this.wrapRow("Provokes (Pencetus) :", pProvokes));
        this.RdoQualityTajam = this.createRadioButton("Tajam");
        this.RdoQualityTumpul = this.createRadioButton("Tumpul");
        this.RdoQualityDitusuk = this.createRadioButton("Seperti ditusuk");
        this.RdoQualityDitarik = this.createRadioButton("Seperti ditarik");
        this.RdoQualityDipukul = this.createRadioButton("Seperti dipukul");
        this.RdoQualityBerdenyut = this.createRadioButton("Berdenyut");
        this.RdoQualityDibakar = this.createRadioButton("Seperti dibakar");
        this.RdoQualityDitikam = this.createRadioButton("Seperti ditikam");
        this.RdoQualityDisayat = this.createRadioButton("Seperti disayat");
        ButtonGroup grpQuality = new ButtonGroup();
        grpQuality.add(this.RdoQualityTajam);
        grpQuality.add(this.RdoQualityTumpul);
        grpQuality.add(this.RdoQualityDitusuk);
        grpQuality.add(this.RdoQualityDitarik);
        grpQuality.add(this.RdoQualityDipukul);
        grpQuality.add(this.RdoQualityBerdenyut);
        grpQuality.add(this.RdoQualityDibakar);
        grpQuality.add(this.RdoQualityDitikam);
        grpQuality.add(this.RdoQualityDisayat);
        this.TQualityLainnya = new TextBox();
        this.TQualityLainnya.setPreferredSize(new Dimension(150, 23));
        JPanel pQuality1 = new JPanel(new FlowLayout(0, 2, 0));
        pQuality1.setOpaque(false);
        pQuality1.add(this.RdoQualityTajam);
        pQuality1.add(this.RdoQualityTumpul);
        pQuality1.add(this.RdoQualityDitusuk);
        pQuality1.add(this.RdoQualityDitarik);
        pQuality1.add(this.RdoQualityDipukul);
        pAssesmenNyeri.add(this.wrapRow("Quality (Kualitas) :", pQuality1));
        JPanel pQuality2 = new JPanel(new FlowLayout(0, 2, 0));
        pQuality2.setOpaque(false);
        pQuality2.add(this.RdoQualityBerdenyut);
        pQuality2.add(this.RdoQualityDibakar);
        pQuality2.add(this.RdoQualityDitikam);
        pQuality2.add(this.RdoQualityDisayat);
        pQuality2.add((Component)((Object)this.createLabel(" Lainnya: ")));
        pQuality2.add((Component)((Object)this.TQualityLainnya));
        pAssesmenNyeri.add(this.wrapRow("", pQuality2));
        this.CmbRadiation = new ComboBox();
        this.CmbRadiation.addItem("-");
        this.CmbRadiation.addItem("Menyebarkan");
        this.CmbRadiation.addItem("Menetap");
        this.TRadiationLokasi = new TextBox();
        this.TRadiationLokasi.setPreferredSize(new Dimension(200, 23));
        JPanel pRadiation = new JPanel(new FlowLayout(0, 2, 0));
        pRadiation.setOpaque(false);
        pRadiation.add(this.CmbRadiation);
        pRadiation.add((Component)((Object)this.createLabel(" Lokasi: ")));
        pRadiation.add((Component)((Object)this.TRadiationLokasi));
        pAssesmenNyeri.add(this.wrapRow("Radiation (Penyebaran) :", pRadiation));
        this.CmbSeverityMetode = new ComboBox();
        this.CmbSeverityMetode.addItem("-");
        this.CmbSeverityMetode.addItem("Numerik");
        this.CmbSeverityMetode.addItem("WONG BAKER");
        this.CmbSeverityMetode.addItem("NIPS");
        this.CmbSeverityMetode.addItem("CPOT");
        this.TSeveritySkor = new TextBox();
        this.TSeveritySkor.setPreferredSize(new Dimension(50, 23));
        this.TSeverityNyeri = new TextBox();
        this.TSeverityNyeri.setPreferredSize(new Dimension(200, 23));
        JPanel pSeverity = new JPanel(new FlowLayout(0, 2, 0));
        pSeverity.setOpaque(false);
        pSeverity.add((Component)((Object)this.createLabel("Metode: ")));
        pSeverity.add(this.CmbSeverityMetode);
        pSeverity.add((Component)((Object)this.createLabel(" Derajat SKOR: ")));
        pSeverity.add((Component)((Object)this.TSeveritySkor));
        pSeverity.add((Component)((Object)this.createLabel(" Nyeri: ")));
        pSeverity.add((Component)((Object)this.TSeverityNyeri));
        pAssesmenNyeri.add(this.wrapRow("Severity (Intensitas) :", pSeverity));
        this.CmbTimeSetiap = new ComboBox();
        this.CmbTimeSetiap.addItem("-");
        this.CmbTimeSetiap.addItem("1-2 jam");
        this.CmbTimeSetiap.addItem("3-4 jam");
        this.CmbTimeSelama = new ComboBox();
        this.CmbTimeSelama.addItem("-");
        this.CmbTimeSelama.addItem("< 30 menit");
        this.CmbTimeSelama.addItem("> 30 menit");
        this.TTimeSejak = new TextBox();
        this.TTimeSejak.setPreferredSize(new Dimension(200, 23));
        JPanel pTime = new JPanel(new FlowLayout(0, 2, 0));
        pTime.setOpaque(false);
        pTime.add((Component)((Object)this.createLabel("Setiap: ")));
        pTime.add(this.CmbTimeSetiap);
        pTime.add((Component)((Object)this.createLabel(" selama: ")));
        pTime.add(this.CmbTimeSelama);
        pTime.add((Component)((Object)this.createLabel(" sejak: ")));
        pTime.add((Component)((Object)this.TTimeSejak));
        pAssesmenNyeri.add(this.wrapRow("Time (Waktu) :", pTime));
        JLabel lblSkalaNyeri = new JLabel();
        lblSkalaNyeri.setIcon(new ImageIcon(this.getClass().getResource("/picture/skala_nyeri.png")));
        pAssesmenNyeri.add(this.wrapRow("METODE PENILAIAN INTENSITAS / DERAJAT NYERI :", new JLabel()));
        JPanel pSkorImage = new JPanel(null);
        pSkorImage.setOpaque(false);
        pSkorImage.setPreferredSize(new Dimension(1021, 520));
        pSkorImage.setMinimumSize(new Dimension(1021, 520));
        pSkorImage.setMaximumSize(new Dimension(1021, 520));
        lblSkalaNyeri.setBounds(0, 0, 1021, 492);
        pSkorImage.add(lblSkalaNyeri);
        this.RdoSkor0 = this.createRadioButton("");
        this.RdoSkor1 = this.createRadioButton("");
        this.RdoSkor2 = this.createRadioButton("");
        this.RdoSkor3 = this.createRadioButton("");
        this.RdoSkor4 = this.createRadioButton("");
        this.RdoSkor5 = this.createRadioButton("");
        this.RdoSkor6 = this.createRadioButton("");
        this.RdoSkor7 = this.createRadioButton("");
        this.RdoSkor8 = this.createRadioButton("");
        this.RdoSkor9 = this.createRadioButton("");
        this.RdoSkor10 = this.createRadioButton("");
        ButtonGroup grpSkor = new ButtonGroup();
        grpSkor.add(this.RdoSkor0);
        grpSkor.add(this.RdoSkor1);
        grpSkor.add(this.RdoSkor2);
        grpSkor.add(this.RdoSkor3);
        grpSkor.add(this.RdoSkor4);
        grpSkor.add(this.RdoSkor5);
        grpSkor.add(this.RdoSkor6);
        grpSkor.add(this.RdoSkor7);
        grpSkor.add(this.RdoSkor8);
        grpSkor.add(this.RdoSkor9);
        grpSkor.add(this.RdoSkor10);
        ItemListener ilSkor = e -> {
            if (this.RdoSkor0.isSelected()) {
                this.TSeveritySkor.setText("0");
                this.RdoWb0.setSelected(true);
            } else if (this.RdoSkor1.isSelected()) {
                this.TSeveritySkor.setText("1");
                this.RdoWb0.setSelected(false);
                this.RdoWb2.setSelected(false);
                this.RdoWb4.setSelected(false);
                this.RdoWb6.setSelected(false);
                this.RdoWb8.setSelected(false);
                this.RdoWb10.setSelected(false);
            } else if (this.RdoSkor2.isSelected()) {
                this.TSeveritySkor.setText("2");
                this.RdoWb2.setSelected(true);
            } else if (this.RdoSkor3.isSelected()) {
                this.TSeveritySkor.setText("3");
                this.RdoWb0.setSelected(false);
                this.RdoWb2.setSelected(false);
                this.RdoWb4.setSelected(false);
                this.RdoWb6.setSelected(false);
                this.RdoWb8.setSelected(false);
                this.RdoWb10.setSelected(false);
            } else if (this.RdoSkor4.isSelected()) {
                this.TSeveritySkor.setText("4");
                this.RdoWb4.setSelected(true);
            } else if (this.RdoSkor5.isSelected()) {
                this.TSeveritySkor.setText("5");
                this.RdoWb0.setSelected(false);
                this.RdoWb2.setSelected(false);
                this.RdoWb4.setSelected(false);
                this.RdoWb6.setSelected(false);
                this.RdoWb8.setSelected(false);
                this.RdoWb10.setSelected(false);
            } else if (this.RdoSkor6.isSelected()) {
                this.TSeveritySkor.setText("6");
                this.RdoWb6.setSelected(true);
            } else if (this.RdoSkor7.isSelected()) {
                this.TSeveritySkor.setText("7");
                this.RdoWb0.setSelected(false);
                this.RdoWb2.setSelected(false);
                this.RdoWb4.setSelected(false);
                this.RdoWb6.setSelected(false);
                this.RdoWb8.setSelected(false);
                this.RdoWb10.setSelected(false);
            } else if (this.RdoSkor8.isSelected()) {
                this.TSeveritySkor.setText("8");
                this.RdoWb8.setSelected(true);
            } else if (this.RdoSkor9.isSelected()) {
                this.TSeveritySkor.setText("9");
                this.RdoWb0.setSelected(false);
                this.RdoWb2.setSelected(false);
                this.RdoWb4.setSelected(false);
                this.RdoWb6.setSelected(false);
                this.RdoWb8.setSelected(false);
                this.RdoWb10.setSelected(false);
            } else if (this.RdoSkor10.isSelected()) {
                this.TSeveritySkor.setText("10");
                this.RdoWb10.setSelected(true);
            }
        };
        this.RdoSkor0.addItemListener(ilSkor);
        this.RdoSkor1.addItemListener(ilSkor);
        this.RdoSkor2.addItemListener(ilSkor);
        this.RdoSkor3.addItemListener(ilSkor);
        this.RdoSkor4.addItemListener(ilSkor);
        this.RdoSkor5.addItemListener(ilSkor);
        this.RdoSkor6.addItemListener(ilSkor);
        this.RdoSkor7.addItemListener(ilSkor);
        this.RdoSkor8.addItemListener(ilSkor);
        this.RdoSkor9.addItemListener(ilSkor);
        this.RdoSkor10.addItemListener(ilSkor);
        int[] xs = new int[]{65, 153, 241, 329, 417, 505, 594, 682, 770, 858, 946};
        RadioButton[] rdos = new RadioButton[]{this.RdoSkor0, this.RdoSkor1, this.RdoSkor2, this.RdoSkor3, this.RdoSkor4, this.RdoSkor5, this.RdoSkor6, this.RdoSkor7, this.RdoSkor8, this.RdoSkor9, this.RdoSkor10};
        for (int i3 = 0; i3 < 11; ++i3) {
            rdos[i3].setOpaque(false);
            rdos[i3].setBounds(xs[i3] - 10, 492, 20, 20);
            pSkorImage.add(rdos[i3]);
        }
        this.RdoWb0 = this.createRadioButton("");
        this.RdoWb2 = this.createRadioButton("");
        this.RdoWb4 = this.createRadioButton("");
        this.RdoWb6 = this.createRadioButton("");
        this.RdoWb8 = this.createRadioButton("");
        this.RdoWb10 = this.createRadioButton("");
        ButtonGroup grpSkorWb = new ButtonGroup();
        grpSkorWb.add(this.RdoWb0);
        grpSkorWb.add(this.RdoWb2);
        grpSkorWb.add(this.RdoWb4);
        grpSkorWb.add(this.RdoWb6);
        grpSkorWb.add(this.RdoWb8);
        grpSkorWb.add(this.RdoWb10);
        ItemListener ilSkorWb = e -> {
            if (this.RdoWb0.isSelected()) {
                this.TSeveritySkor.setText("0");
                this.RdoSkor0.setSelected(true);
            } else if (this.RdoWb2.isSelected()) {
                this.TSeveritySkor.setText("2");
                this.RdoSkor2.setSelected(true);
            } else if (this.RdoWb4.isSelected()) {
                this.TSeveritySkor.setText("4");
                this.RdoSkor4.setSelected(true);
            } else if (this.RdoWb6.isSelected()) {
                this.TSeveritySkor.setText("6");
                this.RdoSkor6.setSelected(true);
            } else if (this.RdoWb8.isSelected()) {
                this.TSeveritySkor.setText("8");
                this.RdoSkor8.setSelected(true);
            } else if (this.RdoWb10.isSelected()) {
                this.TSeveritySkor.setText("10");
                this.RdoSkor10.setSelected(true);
            }
        };
        this.RdoWb0.addItemListener(ilSkorWb);
        this.RdoWb2.addItemListener(ilSkorWb);
        this.RdoWb4.addItemListener(ilSkorWb);
        this.RdoWb6.addItemListener(ilSkorWb);
        this.RdoWb8.addItemListener(ilSkorWb);
        this.RdoWb10.addItemListener(ilSkorWb);
        int[] xsWb = new int[]{92, 258, 424, 590, 756, 922};
        RadioButton[] rdosWb = new RadioButton[]{this.RdoWb0, this.RdoWb2, this.RdoWb4, this.RdoWb6, this.RdoWb8, this.RdoWb10};
        for (int i4 = 0; i4 < 6; ++i4) {
            rdosWb[i4].setOpaque(false);
            rdosWb[i4].setBounds(xsWb[i4] - 10, 250, 20, 20);
            pSkorImage.add(rdosWb[i4]);
        }
        pAssesmenNyeri.add(this.wrapRow("", pSkorImage));
        FormInput.add(pAssesmenNyeri);
        panelisi pCpot = new panelisi();
        pCpot.setLayout(new BoxLayout(pCpot, 1));
        pCpot.setBorder(new TitledBorder("CPOT (Critical Care Pain Observation Tool)"));
        this.CmbCpotEkspresi = new ComboBox();
        this.CmbCpotEkspresi.addItem("0 - Tidak ada ketegangan otot, rileks netral");
        this.CmbCpotEkspresi.addItem("1 - Alis mengernyit, mengerutkan kening, menyeringai tegang");
        this.CmbCpotEkspresi.addItem("2 - Meringis, menutup mata, menggigit selang ETT");
        this.CmbCpotGerakan = new ComboBox();
        this.CmbCpotGerakan.addItem("0 - Tidak ada gerakan abnormal");
        this.CmbCpotGerakan.addItem("1 - Gerakan hati-hati menyentuh lokasi nyeri, mencari perhatian melalui gerakan");
        this.CmbCpotGerakan.addItem("2 - Mencabut ETT, gelisah, mencoba untuk duduk, tidak mengikuti perintah, mengamuk, mencoba keluar tempat tidur");
        this.CmbCpotKetegangan = new ComboBox();
        this.CmbCpotKetegangan.addItem("0 - Tidak ada ketegangan otot, relaks");
        this.CmbCpotKetegangan.addItem("1 - Tegang, kaku");
        this.CmbCpotKetegangan.addItem("2 - Sangat tegang, kaku");
        this.CmbCpotVentilator = new ComboBox();
        this.CmbCpotVentilator.addItem("-");
        this.CmbCpotVentilator.addItem("0 - Alarm tidak berbunyi, pasien kooperatif terhadap kerja ventilator");
        this.CmbCpotVentilator.addItem("1 - Alarm berbunyi tapi berhenti secara spontan, alarm aktif tapi mati sendiri");
        this.CmbCpotVentilator.addItem("2 - Alarm sering berbunyi, alarm selalu sendiri");
        this.CmbCpotVokalisasi = new ComboBox();
        this.CmbCpotVokalisasi.addItem("-");
        this.CmbCpotVokalisasi.addItem("0 - Berbicara dalam nada normal, tidak ada suara");
        this.CmbCpotVokalisasi.addItem("1 - Mendesah, mengerang");
        this.CmbCpotVokalisasi.addItem("2 - Menangis, berteriak");
        this.TCpotTotal = new TextBox();
        this.TCpotTotal.setPreferredSize(new Dimension(50, 23));
        this.TCpotTotal.setEditable(false);
        this.TCpotTotal.setText("0");
        this.TCpotKategori = new TextBox();
        this.TCpotKategori.setPreferredSize(new Dimension(200, 23));
        this.TCpotKategori.setEditable(false);
        this.TCpotKategori.setText("Tidak Nyeri");
        ItemListener ilCpotTotal = e -> {
            int total = 0;
            total += this.CmbCpotEkspresi.getSelectedIndex();
            total += this.CmbCpotGerakan.getSelectedIndex();
            total += this.CmbCpotKetegangan.getSelectedIndex();
            if (this.CmbCpotVentilator.getSelectedIndex() > 0) {
                total += this.CmbCpotVentilator.getSelectedIndex() - 1;
            }
            if (this.CmbCpotVokalisasi.getSelectedIndex() > 0) {
                total += this.CmbCpotVokalisasi.getSelectedIndex() - 1;
            }
            this.TCpotTotal.setText(String.valueOf(total));
            if (total == 0) {
                this.TCpotKategori.setText("Tidak Nyeri");
            } else if (total <= 2) {
                this.TCpotKategori.setText("Nyeri Ringan");
            } else if (total <= 4) {
                this.TCpotKategori.setText("Nyeri Sedang");
            } else if (total <= 6) {
                this.TCpotKategori.setText("Nyeri Berat");
            } else {
                this.TCpotKategori.setText("Nyeri Sangat Berat");
            }
        };
        ItemListener ilCpotVent = e -> {
            if (this.CmbCpotVentilator.getSelectedIndex() > 0 && e.getStateChange() == 1) {
                this.CmbCpotVokalisasi.setSelectedIndex(0);
            }
        };
        ItemListener ilCpotVokal = e -> {
            if (this.CmbCpotVokalisasi.getSelectedIndex() > 0 && e.getStateChange() == 1) {
                this.CmbCpotVentilator.setSelectedIndex(0);
            }
        };
        this.CmbCpotEkspresi.addItemListener(ilCpotTotal);
        this.CmbCpotGerakan.addItemListener(ilCpotTotal);
        this.CmbCpotKetegangan.addItemListener(ilCpotTotal);
        this.CmbCpotVentilator.addItemListener(ilCpotTotal);
        this.CmbCpotVentilator.addItemListener(ilCpotVent);
        this.CmbCpotVokalisasi.addItemListener(ilCpotTotal);
        this.CmbCpotVokalisasi.addItemListener(ilCpotVokal);
        pCpot.add(this.wrapRow("Ekspresi Wajah :", this.CmbCpotEkspresi));
        pCpot.add(this.wrapRow("Gerakan Tubuh :", this.CmbCpotGerakan));
        pCpot.add(this.wrapRow("Ketegangan Otot :", this.CmbCpotKetegangan));
        pCpot.add(this.wrapRow("Kesesuaian dg Ventilator :", this.CmbCpotVentilator));
        pCpot.add(this.wrapRow("Vokalisasi (Ekstubasi) :", this.CmbCpotVokalisasi));
        JPanel pTotalCpot = new JPanel(new FlowLayout(0, 2, 0));
        pTotalCpot.setOpaque(false);
        pTotalCpot.add((Component)((Object)this.TCpotTotal));
        pTotalCpot.add((Component)((Object)this.createLabel(" Kategori Nyeri : ")));
        pTotalCpot.add((Component)((Object)this.TCpotKategori));
        pCpot.add(this.wrapRow("Total Skor :", pTotalCpot));
        FormInput.add(pCpot);
        FormInput.add(Box.createVerticalGlue());
        this.TabRawat.addTab("Input Penilaian", this.panelInput);
        
        this.tabMode = new DefaultTableModel(null, new Object[]{"No Rawat", "No RM", "Nama Pasien", "Tgl Lahir", "JK", "Kode Dokter", "Nama Dokter", "Tanggal", "Informasi Dari", "Tgl Tiba", "NIP", "Diagnosa Masuk", "Suhu", "TD", "Nadi Utama", "RR Utama", "Riwayat Keluarga", "Riwayat Pasien", "Deskripsi Penyakit", "Riwayat Sekarang", "Alat Bantu IGD", "Alat Bantu Persetujuan", "Alat Bantu Perintah", "Alergi", "Jenis Alergi", "Status Nikah", "Pendidikan", "Agama", "Warga Negara", "Pekerjaan", "Aktivitas", "Tinggal Bersama", "Ket Tinggal", "Tempat Tinggal", "Ket Tempat Tinggal", "Curiga", "Curiga Ya", "Curiga Tidak", "Budaya", "Budaya Ya", "Budaya Tidak", "Anak", "Jumlah Anak", "Bimbingan Ibadah", "Masalah Psiko 1", "Masalah Psiko 2", "Masalah Psiko 3", "Masalah Psiko 4", "Kondisi Psikologis", "Ket Psiko", "Status Mental", "Orientasi Orang", "Ket Ori Orang", "Orientasi Tempat", "Ket Ori Tempat", "Orientasi Waktu", "Ket Ori Waktu", "Orientasi Situasi", "Ket Ori Situasi", "Memori", "Pupil Kanan Uk", "Pupil Kanan Reflex", "Pupil Kiri Uk", "Pupil Kiri Reflex", "GCS E", "GCS M", "GCS V", "GCS Jml", "Tanda Meningeal Kaku", "Tanda Meningeal Brudzinski", "Tanda Meningeal Kernig", "Tanda Meningeal Lain", "Masalah Neuro 1", "Masalah Neuro 2", "Jalan Nafas", "Benda Asing", "Airway Ukuran", "Pernafasan", "Bunyi Nafas", "Kesulitan Nafas", "Alat Nafas", "Oksigen", "Oksigen Ltr", "Oksigen Jenis", "Frekuensi Nafas", "Batuk", "SpO2", "Masalah Nafas 1", "Masalah Nafas 2", "Masalah Nafas 3", "Masalah Nafas 4", "Sirkulasi", "CRT", "Denyut Nadi", "Nadi", "Nadi Jelas", "Irama", "Pacemaker", "Pacemaker Jelas", "Akral", "Masalah Sirk 1", "Ket Masalah Sirk 1", "Masalah Sirk 2", "Ket Masalah Sirk 2", "Masalah Sirk 3", "Ket Masalah Sirk 3", "BAK", "BAK Lainnya", "Kateter", "Kateter Jelas", "Urin Jumlah", "Urin Warna", "Prostat", "Nyeri Pinggang", "Kelainan Kemih", "Kelainan Sebut", "Masalah Kemih 1", "Ket Masalah Kemih 1", "Status Ob G", "Status Ob P", "Status Ob A", "Menstruasi", "Kehamilan", "Kehamilan HPHT", "Kehamilan HPL", "Post Partum", "Lochea", "Lochea Jumlah", "Payudara", "Pengeluaran ASI", "Kontraksi", "Papsmear", "Papsmear Tgl", "Mammo", "Mammo Tgl", "Sadari", "Skrining Kanker", "Skrining Tgl", "Masalah Rep 1", "Masalah Rep 2", "Masalah Rep 3", "Masalah Rep 4", "Masalah Rep Lain", "Gejala Awal", "Kekerasan Fisik", "Turgor", "Rambut", "Kuku", "Luka", "Luka Dalam", "Perdarahan Integ", "Fraktur", "Lokasi", "Lokasi Lain", "Masalah Integ 1", "Ket Masalah Integ 1", "Masalah Integ 2", "Ket Masalah Integ 2", "Masalah Integ 3", "Ket Masalah Integ 3", "Masalah Integ 4", "Ket Masalah Integ 4", "Telinga", "Telinga Lainnya", "Hidung", "Tenggorokan", "Gigi", "Sakit Gigi", "Gigi Palsu", "Mata", "Mata Lainnya", "Masalah THT", "Wasir", "Perdarahan Rectal", "Jenis Diit", "Feeding Tube", "Pembatasan Cairan", "Abdomen", "Bunyi Usus", "Bunyi Usus Freq", "BAB", "BAB Sejak", "BAB Freq", "Konsistensi", "Warna Cerna", "Pencahar", "Masalah Pencernaan", "Nyeri Tidak Ada", "Nyeri Ada", "Nyeri Akut", "Nyeri Kronis", "Nyeri Viseral", "Nyeri Somatis", "Provokes Diam", "Provokes Mobilisasi", "Provokes Ditekan", "Provokes Tiduran", "Provokes Berdiri", "Provokes Berjalan", "Provokes Lainnya", "Quality Tajam", "Quality Tumpul", "Quality Ditusuk", "Quality Ditarik", "Quality Dipukul", "Quality Berdenyut", "Quality Dibakar", "Quality Ditikam", "Quality Disayat", "Quality Lainnya", "Radiation", "Radiation Lokasi", "Severity Metode", "Severity Skor", "Severity Nyeri", "Time Setiap", "Time Selama", "Time Sejak"}){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        this.tbData = new widget.Table();
        this.tbData.setModel(this.tabMode);
                this.tbData.setPreferredScrollableViewportSize(new Dimension(500, 500));
        this.tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i2 = 0; i2 < this.tbData.getColumnCount(); i2++) {
            javax.swing.table.TableColumn column = this.tbData.getColumnModel().getColumn(i2);
            if (i2 == 0) {
                column.setPreferredWidth(105); // No Rawat
            } else if (i2 == 1) {
                column.setPreferredWidth(70); // No RM
            } else if (i2 == 2) {
                column.setPreferredWidth(150); // Nama Pasien
            } else if (i2 == 3) {
                column.setPreferredWidth(65); // Tgl Lahir
            } else if (i2 == 4) {
                column.setPreferredWidth(25); // JK
            } else if (i2 == 5) {
                column.setPreferredWidth(80); // Kode Dokter
            } else if (i2 == 6) {
                column.setPreferredWidth(150); // Nama Dokter
            } else if (i2 == 7) {
                column.setPreferredWidth(115); // Tanggal
            } else {
                column.setPreferredWidth(100);
            }
        }
        this.tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (tbData.getSelectedRow() != -1) {
                    getData();
                }
            }
        });
        this.tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (tbData.getSelectedRow() != -1) {
                    getData();
                }
            }
        });
        this.scrollData = new widget.ScrollPane();
        this.scrollData.setOpaque(true);
        this.scrollData.setViewportView(this.tbData);

        this.internalFrame3 = new widget.InternalFrame();
        this.internalFrame3.setBorder(null);
        this.internalFrame3.setLayout(new BorderLayout());
        this.internalFrame3.add(this.scrollData, BorderLayout.CENTER);

        this.panelGlass9 = new widget.panelisi();
        this.panelGlass9.setPreferredSize(new Dimension(44, 44));
        this.panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        this.jLabel19 = new widget.Label();
        this.jLabel19.setText("Tgl.Asuhan :");
        this.jLabel19.setPreferredSize(new Dimension(70, 23));
        this.panelGlass9.add(this.jLabel19);

        this.DTPCari1 = new widget.Tanggal();
        this.DTPCari1.setPreferredSize(new Dimension(90, 23));
        this.DTPCari1.setDisplayFormat("dd-MM-yyyy");
        this.panelGlass9.add(this.DTPCari1);

        this.jLabel21 = new widget.Label();
        this.jLabel21.setText("s.d.");
        this.jLabel21.setPreferredSize(new Dimension(23, 23));
        this.panelGlass9.add(this.jLabel21);

        this.DTPCari2 = new widget.Tanggal();
        this.DTPCari2.setPreferredSize(new Dimension(90, 23));
        this.DTPCari2.setDisplayFormat("dd-MM-yyyy");
        this.panelGlass9.add(this.DTPCari2);

        this.jLabel6 = new widget.Label();
        this.jLabel6.setText("Key Word :");
        this.jLabel6.setPreferredSize(new Dimension(70, 23));
        this.panelGlass9.add(this.jLabel6);

        this.TCari = new widget.TextBox();
        this.TCari.setPreferredSize(new Dimension(200, 23));
        this.TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                    BtnCariActionPerformed(null);
                }
            }
        });
        this.panelGlass9.add(this.TCari);

        this.BtnCari = new widget.Button();
        this.BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        this.BtnCari.setText("Tampilkan Data");
        this.BtnCari.setPreferredSize(new Dimension(130, 23));
        this.BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        this.panelGlass9.add(this.BtnCari);

        this.BtnAll = new widget.Button();
        this.BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        this.BtnAll.setText("Semua");
        this.BtnAll.setPreferredSize(new Dimension(100, 23));
        this.BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        this.panelGlass9.add(this.BtnAll);

        this.jLabel7 = new widget.Label();
        this.jLabel7.setText("Record :");
        this.jLabel7.setPreferredSize(new Dimension(65, 23));
        this.panelGlass9.add(this.jLabel7);

        this.LCount = new widget.Label();
        this.LCount.setText("0");
        this.LCount.setPreferredSize(new Dimension(50, 23));
        this.panelGlass9.add(this.LCount);

        this.internalFrame3.add(this.panelGlass9, BorderLayout.PAGE_END);
        this.TabRawat.addTab("Data Penilaian", this.internalFrame3);

        this.internalFrame1.add((Component)this.TabRawat, "Center");
        panelisi panelGlass8 = new panelisi();
        panelGlass8.setPreferredSize(new Dimension(100, 56));
        panelGlass8.setLayout(new FlowLayout(0, 5, 9));
        this.BtnSimpan = new Button();
        this.BtnSimpan.setText("Simpan");
        this.BtnSimpan.setIcon(new ImageIcon(this.getClass().getResource("/picture/save-16x16.png")));
        this.BtnBatal = new Button();
        this.BtnBatal.setText("Baru");
        this.BtnBatal.setIcon(new ImageIcon(this.getClass().getResource("/picture/Cancel-2-16x16.png")));
        this.BtnHapus = new Button();
        this.BtnHapus.setText("Hapus");
        this.BtnHapus.setIcon(new ImageIcon(this.getClass().getResource("/picture/stop_f2.png")));
        this.BtnEdit = new Button();
        this.BtnEdit.setText("Ganti");
        this.BtnEdit.setIcon(new ImageIcon(this.getClass().getResource("/picture/inventaris.png")));
        this.BtnPrint = new Button();
        this.BtnPrint.setText("Cetak");
        this.BtnPrint.setIcon(new ImageIcon(this.getClass().getResource("/picture/b_print.png")));
        this.BtnKeluar = new Button();
        this.BtnKeluar.setText("Keluar");
        this.BtnKeluar.setIcon(new ImageIcon(this.getClass().getResource("/picture/exit.png")));
        panelGlass8.add((Component)((Object)this.BtnSimpan));
        panelGlass8.add((Component)((Object)this.BtnBatal));
        panelGlass8.add((Component)((Object)this.BtnHapus));
        panelGlass8.add((Component)((Object)this.BtnEdit));
        panelGlass8.add((Component)((Object)this.BtnPrint));
        panelGlass8.add((Component)((Object)this.BtnKeluar));
        this.internalFrame1.add((Component)panelGlass8, "Last");
        this.getContentPane().add((Component)this.internalFrame1, "Center");
        this.pack();
        this.jPopupMenu1 = new JPopupMenu();
        this.MnCetak = new JMenuItem();
        this.jPopupMenu1.setName("jPopupMenu1");
        this.MnCetak.setFont(new Font("Tahoma", 0, 11));
        this.MnCetak.setForeground(new Color(50, 50, 50));
        this.MnCetak.setIcon(new ImageIcon(this.getClass().getResource("/picture/b_print.png")));
        this.MnCetak.setText("Cetak Asesmen Keperawatan");
        this.MnCetak.setName("MnCetak");
        this.MnCetak.setPreferredSize(new Dimension(250, 26));
        this.jPopupMenu1.add(this.MnCetak);
        this.tbData.setComponentPopupMenu(this.jPopupMenu1);
        this.MnCetak.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (RMPenilaianAwalKeperawatanRanapDewasa.this.tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    RMPenilaianAwalKeperawatanRanapDewasa.this.BtnBatal.requestFocus();
                } else if (RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getSelectedRow() <= -1) {
                    JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau dicetak...!!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(3));
                    HashMap<String, Object> param = new HashMap<String, Object>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", RMPenilaianAwalKeperawatanRanapDewasa.this.Sequel.cariGambar("select setting.logo from setting"));
                    RMPenilaianAwalKeperawatanRanapDewasa.this.Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanapDewasa.jasper", "report", "::[ Laporan Penilaian Awal Keperawatan Dewasa ]::", "select penilaian_awal_keperawatan_ranap_dewasa.*, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter from penilaian_awal_keperawatan_ranap_dewasa inner join reg_periksa on penilaian_awal_keperawatan_ranap_dewasa.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_awal_keperawatan_ranap_dewasa.kd_dokter=dokter.kd_dokter where penilaian_awal_keperawatan_ranap_dewasa.no_rawat='" + RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getValueAt(RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getSelectedRow(), 0).toString() + "'", param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }

            private void setCursor(Cursor predefinedCursor) {
            }
        });
        this.BtnPrint.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (RMPenilaianAwalKeperawatanRanapDewasa.this.tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    RMPenilaianAwalKeperawatanRanapDewasa.this.BtnBatal.requestFocus();
                } else if (RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getSelectedRow() <= -1) {
                    JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau dicetak...!!!!");
                } else {
                    HashMap<String, Object> param = new HashMap<String, Object>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", RMPenilaianAwalKeperawatanRanapDewasa.this.Sequel.cariGambar("select setting.logo from setting"));
                    RMPenilaianAwalKeperawatanRanapDewasa.this.Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanapDewasa.jasper", "report", "::[ Laporan Penilaian Awal Keperawatan Dewasa ]::", "select penilaian_awal_keperawatan_ranap_dewasa.*, reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, dokter.nm_dokter from penilaian_awal_keperawatan_ranap_dewasa inner join reg_periksa on penilaian_awal_keperawatan_ranap_dewasa.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on penilaian_awal_keperawatan_ranap_dewasa.kd_dokter=dokter.kd_dokter where penilaian_awal_keperawatan_ranap_dewasa.no_rawat='" + RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getValueAt(RMPenilaianAwalKeperawatanRanapDewasa.this.tbData.getSelectedRow(), 0).toString() + "'", param);
                }
            }
        });
        this.BtnSimpan.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RMPenilaianAwalKeperawatanRanapDewasa.this.simpan();
            }
        });
        this.BtnBatal.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RMPenilaianAwalKeperawatanRanapDewasa.this.emptTeks();
            }
        });
        this.BtnHapus.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RMPenilaianAwalKeperawatanRanapDewasa.this.hapus();
            }
        });
        this.BtnEdit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RMPenilaianAwalKeperawatanRanapDewasa.this.ganti();
            }
        });
        this.BtnKeluar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RMPenilaianAwalKeperawatanRanapDewasa.this.dispose();
            }
        });
    }

    public void setNoRm(String norwt) {
        this.TNoRw.setText(norwt);
        this.TCari.setText(norwt);
        this.isRawat();
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                if (RMPenilaianAwalKeperawatanRanapDewasa.this.ChkAsesmen.isSelected()) {
                    this.nilai_jam = now.getHours();
                    this.nilai_menit = now.getMinutes();
                    this.nilai_detik = now.getSeconds();
                } else {
                    this.nilai_jam = RMPenilaianAwalKeperawatanRanapDewasa.this.JamAsesmen.getSelectedIndex();
                    this.nilai_menit = RMPenilaianAwalKeperawatanRanapDewasa.this.MenitAsesmen.getSelectedIndex();
                    this.nilai_detik = RMPenilaianAwalKeperawatanRanapDewasa.this.DetikAsesmen.getSelectedIndex();
                }
                if (this.nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (this.nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (this.nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String jam = nol_jam + Integer.toString(this.nilai_jam);
                String menit = nol_menit + Integer.toString(this.nilai_menit);
                String detik = nol_detik + Integer.toString(this.nilai_detik);
                RMPenilaianAwalKeperawatanRanapDewasa.this.JamAsesmen.setSelectedItem(jam);
                RMPenilaianAwalKeperawatanRanapDewasa.this.MenitAsesmen.setSelectedItem(menit);
                RMPenilaianAwalKeperawatanRanapDewasa.this.DetikAsesmen.setSelectedItem(detik);
                nol_jam = "";
                nol_menit = "";
                nol_detik = "";
                if (RMPenilaianAwalKeperawatanRanapDewasa.this.ChkTiba.isSelected()) {
                    this.nilai_jam = now.getHours();
                    this.nilai_menit = now.getMinutes();
                    this.nilai_detik = now.getSeconds();
                } else {
                    this.nilai_jam = RMPenilaianAwalKeperawatanRanapDewasa.this.JamTiba.getSelectedIndex();
                    this.nilai_menit = RMPenilaianAwalKeperawatanRanapDewasa.this.MenitTiba.getSelectedIndex();
                    this.nilai_detik = RMPenilaianAwalKeperawatanRanapDewasa.this.DetikTiba.getSelectedIndex();
                }
                if (this.nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (this.nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (this.nilai_detik <= 9) {
                    nol_detik = "0";
                }
                jam = nol_jam + Integer.toString(this.nilai_jam);
                menit = nol_menit + Integer.toString(this.nilai_menit);
                detik = nol_detik + Integer.toString(this.nilai_detik);
                RMPenilaianAwalKeperawatanRanapDewasa.this.JamTiba.setSelectedItem(jam);
                RMPenilaianAwalKeperawatanRanapDewasa.this.MenitTiba.setSelectedItem(menit);
                RMPenilaianAwalKeperawatanRanapDewasa.this.DetikTiba.setSelectedItem(detik);
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public void isCek() {
        this.BtnSimpan.setEnabled(true);
        this.BtnHapus.setEnabled(true);
        this.BtnEdit.setEnabled(true);
        this.BtnPrint.setEnabled(true);
        if (akses.getjml2() >= 1) {
            this.NIP.setText(akses.getkode());
            this.NmPetugas.setText(this.Sequel.cariIsi("select nama from petugas where nip=?", akses.getkode()));
        }
    }

    public void setDokter(String kd, String nm) {
        this.KdDokter.setText(kd);
        this.NmDokter.setText(nm);
    }

    private void isRawat() {
        try {
            this.ps = this.koneksi.prepareStatement("select reg_periksa.no_rkm_medis, pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk, pasien.tgl_lahir, pasien.stts_nikah, pasien.pnd, pasien.agama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                this.ps.setString(1, this.TNoRw.getText());
                this.rs = this.ps.executeQuery();
                if (this.rs.next()) {
                    this.TNoRM.setText(this.rs.getString("no_rkm_medis"));
                    this.TNmPasien.setText(this.rs.getString("nm_pasien"));
                    this.JK.setText(this.rs.getString("jk"));
                    this.TglLahir.setText(this.rs.getString("tgl_lahir"));
                    this.TStatusNikah.setText(this.rs.getString("stts_nikah"));
                    this.TPendidikan.setText(this.rs.getString("pnd"));
                    this.TAgama.setText(this.rs.getString("agama"));
                }
            }
            catch (Exception e) {
                System.out.println("Notif : " + e);
            }
            finally {
                if (this.rs != null) {
                    this.rs.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private JPanel wrapRow(String labelText, Component comp) {
        JPanel panel = new JPanel(new FlowLayout(0, 5, 4));
        panel.setOpaque(false);
        Label lbl = this.createLabel(labelText);
        lbl.setPreferredSize(new Dimension(160, 23));
        lbl.setHorizontalAlignment(4);
        panel.add((Component)((Object)lbl));
        if (comp != null) {
            panel.add(comp);
        }
        return panel;
    }

    private JPanel wrapRow(String labelText, int labelWidth, Component comp) {
        JPanel panel = new JPanel(new FlowLayout(0, 5, 4));
        panel.setOpaque(false);
        Label lbl = this.createLabel(labelText);
        lbl.setPreferredSize(new Dimension(labelWidth, 23));
        lbl.setHorizontalAlignment(4);
        panel.add((Component)((Object)lbl));
        if (comp != null) {
            panel.add(comp);
        }
        return panel;
    }

    private Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        return label;
    }

    private CekBox createCekBox(String text) {
        CekBox cb = new CekBox();
        cb.setText(text);
        return cb;
    }

    private ScrollPane createScrollPane(Component comp) {
        ScrollPane sp = new ScrollPane();
        sp.setViewportView(comp);
        sp.setPreferredSize(new Dimension(500, 60));
        sp.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        return sp;
    }

    private RadioButton createRadioButton(String text) {
        RadioButton rb = new RadioButton();
        rb.setText(text);
        return rb;
    }

    private void simpan() {
        if (this.TNoRw.getText().trim().equals("")) {
            this.Valid.textKosong((JTextField)((Object)this.TNoRw), "No.Rawat");
        } else {
            String[] stringArray = new String[220];
            stringArray[0] = this.TNoRw.getText();
            stringArray[1] = this.Valid.SetTgl(this.TglAsesmen.getSelectedItem() + "") + " " + this.JamAsesmen.getSelectedItem() + ":" + this.MenitAsesmen.getSelectedItem() + ":" + this.DetikAsesmen.getSelectedItem();
            stringArray[2] = this.InfoDari.getSelectedItem().toString();
            stringArray[3] = this.Valid.SetTgl(this.TglTiba.getSelectedItem() + "") + " " + this.JamTiba.getSelectedItem() + ":" + this.MenitTiba.getSelectedItem() + ":" + this.DetikTiba.getSelectedItem();
            stringArray[4] = this.NIP.getText();
            stringArray[5] = this.KdDokter.getText();
            stringArray[6] = this.TDiagnosaMasuk.getText();
            stringArray[7] = this.TSuhu.getText();
            stringArray[8] = this.TTD.getText();
            stringArray[9] = this.TNadi.getText();
            stringArray[10] = this.TRR.getText();
            stringArray[11] = this.TRiwayatKeluarga.getText();
            stringArray[12] = this.TRiwayatPasien.getText();
            stringArray[13] = this.TDeskripsiPenyakit.getText();
            stringArray[14] = this.TRiwayatSekarang.getText();
            stringArray[15] = this.ChkIGD.isSelected() ? "Ya" : "Tidak";
            stringArray[16] = this.ChkPersetujuan.isSelected() ? "Ya" : "Tidak";
            stringArray[17] = this.ChkPerintah.isSelected() ? "Ya" : "Tidak";
            stringArray[18] = this.CmbAlergi.getSelectedItem().toString();
            stringArray[19] = this.TJenisAlergi.getText();
            stringArray[20] = this.TStatusNikah.getText();
            stringArray[21] = this.TPendidikan.getText();
            stringArray[22] = this.TAgama.getText();
            stringArray[23] = this.CmbWargaNegara.getSelectedItem().toString();
            stringArray[24] = this.CmbPekerjaan.getSelectedItem().toString();
            stringArray[25] = this.CmbAktivitas.getSelectedItem().toString();
            stringArray[26] = this.CmbTinggalBersama.getSelectedItem().toString();
            stringArray[27] = this.TKetTinggal.getText();
            stringArray[28] = this.CmbTempatTinggal.getSelectedItem().toString();
            stringArray[29] = this.TKetTempatTinggal.getText();
            stringArray[30] = this.CmbCuriga.getSelectedItem().toString();
            stringArray[31] = this.ChkCurigaYa.isSelected() ? "Ya" : "Tidak";
            stringArray[32] = this.ChkCurigaTidak.isSelected() ? "Ya" : "Tidak";
            stringArray[33] = this.CmbBudaya.getSelectedItem().toString();
            stringArray[34] = this.ChkBudayaYa.isSelected() ? "Ya" : "Tidak";
            stringArray[35] = this.ChkBudayaTidak.isSelected() ? "Ya" : "Tidak";
            stringArray[36] = this.CmbAnak.getSelectedItem().toString();
            stringArray[37] = this.TJumlahAnak.getText();
            stringArray[38] = this.CmbBimbinganIbadah.getSelectedItem().toString();
            stringArray[39] = this.ChkMasalahPsiko1.isSelected() ? "Ya" : "Tidak";
            stringArray[40] = this.ChkMasalahPsiko2.isSelected() ? "Ya" : "Tidak";
            stringArray[41] = this.ChkMasalahPsiko3.isSelected() ? "Ya" : "Tidak";
            stringArray[42] = this.ChkMasalahPsiko4.isSelected() ? "Ya" : "Tidak";
            stringArray[43] = this.CmbPsiko.getSelectedItem().toString();
            stringArray[44] = this.TKetPsiko.getText();
            stringArray[45] = this.RdoStatusCM.isSelected() ? "CM" : (this.RdoStatusBingung.isSelected() ? "Bingung" : (this.RdoStatusMengantuk.isSelected() ? "Mengantuk" : (this.RdoStatusApatis.isSelected() ? "Apatis" : (this.RdoStatusTidakRespon.isSelected() ? "Tidak respon" : ""))));
            stringArray[46] = this.RdoOriOrang.isSelected() ? "Baik" : "Tidak";
            stringArray[47] = this.TOriOrang.getText();
            stringArray[48] = this.RdoOriTempat.isSelected() ? "Baik" : "Tidak";
            stringArray[49] = this.TOriTempat.getText();
            stringArray[50] = this.RdoOriWaktu.isSelected() ? "Baik" : "Tidak";
            stringArray[51] = this.TOriWaktu.getText();
            stringArray[52] = this.RdoOriSituasi.isSelected() ? "Baik" : "Tidak";
            stringArray[53] = this.TOriSituasi.getText();
            stringArray[54] = this.CmbMemori.getSelectedItem().toString();
            stringArray[55] = this.TPupilKananUk.getText();
            stringArray[56] = this.TPupilKananReflex.getText();
            stringArray[57] = this.TPupilKiriUk.getText();
            stringArray[58] = this.TPupilKiriReflex.getText();
            stringArray[59] = this.TGCSEye.getText();
            stringArray[60] = this.TGCSMotorik.getText();
            stringArray[61] = this.TGCSVerbal.getText();
            stringArray[62] = this.TGCSJumlah.getText();
            stringArray[63] = this.RdoKakuKuduk.isSelected() ? "Ya" : "Tidak";
            stringArray[64] = this.RdoBrudzinski.isSelected() ? "Ya" : "Tidak";
            stringArray[65] = this.RdoKernig.isSelected() ? "Ya" : "Tidak";
            stringArray[66] = this.RdoNeuroLain.isSelected() ? "Ya" : "Tidak";
            stringArray[67] = this.ChkMasalahNeuro1.isSelected() ? "Ya" : "Tidak";
            stringArray[68] = this.ChkMasalahNeuro2.isSelected() ? "Ya" : "Tidak";
            stringArray[69] = this.RdoAirway1.isSelected() ? "Bebas" : (this.RdoAirway2.isSelected() ? "Sumbatan" : (this.RdoAirway3.isSelected() ? "Spasme" : (this.RdoAirway4.isSelected() ? "Lainnya" : "")));
            stringArray[70] = this.TBendaAsing.getText();
            stringArray[71] = this.TAirwayUkuran.getText();
            stringArray[72] = this.RdoBreath1.isSelected() ? "Normal" : (this.RdoBreath2.isSelected() ? "Kusmaul" : (this.RdoBreath3.isSelected() ? "Biot" : (this.RdoBreath4.isSelected() ? "Cheyne Stokes" : (this.RdoBreath5.isSelected() ? "Lainnya" : ""))));
            stringArray[73] = this.RdoBunyi1.isSelected() ? "Vesikuler" : (this.RdoBunyi2.isSelected() ? "Wheezing" : (this.RdoBunyi3.isSelected() ? "Ronchi" : (this.RdoBunyi4.isSelected() ? "Krekels" : (this.RdoBunyi5.isSelected() ? "Lainnya" : ""))));
            stringArray[74] = this.RdoSulitNafas1.isSelected() ? "Tidak" : (this.RdoSulitNafas2.isSelected() ? "Dyspnea" : (this.RdoSulitNafas3.isSelected() ? "Ortopnea" : ""));
            stringArray[75] = this.RdoAlatNafas1.isSelected() ? "Tidak" : (this.RdoAlatNafas2.isSelected() ? "Ya" : "");
            stringArray[76] = this.RdoOksigen1.isSelected() ? "Tidak" : (this.RdoOksigen2.isSelected() ? "Ya" : "");
            stringArray[77] = this.TOksigenLtr.getText();
            stringArray[78] = this.CmbOksigenJenis.getSelectedItem().toString();
            stringArray[79] = this.TFrekuensiNafas.getText();
            stringArray[80] = this.RdoBatuk1.isSelected() ? "Tidak" : (this.RdoBatuk2.isSelected() ? "Ya" : (this.RdoBatuk3.isSelected() ? "Berdahak" : (this.RdoBatuk4.isSelected() ? "Tidak berdahak" : "")));
            stringArray[81] = this.TSpO2.getText();
            stringArray[82] = this.ChkMasalahNafas1.isSelected() ? "Ya" : "Tidak";
            stringArray[83] = this.ChkMasalahNafas2.isSelected() ? "Ya" : "Tidak";
            stringArray[84] = this.ChkMasalahNafas3.isSelected() ? "Ya" : "Tidak";
            stringArray[85] = this.ChkMasalahNafas4.isSelected() ? "Ya" : "Tidak";
            stringArray[86] = this.RdoSirkulasi1.isSelected() ? "Normal" : (this.RdoSirkulasi2.isSelected() ? "Pucat" : (this.RdoSirkulasi3.isSelected() ? "Sianosis" : (this.RdoSirkulasi4.isSelected() ? "Berkeringat" : (this.RdoSirkulasi5.isSelected() ? "Lainnya" : ""))));
            stringArray[87] = this.RdoCRT1.isSelected() ? "< 2 detik" : (this.RdoCRT2.isSelected() ? "> 2 detik" : "");
            stringArray[88] = this.TDenyutNadi.getText();
            stringArray[89] = this.RdoNadi1.isSelected() ? "Kuat" : (this.RdoNadi2.isSelected() ? "Lemah" : (this.RdoNadi3.isSelected() ? "Tidak teraba" : ""));
            stringArray[90] = this.TNadiJelas.getText();
            stringArray[91] = this.RdoIrama1.isSelected() ? "Teratur" : (this.RdoIrama2.isSelected() ? "Tidak teratur" : "");
            stringArray[92] = this.RdoPacemaker1.isSelected() ? "Tidak" : (this.RdoPacemaker2.isSelected() ? "Ya" : "");
            stringArray[93] = this.TPacemakerJelas.getText();
            stringArray[94] = this.RdoAkral1.isSelected() ? "Hangat" : (this.RdoAkral2.isSelected() ? "Dingin" : "");
            stringArray[95] = this.ChkMasalahSirk1.isSelected() ? "Ya" : "Tidak";
            stringArray[96] = this.TMasalahSirkJelas1.getText();
            stringArray[97] = this.ChkMasalahSirk2.isSelected() ? "Ya" : "Tidak";
            stringArray[98] = this.TMasalahSirkJelas2.getText();
            stringArray[99] = this.ChkMasalahSirk3.isSelected() ? "Ya" : "Tidak";
            stringArray[100] = this.TMasalahSirkJelas3.getText();
            stringArray[101] = this.RdoBAK1.isSelected() ? "Normal" : (this.RdoBAK2.isSelected() ? "Inkontinensia" : (this.RdoBAK3.isSelected() ? "Retensi" : (this.RdoBAK4.isSelected() ? "Anuria" : (this.RdoBAK5.isSelected() ? "Disuria" : (this.RdoBAK6.isSelected() ? "Oliguria" : (this.RdoBAK7.isSelected() ? "Lainnya" : ""))))));
            stringArray[102] = this.TBAKLainnya.getText();
            stringArray[103] = this.RdoKateter1.isSelected() ? "Tidak" : (this.RdoKateter2.isSelected() ? "Ya" : "");
            stringArray[104] = this.TKateterJelas.getText();
            stringArray[105] = this.TUrinJumlah.getText();
            stringArray[106] = this.RdoUrin1.isSelected() ? "Kuning jernih" : (this.RdoUrin2.isSelected() ? "Kuning pekat" : (this.RdoUrin3.isSelected() ? "Lainnya" : ""));
            stringArray[107] = this.RdoProstat1.isSelected() ? "Normal" : (this.RdoProstat2.isSelected() ? "Membesar" : "");
            stringArray[108] = this.RdoNyeriPinggang1.isSelected() ? "Tidak" : (this.RdoNyeriPinggang2.isSelected() ? "Ya" : "");
            stringArray[109] = this.RdoKelainan1.isSelected() ? "Tidak" : (this.RdoKelainan2.isSelected() ? "Ya" : "");
            stringArray[110] = this.TKelainanSebut.getText();
            stringArray[111] = this.ChkMasalahKemih1.isSelected() ? "Ya" : "Tidak";
            stringArray[112] = this.TMasalahKemihJelas1.getText();
            stringArray[113] = this.TStatusObG.getText();
            stringArray[114] = this.TStatusObP.getText();
            stringArray[115] = this.TStatusObA.getText();
            stringArray[116] = this.RdoMens1.isSelected() ? "Tidak" : (this.RdoMens2.isSelected() ? "Teratur" : (this.RdoMens3.isSelected() ? "Tidak teratur" : (this.RdoMens4.isSelected() ? "Menopause" : "")));
            stringArray[117] = this.RdoPregnan1.isSelected() ? "Tidak" : (this.RdoPregnan2.isSelected() ? "Ya" : "");
            stringArray[118] = this.TPregnanHPHT.getText();
            stringArray[119] = this.TPregnanHPL.getText();
            stringArray[120] = this.TPostPartum.getText();
            stringArray[121] = this.TLochea.getText();
            stringArray[122] = this.TLocheaJumlah.getText();
            stringArray[123] = this.TPayudara.getText();
            stringArray[124] = this.TPengeluaranASI.getText();
            stringArray[125] = this.TKontraksi.getText();
            stringArray[126] = this.RdoPapsmear1.isSelected() ? "Tidak" : (this.RdoPapsmear2.isSelected() ? "Ya" : "");
            stringArray[127] = this.TPapsmearTgl.getText();
            stringArray[128] = this.RdoMammo1.isSelected() ? "Tidak" : (this.RdoMammo2.isSelected() ? "Ya" : "");
            stringArray[129] = this.TMammoTgl.getText();
            stringArray[130] = this.RdoSadari1.isSelected() ? "Tidak" : (this.RdoSadari2.isSelected() ? "Ya" : "");
            stringArray[131] = this.RdoSkrining1.isSelected() ? "Tidak" : (this.RdoSkrining2.isSelected() ? "Ya" : "");
            stringArray[132] = this.TSkriningTgl.getText();
            stringArray[133] = this.ChkMasalahRep1.isSelected() ? "Ya" : "Tidak";
            stringArray[134] = this.ChkMasalahRep2.isSelected() ? "Ya" : "Tidak";
            stringArray[135] = this.ChkMasalahRep3.isSelected() ? "Ya" : "Tidak";
            stringArray[136] = this.ChkMasalahRep4.isSelected() ? "Ya" : "Tidak";
            stringArray[137] = this.TMasalahRepLain.getText();
            stringArray[138] = this.RdoInteg1.isSelected() ? "Tidak masalah" : (this.RdoInteg2.isSelected() ? "Rash" : (this.RdoInteg3.isSelected() ? "Lesi" : (this.RdoInteg4.isSelected() ? "Memar" : (this.RdoInteg5.isSelected() ? "Banyak keringat" : ""))));
            stringArray[139] = this.RdoKekerasan1.isSelected() ? "Indikasi kekerasan fisik" : (this.RdoKekerasan2.isSelected() ? "Pucat" : (this.RdoKekerasan3.isSelected() ? "Sianosis" : ""));
            stringArray[140] = this.RdoTurgor1.isSelected() ? "Baik" : (this.RdoTurgor2.isSelected() ? "Sedang" : (this.RdoTurgor3.isSelected() ? "Buruk" : ""));
            stringArray[141] = this.RdoRambut1.isSelected() ? "Bersih" : (this.RdoRambut2.isSelected() ? "Kotor" : "");
            stringArray[142] = this.RdoKuku1.isSelected() ? "Bersih" : (this.RdoKuku2.isSelected() ? "Kotor" : "");
            stringArray[143] = this.RdoLuka1.isSelected() ? "Tidak" : (this.RdoLuka2.isSelected() ? "Ya" : "");
            stringArray[144] = this.TLukaDalam.getText();
            stringArray[145] = this.RdoPerdarahan1.isSelected() ? "Tidak" : (this.RdoPerdarahan2.isSelected() ? "Ya" : "");
            stringArray[146] = this.RdoFraktur1.isSelected() ? "Tidak" : (this.RdoFraktur2.isSelected() ? "Ya" : (this.RdoFraktur3.isSelected() ? "Tertutup" : (this.RdoFraktur4.isSelected() ? "Terbuka" : "")));
            stringArray[147] = this.RdoLokasi1.isSelected() ? "Extremitas atas" : (this.RdoLokasi2.isSelected() ? "Extremitas bawah" : (this.RdoLokasi3.isSelected() ? "Batang Tubuh" : (this.RdoLokasi4.isSelected() ? "Lain-lain" : "")));
            stringArray[148] = this.TLokasiLain.getText();
            stringArray[149] = this.ChkMasalahInteg1.isSelected() ? "Ya" : "Tidak";
            stringArray[150] = this.TMasalahIntegJelas1.getText();
            stringArray[151] = this.ChkMasalahInteg2.isSelected() ? "Ya" : "Tidak";
            stringArray[152] = this.TMasalahIntegJelas2.getText();
            stringArray[153] = this.ChkMasalahInteg3.isSelected() ? "Ya" : "Tidak";
            stringArray[154] = this.TMasalahIntegJelas3.getText();
            stringArray[155] = this.ChkMasalahInteg4.isSelected() ? "Ya" : "Tidak";
            stringArray[156] = this.TMasalahIntegJelas4.getText();
            stringArray[157] = this.RdoTelinga1.isSelected() ? "Normal" : (this.RdoTelinga2.isSelected() ? "Alat bantu dengar" : (this.RdoTelinga3.isSelected() ? "Lainnya" : ""));
            stringArray[158] = this.TTelingaLainnya.getText();
            stringArray[159] = this.RdoHidung1.isSelected() ? "Normal" : (this.RdoHidung2.isSelected() ? "Sinusitis" : (this.RdoHidung3.isSelected() ? "Polip" : (this.RdoHidung4.isSelected() ? "Epistaksis" : (this.RdoHidung5.isSelected() ? "Lainnya" : ""))));
            stringArray[160] = this.RdoTenggorokan1.isSelected() ? "Normal" : (this.RdoTenggorokan2.isSelected() ? "Nyeri telan" : (this.RdoTenggorokan3.isSelected() ? "Tonsilitis" : ""));
            stringArray[161] = this.RdoGigi1.isSelected() ? "Bersih" : (this.RdoGigi2.isSelected() ? "Karies" : (this.RdoGigi3.isSelected() ? "Karang gigi" : (this.RdoGigi4.isSelected() ? "Kotor" : (this.RdoGigi5.isSelected() ? "Ompong" : (this.RdoGigi6.isSelected() ? "Lengkap" : "")))));
            stringArray[162] = this.RdoSakitGigi1.isSelected() ? "Tidak" : (this.RdoSakitGigi2.isSelected() ? "Ya" : "");
            stringArray[163] = this.RdoGigiPalsu1.isSelected() ? "Tidak" : (this.RdoGigiPalsu2.isSelected() ? "Ya" : "");
            stringArray[164] = this.RdoMata1.isSelected() ? "Normal" : (this.RdoMata2.isSelected() ? "Kering" : (this.RdoMata3.isSelected() ? "Buta" : (this.RdoMata4.isSelected() ? "Katarak" : (this.RdoMata5.isSelected() ? "Glaukoma" : (this.RdoMata6.isSelected() ? "Rabun Jauh" : (this.RdoMata7.isSelected() ? "Rabun dekat" : (this.RdoMata8.isSelected() ? "Konjungtivitis" : (this.RdoMata9.isSelected() ? "Kaca mata" : (this.RdoMata10.isSelected() ? "Lainnya" : "")))))))));
            stringArray[165] = this.TMataLainnya.getText();
            stringArray[166] = this.ChkMasalahTHTMata1.isSelected() ? "Ya" : "Tidak";
            stringArray[167] = this.RdoWasir1.isSelected() ? "Ya" : (this.RdoWasir2.isSelected() ? "Tidak" : "");
            stringArray[168] = this.RdoPerdarahanRectal1.isSelected() ? "Ya" : (this.RdoPerdarahanRectal2.isSelected() ? "Tidak" : "");
            stringArray[169] = this.TJenisDiit.getText();
            stringArray[170] = this.TFeedingTube.getText();
            stringArray[171] = this.RdoPembatasanCairan1.isSelected() ? "Ya" : (this.RdoPembatasanCairan2.isSelected() ? "Tidak" : "");
            stringArray[172] = this.RdoAbdomen1.isSelected() ? "Supel" : (this.RdoAbdomen2.isSelected() ? "Distensi" : (this.RdoAbdomen3.isSelected() ? "Kembung" : ""));
            stringArray[173] = this.RdoBunyiUsus1.isSelected() ? "Normal" : (this.RdoBunyiUsus2.isSelected() ? "Tidak ada" : (this.RdoBunyiUsus3.isSelected() ? "Frekuensi" : ""));
            stringArray[174] = this.TBunyiUsusFreq.getText();
            stringArray[175] = this.RdoBAB1.isSelected() ? "Normal" : (this.RdoBAB2.isSelected() ? "Diare, sejak :" : (this.RdoBAB3.isSelected() ? "Frekwensi" : ""));
            stringArray[176] = this.TBABDiareSejak.getText();
            stringArray[177] = this.TBABFreq.getText();
            stringArray[178] = this.RdoKonsistensi1.isSelected() ? "Padat" : (this.RdoKonsistensi2.isSelected() ? "Cair" : (this.RdoKonsistensi3.isSelected() ? "Lembek" : (this.RdoKonsistensi4.isSelected() ? "Berlendir" : "")));
            stringArray[179] = this.TWarnaCerna.getText();
            stringArray[180] = this.RdoPencahar1.isSelected() ? "Ya" : (this.RdoPencahar2.isSelected() ? "Tidak" : "");
            stringArray[181] = this.ChkMasalahPencernaan1.isSelected() ? "Ya" : "Tidak";
            stringArray[182] = this.RdoNyeriTidakAda.isSelected() ? "Ya" : "Tidak";
            stringArray[183] = this.RdoNyeriAda.isSelected() ? "Ya" : "Tidak";
            stringArray[184] = this.RdoNyeriAkut.isSelected() ? "Ya" : "Tidak";
            stringArray[185] = this.RdoNyeriKronis.isSelected() ? "Ya" : "Tidak";
            stringArray[186] = this.RdoNyeriViseral.isSelected() ? "Ya" : "Tidak";
            stringArray[187] = this.RdoNyeriSomatis.isSelected() ? "Ya" : "Tidak";
            stringArray[188] = this.RdoProvokesDiam.isSelected() ? "Ya" : "Tidak";
            stringArray[189] = this.RdoProvokesMobilisasi.isSelected() ? "Ya" : "Tidak";
            stringArray[190] = this.RdoProvokesDitekan.isSelected() ? "Ya" : "Tidak";
            stringArray[191] = this.RdoProvokesTiduran.isSelected() ? "Ya" : "Tidak";
            stringArray[192] = this.RdoProvokesBerdiri.isSelected() ? "Ya" : "Tidak";
            stringArray[193] = this.RdoProvokesBerjalan.isSelected() ? "Ya" : "Tidak";
            stringArray[194] = this.TProvokesLainnya.getText();
            stringArray[195] = this.RdoQualityTajam.isSelected() ? "Ya" : "Tidak";
            stringArray[196] = this.RdoQualityTumpul.isSelected() ? "Ya" : "Tidak";
            stringArray[197] = this.RdoQualityDitusuk.isSelected() ? "Ya" : "Tidak";
            stringArray[198] = this.RdoQualityDitarik.isSelected() ? "Ya" : "Tidak";
            stringArray[199] = this.RdoQualityDipukul.isSelected() ? "Ya" : "Tidak";
            stringArray[200] = this.RdoQualityBerdenyut.isSelected() ? "Ya" : "Tidak";
            stringArray[201] = this.RdoQualityDibakar.isSelected() ? "Ya" : "Tidak";
            stringArray[202] = this.RdoQualityDitikam.isSelected() ? "Ya" : "Tidak";
            stringArray[203] = this.RdoQualityDisayat.isSelected() ? "Ya" : "Tidak";
            stringArray[204] = this.TQualityLainnya.getText();
            stringArray[205] = this.CmbRadiation.getSelectedItem().toString();
            stringArray[206] = this.TRadiationLokasi.getText();
            stringArray[207] = this.CmbSeverityMetode.getSelectedItem().toString();
            stringArray[208] = this.TSeveritySkor.getText();
            stringArray[209] = this.TSeverityNyeri.getText();
            stringArray[210] = this.CmbTimeSetiap.getSelectedItem().toString();
            stringArray[211] = this.CmbTimeSelama.getSelectedItem().toString();
            stringArray[212] = this.TTimeSejak.getText();
            stringArray[213] = String.valueOf(this.CmbCpotEkspresi.getSelectedIndex());
            stringArray[214] = String.valueOf(this.CmbCpotGerakan.getSelectedIndex());
            stringArray[215] = String.valueOf(this.CmbCpotKetegangan.getSelectedIndex());
            stringArray[216] = this.CmbCpotVentilator.getSelectedIndex() > 0 ? String.valueOf(this.CmbCpotVentilator.getSelectedIndex() - 1) : "-";
            stringArray[217] = this.CmbCpotVokalisasi.getSelectedIndex() > 0 ? String.valueOf(this.CmbCpotVokalisasi.getSelectedIndex() - 1) : "-";
            stringArray[218] = this.TCpotTotal.getText();
            stringArray[219] = this.TCpotKategori.getText();
            if (this.Sequel.menyimpantf("penilaian_awal_keperawatan_ranap_dewasa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 220, stringArray)) {
                this.tampil();
                this.emptTeks();
            JOptionPane.showMessageDialog(null, "Data berhasil tersimpan");
            }
        }
    }

    private void ganti() {
        if (this.TNoRw.getText().trim().equals("")) {
            this.Valid.textKosong((JTextField)((Object)this.TNoRw), "No.Rawat");
        } else {
            String[] stringArray = new String[220];
            stringArray[0] = this.Valid.SetTgl(this.TglAsesmen.getSelectedItem() + "") + " " + this.JamAsesmen.getSelectedItem() + ":" + this.MenitAsesmen.getSelectedItem() + ":" + this.DetikAsesmen.getSelectedItem();
            stringArray[1] = this.InfoDari.getSelectedItem().toString();
            stringArray[2] = this.Valid.SetTgl(this.TglTiba.getSelectedItem() + "") + " " + this.JamTiba.getSelectedItem() + ":" + this.MenitTiba.getSelectedItem() + ":" + this.DetikTiba.getSelectedItem();
            stringArray[3] = this.NIP.getText();
            stringArray[4] = this.KdDokter.getText();
            stringArray[5] = this.TDiagnosaMasuk.getText();
            stringArray[6] = this.TSuhu.getText();
            stringArray[7] = this.TTD.getText();
            stringArray[8] = this.TNadi.getText();
            stringArray[9] = this.TRR.getText();
            stringArray[10] = this.TRiwayatKeluarga.getText();
            stringArray[11] = this.TRiwayatPasien.getText();
            stringArray[12] = this.TDeskripsiPenyakit.getText();
            stringArray[13] = this.TRiwayatSekarang.getText();
            stringArray[14] = this.ChkIGD.isSelected() ? "Ya" : "Tidak";
            stringArray[15] = this.ChkPersetujuan.isSelected() ? "Ya" : "Tidak";
            stringArray[16] = this.ChkPerintah.isSelected() ? "Ya" : "Tidak";
            stringArray[17] = this.CmbAlergi.getSelectedItem().toString();
            stringArray[18] = this.TJenisAlergi.getText();
            stringArray[19] = this.TStatusNikah.getText();
            stringArray[20] = this.TPendidikan.getText();
            stringArray[21] = this.TAgama.getText();
            stringArray[22] = this.CmbWargaNegara.getSelectedItem().toString();
            stringArray[23] = this.CmbPekerjaan.getSelectedItem().toString();
            stringArray[24] = this.CmbAktivitas.getSelectedItem().toString();
            stringArray[25] = this.CmbTinggalBersama.getSelectedItem().toString();
            stringArray[26] = this.TKetTinggal.getText();
            stringArray[27] = this.CmbTempatTinggal.getSelectedItem().toString();
            stringArray[28] = this.TKetTempatTinggal.getText();
            stringArray[29] = this.CmbCuriga.getSelectedItem().toString();
            stringArray[30] = this.ChkCurigaYa.isSelected() ? "Ya" : "Tidak";
            stringArray[31] = this.ChkCurigaTidak.isSelected() ? "Ya" : "Tidak";
            stringArray[32] = this.CmbBudaya.getSelectedItem().toString();
            stringArray[33] = this.ChkBudayaYa.isSelected() ? "Ya" : "Tidak";
            stringArray[34] = this.ChkBudayaTidak.isSelected() ? "Ya" : "Tidak";
            stringArray[35] = this.CmbAnak.getSelectedItem().toString();
            stringArray[36] = this.TJumlahAnak.getText();
            stringArray[37] = this.CmbBimbinganIbadah.getSelectedItem().toString();
            stringArray[38] = this.ChkMasalahPsiko1.isSelected() ? "Ya" : "Tidak";
            stringArray[39] = this.ChkMasalahPsiko2.isSelected() ? "Ya" : "Tidak";
            stringArray[40] = this.ChkMasalahPsiko3.isSelected() ? "Ya" : "Tidak";
            stringArray[41] = this.ChkMasalahPsiko4.isSelected() ? "Ya" : "Tidak";
            stringArray[42] = this.CmbPsiko.getSelectedItem().toString();
            stringArray[43] = this.TKetPsiko.getText();
            stringArray[44] = this.RdoStatusCM.isSelected() ? "CM" : (this.RdoStatusBingung.isSelected() ? "Bingung" : (this.RdoStatusMengantuk.isSelected() ? "Mengantuk" : (this.RdoStatusApatis.isSelected() ? "Apatis" : (this.RdoStatusTidakRespon.isSelected() ? "Tidak respon" : ""))));
            stringArray[45] = this.RdoOriOrang.isSelected() ? "Baik" : "Tidak";
            stringArray[46] = this.TOriOrang.getText();
            stringArray[47] = this.RdoOriTempat.isSelected() ? "Baik" : "Tidak";
            stringArray[48] = this.TOriTempat.getText();
            stringArray[49] = this.RdoOriWaktu.isSelected() ? "Baik" : "Tidak";
            stringArray[50] = this.TOriWaktu.getText();
            stringArray[51] = this.RdoOriSituasi.isSelected() ? "Baik" : "Tidak";
            stringArray[52] = this.TOriSituasi.getText();
            stringArray[53] = this.CmbMemori.getSelectedItem().toString();
            stringArray[54] = this.TPupilKananUk.getText();
            stringArray[55] = this.TPupilKananReflex.getText();
            stringArray[56] = this.TPupilKiriUk.getText();
            stringArray[57] = this.TPupilKiriReflex.getText();
            stringArray[58] = this.TGCSEye.getText();
            stringArray[59] = this.TGCSMotorik.getText();
            stringArray[60] = this.TGCSVerbal.getText();
            stringArray[61] = this.TGCSJumlah.getText();
            stringArray[62] = this.RdoKakuKuduk.isSelected() ? "Ya" : "Tidak";
            stringArray[63] = this.RdoBrudzinski.isSelected() ? "Ya" : "Tidak";
            stringArray[64] = this.RdoKernig.isSelected() ? "Ya" : "Tidak";
            stringArray[65] = this.RdoNeuroLain.isSelected() ? "Ya" : "Tidak";
            stringArray[66] = this.ChkMasalahNeuro1.isSelected() ? "Ya" : "Tidak";
            stringArray[67] = this.ChkMasalahNeuro2.isSelected() ? "Ya" : "Tidak";
            stringArray[68] = this.RdoAirway1.isSelected() ? "Bebas" : (this.RdoAirway2.isSelected() ? "Sumbatan" : (this.RdoAirway3.isSelected() ? "Spasme" : (this.RdoAirway4.isSelected() ? "Lainnya" : "")));
            stringArray[69] = this.TBendaAsing.getText();
            stringArray[70] = this.TAirwayUkuran.getText();
            stringArray[71] = this.RdoBreath1.isSelected() ? "Normal" : (this.RdoBreath2.isSelected() ? "Kusmaul" : (this.RdoBreath3.isSelected() ? "Biot" : (this.RdoBreath4.isSelected() ? "Cheyne Stokes" : (this.RdoBreath5.isSelected() ? "Lainnya" : ""))));
            stringArray[72] = this.RdoBunyi1.isSelected() ? "Vesikuler" : (this.RdoBunyi2.isSelected() ? "Wheezing" : (this.RdoBunyi3.isSelected() ? "Ronchi" : (this.RdoBunyi4.isSelected() ? "Krekels" : (this.RdoBunyi5.isSelected() ? "Lainnya" : ""))));
            stringArray[73] = this.RdoSulitNafas1.isSelected() ? "Tidak" : (this.RdoSulitNafas2.isSelected() ? "Dyspnea" : (this.RdoSulitNafas3.isSelected() ? "Ortopnea" : ""));
            stringArray[74] = this.RdoAlatNafas1.isSelected() ? "Tidak" : (this.RdoAlatNafas2.isSelected() ? "Ya" : "");
            stringArray[75] = this.RdoOksigen1.isSelected() ? "Tidak" : (this.RdoOksigen2.isSelected() ? "Ya" : "");
            stringArray[76] = this.TOksigenLtr.getText();
            stringArray[77] = this.CmbOksigenJenis.getSelectedItem().toString();
            stringArray[78] = this.TFrekuensiNafas.getText();
            stringArray[79] = this.RdoBatuk1.isSelected() ? "Tidak" : (this.RdoBatuk2.isSelected() ? "Ya" : (this.RdoBatuk3.isSelected() ? "Berdahak" : (this.RdoBatuk4.isSelected() ? "Tidak berdahak" : "")));
            stringArray[80] = this.TSpO2.getText();
            stringArray[81] = this.ChkMasalahNafas1.isSelected() ? "Ya" : "Tidak";
            stringArray[82] = this.ChkMasalahNafas2.isSelected() ? "Ya" : "Tidak";
            stringArray[83] = this.ChkMasalahNafas3.isSelected() ? "Ya" : "Tidak";
            stringArray[84] = this.ChkMasalahNafas4.isSelected() ? "Ya" : "Tidak";
            stringArray[85] = this.RdoSirkulasi1.isSelected() ? "Normal" : (this.RdoSirkulasi2.isSelected() ? "Pucat" : (this.RdoSirkulasi3.isSelected() ? "Sianosis" : (this.RdoSirkulasi4.isSelected() ? "Berkeringat" : (this.RdoSirkulasi5.isSelected() ? "Lainnya" : ""))));
            stringArray[86] = this.RdoCRT1.isSelected() ? "< 2 detik" : (this.RdoCRT2.isSelected() ? "> 2 detik" : "");
            stringArray[87] = this.TDenyutNadi.getText();
            stringArray[88] = this.RdoNadi1.isSelected() ? "Kuat" : (this.RdoNadi2.isSelected() ? "Lemah" : (this.RdoNadi3.isSelected() ? "Tidak teraba" : ""));
            stringArray[89] = this.TNadiJelas.getText();
            stringArray[90] = this.RdoIrama1.isSelected() ? "Teratur" : (this.RdoIrama2.isSelected() ? "Tidak teratur" : "");
            stringArray[91] = this.RdoPacemaker1.isSelected() ? "Tidak" : (this.RdoPacemaker2.isSelected() ? "Ya" : "");
            stringArray[92] = this.TPacemakerJelas.getText();
            stringArray[93] = this.RdoAkral1.isSelected() ? "Hangat" : (this.RdoAkral2.isSelected() ? "Dingin" : "");
            stringArray[94] = this.ChkMasalahSirk1.isSelected() ? "Ya" : "Tidak";
            stringArray[95] = this.TMasalahSirkJelas1.getText();
            stringArray[96] = this.ChkMasalahSirk2.isSelected() ? "Ya" : "Tidak";
            stringArray[97] = this.TMasalahSirkJelas2.getText();
            stringArray[98] = this.ChkMasalahSirk3.isSelected() ? "Ya" : "Tidak";
            stringArray[99] = this.TMasalahSirkJelas3.getText();
            stringArray[100] = this.RdoBAK1.isSelected() ? "Normal" : (this.RdoBAK2.isSelected() ? "Inkontinensia" : (this.RdoBAK3.isSelected() ? "Retensi" : (this.RdoBAK4.isSelected() ? "Anuria" : (this.RdoBAK5.isSelected() ? "Disuria" : (this.RdoBAK6.isSelected() ? "Oliguria" : (this.RdoBAK7.isSelected() ? "Lainnya" : ""))))));
            stringArray[101] = this.TBAKLainnya.getText();
            stringArray[102] = this.RdoKateter1.isSelected() ? "Tidak" : (this.RdoKateter2.isSelected() ? "Ya" : "");
            stringArray[103] = this.TKateterJelas.getText();
            stringArray[104] = this.TUrinJumlah.getText();
            stringArray[105] = this.RdoUrin1.isSelected() ? "Kuning jernih" : (this.RdoUrin2.isSelected() ? "Kuning pekat" : (this.RdoUrin3.isSelected() ? "Lainnya" : ""));
            stringArray[106] = this.RdoProstat1.isSelected() ? "Normal" : (this.RdoProstat2.isSelected() ? "Membesar" : "");
            stringArray[107] = this.RdoNyeriPinggang1.isSelected() ? "Tidak" : (this.RdoNyeriPinggang2.isSelected() ? "Ya" : "");
            stringArray[108] = this.RdoKelainan1.isSelected() ? "Tidak" : (this.RdoKelainan2.isSelected() ? "Ya" : "");
            stringArray[109] = this.TKelainanSebut.getText();
            stringArray[110] = this.ChkMasalahKemih1.isSelected() ? "Ya" : "Tidak";
            stringArray[111] = this.TMasalahKemihJelas1.getText();
            stringArray[112] = this.TStatusObG.getText();
            stringArray[113] = this.TStatusObP.getText();
            stringArray[114] = this.TStatusObA.getText();
            stringArray[115] = this.RdoMens1.isSelected() ? "Tidak" : (this.RdoMens2.isSelected() ? "Teratur" : (this.RdoMens3.isSelected() ? "Tidak teratur" : (this.RdoMens4.isSelected() ? "Menopause" : "")));
            stringArray[116] = this.RdoPregnan1.isSelected() ? "Tidak" : (this.RdoPregnan2.isSelected() ? "Ya" : "");
            stringArray[117] = this.TPregnanHPHT.getText();
            stringArray[118] = this.TPregnanHPL.getText();
            stringArray[119] = this.TPostPartum.getText();
            stringArray[120] = this.TLochea.getText();
            stringArray[121] = this.TLocheaJumlah.getText();
            stringArray[122] = this.TPayudara.getText();
            stringArray[123] = this.TPengeluaranASI.getText();
            stringArray[124] = this.TKontraksi.getText();
            stringArray[125] = this.RdoPapsmear1.isSelected() ? "Tidak" : (this.RdoPapsmear2.isSelected() ? "Ya" : "");
            stringArray[126] = this.TPapsmearTgl.getText();
            stringArray[127] = this.RdoMammo1.isSelected() ? "Tidak" : (this.RdoMammo2.isSelected() ? "Ya" : "");
            stringArray[128] = this.TMammoTgl.getText();
            stringArray[129] = this.RdoSadari1.isSelected() ? "Tidak" : (this.RdoSadari2.isSelected() ? "Ya" : "");
            stringArray[130] = this.RdoSkrining1.isSelected() ? "Tidak" : (this.RdoSkrining2.isSelected() ? "Ya" : "");
            stringArray[131] = this.TSkriningTgl.getText();
            stringArray[132] = this.ChkMasalahRep1.isSelected() ? "Ya" : "Tidak";
            stringArray[133] = this.ChkMasalahRep2.isSelected() ? "Ya" : "Tidak";
            stringArray[134] = this.ChkMasalahRep3.isSelected() ? "Ya" : "Tidak";
            stringArray[135] = this.ChkMasalahRep4.isSelected() ? "Ya" : "Tidak";
            stringArray[136] = this.TMasalahRepLain.getText();
            stringArray[137] = this.RdoInteg1.isSelected() ? "Tidak masalah" : (this.RdoInteg2.isSelected() ? "Rash" : (this.RdoInteg3.isSelected() ? "Lesi" : (this.RdoInteg4.isSelected() ? "Memar" : (this.RdoInteg5.isSelected() ? "Banyak keringat" : ""))));
            stringArray[138] = this.RdoKekerasan1.isSelected() ? "Indikasi kekerasan fisik" : (this.RdoKekerasan2.isSelected() ? "Pucat" : (this.RdoKekerasan3.isSelected() ? "Sianosis" : ""));
            stringArray[139] = this.RdoTurgor1.isSelected() ? "Baik" : (this.RdoTurgor2.isSelected() ? "Sedang" : (this.RdoTurgor3.isSelected() ? "Buruk" : ""));
            stringArray[140] = this.RdoRambut1.isSelected() ? "Bersih" : (this.RdoRambut2.isSelected() ? "Kotor" : "");
            stringArray[141] = this.RdoKuku1.isSelected() ? "Bersih" : (this.RdoKuku2.isSelected() ? "Kotor" : "");
            stringArray[142] = this.RdoLuka1.isSelected() ? "Tidak" : (this.RdoLuka2.isSelected() ? "Ya" : "");
            stringArray[143] = this.TLukaDalam.getText();
            stringArray[144] = this.RdoPerdarahan1.isSelected() ? "Tidak" : (this.RdoPerdarahan2.isSelected() ? "Ya" : "");
            stringArray[145] = this.RdoFraktur1.isSelected() ? "Tidak" : (this.RdoFraktur2.isSelected() ? "Ya" : (this.RdoFraktur3.isSelected() ? "Tertutup" : (this.RdoFraktur4.isSelected() ? "Terbuka" : "")));
            stringArray[146] = this.RdoLokasi1.isSelected() ? "Extremitas atas" : (this.RdoLokasi2.isSelected() ? "Extremitas bawah" : (this.RdoLokasi3.isSelected() ? "Batang Tubuh" : (this.RdoLokasi4.isSelected() ? "Lain-lain" : "")));
            stringArray[147] = this.TLokasiLain.getText();
            stringArray[148] = this.ChkMasalahInteg1.isSelected() ? "Ya" : "Tidak";
            stringArray[149] = this.TMasalahIntegJelas1.getText();
            stringArray[150] = this.ChkMasalahInteg2.isSelected() ? "Ya" : "Tidak";
            stringArray[151] = this.TMasalahIntegJelas2.getText();
            stringArray[152] = this.ChkMasalahInteg3.isSelected() ? "Ya" : "Tidak";
            stringArray[153] = this.TMasalahIntegJelas3.getText();
            stringArray[154] = this.ChkMasalahInteg4.isSelected() ? "Ya" : "Tidak";
            stringArray[155] = this.TMasalahIntegJelas4.getText();
            stringArray[156] = this.RdoTelinga1.isSelected() ? "Normal" : (this.RdoTelinga2.isSelected() ? "Alat bantu dengar" : (this.RdoTelinga3.isSelected() ? "Lainnya" : ""));
            stringArray[157] = this.TTelingaLainnya.getText();
            stringArray[158] = this.RdoHidung1.isSelected() ? "Normal" : (this.RdoHidung2.isSelected() ? "Sinusitis" : (this.RdoHidung3.isSelected() ? "Polip" : (this.RdoHidung4.isSelected() ? "Epistaksis" : (this.RdoHidung5.isSelected() ? "Lainnya" : ""))));
            stringArray[159] = this.RdoTenggorokan1.isSelected() ? "Normal" : (this.RdoTenggorokan2.isSelected() ? "Nyeri telan" : (this.RdoTenggorokan3.isSelected() ? "Tonsilitis" : ""));
            stringArray[160] = this.RdoGigi1.isSelected() ? "Bersih" : (this.RdoGigi2.isSelected() ? "Karies" : (this.RdoGigi3.isSelected() ? "Karang gigi" : (this.RdoGigi4.isSelected() ? "Kotor" : (this.RdoGigi5.isSelected() ? "Ompong" : (this.RdoGigi6.isSelected() ? "Lengkap" : "")))));
            stringArray[161] = this.RdoSakitGigi1.isSelected() ? "Tidak" : (this.RdoSakitGigi2.isSelected() ? "Ya" : "");
            stringArray[162] = this.RdoGigiPalsu1.isSelected() ? "Tidak" : (this.RdoGigiPalsu2.isSelected() ? "Ya" : "");
            stringArray[163] = this.RdoMata1.isSelected() ? "Normal" : (this.RdoMata2.isSelected() ? "Kering" : (this.RdoMata3.isSelected() ? "Buta" : (this.RdoMata4.isSelected() ? "Katarak" : (this.RdoMata5.isSelected() ? "Glaukoma" : (this.RdoMata6.isSelected() ? "Rabun Jauh" : (this.RdoMata7.isSelected() ? "Rabun dekat" : (this.RdoMata8.isSelected() ? "Konjungtivitis" : (this.RdoMata9.isSelected() ? "Kaca mata" : (this.RdoMata10.isSelected() ? "Lainnya" : "")))))))));
            stringArray[164] = this.TMataLainnya.getText();
            stringArray[165] = this.ChkMasalahTHTMata1.isSelected() ? "Ya" : "Tidak";
            stringArray[166] = this.RdoWasir1.isSelected() ? "Ya" : (this.RdoWasir2.isSelected() ? "Tidak" : "");
            stringArray[167] = this.RdoPerdarahanRectal1.isSelected() ? "Ya" : (this.RdoPerdarahanRectal2.isSelected() ? "Tidak" : "");
            stringArray[168] = this.TJenisDiit.getText();
            stringArray[169] = this.TFeedingTube.getText();
            stringArray[170] = this.RdoPembatasanCairan1.isSelected() ? "Ya" : (this.RdoPembatasanCairan2.isSelected() ? "Tidak" : "");
            stringArray[171] = this.RdoAbdomen1.isSelected() ? "Supel" : (this.RdoAbdomen2.isSelected() ? "Distensi" : (this.RdoAbdomen3.isSelected() ? "Kembung" : ""));
            stringArray[172] = this.RdoBunyiUsus1.isSelected() ? "Normal" : (this.RdoBunyiUsus2.isSelected() ? "Tidak ada" : (this.RdoBunyiUsus3.isSelected() ? "Frekuensi" : ""));
            stringArray[173] = this.TBunyiUsusFreq.getText();
            stringArray[174] = this.RdoBAB1.isSelected() ? "Normal" : (this.RdoBAB2.isSelected() ? "Diare, sejak :" : (this.RdoBAB3.isSelected() ? "Frekwensi" : ""));
            stringArray[175] = this.TBABDiareSejak.getText();
            stringArray[176] = this.TBABFreq.getText();
            stringArray[177] = this.RdoKonsistensi1.isSelected() ? "Padat" : (this.RdoKonsistensi2.isSelected() ? "Cair" : (this.RdoKonsistensi3.isSelected() ? "Lembek" : (this.RdoKonsistensi4.isSelected() ? "Berlendir" : "")));
            stringArray[178] = this.TWarnaCerna.getText();
            stringArray[179] = this.RdoPencahar1.isSelected() ? "Ya" : (this.RdoPencahar2.isSelected() ? "Tidak" : "");
            stringArray[180] = this.ChkMasalahPencernaan1.isSelected() ? "Ya" : "Tidak";
            stringArray[181] = this.RdoNyeriTidakAda.isSelected() ? "Ya" : "Tidak";
            stringArray[182] = this.RdoNyeriAda.isSelected() ? "Ya" : "Tidak";
            stringArray[183] = this.RdoNyeriAkut.isSelected() ? "Ya" : "Tidak";
            stringArray[184] = this.RdoNyeriKronis.isSelected() ? "Ya" : "Tidak";
            stringArray[185] = this.RdoNyeriViseral.isSelected() ? "Ya" : "Tidak";
            stringArray[186] = this.RdoNyeriSomatis.isSelected() ? "Ya" : "Tidak";
            stringArray[187] = this.RdoProvokesDiam.isSelected() ? "Ya" : "Tidak";
            stringArray[188] = this.RdoProvokesMobilisasi.isSelected() ? "Ya" : "Tidak";
            stringArray[189] = this.RdoProvokesDitekan.isSelected() ? "Ya" : "Tidak";
            stringArray[190] = this.RdoProvokesTiduran.isSelected() ? "Ya" : "Tidak";
            stringArray[191] = this.RdoProvokesBerdiri.isSelected() ? "Ya" : "Tidak";
            stringArray[192] = this.RdoProvokesBerjalan.isSelected() ? "Ya" : "Tidak";
            stringArray[193] = this.TProvokesLainnya.getText();
            stringArray[194] = this.RdoQualityTajam.isSelected() ? "Ya" : "Tidak";
            stringArray[195] = this.RdoQualityTumpul.isSelected() ? "Ya" : "Tidak";
            stringArray[196] = this.RdoQualityDitusuk.isSelected() ? "Ya" : "Tidak";
            stringArray[197] = this.RdoQualityDitarik.isSelected() ? "Ya" : "Tidak";
            stringArray[198] = this.RdoQualityDipukul.isSelected() ? "Ya" : "Tidak";
            stringArray[199] = this.RdoQualityBerdenyut.isSelected() ? "Ya" : "Tidak";
            stringArray[200] = this.RdoQualityDibakar.isSelected() ? "Ya" : "Tidak";
            stringArray[201] = this.RdoQualityDitikam.isSelected() ? "Ya" : "Tidak";
            stringArray[202] = this.RdoQualityDisayat.isSelected() ? "Ya" : "Tidak";
            stringArray[203] = this.TQualityLainnya.getText();
            stringArray[204] = this.CmbRadiation.getSelectedItem().toString();
            stringArray[205] = this.TRadiationLokasi.getText();
            stringArray[206] = this.CmbSeverityMetode.getSelectedItem().toString();
            stringArray[207] = this.TSeveritySkor.getText();
            stringArray[208] = this.TSeverityNyeri.getText();
            stringArray[209] = this.CmbTimeSetiap.getSelectedItem().toString();
            stringArray[210] = this.CmbTimeSelama.getSelectedItem().toString();
            stringArray[211] = this.TTimeSejak.getText();
            stringArray[212] = String.valueOf(this.CmbCpotEkspresi.getSelectedIndex());
            stringArray[213] = String.valueOf(this.CmbCpotGerakan.getSelectedIndex());
            stringArray[214] = String.valueOf(this.CmbCpotKetegangan.getSelectedIndex());
            stringArray[215] = this.CmbCpotVentilator.getSelectedIndex() > 0 ? String.valueOf(this.CmbCpotVentilator.getSelectedIndex() - 1) : "-";
            stringArray[216] = this.CmbCpotVokalisasi.getSelectedIndex() > 0 ? String.valueOf(this.CmbCpotVokalisasi.getSelectedIndex() - 1) : "-";
            stringArray[217] = this.TCpotTotal.getText();
            stringArray[218] = this.TCpotKategori.getText();
            stringArray[219] = this.TNoRw.getText();
            if (this.Sequel.mengedittf("penilaian_awal_keperawatan_ranap_dewasa", "no_rawat=?", "tanggal=?, informasi_dari=?, tgl_tiba=?, nip=?, kd_dokter=?, diagnosa_masuk=?, suhu=?, td=?, nadi_utama=?, rr_utama=?, riwayat_keluarga=?, riwayat_pasien=?, deskripsi_penyakit=?, riwayat_sekarang=?, alat_bantu_igd=?, alat_bantu_persetujuan=?, alat_bantu_perintah=?, alergi=?, jenis_alergi=?, status_nikah=?, pendidikan=?, agama=?, warga_negara=?, pekerjaan=?, aktivitas=?, tinggal_bersama=?, ket_tinggal=?, tempat_tinggal=?, ket_tempat_tinggal=?, curiga=?, curiga_ya=?, curiga_tidak=?, budaya=?, budaya_ya=?, budaya_tidak=?, anak=?, jumlah_anak=?, bimbingan_ibadah=?, masalah_psiko_1=?, masalah_psiko_2=?, masalah_psiko_3=?, masalah_psiko_4=?, kondisi_psikologis=?, ket_psiko=?, status_mental=?, orientasi_orang=?, ket_ori_orang=?, orientasi_tempat=?, ket_ori_tempat=?, orientasi_waktu=?, ket_ori_waktu=?, orientasi_situasi=?, ket_ori_situasi=?, memori=?, pupil_kanan_uk=?, pupil_kanan_reflex=?, pupil_kiri_uk=?, pupil_kiri_reflex=?, gcs_e=?, gcs_m=?, gcs_v=?, gcs_jml=?, tanda_meningeal_kaku=?, tanda_meningeal_brudzinski=?, tanda_meningeal_kernig=?, tanda_meningeal_lain=?, masalah_neuro_1=?, masalah_neuro_2=?, jalan_nafas=?, benda_asing=?, airway_ukuran=?, pernafasan=?, bunyi_nafas=?, kesulitan_nafas=?, alat_nafas=?, oksigen=?, oksigen_ltr=?, oksigen_jenis=?, frekuensi_nafas=?, batuk=?, spo2=?, masalah_nafas_1=?, masalah_nafas_2=?, masalah_nafas_3=?, masalah_nafas_4=?, sirkulasi=?, crt=?, denyut_nadi=?, nadi=?, nadi_jelas=?, irama=?, pacemaker=?, pacemaker_jelas=?, akral=?, masalah_sirk_1=?, ket_masalah_sirk_1=?, masalah_sirk_2=?, ket_masalah_sirk_2=?, masalah_sirk_3=?, ket_masalah_sirk_3=?, bak=?, bak_lainnya=?, kateter=?, kateter_jelas=?, urin_jumlah=?, urin_warna=?, prostat=?, nyeri_pinggang=?, kelainan_kemih=?, kelainan_sebut=?, masalah_kemih_1=?, ket_masalah_kemih_1=?, status_ob_g=?, status_ob_p=?, status_ob_a=?, menstruasi=?, kehamilan=?, kehamilan_hpht=?, kehamilan_hpl=?, post_partum=?, lochea=?, lochea_jumlah=?, payudara=?, pengeluaran_asi=?, kontraksi=?, papsmear=?, papsmear_tgl=?, mammo=?, mammo_tgl=?, sadari=?, skrining_kanker=?, skrining_tgl=?, masalah_rep_1=?, masalah_rep_2=?, masalah_rep_3=?, masalah_rep_4=?, masalah_rep_lain=?, gejala_awal=?, kekerasan_fisik=?, turgor=?, rambut=?, kuku=?, luka=?, luka_dalam=?, perdarahan_integ=?, fraktur=?, lokasi=?, lokasi_lain=?, masalah_integ_1=?, ket_masalah_integ_1=?, masalah_integ_2=?, ket_masalah_integ_2=?, masalah_integ_3=?, ket_masalah_integ_3=?, masalah_integ_4=?, ket_masalah_integ_4=?, telinga=?, telinga_lainnya=?, hidung=?, tenggorokan=?, gigi=?, sakit_gigi=?, gigi_palsu=?, mata=?, mata_lainnya=?, masalah_tht=?, wasir=?, perdarahan_rectal=?, jenis_diit=?, feeding_tube=?, pembatasan_cairan=?, abdomen=?, bunyi_usus=?, bunyi_usus_freq=?, bab=?, bab_sejak=?, bab_freq=?, konsistensi=?, warna_cerna=?, pencahar=?, masalah_pencernaan=?, nyeri_tidak_ada=?, nyeri_ada=?, nyeri_akut=?, nyeri_kronis=?, nyeri_viseral=?, nyeri_somatis=?, nyeri_provokes_diam=?, nyeri_provokes_mobilisasi=?, nyeri_provokes_ditekan=?, nyeri_provokes_tiduran=?, nyeri_provokes_berdiri=?, nyeri_provokes_berjalan=?, nyeri_provokes_lainnya=?, nyeri_quality_tajam=?, nyeri_quality_tumpul=?, nyeri_quality_ditusuk=?, nyeri_quality_ditarik=?, nyeri_quality_dipukul=?, nyeri_quality_berdenyut=?, nyeri_quality_dibakar=?, nyeri_quality_ditikam=?, nyeri_quality_disayat=?, nyeri_quality_lainnya=?, nyeri_radiation=?, nyeri_radiation_lokasi=?, nyeri_severity_metode=?, nyeri_severity_skor=?, nyeri_severity_nyeri=?, nyeri_time_setiap=?, nyeri_time_selama=?, nyeri_time_sejak=?, cpot_ekspresi=?, cpot_gerakan=?, cpot_ketegangan=?, cpot_ventilator=?, cpot_vokalisasi=?, cpot_total=?, cpot_kategori=?", 220, stringArray)) {
                this.tampil();
                this.emptTeks();
            }
        }
    }

    private void hapus() {
        if (this.TNoRw.getText().trim().equals("")) {
            this.Valid.textKosong((JTextField)((Object)this.TNoRw), "No.Rawat");
        } else if (this.Sequel.meghapustf("penilaian_awal_keperawatan_ranap_dewasa", "no_rawat", this.TNoRw.getText())) {
            this.tampil();
            this.emptTeks();
        }
    }

    public void emptTeks() {
        this.TglAsesmen.setDate(new Date());
        this.TNoRM.setText("");
        this.TNmPasien.setText("");
        this.TglLahir.setText("");
        this.JK.setText("");
        this.TDiagnosaMasuk.setText("");
        this.TTD.setText("");
        this.TSuhu.setText("");
        this.TNadi.setText("");
        this.TRR.setText("");
        this.TRiwayatKeluarga.setText("");
        this.TRiwayatPasien.setText("");
        this.TDeskripsiPenyakit.setText("");
        this.TRiwayatSekarang.setText("");
        this.TJenisAlergi.setText("");
        this.TKetTinggal.setText("");
        this.TKetTempatTinggal.setText("");
        this.TJumlahAnak.setText("");
        this.TKetPsiko.setText("");
        this.TOriOrang.setText("");
        this.TOriTempat.setText("");
        this.TOriWaktu.setText("");
        this.TOriSituasi.setText("");
        this.TPupilKananUk.setText("");
        this.TPupilKananReflex.setText("");
        this.TPupilKiriUk.setText("");
        this.TPupilKiriReflex.setText("");
        this.TGCSEye.setText("");
        this.TGCSMotorik.setText("");
        this.TGCSVerbal.setText("");
        this.TGCSJumlah.setText("");
        this.TBendaAsing.setText("");
        this.TAirwayUkuran.setText("");
        this.TOksigenLtr.setText("");
        this.TFrekuensiNafas.setText("");
        this.TSpO2.setText("");
        this.TDenyutNadi.setText("");
        this.TNadiJelas.setText("");
        this.TPacemakerJelas.setText("");
        this.TMasalahSirkJelas1.setText("");
        this.TMasalahSirkJelas2.setText("");
        this.TMasalahSirkJelas3.setText("");
        this.TBAKLainnya.setText("");
        this.TKateterJelas.setText("");
        this.TUrinJumlah.setText("");
        this.TKelainanSebut.setText("");
        this.TMasalahKemihJelas1.setText("");
        this.TStatusObG.setText("");
        this.TStatusObP.setText("");
        this.TStatusObA.setText("");
        this.TPregnanHPHT.setText("");
        this.TPregnanHPL.setText("");
        this.TPostPartum.setText("");
        this.TLochea.setText("");
        this.TLocheaJumlah.setText("");
        this.TPayudara.setText("");
        this.TPengeluaranASI.setText("");
        this.TKontraksi.setText("");
        this.TPapsmearTgl.setText("");
        this.TMammoTgl.setText("");
        this.TSkriningTgl.setText("");
        this.TMasalahRepLain.setText("");
        this.TLukaDalam.setText("");
        this.TLokasiLain.setText("");
        this.TMasalahIntegJelas1.setText("");
        this.TMasalahIntegJelas2.setText("");
        this.TMasalahIntegJelas3.setText("");
        this.TMasalahIntegJelas4.setText("");
        this.TTelingaLainnya.setText("");
        this.TMataLainnya.setText("");
        this.TJenisDiit.setText("");
        this.TFeedingTube.setText("");
        this.TBunyiUsusFreq.setText("");
        this.TBABDiareSejak.setText("");
        this.TBABFreq.setText("");
        this.TWarnaCerna.setText("");
                                                this.TRadiationLokasi.setText("");
        this.TSeveritySkor.setText("");
        this.TSeverityNyeri.setText("");
        this.TTimeSejak.setText("");
        this.CmbRadiation.setSelectedIndex(0);
        this.CmbTimeSetiap.setSelectedIndex(0);
        this.CmbTimeSelama.setSelectedIndex(0);
        this.CmbSeverityMetode.setSelectedIndex(0);
        this.CmbCpotEkspresi.setSelectedIndex(0);
        this.CmbCpotGerakan.setSelectedIndex(0);
        this.CmbCpotKetegangan.setSelectedIndex(0);
        this.CmbCpotVentilator.setSelectedIndex(0);
        this.CmbCpotVokalisasi.setSelectedIndex(0);
        this.TCpotTotal.setText("0");
        this.TCpotKategori.setText("Tidak Nyeri");
                                        this.ChkAsesmen.setSelected(true);
        this.ChkTiba.setSelected(true);
        this.jam();
        this.BtnSimpan.setEnabled(true);
        this.BtnHapus.setEnabled(true);
        this.BtnEdit.setEnabled(true);
        this.BtnPrint.setEnabled(true);
    }

    
    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        tampil();
    }
private void tampil() {
        this.Valid.tabelKosong(this.tabMode);
        try {
                        this.ps = this.koneksi.prepareStatement("select reg_periksa.no_rkm_medis, pasien.nm_pasien, " +
                "if(pasien.jk='L','Laki-Laki','Perempuan') as jk, pasien.tgl_lahir, reg_periksa.kd_dokter, dokter.nm_dokter, " +
                "penilaian_awal_keperawatan_ranap_dewasa.* " +
                "from penilaian_awal_keperawatan_ranap_dewasa inner join reg_periksa on penilaian_awal_keperawatan_ranap_dewasa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter " +
                "where penilaian_awal_keperawatan_ranap_dewasa.tanggal between ? and ? and (penilaian_awal_keperawatan_ranap_dewasa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) order by penilaian_awal_keperawatan_ranap_dewasa.tanggal");
            this.ps.setString(1, this.Valid.SetTgl(this.DTPCari1.getSelectedItem() + "") + " 00:00:00");
            this.ps.setString(2, this.Valid.SetTgl(this.DTPCari2.getSelectedItem() + "") + " 23:59:59");
            this.ps.setString(3, "%" + this.TCari.getText() + "%");
            this.ps.setString(4, "%" + this.TCari.getText() + "%");
            this.ps.setString(5, "%" + this.TCari.getText() + "%");
            this.ps.setString(6, "%" + this.TCari.getText() + "%");
            try {
                this.rs = this.ps.executeQuery();
                while (this.rs.next()) {
                    this.tabMode.addRow(new String[]{this.rs.getString("no_rawat"), this.rs.getString("no_rkm_medis"), this.rs.getString("nm_pasien"), this.rs.getString("tgl_lahir"), this.rs.getString("jk"), this.rs.getString("kd_dokter"), this.rs.getString("nm_dokter"), this.rs.getString("tanggal"), this.rs.getString("informasi_dari"), this.rs.getString("tgl_tiba"), this.rs.getString("nip"), this.rs.getString("diagnosa_masuk"), this.rs.getString("suhu"), this.rs.getString("td"), this.rs.getString("nadi_utama"), this.rs.getString("rr_utama"), this.rs.getString("riwayat_keluarga"), this.rs.getString("riwayat_pasien"), this.rs.getString("deskripsi_penyakit"), this.rs.getString("riwayat_sekarang"), this.rs.getString("alat_bantu_igd"), this.rs.getString("alat_bantu_persetujuan"), this.rs.getString("alat_bantu_perintah"), this.rs.getString("alergi"), this.rs.getString("jenis_alergi"), this.rs.getString("status_nikah"), this.rs.getString("pendidikan"), this.rs.getString("agama"), this.rs.getString("warga_negara"), this.rs.getString("pekerjaan"), this.rs.getString("aktivitas"), this.rs.getString("tinggal_bersama"), this.rs.getString("ket_tinggal"), this.rs.getString("tempat_tinggal"), this.rs.getString("ket_tempat_tinggal"), this.rs.getString("curiga"), this.rs.getString("curiga_ya"), this.rs.getString("curiga_tidak"), this.rs.getString("budaya"), this.rs.getString("budaya_ya"), this.rs.getString("budaya_tidak"), this.rs.getString("anak"), this.rs.getString("jumlah_anak"), this.rs.getString("bimbingan_ibadah"), this.rs.getString("masalah_psiko_1"), this.rs.getString("masalah_psiko_2"), this.rs.getString("masalah_psiko_3"), this.rs.getString("masalah_psiko_4"), this.rs.getString("kondisi_psikologis"), this.rs.getString("ket_psiko"), this.rs.getString("status_mental"), this.rs.getString("orientasi_orang"), this.rs.getString("ket_ori_orang"), this.rs.getString("orientasi_tempat"), this.rs.getString("ket_ori_tempat"), this.rs.getString("orientasi_waktu"), this.rs.getString("ket_ori_waktu"), this.rs.getString("orientasi_situasi"), this.rs.getString("ket_ori_situasi"), this.rs.getString("memori"), this.rs.getString("pupil_kanan_uk"), this.rs.getString("pupil_kanan_reflex"), this.rs.getString("pupil_kiri_uk"), this.rs.getString("pupil_kiri_reflex"), this.rs.getString("gcs_e"), this.rs.getString("gcs_m"), this.rs.getString("gcs_v"), this.rs.getString("gcs_jml"), this.rs.getString("tanda_meningeal_kaku"), this.rs.getString("tanda_meningeal_brudzinski"), this.rs.getString("tanda_meningeal_kernig"), this.rs.getString("tanda_meningeal_lain"), this.rs.getString("masalah_neuro_1"), this.rs.getString("masalah_neuro_2"), this.rs.getString("jalan_nafas"), this.rs.getString("benda_asing"), this.rs.getString("airway_ukuran"), this.rs.getString("pernafasan"), this.rs.getString("bunyi_nafas"), this.rs.getString("kesulitan_nafas"), this.rs.getString("alat_nafas"), this.rs.getString("oksigen"), this.rs.getString("oksigen_ltr"), this.rs.getString("oksigen_jenis"), this.rs.getString("frekuensi_nafas"), this.rs.getString("batuk"), this.rs.getString("spo2"), this.rs.getString("masalah_nafas_1"), this.rs.getString("masalah_nafas_2"), this.rs.getString("masalah_nafas_3"), this.rs.getString("masalah_nafas_4"), this.rs.getString("sirkulasi"), this.rs.getString("crt"), this.rs.getString("denyut_nadi"), this.rs.getString("nadi"), this.rs.getString("nadi_jelas"), this.rs.getString("irama"), this.rs.getString("pacemaker"), this.rs.getString("pacemaker_jelas"), this.rs.getString("akral"), this.rs.getString("masalah_sirk_1"), this.rs.getString("ket_masalah_sirk_1"), this.rs.getString("masalah_sirk_2"), this.rs.getString("ket_masalah_sirk_2"), this.rs.getString("masalah_sirk_3"), this.rs.getString("ket_masalah_sirk_3"), this.rs.getString("bak"), this.rs.getString("bak_lainnya"), this.rs.getString("kateter"), this.rs.getString("kateter_jelas"), this.rs.getString("urin_jumlah"), this.rs.getString("urin_warna"), this.rs.getString("prostat"), this.rs.getString("nyeri_pinggang"), this.rs.getString("kelainan_kemih"), this.rs.getString("kelainan_sebut"), this.rs.getString("masalah_kemih_1"), this.rs.getString("ket_masalah_kemih_1"), this.rs.getString("status_ob_g"), this.rs.getString("status_ob_p"), this.rs.getString("status_ob_a"), this.rs.getString("menstruasi"), this.rs.getString("kehamilan"), this.rs.getString("kehamilan_hpht"), this.rs.getString("kehamilan_hpl"), this.rs.getString("post_partum"), this.rs.getString("lochea"), this.rs.getString("lochea_jumlah"), this.rs.getString("payudara"), this.rs.getString("pengeluaran_asi"), this.rs.getString("kontraksi"), this.rs.getString("papsmear"), this.rs.getString("papsmear_tgl"), this.rs.getString("mammo"), this.rs.getString("mammo_tgl"), this.rs.getString("sadari"), this.rs.getString("skrining_kanker"), this.rs.getString("skrining_tgl"), this.rs.getString("masalah_rep_1"), this.rs.getString("masalah_rep_2"), this.rs.getString("masalah_rep_3"), this.rs.getString("masalah_rep_4"), this.rs.getString("masalah_rep_lain"), this.rs.getString("gejala_awal"), this.rs.getString("kekerasan_fisik"), this.rs.getString("turgor"), this.rs.getString("rambut"), this.rs.getString("kuku"), this.rs.getString("luka"), this.rs.getString("luka_dalam"), this.rs.getString("perdarahan_integ"), this.rs.getString("fraktur"), this.rs.getString("lokasi"), this.rs.getString("lokasi_lain"), this.rs.getString("masalah_integ_1"), this.rs.getString("ket_masalah_integ_1"), this.rs.getString("masalah_integ_2"), this.rs.getString("ket_masalah_integ_2"), this.rs.getString("masalah_integ_3"), this.rs.getString("ket_masalah_integ_3"), this.rs.getString("masalah_integ_4"), this.rs.getString("ket_masalah_integ_4"), this.rs.getString("telinga"), this.rs.getString("telinga_lainnya"), this.rs.getString("hidung"), this.rs.getString("tenggorokan"), this.rs.getString("gigi"), this.rs.getString("sakit_gigi"), this.rs.getString("gigi_palsu"), this.rs.getString("mata"), this.rs.getString("mata_lainnya"), this.rs.getString("masalah_tht"), this.rs.getString("wasir"), this.rs.getString("perdarahan_rectal"), this.rs.getString("jenis_diit"), this.rs.getString("feeding_tube"), this.rs.getString("pembatasan_cairan"), this.rs.getString("abdomen"), this.rs.getString("bunyi_usus"), this.rs.getString("bunyi_usus_freq"), this.rs.getString("bab"), this.rs.getString("bab_sejak"), this.rs.getString("bab_freq"), this.rs.getString("konsistensi"), this.rs.getString("warna_cerna"), this.rs.getString("pencahar"), this.rs.getString("masalah_pencernaan"), this.rs.getString("nyeri_tidak_ada"), this.rs.getString("nyeri_ada"), this.rs.getString("nyeri_akut"), this.rs.getString("nyeri_kronis"), this.rs.getString("nyeri_viseral"), this.rs.getString("nyeri_somatis"), this.rs.getString("nyeri_provokes_diam"), this.rs.getString("nyeri_provokes_mobilisasi"), this.rs.getString("nyeri_provokes_ditekan"), this.rs.getString("nyeri_provokes_tiduran"), this.rs.getString("nyeri_provokes_berdiri"), this.rs.getString("nyeri_provokes_berjalan"), this.rs.getString("nyeri_provokes_lainnya"), this.rs.getString("nyeri_quality_tajam"), this.rs.getString("nyeri_quality_tumpul"), this.rs.getString("nyeri_quality_ditusuk"), this.rs.getString("nyeri_quality_ditarik"), this.rs.getString("nyeri_quality_dipukul"), this.rs.getString("nyeri_quality_berdenyut"), this.rs.getString("nyeri_quality_dibakar"), this.rs.getString("nyeri_quality_ditikam"), this.rs.getString("nyeri_quality_disayat"), this.rs.getString("nyeri_quality_lainnya"), this.rs.getString("nyeri_radiation"), this.rs.getString("nyeri_radiation_lokasi"), this.rs.getString("nyeri_severity_metode"), this.rs.getString("nyeri_severity_skor"), this.rs.getString("nyeri_severity_nyeri"), this.rs.getString("nyeri_time_setiap"), this.rs.getString("nyeri_time_selama"), this.rs.getString("nyeri_time_sejak"), this.rs.getString("cpot_ekspresi"), this.rs.getString("cpot_gerakan"), this.rs.getString("cpot_ketegangan"), this.rs.getString("cpot_ventilator"), this.rs.getString("cpot_vokalisasi"), this.rs.getString("cpot_total"), this.rs.getString("cpot_kategori")});
                }
            }
            catch (Exception e) {
                System.out.println("Notif : " + e);
            }
            finally {
                if (this.rs != null) {
                    this.rs.close();
                }
                if (this.ps != null) {
                    this.ps.close();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void getData() {
        if (this.tbData.getSelectedRow() != -1) {
            block14: {
                this.TNoRw.setText(this.tbData.getValueAt(this.tbData.getSelectedRow(), 0).toString());
                this.KdDokter.setText(this.tbData.getValueAt(this.tbData.getSelectedRow(), 5).toString());
                this.NmDokter.setText(this.tbData.getValueAt(this.tbData.getSelectedRow(), 6).toString());
                this.isRawat();
                try {
                    this.ps = this.koneksi.prepareStatement("SELECT * FROM penilaian_awal_keperawatan_ranap_dewasa WHERE no_rawat=?");
                    try {
                        this.ps.setString(1, this.TNoRw.getText());
                        this.rs = this.ps.executeQuery();
                        if (!this.rs.next()) break block14;
                        this.Valid.SetTgl(this.TglAsesmen, this.rs.getString("tanggal"));
                        try {
                            this.JamAsesmen.setSelectedItem(this.rs.getString("tanggal").substring(11, 13));
                            this.MenitAsesmen.setSelectedItem(this.rs.getString("tanggal").substring(14, 16));
                            this.DetikAsesmen.setSelectedItem(this.rs.getString("tanggal").substring(17, 19));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.Valid.SetTgl(this.TglTiba, this.rs.getString("tgl_tiba"));
                        try {
                            this.JamTiba.setSelectedItem(this.rs.getString("tgl_tiba").substring(11, 13));
                            this.MenitTiba.setSelectedItem(this.rs.getString("tgl_tiba").substring(14, 16));
                            this.DetikTiba.setSelectedItem(this.rs.getString("tgl_tiba").substring(17, 19));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.TDiagnosaMasuk.setText(this.rs.getString("diagnosa_masuk"));
                        this.TSuhu.setText(this.rs.getString("suhu"));
                        this.TTD.setText(this.rs.getString("td"));
                        this.TNadi.setText(this.rs.getString("nadi_utama"));
                        this.TRR.setText(this.rs.getString("rr_utama"));
                        this.TRiwayatKeluarga.setText(this.rs.getString("riwayat_keluarga"));
                        this.TRiwayatPasien.setText(this.rs.getString("riwayat_pasien"));
                        this.TDeskripsiPenyakit.setText(this.rs.getString("deskripsi_penyakit"));
                        this.TRiwayatSekarang.setText(this.rs.getString("riwayat_sekarang"));
                        this.CmbAlergi.setSelectedItem(this.rs.getString("alergi"));
                        this.TJenisAlergi.setText(this.rs.getString("jenis_alergi"));
                        this.TStatusNikah.setText(this.rs.getString("status_nikah"));
                        this.TPendidikan.setText(this.rs.getString("pendidikan"));
                        this.TAgama.setText(this.rs.getString("agama"));
                        this.CmbWargaNegara.setSelectedItem(this.rs.getString("warga_negara"));
                        this.CmbPekerjaan.setSelectedItem(this.rs.getString("pekerjaan"));
                        this.CmbAktivitas.setSelectedItem(this.rs.getString("aktivitas"));
                        this.CmbTinggalBersama.setSelectedItem(this.rs.getString("tinggal_bersama"));
                        this.TKetTinggal.setText(this.rs.getString("ket_tinggal"));
                        this.CmbTempatTinggal.setSelectedItem(this.rs.getString("tempat_tinggal"));
                        this.TKetTempatTinggal.setText(this.rs.getString("ket_tempat_tinggal"));
                        this.CmbCuriga.setSelectedItem(this.rs.getString("curiga"));
                        this.CmbBudaya.setSelectedItem(this.rs.getString("budaya"));
                        this.CmbAnak.setSelectedItem(this.rs.getString("anak"));
                        this.TJumlahAnak.setText(this.rs.getString("jumlah_anak"));
                        this.CmbBimbinganIbadah.setSelectedItem(this.rs.getString("bimbingan_ibadah"));
                        this.CmbPsiko.setSelectedItem(this.rs.getString("kondisi_psikologis"));
                        this.TKetPsiko.setText(this.rs.getString("ket_psiko"));
                        this.TOriOrang.setText(this.rs.getString("ket_ori_orang"));
                        this.TOriTempat.setText(this.rs.getString("ket_ori_tempat"));
                        this.TOriWaktu.setText(this.rs.getString("ket_ori_waktu"));
                        this.TOriSituasi.setText(this.rs.getString("ket_ori_situasi"));
                        this.CmbMemori.setSelectedItem(this.rs.getString("memori"));
                        this.TPupilKananUk.setText(this.rs.getString("pupil_kanan_uk"));
                        this.TPupilKananReflex.setText(this.rs.getString("pupil_kanan_reflex"));
                        this.TPupilKiriUk.setText(this.rs.getString("pupil_kiri_uk"));
                        this.TPupilKiriReflex.setText(this.rs.getString("pupil_kiri_reflex"));
                        this.TGCSEye.setText(this.rs.getString("gcs_e"));
                        this.TGCSMotorik.setText(this.rs.getString("gcs_m"));
                        this.TGCSVerbal.setText(this.rs.getString("gcs_v"));
                        this.TGCSJumlah.setText(this.rs.getString("gcs_jml"));
                        this.TBendaAsing.setText(this.rs.getString("benda_asing"));
                        this.TAirwayUkuran.setText(this.rs.getString("airway_ukuran"));
                        this.TOksigenLtr.setText(this.rs.getString("oksigen_ltr"));
                        this.CmbOksigenJenis.setSelectedItem(this.rs.getString("oksigen_jenis"));
                        this.TFrekuensiNafas.setText(this.rs.getString("frekuensi_nafas"));
                        this.TSpO2.setText(this.rs.getString("spo2"));
                        this.TDenyutNadi.setText(this.rs.getString("denyut_nadi"));
                        this.TNadiJelas.setText(this.rs.getString("nadi_jelas"));
                        this.TPacemakerJelas.setText(this.rs.getString("pacemaker_jelas"));
                        this.TMasalahSirkJelas1.setText(this.rs.getString("ket_masalah_sirk_1"));
                        this.TMasalahSirkJelas2.setText(this.rs.getString("ket_masalah_sirk_2"));
                        this.TMasalahSirkJelas3.setText(this.rs.getString("ket_masalah_sirk_3"));
                        String val_15 = this.rs.getString("alat_bantu_igd");
                        this.ChkIGD.setSelected("Ya".equals(val_15));
                        String val_16 = this.rs.getString("alat_bantu_persetujuan");
                        this.ChkPersetujuan.setSelected("Ya".equals(val_16));
                        String val_17 = this.rs.getString("alat_bantu_perintah");
                        this.ChkPerintah.setSelected("Ya".equals(val_17));
                        String val_31 = this.rs.getString("curiga_ya");
                        this.ChkCurigaYa.setSelected("Ya".equals(val_31));
                        String val_32 = this.rs.getString("curiga_tidak");
                        this.ChkCurigaTidak.setSelected("Ya".equals(val_32));
                        String val_34 = this.rs.getString("budaya_ya");
                        this.ChkBudayaYa.setSelected("Ya".equals(val_34));
                        String val_35 = this.rs.getString("budaya_tidak");
                        this.ChkBudayaTidak.setSelected("Ya".equals(val_35));
                        String val_39 = this.rs.getString("masalah_psiko_1");
                        this.ChkMasalahPsiko1.setSelected("Ya".equals(val_39));
                        String val_40 = this.rs.getString("masalah_psiko_2");
                        this.ChkMasalahPsiko2.setSelected("Ya".equals(val_40));
                        String val_41 = this.rs.getString("masalah_psiko_3");
                        this.ChkMasalahPsiko3.setSelected("Ya".equals(val_41));
                        String val_42 = this.rs.getString("masalah_psiko_4");
                        this.ChkMasalahPsiko4.setSelected("Ya".equals(val_42));
                        String val_45 = this.rs.getString("status_mental");
                        this.RdoStatusCM.setSelected("CM".equals(val_45));
                        this.RdoStatusBingung.setSelected("Bingung".equals(val_45));
                        this.RdoStatusMengantuk.setSelected("Mengantuk".equals(val_45));
                        this.RdoStatusApatis.setSelected("Apatis".equals(val_45));
                        this.RdoStatusTidakRespon.setSelected("Tidak respon".equals(val_45));
                        String val_46 = this.rs.getString("orientasi_orang");
                        this.RdoOriOrang.setSelected("Baik".equals(val_46));
                        String val_48 = this.rs.getString("orientasi_tempat");
                        this.RdoOriTempat.setSelected("Baik".equals(val_48));
                        String val_50 = this.rs.getString("orientasi_waktu");
                        this.RdoOriWaktu.setSelected("Baik".equals(val_50));
                        String val_52 = this.rs.getString("orientasi_situasi");
                        this.RdoOriSituasi.setSelected("Baik".equals(val_52));
                        String val_63 = this.rs.getString("tanda_meningeal_kaku");
                        this.RdoKakuKuduk.setSelected("Ya".equals(val_63));
                        String val_64 = this.rs.getString("tanda_meningeal_brudzinski");
                        this.RdoBrudzinski.setSelected("Ya".equals(val_64));
                        String val_65 = this.rs.getString("tanda_meningeal_kernig");
                        this.RdoKernig.setSelected("Ya".equals(val_65));
                        String val_66 = this.rs.getString("tanda_meningeal_lain");
                        this.RdoNeuroLain.setSelected("Ya".equals(val_66));
                        String val_67 = this.rs.getString("masalah_neuro_1");
                        this.ChkMasalahNeuro1.setSelected("Ya".equals(val_67));
                        String val_68 = this.rs.getString("masalah_neuro_2");
                        this.ChkMasalahNeuro2.setSelected("Ya".equals(val_68));
                        String val_69 = this.rs.getString("jalan_nafas");
                        this.RdoAirway1.setSelected("Bebas".equals(val_69));
                        this.RdoAirway2.setSelected("Sumbatan".equals(val_69));
                        this.RdoAirway3.setSelected("Spasme".equals(val_69));
                        this.RdoAirway4.setSelected("Lainnya".equals(val_69));
                        String val_72 = this.rs.getString("pernafasan");
                        this.RdoBreath1.setSelected("Normal".equals(val_72));
                        this.RdoBreath2.setSelected("Kusmaul".equals(val_72));
                        this.RdoBreath3.setSelected("Biot".equals(val_72));
                        this.RdoBreath4.setSelected("Cheyne Stokes".equals(val_72));
                        this.RdoBreath5.setSelected("Lainnya".equals(val_72));
                        String val_73 = this.rs.getString("bunyi_nafas");
                        this.RdoBunyi1.setSelected("Vesikuler".equals(val_73));
                        this.RdoBunyi2.setSelected("Wheezing".equals(val_73));
                        this.RdoBunyi3.setSelected("Ronchi".equals(val_73));
                        this.RdoBunyi4.setSelected("Krekels".equals(val_73));
                        this.RdoBunyi5.setSelected("Lainnya".equals(val_73));
                        String val_74 = this.rs.getString("kesulitan_nafas");
                        this.RdoSulitNafas1.setSelected("Tidak".equals(val_74));
                        this.RdoSulitNafas2.setSelected("Dyspnea".equals(val_74));
                        this.RdoSulitNafas3.setSelected("Ortopnea".equals(val_74));
                        String val_75 = this.rs.getString("alat_nafas");
                        this.RdoAlatNafas1.setSelected("Tidak".equals(val_75));
                        this.RdoAlatNafas2.setSelected("Ya".equals(val_75));
                        String val_76 = this.rs.getString("oksigen");
                        this.RdoOksigen1.setSelected("Tidak".equals(val_76));
                        this.RdoOksigen2.setSelected("Ya".equals(val_76));
                        String val_80 = this.rs.getString("batuk");
                        this.RdoBatuk1.setSelected("Tidak".equals(val_80));
                        this.RdoBatuk2.setSelected("Ya".equals(val_80));
                        this.RdoBatuk3.setSelected("Berdahak".equals(val_80));
                        this.RdoBatuk4.setSelected("Tidak berdahak".equals(val_80));
                        String val_82 = this.rs.getString("masalah_nafas_1");
                        this.ChkMasalahNafas1.setSelected("Ya".equals(val_82));
                        String val_83 = this.rs.getString("masalah_nafas_2");
                        this.ChkMasalahNafas2.setSelected("Ya".equals(val_83));
                        String val_84 = this.rs.getString("masalah_nafas_3");
                        this.ChkMasalahNafas3.setSelected("Ya".equals(val_84));
                        String val_85 = this.rs.getString("masalah_nafas_4");
                        this.ChkMasalahNafas4.setSelected("Ya".equals(val_85));
                        String val_86 = this.rs.getString("sirkulasi");
                        this.RdoSirkulasi1.setSelected("Normal".equals(val_86));
                        this.RdoSirkulasi2.setSelected("Pucat".equals(val_86));
                        this.RdoSirkulasi3.setSelected("Sianosis".equals(val_86));
                        this.RdoSirkulasi4.setSelected("Berkeringat".equals(val_86));
                        this.RdoSirkulasi5.setSelected("Lainnya".equals(val_86));
                        String val_87 = this.rs.getString("crt");
                        this.RdoCRT1.setSelected("< 2 detik".equals(val_87));
                        this.RdoCRT2.setSelected("> 2 detik".equals(val_87));
                        String val_89 = this.rs.getString("nadi");
                        this.RdoNadi1.setSelected("Kuat".equals(val_89));
                        this.RdoNadi2.setSelected("Lemah".equals(val_89));
                        this.RdoNadi3.setSelected("Tidak teraba".equals(val_89));
                        String val_91 = this.rs.getString("irama");
                        this.RdoIrama1.setSelected("Teratur".equals(val_91));
                        this.RdoIrama2.setSelected("Tidak teratur".equals(val_91));
                        String val_92 = this.rs.getString("pacemaker");
                        this.RdoPacemaker1.setSelected("Tidak".equals(val_92));
                        this.RdoPacemaker2.setSelected("Ya".equals(val_92));
                        String val_94 = this.rs.getString("akral");
                        this.RdoAkral1.setSelected("Hangat".equals(val_94));
                        this.RdoAkral2.setSelected("Dingin".equals(val_94));
                        String val_95 = this.rs.getString("masalah_sirk_1");
                        this.ChkMasalahSirk1.setSelected("Ya".equals(val_95));
                        String val_97 = this.rs.getString("masalah_sirk_2");
                        this.ChkMasalahSirk2.setSelected("Ya".equals(val_97));
                        String val_99 = this.rs.getString("masalah_sirk_3");
                        this.ChkMasalahSirk3.setSelected("Ya".equals(val_99));
                        String val_101 = this.rs.getString("bak");
                        this.RdoBAK1.setSelected("Normal".equals(val_101));
                        this.RdoBAK2.setSelected("Inkontinensia".equals(val_101));
                        this.RdoBAK3.setSelected("Retensi".equals(val_101));
                        this.RdoBAK4.setSelected("Anuria".equals(val_101));
                        this.RdoBAK5.setSelected("Disuria".equals(val_101));
                        this.RdoBAK6.setSelected("Oliguria".equals(val_101));
                        this.RdoBAK7.setSelected("Lainnya".equals(val_101));
                        String val_103 = this.rs.getString("kateter");
                        this.RdoKateter1.setSelected("Tidak".equals(val_103));
                        this.RdoKateter2.setSelected("Ya".equals(val_103));
                        String val_106 = this.rs.getString("urin_warna");
                        this.RdoUrin1.setSelected("Kuning jernih".equals(val_106));
                        this.RdoUrin2.setSelected("Kuning pekat".equals(val_106));
                        this.RdoUrin3.setSelected("Lainnya".equals(val_106));
                        String val_107 = this.rs.getString("prostat");
                        this.RdoProstat1.setSelected("Normal".equals(val_107));
                        this.RdoProstat2.setSelected("Membesar".equals(val_107));
                        String val_108 = this.rs.getString("nyeri_pinggang");
                        this.RdoNyeriPinggang1.setSelected("Tidak".equals(val_108));
                        this.RdoNyeriPinggang2.setSelected("Ya".equals(val_108));
                        String val_109 = this.rs.getString("kelainan_kemih");
                        this.RdoKelainan1.setSelected("Tidak".equals(val_109));
                        this.RdoKelainan2.setSelected("Ya".equals(val_109));
                        String val_111 = this.rs.getString("masalah_kemih_1");
                        this.ChkMasalahKemih1.setSelected("Ya".equals(val_111));
                        String val_116 = this.rs.getString("menstruasi");
                        this.RdoMens1.setSelected("Tidak".equals(val_116));
                        this.RdoMens2.setSelected("Teratur".equals(val_116));
                        this.RdoMens3.setSelected("Tidak teratur".equals(val_116));
                        this.RdoMens4.setSelected("Menopause".equals(val_116));
                        String val_117 = this.rs.getString("kehamilan");
                        this.RdoPregnan1.setSelected("Tidak".equals(val_117));
                        this.RdoPregnan2.setSelected("Ya".equals(val_117));
                        String val_126 = this.rs.getString("papsmear");
                        this.RdoPapsmear1.setSelected("Tidak".equals(val_126));
                        this.RdoPapsmear2.setSelected("Ya".equals(val_126));
                        String val_128 = this.rs.getString("mammo");
                        this.RdoMammo1.setSelected("Tidak".equals(val_128));
                        this.RdoMammo2.setSelected("Ya".equals(val_128));
                        String val_130 = this.rs.getString("sadari");
                        this.RdoSadari1.setSelected("Tidak".equals(val_130));
                        this.RdoSadari2.setSelected("Ya".equals(val_130));
                        String val_131 = this.rs.getString("skrining_kanker");
                        this.RdoSkrining1.setSelected("Tidak".equals(val_131));
                        this.RdoSkrining2.setSelected("Ya".equals(val_131));
                        String val_133 = this.rs.getString("masalah_rep_1");
                        this.ChkMasalahRep1.setSelected("Ya".equals(val_133));
                        String val_134 = this.rs.getString("masalah_rep_2");
                        this.ChkMasalahRep2.setSelected("Ya".equals(val_134));
                        String val_135 = this.rs.getString("masalah_rep_3");
                        this.ChkMasalahRep3.setSelected("Ya".equals(val_135));
                        String val_136 = this.rs.getString("masalah_rep_4");
                        this.ChkMasalahRep4.setSelected("Ya".equals(val_136));
                        String val_138 = this.rs.getString("gejala_awal");
                        this.RdoInteg1.setSelected("Tidak masalah".equals(val_138));
                        this.RdoInteg2.setSelected("Rash".equals(val_138));
                        this.RdoInteg3.setSelected("Lesi".equals(val_138));
                        this.RdoInteg4.setSelected("Memar".equals(val_138));
                        this.RdoInteg5.setSelected("Banyak keringat".equals(val_138));
                        String val_139 = this.rs.getString("kekerasan_fisik");
                        this.RdoKekerasan1.setSelected("Indikasi kekerasan fisik".equals(val_139));
                        this.RdoKekerasan2.setSelected("Pucat".equals(val_139));
                        this.RdoKekerasan3.setSelected("Sianosis".equals(val_139));
                        String val_140 = this.rs.getString("turgor");
                        this.RdoTurgor1.setSelected("Baik".equals(val_140));
                        this.RdoTurgor2.setSelected("Sedang".equals(val_140));
                        this.RdoTurgor3.setSelected("Buruk".equals(val_140));
                        String val_141 = this.rs.getString("rambut");
                        this.RdoRambut1.setSelected("Bersih".equals(val_141));
                        this.RdoRambut2.setSelected("Kotor".equals(val_141));
                        String val_142 = this.rs.getString("kuku");
                        this.RdoKuku1.setSelected("Bersih".equals(val_142));
                        this.RdoKuku2.setSelected("Kotor".equals(val_142));
                        String val_143 = this.rs.getString("luka");
                        this.RdoLuka1.setSelected("Tidak".equals(val_143));
                        this.RdoLuka2.setSelected("Ya".equals(val_143));
                        String val_145 = this.rs.getString("perdarahan_integ");
                        this.RdoPerdarahan1.setSelected("Tidak".equals(val_145));
                        this.RdoPerdarahan2.setSelected("Ya".equals(val_145));
                        String val_146 = this.rs.getString("fraktur");
                        this.RdoFraktur1.setSelected("Tidak".equals(val_146));
                        this.RdoFraktur2.setSelected("Ya".equals(val_146));
                        this.RdoFraktur3.setSelected("Tertutup".equals(val_146));
                        this.RdoFraktur4.setSelected("Terbuka".equals(val_146));
                        String val_147 = this.rs.getString("lokasi");
                        this.RdoLokasi1.setSelected("Extremitas atas".equals(val_147));
                        this.RdoLokasi2.setSelected("Extremitas bawah".equals(val_147));
                        this.RdoLokasi3.setSelected("Batang Tubuh".equals(val_147));
                        this.RdoLokasi4.setSelected("Lain-lain".equals(val_147));
                        String val_149 = this.rs.getString("masalah_integ_1");
                        this.ChkMasalahInteg1.setSelected("Ya".equals(val_149));
                        String val_151 = this.rs.getString("masalah_integ_2");
                        this.ChkMasalahInteg2.setSelected("Ya".equals(val_151));
                        String val_153 = this.rs.getString("masalah_integ_3");
                        this.ChkMasalahInteg3.setSelected("Ya".equals(val_153));
                        String val_155 = this.rs.getString("masalah_integ_4");
                        this.ChkMasalahInteg4.setSelected("Ya".equals(val_155));
                        String val_157 = this.rs.getString("telinga");
                        this.RdoTelinga1.setSelected("Normal".equals(val_157));
                        this.RdoTelinga2.setSelected("Alat bantu dengar".equals(val_157));
                        this.RdoTelinga3.setSelected("Lainnya".equals(val_157));
                        String val_159 = this.rs.getString("hidung");
                        this.RdoHidung1.setSelected("Normal".equals(val_159));
                        this.RdoHidung2.setSelected("Sinusitis".equals(val_159));
                        this.RdoHidung3.setSelected("Polip".equals(val_159));
                        this.RdoHidung4.setSelected("Epistaksis".equals(val_159));
                        this.RdoHidung5.setSelected("Lainnya".equals(val_159));
                        String val_160 = this.rs.getString("tenggorokan");
                        this.RdoTenggorokan1.setSelected("Normal".equals(val_160));
                        this.RdoTenggorokan2.setSelected("Nyeri telan".equals(val_160));
                        this.RdoTenggorokan3.setSelected("Tonsilitis".equals(val_160));
                        String val_161 = this.rs.getString("gigi");
                        this.RdoGigi1.setSelected("Bersih".equals(val_161));
                        this.RdoGigi2.setSelected("Karies".equals(val_161));
                        this.RdoGigi3.setSelected("Karang gigi".equals(val_161));
                        this.RdoGigi4.setSelected("Kotor".equals(val_161));
                        this.RdoGigi5.setSelected("Ompong".equals(val_161));
                        this.RdoGigi6.setSelected("Lengkap".equals(val_161));
                        String val_162 = this.rs.getString("sakit_gigi");
                        this.RdoSakitGigi1.setSelected("Tidak".equals(val_162));
                        this.RdoSakitGigi2.setSelected("Ya".equals(val_162));
                        String val_163 = this.rs.getString("gigi_palsu");
                        this.RdoGigiPalsu1.setSelected("Tidak".equals(val_163));
                        this.RdoGigiPalsu2.setSelected("Ya".equals(val_163));
                        String val_164 = this.rs.getString("mata");
                        this.RdoMata1.setSelected("Normal".equals(val_164));
                        this.RdoMata2.setSelected("Kering".equals(val_164));
                        this.RdoMata3.setSelected("Buta".equals(val_164));
                        this.RdoMata4.setSelected("Katarak".equals(val_164));
                        this.RdoMata5.setSelected("Glaukoma".equals(val_164));
                        this.RdoMata6.setSelected("Rabun Jauh".equals(val_164));
                        this.RdoMata7.setSelected("Rabun dekat".equals(val_164));
                        this.RdoMata8.setSelected("Konjungtivitis".equals(val_164));
                        this.RdoMata9.setSelected("Kaca mata".equals(val_164));
                        this.RdoMata10.setSelected("Lainnya".equals(val_164));
                        String val_166 = this.rs.getString("masalah_tht");
                        this.ChkMasalahTHTMata1.setSelected("Ya".equals(val_166));
                        String val_167 = this.rs.getString("wasir");
                        this.RdoWasir1.setSelected("Ya".equals(val_167));
                        this.RdoWasir2.setSelected("Tidak".equals(val_167));
                        String val_168 = this.rs.getString("perdarahan_rectal");
                        this.RdoPerdarahanRectal1.setSelected("Ya".equals(val_168));
                        this.RdoPerdarahanRectal2.setSelected("Tidak".equals(val_168));
                        String val_171 = this.rs.getString("pembatasan_cairan");
                        this.RdoPembatasanCairan1.setSelected("Ya".equals(val_171));
                        this.RdoPembatasanCairan2.setSelected("Tidak".equals(val_171));
                        String val_172 = this.rs.getString("abdomen");
                        this.RdoAbdomen1.setSelected("Supel".equals(val_172));
                        this.RdoAbdomen2.setSelected("Distensi".equals(val_172));
                        this.RdoAbdomen3.setSelected("Kembung".equals(val_172));
                        String val_173 = this.rs.getString("bunyi_usus");
                        this.RdoBunyiUsus1.setSelected("Normal".equals(val_173));
                        this.RdoBunyiUsus2.setSelected("Tidak ada".equals(val_173));
                        this.RdoBunyiUsus3.setSelected("Frekuensi".equals(val_173));
                        String val_175 = this.rs.getString("bab");
                        this.RdoBAB1.setSelected("Normal".equals(val_175));
                        this.RdoBAB2.setSelected("Diare, sejak :".equals(val_175));
                        this.RdoBAB3.setSelected("Frekwensi".equals(val_175));
                        String val_178 = this.rs.getString("konsistensi");
                        this.RdoKonsistensi1.setSelected("Padat".equals(val_178));
                        this.RdoKonsistensi2.setSelected("Cair".equals(val_178));
                        this.RdoKonsistensi3.setSelected("Lembek".equals(val_178));
                        this.RdoKonsistensi4.setSelected("Berlendir".equals(val_178));
                        String val_180 = this.rs.getString("pencahar");
                        this.RdoPencahar1.setSelected("Ya".equals(val_180));
                        this.RdoPencahar2.setSelected("Tidak".equals(val_180));
                        String val_181 = this.rs.getString("masalah_pencernaan");
                        this.ChkMasalahPencernaan1.setSelected("Ya".equals(val_181));
                        String val_182 = this.rs.getString("nyeri_tidak_ada");
                        this.RdoNyeriTidakAda.setSelected("Ya".equals(val_182));
                        String val_183 = this.rs.getString("nyeri_ada");
                        this.RdoNyeriAda.setSelected("Ya".equals(val_183));
                        String val_184 = this.rs.getString("nyeri_akut");
                        this.RdoNyeriAkut.setSelected("Ya".equals(val_184));
                        String val_185 = this.rs.getString("nyeri_kronis");
                        this.RdoNyeriKronis.setSelected("Ya".equals(val_185));
                        String val_186 = this.rs.getString("nyeri_viseral");
                        this.RdoNyeriViseral.setSelected("Ya".equals(val_186));
                        String val_187 = this.rs.getString("nyeri_somatis");
                        this.RdoNyeriSomatis.setSelected("Ya".equals(val_187));
                        String val_188 = this.rs.getString("nyeri_provokes_diam");
                        this.RdoProvokesDiam.setSelected("Ya".equals(val_188));
                        String val_189 = this.rs.getString("nyeri_provokes_mobilisasi");
                        this.RdoProvokesMobilisasi.setSelected("Ya".equals(val_189));
                        String val_190 = this.rs.getString("nyeri_provokes_ditekan");
                        this.RdoProvokesDitekan.setSelected("Ya".equals(val_190));
                        String val_191 = this.rs.getString("nyeri_provokes_tiduran");
                        this.RdoProvokesTiduran.setSelected("Ya".equals(val_191));
                        String val_192 = this.rs.getString("nyeri_provokes_berdiri");
                        this.RdoProvokesBerdiri.setSelected("Ya".equals(val_192));
                        String val_193 = this.rs.getString("nyeri_provokes_berjalan");
                        this.RdoProvokesBerjalan.setSelected("Ya".equals(val_193));
                        String val_195 = this.rs.getString("nyeri_quality_tajam");
                        this.RdoQualityTajam.setSelected("Ya".equals(val_195));
                        String val_196 = this.rs.getString("nyeri_quality_tumpul");
                        this.RdoQualityTumpul.setSelected("Ya".equals(val_196));
                        String val_197 = this.rs.getString("nyeri_quality_ditusuk");
                        this.RdoQualityDitusuk.setSelected("Ya".equals(val_197));
                        String val_198 = this.rs.getString("nyeri_quality_ditarik");
                        this.RdoQualityDitarik.setSelected("Ya".equals(val_198));
                        String val_199 = this.rs.getString("nyeri_quality_dipukul");
                        this.RdoQualityDipukul.setSelected("Ya".equals(val_199));
                        String val_200 = this.rs.getString("nyeri_quality_berdenyut");
                        this.RdoQualityBerdenyut.setSelected("Ya".equals(val_200));
                        String val_201 = this.rs.getString("nyeri_quality_dibakar");
                        this.RdoQualityDibakar.setSelected("Ya".equals(val_201));
                        String val_202 = this.rs.getString("nyeri_quality_ditikam");
                        this.RdoQualityDitikam.setSelected("Ya".equals(val_202));
                        String val_203 = this.rs.getString("nyeri_quality_disayat");
                        this.RdoQualityDisayat.setSelected("Ya".equals(val_203));
                        String val_194 = this.rs.getString("nyeri_provokes_lainnya");
                        this.TProvokesLainnya.setText(val_194);
                        String val_204 = this.rs.getString("nyeri_quality_lainnya");
                        this.TQualityLainnya.setText(val_204);
                        String val_205 = this.rs.getString("nyeri_radiation");
                        this.CmbRadiation.setSelectedItem(val_205);
                        String val_206 = this.rs.getString("nyeri_radiation_lokasi");
                        this.TRadiationLokasi.setText(val_206);
                        String val_207 = this.rs.getString("nyeri_severity_metode");
                        this.CmbSeverityMetode.setSelectedItem(val_207);
                        String val_208 = this.rs.getString("nyeri_severity_skor");
                        this.TSeveritySkor.setText(val_208);
                        if (val_208 != null) {
                            switch(val_208) {
                                case "0": this.RdoSkor0.setSelected(true); break;
                                case "1": this.RdoSkor1.setSelected(true); break;
                                case "2": this.RdoSkor2.setSelected(true); break;
                                case "3": this.RdoSkor3.setSelected(true); break;
                                case "4": this.RdoSkor4.setSelected(true); break;
                                case "5": this.RdoSkor5.setSelected(true); break;
                                case "6": this.RdoSkor6.setSelected(true); break;
                                case "7": this.RdoSkor7.setSelected(true); break;
                                case "8": this.RdoSkor8.setSelected(true); break;
                                case "9": this.RdoSkor9.setSelected(true); break;
                                case "10": this.RdoSkor10.setSelected(true); break;
                            }
                        }
                        String val_209 = this.rs.getString("nyeri_severity_nyeri");
                        this.TSeverityNyeri.setText(val_209);
                        String val_210 = this.rs.getString("nyeri_time_setiap");
                        this.CmbTimeSetiap.setSelectedItem(val_210);
                        String val_211 = this.rs.getString("nyeri_time_selama");
                        this.CmbTimeSelama.setSelectedItem(val_211);
                        String val_212 = this.rs.getString("nyeri_time_sejak");
                        this.TTimeSejak.setText(val_212);
                        String val_213 = this.rs.getString("cpot_ekspresi");
                        try { this.CmbCpotEkspresi.setSelectedIndex(Integer.parseInt(val_213)); } catch(Exception e) {}
                        String val_214 = this.rs.getString("cpot_gerakan");
                        try { this.CmbCpotGerakan.setSelectedIndex(Integer.parseInt(val_214)); } catch(Exception e) {}
                        String val_215 = this.rs.getString("cpot_ketegangan");
                        try { this.CmbCpotKetegangan.setSelectedIndex(Integer.parseInt(val_215)); } catch(Exception e) {}
                        String val_216 = this.rs.getString("cpot_ventilator");
                        if ("-".equals(val_216)) { this.CmbCpotVentilator.setSelectedIndex(0); } else { try { this.CmbCpotVentilator.setSelectedIndex(Integer.parseInt(val_216) + 1); } catch(Exception e) {} }
                        String val_217 = this.rs.getString("cpot_vokalisasi");
                        if ("-".equals(val_217)) { this.CmbCpotVokalisasi.setSelectedIndex(0); } else { try { this.CmbCpotVokalisasi.setSelectedIndex(Integer.parseInt(val_217) + 1); } catch(Exception e) {} }
                        String val_218 = this.rs.getString("cpot_total");
                        this.TCpotTotal.setText(val_218);
                        String val_219 = this.rs.getString("cpot_kategori");
                        this.TCpotKategori.setText(val_219);

                        this.TBAKLainnya.setText(this.rs.getString("bak_lainnya"));
                        this.TKateterJelas.setText(this.rs.getString("kateter_jelas"));
                        this.TUrinJumlah.setText(this.rs.getString("urin_jumlah"));
                        this.TKelainanSebut.setText(this.rs.getString("kelainan_sebut"));
                        this.TMasalahKemihJelas1.setText(this.rs.getString("ket_masalah_kemih_1"));
                        this.TStatusObG.setText(this.rs.getString("status_ob_g"));
                        this.TStatusObP.setText(this.rs.getString("status_ob_p"));
                        this.TStatusObA.setText(this.rs.getString("status_ob_a"));
                        this.TPregnanHPHT.setText(this.rs.getString("kehamilan_hpht"));
                        this.TPregnanHPL.setText(this.rs.getString("kehamilan_hpl"));
                        this.TPostPartum.setText(this.rs.getString("post_partum"));
                        this.TLochea.setText(this.rs.getString("lochea"));
                        this.TLocheaJumlah.setText(this.rs.getString("lochea_jumlah"));
                        this.TPayudara.setText(this.rs.getString("payudara"));
                        this.TPengeluaranASI.setText(this.rs.getString("pengeluaran_asi"));
                        this.TKontraksi.setText(this.rs.getString("kontraksi"));
                        this.TPapsmearTgl.setText(this.rs.getString("papsmear_tgl"));
                        this.TMammoTgl.setText(this.rs.getString("mammo_tgl"));
                        this.TSkriningTgl.setText(this.rs.getString("skrining_tgl"));
                        this.TMasalahRepLain.setText(this.rs.getString("masalah_rep_lain"));
                        this.TLukaDalam.setText(this.rs.getString("luka_dalam"));
                        this.TLokasiLain.setText(this.rs.getString("lokasi_lain"));
                        this.TMasalahIntegJelas1.setText(this.rs.getString("ket_masalah_integ_1"));
                        this.TMasalahIntegJelas2.setText(this.rs.getString("ket_masalah_integ_2"));
                        this.TMasalahIntegJelas3.setText(this.rs.getString("ket_masalah_integ_3"));
                        this.TMasalahIntegJelas4.setText(this.rs.getString("ket_masalah_integ_4"));
                        this.TTelingaLainnya.setText(this.rs.getString("telinga_lainnya"));
                        this.TMataLainnya.setText(this.rs.getString("mata_lainnya"));
                        this.TJenisDiit.setText(this.rs.getString("jenis_diit"));
                        this.TFeedingTube.setText(this.rs.getString("feeding_tube"));
                        this.TBunyiUsusFreq.setText(this.rs.getString("bunyi_usus_freq"));
                        this.TBABDiareSejak.setText(this.rs.getString("bab_sejak"));
                        this.TBABFreq.setText(this.rs.getString("bab_freq"));
                        this.TWarnaCerna.setText(this.rs.getString("warna_cerna"));
                    }
                    catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                    finally {
                        if (this.rs != null) {
                            this.rs.close();
                        }
                        if (this.ps != null) {
                            this.ps.close();
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
            }
            this.BtnSimpan.setEnabled(true);
            this.BtnHapus.setEnabled(true);
            this.BtnEdit.setEnabled(true);
            this.BtnPrint.setEnabled(true);
        }
    }

    


    public void setTTVFromPonek() {
        java.sql.PreparedStatement psTTV = null;
        java.sql.ResultSet rsTTV = null;
        try {
            psTTV = koneksi.prepareStatement(
                "select obj_td_sistol, obj_td_diastol, obj_hr, obj_rr, obj_suhu " +
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
                    if (!td.isEmpty()) TTD.setText(td);

                    String nadi = rsTTV.getString("obj_hr");
                    if (nadi != null && !nadi.isEmpty() && !nadi.equals("-")) TNadi.setText(nadi);

                    String rr = rsTTV.getString("obj_rr");
                    if (rr != null && !rr.isEmpty() && !rr.equals("-")) TRR.setText(rr);

                    String suhu = rsTTV.getString("obj_suhu");
                    if (suhu != null && !suhu.isEmpty() && !suhu.equals("-")) TSuhu.setText(suhu);
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
