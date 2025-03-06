package codes.wilma24.SkypeChatViewer.v1_0_R1.plugin;

import static java.lang.System.getProperty;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.pgpainless.PGPainless;
import org.pgpainless.algorithm.DocumentSignatureType;
import org.pgpainless.algorithm.KeyFlag;
import org.pgpainless.decryption_verification.ConsumerOptions;
import org.pgpainless.decryption_verification.DecryptionStream;
import org.pgpainless.decryption_verification.MessageMetadata;
import org.pgpainless.encryption_signing.EncryptionOptions;
import org.pgpainless.encryption_signing.EncryptionStream;
import org.pgpainless.encryption_signing.ProducerOptions;
import org.pgpainless.encryption_signing.SigningOptions;
import org.pgpainless.exception.KeyException;
import org.pgpainless.key.generation.KeySpec;
import org.pgpainless.key.generation.type.ecc.EllipticCurve;
import org.pgpainless.key.generation.type.ecc.ecdh.ECDH;
import org.pgpainless.key.generation.type.ecc.ecdsa.ECDSA;
import org.pgpainless.key.generation.type.rsa.RSA;
import org.pgpainless.key.generation.type.rsa.RsaLength;
import org.pgpainless.key.protection.SecretKeyRingProtector;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutAcceptContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupMessageHistory;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutPubKeyExchange;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRefreshToken;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRegister;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutUpdateUser;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.headless.v1_0_R1.Conversation;
import codes.wilma24.Skype.v1_0_R1.AppDelegate;
import codes.wilma24.Skype.v1_0_R1.data.types.Contact;
import codes.wilma24.Skype.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.v1_0_R1.data.types.Status;
import codes.wilma24.Skype.v1_0_R1.plugin.Skype;

import com.google.gson.Gson;

public class SkypeChatImporter extends JDialog implements Runnable {

	private JLabel label;
	private JProgressBar progressBar;
	private JProgressBar progressBar2;

	private Thread thread = null;

	private JFrame parent;

	private File pathToData;

	private String password;

	private Runnable callback;

	private SocketHandlerContext ctx;

	private HashMap<String, UUID> skypeNameAuthCodes = new HashMap<>();

	private HashMap<String, ArrayList<String>> skypeNameContacts = new HashMap<>();

	private HashMap<String, UUID> skypeNameUniqueIds = new HashMap<>();

	private HashMap<UUID, Contact> userMap = new HashMap<>();

	private HashMap<UUID, ArrayList<String>> messageMap = new HashMap<>();

	private Contact loggedInUser = null;

	private PGPUtilities pgpUtilities;

	private PGPPublicKeyRing pubKey = null;

	public SkypeChatImporter(JFrame parent, File pathToData,
			Contact loggedInUser, Runnable callback) {
		super(parent, true);
		this.parent = parent;
		this.callback = callback;
		this.loggedInUser = loggedInUser;
		this.pathToData = pathToData;
		this.pgpUtilities = new PGPUtilities();
	}

