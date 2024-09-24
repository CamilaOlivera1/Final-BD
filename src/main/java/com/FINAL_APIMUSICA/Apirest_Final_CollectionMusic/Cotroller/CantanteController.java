package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Cotroller;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Album;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cantante;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Genero;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Perfil;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.AlbumRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CantanteRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.GeneroRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/cantantes")
public class CantanteController {

    @Autowired
    private CantanteRepository cantanteRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping
    public ResponseEntity<Page<Cantante>> listarCantantes(Pageable pageable) {
        return ResponseEntity.ok(cantanteRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Cantante> guardarCantante(@Validated @RequestBody Cantante cantante) {
        Cantante cantanteGuardado = cantanteRepository.save(cantante);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cantanteGuardado.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(cantanteGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cantante> actualizarCantante(@PathVariable Integer id, @Validated @RequestBody Cantante cantante) {
        if (!cantanteRepository.existsById(id)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        cantante.setId(id);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCantante(@PathVariable Integer id) {
        if (!cantanteRepository.existsById(id)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        cantanteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cantante> obtenerCantante(@PathVariable Integer id) {
        return cantanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping("/{cantanteId}/albumes")
    public ResponseEntity<Set<Album>> obtenerAlbumesDeCantante(@PathVariable Integer cantanteId) {
        return cantanteRepository.findById(cantanteId)
                .map(cantante -> ResponseEntity.ok(cantante.getAlbums()))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping("/{cantanteId}/albumes/{albumId}")
    public ResponseEntity<Void> asignarAlbumACantante(@PathVariable Integer cantanteId, @PathVariable Integer albumId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);
        Optional<Album> albumOptional = albumRepository.findById(albumId);

        if (cantanteOptional.isEmpty() || albumOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        Album album = albumOptional.get();

        cantante.getAlbums().add(album);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cantanteId}/albumes/{albumId}")
    public ResponseEntity<Void> removerAlbumDeCantante(@PathVariable Integer cantanteId, @PathVariable Integer albumId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);
        Optional<Album> albumOptional = albumRepository.findById(albumId);

        if (cantanteOptional.isEmpty() || albumOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        Album album = albumOptional.get();

        cantante.getAlbums().remove(album);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cantanteId}/generos")
    public ResponseEntity<Set<Genero>> obtenerGenerosDeCantante(@PathVariable Integer cantanteId) {
        return cantanteRepository.findById(cantanteId)
                .map(cantante -> ResponseEntity.ok(cantante.getGeneros()))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping("/{cantanteId}/generos/{generoId}")
    public ResponseEntity<Void> asignarGeneroACantante(@PathVariable Integer cantanteId, @PathVariable Integer generoId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);
        Optional<Genero> generoOptional = generoRepository.findById(generoId);

        if (cantanteOptional.isEmpty() || generoOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        Genero genero = generoOptional.get();

        cantante.getGeneros().add(genero);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cantanteId}/generos/{generoId}")
    public ResponseEntity<Void> removerGeneroDeCantante(@PathVariable Integer cantanteId, @PathVariable Integer generoId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);
        Optional<Genero> generoOptional = generoRepository.findById(generoId);

        if (cantanteOptional.isEmpty() || generoOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        Genero genero = generoOptional.get();

        cantante.getGeneros().remove(genero);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<Perfil> obtenerPerfilDeCantante(@PathVariable Integer id) {
        return cantanteRepository.findById(id)
                .map(cantante -> ResponseEntity.ok(cantante.getPerfil()))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping("/{cantanteId}/perfil/{perfilId}")
    public ResponseEntity<Void> asignarPerfilACantante(@PathVariable Integer cantanteId, @PathVariable Integer perfilId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);
        Optional<Perfil> perfilOptional = perfilRepository.findById(perfilId);

        if (cantanteOptional.isEmpty() || perfilOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        Perfil perfil = perfilOptional.get();

        cantante.setPerfil(perfil);
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cantanteId}/perfil")
    public ResponseEntity<Void> removerPerfilDeCantante(@PathVariable Integer cantanteId) {
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(cantanteId);

        if (cantanteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Cantante cantante = cantanteOptional.get();
        cantante.setPerfil(null); // Remueve el perfil
        cantanteRepository.save(cantante);

        return ResponseEntity.noContent().build();
    }

}
