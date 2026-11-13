package fr.esgi.fx.travel.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculDureeVolTest {

    private final CalculDureeVol calcul = new CalculDureeVol();

    @ParameterizedTest(name = "{0} -> {1} dure {2} minutes")
    @CsvSource({
            "10:00, 10:45, 45",
            "10:00, 12:00, 120",
            "23:00, 01:00, 120"
    })
    void CalculDeLaDureeDunVol(LocalTime depart, LocalTime arrivee, long dureeAttendue){
        // Act
        long result = calcul.dureeEnMinutes(depart, arrivee);

        // Assert
        assertThat(result).isEqualTo(dureeAttendue);
    }

    @Test
    void UneHeureDeDepartNullDoitLeverUneException(){
        LocalTime arrivee = LocalTime.of(12,00);

        assertThrows(IllegalArgumentException.class, () -> calcul.dureeEnMinutes(null, arrivee));
    }

    @Test
    void UneHeureDarriveNullDoitLeverUneException(){
        LocalTime depart = LocalTime.of(12,00);

        assertThrows(IllegalArgumentException.class, () -> calcul.dureeEnMinutes(depart, null));
    }
}
