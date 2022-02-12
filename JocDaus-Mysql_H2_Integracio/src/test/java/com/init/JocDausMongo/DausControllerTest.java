package com.init.JocDausMongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.init.JocDausMongo.controller.DausController;
import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;
import com.init.JocDausMongo.service.JugadorServiceImp;
import com.init.JocDausMongo.service.TiradaServiceImp;



@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {DausControllerTest.class})
public class DausControllerTest {
	
	@Mock
	JugadorServiceImp serviceJugador;//pq es el mètode que s'invoca dins  la classe controller
	@Mock
	TiradaServiceImp serviceTirada;
	
	@InjectMocks
	DausController dauscontroller;
	
	public List<Jugador>totsJugadors;
	Jugador jugador;
	Tirada tirada;
	
	@Test
	@Order(1)
	public void test_getAllJugadors() {
		totsJugadors=new ArrayList<Jugador>();
		totsJugadors.add(new Jugador(1,"Jaume",new java.util.Date(1888-06-24)));
		totsJugadors.add(new Jugador(2,"Loli",new java.util.Date(1888-06-24)));
		
		when(serviceJugador.getAllJugadors()).thenReturn(totsJugadors);
		
		ResponseEntity<List<Jugador>> res=dauscontroller.getAllJugadors();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
	@Test
	@Order(2)
	public void test_creaJugador() {
		Jugador jugador=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		when(serviceJugador.saveJugador(jugador)).thenReturn(jugador);
		
		ResponseEntity<Jugador> res=dauscontroller.creaJugador(jugador);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
	}
	@Test
	@Order(3)
	public void test_updateJugadorNom() {
		Jugador jugador=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		String nom="Maria";
		when(serviceJugador.updateJugadorNom(1, "Maria")).thenReturn(jugador);
		ResponseEntity<Jugador> res=dauscontroller.updateJugadorNom(1, "Maria");
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	@Order(4)
	public void test_jugadorFaTirada() {
		Jugador jugador=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		when(serviceTirada.jugadorFaTirada(1)).thenReturn(tirada);
		ResponseEntity<Tirada> res=dauscontroller.jugadorFaTirada(1);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	@Order(5)
	public void test_deleteTiradesByJugador() {
		Jugador jugador=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		List<Tirada>tirades=new ArrayList<Tirada>();
		tirades.add(new Tirada(1,3,4,true,jugador));
		when(serviceTirada.deleteTiradesByJugador(1)).thenReturn("Tirada eliminada");
		ResponseEntity<String> res=dauscontroller.deleteTiradesByJugador(1);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	@Order(6)
	public void test_getPercentatgeMigJugadors(){
		Jugador jugador1=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		Jugador jugador2=new Jugador(2,"Lily",new java.util.Date(1888-06-24));
		totsJugadors=new ArrayList<Jugador>();
		totsJugadors.add(jugador1);
		totsJugadors.add(jugador2);
		List<Tirada>tirades=new ArrayList<Tirada>();
		tirades.add(new Tirada(1,3,4,true,jugador1));
		tirades.add(new Tirada(1,4,4,false,jugador2));
		Map<String,Double>percentatgeMig=new HashMap<String,Double>();
		percentatgeMig.put("Jaume",30.0);
		percentatgeMig.put("Lily",30.0);
		when(serviceJugador.getAllJugadors()).thenReturn(totsJugadors);
		when(serviceJugador.getLlistaPercentatgeMigJugadors()).thenReturn(percentatgeMig);
		ResponseEntity<Map<String, Double>> res=dauscontroller.getPercentatgeMigJugadors();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
	@Test
	@Order(7)
	public void test_llistarTirades() {
		Jugador jugador=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));

		List<Tirada>tirades=new ArrayList<Tirada>();
		tirades.add(new Tirada(1,3,4,true,jugador));
		tirades.add(new Tirada(1,4,4,false,jugador));
		int jugador1=1;
		when(serviceJugador.findJugadorById(jugador1)).thenReturn(Optional.of(jugador));
		when(serviceTirada.llistaTirades(jugador)).thenReturn(tirades);
		ResponseEntity<List<Tirada>> res=dauscontroller.llistarTirades(1);
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
	@Test
	@Order(8)
	public void getRankingJugadors() {
		Jugador jugador1=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		totsJugadors=new ArrayList<Jugador>();
		totsJugadors.add(jugador1);
		Double ranking=30.0;
		when(serviceJugador.findJugadorById(1)).thenReturn(Optional.of(jugador1));
		when(serviceJugador.getRankingJugadors()).thenReturn(ranking);
		ResponseEntity<Double> res=dauscontroller.getRankingJugadors();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
	@Test
	@Order(9)
	public void getJugadorGuanya() {
		Jugador jugador1=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		totsJugadors=new ArrayList<Jugador>();
		totsJugadors.add(jugador1);
		when(serviceJugador.findJugadorById(1)).thenReturn(Optional.of(jugador1));
		when(serviceJugador.getJugadorGuanya(totsJugadors)).thenReturn(jugador1);
		ResponseEntity<Jugador> res=dauscontroller.getJugadorGuanya();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
	@Test
	@Order(9)
	public void getJugadorPerd() {
		Jugador jugador1=new Jugador(1,"Jaume",new java.util.Date(1888-06-24));
		totsJugadors=new ArrayList<Jugador>();
		totsJugadors.add(jugador1);
		when(serviceJugador.findJugadorById(1)).thenReturn(Optional.of(jugador1));
		when(serviceJugador.getJugadorPerd(totsJugadors)).thenReturn(jugador1);
		ResponseEntity<Jugador> res=dauscontroller.getJugadorPerd();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
	}
}
