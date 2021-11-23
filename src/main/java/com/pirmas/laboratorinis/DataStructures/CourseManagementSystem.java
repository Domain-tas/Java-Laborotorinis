package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class CourseManagementSystem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String version;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> allUsers;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> allCourses;
	public CourseManagementSystem(){}

	public CourseManagementSystem(int id, String version, ArrayList<User> allUsers, ArrayList<Course> allCourses) {
		this.id = id;
		this.version = version;
		this.allUsers = allUsers;
		this.allCourses = allCourses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public List<Course> getAllCourses() {
		return allCourses;
	}

	public void setAllCourses(List<Course> allCourses) {
		this.allCourses = allCourses;
	}
}
