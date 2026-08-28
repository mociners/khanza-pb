import re

with open('src/rekammedis/RMDataResumePasienRanap.java', 'r') as f:
    content = f.read()

# 1. Remove jam fields from tabMode
content = re.sub(r'"Tgl\.Masuk", "Jam Masuk", "Tgl\.Keluar", "Jam Keluar",', '"Tgl.Masuk", "Tgl.Keluar",', content)

# 2. Fix widths for tabMode (remove i==10 and i==12, and shift everything > 12 by -2, and >10 by -1)
# Actually, since it's hardcoded if/else, let's just replace the exact block.
old_widths = """            } else if (i == 9) {
                column.setPreferredWidth(65);
            } else if (i == 10) {
                column.setPreferredWidth(65);
            } else if (i == 11) {
                column.setPreferredWidth(65);
            } else if (i == 12) {
                column.setPreferredWidth(65);
            } else if (i == 13) {"""
new_widths = """            } else if (i == 9) {
                column.setPreferredWidth(65);
            } else if (i == 10) {
                column.setPreferredWidth(65);
            } else if (i == 11) {"""
content = content.replace(old_widths, new_widths)
# Now shift the rest of the indices in the widths block:
def shift_widths(m):
    idx = int(m.group(1))
    if idx > 12:
        return f"else if (i == {idx - 2}) {{"
    return m.group(0)
content = re.sub(r'else if \(i == (\d+)\) \{', shift_widths, content)

# 3. Fix ganti() and simpan() - where tabMode.addRow() occurs.
# Remove JamMasuk and JamKeluar from addRow
# Old: Valid.SetTgl(Masuk.getSelectedItem()+""), (cmbJamMasuk.getSelectedItem()+":"+cmbMntMasuk.getSelectedItem()+":"+cmbDtkMasuk.getSelectedItem()), Valid.SetTgl(Keluar.getSelectedItem()+""), (cmbJamKeluar.getSelectedItem()+":"+cmbMntKeluar.getSelectedItem()+":"+cmbDtkKeluar.getSelectedItem()),
# New: Valid.SetTgl(Masuk.getSelectedItem()+""), Valid.SetTgl(Keluar.getSelectedItem()+""),
content = re.sub(
    r'Valid\.SetTgl\(Masuk\.getSelectedItem\(\)\+""\),\s*\([^,]+\),\s*Valid\.SetTgl\(Keluar\.getSelectedItem\(\)\+""\),\s*\([^,]+\),',
    r'Valid.SetTgl(Masuk.getSelectedItem()+""), Valid.SetTgl(Keluar.getSelectedItem()+""),',
    content
)

# 4. Fix getData() indices and parsing
def shift_getdata(m):
    idx = int(m.group(2))
    if idx == 9:
        return f"{m.group(1)} {idx})"
    elif idx > 12:
        return f"{m.group(1)} {idx - 2})"
    elif idx > 10:
        return f"{m.group(1)} {idx - 1})"
    return m.group(0)

# Replace Valid.SetTgl2 with Valid.SetTgl
content = content.replace("Valid.SetTgl2(Masuk", "Valid.SetTgl(Masuk")
content = content.replace("Valid.SetTgl2(Keluar", "Valid.SetTgl(Keluar")

# Remove the time setting logic in getData()
content = re.sub(r'String jamMsk =.*?\} catch \(Exception e\) \{\}\n\s*\}', '', content, flags=re.DOTALL)
content = re.sub(r'String jamKlr =.*?\} catch \(Exception e\) \{\}\n\s*\}', '', content, flags=re.DOTALL)

content = re.sub(r'(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),)\s*(\d+)\)', shift_getdata, content)

# 5. Fix tbObat.setValueAt in BtnSimpanActionPerformed/ganti
def shift_setvalue(m):
    idx = int(m.group(2))
    if idx > 12:
        return f"{m.group(1)} {idx - 2})"
    elif idx > 10:
        return f"{m.group(1)} {idx - 1})"
    return m.group(0)
content = re.sub(r'(tbObat\.setValueAt\(.*?, tbObat\.getSelectedRow\(\),)\s*(\d+)\)', shift_setvalue, content)
# Remove the ones for jammasuk and jamkeluar
content = re.sub(r'\s*tbObat\.setValueAt\(\(cmbJamMasuk.*?;\n', '\n', content)
content = re.sub(r'\s*tbObat\.setValueAt\(\(cmbJamKeluar.*?;\n', '\n', content)

# 6. Fix tampil() indices
# It does tabMode.addRow() with rs.getString("jam_reg") and rs.getString("jam_keluar").
# We just need to remove them.
content = content.replace(
    'rs.getString("tgl_registrasi"), rs.getString("jam_reg"), tglkeluar, rs.getString("jam_keluar"),',
    'rs.getString("tgl_registrasi"), tglkeluar,'
)

# 7. Fix report parameters:
# Remove param.put("jamkeluar", ...)
content = re.sub(r'\s*param\.put\("jamkeluar",.*?\);\n', '\n', content)

# 8. Remove the variables and UI bounds for cmbJamMasuk, etc.
content = re.sub(r'private widget\.ComboBox cmb(?:Jam|Mnt|Dtk)(?:Masuk|Keluar).*?;\n\s*', '', content)
content = re.sub(r'private widget\.CekBox ChkJam(?:Masuk|Keluar).*?;\n\s*', '', content)
content = re.sub(r'FormInput\.add\(cmb(?:Jam|Mnt|Dtk)(?:Masuk|Keluar)\).*?;\n\s*', '', content)
content = re.sub(r'FormInput\.add\(ChkJam(?:Masuk|Keluar)\).*?;\n\s*', '', content)
content = re.sub(r'cmb(?:Jam|Mnt|Dtk)(?:Masuk|Keluar)\.set.*?;', '', content)
content = re.sub(r'ChkJam(?:Masuk|Keluar)\.set.*?;', '', content)
content = re.sub(r'if \(ChkJam(?:Masuk|Keluar).*?\}\n', '', content, flags=re.DOTALL)

# Re-align Masuk and Keluar if needed (user just said "hapus jamnya"). 
# Let's keep Masuk and Keluar at their X bounds. We can remove jLabel18 and jLabel20 (Jam Masuk / Jam Keluar labels)
content = re.sub(r'FormInput\.add\(jLabel18\);', '', content)
content = re.sub(r'jLabel18\.setBounds.*?;', '', content)
content = re.sub(r'jLabel18\.setText\("Jam Masuk :"\);', '', content)
content = re.sub(r'jLabel18\.setName\("jLabel18"\);.*?\n', '', content)

content = re.sub(r'FormInput\.add\(jLabel20\);', '', content)
content = re.sub(r'jLabel20\.setBounds.*?;', '', content)
content = re.sub(r'jLabel20\.setText\("Jam Keluar :"\);', '', content)
content = re.sub(r'jLabel20\.setName\("jLabel20"\);.*?\n', '', content)

with open('src/rekammedis/RMDataResumePasienRanap.java', 'w') as f:
    f.write(content)

print("Done python refactor")
