package com.init.JocDausMongo.Model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;

@Document(collection = "jugador")
public class Jugador implements Serializable {

	//variable global que ens serveix per transformar l'id que genera mongo amb un autonumèric per jugador
	@Transient
	public static final String SEQUENCE_NAME ="jugadors_sequence";
	
	@Id
	@NonNull
	private long id;

	@Field(name = "nom")
	private String nom;

	@JsonIgnore
	@Transient
	private Double tasaExit;

	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Field(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data_entrada;

	
	@DBRef(lazy = true)
	@JsonIgnore
	private List<Tirada> llistaTirades;

	public Jugador() {

	}
	public Jugador(long id, String nom, java.util.Date data_entrada) {
		this.id = id;
		this.nom = nom;
		this.data_entrada = data_entrada;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		if (llistaTirades == null) {// si la llista de tirades està buida
			llistaTirades = new ArrayList<>();
		}
		llistaTirades.add(tirada); // afegeix tirada
		tirada.setIdjugador(this.getId());// guarda'l a jugador amb l'id del jugador
		
	}

	public double getPorcentatgeExitJugador() { // Calcular la tasa d'èxit de les tirades
		double tasaExit = 0;
		int totalGuanyades = 0;	
		 if (llistaTirades != null && llistaTirades.size() > 0) {
			int size=llistaTirades.size();
			 for (int i = 0; i < llistaTirades.size(); i++) {
				if (llistaTirades.get(i).isGuanya()) {
					totalGuanyades ++;
			}
			}
			tasaExit = (totalGuanyades * 100) / size;
			}
		return tasaExit;
	}
	@Override
	public String toString() {
		return "Jugador [id_jugador=" + id + ", nom=" + nom + ", tasaExit=" + tasaExit + ", data_entrada="
				+ data_entrada + ", llistaTirades=" + llistaTirades + "]";

	}
}