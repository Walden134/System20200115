package org.hust.bean.flood;

import com.alibaba.fastjson.JSON;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.hust.bean.hydro.DoubleCurve;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;

public class FloodRisk {
	// 水位库容曲线
	private DoubleCurve levelCapacityCurve;
	// 下泄尾水曲线
	private DoubleCurve leveldownOutflowCurve;
	private List<double[]> typicalFloods;
	private double levelDesign;
	private double levelCheck;
	private double levelDam;
	private String pattern;

	/**
	 * @param ObjP基准期实测数据和4种GCM下预测的降雨
	 * @param SimP未来情景下4种GCM的降雨数据(RCP2.6,RCP4.5,RCP8.5)
	 * @return
	 */
	public String getBMAPJson(List<double[]> ObjP, List<double[]> SimP) {
		double[] Yt = ObjP.get(0);// 基准期实测数据
		int NT = Yt.length;// 基准期实测数据的长度
		int K = ObjP.size() - 1;// 基准期模拟数据的长度:4
		int T = SimP.get(0).length;// 未来一个GCM数据的长度
		double[] Q = new double[NT];
		double[][] f = new double[K][NT];
		double[][] g = new double[K][T];
		double[][] h = new double[K][3];
		for (int i = 0; i < f.length; i++) {
			f[i] = ObjP.get(i + 1);// 基准期模拟数据
			g[i] = SimP.get(i);// 未来数据
		}
		int sum1 = 0;

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
//		double[][] q = new double[1][3];
//		for (int i = 0; i < 30; i++) {
//			q[0][0] += bmaP[i];
//		}
//		q[0][0] = q[0][0] / 30.0;
//		for (int i = 30; i < 55; i++) {
//			q[0][1] += bmaP[i];
//		}
//		q[0][1] = q[0][1] / 25.0;
//		for (int i = 55; i < 80; i++) {
//			q[0][2] += bmaP[i];
//		}
//		q[0][2] = q[0][2] / 25.0;

		return JSON.toJSONString(q);
	}

	public String getBMAPJson2(List<double[]> ObjP, List<double[]> SimP) {
		double[] Yt = ObjP.get(0);// 基准期实测数据
		int NT = Yt.length;// 基准期实测数据的长度
		int K = ObjP.size() - 1;// 基准期模拟数据的长度:4
		int T = SimP.get(0).length;// 未来一个GCM数据的长度
		double[] Q = new double[NT];
		double[][] f = new double[K][NT];
		double[][] g = new double[K][T];
		double[][] h = new double[K][3];
		for (int i = 0; i < f.length; i++) {
			f[i] = ObjP.get(i + 1);// 基准期模拟数据
			g[i] = SimP.get(i);// 未来数据
		}
		int sum1 = 0;

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
//		double[][] q = uncertainty(w, var, SimP.get(0).length, 100, g);// 蒙特卡罗抽样法
		double[][] q = new double[1][3];
		for (int i = 0; i < 30; i++) {
			q[0][0] += bmaP[i];
		}
		q[0][0] = q[0][0] / 30.0;
		for (int i = 30; i < 55; i++) {
			q[0][1] += bmaP[i];
		}
		q[0][1] = q[0][1] / 25.0;
		for (int i = 55; i < 80; i++) {
			q[0][2] += bmaP[i];
		}
		q[0][2] = q[0][2] / 25.0;

		return JSON.toJSONString(q);
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
		double[][] transformMatrix = transformMatrix(q_u_l);
		return transformMatrix;
	}

	public double[][] transformMatrix(double matrix[][]) {
		double a[][] = new double[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				a[i][j] = matrix[j][i];
			}
		}
		return a;

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

