package fr.esgi.fx.travel.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VolTest {

    @Test
    @DisplayName("Méthode qui teste les getters et les setters")
    void testerGettersSetters() {

        Aeroport aeroportDepart = new Aeroport("Zaventem airport");
        Aeroport aeroportArrivee = new Aeroport("Jacques Cartier CA");
        LocalTime heureDepart = LocalTime.of(11, 25);
        LocalTime heureArrivee = LocalTime.of(16, 18);
        float prixEnEuros = (float) 562.23;
        Compagnie compagnie = new Compagnie("Oceanic");

        Vol vol = new Vol(aeroportDepart, aeroportArrivee, heureDepart, heureArrivee, prixEnEuros, compagnie);

        assertEquals(aeroportDepart, vol.getAeroportDepart(), "Aeroport de départ non concordants");
        assertEquals(aeroportArrivee, vol.getAeroportArrivee(), "Aeroport d'arrivée non concordants");
        assertEquals(heureDepart, vol.getHeureDepart(), "Heure de départ non concordantes");
        assertEquals(heureArrivee, vol.getHeureArrivee(), "Heure d'arrivée non concordantes");
        assertEquals(prixEnEuros, vol.getPrixEnEuros(), "Prix en euros non concordants");
        assertEquals(compagnie, vol.getCompagnie(), "Compagnies non concordantes");
    }

    @Test
    @DisplayName("Test qui compare le prix d'un vol plus cher par rapport à un moins cher")
    void shouldConsiderMoreExpensiveFlightAsGreater(){
        //Arrange
        Vol expensive = new Vol(null, null, null, null, 200f, new Compagnie("A"));
        Vol cheap = new Vol(null, null, null, null, 100f, new Compagnie("B"));

        //Act
        int comparison = expensive.compareTo(cheap);

        //Assert
        assertThat(comparison).isPositive();
    }
}
