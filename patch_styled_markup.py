import re

# 1. Update JRXML to use markup="styled"
with open('report/rptObservasiTTVBalance.jrxml', 'r') as f:
    jrxml = f.read()

# Replace html markup with styled markup
jrxml = jrxml.replace('markup="html"', 'markup="styled"')

# Replace the legend textFieldExpression
old_expr = '<textFieldExpression><![CDATA["Keterangan:   <font color=\'red\'>Nadi</font>     Respirasi     <font color=\'blue\'>Suhu</font>"]]></textFieldExpression>'
new_expr = '<textFieldExpression><![CDATA["Keterangan:   <style forecolor=\'red\'>Nadi</style>     Respirasi     <style forecolor=\'blue\'>Suhu</style>"]]></textFieldExpression>'
jrxml = jrxml.replace(old_expr, new_expr)

with open('report/rptObservasiTTVBalance.jrxml', 'w') as f:
    f.write(jrxml)


# 2. Update Java code to use <style forecolor='red'>
with open('src/rekammedis/RMTTVBalanceCairan.java', 'r') as f:
    java_code = f.read()

# Replace HTML font tags with Jasper style tags
java_code = java_code.replace("\"<font color='red'><b>\" + (int)nadi + \"</b></font>\"", "\"<style forecolor='red' isBold='true'>\" + (int)nadi + \"</style>\"")
java_code = java_code.replace("\"<font color='#333333'>\" + (int)resp + \"</font>\"", "\"<style forecolor='black'>\" + (int)resp + \"</style>\"")
java_code = java_code.replace("\"<font color='blue'><b>\" + suhu + \"</b></font>\"", "\"<style forecolor='blue' isBold='true'>\" + suhu + \"</style>\"")

with open('src/rekammedis/RMTTVBalanceCairan.java', 'w') as f:
    f.write(java_code)

print("Updated to use styled markup!")
