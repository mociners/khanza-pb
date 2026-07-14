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

                int recordsPerPage = 12;
                int totalPages = (int) Math.ceil((double)dataList.size() / recordsPerPage);
                if (totalPages == 0) totalPages = 1;

                // Scale definitions for grid chart
                int[] nadiScale =  {160, 140, 120, 100, 80, 60, 40};
                int[] respScale =  {60,  50,  40,  30,  20, 10, 0};
                double[] suhuScale = {41, 40, 39, 38, 37, 36, 35};

                // Helper: find closest row index
                // For nadi: value mapped to nearest scale row
                // For resp: value mapped to nearest scale row
                // For suhu: value mapped to nearest scale row

                for (int p=0; p<totalPages; p++) {
                    int pageNum = p + 1;
                    int startIdx = p * recordsPerPage;

                    // --- HEADER ROWS ---
                    String[] headerLabels = {"Tanggal", "Jam", "Kode Infus/transf."};
                    int[] headerIdx = {0, 1, 5};
                    for (int r=0; r<headerLabels.length; r++) {
                        Object[] rowData = new Object[15];
                        rowData[0] = pageNum;
                        rowData[1] = "HEAD";
                        rowData[2] = headerLabels[r];
                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            rowData[3+c] = (di < dataList.size()) ? dataList.get(di)[headerIdx[r]] : "";
                        }
                        model.addRow(rowData);
                    }

                    // --- GRID CHART ROWS (7 rows) ---
                    for (int g=0; g<7; g++) {
                        Object[] rowData = new Object[15];
                        rowData[0] = pageNum;
                        rowData[1] = "GRID";
                        // Row label: N | R | S scale values
                        String suhuStr = (suhuScale[g] == (int)suhuScale[g]) ? String.valueOf((int)suhuScale[g]) : String.valueOf(suhuScale[g]);
                        rowData[2] = nadiScale[g] + "  " + respScale[g] + "  " + suhuStr;

                        for (int c=0; c<12; c++) {
                            int di = startIdx + c;
                            if (di < dataList.size()) {
                                String[] d = dataList.get(di);
                                StringBuilder cell = new StringBuilder();

                                // Parse values
                                double nadi = 0, resp = 0, suhu = 0;
                                try { nadi = Double.parseDouble(d[6]); } catch(Exception e) {}
                                try { resp = Double.parseDouble(d[7]); } catch(Exception e) {}
                                try { suhu = Double.parseDouble(d[8]); } catch(Exception e) {}

                                // Find closest nadi row
                                if (nadi > 0) {
                                    int bestRow = 0;
                                    double bestDist = Math.abs(nadi - nadiScale[0]);
                                    for (int k=1; k<nadiScale.length; k++) {
                                        double dist = Math.abs(nadi - nadiScale[k]);
                                        if (dist < bestDist) { bestDist = dist; bestRow = k; }
                                    }
                                    if (bestRow == g) {
                                        cell.append("<font color='red'><b>" + (int)nadi + "</b></font>");
                                    }
                                }

                                // Find closest resp row
                                if (resp > 0) {
                                    int bestRow = 0;
                                    double bestDist = Math.abs(resp - respScale[0]);
                                    for (int k=1; k<respScale.length; k++) {
                                        double dist = Math.abs(resp - respScale[k]);
                                        if (dist < bestDist) { bestDist = dist; bestRow = k; }
                                    }
                                    if (bestRow == g) {
                                        if (cell.length() > 0) cell.append(" ");
                                        cell.append("<font color='#333333'>" + (int)resp + "</font>");
                                    }
                                }

                                // Find closest suhu row
                                if (suhu > 0) {
                                    int bestRow = 0;
                                    double bestDist = Math.abs(suhu - suhuScale[0]);
                                    for (int k=1; k<suhuScale.length; k++) {
                                        double dist = Math.abs(suhu - suhuScale[k]);
                                        if (dist < bestDist) { bestDist = dist; bestRow = k; }
                                    }
                                    if (bestRow == g) {
                                        if (cell.length() > 0) cell.append(" ");
                                        cell.append("<font color='blue'><b>" + suhu + "</b></font>");
                                    }
                                }

                                rowData[3+c] = cell.toString();
                            } else {
                                rowData[3+c] = "";
                            }
                        }
                        model.addRow(rowData);
                    }

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
                        Object[] rowData = new Object[15];
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
    print("Patched RMTTVBalanceCairan.java - grid chart TTV")
