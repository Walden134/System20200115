package org.hust.bean.flood;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CalculateFloodRisk {

    //计算未来不同GCM和BMA下的防洪风险率
    public static Map<String, Object> getFutureFloodRisk(List<double[]> obj, double ex, double cv, List fai) {
        Map<String, Object> map = new HashMap<>();
        List<Double> floodObs = new ArrayList<>();

        double[] obs = obj.get(0);
        double[] cnrm = obj.get(1);
        double[] miroc = obj.get(2);
        double[] canesm = obj.get(3);
        double[] gfdl = obj.get(4);
        double[] qm = getTheoryFrequency(ex, cv, fai);

        for (int i = 0; i < obs.length; i++) {
            floodObs.add(obs[i]);
        }


        double[] futureFloodRiskObs = getFloodRisk(floodObs, qm);
        List<Double> yearMaxFloodCnrm = getYearMaxFlood(cnrm);
        double[] futureFloodRiskCnrm = getFloodRisk(yearMaxFloodCnrm, qm);
        List<Double> yearMaxFloodMiroc = getYearMaxFlood(miroc);
        double[] futureFloodRiskMiroc = getFloodRisk(yearMaxFloodMiroc, qm);
        List<Double> yearMaxFloodCanesm = getYearMaxFlood(canesm);
        double[] futureFloodRiskCanesm = getFloodRisk(yearMaxFloodCanesm, qm);
        List<Double> yearMaxFloodGfdl = getYearMaxFlood(gfdl);
        double[] futureFloodRiskGfdl = getFloodRisk(yearMaxFloodGfdl, qm);

        map.put("design", qm);
        map.put("obs", futureFloodRiskObs);
        map.put("cnrm", futureFloodRiskCnrm);
        map.put("miroc", futureFloodRiskMiroc);
        map.put("canesm", futureFloodRiskCanesm);
        map.put("gfdl", futureFloodRiskGfdl);
        return map;
    }

    //计算理论频率下的设计洪水值
    public static double[] getTheoryFrequency(double ex, double cv, List<Double> fai) {
        double[] q = new double[fai.size()];
        for (int i = 0; i < fai.size(); i++) {
            q[i] = ex * (1 + cv * fai.get(i));
        }
        return q;
    }

    //计算年序列中最大值超过设计值的频率的方法
    public static double[] getFloodRisk(List<Double> maxFlood, double[] pQm) {
        int count = 0;
        double[] floodRisk = new double[pQm.length];
        for (int i = 0; i < pQm.length; i++) {  //25
            for (int j = 0; j < maxFlood.size(); j++) {  //77
                if (maxFlood.get(j) > pQm[i]) {
                    count += 1;
                }
            }
            floodRisk[i] = count * 1.00 / maxFlood.size();
            count = 0;
        }
        return floodRisk;
    }

    //计算年序列中最大值的方法
    public static List<Double> getYearMaxFlood(double[] simp) {
        List<Double> oneYear = new ArrayList<>();
        List<Double> yearMaxFlood = new ArrayList<>();
        for (int i = 0; i < 77; i++) {
            for (int j = i * 365; j < (i + 1) * 365; j++) {
                oneYear.add(simp[j]);
            }
            yearMaxFlood.add(Collections.max(oneYear));
            oneYear.clear();
        }
        return yearMaxFlood;
    }

    // 读取输入txt文件的方法
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
