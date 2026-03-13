package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Dialog pencarian dan pemilihan template laporan operasi casemix.
 * Menampilkan daftar template, search, hapus, dan pilih.
 */
public final class DlgCariTemplateLaporanOperasiCasemix extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private validasi Valid = new validasi();
    private sekuel Sequel = new sekuel();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;

    private widget.Table tbKamar;
    private widget.TextBox TCari;
    private widget.Button BtnCari, BtnAll, BtnHapus, BtnKeluar;
    private widget.Label LCount;
    private widget.InternalFrame internalFrame1;
    private widget.ScrollPane Scroll;
    private widget.panelisi panelisi3;
    private widget.Button BtnTambah, BtnEdit;

    // Referensi ke form utama untuk mengambil data yang sedang diisi
    private RMLaporanOperasi formLaporanOperasi;

    public DlgCariTemplateLaporanOperasiCasemix(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initDialog();
        this.setLocation(10, 2);
        setSize(656, 350);

        // 23 columns: no_template, nama_template, + 21 data fields
        Object[] row = {
                "No.Template", "Nama Template",
                "Jenis Operasi", "Jenis Anestesi",
                "Diagnosa Pra Bedah", "Diagnosa Pasca Bedah",
                "Tindakan", "Lama Pembedahan", "Cara Pembiusan", "Posisi Pasien",
                "Uraian Pembedahan", "Komplikasi", "Perdarahan",
                "Jaringan Dikirim", "Ket Jaringan", "Asal Jaringan",
                "Jenis Pembedahan", "Pemasangan Implan",
                "Lokasi Implan", "Jenis Implan", "No Reg Implan",
                "Klasifikasi Operasi", "Konsultasi Intra Operatif"
        };
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbKamar.setModel(tabMode);
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 23; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(70);
            } else if (i == 1) {
                column.setPreferredWidth(250);
            } else {
                // Hide all data columns
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

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
    }

    private void initDialog() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent evt) {
                tampil();
            }
        });

        internalFrame1 = new widget.InternalFrame();
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Template Laporan Operasi Casemix ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50)));
        internalFrame1.setName("internalFrame1");
        internalFrame1.setLayout(new BorderLayout(1, 1));

        // Table
        Scroll = new widget.ScrollPane();
        Scroll.setName("Scroll");
        Scroll.setOpaque(true);

        tbKamar = new widget.Table();
        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar");
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    dispose();
                }
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                    dispose();
                } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                    TCari.setText("");
                    TCari.requestFocus();
                }
            }
        });
        Scroll.setViewportView(tbKamar);
        internalFrame1.add(Scroll, BorderLayout.CENTER);

        // Bottom panel
        panelisi3 = new widget.panelisi();
        panelisi3.setName("panelisi3");
        panelisi3.setPreferredSize(new Dimension(100, 43));
        panelisi3.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 9));

        widget.Label label9 = new widget.Label();
        label9.setText("Key Word :");
        label9.setName("label9");
        label9.setPreferredSize(new Dimension(68, 23));
        panelisi3.add(label9);

        TCari = new widget.TextBox();
        TCari.setName("TCari");
        TCari.setPreferredSize(new Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampil();
                } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
                    tbKamar.requestFocus();
                }
            }
        });
        panelisi3.add(TCari);

        BtnCari = new widget.Button();
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari");
        BtnCari.setPreferredSize(new Dimension(28, 23));
        BtnCari.addActionListener(evt -> tampil());
        panelisi3.add(BtnCari);

        BtnAll = new widget.Button();
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll");
        BtnAll.setPreferredSize(new Dimension(28, 23));
        BtnAll.addActionListener(evt -> {
            TCari.setText("");
            tampil();
        });
        panelisi3.add(BtnAll);

        BtnHapus = new widget.Button();
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('3');
        BtnHapus.setToolTipText("Alt+3 Hapus Template");
        BtnHapus.setName("BtnHapus");
        BtnHapus.setPreferredSize(new Dimension(28, 23));
        BtnHapus.addActionListener(evt -> {
            if (tbKamar.getSelectedRow() != -1) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Hapus template \"" + tabMode.getValueAt(tbKamar.getSelectedRow(), 1) + "\" ?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Sequel.queryu("delete from template_laporan_operasi_casemix where no_template='"
                            + tabMode.getValueAt(tbKamar.getSelectedRow(), 0) + "'");
                    tampil();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih template yang akan dihapus..!!");
            }
        });
        panelisi3.add(BtnHapus);

        widget.Label label10 = new widget.Label();
        label10.setText("Record :");
        label10.setName("label10");
        label10.setPreferredSize(new Dimension(60, 23));
        panelisi3.add(label10);

        LCount = new widget.Label();
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount");
        LCount.setPreferredSize(new Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar = new widget.Button();
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar");
        BtnKeluar.setPreferredSize(new Dimension(28, 23));
        BtnKeluar.addActionListener(evt -> dispose());
        panelisi3.add(BtnKeluar);

        BtnTambah = new widget.Button();
        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png")));
        BtnTambah.setMnemonic('5');
        BtnTambah.setToolTipText("Alt+5 Tambah Template");
        BtnTambah.setName("BtnTambah");
        BtnTambah.setPreferredSize(new Dimension(35, 23));
        BtnTambah.addActionListener(evt -> {
            DlgTemplateLaporanOperasiCasemix master = new DlgTemplateLaporanOperasiCasemix(null, true);
            master.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            master.setLocationRelativeTo(internalFrame1);
            master.emptTeks();
            master.setVisible(true);
            tampil(); // Refresh tabel setelah form tertutup
        });
        panelisi3.add(BtnTambah);

        BtnEdit = new widget.Button();
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setMnemonic('U');
        BtnEdit.setToolTipText("Alt+U Ubah Template");
        BtnEdit.setName("BtnEdit");
        BtnEdit.setPreferredSize(new Dimension(35, 23));
        BtnEdit.addActionListener(evt -> {
            if (tbKamar.getSelectedRow() != -1) {
                int row = tbKamar.getSelectedRow();
                DlgTemplateLaporanOperasiCasemix master = new DlgTemplateLaporanOperasiCasemix(null, true);
                master.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                master.setLocationRelativeTo(internalFrame1);

                master.setTemplateData(
                        tabMode.getValueAt(row, 0).toString(),
                        tabMode.getValueAt(row, 1).toString(),
                        tabMode.getValueAt(row, 2).toString(),
                        tabMode.getValueAt(row, 3).toString(),
                        tabMode.getValueAt(row, 4).toString(),
                        tabMode.getValueAt(row, 5).toString(),
                        tabMode.getValueAt(row, 6).toString(),
                        tabMode.getValueAt(row, 7).toString(),
                        tabMode.getValueAt(row, 8).toString(),
                        tabMode.getValueAt(row, 9).toString(),
                        tabMode.getValueAt(row, 10).toString(),
                        tabMode.getValueAt(row, 11).toString(),
                        tabMode.getValueAt(row, 12).toString(),
                        tabMode.getValueAt(row, 13).toString(),
                        tabMode.getValueAt(row, 14).toString(),
                        tabMode.getValueAt(row, 15).toString(),
                        tabMode.getValueAt(row, 16).toString(),
                        tabMode.getValueAt(row, 17).toString(),
                        tabMode.getValueAt(row, 18).toString(),
                        tabMode.getValueAt(row, 19).toString(),
                        tabMode.getValueAt(row, 20).toString(),
                        tabMode.getValueAt(row, 21).toString(),
                        tabMode.getValueAt(row, 22).toString());

                master.setVisible(true);
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, "Pilih template yang akan diubah..!!");
            }
        });
        panelisi3.add(BtnEdit);

        internalFrame1.add(panelisi3, BorderLayout.PAGE_END);
        getContentPane().add(internalFrame1, BorderLayout.CENTER);
        pack();
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select no_template, nama_template, jenisoperasi, jenisanestesi, "
                            + "diagnosaprabedah, diagnosapascabedah, tindakan, lamapembedahan, "
                            + "pembiusan, posisi, uraian, komplikasi, perdarahan, "
                            + "dikirim, dikirimket, asaljaringan, jenispembedahan, "
                            + "pemasanganimplan, lokasiimplan, jenisimplan, noregimplan, "
                            + "klasifikasioperasi, konsultasiintraoperatif "
                            + "from template_laporan_operasi_casemix "
                            + (TCari.getText().equals("") ? ""
                                    : "where no_template like ? or nama_template like ? or diagnosaprabedah like ? or tindakan like ? ")
                            + "order by no_template");
            try {
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(1, "%" + TCari.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[] {
                            rs.getString("no_template"), rs.getString("nama_template"),
                            rs.getString("jenisoperasi"), rs.getString("jenisanestesi"),
                            rs.getString("diagnosaprabedah"), rs.getString("diagnosapascabedah"),
                            rs.getString("tindakan"), rs.getString("lamapembedahan"),
                            rs.getString("pembiusan"), rs.getString("posisi"),
                            rs.getString("uraian"), rs.getString("komplikasi"),
                            rs.getString("perdarahan"), rs.getString("dikirim"),
                            rs.getString("dikirimket"), rs.getString("asaljaringan"),
                            rs.getString("jenispembedahan"), rs.getString("pemasanganimplan"),
                            rs.getString("lokasiimplan"), rs.getString("jenisimplan"),
                            rs.getString("noregimplan"), rs.getString("klasifikasioperasi"),
                            rs.getString("konsultasiintraoperatif")
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
        TCari.setText("");
        TCari.requestFocus();
    }

    public JTable getTable() {
        return tbKamar;
    }

    public void isCek() {
        BtnHapus.setEnabled(akses.gettemplate_laporan_operasi());
    }
}
