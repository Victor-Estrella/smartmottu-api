package br.com.fiap.smartmottu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MotoRequestDto {

    @NotBlank
    @Size(min = 17, max = 17)
    private String nmChassi;

    @NotBlank @Size(min = 7, max = 7)
    private String placa;

    @NotBlank
    private String status;

    @NotBlank
    private String modelo;

    @NotBlank
    private String unidade;
    

    @NotBlank
    private String setor;

}
