package com.init.JocDausMongo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.init.JocDausMongo.Model.JugadorModel;
import com.init.JocDausMongo.Model.TiradaModel;
import com.init.JocDausMongo.document.Tirada;
import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.repository.JugadorRepository;
import com.init.JocDausMongo.service.JugadorService;


@RestController
public class DausController {
	
	@Autowired
	private JugadorService serviceJugador;
	
	
	@RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(){
        return "The application is up...";
    }
	
	//CREAR JUGADOR - 1.POST:/players
	@PostMapping("/crear") 
	public String creaJugador(@RequestBody JugadorModel jugadorModel) {
		return serviceJugador.creaJugador(jugadorModel);
	}
	//LLISTAR TOTS ELS JUGADOR
	@GetMapping("/llistar")
	public List<JugadorModel> getAllJugadors() {
		return serviceJugador.getAllJugadors();
	}
	//UPDATE NOM JUGADOR - 2.PUT:/jugadors
	@PutMapping("/updateJugador/{id_jugador}")
	public ResponseEntity<Jugador> updateJugadorNom(@PathVariable(value="id_jugador")int idJugador
			,@RequestBody String nom){
		Jugador jugador=serviceJugador.updateJugadorNom(idJugador, nom);
		return ResponseEntity.ok().body(jugador);		
	}
	//JUGADOR FA TIRADA -  3.POST/{id}/tirades
	@PostMapping("/{jugador_id}/tirades")
	public ResponseEntity<Tirada>jugadorFaTirada(@PathVariable(value="jugador_id")int jugador_id){
		//serviceTirades.jugadorFaTirada(idJugador, jugador);
		Tirada novaTirada=serviceJugador.jugadorFaTirada(jugador_id);
		return ResponseEntity.ok().body(novaTirada);
	}
	//ELIMINA TIRADA JUGADOR - 4.DELETE/{id}/tirades
	@DeleteMapping("/{jugador_id}/tirades")
	public ResponseEntity<String> deleteTiradesByJugador(@PathVariable(value="jugador_id") int jugador_id){
		//Optional<Jugador> optionalJugador = serviceJugador.findJugadorById(jugador_id);
		serviceJugador.deleteTiradesByJugador(jugador_id);
		return ResponseEntity.ok().body("La tirada del jugador amb id "+jugador_id+"S'ha eliminat correctament");	
	}
	//LLISTAT JUGADORS PERCENTATGE MIG - 5.GET/jugadors
	@GetMapping("/listar")
	public ResponseEntity <Map<String, Double>> getPercentatgeMigJugadors(){
		List<JugadorModel>jugadors=serviceJugador.getAllJugadors();
		Map<String,Double>percentatgeMig=serviceJugador.getLlistaPercentatgeMigJugadors();
		return new ResponseEntity<Map<String, Double>>(percentatgeMig,HttpStatus.FOUND);
	}
	//RETORNA TIRADES D'UN JUGADOR - 6.GET/{id}tirades
		@GetMapping("/{id}/llistarTirades")
		public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable (value="id")int jugador_id) {
		List<Tirada> tirades = serviceJugador.llistaTirades(jugador_id);
		return new ResponseEntity<List<Tirada>>(tirades, HttpStatus.OK);
		}
	//RANKING MIG - 7.GET/ranking
	@GetMapping("/ranking")
	public ResponseEntity <Double> getRankingJugadors(){
		List<JugadorModel> jugadors=serviceJugador.getAllJugadors();
		Double rankingMig=serviceJugador.getRankingJugadors();
		return new ResponseEntity<Double>(rankingMig,HttpStatus.FOUND);
	}
	//GET JUGADOR GUANYADOR - 8.GET/ranking/winner
	@GetMapping("/ranking/winner") 
	@ResponseBody//es un marcador para el cuerpo de respuesta HTTP
	public ResponseEntity<JugadorModel> getJugadorGuanya(){
	List<JugadorModel> jugadors=serviceJugador.getAllJugadors();
	JugadorModel jugadormillor=serviceJugador.getJugadorGuanya(jugadors);
	return new ResponseEntity<JugadorModel>(jugadormillor,HttpStatus.FOUND);
	}
	//GET JUGADOR PERDEDOR - 9.GET/ranking/loser
	@GetMapping("/ranking/loser") 
	@ResponseBody//es un marcador para el cuerpo de respuesta HTTP
	public ResponseEntity <JugadorModel> getJugadorPerd(){
	List<JugadorModel> jugadors=serviceJugador.getAllJugadors();
	JugadorModel jugadorpitjor=serviceJugador.getJugadorPerd(jugadors);
	return new ResponseEntity<JugadorModel>(jugadorpitjor,HttpStatus.FOUND);
	}
	
}
