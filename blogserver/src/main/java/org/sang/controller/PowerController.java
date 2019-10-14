package org.sang.controller;

import java.util.Map;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.sang.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/power")
public class PowerController {

	@Autowired
	PowerService powerService;

	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public Map<String, Object> calcPowerAndOutput(@RequestParam(value = "hydrostation") String hydrostr,
			@RequestParam(value = "calculateBean") String calcStr) {
		Hydrostation hydrostation = JSON.parseObject(hydrostr, Hydrostation.class);
		CalculateBean calculateBean = JSON.parseObject(calcStr, CalculateBean.class);
		if (hydrostation.getLeveldownOutflowCurve().getCurveData().length == 0
				|| hydrostation.getLeveldownOutflowCurve().getCurveData().length == 0
				|| hydrostation.getHeadlossOutflowCurve().getCurveData().length == 0
				|| hydrostation.getExpectOutputHeadCurve().getCurveData().length == 0) {
			return null;
		}
		long start = System.currentTimeMillis();
		Map<String, Object> map = powerService.calcPowerAndOutput1(hydrostation, calculateBean);
		long end = System.currentTimeMillis();
		System.out.println("计算完成" + (end - start));
		return map;
	}
//
//	@RequestMapping(value = "/powerStatistics", method = RequestMethod.GET)
//	public Map<String, Object> getPowerByState(@RequestParam(value = "situations") String situations,
//			@RequestParam(value = "patterns") String patterns) {
//		List<String> xAxis = new ArrayList<String>();
//		JSONArray situationsArr = JSON.parseArray(situations);
//		JSONArray patternsArr = JSON.parseArray(patterns);
//		for (int i = 0; i < patternsArr.size(); i++) {
//			String p = patternsArr.getString(i);
//			for (int j = 0; j < situationsArr.size(); j++) {
//				String s = situationsArr.getString(j);
//				xAxis.add(p + "_" + s);
//			}
//		}
//		Map<String, Object> map = new HashMap<>();
//		List yAxis = powerService.getPowerByState(xAxis);
//		map.put("xAxis", xAxis);
//		map.put("yAxis", yAxis);
//		return map;
//	}
//
//	@RequestMapping(value = "/outputStatistics", method = RequestMethod.GET)
//	public Map<String, Object> getOutputByState(@RequestParam(value = "situations") String situations,
//			@RequestParam(value = "patterns") String patterns) {
//		List<String> xAxis = new ArrayList<String>();
//		JSONArray situationsArr = JSON.parseArray(situations);
//		JSONArray patternsArr = JSON.parseArray(patterns);
//		for (int i = 0; i < patternsArr.size(); i++) {
//			String p = patternsArr.getString(i);
//			for (int j = 0; j < situationsArr.size(); j++) {
//				String s = situationsArr.getString(j);
//				xAxis.add(p + "_" + s);
//			}
//		}
//		Map<String, Object> map = new HashMap<>();
//		List yAxis = powerService.getOutputByState(xAxis);
//		map.put("xAxis", xAxis);
//		map.put("yAxis", yAxis);
//		return map;
//	}
}
