import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Change declaration
content = content.replace('private widget.TextBox TKodeInfus;', 'private widget.ComboBox TKodeInfus;')

# 2. Change instantiation
content = content.replace('TKodeInfus = new widget.TextBox();', 'TKodeInfus = new widget.ComboBox();')

# 3. Add model when adding to FormInput
old_add = """        TKodeInfus.setBounds(680, 350, 180, 23);
        FormInput.add(TKodeInfus);"""
new_add = """        TKodeInfus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TKodeInfus.setName("TKodeInfus"); // NOI18N
        TKodeInfus.setBounds(680, 350, 180, 23);
        FormInput.add(TKodeInfus);"""
content = content.replace(old_add, new_add)

# 4. Change getText() to getSelectedItem().toString()
content = content.replace('TKodeInfus.getText()', 'TKodeInfus.getSelectedItem().toString()')

# 5. Change setText("") to setSelectedIndex(0)
content = content.replace('TKodeInfus.setText("");', 'TKodeInfus.setSelectedIndex(0);')

# 6. Change setText(...) from DB to setSelectedItem(...)
old_set_db = 'TKodeInfus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());'
new_set_db = 'TKodeInfus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());'
content = content.replace(old_set_db, new_set_db)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
