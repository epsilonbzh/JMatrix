package fr.epsilonbzh.jmatrix.enums;

public enum MessageType {
	TEXTMESSAGE("m.text"),HTMLMESSAGE("m.text"),IMAGE("m.image"),FILE("m.file"),POOL("org.matrix.options"),CONFETTI("nic.custom.confetti");

	MessageType(String string) {
	}

}
