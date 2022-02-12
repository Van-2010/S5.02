package com.init.JocDausMongo.Model;


import java.io.Serializable;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;



@Document(collection="tirada")
public class Tirada implements Serializable {
	
	@Transient
	public static final String SEQUENCE_NAME ="tirades_sequence";
	
	@Id
	@NonNull
	private long id;
	
	@Field(name="dau1")
	private int dau1;
	
	@Field(name="dau2")
	private int dau2;
	
	@Field(name="guanya")
	private boolean guanya;
	
	@JoinColumn(name= "idjugador")//foreign key
	@ManyToOne
	@JsonIgnore
	private long idjugador;//li passo només l'id, així no em crea un jugador sencer cada vegada

	public Tirada() {
		
	}

	public Tirada(long id,long idjugador) {
		this.id=id;
		this.idjugador = idjugador;
	}
	
	
	public Tirada(long id) {
		this.id = id;
	}

	public Tirada(long id, int dau1, int dau2, boolean guanya, long idjugador) {
		this.dau1 = dau1;
		this.dau2 = dau2;
		this.guanya = guanya;
		this.idjugador = idjugador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getIdjugador() {
		return idjugador;
	}

	public void setIdjugador(long idjugador) {
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

	@Override
	public String toString() {
		return "Tirada [id=" + id + ", dau1=" + dau1 + ", dau2=" + dau2 + ", guanya=" + guanya + ", idjugador="
				+ idjugador + "]";
	}

	
	}
	


