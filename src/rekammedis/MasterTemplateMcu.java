package rekammedis;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariAturanPakai;
import inventory.DlgCariMetodeRacik;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;

public class MasterTemplateMcu extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRadiologi,tabModePK,tabModeDetailPK,TabModeTindakan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i,index=0,jml=0,r=0;
    private String[] kode,nama,ciripny,keterangan,kategori,cirium,kode2,panjang,pendek,satuan,nilairujukan,no;
    private boolean[] pilih;
    private double[] jumlah,p1,p2,kps;
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private File file;
    private FileWriter fileWriter;
    private String iyem,la="",ld="",pa="",pd="",noracik="";
    private double jumlahracik=0,persenracik=0,kapasitasracik=0;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariMetodeRacik metoderacik=new DlgCariMetodeRacik(null,false);
    private DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public MasterTemplateMcu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Template","Judul"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
             
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanRadiologi.setModel(tabModeRadiologi);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(490);
            }
        }
        tbPermintaanRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePK=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPK.setModel(tabModePK);

        tbPermintaanPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPK=new DefaultTableModel(null,new Object[]{"P","Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if (colIndex==0) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailPK.setModel(tabModeDetailPK);
        //tampilPr();

        tbDetailPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(326);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else if(i==3){
                column.setPreferredWidth(315);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        
        tbDetailPK.setDefaultRenderer(Object.class, new WarnaTable());
        
                        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan/Tindakan","Kategori"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 4; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(380);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());

        Kd.setDocument(new batasInput((byte)20).getKata(Kd));    
        Judul.setDocument(new batasInput((int)2000).getKata(Judul));  
        CariRadiologi.setDocument(new batasInput((byte)100).getKata(CariRadiologi));  
        CariPK.setDocument(new batasInput((byte)100).getKata(CariPK));  
        CariDetailPK.setDocument(new batasInput((byte)100).getKata(CariDetailPK));  
        CariTindakan.setDocument(new batasInput((byte)100).getKata(CariTindakan)); 
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));    
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
        
                
               
        ChkAccor.setSelected(false);
        isDetail();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        jLabel42 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Judul = new widget.TextArea();
        BtnCariRadiologi = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbPermintaanRadiologi = new widget.Table();
        CariRadiologi = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        CariPK = new widget.TextBox();
        BtnCariLaboratoriumPK = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        tbPermintaanPK = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbDetailPK = new widget.Table();
        CariDetailPK = new widget.TextBox();
        BtnDetailLaboratPK = new widget.Button();
        jLabel21 = new widget.Label();
        CariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        Scroll12 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        BtnAllRadiologi = new widget.Button();
        BtnAllPatologiKlinis = new widget.Button();
        BtnAllDetailLaboratPK = new widget.Button();
        BtnAllTindakan = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormDetail = new widget.PanelBiasa();
        Scroll13 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template MCU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 1500));
        FormInput.setLayout(null);

        label12.setText("No.Template :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 85, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(89, 10, 150, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Judul Template:");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 50, 410, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Judul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Judul.setColumns(20);
        Judul.setRows(7);
        Judul.setName("Judul"); // NOI18N
        Judul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JudulKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Judul);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(10, 70, 700, 73);

        BtnCariRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRadiologi.setMnemonic('1');
        BtnCariRadiologi.setToolTipText("Alt+1");
        BtnCariRadiologi.setName("BtnCariRadiologi"); // NOI18N
        BtnCariRadiologi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRadiologiActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariRadiologi);
        BtnCariRadiologi.setBounds(650, 180, 28, 23);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPermintaanRadiologi.setName("tbPermintaanRadiologi"); // NOI18N
        Scroll3.setViewportView(tbPermintaanRadiologi);

        FormInput.add(Scroll3);
        Scroll3.setBounds(10, 230, 700, 116);

        CariRadiologi.setHighlighter(null);
        CariRadiologi.setName("CariRadiologi"); // NOI18N
        CariRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(CariRadiologi);
        CariRadiologi.setBounds(10, 180, 640, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Permintaan Radiologi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(10, 160, 120, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Permintaan Laborat Patologi Klinis :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(10, 370, 190, 23);

        CariPK.setHighlighter(null);
        CariPK.setName("CariPK"); // NOI18N
        CariPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariPKKeyPressed(evt);
            }
        });
        FormInput.add(CariPK);
        CariPK.setBounds(10, 390, 640, 23);

        BtnCariLaboratoriumPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariLaboratoriumPK.setMnemonic('1');
        BtnCariLaboratoriumPK.setToolTipText("Alt+1");
        BtnCariLaboratoriumPK.setName("BtnCariLaboratoriumPK"); // NOI18N
        BtnCariLaboratoriumPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariLaboratoriumPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariLaboratoriumPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariLaboratoriumPK);
        BtnCariLaboratoriumPK.setBounds(650, 390, 28, 23);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPermintaanPK.setName("tbPermintaanPK"); // NOI18N
        tbPermintaanPK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanPKMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbPermintaanPK);

        FormInput.add(Scroll4);
        Scroll4.setBounds(10, 430, 700, 109);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll5.setComponentPopupMenu(Popup);
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbDetailPK.setComponentPopupMenu(Popup);
        tbDetailPK.setName("tbDetailPK"); // NOI18N
        Scroll5.setViewportView(tbDetailPK);

        FormInput.add(Scroll5);
        Scroll5.setBounds(20, 600, 700, 400);

        CariDetailPK.setHighlighter(null);
        CariDetailPK.setName("CariDetailPK"); // NOI18N
        CariDetailPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariDetailPKKeyPressed(evt);
            }
        });
        FormInput.add(CariDetailPK);
        CariDetailPK.setBounds(10, 540, 640, 23);

        BtnDetailLaboratPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnDetailLaboratPK.setMnemonic('1');
        BtnDetailLaboratPK.setToolTipText("Alt+1");
        BtnDetailLaboratPK.setName("BtnDetailLaboratPK"); // NOI18N
        BtnDetailLaboratPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDetailLaboratPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDetailLaboratPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnDetailLaboratPK);
        BtnDetailLaboratPK.setBounds(660, 540, 28, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Tindakan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(20, 1020, 120, 23);

        CariTindakan.setHighlighter(null);
        CariTindakan.setName("CariTindakan"); // NOI18N
        CariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariTindakanKeyPressed(evt);
            }
        });
        FormInput.add(CariTindakan);
        CariTindakan.setBounds(20, 1040, 640, 23);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariTindakan);
        BtnCariTindakan.setBounds(660, 1040, 28, 23);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbTindakan.setName("tbTindakan"); // NOI18N
        Scroll12.setViewportView(tbTindakan);

        FormInput.add(Scroll12);
        Scroll12.setBounds(20, 1070, 700, 402);

        BtnAllRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRadiologi.setMnemonic('2');
        BtnAllRadiologi.setToolTipText("Alt+2");
        BtnAllRadiologi.setName("BtnAllRadiologi"); // NOI18N
        BtnAllRadiologi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRadiologiActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllRadiologi);
        BtnAllRadiologi.setBounds(680, 180, 28, 23);

        BtnAllPatologiKlinis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPatologiKlinis.setMnemonic('2');
        BtnAllPatologiKlinis.setToolTipText("Alt+2");
        BtnAllPatologiKlinis.setName("BtnAllPatologiKlinis"); // NOI18N
        BtnAllPatologiKlinis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPatologiKlinis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPatologiKlinisActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllPatologiKlinis);
        BtnAllPatologiKlinis.setBounds(680, 390, 28, 23);

        BtnAllDetailLaboratPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllDetailLaboratPK.setMnemonic('2');
        BtnAllDetailLaboratPK.setToolTipText("Alt+2");
        BtnAllDetailLaboratPK.setName("BtnAllDetailLaboratPK"); // NOI18N
        BtnAllDetailLaboratPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllDetailLaboratPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllDetailLaboratPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllDetailLaboratPK);
        BtnAllDetailLaboratPK.setBounds(690, 540, 28, 23);

        BtnAllTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakan.setMnemonic('2');
        BtnAllTindakan.setToolTipText("Alt+2");
        BtnAllTindakan.setName("BtnAllTindakan"); // NOI18N
        BtnAllTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllTindakan);
        BtnAllTindakan.setBounds(690, 1040, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Template", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormDetail.setBackground(new java.awt.Color(255, 255, 255));
        FormDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Detail Template Pemeriksaan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormDetail.setName("FormDetail"); // NOI18N
        FormDetail.setPreferredSize(new java.awt.Dimension(115, 73));
        FormDetail.setLayout(new java.awt.BorderLayout());

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setName("Scroll13"); // NOI18N
        Scroll13.setOpaque(true);
        Scroll13.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll13.setViewportView(LoadHTML);

        FormDetail.add(Scroll13, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormDetail, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Template", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            try {
                panggilDetail();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbDokter.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    if(ChkAccor.isSelected()==false){  
                        if(tbDokter.getSelectedRow()!= -1){
                            ChkAccor.setSelected(true);
                            isDetail();
                            panggilDetail();
                            ChkAccor.setSelected(false);
                            isDetail();
                        }
                    }
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    /*    if(tbDokter.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdDokter.getText().equals(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        } */
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
    if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(Judul.getText().trim().equals("")){
            Valid.textKosong(Judul,"Judul");
        }else{
            ganti();
        } 
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(Judul.getText().trim().equals("")){
            Valid.textKosong(Judul,"Judul");
        }else{
            if(Sequel.menyimpantf("template_pemeriksaan_mcu","?,?","No.Template",2,new String[]{
                Kd.getText(),Judul.getText()
            })==true){
                for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
                    if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_permintaan_radiologi","?,?","Pemeriksaan Radiologi",2,new String[]{
                            Kd.getText(),tbPermintaanRadiologi.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                    if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            Kd.getText(),tbPermintaanPK.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailPK.getRowCount();i++){ 
                    if((!tbDetailPK.getValueAt(i,4).toString().equals(""))&&tbDetailPK.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_mcu_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            Kd.getText(),tbDetailPK.getValueAt(i,5).toString(),tbDetailPK.getValueAt(i,4).toString()
                        });
                    }
                }
                
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_tindakan","?,?","Tindakan Dokter",2,new String[]{
                            Kd.getText(),tbTindakan.getValueAt(i,1).toString()
                        });
                    }
                }
                tabMode.addRow(new String[]{
                    Kd.getText(),Judul.getText()
                });
                emptTeks();
            }              
        } 
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           // Valid.pindah(evt,Subjek,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        //Valid.pindah(evt,TCari,Nm,TCari);
    }//GEN-LAST:event_KdKeyPressed

    private void JudulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JudulKeyPressed
      //  Valid.pindah2(evt,Objek,Plan);
    }//GEN-LAST:event_JudulKeyPressed

    private void BtnCariRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRadiologiActionPerformed
        tampilRadiologi2();
    }//GEN-LAST:event_BtnCariRadiologiActionPerformed

    private void CariRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariRadiologiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilRadiologi2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariPK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        //    Prosedur.requestFocus();
        }
    }//GEN-LAST:event_CariRadiologiKeyPressed

    private void CariPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariPKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPK2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariDetailPK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariRadiologi.requestFocus();
        }
    }//GEN-LAST:event_CariPKKeyPressed

    private void BtnCariLaboratoriumPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariLaboratoriumPKActionPerformed
        tampilPK2();
    }//GEN-LAST:event_BtnCariLaboratoriumPKActionPerformed

    private void CariDetailPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariDetailPKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilDetailPK();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        //    CariPA.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariPK.requestFocus();
        }
    }//GEN-LAST:event_CariDetailPKKeyPressed

    private void BtnDetailLaboratPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDetailLaboratPKActionPerformed
        tampilDetailPK();
    }//GEN-LAST:event_BtnDetailLaboratPKActionPerformed

    private void CariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilTindakan2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
          //  CariObatRacikan.requestFocus();
        }
    }//GEN-LAST:event_CariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        tampilTindakan2();
    }//GEN-LAST:event_BtnCariTindakanActionPerformed

    private void BtnAllRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRadiologiActionPerformed
        CariRadiologi.setText("");
        tampilRadiologi();
        tampilRadiologi2();
    }//GEN-LAST:event_BtnAllRadiologiActionPerformed

    private void BtnAllPatologiKlinisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPatologiKlinisActionPerformed
        //CariPA.setText("");
        tampilPK();
        tampilPK2();
    }//GEN-LAST:event_BtnAllPatologiKlinisActionPerformed

    private void BtnAllDetailLaboratPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllDetailLaboratPKActionPerformed
        CariDetailPK.setText("");
        tampilDetailPK();
    }//GEN-LAST:event_BtnAllDetailLaboratPKActionPerformed

    private void BtnAllTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanActionPerformed
        CariTindakan.setText("");
        tampilTindakan();
        tampilTindakan2();
    }//GEN-LAST:event_BtnAllTindakanActionPerformed

    private void tbPermintaanPKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanPKMouseClicked
        if(tabModePK.getRowCount()!=0){
            try {
                Valid.tabelKosong(tabModeDetailPK);
                tampilDetailPK();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPermintaanPKMouseClicked

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            isDetail();
            panggilDetail();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbDetailPK.getRowCount();i++){
            tbDetailPK.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(i=0;i<tbDetailPK.getRowCount();i++){
            tbDetailPK.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                break;
            case 1:
                tampil();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterTemplateMcu dialog = new MasterTemplateMcu(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAllDetailLaboratPK;
    private widget.Button BtnAllPatologiKlinis;
    private widget.Button BtnAllRadiologi;
    private widget.Button BtnAllTindakan;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariLaboratoriumPK;
    private widget.Button BtnCariRadiologi;
    private widget.Button BtnCariTindakan;
    private widget.Button BtnDetailLaboratPK;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    public widget.TextBox CariDetailPK;
    public widget.TextBox CariPK;
    public widget.TextBox CariRadiologi;
    public widget.TextBox CariTindakan;
    private widget.CekBox ChkAccor;
    private widget.PanelBiasa FormDetail;
    private widget.PanelBiasa FormInput;
    private widget.TextArea Judul;
    private widget.TextBox Kd;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel21;
    private widget.Label jLabel42;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane4;
    public widget.Table tbDetailPK;
    private widget.Table tbDokter;
    public widget.Table tbPermintaanPK;
    public widget.Table tbPermintaanRadiologi;
    public widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                        "select * from template_pemeriksaan_mcu "+
                        (TCari.getText().equals("")?"":"where template_pemeriksaan_mcu.no_template like ? or template_pemeriksaan_mcu.judul like ? "+
                         "order by template_pemeriksaan_mcu.no_template"));
            try {
                    if(!TCari.getText().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");                       }
               
                
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_template"),rs.getString("judul")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Kd.setText("");
        Judul.setText("");
        CariRadiologi.setText("");
        CariPK.setText("");
        CariDetailPK.setText(""); 
        CariTindakan.setText("");
        Valid.tabelKosong(tabModeRadiologi);
        Valid.tabelKosong(tabModePK);
        Valid.tabelKosong(tabModeDetailPK);
        Valid.tabelKosong(TabModeTindakan);
        Valid.autoNomer("template_pemeriksaan_mcu","TPM",16,Kd);
        TabRawat.setSelectedIndex(0);
        //Subjek.requestFocus();
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            Kd.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
            Judul.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettemplate_pemeriksaan());
        BtnHapus.setEnabled(akses.gettemplate_pemeriksaan());
        BtnEdit.setEnabled(akses.gettemplate_pemeriksaan());

    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void tampildiagnosa() {
    /*    try{
            jml=0;
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            ciripny=null;
            ciripny=new String[jml];
            keterangan=null;
            keterangan=new String[jml];
            kategori=null;
            kategori=new String[jml];
            cirium=null;
            cirium=new String[jml];

            index=0; 
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbDiagnosa.getValueAt(i,1).toString();
                    nama[index]=tbDiagnosa.getValueAt(i,2).toString();
                    ciripny[index]=tbDiagnosa.getValueAt(i,3).toString();
                    keterangan[index]=tbDiagnosa.getValueAt(i,4).toString();
                    kategori[index]=tbDiagnosa.getValueAt(i,5).toString();
                    cirium[index]=tbDiagnosa.getValueAt(i,6).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDiagnosa);
            for(i=0;i<jml;i++){
                tabModeDiagnosa.addRow(new Object[] {pilih[i],kode[i],nama[i],ciripny[i],keterangan[i],kategori[i],cirium[i]});
            }       

            ps=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                    "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum from kategori_penyakit inner join penyakit "+
                    "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where penyakit.kd_penyakit like ? or penyakit.nm_penyakit like ? or "+
                    "penyakit.ciri_ciri like ? or penyakit.keterangan like ? or kategori_penyakit.nm_kategori like ? or "+
                    "kategori_penyakit.ciri_umum like ? order by penyakit.kd_penyakit  LIMIT 1000");
            try {
                ps.setString(1,"%"+Diagnosa.getText().trim()+"%");
                ps.setString(2,"%"+Diagnosa.getText().trim()+"%");
                ps.setString(3,"%"+Diagnosa.getText().trim()+"%");
                ps.setString(4,"%"+Diagnosa.getText().trim()+"%");
                ps.setString(5,"%"+Diagnosa.getText().trim()+"%");
                ps.setString(6,"%"+Diagnosa.getText().trim()+"%");  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDiagnosa.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)
                    });
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilprosedure() {
    /*    try{
            jml=0;
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode2=null;
            kode2=new String[jml];
            panjang=null;
            panjang=new String[jml];
            pendek=null;
            pendek=new String[jml];

            index=0; 
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode2[index]=tbProsedur.getValueAt(i,1).toString();
                    panjang[index]=tbProsedur.getValueAt(i,2).toString();
                    pendek[index]=tbProsedur.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeProsedur);
            for(i=0;i<jml;i++){
                tabModeProsedur.addRow(new Object[] {pilih[i],kode2[i],panjang[i],pendek[i]});
            }
            
            ps=koneksi.prepareStatement("select * from icd9 where icd9.kode like ? or icd9.deskripsi_panjang like ? or icd9.deskripsi_pendek like ? order by icd9.kode");
            try{
                ps.setString(1,"%"+Prosedur.getText().trim()+"%");
                ps.setString(2,"%"+Prosedur.getText().trim()+"%");
                ps.setString(3,"%"+Prosedur.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeProsedur.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3)
                    });
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilRadiologi() {         
        try{
            file=new File("./cache/permintaanradiologi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from jns_perawatan_radiologi where jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas='-' or jns_perawatan_radiologi.kelas='Rawat Jalan') order by jns_perawatan_radiologi.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePeriksa\":\""+rs.getString(1)+"\",\"NamaPemeriksaan\":\""+rs.getString(2).replaceAll("\"","")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaanradiologi\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilRadiologi2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){
                if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){
                if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanRadiologi.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanRadiologi.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeRadiologi);
            for(i=0;i<jml;i++){                
                tabModeRadiologi.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            myObj = new FileReader("./cache/permintaanradiologi.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanradiologi");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariRadiologi.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariRadiologi.getText().toLowerCase()))){
                        tabModeRadiologi.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPK() {         
        try{
            file=new File("./cache/permintaanpk.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='PK' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePeriksa\":\""+rs.getString(1)+"\",\"NamaPemeriksaan\":\""+rs.getString(2).replaceAll("\"","")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaanpk\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilPK2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanPK.getRowCount();i++){
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanPK.getRowCount();i++){
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanPK.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanPK.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModePK);
            for(i=0;i<jml;i++){                
                tabModePK.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            myObj = new FileReader("./cache/permintaanpk.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanpk");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariPK.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariPK.getText().toLowerCase()))){
                        tabModePK.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDetailPK() { 
        try {
            jml=0;
            for(i=0;i<tbDetailPK.getRowCount();i++){
                if(tbDetailPK.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }
            
            pilih=null;
            pilih=new boolean[jml];
            nama=null;
            nama=new String[jml];
            satuan=null;
            satuan=new String[jml];
            nilairujukan=null;
            nilairujukan=new String[jml];
            kode=null;
            kode=new String[jml];
            kode2=null;
            kode2=new String[jml];
            
            index=0; 
            for(i=0;i<tbDetailPK.getRowCount();i++){
                if(tbDetailPK.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    nama[index]=tbDetailPK.getValueAt(i,1).toString();
                    satuan[index]=tbDetailPK.getValueAt(i,2).toString();
                    nilairujukan[index]=tbDetailPK.getValueAt(i,3).toString();
                    kode[index]=tbDetailPK.getValueAt(i,4).toString();
                    kode2[index]=tbDetailPK.getValueAt(i,5).toString();
                    index++;
                }
            }
            
            Valid.tabelKosong(tabModeDetailPK);
            
            for(i=0;i<jml;i++){ 
                tabModeDetailPK.addRow(new Object[] {
                    pilih[i],nama[i],satuan[i],nilairujukan[i],kode[i],kode2[i]
                });
            }  
            
            for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    tabModeDetailPK.addRow(new Object[]{false,tbPermintaanPK.getValueAt(i,2).toString(),"","","",""});
                    ps=koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        ps.setString(1,tbPermintaanPK.getValueAt(i,1).toString());
                        ps.setString(2,"%"+CariDetailPK.getText().trim()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            la="";ld="";pa="";pd="";
                            if(!rs.getString("nilai_rujukan_ld").equals("")){
                                ld="LD : "+rs.getString("nilai_rujukan_ld");
                            }
                            if(!rs.getString("nilai_rujukan_la").equals("")){
                                la=", LA : "+rs.getString("nilai_rujukan_la");
                            }
                            if(!rs.getString("nilai_rujukan_pa").equals("")){
                                pd=", PD : "+rs.getString("nilai_rujukan_pd");
                            }
                            if(!rs.getString("nilai_rujukan_pd").equals("")){
                                pa=" PA : "+rs.getString("nilai_rujukan_pa");
                            }
                            tabModeDetailPK.addRow(new Object[]{
                                false,"   "+rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,rs.getString("id_template"),tbPermintaanPK.getValueAt(i,1).toString()
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }                         
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        }
    }
    
    private void tampilPA() {         
        try{
            file=new File("./cache/permintaanpa.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='PA' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePeriksa\":\""+rs.getString(1)+"\",\"NamaPemeriksaan\":\""+rs.getString(2).replaceAll("\"","")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaanpa\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilPA2() {         
    /*    try{
            jml=0;
            for(i=0;i<tbPermintaanPA.getRowCount();i++){
                if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanPA.getRowCount();i++){
                if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanPA.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanPA.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModePA);
            for(i=0;i<jml;i++){                
                tabModePA.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            myObj = new FileReader("./cache/permintaanpa.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanpa");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariPA.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariPA.getText().toLowerCase()))){
                        tabModePA.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilMB() {         
    /*    try{
            file=new File("./cache/permintaanmb.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='MB' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePeriksa\":\""+rs.getString(1)+"\",\"NamaPemeriksaan\":\""+rs.getString(2).replaceAll("\"","")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaanmb\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        } */
    }
    
    private void tampilMB2() {         
    /*    try{
            jml=0;
            for(i=0;i<tbPermintaanMB.getRowCount();i++){
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanMB.getRowCount();i++){
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanMB.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanMB.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeMB);
            for(i=0;i<jml;i++){                
                tabModeMB.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            myObj = new FileReader("./cache/permintaanmb.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanmb");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariMB.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariMB.getText().toLowerCase()))){
                        tabModeMB.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilDetailMB() { 
    /*    try {
            jml=0;
            for(i=0;i<tbDetailMB.getRowCount();i++){
                if(tbDetailMB.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }
            
            pilih=null;
            pilih=new boolean[jml];
            nama=null;
            nama=new String[jml];
            satuan=null;
            satuan=new String[jml];
            nilairujukan=null;
            nilairujukan=new String[jml];
            kode=null;
            kode=new String[jml];
            kode2=null;
            kode2=new String[jml];
            
            index=0; 
            for(i=0;i<tbDetailMB.getRowCount();i++){
                if(tbDetailMB.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    nama[index]=tbDetailMB.getValueAt(i,1).toString();
                    satuan[index]=tbDetailMB.getValueAt(i,2).toString();
                    nilairujukan[index]=tbDetailMB.getValueAt(i,3).toString();
                    kode[index]=tbDetailMB.getValueAt(i,4).toString();
                    kode2[index]=tbDetailMB.getValueAt(i,5).toString();
                    index++;
                }
            }
            
            Valid.tabelKosong(tabModeDetailMB);
            
            for(i=0;i<jml;i++){ 
                tabModeDetailMB.addRow(new Object[] {
                    pilih[i],nama[i],satuan[i],nilairujukan[i],kode[i],kode2[i]
                }); 
            }  
            
            for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    tabModeDetailMB.addRow(new Object[]{false,tbPermintaanMB.getValueAt(i,2).toString(),"","","",""});
                    ps=koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        ps.setString(1,tbPermintaanMB.getValueAt(i,1).toString());
                        ps.setString(2,"%"+CariDetailMB.getText().trim()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            la="";ld="";pa="";pd="";
                            if(!rs.getString("nilai_rujukan_ld").equals("")){
                                ld="LD : "+rs.getString("nilai_rujukan_ld");
                            }
                            if(!rs.getString("nilai_rujukan_la").equals("")){
                                la=", LA : "+rs.getString("nilai_rujukan_la");
                            }
                            if(!rs.getString("nilai_rujukan_pa").equals("")){
                                pd=", PD : "+rs.getString("nilai_rujukan_pd");
                            }
                            if(!rs.getString("nilai_rujukan_pd").equals("")){
                                pa=" PA : "+rs.getString("nilai_rujukan_pa");
                            }
                            tabModeDetailMB.addRow(new Object[]{
                                false,"   "+rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,rs.getString("id_template"),tbPermintaanMB.getValueAt(i,1).toString()
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }                        
                                              
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        } */
    }
    
    private void tampilObatNonRacikan() {         
    /*    try{
            file=new File("./cache/permintaanobatnonracikan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
            ps=koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,databarang.letak_barang,jenis.nama,industrifarmasi.nama_industri,databarang.kapasitas "+
                    "from databarang inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri where databarang.status='1' order by databarang.nama_brng");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodeBarang\":\""+rs.getString(1)+"\",\"NamaBarang\":\""+rs.getString(2).replaceAll("\"","")+"\",\"Satuan\":\""+rs.getString(3)+"\",\"Komposisi\":\""+rs.getString(4)+"\",\"JenisObat\":\""+rs.getString(5)+"\",\"Industri\":\""+rs.getString(6)+"\",\"Kapasitas\":\""+rs.getString(7)+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaanobatnonracikan\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        } */
    }
    
    private void tampilObatNonRacikan2() {         
    /*    try{
            jml=0;
            for(i=0;i<tbObatNonRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){
                    jml++;
                }
            }
            
            pilih=null;
            pilih=new boolean[jml];
            jumlah=null;
            jumlah=new double[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            satuan=null;
            satuan=new String[jml];
            cirium=null;
            cirium=new String[jml];
            kategori=null;
            kategori=new String[jml];
            keterangan=null;
            keterangan=new String[jml];
            ciripny=null;
            ciripny=new String[jml];
            kps=null;
            kps=new double[jml];
            
            index=0; 
            for(i=0;i<tbObatNonRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){
                    pilih[index]=Boolean.parseBoolean(tbObatNonRacikan.getValueAt(i,0).toString());                
                    try {
                        jumlah[index]=Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString());
                    } catch (Exception e) {
                        jumlah[index]=0;
                    }
                    kode[index]=tbObatNonRacikan.getValueAt(i,2).toString();
                    nama[index]=tbObatNonRacikan.getValueAt(i,3).toString();
                    satuan[index]=tbObatNonRacikan.getValueAt(i,4).toString();
                    cirium[index]=tbObatNonRacikan.getValueAt(i,5).toString();
                    kategori[index]=tbObatNonRacikan.getValueAt(i,6).toString();
                    keterangan[index]=tbObatNonRacikan.getValueAt(i,7).toString();
                    ciripny[index]=tbObatNonRacikan.getValueAt(i,8).toString();
                    try {
                        kps[index]=Double.parseDouble(tbObatNonRacikan.getValueAt(i,9).toString());
                    } catch (Exception e) {
                        kps[index]=0;
                    }
                    index++;
                }
            }

            Valid.tabelKosong(tabModeObatUmum);
                    
            for(i=0;i<jml;i++){                
                tabModeObatUmum.addRow(new Object[] {pilih[i],jumlah[i],kode[i],nama[i],satuan[i],cirium[i],kategori[i],keterangan[i],ciripny[i],kps[i]});
            }    
        
            myObj = new FileReader("./cache/permintaanobatnonracikan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanobatnonracikan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodeBarang").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())
                            ||list.path("Komposisi").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())||list.path("JenisObat").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase()))){
                        tabModeObatUmum.addRow(new Object[]{
                            false,"",list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),list.path("Komposisi").asText(),list.path("JenisObat").asText(),"",list.path("Industri").asText(),list.path("Kapasitas").asDouble()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilDetailObatRacikan(){
    /*    try {
            jml=0;
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                    jml++;
                }
            } 

            no=null;
            no=new String[jml]; 
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            satuan=null;
            satuan=new String[jml];
            kategori=null;
            kategori=new String[jml];
            kps=null;
            kps=new double[jml];
            p1=null;
            p1=new double[jml];
            p2=null;
            p2=new double[jml];
            keterangan=null;
            keterangan=new String[jml];
            jumlah=null;
            jumlah=new double[jml];
            cirium=null;
            cirium=new String[jml];
            ciripny=null;
            ciripny=new String[jml];

            index=0;        
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                    no[index]=tbDetailObatRacikan.getValueAt(i,0).toString();
                    kode[index]=tbDetailObatRacikan.getValueAt(i,1).toString();
                    nama[index]=tbDetailObatRacikan.getValueAt(i,2).toString();
                    satuan[index]=tbDetailObatRacikan.getValueAt(i,3).toString();
                    kategori[index]=tbDetailObatRacikan.getValueAt(i,4).toString();
                    try {
                        kps[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,5).toString());
                    }catch (Exception e) {
                        kps[index]=0;
                    }
                    try {
                        p1[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,6).toString());
                    }catch (Exception e) {
                        p1[index]=0;
                    }
                    try {
                        p2[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,8).toString());
                    }catch (Exception e) {
                        p2[index]=0;
                    }
                    keterangan[index]=tbDetailObatRacikan.getValueAt(i,9).toString();
                    try {
                        jumlah[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString());
                    }catch (Exception e) {
                        jumlah[index]=0;
                    }
                    cirium[index]=tbDetailObatRacikan.getValueAt(i,11).toString();
                    ciripny[index]=tbDetailObatRacikan.getValueAt(i,12).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDetailObatRacikan); 
            for(i=0;i<index;i++){
                tabModeDetailObatRacikan.addRow(new Object[] {
                    no[i],kode[i],nama[i],satuan[i],kategori[i],kps[i],p1[i],"/",p2[i],keterangan[i],jumlah[i],cirium[i],ciripny[i]
                });
            }
            
            myObj = new FileReader("./cache/permintaanobatnonracikan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanobatnonracikan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodeBarang").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())
                            ||list.path("Komposisi").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())||list.path("JenisObat").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase()))){
                        tabModeDetailObatRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),
                            list.path("JenisObat").asText(),list.path("Kapasitas").asDouble(),1,"/",1,"",0,list.path("Industri").asText(),list.path("Komposisi").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        } */
    }
    
    private void tampilTindakan() {         
        try{
            file=new File("./cache/permintaantindakan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem=""; 
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori from jns_perawatan inner join kategori_perawatan "+
                    "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where jns_perawatan.status='1' and jns_perawatan.total_byrdr>0 order by jns_perawatan.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePeriksa\":\""+rs.getString(1)+"\",\"NamaPemeriksaan\":\""+rs.getString(2).replaceAll("\"","")+"\",\"Kategori\":\""+rs.getString(3).replaceAll("\"","")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            fileWriter.write("{\"permintaantindakan\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilTindakan2() {         
        try{
            jml=0;
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            
            index=0; 
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakan.getValueAt(i,1).toString();
                    nama[index]=tbTindakan.getValueAt(i,2).toString();
                    kategori[index]=tbTindakan.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(TabModeTindakan);
            for(i=0;i<jml;i++){                
                TabModeTindakan.addRow(new Object[] {pilih[i],kode[i],nama[i],kategori[i]});
            }    
        
            myObj = new FileReader("./cache/permintaantindakan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaantindakan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase())||
                        list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase())||
                        list.path("Kategori").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase()))){
                        TabModeTindakan.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText(),list.path("Kategori").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void isDetail(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame3.getWidth()-200,HEIGHT));
            FormDetail.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormDetail.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilDetail() {
        if(FormDetail.isVisible()==true){
            if(tbDokter.getSelectedRow()!= -1){
                try {
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='top' align='left' width='100%'>"+
                                "Judul : "+tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString()+
                            "</td>"+
                        "</tr>"
                    ); 
                    
                                     
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_mcu_permintaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from template_pemeriksaan_mcu_permintaan_radiologi "+
                            "inner join jns_perawatan_radiologi on template_pemeriksaan_mcu_permintaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where template_pemeriksaan_mcu_permintaan_radiologi.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top' align='left' width='100%'>"+
                                        "Permintaan Radiologi : "+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode Periksa</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80%'>Nama Pemeriksaan</td>"+
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeRadiologi);
                            rs.beforeFirst();
                            while(rs.next()){
                                htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td align='center'>"+rs.getString("kd_jenis_prw")+"</td>"+
                                                "<td>"+rs.getString("nm_perawatan")+"</td>"+
                                            "</tr>"
                                );
                                tabModeRadiologi.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                            }
                            htmlContent.append( 
                                        "</table>"+
                                    "</td>"+
                                "</tr>"
                            ); 

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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_mcu_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_mcu_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_mcu_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_mcu_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PK'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top' align='left' width='100%'>"+
                                        "Permintaan Laborat Patologi Klinis : "+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Kode Periksa</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85%'>Nama Pemeriksaan</td>"+
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModePK);
                            rs.beforeFirst();
                            while(rs.next()){
                                htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td align='center'>"+rs.getString("kd_jenis_prw")+"</td>"+
                                                "<td>"+rs.getString("nm_perawatan")+"</td>"+
                                            "</tr>"
                                );
                                tabModePK.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                                try {
                                    ps2=koneksi.prepareStatement(
                                            "select template_pemeriksaan_mcu_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                            "from template_pemeriksaan_mcu_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_mcu_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_mcu_detail_permintaan_lab.no_template=? and "+
                                            "template_pemeriksaan_mcu_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                    ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    ps2.setString(2,rs.getString("kd_jenis_prw"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        Valid.tabelKosong(tabModeDetailPK);
                                        htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td align='center' width='15%'></td>"+
                                                "<td width='85%'>"+
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                        "<tr class='isi'>"+
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Pemeriksaan</td>"+
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Satuan</td>"+
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Nilai Rujukan</td>"+
                                                        "</tr>"
                                        );
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            la="";ld="";pa="";pd="";
                                            if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                                ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                            }
                                            if(!rs2.getString("nilai_rujukan_la").equals("")){
                                                la=", LA : "+rs2.getString("nilai_rujukan_la");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                                pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                                pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                            }
                                            htmlContent.append(
                                                        "<tr class='isi'>"+
                                                            "<td>"+rs2.getString("Pemeriksaan")+"</td>"+
                                                            "<td align='center'>"+rs2.getString("satuan")+"</td>"+
                                                            "<td>"+ld+la+pd+pa+"</td>"+
                                                        "</tr>"
                                            );
                                            tabModeDetailPK.addRow(new Object[]{
                                                true,"   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                            });
                                        }
                                        htmlContent.append(
                                                    "</table>"+
                                                "</td>"+
                                            "</tr>"
                                        );
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                            htmlContent.append( 
                                        "</table>"+
                                    "</td>"+
                                "</tr>"
                            ); 
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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_mcu_tindakan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori from template_pemeriksaan_mcu_tindakan inner join jns_perawatan "+
                            "on template_pemeriksaan_mcu_tindakan.kd_jenis_prw=jns_perawatan.kd_jenis_prw inner join kategori_perawatan on kategori_perawatan.kd_kategori=jns_perawatan.kd_kategori "+
                            "where template_pemeriksaan_mcu_tindakan.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top' align='left' width='100%'>"+
                                        "Tindakan : "+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>Nama Perawatan/Tindakan</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30%'>Kategori</td>"+
                                            "</tr>"
                            );
                            Valid.tabelKosong(TabModeTindakan);
                            rs.beforeFirst();
                            while(rs.next()){
                                htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td align='center'>"+rs.getString("kd_jenis_prw")+"</td>"+
                                                "<td>"+rs.getString("nm_perawatan")+"</td>"+
                                                "<td align='center'>"+rs.getString("nm_kategori")+"</td>"+
                                            "</tr>"
                                );
                                TabModeTindakan.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori")
                                });
                            }
                            htmlContent.append( 
                                        "</table>"+
                                    "</td>"+
                                "</tr>"
                            ); 

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
                    
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>");
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }
    
    private void ganti(){
    if(Sequel.queryu2tf("delete from template_pemeriksaan_mcu where no_template=?",1,new String[]{
            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
        })==true){
            if(Sequel.menyimpantf("template_pemeriksaan_mcu","?,?","No.Template",2,new String[]{
                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Judul.getText()
            })==true){
                for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
                    if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_permintaan_radiologi","?,?","Pemeriksaan Radiologi",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanRadiologi.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                    if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanPK.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailPK.getRowCount();i++){ 
                    if((!tbDetailPK.getValueAt(i,4).toString().equals(""))&&tbDetailPK.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_mcu_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDetailPK.getValueAt(i,5).toString(),tbDetailPK.getValueAt(i,4).toString()
                        });
                    }
                }
                
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_mcu_tindakan","?,?","Tindakan Dokter",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbTindakan.getValueAt(i,1).toString()
                        });
                    }
                }
                tabMode.addRow(new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Judul.getText()
                });
                tabMode.removeRow(tbDokter.getSelectedRow());
                ChkAccor.setSelected(false);
                isDetail();
                TabRawat.setSelectedIndex(1);
            } 
        }else{
            JOptionPane.showMessageDialog(null,"Gagal mengganti..!!");
        }
    }
    
    private void hapus(){
        if(Sequel.queryu2tf("delete from template_pemeriksaan_mcu where no_template=?",1,new String[]{
            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbDokter.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            LoadHTML.setText("");
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}
