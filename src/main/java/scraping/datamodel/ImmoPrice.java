package scraping.datamodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ImmoPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String objectid;

	private String price;

	private Date readat;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the objectid
	 */
	public String getObjectid() {
		return objectid;
	}

	/**
	 * @param objectid the objectid to set
	 */
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the readat
	 */
	public Date getReadat() {
		return readat;
	}

	/**
	 * @param readat the readat to set
	 */
	public void setReadat(Date readat) {
		this.readat = readat;
	}

}