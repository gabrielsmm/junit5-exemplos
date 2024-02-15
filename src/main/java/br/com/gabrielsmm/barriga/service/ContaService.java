package br.com.gabrielsmm.barriga.service;

import java.util.List;

import br.com.gabrielsmm.barriga.domain.Conta;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.repositories.ContaRepository;

public class ContaService {

	private ContaRepository repository;

	public ContaService(ContaRepository repository) {
		this.repository = repository;
	}
	
	public Conta salvar(Conta conta) {
		List<Conta> contas = repository.getContasByUsuario(conta.usuario().id());
		contas.stream().forEach(c -> {
			if (c.nome().equalsIgnoreCase(conta.nome())) 
				throw new ValidationException("Usuário já possui uma conta com este nome!");
		});
		return repository.salvar(conta);
	}
	
}
