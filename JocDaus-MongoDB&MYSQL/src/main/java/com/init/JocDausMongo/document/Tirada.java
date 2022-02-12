package com.init.JocDausMongo.document;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.init.JocDausMongo.entity.Jugador;


@Document(collection="tirada")//requeriment per interactuar amb mongodb
public class Tirada {
	
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private String id_tirades;
		
	@Field(name="dau1")
	private int dau1;
	
	@Field(name="dau2")
	private int dau2;
	
	@Field(name="guanya")
	private boolean guanya;
	
	@Field(name="idjugador")
	int idjugador;

	Jugador jugador;
	
	public Tirada() {
		
	}
	public Tirada(String id_tirades,int idjugador) {
		this.id_tirades=id_tirades;
		this.idjugador = idjugador;
	}
	public Tirada(Jugador jugador) {
		this.jugador=jugador;
	}

	public Tirada(String id_tirades,int dau1, int dau2, boolean guanya, int idjugador) {
		this.id_tirades=id_tirades;
		this.dau1 = dau1;
		this.dau2 = dau2;
		this.guanya = guanya;
		this.idjugador = idjugador;
		
	}
	public Tirada(int idjugador) {
		this.idjugador = idjugador;
	}
	public String getId_tirades() {
		return id_tirades;
	}

	public void setId_tirades(String id_tirades) {
		this.id_tirades = id_tirades;
	}

	public int getDau1() {
		return dau1;
	}

	public void setDau1(int dau1) {
		this.dau1 = dau1;
	}

	public int getDau2() {
		return dau2;
	}

	public void setDau2(int dau2) {
		this.dau2 = dau2;
	}

	public boolean isGuanya() {
		
		return guanya;
	}

	public void setGuanya(boolean guanya) {
		this.guanya = guanya;
	}


	public int getIdjugador() {
		return idjugador;
	}

	public void setIdjugador(int idjugador) {
		this.idjugador = idjugador;
	}

	public boolean retornaResultat() {
		int suma=dau1+dau2;
		if(suma==7) {
			guanya=true;
		}else {
			guanya=false;
		}
		return guanya;
	}
	public void tiradaDaus(){
		setDau1(tiradesRandom());
		setDau2(tiradesRandom());
		setGuanya(retornaResultat());
	}
	public int tiradesRandom() {
	int random=(int)Math.floor(Math.random()*6+1);//retorna un nº aleatori entre 6 i 1
	return random;
		}
	 	
	}
	

	


