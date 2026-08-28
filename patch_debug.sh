#!/bin/bash
sed -i '/if (hasData) {/i \                    if (!hasData && chkLaporanOperasi.isSelected()) {\n                        htmlContent.append("<br><h2>DEBUG: SQL KOSONG UNTUK " + norawat + "</h2>");\n                    }' src/rekammedis/RMGenerateKlaim.java
