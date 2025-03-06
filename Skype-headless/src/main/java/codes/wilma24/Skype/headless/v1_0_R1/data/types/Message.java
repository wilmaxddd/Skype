/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package codes.wilma24.Skype.headless.v1_0_R1.data.types;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.headless.v1_0_R1.Conversation;
import codes.wilma24.Skype.headless.v1_0_R1.Skype;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.Contact;
import codes.wilma24.Skype.headless.v1_0_R1.data.types.MessageType;
import com.google.gson.Gson;
import java.util.Date;

public class Message
implements Comparable<Message> {
    private volatile UUID uuid;
    public volatile UUID sender;
    public volatile String message;
    private volatile transient String decryptedMessage = null;
    private volatile transient boolean decryptionSuccessful;
    private volatile transient boolean signatureVerified = false;
    public volatile long timestamp;
    public volatile MessageType messageType;
    public volatile transient boolean deleted = false;
    private volatile transient Conversation conversation;

    public Message(UUID uuid, UUID sender, String message, long timestamp, Conversation conversation) {
        this.setUniqueId(uuid);
        this.setSender(sender);
        this.setMessage(message);
        this.setTimestamp(timestamp);
        this.setConversation(conversation);
    }

    public Message(UUID uuid, UUID sender, MessageType messageType, String message, long timestamp, Conversation conversation) {
        this.setUniqueId(uuid);
        this.setSender(sender);
        this.setMessageType(messageType);
        this.setMessage(message);
        this.setTimestamp(timestamp);
        this.setConversation(conversation);
    }

    public Message(String json, Conversation conversation) {
        Gson gson = GsonBuilder.create();
        Message clazz = (Message)gson.fromJson(json, Message.class);
        this.uuid = clazz.uuid;
        this.sender = clazz.sender;
        this.message = clazz.message;
        this.timestamp = clazz.timestamp;
        this.messageType = clazz.messageType;
        this.conversation = conversation;
    }

    public Message(String json) {
        Gson gson = GsonBuilder.create();
        Message clazz = (Message)gson.fromJson(json, Message.class);
        this.uuid = clazz.uuid;
        this.sender = clazz.sender;
        this.message = clazz.message;
        this.timestamp = clazz.timestamp;
        this.messageType = clazz.messageType;
    }

    public String toString() {
        return this.exportAsJson();
    }

    public String exportAsJson() {
        Gson gson = GsonBuilder.create();
        return gson.toJson((Object)this);
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getSender() {
        return this.sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDecryptedMessage(Skype.PGPUtilities pgpUtilities, Contact loggedInUser, Conversation sender) {
        if (this.decryptedMessage != null) {
            return this.decryptedMessage;
        }
        if (this.message.startsWith("-----BEGIN PGP MESSAGE-----")) {
            Skype.PGPUtilities.DecryptionResult result = pgpUtilities.decryptAndVerify(this.message, loggedInUser, sender);
            this.decryptionSuccessful = result.isSuccessful();
            this.signatureVerified = result.isSignatureVerified();
            this.decryptedMessage = result.getMessage();
            return this.decryptedMessage;
        }
        return this.message;
    }

    public boolean isDecryptionSuccessful() {
        return this.decryptionSuccessful;
    }

    public void setDecryptionSuccessful(boolean decryptionSuccessful) {
        this.decryptionSuccessful = decryptionSuccessful;
    }

    public boolean isSignatureVerified() {
        return this.signatureVerified;
    }

    public void setSignatureVerified(boolean signatureVerified) {
        this.signatureVerified = signatureVerified;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDecryptedMessage(String decryptedMessage) {
        this.decryptedMessage = decryptedMessage;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public int compareTo(Message msg) {
        return new Date(this.timestamp).compareTo(new Date(msg.getTimestamp()));
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}

