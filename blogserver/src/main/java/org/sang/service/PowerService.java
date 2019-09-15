package org.sang.service;

import java.math.BigDecimal;
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

//	public int calcPowerAndOutput(Hydrostation hydrostation, CalculateBean calculateBean) {
////		List<Map<String, double[]>> powerAndOutput = PowerCalcData.calcPowerAndOutput(hydrostation, calculateBean);
////		powerMap = powerAndOutput.get(0);
////		outputMap = powerAndOutput.get(1);
//
//		String[] situations = calculateBean.getSituations();
//		String[] patterns = calculateBean.getPatterns();
//		DoubleCurve levelCapacityCurve = new DoubleCurve(hydrostation.getLevelCapacityCurve().getCurveData());
//		DoubleCurve leveldownOutflowCurve = new DoubleCurve(hydrostation.getLeveldownOutflowCurve().getCurveData());
//		DoubleCurve headlossOutflowCurve = new DoubleCurve(hydrostation.getHeadlossOutflowCurve().getCurveData());
//		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
//		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
//		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
//		for (int i = 0; i < situations.length; i++) {
//			for (int j = 0; j < patterns.length; j++) {
//				String key = patterns[j] + "_" + situations[i];
//				String pattern = null;
//				String situ = null;
//				switch (patterns[j]) {
//				case "RCP2.6":
//					pattern = "26";
//					break;
//				case "RCP4.5":
//					pattern = "45";
//					break;
//				case "RCP8.5":
//					pattern = "85";
//					break;
//				default:
//					pattern = "base";
//					break;
//				}
//				switch (situations[i]) {
//				case "GFDL":
//					situ = "gfdl";
//					break;
//				case "CNRM":
//					situ = "cnrm";
//					break;
//				case "CanESM":
//					situ = "canesm";
//					break;
//				case "MIROC":
//					situ = "miroc";
//					break;
//				case "BMA":
//					situ = "bma";
//					break;
//				}
//				List<Runoff> all = powerMapper.getAllRunoffByPatAndSitu(pattern, situ);
//				List<Runoff> qf = powerMapper.getFRunoffByPatAndSitu(pattern, situ);
//				List<Runoff> qp = powerMapper.getPRunoffByPatAndSitu(pattern, situ);
//				List<Runoff> qk = powerMapper.getKRunoffByPatAndSitu(pattern, situ);
//				double[][] res = new double[4][2];
//				res[0] = calPowerAndOutput(hydrostation, calculateBean, all);
//				res[1] = calPowerAndOutput(hydrostation, calculateBean, qf);
//				res[2] = calPowerAndOutput(hydrostation, calculateBean, qp);
//				res[3] = calPowerAndOutput(hydrostation, calculateBean, qk);
//				powerMap.put(key, new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
//				outputMap.put(key, new double[] { res[0][1], res[1][1], res[2][1], res[3][1] });
//			}
//		}
//		System.out.println("计算完成");
//		return 1;
//	}
	public Map<String, Object> calcPowerAndOutput(Hydrostation hydrostation, CalculateBean calculateBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] situations = calculateBean.getSituations();
		String[] patterns = calculateBean.getPatterns();
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

		for (int i = 0; i < situations.length; i++) {
			for (int j = 0; j < patterns.length; j++) {
				String key = patterns[j] + "_" + situations[i];
				String pattern = null;
				String situ = null;
				switch (patterns[j]) {
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
				List<Runoff> all = powerMapper.getAllRunoffByPatAndSitu(pattern, situ);
				List<Runoff> qf = powerMapper.getFRunoffByPatAndSitu(pattern, situ);
				List<Runoff> qp = powerMapper.getPRunoffByPatAndSitu(pattern, situ);
				List<Runoff> qk = powerMapper.getKRunoffByPatAndSitu(pattern, situ);
				double[][] res = new double[4][2];
				res[0] = calPowerAndOutput(hydrostation, calculateBean, all);
				res[1] = calPowerAndOutput(hydrostation, calculateBean, qf);
				res[2] = calPowerAndOutput(hydrostation, calculateBean, qp);
				res[3] = calPowerAndOutput(hydrostation, calculateBean, qk);
				xAxis.add(patterns[j] + "_" + situations[i]);

				powerList.add(new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
				outputList.add(new double[] { res[0][1] });
				powerMap.put(key, new double[] { res[0][0], res[1][0], res[2][0], res[3][0] });
				outputMap.put(key, new double[] { res[0][1], res[1][1], res[2][1], res[3][1] });
			}
		}
		map.put("xAxis", xAxis);
		map.put("powerList", powerList);
		map.put("outputList", outputList);
		System.out.println("计算完成");
		return map;
	}

	private Map<String, ArrayList<Double>> getRunoff(List<Runoff> runoff) {
		Map<String, ArrayList<Double>> q = new TreeMap<String, ArrayList<Double>>();
		for (int i = 0; i < runoff.size(); i++) {
			String year = runoff.get(i).getYear();
			String data = runoff.get(i).getRunoff();
			if (!q.containsKey(year)) {
				ArrayList<Double> value = new ArrayList<Double>();
				value.add(Double.parseDouble(data));
				q.put(year, value);
			} else {
				q.get(year).add(Double.parseDouble(data));
			}
		}
		return q;
	}

	public double[] calPowerAndOutput(Hydrostation hydrostation, CalculateBean bean, List<Runoff> runoff) {
		Map<String, ArrayList<Double>> map = getRunoff(runoff);
		double allPower = 0;
		List<Double> allOutput = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			String year = entry.getKey();
			ArrayList<Double> q = entry.getValue();
			int size = q.size();
			double power = 0;
			double[] output = new double[size];
			for (int j = 0; j < size; j++) {
				bean.setInflow(q.get(j));
				boolean result = hydrostation.calOutputAndPower(bean);
				if (result) {
					power += bean.getPower();
					allPower += bean.getPower();
					output[j] = bean.getOutput();
					allOutput.add(bean.getOutput());
				} else {
					output[j] = Double.MIN_VALUE;
					System.out.println("false");
					break;
				}
			}
//			int flag = (int) (size * 0.05);
//			Arrays.sort(output);
//			System.out.println("第" + year + "年95%保证出力" + ":" + output[flag]);
//			System.out.println("第" + year + "年发电量" + ":" + power / 1e8);
		}
		Collections.sort(allOutput);
//		System.out.println("多年平均年发电量" + ":" + allPower / map.size() / 1e8);
		double output = allOutput.get((int) (allOutput.size() * 0.05)) / 1000;
		double power = allPower / map.size() / 1e8;
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
