package fungsi;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class WarnaTableKasirRalan extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row % 2 == 1) {
            component.setBackground(new Color(255, 244, 244));
            component.setForeground(new Color(50, 50, 50));
        } else {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        }

        if (table.getValueAt(row, 19) != null && table.getValueAt(row, 20).toString().equals("Sudah Bayar")) {
            component.setBackground(new Color(50, 50, 50));
            component.setForeground(new Color(255, 255, 255));
        } else if (table.getColumnCount() >= 26 && table.getValueAt(row, 25) != null && table.getValueAt(row, 25).toString().equals("Sudah")) {
            component.setBackground(new Color(102, 51, 153));
            component.setForeground(new Color(255, 255, 255));
        } else if (table.getValueAt(row, 23) != null && table.getValueAt(row, 24).toString().equals("Sudah")) {
            component.setBackground(new Color(0, 0, 255));
            component.setForeground(new Color(255, 255, 255));
        } else if (table.getValueAt(row, 9) != null && table.getValueAt(row, 9).toString().equals("Sudah")) {
            component.setBackground(new Color(255, 0, 0));
            component.setForeground(new Color(255, 255, 255));
        } else if (table.getValueAt(row, 9) != null && table.getValueAt(row, 9).toString().equals("Batal")) {
            component.setBackground(new Color(255, 255, 0));
            component.setForeground(new Color(50, 50, 50));
        } else if (table.getValueAt(row, 9) != null && table.getValueAt(row, 9).toString().equals("Dirujuk")) {
            component.setBackground(new Color(100, 100, 100));
            component.setForeground(new Color(255, 255, 255));
        } else if (table.getValueAt(row, 9) != null && table.getValueAt(row, 9).toString().equals("Dirawat")) {
            component.setBackground(new Color(200, 255, 200));
            component.setForeground(new Color(50, 50, 50));
        } else if (table.getValueAt(row, 9) != null && table.getValueAt(row, 9).toString().equals("TTV")) {
            component.setBackground(new Color(0, 150, 0));
            component.setForeground(new Color(255, 255, 255));
        }

        if (isSelected) {
            component.setBackground(new Color(255, 217, 255));
            component.setForeground(new Color(50, 50, 50));
        }

        return component;
    }
}