/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.bouncycastle.openpgp.PGPPublicKeyRing
 *  org.pgpainless.PGPainless
 */
package codes.wilma24.Skype.headless.v1_0_R1;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayInReply;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLogin;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupConversationParticipants;
import codes.wilma24.Skype.api.v1_0_R1.packet.PacketPlayOutLookupGroupChatAdmins;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.headless.v1_0_R1.Skype;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Bot;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Message;

import com.google.gson.Gson;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.pgpainless.PGPainless;

public class Conversation {
    public volatile String pubKey;
    public volatile UUID uuid;
    public volatile String skypeName;
    public volatile String name;
    public volatile boolean groupChat = false;
    public volatile String imageIconUrl;
    private volatile transient boolean bot = false;
    public volatile long lastModified;
    public volatile transient int notificationCount;
    private volatile transient boolean hasIncomingFriendRequest = false;
    private volatile transient Message incomingFriendRequestMessage = null;
    private volatile transient boolean hasOutgoingFriendRequest = false;
    public volatile transient ImageIcon imageIcon;
    public volatile transient List<Message> messages = new ArrayList<Message>();
    private List<UUID> participants = null;
    private List<UUID> groupChatAdmins = null;

    public Conversation() {
        if (this instanceof Bot) {
            this.bot = true;
        }
    }

    public Conversation(String json) {
        this.readFromJson(json);
    }

