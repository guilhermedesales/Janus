package com.guilherme.Janus.Application.DTOs.Dash;

import java.util.List;

public record ResumoGeralDTO(
        long total,
        long atrasadas,
        long paraHoje,
        long concluidas,
        List<CategoriaResumoDTO> porCategoria
) {}
