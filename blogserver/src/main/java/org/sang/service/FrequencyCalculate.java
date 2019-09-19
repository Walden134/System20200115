package org.sang.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FrequencyCalculate {
	public static double[] readXLSXFile1() {
		InputStream ExcelFileToRead = null;
		XSSFWorkbook wb = null;
		try {
			ExcelFileToRead = new FileInputStream(
					"D:/Workspace_java/huadongyuan/huadongyuan/blogserver/src/main/java/org/sang/bean/hydro/test.xlsx");
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

	public static double[] readXLSXFile(double cs) {
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

	public static double[] sortNumber(double[] test) {
		double temp;
		for (int i = 0; i <= test.length - 1; i++) {
			for (int j = 0; j <= test.length - 2; j++) {
				if (test[j] < test[j + 1]) {
					temp = test[j];
					test[j] = test[j + 1];
					test[j + 1] = temp;

				}
			}
		}
		return test;
	}

	public static double[] getEmpiricalFrequency(double[] test) {
		double[] p = new double[test.length]; // 经验频率
		for (int i = 0; i <= test.length - 1; i++) {
			p[i] = (i + 1) * 1.0 / (test.length + 1) * 100;
		}
		return p;
	}

	public static double getAvg(double[] test) {
		double sum = 0.0;
		double avg = 0.0;
		for (int i = 0; i <= test.length - 1; i++) {
			sum = sum + test[i];
			avg = sum / test.length; // 计算均值
		}
		return avg;
	}

	public static double getCV(double[] test, double avg) {
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

	public static double getCS(double[] test, double Cv, double avg) {
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

	public static double[] getTheoreticFrequency(double cs, double avg, double cv, double[] Qw) {
		double b3[] = new double[Qw.length];
		for (int i1 = 1; i1 < Qw.length; i1++) {
			b3[i1] = (Qw[i1] * cv + 1.0) * avg;
		}
		return b3;
	}

	public static void main(String[] args) throws IOException {

		double sum = 0.0, sum1 = 0.0, sum2 = 0.0;
		double temp;
		double avg = 0.0;
		double Cv = 0.0;
		double Cs;
		int i, j;
		double[] test = readXLSXFile1();
		double[] p = new double[test.length]; // 经验频率
		double[] K = new double[test.length]; // 模比系数

//		for(i=0;i<test.length;i++) {
//			System.out.print("  "+test[i]+" ");
//		}
//		System.out.println();

//  1.前端获取的数据进行排序
		for (i = 0; i <= test.length - 1; i++) {
			for (j = 0; j <= test.length - 2; j++) {
				if (test[j] < test[j + 1]) {
					temp = test[j];
					test[j] = test[j + 1];
					test[j + 1] = temp;

				}
			}
		}

//  2.对已排序的数据进行经验频率计算	
		for (i = 0; i <= test.length - 1; i++) {
			p[i] = (i + 1) * 1.0 / (test.length + 1) * 100;
		}
//	   
//		for(i=0;i<test.length-1;i++) {
//		System.out.print(" "+p[i]+" ");
//	}	

//	 3.按无偏估值公式计算统计参数
		for (i = 0; i <= test.length - 1; i++) {
			sum = sum + test[i];
			avg = sum / test.length; // 计算均值

		}
		for (i = 0; i <= test.length - 1; i++) {
			K[i] = test[i] / avg;
			sum1 = sum1 + (K[i] - 1) * (K[i] - 1);
			sum2 = sum2 + (K[i] - 1) * (K[i] - 1) * (K[i] - 1);
		}

		Cv = Math.sqrt(sum1 / (test.length - 1));
		Cs = sum2 / ((test.length - 3) * Math.pow(Cv, 3));

//		System.out.println();
//		System.out.println(avg);
//		System.out.println(Cv);
//		System.out.println(Cs);

//   4.选配皮尔逊频率曲线 
		double b3[] = new double[16];
		BigDecimal bg = new BigDecimal(Cs);
		double Cs1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(Cs1);
		double[] b2 = readXLSXFile(Cs1);
		for (int i1 = 1; i1 < b2.length; i1++) {
			b3[i1] = (b2[i1] * Cv + 1.0) * avg;
		}
//         for (int i1 = 1; i1 < b2.length; i1++) {			
//			System.out.print(" " + b2[i1] + " ");}	
//         
// 			System.out.println();
		for (int i1 = 1; i1 < b3.length; i1++) {
			System.out.print(" " + b3[i1] + " ");
		}
	}
}