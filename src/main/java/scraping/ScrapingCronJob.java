package scraping;


import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import scraping.datamodel.Price;
import scraping.datamodel.PriceRepository;
import scraping.properties.AppartmentObjectIds;

@Service
public class ScrapingCronJob {
	@Autowired
	private AppartmentObjectIds objectIds;

	@Autowired
	private ImmoscoutHttpUrlConnection connection;
	
    @Autowired 
    private PriceRepository priceRepository;

	@Scheduled(cron = "0 0 12 * * *")
	public void fetchPricing() {
		if (objectIds.getIds() != null) {

			for (String objectId : objectIds.getIds()) {

				String price = fetchPrice(objectId, connection.getHTMLPageSouce(objectId));
				System.out.println("The price of id-" + objectId + " is " + price);
				
				Price priceModel = new Price();
				priceModel.setObjectid(objectId);
				priceModel.setPrice(price);
				priceModel.setReadat(new Date());
				priceRepository.save(priceModel);

			}
		}
	}

	/**
	 * Fetches the price from the pure html source
	 * @param  string
	 * @return
	 */
	private String fetchPrice(String id, String string) {
		String price = "";
		Document doc = Jsoup.parse(string);
		Elements elements = doc.getElementsByAttributeValue("class",
				"is24qa-kaufpreis is24-value font-semibold is24-preis-value");

		if (elements != null) {
			price = elements.get(0).text();
		}
		return price;
	}
}
