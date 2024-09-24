package com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Repository;

import com.FINAL_APIMUSICA.Apirest_Final_CollectionMusic.Entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
