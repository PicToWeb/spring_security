package fr.diginamic.spring_security.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import fr.diginamic.spring_security.dto.DepartementApiDTO;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.spring_security.dto.DepartementDTO;
import fr.diginamic.spring_security.dto.VilleDTO;
import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;
import fr.diginamic.spring_security.repositories.DepartementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de service utilisée pour les départements. Elle est appelée par le
 * controleur et utilise le repository pour retourner des valeurs.
 * 
 */
@Service
public class DepartementService {

	/** departementRepository */
	private final DepartementRepository departementRepository;

	/** liste */
	private List<DepartementTp6> liste = new ArrayList<>();

	private List<DepartementApiDTO>listeDepApi = new ArrayList<>();

	@Autowired
    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /**
	 * Constructeur utilisé pour charger la liste des départements présent en base
	 * de données
	 * 
	 */

//	@PostConstruct
//	public void initDonne() {
//		this.liste = extractDepartement();
//	}
//
//	@PostConstruct
//	public void initDepVille(){this.listeDepApi = extractDepApi(); }


	/**
	 * Methode utilisée pour récuper tous les départements présents en base de
	 * données
	 * 
	 * @return List<DepartementTp6>
	 */
	public List<DepartementTp6> extractDepartement() {
		Iterable<DepartementTp6> iteList = departementRepository.findAll();
		return IteratorUtils.toList(iteList.iterator());
	}

	public List<DepartementApiDTO> extractDepApi() {
		List<DepartementApiDTO>listeDepExtract= new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();
		DepartementApiDTO[] response = restTemplate.getForObject("https://geo.api.gouv.fr/departements/", DepartementApiDTO[].class);

		List<DepartementApiDTO> departementApiDTOS = new ArrayList<>();
		Collections.addAll(departementApiDTOS,response);

		for(DepartementApiDTO dep : departementApiDTOS){
			DepartementApiDTO newDep = new DepartementApiDTO(dep.getNom(), dep.getCode(),dep.getCodeRegion());
			listeDepExtract.add(newDep);
		}
		return listeDepExtract;
	}

	public String addName(String depCode) {
		DepartementApiDTO departement = listeDepApi.stream()
				.filter(n -> depCode != null && n.getCode().equals(depCode))
				.findFirst()
				.orElse(null);

		if (departement != null) {
			return departement.getNom();
		} else {
			return null;
		}
	}

	/**
	 * Méhthode qui récupère le département contenant le code de département reçu
	 * 
	 * @param codeDep
	 * @return DepartementTp6
	 */
	public DepartementTp6 extractDepCode(String codeDep) {
		return liste.stream().filter(v -> v.getCodeDep() != null && v.getCodeDep().equals(codeDep)).findFirst()
				.orElse(null);
	}

	/**
	 * Méthode qui récupère le département en base de donnée via l'id reçu
	 * 
	 * @param id
	 * @return DepartementTp6
	 */
	public DepartementTp6 findById(int id) {
		return departementRepository.findById(id).orElse(null);
	}

	/**
	 * Méthode qui récupère le département de la liste via le nom du département
	 * reçu
	 * 
	 * @param nomDep
	 * @return
	 */
	public DepartementTp6 extractDepNom(String nomDep) {
		return liste.stream().filter(v -> v.getNom() != null && v.getNom().equals(nomDep)).findFirst().orElse(null);
	}



	/**
	 * Méthode qui vérifie la présence du département en base de donnée et qui
	 * l'ajoute s'il n'existe pas
	 * 
	 * @param departement
	 */
	public void insertDepartement(DepartementTp6 departement) {
		DepartementTp6 departementEnBase = extractDepCode(departement.getCodeDep());
		if (departementEnBase == null) {
//			String nomDep = addName(departement.getCodeDep());
//			departement.setNom(nomDep);
			departementRepository.save(departement);
			liste.add(departement);
		}
	}


	/**
	 * Methode qui converti un département en DTO.
	 * 
	 * @param departement departementTp6
	 * @return DepartementDto departement
	 */
	public DepartementDTO convertirDepartementDto(DepartementTp6 departement) {
		if (departement != null) {
			return new DepartementDTO(departement.getCodeDep(), departement.getNom(), departement.getVilles().stream()
					.map(v ->new VilleDTO(v.getId(), v.getNom(),v.getNbHabitants(), v.getDepartement().getNom(),v.getDepartement().getCodeDep())).toList(),compterPopulation(departement.getVilles()));
		}
		return null;
	}

	/** 
	 * Méthode qui comptabilise le nombre d'habitants d'une liste de ville
	 * @param listeVille liste de villes
	 * @return int nombre
	 */
	public int compterPopulation(Set<VilleTp6> listeVille) {
		int tot = 0;
		for (VilleTp6 v : listeVille) {
			tot += v.getNbHabitants();
		}
		return tot;
	}
	
	/**
	 * Méthode qui modifie un département en base de donnée
	 * 
	 * @param departement département reçu
	 * @param id identifiant du département
	 */
	public void modifierDepartement(DepartementTp6 departement, int id) {
		DepartementTp6 departementEnBase = findById(id);
		if (departementEnBase != null) {
			departementEnBase.setCodeDep(departement.getCodeDep());
			departementEnBase.setNom(departement.getNom());
			departementRepository.save(departementEnBase);
		}

	}

	/**
	 * Méthode qui supprime un département en base de donnée
	 * 
	 * @param idDepartement identifiant du département
	 */
	public void supprimerDepartement(int idDepartement) {
		departementRepository.deleteById(idDepartement);
	}

}
