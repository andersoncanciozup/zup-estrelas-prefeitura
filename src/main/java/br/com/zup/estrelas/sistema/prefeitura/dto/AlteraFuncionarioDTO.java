package br.com.zup.estrelas.sistema.prefeitura.dto;

public class AlteraFuncionarioDTO {
	
	private String nome;
	private double salario;
	private Long idSecretaria;
	private String funcao;
	private boolean concursado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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

}
