import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

old_setNoRm = r'(public void setNoRm\(String norwt\) \{\n\s*)(this\.TNoRw\.setText\(norwt\);)'
new_setNoRm = r'\1\2\n        this.TCari.setText(norwt);'
content = re.sub(old_setNoRm, new_setNoRm, content)

old_dtp1 = r'(this\.DTPCari1 = new widget\.Tanggal\(\);\n\s*this\.DTPCari1\.setPreferredSize\(new Dimension\(90, 23\)\);)'
new_dtp1 = r'\1\n        this.DTPCari1.setDisplayFormat("dd-MM-yyyy");'
content = re.sub(old_dtp1, new_dtp1, content)

old_dtp2 = r'(this\.DTPCari2 = new widget\.Tanggal\(\);\n\s*this\.DTPCari2\.setPreferredSize\(new Dimension\(90, 23\)\);)'
new_dtp2 = r'\1\n        this.DTPCari2.setDisplayFormat("dd-MM-yyyy");'
content = re.sub(old_dtp2, new_dtp2, content)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

