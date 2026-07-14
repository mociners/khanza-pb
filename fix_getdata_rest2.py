import re

with open('generated_missing.txt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if 'Integer.parseInt(val_216)' in line:
        line = '                        if (val_216.equals("-")) { this.CmbCpotVentilator.setSelectedIndex(0); } else { try { this.CmbCpotVentilator.setSelectedIndex(Integer.parseInt(val_216) + 1); } catch(Exception e) {} }\n'
    elif 'Integer.parseInt(val_217)' in line:
        line = '                        if (val_217.equals("-")) { this.CmbCpotVokalisasi.setSelectedIndex(0); } else { try { this.CmbCpotVokalisasi.setSelectedIndex(Integer.parseInt(val_217) + 1); } catch(Exception e) {} }\n'
    new_lines.append(line)

with open('generated_missing.txt', 'w') as f:
    f.writelines(new_lines)
