/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package rekammedis;

import bridging.ICareRiwayatPerawatan;
import bridging.OrthancViewerHybridSplitRad;
import bridging.OrthancViewerHybridSplitSOAPIE;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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
import kepegawaian.DlgCariDokter;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;
//import digitalsignature.DlgViewPdf;
import java.text.SimpleDateFormat;
import inventory.DlgCopyResep;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.Timer;
import rekammedis.RMDataResumePasien;
import inventory.InventoryResepLuar;
import inventory.DlgPemberianObat;
import inventory.DlgPeresepanDokter;
import inventory.DlgPermintaanResepPulang;
import inventory.DlgPermintaanStokPasien;
import keuangan.DlgJnsPerawatanRalan;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import permintaan.DlgBookingOperasi;
import surat.SuratKontrol;
import permintaan.DlgPermintaanPelayananInformasiObat;
import simrskhanza.DlgKamarInap;
import rekammedis.RMRiwayatPenunjang;
import simrskhanza.DRujukInternal;
import inventory.DlgCariObat2;
import inventory.DlgCariObat3;
import java.awt.Color;
import javax.swing.SwingUtilities;

/**
 *
 * @author perpustakaan
 */
public final class ValidasiSOAPPRI extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String FileName, aktifkanparsial="no", kd_pj="", kode_poli="", variabel="",kamar="",jenisbayar="";
    private int i=0, jmlparsial=0, tinggi=0;    
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private RMCariKeluhan carikeluhan=new RMCariKeluhan(null,false);
    private RMCariPemeriksaan caripemeriksaan=new RMCariPemeriksaan(null,false);
    private RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
    private RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
    private RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);
    private DlgDiagnosaPenyakit penyakit=new DlgDiagnosaPenyakit(null,false);

   // private RMCariRadRalan rmcariradralan=new RMCariRadRalan(null,false);
  //  private RMCariLabRalan rmcarilabralan=new RMCariLabRalan(null,false);
    private RMCariTindakan caritindakan=new RMCariTindakan(null,false);
    private RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
   // private RMCariKeluhanAssMedis carikeluhanass=new RMCariKeluhanAssMedis(null,false);
    SimpleDateFormat tanggalNow = new SimpleDateFormat("yyyy-MM-dd");
