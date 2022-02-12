package com.init.JocDausMongo.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.JocDausMongo.security.entity.Rol;
import com.init.JocDausMongo.security.enums.RolNombre;
import com.init.JocDausMongo.security.repository.RolRepository;


@Service
@Transactional
public class RolService {
	
	//només es pot executar una vegada

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}