package org.sang.bean.flood;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;
import com.alibaba.fastjson.JSON;

public class UncertainP {

	private List<double[]> ObjP;
	private String pattern;

	/**
	 * @param ObjP基准期实测数据和4种GCM下预测的降雨
	 * @param SimP未来情景下4种GCM的降雨数据(RCP2.6,RCP4.5,RCP8.5)
	 * @return
	 */
	public String getBMAPJson(List<double[]> ObjP, List<double[]> SimP) {
		double[][] bmap = getBMAP(ObjP, SimP);
		return JSON.toJSONString(bmap);
	}

	public double[][] getBMAP(List<double[]> ObjP, List<double[]> SimP) {
		double[] Yt = ObjP.get(0);// 基准期实测数据
		int NT = Yt.length;// 基准期实测数据的长度
		int K = ObjP.size() - 1;// 基准期模拟数据的长度
		int T = SimP.get(0).length;// 未来一个GCM数据的长度
		double[] Q = new double[NT];
		double[][] f = new double[K][NT];
		double[][] g = new double[K][T];
		for (int i = 0; i < f.length; i++) {
			f[i] = ObjP.get(i + 1);// 基准期模拟数据
			g[i] = SimP.get(i);// 未来数据
		}
		int iterMax = 10000;
		double[] var = new double[K];
		double[] w = new double[K];
		for (int i = 0; i < w.length; i++) {
			w[i] = 1 / K;
			var[i] = 0;
		}
		double sum = 0;
		for (int i = 0; i < K; i++) {
			for (int t = 0; t < NT; t++) {
				sum += Yt[t];
			}
			double average = sum / NT;
			for (int t = 0; t < NT; t++) {
				var[i] += (Yt[t] - f[i][t]) * (Yt[t] - f[i][t]);
			}
			var[i] = var[i] / NT / K;
			sum = 0;
		}

		Q = calcQ(w, f);
		double initval = calcVal(w, var, f, Yt);
		for (int iter = 1; iter < iterMax; iter++) {
			double[][] zkt = calcZkt(var, w, f, Yt);
			w = calcW(zkt, K, NT);
			Q = calcQ(w, f);
			var = calcVar(zkt, K, f, Yt);
			double calcVal = calcVal(w, var, f, Yt);
			if (Math.abs(calcVal - initval) <= 0.000001) {
				break;
			} else {
				initval = calcVal;
			}
		}
		Q = calcQ(w, f);
		double[] cnrm = SimP.get(0);
		double[] miroc = SimP.get(1);
		double[] canesm = SimP.get(2);
		double[] gfdl = SimP.get(3);
		double[] bmaP = new double[T];
		for (int i = 0; i < T; i++) {
			bmaP[i] = w[0] * cnrm[i] + w[1] * miroc[i] + w[2] * canesm[i] + w[3] * gfdl[i];
		}
		double[][] q = uncertainty(w, var, SimP.get(0).length, 100, g);// 蒙特卡罗抽样法
		return q;
	}

	// ----------------蒙特卡罗不确定性抽样方法------------------------
	private double[][] uncertainty(double[] w, double[] var, int sim_length, int time, double[][] g) {// time为抽样的场数，100表示每年抽样100
		double[][] a = new double[sim_length][time];

		double[][] q_u_l = new double[sim_length][time];
		for (int t = 0; t < sim_length; t++) {
			double[] b = new double[time];
			for (int i = 0; i < time; i++) {
				double r = Math.random();
				if (r < w[0]) {
					double mean = g[0][t];
					double sd = var[0];
					double ff = calcxF(g[0][t], mean, sd);
					a[t][i] = ff;
				} else {
					double mean = g[1][t];
					double sd = var[1];
					double ff = calcxF(g[1][t], mean, sd);
					a[t][i] = ff;
				}
			}
			for (int j = 0; j < a[t].length; j++) {
				b[j] = a[t][j];
			}
			Arrays.sort(b);
			q_u_l[t] = b;
		}
		return q_u_l;
	}

	public double calcxF(double x, double mean, double var) {
		NormalDistribution normalDistribution = new NormalDistribution(mean, Math.sqrt(var));
		double rand = normalDistribution.sample();
		return rand;
	}

	private double[] calcQ(double[] w, double[][] f) {
		int K = f.length;
		int NT = f[0].length;
		double[] Q = new double[NT];
		for (int t = 0; t < NT; t++) {
			Q[t] = 0;
			for (int k = 0; k < K; k++) {
				Q[t] += w[k] * f[k][t];
			}
		}
		return Q;
	}

	// 计算似然值val的方法
	public double calcVal(double[] w, double var[], double[][] f, double[] Yt) {
		int K = f.length;
		int NT = f[0].length;
		double val = 0;
		for (int t = 0; t < NT; t++) {
			double sum = 0;
			for (int k = 0; k < K; k++) {
				double mean = f[k][t];
				double sd = var[k];
				double ff = calcF(Yt[t], mean, sd);
				sum += w[k] * ff;
			}
			val += Math.log(sum);
		}
		return val;
	}

	public double calcF(double x, double mean, double var) {
		NormalDistribution normalDistribution = new NormalDistribution(mean, Math.sqrt(var));
		double cumulativeProbability = normalDistribution.cumulativeProbability(x);
		double density = normalDistribution.density(x);
		return density;
	}

	public double[][] calcZkt(double[] var, double[] w, double[][] f, double[] Yt) {
		int K = f.length;
		int NT = f[0].length;
		double[][] zkt = new double[K][Yt.length];
		for (int t = 0; t < Yt.length; t++) {
			double sum = 0;
			for (int k = 0; k < K; k++) {
				double fkt = f[k][t];
				sum += calcF(Yt[t], fkt, var[k]);
			}
			for (int k = 0; k < K; k++) {
				double fkt = f[k][t];
				double calcF = calcF(Yt[t], fkt, var[k]);
				zkt[k][t] = calcF(Yt[t], fkt, var[k]) / sum;
			}
		}
		return zkt;
	}

	// 计算BMA权重的方法
	public double[] calcW(double[][] zkt, int K, int NT) {
		double[] w = new double[K];
		for (int k = 0; k < K; k++) {
			double sum = 0;
			for (int t = 0; t < NT; t++) {
				sum += zkt[k][t];
			}
			w[k] = sum / NT;
		}
		return w;
	}

	// 计算模型预报误差var的方法
	public double[] calcVar(double[][] zkt, int x, double[][] f, double[] Yt) {
		double var[] = new double[x];
		int K = f.length;
		int NT = f[0].length;
		double sum1 = 0;
		double sum2 = 0;
		for (int k = 0; k < K; k++) {
			for (int t = 0; t < NT; t++) {
				sum1 += zkt[k][t] * (Yt[t] - f[k][t]) * (Yt[t] - f[k][t]);
				sum2 += zkt[k][t];
			}
			var[k] = sum1 / sum2;
		}
		return var;
	}

	public List<double[]> getObjP() {
		return ObjP;
	}

	public void setObjP(List<double[]> objP) {
		ObjP = objP;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
