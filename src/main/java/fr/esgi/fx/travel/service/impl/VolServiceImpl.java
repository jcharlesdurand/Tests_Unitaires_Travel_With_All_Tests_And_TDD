package fr.esgi.fx.travel.service.impl;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import fr.esgi.fx.travel.business.Vol;
import fr.esgi.fx.travel.dao.VolDao;
import fr.esgi.fx.travel.service.AeroportService;
import fr.esgi.fx.travel.service.CompagnieService;
import fr.esgi.fx.travel.service.VolService;

public class VolServiceImpl implements VolService {

	private VolDao volDao = null;
	private AeroportService aeroportService = null;
	private CompagnieService compagnieService = null;
	
	public VolServiceImpl(VolDao volDao, AeroportService aeroportService, CompagnieService compagnieService) {
		this.volDao = volDao;
		this.aeroportService = aeroportService;
		this.compagnieService = compagnieService;
	}

	@Override
	public Vol ajouterVol(long idCompagnie, long idAeroportDepart, long idAeroportArrivee, LocalTime heureDepart, LocalTime heureArrivee, float prixEnEuros) {
		Vol vol = new Vol();
		vol.setHeureDepart(heureDepart);
		vol.setHeureArrivee(heureArrivee);
		vol.setAeroportDepart(aeroportService.recupererAeroport(idAeroportDepart));
		vol.setAeroportArrivee(aeroportService.recupererAeroport(idAeroportArrivee));
		vol.setCompagnie(compagnieService.recupererCompagnie(idCompagnie));
		vol.setPrixEnEuros(prixEnEuros);
		try {
			volDao.create(vol);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vol;
	}

	@Override
	public List<Vol> recupererVols() {
		try {
			return volDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
