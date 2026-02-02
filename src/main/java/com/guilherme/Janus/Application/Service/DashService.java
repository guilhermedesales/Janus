package com.guilherme.Janus.Application.Service;

import com.guilherme.Janus.Application.DTOs.Dash.CategoriaResumoDTO;
import com.guilherme.Janus.Application.DTOs.Dash.ResumoGeralDTO;
import com.guilherme.Janus.Data.Repositories.DashRepository;
import com.guilherme.Janus.Domain.enums.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashService {

    private final DashRepository dashRepository;

    public DashService(DashRepository dashRepository) {
        this.dashRepository = dashRepository;
    }

    public ResumoGeralDTO obterResumo(String email) {
        LocalDate hoje = LocalDate.now();

        long total = dashRepository.countTotal(email);
        long atrasadas = dashRepository.countAtrasadas(email, hoje, Status.CONCLUIDO);
        long hojeCount = dashRepository.countParaHoje(email, hoje);
        long concluidas = dashRepository.countConcluidas(email, Status.CONCLUIDO);

        List<CategoriaResumoDTO> porCategoria =
                dashRepository.resumoPorCategoria(email, hoje, Status.CONCLUIDO);

        return new ResumoGeralDTO(
                total,
                atrasadas,
                hojeCount,
                concluidas,
                porCategoria
        );
    }


}
