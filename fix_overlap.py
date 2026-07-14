def fix_java():
    with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
        java = f.read()

    # Move down to Y=570
    java = java.replace('jLabelEdukasiPJ.setBounds(0, 150, 105, 23);', 'jLabelEdukasiPJ.setBounds(0, 570, 105, 23);')
    java = java.replace('EdukasiPJ.setBounds(110, 150, 370, 23);', 'EdukasiPJ.setBounds(110, 570, 370, 23);')
    java = java.replace('jLabelEdukasiRS.setBounds(490, 150, 80, 23);', 'jLabelEdukasiRS.setBounds(490, 570, 80, 23);')
    java = java.replace('EdukasiRS.setBounds(580, 150, 370, 23);', 'EdukasiRS.setBounds(580, 570, 370, 23);')

    with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
        f.write(java)

def fix_form():
    with open("src/surat/SuratPersetujuanUmum.form", "r") as f:
        form = f.read()

    # Move down to Y=570 (Only for the Edukasi elements added at the end)
    # They are the only ones with these specific widths at y="150"
    
    # 1. jLabelEdukasiPJ
    form = form.replace('<AbsoluteConstraints x="0" y="150" width="105" height="23"/>', '<AbsoluteConstraints x="0" y="570" width="105" height="23"/>')
    # 2. EdukasiPJ
    form = form.replace('<AbsoluteConstraints x="110" y="150" width="370" height="23"/>', '<AbsoluteConstraints x="110" y="570" width="370" height="23"/>')
    # 3. jLabelEdukasiRS
    form = form.replace('<AbsoluteConstraints x="490" y="150" width="80" height="23"/>', '<AbsoluteConstraints x="490" y="570" width="80" height="23"/>')
    # 4. EdukasiRS
    form = form.replace('<AbsoluteConstraints x="580" y="150" width="370" height="23"/>', '<AbsoluteConstraints x="580" y="570" width="370" height="23"/>')

    with open("src/surat/SuratPersetujuanUmum.form", "w") as f:
        f.write(form)

if __name__ == "__main__":
    fix_java()
    fix_form()
    print("Fixed GUI overlap")
