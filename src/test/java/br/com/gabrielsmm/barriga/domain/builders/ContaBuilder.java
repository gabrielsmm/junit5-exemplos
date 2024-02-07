package br.com.gabrielsmm.barriga.domain.builders;

import br.com.gabrielsmm.barriga.domain.Usuario;

import static br.com.gabrielsmm.barriga.domain.builders.UsuarioBuilder.umUsuario;

import br.com.gabrielsmm.barriga.domain.Conta;

public class ContaBuilder {
	
	private Long id;
	private String nome;
	private Usuario usuario;

	private ContaBuilder(){}

	public static ContaBuilder umaConta() {
		ContaBuilder builder = new ContaBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	private static void inicializarDadosPadroes(ContaBuilder builder) {
		builder.id = 1L;
		builder.nome = "Conta Válida";
		builder.usuario = umUsuario().agora();
	}

	public ContaBuilder comId(Long id) {
		this.id = id;
		return this;
	}

	public ContaBuilder comNome(String nome) {
		this.nome = nome;
		return this;
	}

	public ContaBuilder comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Conta agora() {
		return new Conta(id, nome, usuario);
	}
	
}
