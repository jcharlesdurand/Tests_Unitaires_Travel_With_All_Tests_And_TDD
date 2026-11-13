package fr.esgi.fx.travel.dao;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Aeroport;

public interface AeroportDao {

	// CRUD : Create Read Update Delete
	
	// C
    Aeroport create(Aeroport aeroport) throws SQLException;

    // R
    Aeroport findOne(Long id) throws SQLException;
    
    List<Aeroport> findAll() throws SQLException;

}
