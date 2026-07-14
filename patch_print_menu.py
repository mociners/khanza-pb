import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Add variable declarations at the end
if "private javax.swing.JPopupMenu jPopupMenu1;" not in content:
    content = content.replace(
        "private widget.ComboBox TKodeInfus;",
        "private widget.ComboBox TKodeInfus;\n    private javax.swing.JPopupMenu jPopupMenu1;\n    private javax.swing.JMenuItem MnCetak;"
    )

# 2. Inject popup menu setup at the end of the constructor
# Find the exact end of the constructor
constructor_end_str = """        });
        FormInput.setFocusCycleRoot(true);
    }"""

popup_setup = """        });
        FormInput.setFocusCycleRoot(true);
        
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetak = new javax.swing.JMenuItem();
        MnCetak.setBackground(new java.awt.Color(255, 255, 254));
        MnCetak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetak.setForeground(new java.awt.Color(50, 50, 50));
        MnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        MnCetak.setText("Cetak Report Observasi TTV & Balance Cairan");
        MnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetak);
        tbObat.setComponentPopupMenu(jPopupMenu1);
    }"""

if "jPopupMenu1 = new javax.swing.JPopupMenu();" not in content:
    content = content.replace(constructor_end_str, popup_setup)

# 3. Add MnCetakActionPerformed method before //GEN-LAST:event_BtnPrintKeyPressed or similar
action_method = """
    private void MnCetakActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                org.jfree.chart.JFreeChart chart = rekammedis.GrafikBalanceCairanRanap.createChartBc(TNoRw.getText());
                java.awt.image.BufferedImage chartImage = chart.createBufferedImage(800, 220);
                
                java.util.Map<String, Object> param = new java.util.HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                param.put("grafik", chartImage);

                Valid.MyReport("rptObservasiTTVBalance.jrxml", "report", "::[ Observasi TTV & Balance Cairan ]::", 
                        "select rm_ttv_balance_cairan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk, " +
                        "rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi, " +
                        "rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb, " +
                        "rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.interval_waktu, " +
                        "rm_ttv_balance_cairan.intake_makan,rm_ttv_balance_cairan.intake_minum,rm_ttv_balance_cairan.intake_ngt, " +
                        "rm_ttv_balance_cairan.intake_transfusi,rm_ttv_balance_cairan.intake_infus,rm_ttv_balance_cairan.intake_sisa_infus, " +
                        "rm_ttv_balance_cairan.jumlah_input,rm_ttv_balance_cairan.jumlah_input_24, " +
                        "rm_ttv_balance_cairan.output_urine,rm_ttv_balance_cairan.output_muntah,rm_ttv_balance_cairan.output_ngt, " +
                        "rm_ttv_balance_cairan.output_iwl,rm_ttv_balance_cairan.output_drain,rm_ttv_balance_cairan.jumlah_output, " +
                        "rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance_24,rm_ttv_balance_cairan.nik,petugas.nama " +
                        "from rm_ttv_balance_cairan inner join reg_periksa on rm_ttv_balance_cairan.no_rawat=reg_periksa.no_rawat " +
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                        "inner join petugas on rm_ttv_balance_cairan.nik=petugas.nip " +
                        "where rm_ttv_balance_cairan.no_rawat='" + TNoRw.getText() + "' order by rm_ttv_balance_cairan.tanggal, rm_ttv_balance_cairan.jam", param);
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
"""

if "private void MnCetakActionPerformed" not in content:
    content = content.replace("private void TCariKeyPressed", action_method + "\n    private void TCariKeyPressed")

# 4. In case the user clicks the Print button, we could map it to MnCetakActionPerformed as well.
content = content.replace("""    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

}//GEN-LAST:event_BtnPrintActionPerformed""", """    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        MnCetakActionPerformed(evt);
}//GEN-LAST:event_BtnPrintActionPerformed""")


with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

