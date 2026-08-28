import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileJasper {
    public static void main(String[] args) {
        try {
            System.setProperty("net.sf.jasperreports.compiler.xml.validation", "false");
            System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
            System.out.println("Compiling report/rptFormulirLaporanOperasi.jrxml...");
            JasperCompileManager.compileReportToFile("report/rptFormulirLaporanOperasi.jrxml", "report/rptFormulirLaporanOperasi.jasper");
            JasperCompileManager.compileReportToFile("report/rptFormulirLaporanOperasi.jrxml", "dist/report/rptFormulirLaporanOperasi.jasper");
            System.out.println("Compilation successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
