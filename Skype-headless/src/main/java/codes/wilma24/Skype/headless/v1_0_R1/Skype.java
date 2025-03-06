/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.openpgp.PGPException
 *  org.bouncycastle.openpgp.PGPKeyRing
 *  org.bouncycastle.openpgp.PGPPublicKeyRing
 *  org.bouncycastle.openpgp.PGPSecretKeyRing
 *  org.pgpainless.PGPainless
 *  org.pgpainless.algorithm.DocumentSignatureType
 *  org.pgpainless.algorithm.KeyFlag
 *  org.pgpainless.decryption_verification.ConsumerOptions
 *  org.pgpainless.decryption_verification.DecryptionStream
 *  org.pgpainless.decryption_verification.MessageMetadata
 *  org.pgpainless.encryption_signing.EncryptionOptions
 *  org.pgpainless.encryption_signing.EncryptionStream
 *  org.pgpainless.encryption_signing.ProducerOptions
 *  org.pgpainless.encryption_signing.SigningOptions
 *  org.pgpainless.exception.KeyException
 *  org.pgpainless.key.generation.KeyRingBuilder
 *  org.pgpainless.key.generation.KeySpec
 *  org.pgpainless.key.generation.type.KeyType
 *  org.pgpainless.key.generation.type.ecc.EllipticCurve
 *  org.pgpainless.key.generation.type.ecc.ecdh.ECDH
 *  org.pgpainless.key.generation.type.ecc.ecdsa.ECDSA
 *  org.pgpainless.key.generation.type.rsa.RSA
 *  org.pgpainless.key.generation.type.rsa.RsaLength
 *  org.pgpainless.key.protection.SecretKeyRingProtector
 */
package codes.wilma24.Skype.headless.v1_0_R1;

import codes.wilma24.Skype.api.v1_0_R1.command.CommandExecutor;
import codes.wilma24.Skype.api.v1_0_R1.command.CommandMap;
import codes.wilma24.Skype.api.v1_0_R1.packet.Packet;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInPing;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInRemoveMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutAcceptContactRequest;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutEnteringListeningMode;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupUser;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutPong;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutPubKeyExchange;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRefreshToken;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutRegister;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutSendMessage;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutUpdateUser;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketType;
import codes.wilma24.Skype.api.v1_0_R1.plugin.event.EventHandler;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.headless.v1_0_R1.AppDelegate;
import codes.wilma24.Skype.headless.v1_0_R1.Conversation;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Contact;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Message;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.MessageType;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Status;
import codes.wilma24.Skype.headless.v1_0_R1.imgur.ImgurUploader;
import codes.wilma24.Skype.headless.v1_0_R1.litterbox.LitterboxUploader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.Timer;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyRing;
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
import org.pgpainless.key.generation.KeyRingBuilder;
import org.pgpainless.key.generation.KeySpec;
import org.pgpainless.key.generation.type.KeyType;
import org.pgpainless.key.generation.type.ecc.EllipticCurve;
import org.pgpainless.key.generation.type.ecc.ecdh.ECDH;
import org.pgpainless.key.generation.type.ecc.ecdsa.ECDSA;
import org.pgpainless.key.generation.type.rsa.RSA;
import org.pgpainless.key.generation.type.rsa.RsaLength;
import org.pgpainless.key.protection.SecretKeyRingProtector;

public class Skype {
    private Contact loggedInUser;
    private UUID authCode;
    public List<Conversation> conversations = new ArrayList<Conversation>();
    private HashMap<UUID, Conversation> cachedUsers = new HashMap();
    private PGPUtilities pgpUtilities = new PGPUtilities();
    private Timer timer1;
    private IncomingMessageCallback incomingMessageCallback;
    private DeletedMessageCallback deletedMessageCallback;
    private ConcurrentHashMap<String, Long> updateUserRateLimiterMap = new ConcurrentHashMap();
    private static Skype plugin = new Skype();
    private SocketHandlerContext handle;
    private ArrayList<SocketHandlerContext> handles = new ArrayList();
    private final String DEFAULT_HOSTNAME = "eu-frankfurt-1.wilma24.codes";
    private String hostname = "eu-frankfurt-1.wilma24.codes";
    private PGPSecretKeyRing privKey;
    private PGPPublicKeyRing pubKey;

