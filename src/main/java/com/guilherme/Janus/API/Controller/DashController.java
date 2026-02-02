package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Dash.ResumoGeralDTO;
import com.guilherme.Janus.Application.Service.DashService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dash")
public class DashController {

    private final DashService dashService;

    public DashController(DashService dashService) {
        this.dashService = dashService;
    }

    @GetMapping("/resumo")
    public ResumoGeralDTO obterResumo(Authentication authentication) {
        String email = authentication.getName();
        return dashService.obterResumo(email);
    }
}
