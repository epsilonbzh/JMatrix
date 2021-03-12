package fr.epsilonbzh.jmatrix;

import java.util.ArrayList;

import fr.epsilonbzh.jmatrix.enums.MessageType;
/**
 * @author epsilonbzh
 */
public class Room {
	private String roomID;
	private MatrixClient client;
	
	public Room(MatrixClient client, String roomID) {
	this.client = client;
	this.roomID = roomID;
	}
	
	protected MatrixClient getClient() {
		return this.client;
	}

	public String getRoomID() {
		return roomID;
		}
	@Deprecated
	public ArrayList<String> getRoomTag() throws MatrixException {
		ArrayList<String> tags = new ArrayList<String>();
		String[] content = client.getRoomTag(client.getSelfUser().getUserID(), this.roomID).split("\"");
		for(String elem : content) {
			switch(elem) {
			case"m.lowpriority":
				tags.add(elem);
				break;
			case"m.favourite":
				tags.add(elem);
				break;
			}
		}
		return tags;
	}

	public ArrayList<User> getUserPresent() throws MatrixException {
		return client.getUserPresentInRoom(roomID);
	}

	public void inviteUser(String userID) throws MatrixException {
		client.inviteUserToRoom(roomID, userID);
	}

	public void kickUser(String userID, String reason) throws MatrixException {
		client.kickUserFromRoom(roomID, userID, reason);
	}

	public void kickUser(String userID) throws MatrixException {
		kickUser(userID, "");
	}

	public void banUser(String userID,String reason) throws MatrixException {
		client.banUserFromRoom(roomID, userID, reason);
	}
	public void banUser(String userID) throws MatrixException {
		banUser(userID, "");
	}
	public void unBanUser(String userID) throws MatrixException {
		client.unBanUserFromRoom(roomID, userID);
	}
	public void leave() throws MatrixException {
		client.leaveRoom(roomID);
	}
	public void join() throws MatrixException {
		client.joinRoom(roomID);
	}

	public void sendMessage(String message) throws MatrixException {
		client.sendMessage(this.roomID, MessageType.TEXTMESSAGE, message);
		
	}
	public void sendMessage(MessageType MessageType, String message) throws MatrixException {
		client.sendMessage(this.roomID, MessageType, message);
	}
	
	public ArrayList<Event> retriveEvent(int amount) throws MatrixException {
		return client.getEventFromRoom(roomID, amount);
	}
	
	private String get(String key, int step) throws MatrixException {
		String request = client.getRoomInfo(this.roomID);
		String[] content = request.split("\"");
		int i = 0;
		String find = "";
		for(String elem : content) {
			if(elem.equals(key)) {
				find = content[i+step];
			}
			i++;
		}
		return find;
		
	}
	
	public User getCreator() throws MatrixException {
		return new User(this.client,get("creator", 2));
	}
	
	public String getName() throws MatrixException {
		return get("name", 2);
	}
	
	public String getTopic() throws MatrixException {
		return get("topic", 2);
	}
	
	public Media getIcon() throws MatrixException {
		String icon = get("url", 2);
		if(icon.startsWith("mxc://")) {
			return new Media(this.client,get("url", 2));
		}
		else {
			System.err.println("Unable to get Room Icon");
			return new Media(this.client,"mxc://matrix.org/000000000000000000000000");					  
		}
	}

}
