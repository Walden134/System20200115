package org.hust.controller;

import com.alibaba.fastjson.JSON;
import org.hust.bean.flood.FloodRisk;
import org.hust.bean.flood.GeneralFlood;
import org.hust.bean.flood.UncertainP;
import org.hust.service.FloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        Map<String, Object> map = floodService.paramEst(generalFlood);
        return map;
    }

    @RequestMapping(value = "/drawLine", method = RequestMethod.GET)
    public Map<String, Object> drawLine(@RequestParam(value = "params") String paramsStr) {
        GeneralFlood generalFlood = JSON.parseObject(paramsStr, GeneralFlood.class);
        Map<String, Object> map = floodService.drawLine(generalFlood);
        return map;
    }

    @RequestMapping(value = "/calcRisk", method = RequestMethod.GET)
    public Map<String, Object> calcRisk(@RequestParam(value = "floodRisk") String paramsStr) {
        FloodRisk floodRisk = JSON.parseObject(paramsStr, FloodRisk.class);
        Map<String, Object> map = floodService.calcRisk(floodRisk);
        return map;
    }

    @RequestMapping(value = "/readBaseP", method = RequestMethod.GET)
    public Map<String, Object> readBaseP(@RequestParam(value = "time") String paramsStr) {
        UncertainP time = JSON.parseObject(paramsStr, UncertainP.class);
        Map<String, Object> map = floodService.readBaseP(time);
        return map;
    }

    @RequestMapping(value = "/readBaseFlood", method = RequestMethod.GET)
    public Map<String, Object> readBaseFlood(@RequestParam(value = "time") String paramsStr) {
        UncertainP time = JSON.parseObject(paramsStr, UncertainP.class);
        Map<String, Object> map = floodService.readBaseFlood(time);
        return map;
    }
}
