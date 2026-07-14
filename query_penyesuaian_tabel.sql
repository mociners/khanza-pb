ALTER TABLE penilaian_medis_ranap_kandungan1 
ADD COLUMN ketuban varchar(50) AFTER kepala,
ADD COLUMN jenis_kelamin_bayi varchar(50) AFTER ketuban,
ADD COLUMN plasenta varchar(50) AFTER jenis_kelamin_bayi;