    public void readFromJson(String json) {
        Gson gson = GsonBuilder.create();
        Conversation clazz = (Conversation)gson.fromJson(json, Conversation.class);
        this.pubKey = clazz.pubKey;
        this.uuid = clazz.uuid;
        this.skypeName = clazz.skypeName;
        this.name = clazz.name;
        this.groupChat = clazz.groupChat;
        this.imageIconUrl = clazz.imageIconUrl;
        if (this.imageIconUrl != null && this.imageIconUrl.length() > 0 && this.imageIconUrl.startsWith("https://i.imgur.com/")) {
            try {
            	ImageIcon imgIcon;
                int r;
                HttpGet request = new HttpGet(this.imageIconUrl.replace(" ", "%20"));
                request.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"130\", \"Microsoft Edge\";v=\"130\"");
                request.addHeader("X-Requested-With", "XMLHttpRequest");
                request.addHeader("sec-ch-ua-mobile", "?0");
                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36 Edg/130.0.0.0");
                request.addHeader("sec-ch-ua-platform", "\"Windows\"");
                request.addHeader("Origin", this.imageIconUrl);
                request.addHeader("Sec-Fetch-Site", "same-origin");
                request.addHeader("Sec-Fetch-Mode", "cors");
                request.addHeader("Sec-Fetch-Dest", "empty");
                request.addHeader("Referer", this.imageIconUrl);
                request.addHeader("Accept-Language", "en-US,en;q=0.9");
                request.addHeader("sec-gpc", "1");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                request.addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Microsoft Edge\";v=\"100\"");
                request.addHeader("sec-ch-ua-mobile", "?0");
                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4863.0 Safari/537.36 Edg/100.0.1163.1");
                CloseableHttpResponse response = httpClient.execute((HttpUriRequest)request);
                InputStream is = response.getEntity().getContent();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((r = is.read(b)) != -1) {
                    baos.write(Arrays.copyOf(b, r));
                    baos.flush();
                }
                baos.close();
                BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
                this.imageIcon = imgIcon = new ImageIcon(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Conversation) {
            Conversation conversation = (Conversation)obj;
            if (this.uuid.toString().equals(conversation.uuid.toString())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.exportAsJson();
    }

    public String exportAsJson() {
        Gson gson = GsonBuilder.create();
        return gson.toJson((Object)this);
    }

    public Optional<PGPPublicKeyRing> getPubKey() {
        try {
            if (this.pubKey != null) {
                PGPPublicKeyRing pubKey = PGPainless.readKeyRing().publicKeyRing(this.pubKey);
                return Optional.of(pubKey);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSkypeName() {
        return this.skypeName;
    }

    public void setSkypeName(String skypeName) {
        this.skypeName = skypeName;
    }

    public String getDisplayName() {
        return this.name;
    }

    public void setDisplayName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return new Date(this.lastModified);
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified.getTime();
    }

    public int getNotificationCount() {
        return this.notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public boolean isGroupChat() {
        return this.groupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.groupChat = groupChat;
    }

    public String getImageIconUrl() {
        return this.imageIconUrl;
    }

    public void setImageIconUrl(String imageIconUrl) {
        this.imageIconUrl = imageIconUrl;
    }

    public boolean hasIncomingFriendRequest() {
        return this.hasIncomingFriendRequest;
    }

    public void setHasIncomingFriendRequest(boolean hasIncomingFriendRequest, Message message) {
        this.hasIncomingFriendRequest = hasIncomingFriendRequest;
        this.incomingFriendRequestMessage = message;
    }

    public Optional<Message> getIncomingFriendRequestMessage() {
        if (this.incomingFriendRequestMessage == null) {
            return Optional.empty();
        }
        return Optional.of(this.incomingFriendRequestMessage);
    }

    public boolean hasOutgoingFriendRequest() {
        return this.hasOutgoingFriendRequest;
    }

    public void setHasOutgoingFriendRequest(boolean hasOutgoingFriendRequest) {
        this.hasOutgoingFriendRequest = hasOutgoingFriendRequest;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }

    public void setParticipants(List<UUID> participants) {
        this.participants = participants;
    }

    public List<UUID> getParticipants(UUID authCode) {
        if (this.participants != null) {
            // empty if block
        }
        if (!this.groupChat) {
            return new ArrayList<UUID>();
        }
        Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
        if (!ctx.isPresent()) {
            return new ArrayList<UUID>();
        }
        Optional<PacketPlayInReply> reply = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutLogin(authCode));
        if (!reply.isPresent() || reply.get().getStatusCode() != 200) {
            return new ArrayList<UUID>();
        }
        authCode = UUID.fromString(reply.get().getText());
        PacketPlayOutLookupConversationParticipants packet = new PacketPlayOutLookupConversationParticipants(authCode, this.getUniqueId());
        Optional<PacketPlayInReply> replyPacket = ctx.get().getOutboundHandler().dispatch(ctx.get(), packet);
        if (!replyPacket.isPresent()) {
            return new ArrayList<UUID>();
        }
        if (replyPacket.get().getStatusCode() != 200) {
            return new ArrayList<UUID>();
        }
        String json = replyPacket.get().getText();
        Gson gson = GsonBuilder.create();
        List<String> participants = (List<String>)gson.fromJson(json, List.class);
        ArrayList<UUID> participantIds = new ArrayList<UUID>();
        for (String participant : participants) {
            participantIds.add(UUID.fromString(participant));
        }
        this.participants = participantIds;
        return participantIds;
    }

    public void setGroupChatAdmins(List<UUID> groupChatAdmins) {
        this.groupChatAdmins = groupChatAdmins;
    }

    public List<UUID> getGroupChatAdmins(UUID authCode) {
        if (this.groupChatAdmins != null) {
            return this.groupChatAdmins;
        }
        if (!this.groupChat) {
            return new ArrayList<UUID>();
        }
        Optional<SocketHandlerContext> ctx = Skype.getPlugin().createHandle();
        if (!ctx.isPresent()) {
            return new ArrayList<UUID>();
        }
        Optional<PacketPlayInReply> reply = ctx.get().getOutboundHandler().dispatch(ctx.get(), new PacketPlayOutLogin(authCode));
        if (!reply.isPresent() || reply.get().getStatusCode() != 200) {
            return new ArrayList<UUID>();
        }
        authCode = UUID.fromString(reply.get().getText());
        PacketPlayOutLookupGroupChatAdmins packet = new PacketPlayOutLookupGroupChatAdmins(authCode, this.getUniqueId());
        Optional<PacketPlayInReply> replyPacket = ctx.get().getOutboundHandler().dispatch(ctx.get(), packet);
        if (!replyPacket.isPresent()) {
            return new ArrayList<UUID>();
        }
        if (replyPacket.get().getStatusCode() != 200) {
            return new ArrayList<UUID>();
        }
        String json = replyPacket.get().getText();
        Gson gson = GsonBuilder.create();
        List<String> participants = (List<String>)gson.fromJson(json, List.class);
        ArrayList<UUID> participantIds = new ArrayList<UUID>();
        for (String participant : participants) {
            participantIds.add(UUID.fromString(participant));
        }
        this.groupChatAdmins = participantIds;
        return participantIds;
    }
}

