package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder.umUsuario;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.service.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@Mock
	private UsuarioRepository repository;
	
	@InjectMocks
	private UsuarioService service;
	
//  Desncessário ao usar @ExtendWith
//	@BeforeEach
//	public void setup() {
//		repository = Mockito.mock(UsuarioRepository.class); // Versão inicial
//		service = new UsuarioService(repository);
//		
//		MockitoAnnotations.openMocks(this); // Iniciando o mock e injetando
//	}
	
//	@AfterEach
//	public void tearDown() {
//		Mockito.verifyNoMoreInteractions(repository);
//	}
	
	@Test
	public void deveRetornarEmptyQuandoUsuarioInexistente() {
//		Mockito.when(repository.getUserByEmail("mail@mail.com")).thenReturn(Optional.empty());
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		Assertions.assertTrue(user.isEmpty());
		
		verify(repository).getUserByEmail("mail@mail.com");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deveRetornarUsuarioPorEmail() {		
		when(repository.getUserByEmail("mail@mail.com"))
			   .thenReturn(Optional.of(umUsuario().comEmail("mail@mail.com").agora()), 
				   		   Optional.of(umUsuario().comEmail("mail@mail.com").agora()), 
				   		   null); // Repetições
		
		Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
		Assertions.assertTrue(user.isPresent());
		Assertions.assertEquals("mail@mail.com", user.get().email());
		
		user = service.getUserByEmail("mail@mail.com"); // Segunda chamada
		System.out.println(user);
		
		user = service.getUserByEmail("mail@mail.com"); // Terceira chamada (null)
		System.out.println(user);
		
		verify(repository, Mockito.atLeastOnce()).getUserByEmail("mail@mail.com");
		verify(repository, Mockito.never()).getUserByEmail("outroEmail@mail.com");
	}
	
	@Test
	public void deveSalvarUsuarioComSucesso() {
		Usuario userToSave = umUsuario().comId(null).agora();
		
//		when(repository.getUserByEmail(userToSave.email()))
//		.thenReturn(Optional.empty());
		
		when(repository.salvar(userToSave))
		.thenReturn(umUsuario().agora());
		
		Usuario savedUser = service.salvar(userToSave);
		
		Assertions.assertNotNull(savedUser.id());
		verify(repository).getUserByEmail(userToSave.email()); // Verificando se houve a chamada
		verify(repository).salvar(userToSave);
	}
	
}
