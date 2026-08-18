/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Owner
 */
public class batasInput {
    private final int length;
    private PlainDocument filter;

    public batasInput(int length){this.length=length;}

    public PlainDocument getFilter(final JTextField inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                if (str == null) return;
                int x = super.getLength();
                if (x < length) {
                    String filtered = str.toUpperCase().replaceAll("'", "").replaceAll("\\\\", "");
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        };return filter;
    }

    public PlainDocument getFilter(final JTextArea inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                if (str == null) return;
                int x = super.getLength();
                if (x < length) {
                    String filtered = str.toUpperCase().replaceAll("'", "").replaceAll("\\\\", "");
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        };return filter;
    }
  
    public PlainDocument getOnlyAngka(final JTextField inputan) {
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                if (str == null) return;
                StringBuilder buf=new StringBuilder();
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                    if(Character.isDigit(upp[i])){
                        buf.append(upp[i]);
                    }
                }
                int x = super.getLength();
                if (x < length) {
                    String filtered = buf.toString();
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        };return filter;
    }

    public PlainDocument getKata(final JTextField inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                if (str == null) return;
                int x = super.getLength();
                if (x < length) {
                    String filtered = str.replaceAll("'", "").replaceAll("\\\\", "");
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        };return filter;
    }
   
    public PlainDocument getKata(final JTextArea inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                if (str == null) return;
                int x = super.getLength();
                if (x < length) {
                    String filtered = str.replaceAll("'", "").replaceAll("\\\\", "");
                    if (x + filtered.length() > length) {
                        filtered = filtered.substring(0, length - x);
                    }
                    super.insertString(offs, filtered, a);
                }
            }
        };return filter;
    }
}
