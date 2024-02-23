package br.com.gabrielsmm.barriga.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Suite de Testes")
//@SelectClasses(value = {
//		UsuarioTest.class,
//		UsuarioServiceTest.class
//})
@SelectPackages(value = {
		"br.com.gabrielsmm.barriga.service",
		"br.com.gabrielsmm.barriga.domain"
})
public class SuiteTest {
	
//	@BeforeAll
	
}
