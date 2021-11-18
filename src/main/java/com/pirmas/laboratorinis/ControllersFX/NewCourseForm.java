package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.nenaudojami.DatabaseControls;
import com.pirmas.laboratorinis.DataStructures.CourseManagementSystem;
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

import java.io.IOException;

public class NewCourseForm {
	@FXML
	public TextField courseId;
	public TextField courseTitle;
	public TextArea courseDesc;
	public DatePicker courseExpEnd;

	private int id;

	public void setCourseFormData(int login) {
		this.id = id;
	}

	public void createCourse(ActionEvent actionEvent) throws IOException {
		//DatabaseControls.addCourse(courseTitle.getText(), courseDesc.getText(), courseExpEnd.getValue(), login);
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CourseWindow.fxml"));
		Parent root = fxmlLoader.load();

		CourseWindow mainCourseWindow = fxmlLoader.getController();
		mainCourseWindow.setUser(id);

		Scene scene = new Scene(root);
		Stage stage = (Stage) courseId.getScene().getWindow();
		//stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.show();
	}
}
