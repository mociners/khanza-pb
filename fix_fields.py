import re

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

missing_fields = """
    <field name="no_rkm_medis" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
    <field name="nm_pasien" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
    <field name="jk" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
    <field name="tgl_lahir" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
    <field name="kd_dokter" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
    <field name="nm_dokter" class="java.lang.String">
        <fieldDescription><![CDATA[]]></fieldDescription>
    </field>
"""

if '<field name="no_rkm_medis"' not in content:
    target = '</queryString>'
    replacement = '</queryString>\n' + missing_fields
    content = content.replace(target, replacement)
    with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
        f.write(content)
    print("SUCCESS: Missing fields injected.")
else:
    print("Fields already exist.")
