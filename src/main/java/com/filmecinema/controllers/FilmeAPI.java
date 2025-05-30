package com.filmecinema.controllers;

import com.filmecinema.models.Filme;
import com.filmecinema.models.Sessao;
import com.filmecinema.services.FilmeService;
import com.filmecinema.services.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/filmes")
public class FilmeAPI {

    private final FilmeService filmeService;
    private final SessaoService sessaoService;

    @Autowired
    public FilmeAPI(FilmeService filmeService, SessaoService sessaoService) {
        this.filmeService = filmeService;
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<Filme> saveNew(@RequestBody @Valid Filme filme, BindingResult result) {
        return ResponseEntity.ok(this.filmeService.save(filme, result));
    }

    @DeleteMapping(value = "/{codigo}/delete")
    public ResponseEntity<Void> delete(@PathVariable("codigo") long codigo) {
        Filme filme = filmeService.findByCodigo(codigo);
        filmeService.delete(filme);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Filme>> listaFilmes() {
        Iterable<Filme> filmes = filmeService.findAll();
        return ResponseEntity.ok(filmes);
    }

    @GetMapping(value = "/cartaz")
    public ResponseEntity<Iterable<Filme>> listaFilmeCartaz() {
        Iterable<Filme> filmes = filmeService.findByEmCartaz(true);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity getFilmeByCode(@PathVariable("codigo") Long codigo) {
        Filme filme = filmeService.findByCodigo(codigo);
        return ResponseEntity.ok(filme);
    }

    @PostMapping(value = "/{codigo}")
    public ResponseEntity detalhesFilmePost(@PathVariable("codigo") Long codigo, @Valid Sessao sessao, BindingResult result) {
        Filme filme = filmeService.findByCodigo(codigo);
        sessaoService.save(sessao, result);
        return ResponseEntity.ok(filme);
    }

    @PatchMapping(value = "/{codigo}")
    public ResponseEntity patchFilmeByCode(@PathVariable("codigo") Long codigo, @RequestBody Map<String, Object> updates, BindingResult result) {
        Filme filme = filmeService.findByCodigo(codigo);
        updates.forEach((key, value) -> {
            switch (key) {
                case "nome":
                    filme.setNome((String) value);
                    break;
                case "dataInicio":
                    filme.setDataInicio((LocalDate) value);
                    break;
                case "dataTermino":
                    filme.setDataTermino((LocalDate) value);
                    break;
            }
        });
        Filme filmeAtualizado = filmeService.save(filme, result);
        return ResponseEntity.ok(filmeAtualizado);
    }

}
