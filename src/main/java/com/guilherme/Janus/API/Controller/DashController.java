package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Dash.ResumoGeralDTO;
import com.guilherme.Janus.Application.Service.DashService;
import com.guilherme.Janus.Application.Service.TokenClaimsService;
import com.guilherme.Janus.Application.Service.UsuarioIdentityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dash")
public class DashController {

    private final DashService dashService;
    private final TokenClaimsService tokenClaimsService;
    private final UsuarioIdentityService usuarioIdentityService;

    public DashController(DashService dashService,
                          TokenClaimsService tokenClaimsService,
                          UsuarioIdentityService usuarioIdentityService) {
        this.dashService = dashService;
        this.tokenClaimsService = tokenClaimsService;
        this.usuarioIdentityService = usuarioIdentityService;
    }

    @GetMapping("/resumo")
    @PreAuthorize("hasAuthority('dash:view')")
    public ResumoGeralDTO obterResumo(Authentication authentication) {
        String email = tokenClaimsService.getEmail(authentication);
        usuarioIdentityService.obterOuCriarUsuario(
                tokenClaimsService.getAuthUserId(authentication),
                email,
                tokenClaimsService.getNome(authentication)
        );
        return dashService.obterResumo(email);
    }
}
