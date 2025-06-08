package br.com.fiap.gsjava.controller;

import br.com.fiap.gsjava.dto.DroneRequestDTO;
import br.com.fiap.gsjava.dto.DroneResponseDTO;
import br.com.fiap.gsjava.model.DroneFilter;
import br.com.fiap.gsjava.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drone")
@Tag(name = "Drones", description = "Endpoint para gerenciamento de drones")
public class DroneController {

    private final DroneService service;

    public DroneController(DroneService service) {
        this.service = service;
    }

    @PostMapping
    @CacheEvict(value = "drones", allEntries = true)
    @Operation(
            summary = "Criar um novo drone",
            description = "Cria um drone com os dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Drone criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para criar drone"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid DroneRequestDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Cacheable(value = "drones")
    @Operation(
            summary = "Listar todos os drones",
            description = "Retorna uma lista paginada de drones com base nos filtros fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de drones retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para listar drones"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<DroneResponseDTO>> listarTodos(DroneFilter filter, Pageable pageable) {
        Page<DroneResponseDTO> drones = service.findAll(filter, pageable);
        return ResponseEntity.ok(drones);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar drone por ID",
            description = "Retorna os detalhes de um drone específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drone encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Drone não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para buscar drone")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<DroneResponseDTO> buscarDronePorId(@PathVariable Long id) {
        return ResponseEntity.ok(DroneResponseDTO.fromDrone(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "drones", allEntries = true)
    @Operation(
            summary = "Atualizar drone por ID",
            description = "Atualiza os dados de um drone específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drone atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Drone não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para atualizar drone"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<DroneResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DroneRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "drones", allEntries = true)
    @Operation(
            summary = "Deletar drone por ID",
            description = "Remove um drone específico com base no ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Drone deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Drone não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para deletar drone")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}