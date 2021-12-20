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

	@RequestMapping(value = "/user/userLogin", method = RequestMethod.POST)
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
		if (data.getProperty("DTYPE").equals("Person")) {
			person = (Person) userHibController.getUserByLogin(data.getProperty("userName"), data.getProperty("userPassword"));
			gson.registerTypeAdapter(Person.class, new PersonGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
		} else if (data.getProperty("DTYPE").equals("Company")) {
			company = (Company) userHibController.getUserByLogin(data.getProperty("userName"), data.getProperty("userPassword"));
			gson.registerTypeAdapter(Company.class, new CompanyGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
		}

		if (person == null && company == null) {
			return "Wrong credentials or no such user";
		}


		Gson builder = gson.create();
		return person != null ? builder.toJson(person) : builder.toJson(company);


	}

	@RequestMapping(value = "/user/createPerson", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String createPerson(@RequestBody String request) {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(User.class, new PersonGsonSerializer());
		Gson parser = gson.create();
		Properties data = parser.fromJson(request, Properties.class);
		userHibController.createUser(new Person(data.getProperty("personName"), data.getProperty("personSurname"), data.getProperty("personEmail"), data.getProperty("personPosition"), data.getProperty("userName"), data.getProperty("userPassword")));
		Person person = (Person) userHibController.getUserByLogin(data.getProperty("userName"), data.getProperty("userPassword"));
		if (person == null) {
			return "User not created";
		}
		return parser.toJson(person);
	}

	@RequestMapping(value = "/user/getUser/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getUser(@PathVariable(name = "id") String id) {
		int intId = Integer.parseInt(id);
		User user = userHibController.getUserById(intId);
		Person person = null;
		Company company = null;
		GsonBuilder gson = new GsonBuilder();
		if (user.getClass() == Person.class) {
			person = (Person) userHibController.getUserById(Integer.parseInt(id));
			gson.registerTypeAdapter(Person.class, new PersonGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()); // registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).
		} else if (user.getClass() == Company.class){
			company = (Company) userHibController.getUserById(Integer.parseInt(id));
			gson.registerTypeAdapter(Company.class, new CompanyGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
		}
		Gson parser = gson.create();

		/*if (user == null) {
			return "No user by given ID";
		}*/
		if (person == null && company == null) {
			return "No user by given ID";
		}
		//return parser.toJson(person);
		return person != null ? parser.toJson(person) : parser.toJson(company);
	}

	@RequestMapping(value = "/user/updateInfo/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String updateUser(@RequestBody String request, @PathVariable(name = "id") String id) {
		Gson parser = new Gson();
		User user = userHibController.getUserById(Integer.parseInt(id));
		Person person = null;
		Company company = null;
		Properties data = parser.fromJson(request, Properties.class);
		if (user.getClass() == Person.class) {
			person = (Person) userHibController.getUserById(Integer.parseInt(id));
			person.setPersonSurname(data.getProperty("personSurname"));
			person.setPersonEmail(data.getProperty("personEmail"));
			person.setPersonPosition(data.getProperty("personPosition"));
			person.setPersonName(data.getProperty("personName"));
		} else if (user.getClass() == Company.class) {
			company = (Company) userHibController.getUserById(Integer.parseInt(id));
			company.setCompanyName(data.getProperty("companyName"));
			company.setCompanyPhoneNumber(data.getProperty("companyPhoneNumber"));
			company.setCompanyRepresentative(data.getProperty("companyRepresentative"));
			company.setCompanyAddress(data.getProperty("companyAddress"));
		}

		/*if (person != null || company != null) {
			try {
				userHibController.editUser(user);
			} catch (Exception e) {
				return "Error while updating";
			}
		}*/
		if (person == null && company == null) {
			return "No user by given ID";
		}
		if (person != null) {
			userHibController.editUser(person);
		} else {
			userHibController.editUser(company);
		}
		return "Update successful";
	}

	@RequestMapping(value = "/user/deleteUser/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String updatePerson(@PathVariable(name = "id") int id) {
		userHibController.removeUser(id);
		User user = userHibController.getUserById(id);
		if (user == null) {
			return "Success";
		}else{
			return "User deletion was unsuccessful";
		}

	}
}
