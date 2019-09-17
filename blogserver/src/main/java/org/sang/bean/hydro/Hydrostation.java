package org.sang.bean.hydro;

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
				+ ExpectOutputHeadCurve + "]";
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
	private DoubleCurve ExpectOutputHeadCurve;

	public Hydrostation() {
	}

	public boolean calOutputAndPower(CalculateBean bean) {

		double levelbegin = bean.getLevelBegin();
		double levelEnd = bean.getLevelEnd();
		double inflow = bean.getInflow();
		double deltaT = bean.getDeltaT();

		double v0 = levelCapacityCurve.getV1ByV0(levelbegin);
		double vt = levelCapacityCurve.getV1ByV0(levelEnd);
		double deltaQ = (vt - v0) * 1e4 / (deltaT * 24 * 3600);
		double outflow = inflow - deltaQ - 0.5;

		/*
		 * 判断下泄流量约束
		 */
		if (outflow > outflowMax) {
			outflow = outflowMax;
//			System.out.println("不满足最大下泄流量约束");
//			return false;
		}
		if (outflow < outflowMin) {
			outflow = outflowMin;
//			System.out.println("不满足最小下泄流量约束");
//			return false;
		}

		/*
		 * 查表得下游尾水位并计算毛水头
		 */
		double headgross = levelNormal - 1 - leveldownOutflowCurve.getV0ByV1(outflow);

		/*
		 * 查表得水头损失计算净水头
		 */
		double headnet = headgross - headlossOutflowCurve.getV1ByV0(outflow);

		/*
		 * 计算出力 KW
		 */
		double output = outputCoefficient * headnet * outflow;
		if (output < 0) {
			System.out.println(output);
		}
//		System.out.println("outputDesign="+outputDesign);
		if (output > installPower * 1000) {
			output = installPower * 1000;
		}
		/*
		 * 计算发电量 kw*h
		 */
		double power = output * deltaT * 24;
		/*
		 * 输出结果
		 */

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
		return ExpectOutputHeadCurve;
	}

	public void setExpectOutputHeadCurve(DoubleCurve expectOutputHeadCurve) {
		ExpectOutputHeadCurve = expectOutputHeadCurve;
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
