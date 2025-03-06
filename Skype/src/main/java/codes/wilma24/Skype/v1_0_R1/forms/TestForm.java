package codes.wilma24.Skype.v1_0_R1.forms;

import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestForm extends JFrame {

	public TestForm() {
		super("Test");
		String imgurl = "https://litter.catbox.moe/rne3g8.mp4";
		try {
			Class.forName("javafx.scene.media.MediaPlayer");
			Class<?> clazz = Class
					.forName("codes.wilma24.Skype.v1_0_R1.uicommon.JavaFxMediaPlayerPanel");
			Object obj = clazz.newInstance();
			Method m = clazz.getMethod("prepareMedia", String.class);
			m.setAccessible(true);
			boolean success = (boolean) m.invoke(obj, imgurl);
			if (success) {
				JPanel javaFxMediaPlayer = (JPanel) obj;
				setContentPane(javaFxMediaPlayer);
				pack();
			} else {
				add(new JLabel("404 Not Found"));
				pack();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
