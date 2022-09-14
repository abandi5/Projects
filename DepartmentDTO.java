package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Department;

public class DepartmentDTO {
	
	private String status;
	private String message;
	List<Department> deptList;
	public String getStatus() {
		return status;
	}
	public DepartmentDTO() {
		
	}
	public DepartmentDTO(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Department> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}
	@Override
	public String toString() {
		return "DepartmentDTO [status=" + status + ", message=" + message + ", deptList=" + deptList + "]";
	}
	
	

}
