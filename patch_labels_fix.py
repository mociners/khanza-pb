with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    # Output replacements
    if 'jLabel254.setText("Muntah : ");' in line:
        lines[i] = '        jLabel254.setText("Urine : ");\n'
    elif 'jLabel255.setText("Muntah : ");' in line:
        lines[i] = '        jLabel255.setText("Muntah : ");\n' # this was correct
    elif 'jLabel256.setText("NGT : ");' in line:
        pass # this was correct
    elif 'jLabel257.setText("Drain : ");' in line:
        lines[i] = '        jLabel257.setText("IWL : ");\n'
    elif 'jLabel258.setText("Drain : ");' in line:
        lines[i] = '        jLabel258.setText("Drain : ");\n'

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.writelines(lines)
