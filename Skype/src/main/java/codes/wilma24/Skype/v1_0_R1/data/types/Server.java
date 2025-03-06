package codes.wilma24.Skype.v1_0_R1.data.types;

import java.util.ArrayList;
import java.util.List;

import codes.wilma24.Skype.api.v1_0_R1.uuid.UUID;

public class Server {
	
	private UUID uuid;
	
	private List<Conversation> conversations = new ArrayList<>(); 
	
	public Server() {
		
	}
	
	public Server(UUID uuid, List<Conversation> conversations) {
		this.uuid = uuid;
		this.conversations = conversations;
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public List<Conversation> getConversations() {
		return conversations;
	}

}
