package fr.esgi.fx.travel.service.impl;

import fr.esgi.fx.travel.business.Aeroport;
import fr.esgi.fx.travel.business.Compagnie;
import fr.esgi.fx.travel.business.Vol;
import fr.esgi.fx.travel.dao.VolDao;
import fr.esgi.fx.travel.exception.CompagnieInexistanteException;
import fr.esgi.fx.travel.service.AeroportService;
import fr.esgi.fx.travel.service.CompagnieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolServiceImplTest {
    @Mock
    VolDao volDao;
    @Mock
    AeroportService aeroportService;
    @Mock
    CompagnieService compagnieService;

    @InjectMocks
    VolServiceImpl volServiceImpl;

    @Test
    @DisplayName("Méthode qui teste l'ajotu d'un vol avec des données valides")
    void testAjouterVolAvecToutesLesDonneesValides() throws SQLException {
        // Arrange
        Aeroport lyon = new Aeroport(1L, "Lyon-Saint Exupéry");
        Aeroport paris = new Aeroport(2L, "Paris-Charles de Gaulle");
        Compagnie airFrance = new Compagnie("Air France", null);
        LocalTime departureTime = LocalTime.of(7, 15);
        LocalTime arrivalTime = LocalTime.of(8, 20);
        float price = 129.90f;

        when(aeroportService.recupererAeroport(1L)).thenReturn(lyon);
        when(aeroportService.recupererAeroport(2L)).thenReturn(paris);
        when(compagnieService.recupererCompagnie(10L)).thenReturn(airFrance);
        when(volDao.create(any(Vol.class))).thenAnswer(invocation -> invocation.getArgument(0, Vol.class));

        // Act
        Vol result = volServiceImpl.ajouterVol(10L, 1L, 2L, departureTime, arrivalTime, price);

        // Assert
        Vol expected = new Vol(lyon, paris, departureTime, arrivalTime, price, airFrance);
        assertEquals(expected, result);
        verify(volDao, times(1)).create(any(Vol.class));
    }

    //Variante avec ArgumentCaptor
    @Test
    @DisplayName("Méthode qui teste l'ajotu d'un vol avec des données valides, avec ArgumentCaptor")
    void testAjouterVolAvecToutesLesDonneesValidesAvecArgumentCaptor() throws SQLException {
        Aeroport lyon = new Aeroport(1L, "Lyon-Saint Exupéry");
        Aeroport paris = new Aeroport(2L, "Paris-Charles de Gaulle");
        Compagnie airFrance = new Compagnie("Air France", null);
        LocalTime departureTime = LocalTime.of(7, 15);
        LocalTime arrivalTime = LocalTime.of(8, 20);
        float price = 129.90f;
        ArgumentCaptor<Vol> volCaptor = ArgumentCaptor.forClass(Vol.class);

        when(aeroportService.recupererAeroport(1L)).thenReturn(lyon);
        when(aeroportService.recupererAeroport(2L)).thenReturn(paris);
        when(compagnieService.recupererCompagnie(10L)).thenReturn(airFrance);

        volServiceImpl.ajouterVol(10L, 1L, 2L, departureTime, arrivalTime, price);

        verify(volDao, times(1)).create(volCaptor.capture());
        Vol captured = volCaptor.getValue();
        Vol expected = new Vol(lyon, paris, departureTime, arrivalTime, price, airFrance);
        assertThat(captured).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Méthode qui teste que l'on a une exception lorsque l'on ajoute un vol sur une compagnie inconnue")
    void testAjouterVolAvecUneCompagnieInconnueRenvoieUneException() throws SQLException {
        Aeroport lyon = new Aeroport(1L, "Lyon-Saint Exupéry");
        Aeroport paris = new Aeroport(2L, "Paris-Charles de Gaulle");

        when(aeroportService.recupererAeroport(1L)).thenReturn(lyon);
        when(aeroportService.recupererAeroport(2L)).thenReturn(paris);
        when(compagnieService.recupererCompagnie(99L)).thenThrow(new CompagnieInexistanteException("Compagnie 99 not found"));

        assertThrows(CompagnieInexistanteException.class,
                () -> volServiceImpl.ajouterVol(99L, 1L, 2L, LocalTime.of(7, 15), LocalTime.of(8, 20), 129.90f));

        verify(volDao, never()).create(any(Vol.class));
    }
}
