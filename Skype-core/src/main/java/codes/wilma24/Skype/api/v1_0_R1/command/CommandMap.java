package codes.wilma24.Skype.api.v1_0_R1.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketType;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;

public class CommandMap {

	private static Map<PacketType, CommandExecutor> registry = new HashMap<>();

	private static Optional<CommandExecutor> getPacketHandler(
			PacketType packetType) {
		if (registry.containsKey(packetType)) {
			return Optional.of(registry.get(packetType));
		} else {
			return Optional.empty();
		}
	}

	public static PacketPlayInReply dispatch(SocketHandlerContext ctx,
			Object msg) {
		PacketType typeOfPacket = Packet.fromJson(msg.toString(), Packet.class)
				.getType();
		Optional<CommandExecutor> packetHandler = CommandMap
				.getPacketHandler(typeOfPacket);
		if (packetHandler.isPresent()) {
			PacketPlayInReply replyPacket = packetHandler.get().onCommand(ctx,
					msg);
			return replyPacket;
		} else {
			PacketPlayInReply replyPacket = new PacketPlayInReply(typeOfPacket,
					PacketPlayInReply.NOT_IMPLEMENTED, typeOfPacket.name()
							+ " is not yet implemented");
			return replyPacket;
		}
	}

	public static boolean register(PacketType packetType,
			CommandExecutor commandExecutor) {
		if (registry.containsKey(packetType)) {
			return false;
		} else {
			registry.put(packetType, commandExecutor);
			return true;
		}
	}

}
