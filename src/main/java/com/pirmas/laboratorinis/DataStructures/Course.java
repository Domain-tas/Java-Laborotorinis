package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int id;
	private String courseName;
	private String courseDescription;
	private LocalDate dateCreated;
	private LocalDate endDate;
	private LocalDate expectedEndDate;
	@ManyToMany(mappedBy = "userCourses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> responsibleUsers=new ArrayList<>();
	@OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Task> courseTasks = new ArrayList<>();

	public Course(String courseName, String courseDescription, LocalDate endDate, List<User> responsibleUsers, List<Task> projectTasks) {
		//this.id = id;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.dateCreated = LocalDate.now();
		this.endDate = endDate;
		this.expectedEndDate = endDate;
		this.responsibleUsers = responsibleUsers;
		this.courseTasks = projectTasks;
	}

	public Course() {
	}

public Course(String courseName, String courseDescription, LocalDate endDate) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.dateCreated = LocalDate.now();
		this.endDate = endDate;
		//this.expectedEndLocalDate = expectedEndLocalDate;
	}

	public int getId() {
		return id;
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

	public LocalDate getLocalDateCreated() {
		return dateCreated;
	}

	public void setLocalDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndLocalDate(LocalDate expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public List<User> getResponsibleUsers() {
		return responsibleUsers;
	}

	public void setResponsibleUsers(List<User> responsibleUsers) {
		this.responsibleUsers = responsibleUsers;
	}

	public List<Task> getCourseTasks() {
		return courseTasks;
	}

	public void setCourseTasks(List<Task> projectTasks) {
		this.courseTasks = projectTasks;
	}
	public void addResponsibleUsers(User user){
		this.responsibleUsers.add(user);
		user.getUserCourses().add(this);
	}
	public void removeResponsibleUsers(User user){
		this.responsibleUsers.remove(user);
		user.getUserCourses().remove(this);
	}
	public void setId(int id) {
		this.id = id;
	}
}
