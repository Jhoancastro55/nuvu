package com.prueba.nuvu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.nuvu.model.Persona;

@Repository
public interface IPersonaDAO extends JpaRepository<Persona, Integer>{
	
	/*
	 * Consulta por cedula
	 */
	Persona findByCedula(String cedula);
}
