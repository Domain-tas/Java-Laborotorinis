package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationWindow implements Initializable {
	@FXML
	public Button registerButton;
	public RadioButton companyButton;
	public ToggleGroup userType;
	public RadioButton personButton;
	public TextField repeatPasswordField;
	public TextField passwordField;
	public TextField userNameField;
	public TextField surnameField;
	public TextField nameField;
	public Button cancelButton;
	public TextField emailFieldPerson;
	public TextField companyName;
	public TextField representativeName;
	public TextField addressFieldCompany;
	public TextField phoneNumberCompany;
	public TextField positionPerson;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

	public RegistrationWindow() {
	}

	@FXML
	public void validateRegisterUser(ActionEvent actionEvent) throws IOException {
		if (!passwordField.getText().equals(repeatPasswordField.getText())) {
			UtilityWindows.alertMessage("Your passwords didn't match");
			return;
		}
		if (personButton.isSelected()) {
			Person person = new Person(nameField.getText(), surnameField.getText(), emailFieldPerson.getText(), positionPerson.getText(), userNameField.getText(), passwordField.getText());
			userHibernateController.createUser(person);
		} else {
			Company company = new Company(userNameField.getText(), passwordField.getText(), companyName.getText(), representativeName.getText(), addressFieldCompany.getText(), phoneNumberCompany.getText());
			userHibernateController.createUser(company);
		}
		registrationSuccessful(true);
	}

	private void registrationSuccessful(boolean isSuccessful) throws IOException {
		if (isSuccessful)
			UtilityWindows.alertMessage("Registration was successful");
		else
			UtilityWindows.alertMessage("Registration failed");
		returnToPrevious();
	}

	@FXML
	public void returnToLogin(ActionEvent actionEvent) throws IOException {
		returnToPrevious();
	}

	private void returnToPrevious() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginWindow.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);

		Stage stage = (Stage) userNameField.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		companyName.setDisable(true);
		representativeName.setDisable(true);
		phoneNumberCompany.setDisable(true);
		addressFieldCompany.setDisable(true);
	}

	public void enableFields(ActionEvent actionEvent) {
		if (personButton.isSelected()) {
			nameField.setDisable(false);
			surnameField.setDisable(false);
			positionPerson.setDisable(false);
			emailFieldPerson.setDisable(false);
			companyName.setDisable(true);
			representativeName.setDisable(true);
			addressFieldCompany.setDisable(true);
			phoneNumberCompany.setDisable(true);
		} else {
			nameField.setDisable(true);
			surnameField.setDisable(true);
			positionPerson.setDisable(true);
			emailFieldPerson.setDisable(true);
			companyName.setDisable(false);
			representativeName.setDisable(false);
			addressFieldCompany.setDisable(false);
			phoneNumberCompany.setDisable(false);
		}
	}

}
