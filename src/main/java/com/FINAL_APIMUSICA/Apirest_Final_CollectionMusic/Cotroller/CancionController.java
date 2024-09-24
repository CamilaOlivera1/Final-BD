package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Cotroller;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cancion;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CancionRepository;
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
@RequestMapping("/api/canciones")
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;


    @GetMapping
    public ResponseEntity<Page<Cancion>> listarCanciones(Pageable pageable) {
        return ResponseEntity.ok(cancionRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Cancion> guardarCancion(@Validated @RequestBody Cancion cancion) {

        Cancion cancionGuardada = cancionRepository.save(cancion);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cancionGuardada.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(cancionGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cancion> actualizarCancion(@PathVariable Integer id, @Validated @RequestBody Cancion cancion) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(id);

        if (!cancionOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cancion cancionExistente = cancionOptional.get();

        cancionExistente.setTitulo(cancion.getTitulo());
        cancionExistente.setDuracion(cancion.getDuracion());

        cancionRepository.save(cancionExistente);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCancion(@PathVariable Integer id) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(id);

        if (!cancionOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        cancionRepository.delete(cancionOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> obtenerCancion(@PathVariable Integer id) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(id);

        if (!cancionOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(cancionOptional.get());
    }
}
