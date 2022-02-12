package com.init.JocDausMongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;
import com.init.JocDausMongo.repository.JugadorDao;
import com.init.JocDausMongo.repository.TiradaDao;

@Service
public class TiradaServiceImp implements ITiradaService {

	@Autowired
	private JugadorDao jugadorDao;

	@Autowired
	private TiradaDao tiradesDao;

	@Override
	public List<Tirada> getAlltirades() {// llista totes les tirades
		// TODO Auto-generated method stub
		return tiradesDao.findAll();
	}	
	// LLISTAR TIRADES	
	@Override
	public List<Tirada> llistaTirades(Jugador jugador) {
	List<Tirada> tirades = jugador.getLlistaTirades();
	return tirades;
	}
	//JUGADOR FA TIRADA
	@Override
	public Tirada jugadorFaTirada(int jugador_id) {// jugador específic realitza tirada
		Optional<Jugador> optionalJugador = jugadorDao.findById(jugador_id);
		if (optionalJugador.isPresent()) {
			Jugador jugador = optionalJugador.get();
			Tirada novatirada = new Tirada(jugador);
			novatirada.tiradaDaus();
			// System.out.println(novatirada.toString());
			return tiradesDao.save(novatirada);
		} else {
			System.out.println("El jugador amb id: " + jugador_id + " no existeix");
		}
		return null;
	}
	//ELIMINA TIRADA
	public String deleteTiradesByJugador(int jugador_id){
		Optional<Jugador> optionalJugador = jugadorDao.findById(jugador_id);
		if (optionalJugador.isPresent()) {
			Jugador jugador = optionalJugador.get();
			tiradesDao.deleteTiradesByJugadorIdJugador(jugador_id);
			System.out.println("La tirada del jugador amb id "+jugador_id+"S'ha eliminat correctament");
		}else {
			System.out.println("Aquest jugador no existeix");
		}
			return null;
	}
	@Override
	public List<Tirada> getTiradesByIdJugador(Jugador jugador) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
