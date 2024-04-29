package dev.ruben.atp.mapper;

import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.dto.TorneoCreateDto;
import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.dto.TorneoUpdateDto;
import dev.ruben.atp.models.Torneo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.management.modelmbean.ModelMBean;
@Component
@RequiredArgsConstructor
public class TorneoMapper {

    private final ModelMapper modelMapper;

    public TorneoResponseDTO toTorneoResponseDTO(Torneo torneo) {
        return  modelMapper.map(torneo, TorneoResponseDTO.class);
    }

    public Torneo toTorneo(TorneoResponseDTO torneoResponseDTO) {
        return modelMapper.map(torneoResponseDTO, Torneo.class);
    }
    public Torneo toTorneo(TorneoCreateDto torneoCreateDto) {
        return modelMapper.map(torneoCreateDto, Torneo.class);
    }
    public Torneo toTorneo(TorneoUpdateDto torneoUpdateDto, Torneo torneo) {
        return modelMapper.map(torneoUpdateDto, Torneo.class);
    }

}
