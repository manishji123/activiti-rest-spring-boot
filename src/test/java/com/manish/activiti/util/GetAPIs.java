/**
 * 
 */
package com.manish.activiti.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author mgoel020
 *
 */
public class GetAPIs {
	
	private TestRestTemplate restTemplate;
	private int port;
	
	
	public GetAPIs(TestRestTemplate restTemplate, int port) {
		this.restTemplate= restTemplate;
		this.port= port;
	}
	
	public String getProcessDefinitionId(String key) throws Exception{
		String processDefinitionId="";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Util.createURLWithPort(port, "/repository/process-definitions"))
		        .queryParam("latest", "true")		        
		        .queryParam("key", key);
				
		ResponseEntity<String> response =
				restTemplate.exchange(builder.toUriString(), 
						HttpMethod.GET, new HttpEntity(Util.getHttpHeaders(MediaType.APPLICATION_JSON)), String.class);
		
		if (response != null) {
			System.out.println("getProcessDefinitionId - status : "+response.getStatusCode());		    
			processDefinitionId= (new JSONObject(response.getBody())).getJSONArray("data").getJSONObject(0).getString("id");
		}
		
		return processDefinitionId;
	}
	
	public JSONObject getProcessTasks(String id) throws Exception {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Util.createURLWithPort(port, "/runtime/tasks"))
		        .queryParam("processInstanceId", id);	        
				
		ResponseEntity<String> response =
				restTemplate.exchange(builder.toUriString(), 
						HttpMethod.GET, new HttpEntity(Util.getHttpHeaders(MediaType.APPLICATION_JSON)), String.class);
		
		if (response != null) {
			System.out.println("getProcessTasks - status : "+response.getStatusCode());
			return new JSONObject(response.getBody());
		} else {
			return null;
		}
	} 
	
	
	public JSONArray getProcessInstanceVariables(String processInstanceId) throws Exception {
		ResponseEntity<String> response =
				restTemplate.exchange(Util.createURLWithPort(port, "/runtime/process-instances/"+processInstanceId+"/variables"), 
						HttpMethod.GET,  new HttpEntity(Util.getHttpHeaders(MediaType.APPLICATION_JSON)), String.class);
		
		if (response != null) {
			System.out.println("getProcessInstanceVariables - status : "+response.getStatusCode());
			return new JSONArray(response.getBody());
		} else {
			return null;
		}
		
		//[{"name":"managerAccept","type":"string","value":"true","scope":"local"},{"name":"fileName","type":"string","value":"manish","scope":"local"},{"name":"uploadDate","type":"string","value":"manish","scope":"local"},{"name":"createdBy","type":"string","value":"manish","scope":"local"},{"name":"duplicateHash","type":"string","value":"","scope":"local"},{"name":"goodocr","type":"string","value":"true","scope":"local"},{"name":"processUrl","type":"string","value":"/app/viewdocument/read","scope":"local"},{"name":"duplicate","type":"string","value":"false","scope":"local"},{"name":"processType","type":"string","value":"Document","scope":"local"},{"name":"hash","type":"string","value":"","scope":"local"}]

		
		
	} 
	
	
	public void getProcessInstance(String id) throws Exception {
		ResponseEntity<String> response =
				restTemplate.exchange(Util.createURLWithPort(port, "/runtime/process-instances/"+id), 
						HttpMethod.GET, new HttpEntity(Util.getHttpHeaders(MediaType.APPLICATION_JSON)), String.class);
		
		if (response != null) {
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
		}
	} 

}
