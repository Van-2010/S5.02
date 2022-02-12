package com.init.JocDausMongo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;
import com.init.JocDausMongo.repository.JugadorDao;
import com.init.JocDausMongo.service.JugadorServiceImp;
import com.init.JocDausMongo.service.TiradaServiceImp;

@RestController
@RequestMapping("/jugador")
public class DausController {
	
	@Autowired
	private JugadorServiceImp serviceJugador;
	
	@Autowired
	private TiradaServiceImp serviceTirades;
	
	//CREAR JUGADOR - 1.POST:/players
	@PostMapping("/crear") 
	public ResponseEntity<Jugador> creaJugador(@RequestBody Jugador jugador) {
		Jugador nouJugador=serviceJugador.creaJugador(jugador);
		return ResponseEntity.ok().body(jugador);//ok().(service.saveJugador(jugador));
	}
	//LLISTAR TOTS ELS JUGADOR
	@GetMapping("/llistar")
	public ResponseEntity<List<Jugador>> getAllJugadors() {
		List<Jugador>jugador=serviceJugador.getAllJugadors();
		return new ResponseEntity<List<Jugador>>(jugador,HttpStatus.FOUND);
	}
	//UPDATE NOM JUGADOR - 2.PUT:/jugadors
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateJugador/{id_jugador}")
	public ResponseEntity<Jugador> updateJugadorNom(@PathVariable(value="id_jugador")int idJugador
			,@RequestBody String nom){
		Jugador jugador=serviceJugador.updateJugadorNom(idJugador, nom);
		return ResponseEntity.ok().body(jugador);		
	}
	//JUGADOR FA TIRADA -  3.POST/{id}/tirades
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{jugador_id}/tirades")
	public ResponseEntity<Tirada>jugadorFaTirada(@PathVariable(value="jugador_id")int jugador_id){
		//serviceTirades.jugadorFaTirada(idJugador, jugador);
		Tirada novaTirada=serviceTirades.jugadorFaTirada(jugador_id);
		return ResponseEntity.ok().body(novaTirada);
	}
	//ELIMINA TIRADA JUGADOR - 4.DELETE/{id}/tirades
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{jugador_id}/tirades")
	public ResponseEntity<String> deleteTiradesByJugador(@PathVariable(value="jugador_id") int jugador_id){
		Optional<Jugador> optionalJugador = serviceJugador.findJugadorById(jugador_id);
		serviceTirades.deleteTiradesByJugador(jugador_id);
		return ResponseEntity.ok().body("La tirada del jugador amb id "+jugador_id+"S'ha eliminat correctament");	
	}
	//LLISTAT JUGADORS PERCENTATGE MIG - 5.GET/jugadors
	@GetMapping("/listar")
	public ResponseEntity <Map<String, Double>> getPercentatgeMigJugadors(){
		List<Jugador>jugadors=serviceJugador.getAllJugadors();
		Map<String,Double>percentatgeMig=serviceJugador.getLlistaPercentatgeMigJugadors();
		return new ResponseEntity<Map<String, Double>>(percentatgeMig,HttpStatus.FOUND);
	}
	//RETORNA TIRADES D'UN JUGADOR - 6.GET/{id}tirades
		@GetMapping("/{id}/llistarTirades")
		public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable int id) {
			Optional<Jugador> optionalJugador = serviceJugador.findJugadorById(id);
			try {
				if (optionalJugador.isPresent()) {
					Jugador jugador = optionalJugador.get();
					List<Tirada> tirades = serviceTirades.llistaTirades(jugador);
					return new ResponseEntity<List<Tirada>>(tirades, HttpStatus.FOUND);
				} else {

					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
	//RANKING MIG - 7.GET/ranking
	@GetMapping("/ranking")
	public ResponseEntity <Double> getRankingJugadors(){
		List<Jugador> jugadors=serviceJugador.getAllJugadors();
		Double rankingMig=serviceJugador.getRankingJugadors();
		return new ResponseEntity<Double>(rankingMig,HttpStatus.FOUND);
	}
	//GET JUGADOR GUANYADOR - 8.GET/ranking/winner
	@GetMapping("/ranking/winner") 
	@ResponseBody//es un marcador para el cuerpo de respuesta HTTP
	public ResponseEntity <Jugador> getJugadorGuanya(){
	List<Jugador> jugadors=serviceJugador.getAllJugadors();
	Jugador jugadormillor=serviceJugador.getJugadorGuanya(jugadors);
	return new ResponseEntity<Jugador>(jugadormillor,HttpStatus.FOUND);
	}
	//GET JUGADOR PERDEDOR - 9.GET/ranking/loser
	@GetMapping("/ranking/loser") 
	@ResponseBody//es un marcador para el cuerpo de respuesta HTTP
	public ResponseEntity <Jugador> getJugadorPerd(){
	List<Jugador> jugadors=serviceJugador.getAllJugadors();
	Jugador jugadorpitjor=serviceJugador.getJugadorPerd(jugadors);
	return new ResponseEntity<Jugador>(jugadorpitjor,HttpStatus.FOUND);
	}
	
}
