import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Variables and UI setup
ui_vars = """    private widget.Label jLabelInfoBiaya;
    private widget.ComboBox InfoBiaya;
    private widget.Label jLabelKeluarga1;
    private widget.TextBox Keluarga1;
    private widget.Label jLabelKeluarga2;
    private widget.TextBox Keluarga2;"""
code = code.replace("""    private widget.Label jLabelInfoBiaya;\n    private widget.ComboBox InfoBiaya;""", ui_vars)

ui_init = """        InfoBiaya.setBounds(583, 150, 180, 23);
        
        jLabelKeluarga1 = new widget.Label();
        jLabelKeluarga1.setText("Keluarga 1 :");
        jLabelKeluarga1.setBounds(765, 150, 70, 23);
        Keluarga1 = new widget.TextBox();
        Keluarga1.setBounds(840, 150, 140, 23);
        
        jLabelKeluarga2 = new widget.Label();
        jLabelKeluarga2.setText("Keluarga 2 :");
        jLabelKeluarga2.setBounds(765, 180, 70, 23);
        Keluarga2 = new widget.TextBox();
        Keluarga2.setBounds(840, 180, 140, 23);"""
code = code.replace("        InfoBiaya.setBounds(583, 150, 180, 23);", ui_init)

ui_add = """        FormInput.add(jLabelInfoBiaya);
        FormInput.add(InfoBiaya);
        FormInput.add(jLabelKeluarga1);
        FormInput.add(Keluarga1);
        FormInput.add(jLabelKeluarga2);
        FormInput.add(Keluarga2);"""
code = code.replace("""        FormInput.add(jLabelInfoBiaya);\n        FormInput.add(InfoBiaya);""", ui_add)

ui_empt = """        Keluarga1.setText("");
        Keluarga2.setText("");
        emptTeks();"""
code = code.replace("        emptTeks();", ui_empt, 1)


# 2. SQL queries
old_cols = "surat_persetujuan_umum.informasi_biaya,surat_persetujuan_umum.jenis_pembiayaan"
new_cols = "surat_persetujuan_umum.informasi_biaya,surat_persetujuan_umum.keluarga_1,surat_persetujuan_umum.keluarga_2,surat_persetujuan_umum.jenis_pembiayaan"
code = code.replace(old_cols, new_cols)

# Note: MyReportqry has spaces!
old_cols_sp = "surat_persetujuan_umum.informasi_biaya, surat_persetujuan_umum.jenis_pembiayaan"
new_cols_sp = "surat_persetujuan_umum.informasi_biaya, surat_persetujuan_umum.keluarga_1, surat_persetujuan_umum.keluarga_2, surat_persetujuan_umum.jenis_pembiayaan"
code = code.replace(old_cols_sp, new_cols_sp)

# 3. tabMode and addRow
code = code.replace('"Informasi Biaya", "Jenis Pembiayaan"', '"Informasi Biaya", "Keluarga 1", "Keluarga 2", "Jenis Pembiayaan"')

old_addrow = 'rs.getString("informasi_biaya"), rs.getString("jenis_pembiayaan")'
new_addrow = 'rs.getString("informasi_biaya"), rs.getString("keluarga_1"), rs.getString("keluarga_2"), rs.getString("jenis_pembiayaan")'
code = code.replace(old_addrow, new_addrow)

# 4. menyimpantf and mengedittf column definitions
old_simpan_cols = "informasi_biaya, jenis_pembiayaan"
new_simpan_cols = "informasi_biaya, keluarga_1, keluarga_2, jenis_pembiayaan"
code = code.replace(old_simpan_cols, new_simpan_cols)

old_edit_cols = "informasi_biaya=?,jenis_pembiayaan=?"
new_edit_cols = "informasi_biaya=?,keluarga_1=?,keluarga_2=?,jenis_pembiayaan=?"
code = code.replace(old_edit_cols, new_edit_cols)

# 5. Param array string insertion
old_arr_vals = 'InfoBiaya.getSelectedItem().toString(), rbUmum.isSelected()'
new_arr_vals = 'InfoBiaya.getSelectedItem().toString(), Keluarga1.getText(), Keluarga2.getText(), rbUmum.isSelected()'
code = code.replace(old_arr_vals, new_arr_vals)

# 6. ? Question marks
old_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
new_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" # added 2
code = code.replace(old_menyimpantf_q, new_menyimpantf_q)

# 7. param counts
code = code.replace('"Data", 27, new String[]', '"Data", 29, new String[]')
code = code.replace('alasan_naik_kelas=?", 25, new String[]', 'alasan_naik_kelas=?", 27, new String[]')


# 8. Shift getData and setValueAt indices (add +2 for anything >= 23)
def replace_get_data(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 23:
        return f"{prefix}{index + 2}{suffix}"
    return match.group(0)

code = re.sub(r'(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),\s*)(\d+)(\))', replace_get_data, code)

# We must add Keluarga1 and 2 to getData()
get_data_jp_line = 'String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString();' # it will become 25 after regex
code = code.replace(get_data_jp_line, 'Keluarga1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());\n            Keluarga2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());\n            ' + get_data_jp_line)

def replace_set_value(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 23:
        return f"{prefix}{index + 2}{suffix}"
    return match.group(0)

code = re.sub(r'(tbObat\.setValueAt\(.*tbObat\.getSelectedRow\(\),\s*)(\d+)(\);)', replace_set_value, code)

set_val_jp_line = 'tbObat.setValueAt(rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), tbObat.getSelectedRow(), 25);'
code = code.replace(set_val_jp_line, 'tbObat.setValueAt(Keluarga1.getText(), tbObat.getSelectedRow(), 23);\n            tbObat.setValueAt(Keluarga2.getText(), tbObat.getSelectedRow(), 24);\n            ' + set_val_jp_line)

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Java Code Patched!")
