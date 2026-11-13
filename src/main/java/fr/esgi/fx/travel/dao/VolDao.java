package fr.esgi.fx.travel.dao;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Vol;

public interface VolDao {

    Vol create(Vol vol) throws SQLException;

    List<Vol> findAll() throws SQLException;
    
    /**
     * Cette méthode a été demandée par les testeurs logiciels
     * pour mener à bien leurs tests
     * (l'écriture de la méthode ne respecte pas le principe YAGNI)
     * Il existe une solution élégante pour exécuter des requêtes SQL
     * avant invocation des méthodes de test, solution élégante
     * à découvrir en stage
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    boolean delete(Long id) throws SQLException;

}
