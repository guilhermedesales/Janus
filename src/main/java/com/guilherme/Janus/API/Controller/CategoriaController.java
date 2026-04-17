package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaSimplesDTO;
import com.guilherme.Janus.Application.DTOs.Categoria.CriarCategoriaDTO;
import com.guilherme.Janus.Application.Mapping.EntityMapper;
import com.guilherme.Janus.Application.Service.CategoriaService;
import com.guilherme.Janus.Application.Service.TokenClaimsService;
import com.guilherme.Janus.Application.Service.UsuarioIdentityService;
import com.guilherme.Janus.Domain.Entities.Categoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de tarefas")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final EntityMapper mapping;
    private final TokenClaimsService tokenClaimsService;
    private final UsuarioIdentityService usuarioIdentityService;

    public CategoriaController(CategoriaService categoriaService,
                               EntityMapper mapping,
                               TokenClaimsService tokenClaimsService,
                               UsuarioIdentityService usuarioIdentityService) {
        this.categoriaService = categoriaService;
        this.mapping = mapping;
        this.tokenClaimsService = tokenClaimsService;
        this.usuarioIdentityService = usuarioIdentityService;
    }

    // cria uma categoria
    @PostMapping("/criar")
    @PreAuthorize("hasAuthority('categoria:create') or hasAuthority('ROLE_GLOBAL_ADMIN')")
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria de tarefa para o usuário autenticado")
    public CriarCategoriaDTO criarCategoria(@RequestBody CriarCategoriaDTO dto, Authentication authentication) {

        String email = syncUsuario(authentication);
        Categoria categoria = categoriaService.criarCategoria(email, dto);
        return mapping.toDto(categoria);
    }

    // lista todas as categoria (sem mostrar tarefas)
    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('categoria:view') or hasAuthority('ROLE_GLOBAL_ADMIN')")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias de tarefas do usuário autenticado")
    public List<CategoriaSimplesDTO> listarCategorias(Authentication authentication) {
        String email = syncUsuario(authentication);
        List<Categoria> categorias = categoriaService.listarCategorias(email);
        return mapping.toCategoriaSimplesDTOList(categorias);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasAuthority('categoria:view') or hasAuthority('ROLE_GLOBAL_ADMIN')")
    @Operation(summary = "Buscar categoria por ID", description = "Busca uma categoria específica pelo ID")
    public CategoriaDTO buscarCategoriaPorId(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        Categoria categoria = categoriaService.buscarCategoriaPorId(email, id);
        return mapping.toCategoriaDTO(categoria);
    }

    // atualiza uma categoria
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAuthority('categoria:update') or hasAuthority('ROLE_GLOBAL_ADMIN')")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    public CriarCategoriaDTO atualizarCategoria(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            @RequestBody CriarCategoriaDTO dto,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        Categoria categoria = categoriaService.atualizarCategoria(email, id, dto);
        return mapping.toDto(categoria);
    }

    // deleta uma categoria
    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('categoria:delete') or hasAuthority('ROLE_GLOBAL_ADMIN')")
    @Operation(summary = "Deletar categoria", description = "Deleta uma categoria pelo ID")
    public void deletarCategoria(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        categoriaService.deletarCategoria(email, id);
    }

    private String syncUsuario(Authentication authentication) {
        UUID authUserId = tokenClaimsService.getAuthUserId(authentication);
        String email = tokenClaimsService.getEmail(authentication);
        String nome = tokenClaimsService.getNome(authentication);
        usuarioIdentityService.obterOuCriarUsuario(authUserId, email, nome);
        return email;
    }
}
