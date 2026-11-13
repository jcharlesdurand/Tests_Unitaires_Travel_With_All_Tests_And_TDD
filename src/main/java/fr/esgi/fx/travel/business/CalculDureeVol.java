package fr.esgi.fx.travel.business;

import java.time.Duration;
import java.time.LocalTime;

public class CalculDureeVol {
    private static final long MINUTES_PAR_JOUR = 24 * 60;

    public long dureeEnMinutes(LocalTime depart, LocalTime arrivee){
        if (depart == null || arrivee == null) {
            throw new IllegalArgumentException("Les heures de départ et d'arrivée sont obligatoire");
        }

        long dureeEnMinutes = Duration.between(depart, arrivee).toMinutes();
        if (dureeEnMinutes < 0){
            // Le vol arrive le lendemain (passage de minuit)
            return dureeEnMinutes + MINUTES_PAR_JOUR;
        }

        return dureeEnMinutes;
    }
}
