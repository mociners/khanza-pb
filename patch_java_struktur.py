import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. menyimpantf
old_menyimpantf_cols = "privasi_khusus, jenis_pembiayaan"
new_menyimpantf_cols = "privasi_khusus, informasi_biaya, jenis_pembiayaan"
code = code.replace(old_menyimpantf_cols, new_menyimpantf_cols)

old_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
new_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
code = code.replace(old_menyimpantf_q, new_menyimpantf_q)

# 2. mengedittf
old_mengedittf_cols = "privasi_khusus=?,jenis_pembiayaan=?"
new_mengedittf_cols = "privasi_khusus=?,informasi_biaya=?,jenis_pembiayaan=?"
code = code.replace(old_mengedittf_cols, new_mengedittf_cols)

# 3. tabMode initialization
old_tabmode = '"Privasi Khusus", "Jenis Pembiayaan"'
new_tabmode = '"Privasi Khusus", "Informasi Biaya", "Jenis Pembiayaan"'
code = code.replace(old_tabmode, new_tabmode)

# 4. array values in menyimpantf, mengedittf, addRow
old_arr_vals = 'Privasi2.getSelectedItem().toString(), rbUmum.isSelected()'
new_arr_vals = 'Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString(), rbUmum.isSelected()'
code = code.replace(old_arr_vals, new_arr_vals)

# 5. Sequel.mengedittf paramCount and menyimpantf paramCount
# For menyimpantf, paramCount was 26, change to 27
code = code.replace('"Data", 26, new String[]', '"Data", 27, new String[]')
# For mengedittf, paramCount was 24, change to 25
code = code.replace('alasan_naik_kelas=?", 24, new String[]', 'alasan_naik_kelas=?", 25, new String[]')

# 6. tampil() sql select and addRow
old_tampil_select = 'surat_persetujuan_umum.privasi_akses, surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.jenis_pembiayaan'
new_tampil_select = 'surat_persetujuan_umum.privasi_akses, surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.informasi_biaya, surat_persetujuan_umum.jenis_pembiayaan'
code = code.replace(old_tampil_select, new_tampil_select)

old_tampil_addrow = 'rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("jenis_pembiayaan")'
new_tampil_addrow = 'rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("informasi_biaya"), rs.getString("jenis_pembiayaan")'
code = code.replace(old_tampil_addrow, new_tampil_addrow)

# 7. MyReportqry
code = code.replace(old_tampil_select, new_tampil_select)

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Patch applied for Java structural changes!")
