package org.sang.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.DoubleCurve;
import org.sang.bean.hydro.Hydrostation;
import org.sang.bean.hydro.Runoff;
import org.sang.mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PowerService {
	Map<String, double[]> powerMap = new HashMap<String, double[]>();
	Map<String, double[]> outputMap = new HashMap<String, double[]>();
	@Autowired
	PowerMapper powerMapper;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Map<String, Object> calcPowerAndOutput1(Hydrostation hydrostation, CalculateBean calculateBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] situations = calculateBean.getSituations();
		String[] patterns = calculateBean.getPatterns();
		String pattern = calculateBean.getPattern();
		DoubleCurve levelCapacityCurve = new DoubleCurve(hydrostation.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(hydrostation.getLeveldownOutflowCurve().getCurveData());
		DoubleCurve headlossOutflowCurve = new DoubleCurve(hydrostation.getHeadlossOutflowCurve().getCurveData());
		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
		calculateBean.setLevelBegin(hydrostation.getLevelNormal());
		calculateBean.setLevelEnd(hydrostation.getLevelNormal());
		List<double[]> powerList = new ArrayList<double[]>();
		List<double[]> outputList = new ArrayList<double[]>();
		List<String> xAxis = new ArrayList<String>();
		switch (pattern) {
		case "RCP2.6":
			pattern = "26";
			break;
		case "RCP4.5":
			pattern = "45";
			break;
		case "RCP8.5":
			pattern = "85";
			break;
		default:
			pattern = "base";
			break;
		}
		List<Runoff> all = powerMapper.getAllRunoffByPattern(pattern);
		List<Runoff> qf = powerMapper.getFRunoffByPattern(pattern);
		List<Runoff> qp = powerMapper.getPRunoffByPattern(pattern);
		List<Runoff> qk = powerMapper.getKRunoffByPattern(pattern);
		for (int i = 0; i < situations.length; i++) {
			String key = pattern + "_" + situations[i];
			xAxis.add(key);
			double[][] res = new double[4][2];
			res[0] = calPowerAndOutput(hydrostation, calculateBean, all);
			res[1] = calPowerAndOutput(hydrostation, calculateBean, qf);
			res[2] = calPowerAndOutput(hydrostation, calculateBean, qp);
			res[3] = calPowerAndOutput(hydrostation, calculateBean, qk);
			powerList.add(new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
			outputList.add(new double[] { res[0][1] });
			powerMap.put(key, new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
			outputMap.put(key, new double[] { res[0][1] });

		}
		map.put("xAxis", xAxis);
		map.put("powerList", powerList);
		map.put("outputList", outputList);
		System.out.println("计算完成");
		return map;
	}

	public Map<String, Object> calcPowerAndOutput(Hydrostation hydrostation, CalculateBean calculateBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] situations = calculateBean.getSituations();
		String[] patterns = calculateBean.getPatterns();
		String pattern = calculateBean.getPattern();
		DoubleCurve levelCapacityCurve = new DoubleCurve(hydrostation.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(hydrostation.getLeveldownOutflowCurve().getCurveData());
		DoubleCurve headlossOutflowCurve = new DoubleCurve(hydrostation.getHeadlossOutflowCurve().getCurveData());
		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
		calculateBean.setLevelBegin(hydrostation.getLevelNormal());
		calculateBean.setLevelEnd(hydrostation.getLevelNormal());
		List<double[]> powerList = new ArrayList<double[]>();
		List<double[]> outputList = new ArrayList<double[]>();
		List<double[]> outputRateList = new ArrayList<double[]>();
		List<String> xAxis = new ArrayList<String>();
		for (int i = 0; i < situations.length; i++) {
//			for (int j = 0; j < patterns.length; j++) {
//				String key = patterns[j] + "_" + situations[i];
			String key = pattern + "_" + situations[i];
			String patternkey = null;
			String situ = null;
			switch (pattern) {
			case "RCP2.6":
				patternkey = "26";
				break;
			case "RCP4.5":
				patternkey = "45";
				break;
			case "RCP8.5":
				patternkey = "85";
				break;
			default:
				patternkey = "base";
				break;
			}
			switch (situations[i]) {
			case "GFDL":
				situ = "gfdl";
				break;
			case "CNRM":
				situ = "cnrm";
				break;
			case "CanESM":
				situ = "canesm";
				break;
			case "MIROC":
				situ = "miroc";
				break;
			case "BMA":
				situ = "bma";
				break;
			}
			List<Runoff> all = powerMapper.getAllRunoffByPatAndSitu(patternkey, situ);
			List<Runoff> qf = powerMapper.getFRunoffByPatAndSitu(patternkey, situ);
			List<Runoff> qp = powerMapper.getPRunoffByPatAndSitu(patternkey, situ);
			List<Runoff> qk = powerMapper.getKRunoffByPatAndSitu(patternkey, situ);
			double[][] res = new double[4][2];
			res[0] = calPowerAndOutput(hydrostation, calculateBean, all);
			double[] outputRate = sortOutput((int) hydrostation.getInstallPower(), allOutput);
			res[1] = calPowerAndOutput(hydrostation, calculateBean, qf);
			res[2] = calPowerAndOutput(hydrostation, calculateBean, qp);
			res[3] = calPowerAndOutput(hydrostation, calculateBean, qk);
//			xAxis.add(patterns[j] + "_" + situations[i]);
			xAxis.add(pattern + "_" + situations[i]);
			powerList.add(new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
			outputList.add(new double[] { res[0][1] });
			outputRateList.add(outputRate);
			powerMap.put(key, new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
			outputMap.put(key, new double[] { res[0][1] });
//				outputMap.put(key, new double[] { res[0][1], res[1][1], res[2][1], res[3][1] });
//			}
		}
		map.put("xAxis", xAxis);
		map.put("powerList", powerList);
		map.put("outputList", outputList);
		List<String> outputRatexAxis = new ArrayList<String>();
//		for (int k = 0; k < (int) hydrostation.getInstallPower(); k++) {
//			outputRateY.add(k + "");
//		}
		for (int k = 0; k <= 100; k++) {
			outputRatexAxis.add(k + "");
		}
		map.put("outputRatexAxis", outputRatexAxis);
		map.put("outputRateList", outputRateList);
		System.out.println("计算完成");
		return map;
	}

	private double[] sortOutput(int isntallpower, List<Double> output) {
		Collections.sort(output);
		double[] outputRate = new double[100];
		int index = 0;
		int count = 0;
		int len = output.size();
//		for (int i = 0; i < len; i++) {
//			if (output.get(i) > index + 1) {
//				index++;
//			}
//			outputRate[index] = (1 - count++ / len) * 100;
//		}
		for (int i = 99; i >= 0; i--) {
			outputRate[99 - i] = output.get(len * (i + 1) / 100 - 1);
		}
		return outputRate;
	}

	private Map<String, ArrayList<Double>> getRunoff(List<Runoff> runoff) {
		Map<String, ArrayList<Double>> map = new TreeMap<String, ArrayList<Double>>();
		for (int i = 0; i < runoff.size(); i++) {
			String year = runoff.get(i).getYear();
			String data = runoff.get(i).getRunoff();
			if (!map.containsKey(year)) {
				ArrayList<Double> value = new ArrayList<Double>();
				value.add(Double.parseDouble(data));
				map.put(year, value);
			} else {
				map.get(year).add(Double.parseDouble(data));
			}
		}
		return map;
	}

	public Map<String, ArrayList<Double>> getQ(Map<String, ArrayList<Double>> map, String flag) {
		Map<String, ArrayList<Double>> data_map = new TreeMap<String, ArrayList<Double>>();
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			String key = entry.getKey();
//			System.out.println(key);
			ArrayList<Double> value = entry.getValue();
			String year = key.substring(0, 4);
			String month = key.substring(4);
			int m = Integer.parseInt(month);
			int y = Integer.parseInt(year);
			if (m < 6) {
				year = (y - 1) + "";
			}
			switch (flag) {
			case "qp":
				if (m == 11 || m == 5) {
					divData(data_map, value, year);
				}
				break;
			case "qf":
				if (m < 11 & m > 5) {
					divData(data_map, value, year);
				}
				break;
			case "qk":
				if (m > 11 || m < 5) {
					divData(data_map, value, year);
				}
				break;
			case "q":
				divData(data_map, value, year);
				break;
			}

		}
		return data_map;
	}

	private void divData(Map<String, ArrayList<Double>> data_map, ArrayList<Double> value, String year) {
		if (!data_map.containsKey(year)) {
			ArrayList<Double> templist = new ArrayList<Double>();
			data_map.put(year, templist);
		}
		data_map.get(year).addAll(value);
	}

	List<Double> allOutput = null;

	public double[] calPowerAndOutput(Hydrostation hydrostation, CalculateBean bean, List<Runoff> runoff) {
		allOutput = new ArrayList<Double>();
		Map<String, ArrayList<Double>> map = getRunoff(runoff);
		double yearPower = 0;
		List<Double> yearOutput = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			ArrayList<Double> q = entry.getValue();
			int size = q.size();
			for (int j = 0; j < size; j++) {
				bean.setInflow(q.get(j));
				boolean result = hydrostation.calOutputAndPower(bean);
				if (result) {
					yearPower += bean.getPower();
					yearOutput.add(bean.getOutput() / 1000);
					allOutput.add(bean.getOutput() / 1000);
				} else {
					System.out.println("false");
					break;
				}
			}
		}
		Collections.sort(yearOutput);
		double output = yearOutput.get((int) (yearOutput.size() * 0.05));
		double power = yearPower / map.size() / 1e8;
		DecimalFormat df = new DecimalFormat("#.00");
		power = Double.parseDouble(df.format(power));
		output = Double.parseDouble(df.format(output));
		return new double[] { power, output };
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
