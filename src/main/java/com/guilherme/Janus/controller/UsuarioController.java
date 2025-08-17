package com.guilherme.Janus.controller;

import com.guilherme.Janus.dto.UsuarioDto;
import com.guilherme.Janus.model.Usuario;
import com.guilherme.Janus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastro")
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
