package simrskhanza;

import fungsi.koneksiDB;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import widget.editorpane;

public class DlgLiatFotoKeluarga extends JDialog {
    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private editorpane LoadHTML = new widget.editorpane();
    private JScrollPane scrollPane = new JScrollPane();

    private final String webFolder = "pernyataanumum/pages/upload";

    public DlgLiatFotoKeluarga(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Bukti Foto Persetujuan Umum");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        LoadHTML.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body { font-family: tahoma; font-size: 11px; }");
        styleSheet.addRule("img { border: 2px solid #000; }");
        LoadHTML.setDocument(kit.createDefaultDocument());
        
        scrollPane.setViewportView(LoadHTML);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setNoRawat(String noRawat) {
        String lokasifile = "";
        try {
            String sql = "select surat_persetujuan_umum_pembuat_pernyataan.photo " +
                         "from surat_persetujuan_umum_pembuat_pernyataan " +
                         "inner join surat_persetujuan_umum on surat_persetujuan_umum_pembuat_pernyataan.no_surat = surat_persetujuan_umum.no_surat " +
                         "where surat_persetujuan_umum.no_rawat=?";
            
            ps = koneksi.prepareStatement(sql);
            try {
                ps.setString(1, noRawat);
                rs = ps.executeQuery();
                if (rs.next()) {
                    lokasifile = rs.getString("photo");
                    
                    if (lokasifile == null || lokasifile.equals("")) {
                        LoadHTML.setText("<html><body><center><br><br><font size='5' color='#FF0000'>Data foto kosong.</font></center></body></html>");
                    } else {
                        String serverUrl = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/" + webFolder + "/" + lokasifile;
                        
                        System.out.println("Menampilkan Foto Persetujuan: " + serverUrl);

                        LoadHTML.setText("<html><body><center><img src='" + serverUrl + "' width='500' height='500'/></center></body></html>");
                    }
                } else {
                    LoadHTML.setText("<html><body><center><br><br><font size='4'>Belum ada Surat Persetujuan Umum untuk No.Rawat ini.</font></center></body></html>");
                }
            } catch (Exception e) {
                System.out.println("Error Load Foto: " + e);
                LoadHTML.setText("<html><body><center>Error: " + e.getMessage() + "</center></body></html>");
            } finally {
                if(rs!=null) rs.close();
                if(ps!=null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Error Query: " + e);
        }
    }
}