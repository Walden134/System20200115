package org.sang.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.DoubleCurve;
import org.sang.bean.hydro.Hydrostation;
import org.sang.bean.hydro.Runoff;
import org.sang.utils.ExcelTool;

public class PowerCalcData {

	public static void main(String[] args) throws IOException, ParseException {

		Hydrostation hydrostation = new Hydrostation();
		hydrostation.setId("001");
		hydrostation.setName("杨房沟");
		hydrostation.setLevelNormal(2094);
		hydrostation.setLevelDead(2088);
		hydrostation.setOutflowMax(15200);
		hydrostation.setOutflowMin(0);
		hydrostation.setOutputCoefficient(8.5);
		CalculateBean bean = new CalculateBean();
		bean.setLevelBegin(2094);
		bean.setLevelEnd(2094);
		bean.setDeltaT(1);

		DoubleCurve levelCapacityCurve = new DoubleCurve(read("水位库容曲线.xlsx", "Sheet1"));
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(read("下泄尾水曲线.xlsx", "Sheet2"));
		DoubleCurve headlossOutflowCurve = new DoubleCurve(read("水头损失曲线.xlsx", "Sheet1"));
		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
		System.out.println("数据读取......");

//		Map<String, ArrayList<Double>> map = read2("2.xlsx", "data", 1);
//		calc(map, hydrostation, bean);
		System.out.println("map_0_OBJ");
		Map<String, ArrayList<Double>> map_0_OBJ = read2("GCMDATA.xlsx", "基准期", 6);
		calc(map_0_OBJ, hydrostation, bean);
		Map<String, ArrayList<Double>> map_0_CNRM = read2("GCMDATA.xlsx", "基准期", 1);
		Map<String, ArrayList<Double>> map_26_CNRM = read2("GCMDATA.xlsx", "2.6", 1);
		Map<String, ArrayList<Double>> map_85_CNRM = read2("GCMDATA.xlsx", "8.5", 1);
		Map<String, ArrayList<Double>> map_45_CNRM = read2("GCMDATA.xlsx", "4.5", 1);
		System.out.println("map_0_CNRM");
		calc(map_0_CNRM, hydrostation, bean);
		System.out.println("map_26_CNRM");
		calc(map_26_CNRM, hydrostation, bean);
		System.out.println("map_45_CNRM");
		calc(map_45_CNRM, hydrostation, bean);
		System.out.println("map_85_CNRM");
		calc(map_85_CNRM, hydrostation, bean);

		Map<String, ArrayList<Double>> map_0_GFDL = read2("GCMDATA.xlsx", "基准期", 2);
		Map<String, ArrayList<Double>> map_26_GFDL = read2("GCMDATA.xlsx", "2.6", 2);
		Map<String, ArrayList<Double>> map_85_GFDL = read2("GCMDATA.xlsx", "8.5", 2);
		Map<String, ArrayList<Double>> map_45_GFDL = read2("GCMDATA.xlsx", "4.5", 2);
		System.out.println("map_0_GFDL");
		calc(map_0_GFDL, hydrostation, bean);
		System.out.println("map_26_GFDL");
		calc(map_26_GFDL, hydrostation, bean);
		System.out.println("map_45_GFDL");
		calc(map_45_GFDL, hydrostation, bean);
		System.out.println("map_85_GFDL");
		calc(map_85_GFDL, hydrostation, bean);

		Map<String, ArrayList<Double>> map_0_CanESM = read2("GCMDATA.xlsx", "基准期", 3);
		Map<String, ArrayList<Double>> map_26_CanESM = read2("GCMDATA.xlsx", "2.6", 3);
		Map<String, ArrayList<Double>> map_85_CanESM = read2("GCMDATA.xlsx", "8.5", 3);
		Map<String, ArrayList<Double>> map_45_CanESM = read2("GCMDATA.xlsx", "4.5", 3);
		System.out.println("map_0_CanESM");
		calc(map_0_CanESM, hydrostation, bean);
		System.out.println("map_26_CanESM");
		calc(map_26_CanESM, hydrostation, bean);
		System.out.println("map_45_CanESM");
		calc(map_45_CanESM, hydrostation, bean);
		System.out.println("map_85_CanESM");
		calc(map_85_CanESM, hydrostation, bean);

		Map<String, ArrayList<Double>> map_0_MIROC = read2("GCMDATA.xlsx", "基准期", 4);
		Map<String, ArrayList<Double>> map_26_MIROC = read2("GCMDATA.xlsx", "2.6", 4);
		Map<String, ArrayList<Double>> map_85_MIROC = read2("GCMDATA.xlsx", "8.5", 4);
		Map<String, ArrayList<Double>> map_45_MIROC = read2("GCMDATA.xlsx", "4.5", 4);
		System.out.println("map_0_MIROC");
		calc(map_0_MIROC, hydrostation, bean);
		System.out.println("map_26_MIROC");
		calc(map_26_MIROC, hydrostation, bean);
		System.out.println("map_45_MIROC");
		calc(map_45_MIROC, hydrostation, bean);
		System.out.println("map_85_MIROC");
		calc(map_85_MIROC, hydrostation, bean);

		Map<String, ArrayList<Double>> map_0_BMA = read2("GCMDATA.xlsx", "基准期", 5);
		Map<String, ArrayList<Double>> map_26_BMA = read2("GCMDATA.xlsx", "2.6", 5);
		Map<String, ArrayList<Double>> map_85_BMA = read2("GCMDATA.xlsx", "8.5", 5);
		Map<String, ArrayList<Double>> map_45_BMA = read2("GCMDATA.xlsx", "4.5", 5);
		System.out.println("map_0_BMA");
		calc(map_0_BMA, hydrostation, bean);
		System.out.println("map_26_BMA");
		calc(map_26_BMA, hydrostation, bean);
		System.out.println("map_45_BMA");
		calc(map_45_BMA, hydrostation, bean);
		System.out.println("map_85_BMA");
		calc(map_85_BMA, hydrostation, bean);

	}

