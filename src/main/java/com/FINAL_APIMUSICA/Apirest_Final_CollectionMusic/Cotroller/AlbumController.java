package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Cotroller;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Album;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cancion;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cantante;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.AlbumRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CancionRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CantanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CantanteRepository cantanteRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @GetMapping
    public ResponseEntity<Page<Album>> listarAlbums(Pageable pageable) {
        return ResponseEntity.ok(albumRepository.findAll(pageable));
    }


    @PostMapping
    public ResponseEntity<Album> guardarAlbum(@RequestBody Album album) {
        // Validar que el cantante está presente
        if (album.getCantante() == null || album.getCantante().getId() == 0) {
            return ResponseEntity.unprocessableEntity().build(); // Cantante es obligatorio
        }

        // Verificar si el cantante existe
        Optional<Cantante> cantanteOptional = cantanteRepository.findById(album.getCantante().getId());
        if (cantanteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Asignar el cantante existente al álbum
        album.setCantante(cantanteOptional.get());

        Album albumGuardado = albumRepository.save(album);
        return ResponseEntity.ok(albumGuardado);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Album> obtenerAlbum(@PathVariable Integer id) {
        return albumRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Album> actualizarAlbum(@PathVariable Integer id, @RequestBody Album album) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Mantener el ID del álbum actual y el cantante asociado
        album.setId(id);
        Album albumActualizado = albumRepository.save(album);

        return ResponseEntity.ok(albumActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlbum(@PathVariable Integer id) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        albumRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{albumId}/canciones")
    public ResponseEntity<Set<Cancion>> obtenerCancionesDeAlbum(@PathVariable Integer albumId) {
        return albumRepository.findById(albumId)
                .map(album -> ResponseEntity.ok(album.getCanciones()))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }


    @PostMapping("/{albumId}/canciones/{cancionId}")
    public ResponseEntity<Void> agregarCancionAAlbum(@PathVariable Integer albumId, @PathVariable Integer cancionId) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        Optional<Cancion> cancionOptional = cancionRepository.findById(cancionId);

        if (albumOptional.isEmpty() || cancionOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Album album = albumOptional.get();
        Cancion cancion = cancionOptional.get();

        // Evitar duplicados
        if (!album.getCanciones().contains(cancion)) {
            album.addCancion(cancion);
            albumRepository.save(album);
        }

        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/{albumId}/canciones/{cancionId}")
    public ResponseEntity<Void> eliminarCancionDeAlbum(@PathVariable Integer albumId, @PathVariable Integer cancionId) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        Optional<Cancion> cancionOptional = cancionRepository.findById(cancionId);

        if (albumOptional.isEmpty() || cancionOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Album album = albumOptional.get();
        Cancion cancion = cancionOptional.get();

        album.removeCancion(cancion);
        albumRepository.save(album);

        return ResponseEntity.noContent().build();
    }

}