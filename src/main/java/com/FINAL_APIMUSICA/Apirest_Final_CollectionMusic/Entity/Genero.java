package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Setter @Getter
@Table(name = "Genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nombre;

    // Relación ManyToMany con Cantante
    @ManyToMany(mappedBy = "generos")
    @JsonIgnore
    private List<Cantante> cantantes;
}
