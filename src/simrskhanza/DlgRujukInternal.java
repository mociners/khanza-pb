package simrskhanza;

import fungsi.WarnaTable;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;

import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter4;

public final class DlgRujukInternal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode, tabModeTindakanKomplikasi;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, jml = 0, index = 0;
    private boolean[] pilih;
    private String[] kode, masalah;
    private DlgCariPoli poli1 = new DlgCariPoli(null, false);
    private DlgCariDokter4 dokter4 = new DlgCariDokter4(null, false);
    private DlgCariDokter dokter1 = new DlgCariDokter(null, false);

    public DlgRujukInternal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K.", "Kode Dokter", "Nama Dokter Perujuk",
                "Kode Poli", "Nama Poli Dituju", "Kode Dokter", "Nama Dokter Dituju", "Diagnosa", "Catatan",
                "Tgl.Rencana", "Status", "Jawab Permintaan", "Saran Tindakan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbRujukan.setModel(tabMode);
        tbRujukan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRujukan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbRujukan.getColumnModel().getColumn(i);
            if (i == 0)
                column.setPreferredWidth(105);
            else if (i == 1)
                column.setPreferredWidth(70);
            else if (i == 2)
                column.setPreferredWidth(150);
            else if (i == 3)
                column.setPreferredWidth(65);
            else if (i == 4)
                column.setPreferredWidth(25);
            else if (i == 5)
                column.setPreferredWidth(80);
            else if (i == 6)
                column.setPreferredWidth(150);
            else if (i == 7)
                column.setPreferredWidth(80);
            else if (i == 8)
                column.setPreferredWidth(150);
            else if (i == 9)
                column.setPreferredWidth(80);
            else if (i == 10)
                column.setPreferredWidth(150);
            else if (i == 11)
                column.setPreferredWidth(150);
            else if (i == 12)
                column.setPreferredWidth(150);
            else if (i == 13)
                column.setPreferredWidth(80);
            else if (i == 14)
                column.setPreferredWidth(80);
            else if (i == 15)
                column.setPreferredWidth(150);
            else if (i == 16)
                column.setPreferredWidth(150);
        }
        tbRujukan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeTindakanKomplikasi = new DefaultTableModel(null, new Object[] { "P", "KODE", "KONSULTASI" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex == 0;
            }

            Class[] types = new Class[] { java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbMasalahKomplikasiKehamilan.setModel(tabModeTindakanKomplikasi);
        tbMasalahKomplikasiKehamilan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbMasalahKomplikasiKehamilan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbMasalahKomplikasiKehamilan.getColumnModel().getColumn(0).setPreferredWidth(20);
        tbMasalahKomplikasiKehamilan.getColumnModel().getColumn(1).setMinWidth(0);
        tbMasalahKomplikasiKehamilan.getColumnModel().getColumn(1).setMaxWidth(0);
        tbMasalahKomplikasiKehamilan.getColumnModel().getColumn(2).setPreferredWidth(350);
        tbMasalahKomplikasiKehamilan.setDefaultRenderer(Object.class, new WarnaTable());

        poli1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli1.getTable().getSelectedRow() != -1) {
                    kd_poli.setText(poli1.getTable().getValueAt(poli1.getTable().getSelectedRow(), 0).toString());
                    nm_poli.setText(poli1.getTable().getValueAt(poli1.getTable().getSelectedRow(), 1).toString());
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

        dokter4.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter4.getTable().getSelectedRow() != -1) {
                    kd_dokter.setText(dokter4.getTable().getValueAt(dokter4.getTable().getSelectedRow(), 0).toString());
                    nm_dokter.setText(dokter4.getTable().getValueAt(dokter4.getTable().getSelectedRow(), 1).toString());
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
                    kd_dokter2
                            .setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(), 0).toString());
                    nama_dokter2
                            .setText(dokter1.getTable().getValueAt(dokter1.getTable().getSelectedRow(), 1).toString());
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

        isCek();
    }

    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        tbKomplikasiKehamilanSebelumnya = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        kd_dokter = new widget.TextBox();
        nm_dokter = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        label16 = new widget.Label();
        kd_dokter2 = new widget.TextBox();
        nama_dokter2 = new widget.TextBox();
        BtnDPJP = new widget.Button();
        Scroll10 = new widget.ScrollPane();
        tbMasalahKomplikasiKehamilan = new widget.Table();
        TCariKomplikasi = new widget.TextBox();
        label15 = new widget.Label();
        BtnCariPemeriksaan3 = new widget.Button();
        BtnTambahMasalah2 = new widget.Button();
        label17 = new widget.Label();
        jLabel12 = new widget.Label();
        nm_poli = new widget.TextBox();
        kd_poli = new widget.TextBox();
        BtnPoli = new widget.Button();
        label18 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        catatan = new widget.TextArea();
        status = new widget.TextBox();
        jawab_permintaan = new widget.TextArea();
        saran_tindakan = new widget.TextArea();
        label19 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        diagnosa = new widget.TextArea();
        TglAsuhan1 = new widget.Tanggal();
        TglRencana = new widget.Tanggal();
        labelTglRencana = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRujukan = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Lembar Konsul ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        tbKomplikasiKehamilanSebelumnya.setPreferredSize(new java.awt.Dimension(942, 307));
        tbKomplikasiKehamilanSebelumnya.setLayout(null);

        TNoRw.setName("TNoRw");
        tbKomplikasiKehamilanSebelumnya.add(TNoRw);
        TNoRw.setBounds(70, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien");
        tbKomplikasiKehamilanSebelumnya.add(TPasien);
        TPasien.setBounds(309, 10, 230, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM");
        tbKomplikasiKehamilanSebelumnya.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText(" dr Perujuk :");
        tbKomplikasiKehamilanSebelumnya.add(label14);
        label14.setBounds(10, 40, 60, 23);

        kd_dokter.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(kd_dokter);
        kd_dokter.setBounds(70, 100, 100, 23);

        nm_dokter.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(nm_dokter);
        nm_dokter.setBounds(190, 100, 290, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dokter4.isCek();
                dokter4.TCari.requestFocus();
                dokter4.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dokter4.setLocationRelativeTo(internalFrame1);
                dokter4.setVisible(true);
            }
        });
        tbKomplikasiKehamilanSebelumnya.add(BtnDokter2);
        BtnDokter2.setBounds(500, 100, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        tbKomplikasiKehamilanSebelumnya.add(jLabel8);
        jLabel8.setBounds(540, 10, 60, 20);

        TglLahir.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(TglLahir);
        TglLahir.setBounds(610, 10, 80, 24);

        Jk.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(Jk);
        Jk.setBounds(770, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        tbKomplikasiKehamilanSebelumnya.add(jLabel10);
        jLabel10.setBounds(10, 10, 60, 23);

        label11.setText("Tanggal :");
        tbKomplikasiKehamilanSebelumnya.add(label11);
        label11.setBounds(550, 40, 50, 20);

        jLabel11.setText("J.K. :");
        tbKomplikasiKehamilanSebelumnya.add(jLabel11);
        jLabel11.setBounds(730, 10, 30, 23);

        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tbKomplikasiKehamilanSebelumnya.add(TglAsuhan);
        TglAsuhan.setBounds(610, 40, 140, 20);

        label16.setText("Kepada dr :");
        tbKomplikasiKehamilanSebelumnya.add(label16);
        label16.setBounds(10, 100, 60, 23);

        kd_dokter2.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(kd_dokter2);
        kd_dokter2.setBounds(70, 40, 100, 23);

        nama_dokter2.setEditable(false);
        tbKomplikasiKehamilanSebelumnya.add(nama_dokter2);
        nama_dokter2.setBounds(190, 40, 290, 23);

        BtnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dokter1.isCek();
                dokter1.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dokter1.setLocationRelativeTo(internalFrame1);
                dokter1.setVisible(true);
            }
        });
        tbKomplikasiKehamilanSebelumnya.add(BtnDPJP);
        BtnDPJP.setBounds(500, 40, 28, 23);

        Scroll10.setViewportView(tbMasalahKomplikasiKehamilan);
        tbKomplikasiKehamilanSebelumnya.add(Scroll10);
        Scroll10.setBounds(180, 130, 320, 120);

        tbKomplikasiKehamilanSebelumnya.add(TCariKomplikasi);
        TCariKomplikasi.setBounds(240, 270, 210, 20);
        TCariKomplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampilKehamilanSebelumnya();
                }
            }
        });

        label15.setText("Diagnosa :");
        tbKomplikasiKehamilanSebelumnya.add(label15);
        label15.setBounds(540, 110, 60, 23);

        labelTglRencana.setText("Tgl.Rencana :");
        tbKomplikasiKehamilanSebelumnya.add(labelTglRencana);
        labelTglRencana.setBounds(520, 70, 80, 23);

        TglRencana.setDisplayFormat("dd-MM-yyyy");
        tbKomplikasiKehamilanSebelumnya.add(TglRencana);
        TglRencana.setBounds(610, 70, 90, 23);

        BtnCariPemeriksaan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCariPemeriksaan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilKehamilanSebelumnya();
            }
        });
        tbKomplikasiKehamilanSebelumnya.add(BtnCariPemeriksaan3);
        BtnCariPemeriksaan3.setBounds(450, 270, 30, 20);

        BtnTambahMasalah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png")));
        BtnTambahMasalah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MasterKonsultasi form = new MasterKonsultasi(null, false);
                form.setVisible(true);
            }
        });
        tbKomplikasiKehamilanSebelumnya.add(BtnTambahMasalah2);
        BtnTambahMasalah2.setBounds(490, 270, 30, 20);

        label17.setText("Key Word :");
        tbKomplikasiKehamilanSebelumnya.add(label17);
        label17.setBounds(170, 270, 60, 20);

        jLabel12.setText("Poli Dituju :");
        tbKomplikasiKehamilanSebelumnya.add(jLabel12);
        jLabel12.setBounds(0, 70, 60, 23);

        tbKomplikasiKehamilanSebelumnya.add(nm_poli);
        nm_poli.setBounds(190, 70, 290, 23);

        tbKomplikasiKehamilanSebelumnya.add(kd_poli);
        kd_poli.setBounds(70, 70, 100, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poli1.isCek();
                poli1.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli1.setLocationRelativeTo(internalFrame1);
                poli1.setVisible(true);
            }
        });
        tbKomplikasiKehamilanSebelumnya.add(BtnPoli);
        BtnPoli.setBounds(500, 70, 28, 23);

        label18.setText("Mohon bantuan sejawat untuk :");
        tbKomplikasiKehamilanSebelumnya.add(label18);
        label18.setBounds(10, 130, 170, 23);

        scrollPane4.setViewportView(catatan);
        tbKomplikasiKehamilanSebelumnya.add(scrollPane4);
        scrollPane4.setBounds(610, 160, 250, 150);

        status.setEditable(false);
        status.setText("belum");
        tbKomplikasiKehamilanSebelumnya.add(status);
        status.setBounds(330, 370, 80, 23);

        tbKomplikasiKehamilanSebelumnya.add(jawab_permintaan);
        jawab_permintaan.setBounds(150, 440, 162, 72);

        tbKomplikasiKehamilanSebelumnya.add(saran_tindakan);
        saran_tindakan.setBounds(0, 0, 162, 72);

        label19.setText("Catatan :");
        tbKomplikasiKehamilanSebelumnya.add(label19);
        label19.setBounds(540, 160, 60, 23);

        scrollPane5.setViewportView(diagnosa);
        tbKomplikasiKehamilanSebelumnya.add(scrollPane5);
        scrollPane5.setBounds(610, 110, 250, 40);

        TglAsuhan1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tbKomplikasiKehamilanSebelumnya.add(TglAsuhan1);
        TglAsuhan1.setBounds(130, 370, 140, 20);

        scrollInput.setViewportView(tbKomplikasiKehamilanSebelumnya);
        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);
        TabRawat.addTab("Lembar Konsul", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));
        Scroll.setViewportView(tbRujukan);
        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rencana :");
        jLabel19.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setText("s.d.");
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    BtnCariActionPerformed(null);
                }
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);
        TabRawat.addTab("Data Rujukan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("")
                || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "Pasien");
        } else if (nm_poli.getText().trim().equals("") || kd_poli.getText().trim().equals("")) {
            Valid.textKosong(kd_poli, "poliklinik");
        } else if (kd_dokter.getText().trim().equals("") || nm_dokter.getText().trim().equals("")) {
            Valid.textKosong(kd_dokter, "dokter");
        } else {
            try {
                java.sql.Connection koneksi = koneksiDB.condb();
                java.sql.PreparedStatement ps = koneksi.prepareStatement(
                        "insert into rujukan_internal_poli(no_rawat,kd_dokter,kd_poli,nm_poli,kd_dokter2,nama_dokter2,diagnosa,catatan,tanggal,status,tanggal_jawab,jawab_permintaan,saran_tindakan,tgl_rencana) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                try {
                    ps.setString(1, TNoRw.getText());
                    ps.setString(2, kd_dokter.getText());
                    ps.setString(3, kd_poli.getText());
                    ps.setString(4, nm_poli.getText());
                    ps.setString(5, kd_dokter2.getText());
                    ps.setString(6, nama_dokter2.getText());
                    ps.setString(7, diagnosa.getText());
                    ps.setString(8, catatan.getText());
                    ps.setString(9, Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " "
                            + TglAsuhan.getSelectedItem().toString().substring(11, 19));
                    ps.setString(10, status.getText());
                    ps.setString(11, Valid.SetTgl(TglAsuhan1.getSelectedItem() + "") + " "
                            + TglAsuhan.getSelectedItem().toString().substring(11, 19));
                    ps.setString(12, jawab_permintaan.getText());
                    ps.setString(13, saran_tindakan.getText());
                    ps.setString(14, Valid.SetTgl(TglRencana.getSelectedItem() + ""));
                    ps.executeUpdate();
                    emptTeks();
                    tampil();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                    JOptionPane.showMessageDialog(null, "Maaf, Gagal menyimpan data...!!!!");
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        kd_poli.setText("");
        nm_poli.setText("");
        kd_dokter2.setText("");
        nama_dokter2.setText("");
        kd_dokter.setText("");
        nm_dokter.setText("");
        catatan.setText("");
        dispose();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        tampilKehamilanSebelumnya();
        tampil();
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        tampil();
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select rujukan_internal_poli.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,"
                                +
                                "rujukan_internal_poli.kd_dokter,d1.nm_dokter,rujukan_internal_poli.kd_poli,poliklinik.nm_poli,"
                                +
                                "rujukan_internal_poli.kd_dokter2,d2.nm_dokter,rujukan_internal_poli.diagnosa,rujukan_internal_poli.catatan,"
                                +
                                "rujukan_internal_poli.tgl_rencana,rujukan_internal_poli.status,rujukan_internal_poli.jawab_permintaan,rujukan_internal_poli.saran_tindakan "
                                +
                                "from rujukan_internal_poli inner join reg_periksa on rujukan_internal_poli.no_rawat=reg_periksa.no_rawat "
                                +
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                                "left join dokter as d1 on rujukan_internal_poli.kd_dokter=d1.kd_dokter " +
                                "left join dokter as d2 on rujukan_internal_poli.kd_dokter2=d2.kd_dokter " +
                                "left join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli " +
                                "where rujukan_internal_poli.tanggal between ? and ? order by rujukan_internal_poli.tanggal");
            } else {
                ps = koneksi.prepareStatement(
                        "select rujukan_internal_poli.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,"
                                +
                                "rujukan_internal_poli.kd_dokter,d1.nm_dokter,rujukan_internal_poli.kd_poli,poliklinik.nm_poli,"
                                +
                                "rujukan_internal_poli.kd_dokter2,d2.nm_dokter,rujukan_internal_poli.diagnosa,rujukan_internal_poli.catatan,"
                                +
                                "rujukan_internal_poli.tgl_rencana,rujukan_internal_poli.status,rujukan_internal_poli.jawab_permintaan,rujukan_internal_poli.saran_tindakan "
                                +
                                "from rujukan_internal_poli inner join reg_periksa on rujukan_internal_poli.no_rawat=reg_periksa.no_rawat "
                                +
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                                "left join dokter as d1 on rujukan_internal_poli.kd_dokter=d1.kd_dokter " +
                                "left join dokter as d2 on rujukan_internal_poli.kd_dokter2=d2.kd_dokter " +
                                "left join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli " +
                                "where rujukan_internal_poli.tanggal between ? and ? and " +
                                "(rujukan_internal_poli.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                +
                                "poliklinik.nm_poli like ? or d1.nm_dokter like ? or d2.nm_dokter like ?) order by rujukan_internal_poli.tanggal");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[] {
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                            rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                            rs.getString(16), rs.getString(17)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    private void tampilKehamilanSebelumnya() {
        try {
            jml = 0;
            for (i = 0; i < tbMasalahKomplikasiKehamilan.getRowCount(); i++) {
                if (tbMasalahKomplikasiKehamilan.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }
            pilih = null;
            pilih = new boolean[jml];
            kode = null;
            kode = new String[jml];
            masalah = null;
            masalah = new String[jml];
            index = 0;
            for (i = 0; i < tbMasalahKomplikasiKehamilan.getRowCount(); i++) {
                if (tbMasalahKomplikasiKehamilan.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = tbMasalahKomplikasiKehamilan.getValueAt(i, 1).toString();
                    masalah[index] = tbMasalahKomplikasiKehamilan.getValueAt(i, 2).toString();
                    index++;
                }
            }
            Valid.tabelKosong(tabModeTindakanKomplikasi);
            for (i = 0; i < jml; i++) {
                tabModeTindakanKomplikasi.addRow(new Object[] { pilih[i], kode[i], masalah[i] });
            }
            ps = koneksi.prepareStatement(
                    "select * from master_konsultasi where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1, "%" + TCariKomplikasi.getText().trim() + "%");
                ps.setString(2, "%" + TCariKomplikasi.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeTindakanKomplikasi.addRow(new Object[] { false, rs.getString(1), rs.getString(2) });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void isCek() {
        /*
         * if (akses.getjml2() >= 1) {
         * kd_dokter.setEditable(false);
         * kd_dokter.setText(akses.getkode());
         * Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nm_dokter,
         * kd_dokter.getText());
         * if (nm_dokter.getText().equals("")) {
         * Sequel.cariIsi("select nama from petugas where nip=?", nm_dokter,
         * kd_dokter.getText());
         * if (nm_dokter.getText().equals("")) {
         * kd_dokter.setText("");
         * JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
         * }
         * }
         * }
         */
    }

    public void setNoRm(String norw, String norm, String namapasien, int lebar, int tinggi) {
        TNoRw.setText(norw);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);
        TCari.setText(norw);
        isRawat();
        isPsien();
        isPsien2();
        tampil();
        kd_dokter2.setText("");
        nama_dokter2.setText("");
        kd_dokter.setText("");
        nm_dokter.setText("");
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
        try {
            ps = koneksi.prepareStatement(
                    "select  reg_periksa.no_rawat,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir," +
                            "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.kd_poli " +
                            "from reg_periksa " +
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter " +
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli " +
                            "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + TNoRM.getText() + "' ", TPasien);
    }

    private void isPsien2() {
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis='" + TNoRM.getText() + "' ", TglLahir);
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        TglRencana.setDate(new Date());
        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        TabRawat.setSelectedIndex(0);
    }

    // Variables declaration
    private widget.Button BtnCari;
    private widget.Button BtnAll;
    private widget.Button BtnCariPemeriksaan3;
    private widget.Button BtnDPJP;
    private widget.Button BtnDokter2;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah2;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Jk;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.TextBox TCari;
    private widget.TextBox TCariKomplikasi;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglAsuhan1;
    private widget.Tanggal TglRencana;
    private widget.TextBox TglLahir;
    private widget.TextArea catatan;
    private widget.TextArea diagnosa;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.TextArea jawab_permintaan;
    private widget.TextBox kd_dokter;
    private widget.TextBox kd_dokter2;
    private widget.TextBox kd_poli;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label labelTglRencana;
    private widget.TextBox nama_dokter2;
    private widget.TextBox nm_dokter;
    private widget.TextBox nm_poli;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextArea saran_tindakan;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.TextBox status;
    private widget.PanelBiasa tbKomplikasiKehamilanSebelumnya;
    private widget.Table tbMasalahKomplikasiKehamilan;
    private widget.Table tbRujukan;
}
