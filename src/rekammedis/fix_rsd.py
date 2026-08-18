import re

with open('/home/itrsupb/Documents/khanza-pb/src/rekammedis/RMSkriningMPPFormA.java', 'r') as f:
    content = f.read()

# find getData() method
match = re.search(r'private void getData\(\)\s*\{', content)
if match:
    start_idx = match.end()
    # Find the end of getData() method (simple brace counting)
    brace_count = 1
    idx = start_idx
    while brace_count > 0 and idx < len(content):
        if content[idx] == '{':
            brace_count += 1
        elif content[idx] == '}':
            brace_count -= 1
        idx += 1
    
    end_idx = idx
    
    # replace rs.getString with rsd.getString within this block
    getData_content = content[start_idx:end_idx]
    new_getData_content = getData_content.replace('rs.getString', 'rsd.getString')
    
    new_content = content[:start_idx] + new_getData_content + content[end_idx:]
    
    with open('/home/itrsupb/Documents/khanza-pb/src/rekammedis/RMSkriningMPPFormA.java', 'w') as f:
        f.write(new_content)
    print("Fixed rs.getString -> rsd.getString in getData()")
else:
    print("getData() not found")
