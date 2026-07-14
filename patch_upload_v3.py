import os

def patch_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # Find simpanGambar or simpanTTD method
    is_webcam = 'simpanGambar()' in content

    # Replace the upload block
    if is_webcam:
        old_upload = """            // --- LANGKAH 2: UPLOAD KE SERVER ---
            uploadImage(fileName, "pernyataanumum", file);"""
        
        new_upload = """            // --- LANGKAH 2: UPLOAD KE SERVER (Sekarang menggunakan metode v3) ---
            boolean uploadSukses = uploadKeServer(file, this.noSurat);
            
            if (!uploadSukses) {
                JOptionPane.showMessageDialog(this, 
                    "Peringatan: Gagal meng-upload file ke server.\\n" +
                    "File hanya tersimpan di komputer lokal.", 
                    "Peringatan Upload", JOptionPane.WARNING_MESSAGE);
            }"""
        content = content.replace(old_upload, new_upload)
    else:
        old_upload = """            uploadImage(fileName, "pernyataanumum", file);
            
            boolean dbSuccess = saveToDatabase(fileName);
            if (dbSuccess) {
                namaFileTersimpan = fileName;
                System.out.println("TTD saved: " + namaFileTersimpan);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan tanda tangan ke database");
                namaFileTersimpan = "";
            }"""
        
        new_upload = """            // --- LANGKAH 2: UPLOAD KE SERVER (Sekarang menggunakan metode v3) ---
            boolean uploadSuccess = uploadKeServer(file, noSurat);
            
            if (!uploadSuccess) {
                JOptionPane.showMessageDialog(this, 
                    "Peringatan: Gagal meng-upload file ke server.\\n" +
                    "File hanya tersimpan di komputer lokal.", 
                    "Peringatan Upload", JOptionPane.WARNING_MESSAGE);
            }

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

    # Now replace uploadImage with uploadKeServer
    start_idx = content.find('    private void uploadImage')
    if start_idx != -1:
        end_idx = content.find('    private boolean saveToDatabase', start_idx)
        if end_idx == -1:
            end_idx = content.find('    public boolean isSaved()', start_idx)
        
        if end_idx != -1:
            old_method = content[start_idx:end_idx]
            
            new_method = """    private boolean uploadKeServer(File file, String noSurat) {
        String urlUpload = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + 
                           koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + 
                           "/imagefreehand/upload.php?doc=pernyataanumum";
        
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        org.apache.commons.httpclient.methods.PostMethod post = new org.apache.commons.httpclient.methods.PostMethod(urlUpload);
        
        try {
            org.apache.commons.httpclient.methods.multipart.Part[] parts = {
                new org.apache.commons.httpclient.methods.multipart.StringPart("noSurat", noSurat),
                new org.apache.commons.httpclient.methods.multipart.FilePart("file", file)
            };
    
            post.setRequestEntity(new org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity(parts, post.getParams()));
    
            int statusCode = client.executeMethod(post);
            
            return statusCode == org.apache.commons.httpclient.HttpStatus.SC_OK; 

        } catch (Exception e) {
            System.out.println("Gagal upload ke server: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            post.releaseConnection(); 
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
