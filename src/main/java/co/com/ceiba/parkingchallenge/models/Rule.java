package co.com.ceiba.parkingchallenge.models;

import lombok.Getter;

@Getter
public class Rule {

	protected String key;
	
	protected Type type;
	
	public enum Type {
		PLATE,
		DISPLACEMENT
	}
	
	public Rule(String key) {
		this.key = key;
	}
}
