package br.com.gabrielsmm.barriga.domain.builders;

import java.time.LocalDate;

import br.com.gabrielsmm.barriga.domain.Conta;
import br.com.gabrielsmm.barriga.domain.Transacao;


public class TransacaoBuilder {
	
	private Transacao elemento;
	
	private TransacaoBuilder() {
		
	}

	public static TransacaoBuilder umaTransacao() {
		TransacaoBuilder builder = new TransacaoBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(TransacaoBuilder builder) {
		builder.elemento = new Transacao();
		Transacao elemento = builder.elemento;

		
		elemento.setId(0L);
		elemento.setDescricao("");
		elemento.setValor(0.0);
		elemento.setConta(null);
		elemento.setData(null);
		elemento.setStatus(false);
	}

	public TransacaoBuilder comId(Long param) {
		elemento.setId(param);
		return this;
	}

	public TransacaoBuilder comDescricao(String param) {
		elemento.setDescricao(param);
		return this;
	}

	public TransacaoBuilder comValor(Double param) {
		elemento.setValor(param);
		return this;
	}

	public TransacaoBuilder comConta(Conta param) {
		elemento.setConta(param);
		return this;
	}

	public TransacaoBuilder comData(LocalDate param) {
		elemento.setData(param);
		return this;
	}

	public TransacaoBuilder comStatus(Boolean param) {
		elemento.setStatus(param);
		return this;
	}

	public Transacao agora() {
		return elemento;
	}
	
}
