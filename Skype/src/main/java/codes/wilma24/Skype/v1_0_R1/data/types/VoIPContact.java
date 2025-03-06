package codes.wilma24.Skype.v1_0_R1.data.types;

import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.voip.VoIP;
import codes.wilma24.Skype.v1_0_R1.imageio.ImageIO;

import com.google.gson.Gson;

public class VoIPContact extends Conversation {

	private volatile boolean contact = false;

	private volatile boolean favorite = false;
	
	public VoIPContact() {
		
	}
	
	public VoIPContact(String json) {
		readFromJson(json);
	}

	public void readFromJson(String json) {
		Gson gson = GsonBuilder.create();
		VoIPContact clazz = gson.fromJson(json, VoIPContact.class);
		this.contact = clazz.contact;
		this.favorite = clazz.favorite;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Conversation) {
			Conversation conversation = (Conversation) obj;
			if (uuid.toString().equals(conversation.uuid.toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return exportAsJson();
	}

	public String exportAsJson() {
		Gson gson = GsonBuilder.create();
		return gson.toJson(this);
	}

	public void setContact(boolean val) {
		this.contact = val;
		Status onlineStatus = Status.NOT_A_CONTACT;
		if (contact) {
			onlineStatus = Status.OFFLINE;
			if (VoIP.getPlugin().isConnected()) {
				onlineStatus = Status.ONLINE;
			}
		}
		Map.Entry<JPanel, JLabel> entry = ImageIO.getConversationIconPanel(
				this.getImageIcon(), onlineStatus);
		onlineStatusPanel = entry.getKey();
		onlineStatusLabel = entry.getValue();
	}

	public boolean isContact() {
		return contact;
	}

	public void setFavorite(boolean val) {
		this.favorite = val;
	}

	public boolean isFavorite() {
		return favorite;
	}

	@Override
	public ImageIcon getImageIcon() {
		return ImageIO.getResourceAsImageIcon("/125923726.png");
	}

}
