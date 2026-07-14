import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Update tabMode headers
tabmode_old = """        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Persetujuan", "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "J.K.", "Tgl.Lahir", 
            "Tanggal", "Pengobatan Kepada", "Nilai Kepercayaan", "Nama Penanggung Jawab", 
            "Umur P.J.", "Nomor KTP P.J.", "J.K. P.J.", "Nomor Telp/HP", "Bertindak Untuk", 
            "Alamat P.J.", "Pekerjaan P.J.", "NIP", "Nama Petugas", 
            "Privasi Akses", "Privasi Khusus", "Jenis Pembiayaan", "Alasan Umum", "Asuransi", "No.Asuransi", 
            "No.BPJS", "Hak Kelas", "Pilih Kelas", "Alasan Naik Kelas"
        })"""
tabmode_new = """        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Persetujuan", "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "J.K.", "Tgl.Lahir", 
            "Tanggal", "Pengobatan Kepada", "Nilai Kepercayaan", "Nama Penanggung Jawab", 
            "Umur P.J.", "Nomor KTP P.J.", "J.K. P.J.", "Nomor Telp/HP", "Bertindak Untuk", 
            "Alamat P.J.", "Pekerjaan P.J.", "NIP", "Nama Petugas", 
            "Privasi Akses", "Privasi Khusus", "Jenis Pembiayaan", 
            "Alasan Tolak BPJS", "Alasan Tolak BPJS Kerja", "Alasan Tolak Jasa Raharja", 
            "Asuransi", "No.Asuransi", "No.BPJS", "Hak Kelas", "Pilih Kelas", "Alasan Naik Kelas"
        })"""
code = code.replace(tabmode_old, tabmode_new)
code = code.replace("for (i = 0; i < 30; i++)", "for (i = 0; i < 32; i++)")

# 2. Add Variables
var_old = """    private widget.Label jLabelAlasanUmum;"""
var_new = """    private widget.Label jLabelAlasanUmum;
    private widget.Label jLabelAlasanUmumKerja;
    private widget.TextBox AlasanUmumKerja;
    private widget.Label jLabelAlasanUmumJasa;
    private widget.TextBox AlasanUmumJasa;"""
code = code.replace(var_old, var_new)

# 3. Add to initComponents (before jLabelAlasanUmum bounds)
init_comp_old = """        jLabelAlasanUmum.setText("Alasan Tolak BPJS :");
        jLabelAlasanUmum.setBounds(30, 210, 110, 23);"""
init_comp_new = """        jLabelAlasanUmum.setText("Alasan Tolak BPJS Kes:");
        jLabelAlasanUmum.setBounds(30, 210, 140, 23);
        
        jLabelAlasanUmumKerja = new widget.Label();
        jLabelAlasanUmumKerja.setText("Alasan Tolak BPJS Kerja:");
        jLabelAlasanUmumKerja.setBounds(30, 240, 140, 23);
        AlasanUmumKerja = new widget.TextBox();
        AlasanUmumKerja.setBounds(175, 240, 300, 23);
        
        jLabelAlasanUmumJasa = new widget.Label();
        jLabelAlasanUmumJasa.setText("Alasan Tolak Jasa Raharja:");
        jLabelAlasanUmumJasa.setBounds(30, 270, 140, 23);
        AlasanUmumJasa = new widget.TextBox();
        AlasanUmumJasa.setBounds(175, 270, 300, 23);
        
        FormInput.add(jLabelAlasanUmumKerja);
        FormInput.add(AlasanUmumKerja);
        FormInput.add(jLabelAlasanUmumJasa);
        FormInput.add(AlasanUmumJasa);"""
code = code.replace(init_comp_old, init_comp_new)

code = code.replace('AlasanUmum.setBounds(145, 210, 300, 23);', 'AlasanUmum.setBounds(175, 210, 300, 23);')

# 4. ItemListener for the new components
list_old = """                    AlasanUmum.setEditable(rbUmum.isSelected());"""
list_new = """                    AlasanUmum.setEditable(rbUmum.isSelected());
                    AlasanUmumKerja.setEditable(rbUmum.isSelected());
                    AlasanUmumJasa.setEditable(rbUmum.isSelected());"""
code = code.replace(list_old, list_new)

clear_old = """                    if(!rbUmum.isSelected()) AlasanUmum.setText("-");"""
clear_new = """                    if(!rbUmum.isSelected()) {
                        AlasanUmum.setText("-");
                        AlasanUmumKerja.setText("-");
                        AlasanUmumJasa.setText("-");
                    }"""
code = code.replace(clear_old, clear_new)

