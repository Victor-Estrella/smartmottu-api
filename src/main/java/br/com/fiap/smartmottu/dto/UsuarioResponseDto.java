package br.com.fiap.smartmottu.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioResponseDto {

    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;

}