	private Optional<Contact> registerUser(String displayName, String username,
			String password) {
		PacketPlayOutRegister registerPacket = new PacketPlayOutRegister(
				displayName, username, password);
		registerPacket.setSilent(true);
		PacketPlayInReply replyPacket = ctx.getOutboundHandler()
				.dispatch(ctx, registerPacket).get();
		Optional<PacketPlayInReply> replyPacket3 = ctx.getOutboundHandler()
				.dispatch(ctx, new PacketPlayOutLogin(username, password));
		if (pubKey == null) {
			return Optional.empty();
		}
		UUID authCode = null;
		if (replyPacket3.isPresent()
				&& replyPacket3.get().getStatusCode() == 200) {
			String text = null;
			try {
				text = pgpUtilities.decryptAndVerify(
						replyPacket3.get().getText(),
						Skype.getPlugin().getPrivKey(), pubKey).getMessage();
			} catch (PGPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			PacketPlayInLogin loginPacket = Packet.fromJson(text,
					PacketPlayInLogin.class);
			authCode = UUID.fromString(loginPacket.getAuthCode());
		}
		if (authCode == null) {
			return Optional.empty();
		}
		UUID participantId = Skype.getPlugin().getUniqueId(username);
		if (replyPacket.getStatusCode() != 200) {
			if (!username.equals(loggedInUser.getSkypeName())) {
				Date now = new Date(new Date().getTime()
						+ AppDelegate.TIME_OFFSET);
				Date startOfTime = new Date(2012 - 1900, 0, 1);
				PacketPlayOutLookupMessageHistory messageHistoryLookup = new PacketPlayOutLookupMessageHistory(
						authCode, loggedInUser.getUniqueId(), startOfTime, now);
				Optional<PacketPlayInReply> replyPacket2 = ctx
						.getOutboundHandler().dispatch(ctx,
								messageHistoryLookup);
				if (replyPacket2.isPresent()
						&& replyPacket2.get().getStatusCode() == 200) {
					Gson gson = GsonBuilder.create();
					List<String> messagePayloads = gson.fromJson(replyPacket2
							.get().getText(), List.class);
					ArrayList<String> messages = new ArrayList<>();
					for (String payload : messagePayloads) {
						Message message = new Message(payload);
						messages.add(message.getMessage().trim());
					}
					messagePayloads.clear();
					replyPacket2 = null;
					messageMap.put(participantId, messages);
				}
			}
		}
		skypeNameAuthCodes.put(username, authCode);
		ctx.getOutboundHandler().dispatch(ctx,
				new PacketPlayOutRefreshToken(authCode));
		Contact contact = new Contact();
		contact.setUniqueId(participantId);
		contact.setSkypeName(username);
		contact.setDisplayName(displayName);
		contact.setOnlineStatus(Status.OFFLINE);
		PacketPlayOutUpdateUser updateUserPacket = new PacketPlayOutUpdateUser(
				authCode, participantId, contact);
		updateUserPacket.setSilent(true);
		ctx.getOutboundHandler().dispatch(ctx, updateUserPacket);
		contact = new Contact(contact.toString());
		return Optional.of(contact);
	}

	private Contact updateUser(String username, String password,
			String newusername, String newdisplayname) {
		if (ctx == null) {
			ctx = Skype.getPlugin().createHandle().get();
		}
		PacketPlayInReply replyPacket = ctx
				.getOutboundHandler()
				.dispatch(
						ctx,
						new PacketPlayOutRegister(newdisplayname, username,
								password)).get();
		UUID authCode = UUID.fromString(ctx.getOutboundHandler()
				.dispatch(ctx, new PacketPlayOutLogin(username, password))
				.get().getText());
		UUID participantId = Skype.getPlugin().getUniqueId(username);
		skypeNameAuthCodes.put(username, authCode);
		Contact contact = new Contact();
		contact.setUniqueId(participantId);
		contact.setSkypeName(newusername);
		contact.setDisplayName(newdisplayname);
		contact.setOnlineStatus(Status.OFFLINE);
		ctx.getOutboundHandler().dispatch(ctx,
				new PacketPlayOutUpdateUser(authCode, participantId, contact));
		contact = new Contact(contact.toString());
		return contact;
	}

	public void sendMessage(Contact arg0, Contact arg1, long timestamp,
			String message, String password) {
		if (messageMap.containsKey(arg1.getUniqueId())) {
			for (String payload : messageMap.get(arg1.getUniqueId())) {
				if (message.trim().equals(payload)) {
					return;
				}
			}
		}
		if (messageMap.containsKey(arg0.getUniqueId())) {
			for (String payload : messageMap.get(arg0.getUniqueId())) {
				if (message.trim().equals(payload)) {
					return;
				}
			}
		}
		ArrayList<String> contacts = skypeNameContacts.getOrDefault(
				arg0.getSkypeName(), new ArrayList<>());
		if (contacts.contains(arg1.getSkypeName()) == false) {
			/*
			 * Send contact request
			 */
			{
				UUID authCode = skypeNameAuthCodes.get(arg0.getSkypeName());
				if (authCode == null) {
					return;
				}
				ctx.getOutboundHandler().dispatch(
						ctx,
						new PacketPlayOutSendContactRequest(authCode, arg1
								.getUniqueId()));
			}
			/*
			 * Accept contact request
			 */
			{
				UUID authCode = skypeNameAuthCodes.get(arg1.getSkypeName());
				if (authCode == null) {
					return;
				}
				ctx.getOutboundHandler().dispatch(
						ctx,
						new PacketPlayOutAcceptContactRequest(authCode, arg0
								.getUniqueId()));
			}
			contacts.add(arg1.getSkypeName());
			skypeNameContacts.put(arg0.getSkypeName(), contacts);
		}
		/*
		 * Send message
		 */
		{
			UUID authCode = skypeNameAuthCodes.get(arg0.getSkypeName());
			if (authCode == null) {
				return;
			}
			UUID messageId = UUID.randomUUID();
			Message payload = new Message(messageId, arg0.getUniqueId(),
					message, timestamp, arg1);
			ctx.getOutboundHandler().dispatch(
					ctx,
					new PacketPlayOutSendMessage(authCode, arg1.getUniqueId(),
							messageId, payload, timestamp));
		}
	}

	@Deprecated
	private Contact readLoggedInUser() throws IOException {
		Contact loggedInUser = null;
		outerLoop: for (File a : pathToData.listFiles()) {
			if (!a.isDirectory()) {
				continue;
			}
			String displayName = null;
			String username = null;
			ArrayList<codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message> messages = new ArrayList<>();
			for (File b : a.listFiles()) {
				if (b.getName().equals("info.txt")) {
					String[] txt = new String(Files.readAllBytes(b.toPath()),
							StandardCharsets.UTF_8).split("\\r?\\n");
					displayName = txt[0];
					username = txt[1].replace("8:", "").trim();
					Contact contact = new Contact();
					UUID participantId = Skype.getPlugin()
							.getUniqueId(username);
					contact.setUniqueId(participantId);
					contact.setSkypeName(username);
					contact.setDisplayName(displayName);
					contact.setOnlineStatus(Status.OFFLINE);
					skypeNameUniqueIds.put(username, contact.getUniqueId());
					userMap.put(contact.getUniqueId(), contact);
					continue;
				}
				codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message message = new codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message(
						new String(Files.readAllBytes(b.toPath()),
								StandardCharsets.UTF_8));
				if (skypeNameUniqueIds.containsKey(message.getUsername()
						.replace("8:", "")) == false) {
					Contact contact = new Contact();
					UUID participantId = Skype.getPlugin().getUniqueId(
							message.getUsername().replace("8:", ""));
					contact.setUniqueId(participantId);
					contact.setSkypeName(message.getUsername()
							.replace("8:", ""));
					contact.setDisplayName(message.getDisplayName());
					contact.setOnlineStatus(Status.OFFLINE);
					skypeNameUniqueIds.put(contact.getSkypeName(),
							contact.getUniqueId());
					userMap.put(contact.getUniqueId(), contact);
				}
				messages.add(message);
			}

			for (codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message message : messages) {
				UUID participantId = skypeNameUniqueIds.get(username);
				Contact contact = userMap.get(participantId);
				UUID conversationId = skypeNameUniqueIds.get(message
						.getUsername().replace("8:", ""));
				Contact sender = userMap.get(conversationId);
				if (sender.getUniqueId().equals(contact.getUniqueId()) == false) {
					loggedInUser = sender;
					break outerLoop;
				}
			}
			messages.clear();
		}
		this.skypeNameAuthCodes.clear();
		this.skypeNameContacts.clear();
		this.skypeNameUniqueIds.clear();
		this.userMap.clear();
		this.messageMap.clear();
		return loggedInUser;
	}

	@Override
	public void show() {
		this.label = new JLabel("Importing data");
		this.progressBar = new JProgressBar();
		this.progressBar.setPreferredSize(new Dimension(220, 20));
		this.progressBar2 = new JProgressBar();
		this.progressBar2.setPreferredSize(new Dimension(220, 20));
		JPanel panel = new JPanel();
		JPanel yLayout = new JPanel();
		yLayout.setLayout(new BoxLayout(yLayout, BoxLayout.Y_AXIS));
		JPanel labelPanel = new JPanel();
		labelPanel.add(label);
		yLayout.add(labelPanel);
		yLayout.add(progressBar);
		yLayout.add(progressBar2);
		panel.add(yLayout);
		this.add(panel);
		if (parent == null) {
			this.setTitle("Import Data");
		} else {
			this.setTitle(parent.getTitle());
		}
		this.setResizable(false);
		if (callback == null) {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					thread.stop();
					dispose();
				}
			});
		}
		this.pack();
		this.setLocationRelativeTo(null);
		if (password == null) {
			password = System.getProperty("password");
			if (password == null) {
				password = System.getenv("password");
				if (password == null) {
					password = JOptionPane.showInputDialog(parent, "password");
					if (password == null) {
						return;
					}
					System.setProperty("password", password);
				}
			}
		}
		Thread thread = new Thread(this);
		thread.start();
		super.show();
	}

	private void importData(String password) throws Exception {
		String oldSkypeName = this.readLoggedInUser().getSkypeName();
		ctx = Skype.getPlugin().createHandle().get();
		{
			Optional<PacketPlayInReply> replyPacket = ctx.getOutboundHandler()
					.dispatch(
							ctx,
							new PacketPlayOutPubKeyExchange(Skype.getPlugin()
									.getPubKey()));
			boolean logging = true;
			if (System.getProperty("logging") != null) {
				logging = Boolean.parseBoolean(System.getProperty("logging"));
			}
			if (logging)
				System.out.println(replyPacket);
			if (replyPacket.isPresent()) {
				if (replyPacket.get().getStatusCode() == 200) {
					try {
						pubKey = PGPainless.readKeyRing().publicKeyRing(
								replyPacket.get().getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		try {
			password = pgpUtilities.encryptAndSign(password, Skype.getPlugin()
					.getPrivKey(), pubKey);
		} catch (KeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(() -> {
			label.setText("Importing data");
			progressBar.setValue(0);
			progressBar2.setValue(0);
		});
		File[] users = pathToData.listFiles();
		progressBar.setMaximum(users.length);
		loop3: for (int i = 0; i < users.length; i++) {
			File a = users[i];
			if (!a.isDirectory()) {
				continue;
			}
			final int fi = i;
			SwingUtilities.invokeLater(() -> {
				progressBar.setValue(fi);
				progressBar2.setValue(0);
			});
			String displayName = null;
			String username = null;
			ArrayList<codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message> messages = new ArrayList<>();
			for (File b : a.listFiles()) {
				if (b.getName().equals("info.txt")) {
					String[] txt = new String(Files.readAllBytes(b.toPath()),
							StandardCharsets.UTF_8).split("\\r?\\n");
					displayName = txt[0];
					username = txt[1].replace("8:", "").trim();
					if (username.equals(oldSkypeName)) {
						username = loggedInUser.getSkypeName();
						displayName = loggedInUser.getDisplayName();
					}
					Optional<Contact> contact = registerUser(displayName,
							username, password);
					if (!contact.isPresent()) {
						JOptionPane.showMessageDialog(this, "LOGIN failed"
								+ '\n' + "displayName=" + displayName + '\n'
								+ "username=" + username + '\n' + "password="
								+ password);
						continue loop3;
					}
					skypeNameUniqueIds.put(username, contact.get()
							.getUniqueId());
					userMap.put(contact.get().getUniqueId(), contact.get());
					continue;
				}
				codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message message = new codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message(
						new String(Files.readAllBytes(b.toPath()),
								StandardCharsets.UTF_8));
				if (message.getUsername().equals(oldSkypeName)) {
					message.setUsername(loggedInUser.getSkypeName());
					message.setDisplayName(loggedInUser.getDisplayName());
				}
				if (skypeNameUniqueIds.containsKey(message.getUsername()
						.replace("8:", "")) == false) {
					Optional<Contact> contact = registerUser(
							message.getDisplayName(), message.getUsername()
									.replace("8:", ""), password);
					if (!contact.isPresent()) {
						JOptionPane.showMessageDialog(
								this,
								"LOGIN failed"
										+ '\n'
										+ "displayName="
										+ message.getDisplayName()
										+ '\n'
										+ "username="
										+ message.getUsername().replace("8:",
												"") + '\n' + "password="
										+ password);
						continue loop3;
					}
					skypeNameUniqueIds.put(contact.get().getSkypeName(),
							contact.get().getUniqueId());
					userMap.put(contact.get().getUniqueId(), contact.get());
				}
				messages.add(message);
			}

			ArrayList<codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message> messages2 = new ArrayList<>();
			int x = 0;
			progressBar2.setMaximum(messages.size());
			for (codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message message : messages) {
				final int fx = x;
				SwingUtilities.invokeLater(() -> {
					progressBar2.setValue(fx);
				});
				UUID participantId = skypeNameUniqueIds.get(username);
				Contact contact = userMap.get(participantId);
				UUID conversationId = skypeNameUniqueIds.get(message
						.getUsername().replace("8:", ""));
				Contact sender = userMap.get(conversationId);
				if (sender.getUniqueId().equals(contact.getUniqueId()) == false) {
					loggedInUser = sender;
					/*
					 * sender is me
					 * 
					 * contact is the other person
					 * 
					 * here we are sending a message from me to the other person
					 */
					sendMessage(sender, contact, message.getTimestamp(),
							message.getMessage(), password);
				} else if (loggedInUser != null) {
					/*
					 * sender is the other person
					 * 
					 * contact is the other person
					 * 
					 * in order for me to see the messages from them, i have to
					 * send their message to myself using a cached unique id of
					 * my profile that we saved previously
					 */
					sendMessage(sender, loggedInUser, message.getTimestamp(),
							message.getMessage(), password);
				} else {
					/*
					 * i do not yet have a cached unique id of my profile, so we
					 * are going to skip to the next message and come back to
					 * this shortly
					 */
					messages2.add(message);
				}
				x++;
			}
			x = 0;
			messages.clear();
			progressBar2.setMaximum(messages2.size());
			if (loggedInUser != null) {
				for (codes.wilma24.SkypeChatViewer.v1_0_R1.data.types.Message message : messages2) {
					UUID conversationId = skypeNameUniqueIds.get(message
							.getUsername().replace("8:", ""));
					Contact sender = userMap.get(conversationId);
					final int fx = x;
					SwingUtilities.invokeLater(() -> {
						progressBar2.setValue(fx);
					});
					sendMessage(sender, loggedInUser, message.getTimestamp(),
							message.getMessage(), password);
					x++;
				}
				messages2.clear();
			}
		}
		SwingUtilities.invokeLater(() -> {
			label.setText("Done");
			progressBar.setMaximum(100);
			progressBar2.setMaximum(100);
			progressBar.setValue(100);
			progressBar2.setValue(100);
		});
	}

	@Override
	public void run() {
		thread = Thread.currentThread();
		try {
			importData(password);
			if (callback != null) {
				Thread thread = new Thread(callback);
				thread.start();
			}
			SwingUtilities
					.invokeLater(() -> {
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dispatchEvent(new WindowEvent(this,
								WindowEvent.WINDOW_CLOSING));
					});
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> {
				label.setText("Error");
				progressBar.setMaximum(100);
				progressBar2.setMaximum(100);
				progressBar.setValue(100);
				progressBar2.setValue(100);
			});
			e.printStackTrace();
		}
	}

	public class PGPUtilities {

		private File getUserDir() {
			String workingDirectory;
			String OS = (getProperty("os.name")).toUpperCase();
			if (OS.contains("WIN")) {
				workingDirectory = System.getenv("AppData");
			} else {
				workingDirectory = getProperty("user.home");
			}
			File userDir = new File(workingDirectory, "Skype");
			userDir.mkdirs();
			return userDir;
		}

		private File getUserFile(String fileName) {
			return new File(getUserDir(), fileName);
		}

		public PGPSecretKeyRing createOrLookupPrivateKey(String skypeName)
				throws IOException, InvalidAlgorithmParameterException,
				NoSuchAlgorithmException, PGPException {
			File privateKeyFile = getUserFile(skypeName + ".key");
			if (!privateKeyFile.exists()) {
				PGPSecretKeyRing privateKey = PGPainless
						.buildKeyRing()
						.setPrimaryKey(
								KeySpec.getBuilder(
										RSA.withLength(RsaLength._4096),
										KeyFlag.SIGN_DATA,
										KeyFlag.CERTIFY_OTHER))
						.addSubkey(
								KeySpec.getBuilder(
										ECDSA.fromCurve(EllipticCurve._P256),
										KeyFlag.SIGN_DATA))
						.addSubkey(
								KeySpec.getBuilder(
										ECDH.fromCurve(EllipticCurve._P256),
										KeyFlag.ENCRYPT_COMMS,
										KeyFlag.ENCRYPT_STORAGE))
						.addUserId(
								skypeName + " <" + skypeName + "@hookipa.net>")
						.build();
				String privateKeyArmoured = PGPainless.asciiArmor(privateKey);
				FileOutputStream fos = new FileOutputStream(privateKeyFile);
				fos.write(privateKeyArmoured.getBytes());
				fos.flush();
				fos.close();
				boolean logging = true;
				if (System.getProperty("logging") != null) {
					logging = Boolean.parseBoolean(System
							.getProperty("logging"));
				}
				if (logging)
					System.out.println(privateKeyArmoured);
			}
			return PGPainless.readKeyRing().secretKeyRing(
					new String(Files.readAllBytes(privateKeyFile.toPath())));
		}

		public String createOrLookupPublicKey(String skypeName)
				throws InvalidAlgorithmParameterException,
				NoSuchAlgorithmException, IOException, PGPException {
			PGPSecretKeyRing secretKey = createOrLookupPrivateKey(skypeName);
			PGPPublicKeyRing secretKeyPubKey = PGPainless
					.extractCertificate(secretKey);
			String publicKeyArmoured = PGPainless.asciiArmor(secretKeyPubKey);
			boolean logging = true;
			if (System.getProperty("logging") != null) {
				logging = Boolean.parseBoolean(System.getProperty("logging"));
			}
			if (logging)
				System.out.println(publicKeyArmoured);
			return publicKeyArmoured;
		}

		public String encryptAndSign(String arg0, Contact loggedInUser,
				Conversation arg1) {
			try {
				PGPSecretKeyRing secretKey = createOrLookupPrivateKey(loggedInUser
						.getSkypeName());
				Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
				if (pubKey.isPresent()) {
					return encryptAndSign(arg0, secretKey, pubKey.get());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arg0;
		}

		public DecryptionResult decryptAndVerify(String arg0,
				Contact loggedInUser, Conversation arg1) {
			try {
				PGPSecretKeyRing secretKey = createOrLookupPrivateKey(loggedInUser
						.getSkypeName());
				Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
				if (pubKey.isPresent()) {
					return decryptAndVerify(arg0, secretKey, pubKey.get());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new DecryptionResult(arg0, false, false);
		}

		public String encryptAndSign(String arg0, PGPSecretKeyRing secretKey,
				PGPPublicKeyRing... pubKeys) throws KeyException, PGPException,
				IOException {
			PGPPublicKeyRing secretKeyPubKey = PGPainless
					.extractCertificate(secretKey);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			EncryptionOptions options = new EncryptionOptions()
					.addRecipient(secretKeyPubKey);
			for (PGPPublicKeyRing pubKey : pubKeys) {
				options.addRecipient(pubKey);
			}
			EncryptionStream encryptionStream = PGPainless
					.encryptAndOrSign()
					.onOutputStream(baos)
					.withOptions(
							ProducerOptions
									.signAndEncrypt(
											options,
											new SigningOptions().addInlineSignature(
													SecretKeyRingProtector
															.unprotectedKeys(),
													secretKey,
													DocumentSignatureType.CANONICAL_TEXT_DOCUMENT))
									.setAsciiArmor(true));

			encryptionStream.write(arg0.getBytes());
			encryptionStream.close();

			return new String(baos.toByteArray());
		}

		public DecryptionResult decryptAndVerify(String arg0,
				PGPSecretKeyRing secretKey, PGPPublicKeyRing pubKey)
				throws PGPException, IOException {
			ByteArrayInputStream bais = new ByteArrayInputStream(
					arg0.getBytes());
			ConsumerOptions options = new ConsumerOptions().addDecryptionKey(
					secretKey, SecretKeyRingProtector.unprotectedKeys());
			options.addVerificationCert(pubKey);
			DecryptionStream decryptionStream = PGPainless.decryptAndOrVerify()
					.onInputStream(bais).withOptions(options);

			byte[] b = new byte[1024];
			int bytesRead;
			StringBuilder sb = new StringBuilder();
			while ((bytesRead = decryptionStream.read(b)) != -1) {
				sb.append(new String(Arrays.copyOf(b, bytesRead)));
			}

			decryptionStream.close();

			MessageMetadata metadata = decryptionStream.getMetadata();

			if (metadata.isVerifiedSignedBy(pubKey)) {
				return new DecryptionResult(sb.toString(), true, true);
			} else {
				return new DecryptionResult(sb.toString(), true, false);
			}
		}

		public class DecryptionResult {

			private String message;

			private boolean successful, signatureVerified;

			public DecryptionResult(String message, boolean successful,
					boolean signatureVerified) {
				this.setMessage(message);
				this.setSuccessful(successful);
				this.setSignatureVerified(signatureVerified);
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public boolean isSuccessful() {
				return successful;
			}

			public void setSuccessful(boolean successful) {
				this.successful = successful;
			}

			public boolean isSignatureVerified() {
				return signatureVerified;
			}

			public void setSignatureVerified(boolean signatureVerified) {
				this.signatureVerified = signatureVerified;
			}

		}

	}

}
