import re

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
            "No.","Nama Parameter","Jumlah Terisi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tbObat.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbObat.getColumnModel().getColumn(1).setPreferredWidth(250);
        tbObat.getColumnModel().getColumn(2).setPreferredWidth(150);
        
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        this.setSize(500, 450);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setTitle("Rekapitulasi Parameter Skrining MPP (Global)");
        panelUtama = new PanelBiasa();
        panelUtama.setLayout(new BorderLayout());
        
        scrollPane = new ScrollPane();
        tbObat = new Table();
        scrollPane.setViewportView(tbObat);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        
        panelBawah = new PanelBiasa();
        panelBawah.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Label lblTanggal = new Label();
        lblTanggal.setText("Tanggal : ");
        panelBawah.add(lblTanggal);
        DTPCari1 = new Tanggal();
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        panelBawah.add(DTPCari1);
        
        Label lblSd = new Label();
        lblSd.setText(" s.d. ");
        panelBawah.add(lblSd);
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
                "select " +
                "SUM(if(param1='Ya',1,0)) as p1, " +
                "SUM(if(param2='Ya',1,0)) as p2, " +
                "SUM(if(param3='Ya',1,0)) as p3, " +
                "SUM(if(param4='Ya',1,0)) as p4, " +
                "SUM(if(param5='Ya',1,0)) as p5, " +
                "SUM(if(param6='Ya',1,0)) as p6, " +
                "SUM(if(param7='Ya',1,0)) as p7, " +
                "SUM(if(param8='Ya',1,0)) as p8, " +
                "SUM(if(param9='Ya',1,0)) as p9, " +
                "SUM(if(param10='Ya',1,0)) as p10, " +
                "SUM(if(param11='Ya',1,0)) as p11, " +
                "SUM(if(param12='Ya',1,0)) as p12, " +
                "SUM(if(param13='Ya',1,0)) as p13, " +
                "SUM(if(param14='Ya',1,0)) as p14, " +
                "SUM(if(param15='Ya',1,0)) as p15, " +
                "SUM(if(param16='Ya',1,0)) as p16, " +
                "SUM(if(param17='Ya',1,0)) as p17 " +
                "from mpp_skrining where tanggal between ? and ?"
            );
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem()+"") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem()+"") + " 23:59:59");
                rs = ps.executeQuery();
                if(rs.next()){
                    for(int x = 1; x <= 17; x++){
                        tabMode.addRow(new String[]{
                            String.valueOf(x),
                            "Parameter " + x,
                            rs.getString("p" + x) != null ? rs.getString("p" + x) : "0"
                        });
                    }
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

print("RMSkriningMPPRekap.java completely rewritten!")
