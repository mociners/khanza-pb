import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Variables
vars = """
    private widget.Label LTanggal;
    private widget.Tanggal Tanggal;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMnt;
    private widget.ComboBox CmbDtk;
    private widget.Button chkJam;
"""

content = re.sub(r'(private widget\.Label LDiagnosa;)', vars + r'\1', content)

# 2. Instantiations
insts = """
        LTanggal = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMnt = new widget.ComboBox();
        CmbDtk = new widget.ComboBox();
        chkJam = new widget.Button();
"""
content = content.replace("LDiagnosa = new widget.Label();", insts + "\n        LDiagnosa = new widget.Label();")

# 3. Setup and Add
setup = """
        LTanggal.setText("Tanggal :");
        LTanggal.setName("LTanggal");
        FormInput.add(LTanggal);
        LTanggal.setBounds(0, 70, 70, 23);
        
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal");
        Tanggal.setOpaque(false);
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 70, 90, 23);
        
        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam");
        FormInput.add(CmbJam);
        CmbJam.setBounds(170, 70, 62, 23);
        
        CmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMnt.setName("CmbMnt");
        FormInput.add(CmbMnt);
        CmbMnt.setBounds(236, 70, 62, 23);
        
        CmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDtk.setName("CmbDtk");
        FormInput.add(CmbDtk);
        CmbDtk.setBounds(302, 70, 62, 23);
        
        chkJam.setText("Jam");
        chkJam.setName("chkJam");
        chkJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbJam.setSelectedItem(jamNow.format(new Date()).substring(0, 2));
                CmbMnt.setSelectedItem(jamNow.format(new Date()).substring(3, 5));
                CmbDtk.setSelectedItem(jamNow.format(new Date()).substring(6, 8));
            }
        });
        FormInput.add(chkJam);
        chkJam.setBounds(370, 70, 60, 23);
"""
content = content.replace("FormInput.add(LDiagnosa);", setup + "\n        FormInput.add(LDiagnosa);")

# 4. Modify BtnSimpanActionPerformed and BtnEditActionPerformed
old_simpan = 'TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),TNadi.getText()'
new_simpan = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMnt.getSelectedItem()+":"+CmbDtk.getSelectedItem(),TNadi.getText()'
content = content.replace(old_simpan, new_simpan)

# And for BtnEditActionPerformed
old_edit = 'TNoRw.getText(),tanggalNow.format(new Date()),jamNow.format(new Date()),TNadi.getText()'
new_edit = 'TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMnt.getSelectedItem()+":"+CmbDtk.getSelectedItem(),TNadi.getText()'
content = content.replace(old_edit, new_edit)

# 5. Modify getData() to load date and time back to the fields
get_data_inject = """            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            CmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(0,2));
            CmbMnt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(3,5));
            CmbDtk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().substring(6,8));
"""
content = re.sub(r'(TNadi\.setText\(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),9\)\.toString\(\)\);)', get_data_inject + r'\n            \1', content)

# 6. Default time on form open
empt_inject = """        Tanggal.setDate(new Date());
        CmbJam.setSelectedItem(jamNow.format(new Date()).substring(0, 2));
        CmbMnt.setSelectedItem(jamNow.format(new Date()).substring(3, 5));
        CmbDtk.setSelectedItem(jamNow.format(new Date()).substring(6, 8));
"""
content = re.sub(r'(TNadi\.setText\(""\);)', empt_inject + r'\n        \1', content)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

