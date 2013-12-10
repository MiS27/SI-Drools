package com.sample;

public class Question {
	public int id = 0;
	public String text;
	public String type;
	public boolean active = false; // might be useless
	public boolean asked = false; // might be useless
	public Option[] possibleAnswers;
	public static String MULTIPLE="multiple";
	public static String SINGLE="single";
	public static String BOOLEAN="boolean";

	public Question(int id, String text, String type, Option[] opt, boolean active) {
		this.id = id;
		this.text = text;
		this.type = type;
		this.possibleAnswers = opt;
		this.active = active;
	}
	public void activate() {
		this.active = true;
	}
	public void asked() {
		this.asked = true;
	}

	public boolean isId(int id) {
		return this.id == id;
	}
}
