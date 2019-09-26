package org.sang.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sang.bean.flood.BaseFlood;
import org.sang.bean.flood.BaseP;
import org.sang.bean.flood.FloodRisk;
import org.sang.bean.flood.FutureP;
import org.sang.bean.flood.GeneralFlood;
import org.sang.bean.flood.P3;
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

	public Map<String, Object> drawLine(GeneralFlood generalFlood) {
		Map<String, Object> map = new HashMap<>();
		double ex = generalFlood.getEx();
		double cv = generalFlood.getCv();
		double cs = generalFlood.getCs();// 2.536
		if (ex == 0 & cv == 0 & cs == 0) {
			System.out.println("请输入正确参数值");
			return null;
		}
		DecimalFormat df2 = new DecimalFormat("#.00");
		DecimalFormat df1 = new DecimalFormat("#.0");
		double cs1 = getDouble(cs, 1);
		List<P3> p31 = floodMapper.getPByCs(cs1);// 2.5
		List<P3> p311 = floodMapper.getPByCs(Double.parseDouble(df1.format(cs1 + 0.1)));// 2.6
		int len = p31.size();
		double[] p = new double[len];
		double[] fai = new double[len];
		for (int i = 0; i < len; i++) {
			p[i] = p31.get(i).getP();
			fai[i] = p31.get(i).getFai() + (p311.get(i).getFai() - p31.get(i).getFai()) * (cs - cs1);
		}
		double[][] theoryFrequency = generalFlood.getTheoryFrequency(p, fai);
		map.put("theoryFrequency", theoryFrequency);
		map.put("fitError", generalFlood.getFitError());
		return map;
	}

	/**
	 * a为一个带有未知位小数的实数 对其取b位小数
	 */
	public double getDouble(double a, int b) {
		int x = 0;
		int y = 1;
		for (int i = 0; i < b; i++) {
			y = y * 10;
		}
		x = (int) (a * y);
		return (double) x / y;
	}

	public Map<String, Object> paramEst(GeneralFlood generalFlood) {
		double[] obs = generalFlood.getMesureData();
		double N = generalFlood.getN();
		int a = generalFlood.getA();
		int l = generalFlood.getL();
		if (obs.length < 1 & N == 0 & a == 0 & l == 0) {
			return null;
		}
		generalFlood.CalcParams();
		Map<String, Object> map = new HashMap<>();
		DecimalFormat df = new DecimalFormat("#.00");
		map.put("ex", Double.parseDouble(df.format(generalFlood.getEx())));
		map.put("cv", Double.parseDouble(df.format(generalFlood.getCv())));
		map.put("cs", Double.parseDouble(df.format(generalFlood.getCs())));
		map.put("fitError", generalFlood.getFitError());
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

	// 降水不確定性分析
	public Map<String, Object> readBaseP(UncertainP time) {
		List<double[]> bmaP = time.getObjP();
		List<double[]> futurePList = getFuturePList(time.getPattern());
		double[][] q = time.getBMAP(bmaP, futurePList);
		double[] bma = time.getBMA(bmaP, futurePList);
		Map<String, Object> map = new HashMap<>();
		double[][] data = new double[7][q.length];
		for (int i = 0; i < q.length; i++) {// 80
			data[0][i] = q[i][4];
			data[1][i] = q[i][94] - q[i][4];
			double[] cnrm = futurePList.get(0);
			double[] canesm = futurePList.get(1);
			double[] miroc = futurePList.get(2);
			double[] gfdl = futurePList.get(3);
			data[2][i] = cnrm[i];
			data[3][i] = canesm[i];
			data[4][i] = miroc[i];
			data[5][i] = gfdl[i];
			data[6][i] = bma[i];
		}
		map.put("q", data);
		return map;
	};

	// 时变设计洪水
	public Map<String, Object> readBaseFlood(UncertainP time) {
		double[][] BaseFlood = time.getBaseFlood();
		List<double[]> bmaP = time.getObjP();
		List<double[]> futurePList = getFuturePList(time.getPattern());
		double[][] q = time.getBMAP(bmaP, futurePList);
		double per5 = 0;
		double per50 = 0;
		double per95 = 0;
		for (int i = 0; i < q.length; i++) {
			per5 += q[i][4];
			per50 += q[i][49];
			per95 += q[i][94];
		}
		double[] bma = { 557.57, 707.87, 873.35 };
		Map<String, Object> tmpmap = time.getDesignP(BaseFlood, bma, time.getFlag());
		double[][] designp = (double[][]) tmpmap.get("piii");
		double[] ex = (double[]) tmpmap.get("ex");
		double[] cv = (double[]) tmpmap.get("cv");
		double[] cs = (double[]) tmpmap.get("cs");
		double[][] data = new double[4][designp.length];
		double[] x = { 0, 0.428489753963786, 0.628784179287867, 0.840854746360197, 1.14318718190678, 1.39266861141484,
				2.07416285850421, 2.43746491991108, 2.68258309596189, 2.87739525188277, 3.19461597274764,
				3.46566938231988, 3.71901648545568, 3.97236358859148, 4.24341699816372, 4.56063771902859,
				4.75544987494947, 5.00056805100028, 5.36387011240715, 6.04536435949652, 6.80924879162353,
				7.00954321694761, 7.43803297091111 };
		DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df2 = new DecimalFormat("#.000");
		for (int j = 0; j < designp.length; j++) {// 23
			data[0][j] = x[j];
			data[1][j] = Double.parseDouble(df.format(designp[j][0]));
			data[2][j] = Double.parseDouble(df.format(designp[j][1]));
			data[3][j] = Double.parseDouble(df.format(designp[j][2]));
		}
		for (int i = 0; i < cv.length; i++) {
			ex[i] = Double.parseDouble(df.format(ex[i]));
			cv[i] = Double.parseDouble(df.format(cv[i]));
			cs[i] = Double.parseDouble(df2.format(cs[i]));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("designp", data);
		map.put("ex", ex);
		map.put("cv", cv);
		map.put("cs", cs);
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
