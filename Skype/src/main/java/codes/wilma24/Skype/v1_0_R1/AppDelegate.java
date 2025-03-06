package codes.wilma24.Skype.v1_0_R1;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bukkit.configuration.file.FileConfiguration;

import codes.wilma24.Skype.v1_0_R1.bukkit.ConfigurationManager;
import codes.wilma24.Skype.v1_0_R1.fontio.FontIO;
import codes.wilma24.Skype.v1_0_R1.forms.LoginForm;
import codes.wilma24.Skype.v1_0_R1.plugin.Skype;

public class AppDelegate {

	public static final long VERSION = 3475;

	public static long TIME_OFFSET = 0L;

	public static String getJavaPath() {
		String tmp1 = System.getProperty("java.home") + "\\bin\\java.exe";
		String tmp2 = System.getProperty("sun.boot.library.path")
				+ "\\java.exe";
		String tmp3 = System.getProperty("java.library.path") + "\\java.exe";
		String tmp4 = "jre8\\bin\\java.exe";
		if (new File(tmp1).exists()) {
			return tmp1;
		} else if (new File(tmp2).exists()) {
			return tmp2;
		} else if (new File(tmp3).exists()) {
			return tmp3;
		} else if (new File(tmp4).exists()) {
			return tmp4;
		} else {
			String[] paths = System.getenv("PATH").split(";");
			for (String path : paths) {
				if (new File(path + "\\java.exe").exists()) {
					return path + "\\java.exe";
				}
			}
		}
		return "";
	}

	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "false");
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
		try {
			String TIME_SERVER = "time-a.nist.gov";
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getTransmitTimeStamp()
					.getTime();
			TIME_OFFSET = returnTime - System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.buttonFont", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"List.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"TableHeader.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"Panel.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"ToggleButton.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"ComboBox.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"ScrollPane.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"Spinner.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put("RadioButtonMenuItem.font", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Slider.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"EditorPane.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"OptionPane.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"ToolBar.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Tree.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put("CheckBoxMenuItem.font", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"TitledBorder.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put("FileChooser.listFont", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Table.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"MenuBar.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"PopupMenu.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Label.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"MenuItem.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put("MenuItem.acceleratorFont", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"TextField.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"TextPane.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"CheckBox.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"ProgressBar.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put("FormattedTextField.font", new FontUIResource(
				FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT, 11)));
		UIManager.put(
				"Menu.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"PasswordField.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put("InternalFrame.titleFont", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(
				FontIO.SEGOE_UI.deriveFont(Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Viewport.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"TabbedPane.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"RadioButton.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));
		UIManager.put(
				"ToolTip.font",
				new FontUIResource(FontIO.SEGOE_UI.deriveFont(
						Font.TRUETYPE_FONT, 12)));
		UIManager.put(
				"Button.font",
				new FontUIResource(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
						11)));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// HealthMonitor.start();
				if (System.getProperty("os.name").startsWith("Windows")) {
					try {
		                int r;
		                String imageIconUrl = "https://raw.githubusercontent.com/wilmaxddd/wilmaxddd.github.io/main/skype/version_client.txt";
		                HttpGet request = new HttpGet(imageIconUrl.replace(" ", "%20"));
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
		                ByteArrayOutputStream baos = new ByteArrayOutputStream();
		                while ((r = is.read(b)) != -1) {
		                    baos.write(Arrays.copyOf(b, r));
		                    baos.flush();
		                }
		                baos.close();
		                String nextLine = new String(baos.toByteArray()).trim();
		                File file = new File("Skype-updater-1.2-SNAPSHOT.jar");
						System.out.println(nextLine);
						System.out.println(file.getAbsolutePath());
						if (Long.parseLong(nextLine) > VERSION) {
							if (file.exists()) {
								ProcessBuilder pb = new ProcessBuilder(
										getJavaPath(), "-jar",
										"Skype-updater-1.2-SNAPSHOT.jar");
								try {
									pb.start();
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.exit(-1);
							}
						}
		            }
		            catch (Exception e) {
		                e.printStackTrace();
		            }
				}
				ConfigurationManager root;
				FileConfiguration config;
				root = new ConfigurationManager();
				root.setup(new File("config.yml"));
				config = root.getData();
				Thread thread = new Thread(() -> {
					while (true) {
						root.saveData();
						try {
							Thread.sleep(20_000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				thread.start();
				Skype.getPlugin().setConfig(config);
				LoginForm loginForm = new LoginForm();
				loginForm.show();
			}
		});
	}
}
