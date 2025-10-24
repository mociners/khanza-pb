@echo off
echo Memperbaiki konflik iText dengan JasperReports...

REM Backup iText lama
if not exist "lib\backup" mkdir "lib\backup"
move "lib\itext-1.3.1.jar" "lib\backup\itext-1.3.1.jar.bak" 2>nul
move "lib\iText-2.1.7.js2.jar" "lib\backup\iText-2.1.7.js2.jar.bak" 2>nul
move "lib\iTextAsian.jar" "lib\backup\iTextAsian.jar.bak" 2>nul

echo iText lama telah di-backup ke lib\backup\
echo Sekarang jalankan aplikasi dengan: java -cp "lib/*;build/classes" simrskhanza.SIMRSKhanza
pause
