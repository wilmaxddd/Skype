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

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.sqlite.ConfigurationSection;
import codes.wilma24.Skype.api.v1_0_R1.sqlite.FileConfiguration;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

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
				+ ".db";
		if (messageHistoryConfig.containsKey(key)) {
			return messageHistoryConfig.get(key);
		} else {
			File file = new File(key);
			if (!file.exists()) {
				if (failIfNotExisting) {
					return null;
				}
			}
			ConfigurationSection section = null;
			try {
				section = new FileConfiguration(key).getConfigurationSection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			messageHistoryConfig.put(key, section);
			return section;
		}
	}

	public boolean addGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		try {
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
			config.replace("conversation." + conversationId.toString()
					+ ".groupChatAdmins", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		try {
			Gson gson = GsonBuilder.create();
			List<UUID> participants = Arrays.asList(participantIds);
			List<String> participants2 = new ArrayList<>();
			for (UUID participant : participants) {
				participants2.add(participant.toString());
			}
			String json = gson.toJson(participants2);
			config.replace("conversation." + conversationId.toString()
					+ ".groupChatAdmins", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeGroupChatAdmins(UUID conversationId,
			UUID... participantIds) {
		try {
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
			config.replace("conversation." + conversationId.toString()
					+ ".groupChatAdmins", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeGroupChatAdmins(UUID conversationId) {
		try {
			config.replace("conversation." + conversationId.toString()
					+ ".groupChatAdmins", null);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Optional<List<UUID>> getGroupChatAdmins(UUID conversationId) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public boolean addParticipants(UUID conversationId, UUID... participantIds) {
		try {
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
			config.replace("conversation." + conversationId.toString()
					+ ".participants", json);
			config.replace("conversation." + conversationId.toString()
					+ ".historicParticipants", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setParticipants(UUID conversationId, UUID... participantIds) {
		try {
			Gson gson = GsonBuilder.create();
			List<UUID> participants = Arrays.asList(participantIds);
			List<String> participants2 = new ArrayList<>();
			for (UUID participant : participants) {
				participants2.add(participant.toString());
			}
			String json = gson.toJson(participants2);
			config.replace("conversation." + conversationId.toString()
					+ ".participants", json);
			config.replace("conversation." + conversationId.toString()
					+ ".historicParticipants", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeParticipants(UUID conversationId,
			UUID... participantIds) {
		try {
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
			config.replace("conversation." + conversationId.toString()
					+ ".participants", json);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeParticipants(UUID conversationId) {
		try {
			config.replace("conversation." + conversationId.toString()
					+ ".participants", null);
			config.replace("conversation." + conversationId.toString()
					+ ".historicParticipants", null);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Optional<List<UUID>> getParticipants(UUID conversationId) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<List<UUID>> getHistoricParticipants(UUID conversationId) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<Object> lookupConversation(UUID conversationId) {
		ConfigurationSection config = this.config
				.getConfigurationSection("conversation."
						+ conversationId.toString());
		try {
			String payload = config.getString("payload");
			if (payload == null) {
				return Optional.empty();
			}
			return Optional.of(payload);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<Object> lookupVoipContacts(UUID conversationId) {
		ConfigurationSection config = this.config
				.getConfigurationSection("voipContacts."
						+ conversationId.toString());
		try {
			String payload = config.getString("payload");
			if (payload == null) {
				return Optional.empty();
			}
			return Optional.of(payload);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public boolean updateConversation(UUID conversationId, Object payload) {
		ConfigurationSection config = this.config
				.getConfigurationSection("conversation."
						+ conversationId.toString());
		try {
			config.replace("payload", payload.toString());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateVoipContacts(UUID conversationId, Object payload) {
		ConfigurationSection config = this.config
				.getConfigurationSection("voipContacts."
						+ conversationId.toString());
		try {
			config.replace("payload", payload.toString());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setLastAccessed(UUID conversationId, UUID participantId,
			long lastRead) {
		ConfigurationSection config = this.config
				.getConfigurationSection("lastAccessed."
						+ conversationId.toString() + "."
						+ participantId.toString());
		try {
			config.replace("payload", lastRead);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public long getLastAccessed(UUID conversationId, UUID participantId) {
		ConfigurationSection config = this.config
				.getConfigurationSection("lastAccessed."
						+ conversationId.toString() + "."
						+ participantId.toString());
		try {
			long lastRead = config.getLong("payload",
					System.currentTimeMillis());
			return lastRead;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
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
			for (String key : section.getKeys(false)) {
				try {
					String payload = section.getString(key + ".payload");
					if (payload == null) {
						continue;
					}
					long timestamp = section.getLong(key + ".timestamp");
					Date date = new Date(timestamp);
					if (to.after(date) && from.before(date)) {
						messages.add(payload);
					}
				} catch (SQLException e) {
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
		try {
			Date date = new Date(timestamp);
			ConfigurationSection section = this.getConfig(conversationId,
					participantId, date, true);
			if (section == null) {
				return Optional.empty();
			}
			section = section.getConfigurationSection("messages");
			section = section.getConfigurationSection(messageId.toString());
			String payload;
			if ((payload = section.getString("payload")) == null) {
				return Optional.empty();
			}
			return Optional.of(payload);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	public boolean addMessage(UUID conversationId, UUID participantId,
			UUID messageId, Object payload, long timestamp) {
		try {
			Date date = new Date(timestamp);
			ConfigurationSection section = this.getConfig(conversationId,
					participantId, date, false).getConfigurationSection(
					"messages");
			section = section.getConfigurationSection(messageId.toString());
			if (payload == null) {
				section.replace("payload", null);
				section.replace("timestamp", null);
			} else {
				section.replace("payload", payload.toString());
				section.replace("timestamp", timestamp);
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
