package fr.esgi.fx.travel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import fr.esgi.fx.travel.business.Vol;
import fr.esgi.fx.travel.dao.AeroportDao;
import fr.esgi.fx.travel.dao.CompagnieDao;
import fr.esgi.fx.travel.dao.ConnexionBdd;
import fr.esgi.fx.travel.dao.Requetes;
import fr.esgi.fx.travel.dao.VolDao;

public class VolDaoImpl implements VolDao {

	private Connection connexion;
	private CompagnieDao compagnieDao = new CompagnieDaoImpl();
	private AeroportDao aeroportDao = new AeroportDaoImpl();

	public VolDaoImpl() {
		try {
			connexion = ConnexionBdd.getConnection();
			compagnieDao = new CompagnieDaoImpl();
			aeroportDao = new AeroportDaoImpl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Vol create(Vol vol) throws SQLException {
		// :screamcat !
		//vol.setPrixEnEuros(2*vol.getPrixEnEuros());
		PreparedStatement ps = connexion.prepareStatement(Requetes.AJOUT_VOL, Statement.RETURN_GENERATED_KEYS);
		ps.setTime(1, Time.valueOf(vol.getHeureDepart()));
		ps.setTime(2, Time.valueOf(vol.getHeureArrivee()));
		ps.setFloat(3, vol.getPrixEnEuros());
		ps.setLong(4, vol.getCompagnie().getId());
		ps.setLong(5, vol.getAeroportDepart().getId());
		ps.setLong(6, vol.getAeroportArrivee().getId());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			vol.setId(rs.getLong(1));
		}
		return vol;
	}

	@Override
	public List<Vol> findAll() throws SQLException {
		List<Vol> vols = new ArrayList<>();
		PreparedStatement ps = connexion.prepareStatement(Requetes.TOUS_LES_VOLS);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Vol vol = new Vol();
			vol.setId(rs.getLong("id"));
			// Utilisation du patron DAO: VolDao fait appel à CompagnieDao
			vol.setCompagnie(compagnieDao.findOne(rs.getLong("compagnie_id")));
			vol.setAeroportDepart(aeroportDao.findOne(rs.getLong("aeroport_depart_id")));
			vol.setAeroportArrivee(aeroportDao.findOne(rs.getLong("aeroport_arrivee_id")));

			vol.setPrixEnEuros(rs.getFloat("prix_en_euros"));
			vol.setHeureDepart(rs.getTime("heure_depart").toLocalTime());
			vol.setHeureArrivee(rs.getTime("heure_arrivee").toLocalTime());

			vols.add(vol);
		}
		return vols;
	}

	@Override
	public boolean delete(Long id) throws SQLException {
		PreparedStatement ps = connexion.prepareStatement(Requetes.SUPPRESSION_VOL);
		ps.setLong(1, id);
		ps.executeUpdate();
		return true;
	}

}