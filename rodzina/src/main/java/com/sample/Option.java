package com.sample;

public class Option {
	public String label; // will be seen
	public Fact fact; // will be added to KnowledgeBase

	public Option(String label, Fact fact){
		this.label = label;
		this.fact = fact;
	}
	public String toString() {
		return label;
	}
}
