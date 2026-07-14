import re
java_file = "src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"
with open(java_file, "r") as f:
    java_text = f.read()

tampil_cols = []
for match in re.finditer(r"this\.rs\.getString\(\"(.*?)\"\)", java_text):
    tampil_cols.append(match.group(1))

tampil_cols = tampil_cols[:50]
print(tampil_cols[15])
print(tampil_cols[16])
print(tampil_cols[17])
print(tampil_cols[18])

mapping = {}
for match in re.finditer(r"stringArray\[(\d+)\] = this\.([A-Za-z0-9_]+)\.(getSelectedItem|isSelected)\(\)", java_text):
    idx = int(match.group(1))
    cmb = match.group(2)
    mapping[idx] = cmb

print("Mapping:")
print(f"15 -> {mapping.get(15)}")
print(f"16 -> {mapping.get(16)}")
print(f"17 -> {mapping.get(17)}")
print(f"18 -> {mapping.get(18)}")
