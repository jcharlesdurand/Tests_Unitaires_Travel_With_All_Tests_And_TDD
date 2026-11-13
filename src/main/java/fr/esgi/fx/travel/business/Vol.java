package fr.esgi.fx.travel.business;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Vol implements Serializable, Comparable<Vol> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Aeroport aeroportDepart;
	private Aeroport aeroportArrivee;
	private LocalTime heureDepart;
	private LocalTime heureArrivee;
	private float prixEnEuros;
	@NonNull
	private Compagnie compagnie;

	public Vol(Aeroport aeroportDepart, Aeroport aeroportArrivee, LocalTime dateHeureDepart, LocalTime dateHeureArrivee,
			float prixEnEuros, Compagnie compagnie) {
		this(compagnie);
		this.aeroportDepart = aeroportDepart;
		this.aeroportArrivee = aeroportArrivee;
		this.heureDepart = dateHeureDepart;
		this.heureArrivee = dateHeureArrivee;
		this.prixEnEuros = prixEnEuros;
	}

	@Override
	public int compareTo(Vol autreVol) {
		return ((Float) this.getPrixEnEuros()).compareTo(autreVol.getPrixEnEuros());
	}

}
