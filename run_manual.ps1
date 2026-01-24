$jars = Get-ChildItem "lib" -Filter *.jar
$classpath = "lib/commons-codec-1.12.jar;" + ($jars.FullName -join ";")
$classpath = "build/classes;" + $classpath

# Clean build directory (already done, but good to keep)
if (Test-Path "build/classes") {
    Remove-Item -Recurse -Force "build/classes"
}
New-Item -ItemType Directory -Force -Path "build/classes"

# Find all Java files
$javaFiles = Get-ChildItem -Path "src" -Recurse -Filter *.java
$javaFilePaths = $javaFiles.FullName

# Create a temporary file to list sources (to avoid command line length limits)
$javaFilePaths | Out-File sources.txt -Encoding ASCII

# Compile
Write-Host "Compiling all files..."
javac -cp $classpath -d build/classes -encoding ISO-8859-1 "@sources.txt" 2> errors.txt

if ($LASTEXITCODE -eq 0) {
    Write-Host "Running..."
    java -cp $classpath simrskhanza.SIMRSKhanza
}
else {
    Write-Host "Compilation failed."
}
