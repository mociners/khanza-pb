import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# 1. Add variable declarations
var_decl = """
    private widget.InternalFrame internalFrame3;
    private widget.panelisi panelGlass9;
    private Tanggal DTPCari1, DTPCari2;
    private TextBox TCari;
    private Button BtnCari, BtnAll;
    private Label LCount, jLabel19, jLabel21, jLabel6, jLabel7;
"""
content = re.sub(r'(private JTable tbData;)', r'\1' + var_decl, content)

# 2. Replace Tab 2 UI Construction
old_ui = r'JPanel panelData = new JPanel\(\);\s*panelData\.setLayout\(new BorderLayout\(\)\);\s*this\.tabMode = new DefaultTableModel\(null, new Object\[\]\{"No Rawat", "Tanggal".*?\};.*?this\.TabRawat\.addTab\("Data Penilaian", panelData\);'

# We need the full array of headers, but we can just parse it from the old one
m = re.search(r'this\.tabMode = new DefaultTableModel\(null, new Object\[\]\{(.*?)\}\)', content, re.DOTALL)
old_headers_str = m.group(1)
# Prepend the new headers
new_headers_str = '"No Rawat", "No RM", "Nama Pasien", "Tgl Lahir", "JK", "Kode Dokter", "Nama Dokter", ' + old_headers_str.replace('"No Rawat", ', '').replace('"Kd Dokter", ', '')

new_ui = f"""
        this.tabMode = new DefaultTableModel(null, new Object[]{{{new_headers_str}}}){{
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {{
                return false;
            }}
        }};
        this.tbData = new widget.Table();
        this.tbData.setModel(this.tabMode);
        this.tbData.setPreferredScrollableViewportSize(new Dimension(500, 500));
        this.tbData.addMouseListener(new java.awt.event.MouseAdapter() {{
            public void mouseClicked(java.awt.event.MouseEvent evt) {{
                if (tbData.getSelectedRow() != -1) {{
                    getData();
                }}
            }}
        }});
        this.tbData.addKeyListener(new java.awt.event.KeyAdapter() {{
            public void keyPressed(java.awt.event.KeyEvent evt) {{
                if (tbData.getSelectedRow() != -1) {{
                    getData();
                }}
            }}
        }});
        this.scrollData = new widget.ScrollPane();
        this.scrollData.setOpaque(true);
        this.scrollData.setViewportView(this.tbData);

        this.internalFrame3 = new widget.InternalFrame();
        this.internalFrame3.setBorder(null);
        this.internalFrame3.setLayout(new BorderLayout());
        this.internalFrame3.add(this.scrollData, BorderLayout.CENTER);

        this.panelGlass9 = new widget.panelisi();
        this.panelGlass9.setPreferredSize(new Dimension(44, 44));
        this.panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        this.jLabel19 = new widget.Label();
        this.jLabel19.setText("Tgl.Asuhan :");
        this.jLabel19.setPreferredSize(new Dimension(70, 23));
        this.panelGlass9.add(this.jLabel19);

        this.DTPCari1 = new widget.Tanggal();
        this.DTPCari1.setPreferredSize(new Dimension(90, 23));
        this.panelGlass9.add(this.DTPCari1);

        this.jLabel21 = new widget.Label();
        this.jLabel21.setText("s.d.");
        this.jLabel21.setPreferredSize(new Dimension(23, 23));
        this.panelGlass9.add(this.jLabel21);

        this.DTPCari2 = new widget.Tanggal();
        this.DTPCari2.setPreferredSize(new Dimension(90, 23));
        this.panelGlass9.add(this.DTPCari2);

        this.jLabel6 = new widget.Label();
        this.jLabel6.setText("Key Word :");
        this.jLabel6.setPreferredSize(new Dimension(70, 23));
        this.panelGlass9.add(this.jLabel6);

        this.TCari = new widget.TextBox();
        this.TCari.setPreferredSize(new Dimension(200, 23));
        this.TCari.addKeyListener(new java.awt.event.KeyAdapter() {{
            public void keyPressed(java.awt.event.KeyEvent evt) {{
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){{
                    BtnCariActionPerformed(null);
                }}
            }}
        }});
        this.panelGlass9.add(this.TCari);

        this.BtnCari = new widget.Button();
        this.BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        this.BtnCari.setText("Tampilkan Data");
        this.BtnCari.setPreferredSize(new Dimension(130, 23));
        this.BtnCari.addActionListener(new java.awt.event.ActionListener() {{
            public void actionPerformed(java.awt.event.ActionEvent evt) {{
                BtnCariActionPerformed(evt);
            }}
        }});
        this.panelGlass9.add(this.BtnCari);

        this.BtnAll = new widget.Button();
        this.BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        this.BtnAll.setText("Semua");
        this.BtnAll.setPreferredSize(new Dimension(100, 23));
        this.BtnAll.addActionListener(new java.awt.event.ActionListener() {{
            public void actionPerformed(java.awt.event.ActionEvent evt) {{
                BtnAllActionPerformed(evt);
            }}
        }});
        this.panelGlass9.add(this.BtnAll);

        this.jLabel7 = new widget.Label();
        this.jLabel7.setText("Record :");
        this.jLabel7.setPreferredSize(new Dimension(65, 23));
        this.panelGlass9.add(this.jLabel7);

        this.LCount = new widget.Label();
        this.LCount.setText("0");
        this.LCount.setPreferredSize(new Dimension(50, 23));
        this.panelGlass9.add(this.LCount);

        this.internalFrame3.add(this.panelGlass9, BorderLayout.PAGE_END);
        this.TabRawat.addTab("Data Penilaian", this.internalFrame3);
"""
content = re.sub(old_ui, new_ui, content, flags=re.DOTALL)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

