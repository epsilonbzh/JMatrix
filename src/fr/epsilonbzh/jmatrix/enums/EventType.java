package fr.epsilonbzh.jmatrix.enums;

public enum EventType {
	ALL("all"),MESSAGE("m.room.message"),ENCRYPTED("m.room.encrypted"),DELETE("m.room.redaction"),STICKER("m.sticker"),REACTION("m.reaction"),MEMBER_MANAGEMENT("m.room.member"),PERMISSION_MANAGEMENT("m.room.power_levels"),ROOMNAME_EDIT("m.room.name"),TOPIC_EDIT("m.room.topic"),PREVIEW_URLS("org.matrix.room.preview_urls"),GUEST_ACCESS("m.room.guest_access"),JOIN_RULES("m.room.join_rules"),HISTORY_VISIBILITY("m.room.history_visibility");
	private String type;
	
	public String getType() {
		return type;
	}
	
	EventType(String type) {
		this.type = type;
	}
}
