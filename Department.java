package com.example.demo.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "department_table", schema = "arun")
public class Department {

	@Column(name = "department_id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "department_name")
	@NotEmpty(message = "name is mandatory.")
	@Size(min = 2, max = 20)
	private String name;

	@Column(name = "department_salary")
	@NotNull(message = "Salary can not be null")
	@DecimalMax(value = "10000000.00")
	@DecimalMin(value = "0.00")
	@Digits(integer = 20, fraction = 2, message = "Salary Should have Fraction=2")
	@Positive
	private BigDecimal salary;

	@Column(name = "department_course")
	@NotEmpty(message = "Course is required")
	@Size(min = 2, max = 20)
	private String course;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the salary
	 */
	public BigDecimal getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", salary=" + salary + ", course=" + course + "]";
	}
	

	
	

}
