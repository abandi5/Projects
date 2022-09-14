package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.service.DepartmentServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
//@Hidden
@Validated
public class DepartmentController {

	@Autowired
	DepartmentServiceInterface departmentServiceInterface;

	private static final java.util.logging.Logger log = java.util.logging.Logger
			.getLogger(DepartmentController.class.getName());

	// @Hidden
	@GetMapping("/getDeptDetails")
	@Operation(summary = "To get Department Details", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })
	public List<Department> getDeptDetails() {
		log.info("Method to run the getDeptDetails");
		return departmentServiceInterface.getDeptDetails();
	}

	@Operation(summary = "To save Department Details", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })
	@PostMapping("/saveDeptDetails")
	public DepartmentDTO saveDeptDetails(@RequestBody @Valid Department department) {
		if (null != department && StringUtils.isNotBlank(department.getName())
				&& !StringUtils.isAlpha(department.getName()))
			throw new InvalidDataException("Name accepts only characters");
		if (null != department && StringUtils.isNotBlank(department.getCourse())
				&& !StringUtils.isAlpha(department.getCourse()))
			throw new InvalidDataException("Course accepts only characters");
		return departmentServiceInterface.saveDeptDetails(department);

	}

	@Operation(summary = "To save multiple Department Details", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })

	@PostMapping("/saveAllDeptDetails")
	public DepartmentDTO saveAllDeptDetails(
			@RequestBody @NotEmpty(message = "Input Department list cannot be empty.") List<@Valid Department> depDapartments) {

		return departmentServiceInterface.saveAllDeptDetails(depDapartments);
	}

	@Operation(summary = "To update Department Details", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })

	@PutMapping("/updateDepartmentDetails")
	public DepartmentDTO updateDepartmentDetails(@RequestBody @Valid Department department) {

		return departmentServiceInterface.updateDepartmentDetails(department);
	}

	@Operation(summary = "To Delete Department Details", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })

	@DeleteMapping("/deleteDepartmentDetailsByid/{id}")
	public DepartmentDTO deleteDepartmentDetailsByid(@PathVariable int id) {
		return departmentServiceInterface.deleteDepartmentDetailsByid(id);
	}

	@Operation(summary = "To get Department Details By Id", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found") })

	// @Hidden
	@GetMapping("/getDepartmentList/{id}")
	public DepartmentDTO getDepartmentList(@PathVariable Integer id) {
		return departmentServiceInterface.getDepartmentList(id);
	}


	@GetMapping("/searchByname/{name}")
	public DepartmentDTO serachByname(@PathVariable(value = "name") String name) {
		if (StringUtils.isNotBlank(name) && name.length() <= 1) {
			return new DepartmentDTO("KO", "Minimum 2 chars allowed...");
		}
		return departmentServiceInterface.serachByname(name);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return errors;
	}
	


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
		String errorMessage = "";
		if (!violations.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			violations.forEach(violation -> builder.append(" " + violation.getMessage()));
			errorMessage = builder.toString();
		} else {
			errorMessage = "ConstraintViolationException occured.";
		}
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}


}
