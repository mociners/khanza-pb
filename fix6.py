import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# Just extract the huge string array instantiation inside tampil()
m = re.search(r'tabMode\.addRow\(new Object\[\]\{(.*?)\}\);', content, re.DOTALL)
if not m:
    print("tabMode.addRow not found!")
    exit(1)

fields = re.findall(r'this\.rs\.getString\("(.*?)"\)', m.group(1))
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
                if len(pairs) == 1:
                    comp, val = pairs[0]
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
                else:
                    for comp, val in pairs:
                        generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')

with open('generated_getData.txt', 'w') as f:
    f.write('\n'.join(generated))
