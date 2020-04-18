package scraping.datamodel;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ImmoPriceRepository extends CrudRepository<ImmoPrice, Integer> {
	List<ImmoPrice> findByObjectid(String objectId);

}
