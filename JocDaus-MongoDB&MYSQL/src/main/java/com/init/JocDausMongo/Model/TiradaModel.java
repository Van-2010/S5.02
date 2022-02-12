package com.init.JocDausMongo.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class TiradaModel {
	
	private String id;
	private int dau1;
	private int dau2;
	float percentatge;
	private boolean guanya;
	int idJugador;
	
	public TiradaModel() {
		
	}

	public TiradaModel(String id_tirades,int dau1, int dau2, boolean guanya) {
		this.id=id_tirades;
		this.dau1 = dau1;
		this.dau2 = dau2;
		this.guanya = guanya;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getPercentatge() {
		return percentatge;
	}

	public void setPercentatge(float percentatge) {
		this.percentatge = percentatge;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
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
	


