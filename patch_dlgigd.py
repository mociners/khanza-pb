import re

with open('src/simrskhanza/DlgIGD.java', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Add MnPenilaianAwalKeperawatanPonek declaration
if "private javax.swing.JMenuItem MnPenilaianAwalKeperawatanPonek;" not in content:
    content = content.replace(
        "private javax.swing.JMenuItem MnPenilaianAwalKeperawatanIGD;",
        "private javax.swing.JMenuItem MnPenilaianAwalKeperawatanIGD;\n    private javax.swing.JMenuItem MnPenilaianAwalKeperawatanPonek;"
    )

# 2. Add MnPenilaianAwalKeperawatanPonek instantiation
if "MnPenilaianAwalKeperawatanPonek = new javax.swing.JMenuItem();" not in content:
    content = content.replace(
        "MnPenilaianAwalKeperawatanIGD = new javax.swing.JMenuItem();",
        "MnPenilaianAwalKeperawatanIGD = new javax.swing.JMenuItem();\n        MnPenilaianAwalKeperawatanPonek = new javax.swing.JMenuItem();"
    )

# 3. Add MnPenilaianAwalKeperawatanPonek properties and add it to MnRMIGD
if "MnPenilaianAwalKeperawatanPonek.setText(\"Penilaian Awal Keperawatan Ponek\");" not in content:
    prop = """
        MnPenilaianAwalKeperawatanPonek.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalKeperawatanPonek.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalKeperawatanPonek.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalKeperawatanPonek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalKeperawatanPonek.setText("Penilaian Awal Keperawatan Ponek");
        MnPenilaianAwalKeperawatanPonek.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalKeperawatanPonek.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalKeperawatanPonek.setName("MnPenilaianAwalKeperawatanPonek"); // NOI18N
        MnPenilaianAwalKeperawatanPonek.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPenilaianAwalKeperawatanPonek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalKeperawatanPonekActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPenilaianAwalKeperawatanPonek);
"""
    content = content.replace(
        "MnRMIGD.add(MnPenilaianAwalKeperawatanIGD);",
        "MnRMIGD.add(MnPenilaianAwalKeperawatanIGD);\n" + prop
    )

# 4. Add BtnPonek declaration
if "private widget.Button BtnPonek;" not in content:
    content = content.replace(
        "private widget.Button BtnPrw;",
        "private widget.Button BtnPonek;\n    private widget.Button BtnPrw;"
    )

# 5. Add BtnPonek instantiation
if "BtnPonek = new widget.Button();" not in content:
    content = content.replace(
        "BtnPrw = new widget.Button();",
        "BtnPonek = new widget.Button();\n        BtnPrw = new widget.Button();"
    )

# 6. Add BtnPonek properties and add it to panelGlass7
if "BtnPonek.setText(\"Asesmen Ponek\");" not in content:
    prop_btn = """
        BtnPonek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-add24.png"))); // NOI18N
        BtnPonek.setMnemonic('P');
        BtnPonek.setText("Asesmen Ponek");
        BtnPonek.setToolTipText("Alt+P");
        BtnPonek.setAutoscrolls(true);
        BtnPonek.setName("BtnPonek"); // NOI18N
        BtnPonek.setPreferredSize(new java.awt.Dimension(140, 30));
        BtnPonek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPonekActionPerformed(evt);
            }
        });
        BtnPonek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPonekKeyPressed(evt);
            }
        });
        panelGlass7.add(BtnPonek);
"""
    content = content.replace(
        "panelGlass7.add(BtnPrw);",
        prop_btn + "\n        panelGlass7.add(BtnPrw);"
    )

# 7. Add action handlers
action_handler = """
    private void BtnPonekActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                rekammedis.RMPenilaianAwalKeperawatanPonek form=new rekammedis.RMPenilaianAwalKeperawatanPonek(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private void BtnPonekKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    private void MnPenilaianAwalKeperawatanPonekActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                rekammedis.RMPenilaianAwalKeperawatanPonek form=new rekammedis.RMPenilaianAwalKeperawatanPonek(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
"""

if "private void BtnPonekActionPerformed" not in content:
    # insert before MnPenilaianAwalKeperawatanIGDActionPerformed or BtnPrwActionPerformed
    content = content.replace(
        "private void BtnPrwActionPerformed(java.awt.event.ActionEvent evt) {",
        action_handler + "\n    private void BtnPrwActionPerformed(java.awt.event.ActionEvent evt) {"
    )

with open('src/simrskhanza/DlgIGD.java', 'w', encoding='utf-8') as f:
    f.write(content)

print("Patching DlgIGD.java complete!")
