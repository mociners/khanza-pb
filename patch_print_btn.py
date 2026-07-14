import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Remove jPopupMenu1 and MnCetak declarations
content = content.replace("    private javax.swing.JPopupMenu jPopupMenu1;\n", "")
content = content.replace("    private javax.swing.JMenuItem MnCetak;\n", "")

# 2. Remove initialization of jPopupMenu1 and MnCetak
popup_init_pattern = r"        jPopupMenu1 = new javax\.swing\.JPopupMenu\(\);\n        MnCetak = new javax\.swing\.JMenuItem\(\);\n.*?        tbObat\.setComponentPopupMenu\(jPopupMenu1\);\n"
content = re.sub(popup_init_pattern, "", content, flags=re.DOTALL)

# 3. Extract logic from MnCetakActionPerformed
mn_cetak_pattern = r"    private void MnCetakActionPerformed\(java\.awt\.event\.ActionEvent evt\) \{\n(.*?)        \}\n    \}\n"
match = re.search(mn_cetak_pattern, content, flags=re.DOTALL)
if match:
    logic = match.group(1)
    
    # 4. Inject logic into BtnPrintActionPerformed
    btn_print_pattern = r"    private void BtnPrintActionPerformed\(java\.awt\.event\.ActionEvent evt\) \{//GEN-FIRST:event_BtnPrintActionPerformed\n\}//GEN-LAST:event_BtnPrintActionPerformed\n"
    new_btn_print = f"    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {{//GEN-FIRST:event_BtnPrintActionPerformed\n{logic}        }}\n    }}//GEN-LAST:event_BtnPrintActionPerformed\n"
    content = re.sub(btn_print_pattern, new_btn_print, content)
    
    # 5. Remove MnCetakActionPerformed completely
    content = re.sub(mn_cetak_pattern, "", content, flags=re.DOTALL)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
