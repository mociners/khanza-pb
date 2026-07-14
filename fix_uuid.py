import re
import uuid

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

# Find all uuid="..."
# Replace with valid UUIDs
def uuid_replacer(match):
    return 'uuid="' + str(uuid.uuid4()) + '"'

content = re.sub(r'uuid="[^"]+"', uuid_replacer, content)

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(content)
