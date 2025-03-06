package codes.wilma24.Skype.api.v1_0_R1.packet;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class PacketPlayOutLookupOnlineStatus extends Packet {

	private UUID authCode;

	private UUID[] conversationIds;

	public PacketPlayOutLookupOnlineStatus(UUID authCode, UUID... conversationIds) {
		super(PacketType.LOOKUP_ONLINE_STATUS);
		this.setAuthCode(authCode);
		this.setConversationIds(conversationIds);
	}

	public UUID getAuthCode() {
		return authCode;
	}

	public void setAuthCode(UUID authCode) {
		this.authCode = authCode;
	}

	public UUID[] getConversationIds() {
		return conversationIds;
	}

	public void setConversationIds(UUID... conversationIds) {
		this.conversationIds = conversationIds;
	}

}
