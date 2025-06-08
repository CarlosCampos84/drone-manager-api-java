package br.com.fiap.gsjava.controller;

import br.com.fiap.gsjava.dto.LoginDTO;
import br.com.fiap.gsjava.dto.UsuarioRequestDTO;
import br.com.fiap.gsjava.dto.TokenDTO;
import br.com.fiap.gsjava.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Endpoint para registro e login de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Retorna um token JWT válido se as credenciais estiverem corretas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token JWT", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Username não encontrado", content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        String token = usuarioService.loginUsuario(dto);
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado com este email", content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> registrarUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {
        usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
