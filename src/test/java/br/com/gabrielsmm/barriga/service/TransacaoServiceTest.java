package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.TransacaoBuilder.umaTransacao;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrielsmm.barriga.domain.Transacao;
import br.com.gabrielsmm.barriga.service.repositories.TransacaoDao;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

	@Mock
	private TransacaoDao dao;
	
	@InjectMocks
	private TransacaoService service;
	
	@Test
	public void deveSalvarTransacaoValida() {
		Transacao transacaoParaSalvar = umaTransacao().comId(null).agora();
		
		when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransacao().agora());
		
		Transacao transacaoSalva = service.salvar(transacaoParaSalvar);
		Assertions.assertEquals(umaTransacao().agora(), transacaoSalva);
		Assertions.assertAll("Transação",
				() -> Assertions.assertEquals(1L, transacaoSalva.getId()),
				() -> Assertions.assertEquals("Transação Válida", transacaoSalva.getDescricao()),
				() -> {
					Assertions.assertAll("Conta", 
							() -> Assertions.assertEquals("Conta Válida", transacaoSalva.getConta().nome()),
							() -> {
								Assertions.assertAll("Usuário", 
										() -> Assertions.assertEquals("Usuário Válido", transacaoSalva.getConta().usuario().nome()),
										() -> Assertions.assertEquals("12345678", transacaoSalva.getConta().usuario().senha())
								);
							}
					);
				}
		);
	}
	
}
