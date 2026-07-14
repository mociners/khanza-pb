import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileJasper {
    public static void main(String[] args) {
        try {
            System.out.println("Compiling report/rptSuratPersetujuanUmum.jrxml ...");
            JasperCompileManager.compileReportToFile("report/rptSuratPersetujuanUmum.jrxml", "report/rptSuratPersetujuanUmum.jasper");
            System.out.println("Done compiling!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
