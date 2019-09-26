package org.sang.bean.flood;

import java.text.DecimalFormat;

import org.apache.commons.math3.special.Gamma;
import org.springframework.stereotype.Component;

@Component
public class GeneralFlood {
	private double ex;
	private double cs;
	private double cv;
	private int n;
	private int a;
	private int l;
	private double fitError;
	private double[] mesureData;

	public void CalcParams() {
		double sum1 = 0.0, sum2 = 0.0;
		double sum3 = 0.0, sum4 = 0.0, sum5 = 0.0;
		double avg = 0.0;
		int tmpn = mesureData.length - a;

		for (int i = 0; i < a; i++) {
			sum1 = sum1 + mesureData[i];
		}
		for (int i = a; i < mesureData.length; i++) {
			sum2 = sum2 + mesureData[i];
		}
		avg = (sum1 + sum2 * (n - a) / (tmpn - l)) / n;

		for (int i = 0; i < a; i++) {
			sum3 = sum3 + Math.pow(mesureData[i] - avg, 2);
		}
		for (int i = a; i < mesureData.length; i++) {
			sum4 = sum4 + Math.pow(mesureData[i] - avg, 2);
		}
		sum5 = (sum3 + sum4 * (n - a) / (tmpn - l)) / (n - 1);
		DecimalFormat df = new DecimalFormat("#.00");
		ex = Double.parseDouble(df.format(avg));
		cv = Double.parseDouble(df.format(Math.sqrt(sum5) / avg));
		cs = Double.parseDouble(df.format(3 * cv));
	}

	public double[][] getTheoryFrequency(double[] p, double[] fai) {
		DecimalFormat df = new DecimalFormat("#.00");
		double[][] res = new double[2][fai.length];
		for (int i = 0; i < fai.length; i++) {
			res[1][i] = Double.parseDouble(df.format(ex * (1 + cv * fai[i])));
			res[0][i] = p[i];
		}
		return res;
	}

	public double[][] getTheoryFrequency1() {
		double a = 4.0 / Math.pow(cs, 2);
		double b = 2.0 / (ex * cv * cs);
		double a0 = ex * (1 - 2.0 * cv / cs);
		double[] sum = new double[mesureData.length];
		double[][] p = new double[2][mesureData.length];
		double[] q = new double[100000];
		double sum1 = 0.0;
		// 求和，循环从第一个区间叠加到第100000个
		for (int i = 0; i < mesureData.length; i++) {
			if (mesureData[i] > a0) {
				for (int j = 0; j < 100000; j++) {
					double x = j / 100000.0;
					q[j] = Math.pow(b, a) * Math.pow(mesureData[i] + (x / (1 - x)) - a0, a - 1)
							* Math.pow(Math.E, -b * (mesureData[i] + x / (1 - x) - a0)) / Gamma.gamma(a)
							/ Math.pow(1 - x, 2);
					sum1 = sum1 + q[j];
				}
				sum[i] = sum1 / 100000.0;
				sum1 = 0.0;
			} else {
				sum[i] = 1;
			}
		}
		for (int j = 0; j < mesureData.length; j++) {
			p[1][j] = mesureData[j];
//			p[0][j] = normsinv(sum[j]) + 3.719016485;
			p[0][j] = sum[j];
		}
		return p;
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
		for (int j = 0; j < obs.length; j++) {
//			p1[0][j] = normsinv(p[j]) + 3.719016485;
			p1[0][j] = p[j];
			p1[1][j] = obs[j];
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

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

}
