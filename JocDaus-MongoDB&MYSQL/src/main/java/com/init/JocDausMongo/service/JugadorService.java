package com.init.JocDausMongo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.init.JocDausMongo.Model.JugadorModel;
import com.init.JocDausMongo.Model.TiradaModel;
import com.init.JocDausMongo.document.Tirada;
import com.init.JocDausMongo.entity.Jugador;
import com.init.JocDausMongo.repository.JugadorRepository;
import com.init.JocDausMongo.repository.TiradaRepository;

@Service
public class JugadorService {

	@Autowired
	private JugadorRepository jugadorRepository;

	@Autowired
	private TiradaRepository tiradesRepository;
	
	/*public Optional<Jugador> findJugadorById(JugadorModel id) {// llista tots jugadors
		Optional<Jugador> jugador = jugadorRepository.findById(id);
		return jugador;
	}*/

		// Llista tots els jugadors
	public List<JugadorModel> getAllJugadors() {
		List<JugadorModel> jugadors = new ArrayList<>();//
		List<Jugador>llistaJugadors=new ArrayList<>();//guarda registres base dades
		try {
			llistaJugadors=jugadorRepository.findAll();//emmagatzamem tots els registres de la base de dades
		}catch(Exception e){
			throw e;
		}
		if(llistaJugadors.size()>0) {
			llistaJugadors.stream().forEach(l-> { //recorra els registres, per  cada registre crea un model
				JugadorModel jugadorModel=new JugadorModel();//copia jugador a jugador model
				jugadorModel.setNom(l.getNom());
				jugadorModel.setData_entrada(l.getData_entrada());
				jugadorModel.setTasaExit(l.getTasaExit());
				List<Tirada>llistaTirades=new ArrayList<>();//emmagatzema totes  les tirades regisrtrades a la base de dades
				List<TiradaModel>tirades=new ArrayList<>();
				
				try {
					llistaTirades = tiradesRepository.findTiradaByIdjugador(jugadorModel.getId());
				}catch(Exception e) {
					throw e;
				}
				if(llistaTirades.size()>0) {
					llistaTirades.stream().forEach(t-> { //per cada tirada de la llista copiarem uuna tirada model
						TiradaModel tiradaModel=new TiradaModel();
						BeanUtils.copyProperties(t,tiradaModel);//(Object source, Object target)/(Object dest, Object orig)
						tirades.add(tiradaModel);
					});
				}
				jugadorModel.setLlistaTirades(tirades);
				jugadors.add(jugadorModel);
			});
		}
		return jugadors;
	}

	// 1.GUARDA UN JUGADOR
	public Jugador saveJugador(Jugador jugador) {// crea jugador
		// return jugadorDao.save(jugador);

		if (jugador.getNom() == null) {
			jugador.setNom("Anonim");
			return jugadorRepository.save(jugador);

		} else if (jugadorRepository.existsByNom(jugador.getNom())) {
			System.out.println("El jugador " + jugador.getNom() + " ja existeix");
		} else {
			return jugadorRepository.save(jugador);
		}
		return jugador;
	}

	// 1.CREA JUGADOR
	@Transactional
	public String creaJugador(JugadorModel jugadorModel) {
		if(!jugadorRepository.existsById(jugadorModel.getId())) {//Veiem si el jugador existeix
		Jugador jugador = new Jugador();
		BeanUtils.copyProperties(jugadorModel,jugador);//copiem el registre del model al jugador 
			jugadorRepository.save(jugador);//guardem jugador
			/*try {
			jugadorModel.getLlistaTirades().stream().forEach(t->{
				Tirada tirada=new Tirada();
				t.setIdJugador(jugadorModel.getId());//setegem l'id perquè és la clau que uneix el jugador i la tirada
				BeanUtils.copyProperties(t,tirada);
				try {
					tiradesRepository.save(tirada);
				}catch(Exception e) {
					throw e;
				}
			});
		}catch(Exception e) {
			throw e;
		}
		}*/
		}
		return "El jugador s'ha creat correctament";
	}
	// MODIFICA NOM JUGADOR
	public Jugador updateJugadorNom(int id, String nom) {
		if (jugadorRepository.existsById(id)) {
			Jugador updateJugador = jugadorRepository.findById(id);
			updateJugador.setNom(nom);
			return jugadorRepository.save(updateJugador);
		} else {
			System.out.println("El jugador amb id " + id + " no existeix");		
		}
		return null;

	}

