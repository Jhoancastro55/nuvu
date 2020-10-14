package com.prueba.nuvu.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prueba.nuvu.dto.PersonaDTO;
import com.prueba.nuvu.model.Persona;

@Component
public interface IPersonaService {

	Persona register(PersonaDTO persona);
	
	List<Persona> listAll();
	
	Persona updatePersona(PersonaDTO persona);
	
	Persona finByPeople(String cedula);
}
