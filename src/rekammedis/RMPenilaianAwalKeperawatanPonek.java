/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */

package rekammedis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileReader;
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
import kepegawaian.DlgCariPetugas;
import java.sql.CallableStatement;

/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanPonek extends javax.swing.JDialog {
    private final DefaultTableModel tabMode, tabModeMasalah, tabModeDetailMasalah, tabModeRencana, tabModeDetailRencana;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private int i = 0, jml = 0, index = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private boolean[] pilih;
    private String[] kode, masalah;
    private String masalahkeperawatanigd = "", finger = "";
    private StringBuilder htmlContent;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    
    // Variabel pengatur ukuran font (ubah nilai ini untuk menyesuaikan ukuran font)
    private int ukuranFont = 14;

    private void aturUkuranFont(java.awt.Container container, int size) {
        for (java.awt.Component comp : container.getComponents()) {
            // Ubah font pada label DAN komponen input
            if (comp instanceof widget.Label || comp instanceof widget.TextBox || comp instanceof widget.TextArea ||
                comp instanceof widget.ComboBox || comp instanceof widget.Tanggal || comp instanceof widget.CekBox ||
                comp instanceof javax.swing.JLabel || comp instanceof javax.swing.JTextField ||
                comp instanceof javax.swing.JTextArea || comp instanceof javax.swing.JComboBox) {
                java.awt.Font currentFont = comp.getFont();
                if (currentFont != null) {
                    comp.setFont(new java.awt.Font(currentFont.getName(), currentFont.getStyle(), size));
                }
            }
            if (comp instanceof java.awt.Container) {
                aturUkuranFont((java.awt.Container) comp, size);
            }
        }
    }

    /**
     * Dua langkah:
     * 1. Skalakan bounds x dan width secara proporsional agar jarak antar komponen seragam.
     * 2. Perlebar label yang masih terpotong, lalu perbarui ukuran FormInput agar
     *    scrollInput bisa menampilkan seluruh konten yang melebar.
     */
    private void initSesuaikanLabel() {
        double scale = (double) ukuranFont / 11.0;
        if (scale != 1.0) {
            skalakanBounds(internalFrame1, scale);
        }
        perbaikiLebarLabel(internalFrame1);
        perbaikiUkuranPanel(FormInput);
    }

    /** Skalakan bounds semua komponen di null-layout container secara rekursif. */
    private void skalakanBounds(java.awt.Container container, double scale) {
        for (java.awt.Component comp : container.getComponents()) {
            if (container.getLayout() == null) {
                java.awt.Rectangle b = comp.getBounds();
                comp.setBounds(
                    (int) Math.round(b.x * scale),
                    b.y,             // y tetap, agar tidak melewati batas vertikal
                    (int) Math.round(b.width * scale),
                    b.height         // height tetap
                );
            }
            if (comp instanceof java.awt.Container) {
                skalakanBounds((java.awt.Container) comp, scale);
            }
        }
    }

    /** Perlebar label yang masih terpotong setelah scaling (hanya ubah lebar, bukan posisi). */
    private void perbaikiLebarLabel(java.awt.Container container) {
        for (java.awt.Component comp : container.getComponents()) {
            boolean isLabel = (comp instanceof widget.Label) || (comp instanceof javax.swing.JLabel);
            if (isLabel) {
                javax.swing.JLabel lbl = (javax.swing.JLabel) comp;
                String text = lbl.getText();
                if (text != null && !text.trim().isEmpty()) {
                    java.awt.FontMetrics fm = lbl.getFontMetrics(lbl.getFont());
                    int neededWidth = fm.stringWidth(text) + 8;
                    java.awt.Rectangle b = comp.getBounds();
                    if (neededWidth > b.width) {
                        comp.setBounds(b.x, b.y, neededWidth, b.height);
                    }
                }
            }
            if (comp instanceof java.awt.Container) {
                perbaikiLebarLabel((java.awt.Container) comp);
            }
        }
    }

    /**
     * Menghitung ulang maximum x+width dari semua komponen di panel null-layout
     * lalu meng-update preferredSize panel agar scrollInput tahu berapa lebar scroll yang dibutuhkan.
     */
    private void perbaikiUkuranPanel(java.awt.Container panel) {
        int maxRight = 0;
        int maxBottom = 0;
        for (java.awt.Component comp : panel.getComponents()) {
            java.awt.Rectangle b = comp.getBounds();
            maxRight  = Math.max(maxRight,  b.x + b.width);
            maxBottom = Math.max(maxBottom, b.y + b.height);
        }
        if (maxRight > 0 || maxBottom > 0) {
            java.awt.Dimension cur = panel.getPreferredSize();
            int newW = Math.max(maxRight + 16, cur.width);
            int newH = Math.max(maxBottom + 16, cur.height);
            panel.setPreferredSize(new java.awt.Dimension(newW, newH));
            panel.revalidate();
        }
    }

    /**
     * Creates new form DlgRujuk
     * 
     * @param parent
     * @param modal
     */
    public RMPenilaianAwalKeperawatanPonek(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initIGD();
        initPosisi();
        // Dipanggil SETELAH initIGD/initPosisi agar semua komponen dinamis ikut terubah
        aturUkuranFont(internalFrame1, ukuranFont);
        initSesuaikanLabel();

        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Agama", "Bahasa", "Cacat Fisik", "Tgl.Lahir", "Tgl.Asuhan",
                "Informasi", "Keluhan Utama", "Riwayat Penyakit Dahulu", "Riwayat Penggunaan obat",
                "Status Hamil", "Gravida", "Para", "Abortus", "HPHT",
                // PRIMARY SURVEY (kolom 18-29)
                "Airway", "Breathing", "SpO2", "Nadi", "CRT", "Warna Kulit", "Circulation Perdarahan", "Turgor Kulit",
                "Respon Neurologi", "Pupil Neurologi", "Reflek", "GCS",
                // SECONDARY SURVEY (kolom 30-40)
                "Tekanan Intrakranial", "Pupil",
                "Neurosensorik/Muskuloskeletal", "Integumen", "Turgor Kulit", "Edema", "Mukosa Mulut", "Perdarahan",
                "Jml Perdarahan (cc)", "Warna Perdarahan", "Intoksikasi",
                // ELIMINASI (kolom 41-48)
                "Frekuensi BAB", "x/", "Konsistensi BAB",
                "Warna BAB", "Frekuensi BAK", "x/", "Warna BAK", "Lain-lain BAK",
                // PSIKOLOGIS & SOSIAL (kolom 49-62)
                "Kondisi Psikologis",
                "Gangguan Jiwa Di Masa Lalu", "Adakah Perilaku", "Dilaporkan Ke", "Sebutkan",
                "Hubungan Pasien Dengan Anggota Keluarga", "Status Pernikahan", "Tinggal Dengan", "Ket. Tinggal Dengan",
                "Pekerjaan", "Pembayaran", "Nilai-nilai Kebudayaan", "Ket. Nilai-nilai Kebudayaan", "Pendidikan Pasien",
                "Pendidikan PJ", "Ket. Pendidikan PJ",
                // EDUKASI & AKTIFITAS (kolom 65-70)
                "Edukasi Diberikan Kepada", "Ket. Edukasi Diberikan Kepada", "Kemampuan Aktifitas Sehari-hari",
                "Aktifitas", "Alat bantu", "Ket. Alat bantu",
                // NYERI & RESIKO JATUH (kolom 71-89)
                "Tingkat Nyeri", "Provokes", "Ket. Provokes", "Kualitas", "Ket. Kualitas", "Lokasi", "Menyebar",
                "Skala Nyeri", "Durasi", "Nyeri Hilang", "Ket. Hilang Nyeri", "Lapor Ke Dokter",
                "Jam Lapor", "Cara Berjalan A", "Cara Berjalan B", "Cara Berjalan C", "Hasil Penilaian Resiko Jatuh",
                "Lapor Dokter", "Ket. Lapor",
                // RENCANA & PETUGAS (kolom 90-92)
                "Rencana", "NIP", "Nama Petugas"
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

        for (i = 0; i < 81; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(65);
            } else if (i == 2) {
                column.setPreferredWidth(160);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(60);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(90);
            } else if (i == 7) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(120);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(200);
            } else if (i == 11) {
                column.setPreferredWidth(200);
            } else if (i == 12) {
                column.setPreferredWidth(200);
            } else if (i == 13) {
                column.setPreferredWidth(70);
            } else if (i == 14) {
                column.setPreferredWidth(50);
            } else if (i == 15) {
                column.setPreferredWidth(50);
            } else if (i == 16) {
                column.setPreferredWidth(50);
            } else if (i == 17) {
                column.setPreferredWidth(70);
            } else if (i == 18) {
                column.setPreferredWidth(110);
            } else if (i == 19) {
                column.setPreferredWidth(50);
            } else if (i == 20) {
                column.setPreferredWidth(160);
            } else if (i == 21) {
                column.setPreferredWidth(80);
            } else if (i == 22) {
                column.setPreferredWidth(67);
            } else if (i == 23) {
                column.setPreferredWidth(77);
            } else if (i == 24) {
                column.setPreferredWidth(77);
            } else if (i == 25) {
                column.setPreferredWidth(66);
            } else if (i == 26) {
                column.setPreferredWidth(107);
            } else if (i == 27) {
                column.setPreferredWidth(105);
            } else if (i == 28) {
                column.setPreferredWidth(90);
            } else if (i == 29) {
                column.setPreferredWidth(80);
            } else if (i == 30) {
                column.setPreferredWidth(65);
            } else if (i == 31) {
                column.setPreferredWidth(85);
            } else if (i == 32) {
                column.setPreferredWidth(80);
            } else if (i == 33) {
                column.setPreferredWidth(77);
            } else if (i == 34) {
                column.setPreferredWidth(65);
            } else if (i == 35) {
                column.setPreferredWidth(65);
            } else if (i == 36) {
                column.setPreferredWidth(80);
            } else if (i == 37) {
                column.setPreferredWidth(100);
            } else if (i == 38) {
                column.setPreferredWidth(147);
            } else if (i == 39) {
                column.setPreferredWidth(190);
            } else if (i == 40) {
                column.setPreferredWidth(90);
            } else if (i == 41) {
                column.setPreferredWidth(100);
            } else if (i == 42) {
                column.setPreferredWidth(220);
            } else if (i == 43) {
                column.setPreferredWidth(95);
            } else if (i == 44) {
                column.setPreferredWidth(85);
            } else if (i == 45) {
                column.setPreferredWidth(105);
            } else if (i == 46) {
                column.setPreferredWidth(100);
            } else if (i == 47) {
                column.setPreferredWidth(100);
            } else if (i == 48) {
                column.setPreferredWidth(120);
            } else if (i == 49) {
                column.setPreferredWidth(150);
            } else if (i == 50) {
                column.setPreferredWidth(97);
            } else if (i == 51) {
                column.setPreferredWidth(97);
            } else if (i == 52) {
                column.setPreferredWidth(110);
            } else if (i == 53) {
                column.setPreferredWidth(135);
            } else if (i == 54) {
                column.setPreferredWidth(155);
            } else if (i == 55) {
                column.setPreferredWidth(175);
            } else if (i == 56) {
                column.setPreferredWidth(65);
            } else if (i == 57) {
                column.setPreferredWidth(63);
            } else if (i == 58) {
                column.setPreferredWidth(97);
            } else if (i == 59) {
                column.setPreferredWidth(87);
            } else if (i == 60) {
                column.setPreferredWidth(85);
            } else if (i == 61) {
                column.setPreferredWidth(100);
            } else if (i == 62) {
                column.setPreferredWidth(90);
            } else if (i == 63) {
                column.setPreferredWidth(150);
            } else if (i == 64) {
                column.setPreferredWidth(80);
            } else if (i == 65) {
                column.setPreferredWidth(58);
            } else if (i == 66) {
                column.setPreferredWidth(65);
            } else if (i == 67) {
                column.setPreferredWidth(45);
            } else if (i == 68) {
                column.setPreferredWidth(85);
            } else if (i == 69) {
                column.setPreferredWidth(100);
            } else if (i == 70) {
                column.setPreferredWidth(85);
            } else if (i == 71) {
                column.setPreferredWidth(60);
            } else if (i == 72) {
                column.setPreferredWidth(85);
            } else if (i == 73) {
                column.setPreferredWidth(85);
            } else if (i == 74) {
                column.setPreferredWidth(85);
            } else if (i == 75) {
                column.setPreferredWidth(203);
            } else if (i == 76) {
                column.setPreferredWidth(70);
            } else if (i == 77) {
                column.setPreferredWidth(90);
            } else if (i == 78) {
                column.setPreferredWidth(210);
            } else if (i == 79) {
                column.setPreferredWidth(75);
            } else if (i == 80) {
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeMasalah = new DefaultTableModel(null, new Object[] {
                "P", "KODE", "MASALAH KEPERAWATAN"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbMasalahKeperawatan.setModel(tabModeMasalah);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(350);
            }
        }
        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeDetailMasalah = new DefaultTableModel(null, new Object[] {
                "Kode", "Masalah Keperawatan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeRencana = new DefaultTableModel(null, new Object[] {
                "P", "KODE", "RENCANA KEPERAWATAN"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbRencanaKeperawatan.setModel(tabModeRencana);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRencanaKeperawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRencanaKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbRencanaKeperawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(350);
            }
        }
        tbRencanaKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeDetailRencana = new DefaultTableModel(null, new Object[] {
                "Kode", "Rencana Keperawatan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbRencanaDetail.setModel(tabModeDetailRencana);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRencanaDetail.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRencanaDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbRencanaDetail.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(420);
            }
        }
        tbRencanaDetail.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KeluhanUtama.setDocument(new batasInput((int) 150).getKata(KeluhanUtama));
        RPD.setDocument(new batasInput((int) 100).getKata(RPD));
        RPO.setDocument(new batasInput((int) 100).getKata(RPO));
        Gravida.setDocument(new batasInput((byte) 20).getKata(Gravida));
        Para.setDocument(new batasInput((byte) 20).getKata(Para));
        Abortus.setDocument(new batasInput((byte) 20).getKata(Abortus));
        HPHT.setDocument(new batasInput((byte) 20).getKata(HPHT));
        JumlahPerdarahan.setDocument(new batasInput((byte) 5).getKata(JumlahPerdarahan));
        WarnaPerdarahan.setDocument(new batasInput((int) 40).getKata(WarnaPerdarahan));
        XBAB.setDocument(new batasInput((byte) 10).getKata(XBAB));
        XBAK.setDocument(new batasInput((byte) 10).getKata(XBAK));
        LBAK.setDocument(new batasInput((int) 40).getKata(LBAK));
        Dilaporkan.setDocument(new batasInput((int) 50).getKata(Dilaporkan));
        Sebutkan.setDocument(new batasInput((int) 50).getKata(Sebutkan));
        KetTinggal.setDocument(new batasInput((int) 50).getKata(KetTinggal));
        KetBudaya.setDocument(new batasInput((int) 50).getKata(KetBudaya));
        KetPendidikanPJ.setDocument(new batasInput((int) 50).getKata(KetPendidikanPJ));
        KetEdukasi.setDocument(new batasInput((int) 50).getKata(KetEdukasi));
        KetAlatBantu.setDocument(new batasInput((int) 50).getKata(KetAlatBantu));
        KetProvokes.setDocument(new batasInput((int) 40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int) 50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int) 50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int) 25).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int) 40).getKata(KetNyeri));
        KetDokter.setDocument(new batasInput((byte) 15).getKata(KetDokter));
        KetLapor.setDocument(new batasInput((int) 15).getKata(KetLapor));
        Rencana.setDocument(new batasInput((int) 200).getKata(Rencana));
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

            TCariMasalah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariMasalah.getText().length() > 2) {
                        tampilMasalah2();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariMasalah.getText().length() > 2) {
                        tampilMasalah2();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariMasalah.getText().length() > 2) {
                        tampilMasalah2();
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
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

        ChkAccor.setSelected(false);
        isMenu();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        Aktifitas = new widget.ComboBox();
        AlatBantu = new widget.ComboBox();
        KetAlatBantu = new widget.TextBox();
        jLabel55 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KetTinggal = new widget.TextBox();
        jLabel60 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Lapor = new widget.ComboBox();
        ATS = new widget.ComboBox();
        BJM = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Hasil = new widget.ComboBox();
        jLabel68 = new widget.Label();
        KetLapor = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel72 = new widget.Label();
        MSA = new widget.ComboBox();
        Nyeri = new widget.ComboBox();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel80 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel83 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel86 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel89 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        KetDokter = new widget.TextBox();
        TglAsuhan = new widget.Tanggal();
        jLabel94 = new widget.Label();
        jLabel51 = new widget.Label();
        CacatFisik = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel95 = new widget.Label();
        StatusBudaya = new widget.ComboBox();
        KetBudaya = new widget.TextBox();
        jLabel97 = new widget.Label();
        jLabel63 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel71 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        Bahasa = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel78 = new widget.Label();
        StatusKehamilan = new widget.ComboBox();
        jLabel29 = new widget.Label();
        Gravida = new widget.TextBox();
        jLabel32 = new widget.Label();
        Para = new widget.TextBox();
        jLabel33 = new widget.Label();
        Abortus = new widget.TextBox();
        jLabel35 = new widget.Label();
        HPHT = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel98 = new widget.Label();
        jLabel90 = new widget.Label();
        Tekanan = new widget.ComboBox();
        jLabel91 = new widget.Label();
        Pupil = new widget.ComboBox();
        Neurosensorik = new widget.ComboBox();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        Integumen = new widget.ComboBox();
        jLabel102 = new widget.Label();
        Turgor = new widget.ComboBox();
        jLabel103 = new widget.Label();
        Edema = new widget.ComboBox();
        jLabel104 = new widget.Label();
        Mukosa = new widget.ComboBox();
        jLabel105 = new widget.Label();
        Perdarahan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        JumlahPerdarahan = new widget.TextBox();
        jLabel37 = new widget.Label();
        WarnaPerdarahan = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel106 = new widget.Label();
        Intoksikasi = new widget.ComboBox();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        KBAB = new widget.ComboBox();
        jLabel109 = new widget.Label();
        BAB = new widget.ComboBox();
        jLabel110 = new widget.Label();
        XBAB = new widget.TextBox();
        jLabel111 = new widget.Label();
        WBAB = new widget.ComboBox();
        jLabel112 = new widget.Label();
        BAK = new widget.ComboBox();
        jLabel113 = new widget.Label();
        XBAK = new widget.TextBox();
        jLabel114 = new widget.Label();
        WBAK = new widget.ComboBox();
        jLabel115 = new widget.Label();
        LBAK = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        Psikologis = new widget.ComboBox();
        jLabel119 = new widget.Label();
        Jiwa = new widget.ComboBox();
        jLabel120 = new widget.Label();
        Perilaku = new widget.ComboBox();
        jLabel118 = new widget.Label();
        Dilaporkan = new widget.TextBox();
        jLabel121 = new widget.Label();
        Sebutkan = new widget.TextBox();
        jLabel122 = new widget.Label();
        Hubungan = new widget.ComboBox();
        jLabel123 = new widget.Label();
        StatusPernikahan = new widget.TextBox();
        jLabel124 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel125 = new widget.Label();
        Pembayaran = new widget.TextBox();
        jLabel126 = new widget.Label();
        PendidikanPasien = new widget.TextBox();
        jLabel127 = new widget.Label();
        PendidikanPJ = new widget.ComboBox();
        KetPendidikanPJ = new widget.TextBox();
        jLabel39 = new widget.Label();
        Informasi = new widget.ComboBox();
        Scroll8 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnAllMasalah = new widget.Button();
        BtnCariMasalah = new widget.Button();
        TCariMasalah = new widget.TextBox();
        label12 = new widget.Label();
        TabRencanaKeperawatan = new javax.swing.JTabbedPane();
        panelBiasa1 = new widget.PanelBiasa();
        Scroll9 = new widget.ScrollPane();
        tbRencanaKeperawatan = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        label13 = new widget.Label();
        TCariRencana = new widget.TextBox();
        BtnCariRencana = new widget.Button();
        BtnAllRencana = new widget.Button();
        BtnTambahRencana = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        
        tbObat = new widget.Table();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnLaporanPenilaian = new javax.swing.JMenuItem();

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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetailMasalah = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbRencanaDetail = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Penilaian Awal Keperawatan PONEK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1800));
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
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 180, 23);

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
        BtnDokter.setBounds(358, 40, 28, 23);

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
        jLabel9.setBounds(440, 90, 150, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(395, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel50.setText("d. Alat Bantu :");
        jLabel50.setName("jLabel50"); // NOI18N

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("V. SKALA NYERI");
        jLabel52.setName("jLabel52"); // NOI18N

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
        scrollPane1.setBounds(179, 90, 260, 53);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 90, 175, 20);

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
        scrollPane2.setBounds(179, 150, 260, 53);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 150, 175, 23);

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
        scrollPane4.setBounds(594, 90, 260, 53);

        Aktifitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tirah Baring", "Duduk", "Berjalan" }));
        Aktifitas.setName("Aktifitas"); // NOI18N
        Aktifitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AktifitasKeyPressed(evt);
            }
        });

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });

        KetAlatBantu.setFocusTraversalPolicyProvider(true);
        KetAlatBantu.setName("KetAlatBantu"); // NOI18N
        KetAlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAlatBantuKeyPressed(evt);
            }
        });

        jLabel55.setText("b. Aktifitas :");
        jLabel55.setName("jLabel55"); // NOI18N

        ADL.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Sebagian", "Ketergantungan Total" }));
        ADL.setSelectedIndex(3);
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });

        jLabel57.setText("a. Kemampuan Aktifitas Sehari-hari :");
        jLabel57.setName("jLabel57"); // NOI18N

        jLabel58.setText("n. Edukasi Diberikan Kepada :");
        jLabel58.setName("jLabel58"); // NOI18N

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Sendiri", "Orang Tua", "Suami / Istri", "Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });

        jLabel60.setText("h. Tinggal Dengan :");
        jLabel60.setName("jLabel60"); // NOI18N

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });

        jLabel64.setText("Jam  :");
        jLabel64.setName("jLabel64"); // NOI18N

        jLabel65.setText("1. Tidak seimbang / sempoyongan / limbung :");
        jLabel65.setName("jLabel65"); // NOI18N

        jLabel66.setText("2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel66.setName("jLabel66"); // NOI18N

        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Lapor.setName("Lapor"); // NOI18N
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });

        ATS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ATS.setName("ATS"); // NOI18N
        ATS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATSKeyPressed(evt);
            }
        });

        BJM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BJM.setName("BJM"); // NOI18N
        BJM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJMKeyPressed(evt);
            }
        });

        jLabel67.setText("Menyebar :");
        jLabel67.setName("jLabel67"); // NOI18N

        Hasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)",
                "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });

        jLabel68.setText("Hasil :");
        jLabel68.setName("jLabel68"); // NOI18N

        KetLapor.setFocusTraversalPolicyProvider(true);
        KetLapor.setName("KetLapor"); // NOI18N
        KetLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporKeyPressed(evt);
            }
        });

        jLabel70.setText(
                "b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang :");
        jLabel70.setName("jLabel70"); // NOI18N

        jLabel72.setText("a. Cara Berjalan :");
        jLabel72.setName("jLabel72"); // NOI18N

        MSA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MSA.setName("MSA"); // NOI18N
        MSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MSAKeyPressed(evt);
            }
        });

        Nyeri.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });

        Provokes.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-lain" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });

        jLabel80.setText("Penyebab :");
        jLabel80.setName("jLabel80"); // NOI18N

        Quality.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-lain" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });

        jLabel81.setText("Kualitas :");
        jLabel81.setName("jLabel81"); // NOI18N

        jLabel82.setText("Wilayah :");
        jLabel82.setName("jLabel82"); // NOI18N

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });

        jLabel86.setText("Diberitahukan pada dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N

        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N

        NyeriHilang.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-09-2025 10:17:24" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(456, 40, 130, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. RIWAYAT PSIKOLOGIS - SOSIAL - EKONOMI - BUDAYA - SPIRITUAL");
        jLabel94.setName("jLabel94"); // NOI18N

        jLabel51.setText("c. Cacat Fisik :");
        jLabel51.setName("jLabel51"); // NOI18N

        CacatFisik.setEditable(false);
        CacatFisik.setFocusTraversalPolicyProvider(true);
        CacatFisik.setName("CacatFisik"); // NOI18N

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. PENGKAJIAN FUNGSI");
        jLabel56.setName("jLabel56"); // NOI18N

        jLabel95.setText("l. Kepercayaan / Budaya / Nilai-nilai Khusus Yang Perlu Diperhatikan :");
        jLabel95.setName("jLabel95"); // NOI18N

        StatusBudaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusBudaya.setName("StatusBudaya"); // NOI18N
        StatusBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusBudayaKeyPressed(evt);
            }
        });

        KetBudaya.setFocusTraversalPolicyProvider(true);
        KetBudaya.setName("KetBudaya"); // NOI18N
        KetBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBudayaKeyPressed(evt);
            }
        });

        jLabel97.setText("Dilaporkan kepada dokter ?");
        jLabel97.setName("jLabel97"); // NOI18N

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("VI. PENILAIAN RESIKO JATUH (GET UP AND GO)");
        jLabel63.setName("jLabel63"); // NOI18N

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N

        jLabel71.setText("Jam dilaporkan :");
        jLabel71.setName("jLabel71"); // NOI18N

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N

        jLabel76.setText("i. Bahasa Sehari-hari :");
        jLabel76.setName("jLabel76"); // NOI18N

        jLabel77.setText("k. Agama :");
        jLabel77.setName("jLabel77"); // NOI18N

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N

        jLabel78.setText("Status Kehamilan :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(440, 150, 106, 23);

        StatusKehamilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Hamil", "Hamil" }));
        StatusKehamilan.setName("StatusKehamilan"); // NOI18N
        StatusKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(StatusKehamilan);
        StatusKehamilan.setBounds(550, 150, 110, 23);

        jLabel29.setText("Gravida :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(715, 180, 50, 23);

        Gravida.setHighlighter(null);
        Gravida.setName("Gravida"); // NOI18N
        Gravida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GravidaKeyPressed(evt);
            }
        });
        FormInput.add(Gravida);
        Gravida.setBounds(769, 180, 85, 23);

        jLabel32.setText("Para :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(440, 180, 43, 23);

        Para.setHighlighter(null);
        Para.setName("Para"); // NOI18N
        Para.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParaKeyPressed(evt);
            }
        });
        FormInput.add(Para);
        Para.setBounds(487, 180, 85, 23);

        jLabel33.setText("Abortus :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(575, 180, 50, 23);

        Abortus.setHighlighter(null);
        Abortus.setName("Abortus"); // NOI18N
        Abortus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbortusKeyPressed(evt);
            }
        });
        FormInput.add(Abortus);
        Abortus.setBounds(629, 180, 85, 23);

        jLabel35.setText("HPHT :");
        jLabel35.setName("jLabel35"); // NOI18N
        // HPHT label and textbox removed from display (moved to Midwifery Assessment section)

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 210, 880, 1);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("I. RIWAYAT KESEHATAN PASIEN");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(10, 70, 180, 23);

        jLabel90.setText("Tekanan Intrakranial :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(0, 230, 150, 23);

        Tekanan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "TAK", "Sakit Kepala", "Muntah", "Pusing", "Bingung" }));
        Tekanan.setName("Tekanan"); // NOI18N
        Tekanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TekananKeyPressed(evt);
            }
        });
        FormInput.add(Tekanan);
        Tekanan.setBounds(154, 230, 112, 23);

        jLabel91.setText("Pupil :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(295, 230, 50, 23);

        Pupil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Miosis", "Isokor", "Anisokor" }));
        Pupil.setName("Pupil"); // NOI18N
        Pupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilKeyPressed(evt);
            }
        });
        FormInput.add(Pupil);
        Pupil.setBounds(349, 230, 93, 23);

        Neurosensorik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Spasme Otot",
                "Perubahan Sensorik", "Perubahan Motorik", "Perubahan Bentuk Ekstremitas",
                "Penurunan Tingkat Kesadaran", "Fraktur/Dislokasi", "Luksasio", "Kerusakan Jaringan/Luka" }));
        Neurosensorik.setName("Neurosensorik"); // NOI18N
        Neurosensorik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurosensorikKeyPressed(evt);
            }
        });
        FormInput.add(Neurosensorik);
        Neurosensorik.setBounds(654, 230, 200, 23);

        jLabel100.setText("Neurosensorik / Muskuloskeletal :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(470, 230, 180, 23);

        jLabel101.setText("Integumen :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 260, 150, 23);

        Integumen.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "TAK", "Luka Bakar", "Luka Robek", "Lecet", "Luka Decubitus", "Luka Gangren" }));
        Integumen.setName("Integumen"); // NOI18N
        Integumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumenKeyPressed(evt);
            }
        });
        FormInput.add(Integumen);
        Integumen.setBounds(154, 260, 125, 23);

        jLabel102.setText("Turgor Kulit :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(290, 260, 80, 23);

        Turgor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Menurun" }));
        Turgor.setName("Turgor"); // NOI18N
        Turgor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TurgorKeyPressed(evt);
            }
        });
        FormInput.add(Turgor);
        Turgor.setBounds(374, 260, 93, 23);

        jLabel103.setText("Edema :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(486, 260, 50, 23);

        Edema.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Tidak Ada", "Ekstremitas", "Seluruh Tubuh", "Asites", "Palpebrae" }));
        Edema.setName("Edema"); // NOI18N
        Edema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdemaKeyPressed(evt);
            }
        });
        FormInput.add(Edema);
        Edema.setBounds(540, 260, 120, 23);

        jLabel104.setText("Mukosa Mulut :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(670, 260, 90, 23);

        Mukosa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lembab", "Kering" }));
        Mukosa.setName("Mukosa"); // NOI18N
        Mukosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukosaKeyPressed(evt);
            }
        });
        FormInput.add(Mukosa);
        Mukosa.setBounds(764, 260, 90, 23);

        jLabel105.setText("Perdarahan :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(0, 290, 150, 23);

        Perdarahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        Perdarahan.setName("Perdarahan"); // NOI18N
        Perdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(Perdarahan);
        Perdarahan.setBounds(154, 290, 100, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("cc");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(379, 290, 20, 23);

        JumlahPerdarahan.setHighlighter(null);
        JumlahPerdarahan.setName("JumlahPerdarahan"); // NOI18N
        JumlahPerdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahPerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(JumlahPerdarahan);
        JumlahPerdarahan.setBounds(306, 290, 70, 23);

        jLabel37.setText(", Warna :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(386, 290, 50, 23);

        WarnaPerdarahan.setHighlighter(null);
        WarnaPerdarahan.setName("WarnaPerdarahan"); // NOI18N
        WarnaPerdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaPerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(WarnaPerdarahan);
        WarnaPerdarahan.setBounds(440, 290, 170, 23);

        jLabel38.setText(", Jumlah :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(252, 290, 50, 23);

        // jLabel106 (X/) hidden for Combobox format
        // jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        // jLabel106.setText("X/");
        // jLabel106.setName("jLabel106"); // NOI18N
        // FormInput.add(jLabel106);
        // jLabel106.setBounds(282, 340, 13, 23);

        Intoksikasi.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Tidak Ada", "Ada", "Gigitan Binatang", "Zat Kimia", "Gas", "Obat" }));
        Intoksikasi.setSelectedIndex(2);
        Intoksikasi.setName("Intoksikasi"); // NOI18N
        Intoksikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntoksikasiKeyPressed(evt);
            }
        });
        FormInput.add(Intoksikasi);
        Intoksikasi.setBounds(719, 290, 135, 23);

        jLabel107.setText("Intoksikasi :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(625, 290, 90, 23);

        jLabel108.setText("Eliminasi :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(0, 320, 150, 23);

        KBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lunak", "Tidak Lunak" }));
        KBAB.setName("KBAB"); // NOI18N
        KBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KBABKeyPressed(evt);
            }
        });
        FormInput.add(KBAB);
        KBAB.setBounds(443, 340, 150, 23);

        jLabel109.setText("Konsistensi :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(369, 340, 70, 23);

        BAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 kali sehari", "1 kali sehari", "2 kali sehari", "3 kali sehari" }));
        BAB.setName("BAB"); // NOI18N
        BAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BABKeyPressed(evt);
            }
        });
        FormInput.add(BAB);
        BAB.setBounds(229, 340, 137, 23); // wider to fit text

        jLabel110.setText("BAB : Frekuensi :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(135, 340, 90, 23);

        XBAB.setHighlighter(null);
        XBAB.setName("XBAB"); // NOI18N
        XBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XBABKeyPressed(evt);
            }
        });
        // FormInput.add(XBAB); // Hidden
        // XBAB.setBounds(298, 340, 70, 23);

        jLabel111.setText("Warna :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(620, 340, 55, 23);

        WBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Khas", "Tidak Khas" }));
        WBAB.setName("WBAB"); // NOI18N
        WBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WBABKeyPressed(evt);
            }
        });
        FormInput.add(WBAB);
        WBAB.setBounds(679, 340, 130, 23);

        jLabel112.setText("BAK : Frekuensi :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(135, 370, 90, 23);

        BAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1-3 kali", "4-6 kali", "6-8 kali" }));
        BAK.setName("BAK"); // NOI18N
        BAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BAKKeyPressed(evt);
            }
        });
        FormInput.add(BAK);
        BAK.setBounds(229, 370, 110, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("X/");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(282, 370, 13, 23);

        XBAK.setHighlighter(null);
        XBAK.setName("XBAK"); // NOI18N
        XBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XBAKKeyPressed(evt);
            }
        });
        // FormInput.add(XBAK);
        XBAK.setBounds(298, 370, 70, 23);

        jLabel114.setText("Warna :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(369, 370, 70, 23);

        WBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Khas", "Tidak Khas" }));
        WBAK.setName("WBAK"); // NOI18N
        WBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WBAKKeyPressed(evt);
            }
        });
        FormInput.add(WBAK);
        WBAK.setBounds(443, 370, 130, 23);

        jLabel115.setText("Lain-lain :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(620, 370, 55, 23);

        LBAK.setHighlighter(null);
        LBAK.setName("LBAK"); // NOI18N
        LBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LBAKKeyPressed(evt);
            }
        });
        FormInput.add(LBAK);
        LBAK.setBounds(679, 370, 175, 23);

        // ============================================================
        // ASSESMEN KEBIDANAN & KANDUNGAN
        // ============================================================
        jSeparatorKebidanan = new javax.swing.JSeparator();
        lblKebidananTitle = new widget.Label();
        lblDataSubjektif = new widget.Label();
        lblRiwayatMenstruasi = new widget.Label();
        lblMenarche = new widget.Label();
        MenarcheUmur = new widget.TextBox();
        lblMenarcheTahun = new widget.Label();
        lblSiklus = new widget.Label();
        SiklusMenstruasi = new widget.TextBox();
        lblSiklusHari = new widget.Label();
        KeteratanMenstruasi = new widget.ComboBox();
        lblLama = new widget.Label();
        LamaMenstruasi = new widget.TextBox();
        lblLamaHari = new widget.Label();
        KeluhanHaid = new widget.TextBox();
        lblRiwayatKehamilanSekarang = new widget.Label();
        lblHamilKe = new widget.Label();
        HamilKe = new widget.TextBox();
        lblUK = new widget.Label();
        UKMinggu = new widget.TextBox();
        lblUKMinggu = new widget.Label();
        UKHari = new widget.TextBox();
        lblUKHari = new widget.Label();
        lblHPHTKbd = new widget.Label();
        HPHTKbd = new widget.TextBox();
        lblHPL = new widget.Label();
        HPLKbd = new widget.TextBox();
        lblBBSebelum = new widget.Label();
        BBSebelumHamil = new widget.TextBox();
        lblBBSebelumKg = new widget.Label();
        lblBBSekarang = new widget.Label();
        BBSekarang = new widget.TextBox();
        lblBBSekarangKg = new widget.Label();
        lblTBKbd = new widget.Label();
        TBKbd = new widget.TextBox();
        lblTBCm = new widget.Label();
        lblPeriksaANC = new widget.Label();
        PeriksaANC = new widget.TextBox();
        lblANCKali = new widget.Label();
        ANCDi = new widget.ComboBox();
        lblANCLainnya = new widget.Label();
        ANCLainnya = new widget.TextBox();
        lblImunisasiTT = new widget.Label();
        ImunisasiTT = new widget.TextBox();
        lblTTKali = new widget.Label();
        lblTglTT1 = new widget.Label();
        TglTT1 = new widget.TextBox();
        lblTglTT2 = new widget.Label();
        TglTT2 = new widget.TextBox();
        lblGerakanJanin = new widget.Label();
        GerakanJanin = new widget.TextBox();
        lblGerakanKali = new widget.Label();
        lblRiwayatKehamilanLalu = new widget.Label();

        // ALIGN HEADERS LEFT
        lblKebidananTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDataSubjektif.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRiwayatMenstruasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRiwayatKehamilanSekarang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRiwayatKehamilanLalu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        // UNITS SHOULD BE LEFT ALIGNED SO THEY SIT RIGHT NEXT TO THE FIELDS
        lblMenarcheTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSiklusHari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUKMinggu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUKHari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBBSebelumKg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBBSekarangKg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTBCm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTTKali.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGerakanKali.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jSeparatorKebidanan.setForeground(new java.awt.Color(99, 104, 114));
        FormInput.add(jSeparatorKebidanan);
        jSeparatorKebidanan.setBounds(0, 400, 880, 2);

        lblKebidananTitle.setText("ASSESMEN KEBIDANAN & KANDUNGAN");
        FormInput.add(lblKebidananTitle);
        lblKebidananTitle.setBounds(10, 405, 300, 23);

        lblDataSubjektif.setText("A. DATA SUBJEKTIF");
        FormInput.add(lblDataSubjektif);
        lblDataSubjektif.setBounds(15, 428, 200, 23);

        lblRiwayatMenstruasi.setText("Riwayat Menstruasi :");
        FormInput.add(lblRiwayatMenstruasi);
        lblRiwayatMenstruasi.setBounds(25, 451, 150, 23);

        // ==== MASTER GRID LAYOUT ====
        // Col 1 Colon: x=230 -> Col 1 Field: x=234
        // Col 2 Colon: x=490 -> Col 2 Field: x=494
        // Col 3 Colon: x=690 -> Col 3 Field: x=694
        
        // ROW 1: Menarche
        lblMenarche.setText("Menarche umur :");
        FormInput.add(lblMenarche);
        lblMenarche.setBounds(0, 474, 230, 23);
        FormInput.add(MenarcheUmur);
        MenarcheUmur.setBounds(234, 474, 50, 23);
        lblMenarcheTahun.setText("tahun");
        FormInput.add(lblMenarcheTahun);
        lblMenarcheTahun.setBounds(288, 474, 40, 23);
        
        lblSiklus.setText("siklus :");
        FormInput.add(lblSiklus);
        lblSiklus.setBounds(330, 474, 160, 23); // ends at 490
        FormInput.add(SiklusMenstruasi);
        SiklusMenstruasi.setBounds(494, 474, 50, 23);
        lblSiklusHari.setText("hari");
        FormInput.add(lblSiklusHari);
        lblSiklusHari.setBounds(548, 474, 30, 23);
        KeteratanMenstruasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        FormInput.add(KeteratanMenstruasi);
        KeteratanMenstruasi.setBounds(582, 474, 120, 23);

        // ROW 2: Lama
        lblLama.setText("Lama :");
        FormInput.add(lblLama);
        lblLama.setBounds(0, 499, 230, 23);
        FormInput.add(LamaMenstruasi);
        LamaMenstruasi.setBounds(234, 499, 50, 23);
        
        lblLamaHari.setText("hari, Keluhan saat haid :");
        FormInput.add(lblLamaHari);
        lblLamaHari.setBounds(290, 499, 200, 23); // ends at 490
        FormInput.add(KeluhanHaid);
        KeluhanHaid.setBounds(494, 499, 360, 23);

        lblRiwayatKehamilanSekarang.setText("Riwayat Kehamilan Sekarang :");
        FormInput.add(lblRiwayatKehamilanSekarang);
        lblRiwayatKehamilanSekarang.setBounds(25, 527, 210, 23);

        // ROW 3: Hamil Ke
        lblHamilKe.setText("Hamil ke- :");
        FormInput.add(lblHamilKe);
        lblHamilKe.setBounds(0, 550, 230, 23);
        FormInput.add(HamilKe);
        HamilKe.setBounds(234, 550, 50, 23);
        
        lblUK.setText("UK :");
        FormInput.add(lblUK);
        lblUK.setBounds(290, 550, 200, 23); // ends at 490
        FormInput.add(UKMinggu);
        UKMinggu.setBounds(494, 550, 50, 23);
        lblUKMinggu.setText("Minggu");
        FormInput.add(lblUKMinggu);
        lblUKMinggu.setBounds(548, 550, 45, 23);
        FormInput.add(UKHari);
        UKHari.setBounds(597, 550, 50, 23);
        lblUKHari.setText("Hari");
        FormInput.add(lblUKHari);
        lblUKHari.setBounds(651, 550, 40, 23);

        // ROW 4: HPHT
        lblHPHTKbd.setText("HPHT :");
        FormInput.add(lblHPHTKbd);
        lblHPHTKbd.setBounds(0, 575, 230, 23);
        FormInput.add(HPHTKbd);
        HPHTKbd.setBounds(234, 575, 170, 23);
        
        lblHPL.setText("HPL :");
        FormInput.add(lblHPL);
        lblHPL.setBounds(410, 575, 80, 23); // ends at 490
        FormInput.add(HPLKbd);
        HPLKbd.setBounds(494, 575, 170, 23);

        // ROW 5: BB Sebelum
        lblBBSebelum.setText("BB sebelum hamil :");
        FormInput.add(lblBBSebelum);
        lblBBSebelum.setBounds(0, 600, 230, 23);
        FormInput.add(BBSebelumHamil);
        BBSebelumHamil.setBounds(234, 600, 50, 23);
        lblBBSebelumKg.setText("Kg");
        FormInput.add(lblBBSebelumKg);
        lblBBSebelumKg.setBounds(288, 600, 25, 23);
        
        lblBBSekarang.setText("BB sekarang :");
        FormInput.add(lblBBSekarang);
        lblBBSekarang.setBounds(315, 600, 175, 23); // ends at 490
        FormInput.add(BBSekarang);
        BBSekarang.setBounds(494, 600, 50, 23);
        lblBBSekarangKg.setText("Kg");
        FormInput.add(lblBBSekarangKg);
        lblBBSekarangKg.setBounds(548, 600, 25, 23);
        
        lblTBKbd.setText("TB :");
        FormInput.add(lblTBKbd);
        lblTBKbd.setBounds(575, 600, 115, 23); // ends at 690
        FormInput.add(TBKbd);
        TBKbd.setBounds(694, 600, 50, 23);
        lblTBCm.setText("cm");
        FormInput.add(lblTBCm);
        lblTBCm.setBounds(748, 600, 25, 23);

        // ROW 6: Periksa ANC
        lblPeriksaANC.setText("Periksa ANC :");
        FormInput.add(lblPeriksaANC);
        lblPeriksaANC.setBounds(0, 625, 230, 23);
        FormInput.add(PeriksaANC);
        PeriksaANC.setBounds(234, 625, 50, 23);
        
        lblANCKali.setText("kali, Di :");
        FormInput.add(lblANCKali);
        lblANCKali.setBounds(290, 625, 200, 23); // ends at 490
        ANCDi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dokter Kandungan", "Bidan", "Lainnya" }));
        FormInput.add(ANCDi);
        ANCDi.setBounds(494, 625, 140, 23);
        
        lblANCLainnya.setText("Lainnya :");
        FormInput.add(lblANCLainnya);
        lblANCLainnya.setBounds(638, 625, 52, 23); // ends at 690
        FormInput.add(ANCLainnya);
        ANCLainnya.setBounds(694, 625, 160, 23);

        // ROW 7: Imunisasi TT
        lblImunisasiTT.setText("Imunisasi TT :");
        FormInput.add(lblImunisasiTT);
        lblImunisasiTT.setBounds(0, 650, 230, 23);
        FormInput.add(ImunisasiTT);
        ImunisasiTT.setBounds(234, 650, 50, 23);
        lblTTKali.setText("kali");
        FormInput.add(lblTTKali);
        lblTTKali.setBounds(288, 650, 30, 23);
        
        lblTglTT1.setText("Tgl TT I :");
        FormInput.add(lblTglTT1);
        lblTglTT1.setBounds(320, 650, 170, 23); // ends at 490
        FormInput.add(TglTT1);
        TglTT1.setBounds(494, 650, 130, 23);
        
        lblTglTT2.setText("TT II :");
        FormInput.add(lblTglTT2);
        lblTglTT2.setBounds(628, 650, 62, 23); // ends at 690
        FormInput.add(TglTT2);
        TglTT2.setBounds(694, 650, 160, 23);

        // ROW 8: Gerakan Janin
        lblGerakanJanin.setText("Gerakan janin dalam 12 jam terakhir :");
        FormInput.add(lblGerakanJanin);
        lblGerakanJanin.setBounds(0, 675, 230, 23);
        FormInput.add(GerakanJanin);
        GerakanJanin.setBounds(234, 675, 50, 23);
        lblGerakanKali.setText("kali");
        FormInput.add(lblGerakanKali);
        lblGerakanKali.setBounds(288, 675, 40, 23);

        // ---- Riwayat kehamilan terdahulu ----
        lblRiwayatKehamilanLalu.setText("Riwayat kehamilan, persalinan dan nifas yang lalu :");
        FormInput.add(lblRiwayatKehamilanLalu);
        lblRiwayatKehamilanLalu.setBounds(25, 703, 380, 23);

        // ---- TABLE ----
        tbRiwayatKehamilan = new javax.swing.JTable();
        tbRiwayatKehamilan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null,null,null,null,null,null,null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null,null,null,null,null,null,null}
            },
            new String [] {
                "<html><center>No</center></html>",
                "<html><center>Tgl<br>Partus</center></html>",
                "<html><center><font size=2><b>Umur Hamil</b></font><br>Abortus</center></html>",
                "<html><center><font size=2><b>Umur Hamil</b></font><br>Prematur</center></html>",
                "<html><center><font size=2><b>Umur Hamil</b></font><br>Aterm</center></html>",
                "<html><center>Jenis<br>Partus</center></html>",
                "<html><center><font size=2><b>Penolong</b></font><br>Nakes</center></html>",
                "<html><center><font size=2><b>Penolong</b></font><br>Non</center></html>",
                "<html><center><font size=2><b>Anak</b></font><br>Jk</center></html>",
                "<html><center><font size=2><b>Anak</b></font><br>BBL</center></html>",
                "<html><center><font size=2><b>Kehidupan</b></font><br>Normal</center></html>",
                "<html><center><font size=2><b>Kehidupan</b></font><br>Cacat</center></html>",
                "<html><center><font size=2><b>Kehidupan</b></font><br>Meninggal</center></html>",
                "<html><center>Ket/<br>Komplikasi</center></html>"
            }
        ));
        tbRiwayatKehamilan.setRowHeight(23);
        tbRiwayatKehamilan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbRiwayatKehamilan.getTableHeader().setReorderingAllowed(false);
        tbRiwayatKehamilan.getTableHeader().setPreferredSize(new java.awt.Dimension(840, 45));

        lblObjektifTitle = new widget.Label();
        lblPmxUmum = new widget.Label();
        lblKUPonek = new widget.Label();
        ObjKU = new widget.TextBox();
        lblKesadaranPonek = new widget.Label();
        ObjKesadaran = new widget.TextBox();
        lblGCSPonek = new widget.Label();
        lblGCSE = new widget.Label();
        ObjGCSE = new widget.TextBox();
        lblGCSV = new widget.Label();
        ObjGCSV = new widget.TextBox();
        lblGCSM = new widget.Label();
        ObjGCSM = new widget.TextBox();
        lblTDPonek = new widget.Label();
        ObjTDSistol = new widget.TextBox();
        lblTDSlash = new widget.Label();
        ObjTDDiastol = new widget.TextBox();
        lblTDMmHg = new widget.Label();
        lblHRPonek = new widget.Label();
        ObjHR = new widget.TextBox();
        lblHRXMenit = new widget.Label();
        lblRRPonek = new widget.Label();
        ObjRR = new widget.TextBox();
        lblRRXMenit = new widget.Label();
        lblSPonek = new widget.Label();
        ObjSuhu = new widget.TextBox();
        lblSC = new widget.Label();
        lblSpO2Ponek = new widget.Label();
        ObjSpO2 = new widget.TextBox();
        lblSpO2Persen = new widget.Label();
        
        lblPmxFisik = new widget.Label();
        lblKepala = new widget.Label();
        ObjKepala = new widget.ComboBox();
        ObjKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rambut warna hitam mengkilat", "Tidak ada ketombe", "Tidak rontok" }));
        lblMata = new widget.Label();
        ObjMata = new widget.ComboBox();
        ObjMata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simetris", "Sclera bening", "Konjungtiva merah muda", "Tidak ada kelainan mata" }));
        lblLeher = new widget.Label();
        ObjLeher = new widget.ComboBox();
        ObjLeher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada pembengkakan kelenjar tiroid", "Tidak ada limfadenitis" }));
        lblThorax = new widget.Label();
        ObjThorax = new widget.ComboBox();
        ObjThorax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak normal" }));
        lblAbdomen = new widget.Label();
        ObjAbdomen = new widget.ComboBox();
        ObjAbdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tidak ada bekas luka operasi/jahitan", "Ada luka operasi/jahitan" }));
        lblInspeksi = new widget.Label();
        ObjInspeksi = new widget.ComboBox();
        ObjInspeksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Simetris" }));
        lblPalpasiTFU = new widget.Label();
        ObjTFU = new widget.ComboBox();
        ObjTFU.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ballottement", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        lblTFUcm = new widget.Label();
        lblLeopold1 = new widget.Label();
        ObjLeopold1 = new widget.ComboBox();
        ObjLeopold1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Kepala janin teraba di bagian fundus, keras, bundar dan melenting", "Bokong janin teraba di bagian fundus, lunak, kurang bundar dan kurang melenting", "Fundus kosong, posisi janin melintang pada rahim" }));
        lblLeopold2 = new widget.Label();
        ObjLeopold2 = new widget.ComboBox();
        ObjLeopold2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Menentukan dimana letak punggung ataupun kaki janin pada kedua sisi perut ibu bagian punggung akan teraba jelas rata cembung kaku/tidak dapat digerakkan bagian-bagian kecil (tangan dan kaki) akan teraba kecil, bentuk/posisi tidak jelas dan menonjol, kemungkinan teraba gerakan kaki janin secara aktif maupun pasif" }));
        lblLeopold3 = new widget.Label();
        ObjLeopold3 = new widget.ComboBox();
        ObjLeopold3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Menentukan bagian kepala sudah masuk PAP atau belum", "Menentukan bagian bokong sudah masuk PAP atau belum" }));
        lblLeopold4 = new widget.Label();
        ObjLeopold4 = new widget.ComboBox();
        ObjLeopold4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Bagian kepala sudah masuk PAP", "Bagian kepala belum masuk PAP", "Bagian bokong sudah masuk PAP", "Bagian bokong belum masuk PAP" }));
        lblTBBJ = new widget.Label();
        ObjTBBJ = new widget.TextBox();
        lblHis = new widget.Label();
        ObjHis = new widget.TextBox();
        lblAuskultasi = new widget.Label();
        ObjAuskultasi = new widget.TextBox();
        
        lblGenitalia = new widget.Label();
        ObjPukul = new widget.TextBox();
        lblPukulWIB2 = new widget.Label();
        lblPengeluaran = new widget.Label();
        ObjPengeluaran = new widget.TextBox();
        lblPmxDalam = new widget.Label();
        ObjPmxDalam = new widget.TextBox();
        lblInspekulo = new widget.Label();
        ObjInspekulo = new widget.TextBox();
        
        lblEkstremitas = new widget.Label();
        ObjOedema1 = new widget.TextBox();
        lblOedemaSlash = new widget.Label();
        ObjOedema2 = new widget.TextBox();
        lblOedemaVarises = new widget.Label();
        ObjVarises1 = new widget.TextBox();
        lblVarisesSlash = new widget.Label();
        ObjVarises2 = new widget.TextBox();
        lblVarisesReflek = new widget.Label();
        ObjReflek1 = new widget.TextBox();
        lblReflekSlash = new widget.Label();
        ObjReflek2 = new widget.TextBox();

        scrollRiwayatKehamilan = new javax.swing.JScrollPane();
        scrollRiwayatKehamilan.setViewportView(tbRiwayatKehamilan);
        FormInput.add(scrollRiwayatKehamilan);
        scrollRiwayatKehamilan.setBounds(20, 728, 840, 170);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("II. PEMERIKSAAN FISIK");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(10, 210, 180, 23);

        jLabel117.setText("a. Kondisi Psikologis :");
        jLabel117.setName("jLabel117"); // NOI18N

        Psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Marah", "Takut",
                "Depresi", "Cepat Lelah", "Cemas", "Gelisah", "Lain-lain" }));
        Psikologis.setName("Psikologis"); // NOI18N
        Psikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PsikologisKeyPressed(evt);
            }
        });

        jLabel119.setText("b. Gangguan Jiwa Di Masa Lalu :");
        jLabel119.setName("jLabel119"); // NOI18N

        Jiwa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Jiwa.setName("Jiwa"); // NOI18N
        Jiwa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JiwaKeyPressed(evt);
            }
        });

        jLabel120.setText("c. Status Pernikahan :");
        jLabel120.setName("jLabel120"); // NOI18N

        Perilaku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Perilaku Kekerasan", "Gangguan Efek",
                "Gangguan Memori", "Halusinasi", "Kecenderungan Percobaan Bunuh Diri", "Lainnya", "-" }));
        Perilaku.setSelectedIndex(6);
        Perilaku.setName("Perilaku"); // NOI18N
        Perilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerilakuKeyPressed(evt);
            }
        });

        jLabel118.setText(", Dilaporkan Ke :");
        jLabel118.setName("jLabel118"); // NOI18N

        Dilaporkan.setHighlighter(null);
        Dilaporkan.setName("Dilaporkan"); // NOI18N
        Dilaporkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DilaporkanKeyPressed(evt);
            }
        });
        Dilaporkan.getAccessibleContext().setAccessibleName("");

        jLabel121.setText(", Sebutkan :");
        jLabel121.setName("jLabel121"); // NOI18N

        Sebutkan.setHighlighter(null);
        Sebutkan.setName("Sebutkan"); // NOI18N
        Sebutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SebutkanKeyPressed(evt);
            }
        });
        Sebutkan.getAccessibleContext().setAccessibleName("");

        jLabel122.setText("e. Hubungan Pasien Dengan Anggota Keluarga :");
        jLabel122.setName("jLabel122"); // NOI18N

        Hubungan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Harmonis", "Kurang Harmonis", "Tidak Harmonis", "Konflik Besar" }));
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });

        jLabel123.setText("d. Adakah Perilaku :");
        jLabel123.setName("jLabel123"); // NOI18N

        StatusPernikahan.setEditable(false);
        StatusPernikahan.setHighlighter(null);
        StatusPernikahan.setName("StatusPernikahan"); // NOI18N
        StatusPernikahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusPernikahanActionPerformed(evt);
            }
        });

        jLabel124.setText("f. Pekerjaan :");
        jLabel124.setName("jLabel124"); // NOI18N

        Pekerjaan.setEditable(false);
        Pekerjaan.setHighlighter(null);
        Pekerjaan.setName("Pekerjaan"); // NOI18N

        jLabel125.setText("g. Pembayaran :");
        jLabel125.setName("jLabel125"); // NOI18N

        Pembayaran.setEditable(false);
        Pembayaran.setHighlighter(null);
        Pembayaran.setName("Pembayaran"); // NOI18N

        jLabel126.setText("j. Pendidikan :");
        jLabel126.setName("jLabel126"); // NOI18N

        PendidikanPasien.setEditable(false);
        PendidikanPasien.setFocusTraversalPolicyProvider(true);
        PendidikanPasien.setName("PendidikanPasien"); // NOI18N

        jLabel127.setText("m. Pendidikan P.J. :");
        jLabel127.setName("jLabel127"); // NOI18N

        PendidikanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA",
                "SLTA/SEDERAJAT", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        PendidikanPJ.setName("PendidikanPJ"); // NOI18N
        PendidikanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanPJKeyPressed(evt);
            }
        });

        KetPendidikanPJ.setFocusTraversalPolicyProvider(true);
        KetPendidikanPJ.setName("KetPendidikanPJ"); // NOI18N
        KetPendidikanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPendidikanPJKeyPressed(evt);
            }
        });

        jLabel39.setText("Informasi didapat dari :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(592, 40, 130, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        tbMasalahKeperawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMasalahKeperawatanMouseClicked(evt);
            }
        });
        tbMasalahKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMasalahKeperawatanKeyReleased(evt);
            }
        });
        Scroll8.setViewportView(tbMasalahKeperawatan);


        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });

        BtnAllMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllMasalah.setMnemonic('2');
        BtnAllMasalah.setToolTipText("2Alt+2");
        BtnAllMasalah.setName("BtnAllMasalah"); // NOI18N
        BtnAllMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllMasalahActionPerformed(evt);
            }
        });
        BtnAllMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllMasalahKeyPressed(evt);
            }
        });

        BtnCariMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMasalah.setMnemonic('1');
        BtnCariMasalah.setToolTipText("Alt+1");
        BtnCariMasalah.setName("BtnCariMasalah"); // NOI18N
        BtnCariMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMasalahActionPerformed(evt);
            }
        });
        BtnCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariMasalahKeyPressed(evt);
            }
        });

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N

        TabRencanaKeperawatan.setBackground(new java.awt.Color(255, 255, 254));
        TabRencanaKeperawatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TabRencanaKeperawatan.setForeground(new java.awt.Color(50, 50, 50));
        TabRencanaKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRencanaKeperawatan.setName("TabRencanaKeperawatan"); // NOI18N

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbRencanaKeperawatan.setName("tbRencanaKeperawatan"); // NOI18N
        Scroll9.setViewportView(tbRencanaKeperawatan);

        panelBiasa1.add(Scroll9, java.awt.BorderLayout.CENTER);

        TabRencanaKeperawatan.addTab("Rencana Keperawatan", panelBiasa1);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.setOpaque(true);
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        TabRencanaKeperawatan.addTab("Rencana Keperawatan Lainnya", scrollPane5);


        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N

        TCariRencana.setToolTipText("Alt+C");
        TCariRencana.setName("TCariRencana"); // NOI18N
        TCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRencanaKeyPressed(evt);
            }
        });

        BtnCariRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRencana.setMnemonic('1');
        BtnCariRencana.setToolTipText("Alt+1");
        BtnCariRencana.setName("BtnCariRencana"); // NOI18N
        BtnCariRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRencanaActionPerformed(evt);
            }
        });
        BtnCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRencanaKeyPressed(evt);
            }
        });

        BtnAllRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRencana.setMnemonic('2');
        BtnAllRencana.setToolTipText("2Alt+2");
        BtnAllRencana.setName("BtnAllRencana"); // NOI18N
        BtnAllRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRencanaActionPerformed(evt);
            }
        });
        BtnAllRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllRencanaKeyPressed(evt);
            }
        });

        BtnTambahRencana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahRencana.setMnemonic('3');
        BtnTambahRencana.setToolTipText("Alt+3");
        BtnTambahRencana.setName("BtnTambahRencana"); // NOI18N
        BtnTambahRencana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahRencanaActionPerformed(evt);
            }
        });

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
        
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setComponentPopupMenu(jPopupMenu1);

        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    if (tbObat.getSelectedRow() != -1) {
                        jPopupMenu1.show(tbObat, evt.getX(), evt.getY());
                    }
                }
            }
        });


        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanPenilaian.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanPenilaian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanPenilaian.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanPenilaian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanPenilaian.setText("Laporan Penilaian Ponek");
        MnLaporanPenilaian.setName("MnLaporanPenilaian"); // NOI18N
        MnLaporanPenilaian.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanPenilaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanPenilaianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanPenilaian);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-09-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-09-2025" }));
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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);


        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(3, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetailMasalah.setName("tbMasalahDetailMasalah"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetailMasalah);


        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbRencanaDetail.setName("tbRencanaDetail"); // NOI18N
        Scroll10.setViewportView(tbRencanaDetail);


        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)),
                "Rencana Keperawatan Lainnya :", javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        DetailRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        DetailRencana.setColumns(20);
        DetailRencana.setRows(5);
        DetailRencana.setName("DetailRencana"); // NOI18N
        scrollPane6.setViewportView(DetailRencana);




        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
    }// GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
            return;
        }
        if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Riwayat Penyakit Sekarang");
            return;
        }
        if (NmPetugas.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Petugas");
            return;
        }

        StringBuilder riwKandungan = new StringBuilder();
        for (int j = 0; j < tbRiwayatKehamilan.getRowCount(); j++) {
            riwKandungan.append((tbRiwayatKehamilan.getValueAt(j, 0)==null?"":tbRiwayatKehamilan.getValueAt(j, 0).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 1)==null?"":tbRiwayatKehamilan.getValueAt(j, 1).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 2)==null?"":tbRiwayatKehamilan.getValueAt(j, 2).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 3)==null?"":tbRiwayatKehamilan.getValueAt(j, 3).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 4)==null?"":tbRiwayatKehamilan.getValueAt(j, 4).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 5)==null?"":tbRiwayatKehamilan.getValueAt(j, 5).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 6)==null?"":tbRiwayatKehamilan.getValueAt(j, 6).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 7)==null?"":tbRiwayatKehamilan.getValueAt(j, 7).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 8)==null?"":tbRiwayatKehamilan.getValueAt(j, 8).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 9)==null?"":tbRiwayatKehamilan.getValueAt(j, 9).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 10)==null?"":tbRiwayatKehamilan.getValueAt(j, 10).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 11)==null?"":tbRiwayatKehamilan.getValueAt(j, 11).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 12)==null?"":tbRiwayatKehamilan.getValueAt(j, 12).toString())).append("|")
                .append((tbRiwayatKehamilan.getValueAt(j, 13)==null?"":tbRiwayatKehamilan.getValueAt(j, 13).toString())).append(";");
        }

        if (Sequel.menyimpantf("penilaian_awal_keperawatan_ponek", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 139, new String[]{
            TNoRw.getText(), Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19),
            Informasi.getSelectedItem().toString(), KeluhanUtama.getText(), RPD.getText(), "", RPO.getText(), StatusKehamilan.getSelectedItem().toString(),
            Gravida.getText(), Para.getText(), Abortus.getText(), HPHT.getText(), "", Airway.getSelectedItem().toString(), Breathing.getSelectedItem().toString(), Spo2.getText(), Nadi.getSelectedItem().toString(), CRT.getSelectedItem().toString(),
            WarnaKulit.getSelectedItem().toString(), CirculationPerdarahan.getSelectedItem().toString(), TurgorKulit.getSelectedItem().toString(),
            ResponNeurologi.getSelectedItem().toString(), PupilNeurologi.getSelectedItem().toString(), Reflek.getText(), GCS.getText(),
            Tekanan.getSelectedItem().toString(), Pupil.getSelectedItem().toString(), Neurosensorik.getSelectedItem().toString(), Integumen.getSelectedItem().toString(),
            Turgor.getSelectedItem().toString(), Edema.getSelectedItem().toString(), Mukosa.getSelectedItem().toString(), Perdarahan.getSelectedItem().toString(),
            JumlahPerdarahan.getText(), WarnaPerdarahan.getText(), Intoksikasi.getSelectedItem().toString(), BAB.getSelectedItem().toString(), 
            XBAB.getText(), KBAB.getSelectedItem().toString(), WBAB.getSelectedItem().toString(), BAK.getSelectedItem().toString(), XBAK.getText(), WBAK.getSelectedItem().toString(),
            LBAK.getText(), MenarcheUmur.getText(), SiklusMenstruasi.getText(), KeteratanMenstruasi.getSelectedItem().toString(), LamaMenstruasi.getText(), KeluhanHaid.getText(),
            HamilKe.getText(), UKMinggu.getText(), UKHari.getText(), HPHTKbd.getText(), HPLKbd.getText(), BBSebelumHamil.getText(), BBSekarang.getText(), 
            TBKbd.getText(), PeriksaANC.getText(), ANCDi.getSelectedItem().toString(), ANCLainnya.getText(), ImunisasiTT.getText(), TglTT1.getText(), TglTT2.getText(), GerakanJanin.getText(),
            riwKandungan.toString(), ObjKU.getText(), ObjKesadaran.getText(), ObjGCSE.getText(), ObjGCSV.getText(), ObjGCSM.getText(), 
            ObjTDSistol.getText(), ObjTDDiastol.getText(), ObjHR.getText(), ObjRR.getText(), ObjSuhu.getText(), ObjSpO2.getText(),
            ObjKepala.getSelectedItem().toString(), ObjMata.getSelectedItem().toString(), ObjLeher.getSelectedItem().toString(), ObjThorax.getSelectedItem().toString(), ObjAbdomen.getSelectedItem().toString(), ObjInspeksi.getSelectedItem().toString(), ObjTFU.getSelectedItem().toString(), ObjLeopold1.getSelectedItem().toString(), ObjLeopold2.getSelectedItem().toString(), ObjLeopold3.getSelectedItem().toString(), 
            ObjLeopold4.getSelectedItem().toString(), ObjTBBJ.getText(), ObjHis.getText(), ObjAuskultasi.getText(), ObjPukul.getText(), ObjPengeluaran.getText(), ObjPmxDalam.getText(), ObjInspekulo.getText(),
            ObjOedema1.getText(), ObjOedema2.getText(), ObjVarises1.getText(), ObjVarises2.getText(), ObjReflek1.getText(), ObjReflek2.getText(), KdPetugas.getText(),
            Psikologis.getSelectedItem().toString(), Jiwa.getSelectedItem().toString(), Perilaku.getSelectedItem().toString(), Dilaporkan.getText(), Sebutkan.getText(),
            Hubungan.getSelectedItem().toString(), TinggalDengan.getSelectedItem().toString(), KetTinggal.getText(), StatusBudaya.getSelectedItem().toString(), KetBudaya.getText(),
            PendidikanPJ.getSelectedItem().toString(), KetPendidikanPJ.getText(), Edukasi.getSelectedItem().toString(), KetEdukasi.getText(), ADL.getSelectedItem().toString(),
            Aktifitas.getSelectedItem().toString(), AlatBantu.getSelectedItem().toString(), KetAlatBantu.getText(), ATS.getSelectedItem().toString(), BJM.getSelectedItem().toString(),
            MSA.getSelectedItem().toString(), Hasil.getSelectedItem().toString(), Lapor.getSelectedItem().toString(), KetLapor.getText(), SkalaNyeri.getSelectedItem().toString(), Nyeri.getSelectedItem().toString(),
            Provokes.getSelectedItem().toString(), KetProvokes.getText(), Quality.getSelectedItem().toString(), KetQuality.getText(), Lokasi.getText(), Menyebar.getSelectedItem().toString(),
            Durasi.getText(), NyeriHilang.getSelectedItem().toString(), KetNyeri.getText(), PadaDokter.getSelectedItem().toString(), KetDokter.getText(), Rencana.getText()
        }) == true) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            tampil();
            emptTeks();
        }
    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, Rencana, BtnBatal);
        }
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

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {

        if (tbObat.getSelectedRow() > -1) {
            if (Sequel.meghapustf("penilaian_awal_keperawatan_ponek", "no_rawat", TNoRw.getText()) == true) {
                    tampil();
                    emptTeks();
            }
            
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Nama Pasien");
            return;
        }
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama") || KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 91).toString())) {
                StringBuilder riwKandungan = new StringBuilder();
                for (int j = 0; j < tbRiwayatKehamilan.getRowCount(); j++) {
                    riwKandungan.append((tbRiwayatKehamilan.getValueAt(j, 0)==null?"":tbRiwayatKehamilan.getValueAt(j, 0).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 1)==null?"":tbRiwayatKehamilan.getValueAt(j, 1).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 2)==null?"":tbRiwayatKehamilan.getValueAt(j, 2).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 3)==null?"":tbRiwayatKehamilan.getValueAt(j, 3).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 4)==null?"":tbRiwayatKehamilan.getValueAt(j, 4).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 5)==null?"":tbRiwayatKehamilan.getValueAt(j, 5).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 6)==null?"":tbRiwayatKehamilan.getValueAt(j, 6).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 7)==null?"":tbRiwayatKehamilan.getValueAt(j, 7).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 8)==null?"":tbRiwayatKehamilan.getValueAt(j, 8).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 9)==null?"":tbRiwayatKehamilan.getValueAt(j, 9).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 10)==null?"":tbRiwayatKehamilan.getValueAt(j, 10).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 11)==null?"":tbRiwayatKehamilan.getValueAt(j, 11).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 12)==null?"":tbRiwayatKehamilan.getValueAt(j, 12).toString())).append("|")
                        .append((tbRiwayatKehamilan.getValueAt(j, 13)==null?"":tbRiwayatKehamilan.getValueAt(j, 13).toString())).append(";");
                }

                if (Sequel.mengedittf("penilaian_awal_keperawatan_ponek", "no_rawat=?", 
                    "tanggal=?, informasi=?, keluhan_utama=?, rpd=?, rpk=?, rpo=?, status_kehamilan=?, gravida=?, para=?, abortus=?, hpht=?, alergi=?, airway=?, breathing=?, spo2=?, nadi=?, crt=?, warna_kulit=?, circulation_perdarahan=?, turgor_kulit=?, respon_neurologi=?, pupil_neurologi=?, reflek=?, gcs=?, tekanan=?, pupil=?, neurosensorik=?, integumen=?, turgor=?, edema=?, mukosa=?, perdarahan=?, jumlah_perdarahan=?, warna_perdarahan=?, menarche_umur=?, siklus_menstruasi=?, keteratan_menstruasi=?, lama_menstruasi=?, keluhan_haid=?, hamil_ke=?, uk_minggu=?, uk_hari=?, hpht_kbd=?, hpl_kbd=?, bb_sebelum_hamil=?, bb_sekarang=?, tb_kbd=?, periksa_anc=?, anc_di=?, anc_lainnya=?, imunisasi_tt=?, tgl_tt1=?, tgl_tt2=?, gerakan_janin=?, riwayat_kehamilan=?, obj_ku=?, obj_kesadaran=?, obj_gcs_e=?, obj_gcs_v=?, obj_gcs_m=?, obj_td_sistol=?, obj_td_diastol=?, obj_hr=?, obj_rr=?, obj_suhu=?, obj_spo2=?, obj_kepala=?, obj_mata=?, obj_leher=?, obj_thorax=?, obj_abdomen=?, obj_inspeksi=?, obj_tfu=?, obj_leopold1=?, obj_leopold2=?, obj_leopold3=?, obj_leopold4=?, obj_tbbj=?, obj_his=?, obj_auskultasi=?, obj_pukul=?, obj_pengeluaran=?, obj_pmxdalam=?, obj_inspekulo=?, obj_oedema1=?, obj_oedema2=?, obj_varises1=?, obj_varises2=?, obj_reflek1=?, obj_reflek2=?, nip=?, psikologis=?, jiwa=?, perilaku=?, dilaporkan=?, sebutkan=?, hubungan=?, tinggal_dengan=?, ket_tinggal=?, budaya=?, ket_budaya=?, pendidikan_pj=?, ket_pendidikan_pj=?, edukasi=?, ket_edukasi=?, kemampuan=?, aktifitas=?, alat_bantu=?, ket_bantu=?, berjalan_a=?, berjalan_b=?, berjalan_c=?, hasil=?, lapor=?, ket_lapor=?, skala_nyeri=?, nyeri=?, provokes=?, ket_provokes=?, quality=?, ket_quality=?, lokasi=?, menyebar=?, durasi=?, nyeri_hilang=?, ket_nyeri=?, pada_dokter=?, ket_dokter=?, rencana=?", 
                    130, new String[]{
                    Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " " + TglAsuhan.getSelectedItem().toString().substring(11, 19),
                    Informasi.getSelectedItem().toString(), KeluhanUtama.getText(), RPD.getText(), "", RPO.getText(), StatusKehamilan.getSelectedItem().toString(),
                    Gravida.getText(), Para.getText(), Abortus.getText(), HPHT.getText(), "", Airway.getSelectedItem().toString(), Breathing.getSelectedItem().toString(), Spo2.getText(), Nadi.getSelectedItem().toString(), CRT.getSelectedItem().toString(),
                    WarnaKulit.getSelectedItem().toString(), CirculationPerdarahan.getSelectedItem().toString(), TurgorKulit.getSelectedItem().toString(),
                    ResponNeurologi.getSelectedItem().toString(), PupilNeurologi.getSelectedItem().toString(), Reflek.getText(), GCS.getText(),
                    Tekanan.getSelectedItem().toString(), Pupil.getSelectedItem().toString(), Neurosensorik.getSelectedItem().toString(), Integumen.getSelectedItem().toString(),
                    Turgor.getSelectedItem().toString(), Edema.getSelectedItem().toString(), Mukosa.getSelectedItem().toString(), Perdarahan.getSelectedItem().toString(),
                    JumlahPerdarahan.getText(), WarnaPerdarahan.getText(), MenarcheUmur.getText(), SiklusMenstruasi.getText(), KeteratanMenstruasi.getSelectedItem().toString(), LamaMenstruasi.getText(), KeluhanHaid.getText(),
                    HamilKe.getText(), UKMinggu.getText(), UKHari.getText(), HPHTKbd.getText(), HPLKbd.getText(), BBSebelumHamil.getText(), BBSekarang.getText(), 
                    TBKbd.getText(), PeriksaANC.getText(), ANCDi.getSelectedItem().toString(), ANCLainnya.getText(), ImunisasiTT.getText(), TglTT1.getText(), TglTT2.getText(), GerakanJanin.getText(),
                    riwKandungan.toString(), ObjKU.getText(), ObjKesadaran.getText(), ObjGCSE.getText(), ObjGCSV.getText(), ObjGCSM.getText(), 
                    ObjTDSistol.getText(), ObjTDDiastol.getText(), ObjHR.getText(), ObjRR.getText(), ObjSuhu.getText(), ObjSpO2.getText(),
                    ObjKepala.getSelectedItem().toString(), ObjMata.getSelectedItem().toString(), ObjLeher.getSelectedItem().toString(), ObjThorax.getSelectedItem().toString(), ObjAbdomen.getSelectedItem().toString(), ObjInspeksi.getSelectedItem().toString(), ObjTFU.getSelectedItem().toString(), ObjLeopold1.getSelectedItem().toString(), ObjLeopold2.getSelectedItem().toString(), ObjLeopold3.getSelectedItem().toString(), 
                    ObjLeopold4.getSelectedItem().toString(), ObjTBBJ.getText(), ObjHis.getText(), ObjAuskultasi.getText(), ObjPukul.getText(), ObjPengeluaran.getText(), ObjPmxDalam.getText(), ObjInspekulo.getText(),
                    ObjOedema1.getText(), ObjOedema2.getText(), ObjVarises1.getText(), ObjVarises2.getText(), ObjReflek1.getText(), ObjReflek2.getText(), KdPetugas.getText(),
                    Psikologis.getSelectedItem().toString(), Jiwa.getSelectedItem().toString(), Perilaku.getSelectedItem().toString(), Dilaporkan.getText(), Sebutkan.getText(),
                    Hubungan.getSelectedItem().toString(), TinggalDengan.getSelectedItem().toString(), KetTinggal.getText(), StatusBudaya.getSelectedItem().toString(), KetBudaya.getText(),
                    PendidikanPJ.getSelectedItem().toString(), KetPendidikanPJ.getText(), Edukasi.getSelectedItem().toString(), KetEdukasi.getText(), ADL.getSelectedItem().toString(),
                    Aktifitas.getSelectedItem().toString(), AlatBantu.getSelectedItem().toString(), KetAlatBantu.getText(), ATS.getSelectedItem().toString(), BJM.getSelectedItem().toString(),
                    MSA.getSelectedItem().toString(), Hasil.getSelectedItem().toString(), Lapor.getSelectedItem().toString(), KetLapor.getText(), SkalaNyeri.getSelectedItem().toString(), Nyeri.getSelectedItem().toString(),
                    Provokes.getSelectedItem().toString(), KetProvokes.getText(), Quality.getSelectedItem().toString(), KetQuality.getText(), Lokasi.getText(), Menyebar.getSelectedItem().toString(),
                    Durasi.getText(), NyeriHilang.getSelectedItem().toString(), KetNyeri.getText(), PadaDokter.getSelectedItem().toString(), KetDokter.getText(), Rencana.getText(),
                    TNoRw.getText() // WHERE no_rawat=?
                }) == true) {
                    tampil();
                    emptTeks();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
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
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {
        if(true) return;// GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            try {
                if (TCari.getText().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ponek.tanggal,penilaian_awal_keperawatan_ponek.informasi,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.keluhan_utama,penilaian_awal_keperawatan_ponek.rpd,penilaian_awal_keperawatan_ponek.rpo,penilaian_awal_keperawatan_ponek.status_kehamilan,penilaian_awal_keperawatan_ponek.gravida,penilaian_awal_keperawatan_ponek.para,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.abortus,penilaian_awal_keperawatan_ponek.hpht,penilaian_awal_keperawatan_ponek.tekanan,penilaian_awal_keperawatan_ponek.pupil,penilaian_awal_keperawatan_ponek.neurosensorik,penilaian_awal_keperawatan_ponek.integumen,penilaian_awal_keperawatan_ponek.turgor,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.edema,penilaian_awal_keperawatan_ponek.mukosa,penilaian_awal_keperawatan_ponek.perdarahan,penilaian_awal_keperawatan_ponek.jumlah_perdarahan,penilaian_awal_keperawatan_ponek.warna_perdarahan,penilaian_awal_keperawatan_ponek.intoksikasi,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.bab,penilaian_awal_keperawatan_ponek.xbab,penilaian_awal_keperawatan_ponek.kbab,penilaian_awal_keperawatan_ponek.wbab,penilaian_awal_keperawatan_ponek.bak,penilaian_awal_keperawatan_ponek.xbak,penilaian_awal_keperawatan_ponek.wbak,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.lbak,penilaian_awal_keperawatan_ponek.psikologis,penilaian_awal_keperawatan_ponek.jiwa,penilaian_awal_keperawatan_ponek.perilaku,penilaian_awal_keperawatan_ponek.dilaporkan,penilaian_awal_keperawatan_ponek.sebutkan,penilaian_awal_keperawatan_ponek.hubungan,pasien.stts_nikah,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.tinggal_dengan,penilaian_awal_keperawatan_ponek.ket_tinggal,pasien.pekerjaan,penjab.png_jawab,penilaian_awal_keperawatan_ponek.budaya,penilaian_awal_keperawatan_ponek.ket_budaya,pasien.pnd,penilaian_awal_keperawatan_ponek.pendidikan_pj,penilaian_awal_keperawatan_ponek.ket_pendidikan_pj,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.edukasi,penilaian_awal_keperawatan_ponek.ket_edukasi,penilaian_awal_keperawatan_ponek.kemampuan,penilaian_awal_keperawatan_ponek.aktifitas,penilaian_awal_keperawatan_ponek.alat_bantu,penilaian_awal_keperawatan_ponek.ket_bantu,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.nyeri,penilaian_awal_keperawatan_ponek.provokes,penilaian_awal_keperawatan_ponek.ket_provokes,penilaian_awal_keperawatan_ponek.quality,penilaian_awal_keperawatan_ponek.ket_quality,penilaian_awal_keperawatan_ponek.lokasi,penilaian_awal_keperawatan_ponek.menyebar,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.skala_nyeri,penilaian_awal_keperawatan_ponek.durasi,penilaian_awal_keperawatan_ponek.nyeri_hilang,penilaian_awal_keperawatan_ponek.ket_nyeri,penilaian_awal_keperawatan_ponek.pada_dokter,penilaian_awal_keperawatan_ponek.ket_dokter,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.berjalan_a,penilaian_awal_keperawatan_ponek.berjalan_b,penilaian_awal_keperawatan_ponek.berjalan_c,penilaian_awal_keperawatan_ponek.hasil,penilaian_awal_keperawatan_ponek.lapor,penilaian_awal_keperawatan_ponek.ket_lapor,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.rencana,penilaian_awal_keperawatan_ponek.nip,petugas.nama "
                                    +
                                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    +
                                    "inner join penilaian_awal_keperawatan_ponek on reg_periksa.no_rawat=penilaian_awal_keperawatan_ponek.no_rawat "
                                    +
                                    "inner join petugas on penilaian_awal_keperawatan_ponek.nip=petugas.nip " +
                                    "left join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien " +
                                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj " +
                                    "left join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where " +
                                    "penilaian_awal_keperawatan_ponek.tanggal between ? and ? order by penilaian_awal_keperawatan_ponek.tanggal");
                } else {
                    ps = koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ponek.tanggal,penilaian_awal_keperawatan_ponek.informasi,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.keluhan_utama,penilaian_awal_keperawatan_ponek.rpd,penilaian_awal_keperawatan_ponek.rpo,penilaian_awal_keperawatan_ponek.status_kehamilan,penilaian_awal_keperawatan_ponek.gravida,penilaian_awal_keperawatan_ponek.para,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.abortus,penilaian_awal_keperawatan_ponek.hpht,penilaian_awal_keperawatan_ponek.tekanan,penilaian_awal_keperawatan_ponek.pupil,penilaian_awal_keperawatan_ponek.neurosensorik,penilaian_awal_keperawatan_ponek.integumen,penilaian_awal_keperawatan_ponek.turgor,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.edema,penilaian_awal_keperawatan_ponek.mukosa,penilaian_awal_keperawatan_ponek.perdarahan,penilaian_awal_keperawatan_ponek.jumlah_perdarahan,penilaian_awal_keperawatan_ponek.warna_perdarahan,penilaian_awal_keperawatan_ponek.intoksikasi,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.bab,penilaian_awal_keperawatan_ponek.xbab,penilaian_awal_keperawatan_ponek.kbab,penilaian_awal_keperawatan_ponek.wbab,penilaian_awal_keperawatan_ponek.bak,penilaian_awal_keperawatan_ponek.xbak,penilaian_awal_keperawatan_ponek.wbak,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.lbak,penilaian_awal_keperawatan_ponek.psikologis,penilaian_awal_keperawatan_ponek.jiwa,penilaian_awal_keperawatan_ponek.perilaku,penilaian_awal_keperawatan_ponek.dilaporkan,penilaian_awal_keperawatan_ponek.sebutkan,penilaian_awal_keperawatan_ponek.hubungan,pasien.stts_nikah,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.tinggal_dengan,penilaian_awal_keperawatan_ponek.ket_tinggal,pasien.pekerjaan,penjab.png_jawab,penilaian_awal_keperawatan_ponek.budaya,penilaian_awal_keperawatan_ponek.ket_budaya,pasien.pnd,penilaian_awal_keperawatan_ponek.pendidikan_pj,penilaian_awal_keperawatan_ponek.ket_pendidikan_pj,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.edukasi,penilaian_awal_keperawatan_ponek.ket_edukasi,penilaian_awal_keperawatan_ponek.kemampuan,penilaian_awal_keperawatan_ponek.aktifitas,penilaian_awal_keperawatan_ponek.alat_bantu,penilaian_awal_keperawatan_ponek.ket_bantu,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.nyeri,penilaian_awal_keperawatan_ponek.provokes,penilaian_awal_keperawatan_ponek.ket_provokes,penilaian_awal_keperawatan_ponek.quality,penilaian_awal_keperawatan_ponek.ket_quality,penilaian_awal_keperawatan_ponek.lokasi,penilaian_awal_keperawatan_ponek.menyebar,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.skala_nyeri,penilaian_awal_keperawatan_ponek.durasi,penilaian_awal_keperawatan_ponek.nyeri_hilang,penilaian_awal_keperawatan_ponek.ket_nyeri,penilaian_awal_keperawatan_ponek.pada_dokter,penilaian_awal_keperawatan_ponek.ket_dokter,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.berjalan_a,penilaian_awal_keperawatan_ponek.berjalan_b,penilaian_awal_keperawatan_ponek.berjalan_c,penilaian_awal_keperawatan_ponek.hasil,penilaian_awal_keperawatan_ponek.lapor,penilaian_awal_keperawatan_ponek.ket_lapor,"
                                    +
                                    "penilaian_awal_keperawatan_ponek.rencana,penilaian_awal_keperawatan_ponek.nip,petugas.nama "
                                    +
                                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                    +
                                    "inner join penilaian_awal_keperawatan_ponek on reg_periksa.no_rawat=penilaian_awal_keperawatan_ponek.no_rawat "
                                    +
                                    "inner join petugas on penilaian_awal_keperawatan_ponek.nip=petugas.nip " +
                                    "left join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien " +
                                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj " +
                                    "left join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where " +
                                    "penilaian_awal_keperawatan_ponek.tanggal between ? and ? and " +
                                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "
                                    +
                                    "penilaian_awal_keperawatan_ponek.nip like ? or petugas.nama like ?) " +
                                    "order by penilaian_awal_keperawatan_ponek.tanggal");
                }

                try {
                    if (TCari.getText().equals("")) {
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
                            "<tr class='isi'>" +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'><b>PASIEN & PETUGAS</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'><b>I. RIWAYAT KESEHATAN PASIEN</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='15%'><b>II. PEMERIKSAAN FISIK</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='14%'><b>III. RIWAYAT PSIKOLOGIS - SOSIAL - EKONOMI - BUDAYA - SPIRITUAL</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'><b>IV. PENGKAJIAN FUNGSI</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'><b>V. SKALA NYERI</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='16%'><b>VI. PENILAIAN RESIKO JATUH (GET UP AND GO)</b></td>"
                                    +
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='11%'><b>MASALAH & RENCANA KEPERAWATAN</b></td>"
                                    +
                                    "</tr>");
                    while (rs.next()) {
                        masalahkeperawatanigd = "";
                        ps2 = koneksi.prepareStatement(
                                "select master_masalah_keperawatan_igd.kode_masalah,master_masalah_keperawatan_igd.nama_masalah from master_masalah_keperawatan_igd "
                                        +
                                        "inner join penilaian_awal_keperawatan_ponek_masalah on penilaian_awal_keperawatan_ponek_masalah.kode_masalah=master_masalah_keperawatan_igd.kode_masalah "
                                        +
                                        "where penilaian_awal_keperawatan_ponek_masalah.no_rawat=? order by penilaian_awal_keperawatan_ponek_masalah.kode_masalah");
                        try {
                            ps2.setString(1, rs.getString("no_rawat"));
                            rs2 = ps2.executeQuery();
                            while (rs2.next()) {
                                masalahkeperawatanigd = rs2.getString("nama_masalah") + ", " + masalahkeperawatanigd;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (ps2 != null) {
                                ps2.close();
                            }
                        }
                        htmlContent.append(
                                "<tr class='isi'>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("no_rawat") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("no_rkm_medis") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("nm_pasien") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>J.K.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("jk") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Tgl.Lahir</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("tgl_lahir") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Agama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("agama") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Bahasa</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("nama_bahasa") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Pekerjaan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("pekerjaan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Pembayaran</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("png_jawab") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Pendidikan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("pnd") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Stts.Nikah</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("stts_nikah") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Cacat Fisik</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("nama_cacat") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("nip") + " " + rs.getString("nama") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Tgl.Asuhan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("tanggal") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Informasi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("informasi") + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>RPS</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("keluhan_utama") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>RPD</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("rpd") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>RPO</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("rpo") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Stts.Hami</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("status_kehamilan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>HPHT</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("hpht") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Para</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("para") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Abortus</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("abortus") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='32%' valign='top'>Gravida</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"
                                        + rs.getString("gravida") + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Tekanan Intrakranial</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("tekanan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Pupil</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("pupil") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Neurosensorik / Muskuloskeletal</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("neurosensorik") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Integumen</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("integumen") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Turgor kulit</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("turgor") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Edema</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("edema") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Mukosa Mulut</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("mukosa") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Perdarahan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("perdarahan") + ", Jumlah : " + rs.getString("jumlah_perdarahan")
                                        + ", Warna : " + rs.getString("warna_perdarahan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Intoksikasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("intoksikasi") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Eliminasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        +
                                        "BAB : -Frekuensi : " + rs.getString("bab") + " x / " + rs.getString("xbab")
                                        + " -Konsistensi : " + rs.getString("kbab") + " -Warna : "
                                        + rs.getString("wbab") + "<br>" +
                                        "BAK : -Frekuensi : " + rs.getString("bak") + " x / " + rs.getString("xbak")
                                        + " -Warna : " + rs.getString("wbak") + " -Lain-lain : " + rs.getString("lbak")
                                        + "<br>" +
                                        "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Kondisi Psikologis</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("psikologis") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Gangguan Jiwa Di Masa Lalu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("jiwa") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Adakah perilaku</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("perilaku") + ", Dilaporkan Ke : " + rs.getString("dilaporkan")
                                        + ", Sebutkan : " + rs.getString("sebutkan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Hubungan Pasien Dengan Anggota Keluarga</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("hubungan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Tinggal Dengan</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("tinggal_dengan")
                                        + (rs.getString("ket_tinggal").equals("") ? ""
                                                : ", " + rs.getString("ket_tinggal"))
                                        + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Kepercayaan / Budaya / Nilai-nilai Khusus</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("budaya")
                                        + (rs.getString("ket_budaya").equals("") ? ""
                                                : ", " + rs.getString("ket_budaya"))
                                        + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Pendidikan P.J.</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("pendidikan_pj")
                                        + (rs.getString("ket_pendidikan_pj").equals("") ? ""
                                                : ", " + rs.getString("ket_pendidikan_pj"))
                                        + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Edukasi Diberikan Kepada</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("edukasi")
                                        + (rs.getString("ket_edukasi").equals("") ? ""
                                                : ", " + rs.getString("ket_edukasi"))
                                        + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Kemampuan Aktifitas Sehari-hari</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("kemampuan") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Aktifitas</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("aktifitas") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='34%' valign='top'>Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"
                                        + rs.getString("alat_bantu")
                                        + (rs.getString("ket_bantu").equals("") ? "" : ", " + rs.getString("ket_bantu"))
                                        + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Tingkat Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("nyeri") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("provokes") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Ket. Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("ket_provokes") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("quality") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Ket. Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("ket_quality") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Lokas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("lokasi") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Menyebar</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("menyebar") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Skala Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("skala_nyeri") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Durasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("durasi") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Nyeri Hilang</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("nyeri_hilang") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Ket. Hilang Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("ket_nyeri") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Lapor Ke Dokter</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("pada_dokter") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='44%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"
                                        + rs.getString("ket_dokter") + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"
                                        +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Tidak seimbang/sempoyongan/limbung</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("berjalan_a") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("berjalan_b") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja/benda lain sebagai penopang</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("berjalan_c") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Hasil</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("hasil") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Dilaporan ke dokter?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("lapor") + "</td>" +
                                        "</tr>" +
                                        "<tr class='isi2'>" +
                                        "<td width='64%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"
                                        + rs.getString("ket_lapor") + "</td>" +
                                        "</tr>" +
                                        "</table>" +
                                        "</td>" +
                                        "<td valign='top' cellpadding='0' cellspacing='0'>" +
                                        "Masalah Keperawatan : " + masalahkeperawatanigd + "<br><br>" +
                                        "Rencana Keperawatan : " + rs.getString("rencana") +
                                        "</td>" +
                                        "</tr>");
                    }
                    LoadHTML.setText(
                            "<html>" +
                                    "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"
                                    +
                                    htmlContent.toString() +
                                    "</table>" +
                                    "</html>");

                    File g = new File("file2.css");
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                            ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                                    +
                                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                                    +
                                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                                    +
                                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                                    +
                                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                                    +
                                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");
                    bg.close();

                    File f = new File("DataPenilaianAwalKeperawatanIGD.html");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write(LoadHTML.getText().replaceAll("<head>", "<head>" +
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />" +
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            +
                            "<tr class='isi2'>" +
                            "<td valign='top' align='center'>" +
                            "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>" +
                            akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                            +
                            akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>" +
                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL KEPERAWATAN IGD<br><br></font>" +
                            "</td>" +
                            "</tr>" +
                            "</table>"));
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
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
                ChkAccor.setSelected(true);
                isMenu();
            } catch (java.lang.NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            if ((evt.getClickCount() == 2) && (tbObat.getSelectedColumn() == 0)) {
                TabRawat.setSelectedIndex(0);
            }
        }
    }// GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
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
    }// GEN-LAST:event_tbObatKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KdPetugasKeyPressed

    }// GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }// GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnDokterKeyPressed
        // Valid.pindah(evt,Monitoring,BtnSimpan);
    }// GEN-LAST:event_BtnDokterKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt, Informasi, RPD);
    }// GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt, KeluhanUtama, RPO);
    }// GEN-LAST:event_RPDKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt, RPD, StatusKehamilan);
    }// GEN-LAST:event_RPOKeyPressed

    private void AktifitasKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AktifitasKeyPressed
        Valid.pindah(evt, ADL, AlatBantu);
    }// GEN-LAST:event_AktifitasKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt, Aktifitas, KetAlatBantu);
    }// GEN-LAST:event_AlatBantuKeyPressed

    private void KetAlatBantuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetAlatBantuKeyPressed
        Valid.pindah(evt, AlatBantu, Nyeri);
    }// GEN-LAST:event_KetAlatBantuKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ADLKeyPressed
        Valid.pindah(evt, KetEdukasi, Aktifitas);
    }// GEN-LAST:event_ADLKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt, Hubungan, KetTinggal);
    }// GEN-LAST:event_TinggalDenganKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetTinggalKeyPressed
        Valid.pindah(evt, TinggalDengan, StatusBudaya);
    }// GEN-LAST:event_KetTinggalKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah(evt, KetPendidikanPJ, KetEdukasi);
    }// GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetEdukasiKeyPressed
        Valid.pindah(evt, Edukasi, ADL);
    }// GEN-LAST:event_KetEdukasiKeyPressed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LaporKeyPressed
        Valid.pindah(evt, Hasil, KetLapor);
    }// GEN-LAST:event_LaporKeyPressed

    private void ATSKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ATSKeyPressed
        Valid.pindah(evt, KetDokter, BJM);
    }// GEN-LAST:event_ATSKeyPressed

    private void BJMKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BJMKeyPressed
        Valid.pindah(evt, ATS, MSA);
    }// GEN-LAST:event_BJMKeyPressed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_HasilKeyPressed
        Valid.pindah(evt, MSA, Lapor);
    }// GEN-LAST:event_HasilKeyPressed

    private void KetLaporKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetLaporKeyPressed
        Valid.pindah(evt, Lapor, Rencana);
    }// GEN-LAST:event_KetLaporKeyPressed

    private void MSAKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MSAKeyPressed
        Valid.pindah(evt, BJM, Hasil);
    }// GEN-LAST:event_MSAKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt, KetAlatBantu, Provokes);
    }// GEN-LAST:event_NyeriKeyPressed

    private void ProvokesKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ProvokesKeyPressed
        Valid.pindah(evt, Nyeri, KetProvokes);
    }// GEN-LAST:event_ProvokesKeyPressed

    private void KetProvokesKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetProvokesKeyPressed
        Valid.pindah(evt, Provokes, Quality);
    }// GEN-LAST:event_KetProvokesKeyPressed

    private void QualityKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_QualityKeyPressed
        Valid.pindah(evt, KetProvokes, KetQuality);
    }// GEN-LAST:event_QualityKeyPressed

    private void KetQualityKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetQualityKeyPressed
        Valid.pindah(evt, Quality, Lokasi);
    }// GEN-LAST:event_KetQualityKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt, KetQuality, Menyebar);
    }// GEN-LAST:event_LokasiKeyPressed

    private void MenyebarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MenyebarKeyPressed
        Valid.pindah(evt, Lokasi, SkalaNyeri);
    }// GEN-LAST:event_MenyebarKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_SkalaNyeriKeyPressed
        Valid.pindah(evt, Menyebar, Durasi);
    }// GEN-LAST:event_SkalaNyeriKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt, SkalaNyeri, NyeriHilang);
    }// GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt, Durasi, KetNyeri);
    }// GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt, NyeriHilang, PadaDokter);
    }// GEN-LAST:event_KetNyeriKeyPressed

    private void PadaDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PadaDokterKeyPressed
        Valid.pindah(evt, KetNyeri, KetDokter);
    }// GEN-LAST:event_PadaDokterKeyPressed

    private void KetDokterKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetDokterKeyPressed
        Valid.pindah(evt, PadaDokter, ATS);
    }// GEN-LAST:event_KetDokterKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt, Rencana, RPD);
    }// GEN-LAST:event_TglAsuhanKeyPressed

    private void StatusBudayaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_StatusBudayaKeyPressed
        Valid.pindah(evt, KetTinggal, KetBudaya);
    }// GEN-LAST:event_StatusBudayaKeyPressed

    private void KetBudayaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetBudayaKeyPressed
        Valid.pindah(evt, StatusBudaya, PendidikanPJ);
    }// GEN-LAST:event_KetBudayaKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        }
    }// GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        try {
            if (Valid.daysOld("./cache/masalahkeperawatanigd.iyem") < 30) {
                tampilMasalah2();
            } else {
                tampilMasalah();
            }
        } catch (Exception e) {
        }
    }// GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChkAccorActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            isMenu();
        } else {
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }// GEN-LAST:event_ChkAccorActionPerformed

    
    private void MnLaporanPenilaianActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbObat.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data terlebih dahulu..!!!!");
        } else {
            BtnPrint1ActionPerformed(null);
        }
    }

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            param.put("nyeri", Sequel.cariGambar("select gambar.nyeri from gambar"));

            String nip = tbObat.getValueAt(tbObat.getSelectedRow(), 90).toString();
            String nmPetugas = tbObat.getValueAt(tbObat.getSelectedRow(), 91).toString();

            String finger = Sequel.cariIsi(
                    "select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    nip);
            param.put("finger",
                    "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh "
                            + nmPetugas + "\nID "
                            + (finger.equals("") ? nip : finger)
                            + "\n" + Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString()));

            // --- LOGIKA PEMBATASAN BARIS TABEL RIWAYAT KANDUNGAN ---
            String riwayat_kehamilan = Sequel.cariIsi("select riwayat_kehamilan from penilaian_awal_keperawatan_ponek where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());

            java.util.Collection listRiwayat = new java.util.ArrayList(); // Raw Collection
            String[] rows_riw = riwayat_kehamilan.split(";");
            int count = 0; // Inisialisasi penghitung baris

            for (String row : rows_riw) {
                // Batasi maksimal 5 baris dan hindari baris kosong dari pemecahan string
                if (!row.trim().isEmpty() && count < 5) {
                    String[] cols = row.split("\\|");
                    java.util.Map map = new java.util.HashMap(); // Raw Map
                    map.put("no", cols.length > 0 ? cols[0] : "");
                    map.put("tgl_partus", cols.length > 1 ? cols[1] : "");
                    map.put("umur_a", cols.length > 2 ? cols[2] : "");
                    map.put("umur_p", cols.length > 3 ? cols[3] : "");
                    map.put("umur_t", cols.length > 4 ? cols[4] : "");
                    map.put("jenis", cols.length > 5 ? cols[5] : "");
                    map.put("penolong_na", cols.length > 6 ? cols[6] : "");
                    map.put("penolong_no", cols.length > 7 ? cols[7] : "");
                    map.put("jk", cols.length > 8 ? cols[8] : "");
                    map.put("bbl", cols.length > 9 ? cols[9] : "");
                    map.put("normal", cols.length > 10 ? cols[10] : "");
                    map.put("cacat", cols.length > 11 ? cols[11] : "");
                    map.put("meninggal", cols.length > 12 ? cols[12] : "");
                    map.put("ket", cols.length > 13 ? cols[13] : "");
                    listRiwayat.add(map);
                    count++; // Tambah jumlah iterasi
                }
            }

            // CEK JIKA KOSONG: Berikan 1 baris data kosong agar tabel tetap tergambar 1 baris di JRXML
            if (listRiwayat.isEmpty()) {
                java.util.Map mapKosong = new java.util.HashMap();
                mapKosong.put("no", "");
                mapKosong.put("tgl_partus", "");
                mapKosong.put("umur_a", "");
                mapKosong.put("umur_p", "");
                mapKosong.put("umur_t", "");
                mapKosong.put("jenis", "");
                mapKosong.put("penolong_na", "");
                mapKosong.put("penolong_no", "");
                mapKosong.put("jk", "");
                mapKosong.put("bbl", "");
                mapKosong.put("normal", "");
                mapKosong.put("cacat", "");
                mapKosong.put("meninggal", "");
                mapKosong.put("ket", "");
                listRiwayat.add(mapKosong);
            }

            param.put("riwayat_kandungan", new net.sf.jasperreports.engine.data.JRMapCollectionDataSource(listRiwayat));
            // -------------------------------------------------------------------------

            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanPonek.jasper", "report",
                    "::[ Laporan Penilaian Awal Keperawatan Ponek ]::",
                    "select * from penilaian_awal_keperawatan_ponek "
                            + "inner join reg_periksa on penilaian_awal_keperawatan_ponek.no_rawat=reg_periksa.no_rawat "
                            + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join petugas on penilaian_awal_keperawatan_ponek.nip=petugas.nip "
                            + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "
                            + "left join bahasa_pasien on pasien.bahasa_pasien=bahasa_pasien.id "
                            + "left join cacat_fisik on pasien.cacat_fisik=cacat_fisik.id "
                            + "where penilaian_awal_keperawatan_ponek.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'",
                    param);
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }// GEN-LAST:event_BtnPrint1ActionPerformed
    
    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_TPasienActionPerformed

    private void StatusKehamilanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_StatusKehamilanKeyPressed
        Valid.pindah(evt, RPO, Gravida);
    }// GEN-LAST:event_StatusKehamilanKeyPressed

    private void TekananKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TekananKeyPressed
        Valid.pindah(evt, Abortus, Pupil);
    }// GEN-LAST:event_TekananKeyPressed

    private void PupilKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PupilKeyPressed
        Valid.pindah(evt, Tekanan, Neurosensorik);
    }// GEN-LAST:event_PupilKeyPressed

    private void NeurosensorikKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NeurosensorikKeyPressed
        Valid.pindah(evt, Pupil, Integumen);
    }// GEN-LAST:event_NeurosensorikKeyPressed

    private void IntegumenKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_IntegumenKeyPressed
        Valid.pindah(evt, Neurosensorik, Turgor);
    }// GEN-LAST:event_IntegumenKeyPressed

    private void TurgorKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TurgorKeyPressed
        Valid.pindah(evt, Integumen, Edema);
    }// GEN-LAST:event_TurgorKeyPressed

    private void EdemaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_EdemaKeyPressed
        Valid.pindah(evt, Turgor, Mukosa);
    }// GEN-LAST:event_EdemaKeyPressed

    private void MukosaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_MukosaKeyPressed
        Valid.pindah(evt, Edema, Perdarahan);
    }// GEN-LAST:event_MukosaKeyPressed

    private void PerdarahanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PerdarahanKeyPressed
        Valid.pindah(evt, Mukosa, JumlahPerdarahan);
    }// GEN-LAST:event_PerdarahanKeyPressed

    private void IntoksikasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_IntoksikasiKeyPressed
        Valid.pindah(evt, WarnaPerdarahan, BAB);
    }// GEN-LAST:event_IntoksikasiKeyPressed

    private void PsikologisKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PsikologisKeyPressed
        Valid.pindah(evt, LBAK, Jiwa);
    }// GEN-LAST:event_PsikologisKeyPressed

    private void JiwaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JiwaKeyPressed
        Valid.pindah(evt, Psikologis, Perilaku);
    }// GEN-LAST:event_JiwaKeyPressed

    private void PerilakuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PerilakuKeyPressed
        Valid.pindah(evt, Jiwa, Dilaporkan);
    }// GEN-LAST:event_PerilakuKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt, Sebutkan, TinggalDengan);
    }// GEN-LAST:event_HubunganKeyPressed

    private void StatusPernikahanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StatusPernikahanActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_StatusPernikahanActionPerformed

    private void KetPendidikanPJKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KetPendidikanPJKeyPressed
        Valid.pindah(evt, PendidikanPJ, Edukasi);
    }// GEN-LAST:event_KetPendidikanPJKeyPressed

    private void PendidikanPJKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_PendidikanPJKeyPressed
        Valid.pindah(evt, KetBudaya, KetPendidikanPJ);
    }// GEN-LAST:event_PendidikanPJKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt, TglAsuhan, KeluhanUtama);
    }// GEN-LAST:event_InformasiKeyPressed

    private void HPHTKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_HPHTKeyPressed
        // unused
    }// GEN-LAST:event_HPHTKeyPressed

    private void ParaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ParaKeyPressed
        Valid.pindah(evt, Gravida, Abortus);
    }// GEN-LAST:event_ParaKeyPressed

    private void AbortusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_AbortusKeyPressed
        Valid.pindah(evt, Para, Tekanan);
    }// GEN-LAST:event_AbortusKeyPressed

    private void GravidaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_GravidaKeyPressed
        Valid.pindah(evt, StatusKehamilan, Para);
    }// GEN-LAST:event_GravidaKeyPressed

    private void JumlahPerdarahanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JumlahPerdarahanKeyPressed
        Valid.pindah(evt, Perdarahan, WarnaPerdarahan);
    }// GEN-LAST:event_JumlahPerdarahanKeyPressed

    private void WarnaPerdarahanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WarnaPerdarahanKeyPressed
        Valid.pindah(evt, JumlahPerdarahan, Intoksikasi);
    }// GEN-LAST:event_WarnaPerdarahanKeyPressed

    private void BABKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BABKeyPressed
        Valid.pindah(evt, Intoksikasi, XBAB);
    }// GEN-LAST:event_BABKeyPressed

    private void XBABKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_XBABKeyPressed
        Valid.pindah(evt, BAB, KBAB);
    }// GEN-LAST:event_XBABKeyPressed

    private void KBABKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KBABKeyPressed
        Valid.pindah(evt, XBAB, WBAB);
    }// GEN-LAST:event_KBABKeyPressed

    private void WBABKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WBABKeyPressed
        Valid.pindah(evt, KBAB, BAK);
    }// GEN-LAST:event_WBABKeyPressed

    private void BAKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BAKKeyPressed
        Valid.pindah(evt, WBAB, XBAK);
    }// GEN-LAST:event_BAKKeyPressed

    private void XBAKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_XBAKKeyPressed
        Valid.pindah(evt, BAK, WBAK);
    }// GEN-LAST:event_XBAKKeyPressed

    private void WBAKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_WBAKKeyPressed
        Valid.pindah(evt, XBAK, LBAK);
    }// GEN-LAST:event_WBAKKeyPressed

    private void LBAKKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_LBAKKeyPressed
        Valid.pindah(evt, WBAK, Psikologis);
    }// GEN-LAST:event_LBAKKeyPressed

    private void DilaporkanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DilaporkanKeyPressed
        Valid.pindah(evt, Perilaku, Sebutkan);
    }// GEN-LAST:event_DilaporkanKeyPressed

    private void SebutkanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-LAST:event_SebutkanKeyPressed
        Valid.pindah(evt, Dilaporkan, Hubungan);
    }// GEN-LAST:event_SebutkanKeyPressed

    private void tbMasalahKeperawatanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbMasalahKeperawatanMouseClicked
        if (tabModeMasalah.getRowCount() != 0) {
            try {
                tampilRencana2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }// GEN-LAST:event_tbMasalahKeperawatanMouseClicked

    private void tbMasalahKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbMasalahKeperawatanKeyPressed
        if (tabModeMasalah.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariMasalah.setText("");
                TCariMasalah.requestFocus();
            }
        }
    }// GEN-LAST:event_tbMasalahKeperawatanKeyPressed

    private void tbMasalahKeperawatanKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbMasalahKeperawatanKeyReleased
        if (tabModeMasalah.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    tampilRencana2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }// GEN-LAST:event_tbMasalahKeperawatanKeyReleased

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatanIGD form = new MasterMasalahKeperawatanIGD(null, false);
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnAllMasalahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllMasalahActionPerformed
        TCari.setText("");
        tampilMasalah();
    }// GEN-LAST:event_BtnAllMasalahActionPerformed

    private void BtnAllMasalahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllMasalahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllMasalahActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCariMasalah, TCariMasalah);
        }
    }// GEN-LAST:event_BtnAllMasalahKeyPressed

    private void BtnCariMasalahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariMasalahActionPerformed
        tampilMasalah2();
    }// GEN-LAST:event_BtnCariMasalahActionPerformed

    private void BtnCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCariMasalahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilMasalah2();
        } else if ((evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            Rencana.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KetDokter.requestFocus();
        }
    }// GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariMasalahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilMasalah2();
        } else if ((evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            Rencana.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KetDokter.requestFocus();
        }
    }// GEN-LAST:event_TCariMasalahKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_RencanaKeyPressed
        Valid.pindah2(evt, TCariMasalah, BtnSimpan);
    }// GEN-LAST:event_RencanaKeyPressed

    private void TCariRencanaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariRencanaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilRencana2();
        } else if ((evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            BtnCariRencana.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TCariMasalah.requestFocus();
        }
    }// GEN-LAST:event_TCariRencanaKeyPressed

    private void BtnCariRencanaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariRencanaActionPerformed
        tampilRencana2();
    }// GEN-LAST:event_BtnCariRencanaActionPerformed

    private void BtnCariRencanaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCariRencanaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilRencana2();
        } else if ((evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TCariRencana.requestFocus();
        }
    }// GEN-LAST:event_BtnCariRencanaKeyPressed

    private void BtnAllRencanaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllRencanaActionPerformed
        TCariRencana.setText("");
        tampilRencana();
        tampilRencana2();
    }// GEN-LAST:event_BtnAllRencanaActionPerformed

    private void BtnAllRencanaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllRencanaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllRencanaActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCariRencana, TCariRencana);
        }
    }// GEN-LAST:event_BtnAllRencanaKeyPressed

    private void BtnTambahRencanaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnTambahRencanaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterRencanaKeperawatanIGD form = new MasterRencanaKeperawatanIGD(null, false);
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_BtnTambahRencanaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanPonek dialog = new RMPenilaianAwalKeperawatanPonek(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADL;
    private widget.ComboBox ATS;
    private widget.TextBox Abortus;
    private widget.TextBox Agama;
    private widget.ComboBox Aktifitas;
    private widget.ComboBox AlatBantu;
    private widget.ComboBox BAB;
    private widget.ComboBox BAK;
    private widget.ComboBox BJM;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnAllMasalah;
    private widget.Button BtnAllRencana;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariMasalah;
    private widget.Button BtnCariRencana;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.Button BtnTambahRencana;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.TextBox Dilaporkan;
    private widget.TextBox Durasi;
    private widget.ComboBox Edema;
    private widget.ComboBox Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Gravida;
    private widget.TextBox HPHT;
    private widget.ComboBox Hasil;
    private widget.ComboBox Hubungan;
    private widget.ComboBox Informasi;
    private widget.ComboBox Integumen;
    private widget.ComboBox Intoksikasi;
    private widget.ComboBox Jiwa;
    private widget.TextBox Jk;
    private widget.TextBox JumlahPerdarahan;
    private widget.ComboBox KBAB;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetAlatBantu;
    private widget.TextBox KetBudaya;
    private widget.TextBox KetDokter;
    private widget.TextBox KetEdukasi;
    private widget.TextBox KetLapor;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetPendidikanPJ;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetQuality;
    private widget.TextBox KetTinggal;
    private widget.TextBox LBAK;
    private widget.Label LCount;
    private widget.ComboBox Lapor;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.ComboBox MSA;
    private widget.ComboBox Menyebar;
    private widget.ComboBox Mukosa;
    private widget.ComboBox Neurosensorik;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.ComboBox PadaDokter;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.TextBox Para;
    private widget.TextBox Pekerjaan;
    private widget.TextBox Pembayaran;
    private widget.ComboBox PendidikanPJ;
    private widget.TextBox PendidikanPasien;
    private widget.ComboBox Perdarahan;
    private widget.ComboBox Perilaku;
    private widget.ComboBox Provokes;
    private widget.ComboBox Psikologis;
    private widget.ComboBox Pupil;
    private widget.ComboBox Quality;
    private widget.TextArea RPD;
    private widget.TextArea RPO;
    private widget.TextArea Rencana;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextBox Sebutkan;
    private widget.ComboBox SkalaNyeri;
    private widget.ComboBox StatusBudaya;
    private widget.ComboBox StatusKehamilan;
    private widget.TextBox StatusPernikahan;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TCariRencana;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRencanaKeperawatan;
    private widget.ComboBox Tekanan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDengan;
    private widget.ComboBox Turgor;
    private widget.ComboBox WBAB;
    private widget.ComboBox WBAK;
    private widget.TextBox WarnaPerdarahan;
    private widget.TextBox XBAB;
    private widget.TextBox XBAK;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
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
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbMasalahDetailMasalah;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnLaporanPenilaian;
    private widget.Table tbRencanaDetail;
    private widget.Table tbRencanaKeperawatan;

    // === ASSESMEN KEBIDANAN & KANDUNGAN ===
    private javax.swing.JSeparator jSeparatorKebidanan;
    private widget.Label lblKebidananTitle;
    private widget.Label lblDataSubjektif;
    private widget.Label lblRiwayatMenstruasi;
    private widget.Label lblMenarche;
    private widget.TextBox MenarcheUmur;
    private widget.Label lblMenarcheTahun;
    private widget.Label lblSiklus;
    private widget.TextBox SiklusMenstruasi;
    private widget.Label lblSiklusHari;
    private widget.ComboBox KeteratanMenstruasi;
    private widget.Label lblLama;
    private widget.TextBox LamaMenstruasi;
    private widget.Label lblLamaHari;
    private widget.TextBox KeluhanHaid;
    private widget.Label lblRiwayatKehamilanSekarang;
    private widget.Label lblHamilKe;
    private widget.TextBox HamilKe;
    private widget.Label lblUK;
    private widget.TextBox UKMinggu;
    private widget.Label lblUKMinggu;
    private widget.TextBox UKHari;
    private widget.Label lblUKHari;
    private widget.Label lblHPHTKbd;
    private widget.TextBox HPHTKbd;
    private widget.Label lblHPL;
    private widget.TextBox HPLKbd;
    private widget.Label lblBBSebelum;
    private widget.TextBox BBSebelumHamil;
    private widget.Label lblBBSebelumKg;
    private widget.Label lblBBSekarang;
    private widget.TextBox BBSekarang;
    private widget.Label lblBBSekarangKg;
    private widget.Label lblTBKbd;
    private widget.TextBox TBKbd;
    private widget.Label lblTBCm;
    private widget.Label lblPeriksaANC;
    private widget.TextBox PeriksaANC;
    private widget.Label lblANCKali;
    private widget.ComboBox ANCDi;
    private widget.Label lblANCLainnya;
    private widget.TextBox ANCLainnya;
    private widget.Label lblImunisasiTT;
    private widget.TextBox ImunisasiTT;
    private widget.Label lblTTKali;
    private widget.Label lblTglTT1;
    private widget.TextBox TglTT1;
    private widget.Label lblTglTT2;
    private widget.TextBox TglTT2;
    private widget.Label lblGerakanJanin;
    private widget.TextBox GerakanJanin;
    private widget.Label lblGerakanKali;
    private widget.Label lblRiwayatKehamilanLalu;
    private javax.swing.JScrollPane scrollRiwayatKehamilan;
    private javax.swing.JTable tbRiwayatKehamilan;

    // === B. OBJEKTIF VARIABLES ===
    private widget.Label lblObjektifTitle;
    private widget.Label lblPmxUmum;
    private widget.Label lblKUPonek;
    private widget.TextBox ObjKU;
    private widget.Label lblKesadaranPonek;
    private widget.TextBox ObjKesadaran;
    private widget.Label lblGCSPonek;
    private widget.Label lblGCSE;
    private widget.TextBox ObjGCSE;
    private widget.Label lblGCSV;
    private widget.TextBox ObjGCSV;
    private widget.Label lblGCSM;
    private widget.TextBox ObjGCSM;
    private widget.Label lblTDPonek;
    private widget.TextBox ObjTDSistol;
    private widget.Label lblTDSlash;
    private widget.TextBox ObjTDDiastol;
    private widget.Label lblTDMmHg;
    private widget.Label lblHRPonek;
    private widget.TextBox ObjHR;
    private widget.Label lblHRXMenit;
    private widget.Label lblRRPonek;
    private widget.TextBox ObjRR;
    private widget.Label lblRRXMenit;
    private widget.Label lblSPonek;
    private widget.TextBox ObjSuhu;
    private widget.Label lblSC;
    private widget.Label lblSpO2Ponek;
    private widget.TextBox ObjSpO2;
    private widget.Label lblSpO2Persen;
    
    // 2. Pemeriksaan Fisik
    private widget.Label lblPmxFisik;
    private widget.Label lblKepala;
    private widget.ComboBox ObjKepala;
    private widget.Label lblMata;
    private widget.ComboBox ObjMata;
    private widget.Label lblLeher;
    private widget.ComboBox ObjLeher;
    private widget.Label lblThorax;
    private widget.ComboBox ObjThorax;
    private widget.Label lblAbdomen;
    private widget.ComboBox ObjAbdomen;
    private widget.Label lblInspeksi;
    private widget.ComboBox ObjInspeksi;
    private widget.Label lblPalpasiTFU;
    private widget.ComboBox ObjTFU;
    private widget.Label lblTFUcm;
    private widget.Label lblLeopold1;
    private widget.ComboBox ObjLeopold1;
    private widget.Label lblLeopold2;
    private widget.ComboBox ObjLeopold2;
    private widget.Label lblLeopold3;
    private widget.ComboBox ObjLeopold3;
    private widget.Label lblLeopold4;
    private widget.ComboBox ObjLeopold4;
    private widget.Label lblTBBJ;
    private widget.TextBox ObjTBBJ;
    private widget.Label lblHis;
    private widget.TextBox ObjHis;
    private widget.Label lblAuskultasi;
    private widget.TextBox ObjAuskultasi;
    
    // f. Genitalia
    private widget.Label lblGenitalia;
    private widget.TextBox ObjPukul;
    private widget.Label lblPukulWIB2;
    private widget.Label lblPengeluaran;
    private widget.TextBox ObjPengeluaran;
    private widget.Label lblPmxDalam;
    private widget.TextBox ObjPmxDalam;
    private widget.Label lblInspekulo;
    private widget.TextBox ObjInspekulo;
    
    // g. Ekstremitas
    private widget.Label lblEkstremitas;
    private widget.TextBox ObjOedema1;
    private widget.Label lblOedemaSlash;
    private widget.TextBox ObjOedema2;
    private widget.Label lblOedemaVarises;
    private widget.TextBox ObjVarises1;
    private widget.Label lblVarisesSlash;
    private widget.TextBox ObjVarises2;
    private widget.Label lblVarisesReflek;
    private widget.TextBox ObjReflek1;
    private widget.Label lblReflekSlash;
    private widget.TextBox ObjReflek2;

    // End of variables declaration//GEN-END:variables

    private widget.Label jLabelPrimarySurvey;
    private javax.swing.JSeparator jSeparatorPrimary;
    private widget.Label jLabelAirway;
    private widget.ComboBox Airway;
    private widget.Label jLabelBreathing;
    private widget.ComboBox Breathing;
    private widget.Label jLabelSpo2;
    private widget.TextBox Spo2;
    private widget.Label jLabelNadi;
    private widget.ComboBox Nadi;
    private widget.Label jLabelCRT;
    private widget.ComboBox CRT;
    private widget.Label jLabelWarnaKulit;
    private widget.ComboBox WarnaKulit;
    private widget.Label jLabelCirculationPerdarahan;
    private widget.ComboBox CirculationPerdarahan;
    private widget.Label jLabelTurgorKulit;
    private widget.ComboBox TurgorKulit;
    private widget.Label jLabelResponNeurologi;
    private widget.ComboBox ResponNeurologi;
    private widget.Label jLabelPupilNeurologi;
    private widget.ComboBox PupilNeurologi;
    private widget.Label jLabelReflek;
    private widget.TextBox Reflek;
    private widget.Label jLabelGCS;
    private widget.TextBox GCS;

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            String sql = "select reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.agama, " +
                "bahasa_pasien.nama_bahasa, cacat_fisik.nama_cacat, pasien.tgl_lahir, penilaian_awal_keperawatan_ponek.tanggal, " +
                "penilaian_awal_keperawatan_ponek.informasi, penilaian_awal_keperawatan_ponek.keluhan_utama, " +
                "penilaian_awal_keperawatan_ponek.rpd, penilaian_awal_keperawatan_ponek.rpk, penilaian_awal_keperawatan_ponek.rpo, " +
                "penilaian_awal_keperawatan_ponek.alergi, penilaian_awal_keperawatan_ponek.airway, " +
                "penilaian_awal_keperawatan_ponek.breathing, penilaian_awal_keperawatan_ponek.spo2, " +
                "penilaian_awal_keperawatan_ponek.nadi, penilaian_awal_keperawatan_ponek.crt, " +
                "penilaian_awal_keperawatan_ponek.warna_kulit, penilaian_awal_keperawatan_ponek.circulation_perdarahan, " +
                "penilaian_awal_keperawatan_ponek.turgor_kulit, penilaian_awal_keperawatan_ponek.respon_neurologi, " +
                "penilaian_awal_keperawatan_ponek.pupil_neurologi, penilaian_awal_keperawatan_ponek.reflek, " +
                "penilaian_awal_keperawatan_ponek.gcs, penilaian_awal_keperawatan_ponek.tekanan, " +
                "penilaian_awal_keperawatan_ponek.pupil, penilaian_awal_keperawatan_ponek.neurosensorik, " +
                "penilaian_awal_keperawatan_ponek.integumen, penilaian_awal_keperawatan_ponek.turgor, " +
                "penilaian_awal_keperawatan_ponek.edema, penilaian_awal_keperawatan_ponek.mukosa, " +
                "penilaian_awal_keperawatan_ponek.perdarahan, penilaian_awal_keperawatan_ponek.jumlah_perdarahan, " +
                "penilaian_awal_keperawatan_ponek.warna_perdarahan, penilaian_awal_keperawatan_ponek.intoksikasi, " +
                "penilaian_awal_keperawatan_ponek.bab, penilaian_awal_keperawatan_ponek.xbab, penilaian_awal_keperawatan_ponek.kbab, " +
                "penilaian_awal_keperawatan_ponek.wbab, penilaian_awal_keperawatan_ponek.bak, penilaian_awal_keperawatan_ponek.xbak, " +
                "penilaian_awal_keperawatan_ponek.wbak, penilaian_awal_keperawatan_ponek.lbak, penilaian_awal_keperawatan_ponek.menarche_umur, " +
                "penilaian_awal_keperawatan_ponek.siklus_menstruasi, penilaian_awal_keperawatan_ponek.keteratan_menstruasi, " +
                "penilaian_awal_keperawatan_ponek.lama_menstruasi, penilaian_awal_keperawatan_ponek.keluhan_haid, " +
                "penilaian_awal_keperawatan_ponek.hamil_ke, penilaian_awal_keperawatan_ponek.uk_minggu, " +
                "penilaian_awal_keperawatan_ponek.uk_hari, penilaian_awal_keperawatan_ponek.hpht_kbd, " +
                "penilaian_awal_keperawatan_ponek.hpl_kbd, penilaian_awal_keperawatan_ponek.bb_sebelum_hamil, " +
                "penilaian_awal_keperawatan_ponek.bb_sekarang, penilaian_awal_keperawatan_ponek.tb_kbd, " +
                "penilaian_awal_keperawatan_ponek.periksa_anc, penilaian_awal_keperawatan_ponek.anc_di, " +
                "penilaian_awal_keperawatan_ponek.anc_lainnya, penilaian_awal_keperawatan_ponek.imunisasi_tt, " +
                "penilaian_awal_keperawatan_ponek.tgl_tt1, penilaian_awal_keperawatan_ponek.tgl_tt2, " +
                "penilaian_awal_keperawatan_ponek.gerakan_janin, penilaian_awal_keperawatan_ponek.riwayat_kehamilan, " +
                "penilaian_awal_keperawatan_ponek.obj_ku, penilaian_awal_keperawatan_ponek.obj_kesadaran, " +
                "penilaian_awal_keperawatan_ponek.obj_gcs_e, penilaian_awal_keperawatan_ponek.obj_gcs_v, " +
                "penilaian_awal_keperawatan_ponek.obj_gcs_m, penilaian_awal_keperawatan_ponek.obj_td_sistol, " +
                "penilaian_awal_keperawatan_ponek.obj_td_diastol, penilaian_awal_keperawatan_ponek.obj_hr, " +
                "penilaian_awal_keperawatan_ponek.obj_rr, penilaian_awal_keperawatan_ponek.obj_suhu, " +
                "penilaian_awal_keperawatan_ponek.obj_spo2, penilaian_awal_keperawatan_ponek.obj_kepala, " +
                "penilaian_awal_keperawatan_ponek.obj_mata, penilaian_awal_keperawatan_ponek.obj_leher, " +
                "penilaian_awal_keperawatan_ponek.obj_thorax, penilaian_awal_keperawatan_ponek.obj_abdomen, " +
                "penilaian_awal_keperawatan_ponek.obj_inspeksi, penilaian_awal_keperawatan_ponek.obj_tfu, " +
                "penilaian_awal_keperawatan_ponek.obj_leopold1, penilaian_awal_keperawatan_ponek.obj_leopold2, " +
                "penilaian_awal_keperawatan_ponek.obj_leopold3, penilaian_awal_keperawatan_ponek.obj_leopold4, " +
                "penilaian_awal_keperawatan_ponek.obj_tbbj, penilaian_awal_keperawatan_ponek.obj_his, " +
                "penilaian_awal_keperawatan_ponek.obj_auskultasi, penilaian_awal_keperawatan_ponek.obj_pukul, " +
                "penilaian_awal_keperawatan_ponek.obj_pengeluaran, penilaian_awal_keperawatan_ponek.obj_pmxdalam, " +
                "penilaian_awal_keperawatan_ponek.obj_inspekulo, penilaian_awal_keperawatan_ponek.obj_oedema1, " +
                "penilaian_awal_keperawatan_ponek.obj_oedema2, penilaian_awal_keperawatan_ponek.obj_varises1, " +
                "penilaian_awal_keperawatan_ponek.obj_varises2, penilaian_awal_keperawatan_ponek.obj_reflek1, " +
                "penilaian_awal_keperawatan_ponek.obj_reflek2, penilaian_awal_keperawatan_ponek.nip, petugas.nama " +
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join penilaian_awal_keperawatan_ponek on reg_periksa.no_rawat=penilaian_awal_keperawatan_ponek.no_rawat " +
                "inner join petugas on penilaian_awal_keperawatan_ponek.nip=petugas.nip " +
                "left join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien " +
                "left join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik ";

            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(sql + "where penilaian_awal_keperawatan_ponek.tanggal between ? and ? order by penilaian_awal_keperawatan_ponek.tanggal");
            } else {
                ps = koneksi.prepareStatement(sql + "where penilaian_awal_keperawatan_ponek.tanggal between ? and ? " +
                        "and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ?) " +
                        "order by penilaian_awal_keperawatan_ponek.tanggal");
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
                }
                
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25),
                        rs.getString(26), rs.getString(27), rs.getString(28), rs.getString(29), rs.getString(30),
                        rs.getString(31), rs.getString(32), rs.getString(33), rs.getString(34), rs.getString(35),
                        rs.getString(36), rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40),
                        rs.getString(41), rs.getString(42), rs.getString(43), rs.getString(44), rs.getString(45),
                        rs.getString(46), rs.getString(47), rs.getString(48), rs.getString(49), rs.getString(50),
                        rs.getString(51), rs.getString(52), rs.getString(53), rs.getString(54), rs.getString(55),
                        rs.getString(56), rs.getString(57), rs.getString(58), rs.getString(59), rs.getString(60),
                        rs.getString(61), rs.getString(62), rs.getString(63), rs.getString(64), rs.getString(65),
                        rs.getString(66), rs.getString(67), rs.getString(68), rs.getString(69), rs.getString(70),
                        rs.getString(71), rs.getString(72), rs.getString(73), rs.getString(74), rs.getString(75),
                        rs.getString(76), rs.getString(77), rs.getString(78), rs.getString(79), rs.getString(80),
                        rs.getString(81), rs.getString(82), rs.getString(83), rs.getString(84), rs.getString(85),
                        rs.getString(86), rs.getString(87), rs.getString(88), rs.getString(89), rs.getString(90),
                        rs.getString(91), rs.getString(92), rs.getString(93), rs.getString(94), rs.getString(95)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ponek Load: " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif Ponek Prep: " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {

        // TNoRw.setText("");
        // TNoRM.setText("");
        // TPasien.setText("");
//        Jk.setText("");
        Agama.setText("");
        Bahasa.setText("");
        CacatFisik.setText("");
//        TglLahir.setText("");
        TglAsuhan.setDate(new Date());
        Informasi.setSelectedIndex(0);
        KeluhanUtama.setText("");
        RPD.setText("");
        RPO.setText("");
        Airway.setSelectedIndex(0);
        Breathing.setSelectedIndex(0);
        Spo2.setText("");
        Nadi.setSelectedIndex(0);
        CRT.setSelectedIndex(0);
        WarnaKulit.setSelectedIndex(0);
        CirculationPerdarahan.setSelectedIndex(0);
        TurgorKulit.setSelectedIndex(0);
        ResponNeurologi.setSelectedIndex(0);
        PupilNeurologi.setSelectedIndex(0);
        Reflek.setText("");
        GCS.setText("");
        Tekanan.setSelectedIndex(0);
        Pupil.setSelectedIndex(0);
        Neurosensorik.setSelectedIndex(0);
        Integumen.setSelectedIndex(0);
        Turgor.setSelectedIndex(0);
        Edema.setSelectedIndex(0);
        Mukosa.setSelectedIndex(0);
        Perdarahan.setSelectedIndex(0);
        JumlahPerdarahan.setText("");
        WarnaPerdarahan.setText("");
        BAB.setSelectedIndex(0);
        KBAB.setSelectedIndex(0);
        WBAB.setSelectedIndex(0);
        BAK.setSelectedIndex(0);
        WBAK.setSelectedIndex(0);
        
        MenarcheUmur.setText("");
        SiklusMenstruasi.setText("");
        KeteratanMenstruasi.setSelectedIndex(0);
        LamaMenstruasi.setText("");
        KeluhanHaid.setText("");
        HamilKe.setText("");
        UKMinggu.setText("");
        UKHari.setText("");
        HPHTKbd.setText("");
        HPLKbd.setText("");
        BBSebelumHamil.setText("");
        BBSekarang.setText("");
        TBKbd.setText("");
        PeriksaANC.setText("");
        ANCDi.setSelectedIndex(0);
        ANCLainnya.setText("");
        ImunisasiTT.setText("");
        TglTT1.setText("");
        TglTT2.setText("");
        GerakanJanin.setText("");
        DefaultTableModel modelRiw = (DefaultTableModel) tbRiwayatKehamilan.getModel();
        Valid.tabelKosong(modelRiw);
        for (int i = 0; i < 10; i++) {
            modelRiw.addRow(new Object[]{ (i+1)+".", "", "", "", "", "", "", "", "", "", "", "", "" });
        }
        
        ObjKU.setText("");
        ObjKesadaran.setText("");
        ObjGCSE.setText("");
        ObjGCSV.setText("");
        ObjGCSM.setText("");
        ObjTDSistol.setText("");
        ObjTDDiastol.setText("");
        ObjHR.setText("");
        ObjRR.setText("");
        ObjSuhu.setText("");
        ObjSpO2.setText("");
        ObjKepala.setSelectedIndex(0);
        ObjMata.setSelectedIndex(0);
        ObjLeher.setSelectedIndex(0);
        ObjThorax.setSelectedIndex(0);
        ObjAbdomen.setSelectedIndex(0);
        ObjInspeksi.setSelectedIndex(0);
        ObjTFU.setSelectedIndex(0);
        ObjLeopold1.setSelectedIndex(0);
        ObjLeopold2.setSelectedIndex(0);
        ObjLeopold3.setSelectedIndex(0);
        ObjLeopold4.setSelectedIndex(0);
        ObjTBBJ.setText("");
        ObjHis.setText("");
        ObjAuskultasi.setText("");
        ObjPukul.setText("");
        ObjPengeluaran.setText("");
        ObjPmxDalam.setText("");
        ObjInspekulo.setText("");
        ObjOedema1.setText("");
        ObjOedema2.setText("");
        ObjVarises1.setText("");
        ObjVarises2.setText("");
        ObjReflek1.setText("");
        ObjReflek2.setText("");

    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            String no_rawat = tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString();
            TNoRw.setText(no_rawat);
            isRawat(); // Isi otomatis No RM, Pasien, Tgl Lahir, Agama, JK dll
            
            try {
                PreparedStatement p = koneksi.prepareStatement("select * from penilaian_awal_keperawatan_ponek where no_rawat=?");
                p.setString(1, no_rawat);
                ResultSet r = p.executeQuery();
                if (r.next()) {
                    Valid.SetTgl2(TglAsuhan, r.getString("tanggal"));
                    Informasi.setSelectedItem(r.getString("informasi"));
                    KeluhanUtama.setText(r.getString("keluhan_utama"));
                    RPD.setText(r.getString("rpd"));
                    RPO.setText(r.getString("rpo"));
                    StatusKehamilan.setSelectedItem(r.getString("status_kehamilan"));
                    Gravida.setText(r.getString("gravida"));
                    Para.setText(r.getString("para"));
                    Abortus.setText(r.getString("abortus"));
                    HPHT.setText(r.getString("hpht"));
                    
                    Airway.setSelectedItem(r.getString("airway"));
                    Breathing.setSelectedItem(r.getString("breathing"));
                    Spo2.setText(r.getString("spo2"));
                    Nadi.setSelectedItem(r.getString("nadi"));
                    CRT.setSelectedItem(r.getString("crt"));
                    WarnaKulit.setSelectedItem(r.getString("warna_kulit"));
                    CirculationPerdarahan.setSelectedItem(r.getString("circulation_perdarahan"));
                    TurgorKulit.setSelectedItem(r.getString("turgor_kulit"));
                    ResponNeurologi.setSelectedItem(r.getString("respon_neurologi"));
                    PupilNeurologi.setSelectedItem(r.getString("pupil_neurologi"));
                    Reflek.setText(r.getString("reflek"));
                    GCS.setText(r.getString("gcs"));
                    
                    Tekanan.setSelectedItem(r.getString("tekanan"));
                    Pupil.setSelectedItem(r.getString("pupil"));
                    Neurosensorik.setSelectedItem(r.getString("neurosensorik"));
                    Integumen.setSelectedItem(r.getString("integumen"));
                    Turgor.setSelectedItem(r.getString("turgor"));
                    Edema.setSelectedItem(r.getString("edema"));
                    Mukosa.setSelectedItem(r.getString("mukosa"));
                    Perdarahan.setSelectedItem(r.getString("perdarahan"));
                    JumlahPerdarahan.setText(r.getString("jumlah_perdarahan"));
                    WarnaPerdarahan.setText(r.getString("warna_perdarahan"));
                    Intoksikasi.setSelectedItem(r.getString("intoksikasi"));
                    BAB.setSelectedItem(r.getString("bab"));
                    XBAB.setText(r.getString("xbab"));
                    KBAB.setSelectedItem(r.getString("kbab"));
                    WBAB.setSelectedItem(r.getString("wbab"));
                    BAK.setSelectedItem(r.getString("bak"));
                    XBAK.setText(r.getString("xbak"));
                    WBAK.setSelectedItem(r.getString("wbak"));
                    LBAK.setText(r.getString("lbak"));
                    
                    MenarcheUmur.setText(r.getString("menarche_umur"));
                    SiklusMenstruasi.setText(r.getString("siklus_menstruasi"));
                    KeteratanMenstruasi.setSelectedItem(r.getString("keteratan_menstruasi"));
                    LamaMenstruasi.setText(r.getString("lama_menstruasi"));
                    KeluhanHaid.setText(r.getString("keluhan_haid"));
                    HamilKe.setText(r.getString("hamil_ke"));
                    UKMinggu.setText(r.getString("uk_minggu"));
                    UKHari.setText(r.getString("uk_hari"));
                    HPHTKbd.setText(r.getString("hpht_kbd"));
                    HPLKbd.setText(r.getString("hpl_kbd"));
                    BBSebelumHamil.setText(r.getString("bb_sebelum_hamil"));
                    BBSekarang.setText(r.getString("bb_sekarang"));
                    TBKbd.setText(r.getString("tb_kbd"));
                    PeriksaANC.setText(r.getString("periksa_anc"));
                    ANCDi.setSelectedItem(r.getString("anc_di"));
                    ANCLainnya.setText(r.getString("anc_lainnya"));
                    ImunisasiTT.setText(r.getString("imunisasi_tt"));
                    TglTT1.setText(r.getString("tgl_tt1"));
                    TglTT2.setText(r.getString("tgl_tt2"));
                    GerakanJanin.setText(r.getString("gerakan_janin"));
                    
                    // Parsing tabel Riwayat Kehamilan Kandungan
                    String riwKandungan = r.getString("riwayat_kehamilan");
                    DefaultTableModel modelRiw = (DefaultTableModel) tbRiwayatKehamilan.getModel();
                    Valid.tabelKosong(modelRiw);
                    if (riwKandungan != null && !riwKandungan.isEmpty()) {
                        String[] rows = riwKandungan.split(";");
                        for (String row : rows) {
                            if (!row.trim().isEmpty()) {
                                String[] cols = row.split("\\|");
                                modelRiw.addRow(new Object[]{
                                    cols.length > 0 ? cols[0] : "", cols.length > 1 ? cols[1] : "", cols.length > 2 ? cols[2] : "",
                                    cols.length > 3 ? cols[3] : "", cols.length > 4 ? cols[4] : "", cols.length > 5 ? cols[5] : "",
                                    cols.length > 6 ? cols[6] : "", cols.length > 7 ? cols[7] : "", cols.length > 8 ? cols[8] : "",
                                    cols.length > 9 ? cols[9] : "", cols.length > 10 ? cols[10] : "", cols.length > 11 ? cols[11] : "",
                                    cols.length > 12 ? cols[12] : "", cols.length > 13 ? cols[13] : ""
                                });
                            }
                        }
                    }
                    
                    ObjKU.setText(r.getString("obj_ku"));
                    ObjKesadaran.setText(r.getString("obj_kesadaran"));
                    ObjGCSE.setText(r.getString("obj_gcs_e"));
                    ObjGCSV.setText(r.getString("obj_gcs_v"));
                    ObjGCSM.setText(r.getString("obj_gcs_m"));
                    ObjTDSistol.setText(r.getString("obj_td_sistol"));
                    ObjTDDiastol.setText(r.getString("obj_td_diastol"));
                    ObjHR.setText(r.getString("obj_hr"));
                    ObjRR.setText(r.getString("obj_rr"));
                    ObjSuhu.setText(r.getString("obj_suhu"));
                    ObjSpO2.setText(r.getString("obj_spo2"));
                    ObjKepala.setSelectedItem(r.getString("obj_kepala"));
                    ObjMata.setSelectedItem(r.getString("obj_mata"));
                    ObjLeher.setSelectedItem(r.getString("obj_leher"));
                    ObjThorax.setSelectedItem(r.getString("obj_thorax"));
                    ObjAbdomen.setSelectedItem(r.getString("obj_abdomen"));
                    ObjInspeksi.setSelectedItem(r.getString("obj_inspeksi"));
                    ObjTFU.setSelectedItem(r.getString("obj_tfu"));
                    ObjLeopold1.setSelectedItem(r.getString("obj_leopold1"));
                    ObjLeopold2.setSelectedItem(r.getString("obj_leopold2"));
                    ObjLeopold3.setSelectedItem(r.getString("obj_leopold3"));
                    ObjLeopold4.setSelectedItem(r.getString("obj_leopold4"));
                    ObjTBBJ.setText(r.getString("obj_tbbj"));
                    ObjHis.setText(r.getString("obj_his"));
                    ObjAuskultasi.setText(r.getString("obj_auskultasi"));
                    ObjPukul.setText(r.getString("obj_pukul"));
                    ObjPengeluaran.setText(r.getString("obj_pengeluaran"));
                    ObjPmxDalam.setText(r.getString("obj_pmxdalam"));
                    ObjInspekulo.setText(r.getString("obj_inspekulo"));
                    ObjOedema1.setText(r.getString("obj_oedema1"));
                    ObjOedema2.setText(r.getString("obj_oedema2"));
                    ObjVarises1.setText(r.getString("obj_varises1"));
                    ObjVarises2.setText(r.getString("obj_varises2"));
                    ObjReflek1.setText(r.getString("obj_reflek1"));
                    ObjReflek2.setText(r.getString("obj_reflek2"));
                    
                    KdPetugas.setText(r.getString("nip"));
                    NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
                    
                    Psikologis.setSelectedItem(r.getString("psikologis"));
                    Jiwa.setSelectedItem(r.getString("jiwa"));
                    Perilaku.setSelectedItem(r.getString("perilaku"));
                    Dilaporkan.setText(r.getString("dilaporkan"));
                    Sebutkan.setText(r.getString("sebutkan"));
                    Hubungan.setSelectedItem(r.getString("hubungan"));
                    TinggalDengan.setSelectedItem(r.getString("tinggal_dengan"));
                    KetTinggal.setText(r.getString("ket_tinggal"));
                    StatusBudaya.setSelectedItem(r.getString("budaya"));
                    KetBudaya.setText(r.getString("ket_budaya"));
                    PendidikanPJ.setSelectedItem(r.getString("pendidikan_pj"));
                    KetPendidikanPJ.setText(r.getString("ket_pendidikan_pj"));
                    Edukasi.setSelectedItem(r.getString("edukasi"));
                    KetEdukasi.setText(r.getString("ket_edukasi"));
                    ADL.setSelectedItem(r.getString("kemampuan"));
                    Aktifitas.setSelectedItem(r.getString("aktifitas"));
                    AlatBantu.setSelectedItem(r.getString("alat_bantu"));
                    KetAlatBantu.setText(r.getString("ket_bantu"));
                    
                    ATS.setSelectedItem(r.getString("berjalan_a"));
                    BJM.setSelectedItem(r.getString("berjalan_b"));
                    MSA.setSelectedItem(r.getString("berjalan_c"));
                    Hasil.setSelectedItem(r.getString("hasil"));
                    Lapor.setSelectedItem(r.getString("lapor"));
                    KetLapor.setText(r.getString("ket_lapor"));
                    
                    SkalaNyeri.setSelectedItem(r.getString("skala_nyeri"));
                    Nyeri.setSelectedItem(r.getString("nyeri"));
                    Provokes.setSelectedItem(r.getString("provokes"));
                    KetProvokes.setText(r.getString("ket_provokes"));
                    Quality.setSelectedItem(r.getString("quality"));
                    KetQuality.setText(r.getString("ket_quality"));
                    Lokasi.setText(r.getString("lokasi"));
                    Menyebar.setSelectedItem(r.getString("menyebar"));
                    Durasi.setText(r.getString("durasi"));
                    NyeriHilang.setSelectedItem(r.getString("nyeri_hilang"));
                    KetNyeri.setText(r.getString("ket_nyeri"));
                    PadaDokter.setSelectedItem(r.getString("pada_dokter"));
                    KetDokter.setText(r.getString("ket_dokter"));
                    Rencana.setText(r.getString("rencana"));
                }
                r.close();
                p.close();
            } catch (Exception e) {
                System.out.println("Error getData Ponek: " + e);
            }
        }
    }

    private void isRawat() {

        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,"
                            +
                            "pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,reg_periksa.tgl_registrasi, "
                            +
                            "pasien.stts_nikah,pasien.pekerjaan,pasien.pnd,penjab.png_jawab " +
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                            "left join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien " +
                            "left join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik " +
                            "left join penjab on penjab.kd_pj=reg_periksa.kd_pj " +
                            "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText().trim());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TNoRM1.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TPasien1.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    CacatFisik.setText(rs.getString("nama_cacat"));
                    StatusPernikahan.setText(rs.getString("stts_nikah"));
                    Pekerjaan.setText(rs.getString("pekerjaan"));
                    PendidikanPasien.setText(rs.getString("pnd"));
                    Pembayaran.setText(rs.getString("png_jawab"));
                } else {
                    System.out.println("NO DATA found in isRawat for: " + TNoRw.getText());
                }
            } catch (Exception e) {
                System.out.println("Notif isRawat: " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif Prep isRawat: " + e);
        }

    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_igd());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_igd());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_igd());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_igd());
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan_igd());
        BtnTambahRencana.setEnabled(akses.getmaster_rencana_keperawatan_igd());
        if (akses.getjml2() >= 1) {
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if (NmPetugas.getText().equals("")) {
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }
    }

    public void setTampil() {
        TabRawat.setSelectedIndex(1);
    }

    private void tampilMasalah() {
        try {
            Valid.tabelKosong(tabModeMasalah);
            file = new File("./cache/masalahkeperawatanigd.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem = "";
            ps = koneksi.prepareStatement(
                    "select * from master_masalah_keperawatan_igd order by master_masalah_keperawatan_igd.kode_masalah");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeMasalah.addRow(new Object[] { false, rs.getString(1), rs.getString(2) });
                    iyem = iyem + "{\"KodeMasalah\":\"" + rs.getString(1) + "\",\"NamaMasalah\":\"" + rs.getString(2)
                            + "\"},";
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
            fileWriter.write("{\"masalahkeperawatanigd\":[" + iyem.substring(0, iyem.length() - 1) + "]}");
            fileWriter.flush();
            fileWriter.close();
            iyem = null;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilMasalah2() {
        try {
            jml = 0;
            for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                if (tbMasalahKeperawatan.getValueAt(i, 0).toString().equals("true")) {
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
            for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                if (tbMasalahKeperawatan.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = tbMasalahKeperawatan.getValueAt(i, 1).toString();
                    masalah[index] = tbMasalahKeperawatan.getValueAt(i, 2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeMasalah);

            for (i = 0; i < jml; i++) {
                tabModeMasalah.addRow(new Object[] {
                        pilih[i], kode[i], masalah[i]
                });
            }

            myObj = new FileReader("./cache/masalahkeperawatanigd.iyem");
            root = mapper.readTree(myObj);
            response = root.path("masalahkeperawatanigd");
            if (response.isArray()) {
                for (JsonNode list : response) {
                    if (list.path("KodeMasalah").asText().toLowerCase().contains(TCariMasalah.getText().toLowerCase())
                            || list.path("NamaMasalah").asText().toLowerCase()
                                    .contains(TCariMasalah.getText().toLowerCase())) {
                        tabModeMasalah.addRow(new Object[] {
                                false, list.path("KodeMasalah").asText(), list.path("NamaMasalah").asText()
                        });
                    }
                }
            }
            myObj.close();
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilRencana() {
        try {
            file = new File("./cache/rencanakeperawatanigd.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem = "";
            ps = koneksi.prepareStatement(
                    "select * from master_rencana_keperawatan_igd order by master_rencana_keperawatan_igd.kode_rencana");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    iyem = iyem + "{\"KodeMasalah\":\"" + rs.getString(1) + "\",\"KodeRencana\":\"" + rs.getString(2)
                            + "\",\"NamaRencana\":\"" + rs.getString(3) + "\"},";
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
            fileWriter.write("{\"rencanakeperawatanigd\":[" + iyem.substring(0, iyem.length() - 1) + "]}");
            fileWriter.flush();
            fileWriter.close();
            iyem = null;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilRencana2() {
        try {
            jml = 0;
            for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                if (tbRencanaKeperawatan.getValueAt(i, 0).toString().equals("true")) {
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
            for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                if (tbRencanaKeperawatan.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode[index] = tbRencanaKeperawatan.getValueAt(i, 1).toString();
                    masalah[index] = tbRencanaKeperawatan.getValueAt(i, 2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeRencana);

            for (i = 0; i < jml; i++) {
                tabModeRencana.addRow(new Object[] {
                        pilih[i], kode[i], masalah[i]
                });
            }

            myObj = new FileReader("./cache/rencanakeperawatanigd.iyem");
            root = mapper.readTree(myObj);
            response = root.path("rencanakeperawatanigd");
            if (response.isArray()) {
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if (tbMasalahKeperawatan.getValueAt(i, 0).toString().equals("true")) {
                        for (JsonNode list : response) {
                            if (list.path("KodeMasalah").asText().trim()
                                    .equalsIgnoreCase(tbMasalahKeperawatan.getValueAt(i, 1).toString().trim())
                                    && list.path("NamaRencana").asText().toLowerCase()
                                            .contains(TCariRencana.getText().toLowerCase())) {
                                tabModeRencana.addRow(new Object[] {
                                        false, list.path("KodeRencana").asText(), list.path("NamaRencana").asText()
                                });
                            }
                        }
                    }
                }
            }
            myObj.close();
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void isMenu() {
        if (ChkAccor.isSelected() == true) {
        } else if (ChkAccor.isSelected() == false) {
        }
    }

    private void ganti() {
        if (Sequel.mengedittf("penilaian_awal_keperawatan_ponek", "no_rawat=?",
                "no_rawat=?,tanggal=?,keluhan_utama=?,rpd=?,rpo=?,status_kehamilan=?,gravida=?," +
                        "para=?,abortus=?,hpht=?,airway=?,breathing=?,spo2=?,nadi=?,crt=?,warna_kulit=?,circulation_perdarahan=?,turgor_kulit=?,respon_neurologi=?,pupil_neurologi=?,reflek=?,gcs=?,tekanan=?,pupil=?,neurosensorik=?,integumen=?,turgor=?,edema=?,mukosa=?,perdarahan=?,jumlah_perdarahan=?,warna_perdarahan=?,"
                        +
                        "intoksikasi=?,bab=?,xbab=?,kbab=?,wbab=?,bak=?,xbak=?,wbak=?,lbak=?,psikologis=?,jiwa=?,perilaku=?,dilaporkan=?,sebutkan=?,hubungan=?,tinggal_dengan=?,"
                        +
                        "ket_tinggal=?,budaya=?,ket_budaya=?,pendidikan_pj=?,ket_pendidikan_pj=?,edukasi=?,ket_edukasi=?,kemampuan=?,aktifitas=?,alat_bantu=?,ket_bantu=?,nyeri=?,"
                        +
                        "provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,pada_dokter=?,ket_dokter=?,"
                        +
                        "berjalan_a=?,berjalan_b=?,berjalan_c=?,hasil=?,lapor=?,ket_lapor=?,rencana=?,nip=?,informasi=?",
                82, new String[] {
                        TNoRw.getText(),
                        Valid.SetTgl(TglAsuhan.getSelectedItem() + "") + " "
                                + TglAsuhan.getSelectedItem().toString().substring(11, 19),
                        KeluhanUtama.getText(), RPD.getText(), RPO.getText(),
                        StatusKehamilan.getSelectedItem().toString(),
                        Gravida.getText(), Para.getText(), Abortus.getText(), HPHT.getText(),
                        Airway.getSelectedItem().toString(), Breathing.getSelectedItem().toString(), Spo2.getText(),
                        Nadi.getSelectedItem().toString(), CRT.getSelectedItem().toString(),
                        WarnaKulit.getSelectedItem().toString(),
                        CirculationPerdarahan.getSelectedItem().toString(),
                        TurgorKulit.getSelectedItem().toString(), ResponNeurologi.getSelectedItem().toString(),
                        PupilNeurologi.getSelectedItem().toString(), Reflek.getText(), GCS.getText(),
                        Tekanan.getSelectedItem().toString(), Pupil.getSelectedItem().toString(),
                        Neurosensorik.getSelectedItem().toString(), Integumen.getSelectedItem().toString(),
                        Turgor.getSelectedItem().toString(), Edema.getSelectedItem().toString(),
                        Mukosa.getSelectedItem().toString(), Perdarahan.getSelectedItem().toString(),
                        JumlahPerdarahan.getText(),
                        WarnaPerdarahan.getText(), Intoksikasi.getSelectedItem().toString(), BAB.getSelectedItem().toString(),
                        XBAB.getText(), KBAB.getSelectedItem().toString(), WBAB.getSelectedItem().toString(), BAK.getSelectedItem().toString(), XBAK.getText(), WBAK.getSelectedItem().toString(),
                        LBAK.getText(), Psikologis.getSelectedItem().toString(),
                        Jiwa.getSelectedItem().toString(), Perilaku.getSelectedItem().toString(), Dilaporkan.getText(),
                        Sebutkan.getText(), Hubungan.getSelectedItem().toString(),
                        TinggalDengan.getSelectedItem().toString(), KetTinggal.getText(),
                        StatusBudaya.getSelectedItem().toString(), KetBudaya.getText(),
                        PendidikanPJ.getSelectedItem().toString(), KetPendidikanPJ.getText(),
                        Edukasi.getSelectedItem().toString(), KetEdukasi.getText(), ADL.getSelectedItem().toString(),
                        Aktifitas.getSelectedItem().toString(), AlatBantu.getSelectedItem().toString(),
                        KetAlatBantu.getText(), Nyeri.getSelectedItem().toString(),
                        Provokes.getSelectedItem().toString(), KetProvokes.getText(),
                        Quality.getSelectedItem().toString(),
                        KetQuality.getText(), Lokasi.getText(), Menyebar.getSelectedItem().toString(),
                        SkalaNyeri.getSelectedItem().toString(), Durasi.getText(),
                        NyeriHilang.getSelectedItem().toString(), KetNyeri.getText(),
                        PadaDokter.getSelectedItem().toString(),
                        KetDokter.getText(), ATS.getSelectedItem().toString(), BJM.getSelectedItem().toString(),
                        MSA.getSelectedItem().toString(), Hasil.getSelectedItem().toString(),
                        Lapor.getSelectedItem().toString(), KetLapor.getText(), Rencana.getText(),
                        KdPetugas.getText(), Informasi.getSelectedItem().toString(),
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                }) == true) {
            Sequel.meghapus("penilaian_awal_keperawatan_ponek_masalah", "no_rawat",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                if (tbMasalahKeperawatan.getValueAt(i, 0).toString().equals("true")) {
                    Sequel.menyimpan2("penilaian_awal_keperawatan_ponek_masalah", "?,?", 2,
                            new String[] { TNoRw.getText(), tbMasalahKeperawatan.getValueAt(i, 1).toString() });
                }
            }
            Sequel.meghapus("penilaian_awal_keperawatan_ralan_rencana_igd", "no_rawat",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                if (tbRencanaKeperawatan.getValueAt(i, 0).toString().equals("true")) {
                    Sequel.menyimpan2("penilaian_awal_keperawatan_ralan_rencana_igd", "?,?", 2,
                            new String[] { TNoRw.getText(), tbRencanaKeperawatan.getValueAt(i, 1).toString() });
                }
            }
            tampil();
            DetailRencana.setText(Rencana.getText());
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void hapus() {
        if(true) return;
        if (Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ponek where no_rawat=?", 1, new String[] {
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            TNoRM1.setText("");
            TPasien1.setText("");
            Sequel.meghapus("penilaian_awal_keperawatan_ponek_masalah", "no_rawat",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            Sequel.meghapus("penilaian_awal_keperawatan_ralan_rencana_igd", "no_rawat",
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            Valid.tabelKosong(tabModeDetailMasalah);
            Valid.tabelKosong(tabModeDetailRencana);
            ChkAccor.setSelected(false);
            isMenu();
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void initIGD() {
        jLabelPrimarySurvey = new widget.Label();
        jLabelPrimarySurvey.setText("PRIMARY SURVEY");
        jLabelPrimarySurvey.setFont(new java.awt.Font("Tahoma", 1, 11)); // Membuat teks tebal
        FormInput.add(jLabelPrimarySurvey);
        jLabelPrimarySurvey.setBounds(10, 215, 200, 23);

        // Airway
        jLabelAirway = new widget.Label();
        jLabelAirway.setText("Airway :");
        FormInput.add(jLabelAirway);
        jLabelAirway.setBounds(30, 240, 60, 23);

        Airway = new widget.ComboBox();
        Airway.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Bebas", "Gurgling", "Stridor", "Wheezing", "Ronchi", "Terintubasi" }));
        Airway.setName("Airway");
        FormInput.add(Airway);
        Airway.setBounds(95, 240, 120, 23);

        // Breathing
        jLabelBreathing = new widget.Label();
        jLabelBreathing.setText("Breathing :");
        FormInput.add(jLabelBreathing);
        jLabelBreathing.setBounds(235, 240, 70, 23);

        Breathing = new widget.ComboBox();
        Breathing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Tachipneu", "Dispneu",
                "Apneu", "Ventilasi Mekanik", "Memakai Ventilator" }));
        Breathing.setName("Breathing");
        FormInput.add(Breathing);
        Breathing.setBounds(310, 240, 150, 23);

        // SpO2
        jLabelSpo2 = new widget.Label();
        jLabelSpo2.setText("SpO2 :");
        FormInput.add(jLabelSpo2);
        jLabelSpo2.setBounds(480, 240, 45, 23);

        Spo2 = new widget.TextBox();
        Spo2.setName("Spo2");
        FormInput.add(Spo2);
        Spo2.setBounds(530, 240, 50, 23);

        // Nadi
        jLabelNadi = new widget.Label();
        jLabelNadi.setText("Nadi :");
        FormInput.add(jLabelNadi);
        jLabelNadi.setBounds(30, 270, 60, 23);

        Nadi = new widget.ComboBox();
        Nadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Lemah" }));
        Nadi.setName("Nadi");
        FormInput.add(Nadi);
        Nadi.setBounds(95, 270, 120, 23);

        // CRT
        jLabelCRT = new widget.Label();
        jLabelCRT.setText("CRT :");
        FormInput.add(jLabelCRT);
        jLabelCRT.setBounds(235, 270, 70, 23);

        CRT = new widget.ComboBox();
        CRT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<2'", ">2'" }));
        CRT.setName("CRT");
        FormInput.add(CRT);
        CRT.setBounds(310, 270, 70, 23);

        // Warna Kulit
        jLabelWarnaKulit = new widget.Label();
        jLabelWarnaKulit.setText("Warna Kulit :");
        FormInput.add(jLabelWarnaKulit);
        jLabelWarnaKulit.setBounds(400, 270, 80, 23);

        WarnaKulit = new widget.ComboBox();
        WarnaKulit.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Pucat", "Ikterik", "Cyanosis" }));
        WarnaKulit.setName("WarnaKulit");
        FormInput.add(WarnaKulit);
        WarnaKulit.setBounds(485, 270, 120, 23);

        // Perdarahan
        jLabelCirculationPerdarahan = new widget.Label();
        jLabelCirculationPerdarahan.setText("Perdarahan :");
        FormInput.add(jLabelCirculationPerdarahan);

        CirculationPerdarahan = new widget.ComboBox();
        CirculationPerdarahan.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Terkontrol", "Tidak Terkontrol" }));
        CirculationPerdarahan.setName("CirculationPerdarahan");
        FormInput.add(CirculationPerdarahan);

        jLabelCirculationPerdarahan = new widget.Label();
        jLabelCirculationPerdarahan.setText("Perdarahan :");
        FormInput.add(jLabelCirculationPerdarahan);

        CirculationPerdarahan = new widget.ComboBox();
        CirculationPerdarahan.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Terkontrol", "Tidak Terkontrol" }));
        CirculationPerdarahan.setName("CirculationPerdarahan");
        FormInput.add(CirculationPerdarahan);

        // Turgor Kulit
        jLabelTurgorKulit = new widget.Label();
        jLabelTurgorKulit.setText("Turgor Kulit :");
        FormInput.add(jLabelTurgorKulit);
        jLabelTurgorKulit.setBounds(345, 300, 80, 23);

        TurgorKulit = new widget.ComboBox();
        TurgorKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Kurang", "Buruk" }));
        TurgorKulit.setName("TurgorKulit");
        FormInput.add(TurgorKulit);
        TurgorKulit.setBounds(430, 300, 90, 23);

        // Respon
        jLabelResponNeurologi = new widget.Label();
        jLabelResponNeurologi.setText("Respon :");
        FormInput.add(jLabelResponNeurologi);
        jLabelResponNeurologi.setBounds(30, 330, 60, 23);

        ResponNeurologi = new widget.ComboBox();
        ResponNeurologi.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Alert", "Pain", "Verbal", "Unrespons" }));
        ResponNeurologi.setName("ResponNeurologi");
        FormInput.add(ResponNeurologi);
        ResponNeurologi.setBounds(95, 330, 120, 23);

        // Pupil
        jLabelPupilNeurologi = new widget.Label();
        jLabelPupilNeurologi.setText("Pupil :");
        FormInput.add(jLabelPupilNeurologi);
        jLabelPupilNeurologi.setBounds(235, 330, 70, 23);

        PupilNeurologi = new widget.ComboBox();
        PupilNeurologi.setModel(
                new javax.swing.DefaultComboBoxModel(new String[] { "Isokor", "Anisokor", "Pin Point", "Midriasis" }));
        PupilNeurologi.setName("PupilNeurologi");
        FormInput.add(PupilNeurologi);
        PupilNeurologi.setBounds(310, 330, 120, 23);

        // Reflek
        jLabelReflek = new widget.Label();
        jLabelReflek.setText("Reflek :");
        FormInput.add(jLabelReflek);
        jLabelReflek.setBounds(450, 330, 50, 23);

        Reflek = new widget.TextBox();
        Reflek.setName("Reflek");
        FormInput.add(Reflek);
        Reflek.setBounds(505, 330, 100, 23);

        // GCS
        jLabelGCS = new widget.Label();
        jLabelGCS.setText("GCS :");
        FormInput.add(jLabelGCS);
        jLabelGCS.setBounds(625, 330, 40, 23);

        GCS = new widget.TextBox();
        GCS.setName("GCS");
        FormInput.add(GCS);
        GCS.setBounds(670, 330, 100, 23);

        // Separator baru
        jSeparatorPrimary = new javax.swing.JSeparator();
        jSeparatorPrimary.setBackground(new java.awt.Color(239, 244, 234));
        jSeparatorPrimary.setForeground(new java.awt.Color(239, 244, 234));
        jSeparatorPrimary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparatorPrimary.setName("jSeparatorPrimary");
        FormInput.add(jSeparatorPrimary);
        jSeparatorPrimary.setBounds(0, 365, 880, 1);
    }

    private void initPosisi() {
        int y_offset = 160;

        TNoRw.setBounds(74, 10, 131, 23);
        TPasien.setBounds(309, 10, 260, 23);
        TNoRM.setBounds(207, 10, 100, 23);
        label14.setBounds(0, 40, 70, 23);
        KdPetugas.setBounds(74, 40, 100, 23);
        NmPetugas.setBounds(176, 40, 180, 23);
        BtnDokter.setBounds(358, 40, 28, 23);
        jLabel8.setBounds(580, 10, 60, 23);
        TglLahir.setBounds(644, 10, 80, 23);
        jLabel9.setBounds(440, 90, 150, 23);
        Jk.setBounds(774, 10, 80, 23);
        jLabel10.setBounds(0, 10, 70, 23);
        label11.setBounds(395, 40, 57, 23);
        jLabel11.setBounds(740, 10, 30, 23);
        scrollPane1.setBounds(179, 90, 260, 53);
        jLabel30.setBounds(0, 90, 175, 20);
        scrollPane2.setBounds(179, 150, 260, 53);
        jLabel31.setBounds(0, 150, 175, 23);
        scrollPane4.setBounds(594, 90, 260, 53);
        TglAsuhan.setBounds(456, 40, 130, 23);
        jSeparator1.setBounds(0, 70, 880, 1);
        jLabel78.setBounds(440, 150, 106, 23);
        StatusKehamilan.setBounds(550, 150, 110, 23);
        jLabel29.setBounds(715, 180, 50, 23);
        Gravida.setBounds(769, 180, 85, 23);
        jLabel32.setBounds(440, 180, 43, 23);
        Para.setBounds(487, 180, 85, 23);
        jLabel33.setBounds(575, 180, 50, 23);
        Abortus.setBounds(629, 180, 85, 23);
        // HPHT hidden
        jSeparator11.setBounds(0, 210, 880, 1);
        jLabel98.setBounds(10, 70, 180, 23);

        // Posisi komponen-komponen yang digeser ke bawah (+ y_offset)
        jLabel116.setBounds(10, 210 + y_offset, 180, 23); // II. PEMERIKSAAN FISIK
        jLabel90.setBounds(0, 230 + y_offset, 150, 23);
        Tekanan.setBounds(154, 230 + y_offset, 112, 23);
        jLabel91.setBounds(295, 230 + y_offset, 50, 23);
        Pupil.setBounds(349, 230 + y_offset, 93, 23);
        Neurosensorik.setBounds(654, 230 + y_offset, 200, 23);
        jLabel100.setBounds(470, 230 + y_offset, 180, 23);
        jLabel101.setBounds(0, 260 + y_offset, 150, 23);
        Integumen.setBounds(154, 260 + y_offset, 125, 23);
        jLabel102.setBounds(290, 260 + y_offset, 80, 23);
        Turgor.setBounds(374, 260 + y_offset, 93, 23);
        jLabel103.setBounds(486, 260 + y_offset, 50, 23);
        Edema.setBounds(540, 260 + y_offset, 120, 23);
        jLabel104.setBounds(670, 260 + y_offset, 90, 23);
        Mukosa.setBounds(764, 260 + y_offset, 90, 23);
        jLabel105.setBounds(0, 290 + y_offset, 150, 23);
        Perdarahan.setBounds(154, 290 + y_offset, 100, 23);
        jLabel36.setBounds(379, 290 + y_offset, 20, 23);
        JumlahPerdarahan.setBounds(306, 290 + y_offset, 70, 23);
        jLabel37.setBounds(386, 290 + y_offset, 50, 23);
        WarnaPerdarahan.setBounds(440, 290 + y_offset, 170, 23);
        jLabel38.setBounds(252, 290 + y_offset, 50, 23);
        Intoksikasi.setBounds(719, 290 + y_offset, 135, 23);
        jLabel107.setBounds(625, 290 + y_offset, 90, 23);
        jLabel108.setBounds(0, 320 + y_offset, 150, 23);
        KBAB.setBounds(443, 340 + y_offset, 150, 23);
        jLabel109.setBounds(369, 340 + y_offset, 70, 23);
        BAB.setBounds(229, 340 + y_offset, 137, 23);
        jLabel110.setBounds(135, 340 + y_offset, 90, 23);
        XBAB.setBounds(298, 340 + y_offset, 70, 23);
        jLabel106.setBounds(282, 340 + y_offset, 13, 23);
        jLabel111.setBounds(620, 340 + y_offset, 55, 23);
        WBAB.setBounds(679, 340 + y_offset, 130, 23);
        jLabel112.setBounds(135, 370 + y_offset, 90, 23);
        BAK.setBounds(229, 370 + y_offset, 110, 23);
        jLabel113.setBounds(282, 370 + y_offset, 13, 23);
        XBAK.setBounds(298, 370 + y_offset, 70, 23);
        jLabel114.setBounds(395, 370 + y_offset, 45, 23);
        WBAK.setBounds(443, 370 + y_offset, 130, 23);
        jLabel115.setBounds(608, 370 + y_offset, 65, 23);
        LBAK.setBounds(679, 370 + y_offset, 175, 23);
        jLabelCirculationPerdarahan.setBounds(30, 300, 80, 23);
        CirculationPerdarahan.setBounds(115, 300, 150, 23); // Lebar disesuaikan

        jLabelTurgorKulit.setBounds(280, 300, 80, 23); // Posisi digeser ke kiri
        TurgorKulit.setBounds(365, 300, 90, 23); // Posisi digeser ke kiri

        // === ASSESMEN KEBIDANAN (y_offset) ===
        jSeparatorKebidanan.setBounds(0, 400 + y_offset, 880, 2);
        lblKebidananTitle.setBounds(10, 405 + y_offset, 300, 23);
        lblDataSubjektif.setBounds(15, 428 + y_offset, 200, 23);
        lblRiwayatMenstruasi.setBounds(25, 451 + y_offset, 150, 23);
        
        lblMenarche.setBounds(0, 474 + y_offset, 230, 23);
        MenarcheUmur.setBounds(234, 474 + y_offset, 50, 23);
        lblMenarcheTahun.setBounds(288, 474 + y_offset, 40, 23);
        lblSiklus.setBounds(330, 474 + y_offset, 160, 23);
        SiklusMenstruasi.setBounds(494, 474 + y_offset, 50, 23);
        lblSiklusHari.setBounds(548, 474 + y_offset, 30, 23);
        KeteratanMenstruasi.setBounds(582, 474 + y_offset, 120, 23);
        
        lblLama.setBounds(0, 499 + y_offset, 230, 23);
        LamaMenstruasi.setBounds(234, 499 + y_offset, 50, 23);
        lblLamaHari.setBounds(290, 499 + y_offset, 200, 23);
        KeluhanHaid.setBounds(494, 499 + y_offset, 360, 23);
        
        lblRiwayatKehamilanSekarang.setBounds(25, 527 + y_offset, 210, 23);
        
        lblHamilKe.setBounds(0, 550 + y_offset, 230, 23);
        HamilKe.setBounds(234, 550 + y_offset, 50, 23);
        lblUK.setBounds(290, 550 + y_offset, 200, 23);
        UKMinggu.setBounds(494, 550 + y_offset, 50, 23);
        lblUKMinggu.setBounds(548, 550 + y_offset, 45, 23);
        UKHari.setBounds(597, 550 + y_offset, 50, 23);
        lblUKHari.setBounds(651, 550 + y_offset, 40, 23);
        
        lblHPHTKbd.setBounds(0, 575 + y_offset, 230, 23);
        HPHTKbd.setBounds(234, 575 + y_offset, 170, 23);
        lblHPL.setBounds(410, 575 + y_offset, 80, 23);
        HPLKbd.setBounds(494, 575 + y_offset, 170, 23);
        
        lblBBSebelum.setBounds(0, 600 + y_offset, 230, 23);
        BBSebelumHamil.setBounds(234, 600 + y_offset, 50, 23);
        lblBBSebelumKg.setBounds(288, 600 + y_offset, 25, 23);
        lblBBSekarang.setBounds(315, 600 + y_offset, 175, 23);
        BBSekarang.setBounds(494, 600 + y_offset, 50, 23);
        lblBBSekarangKg.setBounds(548, 600 + y_offset, 25, 23);
        lblTBKbd.setBounds(575, 600 + y_offset, 115, 23);
        TBKbd.setBounds(694, 600 + y_offset, 50, 23);
        lblTBCm.setBounds(748, 600 + y_offset, 25, 23);
        
        lblPeriksaANC.setBounds(0, 625 + y_offset, 230, 23);
        PeriksaANC.setBounds(234, 625 + y_offset, 50, 23);
        lblANCKali.setBounds(290, 625 + y_offset, 200, 23);
        ANCDi.setBounds(494, 625 + y_offset, 140, 23);
        lblANCLainnya.setBounds(638, 625 + y_offset, 52, 23);
        ANCLainnya.setBounds(694, 625 + y_offset, 160, 23);
        
        lblImunisasiTT.setBounds(0, 650 + y_offset, 230, 23);
        ImunisasiTT.setBounds(234, 650 + y_offset, 50, 23);
        lblTTKali.setBounds(288, 650 + y_offset, 30, 23);
        lblTglTT1.setBounds(320, 650 + y_offset, 170, 23);
        TglTT1.setBounds(494, 650 + y_offset, 130, 23);
        lblTglTT2.setBounds(628, 650 + y_offset, 62, 23);
        TglTT2.setBounds(694, 650 + y_offset, 160, 23);
        
        lblGerakanJanin.setBounds(0, 675 + y_offset, 230, 23);
        GerakanJanin.setBounds(234, 675 + y_offset, 50, 23);
        lblGerakanKali.setBounds(288, 675 + y_offset, 40, 23);
        
        lblRiwayatKehamilanLalu.setBounds(25, 703 + y_offset, 380, 23);
        scrollRiwayatKehamilan.setBounds(20, 728 + y_offset, 840, 170);

        // =========================================================================
        // B. OBJEKTIF TEXT AND ALIGNMENT
        // =========================================================================
        lblObjektifTitle.setText("B. OBJEKTIF");
        lblObjektifTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPmxUmum.setText("1. Pemeriksaan Umum");
        lblPmxUmum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        // Colons set to align at X = 220 
        lblKUPonek.setText("KU :");
        lblKesadaranPonek.setText("Kesadaran :");
        lblGCSPonek.setText("GCS : E");
        // GCS letters
        lblGCSV.setText("V");
        lblGCSM.setText("M");
        
        lblTDPonek.setText("TD :");
        
        lblTDSlash.setText("/");
        lblTDSlash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTDMmHg.setText("mmHg");
        lblTDMmHg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        lblHRPonek.setText("HR :");
        
        lblHRXMenit.setText("x/menit");
        lblHRXMenit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        lblRRPonek.setText("RR :");
        
        lblRRXMenit.setText("x/menit");
        lblRRXMenit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        lblSPonek.setText("S :");
        
        lblSC.setText("°C");
        lblSC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        lblSpO2Ponek.setText("SpO2 :");
        
        lblSpO2Persen.setText("%");
        lblSpO2Persen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        lblPmxFisik.setText("2. Pemeriksaan Fisik");
        lblPmxFisik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        // ALL LABELS RIGHT-ALIGNED NATIVELY TO CREATE A PERFECT COLON LINE
        lblKepala.setText("a. Kepala :");
        lblMata.setText("b. Mata :");
        lblLeher.setText("c. Leher :");
        lblThorax.setText("d. Thorax :");
        lblAbdomen.setText("e. Abdomen :");
        // Do NOT set LEFT for lblAbdomen so its colon aligns!
        
        lblInspeksi.setText("Inspeksi :");
        lblPalpasiTFU.setText("Palpasi : TFU :");
        lblTFUcm.setText("cm");
        lblTFUcm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLeopold1.setText("Leopold I :");
        lblLeopold2.setText("Leopold II :");
        lblLeopold3.setText("Leopold III :");
        lblLeopold4.setText("Leopold IV :");
        lblTBBJ.setText("TBBJ :");
        lblHis.setText("His :");
        lblAuskultasi.setText("Auskultasi :");
        
        lblGenitalia.setText("f. Genitalia : (dilakukan pukul");
        lblPukulWIB2.setText("WIB)");
        lblPukulWIB2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPengeluaran.setText("Pengeluaran pervaginam :");
        lblPmxDalam.setText("Pemeriksaan dalam / VT :");
        lblInspekulo.setText("Inspekulo Vagina :");
        
        lblEkstremitas.setText("g. Ekstremitas : Oedema");
        lblOedemaSlash.setText("/");
        lblOedemaSlash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOedemaVarises.setText("varises");
        lblVarisesSlash.setText("/");
        lblVarisesSlash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVarisesReflek.setText("reflek patella");
        lblReflekSlash.setText("/");
        lblReflekSlash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // =========================================================================
        // ADD COMPONENTS & MASTER BOUNDS
        // Master right-margin colon axis is X=220. Field textboxes start X=224.
        // =========================================================================
        FormInput.add(lblObjektifTitle);        lblObjektifTitle.setBounds(10, 920 + y_offset, 200, 23);
        FormInput.add(lblPmxUmum);              lblPmxUmum.setBounds(25, 945 + y_offset, 250, 23);
        
        // ROW 1: KU (Master colon aligns 220)
        FormInput.add(lblKUPonek);              lblKUPonek.setBounds(0, 970 + y_offset, 220, 23);
        FormInput.add(ObjKU);                   ObjKU.setBounds(224, 970 + y_offset, 100, 23);
        FormInput.add(lblKesadaranPonek);       lblKesadaranPonek.setBounds(335, 970 + y_offset, 90, 23); // colon ends 425
        FormInput.add(ObjKesadaran);            ObjKesadaran.setBounds(429, 970 + y_offset, 100, 23);
        FormInput.add(lblGCSPonek);             lblGCSPonek.setBounds(535, 970 + y_offset, 50, 23); // colon ends 585
        FormInput.add(ObjGCSE);                 ObjGCSE.setBounds(589, 970 + y_offset, 30, 23);
        FormInput.add(lblGCSV);                 lblGCSV.setBounds(623, 970 + y_offset, 15, 23);
        FormInput.add(ObjGCSV);                 ObjGCSV.setBounds(642, 970 + y_offset, 30, 23);
        FormInput.add(lblGCSM);                 lblGCSM.setBounds(676, 970 + y_offset, 15, 23);
        FormInput.add(ObjGCSM);                 ObjGCSM.setBounds(695, 970 + y_offset, 30, 23);
        
        // ROW 2: TD HR RR S SpO2
        FormInput.add(lblTDPonek);              lblTDPonek.setBounds(0, 995 + y_offset, 220, 23);
        FormInput.add(ObjTDSistol);             ObjTDSistol.setBounds(224, 995 + y_offset, 40, 23);
        FormInput.add(lblTDSlash);              lblTDSlash.setBounds(266, 995 + y_offset, 10, 23);
        FormInput.add(ObjTDDiastol);            ObjTDDiastol.setBounds(278, 995 + y_offset, 40, 23);
        FormInput.add(lblTDMmHg);               lblTDMmHg.setBounds(322, 995 + y_offset, 40, 23);
        
        FormInput.add(lblHRPonek);              lblHRPonek.setBounds(370, 995 + y_offset, 50, 23); // colon ends 420
        FormInput.add(ObjHR);                   ObjHR.setBounds(424, 995 + y_offset, 40, 23);
        FormInput.add(lblHRXMenit);             lblHRXMenit.setBounds(468, 995 + y_offset, 50, 23);
        
        FormInput.add(lblRRPonek);              lblRRPonek.setBounds(522, 995 + y_offset, 40, 23); // colon ends 562
        FormInput.add(ObjRR);                   ObjRR.setBounds(566, 995 + y_offset, 40, 23);
        FormInput.add(lblRRXMenit);             lblRRXMenit.setBounds(610, 995 + y_offset, 50, 23);
        
        FormInput.add(lblSPonek);               lblSPonek.setBounds(665, 995 + y_offset, 25, 23); // colon ends 690
        FormInput.add(ObjSuhu);                 ObjSuhu.setBounds(694, 995 + y_offset, 40, 23);
        FormInput.add(lblSC);                   lblSC.setBounds(738, 995 + y_offset, 20, 23);
        
        FormInput.add(lblSpO2Ponek);            lblSpO2Ponek.setBounds(760, 995 + y_offset, 45, 23); // colon ends 805
        FormInput.add(ObjSpO2);                 ObjSpO2.setBounds(809, 995 + y_offset, 40, 23);
        FormInput.add(lblSpO2Persen);           lblSpO2Persen.setBounds(853, 995 + y_offset, 20, 23);
        
        FormInput.add(lblPmxFisik);             lblPmxFisik.setBounds(25, 1030 + y_offset, 250, 23);
        
        // =========================================================================
        // EVERY LABEL IN SECTION 2 NOW HAS BOUNDS(10, y, 210, 23)
        // WHICH PINPOINTS EVERY SINGLE COLON TO EXACTLY 10 + 210 = 220!
        // FIELDS START AT 224, WIDTH 620
        // =========================================================================
        FormInput.add(lblKepala);               lblKepala.setBounds(10, 1055 + y_offset, 210, 23);
        FormInput.add(ObjKepala);               ObjKepala.setBounds(224, 1055 + y_offset, 620, 23);
        
        FormInput.add(lblMata);                 lblMata.setBounds(10, 1080 + y_offset, 210, 23);
        FormInput.add(ObjMata);                 ObjMata.setBounds(224, 1080 + y_offset, 620, 23);
        
        FormInput.add(lblLeher);                lblLeher.setBounds(10, 1105 + y_offset, 210, 23);
        FormInput.add(ObjLeher);                ObjLeher.setBounds(224, 1105 + y_offset, 620, 23);
        
        FormInput.add(lblThorax);               lblThorax.setBounds(10, 1130 + y_offset, 210, 23);
        FormInput.add(ObjThorax);               ObjThorax.setBounds(224, 1130 + y_offset, 620, 23);
        
        FormInput.add(lblAbdomen);              lblAbdomen.setBounds(10, 1155 + y_offset, 210, 23);
        FormInput.add(ObjAbdomen);              ObjAbdomen.setBounds(224, 1155 + y_offset, 620, 23);
        
        FormInput.add(lblInspeksi);             lblInspeksi.setBounds(10, 1180 + y_offset, 210, 23);
        FormInput.add(ObjInspeksi);             ObjInspeksi.setBounds(224, 1180 + y_offset, 620, 23);
        
        FormInput.add(lblPalpasiTFU);           lblPalpasiTFU.setBounds(10, 1205 + y_offset, 210, 23);
        FormInput.add(ObjTFU);                  ObjTFU.setBounds(224, 1205 + y_offset, 100, 23);
        FormInput.add(lblTFUcm);                lblTFUcm.setBounds(328, 1205 + y_offset, 30, 23);
        
        FormInput.add(lblLeopold1);             lblLeopold1.setBounds(10, 1230 + y_offset, 210, 23);
        FormInput.add(ObjLeopold1);             ObjLeopold1.setBounds(224, 1230 + y_offset, 620, 23);
        
        FormInput.add(lblLeopold2);             lblLeopold2.setBounds(10, 1255 + y_offset, 210, 23);
        FormInput.add(ObjLeopold2);             ObjLeopold2.setBounds(224, 1255 + y_offset, 620, 23);
        
        FormInput.add(lblLeopold3);             lblLeopold3.setBounds(10, 1280 + y_offset, 210, 23);
        FormInput.add(ObjLeopold3);             ObjLeopold3.setBounds(224, 1280 + y_offset, 620, 23);
        
        FormInput.add(lblLeopold4);             lblLeopold4.setBounds(10, 1305 + y_offset, 210, 23);
        FormInput.add(ObjLeopold4);             ObjLeopold4.setBounds(224, 1305 + y_offset, 620, 23);
        
        FormInput.add(lblTBBJ);                 lblTBBJ.setBounds(10, 1330 + y_offset, 210, 23);
        FormInput.add(ObjTBBJ);                 ObjTBBJ.setBounds(224, 1330 + y_offset, 620, 23);
        
        FormInput.add(lblHis);                  lblHis.setBounds(10, 1355 + y_offset, 210, 23);
        FormInput.add(ObjHis);                  ObjHis.setBounds(224, 1355 + y_offset, 620, 23);
        
        FormInput.add(lblAuskultasi);           lblAuskultasi.setBounds(10, 1380 + y_offset, 210, 23);
        FormInput.add(ObjAuskultasi);           ObjAuskultasi.setBounds(224, 1380 + y_offset, 620, 23);
        
        FormInput.add(lblGenitalia);            lblGenitalia.setBounds(10, 1405 + y_offset, 210, 23);
        FormInput.add(ObjPukul);                ObjPukul.setBounds(224, 1405 + y_offset, 60, 23);
        FormInput.add(lblPukulWIB2);            lblPukulWIB2.setBounds(288, 1405 + y_offset, 50, 23);
        
        FormInput.add(lblPengeluaran);          lblPengeluaran.setBounds(10, 1430 + y_offset, 210, 23);
        FormInput.add(ObjPengeluaran);          ObjPengeluaran.setBounds(224, 1430 + y_offset, 620, 23);
        
        FormInput.add(lblPmxDalam);             lblPmxDalam.setBounds(10, 1455 + y_offset, 210, 23);
        FormInput.add(ObjPmxDalam);             ObjPmxDalam.setBounds(224, 1455 + y_offset, 620, 23);
        
        FormInput.add(lblInspekulo);            lblInspekulo.setBounds(10, 1480 + y_offset, 210, 23);
        FormInput.add(ObjInspekulo);            ObjInspekulo.setBounds(224, 1480 + y_offset, 620, 23);
        
        FormInput.add(lblEkstremitas);          lblEkstremitas.setBounds(10, 1505 + y_offset, 210, 23);
        FormInput.add(ObjOedema1);              ObjOedema1.setBounds(224, 1505 + y_offset, 40, 23);
        FormInput.add(lblOedemaSlash);          lblOedemaSlash.setBounds(264, 1505 + y_offset, 15, 23);
        FormInput.add(ObjOedema2);              ObjOedema2.setBounds(279, 1505 + y_offset, 40, 23);
        FormInput.add(lblOedemaVarises);        lblOedemaVarises.setBounds(319, 1505 + y_offset, 60, 23);
        FormInput.add(ObjVarises1);             ObjVarises1.setBounds(383, 1505 + y_offset, 40, 23);
        FormInput.add(lblVarisesSlash);         lblVarisesSlash.setBounds(423, 1505 + y_offset, 15, 23);
        FormInput.add(ObjVarises2);             ObjVarises2.setBounds(438, 1505 + y_offset, 40, 23);
        FormInput.add(lblVarisesReflek);        lblVarisesReflek.setBounds(478, 1505 + y_offset, 90, 23);
        FormInput.add(ObjReflek1);              ObjReflek1.setBounds(572, 1505 + y_offset, 40, 23);
        FormInput.add(lblReflekSlash);          lblReflekSlash.setBounds(612, 1505 + y_offset, 15, 23);
        FormInput.add(ObjReflek2);              ObjReflek2.setBounds(627, 1505 + y_offset, 40, 23);
    }

}
