package codes.wilma24.Skype.v1_0_R1.data.types;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;
import codes.wilma24.Skype.v1_0_R1.imageio.ImageIO;

import com.google.gson.Gson;

public class Contact extends Conversation {

	public volatile boolean favorite = false;

	/*
	 * Show profile
	 */
	public volatile String mood;
	public volatile Status onlineStatus = Status.ONLINE;
	public volatile String mobilePhone;
	public volatile String homePhone;
	public volatile String officePhone;

	/*
	 * Show full profile
	 */
	public volatile List<String> favorites = new ArrayList<>();
	public volatile String email;
	public volatile String countryName;
	public volatile String state;
	public volatile String city;
	public volatile String timeZone;
	public volatile String website;
	public volatile String gender;
	public volatile long birthDate;
	public volatile String language;
	public volatile int numberOfContacts;
	public volatile String aboutMe;
	public volatile long lastLogin;

	public Contact() {

	}

	public Contact(String json) {
		readFromJson(json);
	}

	@Override
	public void readFromJson(String json) {
		Gson gson = GsonBuilder.create();
		Contact clazz = gson.fromJson(json, Contact.class);
		/**
		 * Conversation
		 */
		this.pubKey = clazz.pubKey;
		this.uuid = clazz.uuid;
		this.uuid2 = clazz.uuid2;
		this.setSkypeName(clazz.skypeName);
		this.setDisplayName(clazz.name);
		this.groupChat = clazz.groupChat;
		this.setImageIconUrl(clazz.imageIconUrl);
		if (imageIconUrl == null) {
			this.imageIcon = null;
		}
        if (this.imageIconUrl != null && this.imageIconUrl.length() > 0 && this.imageIconUrl.startsWith("https://i.imgur.com/")) {
            try {
            	ImageIcon imgIcon;
                int r;
                HttpGet request = new HttpGet(this.imageIconUrl.replace(" ", "%20"));
                request.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"130\", \"Microsoft Edge\";v=\"130\"");
                request.addHeader("X-Requested-With", "XMLHttpRequest");
                request.addHeader("sec-ch-ua-mobile", "?0");
                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36 Edg/130.0.0.0");
                request.addHeader("sec-ch-ua-platform", "\"Windows\"");
                request.addHeader("Origin", this.imageIconUrl);
                request.addHeader("Sec-Fetch-Site", "same-origin");
                request.addHeader("Sec-Fetch-Mode", "cors");
                request.addHeader("Sec-Fetch-Dest", "empty");
                request.addHeader("Referer", this.imageIconUrl);
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
                BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
                this.imageIcon = imgIcon = new ImageIcon(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
		/**
		 * Contact
		 */
		this.favorite = clazz.favorite;
		this.favorites = clazz.favorites;
		this.setMood(clazz.mood);
		/* this.onlineStatus = clazz.onlineStatus; */
		this.setMobilePhone(clazz.mobilePhone);
		this.setHomePhone(clazz.homePhone);
		this.setOfficePhone(clazz.officePhone);
		this.setEmail(clazz.email);
		this.setCountryName(clazz.countryName);
		this.setState(clazz.state);
		this.setCity(clazz.city);
		this.timeZone = clazz.timeZone;
		this.setWebsite(clazz.website);
		this.setGender(clazz.gender);
		this.birthDate = clazz.birthDate;
		this.setLanguage(clazz.language);
		this.numberOfContacts = clazz.numberOfContacts;
		this.setAboutMe(clazz.aboutMe);
		this.lastLogin = clazz.lastLogin;
		this.onlineStatus = clazz.onlineStatus;
		Contact contact = (Contact) this;
		Map.Entry<JPanel, JLabel> entry = ImageIO.getConversationIconPanel(
				this.getImageIcon(), contact.getOnlineStatus());
		onlineStatusPanel = entry.getKey();
		onlineStatusLabel = entry.getValue();
	}

	@Override
	public String exportAsJson() {
		Gson gson = GsonBuilder.create();
		return gson.toJson(this);
	}

	public List<String> getFavorites() {
		return favorites;
	}

	public boolean isFavorite() {
		if (MainForm.get().getLoggedInUser().getFavorites()
				.contains(this.getUniqueId().toString())) {
			this.favorite = true;
		} else {
			this.favorite = false;
		}
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getMood() {
		return mood;
	}

	public String getFirstLineOfMood() {
		if (mood == null) {
			return mood;
		} else {
			return mood.split("\\r?\\n")[0];
		}
	}

	public void setMood(String mood) {
		if (mood != null && mood.length() > 200) {
			mood = mood.substring(0, 200);
		}
		this.mood = mood;
	}

	public Status getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Status onlineStatus) {
		this.onlineStatus = onlineStatus;
		JLabel iconLabel = getOnlineStatusLabel();
		Map.Entry<JPanel, JLabel> entry = ImageIO.getConversationIconPanel(
				this.getImageIcon(), onlineStatus);
		iconLabel.setIcon(entry.getValue().getIcon());
		iconLabel.validate();
		iconLabel.repaint();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		if (mobilePhone != null && mobilePhone.length() > 200) {
			mobilePhone = mobilePhone.substring(0, 200);
		}
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		if (homePhone != null && homePhone.length() > 200) {
			homePhone = homePhone.substring(0, 200);
		}
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		if (officePhone != null && officePhone.length() > 200) {
			officePhone = officePhone.substring(0, 200);
		}
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null && email.length() > 200) {
			email = email.substring(0, 200);
		}
		this.email = email;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		if (countryName != null && countryName.length() > 200) {
			countryName = countryName.substring(0, 200);
		}
		this.countryName = countryName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state != null && state.length() > 200) {
			state = state.substring(0, 200);
		}
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city != null && city.length() > 200) {
			city = city.substring(0, 200);
		}
		this.city = city;
	}

	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(timeZone);
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone.getDisplayName(Locale.ENGLISH);
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		if (website != null && website.length() > 200) {
			website = website.substring(0, 200);
		}
		this.website = website;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if (gender != null && gender.length() > 200) {
			gender = gender.substring(0, 200);
		}
		this.gender = gender;
	}

	public Date getBirthDate() {
		return new Date(birthDate);
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate.getTime();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		if (language != null && language.length() > 200) {
			language = language.substring(0, 200);
		}
		this.language = language;
	}

	public int getNumberOfContacts() {
		return numberOfContacts;
	}

	public void setNumberOfContacts(int numberOfContacts) {
		this.numberOfContacts = numberOfContacts;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		if (aboutMe != null && aboutMe.length() > 200) {
			aboutMe = aboutMe.substring(0, 200);
		}
		this.aboutMe = aboutMe;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

}
