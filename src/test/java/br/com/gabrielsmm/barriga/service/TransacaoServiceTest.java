package br.com.gabrielsmm.barriga.service;

import static br.com.gabrielsmm.barriga.domain.builders.TransacaoBuilder.umaTransacao;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import br.com.gabrielsmm.barriga.domain.Transacao;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.repositories.TransacaoDao;

@Tag("service")
@Tag("transacao")
//@EnabledIf(value = "isHoraValida")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransacaoServiceTest {

	@Mock
	private TransacaoDao dao;
	
//	@Mock
//	private ClockService clock;
	
	@InjectMocks
	@Spy
	private TransacaoService service;
	
	@Captor
	private ArgumentCaptor<Transacao> captor;
	
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
	
	@Test
	public void deveRejeitarTransacaoSemValor() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Transacao transacao = umaTransacao().comValor(null).agora();
		
		Method metodo = TransacaoService.class.getDeclaredMethod("validarCamposObrigatorios", Transacao.class);
		metodo.setAccessible(true);
		Exception ex = Assertions.assertThrows(Exception.class, 
											  () -> metodo.invoke(new TransacaoService(), transacao));
		Assertions.assertEquals("Valor inexistente", ex.getCause().getMessage());
	}
	
	@Test
	public void deveRejeitarTransacaoTardeDaNoite() {	
//		Mockito.reset(service);
		when(service.getTime()).thenReturn(LocalDateTime.of(2024, 2, 19, 21, 30, 28));
		String errorMessage = Assertions.assertThrows(RuntimeException.class, () -> service.salvar(umaTransacao().agora())).getMessage();
		Assertions.assertEquals("Tente novamente amanhã", errorMessage);
	}
	
	@Test
	public void deveSalvarTransacaoComoPendentePorPadrao() {
		Transacao transacao = umaTransacao().comStatus(null).agora();
		service.salvar(transacao);
		
		Mockito.verify(dao).salvar(captor.capture());
		Transacao transacaoValidada = captor.getValue();
		Assertions.assertFalse(transacaoValidada.getStatus());
	}
	
//	public static boolean isHoraValida() {
//		return LocalDateTime.now().getHour() < 20; // Outra alternativa para evitar execução dos testes caso seja false
//	}
	
}
