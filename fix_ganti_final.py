import re

# 1. Base columns (182 columns, index 0 is no_rawat)
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql1 = f.read()
col_lines = re.findall(r'^\s+([a-zA-Z0-9_]+)\s+[a-zA-Z]', sql1, re.MULTILINE)
cols = [c for c in col_lines if c not in ['PRIMARY', 'KEY', 'CONSTRAINT']]
# exclude no_rawat
cols = cols[1:]

# 2. Nyeri columns
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_nyeri.sql', 'r') as f:
    sql2 = f.read()
nyeri_cols = re.findall(r'ADD COLUMN `([a-zA-Z0-9_]+)`', sql2)

# 3. CPOT columns
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_cpot.sql', 'r') as f:
    sql3 = f.read()
cpot_cols = re.findall(r'ADD COLUMN `([a-zA-Z0-9_]+)`', sql3)

all_cols = cols + nyeri_cols + cpot_cols
print("Total columns for SET:", len(all_cols)) # should be 219

set_str = "=?, ".join(all_cols) + "=?"

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# Replace the ganti() SQL parameters string
# Currently the string inside mengedittf ends with masalah_pencernaan=? because of my previous script
# Wait, let's just find the whole mengedittf parameter
pattern = r'(this\.Sequel\.mengedittf\("penilaian_awal_keperawatan_ranap_dewasa", "no_rawat=\?", ")(.*?)(\", 220, stringArray\))'
m = re.search(pattern, content)
if m:
    new_content = content[:m.start(2)] + set_str + content[m.end(2):]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected successfully!")
else:
    print("Could not find mengedittf!")
