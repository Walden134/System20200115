package org.sang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.service.RunoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/runoff")
public class RunoffController {

	@Autowired
	RunoffService runoffService;

	@ResponseBody
	@RequestMapping(value = "/runSwat", method = RequestMethod.GET)
	public List<Map<String, Object>> runSwat(String section, String period, String rcp, String gcm, String dtbeg,
			String dtend) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		JsonObject dataProRun = new JsonObject();
		dataProRun.addProperty("pack", "HMCE雅砻江");
		dataProRun.addProperty("user", "sys");
		dataProRun.addProperty("stcd", section);
		dataProRun.addProperty("ipro", "5");
		dataProRun.addProperty("dtbeg", dtbeg);
		dataProRun.addProperty("dtend", dtend);
		dataProRun.addProperty("scene", "");
//		dataProRun.addProperty("paras", "null");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] gcms = { "CanESM", "CNRM", "GFDL", "MIROC" };
		for (int i = 0; i < gcms.length; i++) {
			dataProRun.addProperty("scene", gcms[i] + rcp);
			res = runoffService.runSwat(dataProRun);
			list.add(res);
		}

		System.out.println("计算完成");
		return list;
	}

}