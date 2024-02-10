package br.com.gabrielsmm.barriga.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder;
import br.com.gabrielsmm.barriga.service.repositories.UsuarioRepository;

public class UsuarioServiceTest {
	
	private UsuarioService service;
	
	@Test
	public void deveRetornarEmptyQuandoUsuarioInexistente() {
		UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioService(repository);
		
		Mockito.when(repository.getUserByEmail("mail@mail.com")).thenReturn(Optional.empty());
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		Assertions.assertTrue(user.isEmpty());
	}
	
	@Test
	public void deveRetornarUsuarioPorEmail() {
		UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioService(repository);
		
		Mockito.when(repository.getUserByEmail("mail@mail.com"))
			   .thenReturn(Optional.of(UsuarioBuilder.umUsuario().comEmail("mail@mail.com").agora()));
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		Assertions.assertFalse(user.isEmpty());
		Assertions.assertEquals("mail@mail.com", user.get().email());
	}
	
}
