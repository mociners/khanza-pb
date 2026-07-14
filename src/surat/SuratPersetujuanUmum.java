/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package surat;

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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgPersetujuanWebcam;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
/**
 *
 * @author windiartohugroho
 */
public final class SuratPersetujuanUmum extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String finger = "", lokasifile = "";

    public SuratPersetujuanUmum(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (ChkInput.isSelected()) {
                    PanelInput.setPreferredSize(new Dimension(WIDTH, Math.max(150, (int)(SuratPersetujuanUmum.this.getHeight() * 0.55))));
                    PanelInput.revalidate();
                }
            }
        });
        
        
        initGC();
        isForm();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Persetujuan", "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "J.K.", "Tgl.Lahir", 
            "Tanggal", "Pengobatan Kepada", "Nilai Kepercayaan", "Nama Penanggung Jawab", 
            "Umur P.J.", "Nomor KTP P.J.", "J.K. P.J.", "Nomor Telp/HP", "Bertindak Untuk", 
            "Alamat P.J.", "Pekerjaan P.J.", "NIP", "Nama Petugas", 
            "Privasi Akses", "Privasi Khusus", "Informasi Biaya", "Keluarga 1", "Keluarga 2", "Jenis Pembiayaan", 
            "Alasan Tolak BPJS", "Alasan Tolak BPJS Kerja", "Alasan Tolak Jasa Raharja", 
            "Asuransi", "No.Asuransi", "No.BPJS", "Hak Kelas", "Pilih Kelas", "Alasan Naik Kelas", "Edukasi PJ", "Edukasi RS", "Saksi 2"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 38; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(105);
            else if (i == 1) column.setPreferredWidth(105);
            else if (i == 2) column.setPreferredWidth(70);
            else if (i == 3) column.setPreferredWidth(150);
            else if (i == 4) column.setPreferredWidth(45);
            else if (i == 5) column.setPreferredWidth(25);
            else if (i == 6) column.setPreferredWidth(65);
            else if (i == 7) column.setPreferredWidth(65);
            else if (i == 8) column.setPreferredWidth(105);
            else if (i == 9) column.setPreferredWidth(150);
            else if (i == 10) column.setPreferredWidth(150);
            else if (i == 11) column.setPreferredWidth(55);
            else if (i == 12) column.setPreferredWidth(100);
            else if (i == 13) column.setPreferredWidth(45);
            else if (i == 14) column.setPreferredWidth(100);
            else if (i == 15) column.setPreferredWidth(88);
            else if (i == 16) column.setPreferredWidth(150);
            else if (i == 17) column.setPreferredWidth(100);
            else if (i == 18) column.setPreferredWidth(90);
            else if (i == 19) column.setPreferredWidth(150);
            else if (i == 20) column.setPreferredWidth(100);
            else if (i == 21) column.setPreferredWidth(100);
            else if (i == 22) column.setPreferredWidth(150);
            else if (i == 29) column.setPreferredWidth(100);
            else if (i == 29) column.setPreferredWidth(100);
            else if (i == 29) column.setPreferredWidth(100);
            else if (i == 29) column.setPreferredWidth(80);
            else if (i == 29) column.setPreferredWidth(120);
            else if (i == 29) column.setPreferredWidth(150);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte) 20).getKata(NIP));
        NoSurat.setDocument(new batasInput((byte) 20).getKata(NoSurat));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        NamaPJ.setDocument(new batasInput((byte) 50).getKata(NamaPJ));
        NoKTP.setDocument(new batasInput((byte) 20).getKata(NoKTP));
        UmurPJ.setDocument(new batasInput((byte) 3).getKata(UmurPJ));
        NoTelp.setDocument(new batasInput((byte) 30).getKata(NoTelp));
        Alamat.setDocument(new batasInput((int) 100).getKata(Alamat));
        Pekerjaan.setDocument(new batasInput((byte) 50).getKata(Pekerjaan));

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
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                NIP.requestFocus();
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

        ChkAccor.setSelected(false);
        isPhoto();

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JK = new widget.TextBox();
        Umur = new widget.TextBox();
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
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        NamaPJ = new widget.TextBox();
        jLabel9 = new widget.Label();
        JKPJ = new widget.ComboBox();
        jLabel10 = new widget.Label();
        BertindakAtas = new widget.ComboBox();
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        jLabel44 = new widget.Label();
        UmurPJ = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel14 = new widget.Label();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel15 = new widget.Label();
        NoKTP = new widget.TextBox();
        NoTelp = new widget.TextBox();
        jLabel20 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel22 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel23 = new widget.Label();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        
        jLabelSaksi2 = new widget.Label();
        Saksi2 = new widget.TextBox();
        btnAmbilSaksi2 = new widget.Button();
        
        jLabelSaksi2.setText("Saksi 2 :");
        jLabelSaksi2.setName("jLabelSaksi2");
        FormInput.add(jLabelSaksi2);
        jLabelSaksi2.setBounds(0, 630, 130, 23);
        
        Saksi2.setHighlighter(null);
        Saksi2.setName("Saksi2");
        Saksi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, EdukasiRS, BtnSimpan);
            }
        });
        FormInput.add(Saksi2);

        Saksi2.setBounds(134, 630, 300, 23);
        
        btnAmbilSaksi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); 
        btnAmbilSaksi2.setMnemonic('S');
        btnAmbilSaksi2.setText("TTD Saksi 2");
        btnAmbilSaksi2.setToolTipText("Klik untuk TTD Saksi 2");
        btnAmbilSaksi2.setName("btnAmbilSaksi2"); 
        btnAmbilSaksi2.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAmbilSaksi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tbObat.getSelectedRow() > -1) {
                    freehand.DlgTTDSaksi2 dlg = new freehand.DlgTTDSaksi2(null, true);
                    dlg.setNoSurat(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    dlg.setVisible(true);
                    if (!dlg.getNamaFile().equals("")) {
                        panggilPhoto();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
                }
            }
        });
        

        btnAmbilGambar = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Persetujuan Umum ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(215, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 710));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 255, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 111, 23);

        jLabel8.setText("Bertindak Untuk/Atas Nama :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(700, 120, 160, 23);

        NamaPJ.setName("NamaPJ"); // NOI18N
        NamaPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPJKeyPressed(evt);
            }
        });
        FormInput.add(NamaPJ);
        NamaPJ.setBounds(89, 90, 260, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(740, 90, 40, 23);

        JKPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JKPJ.setName("JKPJ"); // NOI18N
        JKPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKPJKeyPressed(evt);
            }
        });
        FormInput.add(JKPJ);
        JKPJ.setBounds(790, 90, 110, 23);

        jLabel10.setText("Nama :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 90, 85, 23);

        BertindakAtas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Ibu", "Saudara", "Keponakan", "Pasien", "Lainnya" }));
        BertindakAtas.setName("BertindakAtas"); // NOI18N
        BertindakAtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BertindakAtasKeyPressed(evt);
            }
        });
        FormInput.add(BertindakAtas);
        BertindakAtas.setBounds(865, 120, 75, 23);
        KetBertindak = new widget.TextBox();
        KetBertindak.setName("KetBertindak");
        KetBertindak.setEnabled(false);
        KetBertindak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, BertindakAtas, Alamat);
            }
        });
        FormInput.add(KetBertindak);
        KetBertindak.setBounds(945, 120, 100, 23);

        BertindakAtas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(BertindakAtas.getSelectedItem().toString().equals("Lainnya")) {
                    KetBertindak.setEnabled(true);
                    KetBertindak.requestFocus();
                } else {
                    KetBertindak.setEnabled(false);
                    KetBertindak.setText("");
                }
            }
        });

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(584, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(648, 10, 85, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(170, 40, 55, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(229, 40, 100, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(331, 40, 157, 23);

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
        btnPetugas.setBounds(490, 40, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        jLabel44.setText("Umur (Tahun) :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(353, 90, 90, 23);

        UmurPJ.setName("UmurPJ"); // NOI18N
        UmurPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurPJKeyPressed(evt);
            }
        });
        FormInput.add(UmurPJ);
        UmurPJ.setBounds(447, 90, 47, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 90, 23);

        jLabel14.setText("Penanggung Jawab Pasien :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 154, 23);

        jLabel3.setText("No.Persetujuan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(520, 40, 90, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(614, 40, 119, 23);

        jLabel15.setText("Nomor KTP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(509, 90, 70, 23);

        NoKTP.setName("NoKTP"); // NOI18N
        NoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKTPKeyPressed(evt);
            }
        });
        FormInput.add(NoKTP);
        NoKTP.setBounds(583, 90, 150, 23);

        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(130, 120, 160, 23);

        jLabel20.setText("Nomor Telp/HP :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(30, 120, 96, 23);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(340, 120, 135, 23);

        jLabel22.setText("Alamat :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(270, 120, 70, 23);

        Pekerjaan.setHighlighter(null);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(600, 120, 105, 23);

        jLabel23.setText("Pekerjaan :");
        jLabel23.setName("jLabel23"); // NOI18N
        
        EdukasiPJ = new widget.TextBox();
        EdukasiRS = new widget.TextBox();
        jLabelEdukasiPJ = new widget.Label();
        jLabelEdukasiRS = new widget.Label();
        
        jLabelEdukasiPJ.setText("Edukasi Keluarga :");
        jLabelEdukasiPJ.setName("jLabelEdukasiPJ");
        FormInput.add(jLabelEdukasiPJ);
        jLabelEdukasiPJ.setBounds(0, 600, 105, 23);

        EdukasiPJ.setName("EdukasiPJ");
        EdukasiPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiPJKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiPJ);
        EdukasiPJ.setBounds(110, 600, 370, 23);

        jLabelEdukasiRS.setText("Edukasi RS :");
        jLabelEdukasiRS.setName("jLabelEdukasiRS");
        FormInput.add(jLabelEdukasiRS);
        jLabelEdukasiRS.setBounds(490, 600, 80, 23);

        EdukasiRS.setName("EdukasiRS");
        EdukasiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiRSKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiRS);
        EdukasiRS.setBounds(580, 600, 370, 23);

        FormInput.add(jLabel23);
        jLabel23.setBounds(535, 120, 65, 23);

                scrollInput.setName("scrollInput");
        scrollInput.setOpaque(true);
        scrollInput.setViewportView(FormInput);
        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Persetujuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 113));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 120));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("TTD Digital");
        btnAmbil.setToolTipText("Klik untuk TTD Digital");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
                FormPass3.add(btnAmbilSaksi2);
