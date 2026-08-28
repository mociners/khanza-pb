import re

with open('src/rekammedis/RMDataResumePasienRanap.java', 'r') as f:
    content = f.read()

# Define the old method and new method
old_method_pattern = r'    private void BtnResepPulangActionPerformed\(java\.awt\.event\.ActionEvent evt\) \{[^{}]*(?:\{[^{}]*\}[^{}]*)*\}                                                      \n'

new_method = """    private void BtnResepPulangActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String noResepLama = Sequel.cariIsi("select ifnull(max(no_resep),'') from resep_obat where no_rawat=?", TNoRw.getText());
                inventory.DlgPeresepanDokter resep = new inventory.DlgPeresepanDokter(null, false);
                resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                java.util.Date tgl = new java.util.Date();
                String jam = String.format("%02d", tgl.getHours());
                String menit = String.format("%02d", tgl.getMinutes());
                String detik = String.format("%02d", tgl.getSeconds());
                resep.setNoRm(TNoRw.getText(), tgl, jam, menit, detik, KodeDokter.getText(), NamaDokter.getText(), "ranap");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
                
                resep.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        String noResepBaru = Sequel.cariIsi("select ifnull(max(no_resep),'') from resep_obat where no_rawat=?", TNoRw.getText());
                        if (!noResepBaru.equals(noResepLama) && !noResepBaru.equals("")) {
                            StringBuilder resepTxt = new StringBuilder();
                            if (ObatPulang.getText().trim().length() > 0) {
                                resepTxt.append("\\n");
                            }
                            
                            String tglResep = Sequel.cariIsi("select date_format(concat(tgl_peresepan,' ',jam_peresepan), '%d-%m-%Y %H:%i:%s') from resep_obat where no_resep=?", noResepBaru);
                            resepTxt.append("Tgl Resep: ").append(tglResep).append("\\n");
                            
                            try {
                                int i = 1;
                                java.sql.PreparedStatement ps = koneksi.prepareStatement(
                                    "select databarang.nama_brng, resep_dokter.aturan_pakai " +
                                    "from resep_dokter inner join databarang on resep_dokter.kode_brng=databarang.kode_brng " +
                                    "where resep_dokter.no_resep=?"
                                );
                                try {
                                    ps.setString(1, noResepBaru);
                                    java.sql.ResultSet rs = ps.executeQuery();
                                    while(rs.next()) {
                                        resepTxt.append(i).append(". ").append(rs.getString("nama_brng")).append(" - ").append(rs.getString("aturan_pakai")).append("\\n");
                                        i++;
                                    }
                                } finally {
                                    ps.close();
                                }
                                
                                java.sql.PreparedStatement psRacik = koneksi.prepareStatement(
                                    "select nama_racik, aturan_pakai from resep_dokter_racikan where no_resep=?"
                                );
                                try {
                                    psRacik.setString(1, noResepBaru);
                                    java.sql.ResultSet rsRacik = psRacik.executeQuery();
                                    while(rsRacik.next()) {
                                        resepTxt.append(i).append(". ").append(rsRacik.getString("nama_racik")).append(" - ").append(rsRacik.getString("aturan_pakai")).append("\\n");
                                        i++;
                                    }
                                } finally {
                                    psRacik.close();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notif : " + ex);
                            }
                            
                            ObatPulang.setText(ObatPulang.getText() + resepTxt.toString());
                        }
                    }
                });
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
"""

content = re.sub(old_method_pattern, new_method, content, count=1)

with open('src/rekammedis/RMDataResumePasienRanap.java', 'w') as f:
    f.write(content)
