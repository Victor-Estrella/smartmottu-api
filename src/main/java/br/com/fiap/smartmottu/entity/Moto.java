package br.com.fiap.smartmottu.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_SMARTMOTTU_MOTO")
@SequenceGenerator(name = "moto", sequenceName = "SQ_T_SMARTMOTTU_MOTO", allocationSize = 1)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Moto {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moto")
    @Column(name = "id_moto")
    private Long idMoto;

    @Column(name = "nm_chassi", length = 17, nullable = false)
    private String nmChassi;

    @Column(name = "placa", length = 7, nullable = false)
    private String placa;

    @Column(name = "unidade", length = 100)
    private String unidade;

    @JoinColumn(name = "status")
    private String status;

    @JoinColumn(name = "modelo")
    private String modelo;

    @Column(name = "setor", length = 100, nullable = false)
    private String setor;

    @Column(name = "qrcode", length = 500)
    private String qrcode;
}
