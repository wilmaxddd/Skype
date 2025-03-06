/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.hc.client5.http.classic.methods.HttpPost
 *  org.apache.hc.client5.http.entity.mime.HttpMultipartMode
 *  org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.apache.hc.client5.http.impl.classic.HttpClients
 *  org.apache.hc.core5.http.ClassicHttpRequest
 *  org.apache.hc.core5.http.ContentType
 *  org.apache.hc.core5.http.HttpEntity
 *  org.bouncycastle.util.io.Streams
 */
package codes.wilma24.Skype.headless.v1_0_R1.litterbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.bouncycastle.util.io.Streams;

public class LitterboxUploader {
    public Optional<String> uploadImg(byte[] imageData) {
        try {
            File file = File.createTempFile("Skype", UUID.randomUUID().toString().replace("-", "") + ".jpg");
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            FileOutputStream fos = new FileOutputStream(file);
            Streams.pipeAll((InputStream)bais, (OutputStream)fos);
            bais.close();
            fos.flush();
            fos.close();
            Optional<String> r = this.uploadFile(file);
            file.delete();
            return r;
        }
        catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<String> uploadData(byte[] fileData, String fileExt) {
        try {
            File file = File.createTempFile("Skype", UUID.randomUUID().toString().replace("-", "") + "." + fileExt);
            ByteArrayInputStream bais = new ByteArrayInputStream(fileData);
            FileOutputStream fos = new FileOutputStream(file);
            Streams.pipeAll((InputStream)bais, (OutputStream)fos);
            bais.close();
            fos.flush();
            fos.close();
            Optional<String> r = this.uploadFile(file);
            file.delete();
            return r;
        }
        catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<String> uploadFile(File pathToFile) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpEntity entity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.EXTENDED).setBoundary("----WebKitFormBoundaryDeC2E3iWbTv1PwMC").setContentType(ContentType.MULTIPART_FORM_DATA).addBinaryBody("fileToUpload", pathToFile, ContentType.APPLICATION_OCTET_STREAM, pathToFile.getName()).addTextBody("reqtype", "fileupload", ContentType.MULTIPART_FORM_DATA).addTextBody("time", "24h", ContentType.MULTIPART_FORM_DATA).build();
            HttpPost httpPost = new HttpPost("https://litterbox.catbox.moe/resources/internals/api.php");
            httpPost.addHeader("Content-Type", (Object)"multipart/form-data; boundary=----WebKitFormBoundaryDeC2E3iWbTv1PwMC");
            httpPost.setEntity(entity);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Streams.pipeAll((InputStream)httpClient.execute((ClassicHttpRequest)httpPost).getEntity().getContent(), (OutputStream)baos);
            String arg0 = new String(baos.toByteArray());
            System.out.println(arg0);
            return Optional.of(arg0);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

