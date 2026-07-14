import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    lines = f.readlines()

clean_lines = []
for line in lines:
    if re.search(r'String val_\d+\s*=\s*this\.rs\.getString', line):
        continue
    if re.search(r'\.setSelected\(".*?"\.equals\(val_\d+\)\);', line):
        continue
    clean_lines.append(line)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.writelines(clean_lines)

print("Stripped bad lines.")
