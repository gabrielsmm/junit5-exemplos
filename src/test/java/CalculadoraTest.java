import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculadoraTest {

	private static Calculadora calc;
	private static int contador = 0;
	
	@BeforeEach
	public void setup() {

	}
	
	@AfterEach
	public void teardown() {
		
	}
	
	@BeforeAll
	public static void setupAll() {
		System.out.println("--- Before All ---");
		calc = new Calculadora();
	}
	
	@AfterAll
	public static void teardownAll() {
		
	}
	
	@Test
	public void testSomar() {
		System.out.println(++contador);
		Assertions.assertTrue(calc.soma(2, 3) == 5);
		Assertions.assertEquals(5, calc.soma(2, 3));
	}
	
	@Test
	public void assertivas() {
		System.out.println(++contador);
		Assertions.assertEquals("casa", "casa");
		Assertions.assertNotEquals("casa", "Casa");
		Assertions.assertTrue("casa".equalsIgnoreCase("CASA"));
		Assertions.assertTrue("Casa".endsWith("sa"));
		Assertions.assertTrue("Casa".startsWith("Ca"));
		Assertions.assertFalse("Casa".startsWith("ca"));
		
		List<String> s1 = new ArrayList<>();
		List<String> s2 = new ArrayList<>();
		List<String> s3 = null;
		
		Assertions.assertEquals(s1, s2);
		Assertions.assertSame(s1, s1);
		Assertions.assertNotEquals(s1, s3);
		Assertions.assertNull(s3);
		Assertions.assertNotNull(s1);
//		Assertions.fail("Falhou com êxito");
	}
	
	@Test
	public void deveRetornarNumeroInteiroNaDivisao() {
		System.out.println(++contador);
		float resultado = calc.dividir(6, 2);
		Assertions.assertEquals(3, resultado);
	}
	
	@Test
	public void deveRetornarNumeroNegativoNaDivisao() {
		System.out.println(++contador);
		float resultado = calc.dividir(6, -2);
		Assertions.assertEquals(-3, resultado);
	}
	
	@Test
	public void deveRetornarNumeroDecimalNaDivisao() {
		System.out.println(++contador);
		float resultado = calc.dividir(10, 3);
		Assertions.assertEquals(3.3333332538604736, resultado);
		Assertions.assertEquals(3.33, resultado, 0.01); // margem de erro
	}
	
	@Test
	public void deveRetornarZeroComNumeradorZeroNaDivisao() {
		System.out.println(++contador);
		float resultado = calc.dividir(0, 2);
		Assertions.assertEquals(0, resultado);
	}
	
	@Test
	public void deveLancarExcecaoQuandoDividirPorZero_Junit4() {
		System.out.println(++contador);
		try {
			float resultado = 10 / 0;
			Assertions.fail("Deveria ter sido lançada uma exceção na divisão");
		} catch (ArithmeticException e) {	
			Assertions.assertEquals("/ by zero", e.getMessage());
		}
	}
	
	@Test
	public void deveLancarExcecaoQuandoDividirPorZero_Junit5() {
		System.out.println(++contador);
		ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class, () -> {
			float resultado = 10 / 0;
		});
		Assertions.assertEquals("/ by zero", exception.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Teste1", "Teste2"})
	public void testStrings(String param) {
		System.out.println(param);
		assertNotNull(param);
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"6, 2, 3",
			"6, -2, -3",
			"10, 3, 3.3333332538604736",
			"0, 2, 0"
	})
	public void deveDividirCorretamente(int num, int den, double res) {
		float resultado = calc.dividir(num, den);
		Assertions.assertEquals(res, resultado);
	}
	
}
