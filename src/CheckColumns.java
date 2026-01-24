
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class CheckColumns {
    public static void main(String[] args) {
        try {
            Connection koneksi = koneksiDB.condb();
            Statement stat = koneksi.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM rujukan_internal_poli LIMIT 1");
            ResultSetMetaData meta = rs.getMetaData();
            int count = meta.getColumnCount();
            System.out.println("Columns in rujukan_internal_poli:");
            for (int i = 1; i <= count; i++) {
                System.out.println(i + ". " + meta.getColumnName(i) + " (" + meta.getColumnTypeName(i) + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
