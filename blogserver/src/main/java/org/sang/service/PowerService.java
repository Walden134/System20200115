package org.sang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PowerService {
	Map<String, double[]> powerMap = new HashMap<String, double[]>();
	Map<String, double[]> outputMap = new HashMap<String, double[]>();

	public int calpower(Hydrostation hydrostation, CalculateBean calculateBean) {
		List<Map<String, double[]>> powerAndOutput = PowerCalcData.calcPowerAndOutput(hydrostation, calculateBean);
		powerMap = powerAndOutput.get(0);
		outputMap = powerAndOutput.get(1);
		System.out.println("计算完成");
		return 1;
	}

	public List<double[]> getPowerByState(List<String> xAxis) {
		List<double[]> powerList = new ArrayList<double[]>();
		if (powerMap.size() == 0) {
			return null;
		}
		for (int i = 0; i < xAxis.size(); i++) {
			String str = xAxis.get(i);
			double[] ds = powerMap.get(str);
			powerList.add(ds);
		}
		return powerList;
	}

	public List<double[]> getOutputByState(List<String> xAxis) {
		List<double[]> powerList = new ArrayList<double[]>();
		if (outputMap.size() == 0) {
			return null;
		}
		for (int i = 0; i < xAxis.size(); i++) {
			String str = xAxis.get(i);
			double[] ds = outputMap.get(str);
			powerList.add(ds);
		}

		return powerList;
	}
}
