package br.com.fiap.smartmottu.repository;

import br.com.fiap.smartmottu.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    boolean existsByEmailAndSenha(String email, String senha);

    Optional<Usuario> findByEmail(String email);
    
}
