package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Table(name="courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String courseName;
	private String courseDescription;
	private Date dateCreated;
	private Date endDate;
	private Date expectedEndDate;
	@ManyToMany(mappedBy = "userCourses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> responsibleUsers;
	@OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Task> projectTasks;

	public Course(int id, String courseName, String courseDescription, Date dateCreated, Date endDate, Date expectedEndDate, List<User> responsibleUsers, List<Task> projectTasks) {
		this.id = id;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.dateCreated = dateCreated;
		this.endDate = endDate;
		this.expectedEndDate = expectedEndDate;
		this.responsibleUsers = responsibleUsers;
		this.projectTasks = projectTasks;
	}

	public Course() {
	}

	public Course(int id, String courseName, String courseDescription, Date dateCreated, Date endDate, Date expectedEndDate) {
		this.id = id;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.dateCreated = dateCreated;
		this.endDate = endDate;
		this.expectedEndDate = expectedEndDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public List<User> getResponsibleUsers() {
		return responsibleUsers;
	}

	public void setResponsibleUsers(List<User> responsibleUsers) {
		this.responsibleUsers = responsibleUsers;
	}

	public List<Task> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<Task> projectTasks) {
		this.projectTasks = projectTasks;
	}
}
