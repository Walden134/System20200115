package org.sang.bean.flood;

public class GeneralFlood {
	private double ex;
	private double cs;
	private double cv;
	private double fitError;
	private double[] mesureData;

	public double getEx() {
		return ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}

	public double getCs() {
		return cs;
	}

	public void setCs(double cs) {
		this.cs = cs;
	}

	public double getCv() {
		return cv;
	}

	public void setCv(double cv) {
		this.cv = cv;
	}

	public double getFitError() {
		return fitError;
	}

	public void setFitError(double fitError) {
		this.fitError = fitError;
	}

	public double[] getMesureData() {
		return mesureData;
	}

	public void setMesureData(double[] mesureData) {
		this.mesureData = mesureData;
	}

}
