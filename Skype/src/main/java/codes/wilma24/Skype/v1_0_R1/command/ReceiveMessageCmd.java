package codes.wilma24.Skype.v1_0_R1.command;

import java.util.Date;
import java.util.Optional;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupUser;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutMarkConversationAsRead;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.v1_0_R1.AppDelegate;
import codes.wilma24.Skype.v1_0_R1.data.types.Contact;
import codes.wilma24.Skype.v1_0_R1.data.types.Conversation;
import codes.wilma24.Skype.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.v1_0_R1.data.types.MessageType;
import codes.wilma24.Skype.v1_0_R1.forms.MainForm;
import codes.wilma24.Skype.v1_0_R1.forms.NotificationForm;
import codes.wilma24.Skype.v1_0_R1.plugin.Skype;

public class ReceiveMessageCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayInMessage packet = Packet.fromJson(msg.toString(),
				PacketPlayInMessage.class);
		UUID authCode = MainForm.get().getAuthCode();
		Contact loggedInUser = MainForm.get().getLoggedInUser();
		UUID conversationId = packet.getConversationId();
		UUID participantId = packet.getParticipantId();
		String json = packet.getPayload().toString();
		Message message = new Message(json);
		if (message.getSender().equals(loggedInUser.getUniqueId())) {
			return PacketPlayInReply.empty();
		}
		boolean hit = false;
		Conversation _conversation = null;
		for (Conversation conversation : MainForm.get().getConversations()) {
			if (conversation.getUniqueId().equals(conversationId)) {
				message.setConversation(conversation);
				boolean hit2 = false;
				for (Message message2 : conversation.getMessages()
						.toArray(new Message[0]).clone()) {
					if (message2.getUniqueId().equals(message.getUniqueId())) {
						message2.setMessage(message.getMessage());
						message2.setDecryptedMessage(null);
						hit2 = true;
						break;
					}
				}
				if (!hit2) {
					conversation.getMessages().add(message);
				}
				conversation.setNotificationCount(conversation
						.getNotificationCount() + 1);
				conversation.setLastModified(new Date(new Date().getTime()
						+ AppDelegate.TIME_OFFSET));
				if (message.getMessageType() != null) {
					if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
						if (!message.getSender().equals(
								loggedInUser.getUniqueId())) {
							conversation.setHasIncomingFriendRequest(true,
									message);
						} else {
							conversation.setHasOutgoingFriendRequest(true);
						}
					}
					if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST
							|| message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
						if (message.getSender().equals(
								loggedInUser.getUniqueId())) {
							conversation.setHasIncomingFriendRequest(false,
									null);
						} else {
							conversation.setHasOutgoingFriendRequest(false);
						}
					}
				}
				_conversation = conversation;
				hit = true;
			}
		}
		if (hit == false) {
			Conversation conversation = MainForm.get()
					.getSelectedConversation();
			if (conversation != null) {
				if (conversation.getUniqueId().equals(conversationId)) {
					message.setConversation(conversation);
					boolean hit2 = false;
					for (Message message2 : conversation.getMessages()
							.toArray(new Message[0]).clone()) {
						if (message2.getUniqueId()
								.equals(message.getUniqueId())) {
							message2.setMessage(message.getMessage());
							message2.setDecryptedMessage(null);
							hit2 = true;
							break;
						}
					}
					if (!hit2) {
						conversation.getMessages().add(message);
					}
					conversation.setNotificationCount(conversation
							.getNotificationCount() + 1);
					conversation.setLastModified(new Date(new Date().getTime()
							+ AppDelegate.TIME_OFFSET));
					_conversation = conversation;
					MainForm.get().getConversations().add(conversation);
					hit = true;
				}
			}
		}
		if (hit == false) {
			Optional<SocketHandlerContext> socketHandlerContext = Skype
					.getPlugin().createHandle();
			if (!socketHandlerContext.isPresent()) {
				return PacketPlayInReply.empty();
			}
			Optional<PacketPlayInReply> replyPacket = socketHandlerContext
					.get()
					.getOutboundHandler()
					.dispatch(
							socketHandlerContext.get(),
							new PacketPlayOutLookupUser(authCode,
									conversationId));
			if (!replyPacket.isPresent()) {
				return PacketPlayInReply.empty();
			}
			Conversation conversation = new Conversation(replyPacket.get()
					.getText());
			if (conversation.isGroupChat()) {
				message.setConversation(conversation);
			} else {
				conversation.setDisplayName(conversation.getSkypeName());
			}
			conversation.getMessages().add(message);
			conversation.setNotificationCount(1);
			conversation.setLastModified(new Date(new Date().getTime()
					+ AppDelegate.TIME_OFFSET));
			_conversation = conversation;
			MainForm.get().getConversations().add(conversation);
		}
		if (MainForm.get().getSelectedConversation() != null) {
			if (MainForm.get().getSelectedConversation().getUniqueId() == _conversation
					.getUniqueId()) {
				Optional<SocketHandlerContext> ctx2 = Skype.getPlugin()
						.createHandle();
				if (ctx2.isPresent()) {
					ctx2.get()
							.getOutboundHandler()
							.write(ctx2.get(),
									new PacketPlayOutMarkConversationAsRead(
											authCode, _conversation
													.getUniqueId()));
				}
			}
		}
		NotificationForm notif = new NotificationForm(_conversation, message,
				true);
		notif.show();
		MainForm.get().refreshWindow();
		return PacketPlayInReply.empty();
	}
}
