import re

with open("report/rptBuktiRegister.jrxml", "r") as f:
    xml = f.read()

# Remove stretchType="RelativeToTallestObject" everywhere
xml = xml.replace(' stretchType="RelativeToTallestObject"', '')

# For static text at x="5", increase width to 211
# This ensures it overlaps the textfield so that positionType="Float" pushes them down symmetrically
# But make sure we only match the ones in that specific block, or all x="5" in detail band.
xml = re.sub(r'(<reportElement positionType="Float" x="5" y="\d+" width=")\d+(" height="\d+")', r'\g<1>211\g<2>', xml)

# For static text at x="75" (colon), increase width to 141
xml = re.sub(r'(<reportElement positionType="Float" x="75" y="\d+" width=")\d+(" height="\d+")', r'\g<1>141\g<2>', xml)

with open("report/rptBuktiRegister.jrxml", "w") as f:
    f.write(xml)

print("Jasper layout updated perfectly.")
