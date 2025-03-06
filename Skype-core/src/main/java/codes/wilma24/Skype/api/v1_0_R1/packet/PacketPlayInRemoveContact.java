package codes.wilma24.Skype.api.v1_0_R1.packet;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class PacketPlayInRemoveContact extends Packet {
	
	private UUID conversationId;

	public PacketPlayInRemoveContact(UUID conversationId) {
		super(PacketType.REMOVE_CONTACT_IN);
		this.setConversationId(conversationId);
	}

	public UUID getConversationId() {
		return conversationId;
	}

	public void setConversationId(UUID conversationId) {
		this.conversationId = conversationId;
	}

}
