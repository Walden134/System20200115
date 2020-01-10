package com.hust.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.xml.soap.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WebServicesTest {
    public static void main(String[] args) throws Exception {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month = cal.get(Calendar.MONTH);// 获取月份


        Map<String, Object> resp = solveWebServices("222.20.80.182", "ProRun",
                "{\"pack\":\"HMCE南欧1江\",\"user\":\"sys1\",\"stcd\":\"RR601\",\"ipro\":19,\"dtbeg\":\"2010-1-1\",\"dtend\":\"2015-1-1\",\"scene\":\"null\"}");
        System.out.println(resp.get("msg"));
        System.out.println(resp.get("status"));
    }

    private static Map<String, Object> solveWebServices(String ip, String method, String reqStr) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        // 第一步：创建服务地址
        URL url = new URL("http://" + ip + "/webService.asmx?wsdl");
        // 第二步：打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 第三步：设置参数
        // 3.1发送方式设置：
        connection.setRequestMethod("POST");
        // 3.2设置数据格式：content-type
        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        // 3.3设置输入输出，因为默认新创建的connection没有读写权限，
        connection.setDoInput(true);
        connection.setDoOutput(true);
        // 第四步：组织SOAP数据，发送请求
        String soapXML = getXML(method, reqStr);
        // 将信息以流的方式发送出去
        OutputStream os = connection.getOutputStream();
        os.write(soapXML.getBytes());
        // 第五步：接收服务端响应，打印
        int responseCode = connection.getResponseCode();

        if (200 == responseCode) {// 表示服务端响应成功
            // 获取当前连接请求返回的数据流
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String temp = null;
            while (null != (temp = br.readLine())) {
                sb.append(temp);
            }
            map = parseSoapMessage(sb.toString());
            is.close();
            isr.close();
            br.close();
        }

        os.close();
        return map;
    }

    public static String getXML(String method, String reqStr) {

        String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "  <soap:Body>\n" + "    <" + method + " xmlns=\"http://tempuri.org/\">\n" + "      <strIn>" + reqStr
                + "</strIn>\n" + "    </" + method + ">\n" + "  </soap:Body>\n" + "</soap:Envelope>";
        return soapXML;
    }

    /**
     * 解析soapXML
     *
     * @param soapXML
     * @return
     * @throws SOAPException
     */
    public static Map<String, Object> parseSoapMessage(String soapXML) throws SOAPException {
        Map<String, Object> map = new HashMap<String, Object>();
        SOAPMessage SOAPMessage = formatSoapString(soapXML);
        SOAPBody body = SOAPMessage.getSOAPBody();
        SOAPElement el = (SOAPElement) body.getFirstChild().getFirstChild();
        JSONObject parseObject = JSON.parseObject(el.getValue());
        if (parseObject.containsKey("status"))
            map.put("status", parseObject.get("status"));
        if (parseObject.containsKey("info"))
            map.put("info", parseObject.get("info"));
        if (parseObject.containsKey("pros"))
            map.put("pros", parseObject.get("pros"));
        if (parseObject.containsKey("px"))
            map.put("px", parseObject.get("px"));
        if (parseObject.containsKey("sse"))
            map.put("sse", parseObject.get("sse"));
        if (parseObject.containsKey("ssp"))
            map.put("ssp", (JSONArray) parseObject.get("ssp"));
        if (parseObject.containsKey("ccq"))
            map.put("ccq", (JSONArray) parseObject.get("ccq"));
        return map;
    }

    /**
     * 把soap字符串格式化为SOAPMessage
     *
     * @param soapString
     * @return
     */
    public static SOAPMessage formatSoapString(String soapString) {
        MessageFactory msgFactory;
        try {
            msgFactory = MessageFactory.newInstance();
            SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
                    new ByteArrayInputStream(soapString.getBytes("UTF-8")));
            reqMsg.saveChanges();
            return reqMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
