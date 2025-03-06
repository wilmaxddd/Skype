package codes.wilma24.Skype.server.v1_0_R1.command;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.openpgp.PGPException;
import org.pgpainless.exception.KeyException;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLogin;
import codes.wilma24.Skype.api.v1_0_R1.pgp.PGPUtilities;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class LoginCmd extends CommandExecutor {

	@Override
	public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
		PacketPlayOutLogin packet = Packet.fromJson(msg.toString(),
				PacketPlayOutLogin.class);
		String skypeName = packet.getSkypeName();
		if (packet.getAuthCode() == null) {
			if (ctx.getPubKey() == null) {
				PacketPlayInReply replyPacket = new PacketPlayInReply(
						packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
								.getType().name() + " failed");
				return replyPacket;
			}
			try {
				packet.setPassword(PGPUtilities.decryptAndVerify(
						packet.getPassword(),
						PGPUtilities.createOrLookupPrivateKey(),
						ctx.getPubKey()).getMessage());
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (PGPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String password = packet.getPassword();

			if (!Skype.getPlugin().getUserManager()
					.validatePassword(ctx, skypeName, password)
					&& !Skype.getPlugin().getTokenMap().containsKey(password)) {
				PacketPlayInReply replyPacket = new PacketPlayInReply(
						packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
								.getType().name() + " failed");
				return replyPacket;
			}
		} else {
			UUID authCode = packet.getAuthCode();
			Connection con = Skype.getPlugin().getUserManager()
					.getConnection(authCode);
			if (con == null) {
				PacketPlayInReply replyPacket = new PacketPlayInReply(
						packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
								.getType().name() + " failed");
				return replyPacket;
			}
			if (packet.getSkypeName() == null
					|| packet.getSkypeName().equals(con.getSkypeName())) {
				skypeName = con.getSkypeName();
			} else {
				if (!Skype.getPlugin().getUserManager()
						.isGroupChat(packet.getSkypeName())) {
					PacketPlayInReply replyPacket = new PacketPlayInReply(
							packet.getType(), PacketPlayInReply.BAD_REQUEST,
							packet.getType().name() + " failed");
					return replyPacket;
				}
				UUID conversationId = Skype.getPlugin().getUserManager()
						.getUniqueId(packet.getSkypeName());
				Optional<List<UUID>> groupChatAdmins = Skype.getPlugin()
						.getConversationManager()
						.getGroupChatAdmins(conversationId);
				if (!groupChatAdmins.isPresent()) {
					PacketPlayInReply replyPacket = new PacketPlayInReply(
							packet.getType(), PacketPlayInReply.BAD_REQUEST,
							packet.getType().name() + " failed");
					return replyPacket;
				}
				boolean hit = false;
				for (UUID participantId : groupChatAdmins.get()) {
					if (Skype.getPlugin().getUserManager()
							.getUniqueId(con.getSkypeName())
							.equals(participantId)) {
						skypeName = packet.getSkypeName();
						hit = true;
					}
				}
				if (!hit) {
					PacketPlayInReply replyPacket = new PacketPlayInReply(
							packet.getType(), PacketPlayInReply.BAD_REQUEST,
							packet.getType().name() + " failed");
					return replyPacket;
				}
			}
		}

		if (packet.getProtocolVersion() != PacketPlayOutLogin.PROTOCOL_VERSION) {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		}

		UUID authCode = UUID.randomUUID();

		/**
		 * We want to set the auth code to expire after 30 minutes
		 * 
		 * If they refresh the token before 30 minutes it does not expire
		 */
		long expiryTime = System.currentTimeMillis() + (30 * (60 * 1000L));

		/**
		 * Store the connection in memory for reference
		 */
		Connection con = new Connection(authCode, expiryTime, skypeName, ctx);
		Skype.getPlugin().getConnectionMap().put(authCode, con);

		/**
		 * Construct reply packet with the text being the auth code
		 */
		if (packet.getAuthCode() == null) {
			String token = UUID.randomUUID().getUUID().toString();
			Skype.getPlugin().getTokenMap().put(token, skypeName);
			PacketPlayInLogin loginPacket = new PacketPlayInLogin(authCode
					.getUUID().toString(), token);
			try {
				String text = PGPUtilities.encryptAndSign(
						loginPacket.toString(),
						PGPUtilities.createOrLookupPrivateKey(),
						ctx.getPubKey());
				PacketPlayInReply replyPacket = new PacketPlayInReply(
						packet.getType(), PacketPlayInReply.OK, text);
				return replyPacket;
			} catch (KeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (PGPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.BAD_REQUEST, packet
							.getType().name() + " failed");
			return replyPacket;
		} else {
			PacketPlayInReply replyPacket = new PacketPlayInReply(
					packet.getType(), PacketPlayInReply.OK, authCode.getUUID()
							.toString());
			return replyPacket;
		}
	}

}
