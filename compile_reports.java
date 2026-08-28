import net.sf.jasperreports.engine.JasperCompileManager;
public class compile_reports {
    public static void main(String[] args) {
        try {
            System.out.println("Compiling rptResepTelaah.jrxml...");
            JasperCompileManager.compileReportToFile("report/rptResepTelaah.jrxml", "report/rptResepTelaah.jasper");
            System.out.println("Compiling rptResepTelaah1.jrxml...");
            JasperCompileManager.compileReportToFile("report/rptResepTelaah1.jrxml", "report/rptResepTelaah1.jasper");
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
