package com.guilherme.Janus.Data.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.Janus.Domain.Entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByAuthUserId(UUID authUserId);
    Optional<Usuario> findByEmail(String email);
}
