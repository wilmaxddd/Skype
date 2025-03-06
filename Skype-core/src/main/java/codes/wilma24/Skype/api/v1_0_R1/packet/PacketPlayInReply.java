package codes.wilma24.Skype.api.v1_0_R1.packet;

public class PacketPlayInReply extends Packet {

	/**
	 * HTML Status Codes
	 */
	public static final int BAD_REQUEST = 400;
	public static final int NOT_IMPLEMENTED = 501;
	public static final int OK = 200;

	private PacketType replyToPacketType;

	private int statusCode;

	private String text;

	public PacketPlayInReply(PacketType replyToPacketType, int statusCode,
			String text) {
		super(PacketType.REPLY);
		this.setReplyToPacketType(replyToPacketType);
		this.setStatusCode(statusCode);
		this.setText(text);
	}

	public PacketType getReplyToPacketType() {
		return replyToPacketType;
	}

	public void setReplyToPacketType(PacketType replyToPacketType) {
		this.replyToPacketType = replyToPacketType;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static PacketPlayInReply empty() {
		return null;
	}

}
