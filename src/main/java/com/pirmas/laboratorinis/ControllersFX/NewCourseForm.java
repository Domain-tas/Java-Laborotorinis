package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.FolderHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class NewCourseForm {
	@FXML
	public TextField courseTitle;
	public TextArea courseDesc;
	public DatePicker courseExpEnd;
	public Button buttonText;

	private User user;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	CourseHibernateController courseHibernateController = new CourseHibernateController(entityManagerFactory);
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);
	FolderHibernateController folderHibernateController = new FolderHibernateController(entityManagerFactory);

	public void setCourseFormData(User user) {
		this.user = user;
	}

	public void createCourse(ActionEvent actionEvent) throws IOException {
		Person person = userHibernateController.getPersonById(user.getId());
		Course course = new Course(courseTitle.getText(), courseDesc.getText(), courseExpEnd.getValue());
		Folder folder = new Folder(courseTitle.getText(), userHibernateController.getPersonById(user.getId()), user);
		person.addToMyFolders(folder);
		//person.addToMyCourses(course);
		course.addCourseFolder(folder);
		course.addResponsibleUsers(user);
		//person.addEditableCourses(course);
		//course.setCreator(person);
		//course.addResponsibleUsers(user);
		folder.setCourse(course);
		folder.setParentFolder(folder);
		user.addUserCourses(course);
		userHibernateController.editUser(user);
		//userHibernateController.editUser(person);
		//courseHibernateController.createCourse(course);
		//folderHibernateController.createFolder(folder);

		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CourseWindow.fxml"));
		Parent root = fxmlLoader.load();
		CourseWindow mainCourseWindow = fxmlLoader.getController();
		mainCourseWindow.setUser(userHibernateController.getUserById(user.getId()));
		Scene scene = new Scene(root);
		Stage stage = (Stage) courseTitle.getScene().getWindow();
		//stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.show();
	}
}