	/**
	 * @param baseFloodJson 实测洪水序列
	 * @param bmaPJson      bma年降水
	 * @return 设计洪水序列
	 */
	public Map<String, double[][]> getDesignFloods(String baseFloodJson, String bmaPJson, String yearStr) {
		Map<String, double[][]> designFloods = new HashMap<>();
		designFloods.put("d_0", getDesignFlood(baseFloodJson, bmaPJson, "2", yearStr));
		designFloods.put("d_1", getDesignFlood(baseFloodJson, bmaPJson, "3", yearStr));
		designFloods.put("d_3", getDesignFlood(baseFloodJson, bmaPJson, "4", yearStr));
		designFloods.put("d_7", getDesignFlood(baseFloodJson, bmaPJson, "5", yearStr));
		designFloods.put("d_15", getDesignFlood(baseFloodJson, bmaPJson, "6", yearStr));
		return designFloods;
	}

	public double[][] getDesignFlood(String baseFloodJson, String bmaPJson, String bmaQ, String yearStr) {
		double[][] designFlood = null;
		try {
			RConnection c = new RConnection();
			c.eval("R.version.string");
			/**
			 * 1.加载R程序包，读取计算数据
			 */
			c.eval("library(lmom)");
			c.eval("library(gamlss)");
			c.eval("library(PearsonDS)");
			c.eval("library(rjson)");
			c.eval("library(jsonlite)");
			c.eval("json <-'" + bmaPJson + "'");
			c.eval("BmaP <- fromJSON(json)");// 基准期洪水降水数据
			c.eval("p=data.frame(BmaP)");
			c.eval("json <-'" + baseFloodJson + "'");
			c.eval("BaseFlood <- fromJSON(json)");// 基准期洪水降水数据
			c.eval("d=data.frame(BaseFlood)");
			c.eval("y=BaseFlood[," + bmaQ + "]");// 基准期洪峰序列
			c.eval("P=BaseFlood[,7]");// 基准期年降水
			c.eval("lmom = samlmu(y)");
			c.eval("para<-pelpe3(lmom)");
			/**
			 * 2.构建GAMLSS模型，求出PIII分布的参数
			 */
			// 构建GAMLSS模型PIII分布的几个函数
			c.eval("PearsonTypeIII<-function(mu.link='log',sigma.link='log', nu.link ='identity'){\n"
					+ "require(PearsonDS)\n"
					+ "mstats<-checklink('mu.link','Pearson Type III Family',substitute(mu.link),c('inverse','log','identity','own'))\n"
					+ "dstats <- checklink('sigma.link','Pearson Type III Family', substitute(sigma.link), c('inverse', 'log', 'identity', 'own'))\n"
					+ "vstats <- checklink('nu.link', 'Pearson Type III Family', substitute(nu.link), c('inverse', 'log', 'identity', 'own'))\n "
					+ "structure(\n " + "list(family = c('PearsonTypeIII', 'Pearson Type III Family'),\n "
					+ "parameters = list(mu=TRUE, sigma=TRUE, nu=TRUE),\n " + "nopar = 3,\n " + "type = 'Continuous',\n"
					+ "mu.link = as.character(substitute(mu.link)),\n "
					+ "sigma.link = as.character(substitute(sigma.link)),\n "
					+ "nu.link = as.character(substitute(nu.link)),\n " + "mu.linkfun = mstats$linkfun,\n "
					+ "sigma.linkfun = dstats$linkfun,\n " + "nu.linkfun = vstats$linkfun,\n "
					+ "mu.linkinv = mstats$linkinv,\n " + "sigma.linkinv = dstats$linkinv,\n"
					+ "nu.linkinv = vstats$linkinv,\n " + "mu.dr = mstats$mu.eta,\n" + "sigma.dr = dstats$mu.eta,\n "
					+ "nu.dr = vstats$mu.eta,\n"
					+ "dldm = function(y,mu,sigma,nu) {-(mu^2*sigma^2 - nu*mu^2*sigma + mu*y - y^2)/(mu^2*sigma*(mu*sigma - mu*nu + nu*y))},\n"
					+ "d2ldm2=function(y,mu,sigma,nu) {-(- mu^3*nu^2*sigma + 2*mu^3*nu*sigma^2 - mu^3*sigma^3 + 2*mu^2*nu*y - 2*mu^2*sigma*y - 4*mu*nu*y^2 + 3*mu*sigma*y^2 + 2*nu*y^3)/(mu^3*sigma*(mu*sigma - mu*nu + nu*y)^2)},\n "
					+ "dldd = function(y,mu,sigma,nu) {-(mu^2*sigma^2 - mu^2 + 2*mu*y - y^2)/(mu*sigma^2*(mu*sigma - mu*nu + nu*y))},\n"
					+ "d2ldd2 = function(y,mu,sigma,nu) {(mu^3*sigma^3 - 3*mu^3*sigma + 2*nu*mu^3 + 6*mu^2*sigma*y - 6*nu*mu^2*y - 3*mu*sigma*y^2 + 6*nu*mu*y^2 - 2*nu*y^3)/(mu*sigma^3*(mu*sigma - mu*nu + nu*y)^2)},\n"
					+ "dldv = function(y,mu,sigma,nu) {(nu^2*y^2 + mu^2*nu^2 - 2*mu^2*nu*sigma - 2*mu*nu^2*y + 2*mu^2*sigma^2*digamma(1/nu^2) - 2*mu^2*sigma^2*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + mu^2*nu^2*sigma^2 + 2*mu*nu*sigma*y - 2*mu^2*nu*sigma*digamma(1/nu^2) + 2*mu^2*nu*sigma*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + 2*mu*nu*sigma*y*digamma(1/nu^2) - 2*mu*nu*sigma*y*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)))/(mu*nu^3*sigma*(mu*sigma - mu*nu + nu*y))},\n"
					+ "d2ldv2 = function(y,mu,sigma,nu) {-(2*nu^5*y^3 - 2*mu^3*nu^5 + 4*mu^3*sigma^3*trigamma(1/nu^2) + 5*mu^3*nu^4*sigma - 6*mu*nu^5*y^2 + 6*mu^2*nu^5*y - 4*mu^3*nu^2*sigma^3 + 2*mu^3*nu^3*sigma^2 + mu^3*nu^4*sigma^3 - 2*mu^3*nu^5*sigma^2 + 6*mu^3*nu^2*sigma^3*digamma(1/nu^2) - 12*mu^3*nu^3*sigma^2*digamma(1/nu^2) - 6*mu^3*nu^2*sigma^3*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + 12*mu^3*nu^3*sigma^2*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) - 2*mu^2*nu^3*sigma^2*y + 2*mu^2*nu^5*sigma^2*y - 8*mu^3*nu*sigma^2*trigamma( 1/nu^2) + 4*mu^3*nu^2*sigma*trigamma(1/nu^2) + 6*mu^3*nu^4*sigma*digamma(1/nu^2) - 6*mu^3*nu^4*sigma*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + 5*mu*nu^4*sigma*y^2 - 10*mu^2*nu^4*sigma*y + 4*mu*nu^2*sigma*y^2*trigamma(1/nu^2) + 8*mu^2*nu*sigma^2*y*trigamma( 1/nu^2) - 8*mu^2*nu^2*sigma*y*trigamma( 1/nu^2) + 6*mu*nu^4*sigma*y^2*digamma(1/nu^2) - 12*mu^2*nu^4*sigma*y*digamma(1/nu^2) - 6*mu*nu^4*sigma*y^2*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + 12*mu^2*nu^4*sigma*y*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)) + 12*mu^2*nu^3*sigma^2*y*digamma(1/nu^2) - 12*mu^2*nu^3*sigma^2*y*log((mu*sigma - mu*nu + nu*y)/(mu*nu^2*sigma)))/(mu*nu^6*sigma*(mu*sigma - mu*nu + nu*y)^2)},\n"
					+ "d2ldmdd = function(y,mu,sigma,nu) {-(y*(nu*mu^2*sigma^2 - 2*mu^2*sigma + nu*mu^2 + 2*mu*sigma*y - 2*nu*mu*y + nu*y^2))/(mu^2*sigma^2*(mu*sigma - mu*nu + nu*y)^2)},\n"
					+ "d2ldmdv = function(y,mu,sigma,nu) {(y*(mu^2*sigma^2 - mu^2 + 2*mu*y - y^2))/(mu^2*sigma*(mu*sigma - mu*nu + nu*y)^2)},\nd2ldddv = function(y,mu,sigma,nu) {-((mu - y)*(mu^2*sigma^2 - mu^2 + 2*mu*y - y^2))/(mu*sigma^2*(mu*sigma - mu*nu + nu*y)^2)},\n "
					+ "G.dev.incr = function(y,mu,sigma,nu,...){\n "
					+ "GD<- -2*dPearsonTypeIII(y,mu=mu,sigma=sigma,nu=nu,log=TRUE)},\n "
					+ "rqres = expression(rqres(pfun='pPearsonTypeIII', type='Continuous', y=y, mu=mu, sigma=sigma, nu=nu)),\n"
					+ "mu.initial = expression( mu <- (y+mean(y))/2),\n "
					+ "sigma.initial = expression( sigma <- rep(1,length(y))),\n "
					+ "nu.initial = expression( nu <- rep(1, length(y))),\n "
					+ "mu.valid = function(mu) all(mu > 0),\n " + "sigma.valid = function(sigma) all(sigma > 0),\n "
					+ "nu.valid = function(nu) TRUE,\n" + "y.valid = function(y) all(y>0)),\n"
					+ "class = c('gamlss.family','family'))}");
			c.eval("require(FAdist)");
			c.eval("dPearsonTypeIII<-function(y, mu, sigma, nu, log=FALSE){\n" + "afa<- 1/nu/nu\n "
					+ "bta<- sigma*mu*nu\n" + "a0<- mu-sigma*mu/nu\n"
					+ " fy <- dpearsonIII(y, shape = afa,location = a0,scale = bta,log = log)\n" + "fy}");
			c.eval("pPearsonTypeIII <- function(q, mu, sigma, nu,lower.tail = TRUE, log.p = FALSE){\n "
					+ "afa<- 1/nu/nu\n" + "bta<- sigma*mu*nu\n" + "a0<- mu-sigma*mu/nu\n"
					+ "cdf <- ppearsonIII(q, shape = afa,location = a0,scale = bta,lower.tail = TRUE, log.p = FALSE)\n"
					+ "cdf}");
			c.eval("qPearsonTypeIII <- function(p, mu, sigma, nu,lower.tail = TRUE, log.p = FALSE){\n"
					+ "afa<- 1/nu/nu\n" + "bta<- sigma*mu*nu\n" + "a0<- mu-sigma*mu/nu\n"
					+ "if (any(p < 0)|any(p > 1)) stop(paste('p must be between 0 and 1', '\n', ''))\n"
					+ "q <- qpearsonIII(p, shape = afa,location = a0,scale = bta,lower.tail = TRUE, log.p = FALSE)\n"
					+ "q}");
			c.eval("rPearsonTypeIII <- function(n, mu, sigma, nu){\n" + "afa<- 1/nu/nu\n" + "bta<- sigma*mu*nu\n"
					+ "a0<- mu-sigma*mu/nu\n" + "r <- rpearsonIII(n, shape = afa,location = a0,scale = bta)\n" + "r}");
			// 建立GAMLSS模型
			c.eval("con=gamlss.control(c.crit = 0.001, n.cyc = 20000, mu.step = 0.1, sigma.step = 0.01, nu.step = 0.01,tau.step = 1, gd.tol = 5000, iter = 0, trace = TRUE, autostep = TRUE,save = TRUE)");
			c.eval("mod.try=gamlss(y ~P, sigma.fo = ~1,nu.fo = ~1,data=d,family = PearsonTypeIII,mu.start=para[1],sigma.start=para[2]/para[1],nu.start=para[3]/2,mixed=CG(),control=con)");
			// 求出GAMLSS模型PIII参数
			c.eval("pa = c(mod.try$mu.coefficients[1], mod.try$mu.coefficients[2],mod.try$sigma.coefficients[1],mod.try$nu.coefficients[1])");
			/**
			 * 3. 计算未来情景洪水系列值
			 */
			c.eval("mu<-as.matrix(exp(pa[1]+pa[2]*p))");
//            c.eval("sigma=exp(pa[3])");
//            c.eval("nu<-pa[4]");

			c.eval("sigma=0.29");
			c.eval("nu<-0.58");
			c.eval("location<-array(mu*(1-sigma/nu),dim=c(100,3))");
			c.eval("scale<-array((mu*sigma*nu),dim=c(100,3))");
			c.eval("qpiii<-qpearsonIII(" + yearStr
					+ ",shape=1/(nu*nu), location=mu*(1-sigma/nu), scale=(mu*sigma*nu))");
			REXP eval = c.eval("qpiii");
			designFlood = eval.asDoubleMatrix();
			c.close();
		} catch (Exception e) {
		}
		return designFlood;
	}

