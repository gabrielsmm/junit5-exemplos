package br.com.gabrielsmm.barriga.service;

import java.util.List;

import br.com.gabrielsmm.barriga.domain.Conta;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.external.ContaEvent;
import br.com.gabrielsmm.barriga.service.external.ContaEvent.EventType;
import br.com.gabrielsmm.barriga.service.repositories.ContaRepository;

public class ContaService {

	private ContaRepository repository;
	private ContaEvent event;

	public ContaService(ContaRepository repository, ContaEvent event) {
		this.repository = repository;
		this.event = event;
	}
	
	public Conta salvar(Conta conta) {
		List<Conta> contas = repository.getContasByUsuario(conta.usuario().id());
		contas.stream().forEach(c -> {
			if (c.nome().equalsIgnoreCase(conta.nome())) 
				throw new ValidationException("Usuário já possui uma conta com este nome!");
		});
		Conta contaPersistida = repository.salvar(conta);
		try {
			event.dispatch(contaPersistida, EventType.CREATED);
		} catch (Exception e) {
			repository.delete(contaPersistida);
			throw new RuntimeException("Falha na crição da conta, tente novamente");
		}
		return contaPersistida;
	}
	
}
