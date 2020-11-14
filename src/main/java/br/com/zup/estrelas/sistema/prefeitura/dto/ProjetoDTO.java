package br.com.zup.estrelas.sistema.prefeitura.dto;

public class ProjetoDTO {
	
	private String nome;
	private long idSecretaria;
	private String descricao;
	private double custo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getCusto() {
		return custo;
	}
	public void setCusto(double custo) {
		this.custo = custo;
	}
	public long getIdSecretaria() {
		return idSecretaria;
	}
	public void setIdSecretaria(long idSecretaria) {
		this.idSecretaria = idSecretaria;
	}
	
	

}
