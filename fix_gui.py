import re

def process_java():
    filepath = 'src/surat/SuratPersetujuanUmum.java'
    with open(filepath, 'r') as f:
        content = f.read()
    
    # 1. Shift all Y coordinates >= 150 by +30
    def shift_y(match):
        pre = match.group(1)
        var = match.group(2)
        x = match.group(3)
        y = int(match.group(4))
        w = match.group(5)
        h = match.group(6)
        post = match.group(7)
        if y >= 150:
            y += 30
        return f"{pre}{var}.setBounds({x}, {y}, {w}, {h}){post}"

    pattern = r'(^([ \t]*[a-zA-Z0-9_]+)\.setBounds\()(\d+),\s*(\d+),\s*(\d+),\s*(\d+)(\);)'
    
    lines = content.split('\n')
    new_lines = []
    for line in lines:
        if '.setBounds(' in line:
            line = re.sub(pattern, shift_y, line)
        new_lines.append(line)
        
    content = '\n'.join(new_lines)
    
    # 2. Hardcode new bounds for specific components to fix overlaps and widths
    replacements = {
        # Y=90
        r'jLabel44\.setBounds\(353, 90, 90, 23\);': r'jLabel44.setBounds(355, 90, 60, 23);',
        r'UmurPJ\.setBounds\(447, 90, 47, 23\);': r'UmurPJ.setBounds(420, 90, 47, 23);',
        r'jLabel15\.setBounds\(509, 90, 70, 23\);': r'jLabel15.setBounds(475, 90, 55, 23);',
        r'NoKTP\.setBounds\(583, 90, 150, 23\);': r'NoKTP.setBounds(535, 90, 150, 23);',
        r'jLabel9\.setBounds\(740, 90, 40, 23\);': r'jLabel9.setBounds(695, 90, 40, 23);',
        r'JKPJ\.setBounds\(790, 90, 110, 23\);': r'JKPJ.setBounds(740, 90, 110, 23);',
        
        # Y=120
        r'jLabel20\.setBounds\(30, 120, 96, 23\);': r'jLabel20.setBounds(0, 120, 85, 23);',
        r'NoTelp\.setBounds\(130, 120, 160, 23\);': r'NoTelp.setBounds(89, 120, 130, 23);',
        r'jLabel22\.setBounds\(270, 120, 70, 23\);': r'jLabel22.setBounds(225, 120, 50, 23);',
        r'Alamat\.setBounds\(340, 120, 135, 23\);': r'Alamat.setBounds(280, 120, 230, 23);',
        r'ChkAlamatPJ\.setBounds\(475, 120, 60, 23\);': r'ChkAlamatPJ.setBounds(515, 120, 60, 23);',
        r'jLabel23\.setBounds\(535, 120, 65, 23\);': r'jLabel23.setBounds(580, 120, 65, 23);',
        r'Pekerjaan\.setBounds\(600, 120, 105, 23\);': r'Pekerjaan.setBounds(650, 120, 130, 23);',
        
        # Move Hubungan to Y=150
        r'jLabel8\.setBounds\(700, 120, 160, 23\);': r'jLabel8.setBounds(0, 150, 85, 23);',
        r'BertindakAtas\.setBounds\(865, 120, 75, 23\);': r'BertindakAtas.setBounds(89, 150, 110, 23);',
        r'KetBertindak\.setBounds\(945, 120, 100, 23\);': r'KetBertindak.setBounds(205, 150, 250, 23);',
        
        # Y=180 (previously 150)
        r'jLabelInfoBiaya\.setBounds\(490, 180, 90, 23\);': r'jLabelInfoBiaya.setBounds(485, 180, 90, 23);',
        r'InfoBiaya\.setBounds\(583, 180, 180, 23\);': r'InfoBiaya.setBounds(580, 180, 140, 23);',
        r'jLabelKeluarga1\.setBounds\(765, 180, 70, 23\);': r'jLabelKeluarga1.setBounds(725, 180, 75, 23);',
        r'Keluarga1\.setBounds\(840, 180, 140, 23\);': r'Keluarga1.setBounds(805, 180, 140, 23);',
        
        # Y=210 (previously 180)
        r'jLabelKeluarga2\.setBounds\(765, 210, 70, 23\);': r'jLabelKeluarga2.setBounds(725, 210, 75, 23);',
        r'Keluarga2\.setBounds\(840, 210, 140, 23\);': r'Keluarga2.setBounds(805, 210, 140, 23);',
        
        # Y=600 (previously 570)
        r'EdukasiPJ\.setBounds\(110, 600, 370, 23\);': r'EdukasiPJ.setBounds(110, 600, 350, 23);',
        r'jLabelEdukasiRS\.setBounds\(490, 600, 80, 23\);': r'jLabelEdukasiRS.setBounds(470, 600, 80, 23);',
        r'EdukasiRS\.setBounds\(580, 600, 370, 23\);': r'EdukasiRS.setBounds(555, 600, 350, 23);',
        
        # Increase FormInput height
        r'FormInput\.setPreferredSize\(new java\.awt\.Dimension\(100, 680\)\);': r'FormInput.setPreferredSize(new java.awt.Dimension(100, 710));',
        r'PanelInput\.setPreferredSize\(new java\.awt\.Dimension\(192, 680\)\);': r'PanelInput.setPreferredSize(new java.awt.Dimension(192, 710));',
        r'PanelInput\.setPreferredSize\(new Dimension\(WIDTH, 680\)\);': r'PanelInput.setPreferredSize(new Dimension(WIDTH, 710));'
    }

    for old_regex, new_val in replacements.items():
        content = re.sub(old_regex, new_val, content)

    with open(filepath, 'w') as f:
        f.write(content)

if __name__ == '__main__':
    process_java()
