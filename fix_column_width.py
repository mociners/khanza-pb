import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

column_width_logic = """        this.tbData.setPreferredScrollableViewportSize(new Dimension(500, 500));
        this.tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 225; i++) {
            javax.swing.table.TableColumn column = this.tbData.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105); // No Rawat
            } else if (i == 1) {
                column.setPreferredWidth(70); // No RM
            } else if (i == 2) {
                column.setPreferredWidth(150); // Nama Pasien
            } else if (i == 3) {
                column.setPreferredWidth(65); // Tgl Lahir
            } else if (i == 4) {
                column.setPreferredWidth(25); // JK
            } else if (i == 5) {
                column.setPreferredWidth(80); // Kode Dokter
            } else if (i == 6) {
                column.setPreferredWidth(150); // Nama Dokter
            } else if (i == 7) {
                column.setPreferredWidth(115); // Tanggal
            } else {
                column.setPreferredWidth(100);
            }
        }
"""

content = content.replace('this.tbData.setPreferredScrollableViewportSize(new Dimension(500, 500));\n', column_width_logic)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)
