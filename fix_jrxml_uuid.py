import os
import re
import uuid

def patch_jrxml_uuid():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # Fix UUID
    valid_uuid_img = str(uuid.uuid4())
    valid_uuid_txt = str(uuid.uuid4())
    jrxml = jrxml.replace('uuid="saksi2-img-1234"', f'uuid="{valid_uuid_img}"')
    jrxml = jrxml.replace('uuid="saksi2-text-1234"', f'uuid="{valid_uuid_txt}"')

    with open(path, "w") as f:
        f.write(jrxml)

if __name__ == "__main__":
    patch_jrxml_uuid()
    print("Fixed UUID in JRXML")
