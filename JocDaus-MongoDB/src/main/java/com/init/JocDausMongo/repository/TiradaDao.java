package com.init.JocDausMongo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.Model.Jugador;
import com.init.JocDausMongo.Model.Tirada;

@Repository
@Transactional
public interface TiradaDao extends MongoRepository <Tirada,Long>  {
	
	List<Tirada> getTiradaByIdjugador(long id_jugador);
	
}
