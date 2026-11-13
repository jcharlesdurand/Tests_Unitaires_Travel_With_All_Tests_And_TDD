package fr.esgi.fx.travel.dao;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Compagnie;

public interface CompagnieDao {

	// C
	Compagnie create(Compagnie compagnie) throws SQLException;

	// R
	Compagnie findOne(Long id) throws SQLException;

	List<Compagnie> findAll() throws SQLException;

	// D
	boolean delete(Long id) throws SQLException;

}
