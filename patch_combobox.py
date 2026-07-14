import os
import re

def patch_java():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        java = f.read()

    # 1. Update model
    old_model = 'BertindakAtas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Saudara", "Keponakan" }));'
    new_model = 'BertindakAtas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Ibu", "Saudara", "Keponakan", "Pasien", "Lainnya" }));'
    java = java.replace(old_model, new_model)

    # 2. Update bounds and add KetBertindak GUI
    old_bounds = 'BertindakAtas.setBounds(865, 120, 115, 23);'
    new_bounds = """BertindakAtas.setBounds(865, 120, 75, 23);
        KetBertindak = new widget.TextBox();
        KetBertindak.setName("KetBertindak");
        KetBertindak.setEnabled(false);
        KetBertindak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, BertindakAtas, Alamat);
            }
        });
        FormInput.add(KetBertindak);
        KetBertindak.setBounds(945, 120, 100, 23);

        BertindakAtas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if(BertindakAtas.getSelectedItem().toString().equals("Lainnya")) {
                    KetBertindak.setEnabled(true);
                    KetBertindak.requestFocus();
                } else {
                    KetBertindak.setEnabled(false);
                    KetBertindak.setText("");
                }
            }
        });"""
    java = java.replace(old_bounds, new_bounds)

    # 3. Add variable declaration
    if "private widget.TextBox KetBertindak;" not in java:
        java = java.replace(
            "private widget.ComboBox BertindakAtas;",
            "private widget.ComboBox BertindakAtas;\n    private widget.TextBox KetBertindak;"
        )

    # 4. Modify simpan() & ganti() SQL
    # In simpan(): BertindakAtas.getSelectedItem().toString()
    # Replace it with: (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString())
    # BUT wait! It occurs in many places.
    # We should search and replace in simpan/ganti, specifically for the SQL queries.
    
    # In simpan():
    # JKPJ.getSelectedItem().toString().substring(0, 1), BertindakAtas.getSelectedItem().toString(), NoTelp.getText(), NIP.getText(),
    
    simpan_old = 'JKPJ.getSelectedItem().toString().substring(0, 1), BertindakAtas.getSelectedItem().toString(), NoTelp.getText(), NIP.getText(),'
    simpan_new = 'JKPJ.getSelectedItem().toString().substring(0, 1), (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), NoTelp.getText(), NIP.getText(),'
    java = java.replace(simpan_old, simpan_new)

    # In ganti():
    # NoTelp.getText(), BertindakAtas.getSelectedItem().toString(), 
    ganti_old = 'NoTelp.getText(), BertindakAtas.getSelectedItem().toString(), '
    ganti_new = 'NoTelp.getText(), (BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), '
    java = java.replace(ganti_old, ganti_new)

    # In tbObatMouseClicked():
    # BertindakAtas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
    # We need to change it to check if it's one of the options. If not, set to "Lainnya" and KetBertindak.setText()
    
    tbclick_old = 'BertindakAtas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());'
    tbclick_new = """String bertindak = tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString();
            boolean found = false;
            for(int i=0; i<BertindakAtas.getItemCount(); i++){
                if(BertindakAtas.getItemAt(i).toString().equals(bertindak)){
                    found = true;
                    break;
                }
            }
            if(found){
                BertindakAtas.setSelectedItem(bertindak);
                KetBertindak.setText("");
                KetBertindak.setEnabled(false);
            } else {
                BertindakAtas.setSelectedItem("Lainnya");
                KetBertindak.setText(bertindak);
                KetBertindak.setEnabled(true);
            }"""
    java = java.replace(tbclick_old, tbclick_new)
    
    # In emptTeks():
    # BertindakAtas.setSelectedIndex(0);
    empt_old = 'BertindakAtas.setSelectedIndex(0);'
    empt_new = 'BertindakAtas.setSelectedIndex(0);\n        KetBertindak.setText("");\n        KetBertindak.setEnabled(false);'
    java = java.replace(empt_old, empt_new)
    
    # In ganti() tbObat.setValueAt
    # tbObat.setValueAt(BertindakAtas.getSelectedItem().toString(), tbObat.getSelectedRow(), 15);
    val_old = 'tbObat.setValueAt(BertindakAtas.getSelectedItem().toString(), tbObat.getSelectedRow(), 15);'
    val_new = 'tbObat.setValueAt((BertindakAtas.getSelectedItem().toString().equals("Lainnya") ? KetBertindak.getText() : BertindakAtas.getSelectedItem().toString()), tbObat.getSelectedRow(), 15);'
    java = java.replace(val_old, val_new)

    with open(path, "w") as f:
        f.write(java)

def patch_form():
    path = "src/surat/SuratPersetujuanUmum.form"
    with open(path, "r") as f:
        form = f.read()

    # 1. Update combobox
    form_cb_old = """                  <StringArray count="6">
                    <StringItem index="0" value="Suami"/>
                    <StringItem index="1" value="Istri"/>
                    <StringItem index="2" value="Anak"/>
                    <StringItem index="3" value="Ayah"/>
                    <StringItem index="4" value="Saudara"/>
                    <StringItem index="5" value="Keponakan"/>
                  </StringArray>"""
    form_cb_new = """                  <StringArray count="9">
                    <StringItem index="0" value="Suami"/>
                    <StringItem index="1" value="Istri"/>
                    <StringItem index="2" value="Anak"/>
                    <StringItem index="3" value="Ayah"/>
                    <StringItem index="4" value="Ibu"/>
                    <StringItem index="5" value="Saudara"/>
                    <StringItem index="6" value="Keponakan"/>
                    <StringItem index="7" value="Pasien"/>
                    <StringItem index="8" value="Lainnya"/>
                  </StringArray>"""
    form = form.replace(form_cb_old, form_cb_new)
    
    # 2. Add KetBertindak
    # First update BertindakAtas width
    form = form.replace('<AbsoluteConstraints x="870" y="120" width="110" height="23"/>', '<AbsoluteConstraints x="865" y="120" width="75" height="23"/>')
    form = form.replace('<AbsoluteConstraints x="865" y="120" width="115" height="23"/>', '<AbsoluteConstraints x="865" y="120" width="75" height="23"/>')
    
    # Insert KetBertindak XML after BertindakAtas XML block
    bertindak_xml_end = """                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="865" y="120" width="75" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>"""
                
    ket_xml = """
                <Component class="widget.TextBox" name="KetBertindak">
                  <Properties>
                    <Property name="name" type="java.lang.String" value="KetBertindak" noResource="true"/>
                  </Properties>
                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="945" y="120" width="100" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>"""
                
    form = form.replace(bertindak_xml_end, bertindak_xml_end + ket_xml)
    
    with open(path, "w") as f:
        f.write(form)

if __name__ == "__main__":
    patch_java()
    patch_form()
    print("Patched ComboBox Logic")
