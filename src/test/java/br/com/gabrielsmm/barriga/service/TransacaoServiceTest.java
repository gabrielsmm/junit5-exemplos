package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.TransacaoBuilder.umaTransacao;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrielsmm.barriga.domain.Transacao;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.repositories.TransacaoDao;

//@EnabledIf(value = "isHoraValida")
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

	@Mock
	private TransacaoDao dao;
	
//	@Mock
//	private ClockService clock;
	
	@InjectMocks
	@Spy
	private TransacaoService service;
	
//	@BeforeEach
//	public void checkTime() {
//		Assumptions.assumeTrue(LocalDateTime.now().getHour() < 20); // Evitar execução dos testes caso seja false
//	}
	
	@BeforeEach
	public void setup() {
//		when(clock.getCurrentTime()).thenReturn(LocalDateTime.of(2024, 2, 19, 4, 30, 28));
		when(service.getTime()).thenReturn(LocalDateTime.of(2024, 2, 19, 4, 30, 28));
	}
	
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
	
	@ParameterizedTest(name = "{1}")
	@MethodSource(value = "dataProvider")
	public void deveRejeitarTransacaoInvalida(Transacao transacaoParaSalvar, String mensagem) {
		String errorMessage = Assertions.assertThrows(ValidationException.class, () -> service.salvar(transacaoParaSalvar)).getMessage();
		Assertions.assertEquals(mensagem, errorMessage);
	}
	
	private static Stream<Arguments> dataProvider() {
		return Stream.of(
				Arguments.of(umaTransacao().comDescricao(null).agora(), "Descrição inexistente"),
				Arguments.of(umaTransacao().comValor(null).agora(), "Valor inexistente"),
				Arguments.of(umaTransacao().comData(null).agora(), "Data inexistente"),
				Arguments.of(umaTransacao().comConta(null).agora(), "Conta inexistente")
		);
	}
	
//	public static boolean isHoraValida() {
//		return LocalDateTime.now().getHour() < 20; // Outra alternativa para evitar execução dos testes caso seja false
//	}
	
}
