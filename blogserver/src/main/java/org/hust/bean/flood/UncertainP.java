package org.hust.bean.flood;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import com.alibaba.fastjson.JSON;

public class UncertainP {
	private double[][] BaseFlood;
	private List<double[]> ObjP;
	private String pattern;
	private String flag;

	// -----------以下为计算一定设计频率下设计洪峰、设计24h洪量、设计3日洪量、设计7日洪量、设计15日洪量的方法---------------------------------------------------------------------------
	public Map<String, Object> getDesignP(double[][] baseFlood, double[] bma, String flag) {
		double[][] piii = null;
		Map<String, Object> map = new HashMap<String, Object>();
		String baseJson = JSON.toJSONString(baseFlood);
		String bmaJson = JSON.toJSONString(bma);
		try {
			RConnection c = new RConnection();
			REXP Rservesion = c.eval("R.version.string");
			// 1.加载R程序包，读取计算数据
			c.eval("library(lmom)");
			c.eval("library(gamlss)");
			c.eval("library(PearsonDS)");
			c.eval("library(rjson)");
			c.eval("library(jsonlite)");
			c.eval("json <-'" + baseJson + "'");
			c.eval("mydf <- fromJSON(json)");// 基准期洪水降水序列
			c.eval("d=data.frame(mydf)");
			c.eval("y=mydf[" + flag + ",]");// 基准期洪峰序列
			c.eval("P=mydf[6,]");// 基准期年降水
			c.eval("lmom = samlmu(y)");
			c.eval("para<-pelpe3(lmom)");
			// 2.构建GAMLSS模型，求出PIII分布的参数
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
			// 计算GAMLSS模型PIII分布中的参数
			c.eval("pa = c(mod.try$mu.coefficients[1], mod.try$mu.coefficients[2],mod.try$sigma.coefficients[1],mod.try$nu.coefficients[1])");
			// 3.计算未来情景设计频率下的设计洪水值
			c.eval("pi<-c(0.9999,0.9995,0.999,0.998,0.995,0.99,0.95,0.9,0.85,0.8,0.7,0.6,0.5,0.4,0.3,0.2,0.15,0.1,0.05,0.01,0.001,0.0005,0.0001)");
			c.eval("dim(pi)<-c(23,1)");
			c.eval("json <-'" + bmaJson + "'");
			c.eval("mydf <- fromJSON(json)");
			c.eval("ppp<-mydf");
			c.eval("mu<-array(exp(pa[1]+pa[2]*ppp),dim=c(1,3))");
			c.eval("sigma<-rep(exp(pa[3]),3)");
			c.eval("nu<-array(pa[4],dim=c(1,3))");

			c.eval("a<-array(0,dim=c(23,3))");
			c.eval("for(i in 1:23){\n" + "" + "qpiii<-qPearsonTypeIII(pi[i,1],mu,sigma,nu)\n" + "" + "a[i,]<-qpiii}");
			REXP eval = c.eval("a");
			REXP eval2 = c.eval("mu");
			REXP eval3 = c.eval("sigma");
			REXP eval4 = c.eval("nu*2");
			piii = eval.asDoubleMatrix();
			double[] ex = eval2.asDoubles();
			double[] cv = eval3.asDoubles();
			double[] cs = eval4.asDoubles();
			map.put("ex", ex);
			map.put("cv", cv);
			map.put("cs", cs);
			map.put("piii", piii);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

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

	public double[] getBMA(List<double[]> ObjP, List<double[]> SimP) {
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
		double[] bma = new double[T];
		for (int i = 0; i < T; i++) {
			bma[i] = w[0] * cnrm[i] + w[1] * miroc[i] + w[2] * canesm[i] + w[3] * gfdl[i];
		}
		return bma;
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

	public double[][] getBaseFlood() {
		return BaseFlood;
	}

	public void setBaseFlood(double[][] baseFlood) {
		BaseFlood = baseFlood;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
