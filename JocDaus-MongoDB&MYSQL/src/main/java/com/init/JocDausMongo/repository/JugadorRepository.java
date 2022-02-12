package com.init.JocDausMongo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.Model.JugadorModel;
import com.init.JocDausMongo.entity.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Integer>{

	//Boolean existsById(int id);
	//Boolean existsByNom(String nom);
	//public Optional <Jugador> findById(int id);
	public Jugador findById(int id);

	boolean existsByNom(String nom);
}
