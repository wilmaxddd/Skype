package codes.wilma24.Skype.server.v1_0_R1.manager;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;
import codes.wilma24.Skype.server.v1_0_R1.ConfigurationManager;

import com.google.gson.Gson;

public class ConversationManager {

	private ConfigurationSection config;

	private Map<String, ConfigurationSection> messageHistoryConfig = new HashMap<>();

	public ConversationManager(ConfigurationSection config) {
		this.config = config;
	}

	public ConfigurationSection getConfig(UUID conversationId,
			UUID participantId, Date timestamp, boolean failIfNotExisting) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
		String key = "config_" + conversationId.toString() + "_"
				+ participantId.toString() + "_" + format.format(timestamp)
				+ ".yml";
		if (messageHistoryConfig.containsKey(key)) {
			return messageHistoryConfig.get(key);
		} else {
			File file = new File(key);
			if (!file.exists()) {
				if (failIfNotExisting) {
					return null;
				}
			}
			codes.wilma24.Skype.api.v1_0_R1.bukkit.ConfigurationManager root = new codes.wilma24.Skype.api.v1_0_R1.bukkit.ConfigurationManager();
	        root.setup(new File(key));
	        ConfigurationSection section = root.getData();
			messageHistoryConfig.put(key, section);
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

	public boolean addGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> participants = new ArrayList<String>();
		if (config.contains("conversation." + conversationId.toString()
				+ ".groupChatAdmins")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".groupChatAdmins");
			participants = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			if (!participants.contains(participant.toString())) {
				participants.add(participant.toString());
			}
		}
		String json = gson.toJson(participants);
		config.set("conversation." + conversationId.toString()
				+ ".groupChatAdmins", json);
		return true;
	}

	public boolean setGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<UUID> participants = Arrays.asList(participantIds);
		List<String> participants2 = new ArrayList<>();
		for (UUID participant : participants) {
			participants2.add(participant.toString());
		}
		String json = gson.toJson(participants2);
		config.set("conversation." + conversationId.toString()
				+ ".groupChatAdmins", json);
		return true;
	}

	public boolean removeGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> participants = new ArrayList<String>();
		if (config.contains("conversation." + conversationId.toString()
				+ ".groupChatAdmins")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".groupChatAdmins");
			participants = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			participants.remove(participant.toString());
		}
		String json = gson.toJson(participants);
		config.set("conversation." + conversationId.toString()
				+ ".groupChatAdmins", json);
		return true;
	}

	public boolean removeGroupChatAdmins(UUID conversationId) {
		config.set("conversation." + conversationId.toString()
				+ ".groupChatAdmins", null);
		return true;
	}

	public Optional<List<UUID>> getGroupChatAdmins(UUID conversationId) {
		List<String> list = new ArrayList<String>();
		Gson gson = GsonBuilder.create();
		if (config.contains("conversation." + conversationId.toString()
				+ ".groupChatAdmins")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".groupChatAdmins");
			list = gson.fromJson(json, List.class);
		}
		List<UUID> participantIds = new ArrayList<>();
		for (String participant : list) {
			participantIds.add(UUID.fromString(participant));
		}
		return Optional.of(participantIds);
	}

	public boolean addParticipants(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> participants = new ArrayList<String>();
		List<String> historicParticipants = new ArrayList<String>();
		if (config.contains("conversation." + conversationId.toString()
				+ ".participants")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".participants");
			participants = gson.fromJson(json, List.class);
		}
		if (config.contains("conversation." + conversationId.toString()
				+ ".historicParticipants")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".historicParticipants");
			historicParticipants = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			if (!participants.contains(participant.toString())) {
				participants.add(participant.toString());
			}
			if (!historicParticipants.contains(participant.toString())) {
				historicParticipants.add(participant.toString());
			}
		}
		String json = gson.toJson(participants);
		config.set("conversation." + conversationId.toString()
				+ ".participants", json);
		config.set("conversation." + conversationId.toString()
				+ ".historicParticipants", json);
		return true;
	}

	public boolean setParticipants(UUID conversationId, UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<UUID> participants = Arrays.asList(participantIds);
		List<String> participants2 = new ArrayList<>();
		for (UUID participant : participants) {
			participants2.add(participant.toString());
		}
		String json = gson.toJson(participants2);
		config.set("conversation." + conversationId.toString()
				+ ".participants", json);
		config.set("conversation." + conversationId.toString()
				+ ".historicParticipants", json);
		return true;
	}

	public boolean removeParticipants(UUID conversationId,
			UUID... participantIds) {
		Gson gson = GsonBuilder.create();
		List<String> participants = new ArrayList<String>();
		if (config.contains("conversation." + conversationId.toString()
				+ ".participants")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".participants");
			participants = gson.fromJson(json, List.class);
		}
		for (UUID participant : participantIds) {
			participants.remove(participant.toString());
		}
		String json = gson.toJson(participants);
		config.set("conversation." + conversationId.toString()
				+ ".participants", json);
		return true;
	}

	public boolean removeParticipants(UUID conversationId) {
		config.set("conversation." + conversationId.toString()
				+ ".participants", null);
		config.set("conversation." + conversationId.toString()
				+ ".historicParticipants", null);
		return true;
	}

	public Optional<List<UUID>> getParticipants(UUID conversationId) {
		List<String> list = new ArrayList<String>();
		Gson gson = GsonBuilder.create();
		if (config.contains("conversation." + conversationId.toString()
				+ ".participants")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".participants");
			list = gson.fromJson(json, List.class);
		}
		List<UUID> participantIds = new ArrayList<>();
		for (String participant : list) {
			participantIds.add(UUID.fromString(participant));
		}
		return Optional.of(participantIds);
	}

	public Optional<List<UUID>> getHistoricParticipants(UUID conversationId) {
		List<String> list = new ArrayList<String>();
		Gson gson = GsonBuilder.create();
		if (config.contains("conversation." + conversationId.toString()
				+ ".historicParticipants")) {
			String json = config.getString("conversation."
					+ conversationId.toString() + ".historicParticipants");
			list = gson.fromJson(json, List.class);
		}
		List<UUID> participantIds = new ArrayList<>();
		for (String participant : list) {
			participantIds.add(UUID.fromString(participant));
		}
		return Optional.of(participantIds);
	}

	public Optional<Object> lookupConversation(UUID conversationId) {
		if (this.config.contains("conversation." + conversationId.toString() + ".payload")) {
			return Optional.of(this.config.getString("conversation." + conversationId.toString() + ".payload"));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Object> lookupVoipContacts(UUID conversationId) {
		if (this.config.contains("voipContacts." + conversationId.toString() + ".payload")) {
			return Optional.of(this.config.getString("voipContacts." + conversationId.toString() + ".payload"));
		} else {
			return Optional.empty();
		}
	}

	public boolean updateConversation(UUID conversationId, Object payload) {
		config.set("conversation." + conversationId.toString() + ".payload", payload.toString());
		return true;
	}

	public boolean updateVoipContacts(UUID conversationId, Object payload) {
		config.set("voipContacts." + conversationId.toString() + ".payload", payload.toString());
		return true;
	}

	public boolean setLastAccessed(UUID conversationId, UUID participantId,
			long lastRead) {
		config.set("lastAccessed." + conversationId.toString() + "." + participantId.toString() + ".payload", lastRead);
		return true;
	}

	public long getLastAccessed(UUID conversationId, UUID participantId) {
		if (this.config.contains("lastAccessed." + conversationId.toString() + "." + participantId.toString() + ".payload")) {
			long lastRead = config.getLong("lastAccessed." + conversationId.toString() + "." + participantId.toString() + ".payload",
					System.currentTimeMillis());
			return lastRead;
		} else {
			return System.currentTimeMillis();
		}
	}

	public LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public List<String> getMessages(UUID conversationId, UUID participantId,
			Date from, Date to) {
		List<String> messages = new ArrayList<>();
		LocalDate start = asLocalDate(from);
		LocalDate end = asLocalDate(to);
		start = start.minusMonths(1);
		do {
			start = start.plusMonths(1);
			Date localDate = Date.from(start.atStartOfDay(
					ZoneId.systemDefault()).toInstant());
			ConfigurationSection section = this.getConfig(conversationId,
					participantId, localDate, true);
			if (section == null) {
				continue;
			}
			section = section.getConfigurationSection("messages");
			if (section == null) {
				continue;
			}
			for (String key : section.getKeys(false)) {
				String payload = section.getString(key + ".payload");
				if (payload == null) {
					continue;
				}
				long timestamp = section.getLong(key + ".timestamp");
				Date date = new Date(timestamp);
				if (to.after(date) && from.before(date)) {
					messages.add(payload);
				}
			}
		} while (!start.isAfter(end));
		return messages;
	}

	public List<String> getMessages(UUID conversationId, Date from, Date to) {
		Optional<List<UUID>> participantIds = this
				.getHistoricParticipants(conversationId);
		List<String> messages = new ArrayList<>();
		if (participantIds.isPresent()) {
			for (UUID participantId : participantIds.get()) {
				messages.addAll(this.getMessages(conversationId, participantId,
						from, to));
			}
		}
		return messages;
	}

	public Optional<String> lookupMessage(UUID conversationId,
			UUID participantId, UUID messageId, long timestamp) {
		Date date = new Date(timestamp);
		ConfigurationSection section = this.getConfig(conversationId,
				participantId, date, true);
		if (section == null) {
			return Optional.empty();
		}
		String payload;
		if ((payload = section.getString("messages." + messageId.toString() + ".payload", null)) == null) {
			return Optional.empty();
		}
		return Optional.of(payload);
	}

	public boolean addMessage(UUID conversationId, UUID participantId,
			UUID messageId, Object payload, long timestamp) {
		Date date = new Date(timestamp);
		ConfigurationSection section = this.getConfig(conversationId,
				participantId, date, false);
		if (payload == null) {
			section.set("messages." + messageId.toString() + ".payload", null);
			section.set("messages." + messageId.toString() + ".timestamp", null);
		} else {
			section.set("messages." + messageId.toString() + ".payload", payload.toString());
			section.set("messages." + messageId.toString() + ".timestamp", timestamp);
		}
		return true;
	}

}
