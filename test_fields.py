import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

m = re.search(r'rs\.getString\("no_rawat"\), (.*?)rs\.getString\("cpot_kategori"\)', content, re.DOTALL)
text = 'rs.getString("no_rawat"), ' + m.group(1) + 'rs.getString("cpot_kategori")'
fields = re.findall(r'rs\.getString\("(.*?)"\)', text)

# Find 'tanggal' index
idx = fields.index('tanggal')
fields_to_update = fields[idx:]

print("Columns from tanggal:", len(fields_to_update))
set_str = "=?, ".join(fields_to_update) + "=?"
print(set_str)

