import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Rename Class
content = content.replace('EWSBalanceCairan', 'RMTTVBalanceCairan')
content = content.replace('ews_balance_cairan', 'rm_ttv_balance_cairan')

# 2. Variables
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

# 3. Instantiations
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

# 4. Setup and Add
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

# 5. tabMode Columns
old_tab_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Peroral/NGT","Infus/Parenteral 1","Infus/Parenteral 2","Transfusi","CVC","Epidural","Jumlah Masuk","Fases","Urine",\n            "Muntah/NGT","Drain/Darah","IWL","Jumlah Keluar","Balance Cairan"'
new_tab_cols = '"No.Rawat","No.RM","Nama Pasien","Tanggal Lahir","J.K.","NIP","Nama Petugas","Tanggal","Jam","Nadi","Respirasi","Suhu","Tensi","BB","TB","Diet","Kode Infus","Peroral/NGT","Infus/Parenteral 1","Infus/Parenteral 2","Transfusi","CVC","Epidural","Jumlah Masuk","Fases","Urine",\n            "Muntah/NGT","Drain/Darah","IWL","Jumlah Keluar","Balance Cairan"'
content = content.replace(old_tab_cols, new_tab_cols)

# 6. BtnSimpan Update
old_simpan = 'Sequel.menyimpantf("rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",18,new String[]{'
new_simpan = 'Sequel.menyimpantf("rm_ttv_balance_cairan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",26,new String[]{'
content = content.replace(old_simpan, new_simpan)

old_simpan_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),KdPetugas.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),\n                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText()'
new_simpan_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),\n                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),KdPetugas.getText()'
content = content.replace(old_simpan_args, new_simpan_args)

# 7. BtnEdit Update
old_edit = 'Sequel.mengedittf("rm_ttv_balance_cairan","jam=?","no_rawat=?,tanggal=?,jam=?,nik=?,masuk1=?,masuk2=?,masuk3=?,masuk4=?,masuk5=?,masuk6=?,jumlahmasuk=?,keluar1=?,keluar2=?,keluar3=?,keluar4=?,keluar5=?,jumlahkeluar=?,bc=?",19,new String[]{'
new_edit = 'Sequel.mengedittf("rm_ttv_balance_cairan","tanggal=? and jam=? and no_rawat=?","no_rawat=?,tanggal=?,jam=?,nadi=?,respirasi=?,suhu=?,tensi=?,bb=?,tb=?,diet=?,kode_infus=?,intake_makan=?,intake_minum=?,intake_ngt=?,intake_transfusi=?,intake_infus=?,intake_sisa_infus=?,jumlah_input=?,output_urine=?,output_muntah=?,output_ngt=?,output_iwl=?,output_drain=?,jumlah_output=?,balance=?,nik=?",29,new String[]{'
content = content.replace(old_edit, new_edit)

old_edit_args = 'TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),KdPetugas.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),\n                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()'
new_edit_args = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem().toString(),TNadi.getText(),TRespirasi.getText(),TSuhu.getText(),TTensi.getText(),TBB.getText(),TTB.getText(),TDiet.getText(),TKodeInfus.getText(),Masuk1.getText(),Masuk2.getText(),Masuk3.getText(),Masuk4.getText(),Masuk5.getText(),Masuk6.getText(),JumlahMasuk.getText(),Keluar1.getText(),Keluar2.getText(),\n                    Keluar3.getText(),Keluar4.getText(),Keluar5.getText(),JumlahKeluar.getText(),BC.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()'
content = content.replace(old_edit_args, new_edit_args)

# 8. SQL Select Update (tampil)
old_select = '"select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk=\'L\',\'Laki-Laki\',\'Perempuan\') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nik,pegawai.nama,"+'
new_select = '"select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk=\'L\',\'Laki-Laki\',\'Perempuan\') as jk,pasien.tgl_lahir,rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi,rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb,rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.nik,pegawai.nama,"+'
content = content.replace(old_select, new_select)

# 9. tabMode.addRow (tampil)
old_add_row = '                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nik"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("jam"),\n                        rs.getString("masuk1"),rs.getString("masuk2"),rs.getString("masuk3"),rs.getString("masuk4"),rs.getString("masuk5"),rs.getString("masuk6"),rs.getString("jumlahmasuk"),\n                        rs.getString("keluar1"),rs.getString("keluar2"),rs.getString("keluar3"),rs.getString("keluar4"),rs.getString("keluar5"),rs.getString("jumlahkeluar"),rs.getString("bc")'
new_add_row = '                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nik"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("jam"),rs.getString("nadi"),rs.getString("respirasi"),rs.getString("suhu"),rs.getString("tensi"),rs.getString("bb"),rs.getString("tb"),rs.getString("diet"),rs.getString("kode_infus"),\n                        rs.getString("masuk1"),rs.getString("masuk2"),rs.getString("masuk3"),rs.getString("masuk4"),rs.getString("masuk5"),rs.getString("masuk6"),rs.getString("jumlahmasuk"),\n                        rs.getString("keluar1"),rs.getString("keluar2"),rs.getString("keluar3"),rs.getString("keluar4"),rs.getString("keluar5"),rs.getString("jumlahkeluar"),rs.getString("bc")'
content = content.replace(old_add_row, new_add_row)

# 10. getData()
old_get_data = """            Masuk1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Masuk2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Masuk3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Masuk4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Masuk5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Masuk6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            JumlahMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Keluar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Keluar2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Keluar3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Keluar4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Keluar5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            BC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());"""

new_get_data = """            TNadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TRespirasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            TSuhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TTensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            TBB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TTB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            TDiet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TKodeInfus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            
            Masuk1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Masuk2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Masuk3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Masuk4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Masuk5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Masuk6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            JumlahMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Keluar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Keluar2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Keluar3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Keluar4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Keluar5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            JumlahKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            BC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());"""
content = content.replace(old_get_data, new_get_data)

# Fix BtnHapus Update 
old_hapus = 'Sequel.queryu2tf("delete from rm_ttv_balance_cairan where jam=?",1,new String[]{'
new_hapus = 'Sequel.queryu2tf("delete from rm_ttv_balance_cairan where tanggal=? and jam=? and no_rawat=?",3,new String[]{'
content = content.replace(old_hapus, new_hapus)

old_hapus_args = 'tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()'
new_hapus_args = 'tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()'
content = content.replace(old_hapus_args, new_hapus_args)

# 11. emptTeks()
old_empt = """        Masuk1.setText("0");
        Masuk2.setText("0");
        Masuk3.setText("0");
        Masuk4.setText("0");
        Masuk5.setText("0");
        Masuk6.setText("0");"""
new_empt = """        TNadi.setText("");
        TRespirasi.setText("");
        TSuhu.setText("");
        TTensi.setText("");
        TBB.setText("");
        TTB.setText("");
        TDiet.setText("");
        TKodeInfus.setText("");
        Masuk1.setText("0");
        Masuk2.setText("0");
        Masuk3.setText("0");
        Masuk4.setText("0");
        Masuk5.setText("0");
        Masuk6.setText("0");"""
content = content.replace(old_empt, new_empt)


with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
