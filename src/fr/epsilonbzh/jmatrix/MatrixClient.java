package fr.epsilonbzh.jmatrix;

import java.util.ArrayList;
import java.util.HashMap;

import fr.epsilonbzh.jmatrix.enums.MessageType;

/**
 * @author epsilonbzh
 */
public class MatrixClient extends MatrixSDK{
	
	private MatrixSDK sdk;
	
	private String token;
	private String serverURL;
	private String baseURL;
	
	public MatrixClient(String token) {
		this.token = token;
		this.sdk = new MatrixSDK();
		this.serverURL = sdk.getServerURL();
		this.baseURL = serverURL + "/_matrix/client/r0/";
	}
	
	public MatrixClient(MatrixSDK sdk,String token) {
		this.token = token;
		this.sdk = sdk;
		this.serverURL = sdk.getServerURL();
		this.baseURL = serverURL + "/_matrix/client/r0/";
		
		
	}
	
	public User getSelfUser() throws MatrixException{
			String response = sdk.RequestGET(baseURL + "account/whoami?access_token=" + token);
			String[] content = response.split("\"");
			return new User(this,content[3]);
		
	}
	/**
	 * @param userID full syntaxe of userID like : \@exemple:matrix.org 
	 * @return user's ID
	 */
	public User getUserByID(String userID) {
		return new User(this, userID);
	}
	
