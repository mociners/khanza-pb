with open("enum_cols.txt") as f:
    cols = [l.strip() for l in f if l.strip()]

out = "A L T E R TABLE penilaian_awal_keperawatan_ranap_dewasa \n"
for c in cols[:-1]:
    out += f"  MODIFY COLUMN {c} VARCHAR(100),\n"
out += f"  MODIFY COLUMN {cols[-1]} VARCHAR(100);\n"

with open("output_sql.txt", "w") as f:
    f.write(out.replace("A L T E R", "ALTER"))
