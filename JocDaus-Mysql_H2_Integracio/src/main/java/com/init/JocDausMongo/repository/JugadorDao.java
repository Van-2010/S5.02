package com.init.JocDausMongo.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.entity.Jugador;

@Repository
@Transactional
public interface JugadorDao extends JpaRepository<Jugador,Integer>{

	Boolean existsByNom(String nom);
	Optional<Jugador> findById(int id);
	
}
