#!/bin/bash
sed -i 's/\.append(rs2.getString("tindakan"))/.append(sanitize(rs2.getString("tindakan")))/g' src/rekammedis/RMGenerateKlaim.java
sed -i 's/\.append(rs2.getString("komplikasi"))/.append(sanitize(rs2.getString("komplikasi")))/g' src/rekammedis/RMGenerateKlaim.java
sed -i 's/\.append(rs2.getString("diagnosaprabedah"))/.append(sanitize(rs2.getString("diagnosaprabedah")))/g' src/rekammedis/RMGenerateKlaim.java
sed -i 's/\.append(rs2.getString("diagnosapascabedah"))/.append(sanitize(rs2.getString("diagnosapascabedah")))/g' src/rekammedis/RMGenerateKlaim.java
sed -i 's/\.append((rs2.getString("uraian") != null ? rs2.getString("uraian") : "")/\.append((sanitize(rs2.getString("uraian")) != null ? sanitize(rs2.getString("uraian")) : "")/g' src/rekammedis/RMGenerateKlaim.java
