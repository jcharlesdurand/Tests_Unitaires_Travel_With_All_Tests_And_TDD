package fr.esgi.fx.travel.service.impl;

import fr.esgi.fx.travel.business.Compagnie;
import fr.esgi.fx.travel.dao.CompagnieDao;
import fr.esgi.fx.travel.exception.CompagnieDejaPresenteException;
import fr.esgi.fx.travel.exception.CompagnieInexistanteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompagnieServiceImplTest {

    @Mock
    private CompagnieDao compagnieDao;

    @InjectMocks
    private CompagnieServiceImpl service;

    @Test
    @DisplayName("Méthode qui teste l'ajout d'une compagnie")
    void testAjouterUneCompagnie() throws SQLException {
        //Arrange
        String nom = "Air France";
        when(compagnieDao.create(any(Compagnie.class))).thenReturn(new Compagnie(nom));

        //Act
        Compagnie compagnieAjoutee = service.ajouterCompagnie(nom);

        //Assert
        assertEquals(nom, compagnieAjoutee.getNom());
    }

    @Test
    @DisplayName("Méthode qui teste que l'on a l'exception sur l'ajout d'une compagnie existante")
    void testAjouterUneCompagnieExistanteRenvoieUneException() throws SQLException {
        //Arrange
        String nom = "Air France";
        when(compagnieDao.findAll()).thenReturn(new ArrayList<>())
                                    .thenReturn(List.of(new Compagnie(nom)));

        when(compagnieDao.create(any(Compagnie.class))).thenReturn(new Compagnie(nom));
        service.ajouterCompagnie(nom);

        //Act
        CompagnieDejaPresenteException exception = assertThrows(CompagnieDejaPresenteException.class,
                                    () -> service.ajouterCompagnie((nom)));

        //Assert
        assertEquals("Cette compagnie est déjà présente", exception.getMessage());
    }

    // Ajout pour le TP

    @Test
    @DisplayName("Méthode qui teste la suppression d'une compagnie existante")
    void testSupprimerUneCompagnieExistante() throws SQLException {
        // Arrange
        Compagnie compagnie = new Compagnie("nom", null);
        compagnie.setId(22L);
        when(compagnieDao.findOne(22L)).thenReturn(compagnie);
        when(compagnieDao.delete(22L)).thenReturn(true);

        // Act
        boolean resultat = service.supprimerCompagnie(22L);

        // Assert
        Assertions.assertTrue(resultat);
    }

    @Test
    @DisplayName("Méthode qui teste que l'on a l'exception sur la supperssion d'une compagnie inexistante")
    void testSupprimerUneCompagnieInexistanteRenvoieUneException() throws SQLException {
        // Arrange
        when(compagnieDao.findOne(anyLong())).thenReturn(null);

        // Act
        CompagnieInexistanteException exception  = assertThrows(CompagnieInexistanteException.class,
                                    () -> service.supprimerCompagnie(Long.valueOf(1)));

        // Assert
        assertEquals("La compagnie avec l'id 1 n'existe pas", exception.getMessage());
    }
}
