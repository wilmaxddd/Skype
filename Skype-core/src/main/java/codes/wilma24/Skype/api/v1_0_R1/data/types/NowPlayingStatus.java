package codes.wilma24.Skype.api.v1_0_R1.data.types;

import com.google.gson.Gson;

import codes.wilma24.Skype.api.v1_0_R1.gson.GsonBuilder;
import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class NowPlayingStatus {

	public volatile UUID uuid = UUID.randomUUID();

	public volatile String itemName;

	public volatile String uri;

	public volatile long timestamp;
	
	public volatile long progressMs;
	
	public volatile long durationMs;
	
	public NowPlayingStatus(String itemName, String uri, long timestamp, long progressMs, long durationMs) {
		this.itemName = itemName;
		this.uri = uri;
		this.timestamp = timestamp;
		this.progressMs = progressMs;
		this.durationMs = durationMs;
	}

	public NowPlayingStatus(String json) {
		readFromJson(json);
	}

	public void readFromJson(String json) {
		Gson gson = GsonBuilder.create();
		NowPlayingStatus clazz = gson.fromJson(json, NowPlayingStatus.class);
		this.uuid = clazz.uuid;
		this.itemName = clazz.itemName;
		this.uri = clazz.uri;
		this.timestamp = clazz.timestamp;
		this.progressMs = clazz.progressMs;
		this.durationMs = clazz.durationMs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof NowPlayingStatus) {
			NowPlayingStatus conversation = (NowPlayingStatus) obj;
			if (uuid.toString().equals(conversation.uuid.toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return exportAsJson();
	}

	public String exportAsJson() {
		Gson gson = GsonBuilder.create();
		return gson.toJson(this);
	}

	public UUID getUniqueId() {
		return uuid;
	}

	public void setUniqueId(UUID uuid) {
		this.uuid = uuid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getProgressMs() {
		return progressMs;
	}
	
	public void setProgressMs(long progressMs) {
		this.progressMs = progressMs;
	}
	
	public long getDurationMs() {
		return durationMs;
	}
	
	public void setDurationMs(long durationMs) {
		this.durationMs = durationMs;
	}

}
