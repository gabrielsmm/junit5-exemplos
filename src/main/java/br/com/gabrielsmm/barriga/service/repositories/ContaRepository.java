package br.com.gabrielsmm.barriga.service.repositories;

import java.util.List;

import br.com.gabrielsmm.barriga.domain.Conta;

public interface ContaRepository {

	Conta salvar(Conta conta);
	
	List<Conta> getContasByUsuario(Long usuarioId);
}
