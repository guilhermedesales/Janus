package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Usuario.UsuarioDto;
import com.guilherme.Janus.Domain.Entities.Usuario;
import com.guilherme.Janus.Data.Repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para cadastro e gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    // cadastro de um novo usuário
    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário com nome, email e senha criptografada")
    public ResponseEntity<String> cadastrar (@RequestBody UsuarioDto dto){

        if (repository.findByEmail(dto.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Usuario ja existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encoder.encode(dto.getSenha()));

        repository.save(usuario);

        return ResponseEntity.ok().body("Usuario cadastrado com sucesso");

    }

}
