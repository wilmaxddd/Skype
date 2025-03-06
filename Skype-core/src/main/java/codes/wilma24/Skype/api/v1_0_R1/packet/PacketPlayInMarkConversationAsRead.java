package codes.wilma24.Skype.api.v1_0_R1.packet;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class PacketPlayInMarkConversationAsRead extends Packet {

	private UUID conversationId;

	public PacketPlayInMarkConversationAsRead(UUID conversationId) {
		super(PacketType.MARK_CONVERSATION_AS_READ_IN);
		this.setConversationId(conversationId);
	}

	public UUID getConversationId() {
		return conversationId;
	}

	public void setConversationId(UUID conversationId) {
		this.conversationId = conversationId;
	}

}
