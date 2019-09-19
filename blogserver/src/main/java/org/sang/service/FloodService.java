package org.sang.service;

import java.math.BigDecimal;

import org.sang.mapper.FloodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FloodService {
	@Autowired
	FloodMapper floodMapper;

	public double[] getEmpiricalFrequency(double[] test) {
		double[] p = new double[test.length]; // 经验频率
		for (int i = 0; i <= test.length - 1; i++) {
			p[i] = (i + 1) * 1.0 / (test.length + 1) * 100;
		}
		return p;
	}

	public double getAvg(double[] test) {
		double sum = 0.0;
		double avg = 0.0;
		for (int i = 0; i <= test.length - 1; i++) {
			sum = sum + test[i];
			avg = sum / test.length; // 计算均值
		}
		return avg;
	}

	public double getCV(double[] test, double avg) {
		double sum1 = 0.0, sum2 = 0.0;
		double Cv = 0.0;
		double[] K = new double[test.length]; // 模比系数
		for (int i = 0; i <= test.length - 1; i++) {
			K[i] = test[i] / avg;
			sum1 = sum1 + (K[i] - 1) * (K[i] - 1);
			sum2 = sum2 + (K[i] - 1) * (K[i] - 1) * (K[i] - 1);
		}

		Cv = Math.sqrt(sum1 / (test.length - 1));
		return Cv;
	}

	public double getCS(double[] test, double Cv, double avg) {
		double sum2 = 0.0;
		double Cs;
		double[] K = new double[test.length]; // 模比系数
		for (int i = 0; i <= test.length - 1; i++) {
			K[i] = test[i] / avg;
			sum2 = sum2 + (K[i] - 1) * (K[i] - 1) * (K[i] - 1);
		}
		Cs = sum2 / ((test.length - 3) * Math.pow(Cv, 3));
		BigDecimal bg = new BigDecimal(Cs);
		double Cs1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return Cs1;
	}

	public double[] getTheoreticFrequency(double cs, double avg, double cv, double[] Qw) {
		double b3[] = new double[Qw.length];
		for (int i1 = 1; i1 < Qw.length; i1++) {
			b3[i1] = (Qw[i1] * cv + 1.0) * avg;
		}
		return b3;
	}

}