	public List<double[][]> calcEnlargeFlood(List<double[]> typicalFloods, Map<String, double[][]> designFloods) {
		double[] flood = typicalFloods.get(0);// 典型洪水过程//180
		double[] dd = typicalFloods.get(1);// 典型洪峰、一日、三日、七日、十五日洪量
		double[] wd = new double[dd.length];
		double W_m = 5670;// 典型设计洪水
		double W_1 = 4.81;
		double W_31 = 13.5;
		double W_73 = 27.4;
		double W_157 = 47.3;
		int num = designFloods.get("d_0").length;// 100
		int years = designFloods.get("d_0")[0].length;// 80
		List<double[][]> floodHydrograph = new ArrayList<>();
		for (int i = 0; i < years; i++) {
			double[][] newFlood = new double[num][flood.length];
			for (int j = 0; j < num; j++) {
				double tmp_d0 = designFloods.get("d_0")[j][i];
				double tmp_d1 = designFloods.get("d_1")[j][i];
				double tmp_d3 = designFloods.get("d_3")[j][i];
				double tmp_d7 = designFloods.get("d_7")[j][i];
				double tmp_d15 = designFloods.get("d_15")[j][i];
				double R_m = tmp_d0 / W_m;
				double R_1 = tmp_d0 / W_m;
				double R_31 = tmp_d0 / W_m;
				double R_73 = tmp_d0 / W_m;
				double R_157 = tmp_d0 / W_m;

//                double R_1 = tmp_d1 / W_1;
//                double R_31 = (tmp_d3 - tmp_d1) / (W_31 - W_1);
//                double R_73 = (tmp_d7 - tmp_d3) / (W_73 - W_31);
//                double R_157 = (tmp_d15 - tmp_d7) / (W_157 - W_73);
				for (int k = 0; k < flood.length; k++) {// 180
					if (dd[k] == W_m) {
						wd[k] = R_m;
					}
					if (dd[k] == W_1) {
						wd[k] = R_1;
					}
					if (dd[k] == W_31) {
						wd[k] = R_31;
					}
					if (dd[k] == W_73) {
						wd[k] = R_73;
					}
					if (dd[k] == W_157) {
						wd[k] = R_157;
					}
					newFlood[j][k] = flood[k] * wd[k];
				}
			}
			floodHydrograph.add(newFlood);
		}
		return floodHydrograph;
	}

