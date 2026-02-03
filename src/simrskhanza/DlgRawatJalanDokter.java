package simrskhanza;

import bridging.ICareRiwayatPerawatan;
import bridging.OrthancViewerHybridSplitRad;
import bridging.OrthancViewerHybridSplitRadPetugas;
import bridging.OrthancViewerHybridSplitSOAPIE;
import bridging.SatuSehatCariAllergy;
import freehand.DlgMarkingImageSoapRalan;
import surat.SuratKontrol;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import inventory.DlgPemberianObat;
import fungsi.WarnaTable;
import fungsi.WarnaTableSoap;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import inventory.DlgCopyResep;
import inventory.DlgPeresepanDokter;
import inventory.DlgPeresepanDokter2;
import inventory.DlgPeresepanDokterRalankeRanap;
import inventory.DlgPeresepanDokterTemplate;
import inventory.DlgTemplatePemberianObat;
import inventory.DlgTemplateResep;
import inventory.InventoryResepLuar;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPegawai2;
import keuangan.DlgJnsPerawatanRalan;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import permintaan.DlgBookingOperasi;
import rekammedis.RMDataResumePasien;
import rekammedis.CopyDiagnosa;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanPelayananInformasiObat;
import permintaan.DlgPermintaanRadiologi;
import rekammedis.DlgOdontogram;
import rekammedis.DlgSBARRalan;
import rekammedis.DlgTBAKRalan;
import rekammedis.MasterCariTemplatePemeriksaan;
import rekammedis.MasterCariTemplateSOAPIE;
import rekammedis.MasterCariTemplateSOAPIEPerawat;
import rekammedis.RMCari5SOAPTerakhir;
import rekammedis.RMCatatanADIMEGizi;
import rekammedis.RMCatatanPersalinan;
import rekammedis.RMChecklistKriteriaMasukHCU;
import rekammedis.RMChecklistKriteriaMasukICU;
import rekammedis.RMChecklistPostOperasi;
import rekammedis.RMChecklistPreOperasi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataCatatanCekGDS;
import rekammedis.RMDataCatatanKeperawatanRalan;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataMonitoringReaksiTranfusi;
import rekammedis.RMDataSkriningGiziLanjut;
import rekammedis.RMEdukasiPasienKeluargaRawatJalan;
import rekammedis.RMHasilPemeriksaanUSG;
import rekammedis.RMHasilPemeriksaanUSGGynecologi;
import rekammedis.RMHasilPemeriksaanUSGUrologi;
import rekammedis.RMHasilTindakanESWL;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMLayananKedokteranFisikRehabilitasi;
import rekammedis.RMMCU;
import rekammedis.RMMonitoringAldrettePascaAnestesi;
import rekammedis.RMMonitoringBromagePascaAnestesi;
import rekammedis.RMMonitoringStewardPascaAnestesi;
import rekammedis.RMPemantauanMEOWS;
import rekammedis.RMPemantauanPEWS;
import rekammedis.RMPemantauanEWSD;
import rekammedis.RMPemantauanEWSNeonatus;
import rekammedis.RMPengkajianRestrain;
import rekammedis.RMPenilaianAwalKeperawatanBayiAnak;
import rekammedis.RMPenilaianAwalKeperawatanGigi;
import rekammedis.RMPenilaianAwalKeperawatanIGD;
import rekammedis.RMPenilaianAwalKeperawatanKebidanan;
import rekammedis.RMPenilaianAwalKeperawatanRalan;
import rekammedis.RMPenilaianAwalKeperawatanRalanGeriatri;
import rekammedis.RMPenilaianAwalKeperawatanRalanPsikiatri;
import rekammedis.RMPenilaianAwalMedisHemodialisa;
import rekammedis.RMPenilaianAwalMedisIGD;
import rekammedis.RMPenilaianAwalMedisIGDPsikiatri;
import rekammedis.RMPenilaianAwalMedisRalanAnak;
import rekammedis.RMPenilaianAwalMedisRalanBedah;
import rekammedis.RMPenilaianAwalMedisRalanBedahMulut;
import rekammedis.RMPenilaianAwalMedisRalanDewasa;
import rekammedis.RMPenilaianAwalMedisRalanGeriatri;
import rekammedis.RMPenilaianAwalMedisRalanKandungan;
import rekammedis.RMPenilaianAwalMedisRalanKulitDanKelamin;
import rekammedis.RMPenilaianAwalMedisRalanMata;
import rekammedis.RMPenilaianAwalMedisRalanNeurologi;
import rekammedis.RMPenilaianAwalMedisRalanOrthopedi;
import rekammedis.RMPenilaianAwalMedisRalanParu;
import rekammedis.RMPenilaianAwalMedisRalanPenyakitDalam;
import rekammedis.RMPenilaianAwalMedisRalanPsikiatrik;
import rekammedis.RMPenilaianAwalMedisRalanRehabMedik;
import rekammedis.RMPenilaianAwalMedisRalanTHT;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMPenilaianKorbanKekerasan;
import rekammedis.RMPenilaianLanjutanRisikoJatuhAnak;
import rekammedis.RMPenilaianLanjutanRisikoJatuhDewasa;
import rekammedis.RMPenilaianLanjutanRisikoJatuhGeriatri;
import rekammedis.RMPenilaianLanjutanRisikoJatuhLansia;
import rekammedis.RMPenilaianLanjutanRisikoJatuhPsikiatri;
import rekammedis.RMPenilaianLanjutanSkriningFungsional;
import rekammedis.RMPenilaianPasienKeracunan;
import rekammedis.RMPenilaianPasienPenyakitMenular;
import rekammedis.RMPenilaianPasienTerminal;
import rekammedis.RMPenilaianPreAnastesi;
import rekammedis.RMPenilaianPreInduksi;
import rekammedis.RMPenilaianPreOperasi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMPenilaianRisikoJatuhNeonatus;
import rekammedis.RMPenilaianTambahanBunuhDiri;
import rekammedis.RMPenilaianTambahanGeriatri;
import rekammedis.RMPenilaianTambahanMelarikanDiri;
import rekammedis.RMPenilaianTambahanPerilakuKekerasan;
import rekammedis.RMPenilaianTerapiWicara;
import rekammedis.RMPenilaianUlangNyeri;
import rekammedis.RMRekonsiliasiObat;
import rekammedis.RMRiwayatOperasi;
import rekammedis.RMRiwayatPengobatan;
import rekammedis.RMRiwayatPenunjang;
import rekammedis.RMRiwayatPerawatan;
import rekammedis.RMSignInSebelumAnastesi;
import rekammedis.RMSignOutSebelumMenutupLuka;
import rekammedis.RMSkriningNutrisiAnak;
import rekammedis.RMSkriningNutrisiDewasa;
import rekammedis.RMSkriningNutrisiLansia;
import rekammedis.RMTimeOutSebelumInsisi;
import rekammedis.RMTransferPasienAntarRuang;
import rekammedis.RMTriaseIGD;
import rekammedis.RMUjiFungsiKFR;
import surat.SuratRujukanBalik;

/**
 *
 * @author dosen
 */
public final class DlgRawatJalanDokter extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeDr, tabModePr, tabModeDrPr,
            tabModePemeriksaan, tabModeObstetri, tabModeGinekologi,
            TabModeTindakan, TabModeTindakan2, TabModeTindakan3, TabModeCatatan, tabModePemeriksaanSbar,
            tabModePemeriksaanTbak, tabModeCatatanPerawatIGD;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private DlgCariPasien pasien = new DlgCariPasien(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    public DlgCariPegawai pegawai = new DlgCariPegawai(null, false);
    public DlgCariPegawai2 pegawai2 = new DlgCariPegawai2(null, false);
    private RMCari5SOAPTerakhir soapterakhir = new RMCari5SOAPTerakhir(null, false);
    private OrthancViewerHybridSplitSOAPIE dicomViewer = new OrthancViewerHybridSplitSOAPIE(null, false);
    private PreparedStatement ps, ps2, ps3, ps4, ps5, ps6, ps7, pstindakan, psset_tarif, psrekening;
    private MasterCariTemplateSOAPIE templatesoapie = new MasterCariTemplateSOAPIE(null, false);
    private MasterCariTemplateSOAPIEPerawat templatesoapieperawat = new MasterCariTemplateSOAPIEPerawat(null, false);
    private ResultSet rs, rstindakan, rsset_tarif, rsrekening;
    private int i = 0, jmlparsial = 0, jml = 0, index = 0, tinggi = 0;
    private String aktifkanparsial = "no", kode_poli = "", kd_pj = "", poli_ralan = "No", cara_bayar_ralan = "No",
            Suspen_Piutang_Tindakan_Ralan = "", Tindakan_Ralan = "", Beban_Jasa_Medik_Dokter_Tindakan_Ralan = "",
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan = "",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan = "", Utang_Jasa_Medik_Paramedis_Tindakan_Ralan = "",
            Beban_KSO_Tindakan_Ralan = "", Utang_KSO_Tindakan_Ralan = "",
            Beban_Jasa_Sarana_Tindakan_Ralan = "", Utang_Jasa_Sarana_Tindakan_Ralan = "", HPP_BHP_Tindakan_Ralan = "",
            Persediaan_BHP_Tindakan_Ralan = "",
            Beban_Jasa_Menejemen_Tindakan_Ralan = "", Utang_Jasa_Menejemen_Tindakan_Ralan = "", poli = "", mode = "",
            variabel = "";
    private final Properties prop = new Properties();
    private String tglPemeriksaan = "", jamPemeriksaan = "", nipPemeriksaan = "";
    private boolean[] pilih;
    private String[] kode, nama, kategori;
    private double[] totaltnd, bagianrs, bhp, jmdokter, jmperawat, kso, menejemen;
    private boolean sukses = false;
    private double ttljmdokter = 0, ttljmperawat = 0, ttlkso = 0, ttljasasarana = 0, ttlbhp = 0, ttlmenejemen = 0,
            ttlpendapatan = 0;
    private Jurnal jur = new Jurnal();
    private SatuSehatCariAllergy allergycode = new SatuSehatCariAllergy(null, false);
    private String fileTTD = "";
    private widget.Button BtnTTD;

    /**
     * Creates new form DlgPerawatan
     *
     * @param parent
     * @param modal
     */
    public DlgRawatJalanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initRawatJalan();

        // Note: aturUrutanTombolFormMenu() sekarang dipanggil di isCek() setelah user
        // login

        java.awt.Color darkTextColor = java.awt.Color.BLACK;

        int inputFieldFontSize = 12;

        java.awt.Font newFont = new java.awt.Font("Tahoma", java.awt.Font.PLAIN, inputFieldFontSize);

        TKeluhan.setForeground(darkTextColor);
        TKeluhan.setFont(newFont);

        TPemeriksaan.setForeground(darkTextColor);
        TPemeriksaan.setFont(newFont);

        TPenilaian.setForeground(darkTextColor);
        TPenilaian.setFont(newFont);

        TindakLanjut.setForeground(darkTextColor);
        TindakLanjut.setFont(newFont);

        TInstruksi.setForeground(darkTextColor);
        TInstruksi.setFont(newFont);

        TEvaluasi.setForeground(darkTextColor);
        TEvaluasi.setFont(newFont);

        TSuhu.setForeground(darkTextColor);
        TSuhu.setFont(newFont);

        TTensi.setForeground(darkTextColor);
        TTensi.setFont(newFont);

        TTinggi.setForeground(darkTextColor);
        TTinggi.setFont(newFont);

        TRespirasi.setForeground(darkTextColor);
        TRespirasi.setFont(newFont);

        TBerat.setForeground(darkTextColor);
        TBerat.setFont(newFont);

        TNadi.setForeground(darkTextColor);
        TNadi.setFont(newFont);

        TGCS.setForeground(darkTextColor);
        TGCS.setFont(newFont);

        TAlergi.setForeground(darkTextColor);
        TAlergi.setFont(newFont);

        KdPeg.setForeground(darkTextColor);
        KdPeg.setFont(newFont);

        TPegawai.setForeground(darkTextColor);
        TPegawai.setFont(newFont);

        Jabatan.setForeground(darkTextColor);
        Jabatan.setFont(newFont);

        SpO2.setForeground(darkTextColor);
        SpO2.setFont(newFont);

        LingkarPerut.setForeground(darkTextColor);
        LingkarPerut.setFont(newFont);

        TKeluhan.setOpaque(true);
        TKeluhan.setBackground(java.awt.Color.WHITE);
        scrollPane5.getVerticalScrollBar().setUnitIncrement(16);

        TPemeriksaan.setOpaque(true);
        TPemeriksaan.setBackground(java.awt.Color.WHITE);
        scrollPane6.getVerticalScrollBar().setUnitIncrement(16);

        TPenilaian.setOpaque(true);
        TPenilaian.setBackground(java.awt.Color.WHITE);
        scrollPane9.getVerticalScrollBar().setUnitIncrement(16);

        TindakLanjut.setOpaque(true);
        TindakLanjut.setBackground(java.awt.Color.WHITE);
        scrollPane10.getVerticalScrollBar().setUnitIncrement(16);

        TInstruksi.setOpaque(true);
        TInstruksi.setBackground(java.awt.Color.WHITE);
        scrollPane7.getVerticalScrollBar().setUnitIncrement(16);

        TEvaluasi.setOpaque(true);
        TEvaluasi.setBackground(java.awt.Color.WHITE);
        scrollPane8.getVerticalScrollBar().setUnitIncrement(16);
        javax.swing.border.Border lineBorder = javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK);
        javax.swing.border.Border emptyBorder = javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5);
        javax.swing.border.Border compoundBorder = javax.swing.BorderFactory.createCompoundBorder(lineBorder,
                emptyBorder);

        TKeluhan.setBorder(compoundBorder);
        TPemeriksaan.setBorder(compoundBorder);
        TPenilaian.setBorder(compoundBorder);
        TindakLanjut.setBorder(compoundBorder);
        TInstruksi.setBorder(compoundBorder);
        TEvaluasi.setBorder(compoundBorder);

        this.setLocation(8, 1);
        setSize(1350, 700); // Expanded width for side-by-side layout

        autoFillPetugas();

        tabModeDr = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Perawatan/Tindakan", "Kode Dokter", "Dokter Yg Menangani",
                "Tgl.Rawat", "Jam Rawat", "Biaya", "Kode", "Tarif Dokter", "KSO", "Jasa Sarana", "BHP", "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbRawatDr.setModel(tabModeDr);
        // tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePr = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Perawatan/Tindakan", "NIP", "Petugas Yg Menangani",
                "Tgl.Rawat", "Jam Rawat", "Biaya", "Kode", "Tarif Perawat", "KSO", "Jasa Sarana", "BHP",
                "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbRawatPr.setModel(tabModePr);
        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeDrPr = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Perawatan/Tindakan", "Kode Dokter", "Dokter Yg Menangani",
                "NIP", "Petugas Yg Menangani", "Tgl.Rawat", "Jam Rawat",
                "Biaya", "Kode", "Tarif Dokter", "Tarif Petugas", "KSO", "Jasa Sarana", "BHP", "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbRawatDrPr.setModel(tabModeDrPr);
        tbRawatDrPr.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRawatDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbRawatDrPr.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(180);
            } else if (i == 7) {
                column.setPreferredWidth(90);
            } else if (i == 8) {
                column.setPreferredWidth(180);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(75);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 16) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 17) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 18) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDrPr.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePemeriksaan = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam", "Suhu(C)", "Tensi", "Nadi(/menit)",
                "Respirasi(/menit)", "Tinggi(Cm)", "Berat(Kg)", "SpO2(%)", "GCS(E,V,M)", "Kesadaran", "Subjek", "Objek",
                "Alergi",
                "L.P.(Cm)", "Plan", "Asesmen", "Instruksi", "Evaluasi", "NIP", "Dokter/Paramedis",
                "Profesi/Jabatan" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(45);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(73);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(63);
            } else if (i == 11) {
                column.setPreferredWidth(57);
            } else if (i == 12) {
                column.setPreferredWidth(50);
            } else if (i == 13) {
                column.setPreferredWidth(64);
            } else if (i == 14) {
                column.setPreferredWidth(90);
            } else if (i == 15) {
                column.setPreferredWidth(180);
            } else if (i == 16) {
                column.setPreferredWidth(180);
            } else if (i == 17) {
                column.setPreferredWidth(130);
            } else if (i == 18) {
                column.setPreferredWidth(50);
            } else if (i == 19) {
                column.setPreferredWidth(180);
            } else if (i == 20) {
                column.setPreferredWidth(180);
            } else if (i == 21) {
                column.setPreferredWidth(180);
            } else if (i == 22) {
                column.setPreferredWidth(180);
            } else if (i == 23) {
                column.setPreferredWidth(80);
            } else if (i == 24) {
                column.setPreferredWidth(150);
            } else if (i == 25) {
                column.setPreferredWidth(100);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTableSoap());

        tabModeObstetri = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M", "Nama Pasien", "Tgl.Rawat", "Jam Rawat",
                "Tinggi Fundus", "Janin", "Letak", "Panggul", "Denyut", "Kontraksi",
                "Kualitas Mnt", "Kualitas Detik", "Fluksus", "Albus", "Vulva",
                "Portio", "Dalam", "Tebal", "Arah", "Pembukaan", "Penurunan",
                "Denominator", "Ketuban", "Feto" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPemeriksaanObstetri.setModel(tabModeObstetri);
        tbPemeriksaanObstetri.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanObstetri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
            TableColumn column = tbPemeriksaanObstetri.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(80);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(60);
            } else if (i == 9) {
                column.setPreferredWidth(60);
            } else if (i == 10) {
                column.setPreferredWidth(60);
            } else if (i == 11) {
                column.setPreferredWidth(60);
            } else if (i == 12) {
                column.setPreferredWidth(70);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(50);
            } else if (i == 15) {
                column.setPreferredWidth(40);
            } else if (i == 16) {
                column.setPreferredWidth(170);
            } else if (i == 17) {
                column.setPreferredWidth(170);
            } else if (i == 18) {
                column.setPreferredWidth(60);
            } else if (i == 19) {
                column.setPreferredWidth(50);
            } else if (i == 20) {
                column.setPreferredWidth(60);
            } else if (i == 21) {
                column.setPreferredWidth(170);
            } else if (i == 22) {
                column.setPreferredWidth(170);
            } else if (i == 23) {
                column.setPreferredWidth(170);
            } else if (i == 24) {
                column.setPreferredWidth(50);
            } else if (i == 25) {
                column.setPreferredWidth(70);
            }
        }
        tbPemeriksaanObstetri.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeGinekologi = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M", "Nama Pasien", "Tgl.Rawat", "Jam Rawat",
                "Inpeksi", "Inspeksi Vulva/Uretra/Vagina", "Inspekulo", "Fluxus",
                "Fluor Albus", "Inspekulo Vulva/Vagina", "Inspekulo Portio", "Inspekulo Sondage",
                "Pemeriksaan Dalam Portio", "Pemeriksaan Dalam Bentuk", "Pemeriksaan Dalam Cavum Uteri", "Mobilitas",
                "Ukuran Cavum Uteri", "Nyeri Tekan", "Pemeriksaan Dalam Adnexa Kanan", "Pemeriksaan Dalam Adnexa Kiri",
                "Pemeriksaan Dalam Cavum Douglas" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class

            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPemeriksaanGinekologi.setModel(tabModeGinekologi);
        tbPemeriksaanGinekologi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanGinekologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbPemeriksaanGinekologi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {
                column.setPreferredWidth(42);
            } else if (i == 10) {
                column.setPreferredWidth(62);
            } else if (i == 11) {
                column.setPreferredWidth(200);
            } else if (i == 12) {
                column.setPreferredWidth(200);
            } else if (i == 13) {
                column.setPreferredWidth(200);
            } else if (i == 14) {
                column.setPreferredWidth(200);
            } else if (i == 15) {
                column.setPreferredWidth(200);
            } else if (i == 16) {
                column.setPreferredWidth(200);
            } else if (i == 17) {
                column.setPreferredWidth(50);
            } else if (i == 18) {
                column.setPreferredWidth(200);
            } else if (i == 19) {
                column.setPreferredWidth(67);
            } else if (i == 20) {
                column.setPreferredWidth(200);
            } else if (i == 21) {
                column.setPreferredWidth(200);
            } else if (i == 22) {
                column.setPreferredWidth(200);
            }
        }
        tbPemeriksaanGinekologi.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakan = new DefaultTableModel(null, new Object[] {
                "P", "Kode", "Nama Perawatan", "Kategori Perawatan", "Tarif/Biaya", "Bagian RS", "BHP", "JM Dokter",
                "JM Perawat", "KSO", "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class
            };

            /*
             * Class[] types = new Class[] {
             * java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
             * java.lang.Object.class
             * };
             */
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(420);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else {
                column.setPreferredWidth(90);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakan2 = new DefaultTableModel(null,
                new Object[] { "P", "Kode", "Nama Perawatan", "Kategori Perawatan", "Tarif/Biaya", "Bagian RS", "BHP",
                        "JM Dokter", "JM Perawat", "KSO", "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class
            };

            /*
             * Class[] types = new Class[] {
             * java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
             * java.lang.Object.class
             * };
             */
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTindakan2.setModel(TabModeTindakan2);
        tbTindakan2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTindakan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(420);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else {
                column.setPreferredWidth(90);
            }
        }
        tbTindakan2.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakan3 = new DefaultTableModel(null,
                new Object[] { "P", "Kode", "Nama Perawatan", "Kategori Perawatan", "Tarif/Biaya", "Bagian RS", "BHP",
                        "JM Dokter", "JM Perawat", "KSO", "Menejemen" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                    java.lang.Double.class, java.lang.Double.class
            };

            /*
             * Class[] types = new Class[] {
             * java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
             * java.lang.Object.class
             * };
             */
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTindakan3.setModel(TabModeTindakan3);
        tbTindakan3.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTindakan3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan3.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(420);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else {
                column.setPreferredWidth(90);
            }
        }
        tbTindakan3.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeCatatan = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tanggal", "Jam", "Kode Dokter", "Nama Dokter",
                "Catatan" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbCatatan.setModel(TabModeCatatan);
        tbCatatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbCatatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbCatatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(75);
            } else if (i == 6) {
                column.setPreferredWidth(80);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setPreferredWidth(700);
            }
        }
        tbCatatan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeCatatanPerawatIGD = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam", "Uraian", "NIP",
                "Nama Petugas"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbCatatanPerawatIGD.setModel(tabModeCatatanPerawatIGD);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbCatatanPerawatIGD.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbCatatanPerawatIGD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbCatatanPerawatIGD.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);

            } else if (i == 1) {
                column.setPreferredWidth(65);
            } else if (i == 2) {
                column.setPreferredWidth(160);
            } else if (i == 3) {
                column.setPreferredWidth(35);
            } else if (i == 4) {
                column.setPreferredWidth(20);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(500);
            } else if (i == 9) {
                column.setPreferredWidth(120);
            } else if (i == 10) {
                column.setPreferredWidth(160);
            }
        }
        tbCatatanPerawatIGD.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePemeriksaanSbar = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam",
                "Situation", "Background", "Assesment", "Recommendation", "NIP", "Dokter/Paramedis",
                "Profesi/Jabatan" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class

            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPemeriksaanSbar.setModel(tabModePemeriksaanSbar);
        tbPemeriksaanSbar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanSbar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbPemeriksaanSbar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(190);
            } else if (i == 8) {
                column.setPreferredWidth(190);
            } else if (i == 9) {
                column.setPreferredWidth(180);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(120);
            }
        }
        tbPemeriksaanSbar.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePemeriksaanTbak = new DefaultTableModel(null, new Object[] {
                "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam",
                "Situation", "Background", "Assesment", "Recommendation", "NIP", "Dokter/Paramedis",
                "Profesi/Jabatan" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class

            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPemeriksaanTbak.setModel(tabModePemeriksaanTbak);
        tbPemeriksaanTbak.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanTbak.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbPemeriksaanTbak.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(120);
            }
        }
        tbPemeriksaanTbak.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Umur", "No.Telp", "Cara Bayar", "TANGGAL", "KODE",
                "ALERGI", "KATEGORI", "REAKSI KODE", "SEVERITY", "NOTE", "NIP", "Nama Petugas"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(120);
            } else if (i == 8) {
                column.setPreferredWidth(115);
            } else if (i == 9) {
                column.setPreferredWidth(100);
            } else if (i == 10) {
                column.setPreferredWidth(150);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(90);
            } else if (i == 13) {
                column.setPreferredWidth(100);
            } else if (i == 14) {
                column.setPreferredWidth(150);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        kdptg.setDocument(new batasInput((byte) 20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte) 20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte) 20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte) 20).getKata(KdDok2));
        KdPeg.setDocument(new batasInput((byte) 20).getKata(KdPeg));
        TKeluhan.setDocument(new batasInput((int) 2000).getKata(TKeluhan));
        TPemeriksaan.setDocument(new batasInput((int) 2000).getKata(TPemeriksaan));
        TPenilaian.setDocument(new batasInput((int) 2000).getKata(TPenilaian));
        TInstruksi.setDocument(new batasInput((int) 2000).getKata(TInstruksi));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        TindakLanjut.setDocument(new batasInput((int) 2000).getKata(TindakLanjut));
        TEvaluasi.setDocument(new batasInput((int) 2000).getKata(TEvaluasi));
        TTinggi_uteri.setDocument(new batasInput((byte) 5).getKata(TTinggi_uteri));
        TLetak.setDocument(new batasInput((byte) 50).getKata(TLetak));
        TDenyut.setDocument(new batasInput((byte) 5).getKata(TDenyut));
        TKualitas_dtk.setDocument(new batasInput((byte) 5).getKata(TKualitas_dtk));
        TKualitas_mnt.setDocument(new batasInput((byte) 5).getKata(TKualitas_mnt));
        TVulva.setDocument(new batasInput((byte) 50).getKata(TVulva));
        TPortio.setDocument(new batasInput((byte) 50).getKata(TPortio));
        TTebal.setDocument(new batasInput((byte) 5).getKata(TTebal));
        TPembukaan.setDocument(new batasInput((byte) 50).getKata(TPembukaan));
        TPenurunan.setDocument(new batasInput((byte) 50).getKata(TPenurunan));
        TDenominator.setDocument(new batasInput((byte) 50).getKata(TDenominator));
        TInspeksi.setDocument(new batasInput((byte) 50).getKata(TInspeksi));
        TInspeksiVulva.setDocument(new batasInput((byte) 50).getKata(TInspeksiVulva));
        TInspekuloGine.setDocument(new batasInput((byte) 50).getKata(TInspekuloGine));
        TVulvaInspekulo.setDocument(new batasInput((byte) 50).getKata(TVulvaInspekulo));
        TPortioInspekulo.setDocument(new batasInput((byte) 50).getKata(TPortioInspekulo));
        TSondage.setDocument(new batasInput((byte) 50).getKata(TSondage));
        TPortioDalam.setDocument(new batasInput((byte) 50).getKata(TPortioDalam));
        TBentuk.setDocument(new batasInput((byte) 50).getKata(TBentuk));
        TCavumUteri.setDocument(new batasInput((byte) 50).getKata(TCavumUteri));
        TUkuran.setDocument(new batasInput((byte) 50).getKata(TUkuran));
        TAdnexaKanan.setDocument(new batasInput((byte) 50).getKata(TAdnexaKanan));
        TAdnexaKiri.setDocument(new batasInput((byte) 50).getKata(TAdnexaKiri));
        TCavumDouglas.setDocument(new batasInput((byte) 50).getKata(TCavumDouglas));
        Catatan.setDocument(new batasInput((int) 700).getKata(Catatan));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        TampilkanData();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        TampilkanData();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        TampilkanData();
                    }
                }
            });
        }

        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokterDokter")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TCariPasien.setText(
                                pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 0).toString());
                    }
                    TCariPasien.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokterDokter")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pegawai2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokterDokter")) {
                    if (pegawai2.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 8) {
                            KdPeg2.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 0).toString());
                            TPegawai2.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 1).toString());
                            Jabatan1.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 3).toString());
                            KdPeg2.requestFocus();
                        }
                    }
                }

            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pegawai2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokterDokter")) {
                    if (pegawai2.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 8) {
                            KdPeg4.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 0).toString());
                            TPegawai4.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 1).toString());
                            Jabatan2.setText(
                                    pegawai2.getTable().getValueAt(pegawai2.getTable().getSelectedRow(), 3).toString());
                            KdPeg4.requestFocus();
                        }
                    }
                }

            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokterDokter")) {
                    if (dokter.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 0) {
                            KdDok.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            TDokter.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            KdDok.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            KdDok2.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            TDokter2.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            KdDok2.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 8) {
                            KdDok3.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            TDokter3.setText(
                                    dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            KdDok3.requestFocus();
                        }
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokter")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        if (TabRawat.getSelectedIndex() == 1) {
                            kdptg.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                            TPerawat.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                            kdptg.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 2) {
                            kdptg2.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                            TPerawat2.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                            kdptg2.requestFocus();
                        } else if (TabRawat.getSelectedIndex() == 10) {
                            kdptg3.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                            TPerawat3.setText(
                                    petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                            kdptg3.requestFocus();
                        }
                    }

                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatJalanDokter")) {
                    if (pegawai.getTable().getSelectedRow() != -1) {
                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 0).toString());
                        TPegawai.setText(
                                pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 1).toString());
                        Jabatan.setText(
                                pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 3).toString());
                        KdPeg1.setText(
                                pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 0).toString());
                        TPegawai1.setText(
                                pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 1).toString());
                        Jabatan4.setText(
                                pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 3).toString());
                        // KdPeg.requestFocus();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        soapterakhir.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (soapterakhir.getTable().getSelectedRow() != -1) {
                    TKeluhan.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 2).toString());
                    TPemeriksaan.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 3).toString());
                    TPenilaian.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 4).toString());
                    TindakLanjut.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 5).toString());
                    TInstruksi.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 6).toString());
                    TEvaluasi.setText(
                            soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(), 7).toString());
                    TEvaluasi.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        templatesoapie.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (templatesoapie.getTable().getSelectedRow() != -1) {
                    TKeluhan.setText(templatesoapie.getTable().getValueAt(templatesoapie.getTable().getSelectedRow(), 3)
                            .toString());
                    TPemeriksaan.setText(templatesoapie.getTable()
                            .getValueAt(templatesoapie.getTable().getSelectedRow(), 4).toString());
                    TPenilaian.setText(templatesoapie.getTable()
                            .getValueAt(templatesoapie.getTable().getSelectedRow(), 5).toString());
                    TindakLanjut.setText(templatesoapie.getTable()
                            .getValueAt(templatesoapie.getTable().getSelectedRow(), 6).toString());
                    TInstruksi.setText(templatesoapie.getTable()
                            .getValueAt(templatesoapie.getTable().getSelectedRow(), 7).toString());
                    TEvaluasi.setText(templatesoapie.getTable()
                            .getValueAt(templatesoapie.getTable().getSelectedRow(), 8).toString());

                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        templatesoapieperawat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (templatesoapieperawat.getTable().getSelectedRow() != -1) {
                    TKeluhan.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 1).toString());
                    TPemeriksaan.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 2).toString());
                    TPenilaian.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 3).toString());
                    TindakLanjut.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 4).toString());
                    TInstruksi.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 5).toString());
                    TEvaluasi.setText(templatesoapieperawat.getTable()
                            .getValueAt(templatesoapieperawat.getTable().getSelectedRow(), 6).toString());

                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        panelDiagnosa1.TabRawat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LCount.setText(panelDiagnosa1.getRecord() + "");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        panelDiagnosa1.tbDiagnosaPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (panelDiagnosa1.tbDiagnosaPasien.getSelectedRow() != -1) {
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 4).toString());
                }
            }
        });

        panelDiagnosa1.tbDiagnosaPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panelDiagnosa1.tbDiagnosaPasien.getSelectedRow() != -1) {
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien
                            .getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 4).toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        panelDiagnosa1.tbTindakanPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panelDiagnosa1.tbTindakanPasien.getSelectedRow() != -1) {
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 4).toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        panelDiagnosa1.tbTindakanPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (panelDiagnosa1.tbTindakanPasien.getSelectedRow() != -1) {
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien
                            .getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(), 4).toString());
                }
            }
        });

        allergycode.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (allergycode.getTable().getSelectedRow() != -1) {
                    if (mode.equals("alergi")) {
                        AlergiCode.setText(allergycode.getTable().getValueAt(allergycode.getTable().getSelectedRow(), 0)
                                .toString());
                        AlergySystem.setText(allergycode.getTable()
                                .getValueAt(allergycode.getTable().getSelectedRow(), 1).toString());
                        AlergyDisplay.setText(allergycode.getTable()
                                .getValueAt(allergycode.getTable().getSelectedRow(), 2).toString());
                    } else if (mode.equals("reaksialergi")) {
                        ReaksiCode.setText(allergycode.getTable().getValueAt(allergycode.getTable().getSelectedRow(), 0)
                                .toString());
                        ReaksiSystem.setText(allergycode.getTable()
                                .getValueAt(allergycode.getTable().getSelectedRow(), 1).toString());
                        ReaksiDisplay.setText(allergycode.getTable()
                                .getValueAt(allergycode.getTable().getSelectedRow(), 2).toString());
                    }

                }
                btnJenisAlergi.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        ChkInput.setSelected(false);
        isForm();
        ChkInput1.setSelected(false);
        isForm2();
        ChkInput2.setSelected(false);
        isForm3();
        ChkInput3.setSelected(false);
        isForm4();
        ChkAccor.setSelected(true);
        isMenu();
        jam();

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial = prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {
            aktifkanparsial = "no";
        }

        try {
            psrekening = koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"
                            + "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"
                            + "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,"
                            + "set_akun_ralan.Utang_KSO_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,"
                            + "set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"
                            + "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Tindakan_Ralan = rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan = rsrekening.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan = rsrekening
                            .getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan = rsrekening
                            .getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan = rsrekening
                            .getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan = rsrekening
                            .getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan = rsrekening.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan = rsrekening.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan = rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan = rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan = rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan = rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan = rsrekening.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan = rsrekening.getString("Persediaan_BHP_Tindakan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            psset_tarif = koneksi
                    .prepareStatement("select set_tarif.poli_ralan,set_tarif.cara_bayar_ralan from set_tarif");
            try {
                rsset_tarif = psset_tarif.executeQuery();
                if (rsset_tarif.next()) {
                    poli_ralan = rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan = rsset_tarif.getString("cara_bayar_ralan");
                } else {
                    poli_ralan = "Yes";
                    cara_bayar_ralan = "Yes";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rsset_tarif != null) {
                    rsset_tarif.close();
                }
                if (psset_tarif != null) {
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        KdNoRawat = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        TCariPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnTambahTindakan = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        TabRawatTindakanDokter = new javax.swing.JTabbedPane();
        Scroll6 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        TabRawatTindakanPetugas = new javax.swing.JTabbedPane();
        Scroll7 = new widget.ScrollPane();
        tbTindakan2 = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        TabRawatTindakanDokterPetugas = new javax.swing.JTabbedPane();
        Scroll9 = new widget.ScrollPane();
        tbTindakan3 = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbRawatDrPr = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        internalFrame5.setLayout(new java.awt.BorderLayout());
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        PanelInput.setPreferredSize(new java.awt.Dimension(800, 750));
        panelGlass12 = new widget.panelisi();
        jLabel8 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        jLabel28 = new widget.Label();
        jLabel26 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        TSuhu = new widget.TextBox();
        TTensi = new widget.TextBox();
        TTinggi = new widget.TextBox();
        TRespirasi = new widget.TextBox();
        TBerat = new widget.TextBox();
        TNadi = new widget.TextBox();
        BtnRiwayatFKTP = new widget.Button();
        TGCS = new widget.TextBox();
        TAlergi = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        jLabel29 = new widget.Label();
        cmbKesadaran = new widget.ComboBox();
        jLabel37 = new widget.Label();
        KdPeg = new widget.TextBox();
        TPegawai = new widget.TextBox();
        BtnSeekPegawai = new widget.Button();
        Jabatan = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel53 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        TInstruksi = new widget.TextArea();
        jLabel54 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel56 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        TEvaluasi = new widget.TextArea();
        LingkarPerut = new widget.TextBox();
        Btn5Soap = new widget.Button();
        BtnRiwayatPenunjang = new widget.Button();
        BtnTemplatePemeriksaan = new widget.Button();
        BtnOdontogram = new widget.Button();
        BtnTemplateResep = new widget.Button();
        BtnTemplatePemberianObat1 = new widget.Button();
        Btn5Soap1 = new widget.Button();
        BtnHasilRadiologi = new widget.Button();
        BtnHasilPengobatan = new widget.Button();
        BtnHasilPengobatan1 = new widget.Button();
        BtnInputLAB = new widget.Button();
        BtnInputRAD = new widget.Button();
        BtnInputTerimaPasienAntarRuang = new widget.Button();
        BtnInputKonsul1 = new widget.Button();
        BtnTemplatePemberianObat2 = new widget.Button();
        BtnDicom = new widget.Button();
        BtnRiwayatFKTP = new widget.Button();
        lblTemplate = new widget.Label();
        ChkTemplate = new widget.CekBox();
        BtnSoapDokter = new widget.Button();
        BtnSoapDokter1 = new widget.Button();
        lblTemplate1 = new widget.Label();
        ChkTemplatePerawat = new widget.CekBox();
        BtnSuratRujukanBalik = new widget.Button();
        BtnCopyDiagnosa = new widget.Button();
        BtnPeriksa = new widget.Button();
        BtnPeriksaRad = new widget.Button();
        ChkInput = new widget.CekBox();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbPemeriksaanObstetri = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput1 = new widget.CekBox();
        panelGlass13 = new widget.panelisi();
        jLabel27 = new widget.Label();
        TTinggi_uteri = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        TLetak = new widget.TextBox();
        jLabel32 = new widget.Label();
        TKualitas_dtk = new widget.TextBox();
        jLabel33 = new widget.Label();
        cmbPanggul = new widget.ComboBox();
        jLabel34 = new widget.Label();
        TTebal = new widget.TextBox();
        TDenyut = new widget.TextBox();
        jLabel36 = new widget.Label();
        TDenominator = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        TKualitas_mnt = new widget.TextBox();
        jLabel40 = new widget.Label();
        cmbFeto = new widget.ComboBox();
        jLabel42 = new widget.Label();
        cmbJanin = new widget.ComboBox();
        cmbKetuban = new widget.ComboBox();
        TPortio = new widget.TextBox();
        jLabel43 = new widget.Label();
        TVulva = new widget.TextBox();
        cmbKontraksi = new widget.ComboBox();
        cmbAlbus = new widget.ComboBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel44 = new widget.Label();
        cmbFluksus = new widget.ComboBox();
        jLabel48 = new widget.Label();
        cmbDalam = new widget.ComboBox();
        jLabel49 = new widget.Label();
        TPembukaan = new widget.TextBox();
        TPenurunan = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        cmbArah = new widget.ComboBox();
        jLabel52 = new widget.Label();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbPemeriksaanGinekologi = new widget.Table();
        PanelInput2 = new javax.swing.JPanel();
        ChkInput2 = new widget.CekBox();
        panelGlass14 = new widget.panelisi();
        jLabel35 = new widget.Label();
        TInspeksiVulva = new widget.TextBox();
        TAdnexaKanan = new widget.TextBox();
        jLabel57 = new widget.Label();
        cmbMobilitas = new widget.ComboBox();
        jLabel60 = new widget.Label();
        TInspekuloGine = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel67 = new widget.Label();
        TPortioInspekulo = new widget.TextBox();
        TCavumUteri = new widget.TextBox();
        cmbFluorGine = new widget.ComboBox();
        TInspeksi = new widget.TextBox();
        cmbFluxusGine = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        TVulvaInspekulo = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        TPortioDalam = new widget.TextBox();
        TBentuk = new widget.TextBox();
        jLabel78 = new widget.Label();
        cmbNyeriTekan = new widget.ComboBox();
        TSondage = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        TAdnexaKiri = new widget.TextBox();
        jLabel81 = new widget.Label();
        TCavumDouglas = new widget.TextBox();
        TUkuran = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        panelDiagnosa1 = new laporan.PanelDiagnosa();
        internalFrame8 = new widget.InternalFrame();
        PanelInput3 = new javax.swing.JPanel();
        ChkInput3 = new widget.CekBox();
        panelGlass15 = new widget.panelisi();
        jLabel55 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Catatan = new widget.TextArea();
        jLabel11 = new widget.Label();
        KdDok3 = new widget.TextBox();
        TDokter3 = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbCatatan = new widget.Table();
        internalFrame11 = new widget.InternalFrame();
        Scroll14 = new widget.ScrollPane();
        tbPemeriksaanSbar = new widget.Table();
        PanelInput4 = new javax.swing.JPanel();
        ChkInput4 = new widget.CekBox();
        panelGlass17 = new widget.panelisi();
        scrollPane5 = new widget.ScrollPane();
        TSituation = new widget.TextArea();
        scrollPane9 = new widget.ScrollPane();
        TBackground = new widget.TextArea();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        TAssesment = new widget.TextArea();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        TRecommendation = new widget.TextArea();
        jLabel92 = new widget.Label();
        KdPeg2 = new widget.TextBox();
        TPegawai2 = new widget.TextBox();
        BtnSeekPegawai1 = new widget.Button();
        Jabatan1 = new widget.TextBox();
        jLabel93 = new widget.Label();
        TPegawai3 = new widget.TextBox();
        KdPeg3 = new widget.TextBox();
        jLabel70 = new widget.Label();
        BtnVerifSbar = new widget.Button();
        jLabel84 = new widget.Label();
        internalFrame12 = new widget.InternalFrame();
        Scroll15 = new widget.ScrollPane();
        tbPemeriksaanTbak = new widget.Table();
        PanelInput5 = new javax.swing.JPanel();
        ChkInput5 = new widget.CekBox();
        panelGlass18 = new widget.panelisi();
        scrollPane12 = new widget.ScrollPane();
        TSituation1 = new widget.TextArea();
        scrollPane13 = new widget.ScrollPane();
        TBackground1 = new widget.TextArea();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        TAssesment1 = new widget.TextArea();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        TRecommendation1 = new widget.TextArea();
        jLabel98 = new widget.Label();
        KdPeg4 = new widget.TextBox();
        TPegawai4 = new widget.TextBox();
        BtnSeekPegawai2 = new widget.Button();
        Jabatan2 = new widget.TextBox();
        jLabel99 = new widget.Label();
        TPegawai5 = new widget.TextBox();
        KdPeg5 = new widget.TextBox();
        jLabel85 = new widget.Label();
        BtnVerifSbar1 = new widget.Button();
        jLabel86 = new widget.Label();
        internalFrame14 = new widget.InternalFrame();
        PanelInput7 = new javax.swing.JPanel();
        ChkInput7 = new widget.CekBox();
        panelGlass20 = new widget.panelisi();
        jLabel66 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Catatan1 = new widget.TextArea();
        jLabel68 = new widget.Label();
        kdptg3 = new widget.TextBox();
        TPerawat3 = new widget.TextBox();
        BtnSeekPetugas3 = new widget.Button();
        Scroll17 = new widget.ScrollPane();
        tbCatatanPerawatIGD = new widget.Table();
        internalFrame15 = new widget.InternalFrame();
        PanelInput6 = new javax.swing.JPanel();
        FormInput1 = new widget.PanelBiasa();
        btnJenisAlergi = new widget.Button();
        jLabel59 = new widget.Label();
        AlergyDisplay = new widget.TextBox();
        AlergySystem = new widget.TextBox();
        jLabel61 = new widget.Label();
        AlergiCode = new widget.TextBox();
        jLabel69 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        TKeterangan = new widget.TextArea();
        jLabel87 = new widget.Label();
        cmbKategory = new widget.ComboBox();
        jLabel100 = new widget.Label();
        btnReaksiAlergi = new widget.Button();
        ReaksiDisplay = new widget.TextBox();
        ReaksiSystem = new widget.TextBox();
        ReaksiCode = new widget.TextBox();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        cmbSeverity = new widget.ComboBox();
        jLabel58 = new widget.Label();
        KdPeg1 = new widget.TextBox();
        TPegawai1 = new widget.TextBox();
        BtnSeekPegawai3 = new widget.Button();
        Jabatan4 = new widget.TextBox();
        jLabel103 = new widget.Label();
        Scroll1 = new widget.ScrollPane();
        tbObat = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel23 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        TglLahir = new widget.TextBox();
        jLabel63 = new widget.Label();
        Umur = new widget.TextBox();
        jLabel65 = new widget.Label();
        BtnPanggilPasien = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayat = new widget.Button();
        BtnResepObat = new widget.Button();
        BtnTemplate = new widget.Button();
        BtnCopyResep = new widget.Button();
        BtnResepLuar = new widget.Button();
        BtnInputObat = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnKamar = new widget.Button();
        BtnTriaseIGD = new widget.Button();
        BtnRujukInternal = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalKeperawatanIGD = new widget.Button();
        BtnAwalKeperawatan = new widget.Button();
        BtnAwalKeperawatanGigi = new widget.Button();
        BtnAwalKeperawatanKandungan = new widget.Button();
        BtnAwalKeperawatanAnak = new widget.Button();
        BtnAwalKeperawatanPsikiatri = new widget.Button();
        BtnAwalKeperawatanGeriatri = new widget.Button();
        BtnAwalFisioterapi = new widget.Button();
        BtnAwalTerapiWicara = new widget.Button();
        BtnAwalMedisIGD = new widget.Button();
        BtnAwalMedisIGDPsikiatri = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnAwalMedisAnak = new widget.Button();
        BtnAwalMedisTHT = new widget.Button();
        BtnAwalMedisPsikiatri = new widget.Button();
        BtnAwalMedisPenyakitDalam = new widget.Button();
        BtnAwalMedisMata = new widget.Button();
        BtnAwalMedisNeurologi = new widget.Button();
        BtnAwalMedisOrthopedi = new widget.Button();
        BtnAwalMedisBedah = new widget.Button();
        BtnAwalMedisBedahMulut = new widget.Button();
        BtnAwalMedisGeriatri = new widget.Button();
        BtnAwalMedisKulitKelamin = new widget.Button();
        BtnAwalMedisParu = new widget.Button();
        BtnAwalMedisRehabMedik = new widget.Button();
        BtnLayananKedokteranFisik = new widget.Button();
        BtnAwalMedisHemodialisa = new widget.Button();
        BtnRujukKeluar = new widget.Button();
        BtnCatatan = new widget.Button();
        BtnCatatanObservasiIGD = new widget.Button();
        BtnCatatanCekGDS = new widget.Button();
        BtnCatatanKeperawatan = new widget.Button();
        BtnPenilaianUlangNyeri = new widget.Button();
        BtnPemantauanPEWSAnak = new widget.Button();
        BtnPemantauanPEWSDewasa = new widget.Button();
        BtnPemantauanMEOWS = new widget.Button();
        BtnPemantauanEWSNeonatus = new widget.Button();
        BtnMonitoringReaksiTranfusi = new widget.Button();
        BtnUjiFungsiKFR = new widget.Button();
        BtnChecklistKriteriaMasukHCU = new widget.Button();
        BtnChecklistKriteriaMasukICU = new widget.Button();
        BtnChecklistPreOperasi = new widget.Button();
        BtnSignInSebelumAnestesi = new widget.Button();
        BtnTimeOutSebelumInsisi = new widget.Button();
        BtnSignOutSebelumMenutupLuka = new widget.Button();
        BtnChecklistPostOperasi = new widget.Button();
        BtnPenilaianPreOperasi = new widget.Button();
        BtnPenilaianPreAnestesi = new widget.Button();
        BtnSkorAldrettePascaAnestesi = new widget.Button();
        BtnSkorStewardPascaAnestesi = new widget.Button();
        BtnMedicalCheckUp = new widget.Button();
        BtnPenilaianPsikolog = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhDewasa = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhAnak = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhLansia = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhNeonatus = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhGeriatri = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhPsikiatri = new widget.Button();
        BtnPenilaianLanjutanSkriningFungsional = new widget.Button();
        BtnHasilPemeriksaanUSG = new widget.Button();
        BtnDokumentasiESWL = new widget.Button();
        BtnCatatanPersalinanan = new widget.Button();
        BtnSkriningNutrisiDewasa = new widget.Button();
        BtnSkriningNutrisiLansia = new widget.Button();
        BtnSkriningNutrisiAnak = new widget.Button();
        BtnSkriningGiziLanjut = new widget.Button();
        BtnAsuhanGizi = new widget.Button();
        BtnMonitoringAsuhanGizi = new widget.Button();
        BtnCatatanADIMEGizi = new widget.Button();
        BtnKonselingFarmasi = new widget.Button();
        BtnInformasiObat = new widget.Button();
        BtnRekonsiliasiObat = new widget.Button();
        BtnTransferAntarRuang = new widget.Button();
        BtnEdukasiPasienKeluarga = new widget.Button();
        BtnPengkajianRestrain = new widget.Button();
        BtnPenilaianPasienTerminal = new widget.Button();
        BtnPenilaianKorbanKekerasan = new widget.Button();
        BtnPenilaianPasienPenyakitMenular = new widget.Button();
        BtnPenilaianPasienKeracunan = new widget.Button();
        BtnPenilaianTambahanGeriatri = new widget.Button();
        BtnPenilaianTambahanBunuhDiri = new widget.Button();
        BtnPenilaianTambahanPerilakuKekerasan = new widget.Button();
        BtnPenilaianTambahanMelarikanDiri = new widget.Button();

        KdNoRawat.setHighlighter(null);
        KdNoRawat.setName("KdNoRawat"); // NOI18N
        KdNoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdNoRawatKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Perawatan/Tindakan Rawat Jalan Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
        panelGlass8.add(LCount);

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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel24.setText("No.RM :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel24);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass9.add(TCariPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass9.add(btnPasien);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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

        BtnTambahTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahTindakan.setMnemonic('3');
        BtnTambahTindakan.setToolTipText("Alt+3");
        BtnTambahTindakan.setName("BtnTambahTindakan"); // NOI18N
        BtnTambahTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnTambahTindakan);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setForeground(new java.awt.Color(50, 50, 50));
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 55, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(58, 10, 146, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(749, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 540, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokter.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokter.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatTindakanDokter.setName("TabRawatTindakanDokter"); // NOI18N
        TabRawatTindakanDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterMouseClicked(evt);
            }
        });

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbTindakan.setToolTipText("");
        tbTindakan.setName("tbTindakan"); // NOI18N
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbTindakan);

        TabRawatTindakanDokter.addTab("Daftar Tindakan/Tagihan", Scroll6);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        TabRawatTindakanDokter.addTab("Tindakan Dilakukan", Scroll);

        internalFrame2.add(TabRawatTindakanDokter, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 63, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(66, 10, 146, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(749, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(214, 10, 532, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatTindakanPetugas.setName("TabRawatTindakanPetugas"); // NOI18N
        TabRawatTindakanPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanPetugasMouseClicked(evt);
            }
        });

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbTindakan2.setToolTipText("");
        tbTindakan2.setName("tbTindakan2"); // NOI18N
        tbTindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan2KeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbTindakan2);

        TabRawatTindakanPetugas.addTab("Daftar Tindakan/Tagihan", Scroll7);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyReleased(evt);
            }
        });
        Scroll8.setViewportView(tbRawatPr);

        TabRawatTindakanPetugas.addTab("Tindakan Dilakukan", Scroll8);

        internalFrame3.add(TabRawatTindakanPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Petugas", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(null);
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 65, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); // NOI18N
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(68, 40, 130, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(749, 40, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); // NOI18N
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(200, 40, 546, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 65, 23);

        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); // NOI18N
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(68, 10, 130, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        TDokter2.setName("TDokter2"); // NOI18N
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(200, 10, 546, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.setName("BtnSeekDokter2"); // NOI18N
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(749, 10, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokterPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokterPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokterPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatTindakanDokterPetugas.setName("TabRawatTindakanDokterPetugas"); // NOI18N
        TabRawatTindakanDokterPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterPetugasMouseClicked(evt);
            }
        });

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbTindakan3.setToolTipText("");
        tbTindakan3.setName("tbTindakan3"); // NOI18N
        tbTindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan3KeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbTindakan3);

        TabRawatTindakanDokterPetugas.addTab("Daftar Tindakan/Tagihan", Scroll9);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbRawatDrPr.setName("tbRawatDrPr"); // NOI18N
        tbRawatDrPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrMouseClicked(evt);
            }
        });
        tbRawatDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrPrKeyReleased(evt);
            }
        });
        Scroll10.setViewportView(tbRawatDrPr);

        TabRawatTindakanDokterPetugas.addTab("Tindakan Dilakukan", Scroll10);

        internalFrame4.add(TabRawatTindakanDokterPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter & Petugas", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbPemeriksaan);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(800, 800));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(800, 770));
        panelGlass12.setLayout(null);

        // === BLOK 1: TANDA-TANDA VITAL (KIRI ATAS) ===
        int ttvX = 5;
        int ttvY = 10;
        int labelWidth = 70;
        int fieldWidth = 80;
        int gap = 25; // Jarak vertikal antar TTV

        // Suhu
        jLabel7.setText("Suhu :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass12.add(jLabel7);
        jLabel7.setBounds(ttvX, ttvY, labelWidth, 23);
        TSuhu.setName("TSuhu"); // NOI18N

        TSuhu.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TSuhu);
        TSuhu.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap; // Pindah ke baris berikutnya

        // Tensi
        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass12.add(jLabel4);
        jLabel4.setBounds(ttvX, ttvY, labelWidth, 23);
        TTensi.setName("TTensi"); // NOI18N

        TTensi.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TTensi);
        TTensi.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // Nadi
        jLabel18.setText("Nadi :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelGlass12.add(jLabel18);
        jLabel18.setBounds(ttvX, ttvY, labelWidth, 23);
        TNadi.setName("TNadi"); // NOI18N

        TNadi.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TNadi);
        TNadi.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // Respirasi
        jLabel20.setText("Respirasi :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelGlass12.add(jLabel20);
        jLabel20.setBounds(ttvX, ttvY, labelWidth, 23);
        TRespirasi.setName("TRespirasi"); // NOI18N

        TRespirasi.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TRespirasi);
        TRespirasi.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // Tinggi
        jLabel17.setText("Tinggi :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelGlass12.add(jLabel17);
        jLabel17.setBounds(ttvX, ttvY, labelWidth, 23);
        TTinggi.setName("TTinggi"); // NOI18N

        TTinggi.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TTinggi);
        TTinggi.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // Berat
        jLabel16.setText("Berat :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(ttvX, ttvY, labelWidth, 23);
        TBerat.setName("TBerat"); // NOI18N

        TBerat.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TBerat);
        TBerat.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // SpO2
        jLabel54.setText("SpO2 :");
        jLabel54.setName("jLabel54"); // NOI18N
        panelGlass12.add(jLabel54);
        jLabel54.setBounds(ttvX, ttvY, labelWidth, 23);
        SpO2.setName("SpO2"); // NOI18N

        SpO2.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(SpO2);
        SpO2.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // GCS
        jLabel22.setText("GCS :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(ttvX, ttvY, labelWidth, 23);
        TGCS.setName("TGCS"); // NOI18N

        TGCS.setBackground(new java.awt.Color(245, 245, 245));
        panelGlass12.add(TGCS);
        TGCS.setBounds(ttvX + labelWidth + 5, ttvY, fieldWidth, 23);
        ttvY += gap;

        // Btn5Soap (Riwayat SOAP)
        Btn5Soap = new widget.Button();
        Btn5Soap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Btn5Soap.setMnemonic('4');
        Btn5Soap.setText("Riwayat SOAP");
        Btn5Soap.setToolTipText("Alt+4");
        Btn5Soap.setName("Btn5Soap"); // NOI18N
        Btn5Soap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn5SoapActionPerformed(evt);
            }
        });
        panelGlass12.add(Btn5Soap);
        Btn5Soap.setBounds(ttvX + 5, ttvY, 120, 23);
        ttvY += gap; // Move to next line

        // BtnRiwayatPenunjang (Riwayat Penunjang Medis)
        BtnRiwayatPenunjang = new widget.Button();
        BtnRiwayatPenunjang.setForeground(new java.awt.Color(0, 0, 0));
        BtnRiwayatPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/TestTubes.png"))); // NOI18N
        BtnRiwayatPenunjang.setMnemonic('5');
        BtnRiwayatPenunjang.setText("Riwayat Penunjang");
        BtnRiwayatPenunjang.setToolTipText("Alt+5");
        BtnRiwayatPenunjang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatPenunjang.setGlassColor(new java.awt.Color(255, 153, 153));
        BtnRiwayatPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatPenunjang.setName("BtnRiwayatPenunjang"); // NOI18N
        BtnRiwayatPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatPenunjangActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnRiwayatPenunjang);
        BtnRiwayatPenunjang.setBounds(ttvX + 5, ttvY, 150, 23);
        ttvY += gap; // Move to next line

        BtnRiwayatFKTP.setForeground(new java.awt.Color(0, 0, 200));
        BtnRiwayatFKTP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Hospital.png"))); // NOI18N
        BtnRiwayatFKTP.setMnemonic('6');
        BtnRiwayatFKTP.setText("Jejak Medis Luar");
        BtnRiwayatFKTP.setToolTipText("Alt+6");
        BtnRiwayatFKTP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatFKTP.setGlassColor(new java.awt.Color(255, 153, 153));
        BtnRiwayatFKTP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatFKTP.setName("BtnRiwayatFKTP"); // NOI18N
        BtnRiwayatFKTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatFKTPActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnRiwayatFKTP);
        BtnRiwayatFKTP.setBounds(ttvX + 5, ttvY, 150, 23);

        // === BLOK 2: S/O (KANAN ATAS) ===
        int soapX = 180;
        int soapWidth = 580;
        int fieldWidthKanan = 150;
        int labelWidthKanan = 80;

        // Kesadaran
        jLabel29.setText("Kesadaran :");
        jLabel29.setName("jLabel29"); // NOI18N
        panelGlass12.add(jLabel29);
        jLabel29.setBounds(soapX, 10, 80, 23);
        cmbKesadaran.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma" }));
        cmbKesadaran.setName("cmbKesadaran"); // NOI18N
        panelGlass12.add(cmbKesadaran);
        cmbKesadaran.setBounds(soapX + labelWidthKanan + 5, 10, fieldWidthKanan, 23);

        // Petugas Anamnesa (Moved here)
        jLabel37.setText("Petugas :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass12.add(jLabel37);
        jLabel37.setBounds(425, 10, 60, 23); // Next to Kesadaran
        KdPeg.setName("KdPeg"); // NOI18N
        panelGlass12.add(KdPeg);
        KdPeg.setBounds(490, 10, 80, 23);
        TPegawai.setEditable(false);
        TPegawai.setName("TPegawai"); // NOI18N
        panelGlass12.add(TPegawai);
        TPegawai.setBounds(575, 10, 180, 23);
        BtnSeekPegawai.setName("BtnSeekPegawai"); // NOI18N
        panelGlass12.add(BtnSeekPegawai);
        BtnSeekPegawai.setBounds(760, 10, 28, 23);

        // Keluhan (Subject)
        jLabel8.setText("Keluhan : (Subject)");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass12.add(jLabel8);
        jLabel8.setBounds(soapX, 40, 120, 23);
        scrollPane1.setName("scrollPane1"); // NOI18N
        TKeluhan.setName("TKeluhan"); // NOI18N
        scrollPane1.setViewportView(TKeluhan);
        panelGlass12.add(scrollPane1);
        scrollPane1.setBounds(soapX, 65, soapWidth, 80); // Increased height to 80

        // Pemeriksaan (Object)
        jLabel9.setText("Pemeriksaan : (Object)");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass12.add(jLabel9);
        jLabel9.setBounds(soapX, 150, 120, 23);
        scrollPane2.setName("scrollPane2"); // NOI18N
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        scrollPane2.setViewportView(TPemeriksaan);
        panelGlass12.add(scrollPane2);
        scrollPane2.setBounds(soapX, 175, soapWidth, 80); // Increased height to 80

        // === BLOK 3: TENGAH (ALERGI & PETUGAS) ===
        int middleY = 265; // Adjusted Y position below enlarged fields

        // BtnTTD - Tombol Tanda Tangan Dokter (di sebelah kiri Alergi)
        BtnTTD = new widget.Button();
        BtnTTD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnTTD.setText("Tanda Tangan");
        BtnTTD.setToolTipText("Wajib diisi oleh Dokter");
        BtnTTD.setName("BtnTTD");
        BtnTTD.setForeground(new java.awt.Color(0, 0, 0));
        BtnTTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (TNoRw.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Silahkan pilih pasien terlebih dahulu!");
                    return;
                }
                freehand.DlgTTDDokter form = new freehand.DlgTTDDokter(null, true);
                form.setNoRawat(TNoRw.getText());
                form.setVisible(true);
                if (!form.getNamaFile().equals("")) {
                    fileTTD = form.getNamaFile();
                    BtnTTD.setText("");
                    try {
                        String urlGambar = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                                + koneksiDB.HYBRIDWEB() + "/imagefreehand/" + fileTTD;
                        java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(new java.net.URL(urlGambar));
                        BtnTTD.setIcon(new javax.swing.ImageIcon(
                                image.getScaledInstance(80, 23, java.awt.Image.SCALE_SMOOTH)));
                    } catch (Exception ex) {
                        BtnTTD.setText("OK");
                    }
                }
            }
        });
        panelGlass12.add(BtnTTD);
        BtnTTD.setBounds(165, middleY, 110, 23);

        // Alergi & Lingkar Perut - Shifted to the right by 90px to make room for BtnTTD
        jLabel15.setText("Alergi :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelGlass12.add(jLabel15);
        jLabel15.setBounds(280, middleY, 70, 23);
        TAlergi.setName("TAlergi"); // NOI18N
        panelGlass12.add(TAlergi);
        TAlergi.setBounds(355, middleY, 230, 23);

        jLabel25.setText("Lingkar Perut :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelGlass12.add(jLabel25);
        jLabel25.setBounds(590, middleY, 90, 23);
        LingkarPerut.setName("LingkarPerut"); // NOI18N
        panelGlass12.add(LingkarPerut);
        LingkarPerut.setBounds(685, middleY, 80, 23);

        // Removed Petugas from here (moved up)

        // Sembunyikan komponen Jabatan
        Jabatan.setEditable(false);
        Jabatan.setName("Jabatan"); // NOI18N
        jLabel41.setText("Profesi / Jabatan / Departemen :");
        jLabel41.setName("jLabel41"); // NOI18N

        // === BLOK 4: BAWAH (A/P/I/E - 2x2 GRID) ===
        int bottomY = middleY + 50; // Increased spacing as requested (was 30)
        int halfWidth = 370; // 750 / 2 approx
        int areaHeight = 80; // Match height of Keluhan/Pemeriksaan
        int col2X = 395; // 5 + 370 + 20 gap

        // Row 1: Asesmen (Left) & Instruksi (Right)

        // Asesmen (A) - Left
        jLabel28.setText("Asesmen /Diagnosa :");
        jLabel28.setName("jLabel28"); // NOI18N
        panelGlass12.add(jLabel28);
        jLabel28.setBounds(5, bottomY, 130, 23);

        // Instruksi (I) - Right
        jLabel53.setText("Instruksi :");
        jLabel53.setName("jLabel53"); // NOI18N
        panelGlass12.add(jLabel53);
        jLabel53.setBounds(col2X, bottomY, 130, 23);

        bottomY += 25; // Pindah ke bawah label

        // Asesmen Input
        scrollPane3.setName("scrollPane3"); // NOI18N
        TPenilaian.setName("TPenilaian"); // NOI18N
        scrollPane3.setViewportView(TPenilaian);
        panelGlass12.add(scrollPane3);
        scrollPane3.setBounds(5, bottomY, halfWidth, areaHeight);

        // Instruksi Input
        scrollPane7.setName("scrollPane7"); // NOI18N
        TInstruksi.setName("TInstruksi"); // NOI18N
        scrollPane7.setViewportView(TInstruksi);
        panelGlass12.add(scrollPane7);
        scrollPane7.setBounds(col2X, bottomY, halfWidth, areaHeight);

        bottomY += areaHeight + 10; // Gap antar row

        // Row 2: Plan (Left) & Evaluasi (Right)

        // Plan (P) - Left
        jLabel26.setText("Plan /Terapi :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelGlass12.add(jLabel26);
        jLabel26.setBounds(5, bottomY, 130, 23);

        // Evaluasi (E) - Right
        jLabel56.setText("Evaluasi :");
        jLabel56.setName("jLabel56"); // NOI18N
        panelGlass12.add(jLabel56);
        jLabel56.setBounds(col2X, bottomY, 130, 23);

        bottomY += 25;

        // Plan Input
        scrollPane6.setName("scrollPane6"); // NOI18N
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        scrollPane6.setViewportView(TindakLanjut);
        panelGlass12.add(scrollPane6);
        scrollPane6.setBounds(5, bottomY, halfWidth, 180);

        // Evaluasi Input
        scrollPane8.setName("scrollPane8"); // NOI18N
        TEvaluasi.setName("TEvaluasi"); // NOI18N
        scrollPane8.setViewportView(TEvaluasi);
        panelGlass12.add(scrollPane8);
        scrollPane8.setBounds(col2X, bottomY, halfWidth, 180);

        bottomY += 180 + 5;

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(""); // Removed text as requested
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        panelGlass12.add(ChkInput);
        ChkInput.setBounds(-50, 290, 1513, 20);

        PanelInput.setLayout(new java.awt.BorderLayout());
        PanelInput.add(panelGlass12, java.awt.BorderLayout.CENTER);

        // Use JScrollPane for safety as widget.ScrollPane might miss the constructor
        splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT,
                new javax.swing.JScrollPane(PanelInput), Scroll3);
        splitPane.setDividerLocation(800);
        internalFrame5.add(splitPane, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("SOAP/ CPPT", internalFrame5);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPemeriksaanObstetri.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanObstetri.setName("tbPemeriksaanObstetri"); // NOI18N
        tbPemeriksaanObstetri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanObstetriMouseClicked(evt);
            }
        });
        tbPemeriksaanObstetri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanObstetriKeyReleased(evt);
            }
        });
        Scroll4.setViewportView(tbPemeriksaanObstetri);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setOpaque(false);
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); // NOI18N
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass13.setLayout(null);

        jLabel27.setText("Tinggi Fundus Uteri (Cm) :");
        jLabel27.setName("jLabel27"); // NOI18N
        panelGlass13.add(jLabel27);
        jLabel27.setBounds(0, 10, 135, 23);

        TTinggi_uteri.setHighlighter(null);
        TTinggi_uteri.setName("TTinggi_uteri"); // NOI18N
        TTinggi_uteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggi_uteriKeyPressed(evt);
            }
        });
        panelGlass13.add(TTinggi_uteri);
        TTinggi_uteri.setBounds(138, 10, 50, 23);

        jLabel30.setText("Janin :");
        jLabel30.setName("jLabel30"); // NOI18N
        panelGlass13.add(jLabel30);
        jLabel30.setBounds(194, 10, 45, 23);

        jLabel31.setText("Letak :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelGlass13.add(jLabel31);
        jLabel31.setBounds(375, 10, 40, 23);

        TLetak.setHighlighter(null);
        TLetak.setName("TLetak"); // NOI18N
        TLetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLetakKeyPressed(evt);
            }
        });
        panelGlass13.add(TLetak);
        TLetak.setBounds(418, 10, 50, 23);

        jLabel32.setText("Bagian Bawah Panggul :");
        jLabel32.setName("jLabel32"); // NOI18N
        panelGlass13.add(jLabel32);
        jLabel32.setBounds(486, 10, 130, 23);

        TKualitas_dtk.setFocusTraversalPolicyProvider(true);
        TKualitas_dtk.setName("TKualitas_dtk"); // NOI18N
        TKualitas_dtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_dtkKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_dtk);
        TKualitas_dtk.setBounds(402, 40, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("detik");
        jLabel33.setName("jLabel33"); // NOI18N
        panelGlass13.add(jLabel33);
        jLabel33.setBounds(455, 40, 30, 23);

        cmbPanggul.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "-", "5/5", "4/5", "3/5", "2/5", "1/5" }));
        cmbPanggul.setName("cmbPanggul"); // NOI18N
        cmbPanggul.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPanggul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPanggulKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbPanggul);
        cmbPanggul.setBounds(619, 10, 62, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("/10 menit/");
        jLabel34.setName("jLabel34"); // NOI18N
        panelGlass13.add(jLabel34);
        jLabel34.setBounds(343, 40, 58, 23);

        TTebal.setHighlighter(null);
        TTebal.setName("TTebal"); // NOI18N
        TTebal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTebalKeyPressed(evt);
            }
        });
        panelGlass13.add(TTebal);
        TTebal.setBounds(709, 70, 50, 23);

        TDenyut.setHighlighter(null);
        TDenyut.setName("TDenyut"); // NOI18N
        TDenyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenyutKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenyut);
        TDenyut.setBounds(876, 10, 50, 23);

        jLabel36.setText("Denyut Jantung Fetus (x/mnt) :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelGlass13.add(jLabel36);
        jLabel36.setBounds(693, 10, 170, 23);

        TDenominator.setHighlighter(null);
        TDenominator.setName("TDenominator"); // NOI18N
        TDenominator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenominatorKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenominator);
        TDenominator.setBounds(548, 100, 125, 23);

        jLabel38.setText("Penurunan :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelGlass13.add(jLabel38);
        jLabel38.setBounds(267, 100, 70, 23);

        jLabel39.setText("Imbang Feto-Pelvik :");
        jLabel39.setName("jLabel39"); // NOI18N
        panelGlass13.add(jLabel39);
        jLabel39.setBounds(673, 100, 110, 23);

        TKualitas_mnt.setHighlighter(null);
        TKualitas_mnt.setName("TKualitas_mnt"); // NOI18N
        TKualitas_mnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_mntKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_mnt);
        TKualitas_mnt.setBounds(293, 40, 50, 23);

        jLabel40.setText("Portio Inspekulo :");
        jLabel40.setName("jLabel40"); // NOI18N
        panelGlass13.add(jLabel40);
        jLabel40.setBounds(272, 70, 90, 23);

        cmbFeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Susp.CPD-FPD", "CPD-FPD" }));
        cmbFeto.setName("cmbFeto"); // NOI18N
        cmbFeto.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFetoKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFeto);
        cmbFeto.setBounds(786, 100, 140, 23);

        jLabel42.setText("Denominator :");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass13.add(jLabel42);
        jLabel42.setBounds(470, 100, 75, 23);

        cmbJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tunggal", "Gemelli" }));
        cmbJanin.setName("cmbJanin"); // NOI18N
        cmbJanin.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJaninKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbJanin);
        cmbJanin.setBounds(242, 10, 115, 23);

        cmbKetuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "+" }));
        cmbKetuban.setName("cmbKetuban"); // NOI18N
        cmbKetuban.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKetubanKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKetuban);
        cmbKetuban.setBounds(864, 40, 62, 23);

        TPortio.setFocusTraversalPolicyProvider(true);
        TPortio.setName("TPortio"); // NOI18N
        TPortio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioKeyPressed(evt);
            }
        });
        panelGlass13.add(TPortio);
        TPortio.setBounds(365, 70, 125, 23);

        jLabel43.setText("Kualitas (x/mnt) : ");
        jLabel43.setName("jLabel43"); // NOI18N
        panelGlass13.add(jLabel43);
        jLabel43.setBounds(193, 40, 100, 23);

        TVulva.setHighlighter(null);
        TVulva.setName("TVulva"); // NOI18N
        TVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaKeyPressed(evt);
            }
        });
        panelGlass13.add(TVulva);
        TVulva.setBounds(138, 70, 125, 23);

        cmbKontraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbKontraksi.setName("cmbKontraksi"); // NOI18N
        cmbKontraksi.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKontraksiKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKontraksi);
        cmbKontraksi.setBounds(138, 40, 62, 23);

        cmbAlbus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbAlbus.setName("cmbAlbus"); // NOI18N
        cmbAlbus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbAlbus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAlbusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbAlbus);
        cmbAlbus.setBounds(698, 40, 62, 23);

        jLabel45.setText("Kontraksi :");
        jLabel45.setName("jLabel45"); // NOI18N
        panelGlass13.add(jLabel45);
        jLabel45.setBounds(0, 40, 135, 23);

        jLabel46.setText("Fluor Albus :");
        jLabel46.setName("jLabel46"); // NOI18N
        panelGlass13.add(jLabel46);
        jLabel46.setBounds(623, 40, 72, 23);

        jLabel47.setText("Vulva/Vagina :");
        jLabel47.setName("jLabel47"); // NOI18N
        panelGlass13.add(jLabel47);
        jLabel47.setBounds(0, 70, 135, 23);

        jLabel44.setText("Fluksus :");
        jLabel44.setName("jLabel44"); // NOI18N
        panelGlass13.add(jLabel44);
        jLabel44.setBounds(488, 40, 58, 23);

        cmbFluksus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluksus.setName("cmbFluksus"); // NOI18N
        cmbFluksus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluksus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluksusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFluksus);
        cmbFluksus.setBounds(549, 40, 62, 23);

        jLabel48.setText("Dalam :");
        jLabel48.setName("jLabel48"); // NOI18N
        panelGlass13.add(jLabel48);
        jLabel48.setBounds(500, 70, 47, 23);

        cmbDalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kenyal", "Lunak" }));
        cmbDalam.setName("cmbDalam"); // NOI18N
        cmbDalam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDalamKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbDalam);
        cmbDalam.setBounds(550, 70, 95, 23);

        jLabel49.setText("Pembukaan :");
        jLabel49.setName("jLabel49"); // NOI18N
        panelGlass13.add(jLabel49);
        jLabel49.setBounds(0, 100, 135, 23);

        TPembukaan.setHighlighter(null);
        TPembukaan.setName("TPembukaan"); // NOI18N
        TPembukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPembukaanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPembukaan);
        TPembukaan.setBounds(138, 100, 125, 23);

        TPenurunan.setHighlighter(null);
        TPenurunan.setName("TPenurunan"); // NOI18N
        TPenurunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenurunanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPenurunan);
        TPenurunan.setBounds(340, 100, 125, 23);

        jLabel50.setText("Tebal/cm :");
        jLabel50.setName("jLabel50"); // NOI18N
        panelGlass13.add(jLabel50);
        jLabel50.setBounds(646, 70, 60, 23);

        jLabel51.setText("Selaput Ketuban :");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel51.setName("jLabel51"); // NOI18N
        panelGlass13.add(jLabel51);
        jLabel51.setBounds(771, 40, 90, 23);

        cmbArah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depan", "Axial", "Belakang" }));
        cmbArah.setName("cmbArah"); // NOI18N
        cmbArah.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbArah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbArahKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbArah);
        cmbArah.setBounds(806, 70, 120, 23);

        jLabel52.setText("Arah :");
        jLabel52.setName("jLabel52"); // NOI18N
        panelGlass13.add(jLabel52);
        jLabel52.setBounds(763, 70, 40, 23);

        PanelInput1.add(panelGlass13, java.awt.BorderLayout.CENTER);

        internalFrame6.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Obstetri", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Scroll5KeyPressed(evt);
            }
        });

        tbPemeriksaanGinekologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanGinekologi.setName("tbPemeriksaanGinekologi"); // NOI18N
        tbPemeriksaanGinekologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanGinekologiMouseClicked(evt);
            }
        });
        tbPemeriksaanGinekologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanGinekologiKeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbPemeriksaanGinekologi);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelInput2.setName("PanelInput2"); // NOI18N
        PanelInput2.setOpaque(false);
        PanelInput2.setPreferredSize(new java.awt.Dimension(192, 245));
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setMnemonic('I');
        ChkInput2.setText(".: Input Data");
        ChkInput2.setToolTipText("Alt+I");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2"); // NOI18N
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass14.setLayout(null);

        jLabel35.setText("Inspeksi :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelGlass14.add(jLabel35);
        jLabel35.setBounds(0, 10, 70, 23);

        TInspeksiVulva.setHighlighter(null);
        TInspeksiVulva.setName("TInspeksiVulva"); // NOI18N
        TInspeksiVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiVulvaKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksiVulva);
        TInspeksiVulva.setBounds(118, 40, 223, 23);

        TAdnexaKanan.setHighlighter(null);
        TAdnexaKanan.setName("TAdnexaKanan"); // NOI18N
        TAdnexaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKananKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKanan);
        TAdnexaKanan.setBounds(510, 120, 355, 23);

        jLabel57.setText("Fluor Albus :");
        jLabel57.setName("jLabel57"); // NOI18N
        panelGlass14.add(jLabel57);
        jLabel57.setBounds(206, 100, 70, 23);

        cmbMobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbMobilitas.setName("cmbMobilitas"); // NOI18N
        cmbMobilitas.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMobilitasKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbMobilitas);
        cmbMobilitas.setBounds(803, 60, 62, 23);

        jLabel60.setText("Sondage :");
        jLabel60.setName("jLabel60"); // NOI18N
        jLabel60.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel60);
        jLabel60.setBounds(20, 190, 95, 23);

        TInspekuloGine.setHighlighter(null);
        TInspekuloGine.setName("TInspekuloGine"); // NOI18N
        TInspekuloGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspekuloGineKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspekuloGine);
        TInspekuloGine.setBounds(73, 70, 268, 23);

        jLabel62.setText("Vulva/Uretra/Vagina :");
        jLabel62.setName("jLabel62"); // NOI18N
        panelGlass14.add(jLabel62);
        jLabel62.setBounds(0, 40, 115, 23);

        jLabel64.setText("Inspekulo :");
        jLabel64.setName("jLabel64"); // NOI18N
        panelGlass14.add(jLabel64);
        jLabel64.setBounds(0, 70, 70, 23);

        jLabel67.setText("Fluxus :");
        jLabel67.setName("jLabel67"); // NOI18N
        panelGlass14.add(jLabel67);
        jLabel67.setBounds(0, 100, 115, 23);

        TPortioInspekulo.setHighlighter(null);
        TPortioInspekulo.setName("TPortioInspekulo"); // NOI18N
        TPortioInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioInspekulo);
        TPortioInspekulo.setBounds(118, 160, 223, 23);

        TCavumUteri.setHighlighter(null);
        TCavumUteri.setName("TCavumUteri"); // NOI18N
        TCavumUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumUteriKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumUteri);
        TCavumUteri.setBounds(468, 60, 272, 23);

        cmbFluorGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluorGine.setName("cmbFluorGine"); // NOI18N
        cmbFluorGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluorGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluorGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluorGine);
        cmbFluorGine.setBounds(279, 100, 62, 23);

        TInspeksi.setHighlighter(null);
        TInspeksi.setName("TInspeksi"); // NOI18N
        TInspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksi);
        TInspeksi.setBounds(73, 10, 268, 23);

        cmbFluxusGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluxusGine.setName("cmbFluxusGine"); // NOI18N
        cmbFluxusGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluxusGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluxusGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluxusGine);
        cmbFluxusGine.setBounds(118, 100, 62, 23);

        jLabel71.setText("Adnexa/Parametrium :");
        jLabel71.setName("jLabel71"); // NOI18N
        jLabel71.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel71);
        jLabel71.setBounds(340, 120, 125, 23);

        jLabel72.setText("Portio :");
        jLabel72.setName("jLabel72"); // NOI18N
        jLabel72.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel72);
        jLabel72.setBounds(20, 160, 95, 23);

        jLabel73.setText("Vulva/Vagina :");
        jLabel73.setName("jLabel73"); // NOI18N
        jLabel73.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel73);
        jLabel73.setBounds(20, 130, 95, 23);

        jLabel74.setText("Pemeriksaan Dalam :");
        jLabel74.setName("jLabel74"); // NOI18N
        jLabel74.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel74);
        jLabel74.setBounds(340, 10, 110, 23);

        jLabel75.setText("Kanan :");
        jLabel75.setName("jLabel75"); // NOI18N
        jLabel75.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel75);
        jLabel75.setBounds(437, 120, 70, 23);

        TVulvaInspekulo.setHighlighter(null);
        TVulvaInspekulo.setName("TVulvaInspekulo"); // NOI18N
        TVulvaInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TVulvaInspekulo);
        TVulvaInspekulo.setBounds(118, 130, 223, 23);

        jLabel76.setText(", Bentuk :");
        jLabel76.setName("jLabel76"); // NOI18N
        jLabel76.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel76);
        jLabel76.setBounds(640, 30, 50, 23);

        jLabel77.setText(", Mobilitas :");
        jLabel77.setName("jLabel77"); // NOI18N
        jLabel77.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel77);
        jLabel77.setBounds(740, 60, 60, 23);

        TPortioDalam.setHighlighter(null);
        TPortioDalam.setName("TPortioDalam"); // NOI18N
        TPortioDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioDalamKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioDalam);
        TPortioDalam.setBounds(468, 30, 173, 23);

        TBentuk.setHighlighter(null);
        TBentuk.setName("TBentuk"); // NOI18N
        TBentuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBentukKeyPressed(evt);
            }
        });
        panelGlass14.add(TBentuk);
        TBentuk.setBounds(693, 30, 173, 23);

        jLabel78.setText("Ukuran :");
        jLabel78.setName("jLabel78"); // NOI18N
        jLabel78.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel78);
        jLabel78.setBounds(437, 90, 70, 23);

        cmbNyeriTekan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbNyeriTekan.setName("cmbNyeriTekan"); // NOI18N
        cmbNyeriTekan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbNyeriTekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbNyeriTekanKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbNyeriTekan);
        cmbNyeriTekan.setBounds(803, 90, 62, 23);

        TSondage.setHighlighter(null);
        TSondage.setName("TSondage"); // NOI18N
        TSondage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSondageKeyPressed(evt);
            }
        });
        panelGlass14.add(TSondage);
        TSondage.setBounds(118, 190, 223, 23);

        jLabel79.setText("Cavum Uteri :");
        jLabel79.setName("jLabel79"); // NOI18N
        jLabel79.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel79);
        jLabel79.setBounds(340, 60, 125, 23);

        jLabel80.setText("Kiri :");
        jLabel80.setName("jLabel80"); // NOI18N
        jLabel80.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel80);
        jLabel80.setBounds(437, 150, 70, 23);

        TAdnexaKiri.setHighlighter(null);
        TAdnexaKiri.setName("TAdnexaKiri"); // NOI18N
        TAdnexaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKiriKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKiri);
        TAdnexaKiri.setBounds(510, 150, 355, 23);

        jLabel81.setText("Cavum Douglas :");
        jLabel81.setName("jLabel81"); // NOI18N
        jLabel81.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel81);
        jLabel81.setBounds(340, 180, 125, 23);

        TCavumDouglas.setHighlighter(null);
        TCavumDouglas.setName("TCavumDouglas"); // NOI18N
        TCavumDouglas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumDouglasKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumDouglas);
        TCavumDouglas.setBounds(468, 180, 397, 23);

        TUkuran.setHighlighter(null);
        TUkuran.setName("TUkuran"); // NOI18N
        TUkuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUkuranKeyPressed(evt);
            }
        });
        panelGlass14.add(TUkuran);
        TUkuran.setBounds(510, 90, 217, 23);

        jLabel82.setText(", Nyeri Tekan :");
        jLabel82.setName("jLabel82"); // NOI18N
        jLabel82.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel82);
        jLabel82.setBounds(724, 90, 76, 23);

        jLabel83.setText("Portio :");
        jLabel83.setName("jLabel83"); // NOI18N
        jLabel83.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel83);
        jLabel83.setBounds(340, 30, 125, 23);

        PanelInput2.add(panelGlass14, java.awt.BorderLayout.CENTER);

        internalFrame7.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Ginekologi", internalFrame7);

        panelDiagnosa1.setBorder(null);
        panelDiagnosa1.setName("panelDiagnosa1"); // NOI18N
        TabRawat.addTab("Diagnosa", panelDiagnosa1);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput3.setName("PanelInput3"); // NOI18N
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 140));
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setMnemonic('I');
        ChkInput3.setText(".: Input Data");
        ChkInput3.setToolTipText("Alt+I");
        ChkInput3.setBorderPainted(true);
        ChkInput3.setBorderPaintedFlat(true);
        ChkInput3.setFocusable(false);
        ChkInput3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput3.setName("ChkInput3"); // NOI18N
        ChkInput3.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput3ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput3, java.awt.BorderLayout.PAGE_END);

        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass15.setLayout(null);

        jLabel55.setText("Catatan :");
        jLabel55.setName("jLabel55"); // NOI18N
        panelGlass15.add(jLabel55);
        jLabel55.setBounds(0, 40, 60, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Catatan.setBorder(null);
        Catatan.setColumns(20);
        Catatan.setRows(5);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Catatan);

        panelGlass15.add(scrollPane4);
        scrollPane4.setBounds(64, 40, 713, 68);

        jLabel11.setText("Dokter :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass15.add(jLabel11);
        jLabel11.setBounds(0, 10, 60, 23);

        KdDok3.setHighlighter(null);
        KdDok3.setName("KdDok3"); // NOI18N
        KdDok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok3KeyPressed(evt);
            }
        });
        panelGlass15.add(KdDok3);
        KdDok3.setBounds(64, 10, 146, 23);

        TDokter3.setEditable(false);
        TDokter3.setHighlighter(null);
        TDokter3.setName("TDokter3"); // NOI18N
        panelGlass15.add(TDokter3);
        TDokter3.setBounds(212, 10, 534, 23);

        BtnSeekDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter3.setMnemonic('4');
        BtnSeekDokter3.setToolTipText("ALt+4");
        BtnSeekDokter3.setName("BtnSeekDokter3"); // NOI18N
        BtnSeekDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter3ActionPerformed(evt);
            }
        });
        panelGlass15.add(BtnSeekDokter3);
        BtnSeekDokter3.setBounds(749, 10, 28, 23);

        PanelInput3.add(panelGlass15, java.awt.BorderLayout.CENTER);

        internalFrame8.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbCatatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbCatatan.setName("tbCatatan"); // NOI18N
        tbCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCatatanMouseClicked(evt);
            }
        });
        tbCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCatatanKeyReleased(evt);
            }
        });
        Scroll11.setViewportView(tbCatatan);

        internalFrame8.add(Scroll11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Dokter", internalFrame8);

        internalFrame11.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll14.setName("Scroll14"); // NOI18N
        Scroll14.setOpaque(true);

        tbPemeriksaanSbar.setAutoCreateRowSorter(true);
        tbPemeriksaanSbar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanSbar.setName("tbPemeriksaanSbar"); // NOI18N
        tbPemeriksaanSbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanSbarMouseClicked(evt);
            }
        });
        tbPemeriksaanSbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanSbarKeyReleased(evt);
            }
        });
        Scroll14.setViewportView(tbPemeriksaanSbar);

        internalFrame11.add(Scroll14, java.awt.BorderLayout.CENTER);

        PanelInput4.setName("PanelInput4"); // NOI18N
        PanelInput4.setOpaque(false);
        PanelInput4.setPreferredSize(new java.awt.Dimension(192, 273));
        PanelInput4.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setMnemonic('I');
        ChkInput4.setText(".: Input Data");
        ChkInput4.setToolTipText("Alt+I");
        ChkInput4.setBorderPainted(true);
        ChkInput4.setBorderPaintedFlat(true);
        ChkInput4.setFocusable(false);
        ChkInput4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput4.setName("ChkInput4"); // NOI18N
        ChkInput4.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput4ActionPerformed(evt);
            }
        });
        PanelInput4.add(ChkInput4, java.awt.BorderLayout.PAGE_END);

        panelGlass17.setName("panelGlass17"); // NOI18N
        panelGlass17.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass17.setLayout(null);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TSituation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSituation.setColumns(20);
        TSituation.setRows(5);
        TSituation.setName("TSituation"); // NOI18N
        TSituation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSituationKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TSituation);

        panelGlass17.add(scrollPane5);
        scrollPane5.setBounds(70, 50, 360, 70);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        TBackground.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TBackground.setColumns(20);
        TBackground.setRows(5);
        TBackground.setName("TBackground"); // NOI18N
        TBackground.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBackgroundKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(TBackground);

        panelGlass17.add(scrollPane9);
        scrollPane9.setBounds(70, 130, 360, 70);

        jLabel88.setText("Situation :");
        jLabel88.setName("jLabel88"); // NOI18N
        panelGlass17.add(jLabel88);
        jLabel88.setBounds(0, 50, 70, 23);

        jLabel89.setText("Background :");
        jLabel89.setName("jLabel89"); // NOI18N
        panelGlass17.add(jLabel89);
        jLabel89.setBounds(0, 130, 70, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        TAssesment.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAssesment.setColumns(20);
        TAssesment.setRows(5);
        TAssesment.setName("TAssesment"); // NOI18N
        TAssesment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAssesmentKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(TAssesment);

        panelGlass17.add(scrollPane10);
        scrollPane10.setBounds(550, 50, 360, 70);

        jLabel90.setText("Asesmen :");
        jLabel90.setName("jLabel90"); // NOI18N
        panelGlass17.add(jLabel90);
        jLabel90.setBounds(450, 50, 90, 23);

        jLabel91.setText("Recommendation :");
        jLabel91.setName("jLabel91"); // NOI18N
        panelGlass17.add(jLabel91);
        jLabel91.setBounds(450, 130, 90, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        TRecommendation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRecommendation.setColumns(20);
        TRecommendation.setRows(5);
        TRecommendation.setName("TRecommendation"); // NOI18N
        TRecommendation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRecommendationKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(TRecommendation);

        panelGlass17.add(scrollPane11);
        scrollPane11.setBounds(550, 130, 360, 70);

        jLabel92.setText("Dilakukan :");
        jLabel92.setName("jLabel92"); // NOI18N
        panelGlass17.add(jLabel92);
        jLabel92.setBounds(0, 10, 70, 23);

        KdPeg2.setHighlighter(null);
        KdPeg2.setName("KdPeg2"); // NOI18N
        KdPeg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPeg2ActionPerformed(evt);
            }
        });
        KdPeg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg2KeyPressed(evt);
            }
        });
        panelGlass17.add(KdPeg2);
        KdPeg2.setBounds(70, 10, 115, 23);

        TPegawai2.setEditable(false);
        TPegawai2.setHighlighter(null);
        TPegawai2.setName("TPegawai2"); // NOI18N
        TPegawai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPegawai2ActionPerformed(evt);
            }
        });
        panelGlass17.add(TPegawai2);
        TPegawai2.setBounds(190, 10, 212, 23);

        BtnSeekPegawai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai1.setMnemonic('4');
        BtnSeekPegawai1.setToolTipText("ALt+4");
        BtnSeekPegawai1.setName("BtnSeekPegawai1"); // NOI18N
        BtnSeekPegawai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai1ActionPerformed(evt);
            }
        });
        panelGlass17.add(BtnSeekPegawai1);
        BtnSeekPegawai1.setBounds(405, 10, 28, 23);

        Jabatan1.setEditable(false);
        Jabatan1.setHighlighter(null);
        Jabatan1.setName("Jabatan1"); // NOI18N
        panelGlass17.add(Jabatan1);
        Jabatan1.setBounds(630, 10, 209, 23);

        jLabel93.setText("Profesi / Jabatan / Departemen :");
        jLabel93.setName("jLabel93"); // NOI18N
        panelGlass17.add(jLabel93);
        jLabel93.setBounds(440, 10, 190, 23);

        TPegawai3.setEditable(false);
        TPegawai3.setHighlighter(null);
        TPegawai3.setName("TPegawai3"); // NOI18N
        panelGlass17.add(TPegawai3);
        TPegawai3.setBounds(330, 520, 212, 23);

        KdPeg3.setHighlighter(null);
        KdPeg3.setName("KdPeg3"); // NOI18N
        KdPeg3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg3KeyPressed(evt);
            }
        });
        panelGlass17.add(KdPeg3);
        KdPeg3.setBounds(210, 520, 115, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Dokter DPJP Utama");
        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setName("jLabel70"); // NOI18N
        panelGlass17.add(jLabel70);
        jLabel70.setBounds(80, 520, 140, 23);

        BtnVerifSbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/folder.png"))); // NOI18N
        BtnVerifSbar.setMnemonic('4');
        BtnVerifSbar.setToolTipText("ALt+4");
        BtnVerifSbar.setName("BtnVerifSbar"); // NOI18N
        BtnVerifSbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerifSbarActionPerformed(evt);
            }
        });
        panelGlass17.add(BtnVerifSbar);
        BtnVerifSbar.setBounds(920, 50, 28, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Status Verifikasi SBAR");
        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel84.setName("jLabel84"); // NOI18N
        panelGlass17.add(jLabel84);
        jLabel84.setBounds(960, 50, 200, 23);

        PanelInput4.add(panelGlass17, java.awt.BorderLayout.CENTER);

        internalFrame11.add(PanelInput4, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("SBAR", internalFrame11);

        internalFrame12.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame12.setName("internalFrame12"); // NOI18N
        internalFrame12.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll15.setName("Scroll15"); // NOI18N
        Scroll15.setOpaque(true);

        tbPemeriksaanTbak.setAutoCreateRowSorter(true);
        tbPemeriksaanTbak.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanTbak.setName("tbPemeriksaanTbak"); // NOI18N
        tbPemeriksaanTbak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanTbakMouseClicked(evt);
            }
        });
        tbPemeriksaanTbak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanTbakKeyReleased(evt);
            }
        });
        Scroll15.setViewportView(tbPemeriksaanTbak);

        internalFrame12.add(Scroll15, java.awt.BorderLayout.CENTER);

        PanelInput5.setName("PanelInput5"); // NOI18N
        PanelInput5.setOpaque(false);
        PanelInput5.setPreferredSize(new java.awt.Dimension(192, 273));
        PanelInput5.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput5.setMnemonic('I');
        ChkInput5.setText(".: Input Data");
        ChkInput5.setToolTipText("Alt+I");
        ChkInput5.setBorderPainted(true);
        ChkInput5.setBorderPaintedFlat(true);
        ChkInput5.setFocusable(false);
        ChkInput5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput5.setName("ChkInput5"); // NOI18N
        ChkInput5.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput5.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput5ActionPerformed(evt);
            }
        });
        PanelInput5.add(ChkInput5, java.awt.BorderLayout.PAGE_END);

        panelGlass18.setName("panelGlass18"); // NOI18N
        panelGlass18.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass18.setLayout(null);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        TSituation1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSituation1.setColumns(20);
        TSituation1.setRows(5);
        TSituation1.setName("TSituation1"); // NOI18N
        TSituation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSituation1KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(TSituation1);

        panelGlass18.add(scrollPane12);
        scrollPane12.setBounds(70, 50, 370, 110);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        TBackground1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TBackground1.setColumns(20);
        TBackground1.setRows(5);
        TBackground1.setName("TBackground1"); // NOI18N
        TBackground1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBackground1KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(TBackground1);

        panelGlass18.add(scrollPane13);
        scrollPane13.setBounds(90, 930, 360, 70);

        jLabel94.setText("Instruksi :");
        jLabel94.setName("jLabel94"); // NOI18N
        panelGlass18.add(jLabel94);
        jLabel94.setBounds(0, 50, 70, 23);

        jLabel95.setText("Background :");
        jLabel95.setName("jLabel95"); // NOI18N
        panelGlass18.add(jLabel95);
        jLabel95.setBounds(20, 930, 70, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        TAssesment1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAssesment1.setColumns(20);
        TAssesment1.setRows(5);
        TAssesment1.setName("TAssesment1"); // NOI18N
        TAssesment1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAssesment1KeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(TAssesment1);

        panelGlass18.add(scrollPane14);
        scrollPane14.setBounds(570, 850, 360, 70);

        jLabel96.setText("Asesmen :");
        jLabel96.setName("jLabel96"); // NOI18N
        panelGlass18.add(jLabel96);
        jLabel96.setBounds(470, 850, 90, 23);

        jLabel97.setText("Recommendation :");
        jLabel97.setName("jLabel97"); // NOI18N
        panelGlass18.add(jLabel97);
        jLabel97.setBounds(470, 930, 90, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        TRecommendation1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRecommendation1.setColumns(20);
        TRecommendation1.setRows(5);
        TRecommendation1.setName("TRecommendation1"); // NOI18N
        TRecommendation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRecommendation1KeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(TRecommendation1);

        panelGlass18.add(scrollPane15);
        scrollPane15.setBounds(570, 930, 360, 70);

        jLabel98.setText("Dilakukan :");
        jLabel98.setName("jLabel98"); // NOI18N
        panelGlass18.add(jLabel98);
        jLabel98.setBounds(0, 10, 70, 23);

        KdPeg4.setHighlighter(null);
        KdPeg4.setName("KdPeg4"); // NOI18N
        KdPeg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPeg4ActionPerformed(evt);
            }
        });
        KdPeg4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg4KeyPressed(evt);
            }
        });
        panelGlass18.add(KdPeg4);
        KdPeg4.setBounds(70, 10, 115, 23);

        TPegawai4.setEditable(false);
        TPegawai4.setHighlighter(null);
        TPegawai4.setName("TPegawai4"); // NOI18N
        TPegawai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPegawai4ActionPerformed(evt);
            }
        });
        panelGlass18.add(TPegawai4);
        TPegawai4.setBounds(190, 10, 212, 23);

        BtnSeekPegawai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai2.setMnemonic('4');
        BtnSeekPegawai2.setToolTipText("ALt+4");
        BtnSeekPegawai2.setName("BtnSeekPegawai2"); // NOI18N
        BtnSeekPegawai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai2ActionPerformed(evt);
            }
        });
        panelGlass18.add(BtnSeekPegawai2);
        BtnSeekPegawai2.setBounds(405, 10, 28, 23);

        Jabatan2.setEditable(false);
        Jabatan2.setHighlighter(null);
        Jabatan2.setName("Jabatan2"); // NOI18N
        panelGlass18.add(Jabatan2);
        Jabatan2.setBounds(630, 10, 209, 23);

        jLabel99.setText("Profesi / Jabatan / Departemen :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelGlass18.add(jLabel99);
        jLabel99.setBounds(440, 10, 190, 23);

        TPegawai5.setEditable(false);
        TPegawai5.setHighlighter(null);
        TPegawai5.setName("TPegawai5"); // NOI18N
        panelGlass18.add(TPegawai5);
        TPegawai5.setBounds(340, 1010, 212, 23);

        KdPeg5.setHighlighter(null);
        KdPeg5.setName("KdPeg5"); // NOI18N
        KdPeg5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg5KeyPressed(evt);
            }
        });
        panelGlass18.add(KdPeg5);
        KdPeg5.setBounds(220, 1010, 115, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Dokter DPJP Utama");
        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel85.setName("jLabel85"); // NOI18N
        panelGlass18.add(jLabel85);
        jLabel85.setBounds(90, 1010, 140, 23);

        BtnVerifSbar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/folder.png"))); // NOI18N
        BtnVerifSbar1.setMnemonic('4');
        BtnVerifSbar1.setToolTipText("ALt+4");
        BtnVerifSbar1.setName("BtnVerifSbar1"); // NOI18N
        BtnVerifSbar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerifSbar1ActionPerformed(evt);
            }
        });
        panelGlass18.add(BtnVerifSbar1);
        BtnVerifSbar1.setBounds(460, 50, 28, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Status Verifikasi TBAK");
        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel86.setName("jLabel86"); // NOI18N
        panelGlass18.add(jLabel86);
        jLabel86.setBounds(500, 50, 200, 23);

        PanelInput5.add(panelGlass18, java.awt.BorderLayout.CENTER);

        internalFrame12.add(PanelInput5, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("TBAK", internalFrame12);

        internalFrame14.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame14.setName("internalFrame14"); // NOI18N
        internalFrame14.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput7.setName("PanelInput7"); // NOI18N
        PanelInput7.setOpaque(false);
        PanelInput7.setPreferredSize(new java.awt.Dimension(192, 140));
        PanelInput7.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput7.setMnemonic('I');
        ChkInput7.setText(".: Input Data");
        ChkInput7.setToolTipText("Alt+I");
        ChkInput7.setBorderPainted(true);
        ChkInput7.setBorderPaintedFlat(true);
        ChkInput7.setFocusable(false);
        ChkInput7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput7.setName("ChkInput7"); // NOI18N
        ChkInput7.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput7.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput7ActionPerformed(evt);
            }
        });
        PanelInput7.add(ChkInput7, java.awt.BorderLayout.PAGE_END);

        panelGlass20.setName("panelGlass20"); // NOI18N
        panelGlass20.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass20.setLayout(null);

        jLabel66.setText("Catatan :");
        jLabel66.setName("jLabel66"); // NOI18N
        panelGlass20.add(jLabel66);
        jLabel66.setBounds(0, 40, 60, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Catatan1.setBorder(null);
        Catatan1.setColumns(20);
        Catatan1.setRows(5);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Catatan1);

        panelGlass20.add(scrollPane17);
        scrollPane17.setBounds(64, 40, 713, 68);

        jLabel68.setText("Petugas :");
        jLabel68.setName("jLabel68"); // NOI18N
        panelGlass20.add(jLabel68);
        jLabel68.setBounds(0, 10, 63, 23);

        kdptg3.setHighlighter(null);
        kdptg3.setName("kdptg3"); // NOI18N
        kdptg3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg3KeyPressed(evt);
            }
        });
        panelGlass20.add(kdptg3);
        kdptg3.setBounds(66, 10, 146, 23);

        TPerawat3.setEditable(false);
        TPerawat3.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat3.setHighlighter(null);
        TPerawat3.setName("TPerawat3"); // NOI18N
        panelGlass20.add(TPerawat3);
        TPerawat3.setBounds(214, 10, 532, 23);

        BtnSeekPetugas3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas3.setMnemonic('5');
        BtnSeekPetugas3.setToolTipText("ALt+5");
        BtnSeekPetugas3.setName("BtnSeekPetugas3"); // NOI18N
        BtnSeekPetugas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas3ActionPerformed(evt);
            }
        });
        panelGlass20.add(BtnSeekPetugas3);
        BtnSeekPetugas3.setBounds(749, 10, 28, 23);

        PanelInput7.add(panelGlass20, java.awt.BorderLayout.CENTER);

        internalFrame14.add(PanelInput7, java.awt.BorderLayout.PAGE_START);

        Scroll17.setName("Scroll17"); // NOI18N
        Scroll17.setOpaque(true);
        Scroll17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll17MouseClicked(evt);
            }
        });

        tbCatatanPerawatIGD.setAutoCreateRowSorter(true);
        tbCatatanPerawatIGD.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbCatatanPerawatIGD.setName("tbCatatanPerawatIGD"); // NOI18N
        tbCatatanPerawatIGD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCatatanPerawatIGDMouseClicked(evt);
            }
        });
        tbCatatanPerawatIGD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCatatanPerawatIGDKeyReleased(evt);
            }
        });
        Scroll17.setViewportView(tbCatatanPerawatIGD);

        internalFrame14.add(Scroll17, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Perawat IGD", internalFrame14);

        internalFrame15.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame15.setName("internalFrame15"); // NOI18N
        internalFrame15.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput6.setName("PanelInput6"); // NOI18N
        PanelInput6.setOpaque(false);
        PanelInput6.setPreferredSize(new java.awt.Dimension(150, 200));
        PanelInput6.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(150, 90));
        FormInput1.setLayout(null);

        btnJenisAlergi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenisAlergi.setMnemonic('1');
        btnJenisAlergi.setText("Jenis Alergi");
        btnJenisAlergi.setToolTipText("Alt+1");
        btnJenisAlergi.setName("btnJenisAlergi"); // NOI18N
        btnJenisAlergi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisAlergiActionPerformed(evt);
            }
        });
        btnJenisAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnJenisAlergiKeyPressed(evt);
            }
        });
        FormInput1.add(btnJenisAlergi);
        btnJenisAlergi.setBounds(710, 40, 150, 23);

        jLabel59.setText("Display :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput1.add(jLabel59);
        jLabel59.setBounds(410, 40, 60, 23);

        AlergyDisplay.setEditable(false);
        AlergyDisplay.setHighlighter(null);
        AlergyDisplay.setName("AlergyDisplay"); // NOI18N
        AlergyDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergyDisplayKeyPressed(evt);
            }
        });
        FormInput1.add(AlergyDisplay);
        AlergyDisplay.setBounds(470, 40, 230, 23);

        AlergySystem.setEditable(false);
        AlergySystem.setHighlighter(null);
        AlergySystem.setName("AlergySystem"); // NOI18N
        AlergySystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergySystemKeyPressed(evt);
            }
        });
        FormInput1.add(AlergySystem);
        AlergySystem.setBounds(210, 40, 200, 23);

        jLabel61.setText("System :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput1.add(jLabel61);
        jLabel61.setBounds(150, 40, 60, 23);

        AlergiCode.setEditable(false);
        AlergiCode.setHighlighter(null);
        AlergiCode.setName("AlergiCode"); // NOI18N
        AlergiCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiCodeKeyPressed(evt);
            }
        });
        FormInput1.add(AlergiCode);
        AlergiCode.setBounds(70, 40, 80, 23);

        jLabel69.setText("Note / Keterangan :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput1.add(jLabel69);
        jLabel69.setBounds(210, 110, 120, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        TKeterangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeterangan.setColumns(20);
        TKeterangan.setRows(5);
        TKeterangan.setName("TKeterangan"); // NOI18N
        TKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeteranganKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(TKeterangan);

        FormInput1.add(scrollPane16);
        scrollPane16.setBounds(330, 100, 370, 70);

        jLabel87.setText("Code :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput1.add(jLabel87);
        jLabel87.setBounds(0, 40, 70, 23);

        cmbKategory.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Makanan", "Medication", "Lingkungan", "Biologis" }));
        cmbKategory.setName("cmbKategory"); // NOI18N
        cmbKategory.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKategoryKeyPressed(evt);
            }
        });
        FormInput1.add(cmbKategory);
        cmbKategory.setBounds(70, 110, 120, 23);

        jLabel100.setText("Kategori :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput1.add(jLabel100);
        jLabel100.setBounds(0, 110, 70, 23);

        btnReaksiAlergi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnReaksiAlergi.setMnemonic('1');
        btnReaksiAlergi.setText("Reaksi Alergi");
        btnReaksiAlergi.setToolTipText("Alt+1");
        btnReaksiAlergi.setName("btnReaksiAlergi"); // NOI18N
        btnReaksiAlergi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReaksiAlergiActionPerformed(evt);
            }
        });
        btnReaksiAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnReaksiAlergiKeyPressed(evt);
            }
        });
        FormInput1.add(btnReaksiAlergi);
        btnReaksiAlergi.setBounds(710, 70, 150, 23);

        ReaksiDisplay.setEditable(false);
        ReaksiDisplay.setHighlighter(null);
        ReaksiDisplay.setName("ReaksiDisplay"); // NOI18N
        ReaksiDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiDisplayKeyPressed(evt);
            }
        });
        FormInput1.add(ReaksiDisplay);
        ReaksiDisplay.setBounds(420, 70, 280, 23);

        ReaksiSystem.setEditable(false);
        ReaksiSystem.setHighlighter(null);
        ReaksiSystem.setName("ReaksiSystem"); // NOI18N
        ReaksiSystem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiSystemKeyPressed(evt);
            }
        });
        FormInput1.add(ReaksiSystem);
        ReaksiSystem.setBounds(160, 70, 250, 23);

        ReaksiCode.setEditable(false);
        ReaksiCode.setHighlighter(null);
        ReaksiCode.setName("ReaksiCode"); // NOI18N
        ReaksiCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiCodeKeyPressed(evt);
            }
        });
        FormInput1.add(ReaksiCode);
        ReaksiCode.setBounds(70, 70, 80, 23);

        jLabel101.setText("Reaksi :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput1.add(jLabel101);
        jLabel101.setBounds(0, 70, 70, 23);

        jLabel102.setText("Critical? :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput1.add(jLabel102);
        jLabel102.setBounds(0, 140, 70, 23);

        cmbSeverity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "low", "high", "unable-to-assess" }));
        cmbSeverity.setName("cmbSeverity"); // NOI18N
        cmbSeverity.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbSeverity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSeverityKeyPressed(evt);
            }
        });
        FormInput1.add(cmbSeverity);
        cmbSeverity.setBounds(70, 140, 120, 23);

        jLabel58.setText("Dilakukan :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput1.add(jLabel58);
        jLabel58.setBounds(0, 10, 70, 23);

        KdPeg1.setHighlighter(null);
        KdPeg1.setName("KdPeg1"); // NOI18N
        KdPeg1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeg1KeyPressed(evt);
            }
        });
        FormInput1.add(KdPeg1);
        KdPeg1.setBounds(70, 10, 115, 23);

        TPegawai1.setEditable(false);
        TPegawai1.setHighlighter(null);
        TPegawai1.setName("TPegawai1"); // NOI18N
        FormInput1.add(TPegawai1);
        TPegawai1.setBounds(190, 10, 212, 23);

        BtnSeekPegawai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai3.setMnemonic('4');
        BtnSeekPegawai3.setToolTipText("ALt+4");
        BtnSeekPegawai3.setName("BtnSeekPegawai3"); // NOI18N
        BtnSeekPegawai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai3ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnSeekPegawai3);
        BtnSeekPegawai3.setBounds(400, 10, 28, 23);

        Jabatan4.setEditable(false);
        Jabatan4.setHighlighter(null);
        Jabatan4.setName("Jabatan4"); // NOI18N
        FormInput1.add(Jabatan4);
        Jabatan4.setBounds(620, 10, 178, 23);

        jLabel103.setText("Profesi / Jabatan / Departemen :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput1.add(jLabel103);
        jLabel103.setBounds(430, 10, 190, 23);

        PanelInput6.add(FormInput1, java.awt.BorderLayout.CENTER);

        internalFrame15.add(PanelInput6, java.awt.BorderLayout.PAGE_START);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

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
        Scroll1.setViewportView(tbObat);

        internalFrame15.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Data Alergi", internalFrame15);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TNoRwMouseClicked(evt);
            }
        });
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(283, 10, 270, 23);

        jLabel23.setText("Tanggal :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(554, 10, 60, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-10-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                        "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                        "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
                        "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                        "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46",
                        "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(841, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLahirKeyPressed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(1130, 10, 125, 23);

        jLabel63.setText("Tanggal Lahir:");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(1060, 10, 70, 23);

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        FormInput.add(Umur);
        Umur.setBounds(1310, 10, 130, 23);

        jLabel65.setText("Umur:");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(1260, 10, 40, 23);

        BtnPanggilPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/man1-24.png"))); // NOI18N
        BtnPanggilPasien.setMnemonic('S');
        BtnPanggilPasien.setText("Panggil Pasien");
        BtnPanggilPasien.setToolTipText("Alt+S");
        BtnPanggilPasien.setName("BtnPanggilPasien"); // NOI18N
        BtnPanggilPasien.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPanggilPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanggilPasienActionPerformed(evt);
            }
        });
        BtnPanggilPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanggilPasienKeyPressed(evt);
            }
        });
        FormInput.add(BtnPanggilPasien);
        BtnPanggilPasien.setBounds(930, 10, 130, 30);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(205, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(130, 383));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(150, 483));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayat);

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat.setText("Input Resep");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); // NOI18N
        BtnResepObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepObat);

        BtnTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTemplate.setText("Input Template Resep");
        BtnTemplate.setFocusPainted(false);
        BtnTemplate.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTemplate.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTemplate.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTemplate.setName("BtnTemplate"); // NOI18N
        BtnTemplate.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTemplate.setRoundRect(false);
        BtnTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplateActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTemplate);

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); // NOI18N
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCopyResep);

        BtnResepLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepLuar.setText("Resep Luar");
        BtnResepLuar.setFocusPainted(false);
        BtnResepLuar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepLuar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepLuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepLuar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepLuar.setName("BtnResepLuar"); // NOI18N
        BtnResepLuar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepLuar.setRoundRect(false);
        BtnResepLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepLuarActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepLuar);

        BtnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInputObat.setText("Input Obat & BHP");
        BtnInputObat.setFocusPainted(false);
        BtnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInputObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInputObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInputObat.setName("BtnInputObat"); // NOI18N
        BtnInputObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInputObat.setRoundRect(false);
        BtnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInputObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInputObat);

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); // NOI18N
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });
        FormMenu.add(BtnObatBhp);

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); // NOI18N
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnBerkasDigital);

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); // NOI18N
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanLab);

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); // NOI18N
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanRad);

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); // NOI18N
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnJadwalOperasi);

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); // NOI18N
        BtnSKDP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSKDP);

        BtnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKamar.setText("Kamar Inap");
        BtnKamar.setFocusPainted(false);
        BtnKamar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKamar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKamar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKamar.setName("BtnKamar"); // NOI18N
        BtnKamar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKamar.setRoundRect(false);
        BtnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKamar);

        BtnTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTriaseIGD.setText("Triase IGD");
        BtnTriaseIGD.setFocusPainted(false);
        BtnTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTriaseIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTriaseIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTriaseIGD.setName("BtnTriaseIGD"); // NOI18N
        BtnTriaseIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTriaseIGD.setRoundRect(false);
        BtnTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTriaseIGDActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTriaseIGD);

        BtnRujukInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukInternal.setText("Rujuk Internal");
        BtnRujukInternal.setFocusPainted(false);
        BtnRujukInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukInternal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukInternal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukInternal.setName("BtnRujukInternal"); // NOI18N
        BtnRujukInternal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukInternal.setRoundRect(false);
        BtnRujukInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukInternalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRujukInternal);

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResume.setText("Resume Pasien");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); // NOI18N
        BtnResume.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResume);

        BtnAwalKeperawatanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanIGD.setText("Awal Keperawatan IGD");
        BtnAwalKeperawatanIGD.setFocusPainted(false);
        BtnAwalKeperawatanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanIGD.setName("BtnAwalKeperawatanIGD"); // NOI18N
        BtnAwalKeperawatanIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanIGD.setRoundRect(false);
        BtnAwalKeperawatanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanIGDActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanIGD);

        BtnAwalKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatan.setText("Awal Keperawatan Umum");
        BtnAwalKeperawatan.setFocusPainted(false);
        BtnAwalKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan.setName("BtnAwalKeperawatan"); // NOI18N
        BtnAwalKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan.setRoundRect(false);
        BtnAwalKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatan);

        BtnAwalKeperawatanGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanGigi.setText("Awal Keperawatan Gigi");
        BtnAwalKeperawatanGigi.setFocusPainted(false);
        BtnAwalKeperawatanGigi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanGigi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGigi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGigi.setName("BtnAwalKeperawatanGigi"); // NOI18N
        BtnAwalKeperawatanGigi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGigi.setRoundRect(false);
        BtnAwalKeperawatanGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGigiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanGigi);

        BtnAwalKeperawatanKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanKandungan.setText("Awal Keperawatan Kandungan");
        BtnAwalKeperawatanKandungan.setFocusPainted(false);
        BtnAwalKeperawatanKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanKandungan.setName("BtnAwalKeperawatanKandungan"); // NOI18N
        BtnAwalKeperawatanKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanKandungan.setRoundRect(false);
        BtnAwalKeperawatanKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanKandunganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanKandungan);

        BtnAwalKeperawatanAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanAnak.setText("Awal Keperawatan Bayi/Anak");
        BtnAwalKeperawatanAnak.setFocusPainted(false);
        BtnAwalKeperawatanAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanAnak.setName("BtnAwalKeperawatanAnak"); // NOI18N
        BtnAwalKeperawatanAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanAnak.setRoundRect(false);
        BtnAwalKeperawatanAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanAnak);

        BtnAwalKeperawatanPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanPsikiatri.setText("Awal Keperawatan Psikiatri");
        BtnAwalKeperawatanPsikiatri.setFocusPainted(false);
        BtnAwalKeperawatanPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanPsikiatri.setName("BtnAwalKeperawatanPsikiatri"); // NOI18N
        BtnAwalKeperawatanPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanPsikiatri.setRoundRect(false);
        BtnAwalKeperawatanPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanPsikiatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanPsikiatri);

        BtnAwalKeperawatanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanGeriatri.setText("Awal Keperawatan Geriatri");
        BtnAwalKeperawatanGeriatri.setFocusPainted(false);
        BtnAwalKeperawatanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGeriatri.setName("BtnAwalKeperawatanGeriatri"); // NOI18N
        BtnAwalKeperawatanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGeriatri.setRoundRect(false);
        BtnAwalKeperawatanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanGeriatri);

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); // NOI18N
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalFisioterapi);

        BtnAwalTerapiWicara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalTerapiWicara.setText("Terapi Wicara");
        BtnAwalTerapiWicara.setFocusPainted(false);
        BtnAwalTerapiWicara.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalTerapiWicara.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalTerapiWicara.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalTerapiWicara.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalTerapiWicara.setName("BtnAwalTerapiWicara"); // NOI18N
        BtnAwalTerapiWicara.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalTerapiWicara.setRoundRect(false);
        BtnAwalTerapiWicara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalTerapiWicaraActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalTerapiWicara);

        BtnAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisIGD.setText("Awal Medis IGD");
        BtnAwalMedisIGD.setFocusPainted(false);
        BtnAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisIGD.setName("BtnAwalMedisIGD"); // NOI18N
        BtnAwalMedisIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisIGD.setRoundRect(false);
        BtnAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisIGDActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisIGD);

        BtnAwalMedisIGDPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisIGDPsikiatri.setText("Awal Medis IGD Psikiatri");
        BtnAwalMedisIGDPsikiatri.setFocusPainted(false);
        BtnAwalMedisIGDPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisIGDPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisIGDPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisIGDPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisIGDPsikiatri.setName("BtnAwalMedisIGDPsikiatri"); // NOI18N
        BtnAwalMedisIGDPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisIGDPsikiatri.setRoundRect(false);
        BtnAwalMedisIGDPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisIGDPsikiatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisIGDPsikiatri);

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis.setText("Awal Medis Umum");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); // NOI18N
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedis);

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); // NOI18N
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisKandungan);

        BtnAwalMedisAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisAnak.setText("Awal Medis Bayi/Anak");
        BtnAwalMedisAnak.setFocusPainted(false);
        BtnAwalMedisAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisAnak.setName("BtnAwalMedisAnak"); // NOI18N
        BtnAwalMedisAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisAnak.setRoundRect(false);
        BtnAwalMedisAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisAnak);

        BtnAwalMedisTHT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisTHT.setText("Awal Medis THT");
        BtnAwalMedisTHT.setFocusPainted(false);
        BtnAwalMedisTHT.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisTHT.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisTHT.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisTHT.setName("BtnAwalMedisTHT"); // NOI18N
        BtnAwalMedisTHT.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisTHT.setRoundRect(false);
        BtnAwalMedisTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisTHTActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisTHT);

        BtnAwalMedisPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisPsikiatri.setText("Awal Medis Psikiatri");
        BtnAwalMedisPsikiatri.setFocusPainted(false);
        BtnAwalMedisPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPsikiatri.setName("BtnAwalMedisPsikiatri"); // NOI18N
        BtnAwalMedisPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPsikiatri.setRoundRect(false);
        BtnAwalMedisPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPsikiatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisPsikiatri);

        BtnAwalMedisPenyakitDalam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisPenyakitDalam.setText("Awal Medis Penyakit Dalam");
        BtnAwalMedisPenyakitDalam.setFocusPainted(false);
        BtnAwalMedisPenyakitDalam.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisPenyakitDalam.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPenyakitDalam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPenyakitDalam.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPenyakitDalam.setName("BtnAwalMedisPenyakitDalam"); // NOI18N
        BtnAwalMedisPenyakitDalam.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPenyakitDalam.setRoundRect(false);
        BtnAwalMedisPenyakitDalam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPenyakitDalamActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisPenyakitDalam);

        BtnAwalMedisMata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisMata.setText("Awal Medis Mata");
        BtnAwalMedisMata.setFocusPainted(false);
        BtnAwalMedisMata.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisMata.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisMata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisMata.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisMata.setName("BtnAwalMedisMata"); // NOI18N
        BtnAwalMedisMata.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisMata.setRoundRect(false);
        BtnAwalMedisMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisMataActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisMata);

        BtnAwalMedisNeurologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisNeurologi.setText("Awal Medis Neurologi");
        BtnAwalMedisNeurologi.setFocusPainted(false);
        BtnAwalMedisNeurologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisNeurologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisNeurologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisNeurologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisNeurologi.setName("BtnAwalMedisNeurologi"); // NOI18N
        BtnAwalMedisNeurologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisNeurologi.setRoundRect(false);
        BtnAwalMedisNeurologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisNeurologiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisNeurologi);

        BtnAwalMedisOrthopedi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisOrthopedi.setText("Awal Medis Orthopedi");
        BtnAwalMedisOrthopedi.setFocusPainted(false);
        BtnAwalMedisOrthopedi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisOrthopedi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisOrthopedi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisOrthopedi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisOrthopedi.setName("BtnAwalMedisOrthopedi"); // NOI18N
        BtnAwalMedisOrthopedi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisOrthopedi.setRoundRect(false);
        BtnAwalMedisOrthopedi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisOrthopediActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisOrthopedi);

        BtnAwalMedisBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisBedah.setText("Awal Medis Bedah");
        BtnAwalMedisBedah.setFocusPainted(false);
        BtnAwalMedisBedah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisBedah.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisBedah.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisBedah.setName("BtnAwalMedisBedah"); // NOI18N
        BtnAwalMedisBedah.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisBedah.setRoundRect(false);
        BtnAwalMedisBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisBedahActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisBedah);

        BtnAwalMedisBedahMulut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisBedahMulut.setText("Awal Medis Bedah Mulut");
        BtnAwalMedisBedahMulut.setFocusPainted(false);
        BtnAwalMedisBedahMulut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisBedahMulut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisBedahMulut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisBedahMulut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisBedahMulut.setName("BtnAwalMedisBedahMulut"); // NOI18N
        BtnAwalMedisBedahMulut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisBedahMulut.setRoundRect(false);
        BtnAwalMedisBedahMulut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisBedahMulutActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisBedahMulut);

        BtnAwalMedisGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisGeriatri.setText("Awal Medis Geriatri");
        BtnAwalMedisGeriatri.setFocusPainted(false);
        BtnAwalMedisGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisGeriatri.setName("BtnAwalMedisGeriatri"); // NOI18N
        BtnAwalMedisGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisGeriatri.setRoundRect(false);
        BtnAwalMedisGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisGeriatri);

        BtnAwalMedisKulitKelamin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKulitKelamin.setText("Awal Medis Kulit & Kelamin");
        BtnAwalMedisKulitKelamin.setFocusPainted(false);
        BtnAwalMedisKulitKelamin.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKulitKelamin.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKulitKelamin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKulitKelamin.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKulitKelamin.setName("BtnAwalMedisKulitKelamin"); // NOI18N
        BtnAwalMedisKulitKelamin.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKulitKelamin.setRoundRect(false);
        BtnAwalMedisKulitKelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKulitKelaminActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisKulitKelamin);

        BtnAwalMedisParu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisParu.setText("Awal Medis Paru");
        BtnAwalMedisParu.setFocusPainted(false);
        BtnAwalMedisParu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisParu.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisParu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisParu.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisParu.setName("BtnAwalMedisParu"); // NOI18N
        BtnAwalMedisParu.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisParu.setRoundRect(false);
        BtnAwalMedisParu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisParuActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisParu);

        BtnAwalMedisRehabMedik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisRehabMedik.setText("Awal Medis Fisik & Rehabilitasi");
        BtnAwalMedisRehabMedik.setFocusPainted(false);
        BtnAwalMedisRehabMedik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisRehabMedik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisRehabMedik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisRehabMedik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisRehabMedik.setName("BtnAwalMedisRehabMedik"); // NOI18N
        BtnAwalMedisRehabMedik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisRehabMedik.setRoundRect(false);
        BtnAwalMedisRehabMedik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisRehabMedikActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisRehabMedik);

        BtnLayananKedokteranFisik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnLayananKedokteranFisik.setText("Layanan Kedokteran Fisik ");
        BtnLayananKedokteranFisik.setFocusPainted(false);
        BtnLayananKedokteranFisik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnLayananKedokteranFisik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnLayananKedokteranFisik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnLayananKedokteranFisik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnLayananKedokteranFisik.setName("BtnLayananKedokteranFisik"); // NOI18N
        BtnLayananKedokteranFisik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnLayananKedokteranFisik.setRoundRect(false);
        BtnLayananKedokteranFisik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLayananKedokteranFisikActionPerformed(evt);
            }
        });
        FormMenu.add(BtnLayananKedokteranFisik);

        BtnAwalMedisHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisHemodialisa.setText("Awal Medis Hemodialisa");
        BtnAwalMedisHemodialisa.setFocusPainted(false);
        BtnAwalMedisHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisHemodialisa.setName("BtnAwalMedisHemodialisa"); // NOI18N
        BtnAwalMedisHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisHemodialisa.setRoundRect(false);
        BtnAwalMedisHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisHemodialisaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisHemodialisa);

        BtnRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukKeluar.setText("Rujuk Keluar");
        BtnRujukKeluar.setFocusPainted(false);
        BtnRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukKeluar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukKeluar.setName("BtnRujukKeluar"); // NOI18N
        BtnRujukKeluar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukKeluar.setRoundRect(false);
        BtnRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukKeluarActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRujukKeluar);

        BtnCatatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatan.setText("Catatan Pasien");
        BtnCatatan.setFocusPainted(false);
        BtnCatatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatan.setName("BtnCatatan"); // NOI18N
        BtnCatatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatan.setRoundRect(false);
        BtnCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatan);

        BtnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiIGD.setText("Observasi IGD");
        BtnCatatanObservasiIGD.setFocusPainted(false);
        BtnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiIGD.setName("BtnCatatanObservasiIGD"); // NOI18N
        BtnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiIGD.setRoundRect(false);
        BtnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiIGDActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanObservasiIGD);

        BtnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanCekGDS.setText("Catatan Cek GDS");
        BtnCatatanCekGDS.setFocusPainted(false);
        BtnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanCekGDS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCekGDS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCekGDS.setName("BtnCatatanCekGDS"); // NOI18N
        BtnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCekGDS.setRoundRect(false);
        BtnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanCekGDSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanCekGDS);

        BtnCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanKeperawatan.setText("Catatan Keperawatan");
        BtnCatatanKeperawatan.setFocusPainted(false);
        BtnCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanKeperawatan.setName("BtnCatatanKeperawatan"); // NOI18N
        BtnCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanKeperawatan.setRoundRect(false);
        BtnCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanKeperawatanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanKeperawatan);

        BtnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianUlangNyeri.setText("Penilaian Ulang Nyeri");
        BtnPenilaianUlangNyeri.setFocusPainted(false);
        BtnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianUlangNyeri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianUlangNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianUlangNyeri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianUlangNyeri.setName("BtnPenilaianUlangNyeri"); // NOI18N
        BtnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianUlangNyeri.setRoundRect(false);
        BtnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianUlangNyeriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianUlangNyeri);

        BtnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSAnak.setText("Pemantauan PEWS Anak");
        BtnPemantauanPEWSAnak.setFocusPainted(false);
        BtnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSAnak.setName("BtnPemantauanPEWSAnak"); // NOI18N
        BtnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSAnak.setRoundRect(false);
        BtnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanPEWSAnak);

        BtnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSDewasa.setText("Pemantauan EWS Dewasa");
        BtnPemantauanPEWSDewasa.setFocusPainted(false);
        BtnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSDewasa.setName("BtnPemantauanPEWSDewasa"); // NOI18N
        BtnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSDewasa.setRoundRect(false);
        BtnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanPEWSDewasa);

        BtnPemantauanMEOWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanMEOWS.setText("Pemantauan MEOWS Obstetri");
        BtnPemantauanMEOWS.setFocusPainted(false);
        BtnPemantauanMEOWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanMEOWS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanMEOWS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanMEOWS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanMEOWS.setName("BtnPemantauanMEOWS"); // NOI18N
        BtnPemantauanMEOWS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanMEOWS.setRoundRect(false);
        BtnPemantauanMEOWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanMEOWSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanMEOWS);

        BtnPemantauanEWSNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanEWSNeonatus.setText("Pemantauan EWS Neonatus");
        BtnPemantauanEWSNeonatus.setFocusPainted(false);
        BtnPemantauanEWSNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanEWSNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanEWSNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanEWSNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanEWSNeonatus.setName("BtnPemantauanEWSNeonatus"); // NOI18N
        BtnPemantauanEWSNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanEWSNeonatus.setRoundRect(false);
        BtnPemantauanEWSNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanEWSNeonatusActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanEWSNeonatus);

        BtnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        BtnMonitoringReaksiTranfusi.setFocusPainted(false);
        BtnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringReaksiTranfusi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringReaksiTranfusi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringReaksiTranfusi.setName("BtnMonitoringReaksiTranfusi"); // NOI18N
        BtnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringReaksiTranfusi.setRoundRect(false);
        BtnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMonitoringReaksiTranfusi);

        BtnUjiFungsiKFR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnUjiFungsiKFR.setText("Uji Fungsi/Prosedur KFR");
        BtnUjiFungsiKFR.setFocusPainted(false);
        BtnUjiFungsiKFR.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnUjiFungsiKFR.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnUjiFungsiKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnUjiFungsiKFR.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnUjiFungsiKFR.setName("BtnUjiFungsiKFR"); // NOI18N
        BtnUjiFungsiKFR.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnUjiFungsiKFR.setRoundRect(false);
        BtnUjiFungsiKFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUjiFungsiKFRActionPerformed(evt);
            }
        });
        FormMenu.add(BtnUjiFungsiKFR);

        BtnChecklistKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukHCU.setText("Check List Masuk HCU");
        BtnChecklistKriteriaMasukHCU.setFocusPainted(false);
        BtnChecklistKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukHCU.setName("BtnChecklistKriteriaMasukHCU"); // NOI18N
        BtnChecklistKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukHCU.setRoundRect(false);
        BtnChecklistKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukHCUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaMasukHCU);

        BtnChecklistKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukICU.setText("Check List Masuk ICU");
        BtnChecklistKriteriaMasukICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukICU.setName("BtnChecklistKriteriaMasukICU"); // NOI18N
        BtnChecklistKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukICU.setRoundRect(false);
        BtnChecklistKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukICUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaMasukICU);

        BtnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPreOperasi.setText("Check List Pre Operasi");
        BtnChecklistPreOperasi.setFocusPainted(false);
        BtnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPreOperasi.setName("BtnChecklistPreOperasi"); // NOI18N
        BtnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPreOperasi.setRoundRect(false);
        BtnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPreOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistPreOperasi);

        BtnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        BtnSignInSebelumAnestesi.setFocusPainted(false);
        BtnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignInSebelumAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignInSebelumAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignInSebelumAnestesi.setName("BtnSignInSebelumAnestesi"); // NOI18N
        BtnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignInSebelumAnestesi.setRoundRect(false);
        BtnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignInSebelumAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSignInSebelumAnestesi);

        BtnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        BtnTimeOutSebelumInsisi.setFocusPainted(false);
        BtnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTimeOutSebelumInsisi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTimeOutSebelumInsisi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTimeOutSebelumInsisi.setName("BtnTimeOutSebelumInsisi"); // NOI18N
        BtnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTimeOutSebelumInsisi.setRoundRect(false);
        BtnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTimeOutSebelumInsisi);

        BtnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        BtnSignOutSebelumMenutupLuka.setFocusPainted(false);
        BtnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignOutSebelumMenutupLuka.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignOutSebelumMenutupLuka.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignOutSebelumMenutupLuka.setName("BtnSignOutSebelumMenutupLuka"); // NOI18N
        BtnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignOutSebelumMenutupLuka.setRoundRect(false);
        BtnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSignOutSebelumMenutupLuka);

        BtnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPostOperasi.setText("Check List Post Operasi");
        BtnChecklistPostOperasi.setFocusPainted(false);
        BtnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPostOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPostOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPostOperasi.setName("BtnChecklistPostOperasi"); // NOI18N
        BtnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPostOperasi.setRoundRect(false);
        BtnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPostOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistPostOperasi);

        BtnPenilaianPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreOperasi.setText("Penilaian Pre Operasi");
        BtnPenilaianPreOperasi.setFocusPainted(false);
        BtnPenilaianPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreOperasi.setName("BtnPenilaianPreOperasi"); // NOI18N
        BtnPenilaianPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreOperasi.setRoundRect(false);
        BtnPenilaianPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPreOperasi);

        BtnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreAnestesi.setText("Penilaian Pre Anestesi");
        BtnPenilaianPreAnestesi.setFocusPainted(false);
        BtnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreAnestesi.setName("BtnPenilaianPreAnestesi"); // NOI18N
        BtnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreAnestesi.setRoundRect(false);
        BtnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPreAnestesi);

        BtnSkorAldrettePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorAldrettePascaAnestesi.setText("Skor Aldrette Pasca Anestesi");
        BtnSkorAldrettePascaAnestesi.setFocusPainted(false);
        BtnSkorAldrettePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorAldrettePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorAldrettePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorAldrettePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorAldrettePascaAnestesi.setName("BtnSkorAldrettePascaAnestesi"); // NOI18N
        BtnSkorAldrettePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorAldrettePascaAnestesi.setRoundRect(false);
        BtnSkorAldrettePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorAldrettePascaAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkorAldrettePascaAnestesi);

        BtnSkorStewardPascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorStewardPascaAnestesi.setText("Skor Steward Pasca Anestesi");
        BtnSkorStewardPascaAnestesi.setFocusPainted(false);
        BtnSkorStewardPascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorStewardPascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorStewardPascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorStewardPascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorStewardPascaAnestesi.setName("BtnSkorStewardPascaAnestesi"); // NOI18N
        BtnSkorStewardPascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorStewardPascaAnestesi.setRoundRect(false);
        BtnSkorStewardPascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorStewardPascaAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkorStewardPascaAnestesi);

        BtnMedicalCheckUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMedicalCheckUp.setText("Medical Check Up");
        BtnMedicalCheckUp.setFocusPainted(false);
        BtnMedicalCheckUp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMedicalCheckUp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMedicalCheckUp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMedicalCheckUp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMedicalCheckUp.setName("BtnMedicalCheckUp"); // NOI18N
        BtnMedicalCheckUp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMedicalCheckUp.setRoundRect(false);
        BtnMedicalCheckUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMedicalCheckUpActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMedicalCheckUp);

        BtnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPsikolog.setText("Penilaian Psikolog");
        BtnPenilaianPsikolog.setFocusPainted(false);
        BtnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPsikolog.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikolog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikolog.setName("BtnPenilaianPsikolog"); // NOI18N
        BtnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikolog.setRoundRect(false);
        BtnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPsikologActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPsikolog);

        BtnPenilaianLanjutanRisikoJatuhDewasa
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setText("Lanjutan Risiko Jatuh Dewasa");
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setName("BtnPenilaianLanjutanRisikoJatuhDewasa"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhDewasa);

        BtnPenilaianLanjutanRisikoJatuhAnak
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setText("Lanjutan Risiko Jatuh Anak");
        BtnPenilaianLanjutanRisikoJatuhAnak.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhAnak.setName("BtnPenilaianLanjutanRisikoJatuhAnak"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhAnak.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhAnak);

        BtnPenilaianLanjutanRisikoJatuhLansia
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setText("Lanjutan Risiko Jatuh Lansia");
        BtnPenilaianLanjutanRisikoJatuhLansia.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhLansia.setName("BtnPenilaianLanjutanRisikoJatuhLansia"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhLansia.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhLansia);

        BtnPenilaianLanjutanRisikoJatuhNeonatus
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setText("Lanjutan Risiko Jatuh Neonatus");
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setName("BtnPenilaianLanjutanRisikoJatuhNeonatus"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhNeonatus);

        BtnPenilaianLanjutanRisikoJatuhGeriatri
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setText("Lanjutan Risiko Jatuh Geriatri");
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setName("BtnPenilaianLanjutanRisikoJatuhGeriatri"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhGeriatri);

        BtnPenilaianLanjutanRisikoJatuhPsikiatri
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setText("Lanjutan Risiko Jatuh Psikiatri");
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setName("BtnPenilaianLanjutanRisikoJatuhPsikiatri"); // NOI18N
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhPsikiatri);

        BtnPenilaianLanjutanSkriningFungsional
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setText("Lanjutan Skrining Fungsional");
        BtnPenilaianLanjutanSkriningFungsional.setFocusPainted(false);
        BtnPenilaianLanjutanSkriningFungsional.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanSkriningFungsional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanSkriningFungsional.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanSkriningFungsional.setName("BtnPenilaianLanjutanSkriningFungsional"); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanSkriningFungsional.setRoundRect(false);
        BtnPenilaianLanjutanSkriningFungsional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanSkriningFungsionalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);

        BtnHasilPemeriksaanUSG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilPemeriksaanUSG.setText("Hasil USG Kandungan");
        BtnHasilPemeriksaanUSG.setFocusPainted(false);
        BtnHasilPemeriksaanUSG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG.setName("BtnHasilPemeriksaanUSG"); // NOI18N
        BtnHasilPemeriksaanUSG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG.setRoundRect(false);
        BtnHasilPemeriksaanUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSGActionPerformed(evt);
            }
        });
        FormMenu.add(BtnHasilPemeriksaanUSG);

        BtnDokumentasiESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDokumentasiESWL.setText("Dokumentasi Tindakan ESWL");
        BtnDokumentasiESWL.setFocusPainted(false);
        BtnDokumentasiESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDokumentasiESWL.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiESWL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiESWL.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiESWL.setName("BtnDokumentasiESWL"); // NOI18N
        BtnDokumentasiESWL.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDokumentasiESWL.setRoundRect(false);
        BtnDokumentasiESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiESWLActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDokumentasiESWL);

        BtnCatatanPersalinanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanPersalinanan.setText("Catatan Persalinan");
        BtnCatatanPersalinanan.setFocusPainted(false);
        BtnCatatanPersalinanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanPersalinanan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanPersalinanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanPersalinanan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanPersalinanan.setName("BtnCatatanPersalinanan"); // NOI18N
        BtnCatatanPersalinanan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanPersalinanan.setRoundRect(false);
        BtnCatatanPersalinanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanPersalinananActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanPersalinanan);

        BtnSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiDewasa.setText("Skrining Nutrisi Dewasa");
        BtnSkriningNutrisiDewasa.setFocusPainted(false);
        BtnSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiDewasa.setName("BtnSkriningNutrisiDewasa"); // NOI18N
        BtnSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiDewasa.setRoundRect(false);
        BtnSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiDewasa);

        BtnSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiLansia.setText("Skrining Nutrisi Lansia");
        BtnSkriningNutrisiLansia.setFocusPainted(false);
        BtnSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiLansia.setName("BtnSkriningNutrisiLansia"); // NOI18N
        BtnSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiLansia.setRoundRect(false);
        BtnSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiLansiaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiLansia);

        BtnSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiAnak.setText("Skrining Nutrisi Anak");
        BtnSkriningNutrisiAnak.setFocusPainted(false);
        BtnSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiAnak.setName("BtnSkriningNutrisiAnak"); // NOI18N
        BtnSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiAnak.setRoundRect(false);
        BtnSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiAnak);

        BtnSkriningGiziLanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        BtnSkriningGiziLanjut.setFocusPainted(false);
        BtnSkriningGiziLanjut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningGiziLanjut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziLanjut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziLanjut.setName("BtnSkriningGiziLanjut"); // NOI18N
        BtnSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziLanjut.setRoundRect(false);
        BtnSkriningGiziLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningGiziLanjutActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningGiziLanjut);

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); // NOI18N
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAsuhanGizi);

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); // NOI18N
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMonitoringAsuhanGizi);

        BtnCatatanADIMEGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanADIMEGizi.setText("Catatan ADIME Gizi");
        BtnCatatanADIMEGizi.setFocusPainted(false);
        BtnCatatanADIMEGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanADIMEGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanADIMEGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanADIMEGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanADIMEGizi.setName("BtnCatatanADIMEGizi"); // NOI18N
        BtnCatatanADIMEGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanADIMEGizi.setRoundRect(false);
        BtnCatatanADIMEGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanADIMEGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanADIMEGizi);

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); // NOI18N
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKonselingFarmasi);

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); // NOI18N
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInformasiObat);

        BtnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        BtnRekonsiliasiObat.setFocusPainted(false);
        BtnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRekonsiliasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRekonsiliasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRekonsiliasiObat.setName("BtnRekonsiliasiObat"); // NOI18N
        BtnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRekonsiliasiObat.setRoundRect(false);
        BtnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekonsiliasiObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRekonsiliasiObat);

        BtnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTransferAntarRuang.setText("Transfer Antar Ruang");
        BtnTransferAntarRuang.setFocusPainted(false);
        BtnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTransferAntarRuang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTransferAntarRuang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTransferAntarRuang.setName("BtnTransferAntarRuang"); // NOI18N
        BtnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTransferAntarRuang.setRoundRect(false);
        BtnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTransferAntarRuangActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTransferAntarRuang);

        BtnEdukasiPasienKeluarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnEdukasiPasienKeluarga.setText("Edukasi Pasien & Keluarga");
        BtnEdukasiPasienKeluarga.setFocusPainted(false);
        BtnEdukasiPasienKeluarga.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnEdukasiPasienKeluarga.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnEdukasiPasienKeluarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEdukasiPasienKeluarga.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnEdukasiPasienKeluarga.setName("BtnEdukasiPasienKeluarga"); // NOI18N
        BtnEdukasiPasienKeluarga.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnEdukasiPasienKeluarga.setRoundRect(false);
        BtnEdukasiPasienKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdukasiPasienKeluargaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnEdukasiPasienKeluarga);

        BtnPengkajianRestrain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPengkajianRestrain.setText("Pengkajian Restrain");
        BtnPengkajianRestrain.setFocusPainted(false);
        BtnPengkajianRestrain.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPengkajianRestrain.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPengkajianRestrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPengkajianRestrain.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPengkajianRestrain.setName("BtnPengkajianRestrain"); // NOI18N
        BtnPengkajianRestrain.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPengkajianRestrain.setRoundRect(false);
        BtnPengkajianRestrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPengkajianRestrainActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPengkajianRestrain);

        BtnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienTerminal.setText("Penilaian Pasien Terminal");
        BtnPenilaianPasienTerminal.setFocusPainted(false);
        BtnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienTerminal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienTerminal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienTerminal.setName("BtnPenilaianPasienTerminal"); // NOI18N
        BtnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienTerminal.setRoundRect(false);
        BtnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienTerminalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPasienTerminal);

        BtnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKorbanKekerasan.setText("Penilaian Korban Kekerasan");
        BtnPenilaianKorbanKekerasan.setFocusPainted(false);
        BtnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKorbanKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKorbanKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKorbanKekerasan.setName("BtnPenilaianKorbanKekerasan"); // NOI18N
        BtnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKorbanKekerasan.setRoundRect(false);
        BtnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianKorbanKekerasan);

        BtnPenilaianPasienPenyakitMenular
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setText("Pasien Penyakit Menular");
        BtnPenilaianPasienPenyakitMenular.setFocusPainted(false);
        BtnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienPenyakitMenular.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienPenyakitMenular.setName("BtnPenilaianPasienPenyakitMenular"); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienPenyakitMenular.setRoundRect(false);
        BtnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);

        BtnPenilaianPasienKeracunan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienKeracunan.setText("Pasien Keracunan");
        BtnPenilaianPasienKeracunan.setFocusPainted(false);
        BtnPenilaianPasienKeracunan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienKeracunan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienKeracunan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienKeracunan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienKeracunan.setName("BtnPenilaianPasienKeracunan"); // NOI18N
        BtnPenilaianPasienKeracunan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienKeracunan.setRoundRect(false);
        BtnPenilaianPasienKeracunan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienKeracunanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPasienKeracunan);

        BtnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanGeriatri.setText("Tambahan Pasien Geriatri");
        BtnPenilaianTambahanGeriatri.setFocusPainted(false);
        BtnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanGeriatri.setName("BtnPenilaianTambahanGeriatri"); // NOI18N
        BtnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanGeriatri.setRoundRect(false);
        BtnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanGeriatri);

        BtnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setText("Tambahan Bunuh Diri");
        BtnPenilaianTambahanBunuhDiri.setFocusPainted(false);
        BtnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanBunuhDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanBunuhDiri.setName("BtnPenilaianTambahanBunuhDiri"); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanBunuhDiri.setRoundRect(false);
        BtnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanBunuhDiri);

        BtnPenilaianTambahanPerilakuKekerasan
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setText("Tambahan Perilaku Kekerasan");
        BtnPenilaianTambahanPerilakuKekerasan.setFocusPainted(false);
        BtnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanPerilakuKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanPerilakuKekerasan.setName("BtnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanPerilakuKekerasan.setRoundRect(false);
        BtnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);

        BtnPenilaianTambahanMelarikanDiri
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setText("Tambahan Melarikan Diri");
        BtnPenilaianTambahanMelarikanDiri.setFocusPainted(false);
        BtnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanMelarikanDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanMelarikanDiri.setName("BtnPenilaianTambahanMelarikanDiri"); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanMelarikanDiri.setRoundRect(false);
        BtnPenilaianTambahanMelarikanDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanMelarikanDiriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
            isPsien();
            kd_pj = Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                    TNoRw.getText());
            kode_poli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",
                    TNoRw.getText());
        } else {
            if (TabRawat.getSelectedIndex() == 0) {
                Valid.pindah(evt, DTPTgl, KdDok);
            } else if (TabRawat.getSelectedIndex() == 1) {
                Valid.pindah(evt, DTPTgl, kdptg);
            } else if (TabRawat.getSelectedIndex() == 2) {
                Valid.pindah(evt, DTPTgl, KdDok2);
            } else if (TabRawat.getSelectedIndex() == 3) {
                Valid.pindah(evt, DTPTgl, KdPeg);
            } else if (TabRawat.getSelectedIndex() == 4) {
            } else if (TabRawat.getSelectedIndex() == 5) {
                Valid.pindah(evt, DTPTgl, TInspeksi);
            } else if (TabRawat.getSelectedIndex() == 8) {
                Valid.pindah(evt, DTPTgl, KdDok3);
            }
        }
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if (KdDok.getText().trim().equals("") || TDokter.getText().trim().equals("")) {
                        Valid.textKosong(KdDok, "Dokter");
                    } else {
                        try {
                            jmlparsial = 0;
                            if (aktifkanparsial.equals("yes")) {
                                jmlparsial = Sequel.cariInteger(
                                        "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                                        Sequel.cariIsi(
                                                "select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                                TNoRw.getText()));
                            }
                            if (jmlparsial > 0) {
                                SimpanPenangananDokter();
                            } else {
                                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                } else {
                                    SimpanPenangananDokter();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;

                case 1:
                    if (kdptg.getText().trim().equals("") || TPerawat.getText().trim().equals("")) {
                        Valid.textKosong(kdptg, "Petugas");
                    } else {
                        try {
                            jmlparsial = 0;
                            if (aktifkanparsial.equals("yes")) {
                                jmlparsial = Sequel.cariInteger(
                                        "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                                        Sequel.cariIsi(
                                                "select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                                TNoRw.getText()));
                            }
                            if (jmlparsial > 0) {
                                SimpanPenangananPetugas();
                            } else {
                                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                } else {
                                    SimpanPenangananPetugas();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;

                case 2:
                    if (KdDok2.getText().trim().equals("") || TDokter2.getText().trim().equals("")) {
                        Valid.textKosong(KdDok2, "Dokter");
                    } else if (kdptg2.getText().trim().equals("") || TPerawat2.getText().trim().equals("")) {
                        Valid.textKosong(kdptg2, "Petugas");
                    } else {
                        try {
                            jmlparsial = 0;
                            if (aktifkanparsial.equals("yes")) {
                                jmlparsial = Sequel.cariInteger(
                                        "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                                        Sequel.cariIsi(
                                                "select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                                TNoRw.getText()));
                            }
                            if (jmlparsial > 0) {
                                SimpanPenangananDokterPetugas();
                            } else {
                                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                } else {
                                    SimpanPenangananDokterPetugas();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;

                case 3:
                    if ((!TKeluhan.getText().trim().equals("")) || (!TPemeriksaan.getText().trim().equals(""))
                            || (!TSuhu.getText().trim().equals("")) || (!TTensi.getText().trim().equals(""))
                            || (!TAlergi.getText().trim().equals("")) || (!TTinggi.getText().trim().equals(""))
                            || (!TBerat.getText().trim().equals("")) || (!TRespirasi.getText().trim().equals(""))
                            || (!TNadi.getText().trim().equals("")) || (!TGCS.getText().trim().equals(""))
                            || (!TindakLanjut.getText().trim().equals("")) || (!TPenilaian.getText().trim().equals(""))
                            || (!TInstruksi.getText().trim().equals("")) || (!SpO2.getText().trim().equals(""))
                            || (!TEvaluasi.getText().trim().equals(""))) {

                        if (KdPeg.getText().trim().equals("") || TPegawai.getText().trim().equals("")) {
                            Valid.textKosong(KdPeg, "Dokter/Paramedis masih kosong...!!");
                        } else {

                            // int isDokter = Sequel.cariInteger("select count(kd_dokter) from dokter where
                            // kd_dokter=?", KdPeg.getText());
                            //
                            // if (isDokter > 0 && fileTTD.equals("")) {
                            // JOptionPane.showMessageDialog(null,
                            // "Maaf, Dokter WAJIB melakukan Tanda Tangan Digital (TTD) sebelum menyimpan
                            // SOAP!\n" +
                            // "Identitas anda terdeteksi sebagai Dokter.\n" +
                            // "Silahkan klik tombol TTD di samping kolom Alergi.");
                            // BtnTTD.requestFocus();
                            // break;
                            // }

                            if (akses.getkode().equals("Admin Utama")) {
                                if (Sequel.menyimpantf("pemeriksaan_ralan", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                                        "Data", 21, new String[] {
                                                TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                        + cmbDtk.getSelectedItem(),
                                                TSuhu.getText(), TTensi.getText(), TNadi.getText(),
                                                TRespirasi.getText(), TTinggi.getText(), TBerat.getText(),
                                                SpO2.getText(), TGCS.getText(),
                                                cmbKesadaran.getSelectedItem().toString(), TKeluhan.getText(),
                                                TPemeriksaan.getText(), TAlergi.getText(),
                                                LingkarPerut.getText(), TindakLanjut.getText(), TPenilaian.getText(),
                                                TInstruksi.getText(), TEvaluasi.getText(), KdPeg.getText() }) == true) {

                                    if (!fileTTD.equals("")) {
                                        Sequel.menyimpan("ttd_dokter_ralan",
                                                "'" + TNoRw.getText() + "','"
                                                        + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','" +
                                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                        + cmbDtk.getSelectedItem() + "','" +
                                                        KdPeg.getText() + "','" + fileTTD + "'",
                                                "Tanda Tangan Dokter");
                                    }

                                    tabModePemeriksaan.addRow(new Object[] {
                                            false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                                            Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                            cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                    + cmbDtk.getSelectedItem(),
                                            TSuhu.getText(), TTensi.getText(), TNadi.getText(), TRespirasi.getText(),
                                            TTinggi.getText(), TBerat.getText(), SpO2.getText(), TGCS.getText(),
                                            cmbKesadaran.getSelectedItem().toString(),
                                            TKeluhan.getText(), TPemeriksaan.getText(), TAlergi.getText(),
                                            LingkarPerut.getText(), TindakLanjut.getText(), TPenilaian.getText(),
                                            TInstruksi.getText(), TEvaluasi.getText(),
                                            KdPeg.getText(), TPegawai.getText(), Jabatan.getText()
                                    });
                                    SimpanTemplateSOAPIE();
                                    SimpanTemplateSOAPIEPerawat();
                                    emptTeks();

                                    fileTTD = "";
                                    BtnTTD.setText("TTD");
                                    BtnTTD.setIcon(
                                            new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
                                    LCount.setText("" + tabModePemeriksaan.getRowCount());
                                }
                            } else {
                                if (akses.getkode().equals(KdPeg.getText())) {
                                    if (Sequel.menyimpantf("pemeriksaan_ralan",
                                            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 21, new String[] {
                                                    TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                            + cmbDtk.getSelectedItem(),
                                                    TSuhu.getText(), TTensi.getText(), TNadi.getText(),
                                                    TRespirasi.getText(), TTinggi.getText(), TBerat.getText(),
                                                    SpO2.getText(), TGCS.getText(),
                                                    cmbKesadaran.getSelectedItem().toString(), TKeluhan.getText(),
                                                    TPemeriksaan.getText(), TAlergi.getText(),
                                                    LingkarPerut.getText(), TindakLanjut.getText(),
                                                    TPenilaian.getText(), TInstruksi.getText(), TEvaluasi.getText(),
                                                    KdPeg.getText() }) == true) {

                                        if (!fileTTD.equals("")) {
                                            Sequel.menyimpan("ttd_dokter_ralan",
                                                    "'" + TNoRw.getText() + "','"
                                                            + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','" +
                                                            cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem()
                                                            + ":" + cmbDtk.getSelectedItem() + "','" +
                                                            KdPeg.getText() + "','" + fileTTD + "'",
                                                    "Tanda Tangan Dokter");
                                        }

                                        tabModePemeriksaan.addRow(new Object[] {
                                                false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                        + cmbDtk.getSelectedItem(),
                                                TSuhu.getText(), TTensi.getText(), TNadi.getText(),
                                                TRespirasi.getText(), TTinggi.getText(), TBerat.getText(),
                                                SpO2.getText(), TGCS.getText(),
                                                cmbKesadaran.getSelectedItem().toString(),
                                                TKeluhan.getText(), TPemeriksaan.getText(), TAlergi.getText(),
                                                LingkarPerut.getText(), TindakLanjut.getText(), TPenilaian.getText(),
                                                TInstruksi.getText(), TEvaluasi.getText(),
                                                KdPeg.getText(), TPegawai.getText(), Jabatan.getText()
                                        });
                                        SimpanTemplateSOAPIE();
                                        SimpanTemplateSOAPIEPerawat();
                                        emptTeks();

                                        fileTTD = "";
                                        BtnTTD.setText("TTD");
                                        BtnTTD.setIcon(new javax.swing.ImageIcon(
                                                getClass().getResource("/picture/b_print.png")));
                                        LCount.setText("" + tabModePemeriksaan.getRowCount());
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    break;

                case 4:
                    if ((!TTinggi_uteri.getText().trim().equals("")) || (!TLetak.getText().trim().equals(""))
                            || (!TDenyut.getText().trim().equals("")) || (!TKualitas_mnt.getText().trim().equals(""))
                            || (!TKualitas_dtk.getText().trim().equals("")) || (!TVulva.getText().trim().equals(""))
                            || (!TPortio.getText().trim().equals("")) || (!TTebal.getText().trim().equals(""))
                            || (!TPembukaan.getText().trim().equals("")) || (!TPenurunan.getText().trim().equals(""))
                            || (!TDenominator.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("pemeriksaan_obstetri_ralan",
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 23, new String[] {
                                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                + cmbDtk.getSelectedItem(),
                                        TTinggi_uteri.getText(), cmbJanin.getSelectedItem().toString(),
                                        TLetak.getText(), cmbPanggul.getSelectedItem().toString(), TDenyut.getText(),
                                        cmbKontraksi.getSelectedItem().toString(), TKualitas_mnt.getText(),
                                        TKualitas_dtk.getText(), cmbFluksus.getSelectedItem().toString(),
                                        cmbAlbus.getSelectedItem().toString(), TVulva.getText(), TPortio.getText(),
                                        cmbDalam.getSelectedItem().toString(), TTebal.getText(),
                                        cmbArah.getSelectedItem().toString(), TPembukaan.getText(),
                                        TPenurunan.getText(), TDenominator.getText(),
                                        cmbKetuban.getSelectedItem().toString(),
                                        cmbFeto.getSelectedItem().toString() }) == true) {
                            tabModeObstetri.addRow(new Object[] {
                                    false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    TTinggi_uteri.getText(), cmbJanin.getSelectedItem().toString(), TLetak.getText(),
                                    cmbPanggul.getSelectedItem().toString(), TDenyut.getText(),
                                    cmbKontraksi.getSelectedItem().toString(),
                                    TKualitas_mnt.getText(), TKualitas_dtk.getText(),
                                    cmbFluksus.getSelectedItem().toString(), cmbAlbus.getSelectedItem().toString(),
                                    TVulva.getText(), TPortio.getText(),
                                    cmbDalam.getSelectedItem().toString(), TTebal.getText(),
                                    cmbArah.getSelectedItem().toString(), TPembukaan.getText(), TPenurunan.getText(),
                                    TDenominator.getText(),
                                    cmbKetuban.getSelectedItem().toString(), cmbFeto.getSelectedItem().toString()
                            });
                            TTinggi_uteri.setText("");
                            cmbJanin.setSelectedIndex(0);
                            TLetak.setText("");
                            cmbPanggul.setSelectedIndex(0);
                            TDenyut.setText("");
                            cmbKontraksi.setSelectedIndex(0);
                            TKualitas_mnt.setText("");
                            TKualitas_dtk.setText("");
                            cmbFluksus.setSelectedIndex(0);
                            cmbAlbus.setSelectedIndex(0);
                            TVulva.setText("");
                            TPortio.setText("");
                            cmbDalam.setSelectedIndex(0);
                            TTebal.setText("");
                            cmbArah.setSelectedIndex(0);
                            TPembukaan.setText("");
                            TPenurunan.setText("");
                            TDenominator.setText("");
                            cmbKetuban.setSelectedIndex(0);
                            cmbFeto.getSelectedItem().toString();
                            LCount.setText("" + tabModeObstetri.getRowCount());
                        }
                    }
                    break;

                case 5:
                    if ((!TInspeksi.getText().trim().equals("")) || (!TInspeksiVulva.getText().trim().equals(""))
                            || (!TInspekuloGine.getText().trim().equals("")) || (!TUkuran.getText().trim().equals(""))
                            || (!TPortioInspekulo.getText().trim().equals(""))
                            || (!TSondage.getText().trim().equals(""))
                            || (!TPortioDalam.getText().trim().equals("")) || (!TBentuk.getText().trim().equals(""))
                            || (!TCavumUteri.getText().trim().equals("")) || (!TUkuran.getText().trim().equals(""))
                            || (!TAdnexaKanan.getText().trim().equals("")) || (!TAdnexaKiri.getText().trim().equals(""))
                            || (!TCavumDouglas.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("pemeriksaan_ginekologi_ralan",
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 20, new String[] {
                                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                + cmbDtk.getSelectedItem(),
                                        TInspeksi.getText(), TInspeksiVulva.getText(), TInspekuloGine.getText(),
                                        cmbFluxusGine.getSelectedItem().toString(),
                                        cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                                        TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                                        TBentuk.getText(), TCavumUteri.getText(),
                                        cmbMobilitas.getSelectedItem().toString(),
                                        TUkuran.getText(), cmbNyeriTekan.getSelectedItem().toString(),
                                        TAdnexaKanan.getText(), TAdnexaKiri.getText(),
                                        TCavumDouglas.getText() }) == true) {
                            tabModeGinekologi.addRow(new Object[] {
                                    false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    TInspeksi.getText(), TInspeksiVulva.getText(), TInspekuloGine.getText(),
                                    cmbFluxusGine.getSelectedItem().toString(),
                                    cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                                    TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                                    TBentuk.getText(), TCavumUteri.getText(), cmbMobilitas.getSelectedItem().toString(),
                                    TUkuran.getText(),
                                    cmbNyeriTekan.getSelectedItem().toString(), TAdnexaKanan.getText(),
                                    TAdnexaKiri.getText(), TCavumDouglas.getText()
                            });
                            TInspeksi.setText("");
                            TInspeksiVulva.setText("");
                            TInspekuloGine.setText("");
                            cmbFluxusGine.setSelectedIndex(0);
                            cmbFluorGine.setSelectedIndex(0);
                            TVulvaInspekulo.setText("");
                            TPortioInspekulo.setText("");
                            TSondage.setText("");
                            TPortioDalam.setText("");
                            TBentuk.setText("");
                            TCavumUteri.setText("");
                            cmbMobilitas.setSelectedIndex(0);
                            TUkuran.setText("");
                            cmbNyeriTekan.setSelectedIndex(0);
                            TAdnexaKanan.setText("");
                            TAdnexaKiri.setText("");
                            TCavumDouglas.getText();
                            LCount.setText("" + tabModeGinekologi.getRowCount());
                        }
                    }
                    break;

                case 6:
                    if (akses.getdiagnosa_pasien() == true) {
                        panelDiagnosa1.setRM(TNoRw.getText(), TNoRM.getText(),
                                Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                                Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", TCari.getText().trim());
                        panelDiagnosa1.simpan();
                    }
                    break;

                case 7:
                    if ((!KdDok3.getText().trim().equals("")) && (!TDokter3.getText().trim().equals(""))
                            && (!Catatan.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("catatan_perawatan", "?,?,?,?,?", "Data", 5, new String[] {
                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem(),
                                TNoRw.getText(), KdDok3.getText(), Catatan.getText()
                        }) == true) {
                            TabModeCatatan.addRow(new Object[] {
                                    false, TNoRw.getText(), TNoRM.getText(), TPasien.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    KdDok3.getText(), TDokter3.getText(), Catatan.getText()
                            });
                            Catatan.setText("");
                            LCount.setText("" + TabModeCatatan.getRowCount());
                        }
                    }
                    break;

                case 8:
                    if ((!TSituation.getText().trim().equals("")) || (!TBackground.getText().trim().equals(""))
                            || (!TAssesment.getText().trim().equals(""))
                            || (!TRecommendation.getText().trim().equals(""))) {
                        if (KdPeg2.getText().trim().equals("") || TPegawai2.getText().trim().equals("")) {
                            Valid.textKosong(KdPeg2, "Dokter/Paramedis masih kosong...!!");
                        } else {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.menyimpan("pemeriksaan_ralan_sbar", "?,?,?,?,?,?,?,?", "Data", 8, new String[] {
                                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                + cmbDtk.getSelectedItem(),
                                        TSituation.getText(), TBackground.getText(), TAssesment.getText(),
                                        TRecommendation.getText(), KdPeg2.getText()
                                });
                                tampilPemeriksaanSbar();
                                BtnBatalActionPerformed(evt);
                            } else {
                                if (akses.getkode().equals(KdPeg2.getText())) {
                                    Sequel.menyimpan("pemeriksaan_ralan_sbar", "?,?,?,?,?,?,?,?", "Data", 8,
                                            new String[] {
                                                    TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                            + cmbDtk.getSelectedItem(),
                                                    TSituation.getText(), TBackground.getText(), TAssesment.getText(),
                                                    TRecommendation.getText(), KdPeg2.getText()
                                            });
                                    tampilPemeriksaanSbar();
                                    BtnBatalActionPerformed(evt);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    break;

                case 9:
                    if ((!TSituation1.getText().trim().equals("")) || (!TBackground1.getText().trim().equals(""))
                            || (!TAssesment1.getText().trim().equals(""))
                            || (!TRecommendation1.getText().trim().equals(""))) {
                        if (KdPeg4.getText().trim().equals("") || TPegawai4.getText().trim().equals("")) {
                            Valid.textKosong(KdPeg4, "Dokter/Paramedis masih kosong...!!");
                        } else {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.menyimpan("pemeriksaan_ralan_tbak", "?,?,?,?,?,?,?,?", "Data", 8, new String[] {
                                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                + cmbDtk.getSelectedItem(),
                                        TSituation1.getText(), TBackground1.getText(), TAssesment1.getText(),
                                        TRecommendation1.getText(), KdPeg4.getText()
                                });
                                tampilPemeriksaanTbak();
                                BtnBatalActionPerformed(evt);
                            } else {
                                if (akses.getkode().equals(KdPeg4.getText())) {
                                    Sequel.menyimpan("pemeriksaan_ralan_tbak", "?,?,?,?,?,?,?,?", "Data", 8,
                                            new String[] {
                                                    TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                            + cmbDtk.getSelectedItem(),
                                                    TSituation1.getText(), TBackground1.getText(),
                                                    TAssesment1.getText(), TRecommendation1.getText(), KdPeg4.getText()
                                            });
                                    tampilPemeriksaanTbak();
                                    BtnBatalActionPerformed(evt);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    break;

                case 10:
                    if ((!kdptg3.getText().trim().equals("")) && (!TPerawat3.getText().trim().equals(""))
                            && (!Catatan1.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("catatan_keperawatan_ralan", "?,?,?,?,?", "Data", 5, new String[] {
                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem(),
                                TNoRw.getText(), Catatan1.getText(), kdptg3.getText()
                        }) == true) {
                            Catatan1.setText("");
                            tampilCatatanPerawatIGD();
                        }
                    }
                    break;

                case 11:
                    if (akses.getkode().equals("Admin Utama")) {
                        JOptionPane.showMessageDialog(null, "Anda login sebagai admin utama, harap login sebagai user");
                    } else if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")
                            || TPasien.getText().trim().equals("")) {
                        Valid.textKosong(TCari, "Pasien");
                    } else if (AlergiCode.getText().trim().equals("")) {
                        Valid.textKosong(AlergiCode, "Kode Vaksin");
                    } else if (ReaksiCode.getText().trim().equals("")) {
                        Valid.textKosong(ReaksiCode, "Reaksi");
                    } else if (TKeterangan.getText().trim().equals("")) {
                        Valid.textKosong(TKeterangan, "Note / Keterangan");
                    } else {
                        if (Sequel.menyimpantf("alergi_pasien", "?,?,?,?,?,?,?,?", "No.Rawat", 8, new String[] {
                                TNoRw.getText(), Sequel.cariIsi("select CURRENT_TIMESTAMP()"), AlergiCode.getText(),
                                cmbKategory.getSelectedItem().toString(), KdPeg1.getText(), TKeterangan.getText(),
                                ReaksiCode.getText(), cmbSeverity.getSelectedItem().toString()
                        }) == true) {
                            tampil();
                            emptTeks();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            if (TabRawat.getSelectedIndex() == 0) {
                Valid.pindah(evt, BtnSeekDokter, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 1) {
                Valid.pindah(evt, BtnSeekPetugas, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 2) {
                Valid.pindah(evt, BtnSeekPetugas2, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 3) {
                Valid.pindah(evt, TEvaluasi, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 4) {
                Valid.pindah(evt, cmbFeto, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 5) {
                Valid.pindah(evt, TCavumDouglas, BtnBatal);
            } else if (TabRawat.getSelectedIndex() == 8) {
                Valid.pindah(evt, Catatan, BtnBatal);
            }
        }
    }

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        ChkInput.setSelected(true);
        ChkInput1.setSelected(true);
        ChkInput2.setSelected(true);
        ChkInput3.setSelected(true);
        isForm();
        isForm2();
        isForm3();
        isForm4();
        TSuhu.setText("");
        TTensi.setText("");
        TKeluhan.setText("");
        TInstruksi.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TAlergi.setText("");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
        TRespirasi.setText("");
        TGCS.setText("");
        TindakLanjut.setText("");
        TTinggi_uteri.setText("");
        TLetak.setText("");
        TDenyut.setText("");
        TVulva.setText("");
        TPortio.setText("");
        TTebal.setText("");
        TPembukaan.setText("");
        TPenurunan.setText("");
        TDenominator.setText("");
        TKualitas_mnt.setText("");
        TKualitas_dtk.setText("");
        TInspeksi.setText("");
        TInspeksiVulva.setText("");
        TInspekuloGine.setText("");
        TVulvaInspekulo.setText("");
        SpO2.setText("");
        TEvaluasi.setText("");
        TPortioInspekulo.setText("");
        TSondage.setText("");
        TPortioDalam.setText("");
        TBentuk.setText("");
        TCavumUteri.setText("");
        TUkuran.setText("");
        TAdnexaKanan.setText("");
        TAdnexaKiri.setText("");
        TCavumDouglas.setText("");
        Catatan.setText("");
        cmbKesadaran.setSelectedIndex(0);
        TSituation.setText("");
        TBackground.setText("");
        TAssesment.setText("");
        TRecommendation.setText("");
        TSituation1.setText("");
        TBackground1.setText("");
        TAssesment1.setText("");
        TRecommendation1.setText("");
        Catatan1.setText("");
        autoFillPetugas();

    }

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if (tabModeDr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    Sequel.AutoComitFalse();
                    sukses = true;
                    ttljmdokter = 0;
                    ttljmperawat = 0;
                    ttlkso = 0;
                    ttlpendapatan = 0;
                    ttljasasarana = 0;
                    ttlbhp = 0;
                    ttlmenejemen = 0;
                    for (i = 0; i < tbRawatDr.getRowCount(); i++) {
                        if (tbRawatDr.getValueAt(i, 0).toString().equals("true")) {
                            if (Sequel.cariRegistrasi(tbRawatDr.getValueAt(i, 1).toString()) > 0) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatDr.setValueAt(false, i, 0);
                                TCari.requestFocus();
                            } else {
                                if (Sequel.queryutf("delete from rawat_jl_dr where no_rawat='"
                                        + tbRawatDr.getValueAt(i, 1).toString()
                                        + "' and kd_jenis_prw='" + tbRawatDr.getValueAt(i, 10)
                                        + "' and kd_dokter='" + tbRawatDr.getValueAt(i, 5).toString()
                                        + "' and tgl_perawatan='" + tbRawatDr.getValueAt(i, 7).toString()
                                        + "' and jam_rawat='" + tbRawatDr.getValueAt(i, 8).toString() + "'") == true) {
                                    ttljmdokter = ttljmdokter
                                            + Double.parseDouble(tbRawatDr.getValueAt(i, 11).toString());
                                    ttlkso = ttlkso + Double.parseDouble(tbRawatDr.getValueAt(i, 12).toString());
                                    ttlpendapatan = ttlpendapatan
                                            + Double.parseDouble(tbRawatDr.getValueAt(i, 9).toString());
                                    ttljasasarana = ttljasasarana
                                            + Double.parseDouble(tbRawatDr.getValueAt(i, 13).toString());
                                    ttlbhp = ttlbhp + Double.parseDouble(tbRawatDr.getValueAt(i, 14).toString());
                                    ttlmenejemen = ttlmenejemen
                                            + Double.parseDouble(tbRawatDr.getValueAt(i, 15).toString());
                                } else {
                                    sukses = false;
                                }
                            }
                        }
                    }

                    if (sukses == true) {
                        Sequel.queryu("delete from tampjurnal");
                        if (ttlpendapatan > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','0','"
                                            + ttlpendapatan + "'",
                                    "kredit=kredit+'" + (ttlpendapatan) + "'",
                                    "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Jalan','" + ttlpendapatan
                                            + "','0'",
                                    "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                        }
                        if (ttljmdokter > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan
                                            + "','Beban Jasa Medik Dokter Tindakan Ralan','0','" + ttljmdokter + "'",
                                    "kredit=kredit+'" + (ttljmdokter) + "'",
                                    "kd_rek='" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan
                                            + "','Utang Jasa Medik Dokter Tindakan Ralan','" + ttljmdokter + "','0'",
                                    "debet=debet+'" + (ttljmdokter) + "'",
                                    "kd_rek='" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                        }
                        if (ttlkso > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','0','" + ttlkso
                                            + "'",
                                    "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','" + ttlkso
                                            + "','0'",
                                    "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                        }
                        if (ttlmenejemen > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Beban Jasa Menejemen Tindakan Ralan','0','" + ttlmenejemen + "'",
                                    "kredit=kredit+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Utang Jasa Menejemen Tindakan Ralan','" + ttlmenejemen + "','0'",
                                    "debet=debet+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                        }
                        if (ttljasasarana > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Sarana_Tindakan_Ralan
                                            + "','Beban Jasa Sarana Tindakan Ralan','0','" + ttljasasarana + "'",
                                    "kredit=kredit+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','"
                                            + ttljasasarana + "','0'",
                                    "debet=debet+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                        }
                        if (ttlbhp > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','0','" + ttlbhp + "'",
                                    "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','" + ttlbhp
                                            + "','0'",
                                    "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                        }
                        sukses = jur.simpanJurnal(TNoRw.getText(), "U", "PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "
                                + TNoRM.getText() + " " + TPasien.getText() + " OLEH " + akses.getkode());
                    }

                    if (sukses == true) {
                        Sequel.Commit();
                        for (i = 0; i < tbRawatDr.getRowCount(); i++) {
                            if (tbRawatDr.getValueAt(i, 0).toString().equals("true")) {
                                tabModeDr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText("" + tabModeDr.getRowCount());
                    } else {
                        sukses = false;
                        JOptionPane.showMessageDialog(null,
                                "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }
                break;
            case 1:
                if (tabModePr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    Sequel.AutoComitFalse();
                    sukses = true;
                    ttljmdokter = 0;
                    ttljmperawat = 0;
                    ttlkso = 0;
                    ttlpendapatan = 0;
                    ttljasasarana = 0;
                    ttlbhp = 0;
                    ttlmenejemen = 0;
                    for (i = 0; i < tbRawatPr.getRowCount(); i++) {
                        if (tbRawatPr.getValueAt(i, 0).toString().equals("true")) {
                            if (Sequel.cariRegistrasi(tbRawatPr.getValueAt(i, 1).toString()) > 0) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatPr.setValueAt(false, i, 0);
                                TCari.requestFocus();
                            } else {
                                if (Sequel.queryutf("delete from rawat_jl_pr where no_rawat='"
                                        + tbRawatPr.getValueAt(i, 1).toString()
                                        + "' and kd_jenis_prw='" + tbRawatPr.getValueAt(i, 10)
                                        + "' and nip='" + tbRawatPr.getValueAt(i, 5).toString()
                                        + "' and tgl_perawatan='" + tbRawatPr.getValueAt(i, 7).toString()
                                        + "' and jam_rawat='" + tbRawatPr.getValueAt(i, 8).toString() + "' ") == true) {
                                    ttljmperawat = ttljmperawat
                                            + Double.parseDouble(tbRawatPr.getValueAt(i, 11).toString());
                                    ttlkso = ttlkso + Double.parseDouble(tbRawatPr.getValueAt(i, 12).toString());
                                    ttlpendapatan = ttlpendapatan
                                            + Double.parseDouble(tbRawatPr.getValueAt(i, 9).toString());
                                    ttljasasarana = ttljasasarana
                                            + Double.parseDouble(tbRawatPr.getValueAt(i, 13).toString());
                                    ttlbhp = ttlbhp + Double.parseDouble(tbRawatPr.getValueAt(i, 14).toString());
                                    ttlmenejemen = ttlmenejemen
                                            + Double.parseDouble(tbRawatPr.getValueAt(i, 15).toString());
                                } else {
                                    sukses = false;
                                }
                            }
                        }
                    }

                    if (sukses == true) {
                        Sequel.queryu("delete from tampjurnal");
                        if (ttlpendapatan > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','0','"
                                            + ttlpendapatan + "'",
                                    "kredit=kredit+'" + (ttlpendapatan) + "'",
                                    "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Jalan','" + ttlpendapatan
                                            + "','0'",
                                    "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                        }
                        if (ttljmperawat > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Beban Jasa Medik Paramedis Tindakan Ralan','0','" + ttljmperawat + "'",
                                    "kredit=kredit+'" + (ttljmperawat) + "'",
                                    "kd_rek='" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal", "'" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Utang Jasa Medik Paramedis Tindakan Ralan','" + ttljmperawat + "','0'",
                                    "debet=debet+'" + (ttljmperawat) + "'",
                                    "kd_rek='" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                        }
                        if (ttlkso > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','0','" + ttlkso
                                            + "'",
                                    "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','" + ttlkso
                                            + "','0'",
                                    "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                        }
                        if (ttlmenejemen > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Beban Jasa Menejemen Tindakan Ralan','0','" + ttlmenejemen + "'",
                                    "kredit=kredit+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Utang Jasa Menejemen Tindakan Ralan','" + ttlmenejemen + "','0'",
                                    "debet=debet+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                        }
                        if (ttljasasarana > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Sarana_Tindakan_Ralan
                                            + "','Beban Jasa Sarana Tindakan Ralan','0','" + ttljasasarana + "'",
                                    "kredit=kredit+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','"
                                            + ttljasasarana + "','0'",
                                    "debet=debet+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                        }
                        if (ttlbhp > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','0','" + ttlbhp + "'",
                                    "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','" + ttlbhp
                                            + "','0'",
                                    "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                        }
                        sukses = jur.simpanJurnal(TNoRw.getText(), "U", "PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "
                                + TNoRM.getText() + " " + TPasien.getText() + " OLEH " + akses.getkode());
                    }

                    if (sukses == true) {
                        Sequel.Commit();
                        for (i = 0; i < tbRawatPr.getRowCount(); i++) {
                            if (tbRawatPr.getValueAt(i, 0).toString().equals("true")) {
                                tabModePr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText("" + tabModePr.getRowCount());
                    } else {
                        sukses = false;
                        JOptionPane.showMessageDialog(null,
                                "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }
                break;
            case 2:
                if (tabModeDrPr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    Sequel.AutoComitFalse();
                    sukses = true;
                    ttljmdokter = 0;
                    ttljmperawat = 0;
                    ttlkso = 0;
                    ttlpendapatan = 0;
                    ttljasasarana = 0;
                    ttlbhp = 0;
                    ttlmenejemen = 0;
                    for (i = 0; i < tbRawatDrPr.getRowCount(); i++) {
                        if (tbRawatDrPr.getValueAt(i, 0).toString().equals("true")) {
                            if (Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i, 1).toString()) > 0) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                tbRawatDrPr.setValueAt(false, i, 0);
                                TCari.requestFocus();
                            } else {
                                if (Sequel.queryutf("delete from rawat_jl_drpr where no_rawat='"
                                        + tbRawatDrPr.getValueAt(i, 1).toString()
                                        + "' and kd_jenis_prw='" + tbRawatDrPr.getValueAt(i, 12)
                                        + "' and kd_dokter='" + tbRawatDrPr.getValueAt(i, 5).toString()
                                        + "' and nip='" + tbRawatDrPr.getValueAt(i, 7).toString()
                                        + "' and tgl_perawatan='" + tbRawatDrPr.getValueAt(i, 9).toString()
                                        + "' and jam_rawat='" + tbRawatDrPr.getValueAt(i, 10).toString()
                                        + "' ") == true) {
                                    ttljmdokter = ttljmdokter
                                            + Double.parseDouble(tbRawatDrPr.getValueAt(i, 13).toString());
                                    ttljmperawat = ttljmperawat
                                            + Double.parseDouble(tbRawatDrPr.getValueAt(i, 14).toString());
                                    ttlkso = ttlkso + Double.parseDouble(tbRawatDrPr.getValueAt(i, 15).toString());
                                    ttlpendapatan = ttlpendapatan
                                            + Double.parseDouble(tbRawatDrPr.getValueAt(i, 11).toString());
                                    ttljasasarana = ttljasasarana
                                            + Double.parseDouble(tbRawatDrPr.getValueAt(i, 16).toString());
                                    ttlbhp = ttlbhp + Double.parseDouble(tbRawatDrPr.getValueAt(i, 17).toString());
                                    ttlmenejemen = ttlmenejemen
                                            + Double.parseDouble(tbRawatDrPr.getValueAt(i, 18).toString());
                                } else {
                                    sukses = false;
                                }
                            }
                        }
                    }

                    if (sukses == true) {
                        Sequel.queryu("delete from tampjurnal");
                        if (ttlpendapatan > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','0','"
                                            + ttlpendapatan + "'",
                                    "kredit=kredit+'" + (ttlpendapatan) + "'",
                                    "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Jalan','" + ttlpendapatan
                                            + "','0'",
                                    "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                        }
                        if (ttljmdokter > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan
                                            + "','Beban Jasa Medik Dokter Tindakan Ralan','0','" + ttljmdokter + "'",
                                    "kredit=kredit+'" + (ttljmdokter) + "'",
                                    "kd_rek='" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan
                                            + "','Utang Jasa Medik Dokter Tindakan Ralan','" + ttljmdokter + "','0'",
                                    "debet=debet+'" + (ttljmdokter) + "'",
                                    "kd_rek='" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                        }
                        if (ttljmperawat > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Beban Jasa Medik Paramedis Tindakan Ralan','0','" + ttljmperawat + "'",
                                    "kredit=kredit+'" + (ttljmperawat) + "'",
                                    "kd_rek='" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal", "'" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Utang Jasa Medik Paramedis Tindakan Ralan','" + ttljmperawat + "','0'",
                                    "debet=debet+'" + (ttljmperawat) + "'",
                                    "kd_rek='" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                        }
                        if (ttlkso > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','0','" + ttlkso
                                            + "'",
                                    "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','" + ttlkso
                                            + "','0'",
                                    "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                        }
                        if (ttlmenejemen > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Beban Jasa Menejemen Tindakan Ralan','0','" + ttlmenejemen + "'",
                                    "kredit=kredit+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Menejemen_Tindakan_Ralan
                                            + "','Utang Jasa Menejemen Tindakan Ralan','" + ttlmenejemen + "','0'",
                                    "debet=debet+'" + (ttlmenejemen) + "'",
                                    "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                        }
                        if (ttljasasarana > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Beban_Jasa_Sarana_Tindakan_Ralan
                                            + "','Beban Jasa Sarana Tindakan Ralan','0','" + ttljasasarana + "'",
                                    "kredit=kredit+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','"
                                            + ttljasasarana + "','0'",
                                    "debet=debet+'" + (ttljasasarana) + "'",
                                    "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                        }
                        if (ttlbhp > 0) {
                            Sequel.menyimpan("tampjurnal",
                                    "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','0','" + ttlbhp + "'",
                                    "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                            Sequel.menyimpan("tampjurnal",
                                    "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','" + ttlbhp
                                            + "','0'",
                                    "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                        }
                        sukses = jur.simpanJurnal(TNoRw.getText(), "U", "PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "
                                + TNoRM.getText() + " " + TPasien.getText() + " OLEH " + akses.getkode());
                    }

                    if (sukses == true) {
                        Sequel.Commit();
                        for (i = 0; i < tbRawatDrPr.getRowCount(); i++) {
                            if (tbRawatDrPr.getValueAt(i, 0).toString().equals("true")) {
                                tabModeDrPr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText("" + tabModeDrPr.getRowCount());
                    } else {
                        sukses = false;
                        JOptionPane.showMessageDialog(null,
                                "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }
                break;
            case 3:
                if (tabModePemeriksaan.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                        if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"
                                        + tbPemeriksaan.getValueAt(i, 1).toString()
                                        + "' and tgl_perawatan='" + tbPemeriksaan.getValueAt(i, 4).toString()
                                        + "' and jam_rawat='" + tbPemeriksaan.getValueAt(i, 5).toString() + "' ");
                                tabModePemeriksaan.removeRow(i);
                                i--;
                            } else {
                                if (akses.getkode().equals(tbPemeriksaan.getValueAt(i, 23).toString())) {
                                    Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"
                                            + tbPemeriksaan.getValueAt(i, 1).toString()
                                            + "' and tgl_perawatan='" + tbPemeriksaan.getValueAt(i, 4).toString()
                                            + "' and jam_rawat='" + tbPemeriksaan.getValueAt(i, 5).toString() + "' ");
                                    tabModePemeriksaan.removeRow(i);
                                    i--;
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    LCount.setText("" + tabModePemeriksaan.getRowCount());
                }
                break;
            case 4:
                if (tabModeObstetri.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbPemeriksaanObstetri.getRowCount(); i++) {
                        if (tbPemeriksaanObstetri.getValueAt(i, 0).toString().equals("true")) {
                            Sequel.queryu("delete from pemeriksaan_obstetri_ralan where no_rawat='"
                                    + tbPemeriksaanObstetri.getValueAt(i, 1).toString()
                                    + "' and tgl_perawatan='" + tbPemeriksaanObstetri.getValueAt(i, 4).toString()
                                    + "' and jam_rawat='" + tbPemeriksaanObstetri.getValueAt(i, 5).toString() + "' ");
                            tabModeObstetri.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText("" + tabModeObstetri.getRowCount());
                }
                break;
            case 5:
                if (tabModeGinekologi.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbPemeriksaanGinekologi.getRowCount(); i++) {
                        if (tbPemeriksaanGinekologi.getValueAt(i, 0).toString().equals("true")) {
                            Sequel.queryu("delete from pemeriksaan_ginekologi_ralan where no_rawat='"
                                    + tbPemeriksaanGinekologi.getValueAt(i, 1).toString()
                                    + "' and tgl_perawatan='" + tbPemeriksaanGinekologi.getValueAt(i, 4).toString()
                                    + "' and jam_rawat='" + tbPemeriksaanGinekologi.getValueAt(i, 5).toString() + "' ");
                            tabModeGinekologi.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText("" + tabModeGinekologi.getRowCount());
                }
                break;
            case 6:
                panelDiagnosa1.setRM(TNoRw.getText(), TNoRM.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                        Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", TCari.getText().trim());
                panelDiagnosa1.hapus();
                LCount.setText(panelDiagnosa1.getRecord() + "");
                break;
            case 7:
                if (TabModeCatatan.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbCatatan.getRowCount(); i++) {
                        if (tbCatatan.getValueAt(i, 0).toString().equals("true")) {
                            Sequel.queryu("delete from catatan_perawatan where no_rawat='"
                                    + tbCatatan.getValueAt(i, 1).toString()
                                    + "' and tanggal='" + tbCatatan.getValueAt(i, 4).toString()
                                    + "' and jam='" + tbCatatan.getValueAt(i, 5).toString()
                                    + "' and kd_dokter='" + tbCatatan.getValueAt(i, 6).toString() + "' ");
                            TabModeCatatan.removeRow(i);
                            i--;
                        }
                    }
                    LCount.setText("" + TabModeCatatan.getRowCount());
                }
                break;
            case 8:
                if (tabModePemeriksaanSbar.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbPemeriksaanSbar.getRowCount(); i++) {
                        if (tbPemeriksaanSbar.getValueAt(i, 0).toString().equals("true")) {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.queryu("delete from pemeriksaan_ralan_sbar where no_rawat='"
                                        + tbPemeriksaanSbar.getValueAt(i, 1).toString()
                                        + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(i, 4).toString()
                                        + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(i, 5).toString() + "' ");
                            } else {
                                if (akses.getkode().equals(tbPemeriksaanSbar.getValueAt(i, 10).toString())) {
                                    Sequel.queryu("delete from pemeriksaan_ralan_sbar where no_rawat='"
                                            + tbPemeriksaanSbar.getValueAt(i, 1).toString()
                                            + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(i, 4).toString()
                                            + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(i, 5).toString()
                                            + "' ");
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    tampilPemeriksaanSbar();
                }
                break;

            case 9:
                if (tabModePemeriksaanTbak.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else {
                    for (i = 0; i < tbPemeriksaanTbak.getRowCount(); i++) {
                        if (tbPemeriksaanTbak.getValueAt(i, 0).toString().equals("true")) {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.queryu("delete from pemeriksaan_ralan_tbak where no_rawat='"
                                        + tbPemeriksaanTbak.getValueAt(i, 1).toString()
                                        + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(i, 4).toString()
                                        + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(i, 5).toString() + "' ");
                            } else {
                                if (akses.getkode().equals(tbPemeriksaanTbak.getValueAt(i, 10).toString())) {
                                    Sequel.queryu("delete from pemeriksaan_ralan_tbak where no_rawat='"
                                            + tbPemeriksaanTbak.getValueAt(i, 1).toString()
                                            + "' and tgl_perawatan='" + tbPemeriksaanTbak.getValueAt(i, 4).toString()
                                            + "' and jam_rawat='" + tbPemeriksaanTbak.getValueAt(i, 5).toString()
                                            + "' ");
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }
                    }
                    tampilPemeriksaanTbak();
                }
                break;

            case 10:

                if (tbCatatanPerawatIGD.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        hapusCatatanPerawatIGD();
                    } else {
                        if (kdptg3.getText().equals(
                                tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 9).toString())) {
                            hapusCatatanPerawatIGD();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");

                }
                break;
            case 11:
                if (tbObat.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        if (Sequel.queryu2tf("delete from alergi_pasien where no_rawat=?", 1, new String[] {
                                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                        }) == true) {
                            tampil();
                            emptTeks();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Gagal menghapus..!!");
                        }
                    } else {
                        if (akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString())) {
                            if (Sequel.queryu2tf("delete from alergi_pasien where no_rawat=?", 1, new String[] {
                                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                            }) == true) {
                                tampil();
                                emptTeks();
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Gagal menghapus..!!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");

                }
                break;
            default:
                break;
        }

        BtnBatalActionPerformed(evt);
    }

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if (tabModeDr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeDr.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " rawat_jl_dr.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "")
                            + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanDr.jasper", "report", "::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,dokter.nm_dokter,"
                                    + "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat "
                                    + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                                    + "dokter inner join rawat_jl_dr "
                                    + "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "
                                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    + "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                                    + "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "
                                    + "where " + tgl + " and rawat_jl_dr.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and jns_perawatan.nm_perawatan like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and rawat_jl_dr.kd_dokter like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and tgl_perawatan like '%" + TCari.getText().trim() + "%' "
                                    + " order by rawat_jl_dr.no_rawat desc",
                            param);

                }
                break;
            case 1:
                if (tabModePr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModePr.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " rawat_jl_pr.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "")
                            + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanPr.jasper", "report", "::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                            "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "jns_perawatan.nm_perawatan,rawat_jl_pr.nip,petugas.nama,"
                                    + "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat "
                                    + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                                    + "petugas inner join rawat_jl_pr "
                                    + "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "
                                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    + "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                                    + "and rawat_jl_pr.nip=petugas.nip where  "
                                    + tgl + "and rawat_jl_pr.no_rawat like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and jns_perawatan.nm_perawatan like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and rawat_jl_pr.nip like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and petugas.nama like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and rawat_jl_pr.tgl_perawatan like '%" + TCari.getText().trim() + "%'  "
                                    + "order by rawat_jl_pr.no_rawat desc",
                            param);
                }
                break;
            case 2:
                if (tabModeDrPr.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeDrPr.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " rawat_jl_drpr.tgl_perawatan between '"
                            + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '"
                            + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanDrPr.jasper", "report", "::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "jns_perawatan.nm_perawatan,rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,"
                                    + "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat "
                                    + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                                    + "dokter inner join rawat_jl_drpr inner join "
                                    + "petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "
                                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    + "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                                    + "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "
                                    + "and rawat_jl_drpr.nip=petugas.nip "
                                    + "where " + tgl + " and rawat_jl_drpr.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and jns_perawatan.nm_perawatan like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and rawat_jl_drpr.kd_dokter like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and rawat_jl_drpr.nip like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and petugas.nama like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and tgl_perawatan like '%" + TCari.getText().trim() + "%' "
                                    + " order by rawat_jl_drpr.no_rawat desc",
                            param);
                }
                break;
            case 3:
                if (tabModePemeriksaan.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModePemeriksaan.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " pemeriksaan_ralan.tgl_perawatan between '"
                            + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '"
                            + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanPemeriksaan.jasper", "report", "::[ Data Pemeriksaan Rawat Jalan ]::",
                            "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, "
                                    + "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, "
                                    + "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "
                                    + "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,"
                                    + "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama "
                                    + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    + "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "
                                    + "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "
                                    + tgl + "and (pemeriksaan_ralan.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + "pasien.nm_pasien like '%" + TCari.getText().trim()
                                    + "%' or pemeriksaan_ralan.alergi like '%" + TCari.getText().trim() + "%' or "
                                    + "pemeriksaan_ralan.keluhan like '%" + TCari.getText().trim()
                                    + "%' or pemeriksaan_ralan.penilaian like '%" + TCari.getText().trim() + "%' or "
                                    + "pemeriksaan_ralan.pemeriksaan like '%" + TCari.getText().trim()
                                    + "%' or pegawai.nama like '%" + TCari.getText().trim() + "%') "
                                    + "order by pemeriksaan_ralan.no_rawat desc",
                            param);
                }
                break;
            case 4:
                if (tabModeObstetri.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeObstetri.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " pemeriksaan_obstetri_ralan.tgl_perawatan between '"
                            + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '"
                            + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanObstetri.jasper", "report",
                            "::[ Data Pemeriksaan Obstetri Rawat Jalan ]::",
                            "select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, "
                                    + "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, "
                                    + "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, "
                                    + "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan,"
                                    + "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto "
                                    + "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "
                                    + "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                                    + tgl + "and pemeriksaan_obstetri_ralan.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or  "
                                    + tgl + "and pemeriksaan_obstetri_ralan.tinggi_uteri like '%"
                                    + TCari.getText().trim() + "%' or "
                                    + tgl + "and pemeriksaan_obstetri_ralan.janin like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and pemeriksaan_obstetri_ralan.letak like '%" + TCari.getText().trim()
                                    + "%' "
                                    + "order by pemeriksaan_obstetri_ralan.no_rawat desc",
                            param);
                }
                break;
            case 5:
                if (tabModeGinekologi.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (tabModeGinekologi.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " pemeriksaan_ginekologi_ralan.tgl_perawatan between '"
                            + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '"
                            + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptJalanGinekologi.jasper", "report",
                            "::[ Data Pemeriksaan Ginekologi Rawat Jalan ]::",
                            "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, "
                                    + "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, "
                                    + "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, "
                                    + "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri,"
                                    + "pemeriksaan_ginekologi_ralan.cavum_douglas "
                                    + "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "
                                    + "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                                    + tgl + "and pemeriksaan_ginekologi_ralan.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or  "
                                    + tgl + "and pemeriksaan_ginekologi_ralan.inspeksi like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + "and pemeriksaan_ginekologi_ralan.inspeksi_vulva like '%"
                                    + TCari.getText().trim() + "%' or "
                                    + tgl + "and pemeriksaan_ginekologi_ralan.inspekulo_gine like '%"
                                    + TCari.getText().trim() + "%' "
                                    + "order by pemeriksaan_ginekologi_ralan.no_rawat desc",
                            param);
                }
                break;
            case 6:
                if (akses.getdiagnosa_pasien() == true) {
                    panelDiagnosa1.cetak();
                }
                break;
            case 7:
                if (TabModeCatatan.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                } else if (TabModeCatatan.getRowCount() != 0) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                    String pas = " and reg_periksa.no_rkm_medis like '%" + TCariPasien.getText() + "%' ";

                    String tgl = " catatan_perawatan.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "")
                            + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
                    Valid.MyReportqry("rptCatatanDokter.jasper", "report", "::[ Data Catatan Dokter ]::",
                            "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                                    + "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"
                                    + "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "
                                    + "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    + "and catatan_perawatan.kd_dokter=dokter.kd_dokter where  "
                                    + tgl + " and catatan_perawatan.no_rawat like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + " and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + " and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or  "
                                    + tgl + " and catatan_perawatan.catatan like '%" + TCari.getText().trim() + "%' or "
                                    + tgl + " and catatan_perawatan.kd_dokter like '%" + TCari.getText().trim()
                                    + "%' or "
                                    + tgl + " and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' "
                                    + "order by catatan_perawatan.no_rawat desc",
                            param);
                }
                break;
            default:
                break;
        }

        this.setCursor(Cursor.getDefaultCursor());
    }

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        petugas.dispose();
        dokter.dispose();
        pasien.dispose();
        try {
            i = JOptionPane.showConfirmDialog(null, "Mau skalian update status pasien sudah diperiksa ????",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                if (Sequel.mengedittf("reg_periksa", "no_rawat=?", "stts=?", 2,
                        new String[] { "Sudah", TNoRw.getText() }) == true) {
                    // Sequel.menyimpan("mutasi_berkas", "'" + TNoRw.getText() + "','Sudah
                    // Kembali',now(),'0000-00-00 00:00:00',now(),'0000-00-00 00:00:00','0000-00-00
                    // 00:00:00'", "status='Sudah Kembali',kembali=now()", "no_rawat='" +
                    // TNoRw.getText() + "'");
                }
            } else {

            }
        } catch (Exception e) {
        }
        dispose();
    }

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        TCariPasien.setText("");
        TampilkanData();
    }

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TampilkanData();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if (TabRawatTindakanDokter.getSelectedIndex() == 0) {
                        tbTindakan.requestFocus();
                    } else if (TabRawatTindakanDokter.getSelectedIndex() == 1) {
                        tbRawatDr.requestFocus();
                    }
                    break;
                case 1:
                    if (TabRawatTindakanPetugas.getSelectedIndex() == 0) {
                        tbTindakan2.requestFocus();
                    } else if (TabRawatTindakanPetugas.getSelectedIndex() == 1) {
                        tbRawatPr.requestFocus();
                    }
                    break;
                case 2:
                    if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 0) {
                        tbTindakan3.requestFocus();
                    } else if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 1) {
                        tbRawatDrPr.requestFocus();
                    }
                    break;
                case 3:
                    tbPemeriksaan.requestFocus();
                    break;
                case 4:
                    tbPemeriksaanObstetri.requestFocus();
                    break;
                case 5:
                    tbPemeriksaanGinekologi.requestFocus();
                    break;
                default:
                    break;
            }
        }
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        TampilkanData();
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true);
                TCari.setPreferredSize(new Dimension(207, 23));
                TabRawatTindakanDokterMouseClicked(null);
                break;
            case 1:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true);
                TCari.setPreferredSize(new Dimension(207, 23));
                TabRawatTindakanPetugasMouseClicked(null);
                break;
            case 2:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true);
                TCari.setPreferredSize(new Dimension(207, 23));
                TabRawatTindakanDokterPetugasMouseClicked(null);
                break;
            case 3:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240, 23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaan();
                tampilSoapPerawat();
                break;
            case 4:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240, 23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaanObstetri();
                break;
            case 5:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240, 23));
                TCariPasien.setText(TNoRM.getText());
                tampilPemeriksaanGinekologi();
                break;
            case 6:
                BtnSimpan.setEnabled(akses.getdiagnosa_pasien());
                BtnHapus.setEnabled(akses.getdiagnosa_pasien());
                BtnEdit.setEnabled(akses.getdiagnosa_pasien());
                BtnPrint.setEnabled(akses.getdiagnosa_pasien());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240, 23));
                TCariPasien.setText(TNoRM.getText());
                if (akses.getdiagnosa_pasien() == true) {
                    panelDiagnosa1.setRM(TNoRw.getText(), TNoRM.getText(),
                            Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                            Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord() + "");
                }
                break;
            case 7:
                BtnSimpan.setEnabled(akses.getcatatan_perawatan());
                BtnHapus.setEnabled(akses.getcatatan_perawatan());
                BtnEdit.setEnabled(akses.getcatatan_perawatan());
                BtnPrint.setEnabled(akses.getcatatan_perawatan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240, 23));
                TCariPasien.setText(TNoRM.getText());
                if (akses.getcatatan_perawatan() == true) {
                    tampilCatatan();
                }
                break;
            case 11:
                isCekCatatanAlergi();
            default:
                break;
        }
    }

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeDr.getRowCount() != 0) {
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModePr.getRowCount() != 0) {
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }

    private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TDokter.setText(dokter.tampil3(KdDok.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, BtnSeekDokter);
        }
    }

    private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TPerawat.setText(petugas.tampil3(kdptg.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPetugasActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, BtnSeekPetugas);
        }
    }

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            switch (TabRawat.getSelectedIndex()) {
                case 3:
                    if (KdPeg.getText().trim().equals("") || TPegawai.getText().trim().equals("")) {
                        Valid.textKosong(KdPeg, "Dokter/Paramedis masih kosong...!!");
                    } else if ((!TKeluhan.getText().trim().equals("")) || (!TPemeriksaan.getText().trim().equals(""))
                            || (!TSuhu.getText().trim().equals(""))
                            || (!TTensi.getText().trim().equals("")) || (!TAlergi.getText().trim().equals(""))
                            || (!TTinggi.getText().trim().equals(""))
                            || (!TBerat.getText().trim().equals("")) || (!TRespirasi.getText().trim().equals(""))
                            || (!TNadi.getText().trim().equals(""))
                            || (!TGCS.getText().trim().equals("")) || (!TindakLanjut.getText().trim().equals(""))
                            || (!TPenilaian.getText().trim().equals(""))
                            || (!KdPeg.getText().trim().equals("")) || (!TPegawai.getText().trim().equals(""))
                            || (!TInstruksi.getText().trim().equals(""))
                            || (!SpO2.getText().trim().equals("")) || (!TEvaluasi.getText().trim().equals(""))) {
                        if (tbPemeriksaan.getSelectedRow() > -1) {
                            if (akses.getkode().equals("Admin Utama")) {
                                if (Sequel.mengedittf("pemeriksaan_ralan",
                                        "no_rawat='" + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 1)
                                                + "' and tgl_perawatan='"
                                                + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 4)
                                                + "' and jam_rawat='"
                                                + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5) + "'",
                                        "no_rawat='" + TNoRw.getText() + "',suhu_tubuh='" + TSuhu.getText()
                                                + "',tensi='" + TTensi.getText() + "',"
                                                + "keluhan='" + TKeluhan.getText() + "',pemeriksaan='"
                                                + TPemeriksaan.getText() + "',"
                                                + "nadi='" + TNadi.getText() + "',respirasi='" + TRespirasi.getText()
                                                + "',"
                                                + "tinggi='" + TTinggi.getText() + "',berat='" + TBerat.getText()
                                                + "',spo2='" + SpO2.getText() + "',"
                                                + "gcs='" + TGCS.getText() + "',kesadaran='"
                                                + cmbKesadaran.getSelectedItem() + "',"
                                                + "alergi='" + TAlergi.getText() + "',lingkar_perut='"
                                                + LingkarPerut.getText() + "',"
                                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                                                + "rtl='" + TindakLanjut.getText() + "',penilaian='"
                                                + TPenilaian.getText() + "',"
                                                + "instruksi='" + TInstruksi.getText() + "',evaluasi='"
                                                + TEvaluasi.getText() + "',nip='" + KdPeg.getText() + "'") == true) {
                                    tbPemeriksaan.setValueAt(TNoRw.getText(), tbPemeriksaan.getSelectedRow(), 1);
                                    tbPemeriksaan.setValueAt(TNoRM.getText(), tbPemeriksaan.getSelectedRow(), 2);
                                    tbPemeriksaan.setValueAt(TPasien.getText(), tbPemeriksaan.getSelectedRow(), 3);
                                    tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                            tbPemeriksaan.getSelectedRow(), 4);
                                    tbPemeriksaan.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem()
                                            + ":" + cmbDtk.getSelectedItem(), tbPemeriksaan.getSelectedRow(), 5);
                                    tbPemeriksaan.setValueAt(TSuhu.getText(), tbPemeriksaan.getSelectedRow(), 6);
                                    tbPemeriksaan.setValueAt(TTensi.getText(), tbPemeriksaan.getSelectedRow(), 7);
                                    tbPemeriksaan.setValueAt(TNadi.getText(), tbPemeriksaan.getSelectedRow(), 8);
                                    tbPemeriksaan.setValueAt(TRespirasi.getText(), tbPemeriksaan.getSelectedRow(), 9);
                                    tbPemeriksaan.setValueAt(TTinggi.getText(), tbPemeriksaan.getSelectedRow(), 10);
                                    tbPemeriksaan.setValueAt(TBerat.getText(), tbPemeriksaan.getSelectedRow(), 11);
                                    tbPemeriksaan.setValueAt(SpO2.getText(), tbPemeriksaan.getSelectedRow(), 12);
                                    tbPemeriksaan.setValueAt(TGCS.getText(), tbPemeriksaan.getSelectedRow(), 13);
                                    tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),
                                            tbPemeriksaan.getSelectedRow(), 14);
                                    tbPemeriksaan.setValueAt(TKeluhan.getText(), tbPemeriksaan.getSelectedRow(), 15);
                                    tbPemeriksaan.setValueAt(TPemeriksaan.getText(), tbPemeriksaan.getSelectedRow(),
                                            16);
                                    tbPemeriksaan.setValueAt(TAlergi.getText(), tbPemeriksaan.getSelectedRow(), 17);
                                    tbPemeriksaan.setValueAt(LingkarPerut.getText(), tbPemeriksaan.getSelectedRow(),
                                            18);
                                    tbPemeriksaan.setValueAt(TindakLanjut.getText(), tbPemeriksaan.getSelectedRow(),
                                            19);
                                    tbPemeriksaan.setValueAt(TPenilaian.getText(), tbPemeriksaan.getSelectedRow(), 20);
                                    tbPemeriksaan.setValueAt(TInstruksi.getText(), tbPemeriksaan.getSelectedRow(), 21);
                                    tbPemeriksaan.setValueAt(TEvaluasi.getText(), tbPemeriksaan.getSelectedRow(), 22);
                                    tbPemeriksaan.setValueAt(KdPeg.getText(), tbPemeriksaan.getSelectedRow(), 23);
                                    tbPemeriksaan.setValueAt(TPegawai.getText(), tbPemeriksaan.getSelectedRow(), 24);
                                    tbPemeriksaan.setValueAt(Jabatan.getText(), tbPemeriksaan.getSelectedRow(), 25);
                                    TSuhu.setText("");
                                    TTensi.setText("");
                                    TNadi.setText("");
                                    TRespirasi.setText("");
                                    TTinggi.setText("");
                                    TBerat.setText("");
                                    TGCS.setText("");
                                    TKeluhan.setText("");
                                    TPemeriksaan.setText("");
                                    TAlergi.setText("");
                                    LingkarPerut.setText("");
                                    TindakLanjut.setText("");
                                    TPenilaian.setText("");
                                    TInstruksi.setText("");
                                    SpO2.setText("");
                                    TEvaluasi.setText("");
                                }
                            } else {
                                if (akses.getkode().equals(
                                        tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 23).toString())) {
                                    if (Sequel.mengedittf("pemeriksaan_ralan",
                                            "no_rawat='" + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 1)
                                                    + "' and tgl_perawatan='"
                                                    + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 4)
                                                    + "' and jam_rawat='"
                                                    + tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5) + "'",
                                            "no_rawat='" + TNoRw.getText() + "',suhu_tubuh='" + TSuhu.getText()
                                                    + "',tensi='" + TTensi.getText() + "',"
                                                    + "keluhan='" + TKeluhan.getText() + "',pemeriksaan='"
                                                    + TPemeriksaan.getText() + "',"
                                                    + "nadi='" + TNadi.getText() + "',respirasi='"
                                                    + TRespirasi.getText() + "',"
                                                    + "tinggi='" + TTinggi.getText() + "',berat='" + TBerat.getText()
                                                    + "',spo2='" + SpO2.getText() + "',"
                                                    + "gcs='" + TGCS.getText() + "',kesadaran='"
                                                    + cmbKesadaran.getSelectedItem() + "',"
                                                    + "alergi='" + TAlergi.getText() + "',lingkar_perut='"
                                                    + LingkarPerut.getText() + "',"
                                                    + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                                                    + "',"
                                                    + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                    + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                                                    + "rtl='" + TindakLanjut.getText() + "',penilaian='"
                                                    + TPenilaian.getText() + "',"
                                                    + "instruksi='" + TInstruksi.getText() + "',evaluasi='"
                                                    + TEvaluasi.getText() + "'") == true) {
                                        tbPemeriksaan.setValueAt(TNoRw.getText(), tbPemeriksaan.getSelectedRow(), 1);
                                        tbPemeriksaan.setValueAt(TNoRM.getText(), tbPemeriksaan.getSelectedRow(), 2);
                                        tbPemeriksaan.setValueAt(TPasien.getText(), tbPemeriksaan.getSelectedRow(), 3);
                                        tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                tbPemeriksaan.getSelectedRow(), 4);
                                        tbPemeriksaan
                                                .setValueAt(
                                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                                + cmbDtk.getSelectedItem(),
                                                        tbPemeriksaan.getSelectedRow(), 5);
                                        tbPemeriksaan.setValueAt(TSuhu.getText(), tbPemeriksaan.getSelectedRow(), 6);
                                        tbPemeriksaan.setValueAt(TTensi.getText(), tbPemeriksaan.getSelectedRow(), 7);
                                        tbPemeriksaan.setValueAt(TNadi.getText(), tbPemeriksaan.getSelectedRow(), 8);
                                        tbPemeriksaan.setValueAt(TRespirasi.getText(), tbPemeriksaan.getSelectedRow(),
                                                9);
                                        tbPemeriksaan.setValueAt(TTinggi.getText(), tbPemeriksaan.getSelectedRow(), 10);
                                        tbPemeriksaan.setValueAt(TBerat.getText(), tbPemeriksaan.getSelectedRow(), 11);
                                        tbPemeriksaan.setValueAt(SpO2.getText(), tbPemeriksaan.getSelectedRow(), 12);
                                        tbPemeriksaan.setValueAt(TGCS.getText(), tbPemeriksaan.getSelectedRow(), 13);
                                        tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),
                                                tbPemeriksaan.getSelectedRow(), 14);
                                        tbPemeriksaan.setValueAt(TKeluhan.getText(), tbPemeriksaan.getSelectedRow(),
                                                15);
                                        tbPemeriksaan.setValueAt(TPemeriksaan.getText(), tbPemeriksaan.getSelectedRow(),
                                                16);
                                        tbPemeriksaan.setValueAt(TAlergi.getText(), tbPemeriksaan.getSelectedRow(), 17);
                                        tbPemeriksaan.setValueAt(LingkarPerut.getText(), tbPemeriksaan.getSelectedRow(),
                                                18);
                                        tbPemeriksaan.setValueAt(TindakLanjut.getText(), tbPemeriksaan.getSelectedRow(),
                                                19);
                                        tbPemeriksaan.setValueAt(TPenilaian.getText(), tbPemeriksaan.getSelectedRow(),
                                                20);
                                        tbPemeriksaan.setValueAt(TInstruksi.getText(), tbPemeriksaan.getSelectedRow(),
                                                21);
                                        tbPemeriksaan.setValueAt(TEvaluasi.getText(), tbPemeriksaan.getSelectedRow(),
                                                22);
                                        tbPemeriksaan.setValueAt(KdPeg.getText(), tbPemeriksaan.getSelectedRow(), 23);
                                        tbPemeriksaan.setValueAt(TPegawai.getText(), tbPemeriksaan.getSelectedRow(),
                                                24);
                                        tbPemeriksaan.setValueAt(Jabatan.getText(), tbPemeriksaan.getSelectedRow(), 25);
                                        TSuhu.setText("");
                                        TTensi.setText("");
                                        TNadi.setText("");
                                        TRespirasi.setText("");
                                        TTinggi.setText("");
                                        TBerat.setText("");
                                        TGCS.setText("");
                                        TKeluhan.setText("");
                                        TPemeriksaan.setText("");
                                        TAlergi.setText("");
                                        LingkarPerut.setText("");
                                        TindakLanjut.setText("");
                                        TPenilaian.setText("");
                                        TInstruksi.setText("");
                                        SpO2.setText("");
                                        TEvaluasi.setText("");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        } else {
                            if (!tglPemeriksaan.equals("") && !jamPemeriksaan.equals("")) {
                                if (akses.getkode().equals("Admin Utama") || akses.getkode().equals(nipPemeriksaan)) {
                                    if (Sequel.mengedittf("pemeriksaan_ralan",
                                            "no_rawat=? and tgl_perawatan=? and jam_rawat=?",
                                            "suhu_tubuh=?,tensi=?,nadi=?,respirasi=?,tinggi=?,berat=?,spo2=?,gcs=?,kesadaran=?,keluhan=?,pemeriksaan=?,alergi=?,lingkar_perut=?,rtl=?,penilaian=?,instruksi=?,evaluasi=?,nip=?,tgl_perawatan=?,jam_rawat=?",
                                            23, new String[] {
                                                    TSuhu.getText(), TTensi.getText(), TNadi.getText(),
                                                    TRespirasi.getText(),
                                                    TTinggi.getText(), TBerat.getText(), SpO2.getText(), TGCS.getText(),
                                                    cmbKesadaran.getSelectedItem().toString(), TKeluhan.getText(),
                                                    TPemeriksaan.getText(),
                                                    TAlergi.getText(), LingkarPerut.getText(), TindakLanjut.getText(),
                                                    TPenilaian.getText(), TInstruksi.getText(), TEvaluasi.getText(),
                                                    KdPeg.getText(),
                                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                            + cmbDtk.getSelectedItem(),
                                                    TNoRw.getText(), tglPemeriksaan, jamPemeriksaan
                                            }) == true) {
                                        tampilPemeriksaan();
                                        emptTeks();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                                TCari.requestFocus();
                            }
                        }
                    }
                    break;
                case 4:
                    if ((!TTinggi_uteri.getText().trim().equals("")) || (!TLetak.getText().trim().equals(""))
                            || (!TDenyut.getText().trim().equals("")) || (!TKualitas_mnt.getText().trim().equals(""))
                            || (!TKualitas_dtk.getText().trim().equals("")) || (!TVulva.getText().trim().equals(""))
                            || (!TPortio.getText().trim().equals("")) || (!TTebal.getText().trim().equals(""))
                            || (!TPembukaan.getText().trim().equals("")) || (!TPenurunan.getText().trim().equals(""))
                            || (!TDenominator.getText().trim().equals(""))) {
                        if (tbPemeriksaanObstetri.getSelectedRow() > -1) {
                            if (Sequel.mengedittf("pemeriksaan_obstetri_ralan", "no_rawat='"
                                    + tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 1)
                                    + "' and tgl_perawatan='"
                                    + tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 4)
                                    + "' and jam_rawat='"
                                    + tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 5) + "'",
                                    "no_rawat='" + TNoRw.getText() + "', tgl_perawatan='"
                                            + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "', "
                                            + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem()
                                            + ":" + cmbDtk.getSelectedItem() + "', "
                                            + "tinggi_uteri='" + TTinggi_uteri.getText() + "', janin='"
                                            + cmbJanin.getSelectedItem() + "', letak='" + TLetak.getText() + "', "
                                            + "panggul='" + cmbPanggul.getSelectedItem() + "', denyut='"
                                            + TDenyut.getText() + "', kontraksi='" + cmbKontraksi.getSelectedItem()
                                            + "', "
                                            + "kualitas_mnt='" + TKualitas_mnt.getText() + "', kualitas_dtk='"
                                            + TKualitas_dtk.getText() + "', "
                                            + "fluksus='" + cmbFluksus.getSelectedItem() + "', albus='"
                                            + cmbAlbus.getSelectedItem() + "', vulva='" + TVulva.getText() + "',"
                                            + "portio='" + TPortio.getText() + "', dalam='" + cmbDalam.getSelectedItem()
                                            + "', tebal='" + TTebal.getText() + "', "
                                            + "arah='" + cmbArah.getSelectedItem() + "', pembukaan='"
                                            + TPembukaan.getText() + "', penurunan='" + TPenurunan.getText() + "', "
                                            + "denominator='" + TDenominator.getText() + "', ketuban='"
                                            + cmbKetuban.getSelectedItem() + "', feto='" + cmbFeto.getSelectedItem()
                                            + "'") == true) {
                                tbPemeriksaanObstetri.setValueAt(TNoRw.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 1);
                                tbPemeriksaanObstetri.setValueAt(TNoRM.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 2);
                                tbPemeriksaanObstetri.setValueAt(TPasien.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 3);
                                tbPemeriksaanObstetri.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        tbPemeriksaanObstetri.getSelectedRow(), 4);
                                tbPemeriksaanObstetri
                                        .setValueAt(
                                                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                        + cmbDtk.getSelectedItem(),
                                                tbPemeriksaanObstetri.getSelectedRow(), 5);
                                tbPemeriksaanObstetri.setValueAt(TTinggi_uteri.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 6);
                                tbPemeriksaanObstetri.setValueAt(cmbJanin.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 7);
                                tbPemeriksaanObstetri.setValueAt(TLetak.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 8);
                                tbPemeriksaanObstetri.setValueAt(cmbPanggul.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 9);
                                tbPemeriksaanObstetri.setValueAt(TDenyut.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 10);
                                tbPemeriksaanObstetri.setValueAt(cmbKontraksi.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 11);
                                tbPemeriksaanObstetri.setValueAt(TKualitas_mnt.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 12);
                                tbPemeriksaanObstetri.setValueAt(TKualitas_dtk.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 13);
                                tbPemeriksaanObstetri.setValueAt(cmbFluksus.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 14);
                                tbPemeriksaanObstetri.setValueAt(cmbAlbus.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 15);
                                tbPemeriksaanObstetri.setValueAt(TVulva.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 16);
                                tbPemeriksaanObstetri.setValueAt(TPortio.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 17);
                                tbPemeriksaanObstetri.setValueAt(cmbDalam.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 18);
                                tbPemeriksaanObstetri.setValueAt(TTebal.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 19);
                                tbPemeriksaanObstetri.setValueAt(cmbArah.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 20);
                                tbPemeriksaanObstetri.setValueAt(TPembukaan.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 21);
                                tbPemeriksaanObstetri.setValueAt(TPenurunan.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 22);
                                tbPemeriksaanObstetri.setValueAt(TDenominator.getText(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 23);
                                tbPemeriksaanObstetri.setValueAt(cmbKetuban.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 24);
                                tbPemeriksaanObstetri.setValueAt(cmbFeto.getSelectedItem().toString(),
                                        tbPemeriksaanObstetri.getSelectedRow(), 25);
                                TTinggi_uteri.setText("");
                                cmbJanin.setSelectedIndex(0);
                                TLetak.setText("");
                                cmbPanggul.setSelectedIndex(0);
                                TDenyut.setText("");
                                cmbKontraksi.setSelectedIndex(0);
                                TKualitas_mnt.setText("");
                                TKualitas_dtk.setText("");
                                cmbFluksus.setSelectedIndex(0);
                                cmbAlbus.setSelectedIndex(0);
                                TVulva.setText("");
                                TPortio.setText("");
                                cmbDalam.setSelectedIndex(0);
                                TTebal.setText("");
                                cmbArah.setSelectedIndex(0);
                                TPembukaan.setText("");
                                TPenurunan.setText("");
                                TDenominator.setText("");
                                cmbKetuban.setSelectedIndex(0);
                                cmbFeto.getSelectedItem().toString();
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }
                    break;
                case 5:
                    if ((!TInspeksi.getText().trim().equals("")) || (!TInspeksiVulva.getText().trim().equals(""))
                            || (!TInspekuloGine.getText().trim().equals(""))
                            || (!TVulvaInspekulo.getText().trim().equals(""))
                            || (!TPortioInspekulo.getText().trim().equals(""))
                            || (!TSondage.getText().trim().equals(""))
                            || (!TPortioDalam.getText().trim().equals("")) || (!TBentuk.getText().trim().equals(""))
                            || (!TCavumUteri.getText().trim().equals("")) || (!TUkuran.getText().trim().equals(""))
                            || (!TAdnexaKanan.getText().trim().equals("")) || (!TAdnexaKiri.getText().trim().equals(""))
                            || (!TCavumDouglas.getText().trim().equals(""))) {
                        if (tbPemeriksaanGinekologi.getSelectedRow() > -1) {
                            if (Sequel.mengedittf("pemeriksaan_ginekologi_ralan",
                                    "no_rawat='"
                                            + tbPemeriksaanGinekologi
                                                    .getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 1)
                                            + "' and tgl_perawatan='"
                                            + tbPemeriksaanGinekologi
                                                    .getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 4)
                                            + "' and jam_rawat='"
                                            + tbPemeriksaanGinekologi
                                                    .getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 5)
                                            + "'",
                                    "no_rawat='" + TNoRw.getText() + "', tgl_perawatan='"
                                            + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "', "
                                            + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem()
                                            + ":" + cmbDtk.getSelectedItem() + "', "
                                            + "inspeksi='" + TInspeksi.getText() + "', inspeksi_vulva='"
                                            + TInspeksiVulva.getText() + "', inspekulo_gine='"
                                            + TInspekuloGine.getText() + "', "
                                            + "fluxus_gine='" + cmbFluxusGine.getSelectedItem() + "', fluor_gine='"
                                            + cmbFluorGine.getSelectedItem() + "', "
                                            + "vulva_inspekulo='" + TVulvaInspekulo.getText() + "', portio_inspekulo='"
                                            + TPortioInspekulo.getText() + "', sondage='" + TSondage.getText() + "', "
                                            + "portio_dalam='" + TPortioDalam.getText() + "', bentuk='"
                                            + TBentuk.getText() + "', cavum_uteri='" + TCavumUteri.getText() + "', "
                                            + "mobilitas='" + cmbMobilitas.getSelectedItem() + "', ukuran='"
                                            + TUkuran.getText() + "', nyeri_tekan='" + cmbNyeriTekan.getSelectedItem()
                                            + "',"
                                            + "adnexa_kanan='" + TAdnexaKanan.getText() + "', adnexa_kiri='"
                                            + TAdnexaKiri.getText() + "', cavum_douglas='" + TCavumDouglas.getText()
                                            + "'") == true) {
                                tbPemeriksaanGinekologi.setValueAt(TNoRw.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 1);
                                tbPemeriksaanGinekologi.setValueAt(TNoRM.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 2);
                                tbPemeriksaanGinekologi.setValueAt(TPasien.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 3);
                                tbPemeriksaanGinekologi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 4);
                                tbPemeriksaanGinekologi.setValueAt(
                                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                                + cmbDtk.getSelectedItem(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 5);
                                tbPemeriksaanGinekologi.setValueAt(TInspeksi.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 6);
                                tbPemeriksaanGinekologi.setValueAt(TInspeksiVulva.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 7);
                                tbPemeriksaanGinekologi.setValueAt(TInspekuloGine.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 8);
                                tbPemeriksaanGinekologi.setValueAt(cmbFluxusGine.getSelectedItem().toString(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 9);
                                tbPemeriksaanGinekologi.setValueAt(cmbFluorGine.getSelectedItem().toString(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 10);
                                tbPemeriksaanGinekologi.setValueAt(TVulvaInspekulo.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 11);
                                tbPemeriksaanGinekologi.setValueAt(TPortioInspekulo.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 12);
                                tbPemeriksaanGinekologi.setValueAt(TSondage.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 13);
                                tbPemeriksaanGinekologi.setValueAt(TPortioDalam.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 14);
                                tbPemeriksaanGinekologi.setValueAt(TBentuk.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 15);
                                tbPemeriksaanGinekologi.setValueAt(TCavumUteri.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 16);
                                tbPemeriksaanGinekologi.setValueAt(cmbMobilitas.getSelectedItem().toString(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 17);
                                tbPemeriksaanGinekologi.setValueAt(TUkuran.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 18);
                                tbPemeriksaanGinekologi.setValueAt(cmbNyeriTekan.getSelectedItem().toString(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 19);
                                tbPemeriksaanGinekologi.setValueAt(TAdnexaKanan.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 20);
                                tbPemeriksaanGinekologi.setValueAt(TAdnexaKiri.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 21);
                                tbPemeriksaanGinekologi.setValueAt(TCavumDouglas.getText(),
                                        tbPemeriksaanGinekologi.getSelectedRow(), 22);
                                TInspeksi.setText("");
                                TInspeksiVulva.setText("");
                                TInspekuloGine.setText("");
                                cmbFluxusGine.setSelectedIndex(0);
                                cmbFluorGine.setSelectedIndex(0);
                                TVulvaInspekulo.setText("");
                                TPortioInspekulo.setText("");
                                TSondage.setText("");
                                TPortioDalam.setText("");
                                TBentuk.setText("");
                                TCavumUteri.setText("");
                                cmbMobilitas.setSelectedIndex(0);
                                TUkuran.setText("");
                                cmbNyeriTekan.setSelectedIndex(0);
                                TAdnexaKanan.setText("");
                                TAdnexaKiri.setText("");
                                TCavumDouglas.getText();
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }
                    break;
                case 7:
                    if (!Catatan.getText().trim().equals("")) {
                        if (tbCatatan.getSelectedRow() > -1) {
                            if (Sequel.mengedittf("catatan_perawatan",
                                    "no_rawat='" + tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 1)
                                            + "' and tanggal='" + tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 4)
                                            + "' and jam='" + tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 5)
                                            + "' and kd_dokter='" + tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 6)
                                            + "'",
                                    "no_rawat='" + TNoRw.getText() + "',catatan='" + Catatan.getText() + "',"
                                            + "kd_dokter='" + KdDok3.getText() + "',tanggal='"
                                            + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                            + "jam='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem() + "'") == true) {
                                tbCatatan.setValueAt(TNoRw.getText(), tbCatatan.getSelectedRow(), 1);
                                tbCatatan.setValueAt(TNoRM.getText(), tbCatatan.getSelectedRow(), 2);
                                tbCatatan.setValueAt(TPasien.getText(), tbCatatan.getSelectedRow(), 3);
                                tbCatatan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                        tbCatatan.getSelectedRow(), 4);
                                tbCatatan.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem(), tbCatatan.getSelectedRow(), 5);
                                tbCatatan.setValueAt(KdDok3.getText(), tbCatatan.getSelectedRow(), 6);
                                tbCatatan.setValueAt(TDokter3.getText(), tbCatatan.getSelectedRow(), 7);
                                tbCatatan.setValueAt(Catatan.getText(), tbCatatan.getSelectedRow(), 8);
                                Catatan.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }
                    break;
                case 8:
                    if ((!TSituation.getText().trim().equals("")) || (!TBackground.getText().trim().equals(""))
                            || (!TAssesment.getText().trim().equals(""))
                            || (!TRecommendation.getText().trim().equals(""))) {
                        if (tbPemeriksaanSbar.getSelectedRow() > -1) {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.mengedit("pemeriksaan_ralan_sbar", "no_rawat='"
                                        + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                                        + "' and tgl_perawatan='"
                                        + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                                        + "' and jam_rawat='"
                                        + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                                        "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText()
                                                + "',background='" + TBackground.getText() + "',"
                                                + "assesment='" + TAssesment.getText() + "',recommendation='"
                                                + TRecommendation.getText() + "',"
                                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                                                + "nip='" + KdPeg2.getText() + "'");
                                tampilPemeriksaanSbar();
                                BtnBatalActionPerformed(evt);
                            } else {
                                if (akses.getkode().equals(tbPemeriksaanSbar
                                        .getValueAt(tbPemeriksaanSbar.getSelectedRow(), 10).toString())) {
                                    Sequel.mengedit("pemeriksaan_ralan_sbar", "no_rawat='"
                                            + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                                            + "' and tgl_perawatan='"
                                            + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                                            + "' and jam_rawat='"
                                            + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                                            "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText()
                                                    + "',background='" + TBackground.getText() + "',"
                                                    + "assesment='" + TAssesment.getText() + "',recommendation='"
                                                    + TRecommendation.getText() + "',"
                                                    + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                                                    + "',"
                                                    + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                    + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'");

                                    tampilPemeriksaanSbar();
                                    BtnBatalActionPerformed(evt);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }
                    break;

                case 9:
                    if ((!TSituation1.getText().trim().equals("")) || (!TBackground1.getText().trim().equals(""))
                            || (!TAssesment1.getText().trim().equals(""))
                            || (!TRecommendation1.getText().trim().equals(""))) {
                        if (tbPemeriksaanTbak.getSelectedRow() > -1) {
                            if (akses.getkode().equals("Admin Utama")) {
                                Sequel.mengedit("pemeriksaan_ralan_tbak", "no_rawat='"
                                        + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1)
                                        + "' and tgl_perawatan='"
                                        + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4)
                                        + "' and jam_rawat='"
                                        + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5) + "'",
                                        "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation1.getText()
                                                + "',background='" + TBackground1.getText() + "',"
                                                + "assesment='" + TAssesment1.getText() + "',recommendation='"
                                                + TRecommendation1.getText() + "',"
                                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                                                + "nip='" + KdPeg4.getText() + "'");
                                tampilPemeriksaanTbak();
                                BtnBatalActionPerformed(evt);
                            } else {
                                if (akses.getkode().equals(tbPemeriksaanTbak
                                        .getValueAt(tbPemeriksaanTbak.getSelectedRow(), 10).toString())) {
                                    Sequel.mengedit("pemeriksaan_ralan_tbak", "no_rawat='"
                                            + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1)
                                            + "' and tgl_perawatan='"
                                            + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4)
                                            + "' and jam_rawat='"
                                            + tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5) + "'",
                                            "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation1.getText()
                                                    + "',background='" + TBackground1.getText() + "',"
                                                    + "assesment='" + TAssesment1.getText() + "',recommendation='"
                                                    + TRecommendation1.getText() + "',"
                                                    + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "")
                                                    + "',"
                                                    + "jam_rawat='" + cmbJam.getSelectedItem() + ":"
                                                    + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'");

                                    tampilPemeriksaanTbak();
                                    BtnBatalActionPerformed(evt);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }
                    break;

                case 10:

                    if (tbCatatanPerawatIGD.getSelectedRow() > -1) {
                        if (tbCatatanPerawatIGD.getSelectedRow() > -1) {
                            if (akses.getkode().equals("Admin Utama")) {
                                gantiCatatanPerawatIGD();
                            } else {
                                if (kdptg3.getText().equals(tbCatatanPerawatIGD
                                        .getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 9).toString())) {
                                    gantiCatatanPerawatIGD();
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                        }
                    }
                    break;
                case 11:
                    if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")
                            || TPasien.getText().trim().equals("")) {
                        Valid.textKosong(TCari, "Pasien");
                    } else if (AlergiCode.getText().trim().equals("")) {
                        Valid.textKosong(AlergiCode, "Kode Vaksin");
                    } else if (ReaksiCode.getText().trim().equals("")) {
                        Valid.textKosong(ReaksiCode, "Reaksi");
                    } else if (TKeterangan.getText().trim().equals("")) {
                        Valid.textKosong(TKeterangan, "Note / Keterangan");
                    } else {
                        if (tbObat.getSelectedRow() > -1) {
                            if (akses.getkode().equals("Admin Utama")) {
                                ganti();
                            } else {
                                if (akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString())) {
                                    ganti();
                                } else {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }

    private void tbRawatDrPrMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeDrPr.getRowCount() != 0) {
            try {
                getDataDrPr();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TPerawat2.setText(petugas.tampil3(kdptg2.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPetugas2ActionPerformed(null);
        } else {
            Valid.pindah(evt, KdDok2, BtnSeekPetugas2);
        }
    }

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TDokter2.setText(dokter.tampil3(KdDok2.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekDokter2ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, kdptg2);
        }
    }

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModePemeriksaan.getRowCount() != 0) {
            try {
                getDataPemeriksaan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, BtnSeekDokter, cmbJam);
    }

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, DTPTgl, cmbMnt);
    }

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbJam, cmbDtk);
    }

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbMnt, TCari);
    }

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
    }

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TCariPasien, DTPCari1);
    }

    private void tbPemeriksaanObstetriMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        if (tabModeObstetri.getRowCount() != 0) {
            try {
                getDataPemeriksaanObstetri();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        isForm2();
    }

    private void TTinggi_uteriKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TNoRw, cmbJanin);
    }

    private void TLetakKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbJanin, cmbPanggul);
    }

    private void TKualitas_dtkKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TKualitas_mnt, cmbFluksus);
    }

    private void cmbPanggulKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TLetak, TDenyut);
    }

    private void TTebalKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbDalam, cmbArah);
    }

    private void TDenyutKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbPanggul, cmbKontraksi);
    }

    private void TDenominatorKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TPenurunan, cmbFeto);
    }

    private void TKualitas_mntKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbKontraksi, TKualitas_dtk);
    }

    private void cmbFetoKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TDenominator, BtnSimpan);
    }

    private void cmbJaninKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void cmbKetubanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbAlbus, TVulva);
    }

    private void TPortioKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TVulva, cmbDalam);
    }

    private void TVulvaKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbKetuban, TPortio);
    }

    private void cmbKontraksiKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TDenyut, TKualitas_mnt);
    }

    private void cmbAlbusKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbFluksus, cmbKetuban);
    }

    private void cmbFluksusKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TKualitas_dtk, cmbAlbus);
    }

    private void cmbDalamKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TPortio, TTebal);
    }

    private void TPembukaanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbArah, TPenurunan);
    }

    private void TPenurunanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TPembukaan, TDenominator);
    }

    private void cmbArahKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TTebal, TPembukaan);
    }

    private void tbPemeriksaanGinekologiMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        if (tabModeGinekologi.getRowCount() != 0) {
            try {
                getDataPemeriksaanGinekologi();

            } catch (java.lang.NullPointerException e) {

            }
        }
    }

    private void Scroll5KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ChkInput2ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm3();
    }

    private void TInspeksiVulvaKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TInspeksi, TInspekuloGine);
    }

    private void TAdnexaKananKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbNyeriTekan, TAdnexaKiri);
    }

    private void cmbMobilitasKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TCavumUteri, TUkuran);
    }

    private void TInspekuloGineKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TInspeksiVulva, cmbFluxusGine);
    }

    private void TPortioInspekuloKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TVulvaInspekulo, TSondage);
    }

    private void TCavumUteriKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TBentuk, cmbMobilitas);
    }

    private void cmbFluorGineKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbFluxusGine, TVulvaInspekulo);
    }

    private void TInspeksiKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TNoRw, TInspeksiVulva);
    }

    private void cmbFluxusGineKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TInspekuloGine, cmbFluorGine);
    }

    private void TVulvaInspekuloKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbFluorGine, TPortioInspekulo);
    }

    private void TPortioDalamKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TSondage, TBentuk);
    }

    private void TBentukKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TPortioDalam, TCavumUteri);
    }

    private void cmbNyeriTekanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TUkuran, TAdnexaKanan);
    }

    private void TSondageKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TPortioInspekulo, TPortioDalam);
    }

    private void TAdnexaKiriKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TAdnexaKanan, TCavumDouglas);
    }

    private void TCavumDouglasKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TAdnexaKiri, BtnSimpan);
    }

    private void TUkuranKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, cmbMobilitas, cmbNyeriTekan);
    }

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah2(evt, KdPeg, TPemeriksaan);
    }

    private void tbRawatDrKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModeDr.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }

    private void tbRawatPrKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModePr.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbRawatDrPrKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModeDrPr.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataDrPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbPemeriksaanKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModePemeriksaan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }

    private void tbPemeriksaanObstetriKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (tabModeObstetri.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanObstetri();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbPemeriksaanGinekologiKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (tabModeGinekologi.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanGinekologi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void TabRawatTindakanDokterMouseClicked(java.awt.event.MouseEvent evt) {
        if (TabRawatTindakanDokter.getSelectedIndex() == 0) {
            TCari.setText("");
        } else if (TabRawatTindakanDokter.getSelectedIndex() == 1) {
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokter();
    }

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {
        if (tbTindakan.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbTindakan.getSelectedColumn();
                    if (i == 1) {
                        if (tbTindakan.getSelectedRow() > -1) {
                            tbTindakan.setValueAt(true, tbTindakan.getSelectedRow(), 0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }

    private void tbTindakan2KeyPressed(java.awt.event.KeyEvent evt) {
        if (tbTindakan2.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbTindakan2.getSelectedColumn();
                    if (i == 1) {
                        if (tbTindakan2.getSelectedRow() > -1) {
                            tbTindakan2.setValueAt(true, tbTindakan2.getSelectedRow(), 0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }

    private void TabRawatTindakanPetugasMouseClicked(java.awt.event.MouseEvent evt) {
        if (TabRawatTindakanPetugas.getSelectedIndex() == 0) {
            TCari.setText("");
        } else if (TabRawatTindakanPetugas.getSelectedIndex() == 1) {
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananPetugas();
    }

    private void tbTindakan3KeyPressed(java.awt.event.KeyEvent evt) {
        if (tbTindakan3.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbTindakan3.getSelectedColumn();
                    if (i == 1) {
                        if (tbTindakan3.getSelectedRow() > -1) {
                            tbTindakan3.setValueAt(true, tbTindakan3.getSelectedRow(), 0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }

    private void TabRawatTindakanDokterPetugasMouseClicked(java.awt.event.MouseEvent evt) {
        if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 0) {
            TCari.setText("");
        } else if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 1) {
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokterPetugas();
    }

    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRalan perawatan = new DlgJnsPerawatanRalan(null, false);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void BtnResepObatActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                jmlparsial = 0;
                if (aktifkanparsial.equals("yes")) {
                    jmlparsial = Sequel.cariInteger(
                            "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                            kd_pj);
                }
                if (jmlparsial > 0) {
                    inputResep();
                } else {
                    if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                    } else {
                        inputResep();
                    }
                }
            }
        }
    }

    private void BtnObatBhpActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap = new DlgPemberianObat(null, false);
            dlgrwinap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm2(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "ralan");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnBerkasDigitalActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");

            try {
                if (akses.gethapus_berkas_digital_perawatan() == true) {
                    berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                            + koneksiDB.HYBRIDWEB() + "/" + "berkasrawat/login2.php?act=login&usere="
                            + koneksiDB.USERHYBRIDWEB() + "&passwordte=" + koneksiDB.PASHYBRIDWEB() + "&no_rawat="
                            + TNoRw.getText());
                } else {
                    berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                            + koneksiDB.HYBRIDWEB() + "/" + "berkasrawat/login2nonhapus.php?act=login&usere="
                            + koneksiDB.USERHYBRIDWEB() + "&passwordte=" + koneksiDB.PASHYBRIDWEB() + "&no_rawat="
                            + TNoRw.getText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            }

            berkas.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private void BtnPermintaanRadActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private void BtnInputObatActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                jmlparsial = 0;
                if (aktifkanparsial.equals("yes")) {
                    jmlparsial = Sequel.cariInteger(
                            "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                            kd_pj);
                }
                if (jmlparsial > 0) {
                    inputObat();
                } else {
                    if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                    } else {
                        inputObat();
                    }
                }
            }
        }
    }

    private void BtnSKDPActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                SuratKontrol form = new SuratKontrol(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks();
                form.setNoRm(TNoRM.getText(), TPasien.getText(), kode_poli, Sequel
                        .cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?", kode_poli),
                        KdDok.getText(), TDokter.getText());
                form.setVisible(true);
            }
        }
    }

    private void BtnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar = new DlgCopyResep(null, false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(), TNoRM.getText(), KdDok.getText(), kd_pj, "ralan");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {
        isMenu();
    }

    private void BtnKamarActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                } else {
                    inputKamar();
                }
            }
        }
    }

    private void BtnRujukInternalActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DRujukInternal dlgrjk = new DRujukInternal(null, false);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), this.getWidth() + 20,
                            this.getHeight() + 20);
                    dlgrjk.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }

    private void BtnRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgRujuk dlgrjk = new DlgRujuk(null, false);
                dlgrjk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.emptTeks();
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                dlgrjk.tampil();
                dlgrjk.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private void BtnCatatanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan = new DlgCatatan(null, true);
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720, 330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void ChkInput3ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm4();
    }

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, KdDok3, BtnSimpan);
    }

    private void tbCatatanMouseClicked(java.awt.event.MouseEvent evt) {
        if (TabModeCatatan.getRowCount() != 0) {
            try {
                getDataCatatan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }

    private void tbCatatanKeyReleased(java.awt.event.KeyEvent evt) {
        if (TabModeCatatan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataCatatan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }

    private void KdDok3KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TDokter3.setText(dokter.tampil3(KdDok3.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekDokter3ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, BtnSeekDokter3);
        }
    }

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah2(evt, TPenilaian, TInstruksi);
    }

    private void BtnTriaseIGDActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTriaseIGD form = new RMTriaseIGD(null, false);
            form.isCek();
            form.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText());
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnResumeActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasien resume = new RMDataResumePasien(null, false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void cmbKesadaranKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void KdPegKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPegawaiActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, TKeluhan);
        }
    }

    private void BtnSeekPegawaiActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }

    private void TInstruksiKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah2(evt, TindakLanjut, TEvaluasi);
    }

    private void BtnResepLuarActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap...!!!");
            } else {
                InventoryResepLuar resep = new InventoryResepLuar(null, false);
                resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(), KdDok.getText(), TDokter.getText(),
                        TNoRM.getText() + " " + TPasien.getText());
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }

    private void BtnAwalKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form = new RMPenilaianAwalKeperawatanRalan(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalKeperawatanGigiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanGigi form = new RMPenilaianAwalKeperawatanGigi(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalKeperawatanKandunganActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanKebidanan form = new RMPenilaianAwalKeperawatanKebidanan(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalKeperawatanAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanBayiAnak form = new RMPenilaianAwalKeperawatanBayiAnak(null, false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanDewasa form = new RMPenilaianAwalMedisRalanDewasa(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume = new RMRiwayatPerawatan(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisKandunganActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanKandungan form = new RMPenilaianAwalMedisRalanKandungan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                form.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Sequel.cariIsi(
                        "select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?", kode_poli), "Ralan");
                form.setVisible(true);
            }
        }
    }

    private void BtnAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisIGD form = new RMPenilaianAwalMedisIGD(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanAnak form = new RMPenilaianAwalMedisRalanAnak(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianFisioterapi form = new RMPenilaianFisioterapi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnMedicalCheckUpActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMCU form = new RMMCU(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnUjiFungsiKFRActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMUjiFungsiKFR form = new RMUjiFungsiKFR(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void TEvaluasiKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah2(evt, TInstruksi, BtnSimpan);
    }

    private void BtnAwalKeperawatanIGDActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanIGD form = new RMPenilaianAwalKeperawatanIGD(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiIGD form = new RMDataCatatanObservasiIGD(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisTHTActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanTHT form = new RMPenilaianAwalMedisRalanTHT(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPsikologi form = new RMPenilaianPsikologi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void LingkarPerutKeyPressed(java.awt.event.KeyEvent evt) {
    }

    private void BtnAwalMedisPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPsikiatrik form = new RMPenilaianAwalMedisRalanPsikiatrik(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        }
    }

    private void BtnAwalMedisPenyakitDalamActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPenyakitDalam form = new RMPenilaianAwalMedisRalanPenyakitDalam(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisMataActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanMata form = new RMPenilaianAwalMedisRalanMata(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisNeurologiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanNeurologi form = new RMPenilaianAwalMedisRalanNeurologi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisOrthopediActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanOrthopedi form = new RMPenilaianAwalMedisRalanOrthopedi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisBedahActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanBedah form = new RMPenilaianAwalMedisRalanBedah(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalKeperawatanPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanPsikiatri form = new RMPenilaianAwalKeperawatanRalanPsikiatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPemantauanPEWSAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanPEWS form = new RMPemantauanPEWS(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreOperasi form = new RMPenilaianPreOperasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPreAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreAnastesi form = new RMPenilaianPreAnastesi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhDewasa form = new RMPenilaianLanjutanRisikoJatuhDewasa(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhAnak form = new RMPenilaianLanjutanRisikoJatuhAnak(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisGeriatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanGeriatri form = new RMPenilaianAwalMedisRalanGeriatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianTambahanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanGeriatri form = new RMPenilaianTambahanGeriatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnHasilPemeriksaanUSGActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSG form = new RMHasilPemeriksaanUSG(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkriningNutrisiDewasaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiDewasa form = new RMSkriningNutrisiDewasa(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkriningNutrisiLansiaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiLansia form = new RMSkriningNutrisiLansia(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkriningNutrisiAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiAnak form = new RMSkriningNutrisiAnak(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkriningGiziLanjutActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataSkriningGiziLanjut form = new RMDataSkriningGiziLanjut(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataAsuhanGizi form = new RMDataAsuhanGizi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnMonitoringAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringAsuhanGizi form = new RMDataMonitoringAsuhanGizi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMKonselingFarmasi form = new RMKonselingFarmasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanPelayananInformasiObat form = new DlgPermintaanPelayananInformasiObat(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnTransferAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form = new RMTransferPasienAntarRuang(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanCekGDS form = new RMDataCatatanCekGDS(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnChecklistPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPreOperasi form = new RMChecklistPreOperasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSignInSebelumAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignInSebelumAnastesi form = new RMSignInSebelumAnastesi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTimeOutSebelumInsisi form = new RMTimeOutSebelumInsisi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignOutSebelumMenutupLuka form = new RMSignOutSebelumMenutupLuka(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnChecklistPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPostOperasi form = new RMChecklistPostOperasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnRekonsiliasiObatActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRekonsiliasiObat form = new RMRekonsiliasiObat(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienTerminal form = new RMPenilaianPasienTerminal(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnMonitoringReaksiTranfusiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringReaksiTranfusi form = new RMDataMonitoringReaksiTranfusi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianKorbanKekerasan form = new RMPenilaianKorbanKekerasan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhLansia form = new RMPenilaianLanjutanRisikoJatuhLansia(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPasienPenyakitMenularActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienPenyakitMenular form = new RMPenilaianPasienPenyakitMenular(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnEdukasiPasienKeluargaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMEdukasiPasienKeluargaRawatJalan form = new RMEdukasiPasienKeluargaRawatJalan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPemantauanPEWSDewasaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSD form = new RMPemantauanEWSD(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianTambahanBunuhDiriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanBunuhDiri form = new RMPenilaianTambahanBunuhDiri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanPerilakuKekerasan form = new RMPenilaianTambahanPerilakuKekerasan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianTambahanMelarikanDiriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanMelarikanDiri form = new RMPenilaianTambahanMelarikanDiri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisBedahMulutActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanBedahMulut form = new RMPenilaianAwalMedisRalanBedahMulut(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPasienKeracunanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienKeracunan form = new RMPenilaianPasienKeracunan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPemantauanMEOWSActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanMEOWS form = new RMPemantauanMEOWS(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnCatatanADIMEGiziActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanADIMEGizi form = new RMCatatanADIMEGizi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalKeperawatanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanGeriatri form = new RMPenilaianAwalKeperawatanRalanGeriatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnChecklistKriteriaMasukHCUActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukHCU form = new RMChecklistKriteriaMasukHCU(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnDokumentasiESWLActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilTindakanESWL form = new RMHasilTindakanESWL(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnChecklistKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukICU form = new RMChecklistKriteriaMasukICU(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianRisikoJatuhNeonatus form = new RMPenilaianRisikoJatuhNeonatus(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhGeriatri form = new RMPenilaianLanjutanRisikoJatuhGeriatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPemantauanEWSNeonatusActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSNeonatus form = new RMPemantauanEWSNeonatus(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisKulitKelaminActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanKulitDanKelamin form = new RMPenilaianAwalMedisRalanKulitDanKelamin(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisHemodialisa form = new RMPenilaianAwalMedisHemodialisa(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate(), "Rawat Jalan/IGD");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhPsikiatri form = new RMPenilaianLanjutanRisikoJatuhPsikiatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianLanjutanSkriningFungsionalActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanSkriningFungsional form = new RMPenilaianLanjutanSkriningFungsional(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisRehabMedikActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanRehabMedik form = new RMPenilaianAwalMedisRalanRehabMedik(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisIGDPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisIGDPsikiatri form = new RMPenilaianAwalMedisIGDPsikiatri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianUlangNyeriActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianUlangNyeri form = new RMPenilaianUlangNyeri(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnTemplatePemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else if (TPegawai.getText().trim().equals("") || KdPeg.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        } else {
            jmlparsial = 0;
            if (aktifkanparsial.equals("yes")) {
                jmlparsial = Sequel.cariInteger(
                        "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                        Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                TNoRw.getText()));
            }
            if (jmlparsial > 0) {
                inputTemplate();
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                } else {
                    inputTemplate();
                }
            }
        }
    }

    private void BtnAwalTerapiWicaraActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTerapiWicara form = new RMPenilaianTerapiWicara(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPengkajianRestrainActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPengkajianRestrain form = new RMPengkajianRestrain(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnAwalMedisParuActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanParu form = new RMPenilaianAwalMedisRalanParu(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanKeperawatanRalan form = new RMDataCatatanKeperawatanRalan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnCatatanPersalinananActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanPersalinan form = new RMCatatanPersalinan(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkorAldrettePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringAldrettePascaAnestesi form = new RMMonitoringAldrettePascaAnestesi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnSkorStewardPascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringStewardPascaAnestesi form = new RMMonitoringStewardPascaAnestesi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnOdontogramActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgOdontogram odontogram = new DlgOdontogram(null, false);
            odontogram.isCek();
            odontogram.emptTeks();
            odontogram.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            odontogram.setLocationRelativeTo(internalFrame1);
            odontogram.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            odontogram.tampil();
            odontogram.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void TglLahirKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnTemplateResepActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgTemplateResep template = new DlgTemplateResep(null, false);
            template.isCek();
            template.setRM(TNoRw.getText(), TNoRM.getText(), KdDok.getText(), kd_pj, "ralan");
            template.tampil();
            template.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            template.setLocationRelativeTo(internalFrame1);
            template.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnTemplatePemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                jmlparsial = 0;
                if (aktifkanparsial.equals("yes")) {
                    jmlparsial = Sequel.cariInteger(
                            "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                            kd_pj);
                }
                if (jmlparsial > 0) {
                    inputResep2();
                } else {
                    if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                    } else {
                        inputResep2();
                    }
                }
            }
        }
    }

    private void Btn5Soap1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else if (TPegawai.getText().trim().equals("") || KdPeg.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu petugas/dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            soapterakhir.setNoRM(TNoRM.getText(), KdPeg.getText(), "Ralan");
            soapterakhir.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            soapterakhir.setLocationRelativeTo(internalFrame1);
            soapterakhir.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnHasilRadiologiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPenunjang resume = new RMRiwayatPenunjang(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } // TODO add your handling code here:
    }

    private void BtnHasilPengobatanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPengobatan resume = new RMRiwayatPengobatan(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } // TODO add your handling code here:
    }

    private void BtnHasilPengobatan1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatOperasi resume = new RMRiwayatOperasi(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } // TODO add your handling code here:
    }

    private void BtnInputLABActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        } // TODO add your handling code here:
    }

    private void BtnInputRADActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        } // TODO add your handling code here:
    }

    private void BtnInputTerimaPasienAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form = new RMTransferPasienAntarRuang(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        } // TODO add your handling code here:
    }

    private void BtnInputKonsul1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasien resume = new RMDataResumePasien(null, false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } // TODO add your handling code here:
    }

    private void BtnPanggilPasienActionPerformed(java.awt.event.ActionEvent evt) {
        poli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",
                TNoRw.getText());
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No Rawat dan No RM");
        } else {
            Sequel.queryu("delete from antripoli where kd_dokter='" + KdDok.getText() + "' and kd_poli='" + poli + "'");
            Sequel.queryu("insert into antripoli values('" + KdDok.getText() + "','" + poli + "','1','"
                    + TNoRw.getText() + "')");
            Sequel.menyimpan("mutasi_berkas", "'" + TNoRw.getText()
                    + "','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00'",
                    "status='Sudah Diterima',diterima=now()", "no_rawat='" + TNoRw.getText() + "'");
        }

    }

    private void BtnPanggilPasienKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnTemplatePemberianObat2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DRujukInternal dlgrjk = new DRujukInternal(null, false);
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), this.getWidth() + 20,
                        this.getHeight() + 20);
                dlgrjk.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private void tbPemeriksaanSbarMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModePemeriksaanSbar.getRowCount() != 0) {
            try {
                getDataPemeriksaanSbar();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }

    private void tbPemeriksaanSbarKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModePemeriksaan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanSbar();
                } catch (java.lang.NullPointerException e) {
                }
            }

        } // TODO add your handling code here:
    }

    private void ChkInput4ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm4();
    }

    private void TSituationKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TBackgroundKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TAssesmentKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TRecommendationKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void KdPeg2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void KdPeg2KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?", TPegawai2, KdPeg2.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPegawai1ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, TSituation);
        }
    }

    private void TPegawai2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnSeekPegawai1ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        pegawai2.emptTeks();
        pegawai2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai2.setLocationRelativeTo(internalFrame1);
        pegawai2.setVisible(true);
    }

    private void KdPeg3KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnVerifSbarActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgSBARRalan soap = new DlgSBARRalan(null, false);
            soap.setNoRawat(TNoRw.getText(), TNoRw.getText());
            soap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            soap.setLocationRelativeTo(internalFrame1);
            soap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void tbPemeriksaanTbakMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModePemeriksaanTbak.getRowCount() != 0) {
            try {
                getDataPemeriksaanTbak();
            } catch (java.lang.NullPointerException e) {
            }

        }
        // TODO add your handling code here:
    }

    private void tbPemeriksaanTbakKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ChkInput5ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void TSituation1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TBackground1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TAssesment1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TRecommendation1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void KdPeg4ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void KdPeg4KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void TPegawai4ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnSeekPegawai2ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        pegawai2.emptTeks();
        pegawai2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai2.setLocationRelativeTo(internalFrame1);
        pegawai2.setVisible(true);
        // TODO add your handling code here:
    }

    private void KdPeg5KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnVerifSbar1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgTBAKRalan soap = new DlgTBAKRalan(null, false);
            soap.setNoRawat(TNoRw.getText(), TNoRw.getText());
            soap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            soap.setLocationRelativeTo(internalFrame1);
            soap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
        // TODO add your handling code here:
    }

    private void ChkInput7ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void Catatan1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void kdptg3KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?", TPerawat3, kdptg3.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPetugas3ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, Catatan1);
        }
    }

    private void BtnSeekPetugas3ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true); // TODO add your handling code here:
    }

    private void tbCatatanPerawatIGDMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeCatatanPerawatIGD.getRowCount() != 0) {
            try {
                getDataCatatanPerawatIGD();
            } catch (java.lang.NullPointerException e) {
            }

        } // TODO add your handling code here:
    }

    private void tbCatatanPerawatIGDKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabModeCatatanPerawatIGD.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataCatatanPerawatIGD();
                } catch (java.lang.NullPointerException e) {
                }
            }

        } // TODO add your handling code here:
    }

    private void Scroll17MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void btnJenisAlergiActionPerformed(java.awt.event.ActionEvent evt) {
        mode = "alergi";
        allergycode.isCek("alergi");
        allergycode.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        allergycode.setLocationRelativeTo(internalFrame1);
        allergycode.setVisible(true);
    }

    private void btnJenisAlergiKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void AlergyDisplayKeyPressed(java.awt.event.KeyEvent evt) {

    }

    private void AlergySystemKeyPressed(java.awt.event.KeyEvent evt) {

    }

    private void AlergiCodeKeyPressed(java.awt.event.KeyEvent evt) {
        Valid.pindah(evt, TCari, AlergySystem);
    }

    private void TKeteranganKeyPressed(java.awt.event.KeyEvent evt) {

    }

    private void cmbKategoryKeyPressed(java.awt.event.KeyEvent evt) {

    }

    private void btnReaksiAlergiActionPerformed(java.awt.event.ActionEvent evt) {
        mode = "reaksialergi";
        allergycode.isCek("reaksialergi");
        allergycode.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        allergycode.setLocationRelativeTo(internalFrame1);
        allergycode.setVisible(true);
    }

    private void btnReaksiAlergiKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ReaksiDisplayKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ReaksiSystemKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ReaksiCodeKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void cmbSeverityKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {

            }
        }
    }

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void KdPeg1KeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnSeekPegawai3ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }

    private void BtnDicomActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TNoRw.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Nomor rawat masih kosong...!!!!");
        } else {
            try {
                String noRekammedis = TNoRM.getText();
                dicomViewer.setPasien(TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        true);
                dicomViewer.tampilDicomServer(Valid.SetTgl(DTPCari1.getSelectedItem() + "").replaceAll("-", ""),
                        Valid.SetTgl(DTPCari2.getSelectedItem() + "").replaceAll("-", ""), TNoRM.getText(),
                        TNoRw.getText());
                dicomViewer.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dicomViewer.setLocationRelativeTo(internalFrame1);
                dicomViewer.setVisible(true);
                dicomViewer.isForm();
                dicomViewer.isCek();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void BtnRiwayatFKTPActionPerformed(java.awt.event.ActionEvent evt) {
        variabel = Sequel.cariIsi(
                "select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",
                KdPeg.getText());
        if (!variabel.equals("")) {
            akses.setform("DlgRawatJalanDokter");
            ICareRiwayatPerawatan dlgki = new ICareRiwayatPerawatan(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.setPasien(
                    Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_rkm_medis=?", TNoRM.getText()),
                    variabel);
            dlgki.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!");
        }
    }

    private void KdNoRawatKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void ChkTemplateItemStateChanged(java.awt.event.ItemEvent evt) {
    }

    private void BtnSoapDokterActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        templatesoapie.emptTeks();
        templatesoapie.isCek();
        // templatesoapi.setRM(KdPeg.getText(), TPegawai.getText());
        templatesoapie.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        templatesoapie.setLocationRelativeTo(internalFrame1);
        templatesoapie.setVisible(true);
    }

    private void BtnSoapDokter1ActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgRawatJalanDokter");
        templatesoapieperawat.emptTeks();
        templatesoapieperawat.isCek();
        templatesoapieperawat.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        templatesoapieperawat.setLocationRelativeTo(internalFrame1);
        templatesoapieperawat.setVisible(true);
    }

    private void ChkTemplatePerawatItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnSuratRujukanBalikActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                SuratRujukanBalik form = new SuratRujukanBalik(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);
                // form.emptTeks();
                form.setNoRm(TNoRw.getText(), TNoRM.getText(), KdDok.getText(), TDokter.getText(), TPasien.getText());
                form.setVisible(true);
            }
        }
    }

    private void BtnCopyDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
            /*
             * } else if (TPegawai.getText().trim().equals("") ||
             * KdPeg.getText().trim().equals("")) {
             * JOptionPane.showMessageDialog(null,
             * "Maaf, Silahkan anda pilih dulu dokter pemberi asuhan...!!!");
             * TCari.requestFocus();
             */
        } else {
            jmlparsial = 0;
            if (aktifkanparsial.equals("yes")) {
                jmlparsial = Sequel.cariInteger(
                        "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                        Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                TNoRw.getText()));
            }
            if (jmlparsial > 0) {
                CopyDiagnosa();
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                } else {
                    CopyDiagnosa();
                }
            }
        }
    }

    private void BtnPeriksaActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCariPeriksaLabPetugas form = new DlgCariPeriksaLabPetugas(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            // form.emptTeks();
            form.setPasien(TNoRw.getText());
            form.TampilHasil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnLayananKedokteranFisikActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMLayananKedokteranFisikRehabilitasi form = new RMLayananKedokteranFisikRehabilitasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPeriksaRadActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            OrthancViewerHybridSplitRadPetugas form = new OrthancViewerHybridSplitRadPetugas(null, false);
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.tampilDicomServer(Valid.SetTgl(DTPCari1.getDate() + " "), Valid.SetTgl(DTPCari2.getDate() + " "),
                    TNoRM.getText(), TNoRw.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void TAlergiActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void TNadiActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void BtnTemplateActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",
                    TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                jmlparsial = 0;
                if (aktifkanparsial.equals("yes")) {
                    jmlparsial = Sequel.cariInteger(
                            "select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",
                            kd_pj);
                }
                if (jmlparsial > 0) {
                    inputTemplateResep();
                } else {
                    if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                    } else {
                        inputTemplateResep();
                    }
                }
            }
        }
    }

    private void BtnSkorBromagePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringBromagePascaAnestesi form = new RMMonitoringBromagePascaAnestesi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnPenilaianPreInduksiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreInduksi form = new RMPenilaianPreInduksi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnHasilPemeriksaanUSGUrologiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGUrologi form = new RMHasilPemeriksaanUSGUrologi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnHasilPemeriksaanUSGGynecologiActionPerformed(java.awt.event.ActionEvent evt) {
        if (TPasien.getText().trim().equals("") || TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGGynecologi form = new RMHasilPemeriksaanUSGGynecologi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(), DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatJalanDokter dialog = new DlgRawatJalanDokter(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify
    private widget.TextBox AlergiCode;
    private widget.TextBox AlergyDisplay;
    private widget.TextBox AlergySystem;
    private widget.Button Btn5Soap;
    private widget.Button BtnRiwayatPenunjang;
    private widget.Button BtnRiwayatFKTP;
    private widget.Button Btn5Soap1;
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalKeperawatan;
    private widget.Button BtnAwalKeperawatanAnak;
    private widget.Button BtnAwalKeperawatanGeriatri;
    private widget.Button BtnAwalKeperawatanGigi;
    private widget.Button BtnAwalKeperawatanIGD;
    private widget.Button BtnAwalKeperawatanKandungan;
    private widget.Button BtnAwalKeperawatanPsikiatri;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedisAnak;
    private widget.Button BtnAwalMedisBedah;
    private widget.Button BtnAwalMedisBedahMulut;
    private widget.Button BtnAwalMedisGeriatri;
    private widget.Button BtnAwalMedisHemodialisa;
    private widget.Button BtnAwalMedisIGD;
    private widget.Button BtnAwalMedisIGDPsikiatri;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnAwalMedisKulitKelamin;
    private widget.Button BtnAwalMedisMata;
    private widget.Button BtnAwalMedisNeurologi;
    private widget.Button BtnAwalMedisOrthopedi;
    private widget.Button BtnAwalMedisParu;
    private widget.Button BtnAwalMedisPenyakitDalam;
    private widget.Button BtnAwalMedisPsikiatri;
    private widget.Button BtnAwalMedisRehabMedik;
    private widget.Button BtnAwalMedisTHT;
    private widget.Button BtnAwalTerapiWicara;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnCari;
    private widget.Button BtnCatatan;
    private widget.Button BtnCatatanADIMEGizi;
    private widget.Button BtnCatatanCekGDS;
    private widget.Button BtnCatatanKeperawatan;
    private widget.Button BtnCatatanObservasiIGD;
    private widget.Button BtnCatatanPersalinanan;
    private widget.Button BtnChecklistKriteriaMasukHCU;
    private widget.Button BtnChecklistKriteriaMasukICU;
    private widget.Button BtnChecklistPostOperasi;
    private widget.Button BtnChecklistPreOperasi;
    private widget.Button BtnCopyDiagnosa;
    private widget.Button BtnCopyResep;
    private widget.Button BtnDicom;
    private widget.Button BtnDokumentasiESWL;
    private widget.Button BtnEdit;
    private widget.Button BtnEdukasiPasienKeluarga;
    private widget.Button BtnHapus;
    private widget.Button BtnHasilPemeriksaanUSG;
    private widget.Button BtnHasilPengobatan;
    private widget.Button BtnHasilPengobatan1;
    private widget.Button BtnHasilRadiologi;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnInputKonsul1;
    private widget.Button BtnInputLAB;
    private widget.Button BtnInputObat;
    private widget.Button BtnInputRAD;
    private widget.Button BtnInputTerimaPasienAntarRuang;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnKamar;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnLayananKedokteranFisik;
    private widget.Button BtnMedicalCheckUp;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnMonitoringReaksiTranfusi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnOdontogram;
    private widget.Button BtnPanggilPasien;
    private widget.Button BtnPemantauanEWSNeonatus;
    private widget.Button BtnPemantauanMEOWS;
    private widget.Button BtnPemantauanPEWSAnak;
    private widget.Button BtnPemantauanPEWSDewasa;
    private widget.Button BtnPengkajianRestrain;
    private widget.Button BtnPenilaianKorbanKekerasan;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhAnak;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhDewasa;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhGeriatri;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhLansia;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhNeonatus;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhPsikiatri;
    private widget.Button BtnPenilaianLanjutanSkriningFungsional;
    private widget.Button BtnPenilaianPasienKeracunan;
    private widget.Button BtnPenilaianPasienPenyakitMenular;
    private widget.Button BtnPenilaianPasienTerminal;
    private widget.Button BtnPenilaianPreAnestesi;
    private widget.Button BtnPenilaianPreOperasi;
    private widget.Button BtnPenilaianPsikolog;
    private widget.Button BtnPenilaianTambahanBunuhDiri;
    private widget.Button BtnPenilaianTambahanGeriatri;
    private widget.Button BtnPenilaianTambahanMelarikanDiri;
    private widget.Button BtnPenilaianTambahanPerilakuKekerasan;
    private widget.Button BtnPenilaianUlangNyeri;
    private widget.Button BtnPeriksa;
    private widget.Button BtnPeriksaRad;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPrint;
    private widget.Button BtnRekonsiliasiObat;
    private widget.Button BtnResepLuar;
    private widget.Button BtnResepObat;
    private widget.Button BtnResume;
    private widget.Button BtnRiwayat;
    private widget.Button BtnRujukInternal;
    private widget.Button BtnRujukKeluar;
    private widget.Button BtnSKDP;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekDokter3;
    private widget.Button BtnSeekPegawai;
    private widget.Button BtnSeekPegawai1;
    private widget.Button BtnSeekPegawai2;
    private widget.Button BtnSeekPegawai3;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSeekPetugas3;
    private widget.Button BtnSignInSebelumAnestesi;
    private widget.Button BtnSignOutSebelumMenutupLuka;
    private widget.Button BtnSimpan;
    private widget.Button BtnSkorAldrettePascaAnestesi;
    private widget.Button BtnSkorStewardPascaAnestesi;
    private widget.Button BtnSkriningGiziLanjut;
    private widget.Button BtnSkriningNutrisiAnak;
    private widget.Button BtnSkriningNutrisiDewasa;
    private widget.Button BtnSkriningNutrisiLansia;
    private widget.Button BtnSoapDokter;
    private widget.Button BtnSoapDokter1;
    private widget.Button BtnSuratRujukanBalik;
    private widget.Button BtnTambahTindakan;
    private widget.Button BtnTemplate;
    private widget.Button BtnTemplatePemberianObat1;
    private widget.Button BtnTemplatePemberianObat2;
    private widget.Button BtnTemplatePemeriksaan;
    private widget.Button BtnTemplateResep;
    private widget.Button BtnTimeOutSebelumInsisi;
    private widget.Button BtnTransferAntarRuang;
    private widget.Button BtnTriaseIGD;
    private widget.Button BtnUjiFungsiKFR;
    private widget.Button BtnVerifSbar;
    private widget.Button BtnVerifSbar1;
    private widget.TextArea Catatan;
    private widget.TextArea Catatan1;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkInput2;
    private widget.CekBox ChkInput3;
    private widget.CekBox ChkInput4;
    private widget.CekBox ChkInput5;
    private widget.CekBox ChkInput7;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkTemplate;
    private widget.CekBox ChkTemplatePerawat;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Jabatan;
    private widget.TextBox Jabatan1;
    private widget.TextBox Jabatan2;
    private widget.TextBox Jabatan4;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.TextBox KdDok3;
    private widget.TextBox KdNoRawat;
    private widget.TextBox KdPeg;
    private widget.TextBox KdPeg1;
    private widget.TextBox KdPeg2;
    private widget.TextBox KdPeg3;
    private widget.TextBox KdPeg4;
    private widget.TextBox KdPeg5;
    private widget.Label LCount;
    private widget.TextBox LingkarPerut;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPanel PanelInput1;
    private javax.swing.JPanel PanelInput2;
    private javax.swing.JPanel PanelInput3;
    private javax.swing.JPanel PanelInput4;
    private javax.swing.JPanel PanelInput5;
    private javax.swing.JPanel PanelInput6;
    private javax.swing.JPanel PanelInput7;
    private widget.TextBox ReaksiCode;
    private widget.TextBox ReaksiDisplay;
    private widget.TextBox ReaksiSystem;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll14;
    private widget.ScrollPane Scroll15;
    private widget.ScrollPane Scroll17;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox SpO2;
    private widget.TextBox TAdnexaKanan;
    private widget.TextBox TAdnexaKiri;
    private widget.TextBox TAlergi;
    private widget.TextArea TAssesment;
    private widget.TextArea TAssesment1;
    private widget.TextArea TBackground;
    private widget.TextArea TBackground1;
    private widget.TextBox TBentuk;
    private widget.TextBox TBerat;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TCavumDouglas;
    private widget.TextBox TCavumUteri;
    private widget.TextBox TDenominator;
    private widget.TextBox TDenyut;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextBox TDokter3;
    private widget.TextArea TEvaluasi;
    private widget.TextBox TGCS;
    private widget.TextBox TInspeksi;
    private widget.TextBox TInspeksiVulva;
    private widget.TextBox TInspekuloGine;
    private widget.TextArea TInstruksi;
    private widget.TextArea TKeluhan;
    private widget.TextArea TKeterangan;
    private widget.TextBox TKualitas_dtk;
    private widget.TextBox TKualitas_mnt;
    private widget.TextBox TLetak;
    private widget.TextBox TNadi;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPegawai;
    private widget.TextBox TPegawai1;
    private widget.TextBox TPegawai2;
    private widget.TextBox TPegawai3;
    private widget.TextBox TPegawai4;
    private widget.TextBox TPegawai5;
    private widget.TextBox TPembukaan;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TPenilaian;
    private widget.TextBox TPenurunan;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private widget.TextBox TPerawat3;
    private widget.TextBox TPortio;
    private widget.TextBox TPortioDalam;
    private widget.TextBox TPortioInspekulo;
    private widget.TextArea TRecommendation;
    private widget.TextArea TRecommendation1;
    private widget.TextBox TRespirasi;
    private widget.TextArea TSituation;
    private widget.TextArea TSituation1;
    private widget.TextBox TSondage;
    private widget.TextBox TSuhu;
    private widget.TextBox TTebal;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private widget.TextBox TTinggi_uteri;
    private widget.TextBox TUkuran;
    private widget.TextBox TVulva;
    private widget.TextBox TVulvaInspekulo;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawatTindakanDokter;
    private javax.swing.JTabbedPane TabRawatTindakanDokterPetugas;
    private javax.swing.JTabbedPane TabRawatTindakanPetugas;
    private widget.TextBox TglLahir;
    private widget.TextArea TindakLanjut;
    private widget.TextBox Umur;
    private widget.Button btnJenisAlergi;
    private widget.Button btnPasien;
    private widget.Button btnReaksiAlergi;
    private widget.ComboBox cmbAlbus;
    private widget.ComboBox cmbArah;
    private widget.ComboBox cmbDalam;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbFeto;
    private widget.ComboBox cmbFluksus;
    private widget.ComboBox cmbFluorGine;
    private widget.ComboBox cmbFluxusGine;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJanin;
    private widget.ComboBox cmbKategory;
    private widget.ComboBox cmbKesadaran;
    private widget.ComboBox cmbKetuban;
    private widget.ComboBox cmbKontraksi;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMobilitas;
    private widget.ComboBox cmbNyeriTekan;
    private widget.ComboBox cmbPanggul;
    private widget.ComboBox cmbSeverity;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame14;
    private widget.InternalFrame internalFrame15;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg2;
    private widget.TextBox kdptg3;
    private widget.Label lblTemplate;
    private widget.Label lblTemplate1;
    private laporan.PanelDiagnosa panelDiagnosa1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass17;
    private widget.panelisi panelGlass18;
    private widget.panelisi panelGlass20;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private javax.swing.JSplitPane splitPane;
    private widget.Table tbCatatan;
    private widget.Table tbCatatanPerawatIGD;
    private widget.Table tbObat;
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaanGinekologi;
    private widget.Table tbPemeriksaanObstetri;
    private widget.Table tbPemeriksaanSbar;
    private widget.Table tbPemeriksaanTbak;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    private widget.Table tbTindakan;
    private widget.Table tbTindakan2;
    private widget.Table tbTindakan3;
    // End of variables declaration
    private widget.Button BtnSkorBromagePascaAnestesi, BtnPenilaianPreInduksi, BtnHasilPemeriksaanUSGUrologi,
            BtnHasilPemeriksaanUSGGynecologi;

    private void Btn5SoapActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "Nama Pasien");
        } else {
            soapterakhir.setNoRM(TNoRM.getText(), KdPeg.getText(), "Ralan");
            soapterakhir.setSize(DlgRawatJalanDokter.this.getWidth() - 20, DlgRawatJalanDokter.this.getHeight() - 20);
            soapterakhir.setLocationRelativeTo(DlgRawatJalanDokter.this);
            soapterakhir.setVisible(true);
        }
    }

    private void BtnRiwayatPenunjangActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPenunjang resume = new RMRiwayatPenunjang(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try {
            String sql;
            if (TCari.getText().trim().contains("20") && TCari.getText().trim().length() > 10) {
                sql = "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,dokter.nm_dokter,"
                        + "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat,rawat_jl_dr.kd_jenis_prw, "
                        + "rawat_jl_dr.tarif_tindakandr,rawat_jl_dr.kso,rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.menejemen "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "dokter inner join rawat_jl_dr "
                        + "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "
                        + "where rawat_jl_dr.no_rawat=? order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat desc";
                ps = koneksi.prepareStatement(sql);
                ps.setString(1, TCari.getText());
            } else {
                sql = "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,dokter.nm_dokter,"
                        + "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat,rawat_jl_dr.kd_jenis_prw, "
                        + "rawat_jl_dr.tarif_tindakandr,rawat_jl_dr.kso,rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.menejemen "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "dokter inner join rawat_jl_dr "
                        + "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "
                        + "where rawat_jl_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                        + (TCari.getText().trim().equals("") ? ""
                                : "and (rawat_jl_dr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                        + "jns_perawatan.nm_perawatan like ? or rawat_jl_dr.kd_dokter like ? or dokter.nm_dokter like ? )")
                        + " order by rawat_jl_dr.no_rawat,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat desc";
                ps = koneksi.prepareStatement(sql);
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");
                }
            }
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeDr.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getDouble(9), rs.getString("kd_jenis_prw"),
                            rs.getString("tarif_tindakandr"), rs.getString("kso"),
                            rs.getString("material"), rs.getString("bhp"), rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeDr.getRowCount());
    }

    private void getDataDr() {
        if (tbRawatDr.getSelectedRow() != -1) {
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 1).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 2).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 3).toString());
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 6).toString());
            cmbJam.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 8).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 8).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 8).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(), 7).toString());
        }
    }

    private void tampilPr() {
        Valid.tabelKosong(tabModePr);
        try {
            String sql;
            if (TCari.getText().trim().contains("20") && TCari.getText().trim().length() > 10) {
                sql = "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,"
                        + "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat,rawat_jl_pr.kd_jenis_prw, "
                        + "rawat_jl_pr.tarif_tindakanpr,rawat_jl_pr.kso,rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.menejemen "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "petugas inner join rawat_jl_pr "
                        + "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat=? "
                        + "order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat desc";
                ps2 = koneksi.prepareStatement(sql);
                ps2.setString(1, TCari.getText());
            } else {
                sql = "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,"
                        + "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat,rawat_jl_pr.kd_jenis_prw, "
                        + "rawat_jl_pr.tarif_tindakanpr,rawat_jl_pr.kso,rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.menejemen "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "petugas inner join rawat_jl_pr "
                        + "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_pr.nip=petugas.nip where  "
                        + "rawat_jl_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                        + (TCari.getText().trim().equals("") ? ""
                                : "and (rawat_jl_pr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                        + "jns_perawatan.nm_perawatan like ? or rawat_jl_pr.nip like ? or petugas.nama like ?) ")
                        + "order by rawat_jl_pr.no_rawat,rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat desc";
                ps2 = koneksi.prepareStatement(sql);
                ps2.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps2.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps2.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps2.setString(4, "%" + TCari.getText().trim() + "%");
                    ps2.setString(5, "%" + TCari.getText().trim() + "%");
                    ps2.setString(6, "%" + TCari.getText().trim() + "%");
                    ps2.setString(7, "%" + TCari.getText().trim() + "%");
                    ps2.setString(8, "%" + TCari.getText().trim() + "%");
                    ps2.setString(9, "%" + TCari.getText().trim() + "%");
                }
            }

            try {
                rs = ps2.executeQuery();
                while (rs.next()) {
                    tabModePr.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6),
                            rs.getString(7), rs.getString(8), rs.getDouble(9),
                            rs.getString("kd_jenis_prw"), rs.getString("tarif_tindakanpr"),
                            rs.getString("kso"), rs.getString("material"),
                            rs.getString("bhp"), rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePr.getRowCount());
    }

    private void getDataPr() {
        if (tbRawatPr.getSelectedRow() != -1) {
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 1).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 2).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 3).toString());
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 6).toString());
            cmbJam.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 8).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 8).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 8).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(), 7).toString());
        }
    }

    private void tampilDrPr() {
        Valid.tabelKosong(tabModeDrPr);
        try {
            String sql;
            if (TCari.getText().trim().contains("20") && TCari.getText().trim().length() > 10) {
                sql = "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_drpr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_drpr.kd_dokter,dokter.nm_dokter,"
                        + "rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat,rawat_jl_drpr.kd_jenis_prw, "
                        + "rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,rawat_jl_drpr.kso,rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.menejemen  "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "dokter inner join rawat_jl_drpr inner join petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter and rawat_jl_drpr.nip=petugas.nip "
                        + "where rawat_jl_drpr.no_rawat=? order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat desc";
                ps3 = koneksi.prepareStatement(sql);
                ps3.setString(1, TCari.getText());
            } else {
                sql = "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                        + "concat(rawat_jl_drpr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_drpr.kd_dokter,dokter.nm_dokter,"
                        + "rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat,rawat_jl_drpr.kd_jenis_prw, "
                        + "rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,rawat_jl_drpr.kso,rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.menejemen  "
                        + "from pasien inner join reg_periksa inner join jns_perawatan inner join "
                        + "dokter inner join rawat_jl_drpr inner join petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "
                        + "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter and rawat_jl_drpr.nip=petugas.nip "
                        + "where rawat_jl_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                        + (TCari.getText().trim().equals("") ? ""
                                : "and (rawat_jl_drpr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                        + "jns_perawatan.nm_perawatan like ? or rawat_jl_drpr.kd_dokter like ? or dokter.nm_dokter like ? or "
                                        + "rawat_jl_drpr.nip like ? or petugas.nama like ?)")
                        + " order by rawat_jl_drpr.no_rawat,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat desc";
                ps3 = koneksi.prepareStatement(sql);
                ps3.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps3.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps3.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps3.setString(4, "%" + TCari.getText().trim() + "%");
                    ps3.setString(5, "%" + TCari.getText().trim() + "%");
                    ps3.setString(6, "%" + TCari.getText().trim() + "%");
                    ps3.setString(7, "%" + TCari.getText().trim() + "%");
                    ps3.setString(8, "%" + TCari.getText().trim() + "%");
                    ps3.setString(9, "%" + TCari.getText().trim() + "%");
                    ps3.setString(10, "%" + TCari.getText().trim() + "%");
                    ps3.setString(11, "%" + TCari.getText().trim() + "%");
                }
            }

            try {
                rs = ps3.executeQuery();
                while (rs.next()) {
                    tabModeDrPr.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getDouble(11),
                            rs.getString("kd_jenis_prw"),
                            rs.getString("tarif_tindakandr"), rs.getString("tarif_tindakanpr"), rs.getString("kso"),
                            rs.getString("material"), rs.getString("bhp"), rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeDrPr.getRowCount());
    }

    private void getDataDrPr() {
        if (tbRawatDrPr.getSelectedRow() != -1) {
            TNoRw.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 1).toString());
            TNoRM.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 2).toString());
            TPasien.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 3).toString());
            KdDok2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 5).toString());
            TDokter2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 6).toString());
            kdptg2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 7).toString());
            TPerawat2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 8).toString());
            cmbJam.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 10).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 10).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 10).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(), 9).toString());
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ", TNoRM,
                TNoRw.getText());
        TCariPasien.setText(TNoRM.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select concat(pasien.nm_pasien,' (',pasien.umur,')') from pasien where pasien.no_rkm_medis=? ",
                TPasien, TNoRM.getText());
        Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y')as tgl_lahir from pasien where no_rkm_medis=? ",
                TglLahir, TNoRM.getText());
        Sequel.cariIsi("select pasien.umur from pasien where no_rkm_medis=? ", Umur, TNoRM.getText());

    }

    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        KdDok.setText(
                Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?", norwt));
        TDokter.setText(dokter.tampil3(KdDok.getText()));
        KdDok2.setText(KdDok.getText());
        KdDok3.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText());
        TDokter3.setText(TDokter.getText());
        ChkInput.setSelected(true);
        isForm();
        ChkInput1.setSelected(true);
        isForm2();
        ChkInput2.setSelected(true);
        isForm3();
        ChkInput3.setSelected(true);
        isForm4();
        TabRawatMouseClicked(null);
        tampilSoapPerawat();
        tampilSoapPerawat();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 800));
            panelGlass12.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass12.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void isForm4() {
        if (ChkInput3.isSelected() == true) {
            ChkInput3.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH, 140));
            panelGlass15.setVisible(true);
            ChkInput3.setVisible(true);
        } else if (ChkInput3.isSelected() == false) {
            ChkInput3.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass15.setVisible(false);
            ChkInput3.setVisible(true);
        }
    }

    private void isMenu() {
        if (ChkAccor.isSelected() == true) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(205, HEIGHT));
            FormMenu.setVisible(true);
            ChkAccor.setVisible(true);
        } else if (ChkAccor.isSelected() == false) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
            FormMenu.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    public void isCek() {
        tinggi = 0;
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        BtnTambahTindakan.setEnabled(akses.gettarif_ralan());
        BtnResepObat.setVisible(akses.getresep_dokter());
        BtnCopyResep.setVisible(akses.getresep_dokter());
        BtnTemplatePemeriksaan.setEnabled(akses.gettemplate_pemeriksaan());
        if (akses.getresep_dokter() == true) {
            tinggi = tinggi + 48;
        }
        BtnObatBhp.setVisible(akses.getberi_obat());
        BtnInputObat.setVisible(akses.getberi_obat());
        if (akses.getberi_obat() == true) {
            tinggi = tinggi + 48;
        }
        BtnPermintaanLab.setVisible(akses.getpermintaan_lab());
        if (akses.getpermintaan_lab() == true) {
            tinggi = tinggi + 24;
        }
        BtnBerkasDigital.setVisible(akses.getberkas_digital_perawatan());
        if (akses.getberkas_digital_perawatan() == true) {
            tinggi = tinggi + 24;
        }
        BtnPermintaanRad.setVisible(akses.getpermintaan_radiologi());
        if (akses.getpermintaan_radiologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnKamar.setVisible(akses.getkamar_inap());
        if (akses.getkamar_inap() == true) {
            tinggi = tinggi + 24;
        }
        BtnRujukInternal.setVisible(akses.getrujukan_poli_internal());
        if (akses.getrujukan_poli_internal() == true) {
            tinggi = tinggi + 24;
        }
        BtnRujukKeluar.setVisible(akses.getrujukan_keluar());
        if (akses.getrujukan_keluar() == true) {
            tinggi = tinggi + 24;
        }
        BtnSKDP.setVisible(akses.getskdp_bpjs());
        if (akses.getskdp_bpjs() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatan.setVisible(akses.getcatatan_pasien());
        if (akses.getcatatan_pasien() == true) {
            tinggi = tinggi + 24;
        }
        BtnTriaseIGD.setVisible(akses.getdata_triase_igd());
        if (akses.getdata_triase_igd() == true) {
            tinggi = tinggi + 24;
        }
        BtnResume.setVisible(akses.getdata_resume_pasien());
        if (akses.getdata_resume_pasien() == true) {
            tinggi = tinggi + 24;
        }
        BtnResepLuar.setVisible(akses.getresep_luar());
        if (akses.getresep_luar() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatan.setVisible(akses.getpenilaian_awal_keperawatan_ralan());
        if (akses.getpenilaian_awal_keperawatan_ralan() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanIGD.setVisible(akses.getpenilaian_awal_keperawatan_igd());
        if (akses.getpenilaian_awal_keperawatan_igd() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanGigi.setVisible(akses.getpenilaian_awal_keperawatan_gigi());
        if (akses.getpenilaian_awal_keperawatan_gigi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanKandungan.setVisible(akses.getpenilaian_awal_keperawatan_kebidanan());
        if (akses.getpenilaian_awal_keperawatan_kebidanan() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanAnak.setVisible(akses.getpenilaian_awal_keperawatan_anak());
        if (akses.getpenilaian_awal_keperawatan_anak() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanPsikiatri.setVisible(akses.getpenilaian_awal_keperawatan_psikiatri());
        if (akses.getpenilaian_awal_keperawatan_psikiatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedis.setVisible(akses.getpenilaian_awal_medis_ralan());
        if (akses.getpenilaian_awal_medis_ralan() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisKandungan.setVisible(akses.getpenilaian_awal_medis_ralan_kebidanan());
        if (akses.getpenilaian_awal_medis_ralan_kebidanan() == true) {
            tinggi = tinggi + 24;
        }
        BtnRiwayat.setVisible(akses.getresume_pasien());
        Btn5Soap.setEnabled(akses.getresume_pasien());
        if (akses.getresume_pasien() == true) {
            tinggi = tinggi + 24;
        }
        BtnJadwalOperasi.setVisible(akses.getbooking_operasi());
        if (akses.getbooking_operasi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisIGD.setVisible(akses.getpenilaian_awal_medis_igd());
        if (akses.getpenilaian_awal_medis_igd() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisAnak.setVisible(akses.getpenilaian_awal_medis_ralan_anak());
        if (akses.getpenilaian_awal_medis_ralan_anak() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalFisioterapi.setVisible(akses.getpenilaian_fisioterapi());
        if (akses.getpenilaian_fisioterapi() == true) {
            tinggi = tinggi + 24;
        }
        BtnMedicalCheckUp.setVisible(akses.getpenilaian_mcu());
        if (akses.getpenilaian_mcu() == true) {
            tinggi = tinggi + 24;
        }
        BtnUjiFungsiKFR.setVisible(akses.getuji_fungsi_kfr());
        if (akses.getuji_fungsi_kfr() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatanObservasiIGD.setVisible(akses.getcatatan_observasi_igd());
        if (akses.getcatatan_observasi_igd() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisTHT.setVisible(akses.getpenilaian_awal_medis_ralan_tht());
        if (akses.getpenilaian_awal_medis_ralan_tht() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisPsikiatri.setVisible(akses.getpenilaian_awal_medis_ralan_psikiatri());
        if (akses.getpenilaian_awal_medis_ralan_psikiatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisPenyakitDalam.setVisible(akses.getpenilaian_awal_medis_ralan_penyakit_dalam());
        if (akses.getpenilaian_awal_medis_ralan_penyakit_dalam() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisMata.setVisible(akses.getpenilaian_awal_medis_ralan_mata());
        if (akses.getpenilaian_awal_medis_ralan_mata() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisNeurologi.setVisible(akses.getpenilaian_awal_medis_ralan_neurologi());
        if (akses.getpenilaian_awal_medis_ralan_neurologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisOrthopedi.setVisible(akses.getpenilaian_awal_medis_ralan_orthopedi());
        if (akses.getpenilaian_awal_medis_ralan_orthopedi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisBedah.setVisible(akses.getpenilaian_awal_medis_ralan_bedah());
        if (akses.getpenilaian_awal_medis_ralan_bedah() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPsikolog.setVisible(akses.getpenilaian_psikologi());
        if (akses.getpenilaian_psikologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPemantauanPEWSAnak.setVisible(akses.getpemantauan_pews_anak());
        if (akses.getpenilaian_psikologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPreOperasi.setVisible(akses.getpenilaian_pre_operasi());
        if (akses.getpenilaian_pre_operasi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPreAnestesi.setVisible(akses.getpenilaian_pre_anestesi());
        if (akses.getpenilaian_pre_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhDewasa.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa());
        if (akses.getpenilaian_lanjutan_resiko_jatuh_dewasa() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhAnak.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_anak());
        if (akses.getpenilaian_lanjutan_resiko_jatuh_anak() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisGeriatri.setVisible(akses.getpenilaian_awal_medis_ralan_geriatri());
        if (akses.getpenilaian_awal_medis_ralan_geriatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianTambahanGeriatri.setVisible(akses.getpenilaian_tambahan_pasien_geriatri());
        if (akses.getpenilaian_tambahan_pasien_geriatri() == true) {
            tinggi = tinggi + 24;
        }

        BtnSkriningNutrisiDewasa.setVisible(akses.getskrining_nutrisi_dewasa());
        if (akses.getskrining_nutrisi_dewasa() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkriningNutrisiLansia.setVisible(akses.getskrining_nutrisi_lansia());
        if (akses.getskrining_nutrisi_lansia() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkriningNutrisiAnak.setVisible(akses.getskrining_nutrisi_anak());
        if (akses.getskrining_nutrisi_anak() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkriningGiziLanjut.setVisible(akses.getskrining_gizi());
        if (akses.getskrining_gizi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if (akses.getasuhan_gizi() == true) {
            tinggi = tinggi + 24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if (akses.getmonitoring_asuhan_gizi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSG.setVisible(akses.gethasil_pemeriksaan_usg());
        if (akses.gethasil_pemeriksaan_usg() == true) {
            tinggi = tinggi + 24;
        }
        BtnKonselingFarmasi.setVisible(akses.getkonseling_farmasi());
        if (akses.getkonseling_farmasi() == true) {
            tinggi = tinggi + 24;
        }
        BtnInformasiObat.setVisible(akses.getpelayanan_informasi_obat());
        if (akses.getpelayanan_informasi_obat() == true) {
            tinggi = tinggi + 24;
        }
        BtnTransferAntarRuang.setVisible(akses.gettransfer_pasien_antar_ruang());
        if (akses.gettransfer_pasien_antar_ruang() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatanCekGDS.setVisible(akses.getcatatan_cek_gds());
        if (akses.getcatatan_cek_gds() == true) {
            tinggi = tinggi + 24;
        }
        BtnChecklistPreOperasi.setVisible(akses.getchecklist_pre_operasi());
        if (akses.getchecklist_pre_operasi() == true) {
            tinggi = tinggi + 24;
        }
        BtnSignInSebelumAnestesi.setVisible(akses.getsignin_sebelum_anestesi());
        if (akses.getsignin_sebelum_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnTimeOutSebelumInsisi.setVisible(akses.gettimeout_sebelum_insisi());
        if (akses.gettimeout_sebelum_insisi() == true) {
            tinggi = tinggi + 24;
        }
        BtnSignOutSebelumMenutupLuka.setVisible(akses.getsignout_sebelum_menutup_luka());
        if (akses.getsignout_sebelum_menutup_luka() == true) {
            tinggi = tinggi + 24;
        }
        BtnChecklistPostOperasi.setVisible(akses.getchecklist_post_operasi());
        if (akses.getchecklist_post_operasi() == true) {
            tinggi = tinggi + 24;
        }
        BtnRekonsiliasiObat.setVisible(akses.getrekonsiliasi_obat());
        if (akses.getrekonsiliasi_obat() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPasienTerminal.setVisible(akses.getpenilaian_pasien_terminal());
        if (akses.getpenilaian_pasien_terminal() == true) {
            tinggi = tinggi + 24;
        }
        BtnMonitoringReaksiTranfusi.setVisible(akses.getmonitoring_reaksi_tranfusi());
        if (akses.getmonitoring_reaksi_tranfusi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianKorbanKekerasan.setVisible(akses.getpenilaian_korban_kekerasan());
        if (akses.getpenilaian_korban_kekerasan() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhLansia.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_lansia());
        if (akses.getpenilaian_lanjutan_resiko_jatuh_lansia() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPasienPenyakitMenular.setVisible(akses.getpenilaian_pasien_penyakit_menular());
        if (akses.getpenilaian_pasien_penyakit_menular() == true) {
            tinggi = tinggi + 24;
        }
        BtnEdukasiPasienKeluarga.setVisible(akses.getedukasi_pasien_keluarga_rj());
        if (akses.getedukasi_pasien_keluarga_rj() == true) {
            tinggi = tinggi + 24;
        }
        BtnPemantauanPEWSDewasa.setVisible(akses.getpemantauan_pews_dewasa());
        if (akses.getpemantauan_pews_dewasa() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianTambahanBunuhDiri.setVisible(akses.getpenilaian_tambahan_bunuh_diri());
        if (akses.getpenilaian_tambahan_bunuh_diri() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianTambahanPerilakuKekerasan.setVisible(akses.getpenilaian_tambahan_perilaku_kekerasan());
        if (akses.getpenilaian_tambahan_perilaku_kekerasan() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianTambahanMelarikanDiri.setVisible(akses.getpenilaian_tambahan_beresiko_melarikan_diri());
        if (akses.getpenilaian_tambahan_beresiko_melarikan_diri() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisBedahMulut.setVisible(akses.getpenilaian_awal_medis_ralan_bedah_mulut());
        if (akses.getpenilaian_awal_medis_ralan_bedah_mulut() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPasienKeracunan.setVisible(akses.getpenilaian_pasien_keracunan());
        if (akses.getpenilaian_pasien_keracunan() == true) {
            tinggi = tinggi + 24;
        }
        BtnPemantauanMEOWS.setVisible(akses.getpemantauan_meows_obstetri());
        if (akses.getpemantauan_meows_obstetri() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatanADIMEGizi.setVisible(akses.getcatatan_adime_gizi());
        if (akses.getcatatan_adime_gizi() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalKeperawatanGeriatri.setVisible(akses.getpenilaian_awal_keperawatan_ralan_geriatri());
        if (akses.getpenilaian_awal_keperawatan_ralan_geriatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnChecklistKriteriaMasukHCU.setVisible(akses.getchecklist_kriteria_masuk_hcu());
        if (akses.getchecklist_kriteria_masuk_hcu() == true) {
            tinggi = tinggi + 24;
        }
        BtnDokumentasiESWL.setVisible(akses.gethasil_tindakan_eswl());
        if (akses.gethasil_tindakan_eswl() == true) {
            tinggi = tinggi + 24;
        }
        BtnChecklistKriteriaMasukICU.setVisible(akses.getchecklist_kriteria_masuk_icu());
        if (akses.getchecklist_kriteria_masuk_icu() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setVisible(akses.getpenilaian_risiko_jatuh_neonatus());
        if (akses.getpenilaian_risiko_jatuh_neonatus() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri());
        if (akses.getpenilaian_lanjutan_resiko_jatuh_geriatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnPemantauanEWSNeonatus.setVisible(akses.getpemantauan_ews_neonatus());
        if (akses.getpemantauan_ews_neonatus() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisKulitKelamin.setVisible(akses.getpenilaian_awal_medis_ralan_kulit_kelamin());
        if (akses.getpenilaian_awal_medis_ralan_kulit_kelamin() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisHemodialisa.setVisible(akses.getpenilaian_medis_ralan_hemodialisa());
        if (akses.getpenilaian_medis_ralan_hemodialisa() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri());
        if (akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianLanjutanSkriningFungsional.setVisible(akses.getpenilaian_lanjutan_skrining_fungsional());
        if (akses.getpenilaian_lanjutan_skrining_fungsional() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisRehabMedik.setVisible(akses.getpenilaian_medis_ralan_rehab_medik());
        if (akses.getpenilaian_medis_ralan_rehab_medik() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisIGDPsikiatri.setVisible(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
        if (akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianUlangNyeri.setVisible(akses.getpenilaian_ulang_nyeri());
        if (akses.getpenilaian_ulang_nyeri() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalTerapiWicara.setVisible(akses.getpenilaian_terapi_wicara());
        if (akses.getpenilaian_terapi_wicara() == true) {
            tinggi = tinggi + 24;
        }
        BtnPengkajianRestrain.setVisible(akses.getpengkajian_restrain());
        if (akses.getpengkajian_restrain() == true) {
            tinggi = tinggi + 24;
        }
        BtnAwalMedisParu.setVisible(akses.getpenilaian_awal_medis_ralan_paru());
        if (akses.getpenilaian_awal_medis_ralan_paru() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatanKeperawatan.setVisible(akses.getcatatan_keperawatan_ralan());
        if (akses.getcatatan_keperawatan_ralan() == true) {
            tinggi = tinggi + 24;
        }
        BtnCatatanPersalinanan.setVisible(akses.getcatatan_persalinan());
        if (akses.getcatatan_persalinan() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkorAldrettePascaAnestesi.setVisible(akses.getskor_aldrette_pasca_anestesi());
        if (akses.getskor_aldrette_pasca_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkorStewardPascaAnestesi.setVisible(akses.getskor_steward_pasca_anestesi());
        if (akses.getskor_steward_pasca_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnSkorBromagePascaAnestesi.setVisible(akses.getskor_bromage_pasca_anestesi());
        if (akses.getskor_bromage_pasca_anestesi() == true) {
            tinggi = tinggi + 24;
        }
        BtnPenilaianPreInduksi.setVisible(akses.getpenilaian_pre_induksi());
        if (akses.getpenilaian_pre_induksi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSGUrologi.setVisible(akses.gethasil_usg_urologi());
        if (akses.gethasil_usg_urologi() == true) {
            tinggi = tinggi + 24;
        }
        BtnHasilPemeriksaanUSGGynecologi.setVisible(akses.gethasil_usg_gynecologi());
        if (akses.gethasil_usg_gynecologi() == true) {
            tinggi = tinggi + 24;
        }
        FormMenu.setPreferredSize(new Dimension(195, (tinggi + 10)));
        TCari.setPreferredSize(new Dimension(207, 23));

        if (akses.getjml2() >= 1) {
            KdPeg.setText(akses.getkode());
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
            Jabatan.setText(pegawai.tampilJbatan(KdPeg.getText()));
        }

        // Atur urutan tombol FormMenu sesuai spesialisasi dokter
        try {
            aturUrutanTombolFormMenu();
        } catch (Exception e) {
            System.out.println("Notifikasi aturUrutanTombolFormMenu: " + e);
        }
    }

    private void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try {
            ps4 = koneksi
                    .prepareStatement("select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, "
                            + "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, "
                            + "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "
                            + "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,"
                            + "pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn "
                            + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "
                            + "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "
                            + "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (pemeriksaan_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                            + "pemeriksaan_ralan.alergi like ? or pemeriksaan_ralan.keluhan like ? or pemeriksaan_ralan.penilaian like ? or "
                                            + "pemeriksaan_ralan.pemeriksaan like ? or pegawai.nama like ?) ")
                            + "order by pemeriksaan_ralan.no_rawat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat desc");
            try {
                ps4.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps4.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps4.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps4.setString(4, "%" + TCari.getText().trim() + "%");
                    ps4.setString(5, "%" + TCari.getText().trim() + "%");
                    ps4.setString(6, "%" + TCari.getText().trim() + "%");
                    ps4.setString(7, "%" + TCari.getText().trim() + "%");
                    ps4.setString(8, "%" + TCari.getText().trim() + "%");
                    ps4.setString(9, "%" + TCari.getText().trim() + "%");
                    ps4.setString(10, "%" + TCari.getText().trim() + "%");
                    ps4.setString(11, "%" + TCari.getText().trim() + "%");
                }
                rs = ps4.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaan.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                            rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                            rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),
                            rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23),
                            rs.getString(24), rs.getString(25)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePemeriksaan.getRowCount());
    }

    private void tampilCatatan() {
        Valid.tabelKosong(TabModeCatatan);
        try {
            ps4 = koneksi
                    .prepareStatement("select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"
                            + "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "
                            + "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and catatan_perawatan.kd_dokter=dokter.kd_dokter where  "
                            + "catatan_perawatan.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (catatan_perawatan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "
                                            + "catatan_perawatan.catatan like ? or catatan_perawatan.kd_dokter like ? or dokter.nm_dokter like ?) ")
                            + "order by catatan_perawatan.no_rawat,catatan_perawatan.tanggal,catatan_perawatan.jam desc");
            try {
                ps4.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps4.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps4.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps4.setString(4, "%" + TCari.getText().trim() + "%");
                    ps4.setString(5, "%" + TCari.getText().trim() + "%");
                    ps4.setString(6, "%" + TCari.getText().trim() + "%");
                    ps4.setString(7, "%" + TCari.getText().trim() + "%");
                    ps4.setString(8, "%" + TCari.getText().trim() + "%");
                    ps4.setString(9, "%" + TCari.getText().trim() + "%");
                }
                rs = ps4.executeQuery();
                while (rs.next()) {
                    TabModeCatatan.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getString(7), rs.getString(8)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Catatan : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + TabModeCatatan.getRowCount());
    }

    private void getDataPemeriksaan() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 3).toString());
            TSuhu.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 6).toString());
            TTensi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 7).toString());
            TNadi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 8).toString());
            TRespirasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 9).toString());
            TTinggi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 10).toString());
            TBerat.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 11).toString());
            SpO2.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 12).toString());
            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 13).toString());
            String kesadaranValue = tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 14).toString();
            cmbKesadaran.setSelectedItem(kesadaranValue);
            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 15).toString());
            TKeluhan.setCaretPosition(0);
            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 16).toString());
            TPemeriksaan.setCaretPosition(0);
            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 17).toString());
            LingkarPerut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 18).toString());
            if (!tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 19).toString().equals("")) {
                TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 19).toString());
            }
            if (!tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 20).toString().equals("")) {
                TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 20).toString());
            }
            if (!tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 21).toString().equals("")) {
                TInstruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 21).toString());
            }
            if (!tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 22).toString().equals("")) {
                TEvaluasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 22).toString());
            }
            cmbJam.setSelectedItem(
                    tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(
                    tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(
                    tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 4).toString());
        }
    }

    private void getDataCatatan() {
        if (tbCatatan.getSelectedRow() != -1) {
            TNoRw.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 1).toString());
            TNoRM.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 2).toString());
            TPasien.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 3).toString());
            KdDok3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 6).toString());
            TDokter3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 7).toString());
            Catatan.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 8).toString());
            cmbJam.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbCatatan.getValueAt(tbCatatan.getSelectedRow(), 4).toString());
        }
    }

    private void tampilPemeriksaanObstetri() {
        Valid.tabelKosong(tabModeObstetri);
        try {
            ps5 = koneksi.prepareStatement(
                    "select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, "
                            + "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, "
                            + "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, "
                            + "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan,"
                            + "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto "
                            + "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "
                            + "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                            + "pemeriksaan_obstetri_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (pemeriksaan_obstetri_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "
                                            + "pemeriksaan_obstetri_ralan.tinggi_uteri like ? or pemeriksaan_obstetri_ralan.janin like ? or pemeriksaan_obstetri_ralan.letak like ?) ")
                            + "order by pemeriksaan_obstetri_ralan.no_rawat,pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat desc");
            try {
                ps5.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps5.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps5.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps5.setString(4, "%" + TCari.getText().trim() + "%");
                    ps5.setString(5, "%" + TCari.getText().trim() + "%");
                    ps5.setString(6, "%" + TCari.getText().trim() + "%");
                    ps5.setString(7, "%" + TCari.getText().trim() + "%");
                    ps5.setString(8, "%" + TCari.getText().trim() + "%");
                    ps5.setString(9, "%" + TCari.getText().trim() + "%");
                }
                rs = ps5.executeQuery();
                while (rs.next()) {
                    tabModeObstetri.addRow(new Object[] {
                            false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                            rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("tinggi_uteri"),
                            rs.getString("janin"), rs.getString("letak"), rs.getString("panggul"),
                            rs.getString("denyut"), rs.getString("kontraksi"), rs.getString("kualitas_mnt"),
                            rs.getString("kualitas_dtk"), rs.getString("fluksus"), rs.getString("albus"),
                            rs.getString("vulva"), rs.getString("portio"), rs.getString("dalam"),
                            rs.getString("tebal"), rs.getString("arah"), rs.getString("pembukaan"),
                            rs.getString("penurunan"), rs.getString("denominator"), rs.getString("ketuban"),
                            rs.getString("feto")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps5 != null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" + e);
        }
        LCount.setText("" + tabModeObstetri.getRowCount());
    }

    private void getDataPemeriksaanObstetri() {
        if (tbPemeriksaanObstetri.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 3).toString());
            Valid.SetTgl(DTPTgl,
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 5)
                    .toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 5)
                    .toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 5)
                    .toString().substring(6, 8));
            TTinggi_uteri
                    .setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 6).toString());
            cmbJanin.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 7).toString());
            TLetak.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 8).toString());
            cmbPanggul.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 9).toString());
            TDenyut.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 10).toString());
            cmbKontraksi.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 11).toString());
            TKualitas_mnt
                    .setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 12).toString());
            TKualitas_dtk
                    .setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 13).toString());
            cmbFluksus.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 14).toString());
            cmbAlbus.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 15).toString());
            TVulva.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 16).toString());
            TPortio.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 17).toString());
            cmbDalam.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 18).toString());
            TTebal.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 19).toString());
            cmbArah.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 20).toString());
            TPembukaan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 21).toString());
            TPenurunan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 22).toString());
            TDenominator
                    .setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 23).toString());
            cmbKetuban.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 24).toString());
            cmbFeto.setSelectedItem(
                    tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(), 25).toString());
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                // Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
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
                // tampil_jam.setText(" " + jam + " : " + menit + " : " + detik + " ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void setNoRm(String norwt, Date tgl1, Date tgl2, String kodedokter, String namadokter) {
        TNoRw.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
        ChkInput1.setSelected(true);
        isForm2();
        ChkInput2.setSelected(true);
        isForm3();
        ChkInput3.setSelected(true);
        isForm4();
        KdDok.setText(kodedokter);
        KdDok2.setText(kodedokter);
        KdDok3.setText(kodedokter);
        TDokter.setText(namadokter);
        TDokter2.setText(namadokter);
        TDokter3.setText(namadokter);
        tampilSoapPerawat();
        tampilSoapPerawat();
    }

    public void SetPoli(String KodePoli) {
        this.kode_poli = KodePoli;
    }

    public void SetPj(String KodePj) {
        this.kd_pj = KodePj;
    }

    private void isForm2() {
        if (ChkInput1.isSelected() == true) {
            ChkInput1.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH, 156));
            panelGlass13.setVisible(true);
            ChkInput1.setVisible(true);
        } else if (ChkInput1.isSelected() == false) {
            ChkInput1.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass13.setVisible(false);
            ChkInput1.setVisible(true);
        }
    }

    private void tampilPemeriksaanGinekologi() {
        Valid.tabelKosong(tabModeGinekologi);
        try {
            ps6 = koneksi.prepareStatement(
                    "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, "
                            + "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, "
                            + "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, "
                            + "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri,"
                            + "pemeriksaan_ginekologi_ralan.cavum_douglas "
                            + "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "
                            + "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                            + "pemeriksaan_ginekologi_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (pemeriksaan_ginekologi_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "
                                            + "pasien.nm_pasien like ? or  pemeriksaan_ginekologi_ralan.inspeksi like ? or pemeriksaan_ginekologi_ralan.inspeksi_vulva like ? or "
                                            + "pemeriksaan_ginekologi_ralan.inspekulo_gine like ?) ")
                            + "order by pemeriksaan_ginekologi_ralan.no_rawat,pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat desc");
            try {
                ps6.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps6.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps6.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps6.setString(4, "%" + TCari.getText().trim() + "%");
                    ps6.setString(5, "%" + TCari.getText().trim() + "%");
                    ps6.setString(6, "%" + TCari.getText().trim() + "%");
                    ps6.setString(7, "%" + TCari.getText().trim() + "%");
                    ps6.setString(8, "%" + TCari.getText().trim() + "%");
                    ps6.setString(9, "%" + TCari.getText().trim() + "%");
                }
                rs = ps6.executeQuery();
                while (rs.next()) {
                    tabModeGinekologi.addRow(new Object[] {
                            false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                            rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("inspeksi"),
                            rs.getString("inspeksi_vulva"), rs.getString("inspekulo_gine"), rs.getString("fluxus_gine"),
                            rs.getString("fluor_gine"), rs.getString("vulva_inspekulo"),
                            rs.getString("portio_inspekulo"),
                            rs.getString("sondage"), rs.getString("portio_dalam"), rs.getString("bentuk"),
                            rs.getString("cavum_uteri"), rs.getString("mobilitas"), rs.getString("ukuran"),
                            rs.getString("nyeri_tekan"), rs.getString("adnexa_kanan"), rs.getString("adnexa_kiri"),
                            rs.getString("cavum_douglas")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps5 != null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" + e);
        }
        LCount.setText("" + tabModeGinekologi.getRowCount());
    }

    private void getDataPemeriksaanGinekologi() {
        if (tbPemeriksaanGinekologi.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 3).toString());
            Valid.SetTgl(DTPTgl,
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 5)
                    .toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 5)
                    .toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 5)
                    .toString().substring(6, 8));
            TInspeksi.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 6).toString());
            TInspeksiVulva.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 7).toString());
            TInspekuloGine.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 8).toString());
            cmbFluxusGine.setSelectedItem(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 9).toString());
            cmbFluorGine.setSelectedItem(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 10).toString());
            TVulvaInspekulo.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 11).toString());
            TPortioInspekulo.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 12).toString());
            TSondage.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 13).toString());
            TPortioDalam.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 14).toString());
            TBentuk.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 15).toString());
            TCavumUteri.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 16).toString());
            cmbMobilitas.setSelectedItem(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 17).toString());
            TUkuran.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 18).toString());
            cmbNyeriTekan.setSelectedItem(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 19).toString());
            TAdnexaKanan.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 20).toString());
            TAdnexaKiri.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 21).toString());
            TCavumDouglas.setText(
                    tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(), 22).toString());
        }
    }

    private void isForm3() {
        if (ChkInput2.isSelected() == true) {
            ChkInput2.setVisible(false);
            PanelInput2.setPreferredSize(new Dimension(WIDTH, 246));
            panelGlass14.setVisible(true);
            ChkInput2.setVisible(true);
        } else if (ChkInput2.isSelected() == false) {
            ChkInput2.setVisible(false);
            PanelInput2.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass14.setVisible(false);
            ChkInput2.setVisible(true);
        }
    }

    private void tampilTindakanDr() {
        try {
            jml = 0;
            for (i = 0; i < TabModeTindakan.getRowCount(); i++) {
                if (TabModeTindakan.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];
            kategori = null;
            kategori = new String[jml];
            totaltnd = null;
            totaltnd = new double[jml];
            bagianrs = null;
            bagianrs = new double[jml];
            bhp = null;
            bhp = new double[jml];
            jmdokter = null;
            jmdokter = new double[jml];
            jmperawat = null;
            jmperawat = new double[jml];
            kso = null;
            kso = new double[jml];
            menejemen = null;
            menejemen = new double[jml];

            index = 0;
            for (i = 0; i < TabModeTindakan.getRowCount(); i++) {
                if (TabModeTindakan.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = TabModeTindakan.getValueAt(i, 1).toString();
                    nama[index] = TabModeTindakan.getValueAt(i, 2).toString();
                    kategori[index] = TabModeTindakan.getValueAt(i, 3).toString();
                    totaltnd[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 4).toString());
                    bagianrs[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 5).toString());
                    bhp[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 6).toString());
                    jmdokter[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 7).toString());
                    jmperawat[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 8).toString());
                    kso[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 9).toString());
                    menejemen[index] = Double.parseDouble(TabModeTindakan.getValueAt(i, 10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(TabModeTindakan);

            for (i = 0; i < jml; i++) {
                TabModeTindakan.addRow(new Object[] {
                        pilih[i], kode[i], nama[i], kategori[i], totaltnd[i], bagianrs[i], bhp[i], jmdokter[i],
                        jmperawat[i], kso[i], menejemen[i]
                });
            }

            if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            }

            try {
                if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, kode_poli.trim());
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(4, kd_pj.trim());
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(7, kd_pj.trim());
                    pstindakan.setString(8, kode_poli.trim());
                    pstindakan.setString(9, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kd_pj.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kd_pj.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, kode_poli.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kode_poli.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    TabModeTindakan.addRow(new Object[] {
                            false, rstindakan.getString(1), rstindakan.getString(2), rstindakan.getString(3),
                            rstindakan.getDouble("total_byrdr"), rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"), rstindakan.getDouble("tarif_tindakandr"),
                            rstindakan.getDouble("tarif_tindakanpr"), rstindakan.getDouble("kso"),
                            rstindakan.getDouble("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + TabModeTindakan.getRowCount());
    }

    private void tampilTindakanPr() {
        try {
            jml = 0;
            for (i = 0; i < TabModeTindakan2.getRowCount(); i++) {
                if (TabModeTindakan2.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];
            kategori = null;
            kategori = new String[jml];
            totaltnd = null;
            totaltnd = new double[jml];
            bagianrs = null;
            bagianrs = new double[jml];
            bhp = null;
            bhp = new double[jml];
            jmdokter = null;
            jmdokter = new double[jml];
            jmperawat = null;
            jmperawat = new double[jml];
            kso = null;
            kso = new double[jml];
            menejemen = null;
            menejemen = new double[jml];

            index = 0;
            for (i = 0; i < TabModeTindakan2.getRowCount(); i++) {
                if (TabModeTindakan2.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = TabModeTindakan2.getValueAt(i, 1).toString();
                    nama[index] = TabModeTindakan2.getValueAt(i, 2).toString();
                    kategori[index] = TabModeTindakan2.getValueAt(i, 3).toString();
                    totaltnd[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 4).toString());
                    bagianrs[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 5).toString());
                    bhp[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 6).toString());
                    jmdokter[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 7).toString());
                    jmperawat[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 8).toString());
                    kso[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 9).toString());
                    menejemen[index] = Double.parseDouble(TabModeTindakan2.getValueAt(i, 10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(TabModeTindakan2);

            for (i = 0; i < jml; i++) {
                TabModeTindakan2.addRow(new Object[] {
                        pilih[i], kode[i], nama[i], kategori[i], totaltnd[i], bagianrs[i], bhp[i], jmdokter[i],
                        jmperawat[i], kso[i], menejemen[i]
                });
            }

            if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            }

            try {
                if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, kode_poli.trim());
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(4, kd_pj.trim());
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(7, kd_pj.trim());
                    pstindakan.setString(8, kode_poli.trim());
                    pstindakan.setString(9, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kd_pj.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kd_pj.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, kode_poli.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kode_poli.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    TabModeTindakan2.addRow(new Object[] {
                            false, rstindakan.getString(1), rstindakan.getString(2), rstindakan.getString(3),
                            rstindakan.getDouble("total_byrpr"), rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"), rstindakan.getDouble("tarif_tindakandr"),
                            rstindakan.getDouble("tarif_tindakanpr"), rstindakan.getDouble("kso"),
                            rstindakan.getDouble("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + TabModeTindakan2.getRowCount());
    }

    private void tampilTindakanDrPr() {
        try {
            jml = 0;
            for (i = 0; i < TabModeTindakan3.getRowCount(); i++) {
                if (TabModeTindakan3.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];
            kategori = null;
            kategori = new String[jml];
            totaltnd = null;
            totaltnd = new double[jml];
            bagianrs = null;
            bagianrs = new double[jml];
            bhp = null;
            bhp = new double[jml];
            jmdokter = null;
            jmdokter = new double[jml];
            jmperawat = null;
            jmperawat = new double[jml];
            kso = null;
            kso = new double[jml];
            menejemen = null;
            menejemen = new double[jml];

            index = 0;
            for (i = 0; i < TabModeTindakan3.getRowCount(); i++) {
                if (TabModeTindakan3.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = TabModeTindakan3.getValueAt(i, 1).toString();
                    nama[index] = TabModeTindakan3.getValueAt(i, 2).toString();
                    kategori[index] = TabModeTindakan3.getValueAt(i, 3).toString();
                    totaltnd[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 4).toString());
                    bagianrs[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 5).toString());
                    bhp[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 6).toString());
                    jmdokter[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 7).toString());
                    jmperawat[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 8).toString());
                    kso[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 9).toString());
                    menejemen[index] = Double.parseDouble(TabModeTindakan3.getValueAt(i, 10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(TabModeTindakan3);

            for (i = 0; i < jml; i++) {
                TabModeTindakan3.addRow(new Object[] {
                        pilih[i], kode[i], nama[i], kategori[i], totaltnd[i], bagianrs[i], bhp[i], jmdokter[i],
                        jmperawat[i], kso[i], menejemen[i]
                });
            }

            if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                pstindakan = koneksi.prepareStatement(
                        "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"
                                + "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"
                                + "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "
                                + "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "
                                + "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "
                                + "jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");
            }

            try {
                if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, kode_poli.trim());
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(4, kd_pj.trim());
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(7, kd_pj.trim());
                    pstindakan.setString(8, kode_poli.trim());
                    pstindakan.setString(9, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("Yes")) {
                    pstindakan.setString(1, kd_pj.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kd_pj.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kd_pj.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("Yes") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, kode_poli.trim());
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, kode_poli.trim());
                    pstindakan.setString(4, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(5, kode_poli.trim());
                    pstindakan.setString(6, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                } else if (poli_ralan.equals("No") && cara_bayar_ralan.equals("No")) {
                    pstindakan.setString(1, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(2, "%" + TCari.getText().trim() + "%");
                    pstindakan.setString(3, "%" + TCari.getText().trim() + "%");
                    rstindakan = pstindakan.executeQuery();
                }

                while (rstindakan.next()) {
                    TabModeTindakan3.addRow(new Object[] {
                            false, rstindakan.getString(1), rstindakan.getString(2), rstindakan.getString(3),
                            rstindakan.getDouble("total_byrdrpr"), rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"), rstindakan.getDouble("tarif_tindakandr"),
                            rstindakan.getDouble("tarif_tindakanpr"), rstindakan.getDouble("kso"),
                            rstindakan.getDouble("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rstindakan != null) {
                    rstindakan.close();
                }
                if (pstindakan != null) {
                    pstindakan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + TabModeTindakan3.getRowCount());
    }

    private void TampilkanData() {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilkanPenangananDokter();
                break;
            case 1:
                tampilkanPenangananPetugas();
                break;
            case 2:
                tampilkanPenangananDokterPetugas();
                break;
            case 3:
                tampilPemeriksaan();
                break;
            case 4:
                tampilPemeriksaanObstetri();
                break;
            case 5:
                tampilPemeriksaanGinekologi();
                break;
            case 6:
                if (akses.getdiagnosa_pasien() == true) {
                    panelDiagnosa1.setRM(TNoRw.getText(), TNoRM.getText(),
                            Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                            Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord() + "");
                }
                break;
            case 7:
                if (akses.getcatatan_perawatan() == true) {
                    tampilCatatan();
                }
                break;
            case 11:
                tampil();
                break;
            default:
                break;
        }
    }

    private void tampilkanPenangananDokter() {
        if (TabRawatTindakanDokter.getSelectedIndex() == 0) {
            tampilTindakanDr();
        } else if (TabRawatTindakanDokter.getSelectedIndex() == 1) {
            tampilDr();
        }
    }

    private void SimpanPenangananDokter() {
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses = true;
            ttljmdokter = 0;
            ttlkso = 0;
            ttlpendapatan = 0;
            ttljasasarana = 0;
            ttlbhp = 0;
            ttlmenejemen = 0;
            for (i = 0; i < tbTindakan.getRowCount(); i++) {
                if (tbTindakan.getValueAt(i, 0).toString().equals("true")) {
                    if (Sequel.menyimpantf("rawat_jl_dr", "?,?,?,?,?,?,?,?,?,?,?,'Belum'", "Tindakan", 11,
                            new String[] {
                                    TNoRw.getText(), tbTindakan.getValueAt(i, 1).toString(), KdDok.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    tbTindakan.getValueAt(i, 5).toString(),
                                    tbTindakan.getValueAt(i, 6).toString(), tbTindakan.getValueAt(i, 7).toString(),
                                    tbTindakan.getValueAt(i, 9).toString(),
                                    tbTindakan.getValueAt(i, 10).toString(), tbTindakan.getValueAt(i, 4).toString()
                            }) == true) {
                        ttljmdokter = ttljmdokter + Double.parseDouble(tbTindakan.getValueAt(i, 7).toString());
                        ttlkso = ttlkso + Double.parseDouble(tbTindakan.getValueAt(i, 9).toString());
                        ttlpendapatan = ttlpendapatan + Double.parseDouble(tbTindakan.getValueAt(i, 4).toString());
                        ttljasasarana = ttljasasarana + Double.parseDouble(tbTindakan.getValueAt(i, 5).toString());
                        ttlbhp = ttlbhp + Double.parseDouble(tbTindakan.getValueAt(i, 6).toString());
                        ttlmenejemen = ttlmenejemen + Double.parseDouble(tbTindakan.getValueAt(i, 10).toString());
                    } else {
                        sukses = false;
                    }
                }
            }
            if (sukses == true) {
                Sequel.queryu("delete from tampjurnal");
                if (ttlpendapatan > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','" + ttlpendapatan
                                    + "','0'",
                            "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Inap','0','" + ttlpendapatan + "'",
                            "kredit=kredit+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                }
                if (ttljmdokter > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan
                                    + "','Beban Jasa Medik Dokter Tindakan Ralan','" + ttljmdokter + "','0'",
                            "debet=debet+'" + (ttljmdokter) + "'",
                            "kd_rek='" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan
                                    + "','Utang Jasa Medik Dokter Tindakan Ralan','0','" + ttljmdokter + "'",
                            "kredit=kredit+'" + (ttljmdokter) + "'",
                            "kd_rek='" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                }
                if (ttlkso > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','" + ttlkso + "','0'",
                            "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','0','" + ttlkso + "'",
                            "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                }
                if (ttljasasarana > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Sarana_Tindakan_Ralan + "','Beban Jasa Sarana Tindakan Ralan','"
                                    + ttljasasarana + "','0'",
                            "debet=debet+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','0','"
                                    + ttljasasarana + "'",
                            "kredit=kredit+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                }
                if (ttlbhp > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','" + ttlbhp + "','0'",
                            "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','0','" + ttlbhp
                                    + "'",
                            "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                }
                if (ttlmenejemen > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Menejemen_Tindakan_Ralan + "','Beban Jasa Menejemen Tindakan Ralan','"
                                    + ttlmenejemen + "','0'",
                            "debet=debet+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Menejemen_Tindakan_Ralan + "','Utang Jasa Menejemen Tindakan Ralan','0','"
                                    + ttlmenejemen + "'",
                            "kredit=kredit+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                }
                sukses = jur.simpanJurnal(TNoRw.getText(), "U", "TINDAKAN RAWAT JALAN PASIEN " + TNoRM.getText() + " "
                        + TPasien.getText() + ", DIPOSTING OLEH " + akses.getkode());
            }

            if (sukses == true) {
                Sequel.Commit();
                for (i = 0; i < tbTindakan.getRowCount(); i++) {
                    tbTindakan.setValueAt(false, i, 0);
                }
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void SimpanPenangananPetugas() {
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses = true;
            ttljmperawat = 0;
            ttlkso = 0;
            ttlpendapatan = 0;
            ttljasasarana = 0;
            ttlbhp = 0;
            ttlmenejemen = 0;
            for (i = 0; i < tbTindakan2.getRowCount(); i++) {
                if (tbTindakan2.getValueAt(i, 0).toString().equals("true")) {
                    if (Sequel.menyimpantf("rawat_jl_pr", "?,?,?,?,?,?,?,?,?,?,?,'Belum'", "Tindakan", 11,
                            new String[] {
                                    TNoRw.getText(), tbTindakan2.getValueAt(i, 1).toString(), kdptg.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    tbTindakan2.getValueAt(i, 5).toString(),
                                    tbTindakan2.getValueAt(i, 6).toString(), tbTindakan2.getValueAt(i, 8).toString(),
                                    tbTindakan2.getValueAt(i, 9).toString(),
                                    tbTindakan2.getValueAt(i, 10).toString(), tbTindakan2.getValueAt(i, 4).toString()
                            }) == true) {
                        ttljmperawat = ttljmperawat + Double.parseDouble(tbTindakan2.getValueAt(i, 8).toString());
                        ttlkso = ttlkso + Double.parseDouble(tbTindakan2.getValueAt(i, 9).toString());
                        ttlpendapatan = ttlpendapatan + Double.parseDouble(tbTindakan2.getValueAt(i, 4).toString());
                        ttljasasarana = ttljasasarana + Double.parseDouble(tbTindakan2.getValueAt(i, 5).toString());
                        ttlbhp = ttlbhp + Double.parseDouble(tbTindakan2.getValueAt(i, 6).toString());
                        ttlmenejemen = ttlmenejemen + Double.parseDouble(tbTindakan2.getValueAt(i, 10).toString());
                    } else {
                        sukses = false;
                    }
                }
            }
            if (sukses == true) {
                Sequel.queryu("delete from tampjurnal");
                if (ttlpendapatan > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','" + ttlpendapatan
                                    + "','0'",
                            "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Inap','0','" + ttlpendapatan + "'",
                            "kredit=kredit+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                }
                if (ttljmperawat > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Beban Jasa Medik Paramedis Tindakan Ralan','" + ttljmperawat + "','0'",
                            "debet=debet+'" + (ttljmperawat) + "'",
                            "kd_rek='" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Utang Jasa Medik Paramedis Tindakan Ralan','0','" + ttljmperawat + "'",
                            "kredit=kredit+'" + (ttljmperawat) + "'",
                            "kd_rek='" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                }
                if (ttlkso > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','" + ttlkso + "','0'",
                            "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','0','" + ttlkso + "'",
                            "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                }
                if (ttljasasarana > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Sarana_Tindakan_Ralan + "','Beban Jasa Sarana Tindakan Ralan','"
                                    + ttljasasarana + "','0'",
                            "debet=debet+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','0','"
                                    + ttljasasarana + "'",
                            "kredit=kredit+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                }
                if (ttlbhp > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','" + ttlbhp + "','0'",
                            "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','0','" + ttlbhp
                                    + "'",
                            "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                }
                if (ttlmenejemen > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Menejemen_Tindakan_Ralan + "','Beban Jasa Menejemen Tindakan Ralan','"
                                    + ttlmenejemen + "','0'",
                            "debet=debet+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Menejemen_Tindakan_Ralan + "','Utang Jasa Menejemen Tindakan Ralan','0','"
                                    + ttlmenejemen + "'",
                            "kredit=kredit+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                }
                sukses = jur.simpanJurnal(TNoRw.getText(), "U", "TINDAKAN RAWAT JALAN PASIEN " + TNoRM.getText() + " "
                        + TPasien.getText() + ", DIPOSTING OLEH " + akses.getkode());
            }

            if (sukses == true) {
                Sequel.Commit();
                for (i = 0; i < tbTindakan2.getRowCount(); i++) {
                    tbTindakan2.setValueAt(false, i, 0);
                }
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void SimpanPenangananDokterPetugas() {
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses = true;
            ttljmdokter = 0;
            ttljmperawat = 0;
            ttlkso = 0;
            ttlpendapatan = 0;
            ttljasasarana = 0;
            ttlbhp = 0;
            ttlmenejemen = 0;
            for (i = 0; i < tbTindakan3.getRowCount(); i++) {
                if (tbTindakan3.getValueAt(i, 0).toString().equals("true")) {
                    if (Sequel.menyimpantf("rawat_jl_drpr", "?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum'", "Tindakan", 13,
                            new String[] {
                                    TNoRw.getText(), tbTindakan3.getValueAt(i, 1).toString(), KdDok2.getText(),
                                    kdptg2.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                            + cmbDtk.getSelectedItem(),
                                    tbTindakan3.getValueAt(i, 5).toString(), tbTindakan3.getValueAt(i, 6).toString(),
                                    tbTindakan3.getValueAt(i, 7).toString(),
                                    tbTindakan3.getValueAt(i, 8).toString(), tbTindakan3.getValueAt(i, 9).toString(),
                                    tbTindakan3.getValueAt(i, 10).toString(),
                                    tbTindakan3.getValueAt(i, 4).toString()
                            }) == true) {
                        ttljmdokter = ttljmdokter + Double.parseDouble(tbTindakan3.getValueAt(i, 7).toString());
                        ttljmperawat = ttljmperawat + Double.parseDouble(tbTindakan3.getValueAt(i, 8).toString());
                        ttlkso = ttlkso + Double.parseDouble(tbTindakan3.getValueAt(i, 9).toString());
                        ttlpendapatan = ttlpendapatan + Double.parseDouble(tbTindakan3.getValueAt(i, 4).toString());
                        ttljasasarana = ttljasasarana + Double.parseDouble(tbTindakan3.getValueAt(i, 5).toString());
                        ttlbhp = ttlbhp + Double.parseDouble(tbTindakan3.getValueAt(i, 6).toString());
                        ttlmenejemen = ttlmenejemen + Double.parseDouble(tbTindakan3.getValueAt(i, 10).toString());
                    } else {
                        sukses = false;
                    }
                }
            }
            if (sukses == true) {
                Sequel.queryu("delete from tampjurnal");
                if (ttlpendapatan > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Suspen_Piutang_Tindakan_Ralan + "','Suspen Piutang Tindakan Ralan','" + ttlpendapatan
                                    + "','0'",
                            "debet=debet+'" + (ttlpendapatan) + "'", "kd_rek='" + Suspen_Piutang_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Tindakan_Ralan + "','Pendapatan Tindakan Rawat Inap','0','" + ttlpendapatan + "'",
                            "kredit=kredit+'" + (ttlpendapatan) + "'", "kd_rek='" + Tindakan_Ralan + "'");
                }
                if (ttljmdokter > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan
                                    + "','Beban Jasa Medik Dokter Tindakan Ralan','" + ttljmdokter + "','0'",
                            "debet=debet+'" + (ttljmdokter) + "'",
                            "kd_rek='" + Beban_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan
                                    + "','Utang Jasa Medik Dokter Tindakan Ralan','0','" + ttljmdokter + "'",
                            "kredit=kredit+'" + (ttljmdokter) + "'",
                            "kd_rek='" + Utang_Jasa_Medik_Dokter_Tindakan_Ralan + "'");
                }
                if (ttljmperawat > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Beban Jasa Medik Paramedis Tindakan Ralan','" + ttljmperawat + "','0'",
                            "debet=debet+'" + (ttljmperawat) + "'",
                            "kd_rek='" + Beban_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan
                                    + "','Utang Jasa Medik Paramedis Tindakan Ralan','0','" + ttljmperawat + "'",
                            "kredit=kredit+'" + (ttljmperawat) + "'",
                            "kd_rek='" + Utang_Jasa_Medik_Paramedis_Tindakan_Ralan + "'");
                }
                if (ttlkso > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_KSO_Tindakan_Ralan + "','Beban KSO Tindakan Ralan','" + ttlkso + "','0'",
                            "debet=debet+'" + (ttlkso) + "'", "kd_rek='" + Beban_KSO_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_KSO_Tindakan_Ralan + "','Utang KSO Tindakan Ralan','0','" + ttlkso + "'",
                            "kredit=kredit+'" + (ttlkso) + "'", "kd_rek='" + Utang_KSO_Tindakan_Ralan + "'");
                }
                if (ttljasasarana > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Sarana_Tindakan_Ralan + "','Beban Jasa Sarana Tindakan Ralan','"
                                    + ttljasasarana + "','0'",
                            "debet=debet+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Beban_Jasa_Sarana_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Sarana_Tindakan_Ralan + "','Utang Jasa Sarana Tindakan Ralan','0','"
                                    + ttljasasarana + "'",
                            "kredit=kredit+'" + (ttljasasarana) + "'",
                            "kd_rek='" + Utang_Jasa_Sarana_Tindakan_Ralan + "'");
                }
                if (ttlbhp > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + HPP_BHP_Tindakan_Ralan + "','HPP BHP Tindakan Ralan','" + ttlbhp + "','0'",
                            "debet=debet+'" + (ttlbhp) + "'", "kd_rek='" + HPP_BHP_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Persediaan_BHP_Tindakan_Ralan + "','Persediaan BHP Tindakan Ralan','0','" + ttlbhp
                                    + "'",
                            "kredit=kredit+'" + (ttlbhp) + "'", "kd_rek='" + Persediaan_BHP_Tindakan_Ralan + "'");
                }
                if (ttlmenejemen > 0) {
                    Sequel.menyimpan("tampjurnal",
                            "'" + Beban_Jasa_Menejemen_Tindakan_Ralan + "','Beban Jasa Menejemen Tindakan Ralan','"
                                    + ttlmenejemen + "','0'",
                            "debet=debet+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Beban_Jasa_Menejemen_Tindakan_Ralan + "'");
                    Sequel.menyimpan("tampjurnal",
                            "'" + Utang_Jasa_Menejemen_Tindakan_Ralan + "','Utang Jasa Menejemen Tindakan Ralan','0','"
                                    + ttlmenejemen + "'",
                            "kredit=kredit+'" + (ttlmenejemen) + "'",
                            "kd_rek='" + Utang_Jasa_Menejemen_Tindakan_Ralan + "'");
                }
                sukses = jur.simpanJurnal(TNoRw.getText(), "U", "TINDAKAN RAWAT JALAN PASIEN " + TNoRM.getText() + " "
                        + TPasien.getText() + ", DIPOSTING OLEH " + akses.getkode());
            }

            if (sukses == true) {
                Sequel.Commit();
                for (i = 0; i < tbTindakan3.getRowCount(); i++) {
                    tbTindakan3.setValueAt(false, i, 0);
                }
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void tampilkanPenangananPetugas() {
        if (TabRawatTindakanPetugas.getSelectedIndex() == 0) {
            tampilTindakanPr();
        } else if (TabRawatTindakanPetugas.getSelectedIndex() == 1) {
            tampilPr();
        }
    }

    private void tampilkanPenangananDokterPetugas() {
        if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 0) {
            tampilTindakanDrPr();
        } else if (TabRawatTindakanDokterPetugas.getSelectedIndex() == 1) {
            tampilDrPr();
        }
    }

    private void inputObat() {
        DlgCariObat dlgobt = new DlgCariObat(null, false);
        dlgobt.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem());
        dlgobt.isCek();
        dlgobt.setDokter(KdDok.getText(), TDokter.getText());
        dlgobt.tampilobat();
        dlgobt.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dlgobt.setLocationRelativeTo(internalFrame1);
        dlgobt.setVisible(true);
    }

    private void inputResep() {
        DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), cmbJam.getSelectedItem().toString(),
                cmbMnt.getSelectedItem().toString(),
                cmbDtk.getSelectedItem().toString(), KdDok.getText(), TDokter.getText(), "ralan");
        resep.isCek();
        resep.tampilobat();
        resep.setVisible(true);
    }

    private void inputTemplateResep() {
        try {
            // Pemanggilan ini sekarang akan berhasil
            DlgPeresepanDokterTemplate resep = new DlgPeresepanDokterTemplate(akses.getFrame(), false);

            resep.isCek();
            resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuka form template resep: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void inputKamar() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setstatus(true);
        DlgKamarInap dlgki = new DlgKamarInap(null, false);
        dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dlgki.setLocationRelativeTo(internalFrame1);
        dlgki.emptTeks();
        dlgki.isCek();
        dlgki.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText());
        dlgki.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void inputTemplate() {
        if (dokter.tampil3(KdPeg.getText()).equals("")) {
            JOptionPane.showMessageDialog(null, "Template pemeriksaan hanya untuk dokter...!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterCariTemplatePemeriksaan templatepemeriksaan = new MasterCariTemplatePemeriksaan(null, false);
            templatepemeriksaan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            templatepemeriksaan.setLocationRelativeTo(internalFrame1);
            templatepemeriksaan.isCek();
            templatepemeriksaan.setDokter(KdPeg.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                    TNoRw.getText(), TNoRM.getText());
            templatepemeriksaan.tampil();
            templatepemeriksaan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void CopyDiagnosa() {
        // if (dokter.tampil3(KdPeg.getText()).equals("")) {
        // JOptionPane.showMessageDialog(null, "Template pemeriksaan hanya untuk
        // dokter...!!");
        // } else {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CopyDiagnosa diagnosa = new CopyDiagnosa(null, false);
        diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.isCek();
        diagnosa.setDokter(KdPeg.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                TNoRw.getText(), TNoRM.getText());
        diagnosa.setVisible(true);
        diagnosa.tampil();
        this.setCursor(Cursor.getDefaultCursor());
        // }
    }

    public void emptTeks() {
        BtnBatalActionPerformed(null);
        TabRawat.setSelectedIndex(3);
    }

    private void initRawatJalan() {
        BtnSkorBromagePascaAnestesi = new widget.Button();
        BtnSkorBromagePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorBromagePascaAnestesi.setText("Skor Bromage Pasca Anestesi");
        BtnSkorBromagePascaAnestesi.setFocusPainted(false);
        BtnSkorBromagePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorBromagePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorBromagePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorBromagePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorBromagePascaAnestesi.setName("BtnSkorBromagePascaAnestesi"); // NOI18N
        BtnSkorBromagePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorBromagePascaAnestesi.setRoundRect(false);
        BtnSkorBromagePascaAnestesi.addActionListener(this::BtnSkorBromagePascaAnestesiActionPerformed);

        BtnPenilaianPreInduksi = new widget.Button();
        BtnPenilaianPreInduksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnPenilaianPreInduksi.setText("Penilaian Pre Induksi");
        BtnPenilaianPreInduksi.setFocusPainted(false);
        BtnPenilaianPreInduksi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnPenilaianPreInduksi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreInduksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreInduksi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreInduksi.setName("Penilaian Pre Induksi");
        BtnPenilaianPreInduksi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreInduksi.setRoundRect(false);
        BtnPenilaianPreInduksi.addActionListener(this::BtnPenilaianPreInduksiActionPerformed);

        BtnHasilPemeriksaanUSGUrologi = new widget.Button();
        BtnHasilPemeriksaanUSGUrologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGUrologi.setText("Hasil USG Urologi");
        BtnHasilPemeriksaanUSGUrologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGUrologi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGUrologi.setName("BtnHasilPemeriksaanUSGUrologi");
        BtnHasilPemeriksaanUSGUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGUrologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGUrologi.addActionListener(this::BtnHasilPemeriksaanUSGUrologiActionPerformed);

        BtnHasilPemeriksaanUSGGynecologi = new widget.Button();
        BtnHasilPemeriksaanUSGGynecologi
                .setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGGynecologi.setText("Hasil USG Gynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGGynecologi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGGynecologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGGynecologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGGynecologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGGynecologi.setName("BtnHasilPemeriksaanUSGGynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGGynecologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGGynecologi.addActionListener(this::BtnHasilPemeriksaanUSGGynecologiActionPerformed);

        // BtnOdontogram = new widget.Button();
        // BtnOdontogram.setForeground(new java.awt.Color(0, 0, 0));
        // BtnOdontogram.setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/picture/Add patient.png")));
        // // NOI18N
        // BtnOdontogram.setMnemonic('4');
        // BtnOdontogram.setText("Odontogram");
        // BtnOdontogram.setToolTipText("");
        // BtnOdontogram.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        // BtnOdontogram.setGlassColor(new java.awt.Color(255, 153, 153));
        // BtnOdontogram.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        // BtnOdontogram.setName("BtnOdontogram"); // NOI18N
        // BtnOdontogram.addActionListener(new java.awt.event.ActionListener() {
        // public void actionPerformed(java.awt.event.ActionEvent evt) {
        // BtnOdontogramActionPerformed(evt);
        // }
        // });
        // panelGlass12.add(BtnOdontogram);
        // BtnOdontogram.setBounds(910, 70, 160, 30);

        FormMenu.add(BtnRiwayat);
        FormMenu.add(BtnResepObat);
        FormMenu.add(BtnCopyResep);
        FormMenu.add(BtnResepLuar);
        FormMenu.add(BtnInputObat);
        FormMenu.add(BtnObatBhp);
        FormMenu.add(BtnBerkasDigital);
        FormMenu.add(BtnPermintaanLab);
        FormMenu.add(BtnPermintaanRad);
        FormMenu.add(BtnJadwalOperasi);
        FormMenu.add(BtnSKDP);
        FormMenu.add(BtnKamar);
        FormMenu.add(BtnTriaseIGD);
        FormMenu.add(BtnRujukInternal);
        FormMenu.add(BtnResume);
        FormMenu.add(BtnAwalKeperawatanIGD);
        FormMenu.add(BtnAwalKeperawatan);
        FormMenu.add(BtnAwalKeperawatanGigi);
        FormMenu.add(BtnAwalKeperawatanKandungan);
        FormMenu.add(BtnAwalKeperawatanAnak);
        FormMenu.add(BtnAwalKeperawatanPsikiatri);
        FormMenu.add(BtnAwalKeperawatanGeriatri);
        FormMenu.add(BtnAwalFisioterapi);
        FormMenu.add(BtnAwalTerapiWicara);
        FormMenu.add(BtnAwalMedisIGD);
        FormMenu.add(BtnAwalMedisIGDPsikiatri);
        FormMenu.add(BtnAwalMedis);
        FormMenu.add(BtnAwalMedisKandungan);
        FormMenu.add(BtnAwalMedisAnak);
        FormMenu.add(BtnAwalMedisTHT);
        FormMenu.add(BtnAwalMedisPsikiatri);
        FormMenu.add(BtnAwalMedisPenyakitDalam);
        FormMenu.add(BtnAwalMedisMata);
        FormMenu.add(BtnAwalMedisNeurologi);
        FormMenu.add(BtnAwalMedisOrthopedi);
        FormMenu.add(BtnAwalMedisBedah);
        FormMenu.add(BtnAwalMedisBedahMulut);
        FormMenu.add(BtnAwalMedisGeriatri);
        FormMenu.add(BtnAwalMedisKulitKelamin);
        FormMenu.add(BtnAwalMedisParu);
        FormMenu.add(BtnAwalMedisRehabMedik);
        FormMenu.add(BtnAwalMedisHemodialisa);
        FormMenu.add(BtnRujukKeluar);
        FormMenu.add(BtnCatatan);
        FormMenu.add(BtnCatatanObservasiIGD);
        FormMenu.add(BtnCatatanCekGDS);
        FormMenu.add(BtnCatatanKeperawatan);
        FormMenu.add(BtnPenilaianUlangNyeri);
        FormMenu.add(BtnPemantauanPEWSAnak);
        FormMenu.add(BtnPemantauanPEWSDewasa);
        FormMenu.add(BtnPemantauanMEOWS);
        FormMenu.add(BtnPemantauanEWSNeonatus);
        FormMenu.add(BtnMonitoringReaksiTranfusi);
        FormMenu.add(BtnUjiFungsiKFR);
        FormMenu.add(BtnChecklistKriteriaMasukHCU);
        FormMenu.add(BtnChecklistKriteriaMasukICU);
        FormMenu.add(BtnPenilaianPreInduksi);
        FormMenu.add(BtnChecklistPreOperasi);
        FormMenu.add(BtnSignInSebelumAnestesi);
        FormMenu.add(BtnTimeOutSebelumInsisi);
        FormMenu.add(BtnSignOutSebelumMenutupLuka);
        FormMenu.add(BtnChecklistPostOperasi);
        FormMenu.add(BtnPenilaianPreOperasi);
        FormMenu.add(BtnPenilaianPreAnestesi);
        FormMenu.add(BtnSkorAldrettePascaAnestesi);
        FormMenu.add(BtnSkorStewardPascaAnestesi);
        FormMenu.add(BtnSkorBromagePascaAnestesi);
        FormMenu.add(BtnMedicalCheckUp);
        FormMenu.add(BtnPenilaianPsikolog);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhDewasa);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhAnak);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhLansia);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhNeonatus);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhGeriatri);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhPsikiatri);
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);
        FormMenu.add(BtnHasilPemeriksaanUSG);
        FormMenu.add(BtnHasilPemeriksaanUSGUrologi);
        FormMenu.add(BtnHasilPemeriksaanUSGGynecologi);
        FormMenu.add(BtnDokumentasiESWL);
        FormMenu.add(BtnCatatanPersalinanan);
        FormMenu.add(BtnSkriningNutrisiDewasa);
        FormMenu.add(BtnSkriningNutrisiLansia);
        FormMenu.add(BtnSkriningNutrisiAnak);
        FormMenu.add(BtnSkriningGiziLanjut);
        FormMenu.add(BtnAsuhanGizi);
        FormMenu.add(BtnMonitoringAsuhanGizi);
        FormMenu.add(BtnCatatanADIMEGizi);
        FormMenu.add(BtnKonselingFarmasi);
        FormMenu.add(BtnInformasiObat);
        FormMenu.add(BtnRekonsiliasiObat);
        FormMenu.add(BtnTransferAntarRuang);
        FormMenu.add(BtnEdukasiPasienKeluarga);
        FormMenu.add(BtnPengkajianRestrain);
        FormMenu.add(BtnPenilaianPasienTerminal);
        FormMenu.add(BtnPenilaianKorbanKekerasan);
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);
        FormMenu.add(BtnPenilaianPasienKeracunan);
        FormMenu.add(BtnPenilaianTambahanGeriatri);
        FormMenu.add(BtnPenilaianTambahanBunuhDiri);
        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);
        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);
    }

    private void tampilSoapPerawat() {
        if (TNoRw.getText().trim().equals("")) {
            return;
        }
        try {
            // Ambil data pemeriksaan ralan TERBARU untuk no_rawat ini
            ps4 = koneksi.prepareStatement(
                    "SELECT suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, spo2, gcs, kesadaran, keluhan, " +
                            "pemeriksaan, alergi, lingkar_perut, nip, pegawai.nama, pegawai.jbtn, tgl_perawatan, jam_rawat "
                            +
                            "FROM pemeriksaan_ralan " +
                            "LEFT JOIN pegawai ON pemeriksaan_ralan.nip=pegawai.nik " +
                            "WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1");
            try {
                ps4.setString(1, TNoRw.getText());
                rs = ps4.executeQuery();
                if (rs.next()) {
                    tglPemeriksaan = rs.getString("tgl_perawatan");
                    jamPemeriksaan = rs.getString("jam_rawat");
                    nipPemeriksaan = rs.getString("nip");
                    // Isi TTV
                    TSuhu.setText(rs.getString("suhu_tubuh") != null ? rs.getString("suhu_tubuh") : "");
                    TTensi.setText(rs.getString("tensi") != null ? rs.getString("tensi") : "");
                    TNadi.setText(rs.getString("nadi") != null ? rs.getString("nadi") : "");
                    TRespirasi.setText(rs.getString("respirasi") != null ? rs.getString("respirasi") : "");
                    TTinggi.setText(rs.getString("tinggi") != null ? rs.getString("tinggi") : "");
                    TBerat.setText(rs.getString("berat") != null ? rs.getString("berat") : "");
                    SpO2.setText(rs.getString("spo2") != null ? rs.getString("spo2") : "");
                    TGCS.setText(rs.getString("gcs") != null ? rs.getString("gcs") : "");
                    String kesadaran = rs.getString("kesadaran") != null ? rs.getString("kesadaran") : "Compos Mentis";
                    cmbKesadaran.setSelectedItem(kesadaran);
                    TAlergi.setText(rs.getString("alergi") != null ? rs.getString("alergi") : "");
                    LingkarPerut.setText(rs.getString("lingkar_perut") != null ? rs.getString("lingkar_perut") : "");

                    // Isi S (Keluhan) dan O (Pemeriksaan)
                    TKeluhan.setText(rs.getString("keluhan") != null ? rs.getString("keluhan") : "");
                    TKeluhan.setCaretPosition(0);
                    TPemeriksaan.setText(rs.getString("pemeriksaan") != null ? rs.getString("pemeriksaan") : "");
                    TPemeriksaan.setCaretPosition(0);

                    // Isi Petugas Anamnesa
                    // KdPeg.setText(rs.getString("nip") != null ? rs.getString("nip") : "");
                    // TPegawai.setText(rs.getString("nama") != null ? rs.getString("nama") : "");
                    // Jabatan.setText(rs.getString("jbtn") != null ? rs.getString("jbtn") : "");

                    if (akses.getjml2() >= 1) {
                        KdPeg.setText(akses.getkode());
                        Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?", TPegawai,
                                KdPeg.getText());
                        Sequel.cariIsi("select pegawai.jbtn from pegawai where pegawai.nik=?", Jabatan,
                                KdPeg.getText());
                    } else {
                        KdPeg.setText(rs.getString("nip") != null ? rs.getString("nip") : "");
                        TPegawai.setText(rs.getString("nama") != null ? rs.getString("nama") : "");
                        Jabatan.setText(rs.getString("jbtn") != null ? rs.getString("jbtn") : "");
                    }

                    TPenilaian.requestFocus();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void inputResep2() {
        DlgPeresepanDokter2 resep = new DlgPeresepanDokter2(null, false);
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), cmbJam.getSelectedItem().toString(),
                cmbMnt.getSelectedItem().toString(),
                cmbDtk.getSelectedItem().toString(), KdDok.getText(), TDokter.getText(), "ralan");
        resep.isCek();
        resep.tampilobat();
        resep.setVisible(true);
    }

    private void getDataPemeriksaanSbar() {
        if (tbPemeriksaanSbar.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 3).toString());
            TSituation.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 6).toString());
            TBackground.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 7).toString());
            TAssesment.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 8).toString());
            TRecommendation.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 9).toString());
            cmbJam.setSelectedItem(
                    tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(
                    tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(
                    tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4).toString());
        }
    }

    private void getDataPemeriksaanTbak() {
        if (tbPemeriksaanTbak.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 3).toString());
            TSituation1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 6).toString());
            TBackground1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 7).toString());
            TAssesment1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 8).toString());
            TRecommendation1.setText(tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 9).toString());
            cmbJam.setSelectedItem(
                    tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(
                    tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(
                    tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbPemeriksaanTbak.getValueAt(tbPemeriksaanTbak.getSelectedRow(), 4).toString());
        }
    }

    private void tampilPemeriksaanSbar() {
        Valid.tabelKosong(tabModePemeriksaanSbar);
        try {
            ps7 = koneksi.prepareStatement(
                    "select pemeriksaan_ralan_sbar.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "pemeriksaan_ralan_sbar.tgl_perawatan,pemeriksaan_ralan_sbar.jam_rawat,pemeriksaan_ralan_sbar.situation,pemeriksaan_ralan_sbar.background, "
                            + "pemeriksaan_ralan_sbar.assesment,pemeriksaan_ralan_sbar.recommendation,pemeriksaan_ralan_sbar.nip,pegawai.nama,pegawai.jbtn "
                            + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join pemeriksaan_ralan_sbar on pemeriksaan_ralan_sbar.no_rawat=reg_periksa.no_rawat "
                            + "inner join pegawai on pemeriksaan_ralan_sbar.nip=pegawai.nik where "
                            + "pemeriksaan_ralan_sbar.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (pemeriksaan_ralan_sbar.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                            + "pemeriksaan_ralan_sbar.situation like ? or pemeriksaan_ralan_sbar.background like ? or pemeriksaan_ralan_sbar.assesment like ? or "
                                            + "pemeriksaan_ralan_sbar.recommendation like ?)")
                            + "order by pemeriksaan_ralan_sbar.no_rawat,pemeriksaan_ralan_sbar.tgl_perawatan,pemeriksaan_ralan_sbar.jam_rawat desc");
            try {
                ps7.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps7.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps7.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps7.setString(4, "%" + TCari.getText().trim() + "%");
                    ps7.setString(5, "%" + TCari.getText().trim() + "%");
                    ps7.setString(6, "%" + TCari.getText().trim() + "%");
                    ps7.setString(7, "%" + TCari.getText().trim() + "%");
                    ps7.setString(8, "%" + TCari.getText().trim() + "%");
                    ps7.setString(9, "%" + TCari.getText().trim() + "%");
                    ps7.setString(10, "%" + TCari.getText().trim() + "%");
                }

                rs = ps7.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaanSbar.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                            rs.getString(12)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePemeriksaanSbar.getRowCount());
    }

    private void tampilPemeriksaanTbak() {
        Valid.tabelKosong(tabModePemeriksaanTbak);
        try {
            ps7 = koneksi.prepareStatement(
                    "select pemeriksaan_ralan_tbak.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "pemeriksaan_ralan_tbak.tgl_perawatan,pemeriksaan_ralan_tbak.jam_rawat,pemeriksaan_ralan_tbak.situation,pemeriksaan_ralan_tbak.background, "
                            + "pemeriksaan_ralan_tbak.assesment,pemeriksaan_ralan_tbak.recommendation,pemeriksaan_ralan_tbak.nip,pegawai.nama,pegawai.jbtn "
                            + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join pemeriksaan_ralan_tbak on pemeriksaan_ralan_tbak.no_rawat=reg_periksa.no_rawat "
                            + "inner join pegawai on pemeriksaan_ralan_tbak.nip=pegawai.nik where "
                            + "pemeriksaan_ralan_tbak.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "
                            + (TCari.getText().trim().equals("") ? ""
                                    : "and (pemeriksaan_ralan_tbak.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                            + "pemeriksaan_ralan_tbak.situation like ? or pemeriksaan_ralan_tbak.background like ? or pemeriksaan_ralan_tbak.assesment like ? or "
                                            + "pemeriksaan_ralan_tbak.recommendation like ?)")
                            + "order by pemeriksaan_ralan_tbak.no_rawat,pemeriksaan_ralan_tbak.tgl_perawatan,pemeriksaan_ralan_tbak.jam_rawat desc");
            try {
                ps7.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps7.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps7.setString(3, "%" + TCariPasien.getText() + "%");
                if (!TCari.getText().trim().equals("")) {
                    ps7.setString(4, "%" + TCari.getText().trim() + "%");
                    ps7.setString(5, "%" + TCari.getText().trim() + "%");
                    ps7.setString(6, "%" + TCari.getText().trim() + "%");
                    ps7.setString(7, "%" + TCari.getText().trim() + "%");
                    ps7.setString(8, "%" + TCari.getText().trim() + "%");
                    ps7.setString(9, "%" + TCari.getText().trim() + "%");
                    ps7.setString(10, "%" + TCari.getText().trim() + "%");
                }

                rs = ps7.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaanTbak.addRow(new Object[] {
                            false, rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                            rs.getString(12)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePemeriksaanTbak.getRowCount());
    }

    private void tampilCatatanPerawatIGD() {
        Valid.tabelKosong(tabModeCatatanPerawatIGD);
        try {
            if (TCari.getText().toString().trim().equals("")) {

                //
                ps4 = koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                                + "pasien.jk,pasien.tgl_lahir,catatan_keperawatan_ralan.tanggal,catatan_keperawatan_ralan.jam,catatan_keperawatan_ralan.uraian,"
                                + "catatan_keperawatan_ralan.nip,petugas.nama from catatan_keperawatan_ralan inner join reg_periksa on catatan_keperawatan_ralan.no_rawat=reg_periksa.no_rawat "
                                + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join petugas on catatan_keperawatan_ralan.nip=petugas.nip where "
                                + "catatan_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? ");
                // + "order by catatan_keperawatan_ralan.tanggal");
            } else {
                ps4 = koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                                + "pasien.jk,pasien.tgl_lahir,catatan_keperawatan_ralan.tanggal,catatan_keperawatan_ralan.jam,catatan_keperawatan_ralan.uraian,"
                                + "catatan_keperawatan_ralan.nip,petugas.nama from catatan_keperawatan_ralan inner join reg_periksa on catatan_keperawatan_ralan.no_rawat=reg_periksa.no_rawat "
                                + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join petugas on catatan_keperawatan_ralan.nip=petugas.nip where "
                                + "catatan_keperawatan_ralan.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or catatan_keperawatan_ralan.nip like ? or petugas.nama like ?) "
                                + "order by catatan_keperawatan_ralan.tanggal ");
            }

            try {
                if (TCari.getText().toString().trim().equals("")) {
                    ps4.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps4.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps4.setString(3, "%" + TCariPasien.getText() + "%");
                } else {
                    ps4.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps4.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps4.setString(3, "%" + TCari.getText() + "%");
                    ps4.setString(4, "%" + TCari.getText() + "%");
                    ps4.setString(5, "%" + TCari.getText() + "%");
                    ps4.setString(6, "%" + TCari.getText() + "%");
                    ps4.setString(7, "%" + TCari.getText() + "%");
                }

                rs = ps4.executeQuery();
                while (rs.next()) {
                    tabModeCatatanPerawatIGD.addRow(new String[] {
                            rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                            rs.getString("umurdaftar") + " " + rs.getString("sttsumur"), rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("tanggal"), rs.getString("jam"), rs.getString("uraian"), rs.getString("nip"),
                            rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeCatatanPerawatIGD.getRowCount());
    }

    private void hapusCatatanPerawatIGD() {
        if (Sequel.queryu2tf("delete from catatan_keperawatan_ralan where tanggal=? and jam=? and no_rawat=?", 3,
                new String[] {
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 6).toString(),
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 7).toString(),
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 0).toString()
                }) == true) {
            tampilCatatanPerawatIGD();
            // emptTeks();
            isCekCatatanPerawatIGD();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    public void isCekCatatanPerawatIGD() {
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        if (akses.getjml2() >= 1) {
            kdptg3.setEditable(false);
            BtnSeekPetugas3.setEnabled(false);
            kdptg3.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", TPerawat3, kdptg3.getText());
            if (TPerawat3.getText().equals("")) {
                kdptg3.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }
    }

    public void isCekCatatanAlergi() {
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        if (akses.getjml2() >= 1) {
            KdPeg1.setEditable(false);
            BtnSeekPegawai3.setEnabled(false);
            KdPeg1.setText(akses.getkode());
            Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nip=?", TPegawai1, KdPeg1.getText());

        }
    }

    private void gantiCatatanPerawatIGD() {
        Sequel.mengedit("catatan_keperawatan_ralan", "tanggal=? and jam=? and no_rawat=?",
                "no_rawat=?,tanggal=?,jam=?,uraian=?,nip=?", 8, new String[] {
                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""),
                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        Catatan1.getText(), kdptg3.getText(),
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 6).toString(),
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 7).toString(),
                        tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 0).toString()
                });
        if (tabModeCatatanPerawatIGD.getRowCount() != 0) {
            tampilCatatanPerawatIGD();
        }
        // emptTeks();
    }

    private void getDataCatatanPerawatIGD() {
        if (tbCatatanPerawatIGD.getSelectedRow() != -1) {
            TNoRw.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 0).toString());
            TNoRM.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 1).toString());
            TPasien.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 2).toString());
            // kdptg3.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(),9).toString());
            // TPerawat3.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(),10).toString());
            Catatan1.setText(tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 8).toString());
            cmbJam.setSelectedItem(
                    tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 7).toString().substring(0, 2));
            cmbMnt.setSelectedItem(
                    tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 7).toString().substring(3, 5));
            cmbDtk.setSelectedItem(
                    tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 7).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbCatatanPerawatIGD.getValueAt(tbCatatanPerawatIGD.getSelectedRow(), 6).toString());
        }
    }

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT alergi_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) AS umur,pasien.no_tlp,"
                            + "penjab.png_jawab,alergi_pasien.tgl_perawatan,alergi_pasien.allergy_code,satu_sehat_ref_allergy.display,alergi_pasien.category, alergi_pasien.reactioncode,"
                            + "alergi_pasien.severity,alergi_pasien.note,alergi_pasien.nippetugas,pegawai.nama FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj INNER JOIN alergi_pasien ON reg_periksa.no_rawat = alergi_pasien.no_rawat INNER JOIN pegawai ON alergi_pasien.nippetugas = pegawai.nik "
                            + "INNER JOIN satu_sehat_ref_allergy ON alergi_pasien.allergy_code = satu_sehat_ref_allergy.kode INNER JOIN satu_sehat_ref_allergy_reaction ON alergi_pasien.reactioncode = satu_sehat_ref_allergy_reaction.kode "
                            + "where alergi_pasien.tgl_perawatan between ? and ? "
                            + (TCari.getText().equals("") ? ""
                                    : " and alergi_pasien.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? ")
                            + " order by alergi_pasien.no_rawat");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                if (!TCari.getText().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                }
                // System.out.println(ps.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    // "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Umur", "No.Telp", "Cara Bayar",
                    // "TANGGAL", "KODE", "ALERGI", "KATEGORI", "REAKSI KODE", "SEVERITY", "NOTE",
                    // "NIP", "Nama Petugas"
                    tabMode.addRow(new String[] {
                            rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                            rs.getString("jk"), rs.getString("umur"), rs.getString("no_tlp"),
                            rs.getString("png_jawab"), rs.getString("tgl_perawatan"), rs.getString("allergy_code"),
                            rs.getString("display"), rs.getString("category"), rs.getString("reactioncode"),
                            rs.getString("severity"), rs.getString("note"), rs.getString("nippetugas"),
                            rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Vaksin : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    private void getData() {
        // "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Umur", "No.Telp", "Cara Bayar",
        // "TANGGAL", "KODE", "ALERGI", "KATEGORI", "REAKSI KODE", "SEVERITY", "NOTE",
        // "NIP", "Nama Petugas"
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            ReaksiCode.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            AlergiCode.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            AlergySystem.setText(Sequel
                    .cariIsi("select system from satu_sehat_ref_allergy where kode='" + AlergiCode.getText() + "'"));
            AlergyDisplay.setText(Sequel
                    .cariIsi("select display from satu_sehat_ref_allergy where kode='" + AlergiCode.getText() + "'"));
            ReaksiDisplay.setText(Sequel.cariIsi(
                    "select display from satu_sehat_ref_allergy_reaction where kode='" + ReaksiCode.getText() + "'"));
            ReaksiSystem.setText(Sequel.cariIsi(
                    "select system from satu_sehat_ref_allergy_reaction where kode='" + ReaksiCode.getText() + "'"));
            TKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            cmbKategory.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            cmbSeverity.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
        }
    }

    private void ganti() {

        if (Sequel.mengedittf("alergi_pasien", "no_rawat=?",
                "no_rawat=?,tgl_perawatan=?,allergy_code=?,category=?,nippetugas=?,note=?,reactioncode=?,severity=?", 9,
                new String[] {
                        TNoRw.getText(), Sequel.cariIsi("select CURRENT_TIMESTAMP()"), AlergiCode.getText(),
                        cmbKategory.getSelectedItem().toString(), akses.getkode(), TKeterangan.getText(),
                        ReaksiCode.getText(), cmbSeverity.getSelectedItem().toString(),
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                }) == true) {
            tampil();
            emptTeks();
        }
    }

    private void SimpanTemplateSOAPIE() {
        if (ChkTemplate.isSelected() == true) {
            Valid.autoNomer("template_pemeriksaan_dokter", "S", 4, KdNoRawat);
            if (KdNoRawat.getText().trim().equals("")) {
                Valid.textKosong(KdNoRawat, "No.Template");
            } else if (KdDok.getText().trim().equals("")) {
                Valid.textKosong(KdDok, "Kode Dokter");
            } else if (TKeluhan.getText().trim().equals("")) {
                Valid.textKosong(TKeluhan, "Subyek");
            } else if (TPemeriksaan.getText().trim().equals("")) {
                Valid.textKosong(TPemeriksaan, "Obyek");
            } else if (TPenilaian.getText().trim().equals("")) {
                Valid.textKosong(TPenilaian, "Asesmen");
            } else if (TInstruksi.getText().trim().equals("")) {
                Valid.textKosong(TInstruksi, "Instruksi");
            } else if (TEvaluasi.getText().trim().equals("")) {
                Valid.textKosong(TEvaluasi, "Evaluasi");
            } else {
                if (Sequel.menyimpantf("template_pemeriksaan_dokter", "?,?,?,?,?,?,?,?", "No.Template", 8,
                        new String[] {
                                KdNoRawat.getText(), KdDok.getText(), TKeluhan.getText(), TPemeriksaan.getText(),
                                TPenilaian.getText(), TindakLanjut.getText(),
                                TInstruksi.getText(), TEvaluasi.getText()
                        }) == true) {
                    JOptionPane.showMessageDialog(null, "Template SOAPIE Sukses Tersimpan");
                    TEvaluasi.requestFocus();
                }
            }
        }
    }

    private void SimpanTemplateSOAPIEPerawat() {
        if (ChkTemplatePerawat.isSelected() == true) {
            Valid.autoNomer("template_soapie_perawat", "S", 4, KdNoRawat);
            if (KdNoRawat.getText().trim().equals("")) {
                Valid.textKosong(KdNoRawat, "No.Template");
            } else if (TKeluhan.getText().trim().equals("")) {
                Valid.textKosong(TKeluhan, "Subyek");
            } else if (TPemeriksaan.getText().trim().equals("")) {
                Valid.textKosong(TPemeriksaan, "Obyek");
            } else if (TPenilaian.getText().trim().equals("")) {
                Valid.textKosong(TPenilaian, "Asesmen");
            } else if (TInstruksi.getText().trim().equals("")) {
                Valid.textKosong(TInstruksi, "Instruksi");
            } else if (TEvaluasi.getText().trim().equals("")) {
                Valid.textKosong(TEvaluasi, "Evaluasi");
            } else {
                if (Sequel.menyimpantf("template_soapie_perawat", "?,?,?,?,?,?,?", "No.Template", 7, new String[] {
                        KdNoRawat.getText(), TKeluhan.getText(), TPemeriksaan.getText(), TPenilaian.getText(),
                        TindakLanjut.getText(),
                        TInstruksi.getText(), TEvaluasi.getText()
                }) == true) {
                    JOptionPane.showMessageDialog(null, "Template SOAPIE Sukses Tersimpan");
                    TEvaluasi.requestFocus();
                }
            }
        }
    }

    private void autoFillPetugas() {
        if (akses.getjml2() >= 1) {
            KdPeg.setText(akses.getkode());
            Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?", TPegawai, KdPeg.getText());
            Sequel.cariIsi("select pegawai.jbtn from pegawai where pegawai.nik=?", Jabatan, KdPeg.getText());
        }
    }

    private void aturUrutanTombolFormMenu() {
        try {
            String spesialis = Sequel.cariIsi(
                    "select spesialis.nm_sps from spesialis inner join dokter on dokter.kd_sps=spesialis.kd_sps " +
                            "where dokter.kd_dokter=?",
                    akses.getkode());

            if (spesialis.toLowerCase().contains("umum")) {
                java.awt.Component[] comps = FormMenu.getComponents();

                java.awt.Component[] prioritas = {
                        BtnRiwayat,
                        BtnAwalMedisIGD,
                        BtnResepObat,
                        BtnPermintaanLab,
                        BtnPermintaanRad,
                        BtnKamar
                };

                FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 5));

                FormMenu.removeAll();

                for (java.awt.Component c : prioritas) {
                    if (c != null) {
                        FormMenu.add(c);
                    }
                }

                javax.swing.JSeparator garis = new javax.swing.JSeparator(javax.swing.SwingConstants.VERTICAL);
                garis.setPreferredSize(new java.awt.Dimension(2, 30));
                FormMenu.add(garis);

                for (java.awt.Component c : comps) {
                    boolean isPriority = false;
                    for (java.awt.Component p : prioritas) {
                        if (c == p) {
                            isPriority = true;
                            break;
                        }
                    }
                    if (!isPriority && !(c instanceof javax.swing.JSeparator)) {
                        FormMenu.add(c);
                    }
                }

                FormMenu.revalidate();
                FormMenu.repaint();
            } else if (!spesialis.trim().isEmpty()) {
                // Dokter Spesialis - urutan tombol sesuai kebutuhan
                java.awt.Component[] comps = FormMenu.getComponents();

                // Cari tombol Awal Medis yang sesuai dengan spesialisasi berdasarkan caption
                java.awt.Component btnAwalMedisSpesialis = null;
                String spesialisLower = spesialis.toLowerCase();

                // Mapping spesialisasi ke caption tombol
                String targetCaption = null;
                if (spesialisLower.equals("anak") || spesialisLower.contains("pediatri")) {
                    targetCaption = "Awal Medis Bayi/Anak";
                } else if (spesialisLower.equals("obgyn") || spesialisLower.contains("kandungan") ||
                        spesialisLower.contains("kebidanan")) {
                    targetCaption = "Awal Medis Kandungan";
                } else if (spesialisLower.equals("tht")) {
                    targetCaption = "Awal Medis THT";
                } else if (spesialisLower.equals("kejiwaan") || spesialisLower.contains("psikiatri") ||
                        spesialisLower.contains("jiwa")) {
                    targetCaption = "Awal Medis Psikiatri";
                } else if (spesialisLower.equals("dalam") || spesialisLower.contains("penyakit dalam") ||
                        spesialisLower.contains("internis") || spesialisLower.contains("interna")) {
                    targetCaption = "Awal Medis Penyakit Dalam";
                } else if (spesialisLower.equals("mata") || spesialisLower.contains("oftalmologi")) {
                    targetCaption = "Awal Medis Mata";
                } else if (spesialisLower.equals("syaraf") || spesialisLower.equals("saraf") ||
                        spesialisLower.contains("neurologi")) {
                    targetCaption = "Awal Medis Neurologi";
                } else if (spesialisLower.equals("orthopedi") || spesialisLower.contains("tulang")) {
                    targetCaption = "Awal Medis Orthopedi";
                } else if (spesialisLower.equals("gigi dan mulut") || spesialisLower.equals("dokter gigi") ||
                        spesialisLower.contains("periodonsia") || spesialisLower.contains("bedah mulut")) {
                    targetCaption = "Awal Medis Bedah Mulut";
                } else if (spesialisLower.equals("bedah") && !spesialisLower.contains("mulut")) {
                    targetCaption = "Awal Medis Bedah";
                } else if (spesialisLower.contains("geriatri")) {
                    targetCaption = "Awal Medis Geriatri";
                } else if (spesialisLower.equals("kulit dan kelamin") || spesialisLower.contains("kulit") ||
                        spesialisLower.contains("kelamin") || spesialisLower.contains("dermatologi")) {
                    targetCaption = "Awal Medis Kulit & Kelamin";
                } else if (spesialisLower.equals("paru") || spesialisLower.contains("pulmonologi")) {
                    targetCaption = "Awal Medis Paru";
                } else if (spesialisLower.contains("rehabilitasi") || spesialisLower.contains("fisik") ||
                        spesialisLower.contains("kfr")) {
                    targetCaption = "Awal Medis Fisik & Rehabilitasi";
                } else if (spesialisLower.contains("hemodialisa") || spesialisLower.contains("dialisis")) {
                    targetCaption = "Awal Medis Hemodialisa";
                } else if (spesialisLower.equals("jantung") || spesialisLower.contains("kardiologi")) {
                    targetCaption = null;
                } else if (spesialisLower.equals("urologi")) {
                    targetCaption = null;
                }

                // Cari tombol dengan caption yang sesuai
                if (targetCaption != null) {
                    for (java.awt.Component c : comps) {
                        if (c instanceof javax.swing.JButton) {
                            String caption = ((javax.swing.JButton) c).getText();
                            if (caption != null && caption.equals(targetCaption)) {
                                btnAwalMedisSpesialis = c;
                                break;
                            }
                        }
                    }
                }

                // Daftar tombol prioritas untuk dokter spesialis berdasarkan caption
                java.util.List<java.awt.Component> prioritasList = new java.util.ArrayList<>();

                // 1. Awal Medis (spesialisasi) - sudah dicari di atas
                if (btnAwalMedisSpesialis != null) {
                    prioritasList.add(btnAwalMedisSpesialis);
                }
                // 2. Riwayat Pasien
                prioritasList.add(BtnRiwayat);
                // 3. Input Resep
                prioritasList.add(BtnResepObat);
                // 4. Copy Resep
                prioritasList.add(BtnCopyResep);
                // 5. Input Template Resep
                prioritasList.add(BtnTemplate);
                // 6. Resume Pasien
                prioritasList.add(BtnResume);
                // 7. Berkas Digital
                prioritasList.add(BtnBerkasDigital);
                // 8. Permintaan Lab
                prioritasList.add(BtnPermintaanLab);
                // 9. Permintaan Rad
                prioritasList.add(BtnPermintaanRad);
                // 10. Surat Kontrol
                prioritasList.add(BtnSKDP);
                // 11. Kamar Inap
                prioritasList.add(BtnKamar);
                // 12. Rujuk Internal
                prioritasList.add(BtnRujukInternal);
                // 13. Rujuk Keluar
                prioritasList.add(BtnRujukKeluar);

                FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 5));
                FormMenu.removeAll();

                // Tambahkan tombol prioritas
                for (java.awt.Component c : prioritasList) {
                    if (c != null) {
                        FormMenu.add(c);
                    }
                }

                // Tambahkan separator
                javax.swing.JSeparator garis = new javax.swing.JSeparator(javax.swing.SwingConstants.VERTICAL);
                garis.setPreferredSize(new java.awt.Dimension(2, 30));
                FormMenu.add(garis);

                // Tambahkan tombol lainnya yang bukan prioritas
                for (java.awt.Component c : comps) {
                    boolean isPriority = prioritasList.contains(c);
                    if (!isPriority && !(c instanceof javax.swing.JSeparator)) {
                        FormMenu.add(c);
                    }
                }

                FormMenu.revalidate();
                FormMenu.repaint();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi Gagal Urutkan FormMenu : " + e);
        }
    }

}
