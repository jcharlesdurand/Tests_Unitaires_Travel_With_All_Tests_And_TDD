package fr.esgi.fx.travel.service.impl;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Aeroport;
import fr.esgi.fx.travel.dao.AeroportDao;
import fr.esgi.fx.travel.exception.AeroportDejaPresentException;
import fr.esgi.fx.travel.service.AeroportService;

public class AeroportServiceImpl implements AeroportService {

    private AeroportDao aeroportDao = null;

    public AeroportServiceImpl(AeroportDao aeroportDao) {
		super();
		this.aeroportDao = aeroportDao;
	}

	@Override
    public Aeroport ajouterAeroport(String nom) {
		try {
			if (!aeroportDao.findAll().stream().filter(a -> a.getNom().equals(nom)).toList().isEmpty()) {
				throw new AeroportDejaPresentException("Cet aéroport est déjà présent");
			}
			else {
				 Aeroport aeroport = new Aeroport(nom);
		         aeroportDao.create(aeroport);
		         return aeroport;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
           
    }

    @Override
    public Aeroport recupererAeroport(Long id) {
            try {
                    return aeroportDao.findOne(id);
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    }

    @Override
    public List<Aeroport> recupererAeroports() {
            try {
                return aeroportDao.findAll();
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;
    }
}