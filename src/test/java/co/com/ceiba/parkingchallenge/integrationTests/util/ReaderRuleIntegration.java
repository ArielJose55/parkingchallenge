package co.com.ceiba.parkingchallenge.integrationTests.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.RuleDisplacement;
import co.com.ceiba.parkingchallenge.util.IReaderRule;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

public class ReaderRuleIntegration {
	
	private File fileRules;
	
	@Before
	public void setUp() {
		fileRules = new File("rules.xml");
	}
	
	@Test
	public void existFileXmlRule() {
		assertThat(fileRules).exists();
	}
	
	@Test
	public void applyReaderRuleDay() {
		IReaderRule reader = new ReaderContraintXml();
		assertThat(reader.readerRules(fileRules ,Rule.Type.PLATE))
			.filteredOn(r -> r.getType().equals(Rule.Type.PLATE))
			.first()
			.isInstanceOf(RuleDay.class);
	}
	
	@Test
	public void applyReaderRuleDisplacement() {
		
		IReaderRule reader = new ReaderContraintXml();
		assertThat(reader.readerRules(fileRules ,Rule.Type.DISPLACEMENT))
			.filteredOn(r -> r.getType().equals(Rule.Type.DISPLACEMENT))
			.first()
			.isInstanceOf(RuleDisplacement.class);
	}
}
