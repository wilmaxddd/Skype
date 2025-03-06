package codes.wilma24.Skype.server.v1_0_R1.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupMessageHistory;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class LookupMessageHistoryCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutLookupMessageHistory packet = Packet.fromJson(
				msg.toString(), PacketPlayOutLookupMessageHistory.class);
		UUID authCode = packet.getAuthCode();
		UUID conversationId = packet.getConversationId();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		UUID participantId = con.getUniqueId();
		Date from = packet.getFrom();
		Date to = packet.getTo();
		List<String> messages = new ArrayList<>();
		if (Skype.getPlugin().getUserManager().isGroupChat(conversationId)) {
			for (UUID participant : Skype.getPlugin().getConversationManager()
					.getParticipants(conversationId).get()) {
				messages.addAll(Skype.getPlugin().getConversationManager()
						.getMessages(participant, conversationId, from, to));
				messages.addAll(Skype.getPlugin().getConversationManager()
						.getMessages(conversationId, participant, from, to));
			}
		} else {
			messages = Skype.getPlugin().getConversationManager()
					.getMessages(conversationId, participantId, from, to);
			messages.addAll(Skype.getPlugin().getConversationManager()
					.getMessages(participantId, conversationId, from, to));
		}
		if (messages.size() == 0) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		PacketPlayInReply replyPacket = new PacketPlayInReply(packet.getType(),
				PacketPlayInReply.OK, GsonBuilder.create().toJson(messages));
		return replyPacket;
	}

}
