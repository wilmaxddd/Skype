package codes.wilma24.Skype.server.v1_0_R1.manager;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.socket.SocketHandlerContext;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.Skype;
import codes.wilma24.Skype.server.v1_0_R1.data.types.Connection;

import com.google.gson.Gson;

public class UserManager {

	private ConfigurationSection config;

	public UserManager(ConfigurationSection config) {
		this.config = config;
	}

	public Optional<String> getSkypeName(UUID participantId) {
		if (config.contains("registry." + participantId.toString() + ".skypeName")) {
			return Optional.of(config.getString("registry." + participantId.toString() + ".skypeName"));
		} else {
			return Optional.empty();
		}
	}

	public UUID getUniqueId(String skypeName) {
		return UUID.nameUUIDFromBytes(("skype:" + skypeName).getBytes());
	}

	public boolean validatePassword(SocketHandlerContext ctx, String skypeName, String password) {
		password = ctx.getCryptographicContext().encodeToString(password.getBytes());
		UUID participantId = getUniqueId(skypeName);
		if (userExists(skypeName)) {
			if (password.equals(config.getString("registry." + participantId.toString() + ".password"))) {
				return true;
			}
		}
		return false;
	}

	public boolean userExists(String skypeName) {
		UUID participantId = getUniqueId(skypeName);
		if (!config.contains("registry." + participantId.toString() + ".password")) {
			return false;
		}
		if (!config.contains("registry." + participantId.toString() + ".skypeName")) {
			return false;
		}
		return true;
	}

