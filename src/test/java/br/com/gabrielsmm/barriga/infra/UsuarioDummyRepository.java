package br.com.gabrielsmm.barriga.infra;

import static br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder.umUsuario;

import java.util.Optional;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.service.repositories.UsuarioRepository;

public class UsuarioDummyRepository implements UsuarioRepository {

	@Override
	public Usuario salvar(Usuario usuario) {
		return umUsuario()
				.comNome(usuario.nome())
				.comEmail(usuario.email())
				.comSenha(usuario.senha())
				.agora();
	}

	@Override
	public Optional<Usuario> getUserByEmail(String email) {
		if ("user@mail.com".equals(email)) 
			return Optional.of(umUsuario().comEmail(email).agora());
		return Optional.empty();
	}

}
