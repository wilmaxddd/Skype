package codes.wilma24.Skype.server.v1_0_R1.command;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupConversationLastAccessed;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class LookupConversationLastAccessed2Cmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutLookupConversationLastAccessed packet = Packet.fromJson(
				msg.toString(),
				PacketPlayOutLookupConversationLastAccessed.class);
		UUID authCode = packet.getAuthCode();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		UUID conversationId = con.getUniqueId();
		UUID participantId = packet.getConversationId();
		long lastAccessed = Skype.getPlugin().getConversationManager()
				.getLastAccessed(participantId, conversationId);
		PacketPlayInReply replyPacket = new PacketPlayInReply(packet.getType(),
				PacketPlayInReply.OK, lastAccessed + "");
		return replyPacket;
	}
}
