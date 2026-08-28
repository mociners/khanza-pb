import javax.swing.*;
import java.awt.*;
public class TestZOrder {
    public static void main(String[] args) {
        JPanel panel = new JPanel(null);
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();
        panel.add(t1); // 0
        panel.add(t2); // 1
        panel.add(t3); // 2
        try {
            Component[] comps = {t3, t1, t2};
            int z = 0;
            for(int i = 0; i < comps.length; i++) {
                panel.setComponentZOrder(comps[i], z + i);
            }
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
