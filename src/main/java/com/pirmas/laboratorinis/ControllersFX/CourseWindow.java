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
		user.addUserCourses(course);
		userHibernateController.editUser(user);
	}

	public void deleteSelected(ActionEvent actionEvent) {
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		//Course course = courseHibernateController.getCourseById(courseId);
		//Person person = userHibernateController.getPersonById(user.getId());
		//person
/*		Course course = courseHibernateController.getCourseById(courseId);
		//user.removeUserCourses(course);

		//userHibernateController.editUser(user);
		//course.removeResponsibleUsers(user);
		//courseHibernateController.editCourse(course);*/
/*		for (User user : course.getResponsibleUsers())
		{
			user.getUserCourses().remove(course);
		}
		for(Folder folder : course.getCourseFolders())
		{
			folderHibernateController.removeFolder(folder.getId());
		}
		for(int i=0; i<course.getCourseFolders().size(); i++)
		{
			course.removeCourseFolder(course.getCourseFolders().get(i));
		}*/

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
		//courseFoldersTree.getProperties().clear();


		/*for (Course myCourse : userCourses) {
			myFolders = myCourse.getCourseFolders();
			TreeItem<String> root = new TreeItem<>();
			TreeItem<String> treeItem = new TreeItem<>();
			Folder rootFolder = null;
			for (Folder folder : myFolders) {
				if (folder.getParentFolder() == null) {
					root = new TreeItem<>(folder.getFolderName());
					rootFolder=folder;
					break;
				}else {
					continue;
*//*					parentItem = new TreeItem<>(folder.getParentFolder().getFolderName());
					if (parentItem == null) {
						// in case the root item has no parent in the resultset, this is it
						root.getChildren().add(new TreeItem<>(folder.getFolderName()));
					} else {
						// add to parent treeitem
						parentItem.getChildren().add(new TreeItem<>(folder.getFolderName()));
					}*//*
				}
			}
			courseFoldersTree.setRoot(root);*/
	}

	private void displayTreeView() {
		foldersTreeView.setRoot(null);
		Map<Integer, String> itemById = new HashMap<>();
		Map<Integer, Integer> parents = new HashMap<>();
		List<Folder> myFolders;
		Folder parentFolder = null;
		TreeItem<String> root = new TreeItem<>("ROOT");
		List<Course> userCourses = courseHibernateController.getCoursesByUserId(user.getId());
		for (Course myCourse : userCourses) {
			for (Folder folder : myCourse.getCourseFolders()) {
				if (folder.getId() == folder.getParentFolder().getId()) {
					parentFolder = folder;
				}
			}
		}
		/*for (Course myCourse : userCourses) {
			for (Folder folder : myCourse.getCourseFolders()) {
				itemById.put(folder.getId(), folder.getFolderName());
				parents.put(folder.getId(), folder.getParentFolder().getId())
*//*				if (folder.getParentFolder().getId() != folder.getId()) {
					parents.put(folder.getId(), folder.getId());
				} else {
					parents.put(folder.getId(), folder.getParentFolder().getId());
				}*//*

			}
			TreeItem<String> root = new TreeItem<>("ROOT");
			for (Map.Entry<Integer, String> entry : itemById.entrySet()) {
				Integer key = entry.getKey();
				Integer parent = parents.get(key);
				if (parent.equals(key)) {
					// in case the root item points to itself, this is it
					root.getChildren().add(new TreeItem<>(entry.getValue() + " : " + entry.getKey().toString()));
				} else {
					TreeItem<String> parentItem = new TreeItem<>(entry.getValue() + " : " + entry.getKey().toString());
					// add to parent treeitem
					parentItem.getChildren().add(new TreeItem<>(entry.getValue() + " : " + entry.getKey().toString()));
				}
			}
			foldersTreeView.setRoot(root);
		}*/
		if(parentFolder!=null) {

			TreeItem<String> branch =new TreeItem<>(parentFolder.getFolderName() + " : " + parentFolder.getId());
			root.getChildren().add(branch);
			displayTreeViewV2(parentFolder, branch);
			//root.getChildren().add(branches);
			UtilityWindows.alertMessage("OK");
			foldersTreeView.setRoot(root);
		}
	}

	public void showMyCourses(ActionEvent actionEvent) {
		myCourses.getItems().clear();
		List<Course> courseDatabase = courseHibernateController.getCoursesByUserId(user.getId());
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
	}

	public void editFolder(ActionEvent actionEvent) {
		TreeItem<String> item = (TreeItem<String>) foldersTreeView.getSelectionModel().getSelectedItem();
		UtilityWindows.alertMessage(item.getValue());
	}

	public void addFolder(ActionEvent actionEvent) throws IOException {
		TreeItem<String> item = (TreeItem<String>) foldersTreeView.getSelectionModel().getSelectedItem();
		/*Folder folder =
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AddFolderForm.fxml"));
		Parent root = fxmlLoader.load();
		AddFolderForm addFolderForm = fxmlLoader.getController();
		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
		addFolderForm.setCourseFormData(user);
		Scene scene = new Scene(root);
		Stage stage = (Stage) myCourses.getScene().getWindow();
		stage.setScene(scene);
		stage.show();*/
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

//	public void showSelectedFolders(MouseEvent mouseEvent) {
//		int courseId = Integer.parseInt(myCourses.getSelectionModel().getSelectedItem().toString().split(" :")[0]);
//		Course course = courseHibernateController.getCourseById(courseId);
//
//	}

//	public void editPersonalForm(ActionEvent actionEvent) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("EditCourseForm.fxml"));
//		Parent root = fxmlLoader.load();
//		EditPersonForm editPersonForm = fxmlLoader.getController();
//		editPersonForm.changeName.setText(user.getUserName());
//		editPersonForm.changePassword.setText(user.getUserPassword());
//		editPersonForm.setPersonFormData(user);
//		Scene scene = new Scene(root);
//		Stage stage = (Stage) myCourses.getScene().getWindow();
//		stage.setScene(scene);
//		stage.show();
//	}

	private TreeItem<String> displayTreeViewV2(Folder rootFolder, TreeItem<String> root) {
		for (Folder subFolder : rootFolder.getSubFolders()) {
			if(subFolder.getId()==subFolder.getParentFolder().getId()){
				continue;
			}
			TreeItem<String> branch = new TreeItem<>(subFolder.getFolderName()+" : "+subFolder.getId());
			root.getChildren().add(branch);
			if(!subFolder.getSubFolders().isEmpty())
			{
				branch.getChildren().add(displayTreeViewV2(subFolder, branch));
			}
		}
		return root;
	}
}
