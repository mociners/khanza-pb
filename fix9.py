import re

with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql_content = f.read()

# Extract column names (ignoring PRIMARY KEY etc)
col_lines = re.findall(r'^\s+([a-zA-Z0-9_]+)\s+[a-zA-Z]', sql_content, re.MULTILINE)
columns = {i: col for i, col in enumerate(col_lines)}

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
                # For CheckBoxes (Yes/No), just use true/false directly
                if len(pairs) == 1 and ("Ya" in pairs[0][1] or pairs[0][1] in ["Baik", "Bebas", "Normal", "Vesikuler", "Teratur", "Kuning jernih", "Tidak masalah", "Tertutup", "Terbuka", "Tidak"]):
                    comp, val = pairs[0]
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
                else:
                    for comp, val in pairs:
                        generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')

with open('generated_getData.txt', 'w') as f:
    f.write('\n'.join(generated))
print(f"Generated {len(generated)} lines")