	public static List<List<Double>> getYAxis(String str) {
		String[] split = str.split(str);

		return null;
	}

	public static void calc(Map<String, ArrayList<Double>> dataMap, Hydrostation hydrostation, CalculateBean bean) {
		Map<String, ArrayList<Double>> q = getQ(dataMap, "q");
		Map<String, ArrayList<Double>> qf = getQ(dataMap, "qf");
		Map<String, ArrayList<Double>> qp = getQ(dataMap, "qp");
		Map<String, ArrayList<Double>> qk = getQ(dataMap, "qk");
		System.out.println("==========qcalpower==========");
		calpower(hydrostation, bean, q);

		System.out.println("==========qfcalpower==========");
		calpower(hydrostation, bean, qf);

		System.out.println("==========qpcalpower==========");
		calpower(hydrostation, bean, qp);

		System.out.println("==========qkcalpower==========");
		calpower(hydrostation, bean, qk);

		System.out.println("==========qcaloutput==========");
		caloutput(hydrostation, bean, q);
	}

	public static List<Map<String, double[]>> calcPowerAndOutput(Hydrostation hydrostation, CalculateBean bean) {
		Map<String, double[]> powerMap = new HashMap<String, double[]>();
		Map<String, double[]> outputMap = new HashMap<String, double[]>();
		List<Map<String, double[]>> res = new ArrayList<Map<String, double[]>>();
		String[] situations = bean.getSituations();
		String[] patterns = bean.getPatterns();
		DoubleCurve levelCapacityCurve = new DoubleCurve(hydrostation.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(hydrostation.getLeveldownOutflowCurve().getCurveData());
		DoubleCurve headlossOutflowCurve = new DoubleCurve(hydrostation.getHeadlossOutflowCurve().getCurveData());
		hydrostation.setLevelCapacityCurve(levelCapacityCurve);
		hydrostation.setHeadlossOutflowCurve(headlossOutflowCurve);
		hydrostation.setLeveldownOutflowCurve(leveldownOutflowCurve);
		Map<String, ArrayList<Double>> dataMap = null;
		for (int i = 0; i < situations.length; i++) {
			for (int j = 0; j < patterns.length; j++) {
				String key = patterns[j] + "_" + situations[i];
				String pattern = null;
				int situ = 0;
				switch (patterns[j]) {
				case "RCP2.6":
					pattern = "2.6";
					break;
				case "RCP4.5":
					pattern = "4.5";
					break;
				case "RCP8.5":
					pattern = "8.5";
					break;
				default:
					pattern = "基准期";
					break;
				}
				switch (situations[i]) {
				case "GFDL":
					situ = 2;
					break;
				case "CNRM":
					situ = 1;
					break;
				case "CanESM":
					situ = 3;
					break;
				case "MIROC":
					situ = 4;
					break;
				case "BMA":
					situ = 5;
					break;
				}
				dataMap = read2("D:\\Documents\\GitHub\\huadongyuan\\blogserver\\src\\main\\resources\\GCMDATA.xlsx",
						pattern, situ);
				Map<String, ArrayList<Double>> q = getQ(dataMap, "q");
				Map<String, ArrayList<Double>> qf = getQ(dataMap, "qf");
				Map<String, ArrayList<Double>> qp = getQ(dataMap, "qp");
				Map<String, ArrayList<Double>> qk = getQ(dataMap, "qk");

				double[] power = new double[4];
				power[0] = calpower(hydrostation, bean, q);
				power[1] = calpower(hydrostation, bean, qf);
				power[2] = calpower(hydrostation, bean, qp);
				power[3] = calpower(hydrostation, bean, qk);
				double[] output = new double[4];
				output[0] = caloutput(hydrostation, bean, q);
				output[1] = caloutput(hydrostation, bean, qf);
				output[2] = caloutput(hydrostation, bean, qp);
				output[3] = caloutput(hydrostation, bean, qk);
				powerMap.put(key, power);
				outputMap.put(key, output);
			}
		}
		res.add(powerMap);
		res.add(outputMap);
		return res;
	}

	public static Map<String, ArrayList<Double>> getQ(Map<String, ArrayList<Double>> map, String flag) {
		Map<String, ArrayList<Double>> data_map = new TreeMap<String, ArrayList<Double>>();
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			String key = entry.getKey();
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

	private static void divData(Map<String, ArrayList<Double>> data_map, ArrayList<Double> value, String year) {
		if (!data_map.containsKey(year)) {
			ArrayList<Double> templist = new ArrayList<Double>();
			data_map.put(year, templist);
		}
		data_map.get(year).addAll(value);
	}

	public static double[][] read(String Path, String sheet) throws IOException {
		Object data[][] = ExcelTool.read07Excel(Path, sheet);
		double[][] number = new double[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				number[i][j] = (Double) data[i][j];
			}
		}
		return number;
	}

	public static double calpower(Hydrostation hydrostation, CalculateBean bean, Map<String, ArrayList<Double>> map) {
		boolean result = false;
		double allPower = 0;
		String year = "";
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			double power = 0;
			year = entry.getKey();
			ArrayList<Double> q = entry.getValue();
			for (int j = 0; j < q.size(); j++) {
				bean.setInflow(q.get(j));
				result = hydrostation.calOutputAndPower(bean);
				if (result) {
					power += bean.getPower();
					allPower += bean.getPower();
				} else {
					System.out.println("false");
					break;
				}
			}
//			System.out.println("第" + year + "年发电量" + ":" + power / 1e8);
		}
//		System.out.println("多年平均年发电量" + ":" + allPower / map.size() / 1e8);
		return allPower / map.size() / 1e8;
	}

