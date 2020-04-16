package scraping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scraping.datamodel.Price;
import scraping.datamodel.PriceRepository;
import scraping.properties.AppartmentObjectIds;

@Service
public class ChartsService {
	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private AppartmentObjectIds objectIds;

	private static final String yaxis = "y";
	private static final String xaxis = "x";

	public Map<Object,List<Map<Object, Object>>> getChartData() {
		final Map<Object,List<Map<Object, Object>>> list = new HashMap<Object,List<Map<Object, Object>>>();

		for (String objectId : objectIds.getIds()) {
			final List<Price> pricesForOneObject = priceRepository.findByObjectid(objectId);
			final List<Map<Object, Object>> dataPoints = new ArrayList<Map<Object, Object>>();
			for (Price price : pricesForOneObject) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put(xaxis, price.getReadat().getTime());
				Integer priceInt = convertPriceStringToInteger(price.getPrice());
				map.put(yaxis, priceInt);

				dataPoints.add(map);
			}
			list.put(objectId, dataPoints);

		}
		return list;

	}

	private Integer convertPriceStringToInteger(String price) {
		String priceWithOutCurrency = Optional.ofNullable(price.trim()).filter(sStr -> sStr.length() != 0)
				.map(sStr -> sStr.substring(0, sStr.length() - 1)).orElse(price.trim()).trim();
		String priceWithoutPunkt = priceWithOutCurrency.replace(".", "");
		return Integer.parseInt(priceWithoutPunkt);
	}
}
