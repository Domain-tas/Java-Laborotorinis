package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.List;

@Entity
public class Person extends User implements Serializable {
	private String personName;
	private String personSurname;
	private String personEmail;
	private String personPosition;
	@OneToMany(mappedBy = "responsible", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("id ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Task> myTasks;

	public Person(String personName, String personSurname, String personEmail, String personPosition, String username, String userPassword)
	{
		super(username, userPassword);
		this.personName = personName;
		this.personSurname = personSurname;
		this.personEmail = personEmail;
		this.personPosition = personPosition;
	}
	public Person(){}

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

	public List<Task> getMyTasks() {
		return myTasks;
	}

	public void setMyTasks(List<Task> myTasks) {
		this.myTasks = myTasks;
	}

}
