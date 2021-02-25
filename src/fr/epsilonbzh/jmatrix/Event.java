package fr.epsilonbzh.jmatrix;

public class Event {
	private String eventID;
	private Room room;
	private MatrixClient client;
	
	public Event(Room room, String eventID) {
		this.room = room;
		this.eventID = eventID;
		this.client = room.getClient();
	}
	
	private String get(String key, int step) throws MatrixException {
		String request = client.getEventDetails(this.room,this.eventID);
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
	
	public String getEventID() {
		return this.eventID;
	}
	
	public String getBody() throws MatrixException {
		return this.get("body",2);
	}
	public String getSender() throws MatrixException {
		return this.get("sender",2);
	}
	public String getType() throws MatrixException {
		return this.get("type",2);
	}
	
	public String getTimestamp() throws MatrixException {
		return this.get("origin_server_ts",1).replace(":", "").replace(",", "");
	}

}
