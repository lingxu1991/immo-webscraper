package scraping.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ObjectIds {

	List<String> appartmentIds;
	
	List<String> autoIds;

	/**
	 * @return the autoIds
	 */
	public List<String> getAutoIds() {
		return autoIds;
	}

	/**
	 * @param autoIds the autoIds to set
	 */
	public void setAutoIds(List<String> autoIds) {
		this.autoIds = autoIds;
	}

	/**
	 * @return the appartmentIds
	 */
	public  List<String> getAppartmentIds() {
		return appartmentIds;
	}

	/**
	 * @param appartmentIds the appartmentIds to set
	 */
	public void setAppartmentIds(List<String> appartmentIds) {
		this.appartmentIds = appartmentIds;
	}

}
