package codes.wilma24.Skype.api.v1_0_R1.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.Timer;

import org.bouncycastle.openpgp.PGPPublicKeyRing;

import codes.wilma24.Skype.api.v1_0_R1.cryptography.CryptographicContext;
import codes.wilma24.Skype.api.v1_0_R1.cryptography.SimpleCryptographicContext;
import codes.wilma24.Skype.api.v1_0_R1.json.JsonManipulator;
import codes.wilma24.Skype.api.v1_0_R1.json.JsonManipulatorCurrent;

public class SocketHandlerContext {

	private List<Socket> socket;

	private SocketInboundHandler inboundHandler;

	private boolean inboundHandlerAdded = false;

	private SocketOutboundHandler outboundHandler;

	private boolean outboundHandlerAdded = false;

	private CryptographicContext cryptographicContext;

	private JsonManipulator jsonManipulator;

	private final UUID uniqueId = UUID.randomUUID();

	private PGPPublicKeyRing pubKey = null;

	public SocketHandlerContext(Socket socket) {
		this.setSocket(socket);

		try {
			/**
			 * If a read operation blocks for more then 8s, throw err
			 */
			socket.setSoTimeout(8000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		this.inboundHandler = new SocketInboundHandler();
		this.outboundHandler = new SocketOutboundHandler();
		this.cryptographicContext = new SimpleCryptographicContext();
		this.jsonManipulator = new JsonManipulatorCurrent();

		this.fireOutboundHandlerActive();
	}

	public Socket getSocket() {
		return socket.get(0);
	}
	
	public List<Socket> getSockets() {
		return socket;
	}

	private void setSocket(Socket socket) {
		ArrayList<Socket> sockets = new ArrayList<>();
		sockets.add(socket);
		this.socket = sockets;
	}
	
	public void addSocket(Socket socket) {
		this.socket.add(socket);
	}

	public SocketInboundHandler getInboundHandler() {
		return inboundHandler;
	}

	public boolean isInboundHandlerAdded() {
		return inboundHandlerAdded;
	}

	public SocketOutboundHandler getOutboundHandler() {
		return outboundHandler;
	}

	public boolean isOutboundHandlerAdded() {
		return outboundHandlerAdded;
	}

	public CryptographicContext getCryptographicContext() {
		return cryptographicContext;
	}

	public JsonManipulator getJsonManipulator() {
		return jsonManipulator;
	}

	public SocketHandlerContext fireInboundHandlerActive(Runnable callback) {
		SocketHandlerContext ctx = this;
		if (!inboundHandlerAdded) {
			inboundHandler.handlerAdded(ctx, callback);
			inboundHandlerAdded = true;
		}
		return ctx;
	}
	
	public SocketHandlerContext addSocketTimeoutExceptionCallback(Runnable callback) {
		SocketHandlerContext ctx = this;
		if (inboundHandlerAdded) {
			inboundHandler.callback2 = callback;
		}
		return ctx;
	}

	@Deprecated
	public SocketHandlerContext fireOutboundHandlerActive() {
		SocketHandlerContext ctx = this;
		if (!outboundHandlerAdded) {
			outboundHandler.handlerAdded(ctx);
			outboundHandlerAdded = true;
		}
		return ctx;
	}

	@Deprecated
	public SocketHandlerContext fireSocketActive(Runnable callback) {
		SocketHandlerContext ctx = this;
		if (!outboundHandlerAdded) {
			outboundHandler.handlerAdded(ctx);
			outboundHandlerAdded = true;
		}
		if (!inboundHandlerAdded) {
			inboundHandler.handlerAdded(ctx, callback);
			inboundHandlerAdded = true;
		}
		return ctx;
	}

	public SocketHandlerContext fireInboundHandlerInactive() {
		SocketHandlerContext ctx = this;
		if (inboundHandlerAdded) {
			inboundHandler.handlerRemoved(ctx);
			inboundHandlerAdded = false;
		}
		return ctx;
	}

	@Deprecated
	public SocketHandlerContext fireOutboundHandlerInactive() {
		SocketHandlerContext ctx = this;
		if (outboundHandlerAdded) {
			outboundHandler.handlerRemoved(ctx);
			outboundHandlerAdded = false;
		}
		return ctx;
	}

	@Deprecated
	public SocketHandlerContext fireSocketInactive() {
		SocketHandlerContext ctx = this;
		if (outboundHandlerAdded) {
			outboundHandlerAdded = false;
			outboundHandler.handlerRemoved(ctx);
		}
		if (inboundHandlerAdded) {
			inboundHandlerAdded = false;
			inboundHandler.handlerRemoved(ctx);
		}
		return ctx;
	}

	@Deprecated
	public void start(Runnable callback) {
		fireSocketActive(callback);
	}

	@Deprecated
	public void stop() {
		fireSocketInactive();
	}

	@Deprecated
	public UUID getUniqueId() {
		return uniqueId;
	}

	public PGPPublicKeyRing getPubKey() {
		return pubKey;
	}

	public void setPubKey(PGPPublicKeyRing pubKey) {
		this.pubKey = pubKey;
	}

}
