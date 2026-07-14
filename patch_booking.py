import re

with open("src/permintaan/DlgBookingOperasi.java", "r") as f:
    content = f.read()

# 1. Variable declaration
if "private widget.Button BtnObservasiTTV;" not in content:
    content = content.replace("private widget.Button BtnPermintaanLab;", "private widget.Button BtnPermintaanLab;\n    private widget.Button BtnObservasiTTV;")

# 2. Add button initialization to FormMenu
button_init = """        FormMenu.add(BtnCatatanKamarPemulihan);

        BtnObservasiTTV = new widget.Button();
        BtnObservasiTTV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnObservasiTTV.setText("Observasi TTV & Balance Cairan");
        BtnObservasiTTV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObservasiTTV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnObservasiTTV.setName("BtnObservasiTTV"); // NOI18N
        BtnObservasiTTV.setPreferredSize(new java.awt.Dimension(250, 26));
        BtnObservasiTTV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObservasiTTVActionPerformed(evt);
            }
        });
        FormMenu.add(BtnObservasiTTV);"""

if "BtnObservasiTTV = new widget.Button();" not in content:
    content = content.replace("        FormMenu.add(BtnCatatanKamarPemulihan);", button_init)

# 3. Add the action method
action_method = """    private void BtnObservasiTTVActionPerformed(java.awt.event.ActionEvent evt) {
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
            rekammedis.RMTTVBalanceCairan form = new rekammedis.RMTTVBalanceCairan(null, false);
            form.isCek();
            form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(), Valid.SetTgl(Tanggal.getSelectedItem() + ""));
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(java.awt.Cursor.getDefaultCursor());
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data Booking Operasi terlebih dahulu...!!!!");
            tbObat.requestFocus();
        }
    }
"""

if "BtnObservasiTTVActionPerformed" not in content:
    content = content.replace("    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {", action_method + "\n    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {")

# 4. Modify visibility rules
# Default block
if "BtnObservasiTTV.setVisible(true);" not in content:
    content = content.replace("BtnTransferAntarRuang.setVisible(true);", "BtnTransferAntarRuang.setVisible(true);\n            BtnObservasiTTV.setVisible(true);")

# isPerawatOK block
content = re.sub(r'(} else if \(isDokterBedah\) \{)', r'    BtnObservasiTTV.setVisible(false);\n        \1', content)

# isDokterBedah block
content = re.sub(r'(} else if \(isDokterAnestesi\) \{)', r'    BtnObservasiTTV.setVisible(false);\n        \1', content)

# isDokterAnestesi block
content = re.sub(r'(} else if \(isPerawatRR\) \{)', r'    BtnObservasiTTV.setVisible(false);\n        \1', content)

# isPerawatRR block
content = re.sub(r'(} else if \(isPenataAnestesi\) \{)', r'    BtnObservasiTTV.setVisible(true);\n        \1', content)

# isPenataAnestesi block
# Find the end of aturVisibilitasTombolFormMenu, which is before the next private void
content = re.sub(r'(        }\n    }\n\n    private void DTPCari1KeyPressed)', r'            BtnObservasiTTV.setVisible(false);\n\1', content)


with open("src/permintaan/DlgBookingOperasi.java", "w") as f:
    f.write(content)
