/**
 * 
 */
package com.manish.activiti.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Manish Goel
 *
 */
public class DeployFile {
	
	private TestRestTemplate restTemplate;
	private int port;
	
	
	public DeployFile(TestRestTemplate restTemplate, int port) {
		this.restTemplate= restTemplate;
		this.port= port;
	}
	
	
	public String deploy(String filePath) throws Exception{
		String status="";

		//File parts
		MultiValueMap<String, Object> parts= new LinkedMultiValueMap();
		parts.add("file", new FileSystemResource(getClass().getClassLoader().getResource(filePath).getPath()));
		
		//HttpEntity
		HttpEntity<MultiValueMap<String, Object>> requestEntity =
				new HttpEntity<MultiValueMap<String, Object>>(parts, Util.getHttpHeaders(MediaType.MULTIPART_FORM_DATA));
		
		//Rest call
		ResponseEntity<String> response = restTemplate.exchange(Util.createURLWithPort(port, "/repository/deployments"), 
						HttpMethod.POST, requestEntity, String.class);
		
		if (response != null) {
			status= response.getStatusCode().toString();
			System.out.println("deploy - status : "+status);
			System.out.println(response.getBody());
		}
		
		return status;
	}
}
