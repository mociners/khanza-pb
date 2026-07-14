file_path = "/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"
with open(file_path, 'r') as f:
    java_code = f.read()

# Replace tbObat with tbData ONLY in the block I injected!
# Wait, I injected tbObat.setComponentPopupMenu(jPopupMenu1) and tbObat.getSelectedRow() and tbObat.getValueAt()
# Let's just replace tbObat.setComponentPopupMenu with tbData.setComponentPopupMenu
java_code = java_code.replace("tbObat.setComponentPopupMenu(jPopupMenu1)", "tbData.setComponentPopupMenu(jPopupMenu1)")
java_code = java_code.replace("tbObat.getSelectedRow()", "tbData.getSelectedRow()")
java_code = java_code.replace("tbObat.getValueAt(", "tbData.getValueAt(")

if "import java.awt.Cursor;" not in java_code:
    java_code = java_code.replace("import java.awt.Dimension;", "import java.awt.Dimension;\nimport java.awt.Cursor;")

with open(file_path, 'w') as f:
    f.write(java_code)