	/**
	 * @param isPublic Room's visibility
	 * @param name Room's name
	 * @param topic Room's topic
	 * @param alias Room alias, without the "#"
	 * @throws MatrixException
	 */
	public void createRoom(boolean isPublic,String name, String topic,String alias) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("name",name);
		args.put("topic", topic);
		if(isPublic == true) {
			args.put("preset", "public_chat");
		}else {
			args.put("preset", "trusted_private_chat");
		}
		args.put("room_alias_name", alias);
		String response = sdk.RequestPOST(baseURL + "createRoom?access_token=" + token, args);
		String[] content = response.split("\"");
		System.out.println("[INFO] A new room has been created with id : " + content[3]);
	}
	
	
	/**
	 * @param isPublic Room's visibility
	 * @param name Room's name
	 * @param topic Room's topic
	 * @throws MatrixException
	 */
	public Room createRoom(boolean isPublic,String name,String topic) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("name",name);
		args.put("topic", topic);
		if(isPublic == true) {
			args.put("preset", "public_chat");
		}else {
			args.put("preset", "trusted_private_chat");
		}
	
		
		String response = sdk.RequestPOST(baseURL + "createRoom?access_token=" + token, args);
		String[] content = response.split("\"");
		System.out.println("[INFO] A new room has been created with id : " + content[3]);
		return new Room(this, content[3]);
	}
	/**
	 * @param isPublic Room's visibility
	 * @param name Room's name
	 * @throws MatrixException
	 */
	public Room createRoom(boolean isPublic,String name) throws MatrixException {
		return createRoom(isPublic, name, "");
	}
	
	public ArrayList<Device> getDevices() throws MatrixException {
		String response = sdk.RequestGET(baseURL + "devices?access_token=" + token);
		String[] content = response.split("\"");
		
		int i = 0;
		ArrayList<String> deviceID = new ArrayList<String>();
		ArrayList<String> displayName = new ArrayList<String>();
		ArrayList<String> lastSeenTS = new ArrayList<String>();
		ArrayList<String> lastSeenIP = new ArrayList<String>();
		ArrayList<Device> users = new ArrayList<Device>();
		
		for(String item : content) {
			switch(item) {
			case "device_id":
				deviceID.add(content[i+2]);
				i++;
				break;
			case "display_name":
				displayName.add(content[i+2]);
				i++;
				break;
			case "last_seen_ts":
				lastSeenTS.add(content[i+1].substring(2).replace(",", ""));
				i++;
				break;
			case "last_seen_ip":
				lastSeenIP.add(content[i+2]);
				i++;
				break;
			default:
				i++;
				break;
			}
		}
		
	
		
		for(i=0;i < deviceID.size();i++) {
			users.add(new Device(deviceID.get(i),displayName.get(i),lastSeenTS.get(i),lastSeenIP.get(i)));
		}
		return users;
		
	}
	
	
	public Device getDevice(String deviceID) throws MatrixException {
		String response = sdk.RequestGET(baseURL + "devices/" + deviceID + "?access_token=" + token);
		String[] content = response.split("\"");
		
		int i = 0;
		String displayName = null;
		String lastSeenTS = null;
		String lastSeenIP = null;
		
		for(String item : content) {
			switch(item) {
			case "device_id":
				deviceID = content[i+2];
				i++;
				break;
			case "display_name":
				displayName = content[i+2];
				i++;
				break;
			case "last_seen_ts":
				lastSeenTS = content[i+1].substring(2).replace(",", "");
				i++;
				break;
			case "last_seen_ip":
				lastSeenIP = content[i+2];
				i++;
				break;
			default:
				i++;
				break;
			}
		}
		return new Device(deviceID, displayName, lastSeenTS, lastSeenIP);
	}
	
	protected void sendMessage(String roomID,MessageType messageType,String message) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		switch(messageType) {
			case TEXTMESSAGE:
				args.put("msgtype", "m.text");
				args.put("body", message);
				break;
			case HTMLMESSAGE:
				args.put("msgtype", "m.text");
				args.put("body", message);
				args.put("format", "org.matrix.custom.html");
				args.put("formatted_body", message);
				break;
			case IMAGE:
				args.put("msgtype", "m.image");
				args.put("body", message);
				args.put("url", message);
				break;
			case CONFETTI:
				args.put("msgtype", "nic.custom.confetti");
				args.put("body", message);
				break;
		}
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/send/m.room.message?access_token=" + token, args);
	}
	
	
	protected String getRoomTag(String userID,String roomID) throws MatrixException {
			String response = sdk.RequestGET(baseURL + "user/" + userID + "/rooms/" + roomID + "/tags?access_token=" + token);
			return response;
	}
	
	protected ArrayList<User> getUserPresentInRoom(String roomID) throws MatrixException {
		String response = sdk.RequestGET(baseURL + "rooms/" + roomID + "/joined_members?access_token=" + token);
		String[] content = response.split("\"");
		ArrayList<User> userIDs = new ArrayList<User>();
		for(String item : content) {
			if(item.startsWith("@")) {
				userIDs.add(new User(this,item));
			}
		}
		return userIDs;
		
	}
	
	protected void inviteUserToRoom(String roomID,String userID) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("user_id", userID);
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/invite?access_token=" + token,args);
	}
	
	protected void kickUserFromRoom(String roomID,String userID,String reason) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("user_id", userID);
		args.put("reason", reason);
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/kick?access_token=" + token,args);
	}
	
	protected void banUserFromRoom(String roomID,String userID,String reason) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("user_id", userID);
		args.put("reason", reason);
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/ban?access_token=" + token,args);
	}
	
	protected void unBanUserFromRoom(String roomID,String userID) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("user_id", userID);
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/unban?access_token=" + token,args);
	}
	protected void joinRoom(String roomID) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/join?access_token=" + token,args);	
	}
	
	protected void leaveRoom(String roomID) throws MatrixException {
		HashMap<String, String> args = new HashMap<String, String>();
		sdk.RequestPOST(baseURL + "rooms/" + roomID + "/leave?access_token=" + token,args);
	}
	
	public ArrayList<Room> getJoinedRoom() throws MatrixException {
		ArrayList<Room> joined_room = new ArrayList<Room>();
		String[] roomlist = sdk.RequestGET(baseURL + "joined_rooms?access_token=" + token).split(",");
		for(String elem : roomlist) {
			elem = elem.replace("{\"joined_rooms\":[", "");
			elem = elem.replace("]}", "");
			elem = elem.replace("\"", "");
			joined_room.add(new Room(this,elem));
		}
		return joined_room;
		
	}
	
	protected String getEventDetails(Room room, String eventID) throws MatrixException {
		String request = sdk.RequestGET(baseURL + "rooms/" + room.getRoomID() + "/event/" + eventID + "?access_token=" + token);
		return request;
	}
	
	protected String getRoomInfo(String roomID) throws MatrixException {
		String request = sdk.RequestGET(baseURL + "rooms/" + roomID + "/state?access_token=" + token);
		return request;
	}
	
	@Deprecated
	public String getEmail() throws MatrixException {
			String response = sdk.RequestGET(baseURL + "account/3pid?access_token=" + token);
			return response;
	}
	
	protected String isUserOnline(String userID) throws MatrixException {
			String response = sdk.RequestGET(baseURL + "presence/" + userID +  "/status?access_token=" + token);
			return response;
	}
	
	public Room getRoomByID(String roomID) {
		return new Room(this,roomID);
	}
	
}
