package com.prueba.nuvu.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.nuvu.dto.MessageDTO;
import com.prueba.nuvu.dto.PersonaDTO;
import com.prueba.nuvu.model.Persona;
import com.prueba.nuvu.model.Usuario;
import com.prueba.nuvu.service.IPersonaService;
import com.prueba.nuvu.service.IUsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class NuvuController {

	@Autowired
	private IPersonaService servicePersona;
	
	@Autowired 
	private IUsuarioService serviceUsuario;
	
	MessageDTO dto = new MessageDTO();
	
	/*
	 * Metodo para autenticarse y hacer un llamado al API
	 */
	@GetMapping(value = "/hello", produces = "application/json")
	public ResponseEntity<MessageDTO> hello(@RequestParam("user") String usuario, @RequestParam("contra") String contrasena){
		HttpStatus httpStatus = null;
		try {
			Usuario usuarios = serviceUsuario.listByUsuario(usuario);
			if(usuarios != null) {
				String token = getJWTToken(usuario);
				dto.setId("1");
				dto.setMessage("Conexión Establecida con prueba Nuvu: " + usuario);
				dto.setToken(token);
				httpStatus = HttpStatus.OK;
			}else {
				dto.setId("3");
				dto.setMessage("No se encuentra usuario para autenticación");
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
		} catch (Exception e) {
			dto.setId("3");
			dto.setMessage(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<MessageDTO>(dto, httpStatus);
	}
	
	/*
	 * Metodo para registrar persona, recibe el header de authorizacion que debe ser el token 
	 */
	@PostMapping(value = "/registerPeople", consumes = "application/json", produces = "application/json")
	public ResponseEntity<MessageDTO> registerUser(@RequestHeader(value = "Authorization") String token,
			@RequestBody PersonaDTO personaDTO){
		HttpStatus httpStatus = null;
		try {
			if(token == "") {
				dto.setId("7");
				dto.setMessage("Header: Authorization es obligatorio");
			}else{
				httpStatus = HttpStatus.OK;
				String cedula = personaDTO.getCedula();
				Persona personas = new Persona(); 
				personas = servicePersona.finByPeople(cedula);
				if(personas == null) {
					Persona persona = servicePersona.register(personaDTO);
					dto.setId("2");
					dto.setMessage("Registro exitoso");
					httpStatus = HttpStatus.CREATED;
				}else {
					dto.setId("6");
					dto.setMessage("Este registro ya se encuentra registrado en la base de datos");
					httpStatus = HttpStatus.OK;
				}
			}
		} catch (Exception e) {
			dto.setId("3");
			dto.setMessage(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<MessageDTO>(dto, httpStatus);
	}
	
	/*
	 * Metodo para listar personas
	 */
	@GetMapping(value = "/listarPeople", produces = "application/json")
	public Object listarUsuarios(@RequestHeader(value = "Authorization") String token){
		if(token == "") {
			dto.setId("7");
			dto.setMessage("Header: Authorization es obligatorio");
			return dto;
		}else  {
			List<Persona> listAll = servicePersona.listAll();
			if(listAll == null || listAll.isEmpty()) {
				dto.setId("5");
				dto.setMessage("No hay registro en la base de datos");
				return dto;
			}else {
				return listAll;
			}
		}
	}
	
	/*
	 * Metodo para modificar personas
	 */
	@PutMapping(value = "/editarPeople", consumes = "application/json", produces = "application/json")
	public ResponseEntity<MessageDTO> editarUsuario(@RequestHeader(value = "Authorization") String token,
			@RequestBody PersonaDTO personaDTO, @RequestParam String cedula){
		HttpStatus httpStatus = null;
		try {
			if(token == "") {
				dto.setId("7");
				dto.setMessage("Header: Authorization es obligatorio");
			}else {
				Persona persona1 = new Persona();
				persona1 = servicePersona.finByPeople(cedula);
				personaDTO.setId(persona1.getId());
				Persona persona = servicePersona.updatePersona(personaDTO);
				dto.setId("4");
				dto.setMessage("Registro modificado");
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			dto.setId("3");
			dto.setMessage(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<MessageDTO>(dto, httpStatus);
		
	}
	
	/*
	 * Metodo para generar el token de JWT 
	 */
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
		
		String retorno = token;
		return retorno;
	}
}
