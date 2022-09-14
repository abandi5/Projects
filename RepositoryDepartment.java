package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;

@Repository
public interface RepositoryDepartment extends JpaRepository<Department, Integer> {

	// @Query("Select d from Department d where d.name like %:name%")
	List<Department> findByNameContaining(String name);
	
	//public List<Department> findByNameContainingOrCourseContaining(String name, String course);
	
	public List<Department> findByNameOrCourse(String name, String course);

}
