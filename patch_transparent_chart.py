import re

java_logic = r"""
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                java.util.Map<String, Object> param = new java.util.HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                param.put("diagnosa", TDiagnosa.getText());
                param.put("ruang", TKamar.getText());
                param.put("norm", TNoRM.getText());
                param.put("nama", TPasien.getText());
                param.put("tgl_lahir", TglLahir.getText());
                param.put("jk", Jk.getText());

                // Fetch all data
                java.util.List<String[]> dataList = new java.util.ArrayList<>();
                ps = koneksi.prepareStatement(
                        "select tanggal, jam, tensi, bb, tb, diet, kode_infus, " +
                        "nadi, respirasi, suhu, " +
                        "intake_makan, intake_minum, intake_ngt, intake_transfusi, intake_infus, intake_sisa_infus, " +
                        "jumlah_input, jumlah_input_24, output_urine, output_muntah, output_ngt, output_iwl, output_drain, " +
                        "jumlah_output, jumlah_output_24, balance_24 " +
                        "from rm_ttv_balance_cairan where no_rawat=? order by tanggal, jam");
                try {
                    ps.setString(1, TNoRw.getText());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        String[] arr = new String[26];
                        arr[0] = rs.getString("tanggal");
                        arr[1] = rs.getString("jam");
                        arr[2] = rs.getString("tensi");
                        String bb = rs.getString("bb"); String tb = rs.getString("tb");
                        arr[3] = (bb != null ? bb : "") + " / " + (tb != null ? tb : "");
                        arr[4] = rs.getString("diet");
                        arr[5] = rs.getString("kode_infus");
                        arr[6] = rs.getString("nadi");
                        arr[7] = rs.getString("respirasi");
                        arr[8] = rs.getString("suhu");
                        arr[9] = rs.getString("intake_makan");
                        arr[10] = rs.getString("intake_minum");
                        arr[11] = rs.getString("intake_ngt");
                        arr[12] = rs.getString("intake_transfusi");
                        arr[13] = rs.getString("intake_infus");
                        arr[14] = rs.getString("intake_sisa_infus");
                        arr[15] = rs.getString("jumlah_input");
                        arr[16] = rs.getString("jumlah_input_24");
                        arr[17] = rs.getString("output_urine");
                        arr[18] = rs.getString("output_muntah");
                        arr[19] = rs.getString("output_ngt");
                        arr[20] = rs.getString("output_iwl");
                        arr[21] = rs.getString("output_drain");
                        arr[22] = rs.getString("jumlah_output");
                        arr[23] = rs.getString("jumlah_output_24");
                        arr[24] = rs.getString("balance_24");
                        dataList.add(arr);
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }

                javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
                model.addColumn("page_number");
                model.addColumn("kelompok");
                model.addColumn("row_label");
                for (int i=1; i<=12; i++) model.addColumn("col"+i);
                model.addColumn("chart_image"); // Pass the chart image

                int recordsPerPage = 12;
                int totalPages = (int) Math.ceil((double)dataList.size() / recordsPerPage);
                if (totalPages == 0) totalPages = 1;

                for (int p=0; p<totalPages; p++) {
                    int pageNum = p + 1;
                    int startIdx = p * recordsPerPage;

                    // --- GENERATE CHART FOR THIS PAGE ---
                    org.jfree.data.category.DefaultCategoryDataset nadiDataset = new org.jfree.data.category.DefaultCategoryDataset();
                    org.jfree.data.category.DefaultCategoryDataset respDataset = new org.jfree.data.category.DefaultCategoryDataset();
                    org.jfree.data.category.DefaultCategoryDataset suhuDataset = new org.jfree.data.category.DefaultCategoryDataset();

                    for (int c=0; c<12; c++) {
                        int di = startIdx + c;
                        String cat = "C" + c;
                        if (di < dataList.size()) {
                            String[] d = dataList.get(di);
                            try { nadiDataset.addValue(Double.parseDouble(d[6]), "Nadi", cat); } catch(Exception e) { nadiDataset.addValue(null, "Nadi", cat); }
                            try { respDataset.addValue(Double.parseDouble(d[7]), "Respirasi", cat); } catch(Exception e) { respDataset.addValue(null, "Respirasi", cat); }
                            try { suhuDataset.addValue(Double.parseDouble(d[8]), "Suhu", cat); } catch(Exception e) { suhuDataset.addValue(null, "Suhu", cat); }
                        } else {
                            nadiDataset.addValue(null, "Nadi", cat);
                            respDataset.addValue(null, "Respirasi", cat);
                            suhuDataset.addValue(null, "Suhu", cat);
                        }
                    }

                    // Create chart (transparent)
                    org.jfree.chart.plot.CategoryPlot plot = new org.jfree.chart.plot.CategoryPlot();
                    
                    // Renderer for Nadi (Red Square)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendNadi = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendNadi.setSeriesPaint(0, java.awt.Color.RED);
                    rendNadi.setSeriesShape(0, new java.awt.geom.Rectangle2D.Double(-3, -3, 6, 6));
                    plot.setDataset(0, nadiDataset);
                    plot.setRenderer(0, rendNadi);

                    // Renderer for Respirasi (Black Circle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendResp = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendResp.setSeriesPaint(0, java.awt.Color.BLACK);
                    rendResp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));
                    plot.setDataset(1, respDataset);
                    plot.setRenderer(1, rendResp);

                    // Renderer for Suhu (Blue Triangle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendSuhu = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendSuhu.setSeriesPaint(0, java.awt.Color.BLUE);
                    java.awt.geom.GeneralPath triangle = new java.awt.geom.GeneralPath();
                    triangle.moveTo(0, -4); triangle.lineTo(4, 4); triangle.lineTo(-4, 4); triangle.closePath();
                    rendSuhu.setSeriesShape(0, triangle);
                    plot.setDataset(2, suhuDataset);
                    plot.setRenderer(2, rendSuhu);

                    // Axes (Hidden text, but explicit ranges for perfectly matched gridlines)
                    org.jfree.chart.axis.CategoryAxis domainAxis = new org.jfree.chart.axis.CategoryAxis();
                    domainAxis.setTickLabelsVisible(false);
                    domainAxis.setTickMarksVisible(false);
                    domainAxis.setAxisLineVisible(false);
                    // VERY IMPORTANT: Margin 0 so the first/last category fills exactly its 1/12th width box!
                    domainAxis.setLowerMargin(0.0);
                    domainAxis.setUpperMargin(0.0);
                    plot.setDomainAxis(domainAxis);

                    org.jfree.chart.axis.NumberAxis axisNadi = new org.jfree.chart.axis.NumberAxis();
                    axisNadi.setRange(40.0, 160.0);
                    axisNadi.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(20.0));
                    axisNadi.setTickLabelsVisible(false);
                    axisNadi.setTickMarksVisible(false);
                    axisNadi.setAxisLineVisible(false);
                    plot.setRangeAxis(0, axisNadi);
                    plot.mapDatasetToRangeAxis(0, 0);

                    org.jfree.chart.axis.NumberAxis axisResp = new org.jfree.chart.axis.NumberAxis();
                    axisResp.setRange(0.0, 60.0);
                    axisResp.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(10.0));
                    axisResp.setTickLabelsVisible(false);
                    axisResp.setTickMarksVisible(false);
                    axisResp.setAxisLineVisible(false);
                    plot.setRangeAxis(1, axisResp);
                    plot.mapDatasetToRangeAxis(1, 1);

                    org.jfree.chart.axis.NumberAxis axisSuhu = new org.jfree.chart.axis.NumberAxis();
                    axisSuhu.setRange(35.0, 41.0);
                    axisSuhu.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1.0));
                    axisSuhu.setTickLabelsVisible(false);
                    axisSuhu.setTickMarksVisible(false);
                    axisSuhu.setAxisLineVisible(false);
                    plot.setRangeAxis(2, axisSuhu);
                    plot.mapDatasetToRangeAxis(2, 2);

                    // Transparent background!
                    plot.setBackgroundPaint(new java.awt.Color(0,0,0,0));
                    plot.setOutlineVisible(false);
                    
                    // Gridlines
                    plot.setRangeGridlinesVisible(true);
                    plot.setRangeGridlinePaint(new java.awt.Color(200,200,200));
                    plot.setDomainGridlinesVisible(false);

                    org.jfree.chart.JFreeChart chart = new org.jfree.chart.JFreeChart(plot);
                    chart.setBackgroundPaint(new java.awt.Color(0,0,0,0));
                    chart.removeLegend();

                    // Generate image
                    java.awt.image.BufferedImage chartImg = chart.createBufferedImage(672, 120, java.awt.image.BufferedImage.TYPE_INT_ARGB, null);

                    // --- POPULATE TABLE MODEL ---
                    String[] headerLabels = {"Tanggal", "Jam", "Kode Infus/transf."};
                    int[] headerIdx = {0, 1, 5};
                    for (int r=0; r<headerLabels.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "HEAD";
                        rowData[2] = headerLabels[r];
                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            rowData[3+c] = (di < dataList.size()) ? dataList.get(di)[headerIdx[r]] : "";
                        }
                        rowData[15] = chartImg;
                        model.addRow(rowData);
                    }

                    // ADD THE CHART PLACEHOLDER ROW
                    Object[] chartRow = new Object[16];
                    chartRow[0] = pageNum;
                    chartRow[1] = "CHART";
                    chartRow[2] = "CHART";
                    chartRow[15] = chartImg;
                    model.addRow(chartRow);

                    // --- DATA ROWS (TD, BB/TB, Diet, Intake, Output) ---
                    String[] dataLabels = {
                        "TD", "BB / TB", "Diet", "Interval/6 jam :",
                        "    Makan", "    Minum", "    NGT", "    Transfusi", "    Infus", "    Sisa Infus",
                        "Jumlah Input", "Jumlah input/24 jam",
                        "OUTPUT",
                        "    Urine", "    Muntah", "    NGT ", "    IWL", "    Drain",
                        "Jumlah Output", "Jumlah Output/24 Jam", "Jumlah Total/24 Jam"
                    };
                    int[] dataIdx = {
                        2, 3, 4, -1,
                        9, 10, 11, 12, 13, 14,
                        15, 16,
                        -1,
                        17, 18, 19, 20, 21,
                        22, 23, 24
                    };

                    for (int r=0; r<dataLabels.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "DATA";
                        rowData[2] = dataLabels[r];
                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            if (di < dataList.size()) {
                                if (dataIdx[r] == -1) {
                                    rowData[3+c] = "";
                                } else {
                                    String val = dataList.get(di)[dataIdx[r]];
                                    rowData[3+c] = (val != null ? val : "-");
                                }
                            } else {
                                rowData[3+c] = "";
                            }
                        }
                        rowData[15] = chartImg;
                        model.addRow(rowData);
                    }
                }

                Valid.MyReport("./report/rptObservasiTTVBalance.jasper", param, new net.sf.jasperreports.engine.data.JRTableModelDataSource(model));
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
"""

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

start_marker = "    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed"
end_marker = "    }//GEN-LAST:event_BtnPrintActionPerformed"

start_pos = content.find(start_marker)
end_pos = content.find(end_marker, start_pos)
if start_pos == -1 or end_pos == -1:
    print("ERROR: Could not find markers")
else:
    end_pos += len(end_marker)
    new_method = start_marker + "\n" + java_logic + "    }//GEN-LAST:event_BtnPrintActionPerformed"
    content = content[:start_pos] + new_method + content[end_pos:]
    with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
        f.write(content)
    print("Patched RMTTVBalanceCairan.java - Transparent Chart")
