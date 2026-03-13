/*
 * By Mas Elkhanza
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMObservasiAnestesi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariDokter dokter1 = new DlgCariDokter(null, false);
    private DlgCariDokter dokter2 = new DlgCariDokter(null, false);
    private DlgCariDokter dokter3 = new DlgCariDokter(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariPetugas petugas1 = new DlgCariPetugas(null, false);
    private StringBuilder htmlContent;
    private String finger = "";

    // UI components for Data Tables
    private widget.CekBox ChkInput;
    private widget.ScrollPane Scroll1;
    private widget.Table tbPreAnestesi;
    private DefaultTableModel tabModePreAnestesi = new DefaultTableModel(null,
            new Object[] { "No.Rawat", "Tanggal", "Kd.Dokter Anestesi", "Kd.Asisten", "Kd.Dokter Bedah",
                    "Diagnosa Bedah", "Jenis Pembedahan", "Diagnosa Pasca Bedah", "Teknik Anestesi", "Status ASA",
                    "Alergi", "Penyakit Pre Anestesi", "Checklist", "Kesadaran", "Suhu", "BB", "TD", "Sat O2", "Nadi",
                    "TB", "RR", "Lainnya", "Catatan", "Catatan 2" }) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };
    private widget.ScrollPane Scroll2;
    private widget.Table tbSelamaAnestesi;
    private DefaultTableModel tabModeSelamaAnestesi = new DefaultTableModel(null,
            new Object[] { "No.Rawat", "No.RM", "Nama Pasien", "Infus Perifer", "Premedikasi", "Posisi", "Oral",
                    "Jam Oral", "IM", "Jam IM", "IV", "Jam IV", "Induksi", "Jalan Nafas", "Intubasi", "Ventilasi",
                    "TVR", "RR", "Teknik Regional", "Type", "Daerah Pemasangan", "Jarum No", "Kateter",
                    "Bromage Skor" }) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };
    private widget.ScrollPane Scroll3;
    private widget.Table tbKamarPemulihan;
    private DefaultTableModel tabModeKamarPemulihan = new DefaultTableModel(null,
            new Object[] { "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Lahir", "Jam Masuk", "Pernapasan", "Bila Spontan",
                    "Kesadaran", "Aktifitas 1", "Sirkulasi 1", "Pernapasan 1", "Kesadaran 1", "Warna Kulit 1",
                    "Total 1", "Jam Di", "Aktifitas 2", "Sirkulasi 2", "Pernapasan 2", "Kesadaran 2", "Warna Kulit 2",
                    "Total 2", "VAS", "Jam Keluar", "Aktifitas 3", "Sirkulasi 3", "Pernapasan 3", "Kesadaran 3",
                    "Warna Kulit 3", "Ke", "Bila Kesakitan", "Bila Mual", "Obat", "Infus", "Pemantauan", "Selama",
                    "Lainnya", "NIP", "Nama Petugas" }) {
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    };

    /**
     * Creates new form DlgRujuk
     * 
     * @param parent
     * @param modal
     */
    public RMObservasiAnestesi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Kode Dokter", "Nama Dokter", "Tanggal",
                "Tgl.Operasi", "Diagnosa", "Rencana Tindakan",
                "TB", "BB", "TD", "IO2", "Nadi", "Pernapasan", "Suhu", "Asesmen Fisik Cardiovasculer",
                "Asesmen Fisik Paru", "Asesmen Fisik Abdomen",
                "Asesmen Fisik Extrimitas", "Asesmen Fisik Endokrin", "Asesmen Fisik Ginjal",
                "Asesmen Fisik Obat-obatan", "Asesmen Fisik Laborat",
                "Asesmen Fisik Penunjang", "Riwayat Penyakit Alergi Obat", "Riwayat Penyakit Alergi Lainnya",
                "Riwayat Penyakit Terapi",
                "Kebiasaan Merokok", "Jml.Rokok", "Kebiasaan Alkohol", "Jml.Alko", "Penggunaan Obat", "Obat Dikonsumsi",
                "Riwayat Medis Cardiovasculer",
                "Riwayat Medis Respiratory", "Riwayat Medis Endocrine", "Riwayat Medis Lainnya", "Angka ASA",
                "Mulai Puasa", "Rencana Anestesi",
                "Rencana Perawatan", "Catatan Khusus"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        // Pernapasan.setDocument(new batasInput((byte)5).getKata(Pernapasan));
        // TCari.setDocument(new batasInput((int)100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            /*
             * TCari.getDocument().addDocumentListener(new
             * javax.swing.event.DocumentListener(){
             * 
             * @Override
             * public void insertUpdate(DocumentEvent e) {
             * if(TCari.getText().length()>2){
             * tampil();
             * }
             * }
             * 
             * @Override
             * public void removeUpdate(DocumentEvent e) {
             * if(TCari.getText().length()>2){
             * tampil();
             * }
             * }
             * 
             * @Override
             * public void changedUpdate(DocumentEvent e) {
             * if(TCari.getText().length()>2){
             * tampil();
             * }
             * }
             * });
             */
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
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

        dokter1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter1.getTable().getSelectedRow() != -1) {
                    KdDokter1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(), 0).toString());
                    NmDokter1.setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(), 1).toString());
                    KdDokter1.requestFocus();
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

        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter2.getTable().getSelectedRow() != -1) {
                    KdDokter2.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 0).toString());
                    NmDokter2.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 1).toString());
                    KdDokter2.requestFocus();
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

        dokter3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter3.getTable().getSelectedRow() != -1) {
                    KdDokter3.setText(dokter3.getTable().getValueAt(dokter3.getTable().getSelectedRow(), 0).toString());
                    NmDokter3.setText(dokter3.getTable().getValueAt(dokter3.getTable().getSelectedRow(), 1).toString());
                    KdDokter3.requestFocus();
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
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPegawai
                            .setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                    NIP.requestFocus();
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

        petugas1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas1.getTable().getSelectedRow() != -1) {
                    NIP1.setText(petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(), 0).toString());
                    NamaPegawai1.setText(
                            petugas1.getTable().getValueAt(petugas1.getTable().getSelectedRow(), 1).toString());
                    NIP1.requestFocus();
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

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}" +
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        +
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}" +
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}" +
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}" +
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}" +
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        jam();

        initObsAnestesi();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        internalFrame5 = new widget.InternalFrame();
        scrollInput3 = new widget.ScrollPane();
        FormInput3 = new widget.PanelBiasa();
        label16 = new widget.Label();
        KdDokter2 = new widget.TextBox();
        NmDokter2 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        jLabel32 = new widget.Label();
        Pernapasan1 = new widget.ComboBox();
        jLabel33 = new widget.Label();
        BilaSpontan1 = new widget.ComboBox();
        Kesadaran2 = new widget.ComboBox();
        jLabel16 = new widget.Label();
        jLabel57 = new widget.Label();
        AktifitasMasuk4 = new widget.TextBox();
        jLabel58 = new widget.Label();
        SirkulasiMasuk4 = new widget.TextBox();
        jLabel59 = new widget.Label();
        PernapasanMasuk5 = new widget.TextBox();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        KesadaranMasuk5 = new widget.TextBox();
        WarnaKulitMasuk3 = new widget.TextBox();
        jLabel62 = new widget.Label();
        TotalMasuk3 = new widget.TextBox();
        jLabel63 = new widget.Label();
        NIP1 = new widget.TextBox();
        NamaPegawai1 = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Kesakitan1 = new widget.TextBox();
        jLabel66 = new widget.Label();
        MualMuntah1 = new widget.TextBox();
        jLabel67 = new widget.Label();
        Obat1 = new widget.TextBox();
        Infus1 = new widget.TextBox();
        PemantauanNadi1 = new widget.TextBox();
        jLabel70 = new widget.Label();
        Lainnya1 = new widget.TextBox();
        Selama1 = new widget.TextBox();
        label17 = new widget.Label();
        KdDokter3 = new widget.TextBox();
        NmDokter3 = new widget.TextBox();
        BtnDokter3 = new widget.Button();
        Pernapasan2 = new widget.ComboBox();
        Pernapasan3 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        SirkulasiMasuk5 = new widget.TextBox();
        jLabel74 = new widget.Label();
        PernapasanMasuk6 = new widget.TextBox();
        jLabel75 = new widget.Label();
        KesadaranMasuk6 = new widget.TextBox();
        jLabel76 = new widget.Label();
        WarnaKulitMasuk4 = new widget.TextBox();
        jLabel77 = new widget.Label();
        MualMuntah2 = new widget.TextBox();
        internalFrame6 = new widget.InternalFrame();
        scrollInput4 = new widget.ScrollPane();
        FormInput4 = new widget.PanelBiasa();
        jLabel68 = new widget.Label();
        jLabel44 = new widget.Label();
        Pernapasan4 = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        textArea1 = new widget.TextArea();
        jLabel69 = new widget.Label();
        SirkulasiMasuk6 = new widget.TextBox();
        jLabel71 = new widget.Label();
        PernapasanMasuk7 = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        SirkulasiMasuk7 = new widget.TextBox();
        jLabel81 = new widget.Label();
        PernapasanMasuk8 = new widget.TextBox();
        jLabel82 = new widget.Label();
        SirkulasiMasuk8 = new widget.TextBox();
        jLabel83 = new widget.Label();
        PernapasanMasuk9 = new widget.TextBox();
        Pernapasan5 = new widget.ComboBox();
        jLabel84 = new widget.Label();
        PernapasanMasuk10 = new widget.TextBox();
        jLabel85 = new widget.Label();
        Pernapasan6 = new widget.ComboBox();
        PernapasanMasuk11 = new widget.TextBox();
        jLabel86 = new widget.Label();
        Pernapasan7 = new widget.ComboBox();
        jLabel87 = new widget.Label();
        Pernapasan8 = new widget.ComboBox();
        jLabel78 = new widget.Label();
        SirkulasiMasuk9 = new widget.TextBox();
        jLabel88 = new widget.Label();
        PernapasanMasuk13 = new widget.TextBox();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        SirkulasiMasuk10 = new widget.TextBox();
        SirkulasiMasuk11 = new widget.TextBox();
        SirkulasiMasuk12 = new widget.TextBox();
        jLabel92 = new widget.Label();
        SirkulasiMasuk13 = new widget.TextBox();
        jLabel93 = new widget.Label();
        Pernapasan9 = new widget.ComboBox();
        jLabel94 = new widget.Label();
        Pernapasan10 = new widget.ComboBox();
        TabRawat1 = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        label12 = new widget.Label();
        TglOperasi = new widget.Tanggal();
        jLabel28 = new widget.Label();
        Pernapasan = new widget.ComboBox();
        jLabel30 = new widget.Label();
        BilaSpontan = new widget.ComboBox();
        jLabel31 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel12 = new widget.Label();
        jLabel23 = new widget.Label();
        AktifitasMasuk = new widget.TextBox();
        jLabel17 = new widget.Label();
        SirkulasiMasuk = new widget.TextBox();
        jLabel27 = new widget.Label();
        PernapasanMasuk = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel25 = new widget.Label();
        KesadaranMasuk = new widget.TextBox();
        WarnaKulitMasuk = new widget.TextBox();
        jLabel22 = new widget.Label();
        TotalMasuk = new widget.TextBox();
        jLabel19 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPegawai = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel14 = new widget.Label();
        jLabel29 = new widget.Label();
        Kesakitan = new widget.TextBox();
        jLabel43 = new widget.Label();
        MualMuntah = new widget.TextBox();
        jLabel48 = new widget.Label();
        Obat = new widget.TextBox();
        jLabel49 = new widget.Label();
        Infus = new widget.TextBox();
        jLabel50 = new widget.Label();
        PemantauanNadi = new widget.TextBox();
        jLabel51 = new widget.Label();
        Lainnya = new widget.TextBox();
        Selama = new widget.TextBox();
        jLabel52 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        scrollInput1 = new widget.ScrollPane();
        FormInput1 = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        jLabel26 = new widget.Label();
        AktifitasMasuk1 = new widget.TextBox();
        jLabel18 = new widget.Label();
        SirkulasiMasuk1 = new widget.TextBox();
        jLabel34 = new widget.Label();
        PernapasanMasuk1 = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        KesadaranMasuk1 = new widget.TextBox();
        WarnaKulitMasuk1 = new widget.TextBox();
        jLabel37 = new widget.Label();
        TotalMasuk1 = new widget.TextBox();
        jLabel38 = new widget.Label();
        AktifitasMasuk2 = new widget.TextBox();
        jLabel39 = new widget.Label();
        SirkulasiMasuk2 = new widget.TextBox();
        PernapasanMasuk2 = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        KesadaranMasuk2 = new widget.TextBox();
        jLabel42 = new widget.Label();
        KesadaranMasuk3 = new widget.TextBox();
        internalFrame4 = new widget.InternalFrame();
        scrollInput2 = new widget.ScrollPane();
        FormInput2 = new widget.PanelBiasa();
        label15 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        label13 = new widget.Label();
        TglOperasi1 = new widget.Tanggal();
        jLabel15 = new widget.Label();
        jLabel45 = new widget.Label();
        AktifitasMasuk3 = new widget.TextBox();
        jLabel21 = new widget.Label();
        SirkulasiMasuk3 = new widget.TextBox();
        jLabel46 = new widget.Label();
        PernapasanMasuk3 = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel53 = new widget.Label();
        KesadaranMasuk4 = new widget.TextBox();
        WarnaKulitMasuk2 = new widget.TextBox();
        jLabel54 = new widget.Label();
        TotalMasuk2 = new widget.TextBox();
        jLabel55 = new widget.Label();
        Kesadaran1 = new widget.ComboBox();
        jLabel56 = new widget.Label();
        PernapasanMasuk4 = new widget.TextBox();
        panelGlass10 = new widget.panelisi();
        jLabel10 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel11 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel24 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Pre Anestesi");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Lembar Observasi Anestesi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput3.setName("scrollInput3"); // NOI18N
        scrollInput3.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput3.setBackground(new java.awt.Color(255, 255, 255));
        FormInput3.setBorder(null);
        FormInput3.setName("FormInput3"); // NOI18N
        FormInput3.setPreferredSize(new java.awt.Dimension(750, 350));
        FormInput3.setLayout(null);

        label16.setText("Dokter Anestesi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput3.add(label16);
        label16.setBounds(0, 10, 90, 23);

        KdDokter2.setEditable(false);
        KdDokter2.setName("KdDokter2"); // NOI18N
        KdDokter2.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput3.add(KdDokter2);
        KdDokter2.setBounds(100, 10, 90, 23);

        NmDokter2.setEditable(false);
        NmDokter2.setName("NmDokter2"); // NOI18N
        NmDokter2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput3.add(NmDokter2);
        NmDokter2.setBounds(200, 10, 180, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        BtnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter2KeyPressed(evt);
            }
        });
        FormInput3.add(BtnDokter2);
        BtnDokter2.setBounds(390, 10, 28, 23);

        jLabel32.setText("Teknik Anestesi:");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput3.add(jLabel32);
        jLabel32.setBounds(0, 100, 90, 23);

        Pernapasan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "E" }));
        Pernapasan1.setName("Pernapasan1"); // NOI18N
        FormInput3.add(Pernapasan1);
        Pernapasan1.setBounds(630, 100, 50, 20);

        jLabel33.setText("Checklist Persiapan Anestesia:");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput3.add(jLabel33);
        jLabel33.setBounds(540, 160, 170, 23);

        BilaSpontan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hipotensi", "Bronchoscopi", "HCL",
                "Glidescope", "CPB", "Stimulator Syaraf", "Ventilasi paru-paru", "Lainnya", " ", " " }));
        BilaSpontan1.setName("BilaSpontan1"); // NOI18N
        FormInput3.add(BilaSpontan1);
        BilaSpontan1.setBounds(100, 130, 130, 20);

        Kesadaran2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EKG lead", "CVP", "NGT", "Arteri line",
                "Cath A pulmo", "BIS", "Et CO2", "SPO2", "Stetoscope", "Kateter Urina", "NIBP", "Temp", "Lainnya" }));
        Kesadaran2.setName("Kesadaran2"); // NOI18N
        FormInput3.add(Kesadaran2);
        Kesadaran2.setBounds(100, 160, 130, 20);

        jLabel16.setText("Status fisik ASA:");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput3.add(jLabel16);
        jLabel16.setBounds(540, 100, 80, 23);

        jLabel57.setText("Jam:");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput3.add(jLabel57);
        jLabel57.setBounds(40, 230, 50, 23);

        AktifitasMasuk4.setFocusTraversalPolicyProvider(true);
        AktifitasMasuk4.setName("AktifitasMasuk4"); // NOI18N
        AktifitasMasuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasMasuk4KeyPressed(evt);
            }
        });
        FormInput3.add(AktifitasMasuk4);
        AktifitasMasuk4.setBounds(100, 230, 50, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Kesadaran:");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput3.add(jLabel58);
        jLabel58.setBounds(160, 230, 70, 23);

        SirkulasiMasuk4.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk4.setName("SirkulasiMasuk4"); // NOI18N
        SirkulasiMasuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk4KeyPressed(evt);
            }
        });
        FormInput3.add(SirkulasiMasuk4);
        SirkulasiMasuk4.setBounds(230, 230, 70, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Suhu:");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput3.add(jLabel59);
        jLabel59.setBounds(320, 230, 50, 23);

        PernapasanMasuk5.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk5.setName("PernapasanMasuk5"); // NOI18N
        PernapasanMasuk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk5KeyPressed(evt);
            }
        });
        FormInput3.add(PernapasanMasuk5);
        PernapasanMasuk5.setBounds(350, 230, 130, 23);

        jLabel60.setText("BB:");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput3.add(jLabel60);
        jLabel60.setBounds(490, 230, 30, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("TD:");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput3.add(jLabel61);
        jLabel61.setBounds(580, 230, 50, 23);

        KesadaranMasuk5.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk5.setName("KesadaranMasuk5"); // NOI18N
        KesadaranMasuk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk5KeyPressed(evt);
            }
        });
        FormInput3.add(KesadaranMasuk5);
        KesadaranMasuk5.setBounds(530, 230, 40, 23);

        WarnaKulitMasuk3.setFocusTraversalPolicyProvider(true);
        WarnaKulitMasuk3.setName("WarnaKulitMasuk3"); // NOI18N
        WarnaKulitMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitMasuk3KeyPressed(evt);
            }
        });
        FormInput3.add(WarnaKulitMasuk3);
        WarnaKulitMasuk3.setBounds(610, 230, 80, 23);

        jLabel62.setText("Sat O2:");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput3.add(jLabel62);
        jLabel62.setBounds(700, 230, 40, 23);

        TotalMasuk3.setFocusTraversalPolicyProvider(true);
        TotalMasuk3.setName("TotalMasuk3"); // NOI18N
        TotalMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalMasuk3KeyPressed(evt);
            }
        });
        FormInput3.add(TotalMasuk3);
        TotalMasuk3.setBounds(740, 230, 64, 23);

        jLabel63.setText("Asisten Anestesi:");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput3.add(jLabel63);
        jLabel63.setBounds(420, 10, 110, 23);

        NIP1.setEditable(false);
        NIP1.setHighlighter(null);
        NIP1.setName("NIP1"); // NOI18N
        NIP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIP1KeyPressed(evt);
            }
        });
        FormInput3.add(NIP1);
        NIP1.setBounds(530, 10, 94, 23);

        NamaPegawai1.setEditable(false);
        NamaPegawai1.setName("NamaPegawai1"); // NOI18N
        FormInput3.add(NamaPegawai1);
        NamaPegawai1.setBounds(630, 10, 187, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('2');
        btnPetugas1.setToolTipText("ALt+2");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        btnPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugas1KeyPressed(evt);
            }
        });
        FormInput3.add(btnPetugas1);
        btnPetugas1.setBounds(820, 10, 28, 23);

        jLabel64.setText("Penilaian Pre Induksi:");
        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput3.add(jLabel64);
        jLabel64.setBounds(0, 200, 130, 23);

        jLabel65.setText("Diagnosa bedah:");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput3.add(jLabel65);
        jLabel65.setBounds(420, 40, 110, 23);

        Kesakitan1.setFocusTraversalPolicyProvider(true);
        Kesakitan1.setName("Kesakitan1"); // NOI18N
        Kesakitan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Kesakitan1ActionPerformed(evt);
            }
        });
        Kesakitan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesakitan1KeyPressed(evt);
            }
        });
        FormInput3.add(Kesakitan1);
        Kesakitan1.setBounds(530, 40, 390, 23);

        jLabel66.setText("Jenis Pembedahan:");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput3.add(jLabel66);
        jLabel66.setBounds(-10, 70, 100, 23);

        MualMuntah1.setFocusTraversalPolicyProvider(true);
        MualMuntah1.setName("MualMuntah1"); // NOI18N
        MualMuntah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualMuntah1ActionPerformed(evt);
            }
        });
        MualMuntah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MualMuntah1KeyPressed(evt);
            }
        });
        FormInput3.add(MualMuntah1);
        MualMuntah1.setBounds(100, 70, 280, 23);

        jLabel67.setText("Diagnosa pasca bedah:");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput3.add(jLabel67);
        jLabel67.setBounds(340, 70, 180, 23);

        Obat1.setFocusTraversalPolicyProvider(true);
        Obat1.setName("Obat1"); // NOI18N
        Obat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Obat1ActionPerformed(evt);
            }
        });
        Obat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat1KeyPressed(evt);
            }
        });
        FormInput3.add(Obat1);
        Obat1.setBounds(530, 70, 390, 23);

        Infus1.setFocusTraversalPolicyProvider(true);
        Infus1.setName("Infus1"); // NOI18N
        Infus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Infus1ActionPerformed(evt);
            }
        });
        Infus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Infus1KeyPressed(evt);
            }
        });
        FormInput3.add(Infus1);
        Infus1.setBounds(250, 100, 280, 23);

        PemantauanNadi1.setFocusTraversalPolicyProvider(true);
        PemantauanNadi1.setName("PemantauanNadi1"); // NOI18N
        PemantauanNadi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemantauanNadi1ActionPerformed(evt);
            }
        });
        PemantauanNadi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemantauanNadi1KeyPressed(evt);
            }
        });
        FormInput3.add(PemantauanNadi1);
        PemantauanNadi1.setBounds(250, 130, 280, 23);

        jLabel70.setText("Penyakit Pre Anestesi:");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput3.add(jLabel70);
        jLabel70.setBounds(540, 130, 130, 23);

        Lainnya1.setFocusTraversalPolicyProvider(true);
        Lainnya1.setName("Lainnya1"); // NOI18N
        Lainnya1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Lainnya1ActionPerformed(evt);
            }
        });
        Lainnya1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lainnya1KeyPressed(evt);
            }
        });
        FormInput3.add(Lainnya1);
        Lainnya1.setBounds(680, 130, 250, 23);

        Selama1.setFocusTraversalPolicyProvider(true);
        Selama1.setName("Selama1"); // NOI18N
        Selama1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Selama1ActionPerformed(evt);
            }
        });
        Selama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Selama1KeyPressed(evt);
            }
        });
        FormInput3.add(Selama1);
        Selama1.setBounds(250, 160, 280, 23);

        label17.setText("Dokter Bedah:");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput3.add(label17);
        label17.setBounds(0, 40, 90, 23);

        KdDokter3.setEditable(false);
        KdDokter3.setName("KdDokter3"); // NOI18N
        KdDokter3.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput3.add(KdDokter3);
        KdDokter3.setBounds(100, 40, 90, 23);

        NmDokter3.setEditable(false);
        NmDokter3.setName("NmDokter3"); // NOI18N
        NmDokter3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput3.add(NmDokter3);
        NmDokter3.setBounds(200, 40, 180, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        BtnDokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter3KeyPressed(evt);
            }
        });
        FormInput3.add(BtnDokter3);
        BtnDokter3.setBounds(390, 40, 28, 23);

        Pernapasan2.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Sedasi", "Spinal", "Anestesi umum", "Epidural", "Kaudal", "Lainnya" }));
        Pernapasan2.setName("Pernapasan2"); // NOI18N
        FormInput3.add(Pernapasan2);
        Pernapasan2.setBounds(100, 100, 130, 20);

        Pernapasan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ", "Ya" }));
        Pernapasan3.setName("Pernapasan3"); // NOI18N
        FormInput3.add(Pernapasan3);
        Pernapasan3.setBounds(780, 100, 60, 20);

        jLabel72.setText("Alergi:");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput3.add(jLabel72);
        jLabel72.setBounds(690, 100, 80, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Nadi:");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput3.add(jLabel73);
        jLabel73.setBounds(160, 260, 70, 23);

        SirkulasiMasuk5.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk5.setName("SirkulasiMasuk5"); // NOI18N
        SirkulasiMasuk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk5KeyPressed(evt);
            }
        });
        FormInput3.add(SirkulasiMasuk5);
        SirkulasiMasuk5.setBounds(230, 260, 70, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("TB:");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput3.add(jLabel74);
        jLabel74.setBounds(320, 260, 50, 23);

        PernapasanMasuk6.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk6.setName("PernapasanMasuk6"); // NOI18N
        PernapasanMasuk6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk6KeyPressed(evt);
            }
        });
        FormInput3.add(PernapasanMasuk6);
        PernapasanMasuk6.setBounds(350, 260, 130, 23);

        jLabel75.setText("RR:");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput3.add(jLabel75);
        jLabel75.setBounds(490, 260, 30, 23);

        KesadaranMasuk6.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk6.setName("KesadaranMasuk6"); // NOI18N
        KesadaranMasuk6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk6KeyPressed(evt);
            }
        });
        FormInput3.add(KesadaranMasuk6);
        KesadaranMasuk6.setBounds(530, 260, 40, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Lainnya:");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput3.add(jLabel76);
        jLabel76.setBounds(580, 260, 50, 23);

        WarnaKulitMasuk4.setFocusTraversalPolicyProvider(true);
        WarnaKulitMasuk4.setName("WarnaKulitMasuk4"); // NOI18N
        WarnaKulitMasuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitMasuk4KeyPressed(evt);
            }
        });
        FormInput3.add(WarnaKulitMasuk4);
        WarnaKulitMasuk4.setBounds(620, 260, 80, 23);

        jLabel77.setText("Catatan:");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput3.add(jLabel77);
        jLabel77.setBounds(10, 290, 100, 23);

        MualMuntah2.setFocusTraversalPolicyProvider(true);
        MualMuntah2.setName("MualMuntah2"); // NOI18N
        MualMuntah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualMuntah2ActionPerformed(evt);
            }
        });
        MualMuntah2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MualMuntah2KeyPressed(evt);
            }
        });
        FormInput3.add(MualMuntah2);
        MualMuntah2.setBounds(120, 290, 810, 23);

        internalFrame5.add(FormInput3, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("PreAnestesi", internalFrame5);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput4.setName("scrollInput4"); // NOI18N
        scrollInput4.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput4.setBackground(new java.awt.Color(255, 255, 255));
        FormInput4.setBorder(null);
        FormInput4.setName("FormInput4"); // NOI18N
        FormInput4.setPreferredSize(new java.awt.Dimension(750, 450));
        FormInput4.setLayout(null);

        jLabel68.setText("Infus Perifer:");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput4.add(jLabel68);
        jLabel68.setBounds(0, 10, 80, 14);

        jLabel44.setText("Premedikasi:");
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput4.add(jLabel44);
        jLabel44.setBounds(640, 10, 80, 23);

        Pernapasan4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Terlentang", "Lithotomi",
                "Prone kanan", "Prone kiri", "Lateral", "Lain-lain", "Perlindungan mata" }));
        Pernapasan4.setName("Pernapasan4"); // NOI18N
        FormInput4.add(Pernapasan4);
        Pernapasan4.setBounds(520, 10, 110, 20);

        scrollPane1.setName("scrollPane1"); // NOI18N

        textArea1.setColumns(20);
        textArea1.setRows(5);
        textArea1.setName("textArea1"); // NOI18N
        scrollPane1.setViewportView(textArea1);

        FormInput4.add(scrollPane1);
        scrollPane1.setBounds(90, 10, 380, 130);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Oral:");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput4.add(jLabel69);
        jLabel69.setBounds(730, 10, 40, 23);

        SirkulasiMasuk6.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk6.setName("SirkulasiMasuk6"); // NOI18N
        SirkulasiMasuk6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk6KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk6);
        SirkulasiMasuk6.setBounds(770, 10, 100, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Jam:");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput4.add(jLabel71);
        jLabel71.setBounds(880, 10, 50, 23);

        PernapasanMasuk7.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk7.setName("PernapasanMasuk7"); // NOI18N
        PernapasanMasuk7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk7KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk7);
        PernapasanMasuk7.setBounds(910, 10, 100, 23);

        jLabel79.setText("Posisi:");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput4.add(jLabel79);
        jLabel79.setBounds(480, 10, 40, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("IM:");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput4.add(jLabel80);
        jLabel80.setBounds(730, 40, 40, 23);

        SirkulasiMasuk7.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk7.setName("SirkulasiMasuk7"); // NOI18N
        SirkulasiMasuk7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk7KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk7);
        SirkulasiMasuk7.setBounds(770, 40, 100, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Jam:");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput4.add(jLabel81);
        jLabel81.setBounds(880, 40, 50, 23);

        PernapasanMasuk8.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk8.setName("PernapasanMasuk8"); // NOI18N
        PernapasanMasuk8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk8KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk8);
        PernapasanMasuk8.setBounds(910, 40, 100, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("IV:");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput4.add(jLabel82);
        jLabel82.setBounds(730, 70, 30, 23);

        SirkulasiMasuk8.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk8.setName("SirkulasiMasuk8"); // NOI18N
        SirkulasiMasuk8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk8KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk8);
        SirkulasiMasuk8.setBounds(770, 70, 100, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Jam:");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput4.add(jLabel83);
        jLabel83.setBounds(880, 70, 50, 23);

        PernapasanMasuk9.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk9.setName("PernapasanMasuk9"); // NOI18N
        PernapasanMasuk9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk9KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk9);
        PernapasanMasuk9.setBounds(880, 110, 140, 23);

        Pernapasan5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Intravena", "Inhalasi" }));
        Pernapasan5.setName("Pernapasan5"); // NOI18N
        FormInput4.add(Pernapasan5);
        Pernapasan5.setBounds(760, 110, 110, 20);

        jLabel84.setText("Induksi:");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput4.add(jLabel84);
        jLabel84.setBounds(720, 110, 40, 23);

        PernapasanMasuk10.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk10.setName("PernapasanMasuk10"); // NOI18N
        PernapasanMasuk10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk10KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk10);
        PernapasanMasuk10.setBounds(910, 70, 100, 23);

        jLabel85.setText("Intubasi:");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput4.add(jLabel85);
        jLabel85.setBounds(690, 170, 70, 23);

        Pernapasan6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pernapasan6.setName("Pernapasan6"); // NOI18N
        FormInput4.add(Pernapasan6);
        Pernapasan6.setBounds(770, 370, 110, 20);

        PernapasanMasuk11.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk11.setName("PernapasanMasuk11"); // NOI18N
        PernapasanMasuk11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk11KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk11);
        PernapasanMasuk11.setBounds(880, 140, 140, 23);

        jLabel86.setText("Jalan nafas");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput4.add(jLabel86);
        jLabel86.setBounds(690, 140, 70, 23);

        Pernapasan7.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "ETT", "Oral", "Nasal", "LM", "Trakheostomi", "Lain-lain" }));
        Pernapasan7.setName("Pernapasan7"); // NOI18N
        FormInput4.add(Pernapasan7);
        Pernapasan7.setBounds(760, 140, 110, 20);

        jLabel87.setText("Ventilasi:");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput4.add(jLabel87);
        jLabel87.setBounds(700, 220, 70, 23);

        Pernapasan8.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Spontan", "Spontan assisted", "Ventilator", "Lain-lain" }));
        Pernapasan8.setName("Pernapasan8"); // NOI18N
        FormInput4.add(Pernapasan8);
        Pernapasan8.setBounds(770, 220, 110, 20);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("TVR:");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput4.add(jLabel78);
        jLabel78.setBounds(890, 220, 40, 23);

        SirkulasiMasuk9.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk9.setName("SirkulasiMasuk9"); // NOI18N
        SirkulasiMasuk9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk9KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk9);
        SirkulasiMasuk9.setBounds(930, 220, 100, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("RR:");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput4.add(jLabel88);
        jLabel88.setBounds(1040, 220, 50, 23);

        PernapasanMasuk13.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk13.setName("PernapasanMasuk13"); // NOI18N
        PernapasanMasuk13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk13KeyPressed(evt);
            }
        });
        FormInput4.add(PernapasanMasuk13);
        PernapasanMasuk13.setBounds(1070, 220, 100, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Teknik Regional:");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput4.add(jLabel89);
        jLabel89.setBounds(680, 250, 90, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Type:");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput4.add(jLabel90);
        jLabel90.setBounds(730, 280, 40, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Daerah Pemasangan:");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput4.add(jLabel91);
        jLabel91.setBounds(650, 310, 110, 23);

        SirkulasiMasuk10.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk10.setName("SirkulasiMasuk10"); // NOI18N
        SirkulasiMasuk10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk10KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk10);
        SirkulasiMasuk10.setBounds(770, 310, 100, 23);

        SirkulasiMasuk11.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk11.setName("SirkulasiMasuk11"); // NOI18N
        SirkulasiMasuk11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk11KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk11);
        SirkulasiMasuk11.setBounds(770, 280, 100, 23);

        SirkulasiMasuk12.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk12.setName("SirkulasiMasuk12"); // NOI18N
        SirkulasiMasuk12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk12KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk12);
        SirkulasiMasuk12.setBounds(770, 250, 100, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Jarun No:");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput4.add(jLabel92);
        jLabel92.setBounds(710, 340, 60, 23);

        SirkulasiMasuk13.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk13.setName("SirkulasiMasuk13"); // NOI18N
        SirkulasiMasuk13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk13KeyPressed(evt);
            }
        });
        FormInput4.add(SirkulasiMasuk13);
        SirkulasiMasuk13.setBounds(770, 340, 100, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Kateter:");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput4.add(jLabel93);
        jLabel93.setBounds(720, 370, 40, 23);

        Pernapasan9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesudah tidur", "Oral", "Fiber optik",
                "Preksigenasi", "Mudah masukventilasi", "Mudah intubasi", "Sulit intubasi", "Dengan stilet",
                "Tekanan luar laring", "Pack", "Blind", "Trakeostomi", "Nasal kanan", "Nasal kiri" }));
        Pernapasan9.setName("Pernapasan9"); // NOI18N
        FormInput4.add(Pernapasan9);
        Pernapasan9.setBounds(760, 170, 110, 20);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("Bromage Skor:");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput4.add(jLabel94);
        jLabel94.setBounds(680, 400, 80, 23);

        Pernapasan10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pernapasan10.setName("Pernapasan10"); // NOI18N
        FormInput4.add(Pernapasan10);
        Pernapasan10.setBounds(770, 400, 110, 20);

        internalFrame6.add(FormInput4, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Selama Anestesi", internalFrame6);

        TabRawat1.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat1.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat1.setName("TabRawat1"); // NOI18N
        TabRawat1.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 350));
        FormInput.setLayout(null);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(40, 100, 50, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(100, 100, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(210, 100, 180, 23);

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
        BtnDokter.setBounds(390, 100, 28, 23);

        label12.setText("Masuk kamar pulih dasar jam:");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 150, 23);

        TglOperasi.setForeground(new java.awt.Color(50, 70, 50));
        TglOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2024 12:39:44" }));
        TglOperasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglOperasi.setName("TglOperasi"); // NOI18N
        TglOperasi.setOpaque(false);
        TglOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglOperasiKeyPressed(evt);
            }
        });
        FormInput.add(TglOperasi);
        TglOperasi.setBounds(150, 10, 130, 23);

        jLabel28.setText("Pernapasan:");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(290, 10, 70, 23);

        Pernapasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Dibantu" }));
        Pernapasan.setName("Pernapasan"); // NOI18N
        FormInput.add(Pernapasan);
        Pernapasan.setBounds(370, 10, 130, 20);

        jLabel30.setText("Bila Spontan:");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(510, 10, 70, 23);

        BilaSpontan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Adekuat bersuara", "Penyumbat", "Tidur dalam", " ", " " }));
        BilaSpontan.setName("BilaSpontan"); // NOI18N
        FormInput.add(BilaSpontan);
        BilaSpontan.setBounds(590, 10, 130, 20);

        jLabel31.setText("Kesadaran:");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(730, 10, 70, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sadar betul", "Belum sadar" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(810, 10, 130, 20);

        jLabel12.setText("Skor ALDRETTE:");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(20, 40, 80, 23);

        jLabel23.setText("Aktifitas:");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(40, 60, 50, 23);

        AktifitasMasuk.setFocusTraversalPolicyProvider(true);
        AktifitasMasuk.setName("AktifitasMasuk"); // NOI18N
        AktifitasMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasMasukKeyPressed(evt);
            }
        });
        FormInput.add(AktifitasMasuk);
        AktifitasMasuk.setBounds(100, 60, 50, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Sirkulasi:");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(160, 60, 50, 23);

        SirkulasiMasuk.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk.setName("SirkulasiMasuk"); // NOI18N
        SirkulasiMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasukKeyPressed(evt);
            }
        });
        FormInput.add(SirkulasiMasuk);
        SirkulasiMasuk.setBounds(210, 60, 70, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Pernapasan:");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(300, 60, 70, 23);

        PernapasanMasuk.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk.setName("PernapasanMasuk"); // NOI18N
        PernapasanMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasukKeyPressed(evt);
            }
        });
        FormInput.add(PernapasanMasuk);
        PernapasanMasuk.setBounds(370, 60, 90, 23);

        jLabel20.setText("Kesadaran:");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(500, 60, 70, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Warna kulit:");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(630, 60, 70, 23);

        KesadaranMasuk.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk.setName("KesadaranMasuk"); // NOI18N
        KesadaranMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasukKeyPressed(evt);
            }
        });
        FormInput.add(KesadaranMasuk);
        KesadaranMasuk.setBounds(580, 60, 40, 23);

        WarnaKulitMasuk.setFocusTraversalPolicyProvider(true);
        WarnaKulitMasuk.setName("WarnaKulitMasuk"); // NOI18N
        WarnaKulitMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitMasukKeyPressed(evt);
            }
        });
        FormInput.add(WarnaKulitMasuk);
        WarnaKulitMasuk.setBounds(700, 60, 40, 23);

        jLabel22.setText("Total:");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(750, 60, 40, 23);

        TotalMasuk.setFocusTraversalPolicyProvider(true);
        TotalMasuk.setName("TotalMasuk"); // NOI18N
        TotalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalMasukKeyPressed(evt);
            }
        });
        FormInput.add(TotalMasuk);
        TotalMasuk.setBounds(790, 60, 64, 23);

        jLabel19.setText("Pegawai:");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(150, 360, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(220, 360, 94, 23);

        NamaPegawai.setEditable(false);
        NamaPegawai.setName("NamaPegawai"); // NOI18N
        FormInput.add(NamaPegawai);
        NamaPegawai.setBounds(320, 360, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(510, 360, 28, 23);

        jLabel14.setText("INSTRUKSI PASCA ANESTESI SELAMA DI RUANG PEMULIHAN");
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(30, 150, 340, 23);

        jLabel29.setText("Bila kesakitan:");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(30, 180, 180, 23);

        Kesakitan.setFocusTraversalPolicyProvider(true);
        Kesakitan.setName("Kesakitan"); // NOI18N
        Kesakitan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KesakitanActionPerformed(evt);
            }
        });
        Kesakitan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesakitanKeyPressed(evt);
            }
        });
        FormInput.add(Kesakitan);
        Kesakitan.setBounds(220, 180, 610, 23);

        jLabel43.setText("Bila mual/ muntah:");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(30, 210, 180, 23);

        MualMuntah.setFocusTraversalPolicyProvider(true);
        MualMuntah.setName("MualMuntah"); // NOI18N
        MualMuntah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MualMuntahActionPerformed(evt);
            }
        });
        MualMuntah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MualMuntahKeyPressed(evt);
            }
        });
        FormInput.add(MualMuntah);
        MualMuntah.setBounds(220, 210, 610, 23);

        jLabel48.setText("Obat-obatan:");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(30, 240, 180, 23);

        Obat.setFocusTraversalPolicyProvider(true);
        Obat.setName("Obat"); // NOI18N
        Obat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ObatActionPerformed(evt);
            }
        });
        Obat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatKeyPressed(evt);
            }
        });
        FormInput.add(Obat);
        Obat.setBounds(220, 240, 610, 23);

        jLabel49.setText("Infus:");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(30, 270, 180, 23);

        Infus.setFocusTraversalPolicyProvider(true);
        Infus.setName("Infus"); // NOI18N
        Infus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InfusActionPerformed(evt);
            }
        });
        Infus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfusKeyPressed(evt);
            }
        });
        FormInput.add(Infus);
        Infus.setBounds(220, 270, 610, 23);

        jLabel50.setText("Pemantauan tensi, nadi, nafas setiap:");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(30, 300, 190, 23);

        PemantauanNadi.setFocusTraversalPolicyProvider(true);
        PemantauanNadi.setName("PemantauanNadi"); // NOI18N
        PemantauanNadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PemantauanNadiActionPerformed(evt);
            }
        });
        PemantauanNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemantauanNadiKeyPressed(evt);
            }
        });
        FormInput.add(PemantauanNadi);
        PemantauanNadi.setBounds(230, 300, 260, 23);

        jLabel51.setText("Lainnya:");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(30, 330, 180, 23);

        Lainnya.setFocusTraversalPolicyProvider(true);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LainnyaActionPerformed(evt);
            }
        });
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        FormInput.add(Lainnya);
        Lainnya.setBounds(220, 330, 610, 23);

        Selama.setFocusTraversalPolicyProvider(true);
        Selama.setName("Selama"); // NOI18N
        Selama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelamaActionPerformed(evt);
            }
        });
        Selama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SelamaKeyPressed(evt);
            }
        });
        FormInput.add(Selama);
        Selama.setBounds(560, 300, 270, 23);

        jLabel52.setText("Selama:");
        jLabel52.setName("jLabel52"); // NOI18N
        // The UI originally had TabRawat1 within internalFrame, we'll bind Kamar
        // Pemulihan table outside TabRawat1 since it covers "Masuk", "Di Kamar",
        // "Keluar"
        internalFrame2.add(FormInput, java.awt.BorderLayout.PAGE_START);
        TabRawat1.addTab("Masuk Kamar", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput1.setName("scrollInput1"); // NOI18N
        scrollInput1.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput1.setBackground(new java.awt.Color(255, 255, 255));
        FormInput1.setBorder(null);
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(750, 120));
        FormInput1.setLayout(null);

        jLabel13.setText("Skor ALDRETTE:");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput1.add(jLabel13);
        jLabel13.setBounds(10, 40, 80, 23);

        jLabel26.setText("Aktifitas:");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput1.add(jLabel26);
        jLabel26.setBounds(30, 60, 50, 23);

        AktifitasMasuk1.setFocusTraversalPolicyProvider(true);
        AktifitasMasuk1.setName("AktifitasMasuk1"); // NOI18N
        AktifitasMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(AktifitasMasuk1);
        AktifitasMasuk1.setBounds(90, 60, 50, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Sirkulasi:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput1.add(jLabel18);
        jLabel18.setBounds(150, 60, 50, 23);

        SirkulasiMasuk1.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk1.setName("SirkulasiMasuk1"); // NOI18N
        SirkulasiMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(SirkulasiMasuk1);
        SirkulasiMasuk1.setBounds(200, 60, 70, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Pernapasan:");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput1.add(jLabel34);
        jLabel34.setBounds(290, 60, 70, 23);

        PernapasanMasuk1.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk1.setName("PernapasanMasuk1"); // NOI18N
        PernapasanMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(PernapasanMasuk1);
        PernapasanMasuk1.setBounds(360, 60, 90, 23);

        jLabel35.setText("Kesadaran:");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput1.add(jLabel35);
        jLabel35.setBounds(490, 60, 70, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Warna kulit:");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(620, 60, 70, 23);

        KesadaranMasuk1.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk1.setName("KesadaranMasuk1"); // NOI18N
        KesadaranMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(KesadaranMasuk1);
        KesadaranMasuk1.setBounds(570, 60, 40, 23);

        WarnaKulitMasuk1.setFocusTraversalPolicyProvider(true);
        WarnaKulitMasuk1.setName("WarnaKulitMasuk1"); // NOI18N
        WarnaKulitMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(WarnaKulitMasuk1);
        WarnaKulitMasuk1.setBounds(690, 60, 40, 23);

        jLabel37.setText("Total:");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput1.add(jLabel37);
        jLabel37.setBounds(740, 60, 40, 23);

        TotalMasuk1.setFocusTraversalPolicyProvider(true);
        TotalMasuk1.setName("TotalMasuk1"); // NOI18N
        TotalMasuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalMasuk1KeyPressed(evt);
            }
        });
        FormInput1.add(TotalMasuk1);
        TotalMasuk1.setBounds(780, 60, 64, 23);

        jLabel38.setText("TVS:");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput1.add(jLabel38);
        jLabel38.setBounds(10, 10, 50, 23);

        AktifitasMasuk2.setFocusTraversalPolicyProvider(true);
        AktifitasMasuk2.setName("AktifitasMasuk2"); // NOI18N
        AktifitasMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasMasuk2KeyPressed(evt);
            }
        });
        FormInput1.add(AktifitasMasuk2);
        AktifitasMasuk2.setBounds(70, 10, 50, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("RR:");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput1.add(jLabel39);
        jLabel39.setBounds(130, 10, 50, 23);

        SirkulasiMasuk2.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk2.setName("SirkulasiMasuk2"); // NOI18N
        SirkulasiMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk2KeyPressed(evt);
            }
        });
        FormInput1.add(SirkulasiMasuk2);
        SirkulasiMasuk2.setBounds(180, 10, 70, 23);

        PernapasanMasuk2.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk2.setName("PernapasanMasuk2"); // NOI18N
        PernapasanMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk2KeyPressed(evt);
            }
        });
        FormInput1.add(PernapasanMasuk2);
        PernapasanMasuk2.setBounds(340, 10, 90, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Nadi:");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput1.add(jLabel40);
        jLabel40.setBounds(270, 10, 70, 23);

        jLabel41.setText("Tekanan Darah:");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput1.add(jLabel41);
        jLabel41.setBounds(450, 10, 90, 23);

        KesadaranMasuk2.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk2.setName("KesadaranMasuk2"); // NOI18N
        KesadaranMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk2KeyPressed(evt);
            }
        });
        FormInput1.add(KesadaranMasuk2);
        KesadaranMasuk2.setBounds(550, 10, 40, 23);

        jLabel42.setText("VAS\"");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput1.add(jLabel42);
        jLabel42.setBounds(590, 10, 90, 23);

        KesadaranMasuk3.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk3.setName("KesadaranMasuk3"); // NOI18N
        KesadaranMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk3KeyPressed(evt);
            }
        });
        FormInput1.add(KesadaranMasuk3);
        KesadaranMasuk3.setBounds(690, 10, 40, 23);

        internalFrame3.add(FormInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat1.addTab("Di Kamar", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput2.setName("scrollInput2"); // NOI18N
        scrollInput2.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput2.setBackground(new java.awt.Color(255, 255, 255));
        FormInput2.setBorder(null);
        FormInput2.setName("FormInput2"); // NOI18N
        FormInput2.setPreferredSize(new java.awt.Dimension(750, 180));
        FormInput2.setLayout(null);

        label15.setText("Dokter :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput2.add(label15);
        label15.setBounds(40, 100, 50, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setName("KdDokter1"); // NOI18N
        KdDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput2.add(KdDokter1);
        KdDokter1.setBounds(100, 100, 90, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setName("NmDokter1"); // NOI18N
        NmDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput2.add(NmDokter1);
        NmDokter1.setBounds(210, 100, 180, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        FormInput2.add(BtnDokter1);
        BtnDokter1.setBounds(390, 100, 28, 23);

        label13.setText("Jam keluar kamar pulih:");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput2.add(label13);
        label13.setBounds(0, 10, 150, 23);

        TglOperasi1.setForeground(new java.awt.Color(50, 70, 50));
        TglOperasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2024 15:18:01" }));
        TglOperasi1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglOperasi1.setName("TglOperasi1"); // NOI18N
        TglOperasi1.setOpaque(false);
        TglOperasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglOperasi1KeyPressed(evt);
            }
        });
        FormInput2.add(TglOperasi1);
        TglOperasi1.setBounds(150, 10, 130, 23);

        jLabel15.setText("Skor ALDRETTE:");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput2.add(jLabel15);
        jLabel15.setBounds(20, 40, 80, 23);

        jLabel45.setText("Ke:");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput2.add(jLabel45);
        jLabel45.setBounds(430, 100, 50, 23);

        AktifitasMasuk3.setFocusTraversalPolicyProvider(true);
        AktifitasMasuk3.setName("AktifitasMasuk3"); // NOI18N
        AktifitasMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasMasuk3KeyPressed(evt);
            }
        });
        FormInput2.add(AktifitasMasuk3);
        AktifitasMasuk3.setBounds(100, 60, 50, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Sirkulasi:");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput2.add(jLabel21);
        jLabel21.setBounds(160, 60, 50, 23);

        SirkulasiMasuk3.setFocusTraversalPolicyProvider(true);
        SirkulasiMasuk3.setName("SirkulasiMasuk3"); // NOI18N
        SirkulasiMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SirkulasiMasuk3KeyPressed(evt);
            }
        });
        FormInput2.add(SirkulasiMasuk3);
        SirkulasiMasuk3.setBounds(210, 60, 70, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Pernapasan:");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput2.add(jLabel46);
        jLabel46.setBounds(300, 60, 70, 23);

        PernapasanMasuk3.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk3.setName("PernapasanMasuk3"); // NOI18N
        PernapasanMasuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk3KeyPressed(evt);
            }
        });
        FormInput2.add(PernapasanMasuk3);
        PernapasanMasuk3.setBounds(370, 60, 90, 23);

        jLabel47.setText("Kesadaran:");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput2.add(jLabel47);
        jLabel47.setBounds(500, 60, 70, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Warna kulit:");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput2.add(jLabel53);
        jLabel53.setBounds(630, 60, 70, 23);

        KesadaranMasuk4.setFocusTraversalPolicyProvider(true);
        KesadaranMasuk4.setName("KesadaranMasuk4"); // NOI18N
        KesadaranMasuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMasuk4KeyPressed(evt);
            }
        });
        FormInput2.add(KesadaranMasuk4);
        KesadaranMasuk4.setBounds(580, 60, 40, 23);

        WarnaKulitMasuk2.setFocusTraversalPolicyProvider(true);
        WarnaKulitMasuk2.setName("WarnaKulitMasuk2"); // NOI18N
        WarnaKulitMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKulitMasuk2KeyPressed(evt);
            }
        });
        FormInput2.add(WarnaKulitMasuk2);
        WarnaKulitMasuk2.setBounds(700, 60, 40, 23);

        jLabel54.setText("Total:");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput2.add(jLabel54);
        jLabel54.setBounds(750, 60, 40, 23);

        TotalMasuk2.setFocusTraversalPolicyProvider(true);
        TotalMasuk2.setName("TotalMasuk2"); // NOI18N
        TotalMasuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalMasuk2KeyPressed(evt);
            }
        });
        FormInput2.add(TotalMasuk2);
        TotalMasuk2.setBounds(790, 60, 64, 23);

        jLabel55.setText("Aktifitas:");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput2.add(jLabel55);
        jLabel55.setBounds(40, 60, 50, 23);

        Kesadaran1.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Ruang rawat", "ICU", "Langsung pulang" }));
        Kesadaran1.setName("Kesadaran1"); // NOI18N
        FormInput2.add(Kesadaran1);
        Kesadaran1.setBounds(480, 100, 130, 20);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Catatan Khusus Ruang Pemulihan:");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput2.add(jLabel56);
        jLabel56.setBounds(30, 130, 180, 23);

        PernapasanMasuk4.setFocusTraversalPolicyProvider(true);
        PernapasanMasuk4.setName("PernapasanMasuk4"); // NOI18N
        PernapasanMasuk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernapasanMasuk4KeyPressed(evt);
            }
        });
        FormInput2.add(PernapasanMasuk4);
        PernapasanMasuk4.setBounds(200, 130, 660, 23);

        internalFrame4.add(FormInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat1.addTab("Keluar Kamar", internalFrame4);

        Scroll3 = new widget.ScrollPane();
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);
        tbKamarPemulihan = new widget.Table();
        tbKamarPemulihan.setName("tbKamarPemulihan"); // NOI18N
        tbKamarPemulihan.setModel(tabModeKamarPemulihan);
        Scroll3.setViewportView(tbKamarPemulihan);

        // Wrap TabRawat1 and Table3 inside a new JPanel for the third tab
        javax.swing.JPanel panelKamarPemulihan = new javax.swing.JPanel();
        panelKamarPemulihan.setLayout(new java.awt.BorderLayout(1, 1));
        panelKamarPemulihan.setOpaque(false);
        panelKamarPemulihan.add(TabRawat1, java.awt.BorderLayout.PAGE_START);
        panelKamarPemulihan.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Kamar Pemulihan", panelKamarPemulihan);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass10.add(jLabel10);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass10.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        panelGlass10.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(200, 24));
        panelGlass10.add(TPasien);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass10.add(jLabel8);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        panelGlass10.add(TglLahir);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass10.add(jLabel11);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        panelGlass10.add(Jk);

        jLabel24.setText("Tanggal :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelGlass10.add(jLabel24);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2024" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelGlass10.add(DTPTgl);

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
        panelGlass10.add(cmbJam);

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
        panelGlass10.add(cmbMnt);

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
        panelGlass10.add(cmbDtk);

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
        panelGlass10.add(ChkJln);

        internalFrame1.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            // Valid.pindah(evt,TCari,BtnDokter);
        }
    }// GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanActionPerformed
        int tab = TabRawat.getSelectedIndex();
        if (TNoRw.getText().trim().isEmpty()) {
            Valid.textKosong(TNoRw, "No. Rawat");
            return;
        }
        String noRawat = TNoRw.getText();
        String tgl = Valid.SetTgl(DTPTgl.getSelectedItem() + "") + " 00:00:00";

        if (tab == 0) { // PreAnestesi
            if (Sequel.menyimpantf("catatan_pre_anestesi_obs",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "No.Rawat & Tanggal", 24,
                    new String[] {
                            noRawat, tgl,
                            KdDokter2.getText(), NIP1.getText(),
                            KdDokter3.getText(), Kesakitan1.getText(),
                            MualMuntah1.getText(), Obat1.getText(),
                            Pernapasan2.getSelectedItem().toString(), Pernapasan1.getSelectedItem().toString(),
                            Pernapasan3.getSelectedItem().toString(), Selama1.getText(),
                            Kesadaran2.getSelectedItem().toString(),
                            AktifitasMasuk4.getText(), SirkulasiMasuk4.getText(),
                            PernapasanMasuk5.getText(), KesadaranMasuk5.getText(),
                            WarnaKulitMasuk3.getText(), TotalMasuk3.getText(),
                            SirkulasiMasuk5.getText(), PernapasanMasuk6.getText(),
                            KesadaranMasuk6.getText(), WarnaKulitMasuk4.getText(),
                            MualMuntah2.getText()
                    })) {
                tabModePreAnestesi.addRow(new String[] {
                        noRawat, // No.Rawat
                        tgl, // Tanggal
                        KdDokter2.getText(), // Kd.Dokter Anestesi
                        NIP1.getText(), // Kd.Asisten
                        KdDokter3.getText(), // Kd.Dokter Bedah
                        Kesakitan1.getText(), // Diagnosa Bedah
                        MualMuntah1.getText(), // Jenis Pembedahan
                        Obat1.getText(), // Diagnosa Pasca Bedah
                        Pernapasan2.getSelectedItem().toString(), // Teknik Anestesi
                        Pernapasan1.getSelectedItem().toString(), // Status ASA
                        Pernapasan3.getSelectedItem().toString(), // Alergi
                        Lainnya1.getText(), // Penyakit Pre Anestesi
                        Selama1.getText(), // Checklist
                        AktifitasMasuk4.getText(), // Kesadaran (Jam Induksi)
                        SirkulasiMasuk4.getText(), // Suhu
                        PernapasanMasuk5.getText(), // BB
                        KesadaranMasuk5.getText(), // TD
                        WarnaKulitMasuk3.getText(), // Sat O2
                        TotalMasuk3.getText(), // Nadi
                        SirkulasiMasuk5.getText(), // TB
                        PernapasanMasuk6.getText(), // RR
                        KesadaranMasuk6.getText(), // Lainnya
                        WarnaKulitMasuk4.getText(), // Catatan
                        MualMuntah2.getText() // Catatan 2
                });
                // row added successfully
                emptTeks();
            }
        } else if (tab == 1) { // Selama Anestesi
            if (Sequel.menyimpantf("catatan_selama_anestesi_obs",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "No.Rawat & Tanggal", 23,
                    new String[] {
                            noRawat, tgl,
                            textArea1.getText(), Pernapasan4.getSelectedItem().toString(),
                            Pernapasan5.getSelectedItem().toString(),
                            SirkulasiMasuk6.getText(), PernapasanMasuk7.getText(),
                            SirkulasiMasuk7.getText(), PernapasanMasuk8.getText(),
                            SirkulasiMasuk8.getText(), PernapasanMasuk10.getText(),
                            Pernapasan5.getSelectedItem().toString(), Pernapasan7.getSelectedItem().toString(),
                            Pernapasan9.getSelectedItem().toString(), Pernapasan8.getSelectedItem().toString(),
                            SirkulasiMasuk9.getText(), PernapasanMasuk13.getText(),
                            SirkulasiMasuk12.getText(), SirkulasiMasuk11.getText(),
                            SirkulasiMasuk10.getText(), SirkulasiMasuk13.getText(),
                            Pernapasan6.getSelectedItem().toString(), Pernapasan10.getSelectedItem().toString()
                    })) {
                tabModeSelamaAnestesi.addRow(new String[] {
                        noRawat, tgl, TNoRM.getText(), TPasien.getText(),
                        textArea1.getText(), Pernapasan4.getSelectedItem().toString(),
                        Pernapasan5.getSelectedItem().toString(),
                        SirkulasiMasuk6.getText(), PernapasanMasuk7.getText(),
                        SirkulasiMasuk7.getText(), PernapasanMasuk8.getText(),
                        SirkulasiMasuk8.getText(), PernapasanMasuk10.getText(),
                        Pernapasan5.getSelectedItem().toString(), Pernapasan7.getSelectedItem().toString(),
                        Pernapasan9.getSelectedItem().toString(), Pernapasan8.getSelectedItem().toString(),
                        SirkulasiMasuk9.getText(), PernapasanMasuk13.getText(),
                        SirkulasiMasuk12.getText(), SirkulasiMasuk11.getText(),
                        SirkulasiMasuk10.getText(), SirkulasiMasuk13.getText(),
                        Pernapasan6.getSelectedItem().toString(), Pernapasan10.getSelectedItem().toString()
                });
                // row added successfully
                emptTeks();
            }
        } else if (tab == 2) { // Catatan Kamar Pemulihan - delegates to sub-tab of TabRawat1
            int subtab = TabRawat1.getSelectedIndex(); // 0=Masuk,1=Di Kamar,2=Keluar
            String subTgl = Valid.SetTgl(TglOperasi.getSelectedItem() + "") + " "
                    + TglOperasi.getSelectedItem().toString().substring(11, 19);
            if (subtab == 0) { // Masuk Kamar
                if (Sequel.menyimpantf("catatan_kamar_pemulihan_obs",
                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                        "No.Rawat & Tanggal", 36,
                        new String[] {
                                noRawat, subTgl,
                                Pernapasan.getSelectedItem().toString(), BilaSpontan.getSelectedItem().toString(),
                                Kesadaran.getSelectedItem().toString(),
                                AktifitasMasuk.getText(), SirkulasiMasuk.getText(),
                                PernapasanMasuk.getText(), KesadaranMasuk.getText(),
                                WarnaKulitMasuk.getText(), TotalMasuk.getText(),
                                Valid.SetTgl(TglOperasi.getSelectedItem() + "") + " 00:00:00",
                                "0", "0", "0", "0", "0", "0", "0",
                                Valid.SetTgl(TglOperasi.getSelectedItem() + "") + " 00:00:00",
                                "0", "0", "0", "0", "0", "0",
                                "Ruang rawat", "",
                                Kesakitan.getText(), MualMuntah.getText(),
                                Obat.getText(), Infus.getText(),
                                PemantauanNadi.getText(), Selama.getText(),
                                Lainnya.getText(), NIP.getText()
                        })) {
                    tabModeKamarPemulihan.addRow(new String[] {
                            noRawat, TNoRM.getText(), TPasien.getText(), TglLahir.getText(),
                            subTgl, Pernapasan.getSelectedItem().toString(),
                            BilaSpontan.getSelectedItem().toString(), Kesadaran.getSelectedItem().toString(),
                            AktifitasMasuk.getText(), SirkulasiMasuk.getText(),
                            PernapasanMasuk.getText(), KesadaranMasuk.getText(),
                            WarnaKulitMasuk.getText(), TotalMasuk.getText(),
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                            "Ruang rawat",
                            Kesakitan.getText(), MualMuntah.getText(),
                            Obat.getText(), Infus.getText(),
                            PemantauanNadi.getText(), Selama.getText(),
                            Lainnya.getText(), NIP.getText(), NamaPegawai.getText()
                    });
                    // row added successfully
                    emptTeks();
                }
            } else if (subtab == 1) { // Di Kamar
                int selectedRow = tbKamarPemulihan.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Pilih baris dari tabel terlebih dahulu.");
                    return;
                }
                String tglKunci = tabModeKamarPemulihan.getValueAt(selectedRow, 4).toString(); // col4 = Jam Masuk
                try {
                    ps = koneksi.prepareStatement(
                            "UPDATE catatan_kamar_pemulihan_obs SET " +
                                    "aktifitas2=?, sirkulasi2=?, pernapasan2=?, kesadaran2=?, kulit2=?, total2=?, vas=?, jamkeluar=? "
                                    +
                                    "WHERE no_rawat=? AND tanggal=?");
                    ps.setString(1, AktifitasMasuk1.getText());
                    ps.setString(2, SirkulasiMasuk1.getText());
                    ps.setString(3, PernapasanMasuk1.getText());
                    ps.setString(4, KesadaranMasuk1.getText());
                    ps.setString(5, WarnaKulitMasuk1.getText());
                    ps.setString(6, TotalMasuk1.getText());
                    ps.setString(7, PernapasanMasuk2.getText()); // VAS field
                    ps.setString(8, Valid.SetTgl(TglOperasi.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(9, noRawat);
                    ps.setString(10, tglKunci);
                    int updated = ps.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(null, "Data Di Kamar berhasil diperbarui.");
                        tampilKamarPemulihan();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Di Kamar: " + ex.getMessage());
                } finally {
                    try {
                        if (ps != null)
                            ps.close();
                    } catch (Exception ex2) {
                    }
                }
            } else if (subtab == 2) { // Keluar Kamar
                int selectedRow = tbKamarPemulihan.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Pilih baris dari tabel terlebih dahulu.");
                    return;
                }
                String tglKunci = tabModeKamarPemulihan.getValueAt(selectedRow, 4).toString();
                try {
                    ps = koneksi.prepareStatement(
                            "UPDATE catatan_kamar_pemulihan_obs SET " +
                                    "aktifitas3=?, sirkulasi3=?, pernapasan3=?, kesadaran3=?, kulit3=?, total3=?, ke=? "
                                    +
                                    "WHERE no_rawat=? AND tanggal=?");
                    ps.setString(1, AktifitasMasuk2.getText());
                    ps.setString(2, SirkulasiMasuk2.getText());
                    ps.setString(3, PernapasanMasuk3.getText());
                    ps.setString(4, KesadaranMasuk2.getText());
                    ps.setString(5, WarnaKulitMasuk2.getText());
                    ps.setString(6, TotalMasuk2.getText());
                    ps.setString(7, BilaSpontan1.getSelectedItem().toString()); // Ke dropdown
                    ps.setString(8, noRawat);
                    ps.setString(9, tglKunci);
                    int updated = ps.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(null, "Data Keluar Kamar berhasil diperbarui.");
                        tampilKamarPemulihan();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Keluar Kamar: " + ex.getMessage());
                } finally {
                    try {
                        if (ps != null)
                            ps.close();
                    } catch (Exception ex2) {
                    }
                }
            }
        }
    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        /*
         * if(evt.getKeyCode()==KeyEvent.VK_SPACE){
         * BtnSimpanActionPerformed(null);
         * }else{
         * Valid.pindah(evt,CatatanKhusus,BtnBatal);
         * }
         */
    }// GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }// GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }// GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnHapusActionPerformed
        int tab = TabRawat.getSelectedIndex();
        int selectedRow = -1;
        String tableName = "";
        String whereCol1 = "no_rawat", whereCol2 = "tanggal";

        if (tab == 0 && tbPreAnestesi != null) {
            selectedRow = tbPreAnestesi.getSelectedRow();
            tableName = "catatan_pre_anestesi_obs";
        } else if (tab == 1 && tbSelamaAnestesi != null) {
            selectedRow = tbSelamaAnestesi.getSelectedRow();
            tableName = "catatan_selama_anestesi_obs";
        } else if (tab == 2 && tbKamarPemulihan != null) {
            selectedRow = tbKamarPemulihan.getSelectedRow();
            tableName = "catatan_kamar_pemulihan_obs";
        }

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(rootPane, "Silakan pilih data terlebih dahulu!");
            return;
        }

        int konfirm = JOptionPane.showConfirmDialog(null, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm == JOptionPane.YES_OPTION) {
            try {
                if (tab == 0) {
                    String noRawatVal = tabModePreAnestesi.getValueAt(selectedRow, 0).toString();
                    String tglVal = tabModePreAnestesi.getValueAt(selectedRow, 1).toString();
                    ps = koneksi.prepareStatement("DELETE FROM " + tableName + " WHERE no_rawat=? AND tanggal=?");
                    ps.setString(1, noRawatVal);
                    ps.setString(2, tglVal);
                    ps.executeUpdate();
                    tabModePreAnestesi.removeRow(selectedRow);

                } else if (tab == 1) {
                    String noRawatVal = tabModeSelamaAnestesi.getValueAt(selectedRow, 0).toString();
                    String tglVal = tabModeSelamaAnestesi.getValueAt(selectedRow, 1).toString();
                    ps = koneksi.prepareStatement("DELETE FROM " + tableName + " WHERE no_rawat=? AND tanggal=?");
                    ps.setString(1, noRawatVal);
                    ps.setString(2, tglVal);
                    ps.executeUpdate();
                    tabModeSelamaAnestesi.removeRow(selectedRow);

                } else if (tab == 2) {
                    String noRawatVal = tabModeKamarPemulihan.getValueAt(selectedRow, 0).toString();
                    String tglVal = tabModeKamarPemulihan.getValueAt(selectedRow, 4).toString();
                    ps = koneksi.prepareStatement("DELETE FROM " + tableName + " WHERE no_rawat=? AND tanggal=?");
                    ps.setString(1, noRawatVal);
                    ps.setString(2, tglVal);
                    ps.executeUpdate();
                    tabModeKamarPemulihan.removeRow(selectedRow);

                }
            } catch (Exception e) {
                System.out.println("Error hapus: " + e);
                JOptionPane.showMessageDialog(null, "Gagal menghapus: " + e.getMessage());
            }
        }
    }// GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }// GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnEditActionPerformed
        int tab = TabRawat.getSelectedIndex();
        String noRawat = TNoRw.getText();

        try {
            if (tab == 0) { // PreAnestesi
                int r = tbPreAnestesi.getSelectedRow();
                if (r < 0) {
                    JOptionPane.showMessageDialog(null, "Pilih baris terlebih dahulu.");
                    return;
                }
                String tglKunci = tabModePreAnestesi.getValueAt(r, 1).toString();
                ps = koneksi.prepareStatement(
                        "UPDATE catatan_pre_anestesi_obs SET " +
                                "kd_dokter_anestesi=?, kd_asisten_anestesi=?, kd_dokter_bedah=?, " +
                                "diagnosa_bedah=?, jenis_pembedahan=?, diagnosa_pasca_bedah=?, " +
                                "teknik_anestesi=?, status_fisik_asa=?, alergi=?, " +
                                "penyakit_pre_anestesi=?, checklist_persiapan=?, jam_induksi=?, " +
                                "kesadaran=?, suhu=?, bb=?, td=?, sat_o2=?, nadi=?, tb=?, rr=?, " +
                                "lainnya=?, catatan=? " +
                                "WHERE no_rawat=? AND tanggal=?");
                ps.setString(1, KdDokter2.getText());
                ps.setString(2, NIP1.getText());
                ps.setString(3, KdDokter3.getText());
                ps.setString(4, Kesakitan1.getText());
                ps.setString(5, MualMuntah1.getText());
                ps.setString(6, Obat1.getText());
                ps.setString(7, Pernapasan2.getSelectedItem().toString());
                ps.setString(8, Pernapasan1.getSelectedItem().toString());
                ps.setString(9, Pernapasan3.getSelectedItem().toString());
                ps.setString(10, Lainnya1.getText());
                ps.setString(11, Selama1.getText());
                ps.setString(12, AktifitasMasuk4.getText());
                ps.setString(13, SirkulasiMasuk4.getText());
                ps.setString(14, PernapasanMasuk5.getText());
                ps.setString(15, KesadaranMasuk5.getText());
                ps.setString(16, WarnaKulitMasuk3.getText());
                ps.setString(17, TotalMasuk3.getText());
                ps.setString(18, SirkulasiMasuk5.getText());
                ps.setString(19, PernapasanMasuk6.getText());
                ps.setString(20, KesadaranMasuk6.getText());
                ps.setString(21, WarnaKulitMasuk4.getText());
                ps.setString(22, MualMuntah2.getText());
                ps.setString(23, noRawat);
                ps.setString(24, tglKunci);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Data PreAnestesi berhasil diperbarui.");
                    tampilPreAnestesi();
                    emptTeks();
                }

            } else if (tab == 1) { // Selama Anestesi
                int r = tbSelamaAnestesi.getSelectedRow();
                if (r < 0) {
                    JOptionPane.showMessageDialog(null, "Pilih baris terlebih dahulu.");
                    return;
                }
                String tglKunci = tabModeSelamaAnestesi.getValueAt(r, 1).toString();
                ps = koneksi.prepareStatement(
                        "UPDATE catatan_selama_anestesi_obs SET " +
                                "infus_perifer=?, premedikasi=?, posisi=?, " +
                                "oral=?, jam_oral=?, im=?, jam_im=?, iv=?, jam_iv=?, " +
                                "induksi=?, jalan_nafas=?, intubasi=?, ventilasi=?, " +
                                "tvr=?, rr=?, teknik_regional=?, type=?, " +
                                "daerah_pemasangan=?, jarum_no=?, kateter=?, bromage_skor=? " +
                                "WHERE no_rawat=? AND tanggal=?");
                ps.setString(1, textArea1.getText());
                ps.setString(2, Pernapasan4.getSelectedItem().toString());
                ps.setString(3, Pernapasan5.getSelectedItem().toString());
                ps.setString(4, SirkulasiMasuk6.getText());
                ps.setString(5, PernapasanMasuk7.getText());
                ps.setString(6, SirkulasiMasuk7.getText());
                ps.setString(7, PernapasanMasuk8.getText());
                ps.setString(8, SirkulasiMasuk8.getText());
                ps.setString(9, PernapasanMasuk10.getText());
                ps.setString(10, Pernapasan7.getSelectedItem().toString());
                ps.setString(11, Pernapasan9.getSelectedItem().toString());
                ps.setString(12, Pernapasan8.getSelectedItem().toString());
                ps.setString(13, SirkulasiMasuk9.getText());
                ps.setString(14, PernapasanMasuk13.getText());
                ps.setString(15, SirkulasiMasuk12.getText());
                ps.setString(16, SirkulasiMasuk11.getText());
                ps.setString(17, SirkulasiMasuk10.getText());
                ps.setString(18, SirkulasiMasuk13.getText());
                ps.setString(19, Pernapasan6.getSelectedItem().toString());
                ps.setString(20, Pernapasan10.getSelectedItem().toString());
                ps.setString(21, noRawat);
                ps.setString(22, tglKunci);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Data Selama Anestesi berhasil diperbarui.");
                    tampilSelamaAnestesi();
                    emptTeks();
                }

            } else if (tab == 2) { // Kamar Pemulihan (Masuk Kamar)
                int r = tbKamarPemulihan.getSelectedRow();
                if (r < 0) {
                    JOptionPane.showMessageDialog(null, "Pilih baris terlebih dahulu.");
                    return;
                }
                String tglKunci = tabModeKamarPemulihan.getValueAt(r, 4).toString();
                ps = koneksi.prepareStatement(
                        "UPDATE catatan_kamar_pemulihan_obs SET " +
                                "pernapasan=?, spontan=?, status_kesadaran=?, " +
                                "aktifitas1=?, sirkulasi1=?, pernapasan1=?, kesadaran1=?, warnakulit1=?, total1=?, " +
                                "kesakitan=?, mualmuntah=?, obat=?, infus=?, pemantauan=?, selama=?, lainnya=? " +
                                "WHERE no_rawat=? AND tanggal=?");
                ps.setString(1, Pernapasan.getSelectedItem().toString());
                ps.setString(2, BilaSpontan.getSelectedItem().toString());
                ps.setString(3, Kesadaran.getSelectedItem().toString());
                ps.setString(4, AktifitasMasuk.getText());
                ps.setString(5, SirkulasiMasuk.getText());
                ps.setString(6, PernapasanMasuk.getText());
                ps.setString(7, KesadaranMasuk.getText());
                ps.setString(8, WarnaKulitMasuk.getText());
                ps.setString(9, TotalMasuk.getText());
                ps.setString(10, Kesakitan.getText());
                ps.setString(11, MualMuntah.getText());
                ps.setString(12, Obat.getText());
                ps.setString(13, Infus.getText());
                ps.setString(14, PemantauanNadi.getText());
                ps.setString(15, Selama.getText());
                ps.setString(16, Lainnya.getText());
                ps.setString(17, noRawat);
                ps.setString(18, tglKunci);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Data Kamar Pemulihan berhasil diperbarui.");
                    tampilKamarPemulihan();
                    emptTeks();
                }
            }
        } catch (Exception e) {
            System.out.println("Error ganti: " + e);
            JOptionPane.showMessageDialog(null, "Gagal memperbarui: " + e.getMessage());
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
        }
    }// GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }// GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        /*
         * if(evt.getKeyCode()==KeyEvent.VK_SPACE){
         * BtnKeluarActionPerformed(null);
         * }else{Valid.pindah(evt,BtnEdit,TCari);}
         */
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPrintActionPerformed
        /*
         * this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         * if(tabMode.getRowCount()==0){
         * JOptionPane.showMessageDialog(
         * null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
         * BtnBatal.requestFocus();
         * }else if(tabMode.getRowCount()!=0){
         * try{
         * htmlContent = new StringBuilder();
         * htmlContent.append(
         * "<tr class='isi'>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Operasi</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Tindakan</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>IO2</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pernapasan</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Cardiovasculer</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Paru</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Abdomen</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Extrimitas</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Endokrin</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Ginjal</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Obat-obatan</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Laborat</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Penunjang</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Alergi Obat</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Alergi Lainnya</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Terapi</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebiasaan Merokok</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml.Rokok</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebiasaan Alkohol</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml.Alko</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penggunaan Obat</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Dikonsumsi</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Cardiovasculer</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Respiratory</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Endocrine</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Lainnya</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Angka ASA</b></td>"+
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mulai Puasa</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Anestesi</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Perawatan</b></td>"
         * +
         * "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Catatan Khusus</b></td>"
         * +
         * "</tr>"
         * );
         * 
         * for (i = 0; i < tabMode.getRowCount(); i++) {
         * htmlContent.append(
         * "<tr class='isi'>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
         * "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
         * "</tr>");
         * }
         * 
         * LoadHTML.setText(
         * "<html>"+
         * "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
         * +
         * htmlContent.toString()+
         * "</table>"+
         * "</html>"
         * );
         * 
         * File g = new File("file2.css");
         * BufferedWriter bg = new BufferedWriter(new FileWriter(g));
         * bg.write(
         * ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
         * +
         * ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
         * +
         * ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
         * +
         * ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
         * +
         * ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
         * +
         * ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
         * +
         * ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
         * +
         * ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
         * +
         * ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
         * );
         * bg.close();
         * 
         * File f = new File("DataPenilaianPreAnestesi.html");
         * BufferedWriter bw = new BufferedWriter(new FileWriter(f));
         * bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
         * "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
         * "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
         * +
         * "<tr class='isi2'>"+
         * "<td valign='top' align='center'>"+
         * "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
         * akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+
         * "<br>"+
         * akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
         * "<font size='2' face='Tahoma'>DATA PENILAIAN PRE ANESTESI<br><br></font>"+
         * "</td>"+
         * "</tr>"+
         * "</table>")
         * );
         * bw.close();
         * Desktop.getDesktop().browse(f.toURI());
         * 
         * }catch(Exception e){
         * System.out.println("Notifikasi : "+e);
         * }
         * }
         * this.setCursor(Cursor.getDefaultCursor());
         */
    }// GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }// GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllActionPerformed
        // TCari.setText("");
        tampil();
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        /*
         * if(evt.getKeyCode()==KeyEvent.VK_SPACE){
         * TCari.setText("");
         * tampil();
         * }else{
         * Valid.pindah(evt, BtnCari, TPasien);
         * }
         */
    }// GEN-LAST:event_BtnAllKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }// GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokterKeyPressed
        // Valid.pindah(evt,Edukasi,Hubungan);
    }// GEN-LAST:event_BtnDokterKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPenilaianMedisActionPerformed
        /*
         * if(tbObat.getSelectedRow()>-1){
         * Map<String, Object> param = new HashMap<>();
         * param.put("namars",akses.getnamars());
         * param.put("alamatrs",akses.getalamatrs());
         * param.put("kotars",akses.getkabupatenrs());
         * param.put("propinsirs",akses.getpropinsirs());
         * param.put("kontakrs",akses.getkontakrs());
         * param.put("emailrs",akses.getemailrs());
         * param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
         * finger=Sequel.
         * cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?"
         * ,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
         * param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "
         * +akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.
         * getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?
         * tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.
         * SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
         * 
         * Valid.MyReportqry("rptCetakPenilaianPreAnestesi.jasper",
         * "report","::[ Laporan Penilaian Pre Anestesi ]::",
         * "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"
         * +
         * "penilaian_pre_anestesi.kd_dokter,DATE_FORMAT(penilaian_pre_anestesi.tanggal_operasi,'%d-%m-%Y %H:%m:%s') as tanggal_operasi,penilaian_pre_anestesi.diagnosa,"
         * +
         * "penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,"+
         * "penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,"+
         * "penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,"+
         * "penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,"+
         * "penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_obat,penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,"+
         * "penilaian_pre_anestesi.riwayat_medis_respiratory,penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,"+
         * "penilaian_pre_anestesi.asa,DATE_FORMAT(penilaian_pre_anestesi.puasa,'%d-%m-%Y %H:%m:%s') as puasa,penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,"
         * +
         * "penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
         * +
         * "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "
         * +
         * "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where penilaian_pre_anestesi.no_rawat='"
         * +tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
         * "and penilaian_pre_anestesi.tanggal='"+tbObat.getValueAt(tbObat.
         * getSelectedRow(),7).toString()+"'",param);
         * }
         */
    }// GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void TglOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TglOperasiKeyPressed
        // Valid.pindah(evt,RencanaTindakan,TB);
    }// GEN-LAST:event_TglOperasiKeyPressed

    private void AktifitasMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasMasukKeyPressed
        // Valid.pindah(evt,btnPetugas,SirkulasiMasuk);
    }// GEN-LAST:event_AktifitasMasukKeyPressed

    private void SirkulasiMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasukKeyPressed
        Valid.pindah(evt, AktifitasMasuk, PernapasanMasuk);
    }// GEN-LAST:event_SirkulasiMasukKeyPressed

    private void PernapasanMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasukKeyPressed
        Valid.pindah(evt, SirkulasiMasuk, WarnaKulitMasuk);
    }// GEN-LAST:event_PernapasanMasukKeyPressed

    private void KesadaranMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasukKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasukKeyPressed

    private void WarnaKulitMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaKulitMasukKeyPressed
        Valid.pindah(evt, PernapasanMasuk, TotalMasuk);
    }// GEN-LAST:event_WarnaKulitMasukKeyPressed

    private void TotalMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TotalMasukKeyPressed
        // Valid.pindah(evt,WarnaKulitMasuk,Catatan);
    }// GEN-LAST:event_TotalMasukKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTglKeyPressed
        // Valid.pindah(evt,BtnSeekDokter,cmbJam);
    }// GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, DTPTgl, cmbMnt);
    }// GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }// GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbDtkKeyPressed
        // Valid.pindah(evt,cmbMnt,TCari);
    }// GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ChkJlnActionPerformed

    private void AktifitasMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AktifitasMasuk1KeyPressed

    private void SirkulasiMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk1KeyPressed

    private void PernapasanMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk1KeyPressed

    private void KesadaranMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk1KeyPressed

    private void WarnaKulitMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaKulitMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_WarnaKulitMasuk1KeyPressed

    private void TotalMasuk1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TotalMasuk1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TotalMasuk1KeyPressed

    private void AktifitasMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AktifitasMasuk2KeyPressed

    private void SirkulasiMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk2KeyPressed

    private void PernapasanMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk2KeyPressed

    private void KesadaranMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk2KeyPressed

    private void KesadaranMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk3KeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NIPKeyPressed
        /*
         * if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
         * NamaPegawai.setText(petugas.tampil3(NIP.getText()));
         * }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
         * DetikMasuk.requestFocus();
         * }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
         * AktifitasMasuk.requestFocus();
         * }else if(evt.getKeyCode()==KeyEvent.VK_UP){
         * btnPetugasActionPerformed(null);
         * }
         */
    }// GEN-LAST:event_NIPKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }// GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPetugasKeyPressed
        // Valid.pindah(evt,DetikMasuk,AktifitasMasuk);
    }// GEN-LAST:event_btnPetugasKeyPressed

    private void KesakitanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_KesakitanActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesakitanActionPerformed

    private void KesakitanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesakitanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesakitanKeyPressed

    private void MualMuntahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MualMuntahActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntahActionPerformed

    private void MualMuntahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MualMuntahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntahKeyPressed

    private void ObatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ObatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ObatActionPerformed

    private void ObatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ObatKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_ObatKeyPressed

    private void InfusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_InfusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_InfusActionPerformed

    private void InfusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_InfusKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_InfusKeyPressed

    private void PemantauanNadiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PemantauanNadiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_PemantauanNadiActionPerformed

    private void PemantauanNadiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PemantauanNadiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PemantauanNadiKeyPressed

    private void LainnyaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LainnyaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_LainnyaActionPerformed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LainnyaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_LainnyaKeyPressed

    private void SelamaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SelamaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_SelamaActionPerformed

    private void SelamaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SelamaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SelamaKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokter1ActionPerformed
        dokter1.isCek();
        dokter1.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter1.setLocationRelativeTo(internalFrame1);
        dokter1.setAlwaysOnTop(false);
        dokter1.setVisible(true);
    }// GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnDokter1KeyPressed

    private void TglOperasi1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TglOperasi1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TglOperasi1KeyPressed

    private void AktifitasMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AktifitasMasuk3KeyPressed

    private void SirkulasiMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk3KeyPressed

    private void PernapasanMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk3KeyPressed

    private void KesadaranMasuk4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk4KeyPressed

    private void WarnaKulitMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaKulitMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_WarnaKulitMasuk2KeyPressed

    private void TotalMasuk2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TotalMasuk2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TotalMasuk2KeyPressed

    private void PernapasanMasuk4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk4KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokter2ActionPerformed
        dokter2.isCek();
        dokter2.TCari.setText("anestesi");
        dokter2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter2.setLocationRelativeTo(internalFrame1);
        dokter2.setAlwaysOnTop(false);
        dokter2.setVisible(true);
    }// GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnDokter2KeyPressed

    private void AktifitasMasuk4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasMasuk4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AktifitasMasuk4KeyPressed

    private void SirkulasiMasuk4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk4KeyPressed

    private void PernapasanMasuk5KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk5KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk5KeyPressed

    private void KesadaranMasuk5KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk5KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk5KeyPressed

    private void WarnaKulitMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaKulitMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_WarnaKulitMasuk3KeyPressed

    private void TotalMasuk3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TotalMasuk3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TotalMasuk3KeyPressed

    private void NIP1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NIP1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_NIP1KeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPetugas1ActionPerformed
        petugas1.emptTeks();
        petugas1.isCek();
        petugas1.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas1.setLocationRelativeTo(internalFrame1);
        petugas1.setAlwaysOnTop(false);
        petugas1.setVisible(true);
    }// GEN-LAST:event_btnPetugas1ActionPerformed

    private void btnPetugas1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPetugas1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnPetugas1KeyPressed

    private void Kesakitan1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Kesakitan1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_Kesakitan1ActionPerformed

    private void Kesakitan1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_Kesakitan1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_Kesakitan1KeyPressed

    private void MualMuntah1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MualMuntah1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntah1ActionPerformed

    private void MualMuntah1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MualMuntah1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntah1KeyPressed

    private void Obat1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Obat1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_Obat1ActionPerformed

    private void Obat1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_Obat1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_Obat1KeyPressed

    private void Infus1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Infus1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_Infus1ActionPerformed

    private void Infus1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_Infus1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_Infus1KeyPressed

    private void PemantauanNadi1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PemantauanNadi1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_PemantauanNadi1ActionPerformed

    private void PemantauanNadi1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PemantauanNadi1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PemantauanNadi1KeyPressed

    private void Lainnya1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Lainnya1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_Lainnya1ActionPerformed

    private void Lainnya1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_Lainnya1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_Lainnya1KeyPressed

    private void Selama1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Selama1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_Selama1ActionPerformed

    private void Selama1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_Selama1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_Selama1KeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokter3ActionPerformed
        dokter3.isCek();
        dokter3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter3.setLocationRelativeTo(internalFrame1);
        dokter3.setAlwaysOnTop(false);
        dokter3.setVisible(true);
    }// GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokter3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnDokter3KeyPressed

    private void SirkulasiMasuk5KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk5KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk5KeyPressed

    private void PernapasanMasuk6KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk6KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk6KeyPressed

    private void KesadaranMasuk6KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KesadaranMasuk6KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KesadaranMasuk6KeyPressed

    private void WarnaKulitMasuk4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaKulitMasuk4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_WarnaKulitMasuk4KeyPressed

    private void MualMuntah2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MualMuntah2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntah2ActionPerformed

    private void MualMuntah2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MualMuntah2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_MualMuntah2KeyPressed

    private void SirkulasiMasuk6KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk6KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk6KeyPressed

    private void PernapasanMasuk7KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk7KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk7KeyPressed

    private void SirkulasiMasuk7KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk7KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk7KeyPressed

    private void PernapasanMasuk8KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk8KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk8KeyPressed

    private void SirkulasiMasuk8KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk8KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk8KeyPressed

    private void PernapasanMasuk9KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk9KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk9KeyPressed

    private void PernapasanMasuk10KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk10KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk10KeyPressed

    private void PernapasanMasuk11KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk11KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk11KeyPressed

    private void SirkulasiMasuk9KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk9KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk9KeyPressed

    private void PernapasanMasuk13KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PernapasanMasuk13KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PernapasanMasuk13KeyPressed

    private void SirkulasiMasuk10KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk10KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk10KeyPressed

    private void SirkulasiMasuk11KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk11KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk11KeyPressed

    private void SirkulasiMasuk12KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk12KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk12KeyPressed

    private void SirkulasiMasuk13KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SirkulasiMasuk13KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_SirkulasiMasuk13KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMObservasiAnestesi dialog = new RMObservasiAnestesi(new javax.swing.JFrame(), true);
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
    private widget.TextBox AktifitasMasuk;
    private widget.TextBox AktifitasMasuk1;
    private widget.TextBox AktifitasMasuk2;
    private widget.TextBox AktifitasMasuk3;
    private widget.TextBox AktifitasMasuk4;
    private widget.ComboBox BilaSpontan;
    private widget.ComboBox BilaSpontan1;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.PanelBiasa FormInput2;
    private widget.PanelBiasa FormInput3;
    private widget.PanelBiasa FormInput4;
    private widget.TextBox Infus;
    private widget.TextBox Infus1;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdDokter2;
    private widget.TextBox KdDokter3;
    private widget.ComboBox Kesadaran;
    private widget.ComboBox Kesadaran1;
    private widget.ComboBox Kesadaran2;
    private widget.TextBox KesadaranMasuk;
    private widget.TextBox KesadaranMasuk1;
    private widget.TextBox KesadaranMasuk2;
    private widget.TextBox KesadaranMasuk3;
    private widget.TextBox KesadaranMasuk4;
    private widget.TextBox KesadaranMasuk5;
    private widget.TextBox KesadaranMasuk6;
    private widget.TextBox Kesakitan;
    private widget.TextBox Kesakitan1;
    private widget.TextBox Lainnya;
    private widget.TextBox Lainnya1;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox MualMuntah;
    private widget.TextBox MualMuntah1;
    private widget.TextBox MualMuntah2;
    private widget.TextBox NIP;
    private widget.TextBox NIP1;
    private widget.TextBox NamaPegawai;
    private widget.TextBox NamaPegawai1;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmDokter2;
    private widget.TextBox NmDokter3;
    private widget.TextBox Obat;
    private widget.TextBox Obat1;
    private widget.TextBox PemantauanNadi;
    private widget.TextBox PemantauanNadi1;
    private widget.ComboBox Pernapasan;
    private widget.ComboBox Pernapasan1;
    private widget.ComboBox Pernapasan10;
    private widget.ComboBox Pernapasan2;
    private widget.ComboBox Pernapasan3;
    private widget.ComboBox Pernapasan4;
    private widget.ComboBox Pernapasan5;
    private widget.ComboBox Pernapasan6;
    private widget.ComboBox Pernapasan7;
    private widget.ComboBox Pernapasan8;
    private widget.ComboBox Pernapasan9;
    private widget.TextBox PernapasanMasuk;
    private widget.TextBox PernapasanMasuk1;
    private widget.TextBox PernapasanMasuk10;
    private widget.TextBox PernapasanMasuk11;
    private widget.TextBox PernapasanMasuk13;
    private widget.TextBox PernapasanMasuk2;
    private widget.TextBox PernapasanMasuk3;
    private widget.TextBox PernapasanMasuk4;
    private widget.TextBox PernapasanMasuk5;
    private widget.TextBox PernapasanMasuk6;
    private widget.TextBox PernapasanMasuk7;
    private widget.TextBox PernapasanMasuk8;
    private widget.TextBox PernapasanMasuk9;
    private widget.TextBox Selama;
    private widget.TextBox Selama1;
    private widget.TextBox SirkulasiMasuk;
    private widget.TextBox SirkulasiMasuk1;
    private widget.TextBox SirkulasiMasuk10;
    private widget.TextBox SirkulasiMasuk11;
    private widget.TextBox SirkulasiMasuk12;
    private widget.TextBox SirkulasiMasuk13;
    private widget.TextBox SirkulasiMasuk2;
    private widget.TextBox SirkulasiMasuk3;
    private widget.TextBox SirkulasiMasuk4;
    private widget.TextBox SirkulasiMasuk5;
    private widget.TextBox SirkulasiMasuk6;
    private widget.TextBox SirkulasiMasuk7;
    private widget.TextBox SirkulasiMasuk8;
    private widget.TextBox SirkulasiMasuk9;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawat1;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglOperasi;
    private widget.Tanggal TglOperasi1;
    private widget.TextBox TotalMasuk;
    private widget.TextBox TotalMasuk1;
    private widget.TextBox TotalMasuk2;
    private widget.TextBox TotalMasuk3;
    private widget.TextBox WarnaKulitMasuk;
    private widget.TextBox WarnaKulitMasuk1;
    private widget.TextBox WarnaKulitMasuk2;
    private widget.TextBox WarnaKulitMasuk3;
    private widget.TextBox WarnaKulitMasuk4;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
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
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollInput1;
    private widget.ScrollPane scrollInput2;
    private widget.ScrollPane scrollInput3;
    private widget.ScrollPane scrollInput4;
    private widget.ScrollPane scrollPane1;
    private widget.TextArea textArea1;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        /*
         * Valid.tabelKosong(tabMode);
         * try{
         * if(TCari.getText().trim().equals("")){
         * ps=koneksi.prepareStatement(
         * "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"
         * +
         * "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
         * "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
         * "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
         * "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
         * "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
         * "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
         * "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
         * "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "
         * +
         * "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
         * +
         * "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "
         * +
         * "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "
         * +
         * "penilaian_pre_anestesi.tanggal between ? and ? order by penilaian_pre_anestesi.tanggal"
         * );
         * }else{
         * ps=koneksi.prepareStatement(
         * "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"
         * +
         * "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
         * "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
         * "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
         * "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
         * "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
         * "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
         * "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
         * "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
         * "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "
         * +
         * "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
         * +
         * "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "
         * +
         * "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "
         * +
         * "penilaian_pre_anestesi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
         * +
         * "penilaian_pre_anestesi.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_pre_anestesi.tanggal"
         * );
         * }
         * 
         * try {
         * if(TCari.getText().trim().equals("")){
         * ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
         * ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
         * }else{
         * ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
         * ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
         * ps.setString(3,"%"+TCari.getText()+"%");
         * ps.setString(4,"%"+TCari.getText()+"%");
         * ps.setString(5,"%"+TCari.getText()+"%");
         * ps.setString(6,"%"+TCari.getText()+"%");
         * ps.setString(7,"%"+TCari.getText()+"%");
         * }
         * rs=ps.executeQuery();
         * while(rs.next()){
         * tabMode.addRow(new String[]{
         * rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString(
         * "nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString(
         * "kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
         * rs.getString("tanggal_operasi"),rs.getString("diagnosa"),rs.getString(
         * "rencana_tindakan"),rs.getString("tb"),rs.getString("bb"),rs.getString("td"),
         * rs.getString("io2"),rs.getString("nadi"),rs.getString("pernapasan"),
         * rs.getString("suhu"),rs.getString("fisik_cardiovasculer"),rs.getString(
         * "fisik_paru"),rs.getString("fisik_abdomen"),rs.getString("fisik_extrimitas"),
         * rs.getString("fisik_endokrin"),rs.getString("fisik_ginjal"),
         * rs.getString("fisik_obatobatan"),rs.getString("fisik_laborat"),rs.getString(
         * "fisik_penunjang"),rs.getString("riwayat_penyakit_alergiobat"),rs.getString(
         * "riwayat_penyakit_alergilainnya"),
         * rs.getString("riwayat_penyakit_terapi"),rs.getString(
         * "riwayat_kebiasaan_merokok"),rs.getString("riwayat_kebiasaan_ket_merokok"),rs
         * .getString("riwayat_kebiasaan_alkohol"),rs.getString(
         * "riwayat_kebiasaan_ket_alkohol"),
         * rs.getString("riwayat_kebiasaan_obat"),rs.getString(
         * "riwayat_kebiasaan_ket_obat"),rs.getString("riwayat_medis_cardiovasculer"),rs
         * .getString("riwayat_medis_respiratory"),rs.getString(
         * "riwayat_medis_endocrine"),
         * rs.getString("riwayat_medis_lainnya"),rs.getString("asa"),rs.getString(
         * "puasa"),rs.getString("rencana_anestesi"),rs.getString("rencana_perawatan"),
         * rs.getString("catatan_khusus")
         * });
         * }
         * } catch (Exception e) {
         * System.out.println("Notif : "+e);
         * } finally{
         * if(rs!=null){
         * rs.close();
         * }
         * if(ps!=null){
         * ps.close();
         * }
         * }
         * 
         * }catch(Exception e){
         * System.out.println("Notifikasi : "+e);
         * }
         * LCount.setText(""+tabMode.getRowCount());
         */
    }

    public void emptTeks() {

        // Pernapasan.setText("");

        TglOperasi.setDate(new Date());

        TabRawat.setSelectedIndex(0);

    }

    private void getData() {
        /*
         * if(tbObat.getSelectedRow()!= -1){
         * TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
         * TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
         * TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
         * TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
         * Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
         * Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
         * RencanaTindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).
         * toString());
         * TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
         * BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
         * TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
         * IO2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
         * Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
         * Pernapasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
         * Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
         * FisikCardio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())
         * ;
         * FisikParu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
         * FisikAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()
         * );
         * FisikExtrimitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).
         * toString());
         * FisikEndokrin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString(
         * ));
         * FisikGinjal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())
         * ;
         * FisikObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
         * FisikLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()
         * );
         * FisikPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString
         * ());
         * PenyakitAlergiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).
         * toString());
         * PenyakitAlergiLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).
         * toString());
         * PenyakitTerapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString
         * ());
         * PenyakitKebiasaanMerokok.setSelectedItem(tbObat.getValueAt(tbObat.
         * getSelectedRow(),30).toString());
         * PenyakitKebiasaanJumlahRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(
         * ),31).toString());
         * PenyakitKebiasaanAlkohol.setSelectedItem(tbObat.getValueAt(tbObat.
         * getSelectedRow(),32).toString());
         * PenyakitKebiasaanJumlahAlkohol.setText(tbObat.getValueAt(tbObat.
         * getSelectedRow(),33).toString());
         * PenyakitKebiasaanObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow
         * (),34).toString());
         * PenyakitKebiasaanObatDiminum.setText(tbObat.getValueAt(tbObat.getSelectedRow(
         * ),35).toString());
         * MedisCardio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())
         * ;
         * MedisRespiratory.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).
         * toString());
         * MedisEndocrine.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString
         * ());
         * MedisLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()
         * );
         * AngkaASA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).
         * toString());
         * RencanaAnestesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42)
         * .toString());
         * RencanaPerawatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).
         * toString());
         * CatatanKhusus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString(
         * ));
         * Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString
         * ());
         * Valid.SetTgl2(TglOperasi,tbObat.getValueAt(tbObat.getSelectedRow(),8).
         * toString());
         * Valid.SetTgl2(TglPuasa,tbObat.getValueAt(tbObat.getSelectedRow(),41).toString
         * ());
         * }
         */
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "
                            +
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    // DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        // Reload all tab tables
        tampilPreAnestesi();
        tampilSelamaAnestesi();
        tampilKamarPemulihan();
    }

    private void tampilPreAnestesi() {
        try {
            Valid.tabelKosong(tabModePreAnestesi);
            ps = koneksi.prepareStatement(
                    "SELECT no_rawat, tanggal, kd_dokter_anestesi, kd_asisten_anestesi, " +
                            "kd_dokter_bedah, diagnosa_bedah, jenis_pembedahan, diagnosa_pasca_bedah, " +
                            "teknik_anestesi, status_fisik_asa, alergi, penyakit_pre_anestesi, " +
                            "checklist_persiapan, kesadaran, suhu, bb, td, sat_o2, nadi, tb, rr, lainnya, catatan, catatan "
                            +
                            "FROM catatan_pre_anestesi_obs WHERE no_rawat = ? ORDER BY tanggal");
            ps.setString(1, TNoRw.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                tabModePreAnestesi.addRow(new String[] {
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24)
                });
            }
        } catch (Exception e) {
            System.out.println("tampilPreAnestesi error: " + e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
        }
    }

    private void tampilSelamaAnestesi() {
        try {
            Valid.tabelKosong(tabModeSelamaAnestesi);
            ps = koneksi.prepareStatement(
                    "SELECT no_rawat, tanggal, infus_perifer, premedikasi, posisi, " +
                            "oral, jam_oral, im, jam_im, iv, jam_iv, induksi, jalan_nafas, " +
                            "intubasi, ventilasi, tvr, rr, teknik_regional, type, " +
                            "daerah_pemasangan, jarum_no, kateter, bromage_skor, tanggal " +
                            "FROM catatan_selama_anestesi_obs WHERE no_rawat = ? ORDER BY tanggal");
            ps.setString(1, TNoRw.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeSelamaAnestesi.addRow(new String[] {
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24)
                });
            }
        } catch (Exception e) {
            System.out.println("tampilSelamaAnestesi error: " + e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
        }
    }

    private void tampilKamarPemulihan() {
        try {
            Valid.tabelKosong(tabModeKamarPemulihan);
            ps = koneksi.prepareStatement(
                    "SELECT no_rawat, tanggal, pernapasan, spontan, status_kesadaran, " +
                            "aktifitas1, sirkulasi1, pernapasan1, kesadaran1, warnakulit1, total1, " +
                            "jamdi, aktifitas2, sirkulasi2, pernapasan2, kesadaran2, kulit2, total2, vas, " +
                            "jamkeluar, aktifitas3, sirkulasi3, pernapasan3, kesadaran3, kulit3, total3, " +
                            "ke, catatan, kesakitan, mualmuntah, obat, infus, pemantauan, selama, lainnya, pegawai " +
                            "FROM catatan_kamar_pemulihan_obs WHERE no_rawat = ? ORDER BY tanggal");
            ps.setString(1, TNoRw.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                // tabModeKamarPemulihan has 38 cols:
                // 0=No.Rawat,1=No.R.M.(pad),2=Nama(pad),3=Tgl.Lahir(pad),4=Jam
                // Masuk,5=Pernapasan...
                tabModeKamarPemulihan.addRow(new String[] {
                        rs.getString(1), // No.Rawat
                        rs.getString(1), // No.R.M. (pad with no_rawat)
                        "", // Nama Pasien (not in obs table)
                        TglLahir.getText(), // Tgl.Lahir from form
                        rs.getString(2), // Jam Masuk = tanggal
                        rs.getString(3), // Pernapasan
                        rs.getString(4), // Bila Spontan
                        rs.getString(5), // Kesadaran
                        rs.getString(6), // Aktifitas 1
                        rs.getString(7), // Sirkulasi 1
                        rs.getString(8), // Pernapasan 1
                        rs.getString(9), // Kesadaran 1
                        rs.getString(10), // Warna Kulit 1
                        rs.getString(11), // Total 1
                        rs.getString(12), // Jam Di
                        rs.getString(13), // Aktifitas 2
                        rs.getString(14), // Sirkulasi 2
                        rs.getString(15), // Pernapasan 2
                        rs.getString(16), // Kesadaran 2
                        rs.getString(17), // Warna Kulit 2
                        rs.getString(18), // Total 2
                        rs.getString(19), // VAS
                        rs.getString(20), // Jam Keluar
                        rs.getString(21), // Aktifitas 3
                        rs.getString(22), // Sirkulasi 3
                        rs.getString(23), // Pernapasan 3
                        rs.getString(24), // Kesadaran 3
                        rs.getString(25), // Warna Kulit 3
                        rs.getString(27), // Ke
                        rs.getString(29), // Bila Kesakitan
                        rs.getString(30), // Bila Mual
                        rs.getString(31), // Obat
                        rs.getString(32), // Infus
                        rs.getString(33), // Pemantauan
                        rs.getString(34), // Selama
                        rs.getString(35), // Lainnya
                        rs.getString(36), // NIP
                        rs.getString(36) // Nama Petugas (pad with pegawai NIP)
                });
            }
        } catch (Exception e) {
            System.out.println("tampilKamarPemulihan error: " + e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        // TCari.setText(norwt);
        // DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void setNoRm(String norwt, Date tgl2, String kdDokter, String nmDokter, String kdDokter3, String nmDokter3) {
        TNoRw.setText(norwt);
        KdDokter.setText(kdDokter);
        NmDokter.setText(nmDokter);
        // TCari.setText(norwt);
        // DTPCari2.setDate(tgl2);
        isRawat();
        KdDokter3.setText(kdDokter3);
        NmDokter3.setText(nmDokter3);
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnHapus.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
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

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        /*
         * if(Sequel.
         * queryu2tf("delete from penilaian_pre_anestesi where no_rawat=? and tanggal=?"
         * ,2,new String[]{
         * tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(
         * tbObat.getSelectedRow(),7).toString()
         * })==true){
         * tabMode.removeRow(tbObat.getSelectedRow());
         * LCount.setText(""+tabMode.getRowCount());
         * TabRawat.setSelectedIndex(1);
         * }else{
         * JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
         * }
         */
    }

    private void ganti() {
        /*
         * if(Sequel.mengedittf("penilaian_pre_anestesi","no_rawat=? and tanggal=?"
         * ,"no_rawat=?,tanggal=?,kd_dokter=?,tanggal_operasi=?,diagnosa=?,rencana_tindakan=?,tb=?,bb=?,td=?,io2=?,nadi=?,"+
         * "pernapasan=?,suhu=?,fisik_cardiovasculer=?,fisik_paru=?,fisik_abdomen=?,fisik_extrimitas=?,fisik_endokrin=?,fisik_ginjal=?,fisik_obatobatan=?,fisik_laborat=?,fisik_penunjang=?,"+
         * "riwayat_penyakit_alergiobat=?,riwayat_penyakit_alergilainnya=?,riwayat_penyakit_terapi=?,riwayat_kebiasaan_merokok=?,riwayat_kebiasaan_ket_merokok=?,riwayat_kebiasaan_alkohol=?,"+
         * "riwayat_kebiasaan_ket_alkohol=?,riwayat_kebiasaan_obat=?,riwayat_kebiasaan_ket_obat=?,riwayat_medis_cardiovasculer=?,riwayat_medis_respiratory=?,riwayat_medis_endocrine=?,"+
         * "riwayat_medis_lainnya=?,asa=?,puasa=?,rencana_anestesi=?,rencana_perawatan=?,catatan_khusus=?"
         * ,42,new String[]{
         * TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.
         * getSelectedItem().toString().substring(11,19),KdDokter.getText(),
         * Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "+TglOperasi.getSelectedItem(
         * ).toString().substring(11,19),Diagnosa.getText(),RencanaTindakan.getText(),
         * TB.getText(),BB.getText(),TD.getText(),IO2.getText(),Nadi.getText(),
         * Pernapasan.getText(),Suhu.getText(),FisikCardio.getText(),FisikParu.getText()
         * ,
         * FisikAbdomen.getText(),FisikExtrimitas.getText(),FisikEndokrin.getText(),
         * FisikGinjal.getText(),FisikObat.getText(),FisikLaborat.getText(),
         * FisikPenunjang.getText(),PenyakitAlergiObat.getText(),PenyakitAlergiLainnya.
         * getText(),PenyakitTerapi.getText(),PenyakitKebiasaanMerokok.getSelectedItem()
         * .toString(),
         * PenyakitKebiasaanJumlahRokok.getText(),PenyakitKebiasaanAlkohol.
         * getSelectedItem().toString(),PenyakitKebiasaanJumlahAlkohol.getText(),
         * PenyakitKebiasaanObat.getSelectedItem().toString(),
         * PenyakitKebiasaanObatDiminum.getText(),MedisCardio.getText(),MedisRespiratory
         * .getText(),MedisEndocrine.getText(),
         * MedisLainnya.getText(),AngkaASA.getSelectedItem().toString(),Valid.SetTgl(
         * TglPuasa.getSelectedItem()+"")+" "+TglPuasa.getSelectedItem().toString().
         * substring(11,19),
         * RencanaAnestesi.getSelectedItem().toString(),RencanaPerawatan.getText(),
         * CatatanKhusus.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString
         * (),
         * tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
         * })==true){
         * tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
         * tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
         * tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
         * tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
         * tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
         * tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
         * tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
         * tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.
         * getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
         * tbObat.setValueAt(Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "
         * +TglOperasi.getSelectedItem().toString().substring(11,19),tbObat.
         * getSelectedRow(),8);
         * tbObat.setValueAt(Diagnosa.getText(),tbObat.getSelectedRow(),9);
         * tbObat.setValueAt(RencanaTindakan.getText(),tbObat.getSelectedRow(),10);
         * tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),11);
         * tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),12);
         * tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),13);
         * tbObat.setValueAt(IO2.getText(),tbObat.getSelectedRow(),14);
         * tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),15);
         * tbObat.setValueAt(Pernapasan.getText(),tbObat.getSelectedRow(),16);
         * tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),17);
         * tbObat.setValueAt(FisikCardio.getText(),tbObat.getSelectedRow(),18);
         * tbObat.setValueAt(FisikParu.getText(),tbObat.getSelectedRow(),19);
         * tbObat.setValueAt(FisikAbdomen.getText(),tbObat.getSelectedRow(),20);
         * tbObat.setValueAt(FisikExtrimitas.getText(),tbObat.getSelectedRow(),21);
         * tbObat.setValueAt(FisikEndokrin.getText(),tbObat.getSelectedRow(),22);
         * tbObat.setValueAt(FisikGinjal.getText(),tbObat.getSelectedRow(),23);
         * tbObat.setValueAt(FisikObat.getText(),tbObat.getSelectedRow(),24);
         * tbObat.setValueAt(FisikLaborat.getText(),tbObat.getSelectedRow(),25);
         * tbObat.setValueAt(FisikPenunjang.getText(),tbObat.getSelectedRow(),26);
         * tbObat.setValueAt(PenyakitAlergiObat.getText(),tbObat.getSelectedRow(),27);
         * tbObat.setValueAt(PenyakitAlergiLainnya.getText(),tbObat.getSelectedRow(),28)
         * ;
         * tbObat.setValueAt(PenyakitTerapi.getText(),tbObat.getSelectedRow(),29);
         * tbObat.setValueAt(PenyakitKebiasaanMerokok.getSelectedItem().toString(),
         * tbObat.getSelectedRow(),30);
         * tbObat.setValueAt(PenyakitKebiasaanJumlahRokok.getText(),tbObat.
         * getSelectedRow(),31);
         * tbObat.setValueAt(PenyakitKebiasaanAlkohol.getSelectedItem().toString(),
         * tbObat.getSelectedRow(),32);
         * tbObat.setValueAt(PenyakitKebiasaanJumlahAlkohol.getText(),tbObat.
         * getSelectedRow(),33);
         * tbObat.setValueAt(PenyakitKebiasaanObat.getSelectedItem().toString(),tbObat.
         * getSelectedRow(),34);
         * tbObat.setValueAt(PenyakitKebiasaanObatDiminum.getText(),tbObat.
         * getSelectedRow(),35);
         * tbObat.setValueAt(MedisCardio.getText(),tbObat.getSelectedRow(),36);
         * tbObat.setValueAt(MedisRespiratory.getText(),tbObat.getSelectedRow(),37);
         * tbObat.setValueAt(MedisEndocrine.getText(),tbObat.getSelectedRow(),38);
         * tbObat.setValueAt(MedisLainnya.getText(),tbObat.getSelectedRow(),39);
         * tbObat.setValueAt(AngkaASA.getSelectedItem().toString(),tbObat.getSelectedRow
         * (),40);
         * tbObat.setValueAt(Valid.SetTgl(TglPuasa.getSelectedItem()+"")+" "+TglPuasa.
         * getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),41);
         * tbObat.setValueAt(RencanaAnestesi.getSelectedItem().toString(),tbObat.
         * getSelectedRow(),42);
         * tbObat.setValueAt(RencanaPerawatan.getText(),tbObat.getSelectedRow(),43);
         * tbObat.setValueAt(CatatanKhusus.getText(),tbObat.getSelectedRow(),44);
         * emptTeks();
         * TabRawat.setSelectedIndex(1);
         * }
         */
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

    private void initObsAnestesi() {
        // Assignment removed to respect custom column definition

        javax.swing.JPanel PanelAtas = new javax.swing.JPanel();
        PanelAtas.setName("PanelAtas");
        PanelAtas.setOpaque(false);
        PanelAtas.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame1.remove(TabRawat);
        internalFrame1.remove(panelGlass10);

        PanelAtas.add(panelGlass10, java.awt.BorderLayout.CENTER);

        ChkInput = new widget.CekBox();
        ChkInput.setText(".: Input Data Observasi Anestesi");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolean show = ChkInput.isSelected();
                panelGlass10.setVisible(show);
                FormInput3.setVisible(show);
                FormInput4.setVisible(show);
                TabRawat1.setVisible(show);

                internalFrame1.revalidate();
                internalFrame1.repaint();
            }
        });
        ChkInput.setSelected(true);
        PanelAtas.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelAtas, java.awt.BorderLayout.PAGE_START);

        Scroll1 = new widget.ScrollPane();
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        tbPreAnestesi = new widget.Table();
        tbPreAnestesi.setName("tbPreAnestesi"); // NOI18N
        tbPreAnestesi.setModel(tabModePreAnestesi);
        Scroll1.setViewportView(tbPreAnestesi);
        internalFrame5.add(Scroll1, java.awt.BorderLayout.CENTER);

        Scroll2 = new widget.ScrollPane();
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);
        tbSelamaAnestesi = new widget.Table();
        tbSelamaAnestesi.setName("tbSelamaAnestesi"); // NOI18N
        tbSelamaAnestesi.setModel(tabModeSelamaAnestesi);
        Scroll2.setViewportView(tbSelamaAnestesi);
        internalFrame6.add(Scroll2, java.awt.BorderLayout.CENTER);

        // tbPreAnestesi row click → parse to FormInput3 fields
        tbPreAnestesi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbPreAnestesi.getSelectedRow() >= 0) {
                int r = tbPreAnestesi.getSelectedRow();
                // col2=KdDokterAnestesi, col3=KdAsisten, col4=KdDokterBedah
                KdDokter2.setText(
                        tabModePreAnestesi.getValueAt(r, 2) != null ? tabModePreAnestesi.getValueAt(r, 2).toString()
                                : "");
                NIP1.setText(
                        tabModePreAnestesi.getValueAt(r, 3) != null ? tabModePreAnestesi.getValueAt(r, 3).toString()
                                : "");
                KdDokter3.setText(
                        tabModePreAnestesi.getValueAt(r, 4) != null ? tabModePreAnestesi.getValueAt(r, 4).toString()
                                : "");
                // col5..12 = text fields
                safeSetText(Kesakitan1, tabModePreAnestesi.getValueAt(r, 5));
                safeSetText(MualMuntah1, tabModePreAnestesi.getValueAt(r, 6));
                safeSetText(Obat1, tabModePreAnestesi.getValueAt(r, 7));
                safeSetCombo(Pernapasan2, tabModePreAnestesi.getValueAt(r, 8));
                safeSetCombo(Pernapasan1, tabModePreAnestesi.getValueAt(r, 9));
                safeSetCombo(Pernapasan3, tabModePreAnestesi.getValueAt(r, 10));
                safeSetText(Selama1, tabModePreAnestesi.getValueAt(r, 11));
                safeSetCombo(Kesadaran2, tabModePreAnestesi.getValueAt(r, 12));
                safeSetText(AktifitasMasuk4, tabModePreAnestesi.getValueAt(r, 13));
                safeSetText(SirkulasiMasuk4, tabModePreAnestesi.getValueAt(r, 14));
                safeSetText(PernapasanMasuk5, tabModePreAnestesi.getValueAt(r, 15));
                safeSetText(KesadaranMasuk5, tabModePreAnestesi.getValueAt(r, 16));
                safeSetText(WarnaKulitMasuk3, tabModePreAnestesi.getValueAt(r, 17));
                safeSetText(TotalMasuk3, tabModePreAnestesi.getValueAt(r, 18));
                safeSetText(SirkulasiMasuk5, tabModePreAnestesi.getValueAt(r, 19));
                safeSetText(PernapasanMasuk6, tabModePreAnestesi.getValueAt(r, 20));
                safeSetText(KesadaranMasuk6, tabModePreAnestesi.getValueAt(r, 21));
                safeSetText(WarnaKulitMasuk4, tabModePreAnestesi.getValueAt(r, 22));
                safeSetText(MualMuntah2, tabModePreAnestesi.getValueAt(r, 23));
            }
        });

        // tbSelamaAnestesi row click → parse to FormInput4 fields
        tbSelamaAnestesi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbSelamaAnestesi.getSelectedRow() >= 0) {
                int r = tbSelamaAnestesi.getSelectedRow();
                // col 3=Infus Perifer, 4=Premedikasi, 5=Posisi
                safeSetTextArea(textArea1, tabModeSelamaAnestesi.getValueAt(r, 3));
                safeSetCombo(Pernapasan4, tabModeSelamaAnestesi.getValueAt(r, 4));
                safeSetCombo(Pernapasan5, tabModeSelamaAnestesi.getValueAt(r, 5));
                // col 6=Oral, 7=Jam Oral, 8=IM, 9=Jam IM, 10=IV, 11=Jam IV
                safeSetText(SirkulasiMasuk6, tabModeSelamaAnestesi.getValueAt(r, 6));
                safeSetText(PernapasanMasuk7, tabModeSelamaAnestesi.getValueAt(r, 7));
                safeSetText(SirkulasiMasuk7, tabModeSelamaAnestesi.getValueAt(r, 8));
                safeSetText(PernapasanMasuk8, tabModeSelamaAnestesi.getValueAt(r, 9));
                safeSetText(SirkulasiMasuk8, tabModeSelamaAnestesi.getValueAt(r, 10));
                safeSetText(PernapasanMasuk10, tabModeSelamaAnestesi.getValueAt(r, 11));
                // col 12=Induksi, 13=Jalan Nafas, 14=Intubasi, 15=Ventilasi
                safeSetCombo(Pernapasan5, tabModeSelamaAnestesi.getValueAt(r, 12));
                safeSetCombo(Pernapasan7, tabModeSelamaAnestesi.getValueAt(r, 13));
                safeSetCombo(Pernapasan9, tabModeSelamaAnestesi.getValueAt(r, 14));
                safeSetCombo(Pernapasan8, tabModeSelamaAnestesi.getValueAt(r, 15));
                // col 16=TVR, 17=RR, 18=Teknik Regional, 19=Type, 20=Daerah, 21=Jarum No
                safeSetText(SirkulasiMasuk9, tabModeSelamaAnestesi.getValueAt(r, 16));
                safeSetText(PernapasanMasuk13, tabModeSelamaAnestesi.getValueAt(r, 17));
                safeSetText(SirkulasiMasuk12, tabModeSelamaAnestesi.getValueAt(r, 18));
                safeSetText(SirkulasiMasuk11, tabModeSelamaAnestesi.getValueAt(r, 19));
                safeSetText(SirkulasiMasuk10, tabModeSelamaAnestesi.getValueAt(r, 20));
                safeSetText(SirkulasiMasuk13, tabModeSelamaAnestesi.getValueAt(r, 21));
                // col 22=Kateter, 23=Bromage Skor
                safeSetCombo(Pernapasan6, tabModeSelamaAnestesi.getValueAt(r, 22));
                safeSetCombo(Pernapasan10, tabModeSelamaAnestesi.getValueAt(r, 23));
            }
        });

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
    }

    /** Helper: safely set text to a widget.TextBox or javax.swing.JTextField */
    private void safeSetText(Object field, Object val) {
        String v = val != null ? val.toString() : "";
        if (field instanceof widget.TextBox)
            ((widget.TextBox) field).setText(v);
        else if (field instanceof javax.swing.JTextField)
            ((javax.swing.JTextField) field).setText(v);
    }

    /** Helper: safely set text to a javax.swing.JTextArea */
    private void safeSetTextArea(javax.swing.JTextArea area, Object val) {
        if (area != null)
            area.setText(val != null ? val.toString() : "");
    }

    /** Helper: safely select item in a JComboBox */
    private void safeSetCombo(javax.swing.JComboBox<?> combo, Object val) {
        if (combo == null || val == null)
            return;
        String v = val.toString();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).toString().equals(v)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}
