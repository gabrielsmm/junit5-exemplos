package br.com.gabrielsmm.barriga.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;

@DisplayName("Domínio do usuário")
public class UsuarioTest {
	
	@Test
	@DisplayName("Deve criar um usuário válido")
	public void deveCriarUsuarioValido() {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Assertions.assertAll("Usuario",
				() -> assertEquals(1L, usuario.id()),
				() -> assertEquals("Usuário Válido", usuario.nome()),
				() -> assertEquals("user@mail.com", usuario.email()),
				() -> assertEquals("12345678", usuario.senha())
		);
	}
	
	@Test
	@DisplayName("Deve rejeitar usuário sem nome")
	public void deveRejeitarUsuarioSemNome() {
		ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> UsuarioBuilder.umUsuario().comNome(null).agora());
		assertEquals("Nome é obrigatório", exception.getMessage());
	}
	
}