	// JUGADOR MILLOR PERCENTATGE
	public JugadorModel getJugadorGuanya(List<JugadorModel> jugadors) {// millor jugador
		jugadors.sort(Comparator.comparing(JugadorModel::getPorcentatgeExitJugador).reversed());
		return jugadors.get(0);
	}

	// JUGADOR PITJOR PERCENTATGE
	public JugadorModel getJugadorPerd(List<JugadorModel> jugadors) {// pitjor jugador
		jugadors.sort(Comparator.comparing(JugadorModel::getPorcentatgeExitJugador));
		return jugadors.get(0);
	}

	// RANKING JUGADOR
	public Double getRankingJugadors() {
		Double rankingMig = 0.0;
		List<JugadorModel> jugadors = getAllJugadors();
		if (jugadors != null && jugadors.size() > 0) {
			double sumaPercentatge = 0.0;
			for (int i = 0; i < jugadors.size(); i++) {
				sumaPercentatge = +jugadors.get(i).getPorcentatgeExitJugador();
			}
			rankingMig = sumaPercentatge / jugadors.size();
		}
		return rankingMig;
	}

	// ACTUALITZA JUGADOR
	public Jugador updateJugador(int id, Jugador jugador) {// actualitza jugador
		//Optional<Jugador> optionalJugador = jugadorRepository.findById(id);//findById(id);
		if (jugadorRepository.existsById(id)) {
			Jugador updateJugador = jugadorRepository.findById(id);
			updateJugador.setNom(jugador.getNom());
			updateJugador.setData_entrada(jugador.getData_entrada());
			jugadorRepository.save(updateJugador);// Jugador jugador=optionalJugador.get();
		} else {
			System.out.println("El jugador aamb id " + id + " no existeix");
		}
		return jugadorRepository.save(jugador);
	}

	// LLISTAR PERCENTATGE MIG DELS JUGADORS
	public Map<String, Double> getLlistaPercentatgeMigJugadors() {// llista tots jugadors i el seu percentage mig
		List<JugadorModel> jugadors = getAllJugadors();// busca jugador per id
		Map<String, Double> mapTasaExitJugador = new HashMap<String, Double>(); // Crea un nou map
		if (jugadors != null && jugadors.size() > 0) { // si llista jugadors no es null i més gran que 0
			List<Tirada> tiradesActualJugador; // creo nova llista tiradaes per l'actual jugador
			for (JugadorModel jugador : jugadors) { // recorro llista jugagdors
				tiradesActualJugador = tiradesRepository.findTiradaByIdjugador(jugador.getId());
				if (tiradesActualJugador.size() > 0) {
					String key = jugador.getNom();
					Double value = jugador.getPorcentatgeExitJugador();
					System.out.println();
					mapTasaExitJugador.put(key, value);
				} else {
					mapTasaExitJugador.put(jugador.getNom(), (double) 0);
				}
			}
		}

		return mapTasaExitJugador;
	}	
	// LLISTAR TIRADES	
	public List<Tirada> llistaTirades(int jugador_id) {
	List<Tirada> tirades = tiradesRepository.findTiradaByIdjugador(jugador_id);
	return tirades;
	}
	//JUGADOR FA TIRADA
	public Tirada jugadorFaTirada(int jugador_id) {// jugador específic realitza tirada
		//Optional<Jugador> optionalJugador = jugadorRepository.findById(jugador_id);
		if (jugadorRepository.existsById(jugador_id)) {
			//Jugador jugador = optionalJugador.get();
			Tirada novatirada =new Tirada(jugador_id);
			novatirada.tiradaDaus();
			// System.out.println(novatirada.toString());
			return tiradesRepository.save(novatirada);
		} else {
			System.out.println("El jugador amb id: " + jugador_id + " no existeix");
		}
		return null;
	}
	//ELIMINA TIRADA
	public String deleteTiradesByJugador(int jugador_id){
		//Optional<Jugador> optionalJugador = jugadorRepository.findById(jugador_id);
		if (jugadorRepository.existsById(jugador_id)) {
			//Jugador jugador = optionalJugador.get();
			tiradesRepository.deleteAllByIdjugador(jugador_id);
			System.out.println("La tirada del jugador amb id "+jugador_id+"S'ha eliminat correctament");
		}else {
			System.out.println("Aquest jugador no existeix");
		}
			return null;
	}

	
}
