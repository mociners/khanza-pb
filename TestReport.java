
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.*;

public class TestReport {
    public static void main(String[] args) {
        try {
            net.sf.jasperreports.engine.DefaultJasperReportsContext.getInstance().setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJavacCompiler");
            System.out.println("Compiling report to file...");
            JasperCompileManager.compileReportToFile("report/rptResepTelaah.jrxml", "report/rptResepTelaah.jasper");
            System.out.println("Compiling report in memory...");
            JasperReport report = JasperCompileManager.compileReport("report/rptResepTelaah.jrxml");
            
            Map<String, Object> params = new HashMap<>();
            params.put("penanggung", "BPJS");
            params.put("namars", "RS Suka Sehat");
            params.put("alamatrs", "Jl. Raya Sehat");
            params.put("kotars", "Jakarta");
            params.put("propinsirs", "DKI Jakarta");
            params.put("kontakrs", "021-123456");
            params.put("peresep", "Dr. Budi");
            params.put("tanggal", "05-05-2026");
            params.put("norm", "123456");
            params.put("pasien", "Pasien Test");
            params.put("umur", "30 Th");
            params.put("tanggallahir", "01-01-1996");
            params.put("alamat", "Jl. Test");
            params.put("bb", "60");
            params.put("tb", "170");
            params.put("sipdokter", "SIP.123/2026/DOKTER");
            params.put("no_tlp", "081234567890");

            List<Map<String, Object>> data = new ArrayList<>();
            for(int i=0; i<3; i++) {
                Map<String, Object> row = new HashMap<>();
                row.put("temp1", "Paracetamol 500mg");
                row.put("temp3", "10");
                row.put("temp2", "3x1 sesudah makan");
                data.add(row);
            }

            JRDataSource ds = new JRBeanCollectionDataSource(data);
            
            System.out.println("Filling report...");
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);
            
            System.out.println("Exporting to HTML...");
            JasperExportManager.exportReportToHtmlFile(print, "test_report.html");
            System.out.println("Success! HTML generated at test_report.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
