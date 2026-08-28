import re

with open('report/rptLaporanResumeRanap.jrxml', 'r') as f:
    content = f.read()

# Remove jamkeluar parameter
content = re.sub(r'<parameter name="jamkeluar".*?/>\n\s*', '', content)

# Remove textField for jam_reg
content = re.sub(
    r'<textField isStretchWithOverflow="true" pattern="HH:mm:ss">\s*<reportElement x="446".*?</textField>\n\s*',
    '',
    content,
    flags=re.DOTALL
)

# Remove textField for jamkeluar
content = re.sub(
    r'<textField isStretchWithOverflow="true">\s*<reportElement x="446".*?\n.*?<textFieldExpression><!\[CDATA\[\$P\{jamkeluar\}\]\]></textFieldExpression>\n\s*</textField>\n\s*',
    '',
    content,
    flags=re.DOTALL
)

# Fix the format of the date fields. Wait, they might have been sized to share space with time.
# Let's check how wide the tgl_registrasi and tanggalkeluar fields are.
# But just removing time is enough.

with open('report/rptLaporanResumeRanap.jrxml', 'w') as f:
    f.write(content)
