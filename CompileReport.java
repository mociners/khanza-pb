import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileReport {
    public static void main(String[] args) {
        try {
            JasperCompileManager.compileReportToFile("report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml", "report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jasper");
            System.out.println("Report compiled successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
