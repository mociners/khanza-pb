import re

# 1. Base columns
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql1 = f.read()
col_lines = re.findall(r'^\s+([a-zA-Z0-9_]+)\s+[a-zA-Z]', sql1, re.MULTILINE)
cols = [c for c in col_lines if c not in ['PRIMARY', 'KEY', 'CONSTRAINT']]

# 2. Nyeri columns
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_nyeri.sql', 'r') as f:
    sql2 = f.read()
nyeri_cols = re.findall(r'ADD COLUMN `([a-zA-Z0-9_]+)`', sql2)

# 3. CPOT columns
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_cpot.sql', 'r') as f:
    sql3 = f.read()
cpot_cols = re.findall(r'ADD COLUMN `([a-zA-Z0-9_]+)`', sql3)

all_cols = cols + nyeri_cols + cpot_cols
columns = {i: col for i, col in enumerate(all_cols)}

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

simpan_lines = re.search(r'private void simpan\(\) \{(.*?)if \(this\.Sequel\.menyimpan', content, re.DOTALL).group(1)

generated = []
for line in simpan_lines.split('\n'):
    m2 = re.search(r'stringArray\[(\d+)\]\s*=\s*(.*);', line)
    if m2:
        idx = int(m2.group(1))
        expr = m2.group(2)
        if 'isSelected' in expr and idx in columns:
            col = columns[idx]
            pairs = re.findall(r'(this\.[A-Za-z0-9_]+)\.isSelected\(\)\s*\?\s*"([^"]*)"', expr)
            if pairs:
                generated.append(f'                        String val_{idx} = this.rs.getString("{col}");')
                if len(pairs) == 1 and ("Ya" in pairs[0][1] or pairs[0][1] in ["Baik", "Bebas", "Normal", "Vesikuler", "Teratur", "Kuning jernih", "Tidak masalah", "Tertutup", "Terbuka", "Tidak"]):
                    comp, val = pairs[0]
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
                else:
                    for comp, val in pairs:
                        generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')

# Find ket_masalah_sirk_3 to inject
pattern = r'(this\.TMasalahSirkJelas3\.setText\(this\.rs\.getString\("ket_masalah_sirk_3"\)\);)'
m = re.search(pattern, content)
if m:
    new_content = content[:m.end()] + '\n' + '\n'.join(generated) + content[m.end():]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected cleanly.")
else:
    print("Anchor not found!")

