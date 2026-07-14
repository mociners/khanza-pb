import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    lines = f.readlines()

new_lines = []
changed = 0

for line in lines:
    # Match: String string123 = ... stringArray[XYZ] = ...
    # Wait, some lines have "String string = stringArray[68] = ..."
    # Let's handle "String string\d* = stringArray\[\d+\] = (.*);"
    
    m1 = re.search(r'^\s*String string\d* = (stringArray\[\d+\] = .*?;)$', line)
    if m1:
        indent = line[:len(line) - len(line.lstrip())]
        new_lines.append(indent + m1.group(1) + '\n')
        changed += 1
        continue
        
    m2 = re.search(r'^\s*String string\d*\s*=\s*(.*?);\s*$', line)
    if m2:
        content = m2.group(1)
        # Find stringArray[\d+] inside the content
        m_arr = re.search(r'(stringArray\[\d+\])\s*=\s*', content)
        if m_arr:
            arr_var = m_arr.group(1)
            # Remove "stringArray[XYZ] = " from the content
            new_content = content.replace(arr_var + " = ", "")
            indent = line[:len(line) - len(line.lstrip())]
            new_lines.append(indent + arr_var + " = " + new_content + ";\n")
            changed += 1
            continue

    new_lines.append(line)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.writelines(new_lines)

print("Changed ternary lines:", changed)
