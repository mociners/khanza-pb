import re

with open('src/rekammedis/RMDataResumePasienRanap.java', 'r') as f:
    content = f.read()

# Add ItemListener to DIlanjutkan
if 'DIlanjutkanItemStateChanged' not in content:
    content = content.replace(
        '''        DIlanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {''',
        '''        DIlanjutkan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DIlanjutkanItemStateChanged(evt);
            }
        });
        DIlanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {'''
    )

# Add event methods
method_code = """
    private void DIlanjutkanItemStateChanged(java.awt.event.ItemEvent evt) {
        if (DIlanjutkan.getSelectedItem() != null && DIlanjutkan.getSelectedItem().toString().equals("Kembali Ke RS")) {
            labelPoli.setVisible(true);
            CmbPoli.setVisible(true);
            if(CmbPoli.getItemCount() > 0 && CmbPoli.getSelectedItem() != null) {
                KetDilanjutkan.setText(CmbPoli.getSelectedItem().toString());
            }
        } else {
            labelPoli.setVisible(false);
            CmbPoli.setVisible(false);
            KetDilanjutkan.setText("");
        }
    }

    private void CmbPoliItemStateChanged(java.awt.event.ItemEvent evt) {
        if(CmbPoli.getSelectedItem() != null && DIlanjutkan.getSelectedItem() != null && DIlanjutkan.getSelectedItem().toString().equals("Kembali Ke RS")){
            KetDilanjutkan.setText(CmbPoli.getSelectedItem().toString());
        }
    }
    
    private void setPoli() {
        try {
            CmbPoli.removeAllItems();
            java.sql.PreparedStatement ps = koneksi.prepareStatement("select nm_poli from poliklinik order by nm_poli");
            try {
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CmbPoli.addItem(rs.getString("nm_poli"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }
"""

if 'private void setPoli()' not in content:
    content = content.replace(
        '    // Variables declaration - do not modify',
        method_code + '\n    // Variables declaration - do not modify'
    )

with open('src/rekammedis/RMDataResumePasienRanap.java', 'w') as f:
    f.write(content)
