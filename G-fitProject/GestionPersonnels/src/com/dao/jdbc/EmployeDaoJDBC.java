package com.dao.jdbc;

import java.util.List;









import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;






import com.dao.EmployeDao;
import com.models.Directeur;
import com.models.Employe;

public class EmployeDaoJDBC implements EmployeDao {
	private EntityManager entityManger = Persistence.createEntityManagerFactory("GestionPersonnels").createEntityManager();
	private EntityTransaction trans = entityManger.getTransaction();
	
	public boolean creer(Employe emp) {
		TypedQuery<Employe> query = entityManger.createQuery("select e from Employe e where e.prenom ='" + emp.getPrenom()+ "'" 
															+ " and e.nom ='"+ emp.getNom() + "'", Employe.class);
		if(query.getResultList().isEmpty()){
			trans.begin();
				entityManger.persist(emp);
			trans.commit();
			
			return true;
		}
		else
			
			return false;
	}

	public void creer(Directeur dir) {
			trans.begin();
				entityManger.persist(dir);
			trans.commit();
	}
	
	public List<Employe> lister() {
			trans.begin();
				TypedQuery<Employe> query = entityManger.createQuery("SELECT e FROM Employe e", Employe.class);
			trans.commit();
			return query.getResultList();
	}
	
	
@SuppressWarnings("unchecked")
public List<Employe> research(String nom,String prenom){
	Query query;
		if(prenom == ""){
			trans.begin();
					query = entityManger.createQuery("select e from Employe e where e.nom LIKE '%" + nom + "%'");
			trans.commit();
			return query.getResultList();
		}else if(nom == ""){
			trans.begin();
					query = entityManger.createQuery("select e from Employe e where e.prenom LIKE '%" + prenom+ "%'");
			trans.commit();
			return query.getResultList();
		}
		else{
			trans.begin();
					query = entityManger.createQuery("select e from Employe e where e.prenom LIKE '%" + prenom+ "%'" + " and e.nom LIKE '%"+ nom + "%'");
			trans.commit();
			return query.getResultList();
		}
	
}

	public Employe findById(int id){
		Employe employe = entityManger.find(Employe.class, id);
		return employe;
	}
	
	public void Delete(Employe emp) {
			Employe empl = findById(emp.getId());
			trans.begin();
				entityManger.remove(empl);
			trans.commit();
	}

	public void Modifier(Employe emp) {
		trans.begin();
			entityManger.merge(emp);
		trans.commit();
	}
	public Query Verifier(String Email,String password){
		trans.begin();
			Query query = entityManger.createQuery("select e.poste from Employe e where e.email ='" + Email+ "'" + " and e.motDePasse ='"+ password + "'");
			
			trans.commit();
		if(query.getResultList().isEmpty())
			return null;
		else
			return query;
			
		}
	public boolean Verifierdirecteur(String Email,String password){
		trans.begin();
			TypedQuery<Directeur> query = entityManger.createQuery("select d from Directeur d where d.email ='" + Email+ "'" + " and d.motDePasse ='"+ password + "'",Directeur.class);
		trans.commit();
		if(query.getResultList().isEmpty())
			return false;
		else
			return true;
	}
}
