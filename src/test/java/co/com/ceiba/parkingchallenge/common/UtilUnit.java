package co.com.ceiba.parkingchallenge.common;

import java.time.LocalDateTime;

import org.apache.commons.lang.RandomStringUtils;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.entities.VehicleEntity;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.RuleDisplacement;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.models.RuleDay.PlaceKey;

public class UtilUnit {
	
	/**
	 * 
	 * @param plate
	 * @return
	 */
	protected Vehicle createVehicleCar(String plate) {
		return new Car(
				plate, RandomStringUtils.random(10, true, false), RandomStringUtils.random(4, true, false));
	}
	
	/**
	 * 
	 * @param key
	 * @param valueAdded
	 * @return RuleDay
	 */
	protected Rule createRule(String key, String... days) {
		RuleDay rule = new RuleDay(key);
		rule.setPlace(PlaceKey.START);
		rule.setDays(Lists.newArrayList(days));
		return rule;
	}
	
	/**
	 * 
	 * @param plate
	 * @return
	 */
	protected CarEntity createCarEntity(String plate) {
		return new CarEntity(1L, "Combustion", 
				createVehicleEntity(plate));
	}

	/**
	 * 
	 * @param plate
	 * @return
	 */
	protected MotorbikeEntity createMotorbikeEntity(String plate) {
		return new MotorbikeEntity(1L, 1000, 
				createVehicleEntity(plate));
	}
	
	/**
	 * 
	 * @param plate
	 * @return
	 */
	protected VehicleEntity createVehicleEntity(String plate) {
		return new VehicleEntity( plate,
				RandomStringUtils.random(10, false, true),RandomStringUtils.random(10, true, true));
	}
	
	/**
	 * 
	 * @param state
	 * @param vehicleEntity
	 * @return
	 */
	protected RegistrationEntity createRegistration(StateType state, VehicleEntity vehicleEntity) {
		return new RegistrationEntity(1L, LocalDateTime.now(), state, vehicleEntity);
	}
	
	/**
	 * 
	 * @param hours
	 * @param value
	 * @param type
	 * @return
	 */
	protected TariffEntity createTariff(int hours, double value, String type) {
		return new TariffEntity(1L, hours, value, type);
	}
	
	/**
	 * 
	 * @param key
	 * @param valueAdded
	 * @return RuleDisplacement
	 */
	protected Rule createRule(String key, double valueAdded) {
		RuleDisplacement rule = new RuleDisplacement(key);
		rule.setValueAdded(valueAdded);
		return rule;
	}
	
	/**
	 * 
	 * @param state
	 * @param vehicle
	 * @return
	 */
	protected Registration createRegistration(StateType state, Vehicle vehicle) {
		return new Registration(1l, LocalDateTime.now(), state, vehicle);
	}
	
}
