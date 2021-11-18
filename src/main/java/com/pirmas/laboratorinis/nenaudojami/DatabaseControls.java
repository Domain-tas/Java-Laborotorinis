package com.pirmas.laboratorinis.nenaudojami;

import com.pirmas.laboratorinis.DataStructures.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseControls {
	public static Connection connectToDatabase() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB_URL = "jdbc:mysql://localhost/course_management";
			String USER = "root";
			String PASS = "";
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}
		return connection;
	}
	public static void disconnectFromDatabase(Connection connection, Statement statement){
		try {
			if(connection != null && statement != null)
			{
				connection.close();
				statement.close();
			}
		} catch (Exception e){};

	}

	public static void deleteUser(int userId) {
		Connection connection = connectToDatabase();
		String sql = "DELETE FROM users where id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			preparedStatement.execute();
			disconnectFromDatabase(connection, preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addUser(){

	}

	public static void updateField(String dbColName, String value, int userId) {
		Connection connection = connectToDatabase();
		String sql = "UPDATE users SET " + dbColName + " = ? WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, userId);
			preparedStatement.execute();
			disconnectFromDatabase(connection, preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getUserByLogin(String login, Connection connection) {
		int id = 0;
		try {
			String sql = "SELECT id FROM users WHERE userName = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				id = rs.getInt(1);
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void addCourse(String title, String desc, LocalDate expectedDate, String login) {
		try {
			Connection connection = connectToDatabase();
			String sql = "INSERT INTO course (`courseName`, `courseDescription`, `dateCreated`, `endDate`) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, desc);
			preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
			preparedStatement.setDate(4, Date.valueOf(expectedDate));
			preparedStatement.execute();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int userId = generatedKeys.getInt(1);
					sql = "INSERT INTO users_course (`responsibleUsers_id`, `userCourses_id`) VALUES (?,?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, getUserByLogin(login, connection));
					preparedStatement.setInt(2, userId);
					preparedStatement.execute();
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			/*if(rs.getString(7) != null) Person person = new Person();else Company company = new Company();*/
			//return person arba company
			disconnectFromDatabase(connection, preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Course> getCoursesByUser(String login) {
		Connection connection = connectToDatabase();
		ArrayList<Course> projects = new ArrayList<>();
		String sql = "SELECT * FROM course,users_course,users WHERE course.id = users_course.userCourses_id AND users_course.responsibleUsers_id = users.id AND users.userName = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				projects.add(new Course(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5), rs.getDate(6)));
			disconnectFromDatabase(connection, preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}
}
