import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Variables
vars = """
    private widget.Label LNadi;
    private widget.TextBox TNadi;
    private widget.Label LRespirasi;
    private widget.TextBox TRespirasi;
    private widget.Label LSuhu;
    private widget.TextBox TSuhu;
    private widget.Label LTensi;
    private widget.TextBox TTensi;
    private widget.Label LBB;
    private widget.TextBox TBB;
    private widget.Label LTB;
    private widget.TextBox TTB;
    private widget.Label LDiet;
    private widget.TextBox TDiet;
    private widget.Label LKodeInfus;
    private widget.TextBox TKodeInfus;
"""
content = re.sub(r'(\}\s*)$', vars + r'\1', content)

# 2. Instantiations
insts = """
        LNadi = new widget.Label();
        TNadi = new widget.TextBox();
        LRespirasi = new widget.Label();
        TRespirasi = new widget.TextBox();
        LSuhu = new widget.Label();
        TSuhu = new widget.TextBox();
        LTensi = new widget.Label();
        TTensi = new widget.TextBox();
        LBB = new widget.Label();
        TBB = new widget.TextBox();
        LTB = new widget.Label();
        TTB = new widget.TextBox();
        LDiet = new widget.Label();
        TDiet = new widget.TextBox();
        LKodeInfus = new widget.Label();
        TKodeInfus = new widget.TextBox();
"""
content = content.replace("LoadHTML = new widget.editorpane();", insts + "\n        LoadHTML = new widget.editorpane();")

# 3. Setup and Add
setup = """
        LNadi.setText("Nadi :");
        LNadi.setBounds(450, 90, 90, 23);
        FormInput.add(LNadi);
        TNadi.setBounds(550, 90, 100, 23);
        FormInput.add(TNadi);
        
        LRespirasi.setText("Respirasi :");
        LRespirasi.setBounds(450, 120, 90, 23);
        FormInput.add(LRespirasi);
        TRespirasi.setBounds(550, 120, 100, 23);
        FormInput.add(TRespirasi);
        
        LSuhu.setText("Suhu :");
        LSuhu.setBounds(450, 150, 90, 23);
        FormInput.add(LSuhu);
        TSuhu.setBounds(550, 150, 100, 23);
        FormInput.add(TSuhu);
        
        LTensi.setText("Tensi :");
        LTensi.setBounds(450, 180, 90, 23);
        FormInput.add(LTensi);
        TTensi.setBounds(550, 180, 100, 23);
        FormInput.add(TTensi);
        
        LBB.setText("BB :");
        LBB.setBounds(450, 210, 90, 23);
        FormInput.add(LBB);
        TBB.setBounds(550, 210, 100, 23);
        FormInput.add(TBB);
        
        LTB.setText("TB :");
        LTB.setBounds(450, 240, 90, 23);
        FormInput.add(LTB);
        TTB.setBounds(550, 240, 100, 23);
        FormInput.add(TTB);
        
        LDiet.setText("Diet :");
        LDiet.setBounds(450, 270, 90, 23);
        FormInput.add(LDiet);
        TDiet.setBounds(550, 270, 200, 23);
        FormInput.add(TDiet);
        
        LKodeInfus.setText("Kode Infus :");
        LKodeInfus.setBounds(450, 300, 90, 23);
        FormInput.add(LKodeInfus);
        TKodeInfus.setBounds(550, 300, 200, 23);
        FormInput.add(TKodeInfus);
"""
content = content.replace("FormInput.setLayout(null);", "FormInput.setLayout(null);\n" + setup)

# 4. Update SQL BtnSimpan
old_simpan = 'Sequel.menyimpantf("rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",18,new String[]{'
new_simpan = 'Sequel.menyimpantf("rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",26,new String[]{'
content = content.replace(old_simpan, new_simpan)

old_simpan_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),KdPetugas.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText()'
new_simpan_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),KdPetugas.getText()'
content = content.replace(old_simpan_args, new_simpan_args)

# 5. Update SQL BtnEdit
old_edit = 'Sequel.mengedittf("rm_ttv_balance_cairan","jam=?","no_rawat=?,tanggal=?,jam=?,nik=?,masuk1=?,masuk2=?,masuk3=?,masuk4=?,masuk5=?,masuk6=?,jumlahmasuk=?,keluar1=?,keluar2=?,keluar3=?,keluar4=?,keluar5=?,jumlahkeluar=?,bc=?",19,new String[]{'
new_edit = 'Sequel.mengedittf("rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,balance=?,nik=?",29,new String[]{'
content = content.replace(old_edit, new_edit)

old_edit_args1 = 'TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),'
old_edit_args2 = 'Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()'

new_edit_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),KdPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),TNoRw.getText()'

# We need to replace the multi-line edit args
content = re.sub(r'TNoRw\.getText\(\).*?tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),8\)\.toString\(\)', new_edit_args, content, flags=re.DOTALL)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

