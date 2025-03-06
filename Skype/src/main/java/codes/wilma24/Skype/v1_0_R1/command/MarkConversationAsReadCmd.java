package codes.wilma24.Skype.v1_0_R1.command;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInMarkConversationAsRead;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.v1_0_R1.data.types.Conversation;
import codes.wilma24.Skype.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;

public class MarkConversationAsReadCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayInMarkConversationAsRead packet = Packet.fromJson(
				msg.toString(), PacketPlayInMarkConversationAsRead.class);
		UUID conversationId = packet.getConversationId();
		for (Conversation conversation : MainForm.get().getConversations()
				.toArray(new Conversation[0])) {
			if (conversation.getUniqueId().equals(conversationId)) {
				for (Message message : conversation.getMessages()) {
					if (!message.isRead()) {
						message.setRead(System.currentTimeMillis());
					}
				}
			}
		}
		MainForm.get().refreshWindow();
		return PacketPlayInReply.empty();
	}
}
