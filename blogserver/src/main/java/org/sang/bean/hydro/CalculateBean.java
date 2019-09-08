package org.sang.bean.hydro;

public class CalculateBean {
	
	private double levelBegin;
	private double levelEnd;
	private double inflow;
	private double deltaT;
	
	private double headgross;
	private double headnet;
	private double outflow;
	private double outflowDesert;
	private double output;
	private double power;
	
	
	@Override
	public String toString() {
		return "CalculateBean [levelBegin=" + levelBegin + ", levelEnd=" + levelEnd + ", inflow=" + inflow + ", deltaT="
				+ deltaT + ", headgross=" + headgross + ", headnet=" + headnet + ", outflow=" + outflow
				+ ", outflowDesert=" + outflowDesert + ", output=" + output + ", power=" + power + "]";
	}
	public double getLevelBegin() {
		return levelBegin;
	}
	public void setLevelBegin(double levelBegin) {
		this.levelBegin = levelBegin;
	}
	public double getLevelEnd() {
		return levelEnd;
	}
	public void setLevelEnd(double levelEnd) {
		this.levelEnd = levelEnd;
	}
	public double getInflow() {
		return inflow;
	}
	public void setInflow(double inflow) {
		this.inflow = inflow;
	}
	public double getDeltaT() {
		return deltaT;
	}
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}
	public double getHeadgross() {
		return headgross;
	}
	public void setHeadgross(double headgross) {
		this.headgross = headgross;
	}
	public double getHeadnet() {
		return headnet;
	}
	public void setHeadnet(double headnet) {
		this.headnet = headnet;
	}
	public double getOutflow() {
		return outflow;
	}
	public void setOutflow(double outflow) {
		this.outflow = outflow;
	}
	public double getOutflowDesert() {
		return outflowDesert;
	}
	public void setOutflowDesert(double outflowDesert) {
		this.outflowDesert = outflowDesert;
	}
	public double getOutput() {
		return output;
	}
	public void setOutput(double output) {
		this.output = output;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	
	

}
