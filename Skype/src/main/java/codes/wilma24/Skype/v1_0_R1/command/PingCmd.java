package codes.wilma24.Skype.v1_0_R1.command;

import java.util.Optional;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInPing;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutPong;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;
import codes.wilma24.Skype.v1_0_R1.plugin.Skype;

public class PingCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayInPing packet = Packet.fromJson(msg.toString(),
				PacketPlayInPing.class);
		Optional<SocketHandlerContext> ctx2 = Skype.getPlugin().createHandle();
		if (!ctx2.isPresent()) {
			return PacketPlayInReply.empty();
		}
		UUID authCode = MainForm.get().getAuthCode();
		ctx2.get().getOutboundHandler()
				.write(ctx2.get(), new PacketPlayOutPong(authCode));
		return PacketPlayInReply.empty();
	}
}
