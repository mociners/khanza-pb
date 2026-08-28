import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileReport {
    public static void main(String[] args) {
        try {
            JasperCompileManager.compileReportToFile("report/rptLaporanResumeRanap.jrxml", "report/rptLaporanResumeRanap.jasper");
            System.out.println("Compilation successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
