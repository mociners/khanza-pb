package kepegawaian;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgDetailKeterlambatan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String tglAwal="",tglAkhir="";

    public DlgDetailKeterlambatan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "Tanggal","Shift","Jam Datang","Jam Pulang","Status","Keterlambatan","Durasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetail.setModel(tabMode);
        tbDetail.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }
        }
        tbDetail.setDefaultRenderer(Object.class, new WarnaTable());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDetail = new widget.Table();
        panelGlass5 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail Keterlambatan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); 
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setOpaque(true);
        tbDetail.setAutoCreateRowSorter(true);
        Scroll.setViewportView(tbDetail);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass5.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass5.add(LCount);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        dispose();
    }                                         

    // Variables declaration - do not modify                     
    private widget.Button BtnKeluar;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.panelisi panelGlass5;
    private widget.Table tbDetail;
    // End of variables declaration                   

    public void setData(String nip, String nama, String tahun, String bulan, int jenisKolom){
        int thnAngka = Integer.parseInt(tahun);
        int blnAngka = Integer.parseInt(bulan);
        if (blnAngka == 1) {
            tglAwal = (thnAngka - 1) + "-12-26 00:00:00";
        } else {
            String blnLalu = String.format("%02d", (blnAngka - 1));
            tglAwal = tahun + "-" + blnLalu + "-26 00:00:00";
        }
        tglAkhir = tahun + "-" + bulan + "-25 23:59:59";
        
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail "+nama+" ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); 
        tampil(nip, jenisKolom);
    }

    private void tampil(String nip, int jenisKolom) {
        Valid.tabelKosong(tabMode);
        try {
            int toleransiMenit = 0; 
            
            PreparedStatement psToleransi = koneksi.prepareStatement("select toleransi from set_keterlambatan limit 1");
            try {
                ResultSet rsToleransi = psToleransi.executeQuery();
                if (rsToleransi.next()) {
                    toleransiMenit = rsToleransi.getInt("toleransi");
                }
                if (toleransiMenit == 0) {
                    toleransiMenit = 15;
                }
            } catch (Exception e) {
                System.out.println("Error Load Toleransi: " + e);
                toleransiMenit = 15;
            } finally {
                if(psToleransi != null) psToleransi.close();
            }
            
            int toleransiDetik = toleransiMenit * 60;
            
            String idPegawai = "";
            ps = koneksi.prepareStatement("select id from pegawai where nik=?");
            try {
                ps.setString(1, nip);
                rs = ps.executeQuery();
                if(rs.next()){
                    idPegawai = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println("Error ID: "+e);
            } finally{
                if(rs!=null) rs.close();
                if(ps!=null) ps.close();
            }

            if(!idPegawai.equals("")){
                String sql = "select jam_datang, shift, jam_datang, jam_pulang, status, keterlambatan, durasi " +
                             "from rekap_presensi where id=? and jam_datang between ? and ? ";
                
                if(jenisKolom == 11){
                    sql += "and TIME_TO_SEC(durasi) < 60 ";
                    
                } else if(jenisKolom == 13){
                    sql += "and TIME_TO_SEC(durasi) >= 60 " +
                           "and floor(TIME_TO_SEC(keterlambatan)/"+toleransiDetik+") > 0 ";
                }

                sql += "order by jam_datang";

                ps = koneksi.prepareStatement(sql);
                try {
                    ps.setString(1, idPegawai);
                    ps.setString(2, tglAwal);
                    ps.setString(3, tglAkhir);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        String tgl = rs.getString(1).substring(0, 10);
                        tabMode.addRow(new Object[]{
                            tgl,
                            rs.getString(2),
                            rs.getString(3).substring(11),
                            rs.getString(4).substring(11),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Error Data: "+e);
                } finally{
                    if(rs!=null) rs.close();
                    if(ps!=null) ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
}