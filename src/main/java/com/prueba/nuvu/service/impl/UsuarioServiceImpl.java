package com.prueba.nuvu.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.nuvu.dao.IUsuarioDAO;
import com.prueba.nuvu.model.Usuario;
import com.prueba.nuvu.service.IUsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private IUsuarioDAO dao;

	@Override
	public Usuario listByUsuario(String usuario) {
		return dao.findByUsuario(usuario);
	}

}
