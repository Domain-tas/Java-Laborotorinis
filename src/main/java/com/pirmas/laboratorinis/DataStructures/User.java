package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
//@Table(name="users")
public abstract class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;
	private String userPassword;
	private LocalDate dateCreated;
	private LocalDate dateModified;
	private boolean isActive;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> userCourses;

	public User(String userName, String userPassword, LocalDate dateCreated, LocalDate dateModified, boolean isActive) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.isActive = isActive;
	}

	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.dateCreated = LocalDate.now();
		this.dateModified = LocalDate.now();
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

	public void setId(int userId) {
		this.id = userId;
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
}
