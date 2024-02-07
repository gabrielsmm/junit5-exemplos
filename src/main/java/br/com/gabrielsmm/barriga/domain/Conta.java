package br.com.gabrielsmm.barriga.domain;

import br.com.gabrielsmm.barriga.domain.exceptions.ValidationException;

public class Conta {
	
	private Long id;
	private String nome;
	private Usuario usuario;
	
	public Conta(Long id, String nome, Usuario usuario) {
		if (nome == null) throw new ValidationException("Nome é obrigatório");
		if (usuario == null) throw new ValidationException("Usuário é obrigatório");
		
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
	}

	public Long id() {
		return id;
	}

	public String nome() {
		return nome;
	}

	public Usuario usuario() {
		return usuario;
	}
	
}
