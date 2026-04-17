package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.Service.TokenClaimsService;
import com.guilherme.Janus.Application.Service.UsuarioIdentityService;
import com.guilherme.Janus.Domain.Entities.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Endpoints de perfil do usuario autenticado")
public class UsuarioController {

    private final UsuarioIdentityService usuarioIdentityService;
    private final TokenClaimsService tokenClaimsService;

    public UsuarioController(UsuarioIdentityService usuarioIdentityService,
                             TokenClaimsService tokenClaimsService) {
        this.usuarioIdentityService = usuarioIdentityService;
        this.tokenClaimsService = tokenClaimsService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('usuario:view')")
    @Operation(summary = "Perfil autenticado", description = "Retorna e sincroniza localmente o usuario autenticado via JWT")
    public ResponseEntity<Map<String, Object>> me(Authentication authentication) {
        UUID authUserId = tokenClaimsService.getAuthUserId(authentication);
        String email = tokenClaimsService.getEmail(authentication);
        String nome = tokenClaimsService.getNome(authentication);

        Usuario usuario = usuarioIdentityService.obterOuCriarUsuario(authUserId, email, nome);

        return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "authUserId", usuario.getAuthUserId(),
                "nome", usuario.getNome(),
                "email", usuario.getEmail()
        ));
    }
}
