package com.esercizio.agenzia.turismo.controller;

import com.esercizio.agenzia.turismo.domain.Cliente;
import com.esercizio.agenzia.turismo.domain.Spettacolo;
import com.esercizio.agenzia.turismo.service.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class SpettacoloController {

    @Autowired
    SpettacoloService spettacoloService;

    @GetMapping(path = "/{id}")
    ResponseEntity<Spettacolo> findById(@PathVariable Long id) {
        Spettacolo spettacolo1 = spettacoloService.findById(id);
        return new ResponseEntity<>(spettacolo1, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<?> findAll() {
        return new ResponseEntity<>(spettacoloService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Spettacolo> saveOne(@RequestParam int n_prenotazioni) {
        Spettacolo spettacolo1 = spettacoloService.saveOne(n_prenotazioni);
        return new ResponseEntity<>(spettacolo1, HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<String> prenota(@RequestParam Long id, @RequestParam String name, @RequestParam String tel) {
        spettacoloService.prenota(id, name, tel);
        return new ResponseEntity<String>("Aggiunto", HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<String> disdici(@RequestParam Long id, @RequestParam String name, @RequestParam String tel) {
        spettacoloService.disdici(id, name, tel);
        return new ResponseEntity<String>("Disdetto", HttpStatus.OK);
    }

    @GetMapping("incompleto")
    ResponseEntity<Boolean> incompleto(@RequestParam Long id) {
        return new ResponseEntity<Boolean>(spettacoloService.incompleto(id), HttpStatus.OK);
    }

}
