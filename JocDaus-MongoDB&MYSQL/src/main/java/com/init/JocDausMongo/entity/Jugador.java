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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.init.JocDausMongo.document.Tirada;

//per interactuar amb Mysql
@Entity
@Table(name="jugador")
public class Jugador{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_jugador")
	private int id;
	
	@Column(name="nom")
	private String nom;
	
	@Transient
	private Double tasaExit;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data_entrada;
		
	public Jugador() {
	
	}
	public Jugador(String nom,java.util.Date data_entrada) {
		this.nom=nom;
		this.data_entrada = data_entrada;
		
	}
	public Jugador(int id, String nom,Double tasaExit, java.util.Date data_entrada) {
		this.id = id;
		this.nom = nom;
		this.tasaExit=tasaExit;
		this.data_entrada = data_entrada;
	}

	public int getId() {
		return id;
	}

	public void setIdJugador(int id) {
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
		@Override
	public String toString() {
		return "Jugador [id_jugador=" + id + ", nom=" + nom + ", tasaExit=" + tasaExit + ", data_entrada="
				+ data_entrada + "]";
	
	}
}