import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

content = content.replace('if (val_216.equals("-"))', 'if ("-".equals(val_216))')
content = content.replace('if (val_217.equals("-"))', 'if ("-".equals(val_217))')

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

print("Fixed NPE for CPOT")
