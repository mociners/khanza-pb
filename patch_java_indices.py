import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Update getData() indices
def replace_get_data(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 22:
        return f"{prefix}{index + 1}{suffix}"
    return match.group(0)

# We find tbObat.getValueAt(tbObat.getSelectedRow(), XX)
code = re.sub(r'(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),\s*)(\d+)(\))', replace_get_data, code)

# We must add InfoBiaya.setSelectedItem(...) for index 22
get_data_jp_line = 'String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString();'
if get_data_jp_line in code:
    code = code.replace(get_data_jp_line, 'InfoBiaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());\n            ' + get_data_jp_line)
else:
    print("WARNING: Could not find jp line in getData()")

# 2. Update setValueAt() indices
def replace_set_value(match):
    prefix = match.group(1)
    index = int(match.group(2))
    suffix = match.group(3)
    if index >= 22:
        return f"{prefix}{index + 1}{suffix}"
    return match.group(0)

# We find tbObat.setValueAt(..., tbObat.getSelectedRow(), XX);
code = re.sub(r'(tbObat\.setValueAt\([^,]+,\s*tbObat\.getSelectedRow\(\),\s*)(\d+)(\);)', replace_set_value, code)
# Wait, some might have ternary operators like: tbObat.setValueAt(rbUmum.isSelected() ? ... : ..., tbObat.getSelectedRow(), XX);
# A safer regex for setValueAt index is:
code = re.sub(r'(tbObat\.setValueAt\(.*tbObat\.getSelectedRow\(\),\s*)(\d+)(\);)', replace_set_value, code)

# We must add tbObat.setValueAt(InfoBiaya.getSelectedItem().toString(), tbObat.getSelectedRow(), 22);
set_val_jp_line = 'tbObat.setValueAt(rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan"))), tbObat.getSelectedRow(), 23);'
if set_val_jp_line in code:
    code = code.replace(set_val_jp_line, 'tbObat.setValueAt(InfoBiaya.getSelectedItem().toString(), tbObat.getSelectedRow(), 22);\n            ' + set_val_jp_line)
else:
    print("WARNING: Could not find set_val_jp_line in setValueAt")


with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Patch applied for Java indices!")
