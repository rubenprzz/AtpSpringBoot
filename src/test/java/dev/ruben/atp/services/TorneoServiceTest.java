package dev.ruben.atp.services;

import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.repository.TorneoRepository;
import dev.ruben.atp.services.TorneoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TorneoServiceTest {

    @Mock
    private TorneoRepository torneoRepository;

    private TorneoService torneoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        torneoService = new TorneoService();
    }

    @Test
    @DisplayName("Should return torneo when findTorneoByIdSec is called with existing idSec")
    public void shouldReturnTorneoWhenFindTorneoByIdSecCalledWithExistingIdSec() {
        Torneo torneo = new Torneo();
        when(torneoRepository.findTorneoByIdSec(1L)).thenReturn(Optional.of(torneo));

        Optional<Torneo> result = torneoRepository.findTorneoByIdSec(1L);

        assertEquals(torneo, result.orElse(null));
    }

    @Test
    @DisplayName("Should return empty when findTorneoByIdSec is called with non-existing idSec")
    public void shouldReturnEmptyWhenFindTorneoByIdSecCalledWithNonExistingIdSec() {
        when(torneoRepository.findTorneoByIdSec(1L)).thenReturn(Optional.empty());

        Optional<Torneo> result = torneoRepository.findTorneoByIdSec(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("Should return saved torneo when save is called with new torneo")
    public void shouldReturnSavedTorneoWhenSaveCalledWithNewTorneo() {
        Torneo torneo = new Torneo();

        Torneo result = torneoService.save(torneo);

        assertNotNull(result.getId());
    }
}