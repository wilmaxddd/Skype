package codes.wilma24.Skype.server.v1_0_R1.command;

import java.util.ArrayList;
import java.util.List;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.data.types.Call;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInCallParticipantsChanged;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutAcceptCallDataStreamRequest;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class AcceptCallDataStreamRequestCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutAcceptCallDataStreamRequest packet = Packet.fromJson(
				msg.toString(), PacketPlayOutAcceptCallDataStreamRequest.class);
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
		UUID participantId = packet.getParticipantId();
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
		if (!call.isParticipant(con.getUniqueId())) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		if (con.isListening()) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		ctx.fireInboundHandlerInactive();
		con.setCallDataStream(callId, participantId);
		List<String> skypeNames = new ArrayList<>();
		for (UUID callParticipant : call.getParticipants().toArray(new UUID[0])
				.clone()) {
			for (Connection con2 : Skype
					.getPlugin()
					.getUserManager()
					.getListeningDataStreamConnectionsInCall(callParticipant,
							call.getCallId()).toArray(new Connection[0])
					.clone()) {
				if (!con2.getCallId().isPresent()) {
					continue;
				}
				if (!con2.getReceivingCallDataStreamParticipantId().isPresent()) {
					continue;
				}
				UUID receivingCallId = con2.getCallId().get();
				if (receivingCallId.equals(call.getCallId())) {
					if (!con2.isCallDataStreamEnded()) {
						if (!skypeNames.contains(con2.getSkypeName())) {
							skypeNames.add(con2.getSkypeName());
						}
					}
				}
			}
		}
		List<String> participantIds = new ArrayList<>();
		for (String skypeName : skypeNames) {
			participantIds.add(Skype.getPlugin().getUserManager()
					.getUniqueId(skypeName).toString());
		}
		Object payload = GsonBuilder.create().toJson(participantIds);
		for (UUID callParticipant : call.getParticipants()) {
			boolean hasParticipantAnsweredCall = Skype
					.getPlugin()
					.getUserManager()
					.getCallingInboundHandlerConnectionsInCall(callParticipant,
							callId).size() > 0;
			if (!hasParticipantAnsweredCall) {
				continue;
			}
			PacketPlayInCallParticipantsChanged callParticipantsChangedPacket = new PacketPlayInCallParticipantsChanged(
					callId, payload);
			for (Connection listeningParticipant : Skype.getPlugin()
					.getUserManager().getListeningConnections(callParticipant)) {
				listeningParticipant
						.getSocketHandlerContext()
						.getOutboundHandler()
						.write(listeningParticipant.getSocketHandlerContext(),
								callParticipantsChangedPacket);
			}
		}
		return PacketPlayInReply.empty();
	}

}
