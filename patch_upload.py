import os

def patch_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # Find simpanGambar or simpanTTD method and replace the upload success check
    if 'simpanGambar()' in content:
        simpan_method_start = content.find('private void simpanGambar() {')
    else:
        simpan_method_start = content.find('private void simpanTTD() {')

    if simpan_method_start == -1:
        return

    # Let's just do text replacement for the whole upload flow
    if 'boolean uploadSukses = uploadKeServer(file, this.noSurat);' in content:
        # For DlgPersetujuanWebcam
        old_upload = """            // --- LANGKAH 2: UPLOAD KE SERVER (Sekarang menggunakan metode v3) ---
            boolean uploadSukses = uploadKeServer(file, this.noSurat);
            
            if (!uploadSukses) {
                JOptionPane.showMessageDialog(this, 
                    "Peringatan: Gagal meng-upload file ke server.\\n" +
                    "File hanya tersimpan di komputer lokal.", 
                    "Peringatan Upload", JOptionPane.WARNING_MESSAGE);
            }"""
        new_upload = """            // --- LANGKAH 2: UPLOAD KE SERVER ---
            uploadImage(fileName, "pernyataanumum", file);"""
        content = content.replace(old_upload, new_upload)

    if 'boolean uploadSuccess = uploadKeServer(file, noSurat);' in content:
        # For DlgTTD...
        old_upload = """            boolean uploadSuccess = uploadKeServer(file, noSurat);

            if (uploadSuccess) {
                boolean dbSuccess = saveToDatabase(fileName);

                if (dbSuccess) {
                    namaFileTersimpan = fileName;
                    System.out.println("TTD uploaded and saved to DB: " + namaFileTersimpan);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan tanda tangan ke database");
                    namaFileTersimpan = "";
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gagal upload tanda tangan ke server, pastikan server aktif.");
                namaFileTersimpan = "";
            }"""
        
        new_upload = """            uploadImage(fileName, "pernyataanumum", file);
            
            boolean dbSuccess = saveToDatabase(fileName);
            if (dbSuccess) {
                namaFileTersimpan = fileName;
                System.out.println("TTD saved: " + namaFileTersimpan);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan tanda tangan ke database");
                namaFileTersimpan = "";
            }"""
        content = content.replace(old_upload, new_upload)

    # Now replace the uploadKeServer method with uploadImage
    # First, find the start and end of uploadKeServer
    start_idx = content.find('private boolean uploadKeServer')
    if start_idx != -1:
        end_idx = content.find('    private void simpanGambar', start_idx)
        if end_idx == -1:
            end_idx = content.find('    private boolean saveToDatabase', start_idx)
        
        if end_idx != -1:
            old_method = content[start_idx:end_idx]
            
            new_method = """    private void uploadImage(String FileName, String docpath, File file) {
        try {
            byte[] data = new byte[(int) file.length()];
            data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
            org.apache.http.client.HttpClient httpClient = new org.apache.http.impl.client.DefaultHttpClient();
            org.apache.http.client.methods.HttpPost postRequest = new org.apache.http.client.methods.HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                    + koneksiDB.HYBRIDWEB() + "/imagefreehand/upload.php?doc=" + docpath);
            org.apache.http.entity.mime.content.ByteArrayBody fileData = new org.apache.http.entity.mime.content.ByteArrayBody(data, FileName);
            org.apache.http.entity.mime.MultipartEntity reqEntity = new org.apache.http.entity.mime.MultipartEntity(org.apache.http.entity.mime.HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
        } catch (Exception e) {
            System.out.println("Upload error: " + e);
        }
    }

"""
            content = content.replace(old_method, new_method)

    with open(filepath, 'w') as f:
        f.write(content)

if __name__ == '__main__':
    files = [
        "src/simrskhanza/DlgPersetujuanWebcam.java",
        "src/freehand/DlgTTDPersetujuanUmum.java",
        "src/freehand/DlgTTDSaksi2.java"
    ]
    for file in files:
        patch_file(file)
        print("Patched " + file)
