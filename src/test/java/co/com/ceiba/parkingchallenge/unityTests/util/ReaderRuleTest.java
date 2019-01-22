package co.com.ceiba.parkingchallenge.unityTests.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@RunWith(SpringRunner.class)
public class ReaderRuleTest {

	@Mock
	private ReaderContraintXml reader;
	
	@Test
	public void readerRuleTest() {
		when(reader.readerRules(new File("rules.xml") , Rule.Type.PLATE)).thenReturn(Lists.newArrayList(
				createRuleAleatory("A", "SATURDAY", "SUNDAY")
		));
		
		assertThat(reader.readerRules(new File("rules.xml") ,Rule.Type.PLATE).size())
			.isEqualTo(1);
	}
	
	@Test
	public void readerApplyRuleTest() {
		when(reader.readerRules(new File("rules.xml"), Rule.Type.PLATE)).thenReturn(Lists.newArrayList(
				createRuleAleatory("A", "SATURDAY", "SUNDAY")
		));
		
		assertThat(reader.readerRules(new File("rules.xml"), Rule.Type.PLATE).get(0))
			.isInstanceOf(RuleDay.class);
	}
	
	@Test
	public void readerRulesTestFail() {
		File file = new File("ARHIVO");
		
		ReaderContraintXml reader = new ReaderContraintXml();
		try {
			reader.readerRules(file, Rule.Type.PLATE);
		}catch (UnsupportedOperationException e) {
			assertThat(e)
				.hasMessageStartingWith("Error al leer archivo ");
		}		
	}
	
	/**
	 * 
	 * @param key key a la que applica la restriccion 
	 * @param days
	 * @return
	 */
	private RuleDay createRuleAleatory(String key, String... days) {
		RuleDay rule = new RuleDay(key);
		rule.setDays(
			Lists.newArrayList(days)
		);
		return rule;
	}

}
