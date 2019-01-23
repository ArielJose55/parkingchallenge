package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RuleDay extends Rule{

	private PlaceKey place;
	
	public enum PlaceKey{
		START("START"),
		END("END"),
		CONTAIN("CONTAIN");
	    
	    private  String code;

	    PlaceKey(String code) {
	        this.code = code;
	    }

	    @Override
	    public String toString() {
	        return code;
	    }

	}
	
	private List<String> days;
	
	public RuleDay(String key) {
		super(key);
		this.type = Rule.Type.PLATE;
	}
	
	public boolean compare(PlaceKey place, String value, String key, List<String> days) {
		switch (place) {
		case START:
			return value.startsWith(key) && days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
		
		case END:
			return value.endsWith(key) && days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
		
		default:
			return value.contains(key) && days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
		}
	}
	//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
}