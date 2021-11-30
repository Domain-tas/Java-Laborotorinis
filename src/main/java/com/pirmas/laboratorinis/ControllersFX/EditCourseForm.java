package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class EditCourseForm {
	@FXML
	public TextField courseTitle;
	public TextArea courseDesc;
	public DatePicker courseExpEnd;

	private Course course;
	private User user;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	CourseHibernateController courseHibernateController = new CourseHibernateController(entityManagerFactory);

	public void setCourseFormData(Course course, User user) {
		this.user = user;
		this.course = course;
	}

	public void editCourse(ActionEvent actionEvent)  throws IOException {
		course.setCourseName(courseTitle.getText());
		course.setCourseDescription(courseDesc.getText());
		course.setEndDate(courseExpEnd.getValue());
		courseHibernateController.editCourse(course);
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CourseWindow.fxml"));
		Parent root = fxmlLoader.load();

		CourseWindow mainCourseWindow = fxmlLoader.getController();
		mainCourseWindow.setUser(user);

		Scene scene = new Scene(root);
		Stage stage = (Stage) courseTitle.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

}
