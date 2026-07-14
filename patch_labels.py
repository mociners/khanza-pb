import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Intake replacements
content = content.replace('"Peroral/NGT : "', '"Makan : "')
content = content.replace('"Infus/Parenteral 1 :"', '"Minum : "')
content = content.replace('"Infus/Parenteral 2 :"', '"NGT : "')
content = content.replace('"Transfusi : "', '"Transfusi : "')
content = content.replace('"CVC : "', '"Infus : "')
content = content.replace('"Epidural : "', '"Sisa Infus : "')

# Output replacements
content = content.replace('"Feses : "', '"Urine : "')
content = content.replace('"Urine : "', '"Muntah : "')
content = content.replace('"Muntah/NGT : "', '"NGT : "')
content = content.replace('"Drain/Darah : "', '"IWL : "')
content = content.replace('"IWL : "', '"Drain : "')

# Also fix the form's layout and column names
old_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Peroral/NGT","Infus/Parenteral 1","Infus/Parenteral 2","Transfusi","CVC","Epidural","Jumlah Masuk","Fases","Urine",\n            "Muntah/NGT","Drain/Darah","IWL","Jumlah Keluar","Balance Cairan"'
new_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Balance Cairan"'
content = content.replace(old_cols, new_cols)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

