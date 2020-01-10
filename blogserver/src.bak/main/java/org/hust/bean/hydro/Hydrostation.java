package org.hust.bean.hydro;

import java.io.Serializable;

public class Hydrostation implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6206845039007232288L;

	@Override
	public String toString() {
		return "Hydrostation [id=" + id + ", name=" + name + ", levelNormal=" + levelNormal + ", levelDead=" + levelDead
				+ ", installPower=" + installPower + ", outflowMax=" + outflowMax + ", outflowMin=" + outflowMin
				+ ", outputCoefficient=" + outputCoefficient + ", outputDesign=" + outputDesign + ", avgDesiginPower="
				+ avgDesiginPower + ", levelCapacityCurve=" + levelCapacityCurve + ", leveldownOutflowCurve="
				+ leveldownOutflowCurve + ", headlossOutflowCurve=" + headlossOutflowCurve + ", ExpectOutputHeadCurve="
				+ expectOutputHeadCurve + "]";
	}

	/*
	 * id
	 */
	private String id;

	/*
	 * 名称
	 */
	private String name;

	/*
	 * 正常蓄水位
	 */
	private double levelNormal;

	/*
	 * 死水位
	 */
	private double levelDead;

	/*
	 * 装机容量
	 */
	private double installPower;

	/*
	 * 最大下泄
	 */
	private double outflowMax;

	/*
	 * 最小下泄
	 */
	private double outflowMin;
	/**
	 * 消落深度
	 */
	private double drawdownDepth;
	/**
	 * 出力系数
	 */
	private double outputCoefficient;
	/**
	 * 保证出力
	 */
	private double outputDesign;
	/**
	 * 多年平均发电量设计值
	 */
	private double avgDesiginPower;

	/**
	 * 水位库容曲线
	 */
	private DoubleCurve levelCapacityCurve;

	/**
	 * 下泄尾水曲线
	 */
	private DoubleCurve leveldownOutflowCurve;

	/**
	 * 下泄水头损失曲线
	 */
	private DoubleCurve headlossOutflowCurve;

	/**
	 * 水头预想出力曲线
	 */
	private DoubleCurve expectOutputHeadCurve;

	public Hydrostation() {
	}

	public boolean calOutputAndPower(CalculateBean bean) {

		double levelBegin = bean.getLevelBegin();
//		double levelBegin = levelNormal - drawdownDepth;
		double levelEnd = bean.getLevelEnd();
		double inflow = bean.getInflow();
		double deltaT = bean.getDeltaT();// deltaT的时间为天数
		// 计算下泄量
		double v0 = levelCapacityCurve.getV1ByV0(levelBegin);
		double vt = levelCapacityCurve.getV1ByV0(levelEnd);
		double deltaQ = (vt - v0) * 1e4 / (deltaT * 24 * 3600);
		double outflow = inflow - deltaQ-0.5;

		/*
		 * 判断下泄流量约束
		 */
		if (outflow > outflowMax) {
			outflow = outflowMax;
			System.out.println("不满足最大下泄流量约束");
//            return false;
		}
		if (outflow < outflowMin) {
			outflow = outflowMin;
			System.out.println("不满足最小下泄流量约束");
//			return false;
		}
		// 3.计算下游水位（尾水位）
		double leveldown = leveldownOutflowCurve.getV0ByV1(outflow);
		// 4.计算毛水头
//		double headgross = (levelBegin + levelEnd) / 2 - leveldown;
		double headgross = levelNormal - drawdownDepth - leveldown;
		// 5.计算水头损失
		double headloss = headlossOutflowCurve.getV1ByV0(outflow); // 补充查询曲线的内容
		// 6.计算净水头
		double headnet = headgross - headloss;
		// 7.计算出力
		double output = outputCoefficient * headnet * outflow;
//		// 8.判断预想出力
//		double expectoutput = expectOutputHeadCurve.getV1ByV0(headnet);
//		double outflowDesert = 0;
//		if (output > expectoutput) {
//			output = expectoutput;
//			outflowDesert = outflow - output * 1e4 / (outputCoefficient * headnet);
//		}
		if (output < 0) {
			System.out.println(output);
		}
		if (output > installPower * 1000) {
			output = installPower * 1000;
		}
		/*
		 * 计算发电量 kw*h
		 */
		double power = output * deltaT * 24;
		// 9.输出结果
		bean.setLevelEnd(levelEnd);
		bean.setHeadgross(headgross);
		bean.setHeadnet(headnet);
		bean.setOutflow(outflow);
//		bean.setOutflowDesert(outflowDesert);
		bean.setPower(power);
		bean.setOutput(output);
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLevelNormal() {
		return levelNormal;
	}

	public void setLevelNormal(double levelNormal) {
		this.levelNormal = levelNormal;
	}

	public double getLevelDead() {
		return levelDead;
	}

	public void setLevelDead(double levelDead) {
		this.levelDead = levelDead;
	}

	public double getInstallPower() {
		return installPower;
	}

	public void setInstallPower(double installPower) {
		this.installPower = installPower;
	}

	public double getOutflowMax() {
		return outflowMax;
	}

	public void setOutflowMax(double outflowMax) {
		this.outflowMax = outflowMax;
	}

	public double getOutflowMin() {
		return outflowMin;
	}

	public void setOutflowMin(double outflowMin) {
		this.outflowMin = outflowMin;
	}

	public double getOutputCoefficient() {
		return outputCoefficient;
	}

	public void setOutputCoefficient(double outputCoefficient) {
		this.outputCoefficient = outputCoefficient;
	}

	public DoubleCurve getLevelCapacityCurve() {
		return levelCapacityCurve;
	}

	public void setLevelCapacityCurve(DoubleCurve levelCapacityCurve) {
		this.levelCapacityCurve = levelCapacityCurve;
	}

	public DoubleCurve getLeveldownOutflowCurve() {
		return leveldownOutflowCurve;
	}

	public void setLeveldownOutflowCurve(DoubleCurve leveldownOutflowCurve) {
		this.leveldownOutflowCurve = leveldownOutflowCurve;
	}

	public DoubleCurve getHeadlossOutflowCurve() {
		return headlossOutflowCurve;
	}

	public void setHeadlossOutflowCurve(DoubleCurve headlossOutflowCurve) {
		this.headlossOutflowCurve = headlossOutflowCurve;
	}

	public DoubleCurve getExpectOutputHeadCurve() {
		return expectOutputHeadCurve;
	}

	public void setExpectOutputHeadCurve(DoubleCurve expectOutputHeadCurve) {
		this.expectOutputHeadCurve = expectOutputHeadCurve;
	}

	public double getOutputDesign() {
		return outputDesign;
	}

	public void setOutputDesign(double outputDesign) {
		this.outputDesign = outputDesign;
	}

	public double getAvgDesiginPower() {
		return avgDesiginPower;
	}

	public void setAvgDesiginPower(double avgDesiginPower) {
		this.avgDesiginPower = avgDesiginPower;
	}

	public double getDrawdownDepth() {
		return drawdownDepth;
	}

	public void setDrawdownDepth(double drawdownDepth) {
		this.drawdownDepth = drawdownDepth;
	}

}