	public List<double[]> calcRiskAnalyst(List<double[][]> floodHydrograph) {
		double V[];
		double Z[];
		double q[];
		double qq[];
		int dt = 1;
		List<double[]> riskRes = new ArrayList<>();
		int yearNum = floodHydrograph.size();// 80
		int floodNum = floodHydrograph.get(0).length;// 100
		double[] riskrate1 = new double[yearNum];
		double[] riskrate2 = new double[yearNum];
		double[] riskrate3 = new double[yearNum];
		double[] risklevel = new double[yearNum];
		double[][] levels = new double[floodNum][yearNum];// 100*80
		int num1;
		int num2;
		int num3;
		for (int k = 0; k < yearNum; k++) {// 80
			num1 = 0;
			num2 = 0;
			num3 = 0;
			for (int m = 0; m < floodNum; m++) {// 100
				double max_lev = 0;
				double Qin[] = floodHydrograph.get(k)[m];
				V = new double[Qin.length];
				Z = new double[Qin.length];
				q = new double[Qin.length];
				qq = new double[Qin.length];
				q[0] = Qin[0];// 第一个下泄流量
				V[0] = 45580;// 第一个调节库容
				double[] level = new double[Qin.length];
				double max = 0;
				double maxq = 0;
				int mIndex = 0;
				double startQ = interpolationY(V[0], "Q_V");
				double startQ1 = leveldownOutflowCurve.getDeltaByV0(V[0]);
				for (int i = 1; i < Qin.length; i++) {// 180
					if (Qin[i] <= startQ) {
						q[i] = Qin[i];
						V[i] = V[0];
					} else {
						double V1 = V[i - 1];
						double V2 = V[i];
						double Qt = Qin[i - 1];
						double Qt1 = Qin[i];
						double qt = q[i - 1];
						double qt1 = qt / 2;// 试算值
						double tmpq = 0;
						double Q1 = 0.5 * 3600 * dt * (Qt1 + Qt) / 10000;
						do {
							qt1 = (qt1 + tmpq) / 2;
							double Q2 = 0.5 * 3600 * dt * (qt1 + qt) / 10000;
							double dQ = Q1 - Q2;
							V2 = V1 + dQ;
							tmpq = interpolationY(V2, "Q_V");// 蓄泄方程:q=f(V)
							double tmpq1 = leveldownOutflowCurve.getDeltaByV0(V[0]);
						} while (Math.abs(qt1 - tmpq) > 0.000001);
						q[i] = qt1;
						V[i] = V2;
					}
					level[i] = interpolationY(V[i], "Z_V");
					double lev = levelCapacityCurve.getDeltaByV1(V[i]);
					if (max <= level[i]) {
						max = level[i];
						mIndex = i;
					}
					if (max_lev <= level[i]) {
						max_lev = level[i];
					}
					if (maxq <= q[i]) {
						maxq = q[i];
					}
				}

				if (level[mIndex] > levelDesign) {
					num1 = num1 + 1;
				}
				// 计算第二个风险率：超校核洪水位
				if (level[mIndex] > levelCheck) {
					num2 = num2 + 1;
				}
				// 计算第三个风险率：超坝顶高程
				if (level[mIndex] > levelDam) {
					num3 = num3 + 1;
				}
				levels[m][k] = max_lev;
			}

			// 计算三种情景下的风险率
			DecimalFormat df = new DecimalFormat("#.00");

			riskrate1[k] = (double) num1 / floodHydrograph.get(k).length;
			riskrate2[k] = (double) num2 / floodHydrograph.get(k).length;
			riskrate3[k] = (double) num3 / floodHydrograph.get(k).length;
			riskrate1[k] = Double.parseDouble(df.format(riskrate1[k]));
			riskrate2[k] = Double.parseDouble(df.format(riskrate2[k]));
			riskrate3[k] = Double.parseDouble(df.format(riskrate3[k]));
		}
		riskRes.add(riskrate1);
		riskRes.add(riskrate2);
		riskRes.add(riskrate3);

		Comparator<Double> cmp = new Comparator<Double>() {
			public int compare(Double i1, Double i2) {
				return (int) (i2 - i1);
			}
		};

		Arrays.sort(levels[49]);
		Arrays.sort(levels[74]);
		Arrays.sort(levels[89]);
		Arrays.sort(levels[94]);
		riskRes.add(levels[49]);
		riskRes.add(levels[74]);
		riskRes.add(levels[89]);
		riskRes.add(levels[94]);
		return riskRes;
	}

