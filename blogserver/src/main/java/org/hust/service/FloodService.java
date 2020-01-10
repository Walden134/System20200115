package org.hust.service;

import com.alibaba.fastjson.JSON;
import org.hust.bean.flood.CalculateFloodRisk;
import org.hust.bean.flood.*;
import org.hust.bean.hydro.DoubleCurve;
import org.hust.mapper.FloodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class FloodService {
	@Autowired
	FloodMapper floodMapper;
	DecimalFormat df1 = new DecimalFormat("#.0");

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

		double cs1 = getDouble(cs, 1);
		List<P3> p31 = floodMapper.getPByCs(cs1);// 2.5
		List<P3> p311 = floodMapper.getPByCs(Double.parseDouble(df1.format(cs1 + 0.1)));// 2.6
		int len = p31.size();
		double[] p = new double[len];
		double[] fai = new double[len];
		for (int i = 0; i < len; i++) {
			p[i] = p31.get(i).getP();
			fai[i] = p31.get(i).getFai() + (p311.get(i).getFai() - p31.get(i).getFai()) * (cs - cs1) * 10;
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

	// 降水不确定性分析
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
	}

	// 时变设计洪水
	public Map<String, Object> readBaseFlood(UncertainP time, double exx, double cvv, double css) {

		double[] p = { 0.01, 0.1, 0.2, 0.5, 1, 2, 3, 5, 10, 20, 25, 30, 40, 50, 60, 70, 75, 80, 85, 90, 95, 99, 99.9 };
		double cs1 = getDouble(css, 1);
		List<Double> fai = floodMapper.getPaiByCsAndP(cs1, p);
		List<Double> fai1 = floodMapper.getPaiByCsAndP(Double.parseDouble(df1.format(cs1 + 0.1)), p);// 2.6
		for (int i = 0; i < fai.size(); i++) {
			double val = fai.get(i) + (fai1.get(i) - fai.get(i)) * (css - cs1) * 10;
			fai.set(i, val);
		}

		double[][] BaseFlood = time.getBaseFlood();
		List<double[]> bmaP = time.getObjP();
		List<double[]> futurePList = getFuturePList(time.getPattern());
		double[] sigmabma = time.getBMA(bmaP, futurePList);
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;
		for (int i = 0; i < 30; i++) {
			sum1 = sum1 + sigmabma[i];
		}
		for (int j = 30; j < 55; j++) {
			sum2 = sum2 + sigmabma[j];
		}
		for (int k = 55; k < 80; k++) {
			sum3 = sum3 + sigmabma[k];
		}
//        double[][] q = time.getBMAP(bmaP, futurePList);
//        double per5 = 0;
//        double per50 = 0;
//        double per95 = 0;
//        for (int i = 0; i < q.length; i++) {
//            per5 += q[i][4];
//            per50 += q[i][49];
//            per95 += q[i][94];
//        }
		double[] bma = { sum1 / 30, sum2 / 25, sum3 / 25, 658.69 };
		Map<String, Object> tmpmap = time.getDesignP(BaseFlood, bma, time.getFlag(), exx, cvv, css, fai);
		double[][] designp = (double[][]) tmpmap.get("piii");
		double[] lilunDesign = (double[]) tmpmap.get("lilunDesign");
//        double[] ex = (double[]) tmpmap.get("ex");
//        double[] cv = (double[]) tmpmap.get("cv");
//        double[] cs = (double[]) tmpmap.get("cs");
		double[][] data = new double[5][designp.length];
		double[] x = { 0, 0.428489754, 0.628784179, 0.840854746, 1.143187182, 1.392668611, 2.074162859, 2.43746492,
				2.682583096, 2.877395252, 3.194615973, 3.465669382, 3.719016485, 3.972363589, 4.243416998, 4.560637719,
				4.755449875, 5.000568051, 5.363870112, 6.045364359, 6.809248792, 7.009543217, 7.438032971 };
		DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df2 = new DecimalFormat("#.000");
		for (int j = 0; j < designp.length; j++) {// 23
			data[0][j] = x[j];
			data[1][j] = Double.parseDouble(df.format(designp[j][0]));// 30s
			data[2][j] = Double.parseDouble(df.format(designp[j][1]));// 60s
			data[3][j] = Double.parseDouble(df.format(designp[j][2]));// 90s
			data[4][j] = Double.parseDouble(df.format(designp[j][3]));//
		}
//        for (int i = 0; i < cv.length; i++) {
//            ex[i] = Double.parseDouble(df.format(ex[i]));
//            cv[i] = Double.parseDouble(df.format(cv[i]));
//            cs[i] = Double.parseDouble(df2.format(cs[i]));
//        }
		Map<String, Object> map = new HashMap<>();
		map.put("designp", data);
//        map.put("ex", ex);
//        map.put("cv", cv);
//        map.put("cs", cs);
		return map;
	}

	// 风防洪险计算
	public Map<String, Object> calcRisk(FloodRisk floodRisk) {
		List<double[]> typicalFloods = floodRisk.getTypicalFloods();
		DoubleCurve levelCapacityCurve = new DoubleCurve(floodRisk.getLevelCapacityCurve().getCurveData());
		DoubleCurve leveldownOutflowCurve = new DoubleCurve(floodRisk.getLeveldownOutflowCurve().getCurveData());
		floodRisk.setLevelCapacityCurve(levelCapacityCurve);
		floodRisk.setLeveldownOutflowCurve(leveldownOutflowCurve);
		String baseFloodJson = getBaseFlood();
		List<double[]> basePList = getBasePList();
		Map<String, Object> map = new HashMap<>();

		List<double[]> futurePList = getFuturePList2("26");
		List<double[]> riskRes = floodRisk.getRiskRes(typicalFloods, baseFloodJson, basePList, futurePList);
		map.put("rcp26", riskRes);

		futurePList = getFuturePList2("45");
		riskRes = floodRisk.getRiskRes(typicalFloods, baseFloodJson, basePList, futurePList);
		map.put("rcp45", riskRes);

		futurePList = getFuturePList2("85");
		riskRes = floodRisk.getRiskRes(typicalFloods, baseFloodJson, basePList, futurePList);
		map.put("rcp85", riskRes);

		List<double[]> futurePList2 = getFuturePList("26");
		List<double[]> riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.998");
		map.put("rcp261", riskRes2);
		riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.9998");
		map.put("rcp262", riskRes2);

		futurePList2 = getFuturePList("45");
		riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.998");
		map.put("rcp451", riskRes2);
		riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.9998");
		map.put("rcp452", riskRes2);

		futurePList2 = getFuturePList("85");
		riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.998");
		map.put("rcp851", riskRes2);
		riskRes2 = floodRisk.getRiskRes2(typicalFloods, baseFloodJson, basePList, futurePList2, "0.9998");
		map.put("rcp852", riskRes2);
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

	private List<double[]> getFuturePList2(String rcpId) {
		List<FutureP> futurePList = floodMapper.getFuturePByRcp2(rcpId);
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
	}

	public Map<String, Object> getFutureFloodRisk(String rcp, double ex, double cv, double cs) {
		double[] p = { 0.2, 0.5, 1, 2, 5, 10, 20, 50 };
		String[] gcmIds = new String[] { "obs", "cnrm", "miroc", "canesm", "gfdl" };
		System.out.println(Arrays.asList(gcmIds));
		List<double[]> obj = new ArrayList<>();
		for (int i = 0; i < gcmIds.length; i++) {
			double[] tmp = floodMapper.getRunoffByRcpAndGcm(rcp, gcmIds[i]);
			obj.add(tmp);
		}
		double cs1 = getDouble(cs, 1);
		List<Double> fai = floodMapper.getPaiByCsAndP(cs1, p);
		List<Double> fai1 = floodMapper.getPaiByCsAndP(Double.parseDouble(df1.format(cs1 + 0.1)), p);// 2.6
		for (int i = 0; i < fai.size(); i++) {
			double val = fai.get(i) + (fai1.get(i) - fai.get(i)) * (cs - cs1) * 10;
			fai.set(i, val);
		}
		Map<String, Object> res = CalculateFloodRisk.getFutureFloodRisk(obj, ex, cv, fai);
		return res;
	}
}
