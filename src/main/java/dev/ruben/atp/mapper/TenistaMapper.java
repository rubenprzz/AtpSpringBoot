package dev.ruben.atp.mapper;

import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.models.Tenista;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class TenistaMapper {

    private final ModelMapper modelMapper;
    public TenistaCreateDTO convertoToTenistaDto(Tenista tenista) {
        return modelMapper.map(tenista, TenistaCreateDTO.class);
    }
    public Tenista convertoToTenista(TenistaCreateDTO tenistaCreateDTO) {
        return modelMapper.map(tenistaCreateDTO, Tenista.class);
    }
    public TenistaUpdateDTO convertoToTenistaUpdateDTO(Tenista tenista) {
        return modelMapper.map(tenista, TenistaUpdateDTO.class);
    }
    public Tenista convertoToTenista(TenistaUpdateDTO tenistaUpdateDTO, Tenista tenista) {
        return modelMapper.map(tenistaUpdateDTO, Tenista.class);
    }

}
