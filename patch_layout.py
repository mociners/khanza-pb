import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Intake labels & inputs
content = re.sub(r'jLabel251\.setBounds\(.*?\);', 'jLabel251.setBounds(70, 140, 100, 23);', content)
content = re.sub(r'Masuk1\.setBounds\(.*?\);', 'Masuk1.setBounds(170, 140, 70, 23);', content)

content = re.sub(r'jLabel262\.setBounds\(.*?\);', 'jLabel262.setBounds(70, 170, 100, 23);', content)
content = re.sub(r'Masuk2\.setBounds\(.*?\);', 'Masuk2.setBounds(170, 170, 70, 23);', content)

content = re.sub(r'jLabel261\.setBounds\(.*?\);', 'jLabel261.setBounds(70, 200, 100, 23);', content)
content = re.sub(r'Masuk3\.setBounds\(.*?\);', 'Masuk3.setBounds(170, 200, 70, 23);', content)

content = re.sub(r'jLabel264\.setBounds\(.*?\);', 'jLabel264.setBounds(70, 230, 100, 23);', content)
content = re.sub(r'Masuk4\.setBounds\(.*?\);', 'Masuk4.setBounds(170, 230, 70, 23);', content)

content = re.sub(r'jLabel265\.setBounds\(.*?\);', 'jLabel265.setBounds(70, 260, 100, 23);', content)
content = re.sub(r'Masuk5\.setBounds\(.*?\);', 'Masuk5.setBounds(170, 260, 70, 23);', content)

content = re.sub(r'jLabel263\.setBounds\(.*?\);', 'jLabel263.setBounds(70, 290, 100, 23);', content)
content = re.sub(r'Masuk6\.setBounds\(.*?\);', 'Masuk6.setBounds(170, 290, 70, 23);', content)

content = re.sub(r'jLabel253\.setBounds\(.*?\);', 'jLabel253.setBounds(70, 320, 100, 23);', content)
content = re.sub(r'JumlahMasuk\.setBounds\(.*?\);', 'JumlahMasuk.setBounds(170, 320, 70, 23);', content)


# Output section
content = re.sub(r'jLabel56\.setBounds\(.*?\);', 'jLabel56.setBounds(40, 350, 60, 23);', content)

content = re.sub(r'jLabel254\.setBounds\(.*?\);', 'jLabel254.setBounds(70, 370, 100, 23);', content)
content = re.sub(r'Keluar1\.setBounds\(.*?\);', 'Keluar1.setBounds(170, 370, 70, 23);', content)

content = re.sub(r'jLabel255\.setBounds\(.*?\);', 'jLabel255.setBounds(70, 400, 100, 23);', content)
content = re.sub(r'Keluar2\.setBounds\(.*?\);', 'Keluar2.setBounds(170, 400, 70, 23);', content)

content = re.sub(r'jLabel256\.setBounds\(.*?\);', 'jLabel256.setBounds(70, 430, 100, 23);', content)
content = re.sub(r'Keluar3\.setBounds\(.*?\);', 'Keluar3.setBounds(170, 430, 70, 23);', content)

content = re.sub(r'jLabel257\.setBounds\(.*?\);', 'jLabel257.setBounds(70, 460, 100, 23);', content)
content = re.sub(r'Keluar4\.setBounds\(.*?\);', 'Keluar4.setBounds(170, 460, 70, 23);', content)

content = re.sub(r'jLabel258\.setBounds\(.*?\);', 'jLabel258.setBounds(70, 490, 100, 23);', content)
content = re.sub(r'Keluar5\.setBounds\(.*?\);', 'Keluar5.setBounds(170, 490, 70, 23);', content)

content = re.sub(r'jLabel259\.setBounds\(.*?\);', 'jLabel259.setBounds(70, 520, 100, 23);', content)
content = re.sub(r'JumlahKeluar\.setBounds\(.*?\);', 'JumlahKeluar.setBounds(170, 520, 70, 23);', content)

content = re.sub(r'jLabel260\.setBounds\(.*?\);', 'jLabel260.setBounds(50, 550, 120, 23);', content)
content = re.sub(r'BC\.setBounds\(.*?\);', 'BC.setBounds(170, 550, 70, 23);', content)

# TTV right side fields 
content = re.sub(r'LNadi\.setBounds\(.*?\);', 'LNadi.setBounds(450, 90, 90, 23);', content)
content = re.sub(r'TNadi\.setBounds\(.*?\);', 'TNadi.setBounds(550, 90, 100, 23);', content)

content = re.sub(r'LRespirasi\.setBounds\(.*?\);', 'LRespirasi.setBounds(450, 120, 90, 23);', content)
content = re.sub(r'TRespirasi\.setBounds\(.*?\);', 'TRespirasi.setBounds(550, 120, 100, 23);', content)

content = re.sub(r'LSuhu\.setBounds\(.*?\);', 'LSuhu.setBounds(450, 150, 90, 23);', content)
content = re.sub(r'TSuhu\.setBounds\(.*?\);', 'TSuhu.setBounds(550, 150, 100, 23);', content)

content = re.sub(r'LTensi\.setBounds\(.*?\);', 'LTensi.setBounds(450, 180, 90, 23);', content)
content = re.sub(r'TTensi\.setBounds\(.*?\);', 'TTensi.setBounds(550, 180, 100, 23);', content)

content = re.sub(r'LBB\.setBounds\(.*?\);', 'LBB.setBounds(450, 210, 90, 23);', content)
content = re.sub(r'TBB\.setBounds\(.*?\);', 'TBB.setBounds(550, 210, 100, 23);', content)

content = re.sub(r'LTB\.setBounds\(.*?\);', 'LTB.setBounds(450, 240, 90, 23);', content)
content = re.sub(r'TTB\.setBounds\(.*?\);', 'TTB.setBounds(550, 240, 100, 23);', content)

content = re.sub(r'LDiet\.setBounds\(.*?\);', 'LDiet.setBounds(450, 270, 90, 23);', content)
content = re.sub(r'TDiet\.setBounds\(.*?\);', 'TDiet.setBounds(550, 270, 200, 23);', content)

content = re.sub(r'LKodeInfus\.setBounds\(.*?\);', 'LKodeInfus.setBounds(450, 300, 90, 23);', content)
content = re.sub(r'TKodeInfus\.setBounds\(.*?\);', 'TKodeInfus.setBounds(550, 300, 200, 23);', content)

content = re.sub(r'BtnEWS2\.setBounds\(.*?\);', 'BtnEWS2.setBounds(450, 340, 190, 30);', content)


with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

