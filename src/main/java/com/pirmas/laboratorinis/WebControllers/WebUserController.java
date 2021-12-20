package com.pirmas.laboratorinis.WebControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import com.pirmas.laboratorinis.Utilities.CompanyGsonSerializer;
import com.pirmas.laboratorinis.Utilities.LocalDateGsonSerializer;
import com.pirmas.laboratorinis.Utilities.PersonGsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Properties;


import org.springframework.stereotype.Controller;


@Controller
public class WebUserController {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibController = new UserHibernateController(entityManagerFactory);

	@RequestMapping(value = "/users/userLogin", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String login(@RequestBody String request) {

		Gson parser = new Gson();
		Properties data = parser.fromJson(request, Properties.class);
		GsonBuilder gson = new GsonBuilder();
		Person person = null;
		Company company = null;
        /*
        { "type":"C"
        "login":
        "psw": }
         */
		if (data.getProperty("type").equals("P")) {
			person = (Person) userHibController.getUserByLogin(data.getProperty("login"), data.getProperty("password"));
			gson.registerTypeAdapter(Person.class, new PersonGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
		} else if (data.getProperty("type").equals("C")) {
			company = (Company) userHibController.getUserByLogin(data.getProperty("login"), data.getProperty("password"));
			gson.registerTypeAdapter(Company.class, new CompanyGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
		}

		if (person == null && company == null) {
			return "Wrong credentials or no such user";
		}


		Gson builder = gson.create();
		return person != null ? builder.toJson(person) : builder.toJson(company);


	}

	@RequestMapping(value = "/users/createPerson", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String createPerson(@RequestBody String request) {

		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(User.class, new PersonGsonSerializer());
		Gson parser = gson.create();
		Properties data = parser.fromJson(request, Properties.class);
		userHibController.createUser(new Person(data.getProperty("login"), data.getProperty("password"), data.getProperty("name"), data.getProperty("surname"), data.getProperty("email"), data.getProperty("position")));
		User user = userHibController.getUserByLogin(data.getProperty("login"), data.getProperty("password"));
		if (user == null) {
			return "User not created";
		}
		return parser.toJson(user);
	}

	@RequestMapping(value = "/users/getUser/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getUser(@PathVariable(name = "id") String id) {

		User user = userHibController.getUserById(Integer.parseInt(id));
		GsonBuilder gson = new GsonBuilder();
		if (user.getClass() == Person.class) {
			gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Person.class, new PersonGsonSerializer());
		} else if (user.getClass() == Company.class)
			gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Company.class, new CompanyGsonSerializer());
		Gson parser = gson.create();

		if (user == null) {
			return "No user by given ID";
		}
		return parser.toJson(user);
	}

   /* @RequestMapping(value = "/user/updateInfo", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String surname = data.getProperty("surname");
        String email = data.getProperty("email");
        String phone = data.getProperty("phone");
        String userId = data.getProperty("id");

        Employee employee = employeeHibernateControl.findEmployee(Integer.parseInt(userId));

        if (employee != null) {
            employee.setSurname(surname);
            employee.setEmail(email);
            employee.setPhoneNumber(phone);
            try {
                employeeHibernateControl.edit(employee);
            } catch (Exception e) {
                return "Error while updating";
            }
        }
        return "Update successful";
    }*/
}
