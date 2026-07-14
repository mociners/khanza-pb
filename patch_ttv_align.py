import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Update TTV right side fields Y + 50
content = re.sub(r'LNadi\.setBounds\(450, 90, 90, 23\);', 'LNadi.setBounds(450, 140, 90, 23);', content)
content = re.sub(r'TNadi\.setBounds\(550, 90, 100, 23\);', 'TNadi.setBounds(550, 140, 100, 23);', content)

content = re.sub(r'LRespirasi\.setBounds\(450, 120, 90, 23\);', 'LRespirasi.setBounds(450, 170, 90, 23);', content)
content = re.sub(r'TRespirasi\.setBounds\(550, 120, 100, 23\);', 'TRespirasi.setBounds(550, 170, 100, 23);', content)

content = re.sub(r'LSuhu\.setBounds\(450, 150, 90, 23\);', 'LSuhu.setBounds(450, 200, 90, 23);', content)
content = re.sub(r'TSuhu\.setBounds\(550, 150, 100, 23\);', 'TSuhu.setBounds(550, 200, 100, 23);', content)

content = re.sub(r'LTensi\.setBounds\(450, 180, 90, 23\);', 'LTensi.setBounds(450, 230, 90, 23);', content)
content = re.sub(r'TTensi\.setBounds\(550, 180, 100, 23\);', 'TTensi.setBounds(550, 230, 100, 23);', content)

content = re.sub(r'LBB\.setBounds\(450, 210, 90, 23\);', 'LBB.setBounds(450, 260, 90, 23);', content)
content = re.sub(r'TBB\.setBounds\(550, 210, 100, 23\);', 'TBB.setBounds(550, 260, 100, 23);', content)

content = re.sub(r'LTB\.setBounds\(450, 240, 90, 23\);', 'LTB.setBounds(450, 290, 90, 23);', content)
content = re.sub(r'TTB\.setBounds\(550, 240, 100, 23\);', 'TTB.setBounds(550, 290, 100, 23);', content)

content = re.sub(r'LDiet\.setBounds\(450, 270, 90, 23\);', 'LDiet.setBounds(450, 320, 90, 23);', content)
content = re.sub(r'TDiet\.setBounds\(550, 270, 200, 23\);', 'TDiet.setBounds(550, 320, 200, 23);', content)

content = re.sub(r'LKodeInfus\.setBounds\(450, 300, 90, 23\);', 'LKodeInfus.setBounds(450, 350, 90, 23);', content)
content = re.sub(r'TKodeInfus\.setBounds\(550, 300, 200, 23\);', 'TKodeInfus.setBounds(550, 350, 200, 23);', content)

content = re.sub(r'BtnEWS2\.setBounds\(450, 340, 190, 30\);', 'BtnEWS2.setBounds(450, 390, 190, 30);', content)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
