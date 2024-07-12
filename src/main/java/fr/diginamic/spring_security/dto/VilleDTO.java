package fr.diginamic.spring_security.dto;

public class VilleDTO {

	public int id;
	
	public String name;
	
	public int nbHab;
	
	public String departementName;

	public String departementCode;

	/** Constructor
	 * @param id
	 * @param name
	 * @param departementName
	 */
	public VilleDTO(int id, String name,int nbHab, String departementName,String departementCode) {
		this.id = id;
		this.name = name;
		this.nbHab=nbHab;
		this.departementName = departementName;
		this.departementCode = departementCode;
	}
	
	public VilleDTO() {
		
	}

	@Override
	public String toString() {
		return getName() + " " + getNbHab()  + " \n";
	}

	/** Getter for id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Getter for name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** Getter for nbHab
	 * @return the nbHab
	 */
	public int getNbHab() {
		return nbHab;
	}

	/** Setter for nbHab
	 * @param nbHab the nbHab to set
	 */
	public void setNbHab(int nbHab) {
		this.nbHab = nbHab;
	}

	/** Getter for departementName
	 * @return the departementName
	 */
	public String getDepartementName() {
		return departementName;
	}
	
	
	
	
}
