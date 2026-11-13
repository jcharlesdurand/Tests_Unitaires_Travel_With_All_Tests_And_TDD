package fr.esgi.fx.travel.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AeroportTest {

    @Test
    @DisplayName(value = "Méthode qui teste les getters setters de la classe métier Aeroport")
    void testerGettersSetters() {
        //Arrange
        String nom = "AeroportTest";
        Aeroport aeroport = new Aeroport();

        // La méthode qui fait l'objet du test
        // Act
        aeroport.setNom(nom);

        // On vérifie que le nom de l'aéroport est égale au nom donné
        // par l'objet nom
        // Assert
        assertNotNull(aeroport.getNom());
        assertEquals(nom, aeroport.getNom(), "Le nom de l'aeroport doit être égal à AeroportTest");
    }
}
