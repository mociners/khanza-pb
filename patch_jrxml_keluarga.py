import re

with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    code = f.read()

# 1. Add fields for keluarga_1 and keluarga_2
field_dec_1 = '\t<field name="keluarga_1" class="java.lang.String"/>\n'
field_dec_2 = '\t<field name="keluarga_2" class="java.lang.String"/>\n'

if field_dec_1 not in code:
    code = code.replace('\t<field name="jenis_pembiayaan"', field_dec_1 + field_dec_2 + '\t<field name="jenis_pembiayaan"')

# 2. Patch the text
old_text = """"c. " + ("Saya memberi wewenang kepada "+$P{namars}+" untuk memberikan informasi tentang diagnosa, hasil pelayanan dan pengobatan saya kepada anggota keluarga saya, kepada:\\n"+
"1. .............................................................\\n"+
"2. .............................................................")"""

new_text = """"c. " + ("Saya memberi wewenang kepada "+$P{namars}+" untuk memberikan informasi tentang diagnosa, hasil pelayanan dan pengobatan saya kepada anggota keluarga saya, kepada:\\n"+
"1. " + ($F{keluarga_1}.equals("") ? "............................................................." : $F{keluarga_1}) + "\\n"+
"2. " + ($F{keluarga_2}.equals("") ? "............................................................." : $F{keluarga_2}))"""

code = code.replace(old_text, new_text)

with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(code)

print("JRXML Code Patched for Keluarga!")
