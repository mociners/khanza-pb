import net.sf.jasperreports.engine.*;
import java.io.File;

public class CompileJasper {
    public static void main(String[] args) {
        try {
            String sourceFileName = "report/rptLembarkonsul.jrxml";
            String destFileName = "report/rptLembarkonsul.jasper";

            System.out.println("Compiling report...");
            JasperCompileManager.compileReportToFile(sourceFileName, destFileName);
            System.out.println("Report compiled successfully!");
            System.out.println("Output: " + destFileName);
        } catch (Exception e) {
            System.err.println("Error compiling report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
