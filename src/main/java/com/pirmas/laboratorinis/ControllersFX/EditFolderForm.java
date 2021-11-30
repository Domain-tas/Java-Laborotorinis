package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.FolderHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.PersonHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EditFolderForm {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	FolderHibernateController folderHibernateController = new FolderHibernateController(entityManagerFactory);

	private Folder folder;

	public TextField newFolderName;

	public void closeWindow(ActionEvent actionEvent) {
		closeThisWindow();
	}

	public void editFolder(ActionEvent actionEvent) {
		folder.setFolderName(newFolderName.getText());
		folderHibernateController.editFolder(folder);
		closeThisWindow();
	}

	public void setCourseFormData(Folder folder) {
		this.folder = folder;
	}
	public void closeThisWindow(){
		Stage stage = (Stage) newFolderName.getScene().getWindow();
		stage.close();
	}
}
