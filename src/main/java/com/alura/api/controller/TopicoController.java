package com.alura.api.controller;

import com.alura.api.mapper.TopicoMapper;
import com.alura.api.model.topico.Topico;
import com.alura.api.model.topico.TopicoCreacionDTO;
import com.alura.api.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Topico>> mostrarTodosLosTopicos(){
        return ResponseEntity.ok(topicoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> mostrarTopico(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()){
            Topico topico = optional.get();
        }
        else {
            throw new EntityNotFoundException("Not found");
        }

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping
    public ResponseEntity guardarTopico(@RequestBody @Valid TopicoCreacionDTO topicoCreacionDTO, UriComponentsBuilder uriComponentsBuilder){
        Topico topicoAGuardar =  TopicoMapper.dtoToEntityTopico(topicoCreacionDTO);
        this.topicoRepository.save(topicoAGuardar);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoAGuardar.getId()).toUri();
        return ResponseEntity.created(uri).body(topicoAGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody TopicoCreacionDTO topicoCreacionDTO){
        Optional<Topico> optional = topicoRepository.findById(id);
        Topico topico;

        if(optional.isPresent()){
            topico = optional.get();
            topico.actualizarTopico(topicoCreacionDTO.titulo(),topicoCreacionDTO.mensaje(),topicoCreacionDTO.autor(),topicoCreacionDTO.curso());
            topicoRepository.save(topico);
        }
        else {
            throw new EntityNotFoundException("Not found");
        }
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        Topico topico;

        if(optional.isPresent()){
            topicoRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("Not found");
        }

        return ResponseEntity.noContent().build();
    }

}
