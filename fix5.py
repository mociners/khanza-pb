import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# 1. Get the columns array from tampil()
m_tampil = re.search(r'addRow\(new Object\[\]\{(.*?)\}\);', content, re.DOTALL)
columns = {}
if m_tampil:
    fields = re.findall(r'this\.rs\.getString\("(.*?)"\)', m_tampil.group(1))
    for i, field in enumerate(fields):
        columns[i] = field

# 2. Get simpan() to map RadioButtons to columns
simpan_lines = re.search(r'private void simpan\(\) \{(.*?)if \(this\.Sequel\.menyimpan', content, re.DOTALL).group(1)

generated = []
for line in simpan_lines.split('\n'):
    m = re.search(r'stringArray\[(\d+)\]\s*=\s*(.*);', line)
    if m:
        idx = int(m.group(1))
        expr = m.group(2)
        if 'isSelected' in expr and idx in columns:
            col = columns[idx]
            # Match any Component.isSelected() ? "Value"
            pairs = re.findall(r'(this\.[A-Za-z0-9_]+)\.isSelected\(\)\s*\?\s*"([^"]*)"', expr)
            if pairs:
                generated.append(f'                        String val_{idx} = this.rs.getString("{col}");')
                # Wait, CheckBoxes just have ? "Ya" : "Tidak".
                # For Checkboxes, if they are Yes/No, it is better to just do `comp.setSelected(this.rs.getString("col").equals("Ya"))`
                if len(pairs) == 1 and ("Ya" in pairs[0][1] or pairs[0][1] in ["Baik", "Bebas", "Normal", "Vesikuler", "Teratur", "Kuning jernih", "Tidak masalah"]): 
                    comp, val = pairs[0]
                    generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
                else:
                    for comp, val in pairs:
                        generated.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')
                        

with open('generated_getData.txt', 'w') as f:
    f.write('\n'.join(generated))
