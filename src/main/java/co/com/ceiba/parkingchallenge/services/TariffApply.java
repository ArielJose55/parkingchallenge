package co.com.ceiba.parkingchallenge.services;

public enum TariffApply {
	HOUR_APPLY(1),
	HOURS_APPLY_PER_DAY(24),
	HOURS_START_DAY(9);
	
	private int hours;
	
	private TariffApply(int hours) {
		this.hours = hours;
	}
}
