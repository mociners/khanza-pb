import re

with open("src/rekammedis/RMSkriningMPP.java", "r") as f:
    c = f.read()

# We want to extract the imports and the basic structure.
# But it's complex. Let's write a clean standalone class using Khanza widgets.
java_code = """
package rekammedis;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import widget.Button;
import widget.Label;
import widget.PanelBiasa;
import widget.ScrollPane;
import widget.Table;
import widget.Tanggal;

public class RMSkriningMPPRekap extends JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    
    private PanelBiasa panelUtama;
    private PanelBiasa panelBawah;
    private Tanggal DTPCari1;
    private Tanggal DTPCari2;
    private Button BtnCari;
    private Button BtnKeluar;
    private Table tbObat;
    private ScrollPane scrollPane;

    public RMSkriningMPPRekap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Skrining","Jumlah Parameter Terisi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tbObat.getColumnModel().getColumn(0).setPreferredWidth(105);
        tbObat.getColumnModel().getColumn(1).setPreferredWidth(70);
        tbObat.getColumnModel().getColumn(2).setPreferredWidth(250);
        tbObat.getColumnModel().getColumn(3).setPreferredWidth(120);
        tbObat.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setTitle("Rekapitulasi Parameter Skrining MPP");
        panelUtama = new PanelBiasa();
        panelUtama.setLayout(new BorderLayout());
        
        scrollPane = new ScrollPane();
        tbObat = new Table();
        scrollPane.setViewportView(tbObat);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        
        panelBawah = new PanelBiasa();
        panelBawah.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelBawah.add(new Label("Tanggal : "));
        DTPCari1 = new Tanggal();
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        panelBawah.add(DTPCari1);
        
        panelBawah.add(new Label(" s.d. "));
        DTPCari2 = new Tanggal();
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        panelBawah.add(DTPCari2);
        
        BtnCari = new Button();
        BtnCari.setText("Cari");
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tampil();
            }
        });
        panelBawah.add(BtnCari);
        
        BtnKeluar = new Button();
        BtnKeluar.setText("Keluar");
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });
        panelBawah.add(BtnKeluar);
        
        panelUtama.add(panelBawah, BorderLayout.SOUTH);
        this.add(panelUtama);
    }

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps = koneksi.prepareStatement(
                "select mpp_skrining.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, mpp_skrining.tanggal, " +
                "(if(mpp_skrining.param1='Ya',1,0) + if(mpp_skrining.param2='Ya',1,0) + if(mpp_skrining.param3='Ya',1,0) + " +
                "if(mpp_skrining.param4='Ya',1,0) + if(mpp_skrining.param5='Ya',1,0) + if(mpp_skrining.param6='Ya',1,0) + " +
                "if(mpp_skrining.param7='Ya',1,0) + if(mpp_skrining.param8='Ya',1,0) + if(mpp_skrining.param9='Ya',1,0) + " +
                "if(mpp_skrining.param10='Ya',1,0) + if(mpp_skrining.param11='Ya',1,0) + if(mpp_skrining.param12='Ya',1,0) + " +
                "if(mpp_skrining.param13='Ya',1,0) + if(mpp_skrining.param14='Ya',1,0) + if(mpp_skrining.param15='Ya',1,0) + " +
                "if(mpp_skrining.param16='Ya',1,0) + if(mpp_skrining.param17='Ya',1,0)) as jumlah_terisi " +
                "from mpp_skrining inner join reg_periksa on mpp_skrining.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "where mpp_skrining.tanggal between ? and ? order by mpp_skrining.tanggal"
            );
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem()+"") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem()+"") + " 23:59:59");
                rs = ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("tanggal"),
                        rs.getString("jumlah_terisi")
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
        }
    }
}
"""

with open("src/rekammedis/RMSkriningMPPRekap.java", "w") as f:
    f.write(java_code)

print("RMSkriningMPPRekap.java generated!")
