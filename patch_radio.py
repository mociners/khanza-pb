import re
import sys

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Add column to tabMode
code = code.replace(
    '            "Privasi Akses", "Privasi Khusus", "Alasan Umum", "Asuransi", "No.Asuransi", ',
    '            "Privasi Akses", "Privasi Khusus", "Jenis Pembiayaan", "Alasan Umum", "Asuransi", "No.Asuransi", '
)

code = code.replace("for (i = 0; i < 29; i++) {", "for (i = 0; i < 30; i++) {")

# Add column width for index 29, and adjust 22 to Jenis Pembiayaan
code = code.replace(
    "            else if (i == 21) column.setPreferredWidth(100);\n            else if (i == 22) column.setPreferredWidth(100);",
    "            else if (i == 21) column.setPreferredWidth(100);\n            else if (i == 22) column.setPreferredWidth(120);\n            else if (i == 23) column.setPreferredWidth(100);"
)
for i in range(23, 29):
    code = code.replace(f"else if (i == {i}) column.setPreferredWidth", f"else if (i == {i+1}) column.setPreferredWidth")

# 2. Increase PanelInput size
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 175));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 235));')
code = code.replace('PanelInput.setPreferredSize(new Dimension(WIDTH, 290));', 'PanelInput.setPreferredSize(new Dimension(WIDTH, 350));')
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 290));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 350));')

# 3. Add to SQL in BtnSimpanActionPerformed
# Find the Sequel.menyimpantf call
simpan_old = '"surat_persetujuan_umum (no_surat, no_rawat, tanggal, pengobatan_kepada, nilai_kepercayaan, nama_pj, umur_pj, no_ktppj, jkpj, bertindak_atas, no_telp, nip, alamat_pj, pekerjaan_pj, privasi_akses, privasi_khusus, alasan_tolak_bpjs, asuransi_swasta, no_kartu_asuransi, no_jkn_jasa_raharja, hak_kelas, pilihan_kamar, alasan_naik_kelas)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 23'
simpan_new = '"surat_persetujuan_umum (no_surat, no_rawat, tanggal, pengobatan_kepada, nilai_kepercayaan, nama_pj, umur_pj, no_ktppj, jkpj, bertindak_atas, no_telp, nip, alamat_pj, pekerjaan_pj, privasi_akses, privasi_khusus, jenis_pembiayaan, alasan_tolak_bpjs, asuransi_swasta, no_kartu_asuransi, no_jkn_jasa_raharja, hak_kelas, pilihan_kamar, alasan_naik_kelas)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 24'
code = code.replace(simpan_old, simpan_new)

# Add value in the String[]
simpan_val_old = 'Privasi1.getSelectedItem().toString(), Privasi2.getSelectedItem().toString(), AlasanUmum.getText(),'
simpan_val_new = 'Privasi1.getSelectedItem().toString(), Privasi2.getSelectedItem().toString(), rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), AlasanUmum.getText(),'
code = code.replace(simpan_val_old, simpan_val_new)

# 4. Add to SQL in ganti()
ganti_old = 'alasan_tolak_bpjs=?,asuransi_swasta=?,no_kartu_asuransi=?,no_jkn_jasa_raharja=?,hak_kelas=?,pilihan_kamar=?,alasan_naik_kelas=?", 21'
ganti_new = 'jenis_pembiayaan=?,alasan_tolak_bpjs=?,asuransi_swasta=?,no_kartu_asuransi=?,no_jkn_jasa_raharja=?,hak_kelas=?,pilihan_kamar=?,alasan_naik_kelas=?", 22'
code = code.replace(ganti_old, ganti_new)
code = code.replace(simpan_val_old, simpan_val_new)

# 5. Add to SQL in tampil()
tampil_old = 'surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.alasan_tolak_bpjs'
tampil_new = 'surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.jenis_pembiayaan,surat_persetujuan_umum.alasan_tolak_bpjs'
code = code.replace(tampil_old, tampil_new)

# Add to tabMode.addRow
addrow_old = 'rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("alasan_tolak_bpjs")'
addrow_new = 'rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("jenis_pembiayaan"), rs.getString("alasan_tolak_bpjs")'
code = code.replace(addrow_old, addrow_new)

# 6. Update getData() indices
# tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString() was AlasanUmum
# Now 22 is Jenis Pembiayaan, 23 is AlasanUmum
getdata_old = """
            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            NamaAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            NoKartuAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            NoKartuBPJS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            HakKelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            PilihanKelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
"""
getdata_new = """
            String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString();
            if(jp.equals("Umum")) rbUmum.setSelected(true);
            else if(jp.equals("Asuransi Swasta")) rbAsuransi.setSelected(true);
            else if(jp.equals("Jasa Raharja")) rbJasaRaharja.setSelected(true);
            else if(jp.equals("BPJS Ketenagakerjaan")) rbBPJSKerja.setSelected(true);
            else rbBPJSKes.setSelected(true);

            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            NamaAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            NoKartuAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            NoKartuBPJS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            HakKelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            PilihanKelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString());
            AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
"""
code = code.replace(getdata_old, getdata_new)

