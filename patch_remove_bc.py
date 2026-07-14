import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()


# 1. Remove BC and jLabel260 from FormInput
content = content.replace('FormInput.add(jLabel260);', '// FormInput.add(jLabel260);')
content = content.replace('FormInput.add(BC);', '// FormInput.add(BC);')

# 2. Relocate LBalance24 and TBalance24 to old BC position
content = content.replace('LBalance24.setBounds(250, 550, 80, 23);', 'LBalance24.setBounds(50, 550, 100, 23);')
content = content.replace('TBalance24.setBounds(330, 550, 70, 23);', 'TBalance24.setBounds(170, 550, 70, 23);')
content = content.replace('LBalance24.setText("Total/24 Jam:");', 'LBalance24.setText("Balance Cairan :");')

# 3. tabMode
old_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Input/24 Jam","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Output/24 Jam","Balance Cairan","Balance/24 Jam"'
new_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Input/24 Jam","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Output/24 Jam","Balance Cairan"'
content = content.replace(old_cols, new_cols)

# 4. BtnSimpan
old_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",30'
new_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",29'
content = content.replace(old_simpan_q, new_simpan_q)

# Args
content = content.replace('JumlahKeluar.getText(),TOutput24.getText(),BC.getText(),TBalance24.getText(),KdPetugas.getText()', 'JumlahKeluar.getText(),TOutput24.getText(),TBalance24.getText(),KdPetugas.getText()')

# 5. BtnEdit
old_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,jumlah_input_24=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,jumlah_output_24=?,balance=?,balance_24=?,nik=?",33'
new_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,jumlah_input_24=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,jumlah_output_24=?,balance_24=?,nik=?",32'
content = content.replace(old_edit_q, new_edit_q)

# Args for BtnEdit (handled globally)


# 6. tampil() Select
content = content.replace('rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance,rm_ttv_balance_cairan.balance_24', 'rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance_24')

# 7. tampil() addRow
content = content.replace('rs.getString("jumlah_output"),rs.getString("jumlah_output_24"),rs.getString("balance"),rs.getString("balance_24")', 'rs.getString("jumlah_output"),rs.getString("jumlah_output_24"),rs.getString("balance_24")')


# 8. getData()
# We need to shift index 34 to 33, because 33 (BC) is removed.
# And `TBalance24` will read from 33.
get_data_inject2 = """            JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            TOutput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            TBalance24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());"""

get_data_new = """            JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            TOutput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            TBalance24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());"""
content = content.replace(get_data_inject2, get_data_new)

# 9. isBc()
isBc_old = """    private void isBc(){
        try {
            BC.setText((Integer.parseInt(JumlahKeluar.getText())-Integer.parseInt(JumlahMasuk.getText()))+"");
            hitung24Jam();
        } catch (Exception e) {
            BC.setText("0");
        }
    }"""
isBc_new = """    private void isBc(){
        hitung24Jam();
    }"""
content = content.replace(isBc_old, isBc_new)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

