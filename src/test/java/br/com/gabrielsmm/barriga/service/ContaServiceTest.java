package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.ContaBuilder.umaConta;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrielsmm.barriga.domain.Conta;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.repositories.ContaRepository;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

	@Mock
	private ContaRepository repository;
	
	@InjectMocks
	private ContaService service;
	
	@Test
	public void deveSalvarPrimeiraContaComSucesso() {
		Conta contaToSave = umaConta().comId(null).agora();
		
		when(repository.salvar(contaToSave)).thenReturn(umaConta().agora());
		
		Conta savedConta = service.salvar(contaToSave);
		
		Assertions.assertNotNull(savedConta.id());
		verify(repository).salvar(savedConta);
	}
	
	@Test
	public void deveSalvarSegundaContaComSucesso() {
		Conta contaToSave = umaConta().comId(null).agora();
		
		when(repository.getContasByUsuario(contaToSave.usuario().id()))
		.thenReturn(Arrays.asList(umaConta().comNome("Outra conta").agora()));
		
		when(repository.salvar(contaToSave)).thenReturn(umaConta().agora());
		
		Conta savedConta = service.salvar(contaToSave);
		
		Assertions.assertNotNull(savedConta.id());
		verify(repository).salvar(savedConta);
	}
	
	@Test
	public void deveRejeitarContaRepetida() {
		Conta contaToSave = umaConta().comId(null).agora();
		
		when(repository.getContasByUsuario(contaToSave.usuario().id()))
			.thenReturn(Arrays.asList(umaConta().agora()));
		
		String errorMessage = Assertions.assertThrows(ValidationException.class, () -> service.salvar(contaToSave)).getMessage();
		Assertions.assertEquals("Usuário já possui uma conta com este nome!", errorMessage);
		
		verify(repository, Mockito.never()).salvar(contaToSave);
	}
	
}
