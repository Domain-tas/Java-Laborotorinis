package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.nenaudojami.DatabaseControls;
import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.CourseManagementSystem;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CourseWindow {
	public MenuItem deleteItem;
	public MenuItem editItem;
	public MenuItem addItem;
	public ListView myCourses;
	public TreeView courseTasks;

	private int id;

	public void setCourseFormData(int id) {
		this.id = id;
		fillTables();
	}

	public void newCourseForm(ActionEvent actionEvent) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("NewCourseForm.fxml"));
		Parent root = fxmlLoader.load();

		NewCourseForm newCourseForm = fxmlLoader.getController();
		newCourseForm.setCourseFormData(id);

		Scene scene = new Scene(root);

		Stage stage = (Stage) myCourses.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	public void setUser(int id) {
		this.id = id;
		fillTables();
	}
	private void fillTables() {
		myCourses.getItems().clear();
		ArrayList<Course> courseDatabase = CourseHibernateController.getCoursesByUser(id);
		for (Course cour : courseDatabase) {
			myCourses.getItems().add(cour.getId() + ":" + cour.getCourseName());
		}
	}

	public void editSelected(ActionEvent actionEvent) {
	}

	public void deleteSelected(ActionEvent actionEvent) {
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

	public void newProjectForm(ActionEvent actionEvent) {
	}
}
