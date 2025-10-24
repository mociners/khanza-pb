/*
 * DlgPeriksaMDT.java
 *
 * Created on October 23, 2025
 */

package simrskhanza;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout; // Import GridLayout
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
import javax.swing.BorderFactory; // Import BorderFactory
import javax.swing.JOptionPane;
import javax.swing.JPanel; // Import JPanel
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.TitledBorder; // Import TitledBorder
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
// import keuangan.Jurnal; // Jurnal tidak dipakai di versi ini

/**
 *
 * @author Agni
 */
public class DlgPeriksaMDT extends javax.swing.JDialog {
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private String status="", noorder="", pilihan="";
    private String tglSimpan="", jamSimpan="";
    private DefaultTableModel tabModeData;
    private int i = 0;
    
    // Variabel untuk status rawat (diperlukan untuk cetak)
    private String kamar,namakamar,kelas; 
    

    /** Creates new form DlgPeriksaMDT */
    public DlgPeriksaMDT(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Model Tabel Data Penilaian
        tabModeData=new DefaultTableModel(null, new Object[]{
                "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Tgl.Lahir", "Tgl.Periksa", "Jam",
                "Hb", "WBC", "PLT", "HCT", "Lym/Mxd/Gra", "MCV", "MCH", "MCHC", "RDW-SD", "RDW-CV", "RBC",
                "Kesan Eritrosit", "Kesan Leukosit", "Kesan Trombosit", "Kesan", "Saran",
                "Kode Dokter PJ", "Nama Dokter PJ", "NIP Petugas", "Nama Petugas", "Kode Dokter Perujuk", "Nama Dokter Perujuk"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataPenilaian.setModel(tabModeData);
        tbDataPenilaian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataPenilaian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Atur lebar kolom tabel data
        for (i = 0; i < 29; i++) { // Sesuaikan jumlah kolom (29)
            TableColumn column = tbDataPenilaian.getColumnModel().getColumn(i);
            if(i==0){ column.setPreferredWidth(105); } // No.Rawat
            else if(i==1){ column.setPreferredWidth(65); }  // No.RM
            else if(i==2){ column.setPreferredWidth(150); } // Nama Pasien
            else if(i==3){ column.setPreferredWidth(30); }  // J.K.
            else if(i==4){ column.setPreferredWidth(70); }  // Tgl.Lahir
            else if(i==5){ column.setPreferredWidth(70); }  // Tgl.Periksa
            else if(i==6){ column.setPreferredWidth(60); }  // Jam
            else if(i>=7 && i<=17){ column.setPreferredWidth(65); } // Kolom Hematologi
            else if(i==18){ column.setPreferredWidth(200); } // Kesan Eritrosit
            else if(i==19){ column.setPreferredWidth(200); } // Kesan Leukosit
            else if(i==20){ column.setPreferredWidth(200); } // Kesan Trombosit
            else if(i==21){ column.setPreferredWidth(200); } // Kesan
            else if(i==22){ column.setPreferredWidth(200); } // Saran
            else if(i==24){ column.setPreferredWidth(150); } // Nama Dokter PJ
            else if(i==26){ column.setPreferredWidth(150); } // Nama Petugas
            else if(i==28){ column.setPreferredWidth(150); } // Nama Dokter Perujuk
            else { column.setMinWidth(0); column.setMaxWidth(0); } // Sembunyikan kolom kode
        }
        tbDataPenilaian.setDefaultRenderer(Object.class, new WarnaTable());

        // Batasan Input
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KodePj.setDocument(new batasInput((byte)20).getKata(KodePj));
        KodePerujuk.setDocument(new batasInput((byte)20).getKata(KodePerujuk));
        KdPtg.setDocument(new batasInput((byte)20).getKata(KdPtg));
        THb.setDocument(new batasInput((byte)10).getKata(THb));
        TWbc.setDocument(new batasInput((byte)10).getKata(TWbc));
        TPlt.setDocument(new batasInput((byte)10).getKata(TPlt));
        THct.setDocument(new batasInput((byte)10).getKata(THct));
        TLymMxdGra.setDocument(new batasInput((byte)20).getKata(TLymMxdGra));
        TMcv.setDocument(new batasInput((byte)10).getKata(TMcv));
        TMch.setDocument(new batasInput((byte)10).getKata(TMch));
        TMchc.setDocument(new batasInput((byte)10).getKata(TMchc));
        TRdwSd.setDocument(new batasInput((byte)10).getKata(TRdwSd));
        TRdwCv.setDocument(new batasInput((byte)10).getKata(TRdwCv));
        TRbc.setDocument(new batasInput((byte)10).getKata(TRbc));
        TKesanEritrosit.setDocument(new batasInput(200).getKata(TKesanEritrosit)); // Text Area
        TKesanLeukosit.setDocument(new batasInput(200).getKata(TKesanLeukosit)); // Text Area
        TKesanTrombosit.setDocument(new batasInput(200).getKata(TKesanTrombosit)); // Text Area
        TKesan.setDocument(new batasInput(200).getKata(TKesan)); // Text Area
        TSaran.setDocument(new batasInput(200).getKata(TSaran)); // Text Area
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
                        // TabRawat.setSelectedIndex(0); // Tidak pindah tab otomatis
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
                if(akses.getform().equals("DlgPeriksaMDT")){ // Sesuaikan nama form
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
                if(akses.getform().equals("DlgPeriksaMDT")){ // Sesuaikan nama form
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
        
        // Hapus listener untuk Jurnal dan Set Tarif
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
        jLabelHb = new widget.Label();
        THb = new widget.TextBox();
        jLabelWbc = new widget.Label();
        TWbc = new widget.TextBox();
        jLabelPlt = new widget.Label();
        TPlt = new widget.TextBox();
        jLabelHct = new widget.Label();
        THct = new widget.TextBox();
        jLabelLym = new widget.Label();
        TLymMxdGra = new widget.TextBox();
        jLabelMcv = new widget.Label();
        TMcv = new widget.TextBox();
        jLabelMch = new widget.Label();
        TMch = new widget.TextBox();
        jLabelMchc = new widget.Label();
        TMchc = new widget.TextBox();
        jLabelRdwSd = new widget.Label();
        TRdwSd = new widget.TextBox();
        jLabelRdwCv = new widget.Label();
        TRdwCv = new widget.TextBox();
        jLabelRbc = new widget.Label();
        TRbc = new widget.TextBox();
        jLabelKesanEritrosit = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKesanEritrosit = new widget.TextArea();
        jLabelKesanLeukosit = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TKesanLeukosit = new widget.TextArea();
        jLabelKesanTrombosit = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TKesanTrombosit = new widget.TextArea();
        jLabelKesan = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        TKesan = new widget.TextArea();
        jLabelSaran = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        TSaran = new widget.TextArea();
        jLabel1 = new widget.Label();
        jLabel2 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
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
        BtnCetak = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemeriksaan Morfologi Darah Tepi (MDT) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
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
        panelInput.setPreferredSize(new java.awt.Dimension(800, 100));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-10-2025" }));
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
        PanelContent.setPreferredSize(new java.awt.Dimension(800, 430));
        PanelContent.setLayout(null);

        jLabelHb.setText("Hb :");
        jLabelHb.setName("jLabelHb"); // NOI18N
        PanelContent.add(jLabelHb);
        jLabelHb.setBounds(10, 10, 80, 23);

        THb.setName("THb"); // NOI18N
        THb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THbKeyPressed(evt);
            }
        });
        PanelContent.add(THb);
        THb.setBounds(95, 10, 70, 23);

        jLabelWbc.setText("WBC :");
        jLabelWbc.setName("jLabelWbc"); // NOI18N
        PanelContent.add(jLabelWbc);
        jLabelWbc.setBounds(10, 40, 80, 23);

        TWbc.setName("TWbc"); // NOI18N
        TWbc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TWbcKeyPressed(evt);
            }
        });
        PanelContent.add(TWbc);
        TWbc.setBounds(95, 40, 70, 23);

        jLabelPlt.setText("PLT :");
        jLabelPlt.setName("jLabelPlt"); // NOI18N
        PanelContent.add(jLabelPlt);
        jLabelPlt.setBounds(10, 70, 80, 23);

        TPlt.setName("TPlt"); // NOI18N
        TPlt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPltKeyPressed(evt);
            }
        });
        PanelContent.add(TPlt);
        TPlt.setBounds(95, 70, 70, 23);

        jLabelHct.setText("HCT :");
        jLabelHct.setName("jLabelHct"); // NOI18N
        PanelContent.add(jLabelHct);
        jLabelHct.setBounds(10, 100, 80, 23);

        THct.setName("THct"); // NOI18N
        THct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THctKeyPressed(evt);
            }
        });
        PanelContent.add(THct);
        THct.setBounds(95, 100, 70, 23);

        jLabelLym.setText("Lym/Mxd/Gra :");
        jLabelLym.setName("jLabelLym"); // NOI18N
        PanelContent.add(jLabelLym);
        jLabelLym.setBounds(10, 130, 80, 23);

        TLymMxdGra.setName("TLymMxdGra"); // NOI18N
        TLymMxdGra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLymMxdGraKeyPressed(evt);
            }
        });
        PanelContent.add(TLymMxdGra);
        TLymMxdGra.setBounds(95, 130, 100, 23);

        jLabelMcv.setText("MCV :");
        jLabelMcv.setName("jLabelMcv"); // NOI18N
        PanelContent.add(jLabelMcv);
        jLabelMcv.setBounds(250, 10, 50, 23);

        TMcv.setName("TMcv"); // NOI18N
        TMcv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMcvKeyPressed(evt);
            }
        });
        PanelContent.add(TMcv);
        TMcv.setBounds(305, 10, 70, 23);

        jLabelMch.setText("MCH :");
        jLabelMch.setName("jLabelMch"); // NOI18N
        PanelContent.add(jLabelMch);
        jLabelMch.setBounds(250, 40, 50, 23);

        TMch.setName("TMch"); // NOI18N
        TMch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMchKeyPressed(evt);
            }
        });
        PanelContent.add(TMch);
        TMch.setBounds(305, 40, 70, 23);

        jLabelMchc.setText("MCHC :");
        jLabelMchc.setName("jLabelMchc"); // NOI18N
        PanelContent.add(jLabelMchc);
        jLabelMchc.setBounds(250, 70, 50, 23);

        TMchc.setName("TMchc"); // NOI18N
        TMchc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TMchcKeyPressed(evt);
            }
        });
        PanelContent.add(TMchc);
        TMchc.setBounds(305, 70, 70, 23);

        jLabelRdwSd.setText("RDW-SD :");
        jLabelRdwSd.setName("jLabelRdwSd"); // NOI18N
        PanelContent.add(jLabelRdwSd);
        jLabelRdwSd.setBounds(250, 100, 50, 23);

        TRdwSd.setName("TRdwSd"); // NOI18N
        TRdwSd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRdwSdKeyPressed(evt);
            }
        });
        PanelContent.add(TRdwSd);
        TRdwSd.setBounds(305, 100, 70, 23);

        jLabelRdwCv.setText("RDW-CV :");
        jLabelRdwCv.setName("jLabelRdwCv"); // NOI18N
        PanelContent.add(jLabelRdwCv);
        jLabelRdwCv.setBounds(250, 130, 50, 23);

        TRdwCv.setName("TRdwCv"); // NOI18N
        TRdwCv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRdwCvKeyPressed(evt);
            }
        });
        PanelContent.add(TRdwCv);
        TRdwCv.setBounds(305, 130, 70, 23);

        jLabelRbc.setText("RBC :");
        jLabelRbc.setName("jLabelRbc"); // NOI18N
        PanelContent.add(jLabelRbc);
        jLabelRbc.setBounds(250, 160, 50, 23);

        TRbc.setName("TRbc"); // NOI18N
        TRbc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRbcKeyPressed(evt);
            }
        });
        PanelContent.add(TRbc);
        TRbc.setBounds(305, 160, 70, 23);

        jLabelKesanEritrosit.setText("Kesan Eritrosit :");
        jLabelKesanEritrosit.setName("jLabelKesanEritrosit"); // NOI18N
        PanelContent.add(jLabelKesanEritrosit);
        jLabelKesanEritrosit.setBounds(10, 190, 100, 23);

        scrollPane1.setName("scrollPane1"); // NOI18N

        TKesanEritrosit.setColumns(20);
        TKesanEritrosit.setRows(3);
        TKesanEritrosit.setName("TKesanEritrosit"); // NOI18N
        TKesanEritrosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanEritrositKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKesanEritrosit);

        PanelContent.add(scrollPane1);
        scrollPane1.setBounds(115, 190, 700, 55);

        jLabelKesanLeukosit.setText("Kesan Leukosit :");
        jLabelKesanLeukosit.setName("jLabelKesanLeukosit"); // NOI18N
        PanelContent.add(jLabelKesanLeukosit);
        jLabelKesanLeukosit.setBounds(10, 250, 100, 23);

        scrollPane2.setName("scrollPane2"); // NOI18N

        TKesanLeukosit.setColumns(20);
        TKesanLeukosit.setRows(2);
        TKesanLeukosit.setName("TKesanLeukosit"); // NOI18N
        TKesanLeukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanLeukositKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TKesanLeukosit);

        PanelContent.add(scrollPane2);
        scrollPane2.setBounds(115, 250, 700, 40);

        jLabelKesanTrombosit.setText("Kesan Trombosit:");
        jLabelKesanTrombosit.setName("jLabelKesanTrombosit"); // NOI18N
        PanelContent.add(jLabelKesanTrombosit);
        jLabelKesanTrombosit.setBounds(10, 295, 100, 23);

        scrollPane3.setName("scrollPane3"); // NOI18N

        TKesanTrombosit.setColumns(20);
        TKesanTrombosit.setRows(2);
        TKesanTrombosit.setName("TKesanTrombosit"); // NOI18N
        TKesanTrombosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanTrombositKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TKesanTrombosit);

        PanelContent.add(scrollPane3);
        scrollPane3.setBounds(115, 295, 700, 40);

        jLabelKesan.setText("Kesan :");
        jLabelKesan.setName("jLabelKesan"); // NOI18N
        PanelContent.add(jLabelKesan);
        jLabelKesan.setBounds(10, 340, 100, 23);

        scrollPane4.setName("scrollPane4"); // NOI18N

        TKesan.setColumns(20);
        TKesan.setRows(2);
        TKesan.setName("TKesan"); // NOI18N
        TKesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TKesan);

        PanelContent.add(scrollPane4);
        scrollPane4.setBounds(115, 340, 700, 40);

        jLabelSaran.setText("Saran :");
        jLabelSaran.setName("jLabelSaran"); // NOI18N
        PanelContent.add(jLabelSaran);
        jLabelSaran.setBounds(10, 385, 100, 23);

        scrollPane5.setName("scrollPane5"); // NOI18N

        TSaran.setColumns(20);
        TSaran.setRows(2);
        TSaran.setName("TSaran"); // NOI18N
        TSaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSaranKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TSaran);

        PanelContent.add(scrollPane5);
        scrollPane5.setBounds(115, 385, 700, 40);

        jLabel1.setText("gr/%");
        jLabel1.setName("jLabel1"); // NOI18N
        PanelContent.add(jLabel1);
        jLabel1.setBounds(170, 10, 30, 23);

        jLabel2.setText("/mmk");
        jLabel2.setName("jLabel2"); // NOI18N
        PanelContent.add(jLabel2);
        jLabel2.setBounds(170, 40, 35, 23);

        jLabel4.setText("/mmk");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelContent.add(jLabel4);
        jLabel4.setBounds(170, 70, 35, 23);

        jLabel5.setText("%");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelContent.add(jLabel5);
        jLabel5.setBounds(170, 100, 30, 23);

        jLabel8.setText("%");
        jLabel8.setName("jLabel8"); // NOI18N
        PanelContent.add(jLabel8);
        jLabel8.setBounds(200, 130, 30, 23);

        jLabel10.setText("fL");
        jLabel10.setName("jLabel10"); // NOI18N
        PanelContent.add(jLabel10);
        jLabel10.setBounds(380, 10, 30, 23);

        jLabel11.setText("pg");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelContent.add(jLabel11);
        jLabel11.setBounds(380, 40, 30, 23);

        jLabel13.setText("g/Dl");
        jLabel13.setName("jLabel13"); // NOI18N
        PanelContent.add(jLabel13);
        jLabel13.setBounds(380, 70, 30, 23);

        jLabel14.setText("fL");
        jLabel14.setName("jLabel14"); // NOI18N
        PanelContent.add(jLabel14);
        jLabel14.setBounds(380, 100, 30, 23);

        jLabel17.setText("%");
        jLabel17.setName("jLabel17"); // NOI18N
        PanelContent.add(jLabel17);
        jLabel17.setBounds(380, 130, 30, 23);

        jLabel18.setText(" ");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelContent.add(jLabel18);
        jLabel18.setBounds(380, 160, 30, 23);

        Scroll.setViewportView(PanelContent);

        TabInput.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pemeriksaan", TabInput);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-10-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-10-2025" }));
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

        TabRawat.addTab("Data Pemeriksaan", TabData);

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
        panelTombol.add(BtnCetak);

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
        setSize(880, 600);
        setLocationRelativeTo(null);
    }// </editor-fold>                        

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
            try {
                // Panggil 'menyimpan' secara langsung
                Sequel.menyimpan("pemeriksaan_mdt", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 22, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem()+""), CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    KodePj.getText(), KodePerujuk.getText(), KdPtg.getText(), THb.getText(), TWbc.getText(), TPlt.getText(), THct.getText(),
                    TLymMxdGra.getText(), TMcv.getText(), TMch.getText(), TMchc.getText(), TRdwSd.getText(), TRdwCv.getText(), TRbc.getText(),
                    TKesanEritrosit.getText(), TKesanLeukosit.getText(), TKesanTrombosit.getText(), TKesan.getText(), TSaran.getText()
                });
                
                JOptionPane.showMessageDialog(null,"Proses simpan selesai...");
                emptTeks();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menyimpan: " + e.getMessage());
            }
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
                try {
                    Sequel.queryu2("UPDATE pemeriksaan_mdt SET tgl_periksa=?, jam=?, kd_dokter_pj=?, kd_dokter_perujuk=?, nip=?, " +
                        "hb=?, wbc=?, plt=?, hct=?, lym_mxd_gra=?, mcv=?, mch=?, mchc=?, rdw_sd=?, rdw_cv=?, rbc=?, " +
                        "kesan_eritrosit=?, kesan_leukosit=?, kesan_trombosit=?, kesan=?, saran=? "+
                        "WHERE no_rawat=? AND tgl_periksa=? AND jam=?", 24, new String[]{ 
                        Valid.SetTgl(Tanggal.getSelectedItem()+""), CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                        KodePj.getText(), KodePerujuk.getText(), KdPtg.getText(), THb.getText(), TWbc.getText(), TPlt.getText(), THct.getText(),
                        TLymMxdGra.getText(), TMcv.getText(), TMch.getText(), TMchc.getText(), TRdwSd.getText(), TRdwCv.getText(), TRbc.getText(),
                        TKesanEritrosit.getText(), TKesanLeukosit.getText(), TKesanTrombosit.getText(), TKesan.getText(), TSaran.getText(),
                        TNoRw.getText(), tglSimpan, jamSimpan
                    });
                    JOptionPane.showMessageDialog(null,"Proses ganti selesai...");
                    emptTeks();
                } catch (Exception e) {
                     JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat mengganti: " + e.getMessage());
                }
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
                try {
                    Sequel.queryu2("DELETE FROM pemeriksaan_mdt WHERE no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        TNoRw.getText(), tglSimpan, jamSimpan
                    });
                    JOptionPane.showMessageDialog(null,"Proses hapus selesai...");
                    emptTeks();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghapus: " + e.getMessage());
                }
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
        akses.setform("DlgPeriksaMDT"); // Sesuaikan nama form
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }                                           

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {                                          
        pilihan="perujuk";
        akses.setform("DlgPeriksaMDT"); // Sesuaikan nama form
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }                                         

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {                                           
        akses.setform("DlgPeriksaMDT"); // Sesuaikan nama form
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

    private void THbKeyPressed(java.awt.event.KeyEvent evt) {                               
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TWbc.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Tanggal.requestFocusInWindow(); // Pindah ke Tanggal jika PageUp
        }
    }                              

    private void TWbcKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TPlt.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            THb.requestFocusInWindow(); // Pindah ke Hb jika PageUp
        }
    }                               

    private void TPltKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            THct.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TWbc.requestFocusInWindow(); // Pindah ke WBC jika PageUp
        }
    }                               

    private void THctKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TLymMxdGra.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TPlt.requestFocusInWindow(); // Pindah ke PLT jika PageUp
        }
    }                               

    private void TLymMxdGraKeyPressed(java.awt.event.KeyEvent evt) {                                      
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TMcv.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            THct.requestFocusInWindow(); // Pindah ke HCT jika PageUp
        }
    }                                     

    private void TMcvKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TMch.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TLymMxdGra.requestFocusInWindow(); // Pindah ke Lym/Mxd/Gra jika PageUp
        }
    }                               

    private void TMchKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TMchc.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TMcv.requestFocusInWindow(); // Pindah ke MCV jika PageUp
        }
    }                               

    private void TMchcKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TRdwSd.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TMch.requestFocusInWindow(); // Pindah ke MCH jika PageUp
        }
    }                                

    private void TRdwSdKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TRdwCv.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TMchc.requestFocusInWindow(); // Pindah ke MCHC jika PageUp
        }
    }                                 

    private void TRdwCvKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TRbc.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TRdwSd.requestFocusInWindow(); // Pindah ke RDW-SD jika PageUp
        }
    }                                 

    private void TRbcKeyPressed(java.awt.event.KeyEvent evt) {                                
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TKesanEritrosit.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TRdwCv.requestFocusInWindow(); // Pindah ke RDW-CV jika PageUp
        }
    }                               

    private void TKesanEritrositKeyPressed(java.awt.event.KeyEvent evt) {                                           
        if (evt.getKeyCode() == KeyEvent.VK_TAB) { // Pindah ke field berikutnya jika Tab
           TKesanLeukosit.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TRbc.requestFocusInWindow(); // Pindah ke RBC jika PageUp
        }
    }                                          

    private void TKesanLeukositKeyPressed(java.awt.event.KeyEvent evt) {                                          
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
           TKesanTrombosit.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TKesanEritrosit.requestFocusInWindow(); // Pindah ke Kesan Eritrosit jika PageUp
        }
    }                                         

    private void TKesanTrombositKeyPressed(java.awt.event.KeyEvent evt) {                                           
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
           TKesan.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TKesanLeukosit.requestFocusInWindow(); // Pindah ke Kesan Leukosit jika PageUp
        }
    }                                          

    private void TKesanKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
           TSaran.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TKesanTrombosit.requestFocusInWindow(); // Pindah ke Kesan Trombosit jika PageUp
        }
    }                                 

    private void TSaranKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocusInWindow();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TKesan.requestFocusInWindow(); // Pindah ke Kesan jika PageUp
        }
    }                                 
    
    private void BtnCetakActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(tabModeData.getRowCount()==0){ // Cek jika tabel kosong
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDataPenilaian.getSelectedRow()== -1){ // Cek jika tidak ada baris dipilih
             JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pemeriksaan pada tabel terlebih dahulu..!");
             tbDataPenilaian.requestFocus();
        }else{ // Jika ada baris dipilih
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            // Ambil data kunci dari baris tabel yang dipilih
            String noRawat = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 0).toString();
            String tglPeriksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 5).toString();
            String jamPeriksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 6).toString();
            
            // Ambil data lain dari tabel
            String noRM = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 1).toString();
            String namaPasien = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 2).toString();
            String jk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 3).toString();
            
            // Ambil data tambahan dari database
            String umurPasien = Sequel.cariIsi("select p.umur from pasien p inner join reg_periksa rp on p.no_rkm_medis=rp.no_rkm_medis where rp.no_rawat=?", noRawat);
            String alamatPasien = Sequel.cariIsi(
                    "select concat(p.alamat,', ',kl.nm_kel,', ',kc.nm_kec,', ',kb.nm_kab) "+
                    "from pasien p inner join reg_periksa rp on p.no_rkm_medis=rp.no_rkm_medis "+
                    "inner join kelurahan kl on p.kd_kel=kl.kd_kel "+
                    "inner join kecamatan kc on p.kd_kec=kc.kd_kec "+
                    "inner join kabupaten kb on p.kd_kab=kb.kd_kab "+
                    "where rp.no_rawat=?", noRawat);
            
            String namaDokterPJ = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 24).toString();
            String namaPetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 26).toString();
            String namaDokterPerujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(), 28).toString();
            
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
            
            // Parameter khusus untuk report ini (sesuai query JRXML)
            param.put("noperiksa", noRawat); // Menggunakan no_rawat sebagai noperiksa di report
            param.put("norm", noRM);
            param.put("namapasien", namaPasien);
            param.put("jkel", jk);
            param.put("umur", umurPasien);
            param.put("pengirim", namaDokterPerujuk); // Nama Dokter Pengirim/Perujuk
            param.put("petugas", namaPetugas);       // Nama Petugas Pelaksana
            param.put("tanggal", tglPeriksa);       // Tgl Pemeriksaan dari tabel
            param.put("jam", jamPeriksa);           // Jam Pemeriksaan dari tabel
            param.put("penjab", namaDokterPJ);      // Nama Dokter PJ
            param.put("alamat", alamatPasien);
            param.put("kamar", kamarLabel);         // Label Kamar/Poli
            param.put("namakamar", namaKamarPoli);  // Nama Kamar/Poli
            
            // Panggil report
            // Pastikan file rptPemeriksaanMDT.jrxml sudah dicompile menjadi rptPemeriksaanMDT.jasper
            Valid.MyReport("rptPemeriksaanMDT.jasper", "report", "::[ Hasil Pemeriksaan Morfologi Darah Tepi ]::", param);
            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }                                        

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgPeriksaMDT dialog = new DlgPeriksaMDT(new javax.swing.JFrame(), true);
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
            String sql="select pc.no_rawat, p.no_rkm_medis, p.nm_pasien, p.jk, p.tgl_lahir, pc.tgl_periksa, pc.jam, "+
                   "pc.hb, pc.wbc, pc.plt, pc.hct, pc.lym_mxd_gra, pc.mcv, pc.mch, pc.mchc, pc.rdw_sd, pc.rdw_cv, pc.rbc, "+ 
                   "pc.kesan_eritrosit, pc.kesan_leukosit, pc.kesan_trombosit, pc.kesan, pc.saran, "+
                   "pc.kd_dokter_pj, d1.nm_dokter as dokter_pj, pc.nip, pt.nama as petugas, pc.kd_dokter_perujuk, d2.nm_dokter as dokter_perujuk "+
                   "from pemeriksaan_mdt pc inner join reg_periksa rp on pc.no_rawat=rp.no_rawat "+ 
                   "inner join pasien p on rp.no_rkm_medis=p.no_rkm_medis "+
                   "inner join dokter d1 on pc.kd_dokter_pj=d1.kd_dokter "+
                   "inner join petugas pt on pc.nip=pt.nip "+
                   "inner join dokter d2 on pc.kd_dokter_perujuk=d2.kd_dokter "+
                   "where pc.tgl_periksa between ? and ? ";

            if(!TCari.getText().trim().equals("")){
                if(TCari.getText().trim().equals(TNoRw.getText().trim())){
                     sql=sql+"and pc.no_rawat = ? ";
                } else {
                     sql=sql+"and (pc.no_rawat like ? or p.no_rkm_medis like ? or p.nm_pasien like ? or pc.kesan like ?) "; 
                }
            }
            sql=sql+"order by pc.tgl_periksa, pc.jam";

            ps=koneksi.prepareStatement(sql);
            try {
                int parameterIndex = 1;
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(parameterIndex++, Valid.SetTgl(DTPCari2.getSelectedItem()+""));

                if(!TCari.getText().trim().equals("")){
                    if(TCari.getText().trim().equals(TNoRw.getText().trim())){
                         ps.setString(parameterIndex++, TNoRw.getText().trim());
                    } else {
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%");
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%");
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%");
                        ps.setString(parameterIndex++, "%"+TCari.getText().trim()+"%"); // Keyword untuk Kesan
                    }
                }

                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeData.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),
                        rs.getString("tgl_lahir"),rs.getString("tgl_periksa"),rs.getString("jam"),
                        rs.getString("hb"), rs.getString("wbc"), rs.getString("plt"), rs.getString("hct"), rs.getString("lym_mxd_gra"),
                        rs.getString("mcv"), rs.getString("mch"), rs.getString("mchc"), rs.getString("rdw_sd"), rs.getString("rdw_cv"), rs.getString("rbc"),
                        rs.getString("kesan_eritrosit"), rs.getString("kesan_leukosit"), rs.getString("kesan_trombosit"),
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

    private void getData(){
        if(tbDataPenilaian.getSelectedRow()!= -1){
            String norawat = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),0).toString();
            String norm = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),1).toString();
            String nampasien = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),2).toString();
            String tglperiksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),5).toString();
            String jamperiksa = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),6).toString();
            String hb = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),7).toString();
            String wbc = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),8).toString();
            String plt = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),9).toString();
            String hct = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),10).toString();
            String lym = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),11).toString();
            String mcv = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),12).toString();
            String mch = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),13).toString();
            String mchc = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),14).toString();
            String rdw_sd = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),15).toString();
            String rdw_cv = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),16).toString();
            String rbc = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),17).toString();
            String kesan_eritrosit = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),18).toString();
            String kesan_leukosit = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),19).toString();
            String kesan_trombosit = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),20).toString();
            String kesan = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),21).toString();
            String saran = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),22).toString();
            String kddokterpj = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),23).toString();
            String nmdokterpj = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),24).toString();
            String nippetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),25).toString();
            String nmpetugas = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),26).toString();
            String kddokterperujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),27).toString();
            String nmdokterperujuk = tbDataPenilaian.getValueAt(tbDataPenilaian.getSelectedRow(),28).toString();

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
            THb.setText(hb);
            TWbc.setText(wbc);
            TPlt.setText(plt);
            THct.setText(hct);
            TLymMxdGra.setText(lym);
            TMcv.setText(mcv);
            TMch.setText(mch);
            TMchc.setText(mchc);
            TRdwSd.setText(rdw_sd);
            TRdwCv.setText(rdw_cv);
            TRbc.setText(rbc);
            TKesanEritrosit.setText(kesan_eritrosit);
            TKesanLeukosit.setText(kesan_leukosit);
            TKesanTrombosit.setText(kesan_trombosit);
            TKesan.setText(kesan);
            TSaran.setText(saran);
            TCari.setText(norawat);

            tglSimpan = tglperiksa;
            jamSimpan = jamperiksa;
            
            Tanggal.setEnabled(false);
            ChkJln.setSelected(false);
            ChkJlnActionPerformed(null);
        }
    }
    
    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {                                      
        if(TabRawat.getSelectedIndex()==1){ // Jika tab data yang dipilih
            tampilData();
        }
    }

    public void emptTeks() {
        KodePerujuk.setText(""); NmPerujuk.setText("");
        KodePj.setText(""); NmDokterPj.setText("");
        KdPtg.setText(""); NmPtg.setText("");
        THb.setText(""); TWbc.setText(""); TPlt.setText(""); THct.setText("");
        TLymMxdGra.setText(""); TMcv.setText(""); TMch.setText(""); TMchc.setText("");
        TRdwSd.setText(""); TRdwCv.setText(""); TRbc.setText("");
        TKesanEritrosit.setText(""); TKesanLeukosit.setText(""); TKesanTrombosit.setText("");
        TKesan.setText(""); TSaran.setText(""); 
        TCari.setText(""); 
        
        tglSimpan = ""; jamSimpan = "";
        
        Tanggal.setEnabled(true);
        ChkJln.setSelected(true);
        ChkJlnActionPerformed(null);
        
        // Atur Dokter PJ Default lagi saat form dikosongkan
        try {
            ps = koneksi.prepareStatement("select kd_dokterlab from set_pjlab");
            try {
                rs = ps.executeQuery();
                if (rs.next()) { 
                    KodePj.setText(rs.getString("kd_dokterlab")); 
                    NmDokterPj.setText(dokter.tampil3(rs.getString("kd_dokterlab"))); 
                } else {
                    KodePj.setText(""); 
                    NmDokterPj.setText("");                     
                }
            } catch (Exception e) { System.out.println("Notif PJ Lab (EmptTeks): " + e); } 
            finally { 
                 if (rs != null) { rs.close(); }
                 if (ps != null) { ps.close(); }
            }
        } catch (Exception e) { System.out.println("Error ambil PJ Lab (EmptTeks): "+e); }
        
        // Atur Petugas Default lagi saat form dikosongkan
         if (akses.getjml2() >= 1) {
            KdPtg.setText(akses.getkode());
            NmPtg.setText(petugas.tampil3(KdPtg.getText()));
        } else {
            KdPtg.setText("");
            NmPtg.setText("");
        }

        THb.requestFocus(); // Fokus awal ke Hb
    }

    public void setNoRm(String norwt, String posisi) {
        noorder = "";
        TNoRw.setText(norwt);
        TCari.setText(norwt); 
        this.status = posisi;
        
        isRawat(); // Ambil status kelas & kamar
        isPasien(); 
        emptTeks(); // Kosongkan form & set default PJ/Petugas
        
        // Set ulang data pasien setelah emptTeks
        TNoRw.setText(norwt); 
        TCari.setText(norwt); 
        isPasien(); // Panggil lagi untuk Dokter Perujuk
        
        TabRawat.setSelectedIndex(0);
        THb.requestFocus(); 
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getperiksa_lab()); // Ganti hak akses jika perlu
        BtnGanti.setEnabled(akses.getperiksa_lab());  // Ganti hak akses jika perlu
        BtnHapus.setEnabled(akses.getperiksa_lab());  // Ganti hak akses jika perlu
        BtnCetak.setEnabled(akses.getperiksa_lab());  // Ganti hak akses jika perlu
        btnPetugas.setEnabled(akses.getubah_petugas_lab_pk()); // Ganti hak akses jika perlu
    }

    private void isRawat(){
        if(status.equals("Ranap")){
            kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",TNoRw.getText());
            kelas=Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                    " where kamar.kd_kamar=? ",kamar);            
            kamar="Kamar"; 
        }else if(status.equals("Ralan")){
            kamar="Poli";
            namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                    "where reg_periksa.no_rawat=?",TNoRw.getText());
            kelas="Rawat Jalan";
        }
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
                    // Dokter Perujuk diisi otomatis dari dokter periksa, bisa diedit
                    KodePerujuk.setText(rs.getString("kd_dokter"));
                    NmPerujuk.setText(rs.getString("nm_dokter"));
                } else { // Jika data reg_periksa tidak ditemukan
                     TNoRM.setText("");
                     TPasien.setText("");
                     KodePerujuk.setText("");
                     NmPerujuk.setText("");
                }
            } catch (Exception e) {
                System.out.println("Notif Pasien : " + e);
            } finally {
                if (rs != null) { rs.close(); }
                if (ps != null) { ps.close(); }
            }
        } catch (Exception e) {
            System.out.println("Notif Error isPasien: " + e);
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
            // Nama tabel diubah menjadi pemeriksaan_mdt
            ps = koneksi.prepareStatement(
                "SELECT * FROM pemeriksaan_mdt WHERE no_rawat=? AND tgl_periksa=? AND jam=?");
            ps.setString(1, norawat);
            ps.setString(2, tanggal);
            ps.setString(3, jam);
            rs = ps.executeQuery();
            if(rs.next()){
                TNoRw.setText(rs.getString("no_rawat"));
                isPasien(); 
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
                THb.setText(rs.getString("hb"));
                TWbc.setText(rs.getString("wbc"));
                TPlt.setText(rs.getString("plt"));
                THct.setText(rs.getString("hct"));
                TLymMxdGra.setText(rs.getString("lym_mxd_gra"));
                TMcv.setText(rs.getString("mcv"));
                TMch.setText(rs.getString("mch"));
                TMchc.setText(rs.getString("mchc"));
                TRdwSd.setText(rs.getString("rdw_sd"));
                TRdwCv.setText(rs.getString("rdw_cv"));
                TRbc.setText(rs.getString("rbc"));
                TKesanEritrosit.setText(rs.getString("kesan_eritrosit"));
                TKesanLeukosit.setText(rs.getString("kesan_leukosit"));
                TKesanTrombosit.setText(rs.getString("kesan_trombosit"));
                TKesan.setText(rs.getString("kesan"));
                TSaran.setText(rs.getString("saran"));
                
                tglSimpan = rs.getString("tgl_periksa");
                jamSimpan = rs.getString("jam");
            }
        } catch (Exception e) {
            System.out.println("Notif Tampil Detail: " + e);
        } finally {
            if(rs != null){
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if(ps != null){
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
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
    
    
    
    // Variables declaration - do not modify                     
    private widget.Button BtnBaru;
    private widget.Button BtnCari;
    private widget.Button BtnCetak;
    private widget.Button BtnGanti;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox KdPtg;
    private widget.TextBox KodePerujuk;
    private widget.TextBox KodePj;
    private widget.Label LCount;
    private widget.TextBox NmDokterPj;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NmPtg;
    private widget.PanelBiasa PanelContent;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollData;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JPanel TabData;
    private javax.swing.JPanel TabInput;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextArea TKesan;
    private widget.TextArea TKesanEritrosit;
    private widget.TextArea TKesanLeukosit;
    private widget.TextArea TKesanTrombosit;
    private widget.TextArea TSaran;
    private widget.TextBox THb;
    private widget.TextBox THct;
    private widget.TextBox TLymMxdGra;
    private widget.TextBox TMch;
    private widget.TextBox TMchc;
    private widget.TextBox TMcv;
    private widget.TextBox TPlt;
    private widget.TextBox TRbc;
    private widget.TextBox TRdwCv;
    private widget.TextBox TRdwSd;
    private widget.TextBox TWbc;
    private widget.Button btnDokter;
    private widget.Button btnDokterPj;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel2;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel7_Data;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabelHct;
    private widget.Label jLabelHb;
    private widget.Label jLabelKesan;
    private widget.Label jLabelKesanEritrosit;
    private widget.Label jLabelKesanLeukosit;
    private widget.Label jLabelKesanTrombosit;
    private widget.Label jLabelLym;
    private widget.Label jLabelMch;
    private widget.Label jLabelMchc;
    private widget.Label jLabelMcv;
    private widget.Label jLabelPlt;
    private widget.Label jLabelRbc;
    private widget.Label jLabelRdwCv;
    private widget.Label jLabelRdwSd;
    private widget.Label jLabelSaran;
    private widget.Label jLabelWbc;
    private widget.PanelBiasa panelInput;
    private widget.panelisi panelCariData;
    private widget.panelisi panelTombol;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbDataPenilaian;
    // End of variables declaration                   
}