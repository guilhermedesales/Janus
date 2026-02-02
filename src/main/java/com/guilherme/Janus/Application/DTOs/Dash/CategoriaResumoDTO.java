package com.guilherme.Janus.Application.DTOs.Dash;

import java.util.UUID;

public record CategoriaResumoDTO(
        UUID categoriaId,
        String nome,
        long total,
        long atrasadas,
        long hoje,
        long concluidas
) {}
