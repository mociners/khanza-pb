import re

with open('src/rekammedis/RMDataResumePasienRanap.java', 'r') as f:
    content = f.read()

# 1. Declare variable at the bottom
if 'private widget.Button BtnResepPulang;' not in content:
    content = re.sub(
        r'(    private widget\.Button BtnSimpan;)',
        r'\1\n    private widget.Button BtnResepPulang;',
        content
    )

# 2. Add instantiation and bounds in initComponents() after label16
btn_code = """
        BtnResepPulang = new widget.Button();
        BtnResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnResepPulang.setMnemonic('X');
        BtnResepPulang.setToolTipText("Alt+X");
        BtnResepPulang.setName("BtnResepPulang"); // NOI18N
        BtnResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepPulangActionPerformed(evt);
            }
        });
        FormInput.add(BtnResepPulang);
        BtnResepPulang.setBounds(102, 1573, 28, 23);
"""
if 'BtnResepPulang = new widget.Button();' not in content:
    content = content.replace(
        'label16.setBounds(30, 1550, 100, 23);',
        'label16.setBounds(30, 1550, 100, 23);\n' + btn_code
    )

# 3. Add the action listener method at the end of the class (before the variables declaration)
method_code = """
    private void BtnResepPulangActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
            } else {
                simrskhanza.DlgPermintaanResepPulang resep = new simrskhanza.DlgPermintaanResepPulang(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.setVisible(true);
                resep.setNoRm(TNoRw.getText(), Keluar.getDate());
                resep.isCek();
                resep.tampil();
            }
        }
    }                                                      
"""

if 'private void BtnResepPulangActionPerformed' not in content:
    # Insert it right before // Variables declaration - do not modify
    content = content.replace(
        '    // Variables declaration - do not modify',
        method_code + '\n    // Variables declaration - do not modify'
    )

with open('src/rekammedis/RMDataResumePasienRanap.java', 'w') as f:
    f.write(content)
