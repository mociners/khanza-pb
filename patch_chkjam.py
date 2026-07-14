import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Change type of chkJam
content = content.replace('private widget.Button chkJam;', 'private widget.CekBox chkJam;')
content = content.replace('chkJam = new widget.Button();', 'chkJam = new widget.CekBox();')

# 2. Change chkJam setup
old_chk = """        chkJam.setText("Jam");
        chkJam.setName("chkJam");
        chkJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbJam.setSelectedItem(jamNow.format(new Date()).substring(0, 2));
                CmbMnt.setSelectedItem(jamNow.format(new Date()).substring(3, 5));
                CmbDtk.setSelectedItem(jamNow.format(new Date()).substring(6, 8));
            }
        });
        FormInput.add(chkJam);
        chkJam.setBounds(370, 70, 60, 23);"""

new_chk = """        chkJam.setText("Jam");
        chkJam.setName("chkJam");
        chkJam.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chkJam.setBorderPainted(true);
        chkJam.setBorderPaintedFlat(true);
        chkJam.setSelected(true);
        FormInput.add(chkJam);
        chkJam.setBounds(370, 70, 60, 23);"""
content = content.replace(old_chk, new_chk)

# 3. Add jam() method
jam_method = """    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(chkJam.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(chkJam.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMnt.getSelectedIndex();
                    nilai_detik =CmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                CmbJam.setSelectedItem(jam);
                CmbMnt.setSelectedItem(menit);
                CmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
"""
content = re.sub(r'(    private void isRawat\(\) \{)', jam_method + r'\n\1', content)

# 4. Call jam() in constructor
content = content.replace("initComponents();\n", "initComponents();\n        jam();\n")

# 5. Remove manual setting of Jam from emptTeks()
empt_inject = """        Tanggal.setDate(new Date());
        chkJam.setSelected(true);"""
content = re.sub(r'Tanggal\.setDate\(new Date\(\)\);\s+CmbJam\.setSelectedItem\(jamNow\.format\(new Date\(\)\)\.substring\(0, 2\)\);\s+CmbMnt\.setSelectedItem\(jamNow\.format\(new Date\(\)\)\.substring\(3, 5\)\);\s+CmbDtk\.setSelectedItem\(jamNow\.format\(new Date\(\)\)\.substring\(6, 8\)\);', empt_inject, content)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
