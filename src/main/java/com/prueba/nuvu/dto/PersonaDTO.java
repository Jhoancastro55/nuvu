package com.prueba.nuvu.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaDTO implements Serializable{

	private Integer id;
	private String cedula;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String telefono;
	private String correo;
	private String tarjeta;
	
	
}
