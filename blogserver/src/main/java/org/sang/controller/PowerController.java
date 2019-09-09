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
		int result = powerService.calc(hydrostation, calculateBean);
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
		System.out.println(situations);
		List<String> list = new ArrayList<String>();
		JSONArray situationsArr = JSON.parseArray(situations);
		JSONArray patternsArr = JSON.parseArray(patterns);
		for (int i = 0; i < patternsArr.size(); i++) {
			String p = patternsArr.getString(i);
			for (int j = 0; j < situationsArr.size(); j++) {
				String s = situationsArr.getString(j);
				list.add(p + "_" + s);
			}
		}
		System.out.println(Arrays.toString(list.toArray()));
		Map<String, Object> map = new HashMap<>();
		map.put("xAxis", list);
		map.put("yAxis", "");
		return map;
	}

	@RequestMapping(value = "/outputStatistics", method = RequestMethod.GET)
	public Map<String, Object> getOutputByState(@RequestParam(value = "situations") String situations,
			@RequestParam(value = "patterns") String patterns) {
		List<String> list = new ArrayList<String>();
		JSONArray situationsArr = JSON.parseArray(situations);
		JSONArray patternsArr = JSON.parseArray(patterns);
		for (int i = 0; i < patternsArr.size(); i++) {
			String p = patternsArr.getString(i);
			for (int j = 0; j < situationsArr.size(); j++) {
				String s = situationsArr.getString(j);
				list.add(p + "_" + s);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("xAxis", list);
		map.put("yAxis", "");
		return map;
	}
}
