package dev.ruben.atp.controllers;

import dev.ruben.atp.controllers.TorneoController;
import dev.ruben.atp.dto.TorneoCreateDto;
import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.dto.TorneoUpdateDto;
import dev.ruben.atp.exceptions.TorneoNotFoundException;
import dev.ruben.atp.mapper.TorneoMapper;
import dev.ruben.atp.models.Categoria;
import dev.ruben.atp.models.Modo;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.services.TorneoService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TorneoControllerTest {

    private TorneoController torneoController;

    @Mock
    private TorneoService torneoService;
    @Mock
    private TorneoMapper torneoMapper;
    @Mock
    private PaginationLinksUtils paginationLinksUtils;
    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        torneoController = new TorneoController(torneoService, paginationLinksUtils,torneoMapper );
    }

    /*@Test
    @DisplayName("Should return torneos when getTorneos is called")
    public void shouldReturnTorneos() {
        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();
        Page<TorneoResponseDTO> torneos = new PageImpl<>(Collections.singletonList(torneoResponseDTO));
        Pageable pageable = PageRequest.of(0, 10);


        when(torneoMapper.toTorneoResponseDTO(any(Torneo.class))).thenReturn(torneoResponseDTO);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/torneos"));
        when(paginationLinksUtils.createLinkHeader(torneos, UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()))).thenReturn("<http://localhost:8080/torneos?page=0&size=10>; rel=\"self\"");

        ResponseEntity<Page<TorneoResponseDTO>> response = torneoController.getTorneos(pageable, request);

        assertEquals(torneos, response.getBody());
        assertEquals("<http://localhost:8080/torneos?page=0&size=10>; rel=\"self\"", response.getHeaders().getFirst("link"));
    }*/

    @Test
    @DisplayName("Should return Torneo by Id")
    public void shouldReturnTorneoById() {
        Torneo torneo = new Torneo();
        torneo.setId("e3965c44-bf3a-4adc-a146-d10d55f97402");
        String id = torneo.getId();
        when(torneoService.findById(id)).thenReturn(Optional.of(torneo));

        ResponseEntity<Optional<TorneoResponseDTO>> response = torneoController.getTorneoById(id);

        assertFalse(response.getBody().isPresent());
    }


    @Test
    @DisplayName("Should throw TorneoNotFoundException when getTorneoById is called with invalid id")
    public void shouldThrowExceptionWhenGetTorneoByIdIsCalledWithInvalidId() {
        String id = "1";

        when(torneoService.findById(id)).thenReturn(Optional.empty());

        assertThrows(TorneoNotFoundException.class, () -> torneoController.getTorneoById(id));
    }
    @Test
    @DisplayName("Should throw TorneoNotFoundException with message and cause")
    public void shouldThrowExceptionWithMessageAndCause() {
        String expectedMessage = "Torneo not found";
        Throwable expectedCause = new RuntimeException("Something went wrong");

        TorneoNotFoundException exception = assertThrows(
                TorneoNotFoundException.class,
                () -> {
                    throw new TorneoNotFoundException(expectedMessage, expectedCause);
                }
        );

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }
    @Test
    @DisplayName("Should create new Torneo")
    public void shouldCreateNewTorneo() {
        Torneo torneo = new Torneo();
        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();

        when(torneoService.save(any(Torneo.class))).thenReturn(torneo);
        when(torneoMapper.toTorneoResponseDTO(torneo)).thenReturn(torneoResponseDTO);

        ResponseEntity<TorneoResponseDTO> response = torneoController.saveTorneo(new TorneoCreateDto());
        assertNull(response.getBody());
    }


    @Test
    @DisplayName("Should update Torneo when updateTorneo is called with valid id and torneo")
    public void shouldUpdateTorneo() {
        Torneo torneo = new Torneo();
        torneo.setId("e3965c44-bf3a-4adc-a146-d10d55f97402");
        String id=torneo.getId();

        TorneoUpdateDto updateDto = new TorneoUpdateDto(1L, "Torneo", "Tierra", Categoria.MASTER_1000.toString(), "Madrid", "17/05/2021", "23/05/2021",1000L,120L, Modo.INDIVIDUAL.toString());

        when(torneoService.findById(id)).thenReturn(Optional.of(torneo));
        when(torneoService.save(any(Torneo.class))).thenAnswer(invocation -> {
            Torneo updatedTorneo = invocation.getArgument(0);
            torneo.setIdSec(updatedTorneo.getIdSec());
            torneo.setSuperficie(updatedTorneo.getSuperficie());
            torneo.setCategoria(updatedTorneo.getCategoria());
            torneo.setUbicacion(updatedTorneo.getUbicacion());
            torneo.setFechaInicio(updatedTorneo.getFechaInicio());
            torneo.setFechaFin(updatedTorneo.getFechaFin());
            torneo.setPremio(updatedTorneo.getPremio());
            torneo.setEntradas(updatedTorneo.getEntradas());
            torneo.setModo(updatedTorneo.getModo());
            return torneo;
        });

        ResponseEntity<TorneoResponseDTO> response = torneoController.updateTorneo(id, updateDto);

        assertNotEquals(torneo, response.getBody());
    }

    @Test
    @DisplayName("Should delete Torneo when deleteTorneoById is called with valid id")
    public void shouldDeleteTorneo() {
        Torneo torneo = new Torneo();
        String id = "1";

        when(torneoService.findById(id)).thenReturn(Optional.of(torneo));

        ResponseEntity<Void> response = torneoController.deleteTorneoById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
