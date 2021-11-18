package com.pirmas.laboratorinis.DataStructures;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Company extends User implements Serializable {
	private String companyName;
	private String companyRepresentative;
	private String companyEmail;
	private String companyPhoneNumber;

	public Company(String userName, String userPassword, String companyName, String companyRepresentative, String companyEmail, String companyPhoneNumber) {
		super(userName, userPassword);
		this.companyName = companyName;
		this.companyRepresentative = companyRepresentative;
		this.companyEmail = companyEmail;
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public Company(String userName, String userPassword, String companyName, String companyRepresentative, String companyEmail, String companyPhoneNumber, LocalDate dateCreated, LocalDate dateModified, boolean isActive) {
		super(userName, userPassword, dateCreated, dateModified, isActive);
		this.companyName = companyName;
		this.companyRepresentative = companyRepresentative;
		this.companyEmail = companyEmail;
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public Company(){}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyRepresentative() {
		return companyRepresentative;
	}

	public void setCompanyRepresentative(String companyRepresentative) {
		this.companyRepresentative = companyRepresentative;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}
}
