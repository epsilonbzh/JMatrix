package fr.epsilonbzh.jmatrix;

import java.util.ArrayList;

import fr.epsilonbzh.jmatrix.enums.MessageType;

public class Room {
	private String roomID;
	private MatrixClient client;
	
	public Room(MatrixClient client, String roomID) {
	this.client = client;
	this.roomID = roomID;
	}


public String getRoomID() {
	return roomID;
	}
@Deprecated
public String[] getRoomTag() throws MatrixException {

	String[] content = client.getRoomTag(client.getUserID(), this.roomID).split("");
	return content;
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


}
