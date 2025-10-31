package com.guilherme.Janus.Mapping;

import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.Tarefa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapping {

    Tarefa toEntity(TarefaDto dto);
    TarefaDto toDto(Tarefa tarefa);

}
