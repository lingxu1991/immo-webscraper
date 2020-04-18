package scraping.datamodel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AutoPriceRepository extends CrudRepository<AutoPrice, Integer> {
	List<AutoPrice> findByObjectid(String objectId);

}
