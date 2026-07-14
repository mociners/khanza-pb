import re

with open("src/rekammedis/GrafikBalanceCairanRanap.java", "r") as f:
    content = f.read()

# Remove static imports
content = content.replace('import static rekammedis.GrafikBalanceCairan.createDatasetBc;\n', '')
content = content.replace('import static rekammedis.GrafikBalanceCairan.createPanelBc;\n', '')

# Update Titles
content = content.replace('::[ Grafik Balance Cairan ]::', '::[ Grafik Observasi TTV ]::')
content = content.replace('"Balance Cairan", internalFrame9', '"Grafik TTV", internalFrame9')

# Replace chart generation methods
old_methods_pattern = r'    public static JPanel createPanelBc\(String noRawat\) \{.*?(?=      private void isForm\(\)\{)'
new_methods = """    public static JPanel createPanelBc(String noRawat) {
        JFreeChart chart = createChartBc(noRawat);
        return new ChartPanel(chart);
    }

    private static JFreeChart createChartBc(String noRawat) {
        // Nadi Dataset
        CategoryDataset datasetNadi = createDataset(noRawat, "nadi", "Nadi");
        NumberAxis axisNadi = new NumberAxis("Nadi");
        axisNadi.setRange(40.0, 160.0);
        LineAndShapeRenderer rendererNadi = new LineAndShapeRenderer(true, true);
        rendererNadi.setSeriesPaint(0, java.awt.Color.RED);
        rendererNadi.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

        CategoryPlot subplot = new CategoryPlot();
        subplot.setDomainGridlinesVisible(true);
        
        subplot.setDataset(0, datasetNadi);
        subplot.setRangeAxis(0, axisNadi);
        subplot.setRenderer(0, rendererNadi);
        subplot.mapDatasetToRangeAxis(0, 0);

        // Respirasi Dataset
        CategoryDataset datasetResp = createDataset(noRawat, "respirasi", "Respirasi");
        NumberAxis axisResp = new NumberAxis("Respirasi");
        axisResp.setRange(0.0, 60.0);
        LineAndShapeRenderer rendererResp = new LineAndShapeRenderer(true, true);
        rendererResp.setSeriesPaint(0, java.awt.Color.BLUE);
        rendererResp.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        
        subplot.setDataset(1, datasetResp);
        subplot.setRangeAxis(1, axisResp);
        subplot.setRenderer(1, rendererResp);
        subplot.mapDatasetToRangeAxis(1, 1);

        // Suhu Dataset
        CategoryDataset datasetSuhu = createDataset(noRawat, "suhu", "Suhu");
        NumberAxis axisSuhu = new NumberAxis("Suhu");
        axisSuhu.setRange(35.0, 41.0);
        LineAndShapeRenderer rendererSuhu = new LineAndShapeRenderer(true, true);
        rendererSuhu.setSeriesPaint(0, java.awt.Color.GREEN);
        rendererSuhu.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        
        subplot.setDataset(2, datasetSuhu);
        subplot.setRangeAxis(2, axisSuhu);
        subplot.setRenderer(2, rendererSuhu);
        subplot.mapDatasetToRangeAxis(2, 2);

        // Put all axes on the left
        subplot.setRangeAxisLocation(0, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);
        subplot.setRangeAxisLocation(1, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);
        subplot.setRangeAxisLocation(2, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);
        
        CategoryAxis domainAxis = new CategoryAxis("");
        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        plot.add(subplot, 1);
        CategoryAxis domainAxis2 = plot.getDomainAxis();
        domainAxis2.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
        
        JFreeChart result = new JFreeChart(
                "Grafik Observasi TTV (Nadi, Respirasi, Suhu)",
                new Font("SansSerif", Font.PLAIN, 12), plot, true);
        return result;
    }

    public static CategoryDataset createDataset(String noRawat, String field, String seriesName) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery("select concat(tanggal,' ',jam) as period, " + field + " as nilai " +
               "from rm_ttv_balance_cairan where no_rawat='"+noRawat+"' order by tanggal, jam");
            while (rs.next()) {
                String tksbr = rs.getString("period");
                String valStr = rs.getString("nilai");
                double val = 0;
                try {
                    val = Double.parseDouble(valStr);
                } catch(Exception e) {
                    val = 0;
                }
                if (val > 0) {
                    result.addValue(val, seriesName, tksbr);
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi Q: " + e);
        }
        return result;
    }

"""
content = re.sub(old_methods_pattern, new_methods, content, flags=re.DOTALL)

with open("src/rekammedis/GrafikBalanceCairanRanap.java", "w") as f:
    f.write(content)
