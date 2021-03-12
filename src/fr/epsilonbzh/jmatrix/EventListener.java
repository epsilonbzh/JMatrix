package fr.epsilonbzh.jmatrix;

import java.util.ArrayList;

public abstract class EventListener {
	private Room room;
	private static Event lastEvent;
	public EventListener(Room room) {
		this.room = room;
	}
	public abstract void onEvent(Event event);
	public void run(int refresh_cooldown) throws MatrixException {
		ArrayList<Event> eventlist = room.retriveEvent(2147483647);
		lastEvent = eventlist.get(eventlist.size() - 1);
		Event current;
		while(true) {
			eventlist = room.retriveEvent(2147483647);
			current = eventlist.get(eventlist.size() - 1);
			if(current.getEventID().equals(lastEvent.getEventID())) {
			
			}
			else {
				lastEvent = current;
				onEvent(current);
			}
			try {
				Thread.sleep(refresh_cooldown);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