	public static double caloutput(Hydrostation hydrostation, CalculateBean bean, Map<String, ArrayList<Double>> map) {
		boolean result;
		int flag = 0;
		String year;
		List<Double> list = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Double>> entry : map.entrySet()) {
			result = false;
			year = entry.getKey();
			ArrayList<Double> q = entry.getValue();
			int size = q.size();
			double[] output = new double[size];
			for (int j = 0; j < size; j++) {
				bean.setInflow(q.get(j));
				result = hydrostation.calOutputAndPower(bean);
				if (result) {
					output[j] = bean.getOutput();
					list.add(bean.getOutput());
				} else {
					output[j] = Double.MIN_VALUE;
					System.out.println("false");
					break;
				}
//				System.out.println(output[j] / 1000);
			}
			flag = (int) (size * 0.05);

			Arrays.sort(output);
//			System.out.println("第" + year + "年95%保证出力" + ":" + output[flag]);
		}
		Collections.sort(list);
//		System.out.println("95%保证出力" + ":" + array[(int) (list.size() * 0.05)]/1000);
		return list.get((int) (list.size() * 0.05)) / 1000;
	}

	public static Map<String, ArrayList<Double>> read2(String Path, String sheet, int m) {
		Object data[][];
		Map<String, ArrayList<Double>> map = new TreeMap<String, ArrayList<Double>>();
		try {
			data = ExcelTool.read07Excel(Path, sheet);
			for (int i = 1; i < data.length; i++) {
				Date dt = (Date) data[i][0];
				String year = String.format("%tY", dt);
				String month = String.format("%tm", dt);
				String day = String.format("%td", dt);
				String key = year + month;
				if (!map.containsKey(key)) {
					ArrayList<Double> list = new ArrayList<Double>();
					map.put(key, list);
				}
				map.get(key).add((Double) data[i][m]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

}
