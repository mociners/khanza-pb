#!/usr/bin/env python3
import re
import uuid

jrxml_file = "report/rptSuratPersetujuanUmum.jrxml"
with open(jrxml_file, "r") as f:
    content = f.read()

# We will use regex to find pairs of staticText (width <= 20) and textField sharing the same Y
# Since XML structure might vary slightly, let's just find them iteratively.

pattern = re.compile(
    r'(\t*<staticText>\s*<reportElement positionType="Float" x="(\d+)" y="(\d+)" width="(\d+)" height="12" uuid="[^"]+"/>\s*<textElement>\s*<font fontName="Tahoma" size="8"/>\s*</textElement>\s*<text><!\[CDATA\[(.*?)\]\]></text>\s*</staticText>\s*<textField isStretchWithOverflow="true">\s*<reportElement positionType="Float" x="(\d+)" y="\3" width="(\d+)" height="12" uuid="[^"]+"/>\s*<textElement textAlignment="Justified">\s*<font fontName="Tahoma" size="8"/>\s*</textElement>\s*<textFieldExpression><!\[CDATA\[(.*?)]]></textFieldExpression>\s*</textField>)',
    re.DOTALL
)

def replacer(match):
    full_match = match.group(1)
    x_static = match.group(2)
    y = match.group(3)
    w_static = int(match.group(4))
    text_static = match.group(5)
    x_tf = match.group(6)
    w_tf = int(match.group(7))
    expr_tf = match.group(8)

    # We only merge if it's a short text like "a.", "1.", "-"
    if w_static > 30:
        return full_match

    # Merge!
    new_uuid = str(uuid.uuid4())
    new_width = w_static + w_tf

    # Safely merge expr_tf with text_static
    if expr_tf.startswith('"') and expr_tf.endswith('"') and '"+' not in expr_tf and '+"' not in expr_tf:
        # Simple string
        new_expr = f'"{text_static} ' + expr_tf[1:]
    else:
        new_expr = f'"{text_static} " + ({expr_tf})'
        
    replacement = f'''\t\t\t<textField isStretchWithOverflow="true">
\t\t\t\t<reportElement positionType="Float" x="{x_static}" y="{y}" width="{new_width}" height="12" uuid="{new_uuid}"/>
\t\t\t\t<textElement textAlignment="Justified">
\t\t\t\t\t<font fontName="Tahoma" size="8"/>
\t\t\t\t</textElement>
\t\t\t\t<textFieldExpression><![CDATA[{new_expr}]]></textFieldExpression>
\t\t\t</textField>'''
    
    print(f"Merged at y={y} with text {text_static}")
    return replacement

new_content = pattern.sub(replacer, content)

# Check if there are any that have x_tf and x_static reversed or similar?
# Let's save the file
with open(jrxml_file, "w") as f:
    f.write(new_content)

print(f"Replacements made. Length before: {len(content)}, after: {len(new_content)}")
