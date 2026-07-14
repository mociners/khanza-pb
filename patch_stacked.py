import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Variables block
var_old = """    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;"""
var_new = """    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;
    private widget.Label jLabelNoJKNJasa;
    private widget.TextBox NoJKNJasa;
    private widget.Label jLabelNoJKNKerja;
    private widget.TextBox NoJKNKerja;"""
code = code.replace(var_old, var_new)

# 2. init block
pattern = re.compile(r'        bgPembiayaan = new javax\.swing\.ButtonGroup\(\);.*?rbUmum\.setSelected\(true\);', re.DOTALL)

init_new = """        bgPembiayaan = new javax.swing.ButtonGroup();
        rbUmum = new widget.RadioButton();
        rbAsuransi = new widget.RadioButton();
        rbJasaRaharja = new widget.RadioButton();
        rbBPJSKerja = new widget.RadioButton();
        rbBPJSKes = new widget.RadioButton();

        rbUmum.setText("Umum");
        rbAsuransi.setText("Asuransi Swasta");
        rbJasaRaharja.setText("Jasa Raharja");
        rbBPJSKerja.setText("BPJS Ketenagakerj.");
        rbBPJSKes.setText("BPJS Kesehatan");
        
        rbUmum.setFocusable(false);
        rbAsuransi.setFocusable(false);
        rbJasaRaharja.setFocusable(false);
        rbBPJSKerja.setFocusable(false);
        rbBPJSKes.setFocusable(false);

        bgPembiayaan.add(rbUmum);
        bgPembiayaan.add(rbAsuransi);
        bgPembiayaan.add(rbJasaRaharja);
        bgPembiayaan.add(rbBPJSKerja);
        bgPembiayaan.add(rbBPJSKes);

        FormInput.add(rbUmum);
        FormInput.add(rbAsuransi);
        FormInput.add(rbJasaRaharja);
        FormInput.add(rbBPJSKerja);
        FormInput.add(rbBPJSKes);

        rbUmum.setBounds(0, 180, 150, 23);
        rbAsuransi.setBounds(0, 240, 150, 23);
        rbJasaRaharja.setBounds(0, 300, 150, 23);
        rbBPJSKerja.setBounds(0, 360, 200, 23);
        rbBPJSKes.setBounds(0, 420, 150, 23);
        
        jLabelNoJKNJasa = new widget.Label();
        jLabelNoJKNJasa.setText("No. JKN / Jasa Raharja :");
        jLabelNoJKNJasa.setBounds(30, 330, 140, 23);
        NoJKNJasa = new widget.TextBox();
        NoJKNJasa.setBounds(175, 330, 180, 23);
        NoJKNJasa.setName("NoJKNJasa"); // NOI18N
        
        jLabelNoJKNKerja = new widget.Label();
        jLabelNoJKNKerja.setText("No. JKN / BPJS Kerja :");
        jLabelNoJKNKerja.setBounds(30, 390, 140, 23);
        NoJKNKerja = new widget.TextBox();
        NoJKNKerja.setBounds(175, 390, 180, 23);
        NoJKNKerja.setName("NoJKNKerja"); // NOI18N

        FormInput.add(jLabelNoJKNJasa);
        FormInput.add(NoJKNJasa);
        FormInput.add(jLabelNoJKNKerja);
        FormInput.add(NoJKNKerja);
        
        java.awt.event.ItemListener rbListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    AlasanUmum.setEditable(rbUmum.isSelected());
                    NamaAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuAsuransi.setEditable(rbAsuransi.isSelected());
                    NoJKNJasa.setEditable(rbJasaRaharja.isSelected());
                    NoJKNKerja.setEditable(rbBPJSKerja.isSelected());
                    NoKartuBPJS.setEditable(rbBPJSKes.isSelected());
                    HakKelas.setEditable(rbBPJSKes.isSelected());
                    
                    if(!rbUmum.isSelected()) AlasanUmum.setText("-");
                    if(!rbAsuransi.isSelected()) {
                        NamaAsuransi.setText("-");
                        NoKartuAsuransi.setText("-");
                    }
                    if(!rbJasaRaharja.isSelected()) NoJKNJasa.setText("-");
                    if(!rbBPJSKerja.isSelected()) NoJKNKerja.setText("-");
                    if(!rbBPJSKes.isSelected()) {
                        NoKartuBPJS.setText("-");
                        HakKelas.setText("-");
                    }
                }
            }
        };
        rbUmum.addItemListener(rbListener);
        rbAsuransi.addItemListener(rbListener);
        rbJasaRaharja.addItemListener(rbListener);
        rbBPJSKerja.addItemListener(rbListener);
        rbBPJSKes.addItemListener(rbListener);
        
        rbUmum.setSelected(true);"""
