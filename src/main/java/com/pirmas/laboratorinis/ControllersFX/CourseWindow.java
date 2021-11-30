package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.*;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.FolderHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseWindow {
	public MenuItem deleteItem;
	public MenuItem editItem;
	public MenuItem addItem;
	public ListView myCourses;
	public TreeView foldersTreeView;
	public Button newCourseButton;
	public MenuItem showAllCourseMenu;
	public MenuItem addItemCourseMenu;
	public MenuItem editItemCourseMenu;
	public MenuItem deleteItemCourseMenu;
	public MenuItem showUserMyAccount;
	public MenuItem deleteSelection;
	public MenuItem editSelection;
	public MenuItem addSelection;
	public MenuItem addResponsibleUserMenu;

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
	FolderHibernateController folderHibernateController = new FolderHibernateController(entityManagerFactory);

	public Folder fetchFolders(int folderId) {
		return folderHibernateController.getFolderById(folderId);
	}

	public void newCourseForm(ActionEvent actionEvent) throws IOException {
		if (user.getPrivilege() == Privilege.USER) {
			addToMyCourses();
			return;
		}
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
		if (user.getPrivilege() == Privilege.USER) {
			newCourseButton.setVisible(false);
			addItem.setText("Add");
			editItem.setVisible(false);
			deleteItem.setVisible(false);
			addItemCourseMenu.setVisible(false);
			editItemCourseMenu.setVisible(false);
			deleteItemCourseMenu.setVisible(false);
			addSelection.setVisible(false);
			editSelection.setVisible(false);
			deleteSelection.setVisible(false);
			showUserMyAccount.setText("My account");
			myCourses.getItems().clear();
			List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
			for (Course course : courseDatabase) {
				myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
			}
		} else if (user.getPrivilege() == Privilege.EDITOR) {
			showAllCourseMenu.setVisible(false);
			showUserMyAccount.setText("My account");
			myCourses.getItems().clear();
			List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
			for (Course course : courseDatabase) {
				myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
			}
		} else if (user.getPrivilege() == Privilege.ADMIN) {
			myCourses.getItems().clear();
			List<Course> courseDatabase = courseHibernateController.getAllCourses();
			for (Course course : courseDatabase) {
				myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
			}
		}
		myCourses.getItems().clear();
		List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
		for (Course course : courseDatabase) {
			myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
		}
		displayTreeView();
	}

	public void editSelected(ActionEvent actionEvent) throws IOException {
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

	private void addToMyCourses() {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		Course course = courseHibernateController.getCourseById(courseId);
		if(user.getUserCourses().contains(course))
		{
			UtilityWindows.alertMessage("You already have this course added!");
			return;
		}
		user.addUserCourses(course);
		userHibernateController.editUser(user);
	}

	public void deleteSelected(ActionEvent actionEvent) {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		courseHibernateController.removeCourse(courseId);
		fillTables();
	}

	public void loadAllUsersForm(ActionEvent actionEvent) throws IOException, SQLException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("UserForm.fxml"));
		Parent root = fxmlLoader.load();
		UserForm userForm = fxmlLoader.getController();
		userForm.setUser(user);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void loadAllCourses(ActionEvent actionEvent) {
		myCourses.getItems().clear();
		List<Course> courseDatabase = courseHibernateController.getAllCourses();
		for (Course course : courseDatabase) {
			myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
		}
	}

	public void showMyFolders(ActionEvent actionEvent) {
		displayTreeView();
	}

	private void displayTreeView() {
		foldersTreeView.setRoot(null);
		Map<Integer, String> itemById = new HashMap<>();
		Map<Integer, Integer> parents = new HashMap<>();
		List<Folder> myFolders;
		Folder parentFolder = null;
		TreeItem<String> root = new TreeItem<>("ROOT");
		List<Course> userCourses = courseHibernateController.getCoursesByUserId(user.getId());
		if(user.getPrivilege()==Privilege.USER || user.getPrivilege()==Privilege.EDITOR){
			userCourses = userHibernateController.getUserById(user.getId()).getUserCourses();
		}
		for (Course myCourse : userCourses) {
			for (Folder folder : myCourse.getCourseFolders()) {
				if (folder.getId() == folder.getParentFolder().getId()) {
					parentFolder = folder;
				}
			}
			if(parentFolder!=null) {
				root.getChildren().add(createFolderHierarchy(parentFolder));
				foldersTreeView.setShowRoot(false);
				foldersTreeView.setRoot(root);
			}
		}
	}

	public void showMyCourses(ActionEvent actionEvent) {
		myCourses.getItems().clear();
		List<Course> courseDatabase = userHibernateController.getUserById(user.getId()).getUserCourses();
		for (Course course : courseDatabase) {
			myCourses.getItems().add(course.getId() + " : " + course.getCourseName());
		}
	}

	public void showProjectFolders(ActionEvent actionEvent) {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		Course course = courseHibernateController.getCourseById(courseId);
		//course.g
	}

	public void deleteFolder(ActionEvent actionEvent) {
		TreeItem<String> item = (TreeItem<String>) foldersTreeView.getSelectionModel().getSelectedItem();
		int folderId = Integer.parseInt(item.getValue().split(" : ")[1]);
		Folder folder = folderHibernateController.getFolderById(folderId);
		if(folder.getId()!=folder.getParentFolder().getId()){
			folderHibernateController.removeFolder(folderId);
			displayTreeView();
		}else{
			UtilityWindows.alertMessage("To delete the root folder you need to delete the course");
		}
	}

	public void editFolder(ActionEvent actionEvent) throws IOException {
		TreeItem<String> item = (TreeItem<String>) foldersTreeView.getSelectionModel().getSelectedItem();
		int folderId = Integer.parseInt(item.getValue().split(" : ")[1]);
		Folder folder = folderHibernateController.getFolderById(folderId);

		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("EditFolderForm.fxml"));
		Parent root = fxmlLoader.load();

		EditFolderForm editFolderForm = fxmlLoader.getController();
		editFolderForm.newFolderName.setText(folder.getFolderName());
		editFolderForm.setCourseFormData(folder);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		user=userHibernateController.getUserById(user.getId());
		displayTreeView();
	}

	public void addFolder(ActionEvent actionEvent) throws IOException {
		TreeItem<String> item = (TreeItem<String>) foldersTreeView.getSelectionModel().getSelectedItem();
		int folderId = Integer.parseInt(item.getValue().split(" : ")[1]);
		TreeItem<String> newFolder = new TreeItem<>("newFolder");
		item.getChildren().add(newFolder);
		CreateFolder(folderId);
		displayTreeView();
	}

	private void CreateFolder(int folderId) {
		Folder folder = folderHibernateController.getFolderById(folderId);
		Person person = userHibernateController.getPersonById(user.getId());
		Folder newFolder = new Folder("newFolder", person, user);
		newFolder.setParentFolder(folder);
		Course course = folder.getCourse();
		newFolder.setCourse(course);
		folderHibernateController.editFolder(newFolder);
	}

	private TreeItem<String> createFolderHierarchy(Folder rootFolder) {
		TreeItem<String> result = new TreeItem<>(rootFolder.getFolderName()+" : "+rootFolder.getId());
		for (Folder subFolder : rootFolder.getSubFolders()) {
			if(subFolder.getId()==subFolder.getParentFolder().getId()){
				continue;
			}
			result.getChildren().add(createFolderHierarchy(subFolder));
		}
		return result;
	}

	public void addUserResponsible(ActionEvent actionEvent) throws IOException {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		Course course = courseHibernateController.getCourseById(courseId);

		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AddResponsibleUser.fxml"));
		Parent root = fxmlLoader.load();

		AddResponsibleUser addResponsibleForm = fxmlLoader.getController();
		addResponsibleForm.courseName.setText(course.getCourseName());
		addResponsibleForm.setCourseFormData(course);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
}
