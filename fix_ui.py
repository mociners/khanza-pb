import re

with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
    code = f.read()

# 1. Fix AlasanNaik bounds
code = code.replace('jLabelAlasanNaik.setBounds(280, 240, 110, 23);', 'jLabelAlasanNaik.setBounds(280, 270, 110, 23);')
code = code.replace('AlasanNaik.setBounds(395, 240, 200, 23);', 'AlasanNaik.setBounds(395, 270, 200, 23);')

# 2. Inject RadioButtons
target = 'AlasanNaik.setBounds(395, 270, 200, 23);'

injection = """
        bgPembiayaan = new javax.swing.ButtonGroup();
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
        
        rbUmum.setSelected(true);"""

code = code.replace(target, target + "\n" + injection)

with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
    f.write(code)

print("Injected RadioButtons correctly.")
