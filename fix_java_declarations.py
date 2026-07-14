file_path = "/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"
with open(file_path, 'r') as f:
    java_code = f.read()

if "JPopupMenu jPopupMenu1" not in java_code:
    java_code = java_code.replace("private DefaultTableModel tabMode;", "private DefaultTableModel tabMode;\n    private javax.swing.JPopupMenu jPopupMenu1;\n    private javax.swing.JMenuItem MnCetak;")

with open(file_path, 'w') as f:
    f.write(java_code)
