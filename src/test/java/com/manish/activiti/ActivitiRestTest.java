/**
 * 
 */
package com.manish.activiti;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * @author Manish Goel
 *
 */
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.manish.activiti.util.CreateProcess;
import com.manish.activiti.util.DeployFile;
import com.manish.activiti.util.GetAPIs;
import com.manish.activiti.util.Util;


@RunWith(SpringRunner.class)
@SpringBootTest( classes= {InitializeBeansForTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiRestTest {
	
	
	@LocalServerPort
	private int port;
	private TestRestTemplate restTemplate = new TestRestTemplate();
	

	@Test
	public void test() {
		System.out.println("###########################################");
		try {
			GetAPIs getAPIs= new GetAPIs(restTemplate, port);
			
			//Deploy file
			String status= new DeployFile(restTemplate, port)
						.deploy("processes/Developer_Hiring.bpmn20.xml");
			assertEquals("Error in deploying bpmn xml.", "201", status);

			System.out.println("------------------------------------------------------------");

			//Get processDefinitionId
			String processDefinitionId= getAPIs.getProcessDefinitionId("hireProcess");
			System.out.println("processDefinitionId : "+processDefinitionId);
			assertTrue(StringUtils.isNotEmpty(processDefinitionId));
			
			System.out.println("------------------------------------------------------------");
			
			//Create Process
			String processId= new CreateProcess(restTemplate, port).create(processDefinitionId, 
					new ArrayList<Map<String, Object>>(){{
							add(Util.buildProcessVariableMap("fileName", "manish", "string"));
							add(Util.buildProcessVariableMap("uploadDate", "manish", "string"));
							add(Util.buildProcessVariableMap("createdBy", "manish", "string"));
						}}
					);
			System.out.println("processInstanceId : "+processId);
			assertTrue(StringUtils.isNotEmpty(processId));
			
			System.out.println("------------------------------------------------------------");
			
			//Get ProcessInstanceVariables
			JSONArray jsonArray= getAPIs.getProcessInstanceVariables(processId);
			System.out.println("ProcessInstanceVariables : "+jsonArray);
			assertNotNull(jsonArray);
			
			System.out.println("------------------------------------------------------------");
			
			//Get ProcessTasks
			JSONObject jsonObject= getAPIs.getProcessTasks(processId);
			System.out.println("ProcessTasks : "+jsonObject.getJSONArray("data"));
			assertNotNull(jsonObject);
			System.out.println("------------------------------------------------------------");

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("###########################################");
	}
	

}