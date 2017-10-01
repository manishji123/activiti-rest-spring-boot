/**
 * 
 */
package com.manish.activiti.util;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Manish Goel
 *
 */
public class CreateProcess {
	
	public String create(String processDefinitionId, List<Map<String, Object>> variables) throws Exception {
		String id= "";
		JSONObject request = new JSONObject();
		request.put("processDefinitionId", processDefinitionId);
		request.put("variables", new JSONArray(variables));
		
		ResponseEntity<String> response =
				Util.getRestTemplate().exchange(Util.createURL("/runtime/process-instances"), HttpMethod.POST, 
						new HttpEntity<String>(request.toString(), Util.getHttpHeaders(MediaType.APPLICATION_JSON))
						, String.class);
		
		if (response != null) {
			System.out.println("CreateProcess - status : "+response.getStatusCode());
			System.out.println(response.getBody());
			id= (new JSONObject(response.getBody())).getString("id");
		}
		
		return id;
	}
	
}
