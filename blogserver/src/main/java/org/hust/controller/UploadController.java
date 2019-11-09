package org.hust.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@RestController
@RequestMapping("/upload")
public class UploadController {

	@ResponseBody
	@RequestMapping(value = "/paramescheme", method = RequestMethod.POST)
	public Map<String, Object> upload(HttpServletRequest request) throws Exception {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");
			if (file.isEmpty()) {
				System.out.println("文件不存在!");
			}
			InputStreamReader read = new InputStreamReader(file.getInputStream());
			BufferedReader buf = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = buf.readLine()) != null) {
				System.out.println(lineTxt);
			}
			read.close();
		}
		return null;
	}
}