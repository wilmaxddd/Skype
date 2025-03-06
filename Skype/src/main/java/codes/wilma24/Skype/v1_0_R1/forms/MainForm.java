package codes.wilma24.Skype.v1_0_R1.forms;

import static java.lang.System.getProperty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.crypto.NoSuchPaddingException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.lang3.text.WordUtils;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.util.io.Streams;
import org.pgpainless.PGPainless;

import codes.wilma24.Skype.api.v1_0_R1.data.types.VoIPCall;
import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutAcceptContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutDeclineCallRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutDeclineContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutEnteringListeningMode;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutIsRatelimitingEnabled;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupContacts;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupConversationHistory;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupConversationLastAccessed;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupConversationLastAccessed2;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupMessageHistory;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupOnlineStatus;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupUser;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupUserRegistry;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupVoipContacts;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutMarkConversationAsRead;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutPubKeyExchange;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRefreshToken;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRegister;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRemoveContact;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRemoveMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendCallRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendFileTransferRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendVideoCallRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutUpdateGroupChatParticipants;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutUpdateUser;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutUpdateVoipContacts;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutVideoCallResolutionChanged;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.api.v1_0_R1.voip.VoIP;
import codes.wilma24.Skype.v1_0_R1.AppDelegate;
import codes.wilma24.Skype.v1_0_R1.Utils;
import codes.wilma24.Skype.v1_0_R1.audioio.AudioIO;
import codes.wilma24.Skype.v1_0_R1.cipher.CipherOutputStream;
import codes.wilma24.Skype.v1_0_R1.cipher.CipherUtilities;
import codes.wilma24.Skype.v1_0_R1.data.types.Bot;
import codes.wilma24.Skype.v1_0_R1.data.types.Contact;
import codes.wilma24.Skype.v1_0_R1.data.types.Conversation;
import codes.wilma24.Skype.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.v1_0_R1.data.types.MessageType;
import codes.wilma24.Skype.v1_0_R1.data.types.Server;
import codes.wilma24.Skype.v1_0_R1.data.types.Status;
import codes.wilma24.Skype.v1_0_R1.data.types.VoIPContact;
import codes.wilma24.Skype.v1_0_R1.data.types.VoIPContactList;
import codes.wilma24.Skype.v1_0_R1.fontio.FontIO;
import codes.wilma24.Skype.v1_0_R1.imageio.ImageIO;
import codes.wilma24.Skype.v1_0_R1.imgur.ImgurUploader;
import codes.wilma24.Skype.v1_0_R1.litterbox.LitterboxUploader;
import codes.wilma24.Skype.v1_0_R1.pgp.PGPUtilities;
import codes.wilma24.Skype.v1_0_R1.plugin.Skype;
import codes.wilma24.Skype.v1_0_R1.uicommon.CombinedAction;
import codes.wilma24.Skype.v1_0_R1.uicommon.CopyOfJButtonRounded;
import codes.wilma24.Skype.v1_0_R1.uicommon.JContactsConversationGroup;
import codes.wilma24.Skype.v1_0_R1.uicommon.JMenuItemDisabled;
import codes.wilma24.Skype.v1_0_R1.uicommon.JRecentConversationGroup;
import codes.wilma24.Skype.v1_0_R1.uicommon.JVerticalLayout;
import codes.wilma24.SkypeChatViewer.v1_0_R1.plugin.SkypeChatImporter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class MainForm extends JFrame {

	private JFrame frame = this;

	private String password;

	private static MainForm instance;

	private Timer timer1, timer2, timer3;

	private int messagesToBeDisplayed = 30;

	public List<String> registry = new ArrayList<String>();

	private JTextField conversationTextField = new JTextField();

	public static Object webcam = null;

	private boolean isVoIPSetUp = false;

	public void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	private WindowAdapter windowAdapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			DialogForm form = new DialogForm(
					frame,
					"Quit Skype?",
					"Sure you want to quit Skype?",
					"You won't be able to send or receive instant messages and\ncalls if you do.",
					"Quit", new Runnable() {

						@Override
						public void run() {
							setVisible(false);
							AudioIO.LOGOUT.playSoundSync();
							System.exit(-1);
						}

					});
			form.show();
		}
	};

	private SimpleDateFormat twelveHourTime = new SimpleDateFormat("h:mm a");

	private SimpleDateFormat twentyFourHourTime = new SimpleDateFormat("HH:mm");

	private UUID authCode;

	private Contact loggedInUser;

	public static List<Conversation> conversations = new ArrayList<Conversation>();

	private Conversation selectedConversation;

	private Message selectedMessage;

	/**
	 * UI Defaults
	 */
	private final Dimension defaultWindowSize = new Dimension(1008, 709);

	private final int defaultLeftPanelWidth = 242;

	/*
	 * This controls the speed at which the scroll pane will let us scroll
	 */
	private final int scrollBarUnitIncrement = 16;

	/*
	 * We add one pixel to each of these panels to give room for the border
	 */
	private final int defaultRightTopPanelHeight = 80 + 1;
	private final int defaultRightBottomBottomPanelHeight = 76 + 1;

	/**
	 * UI
	 */
	private Font font = new Font("Seriff", Font.TRUETYPE_FONT, 13);

	private FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(
			font.deriveFont(Font.BOLD, font.getSize()));

	private int scrollBarWidth = ((Integer) UIManager.get("ScrollBar.width"))
			.intValue() + 5;

	private int splitPaneDividerSize = new JSplitPane().getDividerSize();

	private String leftBottomPanelPage = "Contacts";
	public String rightPanelPage = "AccountHome";

	/*
	 * Notification count next to Recent button in top left panel
	 */
	private JPanel recentNotificationCountLabelPanel = new JPanel();
	private JLabel recentNotificationCountLabel = new JLabel(""
			+ this.notificationCount);
	private int notificationCount = 0;

	/**
	 * UI Controls
	 */
	private JSplitPane splitPane = new JSplitPane();
	private JSplitPane leftSplitPane = new JSplitPane();
	private JSplitPane rightSplitPane = new JSplitPane();

	/*
	 * Top left panel
	 */
	private JPanel leftTopPanel = new JPanel();

	private JTextField searchTextField = new JTextField("Search");
	private List<Conversation> searchTextFieldConversations = new ArrayList<Conversation>();

	private JPanel homeButtonBackgroundPanel = new JPanel();
	private JPanel callPhonesButtonBackgroundPanel = new JPanel();

	private JLabel contactsLabel = new JLabel("CONTACTS");
	private JLabel recentLabel = new JLabel("RECENT");

	/*
	 * Bottom left panel
	 */
	private JScrollPane leftBottomScrollPane = new JScrollPane();

	/*
	 * Top right panel
	 */
	private JPanel rightTopPanel = new JPanel();

	/*
	 * Bottom right panel
	 */
	private JSplitPane rightBottomSplitPane = new JSplitPane();

	public JScrollPane rightBottomTopPanel = new JScrollPane();

	private JPanel rightBottomBottomPanel = new JPanel();

	/*
	 * Call variables
	 */
	public List<IncomingCallForm> incomingCallForms = new ArrayList<>();
	public final int WEBCAM_CAPTURE_MODE = 0;
	public final int SCREEN_CAPTURE_MODE = 1;
	public boolean microphoneEnabled = true;
	public int videoMode = WEBCAM_CAPTURE_MODE;
	public boolean videoEnabled = false;
	public VoIPCall ongoingCallObj = null;
	public boolean ongoingCall = false;
	public JLabel ongoingCallTimeLabel = new JLabel();
	public long ongoingCallStartTime = 0L;
	public Conversation ongoingCallConversation = null;
	public List<UUID> ongoingCallParticipants = new ArrayList<>();
	public UUID ongoingCallId = null;
	public byte[] ongoingCallCipher = null;
	public List<Socket> callOutgoingAudioSockets = new ArrayList<Socket>();
	public List<Socket> callIncomingAudioSockets = new ArrayList<Socket>();
	public JLabel ongoingCallProfilePictureImageLabel;
	public static volatile int ongoingCallProfilePictureImageLabelWidth = 256;
	public static volatile int ongoingCallProfilePictureImageLabelHeight = 256;

	/*
	 * Video call variables
	 */
	public boolean ongoingVideoCall = false;
	public UUID ongoingVideoCallId = null;
	public byte[] ongoingVideoCallCipher = null;
	public List<Socket> videoCallOutgoingAudioSockets = new ArrayList<Socket>();
	public List<Socket> videoCallIncomingAudioSockets = new ArrayList<Socket>();
	public static volatile double ongoingVideoCallWidth = 0;
	public static volatile double ongoingVideoCallHeight = 0;

	/*
	 * File transfer variables
	 */
	public byte[] ongoingFileTransferData = null;
	public boolean ongoingFileTransfer = false;
	public Conversation ongoingFileTransferConversation = null;
	public List<UUID> ongoingFileTransferParticipants = new ArrayList<>();
	public UUID ongoingFileTransferId = null;
	public String ongoingFileTransferFileName = null;
	public long ongoingFileTransferLength = 0L;
	public byte[] ongoingFileTransferCipher = null;
	public boolean fileTransferDataTransferFinished = false;
	public List<Socket> fileTransferOutgoingAudioSockets = new ArrayList<Socket>();
	public List<Socket> fileTransferIncomingAudioSockets = new ArrayList<Socket>();

	/*
	 * User lookup
	 */
	private HashMap<UUID, Conversation> cachedUsers = new HashMap<>();

	/*
	 * Voip
	 */
	public String voipNumber = "";

	private SystemTray tray = SystemTray.getSystemTray();

	private TrayIcon trayIcon;

	private boolean isServer(Conversation conv) {
		if (!conv.isGroupChat()) {
			return false;
		}
		int hits = 0;
		for (Conversation conversation : conversations) {
			if (conversation.getUniqueId2().equals(conv.getUniqueId2())) {
				hits++;
			}
		}
		if (hits > 1) {
			return true;
		}
		return false;
	}

	public List<Server> getServers() {
		Map<UUID, List<Conversation>> map = new HashMap<>();
		for (Conversation conversation : conversations) {
			List<Conversation> conversations2;
			if (map.containsKey(conversation.getUniqueId2())) {
				conversations2 = map.get(conversation.getUniqueId2());
			} else {
				conversations2 = new ArrayList<>();
			}
			conversations2.add(conversation);
			map.put(conversation.getUniqueId2(), conversations2);
		}
		List<Server> servers = new ArrayList<>();
		for (Map.Entry<UUID, List<Conversation>> entry : map.entrySet()) {
			if (entry.getValue().size() <= 1) {
				continue;
			}
			Server server = new Server(entry.getKey(), entry.getValue());
			servers.add(server);
		}
		return servers;
	}

	private boolean refreshTaskbarIcon() {
		try {
			/**
			 * Construct icon for application using the images in 151845799.ico
			 */
			List<BufferedImage> images = Imaging.getAllBufferedImages(
					ImageIO.getResourceAsStream("/151845799.ico"),
					"151845799.ico");
			ImageIcon imageIcon = ImageIO.getCircularStatusIcon18(loggedInUser
					.getOnlineStatus());
			int unread = 0;
			for (Conversation conversation : conversations) {
				unread += conversation.getNotificationCount();
			}
			if (unread > 0) {
				// imageIcon = ImageIO.getResourceAsImageIcon("/47572014.png");
			}
			for (BufferedImage image : images) {
				int width = image.getWidth();
				if (width < 32) {
					continue;
				}
				Graphics g = image.createGraphics();
				g.drawImage(imageIcon.getImage(),
						width - imageIcon.getIconWidth(), 0, null);
			}
			setIconImages(images);

			Image image = null;

			List<BufferedImage> images2;

			switch (loggedInUser.getOnlineStatus()) {
			case ONLINE:
				images2 = Imaging.getAllBufferedImages(
						ImageIO.getResourceAsStream("/1513278348.ico"),
						"1513278348.ico");
				break;
			case AWAY:
				images2 = Imaging.getAllBufferedImages(
						ImageIO.getResourceAsStream("/1534519331.ico"),
						"1534519331.ico");
				break;
			case DO_NOT_DISTURB:
				images2 = Imaging.getAllBufferedImages(
						ImageIO.getResourceAsStream("/1536366373.ico"),
						"1536366373.ico");
				break;
			case INVISIBLE:
			case OFFLINE:
				images2 = Imaging.getAllBufferedImages(
						ImageIO.getResourceAsStream("/1540060457.ico"),
						"1540060457.ico");
				break;
			default:
				images2 = Imaging.getAllBufferedImages(
						ImageIO.getResourceAsStream("/1538213415.ico"),
						"1538213415.ico");
				break;
			}
			for (BufferedImage image3 : images2) {
				if (image3.getWidth() == 16) {
					image = image3;
				}
			}
			if (trayIcon == null) {
				trayIcon = new TrayIcon(image, getTitle(), new PopupMenu());
				trayIcon.setImageAutoSize(true);
				trayIcon.setToolTip(getTitle());
				trayIcon.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (evt.getButton() == 1) {
							setVisible(true);
							toFront();
							requestFocus();
							repaint();
						}
					}
				});
				PopupMenu menu = trayIcon.getPopupMenu();
				Menu onlineStatusMenu = new Menu("Online Status");
				menu.add(onlineStatusMenu);
				{
					MenuItem menuItem = new MenuItem("Online");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							try {
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									loggedInUser.setOnlineStatus(Status.ONLINE);
									PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
											authCode, loggedInUser
													.getUniqueId(),
											loggedInUser);
									ctx.get().getOutboundHandler()
											.write(ctx.get(), msg);
									refreshWindow();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Away");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							try {
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									loggedInUser.setOnlineStatus(Status.AWAY);
									PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
											authCode, loggedInUser
													.getUniqueId(),
											loggedInUser);
									ctx.get().getOutboundHandler()
											.write(ctx.get(), msg);
									refreshWindow();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Do Not Disturb");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							try {
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									loggedInUser
											.setOnlineStatus(Status.DO_NOT_DISTURB);
									PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
											authCode, loggedInUser
													.getUniqueId(),
											loggedInUser);
									ctx.get().getOutboundHandler()
											.write(ctx.get(), msg);
									refreshWindow();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Invisible");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							try {
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									loggedInUser
											.setOnlineStatus(Status.INVISIBLE);
									PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
											authCode, loggedInUser
													.getUniqueId(),
											loggedInUser);
									ctx.get().getOutboundHandler()
											.write(ctx.get(), msg);
									refreshWindow();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Offline");
					menuItem.setEnabled(false);
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem(
							"Set up Call Forwarding...");
					menuItem.setEnabled(false);
					onlineStatusMenu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Open Skype");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							setVisible(true);
							toFront();
							requestFocus();
							repaint();
						}

					});
					menu.add(menuItem);
				}
				{
					MenuItem menuItem = new MenuItem("Quit");
					menuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							System.exit(-1);
						}

					});
					menu.add(menuItem);
				}
				tray.add(trayIcon);
			} else {
				trayIcon.setImage(image);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Optional<Conversation> lookupUser(UUID participantId) {
		if (loggedInUser.getUniqueId().equals(participantId)) {
			return Optional.of(loggedInUser);
		}
		for (Conversation conversation : conversations) {
			if (conversation.getUniqueId().equals(participantId)) {
				return Optional.of(conversation);
			}
		}
		for (Conversation conversation : searchTextFieldConversations) {
			if (conversation.getUniqueId().equals(participantId)) {
				return Optional.of(conversation);
			}
		}
		if (selectedConversation != null) {
			if (selectedConversation.getUniqueId().equals(participantId)) {
				return Optional.of(selectedConversation);
			}
		}
		if (cachedUsers.containsKey(participantId)) {
			return Optional.of(cachedUsers.get(participantId));
		}
		Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
		if (!ctx.isPresent()) {
			return Optional.empty();
		}
		Optional<PacketPlayInReply> replyPacket = ctx.get()
				.getOutboundHandler()
				.dispatch(ctx.get(), new PacketPlayOutLogin(authCode));
		UUID authCode = UUID.fromString(replyPacket.get().getText());
		replyPacket = ctx
				.get()
				.getOutboundHandler()
				.dispatch(ctx.get(),
						new PacketPlayOutLookupUser(authCode, participantId));
		if (!replyPacket.isPresent()) {
			return Optional.empty();
		}
		if (participantId.equals(echoSoundTestService.getUniqueId())) {
			Contact contact = new Contact(replyPacket.get().getText());
			if (!contact.isGroupChat()) {
				contact.setDisplayName(contact.getSkypeName());
			}
			cachedUsers.put(participantId, contact);
			return Optional.of(contact);
		} else {
			Conversation conversation = new Conversation(replyPacket.get()
					.getText());
			if (!conversation.isGroupChat()) {
				conversation.setDisplayName(conversation.getSkypeName());
			}
			cachedUsers.put(participantId, conversation);
			return Optional.of(conversation);
		}
	}

	public UUID getAuthCode() {
		return authCode;
	}

	public Optional<Conversation> getConversation(UUID participantId) {
		for (Conversation conversation : conversations) {
			if (conversation.getUniqueId().equals(participantId)) {
				return Optional.of(conversation);
			}
		}
		for (Conversation conversation : searchTextFieldConversations) {
			if (conversation.getUniqueId().equals(participantId)) {
				return Optional.of(conversation);
			}
		}
		if (selectedConversation != null) {
			if (selectedConversation.getUniqueId().equals(participantId)) {
				return Optional.of(selectedConversation);
			}
		}
		return Optional.empty();
	}

	public void setSelectedConversation(Conversation conversation) {
		conversationTextField.setText("");
		this.selectedConversation = conversation;
	}

	public Conversation getSelectedConversation() {
		return selectedConversation;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public List<Conversation> getSearchTextFieldConversations() {
		return this.searchTextFieldConversations;
	}

	public Contact getLoggedInUser() {
		return loggedInUser;
	}

	public static MainForm get() {
		return instance;
	}

	public void run2(File file) {
		UUID fileTransferId = UUID.randomUUID();
		Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
		if (!ctx.isPresent()) {
			return;
		}
		byte[] cipher;
		try {
			cipher = CipherUtilities.randomCipher();
		} catch (InvalidKeyException | NoSuchPaddingException
				| NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		String base64 = CipherUtilities.encodeBase64(cipher);
		String message = PGPUtilities.encryptAndSign(base64,
				selectedConversation);
		UUID conversationId = selectedConversation.getUniqueId();
		try {
			String header = "Send file?";
			String text = "This file is to be encrypted, do you want to send this file?";
			boolean hit = false;
			if (file.length() >= Integer.MAX_VALUE) {
				header = "Send file? The file is too large to be sent!";
				text = "This file can not be sent, since it is larger than "
						+ (int) Math
								.floor(((Integer.MAX_VALUE / 1000.0) / 1000.0))
						+ " MB!"
						+ '\n'
						+ "https://en.wikipedia.org/wiki/2,147,483,647"
						+ '\n'
						+ " "
						+ '\n'
						+ "You can help contribute to our project by making a pr"
						+ '\n'
						+ "to our project to add support for larger file uploads!";
				hit = true;
			}
			if (selectedConversation.isGroupChat()) {
				Optional<SocketHandlerContext> ctx2 = Skype.getPlugin()
						.createHandle();
				if (!ctx2.isPresent()) {
					return;
				}
				UUID authCode = UUID.fromString(ctx2
						.get()
						.getOutboundHandler()
						.dispatch(ctx2.get(),
								new PacketPlayOutLogin(this.authCode)).get()
						.getText());
				boolean hit2 = false;
				for (UUID participantId : selectedConversation
						.getParticipants()) {
					Status onlineStatus = Status.OFFLINE;
					{
						PacketPlayOutLookupOnlineStatus onlineStatusLookup = new PacketPlayOutLookupOnlineStatus(
								authCode, participantId);
						Optional<PacketPlayInReply> replyPacket = ctx2.get()
								.getOutboundHandler()
								.dispatch(ctx2.get(), onlineStatusLookup);
						onlineStatus = Status.valueOf(replyPacket.get()
								.getText());
					}
					if (onlineStatus == Status.OFFLINE) {
						hit2 = true;
						break;
					}
				}
				if (hit2) {
					header = "Send file?";
					text = "This file can not be sent since not all participants are online,"
							+ '\n'
							+ "since every group participant must be online to send a file."
							+ '\n'
							+ " "
							+ '\n'
							+ "You can send this file to the participants that are online"
							+ '\n'
							+ "by adding them as contact and sending it to them in dm.";
					hit = true;
				}
			} else {
				Optional<SocketHandlerContext> ctx2 = Skype.getPlugin()
						.createHandle();
				if (!ctx2.isPresent()) {
					return;
				}
				UUID authCode = UUID.fromString(ctx2
						.get()
						.getOutboundHandler()
						.dispatch(ctx2.get(),
								new PacketPlayOutLogin(this.authCode)).get()
						.getText());
				boolean hit2 = false;
				Status onlineStatus = Status.OFFLINE;
				{
					PacketPlayOutLookupOnlineStatus onlineStatusLookup = new PacketPlayOutLookupOnlineStatus(
							authCode, selectedConversation.getUniqueId());
					Optional<PacketPlayInReply> replyPacket = ctx2.get()
							.getOutboundHandler()
							.dispatch(ctx2.get(), onlineStatusLookup);
					onlineStatus = Status.valueOf(replyPacket.get().getText());
				}
				if (selectedConversation instanceof Contact) {
					if (onlineStatus == Status.OFFLINE) {
						((Contact) selectedConversation)
								.setOnlineStatus(onlineStatus);
					}
				}
				if (onlineStatus == Status.OFFLINE) {
					hit2 = true;
				}
				if (hit2) {
					header = "Send file?";
					text = "This file can not be sent to them since they are not online,"
							+ '\n'
							+ "since both of you have to be online in order to send a file."
							+ '\n'
							+ " "
							+ '\n'
							+ "You can send this file to them once they are online, maybe"
							+ '\n'
							+ "you should send them a ping on discord to let them know.";
					hit = true;
				}
			}
			DialogForm form = new DialogForm(null, "Skype" + "\u2122"
					+ " - Send file?", header, text, hit == false ? "Send"
					: null, new Runnable() {

				@Override
				public void run() {
					try {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						CipherOutputStream cos = new CipherOutputStream(baos,
								cipher);
						cos.write(Files.readAllBytes(file.toPath()));
						cos.flush();
						cos.close();
						byte[] b3 = baos.toByteArray();
						long length = b3.length;
						ongoingFileTransferData = b3;
						Optional<PacketPlayInReply> replyPacket = ctx
								.get()
								.getOutboundHandler()
								.dispatch(
										ctx.get(),
										new PacketPlayOutSendFileTransferRequest(
												authCode, conversationId,
												fileTransferId, file.getName(),
												length, message));
						if (!replyPacket.isPresent()) {
							return;
						}
						if (replyPacket.get().getStatusCode() != 200) {
							return;
						}
						AudioIO.IM_SENT.playSound();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			form.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Clip echo123 = null;

	public String getDateLabelInReferenceToToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String parsedDate = new SimpleDateFormat("EEEE, MMMM d, yyyy",
				Locale.US).format(date);
		for (int i = 0; i <= 7; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(new Date().getTime()
					+ AppDelegate.TIME_OFFSET));
			calendar.add(Calendar.DAY_OF_MONTH, -i);
			int todayYear = calendar.get(Calendar.YEAR);
			int todayMonth = calendar.get(Calendar.MONTH);
			int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
			if (year == todayYear) {
				if (month == todayMonth) {
					if (day == todayDay) {
						if (i == 0) {
							return "Today";
						} else if (i == 1) {
							return "Yesterday";
						} else {
							return new SimpleDateFormat("EEEE").format(date);
						}
					}
				}
			}
		}
		return parsedDate;
	}

	private JLayeredPane createLeftTopLayeredPane(int panelWidth) {
		Color leftTopPaneBackgroundColor = new Color(245, 252, 254);

		/* Start left top panel */
		/**
		 * Construct layered pane for absolute positioning with z index
		 */
		JLayeredPane leftTopLayeredPane = new JLayeredPane();
		leftTopLayeredPane.setBounds(0, 0, panelWidth, 230);
		leftTopLayeredPane.setPreferredSize(new Dimension(panelWidth, 230));
		leftTopLayeredPane.setBackground(leftTopPaneBackgroundColor);
		leftTopLayeredPane.setOpaque(true);

		/**
		 * Add decoration floating in top left panel
		 */
		{
			JPanel iconLabelPanel = new JPanel();
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/52189619.png");
			JLabel iconLabel = new JLabel(imageIcon);
			iconLabelPanel.setBounds(0, -5, 130, 35);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
		}

		/**
		 * Add decoration floating in top left panel 2
		 */
		{
			BufferedImage bImg = new BufferedImage(panelWidth, 9,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = bImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(211, 230, 234));
			if (leftBottomPanelPage.equals("Contacts")) {
				int mov = -72;
				g2.drawLine(0, 8, 105 + mov, 8);
				g2.drawLine(106 + mov, 8, 117 + mov, 0);
				g2.drawLine(117 + mov, 0, 130 + mov, 8);
				g2.drawLine(131 + mov, 8, panelWidth, 8);
			} else if (leftBottomPanelPage.equals("Recent")
					|| leftBottomPanelPage.equals("RecentOlder")) {
				int mov = 0;
				g2.drawLine(0, 8, 105 + mov, 8);
				g2.drawLine(106 + mov, 8, 117 + mov, 0);
				g2.drawLine(117 + mov, 0, 130 + mov, 8);
				g2.drawLine(131 + mov, 8, panelWidth, 8);
			}
			g2.dispose();
			ImageIcon imageIcon = new ImageIcon(bImg);
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			JLabel iconLabel = new JLabel(imageIcon);
			iconLabelPanel.setBounds(0, 230 - 9, panelWidth, 9);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
		}

		/**
		 * Add profile icon floating in top left panel
		 */
		{
			JPanel iconLabelPanel = new JPanel();
			ImageIcon imageIcon = loggedInUser.getImageIcon();
			imageIcon = ImageIO.getScaledImageIcon(imageIcon, new Dimension(40,
					40));
			imageIcon = ImageIO.getCircularImageIcon(imageIcon);
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					selectedConversation = null;
					rightPanelPage = "AccountHome";
					refreshWindow();
				}

			};

			iconLabel.addMouseListener(mouseAdapter);

			iconLabelPanel.setBounds(15, 19, 40, 50);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(1), 0);
		}

		/**
		 * Add status icon floating in top left panel
		 */
		{
			JPanel iconLabelPanel = new JPanel();

			/**
			 * We load the status icon from memory as an icon image
			 */
			ImageIcon imageIcon = ImageIO.getCircularStatusIcon(
					loggedInUser.getOnlineStatus(), Color.white);

			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					JPopupMenu menu = new JPopupMenu();
					{
						JMenuItem menuItem = new JMenuItem("Online");
						menuItem.setIcon(ImageIO.getCircularStatusIcon(
								Status.ONLINE, Color.white));
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try {
									Optional<SocketHandlerContext> ctx = Skype
											.getPlugin().createHandle();
									if (ctx.isPresent()) {
										loggedInUser
												.setOnlineStatus(Status.ONLINE);
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.write(ctx.get(), msg);
										refreshWindow();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
						menu.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Away");
						menuItem.setIcon(ImageIO.getCircularStatusIcon(
								Status.AWAY, Color.white));
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try {
									Optional<SocketHandlerContext> ctx = Skype
											.getPlugin().createHandle();
									if (ctx.isPresent()) {
										loggedInUser
												.setOnlineStatus(Status.AWAY);
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.write(ctx.get(), msg);
										refreshWindow();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
						menu.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Do Not Disturb");
						menuItem.setIcon(ImageIO.getCircularStatusIcon(
								Status.DO_NOT_DISTURB, Color.white));
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try {
									Optional<SocketHandlerContext> ctx = Skype
											.getPlugin().createHandle();
									if (ctx.isPresent()) {
										loggedInUser
												.setOnlineStatus(Status.DO_NOT_DISTURB);
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.write(ctx.get(), msg);
										refreshWindow();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
						menu.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Invisible");
						menuItem.setIcon(ImageIO.getCircularStatusIcon(
								Status.INVISIBLE, Color.white));
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try {
									Optional<SocketHandlerContext> ctx = Skype
											.getPlugin().createHandle();
									if (ctx.isPresent()) {
										loggedInUser
												.setOnlineStatus(Status.INVISIBLE);
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.write(ctx.get(), msg);
										refreshWindow();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
						menu.add(menuItem);
					}
					menu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			};

			iconLabel.addMouseListener(mouseAdapter);

			iconLabelPanel.addMouseListener(mouseAdapter);

			iconLabelPanel.setBounds(42, 46, 14, 24);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 2, above profile icon
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(2), 0);
		}

		/**
		 * Add status text floating in top left panel
		 */
		{
			JPanel statusLabelPanel = new JPanel();

			statusLabelPanel.setOpaque(false);

			/**
			 * Capitalize only the first letter of the online status
			 */
			JLabel statusLabel = new JLabel(
					loggedInUser.getFirstLineOfMood() == null ? WordUtils
							.capitalize(loggedInUser.getOnlineStatus()
									.getReadableName()) : Utils
							.concatStringEllipses(fm, panelWidth - 73
									- this.scrollBarWidth,
									loggedInUser.getFirstLineOfMood()));

			statusLabel.setFont(font);

			/**
			 * Reserved for future use
			 */
			statusLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (loggedInUser.getPubKey().isPresent()) {
						String res2 = (String) JOptionPane.showInputDialog(
								frame,
								"Enter new mood for "
										+ loggedInUser.getSkypeName(),
								frame.getTitle(), JOptionPane.PLAIN_MESSAGE,
								null, null, loggedInUser.getFirstLineOfMood());
						if (res2 != null) {
							if (res2.trim().equals("")) {
								res2 = null;
							}
							loggedInUser.setMood(res2);
							Optional<UUID> authCode2 = registerUser(
									loggedInUser, password);
							if (authCode2.isPresent()) {
								refreshWindow(SCROLL_TO_BOTTOM);
							}
						}
					} else {
						JOptionPane.showMessageDialog(frame, loggedInUser);
					}
				}

			};

			statusLabel.addMouseListener(mouseAdapter);

			int width = statusLabel.getPreferredSize().width;
			int height = statusLabel.getPreferredSize().height;

			statusLabelPanel.add(statusLabel);
			statusLabelPanel.setBounds(70, 41, width, height + 10);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(statusLabelPanel, new Integer(1), 0);
		}

		/**
		 * Add display name floating in top left panel
		 */
		{
			JPanel displayNameLabelPanel = new JPanel();
			JLabel displayNameLabel = new JLabel(loggedInUser.getDisplayName());
			displayNameLabel.setFont(font);

			/**
			 * Reserved for future use
			 */
			displayNameLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (loggedInUser.getPubKey().isPresent()) {
						String res2 = (String) JOptionPane.showInputDialog(
								frame,
								"Enter new name for "
										+ loggedInUser.getSkypeName(),
								frame.getTitle(), JOptionPane.PLAIN_MESSAGE,
								null, null, loggedInUser.getDisplayName());
						if (res2 != null) {
							if (res2.trim().equals("")) {
								res2 = loggedInUser.getSkypeName();
							}
							loggedInUser.setDisplayName(res2);
							Optional<UUID> authCode2 = registerUser(
									loggedInUser, password);
							if (authCode2.isPresent()) {
								refreshWindow(SCROLL_TO_BOTTOM);
							}
						}
					} else {
						JOptionPane.showMessageDialog(frame, loggedInUser);
					}
				}

			};

			displayNameLabel.addMouseListener(mouseAdapter);

			displayNameLabelPanel.setOpaque(false);
			displayNameLabelPanel.add(displayNameLabel);
			int width = displayNameLabel.getPreferredSize().width;
			int height = displayNameLabel.getPreferredSize().height;
			displayNameLabelPanel.setBounds(70, 20, width, height + 10);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(displayNameLabelPanel, new Integer(1), 0);
		}

		/* Start search panel */

		/**
		 * Add search icon floating in window
		 */
		{
			JPanel iconLabelPanel = new JPanel();
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1008012489.png");
			JLabel iconLabel = new JLabel(imageIcon);
			iconLabelPanel.setBounds(0, 65, 120, 51);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(1), 0);
		}

		/**
		 * Add search border line floating in window
		 */
		{
			JPanel searchBorderLinePanel = new JPanel();
			int width = panelWidth - 30;
			searchBorderLinePanel.setBounds(15, 110, width, 1);
			searchBorderLinePanel.setOpaque(true);
			searchBorderLinePanel.setBackground(new Color(211, 230, 234));

			/**
			 * Panel added to pane with z-index 2, above search icon label
			 */
			leftTopLayeredPane.add(searchBorderLinePanel, new Integer(2), 0);
		}

		/**
		 * Add search text field floating in window
		 */
		{
			JPanel searchTextFieldPanel = new JPanel();
			searchTextFieldPanel.setBounds(38, 75, 192, 25);
			searchTextFieldPanel.setOpaque(false);

			searchTextField.setFont(font);
			searchTextField.setPreferredSize(new Dimension(192, 20));

			/**
			 * We remove all signs of there being a text field here
			 * 
			 * This is because we are decorating the text field ourselves
			 */
			searchTextField.setBackground(new Color(245, 252, 254));
			searchTextField.setBorder(BorderFactory.createEmptyBorder());

			searchTextFieldPanel.add(searchTextField);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(searchTextFieldPanel, new Integer(1), 0);
		}
		/* End search panel */

		/* Start home button panel */

		MouseAdapter homeButtonMouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!rightPanelPage.equals("AccountHome")) {
					/*
					 * Home button is not selected
					 * 
					 * We will now make it selected
					 */
					homeButtonBackgroundPanel.setBackground(new Color(199, 237,
							252));
					callPhonesButtonBackgroundPanel
							.setBackground(leftTopPaneBackgroundColor);
					rightPanelPage = "AccountHome";
					selectedConversation = null;
					refreshWindow();
				} else {
					/*
					 * Home button is selected
					 * 
					 * We will not do anything
					 */
					return;
				}
			}
		};

		/**
		 * Add home button background floating in window
		 */
		{
			homeButtonBackgroundPanel.setBounds(0, 121, panelWidth, 31);
			homeButtonBackgroundPanel.setOpaque(true);
			if (rightPanelPage.equals("AccountHome")) {
				homeButtonBackgroundPanel
						.setBackground(new Color(199, 237, 252));
			} else {
				homeButtonBackgroundPanel
						.setBackground(leftTopPaneBackgroundColor);
			}
			homeButtonBackgroundPanel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			homeButtonBackgroundPanel.addMouseListener(homeButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane
					.add(homeButtonBackgroundPanel, new Integer(1), 0);
		}

		/**
		 * Add home button icon floating in window
		 */
		{
			JPanel iconLabelPanel = new JPanel();
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1014844487.png");
			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(7, 116, 32, 32);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);
			iconLabelPanel.addMouseListener(homeButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 2, above home button background
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(2), 0);
		}

		/**
		 * Add home label floating in top left panel
		 */
		{
			JPanel homeLabelPanel = new JPanel();
			JLabel homeLabel = new JLabel("Home");
			homeLabel.setFont(font);
			homeLabelPanel.setOpaque(false);
			homeLabelPanel.add(homeLabel);
			int width = homeLabel.getPreferredSize().width;
			int height = homeLabel.getPreferredSize().height;
			homeLabelPanel.setBounds(40, 122, width, height + 10);

			homeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			homeLabel.addMouseListener(homeButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 2, above home button background
			 */
			leftTopLayeredPane.add(homeLabelPanel, new Integer(2), 0);
		}

		/* End home button panel */

		/* Start call phones button panel */

		MouseAdapter callPhonesButtonMouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!rightPanelPage.equals("CallPhones")) {
					/*
					 * Call phones button is not selected
					 * 
					 * We will now make it selected
					 */
					if (!isVoIPSetUp) {
						VoIPRegisterForm form = new VoIPRegisterForm(frame,
								new VoIPRegisterForm.Runnable() {

									@Override
									public void run() {
										String sipserver = getSIPServer();
										String username = getUsername();
										String password = getPassword();
										try {
											Skype.getPlugin()
													.getConfig()
													.set(
															loggedInUser
																	.getSkypeName()
																	+ ".sipserver",
															sipserver);
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											Skype.getPlugin()
													.getConfig()
													.set(
															loggedInUser
																	.getSkypeName()
																	+ ".sipusername",
															username);
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											Skype.getPlugin()
													.getConfig()
													.set(
															loggedInUser
																	.getSkypeName()
																	+ ".sippassword",
															password);
										} catch (Exception e) {
											e.printStackTrace();
										}
										for (Conversation conversation : conversations) {
											if (conversation instanceof VoIPContact) {
												((VoIPContact) conversation)
														.setContact((((VoIPContact) conversation)
																.isContact()));
											}
										}
										isVoIPSetUp = true;
										refreshWindow();
									}
								});
						form.show();
					}
					callPhonesButtonBackgroundPanel.setBackground(new Color(
							199, 237, 252));
					homeButtonBackgroundPanel
							.setBackground(leftTopPaneBackgroundColor);
					rightPanelPage = "CallPhones";
					voipNumber = "";
					selectedConversation = null;
					refreshWindow();
				} else {
					/*
					 * Call phones button is selected
					 * 
					 * We will not do anything
					 */
					return;
				}
			}
		};

		/**
		 * Add call phones button background floating in window
		 */
		{
			callPhonesButtonBackgroundPanel.setBounds(0, 152, panelWidth, 31);
			callPhonesButtonBackgroundPanel.setOpaque(true);
			if (rightPanelPage.equals("CallPhones")) {
				callPhonesButtonBackgroundPanel.setBackground(new Color(199,
						237, 252));
			} else {
				callPhonesButtonBackgroundPanel
						.setBackground(leftTopPaneBackgroundColor);

			}

			callPhonesButtonBackgroundPanel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			callPhonesButtonBackgroundPanel
					.addMouseListener(callPhonesButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(callPhonesButtonBackgroundPanel,
					new Integer(1), 0);
		}

		/**
		 * Add call phones icon floating in top left panel
		 */
		{
			JPanel iconLabelPanel = new JPanel();
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1161558118.png");
			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(7, 146, 32, 32);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);
			iconLabelPanel.addMouseListener(callPhonesButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 2, above call phones background
			 */
			leftTopLayeredPane.add(iconLabelPanel, new Integer(2), 0);
		}

		/**
		 * Add call phones label floating in top left panel
		 */
		{
			JPanel callPhonesLabelPanel = new JPanel();
			JLabel callPhonesLabel = new JLabel("Call phones");
			callPhonesLabel.setFont(font);
			callPhonesLabelPanel.setOpaque(false);
			callPhonesLabelPanel.add(callPhonesLabel);
			int width = callPhonesLabel.getPreferredSize().width;
			int height = callPhonesLabel.getPreferredSize().height;
			callPhonesLabelPanel.setBounds(40, 152, width, height + 10);

			callPhonesLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			callPhonesLabel.addMouseListener(callPhonesButtonMouseAdapter);

			/**
			 * Panel added to pane with z-index 2, above call phones background
			 */
			leftTopLayeredPane.add(callPhonesLabelPanel, new Integer(2), 0);
		}

		/* End home button panel */

		/* Start contacts label */
		/**
		 * Add contacts label floating in top left panel
		 */
		{
			JPanel contactsLabelPanel = new JPanel();
			contactsLabel.setFont(font.deriveFont(Font.TRUETYPE_FONT, 12));
			if (leftBottomPanelPage.equals("Contacts")) {
				contactsLabel.setForeground(new Color(0, 149, 204));
			} else {
				contactsLabel.setForeground(Color.black);
			}
			contactsLabelPanel.setOpaque(false);
			contactsLabelPanel.add(contactsLabel);
			int width = contactsLabel.getPreferredSize().width;
			int height = contactsLabel.getPreferredSize().height;
			contactsLabelPanel.setBounds(15, 193, width, height + 10);

			contactsLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			contactsLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
					if (leftBottomPanelPage.equals("Recent")
							|| leftBottomPanelPage.equals("RecentOlder")) {
						leftBottomPanelPage = "Contacts";
						refreshWindow();
					}
				}
			});

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(contactsLabelPanel, new Integer(1), 0);
		}

		/* End contacts label */

		/* Start recent label */
		/**
		 * Add recent label floating in top left panel
		 */
		{
			JPanel recentLabelPanel = new JPanel();
			if (leftBottomPanelPage.equals("Recent")
					|| leftBottomPanelPage.equals("RecentOlder")) {
				recentLabel.setForeground(new Color(0, 149, 204));
			} else {
				recentLabel.setForeground(Color.black);
			}
			recentLabel.setFont(font.deriveFont(Font.TRUETYPE_FONT, 12));
			recentLabelPanel.setOpaque(false);
			recentLabelPanel.add(recentLabel);
			int width = recentLabel.getPreferredSize().width;
			int height = recentLabel.getPreferredSize().height;
			recentLabelPanel.setBounds(96, 193, width, height + 10);

			recentLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			recentLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
					if (leftBottomPanelPage.equals("Contacts")) {
						leftBottomPanelPage = "Recent";
						refreshWindow();
					}
				}
			});

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			leftTopLayeredPane.add(recentLabelPanel, new Integer(1), 0);
		}

		/**
		 * Add notification count in top left panel
		 */
		recentNotificationCountLabel.setText("" + this.notificationCount);
		recentNotificationCountLabel.setFont(font);
		recentNotificationCountLabel.setForeground(new Color(255, 125, 0));
		recentNotificationCountLabel.setFont(font);

		recentNotificationCountLabelPanel.setOpaque(false);
		recentNotificationCountLabelPanel.add(recentNotificationCountLabel);
		int width = recentNotificationCountLabel.getPreferredSize().width;
		int height = recentNotificationCountLabel.getPreferredSize().height;
		recentNotificationCountLabelPanel.setBounds(151, 192, width,
				height + 10);

		/**
		 * Panel added to pane with z-index 1
		 */
		leftTopLayeredPane.add(recentNotificationCountLabelPanel,
				new Integer(1), 0);

		if (this.notificationCount == 0) {
			recentNotificationCountLabelPanel.setVisible(false);
		} else {
			recentNotificationCountLabelPanel.setVisible(true);
		}

		/* End recent label */

		/* End left top panel */
		return leftTopLayeredPane;
	}

	private JPanel createLeftBottomPanel(int panelWidth) {

		final JPanel panel = new JPanel();

		panel.setLayout(new JVerticalLayout(0, JVerticalLayout.LEFT,
				JVerticalLayout.TOP));

		final Color scrollPaneColor = new Color(245, 252, 254);

		panel.setBackground(scrollPaneColor);

		final int groupNamePanelHeight = 40;
		final int conversationHeight = 50;

		this.notificationCount = 0;

		List<Conversation> conversations = this.conversations;

		if (searchTextField.getText().length() > 0) {
			if (!searchTextField.getText().equals("Search")) {
				conversations = this.searchTextFieldConversations;
			}
		}

		if (leftBottomPanelPage.equals("Contacts")) {
			Map<String, List<Conversation>> groups = new HashMap<>();

			groups.put("Favorites", new ArrayList<Conversation>());
			groups.put("All", new ArrayList<Conversation>());

			for (Server server : getServers()) {
				groups.put(server.getUniqueId() + "", server.getConversations());
			}

			for (Conversation conversation : conversations) {
				if (conversation instanceof Contact
						|| (conversation instanceof VoIPContact && ((VoIPContact) conversation)
								.isContact())) {
					if (((conversation instanceof VoIPContact && ((VoIPContact) conversation)
							.isContact()) && ((VoIPContact) conversation)
							.isFavorite())
							|| (!((conversation instanceof VoIPContact && ((VoIPContact) conversation)
									.isContact())) && ((Contact) conversation)
									.isFavorite())) {
						groups.get("Favorites").add(conversation);
					} else {
						groups.get("All").add(conversation);
					}
				}
				this.notificationCount += conversation.getNotificationCount();
			}

			List<JContactsConversationGroup> sortedGroups = new ArrayList<>();

			sortedGroups.add(new JContactsConversationGroup("Favorites", groups
					.get("Favorites")));
			sortedGroups.add(new JContactsConversationGroup("All", groups
					.get("All")));
			for (String str : groups.keySet()) {
				if (str.equals("Favorites") || str.equals("All"))
					continue;
				sortedGroups.add(new JContactsConversationGroup(str, groups
						.get(str)));
			}

			for (JContactsConversationGroup conversationGroup : sortedGroups) {
				if (conversationGroup.getConversations().size() > 0) {
					JPanel labelPanel = new JPanel();
					labelPanel.setOpaque(false);

					/**
					 * This allows us to put a layered pane at x 0 y 0
					 * 
					 * Note that the layered pane by default has a null layout
					 * 
					 * That null layout allows us to set the bounds to start at
					 * x 0 y 0
					 * 
					 * If we do not put this code then there will be extra
					 * padding
					 * 
					 * That padding would interfere with the look of the program
					 */
					labelPanel
							.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

					JLayeredPane labelLayeredPane = new JLayeredPane();
					labelLayeredPane.setBounds(0, 0, panelWidth,
							groupNamePanelHeight);
					labelLayeredPane.setPreferredSize(new Dimension(panelWidth,
							groupNamePanelHeight));
					labelLayeredPane.setOpaque(false);

					/**
					 * Add label name in conversation panel
					 */
					{
						JPanel labelNamePanel = new JPanel();
						JLabel labelNameLabel = new JLabel(
								conversationGroup.getGroup());
						labelNameLabel.setFont(font);
						labelNamePanel.setOpaque(false);
						labelNamePanel.add(labelNameLabel);
						int width = labelNameLabel.getPreferredSize().width;
						int height = labelNameLabel.getPreferredSize().height;
						labelNamePanel.setBounds(15, 5, width, height + 10);

						/**
						 * Panel added to pane with z-index 0
						 */
						labelLayeredPane.add(labelNamePanel, new Integer(0), 0);
					}

					labelPanel.add(labelLayeredPane);

					panel.add(labelPanel);
				}
				for (Conversation conversation : conversationGroup
						.getConversations()) {
					JPanel conversationPanel = new JPanel();

					MouseAdapter conversationMouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							if (evt.getButton() != MouseEvent.BUTTON1) {
								return;
							}
							selectConversation(conversation);
						}
					};

					conversationPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					conversationPanel
							.addMouseListener(conversationMouseAdapter);

					JPopupMenu popUp = new JPopupMenu();
					{
						JMenuItem menuItem = new JMenuItem("Call");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Video Call");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send IM");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Video Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send SMS Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Contacts...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send files...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Voice Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Share Screens...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("View Profile");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Rename");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						boolean val = false;
						if (conversation instanceof Contact) {
							val = ((Contact) conversation).isFavorite();
						}
						if (conversation instanceof VoIPContact) {
							val = ((VoIPContact) conversation).isFavorite();
						}
						if (val) {
							JMenuItem menuItem = new JMenuItem(
									"Remove from Favorites");
							menuItem.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									if (conversation instanceof VoIPContact) {
										((VoIPContact) conversation)
												.setFavorite(false);
										saveVoipContactList();
										refreshWindow();
									} else if (conversation instanceof Contact) {
										loggedInUser.getFavorites().remove(
												conversation.getUniqueId()
														.toString());
										Optional<SocketHandlerContext> ctx = Skype
												.getPlugin().createHandle();
										if (!ctx.isPresent()) {
											return;
										}
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.dispatch(ctx.get(), msg);
									}
								}

							});
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem(
									"Add to Favorites");
							menuItem.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									if (conversation instanceof VoIPContact) {
										((VoIPContact) conversation)
												.setFavorite(true);
										saveVoipContactList();
										refreshWindow();
									} else if (conversation instanceof Contact) {
										loggedInUser.getFavorites().add(
												conversation.getUniqueId()
														.toString());
										Optional<SocketHandlerContext> ctx = Skype
												.getPlugin().createHandle();
										if (!ctx.isPresent()) {
											return;
										}
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.dispatch(ctx.get(), msg);
									}
								}

							});
							popUp.add(menuItem);
						}
					}
					{
						JMenuItem menuItem = new JMenuItem("Add to List");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						if (conversation.getNotificationCount() > 0) {
							JMenuItem menuItem = new JMenuItem("Mark as Read");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem("Mark as Unread");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
					}
					{
						JMenuItem menuItem = new JMenuItem(
								"Block This Person...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					if (!(conversation instanceof Contact)
							&& !(conversation instanceof VoIPContact && ((VoIPContact) conversation)
									.isContact())) {
						if (conversation.hasOutgoingFriendRequest()) {
							JMenuItem menuItem = new JMenuItem(
									"Resend Contact Request");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem(
									"Send Contact Request");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
					}
					{
						JMenu menuItem = new JMenu("View Old Messages");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem(
								"Remove from Contacts");
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (conversation == null) {
									return;
								}
								if (!(conversation instanceof Contact)
										&& !(conversation instanceof VoIPContact)) {
									return;
								}
								DialogForm form = new DialogForm(null, "Skype"
										+ "\u2122" + " - Remove contact?",
										"Remove contact?", "Remove "
												+ conversation.getDisplayName()
												+ " from Contacts?", "Remove",
										new Runnable() {

											@Override
											public void run() {
												if (conversation instanceof VoIPContact) {
													MainForm.get().conversations
															.remove(conversation);
													if (selectedConversation != null
															&& selectedConversation
																	.getUniqueId()
																	.equals(conversation
																			.getUniqueId())) {
														selectedConversation = null;
														rightPanelPage = "AccountHome";
													}
													saveVoipContactList();
													refreshWindow();
													return;
												}
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (ctx.isPresent()) {
													Optional<PacketPlayInReply> reply = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	new PacketPlayOutLogin(
																			authCode));
													if (!reply.isPresent()
															|| reply.get()
																	.getStatusCode() != 200) {
														return;
													}
													PacketPlayOutRemoveContact removeContactPacket = new PacketPlayOutRemoveContact(
															authCode,
															conversation
																	.getUniqueId());
													Optional<PacketPlayInReply> reply2 = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	removeContactPacket);
													if (!reply2.isPresent()
															|| reply2
																	.get()
																	.getStatusCode() != 200) {
														return;
													}
													for (Message message : conversation
															.getMessages()
															.toArray(
																	new Message[0])
															.clone()) {
														if (message.isDeleted()) {
															continue;
														}
														UUID authCode = UUID
																.fromString(reply
																		.get()
																		.getText());
														PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																authCode,
																conversation
																		.getUniqueId(),
																message.getUniqueId(),
																message.getTimestamp());
														ctx.get()
																.getOutboundHandler()
																.write(ctx
																		.get(),
																		removeMessage);
													}
												}
											}
										});

								form.show();
							}

						});
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("Hide conversation");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("Clear chat with "
								+ conversation.getSkypeName());
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								DialogForm form = new DialogForm(
										null,
										"Skype" + "\u2122" + " - Clear chat?",
										"Clear chat?",
										"Are you sure you want to clear this chat?",
										"Remove", new Runnable() {

											@Override
											public void run() {
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (ctx.isPresent()) {
													Optional<PacketPlayInReply> reply = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	new PacketPlayOutLogin(
																			authCode));
													if (!reply.isPresent()
															|| reply.get()
																	.getStatusCode() != 200) {
														return;
													}
													for (Message message : conversation
															.getMessages()
															.toArray(
																	new Message[0])
															.clone()) {
														if (message.isDeleted()) {
															continue;
														}
														if (message
																.getMessageType() != null) {
															continue;
														}
														UUID authCode = UUID
																.fromString(reply
																		.get()
																		.getText());
														PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																authCode,
																conversation
																		.getUniqueId(),
																message.getUniqueId(),
																message.getTimestamp());
														ctx.get()
																.getOutboundHandler()
																.write(ctx
																		.get(),
																		removeMessage);
													}
												}
											}
										});

								form.show();

							}
						});
						popUp.add(menuItem);
					}

					if (!conversation.isGroupChat()) {

						conversationPanel.setComponentPopupMenu(popUp);

					}

					if (selectedConversation != null
							&& selectedConversation.equals(conversation)) {
						conversationPanel
								.setBackground(new Color(199, 237, 252));
						conversationPanel.setOpaque(true);
					} else {
						conversationPanel.setOpaque(false);
					}

					/**
					 * This allows us to put a layered pane at x 0 y 0
					 * 
					 * Note that the layered pane by default has a null layout
					 * 
					 * That null layout allows us to set the bounds to start at
					 * x 0 y 0
					 * 
					 * If we do not put this code then there will be extra
					 * padding
					 * 
					 * That padding would interfere with the look of the program
					 */
					conversationPanel.setLayout(new FlowLayout(
							FlowLayout.CENTER, 0, 0));

					JLayeredPane conversationLayeredPane = new JLayeredPane();
					conversationLayeredPane.setBounds(0, 0, panelWidth,
							conversationHeight);
					conversationLayeredPane.setPreferredSize(new Dimension(
							panelWidth, conversationHeight));
					conversationLayeredPane.setOpaque(false);

					/**
					 * Add profile icon with status icon in conversation panel
					 */
					{
						JPanel iconLabelPanel = conversation
								.getOnlineStatusPanel();

						if (conversation.isGroupChat()) {
							conversation.getOnlineStatusLabel().setVisible(
									false);
						}

						iconLabelPanel.setBounds(14, 0, 40, 50);

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(iconLabelPanel,
								new Integer(0), 0);
					}

					/**
					 * Add notification count in conversation panel
					 * 
					 * This is only done if the count is less then 3 digits long
					 */
					if (conversation.getNotificationCount() > 0
							&& conversation.getNotificationCount() < 100) {
						JPanel notificationCountLabelPanel = new JPanel();

						JLabel notificationCountLabel = new JLabel(""
								+ conversation.getNotificationCount());
						notificationCountLabel
								.addMouseListener(conversationMouseAdapter);
						if (!conversation.isGroupChat()) {
							notificationCountLabel.setComponentPopupMenu(popUp);
						}
						notificationCountLabel.setFont(font);
						notificationCountLabel.setForeground(new Color(255,
								125, 0));
						notificationCountLabel.setFont(font.deriveFont(
								Font.BOLD, font.getSize()));

						notificationCountLabelPanel.setOpaque(false);
						notificationCountLabelPanel.add(notificationCountLabel);
						int width = notificationCountLabel.getPreferredSize().width;
						int height = notificationCountLabel.getPreferredSize().height;
						if (conversation instanceof Contact) {
							Contact contact = (Contact) conversation;
							if (contact.getFirstLineOfMood() != null
									&& contact.getFirstLineOfMood().length() > 0) {
								notificationCountLabelPanel.setBounds(
										panelWidth - 16 - width, 4, width,
										height + 10);
							} else {
								notificationCountLabelPanel.setBounds(
										panelWidth - 16 - width, 11, width,
										height + 10);
							}
						} else {
							notificationCountLabelPanel.setBounds(panelWidth
									- 16 - width, 11, width, height + 10);
						}

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(
								notificationCountLabelPanel, new Integer(0), 0);
					}

					/**
					 * Add conversation name in conversation panel
					 */
					{
						JPanel conversationNameLabelPanel = new JPanel();
						int notificationCountWidth = fm.stringWidth(""
								+ conversation.getNotificationCount());
						JLabel conversationNameLabel = new JLabel(
								Utils.concatStringEllipses(
										fm,
										panelWidth
												- 73
												- (conversation
														.getNotificationCount() > 0
														&& conversation
																.getNotificationCount() < 100 ? notificationCountWidth
														+ fm.charWidth('1')
														: 0)
												- this.scrollBarWidth,
										conversation.getDisplayName()));
						conversationNameLabel
								.addMouseListener(conversationMouseAdapter);
						if (!conversation.isGroupChat()) {
							conversationNameLabel.setComponentPopupMenu(popUp);
						}
						conversationNameLabel.setFont(font);
						conversationNameLabel.setToolTipText(conversation
								.getDisplayName());
						if (conversation.getNotificationCount() > 0) {
							conversationNameLabel.setForeground(new Color(255,
									125, 0));
							conversationNameLabel.setFont(font.deriveFont(
									Font.BOLD, font.getSize()));
						}

						conversationNameLabelPanel.setOpaque(false);
						conversationNameLabelPanel.add(conversationNameLabel);
						int width = conversationNameLabel.getPreferredSize().width;
						int height = conversationNameLabel.getPreferredSize().height;
						if (conversation instanceof Contact) {
							Contact contact = (Contact) conversation;
							if ((contact.getFirstLineOfMood() != null && contact
									.getFirstLineOfMood().length() > 0)) {
								conversationNameLabelPanel.setBounds(70, 4,
										width, height + 10);
							} else {
								conversationNameLabelPanel.setBounds(70, 11,
										width, height + 10);
							}
						} else {
							conversationNameLabelPanel.setBounds(70, 11, width,
									height + 10);
						}

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(conversationNameLabelPanel,
								new Integer(0), 0);
					}

					if (conversation instanceof Contact) {
						Contact contact = (Contact) conversation;
						if ((contact.getFirstLineOfMood() != null && contact
								.getFirstLineOfMood().length() > 0)) {
							/**
							 * Add contact mood in conversation panel
							 */
							JPanel contactMoodLabelPanel = new JPanel();
							int notificationCountWidth = fm.stringWidth(""
									+ conversation.getNotificationCount());
							JLabel contactMoodLabel;
							contactMoodLabel = new JLabel(
									Utils.concatStringEllipses(
											fm,
											panelWidth
													- 73
													- (conversation
															.getNotificationCount() > 0
															&& conversation
																	.getNotificationCount() < 100 ? notificationCountWidth
															+ fm.charWidth('1')
															: 0)
													- this.scrollBarWidth,
											contact.getFirstLineOfMood()));
							contactMoodLabel
									.addMouseListener(conversationMouseAdapter);
							if (!conversation.isGroupChat()) {
								contactMoodLabel.setComponentPopupMenu(popUp);
							}
							contactMoodLabel.setFont(font);
							contactMoodLabel.setToolTipText(contact
									.getFirstLineOfMood());
							contactMoodLabel.setForeground(new Color(158, 166,
									169));

							contactMoodLabelPanel.setOpaque(false);
							contactMoodLabelPanel.add(contactMoodLabel);
							int width = contactMoodLabel.getPreferredSize().width;
							int height = contactMoodLabel.getPreferredSize().height;
							contactMoodLabelPanel.setBounds(70, 22, width,
									height + 10);

							/**
							 * Panel added to pane with z-index 0
							 */
							conversationLayeredPane.add(contactMoodLabelPanel,
									new Integer(0), 0);
						}
					}

					conversationPanel.add(conversationLayeredPane);

					panel.add(conversationPanel);
				}
			}
		} else if (leftBottomPanelPage.equals("Recent")
				|| leftBottomPanelPage.equals("RecentOlder")) {
			Map<String, List<Conversation>> groups = new HashMap<>();

			groups.put("Today", new ArrayList<Conversation>());
			groups.put("Yesterday", new ArrayList<Conversation>());

			for (int i = 1; i < 8; i++) {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				Date lastModified = new Date(year - 1900, month, day - i);
				String dayOfWeek = new SimpleDateFormat("EEEE")
						.format(lastModified);
				groups.put(dayOfWeek, new ArrayList<Conversation>());
			}

			groups.put("Older than a week", new ArrayList<Conversation>());
			groups.put("Older than a month", new ArrayList<Conversation>());

			for (Conversation conversation : conversations) {
				this.notificationCount += conversation.getNotificationCount();
				Date now = new Date(new Date().getTime()
						+ AppDelegate.TIME_OFFSET);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(conversation.getLastModified());
				int lastModifiedYear = calendar.get(Calendar.YEAR);
				int lastModifiedMonth = calendar.get(Calendar.MONTH);
				int lastModifiedDay = calendar.get(Calendar.DAY_OF_MONTH);
				String group = new SimpleDateFormat("EEEE").format(conversation
						.getLastModified());
				calendar.setTime(now);
				int todayYear = calendar.get(Calendar.YEAR);
				int todayMonth = calendar.get(Calendar.MONTH);
				int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
				if (lastModifiedYear == todayYear) {
					if (lastModifiedMonth == todayMonth) {
						if (lastModifiedDay == todayDay) {
							group = "Today";
						} else if (lastModifiedDay == todayDay - 1) {
							group = "Yesterday";
						}
					}
				}
				long diffInMillies = now.getTime()
						- conversation.getLastModified().getTime();
				long diffInDays = TimeUnit.DAYS.convert(diffInMillies,
						TimeUnit.MILLISECONDS);
				if (diffInDays > 30) {
					group = "Older than a month";
				} else if (diffInDays >= 7) {
					group = "Older than a week";
				}
				groups.get(group).add(conversation);
			}

			List<JRecentConversationGroup> sortedGroups = new ArrayList<>();

			sortedGroups.add(new JRecentConversationGroup("Today", groups
					.get("Today")));
			sortedGroups.add(new JRecentConversationGroup("Yesterday", groups
					.get("Yesterday")));

			for (int i = 2; i < 8; i++) {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				Date lastModified = new Date(year - 1900, month, day - i);
				String dayOfWeek = new SimpleDateFormat("EEEE")
						.format(lastModified);
				sortedGroups.add(new JRecentConversationGroup(dayOfWeek, groups
						.get(dayOfWeek)));
			}

			sortedGroups.add(new JRecentConversationGroup("Older than a week",
					groups.get("Older than a week")));
			sortedGroups.add(new JRecentConversationGroup("Older than a month",
					groups.get("Older than a month")));

			for (JRecentConversationGroup conversationGroup : sortedGroups) {
				if (conversationGroup.getConversations().size() > 0) {
					if (conversationGroup.getGroup().equals(
							"Older than a month")) {
						if (leftBottomPanelPage.equals("Recent")) {
							JPanel showEarlierMessagesPanel = new JPanel();
							showEarlierMessagesPanel.setOpaque(false);

							/**
							 * This allows us to put a layered pane at x 0 y 0
							 * 
							 * Note that the layered pane by default has a null
							 * layout
							 * 
							 * That null layout allows us to set the bounds to
							 * start at x 0 y 0
							 * 
							 * If we do not put this code then there will be
							 * extra padding
							 * 
							 * That padding would interfere with the look of the
							 * program
							 */
							showEarlierMessagesPanel.setLayout(new FlowLayout(
									FlowLayout.CENTER, 0, 0));

							JLayeredPane showEarlierMessagesLayeredPane = new JLayeredPane();
							showEarlierMessagesLayeredPane.setBounds(0, 0,
									panelWidth, groupNamePanelHeight - 5);
							showEarlierMessagesLayeredPane
									.setPreferredSize(new Dimension(panelWidth,
											groupNamePanelHeight));
							showEarlierMessagesLayeredPane.setOpaque(false);

							/**
							 * Add label name in conversation panel
							 */
							{
								JPanel showEarlierMessagesLabelPanel = new JPanel();
								JLabel showEarlierMessagesLabel = new JLabel(
										"Show earlier messages");
								showEarlierMessagesLabel
										.setForeground(new Color(0, 149, 204));
								showEarlierMessagesLabel
										.setCursor(Cursor
												.getPredefinedCursor(Cursor.HAND_CURSOR));
								showEarlierMessagesLabel
										.addMouseListener(new MouseAdapter() {
											@Override
											public void mousePressed(
													MouseEvent evt) {
												leftBottomPanelPage = "RecentOlder";
												refreshWindow();
											}
										});
								showEarlierMessagesLabel.setFont(font);
								showEarlierMessagesLabelPanel.setOpaque(false);
								showEarlierMessagesLabelPanel
										.add(showEarlierMessagesLabel);
								int width = showEarlierMessagesLabel
										.getPreferredSize().width;
								int height = showEarlierMessagesLabel
										.getPreferredSize().height;
								showEarlierMessagesLabelPanel.setBounds(15, 5,
										width, height + 10);

								/**
								 * Panel added to pane with z-index 0
								 */
								showEarlierMessagesLayeredPane.add(
										showEarlierMessagesLabelPanel,
										new Integer(0), 0);
							}

							showEarlierMessagesPanel
									.add(showEarlierMessagesLayeredPane);

							panel.add(showEarlierMessagesPanel);
							continue;
						}
					}
					JPanel labelPanel = new JPanel();
					labelPanel.setOpaque(false);

					/**
					 * This allows us to put a layered pane at x 0 y 0
					 * 
					 * Note that the layered pane by default has a null layout
					 * 
					 * That null layout allows us to set the bounds to start at
					 * x 0 y 0
					 * 
					 * If we do not put this code then there will be extra
					 * padding
					 * 
					 * That padding would interfere with the look of the program
					 */
					labelPanel
							.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

					JLayeredPane labelLayeredPane = new JLayeredPane();
					labelLayeredPane.setBounds(0, 0, panelWidth,
							groupNamePanelHeight);
					labelLayeredPane.setPreferredSize(new Dimension(panelWidth,
							groupNamePanelHeight));
					labelLayeredPane.setOpaque(false);

					/**
					 * Add label name in conversation panel
					 */
					{
						JPanel labelNamePanel = new JPanel();
						JLabel labelNameLabel = new JLabel(
								conversationGroup.getGroup());
						labelNameLabel.setFont(font);
						labelNamePanel.setOpaque(false);
						labelNamePanel.add(labelNameLabel);
						int width = labelNameLabel.getPreferredSize().width;
						int height = labelNameLabel.getPreferredSize().height;
						labelNamePanel.setBounds(15, 5, width, height + 10);

						/**
						 * Panel added to pane with z-index 0
						 */
						labelLayeredPane.add(labelNamePanel, new Integer(0), 0);
					}

					labelPanel.add(labelLayeredPane);

					panel.add(labelPanel);
				}
				for (Conversation conversation : conversationGroup
						.getConversations()) {
					JPanel conversationPanel = new JPanel();

					MouseAdapter conversationMouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							if (evt.getButton() != MouseEvent.BUTTON1) {
								return;
							}
							selectConversation(conversation);
						}
					};

					conversationPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					conversationPanel
							.addMouseListener(conversationMouseAdapter);

					JPopupMenu popUp = new JPopupMenu();
					{
						JMenuItem menuItem = new JMenuItem("Call");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Video Call");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send IM");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Video Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send SMS Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Contacts...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send files...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Send Voice Message");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Share Screens...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("View Profile");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem("Rename");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						boolean val = false;
						if (conversation instanceof Contact) {
							val = ((Contact) conversation).isFavorite();
						}
						if (conversation instanceof VoIPContact) {
							val = ((VoIPContact) conversation).isFavorite();
						}
						if (val) {
							JMenuItem menuItem = new JMenuItem(
									"Remove from Favorites");
							menuItem.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									if (conversation instanceof VoIPContact) {
										((VoIPContact) conversation)
												.setFavorite(false);
										saveVoipContactList();
										refreshWindow();
									} else if (conversation instanceof Contact) {
										loggedInUser.getFavorites().remove(
												conversation.getUniqueId()
														.toString());
										Optional<SocketHandlerContext> ctx = Skype
												.getPlugin().createHandle();
										if (!ctx.isPresent()) {
											return;
										}
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.dispatch(ctx.get(), msg);
									}
								}

							});
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem(
									"Add to Favorites");
							menuItem.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									if (conversation instanceof VoIPContact) {
										((VoIPContact) conversation)
												.setFavorite(true);
										saveVoipContactList();
										refreshWindow();
									} else if (conversation instanceof Contact) {
										loggedInUser.getFavorites().add(
												conversation.getUniqueId()
														.toString());
										Optional<SocketHandlerContext> ctx = Skype
												.getPlugin().createHandle();
										if (!ctx.isPresent()) {
											return;
										}
										PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
												authCode, loggedInUser
														.getUniqueId(),
												loggedInUser);
										ctx.get().getOutboundHandler()
												.dispatch(ctx.get(), msg);
									}
								}

							});
							popUp.add(menuItem);
						}
					}
					{
						JMenuItem menuItem = new JMenuItem("Add to List");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						if (conversation.getNotificationCount() > 0) {
							JMenuItem menuItem = new JMenuItem("Mark as Read");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem("Mark as Unread");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
					}
					{
						JMenuItem menuItem = new JMenuItem(
								"Block This Person...");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					if (!(conversation instanceof Contact)
							&& !(conversation instanceof VoIPContact && ((VoIPContact) conversation)
									.isContact())) {
						if (conversation.hasOutgoingFriendRequest()) {
							JMenuItem menuItem = new JMenuItem(
									"Resend Contact Request");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						} else {
							JMenuItem menuItem = new JMenuItem(
									"Send Contact Request");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
					}
					{
						JMenu menuItem = new JMenu("View Old Messages");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
					}
					{
						JMenuItem menuItem = new JMenuItem(
								"Remove from Contacts");
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (conversation == null) {
									return;
								}
								if (!(conversation instanceof Contact)
										&& !(conversation instanceof VoIPContact)) {
									return;
								}
								DialogForm form = new DialogForm(null, "Skype"
										+ "\u2122" + " - Remove contact?",
										"Remove contact?", "Remove "
												+ conversation.getDisplayName()
												+ " from Contacts?", "Remove",
										new Runnable() {

											@Override
											public void run() {
												if (conversation instanceof VoIPContact) {
													MainForm.get().conversations
															.remove(conversation);
													if (selectedConversation != null
															&& selectedConversation
																	.getUniqueId()
																	.equals(conversation
																			.getUniqueId())) {
														selectedConversation = null;
														rightPanelPage = "AccountHome";
													}
													saveVoipContactList();
													refreshWindow();
													return;
												}
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (ctx.isPresent()) {
													Optional<PacketPlayInReply> reply = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	new PacketPlayOutLogin(
																			authCode));
													if (!reply.isPresent()
															|| reply.get()
																	.getStatusCode() != 200) {
														return;
													}
													PacketPlayOutRemoveContact removeContactPacket = new PacketPlayOutRemoveContact(
															authCode,
															conversation
																	.getUniqueId());
													Optional<PacketPlayInReply> reply2 = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	removeContactPacket);
													if (!reply2.isPresent()
															|| reply2
																	.get()
																	.getStatusCode() != 200) {
														return;
													}
													for (Message message : conversation
															.getMessages()
															.toArray(
																	new Message[0])
															.clone()) {
														if (message.isDeleted()) {
															continue;
														}
														UUID authCode = UUID
																.fromString(reply
																		.get()
																		.getText());
														PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																authCode,
																conversation
																		.getUniqueId(),
																message.getUniqueId(),
																message.getTimestamp());
														ctx.get()
																.getOutboundHandler()
																.write(ctx
																		.get(),
																		removeMessage);
													}
												}
											}
										});

								form.show();
							}

						});
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("Hide conversation");
						menuItem.setEnabled(false);
						popUp.add(menuItem);
						popUp.add(new JSeparator());
					}
					{
						JMenuItem menuItem = new JMenuItem("Clear chat with "
								+ conversation.getSkypeName());
						menuItem.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								DialogForm form = new DialogForm(
										null,
										"Skype" + "\u2122" + " - Clear chat?",
										"Clear chat?",
										"Are you sure you want to clear this chat?",
										"Remove", new Runnable() {

											@Override
											public void run() {
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (ctx.isPresent()) {
													Optional<PacketPlayInReply> reply = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	new PacketPlayOutLogin(
																			authCode));
													if (!reply.isPresent()
															|| reply.get()
																	.getStatusCode() != 200) {
														return;
													}
													PacketPlayOutRemoveContact removeContactPacket = new PacketPlayOutRemoveContact(
															authCode,
															conversation
																	.getUniqueId());
													Optional<PacketPlayInReply> reply2 = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	removeContactPacket);
													if (!reply2.isPresent()
															|| reply2
																	.get()
																	.getStatusCode() != 200) {
														return;
													}
													for (Message message : conversation
															.getMessages()
															.toArray(
																	new Message[0])
															.clone()) {
														if (message.isDeleted()) {
															continue;
														}
														if (message
																.getMessageType() != null) {
															continue;
														}
														UUID authCode = UUID
																.fromString(reply
																		.get()
																		.getText());
														PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																authCode,
																conversation
																		.getUniqueId(),
																message.getUniqueId(),
																message.getTimestamp());
														ctx.get()
																.getOutboundHandler()
																.write(ctx
																		.get(),
																		removeMessage);
													}
												}
											}
										});

								form.show();

							}
						});
						popUp.add(menuItem);
					}

					if (!conversation.isGroupChat()) {

						conversationPanel.setComponentPopupMenu(popUp);

					}

					if (selectedConversation != null
							&& selectedConversation.equals(conversation)) {
						conversationPanel
								.setBackground(new Color(199, 237, 252));
						conversationPanel.setOpaque(true);
					} else {
						conversationPanel.setOpaque(false);
					}

					/**
					 * This allows us to put a layered pane at x 0 y 0
					 * 
					 * Note that the layered pane by default has a null layout
					 * 
					 * That null layout allows us to set the bounds to start at
					 * x 0 y 0
					 * 
					 * If we do not put this code then there will be extra
					 * padding
					 * 
					 * That padding would interfere with the look of the program
					 */
					conversationPanel.setLayout(new FlowLayout(
							FlowLayout.CENTER, 0, 0));

					JLayeredPane conversationLayeredPane = new JLayeredPane();
					conversationLayeredPane.setBounds(0, 0, panelWidth,
							conversationHeight);
					conversationLayeredPane.setPreferredSize(new Dimension(
							panelWidth, conversationHeight));
					conversationLayeredPane.setOpaque(false);

					/**
					 * Add profile icon with status icon in conversation panel
					 */
					{
						JPanel iconLabelPanel = conversation
								.getOnlineStatusPanel();

						if (conversation.isGroupChat()) {
							conversation.getOnlineStatusLabel().setVisible(
									false);
						}

						iconLabelPanel.setBounds(14, 0, 40, 50);

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(iconLabelPanel,
								new Integer(0), 0);
					}

					/**
					 * Add notification count in conversation panel
					 * 
					 * This is only done if the count is less then 3 digits long
					 */
					if (conversation.getNotificationCount() > 0
							&& conversation.getNotificationCount() < 100) {
						JPanel notificationCountLabelPanel = new JPanel();

						JLabel notificationCountLabel = new JLabel(""
								+ conversation.getNotificationCount());
						notificationCountLabel
								.addMouseListener(conversationMouseAdapter);
						if (!conversation.isGroupChat()) {
							notificationCountLabel.setComponentPopupMenu(popUp);
						}
						notificationCountLabel.setFont(font);
						notificationCountLabel.setForeground(new Color(255,
								125, 0));
						notificationCountLabel.setFont(font.deriveFont(
								Font.BOLD, font.getSize()));

						notificationCountLabelPanel.setOpaque(false);
						notificationCountLabelPanel.add(notificationCountLabel);
						int width = notificationCountLabel.getPreferredSize().width;
						int height = notificationCountLabel.getPreferredSize().height;
						if (isServer(conversation)) {
							notificationCountLabelPanel.setBounds(panelWidth
									- 16 - width, 4, width, height + 10);
						} else if (conversation instanceof Contact) {
							Contact contact = (Contact) conversation;
							if (contact.getFirstLineOfMood() != null
									&& contact.getFirstLineOfMood().length() > 0) {
								notificationCountLabelPanel.setBounds(
										panelWidth - 16 - width, 4, width,
										height + 10);
							} else {
								notificationCountLabelPanel.setBounds(
										panelWidth - 16 - width, 11, width,
										height + 10);
							}
						} else {
							notificationCountLabelPanel.setBounds(panelWidth
									- 16 - width, 11, width, height + 10);
						}

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(
								notificationCountLabelPanel, new Integer(0), 0);
					}

					/**
					 * Add conversation name in conversation panel
					 */
					{
						JPanel conversationNameLabelPanel = new JPanel();
						int notificationCountWidth = fm.stringWidth(""
								+ conversation.getNotificationCount());
						JLabel conversationNameLabel = new JLabel(
								Utils.concatStringEllipses(
										fm,
										panelWidth
												- 73
												- (conversation
														.getNotificationCount() > 0
														&& conversation
																.getNotificationCount() < 100 ? notificationCountWidth
														+ fm.charWidth('1')
														: 0)
												- this.scrollBarWidth,
										conversation.getDisplayName()));
						conversationNameLabel
								.addMouseListener(conversationMouseAdapter);
						if (!conversation.isGroupChat()) {
							conversationNameLabel.setComponentPopupMenu(popUp);
						}
						conversationNameLabel.setFont(font);
						conversationNameLabel.setToolTipText(conversation
								.getDisplayName());
						if (conversation.getNotificationCount() > 0) {
							conversationNameLabel.setForeground(new Color(255,
									125, 0));
							conversationNameLabel.setFont(font.deriveFont(
									Font.BOLD, font.getSize()));
						}

						conversationNameLabelPanel.setOpaque(false);
						conversationNameLabelPanel.add(conversationNameLabel);
						int width = conversationNameLabel.getPreferredSize().width;
						int height = conversationNameLabel.getPreferredSize().height;
						if (isServer(conversation)) {
							conversationNameLabelPanel.setBounds(70, 4, width,
									height + 10);
						} else if (conversation instanceof Contact) {
							Contact contact = (Contact) conversation;
							if ((contact.getFirstLineOfMood() != null && contact
									.getFirstLineOfMood().length() > 0)) {
								conversationNameLabelPanel.setBounds(70, 4,
										width, height + 10);
							} else {
								conversationNameLabelPanel.setBounds(70, 11,
										width, height + 10);
							}
						} else {
							conversationNameLabelPanel.setBounds(70, 11, width,
									height + 10);
						}

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(conversationNameLabelPanel,
								new Integer(0), 0);
					}

					if (conversation instanceof Contact) {
						Contact contact = (Contact) conversation;
						if ((contact.getFirstLineOfMood() != null && contact
								.getFirstLineOfMood().length() > 0)) {
							/**
							 * Add contact mood in conversation panel
							 */
							JPanel contactMoodLabelPanel = new JPanel();
							int notificationCountWidth = fm.stringWidth(""
									+ conversation.getNotificationCount());
							JLabel contactMoodLabel;
							contactMoodLabel = new JLabel(
									Utils.concatStringEllipses(
											fm,
											panelWidth
													- 73
													- (conversation
															.getNotificationCount() > 0
															&& conversation
																	.getNotificationCount() < 100 ? notificationCountWidth
															+ fm.charWidth('1')
															: 0)
													- this.scrollBarWidth,
											contact.getFirstLineOfMood()));
							contactMoodLabel
									.addMouseListener(conversationMouseAdapter);
							{
								contactMoodLabel.setComponentPopupMenu(popUp);
							}
							contactMoodLabel.setFont(font);
							contactMoodLabel.setToolTipText(contact
									.getFirstLineOfMood());
							contactMoodLabel.setForeground(new Color(158, 166,
									169));

							contactMoodLabelPanel.setOpaque(false);
							contactMoodLabelPanel.add(contactMoodLabel);
							int width = contactMoodLabel.getPreferredSize().width;
							int height = contactMoodLabel.getPreferredSize().height;
							contactMoodLabelPanel.setBounds(70, 22, width,
									height + 10);

							/**
							 * Panel added to pane with z-index 0
							 */
							conversationLayeredPane.add(contactMoodLabelPanel,
									new Integer(0), 0);
						}
					}

					if (isServer(conversation)) {
						/**
						 * Add contact mood in conversation panel
						 */
						String mood = conversation.getUniqueId2() + "";
						JPanel contactMoodLabelPanel = new JPanel();
						int notificationCountWidth = fm.stringWidth(""
								+ conversation.getNotificationCount());
						JLabel contactMoodLabel;
						contactMoodLabel = new JLabel(
								Utils.concatStringEllipses(
										fm,
										panelWidth
												- 73
												- (conversation
														.getNotificationCount() > 0
														&& conversation
																.getNotificationCount() < 100 ? notificationCountWidth
														+ fm.charWidth('1')
														: 0)
												- this.scrollBarWidth, mood));
						contactMoodLabel
								.addMouseListener(conversationMouseAdapter);
						{
							contactMoodLabel.setComponentPopupMenu(popUp);
						}
						contactMoodLabel.setFont(font);
						contactMoodLabel.setToolTipText(mood);
						contactMoodLabel
								.setForeground(new Color(158, 166, 169));

						contactMoodLabelPanel.setOpaque(false);
						contactMoodLabelPanel.add(contactMoodLabel);
						int width = contactMoodLabel.getPreferredSize().width;
						int height = contactMoodLabel.getPreferredSize().height;
						contactMoodLabelPanel.setBounds(70, 22, width,
								height + 10);

						/**
						 * Panel added to pane with z-index 0
						 */
						conversationLayeredPane.add(contactMoodLabelPanel,
								new Integer(0), 0);
					}

					conversationPanel.add(conversationLayeredPane);

					panel.add(conversationPanel);
				}
			}
		}

		panel.add(Box.createRigidArea(new Dimension(10, 5)));

		recentNotificationCountLabel.setText("" + this.notificationCount);

		if (this.notificationCount == 0) {
			recentNotificationCountLabelPanel.setVisible(false);
		} else {
			Rectangle rect = recentNotificationCountLabelPanel.getBounds();
			rect.width = recentNotificationCountLabel.getPreferredSize().width;
			recentNotificationCountLabelPanel.setBounds(rect);
			recentNotificationCountLabelPanel.setVisible(true);
		}

		return panel;
	}

	private JLayeredPane createRightTopLayeredPane(int panelWidth,
			int panelHeight) {
		/* Start right top panel */
		/**
		 * Construct layered pane for absolute positioning with z index
		 */
		JLayeredPane rightTopLayeredPane = new JLayeredPane();
		rightTopLayeredPane.setBounds(0, 0, panelWidth, panelHeight);
		rightTopLayeredPane.setPreferredSize(new Dimension(panelWidth,
				panelHeight));
		rightTopLayeredPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1,
				0, new Color(211, 230, 234)));
		rightTopLayeredPane.setOpaque(false);

		if (selectedConversation == null
				&& !rightPanelPage.equals("CallPhones")) {
			/**
			 * The selected right panel page is not Conversation
			 * 
			 * We want the right panel to be just a JScrollPane in this case
			 * 
			 * So we will not put a right top layered pane in this case
			 */
			return rightTopLayeredPane;
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon;
			if (!rightPanelPage.equals("CallPhones")) {
				imageIcon = selectedConversation.getImageIcon();
			} else {
				imageIcon = ImageIO.getResourceAsImageIcon("/125923726.png");
			}
			imageIcon = ImageIO.getScaledImageIcon(imageIcon, new Dimension(60,
					60));
			imageIcon = ImageIO.getCircularImageIcon(imageIcon);
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (!rightPanelPage.equals("CallPhones")) {
						if (selectedConversation.getPubKey().isPresent()) {
							String pubKey;
							try {
								pubKey = PGPainless
										.asciiArmor(selectedConversation
												.getPubKey().get());
								if (selectedConversation instanceof Contact) {
									rightPanelPage = "ViewUser";
									refreshWindow();
								} else {
									JOptionPane
											.showMessageDialog(frame, pubKey);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(frame,
									selectedConversation);
						}
					}
				}

			};

			iconLabel.addMouseListener(mouseAdapter);

			iconLabelPanel.setBounds(10, 10, 60, 60);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")) {
				rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1074949811.png");
			if (!rightPanelPage.equals("CallPhones")) {
				if (selectedConversation instanceof Contact) {
					if (((Contact) selectedConversation).isFavorite()) {
						imageIcon = new ImageIcon(new BufferedImage(30, 30,
								BufferedImage.TYPE_INT_ARGB));
					}
				}
			}
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(80, 25, 14, 14);

			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")) {
				rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getCircularStatusIcon(Status.NOT_A_CONTACT);
			if (!rightPanelPage.equals("CallPhones")) {
				if (selectedConversation instanceof Contact) {
					imageIcon = ImageIO
							.getCircularStatusIcon(((Contact) selectedConversation)
									.getOnlineStatus());
				} else if ((selectedConversation instanceof VoIPContact && ((VoIPContact) selectedConversation)
						.isContact())) {
					Status onlineStatus = Status.OFFLINE;
					if (VoIP.getPlugin().isConnected()) {
						onlineStatus = Status.ONLINE;
					}
					imageIcon = ImageIO.getCircularStatusIcon(onlineStatus);
				} else if (selectedConversation.isGroupChat()) {
					imageIcon = ImageIO
							.getResourceAsImageIcon("/212013846.png");
				}
			}
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				iconLabelPanel.setBounds(81, 48, 12, 12);
			} else {
				iconLabelPanel.setBounds(80, 47, 14, 14);
			}
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")) {
				rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
		}

		/**
		 * Add status text floating in top left panel
		 */
		{
			JPanel statusLabelPanel = new JPanel();

			statusLabelPanel.setOpaque(false);

			/**
			 * Capitalize only the first letter of the online status
			 */
			JLabel statusLabel = new JLabel(
					" This person isn't in your Contact list.");
			if (rightPanelPage.equals("CallPhones")) {
			} else {
				if ((selectedConversation instanceof VoIPContact && ((VoIPContact) selectedConversation)
						.isContact())) {
					Status onlineStatus = Status.OFFLINE;
					if (VoIP.getPlugin().isConnected()) {
						onlineStatus = Status.ONLINE;
					}
					statusLabel = new JLabel(WordUtils.capitalize(onlineStatus
							.getReadableName()));
				} else if (selectedConversation instanceof Contact) {
					Status onlineStatus = ((Contact) selectedConversation)
							.getOnlineStatus();
					statusLabel = new JLabel(WordUtils.capitalize(onlineStatus
							.getReadableName()));
				} else if (selectedConversation.isGroupChat()) {
					JLabel label = new JLabel(selectedConversation
							.getParticipants().size() + " people");
					MouseAdapter mouseAdapter = new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent evt) {
							super.mousePressed(evt);
							if (selectedConversation.getGroupChatAdmins()
									.contains(loggedInUser.getUniqueId())) {
								List<String> participants = new ArrayList<>();
								for (UUID participantId : selectedConversation
										.getParticipants()) {
									Optional<Conversation> userLookup = lookupUser(participantId);
									if (userLookup.isPresent()) {
										participants.add(userLookup.get()
												.getSkypeName());
									}
								}
								RemoveParticipantsFromGroupChatForm form = new RemoveParticipantsFromGroupChatForm(
										participants,
										new RemoveParticipantsFromGroupChatForm.Runnable() {

											@Override
											public void run() {
												Conversation groupChat = selectedConversation;
												boolean displayNameUpdate = false;
												{
													String displayName = "";
													int x = 0;
													for (UUID participantId2 : groupChat
															.getParticipants()) {
														Optional<Conversation> userLookup = lookupUser(participantId2);
														if (userLookup
																.isPresent()) {
															if (x == groupChat
																	.getParticipants()
																	.size() - 2) {
																displayName += userLookup
																		.get()
																		.getDisplayName()
																		+ ", and ";
															} else {
																displayName += userLookup
																		.get()
																		.getDisplayName()
																		+ ", ";
															}
														} else {
															displayName += participantId2
																	.toString()
																	+ ", ";
														}
														x++;
													}
													if (displayName.length() > 0) {
														displayName = displayName
																.substring(
																		0,
																		displayName
																				.length() - 2);
													}
													if (displayName.equals(groupChat
															.getDisplayName())) {
														displayNameUpdate = true;
													}
												}
												Optional<UUID> authCode2 = registerUser(
														groupChat, password);
												if (!authCode2.isPresent()) {
													return;
												}
												UUID messageId = UUID
														.randomUUID();
												long timestamp = System
														.currentTimeMillis();
												Message message = new Message(
														messageId,
														loggedInUser
																.getUniqueId(),
														MessageType.GROUP_MEMBER_REMOVED,
														"?", timestamp,
														groupChat);
												String displayName = "";
												int x = 0;
												for (String participantId2 : this
														.getParticipants()) {
													Optional<Conversation> userLookup = lookupUser(Skype
															.getPlugin()
															.getUniqueId(
																	participantId2));
													if (userLookup.isPresent()) {
														if (x == this
																.getParticipants()
																.size() - 2) {
															displayName += userLookup
																	.get()
																	.getDisplayName()
																	+ ", and ";
														} else {
															displayName += userLookup
																	.get()
																	.getDisplayName()
																	+ ", ";
														}
													} else {
														displayName += participantId2
																.toString()
																+ ", ";
													}
													x++;
												}
												if (displayName.length() > 0) {
													displayName = displayName
															.substring(
																	0,
																	displayName
																			.length() - 2);
												}
												message.setMessage(displayName);
												if (!conversations
														.contains(groupChat)) {
													conversations
															.add(groupChat);
												}
												UUID conversationId = groupChat
														.getUniqueId();
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (!ctx.isPresent()) {
													return;
												}
												try {
													ctx.get()
															.getSocket()
															.setSoTimeout(10000);
												} catch (SocketException e) {
													e.printStackTrace();
												}
												List<String> list = participants;
												for (String participant : this
														.getParticipants()) {
													list.remove(participant);
												}
												Object payload = GsonBuilder
														.create().toJson(list);
												Optional<PacketPlayInReply> replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutSendMessage(
																		authCode,
																		conversationId,
																		messageId,
																		message.toString(),
																		timestamp));
												if (!replyPacket.isPresent()) {
													return;
												}
												if (replyPacket.get()
														.getStatusCode() != 200) {
													return;
												}
												groupChat.getMessages().add(
														message);
												replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutUpdateGroupChatParticipants(
																		authCode2
																				.get(),
																		payload));
												if (!replyPacket.isPresent()) {
													return;
												}
												if (replyPacket.get()
														.getStatusCode() != 200) {
													return;
												}
												groupChat
														.setLastModified(new Date(
																new Date()
																		.getTime()
																		+ AppDelegate.TIME_OFFSET));
												if (displayNameUpdate) {
													String displayName2 = "";
													int x2 = 0;
													for (String participantId2 : list) {
														if (x2 == list.size() - 2) {
															displayName2 += lookupUser(
																	Skype.getPlugin()
																			.getUniqueId(
																					participantId2))
																	.get()
																	.getDisplayName()
																	+ ", and ";
														} else {
															displayName2 += lookupUser(
																	Skype.getPlugin()
																			.getUniqueId(
																					participantId2))
																	.get()
																	.getDisplayName()
																	+ ", ";
														}
														x2++;
													}
													if (displayName2.length() > 0) {
														displayName2 = displayName2
																.substring(
																		0,
																		displayName2
																				.length() - 2);
													}
													String res2 = displayName2;
													groupChat
															.setDisplayName(res2);
													registerUser(groupChat,
															password);
													if (authCode2.isPresent()) {
														refreshWindow(SCROLL_TO_BOTTOM);
													} else {
														groupChat
																.setDisplayName(displayName2);
													}
												} else {
													refreshWindow(SCROLL_TO_BOTTOM);
												}
												if (!participants.contains(loggedInUser
														.getSkypeName())) {
													conversations
															.remove(groupChat);
													rightPanelPage = "AccountHome";
													refreshWindow(SCROLL_TO_BOTTOM);
												}
												AudioIO.IM_SENT.playSound();
											}
										});
								form.setLocationRelativeTo(label);
								int x = form.getLocation().x;
								int y = form.getLocation().y
										+ (form.getContentPane().getHeight() / 2)
										+ (label.getHeight() / 2) + 7;
								form.setLocation(x, y);
								form.show();
							} else {
								List<String> participants = new ArrayList<>();
								for (UUID participantId : selectedConversation
										.getParticipants()) {
									Optional<Conversation> userLookup = lookupUser(participantId);
									if (userLookup.isPresent()) {
										participants.add(userLookup.get()
												.getSkypeName());
									}
								}
								ViewParticipantsInGroupChatForm form = new ViewParticipantsInGroupChatForm(
										participants);
								form.setLocationRelativeTo(label);
								int x = form.getLocation().x;
								int y = form.getLocation().y
										+ (form.getContentPane().getHeight() / 2)
										+ (label.getHeight() / 2) + 7;
								form.setLocation(x, y);
								form.show();
							}
						}

					};

					label.addMouseListener(mouseAdapter);
					label.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					statusLabel = label;
				}
			}

			statusLabel.setFont(font);

			int width = statusLabel.getPreferredSize().width;
			int height = statusLabel.getPreferredSize().height;

			statusLabelPanel.add(statusLabel);
			statusLabelPanel.setBounds(104, 40, width, height + 10);

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			if (!rightPanelPage.equals("CallPhones")) {
				rightTopLayeredPane.add(statusLabelPanel, new Integer(1), 0);
			}
		}

		/**
		 * Add display name floating in top left panel
		 */
		{
			JPanel displayNameLabelPanel = new JPanel();
			FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(
					FontIO.SEGOE_UI_LIGHT.deriveFont(Font.TRUETYPE_FONT, 22));
			JLabel displayNameLabel = new JLabel(Utils.concatStringEllipses(fm,
					panelWidth - 320,
					rightPanelPage.equals("CallPhones") ? voipNumber
							: selectedConversation.getDisplayName()));
			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
				} else {
					displayNameLabel = new JLabel(
							Utils.concatStringEllipses(
									fm,
									panelWidth - 320 + 55,
									rightPanelPage.equals("CallPhones") ? voipNumber
											: selectedConversation
													.getDisplayName()));
				}
			}
			displayNameLabel.setFont(FontIO.SEGOE_UI_LIGHT.deriveFont(
					Font.TRUETYPE_FONT, 22));

			/**
			 * Reserved for future use
			 */
			displayNameLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (rightPanelPage.equals("CallPhones")) {
						String res2 = (String) JOptionPane.showInputDialog(
								frame, "Enter new phone number",
								frame.getTitle(), JOptionPane.PLAIN_MESSAGE,
								null, null, voipNumber);
						if (res2 != null) {
							if (res2.trim().equals("")) {
								res2 = "";
							}
							voipNumber = res2;
							refreshWindow();
						}
					} else {
						if (selectedConversation.getPubKey().isPresent()) {
							if (selectedConversation instanceof Contact) {
								rightPanelPage = "ViewUser";
								refreshWindow();
							} else {
								String pubKey;
								try {
									pubKey = PGPainless
											.asciiArmor(selectedConversation
													.getPubKey().get());
									JOptionPane
											.showMessageDialog(frame, pubKey);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} else {
							if (selectedConversation.isGroupChat()
									&& selectedConversation
											.getGroupChatAdmins().contains(
													loggedInUser.getUniqueId())) {
								Conversation groupChat = selectedConversation;
								String res2 = (String) JOptionPane
										.showInputDialog(
												frame,
												"Enter new name for group chat",
												frame.getTitle(),
												JOptionPane.PLAIN_MESSAGE,
												null, null,
												groupChat.getDisplayName());
								if (res2 != null) {
									if (res2.trim().equals("")) {
										String displayName = "";
										int x = 0;
										for (UUID participantId2 : groupChat
												.getParticipants()) {
											Optional<Conversation> userLookup = lookupUser(participantId2);
											if (userLookup.isPresent()) {
												if (x == groupChat
														.getParticipants()
														.size() - 2) {
													displayName += userLookup
															.get()
															.getDisplayName()
															+ ", and ";
												} else {
													displayName += userLookup
															.get()
															.getDisplayName()
															+ ", ";
												}
											} else {
												displayName += participantId2
														.toString() + ", ";
											}
											x++;
										}
										if (displayName.length() > 0) {
											displayName = displayName
													.substring(0, displayName
															.length() - 2);
										}
										res2 = displayName;
									}
									String displayName = groupChat
											.getDisplayName();
									groupChat.setDisplayName(res2);
									Optional<UUID> authCode2 = registerUser(
											groupChat, password);
									if (authCode2.isPresent()) {
										refreshWindow(SCROLL_TO_BOTTOM);
									} else {
										groupChat.setDisplayName(displayName);
									}
								}
								return;
							} else {
								JOptionPane.showMessageDialog(frame,
										selectedConversation.toString());
							}
						}
					}
				}

			};

			displayNameLabel.addMouseListener(mouseAdapter);

			displayNameLabelPanel.setOpaque(false);
			displayNameLabelPanel.add(displayNameLabel);
			int width = displayNameLabel.getPreferredSize().width;
			int height = displayNameLabel.getPreferredSize().height;
			if (rightPanelPage.equals("CallPhones")) {
				displayNameLabelPanel
						.setBounds(104 - 84, 9, width, height + 10);
			} else {
				displayNameLabelPanel.setBounds(104, 11, width, height + 10);
			}

			/**
			 * Panel added to pane with z-index 1, above decoration panel
			 */
			rightTopLayeredPane.add(displayNameLabelPanel, new Integer(1), 0);
		}

		/**
		 * Add country code and name floating in top left panel
		 */
		{
			JPanel countryCodeAndNamePanel = new JPanel();
			JLabel countryCodeAndNameLabel = new JLabel("San Marino (+378)");
			if (voipNumber.startsWith("+") && voipNumber.length() >= 2) {
				PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
				try {
					PhoneNumber number = phoneUtil.parse(voipNumber, "gb");
					countryCodeAndNameLabel.setText(new Locale("", phoneUtil
							.getRegionCodeForNumber(number))
							.getDisplayCountry()
							+ " (+" + number.getCountryCode() + ")");
				} catch (Exception e) {
				}
			}
			countryCodeAndNameLabel.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
			countryCodeAndNameLabel.setForeground(new Color(0, 149, 204));

			/**
			 * Reserved for future use
			 */
			countryCodeAndNameLabel.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			countryCodeAndNamePanel.setOpaque(false);
			countryCodeAndNamePanel.add(countryCodeAndNameLabel);
			int width = countryCodeAndNameLabel.getPreferredSize().width;
			int height = countryCodeAndNameLabel.getPreferredSize().height;
			if (rightPanelPage.equals("CallPhones")) {
				countryCodeAndNamePanel.setBounds(104 - 84, 40, width,
						height + 10);
				rightTopLayeredPane.add(countryCodeAndNamePanel,
						new Integer(1), 0);
			}
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/62844439.png");
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					voipNumber = "";
					refreshWindow();
				}
			});

			iconLabelPanel.setBounds(panelWidth - 111 - 15 - 10 - 27, 31, 18,
					18);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (rightPanelPage.equals("CallPhones")) {
				if (voipNumber != null && !voipNumber.equals("")) {
					rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
				}
			}
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1154498825.png");
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
					iconLabelPanel.setBounds(panelWidth - 166, 20, 40, 40);
				} else {
					iconLabelPanel.setBounds(panelWidth - 111, 20, 40, 40);
				}
			} else {
				iconLabelPanel.setBounds(panelWidth - 166, 20, 40, 40);
			}
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")) {
				rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
		}

		{
			MouseAdapter iconLabelMouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (rightPanelPage.equals("CallPhones")
							|| selectedConversation instanceof VoIPContact) {
						if (!(selectedConversation instanceof VoIPContact)) {
							if (voipNumber.equals("")) {
								return;
							}
							VoIPContact testvoip = new VoIPContact();
							testvoip.setSkypeName(voipNumber);
							testvoip.setUniqueId(Skype.getPlugin().getUniqueId(
									testvoip.getSkypeName()));
							testvoip.setDisplayName(testvoip.getSkypeName());
							testvoip.setLastModified(new Date(new Date()
									.getTime() + AppDelegate.TIME_OFFSET));
							boolean hit = false;
							for (Conversation conversation : conversations) {
								if (conversation.getUniqueId().equals(
										testvoip.getUniqueId())) {
									testvoip = (VoIPContact) conversation;
									hit = true;
									break;
								}
							}
							if (!hit) {
								conversations.add(testvoip);
								saveVoipContactList();
							}
							ongoingCall = true;
							selectedConversation = testvoip;
							ongoingCallConversation = testvoip;
						} else {
							ongoingCall = true;
							ongoingCallConversation = selectedConversation;
							voipNumber = selectedConversation.getDisplayName();
						}
						ongoingCallStartTime = System.currentTimeMillis();
						rightPanelPage = "OngoingCall";
						AudioIO.CALL_INIT.playSound();
						String peer2 = ongoingCallConversation.getDisplayName();
						VoIPCall ret = VoIP.getPlugin().API_Call(voipNumber,
								true, new VoIP.Runnable() {

									@Override
									public void run() {
										if (!ongoingCall) {
											return;
										}
										if (!peer2
												.equals(ongoingCallConversation
														.getDisplayName())) {
											return;
										}
										if (ongoingCallConversation instanceof VoIPContact) {
											VoIP.getPlugin().API_Hangup(
													VoIP.getPlugin()
															.API_GetLine());
										}
										if (ongoingCallConversation
												.getUniqueId().equals(
														echoSoundTestService
																.getUniqueId())) {
											if (echo123 == null) {
											} else {
												echo123.stop();
											}
										}
										mic.stop();
										mic.drain();
										ongoingCall = false;
										ongoingCallConversation = null;
										MainForm.get().ongoingCallParticipants
												.clear();
										ongoingCallId = null;
										ongoingCallCipher = null;
										ongoingCallStartTime = 0L;
										rightPanelPage = "Conversation";
										refreshWindow(SCROLL_TO_BOTTOM);
										try {
											for (Socket socket : callIncomingAudioSockets) {
												socket.close();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											for (Socket socket : callOutgoingAudioSockets) {
												socket.close();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										ongoingVideoCall = false;
										ongoingVideoCallId = null;
										ongoingVideoCallCipher = null;
										try {
											for (Socket socket : videoCallIncomingAudioSockets) {
												socket.close();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											for (Socket socket : videoCallOutgoingAudioSockets) {
												socket.close();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										videoEnabled = false;
										microphoneEnabled = true;
										MainForm.get().videoMode = MainForm
												.get().WEBCAM_CAPTURE_MODE;
										MainForm.ongoingVideoCallWidth = 0;
										MainForm.ongoingVideoCallHeight = 0;
										try {
											Class<?> clazz = Class
													.forName("com.github.sarxos.webcam.Webcam");
											Method close = clazz.getMethod(
													"close", null);
											close.invoke(MainForm.webcam, null);
										} catch (Exception e) {
											e.printStackTrace();
										}
										AudioIO.HANGUP.playSound();
									}

								});
						ongoingCallObj = ret;
						refreshWindow();
					} else {
						microphoneEnabled = true;
						mic.start();
						if (ongoingCall == false) {
							Optional<SocketHandlerContext> ctx = Skype
									.getPlugin().createHandle();
							if (!ctx.isPresent()) {
								return;
							}
							byte[] cipher;
							try {
								cipher = CipherUtilities.randomCipher();
							} catch (InvalidKeyException
									| NoSuchPaddingException
									| NoSuchAlgorithmException
									| InvalidAlgorithmParameterException
									| UnsupportedEncodingException e) {
								e.printStackTrace();
								return;
							}
							String base64 = CipherUtilities
									.encodeBase64(cipher);
							String message = PGPUtilities.encryptAndSign(
									base64, selectedConversation);
							Optional<PacketPlayInReply> reply = ctx
									.get()
									.getOutboundHandler()
									.dispatch(
											ctx.get(),
											new PacketPlayOutSendCallRequest(
													authCode,
													selectedConversation
															.getUniqueId(),
													message));
							if (!reply.isPresent()) {
								return;
							}
							if (reply.get().getStatusCode() != 200) {
								return;
							}
							AudioIO.CALL_INIT.playSound();
							if (selectedConversation.getUniqueId().equals(
									echoSoundTestService.getUniqueId())) {
								if (echo123 == null) {
									echo123 = AudioIO.ECHO123.playSound();
								} else {
									echo123.stop();
									echo123.close();
									echo123 = AudioIO.ECHO123.playSound();
								}
							}
						}
						rightPanelPage = "OngoingCall";
						ongoingCall = true;
						ongoingCallConversation = selectedConversation;
						ongoingCallStartTime = new Date(new Date().getTime()
								+ AppDelegate.TIME_OFFSET).getTime();
						refreshWindow();
					}
				}
			};
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1342120668.png");
			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			iconLabel.addMouseListener(iconLabelMouseAdapter);

			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
					iconLabelPanel.setBounds(panelWidth - 111, 20, 40, 40);
				} else {
					iconLabelPanel.setBounds(panelWidth - 56, 20, 40, 40);
				}
			} else {
				iconLabelPanel.setBounds(panelWidth - 111, 20, 40, 40);
			}

			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);
			iconLabelPanel.addMouseListener(iconLabelMouseAdapter);

			/**
			 * Panel added to pane with z-index 0
			 */
			rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
		}

		{
			JPanel iconLabelPanel = new JPanel();

			MouseAdapter iconLabelMouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (rightPanelPage.equals("CallPhones")) {
						if (voipNumber.equals("")) {
							return;
						}
						VoIPContact testvoip = new VoIPContact();
						testvoip.setSkypeName(voipNumber);
						testvoip.setUniqueId(Skype.getPlugin().getUniqueId(
								testvoip.getSkypeName()));
						testvoip.setDisplayName(testvoip.getSkypeName());
						testvoip.setLastModified(new Date(new Date().getTime()
								+ AppDelegate.TIME_OFFSET));
						boolean hit = false;
						for (Conversation conversation : conversations) {
							if (conversation.getUniqueId().equals(
									testvoip.getUniqueId())) {
								testvoip = (VoIPContact) conversation;
								hit = true;
								break;
							}
						}
						if (!hit) {
							conversations.add(testvoip);
							saveVoipContactList();
						}
						selectedConversation = testvoip;
						rightPanelPage = "Conversation";
						refreshWindow();
					} else {
						List<String> skypeNames = new ArrayList<>();
						for (Conversation conversation : conversations) {
							if (conversation instanceof Contact) {
								if (selectedConversation.isGroupChat()) {
									if (selectedConversation.getParticipants()
											.contains(
													conversation.getUniqueId())) {
										continue;
									}
								}
								skypeNames.add(conversation.getSkypeName());
							}
						}
						AddParticipantsToGroupChatForm form = new AddParticipantsToGroupChatForm(
								selectedConversation, skypeNames,
								new AddParticipantsToGroupChatForm.Runnable() {

									@Override
									public void run() {
										Conversation groupChat = selectedConversation;
										boolean displayNameUpdate = false;
										if (!selectedConversation.isGroupChat()) {
											String skypeName;
											do {
												skypeName = "guest:"
														+ UUID.randomUUID()
																.toString()
																.replace("-",
																		"")
																.substring(0,
																		16);
											} while (registry
													.contains(skypeName));
											UUID participantId = Skype
													.getPlugin().getUniqueId(
															skypeName);
											groupChat = new Conversation();
											groupChat
													.setUniqueId(participantId);
											groupChat.setGroupChat(true);
											String displayName = "";
											int x = 0;
											for (String participantId2 : this
													.getParticipants()) {
												Optional<Conversation> userLookup = lookupUser(Skype
														.getPlugin()
														.getUniqueId(
																participantId2));
												if (userLookup.isPresent()) {
													if (x == this
															.getParticipants()
															.size() - 2) {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", and ";
													} else {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", ";
													}
												} else {
													displayName += participantId2
															.toString() + ", ";
												}
												x++;
											}
											if (displayName.length() > 0) {
												displayName = displayName
														.substring(
																0,
																displayName
																		.length() - 2);
											}
											groupChat
													.setDisplayName(displayName);
											groupChat.setSkypeName(skypeName);
										} else {
											String displayName = "";
											int x = 0;
											for (UUID participantId2 : groupChat
													.getParticipants()) {
												Optional<Conversation> userLookup = lookupUser(participantId2);
												if (userLookup.isPresent()) {
													if (x == groupChat
															.getParticipants()
															.size() - 2) {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", and ";
													} else {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", ";
													}
												} else {
													displayName += participantId2
															.toString() + ", ";
												}
												x++;
											}
											if (displayName.length() > 0) {
												displayName = displayName
														.substring(
																0,
																displayName
																		.length() - 2);
											}
											if (displayName.equals(groupChat
													.getDisplayName())) {
												displayNameUpdate = true;
											}
										}
										Optional<UUID> authCode2 = registerUser(
												groupChat, password);
										if (!authCode2.isPresent()) {
											return;
										}
										UUID messageId = UUID.randomUUID();
										long timestamp = new Date(new Date()
												.getTime()
												+ AppDelegate.TIME_OFFSET)
												.getTime();
										Message message = new Message(
												messageId, loggedInUser
														.getUniqueId(),
												MessageType.GROUP_MEMBER_ADDED,
												"?", timestamp, groupChat);
										if (!selectedConversation.isGroupChat()) {
											message = new Message(
													messageId,
													loggedInUser.getUniqueId(),
													MessageType.GROUP_CHAT_CREATED,
													"?", timestamp, groupChat);
										} else {
											String displayName = "";
											int x = 0;
											List<String> names = new ArrayList<>();
											for (String name : this
													.getParticipants()) {
												if (name.equals(loggedInUser
														.getSkypeName())) {
													continue;
												}
												names.add(name);
											}
											for (String participantId2 : names) {
												Optional<Conversation> userLookup = lookupUser(Skype
														.getPlugin()
														.getUniqueId(
																participantId2));
												if (userLookup.isPresent()) {
													if (x == names.size() - 2) {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", and ";
													} else {
														displayName += userLookup
																.get()
																.getDisplayName()
																+ ", ";
													}
												} else {
													displayName += participantId2
															.toString() + ", ";
												}
												x++;
											}
											if (displayName.length() > 0) {
												displayName = displayName
														.substring(
																0,
																displayName
																		.length() - 2);
											}
											message.setMessage(displayName);
										}
										if (!conversations.contains(groupChat)) {
											conversations.add(groupChat);
										}
										UUID conversationId = groupChat
												.getUniqueId();
										Optional<SocketHandlerContext> ctx = Skype
												.getPlugin().createHandle();
										if (!ctx.isPresent()) {
											return;
										}
										try {
											ctx.get().getSocket()
													.setSoTimeout(10000);
										} catch (SocketException e) {
											e.printStackTrace();
										}
										List<String> list = this
												.getParticipants();
										if (selectedConversation.isGroupChat()) {
											List<String> skypeNames = new ArrayList<>();
											for (UUID participantId2 : selectedConversation
													.getParticipants()) {
												Optional<Conversation> userLookup = lookupUser(participantId2);
												if (userLookup.isPresent()) {
													String skypeName = userLookup
															.get()
															.getSkypeName();
													if (skypeName
															.equals(loggedInUser
																	.getSkypeName())) {
														continue;
													}
													skypeNames.add(skypeName);
												}
											}
											list.addAll(skypeNames);
										}
										Object payload = GsonBuilder.create()
												.toJson(list);
										Optional<PacketPlayInReply> replyPacket = ctx
												.get()
												.getOutboundHandler()
												.dispatch(
														ctx.get(),
														new PacketPlayOutUpdateGroupChatParticipants(
																authCode2.get(),
																payload));
										if (!replyPacket.isPresent()) {
											return;
										}
										if (replyPacket.get().getStatusCode() != 200) {
											return;
										}
										replyPacket = ctx
												.get()
												.getOutboundHandler()
												.dispatch(
														ctx.get(),
														new PacketPlayOutSendMessage(
																authCode,
																conversationId,
																messageId,
																message.toString(),
																timestamp));
										if (!replyPacket.isPresent()) {
											return;
										}
										if (replyPacket.get().getStatusCode() != 200) {
											return;
										}
										groupChat.getMessages().add(message);
										groupChat
												.setLastModified(new Date(
														new Date().getTime()
																+ AppDelegate.TIME_OFFSET));
										if (selectedConversation.isGroupChat()
												&& displayNameUpdate) {
											String displayName2 = "";
											int x2 = 0;
											for (String participantId2 : list) {
												if (x2 == list.size() - 2) {
													displayName2 += lookupUser(
															Skype.getPlugin()
																	.getUniqueId(
																			participantId2))
															.get()
															.getDisplayName()
															+ ", and ";
												} else {
													displayName2 += lookupUser(
															Skype.getPlugin()
																	.getUniqueId(
																			participantId2))
															.get()
															.getDisplayName()
															+ ", ";
												}
												x2++;
											}
											if (displayName2.length() > 0) {
												displayName2 = displayName2
														.substring(
																0,
																displayName2
																		.length() - 2);
											}
											String res2 = displayName2;
											groupChat.setDisplayName(res2);
											registerUser(groupChat, password);
											if (authCode2.isPresent()) {
												refreshWindow(SCROLL_TO_BOTTOM);
											} else {
												groupChat
														.setDisplayName(displayName2);
											}
										} else {
											refreshWindow(SCROLL_TO_BOTTOM);
										}
										AudioIO.IM_SENT.playSound();
									}
								});
						form.setLocationRelativeTo(iconLabelPanel);
						int x = form.getLocation().x;
						int y = form.getLocation().y
								+ (form.getContentPane().getHeight() / 2)
								+ (iconLabelPanel.getHeight() / 2) + 7;
						form.setLocation(x, y);
						form.show();
					}
				}
			};

			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/653174002.png");
			if (rightPanelPage.equals("CallPhones")) {
				imageIcon = ImageIO.getResourceAsImageIcon("/67276506.png");
			}
			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			iconLabel.addMouseListener(iconLabelMouseAdapter);

			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
					iconLabelPanel.setBounds(panelWidth - 56, 20, 40, 40);
				} else {
					iconLabelPanel.setBounds(panelWidth - 56, 20, 40, 40);
				}
			} else {
				iconLabelPanel.setBounds(panelWidth - 56, 20, 40, 40);
			}
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);
			iconLabelPanel.addMouseListener(iconLabelMouseAdapter);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
					rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
				}
			} else {
				rightTopLayeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
		}

		{
			JPanel iconLabelPanel = new JPanel();

			MouseAdapter iconLabelMouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (rightPanelPage.equals("CallPhones")) {
					} else {
						List<String> skypeNames = new ArrayList<>();
						for (UUID participantId : selectedConversation
								.getParticipants()) {
							skypeNames.add(lookupUser(participantId).get()
									.getSkypeName());
						}

						Conversation groupChat = selectedConversation;
						boolean displayNameUpdate = false;
						UUID uuid2 = groupChat.getUniqueId2();
						{
							String skypeName;
							do {
								skypeName = "guest:"
										+ UUID.randomUUID().toString()
												.replace("-", "")
												.substring(0, 16);
							} while (registry.contains(skypeName));
							UUID participantId = Skype.getPlugin().getUniqueId(
									skypeName);
							groupChat = new Conversation();
							groupChat.setUniqueId(participantId);
							groupChat.setUniqueId2(uuid2);
							groupChat.setGroupChat(true);
							String displayName = "";
							int x = 0;
							for (String participantId2 : skypeNames) {
								Optional<Conversation> userLookup = lookupUser(Skype
										.getPlugin()
										.getUniqueId(participantId2));
								if (userLookup.isPresent()) {
									if (x == skypeNames.size() - 2) {
										displayName += userLookup.get()
												.getDisplayName() + ", and ";
									} else {
										displayName += userLookup.get()
												.getDisplayName() + ", ";
									}
								} else {
									displayName += participantId2.toString()
											+ ", ";
								}
								x++;
							}
							if (displayName.length() > 0) {
								displayName = displayName.substring(0,
										displayName.length() - 2);
							}
							groupChat.setDisplayName(displayName);
							groupChat.setSkypeName(skypeName);
						}
						Optional<UUID> authCode2 = registerUser(groupChat,
								password);
						if (!authCode2.isPresent()) {
							return;
						}
						UUID messageId = UUID.randomUUID();
						long timestamp = new Date(new Date().getTime()
								+ AppDelegate.TIME_OFFSET).getTime();
						Message message = new Message(messageId,
								loggedInUser.getUniqueId(),
								MessageType.GROUP_MEMBER_ADDED, "?", timestamp,
								groupChat);
						if (!selectedConversation.isGroupChat()) {
							message = new Message(messageId,
									loggedInUser.getUniqueId(),
									MessageType.GROUP_CHAT_CREATED, "?",
									timestamp, groupChat);
						} else {
							String displayName = "";
							int x = 0;
							List<String> names = new ArrayList<>();
							for (String name : skypeNames) {
								if (name.equals(loggedInUser.getSkypeName())) {
									continue;
								}
								names.add(name);
							}
							for (String participantId2 : names) {
								Optional<Conversation> userLookup = lookupUser(Skype
										.getPlugin()
										.getUniqueId(participantId2));
								if (userLookup.isPresent()) {
									if (x == names.size() - 2) {
										displayName += userLookup.get()
												.getDisplayName() + ", and ";
									} else {
										displayName += userLookup.get()
												.getDisplayName() + ", ";
									}
								} else {
									displayName += participantId2.toString()
											+ ", ";
								}
								x++;
							}
							if (displayName.length() > 0) {
								displayName = displayName.substring(0,
										displayName.length() - 2);
							}
							message.setMessage(displayName);
						}
						if (!conversations.contains(groupChat)) {
							conversations.add(groupChat);
						}
						UUID conversationId = groupChat.getUniqueId();
						Optional<SocketHandlerContext> ctx = Skype.getPlugin()
								.createHandle();
						if (!ctx.isPresent()) {
							return;
						}
						try {
							ctx.get().getSocket().setSoTimeout(10000);
						} catch (SocketException e) {
							e.printStackTrace();
						}
						List<String> list = skypeNames;
						Object payload = GsonBuilder.create().toJson(list);
						Optional<PacketPlayInReply> replyPacket = ctx
								.get()
								.getOutboundHandler()
								.dispatch(
										ctx.get(),
										new PacketPlayOutUpdateGroupChatParticipants(
												authCode2.get(), payload));
						if (!replyPacket.isPresent()) {
							return;
						}
						if (replyPacket.get().getStatusCode() != 200) {
							return;
						}
						replyPacket = ctx
								.get()
								.getOutboundHandler()
								.dispatch(
										ctx.get(),
										new PacketPlayOutSendMessage(authCode,
												conversationId, messageId,
												message.toString(), timestamp));
						if (!replyPacket.isPresent()) {
							return;
						}
						if (replyPacket.get().getStatusCode() != 200) {
							return;
						}
						groupChat.getMessages().add(message);
						groupChat.setLastModified(new Date(new Date().getTime()
								+ AppDelegate.TIME_OFFSET));
						refreshWindow(SCROLL_TO_BOTTOM);
						AudioIO.IM_SENT.playSound();
					}
				}
			};

			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1332517131.png");
			JLabel iconLabel = new JLabel(imageIcon);

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			iconLabel.addMouseListener(iconLabelMouseAdapter);

			iconLabelPanel.setBounds(panelWidth - 56, 20, 40, 40);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);
			iconLabelPanel.addMouseListener(iconLabelMouseAdapter);

			/**
			 * Panel added to pane with z-index 0
			 */
			if (!rightPanelPage.equals("CallPhones")
					&& selectedConversation.isGroupChat()) {
				if (selectedConversation.getGroupChatAdmins().contains(
						loggedInUser.getUniqueId())) {
				}
			}
		}

		return rightTopLayeredPane;
	}

	private JPanel createRightBottomTopPanel(int panelWidth, int flag) {
		JPanel panel = new JPanel();

		if (rightPanelPage.equals("CallPhones")) {
			int defaultRightTopPanelHeight = this.defaultRightTopPanelHeight
					+ this.splitPaneDividerSize;
			if (VoIP.getPlugin().isConnected()) {
			} else {
				return panel;
			}
			int leftSplitPaneWidth = leftSplitPane.getDividerLocation();
			if (leftSplitPaneWidth == 0 || leftSplitPaneWidth == -1) {
				leftSplitPaneWidth = this.defaultLeftPanelWidth;
			}
			panelWidth = getContentPane().getSize().width
					- this.splitPaneDividerSize - leftSplitPaneWidth;
			int panelHeight = this.getContentPane().getHeight()
					- defaultRightTopPanelHeight;
			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setBounds(0, 0, panelWidth, panelHeight);
			layeredPane
					.setPreferredSize(new Dimension(panelWidth, panelHeight));
			layeredPane.setOpaque(true);
			layeredPane.setBackground(Color.white);
			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(true);
				labelPanel.setBounds(79, 75, 304, 305);
				labelPanel.setBackground(new Color(228, 236, 239));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			// 1
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/168230556.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "1";
						AudioIO.DTMF_1.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(80, 76, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 2
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/973462361.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "2";
						AudioIO.DTMF_2.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(181, 76, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 3
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/964333089.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "3";
						AudioIO.DTMF_3.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(282, 76, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 4
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/965788035.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "4";
						AudioIO.DTMF_4.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(80, 152, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 5
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/265520345.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "5";
						AudioIO.DTMF_5.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(181, 152, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 6
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/586774838.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "6";
						AudioIO.DTMF_6.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(282, 152, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 7
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/394492702.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "7";
						AudioIO.DTMF_7.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(80, 228, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 8
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/723224497.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "8";
						AudioIO.DTMF_8.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(181, 228, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 9
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/549250036.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "9";
						AudioIO.DTMF_9.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(282, 228, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// *
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/634854321.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "*";
						AudioIO.DTMF_STAR.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(80, 304, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// 0
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/473946381.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					long before = 0L;

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						before = System.currentTimeMillis();
						AudioIO.DTMF_0.playSound();
					}

					@Override
					public void mouseReleased(MouseEvent evt) {
						super.mouseReleased(evt);
						long elapsed = System.currentTimeMillis() - before;
						if (elapsed < 500) {
							voipNumber += "0";
						} else {
							voipNumber += "+";
						}
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(181, 304, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			// #
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/470929456.png");
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						voipNumber += "#";
						AudioIO.DTMF_POUND.playSound();
						refreshWindow();
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(282, 304, 100, 75);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			int x = -1;
			int y = -5;
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Your Skype Credit");
				label.setFont(FontIO.SEGOE_UI_LIGHT.deriveFont(22.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 - 1 + 1 + x, 82 - 7 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("" + "\u20ac" + " 0,00");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(414 + 1 + x, 110 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Call San Marino from 4" + "\u00A2"
						+ "/min");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 127 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Add Skype Credit");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 144 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Subscriptions");
				label.setFont(FontIO.SEGOE_UI_LIGHT.deriveFont(22.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(414 + 1 - 1 + x, 185 - 7 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("You have no subscriptions");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 213 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Get a subscription");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 230 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("See all rates");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 304 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("View my account");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 267 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("A small");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 323 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("connection fee");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(455 + 1 + x, 323 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("applies to Pay");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(537 + 1 + x, 323 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("As You Go calls only.");
				label.setFont(FontIO.SEGOE_UI.deriveFont(12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(413 + 1 + x, 340 - 7 + 3 + y, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel.add(layeredPane);
			return panel;
		}

		if (rightPanelPage.equals("AccountHome")
				|| rightPanelPage.equals("ViewUser")) {
			Contact selectedUser = loggedInUser;
			if (rightPanelPage.equals("ViewUser")) {
				if (selectedConversation instanceof Contact) {
					selectedUser = ((Contact) selectedConversation);
				}
			}
			final Contact fselectedUser = selectedUser;
			int adj = -15;
			int leftSplitPaneWidth = leftSplitPane.getDividerLocation();
			if (leftSplitPaneWidth == 0 || leftSplitPaneWidth == -1) {
				leftSplitPaneWidth = this.defaultLeftPanelWidth;
			}
			panelWidth = getContentPane().getSize().width
					- this.splitPaneDividerSize - leftSplitPaneWidth;
			int panelHeight = this.getContentPane().getHeight();
			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setBounds(0, 0, panelWidth, panelHeight);
			layeredPane
					.setPreferredSize(new Dimension(panelWidth, panelHeight));
			layeredPane.setOpaque(true);
			layeredPane.setBackground(Color.white);

			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(false);
				labelPanel.setBounds(260, 19, panelWidth - 331, 1);
				labelPanel.setBorder(BorderFactory
						.createDashedBorder(new Color(147, 153, 157)));

				/**
				 * Panel added to pane with z-index 0
				 */
				// layeredPane.add(labelPanel, new Integer(0), 0);
			}

			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(false);
				labelPanel.setBounds(260, 103 + adj, panelWidth - 331, 1);
				labelPanel.setBorder(BorderFactory
						.createDashedBorder(new Color(147, 153, 157)));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(false);
				labelPanel.setBounds(19, 19, 222, 222);
				labelPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
						1, new Color(179, 231, 251)));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = fselectedUser.getImageIcon();
				imageIcon = ImageIO.getScaledImageIcon(imageIcon,
						new Dimension(160, 160));
				JLabel iconLabel = new JLabel(imageIcon);

				/**
				 * Reserved for future use
				 */
				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()) {
							String pubKey;
							try {
								pubKey = PGPainless.asciiArmor(fselectedUser
										.getPubKey().get());
								JOptionPane.showMessageDialog(frame, pubKey);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				iconLabel.addMouseListener(mouseAdapter);

				iconLabelPanel.setBounds(50, 50, 160, 160);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(iconLabelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel(fselectedUser.getDisplayName());
				label.setFont(FontIO.SEGOE_UI.deriveFont(18.f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(350, 26 - 9 + adj, width, height + 10);

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()
								&& !rightPanelPage.equals("ViewUser")) {
							String res2 = (String) JOptionPane.showInputDialog(
									frame, "Enter new name for "
											+ fselectedUser.getSkypeName(),
									frame.getTitle(),
									JOptionPane.PLAIN_MESSAGE, null, null,
									fselectedUser.getDisplayName());
							if (res2 != null) {
								if (res2.trim().equals("")) {
									res2 = fselectedUser.getSkypeName();
								}
								fselectedUser.setDisplayName(res2);
								Optional<UUID> authCode2 = registerUser(
										fselectedUser, password);
								if (authCode2.isPresent()) {
									refreshWindow(SCROLL_TO_BOTTOM);
								}
							}
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				label.addMouseListener(mouseAdapter);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				// layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Accounts");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(291 + 1, 58 - 8 + adj, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Public");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(panelWidth - 70 - 10 - width,
						58 - 8 + adj, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel(fselectedUser.getSkypeName());
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(349 + 1, 58 - 8 + adj, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Manage");
				// label.setFont(font);
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(349 + 1, 75 - 8 + adj, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Mood");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel
						.setBounds(309 + 1, 126 - 7 + adj, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			int diff = 0;
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("meow");
				int height2 = label.getPreferredSize().height;
				if (fselectedUser.getMood() == null
						|| fselectedUser.getMood().equals("")) {
					label = new JLabel("Enter mood message");
					label.setForeground(new Color(0, 149, 204));
				} else {
					StringBuilder sb = new StringBuilder();
					for (String line : fselectedUser.getMood()
							.replace("<", "&lt;").replace(">", "&gt;")
							.split("\\r?\\n")) {
						if (line.contains("https://")) {
							int startIndex = line.indexOf("https://");
							int endIndex = line.substring(startIndex).indexOf(
									" ");
							if (endIndex == -1) {
								String pre = line.substring(0, startIndex);
								String link = line.substring(startIndex);
								sb.append(pre + "<a href=\"" + link + "\">"
										+ link + "</a>" + '\n');
							} else {
								String pre = line.substring(0, startIndex);
								String link = line.substring(startIndex,
										endIndex + startIndex);
								String post = line.substring(endIndex
										+ startIndex);
								sb.append(pre + "<a href=\"" + link + "\">"
										+ link + "</a>" + post + '\n');
							}
						} else if (line.contains("http://")) {
							int startIndex = line.indexOf("http://");
							int endIndex = line.substring(startIndex).indexOf(
									" ");
							if (endIndex == -1) {
								String pre = line.substring(0, startIndex);
								String link = line.substring(startIndex);
								sb.append(pre + "<a href=\"" + link + "\">"
										+ link + "</a>" + '\n');
							} else {
								String pre = line.substring(0, startIndex);
								String link = line.substring(startIndex,
										endIndex + startIndex);
								String post = line.substring(endIndex
										+ startIndex);
								sb.append(pre + "<a href=\"" + link + "\">"
										+ link + "</a>" + post + '\n');
							}
						} else {
							sb.append(line + '\n');
							continue;
						}
					}
					label = new JLabel("<html><body>"
							+ sb.toString().replace("\r\n", "<br/>")
									.replace("\r", "<br/>")
									.replace("\n", "<br/>") + "</body></html>");
				}
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				diff = height - height2;
				labelPanel
						.setBounds(351 + 1, 126 - 7 + adj, width, height + 10);

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()
								&& !rightPanelPage.equals("ViewUser")) {
							NotepadViewController form = new NotepadViewController(
									MainForm.get(), "Enter mood message",
									fselectedUser.getMood(),
									new NotepadViewController.Runnable() {

										@Override
										public void run() {
											String res2 = this.text;
											if (res2 != null) {
												if (res2.trim().equals("")) {
													res2 = null;
												}
												fselectedUser.setMood(res2);
												Optional<UUID> authCode2 = registerUser(
														fselectedUser, password);
												if (authCode2.isPresent()) {
													refreshWindow(SCROLL_TO_BOTTOM);
												}
											}
										}
									});
							form.show();
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				label.addMouseListener(mouseAdapter);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(false);
				labelPanel
						.setBounds(260, 160 + adj + diff, panelWidth - 331, 1);
				labelPanel.setBorder(BorderFactory
						.createDashedBorder(new Color(147, 153, 157)));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				labelPanel.setOpaque(false);
				labelPanel.setBounds(20, 319 + diff, panelWidth - 91, 1);
				labelPanel.setBorder(BorderFactory
						.createDashedBorder(new Color(147, 153, 157)));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Mobile phone");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(272 + 1, 177 - 7 + adj + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label;
				if (fselectedUser.getMobilePhone() == null
						|| fselectedUser.getMobilePhone().equals("")) {
					label = new JLabel("Add number");
					label.setForeground(new Color(0, 149, 204));
				} else {
					label = new JLabel(fselectedUser.getMobilePhone());
				}
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(349 + 1, 177 - 7 + adj + diff, width,
						height + 10);

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()
								&& !rightPanelPage.equals("ViewUser")) {
							String res2 = (String) JOptionPane.showInputDialog(
									frame, "Enter new mobile phone for "
											+ fselectedUser.getSkypeName(),
									frame.getTitle(),
									JOptionPane.PLAIN_MESSAGE, null, null,
									fselectedUser.getMobilePhone());
							if (res2 != null) {
								if (res2.trim().equals("")) {
									res2 = null;
								}
								fselectedUser.setMobilePhone(res2);
								Optional<UUID> authCode2 = registerUser(
										fselectedUser, password);
								if (authCode2.isPresent()) {
									refreshWindow(SCROLL_TO_BOTTOM);
								}
							}
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				label.addMouseListener(mouseAdapter);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Contacts");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(panelWidth - 70 - 10 - width, 177 - 7
						+ adj + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Home phone");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(275 + 1, 211 - 7 + adj + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label;
				if (fselectedUser.getHomePhone() == null
						|| fselectedUser.getHomePhone().equals("")) {
					label = new JLabel("Add number");
					label.setForeground(new Color(0, 149, 204));
				} else {
					label = new JLabel(fselectedUser.getHomePhone());
				}
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(349 + 1, 211 - 7 + adj + diff, width,
						height + 10);

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()
								&& !rightPanelPage.equals("ViewUser")) {
							String res2 = (String) JOptionPane.showInputDialog(
									frame, "Enter new home phone for "
											+ fselectedUser.getSkypeName(),
									frame.getTitle(),
									JOptionPane.PLAIN_MESSAGE, null, null,
									fselectedUser.getHomePhone());
							if (res2 != null) {
								if (res2.trim().equals("")) {
									res2 = null;
								}
								fselectedUser.setHomePhone(res2);
								Optional<UUID> authCode2 = registerUser(
										fselectedUser, password);
								if (authCode2.isPresent()) {
									refreshWindow(SCROLL_TO_BOTTOM);
								}
							}
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				label.addMouseListener(mouseAdapter);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Contacts");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(panelWidth - 70 - 10 - width, 211 - 7
						+ adj + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Office phone");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(273 + 1, 245 - 7 + adj + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label;
				if (fselectedUser.getOfficePhone() == null
						|| fselectedUser.getOfficePhone().equals("")) {
					label = new JLabel("Add number");
					label.setForeground(new Color(0, 149, 204));
				} else {
					label = new JLabel(fselectedUser.getOfficePhone());
				}
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(349 + 1, 245 - 7 + adj + diff, width,
						height + 10);

				MouseAdapter mouseAdapter = new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						if (fselectedUser.getPubKey().isPresent()
								&& !rightPanelPage.equals("ViewUser")) {
							String res2 = (String) JOptionPane.showInputDialog(
									frame, "Enter new office phone for "
											+ fselectedUser.getSkypeName(),
									frame.getTitle(),
									JOptionPane.PLAIN_MESSAGE, null, null,
									fselectedUser.getOfficePhone());
							if (res2 != null) {
								if (res2.trim().equals("")) {
									res2 = null;
								}
								fselectedUser.setOfficePhone(res2);
								Optional<UUID> authCode2 = registerUser(
										fselectedUser, password);
								if (authCode2.isPresent()) {
									refreshWindow(SCROLL_TO_BOTTOM);
								}
							}
						} else {
							JOptionPane.showMessageDialog(frame, fselectedUser);
						}
					}

				};

				label.addMouseListener(mouseAdapter);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Contacts");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(panelWidth - 70 - 10 - width, 245 - 7
						+ adj + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/22109567.png");

				JLabel iconLabel = new JLabel(imageIcon);

				iconLabelPanel.setBounds(353, 292 + diff, 4, 6);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				MouseAdapter mouseAdapter = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						/*
						 * See connection details
						 */
						refreshWindow();
					}
				};

				iconLabel.addMouseListener(mouseAdapter);
				iconLabelPanel.addMouseListener(mouseAdapter);

				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				iconLabelPanel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Show full profile");
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(365 + 1, 290 - 7 + diff, width,
						height + 10);

				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						super.mousePressed(evt);
						JOptionPane.showMessageDialog(frame, fselectedUser);
					}
				});

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Change picture");
				// label.setFont(font);
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(19 + 1, 254 - 7, width, height + 10);

				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						if (rightPanelPage.equals("ViewUser")) {
							return;
						}
						JFileChooser fc = new JFileChooser();
						fc.setCurrentDirectory(new java.io.File("."));
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"*.png|*.jpg|*.jpeg|*.gif|*.bmp", "png", "jpg",
								"jpeg", "gif", "bmp");
						fc.setFileFilter(filter);
						int returnVal = fc.showSaveDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							String ext = fc.getSelectedFile().getName();
							if (ext.contains(".")) {
								ext = ext.substring(ext.lastIndexOf(".") + 1);
							}
							if (ext.equals("png") || ext.equals("jpeg")
									|| ext.equals("jpg") || ext.equals("gif")
									|| ext.equals("bmp")) {
								try {
									InputStream resourceUrl = new FileInputStream(
											fc.getSelectedFile());
									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									Streams.pipeAll(resourceUrl, baos);
									resourceUrl.close();
									ImageIcon imageIcon = new ImageIcon(baos
											.toByteArray());
									CropImageForm cropImageFrame = new CropImageForm(
											frame, imageIcon,
											new CropImageForm.Runnable() {

												@Override
												public void run() {
													try {
														ImageIcon subImage = this
																.getSubImage();
														BufferedImage bi = new BufferedImage(
																subImage.getIconWidth(),
																subImage.getIconHeight(),
																BufferedImage.TYPE_INT_RGB);
														Graphics g = bi
																.createGraphics();
														subImage.paintIcon(
																null, g, 0, 0);
														g.dispose();
														File file = File
																.createTempFile(
																		"image",
																		"jpg");
														if (file.exists()) {
															file.delete();
														}
														javax.imageio.ImageIO
																.write(bi,
																		"jpg",
																		file);
														ImgurUploader imgurUploader = new ImgurUploader();
														Optional<String> url = imgurUploader
																.uploadFile(file);
														if (!url.isPresent()) {
															return;
														}
														Optional<SocketHandlerContext> ctx = Skype
																.getPlugin()
																.createHandle();
														if (!ctx.isPresent()) {
															return;
														}
														fselectedUser
																.setImageIconUrl(url
																		.get());
														fselectedUser
																.setImageIcon(subImage);
														PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
																authCode,
																fselectedUser
																		.getUniqueId(),
																fselectedUser);
														ctx.get()
																.getOutboundHandler()
																.dispatch(
																		ctx.get(),
																		msg);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}, true);
									cropImageFrame.show();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} else {
							DialogForm form = new DialogForm(
									null,
									"Skype" + "\u2122"
											+ " - Remove profile picture?",
									"Remove profile picture?",
									"Are you sure you want to remove this profile picture?",
									"Remove", new Runnable() {

										@Override
										public void run() {
											Optional<SocketHandlerContext> ctx = Skype
													.getPlugin().createHandle();
											if (!ctx.isPresent()) {
												return;
											}
											fselectedUser.setImageIconUrl(null);
											fselectedUser.setImageIcon(null);
											PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
													authCode, fselectedUser
															.getUniqueId(),
													fselectedUser);
											ctx.get().getOutboundHandler()
													.dispatch(ctx.get(), msg);
										}
									});
							form.show();
						}
					}
				});

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Public");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(19 + 1, 273 - 7, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/16870375.png");

				JLabel iconLabel = new JLabel(imageIcon);

				iconLabelPanel.setBounds(51, 277, 7, 4);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				MouseAdapter mouseAdapter = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						/*
						 * See connection details
						 */
						refreshWindow();
					}
				};

				iconLabel.addMouseListener(mouseAdapter);
				iconLabelPanel.addMouseListener(mouseAdapter);

				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				iconLabelPanel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Subscriptions");
				Font font = label.getFont();
				label.setFont(font.deriveFont(Font.BOLD, 12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(371 + 1, 384 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Call a lot? Save a lot...");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(369 + 1, 407 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("\u25CF" + " Lowest calling rates");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(369 - 2 + 1, 428 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("\u25CF"
						+ " No long-term commitments");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(369 - 2 + 1, 449 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Get a subscription");
				// label.setFont(font);
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(369 + 1, 470 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/48990194.png");

				JLabel iconLabel = new JLabel(imageIcon);

				iconLabelPanel.setBounds(321, 381 + diff, 30, 32);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				MouseAdapter mouseAdapter = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						/*
						 * See connection details
						 */
						refreshWindow();
					}
				};

				iconLabel.addMouseListener(mouseAdapter);
				iconLabelPanel.addMouseListener(mouseAdapter);

				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				iconLabelPanel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Credit");
				Font font = label.getFont();
				label.setFont(font.deriveFont(Font.BOLD, 12.0f));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel
						.setBounds(70 + 1, 384 - 7 + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Call who you want, when you want");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel
						.setBounds(69 + 1, 407 - 7 + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("\u25CF"
						+ " Call mobiles and landlines worldwide");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(69 - 2 + 1, 428 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("\u25CF"
						+ " Send text messages and access WiFi hotspots");
				// label.setFont(font);
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel.setBounds(69 - 2 + 1, 449 - 7 + diff, width,
						height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel labelPanel = new JPanel();
				JLabel label = new JLabel("Add Skype Credit");
				// label.setFont(font);
				label.setForeground(new Color(0, 149, 204));
				labelPanel.setOpaque(false);
				labelPanel.add(label);
				int width = label.getPreferredSize().width;
				int height = label.getPreferredSize().height;
				labelPanel
						.setBounds(69 + 1, 470 - 7 + diff, width, height + 10);

				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 0
				 */
				layeredPane.add(labelPanel, new Integer(0), 0);
			}
			{
				JPanel iconLabelPanel = new JPanel();
				iconLabelPanel
						.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				ImageIcon imageIcon = ImageIO
						.getResourceAsImageIcon("/43252662.png");

				JLabel iconLabel = new JLabel(imageIcon);

				iconLabelPanel.setBounds(20, 381 + diff, 32, 32);
				iconLabelPanel.setOpaque(false);
				iconLabelPanel.add(iconLabel);

				MouseAdapter mouseAdapter = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						/*
						 * See connection details
						 */
						refreshWindow();
					}
				};

				iconLabel.addMouseListener(mouseAdapter);
				iconLabelPanel.addMouseListener(mouseAdapter);

				iconLabel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				iconLabelPanel.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));

				/**
				 * Panel added to pane with z-index 1
				 */
				layeredPane.add(iconLabelPanel, new Integer(1), 0);
			}
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel.add(layeredPane);
			return panel;
		}

		if (rightPanelPage.equals("OngoingCall")) {
			int leftSplitPaneWidth = leftSplitPane.getDividerLocation();
			if (leftSplitPaneWidth == 0 || leftSplitPaneWidth == -1) {
				leftSplitPaneWidth = this.defaultLeftPanelWidth;
			}
			panelWidth = getContentPane().getSize().width
					- this.splitPaneDividerSize - leftSplitPaneWidth;
			int panelHeight = this.getContentPane().getHeight();
			JLayeredPane layeredPane = new JLayeredPane();
			layeredPane.setBounds(0, 0, panelWidth, panelHeight);
			layeredPane
					.setPreferredSize(new Dimension(panelWidth, panelHeight));
			layeredPane.setOpaque(false);

			{
				JPanel panel2 = new JPanel();
				panel2.setBounds(0, 0, panelWidth, panelHeight);
				panel2.setPreferredSize(new Dimension(panelWidth, panelHeight));
				panel2.setLayout(new BorderLayout());
				panel2.setBackground(new Color(38, 38, 38));

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon;
					if (videoEnabled) {
						imageIcon = ImageIO
								.getResourceAsImageIcon("/628935161.png");
					} else {
						imageIcon = ImageIO
								.getResourceAsImageIcon("/652946707.png");
					}
					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(panelWidth / 2 - 133 + (46 / 2)
							- 3, panelHeight - 24 - 46, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							if (ongoingCallConversation instanceof VoIPContact) {
								return;
							}
							videoEnabled = !videoEnabled;
							if (videoEnabled) {
								if (ongoingVideoCall) {
									Thread thread = new Thread(
											() -> {
												byte[] cipher = ongoingVideoCallCipher;
												UUID callId = ongoingVideoCallId;
												Socket socket = videoCallOutgoingAudioSockets
														.get(1);
												ByteArrayOutputStream baos = new ByteArrayOutputStream();
												CipherOutputStream cos = new CipherOutputStream(
														baos, cipher);
												BufferedImage image = null;
												boolean err = false;
												if (MainForm.get().videoMode == MainForm
														.get().WEBCAM_CAPTURE_MODE) {
													try {
														if (System
																.getProperty(
																		"os.name")
																.startsWith(
																		"Windows")) {
															Class<?> clazz = Class
																	.forName("com.github.sarxos.webcam.Webcam");
															Method getDefault = clazz
																	.getMethod(
																			"getDefault",
																			null);
															Method open = clazz
																	.getMethod(
																			"open",
																			null);
															Method close = clazz
																	.getMethod(
																			"close",
																			null);
															Method getImage = clazz
																	.getMethod(
																			"getImage",
																			null);
															if (webcam == null) {
																Object o = getDefault
																		.invoke(clazz,
																				null);
																webcam = o;
															}
															boolean ret = (boolean) open
																	.invoke(webcam,
																			null);
															if (!ret) {
																err = true;
															} else {
																Object ret2 = getImage
																		.invoke(webcam,
																				null);
																if (ret2 == null) {
																	err = true;
																} else {
																	image = (BufferedImage) ret2;
																}
															}
														} else {
															err = true;
														}
													} catch (Exception e) {
														e.printStackTrace();
														err = true;
													}
												}
												if (err) {
													DialogForm form = new DialogForm(
															frame,
															"Skype"
																	+ "\u2122"
																	+ " - "
																	+ loggedInUser
																			.getSkypeName(),
															"Webcam sharing failed",
															"The webcam could not be opened, please check to make"
																	+ '\n'
																	+ "sure no other programs are using your webcam."
																	+ '\n'
																	+ '\n'
																	+ " "
																	+ '\n'
																	+ "Having issues? Please check to make sure your webcam driver"
																	+ '\n'
																	+ "is working by checking the device status in devmgmt.msc.",
															null, null);
													form.show();
													videoEnabled = false;
													try {
														Class<?> clazz = Class
																.forName("com.github.sarxos.webcam.Webcam");
														Method close = clazz
																.getMethod(
																		"close",
																		null);
														close.invoke(webcam,
																null);
													} catch (Exception e) {
														e.printStackTrace();
													}
													refreshWindow();
													return;
												}
												Rectangle screenRect = new Rectangle(
														Toolkit.getDefaultToolkit()
																.getScreenSize());
												if (MainForm.get().videoMode == MainForm
														.get().WEBCAM_CAPTURE_MODE) {
													screenRect = new Rectangle(
															0, 0,
															image.getWidth(),
															image.getHeight());
												}
												Optional<SocketHandlerContext> ctx2 = Skype
														.getPlugin()
														.createHandle();
												if (!ctx2.isPresent()) {
													return;
												}
												Optional<PacketPlayInReply> reply = ctx2
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx2.get(),
																new PacketPlayOutLogin(
																		authCode));
												if (!reply.isPresent()) {
													return;
												}
												if (reply.get().getStatusCode() != 200) {
													return;
												}
												UUID authCode = UUID
														.fromString(reply.get()
																.getText());
												reply = ctx2
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx2.get(),
																new PacketPlayOutVideoCallResolutionChanged(
																		authCode,
																		ongoingVideoCallId,
																		screenRect.width,
																		screenRect.height));
												if (!reply.isPresent()) {
													return;
												}
												if (reply.get().getStatusCode() != 200) {
													return;
												}
												try {
													if (socket != null) {
														JFrame mainForm = MainForm
																.get();
														DataOutputStream dos = new DataOutputStream(
																socket.getOutputStream());
														Robot robot = new Robot();

														Class<?> clazz = Class
																.forName("com.github.sarxos.webcam.Webcam");
														Method getImage = clazz
																.getMethod(
																		"getImage",
																		null);
														while (mainForm
																.isVisible()) {
															if (videoMode == WEBCAM_CAPTURE_MODE) {
																image = (BufferedImage) getImage
																		.invoke(webcam,
																				null);
																ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
																javax.imageio.ImageIO
																		.write(image,
																				"jpg",
																				baos2);
																byte[] b2 = baos2
																		.toByteArray();
																baos.reset();
																cos.write(b2);
																byte[] b = baos
																		.toByteArray();
																dos.writeInt(b.length);
																dos.flush();
																dos.write(b);
																dos.flush();
																image.flush();
																baos2.close();
																System.gc();
															} else if (videoMode == SCREEN_CAPTURE_MODE) {
																BufferedImage bi = robot
																		.createScreenCapture(screenRect);
																ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
																javax.imageio.ImageIO
																		.write(bi,
																				"jpg",
																				baos2);
																byte[] b2 = baos2
																		.toByteArray();
																bi.flush();
																baos.reset();
																cos.write(b2);
																byte[] b = baos
																		.toByteArray();
																dos.writeInt(b.length);
																dos.flush();
																dos.write(b);
																dos.flush();
																System.gc();
															}
														}
													}
												} catch (Exception e) {
													e.printStackTrace();
												}
												try {
													cos.close();
												} catch (Exception e1) {
													e1.printStackTrace();
												}
												try {
													Class<?> clazz = Class
															.forName("com.github.sarxos.webcam.Webcam");
													Method close = clazz
															.getMethod("close",
																	null);
													close.invoke(webcam, null);
												} catch (Exception e) {
													e.printStackTrace();
												}
												if (MainForm.get().ongoingVideoCallId != null)
													if (callId.equals(MainForm
															.get().ongoingVideoCallId)) {
														MainForm.get().ongoingVideoCall = false;
														MainForm.get().ongoingVideoCallId = null;
														MainForm.get().ongoingVideoCallCipher = null;
														MainForm.get()
																.refreshWindow(
																		MainForm.get().SCROLL_TO_BOTTOM);
														try {
															for (Socket socket2 : MainForm
																	.get().videoCallIncomingAudioSockets) {
																socket2.close();
															}
														} catch (Exception e) {
															e.printStackTrace();
														}
														try {
															for (Socket socket2 : MainForm
																	.get().videoCallOutgoingAudioSockets) {
																socket2.close();
															}
														} catch (Exception e) {
															e.printStackTrace();
														}
														MainForm.get().videoEnabled = false;
														MainForm.get().videoMode = MainForm
																.get().WEBCAM_CAPTURE_MODE;
														MainForm.ongoingVideoCallWidth = 0;
														MainForm.ongoingVideoCallHeight = 0;
														try {
															Class<?> clazz = Class
																	.forName("com.github.sarxos.webcam.Webcam");
															Method close = clazz
																	.getMethod(
																			"close",
																			null);
															close.invoke(
																	webcam,
																	null);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
											});
									thread.start();
									refreshWindow();
									return;
								}
								if (videoMode == WEBCAM_CAPTURE_MODE) {
									BufferedImage image = null;
									boolean err = false;
									try {
										if (System.getProperty("os.name")
												.startsWith("Windows")) {
											Class<?> clazz = Class
													.forName("com.github.sarxos.webcam.Webcam");
											Method getDefault = clazz
													.getMethod("getDefault",
															null);
											Method open = clazz.getMethod(
													"open", null);
											Method close = clazz.getMethod(
													"close", null);
											Method getImage = clazz.getMethod(
													"getImage", null);
											if (webcam == null) {
												Object o = getDefault.invoke(
														clazz, null);
												webcam = o;
											}
											boolean ret = (boolean) open
													.invoke(webcam, null);
											if (!ret) {
												err = true;
											} else if (getImage.invoke(webcam,
													null) == null) {
												err = true;
											}
										} else {
											err = true;
										}
									} catch (Exception e) {
										e.printStackTrace();
										err = true;
									}
									if (err) {
										DialogForm form = new DialogForm(
												MainForm.get(),
												"Skype"
														+ "\u2122"
														+ " - "
														+ loggedInUser
																.getSkypeName(),
												"Webcam sharing failed",
												"The webcam could not be opened, please check to make"
														+ '\n'
														+ "sure no other programs are using your webcam."
														+ '\n'
														+ '\n'
														+ " "
														+ '\n'
														+ "Having issues? Please check to make sure your webcam driver"
														+ '\n'
														+ "is working by checking the device status in devmgmt.msc.",
												null, null);
										form.show();
										videoEnabled = false;
										try {
											Class<?> clazz = Class
													.forName("com.github.sarxos.webcam.Webcam");
											Method close = clazz.getMethod(
													"close", null);
											close.invoke(webcam, null);
										} catch (Exception e) {
											e.printStackTrace();
										}
										return;
									}
									try {
										Class<?> clazz = Class
												.forName("com.github.sarxos.webcam.Webcam");
										Method close = clazz.getMethod("close",
												null);
										close.invoke(webcam, null);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (!ctx.isPresent()) {
									return;
								}
								byte[] cipher;
								try {
									cipher = CipherUtilities.randomCipher();
								} catch (InvalidKeyException
										| NoSuchPaddingException
										| NoSuchAlgorithmException
										| InvalidAlgorithmParameterException
										| UnsupportedEncodingException e) {
									e.printStackTrace();
									return;
								}
								String base64 = CipherUtilities
										.encodeBase64(cipher);
								String message = PGPUtilities.encryptAndSign(
										base64, selectedConversation);
								Optional<PacketPlayInReply> reply = ctx
										.get()
										.getOutboundHandler()
										.dispatch(
												ctx.get(),
												new PacketPlayOutSendVideoCallRequest(
														authCode,
														selectedConversation
																.getUniqueId(),
														message));
								if (!reply.isPresent()) {
									return;
								}
								if (reply.get().getStatusCode() != 200) {
									return;
								}
								AudioIO.CALL_INIT.playSound();
								rightPanelPage = "OngoingCall";
								ongoingVideoCall = true;
							} else {
								ongoingVideoCall = false;
								ongoingVideoCallId = null;
								ongoingVideoCallCipher = null;
								try {
									for (Socket socket : videoCallIncomingAudioSockets) {
										socket.close();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									for (Socket socket : videoCallOutgoingAudioSockets) {
										socket.close();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								MainForm.get().videoEnabled = false;
								MainForm.get().videoMode = MainForm.get().WEBCAM_CAPTURE_MODE;
								MainForm.ongoingVideoCallWidth = 0;
								MainForm.ongoingVideoCallHeight = 0;
								try {
									Class<?> clazz = Class
											.forName("com.github.sarxos.webcam.Webcam");
									Method close = clazz.getMethod("close",
											null);
									close.invoke(webcam, null);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							refreshWindow();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/567059254.png");
					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(
							panelWidth / 2 - 13 + (46 / 2) - 3,
							panelHeight - 24 - 46, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							CallPopupMenuForm form2 = new CallPopupMenuForm(
									frame, new CallPopupMenuForm.Runnable() {

										@Override
										public void run() {
											String input = getInput();
											if (input.equals("Show dial pad")) {
												DialPadMenuForm form3 = new DialPadMenuForm(
														frame,
														new DialPadMenuForm.Runnable() {

															@Override
															public void run() {
																VoIP.getPlugin()
																		.API_Dtmf(
																				-1,
																				this.getInput()
																						+ "");
															}
														});
												form3.setLocationRelativeTo(iconLabelPanel);
												int x = form3.getLocation().x;
												int y = form3.getLocation().y
														- (form3.getContentPane()
																.getHeight() / 2)
														- (iconLabelPanel
																.getHeight() / 2)
														- 7;
												form3.setLocation(x, y);
												form3.show();
											} else if (input
													.equals("Share screens...")
													|| input.equals("Stop sharing")) {
												if (ongoingCallConversation instanceof VoIPContact) {
													return;
												}
												if (videoEnabled) {
													if (videoMode == WEBCAM_CAPTURE_MODE) {
														videoMode = SCREEN_CAPTURE_MODE;
														Rectangle screenRect = new Rectangle(
																Toolkit.getDefaultToolkit()
																		.getScreenSize());
														Optional<SocketHandlerContext> ctx2 = Skype
																.getPlugin()
																.createHandle();
														if (!ctx2.isPresent()) {
															return;
														}
														Optional<PacketPlayInReply> reply = ctx2
																.get()
																.getOutboundHandler()
																.dispatch(
																		ctx2.get(),
																		new PacketPlayOutLogin(
																				authCode));
														if (!reply.isPresent()) {
															return;
														}
														if (reply
																.get()
																.getStatusCode() != 200) {
															return;
														}
														UUID authCode = UUID
																.fromString(reply
																		.get()
																		.getText());
														reply = ctx2
																.get()
																.getOutboundHandler()
																.dispatch(
																		ctx2.get(),
																		new PacketPlayOutVideoCallResolutionChanged(
																				authCode,
																				ongoingVideoCallId,
																				screenRect.width,
																				screenRect.height));
														if (!reply.isPresent()) {
															return;
														}
														if (reply
																.get()
																.getStatusCode() != 200) {
															return;
														}
														AudioIO.CALL_INIT
																.playSound();
														return;
													}
												}
												videoEnabled = !videoEnabled;
												if (videoEnabled) {
													if (ongoingVideoCall) {
														videoMode = SCREEN_CAPTURE_MODE;
														Thread thread = new Thread(
																() -> {
																	byte[] cipher = ongoingVideoCallCipher;
																	UUID callId = ongoingVideoCallId;
																	Socket socket = videoCallOutgoingAudioSockets
																			.get(1);
																	ByteArrayOutputStream baos = new ByteArrayOutputStream();
																	CipherOutputStream cos = new CipherOutputStream(
																			baos,
																			cipher);
																	Rectangle screenRect = new Rectangle(
																			Toolkit.getDefaultToolkit()
																					.getScreenSize());
																	Optional<SocketHandlerContext> ctx2 = Skype
																			.getPlugin()
																			.createHandle();
																	if (!ctx2
																			.isPresent()) {
																		return;
																	}
																	Optional<PacketPlayInReply> reply = ctx2
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx2.get(),
																					new PacketPlayOutLogin(
																							authCode));
																	if (!reply
																			.isPresent()) {
																		return;
																	}
																	if (reply
																			.get()
																			.getStatusCode() != 200) {
																		return;
																	}
																	UUID authCode = UUID
																			.fromString(reply
																					.get()
																					.getText());
																	reply = ctx2
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx2.get(),
																					new PacketPlayOutVideoCallResolutionChanged(
																							authCode,
																							ongoingVideoCallId,
																							screenRect.width,
																							screenRect.height));
																	if (!reply
																			.isPresent()) {
																		return;
																	}
																	if (reply
																			.get()
																			.getStatusCode() != 200) {
																		return;
																	}
																	try {
																		if (socket != null) {
																			JFrame mainForm = MainForm
																					.get();
																			DataOutputStream dos = new DataOutputStream(
																					socket.getOutputStream());
																			Robot robot = new Robot();
																			while (mainForm
																					.isVisible()) {
																				BufferedImage bi = robot
																						.createScreenCapture(screenRect);
																				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
																				javax.imageio.ImageIO
																						.write(bi,
																								"jpg",
																								baos2);
																				byte[] b2 = baos2
																						.toByteArray();
																				bi.flush();
																				baos.reset();
																				cos.write(b2);
																				byte[] b = baos
																						.toByteArray();
																				dos.writeInt(b.length);
																				dos.flush();
																				dos.write(b);
																				dos.flush();
																				System.gc();
																			}
																		}
																	} catch (Exception e) {
																		e.printStackTrace();
																	}
																	try {
																		cos.close();
																	} catch (Exception e1) {
																		e1.printStackTrace();
																	}
																	try {
																		Class<?> clazz = Class
																				.forName("com.github.sarxos.webcam.Webcam");
																		Method close = clazz
																				.getMethod(
																						"close",
																						null);
																		close.invoke(
																				webcam,
																				null);
																	} catch (Exception e) {
																		e.printStackTrace();
																	}
																	if (MainForm
																			.get().ongoingVideoCallId != null)
																		if (callId
																				.equals(MainForm
																						.get().ongoingVideoCallId)) {
																			MainForm.get().ongoingVideoCall = false;
																			MainForm.get().ongoingVideoCallId = null;
																			MainForm.get().ongoingVideoCallCipher = null;
																			MainForm.get()
																					.refreshWindow(
																							MainForm.get().SCROLL_TO_BOTTOM);
																			try {
																				for (Socket socket2 : MainForm
																						.get().videoCallIncomingAudioSockets) {
																					socket2.close();
																				}
																			} catch (Exception e) {
																				e.printStackTrace();
																			}
																			try {
																				for (Socket socket2 : MainForm
																						.get().videoCallOutgoingAudioSockets) {
																					socket2.close();
																				}
																			} catch (Exception e) {
																				e.printStackTrace();
																			}
																			MainForm.get().videoEnabled = false;
																			MainForm.get().videoMode = MainForm
																					.get().WEBCAM_CAPTURE_MODE;
																			MainForm.ongoingVideoCallWidth = 0;
																			MainForm.ongoingVideoCallHeight = 0;
																			try {
																				Class<?> clazz = Class
																						.forName("com.github.sarxos.webcam.Webcam");
																				Method close = clazz
																						.getMethod(
																								"close",
																								null);
																				close.invoke(
																						webcam,
																						null);
																			} catch (Exception e) {
																				e.printStackTrace();
																			}
																		}
																});
														thread.start();
														refreshWindow();
														return;
													}
													videoMode = SCREEN_CAPTURE_MODE;
													Optional<SocketHandlerContext> ctx = Skype
															.getPlugin()
															.createHandle();
													if (!ctx.isPresent()) {
														return;
													}
													byte[] cipher;
													try {
														cipher = CipherUtilities
																.randomCipher();
													} catch (
															InvalidKeyException
															| NoSuchPaddingException
															| NoSuchAlgorithmException
															| InvalidAlgorithmParameterException
															| UnsupportedEncodingException e) {
														e.printStackTrace();
														return;
													}
													String base64 = CipherUtilities
															.encodeBase64(cipher);
													String message = PGPUtilities
															.encryptAndSign(
																	base64,
																	selectedConversation);
													Optional<PacketPlayInReply> reply = ctx
															.get()
															.getOutboundHandler()
															.dispatch(
																	ctx.get(),
																	new PacketPlayOutSendVideoCallRequest(
																			authCode,
																			selectedConversation
																					.getUniqueId(),
																			message));
													if (!reply.isPresent()) {
														return;
													}
													if (reply.get()
															.getStatusCode() != 200) {
														return;
													}
													AudioIO.CALL_INIT
															.playSound();
													rightPanelPage = "OngoingCall";
													ongoingVideoCall = true;
												} else {
													ongoingVideoCall = false;
													ongoingVideoCallId = null;
													ongoingVideoCallCipher = null;
													try {
														for (Socket socket : videoCallIncomingAudioSockets) {
															socket.close();
														}
													} catch (Exception e) {
														e.printStackTrace();
													}
													try {
														for (Socket socket : videoCallOutgoingAudioSockets) {
															socket.close();
														}
													} catch (Exception e) {
														e.printStackTrace();
													}
													MainForm.get().videoEnabled = false;
													MainForm.get().videoMode = MainForm
															.get().WEBCAM_CAPTURE_MODE;
													MainForm.ongoingVideoCallWidth = 0;
													MainForm.ongoingVideoCallHeight = 0;
													try {
														Class<?> clazz = Class
																.forName("com.github.sarxos.webcam.Webcam");
														Method close = clazz
																.getMethod(
																		"close",
																		null);
														close.invoke(webcam,
																null);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
												refreshWindow();
											}
										}
									});
							form2.setLocationRelativeTo(iconLabelPanel);
							int x = form2.getLocation().x;
							int y = form2.getLocation().y
									- (form2.getContentPane().getHeight() / 2)
									- (iconLabelPanel.getHeight() / 2) - 7;
							form2.setLocation(x, y);
							form2.show();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/58164463.png");
					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(
							panelWidth / 2 + 47 + (46 / 2) - 3,
							panelHeight - 24 - 46, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							/*
							 * TODO: End call
							 */
							if (ongoingCallConversation instanceof VoIPContact) {
								VoIP.getPlugin().API_Hangup(
										VoIP.getPlugin().API_GetLine());
							}
							if (ongoingCallConversation.getUniqueId().equals(
									echoSoundTestService.getUniqueId())) {
								if (echo123 == null) {
								} else {
									echo123.stop();
								}
							}
							mic.stop();
							mic.drain();
							ongoingCall = false;
							ongoingCallConversation = null;
							MainForm.get().ongoingCallParticipants.clear();
							UUID callId = ongoingCallId;
							ongoingCallId = null;
							ongoingCallCipher = null;
							ongoingCallStartTime = 0L;
							rightPanelPage = "Conversation";
							refreshWindow(SCROLL_TO_BOTTOM);
							try {
								for (Socket socket : callIncomingAudioSockets) {
									socket.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								for (Socket socket : callOutgoingAudioSockets) {
									socket.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							ongoingVideoCall = false;
							ongoingVideoCallId = null;
							ongoingVideoCallCipher = null;
							try {
								for (Socket socket : videoCallIncomingAudioSockets) {
									socket.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								for (Socket socket : videoCallOutgoingAudioSockets) {
									socket.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							videoEnabled = false;
							microphoneEnabled = true;
							MainForm.get().videoMode = MainForm.get().WEBCAM_CAPTURE_MODE;
							MainForm.ongoingVideoCallWidth = 0;
							MainForm.ongoingVideoCallHeight = 0;
							try {
								Class<?> clazz = Class
										.forName("com.github.sarxos.webcam.Webcam");
								Method close = clazz.getMethod("close", null);
								close.invoke(MainForm.webcam, null);
							} catch (Exception e) {
								e.printStackTrace();
							}
							AudioIO.HANGUP.playSound();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon;
					if (microphoneEnabled) {
						imageIcon = ImageIO
								.getResourceAsImageIcon("/600306010.png");
					} else {
						imageIcon = ImageIO
								.getResourceAsImageIcon("/624317556.png");
					}
					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(
							panelWidth / 2 - 73 + (46 / 2) - 3,
							panelHeight - 24 - 46, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							microphoneEnabled = !microphoneEnabled;
							if (ongoingCallConversation instanceof VoIPContact) {
								ongoingCallObj.mute(!microphoneEnabled);
							} else {
								if (microphoneEnabled) {
									mic.start();
								} else {
									mic.stop();
									mic.drain();
								}
							}
							refreshWindow();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ongoingCallConversation
							.getImageIcon();

					if (ongoingVideoCall == false
							|| videoCallIncomingAudioSockets.size() == 0
							|| ongoingVideoCallWidth == 0
							|| ongoingVideoCallHeight == 0) {
						imageIcon = ImageIO.getScaledImageIcon(imageIcon,
								new Dimension(256, 256));
						ongoingCallProfilePictureImageLabel.setIcon(imageIcon);
						ongoingCallProfilePictureImageLabel.repaint();
						double width = 256;
						double height = 256;
						ongoingCallProfilePictureImageLabelWidth = (int) width;
						ongoingCallProfilePictureImageLabelHeight = (int) height;
						iconLabelPanel.setPreferredSize(new Dimension(
								(int) width, (int) height));
						iconLabelPanel.setSize((int) width, (int) height);
						iconLabelPanel
								.setBounds(panelWidth / 2 - ((int) width / 2),
										panelHeight / 2 - ((int) height / 2),
										(int) width, (int) height);
					} else {
						double width = panelWidth - 80;
						double height = (ongoingVideoCallHeight / ongoingVideoCallWidth)
								* width;
						ongoingCallProfilePictureImageLabelWidth = (int) width;
						ongoingCallProfilePictureImageLabelHeight = (int) height;
						iconLabelPanel.setPreferredSize(new Dimension(
								(int) width, (int) height));
						iconLabelPanel.setSize((int) width, (int) height);
						iconLabelPanel
								.setBounds(panelWidth / 2 - ((int) width / 2),
										panelHeight / 2 - ((int) height / 2),
										(int) width, (int) height);
					}

					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(ongoingCallProfilePictureImageLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							refreshWindow();
						}
					};

					ongoingCallProfilePictureImageLabel
							.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					ongoingCallProfilePictureImageLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/738834160.png");

					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(panelWidth - 58, 7, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							/*
							 * Open chat window
							 */
							rightPanelPage = "Conversation";
							refreshWindow(SCROLL_TO_BOTTOM);
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/822874571.png");

					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(panelWidth - 116, 7, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							/*
							 * See connection details
							 */
							refreshWindow();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/773927958.png");

					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(panelWidth - 174, 7, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							/*
							 * 
							 */
							refreshWindow();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel iconLabelPanel = new JPanel();
					iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
							0, 0));
					ImageIcon imageIcon = ImageIO
							.getResourceAsImageIcon("/714822614.png");

					JLabel iconLabel = new JLabel(imageIcon);

					iconLabelPanel.setBounds(12, 7, 46, 46);
					iconLabelPanel.setOpaque(false);
					iconLabelPanel.add(iconLabel);

					MouseAdapter mouseAdapter = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent evt) {
							/*
							 * 
							 */
							refreshWindow();
						}
					};

					iconLabel.addMouseListener(mouseAdapter);
					iconLabelPanel.addMouseListener(mouseAdapter);

					iconLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
					iconLabelPanel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1
					 */
					layeredPane.add(iconLabelPanel, new Integer(1), 0);
				}

				{
					JPanel labelPanel = new JPanel();
					String displayName = ongoingCallConversation
							.getDisplayName();
					if (ongoingCallConversation.isGroupChat()) {
						if (!ongoingCallParticipants.isEmpty()) {
							displayName = "";
							for (UUID participantId : ongoingCallParticipants) {
								Optional<Conversation> userLookup = lookupUser(participantId);
								if (userLookup.isPresent()) {
									displayName += userLookup.get()
											.getDisplayName() + ", ";
								} else {
									displayName += participantId.toString()
											+ ", ";
								}
							}
							if (displayName.length() > 0) {
								displayName = displayName.substring(0,
										displayName.length() - 2);
							}
						}
					}
					JLabel label = new JLabel(
							ongoingCallConversation == null ? "Echo / Sound Test Service ."
									: displayName);
					label.setFont(FontIO.SEGOE_UI.deriveFont(15.0f));
					label.setForeground(Color.white);
					labelPanel.setOpaque(false);
					labelPanel.add(label);
					int width = label.getPreferredSize().width;
					int height = label.getPreferredSize().height;
					labelPanel.setBounds(70, 4, width, height + 10);

					label.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1, above background
					 */
					layeredPane.add(labelPanel, new Integer(1), 0);
				}

				{
					JPanel labelPanel = new JPanel();
					SimpleDateFormat df = new SimpleDateFormat("mm:ss");
					ongoingCallTimeLabel = new JLabel(df.format(new Date(System
							.currentTimeMillis() - ongoingCallStartTime)));
					ongoingCallTimeLabel.setFont(FontIO.SEGOE_UI);
					ongoingCallTimeLabel.setForeground(Color.white);
					labelPanel.setOpaque(false);
					labelPanel.add(ongoingCallTimeLabel);
					int width = ongoingCallTimeLabel.getPreferredSize().width;
					int height = ongoingCallTimeLabel.getPreferredSize().height;
					labelPanel.setBounds(70, 29, width, height + 10);

					ongoingCallTimeLabel.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));

					/**
					 * Panel added to pane with z-index 1, above background
					 */
					layeredPane.add(labelPanel, new Integer(1), 0);
				}

				layeredPane.add(panel2, new Integer(0), 0);
			}

			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			panel.add(layeredPane);

		}

		if (rightPanelPage.equals("Conversation")) {
			panel.setLayout(new JVerticalLayout(0));
			panel.setOpaque(false);

			final int spacerHeight = 12;

			if (selectedConversation == null) {
				return panel;
			}

			if (!(selectedConversation instanceof Contact)
					&& !selectedConversation.isGroupChat()
					&& !(selectedConversation instanceof VoIPContact && ((VoIPContact) selectedConversation)
							.isContact())) {
				/**
				 * System level message
				 */
				panel.add(Box.createRigidArea(new Dimension(10,
						spacerHeight + 22)));

				if (selectedConversation.hasOutgoingFriendRequest()) {

					JLabel label = new JLabel("Contact request sent");

					label.setFont(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
							11));

					panel.add(label);

					panel.add(Box.createRigidArea(new Dimension(10, 14)));

					JPanel borderLinePanel = new JPanel();
					borderLinePanel.setLayout(new FlowLayout(FlowLayout.LEFT,
							0, 0));
					borderLinePanel.setOpaque(false);

					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setBounds(0, 0, panelWidth, 1);
					layeredPane.setPreferredSize(new Dimension(panelWidth, 1));
					layeredPane.setBackground(new Color(211, 230, 234));
					layeredPane.setOpaque(true);

					borderLinePanel.add(layeredPane);

					panel.add(borderLinePanel);

				} else if (selectedConversation.hasIncomingFriendRequest()) {

					JPanel labelPanel = new JPanel();
					labelPanel.setLayout(new BoxLayout(labelPanel,
							BoxLayout.X_AXIS));
					labelPanel.setOpaque(false);

					{

						JLabel label = new JLabel(
								selectedConversation.getDisplayName()
										+ " is not in your Contacts - ");

						label.setFont(FontIO.TAHOMA.deriveFont(
								Font.TRUETYPE_FONT, 11));

						labelPanel.add(label);

					}

					{

						JLabel label = new JLabel("Add to Contacts");
						label.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));

						label.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent evt) {
								super.mousePressed(evt);
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									Message incomingFriendRequest = selectedConversation
											.getIncomingFriendRequestMessage()
											.get();
									Optional<PacketPlayInReply> replyPacket = ctx
											.get()
											.getOutboundHandler()
											.dispatch(
													ctx.get(),
													new PacketPlayOutAcceptContactRequest(
															authCode,
															selectedConversation
																	.getUniqueId()));
									if (replyPacket.isPresent()) {
										if (replyPacket.get().getStatusCode() == 200) {
											long timestamp = System
													.currentTimeMillis();
											UUID messageId = UUID.randomUUID();
											Message message = new Message(
													messageId,
													loggedInUser.getUniqueId(),
													MessageType.ACCEPT_FRIEND_REQUEST,
													"", timestamp,
													selectedConversation);
											replyPacket = ctx
													.get()
													.getOutboundHandler()
													.dispatch(
															ctx.get(),
															new PacketPlayOutSendMessage(
																	authCode,
																	selectedConversation
																			.getUniqueId(),
																	messageId,
																	message,
																	timestamp));
											if (replyPacket.isPresent()) {
												if (replyPacket.get()
														.getStatusCode() == 200) {
													if (!conversations
															.contains(selectedConversation)) {
														conversations
																.add(selectedConversation);
													}
													selectedConversation
															.getMessages().add(
																	message);
													selectedConversation
															.setLastModified(new Date(
																	timestamp));
													selectedConversation
															.setHasIncomingFriendRequest(
																	false, null);
													refreshWindow();
													AudioIO.IM_SENT.playSound();
												}
											}
										}
									}
								}
							}
						});

						label.setFont(FontIO.TAHOMA.deriveFont(
								Font.TRUETYPE_FONT, 11));
						label.setForeground(new Color(0, 149, 204));

						labelPanel.add(label);

					}

					panel.add(labelPanel);

					panel.add(Box.createRigidArea(new Dimension(10, 14)));

					JPanel borderLinePanel = new JPanel();
					borderLinePanel.setLayout(new FlowLayout(FlowLayout.LEFT,
							0, 0));
					borderLinePanel.setOpaque(false);

					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setBounds(0, 0, panelWidth, 1);
					layeredPane.setPreferredSize(new Dimension(panelWidth, 1));
					layeredPane.setBackground(new Color(211, 230, 234));
					layeredPane.setOpaque(true);

					borderLinePanel.add(layeredPane);

					panel.add(borderLinePanel);

				} else {

					JLabel label = new JLabel(
							selectedConversation.getDisplayName()
									+ " is not in your Contacts");

					label.setFont(FontIO.TAHOMA.deriveFont(Font.TRUETYPE_FONT,
							11));

					panel.add(label);

					panel.add(Box.createRigidArea(new Dimension(10,
							spacerHeight - 4)));

					CopyOfJButtonRounded addToContactsButton = new CopyOfJButtonRounded(
							new Dimension(170, 29), "Add to Contacts", 35);

					addToContactsButton.setBackground(new Color(35, 170, 226));

					addToContactsButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							AddToContactsForm form = new AddToContactsForm(
									frame, selectedConversation,
									new AddToContactsForm.Runnable() {

										@Override
										public void run() {
											if (selectedConversation instanceof VoIPContact) {
												((VoIPContact) selectedConversation)
														.setContact(true);
												saveVoipContactList();
												refreshWindow();
												return;
											}
											String input = this.getInput();
											Optional<SocketHandlerContext> ctx = Skype
													.getPlugin().createHandle();
											if (ctx.isPresent()) {
												Optional<PacketPlayInReply> replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutSendContactRequest(
																		authCode,
																		selectedConversation
																				.getUniqueId()));
												if (replyPacket.isPresent()) {
													System.out
															.println(replyPacket);
													if (replyPacket.get()
															.getStatusCode() == 200) {
														long timestamp = System
																.currentTimeMillis();
														UUID messageId = UUID
																.randomUUID();
														Message message = new Message(
																messageId,
																loggedInUser
																		.getUniqueId(),
																MessageType.SEND_FRIEND_REQUEST,
																input,
																timestamp,
																selectedConversation);
														replyPacket = ctx
																.get()
																.getOutboundHandler()
																.dispatch(
																		ctx.get(),
																		new PacketPlayOutSendMessage(
																				authCode,
																				selectedConversation
																						.getUniqueId(),
																				messageId,
																				message,
																				timestamp));
														if (replyPacket
																.isPresent()) {
															System.out
																	.println(replyPacket);
															if (replyPacket
																	.get()
																	.getStatusCode() == 200) {
																if (!conversations
																		.contains(selectedConversation)) {
																	conversations
																			.add(selectedConversation);
																}
																selectedConversation
																		.getMessages()
																		.add(message);
																selectedConversation
																		.setLastModified(new Date(
																				timestamp));
																selectedConversation
																		.setHasOutgoingFriendRequest(true);
																refreshWindow();
																AudioIO.IM_SENT
																		.playSound();
															}
														}
													}
												}
											}
										}
									});
							form.setLocationRelativeTo(addToContactsButton);
							int x = form.getLocation().x;
							int y = form.getLocation().y
									+ (form.getContentPane().getHeight() / 2)
									+ (addToContactsButton.getHeight() / 2) + 7;
							form.setLocation(x, y);
							form.show();
						}

					});

					panel.add(addToContactsButton);

					panel.add(Box.createRigidArea(new Dimension(10,
							spacerHeight)));

					JPanel borderLinePanel = new JPanel();
					borderLinePanel.setLayout(new FlowLayout(FlowLayout.LEFT,
							0, 0));
					borderLinePanel.setOpaque(false);

					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setBounds(0, 0, panelWidth, 1);
					layeredPane.setPreferredSize(new Dimension(panelWidth, 1));
					layeredPane.setBackground(new Color(211, 230, 234));
					layeredPane.setOpaque(true);

					borderLinePanel.add(layeredPane);

					panel.add(borderLinePanel);

				}

			}

			UUID lastSender = null;
			int lastDayOfYear = -1;

			List<Message> recentMessages = new ArrayList<>();

			int a = selectedConversation.getMessages().size() > messagesToBeDisplayed ? selectedConversation
					.getMessages().size() - messagesToBeDisplayed - 1
					: 0;

			for (int i = selectedConversation.getMessages().size() - 1; i >= a; i--) {
				recentMessages.add(selectedConversation.getMessages().get(i));
			}

			if (flag != RETAIN_DISPLAY_MESSAGE_COUNT) {
				messagesToBeDisplayed = 15;
			}

			Collections.sort(recentMessages);

			if (recentMessages.size() != selectedConversation.getMessages()
					.size()) {
				panel.add(Box.createRigidArea(new Dimension(10, spacerHeight)));
				JLabel label = new JLabel("Show earlier messages");
				label.setFont(font);
				label.setForeground(new Color(0, 149, 204));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent evt) {
						messagesToBeDisplayed += 15;
						refreshWindow(RETAIN_DISPLAY_MESSAGE_COUNT);
					}
				});
				panel.add(label);
			}

			if (selectedConversation instanceof Contact
					|| (selectedConversation instanceof VoIPContact && ((VoIPContact) selectedConversation)
							.isContact())) {

			} else if (!selectedConversation.isGroupChat()) {
				for (Message message : recentMessages) {
					if (message.getMessageType() != null) {
						if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
							if (!message.getSender().equals(
									loggedInUser.getUniqueId())) {
								selectedConversation
										.setHasIncomingFriendRequest(true,
												message);
							} else {
								selectedConversation
										.setHasOutgoingFriendRequest(true);
							}
						}
						if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST
								|| message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
							if (message.getSender().equals(
									loggedInUser.getUniqueId())) {
								selectedConversation
										.setHasIncomingFriendRequest(false,
												null);
							} else {
								selectedConversation
										.setHasOutgoingFriendRequest(false);
							}
						}
					}
				}
			}

			for (Message message : recentMessages) {

				Calendar cal = Calendar.getInstance();
				Date timestampDate = new Date(message.getTimestamp());
				cal.setTime(timestampDate);
				int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
				if (lastDayOfYear != dayOfYear) {
					lastDayOfYear = dayOfYear;
					panel.add(Box.createRigidArea(new Dimension(10,
							spacerHeight)));
					JLabel label = new JLabel(
							this.getDateLabelInReferenceToToday(timestampDate));
					label.setFont(font);
					label.setForeground(new Color(0, 149, 204));
					panel.add(label);
					panel.add(Box.createRigidArea(new Dimension(10,
							spacerHeight)));
					if (!selectedConversation.isGroupChat()) {
						lastSender = message.getSender();
					}
				}

				if (message.getSender() == null) {
					/**
					 * System level message
					 */
					panel.add(Box.createRigidArea(new Dimension(10,
							spacerHeight)));

					JPanel messagePanel = new JPanel();
					messagePanel
							.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
					messagePanel.setOpaque(false);

					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setOpaque(false);

					{
						JPanel iconLabelPanel = new JPanel();
						iconLabelPanel.setLayout(new FlowLayout(
								FlowLayout.CENTER, 0, 0));
						ImageIcon imageIcon = message.getImageIcon();
						if (imageIcon == null) {
							/**
							 * No system level message icon
							 * 
							 * We will skip to where we display the message
							 */

						} else {
							JLabel iconLabel = new JLabel(imageIcon);

							iconLabelPanel.setBounds(40, 3, 30, 30);
							iconLabelPanel.setOpaque(false);
							iconLabelPanel.add(iconLabel);

							/**
							 * Panel added to pane with z-index 0
							 */
							layeredPane.add(iconLabelPanel, new Integer(0), 0);

						}
					}

					{
						JPanel labelPanel = new JPanel();
						labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0,
								0));
						labelPanel.setOpaque(false);

						final String html = "<html><body style='width: "
								+ (panelWidth - 351) + "px'>%1s</body></html>";

						JLabel label = new JLabel(String.format(
								html,
								message.getDecryptedMessage()
										.replace("<", "&lt;")
										.replace(">", "&gt;")
										.replaceAll("\\r?\\n", "<br/>")));

						JPopupMenu popUp = new JPopupMenu();
						{
							JMenuItem menuItem = new JMenuItem("Copy Selection");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
						{
							JMenuItem menuItem = new JMenuItem("Copy Message");
							menuItem.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									StringSelection selection = new StringSelection(
											message.getDecryptedMessage());
									Clipboard clipboard = Toolkit
											.getDefaultToolkit()
											.getSystemClipboard();
									clipboard.setContents(selection, selection);
								}

							});
							popUp.add(menuItem);
						}
						{
							JMenuItem menuItem = new JMenuItem("Select All");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
						{
							popUp.add(new JSeparator());
						}
						{
							JMenuItem menuItem = new JMenuItem("Edit Message");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
						{
							JMenuItem menuItem = new JMenuItem("Remove Message");
							if (message.isDeleted()) {
								menuItem.setEnabled(false);
							} else {
								menuItem.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										DialogForm form = new DialogForm(
												null,
												"Skype" + "\u2122"
														+ " - Remove message?",
												"Remove message?",
												"Are you sure you want to remove this message?",
												"Remove", new Runnable() {

													@Override
													public void run() {
														Optional<SocketHandlerContext> ctx = Skype
																.getPlugin()
																.createHandle();
														if (ctx.isPresent()) {
															Optional<PacketPlayInReply> reply = ctx
																	.get()
																	.getOutboundHandler()
																	.dispatch(
																			ctx.get(),
																			new PacketPlayOutLogin(
																					authCode));
															if (!reply
																	.isPresent()
																	|| reply.get()
																			.getStatusCode() != 200) {
																return;
															}
															UUID authCode = UUID
																	.fromString(reply
																			.get()
																			.getText());
															PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																	authCode,
																	selectedConversation
																			.getUniqueId(),
																	message.getUniqueId(),
																	message.getTimestamp());
															Optional<PacketPlayInReply> replyPacket2 = ctx
																	.get()
																	.getOutboundHandler()
																	.dispatch(
																			ctx.get(),
																			removeMessage);
															if (!replyPacket2
																	.isPresent()) {
																return;
															}
															if (replyPacket2
																	.get()
																	.getStatusCode() != 200) {
																return;
															}
														}
													}

												});
										form.show();
									}
								});
							}
							popUp.add(menuItem);
						}
						{
							JMenuItem menuItem = new JMenuItem(
									"Move Bookmark Here");
							menuItem.setEnabled(false);
							popUp.add(menuItem);
						}
						label.setComponentPopupMenu(popUp);

						MouseAdapter mouseAdapter = new MouseAdapter() {

							@Override
							public void mousePressed(MouseEvent evt) {
								super.mousePressed(evt);
								if (evt.getButton() != MouseEvent.BUTTON1) {
									return;
								}
								String dialogMessage = message.getMessage();
								if (!message.isSignatureVerified()) {
									dialogMessage = "The data could not be verified"
											+ '\r'
											+ '\n'
											+ "The signature does not match the one that is expected"
											+ '\r'
											+ '\n'
											+ '\r'
											+ '\n'
											+ dialogMessage;
								}
								JOptionPane.showMessageDialog(frame,
										dialogMessage);
							}

						};

						if (!message.getDecryptedMessage().equals(
								message.getMessage())) {
							label.addMouseListener(mouseAdapter);
							label.setCursor(Cursor
									.getPredefinedCursor(Cursor.HAND_CURSOR));
						}

						if (message.isDecryptionSuccessful()
								&& !message.isSignatureVerified()) {
							// label.setForeground(Color.red);
						}

						label.setFont(font);

						if (message.isDeleted()) {
							label.setFont(label.getFont().deriveFont(
									Font.ITALIC));
						}

						int width = label.getPreferredSize().width;
						int height = label.getPreferredSize().height;

						labelPanel.add(label);

						if (width > panelWidth - 236) {
							width = panelWidth - 236;
						}

						labelPanel.setBounds(107, 8, width, height + 10);

						layeredPane
								.setBounds(0, 0, panelWidth, 8 + height + 10);
						layeredPane.setPreferredSize(new Dimension(panelWidth,
								8 + height + 10));

						/**
						 * Panel added to pane with z-index 1
						 */
						layeredPane.add(labelPanel, new Integer(1), 0);
					}

					{
						JPanel labelPanel = new JPanel();
						labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0,
								0));
						labelPanel.setOpaque(false);

						Date date = new Date(message.getTimestamp());

						String timestamp = twentyFourHourTime.format(date);

						JLabel label = new JLabel(timestamp);
						label.setFont(font);

						int width = label.getPreferredSize().width;
						int height = label.getPreferredSize().height;

						labelPanel.add(label);

						labelPanel.setBounds(panelWidth - 56, 8, width,
								height + 10);

						/**
						 * Panel added to pane with z-index 1
						 */
						layeredPane.add(labelPanel, new Integer(1), 0);
					}

					messagePanel.add(layeredPane);

					panel.add(messagePanel);

					lastSender = null;
				} else {
					/**
					 * User level message
					 */

					if (message.getMessageType() != null) {

						/**
						 * Group chat
						 */
						if (message.getMessageType() == MessageType.GROUP_CHAT_CREATED) {
							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							{
								String senderName = selectedConversation
										.getDisplayName();

								{
									Optional<Conversation> userLookup = lookupUser(message.sender);
									if (userLookup.isPresent()) {
										senderName = userLookup.get()
												.getDisplayName();
									}
								}

								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								{

									JLabel label = new JLabel(senderName);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											" has created a group chat");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(".");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10, 1)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setOpaque(false);

								Date date = new Date(message.getTimestamp());

								String timestamp = twentyFourHourTime
										.format(date);

								JLabel label = new JLabel(timestamp);
								label.setFont(font);

								labelPanel.add(label);

								/**
								 * Panel added to pane with z-index 1
								 */
								panel.add(labelPanel);
							}

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							continue;

						}

						if (message.getMessageType() == MessageType.GROUP_MEMBER_ADDED) {
							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							{
								String senderName = selectedConversation
										.getDisplayName();

								{
									Optional<Conversation> userLookup = lookupUser(message.sender);
									if (userLookup.isPresent()) {
										senderName = userLookup.get()
												.getDisplayName();
									}
								}

								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								{

									JLabel label = new JLabel(senderName);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(" has added ");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											message.getMessage());
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											" to the group chat.");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10, 1)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setOpaque(false);

								Date date = new Date(message.getTimestamp());

								String timestamp = twentyFourHourTime
										.format(date);

								JLabel label = new JLabel(timestamp);
								label.setFont(font);

								labelPanel.add(label);

								/**
								 * Panel added to pane with z-index 1
								 */
								panel.add(labelPanel);
							}

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							continue;

						}

						if (message.getMessageType() == MessageType.GROUP_MEMBER_REMOVED) {
							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							{
								String senderName = selectedConversation
										.getDisplayName();

								{
									Optional<Conversation> userLookup = lookupUser(message.sender);
									if (userLookup.isPresent()) {
										senderName = userLookup.get()
												.getDisplayName();
									}
								}

								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								{

									JLabel label = new JLabel(senderName);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(" has removed ");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											message.getMessage());
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											" from the group chat.");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10, 1)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setOpaque(false);

								Date date = new Date(message.getTimestamp());

								String timestamp = twentyFourHourTime
										.format(date);

								JLabel label = new JLabel(timestamp);
								label.setFont(font);

								labelPanel.add(label);

								/**
								 * Panel added to pane with z-index 1
								 */
								panel.add(labelPanel);
							}

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							continue;

						}

						/**
						 * Friend request
						 */

						if (message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							{
								String first = loggedInUser.getDisplayName();
								String second = selectedConversation
										.getDisplayName();
								if (message.getSender().equals(
										selectedConversation.getUniqueId())) {
									first = selectedConversation
											.getDisplayName();
									second = loggedInUser.getDisplayName();
								}

								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								{

									JLabel label = new JLabel(first);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(
											" has shared contact details with ");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(second);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);

								}

								{

									JLabel label = new JLabel(".");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));
									labelPanel.add(label);

								}

								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10, 1)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setOpaque(false);

								Date date = new Date(message.getTimestamp());

								String timestamp = twentyFourHourTime
										.format(date);

								JLabel label = new JLabel(timestamp);
								label.setFont(font);

								labelPanel.add(label);

								/**
								 * Panel added to pane with z-index 1
								 */
								panel.add(labelPanel);
							}

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							continue;

						}

						if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								{
									String name = loggedInUser.getDisplayName();
									if (message.getSender().equals(
											selectedConversation.getUniqueId())) {
										name = selectedConversation
												.getDisplayName();
									}
									JLabel label = new JLabel(name);
									label.setFont(font
											.deriveFont(Font.BOLD, 12));
									labelPanel.add(label);
								}
								{
									JLabel label = new JLabel(
											" would like to add you on Skype");
									label.setFont(font.deriveFont(
											Font.TRUETYPE_FONT, 12));

									labelPanel.add(label);
								}
								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10,
									spacerHeight + 10)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setLayout(new BoxLayout(labelPanel,
										BoxLayout.X_AXIS));
								labelPanel.setOpaque(false);

								JLabel label = new JLabel(message
										.getDecryptedMessage()
										.replace("<", "&lt;")
										.replace(">", "&gt;")
										.replaceAll("\\r?\\n", "<br/>"));

								JPopupMenu popUp = new JPopupMenu();
								{
									JMenuItem menuItem = new JMenuItem(
											"Copy Selection");
									menuItem.setEnabled(false);
									popUp.add(menuItem);
								}
								{
									JMenuItem menuItem = new JMenuItem(
											"Copy Message");
									menuItem.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											StringSelection selection = new StringSelection(
													message.getDecryptedMessage());
											Clipboard clipboard = Toolkit
													.getDefaultToolkit()
													.getSystemClipboard();
											clipboard.setContents(selection,
													selection);
										}

									});
									popUp.add(menuItem);
								}
								{
									JMenuItem menuItem = new JMenuItem(
											"Select All");
									menuItem.setEnabled(false);
									popUp.add(menuItem);
								}
								{
									popUp.add(new JSeparator());
								}
								{
									JMenuItem menuItem = new JMenuItem(
											"Edit Message");
									menuItem.setEnabled(false);
									popUp.add(menuItem);
								}
								{
									JMenuItem menuItem = new JMenuItem(
											"Remove Message");
									if (message.isDeleted()) {
										menuItem.setEnabled(false);
									} else {
										menuItem.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(
													ActionEvent arg0) {
												DialogForm form = new DialogForm(
														null,
														"Skype"
																+ "\u2122"
																+ " - Remove message?",
														"Remove message?",
														"Are you sure you want to remove this message?",
														"Remove",
														new Runnable() {

															@Override
															public void run() {
																Optional<SocketHandlerContext> ctx = Skype
																		.getPlugin()
																		.createHandle();
																if (ctx.isPresent()) {
																	Optional<PacketPlayInReply> reply = ctx
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx.get(),
																					new PacketPlayOutLogin(
																							authCode));
																	if (!reply
																			.isPresent()
																			|| reply.get()
																					.getStatusCode() != 200) {
																		return;
																	}
																	UUID authCode = UUID
																			.fromString(reply
																					.get()
																					.getText());
																	PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																			authCode,
																			selectedConversation
																					.getUniqueId(),
																			message.getUniqueId(),
																			message.getTimestamp());
																	Optional<PacketPlayInReply> replyPacket2 = ctx
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx.get(),
																					removeMessage);
																	if (!replyPacket2
																			.isPresent()) {
																		return;
																	}
																	if (replyPacket2
																			.get()
																			.getStatusCode() != 200) {
																		return;
																	}
																}
															}

														});
												form.show();
											}
										});
									}
									popUp.add(menuItem);
								}
								{
									JMenuItem menuItem = new JMenuItem(
											"Move Bookmark Here");
									menuItem.setEnabled(false);
									popUp.add(menuItem);
								}
								label.setComponentPopupMenu(popUp);

								MouseAdapter mouseAdapter = new MouseAdapter() {

									@Override
									public void mousePressed(MouseEvent evt) {
										super.mousePressed(evt);
										if (evt.getButton() != MouseEvent.BUTTON1) {
											return;
										}
										String dialogMessage = message
												.getMessage();
										if (!message.isSignatureVerified()) {
											dialogMessage = "The data could not be verified"
													+ '\r'
													+ '\n'
													+ "The signature does not match the one that is expected"
													+ '\r'
													+ '\n'
													+ '\r'
													+ '\n'
													+ dialogMessage;
										}
										JOptionPane.showMessageDialog(frame,
												dialogMessage);
									}

								};

								if (!message.getDecryptedMessage().equals(
										message.getMessage())) {
									label.addMouseListener(mouseAdapter);
									label.setCursor(Cursor
											.getPredefinedCursor(Cursor.HAND_CURSOR));
								}

								if (message.isDecryptionSuccessful()
										&& !message.isSignatureVerified()) {
									// label.setForeground(Color.red);
								}

								label.setFont(font.deriveFont(
										Font.TRUETYPE_FONT, 12));
								if (message.isDeleted()) {
									label.setFont(label.getFont().deriveFont(
											Font.ITALIC));
								}
								labelPanel.add(label);

								panel.add(labelPanel);
							}

							panel.add(Box.createRigidArea(new Dimension(10, 1)));

							{
								JPanel labelPanel = new JPanel();
								labelPanel.setOpaque(false);

								Date date = new Date(message.getTimestamp());

								String timestamp = twentyFourHourTime
										.format(date);

								JLabel label = new JLabel(timestamp);
								label.setFont(font);

								labelPanel.add(label);

								/**
								 * Panel added to pane with z-index 1
								 */
								panel.add(labelPanel);
							}

							if (message.getSender().equals(
									loggedInUser.getUniqueId())
									|| message.isDeleted()) {
							} else if (!(selectedConversation instanceof Contact)) {
								panel.add(Box.createRigidArea(new Dimension(0,
										8)));

								JPanel buttonPanel = new JPanel();
								buttonPanel.setOpaque(false);
								buttonPanel.setLayout(new BoxLayout(
										buttonPanel, BoxLayout.X_AXIS));

								{
									CopyOfJButtonRounded button = new CopyOfJButtonRounded(
											new Dimension(113, 29), "Accept",
											35);

									button.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											Optional<SocketHandlerContext> ctx = Skype
													.getPlugin().createHandle();
											if (ctx.isPresent()) {
												Message incomingFriendRequest = selectedConversation
														.getIncomingFriendRequestMessage()
														.get();
												Optional<PacketPlayInReply> replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutAcceptContactRequest(
																		authCode,
																		selectedConversation
																				.getUniqueId()));
												if (replyPacket.isPresent()) {
													System.out
															.println(replyPacket);
													if (replyPacket.get()
															.getStatusCode() == 200) {
														long timestamp = System
																.currentTimeMillis();
														UUID messageId = UUID
																.randomUUID();
														Message message = new Message(
																messageId,
																loggedInUser
																		.getUniqueId(),
																MessageType.ACCEPT_FRIEND_REQUEST,
																"", timestamp,
																selectedConversation);
														replyPacket = ctx
																.get()
																.getOutboundHandler()
																.dispatch(
																		ctx.get(),
																		new PacketPlayOutSendMessage(
																				authCode,
																				selectedConversation
																						.getUniqueId(),
																				messageId,
																				message,
																				timestamp));
														if (replyPacket
																.isPresent()) {
															System.out
																	.println(replyPacket);
															if (replyPacket
																	.get()
																	.getStatusCode() == 200) {
																if (!conversations
																		.contains(selectedConversation)) {
																	conversations
																			.add(selectedConversation);
																}
																selectedConversation
																		.getMessages()
																		.add(message);
																selectedConversation
																		.setLastModified(new Date(
																				timestamp));
																selectedConversation
																		.setHasIncomingFriendRequest(
																				false,
																				null);
																refreshWindow();
																AudioIO.IM_SENT
																		.playSound();
															}
														}
													}
												}
											}
										}

									});

									button.setBackground(new Color(35, 170, 226));

									buttonPanel.add(button);
								}

								buttonPanel.add(Box
										.createRigidArea(new Dimension(15, 1)));

								{
									Dimension buttonSize = new Dimension(113,
											29);
									CopyOfJButtonRounded button = new CopyOfJButtonRounded(
											buttonSize, "Decline", 35);

									button.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											Optional<SocketHandlerContext> ctx = Skype
													.getPlugin().createHandle();
											if (ctx.isPresent()) {
												Message incomingFriendRequest = selectedConversation
														.getIncomingFriendRequestMessage()
														.get();
												Optional<PacketPlayInReply> replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutDeclineContactRequest(
																		authCode,
																		selectedConversation
																				.getUniqueId(),
																		incomingFriendRequest
																				.getUniqueId(),
																		incomingFriendRequest
																				.getTimestamp()));
												if (replyPacket.isPresent()) {
													System.out
															.println(replyPacket);
													if (replyPacket.get()
															.getStatusCode() == 200) {
														selectedConversation
																.setLastModified(new Date(
																		new Date()
																				.getTime()
																				+ AppDelegate.TIME_OFFSET));
														selectedConversation
																.setHasIncomingFriendRequest(
																		false,
																		null);
														refreshWindow();
														AudioIO.IM_SENT
																.playSound();
													}
												}
											}
										}

									});

									button.setBackground(new Color(102, 207,
											246));

									buttonPanel.add(button);
								}
								panel.add(buttonPanel);
							}

							panel.add(Box
									.createRigidArea(new Dimension(10, 14)));

						}

						continue;

					}

					JPanel messagePanel = new JPanel();
					messagePanel
							.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
					messagePanel.setOpaque(false);

					JLayeredPane displayNameLayeredPane = new JLayeredPane();
					displayNameLayeredPane.setOpaque(false);

					JLayeredPane layeredPane = new JLayeredPane();
					layeredPane.setOpaque(false);

					if (lastSender == null
							|| !lastSender.equals(message.getSender())) {
						if (!selectedConversation.isGroupChat()
								|| message.getSender().equals(
										loggedInUser.getUniqueId())) {
							panel.add(Box.createRigidArea(new Dimension(10,
									spacerHeight)));
						}
						lastSender = message.sender;
						if (!message.getSender().equals(
								loggedInUser.getUniqueId())
								&& selectedConversation.isGroupChat()) {
							Optional<Conversation> userLookup = lookupUser(message
									.getSender());
							if (userLookup.isPresent()) {
								if (message.sender.equals(loggedInUser
										.getUniqueId())) {

									{
										JPanel labelPanel = new JPanel();
										labelPanel.setLayout(new FlowLayout(
												FlowLayout.LEFT, 0, 0));
										labelPanel.setOpaque(false);

										JLabel label = new JLabel(userLookup
												.get().getDisplayName());

										label.setBorder(BorderFactory
												.createEmptyBorder());
										label.setFont(font);

										if (message.isDeleted()) {
											label.setFont(label.getFont()
													.deriveFont(Font.ITALIC));
										}

										int width = label.getPreferredSize().width;
										int height = label.getPreferredSize().height;

										labelPanel.add(label);

										if (width > panelWidth - 236) {
											width = panelWidth - 236;
										}

										labelPanel.setBounds(127, 13, width,
												height + 10);

										displayNameLayeredPane.setBounds(0, 0,
												panelWidth, 8 + height + 10);
										displayNameLayeredPane
												.setPreferredSize(new Dimension(
														panelWidth,
														8 + height + 10));

										/**
										 * Panel added to pane with z-index 1
										 */
										displayNameLayeredPane.add(labelPanel,
												new Integer(1), 0);
									}

								} else {

									{
										JPanel labelPanel = new JPanel();
										labelPanel.setLayout(new FlowLayout(
												FlowLayout.LEFT, 0, 0));
										labelPanel.setOpaque(false);

										JLabel label = new JLabel(userLookup
												.get().getDisplayName());

										label.setFont(font);

										if (message.isDeleted()) {
											label.setFont(label.getFont()
													.deriveFont(Font.ITALIC));
										}

										int width = label.getPreferredSize().width;
										int height = label.getPreferredSize().height;

										labelPanel.add(label);

										if (width > panelWidth - 236) {
											width = panelWidth - 236;
										}

										labelPanel.setBounds(91, 13, width,
												height + 10);

										displayNameLayeredPane.setBounds(0, 0,
												panelWidth, 8 + height + 10);
										displayNameLayeredPane
												.setPreferredSize(new Dimension(
														panelWidth,
														8 + height + 10));

										/**
										 * Panel added to pane with z-index 1
										 */
										displayNameLayeredPane.add(labelPanel,
												new Integer(1), 0);
									}
								}

								panel.add(displayNameLayeredPane);

								// TODO: END
							}
						}
						if (!message.sender.equals(loggedInUser.getUniqueId())) {
							{
								JPanel iconLabelPanel = new JPanel();
								iconLabelPanel.setLayout(new FlowLayout(
										FlowLayout.CENTER, 0, 0));
								ImageIcon imageIcon = message.getImageIcon();
								imageIcon = ImageIO.getScaledImageIcon(
										imageIcon, new Dimension(36, 36));
								imageIcon = ImageIO
										.getCircularImageIcon(imageIcon);
								JLabel iconLabel = new JLabel(imageIcon);

								/**
								 * Reserved for future use
								 */
								iconLabel
										.setCursor(Cursor
												.getPredefinedCursor(Cursor.HAND_CURSOR));

								iconLabelPanel.setBounds(37, 0, 36, 36);
								iconLabelPanel.setOpaque(false);
								iconLabelPanel.add(iconLabel);

								/**
								 * Panel added to pane with z-index 0
								 */
								layeredPane.add(iconLabelPanel, new Integer(0),
										0);
							}
						}
					}

					if (message.sender.equals(loggedInUser.getUniqueId())) {

						JPanel backgroundPanel = new JPanel();

						{
							backgroundPanel.setLayout(new FlowLayout(
									FlowLayout.CENTER, 0, 0));
							backgroundPanel.setBackground(new Color(229, 247,
									253));
							if (selectedMessage != null
									&& selectedMessage.getUniqueId() == message
											.getUniqueId()) {
								backgroundPanel.setBorder(BorderFactory
										.createMatteBorder(2, 2, 2, 2,
												new Color(0, 175, 240)));
							}
						}

						{
							JPanel labelPanel = new JPanel();
							labelPanel.setLayout(new FlowLayout(
									FlowLayout.LEFT, 0, 0));
							labelPanel.setOpaque(false);

							final String html = "<html><body style='width: "
									+ (panelWidth - 351)
									+ "px'>%1s</body></html>";

							JComponent label = new JLabel(String.format(
									html,
									message.getDecryptedMessage()
											.replace("<", "&lt;")
											.replace(">", "&gt;")
											.replaceAll("\\r?\\n", "<br/>")));

							boolean img = false;
							String imgurl = null;

							if (message.getDecryptedMessage().startsWith(
									"<img src=\"https://i.imgur.com/")) {
								if (message.getDecryptedMessage().endsWith(
										"\" />")) {
									if (message.getDecryptedMessage().length() == 44
											|| message.getDecryptedMessage()
													.length() == 45) {
										label = new JLabel(
												"<html><body>"
														+ message
																.getDecryptedMessage()
																.replace("src",
																		"width=\"320\" src")
														+ "</body></html>");
										img = true;
										imgurl = message.getDecryptedMessage()
												.substring(
														"<img src=\"".length());
										imgurl = imgurl.substring(0,
												imgurl.indexOf("\""));
									}
								}
							}

							if (message.getDecryptedMessage().startsWith(
									"<video src=\"https://litter.catbox.moe/")) {
								if (message.getDecryptedMessage().endsWith(
										"\" />")) {
									if (message.getDecryptedMessage().length() == 51
											|| message.getDecryptedMessage()
													.length() == 52
											|| message.getDecryptedMessage()
													.length() == 53) {
										img = true;
										imgurl = message.getDecryptedMessage()
												.substring(
														"<video src=\""
																.length());
										imgurl = imgurl.substring(0,
												imgurl.indexOf("\""));
										try {
											label = new JLabel(
													"<html><body><a href=\""
															+ imgurl
															+ "\">"
															+ imgurl
															+ "</a></body></html>");
										} catch (Exception e) {
											e.printStackTrace();
											label = new JLabel(
													"<html><body><a href=\""
															+ imgurl
															+ "\">"
															+ imgurl
															+ "</a></body></html>");
										}
									}
								}
							}

							JPopupMenu popUp = new JPopupMenu();
							{
								JMenuItem menuItem = new JMenuItem(
										"Copy Selection");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Copy Message");
								menuItem.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										StringSelection selection = new StringSelection(
												message.getDecryptedMessage());
										Clipboard clipboard = Toolkit
												.getDefaultToolkit()
												.getSystemClipboard();
										clipboard.setContents(selection,
												selection);
									}

								});
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem("Select All");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							{
								popUp.add(new JSeparator());
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Edit Message");
								if (message.isDeleted()) {
									menuItem.setEnabled(false);
								} else {
									menuItem.setEnabled(true);
									menuItem.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											selectedMessage = message;
											conversationTextField.setText(message
													.getDecryptedMessage());
											refreshWindow(RETAIN_SCROLL_POSITION);
										}

									});
								}
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Remove Message");
								if (message.isDeleted()) {
									menuItem.setEnabled(false);
								} else {
									menuItem.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											DialogForm form = new DialogForm(
													null,
													"Skype"
															+ "\u2122"
															+ " - Remove message?",
													"Remove message?",
													"Are you sure you want to remove this message?",
													"Remove", new Runnable() {

														@Override
														public void run() {
															Optional<SocketHandlerContext> ctx = Skype
																	.getPlugin()
																	.createHandle();
															if (ctx.isPresent()) {
																Optional<PacketPlayInReply> reply = ctx
																		.get()
																		.getOutboundHandler()
																		.dispatch(
																				ctx.get(),
																				new PacketPlayOutLogin(
																						authCode));
																if (!reply
																		.isPresent()
																		|| reply.get()
																				.getStatusCode() != 200) {
																	return;
																}
																UUID authCode = UUID
																		.fromString(reply
																				.get()
																				.getText());
																PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																		authCode,
																		selectedConversation
																				.getUniqueId(),
																		message.getUniqueId(),
																		message.getTimestamp());
																Optional<PacketPlayInReply> replyPacket2 = ctx
																		.get()
																		.getOutboundHandler()
																		.dispatch(
																				ctx.get(),
																				removeMessage);
																if (!replyPacket2
																		.isPresent()) {
																	return;
																}
																if (replyPacket2
																		.get()
																		.getStatusCode() != 200) {
																	return;
																}
															}
														}

													});
											form.show();
										}
									});
								}
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Move Bookmark Here");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							if (label instanceof JLabel) {
								((JComponent) label)
										.setComponentPopupMenu(popUp);
							}

							final boolean fimg = img;
							final String fimgurl = imgurl;

							MouseAdapter mouseAdapter = new MouseAdapter() {

								@Override
								public void mousePressed(MouseEvent evt) {
									super.mousePressed(evt);
									if (evt.getButton() != MouseEvent.BUTTON1) {
										return;
									}
									if (fimg) {
										Desktop desktop = Desktop
												.getDesktop();
										try {
											desktop.browse(URI
													.create(fimgurl));
										} catch (IOException e) {
											e.printStackTrace();
										}
										return;
									}
									String dialogMessage = message.getMessage();
									if (message.getPgpMessage() != null
											&& !message.getPgpMessage().equals(
													message.getMessage())) {
										dialogMessage = message.getPgpMessage();
									}
									if (!message.isSignatureVerified()) {
										dialogMessage = "The data could not be verified"
												+ '\r'
												+ '\n'
												+ "The signature does not match the one that is expected"
												+ '\r'
												+ '\n'
												+ '\r'
												+ '\n'
												+ dialogMessage;
									}
									JOptionPane.showMessageDialog(frame,
											dialogMessage);
								}

							};

							if (label instanceof JLabel) {
								label.addMouseListener(mouseAdapter);
							}
							label.setCursor(Cursor
									.getPredefinedCursor(Cursor.HAND_CURSOR));

							if (message.isDecryptionSuccessful()
									&& !message.isSignatureVerified()) {
								// label.setForeground(Color.red);
							}

							if (label instanceof JComponent) {
								((JComponent) label).setBorder(BorderFactory
										.createEmptyBorder());
							}
							label.setFont(font);

							if (message.isDeleted()) {
								label.setFont(label.getFont().deriveFont(
										Font.ITALIC));
							}

							int width = label.getPreferredSize().width;
							int height = label.getPreferredSize().height;

							labelPanel.add(label);

							if (width > panelWidth - 236) {
								width = panelWidth - 236;
							}

							labelPanel.setBounds(143, 8, width, height + 10);

							backgroundPanel.setBounds(127, 0, panelWidth - 204,
									8 + height + 10);

							layeredPane.setBounds(0, 0, panelWidth,
									8 + height + 10);
							layeredPane.setPreferredSize(new Dimension(
									panelWidth, 8 + height + 10));

							/**
							 * Panel added to pane with z-index 0
							 */
							layeredPane.add(backgroundPanel, new Integer(0), 0);

							/**
							 * Panel added to pane with z-index 1
							 */
							layeredPane.add(labelPanel, new Integer(1), 0);
						}

						{
							JPanel labelPanel = new JPanel();
							labelPanel.setLayout(new FlowLayout(
									FlowLayout.LEFT, 0, 0));
							labelPanel.setOpaque(false);

							Date date = new Date(message.getTimestamp());

							String timestamp = twentyFourHourTime.format(date);

							JLabel label = new JLabel(timestamp);
							label.setFont(font);

							int width = label.getPreferredSize().width;
							int height = label.getPreferredSize().height;

							labelPanel.add(label);

							labelPanel.setBounds(panelWidth - 56, 8, width,
									height + 10);

							/**
							 * Panel added to pane with z-index 1
							 */
							layeredPane.add(labelPanel, new Integer(1), 0);
						}

						{
							boolean wide = true;

							JPanel iconLabelPanel = new JPanel();
							iconLabelPanel.setLayout(new FlowLayout(
									FlowLayout.LEFT, 0, 0));
							ImageIcon imageIcon = ImageIO
									.getScaledImageIcon(
											ImageIO.getResourceAsImageIcon("/892788374.png"),
											new Dimension(wide ? 24 : 16, 16));
							if (message.isRead()) {
								imageIcon = ImageIO
										.getScaledImageIcon(
												ImageIO.getResourceAsImageIcon("/436720454.png"),
												new Dimension(wide ? 24 : 16,
														16));
							}
							JLabel iconLabel = new JLabel(imageIcon);

							/**
							 * Reserved for future use
							 */
							iconLabel.setCursor(Cursor
									.getPredefinedCursor(Cursor.HAND_CURSOR));

							iconLabelPanel.setOpaque(false);
							iconLabelPanel.add(iconLabel);

							iconLabelPanel.setBounds(panelWidth - 56 - 100 + 45
									+ (wide ? 0 : 8), 10, wide ? 24 : 16, 16);

							/**
							 * Panel added to pane with z-index 1
							 */
							layeredPane.add(iconLabelPanel, new Integer(1), 0);
						}

					} else {
						JPanel backgroundPanel = new JPanel();

						{
							backgroundPanel.setLayout(new FlowLayout(
									FlowLayout.CENTER, 0, 0));
							backgroundPanel.setBackground(new Color(199, 237,
									252));
							if (selectedMessage != null
									&& selectedMessage.getUniqueId() == message
											.getUniqueId()) {
								backgroundPanel.setBorder(BorderFactory
										.createMatteBorder(2, 2, 2, 2,
												new Color(0, 175, 240)));
							}
						}

						{
							JPanel labelPanel = new JPanel();
							labelPanel.setLayout(new FlowLayout(
									FlowLayout.LEFT, 0, 0));
							labelPanel.setOpaque(false);

							final String html = "<html><body style='width: "
									+ (panelWidth - 351)
									+ "px'>%1s</body></html>";

							JComponent label = new JLabel(String.format(
									html,
									message.getDecryptedMessage()
											.replace("<", "&lt;")
											.replace(">", "&gt;")
											.replaceAll("\\r?\\n", "<br/>")));

							boolean img = false;
							String imgurl = null;

							if (message.getDecryptedMessage().startsWith(
									"<img src=\"https://i.imgur.com/")) {
								if (message.getDecryptedMessage().endsWith(
										"\" />")) {
									if (message.getDecryptedMessage().length() == 44
											|| message.getDecryptedMessage()
													.length() == 45) {
										label = new JLabel(
												"<html><body>"
														+ message
																.getDecryptedMessage()
																.replace("src",
																		"width=\"320\" src")
														+ "</body></html>");
										img = true;
										imgurl = message.getDecryptedMessage()
												.substring(
														"<img src=\"".length());
										imgurl = imgurl.substring(0,
												imgurl.indexOf("\""));
									}
								}
							}

							if (message.getDecryptedMessage().startsWith(
									"<video src=\"https://litter.catbox.moe/")) {
								if (message.getDecryptedMessage().endsWith(
										"\" />")) {
									if (message.getDecryptedMessage().length() == 51
											|| message.getDecryptedMessage()
													.length() == 52
											|| message.getDecryptedMessage()
													.length() == 53) {
										img = true;
										imgurl = message.getDecryptedMessage()
												.substring(
														"<video src=\""
																.length());
										imgurl = imgurl.substring(0,
												imgurl.indexOf("\""));
										try {
											Class.forName("javafx.scene.media.MediaPlayer");
											Class<?> clazz = Class
													.forName("codes.wilma24.Skype.v1_0_R1.uicommon.JavaFxMediaPlayerPanel");
											Object obj = clazz.newInstance();
											Method m = clazz.getMethod(
													"prepareMedia",
													String.class);
											m.setAccessible(true);
											boolean success = (boolean) m
													.invoke(obj, imgurl);
											if (success) {
												JPanel javaFxMediaPlayer = (JPanel) obj;
												label = javaFxMediaPlayer;
												label.setPreferredSize(new Dimension(
														320, 265));
											} else {
												label = new JLabel(
														"<html><body><a href=\""
																+ imgurl
																+ "\">"
																+ imgurl
																+ "</a></body></html>");
											}
										} catch (Exception e) {
											e.printStackTrace();
											label = new JLabel(
													"<html><body><a href=\""
															+ imgurl
															+ "\">"
															+ imgurl
															+ "</a></body></html>");
										}
									}
								}
							}

							JPopupMenu popUp = new JPopupMenu();
							{
								JMenuItem menuItem = new JMenuItem(
										"Copy Selection");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Copy Message");
								menuItem.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										StringSelection selection = new StringSelection(
												message.getDecryptedMessage());
										Clipboard clipboard = Toolkit
												.getDefaultToolkit()
												.getSystemClipboard();
										clipboard.setContents(selection,
												selection);
									}

								});
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem("Select All");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							{
								popUp.add(new JSeparator());
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Edit Message");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Remove Message");
								if (selectedConversation.isGroupChat()
										&& selectedConversation
												.getGroupChatAdmins().contains(
														loggedInUser
																.getUniqueId())) {
									if (message.isDeleted()) {
										menuItem.setEnabled(false);
									} else {
										menuItem.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(
													ActionEvent arg0) {
												DialogForm form = new DialogForm(
														null,
														"Skype"
																+ "\u2122"
																+ " - Remove message?",
														"Remove message?",
														"Are you sure you want to remove this message?",
														"Remove",
														new Runnable() {

															@Override
															public void run() {
																Optional<SocketHandlerContext> ctx = Skype
																		.getPlugin()
																		.createHandle();
																if (ctx.isPresent()) {
																	Optional<PacketPlayInReply> reply = ctx
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx.get(),
																					new PacketPlayOutLogin(
																							authCode));
																	if (!reply
																			.isPresent()
																			|| reply.get()
																					.getStatusCode() != 200) {
																		return;
																	}
																	UUID authCode = UUID
																			.fromString(reply
																					.get()
																					.getText());
																	PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
																			authCode,
																			selectedConversation
																					.getUniqueId(),
																			message.getUniqueId(),
																			message.getTimestamp());
																	Optional<PacketPlayInReply> replyPacket2 = ctx
																			.get()
																			.getOutboundHandler()
																			.dispatch(
																					ctx.get(),
																					removeMessage);
																	if (!replyPacket2
																			.isPresent()) {
																		return;
																	}
																	if (replyPacket2
																			.get()
																			.getStatusCode() != 200) {
																		return;
																	}
																}
															}

														});
												form.show();
											}
										});
									}
								} else {
									menuItem.setEnabled(false);
								}
								popUp.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem(
										"Move Bookmark Here");
								menuItem.setEnabled(false);
								popUp.add(menuItem);
							}
							if (label instanceof JLabel) {
								((JComponent) label)
										.setComponentPopupMenu(popUp);
							}

							final boolean fimg = img;
							final String fimgurl = imgurl;

							MouseAdapter mouseAdapter = new MouseAdapter() {

								@Override
								public void mousePressed(MouseEvent evt) {
									super.mousePressed(evt);
									if (evt.getButton() != MouseEvent.BUTTON1) {
										return;
									}
									if (fimg) {
										Desktop desktop = Desktop
												.getDesktop();
										try {
											desktop.browse(URI
													.create(fimgurl));
										} catch (IOException e) {
											e.printStackTrace();
										}
										return;
									}
									String dialogMessage = message.getMessage();
									if (message.getPgpMessage() != null
											&& !message.getPgpMessage().equals(
													message.getMessage())) {
										dialogMessage = message.getPgpMessage();
									}
									if (!message.isSignatureVerified()) {
										dialogMessage = "The data could not be verified"
												+ '\r'
												+ '\n'
												+ "The signature does not match the one that is expected"
												+ '\r'
												+ '\n'
												+ '\r'
												+ '\n'
												+ dialogMessage;
									}
									JOptionPane.showMessageDialog(frame,
											dialogMessage);
								}

							};

							if (label instanceof JLabel) {
								label.addMouseListener(mouseAdapter);
							}
							label.setCursor(Cursor
									.getPredefinedCursor(Cursor.HAND_CURSOR));

							if (message.isDecryptionSuccessful()
									&& !message.isSignatureVerified()) {
								// label.setForeground(Color.red);
							}

							label.setFont(font);

							if (message.isDeleted()) {
								label.setFont(label.getFont().deriveFont(
										Font.ITALIC));
							}

							int width = label.getPreferredSize().width;
							int height = label.getPreferredSize().height;

							labelPanel.add(label);

							if (width > panelWidth - 236) {
								width = panelWidth - 236;
							}

							labelPanel.setBounds(107, 8, width, height + 10);

							backgroundPanel.setBounds(91, 0, panelWidth - 204,
									8 + height + 10);

							layeredPane.setBounds(0, 0, panelWidth,
									8 + height + 10);
							layeredPane.setPreferredSize(new Dimension(
									panelWidth, 8 + height + 10));

							/**
							 * Panel added to pane with z-index 0
							 */
							layeredPane.add(backgroundPanel, new Integer(0), 0);

							/**
							 * Panel added to pane with z-index 1
							 */
							layeredPane.add(labelPanel, new Integer(1), 0);
						}
					}

					messagePanel.add(layeredPane);

					panel.add(messagePanel);
				}

			}

			panel.add(Box.createRigidArea(new Dimension(10, spacerHeight)));

		}

		return panel;
	}

	private JLayeredPane createRightBottomBottomLayeredPane(int panelWidth,
			int panelHeight) {
		/* Start right bottom bottom panel */
		/**
		 * Construct layered pane for absolute positioning with z index
		 */
		JLayeredPane rightBottomBottomLayeredPane = new JLayeredPane();
		rightBottomBottomLayeredPane.setBounds(0, 0, panelWidth, panelHeight);
		rightBottomBottomLayeredPane.setPreferredSize(new Dimension(panelWidth,
				panelHeight));
		rightBottomBottomLayeredPane.setOpaque(false);

		if (selectedConversation == null) {
			/**
			 * The selected right panel page is not Conversation
			 * 
			 * We want the right panel to be just a JScrollPane in this case
			 * 
			 * So we will not put a right bottom bottom layered pane in this
			 * case
			 */
			return rightBottomBottomLayeredPane;
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1173550536.png");
			JLabel iconLabel = new JLabel(imageIcon);

			JPopupMenu menu = new JPopupMenu();

			JMenuItem sendFile = new JMenuItem("Send file...");

			sendFile.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new java.io.File("."));
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"*.png|*.jpg|*.jpeg|*.gif|*.bmp|*.mp4|*.mkv|*.avi",
							"png", "jpg", "jpeg", "gif", "bmp", "mp4", "mkv",
							"avi");
					fc.setFileFilter(filter);
					int returnVal = fc.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						String ext = fc.getSelectedFile().getName();
						if (ext.contains(".")) {
							ext = ext.substring(ext.lastIndexOf(".") + 1);
						}
						if (ext.equals("png") || ext.equals("jpeg")
								|| ext.equals("jpg") || ext.equals("gif")
								|| ext.equals("bmp")) {
							DialogForm form = new DialogForm(
									null,
									"Skype" + "\u2122" + " - Encrypt image?",
									"Encrypt image?",
									"Do you want to encrypt and send this image as a file?"
											+ '\n'
											+ " "
											+ '\n'
											+ "If you encrypt and send this image as a file, it will not"
											+ '\n'
											+ "not appear in the chat window. If you hit no it will be"
											+ '\n'
											+ "sent unencrypted to Imgur and shown in the chat window.",
									"Yes", new Runnable() {

										@Override
										public void run() {
											run2(fc.getSelectedFile());
										}

									});

							form.cancelButton.setText("No");

							form.cancelButton
									.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											form.dispatchEvent(new WindowEvent(
													form,
													WindowEvent.WINDOW_CLOSING));
											DialogForm form = new DialogForm(
													frame,
													"Skype"
															+ "\u2122"
															+ " - Upload image?",
													"Upload image?",
													"Are you sure you want to upload this image? The"
															+ '\r'
															+ '\n'
															+ "image will first be uploaded to Imgur and then sent"
															+ '\r'
															+ '\n'
															+ "in the chat. Please check out https://imgur.com/rules to"
															+ '\r'
															+ '\n'
															+ "see what kind of content is not legal to be uploaded.",
													"Upload", new Runnable() {

														@Override
														public void run() {
															File pathToData = fc
																	.getSelectedFile();
															ImgurUploader imgurUploader = new ImgurUploader();
															Optional<String> url = imgurUploader
																	.uploadFile(pathToData);
															if (!url.isPresent()) {
																return;
															}
															Optional<SocketHandlerContext> ctx = Skype
																	.getPlugin()
																	.createHandle();
															if (!ctx.isPresent()) {
																return;
															}
															UUID messageId = UUID
																	.randomUUID();
															long timestamp = System
																	.currentTimeMillis();
															Message message = new Message(
																	messageId,
																	loggedInUser
																			.getUniqueId(),
																	PGPUtilities
																			.encryptAndSign(
																					"<img src=\""
																							+ url.get()
																							+ "\" />",
																					selectedConversation),
																	timestamp,
																	selectedConversation);
															if (!conversations
																	.contains(selectedConversation)) {
																conversations
																		.add(selectedConversation);
															}
															UUID conversationId = selectedConversation
																	.getUniqueId();
															Optional<PacketPlayInReply> replyPacket = ctx
																	.get()
																	.getOutboundHandler()
																	.dispatch(
																			ctx.get(),
																			new PacketPlayOutSendMessage(
																					authCode,
																					conversationId,
																					messageId,
																					message.toString(),
																					timestamp));
															if (!replyPacket
																	.isPresent()) {
																return;
															}
															if (replyPacket
																	.get()
																	.getStatusCode() == 200) {
																if (!selectedConversation
																		.isGroupChat()) {
																	selectedConversation
																			.getMessages()
																			.add(message);
																	selectedConversation
																			.setLastModified(new Date(
																					new Date()
																							.getTime()
																							+ AppDelegate.TIME_OFFSET));
																	refreshWindow();
																}
																AudioIO.IM_SENT
																		.playSound();
															}
														}
													});
											form.show();
										}

									});

							form.show();

						} else if (ext.equals("mp4") || ext.equals("mkv")
								|| ext.equals("avi")) {
							DialogForm form = new DialogForm(
									null,
									"Skype" + "\u2122" + " - Encrypt video?",
									"Encrypt video?",
									"Do you want to encrypt and send this video as a file?"
											+ '\n'
											+ " "
											+ '\n'
											+ "If you encrypt and send this video as a file, it will not"
											+ '\n'
											+ "not appear in the chat window. If you hit no it will be"
											+ '\n'
											+ "sent unencrypted to Litterbox and shown in the chat window.",
									"Yes", new Runnable() {

										@Override
										public void run() {
											run2(fc.getSelectedFile());
										}

									});

							form.cancelButton.setText("No");

							form.cancelButton
									.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent arg0) {
											form.dispatchEvent(new WindowEvent(
													form,
													WindowEvent.WINDOW_CLOSING));
											DialogForm form = new DialogForm(
													frame,
													"Skype"
															+ "\u2122"
															+ " - Upload video?",
													"Upload video?",
													"Are you sure you want to upload this video? The"
															+ '\r'
															+ '\n'
															+ "video will first be uploaded to Litterbox and then sent"
															+ '\r'
															+ '\n'
															+ "in the chat. Please check out https://catbox.moe/faq.php to"
															+ '\r'
															+ '\n'
															+ "see what kind of content is not legal to be uploaded.",
													"Upload", new Runnable() {

														@Override
														public void run() {
															File pathToData = fc
																	.getSelectedFile();
															LitterboxUploader litterboxUploader = new LitterboxUploader();
															Optional<String> url = litterboxUploader
																	.uploadFile(pathToData);
															if (!url.isPresent()) {
																return;
															}
															Optional<SocketHandlerContext> ctx = Skype
																	.getPlugin()
																	.createHandle();
															if (!ctx.isPresent()) {
																return;
															}
															UUID messageId = UUID
																	.randomUUID();
															long timestamp = System
																	.currentTimeMillis();
															Message message = new Message(
																	messageId,
																	loggedInUser
																			.getUniqueId(),
																	PGPUtilities
																			.encryptAndSign(
																					"<video src=\""
																							+ url.get()
																							+ "\" />",
																					selectedConversation),
																	timestamp,
																	selectedConversation);
															if (!conversations
																	.contains(selectedConversation)) {
																conversations
																		.add(selectedConversation);
															}
															UUID conversationId = selectedConversation
																	.getUniqueId();
															Optional<PacketPlayInReply> replyPacket = ctx
																	.get()
																	.getOutboundHandler()
																	.dispatch(
																			ctx.get(),
																			new PacketPlayOutSendMessage(
																					authCode,
																					conversationId,
																					messageId,
																					message.toString(),
																					timestamp));
															if (!replyPacket
																	.isPresent()) {
																return;
															}
															if (replyPacket
																	.get()
																	.getStatusCode() == 200) {
																if (!selectedConversation
																		.isGroupChat()) {
																	selectedConversation
																			.getMessages()
																			.add(message);
																	selectedConversation
																			.setLastModified(new Date(
																					new Date()
																							.getTime()
																							+ AppDelegate.TIME_OFFSET));
																	refreshWindow();
																}
																AudioIO.IM_SENT
																		.playSound();
															}
														}
													});
											form.show();
										}

									});

							form.show();
						} else {
							run2(fc.getSelectedFile());
						}
					}
				}

			});

			menu.add(sendFile);

			MouseAdapter mouseAdapter = new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					super.mousePressed(evt);
					if (evt.getButton() == MouseEvent.BUTTON1) {
						menu.show(evt.getComponent(), evt.getX(), evt.getY());
					}
				}
			};

			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabel.addMouseListener(mouseAdapter);

			iconLabelPanel.setBounds(37, 21, 36, 36);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			iconLabelPanel.addMouseListener(mouseAdapter);

			/**
			 * Panel added to pane with z-index 0
			 */
			rightBottomBottomLayeredPane.add(iconLabelPanel, new Integer(0), 0);
		}

		{
			JPanel labelPanel = new JPanel();

			labelPanel.setOpaque(false);

			JLabel label = new JLabel("via");

			int width = label.getPreferredSize().width;
			int height = label.getPreferredSize().height;

			labelPanel.add(label);
			labelPanel.setBounds(92, -3, width, height + 10);

			/**
			 * Panel added to pane with z-index 0
			 */
			rightBottomBottomLayeredPane.add(labelPanel, new Integer(0), 0);
		}

		{
			JPanel labelPanel = new JPanel();

			labelPanel.setOpaque(false);

			JLabel label = new JLabel("Skype");
			label.setForeground(new Color(0, 149, 204));

			int width = label.getPreferredSize().width;
			int height = label.getPreferredSize().height;

			labelPanel.add(label);
			labelPanel.setBounds(110, -3, width, height + 10);

			/**
			 * Panel added to pane with z-index 0
			 */
			rightBottomBottomLayeredPane.add(labelPanel, new Integer(0), 0);
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1206468417.png");
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(126, 1, 28, 18);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 0
			 */
			rightBottomBottomLayeredPane.add(iconLabelPanel, new Integer(0), 0);
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new BoxLayout(iconLabelPanel,
					BoxLayout.X_AXIS));
			iconLabelPanel.setBounds(91, 21, panelWidth - 195, 36);
			iconLabelPanel.setOpaque(true);
			iconLabelPanel.setBackground(new Color(229, 247, 253));
			iconLabelPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
					1, new Color(102, 207, 246)));
			iconLabelPanel.add(Box.createRigidArea(new Dimension(15, 15)));

			conversationTextField.setPreferredSize(new Dimension(
					panelWidth - 180, 36));

			iconLabelPanel.add(conversationTextField);

			/**
			 * Panel added to pane with z-index 1
			 */
			rightBottomBottomLayeredPane.add(iconLabelPanel, new Integer(1), 0);
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1235097568.png");
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(panelWidth - 130, 31, 16, 16);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 1
			 */
			rightBottomBottomLayeredPane.add(iconLabelPanel, new Integer(1), 0);
		}

		{
			JPanel iconLabelPanel = new JPanel();
			iconLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			ImageIcon imageIcon = ImageIO
					.getResourceAsImageIcon("/1149538990.png");
			JLabel iconLabel = new JLabel(imageIcon);

			/**
			 * Reserved for future use
			 */
			iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			iconLabelPanel.setBounds(panelWidth - 104, 21, 36, 36);
			iconLabelPanel.setOpaque(false);
			iconLabelPanel.add(iconLabel);

			/**
			 * Panel added to pane with z-index 1
			 */
			rightBottomBottomLayeredPane.add(iconLabelPanel, new Integer(1), 0);
		}

		return rightBottomBottomLayeredPane;
	}

	private void selectConversation(Conversation conversation) {
		conversationTextField.setText("");
		selectedMessage = null;
		if (conversation == null) {
			rightPanelPage = "";
			selectedConversation = null;
		} else {
			if (ongoingCall
					&& conversation.getUniqueId().equals(
							ongoingCallConversation.getUniqueId())) {
				rightPanelPage = "OngoingCall";
			} else if (rightPanelPage.equals("Conversation")
					&& !conversation.isGroupChat()
					&& !selectedConversation.isGroupChat()
					&& conversation.getUniqueId().equals(
							selectedConversation.getUniqueId())
					&& conversation instanceof Contact) {
				rightPanelPage = "ViewUser";
			} else {
				rightPanelPage = "Conversation";
			}
			selectedConversation = conversation;
		}
		refreshWindow(SCROLL_TO_BOTTOM);
		rightBottomTopPanel.getVerticalScrollBar().setValue(
				rightBottomTopPanel.getVerticalScrollBar().getMaximum());
	}

	public final int RETAIN_SCROLL_POSITION = 0;
	public final int SCROLL_TO_BOTTOM = 1;
	public final int RETAIN_DISPLAY_MESSAGE_COUNT = 2;

	public void refreshWindow() {
		refreshWindow(RETAIN_SCROLL_POSITION);
	}

	Timer markAsReadTimer;

	public void refreshWindow(int flag) {
		if (selectedConversation != null) {
			selectedConversation.setNotificationCount(0);
			markAsReadTimer.restart();
		}
		refreshTaskbarIcon();

		boolean searchTextFieldFocus = false;

		if (searchTextField.hasFocus()) {
			searchTextFieldFocus = true;
		}

		String searchTextFieldText = searchTextField.getText();
		String conversationTextFieldText = conversationTextField.getText();

		refreshLeftBottomPanel();
		refreshLeftTopPanel();
		refreshRightTopPanel();

		if (rightPanelPage.equals("Conversation")) {
			if (searchTextFieldFocus) {
				searchTextField.requestFocusInWindow();
				searchTextField.setText(searchTextFieldText);
			} else {
				conversationTextField.requestFocusInWindow();
				conversationTextField.setText(conversationTextFieldText);
			}
		}

		final boolean fsearchTextFieldFocus = searchTextFieldFocus;

		/*
		 * We need to add a 100ms delay to refresh the right bottom panel
		 */
		Timer timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				((Timer) evt.getSource()).stop();
				String searchTextFieldText = searchTextField.getText();
				String conversationTextFieldText = conversationTextField
						.getText();
				refreshRightBottomPanel(flag);
				if (fsearchTextFieldFocus) {
					searchTextField.requestFocusInWindow();
					searchTextField.setText(searchTextFieldText);
				} else {
					conversationTextField.requestFocusInWindow();
					conversationTextField.setText(conversationTextFieldText);
				}
			}

		});
		timer.restart();
	}

	public void refreshLeftTopPanel() {
		int width = splitPane.getDividerLocation();

		if (width <= 0) {
			return;
		}

		/**
		 * We must remove the top left layered pane from the panel
		 */
		leftTopPanel.removeAll();

		/**
		 * Now that the width of the panel has changed
		 * 
		 * We must resize the components inside to compensate
		 */
		JLayeredPane leftTopLayeredPane = createLeftTopLayeredPane(width);

		/**
		 * Now we can add back the new top left layered pane
		 */
		leftTopPanel.add(leftTopLayeredPane);

		leftTopPanel.validate();
		leftTopPanel.repaint();
	}

	public void refreshLeftBottomPanel() {
		int width = splitPane.getDividerLocation();

		if (width <= 0) {
			return;
		}

		int verticalScrollBarPosition = leftBottomScrollPane
				.getVerticalScrollBar().getValue();

		/**
		 * We construct the left bottom panel using the right width
		 */
		JPanel leftBottomPanel = createLeftBottomPanel(width - scrollBarWidth
				+ 5);

		/**
		 * We now update the view on the left bottom scroll pane
		 */
		leftBottomScrollPane.setViewportView(leftBottomPanel);

		leftBottomScrollPane.validate();
		leftBottomScrollPane.repaint();

		leftBottomScrollPane.getVerticalScrollBar().setValue(
				verticalScrollBarPosition);
	}

	public void refreshRightTopPanel() {
		if (rightPanelPage.equals("Conversation")
				|| (rightPanelPage.equals("CallPhones") && VoIP.getPlugin()
						.isConnected())) {
			rightTopPanel.setVisible(true);
			rightTopPanel.removeAll();

			int leftSplitPaneWidth = leftSplitPane.getDividerLocation();
			if (leftSplitPaneWidth == 0 || leftSplitPaneWidth == -1) {
				leftSplitPaneWidth = this.defaultLeftPanelWidth;
			}

			JLayeredPane rightTopLayeredPane = this.createRightTopLayeredPane(
					getContentPane().getSize().width
							- this.splitPaneDividerSize - leftSplitPaneWidth,
					this.defaultRightTopPanelHeight);

			rightTopPanel.add(rightTopLayeredPane);

			rightTopPanel.validate();
			rightTopPanel.repaint();
		} else {
			rightTopPanel.setVisible(false);
		}
	}

	public void refreshRightBottomPanel(int flag) {
		this.rightBottomBottomPanel.removeAll();

		int leftSplitPaneWidth = leftSplitPane.getDividerLocation();
		if (leftSplitPaneWidth == 0 || leftSplitPaneWidth == -1) {
			leftSplitPaneWidth = this.defaultLeftPanelWidth;
		}

		int panelWidth = getContentPane().getSize().width
				- this.splitPaneDividerSize - leftSplitPaneWidth;

		int extent = rightBottomTopPanel.getVerticalScrollBar().getModel()
				.getExtent();
		boolean maximum = (rightBottomTopPanel.getVerticalScrollBar()
				.getValue() + extent) == rightBottomTopPanel
				.getVerticalScrollBar().getMaximum();

		JPanel rightBottomTopPanel = this.createRightBottomTopPanel(panelWidth
				- this.scrollBarWidth - 5, flag);

		int verticalScrollBarPosition = this.rightBottomTopPanel
				.getVerticalScrollBar().getValue();

		if (maximum) {
			verticalScrollBarPosition = this.rightBottomTopPanel
					.getVerticalScrollBar().getMaximum();
		}

		this.rightBottomTopPanel.setViewportView(rightBottomTopPanel);

		this.rightBottomTopPanel.getVerticalScrollBar().setValue(
				verticalScrollBarPosition);

		if (rightPanelPage.equals("Conversation")) {

			this.rightBottomBottomPanel.setVisible(true);

			JLayeredPane rightBottomBottomPanel = this
					.createRightBottomBottomLayeredPane(panelWidth,
							this.defaultRightBottomBottomPanelHeight);
			this.rightBottomBottomPanel.add(rightBottomBottomPanel);

		} else {

			this.rightBottomBottomPanel.setVisible(false);
		}

		rightBottomSplitPane.validate();

		if (flag == SCROLL_TO_BOTTOM) {
			JScrollBar vertical = this.rightBottomTopPanel
					.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
		}

		rightBottomSplitPane.repaint();

		rightBottomSplitPane
				.setDividerLocation(getContentPane().getSize().height
						- defaultRightBottomBottomPanelHeight
						- defaultRightTopPanelHeight);
	}

	Bot echoSoundTestService = new Bot();

	public boolean saveVoipContactList() {
		try {
			SocketHandlerContext ctx = Skype.getPlugin().createHandle().get();
			VoIPContactList contactList = new VoIPContactList();
			for (Conversation conversation : conversations) {
				if (conversation instanceof VoIPContact) {
					contactList.getContacts().add((VoIPContact) conversation);
				}
			}
			String json = contactList.toString();
			System.out.println(json);
			UUID authCode = null;
			PGPPublicKeyRing pubKey = null;
			{
				Optional<PacketPlayInReply> replyPacket = ctx
						.getOutboundHandler().dispatch(
								ctx,
								new PacketPlayOutPubKeyExchange(Skype
										.getPlugin().getPubKey()));
				System.out.println(replyPacket);
				if (replyPacket.isPresent()) {
					if (replyPacket.get().getStatusCode() == 200) {
						pubKey = PGPainless.readKeyRing().publicKeyRing(
								replyPacket.get().getText());
					}
				}
			}
			json = PGPUtilities.encryptAndSign(json, Skype.getPlugin()
					.getPrivKey(), pubKey);
			Optional<PacketPlayInReply> replyPacket = ctx.getOutboundHandler()
					.dispatch(
							ctx,
							new PacketPlayOutUpdateVoipContacts(this.authCode,
									json));
			System.out.println(replyPacket);
			if (replyPacket.isPresent()) {
				if (replyPacket.get().getStatusCode() == 200) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Conversation lookupUser2(UUID participantId) {
		for (Conversation conversation : conversations) {
			if (conversation.getUniqueId().equals(participantId)) {
				return conversation;
			}
		}
		return null;
	}

	public void readFromMemory() {
		SocketHandlerContext ctx = Skype.getPlugin().getHandle();

		{
			PacketPlayOutEnteringListeningMode msg = new PacketPlayOutEnteringListeningMode(
					authCode);
			ctx.getOutboundHandler().dispatch(ctx, msg);
		}

		ctx.fireOutboundHandlerInactive();

		shown = true;

		ctx.fireInboundHandlerActive(new Runnable() {

			@Override
			public void run() {
				/*
				 * incoming data stream got closed
				 */
				logOut();
				try {
					timer1.stop();
					timer2.stop();
					timer3.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					mic.stop();
					mic.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.removeWindowListener(windowAdapter);
				frame.dispatchEvent(new WindowEvent(frame,
						WindowEvent.WINDOW_CLOSING));
				AudioIO.LOGOUT.playSound();
				LoginForm loginForm = new LoginForm();
				loginForm.show();
			}

		});

		markAsReadTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				((Timer) evt.getSource()).stop();
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						Optional<SocketHandlerContext> ctx = Skype.getPlugin()
								.createHandle();
						if (!ctx.isPresent()) {
							return;
						}
						if (selectedConversation == null) {
							return;
						}
						UUID conversationId = selectedConversation
								.getUniqueId();
						Optional<PacketPlayInReply> replyPacket = ctx
								.get()
								.getOutboundHandler()
								.dispatch(
										ctx.get(),
										new PacketPlayOutMarkConversationAsRead(
												authCode, conversationId));
						if (!replyPacket.isPresent()) {
							return;
						}
						if (replyPacket.get().getStatusCode() != 200) {
							return;
						}
						try {
							ctx.get().getSocket().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				});
				thread.start();
			}

		});
		// conversations.clear();
		echoSoundTestService.setSkypeName("echo123");
		echoSoundTestService.setUniqueId(Skype.getPlugin().getUniqueId(
				echoSoundTestService.getSkypeName()));
		echoSoundTestService.setDisplayName("Echo / Sound Test Service");
		echoSoundTestService.setLastModified(new Date(new Date().getTime()
				+ AppDelegate.TIME_OFFSET));
		echoSoundTestService.setOnlineStatus(Status.ONLINE);
		if (lookupUser2(echoSoundTestService.getUniqueId()) == null)
			conversations.add(echoSoundTestService);
		ctx = Skype.getPlugin().createHandle().get();
		Date now = new Date(new Date().getTime() + AppDelegate.TIME_OFFSET);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		now = cal.getTime();
		Date startOfTime = new Date(2012 - 1900, 0, 1);
		Gson gson = GsonBuilder.create();
		PacketPlayOutLookupUserRegistry packet = new PacketPlayOutLookupUserRegistry(
				authCode);
		Optional<PacketPlayInReply> replyPacket = ctx.getOutboundHandler()
				.dispatch(ctx, packet);
		if (!replyPacket.isPresent()) {
			return;
		}
		if (replyPacket.get().getStatusCode() != 200) {
			return;
		}
		String json = replyPacket.get().getText();
		registry = gson.fromJson(json, List.class);
		PacketPlayOutLookupVoipContacts voipContactsLookup = new PacketPlayOutLookupVoipContacts(
				authCode);
		replyPacket = ctx.getOutboundHandler()
				.dispatch(ctx, voipContactsLookup);
		if (replyPacket.isPresent() && replyPacket.get().getStatusCode() == 200) {
			json = replyPacket.get().getText();
			PGPPublicKeyRing pubKey = null;
			{
				replyPacket = ctx.getOutboundHandler().dispatch(
						ctx,
						new PacketPlayOutPubKeyExchange(Skype.getPlugin()
								.getPubKey()));
				System.out.println(replyPacket);
				if (replyPacket.isPresent()) {
					if (replyPacket.get().getStatusCode() == 200) {
						try {
							pubKey = PGPainless.readKeyRing().publicKeyRing(
									replyPacket.get().getText());
							json = PGPUtilities.decryptAndVerify(json,
									Skype.getPlugin().getPrivKey(), pubKey)
									.getMessage();
							VoIPContactList contactList = new VoIPContactList(
									json);
							for (VoIPContact contact : contactList
									.getContacts()) {
								contact.setContact(contact.isContact());
								if (lookupUser2(contact.getUniqueId()) == null)
									conversations.add(contact);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		PacketPlayOutLookupContacts contactsLookup = new PacketPlayOutLookupContacts(
				authCode);
		replyPacket = ctx.getOutboundHandler().dispatch(ctx, contactsLookup);
		if (!replyPacket.isPresent()) {
			return;
		}
		List<String> contacts = new ArrayList<>();
		if (replyPacket.get().getStatusCode() == 200) {
			contacts = gson.fromJson(replyPacket.get().getText(), List.class);
		}
		PacketPlayOutLookupConversationHistory conversationHistoryLookup = new PacketPlayOutLookupConversationHistory(
				authCode, startOfTime, now);
		try {
			ctx.getSocket().setSoTimeout(30000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		replyPacket = ctx.getOutboundHandler().dispatch(ctx,
				conversationHistoryLookup);
		if (!replyPacket.isPresent()) {
			return;
		}
		if (replyPacket.get().getStatusCode() != 200) {
			return;
		}
		List<String> participantIds = gson.fromJson(
				replyPacket.get().getText(), List.class);
		for (String key : participantIds) {
			UUID participantId = UUID.fromString(key);
			if (participantId.equals(echoSoundTestService.getUniqueId())) {
				continue;
			}
			if (participantId.equals(loggedInUser.getUniqueId())) {
				continue;
			}
			Conversation contact2;
			boolean hit2 = false;
			if ((contact2 = lookupUser2(participantId)) == null) {
				PacketPlayOutLookupUser userLookup = new PacketPlayOutLookupUser(
						authCode, participantId);
				replyPacket = ctx.getOutboundHandler()
						.dispatch(ctx, userLookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				contact2 = new Contact(replyPacket.get().getText());
				hit2 = true;
			}
			Status onlineStatus = Status.OFFLINE;
			{
				PacketPlayOutLookupOnlineStatus onlineStatusLookup = new PacketPlayOutLookupOnlineStatus(
						authCode, participantId);
				Optional<PacketPlayInReply> replyPacket2 = ctx
						.getOutboundHandler().dispatch(ctx, onlineStatusLookup);
				if (!replyPacket2.isPresent()) {
					return;
				}
				if (replyPacket2.get().getStatusCode() != 200) {
					continue;
				}
				onlineStatus = Status.valueOf(replyPacket2.get().getText());
			}
			boolean hit = false;
			for (String contact : contacts) {
				UUID contactuid = UUID.fromString(contact);
				if (contactuid.equals(participantId)) {
					hit = true;
					break;
				}
			}
			if (hit) {
				Contact contact;
				if (contact2 instanceof Contact) {
					contact = (Contact) contact2;
				} else if (hit2 == false) {
					// fix broken data import
					PacketPlayOutLookupUser userLookup = new PacketPlayOutLookupUser(
							authCode, participantId);
					replyPacket = ctx.getOutboundHandler().dispatch(ctx,
							userLookup);
					if (!replyPacket.isPresent()) {
						return;
					}
					if (replyPacket.get().getStatusCode() != 200) {
						continue;
					}
					contact = new Contact(replyPacket.get().getText());
					contact.getMessages().addAll(contact2.getMessages());
					contact.setLastModified(contact2.getLastModified());
					conversations.remove(contact2);
					conversations.add(contact);
					hit2 = false;
					refreshWindow();
				} else {
					// what da fuk
					contact = new Contact(contact2.toString());
				}
				if (onlineStatus == Status.OFFLINE) {
					contact.setOnlineStatus(Status.OFFLINE);
				}
				Date startTime = startOfTime;
				if (!hit2) {
					startTime = contact.getLastModified();
				}
				PacketPlayOutLookupMessageHistory messageHistoryLookup = new PacketPlayOutLookupMessageHistory(
						authCode, participantId, startTime, now);
				replyPacket = ctx.getOutboundHandler().dispatch(ctx,
						messageHistoryLookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				List<String> messages = gson.fromJson(replyPacket.get()
						.getText(), List.class);
				Collections.sort(messages);
				PacketPlayOutLookupConversationLastAccessed conversationLastReadLookup = new PacketPlayOutLookupConversationLastAccessed(
						authCode, participantId);
				replyPacket = ctx.getOutboundHandler().dispatch(ctx,
						conversationLastReadLookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				long lastAccessed = Long.parseLong(replyPacket.get().getText());
				PacketPlayOutLookupConversationLastAccessed2 conversationLastRead2Lookup = new PacketPlayOutLookupConversationLastAccessed2(
						authCode, participantId);
				replyPacket = ctx.getOutboundHandler().dispatch(ctx,
						conversationLastRead2Lookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				long lastAccessed2 = Long
						.parseLong(replyPacket.get().getText());
				int unread = 0;
				boolean hit3 = contact.getMessages().size() > 0;
				List<Message> messages2 = new ArrayList<>();
				for (String message : messages) {
					Message msg = new Message(message, contact);
					msg.setRead(lastAccessed2);
					messages2.add(msg);
					if (msg.getTimestamp() > lastAccessed) {
						unread++;
					}
				}
				if (hit3) {
					for (Message message : messages2) {
						boolean hit4 = false;
						for (Message message2 : contact.getMessages()) {
							if (message2.getUniqueId().equals(
									message.getUniqueId())) {
								hit4 = true;
								break;
							}
						}
						if (!hit4) {
							contact.getMessages().add(message);
						}
					}
				} else {
					contact.getMessages().addAll(messages2);
				}
				Collections.sort(contact.getMessages(),
						new Comparator<Message>() {
							@Override
							public int compare(Message c1, Message c2) {
								return Long.compare(c1.getTimestamp(),
										c2.getTimestamp());
							}
						});
				contact.setNotificationCount(unread);
				if (contact.getMessages().size() > 0) {
					contact.setLastModified(new Date(contact.getMessages()
							.get(contact.getMessages().size() - 1)
							.getTimestamp()));
					if (hit2)
						conversations.add(contact);
				}
			} else {
				Date startTime = startOfTime;
				if (!hit2) {
					startTime = contact2.getLastModified();
				}
				Conversation conversation = new Conversation(
						contact2.toString());
				if (!conversation.isGroupChat()) {
					conversation.setDisplayName(conversation.getSkypeName());
				}
				PacketPlayOutLookupMessageHistory messageHistoryLookup = new PacketPlayOutLookupMessageHistory(
						authCode, participantId, startTime, now);
				replyPacket = ctx.getOutboundHandler().dispatch(ctx,
						messageHistoryLookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				List<String> messages = gson.fromJson(replyPacket.get()
						.getText(), List.class);
				Collections.sort(messages);
				PacketPlayOutLookupConversationLastAccessed conversationLastReadLookup = new PacketPlayOutLookupConversationLastAccessed(
						authCode, participantId);
				replyPacket = ctx.getOutboundHandler().dispatch(ctx,
						conversationLastReadLookup);
				if (!replyPacket.isPresent()) {
					return;
				}
				if (replyPacket.get().getStatusCode() != 200) {
					continue;
				}
				long lastAccessed = Long.parseLong(replyPacket.get().getText());
				int unread = 0;
				boolean hit3 = conversation.getMessages().size() > 0;
				List<Message> messages2 = new ArrayList<>();
				for (String message : messages) {
					Message msg = new Message(message, conversation);
					messages2.add(msg);
					if (msg.getTimestamp() > lastAccessed) {
						unread++;
					}
				}
				if (hit3) {
					for (Message message : messages2) {
						boolean hit4 = false;
						for (Message message2 : conversation.getMessages()) {
							if (message2.getUniqueId().equals(
									message.getUniqueId())) {
								hit4 = true;
								break;
							}
						}
						if (!hit4) {
							conversation.getMessages().add(message);
						}
					}
				} else {
					conversation.getMessages().addAll(messages2);
				}
				Collections.sort(conversation.getMessages(),
						new Comparator<Message>() {
							@Override
							public int compare(Message c1, Message c2) {
								return Long.compare(c1.getTimestamp(),
										c2.getTimestamp());
							}
						});
				conversation.setNotificationCount(unread);
				for (Message message : conversation.getMessages()) {
					if (message.getMessageType() == null) {
						continue;
					}
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
				if (conversation.getMessages().size() > 0) {
					conversation.setLastModified(new Date(conversation
							.getMessages()
							.get(conversation.getMessages().size() - 1)
							.getTimestamp()));
					if (hit2)
						conversations.add(conversation);
				}
			}
		}
		try {
			ctx.getSocket().setSoTimeout(2000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(authCode,
				loggedInUser.getUniqueId(), loggedInUser);
		ctx.getOutboundHandler().write(ctx, msg);
	}

	float sampleRate = 8000.0F;
	int sampleSizeBits = 16;
	int channels = 1;
	boolean signed = true;
	boolean bigEndian = false;
	AudioFormat format = new AudioFormat(sampleRate, sampleSizeBits, channels,
			signed, bigEndian);
	DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
	public TargetDataLine mic = null;

	private Optional<UUID> registerUser(Conversation conversation,
			String password) {
		Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
		if (!ctx.isPresent()) {
			return Optional.empty();
		}
		PGPPublicKeyRing pubKey = null;
		String fpassword = null;
		try {
			{
				Optional<PacketPlayInReply> replyPacket = ctx
						.get()
						.getOutboundHandler()
						.dispatch(
								ctx.get(),
								new PacketPlayOutPubKeyExchange(Skype
										.getPlugin().getPubKey()));
				System.out.println(replyPacket);
				if (replyPacket.isPresent()) {
					if (replyPacket.get().getStatusCode() == 200) {
						pubKey = PGPainless.readKeyRing().publicKeyRing(
								replyPacket.get().getText());
					}
				}
			}
			fpassword = PGPUtilities.encryptAndSign(password, Skype.getPlugin()
					.getPrivKey(), pubKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PacketPlayOutRegister registerPacket = new PacketPlayOutRegister(
				conversation.getDisplayName(), conversation.getSkypeName(),
				fpassword);
		registerPacket.setSilent(true);
		if (conversation.isGroupChat()) {
			registerPacket.setGroupChatAdmin(loggedInUser.getUniqueId());
		}
		Optional<PacketPlayInReply> replyPacket = ctx.get()
				.getOutboundHandler().dispatch(ctx.get(), registerPacket);
		boolean alreadyRegistered = false;
		if (!replyPacket.isPresent()
				|| replyPacket.get().getStatusCode() != 200) {
			alreadyRegistered = true;
		}
		UUID authCode = null;
		if (alreadyRegistered && conversation.isGroupChat()) {
			authCode = UUID.fromString(ctx
					.get()
					.getOutboundHandler()
					.dispatch(
							ctx.get(),
							new PacketPlayOutLogin(this.authCode, conversation
									.getSkypeName())).get().getText());
		} else {
			replyPacket = Optional.of(
					ctx.get()
							.getOutboundHandler()
							.dispatch(
									ctx.get(),
									new PacketPlayOutLogin(conversation
											.getSkypeName(), fpassword))).get();
			String text = null;
			try {
				text = PGPUtilities.decryptAndVerify(
						replyPacket.get().getText(),
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
		UUID participantId = Skype.getPlugin().getUniqueId(
				conversation.getSkypeName());
		ctx.get().getOutboundHandler()
				.dispatch(ctx.get(), new PacketPlayOutRefreshToken(authCode));
		Contact contact = new Contact();
		if (alreadyRegistered) {
			replyPacket = ctx
					.get()
					.getOutboundHandler()
					.dispatch(
							ctx.get(),
							new PacketPlayOutLookupUser(authCode, participantId));
			if (!replyPacket.isPresent()) {
				return Optional.empty();
			}
			contact = new Contact(replyPacket.get().getText());
		}
		contact.setUniqueId(participantId);
		contact.setUniqueId2(conversation.getUniqueId2());
		contact.setSkypeName(conversation.getSkypeName());
		contact.setDisplayName(conversation.getDisplayName());
		if (!alreadyRegistered) {
			contact.setOnlineStatus(Status.ONLINE);
		}
		contact.setGroupChat(conversation.isGroupChat());
		if (conversation instanceof Contact) {
			contact.setMood(((Contact) conversation).getMood());
			contact.setMobilePhone(((Contact) conversation).getMobilePhone());
			contact.setHomePhone(((Contact) conversation).getHomePhone());
			contact.setOfficePhone(((Contact) conversation).getOfficePhone());
		}
		ctx.get()
				.getOutboundHandler()
				.dispatch(
						ctx.get(),
						new PacketPlayOutUpdateUser(authCode, participantId,
								contact));
		contact = new Contact(contact.toString());
		return Optional.of(authCode);
	}

	public MainForm(UUID authCode, String password, Contact loggedInUser) {
		super("Skype\u2122 - " + loggedInUser.getSkypeName());

		ongoingCallProfilePictureImageLabel = new JLabel(
				ImageIO.getResourceAsImageIcon("/1595064335.png"));

		this.password = password;

		try {
			mic = (TargetDataLine) AudioSystem.getLine(micInfo);
			mic.open(format);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JFrame frame = this;

		instance = this;

		this.authCode = authCode;
		this.loggedInUser = loggedInUser;

		conversationTextField.setBorder(BorderFactory.createEmptyBorder());
		conversationTextField.setOpaque(false);
		conversationTextField.setFont(font);
		KeyStroke enterKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		conversationTextField.getKeymap()
				.removeKeyStrokeBinding(enterKeyStroke);
		conversationTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (selectedMessage != null) {
						selectedMessage = null;
						conversationTextField.setText("");
						refreshWindow(RETAIN_SCROLL_POSITION);
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		conversationTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (conversationTextField.getText().trim().length() == 0) {
					return;
				}
				if (conversationTextField.getText().trim().startsWith("s/")) {
					String oldtext = conversationTextField
							.getText()
							.trim()
							.substring(
									conversationTextField.getText().trim()
											.indexOf("/") + 1);
					if (oldtext.indexOf("/") != -1) {
						String newtext = oldtext.substring(oldtext.indexOf("/") + 1);
						oldtext = oldtext.substring(0, oldtext.indexOf("/"));
						Message lastMessageFromLoggedInUser = null;
						for (Message msg : selectedConversation.getMessages()
								.toArray(new Message[0]).clone()) {
							if (msg.sender.equals(loggedInUser.getUniqueId())) {
								if (lastMessageFromLoggedInUser != null) {
									if (msg.timestamp > lastMessageFromLoggedInUser.timestamp) {
										lastMessageFromLoggedInUser = msg;
									}
								} else {
									lastMessageFromLoggedInUser = msg;
								}
							}
						}
						selectedMessage = lastMessageFromLoggedInUser;
						conversationTextField.setText(selectedMessage
								.getDecryptedMessage()
								.replace(oldtext, newtext));
					}
				}
				String txt = conversationTextField.getText().trim();
				UUID messageId = UUID.randomUUID();
				long timestamp = new Date(new Date().getTime()
						+ AppDelegate.TIME_OFFSET).getTime();
				Message message = new Message(messageId, loggedInUser
						.getUniqueId(), txt, timestamp, selectedConversation);
				if (selectedMessage != null) {
					message = selectedMessage;
					message.setMessage(txt);
					message.setDecryptedMessage(conversationTextField.getText()
							.trim());
				}
				final Message fmessage = new Message(message.toString());
				final Conversation fselectedConversation = selectedConversation;
				Thread thread = new Thread(() -> {
					if (fselectedConversation instanceof VoIPContact) {
						VoIP.getPlugin().API_SendChat(
								fselectedConversation.getDisplayName(), txt);
						AudioIO.IM_SENT.playSound();
						return;
					}
					Optional<SocketHandlerContext> ctx = Skype.getPlugin()
							.createHandle();
					if (!ctx.isPresent()) {
						return;
					}
					fmessage.setMessage(PGPUtilities.encryptAndSign(txt,
							fselectedConversation));
					UUID conversationId = fselectedConversation.getUniqueId();
					Optional<PacketPlayInReply> replyPacket = ctx
							.get()
							.getOutboundHandler()
							.dispatch(
									ctx.get(),
									new PacketPlayOutSendMessage(authCode,
											conversationId, fmessage
													.getUniqueId(), fmessage
													.toString(), fmessage
													.getTimestamp()));
					if (!replyPacket.isPresent()) {
						return;
					}
					if (replyPacket.get().getStatusCode() != 200) {
						SwingUtilities.invokeLater(() -> {
							logOut();
						});
					}
				});
				thread.start();
				if (!conversations.contains(selectedConversation)) {
					conversations.add(selectedConversation);
				}
				if (selectedMessage == null) {
					selectedConversation.getMessages().add(message);
				}
				selectedConversation.setLastModified(new Date(new Date()
						.getTime() + AppDelegate.TIME_OFFSET));
				refreshWindow();
				if (selectedMessage != null) {
					selectedMessage = null;
				}
				conversationTextField.setText("");
				AudioIO.IM_SENT.playSound();
			}

		});

		KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.CTRL_DOWN_MASK);
		final ActionListener ctrlVAction = conversationTextField
				.getActionForKeyStroke(ctrlV);
		conversationTextField.registerKeyboardAction(new CombinedAction(
				ctrlVAction, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Transferable transferable = Toolkit.getDefaultToolkit()
								.getSystemClipboard().getContents(null);
						if (transferable != null
								&& transferable
										.isDataFlavorSupported(DataFlavor.imageFlavor)) {
							try {
								BufferedImage bufferedImage = (BufferedImage) transferable
										.getTransferData(DataFlavor.imageFlavor);
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								javax.imageio.ImageIO.write(bufferedImage,
										"jpg", baos);
								DialogForm form = new DialogForm(
										frame,
										"Skype" + "\u2122" + " - Upload image?",
										"Upload image?",
										"Are you sure you want to upload this image? The"
												+ '\r'
												+ '\n'
												+ "image will first be uploaded to Imgur and then sent"
												+ '\r'
												+ '\n'
												+ "in the chat. Please check out https://imgur.com/rules to"
												+ '\r'
												+ '\n'
												+ "see what kind of content is not legal to be uploaded.",
										"Upload", new Runnable() {

											@Override
											public void run() {
												ImgurUploader imgurUploader = new ImgurUploader();
												Optional<String> url = imgurUploader
														.uploadImg(baos
																.toByteArray());
												if (!url.isPresent()) {
													return;
												}
												Optional<SocketHandlerContext> ctx = Skype
														.getPlugin()
														.createHandle();
												if (!ctx.isPresent()) {
													return;
												}
												UUID messageId = UUID
														.randomUUID();
												long timestamp = System
														.currentTimeMillis();
												Message message = new Message(
														messageId,
														loggedInUser
																.getUniqueId(),
														PGPUtilities
																.encryptAndSign(
																		"<img src=\""
																				+ url.get()
																				+ "\" />",
																		selectedConversation),
														timestamp,
														selectedConversation);
												if (!conversations
														.contains(selectedConversation)) {
													conversations
															.add(selectedConversation);
												}
												UUID conversationId = selectedConversation
														.getUniqueId();
												Optional<PacketPlayInReply> replyPacket = ctx
														.get()
														.getOutboundHandler()
														.dispatch(
																ctx.get(),
																new PacketPlayOutSendMessage(
																		authCode,
																		conversationId,
																		messageId,
																		message.toString(),
																		timestamp));
												if (!replyPacket.isPresent()) {
													return;
												}
												if (replyPacket.get()
														.getStatusCode() == 200) {
													if (!selectedConversation
															.isGroupChat()) {
														selectedConversation
																.getMessages()
																.add(message);
														selectedConversation
																.setLastModified(new Date(
																		new Date()
																				.getTime()
																				+ AppDelegate.TIME_OFFSET));
														refreshWindow();
													}
													AudioIO.IM_SENT.playSound();
												}
											}
										});
								form.show();
							} catch (UnsupportedFlavorException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}), ctrlV, JComponent.WHEN_FOCUSED);

		{

			SocketHandlerContext ctx = Skype.getPlugin().getHandle();

			try {
				loggedInUser.setPubKey(PGPUtilities
						.createOrLookupPublicKey(loggedInUser.getSkypeName()));
			} catch (InvalidAlgorithmParameterException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (PGPException e1) {
				e1.printStackTrace();
			}

			{
				PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(
						authCode, loggedInUser.getUniqueId(), loggedInUser);
				ctx.getOutboundHandler().dispatch(ctx, msg);
			}

		}

		addWindowListener(windowAdapter);

		JMenuBar menuBar = new JMenuBar();

		JMenu skypeMenu = new JMenu("Skype");
		if (getProperty("os.name").startsWith("Windows")) {
			File exeFile = new File("SkypeChatExporter.exe");
			if (!exeFile.exists()) {
				exeFile = new File("SkypeChatExporter.exe");
			}
			if (exeFile.exists()) {
				JMenuItem importData = new JMenuItem("Import Data...");
				final File fexeFile = exeFile;
				importData.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Optional<SocketHandlerContext> ctx = Skype.getPlugin()
								.createHandle();
						if (!ctx.isPresent()) {
							logOut();
							return;
						}
						Optional<PacketPlayInReply> replyPacket = ctx
								.get()
								.getOutboundHandler()
								.dispatch(
										ctx.get(),
										new PacketPlayOutIsRatelimitingEnabled(
												authCode));
						if (!replyPacket.isPresent()
								|| replyPacket.get().getStatusCode() != 200) {
							logOut();
							return;
						}
						boolean ratelimiting = Boolean.parseBoolean(replyPacket
								.get().getText());
						if (ratelimiting) {
							DialogForm form = new DialogForm(
									null,
									"Skype" + "\u2122" + " - Import data?",
									"Import data?",
									"The server needs to first have rate limiting disabled. This"
											+ '\n'
											+ "feature spams the server with a lot of packets. You can"
											+ '\n'
											+ "disable rate limiting in server.properties on the server.",
									null, new Runnable() {

										@Override
										public void run() {
										}
									});

							form.show();
							return;
						}
						try {
							String pathToDbFile;
							JFileChooser fc = new JFileChooser();
							fc.setCurrentDirectory(new java.io.File("."));
							fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
							FileNameExtensionFilter filter = new FileNameExtensionFilter(
									"*.db", "db");
							fc.setFileFilter(filter);
							int returnVal = fc.showOpenDialog(null);
							if (returnVal != JFileChooser.APPROVE_OPTION) {
								return;
							}
							pathToDbFile = fc.getSelectedFile()
									.getAbsolutePath();
							File baseDir = new File(System
									.getProperty("java.io.tmpdir"),
									"SkypeChatExporter");
							baseDir = new File(baseDir, loggedInUser
									.getSkypeName());
							baseDir = new File(baseDir, Skype.getPlugin()
									.getHostname());
							baseDir = new File(baseDir,
									PacketPlayOutLogin.PROTOCOL_VERSION + "");
							baseDir.mkdirs();
							delete(baseDir);
							baseDir.mkdirs();
							String pathToExportDir = baseDir.getAbsolutePath();
							ProcessBuilder pb = new ProcessBuilder(fexeFile
									.getAbsolutePath() + "", pathToDbFile,
									pathToExportDir);
							Process p = pb.inheritIO().start();
							int exitCode = p.waitFor();
							if (exitCode != 0) {
								return;
							}
							File pathToData = baseDir;
							selectedConversation = null;
							rightPanelPage = "CallPhones";
							voipNumber = "";
							SkypeChatImporter skypeChatViewer = new SkypeChatImporter(
									frame, pathToData, loggedInUser,
									new Runnable() {

										@Override
										public void run() {
										}

									});
							Skype.getPlugin().getConfig()
									.set("username", null);
							Skype.getPlugin().getConfig()
									.set("token", null);
							Skype.getPlugin().getConfig()
									.set("hostname", null);
							try {
								Skype.getPlugin()
										.getConfig()
										.set(
												loggedInUser.getSkypeName()
														+ ".sipserver", null);
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								Skype.getPlugin()
										.getConfig()
										.set(
												loggedInUser.getSkypeName()
														+ ".sipusername", null);
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								Skype.getPlugin()
										.getConfig()
										.set(
												loggedInUser.getSkypeName()
														+ ".sippassword", null);
							} catch (Exception e) {
								e.printStackTrace();
							}
							logOut();
							baseDir = new File(System
									.getProperty("java.io.tmpdir"),
									"Skype-savedata");
							baseDir = new File(baseDir, loggedInUser
									.getSkypeName());
							baseDir = new File(baseDir, Skype.getPlugin()
									.getHostname());
							baseDir = new File(baseDir,
									PacketPlayOutLogin.PROTOCOL_VERSION + "");
							delete(baseDir);
							skypeChatViewer.show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
				skypeMenu.add(importData);
				skypeMenu.add(new JSeparator());
			}
		}
		skypeMenu.add(new JMenuItemDisabled("Online Status"));
		skypeMenu.add(new JSeparator());
		skypeMenu.add(new JMenuItemDisabled("Profile"));
		skypeMenu.add(new JMenuItemDisabled("Privacy..."));
		skypeMenu.add(new JMenuItemDisabled("Account..."));
		skypeMenu.add(new JMenuItemDisabled("Buy Skype Credit..."));
		skypeMenu.add(new JSeparator());
		skypeMenu.add(new JMenuItemDisabled("Change Password..."));
		JMenuItem signOut = new JMenuItem("Sign Out");
		signOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Skype.getPlugin().getConfig().set("username", null);
				Skype.getPlugin().getConfig().set("token", null);
				Skype.getPlugin().getConfig().set("hostname", null);
				try {
					Skype.getPlugin()
							.getConfig()
							.set(
									loggedInUser.getSkypeName() + ".sipserver",
									null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Skype.getPlugin()
							.getConfig()
							.set(
									loggedInUser.getSkypeName()
											+ ".sipusername", null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Skype.getPlugin()
							.getConfig()
							.set(
									loggedInUser.getSkypeName()
											+ ".sippassword", null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logOut();
			}

		});
		skypeMenu.add(signOut);
		skypeMenu.add(new JMenuItemDisabled("Close"));
		JMenu contactsMenu = new JMenu("Contacts");
		contactsMenu.add(new JMenuItemDisabled("Add Contact"));
		contactsMenu.add(new JMenuItemDisabled("Import Contacts..."));
		JMenuItem createNewGroupMenuItem = new JMenuItemDisabled(
				"Create New Group...");
		createNewGroupMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		contactsMenu.add(createNewGroupMenuItem);
		contactsMenu.add(new JSeparator());
		contactsMenu.add(new JMenu("Contact Lists"));
		contactsMenu.add(new JMenuItemDisabled("Show Outlook Contacts"));
		contactsMenu.add(new JMenu("Sort Contacts by"));
		contactsMenu.add(new JMenu("Hide Contacts Who"));
		contactsMenu.add(new JMenu("Advanced"));
		contactsMenu.add(new JSeparator());
		{
			JMenuItem menuItem = new JMenuItem("Remove from Contacts");
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (selectedConversation == null) {
						return;
					}
					if (!(selectedConversation instanceof Contact)
							&& !(selectedConversation instanceof VoIPContact)) {
						return;
					}
					DialogForm form = new DialogForm(null, "Skype" + "\u2122"
							+ " - Remove contact?", "Remove contact?",
							"Remove " + selectedConversation.getDisplayName()
									+ " from Contacts?", "Remove",
							new Runnable() {

								@Override
								public void run() {
									if (selectedConversation instanceof VoIPContact) {
										conversations
												.remove(selectedConversation);
										selectedConversation = null;
										rightPanelPage = "AccountHome";
										saveVoipContactList();
										refreshWindow();
										return;
									}
									Optional<SocketHandlerContext> ctx = Skype
											.getPlugin().createHandle();
									if (ctx.isPresent()) {
										Optional<PacketPlayInReply> reply = ctx
												.get()
												.getOutboundHandler()
												.dispatch(
														ctx.get(),
														new PacketPlayOutLogin(
																authCode));
										if (!reply.isPresent()
												|| reply.get().getStatusCode() != 200) {
											return;
										}
										PacketPlayOutRemoveContact removeContactPacket = new PacketPlayOutRemoveContact(
												authCode, selectedConversation
														.getUniqueId());
										Optional<PacketPlayInReply> reply2 = ctx
												.get()
												.getOutboundHandler()
												.dispatch(ctx.get(),
														removeContactPacket);
										if (!reply2.isPresent()
												|| reply2.get().getStatusCode() != 200) {
											return;
										}
										for (Message message : selectedConversation
												.getMessages()
												.toArray(new Message[0])
												.clone()) {
											if (message.isDeleted()) {
												continue;
											}
											UUID authCode = UUID
													.fromString(reply.get()
															.getText());
											PacketPlayOutRemoveMessage removeMessage = new PacketPlayOutRemoveMessage(
													authCode,
													selectedConversation
															.getUniqueId(),
													message.getUniqueId(),
													message.getTimestamp());
											ctx.get()
													.getOutboundHandler()
													.write(ctx.get(),
															removeMessage);
										}
									}
								}
							});

					form.show();
				}

			});
			contactsMenu.add(menuItem);
		}
		JMenu conversationMenu = new JMenu("Conversation");
		conversationMenu.add(new JMenu("Send"));
		conversationMenu.add(new JSeparator());
		conversationMenu.add(new JMenu("Profile Panel"));
		JMenuItem addPeopleMenuItem = new JMenuItemDisabled("Add People...");
		addPeopleMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("control shift A"));
		conversationMenu.add(addPeopleMenuItem);
		conversationMenu.add(new JMenuItemDisabled("Rename..."));
		conversationMenu.add(new JMenuItemDisabled("Leave Conversation"));
		conversationMenu.add(new JMenuItemDisabled("Block..."));
		conversationMenu.add(new JMenuItemDisabled("Notification Settings..."));
		conversationMenu.add(new JSeparator());
		JMenuItem findMenuItem = new JMenuItemDisabled("Find...");
		findMenuItem.setAccelerator(KeyStroke.getKeyStroke("control F"));
		conversationMenu.add(findMenuItem);
		conversationMenu.add(new JMenu("View Old Messages"));
		conversationMenu.add(new JSeparator());
		conversationMenu.add(new JMenuItemDisabled("Add to Favorites"));
		conversationMenu.add(new JMenuItemDisabled("Add to List"));
		JMenuItem markAsUnreadMenuItem = new JMenuItemDisabled("Mark as Unread");
		markAsUnreadMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("control shift U"));
		conversationMenu.add(markAsUnreadMenuItem);
		JMenuItem hideConversationMenuItem = new JMenuItemDisabled(
				"Hide Conversation");
		hideConversationMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("control F4"));
		conversationMenu.add(hideConversationMenuItem);
		JMenuItem closeConversationMenuItem = new JMenuItemDisabled("Close");
		closeConversationMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("control W"));
		conversationMenu.add(closeConversationMenuItem);
		JMenu callMenu = new JMenu("Call");
		JMenuItem callMenuItem = new JMenuItemDisabled("Call");
		callMenuItem.setAccelerator(KeyStroke.getKeyStroke("control alt R"));
		callMenu.add(callMenuItem);
		JMenuItem videoCallMenuItem = new JMenuItemDisabled("Video Call");
		videoCallMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("shift control R"));
		callMenu.add(videoCallMenuItem);
		JMenuItem answerMenuItem = new JMenuItemDisabled("Answer");
		answerMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt PAGE_UP"));
		callMenu.add(answerMenuItem);
		callMenu.add(new JSeparator());
		callMenu.add(new JMenuItemDisabled("Ignore"));
		JMenuItem muteMicrophoneMenuItem = new JMenuItemDisabled(
				"Mute Microphone");
		muteMicrophoneMenuItem.setAccelerator(KeyStroke
				.getKeyStroke("control M"));
		callMenu.add(muteMicrophoneMenuItem);
		callMenu.add(new JMenuItemDisabled("Hold"));
		JMenuItem hangUpMenuItem = new JMenuItemDisabled("Hang up");
		hangUpMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt PAGE_DOWN"));
		callMenu.add(hangUpMenuItem);
		callMenu.add(new JSeparator());
		{
			JMenuItem callPhonesMenuItem = new JMenuItemDisabled("Call Phones");
			callPhonesMenuItem.setAccelerator(KeyStroke
					.getKeyStroke("control D"));
			callMenu.add(callPhonesMenuItem);
		}
		callMenu.add(new JSeparator());
		callMenu.add(new JMenuItemDisabled("Audio Settings..."));
		callMenu.add(new JMenu("Video"));
		callMenu.add(new JMenuItemDisabled("Share Screens..."));
		callMenu.add(new JMenuItemDisabled("Stop Sharing Screen"));
		callMenu.add(new JSeparator());
		callMenu.add(new JMenuItemDisabled("Learn about Call Quality"));
		JMenu viewMenu = new JMenu("View");
		JMenuItem contactsMenuItem = new JMenuItemDisabled("Contacts");
		contactsMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt 1"));
		viewMenu.add(contactsMenuItem);
		JMenuItem recentMenuItem = new JMenuItemDisabled("Recent");
		recentMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt 2"));
		viewMenu.add(recentMenuItem);
		viewMenu.add(new JSeparator());
		viewMenu.add(new JMenuItemDisabled("Compact Sidebar View"));
		viewMenu.add(new JSeparator());
		JMenuItem skypeHomeMenuItem = new JMenuItemDisabled("Skype Home");
		skypeHomeMenuItem.setAccelerator(KeyStroke.getKeyStroke("control H"));
		viewMenu.add(skypeHomeMenuItem);
		JMenuItem profileMenuItem = new JMenuItemDisabled("Profile");
		profileMenuItem.setAccelerator(KeyStroke.getKeyStroke("control I"));
		viewMenu.add(profileMenuItem);
		{
			JMenuItem callPhonesMenuItem = new JMenuItemDisabled("Call Phones");
			callPhonesMenuItem.setAccelerator(KeyStroke
					.getKeyStroke("control D"));
			viewMenu.add(callPhonesMenuItem);
		}
		viewMenu.add(new JMenuItemDisabled("Snapshots Gallery"));
		viewMenu.add(new JSeparator());
		viewMenu.add(new JMenuItemDisabled("Split Window View"));
		JMenuItem fullScreenMenuItem = new JMenuItemDisabled("Full Screen");
		fullScreenMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt ENTER"));
		viewMenu.add(fullScreenMenuItem);
		viewMenu.add(new JSeparator());
		viewMenu.add(new JMenuItemDisabled("Show Hidden Conversations"));
		JMenu toolsMenu = new JMenu("Tools");
		toolsMenu.add(new JMenuItemDisabled("Change Language"));
		toolsMenu.add(new JSeparator());
		toolsMenu.add(new JMenuItemDisabled("Accessibility"));
		toolsMenu.add(new JSeparator());
		toolsMenu.add(new JMenuItemDisabled("Skype WiFi..."));
		toolsMenu.add(new JSeparator());
		toolsMenu.add(new JMenuItemDisabled("Options..."));
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItemDisabled("Learn about Skype for Windows"));
		helpMenu.add(new JMenuItemDisabled("Go to Support"));
		helpMenu.add(new JMenuItemDisabled("Ask the Skype Community"));
		helpMenu.add(new JMenuItemDisabled("Heartbeat (Skype Status)"));
		helpMenu.add(new JSeparator());
		helpMenu.add(new JMenuItemDisabled("Call Quality Guide"));
		helpMenu.add(new JMenuItemDisabled("Check for Updates"));
		helpMenu.add(new JSeparator());
		helpMenu.add(new JMenuItemDisabled("Privacy Policy"));

		JMenuItem aboutSkype = new JMenuItem("About Skype");

		aboutSkype.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					AboutSkypeForm aboutSkypeForm = new AboutSkypeForm(frame);
					aboutSkypeForm.show();
				} catch (FontFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		helpMenu.add(aboutSkype);

		menuBar.add(skypeMenu);
		menuBar.add(contactsMenu);
		menuBar.add(conversationMenu);
		menuBar.add(callMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		/* Start left panel */

		/* Start left top panel */

		leftTopPanel.setBackground(new Color(245, 252, 254));

		JLayeredPane leftTopLayeredPane = this
				.createLeftTopLayeredPane(defaultLeftPanelWidth);

		/**
		 * This allows us to put a layered pane at x 0 y 0
		 * 
		 * Note that the layered pane by default has a null layout
		 * 
		 * That null layout allows us to set the bounds to start at x 0 y 0
		 * 
		 * If we do not put this code then there will be extra padding
		 * 
		 * That padding would interfere with the look of the program
		 */
		leftTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		leftTopPanel.add(leftTopLayeredPane);

		/* End left top panel */

		/* Start left bottom panel */

		/**
		 * Construct left bottom panel scroll pane using default width
		 */
		JPanel leftBottomPanel = this
				.createLeftBottomPanel(defaultLeftPanelWidth - scrollBarWidth);
		leftBottomScrollPane = new JScrollPane(new JPanel(),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		leftBottomScrollPane.setViewportView(leftBottomPanel);

		/**
		 * We speed up the scroll speed of the scroll pane
		 * 
		 * This will be customizable in the future in settings
		 */
		leftBottomScrollPane.getVerticalScrollBar().setUnitIncrement(
				this.scrollBarUnitIncrement);

		/**
		 * Resize the left bottom panel scroll pane to 0 height
		 * 
		 * We shall let the java runtime environment resize it for us
		 */
		leftBottomScrollPane.setPreferredSize(new Dimension(
				defaultLeftPanelWidth, 0));
		leftBottomScrollPane.setBorder(BorderFactory.createEmptyBorder());

		/* End left bottom panel */

		/**
		 * We create the final left panel split pane using the right components
		 * 
		 * The java runtime environment will resize the left split pane
		 */
		leftSplitPane = new JSplitPane(SwingConstants.HORIZONTAL, leftTopPanel,
				leftBottomScrollPane);

		/**
		 * We need to stop the user from resizing the left top panel
		 */
		int leftPanelSplitPaneDividerLocation = leftSplitPane
				.getDividerLocation();
		((BasicSplitPaneUI) leftSplitPane.getUI()).getDivider().setVisible(
				false);
		leftSplitPane.setBorder(BorderFactory.createEmptyBorder());
		leftSplitPane.addPropertyChangeListener("dividerLocation",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						((JSplitPane) e.getSource())
								.setDividerLocation(leftPanelSplitPaneDividerLocation);
					}
				});

		/* End left panel */

		/* Start right panel */

		/* Start right top panel */

		rightTopPanel.setOpaque(false);

		/**
		 * This allows us to put a layered pane at x 0 y 0
		 * 
		 * Note that the layered pane by default has a null layout
		 * 
		 * That null layout allows us to set the bounds to start at x 0 y 0
		 * 
		 * If we do not put this code then there will be extra padding
		 * 
		 * That padding would interfere with the look of the program
		 */
		rightTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLayeredPane rightTopLayeredPane = this.createRightTopLayeredPane(
				this.defaultWindowSize.width - this.splitPaneDividerSize
						- defaultLeftPanelWidth,
				this.defaultRightTopPanelHeight);

		rightTopPanel.add(rightTopLayeredPane);

		/* End right top panel */

		/* Start right bottom panel */

		rightBottomSplitPane.setOpaque(false);

		int rightBottomSplitPaneWidth = this.defaultWindowSize.width
				- this.splitPaneDividerSize - defaultLeftPanelWidth;

		JPanel rightBottomTopPanel = this.createRightBottomTopPanel(
				rightBottomSplitPaneWidth - this.scrollBarWidth - 5,
				RETAIN_SCROLL_POSITION);

		/**
		 * Resize the right bottom top panel scroll pane to 0 width 0 height
		 * 
		 * We shall let the java runtime environment resize it for us
		 */
		this.rightBottomTopPanel.setPreferredSize(new Dimension(0, 0));

		this.rightBottomTopPanel.setViewportView(rightBottomTopPanel);

		this.rightBottomTopPanel.setBorder(BorderFactory.createEmptyBorder());

		this.rightBottomTopPanel.getViewport().setOpaque(false);

		this.rightBottomTopPanel.setOpaque(false);

		/**
		 * We speed up the scroll speed of the scroll pane
		 * 
		 * This will be customizable in the future in settings
		 */
		this.rightBottomTopPanel.getVerticalScrollBar().setUnitIncrement(
				this.scrollBarUnitIncrement);

		JLayeredPane rightBottomBottomLayeredPane = this
				.createRightBottomBottomLayeredPane(rightBottomSplitPaneWidth,
						this.defaultRightBottomBottomPanelHeight);

		/**
		 * This allows us to put a layered pane at x 0 y 0
		 * 
		 * Note that the layered pane by default has a null layout
		 * 
		 * That null layout allows us to set the bounds to start at x 0 y 0
		 * 
		 * If we do not put this code then there will be extra padding
		 * 
		 * That padding would interfere with the look of the program
		 */
		rightBottomBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		rightBottomBottomPanel.setOpaque(false);

		rightBottomBottomPanel.add(rightBottomBottomLayeredPane);

		rightBottomSplitPane = new JSplitPane(SwingConstants.HORIZONTAL,
				this.rightBottomTopPanel, rightBottomBottomPanel);

		rightBottomSplitPane.setBorder(BorderFactory.createEmptyBorder());

		rightBottomSplitPane.setDividerLocation(this.defaultWindowSize.height
				- this.defaultRightBottomBottomPanelHeight
				- this.defaultRightTopPanelHeight);

		rightBottomSplitPane.setOpaque(false);

		/**
		 * We need to stop the user from resizing the right bottom panel
		 */
		((BasicSplitPaneUI) rightBottomSplitPane.getUI()).getDivider()
				.setVisible(false);

		rightBottomSplitPane.setBorder(BorderFactory.createEmptyBorder());

		/* End right bottom panel */

		/**
		 * Construct right split pane
		 */
		rightSplitPane = new JSplitPane(SwingConstants.HORIZONTAL,
				rightTopPanel, rightBottomSplitPane);

		rightSplitPane.setBorder(BorderFactory.createEmptyBorder());

		/**
		 * Resize the right split pane to be of 0 width and 0 height
		 * 
		 * We shall let the java runtime environment resize it for us
		 */
		rightSplitPane.setPreferredSize(new Dimension(0, 0));

		/* End right panel */

		/* Start split pane */
		/**
		 * Construct split pane to allow the user to resize
		 * 
		 * The java runtime environment will resize the right split pane
		 */
		splitPane = new JSplitPane(SwingConstants.VERTICAL, leftSplitPane,
				rightSplitPane);

		splitPane.setBorder(BorderFactory.createEmptyBorder());

		/* End split pane */

		/**
		 * We need to stop the user from resizing the right top panel
		 */
		int rightPanelSplitPaneDividerLocation = rightSplitPane
				.getDividerLocation();
		((BasicSplitPaneUI) rightSplitPane.getUI()).getDivider().setVisible(
				false);
		rightSplitPane.setBorder(BorderFactory.createEmptyBorder());
		rightSplitPane.addPropertyChangeListener("dividerLocation",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						((JSplitPane) e.getSource())
								.setDividerLocation(rightPanelSplitPaneDividerLocation);
					}
				});

		rightSplitPane.setOpaque(false);
		splitPane.setOpaque(false);

		setContentPane(splitPane);

		setBackground(Color.white);

		getContentPane().setPreferredSize(defaultWindowSize);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/**
		 * If focus is gained, check for placeholder and remove it
		 * 
		 * If focus is lost and text is empty, add back the placeholder
		 * 
		 * If the placeholder is not there it will select text on focus
		 */
		searchTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				if (searchTextField.getText().equals("Search")) {
					searchTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (searchTextField.getText().equals("")) {
					searchTextField.setText("Search");
				}
			}

		});

		Timer searchTextFieldTimer = new Timer(100, new ActionListener() {

			String lastText = "";

			Thread thread = null;

			boolean nyaa = false;

			@Override
			public void actionPerformed(ActionEvent evt) {
				((Timer) evt.getSource()).stop();
				if (thread != null) {
					thread.stop();
				}
				thread = new Thread(
						() -> {
							lastText = searchTextField.getText();
							searchTextFieldConversations.clear();
							if (searchTextField.getText().length() == 0
									|| searchTextField.getText().equals(
											"Search")) {
								if (!nyaa) {
									nyaa = true;
									leftBottomPanelPage = "RecentOlder";
									refreshLeftTopPanel();
									refreshLeftBottomPanel();
									searchTextField.grabFocus();
									searchTextField
											.setSelectionStart(searchTextField
													.getText().length());
								}
								return;
							}
							nyaa = false;
							List<String> matchedNames = new ArrayList<>();
							for (Conversation o : conversations) {
								if (o instanceof Contact) {
									Contact contact = (Contact) o;
									if (contact
											.getDisplayName()
											.toLowerCase()
											.contains(
													searchTextField.getText()
															.toLowerCase())) {
										searchTextFieldConversations.add(o);
										matchedNames.add(contact.getSkypeName());
										continue;
									}
								}
								if (o.getDisplayName()
										.toLowerCase()
										.contains(
												searchTextField.getText()
														.toLowerCase())) {
									searchTextFieldConversations.add(o);
									matchedNames.add(o.getSkypeName());
								}
							}
							for (String skypeName : registry) {
								if (skypeName.equals(loggedInUser.skypeName)) {
									continue;
								}
								if (matchedNames.contains(skypeName)) {
									continue;
								}
								UUID participantId = Skype.getPlugin()
										.getUniqueId(skypeName);
								Conversation conversation = new Conversation();
								conversation.setUniqueId(participantId);
								conversation.setDisplayName(skypeName);
								if (!skypeName.contains(searchTextField
										.getText())) {
									continue;
								}
								searchTextFieldConversations.add(conversation);
							}
							SwingUtilities.invokeLater(() -> {
								leftBottomPanelPage = "RecentOlder";
								refreshLeftTopPanel();
								refreshLeftBottomPanel();
								searchTextField.grabFocus();
								searchTextField
										.setSelectionStart(searchTextField
												.getText().length());
							});
						});
				thread.start();

			}

		});

		searchTextField.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent e) {
						valueChanged();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						valueChanged();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						valueChanged();
					}

					public void valueChanged() {
						searchTextFieldTimer.restart();
					}
				});

		/**
		 * Listen for if the user changed the divider location
		 * 
		 * If the divider location changed we need to resize some components
		 */
		splitPane.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent changeEvent) {
				String propertyName = changeEvent.getPropertyName();
				if (propertyName.equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
					if (changeEvent.getSource() instanceof JSplitPane) {
						JSplitPane source = ((JSplitPane) changeEvent
								.getSource());

						/**
						 * If the java runtime environment sets the divider
						 * location for the first time then this value is 0 when
						 * you check getLastDividerLocation() in this method
						 * 
						 * If the user changes the divider location for the
						 * first time then this value is -1 when you check
						 * getLastDividerLocation() in this method
						 * 
						 * So this code here is checking to see if the
						 * getLastDividerLocation() on the JScrollPane is 0 and
						 * if so then we know that the user did not initiate the
						 * divider change and we will not run the code beneath
						 * 
						 * This is for performance reasons when the window first
						 * opens, and also on Linux if we do not add this line
						 * of code then the user interface will be all screwed
						 * up when the program first runs for some reason
						 */
						if (source.getLastDividerLocation() != 0) {
							refreshWindow();
						}
					}
				}
			}
		});

		refreshTaskbarIcon();

		{

			timer1 = new Timer(60 * 1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					Optional<SocketHandlerContext> ctx = Skype.getPlugin()
							.createHandle();
					if (ctx.isPresent()) {
						Optional<PacketPlayInReply> reply = ctx
								.get()
								.getOutboundHandler()
								.dispatch(ctx.get(),
										new PacketPlayOutRefreshToken(authCode));
						if (!reply.isPresent()
								|| reply.get().getStatusCode() != 200) {
							logOut();
							return;
						}
					} else {
						logOut();
						return;
					}
				}

			});

			timer1.start();

		}

		{

			timer2 = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					for (IncomingCallForm incomingCallForm : incomingCallForms
							.toArray(new IncomingCallForm[0]).clone()) {
						long elapsed = System.currentTimeMillis()
								- incomingCallForm.startTime;
						if (elapsed > 8000) {
							incomingCallForm.dispatchEvent(new WindowEvent(
									incomingCallForm,
									WindowEvent.WINDOW_CLOSING));
							Optional<SocketHandlerContext> ctx2 = Skype
									.getPlugin().createHandle();
							if (!ctx2.isPresent()) {
								return;
							}
							UUID authCode = MainForm.get().getAuthCode();
							UUID callId = incomingCallForm.callId;
							ctx2.get()
									.getOutboundHandler()
									.write(ctx2.get(),
											new PacketPlayOutDeclineCallRequest(
													authCode, callId));
							incomingCallForms.remove(incomingCallForm);
						}
					}
					SimpleDateFormat df = new SimpleDateFormat("mm:ss");
					ongoingCallTimeLabel.setText(df.format(new Date(System
							.currentTimeMillis() - ongoingCallStartTime)));
				}

			});

			timer2.start();

		}

		{

			timer3 = new Timer(20 * 1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					Thread thread = new Thread(
							() -> {
								Optional<SocketHandlerContext> ctx = Skype
										.getPlugin().createHandle();
								if (ctx.isPresent()) {
									List<UUID> conversationIds = new ArrayList<>();
									for (Conversation conversation : conversations
											.toArray(new Conversation[0])
											.clone()) {
										if (conversation.getSkypeName().equals(
												"echo123")) {
											continue;
										}
										if (conversation instanceof Contact) {
											conversationIds.add(conversation
													.getUniqueId());
										}
									}
									conversationIds.add(loggedInUser
											.getUniqueId());
									{
										UUID[] conversationIds2 = conversationIds
												.toArray(new UUID[0]);
										PacketPlayOutLookupOnlineStatus onlineStatusLookup = new PacketPlayOutLookupOnlineStatus(
												authCode, conversationIds2);
										Optional<PacketPlayInReply> replyPacket2 = ctx
												.get()
												.getOutboundHandler()
												.dispatch(ctx.get(),
														onlineStatusLookup);
										if (!replyPacket2.isPresent()
												|| replyPacket2.get()
														.getStatusCode() != 200) {
											logOut();
											return;
										}
										String json = replyPacket2.get()
												.getText();
										if (conversationIds2.length == 1) {
											Status onlineStatus = Status
													.valueOf(replyPacket2.get()
															.getText());
											if (conversationIds2[0]
													.equals(loggedInUser
															.getUniqueId())) {
												if (onlineStatus == Status.OFFLINE) {
													logOut();
													return;
												}
											}
											Conversation conversation = lookupUser2(conversationIds2[0]);
											if (conversation instanceof Contact) {
												if (onlineStatus == Status.OFFLINE) {
													((Contact) conversation)
															.setOnlineStatus(Status.OFFLINE);
												}
											}
										} else {
											Gson gson = GsonBuilder.create();
											Map<String, String> onlineStatusLookupResponse = gson
													.fromJson(
															json,
															new TypeToken<HashMap<String, String>>() {
															}.getType());
											for (Map.Entry<String, String> entry : onlineStatusLookupResponse
													.entrySet()) {
												Status onlineStatus = entry
														.getValue().equals(
																"true") ? Status.ONLINE
														: Status.OFFLINE;
												if (UUID.fromString(
														entry.getKey()).equals(
														loggedInUser
																.getUniqueId())) {
													if (onlineStatus == Status.OFFLINE) {
														logOut();
														return;
													}
												}
												Conversation conversation = lookupUser2(UUID
														.fromString(entry
																.getKey()));
												if (conversation instanceof Contact) {
													if (onlineStatus == Status.OFFLINE) {
														((Contact) conversation)
																.setOnlineStatus(Status.OFFLINE);
													}
												}
											}
										}
									}
								} else {
									logOut();
									return;
								}
							});
					thread.start();
				}

			});

			timer3.start();

		}
	}

	public void logOut() {
		Skype.getPlugin().onDisable();
		if (!shown) {
			/*
			 * incoming data stream got closed
			 */
			logOut();
			try {
				timer1.stop();
				timer2.stop();
				timer3.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				mic.stop();
				mic.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.removeWindowListener(windowAdapter);
			frame.dispatchEvent(new WindowEvent(frame,
					WindowEvent.WINDOW_CLOSING));
			AudioIO.LOGOUT.playSound();
			LoginForm loginForm = new LoginForm();
			loginForm.show();
		}
	}

	private boolean shown = false, shown2 = false;

	@Override
	public void show() {
		super.show();

		if (shown2) {
			return;
		}

		Thread thread = new Thread(
				() -> {
					if (!shown) {
						readFromMemory();
					}
					SwingUtilities.invokeLater(() -> {
						MainForm.get().refreshWindow();
						if (Skype
								.getPlugin()
								.getConfig()
								.contains(
										loggedInUser.getSkypeName()
												+ ".sipserver")) {
							try {
								String sipserver = Skype
										.getPlugin()
										.getConfig()
										.getString(
												loggedInUser.getSkypeName()
														+ ".sipserver");
								String username = Skype
										.getPlugin()
										.getConfig()
										.getString(
												loggedInUser.getSkypeName()
														+ ".sipusername");
								String password = Skype
										.getPlugin()
										.getConfig()
										.getString(
												loggedInUser.getSkypeName()
														+ ".sippassword");
								VoIP.Runnable incomingMessageCallback = new VoIP.Runnable() {

									@Override
									public void run() {
										String peer = this.peer;
										String msg = this.msg;
										VoIPContact testvoip = new VoIPContact();
										testvoip.setSkypeName(peer);
										testvoip.setUniqueId(Skype
												.getPlugin()
												.getUniqueId(
														testvoip.getSkypeName()));
										testvoip.setDisplayName(testvoip
												.getSkypeName());
										testvoip.setLastModified(new Date(
												new Date().getTime()
														+ AppDelegate.TIME_OFFSET));
										boolean hit = false;
										for (Conversation conversation : MainForm
												.get().conversations) {
											if (conversation.getUniqueId()
													.equals(testvoip
															.getUniqueId())) {
												testvoip = (VoIPContact) conversation;
												hit = true;
												break;
											}
										}
										if (!hit) {
											MainForm.get().conversations
													.add(testvoip);
											MainForm.get()
													.saveVoipContactList();
										}
										UUID messageId = UUID.randomUUID();
										testvoip.setNotificationCount(testvoip
												.getNotificationCount() + 1);
										Message message = new Message(
												messageId, testvoip
														.getUniqueId(), msg,
												System.currentTimeMillis(),
												testvoip);
										testvoip.getMessages().add(message);
										NotificationForm notif = new NotificationForm(
												testvoip, message, true);
										notif.show();
										MainForm.get().refreshWindow();
									}
								};
								VoIP.getPlugin().API_Start(
										sipserver,
										username,
										password,
										new VoIP.Runnable() {

											@Override
											public void run() {
												String peer = this.peer;
												int line = this.line;
												VoIPContact testvoip = new VoIPContact();
												testvoip.setSkypeName(peer);
												testvoip.setUniqueId(Skype
														.getPlugin()
														.getUniqueId(
																testvoip.getSkypeName()));
												testvoip.setDisplayName(testvoip
														.getSkypeName());
												testvoip.setLastModified(new Date(
														new Date().getTime()
																+ AppDelegate.TIME_OFFSET));
												boolean hit = false;
												for (Conversation conversation : conversations) {
													if (conversation
															.getUniqueId()
															.equals(testvoip
																	.getUniqueId())) {
														testvoip = (VoIPContact) conversation;
														hit = true;
														break;
													}
												}
												if (!hit) {
													conversations.add(testvoip);
													saveVoipContactList();
												}
												IncomingVoIPCallForm form = new IncomingVoIPCallForm(
														testvoip, true, line);
												form.show();
											}
										}, incomingMessageCallback,
										new VoIP.Runnable() {

											@Override
											public void run() {
												for (Conversation conversation : conversations) {
													if (conversation instanceof VoIPContact) {
														((VoIPContact) conversation)
																.setContact((((VoIPContact) conversation)
																		.isContact()));
													}
												}
												refreshWindow();
											}
										}, new VoIP.Runnable() {

											@Override
											public void run() {
												for (Conversation conversation : conversations) {
													if (conversation instanceof VoIPContact) {
														((VoIPContact) conversation)
																.setContact((((VoIPContact) conversation)
																		.isContact()));
													}
												}
												isVoIPSetUp = true;
												refreshWindow();
											}
										});
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

						/**
						 * We need to refresh the top right and bottom right
						 * panels on resize
						 *
						 * We create and use a timer set for 100ms to refresh
						 * the panels
						 * 
						 * If the component is moved within those 100ms then the
						 * timer restarts
						 * 
						 * For best performance turn off
						 * "Show window contents while dragging"
						 * 
						 * You can do this by going to visual effects settings
						 * on Windows 7
						 * 
						 * This timer is just to improve performance for those
						 * who leave that on
						 */
						Timer timer = new Timer(100, new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent evt) {
								((Timer) evt.getSource()).stop();
								refreshWindow();
							}

						});

						addComponentListener(new ComponentAdapter() {
							@Override
							public void componentResized(final ComponentEvent e) {
								if (e.getComponent() instanceof JFrame) {
									super.componentResized(e);
									timer.restart();
								}
							}
						});
					});
				});

		thread.start();

		AudioIO.LOGIN.playSound();

		refreshWindow();

		shown2 = true;
	}
}
