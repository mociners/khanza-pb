import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Variables
vars = """
    private widget.Label LDiagnosa;
    private widget.TextBox TDiagnosa;
    private widget.Label LKamar;
    private widget.TextBox TKamar;
"""
content = re.sub(r'(private widget\.Label jLabel10;)', vars + r'\1', content)

# 2. Instantiations
insts = """
        LDiagnosa = new widget.Label();
        TDiagnosa = new widget.TextBox();
        LKamar = new widget.Label();
        TKamar = new widget.TextBox();
"""
content = content.replace("jLabel10 = new widget.Label();", insts + "\n        jLabel10 = new widget.Label();")

# 3. Setup and Add
setup = """
        LDiagnosa.setText("Diagnosa :");
        LDiagnosa.setName("LDiagnosa");
        FormInput.add(LDiagnosa);
        LDiagnosa.setBounds(0, 40, 70, 23);
        
        TDiagnosa.setEditable(false);
        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa");
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(74, 40, 330, 23);
        
        LKamar.setText("Kamar :");
        LKamar.setName("LKamar");
        FormInput.add(LKamar);
        LKamar.setBounds(410, 40, 60, 23);
        
        TKamar.setEditable(false);
        TKamar.setHighlighter(null);
        TKamar.setName("TKamar");
        FormInput.add(TKamar);
        TKamar.setBounds(474, 40, 380, 23);
"""
content = content.replace("FormInput.add(jLabel10);", setup + "\n        FormInput.add(jLabel10);")

# 4. Modify isRawat()
new_is_rawat_add = """
        TDiagnosa.setText("");
        TKamar.setText("");
        try {
            ps=koneksi.prepareStatement(
                    "select kamar_inap.diagnosa_awal, bangsal.nm_bangsal from kamar_inap "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TDiagnosa.setText(rs.getString("diagnosa_awal"));
                    TKamar.setText(rs.getString("nm_bangsal"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
"""
content = re.sub(r'(\}\s*)(\}\s*private void emptTeks\(\) \{)', new_is_rawat_add + r'\1\2', content)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
