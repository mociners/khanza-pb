import net.sf.jasperreports.engine.*;
import java.io.File;

public class CompileJasper3 {
    public static void main(String[] args) {
        try {
            String source = args.length > 0 ? args[0] : "report/rptBuktiRegister.jrxml";
            String dest = source.replace(".jrxml", ".jasper");

            System.setProperty("jasper.reports.compiler.class", "net.sf.jasperreports.engine.design.JRJavacCompiler");

            System.out.println("Compiling " + source + " -> " + dest);
            JasperCompileManager.compileReportToFile(source, dest);
            System.out.println("Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
