package com.guilherme.Janus.repository;

import com.guilherme.Janus.model.CategoriaTarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoriaTarefaRepository extends JpaRepository<CategoriaTarefa, Long> {
    List<CategoriaTarefa> findByUsuarioEmail(String email);

    Optional<CategoriaTarefa> findByIdAndUsuarioEmail(Long id, String email);
}
