#!/bin/bash
# Generate classpath with ':' separator
CLASSPATH="build/classes"
for jar in lib/*.jar; do
    CLASSPATH="$CLASSPATH:$jar"
done

# Clean build directory
rm -rf build/classes
mkdir -p build/classes

# Find all Java files
find src -name "*.java" > sources.txt

# Compile
echo "Compiling all files..."
javac -cp "$CLASSPATH" -d build/classes -encoding ISO-8859-1 @sources.txt 2> errors.txt

if [ $? -eq 0 ]; then
    echo "Compilation successful. Updating jar files in dist/..."
    UPDATED=false
    for jarfile in dist/khanza.jar dist/SIMRSKhanza.jar; do
        if [ -f "$jarfile" ]; then
            echo "Updating $jarfile..."
            jar uf "$jarfile" -C build/classes .
            if [ $? -eq 0 ]; then
                echo "Successfully updated $jarfile."
                UPDATED=true
            else
                echo "Failed to update $jarfile."
            fi
        fi
    done
    if [ "$UPDATED" = true ]; then
        echo "Jar update complete."
    else
        echo "No matching jar files found in dist/ to update. Only compiled class files in build/classes."
    fi
else
    echo "Compilation failed. Check errors.txt."
fi
