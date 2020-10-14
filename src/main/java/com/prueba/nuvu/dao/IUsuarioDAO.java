package com.prueba.nuvu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.nuvu.model.Usuario;

@Repository
public interface IUsuarioDAO extends JpaRepository<Usuario, Integer> {

	Usuario findByUsuario(String usuario);
}
