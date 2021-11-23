package com.pirmas.laboratorinis.HibernateControllers;

import com.pirmas.laboratorinis.DataStructures.Company;
import com.pirmas.laboratorinis.DataStructures.Person;
import com.pirmas.laboratorinis.DataStructures.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class CompanyHibernateController {
	private EntityManagerFactory emf = null;

	public CompanyHibernateController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public void createCompany(Company company){
		EntityManager em = null;
		try{
			em=getEntityManager();
			em.getTransaction().begin();
			em.persist(company);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (em != null){
				em.close();
			}
		}
	}
	public void editCompany(Company company) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.merge(company);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void removeCompany(int id) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			//Papildomai pries trinant reikia visus rysius ir priklausomybes patikrinti
			Company company = null;
			try {
				company = em.getReference(Company.class, id);
				company.getId();
			} catch (Exception e) {
				System.out.println("No such company by given Id");
			}
			em.remove(company);
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
		return getAllUsers(false, -1, -1);
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

	public Company getCompanyById(int id) {
		EntityManager em = null;
		Company company = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			company = em.getReference(Company.class, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such company by given Id");
		}
		return company;
	}

}
