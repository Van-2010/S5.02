package com.init.JocDausMongo.Model;


import java.util.ArrayList;
import java.util.List;

public class JugadorModel  {
	
	private int id;
	private String nom;
	private Double tasaExit;
	private java.util.Date data_entrada;
	private List<TiradaModel>llistaTirades;

	public JugadorModel() {
	
	}
	public JugadorModel(int id,String nom,java.util.Date data_entrada) {
		this.id=id;
		this.nom=nom;
		this.data_entrada = data_entrada;
		
	}
	public JugadorModel (int id, String nom, java.util.Date data_entrada, List<TiradaModel> llistaTirades, Double tasaExit) {
		this.id = id;
		this.nom = nom;
		this.data_entrada = data_entrada;
		this.llistaTirades = llistaTirades;
		this.tasaExit = tasaExit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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

	public List<TiradaModel> getLlistaTirades() {
		return llistaTirades;
	}

	public void setLlistaTirades(List<TiradaModel> llistaTirades) {
		this.llistaTirades = llistaTirades;
	}
	
	public void addTirada(TiradaModel tirada) {
		if(llistaTirades==null) {//si la llista de tirades està buida
			llistaTirades=new ArrayList<>();
		}
		llistaTirades.add(tirada);//afegeix tirada
		tirada.setIdJugador(this.getId());//guarda'l a jugador amb l'id del jugador	 	
	}
	public double getPorcentatgeExitJugador() {  //Calcular la tasa d'èxit de les tirades
		double tasaExit=0;
		if(llistaTirades!=null && llistaTirades.size()>0) {
			int llistaTiradesNova=llistaTirades.size();
			int totalGuanyades=0;
			for(TiradaModel tirades:llistaTirades) {
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
		return "Jugador [nom=" + nom + ", tasaExit=" + tasaExit + ", data_entrada="
				+ data_entrada + ", llistaTirades=" + llistaTirades + "]";
	
	}
}