package com.guilherme.Janus.Data.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.Entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findByUsuarioEmail(String email);

    List<Categoria> findByUsuario(Usuario usuario);

    Optional<Categoria> findByIdAndUsuarioEmail(UUID id, String email);

}
