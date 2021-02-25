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
	
	private String get(String key) throws MatrixException {
		String request = client.getEventDetails(this.room,this.eventID);
		String[] content = request.split("\"");
		int i = 0;
		String find = "";
		for(String elem : content) {
			if(elem.equals(key)) {
				find = content[i+2];
			}
			i++;
		}
		return find;
		
	}
	
	public String getEventID() {
		return this.eventID;
	}
	
	public String getBody() throws MatrixException {
		return this.get("body");
	}
	public String getSender() throws MatrixException {
		return this.get("sender");
	}
	public String getType() throws MatrixException {
		return this.get("type");
	}

}