# 5. Shift all components below it by 60px (Y+60)
code = code.replace('jLabelAsuransi.setBounds(30, 270, 100, 23);', 'jLabelAsuransi.setBounds(30, 330, 100, 23);')
code = code.replace('NamaAsuransi.setBounds(135, 270, 220, 23);', 'NamaAsuransi.setBounds(135, 330, 220, 23);')
code = code.replace('jLabelNoKartuAsuransi.setBounds(370, 270, 60, 23);', 'jLabelNoKartuAsuransi.setBounds(370, 330, 60, 23);')
code = code.replace('NoKartuAsuransi.setBounds(435, 270, 160, 23);', 'NoKartuAsuransi.setBounds(435, 330, 160, 23);')

code = code.replace('jLabelNoJKNJasa.setBounds(30, 330, 140, 23);', 'jLabelNoJKNJasa.setBounds(30, 390, 140, 23);')
code = code.replace('NoJKNJasa.setBounds(175, 330, 180, 23);', 'NoJKNJasa.setBounds(175, 390, 180, 23);')

code = code.replace('jLabelNoJKNKerja.setBounds(30, 390, 140, 23);', 'jLabelNoJKNKerja.setBounds(30, 450, 140, 23);')
code = code.replace('NoJKNKerja.setBounds(175, 390, 180, 23);', 'NoJKNKerja.setBounds(175, 450, 180, 23);')

code = code.replace('jLabelBPJS.setBounds(30, 450, 130, 23);', 'jLabelBPJS.setBounds(30, 510, 130, 23);')
code = code.replace('NoKartuBPJS.setBounds(165, 450, 180, 23);', 'NoKartuBPJS.setBounds(165, 510, 180, 23);')
code = code.replace('jLabelHakKelas.setBounds(360, 450, 70, 23);', 'jLabelHakKelas.setBounds(360, 510, 70, 23);')
code = code.replace('HakKelas.setBounds(435, 450, 120, 23);', 'HakKelas.setBounds(435, 510, 120, 23);')

code = code.replace('jLabelPilihanKelas.setBounds(30, 480, 90, 23);', 'jLabelPilihanKelas.setBounds(30, 540, 90, 23);')
code = code.replace('PilihanKelas.setBounds(125, 480, 180, 23);', 'PilihanKelas.setBounds(125, 540, 180, 23);')
code = code.replace('jLabelAlasanNaik.setBounds(320, 480, 110, 23);', 'jLabelAlasanNaik.setBounds(320, 540, 110, 23);')
code = code.replace('AlasanNaik.setBounds(435, 480, 250, 23);', 'AlasanNaik.setBounds(435, 540, 250, 23);')

code = code.replace('rbAsuransi.setBounds(0, 240, 150, 23);', 'rbAsuransi.setBounds(0, 300, 150, 23);')
code = code.replace('rbJasaRaharja.setBounds(0, 300, 150, 23);', 'rbJasaRaharja.setBounds(0, 360, 150, 23);')
code = code.replace('rbBPJSKerja.setBounds(0, 360, 200, 23);', 'rbBPJSKerja.setBounds(0, 420, 200, 23);')
code = code.replace('rbBPJSKes.setBounds(0, 420, 150, 23);', 'rbBPJSKes.setBounds(0, 480, 150, 23);')

code = code.replace('new java.awt.Dimension(192, 530)', 'new java.awt.Dimension(192, 590)')
code = code.replace('new Dimension(WIDTH, 530)', 'new Dimension(WIDTH, 590)')

# 6. SQL Operations
# Insert
q_in_old = '"surat_persetujuan_umum (no_surat, no_rawat, tanggal, pengobatan_kepada, nilai_kepercayaan, nama_pj, umur_pj, no_ktppj, jkpj, bertindak_atas, no_telp, nip, alamat_pj, pekerjaan_pj, privasi_akses, privasi_khusus, jenis_pembiayaan, alasan_tolak_bpjs, asuransi_swasta, no_kartu_asuransi, no_jkn_jasa_raharja, hak_kelas, pilihan_kamar, alasan_naik_kelas)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"'
q_in_new = '"surat_persetujuan_umum (no_surat, no_rawat, tanggal, pengobatan_kepada, nilai_kepercayaan, nama_pj, umur_pj, no_ktppj, jkpj, bertindak_atas, no_telp, nip, alamat_pj, pekerjaan_pj, privasi_akses, privasi_khusus, jenis_pembiayaan, alasan_tolak_bpjs, alasan_tolak_bpjs_kerja, alasan_tolak_jasa_raharja, asuransi_swasta, no_kartu_asuransi, no_jkn_jasa_raharja, hak_kelas, pilihan_kamar, alasan_naik_kelas)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"'
code = code.replace(q_in_old, q_in_new)

