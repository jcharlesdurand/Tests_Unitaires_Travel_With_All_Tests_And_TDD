package fr.esgi.fx.travel.business;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
public class Aeroport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nom;

	public Aeroport() {
	}

	public Aeroport(String nom) {
		super();
		this.nom = nom;
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
		Aeroport other = (Aeroport) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return "Aeroport [id=" + id + ", nom=" + nom + "]";
	}

}