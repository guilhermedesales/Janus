package com.guilherme.Janus.Application.Service;

import com.guilherme.Janus.Data.Repositories.UsuarioRepository;
import com.guilherme.Janus.Domain.Entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioIdentityService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioIdentityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obterOuCriarUsuario(UUID authUserId, String email, String nome) {
        return usuarioRepository.findByAuthUserId(authUserId)
                .map(usuario -> atualizarDadosBasicos(usuario, email, nome))
                .orElseGet(() -> criarOuMigrarPorEmail(authUserId, email, nome));
    }

    public Usuario buscarPorEmailOuErro(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado para email autenticado"));
    }

    private Usuario criarOuMigrarPorEmail(UUID authUserId, String email, String nome) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    usuario.setAuthUserId(authUserId);
                    usuario.setNome(nome);
                    return usuarioRepository.save(usuario);
                })
                .orElseGet(() -> {
                    Usuario usuario = new Usuario();
                    usuario.setAuthUserId(authUserId);
                    usuario.setEmail(email);
                    usuario.setNome(nome);
                    return usuarioRepository.save(usuario);
                });
    }

    private Usuario atualizarDadosBasicos(Usuario usuario, String email, String nome) {
        usuario.setEmail(email);
        usuario.setNome(nome);
        return usuarioRepository.save(usuario);
    }
}

