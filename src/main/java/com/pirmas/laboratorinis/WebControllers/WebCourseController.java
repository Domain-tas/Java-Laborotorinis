package com.pirmas.laboratorinis.WebControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.HibernateControllers.CourseHibernateController;
import com.pirmas.laboratorinis.Utilities.CourseGsonSerializer;
import com.pirmas.laboratorinis.Utilities.CourseListGsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.List;

@Controller
public class WebCourseController {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	CourseHibernateController courseHibController = new CourseHibernateController(entityManagerFactory);

	@RequestMapping(value = "/course/getAll", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getAllCourses() {

		List<Course> allCourses = courseHibController.getAllCourses();

		GsonBuilder gson = new GsonBuilder();
		Type courseList = new TypeToken<List<Course>>() {
		}.getType();
		gson.registerTypeAdapter(Course.class, new CourseGsonSerializer()).registerTypeAdapter(courseList, new CourseListGsonSerializer());
		Gson parser = gson.create();
		return parser.toJson(allCourses);
	}

	@RequestMapping(value = "/course/getAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getAllProjectsByUser(@PathVariable(name = "id") int id) {

		List<Course> allCourses = courseHibController.getCoursesByUserId(id);

		GsonBuilder gson = new GsonBuilder();
		Type courseList = new TypeToken<List<Course>>() {
		}.getType();
		gson.registerTypeAdapter(Course.class, new CourseGsonSerializer()).registerTypeAdapter(courseList, new CourseListGsonSerializer());
		Gson parser = gson.create();
		return parser.toJson(allCourses);
	}

	@RequestMapping(value = "/course/deleteCourse/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String deleteProject(@PathVariable(name = "id") String id) {

		courseHibController.removeCourse(Integer.parseInt(id));

		Course course = courseHibController.getCourseById(Integer.parseInt(id));
		if (course == null) {
			return "Success";
		} else {
			return "Not deleted";
		}

	}
}
