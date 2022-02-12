package com.init.JocDausMongo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.init.JocDausMongo.entity.Jugador;

public interface IJugadorService {
	
	List<Jugador> getAllJugadors(); //GET /players/: retorna el llistat de tots  els jugadors del sistema  amb el seu  percentatge mig  d’èxits
	
	Optional<Jugador> findJugadorById(int id);//GET Llegeix les dades d'un jugador
	
	public Map<String, Double> getLlistaPercentatgeMigJugadors(); //percentatge exits
	
	public Double getRankingJugadors();//ranking
	
	public Jugador saveJugador(Jugador jugador);//Crea i guarda un jugador
	
	public Jugador updateJugador(int id, Jugador jugador); //PUT /players : modifica el nom del jugador , actualitza dades
	
	Optional<Jugador> findJugadorById_tirades(int id_tirades);//Busca jugador per tirada

	Jugador updateJugadorNom(int idJugador, String nom); //modifica nom de jugador

	Jugador getJugadorGuanya(List<Jugador> jugador); //ranking jugador guanya

	Jugador getJugadorPerd(List<Jugador> jugador);//ranking jugador perd
	 
}
