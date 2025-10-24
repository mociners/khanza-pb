package permintaan;

import bridging.BPJSSPRI;
import bridging.koneksiDBWa;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import keuangan.DlgKamar;
import laporan.DlgCariPenyakit;
import simrskhanza.DlgKamarInap;
import rekammedis.RMRiwayatPerawatan;
import surat.SuratPersetujuanRawatInap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dosen
 */
public class DlgPermintaanRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private Connection koneksiwa;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,nilai_detik,bookingbaru=0;
    private String alarm="",nol_detik,detik,sql="",finger="",notifwaadmisi="",idgroupwaadmisi="",pesan="",tanggaljamkirim="",petugaswa="";
    private DlgKamar kamar=new DlgKamar(null,false);
    private boolean aktif=false;
    private BackgroundMusic music;
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private DlgCariDokter dokterdpjp=new DlgCariDokter(null,false);
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPermintaanRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSPRI();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat", "No.RM", "Nama Pasien", "J.K.", "Umur", "No.Telp", "Cara Bayar", 
                "Asal Poli/Unit", "Dokter Perujuk", "Dokter DPJP", "Tanggal", "Ruang Diminta", 
                "Diagnosa Awal", "Indikasi Rawat Inap", "KodeDokter", "Kode DPJP"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) { // Total kolom sekarang 16
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) { column.setPreferredWidth(105); } 
            else if (i == 1) { column.setPreferredWidth(70); } 
            else if (i == 2) { column.setPreferredWidth(150); } 
            else if (i == 3) { column.setPreferredWidth(25); } 
            else if (i == 4) { column.setPreferredWidth(40); } 
            else if (i == 5) { column.setPreferredWidth(90); } 
            else if (i == 6) { column.setPreferredWidth(120); } 
            else if (i == 7) { column.setPreferredWidth(130); } 
            else if (i == 8) { column.setPreferredWidth(160); } 
            else if (i == 9) { column.setPreferredWidth(160); } // DPJP
            else if (i == 10) { column.setPreferredWidth(65); } // Tanggal
            else if (i == 11) { column.setPreferredWidth(150); } // Ruang Diminta
            else if (i == 12) { column.setPreferredWidth(150); } // Diagnosa
            else if (i == 13) { column.setPreferredWidth(300); } // Catatan
            else if (i == 14) { column.setMinWidth(0); column.setMaxWidth(0); } // KodeDokter (Hidden)
            else if (i == 15) { column.setMinWidth(0); column.setMaxWidth(0); } // Kode DPJP (Hidden)
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        NoRw.setDocument(new batasInput((byte)17).getKata(NoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        jTextArea1.setDocument(new batasInput((byte)50).getKata(jTextArea1));
        Diagnosa.setDocument(new batasInput((byte)50).getKata(Diagnosa));
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
        
        dokterdpjp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokterdpjp.getTable().getSelectedRow()!= -1){
                    KdDPJP.setText(dokterdpjp.getTable().getValueAt(dokterdpjp.getTable().getSelectedRow(),0).toString());
                    NmDPJP.setText(dokterdpjp.getTable().getValueAt(dokterdpjp.getTable().getSelectedRow(),1).toString());
                    KdDPJP.requestFocus();
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
        
        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kamar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if( penyakit.getTable().getSelectedRow()!= -1){ 
                    if((penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString()).length()<50){
                        Diagnosa.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    }else{
                        Diagnosa.setText((penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString()).substring(0,50));
                    }   
                }  
                Diagnosa.requestFocus();
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
        
        try {
            alarm=koneksiDB.ALARMPERMINTAANRANAP();
        } catch (Exception e) {
            alarm="no";
        }
        
        ChkInput.setSelected(false);
        isForm();
        
        if(alarm.equals("yes")){
            jam();
        }
        
        try {
            notifwaadmisi = koneksiDB.NOTIFWAADMISI();
            idgroupwaadmisi = koneksiDB.IDGROUPWAADMISI();
        } catch (Exception e) {
            notifwaadmisi = "no";
            idgroupwaadmisi = "no";
        } 
        
        ChkAccor.setSelected(false);
        isMenu();
    }
 
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        KdDokter = new widget.TextBox();
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
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        jLabel15 = new widget.Label();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        NoRw = new widget.TextBox();
        NmPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel10 = new widget.Label();
        NoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel8 = new widget.Label();
        Poli = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel11 = new widget.Label();
        Dokter = new widget.TextBox();
        jLabel12 = new widget.Label();
        Diagnosa = new widget.TextBox();
        CaraBayar = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        btnDiagnosa = new widget.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnKamarInap = new widget.Button();
        BtnRiwayatPasien = new widget.Button();
        BtnSuratPermintaan = new widget.Button();
        BtnSuratPRI = new widget.Button();
        BtnPersetujuanRanap = new widget.Button();

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
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
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(370, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

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
        panelGlass10.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Menunggu Masuk Rawat Inap");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(195, 23));
        panelCari.add(R1);

        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(40, 23));
        panelCari.add(jLabel15);

        buttonGroup1.add(R2);
        R2.setText("Sudah Masuk Rawat Inap :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(165, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-09-2025" }));
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
        panelCari.add(DTPCari1);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(jLabel25);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-09-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari2ItemStateChanged(evt);
            }
        });
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 310));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 77));
        FormInput.setLayout(null);

        NoRw.setEditable(false);
        NoRw.setHighlighter(null);
        NoRw.setName("NoRw"); // NOI18N
        NoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRwKeyPressed(evt);
            }
        });
        FormInput.add(NoRw);
        NoRw.setBounds(73, 10, 125, 23);

        NmPasien.setEditable(false);
        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N
        FormInput.add(NmPasien);
        NmPasien.setBounds(288, 10, 330, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-09-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(528, 70, 90, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(454, 70, 70, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(200, 10, 86, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 69, 23);

        NoTelp.setEditable(false);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        FormInput.add(NoTelp);
        NoTelp.setBounds(73, 40, 120, 23);

        jLabel8.setText("No.Telp :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 69, 23);

        Poli.setEditable(false);
        Poli.setHighlighter(null);
        Poli.setName("Poli"); // NOI18N
        FormInput.add(Poli);
        Poli.setBounds(459, 40, 159, 23);

        jLabel9.setText("Unit/Poli :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(395, 40, 60, 23);

        jLabel11.setText("Dokter :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 70, 69, 23);

        Dokter.setEditable(false);
        Dokter.setHighlighter(null);
        Dokter.setName("Dokter"); // NOI18N
        FormInput.add(Dokter);
        Dokter.setBounds(73, 70, 318, 23);

        jLabel12.setText("Diagnosa :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 130, 69, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(73, 130, 213, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setHighlighter(null);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput.add(CaraBayar);
        CaraBayar.setBounds(271, 40, 120, 23);

        jLabel13.setText("Cara Bayar :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(192, 40, 75, 23);

        jLabel14.setText("Indikasi Rawat Inap :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 160, 110, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('3');
        btnDiagnosa.setToolTipText("Alt+3");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(289, 130, 28, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        FormInput.add(jScrollPane1);
        jScrollPane1.setBounds(130, 170, 410, 100);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(175, 43));
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

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKamarInap.setText("Kamar Inap");
        BtnKamarInap.setFocusPainted(false);
        BtnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKamarInap.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKamarInap.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKamarInap.setName("BtnKamarInap"); // NOI18N
        BtnKamarInap.setPreferredSize(new java.awt.Dimension(160, 23));
        BtnKamarInap.setRoundRect(false);
        BtnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarInapActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKamarInap);

        BtnRiwayatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayatPasien.setText("Riwayat Perawatan");
        BtnRiwayatPasien.setFocusPainted(false);
        BtnRiwayatPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatPasien.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayatPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatPasien.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayatPasien.setName("BtnRiwayatPasien"); // NOI18N
        BtnRiwayatPasien.setPreferredSize(new java.awt.Dimension(160, 23));
        BtnRiwayatPasien.setRoundRect(false);
        BtnRiwayatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatPasienActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayatPasien);

        BtnSuratPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSuratPermintaan.setText("Surat Permintaan Ranap");
        BtnSuratPermintaan.setFocusPainted(false);
        BtnSuratPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSuratPermintaan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSuratPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSuratPermintaan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSuratPermintaan.setName("BtnSuratPermintaan"); // NOI18N
        BtnSuratPermintaan.setPreferredSize(new java.awt.Dimension(160, 23));
        BtnSuratPermintaan.setRoundRect(false);
        BtnSuratPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSuratPermintaanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSuratPermintaan);

        BtnSuratPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSuratPRI.setText("Perintah Rawat Inap BPJS");
        BtnSuratPRI.setFocusPainted(false);
        BtnSuratPRI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSuratPRI.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSuratPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSuratPRI.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSuratPRI.setName("BtnSuratPRI"); // NOI18N
        BtnSuratPRI.setPreferredSize(new java.awt.Dimension(160, 23));
        BtnSuratPRI.setRoundRect(false);
        BtnSuratPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSuratPRIActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSuratPRI);

        BtnPersetujuanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPersetujuanRanap.setText("Persetujuan Rawat Inap");
        BtnPersetujuanRanap.setFocusPainted(false);
        BtnPersetujuanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPersetujuanRanap.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPersetujuanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPersetujuanRanap.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPersetujuanRanap.setName("BtnPersetujuanRanap"); // NOI18N
        BtnPersetujuanRanap.setPreferredSize(new java.awt.Dimension(160, 23));
        BtnPersetujuanRanap.setRoundRect(false);
        BtnPersetujuanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPersetujuanRanapActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPersetujuanRanap);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRwKeyPressed
        //Valid.pindah(evt,Status,KdDokter);
        
}//GEN-LAST:event_NoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TCari,Diagnosa);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(NmDPJP.getText().trim().equals("")){
            Valid.textKosong(btnDPJP, "DPJP");
        }else{
            if(Sequel.menyimpantf("permintaan_ranap","?,?,?,?,?,?","Data",6,new String[]{
                NoRw.getText(),
                Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                CmbRuangRawat.getSelectedItem().toString(),
                Diagnosa.getText(),
                jTextArea1.getText(),
                KdDPJP.getText()
            })==true){
                NotifWa();
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,jTextArea1,BtnBatal);
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
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(Valid.hapusTabletf(tabMode,NoRw,"permintaan_ranap","no_rawat")==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            sql="";
            if(R1.isSelected()==true){
                sql="select permintaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.no_tlp,penjab.png_jawab,poliklinik.nm_poli,dokter.nm_dokter,permintaan_ranap.tanggal,permintaan_ranap.kd_kamar,kamar.kd_bangsal,"+
                    "bangsal.nm_bangsal,kamar.trf_kamar,permintaan_ranap.diagnosa,permintaan_ranap.catatan from permintaan_ranap "+
                    "inner join reg_periksa on permintaan_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join kamar on permintaan_ranap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where permintaan_ranap.no_rawat not in (select DISTINCT no_rawat from kamar_inap) "+
                    (TCari.getText().equals("")?"":"and (permintaan_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                    "or penjab.png_jawab like '%"+TCari.getText().trim()+"%' or poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' "+
                    "or permintaan_ranap.diagnosa like '%"+TCari.getText().trim()+"%')")+" order by permintaan_ranap.tanggal";
            }else if(R2.isSelected()==true){
                sql="select permintaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.no_tlp,penjab.png_jawab,poliklinik.nm_poli,dokter.nm_dokter,permintaan_ranap.tanggal,permintaan_ranap.kd_kamar,kamar.kd_bangsal,"+
                    "bangsal.nm_bangsal,kamar.trf_kamar,permintaan_ranap.diagnosa,permintaan_ranap.catatan from permintaan_ranap "+
                    "inner join reg_periksa on permintaan_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join kamar on permintaan_ranap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where permintaan_ranap.no_rawat in (select DISTINCT no_rawat from kamar_inap) and permintaan_ranap.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().equals("")?"":"and (permintaan_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                    "or penjab.png_jawab like '%"+TCari.getText().trim()+"%' or poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' "+
                    "or permintaan_ranap.diagnosa like '%"+TCari.getText().trim()+"%')")+" order by permintaan_ranap.tanggal";
            }
            
            Valid.MyReportqry("rptPermintaanRawatInap.jasper","report","::[ Data Pemesanan Rawat Inap ]::",sql,param);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
            Valid.pindah(evt, BtnCari, NmPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(NmDPJP.getText().trim().equals("")){
            Valid.textKosong(btnDPJP,"DPJP");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(Sequel.mengedittf("permintaan_ranap","no_rawat=?","no_rawat=?,tanggal=?,kd_kamar=?,diagnosa=?,catatan=?,kd_dpjp=?",7,new String[]{
                    NoRw.getText(),
                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                    CmbRuangRawat.getSelectedItem().toString(), // Nilai JComboBox disimpan ke kd_kamar
                    Diagnosa.getText(),
                    jTextArea1.getText(),
                    KdDPJP.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                    tampil();
                    emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        aktif=true;
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt,DTPTgl,jTextArea1);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari2KeyPressed

    private void DTPCari2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari2ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari2ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        aktif=false;
        kamar.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        penyakit.isCek();
        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,Diagnosa,jTextArea1);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKamarInapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(NoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    akses.setstatus(true);
                    akses.setstatus(true);
                    DlgKamarInap kamarinap=new DlgKamarInap(null,false);
                    kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    kamarinap.setLocationRelativeTo(internalFrame1);
                    kamarinap.emptTeks();
                    kamarinap.isCek();
                    kamarinap.setNoRm(NoRw.getText(),NoRM.getText(),NmPasien.getText());
                    kamarinap.setDiagnosaAwal(Diagnosa.getText());
                    kamarinap.Otomatis();
                    kamarinap.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnKamarInapActionPerformed

    private void BtnSuratPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSuratPermintaanActionPerformed
        if (NoRw.getText().trim().isEmpty() || NmPasien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih dulu data permintaan pada tabel...!!!!");
            return;
        }

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            // 1. Siapkan semua parameter seperti biasa
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));

            param.put("no_rm", NoRM.getText());
            param.put("nama_pasien", NmPasien.getText());
            param.put("dokter_perujuk", Dokter.getText());
            param.put("dokter_dpjp", NmDPJP.getText());
            param.put("diagnosa", Diagnosa.getText());
            param.put("indikasi", jTextArea1.getText());
            param.put("rencana_perawatan", CmbRuangRawat.getSelectedItem().toString());

            ps = koneksi.prepareStatement("select DATE_FORMAT(tgl_lahir, '%d-%m-%Y') as tgl_lahir, jk from pasien where no_rkm_medis=?");
            try {
                ps.setString(1, NoRM.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    param.put("tgl_lahir", rs.getString("tgl_lahir"));
                    param.put("jk", rs.getString("jk"));
                }
            } catch (Exception e) {
                System.out.println("Notif DB: " + e);
            } finally {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            }

            // 2. CARA BARU UNTUK MEMUAT FILE REPORT (LEBIH AMAN)
            String reportPath = "/report/rptSuratPermintaanRawatInap.jasper";
            InputStream reportStream = getClass().getResourceAsStream(reportPath);

            // Pengecekan jika file tidak ditemukan di classpath
            if (reportStream == null) {
                JOptionPane.showMessageDialog(null, "File report tidak ditemukan di classpath: " + reportPath + 
                                                      "\nPastikan file rptSuratPermintaanRawatInap.jasper ada di dalam folder 'report' di source packages Anda, dan proyek sudah di-Clean and Build.");
                this.setCursor(Cursor.getDefaultCursor());
                return; // Hentikan proses jika file tidak ada
            }

            // 3. Panggil report menggunakan stream yang sudah ditemukan
            JasperPrint JPrint = JasperFillManager.fillReport(reportStream, param, new JREmptyDataSource());
            JasperViewer.viewReport(JPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal membuka report: " + e.getMessage());
        }

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSuratPermintaanActionPerformed

    private void BtnRiwayatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatPasienActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                resume.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnRiwayatPasienActionPerformed

    private void BtnSuratPRIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSuratPRIActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    ps=koneksi.prepareStatement("select pasien.no_peserta,pasien.tgl_lahir,pasien.jk from pasien where pasien.no_rkm_medis=?");
                    try {
                        ps.setString(1,NoRM.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(rs.getString("no_peserta").length()<13){
                                JOptionPane.showMessageDialog(null,"Kartu BPJS Pasien tidak valid, silahkan hubungi bagian terkait..!!");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSSPRI form=new BPJSSPRI(null,false);
                                form.setNoRm(NoRw.getText(),rs.getString("no_peserta"),NoRM.getText(),NmPasien.getText(),rs.getString("tgl_lahir"),rs.getString("jk"),"-");
                                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }
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
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnSuratPRIActionPerformed

    private void BtnPersetujuanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPersetujuanRanapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPersetujuanRawatInap resume=new SuratPersetujuanRawatInap(null,false);
                resume.isCek();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                resume.emptTeks();
                resume.setNoRm(NoRw.getText(),DTPCari2.getDate());
                resume.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }      
        }
    }//GEN-LAST:event_BtnPersetujuanRanapActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanRanap dialog = new DlgPermintaanRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKamarInap;
    private widget.Button BtnKeluar;
    private widget.Button BtnPersetujuanRanap;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayatPasien;
    private widget.Button BtnSimpan;
    private widget.Button BtnSuratPRI;
    private widget.Button BtnSuratPermintaan;
    private widget.TextBox CaraBayar;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.TextBox Diagnosa;
    private widget.TextBox Dokter;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.TextBox NmPasien;
    private widget.TextBox NoRM;
    private widget.TextBox NoRw;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Poli;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private widget.Button btnDiagnosa;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel25;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private widget.Button btnDPJP;
    private widget.TextBox KdDPJP;
    private widget.TextBox NmDPJP;
    private widget.Label jLabelDPJP;
    private widget.Label jLabelRuangRawat;
    private widget.ComboBox CmbRuangRawat;
    
    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try {
            String commonQuery = " from permintaan_ranap " +
                "inner join reg_periksa on permintaan_ranap.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj " +
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter " +
                "inner join dokter as dpjp on permintaan_ranap.kd_dpjp=dpjp.kd_dokter " +
                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli ";

            String whereClause = "";
            if (R1.isSelected() == true) {
                whereClause = "where permintaan_ranap.no_rawat not in (select DISTINCT no_rawat from kamar_inap) " +
                    (TCari.getText().trim().equals("") ? "" :
                    "and (permintaan_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? " +
                    "or penjab.png_jawab like ? or poliklinik.nm_poli like ? or dokter.nm_dokter like ? or dpjp.nm_dokter like ? " +
                    "or permintaan_ranap.diagnosa like ? or permintaan_ranap.kd_kamar like ?)"); // diubah ke kd_kamar
            } else if (R2.isSelected() == true) {
                whereClause = "where permintaan_ranap.no_rawat in (select DISTINCT no_rawat from kamar_inap) " +
                    "and permintaan_ranap.tanggal between ? and ? " +
                    (TCari.getText().trim().equals("") ? "" :
                    "and (permintaan_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? " +
                    "or penjab.png_jawab like ? or poliklinik.nm_poli like ? or dokter.nm_dokter like ? or dpjp.nm_dokter like ? " +
                    "or permintaan_ranap.diagnosa like ? or permintaan_ranap.kd_kamar like ?)"); // diubah ke kd_kamar
            }

            ps = koneksi.prepareStatement(
                "select permintaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur," +
                "pasien.no_tlp,penjab.png_jawab,poliklinik.nm_poli,dokter.nm_dokter,permintaan_ranap.tanggal,permintaan_ranap.kd_kamar," + // Mengambil kd_kamar
                "permintaan_ranap.diagnosa,permintaan_ranap.catatan,reg_periksa.kd_dokter,permintaan_ranap.kd_dpjp,dpjp.nm_dokter as dpjp" +
                commonQuery + whereClause + " order by permintaan_ranap.tanggal");

            try {
                int paramIndex = 1;
                if (R2.isSelected() == true) {
                    ps.setString(paramIndex++, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(paramIndex++, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                }
                if (!TCari.getText().trim().equals("")) {
                    for (int j = 0; j < 9; j++) {
                        ps.setString(paramIndex++, "%" + TCari.getText().trim() + "%");
                    }
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("jk"),
                        rs.getString("umurdaftar") + " " + rs.getString("sttsumur"),
                        rs.getString("no_tlp"),
                        rs.getString("png_jawab"),
                        rs.getString("nm_poli"),
                        rs.getString("nm_dokter"),
                        rs.getString("dpjp"),
                        rs.getString("tanggal"),
                        rs.getString("kd_kamar"), // Menampilkan isi kd_kamar
                        rs.getString("diagnosa"),
                        rs.getString("catatan"),
                        rs.getString("kd_dokter"),
                        rs.getString("kd_dpjp")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }


    public void emptTeks() {
        NoRw.setText("");
        NoRM.setText("");
        NmPasien.setText("");
        NoTelp.setText("");
        CaraBayar.setText("");
        Poli.setText("");
        Dokter.setText("");
        KdDokter.setText("");
        Diagnosa.setText("");
        jTextArea1.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        CmbRuangRawat.setSelectedIndex(0); // Reset ComboBox
        DTPTgl.setDate(new Date());
        Diagnosa.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            NoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            NmPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            CaraBayar.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            Poli.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            Dokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            Valid.SetTgl(DTPTgl, tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            CmbRuangRawat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            jTextArea1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
        }
    }
    
    public void setNoRm(String norwt,String norm,String nama,String namadokter,String carabayar,String poli,String notelp) {
        NoRw.setText(norwt);
        NoRM.setText(norm);
        NmPasien.setText(nama);
        Dokter.setText(namadokter);
        CaraBayar.setText(carabayar);
        Poli.setText(poli);
        NoTelp.setText(notelp);
        TCari.setText(norwt);
        ChkInput.setSelected(true);
        aktif=false;
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,280));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpermintaan_ranap());
        BtnHapus.setEnabled(akses.getpermintaan_ranap());
        BtnPrint.setEnabled(akses.getpermintaan_ranap());
        BtnKamarInap.setEnabled(akses.getkamar_inap());
        BtnRiwayatPasien.setEnabled(akses.getresume_pasien());
        BtnSuratPRI.setEnabled(akses.getbpjs_surat_pri());
        BtnEdit.setEnabled(akses.getpermintaan_ranap());   
        BtnPersetujuanRanap.setEnabled(akses.getsurat_persetujuan_rawat_inap());
    }

    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    bookingbaru=Sequel.cariInteger("select count(*) from permintaan_ranap where no_rawat not in (select DISTINCT no_rawat from kamar_inap) ");
                    if(bookingbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/ranap.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        
                        i=JOptionPane.showConfirmDialog(null, "Ada permintaan rawat inap baru, apa mau ditampilkan????","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if(i==JOptionPane.YES_OPTION){
                            R1.setSelected(true);
                            TCari.setText("");
                            tampil();
                        }
                    }
                }
            }                
        };
        new Timer(1000, taskPerformer).start();
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(175,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
    
    private void NotifWa() {
        if (notifwaadmisi.equals("yes")) {
            petugaswa = Sequel.cariIsi("select nama from pegawai where nik = ?", akses.getkode());
            LocalTime now = LocalTime.now();
            String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            koneksiwa = koneksiDBWa.condb();    
            String tanggaljamkirim = Valid.SetTgl(DTPTgl.getSelectedItem() + "") + " " + currentTime;


            String pesan = "[PERMINTAAN RAWAT INAP]\n"
                    + "==========================\n"
                    + "No Rawat: " + NoRw.getText() + "\n"
                    + "No RM: " + NoRM.getText() + "\n"
                    + "Nama Pasien : " + NmPasien.getText() + "\n"
                    + "No Telp: " + NoTelp.getText() + "\n"
                    + "Cara Bayar : " + CaraBayar.getText() + "\n\n"
                    + "Unit/ Poli : " + Poli.getText() + "\n"
                    + "Dokter : " + Dokter.getText()+ "\n\n"
                    + "Tanggal : " + DTPTgl.getSelectedItem()+ "\n\n"
                    + "Kamar : " + CmbRuangRawat.getSelectedItem().toString()+ "\n\n"
                    + "Diagnosa : " + Diagnosa.getText()+ "\n\n"
                    + "Catatan : " + jTextArea1.getText()+ "\n\n"
                    + "Petugas : " + petugaswa + "\n\n"                    
                    + "===========================";            
            try {
                String sql = "INSERT INTO wa_outbox (NOMOR, NOWA, PESAN, TANGGAL_JAM, STATUS, SOURCE, SENDER, SUCCESS, RESPONSE, REQUEST, TYPE, FILE) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                ps = koneksiwa.prepareStatement(sql);
                ps.setLong(1, 0);  // NOMOR, jika AUTO_INCREMENT, bisa diubah atau dihilangkan sesuai kebutuhan
                ps.setString(2, idgroupwaadmisi);  // NOWA
                ps.setString(3, pesan);          // PESAN
                ps.setString(4, tanggaljamkirim);  // TANGGAL_JAM
                ps.setString(5, "ANTRIAN");                 // STATUS
                ps.setString(6, "KHANZA");  // SOURCE
                ps.setString(7, "NODEJS");                  // SENDER
                ps.setString(8, "");                        // SUCCESS
                ps.setString(9, "");                        // RESPONSE
                ps.setString(10, "");                       // REQUEST
                ps.setString(11, "TEXT");                   // TYPE
                ps.setString(12, "");                       // FILE
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
    }
    
    private void initSPRI() {
        // 1. Inisialisasi Komponen (Bagian Paling Penting untuk Error Ini)
        // PASTIKAN BARIS-BARIS INISIALISASI INI ADA DI AWAL METHOD
        jLabelDPJP = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        btnDPJP = new widget.Button();
        jLabelRuangRawat = new widget.Label();    // <-- Kemungkinan baris ini hilang
        CmbRuangRawat = new widget.ComboBox();  // <-- Kemungkinan baris ini juga hilang

        // 2. Konfigurasi Properti Komponen DPJP
        jLabelDPJP.setText("DPJP :");
        jLabelDPJP.setName("jLabelDPJP");
        KdDPJP.setEditable(false);
        KdDPJP.setName("KdDPJP");
        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP");
        btnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        btnDPJP.setName("btnDPJP");
        btnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPActionPerformed(evt);
            }
        });

        // 3. Konfigurasi Komponen Ruang Rawat BARU
        jLabelRuangRawat.setText("Ruang Rawat :");
        jLabelRuangRawat.setName("jLabelRuangRawat");

        CmbRuangRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { 
            "Ruang Rawat Anak", "Ruang Rawat Dewasa", "Perinatologi", "Kebidanan", 
            "Isolasi Anak", "Isolasi Dewasa", "Isolasi Covid-19", "ICU", "HCU", "NICU" 
        }));
        CmbRuangRawat.setName("CmbRuangRawat");

        // 4. Mengatur Tata Letak

        // Baris Ruang Rawat (y=100)
        FormInput.add(jLabelRuangRawat);
        jLabelRuangRawat.setBounds(0, 100, 90, 23);
        FormInput.add(CmbRuangRawat);
        CmbRuangRawat.setBounds(95, 100, 523, 23);

        // Baris Diagnosa & DPJP (y=130)
        jLabel12.setBounds(0, 130, 69, 23);
        Diagnosa.setBounds(73, 130, 200, 23);
        btnDiagnosa.setBounds(276, 130, 28, 23);

        FormInput.add(jLabelDPJP);
        jLabelDPJP.setBounds(314, 130, 40, 23);

        FormInput.add(KdDPJP);
        KdDPJP.setBounds(357, 130, 70, 23);

        FormInput.add(NmDPJP);
        NmDPJP.setBounds(430, 130, 160, 23);

        FormInput.add(btnDPJP);
        btnDPJP.setBounds(593, 130, 28, 23);

        // Baris Catatan/Indikasi (y=160)
        jLabel14.setBounds(0, 160, 120, 23);
        jScrollPane1.setBounds(123, 160, 495, 45); 

    }
    
    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {
        akses.setform("DlgPermintaanRanap");
        dokterdpjp.isCek();
        dokterdpjp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokterdpjp.setLocationRelativeTo(internalFrame1);
        dokterdpjp.setVisible(true);
    }
}
