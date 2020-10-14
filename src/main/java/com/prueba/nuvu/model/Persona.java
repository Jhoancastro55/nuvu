package com.prueba.nuvu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prueba.nuvu.dto.PersonaDTO;

import lombok.Data;

@Entity
@Table(name = "personas")
@Data
public class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "cedula")
	private String cedula;
	
	@Column(name = "nombres")
	private String nombres;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "tarjeta")
	private String tarjeta;
	
	/*
	 * Constructor que transforma un DTO a entidad
	 */
	public Persona(PersonaDTO personaDTO) {
		this.id = personaDTO.getId();
		this.cedula = personaDTO.getCedula();
		this.nombres = personaDTO.getNombres();
		this.apellidos = personaDTO.getApellidos();
		this.direccion = personaDTO.getDireccion();
		this.telefono = personaDTO.getTelefono();
		this.correo = personaDTO.getCorreo();
		this.tarjeta = personaDTO.getTarjeta();
	}
	
	public Persona() {
		
	}
}
