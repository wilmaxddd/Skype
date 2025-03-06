package codes.wilma24.Skype.server.v1_0_R1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class ConfigurationManager implements Serializable {

	/*
	 * this option needs to be disabled for "import data" to work
	 */
	private boolean ratelimiting = false;

	/*
	 * this option interferes with bots, only enable if strictly necessary
	 */
	private boolean ratelimiting_msgs = false;

	public ConfigurationManager(File file) throws IOException {
		Properties p = new Properties();
		if (!file.exists()) {
			file.createNewFile();
		}
		try (FileReader reader = new FileReader(file)) {
			p.load(reader);
			if (p.getProperty("ratelimiting") == null) {
				p.setProperty("ratelimiting", ratelimiting + "");
			} else {
				ratelimiting = Boolean.parseBoolean(p
						.getProperty("ratelimiting"));
			}
			if (p.getProperty("ratelimiting_msgs") == null) {
				p.setProperty("ratelimiting_msgs", ratelimiting_msgs + "");
			} else {
				ratelimiting_msgs = Boolean.parseBoolean(p
						.getProperty("ratelimiting_msgs"));
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			p.store(outputStream, null);
			outputStream.close();
		}
	}

	public boolean isRatelimiting() {
		String ratelimiting = null;
		if (ratelimiting == null) {
			ratelimiting = System.getProperty("ratelimiting");
			if (ratelimiting == null) {
				ratelimiting = System.getenv("ratelimiting");
			}
		}
		if (ratelimiting != null) {
			if (Boolean.parseBoolean(ratelimiting) == true) {
				return true;
			}
		}
		return this.ratelimiting;
	}

	public boolean isRatelimitingMsgs() {
		return ratelimiting_msgs;
	}
}
