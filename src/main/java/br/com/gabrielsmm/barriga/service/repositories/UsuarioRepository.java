package br.com.gabrielsmm.barriga.service.repositories;

import java.util.Optional;

import br.com.gabrielsmm.barriga.domain.Usuario;

public interface UsuarioRepository {
	
	Usuario salvar(Usuario usuario);
	Optional<Usuario> getUserByEmail(String email);
	
}
