package com.pirmas.laboratorinis.HibernateControllers;

import com.pirmas.laboratorinis.DataStructures.Course;
import com.pirmas.laboratorinis.DataStructures.Folder;
import com.pirmas.laboratorinis.DataStructures.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FolderHibernateController {
	private EntityManagerFactory emf = null;

	public FolderHibernateController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public List<Folder> getFoldersById(int id) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery query = em.getCriteriaBuilder().createQuery();
			//query.select(query.from(Folder.class, id));
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

	public void createFolder(Folder folder){
		EntityManager em = null;
		try{
			em=getEntityManager();
			em.getTransaction().begin();
			em.persist(folder);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (em != null){
				em.close();
			}
		}
	}
	public void editFolder(Folder folder) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.merge(folder);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void removeFolder(int id) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			//Papildomai pries trinant reikia visus rysius ir priklausomybes patikrinti
			Folder folder = null;
			//Person person = null;
			try {
				folder = em.find(Folder.class, id);
				folder.getId();
			} catch (Exception e) {
				System.out.println("No such user by given Id");
			}
			Course course = folder.getCourse();
			for (Folder subFolder : folder.getEverySubfolder(folder)){
				course.getCourseFolders().remove(subFolder);
				subFolder.getSubFolders().clear();
				subFolder.getCreator().getCreatedFolders().remove(subFolder);
				subFolder.getResponsible().getMyFolders().remove(subFolder);
				subFolder.getParentFolder().getSubFolders().remove(subFolder);
				em.remove(subFolder);
			}
			course.getCourseFolders().remove(folder);
			folder.getSubFolders().clear();
			folder.getCreator().getCreatedFolders().remove(folder);
			folder.getResponsible().getMyFolders().remove(folder);
			folder.getParentFolder().getSubFolders().remove(folder);
			em.remove(folder);
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

	public List<User> getFolders() {
		return getAllFolders(true, -1, -1);
	}

	public List<User> getAllFolders(boolean all, int resMax, int resFirst) {
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

	public Folder getFolderById(int id) {
		EntityManager em = null;
		Folder folder = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			folder = em.getReference(Folder.class, id);
			folder.getId();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No such folder by given Id");
		}
		return folder;
	}

}
