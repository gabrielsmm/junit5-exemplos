package br.com.gabrielsmm.barriga.domain;

import static br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;

@Tag("dominio")
@Tag("usuario")
@DisplayName("Domínio do usuário")
public class UsuarioTest {
	
	@Test
	@DisplayName("Deve criar um usuário válido")
	public void deveCriarUsuarioValido() {
		Usuario usuario = umUsuario().agora();
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
		ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> umUsuario().comNome(null).agora());
		assertEquals("Nome é obrigatório", exception.getMessage());
	}
	
	@Test
	@DisplayName("Deve rejeitar usuário sem email")
	public void deveRejeitarUsuarioSemEmail() {
		ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> umUsuario().comEmail(null).agora());
		assertEquals("Email é obrigatório", exception.getMessage());
	}
	
	@Test
	@DisplayName("Deve rejeitar usuário sem senha")
	public void deveRejeitarUsuarioSemSenha() {
		ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> umUsuario().comSenha(null).agora());
		assertEquals("Senha é obrigatória", exception.getMessage());
	}
	
	@ParameterizedTest(name = "[{index}] - {4}")
	@CsvSource(value = {
			"1, null, user@mail.com, 12345678, Nome é obrigatório",
			"1, Usuário Válido, null, 12345678, Email é obrigatório",
			"1, Usuário Válido, user@mail.com, null, Senha é obrigatória"
	}, 
	nullValues = { "null" })
	public void deveValidarCamposObrigatorios(Long id, String nome, String email, String senha, String mensagem) {
		ValidationException exception = Assertions.assertThrows(ValidationException.class, 
										() -> umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora());
		assertEquals(mensagem, exception.getMessage());
	}
	
	@ParameterizedTest(name = "[{index}] - {4}")
	@CsvFileSource(files = "src\\test\\resources\\camposObrigatoriosUsuario.csv", nullValues = { "null" }, useHeadersInDisplayName = true)
	// numLinesToSkip = 1
	public void deveValidarCamposObrigatoriosV2(Long id, String nome, String email, String senha, String mensagem) {
		ValidationException exception = Assertions.assertThrows(ValidationException.class, 
										() -> umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora());
		assertEquals(mensagem, exception.getMessage());
	}
	
}
