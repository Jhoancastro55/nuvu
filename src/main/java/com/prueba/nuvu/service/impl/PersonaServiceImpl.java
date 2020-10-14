package com.prueba.nuvu.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.nuvu.dao.IPersonaDAO;
import com.prueba.nuvu.dto.PersonaDTO;
import com.prueba.nuvu.model.Persona;
import com.prueba.nuvu.service.IPersonaService;

@Service
@Transactional
public class PersonaServiceImpl implements IPersonaService{

	@Autowired
	private IPersonaDAO dao;

	@Override
	public Persona register(PersonaDTO persona) {
		return dao.save(new Persona(persona));
	}

	@Override
	public List<Persona> listAll() {
		return dao.findAll();
	}

	@Override
	public Persona updatePersona(PersonaDTO persona) {
		Persona persona2 = new Persona(persona);
		return dao.save(persona2);
	}

	@Override
	public Persona finByPeople(String cedula) {
		return dao.findByCedula(cedula);
	}
	
	
}
