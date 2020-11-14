package br.com.zup.estrelas.sistema.prefeitura.dto;

import java.time.LocalDate;

public class FuncionarioDTO {

	private String nome;
	private String cpf;
	private double salario;
	private Long idSecretaria;
	private String funcao;
	private boolean concursado;
	private LocalDate dataAdmissao;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public Long getIdSecretaria() {
		return idSecretaria;
	}
	public void setIdSecretaria(Long idSecretaria) {
		this.idSecretaria = idSecretaria;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public boolean isConcursado() {
		return concursado;
	}
	public void setConcursado(boolean concursado) {
		this.concursado = concursado;
	}
	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
	
}