	public boolean createUser(SocketHandlerContext ctx, String fullName, String skypeName, String password,
			boolean isGroupChat) {
		password = ctx.getCryptographicContext().encodeToString(password.getBytes(StandardCharsets.UTF_8));
		UUID participantId = getUniqueId(skypeName);
		if (!config.contains("registry." + participantId.toString() + ".skypeName")) {
			config.set("registry." + participantId.toString() + ".password", password);
			config.set("registry." + participantId.toString() + ".skypeName", skypeName);
			config.set("registry." + participantId.toString() + ".isGroupChat", isGroupChat);
			Skype.getPlugin().getConversationManager().addParticipants(participantId, participantId);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeUser(String skypeName) {
		UUID participantId = getUniqueId(skypeName);
		config.set("registry." + participantId.toString() + ".password", null);
		config.set("registry." + participantId.toString() + ".skypeName", null);
		config.set("registry." + participantId.toString() + ".isGroupChat", null);
		Skype.getPlugin().getConversationManager().removeParticipants(participantId);
		return true;
	}

	public boolean isGroupChat(String skypeName) {
		UUID participantId = getUniqueId(skypeName);
		try {
			if (config.contains("registry." + participantId.toString() + ".isGroupChat")) {
				return config.getBoolean("registry." + participantId.toString() + ".isGroupChat", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isGroupChat(UUID participantId) {
		try {
			if (config.contains("registry." + participantId.toString() + ".isGroupChat")) {
				return config.getBoolean("registry." + participantId.toString() + ".isGroupChat", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Connection> getAllConnectionsInCall(UUID participantId, UUID callId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.getCallId().isPresent()) {
				continue;
			}
			if (!con.getCallId().get().equals(callId)) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public List<Connection> getCallingInboundHandlerConnectionsInCall(UUID participantId, UUID callId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.isInCall()) {
				continue;
			}
			if (!con.getCallId().get().equals(callId)) {
				continue;
			}
			if (!con.getSocketHandlerContext().isOutboundHandlerAdded()) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public List<Connection> getConnectionsInFileTransfer(UUID participantId, UUID fileTransferId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.isInFileTransfer()) {
				continue;
			}
			if (!con.getFileTransferId().get().equals(fileTransferId)) {
				continue;
			}
			if (!con.getSocketHandlerContext().isOutboundHandlerAdded()) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public List<Connection> getListeningDataStreamConnectionsInCall(UUID participantId, UUID callId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.isCallDataStream()) {
				continue;
			}
			if (!con.getCallId().get().equals(callId)) {
				continue;
			}
			if (!con.getSocketHandlerContext().isOutboundHandlerAdded()) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public List<Connection> getDataStreamConnectionsInFileTransfer(UUID participantId, UUID fileTransferId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.isFileDataStream()) {
				continue;
			}
			if (!con.getFileTransferId().get().equals(fileTransferId)) {
				continue;
			}
			if (!con.getSocketHandlerContext().isOutboundHandlerAdded()) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public List<Connection> getListeningConnections(UUID participantId) {
		List<Connection> cons = new ArrayList<>();
		for (Connection con : Skype.getPlugin().getConnectionMap().values().toArray(new Connection[0]).clone()) {
			if (!con.isListening()) {
				continue;
			}
			if (con.getExpiryTime() < System.currentTimeMillis()) {
				continue;
			}
			if (!con.getUniqueId().equals(participantId)) {
				continue;
			}
			if (!con.getSocketHandlerContext().isOutboundHandlerAdded()) {
				continue;
			}
			cons.add(con);
		}
		return cons;
	}

	public Connection getConnection(UUID authCode) {
		if (Skype.getPlugin().getConnectionMap().containsKey(authCode)) {
			Connection con = Skype.getPlugin().getConnectionMap().get(authCode);
			long expiryTime = con.getExpiryTime();
			if (System.currentTimeMillis() > expiryTime) {
				Skype.getPlugin().getConnectionMap().remove(authCode);
				return null;
			} else {
				return con;
			}
		} else {
			return null;
		}
	}

	/**
	 * Contact
	 */

	private Map<String, ConfigurationSection> configByConversationId = new HashMap<>();

	public ConfigurationSection getConfig(UUID conversationId) {
		String key = "config_" + conversationId.toString() + ".yml";
		if (configByConversationId.containsKey(key)) {
			return configByConversationId.get(key);
		} else {
			codes.wilma24.Skype.api.v1_0_R1.bukkit.ConfigurationManager root = new codes.wilma24.Skype.api.v1_0_R1.bukkit.ConfigurationManager();
	        root.setup(new File(key));
	        ConfigurationSection section = root.getData();
			configByConversationId.put(key, section);
	        Thread thread = new Thread(() -> {
	            while (true) {
	                root.saveData();
	                try {
	                    Thread.sleep(20000L);
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        thread.start();
			return section;
		}
	}

	public boolean setSpotifyCreds(UUID conversationId, String accessToken, String refreshToken) {
		try {
			config.set("spotify." + conversationId.toString() + ".accessToken", accessToken);
			config.set("spotify." + conversationId.toString() + ".refreshToken", refreshToken);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Optional<String> getSpotifyAccessToken(UUID conversationId) {
		try {
			if (config.contains("spotify." + conversationId.toString() + ".accessToken")) {
				return Optional.of(config.getString("spotify." + conversationId.toString() + ".accessToken"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<String> getSpotifyRefreshToken(UUID conversationId) {
		try {
			if (config.contains("spotify." + conversationId.toString() + ".refreshToken")) {
				return Optional.of(config.getString("spotify." + conversationId.toString() + ".refreshToken"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public boolean addContact(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> list = new ArrayList<String>();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contacts")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contacts");
			list = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			if (!list.contains(participant.toString())) {
				list.add(participant.toString());
			}
		}
		String json = gson.toJson(list);
		config.set("conversation." + conversationId.toString() + ".contacts", json);
		return true;
	}

	public boolean removeContact(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> list = new ArrayList<String>();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contacts")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contacts");
			list = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			if (list.contains(participant.toString())) {
				list.remove(participant.toString());
			}
		}
		String json = gson.toJson(list);
		config.set("conversation." + conversationId.toString() + ".contacts", json);
		return true;
	}

	public Optional<List<UUID>> getContacts(UUID conversationId) {
		List<String> list = new ArrayList<String>();
		Gson gson = GsonBuilder.create();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contacts")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contacts");
			list = gson.fromJson(json, List.class);
		}
		List<UUID> participants = new ArrayList<>();
		for (String participant : list) {
			participants.add(UUID.fromString(participant));
		}
		return Optional.of(participants);
	}

	public boolean hasContact(UUID conversationId, UUID participantId) {
		Optional<List<UUID>> contacts = getContacts(conversationId);
		if (contacts.isPresent()) {
			for (UUID uuid : contacts.get()) {
				if (uuid.equals(participantId)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean addContactRequest(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> list = new ArrayList<String>();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contactRequests")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contactRequests");
			list = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			list.add(participant.toString());
		}
		String json = gson.toJson(list);
		config.set("conversation." + conversationId.toString() + ".contactRequests", json);
		return true;
	}

	public boolean removeContactRequest(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> list = new ArrayList<String>();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contactRequests")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contactRequests");
			list = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			list.remove(participant.toString());
		}
		String json = gson.toJson(list);
		config.set("conversation." + conversationId.toString() + ".contactRequests", json);
		return true;
	}

	public Optional<List<UUID>> getContactRequests(UUID conversationId) {
		List<String> list = new ArrayList<String>();
		Gson gson = GsonBuilder.create();
		ConfigurationSection config = this.getConfig(conversationId);
		if (config.contains("conversation." + conversationId.toString() + ".contactRequests")) {
			String json = config.getString("conversation." + conversationId.toString() + ".contactRequests");
			list = gson.fromJson(json, List.class);
		}
		List<UUID> participants = new ArrayList<>();
		for (String participant : list) {
			participants.add(UUID.fromString(participant));
		}
		return Optional.of(participants);
	}

	public boolean hasContactRequest(UUID conversationId, UUID participantId) {
		Optional<List<UUID>> contactRequests = getContactRequests(conversationId);
		if (contactRequests.isPresent()) {
			for (UUID uuid : contactRequests.get()) {
				if (uuid.equals(participantId)) {
					return true;
				}
			}
		}
		return false;
	}
}
