import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Replace variables
var_old = """    private javax.swing.ButtonGroup bgPembiayaan;
    private widget.RadioButton rbUmum;
    private widget.RadioButton rbAsuransi;
    private widget.RadioButton rbJasaRaharja;
    private widget.RadioButton rbBPJSKerja;
    private widget.RadioButton rbBPJSKes;"""
var_new = """    private widget.Label jLabelJenisPembiayaan;
    private widget.ComboBox JenisPembiayaan;"""
code = code.replace(var_old, var_new)

# 2. Replace init block
# Use regex to match from bgPembiayaan = new ... to rbUmum.setSelected(true);
pattern = re.compile(r'        bgPembiayaan = new javax\.swing\.ButtonGroup\(\);.*?rbUmum\.setSelected\(true\);', re.DOTALL)

init_new = """        jLabelJenisPembiayaan = new widget.Label();
        jLabelJenisPembiayaan.setText("Jenis Pembiayaan :");
        jLabelJenisPembiayaan.setBounds(460, 150, 120, 23);
        
        JenisPembiayaan = new widget.ComboBox();
        JenisPembiayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "Umum", "Asuransi Swasta", "Jasa Raharja", "BPJS Ketenagakerjaan", "BPJS Kesehatan"
        }));
        JenisPembiayaan.setBounds(585, 150, 160, 23);
        JenisPembiayaan.setName("JenisPembiayaan"); // NOI18N
        
        FormInput.add(jLabelJenisPembiayaan);
        FormInput.add(JenisPembiayaan);
        
        java.awt.event.ItemListener cbListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    String jp = JenisPembiayaan.getSelectedItem().toString();
                    
                    boolean isUmum = jp.equals("Umum");
                    boolean isAsuransi = jp.equals("Asuransi Swasta");
                    boolean isJasaRaharja = jp.equals("Jasa Raharja") || jp.equals("BPJS Ketenagakerjaan");
                    boolean isBPJSKes = jp.equals("BPJS Kesehatan");
                    
                    jLabelAlasanUmum.setVisible(isUmum);
                    AlasanUmum.setVisible(isUmum);
                    
                    jLabelAsuransi.setVisible(isAsuransi);
                    NamaAsuransi.setVisible(isAsuransi);
                    jLabelNoKartuAsuransi.setVisible(isAsuransi);
                    NoKartuAsuransi.setVisible(isAsuransi);
                    
                    jLabelBPJS.setVisible(isJasaRaharja || isBPJSKes);
                    NoKartuBPJS.setVisible(isJasaRaharja || isBPJSKes);
                    
                    jLabelHakKelas.setVisible(isBPJSKes);
                    HakKelas.setVisible(isBPJSKes);
                    
                    jLabelPilihanKelas.setVisible(isBPJSKes);
                    PilihanKelas.setVisible(isBPJSKes);
                    jLabelAlasanNaik.setVisible(isBPJSKes);
                    AlasanNaik.setVisible(isBPJSKes);
                    
                    if(!isUmum) AlasanUmum.setText("-");
                    if(!isAsuransi) { NamaAsuransi.setText("-"); NoKartuAsuransi.setText("-"); }
                    if(!isJasaRaharja && !isBPJSKes) NoKartuBPJS.setText("-");
                    if(!isBPJSKes) { HakKelas.setText("-"); PilihanKelas.setSelectedIndex(0); AlasanNaik.setText("-"); }
                }
            }
        };
        JenisPembiayaan.addItemListener(cbListener);
        // trigger the listener manually once to hide fields on load
        JenisPembiayaan.setSelectedIndex(0);"""
code = pattern.sub(init_new, code)

# 3. Update SQL logic
sql_val_old = 'rbUmum.isSelected() ? "Umum" : (rbAsuransi.isSelected() ? "Asuransi Swasta" : (rbJasaRaharja.isSelected() ? "Jasa Raharja" : (rbBPJSKerja.isSelected() ? "BPJS Ketenagakerjaan" : "BPJS Kesehatan")))'
sql_val_new = 'JenisPembiayaan.getSelectedItem().toString()'
code = code.replace(sql_val_old, sql_val_new)

# 4. Update getData
getdata_old = """            String jp = tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString();
            if(jp.equals("Umum")) rbUmum.setSelected(true);
            else if(jp.equals("Asuransi Swasta")) rbAsuransi.setSelected(true);
            else if(jp.equals("Jasa Raharja")) rbJasaRaharja.setSelected(true);
            else if(jp.equals("BPJS Ketenagakerjaan")) rbBPJSKerja.setSelected(true);
            else rbBPJSKes.setSelected(true);"""
getdata_new = """            JenisPembiayaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());"""
code = code.replace(getdata_old, getdata_new)

# 5. emptTeks
code = code.replace('Privasi2.setSelectedIndex(0);', 'Privasi2.setSelectedIndex(0);\n        JenisPembiayaan.setSelectedIndex(0);')

# 6. Reposition bounds
code = code.replace('jLabelAlasanUmum.setBounds(480, 150, 110, 23);', 'jLabelAlasanUmum.setBounds(0, 180, 110, 23);')
code = code.replace('AlasanUmum.setBounds(595, 150, 140, 23);', 'AlasanUmum.setBounds(115, 180, 300, 23);')

code = code.replace('jLabelAsuransi.setBounds(0, 210, 100, 23);', 'jLabelAsuransi.setBounds(0, 180, 100, 23);')
code = code.replace('NamaAsuransi.setBounds(105, 210, 220, 23);', 'NamaAsuransi.setBounds(105, 180, 250, 23);')
code = code.replace('jLabelNoKartuAsuransi.setBounds(330, 210, 60, 23);', 'jLabelNoKartuAsuransi.setBounds(370, 180, 60, 23);')
code = code.replace('NoKartuAsuransi.setBounds(395, 210, 140, 23);', 'NoKartuAsuransi.setBounds(435, 180, 200, 23);')

code = code.replace('jLabelBPJS.setBounds(0, 240, 130, 23);', 'jLabelBPJS.setBounds(0, 180, 130, 23);')
code = code.replace('NoKartuBPJS.setBounds(135, 240, 160, 23);', 'NoKartuBPJS.setBounds(135, 180, 180, 23);')
code = code.replace('jLabelHakKelas.setBounds(300, 240, 70, 23);', 'jLabelHakKelas.setBounds(325, 180, 70, 23);')
code = code.replace('HakKelas.setBounds(375, 240, 100, 23);', 'HakKelas.setBounds(400, 180, 120, 23);')

code = code.replace('jLabelPilihanKelas.setBounds(0, 270, 90, 23);', 'jLabelPilihanKelas.setBounds(0, 210, 90, 23);')
code = code.replace('PilihanKelas.setBounds(95, 270, 180, 23);', 'PilihanKelas.setBounds(95, 210, 180, 23);')
code = code.replace('jLabelAlasanNaik.setBounds(280, 270, 110, 23);', 'jLabelAlasanNaik.setBounds(280, 210, 110, 23);')
code = code.replace('AlasanNaik.setBounds(395, 270, 200, 23);', 'AlasanNaik.setBounds(395, 210, 250, 23);')

# 7. Update PanelInput height
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 235));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 255));')
code = code.replace('PanelInput.setPreferredSize(new Dimension(WIDTH, 350));', 'PanelInput.setPreferredSize(new Dimension(WIDTH, 255));')
code = code.replace('PanelInput.setPreferredSize(new java.awt.Dimension(192, 350));', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 255));')

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Dynamic UI logic applied.")
