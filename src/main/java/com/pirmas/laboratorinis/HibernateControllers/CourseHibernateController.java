package com.pirmas.laboratorinis.HibernateControllers;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CourseHibernateController {
	private EntityManagerFactory emf = null;

	public CourseHibernateController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public List<Course> getCoursesByUserId(int id) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery query = em.getCriteriaBuilder().createQuery();
			query.select(query.from(Course.class));
			Query q = em.createQuery(query);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;
	}

	public void createCourse(Course course){
		EntityManager em = null;
		try{
			em=getEntityManager();
			em.getTransaction().begin();
			em.persist(course);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (em != null){
				em.close();
			}
		}
	}
	public void editCourse(Course course) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.merge(course);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void removeCourse(int id) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			//Papildomai pries trinant reikia visus rysius ir priklausomybes patikrinti
			Course course = null;
			try {
				course = em.find(Course.class, id);
				course.getId();
			} catch (Exception e) {
				System.out.println("No such user by given Id");
			}

			for (User user : course.getResponsibleUsers())
			{
				user.getUserCourses().remove(course);
			}
			for (Folder folder : course.getCourseFolders())
			{
				folder.setCourse(null);
				folder.setCreator(null);
				folder.setResponsible(null);
				folder.getSubFolders().clear();
				em.remove(folder);
				//course.removeCourseFolder(folder);
			}

/*			for(int i=0; i<course.getCourseFolders().size(); i++)
			{
				course.removeCourseFolder(course.getCourseFolders().get(i));
			}*/
			course.getCourseFolders().clear();
			//course.getCourseFolders().removeAll();
			em.remove(course);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No such user by given Id");
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Course> getAllCourses() {
		return getAllCourses(true, -1, -1);
	}

	public List<Course> getAllCourses(boolean all, int resMax, int resFirst) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery query = em.getCriteriaBuilder().createQuery();
			query.select(query.from(Course.class));
			Query q = em.createQuery(query);

			if (!all) {
				q.setMaxResults(resMax);
				q.setFirstResult(resFirst);
			}

			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return null;
	}

	public Course getCourseById(int id) {
		EntityManager em = null;
		Course course = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			course = em.getReference(Course.class, id);
			course.getId();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such course by given Id");
		}
		return course;
	}

}
