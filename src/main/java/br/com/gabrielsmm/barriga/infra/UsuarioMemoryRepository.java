package br.com.gabrielsmm.barriga.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.service.repositories.UsuarioRepository;

public class UsuarioMemoryRepository implements UsuarioRepository {
	
	private List<Usuario> users;
	private Long currentId;
	
	public UsuarioMemoryRepository() {
		currentId = 0L;
		users = new ArrayList<>();
		salvar(new Usuario(null, "User #1", "user1@mail.com", "123456"));
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		Usuario newUser = new Usuario(nextId(), usuario.nome(), usuario.email(), usuario.senha());
		users.add(newUser);
		return newUser;
	}

	@Override
	public Optional<Usuario> getUserByEmail(String email) {
		return users.stream().filter(user -> user.email().equalsIgnoreCase(email)).findFirst();
	}
	
	private Long nextId() {
		return ++currentId;
	}

}
