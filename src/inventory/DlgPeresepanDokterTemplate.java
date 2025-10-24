/*
 * DILARANG MENGGANDAKAN/MENGCOPY/MENYEBARKAN/MEMBAJAK/MENDECOMPILE
 * Software ini dalam bentuk apapun tanpa seijin pembuat software
 * (Khanza.Soft Media).
 */
package inventory;

import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import java.awt.event.KeyListener;

/**
 *
 * @author Deka
 */
public final class DlgPeresepanDokterTemplate extends javax.swing.JDialog {

    private final DefaultTableModel tabModeResep, tabModeDetailResepRacikan, tabModeResepRacikan;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, z = 0;
    private boolean ubah = false;
    private boolean[] pilih;
    private double[] jumlah, harga, beli, stok, kapasitas, p1, p2;
    private String[] kodebarang, namabarang, kodesatuan, kandungan, namajenis, aturan, industri, komposisi;
    public DlgCariAturanPakai aturanpakai = new DlgCariAturanPakai(null, false);
    private DlgCariTemplateResep caritemplate = new DlgCariTemplateResep(null, false);
    private WarnaTable2 warna = new WarnaTable2();
    private WarnaTable2 warna2 = new WarnaTable2();
    private WarnaTable2 warna3 = new WarnaTable2();
    private DlgCariMetodeRacik metoderacik = new DlgCariMetodeRacik(null, false);
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    private String STOKKOSONGRESEP = "no", qrystokkosong = "";

    private widget.Button BtnAll;
    private widget.Button BtnBaru;
    private widget.Button BtnCari;
    private widget.Button BtnCariTemplate;
    private widget.Button BtnHapusRacikan;
    private widget.Button BtnHapusTemplate;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahRacikan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdDokter;
    private widget.TextBox KdTemplate;
    private widget.TextBox NmDokter;
    private widget.TextBox NmTemplate;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel3;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.Table tbDetailResepObatRacikan;
    private widget.Table tbObatResepRacikan;
    private widget.Table tbResep;

