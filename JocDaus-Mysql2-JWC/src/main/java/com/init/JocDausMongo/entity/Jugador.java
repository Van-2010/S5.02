package com.init.JocDausMongo.entity;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="player")
public class Jugador implements Serializable {
	
	@Id
	@Column(name="id_jugador")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idJugador;
	
	@Column(name="nom")
	private String nom;
	
	@JsonIgnore
	@Transient
	private Double tasaExit;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data_entrada;

	@JsonIgnore
	@OneToMany(mappedBy="jugador",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Tirada>llistaTirades;

	public Jugador() {
	
	}
	public Jugador(String nom,java.util.Date data_entrada) {
		this.nom=nom;
		this.data_entrada = data_entrada;
		
	}
	public Jugador(int idJugador, String nom, java.util.Date data_entrada) {
		this.idJugador = idJugador;
		this.nom = nom;
		this.data_entrada = data_entrada;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Double getTasaExit() {
		return tasaExit;
	}

	public void setTasaExit(Double tasaExit) {
		this.tasaExit = tasaExit;
	}

	public java.util.Date getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(java.util.Date data_entrada) {
		this.data_entrada = data_entrada;
	}

	public List<Tirada> getLlistaTirades() {
		return llistaTirades;
	}

	public void setLlistaTirades(List<Tirada> llistaTirades) {
		this.llistaTirades = llistaTirades;
	}
	
	public void addTirada(Tirada tirada) {
		if(llistaTirades==null) {//si la llista de tirades està buida
			llistaTirades=new ArrayList<>();
		}
		tirada.setJugador(this);//guarda'l a jugador amb l'id del jugador
		llistaTirades.add(tirada); //afegeix tirada	
	}
	public double getPorcentatgeExitJugador() {  //Calcular la tasa d'èxit de les tirades
		double tasaExit=0;
		if(llistaTirades!=null && llistaTirades.size()>0) {
			int llistaTiradesNova=llistaTirades.size();
			int totalGuanyades=0;
			for(Tirada tirades:llistaTirades) {
				if(tirades.isGuanya()) {
					totalGuanyades++;
				}		
			}
			tasaExit=(totalGuanyades*100)/llistaTiradesNova;
		}
		return tasaExit;
		
	}
	@Override
	public String toString() {
		return "Jugador [id_jugador=" + idJugador + ", nom=" + nom + ", tasaExit=" + tasaExit + ", data_entrada="
				+ data_entrada + ", llistaTirades=" + llistaTirades + "]";
	
	}
}