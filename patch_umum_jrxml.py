import re

with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    code = f.read()

field_old = """	<field name="alasan_tolak_bpjs" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>"""
field_new = """	<field name="alasan_tolak_bpjs" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="alasan_tolak_bpjs_kerja" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="alasan_tolak_jasa_raharja" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>"""
code = code.replace(field_old, field_new)

# BPJS Ketenagakerjaan line
code = re.sub(r'("BPJS Ketenagakerjaan, alasan " \+ \(\$F\{)alasan_tolak_bpjs(\}==null \? "" : \$F\{)alasan_tolak_bpjs(\}\))', r'\1alasan_tolak_bpjs_kerja\2alasan_tolak_bpjs_kerja\3', code)

# Jasa Raharja line
code = re.sub(r'("Jasa Raharja, alasan " \+ \(\$F\{)alasan_tolak_bpjs(\}==null \? "" : \$F\{)alasan_tolak_bpjs(\}\))', r'\1alasan_tolak_jasa_raharja\2alasan_tolak_jasa_raharja\3', code)

with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(code)

print("JRXML File Patched for 3 Umum Inputs!")
