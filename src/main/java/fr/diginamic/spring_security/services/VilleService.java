package fr.diginamic.spring_security.services;

import fr.diginamic.spring_security.dto.VilleDTO;
import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;
import fr.diginamic.spring_security.repositories.VilleRepository;
import fr.diginamic.spring_security.utilitaire.PopComparateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Classe de service pour les villes. Elle est appelée par le controleur et
 * communique avec la base de donnée via le repository des villes
 */
@Service
public class VilleService {

	/** villeRepository */
	@Autowired
	private VilleRepository villeRepository;

	/** departementService */
	@Autowired
	private DepartementService departementService;

	/**
	 * Méthode qui extrait toutes les villes de la base de donnée
	 * 
	 * @return Iterable<VilleTp6>
	 */
	public Iterable<VilleTp6> extractVilles() {
		return villeRepository.findAll();
//		return villes.stream().map(v -> convertirVilleDto(v)).collect(Collectors.toList());
	}

	public VilleDTO convertirVilleDto(VilleTp6 ville) {
		if (ville != null) {
			return new VilleDTO(ville.getId(), ville.getNom(),ville.getNbHabitants(), ville.getDepartement().getNom());
		}
		return null;
	}

	/**
	 * Méthode qui ajoute les 1000 villes les plus peuplées en base de donnée.
	 * 
	 * @param villeListe
	 */
	public void addDataToBase(List<VilleTp6> villeListe) {

		Collections.sort(villeListe, new PopComparateur(false));
		int limite = 1000;
		final int[] compteur = { 0 };

		villeListe.stream().limit(1007).forEach(v -> {
			if (compteur[0] < limite) {
				int result = insertVille(v);
				if (result > 0) {
					compteur[0]++;
				}
			}
		});
	}

	/**
	 * Méthode qui récupère la ville ayant l'identifiant reçu en paramètre
	 * 
	 * @param idVille
	 * @return VilleTp6
	 */
	public VilleTp6 findById(int idVille) {
		return villeRepository.findById(idVille).orElse(null);
	}

	/**
	 * Méthode qui récupère la ville ayant le même nom que celui reçu en paramètre
	 * 
	 * @param nomVille
	 * @return VilleTp6
	 */
	public VilleTp6 findByNom(String nomVille) {
		return villeRepository.findByNom(nomVille);
	}

	/**
	 * Méthode qui retourne une liste de ville commençant par la chaine de caractère
	 * reçu en paramètre
	 * 
	 * @param nomVille
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> findByNomStartingWith(String nomVille) {
		return villeRepository.findByNomStartingWith(nomVille);
	}

	/**
	 * Méthode utilisée pour lister les villes contenant un nombre d habitant
	 * minimum
	 * 
	 * @param popMin
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> findByNbHabitantsGreaterThan(int popMin) {
		return villeRepository.findByNbHabitantsGreaterThan(popMin);
	}

	/**
	 * Méthode utilisée pour lister les villes contenant un nombre d'habitant
	 * compris entre deux valeurs
	 * 
	 * @param popMin
	 * @param popMax
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> findByNbHabitantsBetween(int popMin, int popMax) {
		return villeRepository.findByNbHabitantsBetween(popMin, popMax);
	}

	/**
	 * Méthode utilisée pour lister les villes d'un département donnée ayant un
	 * nombre minimal d'habitant
	 * 
	 * @param departement
	 * @param minPopulation
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> findByDepartementAndNbHabitantsGreaterThan(DepartementTp6 departement, int minPopulation) {
		return villeRepository.findByDepartementAndNbHabitantsGreaterThan(departement, minPopulation);
	}

	/**
	 * Méthode utlisée pour lister les villes d'un département donnée ayant un
	 * nombre d'habitant compris entre deux valeurs
	 * 
	 * @param departement
	 * @param minPopulation
	 * @param maxPopulation
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> findByDepartementAndNbHabitantsBetween(DepartementTp6 departement, int minPopulation,
			int maxPopulation) {
		return villeRepository.findByDepartementAndNbHabitantsBetween(departement, minPopulation, maxPopulation);
	}

	/**
	 * Méthode utilisée pour lister les "x" villes les plus peuplées d'un
	 * département donné
	 * 
	 * @param departement
	 * @param nombre
	 * @return List<VilleTp6>
	 */
	public List<VilleTp6> TopNVillesByDepartementMaxPop(DepartementTp6 departement, int nombre) {
		return villeRepository.TopNVillesByDepartementMaxPop(departement, nombre);
	}

	/**
	 * Méthode qui insère une ville en base de donnée si celle-ci n'existe pas. Elle
	 * retourne 1 après chaque insertion afin de controler le nombre d'insertion
	 * 
	 * @param ville
	 * @return int
	 */
	public int insertVille(VilleTp6 ville) {
		int i = 0;
		VilleTp6 villeEnBase = findByNom(ville.getNom());
		if (villeEnBase == null) {
			departementService.insertDepartement(ville.getDepartement());
			ville.setDepartement(departementService.extractDepCode(ville.getDepartement().getCodeDep()));
			villeRepository.save(ville);
			i++;
		}
		return i;
	}

	/**
	 * Méthode qui permet de modifier les informations d'une ville en base de donnée
	 * @param ville
	 * @param id
	 */
	public void modifierVille(VilleTp6 ville, int id) {
		departementService.modifierDepartement(ville.getDepartement(), ville.getDepartement().getId());
		VilleTp6 villeEnBase = findById(id);
		if (villeEnBase != null) {
			villeEnBase.setNom(ville.getNom());
			villeEnBase.setNbHabitants(ville.getNbHabitants());
			villeEnBase.setDepartement(ville.getDepartement());
			villeRepository.save(villeEnBase);
		}
	}

	/**
	 * Méthode qui permet de supprimer une ville de la base de donnée
	 * @param idVille
	 */
	public void supprimerVille(int idVille) {
		villeRepository.deleteById(idVille);
	}

}
