package com.prueba.nuvu.service;

import org.springframework.stereotype.Component;

import com.prueba.nuvu.model.Usuario;

@Component
public interface IUsuarioService {
	
	Usuario listByUsuario(String usuario);
	
}
