import re
import os

def patch_form():
    path = "src/surat/SuratPersetujuanUmum.form"
    with open(path, "r") as f:
        form = f.read()

    # 1. Increase heights
    form = re.sub(r'<Dimension value="\[100, 650\]"/>', '<Dimension value="[100, 680]"/>', form)
    form = re.sub(r'<Dimension value="\[192, 650\]"/>', '<Dimension value="[192, 680]"/>', form)
    
    # 2. Add Saksi2 TextField and Label
    xml_saksi = """
        <Component class="widget.Label" name="jLabelSaksi2">
          <Properties>
            <Property name="text" type="java.lang.String" value="Saksi 2 :"/>
            <Property name="name" type="java.lang.String" value="jLabelSaksi2" noResource="true"/>
          </Properties>
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
              <AbsoluteConstraints x="0" y="600" width="130" height="23"/>
            </Constraint>
          </Constraints>
        </Component>
        <Component class="widget.TextBox" name="Saksi2">
          <Properties>
            <Property name="highlighter" type="javax.swing.text.Highlighter" editor="org.netbeans.modules.form.ComponentChooserEditor">
              <ComponentRef name="null"/>
            </Property>
            <Property name="name" type="java.lang.String" value="Saksi2" noResource="true"/>
          </Properties>
          <Events>
            <EventHandler event="keyPressed" listener="java.awt.event.KeyListener" parameters="java.awt.event.KeyEvent" handler="Saksi2KeyPressed"/>
          </Events>
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout" value="org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout$AbsoluteConstraintsDescription">
              <AbsoluteConstraints x="134" y="600" width="300" height="23"/>
            </Constraint>
          </Constraints>
        </Component>
"""
    form = form.replace('</SubComponents>\n    </Container>\n    <Container class="widget.PanelBiasa"', xml_saksi + '\n      </SubComponents>\n    </Container>\n    <Container class="widget.PanelBiasa"')
    
    # 3. Increase FormPass3 width to fit another button
    form = form.replace('<Dimension value="[115, 80]"/>', '<Dimension value="[115, 120]"/>')
    form = form.replace('<Dimension value="[115, 73]"/>', '<Dimension value="[115, 113]"/>')

    # 4. Add btnAmbilSaksi2 in FormPass3
    xml_btn = """
            <Component class="widget.Button" name="btnAmbilSaksi2">
              <Properties>
                <Property name="icon" type="javax.swing.Icon" editor="org.netbeans.modules.form.editors2.IconEditor">
                  <Image iconType="3" name="/picture/plus_16.png"/>
                </Property>
                <Property name="mnemonic" type="int" value="83"/>
                <Property name="text" type="java.lang.String" value="TTD Saksi 2"/>
                <Property name="toolTipText" type="java.lang.String" value="Klik untuk TTD Saksi 2"/>
                <Property name="name" type="java.lang.String" value="btnAmbilSaksi2" noResource="true"/>
                <Property name="preferredSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                  <Dimension value="[120, 30]"/>
                </Property>
              </Properties>
              <Events>
                <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="btnAmbilSaksi2ActionPerformed"/>
              </Events>
            </Component>
"""
    form = form.replace('<Component class="widget.Button" name="btnAmbilGambar">', xml_btn + '\n            <Component class="widget.Button" name="btnAmbilGambar">')
    
    with open(path, "w") as f:
        f.write(form)

