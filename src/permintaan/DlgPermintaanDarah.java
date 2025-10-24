/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Ikhsan.java
 *
 * Created on Augt 22, 2024, 10:25:16 PM
 */
package permintaan;

import fungsi.TabelPemeriksaanPMI;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.akses;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import keuangan.DlgKamar;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import simrskhanza.DlgCrossMatching;
import simrskhanza.DlgPasien;
import fungsi.TabelPemeriksaanPMI;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author dosen
 */
public class DlgPermintaanDarah extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgPasien pasien = new DlgPasien(null, false);
    private String terlaksana = "", kdsps = "", kmr = "", key = "", status = "";
    private int x = 0;
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);

    /**
     * Creates new form DlgJadwal
     *
     * @param parent
     * @param modal
     */
    public DlgPermintaanDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);

        Object[] row = {"No Rawat", "No RM", "Nama Pasien", "No Booking", "Ruangan", "Tanggal Lahir", "Tanggal Permintaan",
            "Diagnosis Klinis", "Alasan Transfusi", "Jenis Bayar", "Hb", "Golongan Darah", "Rhesus",
            "Transfusi Sebelumnya", "Waktu Transfusi", "Reaksi Transfusi", "Gejala Reaksi", "Coombs Test",
            "Tempat Coombs Test", "Kapan Coombs", "Hasil Coombs", "Kehamilan", "Abortus", "HDN", "Keadaan",
            "Jenis Darah", "Jumlah Kantong", "DPJP", "Petugas", "Nama Petugas", "Status", "Tanggal di Update"
        };

        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbJadwal.setModel(tabMode);
        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Array untuk lebar kolom
        int[] columnWidths = {100, 110, 65, 220, 90, 110, 100, 70, 75, 70, 100, 60, 60, 128, 150, 70, 70, 128, 100, 100, 100, 150, 150, 150, 150, 150, 150, 150, 150};

