/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMLaporanOperasi extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, pilihan = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private String finger = "", finger2 = "";
    private StringBuilder htmlContent;

    // === Field baru: Jenis Pembedahan, Implan, Klasifikasi, Konsultasi ===
    private widget.ComboBox JenisPembedahan;
    private widget.Label lblJenisPembedahan;
    private javax.swing.JRadioButton rdImplanYa;
    private javax.swing.JRadioButton rdImplanTidak;
    private javax.swing.ButtonGroup bgImplan;
    private widget.Label lblPemasanganImplan;
    private widget.TextBox LokasiImplan;
    private widget.Label lblLokasiImplan;
    private widget.TextBox JenisImplan;
    private widget.Label lblJenisImplan;
    private widget.TextBox NoRegImplan;
    private widget.Label lblNoRegImplan;
    private widget.ComboBox KlasifikasiOperasi;
    private widget.Label lblKlasifikasiOperasi;
    private widget.TextBox KonsultasiIntraOperatif;
    private widget.Label lblKonsultasiIntraOperatif;

    // === Field baru: Template Laporan Operasi Casemix ===
    private DlgCariTemplateLaporanOperasiCasemix template = new DlgCariTemplateLaporanOperasiCasemix(null, false);
    private widget.Button BtnTemplate;
    private widget.Button BtnSimpanTemplate;

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMLaporanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initLaporanOperasi();
        
        // Memindahkan PanelInput dan Scroll ke dalam JTabbedPane
        internalFrame1.remove(PanelInput);
        internalFrame1.remove(Scroll);
        
        javax.swing.JTabbedPane tabForm = new javax.swing.JTabbedPane();
        tabForm.setBackground(new java.awt.Color(255, 255, 253));
        tabForm.setForeground(new java.awt.Color(50, 50, 50));
        tabForm.setFont(new java.awt.Font("Tahoma", 0, 11));
        
        tabForm.addTab("Input Laporan Operasi", PanelInput);
        tabForm.addTab("Data Laporan Operasi", Scroll);
        
        internalFrame1.add(tabForm, java.awt.BorderLayout.CENTER);
        
        // Sembunyikan ChkInput karena sudah tidak diperlukan di model tab
        ChkInput.setVisible(false);
        ChkInput.setPreferredSize(new java.awt.Dimension(0, 0));
        
        this.setLocation(8, 1);
        setSize(628, 674);

        // Inisialisasi model tabel
        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Tanggal",
                "Kode Dokter Bedah", "Dokter Bedah", "Kode Dokter Anestesi", "Dokter Anestesi",
                "Kode Asisten Bedah", "Asisten Bedah", "Kode Asisten Anestesi", "Nama Asisten",
                "Jenis Operasi", "Jenis Anestesi", "Diagnosa Pra Bedah", "Diagnosa Pasca Bedah",
                "Tindakan", "Jam Mulai Operasi", "Jam Selesai Operasi", "Lama Pembedahan",
                "Cara Pembiusan", "Posisi Pasien", "Uraian Pembedahan", "Komplikasi", "Perdaraan", "Dikirim",
                "Jenis Pemeriksaan", "Asal Jaringan", "Jenis Pembedahan", "Pasang Implan",
                "Lokasi Implan", "Jenis Implan", "No.Reg Implan", "Klasifikasi Operasi",
                "Konsultasi Intra Op"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        // Mengatur model ke tabel
        tbObat.setModel(tabMode);

        // Pengaturan ukuran tampilan
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Atur lebar kolom
        int[] columnWidths = { 105, 70, 150, 65, 25, 115, 90, 160, 100, 160,
                100, 150, 53, 85, 130, 88, 98, 102, 89, 149,
                109, 90, 120, 90, 120, 90, 120, 90, 120, 90,
                90, 90, 90, 120, 90, 150, 150 };

        for (int i = 0; i < columnWidths.length; i++) {
            tbObat.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Menambahkan renderer untuk pewarnaan
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        Komplikasi.setDocument(new batasInput((byte) 25).getKata(Komplikasi));
        Tindakan.setDocument(new batasInput((byte) 50).getKata(Tindakan));
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
                    if (pilihan == 1) {
                        KdAsistenBedah.setText(
                                petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPetugasRuangan.setText(
                                petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        btnPetugasRuangan.requestFocus();
                    } else if (pilihan == 2) {
                        KdPenataAnestesi.setText(
                                petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPetugasOK.setText(
                                petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        btnPetugasOK.requestFocus();
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
                if (dokter.getTable().getSelectedRow() != -1) {
                    if (pilihan == 1) {
                        KodeDokterBedah.setText(
                                dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                        NamaDokterBedah.setText(
                                dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        btnDokterBedah.requestFocus();
                    } else if (pilihan == 2) {
                        KodeDokterAnestesi.setText(
                                dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                        NamaDokterAnestesi.setText(
                                dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        btnDokterAnestesi.requestFocus();
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

        ChkInput.setSelected(false);
        isForm();

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
                        + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningNutrisi = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        KdAsistenBedah = new widget.TextBox();
        NmPetugasRuangan = new widget.TextBox();
        btnPetugasRuangan = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        Komplikasi = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        KodeDokterBedah = new widget.TextBox();
        NamaDokterBedah = new widget.TextBox();
        btnDokterBedah = new widget.Button();
        btnDokterAnestesi = new widget.Button();
        NamaDokterAnestesi = new widget.TextBox();
        KodeDokterAnestesi = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel26 = new widget.Label();
        KdPenataAnestesi = new widget.TextBox();
        NmPetugasOK = new widget.TextBox();
        btnPetugasOK = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel27 = new widget.Label();
        jLabel53 = new widget.Label();
        JenisOperasi = new widget.ComboBox();
        jLabel66 = new widget.Label();
        JenisAnastesi = new widget.ComboBox();
        DiagnosaPrabedah = new widget.TextBox();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        DiagnosaPascabedah = new widget.TextBox();
        jLabel28 = new widget.Label();
        CaraPembiusan = new widget.TextBox();
        jLabel69 = new widget.Label();
        KetJaringan = new widget.ComboBox();
        Jaringan = new widget.ComboBox();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        JumlahPerdarahan = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        LaporanOperasi = new widget.TextArea();
        jLabel50 = new widget.Label();
        jLabel40 = new widget.Label();
        PosisiPasien = new widget.TextBox();
        LamaPembedahan = new widget.TextBox();
        jLabel42 = new widget.Label();
        KomplikasiOperasi3 = new widget.TextBox();
        jLabel43 = new widget.Label();
        AsalJaringan = new widget.TextBox();
        TanggalJamMulaiOperasi = new widget.Tanggal();
        TanggalJamSelesaiOperasi = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningNutrisi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningNutrisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningNutrisi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningNutrisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningNutrisi.setText("Formulir Laporan Operasi");
        MnSkriningNutrisi.setName("MnSkriningNutrisi"); // NOI18N
        MnSkriningNutrisi.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningNutrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningNutrisiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningNutrisi);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Laporan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
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
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 800));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Asisten /Instrument :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 110, 110, 23);

        KdAsistenBedah.setEditable(false);
        KdAsistenBedah.setName("KdAsistenBedah"); // NOI18N
        FormInput.add(KdAsistenBedah);
        KdAsistenBedah.setBounds(110, 110, 95, 23);

        NmPetugasRuangan.setEditable(true);
        NmPetugasRuangan.setName("NmPetugasRuangan"); // NOI18N
        FormInput.add(NmPetugasRuangan);
        NmPetugasRuangan.setBounds(210, 110, 165, 23);

        btnPetugasRuangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasRuangan.setMnemonic('2');
        btnPetugasRuangan.setToolTipText("ALt+2");
        btnPetugasRuangan.setName("btnPetugasRuangan"); // NOI18N
        btnPetugasRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasRuanganActionPerformed(evt);
            }
        });
        btnPetugasRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasRuanganKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasRuangan);
        btnPetugasRuangan.setBounds(380, 110, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        Komplikasi.setName("Komplikasi"); // NOI18N
        Komplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiKeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi);
        Komplikasi.setBounds(150, 680, 470, 30);

        jLabel22.setText("Komplikasi:");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(42, 30));
        FormInput.add(jLabel22);
        jLabel22.setBounds(40, 680, 100, 23);

        jLabel23.setText("Dokter Bedah :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(-10, 70, 91, 23);

        KodeDokterBedah.setEditable(false);
        KodeDokterBedah.setName("KodeDokterBedah"); // NOI18N
        FormInput.add(KodeDokterBedah);
        KodeDokterBedah.setBounds(90, 70, 97, 23);

        NamaDokterBedah.setEditable(false);
        NamaDokterBedah.setName("NamaDokterBedah"); // NOI18N
        FormInput.add(NamaDokterBedah);
        NamaDokterBedah.setBounds(190, 70, 175, 23);

        btnDokterBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterBedah.setMnemonic('2');
        btnDokterBedah.setToolTipText("ALt+2");
        btnDokterBedah.setName("btnDokterBedah"); // NOI18N
        btnDokterBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterBedahActionPerformed(evt);
            }
        });
        btnDokterBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterBedahKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterBedah);
        btnDokterBedah.setBounds(370, 70, 28, 23);

        btnDokterAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterAnestesi.setMnemonic('2');
        btnDokterAnestesi.setToolTipText("ALt+2");
        btnDokterAnestesi.setName("btnDokterAnestesi"); // NOI18N
        btnDokterAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterAnestesiActionPerformed(evt);
            }
        });
        btnDokterAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterAnestesi);
        btnDokterAnestesi.setBounds(780, 70, 28, 23);

        NamaDokterAnestesi.setEditable(false);
        NamaDokterAnestesi.setName("NamaDokterAnestesi"); // NOI18N
        FormInput.add(NamaDokterAnestesi);
        NamaDokterAnestesi.setBounds(600, 70, 175, 23);

        KodeDokterAnestesi.setEditable(false);
        KodeDokterAnestesi.setName("KodeDokterAnestesi"); // NOI18N
        FormInput.add(KodeDokterAnestesi);
        KodeDokterAnestesi.setBounds(507, 70, 90, 23);

        jLabel24.setText("Dokter Anestesi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(400, 70, 91, 23);

        jLabel25.setText("Tindakan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(70, 230, 70, 23);

        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(150, 230, 630, 23);

        jLabel26.setText("Penata Anastesi :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(410, 110, 90, 23);

        KdPenataAnestesi.setEditable(false);
        KdPenataAnestesi.setName("KdPenataAnestesi"); // NOI18N
        FormInput.add(KdPenataAnestesi);
        KdPenataAnestesi.setBounds(510, 110, 95, 23);

        NmPetugasOK.setEditable(true);
        NmPetugasOK.setName("NmPetugasOK"); // NOI18N
        FormInput.add(NmPetugasOK);
        NmPetugasOK.setBounds(610, 110, 165, 23);

        btnPetugasOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasOK.setMnemonic('2');
        btnPetugasOK.setToolTipText("ALt+2");
        btnPetugasOK.setName("btnPetugasOK"); // NOI18N
        btnPetugasOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasOKActionPerformed(evt);
            }
        });
        btnPetugasOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasOKKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasOK);
        btnPetugasOK.setBounds(780, 110, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 810, 1);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 110, 100, 23);

        jLabel53.setText("Jenis Operasi :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 140, 140, 23);

        JenisOperasi
                .setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kecil", "Sedang", "Besar", "Khusus" }));
        JenisOperasi.setName("JenisOperasi"); // NOI18N
        JenisOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisOperasiKeyPressed(evt);
            }
        });
        FormInput.add(JenisOperasi);
        JenisOperasi.setBounds(150, 140, 170, 23);

        jLabel66.setText("Diagnosa Pra Bedah :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 170, 140, 23);

        JenisAnastesi.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Umum", "Spinal", "Epidural", "BSP*", "CSE*", "Lokal" }));
        JenisAnastesi.setName("JenisAnastesi"); // NOI18N
        JenisAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(JenisAnastesi);
        JenisAnastesi.setBounds(430, 140, 170, 23);

        DiagnosaPrabedah.setName("DiagnosaPrabedah"); // NOI18N
        DiagnosaPrabedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPrabedahKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPrabedah);
        DiagnosaPrabedah.setBounds(150, 170, 600, 23);

        btnTarikDiagnosa = new widget.Button();
        btnTarikDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTarikDiagnosa.setMnemonic('X');
        btnTarikDiagnosa.setToolTipText("Tarik Diagnosa Prabedah dari Penilaian Medis Ranap Kandungan");
        btnTarikDiagnosa.setName("btnTarikDiagnosa"); // NOI18N
        btnTarikDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarikDiagnosaActionPerformed(evt);
            }
        });
        btnTarikDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTarikDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnTarikDiagnosa);
        btnTarikDiagnosa.setBounds(752, 170, 28, 23);

        jLabel67.setText("Jenis Anastesi :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(330, 140, 90, 23);

        jLabel68.setText("Diagnosa Pasca Bedah :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 200, 140, 23);

        DiagnosaPascabedah.setName("DiagnosaPascabedah"); // NOI18N
        DiagnosaPascabedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPascabedahKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPascabedah);
        DiagnosaPascabedah.setBounds(150, 200, 630, 23);

        jLabel28.setText("Cara Pembiusan:");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(20, 320, 120, 23);

        CaraPembiusan.setName("CaraPembiusan"); // NOI18N
        CaraPembiusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraPembiusanKeyPressed(evt);
            }
        });
        FormInput.add(CaraPembiusan);
        CaraPembiusan.setBounds(150, 320, 630, 23);

        jLabel69.setText("Jaringan DiKirim :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 720, 140, 23);

        KetJaringan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KetJaringan.setName("KetJaringan"); // NOI18N
        KetJaringan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetJaringanKeyPressed(evt);
            }
        });
        FormInput.add(KetJaringan);
        KetJaringan.setBounds(150, 720, 130, 23);

        Jaringan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "PA", "Kultur" }));
        Jaringan.setName("Jaringan"); // NOI18N
        Jaringan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JaringanKeyPressed(evt);
            }
        });
        FormInput.add(Jaringan);
        Jaringan.setBounds(290, 720, 130, 23);

        jLabel32.setText("ml");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(770, 680, 12, 23);

        jLabel33.setText("Perdarahan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(610, 680, 80, 23);

        JumlahPerdarahan.setName("JumlahPerdarahan"); // NOI18N
        JumlahPerdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahPerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(JumlahPerdarahan);
        JumlahPerdarahan.setBounds(700, 680, 60, 23);

        jLabel35.setText("Jam Mulai Operasi :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(40, 260, 110, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Jam Selesai Operasi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(330, 260, 140, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        LaporanOperasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LaporanOperasi.setColumns(20);
        LaporanOperasi.setRows(5);
        LaporanOperasi.setName("LaporanOperasi"); // NOI18N
        LaporanOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporanOperasiKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(LaporanOperasi);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(150, 440, 630, 230);

        jLabel50.setText("Uraian Pembedahan:");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(10, 440, 140, 23);

        jLabel40.setText("Posisi Pasien:");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(20, 350, 120, 23);

        PosisiPasien.setName("PosisiPasien"); // NOI18N
        PosisiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiPasienKeyPressed(evt);
            }
        });
        FormInput.add(PosisiPasien);
        PosisiPasien.setBounds(150, 350, 630, 23);

        LamaPembedahan.setName("LamaPembedahan"); // NOI18N
        LamaPembedahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaPembedahanKeyPressed(evt);
            }
        });
        FormInput.add(LamaPembedahan);
        LamaPembedahan.setBounds(150, 290, 630, 23);

        jLabel42.setText("Lama Pembedahan:");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(20, 290, 120, 23);

        KomplikasiOperasi3.setName("KomplikasiOperasi3"); // NOI18N
        KomplikasiOperasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiOperasi3KeyPressed(evt);
            }
        });
        FormInput.add(KomplikasiOperasi3);
        KomplikasiOperasi3.setBounds(150, 290, 630, 23);

        jLabel43.setText("Asal Jaringan:");
        jLabel43.setName("jLabel43"); // NOI18N
        jLabel43.setPreferredSize(new java.awt.Dimension(42, 30));
        FormInput.add(jLabel43);
        jLabel43.setBounds(430, 720, 100, 23);

        AsalJaringan.setName("AsalJaringan"); // NOI18N
        AsalJaringan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalJaringanKeyPressed(evt);
            }
        });
        FormInput.add(AsalJaringan);
        AsalJaringan.setBounds(540, 720, 260, 30);

        TanggalJamMulaiOperasi.setForeground(new java.awt.Color(50, 70, 50));
        TanggalJamMulaiOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025 06:39:31" }));
        TanggalJamMulaiOperasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalJamMulaiOperasi.setName("TanggalJamMulaiOperasi"); // NOI18N
        TanggalJamMulaiOperasi.setOpaque(false);
        TanggalJamMulaiOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamMulaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(TanggalJamMulaiOperasi);
        TanggalJamMulaiOperasi.setBounds(160, 260, 130, 23);

        TanggalJamSelesaiOperasi.setForeground(new java.awt.Color(50, 70, 50));
        TanggalJamSelesaiOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-01-2025 06:39:31" }));
        TanggalJamSelesaiOperasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalJamSelesaiOperasi.setName("TanggalJamSelesaiOperasi"); // NOI18N
        TanggalJamSelesaiOperasi.setOpaque(false);
        TanggalJamSelesaiOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamSelesaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(TanggalJamSelesaiOperasi);
        TanggalJamSelesaiOperasi.setBounds(470, 260, 130, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KodeDokterBedah.getText().trim().equals("") || NamaDokterBedah.getText().trim().equals("")) {
            Valid.textKosong(btnDokterBedah, "Dokter Bedah");
        } else if (KodeDokterAnestesi.getText().trim().equals("") || NamaDokterAnestesi.getText().trim().equals("")) {
            Valid.textKosong(KodeDokterAnestesi, "Dokter Anestesi");
        } else {
            if (Sequel.menyimpantf("laporan_operasi_casemix",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "Data", 32, new String[] {
                            TNoRw.getText(),
                            Valid.SetTgl(Tanggal.getSelectedItem() + "") + " 00:00:00",
                            KodeDokterBedah.getText(),
                            KodeDokterAnestesi.getText(), 
                            KdAsistenBedah.getText().equals("") ? NmPetugasRuangan.getText() : KdAsistenBedah.getText(), 
                            KdPenataAnestesi.getText().equals("") ? NmPetugasOK.getText() : KdPenataAnestesi.getText(),
                            JenisOperasi.getSelectedItem().toString(), JenisAnastesi.getSelectedItem().toString(),
                            DiagnosaPrabedah.getText(), DiagnosaPascabedah.getText(), Tindakan.getText(),
                            Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                                    + cmbJamMulai.getSelectedItem() + ":" + cmbMntMulai.getSelectedItem() + ":" + cmbDtkMulai.getSelectedItem(),
                            Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                                    + cmbJamSelesai.getSelectedItem() + ":" + cmbMntSelesai.getSelectedItem() + ":" + cmbDtkSelesai.getSelectedItem(),
                            LamaPembedahan.getText(), CaraPembiusan.getText(), PosisiPasien.getText(),
                            "0000-00-00 00:00:00",
                            "0000-00-00 00:00:00",
                            "0000-00-00 00:00:00",
                            LaporanOperasi.getText(), Komplikasi.getText(), JumlahPerdarahan.getText(),
                            KetJaringan.getSelectedItem().toString(), Jaringan.getSelectedItem().toString(),
                            AsalJaringan.getText(),
                            JenisPembedahan.getSelectedItem().toString(),
                            rdImplanYa.isSelected() ? "Ya" : "Tidak",
                            LokasiImplan.getText(),
                            JenisImplan.getText(),
                            NoRegImplan.getText(),
                            KlasifikasiOperasi.getSelectedItem().toString(),
                            KonsultasiIntraOperatif.getText()
                    }) == true) {
                tabMode.addRow(new String[] {
                        TNoRw.getText(), TNoRM.getText(), TPasien.getText(), TglLahir.getText(),
                        JK.getText(),
                        Valid.SetTgl(Tanggal.getSelectedItem() + "") + " 00:00:00",
                        KodeDokterBedah.getText(), NamaDokterBedah.getText(),
                        KodeDokterAnestesi.getText(), NamaDokterAnestesi.getText(),
                        KdAsistenBedah.getText(), NmPetugasRuangan.getText(),
                        KdPenataAnestesi.getText(), NmPetugasOK.getText(),
                        JenisOperasi.getSelectedItem().toString(), JenisAnastesi.getSelectedItem().toString(),
                        DiagnosaPrabedah.getText(), DiagnosaPascabedah.getText(), Tindakan.getText(),
                        Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                                + cmbJamMulai.getSelectedItem() + ":" + cmbMntMulai.getSelectedItem() + ":" + cmbDtkMulai.getSelectedItem(),
                        Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                                + cmbJamSelesai.getSelectedItem() + ":" + cmbMntSelesai.getSelectedItem() + ":" + cmbDtkSelesai.getSelectedItem(),
                        LamaPembedahan.getText(), CaraPembiusan.getText(), PosisiPasien.getText(),
                        LaporanOperasi.getText(), Komplikasi.getText(), JumlahPerdarahan.getText(),
                        KetJaringan.getSelectedItem().toString(), Jaringan.getSelectedItem().toString(),
                        AsalJaringan.getText(),
                        JenisPembedahan.getSelectedItem().toString(),
                        rdImplanYa.isSelected() ? "Ya" : "Tidak",
                        LokasiImplan.getText(),
                        JenisImplan.getText(),
                        NoRegImplan.getText(),
                        KlasifikasiOperasi.getSelectedItem().toString(),
                        KonsultasiIntraOperatif.getText()
                });
                JOptionPane.showMessageDialog(null, "Laporan Berhasil DiSimpan..!!");
                LCount.setText("" + tabMode.getRowCount());
                emptTeks();
            }
        }
    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, btnPetugasOK, BtnBatal);
        }
    }// GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
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
        if (tbObat.getSelectedRow() > -1) {
            hapus();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
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
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KodeDokterBedah.getText().trim().equals("") || NamaDokterBedah.getText().trim().equals("")) {
            Valid.textKosong(btnDokterBedah, "Dokter Bedah");
        } else if (KodeDokterAnestesi.getText().trim().equals("") || NamaDokterAnestesi.getText().trim().equals("")) {
            Valid.textKosong(KodeDokterAnestesi, "Dokter Anestesi");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                ganti();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
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
        petugas.dispose();
        dokter.dispose();
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            try {
                htmlContent = new StringBuilder();
                htmlContent.append(
                        "<tr class='isi'>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Anest</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Anestesi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Asisten Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asisten Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Asisten Anest</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asisten Anestesi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Operasi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Anestesi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Pra Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Pasca Bedah</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam Mulai Operasi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam Selesai Operasi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lama Pembedahan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Pembiusan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Posisi Pasien</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Uraian Pembedahan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Komplikasi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perdarahan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dikirim</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Pemeriksaan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asal Jaringan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Pembedahan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasang Implan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi Implan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Implan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Reg Implan</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Klasifikasi Operasi</b></td>"
                                + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konsultasi Intra Op</b></td>"
                                + "</tr>");

                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                            "<tr class='isi'>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 0).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 1).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 2).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 3).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 4).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 5).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 6).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 7).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 8).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 9).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 10).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 11).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 12).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 13).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 14).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 15).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 16).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 17).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 18).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 19).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 20).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 21).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 22).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 23).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 24).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 25).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 26).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 27).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 28).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 29).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 30).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 31).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 32).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 33).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 34).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 35).toString() + "</td>"
                                    + "<td valign='top'>" + tbObat.getValueAt(i, 36).toString() + "</td>"
                                    + "</tr>");
                }

                LoadHTML.setText(
                        "<html>"
                                + "<table width='3500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
                                + htmlContent.toString()
                                + "</table>"
                                + "</html>");

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
                                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
                bg.close();

                File f = new File("DataChecklistPreOperasi.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>", "<head>"
                        + "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"
                        + "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        + "<tr class='isi2'>"
                        + "<td valign='top' align='center'>"
                        + "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>"
                        + akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                        + akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>"
                        + "<font size='2' face='Tahoma'>DATA CHECK LIST PRE OPERASI<br><br></font>"
                        + "</td>"
                        + "</tr>"
                        + "</table>"));
                bw.close();
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }// GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }// GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }// GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }// GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }// GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }// GEN-LAST:event_tbObatKeyPressed

    private void MnSkriningNutrisiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSkriningNutrisiActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            finger2 = Sequel.cariIsi(
                    "select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            param.put("finger2",
                    "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh "
                            + tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString() + "\nID "
                            + (finger2.equals("") ? tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString() : finger2)
                            + "\n" + Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirLaporanOperasi.jasper", "report", "::[ Formulir Laporan Operasi ]::",
                    "select reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.tgl_lahir, pasien.jk, laporan_operasi_casemix.tanggal, "
                            + "laporan_operasi_casemix.jenisoperasi, laporan_operasi_casemix.jenisanestesi, laporan_operasi_casemix.jammulaioperasi, "
                            + "laporan_operasi_casemix.jamselesaioperasi, laporan_operasi_casemix.tindakan, '' AS selisih_waktu, "
                            + "'' AS jammulaianestesi, '' AS jamselesaianestesi, '' AS selisih_waktu_anastesi, '' AS jampindah, "
                            + "laporan_operasi_casemix.uraian, laporan_operasi_casemix.kddokterbedah, dokterbedah.nm_dokter as dokterbedah, "
                            + "laporan_operasi_casemix.diagnosaprabedah, laporan_operasi_casemix.diagnosapascabedah, laporan_operasi_casemix.komplikasi, "
                            + "laporan_operasi_casemix.perdarahan, laporan_operasi_casemix.dikirim, laporan_operasi_casemix.dikirimket, laporan_operasi_casemix.asaljaringan, "
                            + "laporan_operasi_casemix.kddokteranestesi, dokteranestesi.nm_dokter as dokteranestesi, "
                            + "laporan_operasi_casemix.asistenbedah, petugasruangan.nama as petugasruangan, "
                            + "laporan_operasi_casemix.asistenanestesi, petugasok.nama as petugasok, "
                            + "laporan_operasi_casemix.jenispembedahan, laporan_operasi_casemix.pemasanganimplan, laporan_operasi_casemix.lokasiimplan, "
                            + "laporan_operasi_casemix.jenisimplan, laporan_operasi_casemix.noregimplan, laporan_operasi_casemix.klasifikasioperasi, "
                            + "laporan_operasi_casemix.konsultasiintraoperatif, laporan_operasi_casemix.lama, laporan_operasi_casemix.pembiusan, laporan_operasi_casemix.posisi "
                            + "from laporan_operasi_casemix inner join reg_periksa on laporan_operasi_casemix.no_rawat=reg_periksa.no_rawat "
                            + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "left join dokter as dokterbedah on dokterbedah.kd_dokter=laporan_operasi_casemix.kddokterbedah "
                            + "left join dokter as dokteranestesi on dokteranestesi.kd_dokter=laporan_operasi_casemix.kddokteranestesi "
                            + "left join petugas as petugasruangan on petugasruangan.nip=laporan_operasi_casemix.asistenbedah "
                            + "left join petugas as petugasok on petugasok.nip=laporan_operasi_casemix.asistenanestesi "
                            + "where laporan_operasi_casemix.no_rawat='"
                            + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                            + "' and laporan_operasi_casemix.tanggal='"
                            + tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString() + "' ",
                    param);
        }
    }// GEN-LAST:event_MnSkriningNutrisiActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }// GEN-LAST:event_ChkInputActionPerformed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JKKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JKKeyPressed

    private void JenisAnastesiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JenisAnastesiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JenisAnastesiKeyPressed

    private void JenisOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JenisOperasiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JenisOperasiKeyPressed

    private void btnPetugasOKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPetugasOKKeyPressed
        Valid.pindah(evt, btnPetugasRuangan, BtnSimpan);
    }// GEN-LAST:event_btnPetugasOKKeyPressed

    private void btnPetugasOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPetugasOKActionPerformed
        pilihan = 2;
        petugas.kriteriaJabatan = "penata_anestesi";
        petugas.load();
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }// GEN-LAST:event_btnPetugasOKActionPerformed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt, Komplikasi, btnDokterBedah);
    }// GEN-LAST:event_TindakanKeyPressed

    private void btnDokterAnestesiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnDokterAnestesiKeyPressed
        // Valid.pindah(evt,btnDokterBedah,Identitas);
    }// GEN-LAST:event_btnDokterAnestesiKeyPressed

    private void btnDokterAnestesiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDokterAnestesiActionPerformed
        pilihan = 2;
        dokter.emptTeks();
        dokter.isCek();
        dokter.TCari.setText("Anestesi");
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }// GEN-LAST:event_btnDokterAnestesiActionPerformed

    private void btnDokterBedahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnDokterBedahKeyPressed
        Valid.pindah(evt, Tindakan, btnDokterAnestesi);
    }// GEN-LAST:event_btnDokterBedahKeyPressed

    private void btnDokterBedahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDokterBedahActionPerformed
        pilihan = 1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }// GEN-LAST:event_btnDokterBedahActionPerformed

    private void KomplikasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KomplikasiKeyPressed
        Valid.pindah(evt, Tanggal, Tindakan);
    }// GEN-LAST:event_KomplikasiKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TanggalKeyPressed
        // Valid.pindah(evt,Rencana,Informasi);
    }// GEN-LAST:event_TanggalKeyPressed

    private void btnPetugasRuanganKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPetugasRuanganKeyPressed
        // Valid.pindah(evt,KeteranganMRI,btnPetugasOK);
    }// GEN-LAST:event_btnPetugasRuanganKeyPressed

    private void btnPetugasRuanganActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPetugasRuanganActionPerformed
        pilihan = 1;
        petugas.kriteriaJabatan = "perawat_ok_rr";
        petugas.load();
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }// GEN-LAST:event_btnPetugasRuanganActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }// GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
    }// GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
    }// GEN-LAST:event_TNoRwKeyPressed

    private void DiagnosaPrabedahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DiagnosaPrabedahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DiagnosaPrabedahKeyPressed

    private void btnTarikDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {
        String diagnosaKandungan = Sequel.cariIsi("select diagnosis from penilaian_medis_ranap_kandungan1 where no_rawat=?", TNoRw.getText());
        if (!diagnosaKandungan.equals("")) {
            DiagnosaPrabedah.setText(diagnosaKandungan);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Data diagnosis kandungan tidak ditemukan untuk pasien ini.");
        }
    }

    private void btnTarikDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
            btnTarikDiagnosaActionPerformed(null);
        } else {
            Valid.pindah(evt, DiagnosaPrabedah, DiagnosaPascabedah);
        }
    }

    private void DiagnosaPascabedahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DiagnosaPascabedahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DiagnosaPascabedahKeyPressed

    private void CaraPembiusanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_CaraPembiusanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_CaraPembiusanKeyPressed

    private void KetJaringanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetJaringanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KetJaringanKeyPressed

    private void JaringanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JaringanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JaringanKeyPressed

    private void JumlahPerdarahanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JumlahPerdarahanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JumlahPerdarahanKeyPressed

    private void LaporanOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LaporanOperasiKeyPressed
        // Valid.pindah2(evt,Kista,BtnSimpan);
    }// GEN-LAST:event_LaporanOperasiKeyPressed

    private void PosisiPasienKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PosisiPasienKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_PosisiPasienKeyPressed

    private void LamaPembedahanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LamaPembedahanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_LamaPembedahanKeyPressed

    private void KomplikasiOperasi3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KomplikasiOperasi3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_KomplikasiOperasi3KeyPressed

    private void AsalJaringanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AsalJaringanKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_AsalJaringanKeyPressed

    private void TanggalJamMulaiOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TanggalJamMulaiOperasiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TanggalJamMulaiOperasiKeyPressed

    private void TanggalJamSelesaiOperasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TanggalJamSelesaiOperasiKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TanggalJamSelesaiOperasiKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMLaporanOperasi dialog = new RMLaporanOperasi(new javax.swing.JFrame(), true);
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
    private widget.TextBox AsalJaringan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CaraPembiusan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaPascabedah;
    private widget.TextBox DiagnosaPrabedah;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jaringan;
    private widget.ComboBox JenisAnastesi;
    private widget.ComboBox JenisOperasi;
    private widget.TextBox JumlahPerdarahan;
    private widget.TextBox KdAsistenBedah;
    private widget.TextBox KdPenataAnestesi;
    private widget.ComboBox KetJaringan;
    private widget.TextBox KodeDokterAnestesi;
    private widget.TextBox KodeDokterBedah;
    private widget.TextBox Komplikasi;
    private widget.TextBox KomplikasiOperasi3;
    private widget.Label LCount;
    private widget.TextBox LamaPembedahan;
    private widget.TextArea LaporanOperasi;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnSkriningNutrisi;
    private widget.TextBox NamaDokterAnestesi;
    private widget.TextBox NamaDokterBedah;
    private widget.TextBox NmPetugasOK;
    private widget.TextBox NmPetugasRuangan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PosisiPasien;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalJamMulaiOperasi;
    private widget.Tanggal TanggalJamSelesaiOperasi;
    private widget.ComboBox cmbJamMulai;
    private widget.ComboBox cmbMntMulai;
    private widget.ComboBox cmbDtkMulai;
    private widget.ComboBox cmbJamSelesai;
    private widget.ComboBox cmbMntSelesai;
    private widget.ComboBox cmbDtkSelesai;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.Button btnDokterAnestesi;
    private widget.Button btnDokterBedah;
    private widget.Button btnPetugasOK;
    private widget.Button btnTarikDiagnosa;
    private widget.Button btnPetugasRuangan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel53;
    private widget.Label jLabel6;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane17;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, laporan_operasi_casemix.tanggal, laporan_operasi_casemix.kddokterbedah, laporan_operasi_casemix.kddokteranestesi, "
                                + "laporan_operasi_casemix.asistenbedah, laporan_operasi_casemix.asistenanestesi, laporan_operasi_casemix.jenisoperasi, laporan_operasi_casemix.jenisanestesi, "
                                + "laporan_operasi_casemix.diagnosaprabedah, laporan_operasi_casemix.diagnosapascabedah, laporan_operasi_casemix.tindakan, laporan_operasi_casemix.jammulaioperasi, "
                                + "laporan_operasi_casemix.jamselesaioperasi, laporan_operasi_casemix.lama, laporan_operasi_casemix.pembiusan, laporan_operasi_casemix.posisi, "
                                + "laporan_operasi_casemix.uraian, "
                                + "laporan_operasi_casemix.komplikasi, laporan_operasi_casemix.perdarahan, laporan_operasi_casemix.dikirim, laporan_operasi_casemix.dikirimket, "
                                + "laporan_operasi_casemix.asaljaringan, "
                                + "laporan_operasi_casemix.jenispembedahan, laporan_operasi_casemix.pemasanganimplan, laporan_operasi_casemix.lokasiimplan, "
                                + "laporan_operasi_casemix.jenisimplan, laporan_operasi_casemix.noregimplan, laporan_operasi_casemix.klasifikasioperasi, "
                                + "laporan_operasi_casemix.konsultasiintraoperatif, "
                                + "reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir "
                                + "FROM laporan_operasi_casemix INNER JOIN reg_periksa ON laporan_operasi_casemix.no_rawat = reg_periksa.no_rawat "
                                + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                                + "where laporan_operasi_casemix.tanggal between ? and ? order by laporan_operasi_casemix.tanggal ");
            } else {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, laporan_operasi_casemix.tanggal, laporan_operasi_casemix.kddokterbedah, laporan_operasi_casemix.kddokteranestesi, "
                                + "laporan_operasi_casemix.asistenbedah, laporan_operasi_casemix.asistenanestesi, laporan_operasi_casemix.jenisoperasi, "
                                + "laporan_operasi_casemix.jenisanestesi, laporan_operasi_casemix.diagnosaprabedah, laporan_operasi_casemix.diagnosapascabedah, "
                                + "laporan_operasi_casemix.tindakan, laporan_operasi_casemix.jammulaioperasi, laporan_operasi_casemix.jamselesaioperasi, "
                                + "laporan_operasi_casemix.lama, laporan_operasi_casemix.pembiusan, laporan_operasi_casemix.posisi, "
                                + "laporan_operasi_casemix.uraian, laporan_operasi_casemix.komplikasi, "
                                + "laporan_operasi_casemix.perdarahan, laporan_operasi_casemix.dikirim, laporan_operasi_casemix.dikirimket, "
                                + "laporan_operasi_casemix.asaljaringan, "
                                + "laporan_operasi_casemix.jenispembedahan, laporan_operasi_casemix.pemasanganimplan, laporan_operasi_casemix.lokasiimplan, "
                                + "laporan_operasi_casemix.jenisimplan, laporan_operasi_casemix.noregimplan, laporan_operasi_casemix.klasifikasioperasi, "
                                + "laporan_operasi_casemix.konsultasiintraoperatif, "
                                + "reg_periksa.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir "
                                + "FROM laporan_operasi_casemix INNER JOIN reg_periksa ON laporan_operasi_casemix.no_rawat = reg_periksa.no_rawat "
                                + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                                + "where laporan_operasi_casemix.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "
                                + "pasien.nm_pasien like ?) "
                                + "order by laporan_operasi_casemix.tanggal ");
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
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[] {
                            rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                            rs.getString("tgl_lahir"),
                            rs.getString("jk"), rs.getString("tanggal"), rs.getString("kddokterbedah"),
                            dokter.tampil3(rs.getString("kddokterbedah")),
                            rs.getString("kddokteranestesi"), dokter.tampil3(rs.getString("kddokteranestesi")),
                            rs.getString("asistenbedah"), petugas.tampil3(rs.getString("asistenbedah")).equals("") ? rs.getString("asistenbedah") : petugas.tampil3(rs.getString("asistenbedah")),
                            rs.getString("asistenanestesi"), petugas.tampil3(rs.getString("asistenanestesi")).equals("") ? rs.getString("asistenanestesi") : petugas.tampil3(rs.getString("asistenanestesi")),
                            rs.getString("jenisoperasi"),
                            rs.getString("jenisanestesi"), rs.getString("diagnosaprabedah"),
                            rs.getString("diagnosapascabedah"), rs.getString("tindakan"),
                            rs.getString("jammulaioperasi"), rs.getString("jamselesaioperasi"), rs.getString("lama"),
                            rs.getString("pembiusan"),
                            rs.getString("posisi"), rs.getString("uraian"), rs.getString("komplikasi"), rs.getString("perdarahan"),
                            rs.getString("dikirim"), rs.getString("dikirimket"),
                            rs.getString("asaljaringan"), rs.getString("jenispembedahan"),
                            rs.getString("pemasanganimplan"), rs.getString("lokasiimplan"),
                            rs.getString("jenisimplan"), rs.getString("noregimplan"),
                            rs.getString("klasifikasioperasi"), rs.getString("konsultasiintraoperatif")

                    });

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
        LCount.setText("" + tabMode.getRowCount());
    }

    private void initLaporanOperasi() {
        TanggalJamMulaiOperasi.setVisible(false);
        TanggalJamSelesaiOperasi.setVisible(false);

        java.awt.Component[] components = FormInput.getComponents();
        for (java.awt.Component comp : components) {
            java.awt.Rectangle bounds = comp.getBounds();
            if (bounds.y >= 260) {
                comp.setBounds(bounds.x, bounds.y + 30, bounds.width, bounds.height);
            }
        }

        lblJenisPembedahan = new widget.Label();
        lblJenisPembedahan.setText("Jenis Pembedahan :");
        lblJenisPembedahan.setName("lblJenisPembedahan");
        FormInput.add(lblJenisPembedahan);
        lblJenisPembedahan.setBounds(10, 260, 130, 23);

        JenisPembedahan = new widget.ComboBox();
        JenisPembedahan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Bersih", "Bersih Tercemar", "Tercemar", "Kotor" }));
        JenisPembedahan.setName("JenisPembedahan");
        FormInput.add(JenisPembedahan);
        JenisPembedahan.setBounds(150, 260, 170, 23);

        components = FormInput.getComponents();
        for (java.awt.Component comp : components) {
            java.awt.Rectangle bounds = comp.getBounds();
            if (bounds.y >= 320) {
                comp.setBounds(bounds.x, bounds.y + 30, bounds.width, bounds.height);
            }
        }
        
        jLabel36.setBounds(40, 320, 110, 23);
        TanggalJamSelesaiOperasi.setBounds(160, 320, 90, 23);

        components = FormInput.getComponents();
        for (java.awt.Component comp : components) {
            java.awt.Rectangle bounds = comp.getBounds();
            if (bounds.y >= 470) {
                comp.setBounds(bounds.x, bounds.y + 120, bounds.width, bounds.height);
            }
        }

        lblPemasanganImplan = new widget.Label();
        lblPemasanganImplan.setText("Pemasangan Implan :");
        lblPemasanganImplan.setName("lblPemasanganImplan");
        FormInput.add(lblPemasanganImplan);
        lblPemasanganImplan.setBounds(10, 470, 130, 23);

        bgImplan = new javax.swing.ButtonGroup();
        rdImplanYa = new javax.swing.JRadioButton("Ya");
        rdImplanTidak = new javax.swing.JRadioButton("Tidak");
        rdImplanTidak.setSelected(true);
        bgImplan.add(rdImplanYa);
        bgImplan.add(rdImplanTidak);
        FormInput.add(rdImplanYa);
        FormInput.add(rdImplanTidak);
        rdImplanYa.setBounds(150, 470, 60, 23);
        rdImplanTidak.setBounds(220, 470, 80, 23);

        lblLokasiImplan = new widget.Label();
        lblLokasiImplan.setText("Lokasi Implan :");
        lblLokasiImplan.setName("lblLokasiImplan");
        FormInput.add(lblLokasiImplan);
        lblLokasiImplan.setBounds(10, 500, 130, 23);

        LokasiImplan = new widget.TextBox();
        LokasiImplan.setName("LokasiImplan");
        FormInput.add(LokasiImplan);
        LokasiImplan.setBounds(150, 500, 630, 23);

        lblJenisImplan = new widget.Label();
        lblJenisImplan.setText("Jenis Implan :");
        lblJenisImplan.setName("lblJenisImplan");
        FormInput.add(lblJenisImplan);
        lblJenisImplan.setBounds(10, 530, 130, 23);

        JenisImplan = new widget.TextBox();
        JenisImplan.setName("JenisImplan");
        FormInput.add(JenisImplan);
        JenisImplan.setBounds(150, 530, 630, 23);

        lblNoRegImplan = new widget.Label();
        lblNoRegImplan.setText("No Reg. Implan :");
        lblNoRegImplan.setName("lblNoRegImplan");
        FormInput.add(lblNoRegImplan);
        lblNoRegImplan.setBounds(10, 560, 130, 23);

        NoRegImplan = new widget.TextBox();
        NoRegImplan.setName("NoRegImplan");
        FormInput.add(NoRegImplan);
        NoRegImplan.setBounds(150, 560, 300, 23);

        components = FormInput.getComponents();
        for (java.awt.Component comp : components) {
            java.awt.Rectangle bounds = comp.getBounds();
            if (bounds.y >= 590) {
                comp.setBounds(bounds.x, bounds.y + 60, bounds.width, bounds.height);
            }
        }

        lblKlasifikasiOperasi = new widget.Label();
        lblKlasifikasiOperasi.setText("Klasifikasi Operasi :");
        lblKlasifikasiOperasi.setName("lblKlasifikasiOperasi");
        FormInput.add(lblKlasifikasiOperasi);
        lblKlasifikasiOperasi.setBounds(10, 590, 130, 23);

        KlasifikasiOperasi = new widget.ComboBox();
        KlasifikasiOperasi.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Emergency/Cito", "Elektif", "ODS" }));
        KlasifikasiOperasi.setName("KlasifikasiOperasi");
        FormInput.add(KlasifikasiOperasi);
        KlasifikasiOperasi.setBounds(150, 590, 170, 23);

        lblKonsultasiIntraOperatif = new widget.Label();
        lblKonsultasiIntraOperatif.setText("Konsul Intra Op. :");
        lblKonsultasiIntraOperatif.setName("lblKonsultasiIntraOperatif");
        FormInput.add(lblKonsultasiIntraOperatif);
        lblKonsultasiIntraOperatif.setBounds(10, 620, 130, 23);

        KonsultasiIntraOperatif = new widget.TextBox();
        KonsultasiIntraOperatif.setName("KonsultasiIntraOperatif");
        FormInput.add(KonsultasiIntraOperatif);
        KonsultasiIntraOperatif.setBounds(150, 620, 630, 23);

        java.awt.Dimension prefSize = FormInput.getPreferredSize();
        FormInput.setPreferredSize(new java.awt.Dimension(Math.max(prefSize.width, 870), prefSize.height + 240));

        KodeDokterBedah.setVisible(false);
        NamaDokterBedah.setBounds(90, 70, 275, 23);

        KodeDokterAnestesi.setVisible(false);
        NamaDokterAnestesi.setBounds(507, 70, 268, 23);

        KdAsistenBedah.setVisible(false);
        NmPetugasRuangan.setBounds(110, 110, 265, 23);

        KdPenataAnestesi.setVisible(false);
        NmPetugasOK.setBounds(510, 110, 265, 23);

        cmbJamMulai = new widget.ComboBox();
        cmbJamMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJamMulai.setName("cmbJamMulai");
        cmbJamMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamMulaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbJamMulai);
        cmbJamMulai.setBounds(160, 290, 50, 23);

        cmbMntMulai = new widget.ComboBox();
        cmbMntMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntMulai.setName("cmbMntMulai");
        cmbMntMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamMulaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbMntMulai);
        cmbMntMulai.setBounds(212, 290, 50, 23);

        cmbDtkMulai = new widget.ComboBox();
        cmbDtkMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkMulai.setName("cmbDtkMulai");
        cmbDtkMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamMulaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtkMulai);
        cmbDtkMulai.setBounds(264, 290, 50, 23);

        cmbJamSelesai = new widget.ComboBox();
        cmbJamSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJamSelesai.setName("cmbJamSelesai");
        cmbJamSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamSelesaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbJamSelesai);
        cmbJamSelesai.setBounds(160, 320, 50, 23);

        cmbMntSelesai = new widget.ComboBox();
        cmbMntSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntSelesai.setName("cmbMntSelesai");
        cmbMntSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamSelesaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbMntSelesai);
        cmbMntSelesai.setBounds(212, 320, 50, 23);

        cmbDtkSelesai = new widget.ComboBox();
        cmbDtkSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkSelesai.setName("cmbDtkSelesai");
        cmbDtkSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJamSelesaiOperasiKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtkSelesai);
        cmbDtkSelesai.setBounds(264, 320, 50, 23);

        java.awt.event.ItemListener hitungLamaListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    hitungLamaOperasi();
                }
            }
        };
        cmbJamMulai.addItemListener(hitungLamaListener);
        cmbMntMulai.addItemListener(hitungLamaListener);
        cmbJamSelesai.addItemListener(hitungLamaListener);
        cmbMntSelesai.addItemListener(hitungLamaListener);

        FormInput.revalidate();
        FormInput.repaint();

        BtnTemplate = new widget.Button();
        BtnTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        BtnTemplate.setText("Template");
        BtnTemplate.setToolTipText("Pilih Template Laporan Operasi");
        BtnTemplate.setName("BtnTemplate");
        BtnTemplate.setPreferredSize(new java.awt.Dimension(120, 23));
        BtnTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                template.emptTeks();
                template.isCek();
                template.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                template.setLocationRelativeTo(internalFrame1);
                template.setVisible(true);
            }
        });
        panelGlass9.add(BtnTemplate);


        template.addWindowListener(new java.awt.event.WindowListener() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (template.getTable().getSelectedRow() != -1) {
                    int row = template.getTable().getSelectedRow();
                    javax.swing.JTable tbl = template.getTable();
                    JenisOperasi.setSelectedItem(tbl.getValueAt(row, 2).toString());
                    JenisAnastesi.setSelectedItem(tbl.getValueAt(row, 3).toString());
                    DiagnosaPrabedah.setText(tbl.getValueAt(row, 4).toString());
                    DiagnosaPascabedah.setText(tbl.getValueAt(row, 5).toString());
                    Tindakan.setText(tbl.getValueAt(row, 6).toString());
                    LamaPembedahan.setText(tbl.getValueAt(row, 7).toString());
                    CaraPembiusan.setText(tbl.getValueAt(row, 8).toString());
                    PosisiPasien.setText(tbl.getValueAt(row, 9).toString());
                    LaporanOperasi.setText(tbl.getValueAt(row, 10).toString());
                    Komplikasi.setText(tbl.getValueAt(row, 11).toString());
                    JumlahPerdarahan.setText(tbl.getValueAt(row, 12).toString());
                    KetJaringan.setSelectedItem(tbl.getValueAt(row, 13).toString());
                    Jaringan.setSelectedItem(tbl.getValueAt(row, 14).toString());
                    AsalJaringan.setText(tbl.getValueAt(row, 15).toString());
                    JenisPembedahan.setSelectedItem(tbl.getValueAt(row, 16).toString());
                    String implan = tbl.getValueAt(row, 17).toString();
                    if (implan.equals("Ya")) {
                        rdImplanYa.setSelected(true);
                    } else {
                        rdImplanTidak.setSelected(true);
                    }
                    LokasiImplan.setText(tbl.getValueAt(row, 18).toString());
                    JenisImplan.setText(tbl.getValueAt(row, 19).toString());
                    NoRegImplan.setText(tbl.getValueAt(row, 20).toString());
                    KlasifikasiOperasi.setSelectedItem(tbl.getValueAt(row, 21).toString());
                    KonsultasiIntraOperatif.setText(tbl.getValueAt(row, 22).toString());
                    LaporanOperasi.requestFocus();
                }
            }

            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeactivated(java.awt.event.WindowEvent e) {
            }
        });
    }

    private void hitungLamaOperasi() {
        try {
            int jamMulai = Integer.parseInt(cmbJamMulai.getSelectedItem().toString());
            int mntMulai = Integer.parseInt(cmbMntMulai.getSelectedItem().toString());
            int jamSelesai = Integer.parseInt(cmbJamSelesai.getSelectedItem().toString());
            int mntSelesai = Integer.parseInt(cmbMntSelesai.getSelectedItem().toString());

            int totalMulai = (jamMulai * 60) + mntMulai;
            int totalSelesai = (jamSelesai * 60) + mntSelesai;

            if (totalSelesai < totalMulai) {
                totalSelesai += (24 * 60); // Jika melewati tengah malam
            }

            int selisih = totalSelesai - totalMulai;
            LamaPembedahan.setText(selisih + " Menit");
        } catch (Exception e) {
            // Abaikan jika terjadi error parsing
        }
    }

    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        TglLahir.setText("");
        KodeDokterBedah.setText("");
        NamaDokterBedah.setText("");
        KodeDokterAnestesi.setText("");
        NamaDokterAnestesi.setText("");
        KdAsistenBedah.setText("");
        NmPetugasRuangan.setText("");
        KdPenataAnestesi.setText("");
        NmPetugasOK.setText("");
        JenisOperasi.setSelectedIndex(0);
        JenisAnastesi.setSelectedIndex(0);
        DiagnosaPrabedah.setText("");
        DiagnosaPascabedah.setText("");
        Tindakan.setText("");
        LamaPembedahan.setText("");
        CaraPembiusan.setText("");
        PosisiPasien.setText("");
        LaporanOperasi.setText("");
        Komplikasi.setText("");
        JumlahPerdarahan.setText("");
        AsalJaringan.setText("");
        JenisPembedahan.setSelectedIndex(0);
        rdImplanTidak.setSelected(true);
        LokasiImplan.setText("");
        JenisImplan.setText("");
        NoRegImplan.setText("");
        KlasifikasiOperasi.setSelectedIndex(0);
        KonsultasiIntraOperatif.setText("");
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            int r = tbObat.getSelectedRow();
            TNoRw.setText(tbObat.getValueAt(r, 0).toString());
            TNoRM.setText(tbObat.getValueAt(r, 1).toString());
            TPasien.setText(tbObat.getValueAt(r, 2).toString());
            TglLahir.setText(tbObat.getValueAt(r, 3).toString());
            Valid.SetTgl2(Tanggal, tbObat.getValueAt(r, 5).toString());
            KodeDokterBedah.setText(tbObat.getValueAt(r, 6).toString());
            NamaDokterBedah.setText(tbObat.getValueAt(r, 7).toString());
            KodeDokterAnestesi.setText(tbObat.getValueAt(r, 8).toString());
            NamaDokterAnestesi.setText(tbObat.getValueAt(r, 9).toString());
            KdAsistenBedah.setText(tbObat.getValueAt(r, 10).toString());
            NmPetugasRuangan.setText(tbObat.getValueAt(r, 11).toString());
            KdPenataAnestesi.setText(tbObat.getValueAt(r, 12).toString());
            NmPetugasOK.setText(tbObat.getValueAt(r, 13).toString());
            JenisOperasi.setSelectedItem(tbObat.getValueAt(r, 14).toString());
            JenisAnastesi.setSelectedItem(tbObat.getValueAt(r, 15).toString());
            DiagnosaPrabedah.setText(tbObat.getValueAt(r, 16).toString());
            DiagnosaPascabedah.setText(tbObat.getValueAt(r, 17).toString());
            Tindakan.setText(tbObat.getValueAt(r, 18).toString());
            cmbJamMulai.setSelectedItem(tbObat.getValueAt(r, 19).toString().substring(11, 13));
            cmbMntMulai.setSelectedItem(tbObat.getValueAt(r, 19).toString().substring(14, 16));
            cmbDtkMulai.setSelectedItem(tbObat.getValueAt(r, 19).toString().substring(17, 19));
            cmbJamSelesai.setSelectedItem(tbObat.getValueAt(r, 20).toString().substring(11, 13));
            cmbMntSelesai.setSelectedItem(tbObat.getValueAt(r, 20).toString().substring(14, 16));
            cmbDtkSelesai.setSelectedItem(tbObat.getValueAt(r, 20).toString().substring(17, 19));
            LamaPembedahan.setText(tbObat.getValueAt(r, 21).toString());
            CaraPembiusan.setText(tbObat.getValueAt(r, 22).toString());
            PosisiPasien.setText(tbObat.getValueAt(r, 23).toString());
            LaporanOperasi.setText(tbObat.getValueAt(r, 24).toString());
            Komplikasi.setText(tbObat.getValueAt(r, 25).toString());
            JumlahPerdarahan.setText(tbObat.getValueAt(r, 26).toString());
            KetJaringan.setSelectedItem(tbObat.getValueAt(r, 27).toString());
            Jaringan.setSelectedItem(tbObat.getValueAt(r, 28).toString());
            AsalJaringan.setText(tbObat.getValueAt(r, 29).toString());
            JenisPembedahan.setSelectedItem(tbObat.getValueAt(r, 30).toString());
            String implan = tbObat.getValueAt(r, 31).toString();
            if (implan.equals("Ya")) {
                rdImplanYa.setSelected(true);
            } else {
                rdImplanTidak.setSelected(true);
            }
            LokasiImplan.setText(tbObat.getValueAt(r, 32).toString());
            JenisImplan.setText(tbObat.getValueAt(r, 33).toString());
            NoRegImplan.setText(tbObat.getValueAt(r, 34).toString());
            KlasifikasiOperasi.setSelectedItem(tbObat.getValueAt(r, 35).toString());
            KonsultasiIntraOperatif.setText(tbObat.getValueAt(r, 36).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "
                            + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
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
        
        try {
            ps = koneksi.prepareStatement("select tanggal, jam_mulai, jam_selesai from booking_operasi where no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    String tglMulai = rs.getString("tanggal") + " " + rs.getString("jam_mulai");
                    String tglSelesai = rs.getString("tanggal") + " " + rs.getString("jam_selesai");
                    // Valid.SetTgl2(TanggalJamMulaiOperasi, tglMulai);
                    cmbJamMulai.setSelectedItem(rs.getString("jam_mulai").substring(0, 2));
                    cmbMntMulai.setSelectedItem(rs.getString("jam_mulai").substring(3, 5));
                    cmbDtkMulai.setSelectedItem(rs.getString("jam_mulai").substring(6, 8));
                    // Valid.SetTgl2(TanggalJamSelesaiOperasi, tglSelesai);
                    cmbJamSelesai.setSelectedItem(rs.getString("jam_selesai").substring(0, 2));
                    cmbMntSelesai.setSelectedItem(rs.getString("jam_selesai").substring(3, 5));
                    cmbDtkSelesai.setSelectedItem(rs.getString("jam_selesai").substring(6, 8));
                    // Valid.SetTgl2(TanggalJamMulaiAnestesi, tglMulai);
                    // Valid.SetTgl2(TanggalJamSelesaiAnestesi, tglSelesai);
                    // Valid.SetTgl2(TanggalJamPindah, tglSelesai);
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
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }

    public void setNoRm(String norwt, Date tgl2, String KodeDokter, String NamaDokter, String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
        KodeDokterBedah.setText(KodeDokter);
        NamaDokterBedah.setText(NamaDokter);
        Tindakan.setText(Operasi);
        tampil();
    }

    private void isForm() {
        // Logika dinonaktifkan karena UI sudah diubah menggunakan JTabbedPane
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getchecklist_pre_operasi());
        BtnHapus.setEnabled(akses.getchecklist_pre_operasi());
        BtnEdit.setEnabled(akses.getchecklist_pre_operasi());
        BtnPrint.setEnabled(akses.getchecklist_pre_operasi());
        tampil();
    }

    /**
     * Memaparkan data form saat ini agar bisa disimpan menjadi template
     * oleh DlgCariTemplateLaporanOperasiCasemix
     * 
     * @return array of string dari urutan field data
     */
    public String[] getFieldsData() {
        return new String[] {
                JenisOperasi.getSelectedItem().toString(),
                JenisAnastesi.getSelectedItem().toString(),
                DiagnosaPrabedah.getText(),
                DiagnosaPascabedah.getText(),
                Tindakan.getText(),
                LamaPembedahan.getText(),
                CaraPembiusan.getText(),
                PosisiPasien.getText(),
                LaporanOperasi.getText(),
                Komplikasi.getText(),
                JumlahPerdarahan.getText(),
                KetJaringan.getSelectedItem().toString(),
                Jaringan.getSelectedItem().toString(),
                AsalJaringan.getText(),
                JenisPembedahan.getSelectedItem().toString(),
                rdImplanYa.isSelected() ? "Ya" : "Tidak",
                LokasiImplan.getText(),
                JenisImplan.getText(),
                NoRegImplan.getText(),
                KlasifikasiOperasi.getSelectedItem().toString(),
                KonsultasiIntraOperatif.getText()
        };
    }

    private void ganti() {
        if (Sequel.mengedittf("laporan_operasi_casemix", "no_rawat=? and tanggal=?",
                "kddokterbedah=?, kddokteranestesi=?, asistenbedah=?, asistenanestesi=?, jenisoperasi=?, jenisanestesi=?, "
                + "diagnosaprabedah=?, diagnosapascabedah=?, tindakan=?, jammulaioperasi=?, jamselesaioperasi=?, lama=?, "
                + "pembiusan=?, posisi=?, jammulaianestesi=?, jamselesaianestesi=?, jampindah=?, uraian=?, komplikasi=?, "
                + "perdarahan=?, dikirim=?, dikirimket=?, asaljaringan=?, jenispembedahan=?, pemasanganimplan=?, "
                + "lokasiimplan=?, jenisimplan=?, noregimplan=?, klasifikasioperasi=?, konsultasiintraoperatif=?, no_rawat=?, tanggal=?",
                34, new String[] {
                    KodeDokterBedah.getText(), KodeDokterAnestesi.getText(),
                    KdAsistenBedah.getText().equals("") ? NmPetugasRuangan.getText() : KdAsistenBedah.getText(), 
                    KdPenataAnestesi.getText().equals("") ? NmPetugasOK.getText() : KdPenataAnestesi.getText(),
                    JenisOperasi.getSelectedItem().toString(), JenisAnastesi.getSelectedItem().toString(),
                    DiagnosaPrabedah.getText(), DiagnosaPascabedah.getText(), Tindakan.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                            + cmbJamMulai.getSelectedItem() + ":" + cmbMntMulai.getSelectedItem() + ":" + cmbDtkMulai.getSelectedItem(),
                    Valid.SetTgl(Tanggal.getSelectedItem() + "") + " "
                            + cmbJamSelesai.getSelectedItem() + ":" + cmbMntSelesai.getSelectedItem() + ":" + cmbDtkSelesai.getSelectedItem(),
                    LamaPembedahan.getText(), CaraPembiusan.getText(), PosisiPasien.getText(),
                    "0000-00-00 00:00:00",
                    "0000-00-00 00:00:00",
                    "0000-00-00 00:00:00",
                    LaporanOperasi.getText(), Komplikasi.getText(), JumlahPerdarahan.getText(),
                    KetJaringan.getSelectedItem().toString(), Jaringan.getSelectedItem().toString(),
                    AsalJaringan.getText(),
                    JenisPembedahan.getSelectedItem().toString(),
                    rdImplanYa.isSelected() ? "Ya" : "Tidak",
                    LokasiImplan.getText(),
                    JenisImplan.getText(),
                    NoRegImplan.getText(),
                    KlasifikasiOperasi.getSelectedItem().toString(),
                    KonsultasiIntraOperatif.getText(),
                    TNoRw.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem() + "") + " 00:00:00",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()
                }) == true) {
            
            JOptionPane.showMessageDialog(null, "Laporan Berhasil DiGanti..!!");
            tampil();
            emptTeks();
        }
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from laporan_operasi_casemix where no_rawat=? and tanggal=?", 2, new String[] {
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()
        }) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }
}
