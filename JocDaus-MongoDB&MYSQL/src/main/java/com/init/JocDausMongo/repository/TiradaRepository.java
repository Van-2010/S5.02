package com.init.JocDausMongo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausMongo.Model.JugadorModel;
import com.init.JocDausMongo.Model.TiradaModel;
import com.init.JocDausMongo.document.Tirada;
import com.init.JocDausMongo.entity.Jugador;

@Repository
@Transactional
public interface TiradaRepository extends MongoRepository<Tirada,String>  {
	
	//List<Tirada> getTiradaByIdjugador(int idjugador);

	//public void deleteTiradaByJugadorIdJugador(int idjugador);
	
	//public void deleteTiradesByJugadorId(int jugador_id);
		
	//public List<Tirada> getLlistaTiradesByJugadorId(int jugador_id);
	
	public List<Tirada> findTiradaByIdjugador(int idjugador);

    public void deleteAllByIdjugador(int Idjugador);

}
