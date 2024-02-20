package br.com.gabrielsmm.barriga.service;

import br.com.gabrielsmm.barriga.domain.Transacao;
import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;
import br.com.gabrielsmm.barriga.service.external.ClockService;
import br.com.gabrielsmm.barriga.service.repositories.TransacaoDao;

public class TransacaoService {

	private TransacaoDao dao;
	private ClockService clock;
	
	public Transacao salvar(Transacao transacao) {
		validar(transacao);
		return dao.salvar(transacao);
	}

	private void validar(Transacao transacao) {
		if (clock.getCurrentTime().getHour() >= 20) throw new RuntimeException("Tente novamente amanhã");
		
		if (transacao.getDescricao() == null) throw new ValidationException("Descrição inexistente");
		if (transacao.getValor() == null) throw new ValidationException("Valor inexistente");
		if (transacao.getData() == null) throw new ValidationException("Data inexistente");
		if (transacao.getConta() == null) throw new ValidationException("Conta inexistente");
		if (transacao.getStatus() == null) transacao.setStatus(false);
	}
	
}
