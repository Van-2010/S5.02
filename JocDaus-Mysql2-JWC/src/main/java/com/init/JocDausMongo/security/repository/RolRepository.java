package com.init.JocDausMongo.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.init.JocDausMongo.security.entity.Rol;
import com.init.JocDausMongo.security.enums.RolNombre;


public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
