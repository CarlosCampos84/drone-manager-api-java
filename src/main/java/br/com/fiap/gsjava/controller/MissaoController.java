package br.com.fiap.gsjava.controller;

import br.com.fiap.gsjava.dto.MissaoRequestDTO;
import br.com.fiap.gsjava.dto.MissaoResponseDTO;
import br.com.fiap.gsjava.model.Missao;
import br.com.fiap.gsjava.model.MissaoFilter;
import br.com.fiap.gsjava.service.MissaoService;
import br.com.fiap.gsjava.service.SuprimentoMissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missao")
@Tag(name = "Missões", description = "Endpoint para gerenciamento de missões")
public class MissaoController {

    private final MissaoService service;
    private final SuprimentoMissaoService suprimentoMissaoService;

    public MissaoController(MissaoService service, SuprimentoMissaoService suprimentoMissaoService) {
        this.service = service;
        this.suprimentoMissaoService = suprimentoMissaoService;
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Criar uma nova missão", description = "Cria uma missão com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid MissaoRequestDTO dto) {
        Missao missao = service.cadastrar(dto);
        suprimentoMissaoService.cadastrarSuprimentosMissao(missao, dto.suprimentos());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Listar todas as missões", description = "Retorna uma lista de todas as missões.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de missões retornada com sucesso")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<MissaoResponseDTO>> listarTodos(MissaoFilter filter, Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(filter, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar missão por ID", description = "Retorna os detalhes de uma missão específica com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Missão encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MissaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(MissaoResponseDTO.fromMissao(service.buscarPorId(id)));
    }

    @Transactional
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar missão por ID", description = "Atualiza os dados de uma missão específica com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Missão atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MissaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MissaoRequestDTO dto) {
        Missao missao = service.atualizar(id, dto);
        suprimentoMissaoService.atualizarSuprimentosMissao(missao, dto.suprimentos());
        return ResponseEntity.ok(MissaoResponseDTO.fromMissao(missao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar missão por ID", description = "Remove uma missão específica com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Missão deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/concluir/{id}")
    @Operation(summary = "Conclui missão por ID", description = "Conclui uma missão específica com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Missão concluida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> concluir(@PathVariable Long id) {
        System.out.println(id);
        service.concluir(id);
        return ResponseEntity.noContent().build();
    }
}
