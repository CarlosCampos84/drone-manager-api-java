package br.com.fiap.gsjava.controller;

import br.com.fiap.gsjava.dto.SuprimentoRequestDTO;
import br.com.fiap.gsjava.dto.SuprimentoResponseDTO;
import br.com.fiap.gsjava.model.Suprimento;
import br.com.fiap.gsjava.service.SuprimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suprimento")
@Tag(name = "Suprimentos", description = "Endpoint para gerenciamento de suprimentos")
public class SuprimentoController {

    private final SuprimentoService service;

    public SuprimentoController(SuprimentoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Criar um novo suprimento",
            description = "Cria um suprimento com os dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Suprimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid SuprimentoRequestDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os suprimentos",
            description = "Retorna uma lista de todos os suprimentos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de suprimentos retornada com sucesso")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<SuprimentoResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar suprimento por ID",
            description = "Retorna os detalhes de um suprimento específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suprimento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Suprimento não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<SuprimentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(SuprimentoResponseDTO.fromSuprimento(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar suprimento por ID",
            description = "Atualiza os dados de um suprimento específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suprimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Suprimento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<SuprimentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SuprimentoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar suprimento por ID",
            description = "Remove um suprimento específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suprimento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Suprimento não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
