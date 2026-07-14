import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try {
            Connection conn = koneksiDB.condb();
            Statement stmt = conn.createStatement();
            
            String sql1 = "CREATE TABLE IF NOT EXISTS `template_resep_dokter_umum` (" +
                          "`kd_template` varchar(20) NOT NULL," +
                          "`nm_template` varchar(150) DEFAULT NULL," +
                          "`kd_dokter` varchar(20) DEFAULT NULL," +
                          "PRIMARY KEY (`kd_template`)," +
                          "KEY `kd_dokter` (`kd_dokter`)" +
                          ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
                          
            String sql2 = "CREATE TABLE IF NOT EXISTS `template_resep_dokter_umum_detail` (" +
                          "`kd_template` varchar(20) NOT NULL," +
                          "`kode_brng` varchar(15) NOT NULL," +
                          "`jml` double DEFAULT NULL," +
                          "`aturan_pakai` varchar(150) DEFAULT NULL," +
                          "PRIMARY KEY (`kd_template`,`kode_brng`)," +
                          "KEY `kode_brng` (`kode_brng`)," +
                          "CONSTRAINT `template_resep_dokter_umum_detail_ibfk_1` FOREIGN KEY (`kd_template`) REFERENCES `template_resep_dokter_umum` (`kd_template`) ON DELETE CASCADE ON UPDATE CASCADE," +
                          "CONSTRAINT `template_resep_dokter_umum_detail_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE" +
                          ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
                          
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            System.out.println("Tables created successfully.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
