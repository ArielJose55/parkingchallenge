package co.com.ceiba.parkingchallenge.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.RuleDay.PlaceKey;
import co.com.ceiba.parkingchallenge.models.RuleDisplacement;

/**
 * Clase para obtener reglas a partir de un archivo XML
 * 
 * @author ariel.arnedo
 *
 */
public class ReaderContraintXml implements IReaderRule {

	
	
	@Override
	public List<Rule> readerRules(File fileRules, Rule.Type type ){
		SAXBuilder reader = new SAXBuilder();
        Document document;
		try {
			document = reader.build(fileRules);
		} catch (JDOMException | IOException e) {
			throw new UnsupportedOperationException("Error al leer archivo " + fileRules.getName(), e);
		}
		
        return document.getRootElement().getChildren("rule")
        		.stream()
        		.filter(e -> e.getAttribute("type").getValue().equals(type.name()))
        		.map(e -> mapper(e, type))
        		.collect(Collectors.toList());
	}
	
	public Rule mapper(Element element, Rule.Type type) {
		if(type.compareTo(Rule.Type.PLATE) == 0) {
			
			RuleDay rule = new RuleDay(element.getChildTextTrim("key"));
			rule.setPlace(PlaceKey.valueOf(element.getChildTextTrim("place")));
			rule.setDays(element.getChildren("day")
					.stream()
					.map( Element::getValue )
					.collect(Collectors.toList()));
			return rule;
			
		} else {
			
			RuleDisplacement rule = new RuleDisplacement(element.getChildTextTrim("key"));
			rule.setValueAdded(Double.valueOf(element.getChildTextTrim("value")));
			return rule;
		} 
	}
}
