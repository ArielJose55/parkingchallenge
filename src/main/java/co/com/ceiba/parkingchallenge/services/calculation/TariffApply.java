package co.com.ceiba.parkingchallenge.services.calculation;

public enum TariffApply {
	BY_HOUR(1), // tarifa por unidad de tiempo: horas
	BY_DAY(24), // tarifa por dia en unidades de tiempo: horas
	HOUR_LIMETE_START_DAY(9); //cantidad de unidad de tiempo (horas) en el cual se comienza a cobrar por dia
	
	private int numberTieme;
	
	private TariffApply(int numberTieme) {
		this.numberTieme = numberTieme;
	}
	
	public int getVelue() {
		return this.numberTieme;
	}
}
