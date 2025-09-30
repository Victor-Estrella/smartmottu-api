package br.com.fiap.smartmottu.api.model.controller;

import br.com.fiap.smartmottu.dto.MotoRequestDto;
import br.com.fiap.smartmottu.dto.MotoResponseDto;
import br.com.fiap.smartmottu.entity.Moto;
import br.com.fiap.smartmottu.exception.IdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/motos")
@Tag(name = "moto", description = "CRUD de motos")
public interface MotoAPi {

    @Operation(summary = "Listar todas as motos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de motos retornada com sucesso")
    })
    
    @GetMapping
    ResponseEntity<List<MotoResponseDto>> listAll();

    @Operation(summary = "Buscar moto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto encontrada"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    
    @GetMapping("/{idMoto}")
    ResponseEntity<MotoResponseDto> getById(@PathVariable("idMoto") Long idMoto) throws IdNotFoundException;

    @Operation(summary = "Criar uma nova moto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Moto criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    
    @PostMapping
    ResponseEntity<Moto> create(@Valid @RequestBody MotoRequestDto motoRequestDto);

    @Operation(summary = "Atualizar uma moto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    
    @PutMapping("/{idMoto}")
    ResponseEntity<Moto> update(
            @PathVariable("idMoto") Long idMoto,
            @Valid @RequestBody MotoRequestDto motoRequestDto
    ) throws IdNotFoundException;

    @Operation(summary = "Excluir uma moto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Moto excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    
    @DeleteMapping("/{idMoto}")
    ResponseEntity<Void> delete(@PathVariable("idMoto") Long idMoto) throws IdNotFoundException;



}
