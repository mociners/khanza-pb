import re

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    xml = f.read()

# Increase title band height
xml = re.sub(r'<title>\s*<band height="80">', r'<title>\n        <band height="140">', xml)

# Shift patient info Y coords down by 80
# The patient info starts after the <line> block. 
# We can find all reportElement x="350" y="0", x="410" y="0", x="420" y="0", etc. inside <title>
def shift_y(match):
    prefix = match.group(1)
    y_val = int(match.group(2))
    rest = match.group(3)
    if y_val < 70:  # Only shift those above the line (0, 15, 30, 45)
        y_val += 80
    return f'{prefix}y="{y_val}"{rest}'

# We only want to do this inside the <title> section
title_match = re.search(r'(<title>)(.*?)(</title>)', xml, re.DOTALL)
if title_match:
    title_content = title_match.group(2)
    # Match reportElement x="350" y="0" ...
    # Wait, they could be x="350", x="410", x="420".
    # Let's match all reportElements with y=0, 15, 30, 45 AND x >= 350
    def repl_report_element(m):
        x_val = int(m.group(1))
        y_val = int(m.group(2))
        rest = m.group(3)
        if x_val >= 350 and y_val < 70:
            y_val += 80
            # let's also move them to the left side (x=0, 60, 70) for better readability?
            # Or keep them on the right? Keeping them on the left:
            if x_val == 350: x_val = 0
            elif x_val == 410: x_val = 60
            elif x_val == 420: x_val = 70
        return f'<reportElement x="{x_val}" y="{y_val}" {rest}'

    new_title_content = re.sub(r'<reportElement x="(\d+)" y="(\d+)" (.*?/>)', repl_report_element, title_content)
    xml = xml[:title_match.start(2)] + new_title_content + xml[title_match.end(2):]

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(xml)

print("Header fixed!")