	public List<double[]> calcRiskAnalyst2(List<double[][]> floodHydrograph) {
		double V[];
		double Z[];
		double q[];
		double qq[];
		int dt = 1;
		List<double[]> riskRes = new ArrayList<>();
		int yearNum = floodHydrograph.size();// 3
		int floodNum = floodHydrograph.get(0).length;// 1
		double[] risklevel = new double[yearNum];
		for (int k = 0; k < yearNum; k++) {// 3
			double max_lev = 0;
			for (int m = 0; m < floodNum; m++) {// 1
				double Qin[] = floodHydrograph.get(k)[m];
				V = new double[Qin.length];
				Z = new double[Qin.length];
				q = new double[Qin.length];
				qq = new double[Qin.length];
				q[0] = Qin[0];// 第一个下泄流量
				V[0] = 45580;// 第一个调节库容
				double[] level = new double[Qin.length];
				double max = 0;
				double maxq = 0;
				int mIndex = 0;
				double startQ = interpolationY(V[0], "Q_V");
				double startQ1 = leveldownOutflowCurve.getDeltaByV0(V[0]);
				for (int i = 1; i < Qin.length; i++) {// 180
					if (Qin[i] <= startQ) {
						q[i] = Qin[i];
						V[i] = V[0];
					} else {
						double V1 = V[i - 1];
						double V2 = V[i];
						double Qt = Qin[i - 1];
						double Qt1 = Qin[i];
						double qt = q[i - 1];
						double qt1 = qt / 2;// 试算值
						double tmpq = 0;
						double Q1 = 0.5 * 3600 * dt * (Qt1 + Qt) / 10000;
						do {
							qt1 = (qt1 + tmpq) / 2;
							double Q2 = 0.5 * 3600 * dt * (qt1 + qt) / 10000;
							double dQ = Q1 - Q2;
							V2 = V1 + dQ;
							tmpq = interpolationY(V2, "Q_V");// 蓄泄方程:q=f(V)
							double tmpq1 = leveldownOutflowCurve.getDeltaByV0(V[0]);
						} while (Math.abs(qt1 - tmpq) > 0.000001);
						q[i] = qt1;
						V[i] = V2;
					}
					level[i] = interpolationY(V[i], "Z_V");
					double lev = levelCapacityCurve.getDeltaByV1(V[i]);
					if (max <= level[i]) {
						max = level[i];
						mIndex = i;
					}
					if (max_lev <= level[i]) {
						max_lev = level[i];
					}
				}
			}
			// 计算三种情景下的风险率
			DecimalFormat df = new DecimalFormat("#.00");
			risklevel[k] = Double.parseDouble(df.format(max_lev));
		}
		riskRes.add(risklevel);
		return riskRes;
	}

