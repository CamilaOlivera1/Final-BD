package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@Setter @Getter
@Table(name = "Cancion")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String titulo;
    private String duracion;

    // Relación de muchos a uno entre Álbum y Canción
    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonBackReference
    private Album album;


    public void setAlbum(Album album) {
        this.album = album;
    }

}
