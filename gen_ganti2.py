import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

m = re.search(r'rs\.getString\("no_rawat"\), (.*?)rs\.getString\("cpot_kategori"\)', content, re.DOTALL)
text = 'rs.getString("no_rawat"), ' + m.group(1) + 'rs.getString("cpot_kategori")'
fields = re.findall(r'rs\.getString\("(.*?)"\)', text)

print("Total columns:", len(fields))
# In ganti(), stringArray[0] maps to fields[1] ("tanggal")
# and stringArray[218] maps to fields[219] ("cpot_kategori")
# stringArray[219] is used for "no_rawat=?"

set_str = "=?, ".join(fields[1:]) + "=?"
print("SET String:")
print(set_str)
