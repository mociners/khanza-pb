import javax.swing.*;
import javax.swing.text.*;
public class TestPaste {
    public static void main(String[] args) throws Exception {
        JTextArea area = new JTextArea();
        area.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                int length = 2000;
                int x = super.getLength();
                if (x < length) {
                    String filtered = str.replaceAll("'", "").replaceAll("\\\\", "");
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        });
        area.getDocument().insertString(0, "Pasien rujukan bidan BPM/PKM dengan kehamilan ke… riwayat keguguran tidak ada/.. kali, Riwayat persalinan SC.. tahun yang lalu. Mules belum ada,  keluar air-air (-), lender darah(-)", null);
        System.out.println("Length after paste: " + area.getText().length());
        area.getDocument().insertString(area.getText().length(), "A", null);
        System.out.println("Length after typing A: " + area.getText().length());
        area.getDocument().remove(area.getText().length()-1, 1);
        System.out.println("Length after delete: " + area.getText().length());
        area.getDocument().insertString(area.getText().length(), "B", null);
        System.out.println("Length after typing B: " + area.getText().length());
    }
}
