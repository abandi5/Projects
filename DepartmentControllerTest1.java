package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;
import com.example.demo.exception.ErrorModel;
import com.example.demo.repository.RepositoryDepartment;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest1 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RepositoryDepartment departmentRepository;
	
	@Test
	public void testsaveAllDeptDetails_BadRequestInvalidCourse() throws Exception {
		Department department = getDepartmentDetails();
		department.setId(56);
		department.setCourse("");
		
		 this.mockMvc.perform(post("/saveAllDeptDetails")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(Arrays.asList(department))))
		        .andExpect(status().isBadRequest());
		        
		
		
	}
	
	@Test
	public void testsaveAllDeptDetails_BadRequestInvalidName() throws Exception {
		Department department = getDepartmentDetails();
		department.setId(56);
		department.setName(null);
		
		String contentAsString = this.mockMvc.perform(post("/saveAllDeptDetails")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(Arrays.asList(department))))
		        .andExpect(status().isBadRequest())
		        .andReturn().getResponse().getContentAsString();		
		
		assertThat(contentAsString)
		.contains(" name is mandatory.");
	
		
	}
	
	@Test
	public void testsaveAllDeptDetails_Success() throws Exception {
		Department department = getDepartmentDetails();
		
		department.setId(1234);
		Mockito.when(departmentRepository.findById(1234)).thenReturn(Optional.of(department));
		
		when(departmentRepository.saveAll(Mockito.<Department>anyList()))
        .thenAnswer(i -> i.getArguments()[0]);
		
		String contentAsString = this.mockMvc.perform(post("/saveAllDeptDetails")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(Arrays.asList(department))))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();		
		
		assertThat(contentAsString)
		.contains("{\"status\":\"OK\",\"message\":\"Added Departmenst : [] \\n Already Existed Departments : [1234]\",\"deptList\":[]}");
		
	} 
	
	@Test
	public void testsaveAllDeptDetails_IdNotFound() throws Exception {
		Department department = getDepartmentDetails();
		

		when(departmentRepository.save(Mockito.any(Department.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		
		String contentAsString = this.mockMvc.perform(post("/saveAllDeptDetails")
				.contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(Arrays.asList(department))))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();		
		
		assertThat(contentAsString)
		.contains("{\"status\":\"OK\",\"message\":\"Added Departmenst : [0] \\n Already Existed Departments : []\",\"deptList\":[{\"id\":0,\"name\":\"TEST\",\"salary\":10,\"course\":\"MCA\"}]}");
		
	}
	
	@Test
	public void testGetDepartmentDetails() throws Exception {
		this.mockMvc.perform(get("/getDeptDetails")).andExpect(status().isOk());
	}
	
	@Test
	public void tesPostDepartmentDetails_400BadRequest_InvalidData() throws Exception {
		this.mockMvc.perform(post("/saveDeptDetails"));


		this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(getBadRequestDepartmentDetails())))
		        .andExpect(status().isBadRequest())
		        .andDo(print());
	}
	

	@Test
	public void tesPostDepartmentDetails_400BadRequest_alfaNumericName() throws Exception {
		Department department = getDepartmentDetails();
		department.setName("TEST123");
		
		String contentAsString = this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isBadRequest())
		        .andReturn().getResponse().getContentAsString();
		ErrorModel dto = new ObjectMapper().readValue(contentAsString, ErrorModel.class);
		assertEquals(dto.getErrorCode(),"400");
		assertEquals(dto.getUserMessage(), "Name accepts only characters");
	}

	@Test
	public void tesPostDepartmentDetails_400BadRequest_alfaNumericCourse() throws Exception {
		
		Department department = getDepartmentDetails();
		department.setCourse("TEST123");
		
		String contentAsString = this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isBadRequest())
		        .andReturn().getResponse().getContentAsString();
		ErrorModel dto = new ObjectMapper().readValue(contentAsString, ErrorModel.class);
		assertEquals(dto.getErrorCode(),"400");
		assertEquals(dto.getUserMessage(), "Course accepts only characters");
	
	}
	
	@Test
	public void tesPostDepartmentDetails_Success() throws Exception {
		Department department = getDepartmentDetails();
		
		when(departmentRepository.save(Mockito.any(Department.class)))
        .thenAnswer(i -> i.getArguments()[0]);
		
//		User userToReturnFromRepository = new User();
//	     userToReturnFromRepository.setAuthToken(YOUR_TOKEN);
	     //when(departmentRepository.save(any(Department.class))).thenReturn(Arrays.asList(department));
	     
		
		//Mockito.when(departmentRepository.save(any(Department.class)).thenReturn(Arrays.asList(department)));
		//when(departmentRepository.save(any(Object.class)).thenReturn(false);
		
		String contentAsString = this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
	
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertTrue(dto.getDeptList().size() == 1);
		assertEquals(dto.getStatus(), "OK");
		assertEquals(dto.getMessage(), "Added Successfully.");
		assertThat(contentAsString)
		.contains("{\"id\":0,\"name\":\"TEST\",\"salary\":10,\"course\":\"MCA\"}");
	
	}
	
	@Test
	public void tesPostDepartmentDetails_IdExist() throws Exception {
		Department department = getDepartmentDetails();
		
		department.setId(1234);
		Mockito.when(departmentRepository.findById(1234)).thenReturn(Optional.of(department));
		
//		User userToReturnFromRepository = new User();
//	     userToReturnFromRepository.setAuthToken(YOUR_TOKEN);
	     //when(departmentRepository.save(any(Department.class))).thenReturn(Arrays.asList(department));
	     
		
		//when(departmentRepository.save(any(Object.class)).thenReturn(false);
		//Mockito.when(departmentRepository.save(any(Department.class)).thenReturn(Arrays.asList(department)));
		
		String contentAsString = this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
	
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertEquals(dto.getStatus(), "ERROR");
		assertEquals(dto.getMessage(), "Department Id already Existed..");
	
	}
	
	@Test
	public void tesPostDepartmentDetails_fail() throws Exception {
		Department department = getDepartmentDetails();
		department.setId(56);
		String contentAsString = this.mockMvc.perform(post("/saveDeptDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertEquals(dto.getStatus(), "OK");
		assertEquals(dto.getMessage(), "Added Successfully.");
	
	}
	
	@Test
	public void testUpdateDepartmentDetails_Fail() throws Exception {
		Department department = getDepartmentDetails();
		department.setId(55);
		
		String contentAsString = this.mockMvc.perform(put("/updateDepartmentDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertEquals(dto.getStatus(), "ERROR");
		assertEquals(dto.getMessage(), "unable to update record..");
	
	}
	
	@Test
	public void testUpdateDepartmentDetails_Success() throws Exception {
		Department department = getDepartmentDetails();
		department.setId(56);
		
		String contentAsString = this.mockMvc.perform(put("/updateDepartmentDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertEquals(dto.getStatus(), "ERROR");
		assertEquals(dto.getMessage(), "unable to update record..");
	
	}
	
	@Test
	public void testUpdateDepartmentDetails_Service() throws Exception {
		Department department = getDepartmentDetails();
		
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(department));
		
		String contentAsString = this.mockMvc.perform(put("/updateDepartmentDetails").contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(department)))
		        .andExpect(status().isOk())
		        .andReturn().getResponse().getContentAsString();
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		assertEquals(dto.getStatus(), "OK");
		assertEquals(dto.getMessage(), "Updated successfully.");
		assertThat(contentAsString)
		.contains("{\"status\":\"OK\",\"message\":\"Updated successfully.\",\"deptList\":[null]}");
	
	}
	
	@Test
	public void test_getDeptDetails() throws Exception {
		
		List<Department> list = getDepartmentList();
		Mockito.when(departmentRepository.findAll()).thenReturn(list);
		
		String contentAsString = this.mockMvc.perform(get("/getDeptDetails")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		assertThat(contentAsString)
		.contains("{\"id\":1,\"name\":\"test\",\"salary\":10000,\"course\":\"Java\"}");
		
		
	}
	
	@Test
	public void test_getDeptDetailsById() throws Exception {
		
		List<Department> list = getDepartmentList();
		Optional<Department> department = Optional.of(list.get(0));
		department.get().setId(1234);
		Mockito.when(departmentRepository.findById(1234)).thenReturn(department);
		
		String contentAsString = this.mockMvc.perform(get("/getDepartmentList/{id}", 1234)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		
		assertThat(contentAsString)
		.contains("{\"id\":1234,\"name\":\"test\",\"salary\":10000,\"course\":\"Java\"}");
		assertEquals(dto.getStatus(), "OK");
		assertEquals(dto.getMessage(), "Id found successfully with given id: 1234");
		
	}
	
	@Test
	public void test_getDeptDetailsByIdNotFound() throws Exception {
		

		Optional<Department> department = Optional.ofNullable(null);
		Mockito.when(departmentRepository.findById(1234)).thenReturn(department);
		
		String mvcResult = this.mockMvc.perform(get("/getDepartmentList/{id}", 1234)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse()
		        .getContentAsString();
		
		assertThat(mvcResult)
		.contains("{\"userMessage\":\"Data is not found for id 1234\",\"errorCode\":\"404\",\"moreInfo\":\"Not Found\"}");
				
	}
	
	@Test
	public void test_serachBynameSucces() throws Exception {
		
		List<Department> list = getDepartmentList();
		Optional<Department> department = Optional.of(list.get(0));
		department.get().setName("Arun");
		Mockito.when(departmentRepository.findByNameOrCourse(Mockito.anyString(), Mockito.anyString())).thenReturn(list);
		
		String contentAsString = this.mockMvc.perform(get("/searchByname/{name}", 1234)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		
		assertThat(contentAsString)
		.contains("{\"id\":1,\"name\":\"Arun\",\"salary\":10000,\"course\":\"Java\"}");
		assertEquals(dto.getStatus(), "OK");
		assertEquals(dto.getMessage(), "Records Found..");
		
	}
	
	@Test
	public void test_serachBynameNotFound() throws Exception {
		
		
		Mockito.when(departmentRepository.findByNameOrCourse(Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<Department>());
		
		String contentAsString = this.mockMvc.perform(get("/searchByname/{name}", "arun")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		
		assertThat(contentAsString)
		.contains("{\"status\":\"KO\",\"message\":\"No Records Found..\",\"deptList\":null}");
		assertEquals(dto.getStatus(), "KO");
		assertEquals(dto.getMessage(), "No Records Found..");
		
	}

	
	@Test
	public void test_serachBynameFail() throws Exception {
		
		
		String contentAsString = this.mockMvc.perform(get("/searchByname/{name}", "A")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		DepartmentDTO dto = new ObjectMapper().readValue(contentAsString, DepartmentDTO.class);
		
		assertEquals(dto.getStatus(), "KO");
		assertEquals(dto.getMessage(), "Minimum 2 chars allowed...");
		
	}
	
	@Test
	public void test_deleteDepartmentDetailsByid() throws Exception
	{
		List<Department> list = getDepartmentList();
		Optional<Department> department = Optional.of(list.get(0));
		department.get().setId(1234);
		Mockito.when(departmentRepository.findById(1234)).thenReturn(department);
		
		String contentAsString = this.mockMvc.perform(delete("/deleteDepartmentDetailsByid/{id}", 1234)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		assertThat(contentAsString)
	.contains("{\"status\":\"Ok\",\"message\":\"record deleted successfuylly\",\"deptList\":null}");


	}
	
	@Test
	public void test_deleteDepartmentDetailsByidNotFound() throws Exception
	{
		
		String contentAsString = this.mockMvc.perform(delete("/deleteDepartmentDetailsByid/{id}", 1234)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn()
		        .getResponse()
		        .getContentAsString();
		
		assertThat(contentAsString)
	.contains("{\"status\":\"BadRequest\",\"message\":\"record id not found in database1234\",\"deptList\":null}");

	}
	
	private List<Department> getDepartmentList() {
		List<Department> list = new ArrayList<>();
		Department department = new Department();
		department.setCourse("Java");
		department.setId(1);
		department.setName("test");
		department.setSalary(new BigDecimal(10000.0));
		list.add(department);
		return list;
	}
	
	
	private Department getDepartmentDetails() {
		Department department = new Department();
		department.setName("TEST");
		department.setSalary(BigDecimal.TEN);
		department.setCourse("MCA");
		return department;
	}
	
	private Department getBadRequestDepartmentDetails() {
		return new Department();
	}

}
