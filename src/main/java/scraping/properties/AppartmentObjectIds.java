package scraping.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AppartmentObjectIds {

	List<String> ids;
	
	/**
	 * @return the ids
	 */
	public final List<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public final void setIds(List<String> ids) {
		this.ids = ids;
	}
}
