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

}
