package fr.diginamic.spring_security.utilitaire;

import java.util.Comparator;

import fr.diginamic.spring_security.entity.EnsemblePop;

public class PopComparateur implements Comparator<EnsemblePop> {

	/**  Sens du tri: ascendant (true) ou descendant (false) */
	private boolean asc;
	
	/** Constructeur
	 * @param asc sens du tri
	 */
	public PopComparateur(boolean asc){
		this.asc=asc;
	}
	
	@Override
	public int compare(EnsemblePop o1, EnsemblePop o2) {
		if (asc){
			if (o1.getNbHabitants()>o2.getNbHabitants()){
				return 1;
			}
			else if (o1.getNbHabitants()<o2.getNbHabitants()){
				return -1;
			}
			return 0;
		}
		else {
			if (o1.getNbHabitants()<o2.getNbHabitants()){
				return 1;
			}
			else if (o1.getNbHabitants()>o2.getNbHabitants()){
				return -1;
			}
			return 0;
		}
	}

}
