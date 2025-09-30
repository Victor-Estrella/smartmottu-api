package br.com.fiap.smartmottu.api.model.controller;

import br.com.fiap.smartmottu.dto.MotoRequestDto;
import br.com.fiap.smartmottu.dto.MotoResponseDto;
import br.com.fiap.smartmottu.entity.Moto;
import br.com.fiap.smartmottu.exception.IdNotFoundException;
import br.com.fiap.smartmottu.repository.MotoRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MotoController implements MotoAPi {

    @Autowired
    private MotoRepository motoRepository;

    @Override
    public ResponseEntity<List<MotoResponseDto>> listAll() {
        List<MotoResponseDto> resp = motoRepository.findAll().stream()
                .map(m -> {
                    MotoResponseDto dto = new MotoResponseDto();
                    dto.setIdMoto(m.getIdMoto());
                    dto.setNmChassi(m.getNmChassi());
                    dto.setPlaca(m.getPlaca());
                    dto.setUnidade(m.getUnidade());
                    dto.setStatus(m.getStatus());
                    dto.setModelo(m.getModelo());
                    dto.setSetor(m.getSetor());
                    dto.setQrcode(m.getQrcode());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<MotoResponseDto> getById(Long idMoto) throws IdNotFoundException {
        return motoRepository.findById(idMoto)
                .map(m -> {
                    MotoResponseDto dto = new MotoResponseDto();
                    dto.setIdMoto(m.getIdMoto());
                    dto.setNmChassi(m.getNmChassi());
                    dto.setPlaca(m.getPlaca());
                    dto.setUnidade(m.getUnidade());
                    dto.setStatus(m.getStatus());
                    dto.setModelo(m.getModelo());
                    dto.setSetor(m.getSetor());
                    dto.setQrcode(m.getQrcode());
                    return dto;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Moto> create(@Valid @RequestBody MotoRequestDto motoRequestDto) {
        Moto moto = new Moto();
        moto.setNmChassi(motoRequestDto.getNmChassi());
        moto.setPlaca(motoRequestDto.getPlaca());
        moto.setUnidade(motoRequestDto.getUnidade());
        moto.setStatus(motoRequestDto.getStatus());
        moto.setModelo(motoRequestDto.getModelo());
        moto.setSetor(motoRequestDto.getSetor());
        // Gera string para QRCode        
        Moto saved = motoRepository.save(moto);
        String qrString = "ID: " + saved.getIdMoto() + "\nSetor: " + moto.getSetor() + "\nStatus: " + moto.getStatus() +"\nPlaca: " + moto.getPlaca() + "\nChassi: " + moto.getNmChassi();
        saved.setQrcode(qrString);
        motoRepository.save(saved);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<Moto> update(@PathVariable Long idMoto, @Valid @RequestBody MotoRequestDto motoRequestDto) throws IdNotFoundException {
        return motoRepository.findById(idMoto)
                .map(existingMoto -> {
                    existingMoto.setNmChassi(motoRequestDto.getNmChassi());
                    existingMoto.setPlaca(motoRequestDto.getPlaca());
                    existingMoto.setUnidade(motoRequestDto.getUnidade());
                    existingMoto.setStatus(motoRequestDto.getStatus());
                    existingMoto.setModelo(motoRequestDto.getModelo());
                    existingMoto.setSetor(motoRequestDto.getSetor());
                    // Atualiza o QRCode
                    String qrString = "ID: " + (existingMoto.getIdMoto() != null ? existingMoto.getIdMoto() : "") + "\nSetor" + existingMoto.getSetor() + "\nStatus: " + existingMoto.getStatus() + "\nPlaca: " + existingMoto.getPlaca() 
                    				+ "\nChassi: " + existingMoto.getNmChassi();

                    existingMoto.setQrcode(qrString);
                    Moto updated = motoRepository.save(existingMoto);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long idMoto) throws IdNotFoundException {
        Moto moto = motoRepository.findById(idMoto).orElse(null);

        if (moto == null) {
            return ResponseEntity.notFound().build();
        }

        motoRepository.delete(moto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginacao")
    public ResponseEntity<Page<MotoResponseDto>> findAllPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Moto> motosPage = motoRepository.findAll(pageable);

    Page<MotoResponseDto> motosDtoPage = motosPage.map(m -> {
        MotoResponseDto dto = new MotoResponseDto();
        dto.setIdMoto(m.getIdMoto());
        dto.setNmChassi(m.getNmChassi());
        dto.setPlaca(m.getPlaca());
        dto.setUnidade(m.getUnidade());
        dto.setStatus(m.getStatus());
        dto.setModelo(m.getModelo());
        dto.setSetor(m.getSetor());
        dto.setQrcode(m.getQrcode());
        return dto;
    });

    return ResponseEntity.ok(motosDtoPage);
    }

}

