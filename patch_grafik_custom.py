import re

java_code = """
    public static JFreeChart createChartBcCustom(java.util.List<String[]> pageData) {
        org.jfree.data.category.DefaultCategoryDataset datasetNadi = new org.jfree.data.category.DefaultCategoryDataset();
        org.jfree.data.category.DefaultCategoryDataset datasetResp = new org.jfree.data.category.DefaultCategoryDataset();
        org.jfree.data.category.DefaultCategoryDataset datasetSuhu = new org.jfree.data.category.DefaultCategoryDataset();
        
        for (int i=0; i<12; i++) {
            String[] arr = pageData.get(i);
            String period = String.valueOf(i+1); // Dummy unique category
            
            // Nadi (index 22)
            if (arr[22] != null && !arr[22].isEmpty() && !arr[22].equals("-")) {
                try { datasetNadi.addValue(Double.parseDouble(arr[22]), "Nadi", period); } catch(Exception e) {}
            } else { datasetNadi.addValue(null, "Nadi", period); }
            
            // Respirasi (index 23)
            if (arr[23] != null && !arr[23].isEmpty() && !arr[23].equals("-")) {
                try { datasetResp.addValue(Double.parseDouble(arr[23]), "Respirasi", period); } catch(Exception e) {}
            } else { datasetResp.addValue(null, "Respirasi", period); }
            
            // Suhu (index 24)
            if (arr[24] != null && !arr[24].isEmpty() && !arr[24].equals("-")) {
                try { datasetSuhu.addValue(Double.parseDouble(arr[24]), "Suhu", period); } catch(Exception e) {}
            } else { datasetSuhu.addValue(null, "Suhu", period); }
        }

        org.jfree.chart.labels.CategoryItemLabelGenerator timeLabelGenerator = new org.jfree.chart.labels.StandardCategoryItemLabelGenerator() {
            @Override
            public String generateLabel(org.jfree.data.category.CategoryDataset ds, int row, int col) {
                // Return time from pageData
                int idx = Integer.parseInt(ds.getColumnKey(col).toString()) - 1;
                String[] arr = pageData.get(idx);
                if (arr[1] != null && arr[1].length() >= 5) {
                    return arr[1].substring(0, 5); // HH:mm
                }
                return "";
            }
        };

        org.jfree.chart.axis.NumberAxis axisNadi = new org.jfree.chart.axis.NumberAxis("Nadi");
        axisNadi.setRange(30.0, 170.0);
        axisNadi.setTickLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));
        axisNadi.setLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
        org.jfree.chart.renderer.category.LineAndShapeRenderer rendererNadi = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
        rendererNadi.setSeriesPaint(0, java.awt.Color.RED);
        rendererNadi.setBaseItemLabelsVisible(true);
        rendererNadi.setBaseItemLabelGenerator(timeLabelGenerator);
        rendererNadi.setBaseItemLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));

        org.jfree.chart.plot.CategoryPlot subplot = new org.jfree.chart.plot.CategoryPlot();
        subplot.setDomainGridlinesVisible(true);
        subplot.setRangeGridlinesVisible(true);
        subplot.setRangeGridlinePaint(java.awt.Color.LIGHT_GRAY);
        subplot.setDomainGridlinePaint(java.awt.Color.LIGHT_GRAY);
        subplot.setBackgroundPaint(java.awt.Color.WHITE); // Make plot white

        subplot.setDataset(0, datasetNadi);
        subplot.setRangeAxis(0, axisNadi);
        subplot.setRenderer(0, rendererNadi);
        subplot.mapDatasetToRangeAxis(0, 0);

        org.jfree.chart.axis.NumberAxis axisResp = new org.jfree.chart.axis.NumberAxis("Resp");
        axisResp.setRange(-5.0, 65.0);
        axisResp.setTickLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));
        axisResp.setLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
        org.jfree.chart.renderer.category.LineAndShapeRenderer rendererResp = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
        rendererResp.setSeriesPaint(0, java.awt.Color.BLACK);
        rendererResp.setBaseItemLabelsVisible(true);
        rendererResp.setBaseItemLabelGenerator(timeLabelGenerator);
        rendererResp.setBaseItemLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));

        subplot.setDataset(1, datasetResp);
        subplot.setRangeAxis(1, axisResp);
        subplot.setRenderer(1, rendererResp);
        subplot.mapDatasetToRangeAxis(1, 1);

        org.jfree.chart.axis.NumberAxis axisSuhu = new org.jfree.chart.axis.NumberAxis("Suhu");
        axisSuhu.setRange(34.5, 41.5);
        axisSuhu.setTickLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));
        axisSuhu.setLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 9));
        org.jfree.chart.renderer.category.LineAndShapeRenderer rendererSuhu = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
        rendererSuhu.setSeriesPaint(0, java.awt.Color.BLUE);
        rendererSuhu.setBaseItemLabelsVisible(true);
        rendererSuhu.setBaseItemLabelGenerator(timeLabelGenerator);
        rendererSuhu.setBaseItemLabelFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 8));

        subplot.setDataset(2, datasetSuhu);
        subplot.setRangeAxis(2, axisSuhu);
        subplot.setRenderer(2, rendererSuhu);
        subplot.mapDatasetToRangeAxis(2, 2);

        subplot.setRangeAxisLocation(0, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);
        subplot.setRangeAxisLocation(1, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);
        subplot.setRangeAxisLocation(2, org.jfree.chart.axis.AxisLocation.BOTTOM_OR_LEFT);

        org.jfree.chart.axis.CategoryAxis domainAxis = new org.jfree.chart.axis.CategoryAxis("");
        domainAxis.setTickLabelsVisible(false); // Hide X-axis labels
        domainAxis.setTickMarksVisible(false); // Hide X-axis ticks
        // Make the category margins such that the points align with table columns
        domainAxis.setLowerMargin(0.04); 
        domainAxis.setUpperMargin(0.04);

        org.jfree.chart.plot.CombinedDomainCategoryPlot plot = new org.jfree.chart.plot.CombinedDomainCategoryPlot(domainAxis);
        plot.add(subplot, 1);
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setGap(0); // Remove gap

        JFreeChart result = new JFreeChart(
                null, // Remove Title
                new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12),
                plot,
                true // Legend on
        );
        result.setBackgroundPaint(java.awt.Color.WHITE);
        
        // Put legend at the top so it doesn't take bottom space
        org.jfree.chart.title.LegendTitle legend = result.getLegend();
        legend.setPosition(org.jfree.ui.RectangleEdge.TOP);
        legend.setBackgroundPaint(java.awt.Color.WHITE);

        return result;
    }
"""

with open("src/rekammedis/GrafikBalanceCairanRanap.java", "r") as f:
    content = f.read()

# Insert before createChartBc
pattern = r"    public static JFreeChart createChartBc\(String noRawat\) \{"
content = content.replace("    public static JFreeChart createChartBc(String noRawat) {", java_code + "\n    public static JFreeChart createChartBc(String noRawat) {")

with open("src/rekammedis/GrafikBalanceCairanRanap.java", "w") as f:
    f.write(content)

print("Added createChartBcCustom to GrafikBalanceCairanRanap")
