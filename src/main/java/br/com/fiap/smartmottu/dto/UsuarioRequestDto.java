package br.com.fiap.smartmottu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioRequestDto {

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @Size(min = 8, max = 15)
    private String senha;



}
