package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter @Getter
@Table(name = "Album", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String titulo;
    private LocalDate fechaLanzamiento;

    // Relación de muchos a uno Album-Cantante
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cantante_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cantante cantante;

    // Relación de uno a muchos de Album a Canciones
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Cancion> canciones = new HashSet<>();


    public void addCancion(Cancion cancion) {
        canciones.add(cancion);
        cancion.setAlbum(this);
    }

    public void removeCancion(Cancion cancion) {
        canciones.remove(cancion);
        cancion.setAlbum(null);
    }
}

