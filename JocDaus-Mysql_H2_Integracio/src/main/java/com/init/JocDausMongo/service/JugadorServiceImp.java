package com.init.JocDausMongo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;
import com.init.JocDausMongo.repository.JugadorDao;
import com.init.JocDausMongo.repository.TiradaDao;

@Service
public class JugadorServiceImp implements IJugadorService {

	@Autowired
	private JugadorDao jugadorDao;

	@Autowired
	private TiradaDao tiradesDao;

	Jugador jugador;
	Tirada tirades;

	// Llista tots els jugadors
	@Override
	public List<Jugador> getAllJugadors() {
		List<Jugador> totsJugadors = jugadorDao.findAll();
		return totsJugadors;
	}

	// Llista jugadors per id
	@Override
	public Optional<Jugador> findJugadorById(int id) {// llista tots jugadors
		Optional<Jugador> jugador = jugadorDao.findById(id);
		return jugador;
	}

	// 1.GUARDA UN JUGADOR
	@Override
	public Jugador saveJugador(Jugador jugador) {
		if (jugador.getNom() == null) {
			jugador.setNom("Anonim");
			return jugadorDao.save(jugador);

		} else if (jugadorDao.existsByNom(jugador.getNom())) {
			System.out.println("El jugador " + jugador.getNom() + " ja existeix");
		} else {
			return jugadorDao.save(jugador);
		}
		return jugador;
	}

	// 1.CREA JUGADOR
	public Jugador creaJugador(Jugador jugador) {
		List<Jugador>jugadors=jugadorDao.findAll();	
		if (jugador.getNom() == null) {
			jugador.setNom("Anonim");
			jugadorDao.save(jugador);
		} else if (jugadorDao.existsByNom(jugador.getNom())) {
			System.out.println("El jugador " + jugador.getNom() + " ja existeix");
		} else {
			return jugadorDao.save(jugador);
		}
		return (jugador);
	}

	// MODIFICA NOM JUGADOR
	@Override
	public Jugador updateJugadorNom(int idJugador, String nom) {
		Optional<Jugador> jugadorOptional = jugadorDao.findById(idJugador);
		if (!jugadorOptional.isEmpty()) {
			Jugador updateJugador = jugadorOptional.get();
			updateJugador.setNom(nom);
			return jugadorDao.save(updateJugador);
		} else {
			System.out.println("El jugador amb id " + idJugador + " no existeix");		
		}
		return null;
	}

	// JUGADOR MILLOR PERCENTATGE
	@Override
	public Jugador getJugadorGuanya(List<Jugador> jugador) {// millor jugador
		jugador.sort(Comparator.comparing(Jugador::getPorcentatgeExitJugador).reversed());
		return jugador.get(0);
	}

	// JUGADOR PITJOR PERCENTATGE
	@Override
	public Jugador getJugadorPerd(List<Jugador> jugador) {// pitjor jugador
		jugador.sort(Comparator.comparing(Jugador::getPorcentatgeExitJugador));
		return jugador.get(0);
	}

	// BUSCA JUGADOR PER ID
	@Override
	public Optional<Jugador> findJugadorById_tirades(int id_tirades) {
		return jugadorDao.findById(id_tirades);
	}

	// RANKING JUGADOR
	public Double getRankingJugadors() {
		Double rankingMig = 0.0;
		List<Jugador> jugadors = jugadorDao.findAll();
		if (jugadors != null && jugadors.size() > 0) {
			double sumaPercentatge = 0.0;
			for (int i = 0; i < jugadors.size(); i++) {
				sumaPercentatge = +jugadors.get(i).getPorcentatgeExitJugador();
			}
			rankingMig = sumaPercentatge / jugadors.size();
		}
		return rankingMig;
	}

	// ACTUALITZA JUGADOR
	@Override
	public Jugador updateJugador(int id, Jugador jugador) {// actualitza jugador
		Optional<Jugador> optionalJugador = jugadorDao.findById(id);
		if (optionalJugador.isPresent()) {
			Jugador updateJugador = optionalJugador.get();
			updateJugador.setNom(jugador.getNom());
			updateJugador.setData_entrada(jugador.getData_entrada());
			jugadorDao.save(updateJugador);// Jugador jugador=optionalJugador.get();
		} else {
			System.out.println("El jugador aamb id " + id + " no existeix");
		}
		return jugadorDao.save(jugador);
	}

	// LLISTAR PERCENTATGE MIG DELS JUGADORS
	public Map<String, Double> getLlistaPercentatgeMigJugadors() {// llista tots jugadors i el seu percentage mig
		List<Jugador> jugadors = jugadorDao.findAll(); // busca jugador per id
		Map<String, Double> mapTasaExitJugador = new HashMap<String, Double>(); // Crea un nou map
		if (jugadors != null && jugadors.size() > 0) { // si llista jugadors no es null i més gran que 0
			List<Tirada> tiradesActualJugador; // creo nova llista tiradaes per l'actual jugador
			for (Jugador jugador : jugadors) { // recorro llista jugagdors
				tiradesActualJugador = tiradesDao.getLlistaTiradesByJugadorId(jugador.getId());
				if (tiradesActualJugador.size() > 0) {
					String key = jugador.getNom();
					Double value = jugador.getPorcentatgeExitJugador();
					System.out.println();
					mapTasaExitJugador.put(key, value);
				} else {
					mapTasaExitJugador.put(jugador.getNom(), (double) 0);
				}
			}
		}

		return mapTasaExitJugador;
	}

	
}
