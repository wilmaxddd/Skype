package codes.wilma24.Skype.server.v1_0_R1.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupUserRegistry;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class LookupUserRegistryCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutLookupUserRegistry packet = Packet.fromJson(
				msg.toString(), PacketPlayOutLookupUserRegistry.class);
		UUID authCode = packet.getAuthCode();
		Connection con = Skype.getPlugin().getUserManager()
				.getConnection(authCode);
		if (con == null) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		{
			String skypeName = con.getSkypeName();
			if (Skype.getPlugin().testRateLimitLookupUserRegistry(skypeName,
					ctx.getSocket().getInetAddress().getHostAddress())) {
				PacketPlayInReply replyPacket = new PacketPlayInReply(
						packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
								.getType().name() + " failed");
				return replyPacket;
			}
		}
		List<String> skypeNames = new ArrayList<String>();
		for (String key : Skype.getPlugin().getConfig()
				.getConfigurationSection("registry").getKeys(false)) {
			UUID participantId = UUID.fromString(key);
			Optional<String> skypeName = Skype.getPlugin().getUserManager()
					.getSkypeName(participantId);
			if (skypeName.isPresent()) {
				if (skypeName.get().startsWith("guest:")) {
					if (Skype.getPlugin().getUserManager()
							.isGroupChat(skypeName.get())) {
						continue;
					}
				}
				if (!skypeNames.contains(skypeName.get())) {
					skypeNames.add(skypeName.get());
				}
			}
		}
		if (skypeNames.size() == 0) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}
		PacketPlayInReply replyPacket = new PacketPlayInReply(packet.getType(),
				PacketPlayInReply.OK, GsonBuilder.create().toJson(skypeNames));
		return replyPacket;
	}

}
