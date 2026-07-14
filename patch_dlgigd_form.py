import re

with open('src/simrskhanza/DlgIGD.form', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Add MnPenilaianAwalKeperawatanPonek after MnPenilaianAwalKeperawatanIGD
xml_menu_ponek = """
                <MenuItem class="javax.swing.JMenuItem" name="MnPenilaianAwalKeperawatanPonek">
                  <Properties>
                    <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                      <Color blue="fe" green="ff" red="ff" type="rgb"/>
                    </Property>
                    <Property name="font" type="java.awt.Font" editor="org.netbeans.beaninfo.editors.FontEditor">
                      <Font name="Tahoma" size="11" style="0"/>
                    </Property>
                    <Property name="foreground" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                      <Color blue="32" green="32" red="32" type="rgb"/>
                    </Property>
                    <Property name="icon" type="javax.swing.Icon" editor="org.netbeans.modules.form.editors2.IconEditor">
                      <Image iconType="3" name="/picture/category.png"/>
                    </Property>
                    <Property name="text" type="java.lang.String" value="Penilaian Awal Keperawatan Ponek"/>
                    <Property name="horizontalAlignment" type="int" value="2"/>
                    <Property name="horizontalTextPosition" type="int" value="4"/>
                    <Property name="name" type="java.lang.String" value="MnPenilaianAwalKeperawatanPonek" noResource="true"/>
                    <Property name="preferredSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                      <Dimension value="[230, 26]"/>
                    </Property>
                  </Properties>
                  <Events>
                    <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="MnPenilaianAwalKeperawatanPonekActionPerformed"/>
                  </Events>
                </MenuItem>"""

# find where MnPenilaianAwalKeperawatanIGD ends (</MenuItem>)
match = re.search(r'(<MenuItem class="javax\.swing\.JMenuItem" name="MnPenilaianAwalKeperawatanIGD">.*?</MenuItem>)', content, re.DOTALL)
if match and "name=\"MnPenilaianAwalKeperawatanPonek\"" not in content:
    content = content[:match.end()] + xml_menu_ponek + content[match.end():]

# 2. Add BtnPonek before BtnPrw in panelGlass7
xml_btn_ponek = """
                <Component class="widget.Button" name="BtnPonek">
                  <Properties>
                    <Property name="icon" type="javax.swing.Icon" editor="org.netbeans.modules.form.editors2.IconEditor">
                      <Image iconType="3" name="/picture/addressbook-add24.png"/>
                    </Property>
                    <Property name="mnemonic" type="int" value="80"/>
                    <Property name="text" type="java.lang.String" value="Asesmen Ponek"/>
                    <Property name="toolTipText" type="java.lang.String" value="Alt+P"/>
                    <Property name="autoscrolls" type="boolean" value="true"/>
                    <Property name="name" type="java.lang.String" value="BtnPonek" noResource="true"/>
                    <Property name="preferredSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                      <Dimension value="[140, 30]"/>
                    </Property>
                  </Properties>
                  <Events>
                    <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="BtnPonekActionPerformed"/>
                    <EventHandler event="keyPressed" listener="java.awt.event.KeyListener" parameters="java.awt.event.KeyEvent" handler="BtnPonekKeyPressed"/>
                  </Events>
                </Component>"""

match2 = re.search(r'(<Component class="widget\.Button" name="BtnPrw">)', content, re.DOTALL)
if match2 and "name=\"BtnPonek\"" not in content:
    content = content[:match2.start()] + xml_btn_ponek + "\n" + content[match2.start():]

with open('src/simrskhanza/DlgIGD.form', 'w', encoding='utf-8') as f:
    f.write(content)

print("Patching DlgIGD.form complete!")
