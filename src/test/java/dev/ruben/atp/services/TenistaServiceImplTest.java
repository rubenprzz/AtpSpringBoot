package dev.ruben.atp.services;

import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Assuming Mockito is used for mocking
@ExtendWith(MockitoExtension.class)
public class TenistaServiceImplTest {

    @Mock
    private TenistaRepository tenistaRepository;

    @Mock
    private TenistaMapper tenistaMapper;

    @InjectMocks
    private TenistaServiceImpl tenistaService;

    @Test
    public void testFindAllNoFilters() throws Exception {
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(), new Tenista());
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        List<TenistaResponseDTO> mappedDTOs = Arrays.asList(new TenistaResponseDTO(), new TenistaResponseDTO());
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTOs.get(0));

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), pageable);

        assertEquals(2, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByName() throws Exception {
        String expectedName = "Rafael Nadal";
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, "Spain", null, 0L, 1.85, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, "Spain", null, null, null, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.empty(), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByCountry() throws Exception {
        String expectedCountry = "Spain";
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", expectedCountry, null, 0L, 1.85, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", expectedCountry, null, null, null, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.of(expectedCountry), Optional.empty(), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByHeight() throws Exception {
        Double expectedHeight = 1.85;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", "Spain", null, 0L, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", "Spain", null, null, 1.85, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByWeight() throws Exception {
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", "Spain", null, 0L, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", "Spain", null, null, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndCountry() throws Exception {
        String expectedName = "Rafael Nadal";
        String expectedCountry = "Spain";
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, expectedCountry, null, 0L, 1.85, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, expectedCountry, null, null, 1.85, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.of(expectedCountry), Optional.empty(), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndHeight() throws Exception {
        String expectedName = "Rafael Nadal";
        Double expectedHeight = 1.85;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, "Spain", null, 0L, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, "Spain", null, null, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndWeight() throws Exception {
        String expectedName = "Rafael Nadal";
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, "Spain", null, 0L, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, "Spain", null, null, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByCountryAndHeight() throws Exception {
        String expectedCountry = "Spain";
        Double expectedHeight = 1.85;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", expectedCountry, null, 0L, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", expectedCountry, null, null, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.of(expectedCountry), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByCountryAndWeight() throws Exception {
        String expectedCountry = "Spain";
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", expectedCountry, null, 0L, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", expectedCountry, null, null, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.of(expectedCountry), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByHeightAndWeight() throws Exception {
        Double expectedHeight = 1.85;
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", "Spain", null, 0L, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", "Spain", null, null, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.of(expectedHeight), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameCountryAndHeight() throws Exception {
        String expectedName = "Rafael Nadal";
        String expectedCountry = "Spain";
        Double expectedHeight = 1.85;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, expectedCountry, null, 0L, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, expectedCountry, null, null, expectedHeight, 85.0, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.of(expectedCountry), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameCountryAndWeight() throws Exception {
        String expectedName = "Rafael Nadal";
        String expectedCountry = "Spain";
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, expectedCountry, null, 0L, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, expectedCountry, null, null, 1.85, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.of(expectedCountry), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByCountryHeightAndWeight() throws Exception {
        String expectedCountry = "Spain";
        Double expectedHeight = 1.85;
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, "Rafael Nadal", expectedCountry, null, 0L, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, "Rafael Nadal", expectedCountry, null, null, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.of(expectedCountry), Optional.of(expectedHeight), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameCountryHeightAndWeight() throws Exception {
        String expectedName = "Rafael Nadal";
        String expectedCountry = "Spain";
        Double expectedHeight = 1.85;
        Double expectedWeight = 85.0;
        List<Tenista> tennisPlayers = Arrays.asList(new Tenista(1L, 1L, expectedName, expectedCountry, null, 0L, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null));
        Page<Tenista> mockPage = new PageImpl<>(tennisPlayers);
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        TenistaResponseDTO mappedDTO = new TenistaResponseDTO( 1L, 1L, expectedName, expectedCountry, null, null, expectedHeight, expectedWeight, null, null, null, null, null, null,null,null,null);
        when(tenistaMapper.toTenistaResponseDTO(any(Tenista.class))).thenReturn(mappedDTO);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.of(expectedCountry), Optional.of(expectedHeight), Optional.of(expectedWeight), pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(expectedName, result.getContent().get(0).getNombreCompleto());
        assertEquals(expectedCountry, result.getContent().get(0).getPais());
        assertEquals(expectedHeight, result.getContent().get(0).getAltura());
        assertEquals(expectedWeight, result.getContent().get(0).getPeso());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllNoResults() throws Exception {
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameNoResults() throws Exception {
        String expectedName = "Rafael Nadal";
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.empty(), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByCountryNoResults() throws Exception {
        String expectedCountry = "Spain";
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.of(expectedCountry), Optional.empty(), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByHeightNoResults() throws Exception {
        Double expectedHeight = 1.85;
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByWeightNoResults() throws Exception {
        Double expectedWeight = 85.0;
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndCountryNoResults() throws Exception {
        String expectedName = "Rafael Nadal";
        String expectedCountry = "Spain";
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.of(expectedCountry), Optional.empty(), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndHeightNoResults() throws Exception {
        String expectedName = "Rafael Nadal";
        Double expectedHeight = 1.85;
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.of(expectedHeight), Optional.empty(), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }
    @Test
    public void testFindAllByNameAndWeightNoResults() throws Exception {
        String expectedName = "Rafael Nadal";
        Double expectedWeight = 85.0;
        Page<Tenista> mockPage = new PageImpl<>(Arrays.asList());
        when(tenistaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = Pageable.unpaged();
        Page<TenistaResponseDTO> result = tenistaService.findAll(Optional.of(expectedName), Optional.empty(), Optional.empty(), Optional.of(expectedWeight), pageable);

        assertEquals(0, result.getContent().size());
        verify(tenistaRepository).findAll(any(Specification.class), any(Pageable.class));
    }


}
