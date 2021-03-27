package fr.epsilonbzh.jmatrix.event;

import java.util.ArrayList;

import fr.epsilonbzh.jmatrix.Event;
import fr.epsilonbzh.jmatrix.MatrixException;
import fr.epsilonbzh.jmatrix.Room;

public abstract class EventListener{
	private Room room;
	private Event lastEvent;
	private int refresh_cooldown;
	
	public EventListener(Room room) {
		this.room = room;
	}
	
	protected abstract void onEvent(Event event) throws MatrixException;
	
	private void listen() throws MatrixException {
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
	
	public void start(int cooldown) {
		refresh_cooldown = cooldown;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					listen();
				} catch (MatrixException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		
	}
		public void start() throws MatrixException {
			this.start(1000);
	}
}