//    private SimpleDateFormat tanggalNow = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat jamNow = new SimpleDateFormat("HH:mm:ss");
    private final Properties prop = new Properties();
    private OrthancViewerHybridSplitSOAPIE dicomViewer=new OrthancViewerHybridSplitSOAPIE(null,false);
    private String lastNoRM = "";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public ValidasiSOAPPRI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        initRawatInap();
        SwingUtilities.invokeLater(() -> {
        placeholderDiagnosa();
        
    });
   
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Soap Perawat","No.Rawat","No.RM","Nama Pasien","NIP Dokter","Nama Dokter Pemeriksa","Tanggal SOAP Dokter","Jam SOAP","Suhu (°C)","Tensi (mmHg)","RR (/menit)",
            "Nadi (/menit)","Tinggi Badan","Berat Badan","SpO2 (%)","GCS","S (SUBJECTIVE)","O (OBJECTIVE)",
            "A (ASSESSMENT)","P (PLAN)","DIAGNOSA","Alergi","Catatan Pasien","NIP Petugas","Nama Perawat Petugas",
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(250);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(50);
            }else if(i==14){
                column.setPreferredWidth(50);
            }else if(i==15){
                column.setPreferredWidth(50);
            }else if(i==16){
                column.setPreferredWidth(300);
            }else if(i==17){
                column.setPreferredWidth(300);
            }else if(i==18){
                column.setPreferredWidth(300);
            }else if(i==19){
                column.setPreferredWidth(300);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(200);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TPenilaian.setDocument(new batasInput((int)2000).getKata(TPenilaian));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KodeDokter.requestFocus();
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
            
        ChkInput.setSelected(false);
        isForm();
        
        ChkAccor.setSelected(true);
        isMenu();
        
        jam();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial=prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {            
            aktifkanparsial="no";
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDigitalTTE = new javax.swing.JMenuItem();
        MnLaporanResume = new javax.swing.JMenuItem();
        MnInputDiagnosa = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnBatal = new widget.Button();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayat = new widget.Button();
        BtnResepObat = new widget.Button();
        BtnCopyResep = new widget.Button();
        BtnPermintaanStok = new widget.Button();
        BtnPermintaanResepPulang = new widget.Button();
        BtnInputObat = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnDiagnosa = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalFisioterapi = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnAwalMedisAnak = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnAwalMedisHemodialisa = new widget.Button();
        BtnChecklistPreOperasi = new widget.Button();
        BtnSignInSebelumAnestesi = new widget.Button();
        BtnTimeOutSebelumInsisi = new widget.Button();
        BtnSignOutSebelumMenutupLuka = new widget.Button();
        BtnChecklistPostOperasi = new widget.Button();
        BtnPenilaianPreOperasi = new widget.Button();
        BtnPenilaianPreAnestesi = new widget.Button();
        BtnSkorAldrettePascaAnestesi = new widget.Button();
        BtnSkorStewardPascaAnestesi = new widget.Button();
        BtnPenilaianPsikolog = new widget.Button();
        BtnPerencanaanPemulangan = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhDewasa = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhAnak = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhLansia = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhNeonatus = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhGeriatri = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhPsikiatri = new widget.Button();
        BtnPenilaianLanjutanSkriningFungsional = new widget.Button();
        BtnPenilaianResikoDekubitus = new widget.Button();
        BtnHasilPemeriksaanUSG = new widget.Button();
        BtnDokumentasiESWL = new widget.Button();
        BtnCatatanObservasiRanap = new widget.Button();
        BtnCatatanObservasiRanapKebidanan = new widget.Button();
        BtnCatatanObservasiRanapPostPartum = new widget.Button();
        BtnFollowUpDBD = new widget.Button();
        BtnCatatanKeperawatan = new widget.Button();
        BtnCatatanCekGDS = new widget.Button();
        BtnPenilaianUlangNyeri = new widget.Button();
        BtnPemantauanPEWSAnak = new widget.Button();
        BtnPemantauanPEWSDewasa = new widget.Button();
        BtnPemantauanMEOWS = new widget.Button();
        BtnPemantauanEWSNeonatus = new widget.Button();
        BtnChecklistKriteriaMasukHCU = new widget.Button();
        BtnChecklistKriteriaKeluarHCU = new widget.Button();
        BtnChecklistKriteriaMasukICU = new widget.Button();
        BtnChecklistKriteriaKeluarICU = new widget.Button();
        BtnMonitoringReaksiTranfusi = new widget.Button();
        BtnSkriningNutrisiDewasa = new widget.Button();
        BtnSkriningNutrisiLansia = new widget.Button();
        BtnSkriningNutrisiAnak = new widget.Button();
        BtnSkriningGiziLanjut = new widget.Button();
        BtnAsuhanGizi = new widget.Button();
        BtnMonitoringAsuhanGizi = new widget.Button();
        BtnCatatanADIMEGizi = new widget.Button();
        BtnKonselingFarmasi = new widget.Button();
        BtnInformasiObat = new widget.Button();
        BtnRekonsiliasiObat = new widget.Button();
        BtnTransferAntarRuang = new widget.Button();
        BtnPengkajianRestrain = new widget.Button();
        BtnPenilaianPasienTerminal = new widget.Button();
        BtnPenilaianKorbanKekerasan = new widget.Button();
        BtnPenilaianKecemasanAnak = new widget.Button();
        BtnPenilaianPasienPenyakitMenular = new widget.Button();
        BtnPenilaianTambahanGeriatri = new widget.Button();
        BtnPenilaianTambahanBunuhDiri = new widget.Button();
        BtnPenilaianTambahanPerilakuKekerasan = new widget.Button();
        BtnPenilaianTambahanMelarikanDiri = new widget.Button();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        label15 = new widget.Label();
        KodeDokter1 = new widget.TextBox();
        NamaDokter1 = new widget.TextBox();
        TanggalPemeriksaan = new widget.TextBox();
        label16 = new widget.Label();
        label17 = new widget.Label();
        JamPemeriksaan = new widget.TextBox();
        label20 = new widget.Label();
        suhu = new widget.TextBox();
        label24 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        spo = new widget.TextBox();
        label21 = new widget.Label();
        tensi = new widget.TextBox();
        label18 = new widget.Label();
        gcs = new widget.TextBox();
        label22 = new widget.Label();
        rr = new widget.TextBox();
        label23 = new widget.Label();
        nadi = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        alergi = new widget.TextArea();
        BtnTemplatePemeriksaan = new widget.Button();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        scrollPane5 = new widget.ScrollPane();
        diagnosa = new widget.TextArea();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        jSeparator1 = new javax.swing.JSeparator();
        label19 = new widget.Label();
        tb = new widget.TextBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        bb = new widget.TextBox();
        BtnDicom = new widget.Button();
        BtnHasilRadiologi = new widget.Button();
        scrollPane8 = new widget.ScrollPane();
        catatan = new widget.TextArea();
        label27 = new widget.Label();
        BtnTemplatePemberianObat2 = new widget.Button();
        TanggalPerawatan = new widget.TextBox();
        BtnOdontogram1 = new widget.Button();
        BtnTemplatePemeriksaan1 = new widget.Button();
        BtnOdontogram2 = new widget.Button();
        Tanggal = new widget.Tanggal();
        label28 = new widget.Label();
        BtnRiwayatFKTP1 = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDigitalTTE.setBackground(new java.awt.Color(255, 255, 254));
        MnDigitalTTE.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDigitalTTE.setForeground(new java.awt.Color(50, 50, 50));
        MnDigitalTTE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDigitalTTE.setText("Sign Digital Signature");
        MnDigitalTTE.setToolTipText("");
        MnDigitalTTE.setName("MnDigitalTTE"); // NOI18N
        MnDigitalTTE.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDigitalTTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDigitalTTEActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDigitalTTE);

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Resume Pasien");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        MnInputDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnInputDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnInputDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputDiagnosa.setText("Input Diagnosa Pasien");
        MnInputDiagnosa.setName("MnInputDiagnosa"); // NOI18N
        MnInputDiagnosa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnInputDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputDiagnosaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputDiagnosa);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Validasi SOAP Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("VCR OSD Mono", 0, 16), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setMaximumSize(new java.awt.Dimension(32767, 22767));
        Scroll.setMinimumSize(new java.awt.Dimension(16, 20));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 450));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setPreferredScrollableViewportSize(new java.awt.Dimension(450, 250));
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

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/checked.png"))); // NOI18N
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

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 330));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(205, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(130, 383));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(150, 483));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayat);

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat.setText("Input Resep");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); // NOI18N
        BtnResepObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepObat);

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); // NOI18N
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCopyResep);

        BtnPermintaanStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanStok.setText("Permintaan Stok Pasien");
        BtnPermintaanStok.setFocusPainted(false);
        BtnPermintaanStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanStok.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanStok.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanStok.setName("BtnPermintaanStok"); // NOI18N
        BtnPermintaanStok.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanStok.setRoundRect(false);
        BtnPermintaanStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanStokActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanStok);

        BtnPermintaanResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanResepPulang.setText("Permintaan Resep Pulang");
        BtnPermintaanResepPulang.setFocusPainted(false);
        BtnPermintaanResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanResepPulang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanResepPulang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanResepPulang.setName("BtnPermintaanResepPulang"); // NOI18N
        BtnPermintaanResepPulang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanResepPulang.setRoundRect(false);
        BtnPermintaanResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanResepPulangActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanResepPulang);

        BtnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInputObat.setText("Input Obat & BHP");
        BtnInputObat.setFocusPainted(false);
        BtnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInputObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInputObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInputObat.setName("BtnInputObat"); // NOI18N
        BtnInputObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInputObat.setRoundRect(false);
        BtnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInputObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInputObat);

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); // NOI18N
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });
        FormMenu.add(BtnObatBhp);

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); // NOI18N
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnBerkasDigital);

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); // NOI18N
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanLab);

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); // NOI18N
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanRad);

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); // NOI18N
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnJadwalOperasi);

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); // NOI18N
        BtnSKDP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSKDP);

        BtnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDiagnosa.setText("Diagnosa");
        BtnDiagnosa.setFocusPainted(false);
        BtnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDiagnosa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDiagnosa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDiagnosa.setName("BtnDiagnosa"); // NOI18N
        BtnDiagnosa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDiagnosa.setRoundRect(false);
        BtnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDiagnosa);

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResume.setText("Resume Pasien");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); // NOI18N
        BtnResume.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResume);

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); // NOI18N
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalFisioterapi);

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis.setText("Awal Medis ");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); // NOI18N
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedis);

        BtnAwalMedisAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisAnak.setText("Awal Medis Anak");
        BtnAwalMedisAnak.setFocusPainted(false);
        BtnAwalMedisAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisAnak.setName("BtnAwalMedisAnak"); // NOI18N
        BtnAwalMedisAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisAnak.setRoundRect(false);
        BtnAwalMedisAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisAnak);

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); // NOI18N
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisKandungan);

        BtnAwalMedisHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisHemodialisa.setText("Awal Medis Hemodialisa");
        BtnAwalMedisHemodialisa.setFocusPainted(false);
        BtnAwalMedisHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisHemodialisa.setName("BtnAwalMedisHemodialisa"); // NOI18N
        BtnAwalMedisHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisHemodialisa.setRoundRect(false);
        BtnAwalMedisHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisHemodialisaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisHemodialisa);

        BtnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPreOperasi.setText("Check List Pre Operasi");
        BtnChecklistPreOperasi.setFocusPainted(false);
        BtnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPreOperasi.setName("BtnChecklistPreOperasi"); // NOI18N
        BtnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPreOperasi.setRoundRect(false);
        BtnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPreOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistPreOperasi);

        BtnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        BtnSignInSebelumAnestesi.setFocusPainted(false);
        BtnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignInSebelumAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignInSebelumAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignInSebelumAnestesi.setName("BtnSignInSebelumAnestesi"); // NOI18N
        BtnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignInSebelumAnestesi.setRoundRect(false);
        BtnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignInSebelumAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSignInSebelumAnestesi);

        BtnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        BtnTimeOutSebelumInsisi.setFocusPainted(false);
        BtnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTimeOutSebelumInsisi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTimeOutSebelumInsisi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTimeOutSebelumInsisi.setName("BtnTimeOutSebelumInsisi"); // NOI18N
        BtnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTimeOutSebelumInsisi.setRoundRect(false);
        BtnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTimeOutSebelumInsisi);

        BtnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        BtnSignOutSebelumMenutupLuka.setFocusPainted(false);
        BtnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignOutSebelumMenutupLuka.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignOutSebelumMenutupLuka.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignOutSebelumMenutupLuka.setName("BtnSignOutSebelumMenutupLuka"); // NOI18N
        BtnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignOutSebelumMenutupLuka.setRoundRect(false);
        BtnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSignOutSebelumMenutupLuka);

        BtnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPostOperasi.setText("Check List Post Operasi");
        BtnChecklistPostOperasi.setFocusPainted(false);
        BtnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPostOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPostOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPostOperasi.setName("BtnChecklistPostOperasi"); // NOI18N
        BtnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPostOperasi.setRoundRect(false);
        BtnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPostOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistPostOperasi);

        BtnPenilaianPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreOperasi.setText("Penilaian Pre Operasi");
        BtnPenilaianPreOperasi.setFocusPainted(false);
        BtnPenilaianPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreOperasi.setName("BtnPenilaianPreOperasi"); // NOI18N
        BtnPenilaianPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreOperasi.setRoundRect(false);
        BtnPenilaianPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPreOperasi);

        BtnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreAnestesi.setText("Penilaian Pre Anestesi");
        BtnPenilaianPreAnestesi.setFocusPainted(false);
        BtnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreAnestesi.setName("BtnPenilaianPreAnestesi"); // NOI18N
        BtnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreAnestesi.setRoundRect(false);
        BtnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPreAnestesi);

        BtnSkorAldrettePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorAldrettePascaAnestesi.setText("Skor Aldrette Pasca Anestesi");
        BtnSkorAldrettePascaAnestesi.setFocusPainted(false);
        BtnSkorAldrettePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorAldrettePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorAldrettePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorAldrettePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorAldrettePascaAnestesi.setName("BtnSkorAldrettePascaAnestesi"); // NOI18N
        BtnSkorAldrettePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorAldrettePascaAnestesi.setRoundRect(false);
        BtnSkorAldrettePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorAldrettePascaAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkorAldrettePascaAnestesi);

        BtnSkorStewardPascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorStewardPascaAnestesi.setText("Skor Steward Pasca Anestesi");
        BtnSkorStewardPascaAnestesi.setFocusPainted(false);
        BtnSkorStewardPascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorStewardPascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorStewardPascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorStewardPascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorStewardPascaAnestesi.setName("BtnSkorStewardPascaAnestesi"); // NOI18N
        BtnSkorStewardPascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorStewardPascaAnestesi.setRoundRect(false);
        BtnSkorStewardPascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorStewardPascaAnestesiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkorStewardPascaAnestesi);

        BtnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPsikolog.setText("Penilaian Psikolog");
        BtnPenilaianPsikolog.setFocusPainted(false);
        BtnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPsikolog.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikolog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikolog.setName("BtnPenilaianPsikolog"); // NOI18N
        BtnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikolog.setRoundRect(false);
        BtnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPsikologActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPsikolog);

        BtnPerencanaanPemulangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPerencanaanPemulangan.setText("Perencanaan Pemulangan");
        BtnPerencanaanPemulangan.setFocusPainted(false);
        BtnPerencanaanPemulangan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPerencanaanPemulangan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPerencanaanPemulangan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPerencanaanPemulangan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPerencanaanPemulangan.setName("BtnPerencanaanPemulangan"); // NOI18N
        BtnPerencanaanPemulangan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPerencanaanPemulangan.setRoundRect(false);
        BtnPerencanaanPemulangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerencanaanPemulanganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPerencanaanPemulangan);

        BtnPenilaianLanjutanResikoJatuhDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setText("Lanjutan Risiko Jatuh Dewasa");
        BtnPenilaianLanjutanResikoJatuhDewasa.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhDewasa.setName("BtnPenilaianLanjutanResikoJatuhDewasa"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhDewasa.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhDewasa);

        BtnPenilaianLanjutanResikoJatuhAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setText("Lanjutan Risiko Jatuh Anak");
        BtnPenilaianLanjutanResikoJatuhAnak.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhAnak.setName("BtnPenilaianLanjutanResikoJatuhAnak"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhAnak.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhAnak);

        BtnPenilaianLanjutanResikoJatuhLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setText("Lanjutan Risiko Jatuh Lansia");
        BtnPenilaianLanjutanResikoJatuhLansia.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhLansia.setName("BtnPenilaianLanjutanResikoJatuhLansia"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhLansia.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhLansia);

        BtnPenilaianLanjutanResikoJatuhNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setText("Lanjutan Risiko Jatuh Neonatus");
        BtnPenilaianLanjutanResikoJatuhNeonatus.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setName("BtnPenilaianLanjutanResikoJatuhNeonatus"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhNeonatus);

        BtnPenilaianLanjutanResikoJatuhGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setText("Lanjutan Risiko Jatuh Geriatri");
        BtnPenilaianLanjutanResikoJatuhGeriatri.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setName("BtnPenilaianLanjutanResikoJatuhGeriatri"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhGeriatri);

        BtnPenilaianLanjutanResikoJatuhPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setText("Lanjutan Risiko Jatuh Psikiatri");
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setName("BtnPenilaianLanjutanResikoJatuhPsikiatri"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhPsikiatri);

        BtnPenilaianLanjutanSkriningFungsional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setText("Lanjutan Skrining Fungsional");
        BtnPenilaianLanjutanSkriningFungsional.setFocusPainted(false);
        BtnPenilaianLanjutanSkriningFungsional.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanSkriningFungsional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanSkriningFungsional.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanSkriningFungsional.setName("BtnPenilaianLanjutanSkriningFungsional"); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanSkriningFungsional.setRoundRect(false);
        BtnPenilaianLanjutanSkriningFungsional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanSkriningFungsionalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);

        BtnPenilaianResikoDekubitus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianResikoDekubitus.setText("Risiko Dekubitus");
        BtnPenilaianResikoDekubitus.setFocusPainted(false);
        BtnPenilaianResikoDekubitus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianResikoDekubitus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianResikoDekubitus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianResikoDekubitus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianResikoDekubitus.setName("BtnPenilaianResikoDekubitus"); // NOI18N
        BtnPenilaianResikoDekubitus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianResikoDekubitus.setRoundRect(false);
        BtnPenilaianResikoDekubitus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianResikoDekubitusActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianResikoDekubitus);

        BtnHasilPemeriksaanUSG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilPemeriksaanUSG.setText("Hasil USG Kandungan");
        BtnHasilPemeriksaanUSG.setFocusPainted(false);
        BtnHasilPemeriksaanUSG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG.setName("BtnHasilPemeriksaanUSG"); // NOI18N
        BtnHasilPemeriksaanUSG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG.setRoundRect(false);
        BtnHasilPemeriksaanUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSGActionPerformed(evt);
            }
        });
        FormMenu.add(BtnHasilPemeriksaanUSG);

        BtnDokumentasiESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDokumentasiESWL.setText("Dokumentasi Tindakan ESWL");
        BtnDokumentasiESWL.setFocusPainted(false);
        BtnDokumentasiESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDokumentasiESWL.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiESWL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiESWL.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiESWL.setName("BtnDokumentasiESWL"); // NOI18N
        BtnDokumentasiESWL.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDokumentasiESWL.setRoundRect(false);
        BtnDokumentasiESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiESWLActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDokumentasiESWL);

        BtnCatatanObservasiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanap.setText("Observasi Ranap");
        BtnCatatanObservasiRanap.setFocusPainted(false);
        BtnCatatanObservasiRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanap.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanap.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanap.setName("BtnCatatanObservasiRanap"); // NOI18N
        BtnCatatanObservasiRanap.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanap.setRoundRect(false);
        BtnCatatanObservasiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanObservasiRanap);

        BtnCatatanObservasiRanapKebidanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setText("Observasi Kebidanan");
        BtnCatatanObservasiRanapKebidanan.setFocusPainted(false);
        BtnCatatanObservasiRanapKebidanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanapKebidanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanapKebidanan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanapKebidanan.setName("BtnCatatanObservasiRanapKebidanan"); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanapKebidanan.setRoundRect(false);
        BtnCatatanObservasiRanapKebidanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapKebidananActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanObservasiRanapKebidanan);

        BtnCatatanObservasiRanapPostPartum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setText("Observasi Post Partum");
        BtnCatatanObservasiRanapPostPartum.setFocusPainted(false);
        BtnCatatanObservasiRanapPostPartum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanapPostPartum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanapPostPartum.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanapPostPartum.setName("BtnCatatanObservasiRanapPostPartum"); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanapPostPartum.setRoundRect(false);
        BtnCatatanObservasiRanapPostPartum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapPostPartumActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanObservasiRanapPostPartum);

        BtnFollowUpDBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnFollowUpDBD.setText("Follow Up DBD");
        BtnFollowUpDBD.setFocusPainted(false);
        BtnFollowUpDBD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnFollowUpDBD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnFollowUpDBD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnFollowUpDBD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnFollowUpDBD.setName("BtnFollowUpDBD"); // NOI18N
        BtnFollowUpDBD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnFollowUpDBD.setRoundRect(false);
        BtnFollowUpDBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFollowUpDBDActionPerformed(evt);
            }
        });
        FormMenu.add(BtnFollowUpDBD);

        BtnCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanKeperawatan.setText("Catatan Keperawatan");
        BtnCatatanKeperawatan.setFocusPainted(false);
        BtnCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanKeperawatan.setName("BtnCatatanKeperawatan"); // NOI18N
        BtnCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanKeperawatan.setRoundRect(false);
        BtnCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanKeperawatanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanKeperawatan);

        BtnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanCekGDS.setText("Catatan Cek GDS");
        BtnCatatanCekGDS.setFocusPainted(false);
        BtnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanCekGDS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCekGDS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCekGDS.setName("BtnCatatanCekGDS"); // NOI18N
        BtnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCekGDS.setRoundRect(false);
        BtnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanCekGDSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanCekGDS);

        BtnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianUlangNyeri.setText("Penilaian Ulang Nyeri");
        BtnPenilaianUlangNyeri.setFocusPainted(false);
        BtnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianUlangNyeri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianUlangNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianUlangNyeri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianUlangNyeri.setName("BtnPenilaianUlangNyeri"); // NOI18N
        BtnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianUlangNyeri.setRoundRect(false);
        BtnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianUlangNyeriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianUlangNyeri);

        BtnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSAnak.setText("Pemantauan PEWS Anak");
        BtnPemantauanPEWSAnak.setFocusPainted(false);
        BtnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSAnak.setName("BtnPemantauanPEWSAnak"); // NOI18N
        BtnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSAnak.setRoundRect(false);
        BtnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanPEWSAnak);

        BtnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSDewasa.setText("Pemantauan EWS Dewasa");
        BtnPemantauanPEWSDewasa.setFocusPainted(false);
        BtnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSDewasa.setName("BtnPemantauanPEWSDewasa"); // NOI18N
        BtnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSDewasa.setRoundRect(false);
        BtnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanPEWSDewasa);

        BtnPemantauanMEOWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanMEOWS.setText("Pemantauan MEOWS Obstetri");
        BtnPemantauanMEOWS.setFocusPainted(false);
        BtnPemantauanMEOWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanMEOWS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanMEOWS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanMEOWS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanMEOWS.setName("BtnPemantauanMEOWS"); // NOI18N
        BtnPemantauanMEOWS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanMEOWS.setRoundRect(false);
        BtnPemantauanMEOWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanMEOWSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanMEOWS);

        BtnPemantauanEWSNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanEWSNeonatus.setText("Pemantauan EWS Neonatus");
        BtnPemantauanEWSNeonatus.setFocusPainted(false);
        BtnPemantauanEWSNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanEWSNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanEWSNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanEWSNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanEWSNeonatus.setName("BtnPemantauanEWSNeonatus"); // NOI18N
        BtnPemantauanEWSNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanEWSNeonatus.setRoundRect(false);
        BtnPemantauanEWSNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanEWSNeonatusActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemantauanEWSNeonatus);

        BtnChecklistKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukHCU.setText("Check List Masuk HCU");
        BtnChecklistKriteriaMasukHCU.setFocusPainted(false);
        BtnChecklistKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukHCU.setName("BtnChecklistKriteriaMasukHCU"); // NOI18N
        BtnChecklistKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukHCU.setRoundRect(false);
        BtnChecklistKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukHCUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaMasukHCU);

        BtnChecklistKriteriaKeluarHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setText("Check List Keluar HCU");
        BtnChecklistKriteriaKeluarHCU.setFocusPainted(false);
        BtnChecklistKriteriaKeluarHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaKeluarHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaKeluarHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaKeluarHCU.setName("BtnChecklistKriteriaKeluarHCU"); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaKeluarHCU.setRoundRect(false);
        BtnChecklistKriteriaKeluarHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaKeluarHCUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaKeluarHCU);

        BtnChecklistKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukICU.setText("Check List Masuk ICU");
        BtnChecklistKriteriaMasukICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukICU.setName("BtnChecklistKriteriaMasukICU"); // NOI18N
        BtnChecklistKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukICU.setRoundRect(false);
        BtnChecklistKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukICUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaMasukICU);

        BtnChecklistKriteriaKeluarICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaKeluarICU.setText("Check List Keluar ICU");
        BtnChecklistKriteriaKeluarICU.setFocusPainted(false);
        BtnChecklistKriteriaKeluarICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaKeluarICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaKeluarICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaKeluarICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaKeluarICU.setName("BtnChecklistKriteriaKeluarICU"); // NOI18N
        BtnChecklistKriteriaKeluarICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaKeluarICU.setRoundRect(false);
        BtnChecklistKriteriaKeluarICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaKeluarICUActionPerformed(evt);
            }
        });
        FormMenu.add(BtnChecklistKriteriaKeluarICU);

        BtnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        BtnMonitoringReaksiTranfusi.setFocusPainted(false);
        BtnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringReaksiTranfusi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringReaksiTranfusi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringReaksiTranfusi.setName("BtnMonitoringReaksiTranfusi"); // NOI18N
        BtnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringReaksiTranfusi.setRoundRect(false);
        BtnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMonitoringReaksiTranfusi);

        BtnSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiDewasa.setText("Skrining Nutrisi Dewasa");
        BtnSkriningNutrisiDewasa.setFocusPainted(false);
        BtnSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiDewasa.setName("BtnSkriningNutrisiDewasa"); // NOI18N
        BtnSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiDewasa.setRoundRect(false);
        BtnSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiDewasaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiDewasa);

        BtnSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiLansia.setText("Skrining Nutrisi Lansia");
        BtnSkriningNutrisiLansia.setFocusPainted(false);
        BtnSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiLansia.setName("BtnSkriningNutrisiLansia"); // NOI18N
        BtnSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiLansia.setRoundRect(false);
        BtnSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiLansiaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiLansia);

        BtnSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiAnak.setText("Skrining Nutrisi Anak");
        BtnSkriningNutrisiAnak.setFocusPainted(false);
        BtnSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiAnak.setName("BtnSkriningNutrisiAnak"); // NOI18N
        BtnSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiAnak.setRoundRect(false);
        BtnSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningNutrisiAnak);

        BtnSkriningGiziLanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        BtnSkriningGiziLanjut.setFocusPainted(false);
        BtnSkriningGiziLanjut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningGiziLanjut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziLanjut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziLanjut.setName("BtnSkriningGiziLanjut"); // NOI18N
        BtnSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziLanjut.setRoundRect(false);
        BtnSkriningGiziLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningGiziLanjutActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSkriningGiziLanjut);

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); // NOI18N
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAsuhanGizi);

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); // NOI18N
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMonitoringAsuhanGizi);

        BtnCatatanADIMEGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanADIMEGizi.setText("Catatan ADIME Gizi");
        BtnCatatanADIMEGizi.setFocusPainted(false);
        BtnCatatanADIMEGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanADIMEGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanADIMEGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanADIMEGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanADIMEGizi.setName("BtnCatatanADIMEGizi"); // NOI18N
        BtnCatatanADIMEGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanADIMEGizi.setRoundRect(false);
        BtnCatatanADIMEGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanADIMEGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatanADIMEGizi);

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); // NOI18N
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKonselingFarmasi);

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); // NOI18N
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInformasiObat);

        BtnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        BtnRekonsiliasiObat.setFocusPainted(false);
        BtnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRekonsiliasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRekonsiliasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRekonsiliasiObat.setName("BtnRekonsiliasiObat"); // NOI18N
        BtnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRekonsiliasiObat.setRoundRect(false);
        BtnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekonsiliasiObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRekonsiliasiObat);

        BtnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTransferAntarRuang.setText("Transfer Antar Ruang");
        BtnTransferAntarRuang.setFocusPainted(false);
        BtnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTransferAntarRuang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTransferAntarRuang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTransferAntarRuang.setName("BtnTransferAntarRuang"); // NOI18N
        BtnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTransferAntarRuang.setRoundRect(false);
        BtnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTransferAntarRuangActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTransferAntarRuang);

        BtnPengkajianRestrain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPengkajianRestrain.setText("Pengkajian Restrain");
        BtnPengkajianRestrain.setFocusPainted(false);
        BtnPengkajianRestrain.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPengkajianRestrain.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPengkajianRestrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPengkajianRestrain.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPengkajianRestrain.setName("BtnPengkajianRestrain"); // NOI18N
        BtnPengkajianRestrain.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPengkajianRestrain.setRoundRect(false);
        BtnPengkajianRestrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPengkajianRestrainActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPengkajianRestrain);

        BtnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienTerminal.setText("Penilaian Pasien Terminal");
        BtnPenilaianPasienTerminal.setFocusPainted(false);
        BtnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienTerminal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienTerminal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienTerminal.setName("BtnPenilaianPasienTerminal"); // NOI18N
        BtnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienTerminal.setRoundRect(false);
        BtnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienTerminalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPasienTerminal);

        BtnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKorbanKekerasan.setText("Penilaian Korban Kekerasan");
        BtnPenilaianKorbanKekerasan.setFocusPainted(false);
        BtnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKorbanKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKorbanKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKorbanKekerasan.setName("BtnPenilaianKorbanKekerasan"); // NOI18N
        BtnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKorbanKekerasan.setRoundRect(false);
        BtnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianKorbanKekerasan);

        BtnPenilaianKecemasanAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKecemasanAnak.setText("Penilaian Kecemasan Anak");
        BtnPenilaianKecemasanAnak.setFocusPainted(false);
        BtnPenilaianKecemasanAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKecemasanAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKecemasanAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKecemasanAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKecemasanAnak.setName("BtnPenilaianKecemasanAnak"); // NOI18N
        BtnPenilaianKecemasanAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKecemasanAnak.setRoundRect(false);
        BtnPenilaianKecemasanAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKecemasanAnakActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianKecemasanAnak);

        BtnPenilaianPasienPenyakitMenular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setText("Pasien Penyakit Menular");
        BtnPenilaianPasienPenyakitMenular.setFocusPainted(false);
        BtnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienPenyakitMenular.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienPenyakitMenular.setName("BtnPenilaianPasienPenyakitMenular"); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienPenyakitMenular.setRoundRect(false);
        BtnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);

        BtnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanGeriatri.setText("Tambahan Pasien Geriatri");
        BtnPenilaianTambahanGeriatri.setFocusPainted(false);
        BtnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanGeriatri.setName("BtnPenilaianTambahanGeriatri"); // NOI18N
        BtnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanGeriatri.setRoundRect(false);
        BtnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanGeriatri);

        BtnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setText("Tambahan Bunuh Diri");
        BtnPenilaianTambahanBunuhDiri.setFocusPainted(false);
        BtnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanBunuhDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanBunuhDiri.setName("BtnPenilaianTambahanBunuhDiri"); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanBunuhDiri.setRoundRect(false);
        BtnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanBunuhDiri);

        BtnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setText("Tambahan Perilaku Kekerasan");
        BtnPenilaianTambahanPerilakuKekerasan.setFocusPainted(false);
        BtnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanPerilakuKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanPerilakuKekerasan.setName("BtnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanPerilakuKekerasan.setRoundRect(false);
        BtnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);

        BtnPenilaianTambahanMelarikanDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setText("Tambahan Melarikan Diri");
        BtnPenilaianTambahanMelarikanDiri.setFocusPainted(false);
        BtnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanMelarikanDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanMelarikanDiri.setName("BtnPenilaianTambahanMelarikanDiri"); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanMelarikanDiri.setRoundRect(false);
        BtnPenilaianTambahanMelarikanDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanMelarikanDiriActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        PanelInput.add(PanelAccor, java.awt.BorderLayout.WEST);

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

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setMaximumSize(new java.awt.Dimension(32767, 45767));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 334));
        FormInput.setLayout(null);

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(90, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(350, 10, 360, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(240, 10, 112, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(-10, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(-10, 40, 100, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(100, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(240, 40, 270, 23);

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
        BtnDokter.setBounds(510, 40, 28, 23);

        label15.setText("Tanggal :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(710, 10, 120, 23);

        KodeDokter1.setEditable(false);
        KodeDokter1.setName("KodeDokter1"); // NOI18N
        KodeDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KodeDokter1ActionPerformed(evt);
            }
        });
        KodeDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokter1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter1);
        KodeDokter1.setBounds(680, 40, 141, 23);

        NamaDokter1.setEditable(false);
        NamaDokter1.setName("NamaDokter1"); // NOI18N
        NamaDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter1);
        NamaDokter1.setBounds(820, 40, 270, 23);

        TanggalPemeriksaan.setEditable(false);
        TanggalPemeriksaan.setName("TanggalPemeriksaan"); // NOI18N
        TanggalPemeriksaan.setPreferredSize(new java.awt.Dimension(80, 23));
        TanggalPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPemeriksaanActionPerformed(evt);
            }
        });
        TanggalPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPemeriksaan);
        TanggalPemeriksaan.setBounds(250, 480, 141, 23);

        label16.setText("Tanggal SOAP:");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(160, 480, 90, 23);

        label17.setText("Jam SOAP:");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(400, 480, 60, 23);

        JamPemeriksaan.setEditable(false);
        JamPemeriksaan.setName("JamPemeriksaan"); // NOI18N
        JamPemeriksaan.setPreferredSize(new java.awt.Dimension(80, 23));
        JamPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JamPemeriksaanActionPerformed(evt);
            }
        });
        JamPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(JamPemeriksaan);
        JamPemeriksaan.setBounds(460, 480, 90, 23);

        label20.setText("Suhu (°C) :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(10, 250, 70, 23);

        suhu.setName("suhu"); // NOI18N
        suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                suhuKeyPressed(evt);
            }
        });
        FormInput.add(suhu);
        suhu.setBounds(90, 250, 70, 24);

        label24.setText("SpO2 (%) :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(10, 280, 70, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("S (SUBJECTIVE)"));
        scrollPane6.setName("scrollPane6"); // NOI18N

        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(TKeluhan);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(10, 100, 440, 60);

        spo.setName("spo"); // NOI18N
        spo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                spoKeyPressed(evt);
            }
        });
        FormInput.add(spo);
        spo.setBounds(90, 280, 70, 24);

        label21.setText("Tensi (mmHg) :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(150, 250, 90, 23);

        tensi.setName("tensi"); // NOI18N
        tensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tensiActionPerformed(evt);
            }
        });
        tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tensiKeyPressed(evt);
            }
        });
        FormInput.add(tensi);
        tensi.setBounds(250, 250, 90, 24);

        label18.setText("GCS (E,V,M) :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(340, 280, 80, 23);

        gcs.setName("gcs"); // NOI18N
        gcs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gcsKeyPressed(evt);
            }
        });
        FormInput.add(gcs);
        gcs.setBounds(430, 280, 90, 24);

        label22.setText("RR (/menit) :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(170, 280, 70, 23);

        rr.setName("rr"); // NOI18N
        rr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rrKeyPressed(evt);
            }
        });
        FormInput.add(rr);
        rr.setBounds(250, 280, 90, 24);

        label23.setText("Nadi (/menit) :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(350, 250, 70, 23);

        nadi.setName("nadi"); // NOI18N
        nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nadiKeyPressed(evt);
            }
        });
        FormInput.add(nadi);
        nadi.setBounds(430, 250, 90, 24);

        scrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        scrollPane7.setName("scrollPane7"); // NOI18N

        alergi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        alergi.setColumns(20);
        alergi.setRows(5);
        alergi.setName("alergi"); // NOI18N
        alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                alergiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(alergi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(110, 70, 340, 30);

        BtnTemplatePemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTemplatePemeriksaan.setMnemonic('4');
        BtnTemplatePemeriksaan.setText("SOAP Kunjungan Sebelumnya");
        BtnTemplatePemeriksaan.setToolTipText("ALt+4");
        BtnTemplatePemeriksaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTemplatePemeriksaan.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnTemplatePemeriksaan.setName("BtnTemplatePemeriksaan"); // NOI18N
        BtnTemplatePemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplatePemeriksaanActionPerformed(evt);
            }
        });
        FormInput.add(BtnTemplatePemeriksaan);
        BtnTemplatePemeriksaan.setBounds(880, 70, 200, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("A (ASSESSMENT)"));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TPenilaian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPenilaian.setColumns(20);
        TPenilaian.setRows(5);
        TPenilaian.setName("TPenilaian"); // NOI18N
        TPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenilaianKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TPenilaian);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(460, 100, 390, 60);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("O (OBJECTIVE)"));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(10, 160, 440, 80);

        scrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("P (PLAN)"));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TindakLanjut);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(460, 160, 390, 80);

        scrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("DIAGNOSA\n"));
        scrollPane5.setName("scrollPane5"); // NOI18N

        diagnosa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        diagnosa.setColumns(20);
        diagnosa.setRows(5);
        diagnosa.setName("diagnosa"); // NOI18N
        diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(diagnosa);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(550, 250, 300, 80);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTPTglActionPerformed(evt);
            }
        });
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(30, 860, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(130, 860, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(200, 860, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(270, 860, 62, 23);

        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(330, 860, 23, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(210, 130, 0, 390);

        label19.setText("Alergi :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(10, 70, 80, 23);

        tb.setName("tb"); // NOI18N
        tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKeyPressed(evt);
            }
        });
        FormInput.add(tb);
        tb.setBounds(250, 310, 90, 24);

        label25.setText("TB :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(170, 310, 70, 23);

        label26.setText("BB :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(340, 310, 80, 23);

        bb.setName("bb"); // NOI18N
        bb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bbKeyPressed(evt);
            }
        });
        FormInput.add(bb);
        bb.setBounds(430, 310, 90, 24);

        BtnDicom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Surgeon SH.png"))); // NOI18N
        BtnDicom.setMnemonic('4');
        BtnDicom.setText("Dicom (Foto Radiologi & USG)");
        BtnDicom.setToolTipText("");
        BtnDicom.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDicom.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnDicom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDicom.setName("BtnDicom"); // NOI18N
        BtnDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDicomActionPerformed(evt);
            }
        });
        FormInput.add(BtnDicom);
        BtnDicom.setBounds(880, 100, 200, 26);

        BtnHasilRadiologi.setForeground(new java.awt.Color(0, 0, 0));
        BtnHasilRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/TestTubes.png"))); // NOI18N
        BtnHasilRadiologi.setMnemonic('4');
        BtnHasilRadiologi.setText("Riwayat Penunjang");
        BtnHasilRadiologi.setToolTipText("");
        BtnHasilRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilRadiologi.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnHasilRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilRadiologi.setName("BtnHasilRadiologi"); // NOI18N
        BtnHasilRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilRadiologiActionPerformed(evt);
            }
        });
        FormInput.add(BtnHasilRadiologi);
        BtnHasilRadiologi.setBounds(880, 130, 200, 30);

        scrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        scrollPane8.setName("scrollPane8"); // NOI18N

        catatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        catatan.setColumns(20);
        catatan.setRows(5);
        catatan.setName("catatan"); // NOI18N
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(catatan);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(560, 70, 290, 30);

        label27.setText("Catatan Pasien :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(470, 70, 80, 23);

        BtnTemplatePemberianObat2.setForeground(new java.awt.Color(0, 0, 0));
        BtnTemplatePemberianObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/DutyRoster.png"))); // NOI18N
        BtnTemplatePemberianObat2.setMnemonic('4');
        BtnTemplatePemberianObat2.setText("Rujuk Internal dan Surat Konsul");
        BtnTemplatePemberianObat2.setToolTipText("");
        BtnTemplatePemberianObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTemplatePemberianObat2.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnTemplatePemberianObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTemplatePemberianObat2.setName("BtnTemplatePemberianObat2"); // NOI18N
        BtnTemplatePemberianObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplatePemberianObat2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTemplatePemberianObat2);
        BtnTemplatePemberianObat2.setBounds(880, 160, 200, 30);

        TanggalPerawatan.setEditable(false);
        TanggalPerawatan.setName("TanggalPerawatan"); // NOI18N
        TanggalPerawatan.setPreferredSize(new java.awt.Dimension(80, 23));
        TanggalPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPerawatanActionPerformed(evt);
            }
        });
        TanggalPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPerawatanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPerawatan);
        TanggalPerawatan.setBounds(10, 480, 141, 23);

        BtnOdontogram1.setForeground(new java.awt.Color(0, 0, 0));
        BtnOdontogram1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnOdontogram1.setMnemonic('4');
        BtnOdontogram1.setText("  SBAR Ranap");
        BtnOdontogram1.setToolTipText("");
        BtnOdontogram1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnOdontogram1.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnOdontogram1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnOdontogram1.setName("BtnOdontogram1"); // NOI18N
        BtnOdontogram1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOdontogram1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOdontogram1);
        BtnOdontogram1.setBounds(880, 220, 200, 30);

        BtnTemplatePemeriksaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTemplatePemeriksaan1.setMnemonic('4');
        BtnTemplatePemeriksaan1.setText("Riwayat SOAP");
        BtnTemplatePemeriksaan1.setToolTipText("ALt+4");
        BtnTemplatePemeriksaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTemplatePemeriksaan1.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnTemplatePemeriksaan1.setName("BtnTemplatePemeriksaan1"); // NOI18N
        BtnTemplatePemeriksaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplatePemeriksaan1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTemplatePemeriksaan1);
        BtnTemplatePemeriksaan1.setBounds(1090, 70, 210, 23);

        BtnOdontogram2.setForeground(new java.awt.Color(0, 0, 0));
        BtnOdontogram2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnOdontogram2.setMnemonic('4');
        BtnOdontogram2.setText("  SBAR IGD");
        BtnOdontogram2.setToolTipText("");
        BtnOdontogram2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnOdontogram2.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnOdontogram2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnOdontogram2.setName("BtnOdontogram2"); // NOI18N
        BtnOdontogram2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOdontogram2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOdontogram2);
        BtnOdontogram2.setBounds(880, 250, 200, 30);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026 19:25:13" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(840, 10, 160, 23);

        label28.setText("Nama Perawat/Bidan:");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(550, 40, 120, 23);

        BtnRiwayatFKTP1.setForeground(new java.awt.Color(0, 0, 0));
        BtnRiwayatFKTP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Calendar.png"))); // NOI18N
        BtnRiwayatFKTP1.setMnemonic('4');
        BtnRiwayatFKTP1.setText("Riwayat Pemberian Obat");
        BtnRiwayatFKTP1.setToolTipText("");
        BtnRiwayatFKTP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatFKTP1.setGlassColor(new java.awt.Color(255, 102, 0));
        BtnRiwayatFKTP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatFKTP1.setName("BtnRiwayatFKTP1"); // NOI18N
        BtnRiwayatFKTP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatFKTP1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnRiwayatFKTP1);
        BtnRiwayatFKTP1.setBounds(880, 190, 200, 26);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
            kd_pj=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
            kode_poli=Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Validator");
        }else if(KodeDokter1.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Nama Pemeriksa");
        }else if(TKeluhan.getText().equals("")){
            Valid.textKosong(TKeluhan,"S (SUBJECTIVE");
        }else if(diagnosa.getText().equals("")){
            Valid.textKosong(diagnosa,"DIAGNOSA"); 
        }else{
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(Tanggal.getDate());
            String tanggal = datetime.substring(0,10);
            String jam = datetime.substring(11,19);            
            if(Sequel.menyimpantf("validasi_pemeriksaan_ranap2","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",23,new String[]{
                    TNoRw.getText(),TanggalPemeriksaan.getText(),JamPemeriksaan.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TPenilaian.getText(),TindakLanjut.getText(), 
                    KodeDokter1.getText(),KodeDokter.getText(),tanggal,jam,"Validasi",diagnosa.getText(),suhu.getText(),tensi.getText(),rr.getText(),
                    nadi.getText(),spo.getText(),gcs.getText(),alergi.getText(),tb.getText(),bb.getText(),catatan.getText()
                })==true){
                /*Sequel.mengedit(
                    "pemeriksaan_sbar_balkon_ranap",
                    "no_rawat='" + TNoRw.getText() +
                    "' and tgl_perawatan='" + TanggalPemeriksaan.getText() +
                    "' and jam_rawat='" + JamPemeriksaan.getText() + "'",
                    "baca='Sudah',konfirmasi='Sudah'"
                );*/
                    tampil();
                    emptTeks();
            }  
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,diagnosa,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnBatal);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        carikeluhan.dispose();
        caripemeriksaan.dispose();
        carilaborat.dispose();
        cariobat.dispose();
        cariradiologi.dispose();
        penyakit.dispose();
        /*try {
            i=JOptionPane.showConfirmDialog(null, "Mau sekalian update status pasien sudah diperiksa ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(i==JOptionPane.YES_OPTION){
                if(Sequel.mengedittf("reg_periksa","no_rawat=?","stts=?",2,new String[]{"Sudah",TNoRw.getText()})==true){
                    Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Kembali',now(),'0000-00-00 00:00:00',now(),'0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Kembali',kembali=now()","no_rawat='"+TNoRw.getText()+"'");
                }
            }
        } catch (Exception e) {
        }*/
        dispose();
    
       
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnBatal,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                
                getData();
                
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    
                    getData();
                    
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    ChkInput.setSelected(true);
                    isForm(); 
                    
                    getData();
                    
                    
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        Valid.pindah(evt,TCari,TKeluhan);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TCari,TKeluhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }
            Valid.MyReport("rptLaporanResume.jasper","report","::[ Laporan Resume Pasien ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void MnInputDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputDiagnosaActionPerformed
//        if(TNoRw.getText().trim().equals("")){
//            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
//            TCari.requestFocus();
//        }else{
//            penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
//            penyakit.setLocationRelativeTo(internalFrame1);
//            penyakit.isCek();
//            penyakit.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",TNoRw.getText()));
//            penyakit.panelDiagnosa1.tampil();
//            penyakit.setVisible(true);
//        }
    }//GEN-LAST:event_MnInputDiagnosaActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
                    berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
                    try {
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }

                    berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    berkas.setLocationRelativeTo(internalFrame1);
                    berkas.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void MnDigitalTTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDigitalTTEActionPerformed

    }//GEN-LAST:event_MnDigitalTTEActionPerformed

    private void KodeDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KodeDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokter1ActionPerformed

    private void KodeDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokter1KeyPressed
        Valid.pindah(evt,TCari,BtnCari);
    }//GEN-LAST:event_KodeDokter1KeyPressed

    private void TanggalPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPemeriksaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPemeriksaanActionPerformed

    private void TanggalPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPemeriksaanKeyPressed

    private void JamPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JamPemeriksaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPemeriksaanActionPerformed

    private void JamPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamPemeriksaanKeyPressed

    private void suhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_suhuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_suhuKeyPressed

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeluhanKeyPressed

    private void spoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_spoKeyPressed

    private void tensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tensiKeyPressed

    private void gcsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gcsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gcsKeyPressed

    private void rrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rrKeyPressed

    private void nadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nadiKeyPressed

    private void BtnTemplatePemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplatePemeriksaanActionPerformed
       
    }//GEN-LAST:event_BtnTemplatePemeriksaanActionPerformed

    private void inputResep() {
        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        LocalTime now = LocalTime.now();
        String currentHour = now.format(DateTimeFormatter.ofPattern("HH"));
        String currentMinute = now.format(DateTimeFormatter.ofPattern("mm"));
        String currentSecond = now.format(DateTimeFormatter.ofPattern("ss"));
        // Gunakan SimpleDateFormat untuk memformat tanggal saat ini
        SimpleDateFormat tanggalFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalSekarang = tanggalFormat.format(new Date());
         resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),
                cmbDtk.getSelectedItem().toString(),KodeDokter.getText(),NamaDokter.getText(),"ralan");
        resep.isCek();
        resep.tampilobat();
        resep.setVisible(true);
    }
    
    private void inputTemplate(){
         if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else if(NamaDokter.getText().trim().equals("")||KodeDokter.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu petugas/dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenilaianKeyPressed
        Valid.pindah2(evt,TPemeriksaan,BtnSimpan);
    }//GEN-LAST:event_TPenilaianKeyPressed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void diagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_diagnosaKeyPressed

    private void DTPTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DTPTglActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTglActionPerformed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,gcs,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,BtnTemplatePemeriksaan);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void tensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tensiActionPerformed

    private void alergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_alergiKeyPressed

    private void tbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbKeyPressed

    private void bbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bbKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(205,HEIGHT));
            FormMenu.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }
    
    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
           // RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void BtnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDicomActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TNoRw.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Nomor rawat masih kosong...!!!!");
        }else{
            try {
                String noRekammedis= TNoRM.getText();
                dicomViewer.setPasien(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),true);
                dicomViewer.tampilDicomServer(Valid.SetTgl(DTPCari1.getSelectedItem()+"").replaceAll("-",""),Valid.SetTgl(DTPCari2.getSelectedItem()+"").replaceAll("-",""),TNoRM.getText(),TNoRw.getText());
                dicomViewer.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dicomViewer.setLocationRelativeTo(internalFrame1);
                dicomViewer.setVisible(true);
               // dicomViewer.isForm();
                //dicomViewer.isCek();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnDicomActionPerformed

    private void BtnHasilRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilRadiologiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPenunjang resume=new RMRiwayatPenunjang(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHasilRadiologiActionPerformed

    private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_catatanKeyPressed

    private void BtnTemplatePemberianObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplatePemberianObat2ActionPerformed
        
    }//GEN-LAST:event_BtnTemplatePemberianObat2ActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodeDokter.getText().equals("")||NamaDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(TKeluhan.getText().equals("")){
            Valid.textKosong(TKeluhan,"Keluhan utama riwayat penyakit yang postif");
        }else if(TPenilaian.getText().equals("")){
            Valid.textKosong(TPenilaian,"Assesment Medis");
        }else if(diagnosa.getText().equals("")){
            Valid.textKosong(diagnosa,"Diagnosa Utama");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KodeDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KodeDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TanggalPerawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPerawatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPerawatanActionPerformed

    private void TanggalPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPerawatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPerawatanKeyPressed

    private void BtnOdontogram1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOdontogram1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ValidasiSBAR sbar=new ValidasiSBAR(null,false);
            sbar.isCek();
            sbar.emptTeks();
            sbar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            sbar.setLocationRelativeTo(internalFrame1);
            sbar.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            sbar.tampil();
            sbar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnOdontogram1ActionPerformed

    private void BtnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSKDPActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            SuratKontrol form=new SuratKontrol(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            kode_poli=Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
            form.setNoRm(TNoRM.getText(),TPasien.getText(), kode_poli,Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),KodeDokter.getText(),NamaDokter.getText());
            form.setVisible(true);
        }
    }//GEN-LAST:event_BtnSKDPActionPerformed

    private void BtnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.isCek();
            resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ranap");
            resep.panelDiagnosa1.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_BtnDiagnosaActionPerformed

    private void BtnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResumeActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasienRanap resume=new RMDataResumePasienRanap(null,false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnResumeActionPerformed

    private void BtnAwalFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalFisioterapiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalFisioterapiActionPerformed

    private void BtnAwalMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapDewasa form=new RMPenilaianAwalMedisRanapDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisActionPerformed

    private void BtnAwalMedisKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapKandungan form=new RMPenilaianAwalMedisRanapKandungan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisKandunganActionPerformed

    private void BtnAwalMedisHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisHemodialisaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisHemodialisa form=new RMPenilaianAwalMedisHemodialisa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),"Rawat Inap");
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisHemodialisaActionPerformed

    private void BtnChecklistPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPreOperasi form=new RMChecklistPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPreOperasiActionPerformed

    private void BtnSignInSebelumAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignInSebelumAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignInSebelumAnastesi form=new RMSignInSebelumAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignInSebelumAnestesiActionPerformed

    private void BtnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTimeOutSebelumInsisiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTimeOutSebelumInsisi form=new RMTimeOutSebelumInsisi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTimeOutSebelumInsisiActionPerformed

    private void BtnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignOutSebelumMenutupLukaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignOutSebelumMenutupLuka form=new RMSignOutSebelumMenutupLuka(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignOutSebelumMenutupLukaActionPerformed

    private void BtnChecklistPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPostOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPostOperasi form=new RMChecklistPostOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPostOperasiActionPerformed

    private void BtnPenilaianPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreOperasi form=new RMPenilaianPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreOperasiActionPerformed

    private void BtnPenilaianPreAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreAnastesi form=new RMPenilaianPreAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreAnestesiActionPerformed

    private void BtnSkorAldrettePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkorAldrettePascaAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringAldrettePascaAnestesi form=new RMMonitoringAldrettePascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkorAldrettePascaAnestesiActionPerformed

    private void BtnSkorStewardPascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkorStewardPascaAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringStewardPascaAnestesi form=new RMMonitoringStewardPascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkorStewardPascaAnestesiActionPerformed

    private void BtnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPsikologActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPsikologi form=new RMPenilaianPsikologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPsikologActionPerformed

    private void BtnPerencanaanPemulanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerencanaanPemulanganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPerencanaanPemulangan form=new RMPerencanaanPemulangan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPerencanaanPemulanganActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhDewasa form=new RMPenilaianLanjutanRisikoJatuhDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhAnak form=new RMPenilaianLanjutanRisikoJatuhAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhAnakActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhLansia form=new RMPenilaianLanjutanRisikoJatuhLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianRisikoJatuhNeonatus form=new RMPenilaianRisikoJatuhNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhGeriatri form=new RMPenilaianLanjutanRisikoJatuhGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhPsikiatri form=new RMPenilaianLanjutanRisikoJatuhPsikiatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed

    private void BtnPenilaianLanjutanSkriningFungsionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanSkriningFungsionalActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanSkriningFungsional form=new RMPenilaianLanjutanSkriningFungsional(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanSkriningFungsionalActionPerformed

    private void BtnPenilaianResikoDekubitusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianResikoDekubitusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianRisikoDekubitus form=new RMPenilaianRisikoDekubitus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianResikoDekubitusActionPerformed

    private void BtnHasilPemeriksaanUSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilPemeriksaanUSGActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSG form=new RMHasilPemeriksaanUSG(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilPemeriksaanUSGActionPerformed

    private void BtnDokumentasiESWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokumentasiESWLActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilTindakanESWL form=new RMHasilTindakanESWL(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnDokumentasiESWLActionPerformed

    private void BtnCatatanObservasiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanap form=new RMDataCatatanObservasiRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapActionPerformed

    private void BtnCatatanObservasiRanapKebidananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapKebidananActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanapKebidanan form=new RMDataCatatanObservasiRanapKebidanan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapKebidananActionPerformed

    private void BtnCatatanObservasiRanapPostPartumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapPostPartumActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanapPostPartum form=new RMDataCatatanObservasiRanapPostPartum(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapPostPartumActionPerformed

    private void BtnFollowUpDBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFollowUpDBDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataFollowUpDBD form=new RMDataFollowUpDBD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnFollowUpDBDActionPerformed

    private void BtnCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanKeperawatanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanKeperawatanRanap form=new RMDataCatatanKeperawatanRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanKeperawatanActionPerformed

    private void BtnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanCekGDSActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanCekGDS form=new RMDataCatatanCekGDS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanCekGDSActionPerformed

    private void BtnPenilaianUlangNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianUlangNyeriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianUlangNyeri form=new RMPenilaianUlangNyeri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianUlangNyeriActionPerformed

    private void BtnPemantauanPEWSAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanPEWS form=new RMPemantauanPEWS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSAnakActionPerformed

    private void BtnPemantauanPEWSDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSD form=new RMPemantauanEWSD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSDewasaActionPerformed

    private void BtnPemantauanMEOWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanMEOWSActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanMEOWS form=new RMPemantauanMEOWS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanMEOWSActionPerformed

    private void BtnPemantauanEWSNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanEWSNeonatusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSNeonatus form=new RMPemantauanEWSNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanEWSNeonatusActionPerformed

    private void BtnChecklistKriteriaMasukHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukHCUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukHCU form=new RMChecklistKriteriaMasukHCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukHCUActionPerformed

    private void BtnChecklistKriteriaKeluarHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaKeluarHCUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaKeluarHCU form=new RMChecklistKriteriaKeluarHCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaKeluarHCUActionPerformed

    private void BtnChecklistKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukICU form=new RMChecklistKriteriaMasukICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukICUActionPerformed

    private void BtnChecklistKriteriaKeluarICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaKeluarICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaKeluarICU form=new RMChecklistKriteriaKeluarICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaKeluarICUActionPerformed

    private void BtnMonitoringReaksiTranfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringReaksiTranfusiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringReaksiTranfusi form=new RMDataMonitoringReaksiTranfusi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringReaksiTranfusiActionPerformed

    private void BtnSkriningNutrisiDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiDewasaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiDewasa form=new RMSkriningNutrisiDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiDewasaActionPerformed

    private void BtnSkriningNutrisiLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiLansiaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiLansia form=new RMSkriningNutrisiLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiLansiaActionPerformed

    private void BtnSkriningNutrisiAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiAnakActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiAnak form=new RMSkriningNutrisiAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setVisible(true);
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiAnakActionPerformed

    private void BtnSkriningGiziLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningGiziLanjutActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataSkriningGiziLanjut form=new RMDataSkriningGiziLanjut(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningGiziLanjutActionPerformed

    private void BtnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataAsuhanGizi form=new RMDataAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAsuhanGiziActionPerformed

    private void BtnMonitoringAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringAsuhanGizi form=new RMDataMonitoringAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringAsuhanGiziActionPerformed

    private void BtnCatatanADIMEGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanADIMEGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanADIMEGizi form=new RMCatatanADIMEGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanADIMEGiziActionPerformed

    private void BtnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKonselingFarmasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMKonselingFarmasi form=new RMKonselingFarmasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnKonselingFarmasiActionPerformed

    private void BtnInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInformasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnInformasiObatActionPerformed

    private void BtnRekonsiliasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekonsiliasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRekonsiliasiObat form=new RMRekonsiliasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRekonsiliasiObatActionPerformed

    private void BtnTransferAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTransferAntarRuangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form=new RMTransferPasienAntarRuang(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTransferAntarRuangActionPerformed

    private void BtnPengkajianRestrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPengkajianRestrainActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPengkajianRestrain form=new RMPengkajianRestrain(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPengkajianRestrainActionPerformed

    private void BtnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienTerminalActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienTerminal form=new RMPenilaianPasienTerminal(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienTerminalActionPerformed

    private void BtnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianKorbanKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianKorbanKekerasan form=new RMPenilaianKorbanKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianKorbanKekerasanActionPerformed

    private void BtnPenilaianKecemasanAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianKecemasanAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLevelKecemasanRanapAnak form=new RMPenilaianLevelKecemasanRanapAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianKecemasanAnakActionPerformed

    private void BtnPenilaianPasienPenyakitMenularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienPenyakitMenularActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienPenyakitMenular form=new RMPenilaianPasienPenyakitMenular(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienPenyakitMenularActionPerformed

    private void BtnPenilaianTambahanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanGeriatri form=new RMPenilaianTambahanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanGeriatriActionPerformed

    private void BtnPenilaianTambahanBunuhDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanBunuhDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanBunuhDiri form=new RMPenilaianTambahanBunuhDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanBunuhDiriActionPerformed

    private void BtnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanPerilakuKekerasan form=new RMPenilaianTambahanPerilakuKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed

    private void BtnPenilaianTambahanMelarikanDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanMelarikanDiri form=new RMPenilaianTambahanMelarikanDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed

    private void BtnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),
                    cmbDtk.getSelectedItem().toString(),KodeDokter.getText(),NamaDokter.getText(),"ranap");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnResepObatActionPerformed

    private void BtnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar=new DlgCopyResep(null,false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KodeDokter.getText(),Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()),"ranap");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCopyResepActionPerformed

    private void BtnPermintaanStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanStokActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPermintaanStokPasien resep=new DlgPermintaanStokPasien(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate());
                resep.isCek();
                resep.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnPermintaanStokActionPerformed

    private void BtnPermintaanResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanResepPulangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPermintaanResepPulang resep=new DlgPermintaanResepPulang(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setVisible(true);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate());
                resep.isCek();
                resep.tampil();
            }
        }
    }//GEN-LAST:event_BtnPermintaanResepPulangActionPerformed

    private void BtnInputObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInputObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                if(Sequel.cariInteger("select count(stok_obat_pasien.no_rawat) from stok_obat_pasien where stok_obat_pasien.no_rawat=? ",TNoRw.getText())>0){
                    DlgCariObat3 dlgobt=new DlgCariObat3(null,false);
                    dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPTgl.getDate());
                    dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgobt.setLocationRelativeTo(internalFrame1);
                    dlgobt.setVisible(true);
                }else{
                    DlgCariObat2 dlgobt=new DlgCariObat2(null,false);
                    dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPTgl.getDate());
                    dlgobt.isCek();
                    dlgobt.tampil();
                    dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgobt.setLocationRelativeTo(internalFrame1);
                    dlgobt.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_BtnInputObatActionPerformed

    private void BtnObatBhpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObatBhpActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm2(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ranap");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnObatBhpActionPerformed

    private void BtnBerkasDigitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBerkasDigitalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnBerkasDigitalActionPerformed

    private void BtnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanLabActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),"Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPermintaanLabActionPerformed

    private void BtnPermintaanRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanRadActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),"Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPermintaanRadActionPerformed

    private void BtnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            DlgBookingOperasi form=new DlgBookingOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),kamar,"Ranap");
            form.setVisible(true);
        }
    }//GEN-LAST:event_BtnJadwalOperasiActionPerformed

    private void BtnAwalMedisAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisAnakActionPerformed
 if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapAnak form=new RMPenilaianAwalMedisRanapAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAwalMedisAnakActionPerformed

    private void BtnTemplatePemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplatePemeriksaan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTemplatePemeriksaan1ActionPerformed

    private void BtnOdontogram2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOdontogram2ActionPerformed
          // TODO add your handling code here:
    }//GEN-LAST:event_BtnOdontogram2ActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
      //  Valid.pindah2(evt,KetDilanjutkan,ObatPulang);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnRiwayatFKTP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatFKTP1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar = new DlgCopyResep(null, false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(), TNoRM.getText(), KodeDokter.getText(), Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()), "ranap");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRiwayatFKTP1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ValidasiSOAPPRI dialog = new ValidasiSOAPPRI(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedisAnak;
    private widget.Button BtnAwalMedisHemodialisa;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnCari;
    private widget.Button BtnCatatanADIMEGizi;
    private widget.Button BtnCatatanCekGDS;
    private widget.Button BtnCatatanKeperawatan;
    private widget.Button BtnCatatanObservasiRanap;
    private widget.Button BtnCatatanObservasiRanapKebidanan;
    private widget.Button BtnCatatanObservasiRanapPostPartum;
    private widget.Button BtnChecklistKriteriaKeluarHCU;
    private widget.Button BtnChecklistKriteriaKeluarICU;
    private widget.Button BtnChecklistKriteriaMasukHCU;
    private widget.Button BtnChecklistKriteriaMasukICU;
    private widget.Button BtnChecklistPostOperasi;
    private widget.Button BtnChecklistPreOperasi;
    private widget.Button BtnCopyResep;
    private widget.Button BtnDiagnosa;
    private widget.Button BtnDicom;
    private widget.Button BtnDokter;
    private widget.Button BtnDokumentasiESWL;
    private widget.Button BtnEdit;
    private widget.Button BtnFollowUpDBD;
    private widget.Button BtnHapus;
    private widget.Button BtnHasilPemeriksaanUSG;
    private widget.Button BtnHasilRadiologi;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnInputObat;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnMonitoringReaksiTranfusi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnOdontogram1;
    private widget.Button BtnOdontogram2;
    private widget.Button BtnPemantauanEWSNeonatus;
    private widget.Button BtnPemantauanMEOWS;
    private widget.Button BtnPemantauanPEWSAnak;
    private widget.Button BtnPemantauanPEWSDewasa;
    private widget.Button BtnPengkajianRestrain;
    private widget.Button BtnPenilaianKecemasanAnak;
    private widget.Button BtnPenilaianKorbanKekerasan;
    private widget.Button BtnPenilaianLanjutanResikoJatuhAnak;
    private widget.Button BtnPenilaianLanjutanResikoJatuhDewasa;
    private widget.Button BtnPenilaianLanjutanResikoJatuhGeriatri;
    private widget.Button BtnPenilaianLanjutanResikoJatuhLansia;
    private widget.Button BtnPenilaianLanjutanResikoJatuhNeonatus;
    private widget.Button BtnPenilaianLanjutanResikoJatuhPsikiatri;
    private widget.Button BtnPenilaianLanjutanSkriningFungsional;
    private widget.Button BtnPenilaianPasienPenyakitMenular;
    private widget.Button BtnPenilaianPasienTerminal;
    private widget.Button BtnPenilaianPreAnestesi;
    private widget.Button BtnPenilaianPreOperasi;
    private widget.Button BtnPenilaianPsikolog;
    private widget.Button BtnPenilaianResikoDekubitus;
    private widget.Button BtnPenilaianTambahanBunuhDiri;
    private widget.Button BtnPenilaianTambahanGeriatri;
    private widget.Button BtnPenilaianTambahanMelarikanDiri;
    private widget.Button BtnPenilaianTambahanPerilakuKekerasan;
    private widget.Button BtnPenilaianUlangNyeri;
    private widget.Button BtnPerencanaanPemulangan;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPermintaanResepPulang;
    private widget.Button BtnPermintaanStok;
    private widget.Button BtnRekonsiliasiObat;
    private widget.Button BtnResepObat;
    private widget.Button BtnResume;
    private widget.Button BtnRiwayat;
    private widget.Button BtnRiwayatFKTP1;
    private widget.Button BtnSKDP;
    private widget.Button BtnSignInSebelumAnestesi;
    private widget.Button BtnSignOutSebelumMenutupLuka;
    private widget.Button BtnSimpan;
    private widget.Button BtnSkorAldrettePascaAnestesi;
    private widget.Button BtnSkorStewardPascaAnestesi;
    private widget.Button BtnSkriningGiziLanjut;
    private widget.Button BtnSkriningNutrisiAnak;
    private widget.Button BtnSkriningNutrisiDewasa;
    private widget.Button BtnSkriningNutrisiLansia;
    private widget.Button BtnTemplatePemberianObat2;
    private widget.Button BtnTemplatePemeriksaan;
    private widget.Button BtnTemplatePemeriksaan1;
    private widget.Button BtnTimeOutSebelumInsisi;
    private widget.Button BtnTransferAntarRuang;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox JamPemeriksaan;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeDokter1;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnDigitalTTE;
    private javax.swing.JMenuItem MnInputDiagnosa;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private widget.TextArea TKeluhan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TPenilaian;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalPemeriksaan;
    private widget.TextBox TanggalPerawatan;
    private widget.TextArea TindakLanjut;
    private widget.TextArea alergi;
    private widget.TextBox bb;
    private widget.TextArea catatan;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.TextArea diagnosa;
    private widget.TextBox gcs;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.TextBox nadi;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.TextBox rr;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.TextBox spo;
    private widget.TextBox suhu;
    private widget.TextBox tb;
    private widget.Table tbObat;
    private widget.TextBox tensi;
    // End of variables declaration//GEN-END:variables
