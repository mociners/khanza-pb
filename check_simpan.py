import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

simpan_match = re.search(r'private void simpan\(\) \{.*?(stringArray\[0\] =.*?);.*?if \(this\.Sequel\.menyimpantf', content, re.DOTALL)
if simpan_match:
    simpan_code = simpan_match.group(1)
    
    # Check for .isSelected() usage
    selected = re.findall(r'stringArray\[\d+\] = (.*?).isSelected\(\)', simpan_code)
    print("Found isSelected in simpan:", len(selected))
    for s in selected:
        print(s)
else:
    print("simpan() not found")

