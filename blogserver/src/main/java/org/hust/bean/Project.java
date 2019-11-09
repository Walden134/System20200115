package org.hust.bean;

public class Project {
//	ipro：方案的唯一索引号
//	ireg：区间信息的索引后，用于后期增加二维结果输出功能
//	author：作者
//	runoff：降雨产流模型名称
//	route：坡面汇流模型名称
//	river：河道汇流模型名称，可以为空
//	grade：方案率定时的评级
//	exit：出口站名
//	brans：支流站名列表，可以为空
//	nsce：方案率定时的确定性系数
//	bias：方案率定时的总量误差，单位%
//	errp：方案率定时的洪峰流量误差，单位%
//	errt：方案率定时的洪峰时刻误差，单位h
//	drna：计算区间的面积，单位 km²

	private int ipro;
	private int ireg;
	private String author;
	private String runoff;
	private String route;
	private String river;
	private String grade;
	private String exit;
	private String[] brans;
	private String nsce;
	private double bias;
	private double errp;
	private double errt;
	private double drna;

	public int getIpro() {
		return ipro;
	}

	public void setIpro(int ipro) {
		this.ipro = ipro;
	}

	public int getIreg() {
		return ireg;
	}

	public void setIreg(int ireg) {
		this.ireg = ireg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRunoff() {
		return runoff;
	}

	public void setRunoff(String runoff) {
		this.runoff = runoff;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRiver() {
		return river;
	}

	public void setRiver(String river) {
		this.river = river;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String[] getBrans() {
		return brans;
	}

	public void setBrans(String[] brans) {
		this.brans = brans;
	}

	public String getNsce() {
		return nsce;
	}

	public void setNsce(String nsce) {
		this.nsce = nsce;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getErrp() {
		return errp;
	}

	public void setErrp(double errp) {
		this.errp = errp;
	}

	public double getErrt() {
		return errt;
	}

	public void setErrt(double errt) {
		this.errt = errt;
	}

	public double getDrna() {
		return drna;
	}

	public void setDrna(double drna) {
		this.drna = drna;
	}

}
