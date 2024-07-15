package fr.diginamic.spring_security.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="ville")
public class VilleTp6 implements EnsemblePop {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** nom */
	@NotNull
	@Size(min=2,max=255,message="Le nom de la ville doit être supérieur à 2 caractères")
	private String nom;
	
	/** nbHabitants */
	@Min(value=10,message="Le nombre d'habitants doit être égal ou supérieur à 10")
	private int nbHabitants;
	
	@ManyToOne
	@JoinColumn(name="id_dep")
	@JsonBackReference
	private DepartementTp6 departement;
	
	
	
	/** Constructor
	 * 
	 */
	public VilleTp6() {	}

	/** Constructor
	 * @param nom nom de la ville
	 * @param nbHabitants nombre d'habitants
	 */
	public VilleTp6(String nom, int nbHabitants) {
		this.nom = nom;
		this.nbHabitants = nbHabitants;
	}

	public VilleTp6(String nom, int nbHabitants, DepartementTp6 departement) {
		this.nom = nom;
		this.nbHabitants = nbHabitants;
		this.departement = departement;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VilleTp6 other = (VilleTp6) obj;
		return  Objects.equals(nom, other.nom);
	}
	
	

	@Override
	public String toString() {
		return  getId() + " " + getNom() + " " + getNbHabitants()  +  getDepartement().getNom() + " \n";
	}

	/** Getter for id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Getter for nom
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/** Setter for nom
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/** Getter for nbHabitants
	 * @return the nbHabitants
	 */
	@Override
	public int getNbHabitants() {
		return nbHabitants;
	}
	/** Setter for nbHabitants
	 * @param nbHabitants the nbHabitants to set
	 */
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	/** Getter for departement
	 * @return the departement
	 */
	public DepartementTp6 getDepartement() {
		return departement;
	}

	/** Setter for departement
	 * @param departement the departement to set
	 */
	public void setDepartement(DepartementTp6 departement) {
		this.departement = departement;
	}

	
	
	

}
