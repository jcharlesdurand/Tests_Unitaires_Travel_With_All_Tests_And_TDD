package fr.esgi.fx.travel.service.impl;

import java.sql.SQLException;
import java.util.List;

import fr.esgi.fx.travel.business.Compagnie;
import fr.esgi.fx.travel.dao.CompagnieDao;
import fr.esgi.fx.travel.exception.CompagnieDejaPresenteException;
import fr.esgi.fx.travel.exception.CompagnieInexistanteException;
import fr.esgi.fx.travel.service.CompagnieService;

public class CompagnieServiceImpl implements CompagnieService {

	private CompagnieDao compagnieDao = null;
	
	public CompagnieServiceImpl(CompagnieDao compagnieDao) {
		super();
		this.compagnieDao = compagnieDao;
	}

	@Override
	public Compagnie ajouterCompagnie(String nom) {

		try {
			if (!compagnieDao.findAll().stream().filter(a -> a.getNom().equals(nom)).toList().isEmpty()) {
				throw new CompagnieDejaPresenteException("Cette compagnie est déjà présente");
			}
		}
		catch (SQLException e) {
				e.printStackTrace();
			}

		Compagnie compagnie = new Compagnie(nom);
		try {
			compagnieDao.create(compagnie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compagnie;
	}

	@Override
	public Compagnie recupererCompagnie(Long id) {
        Compagnie compagnie = null;

        try {
            compagnie = compagnieDao.findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (compagnie == null) {
            throw new CompagnieInexistanteException("La compagnie avec l'id " + id + " n'existe pas");
        }
        return compagnie;
    }

	@Override
	public List<Compagnie> recupererCompagnies() {
		try {
			return compagnieDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public boolean supprimerCompagnie(Long id) {
        Compagnie compagnie = recupererCompagnie(id);
        try {
            compagnieDao.delete(id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}