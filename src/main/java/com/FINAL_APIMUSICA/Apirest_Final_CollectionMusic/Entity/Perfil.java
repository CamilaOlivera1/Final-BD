package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Setter @Getter
@Table(name = "Perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private int edad;
    private String instagram;
    private String youtube;



    //RELACION DE UNO A UNO ENTRE CANTANTE Y PERFIL
    @OneToOne
    @JoinColumn(name = "cantante_id")
    @JsonBackReference
    private Cantante cantante;


}