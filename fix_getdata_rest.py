import re

# We will just write the custom assignments for these specific components since there are only ~15 of them.
# Let's map them directly to their column names according to the schema order.

# Let's get the exact columns mapping
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

# Check simpan() for the exact array index of the fields we missed
with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

simpan_lines = re.search(r'private void simpan\(\) \{(.*?)if \(this\.Sequel\.menyimpan', content, re.DOTALL).group(1)

generated = []
for line in simpan_lines.split('\n'):
    m2 = re.search(r'stringArray\[(\d+)\]\s*=\s*(.*);', line)
    if m2:
        idx = int(m2.group(1))
        expr = m2.group(2)
        if 'isSelected' not in expr and idx in columns:
            # It's a text field or combobox!
            # But wait, what if it's already assigned earlier in getData()? 
            # I only want to append the missing ones, which are mostly idx > 180!
            if idx > 180:
                col = columns[idx]
                generated.append(f'                        String val_{idx} = this.rs.getString("{col}");')
                if 'getText()' in expr:
                    comp = re.search(r'(this\.[A-Za-z0-9_]+)\.getText', expr).group(1)
                    generated.append(f'                        {comp}.setText(val_{idx});')
                elif 'getSelectedItem()' in expr:
                    comp = re.search(r'(this\.[A-Za-z0-9_]+)\.getSelectedItem', expr).group(1)
                    generated.append(f'                        {comp}.setSelectedItem(val_{idx});')
                elif 'getSelectedIndex()' in expr:
                    comp = re.search(r'(this\.[A-Za-z0-9_]+)\.getSelectedIndex', expr).group(1)
                    generated.append(f'                        try {{ {comp}.setSelectedIndex(Integer.parseInt(val_{idx})); }} catch(Exception e) {{}}')

print("\n".join(generated))
