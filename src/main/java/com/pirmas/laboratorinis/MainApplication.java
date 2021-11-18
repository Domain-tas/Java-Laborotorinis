package com.pirmas.laboratorinis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginWindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Course Management System");
		stage.setScene(scene);
		stage.show();
	}
	/*private Stage stage;
	private static MainApplication instance;
	@Override
	public void start(Stage primaryStage) throws IOException {
		try{
			stage = primaryStage;
			primaryStage.setResizable(false);
			gotoLogin();
			primaryStage.show();
		} catch(Exception ex){

		}

	}

	private void gotoLogin() {
		try{
			replaceSceneContext("LoginWindow.fxml");
		} catch (Exception ex){

		}
	}

	private Parent replaceSceneContext(String fxml) throws Exception{
		Parent page = (Parent) FXMLLoader.load(MainApplication.class.getResource(fxml),null, new JavaFXBuilderFactory());
		Scene scene = stage.getScene();
		if(scene==null)
		{
			scene = new Scene(page);
			stage.setScene(scene);
			stage.setTitle("Login");
		} else{
			stage.getScene().setRoot(page);
		}
		return page;
	}*/

	public static void main(String[] args) {
		launch();
	}
}