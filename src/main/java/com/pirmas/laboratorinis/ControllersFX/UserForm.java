package com.pirmas.laboratorinis.ControllersFX;

import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.CompanyHibernateController;
import com.pirmas.laboratorinis.HibernateControllers.PersonHibernateController;
import com.pirmas.laboratorinis.nenaudojami.DatabaseControls;
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
	public TableView usersTable;
	public TableColumn<UserTableParameters, Void> dummyField;
	public TableColumn colPrivilege;

	private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

	private User user;
	private Person person;
	private Company company;

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibController = new UserHibernateController(entityManagerFactory);
	PersonHibernateController personHibController = new PersonHibernateController(entityManagerFactory);
	CompanyHibernateController companyHibController = new CompanyHibernateController(entityManagerFactory);


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usersTable.setEditable(true);
		colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		colCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		colModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
		colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		colLogin.setCellFactory(TextFieldTableCell.forTableColumn());
		colLogin.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setLogin(t.getNewValue());
					//Update e record on change
					user=userHibController.getUserById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
					user.setUserName(t.getTableView().getItems().get(t.getTablePosition().getRow()).getLogin());
					userHibController.editUser(user);
//					DatabaseControls.updateField("login", t.getTableView().getItems().get(
//							t.getTablePosition().getRow()).getLogin(), t.getTableView().getItems().get(
//							t.getTablePosition().getRow()).getUserId());
				}
		);
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colName.setCellFactory(TextFieldTableCell.forTableColumn());
		colName.setOnEditCommit(
				t -> {
					String newName = t.getNewValue();
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setName(newName);
					//Update in db
					person=personHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
					person.setPersonName(newName);
					userHibController.editUser(person);
//					DatabaseControls.updateField("`person_name`", newName, t.getTableView().getItems().get(
//							t.getTablePosition().getRow()).getUserId());
				}
		);
		colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
		colSurname.setOnEditCommit(
				t -> {
					String newSurname = t.getNewValue();
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setSurname(t.getNewValue());
					//Update db
					person=personHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
					person.setPersonName(newSurname);
					userHibController.editUser(person);
//					DatabaseControls.updateField("`person_surname`", newSurname, t.getTableView().getItems().get(
//							t.getTablePosition().getRow()).getUserId());
					}
		);
		colPos.setCellValueFactory(new PropertyValueFactory<>("position"));
		colPos.setCellFactory(TextFieldTableCell.forTableColumn());
		colPos.setOnEditCommit(
				t -> {
					t.getTableView().getItems().get(
							t.getTablePosition().getRow()).setPosition(t.getNewValue());
					person=personHibController.getPersonById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
					person.setPersonName(t.getTableView().getItems().get(t.getTablePosition().getRow()).getPosition());
					userHibController.editUser(person);
				}

		);
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		colEmail.setOnEditCommit(
				t -> t.getTableView().getItems().get(
						t.getTablePosition().getRow()).setEmail(t.getNewValue())
		);
		colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
		colCompany.setCellFactory(TextFieldTableCell.forTableColumn());
		colCompany.setOnEditCommit(
				t -> t.getTableView().getItems().get(
						t.getTablePosition().getRow()).setCompany(t.getNewValue())
		);
		colRep.setCellValueFactory(new PropertyValueFactory<>("representative"));
		colRep.setCellFactory(TextFieldTableCell.forTableColumn());
		colRep.setOnEditCommit(
				t -> t.getTableView().getItems().get(
						t.getTablePosition().getRow()).setRepresentative(t.getNewValue())
		);
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
		colAddress.setOnEditCommit(
				t -> t.getTableView().getItems().get(
						t.getTablePosition().getRow()).setAddress(t.getNewValue())
		);

		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		colPhone.setOnEditCommit(
				t -> t.getTableView().getItems().get(
						t.getTablePosition().getRow()).setPhone(t.getNewValue())
		);

		Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> cellFactory = new Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>>() {
			@Override
			public TableCell<UserTableParameters, Void> call(final TableColumn<UserTableParameters, Void> param) {
				final TableCell<UserTableParameters, Void> cell = new TableCell<UserTableParameters, Void>() {

					private final Button button = new Button("Delete");

					{
						button.setOnAction((ActionEvent event) -> {
							UserTableParameters data = getTableView().getItems().get(getIndex());
							//Delete user by id
							//DatabaseControls.deleteUser(data.getUserId());
							//Hibernate version:
							userHibController.removeUser(data.getUserId());

							try {
								//Immediately after delete, update info in table
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
							setGraphic(button);
						}
					}
				};
				return cell;
			}
		};

		dummyField.setCellFactory(cellFactory);

		try {
			loadUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadUsers() throws SQLException {
		usersTable.setEditable(true);
		usersTable.getItems().clear();
		List<User> users = userHibController.getAllUsers();
		for (User user: users) {
			person=(Person)user;
			UserTableParameters userTableParameters = new UserTableParameters();
			userTableParameters.setUserId(user.getId());
			userTableParameters.setLogin(user.getUserName());
			userTableParameters.setDateCreated(user.getDateCreated().toString());
			userTableParameters.setDateModified(user.getDateModified().toString());
			userTableParameters.setName(person.getPersonName());
			userTableParameters.setSurname(person.getPersonSurname());
			userTableParameters.setPosition(person.getPersonPosition());
			userTableParameters.setEmail(person.getPersonEmail());
//			userTableParameters.setCompany(rs.getString(11));
//			userTableParameters.setRepresentative(rs.getString(12));
//			userTableParameters.setAddress(rs.getString(13));
//			userTableParameters.setPhone(rs.getString(14));
			data.add(userTableParameters);
		}
		usersTable.setItems(data);
		/*connection = DatabaseControls.connectToDatabase();
		String sql = "SELECT * FROM users AS u WHERE u.is_active = true";
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			UserTableParameters userTableParameters = new UserTableParameters();
			userTableParameters.setUserId(rs.getInt(1));
			userTableParameters.setLogin(rs.getString(2));
			userTableParameters.setDateCreated(rs.getDate(4).toString());
			userTableParameters.setDateModified(rs.getDate(5).toString());
			userTableParameters.setName(rs.getString(7));
			userTableParameters.setSurname(rs.getString(8));
			userTableParameters.setPosition(rs.getString(9));
			userTableParameters.setEmail(rs.getString(10));
			userTableParameters.setCompany(rs.getString(11));
			userTableParameters.setRepresentative(rs.getString(12));
			userTableParameters.setAddress(rs.getString(13));
			userTableParameters.setPhone(rs.getString(14));
			data.add(userTableParameters);
		}

		usersTable.setItems(data);

		DatabaseControls.disconnectFromDatabase(connection, statement);*/
	}
}
