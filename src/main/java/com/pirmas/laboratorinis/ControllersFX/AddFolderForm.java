package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.PersonHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AddFolderForm {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibernateController = new UserHibernateController(entityManagerFactory);

	private User user;
	private Folder folder;

	public TextField newFolderName;

	public void closeWindow(ActionEvent actionEvent) {
		Stage stage = (Stage) newFolderName.getScene().getWindow();
		stage.close();
	}

	public void createNewFolder(ActionEvent actionEvent) {
		Person person = userHibernateController.getPersonById(user.getId());
		Folder newFolder = new Folder(newFolderName.getText(), person, user);
		//newFolder.setParentFolder();
	}

	public void setCourseFormData(User user, Folder folder) {
		this.user=user;
		this.folder=folder;
	}
}
