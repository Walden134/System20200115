package org.sang.bean.flood;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.rosuda.REngine.REXPMismatchException;
import java.util.Arrays;
import org.rosuda.REngine.REXP;

public class DesignFloodP {
	public static void main(String[] args) throws RserveException, REXPMismatchException {
		String BaseFloodJson = "'[[1953,5420,4.53,12.4,23.5,41.7,677.36],[1954,5700,4.77,13.5,27.9,56.6,858.31],[1955,4360,3.67,10.2,21,43.8,766.08],[1956,2790,2.36,6.52,14.3,25.6,690.17],[1957,4530,3.84,10.8,22.2,41.4,680.89],[1958,3370,2.85,8.1,17.9,37.1,552.5],[1959,3500,2.92,7.33,17.4,33.4,586.48],[1960,3400,2.88,8.43,18.4,37.6,729.01],[1961,3960,3.35,8.97,19.3,37.7,598.49],[1962,5170,4.4,12.9,25.5,48.8,633.66],[1963,4100,3.47,9.3,17.1,34.9,614.74],[1964,4140,3.5,9.76,19.6,38.4,636.9],[1965,5140,4.42,12.8,26.8,54.3,762.94],[1966,4020,3.42,10.3,21.9,44.3,617.31],[1967,2460,2.09,5.87,11.7,23.5,557.09],[1968,3700,3.13,8.76,17.7,33.3,581.25],[1969,3600,3.04,8.47,17.4,35.2,563.81],[1970,5300,4.5,12.3,24.7,46.5,623.73],[1971,2450,2.1,6.11,13.1,26.2,583.25],[1972,3880,3.31,9.31,19.7,36.9,574.67],[1973,2260,1.88,5.1,10.4,21,552.43],[1974,4130,3.49,10.1,20.1,39.5,728.58],[1975,3530,3,8.27,17.7,31.8,636.52],[1976,3990,3.38,9.66,19.9,34.4,647.62],[1977,3470,2.9,7.96,16,29.3,599.62],[1978,3150,2.62,6.73,12.4,27,638.81],[1979,3660,3.11,8.61,17.7,34.4,664.64],[1980,5820,4.91,13.9,26.9,50.1,700.46],[1981,4820,4.06,11.4,19.6,36.3,662.01],[1982,4760,4,11.2,24.4,46.7,664.96],[1983,2450,2.08,5.91,12.2,26.3,605.34],[1984,3910,3.35,9.22,18.3,35.7,581.5],[1985,4020,3.35,9.25,19.4,37.4,720.6],[1986,3000,2.55,7.28,15.3,30.5,595.51],[1987,4520,3.84,10.5,21.9,41.9,694.29],[1988,3020,2.57,7.73,16.6,34.2,636.11],[1989,3620,3.09,9.09,20.8,39.5,733.75],[1990,4370,3.69,10.2,21,36.7,661.22],[1991,3880,3.31,9.34,19.6,38.7,672.75],[1992,3650,2.96,7.97,18.8,35.3,598.94],[1993,5190,4.37,12.3,27.6,56.4,761.99],[1994,3420,2.89,7.7,14.3,24.7,562.1],[1995,4640,3.92,10.8,23,38.2,657.81],[1996,3370,2.83,9.03,19.1,38.2,646.63],[1997,3730,3.17,9.11,18.8,36.6,558.98],[1998,5420,4.6,12.9,26,45.6,747.56],[1999,5710,4.88,14.3,29.3,52.8,694.61],[2000,5580,4.71,13.2,27.2,46.3,708.62],[2001,4280,3.6,10.4,23.1,44.8,649.1],[2002,2630,2.25,6.36,13,24,529.97],[2003,5200,4.41,12.5,28.7,53.7,711.65],[2004,3540,3.01,8.34,17.1,34.5,685.05],[2005,5530,4.65,13.1,28.9,54.7,726.49],[2006,3210,2.74,8.07,17.7,31.6,572.16],[2007,2790,2.4,7.07,15.1,26.6,585.71],[2008,4640,3.84,11.2,23.6,41.9,671.03],[2009,5120,4.35,12,23.3,44.4,701.16],[2010,3430,2.82,7.6,15.7,29.7,612.73],[2011,5560,4.6,11.85,22.89,36.92,644.52],[2012,6170,5.23,15.17,33.26,62.97,769.45]]'";
		String BmaPJson = "'[557.57,707.87,873.35]'";
		getDesignP(BaseFloodJson, BmaPJson);
	}

	// -----------以下为计算一定设计频率下设计洪峰、设计24h洪量、设计3日洪量、设计7日洪量、设计15日洪量的方法---------------------------------------------------------------------------
	public static double[][] getDesignP(String BaseFloodJson, String BmaPJson) {
		double[][] piii = null;
		try {
			RConnection c = new RConnection();
			REXP Rservesion = c.eval("R.version.string");
			// 1.加载R程序包，读取计算数据
			c.eval("library(lmom)");
			c.eval("library(gamlss)");
			c.eval("library(PearsonDS)");
			c.eval("library(rjson)");
			c.eval("library(jsonlite)");
			c.eval("json <-" + BaseFloodJson);
			c.eval("mydf <- fromJSON(json)");// 基准期洪水降水序列
			c.eval("d=data.frame(mydf)");
			c.eval("y=mydf[,2]");// 基准期洪峰序列
			c.eval("P=mydf[,7]");// 基准期年降水
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
			c.eval("json <-" + BmaPJson);
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

			try {
				piii = eval.asDoubleMatrix();
				double[] mean = eval2.asDoubles();
				System.out.println("mean=" + Arrays.toString(mean));
				double[] cv = eval3.asDoubles();
				System.out.println("Cv=" + Arrays.toString(cv));
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}
			System.out.println("设计频率下的设计洪水值Q=" + Arrays.toString(piii));
			
			c.close();
		} catch (RserveException e) {
			e.printStackTrace();
		}
		return piii;
	}
}
