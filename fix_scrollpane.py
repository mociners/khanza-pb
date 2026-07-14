import re

def process_java():
    filepath = 'src/surat/SuratPersetujuanUmum.java'
    with open(filepath, 'r') as f:
        content = f.read()
    
    # 1. Add scrollInput declaration
    content = content.replace('private javax.swing.JPanel PanelInput;', 'private javax.swing.JPanel PanelInput;\n    private widget.ScrollPane scrollInput;')
    
    # 2. Add scrollInput instantiation
    content = content.replace('PanelInput = new javax.swing.JPanel();', 'PanelInput = new javax.swing.JPanel();\n        scrollInput = new widget.ScrollPane();')
    
    # 3. Add scrollInput to PanelInput
    old_add = 'PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);'
    new_add = """        scrollInput.setName("scrollInput");
        scrollInput.setOpaque(true);
        scrollInput.setViewportView(FormInput);
        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);"""
    content = content.replace(old_add, new_add)
    
    # 4. Modify isForm()
    old_isform = """    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 710));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }"""
    
    new_isform = """    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, Math.max(150, this.getHeight() - 122)));
            scrollInput.setVisible(true);
            ChkInput.setVisible(true);
            PanelInput.revalidate();
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            scrollInput.setVisible(false);
            ChkInput.setVisible(true);
            PanelInput.revalidate();
        }
    }"""
    content = content.replace(old_isform, new_isform)
    
    # 5. Add ComponentListener
    # We can inject it in initComponents just before pack() or right after `addWindowListener`
    window_listener_idx = content.find('addWindowListener(new java.awt.event.WindowAdapter() {')
    if window_listener_idx != -1:
        component_listener = """        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (ChkInput.isSelected()) {
                    PanelInput.setPreferredSize(new Dimension(WIDTH, Math.max(150, SuratPersetujuanUmum.this.getHeight() - 122)));
                    PanelInput.revalidate();
                }
            }
        });
        
        """
        content = content[:window_listener_idx] + component_listener + content[window_listener_idx:]
        
    with open(filepath, 'w') as f:
        f.write(content)

if __name__ == '__main__':
    process_java()
