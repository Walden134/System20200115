package org.sang.controller;

import java.util.HashMap;
import java.util.Map;

import org.sang.bean.flood.FloodRisk;
import org.sang.bean.flood.GeneralFlood;
import org.sang.service.FloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/flood")
public class FloodController {
	@Autowired
	FloodService floodService;

	@RequestMapping(value = "/expFrequency", method = RequestMethod.GET)
	public Map<String, Object> inputData(@RequestParam(value = "params") String paramsStr) {
		GeneralFlood generalFlood = JSON.parseObject(paramsStr, GeneralFlood.class);
		Map<String, Object> map = floodService.getExpFrequency(generalFlood);
		return map;
	}
	@RequestMapping(value = "/paramEst", method = RequestMethod.GET)
	public Map<String, Object> paramEst(@RequestParam(value = "params") String paramsStr) {
		GeneralFlood generalFlood = JSON.parseObject(paramsStr, GeneralFlood.class);
		Map<String, Object> map = null;
		return map;
	}
	@RequestMapping(value = "/drawLine", method = RequestMethod.GET)
	public Map<String, Object> drawLine(@RequestParam(value = "params") String paramsStr) {
		GeneralFlood generalFlood = JSON.parseObject(paramsStr, GeneralFlood.class);

		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@RequestMapping(value = "/calcRisk", method = RequestMethod.GET)
	public Map<String, Object> calcRisk(@RequestParam(value = "floodRisk") String paramsStr) {
		FloodRisk floodRisk = JSON.parseObject(paramsStr, FloodRisk.class);
		System.out.println(floodRisk);
		Map<String, Object> map = floodService.calcRisk(floodRisk);
		return map;
	}
}
