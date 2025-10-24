@echo off
echo Menjalankan SIMRS Khanza tanpa iText lama untuk menghindari konflik FopGlyphProcessor...

set CLASSPATH=lib\openpdf-1.3.32.jar;lib\jasperreports-6.21.3.jar;lib\jasperreports-fonts-6.21.3.jar;lib\jasperreports-functions-6.21.3.jar;lib\jasperreports-chart-themes-6.21.3.jar;lib\jasperreports-chart-customizers-6.21.3.jar;lib\jasperreports-custom-visualization-6.21.3.jar;lib\jasperreports-javaflow-6.21.3.jar;lib\jasperreports-metadata-6.21.3.jar;lib\jasperreports-annotation-processors-6.21.3.jar;lib\jasperreports-json.jar;lib\jasperreports-jtidy-r938.jar;lib\jasperreports-htmlcomponent-5.0.1.jar;lib\jasperreports-core-renderer.jar

REM Tambahkan semua JAR files dari lib kecuali iText lama
for %%f in (lib\*.jar) do (
    if not "%%f"=="lib\itext-1.3.1.jar" (
        if not "%%f"=="lib\iText-2.1.7.js2.jar" (
            if not "%%f"=="lib\iTextAsian.jar" (
                set CLASSPATH=!CLASSPATH!;%%f
            )
        )
    )
)

set CLASSPATH=%CLASSPATH%;build\classes

java simrskhanza.SIMRSKhanza
