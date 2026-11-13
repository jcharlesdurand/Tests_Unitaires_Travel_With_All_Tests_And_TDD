package fr.esgi.fx.travel.service.impl;

import fr.esgi.fx.travel.business.Aeroport;
import fr.esgi.fx.travel.dao.AeroportDao;
import fr.esgi.fx.travel.exception.AeroportDejaPresentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AeroportServiceImplTest {
    @Mock
    AeroportDao aeroportDao;
    @InjectMocks
    AeroportServiceImpl aeroportService;

    @Test
    @DisplayName("Méthode qui test l'ajout d'un nouvel aéroport")
    void testAjouterUnNouvelAeroport() throws SQLException {
        // Arrange
        String nom = "LYS";
        when(aeroportDao.findAll()).thenReturn(new ArrayList<>());
        when(aeroportDao.create(Mockito.any(Aeroport.class))).thenReturn(new Aeroport(nom));

        // Act
        Aeroport aeroportAjoute = aeroportService.ajouterAeroport(nom);

        // Assert
        assertNotNull(aeroportAjoute);
        assertEquals(nom, aeroportAjoute.getNom());
    }

    @Test
    @DisplayName("Méthode qui teste que l'on a l'exception sur l'ajout d'un aéroport existant")
    void testAjouterUnNouvelAeroportDejaPresentRenvoieUneException() throws SQLException {
        // Arrange
        String nom = "LYS";
        when(aeroportDao.findAll()).thenReturn(List.of(new Aeroport(nom)));

        //Act
        AeroportDejaPresentException exception = assertThrows(AeroportDejaPresentException.class, () -> aeroportService.ajouterAeroport(nom));

        //Assert
        assertEquals("Cet aéroport est déjà présent", exception.getMessage());
    }

    @Test
    @DisplayName("Méthode qui teste que l'on récupère bien un aéroport en fonction de son id")
    void testRecupererUnAeroportParSonId() throws SQLException {
        // Arrange
        Aeroport aeroport = new Aeroport("Test");
        aeroport.setId(18L);
        when(aeroportDao.findOne(18L)).thenReturn(aeroport);

        // Act
        Aeroport aeroportRecupere = aeroportService.recupererAeroport(18L);

        // Assert
        assertEquals(aeroportRecupere.getNom(), aeroport.getNom());
        assertEquals(aeroportRecupere.getId(), 18L);
    }

    @Test
    @DisplayName("Méthode qui teste que l'on récupère bien tous les aéroports")
    void testRecupererTousLesAeroports() throws SQLException {
        List<String> noms = List.of("Lyon Saint Exupery", "Tunis-Carthage");
        List<Aeroport> aeroportsMockees = new ArrayList<>();
        noms.forEach(n -> aeroportsMockees.add(new Aeroport(n)));

        when(aeroportDao.findAll()).thenReturn(aeroportsMockees);

        // Act
        List<Aeroport> aeroportsRecuperes = aeroportService.recupererAeroports();

        // Assert
        assertFalse(aeroportsRecuperes.isEmpty());
        assertThat(aeroportsRecuperes).containsAll(aeroportsMockees);
    }
}
