import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Replace emptTeks()
emptTeks_old = """    public void emptTeks() {
        Masuk1.setText("");
        Masuk2.setText("");
        Masuk3.setText("");
        Masuk4.setText("");
        Masuk5.setText("");
        Masuk6.setText("");
        JumlahMasuk.setText("0");
        Keluar1.setText("");
        Keluar2.setText("");
        Keluar3.setText("");
        Keluar4.setText("");
        Keluar5.setText("");
        JumlahKeluar.setText("0");
        BC.setText("0");
        TNoRw.requestFocus();
    }"""

emptTeks_new = """    public void emptTeks() {
        Tanggal.setDate(new java.util.Date());
        chkJam.setSelected(true);
        TNadi.setText("");
        TRespirasi.setText("");
        TSuhu.setText("");
        TTensi.setText("");
        TBB.setText("");
        TTB.setText("");
        TDiet.setText("");
        TKodeInfus.setText("");
        TInterval.setText("");
        Masuk1.setText("");
        Masuk2.setText("");
        Masuk3.setText("");
        Masuk4.setText("");
        Masuk5.setText("");
        Masuk6.setText("");
        JumlahMasuk.setText("0");
        TInput24.setText("0");
        Keluar1.setText("");
        Keluar2.setText("");
        Keluar3.setText("");
        Keluar4.setText("");
        Keluar5.setText("");
        JumlahKeluar.setText("0");
        TOutput24.setText("0");
        TBalance24.setText("0");
        TNoRw.requestFocus();
    }"""
content = content.replace(emptTeks_old, emptTeks_new)


# 2. Add FocusTraversalPolicy at the end of constructor
focus_policy = """        FormInput.setFocusTraversalPolicy(new java.awt.FocusTraversalPolicy() {
            java.util.List<java.awt.Component> order = java.util.Arrays.asList(
                TNoRw, Tanggal, CmbJam, CmbMnt, CmbDtk, chkJam, KdPetugas, BtnDokter,
                TNadi, TRespirasi, TSuhu, TTensi, TBB, TTB, TDiet, TKodeInfus, TInterval,
                Masuk1, Masuk2, Masuk3, Masuk4, Masuk5, Masuk6,
                Keluar1, Keluar2, Keluar3, Keluar4, Keluar5,
                BtnSimpan, BtnBatal, BtnEdit, BtnHapus, BtnPrint, BtnKeluar
            );
            public java.awt.Component getComponentAfter(java.awt.Container focusCycleRoot, java.awt.Component aComponent) {
                int idx = (order.indexOf(aComponent) + 1) % order.size();
                return order.get(idx);
            }
            public java.awt.Component getComponentBefore(java.awt.Container focusCycleRoot, java.awt.Component aComponent) {
                int idx = order.indexOf(aComponent) - 1;
                if (idx < 0) idx = order.size() - 1;
                return order.get(idx);
            }
            public java.awt.Component getDefaultComponent(java.awt.Container focusCycleRoot) {
                return order.get(0);
            }
            public java.awt.Component getLastComponent(java.awt.Container focusCycleRoot) {
                return order.get(order.size()-1);
            }
            public java.awt.Component getFirstComponent(java.awt.Container focusCycleRoot) {
                return order.get(0);
            }
        });
        FormInput.setFocusCycleRoot(true);
"""

# Insert right before the end of the constructor
# The constructor ends around:
#        Document doc = kit.createDefaultDocument();
#        LoadHTML.setDocument(doc);
#
#
# //        ChkAccor.setSelected(false);
# //        isMenu();
#     }

content = content.replace('//        isMenu();\n    }', '//        isMenu();\n' + focus_policy + '    }')

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)

