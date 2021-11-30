package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.FolderHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AddResponsibleUser {
	public TextField courseName;
	public TextField userId;

	private Course course;

	public void setCourseFormData(Course course) {
		this.course = course;
	}

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibernateController= new UserHibernateController(entityManagerFactory);

	public void addResponsibleUser(ActionEvent actionEvent) {
		User user = userHibernateController.getPersonById(Integer.parseInt(userId.getText()));
		if(user==null){
			UtilityWindows.alertMessage("There is no user with such id");
			return;
		}
		course.addResponsibleUsers(user);
		user.addUserCourses(course);
		userHibernateController.editUser(user);
		closeThisWindow();
	}

	public void closeWindow(ActionEvent actionEvent) {
		closeThisWindow();
	}
	public void closeThisWindow(){
		Stage stage = (Stage) userId.getScene().getWindow();
		stage.close();
	}
}
