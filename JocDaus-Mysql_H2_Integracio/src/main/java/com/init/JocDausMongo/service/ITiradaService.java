package com.init.JocDausMongo.service;

import java.util.List;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;

public interface ITiradaService {
	
	List<Tirada> getAlltirades();

	//public List<Tirada> getTiradesByIdJugador(Jugador jugador);
	
	//public Tirada updateTirades(int id, Tirada tirades);

	public String deleteTiradesByJugador(int jugador_id);
	
	public Tirada jugadorFaTirada(int jugador_id);

	public List<Tirada> llistaTirades(Jugador jugador);

	//List<Tirada> getTiradesById_iugador(int jugador_id); 

}

