package br.com.gabrielsmm.barriga.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder;
import br.com.gabrielsmm.barriga.infra.UsuarioDummyRepository;

public class UsuarioServiceTest {
	
	private UsuarioService service;
	
	@Test
	public void deveSalvarUsuarioComSucesso() {
		service = new UsuarioService(new UsuarioDummyRepository());
		Usuario user = UsuarioBuilder.umUsuario().comId(null).comEmail("outro@email.com").agora();
		Usuario savedUser = service.salvar(user);
		Assertions.assertNotNull(savedUser.id());
	}
	
}
