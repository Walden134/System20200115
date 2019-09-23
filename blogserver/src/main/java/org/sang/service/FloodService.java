package org.sang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.flood.BaseFlood;
import org.sang.bean.flood.BaseP;
import org.sang.bean.flood.FloodRisk;
import org.sang.bean.flood.FutureP;
import org.sang.bean.flood.GeneralFlood;
import org.sang.bean.hydro.DoubleCurve;
import org.sang.mapper.FloodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class FloodService {
	@Autowired
	FloodMapper floodMapper;

	public Map<String, Object> drawLine(GeneralFlood generalFlood) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	public Map<String, Object> paramEst(GeneralFlood generalFlood) {
		double[] obs = generalFlood.getMesureData();
		double N = generalFlood.getN();
		int a = generalFlood.getA();
		int l = generalFlood.getL();
		double[][] expFrequency = getExpFrequency(obs, N, a, l);
		Map<String, Object> map = new HashMap<>();
		map.put("expFrequency", expFrequency);
		System.out.println(Arrays.toString(expFrequency));
		return map;
	}

	public Map<String, Object> getExpFrequency(GeneralFlood generalFlood) {
		double[] obs = generalFlood.getMesureData();
		double N = generalFlood.getN();
		int a = generalFlood.getA();
		int l = generalFlood.getL();
		double[][] expFrequency = getExpFrequency(obs, N, a, l);
		Map<String, Object> map = new HashMap<>();
		map.put("expFrequency", expFrequency);
		System.out.println(Arrays.toString(expFrequency));
		return map;
	}

	public double[][] getExpFrequency(double[] obs, double N, int a, int l) {

		double[] p = new double[obs.length]; // 经验频率
		double[][] p1 = new double[2][obs.length];
		int n = obs.length - a;

		for (int i = 1; i <= a; i++) {
			p[i - 1] = i / (N + 1);
		}

		for (int i = 1; i <= obs.length - a; i++) {
			p[i + a - 1] = p[a - 1] + (1 - p[a - 1]) * i / (n - l + 1);
		}

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < obs.length; j++) {
				p1[i][j] = normsinv(p[j]) + 3.719016485;
			}
		}
		for (int i = 1; i < 2; i++) {
			for (int j = 0; j < obs.length; j++) {
				p1[i][j] = obs[j];
			}
		}
		return p1;
	}

	public double normsinv(double p) {
		double LOW = 0.02425;
		double HIGH = 0.97575;

		double a[] = { -3.969683028665376e+01, 2.209460984245205e+02, -2.759285104469687e+02, 1.383577518672690e+02,
				-3.066479806614716e+01, 2.506628277459239e+00 };

		double b[] = { -5.447609879822406e+01, 1.615858368580409e+02, -1.556989798598866e+02, 6.680131188771972e+01,
				-1.328068155288572e+01 };

		double c[] = { -7.784894002430293e-03, -3.223964580411365e-01, -2.400758277161838e+00, -2.549732539343734e+00,
				4.374664141464968e+00, 2.938163982698783e+00 };

		double d[] = { 7.784695709041462e-03, 3.224671290700398e-01, 2.445134137142996e+00, 3.754408661907416e+00 };

		double q, r;

		if (p < LOW) {
			q = Math.sqrt(-2 * Math.log(p));
			return (((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q + c[5])
					/ ((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1);
		} else if (p > HIGH) {
			q = Math.sqrt(-2 * Math.log(1 - p));
			return -(((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q + c[5])
					/ ((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1);
		} else {
			q = p - 0.5;
			r = q * q;
			return (((((a[0] * r + a[1]) * r + a[2]) * r + a[3]) * r + a[4]) * r + a[5]) * q
					/ (((((b[0] * r + b[1]) * r + b[2]) * r + b[3]) * r + b[4]) * r + 1);
		}
	}

	public Map<String, Object> calcRisk(FloodRisk floodRisk) {
		List<double[]> typicalFloods = floodRisk.getTypicalFloods();
		DoubleCurve levelCapacityCurve = new DoubleCurve(floodRisk.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(floodRisk.getLeveldownOutflowCurve().getCurveData());
		floodRisk.setLevelCapacityCurve(levelCapacityCurve);
		floodRisk.setLeveldownOutflowCurve(leveldownOutflowCurve);
		String baseFloodJson = getBaseFlood();
		List<double[]> basePList = getBasePList();
		List<double[]> futurePList = getFuturePList(floodRisk.getPattern());
		List<double[]> riskRes = floodRisk.getRiskRes(typicalFloods, baseFloodJson, basePList, futurePList);
		Map<String, Object> map = new HashMap<>();
		map.put("riskRes", riskRes);
		return map;
	}

	private String getBaseFlood() {
		List<BaseFlood> baseFloodList = floodMapper.getBaseFlood();
		double[][] baseFlood = new double[baseFloodList.size()][7];
		for (int i = 0; i < baseFloodList.size(); i++) {
			BaseFlood flood = baseFloodList.get(i);
			baseFlood[i][0] = flood.getYear();
			baseFlood[i][1] = flood.getQ();
			baseFlood[i][2] = flood.getW1();
			baseFlood[i][3] = flood.getW3();
			baseFlood[i][4] = flood.getW7();
			baseFlood[i][5] = flood.getW15();
			baseFlood[i][6] = flood.getP();
		}
		return JSON.toJSONString(baseFlood);
	}

	private List<double[]> getFuturePList(String rcpId) {
		List<FutureP> futurePList = floodMapper.getFuturePByRcp(rcpId);
		List<double[]> list = new ArrayList<double[]>();
		double[] cnrmP = new double[futurePList.size()];
		double[] canesmP = new double[futurePList.size()];
		double[] mirocP = new double[futurePList.size()];
		double[] gfdlP = new double[futurePList.size()];
		for (int i = 0; i < futurePList.size(); i++) {
			FutureP futureP = futurePList.get(i);
			cnrmP[i] = futureP.getCnrmP();
			canesmP[i] = futureP.getCanesmP();
			mirocP[i] = futureP.getMirocP();
			gfdlP[i] = futureP.getGfdlP();
		}
		list.add(cnrmP);
		list.add(canesmP);
		list.add(mirocP);
		list.add(gfdlP);
		return list;
	}

	private List<double[]> getBasePList() {
		List<BaseP> basePList = floodMapper.getBaseP();
		List<double[]> list = new ArrayList<double[]>();
		double[] objP = new double[basePList.size()];
		double[] cnrmP = new double[basePList.size()];
		double[] canesmP = new double[basePList.size()];
		double[] mirocP = new double[basePList.size()];
		double[] gfdlP = new double[basePList.size()];
		for (int i = 0; i < basePList.size(); i++) {
			BaseP baseP = basePList.get(i);
			objP[i] = baseP.getObjP();
			cnrmP[i] = baseP.getCnrmP();
			canesmP[i] = baseP.getCanesmP();
			mirocP[i] = baseP.getMirocP();
			gfdlP[i] = baseP.getGfdlP();
		}
		list.add(objP);
		list.add(cnrmP);
		list.add(canesmP);
		list.add(mirocP);
		list.add(gfdlP);
		return list;
	};
}
