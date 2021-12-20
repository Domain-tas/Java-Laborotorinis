module com.pirmas.laboratorinis {
	requires javafx.controls;
	requires javafx.fxml;
	requires mysql.connector.java;
	requires org.hibernate.orm.core;
	requires java.naming;
	requires java.persistence;
	requires com.google.gson;
	//requires java.sql;
	requires spring.context;
	requires spring.web;
	requires spring.core;


	opens com.pirmas.laboratorinis to javafx.fxml;
	exports com.pirmas.laboratorinis;
	exports com.pirmas.laboratorinis.ControllersFX;
	opens com.pirmas.laboratorinis.ControllersFX to javafx.fxml;
	opens com.pirmas.laboratorinis.DataStructures to javafx.fxml, org.hibernate.orm.core, java.persistence;
	exports com.pirmas.laboratorinis.DataStructures;
	opens java.base to javafx.fxml;
	//exports com.pirmas.laboratorinis.Control;
	//opens com.pirmas.laboratorinis.Control to javafx.fxml;
//	exports com.pirmas.laboratorinis.nenaudojami;
//	opens com.pirmas.laboratorinis.nenaudojami to javafx.fxml;
	//exports com.pmsys.pmsys.otherclasses;


/*	opens com.pirmas.laboratorinis to javafx.fxml;
	exports com.pirmas.laboratorinis;*/
}