private widget.Button BtnSkorBromagePascaAnestesi,BtnPenilaianPreInduksi,BtnHasilPemeriksaanUSGUrologi,BtnHasilPemeriksaanUSGGynecologi;

 public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                   "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,validasi_pemeriksaan_ranap2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "validasi_pemeriksaan_ranap2.tgl_perawatan,validasi_pemeriksaan_ranap2.jam_rawat,validasi_pemeriksaan_ranap2.suhu,validasi_pemeriksaan_ranap2.tensi,validasi_pemeriksaan_ranap2.nadi,validasi_pemeriksaan_ranap2.rr,validasi_pemeriksaan_ranap2.spo,validasi_pemeriksaan_ranap2.gcs,validasi_pemeriksaan_ranap2.keluhan, " +
                    "validasi_pemeriksaan_ranap2.pemeriksaan,validasi_pemeriksaan_ranap2.penilaian,validasi_pemeriksaan_ranap2.rtl,validasi_pemeriksaan_ranap2.alergi,validasi_pemeriksaan_ranap2.diagnosa,validasi_pemeriksaan_ranap2.bb,validasi_pemeriksaan_ranap2.tb,validasi_pemeriksaan_ranap2.catatan,validasi_pemeriksaan_ranap2.tgl_validasi,validasi_pemeriksaan_ranap2.jam_validasi,validasi_pemeriksaan_ranap2.nik_validator,dokter.nm_dokter,validasi_pemeriksaan_ranap2.nik,pegawai.nama " +
                    "from validasi_pemeriksaan_ranap2 inner join reg_periksa on validasi_pemeriksaan_ranap2.no_rawat=reg_periksa.no_rawat " +
                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on validasi_pemeriksaan_ranap2.nik_validator=dokter.kd_dokter "+
                    "inner join pegawai on validasi_pemeriksaan_ranap2.nik=pegawai.nik "+
                    "WHERE validasi_pemeriksaan_ranap2.tgl_perawatan between ? and ? order by validasi_pemeriksaan_ranap2.tgl_validasi DESC, validasi_pemeriksaan_ranap2.jam_validasi DESC");
                    
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,validasi_pemeriksaan_ranap2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "validasi_pemeriksaan_ranap2.tgl_perawatan,validasi_pemeriksaan_ranap2.jam_rawat,validasi_pemeriksaan_ranap2.suhu,validasi_pemeriksaan_ranap2.tensi,validasi_pemeriksaan_ranap2.nadi,validasi_pemeriksaan_ranap2.rr,validasi_pemeriksaan_ranap2.spo,validasi_pemeriksaan_ranap2.gcs,validasi_pemeriksaan_ranap2.keluhan, " +
                    "validasi_pemeriksaan_ranap2.pemeriksaan,validasi_pemeriksaan_ranap2.penilaian,validasi_pemeriksaan_ranap2.rtl,validasi_pemeriksaan_ranap2.alergi,validasi_pemeriksaan_ranap2.diagnosa,validasi_pemeriksaan_ranap2.bb,validasi_pemeriksaan_ranap2.tb,validasi_pemeriksaan_ranap2.catatan,validasi_pemeriksaan_ranap2.tgl_validasi,validasi_pemeriksaan_ranap2.jam_validasi,validasi_pemeriksaan_ranap2.nik_validator,dokter.nm_dokter,validasi_pemeriksaan_ranap2.nik,pegawai.nama "+
                    "from validasi_pemeriksaan_ranap2 inner join reg_periksa on validasi_pemeriksaan_ranap2.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on validasi_pemeriksaan_ranap2.nik_validator=dokter.kd_dokter inner join pegawai on validasi_pemeriksaan_ranap2.nik=pegawai.nik "+
                    "where validasi_pemeriksaan_ranap2.tgl_perawatan between ? and ? and "+
                    "(reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or validasi_pemeriksaan_ranap2.nik_validator like ? or "+
                    "dokter.nm_dokter like ?  or reg_periksa.no_rawat like ?) order by validasi_pemeriksaan_ranap2.tgl_validasi DESC, validasi_pemeriksaan_ranap2.jam_validasi DESC");
                    
            }
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                   
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_perawatan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nik_validator"),rs.getString("nm_dokter"),
                        rs.getString("tgl_validasi"),rs.getString("jam_validasi"),rs.getString("suhu"),rs.getString("tensi"),rs.getString("rr"),rs.getString("nadi"),
                        rs.getString("tb"),rs.getString("bb"),rs.getString("spo"),rs.getString("gcs"),rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("penilaian"),rs.getString("rtl"),
                        rs.getString("diagnosa"),rs.getString("alergi"),rs.getString("catatan"),rs.getString("nik"),rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
 
 
    public void tampil1() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,validasi_pemeriksaan_ranap2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "validasi_pemeriksaan_ranap2.tgl_perawatan,validasi_pemeriksaan_ranap2.jam_rawat,validasi_pemeriksaan_ranap2.suhu_tubuh,validasi_pemeriksaan_ranap2.tensi,validasi_pemeriksaan_ranap2.nadi,validasi_pemeriksaan_ranap2.respirasi,validasi_pemeriksaan_ranap2.spo2,validasi_pemeriksaan_ranap2.gcs,validasi_pemeriksaan_ranap2.keluhan, " +
                    "validasi_pemeriksaan_ranap2.pemeriksaan,validasi_pemeriksaan_ranap2.penilaian,validasi_pemeriksaan_ranap2.rtl,validasi_pemeriksaan_ranap2.alergi,validasi_pemeriksaan_ranap2.diagnosa,validasi_pemeriksaan_ranap2.bb,validasi_pemeriksaan_ranap2.tb,validasi_pemeriksaan_ranap2.catatan,validasi_pemeriksaan_ranap2.nip,pegawai.nama from pasien inner join reg_periksa inner join validasi_pemeriksaan_ranap2 "+
                    "on validasi_pemeriksaan_ranap2.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on validasi_pemeriksaan_ranap2.nip=pegawai.nik"+
                    "WHERE validasi_pemeriksaan_ranap2.no_rawat like ?"+
                    " order by validasi_pemeriksaan_ranap2.no_rawat");
            } /*else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.status_lanjut,validasi_pemeriksaan_ranap2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "validasi_pemeriksaan_ranap2.tgl_perawatan,validasi_pemeriksaan_ranap2.jam_rawat,validasi_pemeriksaan_ranap2.suhu_tubuh,validasi_pemeriksaan_ranap2.tensi,validasi_pemeriksaan_ranap2.nadi,validasi_pemeriksaan_ranap2.respirasi,validasi_pemeriksaan_ranap2.spo2,validasi_pemeriksaan_ranap2.gcs,validasi_pemeriksaan_ranap2.keluhan, " +
                    "validasi_pemeriksaan_ranap2.pemeriksaan,validasi_pemeriksaan_ranap2.penilaian,validasi_pemeriksaan_ranap2.rtl,validasi_pemeriksaan_ranap2.alergi,validasi_pemeriksaan_ranap2.diagnosa,validasi_pemeriksaan_ranap2.bb,validasi_pemeriksaan_ranap2.tb,validasi_pemeriksaan_ranap2.catatan,validasi_pemeriksaan_ranap2.nip,pegawai.nama from pasien inner join reg_periksa inner join validasi_pemeriksaan_ranap2 "+
                    "on validasi_pemeriksaan_ranap2.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on validasi_pemeriksaan_ranap2.nip=pegawai.nik LEFT JOIN validasi_pemeriksaan_ranap2 ON validasi_pemeriksaan_ranap2.no_rawat = validasi_pemeriksaan_ranap2.no_rawat AND validasi_pemeriksaan_ranap2.tgl_perawatan = validasi_pemeriksaan_ranap2.tgl_perawatan AND validasi_pemeriksaan_ranap2.jam_rawat = validasi_pemeriksaan_ranap2.jam_rawat where "+
                    "validasi_pemeriksaan_ranap2.no_rawat like ? and ISNULL(validasi_pemeriksaan_ranap2.status_validasi) order by validasi_pemeriksaan_ranap2.no_rawat desc");
            }*/
            try {
                if(!TNoRw.getText().equals("")){
                    ps.setString(1,"%"+TNoRw.getText()+"%");
                }//else{
                    //ps.setString(1,"%"+TCari.getText()+"%");
                //}   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_registrasi"),rs.getString("status_lanjut"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("suhu_tubuh"),rs.getString("tensi"),rs.getString("respirasi"),rs.getString("nadi"),
                        rs.getString("tb"),rs.getString("bb"),rs.getString("spo2"),rs.getString("gcs"),rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("penilaian"),rs.getString("rtl"),
                        rs.getString("diagnosa"),rs.getString("alergi"),rs.getString("catatan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
        konsul();
    }

    public void emptTeks() {
        KodeDokter1.setText("");
        NamaDokter1.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TindakLanjut.setText("");
        diagnosa.setForeground(Color.GRAY);
        diagnosa.setText("Penulisan Diagnosa jika lebih dari 1 harap menggunakan pemisah ',' (koma)");
        suhu.setText("");
        tensi.setText("");
        rr.setText("");
        nadi.setText("");
        spo.setText("");
        gcs.setText("");
        bb.setText("");
        tb.setText("");
        Tanggal.setDate(new Date());
        TNoRw.requestFocus();
        KodeDokter1.requestFocus();
    } 

    private void getData() {
    if(tbObat.getSelectedRow()!= -1){
    TanggalPerawatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
    TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
    TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
    TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());  
    KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());  
    NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
    TanggalPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());  
    JamPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
    
    
    suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());   
    tensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());   
    rr.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
    nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
    tb.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
    bb.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
    
    spo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());   
    gcs.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());   
    //TKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());   
    TKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());  
    TPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());  
    TPenilaian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
    TindakLanjut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
    diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
    alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
    catatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
    KodeDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());  
    NamaDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());    
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
         if(Sequel.cariInteger("select count(no_rawat) from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"' ")>0){
            TanggalPemeriksaan.setText(Sequel.cariIsi("select tgl_perawatan from pemeriksaan_ranap where no_rawat=?  order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            JamPemeriksaan.setText(Sequel.cariIsi("select jam_rawat from pemeriksaan_ranap where no_rawat=?  order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            suhu.setText(Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ranap where no_rawat=?  order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            tensi.setText(Sequel.cariIsi("select tensi from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));            
            nadi.setText(Sequel.cariIsi("select nadi from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            rr.setText(Sequel.cariIsi("select respirasi from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            spo.setText(Sequel.cariIsi("select spo2 from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            gcs.setText(Sequel.cariIsi("select gcs from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            TKeluhan.setText(Sequel.cariIsi("select keluhan from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            TPemeriksaan.setText(Sequel.cariIsi("select pemeriksaan from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            //TPenilaian.setText(Sequel.cariIsi("select penilaian from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            //TindakLanjut.setText(Sequel.cariIsi("select rtl from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            KodeDokter1.setText(Sequel.cariIsi("select nip from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            NamaDokter1.setText(Sequel.cariIsi("select nama from pegawai where nik=?",KodeDokter1.getText()));
            alergi.setText(Sequel.cariIsi("select alergi from  pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            tb.setText(Sequel.cariIsi("select tinggi from  pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            bb.setText(Sequel.cariIsi("select berat from  pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc, jam_rawat desc limit 1",TNoRw.getText()));
            catatan.setText(Sequel.cariIsi("select catatan from  catatan_pasien where no_rkm_medis=?",TNoRM.getText()));
           // TPenilaian.setText(Sequel.cariIsi("select diagnosis from  penilaian_medis_ranap_anak where no_rawat=?",TNoRw.getText()));
          //  diagnosa.setText(Sequel.cariIsi("select diagnosis from  penilaian_medis_ranap_anak where no_rawat=?",TNoRw.getText()));
           // TindakLanjut.setText(Sequel.cariIsi("select tata from  penilaian_medis_ranap_anak where no_rawat=?",TNoRw.getText()));      
        } 
            String diag="";
            String tata="";

            if(Sequel.cariInteger("select count(no_rawat) from penilaian_medis_ranap where no_rawat='"+TNoRw.getText()+"' ")>0){

                diag=Sequel.cariIsi(
                "select diagnosis from penilaian_medis_ranap where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());

                tata=Sequel.cariIsi(
                "select tata from penilaian_medis_ranap where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());

            }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_medis_ranap_anak where no_rawat='"+TNoRw.getText()+"' ")>0){

                diag=Sequel.cariIsi(
                "select diagnosis from penilaian_medis_ranap_anak where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());

                tata=Sequel.cariIsi(
                "select tata from penilaian_medis_ranap_anak where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());

            }else if(Sequel.cariInteger("select count(no_rawat) from penilaian_medis_ranap_kandungan where no_rawat='"+TNoRw.getText()+"' ")>0){

                diag=Sequel.cariIsi(
                "select diagnosis from penilaian_medis_ranap_kandungan where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());

                tata=Sequel.cariIsi(
                "select tata from penilaian_medis_ranap_kandungan where no_rawat=? order by tanggal desc limit 1",
                TNoRw.getText());
            }

            diagnosa.setText(diag);
            TindakLanjut.setText(tata);
            }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl2, String norm) {
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TCari.setText(norwt);
        
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        TNoRw.requestFocus();
    }
    
    public void setNRm(String norwt,Date tanggal, String jam,String menit,String detik,String kodedokter,String namadokter,String status) {
       
         TNoRw.setText(norwt);
        Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien,' (',pasien.umur,')') from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        DTPTgl.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);           
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
        
        KodeDokter.setText(kodedokter);       
        NamaDokter.setText(namadokter);
        
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-330));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        tinggi=0;
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        
        BtnDiagnosa.setVisible(akses.getdiagnosa_pasien());    
        if(akses.getdiagnosa_pasien()==true){
            tinggi=tinggi+24;
        }       
        BtnRiwayat.setVisible(akses.getresume_pasien()); 
        if(akses.getresume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnResepObat.setVisible(akses.getresep_dokter());
        BtnCopyResep.setVisible(akses.getresep_dokter());
        if(akses.getresep_dokter()==true){
            tinggi=tinggi+48;
        }
        BtnPermintaanStok.setVisible(akses.getpermintaan_stok_obat_pasien());
        if(akses.getpermintaan_stok_obat_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnObatBhp.setVisible(akses.getberi_obat());  
        BtnInputObat.setVisible(akses.getberi_obat());  
        if(akses.getberi_obat()==true){
            tinggi=tinggi+48;
        }
        BtnPermintaanLab.setVisible(akses.getpermintaan_lab());   
        if(akses.getpermintaan_lab()==true){
            tinggi=tinggi+24;
        }
        BtnBerkasDigital.setVisible(akses.getberkas_digital_perawatan()); 
        if(akses.getberkas_digital_perawatan()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanRad.setVisible(akses.getpermintaan_radiologi());  
        if(akses.getpermintaan_radiologi()==true){
            tinggi=tinggi+24;
        }
        
        BtnSKDP.setVisible(akses.getskdp_bpjs());   
        if(akses.getskdp_bpjs()==true){
            tinggi=tinggi+24;
        }
       
        BtnResume.setVisible(akses.getdata_resume_pasien());
        if(akses.getdata_resume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if(akses.getasuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if(akses.getmonitoring_asuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnJadwalOperasi.setVisible(akses.getbooking_operasi());   
        if(akses.getbooking_operasi()==true){
            tinggi=tinggi+24;
        }
       
        BtnAwalMedis.setVisible(akses.getpenilaian_awal_medis_ranap());   
        if(akses.getpenilaian_awal_medis_ranap()==true){
            tinggi=tinggi+24;
        }
        
        BtnAwalMedisAnak.setVisible(akses.getpenilaian_awal_medis_ralan_anak());   
        if(akses.getpenilaian_awal_medis_ralan_anak()==true){
            tinggi=tinggi+24;
        }
        
        BtnAwalMedisKandungan.setVisible(akses.getpenilaian_awal_medis_ranap_kebidanan());   
        if(akses.getpenilaian_awal_medis_ranap_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalFisioterapi.setVisible(akses.getpenilaian_fisioterapi());   
        if(akses.getpenilaian_fisioterapi()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanResepPulang.setVisible(akses.getpermintaan_resep_pulang());   
        if(akses.getpermintaan_resep_pulang()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanap.setVisible(akses.getcatatan_observasi_ranap());   
        if(akses.getcatatan_observasi_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanapKebidanan.setVisible(akses.getcatatan_observasi_ranap_kebidanan());   
        if(akses.getcatatan_observasi_ranap_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanapPostPartum.setVisible(akses.getcatatan_observasi_ranap_postpartum());   
        if(akses.getcatatan_observasi_ranap_postpartum()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPsikolog.setVisible(akses.getpenilaian_psikologi()); 
        if(akses.getpenilaian_psikologi()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanKeperawatan.setVisible(akses.getcatatan_keperawatan_ranap()); 
        if(akses.getcatatan_keperawatan_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanPEWSAnak.setVisible(akses.getpemantauan_pews_anak()); 
        if(akses.getpemantauan_pews_anak()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreOperasi.setVisible(akses.getpenilaian_pre_operasi()); 
        if(akses.getpenilaian_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreAnestesi.setVisible(akses.getpenilaian_pre_anestesi()); 
        if(akses.getpenilaian_pre_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPerencanaanPemulangan.setVisible(akses.getperencanaan_pemulangan()); 
        if(akses.getperencanaan_pemulangan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhDewasa.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhAnak.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_anak()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_anak()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanGeriatri.setVisible(akses.getpenilaian_tambahan_pasien_geriatri()); 
        if(akses.getpenilaian_tambahan_pasien_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiDewasa.setVisible(akses.getskrining_nutrisi_dewasa()); 
        if(akses.getskrining_nutrisi_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiLansia.setVisible(akses.getskrining_nutrisi_lansia()); 
        if(akses.getskrining_nutrisi_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiAnak.setVisible(akses.getskrining_nutrisi_anak()); 
        if(akses.getskrining_nutrisi_anak()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningGiziLanjut.setVisible(akses.getskrining_gizi()); 
        if(akses.getskrining_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSG.setVisible(akses.gethasil_pemeriksaan_usg()); 
        if(akses.gethasil_pemeriksaan_usg()==true){
            tinggi=tinggi+24;
        }
        BtnKonselingFarmasi.setVisible(akses.getkonseling_farmasi()); 
        if(akses.getkonseling_farmasi()==true){
            tinggi=tinggi+24;
        }
        BtnInformasiObat.setVisible(akses.getpelayanan_informasi_obat()); 
        if(akses.getpelayanan_informasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnTransferAntarRuang.setVisible(akses.gettransfer_pasien_antar_ruang()); 
        if(akses.gettransfer_pasien_antar_ruang()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanCekGDS.setVisible(akses.getcatatan_cek_gds()); 
        if(akses.getcatatan_cek_gds()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPreOperasi.setVisible(akses.getchecklist_pre_operasi()); 
        if(akses.getchecklist_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnSignInSebelumAnestesi.setVisible(akses.getsignin_sebelum_anestesi()); 
        if(akses.getsignin_sebelum_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnTimeOutSebelumInsisi.setVisible(akses.gettimeout_sebelum_insisi()); 
        if(akses.gettimeout_sebelum_insisi()==true){
            tinggi=tinggi+24;
        }
        BtnSignOutSebelumMenutupLuka.setVisible(akses.getsignout_sebelum_menutup_luka()); 
        if(akses.getsignout_sebelum_menutup_luka()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPostOperasi.setVisible(akses.getchecklist_post_operasi()); 
        if(akses.getchecklist_post_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnRekonsiliasiObat.setVisible(akses.getrekonsiliasi_obat()); 
        if(akses.getrekonsiliasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienTerminal.setVisible(akses.getpenilaian_pasien_terminal()); 
        if(akses.getpenilaian_pasien_terminal()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringReaksiTranfusi.setVisible(akses.getmonitoring_reaksi_tranfusi()); 
        if(akses.getmonitoring_reaksi_tranfusi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianKorbanKekerasan.setVisible(akses.getpenilaian_korban_kekerasan()); 
        if(akses.getpenilaian_korban_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhLansia.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienPenyakitMenular.setVisible(akses.getpenilaian_pasien_penyakit_menular()); 
        if(akses.getpenilaian_pasien_penyakit_menular()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanPEWSDewasa.setVisible(akses.getpemantauan_pews_dewasa()); 
        if(akses.getpemantauan_pews_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanBunuhDiri.setVisible(akses.getpenilaian_tambahan_bunuh_diri()); 
        if(akses.getpenilaian_tambahan_bunuh_diri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanPerilakuKekerasan.setVisible(akses.getpenilaian_tambahan_perilaku_kekerasan()); 
        if(akses.getpenilaian_tambahan_perilaku_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanMelarikanDiri.setVisible(akses.getpenilaian_tambahan_beresiko_melarikan_diri()); 
        if(akses.getpenilaian_tambahan_beresiko_melarikan_diri()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanMEOWS.setVisible(akses.getpemantauan_meows_obstetri()); 
        if(akses.getpemantauan_meows_obstetri()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanADIMEGizi.setVisible(akses.getcatatan_adime_gizi()); 
        if(akses.getcatatan_adime_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukHCU.setVisible(akses.getchecklist_kriteria_masuk_hcu()); 
        if(akses.getchecklist_kriteria_masuk_hcu()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaKeluarHCU.setVisible(akses.getchecklist_kriteria_keluar_hcu()); 
        if(akses.getchecklist_kriteria_keluar_hcu()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianResikoDekubitus.setVisible(akses.getpenilaian_risiko_dekubitus()); 
        if(akses.getpenilaian_risiko_dekubitus()==true){
            tinggi=tinggi+24;
        }
        BtnDokumentasiESWL.setVisible(akses.gethasil_tindakan_eswl()); 
        if(akses.gethasil_tindakan_eswl()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukICU.setVisible(akses.getchecklist_kriteria_masuk_icu()); 
        if(akses.getchecklist_kriteria_masuk_icu()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaKeluarICU.setVisible(akses.getchecklist_kriteria_keluar_icu()); 
        if(akses.getchecklist_kriteria_keluar_icu()==true){
            tinggi=tinggi+24;
        }
        BtnFollowUpDBD.setVisible(akses.getfollow_up_dbd()); 
        if(akses.getfollow_up_dbd()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhNeonatus.setVisible(akses.getpenilaian_risiko_jatuh_neonatus()); 
        if(akses.getpenilaian_risiko_jatuh_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhGeriatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanEWSNeonatus.setVisible(akses.getpemantauan_ews_neonatus()); 
        if(akses.getpemantauan_ews_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisHemodialisa.setVisible(akses.getpenilaian_medis_ralan_hemodialisa()); 
        if(akses.getpenilaian_medis_ralan_hemodialisa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianKecemasanAnak.setVisible(akses.getpenilaian_level_kecemasan_ranap_anak()); 
        if(akses.getpenilaian_level_kecemasan_ranap_anak()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanSkriningFungsional.setVisible(akses.getpenilaian_lanjutan_skrining_fungsional()); 
        if(akses.getpenilaian_lanjutan_skrining_fungsional()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianUlangNyeri.setVisible(akses.getpenilaian_ulang_nyeri()); 
        if(akses.getpenilaian_ulang_nyeri()==true){
            tinggi=tinggi+24;
        }
        BtnPengkajianRestrain.setVisible(akses.getpengkajian_restrain()); 
        if(akses.getpengkajian_restrain()==true){
            tinggi=tinggi+24;
        }
        
        BtnSkorAldrettePascaAnestesi.setVisible(akses.getskor_aldrette_pasca_anestesi()); 
        if(akses.getskor_aldrette_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnSkorStewardPascaAnestesi.setVisible(akses.getskor_steward_pasca_anestesi()); 
        if(akses.getskor_steward_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnSkorBromagePascaAnestesi.setVisible(akses.getskor_bromage_pasca_anestesi()); 
        if(akses.getskor_bromage_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreInduksi.setVisible(akses.getpenilaian_pre_induksi()); 
        if(akses.getpenilaian_pre_induksi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSGUrologi.setVisible(akses.gethasil_usg_urologi()); 
        if(akses.gethasil_usg_urologi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSGGynecologi.setVisible(akses.gethasil_usg_gynecologi()); 
        if(akses.gethasil_usg_gynecologi()==true){
            tinggi=tinggi+24;
        }
        FormMenu.setPreferredSize(new Dimension(195,(tinggi+10)));
        
        // Cek Jika Bukan Akun Dokter
          if(akses.getjml2()>=1){
            KodeDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KodeDokter.setText(akses.getkode());
            NamaDokter.setText(dokter.tampil3(KodeDokter.getText()));
            if(NamaDokter.getText().equals("")){
                KodeDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
            }
        }   

}

        private void konsul() {
            try {
    PreparedStatement ps = koneksi.prepareStatement(
        "select kd_dokter, kd_dokter_dikonsuli, uraian_konsultasi " +
        "from konsultasi_medik_ranap where no_rawat=?"
    );
    ps.setString(1, TNoRw.getText());
    ResultSet rs = ps.executeQuery();

    String hasil = "";

    while(rs.next()){
        hasil += "Dokter: " + rs.getString("kd_dokter") +
                 " -> Konsul ke: " + rs.getString("kd_dokter_dikonsuli") +
                 "\nUraian: " + rs.getString("uraian_konsultasi") + "\n\n";
    }

    JOptionPane.showMessageDialog(null, hasil);

} catch (Exception e) {
    System.out.println("Error : " + e);
}       
        }
    private void inputObat() {
        DlgCariObat dlgobt=new DlgCariObat(null,false);
        dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
        dlgobt.isCek();
        dlgobt.setDokter(KodeDokter.getText(),NamaDokter.getText());
        dlgobt.tampilobat();
        dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgobt.setLocationRelativeTo(internalFrame1);
        dlgobt.setVisible(true);
    }
    
    /*private void BtnSkorBromagePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringBromagePascaAnestesi form=new RMMonitoringBromagePascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnPenilaianPreInduksiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreInduksi form=new RMPenilaianPreInduksi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilPemeriksaanUSGUrologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGUrologi form=new RMHasilPemeriksaanUSGUrologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }  
    
    private void BtnHasilPemeriksaanUSGGynecologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGGynecologi form=new RMHasilPemeriksaanUSGGynecologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } */
    
   private void BtnHasilPemeriksaanUSGGynecologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGGynecologi form=new RMHasilPemeriksaanUSGGynecologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
   
   private void BtnHasilPemeriksaanUSGUrologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGUrologi form=new RMHasilPemeriksaanUSGUrologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }   
   
    private void BtnPenilaianPreInduksiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreInduksi form=new RMPenilaianPreInduksi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }  
    
     private void BtnSkorBromagePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringBromagePascaAnestesi form=new RMMonitoringBromagePascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }   
     
    private void initRawatInap(){
        BtnSkorBromagePascaAnestesi = new widget.Button();
        BtnSkorBromagePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkorBromagePascaAnestesi.setText("Skor Bromage Pasca Anestesi");
        BtnSkorBromagePascaAnestesi.setFocusPainted(false);
        BtnSkorBromagePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkorBromagePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorBromagePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorBromagePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorBromagePascaAnestesi.setName("BtnSkorBromagePascaAnestesi"); 
        BtnSkorBromagePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorBromagePascaAnestesi.setRoundRect(false);
        BtnSkorBromagePascaAnestesi.addActionListener(this::BtnSkorBromagePascaAnestesiActionPerformed);
        
        BtnPenilaianPreInduksi = new widget.Button();
        BtnPenilaianPreInduksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPreInduksi.setText("Penilaian Pre Induksi");
        BtnPenilaianPreInduksi.setFocusPainted(false);
        BtnPenilaianPreInduksi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPreInduksi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreInduksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreInduksi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreInduksi.setName("Penilaian Pre Induksi"); 
        BtnPenilaianPreInduksi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreInduksi.setRoundRect(false);
        BtnPenilaianPreInduksi.addActionListener(this::BtnPenilaianPreInduksiActionPerformed);
        
        BtnHasilPemeriksaanUSGUrologi = new widget.Button();
        BtnHasilPemeriksaanUSGUrologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGUrologi.setText("Hasil USG Urologi");
        BtnHasilPemeriksaanUSGUrologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGUrologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGUrologi.setName("BtnHasilPemeriksaanUSGUrologi"); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGUrologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGUrologi.addActionListener(this::BtnHasilPemeriksaanUSGUrologiActionPerformed);
        
        BtnHasilPemeriksaanUSGGynecologi = new widget.Button();
        BtnHasilPemeriksaanUSGGynecologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGGynecologi.setText("Hasil USG Gynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGGynecologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGGynecologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGGynecologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGGynecologi.setName("BtnHasilPemeriksaanUSGGynecologi"); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGGynecologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGGynecologi.addActionListener(this::BtnHasilPemeriksaanUSGGynecologiActionPerformed);
        
        FormMenu.add(BtnRiwayat);
        FormMenu.add(BtnResepObat);
        FormMenu.add(BtnCopyResep);
        FormMenu.add(BtnPermintaanStok);
        FormMenu.add(BtnPermintaanResepPulang);
        FormMenu.add(BtnInputObat);
        FormMenu.add(BtnObatBhp);
        FormMenu.add(BtnBerkasDigital);
        FormMenu.add(BtnPermintaanLab);
        FormMenu.add(BtnPermintaanRad);
        FormMenu.add(BtnJadwalOperasi);
        FormMenu.add(BtnSKDP);
     //   FormMenu.add(BtnRujukKeluar);
        FormMenu.add(BtnDiagnosa);
        FormMenu.add(BtnResume);
      //  FormMenu.add(BtnAwalKeperawatanUmum);
      //  FormMenu.add(BtnAwalKeperawatanKandungan);
        FormMenu.add(BtnAwalFisioterapi);
        FormMenu.add(BtnAwalMedis);
        FormMenu.add(BtnAwalMedisAnak);
        FormMenu.add(BtnAwalMedisKandungan);
        FormMenu.add(BtnAwalMedisHemodialisa);
        FormMenu.add(BtnPenilaianPreInduksi);
        FormMenu.add(BtnChecklistPreOperasi);
        FormMenu.add(BtnSignInSebelumAnestesi);
        FormMenu.add(BtnTimeOutSebelumInsisi);
        FormMenu.add(BtnSignOutSebelumMenutupLuka);
        FormMenu.add(BtnChecklistPostOperasi);
        FormMenu.add(BtnPenilaianPreOperasi);
        FormMenu.add(BtnPenilaianPreAnestesi);
        FormMenu.add(BtnSkorAldrettePascaAnestesi);
        FormMenu.add(BtnSkorStewardPascaAnestesi);
        FormMenu.add(BtnSkorBromagePascaAnestesi);
        FormMenu.add(BtnPenilaianPsikolog);
        FormMenu.add(BtnPerencanaanPemulangan);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhDewasa);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhAnak);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhLansia);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhNeonatus);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhGeriatri);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhPsikiatri);
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);
        FormMenu.add(BtnPenilaianResikoDekubitus);
        FormMenu.add(BtnHasilPemeriksaanUSG);
        FormMenu.add(BtnHasilPemeriksaanUSGUrologi);
        FormMenu.add(BtnHasilPemeriksaanUSGGynecologi);
        FormMenu.add(BtnDokumentasiESWL);
     //   FormMenu.add(BtnCatatanPersalinan);
     //   FormMenu.add(BtnCatatan);
        FormMenu.add(BtnCatatanObservasiRanap);
        FormMenu.add(BtnCatatanObservasiRanapKebidanan);
        FormMenu.add(BtnCatatanObservasiRanapPostPartum);
        FormMenu.add(BtnFollowUpDBD);
        FormMenu.add(BtnCatatanKeperawatan);
        FormMenu.add(BtnCatatanCekGDS);
        FormMenu.add(BtnPenilaianUlangNyeri);
        FormMenu.add(BtnPemantauanPEWSAnak);
        FormMenu.add(BtnPemantauanPEWSDewasa);
        FormMenu.add(BtnPemantauanMEOWS);
        FormMenu.add(BtnPemantauanEWSNeonatus);
        FormMenu.add(BtnChecklistKriteriaMasukHCU);
        FormMenu.add(BtnChecklistKriteriaKeluarHCU);
        FormMenu.add(BtnChecklistKriteriaMasukICU);
        FormMenu.add(BtnChecklistKriteriaKeluarICU);
        FormMenu.add(BtnMonitoringReaksiTranfusi);
        FormMenu.add(BtnSkriningNutrisiDewasa);
        FormMenu.add(BtnSkriningNutrisiLansia);
        FormMenu.add(BtnSkriningNutrisiAnak);
        FormMenu.add(BtnSkriningGiziLanjut);
        FormMenu.add(BtnAsuhanGizi);
        FormMenu.add(BtnMonitoringAsuhanGizi);
        FormMenu.add(BtnCatatanADIMEGizi);
        FormMenu.add(BtnKonselingFarmasi);
        FormMenu.add(BtnInformasiObat);
        FormMenu.add(BtnRekonsiliasiObat);
        FormMenu.add(BtnTransferAntarRuang);
        FormMenu.add(BtnPengkajianRestrain);
        FormMenu.add(BtnPenilaianPasienTerminal);
        FormMenu.add(BtnPenilaianKorbanKekerasan);
        FormMenu.add(BtnPenilaianKecemasanAnak);
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);
        FormMenu.add(BtnPenilaianTambahanGeriatri);
        FormMenu.add(BtnPenilaianTambahanBunuhDiri);
        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);
        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);
    }
    
    private void inputKamar() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setstatus(true);
        DlgKamarInap dlgki=new DlgKamarInap(null,false);
        dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgki.setLocationRelativeTo(internalFrame1);
        dlgki.emptTeks();
        dlgki.isCek();
        dlgki.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());  
        dlgki.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    
    
    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRalan perawatan=new DlgJnsPerawatanRalan(null,false);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }    
    
    /*private void BtnTemplatePemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else if(NamaDokter1.getText().trim().equals("")||KodeDokter1.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu petugas/dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            soapterakhir.setNoRM(TNoRM.getText(),KodeDokter1.getText(),"Ralan");
            soapterakhir.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            soapterakhir.setLocationRelativeTo(internalFrame1);
            soapterakhir.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }  */
    
     private void ganti() {
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(Tanggal.getDate());

            String tanggal = datetime.substring(0,10);
            String jam = datetime.substring(11,19);
        if(Sequel.mengedittf("validasi_pemeriksaan_ranap2","no_rawat=? and tgl_validasi=? and jam_validasi=?","no_rawat=?,tgl_perawatan=?,jam_rawat=?,keluhan=?,pemeriksaan=?,penilaian=?,rtl=?,nik=?,nik_validator=?,tgl_validasi=?,jam_validasi=?,status_validasi=?,diagnosa=?,suhu=?,tensi=?,rr=?,nadi=?,spo=?,gcs=?,alergi=?,tb=?,bb=?,catatan=?",26,new String[]{
                TNoRw.getText(),TanggalPerawatan.getText(),JamPemeriksaan.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TPenilaian.getText(),TindakLanjut.getText(), 
                    KodeDokter1.getText(),KodeDokter.getText(),tanggal,jam,"Validasi",diagnosa.getText(),suhu.getText(),tensi.getText(),rr.getText(),
                    nadi.getText(),spo.getText(),gcs.getText(),alergi.getText(),tb.getText(),bb.getText(),catatan.getText(),
                
                tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
                
            })==true){
                tampil();
               emptTeks();
           
        }
    }
     
     private void hapus() {
        if(Sequel.queryu2tf("delete from validasi_pemeriksaan_ranap2 where no_rawat=? and tgl_validasi=? and jam_validasi=?",3,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),
            tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),
            tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
     
     public void SetPj(String KodePj){
        this.kd_pj=KodePj;
    }
     
     public void setKamar(String kamar) {
        this.kamar=kamar;
    }
    
    public void setJenisBayar(String jenisbayar) {
        this.jenisbayar=jenisbayar;
    }
    
    private void placeholderDiagnosa(){

    diagnosa.setText("Penulisan Diagnosa jika lebih dari 1 harap menggunakan pemisah ',' (koma)");
    diagnosa.setForeground(Color.GRAY);

    diagnosa.addFocusListener(new java.awt.event.FocusAdapter() {

        public void focusGained(java.awt.event.FocusEvent evt) {
            if(diagnosa.getText().equals("Penulisan Diagnosa jika lebih dari 1 harap menggunakan pemisah ',' (koma)")){
                diagnosa.setText("");
                diagnosa.setForeground(Color.BLACK);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if(diagnosa.getText().trim().equals("")){
                diagnosa.setText("Penulisan Diagnosa jika lebih dari 1 harap menggunakan pemisah ',' (koma)");
                diagnosa.setForeground(Color.GRAY);
            }
        }

    });
}
    
    
}
