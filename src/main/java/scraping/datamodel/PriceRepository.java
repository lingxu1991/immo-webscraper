package scraping.datamodel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Integer> {
	List<Price> findByObjectid(String objectId);

}
