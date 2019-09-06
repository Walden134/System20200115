package org.sang.controller;

import java.text.SimpleDateFormat;

import org.sang.bean.RespBean;
import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.sang.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/power")
public class PowerController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	PowerService powerService;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public RespBean calc(Hydrostation hydrostation, CalculateBean calculateBean) {
		int result = powerService.calc(hydrostation,calculateBean);
		System.out.println("test");
		if (result == 1) {
			return new RespBean("success", "计算完成");
		} else {
			return new RespBean("error", "计算失败!，请重新设置参数");
		}
	}
}
