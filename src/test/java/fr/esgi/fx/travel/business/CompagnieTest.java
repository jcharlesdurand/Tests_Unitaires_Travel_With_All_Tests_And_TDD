package fr.esgi.fx.travel.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompagnieTest {

    @Test
    @DisplayName("Méthode qui teste les getters et les setters")
    void testerGettersSetters() {
        // AAA : Arrange Act Assert
        // On déclare un objet de type Compagnie (Arrange)
        String nom = "CompagnieTest";
        Compagnie compagnie = new Compagnie();

        // Act : on invoque la méthode qui fait l'objet du test
        compagnie.setNom(nom);

        // On invoque deux méthodes de vérification (Assert)
        assertNotNull(compagnie.getNom());
        assertEquals(nom, compagnie.getNom());
    }
}
