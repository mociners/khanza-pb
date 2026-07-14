package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import kepegawaian.DlgCariPetugas;

public class RMSuratKeteranganKelahiran extends JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    
    public widget.InternalFrame internalFrame1;
    public javax.swing.JPanel PanelInput;
    public widget.ScrollPane scrollInput;
    public widget.PanelBiasa FormInput;
    
    public widget.TextBox NoSurat = new widget.TextBox();
    public widget.Tanggal Tanggal = new widget.Tanggal();
    public widget.ComboBox Jam = newComboBox("Jam", 24);
    public widget.ComboBox Menit = newComboBox("Menit", 60);
    public widget.ComboBox Detik = newComboBox("Detik", 60);
    public widget.CekBox ChkKejadian = new widget.CekBox();
    public widget.TextBox NamaBayi = new widget.TextBox();
    public widget.ComboBox Jk = new widget.ComboBox();
    public widget.TextBox BeratBadan = new widget.TextBox();
    public widget.TextBox PanjangBadan = new widget.TextBox();
    
    public widget.TextBox NamaIbu = new widget.TextBox();
    public widget.TextBox UmurIbu = new widget.TextBox();
    public widget.TextBox SukuIbu = new widget.TextBox();
    
    public widget.TextBox NamaAyah = new widget.TextBox();
    public widget.TextBox UmurAyah = new widget.TextBox();
    public widget.TextBox SukuAyah = new widget.TextBox();
    
    public widget.TextArea Alamat = new widget.TextArea();
    
    public widget.TextBox KdPetugas = new widget.TextBox();
    public widget.TextBox NmPetugas = new widget.TextBox();
    public widget.Button BtnCariPetugas = new widget.Button();
    
    public widget.Button BtnSimpan = new widget.Button();
    public widget.Button BtnBatal = new widget.Button();
    public widget.Button BtnHapus = new widget.Button();
    public widget.Button BtnEdit = new widget.Button();
    public widget.Button BtnKeluar = new widget.Button();
    
    public widget.ScrollPane Scroll;
    public widget.Table tbKelahiran;
    public javax.swing.JPanel jPanel3;
    public widget.panelisi panelGlass8;
    
    private widget.ComboBox newComboBox(String label, int count) {
        widget.ComboBox cb = new widget.ComboBox();
        String[] items = new String[count];
        for (int i = 0; i < count; i++) {
            items[i] = String.format("%02d", i);
        }
        cb.setModel(new javax.swing.DefaultComboBoxModel(items));
        return cb;
    }

    public RMSuratKeteranganKelahiran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Surat", "No.Rawat", "Tanggal", "Jam", "Nama Bayi", "J.K.", "Berat(gr)", "Panjang(Cm)", "Nama Ibu", "Umur Ibu", "Suku Ibu", "Nama Ayah", "Umur Ayah", "Suku Ayah", "Alamat", "NIP", "Dokter"
        }) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) { return false; }
        };
        tbKelahiran.setModel(tabMode);
        tbKelahiran.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKelahiran.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 17; i++) {
            javax.swing.table.TableColumn column = tbKelahiran.getColumnModel().getColumn(i);
            if (i == 0) column.setPreferredWidth(130);
            else if (i == 1) column.setPreferredWidth(105);
            else if (i == 4) column.setPreferredWidth(150);
            else if (i == 8 || i == 11) column.setPreferredWidth(150);
            else if (i == 14) column.setPreferredWidth(200);
            else if (i == 16) column.setPreferredWidth(150);
            else column.setPreferredWidth(80);
        }
        tbKelahiran.setDefaultRenderer(Object.class, new WarnaTable());
        
        Jk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "L", "P" }));
        
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER) {
                    autoNomor();
                }
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        this.setSize(new Dimension(800, 600));
        this.setLayout(new java.awt.BorderLayout(1, 1));
        
        internalFrame1 = new widget.InternalFrame();
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat Keterangan Kelahiran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.setName("internalFrame1");
        
        PanelInput = new javax.swing.JPanel();
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
        PanelInput.setName("PanelInput");
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(100, 350));
        
        scrollInput = new widget.ScrollPane();
        scrollInput.setName("scrollInput");
        
        FormInput = new widget.PanelBiasa();
        FormInput.setLayout(null);
        FormInput.setName("FormInput");
        FormInput.setPreferredSize(new java.awt.Dimension(750, 330));
        
        widget.Label LblNoSurat = new widget.Label();
        LblNoSurat.setText("No. Surat :");
        LblNoSurat.setBounds(0, 10, 120, 23);
        FormInput.add(LblNoSurat);
        NoSurat.setBounds(124, 10, 200, 23);
        FormInput.add(NoSurat);

        widget.Label LblTgl = new widget.Label();
        LblTgl.setText("Tanggal/Waktu :");
        LblTgl.setBounds(0, 40, 120, 23);
        FormInput.add(LblTgl);
        
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setBounds(124, 40, 100, 23);
        FormInput.add(Tanggal);
        
        Jam.setBounds(226, 40, 60, 23);
        FormInput.add(Jam);
        Menit.setBounds(288, 40, 60, 23);
        FormInput.add(Menit);
        Detik.setBounds(350, 40, 60, 23);
        FormInput.add(Detik);
        
        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11));
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setBounds(415, 40, 23, 23);
        FormInput.add(ChkKejadian);

        widget.Label LblNamaBayi = new widget.Label();
        LblNamaBayi.setText("Nama Bayi :");
        LblNamaBayi.setBounds(0, 70, 120, 23);
        FormInput.add(LblNamaBayi);
        NamaBayi.setBounds(124, 70, 300, 23);
        FormInput.add(NamaBayi);

        widget.Label LblJk = new widget.Label();
        LblJk.setText("Jenis Kelamin :");
        LblJk.setBounds(434, 70, 100, 23);
        FormInput.add(LblJk);
        Jk.setBounds(538, 70, 100, 23);
        FormInput.add(Jk);

        widget.Label LblBB = new widget.Label();
        LblBB.setText("Berat Badan (gr) :");
        LblBB.setBounds(0, 100, 120, 23);
        FormInput.add(LblBB);
        BeratBadan.setBounds(124, 100, 100, 23);
        FormInput.add(BeratBadan);

        widget.Label LblPB = new widget.Label();
        LblPB.setText("Panjang Badan (Cm) :");
        LblPB.setBounds(234, 100, 130, 23);
        FormInput.add(LblPB);
        PanjangBadan.setBounds(368, 100, 100, 23);
        FormInput.add(PanjangBadan);

        widget.Label LblIbu = new widget.Label();
        LblIbu.setText("Nama Ibu :");
        LblIbu.setBounds(0, 130, 120, 23);
        FormInput.add(LblIbu);
        NamaIbu.setBounds(124, 130, 300, 23);
        FormInput.add(NamaIbu);

        widget.Label LblUmurIbu = new widget.Label();
        LblUmurIbu.setText("Umur/TTL Ibu :");
        LblUmurIbu.setBounds(0, 160, 120, 23);
        FormInput.add(LblUmurIbu);
        UmurIbu.setBounds(124, 160, 200, 23);
        FormInput.add(UmurIbu);

        widget.Label LblSukuIbu = new widget.Label();
        LblSukuIbu.setText("Suku/Bangsa Ibu :");
        LblSukuIbu.setBounds(334, 160, 120, 23);
        FormInput.add(LblSukuIbu);
        SukuIbu.setBounds(458, 160, 200, 23);
        FormInput.add(SukuIbu);

        widget.Label LblAyah = new widget.Label();
        LblAyah.setText("Nama Ayah :");
        LblAyah.setBounds(0, 190, 120, 23);
        FormInput.add(LblAyah);
        NamaAyah.setBounds(124, 190, 300, 23);
        FormInput.add(NamaAyah);

        widget.Label LblUmurAyah = new widget.Label();
        LblUmurAyah.setText("Umur/TTL Ayah :");
        LblUmurAyah.setBounds(0, 220, 120, 23);
        FormInput.add(LblUmurAyah);
        UmurAyah.setBounds(124, 220, 200, 23);
        FormInput.add(UmurAyah);

        widget.Label LblSukuAyah = new widget.Label();
        LblSukuAyah.setText("Suku/Bangsa Ayah :");
        LblSukuAyah.setBounds(334, 220, 120, 23);
        FormInput.add(LblSukuAyah);
        SukuAyah.setBounds(458, 220, 200, 23);
        FormInput.add(SukuAyah);

        widget.Label LblAlamat = new widget.Label();
        LblAlamat.setText("Alamat :");
        LblAlamat.setBounds(0, 250, 120, 23);
        FormInput.add(LblAlamat);
        
        widget.ScrollPane scrollAlamat = new widget.ScrollPane();
        scrollAlamat.setViewportView(Alamat);
        scrollAlamat.setBounds(124, 250, 534, 43);
        scrollAlamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FormInput.add(scrollAlamat);

        widget.Label LblDokter = new widget.Label();
        LblDokter.setText("Petugas/Dokter :");
        LblDokter.setBounds(0, 303, 120, 23);
        FormInput.add(LblDokter);
        
        KdPetugas.setBounds(124, 303, 90, 23);
        FormInput.add(KdPetugas);
        NmPetugas.setBounds(216, 303, 200, 23);
        FormInput.add(NmPetugas);
        
        BtnCariPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnCariPetugas.setBounds(418, 303, 28, 23);
        FormInput.add(BtnCariPetugas);

        scrollInput.setViewportView(FormInput);
        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);
        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);
        
        Scroll = new widget.ScrollPane();
        tbKelahiran = new widget.Table();
        Scroll.setViewportView(tbKelahiran);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);
        
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 54));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));
        
        panelGlass8 = new widget.panelisi();
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));
        
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        
        BtnSimpan.setText("Simpan"); 
        BtnBatal.setText("Batal"); 
        BtnHapus.setText("Hapus"); 
        BtnEdit.setText("Edit"); 
        BtnKeluar.setText("Keluar");
        
        panelGlass8.add(BtnSimpan);
        panelGlass8.add(BtnBatal);
        panelGlass8.add(BtnHapus);
        panelGlass8.add(BtnEdit);
        panelGlass8.add(BtnKeluar);
        
        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);
        internalFrame1.add(jPanel3, java.awt.BorderLayout.SOUTH);
        
        this.add(internalFrame1, java.awt.BorderLayout.CENTER);
        
        BtnKeluar.addActionListener(evt -> dispose());
        BtnCariPetugas.addActionListener(evt -> {
            petugas.isCek();
            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
            petugas.setAlwaysOnTop(false);
            petugas.setVisible(true);
        });
        
        jam();
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

                Date now = Calendar.getInstance().getTime();

                if (ChkKejadian.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else {
                    nilai_jam = Jam.getSelectedIndex();
                    nilai_menit = Menit.getSelectedIndex();
                    nilai_detik = Detik.getSelectedIndex();
                }

                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public void autoNomor() {
        try {
            java.util.Date d = Tanggal.getDate();
            String tgl = new java.text.SimpleDateFormat("yyyy-MM-dd").format(d);
            String bulan = tgl.substring(5, 7);
            String tahun = tgl.substring(0, 4);
            
            String suffix = "/RSU-PB/KEB/" + bulan + "/" + tahun;
            
            Valid.autoNomer6("select ifnull(MAX(CONVERT(LEFT(no_surat,2),signed)),0) from surat_keterangan_kelahiran where tanggal='"+tgl+"'", suffix, 2, NoSurat);
        } catch (Exception e) {
            System.out.println("Gagal autoNomor : " + e);
        }
    }

    public void setNoRm(String norwt) {
        autoNomor();
    }
}
