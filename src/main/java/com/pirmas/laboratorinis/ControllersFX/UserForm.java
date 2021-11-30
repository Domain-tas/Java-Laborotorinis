package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.Privilege;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserForm implements Initializable {
	public TableColumn<UserTableParameters, Integer> colId;
	public TableColumn<UserTableParameters, String> colLogin;
	public TableColumn<UserTableParameters, String> colCreated;
	public TableColumn<UserTableParameters, String> colModified;
	public TableColumn<UserTableParameters, String> colName;
	public TableColumn<UserTableParameters, String> colSurname;
	public TableColumn<UserTableParameters, String> colPos;
	public TableColumn<UserTableParameters, String> colEmail;
	public TableColumn<UserTableParameters, String> colCompany;
	public TableColumn<UserTableParameters, String> colRep;
	public TableColumn<UserTableParameters, String> colPhone;
	public TableColumn<UserTableParameters, String> colAddress;
	public TableColumn<UserTableParameters, String> colPrivilege;
	public TableView usersTable;
	public TableColumn<UserTableParameters, Void> deleteAction;
	public TableColumn<UserTableParameters, Void> addAction;


	private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

	private User user;

	public void setUser(User user) throws SQLException {
		this.user = user;
		loadUsers();
	}


	private Person person;
	private Company company;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibController = new UserHibernateController(entityManagerFactory);

	@Override
	public void initialize(URL location, ResourceBundle resources) { //URL location, ResourceBundle resources
		usersTable.setEditable(true);
		colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		colCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		colModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
		colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		colLogin.setCellFactory(TextFieldTableCell.forTableColumn());
		colLogin.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setLogin(t.getNewValue());
						User user = userHibController.getUserById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						user.setUserName(t.getTableView().getItems().get(t.getTablePosition().getRow()).getLogin());
						userHibController.editUser(user);
					}

				}
		);
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colName.setCellFactory(TextFieldTableCell.forTableColumn());
		colName.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						String newName = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(newName);
						person = userHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						person.setPersonName(newName);
						userHibController.editUser(person);
					}

				}
		);
		colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
		colSurname.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getSurname().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setSurname("null");
							return;
						}
						String newSurname = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setSurname(t.getNewValue());
						person = userHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						person.setPersonSurname(newSurname);
						userHibController.editUser(person);
					}
				}
		);
		colPos.setCellValueFactory(new PropertyValueFactory<>("position"));
		colPos.setCellFactory(TextFieldTableCell.forTableColumn());
		colPos.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getPosition().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setPosition("null");
							return;
						}
						String newPosition = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setPosition(t.getNewValue());
						person = userHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						person.setPersonPosition(newPosition);
						userHibController.editUser(person);
					}
				}

		);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		colEmail.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getEmail().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail("null");
							return;
						}
						String newEmail = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
						person = userHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						person.setPersonEmail(newEmail);
						userHibController.editUser(person);
					}
				}
		);
		colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
		colCompany.setCellFactory(TextFieldTableCell.forTableColumn());
		colCompany.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getCompany().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setCompany("null");
							return;
						}
						String newCompany = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setCompany(t.getNewValue());
						company = userHibController.getCompanyById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						company.setCompanyName(newCompany);
						userHibController.editUser(company);
					}
				}
		);
		colRep.setCellValueFactory(new PropertyValueFactory<>("representative"));
		colRep.setCellFactory(TextFieldTableCell.forTableColumn());
		colRep.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getRepresentative().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setRepresentative("null");
							return;
						}
						String newRepresentative = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setRepresentative(t.getNewValue());
						company = userHibController.getCompanyById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						company.setCompanyRepresentative(newRepresentative);
						userHibController.editUser(company);
					}
				}
		);
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
		colAddress.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getAddress().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setAddress("null");
							return;
						}
						String newAddress = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setAddress(t.getNewValue());
						company = userHibController.getCompanyById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						company.setCompanyAddress(newAddress);
						userHibController.editUser(company);
					}
				}
		);

		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		colPhone.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone().equals("null")) {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone("null");
							return;
						}
						String newPhone = t.getNewValue();
						t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone(t.getNewValue());
						company = userHibController.getCompanyById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
						company.setCompanyPhoneNumber(newPhone);
						userHibController.editUser(company);
					}
				}
		);
		colPrivilege.setCellValueFactory(new PropertyValueFactory<>("privilege"));
		colPrivilege.setCellFactory(TextFieldTableCell.forTableColumn());
		colPrivilege.setOnEditCommit(
				t -> {
					if (user.getId() == t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId() || user.getPrivilege() == Privilege.ADMIN) {
						String oldPrivilege = t.getOldValue();
						if (user.getPrivilege() == Privilege.ADMIN) {
							String privilege = t.getNewValue();
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrivilege(t.getNewValue());
							User user = userHibController.getUserById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
							company = userHibController.getCompanyById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
							if (company != null) {
								user.setPrivilege(Privilege.valueOf(privilege));
								userHibController.editUser(user);
							} else {
								UtilityWindows.alertMessage("Company privilege cannot be elevated");
							}
						} else {
							t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrivilege(oldPrivilege);
							UtilityWindows.alertMessage("You have no rights to alter user privilege");
							return;
						}
					}
				}
		);

		Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> cellFactory = new Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>>() {
			@Override
			public TableCell<UserTableParameters, Void> call(final TableColumn<UserTableParameters, Void> param) {
				final TableCell<UserTableParameters, Void> cell = new TableCell<UserTableParameters, Void>() {
					private final Button deleteButton = new Button("Delete");

					{
						deleteButton.setOnAction((ActionEvent event) -> {
							UserTableParameters data = getTableView().getItems().get(getIndex());
							if (user.getId() == data.getUserId()) {
								UtilityWindows.alertMessage("Suicide is not allowed");
								return;
							}
							userHibController.removeUser(data.getUserId());
							try {
								loadUsers();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(deleteButton);
						}
					}
				};
				return cell;
			}

		};

		deleteAction.setCellFactory(cellFactory);
		try {
			loadUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadUsers() throws SQLException {
		if (user == null) {
			return;
		}
		usersTable.setEditable(true);
		usersTable.getItems().clear();
		List<User> users = userHibController.getAllUsers();
		for (User loadedUser : users) {
			if (user.getPrivilege() != Privilege.ADMIN && user.getPrivilege() != Privilege.EDITOR) {
				if (loadedUser.getId() != user.getId())
					continue;
			}
			if (user.getPrivilege() == Privilege.EDITOR) {
				if (loadedUser.getPrivilege() == Privilege.USER)
					continue;
			}
			UserTableParameters userTableParameters = new UserTableParameters();
			userTableParameters.setUserId(loadedUser.getId());
			userTableParameters.setPrivilege(loadedUser.getPrivilege().toString());
			userTableParameters.setLogin(loadedUser.getUserName());
			userTableParameters.setDateCreated(loadedUser.getDateCreated().toString());
			userTableParameters.setDateModified(loadedUser.getDateModified().toString());
			if (loadedUser.getDtype().equals("Person")) {
				person = userHibController.getPersonById(loadedUser.getId());
				userTableParameters.setName(person.getPersonName());
				userTableParameters.setSurname(person.getPersonSurname());
				userTableParameters.setPosition(person.getPersonPosition());
				userTableParameters.setEmail(person.getPersonEmail());
				userTableParameters.setCompany("null");
				userTableParameters.setRepresentative("null");
				userTableParameters.setAddress("null");
				userTableParameters.setPhone("null");
			} else {
				company = userHibController.getCompanyById(loadedUser.getId());
				userTableParameters.setName("null");
				userTableParameters.setSurname("null");
				userTableParameters.setPosition("null");
				userTableParameters.setEmail("null");
				userTableParameters.setCompany(company.getCompanyName());
				userTableParameters.setRepresentative(company.getCompanyRepresentative());
				userTableParameters.setAddress(company.getCompanyAddress());
				userTableParameters.setPhone(company.getCompanyPhoneNumber());
			}
			data.add(userTableParameters);
		}
		usersTable.setItems(data);
	}
}
