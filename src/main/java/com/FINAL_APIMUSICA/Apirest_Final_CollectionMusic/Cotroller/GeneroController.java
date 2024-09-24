package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Cotroller;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cantante;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Genero;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.GeneroRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CantanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CantanteRepository cantanteRepository;

    @GetMapping
    public ResponseEntity<Page<Genero>> listarGeneros(Pageable pageable) {
        return ResponseEntity.ok(generoRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Genero> guardarGenero(@Validated @RequestBody Genero genero) {
        Genero generoGuardado = generoRepository.save(genero);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(generoGuardado.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(generoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizarGenero(@PathVariable Integer id, @Validated @RequestBody Genero genero) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (!generoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        genero.setId(generoOptional.get().getId());
        generoRepository.save(genero);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGenero(@PathVariable Integer id) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (!generoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        generoRepository.delete(generoOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obtenerGenero(@PathVariable Integer id) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (!generoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(generoOptional.get());
    }

}
