import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Add GUI Variable Declarations
ui_vars = """    private widget.TextBox Keluarga2;
    private widget.Label jLabelNoTelpPasien;
    private widget.TextBox NoTelpPasien;
    private widget.CekBox ChkAlamatPJ;"""
code = code.replace("""    private widget.TextBox Keluarga2;""", ui_vars)


# 2. Add GUI Instantiations
ui_init = """        Keluarga2.setBounds(840, 180, 140, 23);
        
        jLabelNoTelpPasien = new widget.Label();
        jLabelNoTelpPasien.setText("Telp.Ps :");
        jLabelNoTelpPasien.setBounds(545, 60, 60, 23);
        
        NoTelpPasien = new widget.TextBox();
        NoTelpPasien.setBounds(605, 60, 100, 23);
        
        ChkAlamatPJ = new widget.CekBox();
        ChkAlamatPJ.setText("Sama");
        ChkAlamatPJ.setBounds(495, 120, 60, 23);
        ChkAlamatPJ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(ChkAlamatPJ.isSelected()){
                    Alamat.setText(Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText()));
                }else{
                    Alamat.setText("");
                }
            }
        });
"""
code = code.replace("""        Keluarga2.setBounds(840, 180, 140, 23);""", ui_init)

ui_add = """        FormInput.add(Keluarga2);
        FormInput.add(jLabelNoTelpPasien);
        FormInput.add(NoTelpPasien);
        FormInput.add(ChkAlamatPJ);"""
code = code.replace("""        FormInput.add(Keluarga2);""", ui_add)


# 3. Adjust Y=60 Bounds
code = code.replace('TPasien.setBounds(340, 60, 310, 23);', 'TPasien.setBounds(340, 60, 200, 23);')
code = code.replace('jLabel4.setBounds(654, 60, 56, 23);', 'jLabel4.setBounds(705, 60, 30, 23);')
code = code.replace('JK.setBounds(714, 60, 90, 23);', 'JK.setBounds(735, 60, 65, 23);')


# 4. Adjust Y=120 Bounds for Alamat, Pekerjaan, dll.
code = code.replace('jLabel9.setBounds(490, 120, 66, 23);', 'jLabel9.setBounds(560, 120, 66, 23);')
code = code.replace('Pekerjaan.setBounds(560, 120, 150, 23);', 'Pekerjaan.setBounds(630, 120, 120, 23);')
code = code.replace('jLabel12.setBounds(760, 120, 106, 23);', 'jLabel12.setBounds(755, 120, 106, 23);')
code = code.replace('BertindakAtas.setBounds(870, 120, 110, 23);', 'BertindakAtas.setBounds(865, 120, 115, 23);')


# 5. Remove UmurPJ.setText() auto-population
code = code.replace('UmurPJ.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));', '// Umur PJ tidak di-autopopulate')


# 6. Fetch No Telp Pasien in isRawat()
old_israwat_sql = '"select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"'
new_israwat_sql = '"select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,pasien.no_tlp,"'
code = code.replace(old_israwat_sql, new_israwat_sql)

old_israwat_fetch = 'LahirPasien.setText(rs.getString("tgl_lahir"));'
new_israwat_fetch = 'LahirPasien.setText(rs.getString("tgl_lahir"));\n                    NoTelpPasien.setText(rs.getString("no_tlp"));'
code = code.replace(old_israwat_fetch, new_israwat_fetch)


# 7. Modify SQL queries and array lists (Add no_telp_pasien BEFORE informasi_biaya -> index 22)
# The array indices currently:
# 22: informasi_biaya
# We want: 22: no_telp_pasien, 23: informasi_biaya. So everything >= 22 shifts by +1.

old_simpan_cols = "privasi_khusus, informasi_biaya"
new_simpan_cols = "privasi_khusus, no_telp_pasien, informasi_biaya"
code = code.replace(old_simpan_cols, new_simpan_cols)

old_edit_cols = "privasi_khusus=?,informasi_biaya=?"
new_edit_cols = "privasi_khusus=?,no_telp_pasien=?,informasi_biaya=?"
code = code.replace(old_edit_cols, new_edit_cols)

old_tampil_cols = "surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.informasi_biaya"
new_tampil_cols = "surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.no_telp_pasien,surat_persetujuan_umum.informasi_biaya"
code = code.replace(old_tampil_cols, new_tampil_cols)
# Account for spaces in tampil (if any) or MyReportqry
code = code.replace("surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.informasi_biaya", "surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.no_telp_pasien, surat_persetujuan_umum.informasi_biaya")

old_addrow = 'rs.getString("privasi_khusus"), rs.getString("informasi_biaya")'
new_addrow = 'rs.getString("privasi_khusus"), rs.getString("no_telp_pasien"), rs.getString("informasi_biaya")'
code = code.replace(old_addrow, new_addrow)

old_tabmode = '"Privasi Khusus", "Informasi Biaya"'
new_tabmode = '"Privasi Khusus", "No Telp Pasien", "Informasi Biaya"'
code = code.replace(old_tabmode, new_tabmode)

# Param array string insertion
old_arr_vals = 'Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString()'
new_arr_vals = 'Privasi2.getSelectedItem().toString(), NoTelpPasien.getText(), InfoBiaya.getSelectedItem().toString()'
code = code.replace(old_arr_vals, new_arr_vals)


# 8. Param counts and Question marks
# menyimpantf currently has 29 params, it will be 30
code = code.replace('"Data", 29, new String[]', '"Data", 30, new String[]')
# mengedittf currently has 27 params, it will be 28
code = code.replace('alasan_naik_kelas=?", 27, new String[]', 'alasan_naik_kelas=?", 28, new String[]')

# Add 1 question mark to menyimpantf
old_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" # 29
new_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" # 30
code = code.replace(old_menyimpantf_q, new_menyimpantf_q)


# 9. Shift getData and setValueAt indices (add +1 for anything >= 22)
def replace_get_data(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 22:
        return f"{prefix}{index + 1}{suffix}"
    return match.group(0)

code = re.sub(r'(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),\s*)(\d+)(\))', replace_get_data, code)

# We must add NoTelpPasien to getData()
get_data_jp_line = 'InfoBiaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());' # it will become 23 after regex
code = code.replace(get_data_jp_line, 'NoTelpPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());\n            ' + get_data_jp_line)


def replace_set_value(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 22:
        return f"{prefix}{index + 1}{suffix}"
    return match.group(0)

code = re.sub(r'(tbObat\.setValueAt\(.*tbObat\.getSelectedRow\(\),\s*)(\d+)(\);)', replace_set_value, code)

set_val_jp_line = 'tbObat.setValueAt(InfoBiaya.getSelectedItem().toString(), tbObat.getSelectedRow(), 23);'
code = code.replace(set_val_jp_line, 'tbObat.setValueAt(NoTelpPasien.getText(), tbObat.getSelectedRow(), 22);\n            ' + set_val_jp_line)

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Java Alamat, Telp, Umur PJ Patched!")
