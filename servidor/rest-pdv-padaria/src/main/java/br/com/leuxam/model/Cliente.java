package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String sobrenome;
	private String telefone;
	private Date dataNascimento;
	private String endereco;
	private Double lucratividade;
	private String sexo;
	
	public Cliente() {}

	public Cliente(Long id, String nome, String sobrenome, String telefone, Date dataNascimento, String endereco,
			Double lucratividade, String sexo) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.lucratividade = lucratividade;
		this.sexo = sexo;
	}
	
}
