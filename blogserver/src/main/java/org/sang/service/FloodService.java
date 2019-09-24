package org.sang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.flood.BaseFlood;
import org.sang.bean.flood.BaseP;
import org.sang.bean.flood.FloodRisk;
import org.sang.bean.flood.FutureP;
import org.sang.bean.flood.GeneralFlood;
import org.sang.bean.flood.UncertainP;
import org.sang.bean.hydro.DoubleCurve;
import org.sang.mapper.FloodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class FloodService {
	@Autowired
	FloodMapper floodMapper;
	@Autowired
	GeneralFlood genFlood;

	public Map<String, Object> drawLine(GeneralFlood generalFlood) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	public Map<String, Object> paramEst(GeneralFlood generalFlood) {
		double[] obs = generalFlood.getMesureData();
		double N = generalFlood.getN();
		int a = generalFlood.getA();
		int l = generalFlood.getL();
		if (obs == null || N == 0 || a == 0 || l == 0) {
			generalFlood.CalcParams();
		} else {
			generalFlood.CalcParams();
		}
//		double[][] theoryFrequency = generalFlood.getTheoryFrequency();
		Map<String, Object> map = new HashMap<>();
//		map.put("theoryFrequency", theoryFrequency);
		map.put("ex", generalFlood.getEx());
		map.put("cv", generalFlood.getCv());
		map.put("cs", generalFlood.getCs());
//		map.put("fitError", generalFlood.getFitError());
		return map;
	}

	public Map<String, Object> getExpFrequency(GeneralFlood generalFlood) {
		double[] obs = generalFlood.getMesureData();
		double N = generalFlood.getN();
		int a = generalFlood.getA();
		int l = generalFlood.getL();
		double[][] expFrequency = generalFlood.getExpFrequency(obs, N, a, l);
		Map<String, Object> map = new HashMap<>();
		map.put("expFrequency", expFrequency);
		return map;
	}

	public Map<String, Object> readBaseP(UncertainP time) {
		List<double[]> bmaP = time.getObjP();
		List<double[]> futurePList = getFuturePList(time.getPattern());
		double[][] q = time.getBMAP(bmaP, futurePList);
		Map<String, Object> map = new HashMap<>();
		double[][] data = new double[2][q.length];
		for (int i = 0; i < q.length; i++) {// 80
			data[0][i] = q[i][4];
			data[1][i] = q[i][94] - q[i][4];
		}
		map.put("q", data);
		return map;
	};

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
