package fr.diginamic.spring_security.utilitaire;

import fr.diginamic.spring_security.entity.DepartementTp6;
import fr.diginamic.spring_security.entity.VilleTp6;

/** Permet de constituer notre recensement progressivement é partir des lignes du fichier
 * @author DIGINAMIC
 *
 */
public class ParseurVille {
	
	/** Ajoute une ligne représentant une ville au recensement
	 * @param ligne ligne de laquelle on extrait une ville
	 */
	public static VilleTp6 ajoutLigne(String ligne){
		
		String[] column = ligne.split(";");
		
		
		String nomRegion = "";
		String codeDepartement = column[2].trim();
		String nomCommune = column[6];
		String population = column[9];
		int populationTotale = Integer.parseInt(population.replace(" ", "").trim());
		
		// On cree maintenant la ville avec toutes ses données
		VilleTp6 ville = new VilleTp6(nomCommune, populationTotale);
		ville.setDepartement(new DepartementTp6(codeDepartement,nomRegion));
		
		return ville;
		
	}

}
