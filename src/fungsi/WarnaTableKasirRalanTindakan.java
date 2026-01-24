package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableKasirRalanTindakan extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object statusTindakan = table.getModel().getValueAt(row, 23);

        if (isSelected) {
            component.setBackground(new Color(55, 55, 55));
            component.setForeground(new Color(255, 255, 255));
        } else {
            component.setForeground(new Color(50, 50, 50));

            if (statusTindakan != null && statusTindakan.toString().equals("Sudah")) {
                component.setBackground(new Color(255, 255, 153));
            } else {
                if (row % 2 == 1) {
                    component.setBackground(new Color(240, 245, 235));
                } else {
                    component.setBackground(new Color(255, 255, 255));
                }
            }
        }
        return component;
    }
}