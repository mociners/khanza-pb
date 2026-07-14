import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Variables block
var_old = """    private widget.Label jLabelJenisPembiayaan;
    private widget.ComboBox JenisPembiayaan;"""
var_new = """    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;"""
code = code.replace(var_old, var_new)

# 2. init block
# match from jLabelJenisPembiayaan = new widget.Label(); to JenisPembiayaan.setSelectedIndex(0);
pattern = re.compile(r'        jLabelJenisPembiayaan = new widget\.Label\(\);.*?JenisPembiayaan\.setSelectedIndex\(0\);', re.DOTALL)

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

        rbUmum.setBounds(0, 180, 65, 23);
        rbAsuransi.setBounds(70, 180, 125, 23);
        rbJasaRaharja.setBounds(200, 180, 105, 23);
        rbBPJSKerja.setBounds(310, 180, 140, 23);
        rbBPJSKes.setBounds(455, 180, 130, 23);
        
        java.awt.event.ItemListener rbListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    AlasanUmum.setEditable(rbUmum.isSelected());
                    NamaAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuAsuransi.setEditable(rbAsuransi.isSelected());
                    NoKartuBPJS.setEditable(rbJasaRaharja.isSelected() || rbBPJSKerja.isSelected() || rbBPJSKes.isSelected());
                    HakKelas.setEditable(rbBPJSKes.isSelected());
                    
                    if(!rbUmum.isSelected()) AlasanUmum.setText("-");
                    if(!rbAsuransi.isSelected()) {
                        NamaAsuransi.setText("-");
                        NoKartuAsuransi.setText("-");
                    }
                    if(!rbJasaRaharja.isSelected() && !rbBPJSKerja.isSelected() && !rbBPJSKes.isSelected()) {
                        NoKartuBPJS.setText("-");
                    }
                    if(!rbBPJSKes.isSelected()) HakKelas.setText("-");
                }
            }
        };
        rbUmum.addItemListener(rbListener);
        rbAsuransi.addItemListener(rbListener);
        rbJasaRaharja.addItemListener(rbListener);
        rbBPJSKerja.addItemListener(rbListener);
        rbBPJSKes.addItemListener(rbListener);
        
        // This will trigger the listener since it starts out unselected
        rbUmum.setSelected(true);"""
code = pattern.sub(init_new, code)

# 3. Update SQL logic
sql_old = 'JenisPembiayaan.getSelectedItem().toString()'
sql_new = 'rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan")))'
code = code.replace(sql_old, sql_new)

# 4. Update getData
getdata_old = """            JenisPembiayaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());"""
getdata_new = """            String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString();
            if(jp.equals("Umum")) rbUmum.setSelected(true);
            else if(jp.equals("Asuransi Swasta")) rbAsuransi.setSelected(true);
            else if(jp.equals("Jasa Raharja")) rbJasaRaharja.setSelected(true);
            else if(jp.equals("BPJS Ketenagakerjaan")) rbBPJSKerja.setSelected(true);
            else rbBPJSKes.setSelected(true);"""
code = code.replace(getdata_old, getdata_new)

# 5. emptTeks
code = code.replace('        JenisPembiayaan.setSelectedIndex(0);', '        rbUmum.setSelected(true);')

# 6. Reposition bounds VERTICALLY (restore to stacked layout)
# From Y=180 for all to staggered.

# Alasan Umum to 150
code = code.replace('jLabelAlasanUmum.setBounds(0, 180, 110, 23);', 'jLabelAlasanUmum.setBounds(480, 150, 110, 23);')
code = code.replace('AlasanUmum.setBounds(115, 180, 300, 23);', 'AlasanUmum.setBounds(595, 150, 140, 23);')

# Asuransi to 210
code = code.replace('jLabelAsuransi.setBounds(0, 180, 100, 23);', 'jLabelAsuransi.setBounds(0, 210, 100, 23);')
code = code.replace('NamaAsuransi.setBounds(105, 180, 250, 23);', 'NamaAsuransi.setBounds(105, 210, 220, 23);')
code = code.replace('jLabelNoKartuAsuransi.setBounds(370, 180, 60, 23);', 'jLabelNoKartuAsuransi.setBounds(330, 210, 60, 23);')
code = code.replace('NoKartuAsuransi.setBounds(435, 180, 200, 23);', 'NoKartuAsuransi.setBounds(395, 210, 140, 23);')

# BPJS to 240
code = code.replace('jLabelBPJS.setBounds(0, 180, 130, 23);', 'jLabelBPJS.setBounds(0, 240, 130, 23);')
code = code.replace('NoKartuBPJS.setBounds(135, 180, 180, 23);', 'NoKartuBPJS.setBounds(135, 240, 160, 23);')
code = code.replace('jLabelHakKelas.setBounds(325, 180, 70, 23);', 'jLabelHakKelas.setBounds(300, 240, 70, 23);')
code = code.replace('HakKelas.setBounds(400, 180, 120, 23);', 'HakKelas.setBounds(375, 240, 100, 23);')

# Kelas to 270
code = code.replace('jLabelPilihanKelas.setBounds(0, 210, 90, 23);', 'jLabelPilihanKelas.setBounds(0, 270, 90, 23);')
code = code.replace('PilihanKelas.setBounds(95, 210, 180, 23);', 'PilihanKelas.setBounds(95, 270, 180, 23);')
code = code.replace('jLabelAlasanNaik.setBounds(280, 210, 110, 23);', 'jLabelAlasanNaik.setBounds(280, 270, 110, 23);')
code = code.replace('AlasanNaik.setBounds(395, 210, 250, 23);', 'AlasanNaik.setBounds(395, 270, 200, 23);')

# 7. Update PanelInput height back to 350
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 255));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 350));')
code = code.replace('PanelInput.setPreferredSize(new Dimension(WIDTH, 255));', 'PanelInput.setPreferredSize(new Dimension(WIDTH, 350));')

# Remove any stray visibility logic that might have been leftover
code = code.replace('jLabelAlasanUmum.setVisible(isUmum);', '')
code = code.replace('AlasanUmum.setVisible(isUmum);', '')
code = code.replace('jLabelAsuransi.setVisible(isAsuransi);', '')
code = code.replace('NamaAsuransi.setVisible(isAsuransi);', '')
code = code.replace('jLabelNoKartuAsuransi.setVisible(isAsuransi);', '')
code = code.replace('NoKartuAsuransi.setVisible(isAsuransi);', '')
code = code.replace('jLabelBPJS.setVisible(isJasaRaharja || isBPJSKes);', '')
code = code.replace('NoKartuBPJS.setVisible(isJasaRaharja || isBPJSKes);', '')
code = code.replace('jLabelHakKelas.setVisible(isBPJSKes);', '')
code = code.replace('HakKelas.setVisible(isBPJSKes);', '')
code = code.replace('jLabelPilihanKelas.setVisible(isBPJSKes);', '')
code = code.replace('PilihanKelas.setVisible(isBPJSKes);', '')
code = code.replace('jLabelAlasanNaik.setVisible(isBPJSKes);', '')
code = code.replace('AlasanNaik.setVisible(isBPJSKes);', '')


with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Reverted back to RadioButtons with stacked inputs.")