# 7. Add Variables to GUI
var_decl = """    // Variables declaration - do not modify//GEN-BEGIN:variables"""
var_new = """    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;
"""
code = code.replace(var_decl, var_new)

# 8. Inject UI initialization and repositioning in initComponents
init_target = """        jLabelAlasanNaik.setBounds(280, 240, 110, 23);
        AlasanNaik.setBounds(395, 240, 200, 23);"""

init_new = """        jLabelAlasanNaik.setBounds(280, 300, 110, 23);
        AlasanNaik.setBounds(395, 300, 200, 23);
        
        bgPembiayaan = new javax.swing.ButtonGroup();
        rbUmum = new widget.RadioButton();
        rbAsuransi = new widget.RadioButton();
        rbJasaRaharja = new widget.RadioButton();
        rbBPJSKerja = new widget.RadioButton();
        rbBPJSKes = new widget.RadioButton();

        rbUmum.setText("Umum");
        rbAsuransi.setText("Asuransi Swasta");
        rbJasaRaharja.setText("Jasa Raharja");
        rbBPJSKerja.setText("BPJS Ketenagakerj.");
        rbBPJSKes.setText("BPJS Kesehatan");
        
        rbUmum.setFocusable(false);
        rbAsuransi.setFocusable(false);
        rbJasaRaharja.setFocusable(false);
        rbBPJSKerja.setFocusable(false);
        rbBPJSKes.setFocusable(false);

        bgPembiayaan.add(rbUmum);
        bgPembiayaan.add(rbAsuransi);
        bgPembiayaan.add(rbJasaRaharja);
        bgPembiayaan.add(rbBPJSKerja);
        bgPembiayaan.add(rbBPJSKes);

        FormInput.add(rbUmum);
        FormInput.add(rbAsuransi);
        FormInput.add(rbJasaRaharja);
        FormInput.add(rbBPJSKerja);
        FormInput.add(rbBPJSKes);

        rbUmum.setBounds(0, 180, 65, 23);
        rbAsuransi.setBounds(70, 180, 125, 23);
        rbJasaRaharja.setBounds(200, 180, 105, 23);
        rbBPJSKerja.setBounds(310, 180, 140, 23);
        rbBPJSKes.setBounds(455, 180, 130, 23);
        
        java.awt.event.ItemListener rbListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    AlasanUmum.setEditable(rbUmum.isSelected());
                    NamaAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuBPJS.setEditable(rbJasaRaharja.isSelected() || rbBPJSKerja.isSelected() || rbBPJSKes.isSelected());
                    HakKelas.setEditable(rbBPJSKes.isSelected());
                    
                    if(!rbUmum.isSelected()) AlasanUmum.setText("-");
                    if(!rbAsuransi.isSelected()) {
                        NamaAsuransi.setText("-");
                        NoKartuAsuransi.setText("-");
                    }
                    if(!rbJasaRaharja.isSelected() && !rbBPJSKerja.isSelected() && !rbBPJSKes.isSelected()) {
                        NoKartuBPJS.setText("-");
                    }
                    if(!rbBPJSKes.isSelected()) HakKelas.setText("-");
                }
            }
        };
        rbUmum.addItemListener(rbListener);
        rbAsuransi.addItemListener(rbListener);
        rbJasaRaharja.addItemListener(rbListener);
        rbBPJSKerja.addItemListener(rbListener);
        rbBPJSKes.addItemListener(rbListener);
        
        rbUmum.setSelected(true);"""

code = code.replace(init_target, init_new)

# Reposition bounds
code = code.replace('jLabelAsuransi.setBounds(0, 180, 100, 23);', 'jLabelAsuransi.setBounds(0, 210, 100, 23);')
code = code.replace('NamaAsuransi.setBounds(105, 180, 220, 23);', 'NamaAsuransi.setBounds(105, 210, 220, 23);')
code = code.replace('jLabelNoKartuAsuransi.setBounds(330, 180, 60, 23);', 'jLabelNoKartuAsuransi.setBounds(330, 210, 60, 23);')
code = code.replace('NoKartuAsuransi.setBounds(395, 180, 140, 23);', 'NoKartuAsuransi.setBounds(395, 210, 140, 23);')

code = code.replace('jLabelBPJS.setBounds(0, 210, 130, 23);', 'jLabelBPJS.setBounds(0, 240, 130, 23);')
code = code.replace('NoKartuBPJS.setBounds(135, 210, 160, 23);', 'NoKartuBPJS.setBounds(135, 240, 160, 23);')
code = code.replace('jLabelHakKelas.setBounds(300, 210, 70, 23);', 'jLabelHakKelas.setBounds(300, 240, 70, 23);')
code = code.replace('HakKelas.setBounds(375, 210, 100, 23);', 'HakKelas.setBounds(375, 240, 100, 23);')

code = code.replace('jLabelPilihanKelas.setBounds(0, 240, 90, 23);', 'jLabelPilihanKelas.setBounds(0, 270, 90, 23);')
code = code.replace('PilihanKelas.setBounds(95, 240, 180, 23);', 'PilihanKelas.setBounds(95, 270, 180, 23);')

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Patch applied to SuratPersetujuanUmum.java")
