import xml.etree.ElementTree as ET

tree = ET.parse("report/rptSuratPersetujuanUmum.jrxml")
root = tree.getroot()

# Register namespaces to prevent ns0: prefixes
ET.register_namespace('', "http://jasperreports.sourceforge.net/jasperreports")
ET.register_namespace('xsi', "http://www.w3.org/2001/XMLSchema-instance")

tree.write("report/rptSuratPersetujuanUmum_test.jrxml", encoding="UTF-8", xml_declaration=True)
print("Done")
