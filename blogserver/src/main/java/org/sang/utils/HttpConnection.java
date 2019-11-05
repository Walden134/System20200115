package org.sang.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
	static final String IP = "127.0.0.1:8090";

	public static HttpURLConnection conectWebServices() throws Exception {
		URL url = null;
		HttpURLConnection connection=null;
		url = new URL("http://" + IP + "/webService.asmx?wsdl");
		if (connection == null) {
			connection = (HttpURLConnection) url.openConnection();
		}
		connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

}
