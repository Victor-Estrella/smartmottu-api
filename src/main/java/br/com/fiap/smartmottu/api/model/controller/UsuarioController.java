package br.com.fiap.smartmottu.api.model.controller;

import br.com.fiap.smartmottu.dto.LoginRequestDto;
import br.com.fiap.smartmottu.dto.UsuarioRequestDto;
import br.com.fiap.smartmottu.dto.UsuarioResponseDto;
import br.com.fiap.smartmottu.entity.Usuario;
import br.com.fiap.smartmottu.exception.IdNotFoundException;
import br.com.fiap.smartmottu.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UsuarioController implements UsuarioAPi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<UsuarioResponseDto>> listAll() {
    List<Usuario> usuarios = usuarioService.findAll(Sort.by(Sort.Direction.ASC, "idUsuario"));
    List<UsuarioResponseDto> responseDtos = usuarios.stream()
        .map(usuario -> new UsuarioResponseDto(
            usuario.getIdUsuario(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getSenha()))
        .collect(Collectors.toList());
    return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long idUsuario) throws IdNotFoundException {

        return usuarioService.findById(idUsuario)
                .map(usuario -> ResponseEntity.ok(new UsuarioResponseDto(
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto request) {

        Usuario usuario = new Usuario(
                request.getNome(),
                request.getEmail(),
                request.getSenha()
        );

        Usuario savedUsuario = usuarioService.save(usuario);

        UsuarioResponseDto response = new UsuarioResponseDto(
                savedUsuario.getIdUsuario(),
                savedUsuario.getNome(),
                savedUsuario.getEmail(),
                savedUsuario.getSenha()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UsuarioResponseDto> update(Long idUsuario,
                                          @Valid @RequestBody UsuarioRequestDto request) {
        return usuarioService.findById(idUsuario)
                .map(usuario -> {
                    usuario.setNome(request.getNome());
                    usuario.setEmail(request.getEmail());
                    // Só atualiza a senha se vier preenchida
                    if (request.getSenha() != null && !request.getSenha().isBlank()) {
                        usuario.setSenha(request.getSenha());
                    }
                    Usuario updateUser = usuarioService.save(usuario);
                    return ResponseEntity.ok(new UsuarioResponseDto(updateUser.getIdUsuario(),updateUser.getNome(), updateUser.getEmail(), updateUser.getSenha()));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // Buscar usuário por email (agora usando @RequestParam para evitar problemas de path)
    @Override
    public ResponseEntity<UsuarioResponseDto> getByEmail(@RequestParam("email") String email) {
        List<Usuario> usuarios = usuarioService.findAll(Sort.by(Sort.Direction.ASC, "idUsuario"));
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.ok(new UsuarioResponseDto(
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha()
                ));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long idUsuario) throws IdNotFoundException {
        if (usuarioService.findById(idUsuario).isPresent()) {
            usuarioService.delete(idUsuario);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            Map<String, String> result = usuarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
            // Garante que tanto o token quanto o email estejam presentes no response
            Map<String, String> response = new HashMap<>();
            response.put("token", result.get("token"));
            response.put("email", result.get("email"));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/paginacao")
    public ResponseEntity<Page<UsuarioResponseDto>> findAllPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Usuario> paginacaoUsuario = usuarioService.findAllPage(pageRequest);

        Page<UsuarioResponseDto> paginacaoUsuarioDto = paginacaoUsuario.map(usuario ->
                new UsuarioResponseDto(
                        usuario.getIdUsuario(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha()
                )
        );

        return ResponseEntity.ok(paginacaoUsuarioDto);
    }

}