package co.com.ceiba.parkingchallenge.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class RuleDisplacement extends Rule{
	
	private Double valueAdded;
	
	public RuleDisplacement(String key) {
		super(key);
		this.type = Rule.Type.DISPLACEMENT;
	}
	
	public  boolean isApplyRule(Integer displacement) {
		return Integer.parseInt(this.getKey()) < displacement;
	}
}
