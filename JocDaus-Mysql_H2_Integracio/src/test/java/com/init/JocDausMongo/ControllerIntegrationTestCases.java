package com.init.JocDausMongo;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.entity.Tirada;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTestCases {
	
	@Test
	@Order(1)
	public void getAllJugadorsIntegrationTest() throws JSONException {
		String expected="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"nom\": \"Lola\",\r\n"
				+ "        \"data_entrada\": \"2022-02-10T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExitJugador\": 0.0\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"nom\": \"Francesc\",\r\n"
				+ "        \"data_entrada\": \"2022-02-10T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExitJugador\": 100.0\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate restTemplate=new TestRestTemplate();//a través de restTemplate enviem el request
		ResponseEntity<String>response=restTemplate.getForEntity("http://localhost:8080/jugador/llistar",String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected,response.getBody(),false);//comparem expected amb el get.body
		
	}
	@Test
	@Order(2) 
	public void creaJugadorIntegrationTest() throws JSONException {
		
		Jugador jugador =new Jugador(3,"Lisa",new java.util.Date(07-02-2022));
		
		String expected="{\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"nom\": \"Lisa\",\r\n"
				+ "        \"data_entrada\": \"1969-12-31T23:59:57.983+00:00\",\r\n"
				+ "        \"porcentatgeExitJugador\": 0.0\r\n"
				+ "    }";
			
		TestRestTemplate restTemplate=new TestRestTemplate();//a través de restTemplate enviem el request
		HttpHeaders headers=new HttpHeaders();// podem especificar el tipus de dades que enviarem
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Jugador> request= new HttpEntity<Jugador>(jugador,headers);
		
		ResponseEntity<String>response=restTemplate.postForEntity("http://localhost:8080/jugador/crear",request,String.class);//PostRequest:url/response body/forma en que rebem el  request
		System.out.println(response.getBody());//ens printa la resposta
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());//comparem els dos
		JSONAssert.assertEquals(expected,response.getBody(),false);//verifiquem l'statusCode
	}
	
	@Test
	@Order(3)//
	public void updateJugadorNomIntegrationTest() throws JSONException {
		
		Jugador jugador =new Jugador(2,"Sara",new java.util.Date(06-12-2020));
		String nom="Sara";
		
		String expected="{\r\n"
				+ "    \"id\": 2,\r\n"
				+ "    \"nom\": \"Sara\",\r\n"
				+ "    \"data_entrada\": \"2022-02-10T23:00:00.000+00:00\",\r\n"
				+ "    \"porcentatgeExitJugador\": 100.0\r\n"
				+ "}";
			
		TestRestTemplate restTemplate=new TestRestTemplate();//a través de restTemplate enviem el request
		HttpHeaders headers=new HttpHeaders();// podem especificar el tipus de dades que enviarem
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request= new HttpEntity<String>(nom,headers);
		
		ResponseEntity<String>response=restTemplate.exchange("http://localhost:8080/jugador/updateJugador/2",HttpMethod.PUT,request,String.class);//no existeix putEntity per actualitzar, hem dde posar exchange i afegir un paràmetre
		System.out.println(response.getBody());//ens printa la resposta
		
		assertEquals(HttpStatus.OK,response.getStatusCode());//comparem els dos
		JSONAssert.assertEquals(expected,response.getBody(),false);//verifiquem l'statusCode
		
	}
	@Test
	@Order(4)
	public void jugadorFaTiradaIntegrationTest() throws JSONException {
		
		Jugador jugador =new Jugador(2,"Sara",new java.util.Date(06-12-2020));
		Tirada tirada=new Tirada(3,2,4,false,jugador);
		
		String expected="{\r\n"
				+ "    \"id_tirades\": 3,\r\n"
				+ "    \"dau1\": 2,\r\n"
				+ "    \"dau2\": 4,\r\n"
				+ "    \"guanya\": false\r\n"
				+ "}";
			
		TestRestTemplate restTemplate=new TestRestTemplate();//a través de restTemplate enviem el request
		HttpHeaders headers=new HttpHeaders();// podem especificar el tipus de dades que enviarem
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Tirada> request= new HttpEntity<Tirada>(tirada,headers);
		
		ResponseEntity<String> response=restTemplate.postForEntity("http://localhost:8080/jugador/2/tirades",request, String.class);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		System.out.println(response.getBody());
		//JSONAssert.assertEquals(expected,response.getBody(),false);//verifiquem l'statusCode
		
	}
	@Test
	@Order(5)
	public void deleteTiradesByJugadorIntegrationTest() throws JSONException {
		Jugador jugador =new Jugador(2,"Sara",new java.util.Date(06-12-2020));
		
		String expected= "La tirada del jugador amb id 2S'ha eliminat correctament";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Jugador> request = new HttpEntity<Jugador>(jugador, headers);	
		ResponseEntity<String> response=restTemplate.exchange("http://localhost:8080/jugador/2/tirades", HttpMethod.DELETE,request, String.class);
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		//JSONAssert.assertEquals(expected,response.getBody(), false);
	}
	@Test
	@Order(6)
	public void getPercentatgeMigJugadorsIntegrationTest() throws JSONException {
		
		String expected="{\r\n"
				+ "    \"Sara\": 0.0,\r\n"
				+ "    \"Lola\": 0.0,\r\n"
				+ "    \"Lisa\": 0.0\r\n"
				+ "}";
		
	TestRestTemplate restTemplate=new TestRestTemplate();
	ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/jugador/listar", String.class);
	System.out.println(response.getBody());
	
	assertEquals(HttpStatus.FOUND,response.getStatusCode());
	JSONAssert.assertEquals(expected,response.getBody(), false);

	}
	@Test
	@Order(7)
	public void llistarTiradesIntegrationTest() throws JSONException {
		
		String expected="[\r\n"
				+ "    {\r\n"
				+ "        \"id_tirades\": 1,\r\n"
				+ "        \"dau1\": 2,\r\n"
				+ "        \"dau2\": 3,\r\n"
				+ "        \"guanya\": false\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/jugador/1/llistarTirades", String.class);
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.FOUND,response.getStatusCode());
		JSONAssert.assertEquals(expected,response.getBody(), false);
		
	}
	@Test
	@Order(8)
	public void getRankingJugadorsIntegrationTest() throws JSONException {
		String expected="0.0";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/jugador/ranking", String.class);
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.FOUND,response.getStatusCode());
		JSONAssert.assertEquals(expected,response.getBody(), false);
		
	}
	@Test
	@Order(9)
	public void getJugadorGuanyaIntegrationTest() throws JSONException {
		String expected="{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"nom\": \"Lola\",\r\n"
				+ "    \"data_entrada\": \"2022-02-10T23:00:00.000+00:00\",\r\n"
				+ "    \"porcentatgeExitJugador\": 0.0\r\n"
				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/jugador/ranking/winner", String.class);
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.FOUND,response.getStatusCode());
		JSONAssert.assertEquals(expected,response.getBody(), false);
	}
	@Test
	@Order(10)
	public void getJugadorPerdIntegrationTest() throws JSONException {
		String expected="{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"nom\": \"Lola\",\r\n"
				+ "    \"data_entrada\": \"2022-02-10T23:00:00.000+00:00\",\r\n"
				+ "    \"porcentatgeExitJugador\": 0.0\r\n"
				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/jugador/ranking/loser", String.class);
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.FOUND,response.getStatusCode());
		JSONAssert.assertEquals(expected,response.getBody(), false);
		
	}
}
