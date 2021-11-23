package com.pirmas.laboratorinis.ControllersFX;

import javafx.stage.Modality;

public class UtilityWindows {
	public static void alertMessage(String s) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Message text:");
		alert.setContentText(s);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.showAndWait();
	}
}
