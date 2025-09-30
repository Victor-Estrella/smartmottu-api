package br.com.fiap.smartmottu.api.model.controller;

import br.com.fiap.smartmottu.dto.LoginRequestDto;
import br.com.fiap.smartmottu.dto.UsuarioRequestDto;
import br.com.fiap.smartmottu.dto.UsuarioResponseDto;
import br.com.fiap.smartmottu.exception.IdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários")
public interface UsuarioAPi {


    @Operation(summary = "Listar todos os usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    ResponseEntity<List<UsuarioResponseDto>> listAll();

    
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{idUsuario}")
    ResponseEntity<UsuarioResponseDto> getById(@PathVariable("idUsuario") Long idUsuario) throws IdNotFoundException;

    
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto requestDto);

       
    @Operation(summary = "Atualizar um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{idUsuario}")
    ResponseEntity<UsuarioResponseDto> update(
            @PathVariable("idUsuario") Long idUsuario,
            @Valid @RequestBody UsuarioRequestDto requestDto
    );

    
    @Operation(summary = "Excluir um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{idUsuario}")
    ResponseEntity<Void> delete(@PathVariable("idUsuario") Long idUsuario) throws IdNotFoundException;


    @Operation(summary = "Fazer login de usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDto loginRequest);


    @Operation(summary = "Buscar usuário por email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/email")
    ResponseEntity<UsuarioResponseDto> getByEmail(@RequestParam("email") String email);
}
