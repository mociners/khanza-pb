import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Variables
vars = """
    private widget.Label LInterval;
    private widget.TextBox TInterval;
"""
content = re.sub(r'(private widget\.Label LKodeInfus;)', vars + r'\n\1', content)

# 2. Instantiations
insts = """
        LInterval = new widget.Label();
        TInterval = new widget.TextBox();
"""
content = content.replace("LKodeInfus = new widget.Label();", insts + "\n        LKodeInfus = new widget.Label();")

# 3. Setup and Add
setup = """
        LInterval.setText("Interval/6 Jam :");
        LInterval.setName("LInterval");
        FormInput.add(LInterval);
        LInterval.setBounds(450, 380, 90, 23);
        
        TInterval.setName("TInterval");
        TInterval.setHighlighter(null);
        FormInput.add(TInterval);
        TInterval.setBounds(550, 380, 200, 23);
"""
content = content.replace("FormInput.add(LKodeInfus);", setup + "\n        FormInput.add(LKodeInfus);")

# Move BtnEWS2 down
content = re.sub(r'BtnEWS2\.setBounds\(450, 390, 190, 30\);', 'BtnEWS2.setBounds(450, 420, 190, 30);', content)


# 4. tabMode
old_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Balance Cairan"'
new_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Balance Cairan"'
content = content.replace(old_cols, new_cols)


# 5. BtnSimpan
old_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",26'
new_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",27'
content = content.replace(old_simpan_q, new_simpan_q)

# Fix Simpan Args
content = content.replace('TDiet.getText(),TKodeInfus.getText(),Masuk1.getText()', 'TDiet.getText(),TKodeInfus.getText(),TInterval.getText(),Masuk1.getText()')


# 6. BtnEdit
old_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,balance=?,nik=?",29'
new_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,balance=?,nik=?",30'
content = content.replace(old_edit_q, new_edit_q)

# Simpan and Edit args replace already handled TInterval.getText() globally in the line above!
# Wait, I did `content.replace('TDiet.getText(),TKodeInfus.getText(),Masuk1.getText()', ...)` which replaced both!


# 7. tampil() Select
content = content.replace('rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.nik', 'rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.interval_waktu,rm_ttv_balance_cairan.nik')

# 8. tampil() addRow
content = content.replace('rs.getString("diet"),rs.getString("kode_infus"),\\n                        rs.getString("intake_makan")', 'rs.getString("diet"),rs.getString("kode_infus"),rs.getString("interval_waktu"),\\n                        rs.getString("intake_makan")')
content = content.replace('rs.getString("diet"),rs.getString("kode_infus"),\n                        rs.getString("intake_makan")', 'rs.getString("diet"),rs.getString("kode_infus"),rs.getString("interval_waktu"),\n                        rs.getString("intake_makan")')

# 9. getData()
# We need to shift indices from 17 to 30.
# I will use a regex substitution for getData() indices.
def shift_index(match):
    idx = int(match.group(1))
    if idx >= 17:
        idx += 1
    return f"tbObat.getValueAt(tbObat.getSelectedRow(),{idx})"

content = re.sub(r'tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),(\d+)\)', shift_index, content)

# Inject TInterval into getData()
get_data_inject = """            TKodeInfus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TInterval.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());"""
content = re.sub(r'TKodeInfus\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),16\)\.toString\(\)\);', get_data_inject, content)

# 10. emptTeks()
content = content.replace('TKodeInfus.setText("");', 'TKodeInfus.setText("");\n        TInterval.setText("");')

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
