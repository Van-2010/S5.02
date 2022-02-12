package com.init.JocDausMongo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.init.JocDausMongo.controller.DausController;
import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;
import com.init.JocDausMongo.service.JugadorServiceImp;
import com.init.JocDausMongo.service.TiradaServiceImp;



@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages="com.init.JocDausMongo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes= {ControllerMockMvcTest.class})
public class ControllerMockMvcTest {

	
	@Autowired
	MockMvc	mockMvc	;
	
	@Mock
	JugadorServiceImp serviceJugador;
	
	@Mock
	TiradaServiceImp serviceTirades;
	
	@InjectMocks
	DausController dausController;
	
	List<Jugador>jugadors;
	Jugador jugador;
	
	List<Tirada>tirades;
	Tirada tirada;
	
	@BeforeEach
	public void setUp() { //mètode necessari per activar el MockMvc
		
		mockMvc=MockMvcBuilders.standaloneSetup(dausController).build();//inicialitzem MockkMvc
	}
	@Test
	@Order(1)
	public void test_getAllJugadors() throws Exception {
		jugadors=new ArrayList<Jugador>();
		jugadors.add(new Jugador(1,"Vane",new java.util.Date(7/2/2022)));
		jugadors.add(new Jugador(2,"Lola",new java.util.Date(7/2/2022)));
		when(serviceJugador.getAllJugadors()).thenReturn(jugadors);
		
		this.mockMvc.perform(get("/jugador/llistar")) //cridem al request i no al m``etode com feiem amb els tests unitaris
		.andExpect(status().isFound())//especifiquem
		.andDo(print());//opcional
	}
	@Test
	@Order(2)
	public void test_creaJugador() throws Exception {
		Jugador jugador=new Jugador(1,"Vane",new java.util.Date(7/2/2022));
		
		when(serviceJugador.creaJugador(jugador)).thenReturn(jugador);
		
		ObjectMapper mapper=new ObjectMapper();//per transformar java en json
		String jsonbody=mapper.writeValueAsString(jugador);//ens reetornarà les dades  en String
		
		this.mockMvc.perform(post("/jugador/crear")
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isCreated())
			.andDo(print());
	}
	@Test
	@Order(3)
	public void test_updateJugadorNom() throws Exception {
		Jugador jugador=new Jugador(1,"Vane",new java.util.Date(7/2/2022));
		Jugador jugadorModificat=new Jugador(1,"Maria",new java.util.Date(7/2/2022));
		
		int id=1;
		String  nom="Maria";
		
		when(serviceJugador.updateJugadorNom(id, nom)).thenReturn(jugador);
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonbody=mapper.writeValueAsString(jugador);
		
		
		this.mockMvc.perform(put("/jugador/updateJugador/{id_jugador},id")
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
			.andExpect(jsonPath("$.nom", containsString("Maria")))
			.andDo(print());
	}
	@Test
	@Order(4)
	public void jugadorFaTirada() throws Exception {
		Jugador jugador=new Jugador(1,"Vane",new java.util.Date(7/2/2022));
		int id=1;
		//Jugador jugadorModificat=new Jugador(1,"Vane",new java.util.Date(7/2/2022));
		Tirada novaTirada=new Tirada(1,2,5,true,jugador);
		jugador.addTirada(novaTirada);
		
		
		when(serviceJugador.findJugadorById(1)).thenReturn(Optional.of(jugador));
		when(serviceTirades.jugadorFaTirada(1)).thenReturn(novaTirada);
		
		ObjectMapper mapper=new ObjectMapper();//per transformar java en json
		String jsonbody=mapper.writeValueAsString(novaTirada);//ens reetornarà les dades  en String
		
		this.mockMvc.perform(post("/jugador/{jugador_id}/tirades",id)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id",org.hamcrest.Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Vane"))
			.andExpect(MockMvcResultMatchers.jsonPath(".tirades").isArray())
			.andDo(print());
	}
	
}
