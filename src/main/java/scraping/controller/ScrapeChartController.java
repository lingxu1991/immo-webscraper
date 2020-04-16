package scraping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import scraping.service.ChartsService;

@Controller
@RequestMapping("/chart")
public class ScrapeChartController {
 
	@Autowired
	private ChartsService chartsService;
 
	@RequestMapping(method = RequestMethod.GET)
	public String springMVC(ModelMap modelMap) {
		Map<Object, List<Map<Object, Object>>> canvasjsDataList = chartsService.getChartData();
		modelMap.addAttribute("dataPointsList", canvasjsDataList);
		return "chart";
	}
 
}       
