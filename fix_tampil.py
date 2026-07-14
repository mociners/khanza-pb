import re

file_path = "/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"

with open(file_path, 'r') as f:
    java_code = f.read()

old_tampil = 'rs.getString("nyeri_time_sejak")\n                    });'
new_tampil = '''rs.getString("nyeri_time_sejak"),
                        rs.getString("cpot_ekspresi"),
                        rs.getString("cpot_gerakan"),
                        rs.getString("cpot_ketegangan"),
                        rs.getString("cpot_ventilator"),
                        rs.getString("cpot_vokalisasi"),
                        rs.getString("cpot_total"),
                        rs.getString("cpot_kategori")
                    });'''

java_code = java_code.replace(old_tampil, new_tampil)

with open(file_path, 'w') as f:
    f.write(java_code)

