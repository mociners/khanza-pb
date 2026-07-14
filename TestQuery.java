import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import fungsi.koneksiDB;

public class TestQuery {
    public static void main(String[] args) {
        try {
            Connection connect = koneksiDB.condb();
            String sql = "select no_resep, SUBSTRING(no_resep,9) as sub, CONVERT(SUBSTRING(no_resep,9),signed) as conv from resep_obat order by no_resep desc limit 10";
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("--- Top 10 by no_resep DESC ---");
            while(rs.next()) {
                System.out.println(rs.getString("no_resep") + " | " + rs.getString("sub") + " | " + rs.getInt("conv"));
            }
            rs.close();
            ps.close();

            sql = "select ifnull(MAX(CONVERT(SUBSTRING(no_resep,9),signed)),0) as max_val from resep_obat where tgl_peresepan='2026-06-09' or tgl_perawatan='2026-06-09'";
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println("MAX = " + rs.getInt("max_val"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
