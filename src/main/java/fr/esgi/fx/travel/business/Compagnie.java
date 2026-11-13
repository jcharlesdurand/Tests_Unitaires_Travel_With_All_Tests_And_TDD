package fr.esgi.fx.travel.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Compagnie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nom;
	private List<Vol> vols = new ArrayList<>();

	
	public Compagnie() {
	}
	
	public Compagnie(String nom) {
		this();
		this.nom = nom;
	}

	public Compagnie(String nom, List<Vol> vols) {
		this(nom);
		this.vols = vols;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Vol> getVols() {
		return vols;
	}

	public void setVols(List<Vol> vols) {
		this.vols = vols;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compagnie other = (Compagnie) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return "Compagnie [id=" + id + ", nom=" + nom + "]";
	}

}
