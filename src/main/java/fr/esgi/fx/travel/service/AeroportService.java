package fr.esgi.fx.travel.service;

import java.util.List;

import fr.esgi.fx.travel.business.Aeroport;

public interface AeroportService {

	Aeroport ajouterAeroport(String aeroport);
	
	Aeroport recupererAeroport(Long id);
	
	List<Aeroport> recupererAeroports();
	
}
