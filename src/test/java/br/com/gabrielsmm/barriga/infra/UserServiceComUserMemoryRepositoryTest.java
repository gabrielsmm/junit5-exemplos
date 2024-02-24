package br.com.gabrielsmm.barriga.infra;

import static br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder.umUsuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.gabrielsmm.barriga.domain.Usuario;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.UsuarioService;

@Tag("infra")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceComUserMemoryRepositoryTest {

	private static UsuarioService service = new UsuarioService(new UsuarioMemoryRepository());
	
	@Test
	@Order(1)
	public void deveSalvarUsuarioValido() {
		Usuario user = service.salvar(umUsuario().comId(null).agora());
		Assertions.assertNotNull(user.id());
		Assertions.assertEquals(2L, user.id());
	}
	
	@Test
	@Order(2)
	public void deveRejeitarUsuarioExistente() {
		String errorMessage = Assertions.assertThrows(ValidationException.class, () -> {
			service.salvar(umUsuario().comId(null).agora());
		}).getMessage();
		Assertions.assertEquals("Usuário user@mail.com já cadastrado!", errorMessage);
	}
	
}