    Skype() {
        try {
            this.setPrivKey(this.pgpUtilities.createOrLookupPrivateKey("private"));
            this.setPubKey(PGPainless.readKeyRing().publicKeyRing(this.pgpUtilities.createOrLookupPublicKey("private")));
        }
        catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (PGPException e) {
            e.printStackTrace();
        }
        this.timer1 = new Timer(60000, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
                if (ctx.isPresent()) {
                    Optional<PacketPlayInReply> reply = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutRefreshToken(Skype.this.authCode));
                    if (!reply.isPresent() || reply.get().getStatusCode() != 200) {
                        ((Timer)evt.getSource()).stop();
                        Skype.this.onDisable();
                        return;
                    }
                } else {
                    ((Timer)evt.getSource()).stop();
                    Skype.this.onDisable();
                    return;
                }
            }
        });
        CommandMap.register(PacketType.PING_IN, new CommandExecutor(){

            @Override
            public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
                PacketPlayInPing packet = Packet.fromJson(msg.toString(), PacketPlayInPing.class);
                Optional<SocketHandlerContext> ctx2 = Skype.getPlugin().createHandle();
                if (!ctx2.isPresent()) {
                    return PacketPlayInReply.empty();
                }
                ctx2.get().getOutboundHandler().write(ctx2.get(), new PacketPlayOutPong(Skype.this.authCode));
                return PacketPlayInReply.empty();
            }
        });
        CommandMap.register(PacketType.REMOVE_MESSAGE_IN, new CommandExecutor(){

            @Override
            public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
                Optional<SocketHandlerContext> ctx3;
                Optional<Conversation> userLookup;
                PacketPlayInRemoveMessage packet = Packet.fromJson(msg.toString(), PacketPlayInRemoveMessage.class);
                if (Skype.this.loggedInUser == null) {
                    boolean logging = true;
                    if (System.getProperty("logging") != null) {
                        logging = Boolean.parseBoolean(System.getProperty("logging"));
                    }
                    if (logging) {
                        System.out.println("REMOVE_MESSAGE_IN but user is not logged in!");
                    }
                    return PacketPlayInReply.empty();
                }
                UUID conversationId = packet.getConversationId();
                UUID participantId = packet.getParticipantId();
                UUID messageId = packet.getMessageId();
                if (packet.getPayload() == null) {
                    boolean logging = true;
                    if (System.getProperty("logging") != null) {
                        logging = Boolean.parseBoolean(System.getProperty("logging"));
                    }
                    if (logging) {
                        System.out.println("REMOVE_MESSAGE_IN but payload is empty!");
                    }
                    return PacketPlayInReply.empty();
                }
                String json = packet.getPayload().toString();
                Message message = new Message(json);
                Conversation sender = Skype.this.loggedInUser;
                if (!Skype.this.loggedInUser.getUniqueId().equals(message.sender) && (userLookup = Skype.this.lookupUser(message.sender)).isPresent()) {
                    sender = userLookup.get();
                }
                message.setMessage(message.getDecryptedMessage(Skype.this.pgpUtilities, Skype.this.loggedInUser, sender));
                if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST && (ctx3 = Skype.getPlugin().createHandle()).isPresent()) {
                    PacketPlayOutAcceptContactRequest msg2 = new PacketPlayOutAcceptContactRequest(Skype.this.authCode, conversationId);
                    ctx3.get().getOutboundHandler().write(ctx3.get(), msg2);
                }
                boolean hit = false;
                Conversation _conversation = null;
                for (Conversation conversation : Skype.this.conversations) {
                    if (!conversation.getUniqueId().equals(conversationId)) continue;
                    message.setConversation(conversation);
                    boolean hit2 = false;
                    for (Message message2 : (Message[])conversation.getMessages().toArray(new Message[0]).clone()) {
                        if (!message2.getUniqueId().equals(message.getUniqueId())) continue;
                        message2.setMessage(message.getMessage());
                        message2.setDecryptedMessage(null);
                        hit2 = true;
                        break;
                    }
                    if (!hit2) {
                        conversation.getMessages().add(message);
                    }
                    conversation.setNotificationCount(conversation.getNotificationCount() + 1);
                    conversation.setLastModified(new Date(new Date().getTime() + AppDelegate.TIME_OFFSET));
                    if (message.getMessageType() != null) {
                        if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
                            if (!message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(true, message);
                            } else {
                                conversation.setHasOutgoingFriendRequest(true);
                            }
                        }
                        if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST || message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
                            if (message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(false, null);
                            } else {
                                conversation.setHasOutgoingFriendRequest(false);
                            }
                        }
                    }
                    _conversation = conversation;
                    hit = true;
                }
                if (!hit) {
                    Optional<SocketHandlerContext> socketHandlerContext = Skype.getPlugin().createHandle();
                    if (!socketHandlerContext.isPresent()) {
                        return PacketPlayInReply.empty();
                    }
                    Optional<PacketPlayInReply> replyPacket = socketHandlerContext.get().getOutboundHandler().dispatch(socketHandlerContext.get(), new PacketPlayOutLookupUser(Skype.this.authCode, conversationId));
                    if (!replyPacket.isPresent()) {
                        return PacketPlayInReply.empty();
                    }
                    Conversation conversation = new Conversation(replyPacket.get().getText());
                    if (conversation.isGroupChat()) {
                        message.setConversation(conversation);
                    } else {
                        conversation.setDisplayName(conversation.getSkypeName());
                    }
                    conversation.getMessages().add(message);
                    conversation.setNotificationCount(1);
                    conversation.setLastModified(new Date(new Date().getTime() + AppDelegate.TIME_OFFSET));
                    if (message.getMessageType() != null) {
                        if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
                            if (!message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(true, message);
                            } else {
                                conversation.setHasOutgoingFriendRequest(true);
                            }
                        }
                        if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST || message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
                            if (message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(false, null);
                            } else {
                                conversation.setHasOutgoingFriendRequest(false);
                            }
                        }
                    }
                    _conversation = conversation;
                    Skype.this.conversations.add(conversation);
                }
                System.out.println(message);
                if (message.sender.equals(Skype.this.loggedInUser.getUniqueId())) {
                    return PacketPlayInReply.empty();
                }
                if (Skype.this.deletedMessageCallback != null) {
                    Skype.this.deletedMessageCallback.onMsgDelete(_conversation, sender, message);
                }
                return PacketPlayInReply.empty();
            }
        });
        CommandMap.register(PacketType.MESSAGE_IN, new CommandExecutor(){

            @Override
            public PacketPlayInReply onCommand(SocketHandlerContext ctx, Object msg) {
                Optional<SocketHandlerContext> ctx3;
                Optional<Conversation> userLookup;
                PacketPlayInMessage packet = Packet.fromJson(msg.toString(), PacketPlayInMessage.class);
                if (Skype.this.loggedInUser == null) {
                    boolean logging = true;
                    if (System.getProperty("logging") != null) {
                        logging = Boolean.parseBoolean(System.getProperty("logging"));
                    }
                    if (logging) {
                        System.out.println("MESSAGE_IN but user is not logged in!");
                    }
                    return PacketPlayInReply.empty();
                }
                UUID conversationId = packet.getConversationId();
                UUID participantId = packet.getParticipantId();
                String json = packet.getPayload().toString();
                Message message = new Message(json);
                Conversation sender = Skype.this.loggedInUser;
                if (!Skype.this.loggedInUser.getUniqueId().equals(message.sender) && (userLookup = Skype.this.lookupUser(message.sender)).isPresent()) {
                    sender = userLookup.get();
                }
                message.setMessage(message.getDecryptedMessage(Skype.this.pgpUtilities, Skype.this.loggedInUser, sender));
                if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST && (ctx3 = Skype.getPlugin().createHandle()).isPresent()) {
                    PacketPlayOutAcceptContactRequest msg2 = new PacketPlayOutAcceptContactRequest(Skype.this.authCode, conversationId);
                    ctx3.get().getOutboundHandler().write(ctx3.get(), msg2);
                }
                boolean hit = false;
                Conversation _conversation = null;
                for (Conversation conversation : Skype.this.conversations) {
                    if (!conversation.getUniqueId().equals(conversationId)) continue;
                    message.setConversation(conversation);
                    boolean hit2 = false;
                    for (Message message2 : (Message[])conversation.getMessages().toArray(new Message[0]).clone()) {
                        if (!message2.getUniqueId().equals(message.getUniqueId())) continue;
                        message2.setMessage(message.getMessage());
                        message2.setDecryptedMessage(null);
                        hit2 = true;
                        break;
                    }
                    if (!hit2) {
                        conversation.getMessages().add(message);
                    }
                    conversation.setNotificationCount(conversation.getNotificationCount() + 1);
                    conversation.setLastModified(new Date(new Date().getTime() + AppDelegate.TIME_OFFSET));
                    if (message.getMessageType() != null) {
                        if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
                            if (!message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(true, message);
                            } else {
                                conversation.setHasOutgoingFriendRequest(true);
                            }
                        }
                        if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST || message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
                            if (message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(false, null);
                            } else {
                                conversation.setHasOutgoingFriendRequest(false);
                            }
                        }
                    }
                    _conversation = conversation;
                    hit = true;
                }
                if (!hit) {
                    Optional<SocketHandlerContext> socketHandlerContext = Skype.getPlugin().createHandle();
                    if (!socketHandlerContext.isPresent()) {
                        return PacketPlayInReply.empty();
                    }
                    Optional<PacketPlayInReply> replyPacket = socketHandlerContext.get().getOutboundHandler().dispatch(socketHandlerContext.get(), new PacketPlayOutLookupUser(Skype.this.authCode, conversationId));
                    if (!replyPacket.isPresent()) {
                        return PacketPlayInReply.empty();
                    }
                    Conversation conversation = new Conversation(replyPacket.get().getText());
                    if (conversation.isGroupChat()) {
                        message.setConversation(conversation);
                    } else {
                        conversation.setDisplayName(conversation.getSkypeName());
                    }
                    conversation.getMessages().add(message);
                    conversation.setNotificationCount(1);
                    conversation.setLastModified(new Date(new Date().getTime() + AppDelegate.TIME_OFFSET));
                    if (message.getMessageType() != null) {
                        if (message.getMessageType() == MessageType.SEND_FRIEND_REQUEST) {
                            if (!message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(true, message);
                            } else {
                                conversation.setHasOutgoingFriendRequest(true);
                            }
                        }
                        if (message.getMessageType() == MessageType.DECLINE_FRIEND_REQUEST || message.getMessageType() == MessageType.ACCEPT_FRIEND_REQUEST) {
                            if (message.getSender().equals(Skype.this.loggedInUser.getUniqueId())) {
                                conversation.setHasIncomingFriendRequest(false, null);
                            } else {
                                conversation.setHasOutgoingFriendRequest(false);
                            }
                        }
                    }
                    _conversation = conversation;
                    Skype.this.conversations.add(conversation);
                }
                System.out.println(message);
                if (message.sender.equals(Skype.this.loggedInUser.getUniqueId())) {
                    return PacketPlayInReply.empty();
                }
                if (Skype.this.incomingMessageCallback != null) {
                    Skype.this.incomingMessageCallback.onMsgSend(_conversation, sender, message);
                }
                return PacketPlayInReply.empty();
            }
        });
    }

    public void addIncomingMessageCallback(IncomingMessageCallback callback) {
        this.incomingMessageCallback = callback;
    }

    public void addDeletedMessageCallback(DeletedMessageCallback callback) {
        this.deletedMessageCallback = callback;
    }

    public Optional<PacketPlayInLogin> login(String skypeName, String password) {
        block12: {
            try {
                Optional<SocketHandlerContext> ctx3;
                SocketHandlerContext ctx = Skype.getPlugin().getHandle();
                UUID participantId = Skype.getPlugin().getUniqueId(skypeName);
                UUID authCode = null;
                PGPPublicKeyRing pubKey = null;
                Optional<PacketPlayInReply> replyPacket = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutPubKeyExchange(Skype.getPlugin().getPubKey()));
                boolean logging = true;
                if (System.getProperty("logging") != null) {
                    logging = Boolean.parseBoolean(System.getProperty("logging"));
                }
                if (logging) {
                    System.out.println(replyPacket);
                }
                if (replyPacket.isPresent() && replyPacket.get().getStatusCode() == 200) {
                    pubKey = PGPainless.readKeyRing().publicKeyRing(replyPacket.get().getText());
                }
                String fpassword = this.pgpUtilities.encryptAndSign(password, Skype.getPlugin().getPrivKey(), pubKey);
                Optional<PacketPlayInReply> replyPacket2 = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutLogin(skypeName, fpassword));
                if (!replyPacket2.isPresent() || replyPacket2.get().getStatusCode() != 200) break block12;
                String text = null;
                try {
                    text = this.pgpUtilities.decryptAndVerify(replyPacket2.get().getText(), Skype.getPlugin().getPrivKey(), pubKey).getMessage();
                }
                catch (PGPException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                PacketPlayInLogin loginPacket = Packet.fromJson(text, PacketPlayInLogin.class);
                authCode = UUID.fromString(loginPacket.getAuthCode());
                ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutRefreshToken(authCode));
                replyPacket2 = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutLookupUser(authCode, participantId));
                if (!replyPacket2.isPresent() || replyPacket2.get().getStatusCode() != 200) break block12;
                Contact loggedInUser = new Contact(replyPacket2.get().getText());
                String pubKey2 = this.pgpUtilities.createOrLookupPublicKey(skypeName);
                loggedInUser.setPubKey(pubKey2);
                SocketHandlerContext ctx2 = Skype.getPlugin().getHandle();
                loggedInUser.setOnlineStatus(Status.ONLINE);
                PacketPlayOutEnteringListeningMode msg = new PacketPlayOutEnteringListeningMode(authCode);
                ctx2.getOutboundHandler().dispatch(ctx2, msg);
                ctx2.fireOutboundHandlerInactive();
                ctx2.fireInboundHandlerActive(new Runnable(){

                    @Override
                    public void run() {
                    }
                });
                if (this.testRateLimitUpdateUser(loggedInUser.getSkypeName())) {
                    long timestamp = Long.parseLong(this.updateUserRateLimiterMap.getOrDefault(loggedInUser.getSkypeName(), 0L) + "");
                    long diff = System.currentTimeMillis() - timestamp;
                    diff = 2000L - diff;
                    try {
                        Thread.sleep(diff);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!(ctx3 = Skype.getPlugin().createHandle()).isPresent()) {
                    return Optional.empty();
                }
                PacketPlayOutUpdateUser msg2 = new PacketPlayOutUpdateUser(authCode, loggedInUser.getUniqueId(), loggedInUser);
                ctx3.get().getOutboundHandler().write(ctx3.get(), msg2);
                this.loggedInUser = loggedInUser;
                this.authCode = UUID.fromString(loginPacket.getAuthCode());
                this.timer1.restart();
                return Optional.of(loginPacket);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public boolean register(String skypeName, String password) {
        return this.register(skypeName, skypeName, password);
    }

    public boolean register(String fullName, String skypeName, String password) {
        boolean logging;
        Optional<PacketPlayInReply> replyPacket;
        SocketHandlerContext ctx = Skype.getPlugin().getHandle();
        UUID participantId = Skype.getPlugin().getUniqueId(skypeName);
        UUID authCode = null;
        PGPPublicKeyRing pubKey = null;
        String fpassword = null;
        try {
            replyPacket = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutPubKeyExchange(Skype.getPlugin().getPubKey()));
            logging = true;
            if (System.getProperty("logging") != null) {
                logging = Boolean.parseBoolean(System.getProperty("logging"));
            }
            if (logging) {
                System.out.println(replyPacket);
            }
            if (replyPacket.isPresent() && replyPacket.get().getStatusCode() == 200) {
                pubKey = PGPainless.readKeyRing().publicKeyRing(replyPacket.get().getText());
            }
            fpassword = this.pgpUtilities.encryptAndSign(password, Skype.getPlugin().getPrivKey(), pubKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        replyPacket = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutRegister(fullName, skypeName, fpassword));
        if (replyPacket.isPresent()) {
            if (replyPacket.get().getStatusCode() == 200) {
                authCode = UUID.fromString(replyPacket.get().getText());
            } else {
                if (replyPacket.get().getText().equals("User is already registered.")) {
                    return false;
                }
                return false;
            }
        }
        replyPacket = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutRefreshToken(authCode));
        logging = true;
        if (System.getProperty("logging") != null) {
            logging = Boolean.parseBoolean(System.getProperty("logging"));
        }
        if (logging) {
            System.out.println(replyPacket);
        }
        Contact contact = new Contact();
        contact.setUniqueId(participantId);
        contact.setSkypeName(skypeName);
        contact.setDisplayName(fullName);
        try {
            Thread.sleep(2100L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        replyPacket = ctx.getOutboundHandler().dispatch(ctx, new PacketPlayOutUpdateUser(authCode, participantId, contact));
        if (logging) {
            System.out.println(replyPacket);
        }
        contact = new Contact(contact.toString());
        return true;
    }

    public Optional<Conversation> lookupUser(UUID participantId) {
        if (this.loggedInUser == null) {
            return Optional.empty();
        }
        for (Conversation conversation : this.conversations) {
            if (!conversation.getUniqueId().equals(participantId)) continue;
            return Optional.of(conversation);
        }
        if (this.cachedUsers.containsKey(participantId)) {
            return Optional.of(this.cachedUsers.get(participantId));
        }
        Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
        if (!ctx.isPresent()) {
            return Optional.empty();
        }
        Optional<PacketPlayInReply> replyPacket = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutLogin(this.authCode));
        UUID authCode = UUID.fromString(replyPacket.get().getText());
        replyPacket = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutLookupUser(authCode, participantId));
        if (!replyPacket.isPresent()) {
            return Optional.empty();
        }
        Conversation conversation = new Conversation(replyPacket.get().getText());
        if (!conversation.isGroupChat()) {
            conversation.setDisplayName(conversation.getSkypeName());
        }
        this.cachedUsers.put(participantId, conversation);
        return Optional.of(conversation);
    }

    public boolean setProfilePic(File imageFile) {
        if (this.loggedInUser == null) {
            return false;
        }
        ImgurUploader imgurUploader = new ImgurUploader();
        File pathToData = imageFile;
        Optional<String> url = imgurUploader.uploadFile(pathToData);
        if (!url.isPresent()) {
            return false;
        }
        this.loggedInUser.setImageIconUrl(url.get());
        return this.saveUser();
    }

    public boolean setImageIcon(File imageFile) {
        return this.setProfilePic(imageFile);
    }

    public boolean setProfilePic(InputStream inputStream) throws IOException {
        int r;
        if (this.loggedInUser == null) {
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        while ((r = inputStream.read(b)) != -1) {
            baos.write(b, 0, r);
            baos.flush();
        }
        byte[] imageData = baos.toByteArray();
        baos.close();
        ImgurUploader imgurUploader = new ImgurUploader();
        Optional<String> url = imgurUploader.uploadImg(imageData);
        if (!url.isPresent()) {
            return false;
        }
        this.loggedInUser.setImageIconUrl(url.get());
        return this.saveUser();
    }

    public boolean setImageIcon(InputStream inputStream) throws IOException {
        return this.setProfilePic(inputStream);
    }

    public boolean sendImage(String skypeName, File imageFile) {
        return this.sendImage(skypeName, imageFile, ImageHost.IMGUR);
    }

    public boolean sendImage(String skypeName, File imageFile, ImageHost imageHost) {
        Optional<String> url;
        if (this.loggedInUser == null) {
            return false;
        }
        File pathToData = imageFile;
        if (imageHost == ImageHost.IMGUR) {
            ImgurUploader imgurUploader = new ImgurUploader();
            url = imgurUploader.uploadFile(pathToData);
        } else if (imageHost == ImageHost.LITTERBOX) {
            LitterboxUploader litterboxUploader = new LitterboxUploader();
            url = litterboxUploader.uploadFile(pathToData);
        } else {
            return false;
        }
        if (!url.isPresent()) {
            return false;
        }
        return this.sendMessage(skypeName, "<img src=\"" + url.get() + "\" />");
    }

    public boolean sendImage(String skypeName, InputStream inputStream) throws IOException {
        return this.sendImage(skypeName, inputStream, ImageHost.IMGUR);
    }

    public boolean sendImage(String skypeName, InputStream inputStream, ImageHost imageHost) throws IOException {
        Optional<String> url;
        int r;
        if (this.loggedInUser == null) {
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        while ((r = inputStream.read(b)) != -1) {
            baos.write(b, 0, r);
            baos.flush();
        }
        byte[] imageData = baos.toByteArray();
        baos.close();
        if (imageHost == ImageHost.IMGUR) {
            ImgurUploader imgurUploader = new ImgurUploader();
            url = imgurUploader.uploadImg(imageData);
        } else if (imageHost == ImageHost.LITTERBOX) {
            LitterboxUploader litterboxUploader = new LitterboxUploader();
            url = litterboxUploader.uploadImg(imageData);
        } else {
            return false;
        }
        if (!url.isPresent()) {
            return false;
        }
        return this.sendMessage(skypeName, "<img src=\"" + url.get() + "\" />");
    }

    public boolean sendVideo(String skypeName, File videoFile) {
        if (this.loggedInUser == null) {
            return false;
        }
        LitterboxUploader litterboxUploader = new LitterboxUploader();
        File pathToData = videoFile;
        Optional<String> url = litterboxUploader.uploadFile(pathToData);
        if (!url.isPresent()) {
            return false;
        }
        return this.sendMessage(skypeName, "<video src=\"" + url.get() + "\" />");
    }

    @Deprecated
    public boolean sendVideo(String skypeName, InputStream inputStream) throws IOException {
        return this.sendVideo(skypeName, inputStream, "mp4");
    }

    public boolean sendVideo(String skypeName, InputStream inputStream, String fileExt) throws IOException {
        int r;
        if (this.loggedInUser == null) {
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        while ((r = inputStream.read(b)) != -1) {
            baos.write(b, 0, r);
            baos.flush();
        }
        byte[] imageData = baos.toByteArray();
        baos.close();
        LitterboxUploader litterboxUploader = new LitterboxUploader();
        Optional<String> url = litterboxUploader.uploadData(imageData, fileExt);
        if (!url.isPresent()) {
            return false;
        }
        return this.sendMessage(skypeName, "<video src=\"" + url.get() + "\" />");
    }

    public boolean sendMessage(Conversation conversation, String msg) {
        return this.sendMessage(conversation.getSkypeName(), msg);
    }

    public boolean sendMessage(String skypeName, String msg) {
        if (this.loggedInUser == null) {
            return false;
        }
        UUID participantId = Skype.getPlugin().getUniqueId(skypeName);
        Optional<Conversation> conversation = this.lookupUser(participantId);
        if (!conversation.isPresent()) {
            return false;
        }
        Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
        if (!ctx.isPresent()) {
            return false;
        }
        String txt = this.pgpUtilities.encryptAndSign(msg, this.loggedInUser, conversation.get());
        UUID messageId = UUID.randomUUID();
        long timestamp = new Date(new Date().getTime() + AppDelegate.TIME_OFFSET).getTime();
        Message message = new Message(messageId, this.loggedInUser.getUniqueId(), txt, timestamp, conversation.get());
        if (!this.conversations.contains(conversation.get())) {
            this.conversations.add(conversation.get());
        }
        UUID conversationId = conversation.get().getUniqueId();
        Optional<PacketPlayInReply> replyPacket = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutSendMessage(this.authCode, conversationId, message.getUniqueId(), message.toString(), message.getTimestamp()));
        if (!replyPacket.isPresent()) {
            return false;
        }
        if (replyPacket.get().getStatusCode() != 200) {
            return false;
        }
        if (!conversation.get().isGroupChat()) {
            conversation.get().getMessages().add(message);
        }
        conversation.get().setLastModified(new Date(new Date().getTime() + AppDelegate.TIME_OFFSET));
        boolean logging = true;
        if (System.getProperty("logging") != null) {
            logging = Boolean.parseBoolean(System.getProperty("logging"));
        }
        if (logging) {
            System.out.println(message);
        }
        return true;
    }

    public Contact getUser() {
        return this.loggedInUser;
    }

    public Contact getLoggedInUser() {
        return this.getUser();
    }

    public boolean saveUser() {
        Optional<SocketHandlerContext> ctx3;
        if (this.loggedInUser == null) {
            return false;
        }
        if (this.testRateLimitUpdateUser(this.loggedInUser.getSkypeName())) {
            long timestamp = Long.parseLong(this.updateUserRateLimiterMap.getOrDefault(this.loggedInUser.getSkypeName(), 0L) + "");
            long diff = System.currentTimeMillis() - timestamp;
            diff = 2000L - diff;
            try {
                Thread.sleep(diff);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!(ctx3 = Skype.getPlugin().createHandle()).isPresent()) {
            return false;
        }
        PacketPlayOutUpdateUser msg = new PacketPlayOutUpdateUser(this.authCode, this.loggedInUser.getUniqueId(), this.loggedInUser);
        Optional<PacketPlayInReply> replyPacket = ctx3.get().getOutboundHandler().dispatch(ctx3.get(), msg);
        if (!replyPacket.isPresent()) {
            return false;
        }
        return replyPacket.get().getStatusCode() == 200;
    }

    public boolean saveLoggedInUser() {
        return this.saveUser();
    }

    public boolean testRateLimitUpdateUser(String skypeName) {
        long timestamp = Long.parseLong(this.updateUserRateLimiterMap.getOrDefault(skypeName, 0L) + "");
        this.updateUserRateLimiterMap.put(skypeName, System.currentTimeMillis());
        return System.currentTimeMillis() - timestamp < 2000L;
    }

    static Skype getPlugin() {
        return plugin;
    }

    public String getDefaultHostname() {
        return "eu-frankfurt-1.wilma24.codes";
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Optional<SocketHandlerContext> createHandle() {
        try {
            Socket socket = new Socket(this.hostname, 28109);
            SocketHandlerContext handle = new SocketHandlerContext(socket);
            this.handles.add(handle);
            return Optional.of(handle);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public SocketHandlerContext getHandle() {
        Optional<SocketHandlerContext> handle;
        if (this.handle == null && (handle = this.createHandle()).isPresent()) {
            this.handle = handle.get();
        }
        return this.handle;
    }

    public void setHandle(SocketHandlerContext ctx) {
        this.handle = ctx;
    }

    public UUID getUniqueId(String skypeName) {
        return UUID.nameUUIDFromBytes(("skype:" + skypeName).getBytes());
    }

    @EventHandler
    public void onEnable() {
        this.createHandle();
    }

    @EventHandler
    public void onDisable() {
        this.timer1.stop();
        for (SocketHandlerContext ctx : (SocketHandlerContext[])this.handles.toArray(new SocketHandlerContext[0]).clone()) {
            try {
                ctx.fireSocketInactive();
                ctx.getSocket().close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.handles.clear();
        this.authCode = null;
        this.loggedInUser = null;
        this.conversations.clear();
        this.cachedUsers.clear();
    }

    public void logout() {
        this.onDisable();
    }

    public PGPPublicKeyRing getPubKey() {
        return this.pubKey;
    }

    public void setPubKey(PGPPublicKeyRing pubKey) {
        this.pubKey = pubKey;
    }

    public PGPSecretKeyRing getPrivKey() {
        return this.privKey;
    }

    public void setPrivKey(PGPSecretKeyRing privKey) {
        this.privKey = privKey;
    }

    public class PGPUtilities {
        private File getUserDir() {
            String OS = System.getProperty("os.name").toUpperCase();
            String workingDirectory = OS.contains("WIN") ? System.getenv("AppData") : System.getProperty("user.home");
            File userDir = new File(workingDirectory, "Skype");
            userDir.mkdirs();
            return userDir;
        }

        private File getUserFile(String fileName) {
            return new File(this.getUserDir(), fileName);
        }

        public PGPSecretKeyRing createOrLookupPrivateKey(String skypeName) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, PGPException {
            File privateKeyFile = this.getUserFile(skypeName + ".key");
            if (!privateKeyFile.exists()) {
                PGPSecretKeyRing privateKey = ((KeyRingBuilder)((KeyRingBuilder)((KeyRingBuilder)PGPainless.buildKeyRing().setPrimaryKey(KeySpec.getBuilder((KeyType)RSA.withLength((RsaLength)RsaLength._4096), (KeyFlag)KeyFlag.SIGN_DATA, (KeyFlag[])new KeyFlag[]{KeyFlag.CERTIFY_OTHER}))).addSubkey(KeySpec.getBuilder((KeyType)ECDSA.fromCurve((EllipticCurve)EllipticCurve._P256), (KeyFlag)KeyFlag.SIGN_DATA, (KeyFlag[])new KeyFlag[0]))).addSubkey(KeySpec.getBuilder((KeyType)ECDH.fromCurve((EllipticCurve)EllipticCurve._P256), (KeyFlag)KeyFlag.ENCRYPT_COMMS, (KeyFlag[])new KeyFlag[]{KeyFlag.ENCRYPT_STORAGE}))).addUserId(skypeName + " <" + skypeName + "@hookipa.net>").build();
                String privateKeyArmoured = PGPainless.asciiArmor((PGPKeyRing)privateKey);
                FileOutputStream fos = new FileOutputStream(privateKeyFile);
                fos.write(privateKeyArmoured.getBytes());
                fos.flush();
                fos.close();
                boolean logging = true;
                if (System.getProperty("logging") != null) {
                    logging = Boolean.parseBoolean(System.getProperty("logging"));
                }
                if (logging) {
                    System.out.println(privateKeyArmoured);
                }
            }
            return PGPainless.readKeyRing().secretKeyRing(new String(Files.readAllBytes(privateKeyFile.toPath())));
        }

        public String createOrLookupPublicKey(String skypeName) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, PGPException {
            PGPSecretKeyRing secretKey = this.createOrLookupPrivateKey(skypeName);
            PGPPublicKeyRing secretKeyPubKey = PGPainless.extractCertificate((PGPSecretKeyRing)secretKey);
            String publicKeyArmoured = PGPainless.asciiArmor((PGPKeyRing)secretKeyPubKey);
            boolean logging = true;
            if (System.getProperty("logging") != null) {
                logging = Boolean.parseBoolean(System.getProperty("logging"));
            }
            if (logging) {
                System.out.println(publicKeyArmoured);
            }
            return publicKeyArmoured;
        }

        public String encryptAndSign(String arg0, Contact loggedInUser, Conversation arg1) {
            try {
                PGPSecretKeyRing secretKey = this.createOrLookupPrivateKey(loggedInUser.getSkypeName());
                if (arg1.isGroupChat()) {
                    ArrayList<PGPPublicKeyRing> pubKeys = new ArrayList<PGPPublicKeyRing>();
                    for (UUID participantId : arg1.getParticipants(Skype.this.authCode)) {
                        Optional<PGPPublicKeyRing> pubKey;
                        Optional<Conversation> userLookup = Skype.this.lookupUser(participantId);
                        if (!userLookup.isPresent() || !(pubKey = userLookup.get().getPubKey()).isPresent()) continue;
                        pubKeys.add(pubKey.get());
                    }
                    if (pubKeys.size() > 0) {
                        return this.encryptAndSign(arg0, secretKey, pubKeys.toArray(new PGPPublicKeyRing[0]));
                    }
                } else {
                    Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
                    if (pubKey.isPresent()) {
                        return this.encryptAndSign(arg0, secretKey, pubKey.get());
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return arg0;
        }

        public DecryptionResult decryptAndVerify(String arg0, Contact loggedInUser, Conversation arg1) {
            try {
                PGPSecretKeyRing secretKey = this.createOrLookupPrivateKey(loggedInUser.getSkypeName());
                Optional<PGPPublicKeyRing> pubKey = arg1.getPubKey();
                if (pubKey.isPresent()) {
                    return this.decryptAndVerify(arg0, secretKey, pubKey.get());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return new DecryptionResult(arg0, false, false);
        }

        public String encryptAndSign(String arg0, PGPSecretKeyRing secretKey, PGPPublicKeyRing ... pubKeys) throws KeyException, PGPException, IOException {
            PGPPublicKeyRing secretKeyPubKey = PGPainless.extractCertificate((PGPSecretKeyRing)secretKey);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            EncryptionOptions options = new EncryptionOptions().addRecipient(secretKeyPubKey);
            for (PGPPublicKeyRing pubKey : pubKeys) {
                options.addRecipient(pubKey);
            }
            EncryptionStream encryptionStream = PGPainless.encryptAndOrSign().onOutputStream((OutputStream)baos).withOptions(ProducerOptions.signAndEncrypt((EncryptionOptions)options, (SigningOptions)new SigningOptions().addInlineSignature(SecretKeyRingProtector.unprotectedKeys(), secretKey, DocumentSignatureType.CANONICAL_TEXT_DOCUMENT)).setAsciiArmor(true));
            encryptionStream.write(arg0.getBytes());
            encryptionStream.close();
            return new String(baos.toByteArray());
        }

        public DecryptionResult decryptAndVerify(String arg0, PGPSecretKeyRing secretKey, PGPPublicKeyRing pubKey) throws PGPException, IOException {
            int bytesRead;
            ByteArrayInputStream bais = new ByteArrayInputStream(arg0.getBytes());
            ConsumerOptions options = new ConsumerOptions().addDecryptionKey(secretKey, SecretKeyRingProtector.unprotectedKeys());
            options.addVerificationCert(pubKey);
            DecryptionStream decryptionStream = PGPainless.decryptAndOrVerify().onInputStream((InputStream)bais).withOptions(options);
            byte[] b = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((bytesRead = decryptionStream.read(b)) != -1) {
                sb.append(new String(Arrays.copyOf(b, bytesRead)));
            }
            decryptionStream.close();
            MessageMetadata metadata = decryptionStream.getMetadata();
            if (metadata.isVerifiedSignedBy((PGPKeyRing)pubKey)) {
                return new DecryptionResult(sb.toString(), true, true);
            }
            return new DecryptionResult(sb.toString(), true, false);
        }

        public class DecryptionResult {
            private String message;
            private boolean successful;
            private boolean signatureVerified;

            public DecryptionResult(String message, boolean successful, boolean signatureVerified) {
                this.setMessage(message);
                this.setSuccessful(successful);
                this.setSignatureVerified(signatureVerified);
            }

            public String getMessage() {
                return this.message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public boolean isSuccessful() {
                return this.successful;
            }

            public void setSuccessful(boolean successful) {
                this.successful = successful;
            }

            public boolean isSignatureVerified() {
                return this.signatureVerified;
            }

            public void setSignatureVerified(boolean signatureVerified) {
                this.signatureVerified = signatureVerified;
            }
        }
    }

    public static enum ImageHost {
        IMGUR,
        LITTERBOX;

    }

    static interface DeletedMessageCallback {
        public void onMsgDelete(Conversation var1, Conversation var2, Message var3);
    }

    static interface IncomingMessageCallback {
        public void onMsgSend(Conversation var1, Conversation var2, Message var3);
    }
}

