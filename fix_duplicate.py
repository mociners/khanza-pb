import re

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

target = """    <field name="kd_dokter" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
"""
# Replace ONLY the first occurrence (which is my injected one)
content = content.replace(target, "", 1)

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(content)
