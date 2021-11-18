//package com.pirmas.laboratorinis.Control;
//
//import com.pirmas.laboratorinis.DataStructures.CourseManagementSystem;
//import com.pirmas.laboratorinis.DataStructures.User;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class CourseControls {
//	public static void generateCourseMenu(Scanner scanner, CourseManagementSystem courseManagementSystem, User user) {
//		//Instead of creating multiple Scanner objects, allocating the memomry for each of them, I will pass the one createn in main menu (Start.java)
//		//for start the value of cmpPr is empty the same logic as in main menu
//		String cmd = "";
//
//		//Until the user decides to leave this menu, print all the possible project commands (q will bring us back to the main menu)
//		while (!cmd.equals("q")) { //! means not, equals is a method that we can call when we have a String object. So this means if my string is equal to some value NOT (Master Yoda), then do something
//			//This is Your lab work 1 and continued. You need to implement CRUD operations
//			System.out.println("Please choose an option: \n"
//					+ "\t create a project -- c \n"
//					+ "\t view all projects -- r \n"
//					+ "\t update a project -- u \n"
//					+ "\t delete a project -- d \n"
//					+ "\t manage tasks -- t \n"
//					+ "\t return to main menu -- q \n"
//			);
//			//get the entered command
//			cmd = scanner.nextLine();
//
//			switch (cmd) {
//				case "c":
//					//It is possible to just write all code here, but a separate method would make more sense
//					addCourse(scanner, courseManagementSystem, user);
//					break;
//				case "r":
//					courseManagementSystem.getAllCourses().forEach(c -> System.out.println(c.toString()));
//                    /*this is lambda expression again. Means that:
//                    get all projects in projectMngSys, then loop and for each of the record print the project info
//                    method toString was inherited from Object class, if You go to com.pmsys.pmsys.ds.Project class You'll see it was overridden. Now when calling toString
//                    we receive not the address where that object is stored, but a meaningful and user-friendly information
//                    */
//					break;
//				case "u":
//					//A method for project update
//					updateCourse(scanner, courseManagementSystem);
//					break;
//				case "d":
//					//A method for project delete
//					deleteProject(scanner, courseManagementSystem);
//					break;
//				case "t":
//					//call a menu for task CRUD
//					break;
//				default:
//					System.out.println("Please choose a valid command.\n");
//			}
//		}
//	}
//
//	private static void deleteProject(Scanner scanner, CourseManagementSystem courseManagementSystem) {
//	}
//
//	//static -- class method, void --doesn't return a value. Equivalent to procedure
//	private static void updateCourse(Scanner scanner, CourseManagementSystem courseManagementSystem) {
//		//We need to know which project should be updated. Ask for project name
//		System.out.println("Which project would You like to update?\n");
//
//	}
//
//	private static void addCourse(Scanner scanner, CourseManagementSystem courseManagementSystem, User user) {
//		//We need scanner to read what user has entered. A project belongs to some project system
//		System.out.println("Enter project info: name;description;end date;");
//		//Read line, it is String, then split it by ;. This way we have a String array of project info
//		String[] projectInfo = scanner.nextLine().split(";");
//		//An ArrayList of users:
//		ArrayList<User> responsible = new ArrayList<>();
//		responsible.add(user);
//		//When we create an object with keyword new Class_name, we can provide initial values for variables/
//		//This is where we use constructors. They are methods with the same name as class.
//		//You can have multiple constructors with different signatures (their parameters differ)
//		//Here I call a second constructor, where I ask the user to provide name, description and project en date. Also a list of responsible users
//		//By default, the one that creates it, automatically becomes responsible
//		//com.pmsys.pmsys.ds.Project project = new com.pmsys.pmsys.ds.Project(projectInfo[0], projectInfo[1], LocalDate.parse(projectInfo[2]), responsible);
//		//add the crated project
//		//pms.getAllSysProjects().add(project);
//	}
//}
