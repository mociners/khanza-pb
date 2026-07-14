import re

with open("src/simrskhanza/DlgKamarInap.java", "r") as f:
    content = f.read()

# 1. Declaration
if "private javax.swing.JMenuItem MnRMTTVBalanceCairan;" not in content:
    content = content.replace(
        "private javax.swing.JMenuItem MnCatatanObservasiRanap;",
        "private javax.swing.JMenuItem MnCatatanObservasiRanap;\n    private javax.swing.JMenuItem MnRMTTVBalanceCairan;"
    )

# 2. Instantiation
if "MnRMTTVBalanceCairan = new javax.swing.JMenuItem();" not in content:
    content = content.replace(
        "MnCatatanObservasiRanap = new javax.swing.JMenuItem();",
        "MnCatatanObservasiRanap = new javax.swing.JMenuItem();\n        MnRMTTVBalanceCairan = new javax.swing.JMenuItem();"
    )

# 3. Setup and Add (near MnCatatanObservasiRanap setup)
setup_code = """
        MnRMTTVBalanceCairan.setBackground(new java.awt.Color(255, 255, 254));
        MnRMTTVBalanceCairan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMTTVBalanceCairan.setForeground(new java.awt.Color(50, 50, 50));
        MnRMTTVBalanceCairan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMTTVBalanceCairan.setText("Observasi TTV & Balance Cairan");
        MnRMTTVBalanceCairan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMTTVBalanceCairan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMTTVBalanceCairan.setName("MnRMTTVBalanceCairan"); // NOI18N
        MnRMTTVBalanceCairan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRMTTVBalanceCairan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRMTTVBalanceCairanActionPerformed(evt);
            }
        });
        MnDataRM.add(MnRMTTVBalanceCairan);
"""
if "MnRMTTVBalanceCairan.setBackground" not in content:
    # We add it right before MnDataRM.add(MnCatatanObservasiRanap); or similar in MnDataRM.
    # We will search for MnDataRM.add(MnCatatanObservasiRanap); but earlier I saw MnObservasi.add(MnCatatanObservasiRanap).
    # The user asked for "jPopUpMenu1 -> MnDataRM".
    # In DlgKamarInap.java, MnDataRM.add(...) adds to MnDataRM.
    # Let's add it right before MnDataRM.add(MnEdukasiPasien);
    content = content.replace(
        "MnDataRM.add(MnEdukasiPasien);",
        setup_code + "\n        MnDataRM.add(MnEdukasiPasien);"
    )

# 4. Action Method
action_code = """
    private void MnRMTTVBalanceCairanActionPerformed(java.awt.event.ActionEvent evt) {
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                rekammedis.RMTTVBalanceCairan form = new rekammedis.RMTTVBalanceCairan(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                
                String rawat_inap_no = TNoRwCari.getText();
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                     // try fallback
                     rawat_inap_no = tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString();
                }
                
                if (R1.isSelected() == true) {
                    form.setNoRm(rawat_inap_no, new Date());
                } else if (R2.isSelected() == true) {
                    form.setNoRm(rawat_inap_no, DTPCari2.getDate());
                } else if (R3.isSelected() == true) {
                    form.setNoRm(rawat_inap_no, DTPCari4.getDate());
                }
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data terlebih dahulu...!!!!");
                tbKamIn.requestFocus();
            }
        }
    }
"""
if "MnRMTTVBalanceCairanActionPerformed" not in content:
    content = content.replace(
        "private void MnEdukasiPasienActionPerformed(java.awt.event.ActionEvent evt) {",
        action_code + "\n    private void MnEdukasiPasienActionPerformed(java.awt.event.ActionEvent evt) {"
    )

with open("src/simrskhanza/DlgKamarInap.java", "w") as f:
    f.write(content)
