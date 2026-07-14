ALTER TABLE `penilaian_medis_ranap_kandungan1`
CHANGE COLUMN `his` `ketuban` varchar(50) NOT NULL,
CHANGE COLUMN `kontraksi` `jenis_kelamin_bayi` varchar(50) NOT NULL,
CHANGE COLUMN `kepala` `plasenta` varchar(200) NOT NULL;
