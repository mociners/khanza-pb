/*
 * Berdasarkan form fisik RS THB
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;

/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRanapAnak extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private StringBuilder htmlContent;
    private String finger = "";

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMPenilaianAwalMedisRanapAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No RM", "Nama Pasien", "Tanggal", "Kode Dokter", "Nama Dokter", "Anamnesis", "Hubungan", "Keluhan Utama",
            "Riwayat Penyakit Sekarang (RPS)", "Riwayat Penyakit Dahulu (RPD)", "Riwayat Penyakit Keluarga (RPK)",
            "Riwayat Penggunaan Obat (RPO)", "Implant", "Kepala", "Ket. Kepala", "Mata", "Ket. Mata", "Gigi & Mulut",
            "Ket. Gigi & Mulut", "THT", "Ket. THT", "Leher", "Ket. Leher", "Thoraks", "Ket. Thoraks", "Jantung",
            "Ket. Jantung", "Paru", "Ket. Paru", "Abdomen", "Ket. Abdomen", "Punggung", "Ket. Punggung", "Genital & Anus",
            "Ket. Genital & Anus", "Ekstremitas", "Ket. Ekstremitas", "Kulit", "Ket. Kulit", "Laboratorium",
            "Radiologi", "Penunjang Lainnya", "Diagnosis/Asesmen", "Diagnosis Banding", "Tatalaksana", "Nutrisi",
            "Konsul", "Isi Konsul", "Tindakan", "Isi Tindakan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 51; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(105);
                    break;
                case 1:
                    column.setPreferredWidth(70);
                    break;
                case 2:
                    column.setPreferredWidth(150);
                    break;
                case 3:
                    column.setPreferredWidth(65);
                    break;
                case 4:
                    column.setPreferredWidth(55);
                    break;
                case 5:
                    column.setPreferredWidth(80);
                    break;
                case 6:
                    column.setPreferredWidth(150);
                    break;
                case 7:
                    column.setPreferredWidth(115);
                    break;
                case 8:
                    column.setPreferredWidth(80);
                    break;
                case 9:
                    column.setPreferredWidth(100);
                    break;
                case 10:
                    column.setPreferredWidth(300);
                    break;
                case 11:
                    column.setPreferredWidth(150);
                    break;
                case 12:
                    column.setPreferredWidth(150);
                    break;
                case 13:
                    column.setPreferredWidth(150);
                    break;
                case 14:
                    column.setPreferredWidth(150);
                    break;
                case 15:
                    column.setPreferredWidth(120);
                    break;
                case 16:
                    column.setPreferredWidth(90);
                    break;
                case 17:
                    column.setPreferredWidth(50);
                    break;
                case 18:
                    column.setPreferredWidth(80);
                    break;
                case 19:
                    column.setPreferredWidth(60);
                    break;
                case 20:
                    column.setPreferredWidth(75);
                    break;
                case 21:
                    column.setPreferredWidth(67);
                    break;
                case 22:
                    column.setPreferredWidth(40);
                    break;
                case 23:
                    column.setPreferredWidth(40);
                    break;
                case 24:
                    column.setPreferredWidth(40);
                    break;
                case 25:
                    column.setPreferredWidth(40);
                    break;
                case 26:
                    column.setPreferredWidth(80);
                    break;
                case 27:
                    column.setPreferredWidth(80);
                    break;
                case 28:
                    column.setPreferredWidth(80);
                    break;
                case 29:
                    column.setPreferredWidth(80);
                    break;
                case 30:
                    column.setPreferredWidth(80);
                    break;
                case 31:
                    column.setPreferredWidth(80);
                    break;
                case 32:
                    column.setPreferredWidth(80);
                    break;
                case 33:
                    column.setPreferredWidth(80);
                    break;
                case 34:
                    column.setPreferredWidth(80);
                    break;
                case 35:
                    column.setPreferredWidth(80);
                    break;
                case 36:
                    column.setPreferredWidth(80);
                    break;
                case 37:
                    column.setPreferredWidth(300);
                    break;
                case 38:
                    column.setPreferredWidth(200);
                    break;
                case 39:
                    column.setPreferredWidth(170);
                    break;
                case 40:
                    column.setPreferredWidth(170);
                    break;
                case 41:
                    column.setPreferredWidth(170);
                    break;
                case 42:
                    column.setPreferredWidth(150);
                    break;
                case 43:
                    column.setPreferredWidth(300);
                    break;
                case 44:
                    column.setPreferredWidth(150);
                    break;
                case 45:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
                case 46:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
                case 47:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
                case 48:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
                case 49:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
                case 50:
                    column.setPreferredWidth(150);
                    break; // Kolom tambahan
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int) 30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int) 2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int) 2000).getKata(RPS));
        RPK.setDocument(new batasInput((int) 2000).getKata(RPK));
        RPD.setDocument(new batasInput((int) 1000).getKata(RPD));
        RPO.setDocument(new batasInput((int) 1000).getKata(RPO));
        KetKepala.setDocument(new batasInput((int) 50).getKata(KetKepala));
        KetThoraks.setDocument(new batasInput((byte) 10).getKata(KetThoraks));
        KetMata.setDocument(new batasInput((byte) 8).getKata(KetMata));
        KetGigi.setDocument(new batasInput((byte) 5).getKata(KetGigi));
        KetKulit.setDocument(new batasInput((byte) 5).getKata(KetKulit));
        KetJantung.setDocument(new batasInput((byte) 5).getKata(KetJantung));
        KetAbdomen.setDocument(new batasInput((byte) 5).getKata(KetAbdomen));
        KetGenital.setDocument(new batasInput((byte) 5).getKata(KetGenital));
        KetParu.setDocument(new batasInput((byte) 5).getKata(KetParu));
        KetKulit.setDocument(new batasInput((int) 5000).getKata(KetKulit));
        Radiologi.setDocument(new batasInput((int) 3000).getKata(Radiologi));
        Penunjang.setDocument(new batasInput((int) 3000).getKata(Penunjang));
        Diagnosis.setDocument(new batasInput((int) 500).getKata(Diagnosis));
        Tatalaksana.setDocument(new batasInput((int) 5000).getKata(Tatalaksana));
        KetKonsul.setDocument(new batasInput((int) 1000).getKata(KetKonsul));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
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

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        KetGenital = new widget.TextBox();
        KetParu = new widget.TextBox();
        KetGigi = new widget.TextBox();
        KetJantung = new widget.TextBox();
        KetMata = new widget.TextBox();
        KetKulit = new widget.TextBox();
        KetKepala = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        KetThoraks = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Konsul = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Punggung = new widget.ComboBox();
        jLabel41 = new widget.Label();
        KetAbdomen = new widget.TextBox();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gigi = new widget.ComboBox();
        jLabel45 = new widget.Label();
        THT = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Genital = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Kulit = new widget.ComboBox();
        jLabel99 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jLabel103 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        KetKonsul = new widget.TextArea();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel42 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel47 = new widget.Label();
        Jantung = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Paru = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        jLabel82 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jLabel43 = new widget.Label();
        Implant = new widget.ComboBox();
        KetTHT = new widget.TextBox();
        KetLeher = new widget.TextBox();
        KetPunggung = new widget.TextBox();
        KetEkstermitas = new widget.TextBox();
        jLabel105 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        DiagnosisBanding = new widget.TextArea();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        Nutrisi = new widget.TextArea();
        Leher = new widget.ComboBox();
        Tindakan = new widget.ComboBox();
        scrollPane17 = new widget.ScrollPane();
        KetTindakan = new widget.TextArea();
        jLabel108 = new widget.Label();
        scrollPane18 = new widget.ScrollPane();
        Edukasi3 = new widget.TextArea();
        jLabel109 = new widget.Label();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Pasien Anak Terintergrasi Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1383));
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

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(200, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(270, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(360, 40, 180, 23);

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
        BtnDokter.setBounds(550, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(400, 150, 190, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        KetGenital.setFocusTraversalPolicyProvider(true);
        KetGenital.setName("KetGenital"); // NOI18N
        KetGenital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGenitalKeyPressed(evt);
            }
        });
        FormInput.add(KetGenital);
        KetGenital.setBounds(1120, 260, 220, 23);

        KetParu.setFocusTraversalPolicyProvider(true);
        KetParu.setName("KetParu"); // NOI18N
        KetParu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetParuKeyPressed(evt);
            }
        });
        FormInput.add(KetParu);
        KetParu.setBounds(640, 320, 220, 23);

        KetGigi.setFocusTraversalPolicyProvider(true);
        KetGigi.setName("KetGigi"); // NOI18N
        KetGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGigiKeyPressed(evt);
            }
        });
        FormInput.add(KetGigi);
        KetGigi.setBounds(200, 320, 220, 23);

        KetJantung.setFocusTraversalPolicyProvider(true);
        KetJantung.setName("KetJantung"); // NOI18N
        KetJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetJantungKeyPressed(evt);
            }
        });
        FormInput.add(KetJantung);
        KetJantung.setBounds(640, 290, 220, 23);

        KetMata.setFocusTraversalPolicyProvider(true);
        KetMata.setName("KetMata"); // NOI18N
        KetMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMataKeyPressed(evt);
            }
        });
        FormInput.add(KetMata);
        KetMata.setBounds(200, 290, 220, 23);

        KetKulit.setFocusTraversalPolicyProvider(true);
        KetKulit.setName("KetKulit"); // NOI18N
        KetKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKulitKeyPressed(evt);
            }
        });
        FormInput.add(KetKulit);
        KetKulit.setBounds(1120, 320, 220, 23);

        KetKepala.setFocusTraversalPolicyProvider(true);
        KetKepala.setName("KetKepala"); // NOI18N
        KetKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KetKepala);
        KetKepala.setBounds(200, 260, 220, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(129, 90, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(-20, 140, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(130, 190, 310, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(-20, 190, 150, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(590, 90, 270, 50);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(450, 90, 140, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(590, 150, 270, 42);

        KetThoraks.setFocusTraversalPolicyProvider(true);
        KetThoraks.setName("KetThoraks"); // NOI18N
        KetThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetThoraksKeyPressed(evt);
            }
        });
        FormInput.add(KetThoraks);
        KetThoraks.setBounds(640, 260, 220, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. PEMERIKSAAN FISIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 240, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(130, 140, 310, 43);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 240, 880, 1);

        jLabel39.setText("Punggung:");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(420, 380, 70, 23);

        Konsul.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Konsul.setName("Konsul"); // NOI18N
        Konsul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonsulKeyPressed(evt);
            }
        });
        FormInput.add(Konsul);
        Konsul.setBounds(50, 970, 130, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(-60, 260, 127, 23);

        Punggung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Punggung.setName("Punggung"); // NOI18N
        Punggung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PunggungKeyPressed(evt);
            }
        });
        FormInput.add(Punggung);
        Punggung.setBounds(500, 380, 130, 23);

        jLabel41.setText("Leher:");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(-60, 380, 127, 23);

        KetAbdomen.setFocusTraversalPolicyProvider(true);
        KetAbdomen.setName("KetAbdomen"); // NOI18N
        KetAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KetAbdomen);
        KetAbdomen.setBounds(640, 350, 220, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(60, 260, 128, 23);

        jLabel44.setText("Gigi & Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(-60, 320, 127, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(60, 320, 128, 23);

        jLabel45.setText("THT :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(-60, 350, 127, 23);

        THT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        THT.setName("THT"); // NOI18N
        THT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THTKeyPressed(evt);
            }
        });
        FormInput.add(THT);
        THT.setBounds(60, 350, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(370, 260, 127, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(500, 260, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(400, 350, 95, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(500, 350, 128, 23);

        jLabel50.setText("Genital & Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(870, 260, 95, 23);

        Genital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genital.setName("Genital"); // NOI18N
        Genital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKeyPressed(evt);
            }
        });
        FormInput.add(Genital);
        Genital.setBounds(980, 260, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(870, 290, 95, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(980, 290, 128, 23);

        jLabel52.setText("Kulit :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(870, 320, 95, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(980, 320, 128, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 420, 880, 1);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Laborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Laborat.setColumns(20);
        Laborat.setRows(5);
        Laborat.setName("Laborat"); // NOI18N
        Laborat.setPreferredSize(new java.awt.Dimension(102, 52));
        Laborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Laborat);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(40, 460, 260, 63);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 530, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("III. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 420, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diagnosis);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(40, 550, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 600, 880, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IV. DIAGNOSIS KERJA");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 530, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(20);
        Tatalaksana.setRows(5);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Tatalaksana);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(40, 710, 810, 153);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("VI. RENCANA");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 670, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        KetKonsul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetKonsul.setColumns(20);
        KetKonsul.setRows(5);
        KetKonsul.setName("KetKonsul"); // NOI18N
        KetKonsul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKonsulKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(KetKonsul);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(40, 1000, 810, 63);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(20, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-10-2024 12:02:41" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(70, 40, 130, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 880, 880, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Konsul:");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 970, 190, 23);

        jLabel42.setText("Mata :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(-60, 290, 127, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(60, 290, 128, 23);

        jLabel47.setText("Jantung :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(370, 290, 127, 23);

        Jantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Jantung.setName("Jantung"); // NOI18N
        Jantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JantungKeyPressed(evt);
            }
        });
        FormInput.add(Jantung);
        Jantung.setBounds(500, 290, 128, 23);

        jLabel53.setText("Paru :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(400, 320, 95, 23);

        Paru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Paru.setName("Paru"); // NOI18N
        Paru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKeyPressed(evt);
            }
        });
        FormInput.add(Paru);
        Paru.setBounds(500, 320, 128, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Laboratorium :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(40, 440, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Radiologi :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(320, 440, 150, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Radiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Radiologi.setColumns(20);
        Radiologi.setRows(5);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.setPreferredSize(new java.awt.Dimension(102, 52));
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Radiologi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(320, 460, 260, 63);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Penunjang Lainnya :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(590, 440, 150, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.setPreferredSize(new java.awt.Dimension(102, 52));
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Penunjang);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(590, 460, 260, 63);

        jLabel43.setText("Alat Implant:");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(520, 200, 70, 23);

        Implant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Implant.setName("Implant"); // NOI18N
        Implant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImplantKeyPressed(evt);
            }
        });
        FormInput.add(Implant);
        Implant.setBounds(600, 200, 130, 23);

        KetTHT.setFocusTraversalPolicyProvider(true);
        KetTHT.setName("KetTHT"); // NOI18N
        KetTHT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTHTKeyPressed(evt);
            }
        });
        FormInput.add(KetTHT);
        KetTHT.setBounds(200, 350, 220, 23);

        KetLeher.setFocusTraversalPolicyProvider(true);
        KetLeher.setName("KetLeher"); // NOI18N
        KetLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLeherKeyPressed(evt);
            }
        });
        FormInput.add(KetLeher);
        KetLeher.setBounds(200, 380, 220, 23);

        KetPunggung.setFocusTraversalPolicyProvider(true);
        KetPunggung.setName("KetPunggung"); // NOI18N
        KetPunggung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPunggungKeyPressed(evt);
            }
        });
        FormInput.add(KetPunggung);
        KetPunggung.setBounds(640, 380, 220, 23);

        KetEkstermitas.setFocusTraversalPolicyProvider(true);
        KetEkstermitas.setName("KetEkstermitas"); // NOI18N
        KetEkstermitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEkstermitasKeyPressed(evt);
            }
        });
        FormInput.add(KetEkstermitas);
        KetEkstermitas.setBounds(1120, 290, 220, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("V. DIAGNOSIS BANDING");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(10, 600, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        DiagnosisBanding.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosisBanding.setColumns(20);
        DiagnosisBanding.setRows(3);
        DiagnosisBanding.setName("DiagnosisBanding"); // NOI18N
        DiagnosisBanding.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisBandingKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(DiagnosisBanding);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(40, 620, 810, 43);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("PENGOBATAN");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 690, 190, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("NUTRISI");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(30, 880, 190, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Nutrisi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Nutrisi.setColumns(20);
        Nutrisi.setRows(5);
        Nutrisi.setName("Nutrisi"); // NOI18N
        Nutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NutrisiKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Nutrisi);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(40, 900, 810, 63);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(60, 380, 130, 23);

        Tindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(70, 1080, 130, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        KetTindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetTindakan.setColumns(20);
        KetTindakan.setRows(5);
        KetTindakan.setName("KetTindakan"); // NOI18N
        KetTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTindakanKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(KetTindakan);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(30, 1110, 810, 63);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Tindakan:");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(0, 1080, 190, 23);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        Edukasi3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi3.setColumns(20);
        Edukasi3.setRows(5);
        Edukasi3.setName("Edukasi3"); // NOI18N
        Edukasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi3KeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Edukasi3);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(40, 1730, 810, 63);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Lain-lain");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 1700, 190, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-10-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-10-2024" }));
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan Utama");
        } else if (RPS.getText().trim().equals("")) {
            Valid.textKosong(RPS, "Riwayat Penyakit Sekarang");
        } else if (RPK.getText().trim().equals("")) {
            Valid.textKosong(RPK, "Riwayat Penyakit Keluarga");
        } else if (RPD.getText().trim().equals("")) {
            Valid.textKosong(RPD, "Riwayat Penyakit Dahulu");
        } else if (RPO.getText().trim().equals("")) {
            Valid.textKosong(RPO, "Riwayat Pengunaan obat");
        } else {
            if (Sequel.menyimpantf("penilaian_medis_ranap_anak", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 48, new String[]{
                TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), KdDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
                KeluhanUtama.getText(), RPS.getText(), RPD.getText(), RPK.getText(), RPO.getText(), Implant.getSelectedItem().toString(), Kepala.getSelectedItem().toString(),KetKepala.getText(), Mata.getSelectedItem().toString(), KetMata.getText(),
                Gigi.getSelectedItem().toString(), KetGigi.getText(), THT.getSelectedItem().toString(), KetTHT.getText(), Leher.getSelectedItem().toString(), KetLeher.getText(), Thoraks.getSelectedItem().toString(),
                KetThoraks.getText(), Jantung.getSelectedItem().toString(), KetJantung.getText(), Paru.getSelectedItem().toString(), KetParu.getText(), Abdomen.getSelectedItem().toString(), KetAbdomen.getText(), Punggung.getSelectedItem().toString(), KetPunggung.getText(),
                Genital.getSelectedItem().toString(), KetGenital.getText(), Ekstremitas.getSelectedItem().toString(), KetEkstermitas.getText(), Kulit.getSelectedItem().toString(), KetKulit.getText(), Laborat.getText(),
                Radiologi.getText(), Penunjang.getText(), Diagnosis.getText(), DiagnosisBanding.getText(), Tatalaksana.getText(), Nutrisi.getText(), Konsul.getSelectedItem().toString(), KetKonsul.getText(),
                Tindakan.getSelectedItem().toString(), KetTindakan.getText()
            }) == true) {
                JOptionPane.showMessageDialog(null, "Berhasil Tersimpan..!!");
                emptTeks();
            }
        }

}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, KetKulit, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString())) {
                    hapus();
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
        } else if (NmDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan Utama");
        } else if (RPS.getText().trim().equals("")) {
            Valid.textKosong(RPS, "Riwayat Penyakit Sekarang");
        } else if (RPK.getText().trim().equals("")) {
            Valid.textKosong(RPK, "Riwayat Penyakit Keluarga");
        } else if (RPD.getText().trim().equals("")) {
            Valid.textKosong(RPD, "Riwayat Penyakit Dahulu");
        } else if (RPO.getText().trim().equals("")) {
            Valid.textKosong(RPO, "Riwayat Pengunaan obat");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString())) {
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            try {
                if (TCari.getText().trim().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_anak.tanggal,"
                            + "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"
                            + "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"
                            + "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.jantung,penilaian_medis_ranap_anak.paru,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,"
                            + "penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.lab,penilaian_medis_ranap_anak.rad,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,"
                            + "penilaian_medis_ranap_anak.edukasi,dokter.nm_dokter "
                            + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "
                            + "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where "
                            + "penilaian_medis_ranap_anak.tanggal between ? and ? order by penilaian_medis_ranap_anak.tanggal");
                } else {
                    ps = koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_anak.tanggal,"
                            + "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"
                            + "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"
                            + "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.jantung,penilaian_medis_ranap_anak.paru,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,"
                            + "penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.lab,penilaian_medis_ranap_anak.rad,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,"
                            + "penilaian_medis_ranap_anak.edukasi,dokter.nm_dokter "
                            + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "
                            + "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where "
                            + "penilaian_medis_ranap_anak.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                            + "penilaian_medis_ranap_anak.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap_anak.tanggal");
                }

                try {
                    if (TCari.getText().trim().equals("")) {
                        ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                        ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    } else {
                        ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                        ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                        ps.setString(3, "%" + TCari.getText() + "%");
                        ps.setString(4, "%" + TCari.getText() + "%");
                        ps.setString(5, "%" + TCari.getText() + "%");
                        ps.setString(6, "%" + TCari.getText() + "%");
                        ps.setString(7, "%" + TCari.getText() + "%");
                    }
                    rs = ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                            "<tr class='isi'>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kode Dokter</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Anamnesis</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Hubungan</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Keluhan Utama</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Riwayat Alergi</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Keadaan Umum</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>GCS</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kesadaran</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>TD(mmHg)</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>Nadi(x/menit)</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'><b>RR(x/menit)</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>Suhu</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>SpO2</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>BB(Kg)</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>TB(cm)</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kepala</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Mata</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Gigi & Mulut</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>THT</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Thoraks</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Jantung</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Paru</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Abdomen</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Genital & Anus</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Ekstremitas</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kulit</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Laboratorium</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Radiologi</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Penunjang Lainnya</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Tatalaksana</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Edukasi</b></td>"
                            + "</tr>"
                    );
                    while (rs.next()) {
                        htmlContent.append(
                                "<tr class='isi'>"
                                + "<td valign='top'>" + rs.getString("no_rawat") + "</td>"
                                + "<td valign='top'>" + rs.getString("no_rkm_medis") + "</td>"
                                + "<td valign='top'>" + rs.getString("nm_pasien") + "</td>"
                                + "<td valign='top'>" + rs.getString("tgl_lahir") + "</td>"
                                + "<td valign='top'>" + rs.getString("jk") + "</td>"
                                + "<td valign='top'>" + rs.getString("kd_dokter") + "</td>"
                                + "<td valign='top'>" + rs.getString("nm_dokter") + "</td>"
                                + "<td valign='top'>" + rs.getString("tanggal") + "</td>"
                                + "<td valign='top'>" + rs.getString("anamnesis") + "</td>"
                                + "<td valign='top'>" + rs.getString("hubungan") + "</td>"
                                + "<td valign='top'>" + rs.getString("keluhan_utama") + "</td>"
                                + "<td valign='top'>" + rs.getString("rps") + "</td>"
                                + "<td valign='top'>" + rs.getString("rpd") + "</td>"
                                + "<td valign='top'>" + rs.getString("rpk") + "</td>"
                                + "<td valign='top'>" + rs.getString("rpo") + "</td>"
                                + "<td valign='top'>" + rs.getString("alergi") + "</td>"
                                + "<td valign='top'>" + rs.getString("keadaan") + "</td>"
                                + "<td valign='top'>" + rs.getString("gcs") + "</td>"
                                + "<td valign='top'>" + rs.getString("kesadaran") + "</td>"
                                + "<td valign='top'>" + rs.getString("td") + "</td>"
                                + "<td valign='top'>" + rs.getString("nadi") + "</td>"
                                + "<td valign='top'>" + rs.getString("rr") + "</td>"
                                + "<td valign='top'>" + rs.getString("suhu") + "</td>"
                                + "<td valign='top'>" + rs.getString("spo") + "</td>"
                                + "<td valign='top'>" + rs.getString("bb") + "</td>"
                                + "<td valign='top'>" + rs.getString("tb") + "</td>"
                                + "<td valign='top'>" + rs.getString("kepala") + "</td>"
                                + "<td valign='top'>" + rs.getString("mata") + "</td>"
                                + "<td valign='top'>" + rs.getString("gigi") + "</td>"
                                + "<td valign='top'>" + rs.getString("tht") + "</td>"
                                + "<td valign='top'>" + rs.getString("thoraks") + "</td>"
                                + "<td valign='top'>" + rs.getString("jantung") + "</td>"
                                + "<td valign='top'>" + rs.getString("paru") + "</td>"
                                + "<td valign='top'>" + rs.getString("abdomen") + "</td>"
                                + "<td valign='top'>" + rs.getString("genital") + "</td>"
                                + "<td valign='top'>" + rs.getString("ekstremitas") + "</td>"
                                + "<td valign='top'>" + rs.getString("kulit") + "</td>"
                                + "<td valign='top'>" + rs.getString("ket_fisik") + "</td>"
                                + "<td valign='top'>" + rs.getString("ket_lokalis") + "</td>"
                                + "<td valign='top'>" + rs.getString("lab") + "</td>"
                                + "<td valign='top'>" + rs.getString("rad") + "</td>"
                                + "<td valign='top'>" + rs.getString("penunjang") + "</td>"
                                + "<td valign='top'>" + rs.getString("diagnosis") + "</td>"
                                + "<td valign='top'>" + rs.getString("tata") + "</td>"
                                + "<td valign='top'>" + rs.getString("edukasi") + "</td>"
                                + "</tr>");
                    }
                    LoadHTML.setText(
                            "<html>"
                            + "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
                            + htmlContent.toString()
                            + "</table>"
                            + "</html>"
                    );

                    File g = new File("file2.css");
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                            ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                            + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                            + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                            + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                            + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                            + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                            + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                            + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                            + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                    );
                    bg.close();

                    File f = new File("DataPenilaianAwalMedisRanap.html");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write(LoadHTML.getText().replaceAll("<head>", "<head>"
                            + "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"
                            + "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            + "<tr class='isi2'>"
                            + "<td valign='top' align='center'>"
                            + "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>"
                            + akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                            + akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>"
                            + "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT INAP<br><br></font>"
                            + "</td>"
                            + "</tr>"
                            + "</table>")
                    );
                    bw.close();
                    Desktop.getDesktop().browse(f.toURI());
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
                System.out.println("Notifikasi : " + e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if ((evt.getClickCount() == 2) && (tbObat.getSelectedColumn() == 0)) {
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void KetGenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGenitalKeyPressed
        Valid.pindah(evt, KetParu, KetMata);
    }//GEN-LAST:event_KetGenitalKeyPressed

    private void KetParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetParuKeyPressed
        Valid.pindah(evt, KetThoraks, KetGenital);
    }//GEN-LAST:event_KetParuKeyPressed

    private void KetGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGigiKeyPressed
        Valid.pindah(evt, KetMata, KetKulit);
    }//GEN-LAST:event_KetGigiKeyPressed

    private void KetJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetJantungKeyPressed
        Valid.pindah(evt, KetKulit, KetAbdomen);
    }//GEN-LAST:event_KetJantungKeyPressed

    private void KetMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMataKeyPressed
        Valid.pindah(evt, KetGenital, KetGigi);
    }//GEN-LAST:event_KetMataKeyPressed

    private void KetKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKulitKeyPressed
        Valid.pindah(evt, KetGigi, KetJantung);
    }//GEN-LAST:event_KetKulitKeyPressed

    private void KetKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKepalaKeyPressed
        Valid.pindah(evt, RPO, Konsul);
    }//GEN-LAST:event_KetKepalaKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt, TglAsuhan, Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt, Hubungan, RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt, RPK, RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt, RPS, RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt, RPD, KetKepala);
    }//GEN-LAST:event_RPOKeyPressed

    private void KetThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetThoraksKeyPressed
        Valid.pindah(evt, Punggung, KetParu);
    }//GEN-LAST:event_KetThoraksKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt, KeluhanUtama, RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void KonsulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonsulKeyPressed
        Valid.pindah(evt, KetKepala, Punggung);
    }//GEN-LAST:event_KonsulKeyPressed

    private void PunggungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PunggungKeyPressed
        Valid.pindah(evt, Konsul, KetThoraks);
    }//GEN-LAST:event_PunggungKeyPressed

    private void KetAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAbdomenKeyPressed
        Valid.pindah(evt, KetJantung, Kepala);
    }//GEN-LAST:event_KetAbdomenKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt, KetAbdomen, Mata);
    }//GEN-LAST:event_KepalaKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        Valid.pindah(evt, Mata, THT);
    }//GEN-LAST:event_GigiKeyPressed

    private void THTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THTKeyPressed
        Valid.pindah(evt, Gigi, Thoraks);
    }//GEN-LAST:event_THTKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt, THT, Jantung);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt, Paru, Genital);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void GenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKeyPressed
        Valid.pindah(evt, Abdomen, Ekstremitas);
    }//GEN-LAST:event_GenitalKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt, Genital, Kulit);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        Valid.pindah(evt, Ekstremitas, KetKulit);
    }//GEN-LAST:event_KulitKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        //    Valid.pindah2(evt,KetLokalis,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt, Penunjang, Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        Valid.pindah2(evt, Diagnosis, KetKonsul);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void KetKonsulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKonsulKeyPressed
        Valid.pindah2(evt, Tatalaksana, BtnSimpan);
    }//GEN-LAST:event_KetKonsulKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt, KetKonsul, Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt, Anamnesis, KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            try {
                param.put("lokalis", getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            }
            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() + "\nID " + (finger.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString() : finger) + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()));

            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanap.jasper", "report", "::[ Laporan Penilaian Awal Medis Rawat Inap ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_anak.tanggal,"
                    + "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"
                    + "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"
                    + "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.jantung,penilaian_medis_ranap_anak.paru,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,"
                    + "penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.lab,penilaian_medis_ranap_anak.rad,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,"
                    + "penilaian_medis_ranap_anak.edukasi,dokter.nm_dokter "
                    + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "
                    + "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap_anak.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt, Kepala, Gigi);
    }//GEN-LAST:event_MataKeyPressed

    private void JantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JantungKeyPressed
        Valid.pindah(evt, Thoraks, Paru);
    }//GEN-LAST:event_JantungKeyPressed

    private void ParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKeyPressed
        Valid.pindah(evt, Jantung, Abdomen);
    }//GEN-LAST:event_ParuKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah2(evt, Laborat, Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt, Radiologi, Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void ImplantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImplantKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImplantKeyPressed

    private void KetTHTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTHTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetTHTKeyPressed

    private void KetLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetLeherKeyPressed

    private void KetPunggungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPunggungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPunggungKeyPressed

    private void KetEkstermitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEkstermitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetEkstermitasKeyPressed

    private void DiagnosisBandingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisBandingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisBandingKeyPressed

    private void NutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NutrisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NutrisiKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeherKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKeyPressed

    private void KetTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetTindakanKeyPressed

    private void Edukasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi3KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRanapAnak dialog = new RMPenilaianAwalMedisRanapAnak(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abdomen;
    private widget.ComboBox Anamnesis;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private widget.TextArea DiagnosisBanding;
    private widget.TextArea Edukasi3;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Genital;
    private widget.ComboBox Gigi;
    private widget.TextBox Hubungan;
    private widget.ComboBox Implant;
    private widget.ComboBox Jantung;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.TextBox KetAbdomen;
    private widget.TextBox KetEkstermitas;
    private widget.TextBox KetGenital;
    private widget.TextBox KetGigi;
    private widget.TextBox KetJantung;
    private widget.TextBox KetKepala;
    private widget.TextArea KetKonsul;
    private widget.TextBox KetKulit;
    private widget.TextBox KetLeher;
    private widget.TextBox KetMata;
    private widget.TextBox KetParu;
    private widget.TextBox KetPunggung;
    private widget.TextBox KetTHT;
    private widget.TextBox KetThoraks;
    private widget.TextArea KetTindakan;
    private widget.ComboBox Konsul;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.ComboBox Leher;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Mata;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.TextArea Nutrisi;
    private widget.ComboBox Paru;
    private widget.TextArea Penunjang;
    private widget.ComboBox Punggung;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextArea Radiologi;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox THT;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Tatalaksana;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.ComboBox Tindakan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
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
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir,"
                        + "penilaian_medis_ranap_anak.tanggal, penilaian_medis_ranap_anak.kd_dokter,"
                        + "penilaian_medis_ranap_anak.anamnesis, penilaian_medis_ranap_anak.hubungan,"
                        + "penilaian_medis_ranap_anak.keluhan_utama, penilaian_medis_ranap_anak.rps,"
                        + "penilaian_medis_ranap_anak.rpd, penilaian_medis_ranap_anak.rpk, penilaian_medis_ranap_anak.rpo,"
                        + "penilaian_medis_ranap_anak.implant, penilaian_medis_ranap_anak.kepala, penilaian_medis_ranap_anak.ketkepala,"
                        + "penilaian_medis_ranap_anak.mata, penilaian_medis_ranap_anak.ketmata, penilaian_medis_ranap_anak.gigi,"
                        + "penilaian_medis_ranap_anak.ketgigi, penilaian_medis_ranap_anak.tht, penilaian_medis_ranap_anak.kettht,"
                        + "penilaian_medis_ranap_anak.leher, penilaian_medis_ranap_anak.ketleher, penilaian_medis_ranap_anak.thoraks,"
                        + "penilaian_medis_ranap_anak.ketthoraks, penilaian_medis_ranap_anak.jantung, penilaian_medis_ranap_anak.ketjantung,"
                        + "penilaian_medis_ranap_anak.paru, penilaian_medis_ranap_anak.ketparu, penilaian_medis_ranap_anak.abdomen,"
                        + "penilaian_medis_ranap_anak.ketabdomen, penilaian_medis_ranap_anak.punggung, penilaian_medis_ranap_anak.ketpunggung,"
                        + "penilaian_medis_ranap_anak.genital, penilaian_medis_ranap_anak.ketgenital, penilaian_medis_ranap_anak.ekstremitas,"
                        + "penilaian_medis_ranap_anak.ketekstremitas, penilaian_medis_ranap_anak.kulit, penilaian_medis_ranap_anak.ketkulit,"
                        + "penilaian_medis_ranap_anak.lab, penilaian_medis_ranap_anak.rad, penilaian_medis_ranap_anak.penunjang,"
                        + "penilaian_medis_ranap_anak.diagnosis, penilaian_medis_ranap_anak.diagnosisbanding,"
                        + "penilaian_medis_ranap_anak.tata, penilaian_medis_ranap_anak.nutrisi, penilaian_medis_ranap_anak.konsul,"
                        + "penilaian_medis_ranap_anak.isikonsul, penilaian_medis_ranap_anak.tindakan, penilaian_medis_ranap_anak.isitindakan "
                        + "FROM reg_periksa "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN penilaian_medis_ranap_anak ON reg_periksa.no_rawat = penilaian_medis_ranap_anak.no_rawat "
                        + "INNER JOIN dokter ON penilaian_medis_ranap_anak.kd_dokter = dokter.kd_dokter where "
                        + "penilaian_medis_ranap_anak.tanggal between ? and ? order by penilaian_medis_ranap_anak.tanggal");
            } else {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir,"
                        + "penilaian_medis_ranap_anak.tanggal, penilaian_medis_ranap_anak.kd_dokter,"
                        + "penilaian_medis_ranap_anak.anamnesis, penilaian_medis_ranap_anak.hubungan,"
                        + "penilaian_medis_ranap_anak.keluhan_utama, penilaian_medis_ranap_anak.rps,"
                        + "penilaian_medis_ranap_anak.rpd, penilaian_medis_ranap_anak.rpk, penilaian_medis_ranap_anak.rpo,"
                        + "penilaian_medis_ranap_anak.implant, penilaian_medis_ranap_anak.kepala, penilaian_medis_ranap_anak.ketkepala,"
                        + "penilaian_medis_ranap_anak.mata, penilaian_medis_ranap_anak.ketmata, penilaian_medis_ranap_anak.gigi,"
                        + "penilaian_medis_ranap_anak.ketgigi, penilaian_medis_ranap_anak.tht, penilaian_medis_ranap_anak.kettht,"
                        + "penilaian_medis_ranap_anak.leher, penilaian_medis_ranap_anak.ketleher, penilaian_medis_ranap_anak.thoraks,"
                        + "penilaian_medis_ranap_anak.ketthoraks, penilaian_medis_ranap_anak.jantung, penilaian_medis_ranap_anak.ketjantung,"
                        + "penilaian_medis_ranap_anak.paru, penilaian_medis_ranap_anak.ketparu, penilaian_medis_ranap_anak.abdomen,"
                        + "penilaian_medis_ranap_anak.ketabdomen, penilaian_medis_ranap_anak.punggung, penilaian_medis_ranap_anak.ketpunggung,"
                        + "penilaian_medis_ranap_anak.genital, penilaian_medis_ranap_anak.ketgenital, penilaian_medis_ranap_anak.ekstremitas,"
                        + "penilaian_medis_ranap_anak.ketekstremitas, penilaian_medis_ranap_anak.kulit, penilaian_medis_ranap_anak.ketkulit,"
                        + "penilaian_medis_ranap_anak.lab, penilaian_medis_ranap_anak.rad, penilaian_medis_ranap_anak.penunjang,"
                        + "penilaian_medis_ranap_anak.diagnosis, penilaian_medis_ranap_anak.diagnosisbanding,"
                        + "penilaian_medis_ranap_anak.tata, penilaian_medis_ranap_anak.nutrisi, penilaian_medis_ranap_anak.konsul,"
                        + "penilaian_medis_ranap_anak.isikonsul, penilaian_medis_ranap_anak.tindakan, penilaian_medis_ranap_anak.isitindakan "
                        + "FROM reg_periksa "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN penilaian_medis_ranap_anak ON reg_periksa.no_rawat = penilaian_medis_ranap_anak.no_rawat "
                        + "INNER JOIN dokter ON penilaian_medis_ranap_anak.kd_dokter = dokter.kd_dokter where "
                        + "penilaian_medis_ranap_anak.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                        + "penilaian_medis_ranap_anak.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap_anak.tanggal");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_lahir"),
                        rs.getString("jk"), rs.getString("tanggal"), rs.getString("kd_dokter"), rs.getString("anamnesis"),
                        rs.getString("hubungan"), rs.getString("keluhan_utama"), rs.getString("rps"), rs.getString("rpd"),
                        rs.getString("rpk"), rs.getString("rpo"), rs.getString("implant"), rs.getString("kepala"),
                        rs.getString("ketkepala"), rs.getString("mata"), rs.getString("ketmata"), rs.getString("gigi"),
                        rs.getString("ketgigi"), rs.getString("tht"), rs.getString("kettht"), rs.getString("leher"),
                        rs.getString("ketleher"), rs.getString("thoraks"), rs.getString("ketthoraks"), rs.getString("jantung"),
                        rs.getString("ketjantung"), rs.getString("paru"), rs.getString("ketparu"), rs.getString("abdomen"),
                        rs.getString("ketabdomen"), rs.getString("punggung"), rs.getString("ketpunggung"), rs.getString("genital"),
                        rs.getString("ketgenital"), rs.getString("ekstremitas"), rs.getString("ketekstremitas"), rs.getString("kulit"),
                        rs.getString("ketkulit"), rs.getString("lab"), rs.getString("rad"), rs.getString("penunjang"),
                        rs.getString("diagnosis"), rs.getString("diagnosisbanding"), rs.getString("tata"), rs.getString("nutrisi"),
                        rs.getString("konsul"), rs.getString("isikonsul"), rs.getString("tindakan"), rs.getString("isitindakan")
                    //   });

                    }
                    );
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
            System.out.println("Notifikasi : " + e);
        }

        LCount.setText(
                "" + tabMode.getRowCount());
    }

    public void emptTeks() {
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPK.setText("");
        RPD.setText("");
        RPO.setText("");
        KetKepala.setText("");
        Konsul.setSelectedIndex(0);
        KetThoraks.setText("");
        Punggung.setSelectedIndex(0);
        KetMata.setText("");
        KetGigi.setText("");
        KetKulit.setText("");
        KetJantung.setText("");
        KetGenital.setText("");
        KetParu.setText("");
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Jantung.setSelectedIndex(0);
        Paru.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        THT.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Genital.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Kulit.setSelectedIndex(0);
        KetKulit.setText("");
        Laborat.setText("");
        Radiologi.setText("");
        Penunjang.setText("");
        Diagnosis.setText("");
        Tatalaksana.setText("");
        KetKonsul.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            /*
             "No.Rawat", "No RM", "Nama Pasien", "Tanggal", "Kode Dokter", "Nama Dokter", "Anamnesis", "Hubungan", "Keluhan Utama",
            "Riwayat Penyakit Sekarang (RPS)", "Riwayat Penyakit Dahulu (RPD)", "Riwayat Penyakit Keluarga (RPK)",
            "Riwayat Penggunaan Obat (RPO)", "Implant", "Kepala", "Ket. Kepala", "Mata", "Ket. Mata", "Gigi & Mulut",
            "Ket. Gigi & Mulut", "THT", "Ket. THT", "Leher", "Ket. Leher", "Thoraks", "Ket. Thoraks", "Jantung",
            "Ket. Jantung", "Paru", "Ket. Paru", "Abdomen", "Ket. Abdomen", "Punggung", "Ket. Punggung", "Genital & Anus",
            "Ket. Genital & Anus", "Ekstremitas", "Ket. Ekstremitas", "Kulit", "Ket. Kulit", "Laboratorium",
            "Radiologi", "Penunjang Lainnya", "Diagnosis/Asesmen", "Diagnosis Banding", "Tatalaksana", "Nutrisi",
            "Konsul", "Isi Konsul", "Tindakan", "Isi Tindakan"
             */        
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            KetKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            Konsul.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            KetThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Punggung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            KetMata.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            KetGigi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            KetKulit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            KetJantung.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            KetAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            KetGenital.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            KetParu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            THT.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            Jantung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            Paru.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            Genital.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            KetKulit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            Laborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 40).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString());
            Tatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString());
            KetKonsul.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 44).toString());
            Valid.SetTgl2(TglAsuhan, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "
                    + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
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
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ranap());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ranap());
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
        if (Sequel.queryu2tf("delete from penilaian_medis_ranap_anak where no_rawat=?", 1, new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if (Sequel.mengedittf("penilaian_medis_ranap_anak", "no_rawat=?",
                "no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpd=?,rpk=?,rpo=?,implant=?,kepala=?,ketkepala=?,mata=?,ketmata=?,gigi=?,ketgigi=?,"
                + "tht=?,kettht=?,leher=?,ketleher=?,thoraks=?,ketthoraks=?,jantung=?,ketjantung=?,paru=?,ketparu=?,abdomen=?,ketabdomen=?,punggung=?,ketpunggung=?,genital=?,"
                + "ketgenital=?,ekstremitas=?,ketekstremitas=?,kulit=?,ketkulit=?,lab=?,rad=?,penunjang=?,diagnosis=?,diagnosisbanding=?,tata=?,nutrisi=?,konsul=?,isikonsul=?,tindakan=?,isitindakan=?", 49, new String[]{
                    TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19), KdDokter.getText(), Anamnesis.getSelectedItem().toString(), Hubungan.getText(),
                KeluhanUtama.getText(), RPS.getText(), RPD.getText(), RPK.getText(), RPO.getText(), Implant.getSelectedItem().toString(), Kepala.getSelectedItem().toString(),KetKepala.getText(), Mata.getSelectedItem().toString(), KetMata.getText(),
                Gigi.getSelectedItem().toString(), KetGigi.getText(), THT.getSelectedItem().toString(), KetTHT.getText(), Leher.getSelectedItem().toString(), KetLeher.getText(), Thoraks.getSelectedItem().toString(),
                KetThoraks.getText(), Jantung.getSelectedItem().toString(), KetJantung.getText(), Paru.getSelectedItem().toString(), KetParu.getText(), Abdomen.getSelectedItem().toString(), KetAbdomen.getText(), Punggung.getSelectedItem().toString(), KetPunggung.getText(),
                Genital.getSelectedItem().toString(), KetGenital.getText(), Ekstremitas.getSelectedItem().toString(), KetEkstermitas.getText(), Kulit.getSelectedItem().toString(), KetKulit.getText(), Laborat.getText(),
                Radiologi.getText(), Penunjang.getText(), Diagnosis.getText(), DiagnosisBanding.getText(), Tatalaksana.getText(), Nutrisi.getText(), Konsul.getSelectedItem().toString(), KetKonsul.getText(),
                Tindakan.getSelectedItem().toString(), KetTindakan.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                }) == true) {
            tampil();
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void ambildataawalmedisigdspesialis() {
        try {
            ps = koneksi.prepareStatement(
                    "select penilaian_medis_igd_spesialis.tanggal,"
                    + "penilaian_medis_igd_spesialis.kd_dokter,penilaian_medis_igd_spesialis.anamnesis,penilaian_medis_igd_spesialis.hubungan,penilaian_medis_igd_spesialis.keluhan_utama,penilaian_medis_igd_spesialis.rps,penilaian_medis_igd_spesialis.rpk,penilaian_medis_igd_spesialis.rpd,penilaian_medis_igd_spesialis.rpo,penilaian_medis_igd_spesialis.alergi,"
                    + "penilaian_medis_igd_spesialis.keadaan,penilaian_medis_igd_spesialis.gcs,penilaian_medis_igd_spesialis.kesadaran,penilaian_medis_igd_spesialis.td,penilaian_medis_igd_spesialis.nadi,penilaian_medis_igd_spesialis.rr,penilaian_medis_igd_spesialis.suhu,penilaian_medis_igd_spesialis.spo,penilaian_medis_igd_spesialis.bb,penilaian_medis_igd_spesialis.tb,"
                    + "penilaian_medis_igd_spesialis.kepala,penilaian_medis_igd_spesialis.mata,penilaian_medis_igd_spesialis.gigi,penilaian_medis_igd_spesialis.thoraks,penilaian_medis_igd_spesialis.abdomen,penilaian_medis_igd_spesialis.ekstremitas,penilaian_medis_igd_spesialis.genital,"
                    + "penilaian_medis_igd_spesialis.ket_lokalis,penilaian_medis_igd_spesialis.rad,penilaian_medis_igd_spesialis.lab,penilaian_medis_igd_spesialis.diagnosis,penilaian_medis_igd_spesialis.tata "
                    + "from penilaian_medis_igd_spesialis where "
                    + "penilaian_medis_igd_spesialis.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (!rs.isBeforeFirst()) { // Jika tidak ada baris hasil
                    JOptionPane.showMessageDialog(null, "Tidak Ada Data Asesmen Medis IGD dr. Spesialis", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    while (rs.next()) {
                        KeluhanUtama.setText(rs.getString("keluhan_utama"));
                        RPS.setText(rs.getString("rps"));
                        RPK.setText(rs.getString("rpk"));
                        RPD.setText(rs.getString("rpd"));
                        RPO.setText(rs.getString("rpo"));
                        KetKepala.setText(rs.getString("alergi"));
                        KetThoraks.setText(rs.getString("gcs"));
                        KetParu.setText(rs.getString("tb"));
                        KetGenital.setText(rs.getString("bb"));
                        KetMata.setText(rs.getString("td"));
                        KetGigi.setText(rs.getString("nadi"));
                        KetKulit.setText(rs.getString("rr"));
                        KetJantung.setText(rs.getString("suhu"));
                        KetAbdomen.setText(rs.getString("spo"));
                        Laborat.setText(rs.getString("lab"));
                        Radiologi.setText(rs.getString("rad"));
                        Diagnosis.setText(rs.getString("diagnosis"));
                        Tatalaksana.setText(rs.getString("tata"));
                        Konsul.setSelectedItem(rs.getString("keadaan"));
                        Konsul.setSelectedItem(rs.getString("kesadaran"));
                        Kepala.setSelectedItem(rs.getString("kepala"));
                        Mata.setSelectedItem(rs.getString("mata"));
                        Gigi.setSelectedItem(rs.getString("gigi"));
                        Thoraks.setSelectedItem(rs.getString("thoraks"));
                        Abdomen.setSelectedItem(rs.getString("abdomen"));
                        Genital.setSelectedItem(rs.getString("genital"));
                        Ekstremitas.setSelectedItem(rs.getString("ekstremitas"));
                    }
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
    }

    private void ambildataawalmedisigd() {
        try {
            ps = koneksi.prepareStatement(
                    "select penilaian_medis_igd.tanggal,"
                    + "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"
                    + "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"
                    + "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.gigi,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,"
                    + "penilaian_medis_igd.ket_fisik,penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata "
                    + "from penilaian_medis_igd where "
                    + "penilaian_medis_igd.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                while (rs.next()) {
                    KeluhanUtama.setText(rs.getString("keluhan_utama"));
                    RPS.setText(rs.getString("rps"));
                    RPK.setText(rs.getString("rpk"));
                    RPD.setText(rs.getString("rpd"));
                    RPO.setText(rs.getString("rpo"));
                    KetKepala.setText(rs.getString("alergi"));
                    KetThoraks.setText(rs.getString("gcs"));
                    KetParu.setText(rs.getString("tb"));
                    KetGenital.setText(rs.getString("bb"));
                    KetMata.setText(rs.getString("td"));
                    KetGigi.setText(rs.getString("nadi"));
                    KetKulit.setText(rs.getString("rr"));
                    KetJantung.setText(rs.getString("suhu"));
                    KetAbdomen.setText(rs.getString("spo"));
                    KetKulit.setText(rs.getString("ket_fisik"));
                    Laborat.setText(rs.getString("lab"));
                    Radiologi.setText(rs.getString("rad"));
                    Penunjang.setText(rs.getString("ekg"));
                    Diagnosis.setText(rs.getString("diagnosis"));
                    Tatalaksana.setText(rs.getString("tata"));
                    Konsul.setSelectedItem(rs.getString("keadaan"));
                    Konsul.setSelectedItem(rs.getString("kesadaran"));
                    Kepala.setSelectedItem(rs.getString("kepala"));
                    Mata.setSelectedItem(rs.getString("mata"));
                    Gigi.setSelectedItem(rs.getString("gigi"));
                    Thoraks.setSelectedItem(rs.getString("thoraks"));
                    Abdomen.setSelectedItem(rs.getString("abdomen"));
                    Genital.setSelectedItem(rs.getString("genital"));
                    Ekstremitas.setSelectedItem(rs.getString("ekstremitas"));
//                    KetKepala.setText(rs.getString("ket_kepala"));
//                    KetMata.setText(rs.getString("ket_mata"));
//                    KetGigi.setText(rs.getString("ket_gigi"));
//                    KetThoraks.setText(rs.getString("ket_thorax"));
//                    KetAbdomen.setText(rs.getString("ket_abdomen"));
//                    KetGenital.setText(rs.getString("ket_gintal"));
//                    KetEkstremitas.setText(rs.getString("ket_ekstremitas"));
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
    }

}
