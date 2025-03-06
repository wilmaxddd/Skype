package codes.wilma24.Skype.v1_0_R1.litterbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.bouncycastle.util.io.Streams;

public class LitterboxUploader {
	
	public Optional<String> uploadImg(byte[] imageData) {
		try {
			File file = File.createTempFile("Skype", UUID.randomUUID().toString().replace("-", "") + ".jpg");
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			FileOutputStream fos = new FileOutputStream(file);
			Streams.pipeAll(bais, fos);
			bais.close();
			fos.flush();
			fos.close();
			Optional<String> r = uploadFile(file);
			file.delete();
			return r;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.empty();
		}
		
	}

	public Optional<String> uploadFile(File pathToFile) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpEntity entity = MultipartEntityBuilder
					.create()
					.setMode(HttpMultipartMode.EXTENDED)
					.setBoundary("----WebKitFormBoundaryDeC2E3iWbTv1PwMC")
					.setContentType(ContentType.MULTIPART_FORM_DATA)
					.addBinaryBody("fileToUpload", pathToFile,
							ContentType.APPLICATION_OCTET_STREAM,
							pathToFile.getName())
					.addTextBody("reqtype", "fileupload",
							ContentType.MULTIPART_FORM_DATA)
					.addTextBody("time", "24h",
							ContentType.MULTIPART_FORM_DATA).build();

			HttpPost httpPost = new HttpPost(
					"https://litterbox.catbox.moe/resources/internals/api.php");
			httpPost.addHeader("Content-Type",
					"multipart/form-data; boundary=----WebKitFormBoundaryDeC2E3iWbTv1PwMC");
			httpPost.setEntity(entity);
			// betterPrint(httpPost);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Streams.pipeAll(httpClient.execute(httpPost).getEntity().getContent(), baos);
			String arg0 = new String(baos.toByteArray());
			System.out.println(arg0);
			return Optional.of(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

}
