package scraping;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import scraping.properties.AppartmentObjectIds;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PropertiesReadingTest.TestConfiguration.class })
public class PropertiesReadingTest {
	@Autowired
	private AppartmentObjectIds properties;

	@Test
	public void should_Populate_MyConfigurationProperties() {
		assertTrue(properties.getIds()!= null);

	}

	@EnableConfigurationProperties(AppartmentObjectIds.class)
	public static class TestConfiguration {
		// nothing
	}

}
