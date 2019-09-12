package org.sang.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.RespBean;
import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.sang.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/power")
public class PowerController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	PowerService powerService;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public RespBean calc(@RequestBody String jsonstr) {
		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
		Hydrostation hydrostation = jsonObject.getObject("hydrostation", Hydrostation.class);
		CalculateBean calculateBean = jsonObject.getObject("calculateBean", CalculateBean.class);
		if (hydrostation.getLeveldownOutflowCurve().getCurveData().length == 0
				|| hydrostation.getLeveldownOutflowCurve().getCurveData().length == 0
				|| hydrostation.getHeadlossOutflowCurve().getCurveData().length == 0
				|| hydrostation.getExpectOutputHeadCurve().getCurveData().length == 0) {
			// return new RespBean("error", "请重新导入数据");
		}
		int result = powerService.calpower(hydrostation, calculateBean);
//		int result = 1;
		if (result == 1) {
			return new RespBean("success", "计算完成");
		} else {
			return new RespBean("error", "计算失败!，请重新设置参数");
		}
	}

//	
	@RequestMapping(value = "/powerStatistics", method = RequestMethod.GET)
	public Map<String, Object> getPowerByState(@RequestParam(value = "situations") String situations,
			@RequestParam(value = "patterns") String patterns) {
		List<String> xAxis = new ArrayList<String>();
		JSONArray situationsArr = JSON.parseArray(situations);
		JSONArray patternsArr = JSON.parseArray(patterns);
		for (int i = 0; i < patternsArr.size(); i++) {
			String p = patternsArr.getString(i);
			for (int j = 0; j < situationsArr.size(); j++) {
				String s = situationsArr.getString(j);
				xAxis.add(p + "_" + s);
			}
		}
		Map<String, Object> map = new HashMap<>();
		List yAxis = powerService.getPowerByState(xAxis);
		map.put("xAxis", xAxis);
		map.put("yAxis", yAxis);
		return map;
	}

	@RequestMapping(value = "/outputStatistics", method = RequestMethod.GET)
	public Map<String, Object> getOutputByState(@RequestParam(value = "situations") String situations,
			@RequestParam(value = "patterns") String patterns) {
		List<String> xAxis = new ArrayList<String>();
		JSONArray situationsArr = JSON.parseArray(situations);
		JSONArray patternsArr = JSON.parseArray(patterns);
		for (int i = 0; i < patternsArr.size(); i++) {
			String p = patternsArr.getString(i);
			for (int j = 0; j < situationsArr.size(); j++) {
				String s = situationsArr.getString(j);
				xAxis.add(p + "_" + s);
			}
		}
		Map<String, Object> map = new HashMap<>();
		List yAxis = powerService.getOutputByState(xAxis);
		map.put("xAxis", xAxis);
		map.put("yAxis", yAxis);
		return map;
	}
}
