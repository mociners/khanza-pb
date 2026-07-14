import re

with open("src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java", "r") as f:
    code = f.read()

# Find the block inside tampil() containing rs.getString
tampil_start = code.find('tabMode.addRow(new String[]{')
tampil_end = code.find('});', tampil_start)

tampil_code = code[tampil_start:tampil_end]

# Extract all "field_name" from rs.getString("field_name")
fields = re.findall(r'rs\.getString\("([^"]+)"\)', tampil_code)

print(f"Total fields found: {len(fields)}")
print(", ".join(fields))
