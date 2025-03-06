package codes.wilma24.Skype.server.v1_0_R1.command;

import java.util.List;
import java.util.Optional;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInRemoveMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRemoveMessage;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class RemoveMessageCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutRemoveMessage packet = Packet.fromJson(msg.toString(),
				PacketPlayOutRemoveMessage.class);
		UUID authCode = packet.getAuthCode();
		UUID conversationId = packet.getConversationId();
		UUID messageId = packet.getMessageId();
		long timestamp = packet.getTimestamp();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		UUID participantId = con.getUniqueId();
		Optional<String> payload = Skype
				.getPlugin()
				.getConversationManager()
				.lookupMessage(conversationId, participantId, messageId,
						timestamp);
		if (Skype
				.getPlugin()
				.getConversationManager()
				.addMessage(conversationId, participantId, messageId, null,
						timestamp) == false) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		if (Skype
				.getPlugin()
				.getConversationManager()
				.addMessage(participantId, conversationId, messageId, null,
						timestamp) == false) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		Skype.getPlugin().getConversationManager()
				.addParticipants(conversationId, participantId);
		Skype.getPlugin().getConversationManager()
				.addParticipants(participantId, conversationId);
		if (Skype.getPlugin().getUserManager().isGroupChat(conversationId)) {
			Optional<List<UUID>> participants = Skype.getPlugin()
					.getConversationManager().getParticipants(conversationId);
			if (participants.isPresent()) {
				for (UUID participantId2 : participants.get()) {
					PacketPlayInRemoveMessage messageReceivePacket = new PacketPlayInRemoveMessage(
							conversationId, participantId, messageId, null);
					if (payload.isPresent()) {
						messageReceivePacket = new PacketPlayInRemoveMessage(
								conversationId, participantId, messageId,
								payload.get());
					}
					for (Connection listeningParticipant : Skype.getPlugin()
							.getUserManager()
							.getListeningConnections(participantId2)) {
						try {
							listeningParticipant
									.getSocketHandlerContext()
									.getOutboundHandler()
									.dispatchAsync(
											listeningParticipant
													.getSocketHandlerContext(),
											messageReceivePacket, null);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			for (Connection listeningParticipant : Skype.getPlugin()
					.getUserManager().getListeningConnections(conversationId)) {
				PacketPlayInRemoveMessage messageReceivePacket = new PacketPlayInRemoveMessage(
						participantId, participantId, messageId, null);
				if (payload.isPresent()) {
					messageReceivePacket = new PacketPlayInRemoveMessage(
							participantId, participantId, messageId,
							payload.get());
				}
				try {
					listeningParticipant
							.getSocketHandlerContext()
							.getOutboundHandler()
							.dispatchAsync(
									listeningParticipant
											.getSocketHandlerContext(),
									messageReceivePacket, null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			for (Connection listeningParticipant : Skype.getPlugin()
					.getUserManager().getListeningConnections(participantId)) {
				PacketPlayInRemoveMessage messageReceivePacket = new PacketPlayInRemoveMessage(
						conversationId, participantId, messageId, null);
				if (payload.isPresent()) {
					messageReceivePacket = new PacketPlayInRemoveMessage(
							conversationId, participantId, messageId,
							payload.get());
				}
				try {
					listeningParticipant
							.getSocketHandlerContext()
							.getOutboundHandler()
							.dispatchAsync(
									listeningParticipant
											.getSocketHandlerContext(),
									messageReceivePacket, null);
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
