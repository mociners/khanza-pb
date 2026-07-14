import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Variables
vars = """
    private widget.Label LInput24;
    private widget.TextBox TInput24;
    private widget.Label LOutput24;
    private widget.TextBox TOutput24;
    private widget.Label LBalance24;
    private widget.TextBox TBalance24;
"""
content = re.sub(r'(private widget\.Label LInterval;)', vars + r'\n\1', content)

# 2. Instantiations
insts = """
        LInput24 = new widget.Label();
        TInput24 = new widget.TextBox();
        LOutput24 = new widget.Label();
        TOutput24 = new widget.TextBox();
        LBalance24 = new widget.Label();
        TBalance24 = new widget.TextBox();
"""
content = content.replace("LInterval = new widget.Label();", insts + "\n        LInterval = new widget.Label();")

# 3. Setup and Add
setup = """
        LInput24.setText("Jml/24 Jam :");
        LInput24.setName("LInput24");
        FormInput.add(LInput24);
        LInput24.setBounds(250, 320, 80, 23);
        
        TInput24.setName("TInput24");
        TInput24.setEditable(false);
        TInput24.setHighlighter(null);
        FormInput.add(TInput24);
        TInput24.setBounds(330, 320, 70, 23);
        
        LOutput24.setText("Jml/24 Jam :");
        LOutput24.setName("LOutput24");
        FormInput.add(LOutput24);
        LOutput24.setBounds(250, 520, 80, 23);
        
        TOutput24.setName("TOutput24");
        TOutput24.setEditable(false);
        TOutput24.setHighlighter(null);
        FormInput.add(TOutput24);
        TOutput24.setBounds(330, 520, 70, 23);
        
        LBalance24.setText("Total/24 Jam:");
        LBalance24.setName("LBalance24");
        FormInput.add(LBalance24);
        LBalance24.setBounds(250, 550, 80, 23);
        
        TBalance24.setName("TBalance24");
        TBalance24.setEditable(false);
        TBalance24.setHighlighter(null);
        FormInput.add(TBalance24);
        TBalance24.setBounds(330, 550, 70, 23);
"""
content = content.replace("FormInput.add(LInterval);", setup + "\n        FormInput.add(LInterval);")


# 4. tabMode
old_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Balance Cairan"'
new_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Interval/6 Jam","Makan","Minum","NGT","Transfusi","Infus","Sisa Infus","Jumlah Masuk","Input/24 Jam","Urine","Muntah",\n            "NGT","IWL","Drain","Jumlah Keluar","Output/24 Jam","Balance Cairan","Balance/24 Jam"'
content = content.replace(old_cols, new_cols)


# 5. BtnSimpan
old_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",27'
new_simpan_q = '"rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",30'
content = content.replace(old_simpan_q, new_simpan_q)

# Args
content = content.replace('JumlahMasuk.getText(),Keluar1.getText()', 'JumlahMasuk.getText(),TInput24.getText(),Keluar1.getText()')
content = content.replace('JumlahKeluar.getText(),BC.getText(),KdPetugas.getText()', 'JumlahKeluar.getText(),TOutput24.getText(),BC.getText(),TBalance24.getText(),KdPetugas.getText()')


# 6. BtnEdit
old_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,balance=?,nik=?",30'
new_edit_q = '"rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,interval_waktu=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,jumlah_input_24=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,jumlah_output_24=?,balance=?,balance_24=?,nik=?",33'
content = content.replace(old_edit_q, new_edit_q)

# Args for BtnEdit (already modified globally by step 5 for `JumlahMasuk` and `JumlahKeluar`)


# 7. tampil() Select
content = content.replace('rm_ttv_balance_cairan.jumlah_input,', 'rm_ttv_balance_cairan.jumlah_input,rm_ttv_balance_cairan.jumlah_input_24,')
content = content.replace('rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.balance', 'rm_ttv_balance_cairan.jumlah_output,rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance,rm_ttv_balance_cairan.balance_24')

