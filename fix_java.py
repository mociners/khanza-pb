import re

file_path = "/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"

with open(file_path, 'r') as f:
    java_code = f.read()

# 1. Variables
if "JPopupMenu jPopupMenu1" not in java_code:
    java_code = java_code.replace("private widget.Label label11;", "private widget.Label label11;\n    private javax.swing.JPopupMenu jPopupMenu1;\n    private javax.swing.JMenuItem MnCetak;")

# 2. Init components
init_popup = """        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetak = new javax.swing.JMenuItem();
        jPopupMenu1.setName("jPopupMenu1");
        
        MnCetak.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnCetak.setForeground(new java.awt.Color(50, 50, 50));
        MnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        MnCetak.setText("Cetak Asesmen Keperawatan");
        MnCetak.setName("MnCetak");
        MnCetak.setPreferredSize(new java.awt.Dimension(250, 26));
        jPopupMenu1.add(MnCetak);
        
        tbObat.setComponentPopupMenu(jPopupMenu1);
        
        MnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tbObat.getSelectedRow()<= -1){
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau dicetak...!!!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanapDewasa.jasper","report","::[ Laporan Penilaian Awal Keperawatan Dewasa ]::",
                        "select * from penilaian_awal_keperawatan_ranap_dewasa where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
            private void setCursor(Cursor predefinedCursor) {
                // Wrapper to avoid referring to outer class this
            }
        });
        
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tbObat.getSelectedRow()<= -1){
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau dicetak...!!!!");
                }else{
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanapDewasa.jasper","report","::[ Laporan Penilaian Awal Keperawatan Dewasa ]::",
                        "select * from penilaian_awal_keperawatan_ranap_dewasa where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
                }
            }
        });
        """
if "jPopupMenu1 = new javax.swing.JPopupMenu();" not in java_code:
    # insert before FormInput.add(pAssesmenNyeri);
    # Actually, better place is after table initialization, like around line 1373 before BtnPrint
    # But wait, BtnPrint is initialized at 1373.
    # Let's insert after pack(); around 1383
    pack_pos = java_code.find("pack();\n")
    if pack_pos != -1:
        java_code = java_code[:pack_pos+8] + init_popup + java_code[pack_pos+8:]

# Fix imports if needed
if "import java.util.Map;" not in java_code:
    java_code = java_code.replace("import java.util.Date;", "import java.util.Date;\nimport java.util.Map;\nimport java.util.HashMap;")

with open(file_path, 'w') as f:
    f.write(java_code)

