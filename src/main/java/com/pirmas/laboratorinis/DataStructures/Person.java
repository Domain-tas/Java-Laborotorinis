package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person extends User implements Serializable {
	private String personName;
	private String personSurname;
	private String personEmail;
	private String personPosition;
	@OneToMany(mappedBy = "responsible", cascade = {CascadeType.MERGE},orphanRemoval = true)
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Folder> myFolders=new ArrayList<>();
//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@OrderBy("id ASC")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Course> editableCourses=new ArrayList<>();

	public Person(String personName, String personSurname, String personEmail, String personPosition, String username, String userPassword) {
		super(username, userPassword);
		this.personName = personName;
		this.personSurname = personSurname;
		this.personEmail = personEmail;
		this.personPosition = personPosition;
	}
	public Person() {
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonSurname() {
		return personSurname;
	}

	public void setPersonSurname(String personSurname) {
		this.personSurname = personSurname;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getPersonPosition() {
		return personPosition;
	}

	public void setPersonPosition(String personPhoneNumber) {
		this.personPosition = personPhoneNumber;
	}

	public List<Folder> getMyFolders() {
		return myFolders;
	}

	public void setMyFolders(List<Folder> myFolders) {
		this.myFolders = myFolders;
	}

	public void addToMyFolders(Folder myFolder) {
		this.myFolders.add(myFolder);
	}

//	public List<Course> getEditableCourses() {
//		return editableCourses;
//	}
//
//	public void setEditableCourses(List<Course> editableCourses) {
//		this.editableCourses = editableCourses;
//	}
//	public void addEditableCourses(Course editableCourse) {
//		this.editableCourses.add(editableCourse);
//	}

//	public List<Course> getMyCourses() {
//		return myCourses;
//	}
//	public void setMyCourses(List<Course> myCourses) {
//		this.myCourses = myCourses;
//	}
//	public void addToMyCourses(Course myCourse) {
//		this.myCourses.add(myCourse);
//	}
}
