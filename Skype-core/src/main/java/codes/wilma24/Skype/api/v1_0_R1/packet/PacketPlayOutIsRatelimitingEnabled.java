package codes.wilma24.Skype.api.v1_0_R1.packet;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class PacketPlayOutIsRatelimitingEnabled extends Packet {
	
	private UUID authCode;

	public PacketPlayOutIsRatelimitingEnabled(UUID authCode) {
		super(PacketType.IS_RATELIMITING_ENABLED);
		this.setAuthCode(authCode);
	}

	public UUID getAuthCode() {
		return authCode;
	}

	public void setAuthCode(UUID authCode) {
		this.authCode = authCode;
	}

}
