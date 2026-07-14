import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Update the SQL strings inside tampil()
old_select = '"select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk=\'L\',\'Laki-Laki\',\'Perempuan\') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nik,pegawai.nama,"+'
new_select = '"select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk=\'L\',\'Laki-Laki\',\'Perempuan\') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi,rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb,rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.nik,pegawai.nama,"+'

content = content.replace(old_select, new_select)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
