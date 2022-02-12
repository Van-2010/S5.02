package com.init.JocDausMongo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.Model.Jugador;

@Repository
@Transactional
public interface JugadorDao extends MongoRepository <Jugador,Long>{

	Boolean existsByNom(String nom);
	Optional<Jugador> findById(long id);
}
