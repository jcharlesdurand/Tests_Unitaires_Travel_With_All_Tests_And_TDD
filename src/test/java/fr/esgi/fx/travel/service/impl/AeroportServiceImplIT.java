package fr.esgi.fx.travel.service.impl;

import fr.esgi.fx.travel.business.Aeroport;
import fr.esgi.fx.travel.dao.AeroportDao;
import fr.esgi.fx.travel.dao.ConnexionBdd;
import fr.esgi.fx.travel.dao.impl.AeroportDaoImpl;
import fr.esgi.fx.travel.exception.AeroportDejaPresentException;
import fr.esgi.fx.travel.service.AeroportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AeroportServiceImplIT {

    AeroportDao aeroportDao = new AeroportDaoImpl();
    AeroportService aeroportService = new AeroportServiceImpl(aeroportDao);

    @BeforeEach
    void setup() throws SQLException, ClassNotFoundException {
        PreparedStatement ps = ConnexionBdd.getConnection().prepareStatement("DELETE FROM Vol; DELETE FROM Aeroport");
        ps.executeUpdate();
    }

    @Test
    @DisplayName("Méthode qui test l'ajout d'un aéroport sur le service")
    void testAjoutDunAeroport(){
        //Arrange
        String nomAeroport = "LYS";

        //Act
        Aeroport aeroport = aeroportService.ajouterAeroport(nomAeroport);

        //Assert
        assertEquals(aeroport, aeroportService.recupererAeroport(aeroport.getId()));
    }

    @Test
    @DisplayName("Méthode qui test l'ajout d'un aeroport déjà présent")
    void testAjouterUnAeroportExistantDoitRenvoyerUneException(){
        //Arrange
        String nomAeroport = "LYS";
        aeroportService.ajouterAeroport(nomAeroport);

        //Act
        AeroportDejaPresentException exception = assertThrows(AeroportDejaPresentException.class,
                () -> aeroportService.ajouterAeroport(nomAeroport));

        //Assert
        assertEquals("Cet aéroport est déjà présent", exception.getMessage(), "Le message devrait correspondre");
    }
}
