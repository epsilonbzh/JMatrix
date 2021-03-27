package fr.epsilonbzh.jmatrix.event;

import java.util.ArrayList;

import fr.epsilonbzh.jmatrix.Event;
import fr.epsilonbzh.jmatrix.MatrixException;
import fr.epsilonbzh.jmatrix.Room;
import fr.epsilonbzh.jmatrix.enums.EventType;

public abstract class EventListener{
	private Room room;
	private Event lastEvent;
	private int refresh_cooldown;
	
	public EventListener(Room room) {
		this.room = room;
	}
	
	protected abstract void onEvent(Event event) throws MatrixException;
	
	private void listen(EventType type) throws MatrixException {
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
				if(type == EventType.ALL) {
					onEvent(current);
				}else {
					if(current.getType().equals(type.getType())) {
						onEvent(current);
					}
				}
			}
			try {
				Thread.sleep(refresh_cooldown);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void start(EventType type,int cooldown) {
		refresh_cooldown = cooldown;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					listen(type);
				} catch (MatrixException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		
	}
		public void start(EventType type) throws MatrixException {
			this.start(type,1000);
	}
		public void start(int cooldown) throws MatrixException {
			this.start(EventType.ALL,cooldown);
	}
		public void start() throws MatrixException {
			this.start(EventType.ALL,1000);
	}
}