	// 读取Z,V,P的方法
	public double interpolationY(double x, String string) {
		double y = 0;

		double[] V = { 0, 1, 2, 8, 36, 174, 475, 938, 1625, 2521, 3627, 4960, 6486, 8183, 10054, 12123, 14415, 16954,
				19758, 22819, 26138, 29719, 33543, 37607, 41921, 46495, 51335, 56175 };
		double[] Z = { 1970, 1975, 1980, 1985, 1990, 1995, 2000, 2005, 2010, 2015, 2020, 2025, 2030, 2035, 2040, 2045,
				2050, 2055, 2060, 2065, 2070, 2075, 2080, 2085, 2090, 2095, 2100, 2105 };
		double[] q = { 0, 85.82142857, 171.6428571, 257.4642857, 343.2857143, 429.1071429, 514.9285714, 600.75,
				686.5714286, 772.3928571, 858.2142857, 944.0357143, 1029.857143, 1115.678571, 1201.5, 1808.9, 2281.3,
				2675.4, 3030.9, 3309.1, 3591.1, 3820.1, 4039.4, 4902.6, 6550.8, 8594.2, 11150.5, 13706.8 };
		double maxV = V[V.length - 1];
		double maxZ = Z[Z.length - 1];
		double maxq = q[q.length - 1];

		switch (string) {
		case "Q_Z":

			for (int i = 1; i < Z.length; i++) {
				if (x >= Z[i - 1] && x <= Z[i]) {
					y = q[i - 1] + (x - Z[i - 1]) * (q[i] - q[i - 1]) / (Z[i] - Z[i - 1]);
				}
			}
			break;
		case "Z_V":
			for (int i = 1; i < V.length; i++) {
				if (x >= V[i - 1] && x <= V[i]) {
					y = Z[i - 1] + (x - V[i - 1]) * (Z[i] - Z[i - 1]) / (V[i] - V[i - 1]);
				}
			}
			break;

		case "Q_V":
			// if (x > maxV)
			// return 0.1364 * x - 659.34;
			for (int i = 1; i < V.length; i++) {
				if (x >= V[i - 1] && x <= V[i]) {
					y = q[i - 1] + (x - V[i - 1]) * (q[i] - q[i - 1]) / (V[i] - V[i - 1]);
				}
			}
			break;
		}
		return y;
	}

