package com.pirmas.laboratorinis;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUp {
	public Label messageField;
	public Button closeButton;

	Stage popUp = new Stage();

	public PopUp() throws IOException {
		messageField= new Label();
	}

	public void Display(String windowTitle, String message) throws IOException {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUp.fxml"));
			Parent root = fxmlLoader.load();
			popUp.initModality(Modality.APPLICATION_MODAL);
			popUp.setTitle(windowTitle);
			messageField.setText(message);
			popUp.setScene(new Scene(root));
			popUp.showAndWait();
		}catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public void closeWindow(){
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
