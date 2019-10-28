package org.sang.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.sang.utils.HttpConnection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

@Service
@Transactional
public class RunoffService {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<String> getXAxisByBeg(String dtbeg, String dtend) {
		Date beg = null;
		Date end = null;
		try {
			beg = sdf.parse(dtbeg);
			end = sdf.parse(dtend);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> res = new ArrayList<String>();
		int daysDiff = daysBetween(beg, end);
		Calendar c = Calendar.getInstance();
		for (int i = 0; i <= daysDiff; i++) {
			res.add(sdf.format(beg));
			c.setTime(beg);
			c.add(Calendar.DAY_OF_MONTH, 1);
			beg = c.getTime();
//			String format = sdf.format(beg);
		}
		return res;
	}

	public Map<String, Object> runSwat(JsonObject dataProRun) throws Exception {
		Map<String, Object> res = proRun(dataProRun.toString(), "POST");

		Date beg = sdf.parse(dataProRun.get("dtbeg").toString().replace("\"", ""));
		Date end = sdf.parse(dataProRun.get("dtend").toString().replace("\"", ""));
		List<String> xAxis = new ArrayList<String>();

		int daysDiff = daysBetween(beg, end);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(beg);
		int beg_year = calBegin.get(Calendar.YEAR);// 获取年份
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);
		int end_year = calEnd.get(Calendar.YEAR);// 获取年份
		int num_year = end_year - beg_year + 1;
		double[] sumMon = new double[12];
		double[] sumYear = new double[num_year];
		int[] sizeMon = new int[12];
		int[] sizeYear = new int[num_year];

//		JSONArray ssp = (JSONArray) res.get("ssp");
		JSONArray ccq = (JSONArray) res.get("ccq");
		int year = 0, month = 0;
		double q = 0;
		for (int i = 0; i <= daysDiff; i++) {
			xAxis.add(sdf.format(beg));
			year = calBegin.get(Calendar.YEAR);
			month = calBegin.get(Calendar.MONTH);
			q = ccq.getDoubleValue(i);
			sumMon[month] += q;
			sizeMon[month] += 1;
			sumYear[year - beg_year] += q;
			sizeYear[year - beg_year] += 1;
			// 日期自增一天
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			beg = calBegin.getTime();
		}
		double[] avgMon = new double[12];
		double[] avgYear = new double[end_year - beg_year + 1];
		int[] xYear = new int[num_year];
		for (int i = 0; i < sumYear.length; i++) {
			avgYear[i] = sumYear[i] / sizeYear[i];
			xYear[i] = beg_year + i;
		}
		for (int i = 0; i < sumMon.length; i++) {
			avgMon[i] = sumMon[i] / sizeMon[i];
		}

		res.put("avgYear", avgYear);
		res.put("avgMon", avgMon);
		res.put("xYear", xYear);
		res.put("xAxis", xAxis);
		return res;
	}

	public Map<String, Object> proRun(String reqStr, String reqType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject respJsonObject = sendSOAPXML("ProRun", reqStr, reqType);
		if (null != respJsonObject) {
			if (respJsonObject.containsKey("status"))
				map.put("status", respJsonObject.get("status"));
			if (respJsonObject.containsKey("info"))
				map.put("info", respJsonObject.get("info"));
			if (respJsonObject.containsKey("pros"))
				map.put("pros", respJsonObject.get("pros"));
			if (respJsonObject.containsKey("px"))
				map.put("px", respJsonObject.get("px"));
			if (respJsonObject.containsKey("sse"))
				map.put("sse", respJsonObject.get("sse"));
			if (respJsonObject.containsKey("ssp"))
				map.put("ssp", (JSONArray) respJsonObject.get("ssp"));
			if (respJsonObject.containsKey("ccq"))
				map.put("ccq", (JSONArray) respJsonObject.get("ccq"));
		} else {
			map.put("error", "error");
		}
		return map;
	}

	public Map<String, Object> getListProjects(String reqStr, String reqType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject respJsonObject = sendSOAPXML("ListProjects", reqStr, reqType);
		if (null != respJsonObject) {
			if (respJsonObject.containsKey("status"))
				map.put("status", respJsonObject.get("status"));
			if (respJsonObject.containsKey("info"))
				map.put("info", respJsonObject.get("info"));
			if (respJsonObject.containsKey("pros"))
				map.put("pros", (JSONArray) respJsonObject.get("pros"));// ...
			if (respJsonObject.containsKey("px"))
				map.put("px", respJsonObject.get("px"));
			if (respJsonObject.containsKey("sse"))
				map.put("sse", respJsonObject.get("sse"));
			if (respJsonObject.containsKey("ssp"))
				map.put("ssp", (JSONArray) respJsonObject.get("ssp"));
			if (respJsonObject.containsKey("ccq"))
				map.put("ccq", (JSONArray) respJsonObject.get("ccq"));
		} else {
			map.put("error", "error");
		}
		return map;

	}

	public Map<String, Object> getProDetails(String reqStr, String reqType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject respJsonObject = sendSOAPXML("ProDetails", reqStr, reqType);
		if (null != respJsonObject) {
			if (respJsonObject.containsKey("status"))
				map.put("status", respJsonObject.get("status"));
			if (respJsonObject.containsKey("info"))
				map.put("info", respJsonObject.get("info"));
			if (respJsonObject.containsKey("px"))// ....
				map.put("px", respJsonObject.get("px"));
			if (respJsonObject.containsKey("sse"))
				map.put("sse", respJsonObject.get("sse"));
			if (respJsonObject.containsKey("ssp"))
				map.put("ssp", (JSONArray) respJsonObject.get("ssp"));
			if (respJsonObject.containsKey("ccq"))
				map.put("ccq", (JSONArray) respJsonObject.get("ccq"));
		} else {
			map.put("error", "error");
		}
		return map;

	}

	public JSONObject sendSOAPXML(String reqMed, String reqStr, String reqType) throws Exception {
		HttpURLConnection connection = HttpConnection.conectWebServices();
		connection.setRequestMethod(reqType);
		String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "  <soap:Body>\n" + "    <" + reqMed + " xmlns=\"http://tempuri.org/\">\n" + "      <strIn>" + reqStr
				+ "</strIn>\n" + "    </" + reqMed + ">\n" + "  </soap:Body>\n" + "</soap:Envelope>";
		OutputStream os = connection.getOutputStream();
		os.write(soapXML.getBytes());
		JSONObject respJsonObject = null;
		int responseCode = connection.getResponseCode();
		if (200 == responseCode) {
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			StringBuilder soapString = new StringBuilder();
			String temp = null;
			while (null != (temp = br.readLine())) {
				soapString.append(temp);
			}
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(soapString.toString().getBytes("UTF-8")));
			reqMsg.saveChanges();
			SOAPBody body = reqMsg.getSOAPBody();
			SOAPElement el = (SOAPElement) body.getFirstChild().getFirstChild();
			respJsonObject = JSON.parseObject(el.getValue());
			is.close();
			isr.close();
			br.close();
		}
		os.close();
		return respJsonObject;
	}

	public static int daysBetween(Date early, Date late) {
		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}
}