// Mengatur lebar kolom menggunakan loop
        TableColumnModel columnModel = tbJadwal.getColumnModel();
        for (int i = 0; i < columnWidths.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        tbJadwal.setDefaultRenderer(Object.class, new TabelPemeriksaanPMI());

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TRuangan.setDocument(new batasInput((int) 25).getKata(TRuangan));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
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

        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPermintaanDarah")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TNoRw.setText("-");
                        TNoRm.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 1).toString());
                        Tpasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 2).toString());
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

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPermintaanDarah")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
//untuk jalankan bisa pilih petugas
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPermintaanDarah")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        Nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        TPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        Nip.requestFocus();
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnHasilPemeriksaan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel39 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        BtnPrint = new widget.Button();
        panelGlass9 = new widget.panelisi();
        R1 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        Tpasien = new widget.TextBox();
        btnPasien = new widget.Button();
        TNoRw = new widget.TextBox();
        TNoRm = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoBoking = new widget.TextBox();
        jLabel8 = new widget.Label();
        tglPermintaan = new widget.Tanggal();
        jLabel12 = new widget.Label();
        lblJenisBayar = new widget.Label();
        TRuangan = new widget.TextBox();
        jLabel10 = new widget.Label();
        DiagKlinis = new widget.TextBox();
        Gejala = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel14 = new widget.Label();
        Thb = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        cmbTransfusi = new widget.ComboBox();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        cmbReaksi = new widget.ComboBox();
        jLabel26 = new widget.Label();
        TKapan = new widget.TextBox();
        JumlahKantong = new widget.TextBox();
        Keadaan = new widget.ComboBox();
        JenisDarah = new widget.ComboBox();
        cmbGolDarah = new widget.ComboBox();
        jLabel24 = new widget.Label();
        TPetugas = new widget.TextBox();
        jLabel37 = new widget.Label();
        cmbRhesus = new widget.ComboBox();
        jLabel15 = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        scrollPane8 = new widget.ScrollPane();
        TAlasan = new widget.TextArea();
        jLabel38 = new widget.Label();
        BtnSeekPegawai3 = new widget.Button();
        Nip = new widget.TextBox();
        lblTglLahir = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel40 = new widget.Label();
        TDPJP = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel27 = new widget.Label();
        Abortus = new widget.ComboBox();
        jLabel28 = new widget.Label();
        Kehamilan = new widget.TextBox();
        jLabel29 = new widget.Label();
        CoombsTest = new widget.ComboBox();
        jLabel30 = new widget.Label();
        Dimana = new widget.TextBox();
        jLabel16 = new widget.Label();
        HasilCoombs = new widget.TextBox();
        jLabel31 = new widget.Label();
        CoombsTestKapan = new widget.TextBox();
        jLabel32 = new widget.Label();
        Hdn = new widget.ComboBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel42 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnHasilPemeriksaan.setBackground(new java.awt.Color(255, 255, 254));
        MnHasilPemeriksaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHasilPemeriksaan.setForeground(new java.awt.Color(50, 50, 50));
        MnHasilPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHasilPemeriksaan.setText("Hasil Crossmatching");
        MnHasilPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHasilPemeriksaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHasilPemeriksaan.setName("MnHasilPemeriksaan"); // NOI18N
        MnHasilPemeriksaan.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHasilPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHasilPemeriksaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHasilPemeriksaan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 3), "::[ Permintaan Darah PMI ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(420, 402));

        tbJadwal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJadwal.setComponentPopupMenu(jPopupMenu1);
        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJadwalMouseClicked(evt);
            }
        });
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 9, 9));

        BtnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(90, 26));
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

        BtnBatal.setForeground(new java.awt.Color(0, 0, 0));
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(80, 26));
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

        BtnHapus.setForeground(new java.awt.Color(0, 0, 0));
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(90, 26));
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

        BtnEdit.setForeground(new java.awt.Color(0, 0, 0));
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(90, 26));
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

        BtnKeluar.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(90, 26));
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

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setForeground(new java.awt.Color(0, 0, 0));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);

        jLabel39.setText("Status :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel39);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Belum", "Proses", "Sudah" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(cmbStatus);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        R1.setBackground(new java.awt.Color(255, 102, 102));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 102)));
        buttonGroup1.add(R1);
        R1.setForeground(new java.awt.Color(0, 0, 0));
        R1.setSelected(true);
        R1.setText("Belum");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(75, 23));
        R1.setRequestFocusEnabled(false);
        R1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R1ActionPerformed(evt);
            }
        });
        panelGlass9.add(R1);

        R4.setBackground(new java.awt.Color(255, 255, 0));
        R4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R4);
        R4.setForeground(new java.awt.Color(0, 0, 0));
        R4.setText("Proses");
        R4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R4.setName("R4"); // NOI18N
        R4.setPreferredSize(new java.awt.Dimension(75, 23));
        R4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R4ActionPerformed(evt);
            }
        });
        panelGlass9.add(R4);

        R2.setBackground(new java.awt.Color(0, 204, 102));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Sudah");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(75, 23));
        R2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R2ActionPerformed(evt);
            }
        });
        panelGlass9.add(R2);

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Tgl. Order:");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelGlass9.add(DTPCari1);

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari2ItemStateChanged(evt);
            }
        });
        DTPCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTPCari2ActionPerformed(evt);
            }
        });
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelGlass9.add(DTPCari2);

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setForeground(new java.awt.Color(0, 0, 0));
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setForeground(new java.awt.Color(0, 0, 0));
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setText("Tampilkan Data");
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(130, 23));
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

        BtnAll.setForeground(new java.awt.Color(0, 0, 0));
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('4');
        BtnAll.setText("Semua Data");
        BtnAll.setToolTipText("Alt+4");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(120, 23));
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

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 370));
        panelBiasa1.setLayout(null);

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Pasien :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelBiasa1.add(jLabel3);
        jLabel3.setBounds(0, 10, 100, 23);

        Tpasien.setEditable(false);
        Tpasien.setForeground(new java.awt.Color(0, 0, 0));
        Tpasien.setName("Tpasien"); // NOI18N
        Tpasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TpasienActionPerformed(evt);
            }
        });
        panelBiasa1.add(Tpasien);
        Tpasien.setBounds(310, 10, 290, 23);

        btnPasien.setForeground(new java.awt.Color(0, 0, 0));
        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("ALt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelBiasa1.add(btnPasien);
        btnPasien.setBounds(600, 10, 28, 23);

        TNoRw.setEditable(false);
        TNoRw.setForeground(new java.awt.Color(0, 0, 0));
        TNoRw.setName("TNoRw"); // NOI18N
        panelBiasa1.add(TNoRw);
        TNoRw.setBounds(104, 10, 122, 23);

        TNoRm.setEditable(false);
        TNoRm.setForeground(new java.awt.Color(0, 0, 0));
        TNoRm.setName("TNoRm"); // NOI18N
        panelBiasa1.add(TNoRm);
        TNoRm.setBounds(230, 10, 70, 23);

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("No. Booking :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelBiasa1.add(jLabel5);
        jLabel5.setBounds(0, 38, 100, 23);

        TNoBoking.setForeground(new java.awt.Color(0, 0, 0));
        TNoBoking.setName("TNoBoking"); // NOI18N
        panelBiasa1.add(TNoBoking);
        TNoBoking.setBounds(104, 38, 120, 23);

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Diagnosa Klinis :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(220, 70, 100, 23);

        tglPermintaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2024" }));
        tglPermintaan.setDisplayFormat("dd-MM-yyyy");
        tglPermintaan.setName("tglPermintaan"); // NOI18N
        tglPermintaan.setOpaque(false);
        tglPermintaan.setPreferredSize(new java.awt.Dimension(90, 23));
        panelBiasa1.add(tglPermintaan);
        tglPermintaan.setBounds(110, 70, 110, 23);

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("g/dL");
        jLabel12.setName("jLabel12"); // NOI18N
        panelBiasa1.add(jLabel12);
        jLabel12.setBounds(120, 160, 40, 23);

        lblJenisBayar.setForeground(new java.awt.Color(255, 0, 51));
        lblJenisBayar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJenisBayar.setText("-");
        lblJenisBayar.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblJenisBayar.setName("lblJenisBayar"); // NOI18N
        panelBiasa1.add(lblJenisBayar);
        lblJenisBayar.setBounds(500, 110, 600, 100);

        TRuangan.setForeground(new java.awt.Color(0, 0, 0));
        TRuangan.setName("TRuangan"); // NOI18N
        TRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TRuanganActionPerformed(evt);
            }
        });
        panelBiasa1.add(TRuangan);
        TRuangan.setBounds(300, 40, 180, 23);

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tgl. Permintaan :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(0, 70, 100, 23);

        DiagKlinis.setName("DiagKlinis"); // NOI18N
        DiagKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagKlinisKeyPressed(evt);
            }
        });
        panelBiasa1.add(DiagKlinis);
        DiagKlinis.setBounds(330, 70, 300, 23);

        Gejala.setName("Gejala"); // NOI18N
        Gejala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GejalaKeyPressed(evt);
            }
        });
        panelBiasa1.add(Gejala);
        Gejala.setBounds(320, 220, 320, 23);

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Alasan Tranfusi :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelBiasa1.add(jLabel11);
        jLabel11.setBounds(0, 110, 100, 23);

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Kapan :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelBiasa1.add(jLabel14);
        jLabel14.setBounds(200, 190, 50, 23);

        Thb.setName("Thb"); // NOI18N
        Thb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThbKeyPressed(evt);
            }
        });
        panelBiasa1.add(Thb);
        Thb.setBounds(50, 160, 60, 23);

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("HB :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelBiasa1.add(jLabel17);
        jLabel17.setBounds(10, 160, 30, 23);

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Keadaan :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelBiasa1.add(jLabel18);
        jLabel18.setBounds(-30, 310, 90, 23);

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Petugas:");
        jLabel20.setName("jLabel20"); // NOI18N
        panelBiasa1.add(jLabel20);
        jLabel20.setBounds(250, 340, 60, 23);

        cmbTransfusi.setForeground(new java.awt.Color(0, 0, 0));
        cmbTransfusi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        cmbTransfusi.setName("cmbTransfusi"); // NOI18N
        panelBiasa1.add(cmbTransfusi);
        cmbTransfusi.setBounds(130, 190, 65, 23);

        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Transfusi Sebelumnya :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelBiasa1.add(jLabel22);
        jLabel22.setBounds(0, 190, 120, 23);

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Reaksi Transfusi :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelBiasa1.add(jLabel23);
        jLabel23.setBounds(0, 220, 90, 23);

        cmbReaksi.setForeground(new java.awt.Color(0, 0, 0));
        cmbReaksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak" }));
        cmbReaksi.setName("cmbReaksi"); // NOI18N
        panelBiasa1.add(cmbReaksi);
        cmbReaksi.setBounds(90, 220, 100, 23);

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Perhatian:");
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N
        panelBiasa1.add(jLabel26);
        jLabel26.setBounds(640, 0, 60, 23);

        TKapan.setName("TKapan"); // NOI18N
        TKapan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TKapanActionPerformed(evt);
            }
        });
        TKapan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKapanKeyPressed(evt);
            }
        });
        panelBiasa1.add(TKapan);
        TKapan.setBounds(260, 190, 160, 23);

        JumlahKantong.setName("JumlahKantong"); // NOI18N
        JumlahKantong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKantongKeyPressed(evt);
            }
        });
        panelBiasa1.add(JumlahKantong);
        JumlahKantong.setBounds(460, 310, 70, 23);

        Keadaan.setForeground(new java.awt.Color(0, 0, 0));
        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CITO", "BIASA" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeadaanActionPerformed(evt);
            }
        });
        panelBiasa1.add(Keadaan);
        Keadaan.setBounds(60, 310, 100, 23);

        JenisDarah.setForeground(new java.awt.Color(0, 0, 0));
        JenisDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Whole Blood (WB)", "WB-Baru Max (2-7 Hari)", "WB-Segar (48 Jam)", "Packed Red Cell (PRC)", "Wash Eritrosit (WE)", "Liquid Plasma (LP)", "Fresh Frozen Plasma (FFP)", "Trombosit Biasa (TC)", "Trombosit Aferesis", "Cryoprecipitate (Cryo)", "Lain - lain" }));
        JenisDarah.setName("JenisDarah"); // NOI18N
        panelBiasa1.add(JenisDarah);
        JenisDarah.setBounds(290, 310, 160, 23);

        cmbGolDarah.setForeground(new java.awt.Color(0, 0, 0));
        cmbGolDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "AB", "O" }));
        cmbGolDarah.setName("cmbGolDarah"); // NOI18N
        panelBiasa1.add(cmbGolDarah);
        cmbGolDarah.setBounds(260, 160, 70, 23);

        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Gejala - Gelaja :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelBiasa1.add(jLabel24);
        jLabel24.setBounds(190, 220, 120, 23);

        TPetugas.setName("TPetugas"); // NOI18N
        TPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPetugasKeyPressed(evt);
            }
        });
        panelBiasa1.add(TPetugas);
        TPetugas.setBounds(410, 340, 200, 23);

        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setText("Rhesus :");
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N
        panelBiasa1.add(jLabel37);
        jLabel37.setBounds(340, 160, 70, 23);

        cmbRhesus.setForeground(new java.awt.Color(0, 0, 0));
        cmbRhesus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "(+)", "(-)" }));
        cmbRhesus.setName("cmbRhesus"); // NOI18N
        panelBiasa1.add(cmbRhesus);
        cmbRhesus.setBounds(420, 160, 70, 23);

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Tgl. Lahir :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelBiasa1.add(jLabel15);
        jLabel15.setBounds(480, 40, 60, 23);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2), "::Jenis Bayar::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        panelBiasa1.add(jPanel1);
        jPanel1.setBounds(490, 100, 620, 120);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        TAlasan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAlasan.setColumns(20);
        TAlasan.setRows(5);
        TAlasan.setName("TAlasan"); // NOI18N
        TAlasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlasanKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(TAlasan);

        panelBiasa1.add(scrollPane8);
        scrollPane8.setBounds(110, 100, 340, 50);

        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Kantong");
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N
        panelBiasa1.add(jLabel38);
        jLabel38.setBounds(500, 310, 90, 23);

        BtnSeekPegawai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai3.setMnemonic('4');
        BtnSeekPegawai3.setToolTipText("ALt+4");
        BtnSeekPegawai3.setName("BtnSeekPegawai3"); // NOI18N
        BtnSeekPegawai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai3ActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnSeekPegawai3);
        BtnSeekPegawai3.setBounds(610, 340, 28, 23);

        Nip.setName("Nip"); // NOI18N
        Nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NipKeyPressed(evt);
            }
        });
        panelBiasa1.add(Nip);
        Nip.setBounds(320, 340, 80, 23);

        lblTglLahir.setForeground(new java.awt.Color(0, 0, 0));
        lblTglLahir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTglLahir.setText("-");
        lblTglLahir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTglLahir.setName("lblTglLahir"); // NOI18N
        panelBiasa1.add(lblTglLahir);
        lblTglLahir.setBounds(550, 40, 80, 23);

        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Ruangan :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelBiasa1.add(jLabel25);
        jLabel25.setBounds(230, 40, 60, 23);

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("DPJP :");
        jLabel40.setName("jLabel40"); // NOI18N
        panelBiasa1.add(jLabel40);
        jLabel40.setBounds(-10, 340, 50, 23);

        TDPJP.setName("TDPJP"); // NOI18N
        TDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDPJPActionPerformed(evt);
            }
        });
        TDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDPJPKeyPressed(evt);
            }
        });
        panelBiasa1.add(TDPJP);
        TDPJP.setBounds(50, 340, 190, 23);

        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Jenis Darah :");
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N
        panelBiasa1.add(jLabel41);
        jLabel41.setBounds(170, 310, 110, 23);

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Pernah Abortus:");
        jLabel27.setName("jLabel27"); // NOI18N
        panelBiasa1.add(jLabel27);
        jLabel27.setBounds(150, 280, 90, 23);

        Abortus.setForeground(new java.awt.Color(0, 0, 0));
        Abortus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Pernah" }));
        Abortus.setName("Abortus"); // NOI18N
        panelBiasa1.add(Abortus);
        Abortus.setBounds(250, 280, 65, 23);

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Jumlah Kehamilan:");
        jLabel28.setName("jLabel28"); // NOI18N
        panelBiasa1.add(jLabel28);
        jLabel28.setBounds(-70, 280, 120, 23);

        Kehamilan.setName("Kehamilan"); // NOI18N
        Kehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KehamilanKeyPressed(evt);
            }
        });
        panelBiasa1.add(Kehamilan);
        Kehamilan.setBounds(60, 280, 90, 23);

        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Pernah di periksa Serologi (Coombs Test):");
        jLabel29.setName("jLabel29"); // NOI18N
        panelBiasa1.add(jLabel29);
        jLabel29.setBounds(-90, 250, 230, 23);

        CoombsTest.setForeground(new java.awt.Color(0, 0, 0));
        CoombsTest.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        CoombsTest.setName("CoombsTest"); // NOI18N
        panelBiasa1.add(CoombsTest);
        CoombsTest.setBounds(140, 250, 60, 23);

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Dimana:");
        jLabel30.setName("jLabel30"); // NOI18N
        panelBiasa1.add(jLabel30);
        jLabel30.setBounds(200, 250, 50, 23);

        Dimana.setName("Dimana"); // NOI18N
        Dimana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DimanaKeyPressed(evt);
            }
        });
        panelBiasa1.add(Dimana);
        Dimana.setBounds(250, 250, 120, 23);

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Hasil:");
        jLabel16.setName("jLabel16"); // NOI18N
        panelBiasa1.add(jLabel16);
        jLabel16.setBounds(560, 250, 30, 23);

        HasilCoombs.setName("HasilCoombs"); // NOI18N
        HasilCoombs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HasilCoombsActionPerformed(evt);
            }
        });
        HasilCoombs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilCoombsKeyPressed(evt);
            }
        });
        panelBiasa1.add(HasilCoombs);
        HasilCoombs.setBounds(600, 250, 130, 23);

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Kapan :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelBiasa1.add(jLabel31);
        jLabel31.setBounds(360, 250, 50, 23);

        CoombsTestKapan.setName("CoombsTestKapan"); // NOI18N
        CoombsTestKapan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoombsTestKapanActionPerformed(evt);
            }
        });
        CoombsTestKapan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CoombsTestKapanKeyPressed(evt);
            }
        });
        panelBiasa1.add(CoombsTestKapan);
        CoombsTestKapan.setBounds(420, 250, 140, 23);

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Riwayat Penyakit Hemololik Bayi (HDN) ?");
        jLabel32.setName("jLabel32"); // NOI18N
        panelBiasa1.add(jLabel32);
        jLabel32.setBounds(330, 280, 200, 23);

        Hdn.setForeground(new java.awt.Color(0, 0, 0));
        Hdn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Pernah" }));
        Hdn.setName("Hdn"); // NOI18N
        panelBiasa1.add(Hdn);
        Hdn.setBounds(540, 280, 100, 23);

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText(" dengan identitas pasien yang akan di transfusi. Bila tidak ada ketidak cocokan segera kembalikan");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa1.add(jLabel33);
        jLabel33.setBounds(640, 80, 500, 23);

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Golongan Darah :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa1.add(jLabel34);
        jLabel34.setBounds(150, 160, 100, 23);

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("- Setiap permintaan darah disertai contoh darah beku 5 cc minimal 2 cc");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa1.add(jLabel35);
        jLabel35.setBounds(640, 20, 370, 23);

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("- Nama dan identitas pasien pada formulir dan contoh darahnya harus sama");
        jLabel36.setName("jLabel36"); // NOI18N
        panelBiasa1.add(jLabel36);
        jLabel36.setBounds(640, 40, 370, 23);

        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("- Sebelum transfusi cocokan etiket pada kantong darah dengan labelnya dan sertakan");
        jLabel42.setName("jLabel42"); // NOI18N
        panelBiasa1.add(jLabel42);
        jLabel42.setBounds(640, 60, 430, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (TNoRm.getText().trim().equals("")) {
            Valid.textKosong(TNoRm, "Pasien");
        } else {
            terlaksana = "";
            //kdsps = "";
            autoNomorBooking();
            //kdsps = Sequel.cariIsi("select kd_poli from poliklinik where nm_poli='" + cmbJnsOperasi.getSelectedItem().toString() + "'");
            if (cmbStatus.getSelectedIndex() == 0) {
                terlaksana = "Belum";
            } else if (cmbStatus.getSelectedIndex() == 1) {
                terlaksana = "Sudah";
            } else if (cmbStatus.getSelectedIndex() == 2) { // Perbaiki indeks di sini
                terlaksana = "Proses";
            }
            if (Sequel.menyimpantf("permintaan_darah_pmi", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 25, new String[]{
                TNoRw.getText(), TNoBoking.getText(), Valid.SetTgl(tglPermintaan.getSelectedItem() + ""), DiagKlinis.getText(), TAlasan.getText(),
                Thb.getText(), cmbGolDarah.getSelectedItem().toString(), cmbRhesus.getSelectedItem().toString(), cmbTransfusi.getSelectedItem().toString(),
                TKapan.getText(), cmbReaksi.getSelectedItem().toString(), Gejala.getText(), CoombsTest.getSelectedItem().toString(), Dimana.getText(), CoombsTestKapan.getText(),
                HasilCoombs.getText(), Kehamilan.getText(), Abortus.getSelectedItem().toString(), Hdn.getSelectedItem().toString(), Keadaan.getSelectedItem().toString(),
                JenisDarah.getSelectedItem().toString(), JumlahKantong.getText(), Nip.getText(), "Belum", Sequel.cariIsi("select now()")
            }) == true) {
                tabMode.addRow(new String[]{
                    TNoRw.getText(), TNoRm.getText(), Tpasien.getText(), TNoBoking.getText(), TRuangan.getText(), lblTglLahir.getText(), Valid.SetTgl(tglPermintaan.getSelectedItem() + ""), DiagKlinis.getText(), TAlasan.getText(),
                    lblJenisBayar.getText(), Thb.getText(), cmbGolDarah.getSelectedItem().toString(), cmbRhesus.getSelectedItem().toString(), cmbTransfusi.getSelectedItem().toString(),
                    TKapan.getText(), cmbReaksi.getSelectedItem().toString(), Gejala.getText(), CoombsTest.getSelectedItem().toString(), Dimana.getText(), CoombsTestKapan.getText(),
                    HasilCoombs.getText(), Kehamilan.getText(), Abortus.getSelectedItem().toString(), Hdn.getSelectedItem().toString(), Keadaan.getSelectedItem().toString(),
                    JenisDarah.getSelectedItem().toString(), JumlahKantong.getText(), TDPJP.getText(), Nip.getText(), TPetugas.getText(), "Belum", Sequel.cariIsi("select now()")
                });
                emptTeks();
                /*
                No Rawat", "No RM", "Nama Pasien", "No Booking", "Ruangan", "Tanggal Lahir", "Tanggal Permintaan",
            "Diagnosis Klinis", "Alasan Transfusi", "Jenis Bayar", "Hb", "Golongan Darah", "Rhesus",
            "Transfusi Sebelumnya", "Waktu Transfusi", "Reaksi Transfusi", "Gejala Reaksi", "Coombs Test",
            "Tempat Coombs Test", "Kapan Coombs", "Hasil Coombs", "Kehamilan", "Abortus", "HDN", "Keadaan",
            "Jenis Darah", "Jumlah Kantong", "DPJP", "Petugas", "Nama Petugas","Status","Tanggal di Update"
                 */
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
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
        if (TNoRm.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu salah satu datanya pada tabel..!!");
            tbJadwal.requestFocus();
        } else if (Sequel.cariInteger("select count(-1) from permintaan_darah_pmi where kd_booking='" + TNoBoking.getText() + "'") == 0) {
            JOptionPane.showMessageDialog(null, "No. booking Permintaan Darah PMI tersebut tidak ada tersimpan pada database..!!");
        } else {
            x = JOptionPane.showConfirmDialog(rootPane, "Yakin data mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {
                if (Sequel.queryu2tf("delete from permintaan_darah_pmi where kd_booking=?", 1, new String[]{TNoBoking.getText()
                }) == true) {
                    tampil();
                    emptTeks();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
                }
            }
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
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nomor Rawat");
        } else if (TNoRm.getText().trim().equals("")) {
            Valid.textKosong(TNoRm, "Nomor Rekam Medis");
        } else if (Sequel.cariInteger("select count(-1) from permintaan_darah_pmi where kd_booking='" + TNoBoking.getText() + "'") == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu salah satu datanya pada tabel..!!");
            tbJadwal.requestFocus();
        } else {
            if (Sequel.mengedittf("permintaan_darah_pmi", "kd_booking=?",
                    "no_rawat=?, kd_booking=?, tanggal=?, diagnosa_klinis=?, alasan=?, HB=?, gol_darah=?, rhesus=?, "
                    + "transfusi_sebelumnya=?, kapan_transfusi=?, reaksi=?, gejala=?, coombs_test=?, dimana=?, kapan_coombs=?, "
                    + "hasil_coombs=?, kehamilan=?, abortus=?, hdn=?, keadaan=?, JenisDarah=?, Kantong=?, nip=?, status=?, last_update=?",
                    25, new String[]{
                        TNoRw.getText(), TNoBoking.getText(), Valid.SetTgl(tglPermintaan.getSelectedItem() + ""), DiagKlinis.getText(), TAlasan.getText(),
                        Thb.getText(), cmbGolDarah.getSelectedItem().toString(), cmbRhesus.getSelectedItem().toString(), cmbTransfusi.getSelectedItem().toString(),
                        TKapan.getText(), cmbReaksi.getSelectedItem().toString(), Gejala.getText(), CoombsTest.getSelectedItem().toString(),
                        Dimana.getText(), CoombsTestKapan.getText(), HasilCoombs.getText(), Kehamilan.getText(), Abortus.getSelectedItem().toString(),
                        Hdn.getSelectedItem().toString(), Keadaan.getSelectedItem().toString(), JenisDarah.getSelectedItem().toString(),
                        JumlahKantong.getText(), Nip.getText(), "Belum", Sequel.cariIsi("select now()"), TNoBoking.getText()
                    })) {
                JOptionPane.showMessageDialog(null, "Berhasil Update Data");
                tampil();
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Update data");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

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
            Valid.pindah(evt, BtnCari, TNoRw);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJadwalMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJadwalMouseClicked

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbJadwalKeyPressed

private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
    akses.setform("DlgPermintaanDarah");
    pasien.emptTeks();
    pasien.isCek();
    pasien.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
    pasien.setLocationRelativeTo(internalFrame1);
    pasien.setVisible(true);
}//GEN-LAST:event_btnPasienActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        Sequel.cariIsi("select nm_perawatan FROM paket_operasi WHERE STATUS='1' ORDER BY kode_paket", cmbPaketOperasi);
//        Sequel.cariIsi("SELECT nm_poli from poliklinik where kd_poli in ('KLT','BDO','BED','132','GIG','GND','GPR','JAN','OBG','MAT','ORT','PAR','SAR','THT') order by nm_poli", cmbJnsOperasi);
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DiagKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagKlinisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagKlinisKeyPressed

    private void GejalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GejalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GejalaKeyPressed

    private void ThbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThbKeyPressed

    private void TKapanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKapanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKapanKeyPressed

    private void JumlahKantongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKantongKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahKantongKeyPressed

    private void KeadaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeadaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanActionPerformed

    private void TRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TRuanganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRuanganActionPerformed

    private void TKapanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TKapanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKapanActionPerformed

    private void TPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPetugasKeyPressed

    private void TAlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlasanKeyPressed
//        Valid.pindah2(evt,T,BtnSimpan);
    }//GEN-LAST:event_TAlasanKeyPressed

    private void BtnSeekPegawai3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPegawai3ActionPerformed
        akses.setform("DlgPermintaanDarah");
        petugas.emptTeks();
        //petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setSize(1046, 341);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPegawai3ActionPerformed

    private void NipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NipKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NipKeyPressed

    private void MnHasilPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHasilPemeriksaanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, tabel masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih salah satu data pasiennya pada tabel...!!!!");
            tbJadwal.requestFocus();
        } else {
            DlgCrossMatching pcross = new DlgCrossMatching(null, false);
            pcross.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
            pcross.setLocationRelativeTo(internalFrame1);
            pcross.emptTeks();
            pcross.isCek();
            pcross.setData(TNoRm.getText(), TNoRw.getText());
            pcross.setVisible(true);
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_MnHasilPemeriksaanActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            if (R1.isSelected() == true) {
                kmr = " pd.status='Belum'";

            } else if (R2.isSelected() == true) {
                kmr = " pd.status='Sudah'";
            }
        }

        key = kmr + " ";
        if (!TCari.getText().equals("")) {
            key = kmr + "and (kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + " concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or "
                    + "bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' or pasien.agama like '%" + TCari.getText().trim() + "%') ";

        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void R1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R1ActionPerformed

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R1.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari2ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari2ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        Valid.pindah(evt, TCari, DTPCari2);
    }//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        Valid.pindah(evt, TCari, DTPCari1);
    }//GEN-LAST:event_DTPCari2KeyPressed

    private void R2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R2ActionPerformed

    private void DTPCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTPCari2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari2ActionPerformed

    private void TDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDPJPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDPJPKeyPressed

    private void TDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDPJPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDPJPActionPerformed

    private void TpasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TpasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TpasienActionPerformed

    private void R4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R4ActionPerformed

    private void KehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KehamilanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KehamilanKeyPressed

    private void DimanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DimanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DimanaKeyPressed

    private void HasilCoombsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HasilCoombsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilCoombsActionPerformed

    private void HasilCoombsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilCoombsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilCoombsKeyPressed

    private void CoombsTestKapanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoombsTestKapanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoombsTestKapanActionPerformed

    private void CoombsTestKapanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CoombsTestKapanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoombsTestKapanKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanDarah dialog = new DlgPermintaanDarah(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abortus;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeekPegawai3;
    private widget.Button BtnSimpan;
    private widget.ComboBox CoombsTest;
    private widget.TextBox CoombsTestKapan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagKlinis;
    private widget.TextBox Dimana;
    private widget.TextBox Gejala;
    private widget.TextBox HasilCoombs;
    private widget.ComboBox Hdn;
    private widget.ComboBox JenisDarah;
    private widget.TextBox JumlahKantong;
    private widget.ComboBox Keadaan;
    private widget.TextBox Kehamilan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnHasilPemeriksaan;
    private widget.TextBox Nip;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R4;
    private widget.ScrollPane Scroll;
    private widget.TextArea TAlasan;
    public widget.TextBox TCari;
    private widget.TextBox TDPJP;
    private widget.TextBox TKapan;
    private widget.TextBox TNoBoking;
    private widget.TextBox TNoRm;
    private widget.TextBox TNoRw;
    private widget.TextBox TPetugas;
    private widget.TextBox TRuangan;
    private widget.TextBox Thb;
    private widget.TextBox Tpasien;
    private widget.Button btnPasien;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbGolDarah;
    private widget.ComboBox cmbReaksi;
    private widget.ComboBox cmbRhesus;
    private widget.ComboBox cmbStatus;
    private widget.ComboBox cmbTransfusi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
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
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label lblJenisBayar;
    private widget.Label lblTglLahir;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbJadwal;
    private widget.Tanggal tglPermintaan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        // Tentukan status berdasarkan pilihan radio button
        if (R1.isSelected()) {
            status = "pd.status = 'Belum'";
        } else if (R2.isSelected()) {
            status = "pd.status = 'Sudah'";
        } else if (R4.isSelected()) {
            status = "pd.status = 'Proses'";
        } else if (DTPCari1.getDate() != null && DTPCari2.getDate() != null) {
            status = "pd.tanggal BETWEEN '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' AND '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "'";
        }

        // Kosongkan tabel sebelum menampilkan data baru
        Valid.tabelKosong(tabMode);

        try {
            // Susun query dengan klausa WHERE yang sudah ditentukan
            ps = koneksi.prepareStatement(
                    "SELECT pd.no_rawat, pd.kd_booking, pd.tanggal, pd.diagnosa_klinis, pd.alasan, pd.HB, pd.gol_darah, "
                    + "pd.rhesus, pd.transfusi_sebelumnya, pd.kapan_transfusi, pd.reaksi, pd.gejala, pd.coombs_test, "
                    + "pd.dimana, pd.kapan_coombs, pd.hasil_coombs, pd.kehamilan, pd.abortus, pd.hdn, pd.keadaan, "
                    + "pd.JenisDarah, pd.Kantong, pd.nip, pd.status, pd.last_update, p.nama, pas.nm_pasien, pas.tgl_lahir, "
                    + "pas.no_rkm_medis, r.kd_pj, pj.png_jawab "
                    + "FROM permintaan_darah_pmi pd "
                    + "INNER JOIN reg_periksa r ON pd.no_rawat = r.no_rawat "
                    + "INNER JOIN pasien pas ON r.no_rkm_medis = pas.no_rkm_medis "
                    + "INNER JOIN petugas p ON pd.nip = p.nip "
                    + "INNER JOIN penjab pj ON r.kd_pj = pj.kd_pj "
                    + "WHERE " + status
                    + (TCari.getText().trim().equals("") ? ""
                    : " AND (pd.no_rawat LIKE ? OR pd.kd_booking LIKE ? OR pas.nm_pasien LIKE ? OR p.nama LIKE ?) ")
                    + "ORDER BY pd.tanggal"
            );

            // Jika ada input pencarian, set parameter
            if (!TCari.getText().trim().equals("")) {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                ps.setString(2, "%" + TCari.getText().trim() + "%");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
            }

            // Eksekusi query dan tampilkan hasilnya
            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                    rs.getString("kd_booking"), "", rs.getString("tgl_lahir"), rs.getString("tanggal"),
                    rs.getString("diagnosa_klinis"), rs.getString("alasan"), rs.getString("png_jawab"), rs.getString("HB"),
                    rs.getString("gol_darah"), rs.getString("rhesus"), rs.getString("transfusi_sebelumnya"),
                    rs.getString("kapan_transfusi"), rs.getString("reaksi"), rs.getString("gejala"),
                    rs.getString("coombs_test"), rs.getString("dimana"), rs.getString("kapan_coombs"),
                    rs.getString("hasil_coombs"), rs.getString("kehamilan"), rs.getString("abortus"),
                    rs.getString("hdn"), rs.getString("keadaan"), rs.getString("JenisDarah"),
                    rs.getString("Kantong"), "", rs.getString("nip"), petugas.tampil3(rs.getString("nip")),
                    rs.getString("status"), rs.getString("last_update")
                });
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            // Menutup ResultSet dan PreparedStatement
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }

        // Update jumlah baris yang ditemukan
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        TRuangan.setText("");
        tglPermintaan.setDate(new Date());
        DTPCari1.setDate(new Date());
        btnPasien.setEnabled(true);
        TCari.setText("");
        //edit//
        TNoBoking.setText("-");
        TNoRw.setText("");
        TNoRm.setText("");
        Tpasien.setText("");
        Valid.SetTgl(DTPCari2, Sequel.cariIsi("select DATE_ADD(now(),interval 60 day)"));
        DiagKlinis.setText("");
        TAlasan.setText("");
        Thb.setText("");
        cmbGolDarah.setSelectedIndex(0);
        cmbRhesus.setSelectedIndex(0);
        cmbTransfusi.setSelectedIndex(0);
        TKapan.setText("");
        cmbReaksi.setSelectedIndex(0);
        Keadaan.setSelectedIndex(0);
        JenisDarah.setSelectedIndex(0);
        JumlahKantong.setText("");
        TPetugas.setText("");
        Gejala.setText("");
        TDPJP.setText("");
        cmbStatus.setSelectedIndex(0);
        autoNomorBooking();
    }

    private void getData() {
        terlaksana = "";
        kdsps = "";

        if (tbJadwal.getSelectedRow() != -1) {
            /*
            "No Rawat", "No RM", "Nama Pasien", "No Booking", "Ruangan", "Tanggal Lahir", "Tanggal Permintaan",
            "Diagnosis Klinis", "Alasan Transfusi", "Jenis Bayar", "Hb", "Golongan Darah", "Rhesus",
            "Transfusi Sebelumnya", "Waktu Transfusi", "Reaksi Transfusi", "Gejala Reaksi", "Coombs Test",
            "Tempat Coombs Test", "Kapan Coombs", "Hasil Coombs", "Kehamilan", "Abortus", "HDN", "Keadaan",
            "Jenis Darah", "Jumlah Kantong", "DPJP", "Petugas", "Nama Petugas", "Status", "Tanggal di Update"          */

            TNoRw.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 0).toString());
            TNoRm.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 1).toString());
            Tpasien.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 2).toString());
            TNoBoking.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 3).toString());
            TRuangan.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 4).toString());

            DiagKlinis.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 6).toString());
            TAlasan.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 7).toString());
            Thb.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 9).toString());
            cmbGolDarah.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 10).toString());
            cmbRhesus.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 11).toString());
            cmbTransfusi.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 12).toString());
            TKapan.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 13).toString());
            cmbReaksi.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 14).toString());
            Gejala.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 15).toString());
            CoombsTest.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 16).toString());
            Dimana.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 17).toString());
            CoombsTestKapan.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 18).toString());
            HasilCoombs.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 19).toString());
            Kehamilan.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 20).toString());
            Abortus.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 21).toString());
            Hdn.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 22).toString());
            Keadaan.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 23).toString());
            JenisDarah.setSelectedItem(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 24).toString());
            JumlahKantong.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 25).toString());
            TDPJP.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 26).toString());
            Nip.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 27).toString());
            TPetugas.setText(tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 28).toString());
            lblJenisBayar.setText(Sequel.cariIsi("SELECT penjab.png_jawab FROM reg_periksa INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj WHERE reg_periksa.no_rawat=?", TNoRw.getText()));
            lblTglLahir.setText(Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?", TNoRm.getText()));
            Valid.SetTgl(tglPermintaan, tbJadwal.getValueAt(tbJadwal.getSelectedRow(), 5).toString());
            if (TNoRw.getText().equals("-")) {
                btnPasien.setEnabled(true);
            } else {
                btnPasien.setEnabled(false);
            }
        }
    }

    public void autoNomorBooking() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_darah_pmi.kd_booking,4),signed)),0) from permintaan_darah_pmi where permintaan_darah_pmi.tanggal='" + Valid.SetTgl(tglPermintaan.getSelectedItem() + "") + "' ",
                tglPermintaan.getSelectedItem().toString().substring(6, 10) + tglPermintaan.getSelectedItem().toString().substring(3, 5) + tglPermintaan.getSelectedItem().toString().substring(0, 2), 4, TNoBoking);
        /*
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"' or resep_obat.tgl_perawatan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"' ",
                DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep);        
         */

    }

    public void setData(String norm, String norw) {
        TNoRw.setText(norw);
        TNoRm.setText(norm);
        Tpasien.setText(Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + norm + "'"));
        lblJenisBayar.setText(Sequel.cariIsi("SELECT penjab.png_jawab FROM reg_periksa INNER JOIN penjab ON "
                + "reg_periksa.kd_pj = penjab.kd_pj WHERE reg_periksa.no_rawat=?", TNoRw.getText()));
        lblTglLahir.setText(Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis='" + norm + "'"));
        TRuangan.setText(Sequel.cariIsi("SELECT CONCAT(b.nm_bangsal, ' - ', k.kd_kamar) AS Kamar FROM kamar "
                + "AS k INNER JOIN bangsal AS b ON k.kd_bangsal = b.kd_bangsal\n"
                + "INNER JOIN kamar_inap AS ki ON k.kd_kamar = ki.kd_kamar "
                + "WHERE  ki.no_rawat = '" + norw + "'"));
        TDPJP.setText(Sequel.cariIsi("SELECT dokter.nm_dokter FROM dpjp_ranap INNER JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter\n"
                + "WHERE no_rawat = '" + norw + "'"));
        btnPasien.setEnabled(false);
        TCari.setText(norw);
        autoNomorBooking();
    }

    public void isCek() {

    }
}
