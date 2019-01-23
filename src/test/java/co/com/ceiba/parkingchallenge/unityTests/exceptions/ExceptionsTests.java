package co.com.ceiba.parkingchallenge.unityTests.exceptions;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotRegisterVehicleException;
import co.com.ceiba.parkingchallenge.exceptions.NotSaveModelException;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;

public class ExceptionsTests {

	@Test
	public void verifyThrowMessageModelFound() {
		Throwable throwable = catchThrowable(() -> { throw new NotFountModelException("Modelo no encontrado"); });
		assertThat(throwable).isInstanceOf(NotFountModelException.class)
        .hasMessageContaining("Modelo no encontrado");
	}
	
	@Test
	public void verifyThrowMessageNotRegister() {
		Throwable throwable = catchThrowable(() -> { throw new NotRegisterVehicleException("No se registro la entrada"); });
		assertThat(throwable).isInstanceOf(NotRegisterVehicleException.class)
        .hasMessageContaining("No se registro la entrada");
	}
	
	@Test
	public void verifyThrowMessageNotSave() {
		Throwable throwable = catchThrowable(() -> { throw new NotSaveModelException("No se almaceno el modelo"); });
		assertThat(throwable).isInstanceOf(NotSaveModelException.class)
        .hasMessageContaining("No se almaceno el modelo");
	}
	
	@Test
	public void verifyThrowMessageVialatedRule() {
		Throwable throwable = catchThrowable(() -> { throw new ViolatedConstraintException("Restriccion de salida o entrada violada"); });
		assertThat(throwable).isInstanceOf(ViolatedConstraintException.class)
        .hasMessageContaining("Restriccion de salida o entrada violada");
	}
}
