package org.sang.controller;

import java.text.SimpleDateFormat;

import org.sang.bean.RespBean;
import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.sang.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/power")
public class PowerController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	PowerService powerService;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public RespBean calc1(@RequestBody String jsonstr) {
		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
		Hydrostation hydrostation = jsonObject.getObject("hydrostation", Hydrostation.class);
		CalculateBean calculateBean = jsonObject.getObject("calculateBean", CalculateBean.class);
		
		int result = powerService.calc(hydrostation,calculateBean);
		if (result == 1) {
			return new RespBean("success", "计算完成");
		} else {
			return new RespBean("error", "计算失败!，请重新设置参数");
		}
	}

	@RequestMapping(value = "/submit1", method = RequestMethod.POST)
	public RespBean calc(@RequestBody Hydrostation hydrostation, @RequestBody CalculateBean calculateBean) {
		int result = powerService.calc(hydrostation, calculateBean);

		if (result == 1) {
			return new RespBean("success", "计算完成");
		} else {
			return new RespBean("error", "计算失败!，请重新设置参数");
		}
	}
}
