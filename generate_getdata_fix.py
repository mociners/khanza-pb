import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# 1. Parse tampil() to get column name for each index
tampil_match = re.search(r'this\.tbObat\.addRow\(new Object\[\]\{(.*?)\}\);', content, re.DOTALL)
columns = {}
if tampil_match:
    fields = tampil_match.group(1).split(', ')
    for i, field in enumerate(fields):
        m = re.search(r'this\.rs\.getString\("(.*?)"\)', field)
        if m:
            columns[i] = m.group(1)

# 2. Parse simpan() to get the stringArray assignments
simpan_match = re.search(r'private void simpan\(\) \{.*?(stringArray\[0\] =.*?);.*?if \(this\.Sequel\.menyimpantf', content, re.DOTALL)
if simpan_match:
    simpan_code = simpan_match.group(1)
    
    generated_code = []
    
    # We look for ternary assignments
    # Because of our previous fix_ternary.py, they are all nicely formatted like:
    # stringArray[45] = this.RdoStatusCM.isSelected() ? "CM" : (this.RdoStatusBingung.isSelected() ? "Bingung" : ...);
    
    lines = simpan_code.split('\n')
    for line in lines:
        m = re.search(r'stringArray\[(\d+)\]\s*=\s*(.*);', line)
        if m:
            idx = int(m.group(1))
            expr = m.group(2)
            
            if 'isSelected()' in expr:
                if idx in columns:
                    col_name = columns[idx]
                    
                    # Extract all this.ComponetName.isSelected() ? "Value"
                    pairs = re.findall(r'(this\.[a-zA-Z0-9_]+)\.isSelected\(\)\s*\?\s*"([^"]*)"', expr)
                    if pairs:
                        generated_code.append(f'                        String val_{idx} = this.rs.getString("{col_name}");')
                        for comp, val in pairs:
                            generated_code.append(f'                        {comp}.setSelected("{val}".equals(val_{idx}));')

    print("\n".join(generated_code))
