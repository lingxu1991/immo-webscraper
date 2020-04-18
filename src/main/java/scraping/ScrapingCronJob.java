package scraping;

import java.util.Date;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import scraping.datamodel.AutoPrice;
import scraping.datamodel.AutoPriceRepository;
import scraping.datamodel.ImmoPrice;
import scraping.datamodel.ImmoPriceRepository;
import scraping.properties.ObjectIds;

@Service
public class ScrapingCronJob {
	@Autowired
	private ObjectIds objectIds;

	@Autowired
	private ImmoscoutHttpUrlConnection immoConnection;

	@Autowired
	private AutoScoutHttpUrlConnection autoConnection;

	@Autowired
	private ImmoPriceRepository immoPriceRepository;

	@Autowired
	private AutoPriceRepository autoPriceRepository;

	private static String key_immoclass = "is24qa-kaufpreis is24-value font-semibold is24-preis-value";

	private static String key_autoclass = "cldt-price";

	private static String h2_tag = "h2";
	private static String class_attribute = "class";

	@Scheduled(cron = "0 0 12 * * *")
	public void fetchPricing() {
		if (objectIds.getAppartmentIds() != null) {

			for (String objectId : objectIds.getAppartmentIds()) {

				String price = fetchImmoPrice(key_immoclass, objectId, immoConnection.getHTMLPageSouce(objectId));
				System.out.println("The price of immo id-" + objectId + " is " + price);

				ImmoPrice priceModel = new ImmoPrice();
				priceModel.setObjectid(objectId);
				priceModel.setPrice(price);
				priceModel.setReadat(new Date());
				immoPriceRepository.save(priceModel);

			}
		}

		if (objectIds.getAutoIds() != null) {

			for (String objectId : objectIds.getAutoIds()) {

				String price = fetchAutoPrice(key_autoclass, objectId, autoConnection.getHTMLPageSouce(objectId));
				System.out.println("The price of auto id-" + objectId + " is " + price);

				AutoPrice priceModel = new AutoPrice();
				priceModel.setObjectid(objectId);
				priceModel.setPrice(price);
				priceModel.setReadat(new Date());
				autoPriceRepository.save(priceModel);

			}
		}
	}

	private String fetchAutoPrice(String keyclass, String objectId, String htmlPageSouce) {
		String originalPriceText = "0";
		String price = "0";
		Document doc = Jsoup.parse(htmlPageSouce);
		Elements elements = doc.getElementsByAttributeValue(class_attribute, keyclass);

		if (elements != null && !elements.isEmpty()) {
			Element h2Element = elements.get(0);
			if (h2Element != null) {
				originalPriceText = h2Element.getElementsByTag(h2_tag).text();
				String priceWithOutCurrency = Optional.ofNullable(originalPriceText.trim())
						.filter(sStr -> sStr.length() != 0).map(sStr -> sStr.substring(2))
						.orElse(originalPriceText.trim()).trim();
				String priceWithoutPunkt = priceWithOutCurrency.replace(".", "");
				String priceWithoutComma = priceWithoutPunkt.replace(",", "");
				String priceWithoutdash = priceWithoutComma.replace("-", "");
				price = priceWithoutdash;
			}
		}
		return price;
	}

	/**
	 * Fetches the price from the pure html source
	 * @param  string
	 * @return
	 */
	private String fetchImmoPrice(String keyclass, String id, String string) {
		String originalPriceText = "0";
		String price = "0";
		Document doc = Jsoup.parse(string);
		Elements elements = doc.getElementsByAttributeValue(class_attribute, keyclass);

		if (elements != null && !elements.isEmpty()) {
			originalPriceText = elements.get(0).text();
			String priceWithOutCurrency = Optional.ofNullable(originalPriceText.trim())
					.filter(sStr -> sStr.length() != 0).map(sStr -> sStr.substring(0, sStr.length() - 1))
					.orElse(originalPriceText.trim()).trim();
			String priceWithoutPunkt = priceWithOutCurrency.replace(".", "");
			price = priceWithoutPunkt;
		}
		return price;
	}
}
