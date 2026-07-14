import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# Instead of looking for addRow, let's just find the big list of rs.getString
# It starts with rs.getString("cpot_kategori") or something at the end of tampil()
m = re.search(r'rs\.getString\("no_rawat"\), (.*?)rs\.getString\("cpot_kategori"\)', content, re.DOTALL)
if not m:
    print("big rs.getString block not found")
    exit(1)

text = 'rs.getString("no_rawat"), ' + m.group(1) + 'rs.getString("cpot_kategori")'
fields = re.findall(r'this\.rs\.getString\("(.*?)"\)', text.replace('this.rs', 'rs'))
# Wait, some are this.rs.getString and some are rs.getString.
fields = re.findall(r'rs\.getString\("(.*?)"\)', text)

columns = {i: field for i, field in enumerate(fields)}

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
                for comp, val in pairs:
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')

with open('generated_getData.txt', 'w') as f:
    f.write('\n'.join(generated))
print(f"Generated {len(generated)} lines")
