package fr.esgi.fx.travel.service;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Compagnie;

public interface CompagnieService {

	Compagnie ajouterCompagnie(String nom);
	
	/**
	 * Cette méthode renvoie la compagnie dont l'id est donné en paramètre
	 * 
	 * @param id de la compagnie souhaitée
	 * @return un objet de type Compagnie si elle a été trouvée sinon null
	 */
	Compagnie recupererCompagnie(Long id);
	
	List<Compagnie> recupererCompagnies();

    boolean supprimerCompagnie(Long id);
}