# Update
q_up_old = 'privasi_khusus=?,jenis_pembiayaan=?,alasan_tolak_bpjs=?,asuransi_swasta=?'
q_up_new = 'privasi_khusus=?,jenis_pembiayaan=?,alasan_tolak_bpjs=?,alasan_tolak_bpjs_kerja=?,alasan_tolak_jasa_raharja=?,asuransi_swasta=?'
code = code.replace(q_up_old, q_up_new)

# Select (tampil)
code = code.replace('surat_persetujuan_umum.alasan_tolak_bpjs,surat_persetujuan_umum.asuransi_swasta', 'surat_persetujuan_umum.alasan_tolak_bpjs,surat_persetujuan_umum.alasan_tolak_bpjs_kerja,surat_persetujuan_umum.alasan_tolak_jasa_raharja,surat_persetujuan_umum.asuransi_swasta')
# Check line 1317:
code = code.replace('surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.alasan_tolak_bpjs, "', 'surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.alasan_tolak_bpjs, surat_persetujuan_umum.alasan_tolak_bpjs_kerja, surat_persetujuan_umum.alasan_tolak_jasa_raharja, "')


# Array elements in insert/update
p_old = 'AlasanUmum.getText(), NamaAsuransi.getText()'
p_new = 'AlasanUmum.getText(), AlasanUmumKerja.getText(), AlasanUmumJasa.getText(), NamaAsuransi.getText()'
code = code.replace(p_old, p_new)
code = code.replace('AlasanUmum.getText(), \n                    NamaAsuransi.getText()', 'AlasanUmum.getText(), AlasanUmumKerja.getText(), AlasanUmumJasa.getText(), \n                    NamaAsuransi.getText()')

# 7. getData
d_old = """            tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString();
            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            String noJknData = tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString();
            NoKartuBPJS.setText(noJknData);"""
d_new = """            tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString();
            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            AlasanUmumKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            AlasanUmumJasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            String noJknData = tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString();
            NoKartuBPJS.setText(noJknData);"""
# wait, wait! The indexes in getData are shifted.
# Before: 
# 23 = AlasanUmum? No, the new columns in tbl:
# 22 = Jenis Pembiayaan
# 23 = Alasan Tolak BPJS
# 24 = Alasan Tolak BPJS Kerja
# 25 = Alasan Tolak Jasa Raharja
# 26 = Asuransi
# 27 = No.Asuransi
# 28 = No.BPJS
# 29 = Hak Kelas
# 30 = Pilih Kelas
# 31 = Alasan Naik Kelas
# I'll just use regex or a robust replace block.

# Let's replace the ENTIRE block for tbObat.setValueAt and getData.
# It's safer to just do a precise replace for getData.
# Let's use `re` to replace the index numbers.

def replace_getdata(match):
    return """
            AlasanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            AlasanUmumKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            AlasanUmumJasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            NamaAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            NoKartuAsuransi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            String noJknData = tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString();
            NoKartuBPJS.setText(noJknData);
            NoJKNJasa.setText(noJknData);
            NoJKNKerja.setText(noJknData);
            HakKelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            PilihanKelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
"""

code = re.sub(r'AlasanUmum\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\), 23\).*?AlasanNaik\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\), 29\)\.toString\(\)\);', replace_getdata, code, flags=re.DOTALL)


# For the setValues inside tampil():
# Valid.tabelKosong(tabMode); ... tabMode.addRow(...)
code = re.sub(r'tabMode\.addRow\(new Object\[\]\{\s*rs\.getString\("no_surat"\).*?rs\.getString\("alasan_naik_kelas"\)\s*\}\);', 
              r"""tabMode.addRow(new Object[]{
                        rs.getString("no_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("umurdaftar") + " " + rs.getString("sttsumur"), rs.getString("jk"), rs.getString("tgl_lahir"),
                        rs.getString("tanggal"), rs.getString("pengobatan_kepada"), rs.getString("nilai_kepercayaan"), rs.getString("nama_pj"),
                        rs.getString("umur_pj"), rs.getString("no_ktppj"), rs.getString("jkpj"), rs.getString("no_telp"), rs.getString("bertindak_atas"),
                        rs.getString("alamat_pj"), rs.getString("pekerjaan_pj"), rs.getString("nip"), rs.getString("nama"),
                        rs.getString("privasi_akses"), rs.getString("privasi_khusus"), rs.getString("jenis_pembiayaan"), 
                        rs.getString("alasan_tolak_bpjs"), rs.getString("alasan_tolak_bpjs_kerja"), rs.getString("alasan_tolak_jasa_raharja"), 
                        rs.getString("asuransi_swasta"), rs.getString("no_kartu_asuransi"), rs.getString("no_jkn_jasa_raharja"), 
                        rs.getString("hak_kelas"), rs.getString("pilihan_kamar"), rs.getString("alasan_naik_kelas")
                    });""", code, flags=re.DOTALL)


with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Java File Patched for 3 Umum Inputs!")
