package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "Cantante")
public class Cantante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombreArtistico;
    private String paisOrigen;

    // Relación de muchos a muchos con Genero
    @ManyToMany
    @JoinTable(
            name = "cantante_genero",
            joinColumns = @JoinColumn(name = "cantante_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos = new HashSet<>();

    // Relación de uno a muchos con Album
    @OneToMany(mappedBy = "cantante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();

    //Relacion de uno a uno entre cantante y perfil
    @OneToOne(mappedBy = "cantante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Perfil perfil;

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
        perfil.setCantante(this);
    }

    public void addAlbum(Album album) {
        albums.add(album);
        album.setCantante(this);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setCantante(null);
    }
}
