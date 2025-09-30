package br.com.fiap.smartmottu.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MotoResponseDto {

    private Long   idMoto;
    private String nmChassi;
    private String placa;
    private String unidade;
    private String status;
    private String modelo;
    private String setor;
    private String qrcode;
}