def patch_java():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        java = f.read()

    # Increase Dimension 650 -> 680
    java = re.sub(r'Dimension\(100, 650\)', 'Dimension(100, 680)', java)
    java = re.sub(r'Dimension\(192, 650\)', 'Dimension(192, 680)', java)
    java = re.sub(r'Dimension\(WIDTH, 650\)', 'Dimension(WIDTH, 680)', java)
    
    # Increase FormPass3 Dimension
    java = re.sub(r'FormPhoto\.setPreferredSize\(new java\.awt\.Dimension\(115, 73\)\);', 'FormPhoto.setPreferredSize(new java.awt.Dimension(115, 113));', java)
    java = re.sub(r'FormPass3\.setPreferredSize\(new java\.awt\.Dimension\(115, 80\)\);', 'FormPass3.setPreferredSize(new java.awt.Dimension(115, 120));', java)

    # 2. Add variable declarations
    java = java.replace("private widget.TextBox Saksi;", "private widget.TextBox Saksi;\n    private widget.TextBox Saksi2;\n    private widget.Label jLabelSaksi2;\n    private widget.Button btnAmbilSaksi2;")
    
    # Check if they exist first to avoid double initialization
    if "jLabelSaksi2 = new widget.Label();" not in java:
        init_gui = """
        jLabelSaksi2 = new widget.Label();
        Saksi2 = new widget.TextBox();
        btnAmbilSaksi2 = new widget.Button();
        
        jLabelSaksi2.setText("Saksi 2 :");
        jLabelSaksi2.setName("jLabelSaksi2");
        FormInput.add(jLabelSaksi2);
        jLabelSaksi2.setBounds(0, 600, 130, 23);
        
        Saksi2.setHighlighter(null);
        Saksi2.setName("Saksi2");
        Saksi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Valid.pindah(evt, EdukasiRS, BtnSimpan);
            }
        });
        FormInput.add(Saksi2);
        Saksi2.setBounds(134, 600, 300, 23);
        
        btnAmbilSaksi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); 
        btnAmbilSaksi2.setMnemonic('S');
        btnAmbilSaksi2.setText("TTD Saksi 2");
        btnAmbilSaksi2.setToolTipText("Klik untuk TTD Saksi 2");
        btnAmbilSaksi2.setName("btnAmbilSaksi2"); 
        btnAmbilSaksi2.setPreferredSize(new java.awt.Dimension(120, 30));
        btnAmbilSaksi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tbObat.getSelectedRow() > -1) {
                    freehand.DlgTTDSaksi2 dlg = new freehand.DlgTTDSaksi2(null, true);
                    dlg.setNoSurat(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    dlg.setVisible(true);
                    if (!dlg.getNamaFile().equals("")) {
                        panggilPhoto();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
                }
            }
        });
        FormPass3.add(btnAmbilSaksi2);
"""
        java = java.replace("btnAmbilGambar = new widget.Button();", init_gui + "\n        btnAmbilGambar = new widget.Button();")

    # 3. Update SQL menyimpantf
    java = java.replace("alasan_naik_kelas, edukasi_pj, edukasi_rs)\", \"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?\", \"Data\", 31,", "alasan_naik_kelas, edukasi_pj, edukasi_rs, saksi_2)\", \"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?\", \"Data\", 32,")
    java = java.replace("EdukasiPJ.getText(), EdukasiRS.getText()", "EdukasiPJ.getText(), EdukasiRS.getText(), Saksi2.getText()")

    # 4. Update SQL mengedittf
    java = java.replace("edukasi_pj=?, edukasi_rs=?\", 29,", "edukasi_pj=?, edukasi_rs=?, saksi_2=?\", 30,")
    # Add Saksi2.getText() to mengedittf is already covered by the replacement above (EdukasiRS.getText() -> Saksi2.getText())
    
    # 5. emptTeks()
    java = java.replace("EdukasiRS.setText(\"\");", "EdukasiRS.setText(\"\");\n        Saksi2.setText(\"\");")
    
    # 6. panggilPhoto()
    # Read photo_saksi_2
    add_photo_saksi = """
            String fotoTtdSaksi = "";
            try {
                ps = koneksi.prepareStatement("select photo_saksi_2 from surat_persetujuan_umum where no_surat=?");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        fotoTtdSaksi = rs.getString("photo_saksi_2");
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                }
            } catch (Exception e) { }
            
            boolean adaTtdSaksi = (fotoTtdSaksi != null && !fotoTtdSaksi.equals("") && !fotoTtdSaksi.equals("-") && !fotoTtdSaksi.equals("null"));
"""
    java = java.replace("boolean adaTtd = (fotoTtd != null && !fotoTtd.equals(\"\") && !fotoTtd.equals(\"-\") && !fotoTtd.equals(\"null\"));", "boolean adaTtd = (fotoTtd != null && !fotoTtd.equals(\"\") && !fotoTtd.equals(\"-\") && !fotoTtd.equals(\"null\"));\n" + add_photo_saksi)
    
    html_saksi = """
                if (adaTtdSaksi) {
                    html.append("<br><br><p><b>Tanda Tangan Saksi 2:</b></p>");
                    File fileLokalSaksi = new File("pernyataanumum" + File.separator + "pages" + File.separator + "upload" + File.separator + fotoTtdSaksi);
                    if (fileLokalSaksi.exists()) {
                        html.append("<img src='").append(fileLokalSaksi.toURI().toString()).append("' width='300' height='300'/>");
                    } else {
                        String serverUrlSaksi = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + fotoTtdSaksi;
                        html.append("<img src='").append(serverUrlSaksi).append("' width='300' height='300'/>");
                    }
                }
"""
    java = java.replace('html.append("</center></body></html>");', html_saksi + '\n            html.append("</center></body></html>");')

    # 7. Update tabMode to include Saksi2 if possible (Actually we might not need to update tabMode if it's too much, but let's check if we can.)
    
    with open(path, "w") as f:
        f.write(java)

if __name__ == "__main__":
    patch_form()
    patch_java()
    print("Patched GUI and Java for Saksi 2")
