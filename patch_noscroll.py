import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# INTAKE 24h
content = content.replace('LInput24.setBounds(250, 320, 80, 23);', 'LInput24.setBounds(50, 350, 100, 23);')
content = content.replace('TInput24.setBounds(330, 320, 70, 23);', 'TInput24.setBounds(170, 350, 70, 23);')

# OUTPUT Section -> Move to X=380 (Labels) and X=470 (Fields)
content = content.replace('jLabel254.setBounds(70, 370, 100, 23);', 'jLabel254.setBounds(350, 140, 100, 23);')
content = content.replace('Keluar1.setBounds(170, 370, 70, 23);', 'Keluar1.setBounds(450, 140, 70, 23);')

content = content.replace('jLabel255.setBounds(70, 400, 100, 23);', 'jLabel255.setBounds(350, 170, 100, 23);')
content = content.replace('Keluar2.setBounds(170, 400, 70, 23);', 'Keluar2.setBounds(450, 170, 70, 23);')

content = content.replace('jLabel256.setBounds(70, 430, 100, 23);', 'jLabel256.setBounds(350, 200, 100, 23);')
content = content.replace('Keluar3.setBounds(170, 430, 70, 23);', 'Keluar3.setBounds(450, 200, 70, 23);')

content = content.replace('jLabel257.setBounds(70, 460, 100, 23);', 'jLabel257.setBounds(350, 230, 100, 23);')
content = content.replace('Keluar4.setBounds(170, 460, 70, 23);', 'Keluar4.setBounds(450, 230, 70, 23);')

content = content.replace('jLabel258.setBounds(70, 490, 100, 23);', 'jLabel258.setBounds(350, 260, 100, 23);')
content = content.replace('Keluar5.setBounds(170, 490, 70, 23);', 'Keluar5.setBounds(450, 260, 70, 23);')

content = content.replace('jLabel259.setBounds(70, 520, 100, 23);', 'jLabel259.setBounds(350, 290, 100, 23);')
content = content.replace('JumlahKeluar.setBounds(170, 520, 70, 23);', 'JumlahKeluar.setBounds(450, 290, 70, 23);')

content = content.replace('LOutput24.setBounds(250, 520, 80, 23);', 'LOutput24.setBounds(350, 320, 100, 23);')
content = content.replace('TOutput24.setBounds(330, 520, 70, 23);', 'TOutput24.setBounds(450, 320, 70, 23);')

content = content.replace('LBalance24.setBounds(50, 550, 100, 23);', 'LBalance24.setBounds(350, 350, 100, 23);')
content = content.replace('TBalance24.setBounds(170, 550, 70, 23);', 'TBalance24.setBounds(450, 350, 70, 23);')

# TTV Section -> Move to X=550 (Labels) and X=650 (Fields)
content = content.replace('LNadi.setBounds(450, 140, 90, 23);', 'LNadi.setBounds(580, 140, 90, 23);')
content = content.replace('TNadi.setBounds(550, 140, 100, 23);', 'TNadi.setBounds(680, 140, 100, 23);')

content = content.replace('LRespirasi.setBounds(450, 170, 90, 23);', 'LRespirasi.setBounds(580, 170, 90, 23);')
content = content.replace('TRespirasi.setBounds(550, 170, 100, 23);', 'TRespirasi.setBounds(680, 170, 100, 23);')

content = content.replace('LSuhu.setBounds(450, 200, 90, 23);', 'LSuhu.setBounds(580, 200, 90, 23);')
content = content.replace('TSuhu.setBounds(550, 200, 100, 23);', 'TSuhu.setBounds(680, 200, 100, 23);')

content = content.replace('LTensi.setBounds(450, 230, 90, 23);', 'LTensi.setBounds(580, 230, 90, 23);')
content = content.replace('TTensi.setBounds(550, 230, 100, 23);', 'TTensi.setBounds(680, 230, 100, 23);')

content = content.replace('LBB.setBounds(450, 260, 90, 23);', 'LBB.setBounds(580, 260, 90, 23);')
content = content.replace('TBB.setBounds(550, 260, 100, 23);', 'TBB.setBounds(680, 260, 100, 23);')

content = content.replace('LTB.setBounds(450, 290, 90, 23);', 'LTB.setBounds(580, 290, 90, 23);')
content = content.replace('TTB.setBounds(550, 290, 100, 23);', 'TTB.setBounds(680, 290, 100, 23);')

content = content.replace('LDiet.setBounds(450, 320, 90, 23);', 'LDiet.setBounds(580, 320, 90, 23);')
content = content.replace('TDiet.setBounds(550, 320, 200, 23);', 'TDiet.setBounds(680, 320, 180, 23);')

content = content.replace('LKodeInfus.setBounds(450, 350, 90, 23);', 'LKodeInfus.setBounds(580, 350, 90, 23);')
content = content.replace('TKodeInfus.setBounds(550, 350, 200, 23);', 'TKodeInfus.setBounds(680, 350, 180, 23);')

content = content.replace('LInterval.setBounds(450, 380, 90, 23);', 'LInterval.setBounds(580, 380, 90, 23);')
content = content.replace('TInterval.setBounds(550, 380, 200, 23);', 'TInterval.setBounds(680, 380, 180, 23);')

content = content.replace('BtnEWS2.setBounds(450, 420, 190, 30);', 'BtnEWS2.setBounds(580, 420, 190, 30);')

# Resize FormInput and internalFrame2 so no scroll is needed
content = content.replace('FormInput.setPreferredSize(new java.awt.Dimension(870, 800));', 'FormInput.setPreferredSize(new java.awt.Dimension(870, 470));')
content = content.replace('internalFrame2.setPreferredSize(new java.awt.Dimension(1024, 300));', 'internalFrame2.setPreferredSize(new java.awt.Dimension(1024, 490));')

# Also fix the vertical line separator if any
# We'll just leave jSeparator1 as is for now

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
