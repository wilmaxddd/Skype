package codes.wilma24.Skype.server.v1_0_R1.command;

import java.net.Socket;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.data.types.Call;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInDeclineCallRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutDeclineCallRequest;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class DeclineCallRequestCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutDeclineCallRequest packet = Packet.fromJson(
				msg.toString(), PacketPlayOutDeclineCallRequest.class);
		UUID authCode = packet.getAuthCode();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		UUID callId = packet.getCallId();
		UUID participantId = con.getUniqueId();
		Call call = Skype.getPlugin().getCallMap().getOrDefault(callId, null);
		if (call == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		if (!call.isParticipant(participantId)) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		PacketPlayInDeclineCallRequest declineCallRequestPacket = new PacketPlayInDeclineCallRequest(
				participantId, callId);
		int hits = 0;
		for (UUID participant : call.getParticipants()) {
			boolean hasParticipantAnsweredCall = Skype
					.getPlugin()
					.getUserManager()
					.getCallingInboundHandlerConnectionsInCall(participant,
							callId).size() > 0;
			if (hasParticipantAnsweredCall) {
				for (Connection listeningParticipant : Skype.getPlugin()
						.getUserManager().getListeningConnections(participant)) {
					try {
						listeningParticipant
								.getSocketHandlerContext()
								.getOutboundHandler()
								.dispatchAsync(
										listeningParticipant
												.getSocketHandlerContext(),
										declineCallRequestPacket, null);
						hits++;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (call.getParticipants().size() <= 2) {
			for (UUID callParticipant : call.getParticipants()
					.toArray(new UUID[0]).clone()) {
				for (Connection con2 : Skype
						.getPlugin()
						.getUserManager()
						.getAllConnectionsInCall(callParticipant,
								call.getCallId()).toArray(new Connection[0])
						.clone()) {
					try {
						for (Socket socket2 : con2.getSocketHandlerContext()
								.getSockets()) {
							socket2.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		call.removeParticipant(participantId);

		if (hits == 0) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}

		PacketPlayInReply replyPacket = new PacketPlayInReply(packet.getType(),
				PacketPlayInReply.OK, packet.getType().name() + " success");
		return replyPacket;
	}

}
