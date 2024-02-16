package br.com.gabrielsmm.barriga.service.external;

import br.com.gabrielsmm.barriga.domain.Conta;

public interface ContaEvent {

	public enum EventType { CREATED, UPDATED, DELETED }
	
	void dispatch(Conta conta, EventType type) throws Exception;
	
}
