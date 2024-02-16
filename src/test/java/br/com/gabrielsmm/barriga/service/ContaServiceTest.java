package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.ContaBuilder.umaConta;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrielsmm.barriga.domain.Conta;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.external.ContaEvent;
import br.com.gabrielsmm.barriga.service.external.ContaEvent.EventType;
import br.com.gabrielsmm.barriga.service.repositories.ContaRepository;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

	@Mock
	private ContaRepository repository;
	
	@Mock
	private ContaEvent event;
	
	@InjectMocks
	private ContaService service;
	
	@Captor
	private ArgumentCaptor<Conta> contaCaptor;
	
	@Test
	public void deveSalvarPrimeiraContaComSucesso() throws Exception {
		Conta contaToSave = umaConta().comId(null).agora();
		
		when(repository.salvar(Mockito.any(Conta.class))).thenReturn(umaConta().agora());
		
		Mockito.doNothing().when(event).dispatch(umaConta().agora(), EventType.CREATED); // Validando método void
		
		Conta savedConta = service.salvar(contaToSave);
		
		Assertions.assertNotNull(savedConta.id());
		verify(repository).salvar(contaCaptor.capture());
		Assertions.assertNull(contaCaptor.getValue().id());
		Assertions.assertTrue(contaCaptor.getValue().nome().startsWith("Conta Válida"));
	}
	
	@Test
	public void deveSalvarSegundaContaComSucesso() {
		Conta contaToSave = umaConta().comId(null).agora();
		
		when(repository.getContasByUsuario(contaToSave.usuario().id()))
		.thenReturn(Arrays.asList(umaConta().comNome("Outra conta").agora()));
		
		when(repository.salvar(Mockito.any(Conta.class))).thenReturn(umaConta().agora());
		
		Conta savedConta = service.salvar(contaToSave);
		
		Assertions.assertNotNull(savedConta.id());
		verify(repository).salvar(Mockito.any(Conta.class));
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
	
	@Test
	public void naoDeveManterContaSemEvento() throws Exception {
		Conta contaToSave = umaConta().comId(null).agora();
		Conta contaSalva = umaConta().agora();
		
		when(repository.salvar(Mockito.any(Conta.class))).thenReturn(contaSalva);
		
		Mockito.doThrow(new Exception("Falha catastrófica"))
		.when(event).dispatch(contaSalva, EventType.CREATED);
		
		String errorMessage = Assertions.assertThrows(Exception.class, () -> service.salvar(contaToSave)).getMessage();
		Assertions.assertEquals("Falha na criação da conta, tente novamente", errorMessage);
		
		verify(repository).delete(contaSalva);
	}
	
}
