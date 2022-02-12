package com.init.JocDausMongo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;

@Repository
@Transactional
public interface TiradaDao extends JpaRepository<Tirada,Integer>  {
	
	public List<Tirada> getLlistaTiradesByJugadorIdJugador(int jugador_id);

	public void deleteTiradesByJugadorIdJugador(int jugador_id);

}