	public List<double[]> getRiskRes(List<double[]> typicalFloods, String baseFloodJson, List<double[]> ObjP,
			List<double[]> SimP) {
		String bmaPJson = getBMAPJson(ObjP, SimP);
		Map<String, double[][]> designFloods = getDesignFloods(baseFloodJson, bmaPJson, "0.998");
		List<double[][]> floodHydrograph = calcEnlargeFlood(typicalFloods, designFloods);
		List<double[]> riskRes = calcRiskAnalyst(floodHydrograph);
		return riskRes;
	}

	public List<double[]> getRiskRes2(List<double[]> typicalFloods, String baseFloodJson, List<double[]> ObjP,
			List<double[]> SimP, String yearStr) {
		String bmaPJson = getBMAPJson2(ObjP, SimP);
		Map<String, double[][]> designFloods = getDesignFloods(baseFloodJson, bmaPJson, yearStr);
		List<double[][]> floodHydrograph = calcEnlargeFlood(typicalFloods, designFloods);
		List<double[]> riskRes = calcRiskAnalyst2(floodHydrograph);
		return riskRes;
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

	public List<double[]> getTypicalFloods() {
		return typicalFloods;
	}

	public void setTypicalFloods(List<double[]> typicalFloods) {
		this.typicalFloods = typicalFloods;
	}

	public double getLevelDesign() {
		return levelDesign;
	}

	public void setLevelDesign(double levelDesign) {
		this.levelDesign = levelDesign;
	}

	public double getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(double levelCheck) {
		this.levelCheck = levelCheck;
	}

	public double getLevelDam() {
		return levelDam;
	}

	public void setLevelDam(double levelDam) {
		this.levelDam = levelDam;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		return "FloodRisk [levelCapacityCurve=" + levelCapacityCurve + ", leveldownOutflowCurve="
				+ leveldownOutflowCurve + ", typicalFloods=" + typicalFloods + ", levelDesign=" + levelDesign
				+ ", levelCheck=" + levelCheck + ", levelDam=" + levelDam + ", pattern=" + pattern + "]";
	}

	public static List<double[]> readTxt(String pathname) {
		File filename = new File(pathname); // 要读取以上路径的input.txt文件
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		try {
			line = br.readLine();
			while (line != null) {
				if (line.contains("#") || line == "\\s+") {
					line = br.readLine();
					continue;
				}
				list.add(line);
				line = br.readLine(); // 一次读入一行数据
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<double[]> datas = new ArrayList<double[]>();
		String[] strs = list.get(0).split("\\s+");
		for (int j = 0; j < strs.length; j++) {
			double[] data = new double[list.size()];
			datas.add(data);
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == "") {
				continue;
			}
			strs = list.get(i).split("\\s+");
			for (int j = 0; j < strs.length; j++) {
				double[] data = datas.get(j);
				data[i] = Double.valueOf(strs[j]);
			}
		}
		return datas;
	}

}
