package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Cotroller;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cantante;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Perfil;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.CantanteRepository;
import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/perfil")
public class PerfilController {


    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private CantanteRepository cantanteRepository;


    @GetMapping
    public ResponseEntity<Page<Perfil>> listarPerfiles(Pageable pageable) {
        return ResponseEntity.ok(perfilRepository.findAll(pageable));
    }


    @PostMapping
    public ResponseEntity<Perfil> guardarPerfil(@Validated @RequestBody Perfil perfil) {
        Perfil perfilGuardado = perfilRepository.save(perfil);


        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(perfilGuardado.getId()).toUri();


        return ResponseEntity.created(ubicacion).body(perfilGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> actualizarPerfil(@PathVariable Integer id, @Validated @RequestBody Perfil perfil) {
        if (!perfilRepository.existsById(id)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Encuentra el perfil existente
        Perfil perfilExistente = perfilRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado"));

        // Actualiza los atributos del perfil existente
        perfilExistente.setNombre(perfil.getNombre());
        perfilExistente.setApellido(perfil.getApellido());
        perfilExistente.setFechaNacimiento(perfil.getFechaNacimiento());
        perfilExistente.setEdad(perfil.getEdad());
        perfilExistente.setInstagram(perfil.getInstagram());
        perfilExistente.setYoutube(perfil.getYoutube());

        perfilRepository.save(perfilExistente);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Integer id) {
        // Buscar el perfil por su ID
        Optional<Perfil> perfilOptional = perfilRepository.findById(id);

        // Si no existe, devolver un estado 422
        if (perfilOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Obtener el perfil
        Perfil perfil = perfilOptional.get();

        // Verificar si el perfil tiene un cantante asociado
        if (perfil.getCantante() != null) {
            // Desvincular el perfil del cantante
            Cantante cantante = perfil.getCantante();
            cantante.setPerfil(null); // Establecer el perfil del cantante como null
            cantanteRepository.save(cantante); // Guardar el cantante sin el perfil
        }

        // Eliminar el perfil de la base de datos
        perfilRepository.deleteById(id);

        // Devolver una respuesta vacía (204 No Content)
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> obtenerPerfil(@PathVariable Integer id) {
        return perfilRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }
}
