package org.sang.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.math3.special.Gamma;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FrequencyCalculate {

	public double[] readFloodData() {
		InputStream ExcelFileToRead = null;
		XSSFWorkbook wb = null;
		try {
			ExcelFileToRead = new FileInputStream("D:/tes_flood.xlsx");

			wb = new XSSFWorkbook(ExcelFileToRead);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		// 总行数
		int rowLength = sheet.getPhysicalNumberOfRows();

		// 工作表的列
		row = sheet.getRow(0);
		// 总列数
		int colLength = row.getLastCellNum();
//		
//		System.out.println(rowLength);
//		System.out.println(colLength);
		double[][] objects = new double[rowLength][colLength];
		double[] b1 = new double[rowLength];
		for (int i = 0; i < rowLength; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				break;
			}
//			System.out.println(" ");
			for (int j = 0; j < colLength; j++) {
				XSSFCell cellValue = row.getCell(j);
				int cellType = cellValue.getCellType();
				if (Cell.CELL_TYPE_NUMERIC == cellType) {
					double value = cellValue.getNumericCellValue();

				}
				if (Cell.CELL_TYPE_STRING == cellType) {
					String value = cellValue.getStringCellValue();
				}
				objects[i][j] = cellValue.getNumericCellValue();

//			System.out.print(" " + objects[i][j] + " ");
			}

//			System.out.print(objects.length);
		}
//		System.out.print(" " + objects[2][0] + " ");
//		System.out.print(rowLength);

		for (int j = 0; j < objects.length; j++) {
			b1[j] = objects[j][0];
		}
//				for (int j = 0; j < objects.length; j++) {
//					System.out.print(" " + b1[j] + " ");
//				}
//	        	 else {
//	        		 System.out.print("不存在值"); }
		return b1;

	}

	public double[] readXLSXFile(double cs) {
		InputStream ExcelFileToRead = null;
		XSSFWorkbook wb = null;
		try {
			ExcelFileToRead = new FileInputStream(
					"D:/Workspace_java/huadongyuan/huadongyuan/blogserver/src/main/java/org/sang/bean/hydro/Cs.xlsx");
			wb = new XSSFWorkbook(ExcelFileToRead);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		// 总行数
		int rowLength = sheet.getLastRowNum();
		// 工作表的列
		row = sheet.getRow(0);
		// 总列数
		int colLength = row.getLastCellNum();
		double[][] objects = new double[rowLength][colLength];
		double[] b1 = new double[colLength];

//		System.out.println(rowLength);
//		System.out.println(colLength);

		for (int i = 0; i < rowLength; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				break;
			}
//			System.out.println(" ");
			for (int j = 0; j < colLength; j++) {
				XSSFCell cellValue = row.getCell(j);
				int cellType = cellValue.getCellType();
				if (Cell.CELL_TYPE_NUMERIC == cellType) {
					double value = cellValue.getNumericCellValue();

				}
				if (Cell.CELL_TYPE_STRING == cellType) {
					String value = cellValue.getStringCellValue();
				}
				objects[i][j] = cellValue.getNumericCellValue();

//			System.out.print(" " + objects[i][j] + " ");
			}

//			System.out.print(objects.length);
		}
//		System.out.print(" " + objects[10][6] + " ");

		for (int i = 0; i < rowLength; i++) {
			if (objects[i][0] == cs) {
				for (int j = 0; j < colLength; j++) {
					b1[j] = objects[i][j];
				}
			} else if (objects[i][0] < cs && objects[i + 1][0] > cs) {
				for (int j = 0; j < colLength; j++) {
					b1[j] = (objects[i + 1][j] - objects[i][j]) / (objects[i + 1][0] - objects[i][0])
							* (cs - objects[i][0]) + objects[i][j];
				}
			}
//	        else {
//	        System.out.print("不存在值"); }
//		  }

		}
		return b1;
	}

	public double[] sortNumber(double[] folw_data) {
		double temp;
		for (int i = 0; i <= folw_data.length - 1; i++) {
			for (int j = 0; j <= folw_data.length - 2; j++) {
				if (folw_data[j] < folw_data[j + 1]) {
					temp = folw_data[j];
					folw_data[j] = folw_data[j + 1];
					folw_data[j + 1] = temp;

				}
			}
		}
		return folw_data;
	}

	public double[] frequencyCal(double[] test, double N, int a, int l) {

		double[] p = new double[test.length]; // 经验频率
		int n = test.length - a;

		for (int i = 1; i <= a; i++) {
			p[i - 1] = i / (N + 1);
		}

		for (int i = 1; i <= test.length - a; i++) {
			p[i + a - 1] = p[a - 1] + (1 - p[a - 1]) * i / (n - l + 1);
		}
		return p;
	}

	public double[][] frequencyCal_output(double[] test, double N, int a, int l) {

		double[] p = new double[test.length]; // 经验频率
		double[][] p1 = new double[2][test.length];
		int n = test.length - a;

		for (int i = 1; i <= a; i++) {
			p[i - 1] = i / (N + 1);
		}

		for (int i = 1; i <= test.length - a; i++) {
			p[i + a - 1] = p[a - 1] + (1 - p[a - 1]) * i / (n - l + 1);
		}

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < test.length; j++) {
				p1[i][j] = p[j];
			}
		}
		for (int i = 1; i < 2; i++) {
			for (int j = 0; j < test.length; j++) {
				p1[i][j] = test[j];
			}
		}
		return p1;
	}

	public double parameterCal_avg(double[] test, double N, int a, int l) {

		double sum1 = 0.0, sum2 = 0.0;
		double avg = 0.0;
		int n = test.length - a;

		for (int i = 0; i < a; i++) {
			sum1 = sum1 + test[i];
		}
		for (int i = a; i < test.length; i++) {
			sum2 = sum2 + test[i];
		}

		avg = (sum1 + sum2 * (N - a) / (n - l)) / N; // 计算均值
		BigDecimal bg = new BigDecimal(avg);
		double avg1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return avg1;
	}

	public double parameterCal_Cv(double[] test, double N, int a, int l) {

		double sum1 = 0.0, sum2 = 0.0;
		double sum3 = 0.0, sum4 = 0.0, sum5 = 0.0;
		double avg = 0.0;
		int n = test.length - a;
		double Cv = 0.0;

		for (int i = 0; i < a; i++) {
			sum1 = sum1 + test[i];
		}
		for (int i = a; i < test.length; i++) {
			sum2 = sum2 + test[i];
		}
		avg = (sum1 + sum2 * (N - a) / (n - l)) / N; // 计算均值

		for (int i = 0; i < a; i++) {
			sum3 = sum3 + Math.pow(test[i] - avg, 2);
		}
		for (int i = a; i < test.length; i++) {
			sum4 = sum4 + Math.pow(test[i] - avg, 2);
		}
		sum5 = (sum3 + sum4 * (N - a) / (n - l)) / (N - 1);

		Cv = Math.sqrt(sum5) / avg;
		BigDecimal bg = new BigDecimal(Cv);
		double Cv1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		return Cv1;
	}

	public double parameterCal_Cs(double Cv, double n) {

		return n * Cv;

	}

	public double parameterCal_a(double Cs) {
		return 4.0 / Math.pow(Cs, 2);
	}

	public double parameterCal_b(double Cs, double Cv, double avg) {
		return 2.0 / (avg * Cv * Cs);
	}

	public double parameterCal_a0(double Cs, double Cv, double avg) {
		return avg * (1 - 2.0 * Cv / Cs);
	}

	public double[][] frequencycurveCal(double[] test, double a, double b, double a0) {

		double[] sum = new double[test.length];
		double[][] p = new double[2][test.length];
		double[] q = new double[100000];
		double sum1 = 0.0;

		// 求和，循环从第一个区间叠加到第100000个
		for (int i = 0; i < test.length; i++) {

			if (test[i] > a0) {
				for (int j = 0; j < 100000; j++) {
					double x = j / 100000.0;
					q[j] = Math.pow(b, a) * Math.pow(test[i] + (x / (1 - x)) - a0, a - 1)
							* Math.pow(Math.E, -b * (test[i] + x / (1 - x) - a0)) / Gamma.gamma(a) / Math.pow(1 - x, 2);
					sum1 = sum1 + q[j];
				}
				sum[i] = sum1 / 100000.0;
				sum1 = 0.0;
			} else {
				sum[i] = 1;
			}
		}

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < test.length; j++) {
				p[i][j] = sum[j];
			}
		}
		for (int i = 1; i < 2; i++) {
			for (int j = 0; j < test.length; j++) {
				p[i][j] = test[j];
			}
		}
		return p;
	}

	public double parameterCal_fit(double[] test, double[] fre_p, double a, double b, double a0) {

		double[] sum = new double[test.length];
		double[][] p = new double[2][test.length];
		double[] q = new double[100000];
		double[] p1 = new double[test.length];
		double sum1 = 0.0;

		// 求和，循环从第一个区间叠加到第100000个
		for (int i = 0; i < test.length; i++) {

			if (test[i] > a0) {
				for (int j = 0; j < 100000; j++) {
					double x = j / 100000.0;
					q[j] = Math.pow(b, a) * Math.pow(test[i] + (x / (1 - x)) - a0, a - 1)
							* Math.pow(Math.E, -b * (test[i] + x / (1 - x) - a0)) / Gamma.gamma(a) / Math.pow(1 - x, 2);
					sum1 = sum1 + q[j];
				}
				sum[i] = sum1 / 100000.0;
				sum1 = 0.0;
			} else {
				sum[i] = 1;
			}
		}

		for (int i = 0; i < test.length; i++) {
			if (fre_p[i] != 1) {
				sum1 = sum1 + Math.pow(sum[i] - fre_p[i], 2);
			}
		}
		double fit = 1 - Math.sqrt(sum1 / test.length);

		return fit;
	}

}