package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="users")
public abstract class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	@Column(insertable = false, updatable = false)
	private String dtype;
	private String userName;
	private String userPassword;
	private LocalDate dateCreated;
	private LocalDate dateModified;
	private Privilege privilege;
	private boolean isActive;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> userCourses = new ArrayList<>();
	@OneToMany(mappedBy = "creator", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Folder> createdFolders;

	public User(String userName, String userPassword, LocalDate dateCreated, LocalDate dateModified) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.privilege=Privilege.USER;
		this.isActive = true;
	}

	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.dateCreated = LocalDate.now();
		this.dateModified = LocalDate.now();
		this.privilege=Privilege.USER;
		this.isActive = true;
	}

	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getId() {
		return id;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDateModified() {
		return dateModified;
	}

	public void setDateModified(LocalDate dateModified) {
		this.dateModified = dateModified;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public List<Course> getUserCourses() {
		return userCourses;
	}

	public void setUserCourses(List<Course> userCourses) {
		this.userCourses = userCourses;
	}

	public void addUserCourses(Course course)
	{
		this.userCourses.add(course);
		//course.getCourseUsers().add(this);
		//course.getResponsibleUsers().add(this);
	}

	public void removeUserCourses(Course course)
	{
		this.userCourses.remove(course);
		course.getResponsibleUsers().remove(this);
	}

	public void setId(int id) {
		this.id = id;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public String getDtype() {
		return dtype;
	}

	public List<Folder> getCreatedFolders() {
		return createdFolders;
	}

	public void setCreatedFolders(List<Folder> createdFolders) {
		this.createdFolders = createdFolders;
	}
}
