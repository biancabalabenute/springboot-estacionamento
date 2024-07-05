package com.balabenute.demo_park_api.web.dto.mapper;

import com.balabenute.demo_park_api.entity.Vaga;
import com.balabenute.demo_park_api.web.dto.VagaCreateDto;
import com.balabenute.demo_park_api.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
