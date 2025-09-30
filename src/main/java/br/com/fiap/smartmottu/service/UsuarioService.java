package br.com.fiap.smartmottu.service;

import br.com.fiap.smartmottu.entity.Usuario;
import br.com.fiap.smartmottu.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Cacheable(cacheNames = "motos", key = "#idUsuario")
    public List<Usuario> findAll(Sort idUsuario) {
        return usuarioRepository.findAll();
    }

    @Cacheable(cacheNames = "motos", key = "#idUsuario")
    public Optional<Usuario> findById(Long idUsuario) {
        return  usuarioRepository.findById(idUsuario);
    }

    public Page<Usuario> findAllPage(PageRequest request) {
        return usuarioRepository.findAll(request);
    }

    @CachePut(cacheNames = "usuarios", key = "#usuario.idUsuario")
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @CacheEvict(cacheNames = "usuarios", key = "#idUsuario")
    public void delete(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
    
   
	private final Key jwtSecret = Keys.hmacShaKeyFor("suaChaveSecretaSuperSecreta1234567890".getBytes());
	
    public Map<String, String> autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha)) {
            Usuario usuario = usuarioOpt.get();
            String token = Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getIdUsuario())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(jwtSecret)
                .compact();
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("email", usuario.getEmail());
            return result;
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }
	


}
