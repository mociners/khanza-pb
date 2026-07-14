#!/usr/bin/env python3
import re
import os

# 1. Fix BtnSimpanActionPerformed in SuratPersetujuanUmum.java
java_file = "src/surat/SuratPersetujuanUmum.java"
with open(java_file, "r") as f:
    java_code = f.read()

old_insert = 'Sequel.menyimpantf("surat_persetujuan_umum", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 23'
new_insert = 'Sequel.menyimpantf("surat_persetujuan_umum (no_surat,no_rawat,tanggal,pengobatan_kepada,nilai_kepercayaan,nama_pj,umur_pj,no_ktppj,jkpj,bertindak_atas,no_telp,nip,alamat_pj,pekerjaan_pj,privasi_akses,privasi_khusus,alasan_tolak_bpjs,asuransi_swasta,no_kartu_asuransi,no_jkn_jasa_raharja,hak_kelas,pilihan_kamar,alasan_naik_kelas)", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 23'

java_code = java_code.replace(old_insert, new_insert)

with open(java_file, "w") as f:
    f.write(java_code)

print("Fixed Java insert statement")

# 2. Fix the A B C D and A B C lettered items in rptSuratPersetujuanUmum.jrxml
jrxml_file = "report/rptSuratPersetujuanUmum.jrxml"
with open(jrxml_file, "r") as f:
    jrxml_code = f.read()

# We need to find Band 4 which contains "10. INFORMASI RAWAT JALAN / RAWAT INAP"
# In band 4, we have staticText "a." followed by a textField.
# We will combine them into single textFields that just start with "a. Saya/pasien tidak..."

# Function to merge letter + text pairs
def merge_letter_text(band_content):
    # This is a bit tricky with regex because it spans multiple elements.
    # So we'll use a python loop to parse and replace.
    import xml.etree.ElementTree as ET
    
    # We will just do textual replacement for the known items in Band 4 and Band 5
    # Let's write a quick script that uses xml.etree to safely manipulate the tree
    pass

import xml.etree.ElementTree as ET
ET.register_namespace('', 'http://jasperreports.sourceforge.net/jasperreports')
ET.register_namespace('xsi', 'http://www.w3.org/2001/XMLSchema-instance')

tree = ET.parse(jrxml_file)
root = tree.getroot()

# The namespace
ns = {'jr': 'http://jasperreports.sourceforge.net/jasperreports'}

# We want to find pairs of <staticText> and <textField> that are positioned on the same Y coordinate
# and where the staticText has width=15 and text is like "a.", "a)", etc.

for band in root.findall('.//jr:band', ns):
    elements_by_y = {}
    
    for child in band:
        if child.tag.endswith('staticText') or child.tag.endswith('textField'):
            reportElement = child.find('jr:reportElement', ns)
            if reportElement is not None:
                y = reportElement.get('y')
                if y not in elements_by_y:
                    elements_by_y[y] = []
                elements_by_y[y].append(child)
                
    # Now check for pairs at the same Y
    for y, elements in elements_by_y.items():
        if len(elements) == 2:
            el1, el2 = elements
            
            # Identify which is the letter (staticText with width 15) and which is the text (textField)
            letter_el = None
            text_el = None
            
            for el in [el1, el2]:
                if el.tag.endswith('staticText') and el.find('jr:reportElement', ns).get('width') == '15':
                    letter_el = el
                elif el.tag.endswith('textField'):
                    text_el = el
                    
            if letter_el is not None and text_el is not None:
                # Merge them!
                letter_text = letter_el.find('jr:text', ns).text
                expr_el = text_el.find('jr:textFieldExpression', ns)
                expr_text = expr_el.text
                
                # Combine: '"a. " + ' + original_expr
                # If expr_text starts with '"', we can insert it inside, but the safest is concatenation
                if expr_text.startswith('"') and expr_text.endswith('"') and '"+' not in expr_text and '+"' not in expr_text:
                    # simple string
                    new_expr = f'"{letter_text} ' + expr_text[1:]
                else:
                    new_expr = f'"{letter_text} " + ({expr_text})'
                    
                expr_el.text = new_expr
                
                # Update text_el X and width to absorb the letter
                t_re = text_el.find('jr:reportElement', ns)
                l_re = letter_el.find('jr:reportElement', ns)
                
                new_x = l_re.get('x')
                old_width = int(t_re.get('width'))
                new_width = old_width + 15
                
                t_re.set('x', new_x)
                t_re.set('width', str(new_width))
                
                # Remove letter_el from band
                band.remove(letter_el)
                print(f"Merged {letter_text} at y={y}")

# Also fix the checkboxes which have a textField (width 15) next to a textField (text)
for band in root.findall('.//jr:band', ns):
    elements_by_y = {}
    
    for child in band:
        if child.tag.endswith('textField'):
            reportElement = child.find('jr:reportElement', ns)
            if reportElement is not None:
                y = reportElement.get('y')
                if y not in elements_by_y:
                    elements_by_y[y] = []
                elements_by_y[y].append(child)
                
    for y, elements in elements_by_y.items():
        if len(elements) == 2:
            el1, el2 = elements
            
            cb_el = None
            text_el = None
            
            for el in [el1, el2]:
                if el.find('jr:reportElement', ns).get('width') == '15':
                    cb_el = el
                else:
                    text_el = el
                    
            if cb_el is not None and text_el is not None:
                # Merge checkbox and text
                cb_expr = cb_el.find('jr:textFieldExpression', ns).text
                text_expr = text_el.find('jr:textFieldExpression', ns).text
                
                new_expr = f'({cb_expr}) + " " + ({text_expr})'
                
                text_el.find('jr:textFieldExpression', ns).text = new_expr
                
                t_re = text_el.find('jr:reportElement', ns)
                c_re = cb_el.find('jr:reportElement', ns)
                
                t_re.set('x', c_re.get('x'))
                t_re.set('width', str(int(t_re.get('width')) + 15))
                
                band.remove(cb_el)
                print(f"Merged checkbox at y={y}")

# Because xml.etree writes tags with prefixes like <ns0:jasperReport>, we need to fix it.
xml_str = ET.tostring(root, encoding='utf-8').decode('utf-8')
xml_str = xml_str.replace('ns0:', '')
xml_str = xml_str.replace(':ns0', '')
xml_str = '<?xml version="1.0" encoding="UTF-8"?>\n' + xml_str

# Hack to fix CDATA lost in xml.etree
# We know where CDATA is needed: inside <text>, <textFieldExpression>, <queryString>
# A simple regex to wrap expressions in CDATA if they aren't already
xml_str = re.sub(r'<text>(.*?)</text>', r'<text><![CDATA[\1]]></text>', xml_str)
xml_str = re.sub(r'<textFieldExpression>(.*?)</textFieldExpression>', r'<textFieldExpression><![CDATA[\1]]></textFieldExpression>', xml_str)
xml_str = re.sub(r'<queryString>(.*?)</queryString>', r'<queryString><![CDATA[\1]]></queryString>', xml_str, flags=re.DOTALL)

# Also fix the `&amp;` inside CDATA which ET.tostring might have escaped back
xml_str = xml_str.replace('&amp;', '&')
xml_str = xml_str.replace('&lt;', '<')
xml_str = xml_str.replace('&gt;', '>')
xml_str = xml_str.replace('&quot;', '"')
# But wait, python's ET.tostring escapes <, > and & which messes up java code inside expressions!
# That's why xml.etree is dangerous for JasperReports.
