package com.init.JocDausMongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.JocDausMongo.Model.Jugador;
import com.init.JocDausMongo.Model.Tirada;
import com.init.JocDausMongo.repository.JugadorDao;
import com.init.JocDausMongo.repository.TiradaDao;

@Service
public class TiradaService {

	@Autowired
	private JugadorDao jugadorDao;

	@Autowired
	private TiradaDao tiradesDao;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	
	public List<Tirada> getAlltirades() {// llista totes les tirades
		// TODO Auto-generated method stub
		return tiradesDao.findAll();
	}	
	// LLISTAR TIRADES	
	public List<Tirada> llistaTirades(Jugador jugador) {
	List<Tirada> tirades =tiradesDao.getTiradaByIdjugador(jugador.getId()) ;
	return tirades;
	}
	
	//JUGADOR FA TIRADA
	public Tirada jugadorFaTirada(long id) {// jugador específic realitza tirada
		Optional<Jugador> optionalJugador = jugadorDao.findById(id);
		if (optionalJugador.isPresent()) {
			Jugador jugador = optionalJugador.get();
			Tirada novatirada = new Tirada(jugador.getId());
		    novatirada.setId(sequenceGeneratorService.generateSequence(Tirada.SEQUENCE_NAME));
		    novatirada.tiradaDaus();
		    jugador.addTirada(novatirada);
		    jugadorDao.save(jugador);
			System.out.println(novatirada.toString());
			return tiradesDao.save(novatirada);
		} else {
			System.out.println("El jugador amb id: " + id + " no existeix");
		}
		return null;
	}
	//ELIMINA TIRADA
	public String deleteTiradesByJugador(long jugador_id){
		Optional<Jugador> optionalJugador = jugadorDao.findById(jugador_id);
		String resposta;
		if (optionalJugador.isPresent()) {	
			Jugador jugador = optionalJugador.get();
			tiradesDao.deleteAll(jugador.getLlistaTirades());
			resposta="La tirada del jugador amb id "+jugador_id+"S'ha eliminat correctament";
			
		}else {
			resposta="Aquest jugador no existeix";
		}
			return resposta;
	}
	
	
}
