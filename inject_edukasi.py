import re

def patch_form():
    with open("src/surat/SuratPersetujuanUmum.form", "r") as f:
        form = f.read()

    # 1. Increase PanelInput preferred size
    form = form.replace('<Dimension value="[192, 175]"/>', '<Dimension value="[192, 205]"/>')
    # 2. Increase FormInput preferred size
    form = form.replace('<Dimension value="[100, 165]"/>', '<Dimension value="[100, 195]"/>')

    # 3. Add components to FormInput
    inject_xml = """
                <Component class="widget.Label" name="jLabelEdukasiPJ">
                  <Properties>
                    <Property name="text" type="java.lang.String" value="Edukasi Keluarga :"/>
                    <Property name="name" type="java.lang.String" value="jLabelEdukasiPJ" noResource="true"/>
                  </Properties>
                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="0" y="150" width="105" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>
                <Component class="widget.TextBox" name="EdukasiPJ">
                  <Properties>
                    <Property name="name" type="java.lang.String" value="EdukasiPJ" noResource="true"/>
                  </Properties>
                  <Events>
                    <EventHandler event="keyPressed" listener="java.awt.event.KeyListener" parameters="java.awt.event.KeyEvent" handler="EdukasiPJKeyPressed"/>
                  </Events>
                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="110" y="150" width="370" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>
                <Component class="widget.Label" name="jLabelEdukasiRS">
                  <Properties>
                    <Property name="text" type="java.lang.String" value="Edukasi RS :"/>
                    <Property name="name" type="java.lang.String" value="jLabelEdukasiRS" noResource="true"/>
                  </Properties>
                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="490" y="150" width="80" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>
                <Component class="widget.TextBox" name="EdukasiRS">
                  <Properties>
                    <Property name="name" type="java.lang.String" value="EdukasiRS" noResource="true"/>
                  </Properties>
                  <Events>
                    <EventHandler event="keyPressed" listener="java.awt.event.KeyListener" parameters="java.awt.event.KeyEvent" handler="EdukasiRSKeyPressed"/>
                  </Events>
                  <Constraints>
                    <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
                      <AbsoluteConstraints x="580" y="150" width="370" height="23"/>
                    </Constraint>
                  </Constraints>
                </Component>
"""
    pattern = r'(<Component class="widget.Label" name="jLabel23">.*?</Component>)'
    form = re.sub(pattern, r'\1' + inject_xml, form, flags=re.DOTALL)

    with open("src/surat/SuratPersetujuanUmum.form", "w") as f:
        f.write(form)

def patch_java():
    with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
        java = f.read()

    # Add GUI Declarations
    dec = """
    private widget.TextBox EdukasiPJ;
    private widget.TextBox EdukasiRS;
    private widget.Label jLabelEdukasiPJ;
    private widget.Label jLabelEdukasiRS;
"""
    java = java.replace('private widget.TextBox Pekerjaan;', 'private widget.TextBox Pekerjaan;' + dec)

    # Add GUI initialization inside initComponents()
    init = """
        EdukasiPJ = new widget.TextBox();
        EdukasiRS = new widget.TextBox();
        jLabelEdukasiPJ = new widget.Label();
        jLabelEdukasiRS = new widget.Label();
        
        jLabelEdukasiPJ.setText("Edukasi Keluarga :");
        jLabelEdukasiPJ.setName("jLabelEdukasiPJ");
        FormInput.add(jLabelEdukasiPJ);
        jLabelEdukasiPJ.setBounds(0, 150, 105, 23);

        EdukasiPJ.setName("EdukasiPJ");
        EdukasiPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiPJKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiPJ);
        EdukasiPJ.setBounds(110, 150, 370, 23);

        jLabelEdukasiRS.setText("Edukasi RS :");
        jLabelEdukasiRS.setName("jLabelEdukasiRS");
        FormInput.add(jLabelEdukasiRS);
        jLabelEdukasiRS.setBounds(490, 150, 80, 23);

        EdukasiRS.setName("EdukasiRS");
        EdukasiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiRSKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiRS);
        EdukasiRS.setBounds(580, 150, 370, 23);
"""
    java = java.replace('FormInput.add(jLabel23);', init + '\n        FormInput.add(jLabel23);')

    # Add empty KeyPressed events
    events = """
    private void EdukasiPJKeyPressed(java.awt.event.KeyEvent evt) {                                      
        Valid.pindah(evt, Pekerjaan, EdukasiRS);
    }                                     

    private void EdukasiRSKeyPressed(java.awt.event.KeyEvent evt) {                                      
        Valid.pindah(evt, EdukasiPJ, BtnSimpan);
    } 
"""
    java = java.replace('private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {', events + 'private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {')
    java = java.replace('Valid.pindah(evt, Alamat, TCari);', 'Valid.pindah(evt, Alamat, EdukasiPJ);')

    # Add to DefaultTableModel Object[]
    java = java.replace('"Alasan Naik Kelas"', '"Alasan Naik Kelas", "Edukasi PJ", "Edukasi RS"')

    # Add to tbObat.getColumnModel loop (i < 32 -> i < 37)
    # wait, the original loop has `for (i = 0; i < 35; i++)` ? Let's just blindly change the bounds if needed.
    # Actually `for (i = 0; i < 35; i++)` might not exist exactly, let's use regex
    java = re.sub(r'for \(i = 0; i < 35; i\+\+\)', 'for (i = 0; i < 37; i++)', java)
    # the exact code was `for (i = 0; i < 32; i++)` based on previous grep! Let's check:
    java = re.sub(r'for \(i = 0; i < 32; i\+\+\)', 'for (i = 0; i < 37; i++)', java)

    # Add to INSERT query
    java = java.replace('alasan_naik_kelas)"', 'alasan_naik_kelas, edukasi_pj, edukasi_rs)"')
    java = java.replace('?,?,?,?"', '?,?,?,?,?,?"') # add 2 question marks
    java = java.replace(', new String[]{', ', new String[]{') # Ensure it's there
    java = java.replace('AlasanNaik.getText()', 'AlasanNaik.getText(), EdukasiPJ.getText(), EdukasiRS.getText()')
    java = java.replace('"Data", 29,', '"Data", 31,') # Update number of params

    # Add to UPDATE query (edit)
    java = java.replace('alasan_naik_kelas=?', 'alasan_naik_kelas=?, edukasi_pj=?, edukasi_rs=?')
    java = java.replace('AlasanNaik.getText(),\n', 'AlasanNaik.getText(),\n                                EdukasiPJ.getText(),\n                                EdukasiRS.getText(),\n')

    # Add to SELECT query (tampil) - it appears in 3 places
    java = java.replace('surat_persetujuan_umum.alasan_naik_kelas ', 'surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs ')

    # Add to SELECT query (report MyReportqry)
    java = java.replace('surat_persetujuan_umum.alasan_naik_kelas,', 'surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs,')

    # Add to tbObatMouseClicked
    java = java.replace('AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());', 'AlasanNaik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());\n                EdukasiPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());\n                EdukasiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());')

    # Add to emptTeks()
    java = java.replace('AlasanNaik.setText("");', 'AlasanNaik.setText("");\n        EdukasiPJ.setText("");\n        EdukasiRS.setText("");')

    with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
        f.write(java)

if __name__ == "__main__":
    patch_form()
    patch_java()
    print("Injected Form and Java")
