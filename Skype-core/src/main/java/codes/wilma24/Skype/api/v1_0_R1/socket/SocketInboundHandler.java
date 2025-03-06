package codes.wilma24.Skype.api.v1_0_R1.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.SwingUtilities;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandMap;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;

public class SocketInboundHandler extends SocketHandler {

	protected Thread thread;

	protected Runnable callback;

	protected Runnable callback2;

	@Override
	public void exceptionCaught(SocketHandlerContext ctx, Throwable cause) {
		if (cause instanceof SocketTimeoutException) {
			if (callback2 == null) {
				return;
			}
			callback2.run();
			return;
		}
		cause.printStackTrace();
	}

	@Override
	public void handlerAdded(SocketHandlerContext ctx, Runnable callback) {
		if (thread != null) {
			/**
			 * Handler was added previously, we do not need to add it again
			 */
			return;
		}
		this.callback = callback;
		thread = new Thread(
				() -> {
					Socket socket = ctx.getSocket();
					while (true) {
						int r;
						byte[] b = new byte[32768];
						StringBuilder sb = new StringBuilder();
						List<String> validJsonStrings = new ArrayList<String>();
						try {
							while ((r = socket.getInputStream().read(b, 0,
									b.length)) > 0) {
								sb.append(new String(Arrays.copyOf(b, r),
										StandardCharsets.UTF_8));
								if (!sb.toString().contains("}{")
										&& ctx.getJsonManipulator()
												.validateJsonStrict(
														sb.toString())) {
									/**
									 * String buffer is validated as being a
									 * json string
									 * 
									 * Break read loop so we do not have to wait
									 * for timeout
									 */
									validJsonStrings.add(sb.toString());
									break;
								} else if (sb.toString().startsWith("{")
										&& sb.toString().endsWith("}")) {
									/**
									 * If the latency is too low packets may
									 * become clumped with other packets
									 * 
									 * We can fix this issue by breaking them up
									 * and parsing them separate
									 */
									String[] split2 = sb.toString().split(
											"\\}\\{");
									for (String line : split2) {
										String f = line;
										if (!f.startsWith("{")) {
											f = "{" + f;
										}
										if (!line.endsWith("}")) {
											f = f + "}";
										}
										if (ctx.getJsonManipulator()
												.validateJsonStrict(
														f.toString())) {
											validJsonStrings.add(f);
										}
									}
									if (validJsonStrings.size() > 0) {
										break;
									}
								}
							}
						} catch (SocketTimeoutException e) {
							this.exceptionCaught(ctx, e);
							continue;
						} catch (SocketException e) {
							/**
							 * Connection reset?
							 */
							break;
						} catch (IOException e) {
							/**
							 * Socket is no longer active, remove inbound
							 * handler
							 */
							e.printStackTrace();
							break;
						}
						if (validJsonStrings.size() == 0 && sb.length() == 0) {
							/**
							 * Socket is no longer active, remove inbound
							 * handler
							 */
							break;
						}
						for (String json : validJsonStrings) {
							
							SwingUtilities.invokeLater(() -> {
								if (ctx.getJsonManipulator().validateJsonStrict(
										json)) {
									/**
									 * String buffer is validated as being a json
									 * string
									 * 
									 * We will now call our socketRead(ctx, msg)
									 * method
									 */
									this.socketRead(ctx, json);
								} else {
									/**
									 * Socket did not provide a valid json string
									 * before timeout
									 */
								}
							});
						}
					}
					this.handlerRemoved(ctx);
				});
		thread.start();
	}

	@Override
	public void handlerRemoved(SocketHandlerContext ctx) {
		thread.stop();
		if (callback != null) {
			callback.run();
		}
	}

	private void socketRead(SocketHandlerContext ctx, Object msg) {
		boolean logging = true;
		if (System.getProperty("logging") != null) {
			logging = Boolean.parseBoolean(System.getProperty("logging"));
		}
		if (logging)
			System.out.println(Optional.of(msg.toString()));
		PacketPlayInReply replyPacket = CommandMap.dispatch(ctx, msg);
		if (replyPacket == null) {
			return;
		}
		// ctx.getOutboundHandler().write(ctx, replyPacket);
		msg = replyPacket;
		Socket socket = ctx.getSocket();
		try {
			socket.getOutputStream().write(
					msg.toString().getBytes(StandardCharsets.UTF_8));
			socket.getOutputStream().flush();
		} catch (IOException cause) {
			exceptionCaught(ctx, cause);
			handlerRemoved(ctx); // kill thread on err
		}
	}
}
