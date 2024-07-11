package fr.diginamic.spring_security.utilitaire;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import fr.diginamic.spring_security.entity.VilleTp6;

/** Classe permettant de lire le contenu d'un fichier
 *
 */
public class RecensementUtils {

	/** Lit le contenu du fichier en paramétre, contenant des données de recensement avec le format attendu,
	 * et retourne une instance de la classe Recensement avec toutes ses villes
	 * @param cheminFichier chemin d'accés au fichier sur le disque dur
	 * @return 
	 */
	public static List<VilleTp6> lire(String cheminFichier){
		List<VilleTp6> recensement = new ArrayList<>();
		
		List<String> lignes = null;
		
		try {
			File file = new File(cheminFichier);
			lignes = FileUtils.readLines(file, "UTF-8");
			
			// On supprime la ligne d'entéte avec les noms des colonnes
			lignes.remove(0);
			
			for (String ligne: lignes){
				recensement.add(ParseurVille.ajoutLigne(ligne));
			}
			return recensement;
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
}