    public DlgPeresepanDokterTemplate(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        initComponents();
        this.setLocation(10, 2);
        setSize(656, 250);

        tabModeResep = new DefaultTableModel(null, new Object[]{
            "K", "Jumlah", "Aturan Pakai", "Kode Barang", "Nama Barang", "Satuan",
            "Komposisi", "Harga(Rp)", "Jenis Obat", "I.F.", "H.Beli", "Stok"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return (colIndex == 1) || (colIndex == 2);
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbResep.setModel(tabModeResep);
        tbResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if (i == 0) { column.setMinWidth(0); column.setMaxWidth(0); }
            else if (i == 1) column.setPreferredWidth(45);
            else if (i == 2) column.setPreferredWidth(130);
            else if (i == 3) column.setPreferredWidth(75);
            else if (i == 4) column.setPreferredWidth(240);
            else if (i == 5) column.setPreferredWidth(75);
            else if (i == 6) column.setPreferredWidth(110);
            else if (i == 7) column.setPreferredWidth(85);
            else if (i == 8) column.setPreferredWidth(110);
            else if (i == 9) column.setPreferredWidth(100);
            else if (i == 10) { column.setMinWidth(0); column.setMaxWidth(0); }
            else if (i == 11) column.setPreferredWidth(50);
        }
        warna.kolom = 1;
        tbResep.setDefaultRenderer(Object.class, warna);

        tabModeResepRacikan = new DefaultTableModel(null, new Object[]{
            "No", "Nama Racikan", "Kode Racik", "Metode Racik", "Jml.Dosis", "Aturan Pakai", "Keterangan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                 return !((colIndex == 0) || (colIndex == 2) || (colIndex == 3));
            }
        };
        tbObatResepRacikan.setModel(tabModeResepRacikan);
        tbObatResepRacikan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObatResepRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatResepRacikan.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(25);
            else if (i == 1) column.setPreferredWidth(250);
            else if (i == 2) { column.setMinWidth(0); column.setMaxWidth(0); }
            else if (i == 3) column.setPreferredWidth(100);
            else if (i == 4) column.setPreferredWidth(60);
            else if (i == 5) column.setPreferredWidth(200);
            else if (i == 6) column.setPreferredWidth(250);
        }
        warna2.kolom = 4;
        tbObatResepRacikan.setDefaultRenderer(Object.class, warna2);

        tabModeDetailResepRacikan = new DefaultTableModel(null, new Object[]{
            "No", "Kode Barang", "Nama Barang", "Satuan", "Harga(Rp)", "H.Beli",
            "Jenis Obat", "Stok", "Kps", "P1", "/", "P2", "Kandungan", "Jml", "I.F.",
            "Komposisi"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return (colIndex == 9) || (colIndex == 11) || (colIndex == 12) || (colIndex == 13);
            }
             Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDetailResepObatRacikan.setModel(tabModeDetailResepRacikan);
        tbDetailResepObatRacikan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDetailResepObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbDetailResepObatRacikan.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(25);
            else if (i == 1) column.setPreferredWidth(75);
            else if (i == 2) column.setPreferredWidth(240);
            else if (i == 3) column.setPreferredWidth(45);
            else if (i == 4) column.setPreferredWidth(85);
            else if (i == 5) { column.setMinWidth(0); column.setMaxWidth(0); }
            else if (i == 6) column.setPreferredWidth(110);
            else if (i == 7) column.setPreferredWidth(50);
            else if (i == 8) column.setPreferredWidth(40);
            else if (i == 9) column.setPreferredWidth(25);
            else if (i == 10) { column.setMinWidth(11); column.setMaxWidth(11); }
            else if (i == 11) column.setPreferredWidth(25);
            else if (i == 12) column.setPreferredWidth(70);
            else if (i == 13) column.setPreferredWidth(40);
            else if (i == 14) column.setPreferredWidth(100);
            else if (i == 15) column.setPreferredWidth(150);
        }
        warna3.kolom = 13;
        tbDetailResepObatRacikan.setDefaultRenderer(Object.class, warna3);

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampilObat(); }
                @Override
                public void removeUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampilObat(); }
                @Override
                public void changedUpdate(DocumentEvent e) { if (TCari.getText().length() > 2) tampilObat(); }
            });
        }
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (aturanpakai.getTable().getSelectedRow() != -1) {
                    if (TabRawat.getSelectedIndex() == 0) {
                        tbResep.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tbResep.getSelectedRow(), 2);
                        tbResep.requestFocus();
                    } else if (TabRawat.getSelectedIndex() == 1) {
                        tbObatResepRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tbObatResepRacikan.getSelectedRow(), 5);
                        tbObatResepRacikan.requestFocus();
                    }
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    if (ubah == false) {
                        generateKodeTemplate();
                    }
                }
                KdDokter.requestFocus();
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
        
        caritemplate.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caritemplate.getTable().getSelectedRow()!=-1){                   
                    loadTemplate(caritemplate.getTable().getValueAt(caritemplate.getTable().getSelectedRow(),0).toString());
                }  
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (metoderacik.getTable().getSelectedRow() != -1) {
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(), 1).toString(), tbObatResepRacikan.getSelectedRow(), 2);
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(), 2).toString(), tbObatResepRacikan.getSelectedRow(), 3);
                    tbObatResepRacikan.requestFocus();
                }
            }
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override public void keyReleased(KeyEvent e) {}
        });

        try {
            STOKKOSONGRESEP = koneksiDB.STOKKOSONGRESEP();
        } catch (Exception e) {
            System.out.println("E : " + e);
            STOKKOSONGRESEP = "no";
        }
        
        tbResep.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("tableCellEditor")) {
                    getCekStok();
                }
            }
        });
        
        tbDetailResepObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                 if (evt.getPropertyName().equals("tableCellEditor")) {
                    getCekStokRacikan();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel4 = new widget.Label();
        KdTemplate = new widget.TextBox();
        jLabel5 = new widget.Label();
        NmTemplate = new widget.TextBox();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatResepRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailResepObatRacikan = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        BtnBaru = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnHapusTemplate = new widget.Button();
        BtnCariTemplate = new widget.Button();
        BtnTambahRacikan = new widget.Button();
        BtnHapusRacikan = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Template Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 74));
        FormInput.setLayout(null);

        jLabel3.setText("Dokter Peresep :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 40, 100, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(104, 40, 100, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(206, 40, 290, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(498, 40, 28, 23);

        jLabel4.setText("Kode Template :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 100, 23);

        KdTemplate.setEditable(false);
        KdTemplate.setHighlighter(null);
        KdTemplate.setName("KdTemplate"); // NOI18N
        FormInput.add(KdTemplate);
        KdTemplate.setBounds(104, 10, 150, 23);

        jLabel5.setText("Nama Template :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(260, 10, 100, 23);

        NmTemplate.setHighlighter(null);
        NmTemplate.setName("NmTemplate"); // NOI18N
        NmTemplate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmTemplateKeyPressed(evt);
            }
        });
        FormInput.add(NmTemplate);
        NmTemplate.setBounds(364, 10, 320, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

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

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setName("tbResep"); // NOI18N
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

        TabRawat.addTab("Obat Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 120));

        tbObatResepRacikan.setName("tbObatResepRacikan"); // NOI18N
        tbObatResepRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatResepRacikanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObatResepRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailResepObatRacikan.setAutoCreateRowSorter(true);
        tbDetailResepObatRacikan.setName("tbDetailResepObatRacikan"); // NOI18N
        Scroll2.setViewportView(tbDetailResepObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Obat Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        panelisi3.add(BtnAll);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Beli Luar", "Karyawan", "Utama/BPJS", "Kelas 1", "Kelas 2", "Kelas 3", "VIP", "VVIP" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(110, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        panelisi3.add(Jeniskelas);

        BtnBaru.setMnemonic('B');
        BtnBaru.setText("Baru");
        BtnBaru.setToolTipText("Alt+B");
        BtnBaru.setName("BtnBaru"); // NOI18N
        BtnBaru.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBaruActionPerformed(evt);
            }
        });
        panelisi3.add(BtnBaru);

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
        panelisi3.add(BtnSimpan);

        BtnHapusTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusTemplate.setMnemonic('H');
        BtnHapusTemplate.setText("Hapus Template");
        BtnHapusTemplate.setToolTipText("Alt+H");
        BtnHapusTemplate.setName("BtnHapusTemplate"); // NOI18N
        BtnHapusTemplate.setPreferredSize(new java.awt.Dimension(130, 30));
        BtnHapusTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusTemplateActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapusTemplate);

        BtnCariTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCariTemplate.setMnemonic('C');
        BtnCariTemplate.setText("Cari Template");
        BtnCariTemplate.setToolTipText("Alt+C");
        BtnCariTemplate.setName("BtnCariTemplate"); // NOI18N
        BtnCariTemplate.setPreferredSize(new java.awt.Dimension(130, 30));
        BtnCariTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTemplateActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCariTemplate);

        BtnTambahRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRacikan.setMnemonic('R');
        BtnTambahRacikan.setText("Tambah Racikan");
        BtnTambahRacikan.setToolTipText("Alt+R");
        BtnTambahRacikan.setName("BtnTambahRacikan"); // NOI18N
        BtnTambahRacikan.setPreferredSize(new java.awt.Dimension(140, 30));
        BtnTambahRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRacikanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambahRacikan);
        BtnTambahRacikan.setVisible(false);

        BtnHapusRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnHapusRacikan.setMnemonic('X');
        BtnHapusRacikan.setText("Hapus Racikan");
        BtnHapusRacikan.setToolTipText("Alt+X");
        BtnHapusRacikan.setName("BtnHapusRacikan"); // NOI18N
        BtnHapusRacikan.setPreferredSize(new java.awt.Dimension(140, 30));
        BtnHapusRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusRacikanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapusRacikan);
        BtnHapusRacikan.setVisible(false);

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
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if(TabRawat.getSelectedIndex()==0){
                tbResep.requestFocus();
            }else if(TabRawat.getSelectedIndex()==1){
                tbDetailResepObatRacikan.requestFocus();
            }
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampilObat();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilDetailRacikan();
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BtnCariActionPerformed(evt);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (NmTemplate.getText().trim().isEmpty()) {
            Valid.textKosong(NmTemplate, "Nama Template");
            return;
        }
        if (KdDokter.getText().trim().isEmpty() || NmDokter.getText().trim().isEmpty()) {
            Valid.textKosong(KdDokter, "Dokter Peresep");
            return;
        }
        
        String kd_template_baru = KdTemplate.getText();
        if (kd_template_baru.trim().isEmpty()) {
           JOptionPane.showMessageDialog(null, "Kode Template kosong! Silakan pilih dokter terlebih dahulu untuk membuat template baru.");
           return;
        }

        try {
            Sequel.AutoComitFalse();

            if (ubah) {
                Sequel.meghapus("template_resep_dokter_detail", "kd_template", kd_template_baru);
                Sequel.meghapus("template_resep_dokter_racikan_detail", "kd_template", kd_template_baru);
                Sequel.meghapus("template_resep_dokter_racikan", "kd_template", kd_template_baru);
                Sequel.queryu2("UPDATE template_resep_dokter SET nm_template=?, kd_dokter=? WHERE kd_template=?", 3,
                    new String[]{NmTemplate.getText(), KdDokter.getText(), kd_template_baru});
            } else {
                Sequel.menyimpan("template_resep_dokter", "?,?,?", "Template", 3,
                    new String[]{kd_template_baru, NmTemplate.getText(), KdDokter.getText()});
            }

            for (i = 0; i < tbResep.getRowCount(); i++) {
                if (Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 1))) > 0) {
                    Sequel.menyimpan("template_resep_dokter_detail", "?,?,?,?", "Template Detail Obat", 4, new String[]{
                        kd_template_baru,
                        String.valueOf(tbResep.getValueAt(i, 3)),
                        String.valueOf(tbResep.getValueAt(i, 1)),
                        String.valueOf(tbResep.getValueAt(i, 2))
                    });
                }
            }

            for (i = 0; i < tbObatResepRacikan.getRowCount(); i++) {
                if (!String.valueOf(tbObatResepRacikan.getValueAt(i, 1)).trim().isEmpty()) {
                    String kdRacik = String.valueOf(tbObatResepRacikan.getValueAt(i, 2));
                    if (kdRacik == null || kdRacik.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Metode Racik untuk '" + tbObatResepRacikan.getValueAt(i, 1) + "' belum dipilih. Silakan lengkapi data.");
                        Sequel.RollBack();
                        Sequel.AutoComitTrue();
                        return;
                    }
                    
                    Sequel.menyimpan("template_resep_dokter_racikan", "?,?,?,?,?,?,?", "Template Racikan Header", 7, new String[]{
                        kd_template_baru,
                        String.valueOf(tbObatResepRacikan.getValueAt(i, 0)),
                        String.valueOf(tbObatResepRacikan.getValueAt(i, 1)),
                        kdRacik,
                        "1",
                        String.valueOf(tbObatResepRacikan.getValueAt(i, 5)),
                        String.valueOf(tbObatResepRacikan.getValueAt(i, 6))
                    });
                }
            }

            for (i = 0; i < tbDetailResepObatRacikan.getRowCount(); i++) {
                if (Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 13))) > 0) {
                    Sequel.menyimpan("template_resep_dokter_racikan_detail", "?,?,?,?,?,?,?", "Template Racikan Detail", 7, new String[]{
                        kd_template_baru,
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 0)),
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 1)),
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 9)),
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 11)),
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 12)),
                        String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 13))
                    });
                }
            }

            Sequel.Commit();
            JOptionPane.showMessageDialog(null, "Data template berhasil disimpan!");
            emptTeks();

        } catch (Exception e) {
            Sequel.RollBack();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data template: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Sequel.AutoComitTrue();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if (ubah == false && !KdDokter.getText().isEmpty()) {
                generateKodeTemplate();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, NmTemplate, btnDokter);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
        tampilObat();
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            BtnTambahRacikan.setVisible(false);
            BtnHapusRacikan.setVisible(false);
        } else if (TabRawat.getSelectedIndex() == 1) {
            BtnTambahRacikan.setVisible(true);
            BtnHapusRacikan.setVisible(true);
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbObatResepRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatResepRacikanKeyPressed
        if (tbObatResepRacikan.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (tbObatResepRacikan.getSelectedRow() != -1) {
                    tampilDetailRacikan();
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbObatResepRacikan.getSelectedColumn();
                if (i == 5) {
                    aturanpakai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                } else if (i == 3) {
                    metoderacik.isCek();
                    metoderacik.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    metoderacik.setLocationRelativeTo(internalFrame1);
                    metoderacik.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_tbObatResepRacikanKeyPressed

    private void BtnTambahRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRacikanActionPerformed
        i = tabModeResepRacikan.getRowCount() + 1;
        if (i > 99) {
            JOptionPane.showMessageDialog(null, "Maksimal 99 Racikan..!!");
        } else {
            tabModeResepRacikan.addRow(new Object[]{"" + i, "", "", "", "1", "", ""});
        }
    }//GEN-LAST:event_BtnTambahRacikanActionPerformed

    private void BtnHapusRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRacikanActionPerformed
        if(tbObatResepRacikan.getSelectedRow() > -1){
            tabModeResepRacikan.removeRow(tbObatResepRacikan.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Pilih racikan yang akan dihapus!");
        }
    }//GEN-LAST:event_BtnHapusRacikanActionPerformed

    private void NmTemplateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmTemplateKeyPressed
        Valid.pindah(evt, KdTemplate, KdDokter);
    }//GEN-LAST:event_NmTemplateKeyPressed

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if (tbResep.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbResep.getSelectedColumn();
                if (i == 2) {
                    aturanpakai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                getCekStok();
            }
        }
    }//GEN-LAST:event_tbResepKeyPressed

    private void BtnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBaruActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBaruActionPerformed

    private void BtnHapusTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusTemplateActionPerformed
        if(KdTemplate.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tidak ada template yang dimuat untuk dihapus.\nSilakan cari dan muat template terlebih dahulu.");
            return;
        }

        int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin ingin menghapus template '"+NmTemplate.getText()+"' secara permanen?","Konfirmasi Hapus",JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                Sequel.AutoComitFalse();
                Sequel.meghapus("template_resep_dokter_detail", "kd_template", KdTemplate.getText());
                Sequel.meghapus("template_resep_dokter_racikan_detail", "kd_template", KdTemplate.getText());
                Sequel.meghapus("template_resep_dokter_racikan", "kd_template", KdTemplate.getText());
                Sequel.meghapus("template_resep_dokter", "kd_template", KdTemplate.getText());
                
                Sequel.Commit();
                JOptionPane.showMessageDialog(null, "Template berhasil dihapus!");
                emptTeks();
            } catch (Exception e) {
                Sequel.RollBack();
                JOptionPane.showMessageDialog(null, "Gagal menghapus template: " + e.getMessage());
                e.printStackTrace();
            } finally {
                Sequel.AutoComitTrue();
            }
        }
    }//GEN-LAST:event_BtnHapusTemplateActionPerformed

    private void BtnCariTemplateActionPerformed(java.awt.event.ActionEvent evt) {                                                
        caritemplate.setSize(this.getWidth()-20,this.getHeight()-20);
        caritemplate.setLocationRelativeTo(this);
        caritemplate.tampil();
        caritemplate.setVisible(true);
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        tampilObat();
    }
    
    public void tampilObat() {
        z = 0;
        for (i = 0; i < tbResep.getRowCount(); i++) {
            if (Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 1))) > 0) {
                z++;
            }
        }
        
        pilih = new boolean[z];
        jumlah = new double[z];
        harga = new double[z];
        kodebarang = new String[z];
        namabarang = new String[z];
        kodesatuan = new String[z];
        komposisi = new String[z];
        namajenis = new String[z];
        aturan = new String[z];
        industri = new String[z];
        beli = new double[z];
        stok = new double[z];
        
        z = 0;
        for (i = 0; i < tbResep.getRowCount(); i++) {
            if (Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 1))) > 0) {
                pilih[z] = Boolean.parseBoolean(String.valueOf(tbResep.getValueAt(i, 0)));
                jumlah[z] = Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 1)));
                aturan[z] = String.valueOf(tbResep.getValueAt(i, 2));
                kodebarang[z] = String.valueOf(tbResep.getValueAt(i, 3));
                namabarang[z] = String.valueOf(tbResep.getValueAt(i, 4));
                kodesatuan[z] = String.valueOf(tbResep.getValueAt(i, 5));
                komposisi[z] = String.valueOf(tbResep.getValueAt(i, 6));
                harga[z] = Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 7)));
                namajenis[z] = String.valueOf(tbResep.getValueAt(i, 8));
                industri[z] = String.valueOf(tbResep.getValueAt(i, 9));
                beli[z] = Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 10)));
                stok[z] = Valid.SetAngka(String.valueOf(tbResep.getValueAt(i, 11)));
                z++;
            }
        }

        Valid.tabelKosong(tabModeResep);

        for (i = 0; i < z; i++) {
            tabModeResep.addRow(new Object[]{
                pilih[i], jumlah[i], aturan[i], kodebarang[i], namabarang[i], kodesatuan[i], komposisi[i], harga[i], namajenis[i], industri[i], beli[i], stok[i]
            });
        }
        
        try {
            qrystokkosong = "";
            if (STOKKOSONGRESEP.equals("no")) {
                qrystokkosong = " AND gudangbarang.stok > 0 ";
            }

            StringBuilder sql = new StringBuilder(
                "SELECT databarang.kode_brng, databarang.nama_brng, jenis.nama, databarang.kode_sat, " +
                "databarang.karyawan, databarang.ralan, databarang.beliluar, databarang.kelas1, " +
                "databarang.kelas2, databarang.kelas3, databarang.vip, databarang.vvip, " +
                "databarang.letak_barang, databarang.utama, industrifarmasi.nama_industri, databarang.h_beli, SUM(gudangbarang.stok) as stok " +
                "FROM databarang " +
                "LEFT JOIN jenis ON databarang.kdjns = jenis.kdjns " +
                "LEFT JOIN industrifarmasi ON databarang.kode_industri = industrifarmasi.kode_industri " +
                "LEFT JOIN gudangbarang ON databarang.kode_brng = gudangbarang.kode_brng " +
                "WHERE databarang.status = '1' " + qrystokkosong
            );

            if (!TCari.getText().trim().isEmpty()) {
                sql.append("AND (databarang.kode_brng LIKE ? OR databarang.nama_brng LIKE ? OR jenis.nama LIKE ? OR databarang.letak_barang LIKE ?) ");
            }

            sql.append("GROUP BY databarang.kode_brng ORDER BY databarang.nama_brng");

            ps = koneksi.prepareStatement(sql.toString());

            try {
                if (!TCari.getText().trim().isEmpty()) {
                    String keyword = "%" + TCari.getText().trim() + "%";
                    ps.setString(1, keyword);
                    ps.setString(2, keyword);
                    ps.setString(3, keyword);
                    ps.setString(4, keyword);
                }

                rs = ps.executeQuery();
                
                String hargaField = "ralan";
                String pilihan = Jeniskelas.getSelectedItem().toString();
                if (pilihan.equals("Karyawan")) hargaField = "karyawan";
                else if (pilihan.equals("Beli Luar")) hargaField = "beliluar";
                else if (pilihan.equals("Utama/BPJS")) hargaField = "utama";
                else if (pilihan.equals("Kelas 1")) hargaField = "kelas1";
                else if (pilihan.equals("Kelas 2")) hargaField = "kelas2";
                else if (pilihan.equals("Kelas 3")) hargaField = "kelas3";
                else if (pilihan.equals("VIP")) hargaField = "vip";
                else if (pilihan.equals("VVIP")) hargaField = "vvip";

                while (rs.next()) {
                    tabModeResep.addRow(new Object[]{
                        false, "", "", rs.getString("kode_brng"), rs.getString("nama_brng"), rs.getString("kode_sat"),
                        rs.getString("letak_barang"), Valid.roundUp(rs.getDouble(hargaField), 100), rs.getString("nama"),
                        rs.getString("nama_industri"), rs.getDouble("h_beli"), rs.getDouble("stok")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }
    
    public void tampilDetailRacikan() {
        // Pastikan ada racikan yang dipilih sebelum melakukan apapun
    int selectedRow = tbObatResepRacikan.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Silakan pilih salah satu baris racikan di tabel tengah terlebih dahulu.");
        return; 
    }
    String noRacik = tbObatResepRacikan.getValueAt(selectedRow, 0).toString();

    // Bagian 1: Menyimpan data yang sudah ada (aman dari null)
    z = 0;
    for (i = 0; i < tbDetailResepObatRacikan.getRowCount(); i++) {
        if (Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 13))) > 0) {
            z++;
        }
    }

    jumlah = new double[z];
    harga = new double[z];
    stok = new double[z];
    p1 = new double[z];
    p2 = new double[z];
    kodebarang = new String[z];
    namabarang = new String[z];
    kodesatuan = new String[z];
    komposisi = new String[z];
    String[] no = new String[z];
    namajenis = new String[z];
    industri = new String[z];
    beli = new double[z];
    kapasitas = new double[z];
    kandungan = new String[z];
    
    z = 0;
    for (i = 0; i < tbDetailResepObatRacikan.getRowCount(); i++) {
        if (Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 13))) > 0) {
            no[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 0));
            kodebarang[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 1));
            namabarang[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 2));
            kodesatuan[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 3));
            harga[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 4)));
            beli[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 5)));
            namajenis[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 6));
            stok[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 7)));
            kapasitas[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 8)));
            p1[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 9)));
            p2[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 11)));
            kandungan[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 12));
            jumlah[z] = Valid.SetAngka(String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 13)));
            industri[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 14));
            komposisi[z] = String.valueOf(tbDetailResepObatRacikan.getValueAt(i, 15));
            z++;
        }
    }

    Valid.tabelKosong(tabModeDetailResepRacikan);

    for (i = 0; i < z; i++) {
        tabModeDetailResepRacikan.addRow(new Object[]{
            no[i], kodebarang[i], namabarang[i], kodesatuan[i], harga[i], beli[i],
            namajenis[i], stok[i], kapasitas[i], p1[i], "/", p2[i], kandungan[i],
            jumlah[i], industri[i], komposisi[i]
        });
    }
    
    // Bagian 2: Menambahkan hasil pencarian baru
    try {
        qrystokkosong = "";
        if (STOKKOSONGRESEP.equals("no")) {
            qrystokkosong = " and gudangbarang.stok>0 ";
        }

        ps = koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat," +
                " databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1," +
                " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip," +
                " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,sum(gudangbarang.stok) as stok, databarang.kapasitas " +
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns " +
                " inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri " +
                " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng " +
                " where databarang.status='1' " + qrystokkosong +
                " and (databarang.kode_brng like ? or databarang.nama_brng like ? or jenis.nama like ? or databarang.letak_barang like ?) " +
                " group by databarang.kode_brng order by databarang.nama_brng");
        try {
            ps.setString(1, "%" + TCari.getText().trim() + "%");
            ps.setString(2, "%" + TCari.getText().trim() + "%");
            ps.setString(3, "%" + TCari.getText().trim() + "%");
            ps.setString(4, "%" + TCari.getText().trim() + "%");
            rs = ps.executeQuery();
            
            String hargaField = "ralan";
            String pilihan = Jeniskelas.getSelectedItem().toString();
            if (pilihan.equals("Karyawan")) hargaField = "karyawan";
            else if (pilihan.equals("Beli Luar")) hargaField = "beliluar";
            else if (pilihan.equals("Utama/BPJS")) hargaField = "utama";
            else if (pilihan.equals("Kelas 1")) hargaField = "kelas1";
            else if (pilihan.equals("Kelas 2")) hargaField = "kelas2";
            else if (pilihan.equals("Kelas 3")) hargaField = "kelas3";
            else if (pilihan.equals("VIP")) hargaField = "vip";
            else if (pilihan.equals("VVIP")) hargaField = "vvip";

            while (rs.next()) {
                tabModeDetailResepRacikan.addRow(new Object[]{
                    noRacik,
                    rs.getString("kode_brng"), rs.getString("nama_brng"), rs.getString("kode_sat"), 
                    Valid.roundUp(rs.getDouble(hargaField), 100), rs.getDouble("h_beli"),
                    rs.getString("nama"), rs.getDouble("stok"), rs.getDouble("kapasitas"),
                    1, "/", 1, "", 0, rs.getString("nama_industri"), rs.getString("letak_barang")
                });
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    } catch (Exception e) {
        System.out.println("Notifikasi : " + e);
    }
    }
    
    public void emptTeks() {
        KdTemplate.setText("");
        NmTemplate.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        Valid.tabelKosong(tabModeResep);
        Valid.tabelKosong(tabModeResepRacikan);
        Valid.tabelKosong(tabModeDetailResepRacikan);
        ubah = false;
        NmTemplate.requestFocus();
        tampilObat();
    }
    
    public void isCek() {
        BtnSimpan.setEnabled(akses.getresep_dokter());
        BtnHapusTemplate.setEnabled(akses.getresep_dokter());
    }
    
    private void generateKodeTemplate() {
        if (!KdDokter.getText().trim().isEmpty()) {
            KdTemplate.setText(KdDokter.getText().trim() + System.currentTimeMillis());
        }
    }
    
    private void getCekStok() {
        if (tbResep.getSelectedRow() != -1) {
            if (STOKKOSONGRESEP.equals("no")) {
                try {
                    if (Double.parseDouble(tbResep.getValueAt(tbResep.getSelectedRow(), 1).toString()) > 0) {
                        if (Valid.SetAngka(tbResep.getValueAt(tbResep.getSelectedRow(), 1).toString()) > Valid.SetAngka(tbResep.getValueAt(tbResep.getSelectedRow(), 11).toString())) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                            tbResep.setValueAt("", tbResep.getSelectedRow(), 1);
                        }
                    }
                } catch (Exception e) {
                    tbResep.setValueAt("", tbResep.getSelectedRow(), 1);
                }
            }
        }
    }

    private void getCekStokRacikan() {
        if (tbDetailResepObatRacikan.getSelectedRow() != -1) {
            if (STOKKOSONGRESEP.equals("no")) {
                try {
                    if (Double.parseDouble(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(), 13).toString()) > 0) {
                        if (Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(), 13).toString()) > Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(), 7).toString())) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                            tbDetailResepObatRacikan.setValueAt(0, tbDetailResepObatRacikan.getSelectedRow(), 13);
                        }
                    }
                } catch (Exception e) {
                    tbDetailResepObatRacikan.setValueAt(0, tbDetailResepObatRacikan.getSelectedRow(), 13);
                }
            }
        }
    }

    private void loadTemplate(String kdTemplate) {
        emptTeks(); // Membersihkan form dan memanggil tampilObat() adalah benar
        try {
            // Load header
            ps = koneksi.prepareStatement(
                "SELECT t.nm_template, t.kd_dokter, d.nm_dokter " +
                "FROM template_resep_dokter t " +
                "JOIN dokter d ON t.kd_dokter = d.kd_dokter " +
                "WHERE t.kd_template = ?");
            ps.setString(1, kdTemplate);
            rs = ps.executeQuery();
            if (rs.next()) {
                KdTemplate.setText(kdTemplate);
                NmTemplate.setText(rs.getString("nm_template"));
                KdDokter.setText(rs.getString("kd_dokter"));
                NmDokter.setText(rs.getString("nm_dokter"));
            }

            // Membersihkan baris kosong yang mungkin ditambahkan oleh tampilObat()
            Valid.tabelKosong(tabModeResep);

            // Load detail obat umum
            ps = koneksi.prepareStatement(
                "SELECT d.kode_brng, db.nama_brng, db.kode_sat, db.letak_barang, j.nama AS jenis, " +
                "db.ralan, db.kelas1, db.kelas2, db.kelas3, db.utama, db.vip, db.vvip, db.beliluar, db.karyawan, " +
                "i.nama_industri, db.h_beli, SUM(gb.stok) AS stok, d.jml, d.aturan_pakai " +
                "FROM template_resep_dokter_detail d " +
                "LEFT JOIN databarang db ON d.kode_brng = db.kode_brng " +
                "LEFT JOIN jenis j ON db.kdjns = j.kdjns " +
                "LEFT JOIN industrifarmasi i ON db.kode_industri = i.kode_industri " +
                "LEFT JOIN gudangbarang gb ON d.kode_brng = gb.kode_brng " +
                "WHERE d.kd_template = ? GROUP BY d.kode_brng");
            ps.setString(1, kdTemplate);
            rs = ps.executeQuery();
            while (rs.next()) {
                // ******************* PERBAIKAN LOGIKA HARGA DI SINI ******************* //
                double harga_jual = 0;
                String pilihan = Jeniskelas.getSelectedItem().toString();
                if (pilihan.equals("Karyawan")) harga_jual = rs.getDouble("karyawan");
                else if (pilihan.equals("Beli Luar")) harga_jual = rs.getDouble("beliluar");
                else if (pilihan.equals("Utama/BPJS")) harga_jual = rs.getDouble("utama");
                else if (pilihan.equals("Kelas 1")) harga_jual = rs.getDouble("kelas1");
                else if (pilihan.equals("Kelas 2")) harga_jual = rs.getDouble("kelas2");
                else if (pilihan.equals("Kelas 3")) harga_jual = rs.getDouble("kelas3");
                else if (pilihan.equals("VIP")) harga_jual = rs.getDouble("vip");
                else if (pilihan.equals("VVIP")) harga_jual = rs.getDouble("vvip");
                else harga_jual = rs.getDouble("ralan"); // Default ke 'ralan', BUKAN 'rawatjalan'
                // ********************************************************************** //

                tabModeResep.addRow(new Object[]{
                    false, rs.getDouble("jml"), rs.getString("aturan_pakai"), rs.getString("kode_brng"),
                    rs.getString("nama_brng"), rs.getString("kode_sat"), rs.getString("letak_barang"),
                    harga_jual, rs.getString("jenis"), rs.getString("nama_industri"),
                    rs.getDouble("h_beli"), rs.getDouble("stok")
                });
            }

            // Load racikan
            ps = koneksi.prepareStatement(
                "SELECT r.no_racik, r.nama_racik, r.kd_racik, m.nm_racik, r.jml_dr, r.aturan_pakai, r.keterangan " +
                "FROM template_resep_dokter_racikan r " +
                "JOIN metode_racik m ON r.kd_racik = m.kd_racik " +
                "WHERE r.kd_template = ?");
            ps.setString(1, kdTemplate);
            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeResepRacikan.addRow(new Object[]{
                    rs.getString("no_racik"), rs.getString("nama_racik"), rs.getString("kd_racik"),
                    rs.getString("nm_racik"), rs.getInt("jml_dr"), rs.getString("aturan_pakai"),
                    rs.getString("keterangan")
                });
            }

             // Load detail racikan
             ps = koneksi.prepareStatement(
                "SELECT rd.no_racik, rd.kode_brng, db.nama_brng, db.kode_sat, " +
                "db.ralan, db.kelas1, db.kelas2, db.kelas3, db.utama, db.vip, db.vvip, db.beliluar, db.karyawan, " +
                "db.h_beli, j.nama AS jenis, SUM(gb.stok) AS stok, db.kapasitas, i.nama_industri, " +
                "rd.p1, rd.p2, rd.kandungan, rd.jml, db.letak_barang " +
                "FROM template_resep_dokter_racikan_detail rd " +
                "JOIN databarang db ON rd.kode_brng = db.kode_brng " +
                "JOIN jenis j ON db.kdjns = j.kdjns " +
                "JOIN industrifarmasi i ON db.kode_industri = i.kode_industri " +
                "JOIN gudangbarang gb ON rd.kode_brng = gb.kode_brng " +
                "WHERE rd.kd_template = ? GROUP BY rd.kode_brng, rd.no_racik");
            ps.setString(1, kdTemplate);
            rs = ps.executeQuery();
            while(rs.next()){
                 // ******************* PERBAIKAN LOGIKA HARGA DI SINI JUGA ******************* //
                 double harga = 0;
                 String pilihan = Jeniskelas.getSelectedItem().toString();
                 if (pilihan.equals("Karyawan")) harga = rs.getDouble("karyawan");
                 else if (pilihan.equals("Beli Luar")) harga = rs.getDouble("beliluar");
                 else if (pilihan.equals("Utama/BPJS")) harga = rs.getDouble("utama");
                 else if (pilihan.equals("Kelas 1")) harga = rs.getDouble("kelas1");
                 else if (pilihan.equals("Kelas 2")) harga = rs.getDouble("kelas2");
                 else if (pilihan.equals("Kelas 3")) harga = rs.getDouble("kelas3");
                 else if (pilihan.equals("VIP")) harga = rs.getDouble("vip");
                 else if (pilihan.equals("VVIP")) harga = rs.getDouble("vvip");
                 else harga = rs.getDouble("ralan");
                 // ************************************************************************* //

                 tabModeDetailResepRacikan.addRow(new Object[]{
                    rs.getString("no_racik"),
                    rs.getString("kode_brng"),
                    rs.getString("nama_brng"),
                    rs.getString("kode_sat"),
                    harga,
                    rs.getDouble("h_beli"),
                    rs.getString("jenis"),
                    rs.getDouble("stok"),
                    rs.getDouble("kapasitas"),
                    rs.getDouble("p1"),
                    "/",
                    rs.getDouble("p2"),
                    rs.getString("kandungan"),
                    rs.getDouble("jml"),
                    rs.getString("nama_industri"),
                    rs.getString("letak_barang")
                 });
            }

            ubah = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat template: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
            if (ps != null) { try { ps.close(); } catch (Exception e) {} }
        }
    }
}