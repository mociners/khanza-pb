import re

with open('src/rekammedis/RMDataResumePasienRanap.java', 'r') as f:
    content = f.read()

# 1. Add variable declarations at the bottom
if 'private widget.ComboBox CmbPoli;' not in content:
    content = re.sub(
        r'(    private widget\.ComboBox DIlanjutkan;)',
        r'\1\n    private widget.ComboBox CmbPoli;\n    private widget.Label labelPoli;',
        content
    )

# 2. Add instantiation in initComponents() right after DIlanjutkan
btn_code = """
        labelPoli = new widget.Label();
        labelPoli.setText("Poli :");
        labelPoli.setName("labelPoli"); // NOI18N
        FormInput.add(labelPoli);
        labelPoli.setBounds(390, 1510, 40, 23);

        CmbPoli = new widget.ComboBox();
        CmbPoli.setName("CmbPoli"); // NOI18N
        CmbPoli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbPoliItemStateChanged(evt);
            }
        });
        FormInput.add(CmbPoli);
        CmbPoli.setBounds(435, 1510, 105, 23);
"""

if 'CmbPoli = new widget.ComboBox();' not in content:
    content = content.replace(
        'KetDilanjutkan.setBounds(270, 1510, 270, 23);',
        'KetDilanjutkan.setBounds(270, 1510, 120, 23);\n' + btn_code
    )

# 3. Add ItemStateChanged for DIlanjutkan
# Wait, let's check if DIlanjutkan already has ItemStateChanged in initComponents()
