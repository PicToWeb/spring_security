package fr.diginamic.spring_security.dao;


import org.springframework.stereotype.Service;

@Service
public class DepartementDao {
	
//	@PersistenceContext
//	private EntityManager em;
//	
//	@Transactional
//	public void persistDepartement(DepartementTp6 departement) {
//		em.persist(departement);
//	}
	
//	public List<DepartementTp6> extractDepartement(){
//		TypedQuery<DepartementTp6> query = em.createQuery("SELECT d FROM DepartementTp6 d",DepartementTp6.class);
//		return query.getResultList();
//	}
//	
//	public DepartementTp6 extractDepartementId(String id) {
//		TypedQuery<DepartementTp6> query = em.createQuery("SELECT d FROM DepartementTp6 d WHERE d.id=:id",DepartementTp6.class);
//		 query.setParameter("id",id );
//		 List<DepartementTp6> departements = query.getResultList();
//		 if(departements.size()>0) {
//			 return query.getSingleResult();
//		 }
//		 return null;
//	}
//	
//	@Transactional
//	public void modifierDepartement(String idDep,DepartementTp6 depModifiee){
//		DepartementTp6 depFromDb = em.find(DepartementTp6.class, idDep);
//		depFromDb.setNom(depModifiee.getNom());
//	}
//	
//	@Transactional
//	public DepartementTp6 FindDep(DepartementTp6 dep){
//		return em.find(DepartementTp6.class, dep.getId());	
//	}
//	
//	@Transactional
//	public void supprimerDepartement(String idDep) {
//		DepartementTp6 depFromDb = em.find(DepartementTp6.class, idDep);
//		em.remove(depFromDb);
//	}
}
