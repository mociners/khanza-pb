import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Fix Petugas bounds
content = re.sub(r'label14\.setBounds\(0, 40, 70, 23\);', 'label14.setBounds(440, 70, 60, 23);', content)
content = re.sub(r'KdPetugas\.setBounds\(74, 40, 140, 23\);', 'KdPetugas.setBounds(504, 70, 100, 23);', content)
content = re.sub(r'NmPetugas\.setBounds\(215, 40, 220, 23\);', 'NmPetugas.setBounds(608, 70, 210, 23);', content)
content = re.sub(r'BtnDokter\.setBounds\(430, 40, 28, 23\);', 'BtnDokter.setBounds(820, 70, 28, 23);', content)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

