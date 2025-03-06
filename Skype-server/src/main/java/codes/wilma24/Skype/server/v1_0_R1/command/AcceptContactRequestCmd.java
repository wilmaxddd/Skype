package codes.wilma24.Skype.server.v1_0_R1.command;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInAcceptContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInRemoveMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutAcceptContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class AcceptContactRequestCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutAcceptContactRequest packet = Packet.fromJson(
				msg.toString(), PacketPlayOutAcceptContactRequest.class);
		UUID authCode = packet.getAuthCode();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		UUID participantId = con.getUniqueId();
		UUID conversationId = packet.getConversationId();

		if (!Skype.getPlugin().getUserManager()
				.hasContactRequest(participantId, conversationId)) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}

		Skype.getPlugin().getUserManager()
				.addContact(conversationId, participantId);
		Skype.getPlugin().getUserManager()
				.addContact(participantId, conversationId);

		{
			PacketPlayInAcceptContactRequest acceptContactRequestPacket = new PacketPlayInAcceptContactRequest(
					participantId, conversationId);

			for (Connection listeningParticipant : Skype.getPlugin()
					.getUserManager().getListeningConnections(conversationId)) {
				try {
					listeningParticipant
							.getSocketHandlerContext()
							.getOutboundHandler()
							.dispatchAsync(
									listeningParticipant
											.getSocketHandlerContext(),
									acceptContactRequestPacket, null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}

		{
			PacketPlayInAcceptContactRequest acceptContactRequestPacket = new PacketPlayInAcceptContactRequest(
					conversationId, participantId);

			for (Connection listeningParticipant : Skype.getPlugin()
					.getUserManager().getListeningConnections(participantId)) {
				try {
					listeningParticipant
							.getSocketHandlerContext()
							.getOutboundHandler()
							.dispatchAsync(
									listeningParticipant
											.getSocketHandlerContext(),
									acceptContactRequestPacket, null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}

		PacketPlayInReply replyPacket = new PacketPlayInReply(packet.getType(),
				PacketPlayInReply.OK, packet.getType().name() + " success");
		return replyPacket;
	}

}
