import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# get columns from tampil()
m_tampil = re.search(r'addRow\(new Object\[\]\{(.*?)\}\);', content, re.DOTALL)
columns = {}
if m_tampil:
    # Use re.findall because split(',') fails if there are commas inside string literals, although usually there aren't here
    fields = re.findall(r'this\.rs\.getString\("(.*?)"\)', m_tampil.group(1))
    for i, field in enumerate(fields):
        columns[i] = field
else:
    print("tampil() not found with addRow")

# get assignments from simpan()
simpan_lines = re.search(r'private void simpan\(\) \{(.*?)if \(this\.Sequel\.menyimpan', content, re.DOTALL).group(1)

generated = []
for line in simpan_lines.split('\n'):
    m = re.search(r'stringArray\[(\d+)\]\s*=\s*(.*);', line)
    if m:
        idx = int(m.group(1))
        expr = m.group(2)
        if 'isSelected' in expr and idx in columns:
            col = columns[idx]
            pairs = re.findall(r'(this\.[A-Za-z0-9_]+)\.isSelected\(\)\s*\?\s*"([^"]*)"', expr)
            if pairs:
                generated.append(f'                        String val_{idx} = this.rs.getString("{col}");')
                for comp, val in pairs:
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
            else:
                pass

with open('generated_getData.txt', 'w') as f:
    f.write('\n'.join(generated))
