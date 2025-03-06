package codes.wilma24.Skype.updater.v1_0_R1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class AppDelegate extends JFrame {

	public AppDelegate() {
		super("Skype! Installer");

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		/**
		 * Note to self! setResizable() must come before pack()
		 * 
		 * If you do not do this before pack() then the size is wrong
		 */
		setResizable(false);

		getContentPane().setPreferredSize(new Dimension(221, 65));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 221, 34);
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Downloading update");
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label, BorderLayout.CENTER);

		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				if (label.getText().equals("Downloading update")) {
					label.setText("Downloading update .");
				} else if (label.getText().equals("Downloading update .")) {
					label.setText("Downloading update . .");
				} else if (label.getText().equals("Downloading update . .")) {
					label.setText("Downloading update . . .");
				} else if (label.getText().equals("Downloading update . . .")) {
					label.setText("Downloading update");
				}
			}

		});

		timer.start();

		add(panel);

		JPanel divider = new JPanel();
		divider.setBackground(Color.black);
		divider.setBounds(0, 34, 221, 1);
		add(divider);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(4, 39, 214, 24);
		progressBar.setMaximum(100);
		add(progressBar);

		try {
			setIconImages(Imaging.getAllBufferedImages(
					getResourceAsStream("/Updater.ico"), "Updater.ico"));
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
	                int r;
	                String imageIconUrl = "https://raw.githubusercontent.com/wilmaxddd/wilmaxddd.github.io/main/skype/Skype-1.2-SNAPSHOT.jar";
	                HttpGet request = new HttpGet(imageIconUrl);
	                request.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"130\", \"Microsoft Edge\";v=\"130\"");
	                request.addHeader("X-Requested-With", "XMLHttpRequest");
	                request.addHeader("sec-ch-ua-mobile", "?0");
	                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36 Edg/130.0.0.0");
	                request.addHeader("sec-ch-ua-platform", "\"Windows\"");
	                request.addHeader("Origin", imageIconUrl);
	                request.addHeader("Sec-Fetch-Site", "same-origin");
	                request.addHeader("Sec-Fetch-Mode", "cors");
	                request.addHeader("Sec-Fetch-Dest", "empty");
	                request.addHeader("Referer", imageIconUrl);
	                request.addHeader("Accept-Language", "en-US,en;q=0.9");
	                request.addHeader("sec-gpc", "1");
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                request.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Microsoft Edge\";v=\"100\"");
	                request.addHeader("sec-ch-ua-mobile", "?0");
	                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4863.0 Safari/537.36 Edg/100.0.1163.1");
	                CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
	                InputStream is = response.getEntity().getContent();
	                byte[] b = new byte[1024];
					File file = new File(System.getProperty("java.io.tmpdir"),
							"Skype-1.2-SNAPSHOT.jar");
					FileOutputStream fos = new FileOutputStream(file);
	                while ((r = is.read(b)) != -1) {
	                    fos.write(Arrays.copyOf(b, r));
	                    fos.flush();
	                }
	                fos.close();
	                new File("Skype-1.2-SNAPSHOT.jar").delete();
					fos = new FileOutputStream("Skype-1.2-SNAPSHOT.jar");
					Files.copy(file.toPath(), fos);
					fos.close();
					if (new File("Skype.exe").exists()) {
						Runtime.getRuntime().exec("Skype.exe");
					}
					System.exit(-1);
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
			}

		});
		thread.start();
	}

	public static InputStream getResourceAsStream(String resource) {
		if (resource.startsWith("/")) {
			resource = resource.substring(1);
		}
		try {
			InputStream is = AppDelegate.class.getResource("/" + resource)
					.openStream();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		AppDelegate form = new AppDelegate();
		form.show();
	}

}