FormPass3.add(btnAmbil);

        btnAmbilGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilGambar.setMnemonic('G');
        btnAmbilGambar.setText("Ambil Gambar");
        btnAmbilGambar.setToolTipText("Klik untuk Ambil Gambar Webcam");
        btnAmbilGambar.setName("btnAmbilGambar"); // NOI18N
        btnAmbilGambar.setPreferredSize(new java.awt.Dimension(130, 30));
        btnAmbilGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilGambarActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbilGambar);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NamaPJ.getText().trim().equals("")) {
            Valid.textKosong(NamaPJ, "Nama Penanggung Jawab");
        } else if (UmurPJ.getText().trim().equals("")) {
            Valid.textKosong(UmurPJ, "Umur");
        } else if (NoTelp.getText().trim().equals("")) {
            Valid.textKosong(NoTelp, "Nomor Telp");
        } else if (NoKTP.getText().trim().equals("")) {
            Valid.textKosong(NoKTP, "Nomor KTP");
        } else if (Alamat.getText().trim().equals("")) {
            Valid.textKosong(Alamat, "Alamat Penanggung Jawab");
        } else if (Pekerjaan.getText().trim().equals("")) {
            Valid.textKosong(Pekerjaan, "Pekerjaan Penanggung Jawab");
        } else if (NamaPetugas.getText().trim().equals("")) {
            Valid.textKosong(NamaPetugas, "Petugas");
        } else if (NoSurat.getText().trim().equals("")) {
            Valid.textKosong(NoSurat, "No.Pernyataan");
        } else {
            if (Sequel.menyimpantf("surat_persetujuan_umum (no_surat, no_rawat, tanggal, pengobatan_kepada, nilai_kepercayaan, nama_pj, umur_pj, no_ktppj, jkpj, bertindak_atas, no_telp, nip, alamat_pj, pekerjaan_pj, privasi_akses, privasi_khusus, informasi_biaya, keluarga_1, keluarga_2, jenis_pembiayaan, alasan_tolak_bpjs, alasan_tolak_bpjs_kerja, alasan_tolak_jasa_raharja, asuransi_swasta, no_kartu_asuransi, no_jkn_jasa_raharja, hak_kelas, pilihan_kamar, alasan_naik_kelas, edukasi_pj, edukasi_rs, saksi_2)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 32, new String[]{
                NoSurat.getText(), TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), "-", "", NamaPJ.getText(), UmurPJ.getText(), NoKTP.getText(),
                JKPJ.getSelectedItem().toString().substring(0, 1), (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), NoTelp.getText(), NIP.getText(),
                Alamat.getText(), Pekerjaan.getText(), 
                Privasi1.getSelectedItem().toString(), Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString(), Keluarga1.getText(), Keluarga2.getText(), rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), AlasanUmum.getText(), AlasanUmumKerja.getText(), AlasanUmumJasa.getText(), 
                NamaAsuransi.getText(), NoKartuAsuransi.getText(), (rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), 
                HakKelas.getText(), PilihanKelas.getSelectedItem().toString(), AlasanNaik.getText(), EdukasiPJ.getText(), EdukasiRS.getText(), Saksi2.getText()
            }) == true) {
                tabMode.addRow(new String[]{
                    NoSurat.getText(), TNoRw.getText(), TNoRM.getText(), TPasien.getText(), Umur.getText(), JK.getText(), LahirPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem() + ""), "-", "", NamaPJ.getText(), UmurPJ.getText(), NoKTP.getText(), JKPJ.getSelectedItem().toString().substring(0, 1),
                    NoTelp.getText(), (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), 
                    Alamat.getText(), Pekerjaan.getText(),
                    NIP.getText(), NamaPetugas.getText(),
                    Privasi1.getSelectedItem().toString(), Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString(), Keluarga1.getText(), Keluarga2.getText(), rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), AlasanUmum.getText(), AlasanUmumKerja.getText(), AlasanUmumJasa.getText(), 
                    NamaAsuransi.getText(), NoKartuAsuransi.getText(), (rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), 
                    HakKelas.getText(), PilihanKelas.getSelectedItem().toString(), AlasanNaik.getText(), EdukasiPJ.getText(), EdukasiRS.getText(), Saksi2.getText()
                });
                LCount.setText("" + tabMode.getRowCount());
                Keluarga1.setText("");
        Keluarga2.setText("");
        emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, JKPJ, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
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
            hapus();
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
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NamaPJ.getText().trim().equals("")) {
            Valid.textKosong(NamaPJ, "Nama Penanggung Jawab");
        } else if (UmurPJ.getText().trim().equals("")) {
            Valid.textKosong(UmurPJ, "Umur");
        } else if (NoTelp.getText().trim().equals("")) {
            Valid.textKosong(NoTelp, "Nomor Telp");
        } else if (NoKTP.getText().trim().equals("")) {
            Valid.textKosong(NoKTP, "Nomor KTP");
        } else if (NamaPetugas.getText().trim().equals("")) {
            Valid.textKosong(NamaPetugas, "Petugas");
        } else if (NoSurat.getText().trim().equals("")) {
            Valid.textKosong(NoSurat, "No.Pernyataan");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                ganti();
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
        petugas.dispose();
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
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));

            if (TCari.getText().trim().equals("")) {
                Valid.MyReportqry("rptDataPersetujuanUmum.jasper", "report", "::[ Data Persetujuan Umum ]::",
                        "select surat_persetujuan_umum.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"
                        + "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,"
                        + "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,"
                        + "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.no_telp,surat_persetujuan_umum.nip,"
                        + "petugas.nama from surat_persetujuan_umum inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on surat_persetujuan_umum.nip=petugas.nip where "
                        + "surat_persetujuan_umum.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' order by surat_persetujuan_umum.tanggal", param);
            } else {
                Valid.MyReportqry("rptDataPersetujuanUmum.jasper", "report", "::[ Data Persetujuan Umum ]::",
                        "select surat_persetujuan_umum.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"
                        + "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,"
                        + "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,"
                        + "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.no_telp,surat_persetujuan_umum.nip,"
                        + "petugas.nama from surat_persetujuan_umum inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on surat_persetujuan_umum.nip=petugas.nip where "
                        + "surat_persetujuan_umum.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and "
                        + "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                        + "surat_persetujuan_umum.no_telp like '%" + TCari.getText().trim() + "%' or surat_persetujuan_umum.nama_pj like '%" + TCari.getText().trim() + "%' or "
                        + "surat_persetujuan_umum.nip like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') "
                        + "order by surat_persetujuan_umum.tanggal", param);
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
            tampil();
            TCari.setText("");
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
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt, Tanggal, NamaPJ);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void BertindakAtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BertindakAtasKeyPressed
        Valid.pindah(evt, NoTelp, BtnSimpan);
    }//GEN-LAST:event_BertindakAtasKeyPressed

    private void JKPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKPJKeyPressed
        Valid.pindah(evt, NoKTP, NoTelp);
    }//GEN-LAST:event_JKPJKeyPressed

    private void NamaPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPJKeyPressed
        Valid.pindah(evt, NoSurat, UmurPJ);
    }//GEN-LAST:event_NamaPJKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt, TCari, NoSurat);
    }//GEN-LAST:event_TanggalKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt, btnPetugas, NamaPJ);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void NoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKTPKeyPressed
        Valid.pindah(evt, UmurPJ, JKPJ);
    }//GEN-LAST:event_NoKTPKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt, JKPJ, BertindakAtas);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            isPhoto();
            panggilPhoto();
        } else {
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null, "Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            String noSurat = tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString();

            freehand.DlgTTDPersetujuanUmum dlg = new freehand.DlgTTDPersetujuanUmum(null, true);
            dlg.setNoSurat(noSurat);
            dlg.setVisible(true);

            if (!dlg.getNamaFile().equals("")) {
                panggilPhoto();
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void btnAmbilGambarActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbObat.getSelectedRow() > -1) {
            if (com.github.sarxos.webcam.Webcam.getDefault() == null) {
                JOptionPane.showMessageDialog(rootPane, "Webcam tidak ditemukan! Pastikan webcam terhubung.", "Error Webcam", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String noSurat = tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString();
            String noRawat = tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString();

            simrskhanza.DlgPersetujuanWebcam dlg = new simrskhanza.DlgPersetujuanWebcam(
                null, 
                true, 
                Sequel, 
                koneksi, 
                noSurat, 
                noRawat
            );
            
            dlg.setSize(800, 700); 
            dlg.setLocationRelativeTo(internalFrame1);
            dlg.setVisible(true);

            if (dlg.isSaved()) {
                panggilPhoto();
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            panggilPhoto();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void UmurPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurPJKeyPressed
        Valid.pindah(evt, NamaPJ, NoKTP);
    }//GEN-LAST:event_UmurPJKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            
            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            param.put("finger", finger);
            
            String lokasifile = Sequel.cariIsi("select photo from surat_persetujuan_umum where no_surat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            if (lokasifile == null || lokasifile.equals("") || lokasifile.equals("null") || lokasifile.equals("-")) {
                param.put("photo", ""); 
            } else {
                java.io.File foto = new java.io.File("pernyataanumum/pages/upload/" + lokasifile);
                if (foto.exists()) {
                    param.put("photo", foto.getAbsolutePath());
                } else {
                    param.put("photo", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + lokasifile);
                }
            }

            
            String lokasisaksi2 = Sequel.cariIsi("select photo_saksi_2 from surat_persetujuan_umum where no_surat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            if (lokasisaksi2 == null || lokasisaksi2.equals("") || lokasisaksi2.equals("null") || lokasisaksi2.equals("-")) {
                param.put("photo_saksi_2", ""); 
            } else {
                java.io.File fotoSaksi = new java.io.File("pernyataanumum/pages/upload/" + lokasisaksi2);
                if (fotoSaksi.exists()) {
                    param.put("photo_saksi_2", fotoSaksi.getAbsolutePath());
                } else {
                    param.put("photo_saksi_2", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + lokasisaksi2);
                }
            }

            
            String ttdPetugas = "";
            String nipLogin = akses.getkode();
            if (nipLogin != null) {
                nipLogin = nipLogin.toLowerCase();
            } else {
                nipLogin = "";
            }
            String namaLogin = Sequel.cariIsi("select nama from petugas where nip=?", akses.getkode());
            if (namaLogin != null) {
                namaLogin = namaLogin.toLowerCase();
            } else {
                namaLogin = "";
            }
            
            String picName = "";
            if (nipLogin.contains("wini") || namaLogin.contains("wini")) {
                picName = "/picture/ttdWini.png";
            } else if (nipLogin.contains("sandi") || namaLogin.contains("sandi")) {
                picName = "/picture/ttdSandi.png";
            } else if (nipLogin.contains("ridwan") || namaLogin.contains("ridwan")) {
                picName = "/picture/ttdRidwan.png";
            } else if (nipLogin.contains("inda") || namaLogin.contains("inda")) {
                picName = "/picture/ttdInda.png";
            } else if (nipLogin.contains("lusi") || namaLogin.contains("lusi")) {
                picName = "/picture/ttdLusi.png";
            }
            
            if (!picName.equals("")) {
                java.net.URL urlTTD = SuratPersetujuanUmum.class.getResource(picName);
                if (urlTTD != null) {
                    ttdPetugas = urlTTD.toString();
                }
            }
            param.put("ttd_petugas", ttdPetugas);

            Valid.MyReportqry("rptSuratPersetujuanUmum.jasper", "report", "::[ Surat Persetujuan Umum ]::",
                    "select surat_persetujuan_umum.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"
                    + "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,"
                    + "pasien.tmp_lahir, pasien.no_tlp, pasien.agama, pasien.pnd, pasien.pekerjaan, " // <-- Tambahan Data Pasien
                    + "concat(trim(pasien.alamat),', ',trim(kelurahan.nm_kel),', ',trim(kecamatan.nm_kec),', ',trim(kabupaten.nm_kab),', ',trim(propinsi.nm_prop)) as alamat,"
                    + "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,"
                    + "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.no_telp,"
                    + "surat_persetujuan_umum.alamat_pj, surat_persetujuan_umum.pekerjaan_pj, " // <-- Tambahan Data PJ
                    + "surat_persetujuan_umum.privasi_akses, surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.informasi_biaya, surat_persetujuan_umum.keluarga_1, surat_persetujuan_umum.keluarga_2, surat_persetujuan_umum.jenis_pembiayaan, surat_persetujuan_umum.alasan_tolak_bpjs, surat_persetujuan_umum.alasan_tolak_bpjs_kerja, surat_persetujuan_umum.alasan_tolak_jasa_raharja, "
                    + "surat_persetujuan_umum.asuransi_swasta, surat_persetujuan_umum.no_kartu_asuransi, surat_persetujuan_umum.no_jkn_jasa_raharja, "
                    + "surat_persetujuan_umum.hak_kelas, surat_persetujuan_umum.pilihan_kamar, surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.saksi_2, "
                    + "surat_persetujuan_umum.nip,petugas.nama,penjab.png_jawab "
                    + "from surat_persetujuan_umum inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join petugas on surat_persetujuan_umum.nip=petugas.nip "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "
                    + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "
                    + "where surat_persetujuan_umum.no_surat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatKeyPressed

    
    private void EdukasiPJKeyPressed(java.awt.event.KeyEvent evt) {                                      
        Valid.pindah(evt, Pekerjaan, EdukasiRS);
    }                                     

    private void EdukasiRSKeyPressed(java.awt.event.KeyEvent evt) {                                      
        Valid.pindah(evt, EdukasiPJ, BtnSimpan);
    } 
private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PekerjaanKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratPersetujuanUmum dialog = new SuratPersetujuanUmum(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;
    private widget.Label jLabelNoJKNJasa;
    private widget.TextBox NoJKNJasa;
    private widget.Label jLabelNoJKNKerja;
    private widget.TextBox NoJKNKerja;

    private widget.TextBox Alamat;
    private widget.ComboBox BertindakAtas;
    private widget.TextBox KetBertindak;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox JK;
    private widget.ComboBox JKPJ;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NIP;
    private widget.TextBox NamaPJ;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoKTP;
    private widget.TextBox NoSurat;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane scrollInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox EdukasiPJ;
    private widget.TextBox EdukasiRS;
    private widget.TextBox Saksi2;
    private widget.Label jLabelSaksi2;
    private widget.Button btnAmbilSaksi2;
    private widget.Label jLabelEdukasiPJ;
    private widget.Label jLabelEdukasiRS;

    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.TextBox UmurPJ;
    private widget.Button btnAmbil;
    private widget.Button btnAmbilGambar;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
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
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel44;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select surat_persetujuan_umum.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"
                        + "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,"
                        + "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,"
                        + "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.alamat_pj,surat_persetujuan_umum.pekerjaan_pj,"
                        + "surat_persetujuan_umum.no_telp,surat_persetujuan_umum.nip,"
                        + "petugas.nama, "
                        + "surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.informasi_biaya,surat_persetujuan_umum.keluarga_1,surat_persetujuan_umum.keluarga_2,surat_persetujuan_umum.jenis_pembiayaan,surat_persetujuan_umum.alasan_tolak_bpjs,surat_persetujuan_umum.alasan_tolak_bpjs_kerja,surat_persetujuan_umum.alasan_tolak_jasa_raharja,surat_persetujuan_umum.asuransi_swasta,"
                        + "surat_persetujuan_umum.no_kartu_asuransi,surat_persetujuan_umum.no_jkn_jasa_raharja,surat_persetujuan_umum.hak_kelas,surat_persetujuan_umum.pilihan_kamar,"
                        + "surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.saksi_2 "
                        + "from surat_persetujuan_umum inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on surat_persetujuan_umum.nip=petugas.nip where "
                        + "surat_persetujuan_umum.tanggal between ? and ? order by surat_persetujuan_umum.tanggal");
            } else {
                ps = koneksi.prepareStatement(
                        "select surat_persetujuan_umum.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"
                        + "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,"
                        + "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,"
                        + "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.alamat_pj,surat_persetujuan_umum.pekerjaan_pj,"
                        + "surat_persetujuan_umum.no_telp,surat_persetujuan_umum.nip,"
                        + "petugas.nama, "
                        + "surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.informasi_biaya,surat_persetujuan_umum.keluarga_1,surat_persetujuan_umum.keluarga_2,surat_persetujuan_umum.jenis_pembiayaan,surat_persetujuan_umum.alasan_tolak_bpjs,surat_persetujuan_umum.alasan_tolak_bpjs_kerja,surat_persetujuan_umum.alasan_tolak_jasa_raharja,surat_persetujuan_umum.asuransi_swasta,"
                        + "surat_persetujuan_umum.no_kartu_asuransi,surat_persetujuan_umum.no_jkn_jasa_raharja,surat_persetujuan_umum.hak_kelas,surat_persetujuan_umum.pilihan_kamar,"
                        + "surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.saksi_2 "
                        + "from surat_persetujuan_umum inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on surat_persetujuan_umum.nip=petugas.nip where "
                        + "surat_persetujuan_umum.tanggal between ? and ? and "
                        + "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                        + "surat_persetujuan_umum.no_telp like ? or surat_persetujuan_umum.nama_pj like ? or "
                        + "surat_persetujuan_umum.nip like ? or petugas.nama like ?) "
                        + "order by surat_persetujuan_umum.tanggal");
            }

            try {
                if (TCari.getText().toString().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                    ps.setString(8, "%" + TCari.getText() + "%");
                    ps.setString(9, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("umurdaftar") + " " + rs.getString("sttsumur"), rs.getString("jk"), rs.getString("tgl_lahir"),
                        rs.getString("tanggal"), rs.getString("pengobatan_kepada"), rs.getString("nilai_kepercayaan"), rs.getString("nama_pj"),
                        rs.getString("umur_pj"), rs.getString("no_ktppj"), rs.getString("jkpj").replaceAll("L", "Laki-laki").replaceAll("P", "Perempuan"), rs.getString("no_telp"), rs.getString("bertindak_atas"),
                        rs.getString("alamat_pj"), rs.getString("pekerjaan_pj"), rs.getString("nip"), rs.getString("nama"),
                        rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("informasi_biaya"), rs.getString("keluarga_1"), rs.getString("keluarga_2"), rs.getString("jenis_pembiayaan"), rs.getString("alasan_tolak_bpjs"), rs.getString("alasan_tolak_bpjs_kerja"), rs.getString("alasan_tolak_jasa_raharja"), rs.getString("asuransi_swasta"),
                        rs.getString("no_kartu_asuransi"), rs.getString("no_jkn_jasa_raharja"), rs.getString("hak_kelas"), rs.getString("pilihan_kamar"), rs.getString("alasan_naik_kelas"), rs.getString("edukasi_pj"), rs.getString("edukasi_rs"), rs.getString("saksi_2")
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

    public void emptTeks() {
        NamaPJ.setText("");
        UmurPJ.setText("");
        BertindakAtas.setSelectedIndex(0);
        KetBertindak.setText("");
        KetBertindak.setEnabled(false);
        JKPJ.setSelectedIndex(0);
        NoTelp.setText("");
        NoKTP.setText("");
        Alamat.setText("");
        Pekerjaan.setText("");
        
        Privasi1.setSelectedIndex(0);
        Privasi2.setSelectedIndex(0);
        rbUmum.setSelected(true);
        AlasanUmum.setText("");
        NamaAsuransi.setText("");
        NoKartuAsuransi.setText("");
        NoKartuBPJS.setText("");
        NoJKNJasa.setText("");
        NoJKNKerja.setText("");
        HakKelas.setText("");
        PilihanKelas.setSelectedIndex(0);
        AlasanNaik.setText("");
        EdukasiPJ.setText("");
        EdukasiRS.setText("");
        Saksi2.setText("");
        
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_persetujuan_umum.no_surat,3),signed)),0) from surat_persetujuan_umum where surat_persetujuan_umum.tanggal='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "' ",
                "PSU" + Tanggal.getSelectedItem().toString().substring(6, 10) + Tanggal.getSelectedItem().toString().substring(3, 5) + Tanggal.getSelectedItem().toString().substring(0, 2), 3, NoSurat);
        NamaPJ.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            NamaPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            UmurPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            NoKTP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            JKPJ.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().replaceAll("L", "Laki-laki").replaceAll("P", "Perempuan"));
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            String bertindak = tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString();
            boolean found = false;
            for(int i=0; i<BertindakAtas.getItemCount(); i++){
                if(BertindakAtas.getItemAt(i).toString().equals(bertindak)){
                    found = true;
                    break;
                }
            }
            if(found){
                BertindakAtas.setSelectedItem(bertindak);
                KetBertindak.setText("");
                KetBertindak.setEnabled(false);
            } else {
                BertindakAtas.setSelectedItem("Lainnya");
                KetBertindak.setText(bertindak);
                KetBertindak.setEnabled(true);
            }
            
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Pekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            
            Privasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            Privasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            InfoBiaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            Keluarga1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            Keluarga2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString();
            if(jp.equals("Umum")) rbUmum.setSelected(true);
            else if(jp.equals("Asuransi Swasta")) rbAsuransi.setSelected(true);
            else if(jp.equals("Jasa Raharja")) rbJasaRaharja.setSelected(true);
            else if(jp.equals("BPJS Ketenagakerjaan")) rbBPJSKerja.setSelected(true);
            else rbBPJSKes.setSelected(true);

            
            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            AlasanUmumKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            AlasanUmumJasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            NamaAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            NoKartuAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            String noJknData = tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString();
            if (jp.equals("Jasa Raharja")) {
                NoJKNJasa.setText(noJknData);
            } else if (jp.equals("BPJS Ketenagakerjaan")) {
                NoJKNKerja.setText(noJknData);
            } else if (jp.equals("BPJS Kesehatan")) {
                NoKartuBPJS.setText(noJknData);
            }
            HakKelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            PilihanKelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
                EdukasiPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
                EdukasiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
                Saksi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());

            
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,pasien.no_tlp,"
                    + "reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_peserta from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    LahirPasien.setText(rs.getString("tgl_lahir"));
                    NoTelpPasien.setText(rs.getString("no_tlp"));
                    // Umur PJ tidak di-autopopulate
                    NoKartuBPJS.setText("-");
                    NoJKNJasa.setText("-");
                    NoJKNKerja.setText("-");
                    HakKelas.setText(Sequel.cariIsi("select bridging_sep.klsrawat from bridging_sep where bridging_sep.no_rawat=?", TNoRw.getText()));
                    if (HakKelas.getText().trim().equals("")) {
                        HakKelas.setText("-");
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

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, Math.max(150, (int)(this.getHeight() * 0.55))));
            scrollInput.setVisible(true);
            ChkInput.setVisible(true);
            PanelInput.revalidate();
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            scrollInput.setVisible(false);
            ChkInput.setVisible(true);
            PanelInput.revalidate();
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getsurat_persetujuan_umum());
        BtnHapus.setEnabled(akses.getsurat_persetujuan_umum());
        BtnEdit.setEnabled(akses.getsurat_persetujuan_umum());
        BtnPrint.setEnabled(akses.getsurat_persetujuan_umum());
        if (akses.getjml2() >= 1) {
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if (NamaPetugas.getText().equals("")) {
                NIP.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }
    }

    private void ganti() {
        Sequel.mengedit("pasien", "no_rkm_medis='" + TNoRM.getText() + "'", "no_tlp='" + NoTelpPasien.getText() + "'");
            if (Sequel.mengedittf("surat_persetujuan_umum", "no_surat=?", "no_rawat=?,tanggal=?,nama_pj=?,umur_pj=?,no_ktppj=?,jkpj=?,bertindak_atas=?,no_telp=?,nip=?,alamat_pj=?,pekerjaan_pj=?,privasi_akses=?,privasi_khusus=?,informasi_biaya=?,keluarga_1=?,keluarga_2=?,jenis_pembiayaan=?,alasan_tolak_bpjs=?,alasan_tolak_bpjs_kerja=?,alasan_tolak_jasa_raharja=?,asuransi_swasta=?,no_kartu_asuransi=?,no_jkn_jasa_raharja=?,hak_kelas=?,pilihan_kamar=?,alasan_naik_kelas=?, edukasi_pj=?, edukasi_rs=?, saksi_2=?", 30, new String[]{
            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), NamaPJ.getText(), UmurPJ.getText(), NoKTP.getText(),
            JKPJ.getSelectedItem().toString().substring(0, 1), (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), NoTelp.getText(), NIP.getText(),
            Alamat.getText(), Pekerjaan.getText(), 
            Privasi1.getSelectedItem().toString(), Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString(), Keluarga1.getText(), Keluarga2.getText(), rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), AlasanUmum.getText(), AlasanUmumKerja.getText(), AlasanUmumJasa.getText(), 
            NamaAsuransi.getText(), NoKartuAsuransi.getText(), (rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), 
            HakKelas.getText(), PilihanKelas.getSelectedItem().toString(), AlasanNaik.getText(), EdukasiPJ.getText(), EdukasiRS.getText(), Saksi2.getText(),
            NoSurat.getText()
        }) == true) {
            tbObat.setValueAt(NoSurat.getText(), tbObat.getSelectedRow(), 0);
            tbObat.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 1);
            tbObat.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 2);
            tbObat.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 3);
            tbObat.setValueAt(Umur.getText(), tbObat.getSelectedRow(), 4);
            tbObat.setValueAt(JK.getText(), tbObat.getSelectedRow(), 5);
            tbObat.setValueAt(LahirPasien.getText(), tbObat.getSelectedRow(), 6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem() + ""), tbObat.getSelectedRow(), 7);
            tbObat.setValueAt(NamaPJ.getText(), tbObat.getSelectedRow(), 10);
            tbObat.setValueAt(UmurPJ.getText(), tbObat.getSelectedRow(), 11);
            tbObat.setValueAt(NoKTP.getText(), tbObat.getSelectedRow(), 12);
            tbObat.setValueAt(JKPJ.getSelectedItem().toString().substring(0, 1), tbObat.getSelectedRow(), 13);
            tbObat.setValueAt(NoTelp.getText(), tbObat.getSelectedRow(), 14);
            tbObat.setValueAt((BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), tbObat.getSelectedRow(), 15);
            tbObat.setValueAt(Alamat.getText(), tbObat.getSelectedRow(), 16); 
            tbObat.setValueAt(Pekerjaan.getText(), tbObat.getSelectedRow(), 17);
            
            tbObat.setValueAt(NIP.getText(), tbObat.getSelectedRow(), 18);
            tbObat.setValueAt(NamaPetugas.getText(), tbObat.getSelectedRow(), 19);
            tbObat.setValueAt(Privasi1.getSelectedItem().toString(), tbObat.getSelectedRow(), 20);
            tbObat.setValueAt(Privasi2.getSelectedItem().toString(), tbObat.getSelectedRow(), 21);
            tbObat.setValueAt(InfoBiaya.getSelectedItem().toString(), tbObat.getSelectedRow(), 22);
            tbObat.setValueAt(Keluarga1.getText(), tbObat.getSelectedRow(), 23);
            tbObat.setValueAt(Keluarga2.getText(), tbObat.getSelectedRow(), 24);
            tbObat.setValueAt(rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), tbObat.getSelectedRow(), 25);
            tbObat.setValueAt(AlasanUmum.getText(), tbObat.getSelectedRow(), 26);
            tbObat.setValueAt(AlasanUmumKerja.getText(), tbObat.getSelectedRow(), 27);
            tbObat.setValueAt(AlasanUmumJasa.getText(), tbObat.getSelectedRow(), 28);
            tbObat.setValueAt(NamaAsuransi.getText(), tbObat.getSelectedRow(), 29);
            tbObat.setValueAt(NoKartuAsuransi.getText(), tbObat.getSelectedRow(), 30);
            tbObat.setValueAt((rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), tbObat.getSelectedRow(), 31);
            tbObat.setValueAt(HakKelas.getText(), tbObat.getSelectedRow(), 32);
            tbObat.setValueAt(PilihanKelas.getSelectedItem().toString(), tbObat.getSelectedRow(), 33);
            tbObat.setValueAt(AlasanNaik.getText(), tbObat.getSelectedRow(), 34);
            tbObat.setValueAt(EdukasiPJ.getText(), tbObat.getSelectedRow(), 35);
            tbObat.setValueAt(EdukasiRS.getText(), tbObat.getSelectedRow(), 36);
            tbObat.setValueAt(Saksi2.getText(), tbObat.getSelectedRow(), 37);
            emptTeks();
        }
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from surat_persetujuan_umum where no_surat=?", 1, new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void isPhoto() {
        if (ChkAccor.isSelected() == true) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480, HEIGHT));
            FormPhoto.setVisible(true);
            ChkAccor.setVisible(true);
        } else if (ChkAccor.isSelected() == false) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
            FormPhoto.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if (FormPhoto.isVisible() == true) {
            String fotoWebcam = "";
            String fotoTtd = "";
            
            // Get Webcam Photo
            try {
                ps = koneksi.prepareStatement("select photo from surat_persetujuan_umum_pembuat_pernyataan where no_surat=?");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        fotoWebcam = rs.getString("photo");
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notif panggilPhoto Webcam: " + e);
            }
            
            // Get TTD Digital
            try {
                ps = koneksi.prepareStatement("select photo from surat_persetujuan_umum where no_surat=?");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        fotoTtd = rs.getString("photo");
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notif panggilPhoto TTD: " + e);
            }

            StringBuilder html = new StringBuilder();
            html.append("<html><body><center>");
            
            boolean adaWebcam = (fotoWebcam != null && !fotoWebcam.equals("") && !fotoWebcam.equals("-") && !fotoWebcam.equals("null"));
            boolean adaTtd = (fotoTtd != null && !fotoTtd.equals("") && !fotoTtd.equals("-") && !fotoTtd.equals("null"));

            String fotoTtdSaksi = "";
            try {
                ps = koneksi.prepareStatement("select photo_saksi_2 from surat_persetujuan_umum where no_surat=?");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        fotoTtdSaksi = rs.getString("photo_saksi_2");
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }
            } catch (Exception e) { }
            
            boolean adaTtdSaksi = (fotoTtdSaksi != null && !fotoTtdSaksi.equals("") && !fotoTtdSaksi.equals("-") && !fotoTtdSaksi.equals("null"));

            
            if (!adaWebcam && !adaTtd) {
                html.append("<br><br><font face='tahoma' size='2' color='#434343'>Kosong</font>");
            } else {
                if (adaWebcam) {
                    html.append("<p><b>Foto Dokumentasi:</b></p>");
                    File fileLokal = new File("pernyataanumum" + File.separator + "pages" + File.separator + "upload" + File.separator + fotoWebcam);
                    if (fileLokal.exists()) {
                        html.append("<img src='").append(fileLokal.toURI().toString()).append("' width='500' height='500'/>");
                    } else {
                        String serverUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + fotoWebcam;
                        html.append("<img src='").append(serverUrl).append("' width='500' height='500'/>");
                    }
                }
                if (adaTtd) {
                    html.append("<br><br><p><b>Tanda Tangan Digital:</b></p>");
                    File fileLokal = new File("pernyataanumum" + File.separator + "pages" + File.separator + "upload" + File.separator + fotoTtd);
                    if (fileLokal.exists()) {
                        html.append("<img src='").append(fileLokal.toURI().toString()).append("' width='300' height='300'/>");
                    } else {
                        String serverUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + fotoTtd;
                        html.append("<img src='").append(serverUrl).append("' width='300' height='300'/>");
                    }
                }
            }
            
                if (adaTtdSaksi) {
                    html.append("<br><br><p><b>Tanda Tangan Saksi 2:</b></p>");
                    File fileLokalSaksi = new File("pernyataanumum" + File.separator + "pages" + File.separator + "upload" + File.separator + fotoTtdSaksi);
                    if (fileLokalSaksi.exists()) {
                        html.append("<img src='").append(fileLokalSaksi.toURI().toString()).append("' width='300' height='300'/>");
                    } else {
                        String serverUrlSaksi = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + fotoTtdSaksi;
                        html.append("<img src='").append(serverUrlSaksi).append("' width='300' height='300'/>");
                    }
                }

            html.append("</center></body></html>");
            LoadHTML2.setText(html.toString());
        }
    }
    
    private widget.Label jLabelPrivasi1;
    private widget.ComboBox Privasi1;
    private widget.Label jLabelPrivasi2;
    private widget.ComboBox Privasi2;
    private widget.Label jLabelInfoBiaya;
    private widget.ComboBox InfoBiaya;
    private widget.Label jLabelKeluarga1;
    private widget.TextBox Keluarga1;
    private widget.Label jLabelKeluarga2;
    private widget.TextBox Keluarga2;
    private widget.Label jLabelNoTelpPasien;
    private widget.TextBox NoTelpPasien;
    private widget.CekBox ChkAlamatPJ;
    private widget.Label jLabelAlasanUmum;
    private widget.Label jLabelAlasanUmumKerja;
    private widget.TextBox AlasanUmumKerja;
    private widget.Label jLabelAlasanUmumJasa;
    private widget.TextBox AlasanUmumJasa;
    private widget.TextBox AlasanUmum;
    private widget.Label jLabelAsuransi;
    private widget.TextBox NamaAsuransi;
    private widget.Label jLabelNoKartuAsuransi;
    private widget.TextBox NoKartuAsuransi;
    private widget.Label jLabelBPJS;
    private widget.TextBox NoKartuBPJS;
    private widget.Label jLabelHakKelas;
    private widget.TextBox HakKelas;
    private widget.Label jLabelPilihanKelas;
    private widget.ComboBox PilihanKelas;
    private widget.Label jLabelAlasanNaik;
    private widget.TextBox AlasanNaik;

    private void initGC() {
        jLabelPrivasi1 = new widget.Label();
        jLabelPrivasi1.setText("Privasi Akses :");
        jLabelPrivasi1.setBounds(0, 180, 85, 23);
        Privasi1 = new widget.ComboBox();
        Privasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengijinkan", "Tidak Mengijinkan" }));
        Privasi1.setBounds(89, 180, 140, 23);

        jLabelPrivasi2 = new widget.Label();
        jLabelPrivasi2.setText("Privasi Khusus :");
        jLabelPrivasi2.setBounds(240, 180, 90, 23);
        Privasi2 = new widget.ComboBox();
        Privasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Menginginkan", "Menginginkan" }));
        Privasi2.setBounds(335, 180, 140, 23);
        
        jLabelInfoBiaya = new widget.Label();
        jLabelInfoBiaya.setText("Info Biaya :");
        jLabelInfoBiaya.setBounds(490, 180, 90, 23);
        InfoBiaya = new widget.ComboBox();
        InfoBiaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Rawat Inap", "Instalasi Gawat Darurat" }));
        InfoBiaya.setBounds(583, 180, 180, 23);
        
        jLabelKeluarga1 = new widget.Label();
        jLabelKeluarga1.setText("Keluarga 1 :");
        jLabelKeluarga1.setBounds(765, 180, 70, 23);
        Keluarga1 = new widget.TextBox();
        Keluarga1.setBounds(840, 180, 140, 23);
        
        jLabelKeluarga2 = new widget.Label();
        jLabelKeluarga2.setText("Keluarga 2 :");
        jLabelKeluarga2.setBounds(765, 210, 70, 23);
        Keluarga2 = new widget.TextBox();
        Keluarga2.setBounds(840, 210, 140, 23);
        
        jLabelNoTelpPasien = new widget.Label();
        jLabelNoTelpPasien.setText("Telp.Ps :");
        jLabelNoTelpPasien.setBounds(735, 40, 60, 23);

        NoTelpPasien = new widget.TextBox();
        NoTelpPasien.setBounds(795, 40, 130, 23);
        
        ChkAlamatPJ = new widget.CekBox();
        ChkAlamatPJ.setText("Sama");
        ChkAlamatPJ.setBounds(475, 120, 60, 23);
        ChkAlamatPJ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(ChkAlamatPJ.isSelected()){
                    Alamat.setText(Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText()));
                }else{
                    Alamat.setText("");
                }
            }
        });


        jLabelAlasanUmum = new widget.Label();
        jLabelAlasanUmum.setText("Alasan Tolak BPJS Kes:");
        jLabelAlasanUmum.setBounds(30, 240, 140, 23);
        
        jLabelAlasanUmumKerja = new widget.Label();
        jLabelAlasanUmumKerja.setText("Alasan Tolak BPJS Kerja:");
        jLabelAlasanUmumKerja.setBounds(30, 270, 140, 23);
        AlasanUmumKerja = new widget.TextBox();
        AlasanUmumKerja.setBounds(175, 270, 300, 23);
        
        jLabelAlasanUmumJasa = new widget.Label();
        jLabelAlasanUmumJasa.setText("Alasan Tolak Jasa Raharja:");
        jLabelAlasanUmumJasa.setBounds(30, 300, 140, 23);
        AlasanUmumJasa = new widget.TextBox();
        AlasanUmumJasa.setBounds(175, 300, 300, 23);
        
        FormInput.add(jLabelAlasanUmumKerja);
        FormInput.add(AlasanUmumKerja);
        FormInput.add(jLabelAlasanUmumJasa);
        FormInput.add(AlasanUmumJasa);
        AlasanUmum = new widget.TextBox();
        AlasanUmum.setBounds(175, 240, 300, 23);

        jLabelAsuransi = new widget.Label();
        jLabelAsuransi.setText("Asuransi Swasta :");
        jLabelAsuransi.setBounds(30, 360, 100, 23);
        NamaAsuransi = new widget.TextBox();
        NamaAsuransi.setBounds(135, 360, 220, 23);

        jLabelNoKartuAsuransi = new widget.Label();
        jLabelNoKartuAsuransi.setText("No. Kartu :");
        jLabelNoKartuAsuransi.setBounds(370, 360, 60, 23);
        NoKartuAsuransi = new widget.TextBox();
        NoKartuAsuransi.setBounds(435, 360, 160, 23);

        jLabelBPJS = new widget.Label();
        jLabelBPJS.setText("No. JKN / BPJS Kes :");
        jLabelBPJS.setBounds(30, 540, 130, 23);
        NoKartuBPJS = new widget.TextBox();
        NoKartuBPJS.setBounds(165, 540, 180, 23);

        jLabelHakKelas = new widget.Label();
        jLabelHakKelas.setText("Hak Kelas :");
        jLabelHakKelas.setBounds(360, 540, 70, 23);
        HakKelas = new widget.TextBox();
        HakKelas.setBounds(435, 540, 120, 23);

        jLabelPilihanKelas = new widget.Label();
        jLabelPilihanKelas.setText("Pilihan Kamar :");
        jLabelPilihanKelas.setBounds(30, 570, 90, 23);
        PilihanKelas = new widget.ComboBox();
        PilihanKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai Hak Kelas Rawat", "Naik Kelas Hak Rawat" }));
        PilihanKelas.setBounds(125, 570, 180, 23);

        jLabelAlasanNaik = new widget.Label();
        jLabelAlasanNaik.setText("Alasan Naik Kelas :");
        jLabelAlasanNaik.setBounds(320, 570, 110, 23);
        AlasanNaik = new widget.TextBox();
        AlasanNaik.setBounds(435, 570, 250, 23);

        bgPembiayaan = new javax.swing.ButtonGroup();
        rbUmum = new widget.RadioButton();
        rbAsuransi = new widget.RadioButton();
        rbJasaRaharja = new widget.RadioButton();
        rbBPJSKerja = new widget.RadioButton();
        rbBPJSKes = new widget.RadioButton();

        rbUmum.setText("Umum");
        rbAsuransi.setText("Asuransi Swasta");
        rbJasaRaharja.setText("Jasa Raharja");
        rbBPJSKerja.setText("BPJS Ketenagakerj.");
        rbBPJSKes.setText("BPJS Kesehatan");
        
        rbUmum.setFocusable(false);
        rbAsuransi.setFocusable(false);
        rbJasaRaharja.setFocusable(false);
        rbBPJSKerja.setFocusable(false);
        rbBPJSKes.setFocusable(false);

        bgPembiayaan.add(rbUmum);
        bgPembiayaan.add(rbAsuransi);
        bgPembiayaan.add(rbJasaRaharja);
        bgPembiayaan.add(rbBPJSKerja);
        bgPembiayaan.add(rbBPJSKes);

        FormInput.add(rbUmum);
        FormInput.add(rbAsuransi);
        FormInput.add(rbJasaRaharja);
        FormInput.add(rbBPJSKerja);
        FormInput.add(rbBPJSKes);

        rbUmum.setBounds(0, 210, 150, 23);
        rbAsuransi.setBounds(0, 330, 150, 23);
        rbJasaRaharja.setBounds(0, 390, 150, 23);
        rbBPJSKerja.setBounds(0, 450, 200, 23);
        rbBPJSKes.setBounds(0, 510, 150, 23);
        
        jLabelNoJKNJasa = new widget.Label();
        jLabelNoJKNJasa.setText("No. JKN / Jasa Raharja :");
        jLabelNoJKNJasa.setBounds(30, 420, 140, 23);
        NoJKNJasa = new widget.TextBox();
        NoJKNJasa.setBounds(175, 420, 180, 23);
        NoJKNJasa.setName("NoJKNJasa"); // NOI18N
        
        jLabelNoJKNKerja = new widget.Label();
        jLabelNoJKNKerja.setText("No. JKN / BPJS Kerja :");
        jLabelNoJKNKerja.setBounds(30, 480, 140, 23);
        NoJKNKerja = new widget.TextBox();
        NoJKNKerja.setBounds(175, 480, 180, 23);
        NoJKNKerja.setName("NoJKNKerja"); // NOI18N

        FormInput.add(jLabelNoJKNJasa);
        FormInput.add(NoJKNJasa);
        FormInput.add(jLabelNoJKNKerja);
        FormInput.add(NoJKNKerja);
        
        java.awt.event.ItemListener rbListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    AlasanUmum.setEditable(rbUmum.isSelected());
                    AlasanUmumKerja.setEditable(rbUmum.isSelected());
                    AlasanUmumJasa.setEditable(rbUmum.isSelected());
                    NamaAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuAsuransi.setEditable(rbAsuransi.isSelected());
                    NoJKNJasa.setEditable(rbJasaRaharja.isSelected());
                    NoJKNKerja.setEditable(rbBPJSKerja.isSelected());
                    NoKartuBPJS.setEditable(rbBPJSKes.isSelected());
                    HakKelas.setEditable(rbBPJSKes.isSelected());
                    
                    if(!rbUmum.isSelected()) {
                        AlasanUmum.setText("-");
                        AlasanUmumKerja.setText("-");
                        AlasanUmumJasa.setText("-");
                    }
                    if(!rbAsuransi.isSelected()) {
                        NamaAsuransi.setText("-");
                        NoKartuAsuransi.setText("-");
                    }
                    if(!rbJasaRaharja.isSelected()) NoJKNJasa.setText("-");
                    if(!rbBPJSKerja.isSelected()) NoJKNKerja.setText("-");
                    if(!rbBPJSKes.isSelected()) {
                        NoKartuBPJS.setText("-");
                        HakKelas.setText("-");
                    } else if (NoKartuBPJS.getText().equals("-") || NoKartuBPJS.getText().equals("")) {
                        NoKartuBPJS.setText(Sequel.cariIsi("select pasien.no_peserta from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?", TNoRw.getText()));
                        HakKelas.setText(Sequel.cariIsi("select bridging_sep.klsrawat from bridging_sep where bridging_sep.no_rawat=?", TNoRw.getText()));
                        if (HakKelas.getText().trim().equals("")) {
                            HakKelas.setText("-");
                        }
                        if (NoKartuBPJS.getText().trim().equals("")) {
                            NoKartuBPJS.setText("-");
                        }
                    }
                }
            }
        };
        rbUmum.addItemListener(rbListener);
        rbAsuransi.addItemListener(rbListener);
        rbJasaRaharja.addItemListener(rbListener);
        rbBPJSKerja.addItemListener(rbListener);
        rbBPJSKes.addItemListener(rbListener);
        
        rbUmum.setSelected(true);

        FormInput.add(jLabelPrivasi1);
        FormInput.add(Privasi1);
        FormInput.add(jLabelPrivasi2);
        FormInput.add(Privasi2);
        FormInput.add(jLabelInfoBiaya);
        FormInput.add(InfoBiaya);
        FormInput.add(jLabelKeluarga1);
        FormInput.add(Keluarga1);
        FormInput.add(jLabelKeluarga2);
        FormInput.add(Keluarga2);
        FormInput.add(jLabelNoTelpPasien);
        FormInput.add(NoTelpPasien);
        FormInput.add(ChkAlamatPJ);
        FormInput.add(jLabelAlasanUmum);
        FormInput.add(AlasanUmum);
        FormInput.add(jLabelAsuransi);
        FormInput.add(NamaAsuransi);
        FormInput.add(jLabelNoKartuAsuransi);
        FormInput.add(NoKartuAsuransi);
        FormInput.add(jLabelBPJS);
        FormInput.add(NoKartuBPJS);
        FormInput.add(jLabelHakKelas);
        FormInput.add(HakKelas);
        FormInput.add(jLabelPilihanKelas);
        FormInput.add(PilihanKelas);
        FormInput.add(jLabelAlasanNaik);
        FormInput.add(AlasanNaik);

        FormInput.setPreferredSize(new java.awt.Dimension(100, 710));
        
    }
}
