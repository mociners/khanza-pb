import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# 1. Comment out TabRawat initializations
content = content.replace('TabRawat = new javax.swing.JTabbedPane();', '// TabRawat = new javax.swing.JTabbedPane();')
content = content.replace('TabRawat.setBackground', '// TabRawat.setBackground')
content = content.replace('TabRawat.setForeground', '// TabRawat.setForeground')
content = content.replace('TabRawat.setFont', '// TabRawat.setFont')
content = content.replace('TabRawat.setName("TabRawat");', '// TabRawat.setName("TabRawat");')
content = content.replace('TabRawat.addMouseListener', '// TabRawat.addMouseListener')

# 2. Fix the adding of internalFrame2
old_add_2 = """        TabRawat.addTab("Input Penilaian", internalFrame2);
        internalFrame2.getAccessibleContext().setAccessibleParent(TabRawat);"""
new_add_2 = """        internalFrame2.setPreferredSize(new java.awt.Dimension(1024, 300));
        internalFrame1.add(internalFrame2, java.awt.BorderLayout.PAGE_START);"""
content = content.replace(old_add_2, new_add_2)

# 3. Fix the adding of internalFrame3
old_add_3 = """        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);"""
new_add_3 = """        internalFrame1.add(internalFrame3, java.awt.BorderLayout.CENTER);"""
content = content.replace(old_add_3, new_add_3)

# 4. Comment out setSelectedIndex
content = content.replace('TabRawat.setSelectedIndex(0);', '// TabRawat.setSelectedIndex(0);')
content = content.replace('TabRawat.setSelectedIndex(1);', '// TabRawat.setSelectedIndex(1);')

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
