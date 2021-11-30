package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.DataStructures.CourseManagementSystem;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {
	@FXML
	public Button loginButton;
	public Button registerButton;
	public TextField userPassword;
	public TextField userName;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void loginUser(ActionEvent actionEvent) throws IOException, SQLException {
		User user;
		user = userHibernateController.getUserByLogin(userName.getText(), userPassword.getText());
		if (user != null) {
			FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CourseWindow.fxml"));
			Parent root = fxmlLoader.load();

			CourseWindow courseWindow = fxmlLoader.getController();
			courseWindow.setCourseFormData(user);

			Scene scene = new Scene(root);

			Stage stage = new Stage();
			stage.setTitle("Project Management System");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} else {
			UtilityWindows.alertMessage("Wrong input data, no such user found");
		}
	}

	@FXML
	public void registerUserForm(ActionEvent actionEvent) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("RegistrationWindow.fxml"));
		Parent root = fxmlLoader.load();

		RegistrationWindow registrationForm = fxmlLoader.getController();

		Scene scene = new Scene(root);

		Stage stage = (Stage) userName.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}