code = pattern.sub(init_new, code)

# 3. Update SQL logic (BtnSimpan, ganti)
code = code.replace('NoKartuBPJS.getText()', '(rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText()))')
# Fix the one in tbObat.setValueAt
code = code.replace('tbObat.setValueAt((rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), tbObat.getSelectedRow(), 25);', 'tbObat.setValueAt((rbJasaRaharja.isSelected() ? NoJKNJasa.getText() : (rbBPJSKerja.isSelected() ? NoJKNKerja.getText() : NoKartuBPJS.getText())), tbObat.getSelectedRow(), 25);')

# 4. Update getData
getdata_old = """            tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            NoKartuBPJS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());"""
getdata_new = """            tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString());
            String noJknData = tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString();
            NoKartuBPJS.setText(noJknData);
            NoJKNJasa.setText(noJknData);
            NoJKNKerja.setText(noJknData);"""
code = code.replace(getdata_old, getdata_new)

# 5. emptTeks
code = code.replace('NoKartuBPJS.setText("-");', 'NoKartuBPJS.setText("-");\n        NoJKNJasa.setText("-");\n        NoJKNKerja.setText("-");')
code = code.replace('NoKartuBPJS.setText("");', 'NoKartuBPJS.setText("");\n        NoJKNJasa.setText("");\n        NoJKNKerja.setText("");')

# 6. isRawat
israwat_old = 'NoKartuBPJS.setText(rs.getString("no_peserta"));'
israwat_new = """String noPes = rs.getString("no_peserta");
                    NoKartuBPJS.setText(noPes);
                    NoJKNJasa.setText(noPes);
                    NoJKNKerja.setText(noPes);"""
code = code.replace(israwat_old, israwat_new)

# 7. Reposition bounds VERTICALLY
# UMUM
code = code.replace('jLabelAlasanUmum.setBounds(480, 150, 110, 23);', 'jLabelAlasanUmum.setBounds(30, 210, 110, 23);')
code = code.replace('AlasanUmum.setBounds(595, 150, 140, 23);', 'AlasanUmum.setBounds(145, 210, 300, 23);')

# ASURANSI
code = code.replace('jLabelAsuransi.setBounds(0, 210, 100, 23);', 'jLabelAsuransi.setBounds(30, 270, 100, 23);')
code = code.replace('NamaAsuransi.setBounds(105, 210, 220, 23);', 'NamaAsuransi.setBounds(135, 270, 220, 23);')
code = code.replace('jLabelNoKartuAsuransi.setBounds(330, 210, 60, 23);', 'jLabelNoKartuAsuransi.setBounds(370, 270, 60, 23);')
code = code.replace('NoKartuAsuransi.setBounds(395, 210, 140, 23);', 'NoKartuAsuransi.setBounds(435, 270, 160, 23);')

# BPJS KES
code = code.replace('jLabelBPJS.setBounds(0, 240, 130, 23);', 'jLabelBPJS.setBounds(30, 450, 130, 23);')
code = code.replace('jLabelBPJS.setText("No. JKN/Jasa Raharja :");', 'jLabelBPJS.setText("No. JKN / BPJS Kes :");')
code = code.replace('NoKartuBPJS.setBounds(135, 240, 160, 23);', 'NoKartuBPJS.setBounds(165, 450, 180, 23);')
code = code.replace('jLabelHakKelas.setBounds(300, 240, 70, 23);', 'jLabelHakKelas.setBounds(360, 450, 70, 23);')
code = code.replace('HakKelas.setBounds(375, 240, 100, 23);', 'HakKelas.setBounds(435, 450, 120, 23);')

# KELAS
code = code.replace('jLabelPilihanKelas.setBounds(0, 270, 90, 23);', 'jLabelPilihanKelas.setBounds(30, 480, 90, 23);')
code = code.replace('PilihanKelas.setBounds(95, 270, 180, 23);', 'PilihanKelas.setBounds(125, 480, 180, 23);')
code = code.replace('jLabelAlasanNaik.setBounds(280, 270, 110, 23);', 'jLabelAlasanNaik.setBounds(320, 480, 110, 23);')
code = code.replace('AlasanNaik.setBounds(395, 270, 200, 23);', 'AlasanNaik.setBounds(435, 480, 250, 23);')

# 8. PanelInput height
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 350));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 530));')
code = code.replace('PanelInput.setPreferredSize(new Dimension(WIDTH, 350));', 'PanelInput.setPreferredSize(new Dimension(WIDTH, 530));')


with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Applied strict vertical grouping.")
