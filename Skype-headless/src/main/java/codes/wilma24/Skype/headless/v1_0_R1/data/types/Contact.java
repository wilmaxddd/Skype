/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package codes.wilma24.Skype.headless.v1_0_R1.data.types;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.headless.v1_0_R1.Conversation;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Status;

import com.google.gson.Gson;

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
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class Contact
extends Conversation {
    public volatile boolean favorite = false;
    public volatile String mood;
    public volatile Status onlineStatus = Status.OFFLINE;
    public volatile String mobilePhone;
    public volatile String homePhone;
    public volatile String officePhone;
    public volatile List<String> favorites = new ArrayList<String>();
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
        this.readFromJson(json);
    }

    @Override
    public void readFromJson(String json) {
        Gson gson = GsonBuilder.create();
        Contact clazz = (Contact)gson.fromJson(json, Contact.class);
        this.pubKey = clazz.pubKey;
        this.uuid = clazz.uuid;
        this.skypeName = clazz.skypeName;
        this.name = clazz.name;
        this.groupChat = clazz.groupChat;
        this.imageIconUrl = clazz.imageIconUrl;
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
        this.favorite = clazz.favorite;
        this.favorites = clazz.favorites;
        this.mood = clazz.mood;
        this.mobilePhone = clazz.mobilePhone;
        this.homePhone = clazz.homePhone;
        this.officePhone = clazz.officePhone;
        this.email = clazz.email;
        this.countryName = clazz.countryName;
        this.state = clazz.state;
        this.city = clazz.city;
        this.timeZone = clazz.timeZone;
        this.website = clazz.website;
        this.gender = clazz.gender;
        this.birthDate = clazz.birthDate;
        this.language = clazz.language;
        this.numberOfContacts = clazz.numberOfContacts;
        this.aboutMe = clazz.aboutMe;
        this.lastLogin = clazz.lastLogin;
    }

    @Override
    public String exportAsJson() {
        Gson gson = GsonBuilder.create();
        return gson.toJson((Object)this);
    }

    public List<String> getFavorites() {
        return this.favorites;
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getMood() {
        return this.mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Status getOnlineStatus() {
        return this.onlineStatus;
    }

    public void setOnlineStatus(Status onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(this.timeZone);
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone.getDisplayName(Locale.ENGLISH);
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return new Date(this.birthDate);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate.getTime();
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberOfContacts() {
        return this.numberOfContacts;
    }

    public void setNumberOfContacts(int numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public String getAboutMe() {
        return this.aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public long getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }
}

