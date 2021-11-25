package com.pirmas.laboratorinis.DataStructures;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Company extends User implements Serializable {
	private String companyName;
	private String companyRepresentative;
	private String companyAddress;
	private String companyPhoneNumber;

	public Company(String userName, String userPassword, String companyName, String companyRepresentative, String companyAddress, String companyPhoneNumber) {
		super(userName, userPassword);
		this.companyName = companyName;
		this.companyRepresentative = companyRepresentative;
		this.companyAddress = companyAddress;
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public Company(String userName, String userPassword, String companyName, String companyRepresentative, String companyAddress, String companyPhoneNumber, LocalDate dateCreated, LocalDate dateModified, boolean isActive) {
		super(userName, userPassword, dateCreated, dateModified);
		this.companyName = companyName;
		this.companyRepresentative = companyRepresentative;
		this.companyAddress = companyAddress;
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

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}
}
