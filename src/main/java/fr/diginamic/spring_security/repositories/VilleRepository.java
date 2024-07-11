package fr.diginamic.spring_security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;

/**
 * Interface utilisé pour redéfinir des méthodes du repository utilisée pour communiquer avec la base de donnée
 */
@Repository
public interface VilleRepository extends JpaRepository<VilleTp6,Integer> {

	/**
	 * Méthode utilisée pour trouver une ville via son nom
	 * @param nomVille nom de la ville
	 * @return VilleTp6
	 */
	VilleTp6 findByNom(String nomVille);
	
	 /** 
	  * Méthode utilisée pour trouver une ville qui commence par la chaine de caractère reçue en paramètre
	 * @param prefixe chaine de caractère correspondant au debut du nom d'une ville
	 * @return List<VilleTp6>
	 */
	List<VilleTp6> findByNomStartingWith(String prefixe);
	
	 /**
	  * Méthode utilisée pour lister les villes contenant un nombre d habitant minimum
	 * @param popMin
	 * @return List<VilleTp6>
	 */
	List<VilleTp6> findByNbHabitantsGreaterThan(int popMin);
	 
	 /**
	  * Méthode utilisée pour lister les villes contenant un nombre d'habitant compris entre deux valeurs
	 * @param popMin
	 * @param popMax
	 * @return List<VilleTp6>
	 */
	List<VilleTp6> findByNbHabitantsBetween(int popMin, int popMax);
	
	 /**
	  * Méthode utilisée pour lister les villes d'un département donnée ayant un nombre minimal d'habitant
	 * @param departement
	 * @param minPopulation
	 * @return List<VilleTp6>
	 */
	List<VilleTp6> findByDepartementAndNbHabitantsGreaterThan(DepartementTp6 departement, int minPopulation);
	 
	 /**
	  * Méthode utlisée pour lister les villes d'un département donnée ayant un nombre d'habitant compris entre deux valeurs
	 * @param departement
	 * @param minPopulation
	 * @param maxPopulation
	 * @return List<VilleTp6>
	 */
	List<VilleTp6> findByDepartementAndNbHabitantsBetween(DepartementTp6 departement, int minPopulation, int maxPopulation);
	 
	 	/**
		 * Méthode utilisée pour lister les "x" villes les plus peuplées d'un
		 * département donné
		 * 
		 * @param departement
		 * @param n
		 * @return les plus peuplées
		 */
	@Query("SELECT v FROM VilleTp6 v WHERE v.departement = ?1 ORDER BY v.nbHabitants DESC")
	 List<VilleTp6> TopNVillesByDepartementMaxPop(DepartementTp6 departement, int n);
	

}
