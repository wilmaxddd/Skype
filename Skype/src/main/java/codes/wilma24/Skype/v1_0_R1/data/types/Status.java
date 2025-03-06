package codes.wilma24.Skype.v1_0_R1.data.types;

public enum Status {

	INVISIBLE, OFFLINE, ONLINE, AWAY, DO_NOT_DISTURB, NOT_A_CONTACT;
	
	public String getReadableName() {
		switch (this) {
		case INVISIBLE:
			return "Invisible";
		case OFFLINE:
			return "Offline";
		case ONLINE:
			return "Online";
		case AWAY:
			return "Away";
		case DO_NOT_DISTURB:
			return "Do Not Disturb";
		case NOT_A_CONTACT:
			return "Not A Contact";
		}
		return name();
	}

}
