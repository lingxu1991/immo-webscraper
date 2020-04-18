package scraping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scraping.datamodel.AutoPrice;
import scraping.datamodel.AutoPriceRepository;
import scraping.datamodel.ImmoPrice;
import scraping.datamodel.ImmoPriceRepository;
import scraping.properties.ObjectIds;

@Service
public class ChartsService {
	@Autowired
	private ImmoPriceRepository immoPriceRepository;
	
	@Autowired
	private AutoPriceRepository autoPriceRepository;

	@Autowired
	private ObjectIds objectIds;

	private static final String yaxis = "y";
	private static final String xaxis = "x";

	public Map<Object,List<Map<Object, Object>>> getAppartmentChartData() {
		final Map<Object,List<Map<Object, Object>>> map = new HashMap<Object,List<Map<Object, Object>>>();

		for (String objectId : objectIds.getAppartmentIds()) {
			final List<ImmoPrice> pricesForOneObject = immoPriceRepository.findByObjectid(objectId);
			final List<Map<Object, Object>> dataPoints = new ArrayList<Map<Object, Object>>();
			for (ImmoPrice price : pricesForOneObject) {
				Map<Object, Object> priceMap = new HashMap<Object, Object>();
				priceMap.put(xaxis, price.getReadat().getTime());
				Integer priceInt = convertPriceStringToInteger(price.getPrice());
				priceMap.put(yaxis, priceInt);

				dataPoints.add(priceMap);
			}
			map.put(objectId, dataPoints);

		}
		return map;

	}

	private Integer convertPriceStringToInteger(String price) {
		return Integer.parseInt(price);
	}

	public Map<Object, List<Map<Object, Object>>> getAutoChartData() {
		final Map<Object,List<Map<Object, Object>>> map = new HashMap<Object,List<Map<Object, Object>>>();

		for (String objectId : objectIds.getAutoIds()) {
			final List<AutoPrice> pricesForOneObject = autoPriceRepository.findByObjectid(objectId);
			final List<Map<Object, Object>> dataPoints = new ArrayList<Map<Object, Object>>();
			for (AutoPrice price : pricesForOneObject) {
				Map<Object, Object> priceMap = new HashMap<Object, Object>();
				priceMap.put(xaxis, price.getReadat().getTime());
				Integer priceInt = convertPriceStringToInteger(price.getPrice());
				priceMap.put(yaxis, priceInt);

				dataPoints.add(priceMap);
			}
			map.put(objectId, dataPoints);

		}
		return map;
	}
}
