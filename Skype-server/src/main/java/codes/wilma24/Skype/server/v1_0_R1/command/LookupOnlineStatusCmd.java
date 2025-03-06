package codes.wilma24.Skype.server.v1_0_R1.command;

import java.util.HashMap;
import java.util.Map;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInPing;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupOnlineStatus;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

import com.google.gson.Gson;

public class LookupOnlineStatusCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutLookupOnlineStatus packet = Packet.fromJson(
				msg.toString(), PacketPlayOutLookupOnlineStatus.class);
		UUID authCode = packet.getAuthCode();
		UUID[] conversationIds = packet.getConversationIds();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		Gson gson = GsonBuilder.create();
		Map<String, String> map = new HashMap<>();
		for (UUID conversationId : conversationIds) {
			map.put(conversationId.toString(), "false");
		}
		for (UUID authCode2 : Skype.getPlugin().getConnectionMap().keySet()
				.toArray(new UUID[0]).clone()) {
			Connection con2 = Skype.getPlugin().getUserManager()
					.getConnection(authCode2);
			if (con2 == null) {
				continue;
			}
			if (con2.isListening() == false) {
				continue;
			}
			for (UUID conversationId : conversationIds) {
				if (conversationId.equals(con2.getUniqueId())) {
					try {
						if (con2.getPong() != 0L && con2.getPing() != 0L) {
							if (con2.getPing() - con2.getPong() > (28.5 * (60 * 1000L))) {
								/**
								 * We want to kill the socket after 1.5 minutes
								 * 
								 * If they reply with a PONG packet before 1.5
								 * minutes it does not go offline
								 */
								con2.getSocketHandlerContext().getSocket()
										.close();
								Skype.getPlugin().getConnectionMap()
										.remove(authCode2);
								map.put(con2.getUniqueId().toString(), "false");
								break;
							}
						}
						con2.getSocketHandlerContext()
								.getSocket()
								.getOutputStream()
								.write(new PacketPlayInPing().toString()
										.getBytes("UTF-8"));
						con2.getSocketHandlerContext().getSocket()
								.getOutputStream().flush();
						if (con2.getPing() == 0) {
							con2.setPong(System.currentTimeMillis());
						}
						con2.setPing(System.currentTimeMillis());
					} catch (Exception e) {
						Skype.getPlugin().getConnectionMap().remove(authCode2);
						continue;
					}
					if (con2.getExpiryTime() - System.currentTimeMillis() < (28.5 * (60 * 1000L))) {
						/**
						 * We want to set the online status to offline after 1.5
						 * minutes
						 * 
						 * If they refresh the token before 1.5 minutes it does
						 * not go offline
						 */
						continue;
					}
					map.put(con2.getUniqueId().toString(), "true");
					break;
				}
			}

		}
		if (conversationIds.length == 1) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.OK, map.getOrDefault(
							conversationIds[0].toString(), "false").equals(
							"true") ? "ONLINE" : "OFFLINE");
			return replyPacket;
		} else {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.OK, gson.toJson(map));
			return replyPacket;
		}
	}
}
