package com.pirmas.laboratorinis.WebControllers;

import com.pirmas.laboratorinis.HibernateControllers.UserHibernateController;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Controller
public class WebFolderController {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseManagementSystem");
	UserHibernateController userHibController = new UserHibernateController(entityManagerFactory);


}