# 8. tampil() addRow
content = content.replace('rs.getString("jumlah_input"),', 'rs.getString("jumlah_input"),rs.getString("jumlah_input_24"),')
content = content.replace('rs.getString("jumlah_output"),rs.getString("balance")', 'rs.getString("jumlah_output"),rs.getString("jumlah_output_24"),rs.getString("balance"),rs.getString("balance_24")')


# 9. getData() index shift
# New Columns:
# 25 is Input/24 (was 25=Urine before shift) -> urine goes to 26
# 32 is Output/24 (was 32=BC) -> BC goes to 33
# 34 is Balance/24 
def shift_index2(match):
    idx = int(match.group(1))
    if 25 <= idx <= 30:
        idx += 1
    elif idx == 31: # Balance
        idx += 2
    return f"tbObat.getValueAt(tbObat.getSelectedRow(),{idx})"

content = re.sub(r'tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),(\d+)\)', shift_index2, content)

get_data_inject = """            JumlahMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            TInput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());"""
content = re.sub(r'JumlahMasuk\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),24\)\.toString\(\)\);', get_data_inject, content)

get_data_inject2 = """            JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            TOutput24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            TBalance24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());"""
content = re.sub(r'JumlahKeluar\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),31\)\.toString\(\)\);\s+BC\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),33\)\.toString\(\)\);', get_data_inject2, content)

# Also fix the BC shift index bug if it became 33. wait, old was 31 for BC.
# if 31, it shifted to 33.


# 10. isBc() and hitung24Jam()
hitung24Jam = """    private void hitung24Jam() {
        if (TNoRw.getText().trim().equals("") || Tanggal.getDate() == null) {
            return;
        }
        try {
            int input24 = 0;
            int output24 = 0;
            ps=koneksi.prepareStatement("SELECT SUM(jumlah_input), SUM(jumlah_output) FROM rm_ttv_balance_cairan WHERE no_rawat=? AND tanggal=? AND jam!=?");
            try {
                ps.setString(1, TNoRw.getText());
                ps.setString(2, Valid.SetTgl(Tanggal.getSelectedItem()+""));
                ps.setString(3, CmbJam.getSelectedItem()+":"+CmbMnt.getSelectedItem()+":"+CmbDtk.getSelectedItem());
                rs=ps.executeQuery();
                if(rs.next()){
                    input24 = rs.getInt(1);
                    output24 = rs.getInt(2);
                }
            } catch (Exception e) {
                System.out.println("Notif 24 Jam: "+e);
            } finally{
                if(rs!=null){ rs.close(); }
                if(ps!=null){ ps.close(); }
            }
            
            int currentInput = 0;
            try { currentInput = Integer.parseInt(JumlahMasuk.getText()); } catch(Exception e){}
            
            int currentOutput = 0;
            try { currentOutput = Integer.parseInt(JumlahKeluar.getText()); } catch(Exception e){}
            
            int totalInput24 = input24 + currentInput;
            int totalOutput24 = output24 + currentOutput;
            int balance24 = totalOutput24 - totalInput24;
            
            TInput24.setText(totalInput24 + "");
            TOutput24.setText(totalOutput24 + "");
            TBalance24.setText(balance24 + "");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
"""
content = re.sub(r'(    private void isTotalFrekuensi\(\)\{\})', hitung24Jam + r'\n\1', content)

# Change BC formula and inject hitung24Jam
isBc_new = """    private void isBc(){
        try {
            BC.setText((Integer.parseInt(JumlahKeluar.getText())-Integer.parseInt(JumlahMasuk.getText()))+"");
            hitung24Jam();
        } catch (Exception e) {
            BC.setText("0");
        }
    }"""
content = re.sub(r'    private void isBc\(\)\{[\s\S]*?\}\n    \}', isBc_new, content)

# Wait, `isBc()` ends with:
#    private void isBc(){
#        try {
#            BC.setText((Integer.parseInt(JumlahMasuk.getText())-Integer.parseInt(JumlahKeluar.getText()))+"");
#        } catch (Exception e) {
#            BC.setText("0");
#        }
#    }

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

