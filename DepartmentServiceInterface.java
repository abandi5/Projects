package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.entity.Department;

public interface DepartmentServiceInterface {
	List<Department> getDeptDetails();
	DepartmentDTO saveAllDeptDetails(List<Department> department);
	DepartmentDTO saveDeptDetails(Department department);
	DepartmentDTO updateDepartmentDetails(Department department);
    DepartmentDTO deleteDepartmentDetailsByid(int id);
    DepartmentDTO getDepartmentList(Integer id);
    DepartmentDTO serachByname(String name);
}
