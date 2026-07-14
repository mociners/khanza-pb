with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    code = f.read()

code = code.replace('"\\u2611"', '"[ v ]"')
code = code.replace('"\\u2610"', '"[   ]"')

with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(code)

print("JRXML Fallback Checkboxes Applied!")
