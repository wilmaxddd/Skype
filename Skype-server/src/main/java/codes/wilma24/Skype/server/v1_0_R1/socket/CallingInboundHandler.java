package codes.wilma24.Skype.server.v1_0_R1.socket;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import codes.wilma24.Skype.api.v1_0_R1.data.types.Call;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInCallEndedBeforePickup;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInCallParticipantsChanged;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

public class CallingInboundHandler implements Runnable {

	private UUID loggedInUser;
	private Call call;
	private SocketHandlerContext ctx;
	private Connection con2;
	private Socket socket;

	public CallingInboundHandler(Call call, UUID participantId,
			SocketHandlerContext ctx, Connection con) {
		this.call = call;
		this.loggedInUser = participantId;
		this.ctx = ctx;
		this.con2 = con;
		this.socket = ctx.getSocket();
	}

	public void run2(byte[] b, int len) {
		for (UUID callParticipant : call.getParticipants().toArray(new UUID[0])
				.clone()) {
			if (callParticipant.equals(loggedInUser)) {
				continue;
			}
			for (Connection con : Skype
					.getPlugin()
					.getUserManager()
					.getListeningDataStreamConnectionsInCall(callParticipant,
							call.getCallId()).toArray(new Connection[0])
					.clone()) {
				if (!con.getCallId().isPresent()) {
					continue;
				}
				if (!con.getReceivingCallDataStreamParticipantId().isPresent()) {
					continue;
				}
				UUID receivingCallId = con.getCallId().get();
				UUID receivingCallDataStreamParticipantId = con
						.getReceivingCallDataStreamParticipantId().get();
				if (receivingCallId.equals(call.getCallId())) {
					if (receivingCallDataStreamParticipantId
							.equals(this.loggedInUser)) {
						try {
							b = Arrays.copyOf(b, len);
							con.getSocketHandlerContext().getSocket()
									.getOutputStream().write(b, 0, len);
							con.getSocketHandlerContext().getSocket()
									.getOutputStream().flush();
						} catch (Exception e) {
							e.printStackTrace();
							call.removeParticipant(callParticipant);
							con.setCallDataStream(null, null);
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			socket.setSoTimeout(0);
			ServerSocket server2 = null;
			boolean err = false;
			int port = 0;
			do {
				try {
					Random random = new Random();
					port = random.nextInt(5000) + 40000;
					server2 = new ServerSocket(port);
					err = false;
				} catch (Exception e) {
					err = true;
					e.printStackTrace();
				}
			} while (err == true);
			final ServerSocket server = server2;
			InputStream dis = socket.getInputStream();
			OutputStream dos = socket.getOutputStream();
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					Socket socket = null;
					DataInputStream dis = null;
					try {
						socket = server.accept();
						socket.setSoTimeout(0);
						dos.close();
						ctx.addSocket(socket);
						dis = new DataInputStream(socket.getInputStream());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					while (true) {
						try {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							byte[] b = new byte[1616];
							dis.readFully(b);
							baos.write(b);
							run2(baos.toByteArray(), baos.size());
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					List<String> skypeNames = new ArrayList<>();
					for (UUID callParticipant : call.getParticipants()
							.toArray(new UUID[0]).clone()) {
						for (Connection con : Skype
								.getPlugin()
								.getUserManager()
								.getListeningDataStreamConnectionsInCall(
										callParticipant, call.getCallId())
								.toArray(new Connection[0]).clone()) {
							if (!con.getCallId().isPresent()) {
								continue;
							}
							if (!con.getReceivingCallDataStreamParticipantId()
									.isPresent()) {
								continue;
							}
							UUID receivingCallId = con.getCallId().get();
							UUID receivingCallDataStreamParticipantId = con
									.getReceivingCallDataStreamParticipantId()
									.get();
							if (receivingCallId.equals(call.getCallId())) {
								if (!con.isCallDataStreamEnded()) {
									if (!skypeNames.contains(con.getSkypeName())) {
										if (!con.getSkypeName().equals(
												con2.getSkypeName())) {
											skypeNames.add(con.getSkypeName());
										}
									}
								}
								if (receivingCallDataStreamParticipantId
										.equals(loggedInUser)) {
									con.setCallDataStreamEnded(true);
								}
							}
						}
					}
					List<String> participantIds = new ArrayList<>();
					for (String skypeName : skypeNames) {
						participantIds.add(Skype.getPlugin().getUserManager()
								.getUniqueId(skypeName).toString());
					}
					Object payload = GsonBuilder.create()
							.toJson(participantIds);
					for (UUID callParticipant : call.getParticipants()) {
						boolean hasParticipantAnsweredCall = Skype
								.getPlugin()
								.getUserManager()
								.getCallingInboundHandlerConnectionsInCall(
										callParticipant, call.getCallId())
								.size() > 0
								|| Skype.getPlugin()
										.getUserManager()
										.getListeningDataStreamConnectionsInCall(
												callParticipant,
												call.getCallId()).size() > 0;
						if (!hasParticipantAnsweredCall) {
							continue;
						}
						PacketPlayInCallParticipantsChanged callParticipantsChangedPacket = new PacketPlayInCallParticipantsChanged(
								call.getCallId(), payload);
						for (Connection listeningParticipant : Skype
								.getPlugin().getUserManager()
								.getListeningConnections(callParticipant)) {
							listeningParticipant
									.getSocketHandlerContext()
									.getOutboundHandler()
									.write(listeningParticipant
											.getSocketHandlerContext(),
											callParticipantsChangedPacket);
						}
					}
					if (skypeNames.size() < 2) {
						for (UUID callParticipant : call
								.getRingingParticipants().toArray(new UUID[0])
								.clone()) {
							PacketPlayInCallEndedBeforePickup callEndedBeforePickupPacket = new PacketPlayInCallEndedBeforePickup(
									loggedInUser, call.getCallId());
							for (Connection listeningParticipant : Skype
									.getPlugin().getUserManager()
									.getListeningConnections(callParticipant)) {
								listeningParticipant
										.getSocketHandlerContext()
										.getOutboundHandler()
										.write(listeningParticipant
												.getSocketHandlerContext(),
												callEndedBeforePickupPacket);
							}
						}
						for (UUID callParticipant : call.getParticipants()
								.toArray(new UUID[0]).clone()) {
							for (Connection con2 : Skype
									.getPlugin()
									.getUserManager()
									.getAllConnectionsInCall(callParticipant,
											call.getCallId())
									.toArray(new Connection[0]).clone()) {
								try {
									for (Socket socket2 : con2
											.getSocketHandlerContext()
											.getSockets()) {
										socket2.close();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					call.removeParticipant(loggedInUser);
					Skype.getPlugin().getConnectionMap()
							.remove(con2.getAuthCode(), con2);
					try {
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
			while (true) {
				dis.read(new byte[1024]);
				dos.write((port + "").getBytes());
				dos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
