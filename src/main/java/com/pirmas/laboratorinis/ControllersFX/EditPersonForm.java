package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class EditPersonForm {
	@FXML
	public TextField changePassword;
	public TextField changeName;
	public TextField changeContact;
	public TextField changePosition;

	private User user;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

	public void setPersonFormData(User user) {
		this.user = user;
	}

	public void editPerson(ActionEvent actionEvent) throws IOException {
		user.setUserName(changeName.getText());
		user.setUserPassword(changePassword.getText());
		userHibernateController.editUser(user);
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CourseWindow.fxml"));
		Parent root = fxmlLoader.load();

		CourseWindow mainCourseWindow = fxmlLoader.getController();
		mainCourseWindow.setUser(user);

		Scene scene = new Scene(root);
		Stage stage = (Stage) changePassword.getScene().getWindow();
		//stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.show();
	}
}
