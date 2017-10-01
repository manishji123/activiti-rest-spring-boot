/**
 * 
 */
package com.manish.activiti.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Manish Goel
 *
 */
public class Util {

	public static String getBase64AuthString() {
		String plainCreds = "manish:manish";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		return "Basic " + base64Creds;
		
	}
	
	private static int port;
	public static void setPort(int portVariable) {
		port = portVariable;
	}
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	public static TestRestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	
	public static String createURL(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	public static HttpHeaders getHttpHeaders(MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.add("Authorization", getBase64AuthString());
		
		return headers;
	}
	
	public static Map<String, Object> buildProcessVariableMap(String name, Object value, String type){
		Map<String, Object> variableMap= new HashMap();
		variableMap.put("name", name);
		variableMap.put("type", type);
		variableMap.put("value", value);
		
		return variableMap;
	}
}
