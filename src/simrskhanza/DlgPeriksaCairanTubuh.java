/*
 * DlgPeriksaCairanTubuh.java
 *
 * Created on October 22, 2025
 */

package simrskhanza;

import fungsi.WarnaTable; // Import ini penting
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author Gemini
 */
public class DlgPeriksaCairanTubuh extends javax.swing.JDialog {
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private PreparedStatement ps, ps2; // Tambahkan ps2
    private ResultSet rs, rs2; // Tambahkan rs2
    private String status="", noorder="", pilihan="";
    private String tglSimpan="", jamSimpan="";
    private DefaultTableModel tabModeData; // Model tabel untuk data penilaian
    private int i = 0; // Tambahkan variabel i

    /** Creates new form DlgPeriksaCairanTubuh */
    public DlgPeriksaCairanTubuh(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Model Tabel Data Penilaian
        tabModeData=new DefaultTableModel(null, new Object[]{
                "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Tgl.Lahir", "Tgl.Periksa", "Jam",
                "Spesimen", "Volume", "Warna", "Kejernihan", "Berat Jenis", "pH", "Bau", "Bekuan",
                "Jml.Leukosit", "Sel PNM", "Sel MN", "Jml.Eritrosit", "Rivalta", "Nonne", "Pandy",
                "Protein", "Glukosa", "Kesan", "Usul/Saran", "Kode Dokter PJ", "Nama Dokter PJ",
                "NIP Petugas", "Nama Petugas", "Kode Dokter Perujuk", "Nama Dokter Perujuk"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataPenilaian.setModel(tabModeData);
        tbDataPenilaian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataPenilaian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) { // Sesuaikan jumlah kolom (32)
            TableColumn column = tbDataPenilaian.getColumnModel().getColumn(i);
            // Atur lebar kolom sesuai kebutuhan
            if(i==0){ column.setPreferredWidth(105); }
            else if(i==1){ column.setPreferredWidth(65); }
            else if(i==2){ column.setPreferredWidth(150); }
            else if(i==3){ column.setPreferredWidth(30); }
            else if(i==4){ column.setPreferredWidth(70); }
            else if(i==5){ column.setPreferredWidth(70); }
            else if(i==6){ column.setPreferredWidth(60); }
            else if(i==7){ column.setPreferredWidth(150); } // Spesimen
            else if(i==8){ column.setPreferredWidth(70); } // Volume
            else if(i==9){ column.setPreferredWidth(70); } // Warna
            else if(i==10){ column.setPreferredWidth(70); } // Kejernihan
            else if(i==11){ column.setPreferredWidth(70); } // Berat Jenis
            else if(i==12){ column.setPreferredWidth(40); } // pH
            else if(i==13){ column.setPreferredWidth(70); } // Bau
            else if(i==14){ column.setPreferredWidth(70); } // Bekuan
            else if(i==15){ column.setPreferredWidth(70); } // Jml Leukosit
            else if(i==16){ column.setPreferredWidth(70); } // Sel PNM
            else if(i==17){ column.setPreferredWidth(70); } // Sel MN
            else if(i==18){ column.setPreferredWidth(70); } // Jml Eritrosit
            else if(i==19){ column.setPreferredWidth(70); } // Rivalta
            else if(i==20){ column.setPreferredWidth(70); } // Nonne
            else if(i==21){ column.setPreferredWidth(70); } // Pandy
            else if(i==22){ column.setPreferredWidth(70); } // Protein
            else if(i==23){ column.setPreferredWidth(70); } // Glukosa
            else if(i==24){ column.setPreferredWidth(200); } // Kesan
            else if(i==25){ column.setPreferredWidth(200); } // Saran
            else if(i==26){ column.setMinWidth(0); column.setMaxWidth(0); } // Kode Dokter PJ
            else if(i==27){ column.setPreferredWidth(150); } // Nama Dokter PJ
            else if(i==28){ column.setMinWidth(0); column.setMaxWidth(0); } // NIP Petugas
            else if(i==29){ column.setPreferredWidth(150); } // Nama Petugas
            else if(i==30){ column.setMinWidth(0); column.setMaxWidth(0); } // Kode Dokter Perujuk
            else if(i==31){ column.setPreferredWidth(150); } // Nama Dokter Perujuk
        }
        tbDataPenilaian.setDefaultRenderer(Object.class, new WarnaTable());

        // Batasan Input
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KodePj.setDocument(new batasInput((byte)20).getKata(KodePj));
        KodePerujuk.setDocument(new batasInput((byte)20).getKata(KodePerujuk));
        KdPtg.setDocument(new batasInput((byte)20).getKata(KdPtg));
        Spesimen.setDocument(new batasInput((byte)50).getKata(Spesimen));
        Volume.setDocument(new batasInput((byte)20).getKata(Volume));
        Warna.setDocument(new batasInput((byte)30).getKata(Warna));
        Kejernihan.setDocument(new batasInput((byte)30).getKata(Kejernihan));
        BeratJenis.setDocument(new batasInput((byte)15).getKata(BeratJenis));
        Ph.setDocument(new batasInput((byte)10).getKata(Ph));
        Bau.setDocument(new batasInput((byte)20).getKata(Bau));
        Bekuan.setDocument(new batasInput((byte)20).getKata(Bekuan));
        JmlLeukosit.setDocument(new batasInput((byte)20).getKata(JmlLeukosit));
        SelPNM.setDocument(new batasInput((byte)30).getKata(SelPNM));
        SelMN.setDocument(new batasInput((byte)30).getKata(SelMN));
        JmlEritrosit.setDocument(new batasInput((byte)20).getKata(JmlEritrosit));
        TestRivalta.setDocument(new batasInput((byte)20).getKata(TestRivalta));
        TestNonne.setDocument(new batasInput((byte)20).getKata(TestNonne));
        TestPandy.setDocument(new batasInput((byte)20).getKata(TestPandy));
        Protein.setDocument(new batasInput((byte)20).getKata(Protein));
        Glukosa.setDocument(new batasInput((byte)20).getKata(Glukosa));
        Kesan.setDocument(new batasInput((byte)100).getKata(Kesan));
        Saran.setDocument(new batasInput((byte)100).getKata(Saran));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));

        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampilData(); } }
                @Override
                public void removeUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampilData(); } }
                @Override
                public void changedUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ tampilData(); } }
            });
        }

        jam();
        
        // Listener tabel data
        tbDataPenilaian.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(tabModeData.getRowCount()!=0){
                    try {
                        getData();
                    } catch (java.lang.NullPointerException ex) {
                    }
                }
            }
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPeriksaCairanTubuh")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    } 
                    KdPtg.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPeriksaCairanTubuh")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        if(pilihan.equals("perujuk")){
                            KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodePerujuk.requestFocus();
                        }else if(pilihan.equals("penjab")){
                            KodePj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterPj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodePj.requestFocus();
                        }                        
                    }  
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        TabInput = new javax.swing.JPanel();
        panelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        KodePj = new widget.TextBox();
        NmDokterPj = new widget.TextBox();
        btnDokterPj = new widget.Button();
        jLabel9 = new widget.Label();
        KodePerujuk = new widget.TextBox();
        NmPerujuk = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel12 = new widget.Label();
        KdPtg = new widget.TextBox();
        NmPtg = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel15 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel16 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        Scroll = new widget.ScrollPane();
        PanelContent = new widget.PanelBiasa();
        jLabelSpesimen = new widget.Label();
        Spesimen = new widget.TextBox();
        PanelMakroskopis = new widget.PanelBiasa();
        jLabelVolume = new widget.Label();
        Volume = new widget.TextBox();
        jLabelWarna = new widget.Label();
        Warna = new widget.TextBox();
        jLabelKejernihan = new widget.Label();
        Kejernihan = new widget.TextBox();
        jLabelBeratJenis = new widget.Label();
        BeratJenis = new widget.TextBox();
        jLabelPh = new widget.Label();
        Ph = new widget.TextBox();
        jLabelBau = new widget.Label();
        Bau = new widget.TextBox();
        jLabelBekuan = new widget.Label();
        Bekuan = new widget.TextBox();
        PanelMikroskopis = new widget.PanelBiasa();
        jLabelJmlLeukosit = new widget.Label();
        JmlLeukosit = new widget.TextBox();
        jLabelSelPNM = new widget.Label();
        SelPNM = new widget.TextBox();
        jLabelSelMN = new widget.Label();
        SelMN = new widget.TextBox();
        jLabelJmlEritrosit = new widget.Label();
        JmlEritrosit = new widget.TextBox();
        PanelKimiawi = new widget.PanelBiasa();
        jLabelTestRivalta = new widget.Label();
        TestRivalta = new widget.TextBox();
        jLabelTestNonne = new widget.Label();
        TestNonne = new widget.TextBox();
        jLabelTestPandy = new widget.Label();
        TestPandy = new widget.TextBox();
        jLabelProtein = new widget.Label();
        Protein = new widget.TextBox();
        jLabelGlukosa = new widget.Label();
        Glukosa = new widget.TextBox();
        jLabelKesan = new widget.Label();
        Kesan = new widget.TextBox();
        jLabelSaran = new widget.Label();
        Saran = new widget.TextBox();
        TabData = new javax.swing.JPanel();
        ScrollData = new widget.ScrollPane();
        tbDataPenilaian = new widget.Table();
        panelCariData = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7_Data = new widget.Label();
        LCount = new widget.Label();
        panelTombol = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBaru = new widget.Button();
        BtnGanti = new widget.Button();
        BtnHapus = new widget.Button();
        BtnCetak = new widget.Button(); // Deklarasi BtnCetak ditambahkan di sini
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemeriksaan Cairan Tubuh Patologi Klinis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        TabInput.setBackground(new java.awt.Color(255, 255, 255));
        TabInput.setName("TabInput"); // NOI18N
        TabInput.setLayout(new java.awt.BorderLayout());

        panelInput.setName("panelInput"); // NOI18N
        panelInput.setPreferredSize(new java.awt.Dimension(800, 130));
        panelInput.setLayout(null);

        jLabel3.setText("No. Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelInput.add(jLabel3);
        jLabel3.setBounds(10, 10, 80, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        panelInput.add(TNoRw);
        TNoRw.setBounds(95, 10, 128, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        panelInput.add(TNoRM);
        TNoRM.setBounds(225, 10, 108, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelInput.add(TPasien);
        TPasien.setBounds(335, 10, 480, 23);

        jLabel7.setText("Dokter P.J. :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelInput.add(jLabel7);
        jLabel7.setBounds(10, 40, 80, 23);

        KodePj.setName("KodePj"); // NOI18N
        panelInput.add(KodePj);
        KodePj.setBounds(95, 40, 80, 23);

        NmDokterPj.setEditable(false);
        NmDokterPj.setHighlighter(null);
        NmDokterPj.setName("NmDokterPj"); // NOI18N
        panelInput.add(NmDokterPj);
        NmDokterPj.setBounds(177, 40, 193, 23);

        btnDokterPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterPj.setMnemonic('1');
        btnDokterPj.setToolTipText("Alt+1");
        btnDokterPj.setName("btnDokterPj"); // NOI18N
        btnDokterPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterPjActionPerformed(evt);
            }
        });
        panelInput.add(btnDokterPj);
        btnDokterPj.setBounds(373, 40, 28, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelInput.add(jLabel9);
        jLabel9.setBounds(10, 70, 80, 23);

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        panelInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 70, 80, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        panelInput.add(NmPerujuk);
        NmPerujuk.setBounds(177, 70, 193, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("Alt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        panelInput.add(btnDokter);
        btnDokter.setBounds(373, 70, 28, 23);

        jLabel12.setText("Petugas :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelInput.add(jLabel12);
        jLabel12.setBounds(410, 40, 60, 23);

        KdPtg.setName("KdPtg"); // NOI18N
        KdPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgKeyPressed(evt);
            }
        });
        panelInput.add(KdPtg);
        KdPtg.setBounds(475, 40, 80, 23);

        NmPtg.setEditable(false);
        NmPtg.setName("NmPtg"); // NOI18N
        panelInput.add(NmPtg);
        NmPtg.setBounds(557, 40, 228, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelInput.add(btnPetugas);
        btnPetugas.setBounds(788, 40, 28, 23);

        jLabel15.setText("Tanggal :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelInput.add(jLabel15);
        jLabel15.setBounds(410, 70, 60, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-10-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        panelInput.add(Tanggal);
        Tanggal.setBounds(475, 70, 90, 23);

        jLabel16.setText("Jam :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelInput.add(jLabel16);
        jLabel16.setBounds(565, 70, 40, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        panelInput.add(CmbJam);
        CmbJam.setBounds(608, 70, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        panelInput.add(CmbMenit);
        CmbMenit.setBounds(673, 70, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        panelInput.add(CmbDetik);
        CmbDetik.setBounds(738, 70, 62, 23);

        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
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
        panelInput.add(ChkJln);
        ChkJln.setBounds(803, 70, 23, 23);

        TabInput.add(panelInput, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N

        PanelContent.setName("PanelContent"); // NOI18N
        PanelContent.setPreferredSize(new java.awt.Dimension(800, 365));
        PanelContent.setLayout(null);

        jLabelSpesimen.setText("Spesimen Berupa :");
        jLabelSpesimen.setName("jLabelSpesimen"); // NOI18N
        PanelContent.add(jLabelSpesimen);
        jLabelSpesimen.setBounds(10, 10, 110, 23);

        Spesimen.setName("Spesimen"); // NOI18N
        Spesimen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpesimenKeyPressed(evt);
            }
        });
        PanelContent.add(Spesimen);
        Spesimen.setBounds(125, 10, 695, 23);

        PanelMakroskopis.setBorder(javax.swing.BorderFactory.createTitledBorder(" Makroskopis "));
        PanelMakroskopis.setName("PanelMakroskopis"); // NOI18N
        PanelMakroskopis.setLayout(null);

        jLabelVolume.setText("Volume :");
        jLabelVolume.setName("jLabelVolume"); // NOI18N
        PanelMakroskopis.add(jLabelVolume);
        jLabelVolume.setBounds(15, 20, 80, 23);

        Volume.setName("Volume"); // NOI18N
        Volume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumeKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Volume);
        Volume.setBounds(100, 20, 160, 23);

        jLabelWarna.setText("Warna :");
        jLabelWarna.setName("jLabelWarna"); // NOI18N
        PanelMakroskopis.add(jLabelWarna);
        jLabelWarna.setBounds(15, 50, 80, 23);

        Warna.setName("Warna"); // NOI18N
        Warna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Warna);
        Warna.setBounds(100, 50, 160, 23);

        jLabelKejernihan.setText("Kejernihan :");
        jLabelKejernihan.setName("jLabelKejernihan"); // NOI18N
        PanelMakroskopis.add(jLabelKejernihan);
        jLabelKejernihan.setBounds(15, 80, 80, 23);

        Kejernihan.setName("Kejernihan"); // NOI18N
        Kejernihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KejernihanKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Kejernihan);
        Kejernihan.setBounds(100, 80, 160, 23);

        jLabelBeratJenis.setText("Berat Jenis :");
        jLabelBeratJenis.setName("jLabelBeratJenis"); // NOI18N
        PanelMakroskopis.add(jLabelBeratJenis);
        jLabelBeratJenis.setBounds(15, 110, 80, 23);

        BeratJenis.setName("BeratJenis"); // NOI18N
        BeratJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratJenisKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(BeratJenis);
        BeratJenis.setBounds(100, 110, 160, 23);

        jLabelPh.setText("pH :");
        jLabelPh.setName("jLabelPh"); // NOI18N
        PanelMakroskopis.add(jLabelPh);
        jLabelPh.setBounds(15, 140, 80, 23);

        Ph.setName("Ph"); // NOI18N
        Ph.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PhKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Ph);
        Ph.setBounds(100, 140, 160, 23);

        jLabelBau.setText("Bau :");
        jLabelBau.setName("jLabelBau"); // NOI18N
        PanelMakroskopis.add(jLabelBau);
        jLabelBau.setBounds(15, 170, 80, 23);

        Bau.setName("Bau"); // NOI18N
        Bau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BauKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Bau);
        Bau.setBounds(100, 170, 160, 23);

        jLabelBekuan.setText("Bekuan :");
        jLabelBekuan.setName("jLabelBekuan"); // NOI18N
        PanelMakroskopis.add(jLabelBekuan);
        jLabelBekuan.setBounds(15, 200, 80, 23);

        Bekuan.setName("Bekuan"); // NOI18N
        Bekuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BekuanKeyPressed(evt);
            }
        });
        PanelMakroskopis.add(Bekuan);
        Bekuan.setBounds(100, 200, 160, 23);

        PanelContent.add(PanelMakroskopis);
        PanelMakroskopis.setBounds(10, 45, 275, 240);

        PanelMikroskopis.setBorder(javax.swing.BorderFactory.createTitledBorder(" Mikroskopis "));
        PanelMikroskopis.setName("PanelMikroskopis"); // NOI18N
        PanelMikroskopis.setLayout(null);

        jLabelJmlLeukosit.setText("Jml Leukosit :");
        jLabelJmlLeukosit.setName("jLabelJmlLeukosit"); // NOI18N
        PanelMikroskopis.add(jLabelJmlLeukosit);
        jLabelJmlLeukosit.setBounds(15, 20, 80, 23);

        JmlLeukosit.setName("JmlLeukosit"); // NOI18N
        JmlLeukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlLeukositKeyPressed(evt);
            }
        });
        PanelMikroskopis.add(JmlLeukosit);
        JmlLeukosit.setBounds(100, 20, 160, 23);

        jLabelSelPNM.setText("Sel PNM :");
        jLabelSelPNM.setName("jLabelSelPNM"); // NOI18N
        PanelMikroskopis.add(jLabelSelPNM);
        jLabelSelPNM.setBounds(15, 50, 80, 23);

        SelPNM.setName("SelPNM"); // NOI18N
        SelPNM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SelPNMKeyPressed(evt);
            }
        });
        PanelMikroskopis.add(SelPNM);
        SelPNM.setBounds(100, 50, 160, 23);

        jLabelSelMN.setText("Sel MN :");
        jLabelSelMN.setName("jLabelSelMN"); // NOI18N
        PanelMikroskopis.add(jLabelSelMN);
        jLabelSelMN.setBounds(15, 80, 80, 23);

        SelMN.setName("SelMN"); // NOI18N
        SelMN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SelMNKeyPressed(evt);
            }
        });
        PanelMikroskopis.add(SelMN);
        SelMN.setBounds(100, 80, 160, 23);

        jLabelJmlEritrosit.setText("Jml Eritrosit :");
        jLabelJmlEritrosit.setName("jLabelJmlEritrosit"); // NOI18N
        PanelMikroskopis.add(jLabelJmlEritrosit);
        jLabelJmlEritrosit.setBounds(15, 110, 80, 23);

        JmlEritrosit.setName("JmlEritrosit"); // NOI18N
        JmlEritrosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlEritrositKeyPressed(evt);
            }
        });
        PanelMikroskopis.add(JmlEritrosit);
        JmlEritrosit.setBounds(100, 110, 160, 23);

        PanelContent.add(PanelMikroskopis);
        PanelMikroskopis.setBounds(295, 45, 275, 150);

        PanelKimiawi.setBorder(javax.swing.BorderFactory.createTitledBorder(" Kimiawi "));
        PanelKimiawi.setName("PanelKimiawi"); // NOI18N
        PanelKimiawi.setLayout(null);

        jLabelTestRivalta.setText("Test Rivalta :");
        jLabelTestRivalta.setName("jLabelTestRivalta"); // NOI18N
        PanelKimiawi.add(jLabelTestRivalta);
        jLabelTestRivalta.setBounds(15, 20, 80, 23);

        TestRivalta.setName("TestRivalta"); // NOI18N
        TestRivalta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TestRivaltaKeyPressed(evt);
            }
        });
        PanelKimiawi.add(TestRivalta);
        TestRivalta.setBounds(100, 20, 160, 23);

        jLabelTestNonne.setText("Test nonne :");
        jLabelTestNonne.setName("jLabelTestNonne"); // NOI18N
        PanelKimiawi.add(jLabelTestNonne);
        jLabelTestNonne.setBounds(15, 50, 80, 23);

        TestNonne.setName("TestNonne"); // NOI18N
        TestNonne.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TestNonneKeyPressed(evt);
            }
        });
        PanelKimiawi.add(TestNonne);
        TestNonne.setBounds(100, 50, 160, 23);

        jLabelTestPandy.setText("Test Pandy :");
        jLabelTestPandy.setName("jLabelTestPandy"); // NOI18N
        PanelKimiawi.add(jLabelTestPandy);
        jLabelTestPandy.setBounds(15, 80, 80, 23);

        TestPandy.setName("TestPandy"); // NOI18N
        TestPandy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TestPandyKeyPressed(evt);
            }
        });
        PanelKimiawi.add(TestPandy);
        TestPandy.setBounds(100, 80, 160, 23);

        jLabelProtein.setText("Protein :");
        jLabelProtein.setName("jLabelProtein"); // NOI18N
        PanelKimiawi.add(jLabelProtein);
        jLabelProtein.setBounds(15, 110, 80, 23);

        Protein.setName("Protein"); // NOI18N
        Protein.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProteinKeyPressed(evt);
            }
        });
        PanelKimiawi.add(Protein);
        Protein.setBounds(100, 110, 160, 23);

        jLabelGlukosa.setText("Glukosa :");
        jLabelGlukosa.setName("jLabelGlukosa"); // NOI18N
        PanelKimiawi.add(jLabelGlukosa);
        jLabelGlukosa.setBounds(15, 140, 80, 23);

        Glukosa.setName("Glukosa"); // NOI18N
        Glukosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GlukosaKeyPressed(evt);
            }
        });
        PanelKimiawi.add(Glukosa);
        Glukosa.setBounds(100, 140, 160, 23);

        PanelContent.add(PanelKimiawi);
        PanelKimiawi.setBounds(580, 45, 275, 180);

        jLabelKesan.setText("Kesan :");
        jLabelKesan.setName("jLabelKesan"); // NOI18N
        PanelContent.add(jLabelKesan);
        jLabelKesan.setBounds(10, 295, 110, 23);

        Kesan.setName("Kesan"); // NOI18N
        Kesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesanKeyPressed(evt);
            }
        });
        PanelContent.add(Kesan);
        Kesan.setBounds(125, 295, 700, 23);

        jLabelSaran.setText("Usul/Saran :");
        jLabelSaran.setName("jLabelSaran"); // NOI18N
        PanelContent.add(jLabelSaran);
        jLabelSaran.setBounds(10, 325, 110, 23);

        Saran.setName("Saran"); // NOI18N
        Saran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaranKeyPressed(evt);
            }
        });
        PanelContent.add(Saran);
        Saran.setBounds(125, 325, 700, 23);

        Scroll.setViewportView(PanelContent);

        TabInput.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", TabInput);

        TabData.setBackground(new java.awt.Color(255, 255, 255));
        TabData.setName("TabData"); // NOI18N
        TabData.setLayout(new java.awt.BorderLayout());

        ScrollData.setName("ScrollData"); // NOI18N
        ScrollData.setOpaque(true);

        tbDataPenilaian.setName("tbDataPenilaian"); // NOI18N
        ScrollData.setViewportView(tbDataPenilaian);

        TabData.add(ScrollData, java.awt.BorderLayout.CENTER);

        panelCariData.setName("panelCariData"); // NOI18N
        panelCariData.setPreferredSize(new java.awt.Dimension(0, 43));
        panelCariData.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. Periksa :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelCariData.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-10-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCariData.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelCariData.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-10-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCariData.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelCariData.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelCariData.add(TCari);

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
        panelCariData.add(BtnCari);

        jLabel7_Data.setText("Record :");
        jLabel7_Data.setName("jLabel7_Data"); // NOI18N
        jLabel7_Data.setPreferredSize(new java.awt.Dimension(65, 23));
        panelCariData.add(jLabel7_Data);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelCariData.add(LCount);

        TabData.add(panelCariData, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Penilaian", TabData);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelTombol.setName("panelTombol"); // NOI18N
        panelTombol.setPreferredSize(new java.awt.Dimension(55, 55));
        panelTombol.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelTombol.add(BtnSimpan);

        BtnBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
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
        panelTombol.add(BtnBaru);

        BtnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnGanti.setMnemonic('G');
        BtnGanti.setText("Ganti");
        BtnGanti.setToolTipText("Alt+G");
        BtnGanti.setName("BtnGanti"); // NOI18N
        BtnGanti.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGantiActionPerformed(evt);
            }
        });
        panelTombol.add(BtnGanti);

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
        panelTombol.add(BtnHapus);

        BtnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnCetak.setMnemonic('T');
        BtnCetak.setText("Cetak");
        BtnCetak.setToolTipText("Alt+T");
        BtnCetak.setName("BtnCetak"); // NOI18N
        BtnCetak.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCetakActionPerformed(evt);
            }
        });
        panelTombol.add(BtnCetak); // <<<---- Tambahkan BtnCetak ke panelTombol

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
        panelTombol.add(BtnKeluar);

        internalFrame1.add(panelTombol, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
        setSize(880, 550);
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    // ... (sisa method lainnya: BtnSimpanActionPerformed, BtnGantiActionPerformed, ..., tampil(), jam(), isPasien(), isCek(), emptTeks()) ...
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodePj.getText().trim().equals("")||NmDokterPj.getText().trim().equals("")){
            Valid.textKosong(KodePj,"Dokter P.J.");
        }else if(KodePerujuk.getText().trim().equals("")||NmPerujuk.getText().trim().equals("")){
            Valid.textKosong(KodePerujuk,"Dokter Perujuk");
        }else if(KdPtg.getText().trim().equals("")||NmPtg.getText().trim().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else{
            Sequel.menyimpan("pemeriksaan_cairan_tubuh_pk", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 25, new String[]{
                TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem()+""), CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                KodePj.getText(), KodePerujuk.getText(), KdPtg.getText(), Spesimen.getText(), Volume.getText(), Warna.getText(), Kejernihan.getText(),
                BeratJenis.getText(), Ph.getText(), Bau.getText(), Bekuan.getText(), JmlLeukosit.getText(), SelPNM.getText(), SelMN.getText(),
                JmlEritrosit.getText(), TestRivalta.getText(), TestNonne.getText(), TestPandy.getText(), Protein.getText(), Glukosa.getText(),
                Kesan.getText(), Saran.getText()
            });
            JOptionPane.showMessageDialog(null,"Proses simpan selesai...");
            emptTeks();
        }
    }                                         

    private void BtnGantiActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodePj.getText().trim().equals("")||NmDokterPj.getText().trim().equals("")){
            Valid.textKosong(KodePj,"Dokter P.J.");
        }else if(KodePerujuk.getText().trim().equals("")||NmPerujuk.getText().trim().equals("")){
            Valid.textKosong(KodePerujuk,"Dokter Perujuk");
        }else if(KdPtg.getText().trim().equals("")||NmPtg.getText().trim().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else{
            if(JOptionPane.showConfirmDialog(null, "Yakin anda mau merubah data ini?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                Sequel.queryu2("UPDATE pemeriksaan_cairan_tubuh_pk SET tgl_periksa=?, jam=?, kd_dokter_pj=?, kd_dokter_perujuk=?, nip=?, " +
                    "spesimen=?, makro_volume=?, makro_warna=?, makro_kejernihan=?, makro_berat_jenis=?, makro_ph=?, makro_bau=?, makro_bekuan=?, " +
                    "mikro_jml_leukosit=?, mikro_sel_pnm=?, mikro_sel_mn=?, mikro_jml_eritrosit=?, kimia_rivalta=?, kimia_nonne=?, kimia_pandy=?, " +
                    "kimia_protein=?, kimia_glukosa=?, kesan=?, saran=? WHERE no_rawat=? AND tgl_periksa=? AND jam=?", 26, new String[]{
                    Valid.SetTgl(Tanggal.getSelectedItem()+""), CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    KodePj.getText(), KodePerujuk.getText(), KdPtg.getText(), Spesimen.getText(), Volume.getText(), Warna.getText(), Kejernihan.getText(),
                    BeratJenis.getText(), Ph.getText(), Bau.getText(), Bekuan.getText(), JmlLeukosit.getText(), SelPNM.getText(), SelMN.getText(),
                    JmlEritrosit.getText(), TestRivalta.getText(), TestNonne.getText(), TestPandy.getText(), Protein.getText(), Glukosa.getText(),
                    Kesan.getText(), Saran.getText(),
                    TNoRw.getText(), tglSimpan, jamSimpan
                });
                JOptionPane.showMessageDialog(null,"Proses ganti selesai...");
                emptTeks();
            }
        }
    }                                        

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        } else if(tglSimpan.equals("") || jamSimpan.equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang akan dihapus.");
        } else {
            if(JOptionPane.showConfirmDialog(null, "Yakin anda mau menghapus data ini?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                Sequel.queryu2("DELETE FROM pemeriksaan_cairan_tubuh_pk WHERE no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                    TNoRw.getText(), tglSimpan, jamSimpan
                });
                JOptionPane.showMessageDialog(null,"Proses hapus selesai...");
                emptTeks();
            }
        }
    }                                        

    private void BtnBaruActionPerformed(java.awt.event.ActionEvent evt) {                                        
        emptTeks();
    }                                       

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        dispose();
    }                                         

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        CmbJam.setEnabled(!ChkJln.isSelected());
        CmbMenit.setEnabled(!ChkJln.isSelected());
        CmbDetik.setEnabled(!ChkJln.isSelected());
    }                                      

    private void btnDokterPjActionPerformed(java.awt.event.ActionEvent evt) {                                            
        pilihan="penjab";
        akses.setform("DlgPeriksaCairanTubuh");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }                                           

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {                                          
        pilihan="perujuk";
        akses.setform("DlgPeriksaCairanTubuh");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }                                         

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {                                           
        akses.setform("DlgPeriksaCairanTubuh");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }                                          

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {                                       
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPerujuk.setText(dokter.tampil3(KodePerujuk.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,KdPtg,Tanggal);
        }
    }                                      

    private void KdPtgKeyPressed(java.awt.event.KeyEvent evt) {                                
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPtg.setText(petugas.tampil3(KdPtg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodePj,KodePerujuk);
        }
    }                                

    private void SpesimenKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Volume.requestFocusInWindow();
        }
    }                                   

    private void VolumeKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Warna.requestFocusInWindow();
        }
    }                                 

    private void WarnaKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Kejernihan.requestFocusInWindow();
        }
    }                                

    private void KejernihanKeyPressed(java.awt.event.KeyEvent evt) {                                      
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BeratJenis.requestFocusInWindow();
        }
    }                                     

    private void BeratJenisKeyPressed(java.awt.event.KeyEvent evt) {                                      
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Ph.requestFocusInWindow();
        }
    }                                     

    private void PhKeyPressed(java.awt.event.KeyEvent evt) {                             
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Bau.requestFocusInWindow();
        }
    }                           

    private void BauKeyPressed(java.awt.event.KeyEvent evt) {                             
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Bekuan.requestFocusInWindow();
        }
    }                          

    private void BekuanKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JmlLeukosit.requestFocusInWindow();
        }
    }                                 

    private void JmlLeukositKeyPressed(java.awt.event.KeyEvent evt) {                                       
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SelPNM.requestFocusInWindow();
        }
    }                                      

    private void SelPNMKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SelMN.requestFocusInWindow();
        }
    }                                 

    private void SelMNKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JmlEritrosit.requestFocusInWindow();
        }
    }                               

    private void JmlEritrositKeyPressed(java.awt.event.KeyEvent evt) {                                        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TestRivalta.requestFocusInWindow();
        }
    }                                       

    private void TestRivaltaKeyPressed(java.awt.event.KeyEvent evt) {                                       
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TestNonne.requestFocusInWindow();
        }
    }                                      

    private void TestNonneKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TestPandy.requestFocusInWindow();
        }
    }                                    

    private void TestPandyKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Protein.requestFocusInWindow();
        }
    }                                    

    private void ProteinKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Glukosa.requestFocusInWindow();
        }
    }                                  

    private void GlukosaKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Kesan.requestFocusInWindow();
        }
    }                                  

    private void KesanKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Saran.requestFocusInWindow();
        }
    }                                

    private void SaranKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnSimpan.requestFocusInWindow();
        }
    }                                

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgPeriksaCairanTubuh dialog = new DlgPeriksaCairanTubuh(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Method baru untuk menampilkan data ke tabel
    private void tampilData() {
        Valid.tabelKosong(tabModeData);
        try{
            // Query dasar
            String sql="select pc.no_rawat, p.no_rkm_medis, p.nm_pasien, p.jk, p.tgl_lahir, pc.tgl_periksa, pc.jam, "+
                   "pc.spesimen, pc.makro_volume, pc.makro_warna, pc.makro_kejernihan, pc.makro_berat_jenis, pc.makro_ph, pc.makro_bau, pc.makro_bekuan, "+
                   "pc.mikro_jml_leukosit, pc.mikro_sel_pnm, pc.mikro_sel_mn, pc.mikro_jml_eritrosit, pc.kimia_rivalta, pc.kimia_nonne, pc.kimia_pandy, "+
                   "pc.kimia_protein, pc.kimia_glukosa, pc.kesan, pc.saran, pc.kd_dokter_pj, d1.nm_dokter as dokter_pj, pc.nip, pt.nama as petugas, pc.kd_dokter_perujuk, d2.nm_dokter as dokter_perujuk "+
                   "from pemeriksaan_cairan_tubuh_pk pc inner join reg_periksa rp on pc.no_rawat=rp.no_rawat "+
                   "inner join pasien p on rp.no_rkm_medis=p.no_rkm_medis "+
                   "inner join dokter d1 on pc.kd_dokter_pj=d1.kd_dokter "+
                   "inner join petugas pt on pc.nip=pt.nip "+
                   "inner join dokter d2 on pc.kd_dokter_perujuk=d2.kd_dokter "+
                   "where pc.tgl_periksa between ? and ? "; // Filter tanggal selalu ada

            // Cek isi TCari
            if(!TCari.getText().trim().equals("")){
                // Jika TCari berisi No Rawat yang aktif di form
                if(TCari.getText().trim().equals(TNoRw.getText().trim())){
                     sql=sql+"and pc.no_rawat = ? "; // Filter spesifik No Rawat
                } else {
                // Jika TCari berisi keyword lain (pencarian umum)
                     sql=sql+"and (pc.no_rawat like ? or p.no_rkm_medis like ? or p.nm_pasien like ? or pc.spesimen like ? or pc.kesan like ?) ";
                }
            }
            sql=sql+"order by pc.tgl_periksa, pc.jam"; // Urutkan

            ps=koneksi.prepareStatement(sql);
            try {
                int parameterIndex = 1; // Index untuk parameter PreparedStatement
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari1.getSelectedItem()+"")); // Parameter 1: Tanggal Awal
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari2.getSelectedItem()+"")); // Parameter 2: Tanggal Akhir

                // Set parameter berdasarkan isi TCari
                if(!TCari.getText().trim().equals("")){
                    if(TCari.getText().trim().equals(TNoRw.getText().trim())){
                         ps.setString(parameterIndex++, TNoRw.getText().trim()); // Parameter 3: No Rawat Spesifik
                    } else {
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Parameter 3: Keyword No Rawat
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Parameter 4: Keyword No RM
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Parameter 5: Keyword Nama Pasien
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Parameter 6: Keyword Spesimen
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Parameter 7: Keyword Kesan
                    }
                }

                rs=ps.executeQuery();
                while(rs.next()){
                    // Kode untuk mengisi tabel (sama seperti sebelumnya)
                    tabModeData.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),
                        rs.getString("tgl_lahir"),rs.getString("tgl_periksa"),rs.getString("jam"),rs.getString("spesimen"),
                        rs.getString("makro_volume"),rs.getString("makro_warna"),rs.getString("makro_kejernihan"),rs.getString("makro_berat_jenis"),
                        rs.getString("makro_ph"),rs.getString("makro_bau"),rs.getString("makro_bekuan"),rs.getString("mikro_jml_leukosit"),
                        rs.getString("mikro_sel_pnm"),rs.getString("mikro_sel_mn"),rs.getString("mikro_jml_eritrosit"),rs.getString("kimia_rivalta"),
                        rs.getString("kimia_nonne"),rs.getString("kimia_pandy"),rs.getString("kimia_protein"),rs.getString("kimia_glukosa"),
                        rs.getString("kesan"),rs.getString("saran"),rs.getString("kd_dokter_pj"),rs.getString("dokter_pj"),
                        rs.getString("nip"),rs.getString("petugas"),rs.getString("kd_dokter_perujuk"),rs.getString("dokter_perujuk")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Tampil Data : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCount.setText(""+tabModeData.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi Error Tampil Data: "+e);
        }
    }

    // Method baru untuk mengambil data dari tabel dan menampilkannya di form input
    private void getData(){
        if(tbDataPenilaian.getSelectedRow()!= -1){
            String norawat = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),0).toString();
            String norm = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),1).toString();
            String nampasien = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),2).toString();
            String tglperiksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),5).toString();
            String jamperiksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),6).toString();
            String spesimen = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),7).toString();
            String volume = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),8).toString();
            String warna = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),9).toString();
            String kejernihan = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),10).toString();
            String beratjenis = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),11).toString();
            String ph = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),12).toString();
            String bau = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),13).toString();
            String bekuan = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),14).toString();
            String jmlleukosit = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),15).toString();
            String selpnm = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),16).toString();
            String selmn = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),17).toString();
            String jmleritrosit = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),18).toString();
            String rivalta = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),19).toString();
            String nonne = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),20).toString();
            String pandy = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),21).toString();
            String protein = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),22).toString();
            String glukosa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),23).toString();
            String kesan = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),24).toString();
            String saran = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),25).toString();
            String kddokterpj = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),26).toString();
            String nmdokterpj = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),27).toString();
            String nippetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),28).toString();
            String nmpetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),29).toString();
            String kddokterperujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),30).toString();
            String nmdokterperujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),31).toString();

            TNoRw.setText(norawat);
            TNoRM.setText(norm);
            TPasien.setText(nampasien);
            Valid.SetTgl(Tanggal,tglperiksa);
            CmbJam.setSelectedItem(jamperiksa.substring(0,2));
            CmbMenit.setSelectedItem(jamperiksa.substring(3,5));
            CmbDetik.setSelectedItem(jamperiksa.substring(6,8));
            KodePj.setText(kddokterpj);
            NmDokterPj.setText(nmdokterpj);
            KodePerujuk.setText(kddokterperujuk);
            NmPerujuk.setText(nmdokterperujuk);
            KdPtg.setText(nippetugas);
            NmPtg.setText(nmpetugas);
            Spesimen.setText(spesimen);
            Volume.setText(volume);
            Warna.setText(warna);
            Kejernihan.setText(kejernihan);
            BeratJenis.setText(beratjenis);
            Ph.setText(ph);
            Bau.setText(bau);
            Bekuan.setText(bekuan);
            JmlLeukosit.setText(jmlleukosit);
            SelPNM.setText(selpnm);
            SelMN.setText(selmn);
            JmlEritrosit.setText(jmleritrosit);
            TestRivalta.setText(rivalta);
            TestNonne.setText(nonne);
            TestPandy.setText(pandy);
            Protein.setText(protein);
            Glukosa.setText(glukosa);
            Kesan.setText(kesan);
            Saran.setText(saran);
            TCari.setText(norawat);

            tglSimpan = tglperiksa; // Simpan tanggal asli
            jamSimpan = jamperiksa; // Simpan jam asli
            
            // Nonaktifkan input tanggal & jam jika data lama ditampilkan
            Tanggal.setEnabled(false);
            ChkJln.setSelected(false);
            ChkJlnActionPerformed(null); // Update status enabled ComboBox jam
        }
    }
    
    // Action listener untuk tombol cari data
    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        tampilData();
    }                                       

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnKeluar);
        }
    }                                    

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            // Tidak ada komponen sebelum TCari di tab ini
        }
    }                                
    
    // Action listener saat tab di-klik
    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {                                      
        if(TabRawat.getSelectedIndex()==1){ // Jika tab data yang dipilih
            tampilData();
        }
    }

    // Method kosongkan form, perlu diperbaiki agar mengaktifkan input tgl & jam
    public void emptTeks() {
        // ... (Kode kosongkan textfield seperti sebelumnya) ...
        KodePerujuk.setText("");
        NmPerujuk.setText("");
        KodePj.setText("");
        NmDokterPj.setText("");
        KdPtg.setText("");
        NmPtg.setText("");
        Spesimen.setText("");
        Volume.setText("");
        Warna.setText("");
        Kejernihan.setText("");
        BeratJenis.setText("");
        Ph.setText("");
        Bau.setText("");
        Bekuan.setText("");
        JmlLeukosit.setText("");
        SelPNM.setText("");
        SelMN.setText("");
        JmlEritrosit.setText("");
        TestRivalta.setText("");
        TestNonne.setText("");
        TestPandy.setText("");
        Protein.setText("");
        Glukosa.setText("");
        Kesan.setText("");
        Saran.setText("");
        TCari.setText("");
        
        tglSimpan = "";
        jamSimpan = "";
        
        // Aktifkan kembali input tanggal & jam
        Tanggal.setEnabled(true);
        ChkJln.setSelected(true); // Default ke jam otomatis
        ChkJlnActionPerformed(null); // Update status enabled ComboBox jam
        
        KodePj.requestFocus();
    }

    public void setNoRm(String norwt, String posisi) {
        noorder = "";
        TNoRw.setText(norwt);
        TCari.setText(norwt); 
        this.status = posisi;

        // Bagian untuk mengisi Dokter P.J. otomatis:
        try {
            ps = koneksi.prepareStatement("select kd_dokterlab from set_pjlab"); // Ambil kode dokter PJ Lab
            try {
                rs = ps.executeQuery();
                if (rs.next()) { // Gunakan if karena biasanya hanya ada 1 PJ Lab default
                    KodePj.setText(rs.getString("kd_dokterlab")); // Set Kode Dokter PJ
                    NmDokterPj.setText(dokter.tampil3(rs.getString("kd_dokterlab"))); // Set Nama Dokter PJ
                } else {
                    // Jika tidak ada setting default, kosongkan atau beri pesan
                    KodePj.setText(""); 
                    NmDokterPj.setText(""); 
                    // JOptionPane.showMessageDialog(null,"Setting Dokter PJ Laboratorium belum diatur!");
                }
            } catch (Exception e) {
                System.out.println("Notif PJ Lab: " + e);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error ambil PJ Lab: "+e);
        }
        // Akhir bagian Dokter P.J. otomatis

        isPasien(); 
        emptTeks(); 
        TNoRw.setText(norwt); 
        TCari.setText(norwt); 
        isPasien(); 
        
        // Pastikan Dokter PJ terisi lagi setelah emptTeks
        try {
            ps = koneksi.prepareStatement("select kd_dokterlab from set_pjlab");
            try {
                rs = ps.executeQuery();
                if (rs.next()) { 
                    KodePj.setText(rs.getString("kd_dokterlab")); 
                    NmDokterPj.setText(dokter.tampil3(rs.getString("kd_dokterlab"))); 
                }
            } catch (Exception e) { System.out.println("Notif PJ Lab (ulang): " + e); } 
            finally { /* ... (kode finally sama seperti di atas) ... */ }
        } catch (Exception e) { System.out.println("Error ambil PJ Lab (ulang): "+e); }
        
        TabRawat.setSelectedIndex(0);
        Spesimen.requestFocus();
    }

    public void isCek() {
        if (akses.getjml2() >= 1) {
            KdPtg.setText(akses.getkode());
            NmPtg.setText(petugas.tampil3(KdPtg.getText()));
        } else {
            KdPtg.setText("");
            NmPtg.setText("");
        }
        BtnSimpan.setEnabled(akses.getperiksa_lab());
        BtnGanti.setEnabled(akses.getperiksa_lab());
        BtnHapus.setEnabled(akses.getperiksa_lab());
        btnPetugas.setEnabled(akses.getubah_petugas_lab_pk());
        // Anda mungkin perlu menambahkan hak akses baru yang lebih spesifik untuk form ini
    }

    private void isPasien() {
        try {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis, pasien.nm_pasien, reg_periksa.kd_dokter, dokter.nm_dokter " +
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    KodePerujuk.setText(rs.getString("kd_dokter"));
                    NmPerujuk.setText(rs.getString("nm_dokter"));
                }
            } catch (Exception e) {
                System.out.println("Notif Pasien : " + e);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ChkJln.isSelected()) {
                    String nol_jam = "";
                    String nol_menit = "";
                    String nol_detik = "";
                    Date now = Calendar.getInstance().getTime();
                    int nilai_jam = now.getHours();
                    int nilai_menit = now.getMinutes();
                    int nilai_detik = now.getSeconds();
                    if (nilai_jam <= 9) nol_jam = "0";
                    if (nilai_menit <= 9) nol_menit = "0";
                    if (nilai_detik <= 9) nol_detik = "0";
                    String jam = nol_jam + Integer.toString(nilai_jam);
                    String menit = nol_menit + Integer.toString(nilai_menit);
                    String detik = nol_detik + Integer.toString(nilai_detik);
                    CmbJam.setSelectedItem(jam);
                    CmbMenit.setSelectedItem(menit);
                    CmbDetik.setSelectedItem(detik);
                }
            }
        };
        new Timer(1000, taskPerformer).start();
    }
    
    public void tampil(String norawat, String tanggal, String jam) {
        try {
            ps = koneksi.prepareStatement(
                "SELECT * FROM pemeriksaan_cairan_tubuh_pk WHERE no_rawat=? AND tgl_periksa=? AND jam=?");
            ps.setString(1, norawat);
            ps.setString(2, tanggal);
            ps.setString(3, jam);
            rs = ps.executeQuery();
            if(rs.next()){
                TNoRw.setText(rs.getString("no_rawat"));
                isPasien(); // Memuat data RM & Pasien
                Tanggal.setDate(rs.getDate("tgl_periksa"));
                CmbJam.setSelectedItem(rs.getString("jam").substring(0,2));
                CmbMenit.setSelectedItem(rs.getString("jam").substring(3,5));
                CmbDetik.setSelectedItem(rs.getString("jam").substring(6,8));
                KodePj.setText(rs.getString("kd_dokter_pj"));
                NmDokterPj.setText(dokter.tampil3(KodePj.getText()));
                KodePerujuk.setText(rs.getString("kd_dokter_perujuk"));
                NmPerujuk.setText(dokter.tampil3(KodePerujuk.getText()));
                KdPtg.setText(rs.getString("nip"));
                NmPtg.setText(petugas.tampil3(KdPtg.getText()));
                Spesimen.setText(rs.getString("spesimen"));
                Volume.setText(rs.getString("makro_volume"));
                Warna.setText(rs.getString("makro_warna"));
                Kejernihan.setText(rs.getString("makro_kejernihan"));
                BeratJenis.setText(rs.getString("makro_berat_jenis"));
                Ph.setText(rs.getString("makro_ph"));
                Bau.setText(rs.getString("makro_bau"));
                Bekuan.setText(rs.getString("makro_bekuan"));
                JmlLeukosit.setText(rs.getString("mikro_jml_leukosit"));
                SelPNM.setText(rs.getString("mikro_sel_pnm"));
                SelMN.setText(rs.getString("mikro_sel_mn"));
                JmlEritrosit.setText(rs.getString("mikro_jml_eritrosit"));
                TestRivalta.setText(rs.getString("kimia_rivalta"));
                TestNonne.setText(rs.getString("kimia_nonne"));
                TestPandy.setText(rs.getString("kimia_pandy"));
                Protein.setText(rs.getString("kimia_protein"));
                Glukosa.setText(rs.getString("kimia_glukosa"));
                Kesan.setText(rs.getString("kesan"));
                Saran.setText(rs.getString("saran"));
                
                tglSimpan = rs.getString("tgl_periksa");
                jamSimpan = rs.getString("jam");
            }
        } catch (Exception e) {
            System.out.println("Notif Tampil Detail: " + e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Variables declaration - do not modify                     
    private widget.TextBox Bau;
    private widget.TextBox Bekuan;
    private widget.TextBox BeratJenis;
    private widget.Button BtnBaru;
    private widget.Button BtnCari;
    private widget.Button BtnGanti;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnCetak;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Glukosa;
    private widget.TextBox JmlEritrosit;
    private widget.TextBox JmlLeukosit;
    private widget.TextBox KdPtg;
    private widget.TextBox Kejernihan;
    private widget.TextBox Kesan;
    private widget.TextBox KodePerujuk;
    private widget.TextBox KodePj;
    private widget.Label LCount;
    private widget.TextBox NmDokterPj;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NmPtg;
    private widget.PanelBiasa PanelContent;
    private widget.PanelBiasa PanelKimiawi;
    private widget.PanelBiasa PanelMakroskopis;
    private widget.PanelBiasa PanelMikroskopis;
    private widget.TextBox Ph;
    private widget.TextBox Protein;
    private widget.TextBox Saran;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollData;
    private widget.TextBox SelMN;
    private widget.TextBox SelPNM;
    private widget.TextBox Spesimen;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JPanel TabData;
    private javax.swing.JPanel TabInput;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TestNonne;
    private widget.TextBox TestPandy;
    private widget.TextBox TestRivalta;
    private widget.TextBox Volume;
    private widget.TextBox Warna;
    private widget.Button btnDokter;
    private widget.Button btnDokterPj;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel7_Data;
    private widget.Label jLabel9;
    private widget.Label jLabelBau;
    private widget.Label jLabelBekuan;
    private widget.Label jLabelBeratJenis;
    private widget.Label jLabelGlukosa;
    private widget.Label jLabelJmlEritrosit;
    private widget.Label jLabelJmlLeukosit;
    private widget.Label jLabelKejernihan;
    private widget.Label jLabelKesan;
    private widget.Label jLabelPh;
    private widget.Label jLabelProtein;
    private widget.Label jLabelSaran;
    private widget.Label jLabelSelMN;
    private widget.Label jLabelSelPNM;
    private widget.Label jLabelSpesimen;
    private widget.Label jLabelTestNonne;
    private widget.Label jLabelTestPandy;
    private widget.Label jLabelTestRivalta;
    private widget.Label jLabelVolume;
    private widget.Label jLabelWarna;
    private widget.PanelBiasa panelInput;
    private widget.panelisi panelCariData;
    private widget.panelisi panelTombol;
    private widget.Table tbDataPenilaian;
    // End of variables declaration      
    
    private void BtnCetakActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(tabModeData.getRowCount()==0){ // Cek jika tabel kosong
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDataPenilaian.getSelectedRow()== -1){ // Cek jika tidak ada baris dipilih
             JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data cairan tubuh pada tabel terlebih dahulu..!");
             tbDataPenilaian.requestFocus();
        }else{ // Jika ada baris dipilih
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            // Ambil data kunci dari baris tabel yang dipilih
            String noRawat = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 0).toString();
            String tglPeriksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 5).toString();
            String jamPeriksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 6).toString();
            
            // Ambil data lain dari tabel (opsional, bisa juga query ulang jika perlu data lengkap)
            String noRM = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 1).toString();
            String namaPasien = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 2).toString();
            String jk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 3).toString();
            // Ambil umur dari database atau parameter jika sudah ada
            String umurPasien = Sequel.cariIsi("select p.umur from pasien p inner join reg_periksa rp on p.no_rkm_medis=rp.no_rkm_medis where rp.no_rawat=?", noRawat);
            String alamatPasien = Sequel.cariIsi(
                    "select concat(p.alamat,', ',kl.nm_kel,', ',kc.nm_kec,', ',kb.nm_kab) "+
                    "from pasien p inner join reg_periksa rp on p.no_rkm_medis=rp.no_rkm_medis "+
                    "inner join kelurahan kl on p.kd_kel=kl.kd_kel "+
                    "inner join kecamatan kc on p.kd_kec=kc.kd_kec "+
                    "inner join kabupaten kb on p.kd_kab=kb.kd_kab "+
                    "where rp.no_rawat=?", noRawat);
            String namaDokterPJ = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 27).toString();
            String namaPetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 29).toString();
            String namaDokterPerujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 31).toString();
            // Ambil data kamar/poli
            String statusRawat = Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?", noRawat);
            String kamarLabel = "";
            String namaKamarPoli = "";
            if (statusRawat.equals("Ranap")) {
                kamarLabel = "Ruang/Poli"; // Atau "Kamar" sesuai referensi
                namaKamarPoli = Sequel.cariIsi("select b.nm_bangsal from kamar_inap ki inner join kamar k on ki.kd_kamar=k.kd_kamar inner join bangsal b on k.kd_bangsal=b.kd_bangsal where ki.no_rawat=? order by ki.tgl_masuk desc limit 1", noRawat);
            } else { // Ralan
                kamarLabel = "Ruang/Poli"; // Atau "Poli"
                namaKamarPoli = Sequel.cariIsi("select pl.nm_poli from reg_periksa rp inner join poliklinik pl on rp.kd_poli=pl.kd_poli where rp.no_rawat=?", noRawat);
            }


            // Siapkan parameter untuk report
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            
            param.put("noperiksa", noRawat); // Menggunakan no_rawat sebagai noperiksa di report
            param.put("norm", noRM);
            param.put("namapasien", namaPasien);
            param.put("jkel", jk);
            param.put("umur", umurPasien);
            param.put("pengirim", namaDokterPerujuk); // Nama Dokter Pengirim/Perujuk
            param.put("petugas", namaPetugas);       // Nama Petugas Pelaksana
            param.put("tanggal", tglPeriksa);       // Tgl Pemeriksaan dari tabel
            param.put("jam", jamPeriksa);           // Jam Pemeriksaan dari tabel
            param.put("penjab", namaDokterPJ);       // Nama Dokter PJ
            param.put("alamat", alamatPasien);
            param.put("kamar", kamarLabel);         // Label Kamar/Poli
            param.put("namakamar", namaKamarPoli);  // Nama Kamar/Poli
            
            // Panggil report
            Valid.MyReport("rptPeriksaCairanTubuh.jasper", "report", "::[ Hasil Pemeriksaan Cairan Tubuh ]::", param);
            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}