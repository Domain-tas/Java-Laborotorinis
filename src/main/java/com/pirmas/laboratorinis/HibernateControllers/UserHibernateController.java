package com.pirmas.laboratorinis.HibernateControllers;

import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserHibernateController {
	private EntityManagerFactory emf = null;

	public UserHibernateController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public void createUser(User user){
		EntityManager em = null;
		try{
			em=getEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (em != null){
				em.close();
			}
		}
	}
	public void editUser(User user) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void removeUser(int id) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			//Papildomai pries trinant reikia visus rysius ir priklausomybes patikrinti
			User user = null;
			try {
				user = em.getReference(User.class, id);
				user.getId();
			} catch (Exception e) {
				System.out.println("No such user by given Id");
			}
			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<User> getAllUsers() {
		return getAllUsers(true, -1, -1);
	}

	public List<User> getAllUsers(boolean all, int resMax, int resFirst) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery query = em.getCriteriaBuilder().createQuery();
			query.select(query.from(User.class));
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

	public User getUserById(int id) {
		EntityManager em = null;
		User user = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			user = em.getReference(User.class, id);
			user.getId();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such user by given Id");
		}
		return user;
	}

	public User getUserByLogin(String userName, String userPassword) {
		EntityManager em = null;
		User user = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			CriteriaQuery query = em.getCriteriaBuilder().createQuery();
			Root<User> from = query.from(User.class);
			query.select(from);
			query.where(
					em.getCriteriaBuilder().equal(from.get("userName"), userName ),
					em.getCriteriaBuilder().equal(from.get("userPassword"), userPassword)
			);
			Query q = em.createQuery(query);
			return (User)q.getResultList().get(0);
		} catch (Exception e) {
			System.out.println("User name or password was incorrect");
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return user;
	}
	public Person getPersonById(int id) {
		EntityManager em = null;
		Person person = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			person = em.getReference(Person.class, id);
			person.getId();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such user by given Id");
		}
		return person;
	}
	public Company getCompanyById(int id) {
		EntityManager em = null;
		Company company = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			company = em.getReference(Company.class, id);
			company.getId();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such company by given Id");
		}
		return company;
	}

}
