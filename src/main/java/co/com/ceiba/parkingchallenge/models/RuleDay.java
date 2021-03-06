package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleDay extends Rule {

	private PlaceKey place;

	public enum PlaceKey {
		START("START"),
		END("END"),
		CONTAIN("CONTAIN");

		private String code;

		PlaceKey(String code) {
			this.code = code;
		}
		
		public boolean verifyIfApplicable(String value, String key, List<String> days) {
			if ( valueOf( code ) == START ) {
				return value.startsWith(key)
						&& days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
			} else if ( valueOf( code ) == END ) {
				return value.endsWith(key)
						&& days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
			} else {
				return value.contains(key)
						&& days.stream().filter(s -> s.equals(LocalDateTime.now().getDayOfWeek().name())).count() > 0;
			}
		}
	}

	private List<String> days;

	public RuleDay(String key) {
		super(key);
		this.type = Rule.Type.PLATE;
	}
	//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
}