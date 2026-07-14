import re

java_logic = """
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

                // Fetch data including nadi, respirasi, suhu
                java.util.List<String[]> dataList = new java.util.ArrayList<>();
                ps = koneksi.prepareStatement(
                        "select tanggal, jam, tensi, bb, tb, diet, kode_infus, " +
                        "intake_makan, intake_minum, intake_ngt, intake_transfusi, intake_infus, intake_sisa_infus, " +
                        "jumlah_input, jumlah_input_24, output_urine, output_muntah, output_ngt, output_iwl, output_drain, " +
                        "jumlah_output, jumlah_output_24, balance_24, " +
                        "nadi, respirasi, suhu " +
                        "from rm_ttv_balance_cairan where no_rawat=? order by tanggal, jam");
                try {
                    ps.setString(1, TNoRw.getText());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        String[] arr = new String[26];
                        arr[0] = rs.getString("tanggal");
                        arr[1] = rs.getString("jam");
                        arr[2] = rs.getString("tensi");
                        arr[3] = rs.getString("bb") + " / " + rs.getString("tb");
                        arr[4] = rs.getString("diet");
                        arr[5] = rs.getString("kode_infus");
                        arr[6] = rs.getString("intake_makan");
                        arr[7] = rs.getString("intake_minum");
                        arr[8] = rs.getString("intake_ngt");
                        arr[9] = rs.getString("intake_transfusi");
                        arr[10] = rs.getString("intake_infus");
                        arr[11] = rs.getString("intake_sisa_infus");
                        arr[12] = rs.getString("jumlah_input");
                        arr[13] = rs.getString("jumlah_input_24");
                        arr[14] = rs.getString("output_urine");
                        arr[15] = rs.getString("output_muntah");
                        arr[16] = rs.getString("output_ngt");
                        arr[17] = rs.getString("output_iwl");
                        arr[18] = rs.getString("output_drain");
                        arr[19] = rs.getString("jumlah_output");
                        arr[20] = rs.getString("jumlah_output_24");
                        arr[21] = rs.getString("balance_24");
                        arr[22] = rs.getString("nadi");
                        arr[23] = rs.getString("respirasi");
                        arr[24] = rs.getString("suhu");
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
                model.addColumn("chart_image");

                int recordsPerPage = 12;
                int totalPages = (int) Math.ceil((double)dataList.size() / recordsPerPage);
                if (totalPages == 0) totalPages = 1;

                String[] rowLabelsTop = {"Tanggal", "Jam", "Kode Infus/transf."};
                int[] dataIdxTop = {0, 1, 5};

                String[] rowLabelsBot = {
                    "TD", "BB / TB", "Diet", "Interval/6 jam :", 
                    "    Makan", "    Minum", "    NGT", "    Transfusi", "    Infus", "    Sisa Infus", 
                    "Jumlah Input", "Jumlah input/24 jam", 
                    "OUTPUT", 
                    "    Urine", "    Muntah", "    NGT", "    IWL", "    Drain", 
                    "Jumlah Output", "Jumlah Output/24 Jam", "Jumlah Total/24 Jam"
                };
                int[] dataIdxBot = {
                    2, 3, 4, -1, 
                    6, 7, 8, 9, 10, 11, 
                    12, 13, 
                    -1, 
                    14, 15, 16, 17, 18, 
                    19, 20, 21
                };

                for (int p=0; p<totalPages; p++) {
                    int pageNum = p + 1;
                    int startIdx = p * recordsPerPage;
                    
                    java.util.List<String[]> pageData = new java.util.ArrayList<>();
                    for (int c=0; c<12; c++) {
                        int dataIdx = startIdx + c;
                        if (dataIdx < dataList.size()) {
                            pageData.add(dataList.get(dataIdx));
                        } else {
                            pageData.add(new String[26]);
                        }
                    }
                    
                    org.jfree.chart.JFreeChart chartPage = rekammedis.GrafikBalanceCairanRanap.createChartBcCustom(pageData);
                    java.awt.image.BufferedImage chartImagePage = chartPage.createBufferedImage(672, 175);

                    // TOP group
                    for (int r=0; r<rowLabelsTop.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "TOP";
                        rowData[2] = rowLabelsTop[r];
                        for (int c=0; c<12; c++) {
                            int dataIdx = startIdx + c;
                            if (dataIdx < dataList.size()) {
                                rowData[3 + c] = dataList.get(dataIdx)[dataIdxTop[r]];
                            } else {
                                rowData[3 + c] = "";
                            }
                        }
                        rowData[15] = chartImagePage;
                        model.addRow(rowData);
                    }

                    // BOTTOM group
                    for (int r=0; r<rowLabelsBot.length; r++) {
                        Object[] rowData = new Object[16];
                        rowData[0] = pageNum;
                        rowData[1] = "BOTTOM";
                        rowData[2] = rowLabelsBot[r];
                        for (int c=0; c<12; c++) {
                            int dataIdx = startIdx + c;
                            if (dataIdx < dataList.size()) {
                                if (dataIdxBot[r] == -1) {
                                    rowData[3 + c] = "";
                                } else {
                                    rowData[3 + c] = dataList.get(dataIdx)[dataIdxBot[r]];
                                }
                            } else {
                                rowData[3 + c] = "";
                            }
                        }
                        rowData[15] = chartImagePage;
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

pattern = r"    private void BtnPrintActionPerformed\(java\.awt\.event\.ActionEvent evt\) \{//GEN-FIRST:event_BtnPrintActionPerformed\n.*?\n    \}//GEN-LAST:event_BtnPrintActionPerformed\n"
new_method = f"    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {{//GEN-FIRST:event_BtnPrintActionPerformed\n{java_logic}    }}//GEN-LAST:event_BtnPrintActionPerformed\n"

content = re.sub(pattern, new_method, content, flags=re.DOTALL)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

print("Patched RMTTVBalanceCairan.java successfully")
