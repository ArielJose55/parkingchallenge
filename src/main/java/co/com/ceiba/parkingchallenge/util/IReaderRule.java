package co.com.ceiba.parkingchallenge.util;

import java.io.File;
import java.util.List;

import co.com.ceiba.parkingchallenge.models.Rule;

/**
 * Interfaz que define un metodo para obtener las reglas aplicables a las salidas de vehiculos
 * 
 * @author ariel.arnedo
 *
 */
public interface IReaderRule {

	/**
	 * 
	 * 
	 * @param type Tipo de regla
	 * @return lista de reglas
	 */
	public List<Rule> readerRules( File fileRules, Rule.Type type);
}
