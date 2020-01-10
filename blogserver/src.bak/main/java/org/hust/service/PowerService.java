package org.hust.service;

import org.hust.bean.hydro.CalculateBean;
import org.hust.bean.hydro.DoubleCurve;
import org.hust.bean.hydro.Hydrostation;
import org.hust.bean.hydro.Runoff;
import org.hust.mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class PowerService {
	Map<String, double[]> powerMap = new HashMap<String, double[]>();
	Map<String, double[]> outputMap = new HashMap<String, double[]>();
	@Autowired
	PowerMapper powerMapper;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df = new DecimalFormat("#.00");
	List<Double> allOutput;
	Calendar cal = Calendar.getInstance();

	public Map<String, Object> calcPowerAndOutput1(Hydrostation hydrostation, CalculateBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pattern = bean.getPattern();
		DoubleCurve levelCapacityCurve = new DoubleCurve(hydrostation.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(hydrostation.getLeveldownOutflowCurve().getCurveData());
		DoubleCurve headlossOutflowCurve = new DoubleCurve(hydrostation.getHeadlossOutflowCurve().getCurveData());
		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
		bean.setLevelBegin(hydrostation.getLevelNormal());
		bean.setLevelEnd(hydrostation.getLevelNormal());
		List<double[]> powerList = new ArrayList<double[]>();
		List<List<Double>> powerONEList = new ArrayList<List<Double>>();
		List<double[]> outputList = new ArrayList<double[]>();
		List<double[]> outputRateList = new ArrayList<double[]>();
		List<String> xAxis = new ArrayList<String>();
		List<String> xAxisONE = new ArrayList<String>();
		String patternkey = null;
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
		List<Runoff> all1 = powerMapper.getAllRunoffByPattern(patternkey);
		Map<String, Map<String, List<Runoff>>> dataBySitu = divDataBySitu(all1);
		for (Map.Entry<String, Map<String, List<Runoff>>> entry : dataBySitu.entrySet()) {
			String situ = entry.getKey();
			xAxis.add(pattern + "_" + situ.toUpperCase());// xAxis
			double[] powers = new double[4];// all F P K
			List<Double> yearPowers = new ArrayList<Double>();
			List<Double> yearOutputs = new ArrayList<Double>();
			List<Double> yearPowersf = new ArrayList<Double>();
			List<Double> yearPowersp = new ArrayList<Double>();
			List<Double> yearPowersk = new ArrayList<Double>();
//			List<Runoff> all = entry.getValue().get("q");
			List<Runoff> qf = entry.getValue().get("qf");
			List<Runoff> qp = entry.getValue().get("qp");
			List<Runoff> qk = entry.getValue().get("qk");
//			res[0] = calPowerAndOutput(hydrostation, bean, all);
			powers[1] = calPowerAndOutput(hydrostation, bean, qf, yearOutputs, yearPowersf);
			powers[2] = calPowerAndOutput(hydrostation, bean, qp, yearOutputs, yearPowersp);
			powers[3] = calPowerAndOutput(hydrostation, bean, qk, yearOutputs, yearPowersk);
			powers[0] = Double.parseDouble(df.format(powers[1] + powers[2] + powers[3]));
			powerList.add(powers);// powerList

			Collections.sort(yearOutputs);
			System.out.println("yearOutputs" + yearOutputs);
			double output = yearOutputs.get((int) (yearOutputs.size() * 0.05));
			output = Double.parseDouble(df.format(output));
			outputList.add(new double[] { output });// outputList

			double[] outputRate = getOutputRate((int) hydrostation.getInstallPower(), yearOutputs);
			outputRateList.add(outputRate);// outputRateList
			int len = yearPowersk.size();
			for (int i = 0; i < len; i++) {
				double power = yearPowersf.get(i) + yearPowersp.get(i) + yearPowersk.get(i);
				power = Double.parseDouble(df.format(power));
				yearPowers.add(power);
			}
			powerONEList.add(yearPowers);// powerONEList

		}
		cal.setTime(dataBySitu.get("canesm").get("qf").get(0).getDate());
		int start_year = cal.get(Calendar.YEAR);
		for (int i = 0; i < powerONEList.get(0).size(); i++) {
			xAxisONE.add((start_year + i) + "");// xAxisONE
		}
		List<String> outputRatexAxis = new ArrayList<String>();
		for (int k = 0; k <= 100; k++) {
			outputRatexAxis.add(k + "");// outputRatexAxis
		}

		map.put("xAxis", xAxis);
		map.put("powerList", powerList);
		map.put("xAxisONE", xAxisONE);
		map.put("powerONEList", powerONEList);
		map.put("outputList", outputList);
		map.put("outputRatexAxis", outputRatexAxis);
		map.put("outputRateList", outputRateList);
		return map;
	}

	public double calPowerAndOutput(Hydrostation hydrostation, CalculateBean bean, List<Runoff> runoff,
			List<Double> yearOutputs, List<Double> yearPowers) {
		Map<String, ArrayList<Double>> map = getRunoff(runoff);
		double yearPower = 0;
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			ArrayList<Double> q = entry.getValue();
			int size = q.size();
			double tempPower = 0;
			for (int j = 0; j < size; j++) {
				bean.setInflow(q.get(j));
				boolean result = hydrostation.calOutputAndPower(bean);
				if (result) {
					tempPower += bean.getPower();
					yearOutputs.add(bean.getOutput() / 1000);
				} else {
					yearOutputs.add(Double.MIN_VALUE);
					break;
				}
			}
			yearPower += tempPower;
			yearPowers.add(tempPower / 1e8);
		}

		double power = yearPower / map.size() / 1e8;
		power = Double.parseDouble(df.format(power));
		return power;
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

	private Map<String, Map<String, List<Runoff>>> divDataBySitu(List<Runoff> all) {
		Map<String, Map<String, List<Runoff>>> res = new HashMap<String, Map<String, List<Runoff>>>();
		for (Runoff runoff : all) {
			String gcmId = runoff.getGcmId();
			Date date = runoff.getDate();
			int m = date.getMonth() + 1;
			boolean containsKey = res.containsKey(gcmId);
			if (containsKey) {
				if (m < 11 & m > 5)
					res.get(gcmId).get("qf").add(runoff);
				else if (m == 11 || m == 5)
					res.get(gcmId).get("qp").add(runoff);
				else if (m > 11 || m < 5)
					res.get(gcmId).get("qk").add(runoff);
			} else {
				HashMap<String, List<Runoff>> map = new HashMap<String, List<Runoff>>();
				List<Runoff> qf = new ArrayList<Runoff>();
				List<Runoff> qp = new ArrayList<Runoff>();
				List<Runoff> qk = new ArrayList<Runoff>();
				List<Runoff> q = new ArrayList<Runoff>();
				map.put("qf", qf);
				map.put("qk", qk);
				map.put("qp", qp);
				map.put("q", q);
				res.put(gcmId, map);
				if (m < 11 & m > 5)
					res.get(gcmId).get("qf").add(runoff);
				else if (m == 11 || m == 5)
					res.get(gcmId).get("qp").add(runoff);
				else if (m > 11 || m < 5)
					res.get(gcmId).get("qk").add(runoff);
			}
		}
		return res;
	}

	private Map<String, List<Runoff>> divDataByMonth(List<Runoff> all) {
		List<Runoff> qf = new ArrayList<Runoff>();
		List<Runoff> qp = new ArrayList<Runoff>();
		List<Runoff> qk = new ArrayList<Runoff>();
		Map<String, List<Runoff>> map = new HashMap<String, List<Runoff>>();
		for (Runoff runoff : all) {
			Date date = runoff.getDate();
			int m = date.getMonth();
			if (m < 11 & m > 5)
				qf.add(runoff);
			if (m == 11 || m == 5)
				qp.add(runoff);
			if (m > 11 || m < 5)
				qk.add(runoff);
		}
		map.put("qf", qf);
		map.put("qk", qk);
		map.put("qp", qp);
		return map;
	}

	private double[] getOutputRate(int isntallpower, List<Double> output) {
		double[] outputRate = new double[100];
		int len = output.size();
		for (int i = 99; i >= 0; i--) {
			outputRate[99 - i] = Double.parseDouble(df.format(output.get(len * (i + 1) / 100 - 1)));
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

}
