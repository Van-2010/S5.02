package com.init.JocDausMongo.entity;


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

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="game")
public class Tirada implements Serializable {
	
	@Id
	@Column(name="id_tirades")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_tirades;
	
	@Column(name="dau1")
	private int dau1;//=(int) (Math.random() * (7 - 1)) + (1);
	
	@Column(name="dau2")
	private int dau2;//=(int) (Math.random() * (7 - 1)) + (1);
	
	//@Transient
	//int suma=dau1+dau2;
	
	@Transient
	float percentatge;
	
	@Column(name="guanya")
	private boolean guanya;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name= "jugador_id",nullable = false)//foreign key
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Jugador jugador;

	public Tirada() {
		
	}

	public Tirada(int id_tirades,int dau1, int dau2, boolean guanya, Jugador jugador) {
		this.dau1 = dau1;
		this.dau2 = dau2;
		this.guanya = guanya;
		this.jugador = jugador;
		//this.suma=suma;
	}
	public Tirada(Jugador jugador) {
		this.jugador = jugador;
		//si posés les ordres de sota, no caldria posar el mètode de més abaix "retorna resultat"
		//this.dau1=tiradesRandom();
		//this.dau2=tiradesRandom();
		//this.guanya = retornaResultat();
	}
	public Tirada(int id_tirades,Jugador jugador) {
		this.id_tirades = id_tirades;
		this.jugador = jugador;
	}
	
	public int getId_tirades() {
		return id_tirades;
	}

	public void setId_tirades(int id_tirades) {
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
	
	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
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
		return "Tirada [id_tirades=" + id_tirades + ", dau1=" + dau1 + ", dau2=" + dau2 + ", percentatge=" + percentatge
				+ ", guanya=" + guanya + ", jugador=" + jugador + "]";
	}
	
	
	}
	


