import re

with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql = f.read()

col_lines = re.findall(r'^\s+([a-zA-Z0-9_]+)\s+[a-zA-Z]', sql, re.MULTILINE)

# Remove PRIMARY, KEY, CONSTRAINT which were matched by the regex
cols = []
for c in col_lines:
    if c not in ['PRIMARY', 'KEY', 'CONSTRAINT']:
        cols.append(c)

print("Total columns:", len(cols))
# index 0 is no_rawat. We skip it for the SET string.
set_str = "=?, ".join(cols[1:]) + "=?"
print("SET String:")
print(set_str)

# Now inject this into RMPenilaianAwalKeperawatanRanapDewasa.java!
with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# Replace the ganti() SQL parameters string
# We need to find `this.Sequel.mengedittf("penilaian_awal_keperawatan_ranap_dewasa", "no_rawat=?", "tanggal=?, ... cpot_kategori=?", 220, stringArray)`
pattern = r'(this\.Sequel\.mengedittf\("penilaian_awal_keperawatan_ranap_dewasa", "no_rawat=\?", ")(.*?)(\", 220, stringArray\))'
m = re.search(pattern, content)
if m:
    new_content = content[:m.start(2)] + set_str + content[m.end(2):]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected successfully!")
else:
    print("Could not find mengedittf!")
