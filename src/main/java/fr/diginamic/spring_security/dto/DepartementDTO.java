package fr.diginamic.spring_security.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartementDTO {

	private String codeDep;
	private String nom;
	private int nbHabitants;
	private List<VilleDTO> villes = new ArrayList<>();

	/**
	 *
	 * @param codeDep
	 * @param nom
	 * @param villes
	 * @param nbHabitants
	 */
	public DepartementDTO(String codeDep, String nom, List<VilleDTO> villes, int nbHabitants) {
		super();
		this.codeDep = codeDep;
		this.nom = nom;
		this.villes = villes;
		this.nbHabitants = nbHabitants;
	}

	@Override
	public String toString() {
		return codeDep + " " + nom + " " + nbHabitants + " " + villes + " \n ";
	}

	/**
	 * Getter for villes
	 * 
	 * @return the villes
	 */
	public List<VilleDTO> getVilles() {
		return villes;
	}
	
	

	/** Getter for nbHabitants
	 * @return the nbHabitants
	 */
	public int getNbHabitants() {
		return nbHabitants;
	}

	/**
	 * Setter for villes
	 * 
	 * @param villes the villes to set
	 */
	public void setVilles(List<VilleDTO> villes) {
		this.villes = villes;
	}

	/**
	 * Getter for id
	 * 
	 * @return the id
	 */
	public String getCodeDep() {
		return codeDep;
	}

	/**
	 * Getter for nom
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

}
