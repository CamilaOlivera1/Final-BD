package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancionRepository extends JpaRepository <Cancion, Integer> {
}
