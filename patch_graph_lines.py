import re

# 1. Update RMTTVBalanceCairan.java to interpolate missing graph values
with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    java_code = f.read()

old_chart_code = """                    for (int c=0; c<12; c++) {
                        int di = startIdx + c;
                        String cat = "C" + c;
                        if (di < dataList.size()) {
                            String[] d = dataList.get(di);
                            try { nadiDataset.addValue(Double.parseDouble(d[6]), "Nadi", cat); } catch(Exception e) { nadiDataset.addValue(null, "Nadi", cat); }
                            try { respDataset.addValue(Double.parseDouble(d[7]), "Respirasi", cat); } catch(Exception e) { respDataset.addValue(null, "Respirasi", cat); }
                            try { suhuDataset.addValue(Double.parseDouble(d[8]), "Suhu", cat); } catch(Exception e) { suhuDataset.addValue(null, "Suhu", cat); }
                        } else {
                            nadiDataset.addValue(null, "Nadi", cat);
                            respDataset.addValue(null, "Respirasi", cat);
                            suhuDataset.addValue(null, "Suhu", cat);
                        }
                    }

                    // Create chart (transparent)
                    org.jfree.chart.plot.CategoryPlot plot = new org.jfree.chart.plot.CategoryPlot();
                    
                    // Renderer for Nadi (Red Square)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendNadi = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendNadi.setSeriesPaint(0, java.awt.Color.RED);
                    rendNadi.setSeriesShape(0, new java.awt.geom.Rectangle2D.Double(-3, -3, 6, 6));
                    plot.setDataset(0, nadiDataset);
                    plot.setRenderer(0, rendNadi);

                    // Renderer for Respirasi (Black Circle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendResp = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendResp.setSeriesPaint(0, java.awt.Color.BLACK);
                    rendResp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));
                    plot.setDataset(1, respDataset);
                    plot.setRenderer(1, rendResp);

                    // Renderer for Suhu (Blue Triangle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendSuhu = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true);
                    rendSuhu.setSeriesPaint(0, java.awt.Color.BLUE);
                    java.awt.geom.GeneralPath triangle = new java.awt.geom.GeneralPath();
                    triangle.moveTo(0, -4); triangle.lineTo(4, 4); triangle.lineTo(-4, 4); triangle.closePath();
                    rendSuhu.setSeriesShape(0, triangle);
                    plot.setDataset(2, suhuDataset);
                    plot.setRenderer(2, rendSuhu);"""

new_chart_code = """                    double[] nadiArr = new double[12];
                    double[] respArr = new double[12];
                    double[] suhuArr = new double[12];
                    final boolean[] nadiValid = new boolean[12];
                    final boolean[] respValid = new boolean[12];
                    final boolean[] suhuValid = new boolean[12];

                    for (int c=0; c<12; c++) {
                        int di = startIdx + c;
                        nadiArr[c] = -1; respArr[c] = -1; suhuArr[c] = -1;
                        if (di < dataList.size()) {
                            String[] d = dataList.get(di);
                            try { nadiArr[c] = Double.parseDouble(d[6]); nadiValid[c] = true; } catch(Exception e) {}
                            try { respArr[c] = Double.parseDouble(d[7]); respValid[c] = true; } catch(Exception e) {}
                            try { suhuArr[c] = Double.parseDouble(d[8]); suhuValid[c] = true; } catch(Exception e) {}
                        }
                    }

                    class Interpolator {
                        void process(double[] arr, boolean[] valid) {
                            int lastValid = -1;
                            for (int c=0; c<12; c++) {
                                if (valid[c]) {
                                    if (lastValid != -1 && lastValid < c - 1) {
                                        double y1 = arr[lastValid];
                                        double y2 = arr[c];
                                        for (int i = lastValid + 1; i < c; i++) {
                                            arr[i] = y1 + (y2 - y1) * (i - lastValid) / (c - lastValid);
                                        }
                                    }
                                    lastValid = c;
                                }
                            }
                        }
                    }
                    Interpolator interp = new Interpolator();
                    interp.process(nadiArr, nadiValid);
                    interp.process(respArr, respValid);
                    interp.process(suhuArr, suhuValid);

                    for (int c=0; c<12; c++) {
                        String cat = "C" + c;
                        if (nadiArr[c] != -1) nadiDataset.addValue(nadiArr[c], "Nadi", cat); else nadiDataset.addValue(null, "Nadi", cat);
                        if (respArr[c] != -1) respDataset.addValue(respArr[c], "Respirasi", cat); else respDataset.addValue(null, "Respirasi", cat);
                        if (suhuArr[c] != -1) suhuDataset.addValue(suhuArr[c], "Suhu", cat); else suhuDataset.addValue(null, "Suhu", cat);
                    }

                    // Create chart (transparent)
                    org.jfree.chart.plot.CategoryPlot plot = new org.jfree.chart.plot.CategoryPlot();
                    
                    // Renderer for Nadi (Red Square)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendNadi = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return nadiValid[item];
                        }
                    };
                    rendNadi.setSeriesPaint(0, java.awt.Color.RED);
                    rendNadi.setSeriesShape(0, new java.awt.geom.Rectangle2D.Double(-3, -3, 6, 6));
                    plot.setDataset(0, nadiDataset);
                    plot.setRenderer(0, rendNadi);

                    // Renderer for Respirasi (Black Circle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendResp = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return respValid[item];
                        }
                    };
                    rendResp.setSeriesPaint(0, java.awt.Color.BLACK);
                    rendResp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));
                    plot.setDataset(1, respDataset);
                    plot.setRenderer(1, rendResp);

                    // Renderer for Suhu (Blue Triangle)
                    org.jfree.chart.renderer.category.LineAndShapeRenderer rendSuhu = new org.jfree.chart.renderer.category.LineAndShapeRenderer(true, true) {
                        @Override
                        public boolean getItemShapeVisible(int series, int item) {
                            return suhuValid[item];
                        }
                    };
                    rendSuhu.setSeriesPaint(0, java.awt.Color.BLUE);
                    java.awt.geom.GeneralPath triangle = new java.awt.geom.GeneralPath();
                    triangle.moveTo(0, -4); triangle.lineTo(4, 4); triangle.lineTo(-4, 4); triangle.closePath();
                    rendSuhu.setSeriesShape(0, triangle);
                    plot.setDataset(2, suhuDataset);
                    plot.setRenderer(2, rendSuhu);"""

java_code = java_code.replace(old_chart_code, new_chart_code)
with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(java_code)


# 2. Update Legend in JRXML
with open("report/rptObservasiTTVBalance.jrxml", "r") as f:
    jrxml = f.read()

# Replace any of the broken <font color> or <style color> with unicode characters
# Because the user's screenshot literally shows the HTML tags!
import re
matches = re.findall(r'<textFieldExpression><!\[CDATA\["Keterangan:.*?\]\]></textFieldExpression>', jrxml)
if matches:
    jrxml = jrxml.replace(matches[0], '<textFieldExpression><![CDATA["Keterangan:   \u25A0 = Nadi     \u25CF = Respirasi     \u25B2 = Suhu"]]></textFieldExpression>')

# Also need to make sure markup is none, just in case
jrxml = jrxml.replace('markup="html"', '')
jrxml = jrxml.replace('markup="styled"', '')

with open("report/rptObservasiTTVBalance.jrxml", "w") as f:
    f.write(jrxml)

print("Patch applied: Graph line interpolation & Legend symbols restored")
