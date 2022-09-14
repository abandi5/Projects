package com.example.demo.restclient;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;

import io.swagger.v3.oas.models.media.MediaType;

public class RestClient {

	private static final String Department_Dummy_Data = "http://localhost:8081/createDummyData";
	private static final String GET_ALL_DeptDetails_API = "http://localhost:8081/getDeptDetails";
	private static final String GET_USER_DeptDetailsBYID_API = "http://localhost:8081/getDepartmentList/{id}?id={id}";
	private static final String CREATE_USER_DeptDetails_API = "http://localhost:8081/saveDeptDetails";
	private static final String UPDATE_USER_DeptDetails_API = "http://localhost:8081/updateDepartmentDetails";
	private static final String DELETE_USER_DeptDetails_API = "http://localhost:8081/deleteDepartmentDetailsByid/{id}?id={id}";
	
	static RestTemplate resttemplate = new RestTemplate();
	public static void main(String[] args)
	{
		//GetAllDepDetailsDummyAPI();
		GetAllDepDetailsAPI();
		GetUserDeptDetailsByidAPI();
		CREATEUSERDeptDetailsAPI();
		UPDATEUSERDeptDetailsAPI();
		DELETEUSERDeptDetailsAPI();
		
	}
	private static void GetAllDepDetailsDummyAPI()
	{
	    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	    headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
	    
	    HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
	    
	    ResponseEntity<String> result =resttemplate.exchange(Department_Dummy_Data, HttpMethod.GET,entity, String.class);
	    System.out.println(result);
	}
	private static void GetAllDepDetailsAPI()
	{
	    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	    headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
	    
	    HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
	    
	    ResponseEntity<String> result =resttemplate.exchange(GET_ALL_DeptDetails_API, HttpMethod.GET,entity, String.class);
	    System.out.println(result);
	}
	
	private static void GetUserDeptDetailsByidAPI()
	{
		Map<String, Integer> param = new HashMap<>();
		param.put("id", 100);
		
		DepartmentDTO user = resttemplate.getForObject(GET_USER_DeptDetailsBYID_API, DepartmentDTO.class, param);
//		System.out.println(user.getId());
//		System.out.println(user.getCourse());
//		System.out.println(user.getName());
		System.out.println(user.getMessage());
		System.out.println(user.getDeptList());
}
	
	private static void  CREATEUSERDeptDetailsAPI()
	{
		Department user = new Department();
		ResponseEntity<Department> response=resttemplate.postForEntity(CREATE_USER_DeptDetails_API, user, Department.class);
		System.out.println(response.getBody());
		
	}
	
	private static void UPDATEUSERDeptDetailsAPI()
	{
		Map<String, Integer> param = new HashMap<>();
		param.put("id", 3);
		Department user = new Department();
		  resttemplate.put(UPDATE_USER_DeptDetails_API, user, param);
	
	}
	private static void DELETEUSERDeptDetailsAPI()
	{
		Map<String, Integer> param= new HashMap<>();
		param.put("id", 101);
		resttemplate.delete(DELETE_USER_DeptDetails_API, param);
	}
}
