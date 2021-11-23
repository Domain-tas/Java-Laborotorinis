package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Privilege;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseWindow {
	public MenuItem deleteItem;
	public MenuItem editItem;
	public MenuItem addItem;
	public ListView myCourses;
	public TreeView courseTasks;
	public Button newCourseButton;
	public MenuItem showAllCourseMenu;
	public MenuItem addItemCourseMenu;
	public MenuItem editItemCourseMenu;
	public MenuItem deleteItemCourseMenu;

	private User user;

	public void setCourseFormData(User user) {
		this.user = user;
		fillTables();
	}
	public void setUser(User user) {
		this.user = user;
		fillTables();
	}

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	CourseHibernateController courseHibernateController = new CourseHibernateController(entityManagerFactory);
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

	public void newCourseForm(ActionEvent actionEvent) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("NewCourseForm.fxml"));
		Parent root = fxmlLoader.load();
		NewCourseForm newCourseForm = fxmlLoader.getController();
		newCourseForm.setCourseFormData(user);
		Scene scene = new Scene(root);
		Stage stage = (Stage) myCourses.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	private void fillTables() {
		if(user.getPrivilege()== Privilege.USER)
		{
			newCourseButton.setVisible(false);
			addItem.setVisible(false);
			addItemCourseMenu.setVisible(false);
			editItemCourseMenu.setVisible(false);
			deleteItemCourseMenu.setVisible(false);
		}
		myCourses.getItems().clear();
		List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
		for (Course course : courseDatabase) {
			myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
		}
	}

	public void editSelected(ActionEvent actionEvent) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("EditCourseForm.fxml"));
		Parent root = fxmlLoader.load();
		EditCourseForm editCourseForm = fxmlLoader.getController();
		Course course;
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		course = courseHibernateController.getCourseById(courseId);
		editCourseForm.courseTitle.setText(course.getCourseName());
		editCourseForm.courseDesc.setText(course.getCourseDescription());
		editCourseForm.courseExpEnd.setValue(course.getEndDate());
		editCourseForm.setCourseFormData(course, user);
		Scene scene = new Scene(root);
		Stage stage = (Stage) myCourses.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	public void deleteSelected(ActionEvent actionEvent) {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
/*		Course course = courseHibernateController.getCourseById(courseId);
		//user.removeUserCourses(course);

		//userHibernateController.editUser(user);
		//course.removeResponsibleUsers(user);
		//courseHibernateController.editCourse(course);*/
		courseHibernateController.removeCourse(courseId);
		fillTables();
	}

	public void loadAllUsersForm(ActionEvent actionEvent) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("UserForm.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void loadAllCourses(ActionEvent actionEvent) {
		myCourses.getItems().clear();
		List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
		for (Course course : courseDatabase) {
			myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
		}
	}

	public void editPersonalForm(ActionEvent actionEvent) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("EditCourseForm.fxml"));
		Parent root = fxmlLoader.load();
		EditPersonForm editPersonForm = fxmlLoader.getController();
		editPersonForm.changeName.setText(user.getUserName());
		editPersonForm.changePassword.setText(user.getUserPassword());
		editPersonForm.setPersonFormData(user);
		Scene scene = new Scene(root);
		Stage stage = (Stage) myCourses.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
