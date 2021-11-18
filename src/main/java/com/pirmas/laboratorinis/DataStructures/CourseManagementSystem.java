package com.pirmas.laboratorinis.DataStructures;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class CourseManagementSystem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String version;
	private List<User> allUsers;
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
